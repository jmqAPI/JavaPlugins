package com.skywing.utils.security;

import org.apache.log4j.Logger;

import java.security.*;

/**
 * <p> 计算给定的字节流的 MD5 值. 给出字节数据或是十六进制字串或字符串类型的源数据
 *     以静态方式提供,不需要实例化
 * </p>
 * MD5加密算法
 * @author 李志峰 
 * @version 1.0
 * @创建日期 Mar 24, 2006
 * @创建时间 3:54:43 PM
 */

public class MD5 {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MD5.class);
	
	  /**
	   * 计算 MD5 值,返回值为 byte[]
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
	   * 将二进制数据流转换成十六进制的字符串
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
	   * 取得 MD5 运算器实例.
	   * 出于线程安全考虑.每一次的运算都重新取得一次 md5 运行器实例,不重用该实例.
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
