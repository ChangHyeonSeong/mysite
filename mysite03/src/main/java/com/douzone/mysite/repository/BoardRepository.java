package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;



@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo) == 1;
	}

	public List<BoardVo> findAll(Long begin, Long row) {
		Map<String, Long> map = new HashMap<>();
		map.put("begin", begin);
		map.put("row", row);
		List<BoardVo> list = sqlSession.selectList("board.findAll", map);
		return  list;
	}

	public BoardVo findNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}

	public Long findCount() {
		Long count = 0L;
		count = sqlSession.selectOne("board.findCount");
		return count;
	}
    
	public boolean updateHit(Long no) {
		boolean result = false;
		result = sqlSession.update("board.updateHit", no) == 1;
		return result;
	}
	
	public boolean delete(BoardVo vo) {
		boolean result = false;
		result = sqlSession.delete("board.delete", vo) == 1;
		return result;
	}
	
	public boolean updateTitleAndContent(String title, String contents, Long no) {
		boolean result = false;
		Map<String, Object> map = new HashMap<>();
		map.put("title", title);
		map.put("contents", contents);
		map.put("no", no);
		result = sqlSession.update("board.updateTitleAndContent", map) == 1;
		return result;
	}
	
	public boolean update(BoardVo vo) {
		boolean result = false;
        result = sqlSession.update("board.update", vo) == 1;
		return result;

	}
	
	public boolean insertReply(BoardVo vo) {
		boolean result = false;
		result = sqlSession.insert("board.insertReply", vo) == 1;
		return result;

	}
	

	
}
