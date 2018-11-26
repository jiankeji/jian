package com.jian.portal.reg.controller;

import com.jian.core.model.bean.User;
import com.jian.core.model.util.JsonResult;
import com.jian.core.model.util.Mdfive;
import com.jian.core.model.util.MyRandom;
import com.jian.core.model.util.SDK;
import com.jian.core.server.service.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录注册controller
 * @author shen
 *
 */
@RestController
@RequestMapping(value="/login")
public class LoginController {
	
	@Resource
	private LoginService loginService;
	
	/**
	 * 根据手机号判断新老用户
	 * @param phoneNumber 手机号
	 * @return
	 */
	@RequestMapping(value="/judge",method= RequestMethod.POST)
	public JsonResult judgePhone(@RequestParam String phoneNumber) {
		
		if (phoneNumber.length()!=11) {
			JsonResult result = new JsonResult();
			result.setCode(201);
			result.setMessage("手机号输入错误");
			return result;
		}
		String isUser = loginService.isUserByPhone(phoneNumber);
		Map<String, Object> map = new HashMap<>();
		JsonResult result = new JsonResult(map);
		map.put("isUser", isUser);
		return result;
	}
	
	/**
	 * 密码登录
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/password",method= RequestMethod.POST)
	public JsonResult loginByPwd(HttpServletRequest request, User user) {
		JsonResult result = new JsonResult();
		if(user.getPhoneNumber().length()==11) {
			String isUser = loginService.isUserByPhone(user.getPhoneNumber());
			if("false".equals(isUser)) {
				result.setCode(201);
				result.setMessage("用户不存在");
				return result;
			}
		}
		int s = loginService.loginByPwd(user);
		if(s==201) {
			result.setCode(202);
			result.setMessage("手机号输入错误");
			return result;
		}
		if(s==202) {
			result.setCode(203);
			result.setMessage("密码输入错误");
			return result;
		}
		if(s==204) {
			result.setCode(204);
			result.setMessage("密码不正确");
			return result;
		}
		String token = Mdfive.randomMd5();
		User u = loginService.selectUserAllByPhone(user.getPhoneNumber());
		request.getSession().setAttribute("phoneNumber", user.getPhoneNumber());
		request.getSession().setAttribute("userId", u.getUserId());
		request.getSession().setAttribute("token", token);
		Map<String,Object> map = new HashMap<>();
		map.put("user", u);
		map.put("token", token);
		result.setData(map);
		result.setMessage("登录成功");
		return result;
	}
	
	/**
	 * 发送手机验证码
	 * @param request
	 * @param phoneNumber  手机号
	 * @return
	 */
	@RequestMapping(value="/sendVerity",method= RequestMethod.POST)
	public JsonResult verity(HttpServletRequest request, @RequestParam String phoneNumber) {
		JsonResult result = new JsonResult();
		if(phoneNumber.trim().length()!=11){
			result.setCode(201);
			result.setMessage("手机号不正确");
			return result;
		}
		String random = MyRandom.getCode();
		if(random == null || random.length()==0){
			result.setCode(202);
			result.setMessage("无效的验证码");
			return result;
		}
		try {
			SDK.sdk(phoneNumber, random);
		} catch (Exception e) {
			result.setCode(203);
			result.setMessage("验证码发送失败");
			return result;
		}
		request.getSession().setAttribute("phoneNumber", phoneNumber);
		request.getSession().setAttribute("code", random);
		request.getSession().setAttribute("time", System.currentTimeMillis()+"");
		result.setCode(200);
		result.setMessage("验证码发送成功");
		return result;
	}
	
	/**
	 * 判断发送验证码是否正确
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/isVerity",method= RequestMethod.POST )
	public JsonResult isVerity(HttpServletRequest request, @RequestParam String code) {
		String codes = (String) request.getSession().getAttribute("code");
		JsonResult result = new JsonResult();
		if(code.trim().length()!=4) {
			result.setCode(201);
			result.setMessage("验证码为空或者不是4位");
			return result;
		}
		if(!code.equals(codes)) {
			result.setCode(202);
			result.setMessage("验证码不一致");
			return result;
		}
		result.setCode(200);
		result.setMessage("验证成功");
		return result;
	}
	
	/**
	 * 注册
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/register",method= RequestMethod.POST)
	public JsonResult register(HttpServletRequest request, User user) {
		JsonResult result = new JsonResult();
		if(user.getPassword()==null || user.getPassword().trim().length()==0) {
			result.setCode(201);
			result.setMessage("密码为空");
			return result;
		}
		if(user.getPhoneNumber().trim().length()!=11){
			result.setCode(202);
			result.setMessage("手机号不正确");
			return result;
		}
		String isUser = loginService.isUserByPhone(user.getPhoneNumber());
		if("true".equals(isUser)) {
			result.setCode(203);
			result.setMessage("该手机号已注册");
			return result;
		}
		loginService.register(user);
		String token = Mdfive.randomMd5();
		User u = loginService.selectUserAllByPhone(user.getPhoneNumber());
		request.getSession().setAttribute("phoneNumber", user.getPhoneNumber());
		request.getSession().setAttribute("userId", u.getUserId());
		request.getSession().setAttribute("token", token);
		Map<String,Object> map = new HashMap<>();
		map.put("user", u);
		map.put("token", token);
		result.setCode(200);
		result.setMessage("注册成功");
		result.setData(map);
		return result;
	}
}







