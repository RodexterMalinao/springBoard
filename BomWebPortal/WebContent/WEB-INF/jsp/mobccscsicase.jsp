<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>

<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("input[name=createButton]").click(function() {
		window.location.href = "<c:url value="mobccscsicasecreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value=""/></c:url>";
	});
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
	<colgroup style="width:200px"></colgroup>
	<colgroup></colgroup>
	<tr class="contenttextgreen">
		<td class="table_title" colspan="4">CSI Case History(Order ID: <c:out value="${param.orderId}"/>)</td>
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
</table>

<div style="clear:both;padding-top:5px"></div>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="resultListTable">
	<thead>
		<tr>
		<td class="table_title">CSI Case No.</td>
		<td class="table_title">Follow Up Type</td>
		<td class="table_title">Case Status</td>
		<td class="table_title">Contact Counter</td>
		<td class="table_title">SMS Counter</td>
		<td class="table_title">On-site Visit Ind</td>
		<td class="table_title">On-site Visit Ind Create Date</td>
		<td class="table_title">On-site Visit Result</td>
		<td class="table_title">Fallout Report Date</td>
		<td class="table_title">Next Call Schedule Date</td>
		<td class="table_title">Created By</td>
		<td class="table_title">Created Date</td>
		<td class="table_title">Last Updated Date</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${empty resultList}">
			<tr>
			<td colspan="13">No data</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach items="${resultList}" var="r">
				<tr>
				<td><a href='<c:url value="mobccscsicasecreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value="${r.caseNo}"/></c:url>'><c:out value="${r.caseNo}" /></a></td>
				<td><my:code id="${r.followUpType}" codeType="FOLLOW_UP_TYPE"></my:code></td>
				<td><my:code id="${r.caseStatus}" codeType="CSI_CASE_STATUS"></my:code></td>
				<td><c:out value="${r.contactCount}" /></td>
				<td><c:out value="${r.smsCount}" /></td>
				<td>
					<c:choose>
					<c:when test="${r.onsiteVisitInd}">
						Y
					</c:when>
					<c:otherwise> 
						N
					</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
					<c:when test="${empty r.OVICreateDate}">
						N/A
					</c:when>
					<c:otherwise> 
						<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${r.OVICreateDate}" />
					</c:otherwise>
					</c:choose>
				</td>
				<td>
				<c:out value="${r.onsiteVisitResult}" />
				</td>
				<td>
				<c:out value="${r.falloutReportDateStr}" /> <c:out value="${r.falloutReportTSlot}" />
				</td>
				<td>
				<fmt:formatDate pattern="yyyy/MM/dd" value="${r.nextCallSchDate}" /> <c:out value="${r.nextCallSchTime}" />
				</td>
				<td><c:out value="${r.createBy}" /></td>
				<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${r.createDate}" /></td>
				<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${r.lastUpdDate}" /></td>
				</tr>
			</c:forEach>
		</c:otherwise>
		</c:choose>
	</tbody>
	<tfoot>
	<tr>
	<td colspan="13">
	<c:if test="${user.channelCd=='CFM' || user.channelCd=='QOM'}">
		<input type="button" name="createButton" value="Create CSI Case" style="float:right">
	</c:if>
	</td>
	</tr>
	</tfoot>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>