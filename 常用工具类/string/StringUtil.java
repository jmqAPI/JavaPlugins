package com.skywing.utils.string;

import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.math.BigDecimal;

public final class StringUtil {
	public static final String EMPTY_STRING = "";

	private StringUtil() {
	}
	
	
	
	 /**
     * 将普通字符串格式化成数据库认可的字符串格式
     *
     * @param str 要格式化的字符串
     * @return 合法的数据库字符串
     */
    public static String toSql(String str) {
      String sql = new String(str);
      return Replace(sql, "'", "''");
    }
    
    /**
     * 字符串替换，将 source 中的 oldString 全部换成 newString
     *
     * @param source 源字符串
     * @param oldString 老的字符串
     * @param newString 新的字符串
     * @return 替换后的字符串
     * 用于输入的表单字符串转化成HTML格式的文本
     */
    public static String Replace(String source, String oldString,
                                 String newString) {
      StringBuffer output = new StringBuffer();

      int lengthOfSource = source.length(); // 源字符串长度
      int lengthOfOld = oldString.length(); // 老字符串长度

      int posStart = 0; // 开始搜索位置
      int pos; // 搜索到老字符串的位置

      while ( (pos = source.indexOf(oldString, posStart)) >= 0) {
        output.append(source.substring(posStart, pos));

        output.append(newString);
        posStart = pos + lengthOfOld;
      }

      if (posStart < lengthOfSource) {
        output.append(source.substring(posStart));
      }

      return output.toString();
    }

	  
	
	/**
	 * 得到当前日期(文本格式)
	 * @return
	 */
	  public static String getToday()
      {
              return (new java.sql.Date(System.currentTimeMillis())).toString();
      }

	public static String getGbcode(String str)
	{
		if (str == null)
			return "";

		try {
			byte[] bytesStr = str.getBytes("ISO-8859-1");
			return new String(bytesStr, "GBK");
		} catch (Exception ex) {
			return str;
		}
	}
	
	
	
	/**
	 *  对页面中的标题或需要定长显示的字符串进行格式化，使其适合于在给定的长度内显示，
	 *  长度超过时，显示为"ssssss..."
	 * */
	public static String htmlTitleFilter(String srcTitle,int outputLength){
		String result = srcTitle;
		try{
			while (result.getBytes().length > outputLength){
				result = result.substring(0,result.length()-1);
			}
			if (srcTitle.length() > result.length())
				result = result + "...";
		}catch(Exception e){
		}
		return result;
	}

	public static boolean getBoolean(String property) {
		return Boolean.valueOf(property).booleanValue();
	}

	public static boolean getBoolean(String property, boolean defaultValue) {
		return (property == null) ? defaultValue : Boolean.valueOf(property)
				.booleanValue();
	}

	public static int getInt(String property, int defaultValue) {
		return (property == null) ? defaultValue : Integer.parseInt(property);
	}

	public static int getInt(String property) {
		return Integer.parseInt(property);
	}

	public static String getString(String property, String defaultValue) {
		return (property == null) ? defaultValue : property;
	}

	public static Integer getInteger(String property) {
		return (property == null) ? null : Integer.valueOf(property);
	}

	public static Integer getInteger(String property, Integer defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getInteger(property);
	}

	public static long getLong(String property) {
		return Long.parseLong(property);
	}

	public static long getLong(String property, long defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getLong(property);
	}

	public static double getDouble(String property) {
		return Double.parseDouble(property);
	}

	public static double getDouble(String property, double defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getDouble(property);
	}

	public static float getFloat(String property) {
		return Float.parseFloat(property);
	}

	public static float getFloat(String property, float defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getFloat(property);
	}

	public static java.sql.Date getDate(String str) {
		return str == null ? null : java.sql.Date.valueOf(str);
	}

	public static java.sql.Time getTime(String str) {
		return str == null ? null : java.sql.Time.valueOf(str);
	}

	public static java.sql.Timestamp getTimeStamp(String str) {
		return str == null ? null : java.sql.Timestamp.valueOf(str);
	}

	/**
	 * 获得类对象名字
	 * @param className String
	 * @return String
	 */
	public static String getObjectName(String className) {
		String result = new String(className);
		if (className.indexOf(" ") != -1) {
			result = className.substring(className.lastIndexOf(" ") + 1);
		}
		if (className.indexOf(".") != -1) {
			result = className.substring(className.lastIndexOf(".") + 1);
		}
		return result.toUpperCase();
	}

	/**
	 *
	 * @param property String
	 * @param delim String
	 * @return Map
	 */
	public static Map toMap(String property, String delim) {
		Map map = new HashMap();
		if (property != null) {
			StringTokenizer tokens = new StringTokenizer(property, delim);
			while (tokens.hasMoreTokens()) {
				map.put(tokens.nextToken(), tokens.hasMoreElements() ? tokens
						.nextToken() : EMPTY_STRING);
			}
		}
		return map;
	}

	public static String[] toStringArray(String propValue, String delim) {
		if (propValue != null) {
			return propValue.split(delim);
		} else {
			return null;
		}
	}

	public static String fieldValue2String(Object object) {
		if (object != null) {
			return object.toString();
		} else {
			return null;
		}
	}

	public static String getValue(Object object) {
		String result = "";
		if (object != null) {
			result = String.valueOf(object);
			result = result.equals("null") ? "" : result;
		}
		return result;
	}

	/**
	 * 返回指定对象的字符串值
	 * @param object Object
	 * @return String
	 */
	
	
	 public static final String replace(String line, String oldString, String newString)
     {
             if (line == null)
             {
                     return null;
             }
             int i=0;
             if ((i=line.indexOf( oldString, i )) >= 0)
             {
                     char [] line2 = line.toCharArray();
                     char [] newString2 = newString.toCharArray();
                     int oLength = oldString.length();
                     StringBuffer buf = new StringBuffer(line2.length);
                     buf.append(line2, 0, i).append(newString2);
                     i += oLength;
                     int j = i;
                     while((i=line.indexOf( oldString, i )) > 0)
                     {
                             buf.append(line2, j, i-j).append(newString2);
                             i += oLength;
                             j = i;
                     }
                     buf.append(line2, j, line2.length - j);
                     return buf.toString();
             }
             return line;
     }

	public static String returnValue(Object object) {
		String result = "";
		if (object != null) {
			result = String.valueOf(object);
			result = result.equals("null") ? "" : result;
		}
		return result;
	}

	/**
	 * 将字符串参数格式成sql格式
	 * @param paramStr String   源参数
	 * @param splitStr String   分割字符：如","
	 * @return String
	 */
	public static String returnParam(String paramStr, String splitStr) {
		StringBuffer result = new StringBuffer();
		String[] params = paramStr.split(splitStr);
		for (int i = 0; i < params.length; i++) {
			if (i == 0) {
				result.append("'" + params[i] + "'");
			} else {
				result.append("," + "'" + params[i] + "'");
			}
		}
		return result.toString();
	}

	/**
	 * 判断是否是数字
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean isValidNumber(String param) {
		try {
			int i = Integer.parseInt(param);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 截取标题为指定长度的标题...
	 * @param str String
	 * @param len int
	 * @return String
	 */
	public static String replaceTitle(String str, int len, String taget) {
		StringBuffer buf2 = new StringBuffer();
		if (str.length() > len) {
			buf2.append(str.substring(0, len));
			buf2.append(taget);
		} else {
			buf2.append(str);
		}
		return buf2.toString();
	}

	/**
	 * 判断字符是否为空
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.trim().equals("")) ? true : false;
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static float round(float v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 显示定长的数据
	 * @param str String
	 * @param length int
	 * @return String
	 */
	public static String subString(String str, int length) {
		int len = str.length();
		String strnew = "";
		if (len >= length) {
			strnew = str.substring(0, length - 2) + "....";
		} else {
			strnew = str;
		}
		return strnew;
	}

	/**
	 * 根据提供的文字连续生成该内容
	 * @param str String
	 * @param length int
	 * @return String
	 */
	public static String makeString(String str, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 *
	 * @param str String
	 * @param num int
	 * @param tag String
	 * @return String
	 */
	public static String newString(String str, int num, String tag) {
		StringBuffer sb = new StringBuffer();

		int len = str.length();
		int t = len / num;
		if (t > 0) {
			for (int i = 1; i <= t; i++) {
				if (i == 1) {
					sb.append(str.substring(0, num));
				} else {
					sb.append(tag + str.substring(num * (i - 1), num * (i)));
				}
			}
			sb.append(tag + str.substring(num * t));
		} else {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * html过滤
	 * @param str
	 * @return
	 */
	public static final String htmlFilter(String str) {
		if (str == null)
			return "&nbsp;";
		char toCompare;
		StringBuffer replaceChar = new StringBuffer(str.length() + 256);
		int maxLength = str.length();
		try {
			for (int i = 0; i < maxLength; i++) {
				toCompare = str.charAt(i);
				// 所有的 " 替换成 : &quot;
				if (toCompare == '"')
					replaceChar.append("&quot;");
				// 所有的 < 替换成： &lt;
				else if (toCompare == '<')
					replaceChar.append("&lt;");
				// 所有的 > 替换成： &gt;
				else if (toCompare == '>')
					replaceChar.append("&gt;");
				// 所有的 & 替换嘏: &amp;
				else if (toCompare == '&') {
					if (i < maxLength - 1)
						if (str.charAt(i + 1) == '#') {
							replaceChar.append("&#");
							i++;
						} else
							replaceChar.append("&amp;");
				} else if (toCompare == ' ')
					replaceChar.append("&nbsp;");
				// 所有的 \r\n （using System.getProperty("line.separator") to get it ） 替换成　<br>lihjk
				else if (toCompare == '\r')
					;//replaceChar.append("<br>");
				else if (toCompare == '\n')
					replaceChar.append("<br>");
				else
					replaceChar.append(toCompare);
			}//end for
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			return replaceChar.toString();
		}

	}

	/**
	 *
	 * @param o String
	 * @return String
	 */

	public static void main(String arg[]) {
		String str = "avccdddd";
		System.out.println(str.substring(2));
		htmlFilter("123123123123");
	}

}
