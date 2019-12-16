<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<style type="text/css">
.basketSelectResultTable { width: 100%; border-collapse: separate; border-width: 0px; border-spacing: 0px; }
.basketSelectResultTable tr { padding: 3px 0 }
.overflowDiv { width: 98%; height: 300px; overflow-y: auto; }
.even { background-color: rgb(232,255,232); }
.searchTitle { text-align: left; font-size: 12px; text-align: center; margin: 0; padding: 0 }
.blueHeader { background-color: rgb(232, 242, 254) }
.searchLabel { display: inline-block; float: left; width: 20%; vertical-align: top }
.searchInput { display: inline-block; float: left; width: 75%; vertical-align: top; margin: 2px 0 }
.clear2 { clear: both }
.basketDataControl { font-size: x-small }
.basketDataControl .note { display: inline-block; white-space: nowrap; overflow: hidden; width: 100px; background-color: rgb(192, 192, 192) }
.controlButton { display: inline-block; width: 30px }
.controlButton a { color: rgb(128, 128, 128) }
.controlButton a:hover { background-color: rgb(255, 255, 128) }
.searchRule .error_searchRule0 { margin-left: 200px }
.searchRemark { font-size: smaller; color: rgb(128, 128, 128) }
.searchRule select, .searchRule input { width: 100% }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
function updateSearchCriteria() {
	$("#ratePlanId option:gt(0)").remove();
	$("#brandModelId option:gt(0)").remove();
	$.ajax({
		url: "mobccsbasketassosearchbasketattrrateplan.html"
		, data: {
			customerTierId: $("#customerTierId").val()
			, basketTypeId: $("#basketTypeId").val()
		}
		, cache: false
		, dataType: "json"
		, success: function(data) {
			$.each(data, function(index, ratePlan) {
				var $option = $("<option/>");
				$option.text(ratePlan.ratePlan);
				$option.val(ratePlan.ratePlanId);
				$("#ratePlanId").append($option);
			});
		}
	});
	$.ajax({
		url: "mobccsbasketassosearchbasketattrbrandmodel.html"
		, data: {
			customerTierId: $("#customerTierId").val()
			, basketTypeId: $("#basketTypeId").val()
		}
		, cache: false
		, dataType: "json"
		, success: function(data) {
			$.each(data, function(index, brandModel) {
				var $option = $("<option/>");
				$option.text(brandModel.brandModelDesc);
				$option.val(brandModel.brandId + "_" + brandModel.modelId);
				$("#brandModelId").append($option);
			});
		}
	});
}
function getToday() {
	var date = new Date();
	date.setHours(0);
	date.setMinutes(0);
	date.setSeconds(0);
	date.setMilliseconds(0);
	return date;
}
function displayBasketData(data) {
	var $tbody = $(".basketQueried tbody");
	$tbody.empty();
	$.each(data, function(index, item) {
		var $tr = $("<tr/>");
		if (index % 2 == 0) { $tr.addClass("even"); }
		var $td0 = $("<td/>").css({padding: 0}).append($("<input/>").attr({"type": "checkbox"}).val(item.basketId).click(function() {
			if ($(".basketQueried input[type=checkbox]:checked").length > 0) {
				$("#addBasketButton").show();
			} else {
				$("#addBasketButton").hide();
			}
		}));
		var $td1 = $("<td/>").text(item.basketId);
		var $td2 = $("<td/>").text(item.basketDesc);
		$tbody.append($tr.append($td0).append($td1).append($td2));
	});
}
function changeBasketDisplayPage(nextPage) {
	var pageLimit = 50;
	var basketData = $(".basketDataControl").data("basketData");
	if (basketData != null) {
		if (basketData.data.length > pageLimit) {
			var rangeStart, rangeEnd;
			if (nextPage < 0) {
				rangeStart = basketData.index - pageLimit;
				rangeEnd = basketData.index;
				if (rangeStart < 0) {
					rangeStart = 0;
				}
				if (rangeEnd < 0) {
					rangeEnd = pageLimit < basketData.data.length ? pageLimit : basketData.data.length;
				}
			} else if (nextPage > 0) {
				rangeStart = basketData.index + pageLimit;
				rangeEnd = rangeStart + pageLimit;
				if (rangeStart > basketData.data.length) {
					rangeStart = basketData.index;
				}
				if (rangeEnd > basketData.data.length) {
					rangeEnd = basketData.data.length;
				}
			} else {
				rangeStart = 0;
				rangeEnd = pageLimit;
				if (rangeEnd > basketData.data.length) {
					rangeEnd = basketData.data.length;
				}
			}
			basketData.index = rangeStart;
			displayBasketData(basketData.data.slice(rangeStart, rangeEnd));
			$(".basketDataControl .note").text((rangeStart + 1) + " - " + rangeEnd + " / " + basketData.data.length);
			if (rangeStart == 0) {
				$(".basketDataControl .prev").hide();
			} else {
				$(".basketDataControl .prev").show();
			}
			if (rangeEnd == basketData.data.length) {
				$(".basketDataControl .next").hide();
			} else {
				$(".basketDataControl .next").show();
			}
			$(".basketDataControl").show();
		} else {
			displayBasketData(basketData.data);
			$(".basketDataControl").hide();
		}
	}
}
function searchBasket() {
	$(".searchRule .error").hide();
	var searchData = null;
	switch ($("input[name=searchBy]:checked").attr("id")) {
	case "searchBy0":
		if ($("#customerTierId")[0].selectedIndex == 0
				&& $("#basketTypeId")[0].selectedIndex == 0
				&& $("#ratePlanId")[0].selectedIndex == 0
				&& $("#brandModelId")[0].selectedIndex == 0) {
			$(".error_searchRule0").text("Please select at least one criteria").show();
			return;
		}
		switch ($("#basketTypeId").val()) {
		case "1": // SIM + HANDSET
		case "4": // SIM + TABLNET
			if ($("#brandModelId")[0].selectedIndex == 0) {
				$(".error_searchRule0").text("Require to select Brand/Model").show();
				return;
			}
			break;
		}
		searchData = {
					customerTierId: $("#customerTierId").val()
				, basketTypeId: $("#basketTypeId").val()
				, ratePlanId: $("#ratePlanId").val()
				, brandId: $("#brandModelId").val().length == 0 ? "" : $("#brandModelId").val().split("_")[0]
				, modelId: $("#brandModelId").val().length == 0 ? "" : $("#brandModelId").val().split("_")[1]
		};
		break;
	case "searchBy1":
		var basketDescLen = $.trim($("#basketDesc").val()).length;
		if (basketDescLen == 0) {
			$(".error_searchRule1").text("Please enter Basket Desc").show();
			return;
		}
		if ($.trim($("#basketDesc").val()).replace(/\*/g, '').length < 4) {
			$(".error_searchRule1").text("Please enter at least 4 alphanumeric").show();
			return;
		}
		searchData = {
				basketDesc: $.trim($("#basketDesc").val())
		};
		break;
	}
	var $tbody = $(".basketQueried tbody");
	$tbody.empty();
	$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Loading...")));
	$(".basketDataControl").hide().data("basketData", null);
	$.ajax({
		url: "mobccsbasketassosearchbasket.html"
		, data: searchData
		, cache: false
		, dataType: "json"
		, success: function(data) {
			$("#addBasketButton").hide();
			if (!data || data.length == 0) {
				$tbody.empty();
				$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("No result")));
			} else {
				$tbody.empty();
				$(".basketDataControl").data("basketData", { "data": data, "index": 0 });
				changeBasketDisplayPage(0);
			}
		}
		, error: function() {
			$("#addBasketButton").hide();
			$tbody.empty();
			$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Error found, please retry")));
		}
	});
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

	$("#editForm").submit(function(e) {
		$(".error").hide();
		if ($("input[name=basketIds]").length == 0) {
			$(".error_selectedBasket").text("Please select basket").show();
		}
		if ($("input[name=startDate]").val().length == 0) {
			$(".error_startDate").text("Start Date required").show();
		} else {
			var startDate = $("input[name=startDate]").datepicker("getDate");
			if (startDate < getToday()) {
				$(".error_startDate").text("Please select Start Date on / after today").show();
			}
			if ($("input[name=endDate]").val().length != 0) {
				var endDate = $("input[name=endDate]").datepicker("getDate");
				if (endDate < startDate) {
					$(".error_endDate").text("Please select End Date on / after Start Date").show();
				}
			}
		}
		if ($(".error:visible").length > 0) {
			e.preventDefault();
			return false;
		}

		// check selected Basket with already Selected basket
		var dupSelectedBasket = "";
		var $tdSelectedBasket = $("#selectedBasket");
		var $tdAlreadySelectedBasket = $("#alreadySelectedBasket");
		$tdSelectedBasket.find("input[name=basketIds]").each(function(index, basketId) {
			$tdAlreadySelectedBasket.find(".basket").each(function(aIndex, basket) {
				var basketDesc = $.trim($(basket).find(".basketDesc").text());
				if ($(basketId).val() == $.trim($(basket).find(".basketId").text())) {
					if ($.trim($(basket).find(".endDate").text()).length == 0) {
						dupSelectedBasket += ((dupSelectedBasket.length > 0 ? ", " : "") + $(basketId).val() + " " + basketDesc + " is open ended");
					} else {
						var startDate = $("input[name=startDate]").datepicker("getDate");
						var endDate = $("input[name=endDate]").val().length == 0 ? null : $("input[name=endDate]").datepicker("getDate");
						var basketStartDate = $.datepicker.parseDate("dd/mm/yy", $.trim($(basket).find(".startDate").text()));
						var basketEndDate = $.datepicker.parseDate("dd/mm/yy", $.trim($(basket).find(".endDate").text()));
						if (endDate == null) {
							if (startDate < basketEndDate) {
								dupSelectedBasket += ((dupSelectedBasket.length > 0 ? ", " : "") + $(basketId).val() + " " + basketDesc + " has start date before existing " + $.datepicker.formatDate("dd/mm/yy", basketStartDate));
							}
						}
					}
				}
			});
		});
		if (dupSelectedBasket.length > 0) {
			$(".error_selectedBasket").text(dupSelectedBasket).show();
		}
		if ($(".error:visible").length > 0) {
			e.preventDefault();
			return false;
		}
	});
	$("input[name=backButton]").click(function() {
		window.location.href = "mobccsbasketassojoblistbskasso.html";
	});
	
	$("select[name=basketId]").change(function(e) {
		var basketId = $(this).val();
		$("select[name=basketIdDesc]").children().each(function(index, value) {
			if ($(value).val() == basketId) {
				$("input[name=basketDesc]").val($(value).text());
				return false;
			}
		});
	});

	$("#customerTierId").change(updateSearchCriteria);
	$("#basketTypeId").change(updateSearchCriteria);
	
	$("input[name=searchBy]").click(function(e) {
		$(".searchRule").hide();
		switch ($(this).attr("id")) {
		case "searchBy0":
			$(".searchRule0").show();
			break;
		case "searchBy1":
			$(".searchRule1").show();
			break;
		}
	});
	$("#basketDesc").keydown(function(e) {
		switch (e.keyCode){
		case 13:
			searchBasket();
			e.preventDefault();
			return false;
		}
	});
	$("#searchBasketButton").click(searchBasket);
	
	$("#clearBasketButton").click(function(e) {
		$("#customerTierId")[0].selectedIndex = 0;
		$("#basketTypeId")[0].selectedIndex = 0;
		$("#ratePlanId option:gt(0)").remove();
		$("#brandModelId option:gt(0)").remove();
		$("#basketDesc").val("");
		$(".basketDataControl").hide();
		$(".basketQueried tbody").empty();
		$("input[name=searchBy]:first").click();
	});

	$("#addBasketButton").click(function(e) {
		var $tdSelectedBasket = $("#selectedBasket");
		var $baskets = $(".basketQueried input[type=checkbox]:checked");
		if ($baskets.length == 0) {
			alert("Please select a basket");
			e.preventDefault();
			return false;
		}
		var dupSelectedBasket = "";
		$baskets.each(function() {
			var basketId = $(this).val();
			var basketDesc = $(this).parent().next().next().text();
			if ($tdSelectedBasket.find("input[value=" + basketId + "]").length > 0) {
				dupSelectedBasket += ((dupSelectedBasket.length > 0 ? "\n" : "") + basketId + " " + basketDesc);
			} else {
				var $div = $("<div/>").css({padding: "3px 0"});
				var $spanButton = $("<span/>").css({"width": "25px", "vertical-align": "top", "display": "inline-block"});
				var $spanBasketId = $("<span/>").text(basketId).css({"width": "100px", "vertical-align": "top", "display": "inline-block"});
				var $spanBasketDesc = $("<span/>").text(basketDesc).css({"width": "600px", "vertical-align": "top", "display": "inline-block"});
				var $input = $("<input/>").attr({"type": "hidden", "name": "basketIds"}).val(basketId);
				var $remove = $("<a/>").attr({"href": "#"}).css({"font-weight": "bold", "padding": 0}).text("X").click(function(e) {
					e.preventDefault();
					$div.next().remove();
					$div.remove();
					$tdSelectedBasket.children().removeClass("even");
					$tdSelectedBasket.children(":nth-child(4n+1)").addClass("even");
				});
				$spanButton.append($remove);
				$tdSelectedBasket.append($div.append($spanButton).append($spanBasketId).append($spanBasketDesc).append($input)).append($("<div/>").css("clear", "both"));
			}
		});
		$tdSelectedBasket.children().removeClass("even");
		$tdSelectedBasket.children(":nth-child(4n+1)").addClass("even");
		var errorMsg = "";
		if (dupSelectedBasket.length > 0) {
			errorMsg += "*** Already exists in Selected baslet ***\n";
			errorMsg += "-----------------------------------------\n";
			errorMsg += dupSelectedBasket;
		}
		if (errorMsg.length > 0) {
			alert(errorMsg);
		}
	});
	
	$(".basketCheckAll").click(function(e) {
		e.preventDefault();
		$(".basketQueried input[type=checkbox]").attr("checked", true);
		if ($(".basketQueried input[type=checkbox]:checked").length > 0) {
			$("#addBasketButton").show();
		} else {
			$("#addBasketButton").hide();
		}
		return false;
	});
	$(".basketDataControl .prev").click(function(e) {
		e.preventDefault();
		changeBasketDisplayPage(-1);
		return false;
	});
	$(".basketDataControl .next").click(function(e) {
		e.preventDefault();
		changeBasketDisplayPage(1);
		return false;
	});
});
</script>

<form:form method="POST" name="mobccsbasketassojoblistbskassoedit" id="editForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:10%"></colgroup>
<tr class="contenttextgreen">
	<td class="table_title" colspan="9">Job List / Basket(Campaign Offer) New Association</td>
</tr>
<tr>
	<td>Job List</td>
	<td>
		<c:out value="${command.jobList}"/><form:hidden path="jobList"/>
		<div style="clear:both"></div>
		<form:errors path="jobList" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td colspan="2">
	<table style="width:100%">
	<tbody>
	<tr>
		<td colspan="2">
			<h3 class="searchTitle">Basket Selection</h3>
			<div>
				<div class="searchLabel">Search by:</div>
				<div class="searchInput">
					<label for="searchBy0"><input type="radio" name="searchBy" id="searchBy0" checked="checked">Customer Tier, Basket Type, Rate Plan Recurring Amount or Brand / Model</label>
					<div style="clear:both"></div>
					<label for="searchBy1"><input type="radio" name="searchBy" id="searchBy1">Basket Desc</label>
				</div>
			</div>
			<div class="clear2"></div>
			<hr>
			<div class="searchRule searchRule0">
				<div class="searchLabel">Customer Tier:</div>
				<div class="searchInput">
					<select id="customerTierId">
					<option value=""></option>
					<c:forEach items="${customerTierALL}" var="item"><option value="${item.customerTierId}">${item.customerTier}</option></c:forEach>
					</select>
				</div>
				<div class="clear2"></div>
				<div class="searchLabel">Basket Type:</div>
				<div class="searchInput">
					<select id="basketTypeId">
					<option value=""></option>
					<c:forEach items="${basketTypeALL}" var="item"><option value="${item.basketTypeId}">${item.basketType}</option></c:forEach>
					</select>
				</div>
				<div class="clear2"></div>
				<div class="searchLabel">Rate Plan Recurring Amount:</div>
				<div class="searchInput">
					<select id="ratePlanId">
					<option value=""></option>
					<c:forEach items="${ratePlanALL}" var="item"><option value="${item.ratePlanId}">${item.ratePlan}</option></c:forEach>
					</select>
				</div>
				<div class="clear2"></div>
				<div class="searchLabel">Brand / Model:</div>
				<div class="searchInput">
					<select id="brandModelId">
					<option value=""></option>
					<c:forEach items="${brandModelALL}" var="item"><option value="${item.brandId}_${item.modelId}">${item.brandModelDesc}</option></c:forEach>
					</select>
				</div>
				<div class="clear2"></div>
				<span class="error error_searchRule0" style="display:none"></span>
			</div>
			<div class="clear2"></div>
			<div class="searchRule searchRule1" style="display: none">
				<div class="searchLabel">Basket Desc(* for wildcard):</div>
				<div class="searchInput">
					<input type="text" id="basketDesc" maxlength="100">
					<span class="error error_searchRule1" style="display:none"></span>
					<div class="clear2"></div>
					<span class="searchRemark">Must input min. 4 characters</span>
				</div>
			</div>
			<div class="clear2"></div>
			<div style="text-align: right; vertical-align: bottom">
				<input type="button" id="searchBasketButton" value="Search">
				<input type="button" id="clearBasketButton" value="Clear">
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="3">
		<table class="basketSelectResultTable basketSelected">
		<colgroup width="25px"></colgroup>
		<colgroup width="100px"></colgroup>
		<colgroup width="750px"></colgroup>
		<thead>
		<tr class="blueHeader">
			<th><a class="basketCheckAll" href="#">ALL</a></th>
			<th>Basket ID</th>
			<th>
				<span class="basketDataControl" style="display:none; float: right">
					<span class="controlButton"><a class="prev" href="#">Prev</a></span>
					<span class="note"></span>
					<span class="controlButton"><a class="next" href="#">Next</a></span>
				</span>
				Basket Desc
			</th>
		</tr>
		</thead>
		</table>
		</td>
	</tr>
	<tr>
	<td colspan="3">
		<div class="overflowDiv">
		<table class="basketSelectResultTable basketQueried">
		<colgroup width="25px"></colgroup>
		<colgroup width="100px"></colgroup>
		<colgroup width="750px"></colgroup>
		<tbody></tbody>
		</table>
		</div>
	</td>
	</tr>
	</tbody>
	<tfoot>
	<tr>
		<td><input type="button" id="addBasketButton" value="Select" style="display:none"></td>
		<td></td>
		<td></td>
	</tr>
	</tfoot>
	</table>
	</td>
</tr>
<tr>
	<td>Selected basket</td>
	<td>
	<table style="width:100%">
	<thead>
	<tr>
		<td style="font-weight: bold" class="blueHeader">
			<div style="padding: 3px 0">
				<span style="width: 25px;vertical-align: top;display: inline-block;text-align: center;font-weight: bold">&nbsp;</span>
				<span style="width: 100px;vertical-align: top;display: inline-block;text-align: center;font-weight: bold">Basket ID</span>
				<span style="width: 600px;vertical-align: top;display: inline-block;text-align: center;font-weight: bold">Basket Desc</span>
			</div>
		</td>
	</thead>
	<tbody>
	<tr>
		<td id="selectedBasket"></td>
	</tr>
	</tbody>
	</table>
	<span class="error error_selectedBasket" style="display:none"></span>
	</td>
</tr>
<tr>
	<td>Already selected basket</td>
	<td id="alreadySelectedBasket">
		<div style="padding: 3px 0" class="blueHeader">
			<span style="width: 100px;text-align: center;font-weight: bold;display: inline-block">Basket ID</span>
			<span style="width: 600px;text-align: center;font-weight: bold;display: inline-block">Basket Desc</span>
			<span style="width: 90px;text-align: center;font-weight: bold;display: inline-block">Start Date</span>
			<span style="width: 90px;text-align: center;font-weight: bold;display: inline-block">End Date</span>
		</div>
		<c:forEach items="${resultList}" var="item" varStatus="status">
		<div style="padding: 3px 0" class="basket<c:if test="${status.index % 2 == 0}"> even</c:if>">
			<span style="width: 100px;vertical-align: top;display: inline-block" class="basketId">${item.basketId}</span>
			<span style="width: 600px;vertical-align: top;display: inline-block" class="basketDesc">${item.basketDesc}</span>
			<span style="width: 90px;vertical-align: top;display: inline-block" class="startDate"><fmt:formatDate pattern="dd/MM/yyyy" value="${item.startDate}"/></span>
			<span style="width: 90px;vertical-align: top;display: inline-block" class="endDate"><fmt:formatDate pattern="dd/MM/yyyy" value="${item.endDate}"/></span>
			<input type="hidden" name="alreadyBasketIds" value="${item.basketId}" disabled="disabled">
		</div>
		<div style="clear:both"></div>
		</c:forEach>
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
<tr>
	<td colspan="2">
		<input type="button" name="backButton" value="Quit" style="float:right">
		<input type="submit" name="addButton" value="Create" style="float:right">
	</td>
</tr>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>