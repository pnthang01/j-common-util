package com.etybeno.google.drive;

import com.etybeno.common.util.StringUtil;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by thangpham on 19/04/2018.
 */
public class GoogleDriveFileService {

    private static GoogleDriveFileService _instance;

    public static GoogleDriveFileService _load() throws Exception {
        if (null == _instance) _instance = new GoogleDriveFileService();
        return _instance;
    }

    private GoogleDriveService driveService;

    public GoogleDriveFileService() throws Exception {
        driveService = GoogleDriveService._load();
    }

    public List<String> generatedIds(int count) throws IOException {
        return driveService.drive().files().generateIds().setCount(count).execute().getIds();
    }

    public Drive.Files.Create createFolder(String fileId, String folderPath) throws IOException {
        File fileMetadata = new File()
                .setName(folderPath)
                .setMimeType("application/vnd.google-apps.folder");
        if (!StringUtil.isNullOrEmpty(fileId)) fileMetadata.setId(fileId);
        return driveService.drive().files().create(fileMetadata)
                .setFields("id");
    }

    public void uploadFolder(java.io.File sourceFolder, int copy, boolean shared) {

    }

    public Drive.Files.Create uploadFile(String fileId, java.io.File sourceFile, String destFileName,
                                         String mimeType) throws IOException {
        File fileMetadata = new File()
                .setName(destFileName);
        if (!StringUtil.isNullOrEmpty(fileId)) fileMetadata.setParents(Collections.singletonList(fileId));
        //
        FileContent mediaContent = new FileContent(mimeType, sourceFile);
        return driveService.drive().files()
                .create(fileMetadata, mediaContent)
                .setFields("id");
    }

}
