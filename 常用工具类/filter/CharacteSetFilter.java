package com.skywing.utils.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;



/**
 * �ַ�������������������⣬ϵͳĬ�ϱ���GBK���룬����gbk2312���������֧
 * @author Administrator 
 * @version 1.0
 * @�������� 2006-2-25
 * @����ʱ�� 13:08:37
 */
public class CharacteSetFilter implements Filter{
	
	private String encode="GBK";
	private static final Logger logger = Logger.getLogger(CharacteSetFilter.class);
	
	/**
	 * �ַ����˿�ʼ����ϵͳ�����ļ��ж�ȡweb.xml�ж�ȡϵͳ�ַ������ʽ
	 */
	public void init(FilterConfig conf) throws ServletException {
		logger.info("ϵͳ����������ã�ϵͳĬ�ϵı���ΪGBK����");
		try{
			
			encode=conf.getInitParameter("encode");
			logger.info("ϵͳ����:"+encode);
			
		}catch(Exception ex){
			logger.error("�����ļ���д�쳣��");
		}
	}
	
   /**
   * �ַ�����������
   */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		request.setCharacterEncoding(encode);
		arg2.doFilter(arg0,arg1);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
