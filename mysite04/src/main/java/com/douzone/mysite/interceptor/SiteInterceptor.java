package com.douzone.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

public class SiteInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private SiteService siteService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("SiteInterceptor.preHandle(...)  called");
		SiteVo siteVo = (SiteVo)request.getServletContext().getAttribute("siteVo");
		if(siteVo == null) {
			siteVo = siteService.getSite();
			request.getServletContext().setAttribute("siteVo", siteVo);
		}

		return true;
	}
}