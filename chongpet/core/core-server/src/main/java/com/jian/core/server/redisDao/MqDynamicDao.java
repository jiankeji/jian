package com.jian.core.server.redisDao;

import com.jian.core.model.bean.Dynamic;

public interface MqDynamicDao {

    //发送动态到MQ
    void sendDynamicFans(Dynamic dynamic);
}
