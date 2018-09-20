package com.skywing.utils.db;

import java.util.Vector;

public interface PageObject {
	/**
	 * ���������ܹ�����ҳ��
	 * @param strwhere
	 * @return
	 * @throws Exception
	 */
	public int getAvailableCount(String strwhere)throws Exception;
	/**
	 * ��ʾָ��������
	 * @param page
	 * @param strwhere
	 * @return
	 * @throws Exception
	 */
	public PageBean listData(String page,String strwhere)throws Exception;
	
	/**
	 * ȡ�����ݼ���
	 * @return
	 * @throws Exception
	 */
	public Vector getResult()throws Exception;

}
