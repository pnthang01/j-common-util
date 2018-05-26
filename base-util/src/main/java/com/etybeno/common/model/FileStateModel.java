/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.common.model;

/**
 *
 * @author thangpham
 */
public class FileStateModel {

    private String fileName;
    private long lastModified;
    private long lastMark;

    public FileStateModel(String fileName) {
        this.fileName = fileName;
        this.lastModified = 0;
        this.lastMark = 0;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public long getLastMark() {
        return lastMark;
    }

    public void setLastMark(long lastMark) {
        this.lastMark = lastMark;
    }

}
