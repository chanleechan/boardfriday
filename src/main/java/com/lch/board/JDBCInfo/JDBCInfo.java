package com.lch.board.JDBCInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCInfo {
	public static Connection getConnection() throws SQLException {
		String jdbcUrl = "jdbc:mysql://localhost:3306/board";
		String userId = "root";
		String userPass = "0000";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(jdbcUrl,userId,userPass);
		conn.setAutoCommit(false);
		return conn;
	}
	
	public void close(Connection conn) {
		close(conn);
	}
	
	public void commit(Connection conn) {
		
		commit(conn);
	}
	
	public void rollback(Connection conn) {
		
		rollback(conn);
	}
	public void close(Statement stmt) {
		
		close(stmt);
	}
	
	public void close(ResultSet rs) {
		close(rs);
	}

}
