package com.skywing.utils.db;

import java.util.Vector;

public class PageBean {
	
	public int curPage; //当前是第几页

	public int maxPage; //一共有多少页

	public int maxRowCount; //一共有多少行

	public int rowsPerPage = 20;//默认每页多少行

	public java.util.Vector data;

	public PageBean() {
	}

	public void countMaxPage() {

		if (this.maxRowCount % this.rowsPerPage == 0) {
			this.maxPage = this.maxRowCount / this.rowsPerPage;
		} else {
			this.maxPage = this.maxRowCount / this.rowsPerPage + 1;
		}
	}

	public Vector getResult() {
		return this.data;
	}

	public PageBean(PageObject contact, String strwhere) throws Exception { //System.out.println("---------->PageBean()");
		if (strwhere == null) {
			strwhere = "";
		}
		this.maxRowCount = contact.getAvailableCount(strwhere); //得到总行数
		this.data = contact.getResult(); //得到要显示于本页的数据
		this.countMaxPage();
	}
} 
