package com.jian.core.server.redisDao;

import com.jian.core.model.bean.Banner;

import java.util.List;

public interface HomeBannerRedisDao {

    void RedisSaveBanner(List<Banner> banners);
}
