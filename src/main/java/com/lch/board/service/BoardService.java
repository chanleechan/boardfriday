package com.lch.board.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.lch.board.DAO.BoardDao;

//import org.springframework.stereotype.Service;

import com.lch.board.domain.BoardDomain;

public class BoardService {
	
	public ArrayList<BoardDomain> boardList() throws SQLException{
		ArrayList<BoardDomain> boardList= 
				new ArrayList<BoardDomain>();
		BoardDao bd = new BoardDao();
		boardList = bd.boardList();
		return boardList;
	}

}
