package com.jian.core.server.redisDao.mapper;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.PetNews;
import com.jian.core.model.bo.PetFrontPageBo;
import com.jian.core.model.bo.PetHomeFrontPageBo;
import com.jian.core.model.util.DateTimeUtil;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.redisDao.PetNewsRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.jian.core.model.bean.inter.Constant.REDIS_PET_NEWS_MAP_KEY;
import static com.jian.core.model.bean.inter.Constant.REDIS_PET_NEWS_ZSET_KEY;
@Component
public class PetNewsRedisMapper implements PetNewsRedisDao{

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void saveRedisPetNew(String petNewsList) throws ParseException {
        long dateTime = DateTimeUtil.getFromatTime();
        redisUtil.setHashValue(REDIS_PET_NEWS_MAP_KEY,dateTime+"",petNewsList);
        redisUtil.zAdd(REDIS_PET_NEWS_ZSET_KEY,dateTime+"",dateTime);
    }

    @Override
    public List<List<PetNews>> getRedisPetNewsList() {
        return null;
    }

    @Override
    public List<PetNews> getRedisPetNews() {
        Set<Object> set = redisUtil.reverseRange(REDIS_PET_NEWS_ZSET_KEY,0,-1);
        String id ="";
        List<PetNews> list = new ArrayList<>();
        if (set.size()>0){
            id = String.valueOf(set.toArray()[0]);
            list = JSON.parseArray(redisUtil.getHashValue(REDIS_PET_NEWS_MAP_KEY,id),PetNews.class);
        }
        return list;
    }

    @Override
    public List<PetNews> getPetNewsList(String dateTime) {
        List<PetNews> list =JSON.parseArray( redisUtil.getHashValue(REDIS_PET_NEWS_MAP_KEY,dateTime),PetNews.class);
        return list;
    }

    @Override
    public Set<Object> getPetNews(int pageSize, int pageNum) {
        Set<Object> set = redisUtil.reverseRange(REDIS_PET_NEWS_ZSET_KEY,pageNum,pageSize);
        return set;
    }
}
