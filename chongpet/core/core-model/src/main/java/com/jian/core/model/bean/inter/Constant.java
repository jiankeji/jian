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
}
