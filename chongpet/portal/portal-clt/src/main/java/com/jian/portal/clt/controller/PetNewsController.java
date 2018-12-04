package com.jian.portal.clt.controller;

import com.alibaba.fastjson.JSON;
import com.jian.core.es.server.PetNewsEsService;
import com.jian.core.model.bean.PetNews;
import com.jian.core.model.bo.PetFrontPageBo;
import com.jian.core.model.bo.PetFrontPageListBo;
import com.jian.core.model.bo.PetNewsBo;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.server.dao.PetNewsDao;
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
                                                           @ApiParam(value = "每页条数",required = true)@RequestParam(value = "pageSize",required = true)int pageSize){
        ResultVo<List<PetFrontPageBo>> resultVo = new ResultVo<>(new ArrayList<>());
        pageSize = pageNum<0?0:pageNum*pageSize-1;
        pageNum =(pageNum-1)*pageSize;

        try {
            List<PetFrontPageBo> listBos = petNewsService.getFrontPageBo(pageSize,pageNum);
            resultVo.setObj(listBos);
            return  ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
        }catch (Exception e){
            log.error("【宠物头条获取异常】",e);
            return ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
        }
    }

    @PostMapping(value="/getPetNewsMsg",produces="application/json; charset=UTF-8")
    @ApiOperation(value="查看宠物新闻详情", notes="查看宠物新闻详情", response=ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=PetNewsBo.class),
            @ApiResponse(code=API_EXCEPTION,message="操作异常"), @ApiResponse(code=API_PARAMS_ERROR,message="获取参数错误")})
    public ResultVo<PetNewsBo> getPetNewsMsg(HttpServletRequest request, @ApiParam(value = "新闻ID",required = true)@RequestParam(value = "newId",required = true)int newId){

        ResultVo<PetNewsBo> resultVo = new ResultVo<>(new PetNewsBo());
        try {
            PetNews petNews = petNewsEsService.getNews(newId);
            //计算浏览次数
            petNews.setPageView(petNews.getPageView()+1);
            petNewsEsService.saveNews(petNews);
            //获取的内容插入到bo模版中
            if (petNews != null){
                PetNewsBo bo = JSON.parseObject(JSON.toJSONString(petNews),PetNewsBo.class);
                //这边还要插入评论回复的内容
                resultVo.setObj(bo);
                return ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
            }
        }catch (Exception e){
            log.error("【查询新闻详情内容失败】",e);
            return  ResultUtil.setResultVoDesc(resultVo,API_EXCEPTION);
        }
        return ResultUtil.setResultVoDesc(resultVo,API_SUCCESS);
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
