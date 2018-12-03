package com.jian.core.server.redisDao.mapper;


import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.User;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.redisDao.UserRedisDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class UserRedisDaoMapper implements UserRedisDao {

    @Resource
    RedisUtil redisUtil;

    @Override
    public void saveReidsUser(User user, String token) {

        redisUtil.set("token:"+token,user.getUserId()+"",3l, TimeUnit.DAYS);
        redisUtil.setHashValue("usermap",user.getUserId()+"", JSON.toJSONString(user));

    }

    @Override
    public Integer getUserId(String token) {
        boolean flag = redisUtil.exists("token:" +token);
        int  userId=0;
        if (flag) {
            userId = Integer.parseInt((String) redisUtil.get("token:" + token));
        }
        return userId;
    }

    @Override
    public User gerUserRedis(Integer userId) {
        String data = redisUtil.getHashValue("usermap",userId+"");
        User user = JSON.parseObject(data,User.class);
        return user;
    }
}
