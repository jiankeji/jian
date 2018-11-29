package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("新用户关注/被关注model")
public class UserAttention {

	@ApiModelProperty(value="id", dataType="Integer")
	private Integer sid;

	@ApiModelProperty(value="用户id", dataType="Integer")
	private Integer userId;

	@ApiModelProperty(value="关注用户id", dataType="Integer")
	private Integer followUserId;

	@ApiModelProperty(value="关注时间", dataType="Integer")
	private Date addDate;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFollowUserId() {
		return followUserId;
	}

	public void setFollowUserId(Integer followUserId) {
		this.followUserId = followUserId;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
}
