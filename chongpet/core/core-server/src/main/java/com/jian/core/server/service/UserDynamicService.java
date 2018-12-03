package com.jian.core.server.service;


import com.jian.core.model.bean.Comment;
import com.jian.core.model.bean.Dynamic;
import com.jian.core.model.bean.Img;
import com.jian.core.model.bean.Reply;

import java.util.List;

/**
 * 用户动态 评论 回复 点赞 service接口
 * @author shen
 *
 */
public interface UserDynamicService {

	//发布动态 除了图片
	Dynamic publish(String token, String content);
	//发布动态的图片
	Img publishimg(String paths, Integer dynamicId);
	//将动态 信息上传到redis
	int publistRedis(Dynamic dynamic);
	//点赞或者取消赞
	int like(Integer dynamicId, Integer userId);
	//查询动态 分页
	List<Dynamic>showDynamic(Integer pageNum, Integer pageSize,Integer userId,Integer status);

	//评论
	int comment(Comment comment, String token);
	//回复
	int reply(Reply reply, String token);

	//删除回复
	int deleteReply(Integer replyId,Integer commentId, String token);
	//删除评论
	int deleteComment(Integer commentId,Integer dynamicId, String token);
	//删除动态
	int deleteDynamic(Integer dynamicId, String token);
	//浏览动态
	void browse(Integer dynamicId);

	//评论点赞取赞
	void commentLike(Integer commentId,String token);
}




