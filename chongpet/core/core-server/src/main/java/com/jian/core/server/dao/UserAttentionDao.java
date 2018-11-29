package com.jian.core.server.dao;

import com.jian.core.model.bean.UserAttention;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户关注 取关dao
 * @author shen
 *
 */

public interface UserAttentionDao {

    UserAttention selectByPrimaryKey(Integer sid);

    List<UserAttention> selectByAttention(@Param("userId")Integer userId, @Param("followUserId")Integer followUserId);

    int getFollow(@Param("userId")Integer userId);

    int getFans(@Param("userId")Integer userId);

    int isFollow(@Param("userId")Integer userId, @Param("followUserId")Integer followUserId);

    int deleteByPrimaryKey(Integer sid);

    int insert(UserAttention record);

    int updateByPrimaryKey(UserAttention record);

}








