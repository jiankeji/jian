package com.jian.core.server.dao;

import com.jian.core.model.bean.Attention;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 用户关注 取关dao
 * @author shen
 *
 */
@Mapper
public interface AttentionDao {

	//查看关注状态
	int selectattstatus1(Attention attention);
	int selectattstatus2(Attention attention);
	
	//添加关注  没有粉丝关系
	int addAttention(Attention attention);
	
	//添加关注  有粉丝关系   userIda 关注了 userIdb 且userIda 对应表里的useratt_Id
	//取消关注  一开始是互相关注
	int addAttention2(Attention attention);
	
	//添加关注  有粉丝关系  userIda 关注了 userIdb 且userIdb 对应表里的useratt_Id
	//取消关注  一开始是互相关注 
	int addAttention3(Attention attention);
	
	//取消关注  单向关注 userIda对应表里的useratt_Id
	int  cancelAttention1(Attention attention);
	//取消关注  单向关注 userIda对应表里的useratt_Ids
	int  cancelAttention2(Attention attention);
	
	//添加关注 给自己加个粉丝数
	int addfans(Integer userId);
	//取消关注 给自己减去个粉丝数
	int deletefans(Integer userId);
}








