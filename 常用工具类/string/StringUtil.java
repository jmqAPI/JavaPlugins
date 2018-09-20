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
     * ����ͨ�ַ�����ʽ�������ݿ��Ͽɵ��ַ�����ʽ
     *
     * @param str Ҫ��ʽ�����ַ���
     * @return �Ϸ������ݿ��ַ���
     */
    public static String toSql(String str) {
      String sql = new String(str);
      return Replace(sql, "'", "''");
    }
    
    /**
     * �ַ����滻���� source �е� oldString ȫ������ newString
     *
     * @param source Դ�ַ���
     * @param oldString �ϵ��ַ���
     * @param newString �µ��ַ���
     * @return �滻����ַ���
     * ��������ı��ַ���ת����HTML��ʽ���ı�
     */
    public static String Replace(String source, String oldString,
                                 String newString) {
      StringBuffer output = new StringBuffer();

      int lengthOfSource = source.length(); // Դ�ַ�������
      int lengthOfOld = oldString.length(); // ���ַ�������

      int posStart = 0; // ��ʼ����λ��
      int pos; // ���������ַ�����λ��

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
	 * �õ���ǰ����(�ı���ʽ)
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
	 *  ��ҳ���еı������Ҫ������ʾ���ַ������и�ʽ����ʹ���ʺ����ڸ����ĳ�������ʾ��
	 *  ���ȳ���ʱ����ʾΪ"ssssss..."
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
	 * ������������
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
	 * ����ָ��������ַ���ֵ
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
	 * ���ַ���������ʽ��sql��ʽ
	 * @param paramStr String   Դ����
	 * @param splitStr String   �ָ��ַ�����","
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
	 * �ж��Ƿ�������
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
	 * ��ȡ����Ϊָ�����ȵı���...
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
	 * �ж��ַ��Ƿ�Ϊ��
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.trim().equals("")) ? true : false;
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * @param v ��Ҫ�������������
	 * @param scale С���������λ
	 * @return ���������Ľ��
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
	 * ��ʾ����������
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
	 * �����ṩ�������������ɸ�����
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
	 * html����
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
				// ���е� " �滻�� : &quot;
				if (toCompare == '"')
					replaceChar.append("&quot;");
				// ���е� < �滻�ɣ� &lt;
				else if (toCompare == '<')
					replaceChar.append("&lt;");
				// ���е� > �滻�ɣ� &gt;
				else if (toCompare == '>')
					replaceChar.append("&gt;");
				// ���е� & �滻��: &amp;
				else if (toCompare == '&') {
					if (i < maxLength - 1)
						if (str.charAt(i + 1) == '#') {
							replaceChar.append("&#");
							i++;
						} else
							replaceChar.append("&amp;");
				} else if (toCompare == ' ')
					replaceChar.append("&nbsp;");
				// ���е� \r\n ��using System.getProperty("line.separator") to get it �� �滻�ɡ�<br>lihjk
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
