<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript">

function closeAndNotify(notify) {
	// Firefox lose window.dialogArugments after post form
	if (notify == true) {
		var callback = (window.dialogArguments ? window.dialogArguments : window.opener).reloadcallback;
		if (callback) callback();
	}
	self.close();
};


$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("#closeButton").click(function(e) {
		//window.close();
		closeAndNotify(false);
	});
	
	$("#submitButton").click(function(e) {
		if (confirm("Are you sure to initiate an Order Cancel Request ?")) {
			$("#form").submit();
		}
		
	});
	
	/*
	if ('${done}'=='true') {
		closeAndNotify(true);
	}
	*/

});
</script>

<span class="contenttextgreen">Order ID: <c:out value="${param.orderId}" /></span>

<div class="clear:both"></div>

<br/><br/>
<form:form  id="form"  method="post" action="#">

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<tr>
		<td class="table_title">Order Cancel Request</td>
	</tr>
	<tr>
		<td>
			<table>
				<tr>
					<td><b>Cancel Reason</b></td>
					<td>
						<form:select path="reasonCd" id="reasonCd" >
							<form:options items="${cancelCodes }" itemValue="codeId" itemLabel="codeDesc"/>
						</form:select>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
</table>
</form:form>
</form>
<br/>



<div style="clear:both"></div>

<div style="float:right;padding:10px 0">
<input type="button" id="closeButton" value="Close">
<input type="button" id="submitButton" value="Cancel Request">
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>