package com.skywing.utils.db;

import org.apache.log4j.Logger;

import java.sql.*;
import javax.sql.*;
import javax.transaction.UserTransaction;

import com.skywing.utils.config.*;

/**
 * 
 * @author 李志峰 
 * @version 1.0
 * @创建日期 Mar 11, 2006
 * @创建时间 9:45:27 AM
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
   * 获取事务型数据库连接
   * @return Connection
   */
  public Connection getTransactionConnection() throws SQLException {
    Connection conn = getConnection();
    conn.setAutoCommit(false);
    return conn;
  }

  /**
   * 关闭事务型数据库连接
   * @return Connection
   */
  public static void closeTransactionConnection(Connection conn,boolean abortTransaction) {
    //判断数据源是否存在
    if (conn != null) {
      try {
        DatabaseMetaData metaData = conn.getMetaData();
        if (metaData.supportsTransactions()) {
          //回滚/提交数据
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
   * 关闭非事务性数据库连接
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
   * 关闭连接语句
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
   * 关闭连接语句
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
   * 关闭查询结果集
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
   * 关闭所有对象（普通的数据库连接）
   * @return Connection
   */
  public static void closeResource(Connection conn,PreparedStatement pstmt,ResultSet rst) {
    closeResultSet(rst);
    closePreparedStatement(pstmt);
    closeConnection(conn);
  }
  
  /**
   * 关闭所有对象（普通的数据库连接）
   * @return Connection
   */
  public static void closeResource(Connection conn,Statement stmt,ResultSet rst) {
    closeResultSet(rst);
    closeStatement(stmt);
    closeConnection(conn);
  }

  /**
   * 关闭所有对象（普通的数据库连接）
   * @return Connection
   */
  public static void closeResource(Connection conn,PreparedStatement pstmt) {
    closePreparedStatement(pstmt);
    closeConnection(conn);
  }

  /**
   * 关闭所有对象（带事务的数据库连接）
   * @return Connection
   */
  public static void closeResource(Connection conn,PreparedStatement pstmt,boolean bool) {
    closePreparedStatement(pstmt);
    closeTransactionConnection(conn,bool);
  }

  /**
   * 关闭JTA控制事务
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
   * 关闭存储过程语句
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
   * 通过thin方式获得Oracle数据库的连接.
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
