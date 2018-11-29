package com.jian.core.server.redisDao.mapper;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.PetMsg;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.redisDao.PetMsgRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jian.core.model.bean.inter.Constant.REDIS_PET_MSG_MAP_KEY;

@Component
public class PetMsgRedisMapper implements PetMsgRedisDao{

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void setRedisPetMsg(PetMsg petMsg,int userId) {
        redisUtil.setHashValue(REDIS_PET_MSG_MAP_KEY+":"+userId,petMsg.getSid()+"", JSON.toJSONString(petMsg));
    }

    @Override
    public PetMsg getRedisPetMsg(int userId, int petId) {
        PetMsg petMsg = JSON.parseObject(redisUtil.getHashValue(REDIS_PET_MSG_MAP_KEY+":"+userId,petId+""),PetMsg.class);
        return petMsg;
    }
}
