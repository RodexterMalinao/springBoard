<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="			
					org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.dto.BomSalesUserDTO,
					java.util.Locale
					"
%>
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
<script src="js/common.js" language="javascript"></script>
<script type="text/javascript" src="js/jquery-1.4.4.js">
</script>

<%
	Locale locale = RequestContextUtils.getLocale(request);
	request.setAttribute("language", locale.toString());
	String enableOnloadJScript =  request.getParameter("enableOnloadJScript");
	BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
	
	String shopCd = "";
	String areaCd = "";
	String channelCd = "";
	int channelId = 0;
	String username = "";
	String pilotStatus = "";
	String ltsPilotStatus = "";
	String mobPilotStatus = "";
	if (bomsalesuser != null) {
	  shopCd = bomsalesuser.getShopCd();
	  areaCd = bomsalesuser.getAreaCd();
	  channelCd = bomsalesuser.getChannelCd();
	  channelId = bomsalesuser.getChannelId();
	  username = bomsalesuser.getUsername();
	  pilotStatus = bomsalesuser.getPilotStatus();
	  ltsPilotStatus = bomsalesuser.getLtsPilotStatus();
	  mobPilotStatus = bomsalesuser.getMobPilotStatus();
	}
%>

<body >

<div id="container">
<div id="head">
<div id="header">


	
	<table width="100%" border="0" cellspacing="0">  	
				
           	 <c:choose>
  				<c:when test="${language == 'en'}">
  					<tr>
        				<td><img src="images/topbar_left.png" width="470" height="58"></td>
        				<%-- <%if (bomsalesuser != null){ %>
        					<%if (bomsalesuser.getChannelId() == 1){ %>
		        				<td align="right"> 
			        				<a href="welcome.html?sbuid=${param.sbuid}" onclick="return confirm('All inputted information will be cleared. Sure to continue?');">Home</a> |
<!--			        				<a href="changeshopcode.html">Change Shop Code</a> |  -->
			        				<a href="logout.html">Logout</a>
		        				</td>
	        				<%} else {%>
	        					<td align="right"> 
			        				<a href="welcome.html?sbuid=${param.sbuid}" onclick="return confirm('All inputted information will be cleared. Sure to continue?');">Home</a> | 
			        				<a href="logout.html">Logout</a>
		        				</td>
	        				<%}%>
	        			<%}%>
          				<td align="right">
  						<div class="lang"> English </div>
  					   	</td> --%>
  					</tr>
				</c:when>
 				<c:otherwise>  
 					<tr>
        				<td><img src="images/topbar_left_tc.png" width="470" height="58"></td>
        				<%-- <%if (bomsalesuser != null){ %>
        					<%if (bomsalesuser.getChannelId() == 1){ %>
		        				<td align="right"> 
			        				<a href="welcome.html?sbuid=${param.sbuid}" onclick="return confirm('All inputted information will be cleared. Sure to continue?');">Home</a> |
<!--			        				<a href="changeshopcode.html">Change Shop Code</a> |  -->
			        				<a href="logout.html">Logout</a>
		        				</td>
	        				<%} else {%>
	        					<td align="right"> 
			        				<a href="welcome.html?sbuid=${param.sbuid}" onclick="return confirm('All inputted information will be cleared. Sure to continue?');">Home</a> | 
			        				<a href="logout.html">Logout</a>
		        				</td>
	        				<%}%>
	        			<%}%>
          				<td align="right">
  						<div class="lang"> English </div>
  					   	</td> --%>
  					</tr>
  				</c:otherwise>
  			 </c:choose>     	
       
	</table>
	<table width="100%" border="0" cellspacing="0">  	
		<%if(bomsalesuser!=null){ %>		
          	 <tr class="contenttextgreen">
	          	 <td align="right"><%= channelCd %> / <%= areaCd %> / <%= shopCd %> / <%= username %></td> 
          	 </tr>
       <%}%>
	</table>
</div>
<div id="wrapper">
	
