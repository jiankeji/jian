package com.jian.portal.reg.controller;

import com.jian.core.model.ResC;
import com.jian.core.model.bean.User;
import com.jian.core.model.bo.UserAttentionBo;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.model.util.StringUtil;
import com.jian.core.server.service.LoginService;
import com.jian.core.server.service.UserAttentionService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@RequestMapping(value = "api1/userAttention")
@RestController
@Api(value = "api1/userAttention",description = "用户关注")
public class UserAttentionController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserAttentionService userAttentionService;

    @ResponseBody
    @RequestMapping(value = "payAttention", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "关注操作", notes = "关注或取消关注某个人<br/>执行操作后的现任状态<br/>异常:-1, 无关系:0, 关注:1, 被关注(粉丝):2, 互相关注:3", response = ResultVo.class, position = 1)
    @ApiResponses({@ApiResponse(code = ResC.API_SUCCESS, message = "加载成功", response = Integer.class),
            @ApiResponse(code = ResC.API_PARAMS_ERROR, message = "参数错误"), @ApiResponse(code = ResC.API_EXCEPTION, message = "操作异常"),
            @ApiResponse(code = ResC.API_ERROR_FOLLOW_USER, message = "自己不能关注自己"),
            @ApiResponse(code = ResC.API_ERROR_USER_NULL, message = "用户不存在或无效")})
    public ResultVo<Integer> payAttention(HttpServletRequest request, @ApiParam(value = "关注或取消关注用户id", required = true) @RequestParam(required = true) String followUserId) {
        ResultVo<Integer> resultVo = new ResultVo<>(-1);
        String token = request.getHeader("token");
        int loginUserId = loginService.getUserIdRedis(token);
        if (StringUtils.isEmpty(followUserId)) {
            return ResultUtil.setResultVoDesc(resultVo, ResC.API_PARAMS_ERROR);
        }
        if (followUserId.equals(String.valueOf(loginUserId))) {
            return ResultUtil.setResultVoDesc(resultVo, ResC.API_ERROR_FOLLOW_USER);
        }
        User user = loginService.getUserRedis(Integer.parseInt(followUserId));
        if(user==null||user.getUserId()<1){
            return ResultUtil.setResultVoDesc(resultVo, ResC.API_ERROR_USER_NULL);
        }

        //执行操作后的现任状态
        //异常:-1, 无关系:0, 关注:1, 被关注(粉丝):2, 互相关注:3
        int rint = userAttentionService.payAttention(loginUserId, Integer.parseInt(followUserId));
        if (rint == -1) {
            return ResultUtil.setResultVoDesc(resultVo, ResC.API_EXCEPTION);
        } else {
            resultVo.setObj(rint);
            return ResultUtil.setResultVoDesc(resultVo, ResC.API_SUCCESS);
        }
    }

    @ResponseBody
    @RequestMapping(value = "attentionList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "关系人列表", notes = "关注人:1，粉丝:2，互粉:3", response = ResultVo.class, position = 11)
    @ApiResponses({@ApiResponse(code = ResC.API_SUCCESS, message = "加载成功", response = UserAttentionBo.class),
            @ApiResponse(code = ResC.API_PARAMS_ERROR, message = "参数错误"), @ApiResponse(code = ResC.API_EXCEPTION, message = "操作异常")})
    public ResultVo<List<UserAttentionBo>> attentionList(HttpServletRequest request, @ApiParam(value = "type 关注人:1，粉丝:2", required = true) @RequestParam(required = true) Integer type,
                                                         @ApiParam(value = "pageNum 页号,1开始", required = true) @RequestParam(required = true) Integer pageNum,
                                                         @ApiParam(value = "pageSize 每页记录1开始", required = true) @RequestParam(required = true) Integer pageSize) {
        ResultVo<List<UserAttentionBo>> resultVo = new ResultVo<>(new ArrayList<>());
        if(type==null|| StringUtil.trimInt(pageNum)<1||StringUtil.trimInt(pageSize)<1){
            return ResultUtil.setResultVoDesc(resultVo, ResC.API_PARAMS_ERROR);
        }
        int loginUserId = loginService.getUserIdRedis(request.getHeader("token"));
        resultVo.setObj(userAttentionService.getAttentionList(loginUserId,type,(pageNum-1)*pageSize,pageNum*pageSize-1));
        return ResultUtil.setResultVoDesc(resultVo, ResC.API_SUCCESS);
    }

    @ResponseBody
    @RequestMapping(value = "userFansList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看用户粉丝,用户关注", notes = "关注人:1，粉丝:2，互粉:3", response = ResultVo.class, position = 11)
    @ApiResponses({@ApiResponse(code = ResC.API_SUCCESS, message = "加载成功", response = UserAttentionBo.class),
            @ApiResponse(code = ResC.API_PARAMS_ERROR, message = "参数错误"), @ApiResponse(code = ResC.API_EXCEPTION, message = "操作异常")})
    public ResultVo<List<UserAttentionBo>> userFansList(HttpServletRequest request, @ApiParam(value = "被查看的用户的ID", required = true) @RequestParam(required = true) int fromUserId,
                                                        @ApiParam(value = "pageNum 页号,1开始", required = true) @RequestParam(required = true) Integer pageNum,
                                                        @ApiParam(value = "pageSize 每页记录1开始", required = true) @RequestParam(required = true) Integer pageSize,
                                                        @ApiParam(value = "查看类型1关注 2 粉丝", required = true) @RequestParam(required = true) int type) {
        ResultVo<List<UserAttentionBo>> resultVo = new ResultVo<>(new ArrayList<>());
        int loginUserId = loginService.getUserIdRedis(request.getHeader("token"));
        resultVo.setObj(userAttentionService.getUserFansList(loginUserId,fromUserId,(pageNum-1)*pageSize,pageNum*pageSize-1,type));
        return ResultUtil.setResultVoDesc(resultVo, ResC.API_SUCCESS);
    }
}
