package com.jian.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.InsuranceProducts;
import com.jian.core.model.bo.InsuranceProductsBo;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.dao.InsuranceProductsDao;
import com.jian.core.server.service.InsuranceProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("ALL")
@Component
@Transactional
public class InsuranceProductsServiceImpl implements InsuranceProductsService {

    @Autowired
    private InsuranceProductsDao insuranceProductsDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<InsuranceProducts> getProductsAll() {
        return insuranceProductsDao.getProductsAll();
    }

    @Override
    public InsuranceProducts getProducts(int sid) {
        InsuranceProducts insuranceProducts = insuranceProductsDao.getProducts(sid);
        if (insuranceProducts != null){
            return insuranceProducts;
        }
        return null;
    }
}
