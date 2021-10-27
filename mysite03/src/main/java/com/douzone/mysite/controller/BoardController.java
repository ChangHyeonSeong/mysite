package com.douzone.mysite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping(value = { "", "/{pageNo}" }, method = RequestMethod.GET)
	public String index(
			@AuthUser UserVo authUser, Model model,
			@PathVariable(value = "pageNo", required = false) Long pageNo) {
		Map<String,Object> map = boardService.inIt(pageNo,authUser);
		
		model.addAttribute("map", map);

		return "board/list";
	}

	@Auth
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(
			@AuthUser UserVo authUser, Model model, 
			@RequestParam(value = "n", required = false) Long no,
			@RequestParam(value = "p", required = false, defaultValue = "1") Long pageNo) {

		/** NO 레코드 데이터 가져오기 **/
		BoardVo vo = boardService.getByno(no);
		model.addAttribute("vo", vo);

		/** NO 레코드 hit 조회수 증가 **/
		boardService.upHit(no);

		/** 로그인 유저와 글쓴이가 같은 유저인지 확인 **/
		boolean authCheck = boardService.authUser(authUser, no);

		model.addAttribute("authCheck", authCheck);
		model.addAttribute("pageNo", pageNo);

		return "board/view";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Model model, 
			@RequestParam(value = "n", required = false) Long no,
			@RequestParam(value = "p", required = false, defaultValue = "1") Long pageNo) {
		
		model.addAttribute("no", no);
		model.addAttribute("pageNo", pageNo);

		return "board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo vo,
			@RequestParam(value = "n", required = false) Long no,
			@RequestParam(value = "p", required = false, defaultValue = "1") Long pageNo) {

		vo.setUserNo(authUser.getNo());

		/** 글쓰기와 답글달기 **/
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

			boardService.replyWrite(no, vo);

			return "redirect:/board/" + pageNo;
		}
	}

	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(
			@AuthUser UserVo authUser, Model model, 
			@RequestParam(value = "n", required = false) Long no,
			@RequestParam(value = "p", required = false, defaultValue = "1") Long pageNo) {

		/** 로그인 유저와 글쓴이가 같은 유저인지 확인 **/
		/** NO 레코드 데이터 가져오기 **/
		BoardVo vo = boardService.getByno(no);
		if (authUser.getNo() != vo.getUserNo()) {
			return "board/view?n=" + no + "&p=" + pageNo;
		}

		model.addAttribute("vo", vo);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("no", no);

		return "board/modify";
	}

	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, BoardVo vo,
			@RequestParam(value = "n", required = true) Long no,
			@RequestParam(value = "p", required = false, defaultValue = "1") Long pageNo) {

		 if(no == 0L || no == null ) {
			 return "redirect:/board";
		 }

		/** 로그인 유저와 글쓴이가 같은 유저인지 확인 **/
		BoardVo fvo = boardService.getByno(no);
		if (authUser.getNo() != fvo.getUserNo()) {
			return "board/view?n=" + no + "&p=" + pageNo;
		}

		/** 제목 글내용 업데이트 **/
		boardService.updateTitleAndContent(vo, no);

		return "redirect:/board/view?n=" + no + "&p=" + pageNo;
	}
    
	@Auth
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(
			@AuthUser UserVo authUser, 
			@RequestParam(value = "n", required = false ) Long no,
			@RequestParam(value = "p", required = false, defaultValue = "1") Long pageNo) {

		/** 로그인 유저와 글쓴이가 같은 유저인지 확인 **/
		BoardVo fvo = boardService.getByno(no);
		if (authUser.getNo() != fvo.getUserNo()) {
			return "redirect:board/" + pageNo;
		}

		fvo.setNo(no);

		boardService.delete(fvo);

		return "redirect:/board/" + pageNo;
	}

}
