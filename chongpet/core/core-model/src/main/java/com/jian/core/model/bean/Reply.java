package com.jian.core.model.bean;

/**
 * 用户动态回复  实体类
 * @author shen
 *
 */
public class Reply {

	
	//回复id
	private Integer replyId;
	//评论id
	private Integer commentId;
	//被回复者id
	private Integer toUserId;
	//被回复者昵称
	private String toUserName;
	//被回复者头像
	private String toUserPhoto;
	//回复者id
	private Integer fromUserId;
	//回复者昵称
	private String fromUserName;
	//回复者头像
	private String fromUserPhoto;
	//回复内容
	private String replyContent;
	//回复时间
	private Long replyTime;
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Long getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Long replyTime) {
		this.replyTime = replyTime;
	}
	
	
	
	
}
