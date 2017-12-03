package com.etybeno.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;


/**
 * Created by thangpham on 03/12/2017.
 */
public class AuthorizationOauth2 {

    private static AuthorizationOauth2 _instance;

    public synchronized static AuthorizationOauth2 _load() throws GeneralSecurityException, IOException {
        if(null == _instance) _instance = new AuthorizationOauth2();
        return _instance;
    }

    /** Global instance of the JSON factory. */
    private JsonFactory JSON_FACTORY = null;

    /** Global instance of the HTTP transport. */
    private HttpTransport HTTP_TRANSPORT = null;

    public AuthorizationOauth2() throws GeneralSecurityException, IOException {
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        JSON_FACTORY = JacksonFactory.getDefaultInstance();
    }


    /** Authorizes the installed application to access user's protected data. */
    public Credential authorize(String path, String dataDir, String... scopes) throws Exception {
        // load client secrets
        File file = new File(path);
        if(!file.exists()) throw new IllegalArgumentException("File client secret does not exist.");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(file)));
        // set up authorization code flow
        FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(new File(dataDir));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Arrays.asList(scopes))
                .setDataStoreFactory(dataStoreFactory)
                .build();
//        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public HttpTransport getHttpTransport() {
        return this.HTTP_TRANSPORT;
    }

    public JsonFactory getJsonFactory() {
        return this.JSON_FACTORY;
    }
}
