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

public class ModifyAction implements Action {

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
		boolean isNumeric = true;
		Long no = null;
		String noStr = request.getParameter("n");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String pageNo = request.getParameter("p");
		
		/**페이지번호 string to Long**/
        for (int i = 0; i < noStr.length(); i++) {
            if (!Character.isDigit(noStr.charAt(i))) {
                isNumeric = false;
                MvcUtil.redirect(request.getContextPath() + "/board", request, response);
                return;
            }
        }
		if(isNumeric) {
			no = Long.valueOf(noStr);
		}
		
		/**로그인 유저와 글쓴이가 같은 유저인지 확인**/
		BoardDao dao = new BoardDao();
		BoardVo vo = dao.findNo(no);
		System.out.println(authUser);
		System.out.println(vo);
		if(authUser.getNo() != vo.getUserNo()) {
			MvcUtil.redirect("board?a=view&p="+ pageNo +"&n=" + no, request, response);
			return;
		}
		
		/**제목 글내용 업데이트**/
		dao.updateTitleAndContent(title, content, no);
		
		
		
		
		MvcUtil.redirect("board?a=view&p="+ pageNo +"&n=" + no, request, response);

	}

}
