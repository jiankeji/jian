package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "宠派达人")
public class PetExpert {

    @ApiModelProperty(value = "用户Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "用户头像",dataType = "String")
    private String headImgUrl;

    @ApiModelProperty(value = "用户个人说明",dataType = "String")
    private String explain;//个人说明

    @ApiModelProperty(value = "用户昵称",dataType = "String")
    private String nickName;

    @ApiModelProperty(value = "粉丝数",dataType = "int")
    private int fansNum;

    @ApiModelProperty(value = "用户达人类型",dataType = "String")
    private String talentType;//达人类型

    @ApiModelProperty(value = "用户动态",dataType = "String")
    private String dynamic;//动态

    @ApiModelProperty(value = "用户动态图片集合",dataType = "List")
    private List<String> dynamicImgUrl;//动态图片

    @ApiModelProperty(value = "是否关注",dataType = "boolean")
    private boolean ifAttention;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public String getTalentType() {
        return talentType;
    }

    public void setTalentType(String talentType) {
        this.talentType = talentType;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public List<String> getDynamicImgUrl() {
        return dynamicImgUrl;
    }

    public void setDynamicImgUrl(List<String> dynamicImgUrl) {
        this.dynamicImgUrl = dynamicImgUrl;
    }

    public boolean isIfAttention() {
        return ifAttention;
    }

    public void setIfAttention(boolean ifAttention) {
        this.ifAttention = ifAttention;
    }
}
