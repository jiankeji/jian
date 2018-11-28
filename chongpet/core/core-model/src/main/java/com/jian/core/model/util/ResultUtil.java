package com.jian.core.model.util;

import com.jian.core.model.ResC;
import com.jian.core.model.obj.ResultVo;

public class ResultUtil {
	public static @SuppressWarnings("rawtypes")
	ResultVo setResultVoDesc(ResultVo resultVo, int code){
		return setResultVoDesc(resultVo,code,null);
	}

    public static @SuppressWarnings("rawtypes") ResultVo setResultVoDescUp(ResultVo resultVo, int code){
        return setResultVoDescUp(resultVo,code,null);
    }


	public static  ResultVo setResultVoDesc(ResultVo resultVo,int code,Exception e){
		resultVo.setCode(code);
		String exMsg = "";
		if(e!=null)exMsg = e.getMessage();
		switch (code) {
			case ResC.API_SUCCESS:
				resultVo.setDesc("success");
				break;
			case ResC.API_EXCEPTION:
				resultVo.setDesc("操作异常："+exMsg);
				break;
			case ResC.API_PARAMS_ERROR:
				resultVo.setDesc("参数错误");//手机号码或密码不正确,请确认后再输入
				break;
			case ResC.API_PHONE_NUM_ERROR:
				resultVo.setDesc("手机号错误(位数不够、格式不对)");
				break;
			case ResC.API_MSG_VALIDATE_CODE_ERROR:
				resultVo.setDesc("用户输入的短信验证码不正确");
				break;
			case ResC.API_PHONE_NUM_DIFFER:
				resultVo.setDesc("手机号不一致");
				break;
			case ResC.API_SEND_PHONE_VALIDATE_CODE_ERROR:
				resultVo.setDesc("调用发送手机验证码失败");
				break;
			case ResC.API_PHONE_EXIT:
				resultVo.setDesc("手机号已存在,请换其他手机号");
				break;
			case ResC.API_ERROR_LOGIN_USER:
			    resultVo.setDesc("用户未登录");
				break;
            case ResC.API_ERROR_USER_NULL:
                resultVo.setDesc("用户不存在");
				break;
            case ResC.API_ERROR_FOLLOW_USER:
                resultVo.setDesc("自己不能关注/取消关注自己");
				break;
            case ResC.API_ERROE_FOLLOWING:
                resultVo.setDesc("已经关注");
				break;
            case ResC.API_FOLLOWED_USER:
            	resultVo.setDesc("用户关注/取消关注操作失败");
				break;
			case ResC.API_PARAMS_FORMAT_ERROR:
				resultVo.setDesc("参数格式不正确,请重新输入");//有可能输入的为空
				break;
			case ResC.API_SEND_VERITY_ERROR:
				resultVo.setDesc("验证码发送失败");//有可能输入的为空
				break;
			case ResC.API_ERROR_USER_NOTNULL:
				resultVo.setDesc("用户已经存在");
				break;
			case ResC.API_TOKEN_NOTNULL:
				resultVo.setDesc("token为空");
				break;
			case ResC.API_TOKEN_ERROR:
				resultVo.setDesc("Token认证失败");
				break;
			case ResC.API_RESTART:
				resultVo.setDesc("请重新请求");
				break;
			case ResC.API_USER_NOT_POWER:
				resultVo.setDesc("用户没有权限");
				break;
			default:
		}
		return resultVo;
	}

	public static @SuppressWarnings("rawtypes") ResultVo setResultVoDescUp(ResultVo resultVo,int code,String exMsg){
		resultVo.setCode(code);
		resultVo.setDesc(exMsg);
		return resultVo;
	}
}
