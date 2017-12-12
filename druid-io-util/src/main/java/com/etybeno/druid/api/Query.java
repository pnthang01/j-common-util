/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this e file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import com.etybeno.common.util.MethodUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author thangpham
 * @param <T>
 */
public abstract class Query<T extends Response> implements Serializable {
    
    protected abstract Type getResponseType();
    
    public List<T> executeAndMultiGet(URL url) {
//        String json = OBJECT_MAPPER.toJson(this);
//        String queryResult = HttpClientUtil.executePostJsonSilent(url, json);
//        List<T> buckets = MethodUtil.fromJson(queryResult, getResponseType());
//        return buckets;
        return  null;
    }

    public List<T> executeAndMultiGet(String urlStr) throws MalformedURLException {
//        String json = MethodUtil.toJson(this);
//        String queryResult = HttpClientUtil.executePostJsonSilent(new URL(urlStr), json);
//        List<T> buckets = MethodUtil.fromJson(queryResult, getResponseType());
//        return buckets;
        return  null;
    }

    public T executeAndGet(String urlStr) throws MalformedURLException {
//        String json = MethodUtil.toJson(this);
//        String queryResult = HttpClientUtil.executePostJsonSilent(new URL(urlStr), json);
//        T bucket = MethodUtil.fromJson(queryResult, getResponseType());
//        return bucket;
        return  null;
    }

}
