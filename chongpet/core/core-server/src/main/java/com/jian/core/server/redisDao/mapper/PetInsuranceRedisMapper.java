package com.jian.core.server.redisDao.mapper;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.PetInsurance;
import com.jian.core.model.bo.PetInsuranceBo;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.redisDao.PetInsuranceRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.jian.core.model.bean.inter.Constant.REDIS_HOME_INSURANCE_KEY;
import static com.jian.core.model.bean.inter.Constant.REDIS_HOME_INSURANCE_ZSET_KEY;
@SuppressWarnings("All")
@Component
public class PetInsuranceRedisMapper implements PetInsuranceRedisDao{

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void saveHomeInsurance(List<PetInsurance> petInsurances) {
        Map<String, String> map  =redisUtil.getHashValues(REDIS_HOME_INSURANCE_KEY);
        //先判断这个map自动是否有值
        if (map != null && map.size() > 0){//没有值插入
            setRedis(petInsurances);
        }else{
            //有值 删除原有的值 然后在保存进入reids
            for(Object ins:map.keySet()){
                String id = (String)ins;
                redisUtil.hmDel(REDIS_HOME_INSURANCE_KEY,id);
                redisUtil.zRem(REDIS_HOME_INSURANCE_ZSET_KEY,id);
            }
            setRedis(petInsurances);
        }
    }

    @Override
    public List<PetInsuranceBo> getRedisInsurance(int pageSize, int pageNum) {
        List<PetInsuranceBo> petInsuranceBos = new ArrayList<>();
        Set<Object> set = new HashSet<>();
        set = redisUtil.zRange(REDIS_HOME_INSURANCE_ZSET_KEY, pageSize,pageNum);
        if (set.size() > 0) {
            for (Object ins : set) {
                PetInsuranceBo petInsuranceBo = JSON.parseObject(redisUtil.getHashValue(REDIS_HOME_INSURANCE_KEY, (String) ins), PetInsuranceBo.class);
                petInsuranceBos.add(petInsuranceBo);
            }
        }
        return petInsuranceBos;
    }

    private void setRedis(List<PetInsurance> petInsurances){
        for (PetInsurance petInsurance:petInsurances){
            System.out.println(JSON.toJSONString(petInsurance));
            PetInsuranceBo petInsuranceBo = JSON.parseObject(JSON.toJSONString(petInsurance),PetInsuranceBo.class);
            redisUtil.setHashValue(REDIS_HOME_INSURANCE_KEY,String.valueOf(petInsurance.getSid()),JSON.toJSONString(petInsuranceBo));
            redisUtil.zAdd(REDIS_HOME_INSURANCE_ZSET_KEY,String.valueOf(petInsurance.getSid()),petInsurance.getSortNum());
        }
    }
}
