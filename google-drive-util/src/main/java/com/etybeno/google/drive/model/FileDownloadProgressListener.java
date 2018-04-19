package com.etybeno.google.drive.model;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;

/**
 * Created by thangpham on 19/04/2018.
 */
public class FileDownloadProgressListener implements MediaHttpDownloaderProgressListener {

    @Override
    public void progressChanged(MediaHttpDownloader downloader) {
        switch (downloader.getDownloadState()) {
            case MEDIA_IN_PROGRESS:
                System.out.println("Download is in progress: " + downloader.getProgress());
                break;
            case MEDIA_COMPLETE:
                System.out.println("Download is Complete!");
                break;
        }
    }
}