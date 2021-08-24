package com.lch.board.domain;

public class PageDomain {
	private int page;
	private int pageNum;
	
	public PageDomain() {
		this.page = 1;
		this.pageNum = 10;
	}
	
	public void setPage(int page) {
		if(page<=0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPageNum(int pageNum) {
		if(pageNum <=0 || pageNum>100) {
			this.pageNum = 10;
			return;
		}
		this.pageNum = pageNum;
	}
	
	public int getPageNum() {
		return this.pageNum;
	}
	
	public int getFirstPage() {
		return (this.page -1) * pageNum;
	}

}
