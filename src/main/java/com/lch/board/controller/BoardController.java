package com.lch.board.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lch.board.domain.BoardDomain;
import com.lch.board.service.BoardService;

@Controller
@RequestMapping("/")
public class BoardController {
	
	@RequestMapping("/boardList")
	public String boardList(Model model) throws SQLException{
		ArrayList<BoardDomain> boardList  = null;
		BoardService bs = new BoardService();
		String pageNm = "";
		
		boardList  = bs.boardList();
		if(boardList.size() > 0) {
			model.addAttribute("boardList",boardList);
			pageNm = "/board/boardList";
		}
		
		return pageNm;
	}
	
	@RequestMapping("insertBoard")
	public String insertBoard() {
		String pageNm = "";
		return pageNm;
	}
	
	@RequestMapping("deleteBoard")
	public String deleteBoard() {
		String pageNm = "";
		
		return pageNm ;
	}
	
	@RequestMapping("updateBoard")
	public String updateBoard() {
		String pageNm = "";
		
		return pageNm ;
	}
	

}
