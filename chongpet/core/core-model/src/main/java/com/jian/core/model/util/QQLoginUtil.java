package com.jian.core.model.util;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class QQLoginUtil {
    /**
     * 获取openid QQ的
     * @param accessToken
     * @return
     */
    public static String getOpenid(String accessToken){
        String address = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;

        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流

            connection.disconnect();// 断开连接

            String [] arr = sb.toString().split("\\{");
            String [] arrs = arr[1].split("\\}");
            String data = "{"+arrs[0]+"}";
            Map<String,Object> map = JSON.parseObject(data,Map.class);
            Integer error = (Integer) map.get("error");
            if(error!=null){
                return "-1";
            }
            String openid = (String) map.get("openid");
            return openid;
        } catch (Exception e) {
            return  "-1";
        }
    }

    public static User qqUser(String accessToken){

        String appid = "";

        User user = new User();
        String openid = getOpenid(accessToken);
        if(openid.equals("-1")){
            user.setUserId(-1);
            //return user;
        }

        String address = "https://graph.qq.com/user/get_user_info?access_token="+accessToken+"&oauth_consumer_key="+appid+"&openid="+openid;

        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接

            System.out.println(sb.toString()+1);
            Map<String,Object> map = JSON.parseObject(sb.toString(),Map.class);
            Integer ret = (Integer) map.get("ret");

            System.out.println(ret);
            if(ret!=0){
                user.setUserId(-1);
                return user;
            }

            String nickname = (String)map.get("nickname");
            String avatar = (String)map.get("figureurl_qq_2");

            user.setName(nickname);
            user.setPhoto(avatar);
            user.setQqid(openid);
            return  user;
        } catch (Exception e) {
            user.setUserId(-1);
            return user;
        }


    }


}
