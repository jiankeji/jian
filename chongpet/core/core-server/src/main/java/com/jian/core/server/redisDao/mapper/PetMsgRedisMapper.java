package com.jian.core.server.redisDao.mapper;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.PetLable;
import com.jian.core.model.bean.PetMsg;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.redisDao.PetMsgRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.jian.core.model.bean.inter.Constant.REDIS_PET_LABLE_MSG_MAP_KEY;
import static com.jian.core.model.bean.inter.Constant.REDIS_PET_MSG_MAP_KEY;
import static com.jian.core.model.bean.inter.Constant.REDIS_PET_MSG_ZSET_KEY;

@Component
public class PetMsgRedisMapper implements PetMsgRedisDao{

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void setRedisPetMsg(PetMsg petMsg,int userId) {
        redisUtil.setHashValue(REDIS_PET_MSG_MAP_KEY+":"+userId,petMsg.getSid()+"", JSON.toJSONString(petMsg));
        redisUtil.zAdd(REDIS_PET_MSG_ZSET_KEY+":"+userId,petMsg.getSid()+"",petMsg.getSid());
    }

    @Override
    public PetMsg getRedisPetMsg(int userId, int petId) {
        PetMsg petMsg = JSON.parseObject(redisUtil.getHashValue(REDIS_PET_MSG_MAP_KEY+":"+userId,petId+""),PetMsg.class);
        return petMsg;
    }

    @Override
    public void setReidsPetLable(String lable, int petId) {
        redisUtil.setHashValue(REDIS_PET_LABLE_MSG_MAP_KEY,String.valueOf(petId),lable);
    }

    @Override
    public List<PetLable> getRedisPetLable(int petId) {
        List<PetLable> petLables = new ArrayList<>();
        String lable = redisUtil.getHashValue(REDIS_PET_LABLE_MSG_MAP_KEY,String.valueOf(petId));
        if (lable ==null || "".equals(lable)){
            PetLable petLable = new PetLable();
            petLable.setIfSelect(false);
            petLable.setIfSystem(true);
            petLable.setLabelName("测试");
            petLables.add(petLable);
        }else{
            petLables = JSON.parseArray(lable,PetLable.class);
        }
        return petLables;
    }
}
