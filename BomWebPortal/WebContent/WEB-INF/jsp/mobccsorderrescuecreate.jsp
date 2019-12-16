<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
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
	
	<c:if test="${(bomsalesuser.channelCd != 'QOM' && bomsalesuser.channelCd != 'CFM')}">
	$("form[name=submitForm] input[type=text], form[name=submitForm] input[type=checkbox], form[name=submitForm] textarea").attr("disabled", true);
	</c:if>
});
</script>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:choose>
<c:when test="${param.statusReadonly == 'true'}">
<div class="contenttext_red">Record cannot be created</div>
</c:when>
</c:choose>

<form action="mobccsorderrescuecreate.html" method="post" name="submitForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:150px"></colgroup>
<tr class="contenttextgreen">
	<td class="table_title" colspan="2">Order Rescue (Create)</td>
</tr>
<c:choose>
<c:when test="${orderDto.orderSumLob == 'MOB' && orderDto.orderStatus == '01' && orderDto.checkPoint == '599'}">
<tr>
	<td>Order ID</td>
	<td><c:out value="${orderDto.orderId}" /></td>
</tr>
<tr>
	<td>MSISDN</td>
	<td><c:out value="${orderDto.msisdn}"/></td>
</tr>
<tr>
	<td>Order Status</td>
	<td>
		<c:out value="${orderStatusDesc}"/>
		<c:if test="${not empty checkPointDesc}"> - <c:out value="${checkPointDesc}"/></c:if>
	</td>
</tr>
<tr>
	<td>Cause Code</td>
	<td>
		<c:forEach var="cc" items="${causeCodes}">
		<c:set var="ccChecked" value="false"/>
		<label><input type="checkbox" name="causeCodes" value="${cc.codeId}"><c:out value="${cc.codeDesc}"/></label>
		<div style="clear:both"></div>
		</c:forEach>
		<span class="error_msg error_causeCodes contenttext_red" style="display:none">Cause Code required</span>
	</td>
</tr>
<tr>
	<td>Action</td>
	<td>
		<textarea name="action" rows="5" cols="80"></textarea>
		<div style="clear:both"></div>
		<span class="error_msg error_action contenttext_red" style="display:none">Action over 4000 chars.</span>
	</td>
</tr>
<tr>
	<td>Courier Team</td>
	<td><c:out value="${locationDesc}"/></td>
</tr>
<tr>
	<td>Courier Staff ID</td>
	<td>
		<input type="text" name="courierStaffId" maxlength="30" style="width:30em">
		<span class="error_msg error_courierStaffId contenttext_red" style="display:none">Courier Staff ID required</span>
	</td>
</tr>
<tr>
	<td>Remark</td>
	<td>
		<textarea name="remark" rows="5" cols="80"></textarea>
		<div style="clear:both"></div>
		<span class="error_msg error_remark contenttext_red" style="display:none">Remark over 4000 chars.</span>
	</td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td>
		<label><input type="checkbox" name="serialCancel" value="true">Serial cancellation</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="visitMt1" value="true">Visit &gt; 1 at same day</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="orderSaved" value="true">Order Saved</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="amendment" value="true">Amendment to F&amp;S</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="doa" value="true">DOA</label>
		<div style="clear:both"></div>
		<label><input type="checkbox" name="delContractOnly" value="true">Delivery contract only</label>
	</td>
</tr>
</c:when>
</c:choose>
<tr>
	<td colspan="2">
		<input type="button" name="backButton" value="Quit" style="float:right">
		<input type="hidden" name="orderId" value="${orderDto.orderId}">
		<c:if test="${(bomsalesuser.channelCd == 'QOM' || bomsalesuser.channelCd == 'CFM') && orderDto.orderSumLob == 'MOB' && orderDto.orderStatus == '01' && orderDto.checkPoint == '599'}">
		<input type="submit" value="Create Incident" style="float:right">
		</c:if>
	</td>
</tr>
</table>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>