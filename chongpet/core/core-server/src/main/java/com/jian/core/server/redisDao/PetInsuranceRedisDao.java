package com.jian.core.server.redisDao;

import com.jian.core.model.bean.PetInsurance;
import com.jian.core.model.bo.PetInsuranceBo;

import java.util.List;

public interface PetInsuranceRedisDao {

    void saveHomeInsurance(List<PetInsurance> petInsurances);

    List<PetInsuranceBo> getRedisInsurance(int pageSize,int pageNum);

    String getRedisInsurance(int sid);

}
