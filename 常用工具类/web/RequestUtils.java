package com.skywing.utils.web;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.beanutils.BeanUtils;



public class RequestUtils {
  public RequestUtils() {
  }

  /**
 * <p>Populate the properties of the specified JavaBean from the specified
 * HTTP request, based on matching each parameter name against the
 * corresponding JavaBeans "property setter" methods in the bean's class.
 * Suitable conversion is done for argument types as described under
 * <code>convert()</code>.</p>
 *
 * @param bean The JavaBean whose properties are to be set
 * @param request The HTTP request whose parameters are to be used
 *                to populate bean properties
 *
 * @exception ServletException if an exception is thrown while setting
 *            property values
 */
public static void populate(Object bean, HttpServletRequest request) throws ServletException {

    populate(bean, null, null, request);

}

  /**
    * <p>Populate the properties of the specified JavaBean from the specified
    * HTTP request, based on matching each parameter name (plus an optional
    * prefix and/or suffix) against the corresponding JavaBeans "property
    * setter" methods in the bean's class. Suitable conversion is done for
    * argument types as described under <code>setProperties</code>.</p>
    *
    * <p>If you specify a non-null <code>prefix</code> and a non-null
    * <code>suffix</code>, the parameter name must match <strong>both</strong>
    * conditions for its value(s) to be used in populating bean properties.
    * If the request's content type is "multipart/form-data" and the
    * method is "POST", the <code>HttpServletRequest</code> object will be wrapped in
    * a <code>MultipartRequestWrapper</code object.</p>
    *
    * @param bean The JavaBean whose properties are to be set
    * @param prefix The prefix (if any) to be prepend to bean property
    *               names when looking for matching parameters
    * @param suffix The suffix (if any) to be appended to bean property
    *               names when looking for matching parameters
    * @param request The HTTP request whose parameters are to be used
    *                to populate bean properties
    *
    * @exception ServletException if an exception is thrown while setting
    *            property values
    */
   public static void populate(
           Object bean,
           String prefix,
           String suffix,
           HttpServletRequest request)
           throws ServletException {

       // Build a list of relevant request parameters from this request
       HashMap properties = new HashMap();
       // Iterator of parameter names
       Enumeration names = null;
       // Map for multipart parameters
       Map multipartParameters = null;
       String contentType = request.getContentType();
       String method = request.getMethod();
       boolean isMultipart = false;
       /**暂时不对多类型数据做操作
       if ((contentType != null)

               && (contentType.startsWith("multipart/form-data"))
               && (method.equalsIgnoreCase("POST"))) {

           // Get the ActionServletWrapper from the form bean
           ActionServletWrapper servlet;
           if (bean instanceof ActionForm) {
               servlet = ((ActionForm) bean).getServletWrapper();
           } else {
               throw new ServletException(
                       "bean that's supposed to be "
                       + "populated from a multipart request is not of type "
                       + "\"org.apache.struts.action.ActionForm\", but type "
                       + "\""
                       + bean.getClass().getName()
                       + "\"");
           }

           // Obtain a MultipartRequestHandler
           MultipartRequestHandler multipartHandler = getMultipartHandler(request);

           // Set the multipart request handler for our ActionForm.
           // If the bean isn't an ActionForm, an exception would have been
           // thrown earlier, so it's safe to assume that our bean is
           // in fact an ActionForm.
           ((ActionForm) bean).setMultipartRequestHandler(multipartHandler);

           if (multipartHandler != null) {
               isMultipart = true;
               // Set servlet and mapping info
               servlet.setServletFor(multipartHandler);
               multipartHandler.setMapping(
                       (ActionMapping) request.getAttribute(Globals.MAPPING_KEY));
               // Initialize multipart request class handler
               multipartHandler.handleRequest(request);
               //stop here if the maximum length has been exceeded
               Boolean maxLengthExceeded =
                       (Boolean) request.getAttribute(
                               MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
               if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())) {
                   return;
               }
               //retrieve form values and put into properties
               multipartParameters = getAllParametersForMultipartRequest(
                       request, multipartHandler);
               names = Collections.enumeration(multipartParameters.keySet());
           }
       }
       **/
       if (!isMultipart) {
           names = request.getParameterNames();
       }

       while (names.hasMoreElements()) {
           String name = (String) names.nextElement();
           String stripped = name;
           if (prefix != null) {
               if (!stripped.startsWith(prefix)) {
                   continue;
               }
               stripped = stripped.substring(prefix.length());
           }
           if (suffix != null) {
               if (!stripped.endsWith(suffix)) {
                   continue;
               }
               stripped = stripped.substring(0, stripped.length() - suffix.length());
           }
           Object parameterValue = null;
           if (isMultipart) {
               parameterValue = multipartParameters.get(name);
           } else {
               parameterValue = request.getParameterValues(name);
           }

           // Populate parameters, except "standard" struts attributes
           // such as 'org.apache.struts.action.CANCEL'
           if (!(stripped.startsWith("org.apache.struts."))) {
               properties.put(stripped, parameterValue);
           }
       }
       //设置BEAN的对应的属性
       try {
           BeanUtils.populate(bean, properties);
       } catch(Exception e) {
           throw new ServletException("BeanUtils.populate", e);
       }

   }


}
