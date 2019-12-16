<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
var existTeamCdALL = [];
<c:forEach items="${existTeamCdALL}" var="cd">
existTeamCdALL[existTeamCdALL.length] = "<c:out value="${cd}"/>";
</c:forEach>
var existCentreCdALL = [];
<c:forEach items="${existCentreCdALL}" var="cd">
existCentreCdALL[existCentreCdALL.length] = "<c:out value="${cd}"/>";
</c:forEach>
var existChannelCdALL = [];
<c:forEach items="${existChannelCdALL}" var="cd">
existChannelCdALL[existChannelCdALL.length] = "<c:out value="${cd}"/>";
</c:forEach>
var ccsChannelALL = [];
<c:forEach items="${ccsChannelALL}" var="ccsChannel">
ccsChannelALL[ccsChannelALL.length] = { channelCd: "<c:out value="${ccsChannel.channelCd}"/>", centreCd: "<c:out value="${ccsChannel.centreCd}"/>", teamCd: "<c:out value="${ccsChannel.teamCd}"/>"};
</c:forEach>
if(!Array.prototype.indexOf) {
    Array.prototype.indexOf = function(needle) {
        for(var i = 0; i < this.length; i++) {
            if(this[i] === needle) {
                return i;
            }
        }
        return -1;
    };
}
function addToSelect($select, list, selectedValue) {
	// recreate select to fix change event bug in IE6
	var $newSelect = $("<select/>").attr({"id": $select.attr("id"), "name": $select.attr("name")});
	var $emptyOption = $("<option/>");
	$emptyOption.val("").text(" * ");
	$newSelect.append($emptyOption);
	for (var i = 0; i < list.length; i++) {
		var $option = $("<option/>");
		$option.val(list[i]).text(list[i]);
		if (list[i] == selectedValue) {
			$option.attr("selected", true);
		}
		$newSelect.append($option);
	}
	$newSelect.change(updateOtherList);
	$select.after($newSelect);
	$select.unbind("change").remove();
}
function restoreOtherList() {
	addToSelect($("select[name=teamCd]"), existTeamCdALL);
	addToSelect($("select[name=centreCd]"), existCentreCdALL);
	addToSelect($("select[name=channelCd]"), existChannelCdALL);
}
function updateOtherList() {
	var centreCd = $("select[name=centreCd]").val();
	var teamCd = $("select[name=teamCd]").val();
	var channelCd = $("select[name=channelCd]").val();
	var matchCcsChannels = [];
	if ($("select[name=centreCd]")[0].selectedIndex == 0
			&& $("select[name=teamCd]")[0].selectedIndex == 0
			&& $("select[name=channelCd]")[0].selectedIndex == 0) {
		matchCcsChannels = ccsChannelALL;
	} else {
		for (var i = 0; i < ccsChannelALL.length; i++) {
			if (($("select[name=centreCd]")[0].selectedIndex == 0 || centreCd == ccsChannelALL[i].centreCd)
					&& ($("select[name=teamCd]")[0].selectedIndex == 0 || teamCd == ccsChannelALL[i].teamCd)
					&& ($("select[name=channelCd]")[0].selectedIndex == 0 || channelCd == ccsChannelALL[i].channelCd)) {
				matchCcsChannels[matchCcsChannels.length] = ccsChannelALL[i];
			}
		}
	}
	var centreCdList = [], teamCdList = [], channelCdList = [];
	for (var i = 0; i < matchCcsChannels.length; i++) {
		if (centreCdList.indexOf(matchCcsChannels[i].centreCd) == -1) {
			centreCdList[centreCdList.length] = matchCcsChannels[i].centreCd;
		}
		if (teamCdList.indexOf(matchCcsChannels[i].teamCd) == -1) {
			teamCdList[teamCdList.length] = matchCcsChannels[i].teamCd;
		}
		if (channelCdList.indexOf(matchCcsChannels[i].channelCd) == -1) {
			channelCdList[channelCdList.length] = matchCcsChannels[i].channelCd;
		}
	}
	addToSelect($("select[name=centreCd]"), centreCdList, centreCd);
	addToSelect($("select[name=teamCd]"), teamCdList, teamCd);
	addToSelect($("select[name=channelCd]"), channelCdList, channelCd);
}
$(document).ready(function() {
	$("input[name=newButton]").click(function() {
		var param = "";
		if ($("input[name=rowId]").is(":checked")) {
			param = "?" + $.param({recordJobList: $.trim($("input[name=rowId]:checked").parent().text())});
		}
		window.location.href = "mobccsbasketassojoblistteamassocreate.html" + param;
	});
	$("input[name=backButton]").click(function() {
		window.location.href = "mobccsbasketasso.html";
	});
	$("#searchForm").submit(function(e) {
		var errorFound = false;
		if ($("input[name=searchBy]").is(":checked")) {
			switch ($("input[name=searchBy]:checked").attr("id")) {
			case "searchByJobList":
				if ($("select[name=jobList]")[0].selectedIndex == 0) {
					alert("Please select Job List");
					errorFound = true;
				}
				break;
			case "searchByOther":
				if ($("select[name=centreCd]")[0].selectedIndex == 0
						&& $("select[name=teamCd]")[0].selectedIndex == 0
						&& $("select[name=channelCd]")[0].selectedIndex == 0) {
					alert("Please select Channel or Center or Team");
					errorFound = true;
				}
				break;
			default:
				errorFound = true;
			}
		} else {
			alert("Please select one 'Search by' method");
			errorFound = true;
		}
		if (errorFound) {
			e.preventDefault();
			return false;
		}
	});
	$("#resultListForm").submit(function(e) {
		if ($(this).find("input[name=rowId]:checked").length == 0) {
			e.preventDefault();
			alert("Please select one record");
			return false;
		}
	});
	$("#resultListForm tbody tr.data").each(function(index, tr) {
		var endDateStr = $(tr).find("td:last").text();
		var rowIdDisabled;
		if (endDateStr.length == 0) {
			rowIdDisabled = false;
		} else {
			var endDate = $.datepicker.parseDate('dd/mm/yy', endDateStr);
			rowIdDisabled = endDate < (new Date());
		}
		$(tr).find("input[name=rowId]").attr("disabled", rowIdDisabled);
	});
	$("input[name=clearButton]").click(function() {
		$("select[name=jobList]")[0].selectedIndex = 0;
		restoreOtherList();
		$("#resultListForm tbody").empty();
		$(".editButtons").hide();
		$("input[name=showEnded]").attr("checked", false);
	});
	$(".rowIdRadio").click(function() {
		if ($(this).is(":enabled")) {
			$(".editButtons").show();
		}
	});
	$("input[name=searchBy]").click(function() {
		var targetClass = $(this).attr("id");
		if (!$(".searchBy:visible").hasClass(targetClass)) {
			switch (targetClass) {
			case "searchByJobList":
				restoreOtherList();
				$("select[name=centreCd]")[0].selectedIndex = 0;
				$("select[name=teamCd]")[0].selectedIndex = 0;
				$("select[name=channelCd]")[0].selectedIndex = 0;
				break;
			case "searchByOther":
				$("select[name=jobList]")[0].selectedIndex = 0;
				break;
			}
		}
		$(".searchBy").hide();
		$("." + targetClass).show();
	});
	$(".searchByOther select").change(updateOtherList);
	updateOtherList();
	if ($("select[name=jobList]")[0].selectedIndex > 0) {
		$("#searchByJobList").click();
	} else if ($("select[name=centreCd]")[0].selectedIndex > 0
			|| $("select[name=teamCd]")[0].selectedIndex > 0
			|| $("select[name=channelCd]")[0].selectedIndex > 0) {
		$("#searchByOther").click();
	} else {
		if ($("input[name=searchBy]:checked").length == 0) {
			$("#searchByJobList").click();
		}
	}
});
</script>

<style type="text/css">
.paddingLeft { padding-left: 100px }
.clear2 { clear: both }
.searchLabel { display: inline-block }
.searchLabelFirst { width: 100px }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<form:form method="POST" name="mobccsbasketassojoblistteamasso" id="searchForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
	<td class="table_title">Job List / Team Association Enquiry</td>
</tr>
<tr>
	<td>
		<div style="float:right">
			<form:checkbox path="showEnded" label="Search with History" />
			<input type="submit" value="Search">
			<input type="button" name="clearButton" value="Clear">
		</div>
		<span class="searchLabel searchLabelFirst">Search by:</span> 
		<label for="searchByJobList">
			<input type="radio" name="searchBy" id="searchByJobList">
			Job List
		</label> 
		<label for="searchByOther">
			<input type="radio" name="searchBy" id="searchByOther">
			Channel / Center / Team
		</label>
		<div class="clear2"></div>
		<div class="searchBy searchByJobList" style="display:none">
			<span class="searchLabel searchLabelFirst">Job List</span>
			<form:select path="jobList">
				<form:option value="" label=""/>
				<form:options items="${existJobListALL}"/>
			</form:select>
		</div>
		<div class="clear2"></div>
		<div class="searchBy searchByOther" style="display:none">
			<span class="searchLabel searchLabelFirst searchChannel">Channel</span>
			<form:select path="channelCd">
				<form:option value="" label=" * "/>
				<form:options items="${existChannelCdALL}"/>
			</form:select>
			<span class="searchLabel paddingLeft searchCentreCd">Center</span>
			<form:select path="centreCd">
				<form:option value="" label=" * "/>
				<form:options items="${existCentreCdALL}"/>
			</form:select>
			<span class="searchLabel paddingLeft searchTeamCd">Team</span>
			<form:select path="teamCd">
				<form:option value="" label=" * "/>
				<form:options items="${existTeamCdALL}"/>
			</form:select>
		</div>
	</td>
</tr>
</table>
</form:form>

<div style="clear:both;padding-top:5px"></div>

<form method="get" action="mobccsbasketassojoblistteamassoedit.html" id="resultListForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
<tr>
	<td class="table_title"><span style="padding-left:15px">Job List</span></td>
	<td class="table_title">Center</td>
	<td class="table_title">Team</td>
	<td class="table_title">Channel</td>
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
	<td colspan="6">No data</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${resultList}" var="r">
<tr class="data">
	<td><label><input type="radio" name="rowId" value="${r.rowId}" disabled="disabled" class="rowIdRadio">${r.jobList}</label></td>
	<td>${r.centreCd}</td>
	<td>${r.teamCd}</td>
	<td>${r.channelCd}</td>
	<td><fmt:formatDate pattern="dd/MM/yyyy" value="${r.startDate}"/></td>
	<td><fmt:formatDate pattern="dd/MM/yyyy" value="${r.endDate}"/></td>
</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</tbody>
<tfoot>
<tr>
	<td colspan="6">
		<input type="button" name="backButton" value="Quit" style="float:right">
		<div class="editButtons" style="display:none;float:right">
			<input type="submit" name="endButton" value="End Association">
		</div>
		<input type="button" name="newButton" value="New Association">
	</td>
</tr>
</tfoot>
</table>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>