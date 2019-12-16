<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;">
	<c:choose >
		<c:when test="${not empty ltsOrderSearchResult}">
			<table width="100%" border="1" cellspacing="0" class="contenttext" style="margin: 0;">
				<thead>
					<tr class="contenttextgreen">
						<td class="table_title">LOB</td>
						<td class="table_title">Order ID</td>
						<td class="table_title">OCID</td>
						<td class="table_title">ID Doc Type</td>
						<td class="table_title">ID Doc Number</td>
						<td class="table_title">Customer Name</td>
						<td class="table_title">Service Number</td>
						<td class="table_title">Application Date</td>
						<td class="table_title">Contact Email Address</td>
						<td class="table_title">Order Status</td>
						<td class="table_title">Error Message</td>
						<td class="table_title">Resend Email</td>
						<td class="table_title">Amend SRD</td>
						<td class="table_title">Cancel Order</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ltsOrderSearchResult}" var="ltsResult">
						<tr>
							<td>${ltsResult.lob}</td>
							<td><a href="ltsupgradeeyeorder.html?action=ENQUIRY&orderId=${ltsResult.orderId}"> ${ltsResult.orderId}</a></td>
							<td>${ltsResult.ocId}</td>
							<td>${ltsResult.idDocType}</td>
							<td>${ltsResult.idDocNum}</td>
							<td>${ltsResult.custName}</td>
							<td>${ltsResult.srvNum}</td>
							<td>${ltsResult.applicationDate}</td>
							<td>${ltsResult.contactEmail}</td>
							<td>
								<spring:message code="lts.orderStatus.${ltsResult.status}"
									text="${ltsResult.status}" />
							</td>
							<td>${ltsResult.errorMsg}</td>
							<td>
							<a href="ordersendemailhist.html?orderId=${ltsResult.orderId}&templateId=${ltsResult.emailTemplateId}&action=PREVIEW" class="nextbutton" style="padding:4px;left:2px;top:2px;">Resend</a>
							</td>
							<td>
							<a href="${ltsResult.amendSrdUrl}" target="_blank" class="nextbutton" style="padding:4px;left:2px;top:2px;">Amend</a>
							</td>
							<td>
							<c:if test="${ltsResult.status != 'C' }">
								<a href="ltsordercancel.html?orderId=${ltsResult.orderId}" class="nextbutton" style="padding:4px;left:2px;top:2px;">Cancel</a>
							</c:if>
							</td>														
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
