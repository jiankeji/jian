package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 用户动态评论 实体类
 * @author shen
 *
 */
@ApiModel(value = "用户动态评论")
public class Comment {
	//评论id
	@ApiModelProperty(value = "评论id",dataType = "int")
	private Integer commentId;
	//用户发布的动态id
	@ApiModelProperty(value = "用户发布的动态id",dataType = "int")
	private Integer dynamicId;

	@ApiModelProperty(value = "评论点赞数",dataType = "int")
	private  Integer likeNum;
	//作者id
	@ApiModelProperty(value = "作者id",dataType = "int")
	private Integer toUserId;
	//作者name
	@ApiModelProperty(value = "作者姓名",dataType = "String")
	private String toUserName;
	//作者头像
    @ApiModelProperty(value = "作者头像",dataType = "String")
	private String toUserPhoto;
	//评论者id
    @ApiModelProperty(value = "评论者id",dataType = "int")
	private Integer fromUserId;
	//评论者name
    @ApiModelProperty(value = "评论者name",dataType = "String")
	private String  fromUserName;
	//评论者头像
    @ApiModelProperty(value = "评论者头像",dataType = "String")
	private String  fromUserPhoto;
	//评论内容
    @ApiModelProperty(value = "评论内容",dataType = "String")
	private String commentContent;
	//评论时间
    @ApiModelProperty(value = "评论时间",dataType = "long")
	private Long commentTime;
	//评论的回复集合
    @ApiModelProperty(value = "评论的回复集合",dataType = "List<Reply>")
	private List<Reply> replyList;

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getDynamicId() {
		return dynamicId;
	}
	public void setDynamicId(Integer dynamicId) {
		this.dynamicId = dynamicId;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getToUserPhoto() {
		return toUserPhoto;
	}
	public void setToUserPhoto(String toUserPhoto) {
		this.toUserPhoto = toUserPhoto;
	}
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getFromUserPhoto() {
		return fromUserPhoto;
	}
	public void setFromUserPhoto(String fromUserPhoto) {
		this.fromUserPhoto = fromUserPhoto;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public Long getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Long commentTime) {
		this.commentTime = commentTime;
	}
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	
	
	
	
	
}
