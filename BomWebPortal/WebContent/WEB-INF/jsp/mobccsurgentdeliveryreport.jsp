<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
<%
java.util.Date current=new java.util.Date();
java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("ddMMyyyy"); 
String date=format.format(current);
%>
$(document).ready(function() {
	var processDate = $("#processDate").val();
	$('#processDate').datepicker({
		changeMonth : true,
		changeYear : true,

		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-100Y",
		yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
	//yearRange: "1911:1993" //the range shown in the year selection box
	});
	$('#processDate').datepicker( "setDate" , new Date() );
});

$(".downloadDeliveryReport").click(function(e) {
	e.preventDefault();
	var orderId = $.trim($(this).parent().parent().find(".orderId").text());
	$form.append($("<input/>").attr({type: "hidden", name: "orderId"}).val(orderId));
	$form.append($("<input/>").attr({type: "hidden", name: "processDate"}).val(processDate));
	$(document.body).append($form);
	$form.submit();
	$(document.body).remove($form);
	return false;
});
</script>

<form:form method="POST" action="mobccsurgentdeliveryreport.html">

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width: 150px"></colgroup>
<tr>
<td>Order ID</td>
<td><form:input path="orderId" /></td>

<td>Processing Date</td>
<td><form:input path="processDate" readonly="true"/></td>

<td><input type="submit"></td>
</tr>
</table>
</form:form>


<form:form action="mobccsurgentdeliveryreport.html" method="POST">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
<td class="table_title"  >COURIER</td>
<td class="table_title"  >PROCESS_DATE</td>
<td class="table_title"  >ORDER_ID</td>
<td class="table_title"  >OCID</td>
<td class="table_title"  >MOBILE_NO</td>
<td class="table_title"  >CUST_NAME</td>
<td class="table_title"  >STAFF_ID</td>
<td class="table_title"  >DELIVERY_TYPE</td>
<td class="table_title"  >DELIVERY_DATE</td>
<td class="table_title"  >DELIVERY_TIME_SLOT</td>
<td class="table_title"  >PAYMENT_METHOD</td>
<td class="table_title"  >PAYMENT_AMT</td>
<td class="table_title"  >CONTACT_NAME</td>
<td class="table_title"  >CONTACT_NUM_1</td>
<td class="table_title"  >CONTACT_NUM_2</td>
<td class="table_title"  >DELIVERY_ADDRESS</td>
<td class="table_title"  >ITEM_TYPE</td>
<td class="table_title"  >ITEM_CODE</td>
<td class="table_title"  >ITEM_DESC</td>
<td class="table_title"  >ITEM_SERIAL</td>
<td class="table_title"  >DOA_ITEM_SERIAL</td>
</tr>
<c:choose>
<c:when test="${results == null}"></c:when>
<c:when test="${empty results}">

</c:when>
<c:otherwise>
<c:forEach items="${results}" var="r">
<tr>
<td><c:out value="${r.courier}" /></td>
<td><fmt:formatDate pattern="yyyy/MM/dd" value="${r.processDate}"/></td>
<td><c:out value="${r.orderId}" /></td>
<td><c:out value="${r.ocid}" /></td>
<td><c:out value="${r.msisdn}" /></td>
<td><c:out value="${r.custName}" /></td>
<td><c:out value="${r.staffId}" /></td>
<td><c:out value="${r.deliveryType}" /></td>
<td><fmt:formatDate pattern="yyyy/MM/dd" value="${r.deliveryDate}"/></td>
<td><c:out value="${r.deliveryTimeSlot}" /></td>
<td><c:out value="${r.paymentMethod}" /></td>
<td><c:out value="${r.paymentAmt}" /></td>
<td><c:out value="${r.contactName}" /></td>
<td><c:out value="${r.contactNum1}" /></td>
<td><c:out value="${r.contactNum2}" /></td>
<td><c:out value="${r.deliveryAddress}" /></td>

<td><c:out value="${r.itemType}" /></td>
<td><c:out value="${r.itemCode}" /></td>
<td><c:out value="${r.itemDesc}" /></td>
<td><c:out value="${r.itemSerial}" /></td>
<td><c:out value="${r.doaitemSerial}" /></td>

</tr>
</c:forEach>



<tr>
<td colspan="2">Total count: <c:out value="${fn:length(results)}" /></td>
<td><input type="submit" name="Export CSV" value="Export CSV">
<form:hidden path="orderId"/>
<form:hidden path="processDate"/>
<input type="hidden" name="exportCsv" value="true">
</td>
</tr>
</c:otherwise>
</c:choose>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>