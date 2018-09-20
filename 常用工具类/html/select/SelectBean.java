package com.skywing.utils.html.select;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 生成选择内容的类
 * @author Administrator 
 * @version 1.0
 * @创建日期 2006-3-6
 * @创建时间 6:12:10
 */
public class SelectBean {

	public SelectBean() {
	}
	
	/**
	 * 
	 * @param option
	 * @return
	 */
    
	public static String makeSelect(String[] option) {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=0").append("请选择...").append(">").append(
				"请选择...").append("</option>\n");
		for (int i = 0; i < option.length; i++) {
			sb.append("<option value=").append(i).append(">").append(option[i])
					.append("</option>\n");
		}
		return sb.toString();
	}

	public static String makeSelect(String[] option, String oldval) {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=0").append(oldval).append(">")
				.append("请选择...").append("</option>\n");
		for (int i = 0; i < option.length; i++) {
			System.out.println(i);
			sb.append("<option value=").append(i).append(">").append(option[i])
					.append("</option>\n");
		}
		return sb.toString();
	}

	public static String makeSelect(Vector v) {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=-2").append(">").append("请选择...").append(
				"</option>\n");
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			String[] obj = (String[]) e.nextElement();
			sb.append("<option value=").append(obj[0]).append(">").append(
					obj[1]).append("</option>\n");

		}
		return sb.toString();

	}
	public static String SelectName(Vector v, String id) {
		StringBuffer sb = new StringBuffer();
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			String[] obj = (String[]) e.nextElement();
			if (obj[0].equals(id)) {
				sb.append("<option value=").append(obj[0]).append(">").append(
						obj[1]).append("</option>\n");
				break;
			}
		}
		Enumeration e1 = v.elements();
		while (e1.hasMoreElements()) {
			String[] obj = (String[]) e1.nextElement();
			if (obj[0].equals(id))
				continue;
			sb.append("<option value=").append(obj[0]).append(">").append(
					obj[1]).append("</option>\n");

		}

		return sb.toString();
	}

	
	
	public static String toName(Vector v, String id) {
		String sb = "";
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			String[] obj = (String[]) e.nextElement();
			if (obj[0].equals(id)) {
				sb = obj[1];
				break;
			}
		}

		return sb;
	}

}
