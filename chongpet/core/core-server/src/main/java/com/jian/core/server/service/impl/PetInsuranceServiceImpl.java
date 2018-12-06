package com.jian.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.PetInsurance;
import com.jian.core.model.bo.InsuranceDetailBo;
import com.jian.core.model.bo.PetInsuranceBo;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.dao.PetInsuranceDao;
import com.jian.core.server.redisDao.PetInsuranceRedisDao;
import com.jian.core.server.service.PetInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jian.core.model.bean.inter.Constant.REDIS_INSURANCE_DETAILS_KEY;

@SuppressWarnings("ALL")
@Component
@Transactional
public class PetInsuranceServiceImpl implements PetInsuranceService {
    @Autowired
    private PetInsuranceDao petInsuranceDao;

    @Autowired
    private PetInsuranceRedisDao petInsuranceRedisDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<PetInsurance> getPetinsuranceAll() {
        return petInsuranceDao.getPetinsuranceAll();
    }

    @Override
    public List<PetInsuranceBo> getHomeInsuranceAll(int pageSize,int pageNum) {
        return petInsuranceRedisDao.getRedisInsurance(pageSize,pageNum);
    }

    @Override
    public void saveInsurance() {
        List<PetInsurance> list = petInsuranceDao.getPetinsuranceAll();
        petInsuranceRedisDao.saveHomeInsurance(list);
    }

    @Override
    public PetInsurance getInsurance(int sid) {
        String pet = petInsuranceRedisDao.getRedisInsurance(sid);
        if (pet != null && !"".equals(pet)){
            return JSON.parseObject(pet,PetInsurance.class);
        }
        return null;
    }

    @Override
    public InsuranceDetailBo getDetails(int sid) {
        String date = redisUtil.getHashValue(REDIS_INSURANCE_DETAILS_KEY,sid+"");
        if (date != null && !"".equals(date)){
            return JSON.parseObject(date,InsuranceDetailBo.class);
        }
        return null;
    }

    @Override
    public void setDetails(String date,int sid) {
        redisUtil.setHashValue(REDIS_INSURANCE_DETAILS_KEY,sid+"",date);
    }
}
