package com.jian.core.server.redisDao;

import com.jian.core.model.bean.Banner;
import com.jian.core.model.bo.HomeBannerBo;

import java.util.List;

public interface HomeBannerRedisDao {

    void RedisSaveBanner(List<Banner> banners);

    List<HomeBannerBo> getRedisBanner();
}
