package com.jian.core.server.dao;

import com.jian.core.model.bean.Guarantee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GuaranteeDao {
    final static String RESULT="sid,cn_name as cnName,en_name as enName";

    @Select("select "+RESULT+" from ")
    List<Guarantee> getAll();
}
