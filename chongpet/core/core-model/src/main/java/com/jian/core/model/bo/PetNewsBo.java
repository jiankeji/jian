package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "宠物文章详情")
public class PetNewsBo {

    @ApiModelProperty(value = "文章Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "发布时间",dataType = "date")
    private Date createTime;

    @ApiModelProperty(value = "新闻内容",dataType = "String")
    private String newsContext;

    @ApiModelProperty(value = "新闻标题",dataType = "String")
    private String title;

    @ApiModelProperty(value = "浏览量",dataType = "String")
    private int pageView;

    @ApiModelProperty(value = "发布人ID",dataType = "String")
    private String userName;

    @ApiModelProperty(value = "点赞次数",dataType = "int")
    private int supportNum;
    //评论恢复

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNewsContext() {
        return newsContext;
    }

    public void setNewsContext(String newsContext) {
        this.newsContext = newsContext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageView() {
        return pageView;
    }

    public void setPageView(int pageView) {
        this.pageView = pageView;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(int supportNum) {
        this.supportNum = supportNum;
    }
}
