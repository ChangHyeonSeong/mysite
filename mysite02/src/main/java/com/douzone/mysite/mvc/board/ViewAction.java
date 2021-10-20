package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utill.MvcUtil;

public class ViewAction implements Action {

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
		int  pageNo = 1;
		int vistit = 0;
		String COOKIE_NAME = null;
		
		
		String noStr = request.getParameter("n");
		System.out.println("-----------------------  넘어온 뷰넘버 : " + noStr + "        ----------------------------");
		
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
		System.out.println("-----------------------  넘버링된 뷰넘버 : " + noStr + "        ----------------------------");
	    
		/**NO 레코드 데이터 가져오기**/
		BoardDao dao = new BoardDao();
		BoardVo vo = dao.findNo(no);
		request.setAttribute("vo", vo);
		
		
		COOKIE_NAME = "visit" + no;
		
		// 쿠키 읽기
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (COOKIE_NAME.equals(cookie.getName())) {
					vistit = Integer.parseInt(cookie.getValue());
				}
			}
		}
		
		/**NO 레코드 hit 조회수 증가**/
		// 쿠키 쓰기
		if (vistit == 0) {
			vistit = 1;

			Cookie cookie = new Cookie(COOKIE_NAME, String.valueOf(vistit));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(60); // 60s
			response.addCookie(cookie);
			
			dao.updateHit(no);
		}
		// 화면 출력
		System.out.println(
				"-----------------------  게시판레코드번호 : " + vistit + "        ----------------------------");
		
		
		/**페이지번호 넘버링체크 및 셋팅**/
		String pageNoStr = request.getParameter("p");
		for (int i = 0; i < pageNoStr.length(); i++) {
            if (!Character.isDigit(pageNoStr.charAt(i))) {
                isNumeric = false;
                MvcUtil.redirect(request.getContextPath() + "/board", request, response);
                return;
            }
        }
		if(isNumeric) {
			 pageNo = Integer.valueOf(pageNoStr);
		}
		
		/**로그인 유저와 글쓴이가 같은 유저인지 확인**/
		boolean authCheck = false;
		dao = new BoardDao();
		vo = dao.findNo(no);
		System.out.println(authUser);
		System.out.println(vo);
		if(authUser.getNo() == vo.getUserNo()) {
			authCheck =true;
		}
		request.setAttribute("authCheck", authCheck);
		request.setAttribute("pageNo", pageNo);
		
		MvcUtil.forward("board/view", request, response);

	}

}
