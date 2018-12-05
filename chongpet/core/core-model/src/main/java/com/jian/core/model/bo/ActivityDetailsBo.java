package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "活动详情")
public class ActivityDetailsBo {

    @ApiModelProperty(value = "活动Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "图片路径",dataType = "String")
    private String imgUrl;

    @ApiModelProperty(value = "活动标题",dataType = "String")
    private String title;

    @ApiModelProperty(value = "活动短标题",dataType = "String")
    private String subTitle;

    @ApiModelProperty(value = "活动创建时间",dataType = "Date")
    private Date createTime;

    @ApiModelProperty(value = "浏览量",dataType = "String")
    private int pageView;

    @ApiModelProperty(value = "最后更新时间",dataType = "Date")
    private Date updateTime;

    //评论回复字段


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPageView() {
        return pageView;
    }

    public void setPageView(int pageView) {
        this.pageView = pageView;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
