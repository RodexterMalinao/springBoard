<!DOCTYPE html>
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
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/common.js" language="javascript"></script>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>


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


jQuery.browser = {};
(function () {
    jQuery.browser.msie = false;
    jQuery.browser.version = 0;
    if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
        jQuery.browser.msie = true;
        jQuery.browser.version = RegExp.$1;
    }
})();


function formSubmit(actionType){
	window.location ="welcome.html";
}


$(function() {
	// Datepicker
	$('#deliveryDate').datepicker({
		changeMonth : true,
		changeYear : false,
		showButtonPanel : true,
		numberOfMonths:2,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "+0D",
		maxDate : "+14D", //Y is year, M is month, D is day  
		yearRange : "0:+100" //the range shown in the year selection box
		, beforeShowDay: function(date) { 
			//generate array of selectable urgent date
			var selectableDateArray = [];
			
		
			<c:forEach items="${normalDateList}" var="items">
				selectableDateArray[selectableDateArray.length] = '<c:out value="${items}" />';
			</c:forEach>
			
			
			//disable specific date
			var m = date.getMonth(), d = date.getDate(), y = date.getFullYear();
			if($.inArray(y + '-' + (m+1) + '-' + d,selectableDateArray) > -1 ) {
					return [true];
			    }
			return [false];
		}
	});
	

	
});


function changeDeliveryTime(district, selected) {
	var appDate = "<c:out value="${appDate}" />"
	$.ajax({
		url : 'timeslotlookup.html?district=' + district + '&orderType=SBS&appDate=' + appDate,
	type : 'POST',
	dataType : 'JSON',
	timeout : 5000,
	error : function() {
		alert('Error loading Delivery Time, Please press F5 to refresh page!');
	},
	success : function(msg) {
		//console.log(msg);
		$("#deliveryTimeSlot").empty();
		var $sel = $("#deliveryTimeSlot");
		$.each(
			eval(msg),
			function(i, item) {
				
				var $option = $("<option/>").text(item.time).val(item.timeSlot);
				if (item.timeSlot==selected) {
					$option.attr("selected", true)
				}
				$sel.append($option);

				
			});
		if ($sel.children().length <= 0) {
			var $emptyoption = $("<option/>");
			$sel.append($emptyoption);
		}
	}
});
}

function syncAllDescFields() {
	if ($('.address').length > 0) {
		$('.address').addressgroup().updateAllDesc();
		
		var desc = $('#strCatCd option:selected').text();
		$('#strCatDesc').val(desc);
		
		desc = $('#deliveryTimeSlot option:selected').text();
		$('#deliveryTimeSlotDesc').val(desc);
	}
}


function showDOARequest() {
	var sURL = "<%=mobCosUrl%>doarequest.html?orderId=${param.orderId}&_al=new";
	var vArguments = {};
	var sFeatures = "dialogHeight:768px;dialogLeft:0;dialogTop:0;dialogWidth:1024px;resizable=yes;scroll=yes;status=no";
	window.showModalDialog(sURL, vArguments, sFeatures);

}
function reloadcallback() {
	window.location.reload();
}

$(document).ready(function() {
	$("#updateBtn").click(function(event) {
		event.preventDefault();
		syncAllDescFields();
		$("#contactDeliveryForm").submit();
	});
	
	$("#cancelBtn").click(function(event) {
		event.preventDefault();
		window.close();
	});
	
	$("#doarequestBtn").click(function(event) {
		event.preventDefault();
		showDOARequest();
	})

	$("#lastName, #firstName, #unitNo, #floorNo, #buildNo, #strNo, #strName").focusout(function(){
		$(this).val($(this).val().toUpperCase());
	});
	
	<c:if test="${not empty sbsContactDeliveryInfo.deliveryInfo}">
	$('#areaDesc').addClass("address-area-desc");
	$('#distDesc').addClass("address-district-desc");
	$('#sectDesc').addClass("address-section-desc");
	$('.address').addressgroup(
			'${sbsContactDeliveryInfo.deliveryInfo.areaCd}',
			'${sbsContactDeliveryInfo.deliveryInfo.distNo}',
			'${sbsContactDeliveryInfo.deliveryInfo.sectCd}');
	
	$('#addrarea').change(function() {
		 
		$("#deliveryTimeSlot").empty();
	});
	
	$('#addrdist').change(function() {
		//changeDeliveryTime($('#addrdist option:selected').text());
		changeDeliveryTime($("#addrdist").val());
	});
	
	$('#deliveryTimeSlot').change(function(){
		var desc = $('#deliveryTimeSlot option:selected').text();
		$('#deliveryTimeSlotDesc').val(desc);
	});
	
	$('#strCatCd').change(function() {
		var desc = $('#strCatCd option:selected').text();
		$('#strCatDesc').val(desc);
	});
	
	changeDeliveryTime("${sbsContactDeliveryInfo.deliveryInfo.distNo}", "${sbsContactDeliveryInfo.deliveryInfo.deliveryTimeSlot}");
	
	</c:if>
	
	
	//$('#doarequestform').load("mobccsdoarequest.html?orderId=${order.orderId}")
	
	$("#updateBtn").attr("disabled", ${empty normalDateList});

});
	
</script> 
<body > 					
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

<form:form id="contactDeliveryForm" commandName="sbsContactDeliveryInfo" method="post" action="#">


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
						<td>
							<form:select id="title" path="contactInfo.title">
								<form:options items="${titleList}"/>
							</form:select>
							<form:errors path="contactInfo.title" cssClass="error" />
						</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Family Name</b></td>
						<td>
							<form:input id="lastName" path="contactInfo.lastName" />
							<div><form:errors path="contactInfo.lastName" cssClass="error" /></div>
						</td>
						
						<td><b>Given Name</b></td>
						<td>
							<form:input id="firstName" path="contactInfo.firstName" />
							<div><form:errors path="contactInfo.firstName" cssClass="error" /></div>
						</td>						
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Contact Phone</b></td>
						<td>
							<form:input path="contactInfo.contactPhone" />
							<div><form:errors path="contactInfo.contactPhone" cssClass="error" /></div>
							
						</td>
						<td><b>Email</b></td>
						<td>
							<form:input path="contactInfo.emailAddr" />
							<div><form:errors path="contactInfo.emailAddr" cssClass="error" /></div>						
						</td>
					</tr>
				</table>
			</table>



			<!-- Delivery Information table -->
<c:if test="${(not empty sbsContactDeliveryInfo.deliveryInfo) and (sbsContactDeliveryInfo.orderSubType ne '2')}">
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
						<td>
							<form:input id="unitNo" path="deliveryInfo.unitNo"/>
							<div><form:errors path="deliveryInfo.unitNo" cssClass="error" /></div>
						</td>
						<td><b>Floor</b></td>
						<td><form:input id="floorNo" path="deliveryInfo.floorNo"/></td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Building/Estate/Lot No./House</b></td>
						<td colspan="3">
							<form:input id="buildNo" path="deliveryInfo.buildNo" cssStyle="width: 90%"/>
							<div><form:errors path="deliveryInfo.buildNo" cssClass="error" /></div>
						</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Street Number</b></td>
						<td>
							<form:input id="strNo" path="deliveryInfo.strNo"/>
							<div><form:errors path="deliveryInfo.strNo" cssClass="error" /></div>
						</td>
						<td><b>Street Name</b></td>
						<td><form:input id="strName" path="deliveryInfo.strName"/></td>						
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Street Category</b></td>
						<td>
							<form:select id="strCatCd" path="deliveryInfo.strCatCd" cssStyle="width: 180px;">
								<form:options items="${streetCatList }" itemValue="categoryCode" itemLabel="categoryDesc"/>
							</form:select>
							<form:hidden id="strCatDesc" path="deliveryInfo.strCatDesc"/>
						</td>
					</tr>

					<tr class="contenttext address">
						<td width="6%">&nbsp;</td>
						<td><b>Section</b></td>
						<td>
							<select id="addrsect" name="deliveryInfo.sectCd" class="address-section" style="width: 240px;"></select>
							<form:hidden id="sectDesc" path="deliveryInfo.sectDesc" />
						</td>
					</tr>
					<tr class="contenttext address">
						<td width="6%">&nbsp;</td>
						<td><b>District</b></td>
						<td>
							<select id="addrdist" name="deliveryInfo.distNo" class="address-district" style="width: 240px;"></select>
							<form:hidden id="distDesc" path="deliveryInfo.distDesc" />
							<div><form:errors path="deliveryInfo.distNo" cssClass="error" /></div>
						</td>						
					</tr>
					<tr class="contenttext address">
						<td width="6%">&nbsp;</td>
						<td><b>Area</b></td>
						<td>
							<select id="addrarea" name="deliveryInfo.areaCd" class="address-area" style="width: 240px;"></select>
							<form:hidden id="areaDesc" path="deliveryInfo.areaDesc" />
							<div><form:errors path="deliveryInfo.areaCd" cssClass="error" /></div>
						</td>						
					</tr>
					
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td><b>Delivery Date</b></td>
						<td>
							<form:input path="deliveryInfo.deliveryDate" id="deliveryDate" readonly="true"/>
							<div><form:errors path="deliveryInfo.deliveryDate" cssClass="error" /></div>
						</td>
						<td><b>Delivery Timeslot</b></td>
						<td>
							<select id="deliveryTimeSlot" name="deliveryInfo.deliveryTimeSlot" style="width: 180px;">
								<option value=""></option>
							</select>
							<form:hidden id="deliveryTimeSlotDesc" path="deliveryInfo.deliveryTimeSlotDesc" />
							<div><form:errors path="deliveryInfo.deliveryTimeSlot" cssClass="error" /></div>
						</td>	
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
					
					</tr>
				</table>

				
			</table>
</c:if>			
			<!-- ===========================================================  -->

			<div class="buttonmenubox_R">
				<c:if test="${(order.reasonCd eq 'N002') or (order.reasonCd eq 'N003')  }">
				<button id="doarequestBtn" type="button"> View DOA Request </button>
				</c:if>
				<button id="cancelBtn" type="button"> Cancel </button>
				<button id="updateBtn" type="button"> Update </button>
			</div>

			<br/><br/>


				
			
			<!--content--></td>
		</tr>
	</table>
	<br/><br/><br/>
 <!-- end table -->



						<!--content-->
						<!-- end table -->
					


					<input type="hidden" name="currentView" value="confirmation"/>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>