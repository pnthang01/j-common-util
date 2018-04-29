package com.etybeno.google.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by thangpham on 24/04/2018.
 */
public class SingletonUtil {

    /** Global instance of the HTTP transport. */
    private static NetHttpTransport HTTP_TRANSPORT;

    /** Global instance of the JSON factory. */
    private static JsonFactory JSON_FACTORY = null;

    public synchronized static NetHttpTransport getHttpTransport() throws GeneralSecurityException, IOException {
        if (null == HTTP_TRANSPORT) HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return HTTP_TRANSPORT;
    }

    public synchronized static JsonFactory getJsonFactory() {
        if (null == JSON_FACTORY) JSON_FACTORY = JacksonFactory.getDefaultInstance();
        return JSON_FACTORY;
    }
}