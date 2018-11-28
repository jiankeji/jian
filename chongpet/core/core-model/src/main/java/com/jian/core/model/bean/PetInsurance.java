package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "宠物保险")
public class PetInsurance {

    @ApiModelProperty(value = "保险Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "图片路径",dataType = "String")
    private String imgUrl;

    @ApiModelProperty(value = "保险标题",dataType = "String")
    private String title;

    @ApiModelProperty(value = "保险短内容",dataType = "String")
    private String subcontext;

    @ApiModelProperty(value = "保险内容",dataType = "String")
    private String context;

    @ApiModelProperty(value = "价格",dataType = "float")
    private float price;

    @ApiModelProperty(value = "状态",dataType = "int")
    private int isstatus;

    @ApiModelProperty(value = "排序",dataType = "int")
    private int sortNum;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSubcontext() {
        return subcontext;
    }

    public void setSubcontext(String subcontext) {
        this.subcontext = subcontext;
    }

    public int getIsstatus() {
        return isstatus;
    }

    public void setIsstatus(int isstatus) {
        this.isstatus = isstatus;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "PetInsurance{" +
                "sid=" + sid +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", subcontext='" + subcontext + '\'' +
                ", context='" + context + '\'' +
                ", price=" + price +
                ", isstatus=" + isstatus +
                ", sortNum=" + sortNum +
                '}';
    }
}
