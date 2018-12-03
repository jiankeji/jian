package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "首页宠派头条")
public class PetHomeFrontPageBo {

    @ApiModelProperty(value = "文章Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "新闻标题",dataType = "String")
    private String title;

    @ApiModelProperty(value = "新闻图片",dataType = "String")
    private String newsPic;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsPic() {
        return newsPic;
    }

    public void setNewsPic(String newsPic) {
        this.newsPic = newsPic;
    }
}
