<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>

<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;">
<c:choose>
	<c:when test="${not empty sbsOrderList}">
	
	
	
<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title">LOB</td>
		<td class="table_title">Order ID</td>
		<td class="table_title"><span style="display:inline-block;width: 200px">Customer Name</span></td>		
		<td class="table_title">Application Date</td>
		<td class="table_title"><span style="display:inline-block;width: 300px">Order Status</span></td>
		<td class="table_title">Contact Email Addr</td>
		<td class="table_title">Delivery Date</td>
	</tr>
</thead>
<tbody>
<c:forEach items="${sbsOrderList}" var="o">
	<tr>
		<td>${o.lob }</td>
		<td>
			<a href="sbsorderdetail.html?orderId=${o.orderId}"
							title="Order Enquiry for MOB">${o.orderId}</a>
		</td>
		<td>${o.custFullName}</td>		
		<td>${o.appDate }</td>
		<td>
			<c:if test='${o.orderStatus == "99"}'>
				<my:code id="${o.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
			</c:if>
			<c:if test='${o.orderStatus == "01"}'>
				<my:code id="${o.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
				<c:if test="${fn:startsWith(o.orderId, 'CSBOM')}">
						<c:if test='${o.checkPoint < 399}'>
							<span style="color:red">(Incomplete)</span>
						</c:if>
				</c:if>
			</c:if>
			<c:if test='${o.orderStatus == "02"}'>
				Completed
			</c:if>
			<c:if test='${o.orderStatus == "03"}'>
				Cancelling
			</c:if>
			<c:if test='${o.orderStatus == "04"}'>
				Cancelled
			</c:if>
		</td>
		<td>${o.emailAddr }</td>
		<td>${o.deliveryDate} ${o.deliveryTimeSlot }</td>

	</tr>
</c:forEach>
</tbody>
</table>



	
	</c:when>
	<c:otherwise>
		<center>No record</center>
	</c:otherwise>
</c:choose>

</div>
