package com.skywing.utils.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

/**
 * 
 * @author 李志峰 
 * @version 1.0
 * @创建日期 Mar 12, 2006
 * @创建时间 1:28:07 PM
 */
public final class DbUtils {

    /**
     * 关闭 <code>Connection</code>, 防止关闭NULL Connection.
     * @param conn Connection
     * @throws SQLException
     */

     public static void close(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Close a <code>ResultSet</code>, avoid closing if null.
     */
    public static void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    /**
     * Close a <code>Statement</code>, avoid closing if null.
     */
    public static void close(Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }

    /**
     * Close a <code>Connection</code>, avoid closing if null and hide
     * any SQLExceptions that occur.
     */
    public static void closeQuietly(Connection conn) {
        try {
            close(conn);
        } catch (SQLException sqle) {
            // quiet
        }
    }

    /**
     * Close a <code>Connection</code>, <code>Statement</code> and
     * <code>ResultSet</code>.  Avoid closing if null and hide any
     * SQLExceptions that occur.
     */
    public static void closeQuietly(
        Connection conn,
        Statement stmt,
        ResultSet rs) {

        closeQuietly(rs);
        closeQuietly(stmt);
        closeQuietly(conn);
    }

    /**
     * Close a <code>ResultSet</code>, avoid closing if null and hide
     * any SQLExceptions that occur.
     */
    public static void closeQuietly(ResultSet rs) {
        try {
            close(rs);
        } catch (SQLException sqle) {
            // quiet
        }
    }

    /**
     * Close a <code>Statement</code>, avoid closing if null and hide
     * any SQLExceptions that occur.
     */
    public static void closeQuietly(Statement stmt) {
        try {
            close(stmt);
        } catch (SQLException sqle) {
            // quiet
        }
    }

    /**
     * Commits a <code>Connection</code> then closes it, avoid closing if null.
     */
    public static void commitAndClose(Connection conn) throws SQLException {
        if (conn != null) {
            conn.commit();
            conn.close();
        }
    }

    /**
     * Commits a <code>Connection</code> then closes it, avoid closing if null
     * and hide any SQLExceptions that occur.
     */
    public static void commitAndCloseQuietly(Connection conn) {
        try {
            commitAndClose(conn);
        } catch (SQLException sqle) {
            // quiet
        }
    }

    /**
     * Loads and registers a database driver class.
     * If this succeeds, it returns true, else it returns false.
     */
    public static boolean loadDriver(String driverClassName) {
        try {
            Class.forName(driverClassName).newInstance();
            return true;

        } catch (ClassNotFoundException e) {
            // TODO Logging?
            //e.printStackTrace();
            return false;

        } catch (IllegalAccessException e) {
            // TODO Logging?
            //e.printStackTrace();

            // Constructor is private, OK for DriverManager contract
            return true;

        } catch (InstantiationException e) {
            // TODO Logging?
            //e.printStackTrace();
            return false;

        } catch (Throwable t) {
            return false;
        }
    }

    public static void printStackTrace(SQLException sqle) {
        printStackTrace(sqle, new PrintWriter(System.err));
    }

    public static void printStackTrace(SQLException sqle, PrintWriter pw) {

        SQLException next = sqle;
        while (next != null) {
            next.printStackTrace(pw);
            next = next.getNextException();
            if (next != null) {
                pw.println("Next SQLException:");
            }
        }
    }

    public static void printWarnings(Connection connection) {
        printWarnings(connection, new PrintWriter(System.err));
    }

    public static void printWarnings(Connection conn, PrintWriter pw) {
        if (conn != null) {
            try {
                printStackTrace(conn.getWarnings(), pw);
            } catch (SQLException sqle) {
                printStackTrace(sqle, pw);
            }
        }
    }

    /**
     * Rollback any changes made on the given connection.
     * @param conn The database Connection to rollback.  A null value is legal.
     * @throws SQLException
     */
    public static void rollback(Connection conn) throws SQLException {
        if (conn != null) {
            conn.rollback();
        }
    }

    /**
   * 关闭事务型数据库连接
   * @return Connection
   */
  public static void closeTransactionConnection(Connection conn,
                                                boolean abortTransaction) {
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
        }
        closeQuietly(conn);
      }
      catch (SQLException e) {
        System.out.println(e);
      }
    }
  }


}
