package com.jian.core.server.redisDao.mapper;


import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.Banner;
import com.jian.core.model.bo.HomeBannerBo;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.redisDao.HomeBannerRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.jian.core.model.bean.inter.Constant.REDIS_HOME_BANNER_ZSET_KEY;
import static com.jian.core.model.bean.inter.Constant.REIDS_HOME_BANNER_KEY;

@SuppressWarnings("ALL")
@Component
public class HomeBannerDaoMapper implements HomeBannerRedisDao {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void RedisSaveBanner(List<Banner> banners) {
        Map<String, String> map  =redisUtil.getHashValues(REIDS_HOME_BANNER_KEY);
        //先判断这个map自动是否有值
        if (map != null && map.size() > 0){//没有值插入
            setRedis(banners);
        }else{
            for(Object ins:map.keySet()){
                String id = (String)ins;
                redisUtil.hmDel(REIDS_HOME_BANNER_KEY,id);
                redisUtil.zRem(REDIS_HOME_BANNER_ZSET_KEY,id);
            }
            setRedis(banners);
        }
    }

    @Override
    public List<HomeBannerBo> getRedisBanner() {
        List<HomeBannerBo> homeBannerBos = new ArrayList<>();
        Set<Object> set = redisUtil.zRange(REDIS_HOME_BANNER_ZSET_KEY,0,-1);
        for (Object ins:set){
            HomeBannerBo homeBannerBo = JSON.parseObject(redisUtil.getHashValue(REIDS_HOME_BANNER_KEY, (String) ins),HomeBannerBo.class);
            homeBannerBos.add(homeBannerBo);
        }
        return homeBannerBos;
    }

    private void setRedis(List<Banner> banners){
        for (Banner banner:banners){
            HomeBannerBo homeBannerBo = JSON.parseObject(JSON.toJSONString(banner),HomeBannerBo.class);
            redisUtil.setHashValue(REIDS_HOME_BANNER_KEY,String.valueOf(banner.getSid()),JSON.toJSONString(homeBannerBo));
            redisUtil.zAdd(REDIS_HOME_BANNER_ZSET_KEY,String.valueOf(banner.getSid()),banner.getSortNum());
        }
    }
}
