package com.jian.portal.clt.controller;


import com.jian.core.model.bean.Attention;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.token.Token;
import com.jian.core.model.util.JsonResult;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.server.service.AttentionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.jian.core.model.ResC.*;


/**
 * 用户关注 取关controller
 * @author shen
 *
 */
@SuppressWarnings("ALL")
@Api(value ="api1/attention",description = "用户关注 取关")
@RequestMapping(value="api1/attention")
@RestController
public class AttentionController {

	@Autowired
	private AttentionService attentionService;

	/**
	 *  关注
	 * @param request
	 * @param userId 被关注者
	 * @return
	 */
	//@Token
	@PostMapping(value="/attention",produces="application/json; charset=UTF-8")
    @ApiOperation(value="关注操作", notes="关注操作", response=ResultVo.class,position=1)
    @ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
            @ApiResponse(code=API_ERROR_LOGIN_USER,message="用户未登录"), @ApiResponse(code=API_ERROR_USER_NULL,message="用户不存在"),
            @ApiResponse(code=API_ERROE_FOLLOWING,message="已经关注过了"),@ApiResponse(code = API_ERROR_FOLLOW_USER,message = "自己不能关注自己")})
	public ResultVo<Integer> attention(HttpServletRequest request, @ApiParam(value = "用户ID",required = true)
    @RequestParam(value = "userId",required = true) Integer userId) {
	    ResultVo<Integer> result = new ResultVo<>(-1);
		//JsonResult result = new JsonResult();

		Integer uid = (Integer) request.getSession().getAttribute("userId");
		if(uid == null || uid<=0) {
			//result.setCode(201);
			//result.setMessage("用户未登录");
			return ResultUtil.setResultVoDesc(result,API_ERROR_LOGIN_USER);
		}
		Attention attention = new Attention();
		attention.setUserIdb(uid);
		if(userId==null) {
			return ResultUtil.setResultVoDesc(result,API_ERROR_USER_NULL);
		}
		if(uid==userId) {
			return ResultUtil.setResultVoDesc(result,API_ERROR_FOLLOW_USER);
		}
		attention.setUserIda(userId);
		int status = attentionService.attention(attention);
		if(status==-1) {
			return ResultUtil.setResultVoDesc(result,API_ERROE_FOLLOWING);
		}
		result.setObj(1);
		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
	}

	/**
	 *  取消关注    现在是userIdb要取消关注userIda
	 * @param request
	 * @param userId 被取消关注用户
	 * @return
	 */
	@Token
	@RequestMapping(value="/cancelAttention",method= RequestMethod.POST)
	public JsonResult cancelAttention(HttpServletRequest request, Integer userId) {
		JsonResult result = new JsonResult();

		Integer uid = (Integer) request.getSession().getAttribute("userId");
		if(uid == null || uid<=0) {
			result.setCode(201);
			result.setMessage("用户未登录");
			return result;
		}
		Attention attention = new Attention();
		attention.setUserIdb(uid);
		if(userId==null || userId<=0) {
			result.setCode(202);
			result.setMessage("被取消关注者为空");
			return result;
		}
		if(uid==userId) {
			result.setCode(204);
			result.setMessage("自己不能取消关注自己");
			return result;
		}
		attention.setUserIda(userId);
		int status = attentionService.cancelAttention(attention);
		if(status==-1) {
			result.setCode(203);
			result.setMessage("不存在关注关系");
			return result;
		}
		result.setCode(200);
		result.setMessage("取消关注成功");
		return result;
	}
}







