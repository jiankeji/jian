package com.jian.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.InsurancePlan;
import com.jian.core.model.bo.InsurancePlanBo;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.dao.InsurancePlanDao;
import com.jian.core.server.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.jian.core.model.bean.inter.Constant.REDIS_INSURANCE_PLAN_MSG_KEY;
import static com.jian.core.model.bean.inter.Constant.REDIS_INSURANCE_PLAN_ZSET_KEY;

@Component
@Transactional
@SuppressWarnings("ALL")
public class InsurancePlanServiceImpl implements InsurancePlanService {

    @Autowired
    private InsurancePlanDao insurancePlanDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<InsurancePlan> getPlanAll(int insuranceId) {
        return insurancePlanDao.getPlanAll(insuranceId);
    }

    @Override
    public int setInsurancePlan(InsurancePlan insurancePlan) {
        int num = insurancePlanDao.setInsurancePlan(insurancePlan);
        if (num > 0){
            redisUtil.setHashValue(REDIS_INSURANCE_PLAN_MSG_KEY+":"+insurancePlan.getInsuranceId(),
                    insurancePlan.getSid()+"", JSON.toJSONString(insurancePlan));
            redisUtil.zAdd(REDIS_INSURANCE_PLAN_ZSET_KEY+":"+insurancePlan.getInsuranceId(),insurancePlan.getSid()+"",insurancePlan.getSid());
        }
        return num;
    }

    @Override
    public InsurancePlan getPlan(int sid, int insuranceId) {
        String plan = redisUtil.getHashValue(REDIS_INSURANCE_PLAN_MSG_KEY+":"+insuranceId,sid+"");
        if (plan != null && !"".equals(plan)){
            return JSON.parseObject(plan,InsurancePlan.class);
        }
        return null;
    }

    @Override
    public List<InsurancePlanBo> getRedisPlan(int insuranceId) {
        List<InsurancePlanBo> planBoList = new ArrayList<>();
        Set<Object> set =redisUtil.zRange(REDIS_INSURANCE_PLAN_ZSET_KEY+":"+insuranceId,0,-1);
        for (Object ins :set){
            String id = (String)ins;
            String plan = redisUtil.getHashValue(REDIS_INSURANCE_PLAN_MSG_KEY+":"+insuranceId,id);
            if (plan!=null && !"".equals(plan)){
                InsurancePlanBo bo = JSON.parseObject(plan,InsurancePlanBo.class);
                planBoList.add(bo);
            }
        }
        return planBoList;
    }
}
