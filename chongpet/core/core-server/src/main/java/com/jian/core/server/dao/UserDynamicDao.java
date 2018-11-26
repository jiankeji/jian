package com.jian.core.server.dao;

import com.jian.core.model.bean.Comment;
import com.jian.core.model.bean.Dynamic;
import com.jian.core.model.bean.Img;
import com.jian.core.model.bean.Reply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户动态 评论 回复 点赞 dao
 * @author shen
 *
 */
@Repository
public interface UserDynamicDao {
	//发动态
	int addDynamic(Dynamic dynamic);
	//添加动态图片
	int addDynamicImg(@Param("paths") String paths, @Param("dynamicId") Integer dynamicId);
	//查看是否点赞
	int selectLikeStatus(@Param("dynamicId") Integer dynamicId, @Param("userId") Integer userId);
	//点赞 改状态
	int addUserLike(@Param("dynamicId") Integer dynamicId, @Param("userId") Integer userId);
	//点赞 取消赞  改状态
	int updateLikeStatus(@Param("dynamicId") Integer dynamicId, @Param("userId") Integer userId, @Param("status") Integer status);
	//点赞 likenum加1 取消赞 就减1
	int updateLikeNum(@Param("dynamicId") Integer dynamicId, @Param("number") Integer number);
	//查看动态点赞数
	int selectLikeNum(Integer dynamicId);

	//查询动态 分页
	List<Dynamic> showDynamic();
	//根据动态id查询评论
	List<Comment> selectCommentbyDMC(Integer dynamicId);
	//根据评论id查询回复
	List<Reply> selectReplyByCMT(Integer commentId);
	//根据动态id查询动态图片
	List<Img> selectDMCIMGByDMC(Integer dynamicId);
	//浏览动态 增加浏览数
	int browse(Integer dynamicId);

	//评论
	int comment(Comment comment);
	//回复
	int reply(Reply reply);

	//根据回复id 与 userId   主要是查看用户与回复有没有关系 确定删除权限
	int selectReplyById(@Param("replyId") Integer replyId, @Param("userId") Integer userId);
	//删除回复
	int deleteReply(Integer replyId);

	//主要是判断用户与评论有没有关系 确定删除权限
	int selectCommentById(@Param("commentId") Integer commentId, @Param("userId") Integer userId);
	//删除评论
	int deleteComment(Integer commentId);

	//主要是判断用户与动态有没有关系 确定删除权限
	int selectDynamicById(@Param("dynamicId") Integer dynamicId, @Param("userId") Integer userId);
	//删除动态
	int deleteDynamicById(Integer dynamicId);
	//删除动态图片
	int deleteImgById(Integer imgId);
	
}







