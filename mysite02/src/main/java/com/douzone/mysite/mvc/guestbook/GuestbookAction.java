package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utill.MvcUtil;

public class GuestbookAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestBookDao dao = new GuestBookDao();
		List<GuestBookVo> list = dao.findAll();
		request.setAttribute("list", list);
		
		
		MvcUtil.forward("guestbook/list", request, response);

	}

}
