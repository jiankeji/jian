package com.jian.portal.reg.controller;

import com.jian.core.model.bean.User;
import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.*;
import com.jian.core.server.service.LoginService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
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
	@ApiResponses({@ApiResponse(code=API_SUCCESS, message="操作成功",response=String.class),
			@ApiResponse(code=API_PHONE_NUM_ERROR,message="手机号错误(位数不够、格式不对)",response=String.class),
			@ApiResponse(code=API_PHONE_EXIT,message="手机号已存在,请换其他手机号",response=String.class)})
	@ApiImplicitParam(paramType = "query",name = "phoneNumber",value = "手机号",dataType = "String",required = true)
	public ResultVo<String> judgePhone(@RequestParam String phoneNumber) {
		ResultVo<String> result = new ResultVo<>("");
		if (phoneNumber.length()!=11) {
			return ResultUtil.setResultVoDesc(result,API_PHONE_NUM_ERROR);
		}
		String isUser = loginService.isUserByPhone(phoneNumber);
		if("true".equals(isUser)){
			result.setObj("true");
		}
		if ("false".equals(isUser)){
			result.setObj("false");
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
			@ApiResponse(code=API_SUCCESS, message="操作成功",response=Map.class),
			@ApiResponse(code=API_PARAMS_ERROR,message="参数错误，请确认在输入",response=Map.class),
			@ApiResponse(code=API_ERROR_USER_NULL,message="用户不存在",response=Map.class),
			@ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入",response=Map.class)})
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
		result.setObj(map);
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
			@ApiResponse(code=API_PHONE_NUM_ERROR,message="手机号错误(位数不够、格式不对)",response=String.class),
			@ApiResponse(code=API_SEND_VERITY_ERROR,message="验证码发送失败",response=String.class)})
	@ApiImplicitParam(paramType = "query",name = "phoneNumber",value = "手机号",dataType = "String",required = true)
	public ResultVo<String> verity(HttpServletRequest request, @RequestParam String phoneNumber) {
		ResultVo<String> result = new ResultVo<>("");
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
			@ApiResponse(code=API_SUCCESS, message="操作成功",response=String.class),
			@ApiResponse(code=API_PARAMS_ERROR,message="参数错误，请确认在输入",response=String.class),
			@ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入",response=String.class),
			@ApiResponse(code=API_CODE_LOSE,message="验证码失效",response=String.class)})
	@ApiImplicitParam(paramType = "query",name = "code",value = "手机号",dataType = "String",required = true)
	public ResultVo<String> isVerity(HttpServletRequest request, @RequestParam String code) {
		ResultVo<String> result = new ResultVo<>("");
		String codes = (String) request.getSession().getAttribute("code");
		String time = (String) request.getSession().getAttribute("time");
		if(StringUtils.isEmpty(time)||StringUtils.isEmpty(codes)){
			return  ResultUtil.setResultVoDesc(result,API_CODE_LOSE);
		}
		Long num = 1000*60*30L;//30分钟的时间戳
		Long number = System.currentTimeMillis()-Long.parseLong(time);
		if(number>=num){//超过30分钟验证码失效
			return ResultUtil.setResultVoDesc(result,API_CODE_LOSE);
		}

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
			@ApiResponse(code=API_SUCCESS, message="操作成功",response=Map.class),
			@ApiResponse(code=API_ERROR_USER_NOTNULL,message="用户已经存在",response=Map.class),
			@ApiResponse(code=API_EXCEPTION, message="操作异常",response=Map.class),
			@ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入",response=Map.class)})
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query",name = "phoneNumber",value = "手机号",dataType = "String",required = true),
			@ApiImplicitParam(paramType = "query",name = "password",value = "密码",dataType = "String",required = true),
			@ApiImplicitParam(paramType = "query",name = "phoneType",value = "手机类型 o ios  1安卓",dataType = "int",required = true),
			@ApiImplicitParam(paramType = "query",name = "loginType",value = "注册类型 0手机号注册 1微信登录注册 2支付宝登录注册 3qq登录注册",dataType = "int",required = true),
			@ApiImplicitParam(paramType = "query",name = "code",value = "code 通过这个获取信息 loginType为2或1则需要",dataType = "int",required = false),
			@ApiImplicitParam(paramType = "query",name = "accessToken",value = "accessToken 通过这个获取信息 loginType为3则需要",dataType = "int",required = false)
	})
	public ResultVo<Map<String,String>> register(HttpServletRequest request,@RequestParam  String phoneNumber,
												 @RequestParam String password,@RequestParam Integer phoneType,
												 @RequestParam Integer loginType,@RequestParam String code,
												 @RequestParam String accessToken) {
		User user = new User();
		user.setPhoneNumber(phoneNumber);
		user.setPassword(password);
		user.setPhoneType(phoneType);
		Map<String,String> map = new HashMap<>();
		ResultVo<Map<String,String>> result = new ResultVo<>(map);
		if(StringUtils.isEmpty(password)) {
			return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
		}
		if(user.getPhoneNumber().trim().length()!=11){
			return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
		}
		String isUser = loginService.isUserByPhone(user.getPhoneNumber());
		if("true".equals(isUser)) {
			return ResultUtil.setResultVoDesc(result,API_ERROR_USER_NOTNULL);
		}
		if(loginType==1){//微信登录注册

		}
		if(loginType==2){//支付宝登录注册
			if(StringUtils.isEmpty(code)){
				return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
			}
			User userAli = ALILoginUtil.getAlipay(code);
			if (userAli.getUserId()==-1){
				return ResultUtil.setResultVoDesc(result,API_EXCEPTION);
			}
			user.setPayid(userAli.getPayid());
			user.setName(userAli.getName());
			user.setPhoto(userAli.getPhoto());
		}
		if(loginType==3){//qq登录注册
			if (StringUtils.isEmpty(accessToken)){
				return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
			}
			User userQQ = QQLoginUtil.qqUser(accessToken);
			if(userQQ.getUserId()==-1){
				return ResultUtil.setResultVoDesc(result,API_EXCEPTION);
			}
			user.setName(userQQ.getName());
			user.setQqid(userQQ.getQqid());
			user.setPhoto(userQQ.getPhoto());
		}

		loginService.register(user,loginType);

		User u = loginService.selectUserAllByPhone(user.getPhoneNumber());
		String token = Mdfive.randomMd5(u.getUserId());

		if(u.getUserId()==-1)return ResultUtil.setResultVoDesc(result,API_ERROR_USER_NULL);
		loginService.redisUser(u,token);

		map.put("userId", u.getUserId()+"");
		map.put("token", token);
		result.setObj(map);
		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
	}


	@PostMapping(value="/alipayLogin",produces="application/json; charset=UTF-8")
	@ApiOperation(value="支付宝登录", notes="支付宝登录", response= ResultVo.class,position=1)
	@ApiResponses({
			@ApiResponse(code=API_SUCCESS, message="操作成功",response=Map.class),
			@ApiResponse(code=API_EXCEPTION, message="操作异常",response=Map.class),
			@ApiResponse(code=API_ALIPAY_NOT_BUOND,message="支付宝未绑定过",response=Map.class),
			@ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入",response=Map.class),
			@ApiResponse(code=API_ERROR_USER_NULL,message="用户不存在",response=Map.class)})
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query",name = "code",value = "code",dataType = "String",required = true)
	})
	public ResultVo<Map<String,String>> alipayLogin(@RequestParam String code){
		Map<String,String> map = new HashMap<>();
		ResultVo<Map<String,String>> result = new ResultVo<>(map);
		if(StringUtils.isEmpty(code)){
			return  ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
		}
		User user = ALILoginUtil.getAlipay(code);
		if(user.getUserId()==-1){
			return ResultUtil.setResultVoDesc(result,API_EXCEPTION);
		}
		String phoneNumber = loginService.checkPayid(user.getPayid());
		if(phoneNumber.equals("-1"))return ResultUtil.setResultVoDesc(result,API_ALIPAY_NOT_BUOND);

		User u = loginService.selectUserAllByPhone(phoneNumber);
		String token = Mdfive.randomMd5(u.getUserId());

		if(u.getUserId()==-1)return ResultUtil.setResultVoDesc(result,API_ERROR_USER_NULL);
		loginService.redisUser(u,token);

		map.put("userId", u.getUserId()+"");
		map.put("token", token);
		result.setObj(map);
		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
	}


	@PostMapping(value="/alipayLogin",produces="application/json; charset=UTF-8")
	@ApiOperation(value="qq登录", notes="qq登录", response= ResultVo.class,position=1)
	@ApiResponses({
			@ApiResponse(code=API_SUCCESS, message="操作成功",response=Map.class),
			@ApiResponse(code=API_EXCEPTION, message="操作异常",response=Map.class),
			@ApiResponse(code=API_QQ_NOT_BUOND,message="QQ未绑定过",response=Map.class),
			@ApiResponse(code=API_PARAMS_FORMAT_ERROR,message="参数格式不正确，请重新输入",response=Map.class),
			@ApiResponse(code=API_ERROR_USER_NULL,message="用户不存在",response=Map.class)})
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query",name = "accessToken",value = "accessToken",dataType = "String",required = true)
	})
	public ResultVo<Map<String,String>> qqlogin(@RequestParam String accessToken){
		Map<String,String> map = new HashMap<>();
		ResultVo<Map<String,String>> result = new ResultVo<>(map);
		if(StringUtils.isEmpty(accessToken)){
			return ResultUtil.setResultVoDesc(result,API_PARAMS_FORMAT_ERROR);
		}

		String qqid = QQLoginUtil.getOpenid(accessToken);
		if(("-1".equals(qqid))){
			return ResultUtil.setResultVoDesc(result,API_EXCEPTION);
		}

		String phoneNumber = loginService.checkQQid(qqid);
		if(phoneNumber.equals("-1"))return ResultUtil.setResultVoDesc(result,API_QQ_NOT_BUOND);

		User u = loginService.selectUserAllByPhone(phoneNumber);
		String token = Mdfive.randomMd5(u.getUserId());

		if(u.getUserId()==-1)return ResultUtil.setResultVoDesc(result,API_ERROR_USER_NULL);
		loginService.redisUser(u,token);

		map.put("userId", u.getUserId()+"");
		map.put("token", token);
		result.setObj(map);
		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
	}

}







