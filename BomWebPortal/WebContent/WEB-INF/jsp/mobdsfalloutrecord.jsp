<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>

<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	/* $("input[name=createFalloutCaseButton]").click(function() {
		window.location.href = "<c:url value="mobdsfalloutrecordcreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value=""/></c:url>";
	});
		 */
});

</script>

<c:choose>
	<c:when test="${param.recordCreated == 'true'}">
	<div class="contenttextgreen">Record created</div>
	</c:when>
</c:choose>


<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:120px"></colgroup>
<colgroup></colgroup>

<tr class="contenttextgreen">
<td class="table_title" colspan="2">Fallout Record History (Order ID: <c:out value="${param.orderId}"/>)</td>
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

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="falloutRecordListTable">
	<thead>
		<tr>
		<td class="table_title">Fallout Record Case No.</td>
		<td class="table_title">Staff ID</td>
		<td class="table_title">Fallout Type</td>
		<td class="table_title">Fallout Status</td>
		<td class="table_title">Fallout Category Option</td>
		<td class="table_title">Fallout Remark</td>
		<td class="table_title">Created By</td>
		<td class="table_title">Created Date</td>
		<td class="table_title">Last Updated By</td>
		<td class="table_title">Last Updated Date</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${empty falloutRecordList}">
			<tr>
			<td colspan="9">No data</td>
			</tr>
		</c:when>
		<c:otherwise>
		<c:forEach items="${falloutRecordList}" var="frl">
		<tr>
		<c:choose>
		<c:when test="${user.channelCd=='MOB' && user.areaCd=='OST'}">
		<td><a href='<c:url value="mobdsfalloutrecordcreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value="${frl.caseNo}"/></c:url>'>
		<c:out value="${frl.caseNo}" /></a></td>
		</c:when>
		<c:otherwise>
		<td>
		<c:out value="${frl.caseNo}" />
		</td>
		</c:otherwise>		
		</c:choose>
		<td><c:out value="${frl.staffId}" /></td>
		<td><c:out value="${frl.falloutType}" /></td>
		<td>
			<my:code id="${frl.falloutStatus}" codeType="DS_FALLOUT_STATUS"></my:code>
		</td>
		<td>
			<my:code id="${frl.falloutCatOpt}" codeType="DS_FALLOUT_CAT_OPT"></my:code>
		</td>		
		<td><c:out value="${frl.remark}" /></td>
		<td><c:out value="${frl.createBy}" /></td>
		<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${frl.createDate}" /></td>
		<td><c:out value="${frl.lastUpdBy}" /></td>
		<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${frl.lastUpdDate}" /></td>
		</c:forEach>
		</c:otherwise>
		</c:choose>
	</tbody>
	<tfoot>
	<tr>
	<td colspan="10">
	<c:if test="${user.channelCd=='MOB' && user.areaCd=='OST'}">
	<p align="right"><a href='<c:url value="mobdsfalloutrecordcreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value=""/></c:url>' class="nextbutton3" >Create Fallout Case</a></p>
	</c:if>
	</td>
	</tr>
	</tfoot>

	
</table>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>

