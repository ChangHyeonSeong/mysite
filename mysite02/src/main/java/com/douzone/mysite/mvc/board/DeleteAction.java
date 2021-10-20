package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utill.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Controll(보안, 인증체크)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return;
		}
		////////////////////////////////////////////////////////
		String no = request.getParameter("n");
		String pageNo = request.getParameter("p");
		
		
		 if(!(no.length() > 0) || no == null ) {
			 MvcUtil.redirect(request.getContextPath() + "/board", request, response);
			 return;
		 }
		
		/**로그인 유저와 글쓴이가 같은 유저인지 확인**/
		BoardDao dao = new BoardDao();
		BoardVo vo = dao.findNo(Long.valueOf(no));
		
		if(authUser.getNo() != vo.getUserNo()) {
			MvcUtil.redirect("board?p="+ pageNo, request, response);
			return;
		}
		
		vo.setNo(Long.valueOf(no));
		
		
		dao.delete(vo);
		
		
		MvcUtil.redirect(request.getContextPath() + "/board?p=" +pageNo, request, response);

	}

}
