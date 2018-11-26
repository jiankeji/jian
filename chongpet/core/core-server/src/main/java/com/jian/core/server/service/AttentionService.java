package com.jian.core.server.service;


import com.jian.core.model.bean.Attention;

/**
 * 用户关注取关service接口
 * @author shen
 *
 */
public interface AttentionService {
	
	//关注
	int attention(Attention attention);
	
	//取消关注
	int  cancelAttention(Attention attention);
}









