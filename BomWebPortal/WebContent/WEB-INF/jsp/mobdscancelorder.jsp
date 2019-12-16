<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
	function formSubmit(actionType) {
		
		document.cancelOrderForm.actionType.value = actionType;
		if (actionType == "CLONE" && <c:out value="${not empty cancelOrder.ocid}" />) {
			if (!confirm("Please note that ocid ${cancelOrder.ocid} in BOM must be cancelled before re-submit by sales")) {
				return false;
			}
		} else if (actionType == "CLONE" && <c:out value="${not empty cancelOrder.cloneOrderId}" /> 
						&& <c:out value="${cancelOrder.cloneOrderStatus ne 'CANCELLED'}" />) {
			alert("Previous Order is an clone order and is not CANCELLED! \nNot allow to clone.");
			return false;
		}
		document.cancelOrderForm.submit();
	}
</script>

<form:form name="cancelOrderForm" method="POST" commandName="cancelOrder">
<input type="hidden" name="actionType" id="actionType"/>
<h2 class="table_title" style="font-size:medium;margin:0">Cancel Order: ${cancelOrder.orderId}</h2>
<table border="0" cellspacing="1" class="contenttext">
<tr>
	<td align="right"><b>Order ID: </b></td>
	<td><form:label path="orderId">${cancelOrder.orderId}</form:label></td>
</tr><tr>
	<td align="right"><b>Order Status: </b></td>
	<td><form:label path="orderStatus">${cancelOrder.orderStatus}</form:label></td>
</tr><tr>
	<td align="right"><b>MRT Number: </b></td>
	<td><form:label path="msisdn">${cancelOrder.msisdn}</form:label></td>
</tr><tr>
	<td align="right"><b>MRT Status: </b></td>
	<c:if test="${cancelOrder.mnpInd != 'Y' }">
		<td><form:label path="msisdnStatus">${cancelOrder.msisdnStatus}</form:label></td>
	</c:if>
	<c:if test="${cancelOrder.mnpInd == 'Y' }">
		<td>MNP</td>
	</c:if>
</tr>
</table>
<table width="100%" border="0" cellspacing="1" class="contenttext">
	<tr>
		<td><b>Item Type</b></td>
		<td><b>Item Code</b></td>
		<td><b>Serial Number</b></td>
		<td><b>Item Description</b></td>
		<td><b>Current Status</b></td>
		<td><b>Goods Return</b></td>
	</tr>	
	<c:forEach items="${cancelOrder.stockItemList}" var="stockItem" varStatus="stockItemStatus">
	<c:if test="${!empty stockItem.statusId}">
	<tr>
		<td><form:label path="stockItemList[${stockItemStatus.index}].itemType">${stockItem.itemType}</form:label></td>
		<td><form:label path="stockItemList[${stockItemStatus.index}].itemCode">${stockItem.itemCode}</form:label></td>
		<td><form:label path="stockItemList[${stockItemStatus.index}].itemSerial">${stockItem.itemSerial}</form:label></td>
		<td><form:label path="stockItemList[${stockItemStatus.index}].itemDesc">${stockItem.itemDesc}</form:label></td>
		<td><form:label path="stockItemList[${stockItemStatus.index}].statusDesc">${stockItem.statusDesc}</form:label></td>
		<td>
			<c:if test="${(stockItem.itemType != '02')}">
			<form:select path="stockItemList[${stockItemStatus.index}].statusId" >
			<form:option value="34" label="Goods Returned"></form:option>
			<form:option value="32" label="Goods NOT Returned"></form:option>
			</form:select>
			</c:if>
			<c:if test="${(stockItem.itemType == '02')}">
			<form:select path="stockItemList[${stockItemStatus.index}].statusId" >
			<%-- <form:option value="34" label="SIM Returned"></form:option> --%>
			<form:option value="33" label="SIM Abandon"></form:option>
			</form:select>
			</c:if>
		</td>
		<td></td>
		
	</tr>
	</c:if>
	</c:forEach>
</table>
<div class="buttonmenubox_R">
<c:if test="${((bomsalesuser.category == 'FRONTLINE') || (bomsalesuser.category == 'ORDER SUPPORT')) && !cancelOrder.multiSim && cancelOrder.preRegInd != 'Y'}">
	<a href="#" class="nextbutton3" onClick="javascript:formSubmit('CLONE');">CLONE and Cancel Order &gt;</a>
</c:if>
<c:if test="${((cancelOrder.orderStatus == 'REVIEWING') || (cancelOrder.orderStatus == 'QAPROCESS')
 || (cancelOrder.orderStatus == 'INITIAL')  || (cancelOrder.orderStatus == 'REJECTED'))}">
	<a href="#" class="nextbutton3" onClick="javascript:formSubmit('RELEASE');">RELEASE Resources and Cancel Order &gt;</a>
</c:if>
<c:if test="${(bomsalesuser.category == 'FRONTLINE') || (bomsalesuser.category == 'ORDER SUPPORT')}">
<a href="#" class="nextbutton3" onClick="javascript:formSubmit('CANCEL');">REFUND and Cancel Order (via F&S) &gt;</a>
</c:if>
</div>
</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
