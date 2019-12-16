<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>

<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("input[name=quitButton]").click(function() {
		window.close();
	});
});
</script>

<form:form name="falloutHistForm" method="POST" commandName="falloutHist">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<tr class="contenttextgreen">
		<td class="table_title" colspan="4">Fallout History(Order ID: <c:out value="${param.orderId}"/>)</td>
	</tr>
	<tr class="contenttextgreen">
		<td class="table_title">Fallout Reason</td>
		<td class="table_title">Fallout Remarks</td>
		<td class="table_title">Fallout By</td>
		<td class="table_title">Fallout Date</td>
	</tr>
	<c:forEach items="${falloutHist}" var="falloutHist">
		<tr>
			<td><my:code id="${falloutHist.falloutCode}" codeType="ORD_FALLOUT_CODE" source=""/>&nbsp;</td>
			<td>${falloutHist.remarks}&nbsp;</td>
			<td>${falloutHist.staffId}&nbsp;</td>
			<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${falloutHist.occuranceDate}" />&nbsp;</td>
		</tr>
	</c:forEach>
</table>
	<input type="button" value="Quit" name="quitButton" style="float:right">
</form:form>