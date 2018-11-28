package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "首页宠物保险")
public class PetInsuranceBo {

    @ApiModelProperty(value = "保险Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "图片路径",dataType = "String")
    private String imgUrl;

    @ApiModelProperty(value = "保险标题",dataType = "String")
    private String title;

    @ApiModelProperty(value = "保险短内容",dataType = "String")
    private String subcontext;

    @ApiModelProperty(value = "价格",dataType = "float")
    private float price;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
