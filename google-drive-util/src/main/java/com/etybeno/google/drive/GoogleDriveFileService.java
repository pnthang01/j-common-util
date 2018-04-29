package com.etybeno.google.drive;

import com.etybeno.common.util.StringUtil;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

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
    private ConcurrentLinkedQueue<String> preGeneratedIds;

    public GoogleDriveFileService() throws Exception {
        driveService = GoogleDriveService._load();
        preGeneratedIds = new ConcurrentLinkedQueue<>();
    }

    public List<String> generatedIds(int count) throws IOException {
         return driveService.drive().files().generateIds().setCount(count).execute().getIds();
    }

    public String getPreGeneratedId() throws IOException {
        if(preGeneratedIds.isEmpty()) {
            List<String> strings = generatedIds(10);
            preGeneratedIds.addAll(strings);
        }
        return preGeneratedIds.poll();
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

    /**
     * Create a upload file request. This request will be joined to batch or can be execute immediately.
     * #fileId is optionally param
     * @param parentFileId
     * @param srcFile
     * @param destFilename
     * @param fields
     * @return
     * @throws IOException
     */
    public Drive.Files.Create uploadFile(String parentFileId, String selfId, java.io.File srcFile, String destFilename, List<String> fields) throws IOException {
        return uploadFile(parentFileId, selfId, srcFile, destFilename, null, fields);
    }

    public Drive.Files.Create uploadFile(String parentFileId, java.io.File srcFile, String destFilename, List<String> fields) throws IOException {
        return uploadFile(parentFileId, null, srcFile, destFilename, null, fields);
    }

    public Drive.Files.Create uploadFile(String parentFileId, String selfId, java.io.File srcFile, String destFilename,
                                         List<Permission> permissions, List<String> fields) throws IOException {
        File fileMetadata = new File().setName(destFilename).setId(selfId);
        String mimeType = GoogleDriveUtil.convertMimeType(srcFile);
        String fieldsToStr = fields.stream().collect(Collectors.joining(","));
        if(null != permissions && !permissions.isEmpty()) fileMetadata.setPermissions(permissions);
        if (!StringUtil.isNullOrEmpty(parentFileId)) fileMetadata.setParents(Collections.singletonList(parentFileId));
        //
        FileContent mediaContent = new FileContent(mimeType, srcFile);
        return driveService.drive().files()
                .create(fileMetadata, mediaContent)
                .setFields(fieldsToStr);
    }

}
