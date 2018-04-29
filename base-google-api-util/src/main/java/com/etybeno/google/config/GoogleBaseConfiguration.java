package com.etybeno.google.config;

import com.etybeno.common.util.StringUtil;

/**
 * Created by thangpham on 20/04/2018.
 */
public class GoogleBaseConfiguration {

    private static String baseConfig = "config/";

    private static String GOOGLE_DRIVE_AUTHORIZE_JSON = "google_drive_client_secrets.json";

    public static void setBaseConfig(String targetConfDir) throws IllegalArgumentException {
        if (StringUtil.isNullOrEmpty(targetConfDir)) throw new IllegalArgumentException(
                "The configuration does not have base config. Could not lookup configurations.");
        synchronized (baseConfig) {
            GoogleBaseConfiguration.baseConfig = targetConfDir;
        }
    }

    public static String getGoogleDriveAuthorizeJson() {
        return baseConfig + GOOGLE_DRIVE_AUTHORIZE_JSON;
    }

    public static void setGoogleDriveAuthorizeJson(String fileName) {
        GOOGLE_DRIVE_AUTHORIZE_JSON = fileName;
    }

}
