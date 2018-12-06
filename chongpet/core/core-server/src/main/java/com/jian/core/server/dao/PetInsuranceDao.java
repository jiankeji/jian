package com.jian.core.server.dao;

import com.jian.core.model.bean.PetInsurance;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PetInsuranceDao {
    final static String RESULT= "sid,imgurl as imgUrl,title,sub_context as subcontext,context,price,isstatus,sort_num as sortNum,guarantee_time as guaranteeTime," +
            "feature_img_url as featureImgUrl,information,products,serve,create_time as createTime,update_time as updateTime";

    @Select("select "+RESULT+" from t_petInsurance where isstatus=0 order by sort_num asc,sid desc")
    List<PetInsurance> getPetinsuranceAll();

    @Select("select "+RESULT+" FROM t_petInsurance where sid = #{sid,jdbcType = INTEGER}")
    PetInsurance getInsurance(@Param(value = "sid")int sid);
}
