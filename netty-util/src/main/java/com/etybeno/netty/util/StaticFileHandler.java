package com.etybeno.netty.util;

import com.etybeno.common.util.StringPool;
import com.etybeno.netty.config.NettyServerConfiguration;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * Created by thangpham on 20/04/2018.
 */
public class StaticFileHandler {

    static final String HTTP_HEADER_CACHE = "must_revalidate, private, max-age=";
    static final String HEADER_CONNECTION_CLOSE = "Close";
    static final String KEEP_ALIVE = "Keep-Alive";

    static final byte[] BASE64GIF_BYTES = StringPool.BASE64_GIF_BLANK.getBytes();
    static byte[] FAVICON_BYTES;
    static String FAVICON_ETAG;
    static String httpHeaderCache = "";
    static String proxyHeaderCache = "public";

    static {
        try {
            String faviconUri = NettyServerConfiguration._load().getConfig().getString("server.http.favicon.uri","");
            File faviconFile = new File(faviconUri);
            if(faviconFile.exists()) {
                FAVICON_BYTES = FileUtils.readFileToByteArray(faviconFile);
                FAVICON_ETAG = DigestUtils.md5Hex(FAVICON_BYTES);
            }
            //
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static FullHttpResponse theFaviconImage() {
        if(FAVICON_BYTES == null || FAVICON_BYTES.length == 0) return null;
        ByteBuf byteBuf = Base64.decode(Unpooled.copiedBuffer(FAVICON_BYTES));
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);
        response.headers().set(CONTENT_TYPE, "image/ico");
        response.headers().set(CONTENT_LENGTH, byteBuf.readableBytes());
        response.headers().set(CONNECTION, HEADER_CONNECTION_CLOSE);
        //----------
        response.headers().set("Access-Control-Allow-Origin", "*");
        response.headers().set("Access-Control-Allow-Methods", "GET");
        response.headers().set("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Content-Length");
        response.headers().set("Access-Control-Allow-Credentials", "true");
        return response;
    }

    public static FullHttpResponse theBase64Image1pxGif() {
        ByteBuf byteBuf = Base64.decode(Unpooled.copiedBuffer(BASE64GIF_BYTES));
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);
        response.headers().set(CONTENT_TYPE, StringPool.MIME_TYPE_GIF);
        response.headers().set(CONTENT_LENGTH, byteBuf.readableBytes());
        response.headers().set(CONNECTION, HEADER_CONNECTION_CLOSE);
        //----------
        response.headers().set("Access-Control-Allow-Origin", "*");
        response.headers().set("Access-Control-Allow-Methods", "GET");
        response.headers().set("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Content-Length");
        response.headers().set("Access-Control-Allow-Credentials", "true");

        return response;
    }

    public static FullHttpResponse staticFileResponse(File jsFile, String type) throws IOException {
        if(null == jsFile || !jsFile.exists() || !jsFile.isFile()) return NettyHttpUtil.theHttpStatus(HttpResponseStatus.NOT_FOUND);
        byte[] bytes = FileUtils.readFileToByteArray(jsFile);
        return staticFileResponse(bytes, type);
    }

    public static FullHttpResponse staticFileResponse(String content, String type) {
        return staticFileResponse(content.getBytes(), type);
    }

    public static FullHttpResponse staticFileResponse(byte[] data, String type) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(data);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);
        response.headers().set(CONTENT_TYPE, type);
        response.headers().set(CONTENT_LENGTH, byteBuf.readableBytes());
        response.headers().set(CONNECTION, HEADER_CONNECTION_CLOSE);

        response.headers().set("Access-Control-Allow-Origin", "*");
        response.headers().set("Access-Control-Allow-Methods", "GET");
        response.headers().set("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Content-Length");
        response.headers().set("Access-Control-Allow-Credentials", "true");
        return response;
    }


}
