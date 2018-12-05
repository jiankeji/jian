package com.jian.portal.clt.controller;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.PetLable;
import com.jian.core.model.bean.PetMsg;
import com.jian.core.model.bo.MyPetListBo;
import com.jian.core.model.bo.PetDetailsBo;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.server.service.LoginService;
import com.jian.core.server.service.PetMsgService;
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
import java.util.Set;

import static com.jian.core.model.ResC.*;

@SuppressWarnings("ALL")
@Api(value = "api1/petMsg",description = "宠物信息录入/修改/详情/标签")
@RestController
@RequestMapping(value = "api1/petMsg")
public class PetMsgController {
    private static final Log log = LogFactory.getLog(PetMsgController.class);

    @Autowired
    private PetMsgService petMsgService;

    @Autowired
    private LoginService loginService;


    @PostMapping(value="/setPetMsg",produces="application/json; charset=UTF-8")
    @ApiOperation(value="宠物信息录入/与更新", notes="宠物信息录入", response=ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=Integer.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"),  @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录")})
    public ResultVo<Integer> setPetMsg(HttpServletRequest request, @ApiParam(value = "宠物信息",required = true)@RequestParam(value = "petMsg",required = true)String petMsg){
        ResultVo<Integer> resultVo = new ResultVo<>(-1);
        int userId = loginService.getUserIdRedis(request.getHeader("token"));
        if (userId == 0){
            return ResultUtil.setResultVoDesc(resultVo,API_ERROR_LOGIN_USER);
        }
        PetMsg petMsgString =JSON.parseObject(petMsg,PetMsg.class);
        if (petMsgString.getSid() <= 0){
            try {
                int num = petMsgService.savePetMsg(petMsgString,userId);
                if (num>0){
                    resultVo.setObj(1);
                    return ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
                }
            }catch (Exception e){
                log.error("【宠物信息录入数据库失败】",e);
                return  ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
            }

        }
        if (petMsgString.getSid()>0){
            try {
                int num = petMsgService.updatePetMsg(petMsgString,userId);
                if (num >0){
                    resultVo.setObj(1);
                    return ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
                }
            }catch (Exception e){
                log.error("【宠物信息更新数据库失败】",e);
                return  ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
            }
        }

        return ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
    }


    @PostMapping(value="/getPetMsg",produces="application/json; charset=UTF-8")
    @ApiOperation(value="修改宠物信息获取", notes="修改宠物信息获取", response=ResultVo.class,position=2)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=PetMsg.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"),  @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录")})
    public ResultVo<PetMsg> getPetMsg(HttpServletRequest request, @ApiParam(value = "宠物ID",required = true)@RequestParam(value = "petId",required = true)int petId) {

        ResultVo<PetMsg> resultVo = new ResultVo<>(new PetMsg());
        int userId = loginService.getUserIdRedis(request.getHeader("token"));
        if (userId == 0) {
            return ResultUtil.setResultVoDesc(resultVo, API_ERROR_LOGIN_USER);
        }
        try {
            PetMsg petMsg = petMsgService.getPetMsg(userId,petId);
            resultVo.setObj(petMsg);
            return ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
        }catch (Exception e){
            log.error("【宠物信息获取REDIS异常】",e);
            return ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
        }
    }


    @PostMapping(value="/getPetLable",produces="application/json; charset=UTF-8")
    @ApiOperation(value="宠物标签获取", notes="宠物标签获取", response=ResultVo.class,position=3)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=PetLable.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"),  @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录")})
    public ResultVo<List<PetLable>> getPetLable(HttpServletRequest request, @ApiParam(value = "宠物ID",required = true)@RequestParam(value = "petId",required = true)int petId) {
        ResultVo<List<PetLable>> resultVo = new ResultVo<>(new ArrayList<>());
        int userId = loginService.getUserIdRedis(request.getHeader("token"));
        if (userId == 0) {
            return ResultUtil.setResultVoDesc(resultVo, API_ERROR_LOGIN_USER);
        }
        try {
            List<PetLable> petLable = petMsgService.getRedisPetLable(petId);
            resultVo.setObj(petLable);
            return  ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
        }catch (Exception e){
            log.error("【reids 获取宠物标签失败】",e);
            return ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
        }
    }

    @PostMapping(value="/savePetLable",produces="application/json; charset=UTF-8")
    @ApiOperation(value="宠物标签保存", notes="宠物标签保存", response=ResultVo.class,position=4)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=Integer.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"),  @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录")})
    public ResultVo<Integer> getPetLable(HttpServletRequest request, @ApiParam(value = "宠物标签",required = true)@RequestParam(value = "petLable",required = true)String petLables) {
        ResultVo<Integer> resultVo = new ResultVo<>(-1);
        int userId = loginService.getUserIdRedis(request.getHeader("token"));
        if (userId == 0) {
            return ResultUtil.setResultVoDesc(resultVo, API_ERROR_LOGIN_USER);
        }
        try {
            petMsgService.setReidsPetLable(petLables,userId);
            return  ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
        }catch (Exception e){
            log.error("【reids 录入宠物标签失败】",e);
            return ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
        }
    }

    @PostMapping(value="/getDetails",produces="application/json; charset=UTF-8")
    @ApiOperation(value="宠物详情页", notes="宠物详情页", response=ResultVo.class,position=5)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=PetDetailsBo.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"),  @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录")})
    public ResultVo<PetDetailsBo> getDetails(HttpServletRequest request, @ApiParam(value = "宠物Id",required = true)@RequestParam(value = "petId",required = true)int petId) {
        ResultVo<PetDetailsBo> resultVo = new ResultVo<>(new PetDetailsBo());
        PetDetailsBo petDetailsBo = new PetDetailsBo();
        int userId = loginService.getUserIdRedis(request.getHeader("token"));
        if (userId == 0) {
            return ResultUtil.setResultVoDesc(resultVo, API_ERROR_LOGIN_USER);
        }

        try {
            PetMsg data = petMsgService.getPetMsg(userId,petId);
            if (data != null){
                petDetailsBo = JSON.parseObject(JSON.toJSONString(data),petDetailsBo.getClass());
                List<PetLable> petLable = petMsgService.getRedisPetLable(petId);
                List<PetLable> petLableList = new ArrayList<>();
                if (petLable!= null && petLable.size()>0){
                    for (PetLable lable:petLable){
                        if (lable.isIfSelect())petLableList.add(lable);
                    }
                }
                petDetailsBo.setPetLableList(petLableList);//插入选中的标签
                //petDetailsBo.setPetBreedpage(); 通过Id查询够的种类和品种名称
                //宠物的动态
                //宠物的相册
                resultVo.setObj(petDetailsBo);
                return  ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
            }

        }catch (Exception e){
            log.error("【查询宠物详情异常】",e);
            return ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
        }
        return ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
    }


    @PostMapping(value="/getMyPet",produces="application/json; charset=UTF-8")
    @ApiOperation(value="我的宠物", notes="我的宠物", response=ResultVo.class,position=5)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=MyPetListBo.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"),  @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录")})
    public ResultVo<List<MyPetListBo>> getMyPet(HttpServletRequest request) {
        List<MyPetListBo> list = new ArrayList<>();
        ResultVo<List<MyPetListBo>> resultVo = new ResultVo<>(list);
        int userId = loginService.getUserIdRedis(request.getHeader("token"));
        if (userId == 0) {
            return ResultUtil.setResultVoDesc(resultVo, API_ERROR_LOGIN_USER);
        }
        try {
            Set<Object> set = petMsgService.petMsgZset(userId);
            if (set != null && set.size() > 0) {
                for (Object ins : set) {
                    int id = Integer.parseInt((String) ins);
                    MyPetListBo bo = new MyPetListBo();
                    PetMsg petMsg = petMsgService.getPetMsg(userId, id);
                    bo = JSON.parseObject(JSON.toJSONString(petMsg), MyPetListBo.class);
                    //bo.setPetBreedpage(); 查询宠物的品种
                    list.add(bo);
                }
                resultVo.setObj(list);
                return ResultUtil.setResultVoDesc(resultVo, API_SUCCESS);
            }
        } catch (Exception e) {
            log.error("【查询-我的宠物-异常】", e);
            return ResultUtil.setResultVoDesc(resultVo, API_SUCCESS);
        }
        return ResultUtil.setResultVoDesc(resultVo, API_SUCCESS);
    }
}
