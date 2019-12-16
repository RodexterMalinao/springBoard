<%@ include file="/WEB-INF/jsp/header.jsp"
%>

<script type="text/javascript">
$(document).ready(function() {
	$("#searchForm,#insertForm").submit(function(e) {
		$(this).find(".error").remove();
		
		var $idDocNum = $(this).find("input[name=idDocNum]");
		$idDocNum.val($.trim($idDocNum.val()));
		
		if ($idDocNum.val().length == 0) {
			$idDocNum.after($("<span/>").addClass("error").text("Requires ID Doc Num"));
		}
		
		if ($(this).find(".error").length > 0) {
			e.preventDefault();
			return false;
		}
	});
	$(".search_button").click(function(e) {
		e.preventDefault();
		$("#searchForm").submit();
		return false;
	});
	$(".clear_button").click(function(e) {
		e.preventDefault();
		$("#searchForm select[name=idDocType],#insertForm select[name=idDocType]")[0].selectedIndex = 0;
		$("#searchForm input[name=idDocNum],#insertForm input[name=idDocNum]").val("");
		$("#insertForm select[name=eligibility]")[0].selectedIndex = 0;
		$(".orderSearchResult table tbody").empty();
		return false;
	});
	$(".insert_button").click(function(e) {
		e.preventDefault();
		$("#insertForm").submit();
		return false;
	});
});
</script>
<style type="text/css">
.orderSearch { background-color: white; border: solid 2px #C0C0C0; font-size: 12px }
.orderSearchButton { text-align: right; padding: 0 0.5em 0.5em 0 }
.orderSearchLabel { display: inline-block; width: 90px; padding-left: 2em }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.orderSearchResult { background-color: white }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.orderSearch { padding-left: 0 }
.orderSearchResult { padding-bottom: 1em }
</style>
<![endif]-->

<form:form action="mobccseligibility.html" method="get" id="searchForm">
<div class="orderSearch">
	<h2 class="table_title" style="font-size:medium;margin:0">Eligibility Maintenance</h2>
	<span class="orderSearchLabel">ID Doc Type:</span>
	<form:select path="idDocType">
		<form:options items="${idDocTypes}"/>
	</form:select>
	<span class="orderSearchLabel">ID Doc Num:</span>
	<form:input path="idDocNum"/>
	<c:if test="${idDocNumValid == false}">
	<span class="error">
	<c:choose>
	<c:when test="${command.idDocType == 'HKID'}">Invalid HKID number</c:when>
	<c:when test="${command.idDocType == 'PASS'}">Invalid Passport number</c:when>
	<c:when test="${command.idDocType == 'BS'}">Invalid HKBR number</c:when>
	</c:choose>
	</span>
	</c:if>
	<div style="clear: both"></div>
	<div class="buttonmenubox_R" id="buttonArea">
		<a href="#" class="nextbutton search_button">Search</a>
		<a href="#" class="nextbutton clear_button">Clear</a>
	</div>
</div>
</form:form>

<div class="overflowDiv orderSearchResult" style="margin-top:5px">
<table class="contenttext" border="1" style="width: 100%; background-color: #FFFFFF; border-spacing: 0px">
<thead>
	<tr class="contenttextgreen">
		<th class="table_title">ID Doc Type</th>
		<th class="table_title">ID Doc Num</th>
		<th class="table_title">Eligibility</th>
		<th class="table_title">Create By</th>
		<th class="table_title">Create Date</th>
		<th class="table_title">Last Update By</th>
		<th class="table_title">Last Update Date</th>
	</tr>
</thead>
<tbody>
	<c:choose>
	<c:when test="${resultList == null}">
	</c:when>
	<c:when test="${empty resultList}">
	<tr>
		<td colspan="7">No result</td>
	</tr>
	</c:when>
	<c:otherwise>
	<c:forEach items="${resultList}" var="r">
	<tr>
		<td><c:out value="${r.idDocType}"/></td>
		<td><c:out value="${r.idDocNum}"/></td>
		<td><c:out value="${r.valuePropDesc}"/></td>
		<td><c:out value="${r.createBy}"/></td>
		<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${r.createDate}"/></td>
		<td><c:out value="${r.lastUpdBy}"/></td>
		<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${r.lastUpdDate}"/></td>
	</tr>
	</c:forEach>
	</c:otherwise>
	</c:choose>
</tbody>
</table>
</div>

<form:form action="mobccseligibility.html" method="post" id="insertForm" cssStyle="margin-top:5px">
<div class="orderSearch">
	<h2 class="table_title" style="font-size:medium;margin:0">Add Eligibility</h2>
	<span class="orderSearchLabel">ID Doc Type:</span>
	<form:select path="idDocType">
		<form:options items="${idDocTypes}"/>
	</form:select>
	<form:errors path="idDocType" cssClass="error"/>
	<div style="clear: both"></div>
	<span class="orderSearchLabel">ID Doc Num:</span>
	<form:input path="idDocNum"/>
	<form:errors path="idDocNum" cssClass="error"/>
	<div style="clear: both"></div>
	<span class="orderSearchLabel">Eligibility:</span>
	<form:select path="eligibility">
		<form:options items="${eligibilities}" itemLabel="valuePropDesc" itemValue="valuePropId"/>
	</form:select>
	<form:errors path="eligibility" cssClass="error"/>
	<div style="clear: both"></div>
	<div class="buttonmenubox_R" id="buttonArea">
		<a href="#" class="nextbutton insert_button">Save</a>
		<a href="welcome.html" class="nextbutton quit_button">Quit</a>
	</div>
</div>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>