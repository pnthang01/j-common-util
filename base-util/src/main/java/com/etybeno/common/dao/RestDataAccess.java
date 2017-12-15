package com.etybeno.common.dao;

import com.etybeno.common.config.RestDataConfiguration;
import com.etybeno.common.util.HttpClientUtil;
import com.etybeno.common.util.StringUtil;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by thangpham on 15/12/2017.
 */
public class RestDataAccess {

    public static RestDataAccess _instance;

    public synchronized RestDataAccess _load() {
        if (null == _instance) _instance = new RestDataAccess();
        return _instance;
    }

    private HttpClientUtil httpClientUtil = HttpClientUtil._load();

    public <T> T retrieveDataFromGetURI(String uriKey, Map<String, Object> params, Class<T> clazz) throws Exception {
        String uri = RestDataConfiguration._load().getValue(uriKey);
        uri = uri + "?" + params.keySet().stream().map(s -> s + "=" + params.get(s)).collect(Collectors.joining("&"));
        String data = httpClientUtil.executeGet(uri);
        return StringUtil.GSON.fromJson(data, clazz);
    }

    public <T> T retrieveDataFromPostURI(String uriKey, Map<String, Object> params, Class<T> clazz) throws Exception {
        String uri = RestDataConfiguration._load().getValue(uriKey);
        String requestBody = StringUtil.GSON.toJson(params);
        String data = httpClientUtil.executePost(uri, requestBody);
        return StringUtil.GSON.fromJson(data, clazz);
    }

}
