<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	<c:choose>
	<c:when test="${(bomsalesuser.channelCd == 'QOM' || bomsalesuser.channelCd == 'CFM') && record.orderStatus == '01' && record.checkPoint == '599'}">
	// Only Allow QOM/CFM to Edit
	$(".title_edit").text($(".title_edit").text() + " (Update)");
	$("form[name=submitForm]").submit(function(e) {
		$(".error_msg").hide();
		var errorFound = false;
		if ($("input[name=causeCodes]:checked").length == 0) {
			$(".error_causeCodes").show();
			errorFound = true;
		}
		if ($("textarea[name=action]").val().length > 4000) {
			$(".error_action").show();
			errorFound = true;
		}
		if ($("input[name=courierStaffId]").val().length == 0) {
			$(".error_courierStaffId").show();
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
	$("input[name=backButton]").click(function() {
		window.location.href = "mobccsorderrescuesearch.html";
	});

	</c:when>
	<c:otherwise>

	$("form[name=submitForm] input[type=text], form[name=submitForm] input[type=checkbox], form[name=submitForm] textarea").attr("disabled", true);
	$("input[name=backButton]").click(function() {
		window.location.href = "<c:url value="mobccsorderrescuesearch.html"><c:param name="orderId" value="${record.orderId}"/></c:url>";
	});

	</c:otherwise>
	</c:choose>
});
</script>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:choose>
<c:when test="${param.insertSuccess == 'true'}">
<div class="contenttextgreen">Record created</div>
</c:when>
<c:when test="${param.updateSuccess == 'true'}">
<div class="contenttextgreen">Record updated</div>
</c:when>
<c:when test="${param.statusReadonly == 'true'}">
<div class="contenttext_red">Record cannot be updated</div>
</c:when>
</c:choose>

<form action="mobccsorderrescueedit.html" method="post" name="submitForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:150px"></colgroup>
<tr class="contenttextgreen">
	<td class="table_title title_edit" colspan="2">Order Rescue</td>
</tr>
<c:choose>
<c:when test="${record == null}">
<tr>
	<td colspan="2">
	No record for Incident No: <c:out value="${param.recordIncidentNo}"/>
	</td>
</tr>
</c:when>
<c:otherwise>
<tr>
	<td>Order ID</td>
	<td><c:out value="${record.orderId}" /></td>
</tr>
<tr>
	<td>MSISDN</td>
	<td><c:out value="${record.msisdn}"/></td>
</tr>
<tr>
	<td>Order Status</td>
	<td>
	<c:out value="${record.orderStatusDesc}"/>
	<c:if test="${not empty record.checkPointDesc}"> - <c:out value="${record.checkPointDesc}"/></c:if>
	</td>
</tr>
<tr>
	<td>Incident No</td>
	<td><c:out value="${record.incidentNo}"/></td>
</tr>
<tr>
	<td>Cause Code</td>
	<td>
		<c:forEach var="cc" items="${causeCodes}">
		<c:set var="ccChecked" value="false"/>
		<c:forEach var="rcc" items="${incidentCauses}"><c:if test="${rcc.causeCode == cc.codeId}"><c:set var="ccChecked" value="true"/></c:if></c:forEach>
		<label><input type="checkbox" name="causeCodes" value="${cc.codeId}"<c:if test="${ccChecked}"> checked="checked"</c:if>><c:out value="${cc.codeDesc}"/></label>
		<div style="clear:both"></div>
		</c:forEach>
		<span class="error_msg error_causeCodes contenttext_red" style="display:none">Cause Code required</span>
	</td>
</tr>
<tr>
	<td>Action</td>
	<td>
		<textarea name="action" rows="5" cols="80"><c:out value="${record.action}"/></textarea>
		<div style="clear:both"></div>
		<span class="error_msg error_action contenttext_red" style="display:none">Action over 4000 chars.</span>
	</td>
</tr>
<tr>
	<td>Courier Team</td>
	<td><c:out value="${record.locationDesc}"/></td>
</tr>
<tr>
	<td>Courier Staff ID</td>
	<td>
		<input type="text" name="courierStaffId" maxlength="30" style="width:30em" value="<c:out value="${record.courierStaffId}"/>">
		<span class="error_msg error_courierStaffId contenttext_red" style="display:none">Courier Staff ID required</span>
	</td>
</tr>
<tr>
	<td>Remark</td>
	<td>
		<textarea name="remark" rows="5" cols="80"><c:out value="${record.remark}"/></textarea>
		<div style="clear:both"></div>
		<span class="error_msg error_remark contenttext_red" style="display:none">Remark over 4000 chars.</span>
	</td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td>
		<label><input type="checkbox" name="serialCancel" value="true"<c:if test="${record.serialCancel == 'Y'}"> checked="checked"</c:if>> Serial cancellation</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="visitMt1" value="true"<c:if test="${record.visitMt1 == 'Y'}"> checked="checked"</c:if>> Visit &gt; 1 at same day</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="orderSaved" value="true"<c:if test="${record.orderSaved == 'Y'}"> checked="checked"</c:if>> Order Saved</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="amendment" value="true"<c:if test="${record.amendment == 'Y'}"> checked="checked"</c:if>> Amendment to F&amp;S</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="doa" value="true"<c:if test="${record.doa == 'Y'}"> checked="checked"</c:if>> DOA</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="delContractOnly" value="true"<c:if test="${record.delContractOnly == 'Y'}"> checked="checked"</c:if>> Delivery contract only</label>
	</td>
</tr>
<tr>
	<td>Create date</td>
	<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${record.createDate}" /></td>
</tr>
<tr>
	<td>Handle by</td>
	<td><c:out value="${record.handleBy}" /></td>
</tr>
<tr>
	<td>Handle time</td>
	<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${record.handleTime}" /></td>
</tr>
</c:otherwise>
</c:choose>
<tr>
<td colspan="2">
<input type="button" name="backButton" value="Quit" style="float:right">
<input type="hidden" name="orderId" value="${record.orderId}">
<input type="hidden" name="rowId" value="${record.rowId}">
<c:if test="${(bomsalesuser.channelCd == 'QOM' || bomsalesuser.channelCd == 'CFM') && record.orderStatus == '01' && record.checkPoint == '599'}">
<input type="submit" value="Update Incident" style="float:right">
</c:if>
</td>
</tr>
</table>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>