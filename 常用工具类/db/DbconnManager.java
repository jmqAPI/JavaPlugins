package com.skywing.utils.db;

import org.apache.log4j.Logger;

import java.sql.*;
import javax.sql.*;
import javax.transaction.UserTransaction;

import com.skywing.utils.config.*;

/**
 * 
 * @author ��־�� 
 * @version 1.0
 * @�������� Mar 11, 2006
 * @����ʱ�� 9:45:27 AM
 */
public class DbconnManager {
	/**
	 * Logger for this class
	 */
  private static final Logger logger = Logger.getLogger(DbconnManager.class);
  private static final String DataSourceName=Global.getDataSource();
  private javax.sql.DataSource ds = null;
  
  protected DbconnManager() {
    try {
      //initDs();
    }
    catch (Exception e) {
      System.err.println("your data base config may be error ,please ceck it .");
      e.printStackTrace();
    }

  }

  
  private synchronized void initDs() throws javax.naming.NamingException {
    if (ds == null) {
      Object dsLookup = new javax.naming.InitialContext().lookup(DataSourceName);
      ds = (DataSource) dsLookup;
    }
  }

  public static DbconnManager getInstance() {
    if (instance == null) {
      synchronized (com.skywing.utils.db.DbconnManager.class) {
        if (instance == null) {
          instance = new com.skywing.utils.db.DbconnManager();
        }
      }
    }
    return instance;
  }
  

  public Connection getConnection() throws java.sql.SQLException {
   return getConn();
   /*
    try {
      if (ds == null) {
        initDs();
      }
      return ds.getConnection();
    }
    catch (SQLException sqle) {
      throw sqle;
    }
    catch (Exception otherE) {
      throw new java.sql.SQLException("error geting database connection:" +
                                      otherE);
    }
    */
  }
  
  

  /**
   * ��ȡ���������ݿ�����
   * @return Connection
   */
  public Connection getTransactionConnection() throws SQLException {
    Connection conn = getConnection();
    conn.setAutoCommit(false);
    return conn;
  }

  /**
   * �ر����������ݿ�����
   * @return Connection
   */
  public static void closeTransactionConnection(Connection conn,boolean abortTransaction) {
    //�ж�����Դ�Ƿ����
    if (conn != null) {
      try {
        DatabaseMetaData metaData = conn.getMetaData();
        if (metaData.supportsTransactions()) {
          //�ع�/�ύ����
          if (abortTransaction) {
            conn.commit();
          }
          else {
            conn.rollback();
          }
          conn.setAutoCommit(true);
        }
        closeConnection(conn);
      }
      catch (SQLException e) {
        System.out.println(e);
      }
    }
  }

  
  /**
   * �رշ����������ݿ�����
   * @return Connection
   */
  public static void closeConnection(Connection con) {
    try {
      if (con != null) {
        con.close();
        con = null;
      }
    }
    catch (SQLException e) {
      System.out.println(e);
    }
  }

  
  /**
   * �ر��������
   * @return Connection
   */
  public static void closePreparedStatement(PreparedStatement pstmt) {
    try {
      if (pstmt != null) {
        pstmt.close();
      }
    }
    catch (SQLException e) {
      System.out.println(e);
    }
  }
  
  


  
   /**
   * �ر��������
   * @return Connection
   */
  public static void closeStatement(Statement stmt) {
    try {
      if (stmt != null) {
        stmt.close();
      }
    }
    catch (SQLException e) {
      System.out.println(e);
    }
  }

  /**
   * �رղ�ѯ�����
   * @return Connection
   */
  public static void closeResultSet(ResultSet rst) {
    try {
      if (rst != null) {
        rst.close();
      }
    }
    catch (SQLException e) {
      System.out.println(e);
    }
  }

  /**
   * �ر����ж�����ͨ�����ݿ����ӣ�
   * @return Connection
   */
  public static void closeResource(Connection conn,PreparedStatement pstmt,ResultSet rst) {
    closeResultSet(rst);
    closePreparedStatement(pstmt);
    closeConnection(conn);
  }
  
  /**
   * �ر����ж�����ͨ�����ݿ����ӣ�
   * @return Connection
   */
  public static void closeResource(Connection conn,Statement stmt,ResultSet rst) {
    closeResultSet(rst);
    closeStatement(stmt);
    closeConnection(conn);
  }

  /**
   * �ر����ж�����ͨ�����ݿ����ӣ�
   * @return Connection
   */
  public static void closeResource(Connection conn,PreparedStatement pstmt) {
    closePreparedStatement(pstmt);
    closeConnection(conn);
  }

  /**
   * �ر����ж��󣨴���������ݿ����ӣ�
   * @return Connection
   */
  public static void closeResource(Connection conn,PreparedStatement pstmt,boolean bool) {
    closePreparedStatement(pstmt);
    closeTransactionConnection(conn,bool);
  }

  /**
   * �ر�JTA��������
   * @return UserTransaction
   */
  public static void closeUserTransaction(UserTransaction tx, boolean bool) {
    try {
      if (tx != null) {
        if (bool) {
          tx.rollback();
        }
        else {
          tx.commit();
        }
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * �رմ洢�������
   * @param callableStatement CallableStatement
   */
  public static void closeCallableStatement(CallableStatement callableStatement) {
    try {
      if (callableStatement != null) {
        callableStatement.close();
      }
    }
    catch (SQLException e) {
      System.out.println(e);
    }
  }

  final static String sDBDriver =Global.getJdbcDriver();
  final static String sConnStr = Global.getJdbcUrl();

   /**
   * ͨ��thin��ʽ���Oracle���ݿ������.
   */
  private Connection getConn() {
	  
    Connection conn = null;
    try {
      Class.forName(sDBDriver);
      conn = DriverManager.getConnection(sConnStr, Global.getJdbcUser(), Global.getJdbcPassword());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return conn;
  }
  
  private static DbconnManager instance = null;

  public static void main(String args[]) throws SQLException {
    Connection conn = DbconnManager.getInstance().getConnection();
  }
}
