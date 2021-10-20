package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {
	public boolean insert(GuestbookVo vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			String sql = 
					"insert into guestbook values(null, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩(binding)
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			
			
			//5. SQL 실행
			int count = pstmt.executeUpdate();
			
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("insert error:" + e);
		} finally {
			// clean up
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("finally error:" + e);
			}
		}
		
		return result;		
	}
	
	public List<GuestbookVo> findAll() throws GuestbookRepositoryException{
//		StopWatch sw = new StopWatch();
//		sw.start();
		List<GuestbookVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			String sql = 
				"   select no, name,password, date_format(reg_date, '%Y/%m/%d %H:%i:%s'), message"
				+ " from guestbook"
				+ " order by reg_date desc";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩(binding)
			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String regDate = rs.getString(4);
				String message = rs.getString(5);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setRegDate(regDate);
				vo.setMessage(message);
				
				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("findAll error:" + e);
			throw new GuestbookRepositoryException(e.toString());
		} finally {
			// clean up
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		sw.stop();
//		Long totalTime = sw.getTotalTimeMillis();
//		System.out.println("[Execution Time][GuestbookRepository.findAll] " + totalTime +" millis");
		return result;
	}
	
	public boolean delete(GuestbookVo vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			String sql = 
				"    delete "
				+ "   from guestbook"
				+ "  where no = ?"
				+ "    and password = ?";
			pstmt = conn.prepareStatement(sql);
			
			//4. 바인딩(binding)
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			//5. SQL 실행
			int count = pstmt.executeUpdate();
			
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("delete error:" + e);
		} finally {
			// clean up
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("finally error:" + e);
			}
		}
		
		return result;		
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mysql://127.0.0.1:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
}
