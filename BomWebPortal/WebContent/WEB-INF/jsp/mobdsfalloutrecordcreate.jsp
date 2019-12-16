<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>

<style type="text/css"> 
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
<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("form[name=submitForm]").submit(function(e) {
		$(".error").hide();
		$(".error_msg").hide();
		 var errorFound = false;

		 if ($("textarea[name=remark]").val().length > 4000) {
				$(".error_remark").show();
				errorFound = true;
			} 
		 
		 
		if (errorFound) {
			e.preventDefault();
			return false;
		} 
	});
	
	
	$("input[name=backButton]").click(function() {
		window.location.href = "<c:url value="mobdsfalloutrecord.html"><c:param name="orderId" value="${param.orderId}"/></c:url>";
	});
	

	var channelCd = "<c:out value="${user.channelCd}"/>";
	var areaCd = "<c:out value="${user.areaCd}"/>";
	if (!(channelCd == 'MOB' && areaCd == 'OST')){
		$('input, select').attr('disabled', 'disabled');
		$('input[type="text"], textarea').attr('readonly','readonly');
	}
	
	
});

</script>


<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:120px"></colgroup>
	<colgroup></colgroup>
	<tr class="contenttextgreen">
		<td class="table_title" colspan="4">Order Information (Order ID: <c:out value="${param.orderId}"/>)</td>
	</tr>
	<tr>
		<td>First Name:</td>
		<td><c:out value="${customerProfile.custFirstName}" /></td>
	</tr>
	
	<tr>
		<td>Last Name:</td>
		<td><c:out value="${customerProfile.custLastName}" /></td>
	</tr>
	
	<c:if test="${ !empty customerProfile.companyName }">
	<tr>
		<td>Company Name:</td>
		<td>
		<c:out value="${customerProfile.companyName}" />
		</td>
	</tr>
	</c:if>
	
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
	</tr>
	
	<tr>
		<td>Contact #:</td>
		<td><c:out value="${customerProfile.contactPhone}" /></td>
	</tr>
	
	
	<c:if test="${!empty customerProfile.otherContactPhone }">
	<tr>
		<td>Other Contact #:</td>
		<td><c:out value="${customerProfile.otherContactPhone}" /></td>
	</tr>
	</c:if>
	
</table>

<div style="clear:both;padding-top:5px"></div>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:120px"></colgroup>
	<colgroup></colgroup>
	<tr>
	<td>Current Order Status:</td>
	<td>
		<c:if
		test="${fn:startsWith(orderDTO.orderId, 'D') || fn:length(orderDTO.orderId) != 11}">
		<c:choose>
		<c:when test='${orderDTO.orderStatus == "INITIAL"}'>
			Initial
		</c:when>
		<c:when test='${orderDTO.orderStatus == "REVIEWING"}'>
			Reviewing
		</c:when>
		<c:when test='${orderDTO.orderStatus == "SIGNOFF"}'>
			Signoff
		</c:when>
		<c:when test='${orderDTO.orderStatus == "COMPLETED"}'>
			Completed
		</c:when>
		<c:when test='${orderDTO.orderStatus == "CANCELLED"}'>
			Cancelled
		</c:when>
		<c:when test='${orderDTO.orderStatus == "REJECTED"}'>
			Rejected
		</c:when>
		<c:when test='${orderDTO.orderStatus == "FALLOUT"}'>
			Fallout
		</c:when>
		<c:otherwise>${orderDTO.orderStatus}</c:otherwise>
		</c:choose>
	</c:if>
	</td>
	</tr>
</table>
<div style="clear:both;padding-top:5px"></div>

<form:form action="mobdsfalloutrecordcreate.html" name="submitForm" commandName="mobDsFalloutRecordCreate">


<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:150px"></colgroup>
	<tr class="contenttextgreen">
		<td class="table_title" colspan="2">Fallout Record Create / Update</td>
	</tr>
	<tr>
		<td>Case No.</td>
		<td>
		<c:choose>
		<c:when test="${empty mobDsFalloutRecordCreate.caseNo}">
			#New Case#
		</c:when>
		<c:otherwise> 
			<c:out value="${mobDsFalloutRecordCreate.caseNo}" />
		</c:otherwise>
		</c:choose>
		</td>
	</tr>
	
	<tr>
		<td>Staff Id</td>
		<td>
		<c:out value="${mobDsFalloutRecordCreate.staffId}" />	
		</td>
	</tr>
	<tr>
		<td>Fallout Type</td>
		<td>
		<form:select path="falloutType">
			<form:option value="Missing document" label="Missing document" />
			<form:option value="Fallout" label="Fallout" />
		</form:select>
		</td>
	</tr>
	<tr>
		<td>Fallout Status</td>
		<td>
		<form:select path="falloutStatus">
			<form:options items="${falloutStatusList}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		</td>
	</tr>
	<tr>
		<td>Fallout Category Option</td>
		<td>
		<form:select path="falloutCatOpt">
			<form:options items="${falloutCatOptList}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		</td>
	</tr>
	
	<tr>
		<td>Created by</td>
		<td>
		<c:out value="${mobDsFalloutRecordCreate.createBy}" />
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
		<form:hidden path="createBy"/>
		<input type="button" value="Quit" name="backButton" style="float:right">
		<c:if test="${user.channelCd=='MOB' && user.areaCd=='OST'}">
			<input type="submit" value="Save" style="float:right">
		</c:if>
		</td>
	</tr>
</table>
</form:form>


<div style="clear:both;padding-top:5px"></div>


<c:if test='${not empty mobDsFalloutRecordCreate.caseNo}'>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="resultListTable">
	<thead>
		<tr class="contenttextgreen">
			<td class="table_title" colspan="10">Fallout Log</td>
		</tr>
		<tr>
		<td class="table_title">Sequence No.</td>
		<td class="table_title">Contact Person</td>
		<td class="table_title">Contact Phone</td>
		<td class="table_title">Contact Email</td>
		<td class="table_title">Contact By</td>
		<td class="table_title">Call Type</td>
		<td class="table_title">Relationship with Customer </td>
		<td class="table_title">Result</td>
		<td class="table_title">Remark</td>
		<td class="table_title">Create Date</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${empty resultFalloutLogList}">
			<tr>
			<td colspan="10">No data</td>
			</tr>
		</c:when>
		<c:otherwise> 
			<c:forEach items="${resultFalloutLogList}" var="rfll">
				<tr>
				<td><c:out value="${rfll.seqNo}" /></td>
				<td><c:out value="${rfll.contactName}" /></td>
				<td><c:out value="${rfll.contactPhone}" /></td>
				<td><c:out value="${rfll.contactEmail}" /></td>
				<td><c:out value="${rfll.contactBy}" /></td>
				<td>
				<my:code id="${rfll.callTypeCd}" codeType="CALL_TYPE"></my:code>
				</td>
				<td>
				<my:code id="${rfll.nature}" codeType="REL_WT_CUST"></my:code>
				</td>
				<td>
				<my:code id="${rfll.result}" codeType="CALL_RESULT_TYPE"></my:code>
				</td>
				<td><c:out value="${rfll.remark}" /></td>
				<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${rfll.createDate}" /></td>
				</tr>
			</c:forEach> 
		</c:otherwise>
		</c:choose>
	</tbody>
</table>
<c:if test="${user.channelCd=='MOB' && user.areaCd=='OST'}">
<p align="right"><a href='<c:url value="mobdsfalloutlogcreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value="${param.caseNo}"/></c:url>' class="nextbutton3" >Create Fallout Log</a></p>
</c:if>


</c:if>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>