package com.etybeno.google.drive.loader;

import com.etybeno.google.drive.GoogleDriveUtil;
import com.etybeno.google.drive.model.DriveServiceInformation;
import com.etybeno.google.loader.AbstractCredentialLoader;
import com.etybeno.google.util.SingletonUtil;
import com.etybeno.redis.config.RedisInfo;
import com.etybeno.redis.service.RedisCommand;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.ShardedJedisPool;

import java.io.IOException;
import java.util.Map;

/**
 * Created by thangpham on 24/04/2018.
 */
public class RedisCredentialLoader extends AbstractCredentialLoader<DriveServiceInformation> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCredentialLoader.class);

    private RedisInfo redisInfo;
    private GoogleClientSecrets googleClientSecrets;

    public RedisCredentialLoader(RedisInfo redisInfo, String secretFile) throws IOException {
        if(null == redisInfo) throw new IllegalArgumentException("Connection to redis is null, cannot process");
        this.redisInfo = redisInfo;
        googleClientSecrets = GoogleDriveUtil.loadGoogleClientSecrets(secretFile);
    }

    @Override
    public DriveServiceInformation authorize(final String key) throws Exception {
        ShardedJedisPool jedisPool = redisInfo.getShardedJedisPool();
        DriveServiceInformation driveServiceInformation = new RedisCommand<DriveServiceInformation>(jedisPool) {
            @Override
            protected DriveServiceInformation build() throws Exception {
                Map<String, String> valueMap = jedis.hgetAll(key);
                GoogleCredential credential = new GoogleCredential.Builder()
                        .setClientSecrets(googleClientSecrets)
                        .setJsonFactory(SingletonUtil.getJsonFactory())
                        .setTransport(SingletonUtil.getHttpTransport())
                        .build()
                        .setAccessToken(valueMap.get("access_token"))
                        .setRefreshToken(valueMap.get("refresh_token"));
                DriveServiceInformation information = new DriveServiceInformation();
                information.setRootDirId(valueMap.get("root_dir"));
                information.setCredential(credential);
                return information;
            }
        }.execute();
        return driveServiceInformation;
    }
}