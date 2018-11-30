package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "宠派头条Model")
public class PetFrontPageBo {

    @ApiModelProperty(value = "时间",dataType = "String")
    private String dateTime;

    @ApiModelProperty(value = "头条新闻集合",dataType = "List")
    private List<PetHomeFrontPageBo> petFrontPageBoList = new ArrayList<>();

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<PetHomeFrontPageBo> getPetFrontPageBoList() {
        return petFrontPageBoList;
    }

    public void setPetFrontPageBoList(List<PetHomeFrontPageBo> petFrontPageBoList) {
        this.petFrontPageBoList = petFrontPageBoList;
    }
}
