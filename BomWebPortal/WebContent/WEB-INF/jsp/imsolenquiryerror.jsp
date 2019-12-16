<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page
	import="org.springframework.web.servlet.support.RequestContextUtils,com.bomwebportal.dto.BomSalesUserDTO,java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Springboard</title>
<link rel="shortcut icon" href="pccw_mobile.png">
<link rel="apple-touch-icon" href="pccw_mobile.png" />
<link href="css/ssc.css" rel="stylesheet" type="text/css">
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style style="text/css">
.supportDocForm {
	background-color: white;
	border: 2px solid #BEBEBE;
	padding: 2px;
	margin-left: 1px
}

.supportDocForm h3 {
	margin: 0;
	font-size: medium;
	color: white;
	background-color: #ABD078;
	padding: 5px 10px;
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif"
}

.supportDocForm .error {
	padding-left: 12px
}

.supportDocForm .row {
	margin: 3px 0;
	overflow: hidden
}

.supportDocForm .label {
	display: inline-block;
	width: 25%;
	margin-right: 5px;
	text-align: right;
	vertical-align: middle
}

.supportDocForm input {
	width: 150px
}
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<body>
	<div id="container">
		<div id="head">
			<div id="header">
				<table width="100%" border="0" cellspacing="0">
					<tr>
						<td><img src="images/topbar_left.png" width="470" height="58">
						</td>
						<td align="right"></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0">
				</table>
			</div>
			<div id="wrapper">

				<div style="height: 41px"></div>
				<div class="supportDocForm">
					<h3>Online Order Enquiry Error</h3>
					<p>Order Id: ${imsOnlineOrderEnquiryId} is an Online Order, not available for Order Enquiry.</p>
					
				</div>

				<!---------------------------------------  end content ------------------------------------------->
				<%@ include file="/WEB-INF/jsp/footer.jsp"%>