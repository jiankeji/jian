package com.jian.core.server.dao;

import com.jian.core.model.bean.InsurancePlan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InsurancePlanDao {
    final static String RESULT="sid,insurance_id as insuranceId,plan_name as planName,apply_people as applyPeople," +
            "insurance_detaile as insuranceDetaile," +
            "price,create_time as createTime,update_time as updateTime,isstatus";

    final static String MODEL="insurance_id,plan_name,apply_people," +
            "price,create_time,isstatus";

    @Select("select "+RESULT+" FROM t_insurance_plan where insurance_id = #{insuranceId,jdbcType=INTEGER}")
    List<InsurancePlan> getPlanAll(@Param("insuranceId")int insuranceId);

    @Insert("insert into t_insurance_plan ("+MODEL+") values()")
    int setInsurancePlan(InsurancePlan insurancePlan);
}
