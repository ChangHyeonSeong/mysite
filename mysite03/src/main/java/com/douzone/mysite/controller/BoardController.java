package com.douzone.mysite.controller;

import java.util.List;


import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

import ch.qos.logback.core.net.SyslogOutputStream;




@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping(value = { "", "/{pageNo}" }, method = RequestMethod.GET)
	public String index(HttpSession session, Model model,
			@PathVariable(value = "pageNo", required = false) Long pageNo) {

		UserVo authUser = (UserVo) session.getAttribute("authUser");
        /**가져올 레코드 수**/
		int row = 10;

		/** 넘어온 페이지번호가 널이면 1셋팅 **/
		if (pageNo == null) {
			pageNo = 1L;
		}

		/** 넘어온 페이지넘버가 0이면 1로 대체 **/
		if (pageNo == 0L) {
			pageNo = 1L;
		}

		/*** 데이터 총수 가져오기 ***/
		Long count = boardService.findCount();
		

		/** 페이지번호 총 갯수 계산후 셋팅 **/
		int pageCount = boardService.getPageCount(row);
		

		/** 넘어온 페이지넘버가 현재 페이지번호 총 갯수 보다 크면 제일 큰 페이지넘버 값으로 대체 **/
		if (pageCount > 0 && pageNo > pageCount) {
			pageNo = Long.valueOf(pageCount);
		}

		/** 현재페이지번호에 해당되는 데이터 row개 가져오기 **/
		List<BoardVo> limitList = boardService.findLimitList((long) row, pageNo);
		
		model.addAttribute("limitList", limitList);
		model.addAttribute("count", count);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("authUser", authUser);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("row", row);

		return "board/list";
	}

	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String view(HttpSession session, Model model, 
			@PathVariable(value = "no", required = false) Long no,
			@RequestParam(value="p", required=false, defaultValue="1") Long pageNo) {
		
		// Access Controll(보안, 인증체크)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "user/login";
		}
		////////////////////////////////////////////////////////
		
		/**NO 레코드 데이터 가져오기**/
		BoardVo vo = boardService.getByno(no);
		model.addAttribute("vo", vo);
		
		/**NO 레코드 hit 조회수 증가**/
		boardService.upHit(no);
		
		/**로그인 유저와 글쓴이가 같은 유저인지 확인**/
		boolean authCheck = boardService.authUser(authUser,no);
		
		model.addAttribute("authCheck", authCheck);
		model.addAttribute("pageNo", pageNo);

		return "board/view";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpSession session, Model model,
			@RequestParam(value = "n", required = false) Long no,
			@RequestParam(value="p", required=false, defaultValue="1") Long pageNo) {
		
		// Access Controll(보안, 인증체크)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "user/login";
		}
		////////////////////////////////////////////////////////
		
		model.addAttribute("no", no);
		model.addAttribute("pageNo", pageNo);
		
		return "board/write";
	}

	
	@RequestMapping(value =  "/write", method = RequestMethod.POST)
	public String write(HttpSession session, Model model, BoardVo vo,
			@RequestParam(value = "n", required = false) Long no,
			@RequestParam(value="p", required=false, defaultValue="1") Long pageNo) {
		
		// Access Controll(보안, 인증체크)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "user/login";
		}
		////////////////////////////////////////////////////////
		System.out.println("--------------------------------                  "+no);
		
		vo.setUserNo(authUser.getNo());
		
		if (no == null) {
			/** 제목 공백이면 리다이렉트 **/
			if (vo.getTitle().length() == 0) {
				return "redirect:/board/write?p=" + pageNo;
			}

			boardService.newWrite(vo);
			
			return "redirect:/board";
		} else {
			/** 제목 공백이면 리다이렉트 **/
			if (vo.getTitle().length() == 0) {
				return "redirect:/board/write?n=" + no + "&p=" + pageNo;
			}
			System.out.println("--------------------------------                  "+no);
			System.out.println("--------------------------------                  "+vo);
			boardService.replyWrite(no,vo);

			return "redirect:/board/" + pageNo;
		}
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(BoardVo vo) {
		// boardService.delete(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(BoardVo vo) {
		// boardService.delete(vo);
		return "redirect:/guestbook";
	}
	
}
