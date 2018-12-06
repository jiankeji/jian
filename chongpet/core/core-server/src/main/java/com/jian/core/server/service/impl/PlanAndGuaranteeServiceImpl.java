package com.jian.core.server.service.impl;

import com.jian.core.model.bo.PlanAndGuaranteeBo;
import com.jian.core.server.dao.PlanAndGuaranteeDao;
import com.jian.core.server.service.PlanAndGuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PlanAndGuaranteeServiceImpl implements PlanAndGuaranteeService{

    @Autowired
    private PlanAndGuaranteeDao planAndGuaranteeDao;

    @Override
    public List<PlanAndGuaranteeBo> getBoAll(int planId) {
        return planAndGuaranteeDao.getBoAll(planId);
    }
}
