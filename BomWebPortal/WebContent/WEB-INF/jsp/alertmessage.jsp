<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>

<script>

function formSubmit() {
	document.alertMsgForm.submit();
}

function ocidStatusSearch(ref) {
	$.ajax({
		url : 'mobccsordersearchlookup.html'
		, data: { ocid: $.trim($(ref).text()) }
		, type : 'POST'
		, dataType : 'JSON'
		, timeout : 5000
		, success : function(msg) {
			$.each(eval(msg), function(i, item) {
				alert("BOM Status: "+ item.status);
			});
		}
	});
}

</script>

<style type="text/css">
.orderSearch { background-color: white; border: solid 2px #C0C0C0 }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.orderSearchResult { background-color: white }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.orderSearch { padding-left: 0 }
.orderSearchResult { padding-bottom: 1em }
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
<form:form name="alertMsgForm" method="POST" commandName="alertMsg">
<div class="orderSearch">
	<h2 class="table_title" style="font-size:medium;margin:0">MOB Order Enquiry</h2>
	<div class="overflowDiv orderSearchResult">
	<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" >
	<tr class="contenttextgreen">
		<td class="table_title">Order ID</td>
		<td class="table_title">Order Type</td>
		<td class="table_title"><span style="display:inline-block;width: 300px">Order Status</span></td>
		<td class="table_title"><span style="display:inline-block;width: 200px">Customer Name</span></td>
		<td class="table_title">Service Num</td>
		<td class="table_title">OCID</td>
		<td class="table_title">BOM Order Status</td>
		<td class="table_title">Actual SR Date</td>
		<td class="table_title"><span style="display:inline-block;width: 250px">Error Message</span></td>
		<td class="table_title">SR Date</td>
		<td class="table_title">Application Date</td>
		<td class="table_title">Delivery Date</td>
		<td class="table_title"><span style="display:inline-block;width: 100px">Last Update Date</span></td>
		<td class="table_title">Staff ID</td>
		<td class="table_title">Recall Order</td>
		<c:if test="true">
		<td class="table_title">Cancel Order</td>
		</c:if>
		<td class="table_subtitle_blue">Force Cancellation Remaining Day</td>
	</tr>
	<c:choose>
	<c:when test="${orderList == null}">
	</c:when>
	<c:when test="${empty orderList}">
		<tr>
			<td colspan="13">No Data Found</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${orderList}" var="orderList">
			<tr>
			<td>
				<sb-util:orderpreview orderId="${orderList.orderId}" shopCd="${orderList.shopCode}"  mobcosbaseurl="<%=scheme %>"/>									
			</td>
			<td><my:code id="${orderList.busTxnType}" codeType="ORD_CCS_TYPE"/></td>
				<td>
				<c:if test='${orderList.orderStatus == "99"}'>
					<c:if test='${orderList.salesFalloutFlag == true}'>
						<span style="color:red">
							<my:code id="${orderList.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
						</span>
					</c:if>
					<c:if test='${orderList.salesFalloutFlag == false}'>
						<my:code id="${orderList.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
					</c:if>
				</c:if>
				<c:if test='${orderList.orderStatus == "01"}'>
					<my:code id="${orderList.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
				</c:if>
				<c:if test='${orderList.orderStatus == "02"}'>
					Completed
				</c:if>
				<c:if test='${orderList.orderStatus == "03"}'>
					Cancelling
				</c:if>
				<c:if test='${orderList.orderStatus == "04"}'>
					Cancelled
				</c:if>
			</td>
			<td>${orderList.orderSumCustName}&nbsp;</td>
			<td>${orderList.msisdn}&nbsp;</td>
			<td><a href="#" onclick="ocidStatusSearch(this)">${orderList.ocid}</a></td>
			<td><my:code id="${orderList.bomOrderStatus}" codeType="BOM_MOB_ORD_STATUS" source=""/></td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy"
						value="${orderList.actSrvReqDate}" />
			</td>				
			<td>${orderList.errorMessage}&nbsp;</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy"
						value="${orderList.srvReqDate}" />
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy"
						value="${orderList.appInDate}" />
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy"
						value="${orderList.deliveryDate}" />
				(${orderList.deliveryTimeSlot})
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm"
						value="${orderList.lastUpdateDate}" />
			</td>
			<td>${orderList.salesCd}&nbsp;</td>
			<td>
				<c:if test="${orderList.orderStatus == '99' || (orderList.orderStatus == '01' && orderList.checkPoint <= '399')}">
					<sb-util:orderrecall orderId="${orderList.orderId}" shopCd="${orderList.shopCode}" 
					checkPoint="${orderList.checkPoint}" orderStatus="${orderList.orderStatus}" mobcosbaseurl="<%=scheme %>" frozenInd="${orderList.bomFrozenInd }"/>					
				</c:if>
			</td>
			<td>
				<c:if test="${!(orderList.busTxnType eq 'COS' || orderList.busTxnType eq 'BRM' || orderList.busTxnType eq 'TOO1')}">
					<c:if test="${(orderList.orderStatus == '01' && orderList.checkPoint <= '399') ||( orderList.orderStatus =='99' && orderList.allowCancelInd=='Y')}">
					
						<input type="button" value="Cancel" onClick="window.location='<c:url value="mobccscancellation.html">
						<c:param name="orderId" value="${orderList.orderId}"/>	</c:url>'">
						
					</c:if>
				</c:if>
				<c:if test="${orderList.busTxnType eq 'COS' || orderList.busTxnType eq 'BRM' || orderList.busTxnType eq 'TOO1'}">
					<c:if test="${(orderList.orderStatus == '01' && orderList.checkPoint == '490') ||(orderList.orderStatus == '01' && orderList.checkPoint <= '399') ||( orderList.orderStatus =='99' && orderList.allowCancelInd=='Y')}">
							
					<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
					<sb-util:sso url="${mobcosurl}cancelorder.html?orderId=${orderList.orderId}" var="cosOrderCancelUrl"/>
					<input type="button" value="COS Cancel" onClick="window.location='${cosOrderCancelUrl}'">
					</c:if>
				</c:if>
			</td>
			<td>${orderList.forceCancelRemainDays}</td>
		</tr>
		</c:forEach>
	</c:otherwise>
	</c:choose>
	</table>
	</div>
</div>
</form:form>