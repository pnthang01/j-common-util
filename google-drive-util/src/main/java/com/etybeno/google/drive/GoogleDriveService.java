package com.etybeno.google.drive;

import com.etybeno.common.config.BaseConfiguration;
import com.etybeno.common.util.StringUtil;
import com.etybeno.google.*;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * Created by thangpham on 03/12/2017.
 */
public class GoogleDriveService implements IGoogleApiService {

    private static GoogleDriveService _instance;

    public synchronized static GoogleDriveService _load() throws Exception {
        if (null == _instance) _instance = new GoogleDriveService();
        return _instance;
    }

    private Drive drive;
    private String appName;
    private final BatchRequestService batchService = BatchRequestService._load();

    public GoogleDriveService() throws Exception {
        this("com.etybeno.google.drive", "runtime/var/google-drive", DriveScopes.DRIVE);
    }

    public GoogleDriveService(String appName, String dataStoreDir, String... scopes) throws Exception {
        this.appName = appName;
        AuthorizationOauth2 author = AuthorizationOauth2._load();
        Credential credential = author.authorize(BaseConfiguration.getGoogleDriveAuthorizeJson(),
                dataStoreDir, scopes);
        drive = new Drive.Builder(author.getHttpTransport(), author.getJsonFactory(), credential)
                .setApplicationName(appName)
                .build();
        batchService.setBatchExecutor(new BatchRequestExecutor(appName, drive.batch()));
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
        BaseConfiguration.setBaseConfig("runtime/config/");
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
