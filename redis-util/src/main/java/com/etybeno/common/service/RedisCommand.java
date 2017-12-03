package com.etybeno.common.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by thangpham on 20/11/2017.
 */
public abstract class RedisCommand<T> {

    protected ShardedJedisPool jedisPool;
    protected ShardedJedis shardedJedis = null;
    protected Jedis jedis = null;

    public RedisCommand(ShardedJedisPool jedisPool) {
        if (jedisPool == null) throw new IllegalArgumentException("JedisPool is NULL!");
        this.jedisPool = jedisPool;
    }

    public T execute() {
        boolean commited = false;
        T rs = null;
        try {
            shardedJedis = jedisPool.getResource();
            if (shardedJedis != null) {
                jedis = shardedJedis.getShard("");
                rs = build();
                commited = true;
            }
        } catch (Exception e) {
            StringWriter error = new StringWriter();
            e.printStackTrace(new PrintWriter(error));
            String debugInfo = "";
            if (jedis != null) {
                debugInfo = jedis.getClient().getHost() + ":" + jedis.getClient().getPort();
            }
        } finally {
            freeRedisResource(jedisPool, shardedJedis, commited);
        }
        return rs;
    }

    public void freeRedisResource(ShardedJedisPool jedisPool, ShardedJedis shardedJedis, boolean isCommited) {
        if (shardedJedis != null && jedisPool != null) {
            if (isCommited) {
                jedisPool.returnResource(shardedJedis);
            } else {
                jedisPool.returnBrokenResource(shardedJedis);
            }
        }
    }

    //define the logic at implementer
    protected abstract T build() throws JedisException;
}
