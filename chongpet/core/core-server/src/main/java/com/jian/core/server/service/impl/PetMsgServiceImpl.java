package com.jian.core.server.service.impl;

import com.jian.core.model.bean.PetMsg;
import com.jian.core.model.bean.UserPet;
import com.jian.core.server.dao.PetMsgDao;
import com.jian.core.server.redisDao.PetMsgRedisDao;
import com.jian.core.server.service.PetMsgService;
import org.springframework.beans.factory.annotation.Autowired;

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
            petMsgDao.setUserPet(userPet);
        }
        return num;
    }

    @Override
    public PetMsg getPetMsg(int userId, int petId) {
        return null;
    }
}
