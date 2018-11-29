package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "宠物保险页model")
public class PetInsurancePageBo {

    @ApiModelProperty(value = "保险页banner",dataType = "list")
    List<HomeBannerBo> homeBannerList = new ArrayList<>();

    @ApiModelProperty(value = "保险信息",dataType = "list")
    List<PetInsuranceBo> petInsuranceList = new ArrayList<>();

    public List<HomeBannerBo> getHomeBannerList() {
        return homeBannerList;
    }

    public void setHomeBannerList(List<HomeBannerBo> homeBannerList) {
        this.homeBannerList = homeBannerList;
    }

    public List<PetInsuranceBo> getPetInsuranceList() {
        return petInsuranceList;
    }

    public void setPetInsuranceList(List<PetInsuranceBo> petInsuranceList) {
        this.petInsuranceList = petInsuranceList;
    }
}
