package com.jian.core.model.bean;

public class Attention {

	//关注id
	private Integer attId;
	//用户a的id
	private Integer userIda;
	//用户b的id
	private Integer userIdb;
	//关注状态
	//0  b关注了 a
	//1  a关注了b    
	//2  互相关注
	private Integer status;
	public Integer getAttId() {
		return attId;
	}
	public void setAttId(Integer attId) {
		this.attId = attId;
	}
	public Integer getUserIda() {
		return userIda;
	}
	public void setUserIda(Integer userIda) {
		this.userIda = userIda;
	}
	public Integer getUserIdb() {
		return userIdb;
	}
	public void setUserIdb(Integer userIdb) {
		this.userIdb = userIdb;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
