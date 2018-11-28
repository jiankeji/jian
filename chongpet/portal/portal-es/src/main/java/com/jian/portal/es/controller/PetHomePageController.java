package com.jian.portal.es.controller;

import com.jian.core.es.server.PetHospitalEsService;
import com.jian.core.model.bean.PetHospital;
import com.jian.core.model.bo.HomePageBo;
import com.jian.core.model.bo.HomePetHospitalBo;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
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

import static com.jian.core.model.ResC.API_ERROR_LOGIN_USER;
import static com.jian.core.model.ResC.API_ERROR_USER_NULL;
import static com.jian.core.model.ResC.API_SUCCESS;

@RestController
@RequestMapping(value = "apil/homePage")
@Api(value = "api1/homePage",description = "首页")
public class PetHomePageController {
    private final static Log log = LogFactory.getLog(PetHomePageController.class);

    @Autowired
    private PetHospitalEsService petHospitalEsService;

    @PostMapping(value="/getHomePage",produces="application/json; charset=UTF-8")
    @ApiOperation(value="获取首页信息", notes="关注操作", response=ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录"), @ApiResponse(code=API_ERROR_USER_NULL,message="用户不存在")})
    public ResultVo<HomePageBo> getHomePage(HttpServletRequest request, @ApiParam(value = "页码",required = true)@RequestParam(value = "pageSize",required = true)int pagesize,
                                                  @ApiParam(value = "每页条数",required = true)@RequestParam(value = "pageNum",required = true)int pageNum,
                                                  @ApiParam(value = "纬度 --精确到小数点后6位",required=true)@RequestParam(value = "lat",required = true)Double lat,
                                                  @ApiParam(value = "经度 --精确到小数点后6位",required=true)@RequestParam(value = "lon",required = true)Double lon){
        ResultVo<HomePageBo> resultVo = new ResultVo<>(new HomePageBo());
        List<HomePetHospitalBo> homePetHospitalBos = new ArrayList<>();
        HomePageBo homePageBo = new HomePageBo();
        pagesize =pagesize>=0?1:pagesize;
        if (lat ==0 && lon ==0){//如果经纬度都是0那么默认给北京天安门的地址
            lat = 39.917591;
            lon = 116.403694;
        }
        try {



            homePetHospitalBos = petHospitalEsService.getHomePetHopsptialAll(pagesize,pageNum,lat,lon);
            homePageBo.setHomePetHospitalBoList(homePetHospitalBos);
            resultVo.setObj(homePageBo);
            return ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
        }catch (Exception e){
            log.error("【首页内容获取失败】",e);
            return ResultUtil.setResultVoDesc(resultVo,-1);
        }
    }
}
