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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
        String queryResult = HttpClientUtil._load().executePost(url, json);
        List<T> buckets = StringUtil.GSON.fromJson(queryResult, getResponseType());
        return buckets;
    }

    public List<T> executeAndMultiGet(String urlStr) throws Exception {
        String json = StringUtil.GSON.toJson(this);
        String queryResult = HttpClientUtil._load().executePost(urlStr, json);
        List<T> buckets = StringUtil.GSON.fromJson(queryResult, getResponseType());
        return buckets;
    }

    public T executeAndGet(String urlStr) throws Exception {
        String json = StringUtil.GSON.toJson(this);
        String queryResult = HttpClientUtil._load().executePost(urlStr, json);
        T bucket = StringUtil.GSON.fromJson(queryResult, getResponseType());
        return bucket;
    }

}
