package com.jian.core.server.service;

import com.jian.core.model.bean.PetNews;
import com.jian.core.model.bo.PetFrontPageBo;
import com.jian.core.model.bo.PetHomeFrontPageBo;

import java.text.ParseException;
import java.util.List;

public interface PetNewsService {

    List<PetHomeFrontPageBo> getHomeFrontPage(String dateTime);

    List<PetHomeFrontPageBo> getRedisPetNews();

    List<PetFrontPageBo> getFrontPageBo(int pageSize,int pageNum);

    public void saveRedisPetNew(String petNewsList) throws ParseException;

    List<PetNews> getAll();

}
