package com.etybeno.netty.util;

import com.etybeno.common.util.StringPool;
import com.etybeno.common.util.StringUtil;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
/**
 * Created by thangpham on 16/04/2018.
 */
public class NettyHttpUtil {

    public static final String TAG = NettyHttpUtil.class.getSimpleName();

    public static final String FAVICON_URI = "/favicon.ico";
    public static final String HEADER_REFERER_NAME = "Referer";
    public static final String HEADER_REFRESH_NAME = "Refresh";
    public static final String HEADER_LOCATION_NAME = "Location";
    public static final String HEADER_CONNECTION_CLOSE = "Close";

    //redirect to url using HTML+JavaScript to preserve the referer, solution at https://coderwall.com/p/7a09ja
    static final String HTML_FOR_REDIRECT;

    static {
        StringBuilder s = new StringBuilder();
        s.append("<!DOCTYPE html><html><head><title></title></head><body>");
        s.append("<script type='text/javascript' >window.location=\"$url\";</script>");
        //tracking when javascript can not redirect to targeted url
        //s.append("<noscript><img src='http://t.d.eclick.vn:8080/ar?redirect=$autourl' /></noscript>");
        s.append("</body></html>");
        HTML_FOR_REDIRECT = s.toString();
    }

    public static FullHttpResponse redirectPath(String uri)
            throws UnsupportedEncodingException {
        int i = uri.indexOf("/http");
        if (i > 0) {
            String url = uri.substring(i + 1);
            return redirect(URLDecoder.decode(url, StringPool.UTF_8));
        }
        return null;
    }

    public static FullHttpResponse redirect(String url) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HEADER_LOCATION_NAME, url);
        response.headers().set(CONNECTION, HEADER_CONNECTION_CLOSE);
        return response;
    }

    /**
     * redirect url and preserve the referer in header
     *
     * @param url
     * @return FullHttpResponse
     */
    public static FullHttpResponse redirectWithReferer(String url) {
        String html = HTML_FOR_REDIRECT.replace("$url", url);
        ByteBuf byteBuf = Unpooled.copiedBuffer(html.getBytes());
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        response.headers().set(HEADER_LOCATION_NAME, url);
        response.headers().set(CONTENT_TYPE, StringPool.MIME_TYPE_UTF8_HTML);
        response.headers().set(CONTENT_LENGTH, byteBuf.readableBytes());
        response.headers().set(CONNECTION, HEADER_CONNECTION_CLOSE);
        return response;
    }

    public static FullHttpResponse theHttpContent(String str) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(str.getBytes());
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        response.headers().set(CONTENT_TYPE, StringPool.MIME_TYPE_UTF8_TEXT);
        response.headers().set(CONTENT_LENGTH, byteBuf.readableBytes());
        response.headers().set(CONNECTION, HEADER_CONNECTION_CLOSE);
        return response;
    }

    public static FullHttpResponse theHttpStatus(HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status);
        response.headers().set(CONTENT_TYPE, StringPool.MIME_TYPE_UTF8_TEXT);
        response.headers().set(CONNECTION, HEADER_CONNECTION_CLOSE);
        return response;
    }

    public static String getParamValue(String name, Map<String, List<String>> params) {
        return getParamValue(name, params, StringPool.BLANK);
    }

    public static String getParamValue(String name, Map<String, List<String>> params, String defaultVal) {
        List<String> vals = params.get(name);
        if (vals != null) {
            if (vals.size() > 0) {
                return vals.get(0);
            }
        }
        return defaultVal;
    }

    public static String getRemoteIP(ChannelHandlerContext ctx) {
        try {
            SocketAddress remoteAddress = ctx.channel().remoteAddress();
            if (remoteAddress instanceof InetSocketAddress) {
                return ((InetSocketAddress) remoteAddress).getAddress().getHostAddress();
            }
            return remoteAddress.toString().split("/")[1].split(":")[0];
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }
    static final String unknown = "unknown";

    //http://r.va.gg/2011/07/handling-x-forwarded-for-in-java-and-tomcat.html

    public static String getRequestIP(ChannelHandlerContext ctx, HttpRequest request) {
        String ipAddress = request.headers().get("X-Forwarded-For");
        if (!StringUtil.isNullOrEmpty(ipAddress) && !unknown.equalsIgnoreCase(ipAddress)) {
            //LogUtil.dumpToFileIpLog(ipAddress);
            String[] toks = ipAddress.split(",");
            int len = toks.length;
            if (len > 1) {
                ipAddress = toks[len - 1];
            } else {
                return ipAddress;
            }
        } else {
            ipAddress = NettyHttpUtil.getRemoteIP(ctx);
        }
        return ipAddress;
    }

    public static String getRequestDomain(HttpRequest request) {
        String cookieDomain = "";
        try{
            String host = request.headers().get("Host");
            cookieDomain = "."+host.replaceAll(".*\\.(?=.*\\.)", "");
        }
        catch(Exception err){
            System.out.println("getRequestDomain uri: "+request.getUri()+", error: "+err.getMessage());
        }
        return cookieDomain;
    }

    public static boolean isBadLogRequest(String uri) {
        if (StringUtil.isEmpty(uri)) {
            return true;
        }
        int idx = uri.indexOf("?");
        if (idx < 0) {
            return true;
        }
        String queryDetails = uri.substring(idx + 1);
        if (StringUtil.isEmpty(queryDetails)) {
            return true;
        }
        return false;
    }

    public static String responseAsJsonp(String callbackFunc, Map<String, Object> data) {
        String jsonData = new Gson().toJson(data);
        return responseAsJsonp(callbackFunc, jsonData);
    }

    public static String responseAsJsonp(String callbackFunc, String jsonData) {
        if (StringUtil.isEmpty(callbackFunc)) {
            return jsonData;
        } else {
            StringBuilder jsonp = new StringBuilder(callbackFunc);
            jsonp.append("(").append(jsonData).append(")");
            return jsonp.toString();
        }
    }

    public static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }
        // Send the response and close the connection if necessary.
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
