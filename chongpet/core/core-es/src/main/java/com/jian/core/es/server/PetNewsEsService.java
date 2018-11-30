package com.jian.core.es.server;

import com.jian.core.model.bean.PetNews;

import java.util.List;

public interface PetNewsEsService {

    void saveNews(PetNews petNews);

    /**
     * TODO 根据文章类型和id获取相关信息
     * @param sid
     * @param classification
     * @return
     */
    PetNews selectOneNews(int sid,String classification);

    /**
     * TODO 通过ID 获取信息
     * @param sid
     * @return
     */
    PetNews getNews(int sid);
}
