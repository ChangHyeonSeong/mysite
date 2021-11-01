package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
		
		// 1. 파라미터에 @AuthUser가 붙어 있는지 , 타입이 UserVO인지 확인 
		if(supportsParameter(parameter) == false) {
			// 내가 해석할 수 있는 파라미터가 아니다.
			return WebArgumentResolver.UNRESOLVED;
		}
		
		// 4. 여기까지 진행이 되었다면, @AuthUser가 붙어있고 타입이 UserVO인 경우이다.
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		System.out.println("AuthUserHandlerMethodArgumentResolver.resolveArgument(...)  called ");
		return session.getAttribute("authUser");
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// 2. @AuthUser가 붙어 있는지 확인
		if(authUser == null) {
			return false;
		}
		
		// 3. UserVO 타입이 아닌 경우
		if(parameter.getParameterType().equals(UserVo.class) == false) {
			return false;
		}
		return true;
	}	

}
