package com.jian.portal.clt.controller;

import com.alibaba.fastjson.JSON;
import com.jian.core.es.server.PetNewsEsService;
import com.jian.core.model.bean.PetNews;
import com.jian.core.model.bo.PetFrontPageBo;
import com.jian.core.model.bo.PetFrontPageListBo;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.server.service.LoginService;
import com.jian.core.server.service.PetNewsService;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jian.core.model.ResC.*;

@SuppressWarnings("ALL")
@Api(value = "api1/petNews",description = "宠物头条")
@RequestMapping(value = "api1/petNews")
@RestController
public class PetNewsController {
    private final static Log log = LogFactory.getLog(PetNewsController.class);

    @Autowired
    private PetNewsService petNewsService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PetNewsEsService petNewsEsService;


    @PostMapping(value="/getFrontPage",produces="application/json; charset=UTF-8")
    @ApiOperation(value="获取宠物头条", notes="获取宠物头条", response=ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=PetFrontPageBo.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"), @ApiResponse(code=API_PARAMS_ERROR,message="获取参数错误")})
    public ResultVo<List<PetFrontPageBo>> getFrontPage(HttpServletRequest request, @ApiParam(value = "页码",required = true)@RequestParam(value = "pageNum",required = true)int pageNum,
                                                           @ApiParam(value = "每页条数",required = true)@RequestParam(value = "pageSize",required = true)int pagesize){
        ResultVo<List<PetFrontPageBo>> resultVo = new ResultVo<>(new ArrayList<>());
        pagesize = pageNum<0?0:pageNum*pagesize-1;
        pageNum =(pageNum-1)*pagesize;

        try {
            List<PetFrontPageBo> listBos = petNewsService.getFrontPageBo(pagesize,pageNum);
            resultVo.setObj(listBos);
            return  ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
        }catch (Exception e){
            log.error("【宠物头条获取异常】",e);
            return ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
        }
    }

//    @GetMapping(value="/saveNews",produces="application/json; charset=UTF-8")
//    public void saveNews() {
//        List<PetNews> list = petNewsService.getAll();
//        for (PetNews p:list) {
//            petNewsEsService.saveNews(p);
//        }
//    }
//
//    @GetMapping(value="/setNews",produces="application/json; charset=UTF-8")
//    public void setNews() throws ParseException {
//        PetNews p1 =petNewsEsService.getNews(1);
//        p1.setCreateTime(new Date());
//        PetNews p2 = petNewsEsService.getNews(2);
//        p2.setCreateTime(new Date());
//        PetNews p3 = petNewsEsService.getNews(3);
//        p2.setCreateTime(new Date());
//        List<PetNews> list = new ArrayList<>();
//        list.add(p1);
//        list.add(p2);
//        list.add(p3);
//        petNewsService.saveRedisPetNew(JSON.toJSONString(list));
//    }
}
