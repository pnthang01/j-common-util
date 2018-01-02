package com.etybeno.common.dao;

import com.etybeno.common.util.HttpClientUtil;
import com.etybeno.common.util.StringUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by thangpham on 15/12/2017.
 */
public class RestDataAccess {

    private static RestDataAccess _instance;

    public static synchronized RestDataAccess _load() {
        if (null == _instance) _instance = new RestDataAccess();
        return _instance;
    }

    private HttpClientUtil httpClientUtil = HttpClientUtil._load();

    public String retrieveDataFromGetURI(String uri, Map<String, Object> params) throws Exception {
        String finalUri = uri + "?" + params.keySet().stream().map(s -> s + "=" + params.get(s))
                .collect(Collectors.joining("&"));
        return httpClientUtil.executeGet(finalUri);
    }

    public <T> T retrieveDataFromGetURI(String uri, Map<String, Object> params, Class<T> clazz) throws Exception {
        return StringUtil.OBJECT_MAPPER.readValue(retrieveDataFromGetURI(uri, params), clazz);
    }

    public <T> T retrieveDataFromGetURI(String uri, Map<String, Object> params, TypeReference typeOf) throws Exception {
        return StringUtil.OBJECT_MAPPER.readValue(retrieveDataFromGetURI(uri, params), typeOf);
    }

    public String retrieveDataFromPostURI(String uri, Map<String, Object> params) throws Exception {
        String requestBody = StringUtil.GSON.toJson(params);
        return httpClientUtil.executePost(uri, requestBody);
    }

    public <T> T retrieveDataFromPostURI(String uri, Map<String, Object> params, Class<T> clazz) throws Exception {
        return StringUtil.OBJECT_MAPPER.readValue(retrieveDataFromPostURI(uri, params), clazz);
    }

    public <T> T retrieveDataFromPostURI(String uri, Map<String, Object> params, TypeReference typeOf) throws Exception {
        return StringUtil.OBJECT_MAPPER.readValue(retrieveDataFromPostURI(uri, params), typeOf);
    }

}
