<%@ include file="/WEB-INF/jsp/header.jsp"%>

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
		 if ($.trim($("input[name=contactName]").val()).length == 0) {
			$(".error_contactName").show();
			errorFound = true;
		}
		if ($.trim($("input[name=contactPhone]").val()).length == 0) {
			$(".error_contactPhone").show();
			errorFound = true;
		} 
		/*
		if ($.trim($("input[name=contactEmail]").val()).length == 0) {
			$(".error_contactEmail").show();
			errorFound = true;
		}*/
		
		if ($("input[name=callTypeCd]:checked").length == 0) {
			$(".error_callTypeCd").show();
			errorFound = true;
		}
		if ($("select[name=nature]")[0].selectedIndex == 0) {
			$(".error_nature").show();
			errorFound = true;
		}
		if ($("select[name=result")[0].selectedIndex == 0) {
			$(".error_result").show();
			errorFound = true;
		}
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
		window.location.href = "<c:url value="mobdsfalloutrecordcreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value="${param.caseNo}"/></c:url>";		 
	});
});
</script>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:120px"></colgroup>
<colgroup></colgroup>

<tr class="contenttextgreen">
<td class="table_title" colspan="2">Order Information (Order ID: <c:out value="${param.orderId}"/>)</td>
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

<form:form action="mobdsfalloutlogcreate.html" name="submitForm" commandName="mobDsFalloutLogCreate">
	<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:150px"></colgroup>
	<tr class="contenttextgreen">
		<c:if test="${!empty param.caseNo}">	
			<td class="table_title" colspan="2">Fallout Log Create (Fallout Record No.: <c:out value="${param.caseNo}"/>)</td>	
		</c:if>
		<%-- <c:choose>
		<c:when test="${empty param.caseNo}">
			<td class="table_title" colspan="2">Fallout Log Create (Order ID: <c:out value="${param.orderId}"/>)</td>
		</c:when>
		<c:otherwise> 
			<td class="table_title" colspan="2">Fallout Log Create (Fallout Record No: <c:out value="${param.caseNo}"/>)</td>
		</c:otherwise>
		</c:choose> --%>
	</tr>	
	<tr>
		<td>Contact person</td>
		<td>
		<form:input path="contactName" id="contactName" maxlength="40" cssStyle="width:200px" />
		<form:errors path="contactName" cssClass="error"/>
		<span class="error error_msg error_contactName" style="display:none">Contact Person is required</span>
		</td>
	</tr>
	<tr>
		<td>Contact #</td>
		<td>
		<form:input path="contactPhone" id="contactPhone" maxlength="64" cssStyle="width:200px" />
		<form:errors path="contactPhone" cssClass="error"/>
		<span class="error error_msg error_contactPhone" style="display:none">Contact Phone is required</span>
		</td>
	</tr>
	<tr>
		<td>Contact Email</td>
		<td>
		<form:input path="contactEmail" maxlength="64" cssStyle="width:200px" />
		</td>
	</tr>
	<tr>
		<td>Contact by</td>
		<td><c:out value="${user.username}" /></td>
	</tr>
	<tr>
		<td>Call Type</td>
		<td>
		<form:radiobuttons path="callTypeCd" id="callTypeCd" items="${callTypeCds}" itemLabel="codeDesc" itemValue="codeId" />
		<form:errors path="callTypeCd" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td>Relationship with Customer</td>
		<td>
		<form:select path="nature" id="nature">
			<form:option value="" label="Select" />
			<form:options items="${relWtCustLst}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		<form:errors path="nature" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td>Result</td>
		<td>
		<form:select path="result" id="result"  >
			<form:option value="" label="Select" />
			<form:options items="${results}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		<form:errors path="result" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td>Remark</td>
		<td>
		<form:textarea path="remark" rows="5" cols="80" />
		<div style="clear:both"></div>
		<form:errors path="remark" cssClass="error"/>
		<span class="error error_msg error_remark" style="display:none">Remark over 4000 characters</span>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<form:hidden path="orderId"/>
		<form:hidden path="caseNo"/>
		<input type="button" value="Quit" name="backButton" style="float:right">
		<input type="submit" value="Create" style="float:right">
		</td>
	</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>