package com.etybeno.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thangpham on 09/02/2018.
 */
public class FileUtil {

    public static List<String> readLines(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        List<String> results = new ArrayList<>();
        while((line = reader.readLine()) != null) results.add(line);
        return results;
    }
}
