package com.jian.core.server.service;

import com.jian.core.model.bean.Banner;

import java.util.List;

public interface BannerService {

    /**
     * TODO 获取banner集合
     * @param type
     * @return
     */
    List<Banner> getBannerAll(int type);
}
