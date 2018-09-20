package com.skywing.utils.db;

import java.util.Vector;

public interface PageObject {
	/**
	 * 计算数据总共多少页面
	 * @param strwhere
	 * @return
	 * @throws Exception
	 */
	public int getAvailableCount(String strwhere)throws Exception;
	/**
	 * 显示指定的数据
	 * @param page
	 * @param strwhere
	 * @return
	 * @throws Exception
	 */
	public PageBean listData(String page,String strwhere)throws Exception;
	
	/**
	 * 取得数据集合
	 * @return
	 * @throws Exception
	 */
	public Vector getResult()throws Exception;

}
