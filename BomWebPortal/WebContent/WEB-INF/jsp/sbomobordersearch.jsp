<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util"%>

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

<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;">
<c:choose>
	<c:when test="${not empty sboMobOrderList}">
	
	
	
<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title">LOB</td>
		<td class="table_title">Order ID</td>
		<td class="table_title">OCID</td>
		<td class="table_title">ID Doc Type</td>
		<td class="table_title">ID Doc Num</td>
		<td class="table_title"><span style="display:inline-block;width: 200px">Customer Name</span></td>		
		<td class="table_title">Service Num</td>
		<td class="table_title">Application Date</td>
		<td class="table_title"><span style="display:inline-block;width: 300px">Order Status</span></td>
		<td class="table_title">Contact Email Addr</td>
		<td class="table_title">Delivery Date</td>
		<td class="table_title">SR Date</td>
		<td class="table_title">BOM Order Status</td>
		<td class="table_title">Actual SR Date</td>
		<td class="table_title">Recall Order</td>
		<td class="table_title">Cancel Order</td>
	</tr>
</thead>
<tbody>
<c:forEach items="${sboMobOrderList}" var="o">
	<tr>
		<td>${o.lob }</td>
		<td>
			<c:choose>
			<c:when test="${fn:startsWith(o.orderId, 'CSBS') }">
				<sb-util:orderpreview orderId="${o.orderId}" shopCd="SBS" mobcosbaseurl="<%=scheme %>"/>
				<!-- 
					<a href="sbsorderdetail.html?orderId=${o.orderId}"	title="Order Enquiry for MOB">${o.orderId}</a>
				 -->
			</c:when>
			<c:otherwise>
				<sb-util:orderpreview orderId="${o.orderId}" shopCd="SBO" mobcosbaseurl="<%=scheme %>"/>
				<!-- 
					<a href="mobccspreview.html?orderId=${o.orderId}&action=ENQUIRY"
								title="Order Enquiry for MOB">${o.orderId}</a>
				 -->
			</c:otherwise>
			</c:choose>
		</td>
		<td>${o.ocid }</td>
		<td>${o.idDocType }</td>
		<td>${o.idDocNum }</td>
		<td>${o.custName}</td>		
		<td>${o.msisdn }</td>
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
				<c:if test="${fn:startsWith(o.orderId, 'CSBSM')}">
						<c:if test='${o.checkPoint < 490}'>
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
		<td>${o.esigEmailAddr }</td>
		<td>${o.deliveryDate} ${o.deliveryTimeSlot }</td>
		<td>${o.srvReqDate }</td>
		<td><my:code id="${o.bomOrderStatus}" codeType="BOM_MOB_ORD_STATUS" source=""/></td>
		<td>${o.actSrvReqDate }</td>
		<td>
			<c:if test="${fn:startsWith(o.orderId, 'CSBOM') }"> 
				<c:if test="${(o.orderStatus == '01' && o.checkPoint == '490') || o.orderStatus == '99' || (o.orderStatus == '01' && o.checkPoint <= '399')}">
					<sb-util:orderrecall orderId="${o.orderId}" shopCd="SBO" 
					checkPoint="${o.checkPoint}" orderStatus="${o.orderStatus}" mobcosbaseurl="<%=scheme %>"/>
					<!-- 
						<input type="button" value="Recall" onClick="window.location='mobccspreview.html?orderId=${o.orderId}&status=${o.orderStatus}' ">	
					 -->
				</c:if>
			</c:if>
		</td>
		<td>
			<c:if test="${fn:startsWith(o.orderId, 'CSBOM') }"> 	
				<c:if test="${(o.orderStatus == '01' && o.checkPoint == '490') || (o.orderStatus == '01' && o.checkPoint <= '399') ||( o.orderStatus =='99' && o.allowCancelInd=='Y')}">					
					<c:set var="orderType" value="${fn:substring(o.orderId, 4, 6)}" />
					<c:choose>
						<c:when test="${orderType == 'MR' || orderType == 'MU' || orderType == 'MC' || orderType == 'MB' || orderType == 'MT' || orderType == 'MS' || orderType == 'MP'}">
							<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
							<sb-util:sso url="${mobcosurl}cancelorder.html?orderId=${o.orderId}" var="cosOrderCancelUrl"/>
							<input type="button" value="COS Cancel" onClick="window.location='${cosOrderCancelUrl}'">
						</c:when>
						<c:otherwise>
							<input type="button" value="Cancel" onClick="window.location='<c:url value="mobccscancellation.html">
								<c:param name="orderId" value="${o.orderId}"/>	</c:url>'">
						</c:otherwise>
					</c:choose>
				</c:if>
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
