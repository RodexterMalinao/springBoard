<%@ include file="/WEB-INF/jsp/header.jsp"%>

<style type="text/css">
.forceCancelled { background-color: rgb(255, 224, 224); }
</style>

<h3 style="margin-top:0">Processed Fallout Order(<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${cancelFalloutDate}" />)</h3>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
<tr class="contenttextgreen">
	<th class="table_title">Order ID</th>
	<th class="table_title">OCID</th>
	<th class="table_title">Order Status</th>
	<th class="table_title">Check Point</th>
	<th class="table_title">Reason Code</th>
	<th class="table_title">Max Fallout Date</th>
	<th class="table_title">Fallout Date Diff</th>
	<th class="table_title">Hottest Model</th>
	<th class="table_title">Delivery Fail Count</th>
	<th class="table_title">Force Cancelled</th>
</tr>
</thead>
<tbody>
<c:choose>
<c:when test="${empty cancelFalloutList}">
<tr>
	<td colspan="10">No data</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${cancelFalloutList}" var="r">
<tr<c:if test="${r.forceCancelled}"> class="forceCancelled"</c:if>>
	<td><c:out value="${r.orderId}"/></td>
	<td><c:out value="${r.ocid}"/></td>
	<td><c:out value="${r.orderStatus}"/></td>
	<td><c:out value="${r.checkPoint}"/></td>
	<td><c:out value="${r.reasonCd}"/></td>
	<td><fmt:formatDate pattern="yyyy/MM/dd" value="${r.maxFalloutDate}"/></td>
	<td><c:out value="${r.falloutDateDiff}" /></td>
	<td><c:if test="${r.hottestModel}">Y</c:if></td>
	<td><c:out value="${r.deliveryFailCount}"/></td>
	<td><c:if test="${r.forceCancelled}">Y(<c:out value="${r.forceCancelCode}"/>)</c:if></td>
</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</tbody>
</table>

<h3>Processed Draft Order(<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${cancelDraftDate}" />)</h3>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
<tr class="contenttextgreen">
	<th class="table_title">Order ID</th>
	<th class="table_title">OCID</th>
	<th class="table_title">Order Status</th>
	<th class="table_title">Check Point</th>
	<th class="table_title">Reason Code</th>
	<th class="table_title">Force Cancelled</th>
	<th class="table_title">Last Update Date</th>
	<th class="table_title">Last Update Date Diff</th>
</tr>
</thead>
<tbody>
<c:choose>
<c:when test="${empty cancelDraftList}">
<tr>
	<td colspan="8">No data</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${cancelDraftList}" var="r">
<tr<c:if test="${r.forceCancelled}"> class="forceCancelled"</c:if>>
	<td><c:out value="${r.orderId}"/></td>
	<td><c:out value="${r.ocid}"/></td>
	<td><c:out value="${r.orderStatus}"/></td>
	<td><c:out value="${r.checkPoint}"/></td>
	<td><c:out value="${r.reasonCd}"/></td>
	<td><c:if test="${r.forceCancelled}">Y(<c:out value="${r.forceCancelCode}"/>)</c:if></td>
	<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${r.lastUpdateDate}"/></td>
	<td><c:out value="${r.lastUpdateDateDiff}"/></td>
</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</tbody>
</table>

<h3>Processed Preorder Order(<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${cancelPreorderDate}" />)</h3>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
<tr class="contenttextgreen">
	<th class="table_title">Order ID</th>
	<th class="table_title">OCID</th>
	<th class="table_title">Order Status</th>
	<th class="table_title">Check Point</th>
	<th class="table_title">Reason Code</th>
	<th class="table_title">Channel</th>
	<th class="table_title">Force Cancelled</th>
	<th class="table_title">App Date</th>
	<th class="table_title">App Date Hour Diff</th>
</tr>
</thead>
<tbody>
<c:choose>
<c:when test="${empty cancelPreorderList}">
<tr>
	<td colspan="9">No data</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${cancelPreorderList}" var="r">
<tr<c:if test="${r.forceCancelled}"> class="forceCancelled"</c:if>>
	<td><c:out value="${r.orderId}"/></td>
	<td><c:out value="${r.ocid}"/></td>
	<td><c:out value="${r.orderStatus}"/></td>
	<td><c:out value="${r.checkPoint}"/></td>
	<td><c:out value="${r.reasonCd}"/></td>
	<td><c:out value="${r.shopCd}"/></td>
	<td><c:if test="${r.forceCancelled}">Y(<c:out value="${r.forceCancelCode}"/>)</c:if></td>
	<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${r.appDate}"/></td>
	<td><c:out value="${r.appDateHrDiff}"/></td>
</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</tbody>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>