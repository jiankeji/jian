package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@ApiModel(description = "宠物文章")
@Document(indexName = "petnews",type = "news")
public class PetNews {
    @Id
    @ApiModelProperty(value = "文章Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "发布时间",dataType = "date")
    private Date createTime;

    @ApiModelProperty(value = "新闻类型ID",dataType = "String")
    private String newsType;

    @ApiModelProperty(value = "新闻内容",dataType = "String")
    private String newsContext;

    @ApiModelProperty(value = "新闻短内容",dataType = "String")
    private String newsSub;

    @ApiModelProperty(value = "新闻图片",dataType = "String")
    private String newsPic;

    @ApiModelProperty(value = "新闻标题",dataType = "String")
    private String title;

    @ApiModelProperty(value = "新闻类型名称",dataType = "String")
    private String newsTypeName;

    @ApiModelProperty(value = "新闻分类",dataType = "String")
    private String classification;

    @ApiModelProperty(value = "浏览量",dataType = "String")
    private int pageView;

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

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getNewsContext() {
        return newsContext;
    }

    public void setNewsContext(String newsContext) {
        this.newsContext = newsContext;
    }

    public String getNewsSub() {
        return newsSub;
    }

    public void setNewsSub(String newsSub) {
        this.newsSub = newsSub;
    }

    public String getNewsPic() {
        return newsPic;
    }

    public void setNewsPic(String newsPic) {
        this.newsPic = newsPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsTypeName() {
        return newsTypeName;
    }

    public void setNewsTypeName(String newsTypeName) {
        this.newsTypeName = newsTypeName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public int getPageView() {
        return pageView;
    }

    public void setPageView(int pageView) {
        this.pageView = pageView;
    }
}
