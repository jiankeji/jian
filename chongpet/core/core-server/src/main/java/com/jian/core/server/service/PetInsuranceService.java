package com.jian.core.server.service;

import com.jian.core.model.bean.PetInsurance;
import com.jian.core.model.bo.InsuranceDetailBo;
import com.jian.core.model.bo.PetInsuranceBo;

import java.util.List;

public interface PetInsuranceService {

    /**
     *TODO
     * 获取保险信息
     * @return
     */
    List<PetInsurance> getPetinsuranceAll();

    /**
     * 获取首页保险信息
     * @return
     */
    List<PetInsuranceBo> getHomeInsuranceAll(int pageSize,int pageNum);

    void saveInsurance();

    PetInsurance getInsurance(int sid);

    InsuranceDetailBo getDetails(int sid);

    void setDetails(String date,int sid);
}
