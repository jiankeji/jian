package com.jian.portal.es.controller;

import com.jian.core.es.server.PetHospitalEsService;
import com.jian.core.model.bean.PetHospital;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.server.service.PetHospitalService;
import io.swagger.annotations.*;
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
    @ApiOperation(value="宠物医院搜索", notes="宠物医院搜索", response=ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录"), @ApiResponse(code=API_ERROR_USER_NULL,message="用户不存在")})
    public ResultVo<List<PetHospital>> getHomePage(HttpServletRequest request, @ApiParam(value = "页码",required = true)@RequestParam(value = "pageSize",required = true)int pagesize,
                                                   @ApiParam(value = "每页条数",required = true)@RequestParam(value = "pageNum",required = true)int pageNum){
        ResultVo<List<PetHospital>> resultVo = new ResultVo<>(new ArrayList<>());
        return  resultVo;
    }
}
