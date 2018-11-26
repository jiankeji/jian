package com.jian.core.server.service;


import com.jian.core.model.bean.Comment;
import com.jian.core.model.bean.Dynamic;
import com.jian.core.model.bean.Reply;

import java.util.List;

/**
 * 用户动态 评论 回复 点赞 service接口
 * @author shen
 *
 */
public interface UserDynamicService {

	//发布动态 除了图片
	int publish(Integer userId, String content);
	//发布动态的图片
	int publishimg(String paths, Integer dynamicId);
	//点赞或者取消赞
	int like(Integer dynamicId, Integer userId);
	//查询动态 分页
	List<Dynamic>showDynamic(Integer pageNum, Integer pageSize);

	//评论
	int comment(Comment comment);
	//回复
	int reply(Reply reply);

	//删除回复
	int deleteReply(Integer replyId, Integer userId);
	//删除评论
	int deleteComment(Integer commentId, Integer userId);
	//删除动态
	int deleteDynamic(Integer dynamicId, Integer userId);
}




