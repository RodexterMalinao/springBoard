<%@ include file="/WEB-INF/jsp/header.jsp"%>
<c:set var="adminUser" value="${bomsalesuser.channelId != 2 && bomsalesuser.channelId != 68 && bomsalesuser.channelId != 67}"/>

<script type="text/javascript">
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
		$trs.slice(start, end).css("display", "table-row");
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

		var adminUser = <c:out value="${adminUser}"/>;
		var $tbody = $(".selectResultTable tbody");
		$tbody.empty();
		$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Loading...")));
		$.ajax({
			url: "mobccsstockquantityenquirysearch.html"
			, data: { "stockPool": $("#stockPool option:selected").val(), "type": $("#type option:selected").val(), "itemCode": $("#itemCode").val(), "model": $.trim($("#model").val()) }
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
						
						var $td3 = $("<td/>").attr("align", "center").text(item.free);
						$tbody.append($tr.append($td0).append($td1).append($td2).append($td3));
						
						if (adminUser) {
							var $td4 = $("<td/>").attr("align", "center").text(item.reserve);
							var $td5 = $("<td/>").attr("align", "center").text(item.assign);
							var $td10 = $("<td/>").attr("align", "center").text(item.allocate);
							var $td6 = $("<td/>").attr("align", "center").text(item.transfer);
							var $td7 = $("<td/>").attr("align", "center").text(item.returnField);
							var $td8 = $("<td/>").attr("align", "center").text(item.doa);
							$tr.append($td4).append($td5).append($td10).append($td6).append($td7).append($td8);
						}
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
	<span class="label">Stock Pool:</span>
	<form:select path="stockPool">
		<form:options items="${stockPoolList}" itemValue="parmValue" itemLabel="parmValue" />
	</form:select>
	<div style="clear:both"></div>
	<div style="clear:both"></div>
	<span class="label">Type:</span>
	<form:select path="type">
		<form:options items="${typeList}" itemValue="codeId" itemLabel="codeDesc" />
	</form:select>
	<div style="clear:both"></div>
	<span class="label">Item Code:</span>
	<form:input path="itemCode" maxlength="10"/>
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
	<c:if test="${adminUser}">
		<colgroup width="10%"></colgroup>
		<colgroup width="10%"></colgroup>
		<colgroup width="10%"></colgroup>
		<colgroup width="10%"></colgroup>
		<colgroup width="10%"></colgroup>
		<colgroup width="10%"></colgroup>
	</c:if>
<thead>
	<tr class="blueHeader">
		<th>Type</th>
		<th>Item Desc.</th>
		<th>Item Code</th>
		<th>Free</th>
		<c:if test="${adminUser}">
		<th>Reserve</th>
		<th>Assign</th>
		<th>Allocate</th>
		<th>Transfer</th>
		<th>Return</th>
		<th>DOA</th>
		</c:if>
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
	<c:choose>
	<c:when test="${adminUser}">
		<a href="mobccsstock.html" class="nextbutton">Quit</a>
	</c:when>
	<c:otherwise>
		<a href="welcome.html" class="nextbutton">Quit</a>
	</c:otherwise>
	</c:choose>
</div>
<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>