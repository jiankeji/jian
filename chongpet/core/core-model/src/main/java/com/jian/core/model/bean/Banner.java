package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "首页banner图")
public class Banner {

    @ApiModelProperty(value = "图片Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "图片路径",dataType = "String")
    private String imgUrl;

    @ApiModelProperty(value = "banner内容",dataType = "String")
    private String bannerContext;

    @ApiModelProperty(value = "状态",dataType = "int")
    private int isstatus;

    @ApiModelProperty(value = "排序",dataType = "int")
    private int sortNum;

    @ApiModelProperty(value = "banner类型 1首页banner 2 保险页banner",dataType = "int")
    private int bType;

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

    public String getBannerContext() {
        return bannerContext;
    }

    public void setBannerContext(String bannerContext) {
        this.bannerContext = bannerContext;
    }

    public int getbType() {
        return bType;
    }

    public void setbType(int bType) {
        this.bType = bType;
    }
}
