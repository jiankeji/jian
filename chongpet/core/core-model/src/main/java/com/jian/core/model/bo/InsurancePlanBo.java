package com.jian.core.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

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

    @ApiModelProperty(value = "累计赔偿限额",dataType = "int")
    private int aggregateAnmtmt;//累计赔付金额

    @ApiModelProperty(value = "保险详情",dataType = "String")
    private String insuranceDetaile;//保险详情

    @ApiModelProperty(value = "每次赔偿限额",dataType = "int")
    private int everyTimeMoney;//每次赔付金额

    @ApiModelProperty(value = "每次事故财产损失免赔额",dataType = "int")
    private int everyTimeLoss;//每次损失免赔赔付金额

    @ApiModelProperty(value = "每次事故人身伤害免赔额",dataType = "int")
    private int everyTimeHurt;//每次伤害免赔

    @ApiModelProperty(value = "生效时间类型 0 次日凌晨生效 1立即生效",dataType = "int")
    private int takeEffectType;//生效时间类型 0次日凌晨生效  1 立即生效

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

    public int getAggregateAnmtmt() {
        return aggregateAnmtmt;
    }

    public void setAggregateAnmtmt(int aggregateAnmtmt) {
        this.aggregateAnmtmt = aggregateAnmtmt;
    }

    public String getInsuranceDetaile() {
        return insuranceDetaile;
    }

    public void setInsuranceDetaile(String insuranceDetaile) {
        this.insuranceDetaile = insuranceDetaile;
    }

    public int getEveryTimeMoney() {
        return everyTimeMoney;
    }

    public void setEveryTimeMoney(int everyTimeMoney) {
        this.everyTimeMoney = everyTimeMoney;
    }

    public int getEveryTimeLoss() {
        return everyTimeLoss;
    }

    public void setEveryTimeLoss(int everyTimeLoss) {
        this.everyTimeLoss = everyTimeLoss;
    }

    public int getEveryTimeHurt() {
        return everyTimeHurt;
    }

    public void setEveryTimeHurt(int everyTimeHurt) {
        this.everyTimeHurt = everyTimeHurt;
    }

    public int getTakeEffectType() {
        return takeEffectType;
    }

    public void setTakeEffectType(int takeEffectType) {
        this.takeEffectType = takeEffectType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
