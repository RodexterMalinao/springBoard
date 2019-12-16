<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script>
	
function formSubmit() {
	document.urgentOrderSearchForm.submit();
}

function formClear() {
	document.urgentOrderSearchForm.orderId.value = "";
	document.urgentOrderSearchForm.deliveryDate.value = "";
}

$(function() {
	// Datepicker
	$('#deliveryDate').datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-2Y",
		maxDate : "+7Y" //Y is year, M is month, D is day
	});
});

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
<form:form name="urgentOrderSearchForm" method="POST" commandName="urgentOrderSearch">
	
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Urgent Order Management</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="1" class="contenttext"
				background="images/background2.jpg">
					<tr>
						<td width="10%">&nbsp;</td>
						<td width="40%">&nbsp;</td>
						<td width="10%">&nbsp;</td>
						<td width="40%">&nbsp;</td>
					</tr>
					<tr>
						<td>
							Delivery Date:
						</td>
						<td>
							<form:input path="searchDeliveryDate" id="deliveryDate" readonly="true"/>
						</td>
						<td>
							Order ID:
						</td>
						<td>
							<form:input path="searchOrderId" id="orderId"/>
						</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4">
							<input type="button" value="Clear" onClick="javascript:formClear();">
							<input type="button" value="Search" onClick="javascript:formSubmit();">
						</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
				</table>
				<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
					
					<tr class="contenttextgreen">
						<td class="table_title">Order ID</td>
						<td class="table_title">OCID</td>
						<td class="table_title">Order Status</td>
						<td class="table_title">Error Message</td>
						<td class="table_title">Customer Name</td>
						<td class="table_title">Service Num</td>
						<td class="table_title">Delivery Date</td>
						<td class="table_title">Delivery Time</td>
						<td class="table_title">SR Date</td>
						<td class="table_title">Staff Code</td>
						<td class="table_title">Manager Order</td>
					</tr>
					<c:choose>
					<c:when test="${urgentList == null}">
					</c:when>
					<c:when test="${empty urgentList}">
						<tr>
							<td colspan="11">No Data Found</td>
						</tr>
					</c:when>
					<c:otherwise>
					<c:forEach items="${urgentList}" var="urgentList">
						<tr>
						<td>
							<sb-util:orderpreview orderId="${urgentList.orderId}" shopCd="${urgentList.shopCode}"  mobcosbaseurl="<%=scheme %>"/>																
						</td>
						<td><a href="#" onclick="ocidStatusSearch(this)">${urgentList.ocid}</a></td>
						<td>
							<c:if test='${urgentList.orderStatus == "99"}'>
								<my:code id="${urgentList.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
							</c:if>
							<c:if test='${urgentList.orderStatus == "01"}'>
								<my:code id="${urgentList.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
							</c:if>
							<c:if test='${urgentList.orderStatus == "02"}'>
								Completed
							</c:if>
							<c:if test='${urgentList.orderStatus == "03"}'>
								Cancelling
							</c:if>
							<c:if test='${urgentList.orderStatus == "04"}'>
								Cancelled
							</c:if>
						</td>
						<td>${urgentList.errorMessage}&nbsp;</td>
						<td>${urgentList.orderSumCustName}&nbsp;</td>
						<td>${urgentList.msisdn}&nbsp;</td>
						<td>
							<fmt:formatDate pattern="dd/MM/yyyy"
									value="${urgentList.deliveryDate}" />
						</td>
						<td>${urgentList.deliveryTimeSlot}&nbsp;</td>
						<td>
							<fmt:formatDate pattern="dd/MM/yyyy"
									value="${urgentList.srvReqDate}" />
						</td>
						<td>${urgentList.salesCd}&nbsp;</td>
						<td>																
							<c:if test="${(urgentList.orderStatus == '01' && urgentList.checkPoint >= '199' && urgentList.checkPoint <= '399') ||(urgentList.orderStatus == '99' && urgentList.reasonCd=='M001')  }">
								<input type="button" value="Manager" onClick="window.location='mobccsurgentordermanagement.html?orderId=${urgentList.orderId}' ">
							</c:if>
						</td>
					</tr>
					</c:forEach>
					</c:otherwise>
					</c:choose>
				</table>
			</td>
		</tr>	
	</table>	
	
</form:form>