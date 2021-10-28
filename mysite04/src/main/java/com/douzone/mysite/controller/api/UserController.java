package com.douzone.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller("userApiController")
@RequestMapping("/user/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	//@ResponseBody
	//@RequestMapping("/checkemail")
	@GetMapping("/checkemail")
	public JsonResult checkmail(@RequestParam(value="email", required=true, defaultValue="") String email) {
		UserVo userVo  = userService.getUser(email);
//	    Map<String, Object> map = new HashMap<>();
//		map.put("result", userVo != null);
//		map.put("date", userVo != null);
//		map.put("message", userVo != null);
		
		return JsonResult.success(userVo != null);

	}
}
