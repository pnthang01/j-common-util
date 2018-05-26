package com.etybeno.google.drive;

import com.etybeno.common.util.StringUtil;
import com.etybeno.google.drive.model.GoogleDriveConnection;
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

    public static List<String> generatedIds(GoogleDriveConnection con, int count) throws IOException {
         return con.drive().files().generateIds().setCount(count).execute().getIds();
    }

    public static Drive.Files.Create createFolder(GoogleDriveConnection con, String fileId, String folderPath) throws IOException {
        File fileMetadata = new File()
                .setName(folderPath)
                .setMimeType("application/vnd.google-apps.folder");
        if (!StringUtil.isNullOrEmpty(fileId)) fileMetadata.setId(fileId);
        return con.drive().files().create(fileMetadata)
                .setFields("id");
    }

    public static void uploadFolder(java.io.File sourceFolder, int copy, boolean shared) {

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
    public static Drive.Files.Create uploadFile(GoogleDriveConnection con, String parentFileId, String selfId, java.io.File srcFile, String destFilename, List<String> fields) throws IOException {
        return uploadFile(con, parentFileId, selfId, srcFile, destFilename, null, fields);
    }

    public static Drive.Files.Create uploadFile(GoogleDriveConnection con, String parentFileId, java.io.File srcFile, String destFilename, List<String> fields) throws IOException {
        return uploadFile(con, parentFileId, null, srcFile, destFilename, null, fields);
    }

    public static Drive.Files.Create uploadFile(GoogleDriveConnection con, String parentFileId, String selfId, java.io.File srcFile, String destFilename,
                                         List<Permission> permissions, List<String> fields) throws IOException {
        File fileMetadata = new File().setName(destFilename).setId(selfId);
        String mimeType = GoogleDriveUtil.convertMimeType(srcFile);
        String fieldsToStr = fields.stream().collect(Collectors.joining(","));
        if(null != permissions && !permissions.isEmpty()) fileMetadata.setPermissions(permissions);
        if (!StringUtil.isNullOrEmpty(parentFileId)) fileMetadata.setParents(Collections.singletonList(parentFileId));
        //
        FileContent mediaContent = new FileContent(mimeType, srcFile);
        return con.drive().files()
                .create(fileMetadata, mediaContent)
                .setFields(fieldsToStr);
    }

}
