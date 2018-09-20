package com.skywing.utils.db;

import java.util.Map;
import java.util.HashMap;

import com.skywing.utils.string.StringUtil;
/**
 * 
 * @author ��־�� 
 * @version 1.0
 * @�������� Mar 12, 2006
 * @����ʱ�� 2:01:33 AM
 */
public class Select {
  public Select() {
  }

  protected static final String EMPTY_STRING = " ";
  protected static final String COMMA = ",";
  protected static final String QUESTION_MARK = "?";
  protected static final String OPEN_PAREN = "(";
  protected static final String CLOSE_PAREN = ")";
  protected static final String CONDITION_VALUES = "WHERE";
  protected static final String AND_VALUES = "AND";
  protected static final String SET_VALUES = "SET";
//  protected static final String ACTIONTYPE = "#SELECTTYPE#";
//  protected static final String FIELDVALUES = "#FIELDVALUES#";
//  protected static final String FIELDNAMES = "#FIELDNAMES#";
//  protected static final String FIELDCONDITION = "#FIELDCONDITION#";

  /**
   * ����sql���
   * @param sql String
   * @param type int
   * @return Map
   */
  public static Map analyseSQL(String sql) {
    Map map = new HashMap();
    StringBuffer buf = new StringBuffer(sql.toUpperCase().trim());
    //ȡ�����ݿ��������
    String sqlType = buf.substring(0, buf.indexOf(EMPTY_STRING)).trim();
    System.out.println("sqltype.............................."+sqlType);
    if (sqlType.toUpperCase().equals("INSERT")) {
      int start = buf.indexOf(OPEN_PAREN) + 1;
      int end = buf.indexOf(CLOSE_PAREN);
      
      //ȡ�����в��������ݿ��ֶ�
      String fieldName = buf.substring(start, end);
      System.out.println("fieldName........."+fieldName);
      buf.delete(0, buf.indexOf(CLOSE_PAREN) + 1);
      start = end + buf.indexOf(OPEN_PAREN) + 2;
      end += buf.indexOf(CLOSE_PAREN) + 1;
      //ȡ�����ݿ��ֶ�ֵ
      String fieldValue = buf.substring(buf.indexOf(OPEN_PAREN) + 1,
                                        buf.indexOf(CLOSE_PAREN));
      System.out.println("fieldValue........."+fieldValue);
      analyseInsertSQL(map, fieldName, fieldValue);
    }
    else if (sqlType.toUpperCase().equals("UPDATE") ||
             sqlType.toUpperCase().equals("DELETE")) {
      //ȡ������ֵ�����
      String tmpStr = buf.substring(buf.indexOf(SET_VALUES) + 4);
      String fieldCondition = "";
      String fieldNames = "";
      //�ж��Ƿ���where���
      if (tmpStr.indexOf(CONDITION_VALUES) != -1) {
        fieldNames = tmpStr.substring(0, tmpStr.indexOf(CONDITION_VALUES)).trim();
        fieldCondition = tmpStr.substring(tmpStr.indexOf(CONDITION_VALUES) + 5).
            trim();
      }
      analyseUpdateSQL(map, fieldNames, fieldCondition);
    }
    return map;
  }

  /**
   * ����insert�����ֶμ���ֵ
   * @param map Map
   * @param fieldName String
   * @param fieldValue String
   */
  private static final void analyseInsertSQL(Map map, String fieldName,
                                             String fieldValue) {
    String[] fieldNames = fieldName.split(COMMA);
    String[] fieldValues = fieldValue.split(COMMA);
    if (fieldNames.length == fieldValues.length && fieldNames.length > 0) {
      int tmp = 0;
      for (int i = 0; i < fieldNames.length; i++) {
        if (fieldValues[i].trim().equals(QUESTION_MARK)) {
          map.put(StringUtil.getObjectName(fieldNames[i]), String.valueOf(++tmp));
        }
      }
    }
    else {
      map.clear();
    }
  }

  /**
   * ����update����ֶμ���ֵ
   * @param map Map
   * @param fields String
   * @param condition String
   */
  private static final void analyseUpdateSQL(Map map, String fields,
                                             String condition) {
    String[] fieldNames = fields.split(COMMA);
    int k = 0;
    if (fieldNames.length > 0) {
      for (int i = 0; i < fieldNames.length; i++) {
        String tmpStr = fieldNames[i].trim();
        if (tmpStr.substring(tmpStr.indexOf("=") +
            1).trim().equals(QUESTION_MARK)) {
          map.put(StringUtil.getObjectName(tmpStr.substring(0,
              tmpStr.indexOf("=")).trim()), String.valueOf(++k));
        }
      }
      if (condition.indexOf(AND_VALUES) != -1) {
        String[] conditions = condition.split(AND_VALUES);
        for (int i = 0; i < conditions.length; i++) {
          String tmpStr = conditions[i].trim();
          if (tmpStr.substring(tmpStr.indexOf("=") +
              1).trim().equals(QUESTION_MARK)) {
            map.put(StringUtil.getObjectName(tmpStr.substring(0,
                tmpStr.indexOf("=")).trim()), String.valueOf(++k));
          }
        }
      }
      else {
        map.put(StringUtil.getObjectName(condition.substring(0,
            condition.indexOf("=")).trim()), String.valueOf(++k));
      }
    }
    else {
      map.clear();
    }
  }

  public static void main(String arg[]) {
    Select s = new Select();
//    String sql = "insert into t_system_dept(dept_sq,dept_name,dept_grade," +
//        "dept_level,dept_online) values (?,?,?,?,?)";
//    String sql = "update t_system_dept set dept_name =? ,dept_grade=? ," +
//        "dept_level = ?,dept_online=? where dept_sq= ?  ";
    String sql =
        "INSERT INTO PROMOTION_GIFT(GIFT_ID,UNIT_ID,BRAND_ID, ITEM_ID," +
        "GIFT_NAME,GIFT_PRICE,REMARKS,INUSE,GIFT_NO,STOCK,RANK,UPDATE_DATE," +
        "MAKECYCLE,PER)VALUES(1046, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    Map map = s.analyseSQL(sql);
    System.out.print(map);
  }
}
