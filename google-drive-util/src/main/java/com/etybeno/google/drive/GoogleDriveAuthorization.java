package com.etybeno.google.drive;

import com.etybeno.common.config.BaseConfiguration;
import com.etybeno.google.AuthorizationOauth2;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.text.NumberFormat;


/**
 * Created by thangpham on 03/12/2017.
 */
public class GoogleDriveAuthorization {

    private static GoogleDriveAuthorization _instance;

    public synchronized static GoogleDriveAuthorization _load() throws Exception {
        if(null == _instance) _instance = new GoogleDriveAuthorization();
        return _instance;
    }

    private final Credential credential;
    private final AuthorizationOauth2 authorizationOauth2;
    private final Drive drive;
    private final String DATA_STORE_DIR = "runtime/var/google-drive";


    public GoogleDriveAuthorization() throws Exception {
        authorizationOauth2 = AuthorizationOauth2._load();
        credential = authorizationOauth2.authorize(BaseConfiguration.getGoogleDriveAuthorizeJson(),
                DATA_STORE_DIR, DriveScopes.DRIVE_FILE);
        drive = new Drive.Builder(authorizationOauth2.getHttpTransport(),
                    authorizationOauth2.getJsonFactory(), credential)
                .setApplicationName("com.etybeno.common").build();

    }

    public Credential getCredential() {
        return credential;
    }

    public Drive getDrive() {
        return this.drive;
    }

    public static void main(String[] args) throws Exception {
        BaseConfiguration.setBaseConfig("runtime/config/");
        GoogleDriveAuthorization googleDriveAccesss = GoogleDriveAuthorization._load();
        googleDriveAccesss.uploadFile(true);

        
    }

    /** Uploads a file using either resumable or direct media upload. */
    public File uploadFile(boolean useDirectUpload) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setTitle("/home/thangpham/Downloads/Untitled.jpg");

        FileContent mediaContent = new FileContent("image/jpeg",
                new java.io.File("/home/thangpham/Downloads/Untitled.jpg"));

        Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
        MediaHttpUploader uploader = insert.getMediaHttpUploader();
        uploader.setDirectUploadEnabled(useDirectUpload);
        uploader.setProgressListener(new FileUploadProgressListener());
        return insert.execute();
    }

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
