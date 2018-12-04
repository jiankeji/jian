package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "宠物保险基础信息")
public class InsuranceBo {

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

    @ApiModelProperty(value = "价格",dataType = "double")
    private double price;

    @ApiModelProperty(value = "保障时间--按天算",dataType = "int")
    private int guaranteeTime;

    @ApiModelProperty(value = "产品特色图片",dataType = "String")
    private String featureImgUrl;//产品特色  product

    @ApiModelProperty(value = "投保须知",dataType = "String")
    private String information;//投保须知

    @ApiModelProperty(value = "产品条款编号 例子:1,2,3",dataType = "String")
    private String products;//产品条款

    @ApiModelProperty(value = "理赔服务",dataType = "String")
    private String serve;//理赔服务

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

    public String getSubcontext() {
        return subcontext;
    }

    public void setSubcontext(String subcontext) {
        this.subcontext = subcontext;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getGuaranteeTime() {
        return guaranteeTime;
    }

    public void setGuaranteeTime(int guaranteeTime) {
        this.guaranteeTime = guaranteeTime;
    }

    public String getFeatureImgUrl() {
        return featureImgUrl;
    }

    public void setFeatureImgUrl(String featureImgUrl) {
        this.featureImgUrl = featureImgUrl;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getServe() {
        return serve;
    }

    public void setServe(String serve) {
        this.serve = serve;
    }
}
