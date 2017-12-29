/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this e file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.query;

import com.etybeno.common.util.HttpClientUtil;
import com.etybeno.common.util.StringUtil;
import com.etybeno.druid.api.Response;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

/**
 * @param <T>
 * @author thangpham
 */
public abstract class Query<I extends Query, T extends Response> implements Serializable {

    private String dataSource;

    public final I dataSource(String dataSource) {
        this.dataSource = dataSource;
        return (I) this;
    }

    protected abstract Type getResponseType();

    public List<T> executeAndMultiGet(URL url) throws Exception {
        String json = StringUtil.GSON.toJson(this);
        System.out.println(url.toString());
        System.out.println(json);
        String queryResult = HttpClientUtil._load().executePost(url, json);
        System.out.println(queryResult);
        List<T> buckets = StringUtil.GSON.fromJson(queryResult, getResponseType());
        return buckets;
    }

    public List<T> executeAndMultiGet(String urlStr) throws Exception {
        return executeAndMultiGet(new URL(urlStr));
    }

    public T executeAndGet(String urlStr) throws Exception {
        return executeAndGet(new URL(urlStr));
    }

    public T executeAndGet(URL url) throws Exception {
        String json = StringUtil.GSON.toJson(this);
        System.out.println(json);
        String queryResult = HttpClientUtil._load().executePost(url, json);
        T bucket = StringUtil.GSON.fromJson(queryResult, getResponseType());
        return bucket;
    }

}
