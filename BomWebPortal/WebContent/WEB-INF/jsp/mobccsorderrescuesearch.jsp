<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<script type="text/javascript">
$(document).ready(function() {
	$("form[name=mobccsorderrescuesearch]").submit(function(e) {
		if ($.trim($("input[name=orderId]").val()).length == 0 
				&& $.trim($("input[name=incidentNo]").val()).length == 0
				&& $.trim($("input[name=msisdn]").val()).length == 0) {
			alert("Please enter Order ID or Incident No or MSISDN");
			e.preventDefault();
			return false;
		}
	});
});
</script>

<style type="text/css">
.label { display: inline-block; width: 150px }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%
String scheme = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");

%>
<c:if test="${bomsalesuser.channelCd == 'QOM' || bomsalesuser.channelCd == 'CFM'}">
<form:form method="POST" action="mobccsorderrescuesearch.html">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
	<td class="table_title">Order Rescue</td>
</tr>
<tr>
	<td>
		<span class="label">Order ID:</span><form:input path="orderId" />
		<div style="clear:both"></div>
		<span class="label">Incident No:</span><form:input path="incidentNo" />
		<div style="clear:both"></div>
		<input type="submit" value="Search" style="float:right">
		<span class="label">MSISDN:</span><form:input path="msisdn" />
	</td>
</tr>
</table>
</form:form>
<div style="clear:both;padding-top:5px"></div>
</c:if>

<table width="100%" border="1" cellspacing="0" class="contenttext resultTable" bgcolor="#FFFFFF">
<thead>
<tr class="contenttextgreen">
	<td class="table_title">Order ID</td>
	<td class="table_title">MSISDN</td>
	<td class="table_title">Delivery Centre</td>
	<td class="table_title">Courier Team</td>
	<td class="table_title">Delivery<br>Date / Time</td>
	<td class="table_title">SRD</td>
	<td class="table_title">Sales ID</td>
	<td class="table_title">Order Status</td>
	<c:if test="${bomsalesuser.channelCd == 'QOM' || bomsalesuser.channelCd == 'CFM'}">
	<td class="table_title">Create<br>Incident</td>
	</c:if>
	<td class="table_title">Incident</td>
</tr>
</thead>
<tbody>
<c:choose>
<c:when test="${resultList == null}"></c:when>
<c:when test="${empty resultList}">
<tr>
	<td colspan="<c:choose><c:when test="${bomsalesuser.channelCd == 'QOM' || bomsalesuser.channelCd == 'CFM'}">10</c:when><c:otherwise>9</c:otherwise></c:choose>">No record</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${resultList}" var="r" varStatus="status">
<c:choose>
<c:when test="${r.orderId == resultList[status.index - 1].orderId}">
<tr>
	<td colspan="<c:choose><c:when test="${bomsalesuser.channelCd == 'QOM' || bomsalesuser.channelCd == 'CFM'}">9</c:when><c:otherwise>8</c:otherwise></c:choose>"></td>
	<td>
		<c:if test="${not empty r.incidentNo}">
		<a href="<c:url value="mobccsorderrescuedetail.html"><c:param name="recordIncidentNo" value="${r.incidentNo}"/></c:url>"><c:out value="${r.incidentNo}"/></a>
		</c:if>
	</td>
</tr>
</c:when>
<c:otherwise>
<tr>
	<td>
		<sb-util:orderpreview orderId="${r.orderId}" shopCd="${r.shopCode}"  mobcosbaseurl="<%=scheme %>"/>
	<%-- 	<c:choose>
		<c:when test="${fn:startsWith(r.orderId, 'CSBS') }">
			<a target="_blank" href="sbsorderdetail.html?orderId=${r.orderId}"	title="Order Enquiry for MOB">${r.orderId}</a>
		</c:when>
		<c:otherwise>
			<a target="_blank" href="<c:url value="mobccspreview.html"><c:param name="orderId" value="${r.orderId}"/><c:param name="action" value="ENQUIRY"/></c:url>"><c:out value="${r.orderId}" /></a>
		</c:otherwise>
		</c:choose> --%>
	</td>
	<td><c:out value="${r.msisdn}"/></td>
	<td><c:out value="${r.deliveryCentreDesc}"/></td>
	<td><c:out value="${r.locationDesc}"/></td>
	<td>
		<fmt:formatDate pattern="yyyy/MM/dd" value="${r.deliveryDate}" />
		<c:if test="${not empty r.deliveryTimeFrom && not empty r.deliveryTimeTo}">
		<br>
		<c:out value="${r.deliveryTimeFrom}"/> - <c:out value="${r.deliveryTimeTo}"/>
		</c:if>
	</td>
	<td><fmt:formatDate pattern="yyyy/MM/dd" value="${r.srvReqDate}" /></td>
	<td><c:out value="${r.salesName}" /></td>
	<td>
		<c:choose>
		<c:when test="${r.orderStatus == '01'}">
			<my:code id="${r.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
		</c:when>
		<c:when test="${r.orderStatus == '02'}">
			Completed
		</c:when>
		<c:when test="${r.orderStatus == '03'}">
			Cancelling
		</c:when>
		<c:when test="${r.orderStatus == '04'}">
			Cancelled
		</c:when>
		<c:when test="${r.orderStatus == '99'}">
			<span<c:if test="${r.salesFalloutFlag == true}"> style="color:red"</c:if>>
				<my:code id="${r.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
			</span>
		</c:when>
		</c:choose>
	</td>
	<c:if test="${bomsalesuser.channelCd == 'QOM' || bomsalesuser.channelCd == 'CFM'}">
	<td>
		<c:if test="${r.orderStatus == '01' && r.checkPoint == '599'}">
		<a href="<c:url value="mobccsorderrescuecreate.html"><c:param name="recordOrderId" value="${r.orderId}"/></c:url>">Create</a>
		</c:if>
	</td>
	</c:if>
	<td>
		<c:if test="${not empty r.incidentNo}">
		<a href="<c:url value="mobccsorderrescuedetail.html"><c:param name="recordIncidentNo" value="${r.incidentNo}"/></c:url>"><c:out value="${r.incidentNo}"/></a>
		</c:if>
	</td>
</tr>
</c:otherwise>
</c:choose>
</c:forEach>
</c:otherwise>
</c:choose>
</tbody>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>