package com.skywing.utils.config;

import java.io.Serializable;

/**
 *  ��˵��������Ϣ�ֿ���Ϣȫ�ֳ���������ȫ�ֹ���������Ϣ��
 *  ������    ���ɱ��߼���������Ϊ������
 * ���������� �ɱ䶯ά���ĳ�����infowareconfig.properties �ж��塣��ʼ��ʱ���롣
 *  ע�����
 *          ���õĸ�����Ҫ���²������� application  
 * @author ��־�� 
 * @version 1.0
 * @�������� Mar 11, 2006
 * @����ʱ�� 12:50:15 PM
 */

public final class Global implements Serializable {
	
	public static final String SESSION_USER_KEY = "WATER_SESSION_KEY_2006";
    
    private static Config conf = new Config();
    
    
    /**
     * 
     * @return �������ݿ�����
     */
    public static String getJdbcDriver(){
    	return conf.getJDBCDRIVER();
    }
    
    /**
     * @return�������ݿ�URL��ַ
     */
    public static String getJdbcUrl(){
    	return conf.getJDBCURL();
    }
    
    /**
     * 
     * @return�������ݿ��û���
     */
    public static String getJdbcUser(){
    	return conf.getJDBCUSER();
    }
    
    /**
     * 
     * @return������������
     */
    public static String getJdbcPassword(){
    	return conf.getJDBCPASSWORD();
    }
    
    
  /**
   * 
   * @return ����ϵͳ�ַ�����
   */
    public static String getDataSource(){
    	return  conf.getDATASOURCE();
    }
    
    public static String getEncode(){
    	return conf.getENCODE();
    }
    
    
}