
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page
	import="		org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.dto.BomSalesUserDTO,
					java.util.Locale
					"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Springboard</title>

<link href="css/custom-theme/jquery-ui-1.10.4.custom.css"
	rel="stylesheet" />
<link href="css/custom-theme/ltssb.css" rel="stylesheet" type="text/css" />

<link href="css/custom-theme/datatable/css/jquery.dataTables.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="css/custom-theme/jquery.treetable.theme.default.css" />

<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" language="javascript"
	src="css/custom-theme/datatable/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>

<script>

	function checkWindow(){
		/*hide header/footer if page is inside an popup or iframe*/
		/* if (window.opener) { //popup
			
		}else  */
		if(window.self !== window.top){
			//iframe
			$("#header").hide();
			$("#infoBar").hide();
			$("#progressBar").hide();
			$("#footer").hide();
		}
	}
	
	function changeLang(lang){
		var queryParameters = {}, queryString = location.search.substring(1),
		    re = /([^&=]+)=([^&]*)/g, m;
	
		while (m = re.exec(queryString)) {
		    queryParameters[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
		}
	
		queryParameters['language'] = lang;
	
		location.search = $.param(queryParameters);
	}
	
	$( document ).ready(function(){
		checkWindow();
	});
</script>

<%
	String lang = request.getParameter("language");
	if(lang!=null && !lang.equals(""))
	{
		request.setAttribute("language", lang);
		
	}
	else
	{
		Locale locale = RequestContextUtils.getLocale(request);
		//locale = new Locale("zh", "HK");
		request.setAttribute("language", locale.toString());
	}
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
</head>
<body>

	<div class="paper_w2">
		<div id="header">
			<%if (!"true".equals(request.getParameter("isAmend"))){%>
			<table border="0" width="100%">
				<tr>
					<td align="left" width="70%">
						<img src="images/hkt_logo.png" alt="" width="200" height="50" />
					</td>
					<td align="right">
						<h3 class="demoHeaders">
							<%= channelCd %>
							/
							<%= areaCd %>
							/
							<%= shopCd %>
							/
							<%= username %>
						</h3>
					</td>
					<%if (bomsalesuser != null){ %>
					<% if (bomsalesuser.getChannelId() == 12 || bomsalesuser.getChannelId() == 13) {%>
					<td align="right">
						<c:choose>
							<c:when test="${language == 'en_US' || language == 'en'}">
								<a href="welcome.html?sbuid=${param.sbuid}" onclick="return confirm('All inputted information will be cleared. Sure to continue?');">Home</a>
							</c:when>
							<c:otherwise>
								<a href="welcome.html?sbuid=${param.sbuid}" onclick="return confirm('所有已輸入的資料會被清除，確定繼續？');">Home</a>
							</c:otherwise>
						</c:choose>						
					</td>
					<%}%>
					<%}%>
					<td align="right">
						<c:choose>
							<c:when test="${language == 'en_US' || language == 'en'}">
								<a href="#" onclick="changeLang('zh_HK');">中文</a>
							</c:when>
							<c:otherwise>
								<a href="#" onclick="changeLang('en');" >English</a>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
			<%} %>
		</div>
		<div class="paper_w2" style="height: 100%">