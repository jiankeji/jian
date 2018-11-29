package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "关注和粉丝model")
public class UserAttentionBo {

    @ApiModelProperty(value = "用户Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "用户昵称",dataType = "String")
    private String userNickName;

    @ApiModelProperty(value = "用户头像",dataType = "String")
    private String headImgUrl;

    @ApiModelProperty(value="关系==异常:-1, 无关系:0, 关注:1, 被关注(粉丝):2, 互相关注:3", dataType="Integer")
    private Integer relation = 0;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }
}
