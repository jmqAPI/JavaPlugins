package com.skywing.utils.db;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * �����������������ͨ���벻ͬ�����ݿ⣬Ŀǰ�������ݿⲻ֧��seq �÷���ʵ���˵���ģʽ
 * 
 * @author Administrator
 * @version 1.0
 * @�������� 2006-3-6
 * @����ʱ�� 11:19:16 ע�����Ϊ��֤����������������ڸ÷����ڲ��ر����Ӷ��󣬸����Ӷ���Ĺر���Ҫ�� ҵ���߼���ȥʵ��
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
