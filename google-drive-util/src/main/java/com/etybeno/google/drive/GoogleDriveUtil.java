package com.etybeno.google.drive;

import java.io.File;

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

    public static void main(String[] args) {
        double s = 755.58, sum = 0;
        int step = 3;
        for(int i = 0; i < step; i++) {
            sum += s;
            s *= 2;
            System.out.println("cost: " + s + " sum: " + sum);
        }

        System.out.println(sum / 0.76 / 3600);
    }
}
