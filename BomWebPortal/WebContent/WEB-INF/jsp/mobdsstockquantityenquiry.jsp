<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@page import="java.util.List, net.sf.json.*, com.bomwebportal.mob.ccs.dto.StockDTO"%>

<!-- Store code and team code JSON -->
<%
JSONArray storeListJson = new JSONArray();
if (request.getAttribute("storeList") instanceof List<?>) {
	for (StockDTO store : (List<StockDTO>) request.getAttribute("storeList")) {
		JSONObject json = new JSONObject();
		json.put("storeCode", store.getStoreCode());
		storeListJson.add(json);
	}
}
pageContext.setAttribute("storeListJson", storeListJson);
JSONArray teamListJson = new JSONArray();
if (request.getAttribute("teamListAll") instanceof List<?>) {
	for (StockDTO team : (List<StockDTO>) request.getAttribute("teamListAll")) {
		JSONObject json = new JSONObject();
		json.put("teamCode", team.getTeamCode());
		json.put("storeCode", team.getStoreCode());
		teamListJson.add(json);
	}
}
pageContext.setAttribute("teamListJson", teamListJson);
%>


<script type="text/javascript">
var storeListJson = <c:out value="${storeListJson}" escapeXml="false"/>;
var teamListJson = <c:out value="${teamListJson}" escapeXml="false"/>;

function isBlank(str) {
	return str == null || $.trim(str).length == 0;
}
function isDefault(str) {
	return isBlank(str) || str == "NONE" || str == "";
}
function storeChanged() {
	var selectingStoreCode = $("select[name=storeCode]").val();
	if (selectingStoreCode == "") {
		$("select[name=teamCode]").attr("disabled", true);
		$("select[name=teamCode] option:eq(0)").attr("selected", true);
	} else {
		$("select[name=teamCode]").attr("disabled", false);
		$("select[name=teamCode] option:gt(0)").remove();
		$.each(teamListJson, function(index, team) {
			if (isDefault(selectingStoreCode) || team.storeCode == selectingStoreCode) {
				var $option = $("<option/>").text(team.teamCode).val(team.teamCode);
				$("select[name=teamCode]").append($option);
			}
		});
	}
}

function updatePaging() {
	var $trs = $(".histList tbody tr");
	$(".control").show().data("index", 0);
	$(".control .left a").hide();
	if ($trs.length > 20) {
		$(".control .right a").show();
	} else {
		$(".control .right a").hide();
	}
	$(".control .label").text("1 - " + $(".histList tbody tr:visible").length + " / " + $trs.length);
	$(".control .left a").unbind("click").click(function(e) {
		e.preventDefault();
		$trs.hide();
		var start = $(".control").data("index") - 20;
		if (start < 0) {
			start = 0;
		}
		var end = start + 20;
		if (end > $trs.length) {
			end = $trs.length;
		}
		if (start > 0) {
			$(".control .left a").show();
		} else {
			$(".control .left a").hide();
		}
		if (end < $trs.length) {
			$(".control .right a").show();
		} else {
			$(".control .right a").hide();
		}
		$trs.slice(start, end).show();
		$(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
		$(".control").data("index", start);
		return false;
	});
	$(".control .right a").unbind("click").click(function(e) {
		e.preventDefault();
		$trs.hide();
		var start = $(".control").data("index") + 20;
		if (start > $trs.length) {
			start = $trs.length;
		}
		var end = start + 20;
		if (end > $trs.length) {
			end = $trs.length;
		}
		if (start > 0) {
			$(".control .left a").show();
		} else {
			$(".control .left a").hide();
		}
		if (end < $trs.length) {
			$(".control .right a").show();
		} else {
			$(".control .right a").hide();
		}
		$trs.slice(start, end).show();
		$(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
		$(".control").data("index", start);
		return false;
	});
}
	function onSearch() {
		
		if (($("#type").val() == null) && ($("#model").val() == "")) {
			alert("Please input the searching criteria!");
			return;
		} 

		var $tbody = $(".selectResultTable tbody");
		$tbody.empty();
		$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Loading...")));
		$.ajax({
			url: "mobdsstockquantityenquirysearch.html"
			, data: { "storeCode": $("#storeCode option:selected").val()
					, "teamCode": $("#teamCode option:selected").val()
					, "stockPool": $("#stockPool option:selected").val()
					, "eventCode": $("#eventCode option:selected").val()
					, "type": $("#type option:selected").val()
					, "itemCode": $("#itemCode").val()
					, "model": $.trim($("#model").val()) }
			, cache: false
			, dataType: "json"
			, success: function(data) {
				if (!data || data.length == 0) {
					$tbody.empty();
					$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("No result")));
				} else {
					$tbody.empty();
					$.each(data, function(index, item) {
						var $tr = $("<tr/>");
						if (index % 2 == 0) { 
							$tr.addClass("even"); 
						}
						if (index >= 20) {
							$tr.hide();
						}
						var $td0 = $("<td/>").attr("align", "center").text(item.type);
						var $td1 = $("<td/>").attr("align", "center").text(item.itemDesc);
						var $td2 = $("<td/>").attr("align", "center").text(item.itemCode);
						var $td3 = $("<td/>").attr("align", "center").text(item.sos);
						var $td4 = $("<td/>").attr("align", "center").text(item.rss);
						var $td5 = $("<td/>").attr("align", "center").text(item.rwh);
						var $td6 = $("<td/>").attr("align", "center").text(item.assign);
						var $td7 = $("<td/>").attr("align", "center").text(item.sold);
						$tbody.append($tr.append($td0).append($td1).append($td2));
						$tr.append($td3).append($td4).append($td5).append($td6).append($td7);
					});
				}
				updatePaging();
			}
			, error: function() {
				$tbody.empty();
				$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Error found, please retry")));
			}
		});
	}
	
	$(document).ready(function() {
		var selectedStoreCode = $("select[name=storeCode]").val();
		var selectedTeamCode = $("select[name=teamCode]").val();
		var sessionStoreCode = "<c:out value="${stockQuantityEnquiry.storeCode}"/>";
		var sessionTeamCode = "<c:out value="${stockQuantityEnquiry.teamCode}"/>";
		
		$.each(storeListJson, function(index, store) {
			var $option = $("<option/>").text(store.storeCode).val(store.storeCode);
			if ((!isDefault(selectedStoreCode) && store.storeCode == selectedStoreCode) || store.storeCode == sessionStoreCode) {
				$option.attr("selected", true);
			}
			$("select[name=storeCode]").append($option);
		});
		$("select[name=storeCode] option:eq(0)").attr("selected", true);
		$.each(teamListJson, function(index, team) {
			if ((isDefault(sessionStoreCode) && isDefault(selectedStoreCode)) 
					|| (sessionStoreCode == team.storeCode || selectedStoreCode == team.storeCode)) {
				var $option = $("<option/>").text(team.teamCode).val(team.teamCode);
				if ((!isDefault(selectedTeamCode) && selectedTeamCode == team.teamCode) || team.teamCode == sessionTeamCode) {
					$option.attr("selected", true);
				}
				$("select[name=teamCode]").append($option);
			}
		});
		$("select[name=storeCode]").change(storeChanged);
		
		storeChanged();
		
		$("#stockQuantityEnquiry").keypress(function(e) {
			switch (e.keyCode) {
			case 13: //enter
				onSearch();
				return false;
			}
		});
		
		$(".searchBtn").click(function(e) {
			e.preventDefault();
			onSearch();
			return false;
		});
		
		$(".clearBtn").click(function(e) {
			e.preventDefault();
			$("#storeCode")[0].selectedIndex = 0;
			$("#teamCode")[0].selectedIndex = 0;
			$("#eventCode")[0].selectedIndex = 0;
			$("#type")[0].selectedIndex = 0;
			$("#itemCode").val("");
			$("#model").val("");
			$(".selectResultTable:last tbody").empty();
			return false;
		});
	});
</script>

<style type="text/css">
.selectResultTable { width: 100%; border-collapse: separate; border-width: 0px; border-spacing: 0px; }
.selectResultTable tr { padding: 3px 0 }
.selectResult { width: 100%; height: 300px; overflow-y: auto; }
.even { background-color: rgb(232,255,232); }
.searchTitle { text-align: left; }
.blueHeader { background-color: rgb(232, 242, 254) }
.supportDocForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; font-size: small }
.supportDocForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.label { display: inline-block; width: 150px; padding-left: 3em }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<div class="supportDocForm">
<form:form name="stockQuantityEnquiryForm" method="POST" commandName="stockQuantityEnquiry">
<h3 class="table_title">Item Quantity Enquiry</h3>
	<c:choose>
	<c:when test="${bomsalesuser.category != 'FRONTLINE'}">
		<span class="label">Store Code:</span>
		<form:select path="storeCode">
		</form:select>
		
		<div style="clear:both"></div>
		<span class="label">Team Code:</span>
		<form:select path="teamCode">
			<form:option value="" label="Select" />
		</form:select>
		<div style="clear:both"></div>
	</c:when>
	<c:otherwise>
		<input type="hidden" name="storeCode" value="" />
		<input type="hidden" name="teamCode" value="" />
	</c:otherwise>
	</c:choose>
	<span class="label">Stock Pool:</span>
	<form:select path="stockPool">
		<form:options items="${stockPoolList}" itemValue="parmValue" itemLabel="parmValue" />
	</form:select>
	<div style="clear:both"></div>
	<span class="label">Event:</span>
	<form:select path="eventCode">
		<form:option value="" label=""/>
		<form:options items="${eventList}" itemValue="eventCode" itemLabel="eventCode" />
	</form:select>
	<div style="clear:both"></div>
	<span class="label">Type:</span>
	<form:select path="type">
		<form:options items="${typeList}" itemValue="codeId" itemLabel="codeDesc" />
	</form:select>
	<div style="clear:both"></div>
	<span class="label">Item Code:</span>
	<form:input path="itemCode" maxlength="10" />
	<div style="clear:both"></div>
	<span class="label">Model:</span>
	<form:input path="model" cssStyle="width: 300px"/>
	<div style="clear:both"></div>
	<div class="buttonmenubox_R">
		<input type="hidden" name="actionType" id="actionType" />
		<a href="#" class="nextbutton searchBtn">Search</a>
		<a href="#" class="nextbutton clearBtn">Clear</a>
	</div>
</form:form>
</div>

<div style="clear: both"></div>

<div class="supportDocForm histList">
<table class="selectResultTable">
	<colgroup width="10%"></colgroup>
	<colgroup width="20%"></colgroup>
	<colgroup width="10%"></colgroup>
	<colgroup width="10%"></colgroup>
	<colgroup width="10%"></colgroup>
	<colgroup width="10%"></colgroup>
	<colgroup width="10%"></colgroup>
	<colgroup width="10%"></colgroup>
<thead>
	<tr class="blueHeader">
		<th>Type</th>
		<th>Item Desc.</th>
		<th>Item Code</th>
		<th>SOS</th>
		<th>RSS</th>
		<th>RWH</th>
		<th>Assign</th>
		<th>Sold</th>
	</tr>
</thead>
<tbody>
</tbody>
</table>

<div class="contenttext" style="text-align: center; padding: 5px 0; background-color: white">
	<span class="control">
		<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
		<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
		<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
	</span>
</div>

</div>

<div class="buttonmenubox_R">
	<a href="mobdsstock.html" class="nextbutton">Quit</a>
</div>
<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>