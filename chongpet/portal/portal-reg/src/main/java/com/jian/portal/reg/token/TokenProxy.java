package com.jian.portal.reg.token;

import com.jian.core.model.obj.ResultVo;
import com.jian.core.model.util.ResultUtil;
import com.jian.core.server.redisDao.UserRedisDao;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jian.core.model.ResC.*;

@Aspect
@Component
public class TokenProxy {

	@Resource
	public UserRedisDao userRedisDao;

	@Pointcut(value="@annotation(com.jian.portal.reg.token.Token)")
	public void loginm4() {
		
	}
	@Around(value="loginm4()")
	public ResultVo<Object> before(ProceedingJoinPoint point) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	  	ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		String token = request.getHeader("token");

		Object obj = new Object();
		ResultVo<Object> result = new ResultVo<>(obj);

		if(token==null) {
			return ResultUtil.setResultVoDesc(result,API_TOKEN_NOTNULL);
		}

		Integer  userId = userRedisDao.getUserId(token);
		if(userId == null){
			return ResultUtil.setResultVoDesc(result,API_TOKEN_ERROR);
		}
		try {
			result = (ResultVo<Object>) point.proceed();
		} catch (Throwable throwable) {
			return ResultUtil.setResultVoDesc(result,API_RESTART);
		}
		return ResultUtil.setResultVoDesc(result,API_SUCCESS);
	}
	
}
