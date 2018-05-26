package com.etybeno.google.drive;

import com.etybeno.common.util.StringUtil;
import com.etybeno.google.*;
import com.etybeno.google.config.GoogleBaseConfiguration;
import com.etybeno.google.loader.SingleAppCredentialLoader;
import com.etybeno.google.model.BatchRequestModel;
import com.etybeno.google.util.SingletonUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;


/**
 * Created by thangpham on 03/12/2017.
 */
public class GoogleDriveService {

    private static GoogleDriveService _instance;

    public synchronized static GoogleDriveService _initialize(String secretFile, String dataStoreDir,
                                                              String appName, String... driveScopes) throws Exception {
        if (null == _instance)
            _instance = new GoogleDriveService(StringUtil.isNullOrEmpty(secretFile) ? GoogleBaseConfiguration.getGoogleDriveAuthorizeJson() : secretFile,
                    StringUtil.isNullOrEmpty(dataStoreDir) ? GoogleBaseConfiguration.getBaseConfig() + "google-drive" : dataStoreDir,
                    StringUtil.isNullOrEmpty(appName) ? "com.etybeno.google.drive" : appName,
                    driveScopes);
        return _instance;
    }

    public synchronized static GoogleDriveService _load() throws Exception {
        if (null == _instance) _instance = new GoogleDriveService();
        return _instance;
    }

    private Drive drive;
    private String appName;
    private final BatchRequestService batchService = BatchRequestService._load();

    public GoogleDriveService() throws Exception {
        this(GoogleBaseConfiguration.getGoogleDriveAuthorizeJson(), GoogleBaseConfiguration.getBaseConfig() + "var/google-drive"
                , "com.etybeno.google.drive", DriveScopes.DRIVE);
    }

    public GoogleDriveService(String secretFile, String dataStoreDir, String appName, String... scopes) throws Exception {
        this.appName = appName;
        Credential credential = authorize(secretFile, dataStoreDir, scopes);
        drive = new Drive.Builder(SingletonUtil.getHttpTransport(), SingletonUtil.getJsonFactory(), credential)
                .setApplicationName(appName)
                .build();
        batchService.addBatchExecutor(new BatchRequestExecutor(appName, drive.batch()));
    }


    public GoogleDriveService(Credential credential, String appName) throws Exception {
        this.appName = appName;
        drive = new Drive.Builder(SingletonUtil.getHttpTransport(), SingletonUtil.getJsonFactory(), credential)
                .setApplicationName(appName)
                .build();
        batchService.addBatchExecutor(new BatchRequestExecutor(appName, drive.batch()));
    }



    private Credential authorize(String secretFile, String dataStoreDir, String... scopes) throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(SingletonUtil.getJsonFactory(),
                new FileReader(new java.io.File(secretFile)));
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                SingletonUtil.getHttpTransport(), SingletonUtil.getJsonFactory(), clientSecrets,
                Arrays.asList(scopes)).setDataStoreFactory(
                new FileDataStoreFactory(new java.io.File(dataStoreDir))).build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private RetryBatchCallback createCallback() {
        return new RetryBatchCallback();
    }

    public static void main(String[] args) throws Exception {
        GoogleBaseConfiguration.setBaseConfig("runtime/config/");
        test1();
    }

    public static void test1() throws Exception {
        GoogleDriveService googleDriveAccesss = GoogleDriveService._initialize(null,
                "var/google-drive", null, DriveScopes.DRIVE);
        String pageToken = null;
            FileList result = googleDriveAccesss.drive.files().list()
                    .setQ("mimeType='image/jpeg' and parents='1-lJsVmEcPN54sAkywQg2Wtst-Acls2uZ'")
                    .setFields("nextPageToken, files(id, name, parents, webViewLink, webContentLink, thumbnailLink, iconLink)")
                    .execute();
            System.out.println(result.getFiles().size());
            for (File file : result.getFiles()) {
                System.out.println(String.format("Found file: %s (%s) view = %s parents=%s",
                        file.getName(), file.getId(), file.getWebViewLink(), StringUtil.GSON.toJson(file)));
            }
            pageToken = result.getNextPageToken();
        System.exit(0);
    }

}
