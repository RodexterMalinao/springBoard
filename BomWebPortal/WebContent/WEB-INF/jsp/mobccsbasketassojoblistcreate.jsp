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
	$("form#createForm").submit(function(e) {
		$(".error").hide();
		
		$("input[name=jobList]").val($.trim($("input[name=jobList]").val()));
		$("input[name=jobListDesc]").val($.trim($("input[name=jobListDesc]").val()));
		
		if ($.trim($("input[name=jobList]").val()).length == 0) {
			$(".error_jobList").show();
		}
		if ($.trim($("input[name=jobListDesc]").val()).length == 0) {
			$(".error_jobListDesc").show();
		}
		if ($("select[name=channelCd]")[0].selectedIndex < 1) {
			$(".error_channelCd").show();
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
<c:when test="${param.recordInsert == 'true'}">
<div class="contenttextgreen">Record inserted</div>
</c:when>
<c:when test="${param.recordExist == 'true'}">
<div class="contenttext_red">Record exists for Job List: ${param.recordJobList}</div>
</c:when>
</c:choose>

<form:form method="POST" action="mobccsbasketassojoblistcreate.html" id="createForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:25%"></colgroup>
<tr class="contenttextgreen">
	<td class="table_title" colspan="2">Add New Job list</td>
</tr>
<tr>
	<td>Job List</td>
	<td>
		<form:input path="jobList" maxlength="20" cssStyle="width:600px"/>
		<div style="clear:both"></div>
		<form:errors path="jobList" cssClass="error"/>
		<span class="error error_jobList" style="display:none">Please input Job List</span>
	</td>
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
	<td>
		<form:select path="channelCd">
			<form:option value="" label=""/>
			<form:options items="${channelCdALL}" itemValue="codeId" itemLabel="codeId"/>
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="channelCd" cssClass="error"/>
		<span class="error error_channelCd" style="display:none">Please select Channel</span>
	</td>
</tr>
<tr>
	<td colspan="2">
		<input type="button" value="Quit" name="backButton" style="float:right">
		<input type="submit" value="Add" name="addButton" style="float:right">
	</td>
</tr>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>