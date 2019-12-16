<%@ include file="/WEB-INF/jsp/header.jsp"%>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
<td class="table_title">ORDER ID</td>
<td class="table_title">MRT</td>
<td class="table_title">MRT LEVEL</td>
<td class="table_title">SERVICE REQUEST DATE</td>
<td class="table_title">APPN DATE</td>
<td class="table_title">STAFF ID</td>
<td class="table_title">STAFF NAME</td>
<td class="table_title">ORDER STATUS CODE</td>
<td class="table_title">ORDER STATUS</td>
</tr>
<tr>
<td>${record.orderId}</td>
<td>${record.msisdn}</td>
<td>${record.msisdnlvl}</td>
<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.srvReqDate}"/></td>
<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.appDate}"/></td>
<td>${record.salesCode}</td>
<td>${record.salesName}</td>
<td>${record.orderStatus}</td>
<td>${record.orderStatusDesc}</td>
</tr>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>