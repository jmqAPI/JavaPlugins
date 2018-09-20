package com.skywing.utils.db;

import java.sql.*;
import java.util.*;
import com.skywing.utils.exception.*;


/**
 * 
 * @author 李志峰 
 * @version 1.0
 * @创建日期 Mar 12, 2006
 * @创建时间 1:36:06 PM
 */
public class DataBind {
	
  public DataBind() {
  }

  /**
   * 查询值对象
   * @param conn Connection       数据库连接，可以为null
   * @param sql String            查询语句
   * @param clazz Class           Bean对象或者VO对象
   * @param parameters Parameters 查询参数,可以为null
   * @return Object               可以用(VO)Object转换，调用VO的get方法取值
   * @throws SQLException
   * @throws DBException
   */
  public Object query(Connection conn, String sql, Class clazz,
                      Parameters parameters) throws SQLException {
    if (sql == null || sql.trim().equals("")) {
      throw new DBException("wrong sql syntax ");
    }
    boolean hasConn = false;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    LinkedList methodList = null;
    try {
      if (conn == null) {
        conn = DbconnManager.getInstance().getConnection();
      }
      else {
        hasConn = true;
      }
      pstmt = conn.prepareStatement(sql);
      DataTypeBind bind = new DataTypeBind();
      if (parameters != null) {
        bind.setJdbcParameters(pstmt, parameters);
        parameters = null;
      }
      rst = pstmt.executeQuery();
      if (rst.next()) {
        try {
          methodList = bind.getMethodInfor(rst, clazz);
          return bind.setValueObject(rst, clazz, methodList);
        }
        catch (Exception e) {
          throw new DBException("get valueObject error" + e);
        }
      }
    }
    finally {
      methodList = null;
      closeObject(conn, pstmt, rst, hasConn);
    }
    return null;
  }

  /**
   * 查询值对象，返回查询的所有值
   * @param conn Connection       数据库连接，可以为null
   * @param sql String            查询语句
   * @param clazz Class           Bean对象或者VO对象
   * @param parameters Parameters 查询参数（Parameters可以设置firstRow
   *                              和count确定取值范围），可以为空
   * @return List
   * @throws SQLException
   * @throws DBException
   */
  public List queryAll(Connection conn, String sql, Class clazz,
                       Parameters parameters) throws SQLException {
    if (sql == null || sql.equals("")) {
      throw new DBException("wrong sql syntax ");
    }
    boolean hasConn = false;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    List list = new ArrayList();
    try {
      //判断是否有数据库连接
      if (conn == null) {
        conn = DbconnManager.getInstance().getConnection(); //取得数据库连接
      }
      else {
        hasConn = true;
      }
      int[] rowPoint = getRowPoint(parameters); //取得用户设置取值范围
      if (rowPoint != null) { //创建可滚动结果集语句
        pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                                      ResultSet.CONCUR_READ_ONLY);
      }
      else {
        pstmt = conn.prepareStatement(sql);
      }
      DataTypeBind bind = new DataTypeBind();
      pstmt.setFetchSize(50);
      if (parameters != null) { //设置查询参数
        bind.setJdbcParameters(pstmt, parameters);
        parameters = null;
      }
      rst = pstmt.executeQuery();
      setResultPoint(rst, rowPoint);
      LinkedList methodList = null;
      int count = 0;
      while (rst.next()) {
        try {
          if (methodList == null) {
            methodList = bind.getMethodInfor(rst, clazz);
          }
          list.add(bind.setValueObject(rst, clazz, methodList));
          if (rowPoint != null) {
            ++count;
            if (count >= rowPoint[1]) {
              break;
            }
          }
        }
        catch (Exception e) {
          throw new DBException("get valueObject error : " + e);
        }
      }
      methodList = null;
    }
    finally {
      closeObject(conn, pstmt, rst, hasConn);
    }
    return list;
  }

  /**
   * 查询值对象，返回查询的所有值
   * @param conn Connection       数据库连接，可以为null
   * @param sql String            查询语句
   * @param clazz Class           Bean对象或者VO对象
   * @param parameters Parameters 查询参数（Parameters可以设置firstRow
   *                              和count确定取值范围），可以为空
   * @return List
   * @throws SQLException
   * @throws DBException
   */
  /*
  public Result queryAll(Connection conn, String sql, Class clazz, int currPage) throws
      SQLException {
    if (sql == null || sql.equals("")) {
      throw new DBException("wrong sql syntax ");
    }
    boolean hasConn = false;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    Result rs = new Result();
    try {
      //判断是否有数据库连接
      if (conn == null) {
        conn = DbconnManager.getInstance().getConnection(); //取得数据库连接
      }
      else {
        hasConn = true;
      }
      int[] rowPoint = new int[] {
          1, stateCommon.per_Page_Number}; //取得用户设置取值范围
      if (currPage > 1) {
        rowPoint[0] = (currPage - 1) * stateCommon.per_Page_Number + 1;
        rowPoint[1] = currPage * stateCommon.per_Page_Number;
      }
      //创建可滚动结果集语句
      pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
      DataTypeBind bind = new DataTypeBind();
      pstmt.setFetchSize(50);
      rst = pstmt.executeQuery();
      rst.last();
      rs.setTotalNumber(rst.getRow()); //取得记录数
      rs.setTotalPageNum(this.getTotalPageNum(rs.getTotalNumber())); //取得页数
      if (rowPoint[0] > 1) {
        rst.absolute(rowPoint[0] - 1);
      }else{
        rst.beforeFirst();
      }
      LinkedList methodList = null;
      int count = 0;
      List list = new ArrayList();
      while (rst.next()) {
        try {
          if (methodList == null) {
            methodList = bind.getMethodInfor(rst, clazz);
          }
          list.add(bind.setValueObject(rst, clazz, methodList));
          if (rowPoint != null) {
            ++count;
            if (count>=rowPoint[1]-rowPoint[0]+1) {
              break;
            }
          }
        }
        catch (Exception e) {
          throw new DBException("get valueObject error : " + e);
        }
      }
      rs.setData(list);
      methodList = null;
    }
    finally {
      closeObject(conn, pstmt, rst, hasConn);
    }
    return rs;
  }
*/
  /**
   * 查询值对象
   * @param conn Connection       数据库连接，可以为null
   * @param sql String            查询语句
   * @param clazz Class           Bean对象或者VO对象
   * @param parameters Parameters 查询参数（Parameters可以设置firstRow
   *                              和count确定取值范围，可以为null
   * @return Map
   * @throws SQLException
   * @throws DBException
   */
  public Map queryAll(Connection conn, String sql, Parameters parameters) throws
      SQLException {
    if (sql == null || sql.equals("")) {
      throw new DBException("wrong sql syntax ");
    }
    boolean hasConn = false;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    Map map = new HashMap();
    try {
      //判断是否有数据库连接
      if (conn == null) {
        conn = DbconnManager.getInstance().getConnection(); //取得数据库连接
      }
      else {
        hasConn = true;
      }
      int[] rowPoint = getRowPoint(parameters); //取得用户设置取值范围
      if (rowPoint != null) { //创建可滚动结果集语句
        pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                                      ResultSet.CONCUR_READ_ONLY);
      }
      else {
        pstmt = conn.prepareStatement(sql);
      }
      DataTypeBind bind = new DataTypeBind();
      pstmt.setFetchSize(50);
      if (parameters != null) { //设置查询参数
        bind.setJdbcParameters(pstmt, parameters);
        parameters = null;
      }
      rst = pstmt.executeQuery();
      setResultPoint(rst, rowPoint);
      int count = 0;
      while (rst.next()) {
        map.put(rst.getObject(1), rst.getObject(2));
        if (rowPoint != null) {
          ++count;
          if (count >= rowPoint[1]) {
            break;
          }
        }
      }
    }
    finally {
      closeObject(conn, pstmt, rst, hasConn);
    }
    return map;
  }

  /**
   * 查询记录的数目（语句必须为 select count(*) from table）
   * @param conn Connection       数据库连接，可以为null
   * @param sql String            查询语句
   * @param clazz Class           Bean对象或者VO对象，可以为null
   * @param parameters Parameters 查询参数，可以为null
   * @return int
   * @throws SQLException
   */
  public int count(Connection conn, String sql, Parameters parameters) throws
      SQLException {
    if (sql == null || sql.trim().equals("")) {
      throw new DBException("wrong sql syntax ");
    }
    boolean hasConn = false;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    try {
      if (conn == null) {
        conn = DbconnManager.getInstance().getConnection();
      }
      else {
        hasConn = true;
      }
      pstmt = conn.prepareStatement(sql);
      DataTypeBind bind = new DataTypeBind();
      if (parameters != null) {
        bind.setJdbcParameters(pstmt, parameters);
      }
      rst = pstmt.executeQuery();
      if (rst.next()) {
        return rst.getInt(1);
      }
    }
    finally {
      closeObject(conn, pstmt, rst, hasConn);
    }
    return 0;
  }

  /**
   * 返回序列值
   * @param conn Connection     数据库连接，可以为null
   * @param sequence String     数据库序列名称
   * @return String
   * @throws SQLException
   */
  public String sequence(Connection conn, String sequence) throws
      SQLException {
    boolean hasConn = false;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    try {
      if (conn == null) {
        conn = DbconnManager.getInstance().getConnection();
      }
      else {
        hasConn = true;
      }
      String sql = "select " + sequence + ".nextVal from dual";
      pstmt = conn.prepareStatement(sql);
      rst = pstmt.executeQuery();
      if (rst.next()) {
        return rst.getString(1);
      }
    }
    finally {
      closeObject(conn, pstmt, rst, hasConn);
    }
    return "0";
  }

  /**
   * 添加值对象
   * @param conn Connection       数据库连接，可以为null
   * @param sql String            SQL语句
   * @param Object clazz          VO对象，可以为null
   * @return boolean
   * @throws SQLException
   * @throws DBException
   */
  public boolean insert(Connection conn, String sql, Object clazz) throws
      SQLException {
    if (sql == null || sql.equals("")) {
      throw new DBException("wrong sql syntax ");
    }
    boolean hasConn = false;
    PreparedStatement pstmt = null;
    boolean bool = false;
    LinkedList fieldList = null;
    try {
      if (conn == null) {
        conn = DbconnManager.getInstance().getTransactionConnection();
      }
      else {
        hasConn = true;
      }
      fieldList = null;
      pstmt = conn.prepareStatement(sql);
      DataTypeBind bind = new DataTypeBind();
      if (clazz != null) {
        fieldList = bind.getFieldInfor(sql, clazz.getClass());
        bind.setJdbcParameters(pstmt, clazz, fieldList);
      }
      pstmt.executeUpdate();
      bool = true;
    }
    finally {
      fieldList = null;
      closeObject(conn, pstmt, null, hasConn, bool);
    }
    return bool;
  }

  /**
   * 添加值对象
   * @param conn Connection       数据库连接，可以为null
   * @param sql String            SQL语句
   * @param parameters Parameters 参数，可以为null
   * @return boolean
   * @throws SQLException
   * @throws DBException
   */
  public boolean insert(Connection conn, String sql, Parameters parameters) throws SQLException {
    if (sql == null || sql.equals("")) {
      throw new DBException("wrong sql syntax ");
    }
    boolean hasConn = false;
    PreparedStatement pstmt = null;
    boolean bool = false;
    try {
      if (conn == null) {
        conn = DbconnManager.getInstance().getTransactionConnection();
      }
      else {
        hasConn = true;
      }
      pstmt = conn.prepareStatement(sql);
      DataTypeBind bind = new DataTypeBind();
      if (parameters != null) {
        bind.setJdbcParameters(pstmt, parameters);
      }
      if (parameters != null && parameters.batchSize > 0) {
        try {
          pstmt.executeBatch();
        }
        catch (BatchUpdateException e) {
          throw new DBException("executeBatch method throw error " + e);
        }
      }
      else {
        pstmt.executeUpdate();
      }
      bool = true;
    }
    finally {
      closeObject(conn, pstmt, null, hasConn, bool);
    }
    return bool;
  }

  /**
   * 更新值对象
   * @param conn Connection        数据库连接，可以为null
   * @param sql String             SQL语句
   * @param clazz Object           VO对象，可以为null
   * @return boolean
   * @throws SQLException
   * @throws DBException
   */
  public boolean update(Connection conn, String sql, Object clazz) throws
      SQLException {
    return insert(conn, sql, clazz);
  }

  /**
   * 更新值对象
   * @param conn Connection        数据库连接，可以为null
   * @param sql String             SQL语句
   * @param parameters Parameters  参数，可以为null
   * @return boolean
   * @throws SQLException
   * @throws DBException
   */
  public boolean update(Connection conn, String sql, Parameters parameters) throws
      SQLException {
    return insert(conn, sql, parameters);
  }

  /**
   * 删除值对象
   * @param conn Connection
   * @param sql String
   * @param clazz Object
   * @return boolean
   * @throws SQLException
   * @throws DBException
   */
  public boolean delete(Connection conn, String sql, Object clazz) throws
      SQLException {
    return insert(conn, sql, clazz);
  }

  /**
   * 删除值对象
   * @param conn Connection
   * @param sql String
   * @param parameters Parameters
   * @return boolean
   * @throws SQLException
   * @throws DBException
   */
  public boolean delete(Connection conn, String sql, Parameters parameters) throws
      SQLException {
    return insert(conn, sql, parameters);
  }

  /**
   * 添加值对象
   * @param conn Connection        数据库连接，可以为null
   * @param sql String             SQL语句
   * @param list List              VO对象集合
   * @return boolean
   * @throws SQLException
   * @throws DBException
   */
  public boolean insert(Connection conn, String sql, List list) throws
      SQLException {
    if (sql == null || sql.equals("")) {
      throw new DBException("sql语句为空！");
    }
    if (list == null) {
      throw new DBException("list对象为空！");
    }
    boolean hasConn = false;
    PreparedStatement pstmt = null;
    boolean bool = false;
    try {
      if (conn == null) {
        conn = DbconnManager.getInstance().getTransactionConnection();
      }
      else {
        hasConn = true;
      }
      pstmt = conn.prepareStatement(sql);
      DataTypeBind bind = new DataTypeBind();
      if (list != null) {
        bind.setJdbcObject(pstmt, list, sql);
      }
      try {
        pstmt.executeBatch();
      }
      catch (BatchUpdateException e) {
        throw new DBException("executeBatch method throw error " + e);
      }
      bool = true;
    }
    finally {
      closeObject(conn, pstmt, null, hasConn, bool);
    }
    return bool;
  }

  /**
   * 更新值对象
   * @param conn Connection   数据库连接，可以为null
   * @param sql String        SQL语句
   * @param list List         VO对象集合
   * @return boolean
   * @throws SQLException
   * @throws DBException
   */
  public boolean update(Connection conn, String sql, List list) throws
      SQLException {
    return insert(conn, sql, list);
  }

  /**
   * 删除值对象
   * @param conn Connection   数据库连接，可以为null
   * @param sql String        SQL语句
   * @param list List         VO对象集合
   * @return boolean
   * @throws SQLException
   * @throws DBException
   */
  public boolean delete(Connection conn, String sql, List list) throws
      SQLException {
    return insert(conn, sql, list);
  }

  /**
   * 释放资源
   * @param conn Connection          数据库连接
   * @param pstmt PreparedStatement
   * @param rst ResultSet
   * @param hasConn boolean
   */
  
  private void closeObject(Connection conn, PreparedStatement pstmt,
                           ResultSet rst, boolean hasConn) {
    DbUtils.closeQuietly(rst);
    DbUtils.closeQuietly(pstmt);
    if (!hasConn) {
      DbUtils.closeQuietly(conn);
    }
  }

  /**
   * 释放相关资源
   * @param conn Connection
   * @param pstmt PreparedStatement
   * @param rst ResultSet
   * @param hasConn boolean
   * @param trans boolean
   */
  private void closeObject(Connection conn, PreparedStatement pstmt,
                           ResultSet rst, boolean hasConn, boolean trans) {
    DbUtils.closeQuietly(rst);
    DbUtils.closeQuietly(pstmt);
    if (!hasConn) {
      DbUtils.closeTransactionConnection(conn, trans);
    }
  }

  /**
   * 设置result的取值范围
   * @param rst ResultSet
   * @param rowPoint int[]
   * @throws SQLException
   */
  private void setResultPoint(ResultSet rst, int[] rowPoint) throws
      SQLException {
    if (rowPoint == null) {
      return;
    }
    rst.last();
    int rowCount = rst.getRow();
    rst.beforeFirst();
    if (rowPoint[0] <= rowCount) {
      if (rowPoint[0] != 1) {
        rst.absolute(rowPoint[0] - 1);
        if (rowPoint[1] == -1) {
          rowPoint[1] = rowCount - (rowPoint[0] - 1);
        }
      }
      else if (rowPoint[1] == -1) {
        rowPoint[1] = rowCount;
      }
    }
  }

  /**
   * 取得用户设定的取值范围
   * @param parameters Parameters
   * @return int[]
   */
  private int[] getRowPoint(Parameters parameters) {
    if (parameters == null) {
      return null;
    }
    //第一条记录
    int firstRow = parameters.getFirstRow();
    //取记录的最大数量
    int maxRows = parameters.getCount();
    if (firstRow == -1 && maxRows == -1) {
      return null;
    }
    else {
      if (firstRow != -1) {
        if (firstRow < 1 || maxRows == 0) {
          throw new DBException("firstRow or maxRow parameter setting error");
        }
      }
    }
    int[] result = new int[2];
    result[0] = parameters.getFirstRow();
    result[1] = parameters.getCount();
    return result;
  }

   
  /*
  private int getTotalPageNum(int totalnum) {
    if (totalnum == 0) {
      return 0;
    }
    if (totalnum % stateCommon.per_Page_Number == 0) {
      return totalnum / stateCommon.per_Page_Number;
    }
    else {
      return totalnum / stateCommon.per_Page_Number + 1;
    }
  }
  */
}
