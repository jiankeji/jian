package com.jian.core.server.service.impl;

import com.jian.core.model.bean.Banner;
import com.jian.core.model.bo.HomeBannerBo;
import com.jian.core.server.dao.BannerDao;
import com.jian.core.server.redisDao.HomeBannerRedisDao;
import com.jian.core.server.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("ALL")
@Component
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    @Autowired
    private HomeBannerRedisDao homeBannerRedisDao;

    @Override
    public List<Banner> getBannerAll(int type) {
        return bannerDao.getBannerAll(type);
    }

    @Override
    public List<HomeBannerBo> getHomePageBannerAll() {
        return homeBannerRedisDao.getRedisBanner();
    }
}
