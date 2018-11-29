package com.jian.core.server.service;

import com.jian.core.model.bean.PetMsg;

public interface PetMsgService {

    int savePetMsg(PetMsg petMsg,int userId);

    PetMsg getPetMsg(int userId,int petId);
}
