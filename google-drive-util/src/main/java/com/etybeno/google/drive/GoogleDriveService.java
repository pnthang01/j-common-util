package com.etybeno.google.drive;

import com.etybeno.common.config.BaseConfiguration;
import com.etybeno.common.util.StringUtil;
import com.etybeno.google.*;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.text.NumberFormat;
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

    private final Drive drive;
    private final String appName;
    private final String DATA_STORE_DIR = "runtime/var/google-drive";
    private final BatchRequestService batchService = BatchRequestService._load();


    public GoogleDriveService(String appName) throws Exception {
        this.appName = appName;
        AuthorizationOauth2 author = AuthorizationOauth2._load();
        Credential credential = author.authorize(BaseConfiguration.getGoogleDriveAuthorizeJson(),
                DATA_STORE_DIR, DriveScopes.DRIVE);
        drive = new Drive.Builder(author.getHttpTransport(), author.getJsonFactory(), credential)
                .setApplicationName(appName)
                .build();
        batchService.setBatchExecutor(new BatchRequestExecutor(appName, drive.batch()));
    }

    public GoogleDriveService() throws Exception {
        this("com.etybeno.google.drive");
    }

    public Drive getDrive() {
        return this.drive;
    }

    public static void main(String[] args) throws Exception {
        BaseConfiguration.setBaseConfig("runtime/config/");
        GoogleDriveService googleDriveAccesss = GoogleDriveService._load();
//        googleDriveAccesss.uploadFile("1Iidp8xf8vb2uzDr_fPfTOGtFM1gKKImY", new java.io.File("/home/thangpham/Downloads/0003/06.png"), true).execute();
//        googleDriveAccesss.createFolder("", "vietlinhtinh_test").execute();
        String pageToken = null;
        do {
            FileList result = googleDriveAccesss.getDrive().files().list()
                    .setQ("mimeType='image/jpeg'")
                    .setFields("nextPageToken, files(id, name, parents, webViewLink)")
                    .execute();
//                    .setQ("name contains 'viet'")
//                    .setSpaces("drive")

//                    .setPageToken(pageToken)
//                    .execute();
            System.out.println(result.getFiles().size());
            for (File file : result.getFiles()) {
                System.out.println(String.format("Found file: %s (%s) view = %s parents=%s",
                        file.getName(), file.getId(), file.getWebViewLink(), StringUtil.GSON.toJson(file.getParents())));
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        System.exit(0);
    }

    public List<String> generatedIds(int count) throws IOException {
        return drive.files().generateIds().setCount(count).execute().getIds();
    }

    public Drive.Files.Create createFolder(String id, String folderPath) throws IOException {
        File fileMetadata = new File()
                .setName(folderPath)
                .setMimeType("application/vnd.google-apps.folder");
        if (!StringUtil.isNullOrEmpty(id)) fileMetadata.setId(id);
        return drive.files().create(fileMetadata)
                .setFields("id");
    }

    public void uploadFolder(java.io.File sourceFolder, int copy, boolean shared) {

    }

    public Drive.Files.Create uploadFile(String folderId, java.io.File sourceFile, boolean shared) throws IOException {
        String mimeType = GoogleDriveUtil.convertMimeType(sourceFile);
        return uploadFile(folderId, sourceFile, sourceFile.getName(), mimeType, shared);
    }

    public Drive.Files.Create uploadFile(String folderId, java.io.File sourceFile, String destFileName,
                                         String mimeType, boolean shared) throws IOException {
        File fileMetadata = new File()
                .setName(destFileName);
//                .setShared(shared);
        if (!StringUtil.isNullOrEmpty(folderId)) fileMetadata.setParents(Collections.singletonList(folderId));
        //
        FileContent mediaContent = new FileContent(mimeType, sourceFile);
        return drive.files()
                .create(fileMetadata, mediaContent)
                .setFields("id");
    }

    public void batchCreateFolder(String id, String folderPath) throws IOException {
        Drive.Files.Create folder = createFolder(id, folderPath);
        batchService.addRequest(appName, new BatchRequestModel(folder, createCallback()));
    }

    public void batchUploadFile(String folderId, java.io.File sourceFile, boolean shared) throws IOException {
        Drive.Files.Create create = uploadFile(folderId, sourceFile, shared);
        batchService.addRequest(appName, new BatchRequestModel(create, createCallback()));
    }

    public void batchUploadFile(String folderId, java.io.File sourceFile, String destFileName,
                                String mimeType, boolean shared) throws IOException {
        Drive.Files.Create create = uploadFile(folderId, sourceFile, destFileName, mimeType, shared);
        batchService.addRequest(appName, new BatchRequestModel(create, createCallback()));
    }

    private RetryBatchCallback createCallback() {
        return new RetryBatchCallback();
    }


    //        MediaHttpUploader uploader = insert.getMediaHttpUploader();
//
//        uploader.setDirectUploadEnabled(useDirectUpload);
//        uploader.setProgressListener(new FileUploadProgressListener());
//        return insert.execute();
    public static class FileUploadProgressListener implements MediaHttpUploaderProgressListener {

        @Override
        public void progressChanged(MediaHttpUploader uploader) throws IOException {
            switch (uploader.getUploadState()) {
                case INITIATION_STARTED:
                    System.out.println("Upload Initiation has started.");
                    break;
                case INITIATION_COMPLETE:
                    System.out.println("Upload Initiation is Complete.");
                    break;
                case MEDIA_IN_PROGRESS:
                    System.out.println("Upload is In Progress: "
                            + NumberFormat.getPercentInstance().format(uploader.getProgress()));
                    break;
                case MEDIA_COMPLETE:
                    System.out.println("Upload is Complete!");
                    break;
            }
        }
    }

    public static class FileDownloadProgressListener implements MediaHttpDownloaderProgressListener {

        @Override
        public void progressChanged(MediaHttpDownloader downloader) {
            switch (downloader.getDownloadState()) {
                case MEDIA_IN_PROGRESS:
                    System.out.println("Download is in progress: " + downloader.getProgress());
                    break;
                case MEDIA_COMPLETE:
                    System.out.println("Download is Complete!");
                    break;
            }
        }
    }
}
