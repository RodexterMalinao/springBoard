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
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils,
				 com.bomwebportal.dto.BomSalesUserDTO,
				 java.util.Locale"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Springboard</title>
<link href="css/ssc.css" rel="stylesheet" type="text/css">
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">
</head>
<div id="head"/>
<div id="wrapper"/>
<script type="text/javascript">
<!--

function closeSelfWindows(){
	self.close();
}
function backHome(){
	window.location ="welcome.html?sbuid=${param.sbuid}";
	
}
//-->

</script>
<style type="text/css">
	.shadedRow  {
		background-color: #E2F5D3;
	}
</style>

<table width="100%"  class="tablegrey" background="images/background2.jpg">
  <tr> 
  	<td> 
	  <table width="100%"  border="0" cellspacing="1" >
	   	<tr>
	   	 <div id="header_inner">
	   	 <td  class="contenttextgreen" height="40">
			The order number is ${orderId}.</td>
		</div>
	   	</tr>
	  </table>
	 </td>
  </tr>
 </table>	

<table width="100%" border="0" bordercolor="#ffffff" cellspacing="1">
	<tr> 
		<td><img src="images/x_graphic.gif" height="1" ></td>
	</tr>
	<tr class="contenttextgreen"> 
		<td class="table_title" >Document Capture</td>
	</tr>
	
	<tr> 
		<td><img src="images/x_graphic.gif" height="5" ></td>
	</tr>
	<tr>
		<td>
		<table width="60%" border="1" cellspacing="0" cellpadding="2" bgcolor="#FFFFFF"  class="contenttext">
			<tr class="contenttextgreen">
				<td class="table_title">Supporting Doc Type</td>
				<td class="table_title" align="center">File exists ?</td>
				<td class="table_title" align="center">Upload</td>
			</tr>
			<c:if test="${empty requiredDocList}">
			<tr>
				<td colspan="3" align="center" height="80px">No record.</td>
			</tr>
			</c:if>
			<c:forEach items="${requiredDocList}" var="requiredDoc">
				<c:set var="url" value="docimgupload.html?sbuid=${param.sbuid}&orderId=${requiredDoc.orderId}&docType=${requiredDoc.docType }"/>
				<tr height="30px">
					<td>${requiredDoc.docName }</td>
					<td align="center">${requiredDoc.collectedInd=="Y" ? "Y" : "&nbsp;" }</td>
					<td align="center">
						<a class="nextbutton3" href="${url}"> Upload </a>
					</td>
					
				</tr>
			</c:forEach>
		</table>

		</td>
	</tr>
	<tr> 
		<td><img src="images/x_graphic.gif" height="10" ></td>
	</tr>
	<tr class="contenttextgreen"> 
		<td class="table_title" >Captured Record</td>
	</tr>
	<tr> 
		<td><img src="images/x_graphic.gif" height="5" ></td>
	</tr>	
	<tr>
		<td> 
		<table width="100%" border="1" cellspacing="0" cellpadding="2" bgcolor="#FFFFFF" class="contenttext">
			<tr class="contenttextgreen">
				<td class="table_title"><b>Supporting Doc Type</b></td>
				<td class="table_title" align="center"><b>Seq Num</b></td>
				<td class="table_title" align="center"><b>Capture By</b></td>
				<td class="table_title"><b>Capture Date/Time</b></td>
			</tr>
			<c:if test="${empty capturedRecordList}">
			<tr>
				<td colspan="4" align="center" height="80px">No record.</td>
			</tr>
			</c:if>
		<c:set var="lastType" value="${null}"/>
		<c:set var="shadedRow" value="${true}"/>
		<c:forEach items="${capturedRecordList}" var="capturedRecord">
			<c:set var="shadedRow" value="${ (capturedRecord.docType != lastType)? !shadedRow: shadedRow }"/>
			<c:set var="lastType" value="${capturedRecord.docType }"/>
			<c:set var="typeStyle" value='${ (shadedRow) ? "shadedRow" : "" }'/>
			<tr class="${typeStyle}" height="30px">
				<td>${capturedRecord.docName }</td>
				<td align="center">${capturedRecord.seqNum }</td>
				<td align="center">${capturedRecord.captureBy }</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${capturedRecord.captureDate }" /></td>
			</tr>
		</c:forEach>
		</table>
		</td>
	</tr>
</table>
<div>&nbsp;</div>
<table  width="100%" border="0" cellspacing="0">
	<tr>
		<td align="right">
			
			<a href="#" class="nextbutton3"  onClick="javascript:closeSelfWindows();"> Close</a>
		</td>
	</tr>
</table>

<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>