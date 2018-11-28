package com.jian.core.model.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "首页宠物医院信息")
public class HomePetHospitalBo {

    @ApiModelProperty(value = "宠物医院主键",dataType ="Integer")
    private int sid;

    @ApiModelProperty(value = "店铺图片",dataType = "String" )
    private String shopPic;

    @ApiModelProperty(value = "店铺地址",dataType = "String" )
    private String shopAddress;

    @ApiModelProperty(value = "店铺名称",dataType = "String" )
    private String shopName;

    @ApiModelProperty(value = "店铺等级",dataType = "String" )
    private String shopLevel;

    @ApiModelProperty(value = "城市街道Id",dataType = "String" )
    private String locationId;

    @ApiModelProperty(value = "街道名称",dataType = "String" )
    private String location;

    @ApiModelProperty(value = "城市ID",dataType = "int" )
    private int cityId;

    @ApiModelProperty(value = "城市名称",dataType = "String" )
    private String cityName;

    @ApiModelProperty(value = "距离公里数单位KM",dataType = "double")
    private double distance;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
