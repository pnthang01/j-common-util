package com.etybeno.google.loader;

import com.etybeno.google.model.GoogleServiceInformation;
import com.etybeno.google.util.SingletonUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * Created by thangpham on 24/04/2018.
 */
public class SingleAppCredentialLoader extends AbstractCredentialLoader<GoogleServiceInformation>  {

    private String path;
    private String dataDir;
    private String[] scopes;

    public SingleAppCredentialLoader(String path, String dataDir, String[] scopes) {
        this.path = path;
        this.dataDir = dataDir;
        this.scopes = scopes;
    }

    @Override
    public GoogleServiceInformation authorize(String userId) throws IOException, GeneralSecurityException {
        // load client secrets
        File file = new File(path);
        if(!file.exists()) throw new IllegalArgumentException("File client secret does not exist.");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(SingletonUtil.getJsonFactory(),
                new InputStreamReader(new FileInputStream(file)));
        // set up authorization code flow
        FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(new File(dataDir));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                SingletonUtil.getHttpTransport(), SingletonUtil.getJsonFactory(), clientSecrets, Arrays.asList(scopes))
                .setDataStoreFactory(dataStoreFactory)
                .build();
        // authorize
        Credential authorize = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userId);
        GoogleServiceInformation serviceInformation = new GoogleServiceInformation();
        serviceInformation.setCredential(authorize);
        return serviceInformation;
    }
}
