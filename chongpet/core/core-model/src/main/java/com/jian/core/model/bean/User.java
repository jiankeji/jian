package com.jian.core.model.bean;

import java.io.Serializable;

public class User implements Serializable {


	//用户id
	private Integer userId;
	//用户昵称
	private String name;
	//用户手机号
	private String phoneNumber;
	//用户密码
	private String password;
	//用户性别
	private String sex;
	//用户生日
	private Long birthday;
	//用户所在城市
	private String city;
	//用户个人说明
	private String explain;
	//用户头像
	private String photo;
	//用户最后登入时间
	private Long updateTime;
	//用户创建时间
	private Long createTime;
	//用户微信openid
	private String  wxopenid;
	//用户支付宝id
	private String  payid;
	//用户qq id
	private String  qqid;
	//用户粉丝数
	private Integer fans;
	//用户是否可用 0可用 1不可用
	private Integer usable;
	//用户状态 0可用 1不可用
	private Integer status;
	//推送
	private  String push;
	//手机类型 0 ios  1安卓
	private  Integer phoneType;


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPush() {
		return push;
	}

	public void setPush(String push) {
		this.push = push;
	}

	public Integer getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(Integer phoneType) {
		this.phoneType = phoneType;
	}

	public String getPayid() {
		return payid;
	}
	public void setPayid(String payid) {
		this.payid = payid;
	}
	public String getQqid() {
		return qqid;
	}
	public void setQqid(String qqid) {
		this.qqid = qqid;
	}
	public Integer getUsable() {
		return usable;
	}
	public void setUsable(Integer usable) {
		this.usable = usable;
	}
	public Integer getFans() {
		return fans;
	}
	public void setFans(Integer fans) {
		this.fans = fans;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getWxopenid() {
		return wxopenid;
	}
	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}


}






