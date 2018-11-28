package com.jian.core.server.dao;

import com.jian.core.model.bean.PetInsurance;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PetInsuranceDao {
    final static String RESULT= "sid,imgurl as imgUrl,title,sub_context as subcontext,context,price,isstatus,sort_num as sortNum";

    @Select("select "+RESULT+" from t_petInsurance where isstatus=0 order by sort_num asc,sid desc")
    List<PetInsurance> getPetinsuranceAll();
}
