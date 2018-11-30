package com.jian.core.server.redisDao;

import com.jian.core.model.bean.PetNews;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface PetNewsRedisDao {

    void saveRedisPetNew(String petNewsList) throws ParseException;

    List<List<PetNews>> getRedisPetNewsList();

    List<PetNews> getRedisPetNews();

    List<PetNews> getPetNewsList(String dateTime);

    Set<Object> getPetNews(int pageSize, int pageNum);
}
