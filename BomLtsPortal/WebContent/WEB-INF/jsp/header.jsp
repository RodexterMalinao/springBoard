<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("P3P","CP=CAO PSA OUR");
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils,java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Online Sales</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge; IE=5; IE=7; IE=8; IE=9; IE=EmulateIE7; IE=EmulateIE8; IE=EmulateIE9" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="format-detection" content="telephone=no"/>
<!-- <link rel="stylesheet" type="text/css" href="./css/form_layout.css" />
<script type="text/javascript" src="js/jquery-1.4.4.js"></script> -->
<link rel="stylesheet" type="text/css" href="./css/jquery-ui-1.9.1.custom.css" />
<script src="js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.9.0.custom.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script src="js/ui/jquery.ui.core.js"></script>
<script src="js/ui/jquery.ui.widget.js"></script> 
<script src="js/ui/jquery.ui.button.js"></script>
<script src="js/jquery-scrolltofixed.js"></script>
<script src="js/jquery.floatinglayer.js"></script>
<script type="text/javascript" charset="utf-8" src="./js/service_coverage.js"></script>
<link href="./js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<!--  <link rel="stylesheet" type="text/css" href="./css/onlinesales.css" />-->
<link rel="stylesheet" type="text/css" href="./css/livechat.css" /> 
<!-- link href="css/colorbox.css" rel="stylesheet" type="text/css" /-->

<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='EYE'}">
	<link rel="stylesheet" type="text/css" href="./css/style.css" /> 
	<link href="css/colorbox.css" rel="stylesheet" type="text/css" />
</c:if>
<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='DEL'}">
	<link rel="stylesheet" type="text/css" href="./css/style_del.css" /> 
	<link href="css/colorbox_del.css" rel="stylesheet" type="text/css" />
</c:if>
<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='ALL'}">
	<link rel="stylesheet" type="text/css" href="./css/style_all.css" /> 
	<link href="css/colorbox.css" rel="stylesheet" type="text/css" />
</c:if>
<c:if test="${sessionOnlineOrderCapture.serviceTypeInd==null}">
	<link rel="stylesheet" type="text/css" href="./css/style.css" /> 
	<link href="css/colorbox.css" rel="stylesheet" type="text/css" />
</c:if>
</head>
</html>