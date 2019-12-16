<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#startDateDatepicker').datepicker({
		changeMonth : true,
		changeYear : true,

		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-100Y",
		yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
	//yearRange: "1911:1993" //the range shown in the year selection box
	});
	$('#endDateDatepicker').datepicker({
		changeMonth : true,
		changeYear : true,

		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-100Y",
		yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
	//yearRange: "1911:1993" //the range shown in the year selection box
	});
});
</script>

<form:form method="POST" name="mobccsmrtadmin">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
<td class="table_title" colspan="6">Golden MRT Administration</td>
</tr>
<tr>
<td>Request Date</td>
<td>From</td>
<td><input id="startDateDatepicker" name="startDate" value="${startDate}" readonly="readonly"/></td>
<td>To</td>
<td><input id="endDateDatepicker" name="endDate" value="${endDate}" readonly="readonly"/></td>
<td><input type="submit" value="Search"></td>
</tr>
</table>
</form:form>
<div style="clear:both;padding-top:5px"></div>
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
<td class="table_title">ORDER ID</td>
<td class="table_title">REQUEST DATE</td>
<td class="table_title">GOLDEN SUFFIX</td>
<td class="table_title">TARRIF PRICE</td>
<td class="table_title">CONTRACT PERIOD</td>
<td class="table_title">REQUEST STATUS</td>
</tr>
<c:choose>
<c:when test="${not empty recordList}">
<c:forEach var="r" items="${recordList}">
<tr>
<td><a href="<c:url value="mobccsmrtadmindetail.html"><c:param name="requestRowId" value="${r.rowId}"/></c:url>">${r.orderId}</a></td>
<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${r.requestDate}"/></td>
<td>${r.goldenSuffix}</td>
<td>\$${r.monthlyCharge}</td>
<td>${r.contractPeriod}</td>
<td>
<c:choose>
<c:when test="${r.requestStatus == '01'}">REQUESTED</c:when>
<c:when test="${r.requestStatus == '02'}">RESERVED</c:when>
<c:when test="${r.requestStatus == '03'}">APPROVED</c:when>
<c:when test="${r.requestStatus == '04'}">COMPLETED</c:when>
</c:choose>
</td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
<tr>
<td colspan="5">No records</td>
</tr>
</c:otherwise>
</c:choose>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>