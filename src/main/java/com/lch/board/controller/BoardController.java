package com.lch.board.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

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
	@RequestMapping("insertBoardForm")
	public String insertBoardForm()  {
		String pageNm = "";
		pageNm = "/board/insertBoardForm";
		return pageNm;
	}
	
	@RequestMapping( "insertBoard")
	public String insertBoard(HttpServletRequest req) throws SQLException {
		String pageNm = "";
		int check = 0;
		BoardService bs = new BoardService();
		check = bs.insertBoard(req);
		if(check >0) {
			pageNm ="redirect:/boardList";
		}
		return pageNm;
	}
	@RequestMapping("selectBoard")
	public String selectBoard(Model model,HttpServletRequest req) throws SQLException {
		BoardDomain selectBoard = new BoardDomain();
		String pageNm = "";
		BoardService bs = new BoardService();
		selectBoard = bs.selcetBoard(req);
		
		if(selectBoard != null) {
			model.addAttribute("selectBoard",selectBoard);
			pageNm = "/board/selectBoard";
		}
		return pageNm;
	}
	
	@RequestMapping("deleteBoard")
	public String deleteBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		String pageNm = "";
		BoardService bs = new BoardService();
		check = bs.deleteBoard(req);
		if(check > 0) {
			pageNm = "redirect:/boardList";
		}
		
		return pageNm ;
	}
	
	@RequestMapping("updateBoard")
	public String updateBoard(HttpServletRequest req) throws SQLException {
		int check = 0;
		String pageNm = "";
		BoardService bs = new BoardService();
		check = bs.updateBoard(req);
		if(check > 0) {
			pageNm = "redirect:/boardList";
		}
		return pageNm ;
	}
	

}
