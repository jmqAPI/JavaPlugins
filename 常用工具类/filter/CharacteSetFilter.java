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
 * 字符集过滤器解决编码问题，系统默认编码GBK编码，考虑gbk2312编码对中文支
 * @author Administrator 
 * @version 1.0
 * @创建日期 2006-2-25
 * @创建时间 13:08:37
 */
public class CharacteSetFilter implements Filter{
	
	private String encode="GBK";
	private static final Logger logger = Logger.getLogger(CharacteSetFilter.class);
	
	/**
	 * 字符过滤开始，从系统配置文件中读取web.xml中读取系统字符编码格式
	 */
	public void init(FilterConfig conf) throws ServletException {
		logger.info("系统编码参数配置，系统默认的编码为GBK编码");
		try{
			
			encode=conf.getInitParameter("encode");
			logger.info("系统编码:"+encode);
			
		}catch(Exception ex){
			logger.error("配置文件读写异常！");
		}
	}
	
   /**
   * 字符集编码设置
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
