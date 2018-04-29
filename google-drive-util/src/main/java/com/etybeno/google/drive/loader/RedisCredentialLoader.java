package com.etybeno.google.drive.loader;

import com.etybeno.google.drive.model.DriveServiceInformation;
import com.etybeno.google.loader.AbstractCredentialLoader;
import com.etybeno.redis.config.RedisInfo;
import com.etybeno.redis.service.RedisCommand;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Map;

/**
 * Created by thangpham on 24/04/2018.
 */
public class RedisCredentialLoader extends AbstractCredentialLoader<DriveServiceInformation> {

    private static final Logger LOGGER = LogManager.getLogger(RedisCredentialLoader.class.getName());

    private RedisInfo redisInfo;

    public RedisCredentialLoader(RedisInfo redisInfo) {
        if(null == redisInfo) throw new IllegalArgumentException("Connection to redis is null, cannot process");
        this.redisInfo = redisInfo;
    }

    @Override
    public DriveServiceInformation authorize(final String key) throws Exception {
        ShardedJedisPool jedisPool = redisInfo.getShardedJedisPool();
        DriveServiceInformation driveServiceInformation = new RedisCommand<DriveServiceInformation>(jedisPool) {
            @Override
            protected DriveServiceInformation build() throws Exception {
                Map<String, String> valueMap = jedis.hgetAll(key);
                GoogleCredential credential = new GoogleCredential().setAccessToken(valueMap.get("access_token"));
                DriveServiceInformation information = new DriveServiceInformation();
                information.setRootDirId(valueMap.get("root_dir"));
                information.setCredential(credential);
                return information;
            }
        }.execute();
        return driveServiceInformation;
    }
}