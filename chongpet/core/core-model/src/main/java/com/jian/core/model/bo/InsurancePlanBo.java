package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "保险计划")
public class InsurancePlanBo {

    @ApiModelProperty(value = "计划Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "保险Id",dataType = "int")
    private int insuranceId;

    /**被保信息*/
    @ApiModelProperty(value = "计划名称",dataType = "String")
    private String planName;

    @ApiModelProperty(value = "适用人群",dataType = "String")
    private String applyPeople;

    /**保障利益*/
    @ApiModelProperty(value = "保障利益",dataType = "List")
    private List<PlanAndGuaranteeBo> planAndGuaranteeBoList = new ArrayList<>();

    @ApiModelProperty(value = "保险详情",dataType = "String")
    private String insuranceDetaile;//保险详情


    @ApiModelProperty(value = "价格",dataType = "BigDecimal")
    private BigDecimal price;//decimal  价格

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getApplyPeople() {
        return applyPeople;
    }

    public void setApplyPeople(String applyPeople) {
        this.applyPeople = applyPeople;
    }

    public String getInsuranceDetaile() {
        return insuranceDetaile;
    }

    public void setInsuranceDetaile(String insuranceDetaile) {
        this.insuranceDetaile = insuranceDetaile;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<PlanAndGuaranteeBo> getPlanAndGuaranteeBoList() {
        return planAndGuaranteeBoList;
    }

    public void setPlanAndGuaranteeBoList(List<PlanAndGuaranteeBo> planAndGuaranteeBoList) {
        this.planAndGuaranteeBoList = planAndGuaranteeBoList;
    }
}
