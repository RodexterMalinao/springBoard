<%@ include file="/WEB-INF/jsp/header.jsp"%>
<style type="text/css">
.textInfo {line-height: 20px}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("input[name=quitButton]").click(function() {
		window.location.href = "mobccsmrtadmin.html";
	});
});
</script>

<c:choose>
<c:when test="${param.reserveSuccess == 'true'}">
<div class="contenttextgreen">Success to reserve</div>
</c:when>
<c:when test="${param.approveSuccess == 'true'}">
<div class="contenttextgreen">Success to approve</div>
</c:when>
</c:choose>
<div style="clear:both"></div>

<form:form method="POST" name="mobccsmrtadmindetail">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
<td class="table_title" colspan="4">Golden MRT Administration</td>
</tr>
<c:choose>
<c:when test="${detail == null}">
<tr>
<td colspan="4">No record for rowId: ${rowId}</td>
</tr>
</c:when>
<c:otherwise>
<tr>
<td>Order ID</td><td colspan="3"><span class="textInfo"><c:out value="${detail.orderId}" /></span></td>
</tr>
<tr>
<td>Request Date</td><td colspan="3"><span class="textInfo"><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${detail.requestDate}"/></span></td>
</tr>
<tr>
<td>Customer Name</td><td colspan="3"><span class="textInfo"><c:out value="${detail.title} ${detail.firstName} ${detail.lastName}" /></span></td>
</tr>
<tr>
<td>Salesman Name</td><td><span class="textInfo"><c:out value="${detail.salesName}" /></span></td>
<td>Status</td>
<td>
<span class="textInfo">
<c:choose>
<c:when test="${detail.requestStatus == '01'}">REQUESTED</c:when>
<c:when test="${detail.requestStatus == '02'}">RESERVED</c:when>
<c:when test="${detail.requestStatus == '03'}">APPROVED</c:when>
<c:when test="${detail.requestStatus == '04'}">COMPLETED</c:when>
</c:choose>
</span>
</td>
</tr>
<tr>
<td>Tariff Plan</td><td><span class="textInfo"><c:out value="\$${detail.monthlyCharge}" /></span></td>
<td>Golden MRT Grade</td><td>
<form:select path="msisdnlvl" disabled="${detail.requestStatus != '01'}">
	<form:option value="" label=" ---- Please Select ---- " />
	<form:options items="${msisdnlvlList}" />
</form:select>
<div style="clear:both"></div>
<form:errors path="msisdnlvl" cssClass="error"/>
</td>
</tr>
<tr>
<td>Contract Period</td><td><span class="textInfo"><c:out value="${detail.contractPeriod}" /></span></td>
<td>Golden MRT</td><td>
<form:input path="msisdn" maxlength="16" cssStyle="width:250px" readonly="${detail.requestStatus != '01'}"/>
<div style="clear:both"></div>
<form:errors path="msisdn" cssClass="error"/>
</td>
</tr>
<tr>
<td>Golden Suffix</td><td><span class="textInfo"><c:out value="${detail.goldenSuffix}" /></span></td>
<td>Reserve ID</td><td>
<form:input path="reserveId" maxlength="20" cssStyle="width:250px" readonly="${detail.requestStatus != '01'}"/>
<div style="clear:both"></div>
<form:errors path="reserveId" cssClass="error"/>
</td>
</tr>
<tr>
<td>Remarks</td><td colspan="2"><textarea readonly="readonly" cols="80" rows="5">${detail.remarks}</textarea></td>
<td>
<input type="hidden" name="rowId" value="${detail.rowId}">
<c:choose>
<c:when test="${detail.requestStatus == '01'}">
<input type="submit" value="Reserve" name="updateButton" style="width:200px">
</c:when>
<c:when test="${detail.requestStatus == '02'}">
<input type="submit" value="Approve" name="approveButton" style="width:200px">
</c:when>
</c:choose>
<br>
<input type="button" value="Quit" name="quitButton" style="width:200px">
</td>
</tr>
</c:otherwise>
</c:choose>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>