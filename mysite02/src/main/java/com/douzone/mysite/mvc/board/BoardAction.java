package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utill.MvcUtil;

public class BoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Controll(보안, 인증체크)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if (authUser == null) {
//			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
//			return;
//		}
		////////////////////////////////////////////////////////
		int row = 10;
		int pageNo = 1;
		int pageCount = 0;
		boolean isNumeric = true;
		
		
		/***데이터 총수 가져오기***/
		BoardDao dao = new BoardDao();
		Long count = dao.findCount();
		request.setAttribute("count", count);
		System.out.println("-----------------------  count : " + count + "        ----------------------------");
		
		/**페이지번호 총 갯수 계산후 셋팅**/
		if(count.intValue() < row) {
			pageCount = 1;
		} else if ((count.intValue() % row) > 0) {
			pageCount = count.intValue() / row + 1;
		} else {
			pageCount = count.intValue() / row;
		}
		System.out.println("-----------------------  pageCount : " + pageCount + "        ----------------------------");
		request.setAttribute("pageCount", pageCount);
		
		
		
		String pageNoStr = request.getParameter("p");
		
		System.out.println("-----------------------  넘어온페이지넘버 : " + pageNoStr + "        ----------------------------");
		
		/**넘어온 페이지번호가 널이거나 ""이면 1셋팅 **/
		if (pageNoStr == null || pageNoStr == "") {
			pageNoStr = "1";
		}
		
		/**페이지번호 string to int**/
        for (int i = 0; i < pageNoStr.length(); i++) {
            if (!Character.isDigit(pageNoStr.charAt(i))) {
                isNumeric = false;
            }
        }
		if(isNumeric) {
			pageNo = Integer.valueOf(pageNoStr);
		}
		System.out.println("-----------------------  넘버링된 페이지넘버 : " + pageNo + "        ----------------------------");
		
		/**넘어온 페이지넘버가 0이면 1로 대체**/
		if(pageNo == 0) {
			pageNo = 1;
		}
		
        /**넘어온 페이지넘버가 현재 페이지번호 총 갯수 보다 크면 제일 큰 페이지넘버 값으로 대체**/
		if(pageCount > 0 && pageNo > pageCount ) {
			pageNo = pageCount;
		}
		
		System.out.println("-----------------------  최종페이지넘버 : " + pageNo + "        ----------------------------");
		
		
		
		/**현재페이지번호에 해당되는 데이터 row개 가져오기**/
		long begin = 0;
		begin = (pageNo - 1) * row;
		System.out.println("-----------------------   시작행 : " + begin + "         ----------------------------");
		List<BoardVo> limitList = dao.findAll(begin, (long)row);
		request.setAttribute("limitList", limitList);
		
		request.setAttribute("authUser", authUser);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("row", row);
		
		MvcUtil.forward("board/list", request, response);

	}

}
