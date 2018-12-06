package com.jian.core.model.bo;

import io.swagger.annotations.ApiModelProperty;

public class PlanAndGuaranteeBo {

    @ApiModelProperty(value = "保障利益内容",dataType = "String")
    private String context;

    @ApiModelProperty(value = "中文名称",dataType = "String")
    private String cnName;

    @ApiModelProperty(value = "英文名称",dataType = "String")
    private String enName;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}
