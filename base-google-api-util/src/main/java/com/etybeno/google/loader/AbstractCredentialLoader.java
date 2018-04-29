package com.etybeno.google.loader;

import com.etybeno.google.model.GoogleServiceInformation;

/**
 * Created by thangpham on 24/04/2018.
 */
public abstract class AbstractCredentialLoader<T extends GoogleServiceInformation> {

    public abstract T authorize(String userId) throws Exception;

}
