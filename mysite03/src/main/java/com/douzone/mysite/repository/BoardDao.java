package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardDao {
	public boolean insert(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "insert into board values(null, ?, ?, 0, now(), ifnull((select max(b.group_no) from board b),0) + 1, 0, 0, ?)";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
            pstmt.setString(1,vo.getTitle());
            pstmt.setString(2,vo.getContents());
            pstmt.setLong(3, vo.getUserNo());
            

			// 5. SQL 실행
			int count = pstmt.executeUpdate();

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("insert error:" + e);
		} finally {
			// clean up
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("finally error:" + e);
			}
		}

		return result;
	}

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = 
					"   select b.no, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y-%m-%d %H:%i:%s'), b.group_no, b.order_no, b.depth, u.name, u.no"
					+ " from board b join user u on b.user_no = u.no" 
					+ " order by group_no desc, order_no asc";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)

			// 5. SQL 실행
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				String name = rs.getString(9);
				Long userNo = rs.getLong(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setName(name);
				vo.setUserNo(userNo);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("findAll error:" + e);
		} finally {
			// clean up
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<BoardVo> findAll(Long begin, Long row) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = 
					"   select b.no, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y-%m-%d %H:%i:%s'), b.group_no, b.order_no, b.depth, u.name, u.no"
					+ " from board b join user u on b.user_no = u.no" 
					+ " order by group_no desc, order_no asc limit ?,?";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
            pstmt.setLong(1, begin);
            pstmt.setLong(2, row);
			
			
			// 5. SQL 실행
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				String name = rs.getString(9);
				Long userNo = rs.getLong(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setName(name);
				vo.setUserNo(userNo);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("findAll error:" + e);
		} finally {
			// clean up
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public BoardVo findNo(Long no) {
		BoardVo vo = new BoardVo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = 
					"   select b.no, b.title, b.contents, u.no, b.group_no, b.order_no, b.depth"
					+ " from board b join user u on b.user_no = u.no"
					+ " where b.no=?";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			pstmt.setLong(1, no);

			// 5. SQL 실행
			rs = pstmt.executeQuery();
            
			if (rs.next()) {
				Long reNo = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long userNo = rs.getLong(4);
				Long groupNo = rs.getLong(5);
				Long orderNo = rs.getLong(6);
				Long depth = rs.getLong(7);
				
				vo.setNo(reNo);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserNo(userNo);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
			}
	

		} catch (SQLException e) {
			System.out.println("findNo error:" + e);
		} finally {
			// clean up
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vo;
		
	}
    
	public Long findCount() {
		Long count = 0L;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = 
					"   select count(*)"
					+ " from board;";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			
			// 5. SQL 실행
			rs = pstmt.executeQuery();
            
			if (rs.next()) {	
				count = rs.getLong(1);
			}
	

		} catch (SQLException e) {
			System.out.println("findCount error:" + e);
		} finally {
			// clean up
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
		
	}
    
	public boolean delete(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = 
					"   update board "
					+ " set title = ?, contents=? "
					+ " where no=?";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());

			// 5. SQL 실행
			int count = pstmt.executeUpdate();

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("delete error:" + e);
		} finally {
			// clean up
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("finally error:" + e);
			}
		}

		return result;
	}
	
	public boolean updateHit(Long no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = 
					  "  update board"
					+ "  set hit = hit + 1"
					+ "  where no = ?";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			pstmt.setLong(1, no);

			// 5. SQL 실행
			int count = pstmt.executeUpdate();

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("updateHit error:" + e);
		} finally {
			// clean up
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("finally error:" + e);
			}
		}

		return result;
		
	}
	
	public boolean updateTitleAndContent(String title, String content, Long no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = 
					  "  update board"
					+ "  set title = ?, contents = ?"
					+ "  where no = ?";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			pstmt.setString(1,title);
			pstmt.setString(2,content);
			pstmt.setLong(3, no);

			// 5. SQL 실행
			int count = pstmt.executeUpdate();

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("updateHit error:" + e);
		} finally {
			// clean up
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("finally error:" + e);
			}
		}

		return result;
		
	}
	
	public boolean update(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = 
					  "   update board "
					  + " set order_no = order_no + 1 "
					  + " where group_no = ? and order_no > ?";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
			pstmt.setLong(1,vo.getGroupNo());
			pstmt.setLong(2,vo.getOrderNo());
			

			// 5. SQL 실행
			int count = pstmt.executeUpdate();

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("updateHit error:" + e);
		} finally {
			// clean up
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("finally error:" + e);
			}
		}

		return result;
		
	}
	
	public boolean insertReply(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩(binding)
            pstmt.setString(1,vo.getTitle());
            pstmt.setString(2,vo.getContents());
            pstmt.setLong(3, vo.getGroupNo());
            pstmt.setLong(4, vo.getOrderNo());
            pstmt.setLong(5, vo.getDepth());
            pstmt.setLong(6, vo.getUserNo());
           
            

			// 5. SQL 실행
			int count = pstmt.executeUpdate();

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("insertReply:" + e);
		} finally {
			// clean up
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
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
