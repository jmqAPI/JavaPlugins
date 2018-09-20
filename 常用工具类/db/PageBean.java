package com.skywing.utils.db;

import java.util.Vector;

public class PageBean {
	
	public int curPage; //��ǰ�ǵڼ�ҳ

	public int maxPage; //һ���ж���ҳ

	public int maxRowCount; //һ���ж�����

	public int rowsPerPage = 20;//Ĭ��ÿҳ������

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
		this.maxRowCount = contact.getAvailableCount(strwhere); //�õ�������
		this.data = contact.getResult(); //�õ�Ҫ��ʾ�ڱ�ҳ������
		this.countMaxPage();
	}
} 
