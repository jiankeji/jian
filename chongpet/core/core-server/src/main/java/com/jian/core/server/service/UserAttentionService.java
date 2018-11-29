package com.jian.core.server.service;


import com.jian.core.model.bean.UserAttention;
import com.jian.core.model.bo.UserAttentionBo;

import java.util.List;
import java.util.Set;

public interface UserAttentionService {
    UserAttention selectByPrimaryKey(Integer sid);

	int deleteByPrimaryKey(Integer sid);

    int insert(UserAttention record);

    int updateByPrimaryKey(UserAttention record);

    int payAttention(Integer userId, Integer followUserId);

    int getFollow(Integer userId);

    int getFans(Integer userId);

    int getRelation(Integer userId, Integer followUserId);

    List<UserAttentionBo> getAttentionList(Integer userId, Integer type, long start, long end);

    List<UserAttentionBo> getUserFansList(int LogigUserId, int fromUserId, long start, long end, int type);

    /**
     * 获取用户关注
     * @param userId
     * @return
     */
    Set<Object> getAttention(int userId);

    /**
     * 获取用户粉丝
     * @param userId
     * @return
     */
    Set<Object> getUserFans(int userId);
}
