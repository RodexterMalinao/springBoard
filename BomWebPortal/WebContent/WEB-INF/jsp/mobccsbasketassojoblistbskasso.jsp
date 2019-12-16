<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("form[name=mobccsbasketassojoblistteamasso]").submit(function(e) {
		if ($("select[name=jobList]")[0].selectedIndex == 0) {
			alert("Please select a Job List");
			e.preventDefault();
			return false;
		}
	});
	$("input[name=newButton]").click(function(e) {
		if ($("#editJobListBskAsso select[name=editJobList]")[0].selectedIndex == 0) {
			alert("Please select a Job List");
			e.preventDefault();
			return false;
		}
		$("#editJobListBskAsso").attr("action", "mobccsbasketassojoblistbskassocreate.html").submit();
	});
	$("input[name=endButton]").click(function(e) {
		if ($("#editJobListBskAsso select[name=editJobList]")[0].selectedIndex == 0) {
			alert("Please select a Job List");
			e.preventDefault();
			return false;
		}
		$("#editJobListBskAsso").attr("action", "mobccsbasketassojoblistbskassoend.html").submit();
		//window.location.href = "mobccsbasketasso.html";
	});
	$("input[name=backButton]").click(function() {
		window.location.href = "mobccsbasketasso.html";
	});
	$(".basketDetail").click(function(e) {
		e.preventDefault();
		// http://msdn.microsoft.com/en-us/library/ie/ms536759%28v=vs.85%29.aspx
		var sURL = $(this).attr("href");
		var vArguments = new Object();
		var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
		window.showModalDialog(sURL, vArguments, sFeatures);
	});
	$("input[name=clearButton]").click(function() {
		$("select[name=jobList]")[0].selectedIndex = 0;
		$("select[name=editJobList]")[0].selectedIndex = 0;
		$("#resultList tbody").empty();
		$("input[name=showEnded]").attr("checked", false);
	});
	var selJobList = $("select[name=jobList]").val();
	$("select[name=editJobList] option").each(function() {
		if ($(this).val() == selJobList) {
			$(this).attr("selected", true);
			return false;
		}
	});
});
</script>

<style type="text/css">
.paddingLeft { padding-left: 20px; }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<form:form method="POST" name="mobccsbasketassojoblistteamasso">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
<td class="table_title">Job List / Basket Association Enquiry</td>
</tr>
<tr>
	<td>
		<div style="float:right">
			<form:checkbox path="showEnded" label="Search with History"/>
			<input type="submit" value="Search">
			<input type="button" name="clearButton" value="Clear">
		</div>
		<span>Job List</span>
		<form:select path="jobList">
			<form:option value="" label=""/>
			<form:options items="${jobListALL}"/>
		</form:select>
	</td>
</tr>
</table>
</form:form>

<div style="clear:both;padding-top:5px"></div>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="resultList">
<thead>
<tr>
	<td class="table_title">Job List</td>
	<td class="table_title">Basket ID</td>
	<td class="table_title">Basket Description</td>
	<td class="table_title">Start Date</td>
	<td class="table_title">End Date</td>
</tr>
</thead>
<tbody>
<c:choose>
<c:when test="${resultList == null}">
</c:when>
<c:when test="${empty resultList}">
<tr>
	<td colspan="5">No data</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${resultList}" var="r">
<tr>
	<td>${r.jobList}</td>
	<td><a class="basketDetail" href="<c:url value="mobccsbasketcomparedetail.html"><c:param name="basketIds" value="${r.basketId}"/></c:url>">${r.basketId}</a></td>
	<td>${r.basketDesc}</td>
	<td><fmt:formatDate pattern="dd/MM/yyyy" value="${r.startDate}"/></td>
	<td><fmt:formatDate pattern="dd/MM/yyyy" value="${r.endDate}"/></td>
</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</tbody>
</table>

<div style="clear:both;padding-top:5px"></div>

<form id="editJobListBskAsso" method="get">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
	<td class="table_title">Basket Association</td>
</tr>
<tr>
	<td>
		<input type="button" name="backButton" value="Quit" style="float:right">
		<input type="button" name="endButton" value="End Association" style="float:right">
		<input type="button" name="newButton" value="New Association" style="float:right">
		<span>Job List</span>
		<select name="editJobList">
		<option value=""></option>
		<c:forEach items="${jobListALL}" var="item">
		<option value="${item}">${item}</option>
		</c:forEach>
		</select>
	</td>
</tr>
</table>
</form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>