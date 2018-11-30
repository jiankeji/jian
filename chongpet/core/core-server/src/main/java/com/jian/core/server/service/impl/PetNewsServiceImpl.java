package com.jian.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.jian.core.es.esdao.PetNewsEsDao;
import com.jian.core.model.bean.PetNews;
import com.jian.core.model.bo.PetFrontPageBo;
import com.jian.core.model.bo.PetHomeFrontPageBo;
import com.jian.core.server.dao.PetNewsDao;
import com.jian.core.server.redisDao.PetNewsRedisDao;
import com.jian.core.server.service.PetNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.jian.core.model.bean.inter.Constant.REDIS_PET_NEWS_MAP_KEY;
@SuppressWarnings("ALL")
@Component
public class PetNewsServiceImpl implements PetNewsService {

    @Autowired
    private PetNewsRedisDao petNewsRedisDao;

    @Autowired
    private PetNewsEsDao petNewsEsDao;

    @Autowired
    private PetNewsDao petNewsDao;

    @Override
    public List<PetHomeFrontPageBo> getHomeFrontPage(String dateTime) {
        List<PetNews> petNewsList = petNewsRedisDao.getPetNewsList(dateTime);
        List<PetHomeFrontPageBo> petHomeFrontPageBos = new ArrayList<>();
        if (petNewsList!=null && petNewsList.size()>0){
            for (PetNews pet:petNewsList){
                PetHomeFrontPageBo petHomeFrontPageBo = JSON.parseObject(JSON.toJSONString(pet),PetHomeFrontPageBo.class);
                petHomeFrontPageBos.add(petHomeFrontPageBo);

            }
        }
        return petHomeFrontPageBos;
    }

    @Override
    public List<PetHomeFrontPageBo> getRedisPetNews() {
        List<PetHomeFrontPageBo> homeFrontPageBos = new ArrayList<>();
        List<PetNews> list = petNewsRedisDao.getRedisPetNews();
        if (list!= null && list.size()>0){
            for (PetNews petNews :list){
                PetHomeFrontPageBo homeFrontPageBo = JSON.parseObject(JSON.toJSONString(petNews),PetHomeFrontPageBo.class);
                homeFrontPageBos.add(homeFrontPageBo);
            }
        }
        return homeFrontPageBos;
    }

    @Override
    public List<PetFrontPageBo> getFrontPageBo(int pageSize, int pageNum) {
        Set<Object> set = petNewsRedisDao.getPetNews(pageSize,pageNum);
        List<PetFrontPageBo> frontPageBoList = new ArrayList<>();
        for (Object obj:set){
            PetFrontPageBo bo = new PetFrontPageBo();
            String datetime = (String)obj;
            List<PetNews> petNewsList =petNewsRedisDao.getPetNewsList(datetime);
            List<PetHomeFrontPageBo> homeFrontPageBoList = new ArrayList<>();
            for (PetNews petNews :petNewsList){
                PetHomeFrontPageBo pageBo = JSON.parseObject(JSON.toJSONString(petNews),PetHomeFrontPageBo.class);
                homeFrontPageBoList.add(pageBo);
            }
            bo.setDateTime(datetime);
            bo.setPetFrontPageBoList(homeFrontPageBoList);
            frontPageBoList.add(bo);
        }
        return frontPageBoList;
    }

    @Override
    public void saveRedisPetNew(String petNewsList) throws ParseException {
        petNewsRedisDao.saveRedisPetNew(petNewsList);
    }

    @Override
    public List<PetNews> getAll() {
        return petNewsDao.getPetNewsList();
    }
}
