package com.jian.core.server.service.impl;

import com.jian.core.model.bean.PetLable;
import com.jian.core.model.bean.PetMsg;
import com.jian.core.model.bean.UserPet;
import com.jian.core.server.dao.PetMsgDao;
import com.jian.core.server.redisDao.PetMsgRedisDao;
import com.jian.core.server.service.PetMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("ALL")
@Component
public class PetMsgServiceImpl implements PetMsgService {
    @Autowired
    private PetMsgRedisDao petMsgRedisDao;

    @Autowired
    private PetMsgDao petMsgDao;

    @Override
    public int savePetMsg(PetMsg petMsg, int userId) {
        int num = petMsgDao.setPet(petMsg);
        if (num>0){
            UserPet userPet = new UserPet();
            userPet.setUserId(userId);
            userPet.setPetId(petMsg.getSid());
           int num2= petMsgDao.setUserPet(userPet);
            if (num2>0){
                petMsgRedisDao.setRedisPetMsg(petMsg,userId);
            }
        }
        return num;
    }

    @Override
    public PetMsg getPetMsg(int userId, int petId) {
        PetMsg petMsg = petMsgRedisDao.getRedisPetMsg(userId,petId);
        return petMsg;
    }

    @Override
    public int updatePetMsg(PetMsg petMsg,int userId) {
       int num = petMsgDao.updatePet(petMsg);
       if (num>0){
           petMsgRedisDao.setRedisPetMsg(petMsg,userId);
       }
        return num;
    }

    @Override
    public void setReidsPetLable(String lable, int petId) {
        petMsgRedisDao.setReidsPetLable(lable,petId);
    }

    @Override
    public List<PetLable> getRedisPetLable(int petId) {
        return petMsgRedisDao.getRedisPetLable(petId);
    }
}
