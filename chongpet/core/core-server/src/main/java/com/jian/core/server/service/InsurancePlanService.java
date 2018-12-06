package com.jian.core.server.service;

import com.jian.core.model.bean.InsurancePlan;
import com.jian.core.model.bo.InsurancePlanBo;

import java.util.List;

public interface InsurancePlanService {

    List<InsurancePlan> getPlanAll(int insuranceId);

    int setInsurancePlan(InsurancePlan insurancePlan);

    InsurancePlan getPlan(int sid,int insuranceId);

    List<InsurancePlanBo> getRedisPlan(int insuranceId);
}
