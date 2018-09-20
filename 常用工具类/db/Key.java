package com.skywing.utils.db;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * 该类用与产生主键，通用与不同的数据库，目前部分数据库不支持seq 该方法实现了单例模式
 * 
 * @author Administrator
 * @version 1.0
 * @创建日期 2006-3-6
 * @创建时间 11:19:16 注意事项：为保证事物的完整性我们在该方法在不关闭连接对象，该连接对象的关闭需要在 业务逻辑中去实现
 */
public class Key {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Key.class);

	private Key() {
	}

	private static Key keygen = new Key();

	public synchronized int getNextKey(String keyname, Connection conn) throws SQLException   {
		return getNextKeyFromDB(keyname, conn);
	}

	public static Key getInstance() {
		return keygen;
	}

	public int getNextKeyFromDB(String keyname, Connection conn) throws SQLException{
		int number=0;
		int key = 0;
		ResultSet rs = null;
		Statement stmt = null;
		String strsql1 = "update t_key set keys=keys+1 where name='" + keyname
				+ "'";
		String strsql2 = " select keys from t_key where name='" + keyname + "'";
		try {
			stmt = conn.createStatement();
			number = stmt.executeUpdate(strsql1);
			rs = stmt.executeQuery(strsql2);
			rs.next();
			key = rs.getInt("keys");
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			rs.close();
			stmt.close();
		}

		return key;

	}

}
