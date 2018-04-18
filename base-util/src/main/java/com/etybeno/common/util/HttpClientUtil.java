package com.etybeno.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by thangpham on 14/12/2017.
 */
public class HttpClientUtil {

    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final String GZIP = "gzip";
    public static final String APPLICATION_JSON = "application/json";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:9.0) Gecko/20100101 Firefox/9.0";
    public static final String MOBILE_USER_AGENT = "Mozilla/5.0 (Linux; U; Android 2.2; en-us; DROID2 GLOBAL Build/S273) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

    private static HttpClientUtil _instance = null;

    private ConcurrentMap<Integer, CloseableHttpClient> httpClientPool;
    private int MAX_SIZE = 10;
    private int DEFAULT_TIMEOUT = 300 * 1000;//15 seconds

    public synchronized static HttpClientUtil _initialize(int size, int timeout) {
        if(null == _instance) _instance = new HttpClientUtil(size, timeout);
        return _instance;
    }

    public synchronized static HttpClientUtil _load() {
        if (null == _instance) _instance = new HttpClientUtil(10, 300);
        return _instance;
    }

    public HttpClientUtil(int size, int timeout) {
        MAX_SIZE = size;
        DEFAULT_TIMEOUT = timeout * 1000;
        httpClientPool = new ConcurrentHashMap<>(MAX_SIZE);
    }

    public final CloseableHttpClient getThreadSafeClient() throws Exception {
        int slot = (int) (Math.random() * (MAX_SIZE + 1));
        return getThreadSafeClient(slot);
    }

    public final CloseableHttpClient getThreadSafeClient(int slot) throws Exception {
        CloseableHttpClient httpClient = httpClientPool.get(slot);
        if (null == httpClient) {
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(DEFAULT_TIMEOUT)
                    .setConnectTimeout(DEFAULT_TIMEOUT)
                    .setSocketTimeout(DEFAULT_TIMEOUT).build();
            cm.setMaxTotal(20);
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(cm)
                    .build();
            httpClientPool.put(slot, httpClient);
        }
        return httpClient;
    }

    public String executePost(URL url, String bodyContent) throws Exception {
        return executeRequest(url, HttpPost.METHOD_NAME, bodyContent, USER_AGENT, UTF8, APPLICATION_JSON, GZIP);
    }

    public String executePost(String url, String bodyContent) throws Exception {
        return executeRequest(new URL(url), HttpPost.METHOD_NAME, bodyContent, USER_AGENT, UTF8, APPLICATION_JSON, GZIP);
    }

    public String executePost(URL url, String bodyContent, String userAgent, String acceptCharset, String accept) throws Exception {
        return executeRequest(url, HttpPost.METHOD_NAME, bodyContent, userAgent, Charset.forName(acceptCharset), accept, GZIP);
    }

    public String executePost(String url, String bodyContent, String userAgent, String acceptCharset, String accept) throws Exception {
        return executeRequest(new URL(url), HttpPost.METHOD_NAME, bodyContent, userAgent, Charset.forName(acceptCharset), accept, GZIP);
    }

    public String executeGet(URL url) throws Exception {
        return executeRequest(url, HttpGet.METHOD_NAME, USER_AGENT, USER_AGENT, UTF8, APPLICATION_JSON, GZIP);
    }

    public String executeGet(String url) throws Exception {
        return executeRequest(new URL(url), HttpGet.METHOD_NAME, null, USER_AGENT, UTF8, null, GZIP);
    }

    public String executeDelete(URL url) throws Exception {
        return executeRequest(url, HttpDelete.METHOD_NAME, null, USER_AGENT, UTF8, APPLICATION_JSON, GZIP);
    }

    public String executeDelete(String url) throws Exception {
        return executeRequest(new URL(url), HttpDelete.METHOD_NAME, null,USER_AGENT, UTF8, APPLICATION_JSON, GZIP);
    }

    private String executeRequest(URL url, String method, String bodyContent, String userAgent, Charset acceptCharset,
                                  String contentType, String contentEncoding) throws Exception {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            HttpRequestBase httpRequest;
            if (HttpGet.METHOD_NAME.equals(method)) {
                httpRequest = new HttpGet(url.toURI());
            } else if (HttpPost.METHOD_NAME.equals(method)) {
                HttpPost tmp = new HttpPost(url.toURI());
                if (!StringUtil.isNullOrEmpty(bodyContent)) tmp.setEntity(new StringEntity(bodyContent));
                httpRequest = tmp;
            } else if (HttpPut.METHOD_NAME.equals(method)) {
                httpRequest = new HttpDelete(url.toURI());
            } else {
                throw new IllegalArgumentException("Http Method does not satisfy");
            }
            if (!StringUtil.isNullOrEmpty(userAgent)) httpRequest.setHeader("User-Agent", userAgent);
            if (!StringUtil.isNullOrEmpty(acceptCharset)) httpRequest.setHeader("Accept-Charset", acceptCharset.name());
            if (!StringUtil.isNullOrEmpty(contentType)) httpRequest.setHeader("Content-Type", contentType);
            if (!StringUtil.isNullOrEmpty(contentEncoding)) httpRequest.setHeader("Content-Encoding", contentEncoding);
            httpClient = getThreadSafeClient();
            response = httpClient.execute(httpRequest);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200 || code == 202) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String html = EntityUtils.toString(entity, UTF8);
                    EntityUtils.consume(entity);
                    return html;
                }
            } else if (code == 404) {
                return "404";
            } else {
                return "500";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (response != null) response.close();
            } catch (Exception err) {
            }
        }
        return "";
    }
}
