package com.jian.core.server.redisDao.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jian.core.model.bean.Dynamic;
import com.jian.core.redis.util.RedisUtil1;
import com.jian.core.redis.util.RedisUtil2;
import com.jian.core.server.redisDao.MqDynamicDao;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Set;

import static com.jian.core.model.bean.inter.Constant.REDIS_HOME_DYNAMIC_ATTENTION;

@Component
public class MqDynamicMapper implements MqDynamicDao {


    @Resource
    private RedisUtil1 redisUtil1;
    @Resource
    private RedisUtil2 redisUtil2;

    @Resource
    private JmsTemplate jmsTemplate;


    @Override
    public void sendDynamicFans(Dynamic dynamic) {
        jmsTemplate.send("queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(JSON.toJSONString(dynamic));
                return textMessage;
            }
        });
    }

    @JmsListener(destination = "queue")
    public void receiveDynamicFans(String message) {

        Dynamic dynamic = JSONObject.parseObject(message,Dynamic.class);

        String dataMe = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_ATTENTION+dynamic.getUserId());
        List<Dynamic> listMe = JSONArray.parseArray(dataMe,Dynamic.class);
        listMe.add(dynamic);
        redisUtil1.set(REDIS_HOME_DYNAMIC_ATTENTION+dynamic.getUserId(),listMe);

        Set set = redisUtil2.getFansRange(dynamic.getUserId(),0,-1);
        for (Object s:set){
            if(s instanceof String){
                String userId = (String)s;
                String data = (String) redisUtil1.get(REDIS_HOME_DYNAMIC_ATTENTION+userId);
                List<Dynamic> list = JSONArray.parseArray(data,Dynamic.class);
                list.add(dynamic);
                redisUtil1.set(REDIS_HOME_DYNAMIC_ATTENTION+userId,list);
            }
        }
    }


}
