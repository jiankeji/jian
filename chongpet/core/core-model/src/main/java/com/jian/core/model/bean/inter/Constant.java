package com.jian.core.model.bean.inter;

public interface Constant {

    /**reids首页banner路径*/
    final static String REIDS_HOME_BANNER_KEY="home_banner_key";

    /**reids首页banner排序路径*/
    final static String REDIS_HOME_BANNER_ZSET_KEY="home_banner_zset_key";

    /**reids首页保险路径*/
    final static String REDIS_HOME_INSURANCE_KEY="home_insurance_key";

    /**reids首页排序排序路径*/
    final static String REDIS_HOME_INSURANCE_ZSET_KEY="home_insurance_zset_key";

    /**redis用户动态目录*/
    final static String REDIS_HOME_DYNAMIC="userDynanic:";
    /**redis 用户动态点赞目录*/
    final static String REDIS_HOME_DYNAMIC_LIKE="userDynanicLike:";

    /**用户信息*/
    final static String REDIS_VALID_USER_KEY ="usersmap";


    /**用户宠物信息资料*/
    final static String REDIS_PET_MSG_MAP_KEY = "pet_msg_map_key";
    /**用户宠物顺序*/
    final static String REDIS_PET_MSG_ZSET_KEY = "pet_msg_zset_key";

    /**宠物标签*/
    final static String REDIS_PET_LABLE_MSG_MAP_KEY="pet_lable_msg_map";

    /**宠物头条*/
    final static String REDIS_PET_NEWS_MAP_KEY = "pet_news_map";

    /**宠物头条*/
    final static String REDIS_PET_NEWS_ZSET_KEY = "pet_news_zset";

    /**redis 用户动态关注目录*/
    final static String REDIS_HOME_DYNAMIC_ATTENTION="userDynanicAttention:";

    /**redis 用户动态排序目录 根据时间*/
    final static String REDIS_HOME_DYNAMIC_TIME="userDynamicTime:";
    /**redis 用户动态评论*/
    final static String REDIS_HOME_DYNAMIC_COMMENT="userComment:";
    /**redis 用户动态回复*/
    final static String REDIS_HOME_DYNAMIC_REPLY="userReply:";

    /**活动*/
    final static String REDIS_ACTIVITY_MSG_KEY = "activity_msg";

    /**活动顺序*/
    final static String REDIS_ACTIVITY_MSG_ZSET_KEY = "activity_msg_zset";
}
