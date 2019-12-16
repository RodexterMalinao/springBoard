<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils,com.bomwebportal.dto.BomSalesUserDTO,com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO,com.bomwebportal.bean.LookupTableBean,com.bomwebportal.mob.ccs.dto.CodeLkupDTO,com.bomwebportal.util.ConfigProperties,java.util.*,java.util.Locale"
%>
<%@ page import="com.bomwebportal.util.ConfigProperties"%>


<%
String mobCosUrl = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");
%>
<html>
<head>
<title>PCCW Mobile</title>
<link href="css/ssc.css" rel="stylesheet" type="text/css">
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">
<link href="css/jquery-ui-1.10.3.css" rel="stylesheet" type="text/css">
<script src="js/common.js" language="javascript"></script>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui-1.10.3.js"></script>


<script src="js/jquery-bomaddress.js"></script>

<style type="text/css">
.kingking { margin-left: 20px;
			background: #aaaaaa; }
.contenttextgary{
	font-family:    "Helvetica", "Verdana", "Arial", "sans-serif";
	padding-left: 5px;
	font-size: 14px; 
	color: #616161; 
		}
.contenttextgreen{
	font-family:    "Helvetica", "Verdana", "Arial", "sans-serif";
	padding-left: 5px;
	font-size: 14px;
	color: #64a903;
	font-weight: bold;
}			
.orderRemarkRemark { font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; margin: 0 }
</style>


<script language="Javascript">
	function formSubmit(actionType){
		window.location ="welcome.html";
	}
	
function reloadcallback() {
	window.location.reload();
}

function showUpdateContact() {
	//window.location="sbscontactdelivery.html?orderId=${param.orderId}";
	var sURL = "sbscontactdelivery.html?orderId=${param.orderId}&init=true&hideToolbar=true";
	/* 	var vArguments =self;
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.showModalDialog(sURL, vArguments, sFeatures); */
	window.open(sURL, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')

}

function showFalloutHistory(orderId) {
	var sURL = "mobccsfallouthistory.html?orderId="+orderId+"&hideToolbar=true";
	/*
	var vArguments = new Object();
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.showModalDialog(sURL, vArguments, sFeatures);
	*/
	window.open(sURL, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')

}

function showResendEmail() {

	//var sURL = "ordersendemailhist.html?orderId=${param.orderId}&templateId=RT004&action=PREVIEW&hideToolbar=true";
	var sURL = "sbsordemailhistory.html?orderId=${param.orderId}&hideToolbar=true";
	/*
	var vArguments = self;
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.showModalDialog(sURL, vArguments, sFeatures);
	*/
	window.open(sURL, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')

}


function showRetrieveForm() {
	window.location="mobccsreport.html?osOrderId=${param.orderId}&pdfLang=${order.esigEmailLang=='CHN' ? 'zh_HK' : 'en'}";
	/*
	if (${isFormExists}) {
		window.location="reportdownload.html";
	} else {
		alert("Form not found");
	}
	*/
}

function showAddRemarks() {

	var sURL = "orderremark.html?orderId=${param.orderId}";
	/*
	var vArguments = self;
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.showModalDialog(sURL, vArguments, sFeatures);
	*/
	window.open(sURL, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')
}

function showDOARequest() {
	//var sURL = "mobccsdoarequest.html?orderId=${param.orderId}&hideToolbar=true";
	var sURL = "<%=mobCosUrl%>doarequest.html?orderId=${param.orderId}&_al=new";
	var vArguments = self;
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.open(sURL, vArguments, sFeatures);

}
function showOnsiteDOA() {
	var sURL = "sbsdoa.html?orderId=${param.orderId}&init=true&hideToolbar=true";
	var vArguments = self;
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.open(sURL, vArguments, sFeatures);

}
function showDOA() {
	var sURL = "sbsdoa.html?orderId=${param.orderId}&init=true&hideToolbar=true";
	var vArguments = self;
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.open(sURL, vArguments, sFeatures);

}


function insertPosSM() {
	var sURL = "sbspossminput.html?orderId=${param.orderId}&hideToolbar=true";
	var vArguments = self;
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.open(sURL, vArguments, sFeatures);
	
}
function requestCancelOrder() {
	var sURL = "sbsordercancelrequest.html?orderId=${param.orderId}&hideToolbar=true";
	var vArguments = self;
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.open(sURL, vArguments, sFeatures);
	
}

function paymentTrxn() {
	var sURL = "mobccspaymenttranshistory.html?orderId=${param.orderId}&hideToolbar=true";
	var vArguments = new Object();
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.open(sURL, vArguments, sFeatures);
}

function checkStockQty(orderId) {
	$.ajax({
		url: "mobccscheckstockqtyajax.html"
		, data: {
			orderId: orderId
		}
		, cache: false
		, dataType: "json"
		, success: function(data) {
			var displayText = "Item Code          Location         Qty\n";
			$.each(data, function(index, item) {
				displayText += item.stock_qty + "\n";
			});
			alert(displayText);
		}
		, error: function() {
			alert("Cannot retrieve data. Please re-try later.");
		}
	});
}


$(document).ready(function() {


	$('#address').addressgroup();

	$('#updateContactDeliveryBtn').attr("disabled", ${not allowed['updatecontactdelivery']});
	$('#doaRequestBtn').attr("disabled", ${not allowed['doarequest']});
	$('#requestCancelOrderBtn').attr("disabled", ${not allowed['requestcancelorder']});
	$('#updatePosSmBtn').attr("disabled", ${not allowed['updatepossm']});
	$('#onsiteDoaBtn').attr("disabled", ${not allowed['onsitedoa']});
	$('#doaBtn').attr("disabled", ${not allowed['doa']});

	
	$('#btnFallout').attr("disabled", ${bomsalesuser.channelId ne 2 && bomsalesuser.channelId ne 66});
	$('#btnResendEmail').attr("disabled", ${bomsalesuser.channelId ne 2 && bomsalesuser.channelId ne 66});
	$('#btnRetrieveForm').attr("disabled", ${(bomsalesuser.channelId ne 2 and bomsalesuser.channelId ne 66) or (orderMob.orderSubType eq '2') });
	$('#btnAddRemarks').attr("disabled",${bomsalesuser.channelId ne 2 && bomsalesuser.channelId ne 66});
	

});
	
</script> 


<form name="orderdetail" method="post" action="#">

 <!-- start table-->
 <table width="100%" border="1">
 <tr>
							<td class="table_title">Standalone Online Sales Order Summary</td>
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
			
			
			<!-- action buttons .. -->
			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">&nbsp;</td>
				</tr>
				<tr>
					<td valign="top" bgcolor="#FFFFFF">
						<table width="100%" border="0" cellspacing="1">
							<tr>
								<td width="6%">&nbsp;</td>
								<td><button id="btnFallout" type="button" onclick="javascript:void showFalloutHistory('${param.orderId}');">Fallout History</button></td>
								<td><button id="btnResendEmail" type="button" onclick="javascript:void showResendEmail();">Resend Email (Original Copy Only)</button></td>
								<td><button id="btnRetrieveForm" type="button" onclick="javascript:void showRetrieveForm();">Retrieve Form</button></td>
								<td><button id="btnAddRemarks" type="button" onclick="javascript:void showAddRemarks();">Add Remarks</button></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			
			
<!--  Remarks Section ..... -->

			<div
				style="padding-left: 6px; _padding-left: 0; *padding-left: 0; padding-bottom: 1px">
				<div class="table_title toggleOrderRemark"
					style="font-size: 12px; cursor: pointer"
					onclick="javascript:$('.orderRemarkResultList').toggle(); $('.toggleOrderRemarkResultListStatus').text($('.orderRemarkResultList').is(':visible') ? '[-]' : '[+]');">
					<span class="toggleOrderRemarkResultListStatus"
						style="float: right">[+]</span>
					<spring:message
						code="label.mobccspreview.header.orderRemark" />
				</div>
				<table width="100%" border="1" cellspacing="0"
					class="orderRemarkResultList contenttext"
					bgcolor="#FFFFFF" style="display: none">
					<thead>
						<tr class="contenttextgreen">
							<td class="table_title">Remark</td>
							<td class="table_title">Create By</td>
							<td class="table_title">Create Date</td>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty orderRemarkResultList}">
								<tr>
									<td colspan="3">No record</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${orderRemarkResultList}" var="r">
									<tr>
										<td>
												<c:out value="${r.remark}" />
											</td>
										<td><c:out value="${r.createBy}" /></td>
										<td><fmt:formatDate
												pattern="yyyy/MM/dd HH:mm:ss"
												value="${r.createDate}" /></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			
			<br/>
			<!-- Customer's Information table -->

			<table width="100%" border="0" cellspacing="0" class="contenttext">

				<tr>
					<td colspan="4" class="table_title">Customer's Information</td>
				</tr>
				<tr>
				<td valign="top" bgcolor="#FFFFFF">
				
				
				<table width="100%" border="0" cellspacing="1">
				
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td><b>Title</b></td>
					<td>${contactInfo.title }</td>
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
					<td colspan="2">${contactInfo.emailAddr }</td>
				</tr>
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
				</tr>
				
				<!-- ============== Delivery Info ======================== -->
				<c:if test="${(not empty  deliveryInfo) and (orderMob.orderSubType ne '2')}">
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td><b>Delivery Date</b></td>
					<td><fmt:formatDate value="${deliveryInfo.deliveryDate }" pattern="dd/MM/yyyy"/></td>
					<td><b>Actual Delivery Date</b></td>
					<td><fmt:formatDate value="${deliveryInfo.actualDeliveryDate }" pattern="dd/MM/yyyy"/></td>
				</tr>
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td><b>Delivery Time Slot</b></td>
					<td>${deliveryInfo.deliveryTimeSlotDesc }</td>
				</tr>
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
				</tr>
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
					<td>${deliveryInfo.buildNo }</td>
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
	
				<tr class="contenttext" id="address">
					<td width="6%">&nbsp;</td>
					<td><b>Section</b></td>
					<td>${deliveryInfo.sectDesc }</td>
					<td><b>District</b></td>
					<td>${deliveryInfo.distDesc }</td>
					<td><b>Area</b></td>
					<td>${deliveryInfo.areaDesc }</td>
				</tr>
				<c:if test="${not empty contactInfo.clubMemId}">
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td><b>Club Member Id</b></td>
					<td>${contactInfo.clubMemId}</td>
				</tr>
				</c:if>
				</c:if>										
				<!-- ============== Delivery Info ======================== -->
				
			</table>

			<br/>

<!-- Payment Method -->


			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">Payment Method <a href="#" onClick="paymentTrxn();return false;" class="nextbutton3" style="margin-left: 150px">Payment Transaction</a></td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1">
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>Payment Method </b></td>
									<td class="contenttext_blk">Credit Card</td>
									<td><b>Card Holder's Name</b></td>
									<td class="contenttext_blk">${payment.creditCardHolderName}</td>
									
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Credit Card Number</b></td>
									<td class="contenttext_blk">${payment.creditCardNum}</td>
									<td><b>Expire Date</b></td>
									<td class="contenttext_blk">${payment.creditExpiryMonth}
									/ ${payment.creditExpiryYear}</td>									
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Upfront amount</b></td>
									<td class="contenttext_blk">$ <fmt:formatNumber value="${upfrontAmount}" pattern="#,###.####" /></td>
								</tr>	
								<tr>
									<td>&nbsp;</td>
									<td><b>Paid</b></td>
									<td class="contenttext_blk">$ <fmt:formatNumber value="${order.paidAmt}" pattern="#,###.####" /></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td colspan="2"><hr/></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>O/S Balance</b></td>
									<td class="contenttext_blk">
										$ <fmt:formatNumber value="${upfrontAmount - order.paidAmt}" pattern="#,###.####" />
									</td>
								</tr>
					</table>
					</td>
				</tr>
			</table>
			
			<br/>
			
<!--  Subscribed Item  -->			

			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">
						Subscribed Item
						<a href="#" onClick="javascript:void checkStockQty('${param.orderId}'); return false;" class="nextbutton3" style="margin-left: 150px">Check Current Stock Qty</a>
					</td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1">
						<tr>
							<td width="6%">&nbsp;</td>
							<td><b>Item</b></td>
							<td><b>Upfront Amount</b></td>
							<td><b>Item Code</b></td>
							<td><b>Serial #</b></td>
							<td><b>Charge Class</b></td>
							<td><b>Promo Code</b></td>
							<td><b>Promo Code Mobile Num</b></td>
							<td><b>DOA Old Item Code</b></td>
							<td><b>DOA Old Serial #</b></td>							
						</tr>
						<c:forEach var="item" items="${subscribedItems }">
						<tr>
							<td width="6%">&nbsp;</td>
							<td>${item.itemDesc } ${item.itemType eq 'SVAS' ? '&#x27a9;' : '' }</td>
							<td>$ <fmt:formatNumber value="${0+item.onetimeAmt}" pattern="#,###.####" /></td>
							<td>${item.posItemCd }</td>
							<td>${item.itemSerial }</td>
							<td>${item.chargeClass }</td>
							<td>${item.promoCode }</td>
							<td>${item.promoCodeMrt }</td>
							<td>${item.doaOldItemCode }</td>
							<td>${item.doaOldItemSerial }</td>
						</tr>
							<c:if test="${item.itemType eq 'SVAS'}">
							<tr>
							<td width="6%">&nbsp;</td>
							<td colspan="6">
								<span class="kingking">MRT:${item.msisdn } Expiry Date: <fmt:formatDate pattern="dd/MM/yyyy" value="${item.expDate }"/> Pwd: ${item.password }</span>
							</td>
							</tr>
							</c:if>
						</c:forEach>
					</table>
					</td>
				</tr>
			</table>

			<br/>
<!--  PPOS SM  -->			

			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">PPOS SM</td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1">
						<tr>
							<td width="6%">&nbsp;</td>
							<td><b>POS SM#</b></td>
							<td><b>Type</b></td>
							<td><b>Settle Date</b></td>
							<td><b>Remarks</b></td>
						</tr>
						<c:forEach var="possm" items="${posSms }">
						<tr>
							<td width="6%">&nbsp;</td>
							<td>${possm.smNum }</td>
							<td>${possm.smTypeDesc }</td>
							<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${possm.createDate}" /></td>
							<td>${possm.remark }</td>
						</tr>
						</c:forEach>						
					</table>
					</td>
				</tr>
			</table>

			<br/><br/>


			<!--  remark..... -->
			<table width="100%" border="0" cellspacing="0" class="contenttext">
				<tr>
					<td class="table_title">&nbsp;</td>
				</tr>				

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1">
								<tr>
									<td width="6%">&nbsp;</td>
									<td>
<span class="contenttext_red">
Remarks:<br/><br/>
For Virtual King King service,
<ol>
<li>No cancellation & refund</li>
<li>Once order placed, sales can use “Resend Email” function to send related acknowledgement to user (EXCEPT email with password notification)</li>
<li>For password notification, please use “Forget password” function of prepaid portal. SB will not keep the updated password after sales.</li>
</ol>
</span>
										
									</td>
								</tr>


					</table>
					</td>
				</tr>
			</table>

<!--  ACTIONS BUTTONS  -->

			<!--  SALES ACTIONS -->
			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">Sales / F&S Action</td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1">
								<tr>
									<td width="6%">&nbsp;</td>
									<td><button id="updateContactDeliveryBtn" type="button" onclick="javascript:void showUpdateContact();" >Update Contact / Delivery Info</button></td>
									<td><button id="doaRequestBtn" type="button" onclick="javascript:void showDOARequest();">DOA Request</button></td>
									<td><button id="requestCancelOrderBtn" type="button" onclick="javascript:void requestCancelOrder();" >Request Cancel Order</button></td>
								</tr>


					</table>
					</td>
				</tr>
			</table>

			<!--  F&S ACTIONS -->
			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">F&S Action</td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1">
								<tr>
									<td width="6%">&nbsp;</td>
									<td><button id="updatePosSmBtn" type="button" onclick="javascript:void insertPosSM();">Input POS SM</button></td>
									<td><button id="onsiteDoaBtn" type="button" onclick="javascript:void showOnsiteDOA();">Onsite DOA</button></td>
									<td><button id="doaBtn" type="button" onclick="javascript:void showDOA();">DOA</button></td>
								</tr>


					</table>
					</td>
				</tr>
			</table>
			

			


				
			
			<!--content--></td>
		</tr>
	</table>
 <!-- end table -->



						<!--content-->
						<!-- end table -->
					


					<input type="hidden" name="currentView" value="confirmation"/>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>