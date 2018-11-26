package com.jian.core.model.token;

import com.jian.core.model.util.JsonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class TokenProxy {

	@Pointcut(value="@annotation(com.jian.core.model.token.Token)")
	public void loginm4() {
		
	}
	@Around(value="loginm4()")
	public JsonResult before(ProceedingJoinPoint point) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	  	ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		String t2 = request.getHeader("token");
		String t = (String)request.getSession().getAttribute("token");
		JsonResult result = new JsonResult();
		result.setCode(401);
		result.setMessage("token认证失败");
		if(t2==null) {
			return result;
		}
		if(t==null) {
			return result;
		}
		if(t.equals(t2)) {
			try {
				JsonResult result2 =(JsonResult) point.proceed();
				return result2;
			} catch (Throwable e) {
				JsonResult result2 = new JsonResult();
				result2.setCode(402);
				result2.setMessage("请重新请求");
				return result2;
			}
		}
		return result;
	}
	
}
