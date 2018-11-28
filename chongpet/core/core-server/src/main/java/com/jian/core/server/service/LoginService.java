package com.jian.core.server.service;


import com.jian.core.model.bean.User;


/**
 * 登录注册service接口
 * @author shen
 *
 */
public interface LoginService {

	//判断新老用户
	String isUserByPhone(String phoneNumber);
	//密码登录
	int loginByPwd(User user);
	//根据手机号查出个人信息
	User  selectUserAllByPhone(String phoneNumber);
	//注册
	int register(User user,Integer loginType);
	//将用户信息存到redis0
	void redisUser(User user,String token);

	//根据token获取用户id
	Integer getUserIdRedis(String token);
}
