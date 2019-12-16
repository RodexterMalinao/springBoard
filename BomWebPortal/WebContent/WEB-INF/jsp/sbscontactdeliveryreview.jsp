<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils,com.bomwebportal.dto.BomSalesUserDTO,com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO,com.bomwebportal.bean.LookupTableBean,com.bomwebportal.mob.ccs.dto.CodeLkupDTO,com.bomwebportal.util.ConfigProperties,java.util.*,java.util.Locale"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>PCCW Mobile</title>
<link href="css/ssc.css" rel="stylesheet" type="text/css">
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<link href="css/imsds.css" rel="stylesheet" type="text/css">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/common.js" language="javascript"></script>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui-1.10.3.js"></script>


<script src="js/jquery-bomaddress.js"></script>
<%
	Locale locale = RequestContextUtils.getLocale(request);
	request.setAttribute("language", locale.toString());
	BomSalesUserDTO bomsalesuser = (BomSalesUserDTO) request
			.getSession().getAttribute("orderdetailBomsalesuser");
	String salesChannelId = (String) request
			.getSession().getAttribute("salesChannelId");
	//String enableOnloadJScript =  request.getParameter("enableOnloadJScript");
%>
<script language="Javascript">
function formSubmit(actionType){
	window.location ="welcome.html";
}




$(document).ready(function() {
	$("#cancelBtn").click(function(event) {
		event.preventDefault();
		//window.location="sbscontactdelivery.html?orderId=${order.orderId}";
		//window.history.back();
		document.getElementById('backlink').click();
	});
	$("#confirmBtn").click(function(event) {
		event.preventDefault();
		if (confirm('Contact and delivery information will be updated. Are you sure to proceed ?')) {
			$("#contactDeliveryForm").submit();
		}
	});

});
	
</script> 
<base target="_self"/>
<body >
<a id="backlink" href="sbscontactdelivery.html?orderId=${order.orderId}" style="display:none"></a>			
<div id="container">
<div id="head">
<div id="header">
	<table width="100%" border="0" cellspacing="0">  			
          	 
  					<tr>
        				<td><img src="images/topbar_left.png" width="470" height="58"></td>
        				<%-- <%
        					if (bomsalesuser != null) {
        				%>
        				<td align="right"> <a href="welcome.html">Home</a> | <a href="logout.html">Logout</a></td>
        				<%
        					}
        				%>
          				<td align="right">
  						<div class="lang"> English</div>
  					   	</td> --%>
  					</tr>
	</table>
</div>
<div id="wrapper">
<form id="contactDeliveryForm" method="post" action="#">

 <!-- start table-->
 <table width="100%" border="1">
 <tr>
							<td class="table_title">Update Contact and Delivery Info</td>
						</tr>
		<tr>
			<td>
			
			<!--content-->
			<!-- Title table-->
			<table width="100%" border="0" cellspacing="0">
				<tr>
					<td class="contenttextgreen" height="40">
						<sb-ui:orderstatusinfo value="${order }"/>
					</td>
					<td class="contenttextgary"></td>
				</tr>
			</table>
			
			<c:set var="contactInfo" value="${sbsContactDeliveryInfo.contactInfo }"/>
			<c:set var="deliveryInfo" value="${sbsContactDeliveryInfo.deliveryInfo }"/>
			<!-- Contact Information table -->

			<table width="100%" border="0" cellspacing="0" class="contenttext">


				<tr>
					<td colspan="4" class="table_title">Contact Information</td>
				</tr>
				<tr >
				<td valign="top" bgcolor="#FFFFFF">
				
				
				<table width="100%" border="0" cellspacing="1">
					<colgroup>
						<col style="width:6%;"/>
						<col style="width:200px;"/>	
					</colgroup>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Title</b></td>
						<td>${contactInfo.title}</td>
						<td><b>Family Name</b></td>
						<td>${contactInfo.lastName }</td>
						<td><b>Given Name</b></td>
						<td>${contactInfo.firstName }</td>						
					</tr>

					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Contact Phone</b></td>
						<td>${contactInfo.contactPhone }</td>
						<td><b>Email</b></td>
						<td>${contactInfo.emailAddr }</td>						
					</tr>

				</table>													

				
			</table>

			<!-- Delivery Information table -->
			<c:if test="${(not empty deliveryInfo) and (sbsContactDeliveryInfo.orderSubType ne '2')}">
			<table width="100%" border="0" cellspacing="0" class="contenttext">

				<tr>
					<td colspan="4" class="table_title">Delivery Information</td>
				</tr>
				<tr >
				<td valign="top" bgcolor="#FFFFFF">
				
				
				<table width="100%" border="0" cellspacing="1">
					<colgroup>
						<col style="width:6%;"/>
						<col style="width:200px;"/>	
					</colgroup>			
					

					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Flat</b></td>
						<td>${deliveryInfo.unitNo }</td>
						<td><b>Floor</b></td>
						<td>${deliveryInfo.floorNo }</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Building/Estate/Lot No./House</b></td>
						<td colspan="2">${deliveryInfo.buildNo }</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Street Number</b></td>
						<td>${deliveryInfo.strNo }</td>
						<td><b>Street Name</b></td>
						<td>${deliveryInfo.strName }</td>
						<td><b>Street Category</b></td>
						<td>${deliveryInfo.strCatDesc }</td>					
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Section</b></td>
						<td>${deliveryInfo.sectDesc }</td>
						<td><b>District</b></td>
						<td>${deliveryInfo.distDesc }</td>
						<td><b>Area</b></td>
						<td>${deliveryInfo.areaDesc }</td>												
					</tr>	
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Delivery Date</b></td>
						<td><fmt:formatDate value="${deliveryInfo.deliveryDate }" pattern="dd/MM/yyyy"/></td>
						<td><b>Delivery Time Slot</b></td>
						<td>${deliveryInfo.deliveryTimeSlotDesc }</td>
					</tr>
					
				</table>		
			</table>
			<!-- Delivery Information table -->
			
			</c:if>

			<div class="buttonmenubox_R">
				<button id="cancelBtn" type="button"> Back </button>
				<button id="confirmBtn" type="button"> Confirm </button>
			</div>

			


				
			
			<!--content--></td>
		</tr>
	</table>
 <!-- end table -->



						<!--content-->
						<!-- end table -->
					


					<input type="hidden" name="currentView" value="confirmation"/>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>