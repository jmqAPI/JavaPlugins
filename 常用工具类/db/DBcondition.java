/*
 * 创建日期 2004-11-22
 *
 * 
 */
package com.skywing.utils.db;

import javax.servlet.http.*;
import java.util.Vector;
import com.skywing.utils.string.StringUtil;

/*
 * ///////////////////////// fieldname :数据库子段名称 reqname :对应的页面字段名称 type :子段类型
 * 模糊匹配字符类型 --- String 精确匹配字符类型 --- Char 数字型 --- Number 日期之后类型 --- Date>
 * 日期之后类型(包括当前日期) --- Date>= 日期之前类型 --- Date < 日期之前类型(包括当前日期) --- Date <=
 * /////////////////////////
 */

public class DBcondition {
        private Vector _v;

        public DBcondition() {
                _v = new Vector();
        }

        /**
         * 添加查询条件
         *
         * @param fieldname数据库字段名称
         * @param pageFieldname页面数据名称
         * @param fieldtype数据类型
         */
        
        public void addCondition(String fieldname, String pageFieldname,
                        String fieldtype) {
                String[] _s = new String[3];
                _s[0] = fieldname;
                _s[1] = pageFieldname;
                _s[2] = fieldtype;
                _v.add(_s);
        }

        /**
         * 获得页面查询字符串
         *
         * @param request
         * @param isWhere
         * @return
         */
        public String getsCondition(HttpServletRequest request, boolean isWhere) {
            String sCondition = "";
            String fieldname, reqname, type;
            String[] _s = new String[3];
            if (_v != null && _v.size() > 0) {
                    for (int i = 0; i < _v.size(); i++) {
                            _s = (String[]) _v.get(i);
                            fieldname = _s[0];
                            reqname = _s[1];
                            type = _s[2];

                            if (request.getParameter(reqname) != null
                                            && !request.getParameter(reqname).equals("")) {
                                    String sreq = request.getParameter(reqname).trim();

                                    if (type.equalsIgnoreCase("number>=")) {//数字>=
                                            sCondition += " and " + fieldname + " >= " + sreq + "";
                                    } else if (type.equalsIgnoreCase("number>")) {//数据>
                                            sCondition += " and " + fieldname + " > " + sreq + "";
                                    } else if (type.equalsIgnoreCase("number<=")) {//数据<=
                                            sCondition += " and " + fieldname + " <= " + sreq + "";
                                    } else if (type.equalsIgnoreCase("number<")) {//数据<
                                            sCondition += " and " + fieldname + " < " + sreq + "";
                                    } else if (type.equalsIgnoreCase("number")) {//数据=
                                            sCondition += " and " + fieldname + " = " + sreq + "";
                                    } else if (type.equalsIgnoreCase("string")) {//字符串查询模糊查询
                                            sCondition += " and " + fieldname + " like '%"
                                                            + StringUtil.toSql((sreq)) + "%'";
                                    } else if (type.equalsIgnoreCase("likeR")) {//字符串右模糊查询
                                            sCondition += " and " + fieldname + " like '"
                                                            + StringUtil.toSql((sreq)) + "%'";
                                    } else if (type.equalsIgnoreCase("likeL")) {//字符串左模糊查询
                                            sCondition += " and " + fieldname + " like '%"
                                                            + StringUtil.toSql((sreq)) + "'"; //修改说明：增加toSql语句对'进行处理
                                    } else if (type.equalsIgnoreCase("char")) {//字符＝
                                            sCondition += " and " + fieldname + " = '" + sreq + "'";
                                    } else if (type.equalsIgnoreCase("date>")) {//日期大于
                                            sCondition += " and " + fieldname + " > '" + sreq + "'";
                                    } else if (type.equalsIgnoreCase("date>=")) {//日期大于等于
                                            sCondition += " and " + fieldname + " >= '" + sreq
                                                            + "'";
                                    } else if (type.equalsIgnoreCase("date<")) {//日期小于
                                            sCondition += " and " + fieldname + " < '" + sreq + "'";
                                    } else if (type.equalsIgnoreCase("date<=")) {//日期<=
                                            sCondition += " and " + fieldname + " <= '" + sreq
                                                            + "'";
                                    } else if (type.equalsIgnoreCase("date=")) {//日期类型转换
                                            sCondition += " and CONVERT(VARCHAR(10)," + fieldname
                                                            + ",120) = '" + sreq + "'";
                                    } else if (type.equalsIgnoreCase("in")) {//in查询
                                            sCondition += " and " + fieldname + " in (" + sreq
                                                            + ")";
                                    }
                            }
                    }
            }
            if (!isWhere && sCondition.length() > 5) {
                    sCondition = " WHERE " + sCondition.substring(5);
            }

            return sCondition;

    }
        public static String insertSQL(String tabName, String field[], int fieldtype[], String values[])
       {
        StringBuffer sb = new StringBuffer();
        int usefulNum = 0;
        for(int i = 0; i < values.length; i++)
            if(values[i] != null)
                usefulNum++;

        String newField[] = new String[usefulNum];
        int newFieldtype[] = new int[usefulNum];
        String newValue[] = new String[usefulNum];
        int k = 0;
        for(int i = 0; i < values.length; i++)
            if(values[i] != null)
            {
                newField[k] = field[i];
                newFieldtype[k] = fieldtype[i];
                newValue[k] = values[i];
                k++;
            }

        sb.append("insert into " + tabName);
        sb.append("(");
        for(int j = 0; j < usefulNum - 1; j++)
            sb.append(newField[j]).append(",");

        sb.append(newField[usefulNum - 1]);
        sb.append(") values(");
        for(int p = 0; p < usefulNum; p++)
            switch(newFieldtype[p])
            {
            default:
                break;

            case 4: // 整形
                if(p == usefulNum - 1)
                    sb.append(Integer.parseInt(newValue[p])).append(")");
                else
                    sb.append(Integer.parseInt(newValue[p])).append(",");
                break;

            case 94: // LONG
                if(p == usefulNum - 1)
                    sb.append(Long.parseLong(newValue[p])).append(")");
                else
                    sb.append(Long.parseLong(newValue[p])).append(",");
                break;

            case 12: // VARCHAR
                if(p == usefulNum - 1)
                    sb.append("'").append(newValue[p]).append("')");
                else
                    sb.append("'").append(newValue[p]).append("',");
                break;

            case 91: // DATE
                if(p == usefulNum - 1)
                   // sb.append("to_date('").append(newValue[p]).append("','YYYY-MM-DD'))");
                    sb.append("'").append(newValue[p]).append("')");
                else
                    //sb.append("to_date('").append(newValue[p]).append("','YYYY-MM-DD'),");
                    sb.append("'").append(newValue[p]).append("',");
                break;

            case 8: // DOUBLE
                if(p == usefulNum - 1)
                    sb.append(Double.parseDouble(newValue[p])).append(")");
                else
                    sb.append(Double.parseDouble(newValue[p])).append(",");
                break;

            case 93: // TIMESTAMP
                if(p == usefulNum - 1)
                    sb.append("to_date('").append(newValue[p]).append("','YYYY-MM-DD hh24:mm:ss'))");
                else
                    sb.append("to_date('").append(newValue[p]).append("','YYYY-MM-DD hh24:mm:ss'),");
                break;
            }

        String sql = sb.toString();
        System.out.println(sql);
        return sql;
    }


}
