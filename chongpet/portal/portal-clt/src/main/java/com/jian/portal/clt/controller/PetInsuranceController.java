package com.jian.portal.clt.controller;

import com.jian.core.model.bo.HomeBannerBo;
import com.jian.core.model.bo.PetInsuranceBo;
import com.jian.core.model.bo.PetInsurancePageBo;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.server.service.BannerService;
import com.jian.core.server.service.PetInsuranceService;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static com.jian.core.model.ResC.API_EXCEPTION;
import static com.jian.core.model.ResC.API_PARAMS_ERROR;
import static com.jian.core.model.ResC.API_SUCCESS;

@Api(value = "api1/insurance",description = "保险页信息")
@RestController
@RequestMapping(value = "api1/insurance")
public class PetInsuranceController {
    private final static Log log = LogFactory.getLog(PetInsuranceController.class);

    @Autowired
    private PetInsuranceService petInsuranceService;

    @Autowired
    private BannerService bannerService;

    @PostMapping(value="/getInsurancePage",produces="application/json; charset=UTF-8")
    @ApiOperation(value="获取保险首页信息", notes="获取保险首页信息", response=ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=PetInsurancePageBo.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"), @ApiResponse(code=API_PARAMS_ERROR,message="获取参数错误")})
    public ResultVo<PetInsurancePageBo> getInsurancePage(@ApiParam(value = "页码",required = true)@RequestParam(value = "pageNum",required = true)int pageNum,
                                                         @ApiParam(value = "每页条数",required = true)@RequestParam(value = "pageSize",required = true)int pagesize){
        PetInsurancePageBo petInsurancePageBo = new PetInsurancePageBo();
        ResultVo<PetInsurancePageBo> resultVo = new ResultVo<>(petInsurancePageBo);
        pagesize = pageNum<0?0:pageNum*pagesize-1;
        pageNum =(pageNum-1)*pagesize;

        try {
            //保险页banner   暂时首页和保险页的banner用同一个 不做区分
            List<HomeBannerBo> homeBannerBos =bannerService.getHomePageBannerAll();
            petInsurancePageBo.setHomeBannerList(homeBannerBos);

            //保险页保险信息
            List<PetInsuranceBo> petInsuranceBos = petInsuranceService.getHomeInsuranceAll(pagesize,pageNum);
            petInsurancePageBo.setPetInsuranceList(petInsuranceBos);

            resultVo.setObj(petInsurancePageBo);
            return ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
        }catch (Exception e){
            log.error("【保险页内容获取失败】",e);
            return ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
        }
    }

    @GetMapping(value="/setInsuranc",produces="application/json; charset=UTF-8")
    public void setInsuranc() throws ParseException {
        petInsuranceService.saveInsurance();
    }
}
