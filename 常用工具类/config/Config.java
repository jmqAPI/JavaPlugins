
package com.skywing.utils.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**

 *  ��˵������ȡ��Ϣ�ֿ��ȫ��������Ϣ�������ļ�Ϊ��config.properties
 *  ע���������ʱ��ȡ������Ϣ�����಻�ܴ��ⲿ���ʣ���������ڡ�Global �ࡣ
 * @author ��־�� 
 * @version 1.0
 * @�������� Mar 11, 2006
 * @����ʱ�� 12:44:09 PM
 */
    class Config {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Config.class);


	private static final String CONFIG_FILE = "config.properties";
	
	private String JDBCDRIVER;//���ݿ�������������	
	private String JDBCURL;//��������URL;
	private String JDBCUSER;//���ݿ��û�
	private String JDBCPASSWORD;//���ݿ�����
	private String DATASOURCE;//����Դ����

	private String ENCODE;//ϵͳ�ַ���������
	
    
	/**
	 * ���첢��ȡ�����ļ�
	 */
	public Config() {
		// ��ʼ�����������ļ��ж�ȡȫ�����á�
		// ����ȫ����Ϣ������Ҫ�����ļ��仯
		super();
		if (logger.isDebugEnabled()) {
			logger.debug("Config() - start");
		}
		InputStream input = null;
		Properties conf = new Properties();
		try {
			input = Global.class.getResourceAsStream(CONFIG_FILE);
			conf.load(input);
		} catch (IOException e) {
			
			logger.error("Config()"+ "�Ҳ��������ļ��� " + CONFIG_FILE);
		}
		finally{
			if (input != null ) 
				try{
					input.close();
				}catch(Exception closeE){
					logger.error("Config()", closeE);
}
		}
	  setConfValue(conf);
		if (logger.isDebugEnabled()) {
			logger.debug("Config() - end");
		}
	}

	/**
	 * ��ȡ conf ���������Ϣ�����õ���ʵ��������
	 * ע������ת��
	 * @param conf
	 */
	private void setConfValue(Properties conf){
		
		this.JDBCDRIVER=conf.getProperty("jdbcdriver");
		this.JDBCURL=conf.getProperty("jdbcurl");
		this.JDBCUSER=conf.getProperty("user");
		this.JDBCPASSWORD=conf.getProperty("password");
		this.DATASOURCE=conf.getProperty("datasource");
		this.ENCODE=conf.getProperty("encode");
	}

	public static String getCONFIG_FILE() {
		return CONFIG_FILE;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getJDBCDRIVER() {
		return JDBCDRIVER;
	}

	public String getJDBCPASSWORD() {
		return JDBCPASSWORD;
	}

	public String getJDBCURL() {
		return JDBCURL;
	}

	public String getJDBCUSER() {
		return JDBCUSER;
	}
	
	public String getDATASOURCE(){
		return DATASOURCE;
	}

	public String getENCODE() {
		return ENCODE;
	}


}
