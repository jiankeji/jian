package com.jian.core.server.service.impl;

import com.jian.core.model.bean.Banner;
import com.jian.core.model.bo.HomeBannerBo;
import com.jian.core.model.util.ImgUtil;
import com.jian.core.server.dao.BannerDao;
import com.jian.core.server.redisDao.HomeBannerRedisDao;
import com.jian.core.server.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.jian.core.model.bean.inter.ImgUrls.HOME_BANNER_PATH;
import static com.jian.core.model.bean.inter.ImgUrls.INSURANCE_BANNER_PATH;

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

    @Override
    public void setBanner(int type) {
        List<Banner> list = bannerDao.getBannerAll(type);
        List<Banner> bannerList = new ArrayList<>();
        for (Banner banner:list){
            if (type == 1) banner.setImgUrl(ImgUtil.getImgPath(banner.getImgUrl(),HOME_BANNER_PATH));
            if (type == 2) banner.setImgUrl(ImgUtil.getImgPath(banner.getImgUrl(),INSURANCE_BANNER_PATH));
            bannerList.add(banner);
        }
        homeBannerRedisDao.RedisSaveBanner(bannerList);
    }
}
