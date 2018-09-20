package com.skywing.utils.security;

import org.apache.log4j.Logger;

import java.security.*;

/**
 * <p> ����������ֽ����� MD5 ֵ. �����ֽ����ݻ���ʮ�������ִ����ַ������͵�Դ����
 *     �Ծ�̬��ʽ�ṩ,����Ҫʵ����
 * </p>
 * MD5�����㷨
 * @author ��־�� 
 * @version 1.0
 * @�������� Mar 24, 2006
 * @����ʱ�� 3:54:43 PM
 */

public class MD5 {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MD5.class);
	
	  /**
	   * ���� MD5 ֵ,����ֵΪ byte[]
	   * @param src byte[]
	   * @return byte[]
	   */
	  public static final byte[] md5AsByte(byte[] src){
	    return getMDInstance().digest(src);
	  }
	  
	  /**
	   * 
	   * @param src
	   * @return
	   */
	   public static final String md5AsHexString(byte[] src){
		    return coverToHexString(getMDInstance().digest(src));
		  }
	
	  /**
	   * ��������������ת����ʮ�����Ƶ��ַ���
	   *
	   * @param src byte[]
	   * @return String
	   */
	  public static final String coverToHexString(byte[] src){
	    StringBuffer sb = new StringBuffer(32*2);
	    for (int i = 0 ; i < src.length ;i++){
	              byte currentByte = src[i];
	              byte highHalf= (byte) ((currentByte & 0xF0) >> 4);
	              byte lowHalf = (byte) (currentByte & 0x0F);
	              sb.append(Integer.toHexString(new Byte(highHalf).intValue()));
	              sb.append(Integer.toHexString(new Byte(lowHalf).intValue()));
	          }
	      return sb.toString();
	  }
	  
	  
	  public static  final  String md5AsString(byte[] src){
		    byte[] data = md5AsByte(src);
		    try{
		      return new String(data, "iso8859-1");
		    }catch (Exception e){
		      return ""; // never happend
		    }
		  }
	  
	  
	  private MD5(){
		System.out.println("MD5 struts()");
	  }
	  
	   /**
	   * ȡ�� MD5 ������ʵ��.
	   * �����̰߳�ȫ����.ÿһ�ε����㶼����ȡ��һ�� md5 ������ʵ��,�����ø�ʵ��.
	   * @return MessageDigest
	   */
	  private static final MessageDigest getMDInstance(){
		try {
			MessageDigest returnMessageDigest = MessageDigest
					.getInstance("MD5");
		
			return returnMessageDigest;
		} catch (NoSuchAlgorithmException e) {
		

			throw new RuntimeException("System error ,can't get MD5 Algorith provider");
		}
		  
	  }
	  
	  public static void  main(String args[]){
		  MD5 md=new MD5();
		  md.getMDInstance();
	  }


}
