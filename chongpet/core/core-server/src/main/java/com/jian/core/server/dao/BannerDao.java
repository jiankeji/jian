package com.jian.core.server.dao;

import com.jian.core.model.bean.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BannerDao  {
    final static String RESULT="sid,imgurl as imgUrl,banner_context as bannerContext,isstatus,sort_num as sortNum,b_type as bType";

    /**
     * TODO 获取banner集合
     * @param type
     * @return
     */
    @Select("select "+RESULT+" from t_banner" +
            " where b_type = #{type,jdbcType = INTEGER} and isstatus = 0 order by sort_num asc,sid desc ")
    List<Banner> getBannerAll(@Param(value = "type") int type);
}
