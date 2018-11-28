package com.jian.core.redis.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("ALL")
@Component
public  class RedisUtil1 {
    private  static Log log = LogFactory.getLog ( RedisUtil1.class );

    @Resource(name = "redisTemplate1")
    public RedisTemplate redisTemplate;

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        return set(key,value,0L,null);
    }
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime , TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            if(expireTime>0)redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            log.error ( "redis单key写入错误",e );
        }
        return result;
    }
    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }
    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(String key) {
//        Object result = null;
//        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
//        result = operations.get(key);
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 哈希 添加
     * @param key
     * @param field
     * @param value
     */
    public boolean setHashValue(String key, String field, String value) {
        try{
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            hash.put(key, field, value);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 哈希获取数据
     * @param key
     * @param field
     * @return
     */
    public String getHashValue(String key, String field){
        Object obj = null;
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        obj =hash.get ( key,field );
        return (obj == null) ? "" : String.valueOf ( hash.get(key,field) );
    }

    /**
     *添加map
     * @param key
     * @param fieldMap
     */
    public boolean putHashMap(String key , Map<String, String> fieldMap){
        try {
            redisTemplate.opsForHash().putAll(key, fieldMap);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *添加map
     * @param key
     * @param fieldMap
     */
    public boolean putHashMap2(String key , Map<String, Object> fieldMap){
        try {
            redisTemplate.opsForHash().putAll(key, fieldMap);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Hash数据类型  查找 key绑定的hash集合中hashKey 的元素是否存在
     * @param key
     * @param field
     * @return
     */
    public Boolean hasHashKey(String key,String field){
        Boolean flag = false;
        HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
        flag = hash.hasKey(key,field);
        return flag;
    }

    public Map<String,String> getHashValues(String keys){
        Map<String,String> map = new HashMap<>();
        HashOperations<String,String,String> hash = redisTemplate.opsForHash();
        map = hash.entries(keys);
        return map;
    }

    /**
     *哈希数据删除
     * @param key
     * @param field
     */
    public void hmDel(String key, String field){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        hash.delete ( key,field );
    }
    /**
     * 集合列表添加
     * @param key
     * @param value
     */
    public void lPushList(String key, Object value){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(key, JSON.toJSONString ( value ));
    }

    /**
     * 集合列表获取
     * @param key
     * @param l  0
     * @param l1 -1
     * @return 0 -1表示全部
     */
    public String lRangeList(String key, long l, long l1){
        String obj =null;
        ListOperations<String, Object> list = redisTemplate.opsForList();
        obj = JSON.toJSONString ( list.range ( key,0,l1 ) ) ;
        return (obj == null)?"":JSON.toJSONString ( list.range ( key,0,l1 ));
    }

    /**
     * 删除集合
     * @param key
     * @param l
     * @param value
     */
    public  void delList(String key , long l, Object value){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.remove ( key,l,value);
    }

    /**
     * 修改list集合中的某个数据
     * @param key
     * @param value
     * @param l
     */
    public void updateList(String key,Object value,long l){
        ListOperations<String,Object> list = redisTemplate.opsForList();
        list.set(key,l,value);
    }

    /**
     * SET集合添加
     * @param key
     * @param value
     */
    public void sadd(String key, Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * SET集合删除
     * @param key
     * @param value
     */
    public void srem(String key, Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.remove(key,value);
    }

    /**
     * set集合获取
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param score
     */
    public void zAdd(String key, Object value, double score){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,score);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
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

    //删除
    public Long zRem(String key, Object value) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.remove(key, value);
    }

    //返回zset对应数据的索引值
    public Long zRank(String key, Object value){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rank(key,value);
    }
}
