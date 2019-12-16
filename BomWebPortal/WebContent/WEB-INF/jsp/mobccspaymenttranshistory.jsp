<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("#closeButton").click(function(e) {
		window.close();
	});
});
</script>

<span class="contenttextgreen">Order ID: <c:out value="${param.orderId}" /></span>

<div class="clear:both"></div>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr>
<td class="table_title">Transaction Date</td>
<td class="table_title">Pay Method</td>
<td class="table_title">Credit Card Num</td>
<td class="table_title">Installment Schedule</td>
<td class="table_title">Approval Code</td>
<td class="table_title">Payment Amount</td>
<td class="table_title">Batch Num/ Ref No</td>
<td class="table_title">Bank In Date</td>
<td class="table_title">Epaylink Txn Id</td>
</tr>
<c:choose>
<c:when test="${resultList == null}">
</c:when>
<c:when test="${empty resultList}">
<tr>
<td colspan="9">No record</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${resultList}" var="r">
<tr>
<td><fmt:formatDate pattern="dd/MM/yyyy" value="${r.trans_date}"/></td>
<td>
<c:choose>
<c:when test="${r.pay_method == 'C'}">Credit Card</c:when>
<c:when test="${r.pay_method == 'I'}">Credit Card Installment</c:when>
<c:when test="${r.pay_method == 'M'}">Cash</c:when>
<c:otherwise>N/A</c:otherwise>
</c:choose>
</td>
<td>${r.ccNum}</td>
<td>${r.ccInstSchedule}</td>
<td>${r.approveCode}</td>
<td>\$<fmt:formatNumber pattern="0.0#" value="${r.pay_amount}"/></td>
<td>${r.batchNum}</td>
<td><fmt:formatDate pattern="dd/MM/yyyy" value="${r.bankInDate}"/></td>
<td>${r.epaylinkTxnId}</td>
</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</table>
<br>

<!-- Online Payment trx result-->
<c:if test="${paymentTxnResultList != null}">
<span class="contenttextgreen">Online Payment transaction history</span>
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr>
<td class="table_title">Ref No</td>
<td class="table_title">Status</td>
<td class="table_title">Payment Amount</td>
<td class="table_title">Card Token</td>
<td class="table_title">Expiry Date</td>
<td class="table_title">Return Code</td>
<td class="table_title">Auth Code</td>
<td class="table_title">Epaylink Txn Id</td>
</tr>
<c:choose>
<c:when test="${paymentTxnResultList == null}">
</c:when>
<c:when test="${empty paymentTxnResultList}">
<tr>
<td colspan="8">No record</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${paymentTxnResultList}" var="r">
<tr>
<td >${r.referenceNo}</td>
<td >${r.status}</td>
<td >$<fmt:formatNumber pattern="0.0#" value="${r.payAmount}"/></td>
<td >${r.cardPan}</td>
<td >${r.expDate}</td>
<td >${r.retCode}</td>
<td >${r.authCode}</td>
<td >${r.epaylinkTxnId}</td>


</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</table>
</c:if>

<div style="clear:both"></div>

<div style="float:right;padding:10px 0">
<input type="button" id="closeButton" value="Close">
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>