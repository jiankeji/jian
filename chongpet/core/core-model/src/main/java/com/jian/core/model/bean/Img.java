package com.jian.core.model.bean;

/**
 * 动态图片 实体类
 * @author shen
 *
 */
public class Img {

	//动态图片id
	private Integer imgId;
	//用户发布的动态id
	private Integer dynamicId;
	//用户图片地址
	private String photo;
	
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public Integer getDynamicId() {
		return dynamicId;
	}
	public void setDynamicId(Integer dynamicId) {
		this.dynamicId = dynamicId;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	
}
