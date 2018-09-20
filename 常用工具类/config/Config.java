
package com.skywing.utils.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**

 *  类说明：读取信息仓库的全局配置信息。配置文件为　config.properties
 *  注意事项：构造时读取配置信息。本类不能从外部访问，访问入口在　Global 类。
 * @author 李志峰 
 * @version 1.0
 * @创建日期 Mar 11, 2006
 * @创建时间 12:44:09 PM
 */
    class Config {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Config.class);


	private static final String CONFIG_FILE = "config.properties";
	
	private String JDBCDRIVER;//数据库驱动程序类型	
	private String JDBCURL;//驱动程序URL;
	private String JDBCUSER;//数据库用户
	private String JDBCPASSWORD;//数据库密码
	private String DATASOURCE;//数据源名称

	private String ENCODE;//系统字符编码配置
	
    
	/**
	 * 构造并读取配置文件
	 */
	public Config() {
		// 初始化，从配置文件中读取全局配置。
		// 此种全局信息，不需要监视文件变化
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
			
			logger.error("Config()"+ "找不到配置文件： " + CONFIG_FILE);
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
	 * 读取 conf 里的配置信息，设置到类实例变量。
	 * 注意类型转换
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
