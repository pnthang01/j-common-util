package com.etybeno.common.util;

import com.etybeno.common.model.FileStateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thangpham on 09/02/2018.
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    static String baseFolderPath = "";

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

    public static List<String> getUnreadRecords(File file, FileStateModel state, final int size, final long modifiedDelay, final long offsetDelay) {
        RandomAccessFile randAccess = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        List<String> records = new ArrayList();
        long lastMark = state.getLastMark();
        try {
            //Read real time
            long fileLength = file.length();
            long fileModified = file.lastModified();
            ByteBuffer bb = ByteBuffer.allocate(2048);
            byte[] arr = new byte[2048];
            int le;
            if (fileModified > System.currentTimeMillis() - 10000 && fileLength > lastMark + offsetDelay) {
                randAccess = new RandomAccessFile(file, "r");
                randAccess.seek(lastMark);
                fis = new FileInputStream(randAccess.getFD());
                bis = new BufferedInputStream(fis);
                while ((le = bis.read(arr)) != -1) {
                    for (int i = 0; i < le; ++i) {
                        if ((arr[i] == 10) || (i + 1 < le && (arr[i] == 13 && arr[i + 1] == 10))) {
                            bb.flip();
                            if (arr[i] == 10) state.setLastMark(state.getLastMark() + bb.limit() + 1);
                            else {
                                state.setLastMark(state.getLastMark() + bb.limit() + 2);
                                ++i;
                            }
                            records.add(new String(bb.array(), 0, bb.limit(), "UTF-8").trim());
                            bb.clear();
                        } else bb.put(arr[i]);
                    }
                    if ((size > 0 && records.size() >= size) || fileLength - (offsetDelay / 2) <= state.getLastMark()) break;
                }
            } else if (fileModified < System.currentTimeMillis() - modifiedDelay && fileLength > lastMark) { //Read all in past time
                randAccess = new RandomAccessFile(file, "r");
                randAccess.seek(lastMark);
                fis = new FileInputStream(randAccess.getFD());
                bis = new BufferedInputStream(fis);
                while ((le = bis.read(arr)) != -1) {
                    for (int i = 0; i < le; ++i) {
                        if ((arr[i] == 10) || (i + 1 < le && (arr[i] == 13 && arr[i + 1] == 10))) {
                            bb.flip();
                            if (arr[i] == 10) state.setLastMark(state.getLastMark() + bb.limit() + 1);
                            else {
                                state.setLastMark(state.getLastMark() + bb.limit() + 2);
                                ++i;
                            }
                            records.add(new String(bb.array(), 0, bb.limit(), "UTF-8").trim());
                            bb.clear();
                        } else bb.put(arr[i]);
                    }
                    if ((size > 0 && records.size() >= size) || fileLength <= state.getLastMark()) break;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error happened when getUnreadRecords", ex);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ex) { LOGGER.error("Cannot close BufferedInputStream", ex); }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) { LOGGER.error("Cannot close FileInputStream", ex); }
            }
            if (randAccess != null) {
                try {
                    randAccess.close();
                } catch (IOException ex) {
                    LOGGER.error("Cannot close RandomAccessFile", ex);
                }
            }
        }
        return records;
    }

}
