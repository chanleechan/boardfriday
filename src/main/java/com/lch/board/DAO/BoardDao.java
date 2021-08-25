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
import com.lch.board.domain.PageDomain;
public class BoardDao {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	
	public ArrayList<BoardDomain> boardList(HttpServletRequest req) throws SQLException{
		ArrayList<BoardDomain> boardList = null;
			boardList = new ArrayList<BoardDomain>();
		PageDomain pd = new PageDomain();
		
		int page;
		String stringPage = "1";
		//최초 들어오는 getParameter는 null 이후 a태그로 인하여 getParameter전달됨
		if(req.getParameter("pageNum") == null || "null".equals(req.getParameter("pageNum"))) {
			stringPage = "1";
		}else {
			stringPage = req.getParameter("pageNum");
		}
		
		page = Integer.parseInt(stringPage);
		int pageNum = 10;
		if(page <=0) {
			page = 0;
		}
		page = (page-1) * 10;
		pd.setPage(page);
		pd.setPageNum(pageNum);

		page = pd.getPage();
		pageNum = pd.getPageNum();

		conn = JDBCInfo.getConnection();
		//String sql = "select * from board order by boardNum desc limit ?,? ";
		String sql = "select boardNum,boardSeq , title, contents, groupNum, groupLevel "
				+ "from board "
				+ "order by boardSeq desc, groupNum desc limit ?,?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			pstmt.setInt(2, pageNum);
			rs = pstmt.executeQuery();


			while(rs.next()) {
				BoardDomain bd = new BoardDomain();
				bd.setBoardNum(rs.getInt("boardNum"));
				bd.setBoardSeq(rs.getInt("boardSeq"));
				bd.setTitle(rs.getString("title"));
				bd.setContents(rs.getString("contents"));
				boardList.add(bd);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		rs.close();
		pstmt.close();
		conn.close();

		return boardList;
	}
	public ArrayList<Integer> totalNum() throws SQLException{
		ArrayList<Integer> pageNumList = new ArrayList<Integer>();
		ArrayList<PageDomain> totalNumList = new ArrayList<PageDomain>();
		conn = JDBCInfo.getConnection();
		String totalNumSQL = "select count(boardNum) as totalNum from board";
		
		pstmt = conn.prepareStatement(totalNumSQL);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			PageDomain pd = new PageDomain();
			pd.setTotalNum(rs.getInt("totalNum"));
			totalNumList.add(pd);
			
			int totalNum = 0;
			
			totalNum = pd.getTotalNum()/10;
			
			if(pd.getTotalNum()%10 >0) {
				totalNum++;
			}
			for(int i = 1; i<=totalNum; i++) {
				pageNumList.add(i);
			}
		}
		
		return pageNumList;
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
			bd.setBoardSeq(rs.getInt("boardSeq"));
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
		String insertBoard = "insert into board(boardNum,title,contents,groupNum,groupLevel,boardSeq) "
				+ "values(null, ?,?,(select max(boardSeq)+1 from board as t1),0,(select max(boardSeq)+1 from board as t1))";
		pstmt = conn.prepareStatement(insertBoard);
		pstmt.setString(1, title);
		pstmt.setString(2, contents);
		pstmt.executeUpdate();	
		conn.commit();
		
		check = pstmt.executeUpdate();
		
		if(check >0) {
			System.out.println("추가성공");
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
			System.out.println("삭제성공");
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
			System.out.println("수정성공");
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
			boardSelect.setBoardSeq(rs.getInt("boardSeq"));
			boardSelect.setTitle(rs.getString("title"));
			boardSelect.setContents(rs.getString("contents"));
		}
		return boardSelect;
	}
	public ArrayList<BoardDomain> searchContents(HttpServletRequest req) throws SQLException{
		ArrayList<BoardDomain> searchList = null;
		searchList = new ArrayList<BoardDomain>();
		PageDomain pd = new PageDomain();
		int page;
		String stringPage = "1";
		
		//최초 들어오는 getParameter는 null 이후 a태그로 인하여 getParameter전달됨
		if(req.getParameter("pageNum") == null || "null".equals(req.getParameter("pageNum"))) {
			stringPage = "1";
		}else {
			stringPage = req.getParameter("pageNum");
		}
		
		page = Integer.parseInt(stringPage);
		int pageNum = 10;
		if(page <=0) {
			page = 0;
		}
		page = (page-1) * 10;
		pd.setPage(page);
		pd.setPageNum(pageNum);

		page = pd.getPage();
		pageNum = pd.getPageNum();
		
		
		String search = "";
		String searchSQL = "";
//		if(req.getParameter("contents") == null  || "".equals(req.getParameter("contents"))) {
//			searchSQL = "select * from board";
//			search = "";
//		}else {
			search = req.getParameter("contents");
			searchSQL = "select * from board where contents like ? order by boardNum desc limit ?,? ";
//		}
		conn = JDBCInfo.getConnection();
		
		pstmt = conn.prepareStatement(searchSQL);
		pstmt.setString(1, "%"+search+"%");
		pstmt.setInt(2, page);
		pstmt.setInt(3, pageNum);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			BoardDomain bd = new BoardDomain();
			bd.setBoardNum(rs.getInt("boardNum"));
			bd.setTitle(rs.getString("title"));
			bd.setContents(rs.getString("contents"));
			searchList.add(bd);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return searchList;
	}
	
	public int insertReplyBoard(HttpServletRequest req) throws SQLException {
//		ReplyDomain replyBoard  = new ReplyDomain();
		
		int check = 0;
		int boardSeq = 0;
		String title = "";
		String contents = "";
		boardSeq = Integer.parseInt(req.getParameter("boardSeq"));
		title = req.getParameter("title");
		contents = req.getParameter("contents");
		
		String replySQL = "insert into board(boardNum,title,contents,groupNum,groupLevel,boardSeq) " + 
				"	values(null,?,?, " + 
				"	(select b.groupNum from board b where boardSeq = ?), " + 
				"	(select max(b.groupLevel)+1 from board b where groupNum = ?), " + 
				"	(select b.boardSeq from board b where groupNum = ?) " + 
				");";
		
		conn = JDBCInfo.getConnection();
		pstmt = conn.prepareStatement(replySQL);
		pstmt.setString(1, title);
		pstmt.setString(2, contents);
		pstmt.setInt(3, boardSeq);
		pstmt.setInt(4, boardSeq);
		pstmt.setInt(5, boardSeq);
		check = pstmt.executeUpdate();
		conn.commit();
		
		if(check > 0) {
			check = 1;
		}
		
		return check;
	}
	public int deleteReplyBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		String replyNum = req.getParameter("replyNum");
		String deleteSQL = "delete from replyboard where replyNum = ?";
		conn = JDBCInfo.getConnection();
		
		pstmt = conn.prepareStatement(deleteSQL);
		pstmt.setString(1, replyNum);
		check = pstmt.executeUpdate();
		if(check > 0) {
			check = 1;
		}
		return check ;
	}
}
