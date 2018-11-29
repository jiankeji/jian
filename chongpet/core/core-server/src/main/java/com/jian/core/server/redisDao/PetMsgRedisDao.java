package com.jian.core.server.redisDao;

import com.jian.core.model.bean.PetMsg;

public interface PetMsgRedisDao {

    void setRedisPetMsg(PetMsg petMsg,int userId);

    PetMsg getRedisPetMsg(int userId,int petId);
}
