package com.jian.core.model.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "保单")
@Document(indexName = "slip",type = "slipview")
public class GuaranteeSlip {

    @ApiModelProperty(value = "保单Id",dataType = "int")
    @Id
    private int sid;

    private int uid;

    private int pid;

    private Date beginTime;

    private Date endTime;

    private int petType;

    private int lineage;

    private long dogNum;

    private int petId;

    private Date birthdate;

    private String diseasesHistory;

    private boolean ifSterilization;

    private String petImgUrl;

    private String UserName;

    private int papersType;

    private String papersNum;

    private String phoneNum;

    private String email;

    private BigDecimal price;

    private int isstatus;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPetType() {
        return petType;
    }

    public void setPetType(int petType) {
        this.petType = petType;
    }

    public int getLineage() {
        return lineage;
    }

    public void setLineage(int lineage) {
        this.lineage = lineage;
    }

    public long getDogNum() {
        return dogNum;
    }

    public void setDogNum(long dogNum) {
        this.dogNum = dogNum;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getDiseasesHistory() {
        return diseasesHistory;
    }

    public void setDiseasesHistory(String diseasesHistory) {
        this.diseasesHistory = diseasesHistory;
    }

    public boolean isIfSterilization() {
        return ifSterilization;
    }

    public void setIfSterilization(boolean ifSterilization) {
        this.ifSterilization = ifSterilization;
    }

    public String getPetImgUrl() {
        return petImgUrl;
    }

    public void setPetImgUrl(String petImgUrl) {
        this.petImgUrl = petImgUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getPapersType() {
        return papersType;
    }

    public void setPapersType(int papersType) {
        this.papersType = papersType;
    }

    public String getPapersNum() {
        return papersNum;
    }

    public void setPapersNum(String papersNum) {
        this.papersNum = papersNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getIsstatus() {
        return isstatus;
    }

    public void setIsstatus(int isstatus) {
        this.isstatus = isstatus;
    }
}
