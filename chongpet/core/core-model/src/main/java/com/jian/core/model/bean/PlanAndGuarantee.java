package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "计划和保障利益字段对应表")
public class PlanAndGuarantee {

    @ApiModelProperty(value = "Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "计划Id",dataType = "int")
    private int planId;

    @ApiModelProperty(value = "保障利益内容",dataType = "String")
    private String context;

    @ApiModelProperty(value = "保障ID",dataType = "int")
    private int gid;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
