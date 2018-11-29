package com.jian.core.server.service.impl;

import com.jian.core.model.bean.PetInsurance;
import com.jian.core.model.bo.PetInsuranceBo;
import com.jian.core.server.dao.PetInsuranceDao;
import com.jian.core.server.redisDao.PetInsuranceRedisDao;
import com.jian.core.server.service.PetInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("ALL")
@Component
@Transactional
public class PetInsuranceServiceImpl implements PetInsuranceService {
    @Autowired
    private PetInsuranceDao petInsuranceDao;

    @Autowired
    private PetInsuranceRedisDao petInsuranceRedisDao;

    @Override
    public List<PetInsurance> getPetinsuranceAll() {
        return petInsuranceDao.getPetinsuranceAll();
    }

    @Override
    public List<PetInsuranceBo> getHomeInsuranceAll(int pageSize,int pageNum) {
        return petInsuranceRedisDao.getRedisInsurance(pageSize,pageNum);
    }
}
