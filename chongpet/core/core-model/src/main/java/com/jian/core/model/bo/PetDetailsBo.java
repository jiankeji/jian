package com.jian.core.model.bo;

import com.jian.core.model.bean.PetLable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "宠物详情页信息")
public class PetDetailsBo {

    @ApiModelProperty(value = "宠物Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "宠物昵称",dataType = "String")
    private String petNickName;

    @ApiModelProperty(value = "宠物头像",dataType = "String")
    private String petHeadImgUrl;

    @ApiModelProperty(value = "宠物生日时间戳",dataType = "long")
    private long petBirthday;

    @ApiModelProperty(value = "宠物品种名称",dataType = "String")
    private String petBreedpage;

    @ApiModelProperty(value = "宠物标签",dataType = "List")
    private List<PetLable> petLableList = new ArrayList<>();

    //宠物动态

    //宠物相册


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

    public long getPetBirthday() {
        return petBirthday;
    }

    public void setPetBirthday(long petBirthday) {
        this.petBirthday = petBirthday;
    }

    public String getPetBreedpage() {
        return petBreedpage;
    }

    public void setPetBreedpage(String petBreedpage) {
        this.petBreedpage = petBreedpage;
    }

    public List<PetLable> getPetLableList() {
        return petLableList;
    }

    public void setPetLableList(List<PetLable> petLableList) {
        this.petLableList = petLableList;
    }
}
