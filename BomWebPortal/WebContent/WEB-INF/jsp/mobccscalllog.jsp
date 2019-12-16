<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>

<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("input[name=createButton]").click(function() {
		window.location.href = "<c:url value="mobccscalllogcreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value="${param.caseNo}"/></c:url>";
	});
});
</script>

<c:choose>
<c:when test="${param.recordCreated == 'true'}">
<div class="contenttextgreen">Record created</div>
</c:when>
</c:choose>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:100px"></colgroup>
<colgroup></colgroup>
<colgroup style="width:200px"></colgroup>
<colgroup></colgroup>
<tr class="contenttextgreen">
<td class="table_title" colspan="4">Call Log History(Order ID: <c:out value="${param.orderId}"/>)</td>
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
</table>

<div style="clear:both;padding-top:5px"></div>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="resultListTable">
	<thead>
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
		<c:when test="${empty resultList}">
			<tr>
			<td colspan="9">No data</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach items="${resultList}" var="r">
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
	<tfoot>
		<tr>
		<td colspan="9">
		<input type="button" name="createButton" value="Create Call Log" style="float:right">
		</td>
		</tr>
	</tfoot>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>