package com.jian.core.server.service;

import com.jian.core.model.bean.PetLable;
import com.jian.core.model.bean.PetMsg;

import java.util.List;
import java.util.Set;

public interface PetMsgService {

    int savePetMsg(PetMsg petMsg,int userId);

    PetMsg getPetMsg(int userId,int petId);

    int updatePetMsg(PetMsg petMsg,int userId);

    public void setReidsPetLable(String lable, int petId);

    public List<PetLable> getRedisPetLable(int petId);

    Set<Object> petMsgZset(int userId);
}
