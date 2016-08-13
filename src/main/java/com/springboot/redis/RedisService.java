package com.springboot.redis;

import com.springboot.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by ibong-gi on 2016. 8. 10..
 */

@Service("redisService")
public class RedisService{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JedisConnectionFactory jcf;

    public String getRedisHostName(){
        return jcf.getHostName();
    }

    public int getRedisJedisConnection(){
        return jcf.getDatabase();
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Object hget(String key, String field){
        return redisTemplate.opsForHash().get(key, field);
    }

    public Object bHget(String key, String field) {
        try {
            byte[] b = (byte[]) redisTemplate.opsForHash().get(key, field);
            if(b != null) {
                return ObjectUtil.toObject(b);
            }
        } catch (Exception e) {
            System.out.println("(bHget)can't find this key : " + key);
        }
        return null;
    }
}
