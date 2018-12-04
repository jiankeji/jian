package com.jian.core.server.dao;

import com.jian.core.model.bean.InsurancePlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InsurancePlanDao {
    final static String RESULT="sid,insurance_id as insuranceId,plan_name as planName,apply_people as applyPeople,aggregate_anmtmt as aggregateAnmtmt," +
            "insurance_detaile as insuranceDetaile,every_time_money as everyTimeMoney,every_time_loss as everyTimeLoss,every_time_hurt as everyTimeHurt," +
            "take_effect_type as takeEffectType,price,create_time as createTime,update_time as updateTime,isstatus";

    @Select("select "+RESULT+" FROM t_insurance_plan where insurance_id = #{insuranceId,jdbcType=INTEGER}")
    List<InsurancePlan> getPlanAll(@Param("insuranceId")int insuranceId);
}
