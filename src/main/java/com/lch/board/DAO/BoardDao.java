package com.lch.board.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.lch.board.JDBCInfo.JDBCInfo;
import com.lch.board.domain.BoardDomain;

public class BoardDao {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	
	public ArrayList<BoardDomain> boardList() throws SQLException{
		ArrayList<BoardDomain> boardList = null;
			boardList = new ArrayList<BoardDomain>();
		conn = JDBCInfo.getConnection();
		String sql = "select * from board";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			
			while(rs.next()) {
				BoardDomain bd = new BoardDomain();
				bd.setBoardNum(rs.getInt("boardNum"));
				bd.setTitle(rs.getString("title"));
				bd.setContents(rs.getString("contents"));
				boardList.add(bd);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs.close();
		stmt.close();
		conn.close();
		
		return boardList;
	}
	public ArrayList<BoardDomain> selectBoard(HttpServletRequest req) throws SQLException{
		ArrayList<BoardDomain> boardList = null;
			boardList = new ArrayList<BoardDomain>();
		
		String boardNum = "";
		boardNum = req.getParameter("boardNum");
		
		conn=JDBCInfo.getConnection();
		String sql = "select * from board where boardNum = ?";	
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, boardNum);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			BoardDomain bd = new BoardDomain();
			bd.setBoardNum(rs.getInt("boardNum"));
			bd.setTitle(rs.getString("title"));
			bd.setContents(rs.getString("contents"));
			boardList.add(bd);
		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return boardList;
	}
	
	public void replay(HttpServletRequest req) throws SQLException {
//		ArrayList<repl>
		
		conn  = JDBCInfo.getConnection();
		String sql = "insert into replyBoard values(?,?,?,?)";
		String boardNum = "";
		String replyNum = "";
		String contents = "";
		
		boardNum = req.getParameter("boardNum");
		replyNum = req.getParameter("replyNum");
		contents = req.getParameter(contents);
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, boardNum);
		pstmt.setString(2, replyNum);
		pstmt.setString(3, contents);
		conn.commit();
		
		String replySql = "select * from replyBoard where replyNum = ?";
		pstmt = conn.prepareStatement(replySql);
		pstmt.setString(1, replyNum);
		
		rs = pstmt.executeQuery();
		if(rs.next()) {
			
		}
	}
	
	public int insertBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		String title = "";
		String contents = "";
		title = req.getParameter("title");
		contents = req.getParameter("contents");
		conn = JDBCInfo.getConnection();
		String insertBoard = "insert into board values(null, ?,?)";
		pstmt = conn.prepareStatement(insertBoard);
		pstmt.setString(1, title);
		pstmt.setString(2, contents);
		pstmt.executeUpdate();	
		conn.commit();
		
		check = pstmt.executeUpdate();
		
		if(check >0) {
			System.out.println("추가완료");
			check = 1;
		}
		return check;
	}

	
	public int  deleteBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		String boardNum = req.getParameter("boardNum");
		conn = JDBCInfo.getConnection();
		String deleteSQL = "delete from board where boardNum = ?";
		pstmt = conn.prepareStatement(deleteSQL);
		pstmt.setString(1, boardNum);
		check = pstmt.executeUpdate();
		conn.commit();
		
		if(check>0) {
			System.out.println("삭제완료");
			check = 1;
		}
		pstmt.close();
		conn.close();
		
		return check;
	}
	
	public int updateBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		String contents = req.getParameter("contents");
		String title = req.getParameter("title");
		String boardNum = req.getParameter("boardNum");
		conn = JDBCInfo.getConnection();
		
		String updateSQL ="update board set contents = ?,title = ? where boardNum=? ";
		pstmt = conn.prepareStatement(updateSQL);
		pstmt.setString(1, contents);
		pstmt.setString(2, title);
		pstmt.setString(3, boardNum);
		
		check = pstmt.executeUpdate();
		conn.commit();
		
		if(check > 0) {
			System.out.println("수정완료");
			check = 1;
		}
		pstmt.close();
		conn.close();
		return check ;
	}
	
	public BoardDomain boardInfo(HttpServletRequest req) throws SQLException {
		
		BoardDomain boardSelect = null;
		String boardNum = "";
		boardNum = req.getParameter("boardNum");
		String selectSql = "select * from board where boardNum = ?";
		conn = JDBCInfo.getConnection();
		pstmt = conn.prepareStatement(selectSql);
		pstmt.setString(1,boardNum);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			boardSelect = new BoardDomain();
			boardSelect.setBoardNum(Integer.parseInt(boardNum));
			boardSelect.setTitle(rs.getString("title"));
			boardSelect.setContents(rs.getString("contents"));
		}
		
		return boardSelect;
	}

}
