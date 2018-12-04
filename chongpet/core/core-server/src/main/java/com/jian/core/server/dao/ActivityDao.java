package com.jian.core.server.dao;

import com.jian.core.model.bean.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityDao {
    final static String RESULT="sid,img_url as imgUrl,context,isstatus,sort_num as sortNum,title," +
            "sub_title as subTitle,create_time as createTime,pageview as pageView,update_time as updateTime";

    final static String MODEL="img_url,context,sort_num,title,sub_title,create_time,pageview,update_time";

    @Select("select "+RESULT+" from t_pet_activity where isstatus = 0 order by sort_num asc")
    List<Activity> getActivityAll();

    @Insert("<script>insert into t_pet_activity ("+MODEL+") values(img_url=#{imgUrl,jdbcType=VARCHAR},context=#{context,jdbcType=VARCHAR}," +
            "sort_num=#{sortNum,jdbcType=INTEGER},title=#{title,jdbcType=VARCHAR},sub_title=#{subTitle,jdbcType=VARCHAR}," +
            "create_time=TIMESTAMP,pageview=#{pageView,jdbcType=INTEGER},update_time=TIMESTAMP)</script>")
    @Options(useGeneratedKeys = true,keyProperty = "sid")
    int setActivity(Activity activity);

    @Update("<script> update t_pet_activity set img_url=#{imgUrl,jdbcType=VARCHAR},context=#{context,jdbcType=VARCHAR},sort_num=#{sortNum,jdbcType=INTEGER}," +
            "title=#{title,jdbcType=VARCHAR},sub_title=#{subTitle,jdbcType=VARCHAR},pageview=#{pageView,jdbcType=INTEGER},update_time=TIMESTAMP where sid = #{sid,jdbcType=INTEGER}</script>")
    int updateActivity(Activity activity);
}
