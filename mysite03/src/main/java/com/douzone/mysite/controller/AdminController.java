package com.douzone.mysite.controller;


import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    
    @Autowired
    ServletContext servletContext;
	
    @RequestMapping({"", "/main"})
	public String index() {
    	SiteVo siteVo = (SiteVo)servletContext.getAttribute("siteVo");
    	if(siteVo == null) {
    		SiteVo vo = adminService.getAdmin();
    		servletContext.setAttribute("siteVo", vo);
    	}
    	return "admin/main";
	}

	
	@RequestMapping("/main/update")
	public String update(SiteVo siteVo,
			@RequestParam(value="file") MultipartFile multipartFile) {
		SiteVo vo = adminService.alterData(multipartFile,siteVo);
		servletContext.setAttribute("siteVo", vo);
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/main";
	}
	
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
