package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "我的宠物model")
public class MyPetListBo {

    @ApiModelProperty(value = "宠物Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "宠物昵称",dataType = "String")
    private String petNickName;

    @ApiModelProperty(value = "宠物头像",dataType = "String")
    private String petHeadImgUrl;

    @ApiModelProperty(value = "宠物品种名称",dataType = "String")
    private String petBreedpage;

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

    public String getPetBreedpage() {
        return petBreedpage;
    }

    public void setPetBreedpage(String petBreedpage) {
        this.petBreedpage = petBreedpage;
    }
}
