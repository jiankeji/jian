package com.jian.core.server.redisDao.mapper;


import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.redisDao.UserRedisDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserRedisDaoImpl implements UserRedisDao {

    @Resource
    RedisUtil redisUtil;

    @Override
    public void saveReidsUser(String userId) {
        redisUtil.set("userId",userId,867l, TimeUnit.DAYS);
    }
}
