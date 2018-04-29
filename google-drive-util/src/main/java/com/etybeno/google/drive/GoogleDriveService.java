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

    public synchronized static GoogleDriveService _initialize(String dataStoreDir, String appName, String... driveScopes) throws Exception {
        if (null == _instance)
            _instance = new GoogleDriveService(StringUtil.isNullOrEmpty(dataStoreDir) ? "runtime/var/google-drive" : dataStoreDir,
                    StringUtil.isNullOrEmpty(appName) ? "com.etybeno.google.drive" : appName, driveScopes);
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
        this("runtime/var/google-drive", "com.etybeno.google.drive", DriveScopes.DRIVE);
    }

    public GoogleDriveService(Credential credential, String appName, String[] scopes) throws GeneralSecurityException, IOException {
        this.appName = appName;
        drive = new Drive.Builder(SingletonUtil.getHttpTransport(), SingletonUtil.getJsonFactory(), credential)
                .setApplicationName(appName)
                .build();
        batchService.addBatchExecutor(new BatchRequestExecutor(appName, drive.batch()));
    }

    private Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(SingletonUtil.getJsonFactory(),
                new FileReader(new java.io.File("./config/google/google_drive_client_secrets.json")));
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                SingletonUtil.getHttpTransport(), SingletonUtil.getJsonFactory(), clientSecrets,
                Collections.singleton(DriveScopes.DRIVE)).setDataStoreFactory(
                new FileDataStoreFactory(new java.io.File("runtime/var/google-drive"))).build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public GoogleDriveService(String dataStoreDir, String appName, String... scopes) throws Exception {
        this.appName = appName;
        Credential credential = authorize();
//                GoogleBaseConfiguration.getGoogleDriveAuthorizeJson(), dataStoreDir, scopes);
        drive = new Drive.Builder(SingletonUtil.getHttpTransport(), SingletonUtil.getJsonFactory(), credential)
                .setApplicationName(appName)
                .build();
        batchService.addBatchExecutor(new BatchRequestExecutor(appName, drive.batch()));
    }

    public Drive drive() {
        return this.drive;
    }

    public void addBatch(AbstractGoogleJsonClientRequest request) {
        batchService.addRequest(appName, new BatchRequestModel(request, createCallback()));
    }

    public void addBatch(AbstractGoogleJsonClientRequest request, JsonBatchCallback callback) {
        batchService.addRequest(appName, new BatchRequestModel(request, callback));
    }

    private RetryBatchCallback createCallback() {
        return new RetryBatchCallback();
    }

    public static void main(String[] args) throws Exception {
        GoogleBaseConfiguration.setBaseConfig("runtime/config/");
        test2();
    }

    public static void test2() throws Exception {
        GoogleDriveFileService fileService = GoogleDriveFileService._load();
        GoogleDriveService driveService = GoogleDriveService._load();
        //
        java.io.File srcFile = new java.io.File("/home/thangpham/Workspace/Projects/learning/vietlinhtinh/content/giau_mat/Hot-girl-g%E1%BB%A3i-c%E1%BA%A3m-c%C3%B9ng-v%C3%B2ng-3-n%C3%B3ng-b%E1%BB%8Fng-p-75.jpg");
        Drive.Files.Create create = fileService.uploadFile(null, null, srcFile, "test.jpg",
                Arrays.asList("id", "name", "parents", "webViewLink", "webContentLink", "thumbnailLink", "iconLink"));
        File execute = create.execute();
        System.out.println("ss" + StringUtil.GSON.toJson(execute));
//        BatchRequest batch = driveService.drive().batch();
//        create.queue(batch,
//                new JsonBatchCallback<File>() {
//                    @Override
//                    public void onSuccess(File file, HttpHeaders responseHeaders) throws IOException {
//                        System.out.println(StringUtil.GSON.toJson(file));
//                        System.out.println(responseHeaders);
//                    }
//
//                    @Override
//                    public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
//                    }
//                });

    }

    public static void test1() throws Exception {
        GoogleDriveService googleDriveAccesss = GoogleDriveService._load();
        String pageToken = null;
        do {
            FileList result = googleDriveAccesss.drive().files().list()
                    .setQ("mimeType='image/jpeg'")
                    .setFields("nextPageToken, files(id, name, parents, webViewLink, webContentLink, thumbnailLink, iconLink)")
                    .execute();
            System.out.println(result.getFiles().size());
            for (File file : result.getFiles()) {
                System.out.println(String.format("Found file: %s (%s) view = %s parents=%s",
                        file.getName(), file.getId(), file.getWebViewLink(), StringUtil.GSON.toJson(file)));
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        System.exit(0);
    }

}
