package com.jian.core.server.service;

import com.jian.core.model.bean.Activity;

import java.util.List;

public interface ActivityService {

    List<Activity> getActivityAll();

    int setActivity(Activity activity);

    /**
     * 用于提交活动进入redis中
     * @param activityList
     */
    void  setActivityAll(List<Activity> activityList);

    /**
     * 从reids中获取获取单条活动信息
     * @param id
     * @return
     */
    Activity getActivity(int id);

    List<Activity> getRedisActivityAll(int pageNum,int pageSize);
}
