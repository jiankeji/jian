package com.jian.portal.es.controller;

import com.jian.core.es.server.PetHospitalEsService;
import com.jian.core.model.bean.PetHospital;
import com.jian.core.model.bo.HomePetHospitalBo;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.server.service.PetHospitalService;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.jian.core.model.ResC.*;
import static com.jian.core.model.ResC.API_ERROR_FOLLOW_USER;

@RestController
@RequestMapping(value="api1/petHospitalMsg")
@Api(value = "api1/petHospitalMsg",description = "宠物医院信息")
public class PetHospitalEsController {
    private final static Log log = LogFactory.getLog(PetHospitalEsController.class);

    @Autowired
    PetHospitalEsService petHospitalEsService;

    @Autowired
    PetHospitalService petHospitalService;

    @GetMapping(value = "savePet")
    public void savePet(){
        List<PetHospital> list = petHospitalService.getListPetHospital();
        for (PetHospital pet :list){
            petHospitalEsService.PetHospitalEsSave(pet);
        }
    }

    @PostMapping(value="/search",produces="application/json; charset=UTF-8")
    @ApiOperation(value="宠物医院搜索/快速挂号", notes="宠物医院搜索/快速挂号", response=ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=HomePetHospitalBo.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"),  @ApiResponse(code=API_PARAMS_ERROR,message="获取参数错误")})
    public ResultVo<List<HomePetHospitalBo>> getHomePage(HttpServletRequest request, @ApiParam(value = "页码",required = true)@RequestParam(value = "pageNum",required = true)int pageNum,
                                                         @ApiParam(value = "每页条数",required = true)@RequestParam(value = "pageSize",required = true)int pagesize,
                                                         @ApiParam(value = "纬度 --精确到小数点后6位",required=true)@RequestParam(value = "lat",required = true)Double lat,
                                                         @ApiParam(value = "经度 --精确到小数点后6位",required=true)@RequestParam(value = "lon",required = true)Double lon,
                                                         @ApiParam(value = "搜索内容",required = true)@RequestParam(value = "searchContext",required = true)String searchContext){
        ResultVo<List<HomePetHospitalBo>> resultVo = new ResultVo<>(new ArrayList<>());
        try {
            pageNum =pageNum<=0?1:pageNum;
            List<HomePetHospitalBo> homePetHospitalBos = new ArrayList<>();
            homePetHospitalBos = petHospitalEsService.getHomePetHopsptialAll(pageNum,pagesize,lat,lon,searchContext);
            resultVo.setObj(homePetHospitalBos);
            return ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
        }catch (Exception e){
            log.error("【搜索异常】",e);
            return ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
        }
    }
}
