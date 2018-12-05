package com.jian.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.Activity;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.dao.ActivityDao;
import com.jian.core.server.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.jian.core.model.bean.inter.Constant.REDIS_ACTIVITY_MSG_KEY;
import static com.jian.core.model.bean.inter.Constant.REDIS_ACTIVITY_MSG_ZSET_KEY;

@SuppressWarnings("ALL")
@Component
@Transactional
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public List<Activity> getActivityAll() {
        return activityDao.getActivityAll();
    }

    @Override
    public int setActivity(Activity activity) {
        int num = activityDao.setActivity(activity);
        return num;
    }

    @Override
    public void setActivityAll(List<Activity> activityList) {
        Set<Object> set = redisUtil.zRange(REDIS_ACTIVITY_MSG_ZSET_KEY, 0, -1);
        if (set != null && set.size() > 0) {
            for (Object ins : set) {
                String id = String.valueOf(ins);
                redisUtil.hmDel(REDIS_ACTIVITY_MSG_KEY, id);
                redisUtil.zRem(REDIS_ACTIVITY_MSG_ZSET_KEY, id);
            }
        } else {
            for (Activity activity : activityList) {
                redisUtil.setHashValue(REDIS_ACTIVITY_MSG_KEY, activity.getSid() + "", JSON.toJSONString(activity));
                redisUtil.zAdd(REDIS_ACTIVITY_MSG_ZSET_KEY, activity.getSid() + "", activity.getSid());
            }
        }
    }

    @Override
    public Activity getActivity(int id) {
        String date = redisUtil.getHashValue(REDIS_ACTIVITY_MSG_KEY, id + "");
        Activity activity = new Activity();
        if (date != null && !"".equals(date)) {
            activity = JSON.parseObject(date, Activity.class);
        }
        return activity;
    }

    @Override
    public List<Activity> getRedisActivityAll(int pageNum,int pageSize) {
        List<Activity> list = new ArrayList<>();
        Set<Object> set = redisUtil.zRange(REDIS_ACTIVITY_MSG_ZSET_KEY, pageSize, pageNum);
        if (set != null && set.size() > 0) {
            for (Object ins : set) {
                String id = String.valueOf(ins);
                Activity activity = JSON.parseObject(redisUtil.getHashValue(REDIS_ACTIVITY_MSG_KEY,id),Activity.class);
                list.add(activity);
            }
        }
        return list;
    }
}
