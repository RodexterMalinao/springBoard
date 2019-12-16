<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<script type="text/javascript">

function closeAndNotify() {
	// Firefox lose window.dialogArugments after post form
	var callback = (window.dialogArguments ? window.dialogArguments : window.opener).reloadcallback;
	if (callback) callback();
	self.close();
};

$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("#closeButton").click(function(e) {
		//window.close();
		closeAndNotify();
	});
	
	$("#submitButton").click(function(e) {
		var msg = '';
		var smtype = $("#smType").val();
		if ('${order.orderStatus}'=='03' && (smtype == '9' || smtype == '10')) {
			msg = 'Order will be cancelled. ';
		}
		if (confirm(msg + 'Are you sure to submit the POS SM ?')) {
			$("#possmform").submit();
		}
	});

	$("#smType").change(function() {
		var smtype = $("#smType").val();
		var smtypedesc = $("#smType option:selected").text();
		$("#smTypeDesc").val(smtypedesc);
		
		if ('${order.orderStatus}'=='03' && (smtype == '9' || smtype == '10')) {
			setTimeout(function() {
				alert("Order will be canceled");		
			}, 200);
		}

	});

});
</script>

<div class="contenttextgreen">
	<sb-ui:orderstatusinfo value="${order }"/>
</div>
<br/>
<div class="clear:both"></div>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<tr>
		<td class="table_title">POS SM#</td>
		<td class="table_title">Type</td>
		<td class="table_title">Settle Date</td>
		<td class="table_title">Remarks</td>
	</tr>
	<c:choose>
	<c:when test="${empty posSms}">
	<tr>
		<td colspan="9">No record</td>
	</tr>
	</c:when>
	<c:otherwise>
		<c:forEach var="possm" items="${posSms }">
		<tr>
			<td>${possm.smNum }</td>
			<td>${possm.smTypeDesc }</td>
			<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${possm.createDate}" /></td>
			<td>${possm.remark }</td>
		</tr>
		</c:forEach>			
	</c:otherwise>
	</c:choose>
</table>
<br/><br/>
<form:form id="possmform" commandName="possm" method="post" action="#">

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<tr>
		<td class="table_title">Input POS SM</td>
	</tr>
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td width="100"><b>POS SM</b></td>
					<td>
						<form:input path="smNum"/>
						<form:errors path="smNum" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td width="100"><b>Type</b></td>
					<td>
						<form:select path="smType" id="smType" >
							<form:option value="" label=""/>
							<%--<form:options items="${smTypeList }" itemValue="codeId" itemLabel="codeDesc"/> --%>
							<form:option value="0" label="Normal Sales"/>
							<form:option value="1" label="Advance Sales"/>
							<%--
							<form:option value="6" label="Fast Track"/>
							<form:option value="7" label="Cash On Delivery"/>
							<form:option value="9" label="Void"/>
							--%>
							<form:option value="10" label="Refund"/>
							<form:option value="11" label="Exchange"/>
							<%--
							<form:option value="13" label="Fast Track Advance Order"/>
							--%>
						</form:select>

						<form:hidden id="smTypeDesc" path="smTypeDesc"/> <form:errors path="smTypeDesc" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td width="100"><b>Remarks</b></td>
					<td>
						<form:input path="remark" cssStyle="width: 80%;"/>
						<form:errors path="remark" cssClass="error" />
					</td>
				</tr>								
			</table>
		</td>
	</tr>
</table>
</form:form>
<br/>



<div style="clear:both"></div>

<div style="float:right;padding:10px 0">
<input type="button" id="closeButton" value="Close">
<input type="button" id="submitButton" value="Submit">
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>