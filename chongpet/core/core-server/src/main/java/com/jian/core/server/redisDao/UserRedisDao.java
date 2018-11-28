package com.jian.core.server.redisDao;

import com.jian.core.model.bean.User;

import java.util.Map;

public interface UserRedisDao {

    //保存用户信息
    void  saveReidsUser(User user, String token);

    //查出userId 根据token
    Integer getUserId(String token);

    //根据id查出用户信息
    User gerUserRedis(Integer userId);
}
