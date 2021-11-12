package com.douzone.mysite.controller.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;



@Controller("guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	
	@ResponseBody
	@RequestMapping("/add")
	public JsonResult ex1(@RequestBody GuestbookVo vo){
		// guestbookService.addMessage(vo)를 사용해서 등록작업
		vo.setNo(1L);
		vo.setPassword("");
		
		return JsonResult.success(vo);
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public JsonResult ex2(
			@RequestParam(value="sn", required=true, defaultValue="-1") Long no) {
		
		List<GuestbookVo> list = guestbookService.getList(no);
		return JsonResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("/delete/{no}")
	public JsonResult ex3(@PathVariable("no") Long no, String password) {
		// result = guestbookService.deleteMessage(no, password)를 사용해서 삭제작업
		
		Long data = 0L;
		
		//1. 삭제가 안된 경우
		data = -1L;
		
		//2. 삭제가 된 경우
		data = no;
		
		return JsonResult.success(data);
	}
	
}
