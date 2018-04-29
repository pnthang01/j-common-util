package com.etybeno.google.drive.model;

import com.etybeno.google.model.GoogleServiceInformation;

/**
 * Created by thangpham on 24/04/2018.
 */
public class DriveServiceInformation extends GoogleServiceInformation {

    private String rootDirId;
    private long limit;
    private long usage;
    private long usageInDrive;
    private long usageInDriveTrash;

    public String getRootDirId() {
        return rootDirId;
    }

    public void setRootDirId(String rootDirId) {
        this.rootDirId = rootDirId;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getUsage() {
        return usage;
    }

    public void setUsage(long usage) {
        this.usage = usage;
    }

    public long getUsageInDrive() {
        return usageInDrive;
    }

    public void setUsageInDrive(long usageInDrive) {
        this.usageInDrive = usageInDrive;
    }

    public long getUsageInDriveTrash() {
        return usageInDriveTrash;
    }

    public void setUsageInDriveTrash(long usageInDriveTrash) {
        this.usageInDriveTrash = usageInDriveTrash;
    }
}
