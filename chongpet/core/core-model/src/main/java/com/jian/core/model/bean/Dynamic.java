package com.jian.core.model.bean;

import java.util.List;

/**
 * 用户动态实体类
 * @author shen
 *
 */
public class Dynamic {
	
	//用户发布的动态id
	private Integer dynamicId;
	//用户id
	private Integer userId;
	//用户昵称
	private String name;
	//用户头像
	private String photo;
	//浏览数
	private Integer browse;
	//点赞数
	private Integer likenum;
	//发布动态时间戳
	private Long createTime;
	//动态内容
	private String content;
	//动态评论集合
	private List<Comment> commentList;
	//动态图片集合
	private List<Img> ImgList;
	
	
	
	
	public Integer getDynamicId() {
		return dynamicId;
	}
	public void setDynamicId(Integer dynamicId) {
		this.dynamicId = dynamicId;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	public List<Img> getImgList() {
		return ImgList;
	}
	public void setImgList(List<Img> imgList) {
		ImgList = imgList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getBrowse() {
		return browse;
	}
	public void setBrowse(Integer browse) {
		this.browse = browse;
	}
	public Integer getLikenum() {
		return likenum;
	}
	public void setLikenum(Integer likenum) {
		this.likenum = likenum;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
}
