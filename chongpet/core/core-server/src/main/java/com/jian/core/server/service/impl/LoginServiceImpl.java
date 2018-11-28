package com.jian.core.server.service.impl;

import com.jian.core.model.bean.User;
import com.jian.core.model.util.Mdfive;
import com.jian.core.model.util.MyRandom;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.dao.LoginDao;
import com.jian.core.server.redisDao.UserRedisDao;
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
@SuppressWarnings("ALL")
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Resource
	private LoginDao loginDao;

	@Autowired
	private UserRedisDao redisDao;

	@Resource
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
	public User selectUserAllByPhone(String phoneNumber){
		User user;
		try {
			user = loginDao.selectUserAllByPhone(phoneNumber);

			if (user.getWxopenid()==null)user.setWxopenid("");
			if (user.getBirthday()==null) user.setBirthday(0L);
			if (user.getCity()==null)user.setCity("");
			if (user.getExplain()==null) user.setExplain("");
			if (user.getPayid()==null) user.setPayid("");
			if (user.getQqid()==null) user.setQqid("");
			if (user.getPush()==null) user.setPush("");
			if (user.getPhoneType()==null)user.setPhoneType(0);
		} catch (Exception e) {
			user = new User();
			user.setUserId(-1);
		}
		return user;
	}

	@Override
	public int register(User user,Integer loginType) {
		String phoneNumber = user.getPhoneNumber();
		String pwd = Mdfive.md(user.getPassword());
		if(loginType==1){
			loginDao.updateUserById(user);
			return 0;
		}

		String name = "user"+ phoneNumber.substring(phoneNumber.length()-4, phoneNumber.length());
		user.setPassword(pwd);
		user.setSex("female");
		user.setCreateTime(System.currentTimeMillis());
		user.setUpdateTime(System.currentTimeMillis());
		String photo = loginDao.getdefaultphoto(MyRandom.getRandom());
		user.setFans(0);
		user.setStatus(0);
		user.setUsable(0);
		user.setName(name);
		user.setPhoto(photo);

		loginDao.adduser(user);
		return 0;
	}

	@Override
	public void redisUser(User user,String token) {
		redisDao.saveReidsUser(user,token);
	}

	@Override
	public Integer getUserIdRedis(String token) {
		Integer userId = redisDao.getUserId(token);
		return userId;
	}


}


















