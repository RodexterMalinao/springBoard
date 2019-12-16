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
	$('#startDateDatepicker').datepicker({
		changeMonth : true,
		changeYear : true,

		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-100Y",
		yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
	//yearRange: "1911:1993" //the range shown in the year selection box
	});
	$('#endDateDatepicker').datepicker({
		changeMonth : true,
		changeYear : true,

		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-100Y",
		yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
	//yearRange: "1911:1993" //the range shown in the year selection box
	});

	$("input[name=backButton]").click(function() {
		window.location.href = "mobccsbasketassojoblistteamasso.html";
	});
	
	// event on change
	$("select[name=jobList]").change(function(e) {
		$("input[name=channelCd]").attr("disabled", true).val("");
		$("select[name=centreCd]").attr("disabled", true)[0].selectedIndex = 0;
		$("select[name=teamCd]").attr("disabled", true)[0].selectedIndex = 0;
		$("input[name=startDate]").val("");
		$("input[name=endDate]").val("");
		$("#createForm").submit();
	});
	$("select[name=centreCd]").change(function(e) {
		$("select[name=teamCd]").attr("disabled", true)[0].selectedIndex = 0;
		$("input[name=startDate]").val("");
		$("input[name=endDate]").val("");
		$("#createForm").submit();
	});
	$("select[name=teamCd]").change(function(e) {
		$("input[name=addButton]").attr("disabled", $(this)[0].selectedIndex == 0);
	});
	if ($("select[name=centreCd]").val() == "ALL") {
		$("select[name=teamCd] option").each(function() {
			if ($(this).val() == "ALL") {
				$(this).attr("selected", true);
				$("input[name=addButton]").attr("disabled", false);
				return false;
			}
		});
	} else {
		$("input[name=addButton]").attr("disabled", $("select[name=teamCd]")[0].selectedIndex == 0);
	}
	$("#createForm").submit(function(e) {
		if ($("select[name=jobList]")[0].selectedIndex > 0
				&& $("input[name=channelCd]").val().length > 0
				&& $("select[name=centreCd]")[0].selectedIndex > 0
				&& $("select[name=teamCd]")[0].selectedIndex > 0
				) {
			$(".error").hide();
			if ($("input[name=startDate]").val().length == 0) {
				$(".error_startDate").text("Please select Start Date").show();
			} else {
				var startDate = $("#startDateDatepicker").datepicker("getDate");
				if (startDate < getToday()) {
					$(".error_startDate").text("Please select Start Date on / after today").show();
				} else {
					if ($("input[name=endDate]").val().length > 0) {
						var endDate = $("#endDateDatepicker").datepicker("getDate");
						if (endDate < startDate) {
							$(".error_endDate").text("Please select End Date on / after Start Date").show();
						}
					}
				}
			}
			if ($(".error:visible").length > 0) {
				e.preventDefault();
				return false;
			}	
		}
	});
});
</script>

<style type="text/css">
.recordRow { width: 100%; padding: 2px 0 }
.blueHeader { background-color: rgb(232, 242, 254) }
.headerCol { font-weight:bold; text-align: center; display: inline-block }
.recordCol { text-align: center; display: inline-block }
.col0 { width: 20% }
.col1 { width: 20% }
.col2 { width: 20% }
.col3 { width: 20% }
.col4 { width: 16%; float: right }
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
</c:choose>

<form:form method="POST" name="mobccsbasketassojoblistteamassocreate" id="createForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:100px"></colgroup>
<tr class="contenttextgreen">
	<td class="table_title" colspan="2">Job List / Team New Association</td>
</tr>
<tr>
	<td>Job List</td>
	<td>
		<form:select path="jobList">
			<form:option value="" label=""/>
			<form:options items="${existJobListALL}"/>
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="jobList" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Channel</td>
	<td>
		<form:input path="channelCd" readonly="true"/>
		<div style="clear:both"></div>
		<form:errors path="channelCd" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Center</td>
	<td>
		<form:select path="centreCd">
			<form:option value="" label=""/>
			<form:options items="${existSalesAssignCentreCdALL}"/>
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="centreCd" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Team</td>
	<td>
		<form:select path="teamCd">
			<form:option value="" label=""/>
			<form:options items="${existSalesAssignTeamCdALL}"/>
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="teamCd" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Start Date</td>
	<td>
		<form:input path="startDate" id="startDateDatepicker" readonly="true"/>
		<div style="clear:both"></div>
		<form:errors path="startDate" cssClass="error"/>
		<span class="error error_startDate" style="display:none"></span>
	</td>
</tr>
<tr>
	<td>End Date</td>
	<td>
		<form:input path="endDate" id="endDateDatepicker" readonly="true"/>
		<div style="clear:both"></div>
		<form:errors path="endDate" cssClass="error"/>
		<span class="error error_endDate" style="display:none"></span>
	</td>
</tr>
<c:if test="${not empty command.overlapRecords}">
<tr>
	<td colspan="2">
		<span class="contenttext_red">Overlap with following records:</span>
		<div class="recordRow blueHeader">
			<span class="headerCol col4"></span>
			<span class="headerCol col0">Center</span>
			<span class="headerCol col1">Team</span>
			<span class="headerCol col2">Start Date</span>
			<span class="headerCol col3">End Date</span>
		</div>
		<div style="clear:both"></div>
		<c:forEach var="r" items="${command.overlapRecords}" varStatus="status">
		<div class="recordRow<c:if test="${status.index % 2 == 0}"> even</c:if>">
			<span class="recordCol col4"><a href="<c:url value="mobccsbasketassojoblistteamassoedit.html"><c:param name="rowId" value="${r.rowId}"/></c:url>" target="_blank">View</a></span>
			<span class="recordCol col0"><c:out value="${r.centreCd}"/></span>
			<span class="recordCol col1"><c:out value="${r.teamCd}"/></span>
			<span class="recordCol col2"><fmt:formatDate pattern="dd/MM/yyyy" value="${r.startDate}"/></span>
			<span class="recordCol col3"><fmt:formatDate pattern="dd/MM/yyyy" value="${r.endDate}"/></span>
		</div>
		<div style="clear:both"></div>
		</c:forEach>
	</td>
</tr>
</c:if>
<tr>
	<td colspan="2">
		<input type="button" name="backButton" value="Quit" style="float:right">
		<input type="submit" name="addButton" value="Add" disabled="disabled" style="float:right">
	</td>
</tr>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>