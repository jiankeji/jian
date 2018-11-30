package com.jian.core.server.redisDao;

import com.jian.core.model.bean.PetLable;
import com.jian.core.model.bean.PetMsg;

import java.util.List;

public interface PetMsgRedisDao {

    void setRedisPetMsg(PetMsg petMsg,int userId);

    PetMsg getRedisPetMsg(int userId,int petId);

    void setReidsPetLable(String lable,int petId);

    List<PetLable> getRedisPetLable(int petId);

}
