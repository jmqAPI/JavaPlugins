package com.skywing.utils.web;

import org.apache.log4j.Logger;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

public class SearchCondition {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SearchCondition.class);

        private Vector v ;
        public SearchCondition(){
          v = new Vector();
        }

 /**
       fieldname :���ݿ��Ӷ�����
       reqname  :��Ӧ��ҳ���ֶ�����
       type :�Ӷ�����
            ģ��ƥ���ַ����� 			--- String
            ��ȷƥ���ַ����� 			--- Char
            ������ 			        --- Number
            ����֮������ 				--- Date>
            ����֮������(������ǰ����)  ---   Date>=
            ����֮ǰ���� 				--- Date<
            ����֮ǰ����(������ǰ����) --- Date<=
 */

 public void addCondition(String fieldname,String pageFieldname,String fieldtype){
        String[] s = new String[3] ;
        s[0] = fieldname ;
        s[1] = pageFieldname ;
        s[2] = fieldtype ;
        v.add (s);
}
 
 
public  String getsCondition(HttpServletRequest request,boolean isWhere){
		if (logger.isDebugEnabled()) {
			logger.debug("getsCondition(HttpServletRequest, boolean) - start");
		}
	    
        String sCondition ="";
        String fieldname, reqname, type ;
        String[] s = new String[3] ;
        if(v!=null&&v.size ()>0){
        for(int i = 0 ; i<v.size ();i++){
                s = (String[])v.get (i);
                fieldname = s[0] ;
                reqname = s[1] ;
                type = s[2] ;

                if(request.getParameter(reqname)!=null&&!request.getParameter(reqname).equals("")){
                        String sreq = request.getParameter(reqname).trim() ;
                        if(type.equalsIgnoreCase ("number>=")){
                                sCondition += " and "+fieldname+" >= "+sreq+"";
                        }else if(type.equalsIgnoreCase ("number>")){
                                sCondition += " and "+fieldname+" > "+sreq+"";
                        }else if(type.equalsIgnoreCase ("number<=")){
                                sCondition += " and "+fieldname+" <= "+sreq+"";
                        }else if(type.equalsIgnoreCase ("number<")){
                                sCondition += " and "+fieldname+" < "+sreq+"";
                        }else if(type.equalsIgnoreCase ("number")){
                                sCondition += " and "+fieldname+" = "+sreq+"";
                        }else if(type.equalsIgnoreCase ("string")){
                                sCondition += " and "+fieldname+" like '%"+sreq+"%'";
                        }else if(type.equalsIgnoreCase ("likeR")){
                                sCondition += " and "+fieldname+" like '"+sreq+"%'";
                        }else if(type.equalsIgnoreCase ("likeL")){
                                sCondition += " and "+fieldname+" like '%"+sreq+"'";
                        }else if(type.equalsIgnoreCase ("char")){
                                sCondition += " and "+fieldname+" = '"+sreq+"'";
                        }else if(type.equalsIgnoreCase ("date>")){
                                sCondition += " and trunc("+fieldname+") > to_date('"+sreq+"','yyyy-mm-dd')";
                        }else if(type.equalsIgnoreCase ("date>=")){
                                sCondition += " and trunc("+fieldname+") >= to_date('"+sreq+"','yyyy-mm-dd')";
                        }else  if(type.equalsIgnoreCase ("date<")){
                                sCondition += " and trunc("+fieldname+") < to_date('"+sreq+"','yyyy-mm-dd')";
                        }else  if(type.equalsIgnoreCase ("date<=")){
                                sCondition += " and trunc("+fieldname+") <= to_date('"+sreq+"','yyyy-mm-dd')";
                        }else  if(type.equalsIgnoreCase ("date=")){
                                sCondition += " and trunc("+fieldname+") = to_date('"+sreq+"','yyyy-mm-dd')";
                        }else if(type.equalsIgnoreCase ("datemm>")){
                                sCondition += " and "+fieldname+" > to_date('"+sreq+"','yyyy-mm')";
                        }else if(type.equalsIgnoreCase ("datemm>=")){
                                sCondition += " and "+fieldname+" >= to_date('"+sreq+"','yyyy-mm')";
                        }else  if(type.equalsIgnoreCase ("datemm<")){
                               sCondition += " and "+fieldname+" < to_date('"+sreq+"','yyyy-mm-dd')";
                        }else  if(type.equalsIgnoreCase ("datemm<=")){
                               sCondition += " and "+fieldname+" <= to_date('"+sreq+"','yyyy-mm')";
                        }else  if(type.equalsIgnoreCase ("datemm=")){
                               sCondition += " and "+fieldname+" = to_date('"+sreq+"','yyyy-mm')";
                        }else  if(type.equalsIgnoreCase ("in")){
                                sCondition += " and "+fieldname+" in ("+sreq+")";
                        }
        }
        }
        }
        if(!isWhere&&sCondition.length ()>5){
        sCondition =" WHERE "+ sCondition.substring(5) ;
   }

		if (logger.isDebugEnabled()) {
			logger.debug("getsCondition(HttpServletRequest, boolean) - end");
		}
		 logger.info("�������"+sCondition);
         return sCondition ;

 }


}
