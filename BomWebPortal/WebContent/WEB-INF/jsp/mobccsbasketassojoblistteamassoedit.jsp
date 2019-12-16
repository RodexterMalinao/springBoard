<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
function getToday() {
	var date = new Date();
	date.setHours(0);
	date.setMinutes(0);
	date.setSeconds(0);
	date.setMilliseconds(0);
	return date;
}
$(document).ready(function() {
	$("input[name=backButton]").click(function() {
		window.location.href = "mobccsbasketassojoblistteamasso.html";
	});
	
	var endDateDisabled;
	if ($("#endDateDatepicker").val().length == 0) {
		endDateDisabled = false;
	} else {
		var endDate = $.datepicker.parseDate('dd/mm/yy', $("#endDateDatepicker").val());
		endDateDisabled = endDate < (new Date());
	}
	if (endDateDisabled) {
		$("input[name=updateButton]").hide();
	} else {
		$('#endDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,

			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "-100Y",
			yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		//yearRange: "1911:1993" //the range shown in the year selection box
		});
		$("input[name=updateButton]").show();
	}
	$("input[name=updateButton]").attr("disabled", endDateDisabled);
	$("#editForm").submit(function(e) {
		$(".error").hide();
		if ($("input[name=endDate]").val().length == 0) {
			$(".error_endDate").text("Please select End Date").show();
		} else {
			var startDate = $.datepicker.parseDate("dd/mm/yy", $("input[name=startDate]").val());
			var endDate = $("#endDateDatepicker").datepicker("getDate");
			if (endDate < getToday()) {
				$(".error_endDate").text("Please select End Date on / after Today").show();
			} else if (endDate < startDate) {
				$(".error_endDate").text("Please select End Date on / after Start Date").show();
			}  
		}
		if ($(".error:visible").length > 0) {
			e.preventDefault();
			return false;
		}
	});
});
</script>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:choose>
<c:when test="${param.recordInsert == 'true'}">
<div class="contenttextgreen">Record inserted</div>
</c:when>
<c:when test="${param.recordUpdated == 'true'}">
<div class="contenttextgreen">Record updated</div>
</c:when>
<c:when test="${param.recordUpdated == 'false'}">
<div class=contenttext_red>Record update failed</div>
</c:when>
</c:choose>

<form:form method="POST" action="mobccsbasketassojoblistteamassoedit.html" id="editForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:200px"></colgroup>
<tr class="contenttextgreen">
	<td class="table_title" colspan="9">Job List / Team Association Updating</td>
</tr>
<tr>
	<td>Job List</td>
	<td><c:out value="${command.jobList}"/><form:hidden path="jobList"/></td>
</tr>
<tr>
	<td>Channel</td>
	<td><c:out value="${command.channelCd}"/><form:hidden path="channelCd"/></td>
</tr>
<tr>
	<td>Center</td>
	<td><c:out value="${command.centreCd}"/><form:hidden path="centreCd"/></td>
</tr>
<tr>
	<td>Team</td>
	<td><c:out value="${command.teamCd}"/><form:hidden path="teamCd"/></td>
</tr>
<tr>
	<td>Start Date</td>
	<td><c:out value="${command.startDate}"/><form:hidden path="startDate"/></td>
</tr>
<tr>
	<td>End Date</td>
	<td>
		<form:input path="endDate" id="endDateDatepicker" readonly="true"/>
		<div style="clear:both"></div>
		<span class="error error_endDate" style="display:none"></span>
	</td>
</tr>
<tr>
	<td colspan="2">
		<form:hidden path="rowId"/>
		<input type="button" name="backButton" value="Quit" style="float:right">
		<input type="submit" name="updateButton" value="Update" disabled="disabled" style="display:none;float:right">
	</td>
</tr>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>