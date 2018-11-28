package com.jian.core.server.redisDao.mapper;


import com.jian.core.model.bean.Banner;
import com.jian.core.server.redisDao.HomeBannerRedisDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HomeBannerDaoMapper implements HomeBannerRedisDao {

    @Autowired
    private HomeBannerRedisDao homeBannerRedisDao;

    @Override
    public void RedisSaveBanner(List<Banner> banners) {

    }
}
