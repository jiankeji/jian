package com.jian.core.server.dao;

import com.jian.core.model.bean.User;
import org.springframework.stereotype.Repository;

/**
 * 登录注册dao
 * @author shen
 *
 */
@Repository
public interface LoginDao {

	//根据手机号查密码
	String isUserByPhone(String phoneNumber);
	//根据手机号查出个人信息
	User selectUserAllByPhone(String phoneNumber);
	//更改用户信息
	int updateUserByPhone(User user);
	//随机获得一个默认头像
	String getdefaultphoto(Integer number);
	//新增用户
	int adduser(User user);
	//根据id修改用户信息
	void updateUserById(User user);
}
