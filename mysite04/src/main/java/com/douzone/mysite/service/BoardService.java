package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {
	@Autowired
    private BoardRepository boardRepository;

	/***데이터 총수 가져오기***/
	public Long findCount() {
		Long count = boardRepository.findCount();
		return count;
	}

	/**현재페이지번호에 해당되는 데이터 row개 가져오기**/
	public List<BoardVo> findLimitList( long row, Long pageNo) {
		long begin = 0;
		begin = (pageNo - 1) * row;
		List<BoardVo> list = boardRepository.findAll(begin, row);
		return list;
	}
    
	/** 페이지번호 총 갯수 계산후 셋팅 **/
	public int getPageCount(int row) {
		int pageCount = 0;
		Long count = boardRepository.findCount();
		if (count.intValue() < row) {
			pageCount = 1;
		} else if ((count.intValue() % row) > 0) {
			pageCount = count.intValue() / row + 1;
		} else {
			pageCount = count.intValue() / row;
		}
		return pageCount;
	}
    
	/**NO 레코드 데이터 가져오기**/
	public BoardVo getByno(Long no) {
		return boardRepository.findNo(no);
	}
    
	/**NO 레코드 hit 조회수 증가**/
	public boolean upHit(Long no) {
		return boardRepository.updateHit(no);	
	}

	public boolean authUser(UserVo authUser, Long no) {
		boolean authCheck = false;
		BoardVo vo =boardRepository.findNo(no);
		if(authUser.getNo() == vo.getUserNo()) {
			authCheck =true;
		}
		return authCheck;
	}

	public boolean newWrite(BoardVo vo) {
		return boardRepository.insert(vo);
	}

	public boolean replyWrite(Long no, BoardVo vo) {
		
		BoardVo fvo = new BoardVo();
		fvo = boardRepository.findNo(no);
		
		boardRepository.update(fvo);
		
		BoardVo upvo = new BoardVo();
		upvo.setTitle(vo.getTitle());
		upvo.setContents(vo.getContents());
        upvo.setUserNo(vo.getUserNo());
        
        upvo.setGroupNo(fvo.getGroupNo());
	    upvo.setOrderNo(fvo.getOrderNo() + 1);
	    upvo.setDepth(fvo.getDepth() + 1);
	    
		System.out.println("--------------------------------                  "+upvo);
		return boardRepository.insertReply(upvo);
		
	}

	public BoardVo findNo(Long no) {
		return boardRepository.findNo(no);
	}

	public boolean updateTitleAndContent(BoardVo vo, Long no) {
		return boardRepository.updateTitleAndContent(vo.getTitle(), vo.getContents(), no);
	}

	
	public boolean delete(BoardVo fvo) {
		return boardRepository.delete(fvo);
	}

	public boolean checkUser(UserVo authUser, Long no) {
		BoardVo fvo =getByno(no);
		if (authUser.getNo() != fvo.getUserNo()) {
			return true;
		}
		return false;
	}

	
	public Map<String, Object> inIt(Long pageNo, UserVo authUser) {
		
		/** 가져올 레코드 수 **/
		int row = 5;

		/** 넘어온 페이지번호가 널이면 1셋팅 **/
		if (pageNo == null) {
			pageNo = 1L;
		}

		/** 넘어온 페이지넘버가 0이면 1로 대체 **/
		if (pageNo == 0L) {
			pageNo = 1L;
		}

		/*** 데이터 총수 가져오기 ***/
		Long count = findCount();

		/** 페이지번호 총 갯수 계산후 셋팅 **/
		int pageCount = getPageCount(row);

		/** 넘어온 페이지넘버가 현재 페이지번호 총 갯수 보다 크면 제일 큰 페이지넘버 값으로 대체 **/
		if (pageCount > 0 && pageNo > pageCount) {
			pageNo = Long.valueOf(pageCount);
		}

		/** 현재페이지번호에 해당되는 데이터 row개 가져오기 **/
		List<BoardVo> limitList = findLimitList((long) row, pageNo);

		Map<String, Object> map = new HashMap<>();
		map.put("limitList", limitList);
		map.put("count", count);
		map.put("pageCount", pageCount);
		map.put("authUser", authUser);
		map.put("pageNo", pageNo);
		map.put("row", row);
		return map;
	}
	
}
