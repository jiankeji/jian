package com.jian.core.model.bo;

import com.jian.core.model.bean.PetInsurance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "首页信息")
public class HomePageBo {

    @ApiModelProperty(value = "首页banner",dataType = "list")
    List<HomeBannerBo> homeBannerList = new ArrayList<>();

    @ApiModelProperty(value = "首页保险",dataType = "list")
    List<PetInsuranceBo> petInsuranceList = new ArrayList<>();

    @ApiModelProperty(value = "首页宠物医院",dataType = "list")
    List<HomePetHospitalBo> homePetHospitalBoList = new ArrayList<>();


    public List<PetInsuranceBo> getPetInsuranceList() {
        return petInsuranceList;
    }

    public void setPetInsuranceList(List<PetInsuranceBo> petInsuranceList) {
        this.petInsuranceList = petInsuranceList;
    }

    public List<HomePetHospitalBo> getHomePetHospitalBoList() {
        return homePetHospitalBoList;
    }

    public void setHomePetHospitalBoList(List<HomePetHospitalBo> homePetHospitalBoList) {
        this.homePetHospitalBoList = homePetHospitalBoList;
    }

    public List<HomeBannerBo> getHomeBannerList() {
        return homeBannerList;
    }

    public void setHomeBannerList(List<HomeBannerBo> homeBannerList) {
        this.homeBannerList = homeBannerList;
    }
}
