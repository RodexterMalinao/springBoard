<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.ui.slider.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("form[name=submitForm]").submit(function(e) {
		$(".error").hide();
		$(".error_msg").hide();
		var errorFound = false;
		if ($.trim($("input[name=contactName]").val()).length == 0) {
			$(".error_contactName").show();
			errorFound = true;
		}
		if ($.trim($("input[name=contactPhone]").val()).length == 0) {
			$(".error_contactPhone").show();
			errorFound = true;
		}
		/*
		if ($.trim($("input[name=contactEmail]").val()).length == 0) {
			$(".error_contactEmail").show();
			errorFound = true;
		}
		*/
		if ($("input[name=callTypeCd]:checked").length == 0) {
			$(".error_callTypeCd").show();
			errorFound = true;
		}
		if ($("select[name=nature]")[0].selectedIndex == 0) {
			$(".error_nature").show();
			errorFound = true;
		}
		if ($("input[name=result]:checked").length == 0) {
			$(".error_result").show();
			errorFound = true;
		}
		if ($("textarea[name=remark]").val().length > 4000) {
			$(".error_remark").show();
			errorFound = true;
		}
		if (errorFound) {
			e.preventDefault();
			return false;
		}
	});
	
	$("#caseStatus").change(function() {
		//Only CSI Call Log would have this function
		if ($('[name=caseNo]').val().length > 0 ){
			if ($("select#caseStatus").val()=='CR02' || $("select#caseStatus").val()=='CR03'){
				alert('Send & Update SMS is required', 'Alert Dialog');
			}
			if ($("select#caseStatus").val()=='CR09' || $("select#caseStatus").val()=='CR08' || $("select#caseStatus").val()=='CR12' ){
				alert('Input [Next Contact Date] is required', 'Alert Dialog');
			}
		}
	});
	

	$("input[name=backButton]").click(function() {
		//CSI Case
		if ($('[name=caseNo]').val().length > 0 ){
			window.location.href = "<c:url value="mobccscsicasecreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value="${param.caseNo}"/></c:url>";
		} else //Normal Call Log
		{
			window.location.href = "<c:url value="mobccscalllog.html"><c:param name="orderId" value="${param.orderId}"/></c:url>";
		}
	});
});
</script>

<form:form action="mobccscalllogcreate.html" name="submitForm" commandName="mobccscalllogcreate">
	<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:150px"></colgroup>
	<tr class="contenttextgreen">
		<c:choose>
		<c:when test="${empty param.caseNo}">
			<td class="table_title" colspan="2">Call Log Create(Order ID: <c:out value="${param.orderId}"/>)</td>
		</c:when>
		<c:otherwise> 
			<td class="table_title" colspan="2">Call Log Create(CSI Case No: <c:out value="${param.caseNo}"/>)</td>
		</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td>Contact person</td>
		<td>
		<form:input path="contactName" maxlength="40" cssStyle="width:200px" />
		<form:errors path="contactName" cssClass="error"/>
		<span class="error error_msg error_contactName" style="display:none">Contact person is required</span>
		</td>
	</tr>
	<tr>
		<td>Contact #</td>
		<td>
		<form:input path="contactPhone" maxlength="64" cssStyle="width:200px" />
		<form:errors path="contactPhone" cssClass="error"/>
		<span class="error error_msg error_contactPhone" style="display:none">Contact # is required</span>
		</td>
	</tr>
	<tr>
		<td>Contact Email</td>
		<td>
		<form:input path="contactEmail" maxlength="64" cssStyle="width:200px" />
		<form:errors path="contactEmail" cssClass="error"/>
		<span class="error error_msg error_contactEmail" style="display:none">Contact # is required</span>
		</td>
	</tr>
	<tr>
		<td>Contact by</td>
		<td><c:out value="${bomsalesuser.username}" /></td>
	</tr>
	<tr>
		<td>Call Type</td>
		<td>
		<form:radiobuttons path="callTypeCd" items="${callTypeCds}" itemLabel="codeDesc" itemValue="codeId" />
		<form:errors path="callTypeCd" cssClass="error"/>
		</td>
	</tr>
		<tr>
		<td>Relationship with Customer</td>
		<td>
		<form:select path="relWtCust">
			<form:options items="${relWtCustLst}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		</td>
	</tr>
	<tr>
		<td>Result</td>
		<td>
		<form:select path="resultCd" id="caseStatus" >
			<form:options items="${results}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		<form:errors path="resultCd" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td>Remark</td>
		<td>
		<form:textarea path="remark" rows="5" cols="80" />
		<div style="clear:both"></div>
		<form:errors path="remark" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<form:hidden path="orderId"/>
		<form:hidden path="caseNo"/>
		<input type="button" value="Quit" name="backButton" style="float:right">
		<input type="submit" value="Create" style="float:right">
		</td>
	</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>