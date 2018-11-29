package com.jian.core.redis.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//2号库专给关注用
@SuppressWarnings("ALL")
@Component
public  class RedisUtil2 {
    private static Log log = LogFactory.getLog(RedisUtil2.class);

    public static final String ATTENTION = "attention";//用户关注
    public static final String ATTENTION_FOLLOW = "follow";//用户关注表-关注列表
    public static final String ATTENTION_FANS = "fans";//用户关注表-粉丝列表

    @Resource(name = "redisTemplate2")
    public RedisTemplate redisTemplate;

    //判断缓存中是否有对应的value
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    //获取 RedisSerializer
    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    //写入缓存obj,并设置时效时间
    public void set(final String key, Object value, Long expireTime , TimeUnit timeUnit) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        if(expireTime>0)redisTemplate.expire(key, expireTime, timeUnit);
    }

    //读取缓存
    public Object get(String key) {
        return StringUtils.isEmpty(key)?"":redisTemplate.opsForValue().get(key);
    }

    //删除对应的value
    public void delete(final String key) {
        if (exists(key)) redisTemplate.delete(key);
    }

    //SET集合添加
    public void sAdd(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    //SET集合删除
    public void sRem(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.remove(key, value);
    }

    //set集合获取
    public Set<Object> sMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    //set是否是成员
    public boolean isMember(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.isMember(key, value);
    }

    //返回set内容数
    public Long sCard(final String key) {
        if (redisTemplate != null) {
            return (Long) redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keys = serializer.serialize(key);
                    return connection.sCard(keys);
                }
            });
        }
        return null;
    }

    //返回交集
    public Set<Object> sInter(String key, String key1) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.intersect(key, key1);
    }

    //有序集合添加
    public boolean zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.add(key, value, scoure);
    }

    //删除
    public Long zRem(String key, Object value) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.remove(key, value);
    }

    //正序
    public Set<Object> zRange(String key, long start, long end) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.range(key, start, end);
    }

    //倒序
    public Set<Object> reverseRange(String key, long start, long end) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.reverseRange(key, start, end);
    }

    //获取分数
    public Long zRank(String key, Object value) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rank(key, value);
    }

    //是否是成员
    public boolean zIsMember(String key, Object value) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        if (zset.rank(key, value) == null) {
            return false;
        } else {
            return true;
        }
    }

    //得到的有序集合成员的数量
    public Long zCard(final String key) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.zCard(key);
    }

    //得到分数区间的成员的数量
    public Long zCount(final String key,double d1,double d2) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.count(key,d1,d2);
    }

    //交集入库
    public Long zInter(String key1, String key2, String key3) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.intersectAndStore(key1, key2, key3);
    }

    //zscore
    public Double zScore(String key, Object value) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.score(key, value);
    }

    //业务函数-------------------------------------------------------------
    //添加关注
    public void payAttention(Integer userId, Integer followId, double timestamp) {
        zAdd(ATTENTION + ":" + (userId.toString()) + ":" + ATTENTION_FOLLOW, (followId.toString()), timestamp);
        zAdd(ATTENTION + ":" + (followId.toString()) + ":" + ATTENTION_FANS, (userId.toString()), timestamp);
    }

    //取消关注
    public void cancelAttention(Integer userId, Integer followId) {
        zRem(ATTENTION + ":" + (userId.toString()) + ":" + ATTENTION_FOLLOW, (followId.toString()));
        zRem(ATTENTION + ":" + (followId.toString()) + ":" + ATTENTION_FANS, (userId.toString()));
    }

    //关注人数
    public Long getFollow(Integer userId){
        return zCard(ATTENTION + ":" + (userId.toString()) + ":" + ATTENTION_FOLLOW);
    }

    //关注人数列表
    public Set<Object> getFollowRange(Integer userId,long start, long end){
        return zRange(ATTENTION + ":" + (userId.toString()) + ":" + ATTENTION_FOLLOW,start,end);
    }

    //粉丝人数
    public Long getFans(Integer userId){
        return zCard(ATTENTION + ":" + (userId.toString()) + ":" + ATTENTION_FANS);
    }

    //粉丝人数列表
    public Set<Object> getFansRange(Integer userId,long start, long end){
        return zRange(ATTENTION + ":" + (userId.toString()) + ":" + ATTENTION_FANS,start,end);
    }

    //是否关注此人
    public boolean isFollow(Integer userId, Integer followId){
        return zIsMember(ATTENTION + ":" + (userId.toString()) + ":" + ATTENTION_FOLLOW,(followId.toString()));
    }

}
