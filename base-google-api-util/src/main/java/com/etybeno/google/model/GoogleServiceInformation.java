package com.etybeno.google.model;

import com.google.api.client.auth.oauth2.Credential;

/**
 * Created by thangpham on 24/04/2018.
 */
public class GoogleServiceInformation {

    protected Credential credential;

    public final Credential getCredential() {
        return credential;
    }

    public final void setCredential(Credential credential) {
        this.credential = credential;
    }
}
