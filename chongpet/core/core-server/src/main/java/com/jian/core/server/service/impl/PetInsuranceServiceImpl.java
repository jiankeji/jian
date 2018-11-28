package com.jian.core.server.service.impl;

import com.jian.core.model.bean.PetInsurance;
import com.jian.core.server.dao.PetInsuranceDao;
import com.jian.core.server.service.PetInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PetInsuranceServiceImpl implements PetInsuranceService {
    @Autowired
    private PetInsuranceDao petInsuranceDao;

    @Override
    public List<PetInsurance> getPetinsuranceAll() {
        return petInsuranceDao.getPetinsuranceAll();
    }
}
