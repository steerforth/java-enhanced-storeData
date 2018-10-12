package com.jpz.dcim.poseidon.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: redis客户端
 * @Date: 2018-10-12 14:51
 */
@Component
public class RedisClient <T>{

    @Autowired
    private JedisPool jedisPool;

    public void set(String key, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } finally {
            jedis.close();
        }
    }

    public String get(String key) throws Exception  {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }

    public void setobj(String key, T value) throws Exception {
        Jedis jedis = null;
        try {
            Set<T> set = new HashSet<T>();
            set.add(value);
            jedis = jedisPool.getResource();
            jedis.sadd(key, String.valueOf(set));
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public void hset(String key, String field, String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key,field,value);
        } finally {
            jedis.close();
        }
    }

    public void hmset(String key, Map<String,String> hash){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hmset(key,hash);
        } finally {
            jedis.close();
        }
    }

}
