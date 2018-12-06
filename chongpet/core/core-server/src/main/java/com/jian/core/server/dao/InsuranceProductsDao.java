package com.jian.core.server.dao;

import com.jian.core.model.bean.InsuranceProducts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InsuranceProductsDao {
    final static String RESULT="sid,products_name,products_url,isstatus";

    @Select("select "+RESULT+" from t_insurance_products")
    List<InsuranceProducts> getProductsAll();

    @Select("select "+RESULT+" from t_insurance_products where sid=#{sid,jdbcType=INTEGER}")
    InsuranceProducts getProducts(@Param("sid")int sid);
}
