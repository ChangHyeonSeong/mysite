package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utill.MvcUtil;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Controll(보안, 인증체크)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath()+"/user?a=loginform", request, response);
			return;
		}
//		Enumeration<String> e= session.getAttributeNames();            //세션 속성 이름 확인
//		while (e.hasMoreElements()) {
//			System.out.println("세션 속성 : " + e.nextElement());
//		}
		////////////////////////////////////////////////////////

		
		String pageNo = request.getParameter("p");
		String no = request.getParameter("n");

		request.setAttribute("pageNo", pageNo);
		request.setAttribute("no", no);
		MvcUtil.forward("board/write", request, response);
	}

}
