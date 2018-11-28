package com.jian.core.server.service;

import com.jian.core.model.bean.PetInsurance;

import java.util.List;

public interface PetInsuranceService {

    /**
     *TODO
     * 获取首页保险信息
     * @return
     */
    List<PetInsurance> getPetinsuranceAll();
}
