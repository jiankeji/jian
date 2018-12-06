package com.jian.portal.es.controller;

import com.jian.core.model.bo.HomePageBo;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.server.service.GuaranteeSlipService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Date;

import static com.jian.core.model.ResC.*;

@Api(value = "api1/slip",description = "保单")
@RestController
@RequestMapping(value = "api1/slip")
public class GuaranteeSlipController {

    @Autowired
    private GuaranteeSlipService guaranteeSlipService;

    @PostMapping(value="/setSlip",produces="application/json; charset=UTF-8")
    @ApiOperation(value="保单信息保存", notes="保单信息保存", response=ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=Integer.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"), @ApiResponse(code=API_PARAMS_ERROR,message="参数错误"),
            @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录")})
    public ResultVo<Integer> setSlip(HttpServletRequest request,@ApiParam(value = "计划ID",required = true)@RequestParam(value = "planId",required = true)int planId,
                                     @ApiParam(value = "开始时间",required = true)@RequestParam(value = "beginTime",required = true)Date beginTime,
                                     @ApiParam(value = "结束时间",required = true)@RequestParam(value = "endTime",required = true)Date endTime,
                                     @ApiParam(value = "宠物类型  1：狗  2：猫",required = true)@RequestParam(value = "petType",required = true)int petType,
                                     @ApiParam(value = "是否是纯种 1:纯种 2：非纯种",required = true)@RequestParam(value = "lineage",required = true)int lineage,
                                     @ApiParam(value = "犬证号码",required = true)@RequestParam(value = "dogNum",required = true)long dogNum,
                                     @ApiParam(value = "宠物品种",required = true)@RequestParam(value = "petId",required = true)int petId,
                                     @ApiParam(value = "宠物生日",required = true)@RequestParam(value = "birthdate",required = true)Date birthdate,
                                     @ApiParam(value = "疾病史",required = true)@RequestParam(value = "diseasesHistory",required = true)String diseasesHistory,
                                     @ApiParam(value = "是否绝育",required = true)@RequestParam(value = "ifSterilization",required = true)boolean ifSterilization,
                                     @ApiParam(value = "宠物照片",required = true)@RequestParam(value = "petImgUrl",required = true)String petImgUrl,
                                     @ApiParam(value = "投保主人信息",required = true)@RequestParam(value = "UserName",required = true)String UserName,
                                     @ApiParam(value = "1--身份证 2护照 3军官证",required = true)@RequestParam(value = "papersType",required = true)int papersType,
                                     @ApiParam(value = "证件编号",required = true)@RequestParam(value = "papersNum",required = true)String papersNum,
                                     @ApiParam(value = "电话号码",required = true)@RequestParam(value = "phoneNum",required = true)String phoneNum,
                                     @ApiParam(value = "电子邮箱",required = true)@RequestParam(value = "email",required = true)String email,
                                     @ApiParam(value = "保费总价",required = true)@RequestParam(value = "price",required = true)BigDecimal price){
        ResultVo<Integer> resultVo = new ResultVo<>(-1);
        return resultVo;
    }
}
