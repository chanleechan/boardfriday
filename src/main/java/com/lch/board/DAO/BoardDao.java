package com.lch.board.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lch.board.JDBCInfo.JDBCInfo;
import com.lch.board.domain.BoardDomain;

public class BoardDao {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public ArrayList<BoardDomain> boardList() throws SQLException{
		ArrayList<BoardDomain> boardList = null;
		conn = JDBCInfo.getConnection();
		String sql = "select * from board";
		
		try {
			
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<BoardDomain>();
			
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
	
	public ArrayList<BoardDomain> selectBoard(){
		ArrayList<BoardDomain> boardList = null;
		
		return null;
	}
	

}
