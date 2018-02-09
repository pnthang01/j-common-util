package com.etybeno.common;

import java.io.File;
import java.io.IOException;

/**
 * Created by thangpham on 08/01/2018.
 */
public class Hiaz {
    public static void main(String[] args) throws IOException {
        String folderStr = "/home/thangpham/Workspace/Projects/learning/vietlinhtinh/content/girl/";
        File folder = new File(folderStr);
        File[] tmpArr = folder.listFiles((dir, name) -> name.startsWith("count_"));
        File countFile = tmpArr.length > 0 ? tmpArr[0] : null;
        File[] list = folder.listFiles((dir, name) -> !name.startsWith("count_"));
        int count = 0;
        if (countFile != null) {
            count = Integer.parseInt(countFile.getName().split("_")[1]);
        }
        for (File file : list) {
            if (!file.getName().startsWith("plane_")) {
                String extension = "";
                int i = file.getName().lastIndexOf('.');
                if (i > 0) extension = file.getName().substring(i + 1);
                System.out.println(file.getName());
                boolean exist = true;
                File tmpFile = null;
                while (exist) {
                    String newName = String.format("%06d", ++count);
                    tmpFile = new File(file.getParent() + "/plane_" + newName + "." + extension);
                    exist = tmpFile.exists();
                }
                file.renameTo(tmpFile);
            }
        }
        File newCountFile = new File(folderStr + "count_" + count);
        if (null == countFile) newCountFile.createNewFile();
        else countFile.renameTo(newCountFile);
    }
}
