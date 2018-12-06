package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "保障利益模版model")
public class Guarantee {

    @ApiModelProperty(value = "保障利益Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "中文名称",dataType = "String")
    private String cnName;

    @ApiModelProperty(value = "英文名称",dataType = "String")
    private String enName;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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
