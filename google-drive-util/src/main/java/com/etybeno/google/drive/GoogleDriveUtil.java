package com.etybeno.google.drive;

import com.etybeno.google.util.SingletonUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by thangpham on 18/12/2017.
 */
public class GoogleDriveUtil {

    public static String convertMimeType(String ext) {
        switch (ext) {
            case "html": return "text/html";
            case "rtf": return "application/rtf";
            case "csv" : return "text/csv";
            case "tsv": return "text/tab-separated-values";
            case "pdf": return "application/pdf";
            case "png":
            case "x-png":
                return "image/png";
            case "jfif":
            case "jfif-tbnl":
            case "jpe":
            case "jpeg":
            case "jpg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            default: return null;
        }
    }

    public static String convertMimeType(File file) {
        if (null == file) return null;
        String[] split = file.getName().split("\\.");
        return convertMimeType(split[split.length - 1]);
    }

    public static GoogleClientSecrets loadGoogleClientSecrets(String fileName) throws IOException {
        return loadGoogleClientSecrets(new java.io.File(fileName));
    }

    public static GoogleClientSecrets loadGoogleClientSecrets(File secretFile) throws IOException {
        return GoogleClientSecrets.load(SingletonUtil.getJsonFactory(),
                new FileReader(secretFile));
    }
}
