package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "宠物标签")
public class PetLable {

    @ApiModelProperty(value = "是否是系统标签",dataType = "boolean")
    private boolean ifSystem;

    @ApiModelProperty(value = "标签颜色",dataType = "boolean")
    private String color;

    @ApiModelProperty(value = "是否选中",dataType = "boolean")
    private boolean ifSelect;

    @ApiModelProperty(value = "标签名称",dataType = "String")
    private String labelName;

    public boolean isIfSystem() {
        return ifSystem;
    }

    public void setIfSystem(boolean ifSystem) {
        this.ifSystem = ifSystem;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isIfSelect() {
        return ifSelect;
    }

    public void setIfSelect(boolean ifSelect) {
        this.ifSelect = ifSelect;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
