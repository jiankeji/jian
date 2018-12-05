package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "宠物信息model")
public class PetMsg {

    @ApiModelProperty(value = "宠物Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "宠物昵称",dataType = "String")
    private String petNickName;

    @ApiModelProperty(value = "宠物头像",dataType = "String")
    private String petHeadImgUrl;

    @ApiModelProperty(value = "宠物性别",dataType = "int")
    private int sex;

    @ApiModelProperty(value = "宠物品种Id",dataType = "int")
    private int petBreedpageId;

    @ApiModelProperty(value = "宠物生日时间戳",dataType = "long")
    private long petBirthday;

    @ApiModelProperty(value = "宠物签名",dataType = "long")
    private String petSignautre;

    @ApiModelProperty(value = "创建时间戳",dataType = "long")
    private long petCreateTime;

    @ApiModelProperty(value = "状态",dataType = "int")
    private int isstatus;


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getPetNickName() {
        return petNickName;
    }

    public void setPetNickName(String petNickName) {
        this.petNickName = petNickName;
    }

    public String getPetHeadImgUrl() {
        return petHeadImgUrl;
    }

    public void setPetHeadImgUrl(String petHeadImgUrl) {
        this.petHeadImgUrl = petHeadImgUrl;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getPetBreedpageId() {
        return petBreedpageId;
    }

    public void setPetBreedpageId(int petBreedpageId) {
        this.petBreedpageId = petBreedpageId;
    }

    public long getPetBirthday() {
        return petBirthday;
    }

    public void setPetBirthday(long petBirthday) {
        this.petBirthday = petBirthday;
    }

    public long getPetCreateTime() {
        return petCreateTime;
    }

    public void setPetCreateTime(long petCreateTime) {
        this.petCreateTime = petCreateTime;
    }

    public String getPetSignautre() {
        return petSignautre;
    }

    public void setPetSignautre(String petSignautre) {
        this.petSignautre = petSignautre;
    }

    public int getIsstatus() {
        return isstatus;
    }

    public void setIsstatus(int isstatus) {
        this.isstatus = isstatus;
    }
}
