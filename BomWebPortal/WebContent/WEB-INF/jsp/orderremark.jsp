<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>

<script type="text/javascript">
$(document).ready(function() {
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
});

function formSubmit(actType) {
	$("#actionType").val(actType);
	if ($("#actionType").val() == 'SAVE'){
	if ($("#limitedTextArea").val().length > 200) {
		alert("Remark character limit: 200");
		return false;
	}}
	document.orderRemarkFrom.submit();
}
</script>

<div style="clear:both;padding-top:5px"></div>

<form:form name="orderRemarkFrom" method="POST" commandName="orderRemark">
<div class="row" style="font-weight: bold; color:red">
<c:if test="${actionType == 'success'}">
	The remark is saved successfully.
</c:if>
</div>
<table width="100%" border="0" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:120px"></colgroup>
	<colgroup></colgroup>
	<tr>
	<td colspan="13">Order ID: <c:out value="${param.orderId}" />

	</td>
	</tr>
</table>

<div style="clear:both;padding-top:5px"></div>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="resultListTable">
	<thead>
		<tr>
		<td class="table_title">Remarks</td>
		<td class="table_title">Create By</td>
		<td class="table_title">Create Date</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${empty remarkList}">
			<tr>
			<td colspan="13">No record</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach items="${remarkList}" var="record">
				<tr>
				<td><pre><c:out value="${record.remark}" /></pre></td>
				<td><c:out value="${record.createBy}" /></td>
				<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${record.createDate}" /></td>
				</tr>
			</c:forEach>
		</c:otherwise>
		</c:choose>
	</tbody>
	<tfoot>
	<c:if test='${actionType != "CREATE"}'>
		<tr>
			<td colspan="13">
				<a href="#" class="nextbutton" onClick="javascript:formSubmit('CREATE');" style="float:right">Create Order Remark</a>
			</td>
		</tr>
	</c:if>
	</tfoot>
</table>

<div style="clear:both;padding-top:10px"></div>

<c:if test="${actionType == 'CREATE'}">
	<table width="100%" border="0" cellspacing="5" class="contenttext" bgcolor="#FFFFFF">
	<thead>
	<tr>
		<td  class="table_title" colspan="2">Create New Order Remark</td>
	</tr>
	</thead>
	<tbody>
		<tr>
			<td style="float:right;">Remarks: </td>
			<td><form:textarea  id="limitedTextArea" path="remark" rows="5" cols="30" /></td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="13">
				<a href="#" class="nextbutton" onClick="javascript:formSubmit('QUIT');" style="float:right">Quit</a>
				<a href="#" class="nextbutton" onClick="javascript:formSubmit('SAVE');" style="float:right">Save</a>
			</td>
		</tr>
	</tfoot>
	</table>
</c:if>
	<input type="hidden" name="oderId" id="oderId" />
	<input type="hidden" name="actionType" id="actionType" />
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>