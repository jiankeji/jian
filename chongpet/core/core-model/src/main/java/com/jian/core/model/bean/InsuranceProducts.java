package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "产品条款model")
public class InsuranceProducts {

    @ApiModelProperty(value = "条款Id",dataType = "int")
    private int sid;

    @ApiModelProperty(value = "条款名称",dataType = "String")
    private String productsName;//条款名称

    @ApiModelProperty(value = "条款地址",dataType = "String")
    private String productsUrl;//条款地址

    @ApiModelProperty(value = "状态",dataType = "int")
    private int isstatus;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getProductsUrl() {
        return productsUrl;
    }

    public void setProductsUrl(String productsUrl) {
        this.productsUrl = productsUrl;
    }

    public int getIsstatus() {
        return isstatus;
    }

    public void setIsstatus(int isstatus) {
        this.isstatus = isstatus;
    }
}
