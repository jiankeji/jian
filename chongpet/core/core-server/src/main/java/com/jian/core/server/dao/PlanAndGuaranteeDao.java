package com.jian.core.server.dao;

import com.jian.core.model.bo.PlanAndGuaranteeBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlanAndGuaranteeDao {
    final static String RESULT="p.context as context,g.cn_name as cnName,g.en_name as enName";

    @Select("select "+RESULT+" FROM t_plan_guarantee p JOIN t_guarantee g ON p.g_id = g.sid WHERE p.plan_id=#{planId,jdbcType=INTEGER}")
    List<PlanAndGuaranteeBo> getBoAll(@Param(value = "planId")int planId);
}
