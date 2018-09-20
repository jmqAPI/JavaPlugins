package com.skywing.utils.db;

import java.sql.*;
import java.util.List;
import java.lang.reflect.*;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

import com.skywing.utils.string.StringUtil;
import com.skywing.utils.exception.*;

/**
 * 
 * @author 李志峰 
 * @version 1.0
 * @创建日期 Mar 12, 2006
 * @创建时间 1:47:00 AM
 */
public class DataTypeBind {

  public DataTypeBind() {
  }

  //数据类型
  private static final Map ClASSTYPE = new HashMap();

  static {
    ClASSTYPE.put(SQLDataTypes.LONGVARCHAR, String.class);
    ClASSTYPE.put(SQLDataTypes.CHAR, String.class);
    ClASSTYPE.put(SQLDataTypes.VARCHAR, String.class);
    ClASSTYPE.put(SQLDataTypes.VARBINARY, String.class);
    ClASSTYPE.put(SQLDataTypes.TINYINT, Integer.class);
    ClASSTYPE.put(SQLDataTypes.INTEGER, Integer.class);
    ClASSTYPE.put(SQLDataTypes.INT, Integer.class);
    ClASSTYPE.put(SQLDataTypes.SMALLINT, Integer.class);
    ClASSTYPE.put(SQLDataTypes.BIGINT, Long.class);
    ClASSTYPE.put(SQLDataTypes.BIGINTEGER, Long.class);
    ClASSTYPE.put(SQLDataTypes.NUMERIC, Double.class);
    ClASSTYPE.put(SQLDataTypes.NUMBER, Double.class);
    ClASSTYPE.put(SQLDataTypes.DECIMAL, Double.class);
    ClASSTYPE.put(SQLDataTypes.REAL, Double.class);
    ClASSTYPE.put(SQLDataTypes.DOUBLE, Double.class);
    ClASSTYPE.put(SQLDataTypes.FLOAT, Float.class);
    ClASSTYPE.put(SQLDataTypes.BOOLEAN, Boolean.class);
    ClASSTYPE.put(SQLDataTypes.BOOL, Boolean.class);
    ClASSTYPE.put(SQLDataTypes.DATE, java.sql.Date.class);
    ClASSTYPE.put(SQLDataTypes.TIME, java.sql.Time.class);
    ClASSTYPE.put(SQLDataTypes.TIMESTAMP, java.sql.Timestamp.class);
  }

  /**
   * 数据类型判断
   * @param type String
   * @return int
   */
  private int getSqlType(String type) {
    if (type.equalsIgnoreCase(SQLDataTypes.VARCHAR)) {
      return 12;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.LONGVARCHAR)) {
      return -1;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.INTEGER) ||
        type.equalsIgnoreCase(SQLDataTypes.INT)) {
      return 4;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.BIGINT) ||
        type.equalsIgnoreCase(SQLDataTypes.BIGINTEGER)) {
      return -5;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.CHAR)) {
      return 1;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.DATE)) {
      return 91;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.NUMERIC) ||
        type.equalsIgnoreCase(SQLDataTypes.NUMBER)) {
      return 2;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.DOUBLE)) {
      return 8;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.FLOAT)) {
      return 6;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.DECIMAL)) {
      return 3;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.BIT)) {
      return -7;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.BOOLEAN) ||
        type.equalsIgnoreCase(SQLDataTypes.BOOL)) {
      return 16;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.TIME)) {
      return 92;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.TIMESTAMP)) {
      return 93;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.ARRAY)) {
      return 2003;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.BINARY) ||
        type.equalsIgnoreCase(SQLDataTypes.BIT)) {
      return -2;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.BLOB)) {
      return 2004;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.CLOB)) {
      return 2005;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.DATALINK)) {
      return 70;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.DISTINCT)) {
      return 2001;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.JAVA_OBJECT)) {
      return 2000;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.LONGVARBINARY)) {
      return -4;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.NULL)) {
      return 0;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.REAL)) {
      return 7;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.REF)) {
      return 2006;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.SMALLINT)) {
      return 5;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.STRUCT)) {
      return 2002;
    }
    if (type.equalsIgnoreCase(SQLDataTypes.TINYINT)) {
      return -6;
    }
    return!type.equalsIgnoreCase(SQLDataTypes.VARBINARY) ? 1111 : -3;
  }

  /**
   * 设置SQL语句参数
   * @param ps PreparedStatement
   * @param index int
   * @param type String
   * @param str String
   * @throws SQLException
   */
  private void setPsmtParam(PreparedStatement ps, int index, String type,
                                   String str) throws SQLException {
    int intType = getSqlType(type);
    switch (intType) {
      case -1:
      case 1: //
      case 12: //
      case 1111:
        ps.setString(index, str);
        break;
      case -6:
      case 4: //
      case 5: //
        ps.setInt(index, StringUtil.getInt(str));
        break;
      case -5:
        ps.setLong(index, StringUtil.getLong(str));
        break;
      case 2: //
      case 3: //
      case 7: //
      case 8: //
        ps.setDouble(index, StringUtil.getDouble(str));
        break;
      case 6: //
        ps.setFloat(index, StringUtil.getFloat(str));
        break;
      case 16: //
        ps.setBoolean(index, StringUtil.getBoolean(str));
        break;
      case 91: //
        ps.setDate(index, StringUtil.getDate(str));
        break;
      case 92: //
        ps.setTime(index, StringUtil.getTime(str));
        break;
      case 93: //
        ps.setTimestamp(index, StringUtil.getTimeStamp(str));
        break;
      case 2004: //Blob

//        ps.setClob(index,);
        break;
      case 2005: //CLob
        break;
      default:
        ps.setString(index, str);
        break;
    }
  }

  /**
   * 设置存储过程参数
   * @param ps PreparedStatement
   * @param index int
   * @param type String
   * @param str String
   * @throws SQLException
   */
  private void setCallPsmtParam(CallableStatement callPstmt, int index,
                                       String type, String str) throws
      SQLException {
    int intType = getSqlType(type);
    switch (intType) {
      case -1:
      case 1: //
      case 12: //
      case 1111:
        callPstmt.setString(index, str);
        break;
      case -6:
      case 4: //
      case 5: //
        callPstmt.setInt(index, StringUtil.getInt(str));
        break;
      case -5:
        callPstmt.setLong(index, StringUtil.getLong(str));
        break;
      case 2: //
      case 3: //
      case 7: //
      case 8: //
        callPstmt.setDouble(index, StringUtil.getDouble(str));
        break;
      case 6: //
        callPstmt.setFloat(index, StringUtil.getFloat(str));
        break;
      case 16: //
        callPstmt.setBoolean(index, StringUtil.getBoolean(str));
        break;
      case 91: //
        callPstmt.setDate(index, StringUtil.getDate(str));
        break;
      case 92: //
        callPstmt.setTime(index, StringUtil.getTime(str));
        break;
      case 93: //
        callPstmt.setTimestamp(index, StringUtil.getTimeStamp(str));
        break;
      case 2004: //Blob

//        ps.setClob(index,);
        break;
      case 2005: //CLob
        break;
      default:
        callPstmt.setString(index, str);
        break;
    }
  }

  /**
   *
   * @param pstmt PreparedStatement
   * @param parameters Parameters
   * @throws SQLException
   */
  protected void setJdbcParameters(PreparedStatement pstmt,
                                          Parameters parameters) throws
      SQLException {
    if ( (pstmt != null)) {
      //处理一组参数
      if (parameters.batchSize == 0) {
        List values = parameters.getValues();
        List types = parameters.getTypes();
        for (int i = 0; i < values.size(); i++) {
          String value = values.get(i) == null ? null : (String) values.get(i);
          setPsmtParam(pstmt, i + 1, (String) types.get(i), value);
        }
      }
      else { //处理多组参数
        List valueList = parameters.getValueParameterList();
        List typeList = parameters.getTypeParameterList();
        for (int i = 0; i < parameters.batchSize; i++) {
          List values = (ArrayList) valueList.get(i);
          List types = (ArrayList) typeList.get(i);
          for (int j = 0; j < values.size(); j++) {
            String value = values.get(j) == null ? null : (String) values.get(j);
            setPsmtParam(pstmt, j + 1, (String) types.get(j), value);
          }
          pstmt.addBatch();
        }
      }
    }
  }

  protected void setProcedureParameters(CallableStatement callPstmt,
                                               Parameters parameters) throws
      SQLException {
    if ( (callPstmt != null)) {
      //处理一组方法
      if (parameters.batchSize == 0) {
        List values = parameters.getValues();
        List types = parameters.getTypes();
        for (int i = 0; i < values.size(); i++) {
          String value = values.get(i) == null ? null : (String) values.get(i);
          setCallPsmtParam(callPstmt, i + 1, (String) types.get(i), value);
        }
      }
      else { //处理多组参数
        List valueList = parameters.getValueParameterList();
        List typeList = parameters.getTypeParameterList();
        for (int i = 0; i < parameters.batchSize; i++) {
          List values = (ArrayList) valueList.get(i);
          List types = (ArrayList) typeList.get(i);
          for (int j = 0; j < values.size(); j++) {
            String value = values.get(j) == null ? null : (String) values.get(j);
            setCallPsmtParam(callPstmt, j + 1, (String) types.get(j), value);
          }
          callPstmt.addBatch();
        }
      }
    }
  }

  protected void setJdbcParameters(PreparedStatement pstmt,
                                          Object object, LinkedList fieldList) throws
      SQLException {
    try {
      for (int i = 0; i < fieldList.size(); i++) {
        List inforList = (ArrayList) fieldList.get(i);
        Method method = (Method) inforList.get(2);
        Object obj = method.invoke(object, null);
        String value = null;
        if (obj != null) {
          value = String.valueOf(obj);
        }
        String returnType = (String) inforList.get(3);
        int order = StringUtil.getInt( (String) inforList.get(1));
        System.out.println(order + " " + returnType + "   " + value);
        setPsmtParam(pstmt, order, returnType, value);
      }
    }
    catch (Exception e) {
      throw new DBException("set parameter with class error : " + e);
    }
  }

  protected LinkedList getFieldInfor(String sql, Class clazz) {
    LinkedList list = new LinkedList();
    try {
      Map fieldMap = Select.analyseSQL(sql);
      if (fieldMap.size() == 0) {
        return list;
      }
      Method mm[] = clazz.getDeclaredMethods();
      for (int i = 0; i < mm.length; i++) {
        String methodName = mm[i].getName();
        if (methodName.length() > 3 && methodName.substring(0, 3).equals("get")) {
          String methodField = methodName.substring(3).toUpperCase();
          if (fieldMap.containsKey(methodField)) {
            List methodList = new ArrayList();
            //参数名称
            methodList.add(methodField);
            //参数的位置
            methodList.add(fieldMap.get(methodField));
            //get方法
            methodList.add(mm[i]);
            System.out.println("mm[i]"+mm[i]);
            //方法返回类型
            methodList.add(StringUtil.getObjectName(mm[i].getReturnType().
                getName()));
            list.add(methodList);
          }
        }
      }
    }
    catch (Exception e) {
      throw new DBException("set parameter with class error : " + e);
    }
    return list;
  }

  protected void setJdbcObject(PreparedStatement pstmt, List list,
                                      String sql) throws SQLException {
    LinkedList fieldList = null;
    for (int i = 0; i < list.size(); i++) {
      if (fieldList == null) {
        fieldList = getFieldInfor(sql, list.get(i).getClass());
      }
      setJdbcParameters(pstmt, list.get(i), fieldList);
      pstmt.addBatch();
    }
    fieldList = null;
  }

  protected LinkedList getMethodInfor(ResultSet rst, Class clazz) throws
      SQLException, IllegalAccessException, InstantiationException,
      SecurityException, NoSuchMethodException, IllegalArgumentException,
      InvocationTargetException, IOException {
    LinkedList list = new LinkedList();
    Map fieldMap = getResultField(rst);
    Method mm[] = clazz.getDeclaredMethods();
    for (int i = 0; i < mm.length; i++) {
      String methodName = mm[i].getName();
      if (methodName.length() > 3 &&
          methodName.substring(0, 3).equals("set")) {
        String methodField = methodName.substring(3).toUpperCase();
        if (fieldMap.containsKey(methodField)) {
          List resultList = new ArrayList();
          //数据库字段名称
          resultList.add(methodField);
          //字段类型
          resultList.add(fieldMap.get(methodField));
          //set方法
          resultList.add(mm[i]);
          //根据参数类型名取类型class
          String paramTypeName = StringUtil.getObjectName(mm[i].
              getParameterTypes()[0].getName());
          Object obj = ClASSTYPE.get(paramTypeName);
          if (obj == null) {
            obj = String.class;
          }
          //方法参数
          resultList.add(obj);
          list.add(resultList);
        }
      }
    }
    return list;
  }

  protected Object setValueObject(ResultSet rst, Class clazz,
                                         LinkedList methodList) throws
      SQLException, IllegalAccessException, InstantiationException,
      SecurityException, NoSuchMethodException, IllegalArgumentException,
      InvocationTargetException, IOException {
    Object obj = clazz.newInstance();
    for (int i = 0; i < methodList.size(); i++) {
      List list = (ArrayList) methodList.get(i);
      String fieldName = (String) list.get(0);
      String fieldType = (String) list.get(1);
      String fieldValue = getFieldValue(rst, fieldName, fieldType);
      if (fieldValue != null) {
        Method method = (Method) list.get(2);
        Class sclass = (Class) list.get(3);
        Object[] objects = {
            converType(fieldValue, sclass)};
        method.invoke(obj, objects);
//        System.out.println("set 【" + fieldName + " = " + fieldValue +
//                           "】 success ");
      }
    }
    return obj;
  }

  private final String getFieldValue(ResultSet rst, String fieldName,
                                            String fieldType) throws
      IOException, SQLException {
    if (fieldType.equals("BLOB")) {
      return blobToString(rst.getBlob(fieldName));
    }
    else if (fieldType.equals("CLOB")) {
      return clobToString(rst.getClob(fieldName));
    }
    else {
      return StringUtil.fieldValue2String(rst.getObject(fieldName));
    }
  }

  private final Object converType(String val, Class sclass) throws
      SecurityException, NoSuchMethodException, InvocationTargetException,
      IllegalArgumentException, IllegalAccessException,
      InstantiationException {
    String className = StringUtil.getObjectName(sclass.getName());
    Object obj = "";
    if (className.equals("DATE")) {
      obj = java.sql.Date.valueOf(returnDateType(val, 1));
    }
    else if (className.equals("TIME")) {
      obj = java.sql.Time.valueOf(returnDateType(val, 2));
    }
    else if (className.equals("TIMESTAMP")) {
      obj = java.sql.Timestamp.valueOf(val);
    }
    else {
      Class[] clazzs = {
          String.class};
      Object object = val;
      Object[] objects = {
          object};
      Constructor co = sclass.getConstructor(clazzs);
      obj = co.newInstance(objects);
    }
    return obj;
  }

  private final String returnDateType(String arg, int type) {
    String result = arg;
    if (type == 1) {
      if (arg.indexOf(" ") != -1) {
        result = arg.substring(0, arg.indexOf(" "));
      }
    }
    else if (type == 2) {
      if (arg.indexOf(" ") != -1) {
        result = arg.substring(arg.indexOf(" "));
      }
    }
    return result;
  }

  protected Map getResultField(ResultSet rst) throws SQLException {
    Map map = new HashMap();
    ResultSetMetaData rm = rst.getMetaData();
    for (int i = 1; i <= rm.getColumnCount(); i++) {
      map.put(rm.getColumnName(i).toUpperCase(), rm.getColumnTypeName(i));
    }
    return map;
  }
 /*
  public void setOracleBlob(oracle.sql.BLOB blob,
                                   String value) throws SQLException {
    try {
      byte[] by = value.getBytes();
      java.io.InputStream fin = new java.io.ByteArrayInputStream(by);
      OutputStream out = blob.getBinaryOutputStream();
      int count = -1;
      int total = 0;
      byte[] data = new byte[blob.getBufferSize()];
      //把数据流放入BLOB对象
      while ( (count = fin.read(data, 0, blob.getBufferSize())) != -1) {
        total += count;
        out.write(data, 0, count);
      }
      fin.close();
      out.close();
    }
    catch (IOException e) {
      throw new DBException("set Blob data error " + e);
    }
  }
*/
  public String blobToString(java.sql.Blob blob) throws SQLException,
      IOException {
    String result = "";
    java.io.InputStream input = blob.getBinaryStream();
    byte[] buffer = new byte[ (int) blob.length()];
    input.read(buffer);
    result = new String(new String(buffer).toString().getBytes(),
                        "GBK").toString();
    return result;
  }

  public String clobToString(java.sql.Clob clob) throws SQLException,
      IOException {
    String result = "";
    java.io.InputStream input = clob.getAsciiStream();
    byte[] buffer = new byte[ (int) clob.length()];
    input.read(buffer);
    result = new String(new String(buffer).toString().getBytes(),
                        "GBK").toString();
    return result;
  }

}
