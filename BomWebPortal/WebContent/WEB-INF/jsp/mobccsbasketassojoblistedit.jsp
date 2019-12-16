<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>

<script type="text/javascript">
function getDate(count) {
	var date = new Date();
	date.setHours(0);
	date.setMinutes(0);
	date.setSeconds(0);
	date.setMilliseconds(0);
	date.setDate(date.getDate() + count);
	return date;
}
function getToday() {
	return getDate(0);
}
$(document).ready(function() {
	$("form#editForm").submit(function(e) {
		$(".error").hide();

		$("input[name=jobListDesc]").val($.trim($("input[name=jobListDesc]").val()));
		
		if ($.trim($("input[name=jobListDesc]").val()).length == 0) {
			$(".error_jobListDesc").show();
		}
		if ($(".error:visible").length > 0) {
			e.preventDefault();
			return false;
		}
	});
	$("input[name=backButton]").click(function(e) {
		window.location.href = "mobccsbasketassojoblist.html";
	});
});
</script>

<style type="text/css">
.collapse_table { width: 100% }
.recordRow { width: 100%; padding: 2px 0 }
.blueHeader { background-color: rgb(232, 242, 254) }
.headerCol { font-weight:bold; text-align: center; display: inline-block }
.recordCol { text-align: center; display: inline-block }
.col0 { width: 10% }
.col1 { width: 30% }
.col2 { width: 20% }
.col3 { width: 10% }
.col4 { width: 10% }
.even { background-color: rgb(232,255,232); }
</style>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:choose>
<c:when test="${param.recordUpdated == 'true'}">
<div class="contenttextgreen">Record updated</div>
</c:when>
</c:choose>

<form:form method="POST" action="mobccsbasketassojoblistedit.html" id="editForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
	<td class="table_title" colspan="2">Job list Update</td>
</tr>
<tr>
	<td>Job List</td>
	<td><c:out value="${command.jobList}"/><form:hidden path="jobList"/></td>
</tr>
<tr>
	<td>Job List Description</td>
	<td>
		<form:input path="jobListDesc" maxlength="50" cssStyle="width:600px"/>
		<div style="clear:both"></div>
		<form:errors path="jobListDesc" cssClass="error"/>
		<span class="error error_jobListDesc" style="display:none">Please input Job List Description</span>
	</td>
</tr>
<tr>
	<td>Channel</td>
	<td><c:out value="${command.channelCd}"/><form:hidden path="channelCd"/></td>
</tr>
<tr>
	<td colspan="2">
		<form:hidden path="rowId"/>
		<input type="button" value="Quit" name="backButton" style="float:right">
		<input type="submit" value="Update" name="updateButton" style="float:right">
	</td>
</tr>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>