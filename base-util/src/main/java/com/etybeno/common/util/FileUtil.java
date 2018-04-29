package com.etybeno.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thangpham on 09/02/2018.
 */
public class FileUtil {

    static String baseFolderPath = "";

    public static void main(String[] args) {
        System.out.println(FileUtil.getRuntimeFolderPath());
    }

    public static String getRuntimeFolderPath() {
        if (baseFolderPath.isEmpty()) {
            try {
                File dir1 = new File(".");
                baseFolderPath = dir1.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baseFolderPath;
    }

    public static List<String> readLines(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        List<String> results = new ArrayList<>();
        while((line = reader.readLine()) != null) results.add(line);
        return results;
    }


}
