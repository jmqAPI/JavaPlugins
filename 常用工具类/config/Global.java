package com.skywing.utils.config;

import java.io.Serializable;

/**
 *  类说明：　信息仓库信息全局常量定义与全局公共配置信息。
 *  　　　    不可变逻辑常量定义为变量。
 * 　　　　　 可变动维护的常量在infowareconfig.properties 中定义。初始化时载入。
 *  注意事项：
 *          配置的更新需要重新部署启动 application  
 * @author 李志峰 
 * @version 1.0
 * @创建日期 Mar 11, 2006
 * @创建时间 12:50:15 PM
 */

public final class Global implements Serializable {
	
	public static final String SESSION_USER_KEY = "WATER_SESSION_KEY_2006";
    
    private static Config conf = new Config();
    
    
    /**
     * 
     * @return 返回数据库连接
     */
    public static String getJdbcDriver(){
    	return conf.getJDBCDRIVER();
    }
    
    /**
     * @return返回数据库URL地址
     */
    public static String getJdbcUrl(){
    	return conf.getJDBCURL();
    }
    
    /**
     * 
     * @return返回数据库用户名
     */
    public static String getJdbcUser(){
    	return conf.getJDBCUSER();
    }
    
    /**
     * 
     * @return返回数据密码
     */
    public static String getJdbcPassword(){
    	return conf.getJDBCPASSWORD();
    }
    
    
  /**
   * 
   * @return 返回系统字符集合
   */
    public static String getDataSource(){
    	return  conf.getDATASOURCE();
    }
    
    public static String getEncode(){
    	return conf.getENCODE();
    }
    
    
}