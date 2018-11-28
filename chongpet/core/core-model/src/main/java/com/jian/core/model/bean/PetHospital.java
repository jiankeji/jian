package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.util.Objects;

@Document(indexName = "petview",type = "pet")
@ApiModel(description = "宠物医院信息")
public class PetHospital {

    @Id
    @ApiModelProperty(value = "宠物医院主键",dataType ="Integer")
    private int sid;

    @ApiModelProperty(value = "店铺图片",dataType = "String" )
    private String shopPic;

    @ApiModelProperty(value = "店铺地址",dataType = "String" )
    private String shopAddress;

    @ApiModelProperty(value = "店铺名称",dataType = "String" )
    private String shopName;

    @ApiModelProperty(value = "距离定位地址",dataType = "String" )
    private  @GeoPointField String shopPosition;

    @ApiModelProperty(value = "店铺营业时间 --暂时拉取不到--",dataType = "String" )
    private String shopOpen;

    @ApiModelProperty(value = "店铺等级",dataType = "String" )
    private String shopLevel;

    @ApiModelProperty(value = "店铺详情页地址",dataType = "String" )
    private String shopUrl;

    @ApiModelProperty(value = "服务评分",dataType = "float" )
    private float serviceNum;

    @ApiModelProperty(value = "店铺类型",dataType = "String" )
    private String ctype;

    @ApiModelProperty(value = "城市区域ID",dataType = "String" )
    private String regionId;

    @ApiModelProperty(value = "城市街道Id",dataType = "String" )
    private String locationId;

    @ApiModelProperty(value = "街道名称",dataType = "String" )
    private String location;

    @ApiModelProperty(value = "店铺状态",dataType = "status" )
    private int isstatus;

    @ApiModelProperty(value = "划算分值",dataType = "float" )
    private float paying;

    @ApiModelProperty(value = "环境分值",dataType = "float" )
    private float science;

    @ApiModelProperty(value = "城市ID",dataType = "int" )
    private int cityId;

    @ApiModelProperty(value = "城市名称",dataType = "String" )
    private String cityName;

    @ApiModelProperty(value = "大众使用的ID",dataType = "int" )
    private int shopId;


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

    public String getShopPosition() {
        return shopPosition;
    }

    public void setShopPosition(String shopPosition) {
        this.shopPosition = shopPosition;
    }

    public String getShopOpen() {
        return shopOpen;
    }

    public void setShopOpen(String shopOpen) {
        this.shopOpen = shopOpen;
    }

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public float getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(float serviceNum) {
        this.serviceNum = serviceNum;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
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

    public int getIsstatus() {
        return isstatus;
    }

    public void setIsstatus(int isstatus) {
        this.isstatus = isstatus;
    }

    public float getPaying() {
        return paying;
    }

    public void setPaying(float paying) {
        this.paying = paying;
    }

    public float getScience() {
        return science;
    }

    public void setScience(float science) {
        this.science = science;
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

    public int getShopId() {
        return shopId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetHospital that = (PetHospital) o;
        return sid == that.sid &&
                Float.compare(that.serviceNum, serviceNum) == 0 &&
                isstatus == that.isstatus &&
                Float.compare(that.paying, paying) == 0 &&
                Float.compare(that.science, science) == 0 &&
                cityId == that.cityId &&
                shopId == that.shopId &&
                Double.compare(that.distance, distance) == 0 &&
                Objects.equals(shopPic, that.shopPic) &&
                Objects.equals(shopAddress, that.shopAddress) &&
                Objects.equals(shopName, that.shopName) &&
                Objects.equals(shopPosition, that.shopPosition) &&
                Objects.equals(shopOpen, that.shopOpen) &&
                Objects.equals(shopLevel, that.shopLevel) &&
                Objects.equals(shopUrl, that.shopUrl) &&
                Objects.equals(ctype, that.ctype) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(locationId, that.locationId) &&
                Objects.equals(location, that.location) &&
                Objects.equals(cityName, that.cityName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sid, shopPic, shopAddress, shopName, shopPosition, shopOpen, shopLevel, shopUrl, serviceNum, ctype, regionId, locationId, location, isstatus, paying, science, cityId, cityName, shopId, distance);
    }

    @Override
    public String toString() {
        return "PetHospital{" +
                "sid=" + sid +
                ", shopPic='" + shopPic + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopPosition='" + shopPosition + '\'' +
                ", shopOpen='" + shopOpen + '\'' +
                ", shopLevel='" + shopLevel + '\'' +
                ", shopUrl='" + shopUrl + '\'' +
                ", serviceNum=" + serviceNum +
                ", ctype='" + ctype + '\'' +
                ", regionId='" + regionId + '\'' +
                ", locationId='" + locationId + '\'' +
                ", location='" + location + '\'' +
                ", isstatus=" + isstatus +
                ", paying=" + paying +
                ", science=" + science +
                ", cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", shopId=" + shopId +
                ", distance=" + distance +
                '}';
    }
}
