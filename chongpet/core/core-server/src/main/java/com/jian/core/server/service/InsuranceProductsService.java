package com.jian.core.server.service;

import com.jian.core.model.bean.InsuranceProducts;
import com.jian.core.model.bo.InsuranceProductsBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InsuranceProductsService {

    /**
     * 获取保险对应的产品服务条款
     * @return
     */
    List<InsuranceProducts> getProductsAll();

    /**
     * 获取单个产品条款
     * @param sid
     * @return
     */
    InsuranceProducts getProducts(int sid);
}
