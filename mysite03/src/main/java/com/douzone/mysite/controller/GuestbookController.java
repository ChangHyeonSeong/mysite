package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;



@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("/list")
	public String index(Model model) {
		List<GuestbookVo> list = guestbookService.GetList();
		
		model.addAttribute("list",list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="/add", method= RequestMethod.POST)
	public String form(GuestbookVo vo) {
		guestbookService.add(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String deleteform(@RequestParam(value="no", required=true, defaultValue="") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value="/delete", method= RequestMethod.POST)
	public String delete(GuestbookVo vo) {
		guestbookService.delete(vo);
		return "redirect:/guestbook/list";
	}
}
