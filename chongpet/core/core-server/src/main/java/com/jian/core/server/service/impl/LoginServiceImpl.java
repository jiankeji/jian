package com.jian.core.server.service.impl;

import com.jian.core.model.bean.User;
import com.jian.core.model.util.Mdfive;
import com.jian.core.model.util.MyRandom;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.dao.LoginDao;
import com.jian.core.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 登录注册接口实现
 * @author shen
 *
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Resource
	private LoginDao loginDao;

	@Autowired
	private RedisUtil redisUtil;

	//判断新老用户
	@Override
	public String isUserByPhone(String phoneNumber) {
		String isUser;
		try {
			String phone = loginDao.isUserByPhone(phoneNumber);
			phone.charAt(2);//判断查出来的是否为空
			isUser="true";
		} catch (Exception e) {
			isUser="false";
		}
		return isUser;
	}

	@Override
	public int loginByPwd(User user) {
		if (user.getPhoneNumber().length()!=11) {
			return 201;
		}
		if (user.getPassword().length()==0||user.getPassword()==null) {
			return 202;
		}
		String pwd = Mdfive.md(user.getPassword());
		String mypwd = loginDao.isUserByPhone(user.getPhoneNumber());
		if(!pwd.equals(mypwd)) {
			return 204;
		}
		User user2 = new User();
		user2.setPhoneNumber(user.getPhoneNumber());
		user2.setUpdateTime(System.currentTimeMillis());
		loginDao.updateUserByPhone(user2);//更改登录时间
		return 0;
	}

	@Override
	public User selectUserAllByPhone(String phoneNumber) {
		User user; 
				
		try {
			user = loginDao.selectUserAllByPhone(phoneNumber);
			user.setWxopenid("");
			user.setPassword("");
			
			if (user.getBirthday()==null) {
				user.setBirthday(0L);
			}
			if (user.getCity()==null) {
				user.setCity("");
			}
			if (user.getExplain()==null) {
				user.setExplain("");
			}
			
		} catch (Exception e) {
			user=new User();
			user.setUserId(-1);
		}
		return user;
	}

	@Override
	public int register(User user) {
		String phoneNumber = user.getPhoneNumber();
		String name = "user"+ phoneNumber.substring(phoneNumber.length()-4, phoneNumber.length());
		String pwd = Mdfive.md(user.getPassword());
		user.setPassword(pwd);
		user.setName(name);
		user.setSex("female");
		user.setCreateTime(System.currentTimeMillis());
		user.setUpdateTime(System.currentTimeMillis());
		String photo = loginDao.getdefaultphoto(MyRandom.getRandom());
		user.setPhoto(photo);
		user.setFans(0);
		loginDao.adduser(user);
		return 0;
	}
	
	
}


















