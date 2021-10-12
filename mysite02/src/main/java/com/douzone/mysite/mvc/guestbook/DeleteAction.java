package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String no = request.getParameter("no");
		    String password = request.getParameter("password");
		    
		    
		    GuestBookVo vo = new GuestBookVo();
		    vo.setNo(Long.parseLong(no));
		    vo.setPassword(password);
		    
		    
		    new GuestBookDao().delete(vo);
		    
		    response.sendRedirect("/mysite02/guestbook?a=list");

	}

}
