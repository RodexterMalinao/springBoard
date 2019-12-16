<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<style type="text/css"> 
	/* css for timepicker */
	.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
	.ui-timepicker-div dl { text-align: left; }
	.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
	.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
	.ui-timepicker-div td { font-size: 90%; }
	.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
	
	/*
	 * jQuery UI Slider 1.8.17
	 *
	 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
	 * Dual licensed under the MIT or GPL Version 2 licenses.
	 * http://jquery.org/license
	 *
	 * http://docs.jquery.com/UI/Slider#theming
	 */

	.ui-slider { position: relative; text-align: left; }
	.ui-slider .ui-slider-handle { position: absolute; z-index: 2; width: 1.2em; height: 1.2em; cursor: default; }
	.ui-slider .ui-slider-range { position: absolute; z-index: 1; font-size: .7em; display: block; border: 0; background-position: 0 0; }
	 
	.ui-slider-horizontal { height: .8em; }
	.ui-slider-horizontal .ui-slider-handle { top: -.3em; margin-left: -.6em; }
	.ui-slider-horizontal .ui-slider-range { top: 0; height: 100%; }
	.ui-slider-horizontal .ui-slider-range-min { left: 0; }
	.ui-slider-horizontal .ui-slider-range-max { right: 0; }
	
	.ui-slider-vertical { width: .8em; height: 100px; }
	.ui-slider-vertical .ui-slider-handle { left: -.3em; margin-left: 0; margin-bottom: -.6em; }
	.ui-slider-vertical .ui-slider-range { left: 0; width: 100%; }
	.ui-slider-vertical .ui-slider-range-min { bottom: 0; }
	.ui-slider-vertical .ui-slider-range-max { top: 0; }
	
</style>

<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.ui.slider.js"></script>
<script src="js/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	
	
	

	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("form[name=submitForm]").submit(function(e) {
		$(".error").hide();
		$(".error_msg").hide();
		var errorFound = false;
// 		if ($.trim($("input[name=nextCallSchDateStr]").val()).length == 0) {
// 			$(".error_nextCallSchDate").show();
// 			errorFound = true;
// 		}
//		if ($.trim($("input[name=falloutReportDateStr]").val()).length == 0) {
//			$(".error_falloutReportDate").show();
//			errorFound = true;
//		}
		if (errorFound) {
			e.preventDefault();
			return false;
		}
	});

	$("input[name=backButton]").click(function() {
		window.location.href = "<c:url value="mobccscsicase.html"><c:param name="orderId" value="${param.orderId}"/></c:url>";
	});
	
	$("input[name=clearButton]").click(function() {
		$("input[name=nextCallSchDateStr]").val("");
		$("select#nextCallSchTime").val("");
// 		$("input[name=nextCallSchTime] option[@selected]").val("");
// 		$("input[name=nextCallSchTime] option[@selected]").text("---");	
	});
	
	$('#callDatepicker').datepicker({
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate: 0,
		maxDate: "+1Y", //Y is year, M is month, D is day  
		yearRange: "0:+1" //the range shown in the year selection box
	});
	
	$('#falloutDatepicker').datepicker({
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate: "-1Y",
		maxDate: "+100Y", //Y is year, M is month, D is day
		yearRange: "-8:+2" //the range shown in the year selection box
	});
	
	<c:if test="${!(user.channelCd=='CFM' || user.channelCd=='QOM')}">
	$('input, select').attr('disabled', 'disabled');
	$('input[type="text"], textarea').attr('readonly','readonly');
	</c:if>	
});
</script>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:120px"></colgroup>
	<colgroup></colgroup>
	<colgroup style="width:200px"></colgroup>
	<colgroup></colgroup>
	<tr class="contenttextgreen">
		<td class="table_title" colspan="4">Order Information (Order ID: <c:out value="${param.orderId}"/>)</td>
	</tr>
	<tr>
		<td>First Name:</td>
		<td><c:out value="${customerProfile.custFirstName}" /></td>
		<td>Contact #:</td>
		<td><c:out value="${customerProfile.contactPhone}" /></td>
	</tr>
	<tr>
		<td>Last Name:</td>
		<td><c:out value="${customerProfile.custLastName}" /></td>
		<td>Other Contact #:</td>
		<td><c:out value="${customerProfile.otherContactPhone}" /></td>
	</tr>
	<tr>
		<td>Company Name:</td>
		<td>
		<c:out value="${customerProfile.companyName}" />
		</td>
		<td>Delivery 3rd Party Contact #:</td>
		<td>
		<c:set var="delivery3rdPartyContactPhone" value=""/>
			<c:forEach items="${contact}" var="c">
				<c:if test="${c.contactType == '3C'}">
				<c:set var="delivery3rdPartyContactPhone">${delivery3rdPartyContactPhone}<c:if test="${fn:length(delivery3rdPartyContactPhone) > 0}"><br></c:if>${c.contactPhone}</c:set>
				</c:if>
			</c:forEach>
		<c:out value="${delivery3rdPartyContactPhone}" />
		</td>
	</tr>
	<tr>
	<td>Address:</td>
	<td>
	<c:set var="displayDeliveryAddress" value=""/>
		<c:if test="${! empty customerProfile.flat}"><c:set var="displayDeliveryAddress">Flat ${customerProfile.flat}</c:set></c:if>
		<c:if test="${! empty customerProfile.floor}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>Floor ${customerProfile.floor}</c:set></c:if>
		<c:if test="${! empty customerProfile.lotNum}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${customerProfile.lotNum}</c:set></c:if>
		<c:if test="${! empty customerProfile.buildingName}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${customerProfile.buildingName}</c:set></c:if>
		<c:if test="${! empty customerProfile.streetNum}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${customerProfile.streetNum}</c:set></c:if>
		<c:if test="${! empty customerProfile.streetName}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${customerProfile.streetName}</c:set></c:if>
		<c:if test="${! empty customerProfile.streetCatgDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${customerProfile.streetCatgDesc}</c:set></c:if>
		<c:if test="${! empty customerProfile.sectionCode}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${customerProfile.sectionCode}</c:set></c:if>
		<c:if test="${! empty customerProfile.districtDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${customerProfile.districtDesc}</c:set></c:if>
		<c:if test="${! empty customerProfile.areaDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${customerProfile.areaDesc}</c:set></c:if>
	<c:out value="${displayDeliveryAddress}"/>
	</td>
	<td>
	&nbsp;
	</td>
	<td>
	&nbsp;
	</td>
	</tr>
</table>

<div style="clear:both;padding-top:5px"></div>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:120px"></colgroup>
	<colgroup></colgroup>
	<tr>
	<td>Current Order Status:</td>
	<td>
		<c:if test='${orderDTO.orderStatus == "99"}'>
		<my:code id="${orderDTO.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
		</c:if>
		<c:if test='${orderDTO.orderStatus == "01"}'>
			<my:code id="${orderDTO.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
		</c:if>
		<c:if test='${orderDTO.orderStatus == "02"}'>
			Completed
		</c:if>
		<c:if test='${orderDTO.orderStatus == "03"}'>
			Cancelling
		</c:if>
		<c:if test='${orderDTO.orderStatus == "04"}'>
			Cancelled
		</c:if>
	</td>
	</tr>
	<tr>
	<td>Delivery Date:</td>
	<td>
	<fmt:formatDate pattern="dd/MM/yyyy" value="${deliveryUI.deliveryDate}"/> 
	</td>
	</tr>
</table>

<div style="clear:both;padding-top:5px"></div>

<form:form action="mobccscsicasecreate.html" name="submitForm" commandName="mobccscsicasecreate">


<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:150px"></colgroup>
	<tr class="contenttextgreen">
		<td class="table_title" colspan="2">CSI Case Create / Update</td>
	</tr>
	<tr>
		<td>Case No.</td>
		<td>
		<c:choose>
		<c:when test="${empty mobccscsicasecreate.caseNo}">
			#New Case#
		</c:when>
		<c:otherwise> 
			<c:out value="${mobccscsicasecreate.caseNo}" />
		</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr>
		<td>Follow Up Type</td>
		<td>
		<c:choose>
		<c:when test="${mobccscsicasecreate.followUpTypeisLock}">
		<form:select path="followUpType" disabled="true">
			<form:options items="${followUpTypeLst}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		</c:when>
		<c:otherwise> 
		<form:select path="followUpType">
			<form:options items="${followUpTypeLst}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr>
		<td>Case Status</td>
		<td>
		<form:select path="caseStatus" >
			<form:options items="${csiCaseStatusLst}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		</td>
	</tr>
	<tr>
		<td>Next Call Schedule Date & Time</td>
		<td>
		<form:input path="nextCallSchDateStr" maxlength="10" id="callDatepicker" readonly="true" />
		<form:errors path="nextCallSchDateStr" cssClass="error"/>
		
		&nbsp;
		
		<form:select id="nextCallSchTime" path="nextCallSchTime">
			<form:options items="${nextCallSchTimeLst}" />
		</form:select>
		<form:errors path="nextCallSchTime" cssClass="error"/>
		
		&nbsp;
		
		<input type="button" value="Clear" name="clearButton" />
		
		</td>
	</tr>
	<tr>
		<td>Fallout Report Date</td>
		<td>
		<form:input path="falloutReportDateStr" maxlength="10" id="falloutDatepicker" readonly="true" />
		<form:errors path="falloutReportDateStr" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td>Fallout Report Time Slot</td>
		<td>
		<form:select path="falloutReportTSlot">
			<form:options items="${falloutReportTSlotLst}" />
		</form:select>
		<form:errors path="falloutReportTSlot" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td>On-site Visit Indicator</td>
		<td>
		<c:choose>
		<c:when test="${(mobccscsicasecreate.onsiteVisitInd == true) && (empty mobccscsicasecreate.onsiteVisitResult)}">
			<form:checkbox path="onsiteVisitInd" />
		</c:when>
		<c:otherwise> 
			<form:checkbox path="onsiteVisitInd" disabled="true" />
		</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr>
		<td>On-site Visit Indicator Create Date</td>
		<td>
		<c:choose>
		<c:when test="${empty mobccscsicasecreate.OVICreateDate}">
			N/A
		</c:when>
		<c:otherwise> 
			<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${mobccscsicasecreate.OVICreateDate}" />
		</c:otherwise>
		</c:choose>
	</td>
	</tr>
	<tr>
		<td>On-site Visit Result</td>
		<td>
		<c:choose>
		<c:when test="${empty mobccscsicasecreate.onsiteVisitResult}">
			N/A
		</c:when>
		<c:otherwise> 
			<c:out value="${mobccscsicasecreate.onsiteVisitResult}" />
		</c:otherwise>
		</c:choose>
	</td>
	</tr>
	<tr>
		<td>SMS Counter</td>
		<td>
		<c:choose>
		<c:when test="${empty mobccscsicasecreate.smsCount}">
			0
		</c:when>
		<c:otherwise> 
			<c:out value="${mobccscsicasecreate.smsCount}" />
		</c:otherwise>
		</c:choose>
	</td>
	</tr>
	<tr>
		<td>Contact Counter</td>
		<td>
		<c:choose>
		<c:when test="${empty mobccscsicasecreate.contactCount}">
			0
		</c:when>
		<c:otherwise> 
			<c:out value="${mobccscsicasecreate.contactCount}" />
		</c:otherwise>
		</c:choose>
</td>
	</tr>
	<tr>
		<td>Created by</td>
		<td>
		<c:out value="${mobccscsicasecreate.createBy}" />
		</td>
	</tr>
	<tr>
		<td>Remark</td>
		<td>
		<form:textarea path="remark" rows="5" cols="80" />
		<div style="clear:both"></div>
		<span class="error error_msg error_remark" style="display:none">Remark over 4000 characters</span>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<form:hidden path="orderId"/>
		<form:hidden path="caseNo"/>
		<form:hidden path="onsiteVisitResult"/>
		<form:hidden path="smsCount"/>
		<form:hidden path="contactCount"/>
		<form:hidden path="createBy"/>
		<input type="button" value="Quit" name="backButton" style="float:right">
		<c:if test="${user.channelCd=='CFM' || user.channelCd=='QOM'}">
			<input type="submit" value="Save" style="float:right">
		</c:if>
		</td>
	</tr>
</table>
</form:form>

<div style="clear:both;padding-top:5px"></div>


<c:if test='${not empty mobccscsicasecreate.caseNo}'>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="resultListTable">
	<thead>
		<tr class="contenttextgreen">
			<td class="table_title" colspan="9">Call Log</td>
		</tr>
		<tr>
		<td class="table_title">Contact Name</td>
		<td class="table_title">Contact Phone</td>
		<td class="table_title">Contact Email</td>
		<td class="table_title">Contact By</td>
		<td class="table_title">Call Type</td>
		<td class="table_title">Relationship of Customer</td>
		<td class="table_title">Result</td>
		<td class="table_title">Remark</td>
		<td class="table_title">Create Date</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${empty resultCallLogList}">
			<tr>
			<td colspan="9">No data</td>
			</tr>
		</c:when>
		<c:otherwise> 
			<c:forEach items="${resultCallLogList}" var="r">
				<tr>
				<td><c:out value="${r.contactName}" /></td>
				<td><c:out value="${r.contactPhone}" /></td>
				<td><c:out value="${r.contactEmail}" /></td>
				<td><c:out value="${r.contactBy}" /></td>
				<td>
				<my:code id="${r.callTypeCd}" codeType="CALL_TYPE"></my:code>
				</td>
				<td>
				<my:code id="${r.relWtCust}" codeType="REL_WT_CUST"></my:code>
				</td>
				<td>
				<my:code id="${r.resultCd}" codeType="CALL_RESULT_TYPE"></my:code>
				</td>
				<td><c:out value="${r.remark}" /></td>
				<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${r.createDate}" /></td>
				</tr>
			</c:forEach> 
		</c:otherwise>
		</c:choose>
	</tbody>
</table>
<c:if test="${user.channelCd=='CFM' || user.channelCd=='QOM'}">
<p align="right"><a href='<c:url value="mobccscalllogcreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value="${param.caseNo}"/></c:url>' class="nextbutton3" >Create Call Log</a></p>
</c:if>
<div style="clear:both;padding-top:5px"></div>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="resultListTable">
	<thead>
		<tr class="contenttextgreen">
			<td class="table_title" colspan="9">SMS Log</td>
		</tr>
		<tr>
		<td class="table_title">Send Date</td>
		<td class="table_title">Send Time</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${empty resultSmsLogList}">
			<tr>
			<td colspan="2">No data</td>
			</tr>
		</c:when>
		<c:otherwise> 
			<c:forEach items="${resultSmsLogList}" var="r">
				<tr>
				<td><fmt:formatDate pattern="yyyy/MM/dd" value="${r.smsRecordDate}" /></td>
				<td><fmt:formatDate pattern="HH:mm:ss" value="${r.smsRecordDate}" /></td>
				</tr>
			</c:forEach> 
		</c:otherwise>
		</c:choose>
	</tbody>
</table>
<c:if test="${user.channelCd=='CFM' || user.channelCd=='QOM'}">
<p align="right"><a href='<c:url value="mobccscsismslogcreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value="${param.caseNo}"/></c:url>' class="nextbutton3" >Create SMS Log</a></p>
</c:if>




</c:if>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>