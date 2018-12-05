package com.jian.portal.clt.controller;

import com.alibaba.fastjson.JSON;
import com.jian.core.model.bean.Activity;
import com.jian.core.model.bo.ActivityBo;
import com.jian.core.model.bo.ActivityDetailsBo;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.server.service.ActivityService;
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

@RestController
@RequestMapping(value = "apil/activity")
@Api(value = "apil/activity", description = "活动")
public class ActivityController {
    private final static Log log = LogFactory.getLog(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    @PostMapping(value = "/getActivityList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "宠圈活动页入口", notes = "宠圈活动页入口", response = ResultVo.class, position = 1)
    @ApiResponses({@ApiResponse(code = API_SUCCESS, message = "操作成功", response = ActivityBo.class),
            @ApiResponse(code = API_EXCEPTION, message = "操作异常"), @ApiResponse(code = API_PARAMS_ERROR, message = "获取参数错误")})
    public ResultVo<List<ActivityBo>> getActivityList(HttpServletRequest request, @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum", required = true) int pageNum,
                                                      @ApiParam(value = "每页条数", required = true) @RequestParam(value = "pageSize", required = true) int pageSize) {
        List<ActivityBo> list = new ArrayList<>();
        ResultVo<List<ActivityBo>> resultVo = new ResultVo<>(list);
        try {
            pageSize = pageNum < 0 ? 0 : pageNum * pageSize - 1;
            pageNum = (pageNum - 1) * pageSize;
            List<Activity> activityList = activityService.getRedisActivityAll(pageNum, pageSize);
            for (Activity activity : activityList) {
                ActivityBo activityBo = JSON.parseObject(JSON.toJSONString(activity), ActivityBo.class);
                list.add(activityBo);
            }
            resultVo.setObj(list);
            return ResultUtil.setResultVoDesc(resultVo, API_SUCCESS);
        } catch (Exception e) {
            log.error("【获取活动入口页异常】", e);
            return ResultUtil.setResultVoDesc(resultVo, API_EXCEPTION);
        }
    }

    @PostMapping(value = "/getActivityDetail", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "活动详情", notes = "活动详情", response = ResultVo.class, position = 1)
    @ApiResponses({@ApiResponse(code = API_SUCCESS, message = "操作成功", response = ActivityBo.class),
            @ApiResponse(code = API_EXCEPTION, message = "操作异常"), @ApiResponse(code = API_PARAMS_ERROR, message = "获取参数错误")})
    public ResultVo<ActivityDetailsBo> getActivityDetail(HttpServletRequest request,@ApiParam(value = "活动ID",required = true)
    @RequestParam(value = "activityId",required = true)int activityId){
        ActivityDetailsBo bo = new ActivityDetailsBo();
        ResultVo<ActivityDetailsBo> resultVo = new ResultVo<>(bo);
        //活动信息
        try {
            Activity activity = activityService.getActivity(activityId);
            bo = JSON.parseObject(JSON.toJSONString(activity),ActivityDetailsBo.class);
            //获取bo以后 这边还要获取这个活动的评论
            resultVo.setObj(bo);
            return ResultUtil.setResultVoDesc(resultVo, API_SUCCESS);
        }catch (Exception e){
            log.error("【获取活动详情获取异常】", e);
            return ResultUtil.setResultVoDesc(resultVo, API_EXCEPTION);
        }
    }
}
