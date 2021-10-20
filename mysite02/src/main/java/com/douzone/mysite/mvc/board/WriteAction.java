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

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Controll(보안, 인증체크)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath()+"/user?a=loginform", request, response);
			return;
		}
        ////////////////////////////////////////////////////////
		boolean isNumeric = true;
		Long no = null;
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String pageNo = request.getParameter("p"); 
		
		/**제목 공백이면 리다이렉트**/
		if(title.length() == 0 || "".equals(pageNo) ) {
			MvcUtil.redirect(request.getContextPath() + "/board?a=writeform&p=" + pageNo, request, response);
			return;
		}
		
		
		/**페이지번호 넘버링체크 및 셋팅**/
		String noStr = request.getParameter("n");
		if (noStr != null) {
			for (int i = 0; i < noStr.length(); i++) {
				if (!Character.isDigit(noStr.charAt(i))) {
					isNumeric = false;
					MvcUtil.redirect(request.getContextPath() + "/board", request, response);
					return;
				}
			}
			if (isNumeric && !"".equals(noStr)) {
				no = Long.valueOf(noStr);
			}
		}
		/**넘어온 no가 없으면 글쓰기 있으면 답글달기 실행**/
		if (no == null ) {
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(content);
			vo.setUserNo(authUser.getNo());

			new BoardDao().insert(vo);
			
		} else {
			BoardVo vo = new BoardVo();
			BoardDao dao = new BoardDao();
			
			vo = dao.findNo(no);
			
			
			BoardVo upvo = new BoardVo();
			upvo.setOrderNo(vo.getOrderNo() + 1);
			upvo.setDepth(vo.getDepth() + 1);
			
			dao.update(vo);
			
			upvo.setTitle(title);
			upvo.setContents(content);
		    upvo.setGroupNo(vo.getGroupNo());
		    upvo.setOrderNo(vo.getOrderNo() + 1);
		    upvo.setDepth(vo.getDepth() + 1);
			upvo.setUserNo(authUser.getNo());
			

			dao.insertReply(upvo);
			
			MvcUtil.redirect(request.getContextPath() + "/board?p=" + pageNo, request, response);
			return;
		}
		
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);

	}

}
