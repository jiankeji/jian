package com.jian.portal.clt.controller;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.InsurancePlan;
import com.jian.core.model.bean.InsuranceProducts;
import com.jian.core.model.bean.PetInsurance;
import com.jian.core.model.bo.*;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.server.service.*;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.jian.core.model.ResC.*;

@SuppressWarnings("ALL")
@Api(value = "api1/insurance",description = "保险详细信息,投保")
@RestController
@RequestMapping(value = "api1/insurance")
public class InsuranceDetailController {
    private final static Log log = LogFactory.getLog(InsuranceDetailController.class);

    @Autowired
    private PetInsuranceService petInsuranceService;

    @Autowired
    private InsurancePlanService insurancePlanService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PlanAndGuaranteeService planAndGuaranteeService;

    @Autowired
    private InsuranceProductsService insuranceProductsService;


    @PostMapping(value = "/geInsuranceDetail", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取保险产品详细", notes = "获取保险产品详细", response = ResultVo.class, position = 1)
    @ApiResponses({@ApiResponse(code = API_SUCCESS, message = "操作成功", response = ActivityBo.class),
            @ApiResponse(code = API_EXCEPTION, message = "操作异常"), @ApiResponse(code = API_PARAMS_ERROR, message = "获取参数错误"),
            @ApiResponse(code = API_ERROR_LOGIN_USER, message = "用户不存在或未登录")})
    public ResultVo<InsuranceDetailBo> getInsuranceDetail(HttpServletRequest request,
                                                          @ApiParam(value = "保险ID", required = true) @RequestParam(value = "insuranceId", required = true) int insuranceId) {
        InsuranceDetailBo bo = new InsuranceDetailBo();
        ResultVo<InsuranceDetailBo> resultVo = new ResultVo<>(bo);
        int userId = loginService.getUserIdRedis(request.getHeader("token"));
        if (userId == 0) {
            return ResultUtil.setResultVoDesc(resultVo, API_ERROR_LOGIN_USER);
        }
        try {
            //先去redis中查询
            bo = petInsuranceService.getDetails(insuranceId);
            if (bo != null) {
                //bo.set......此处获取保险的相关提问与回答
                resultVo.setObj(bo);
                return ResultUtil.setResultVoDesc(resultVo, API_SUCCESS);
            } else {
                PetInsurance petInsurance = petInsuranceService.getInsurance(insuranceId);
                if (petInsurance != null) {
                    bo = JSON.parseObject(JSON.toJSONString(petInsurance), InsuranceDetailBo.class);
                    if (petInsurance.getProducts() != null && !"".equals(petInsurance.getProducts())) {
                        //获取保单计划Id
                        //初始化一个新的计划bo 用户存放数据
                        List<InsurancePlanBo> bolist = new ArrayList<>();
                        //查询出计划的先关信息
                        List<InsurancePlanBo> planBoList = insurancePlanService.getRedisPlan(insuranceId);

                        //现在controller 里面处理 后面再改  现在的逻辑有些问题
                        for (InsurancePlanBo planBo : planBoList) {
                            //获取相关计划的保障信息
                            List<PlanAndGuaranteeBo> list = planAndGuaranteeService.getBoAll(planBo.getSid());
                            planBo.setPlanAndGuaranteeBoList(list);
                            bolist.add(planBo);
                        }
                        bo.setInsurancePlanBoList(bolist);
                        //获取产品条款
                        String[] products = petInsurance.getProducts().split(",");

                        List<InsuranceProductsBo> productsBoList = new ArrayList<>();
                        for (String id : products) {
                            //获取保险的产品条款
                            InsuranceProducts insuranceProducts =insuranceProductsService.getProducts(Integer.parseInt(id));
                            if (insuranceProducts != null) {
                                InsuranceProductsBo productsBo = JSON.parseObject(JSON.toJSONString(insuranceProducts), InsuranceProductsBo.class);
                                productsBoList.add(productsBo);
                            }
                        }
                        bo.setInsuranceProductsBoList(productsBoList);

                        //在获取提问与回答之前先存入redis中 方便以后直接从reids中获取
                        petInsuranceService.setDetails(JSON.toJSONString(bo),insuranceId);

                        //bo.set......此处获取保险的相关提问与回答
                    }
                    resultVo.setObj(bo);
                    return ResultUtil.setResultVoDesc(resultVo, API_SUCCESS);
                }
            }
        } catch (Exception e) {
            log.error("【获取保险详细信息异常】", e);
            return ResultUtil.setResultVoDesc(resultVo, API_EXCEPTION);
        }
        return ResultUtil.setResultVoDesc(resultVo, API_SUCCESS);
    }
}
