<%@ include file="/WEB-INF/jsp/header.jsp"%>
<script type="text/javascript">
function changeSearchBy() {
	switch ($("input[name=searchBy]:checked").attr("id")) {
	case "searchByJobList":
		$(".searchByJobList select")[0].selectedIndex = 0;
		$(".searchByJobList select").attr("disabled", false);
		$(".searchByChannel select")[0].selectedIndex = 0;
		$(".searchByChannel select").attr("disabled", true);
		break;
	case "searchByChannel":
		$(".searchByJobList select")[0].selectedIndex = 0;
		$(".searchByJobList select").attr("disabled", true);
		$(".searchByChannel select")[0].selectedIndex = 0;
		$(".searchByChannel select").attr("disabled", false);
		break;
	}
}
$(document).ready(function() {
	$("input[name=newButton]").click(function() {
		window.location.href = "mobccsbasketassojoblistcreate.html";
	});
	$("input[name=backButton]").click(function() {
		window.location.href = "mobccsbasketassomain.html";
	});
	$("#resultListForm").submit(function(e) {
		if ($(this).find("input[name=rowId]:checked").length == 0) {
			e.preventDefault();
			alert("Please select one record");
			return false;
		}
	});
	$("input[name=clearButton]").click(function() {
		$("select[name=jobList]")[0].selectedIndex = 0;
		$("select[name=channelCd]")[0].selectedIndex = 0;
		$("#resultListForm tbody").empty();
	});
	$("input[name=searchBy]").click(changeSearchBy);
	if ($("select[name=channelCd]")[0].selectedIndex > 0) {
		$("#searchByChannel").attr("checked", true);
		$(".searchByJobList select").attr("disabled", true);
		$(".searchByChannel select").attr("disabled", false);
	} else {
		$(".searchByJobList select").attr("disabled", false);
		$(".searchByChannel select").attr("disabled", true);
	}
});
</script>

<style type="text/css">
.paddingLeft { padding-left: 20px; }
.searchLabel { display: inline-block }
.searchLabelFirst { width: 100px }
.selectLabel { display: inline-block; width: 100px }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:choose>
<c:when test="${param.recordAssoNotYetEnded == 'true'}">
<div class="contenttext_red">There are association with ${param.recordJobList}</div>
</c:when>
<c:when test="${param.recordAssoNotYetEnded == 'false'}">
<div class="contenttextgreen">Removed Job List: ${param.recordJobList}</div>
</c:when>
</c:choose>

<form:form method="POST" name="mobccsbasketassojoblist">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
	<td class="table_title">Job List Enquiry</td>
</tr>
<tr>
	<td>
		<div style="float:right">
			<input type="submit" value="Search">
			<input type="button" name="clearButton" value="Clear">
		</div>
		<div class="searchByJobList" style="display:inline-block">
		<span class="searchLabel searchLabelFirst">Search by:</span>
		<label for="searchByJobList" class="selectLabel"><input type="radio" name="searchBy" id="searchByJobList" checked="checked">Job List</label>
		<form:select path="jobList">
			<form:option value="" label=""/>
			<form:options items="${existJobListALL}"/>
		</form:select>
		</div>
		<div class="searchByChannel" style="display:inline-block">
		<span class="searchLabel searchLabelFirst">&nbsp;</span>
		<label for="searchByChannel" class="selectLabel"><input type="radio" name="searchBy" id="searchByChannel">Channel</label>
		<form:select path="channelCd">
			<form:option value="" label=""/>
			<form:options items="${channelCdALL}" itemValue="codeId" itemLabel="codeId"/>
		</form:select>
		</div>
	</td>
</tr>
</table>
</form:form>

<div style="clear:both;padding-top:5px"></div>

<form method="get" action="mobccsbasketassojoblistedit.html" id="resultListForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
<tr>
	<td class="table_title"><span style="padding-left:15px">Job List</span></td>
	<td class="table_title">Description</td>
	<td class="table_title">Channel</td>
</tr>
</thead>
<tbody>
<c:choose>
<c:when test="${resultList == null}">
</c:when>
<c:when test="${empty resultList}">
<tr>
	<td colspan="3">No data</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${resultList}" var="r">
<tr>
	<td><label><input type="radio" name="rowId" value="<c:out value="${r.rowId}"/>"><c:out value="${r.jobList}"/></label></td>
	<td><c:out value="${r.jobListDesc}"/></td>
	<td><c:out value="${r.channelCd}"/></td>
</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</tbody>
<tfoot>
<tr>
	<td colspan="3">
		<input type="button" name="backButton" value="Quit" style="float:right">
		<input type="submit" name="editButton" value="Edit" style="float:right">
		<input type="button" name="newButton" value="New Job List" style="float:right">
	</td>
</tr>
</tfoot>
</table>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>