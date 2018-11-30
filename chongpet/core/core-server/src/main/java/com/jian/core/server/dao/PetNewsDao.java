package com.jian.core.server.dao;

import com.jian.core.model.bean.PetNews;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface PetNewsDao {

    final static String RESULT="sid,create_time as createTime,newtype as newsType,newscontext as newsContext," +
            "newsub as newsSub,newpic as newsPic,title,newtypename as newsTypeName,classification,pageview as pageView";

    @Select("select "+RESULT + " FROM chong_news")
    List<PetNews> getPetNewsList();
}
