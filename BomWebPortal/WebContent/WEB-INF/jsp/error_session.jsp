<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="     
          org.springframework.web.servlet.support.RequestContextUtils,
          com.bomwebportal.dto.BomSalesUserDTO,
          org.apache.commons.lang.exception.*,
          java.util.Locale
          "
%>
<%
String stackTrace = ExceptionUtils.getFullStackTrace((Throwable)request.getAttribute("exception"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>BomWebPortal</title>
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">

<script>
function closeme() {
  window.open('','_self','');
  window.close();
}
</script>
</head>
<body>

<div id="container">
<div id="head">
<div id="header">
  <table width="100%" border="0" cellspacing="0">   
    <tr>
          <td><img src="<c:url value='/images/topbar_left.png'/>" width="470" height="58"></td>               
    </tr>       
  </table>
</div>
</div>
<div id="wrapper">
<h3>
<p>${exception.customMessage}</p>
</h3>
<p>
  Please <a href="#" onclick="closeme();return false;">close this window</a> and try again.
</p>

<!-- 
<p><pre  style="font-size: 12px;"><%=stackTrace%></pre></p>
 -->
</div>
</div>

</body>
</html>