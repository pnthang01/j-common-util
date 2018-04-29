package com.google.api.client.util.store;

import com.etybeno.redis.config.RedisInfo;
import com.etybeno.redis.service.RedisCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.ShardedJedisPool;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by thangpham on 23/04/2018.
 */
public class RedisDataStoreFactory extends AbstractDataStoreFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDataStoreFactory.class);

    private final RedisInfo redisInfo;

    public RedisDataStoreFactory(RedisInfo redisInfo) {
        if(null == redisInfo) throw new NullPointerException("Redis instance is null");
        this.redisInfo = redisInfo;
    }

    @Override
    protected <V extends Serializable> DataStore<V> createDataStore(String id) throws IOException {
        return null;
    }

    static class RedisDataStore<V extends Serializable> extends AbstractMemoryDataStore<V> {

        /**
         * @param dataStoreFactory data store factory
         * @param id               data store ID
         */
        RedisDataStore(RedisDataStoreFactory dataStoreFactory, String id) {
            super(dataStoreFactory, id);
        }

        @Override
        void save() throws IOException {
            ShardedJedisPool jedisPool = this.getDataStoreFactory().redisInfo.getShardedJedisPool();
            new RedisCommand<Boolean>(jedisPool) {
                @Override
                protected Boolean build() throws Exception {
                    return null;
                }
            };
        }

        @Override
        public RedisDataStoreFactory getDataStoreFactory() {
            return (RedisDataStoreFactory) super.getDataStoreFactory();
        }
    }
}