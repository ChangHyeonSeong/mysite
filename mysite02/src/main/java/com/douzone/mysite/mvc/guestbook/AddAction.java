package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.web.mvc.Action;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
	    String password = request.getParameter("password");
	    String message = request.getParameter("content");
	    String regDate = LocalDateTime.now().toString();
	    
	    GuestBookVo vo = new GuestBookVo();
	    vo.setName(name);
	    vo.setPassword(password);
	    vo.setMessage(message);
	    vo.setRegDate(regDate);
	    
	    new GuestBookDao().insert(vo);
		
		response.sendRedirect("/mysite02/guestbook?a=list");

	}

}
