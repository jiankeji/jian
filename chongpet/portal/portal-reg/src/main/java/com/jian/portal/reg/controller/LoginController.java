package com.jian.portal.reg.controller;

import com.jian.core.model.bean.User;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.*;
import com.jian.core.server.service.LoginService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.jian.core.model.ResC.*;

/**
 * 登录注册controller
 * @author shen
 *
 */
@SuppressWarnings("ALL")
@Api(value ="api1/login",description = "登录 注册")
@RestController
@RequestMapping(value="api1/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * 根据手机号判断新老用户
	 * @param phoneNumber 手机号
	 * @return
	 */
	@PostMapping(value="/judge",produces="application/json; charset=UTF-8")
	@ApiOperation(value="判断新老用户", notes="判断新老用户", response= ResultVo.class,position=1)
	@ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
			@ApiResponse(code=API_PHONE_NUM_ERROR,message="手机号错误(位数不够、格式不对)"),
			@ApiResponse(code=API_PHONE_EXIT,message="手机号已存在,请换其他手机号")})
	@ApiImplicitParam(paramType = "query",name = "phoneNumber",value = "手机号",dataType = "String",required = true)
	public ResultVo<String> judgePhone(@RequestParam String phoneNumber) {
		ResultVo<String> result = new ResultVo<>("");
		if (phoneNumber.length()!=11) {
			return ResultUtil.setResultVoDesc(result,API_PHONE_NUM_ERROR);
		}
		String isUser = loginService.isUserByPhone(phoneNumber);
		if("true".equals(isUser)){
			return ResultUtil.setResultVoDesc(result,API_PHONE_EXIT);
		}
		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
	}

	/**
	 * 密码登录
	 * @param user
	 * @return
	 */
	@PostMapping(value="/password",produces="application/json; charset=UTF-8")
	@ApiOperation(value="密码登录", notes="密码登录", response= ResultVo.class,position=1)
	@ApiResponses({
			@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
			@ApiResponse(code=API_PARAMS_ERROR,message="参数错误，请确认在输入"),
			@ApiResponse(code=API_ERROR_USER_NULL,message="用户不存在"),
			@ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query",name = "phoneNumber",value = "手机号",dataType = "String",required = true),
			@ApiImplicitParam(paramType = "query",name = "password",value = "密码",dataType = "String",required = true)
	})
	public ResultVo<Map<String,String>> loginByPwd(HttpServletRequest request,@RequestParam  String phoneNumber,@RequestParam String password) {
		User user = new User();
		user.setPassword(password);
		user.setPhoneNumber(phoneNumber);
		Map<String,String> map = new HashMap<>();
		ResultVo<Map<String,String>> result = new ResultVo<>(map);
		if(user.getPhoneNumber().length()==11) {
			String isUser = loginService.isUserByPhone(user.getPhoneNumber());
			if("false".equals(isUser)) {
				return ResultUtil.setResultVoDesc(result,API_ERROR_USER_NULL);
			}
		}
		int s = loginService.loginByPwd(user);
		if(s==201) {
			return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
		}
		if(s==202) {
			return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
		}
		if(s==204) {
			return ResultUtil.setResultVoDesc(result,API_PARAMS_ERROR);
		}

		User u= loginService.selectUserAllByPhone(user.getPhoneNumber());
		String token = Mdfive.randomMd5(u.getUserId());

		if(u.getUserId()==-1)return ResultUtil.setResultVoDesc(result,API_ERROR_USER_NULL);

		loginService.redisUser(u,token);

		map.put("userId", u.getUserId()+"");
		map.put("token", token);
		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
	}

	/**
	 * 发送手机验证码
	 * @param request
	 * @param phoneNumber  手机号
	 * @return
	 */
	@PostMapping(value="/sendVerity",produces="application/json; charset=UTF-8")
	@ApiOperation(value="发送手机验证码", notes="发送手机验证码", response= ResultVo.class,position=1)
	@ApiResponses({
			@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
			@ApiResponse(code=API_PHONE_NUM_ERROR,message="手机号错误(位数不够、格式不对)"),
			@ApiResponse(code=API_SEND_VERITY_ERROR,message="验证码发送失败")})
	@ApiImplicitParam(paramType = "query",name = "phoneNumber",value = "手机号",dataType = "String",required = true)
	public ResultVo<Integer> verity(HttpServletRequest request, @RequestParam String phoneNumber) {
		ResultVo<Integer> result = new ResultVo<>(-1);
		if(phoneNumber.trim().length()!=11){
			return ResultUtil.setResultVoDesc(result,API_PHONE_NUM_ERROR);
		}
		String random = MyRandom.getCode();
		if(random == null || random.length()==0){
			return ResultUtil.setResultVoDesc(result,API_SEND_VERITY_ERROR);
		}
		try {
			SDK.sdk(phoneNumber, random);
		} catch (Exception e) {
			return ResultUtil.setResultVoDesc(result,API_SEND_VERITY_ERROR);
		}
		request.getSession().setAttribute("phoneNumber", phoneNumber);
		request.getSession().setAttribute("code", random);
		request.getSession().setAttribute("time", System.currentTimeMillis()+"");

		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
}

	/**
	 * 判断发送验证码是否正确
	 * @param request
	 * @param code
	 * @return
	 */
	@PostMapping(value="/isVerity",produces="application/json; charset=UTF-8")
	@ApiOperation(value="判断发送验证码是否正确", notes="判断发送验证码是否正确", response= ResultVo.class,position=1)
	@ApiResponses({
			@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
			@ApiResponse(code=API_PARAMS_ERROR,message="参数错误，请确认在输入"),
			@ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
	@ApiImplicitParam(paramType = "query",name = "phoneNumber",value = "手机号",dataType = "String",required = true)
	public ResultVo<Integer> isVerity(HttpServletRequest request, @RequestParam String code) {
		ResultVo<Integer> result = new ResultVo<>(-1);
		String codes = (String) request.getSession().getAttribute("code");
		if(code.trim().length()!=4) {
			return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
		}
		if(!code.equals(codes)) {
			return ResultUtil.setResultVoDesc(result,API_PARAMS_ERROR);
		}
		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
	}

	/**
	 * 注册
	 * @param request
	 * @param user
	 * @return
	 */
	@PostMapping(value="/register",produces="application/json; charset=UTF-8")
	@ApiOperation(value="注册", notes="注册", response= ResultVo.class,position=1)
	@ApiResponses({
			@ApiResponse(code=API_SUCCESS, message="操作成功",response=ResultVo.class),
			@ApiResponse(code=API_ERROR_USER_NOTNULL,message="用户已经存在"),
			@ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入")})
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query",name = "phoneNumber",value = "手机号",dataType = "String",required = true),
			@ApiImplicitParam(paramType = "query",name = "password",value = "密码",dataType = "String",required = true),
			@ApiImplicitParam(paramType = "query",name = "phoneType",value = "手机类型 o ios  1安卓",dataType = "int",required = true),
			@ApiImplicitParam(paramType = "query",name = "loginType",value = "登录类型 0手机号注册  1第三方登录注册",dataType = "int",required = true),
			@ApiImplicitParam(paramType = "query",name = "userId",value = "用户id 登录类型为1 则需要",dataType = "int",required = false)
	})
	public ResultVo<Map<String,String>> register(HttpServletRequest request,@RequestParam  String phoneNumber,
												 @RequestParam String password,@RequestParam Integer phoneType,
												 @RequestParam Integer loginType,@RequestParam Integer userId) {
		User user = new User();
		user.setPhoneNumber(phoneNumber);
		user.setPassword(password);
		user.setPhoneType(phoneType);
		Map<String,String> map = new HashMap<>();
		ResultVo<Map<String,String>> result = new ResultVo<>(map);
		if(user.getPassword()==null || user.getPassword().trim().length()==0) {
			return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
		}
		if(user.getPhoneNumber().trim().length()!=11){
			return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
		}
		String isUser = loginService.isUserByPhone(user.getPhoneNumber());
		if("true".equals(isUser)) {
			return ResultUtil.setResultVoDesc(result,API_ERROR_USER_NOTNULL);
		}

		if(loginType==1){
			if(userId==null){
				return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
			}
		}

		loginService.register(user,loginType);


		User u = loginService.selectUserAllByPhone(user.getPhoneNumber());
		String token = Mdfive.randomMd5(u.getUserId());

		if(u.getUserId()==-1)return ResultUtil.setResultVoDesc(result,API_ERROR_USER_NULL);
		loginService.redisUser(u,token);

		map.put("userId", u.getUserId()+"");
		map.put("token", token);

		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
	}
}







