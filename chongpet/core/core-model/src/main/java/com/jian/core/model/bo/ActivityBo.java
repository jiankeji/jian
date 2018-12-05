package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "宠圈--活动页入口页")
public class ActivityBo {

    @ApiModelProperty(value = "活动Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "图片路径",dataType = "String")
    private String imgUrl;

    @ApiModelProperty(value = "活动标题",dataType = "String")
    private String title;

    @ApiModelProperty(value = "活动短标题",dataType = "String")
    private String subTitle;

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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
