package com.skywing.utils.db;

import com.skywing.utils.db.SQLDataTypes;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * <p>Title: 查询参数设置</p>
 * <p>Description: 设置SQL参数类型</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京联信永益科技有限公司</p>
 * @author 北京联信永益科技有限公司
 * @version 1.0
 */

public class Parameters {

  private List values = new ArrayList();
  private List types = new ArrayList();
  private List keyValues = new ArrayList();
  private List keyNames = new ArrayList();
  private List keyTypes = new ArrayList();
  private List valueParameterList = new ArrayList();
  private List typeParameterList = new ArrayList();
  private List blobValues = new ArrayList();
  private List blobIndexValues = new ArrayList();
  private List bolbTypes = new ArrayList();
  private List objectTypes = new ArrayList();
  int batchSize = 0;
  private static final Object UNSET_PARAMETER = new Object() {};
  private static final Object UNSET_TYPE = new Object() {};
  private int firstRow = -1;
  private int count = -1;
  private String table;
  public Parameters() {

  }

  public void addBatch() {//批处理添加
    valueParameterList.add(new ArrayList(values));
    typeParameterList.add(new ArrayList(types));
    values.clear();
    types.clear();
    ++batchSize;
  }

  /**
   * 设置参数
   * @param index int
   * @param x boolean
   */
  public void setBoolean(int index, boolean x) {
    setParam(index, SQLDataTypes.BOOLEAN, Boolean.toString(x));
  }

  public void setChar(int index, char c) {
    setParam(index, SQLDataTypes.CHAR, c + "");
  }

  public void setDate(int order, java.sql.Date date) {
    setParam(order, SQLDataTypes.DATE, date.toString());
  }

  public void setDouble(int order, double x) {
    setParam(order, SQLDataTypes.DOUBLE, Double.toString(x));
  }

  public void setFloat(int order, float x) {
    setParam(order, SQLDataTypes.FLOAT, Float.toString(x));
  }

  public void setInt(int index, int x) {
    setParam(index, SQLDataTypes.INT, Integer.toString(x));
  }

  public void setLong(int index, long x) {
    setParam(index, SQLDataTypes.INT, Long.toString(x));
  }

  public void setShort(int index, short x) {
    setParam(index, SQLDataTypes.INT, Short.toString(x));
  }

  public void setString(int index, String str) {
    setParam(index, SQLDataTypes.VARCHAR, str);
  }

  public void setTime(int index, java.sql.Time time) {
    setParam(index, SQLDataTypes.TIME, time.toString());
  }

  public void setTimestamp(int index, java.sql.Timestamp timestamp) {
    setParam(index, SQLDataTypes.TIMESTAMP, timestamp.toString());
  }
  /**
   *
   * @param index int
   * @param type String
   * @param value String
   */
  void setParam(int index, String type, String value) {
    if (index < 1) {
      throw new IllegalArgumentException("error number ");
    }
    int size = values.size();
    if (index < size) {
      values.set(index, value);
      types.set(index, type);
    }
    else {
      for (int i = 0; i < index - size - 1; i++) {
        values.add(UNSET_PARAMETER);
        types.add(UNSET_TYPE);
      }
      values.add(value);
      types.add(type);
    }
  }

  public void setFirstRow(int firstRow) {
    this.firstRow = firstRow;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public List getValues() {
    return values;
  }

  public List getTypes() {
    return types;
  }

  public int getFirstRow() {
    return firstRow;
  }

  public List getTypeParameterList() {
    return typeParameterList;
  }

  public List getValueParameterList() {
    return valueParameterList;
  }

  public int getCount() {
    return count;
  }

  public String getTable() {
    return table;
  }

  public List getBlobIndexValues() {
    return blobIndexValues;
  }

  public List getBlobValues() {
    return blobValues;
  }

  public List getBolbTypes() {
    return bolbTypes;
  }

  public List getKeyNames() {
    return keyNames;
  }

  public List getKeyTypes() {
    return keyTypes;
  }

  public List getKeyValues() {
    return keyValues;
  }

  public List getObjectTypes() {
    return objectTypes;
  }
}
