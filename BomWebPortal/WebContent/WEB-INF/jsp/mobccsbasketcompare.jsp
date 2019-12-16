<%@ include file="/WEB-INF/jsp/header.jsp"%>
<style type="text/css">
.basketSelectResultTable { width: 100%; border-collapse: separate; border-width: 0px; border-spacing: 0px; }
.basketSelectResultTable tr { padding: 3px 0 }
.basketSelectResult { width: 98%; height: 330px; overflow-y: auto; }
.even { background-color: rgb(232,255,232); }
.searchTitle { text-align: left; }
.blueHeader { background-color: rgb(232, 242, 254) }
</style>
<script type="text/javascript">
function updatePaging(tableClass) {
	var $trs = $("table." + tableClass + " tbody tr");
	var $control = $("div." + tableClass);
	$control.find(".control").show().data("index", 0);
	$control.find(".control .left a").hide();
	if ($trs.length > 20) {
		$control.find(".control .right a").show();
	} else {
		$control.find(".control .right a").hide();
	}
	$control.find(".control .label").text("1 - " + $trs.filter(":visible").length + " / " + $trs.length);
	$control.find(".control .left a").unbind("click").click(function(e) {
		e.preventDefault();
		$trs.hide();
		var start = $control.find(".control").data("index") - 20;
		if (start < 0) {
			start = 0;
		}
		var end = start + 20;
		if (end > $trs.length) {
			end = $trs.length;
		}
		if (start > 0) {
			$control.find(".control .left a").show();
		} else {
			$control.find(".control .left a").hide();
		}
		if (end < $trs.length) {
			$control.find(".control .right a").show();
		} else {
			$control.find(".control .right a").hide();
		}
		$trs.slice(start, end).show();
		$control.find(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
		$control.find(".control").data("index", start);
		return false;
	});
	$control.find(".control .right a").unbind("click").click(function(e) {
		e.preventDefault();
		$trs.hide();
		var start = $control.find(".control").data("index") + 20;
		if (start > $trs.length) {
			start = $trs.length;
		}
		var end = start + 20;
		if (end > $trs.length) {
			end = $trs.length;
		}
		if (start > 0) {
			$control.find(".control .left a").show();
		} else {
			$control.find(".control .left a").hide();
		}
		if (end < $trs.length) {
			$control.find(".control .right a").show();
		} else {
			$control.find(".control .right a").hide();
		}
		$trs.slice(start, end).show();
		$control.find(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
		$control.find(".control").data("index", start);
		return false;
	});
}

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
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("#customerTierId").change(updateSearchCriteria);
	$("#basketTypeId").change(updateSearchCriteria);
	$("#searchBasketButton").click(function(e) {
		if ($("#customerTierId")[0].selectedIndex == 0
				&& $("#basketTypeId")[0].selectedIndex == 0
				&& $("#ratePlanId")[0].selectedIndex == 0
				&& $("#brandModelId")[0].selectedIndex == 0) {
			alert("Please select at least one criteria");
			e.preventDefault();
			return false;
		}
		switch ($("#basketTypeId").val()) {
		case "1": // SIM + HANDSET
		case "4": // SIM + TABLNET
			if ($("#brandModelId")[0].selectedIndex == 0) {
				alert("Require to select Brand/Model");
				return false;
			}
			break;
		}
		var $tbody = $(".basketSelectResultTableContext:last tbody");
		$tbody.empty();
		$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Loading...")));
		$.ajax({
			url: "mobccsbasketassosearchbasket.html"
			, data: {
						customerTierId: $("#customerTierId").val()
					, basketTypeId: $("#basketTypeId").val()
					, ratePlanId: $("#ratePlanId").val()
					, brandId: $("#brandModelId").val().length == 0 ? "" : $("#brandModelId").val().split("_")[0]
					, modelId: $("#brandModelId").val().length == 0 ? "" : $("#brandModelId").val().split("_")[1]
			}
			, cache: false
			, dataType: "json"
			, success: function(data) {
				$("#addBasketButton").hide();
				if (!data || data.length == 0) {
					$tbody.empty();
					$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("No result")));
				} else {
					$tbody.empty();
					var displayLimit = 20;
					$.each(data, function(index, item) {
						var $tr = $("<tr/>");
						if (index % 2 == 0) { $tr.addClass("even"); }
						if (index >= displayLimit) { $tr.css("display", "none"); }
						var $td0 = $("<td/>").append($("<a/>").css({"font-weight": "bold", "padding": 0}).attr({"href": "#"}).text("add").click(function(e) {
							e.preventDefault();
							if ($("#selectedBasket input[name=basketIds]").length >= 3) {
								alert("Max compare 3 baskets");
								return false;
							} 
							var basketId = $(this).parent().parent().find("td:eq(1)").text();
							var basketDesc = $(this).parent().parent().find("td:eq(2)").text();
							var basketSelected = false;
							$("#selectedBasket input[name=basketIds]").each(function() {
								if ($(this).val() == basketId) {
									basketSelected = true;
									return false;
								}
							});
							if (basketSelected) {
								return false;
							}
							var $tdSelectedBasket = $("#selectedBasket");

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

								if ($("#selectedBasket input[name=basketIds]").length > 0) {
									$("input[name=compareButton]").show();
									$("input[name=removeButton]").show();
								} else {
									$("input[name=compareButton]").hide();
									$("input[name=removeButton]").hide();
								}
							});
							$spanButton.append($remove);
							$tdSelectedBasket.append($div.append($spanButton).append($spanBasketId).append($spanBasketDesc).append($input)).append($("<div/>").css("clear", "both"));
							
							$tdSelectedBasket.children().removeClass("even");
							$tdSelectedBasket.children(":nth-child(4n+1)").addClass("even");

							if ($("#selectedBasket input[name=basketIds]").length > 0) {
								$("input[name=compareButton]").show();
								$("input[name=removeButton]").show();
							}
						}));
						var $td1 = $("<td/>").text(item.basketId);
						var $td2 = $("<td/>").text(item.basketDesc);
						$tbody.append($tr.append($td0).append($td1).append($td2));
					});
					updatePaging("basketSelectResultTable");
				}
			}
			, error: function() {
				$tbody.empty();
				$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Error found, please retry")));
			}
		});
	});
	
	$("#clearBasketButton").click(function(e) {
		$("#customerTierId")[0].selectedIndex = 0;
		$("#basketTypeId")[0].selectedIndex = 0;
		$("#ratePlanId option:gt(0)").remove();
		$("#brandModelId option:gt(0)").remove();
		$(".basketSelectResultTableContext:last tbody").empty();
	});
	
	$("#compareForm").submit(function(e) {
		e.preventDefault();
		// http://msdn.microsoft.com/en-us/library/ie/ms536759%28v=vs.85%29.aspx
		var sURL = "mobccsbasketcomparedetail.html?" + $(this).serialize();
		var vArguments = new Object();
		var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
		window.showModalDialog(sURL, vArguments, sFeatures);
	});

	$("input[name=removeButton]").click(function(e) {
		$("#selectedBasket a").each(function() {
			$(this).click();
		});
	});
});
</script>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:110px"></colgroup>
<tr class="contenttextgreen">
<td class="table_title" colspan="2">Basket Comparison</td>
</tr>
<tr>
<td colspan="2">

<table style="width:100%">
<colgroup style="width:250px"></colgroup>
<colgroup style="width:350px"></colgroup>
<tbody>
<tr>
<th colspan="3" class="searchTitle">Basket Selection</th>
</tr>
<tr>
<td>Customer Tier:</td>
<td>
<select id="customerTierId">
<option value=""></option>
<c:forEach items="${customerTierALL}" var="item"><option value="${item.customerTierId}">${item.customerTier}</option></c:forEach>
</select>
</td>
<td></td>
</tr>
<tr>
<td>Basket Type:</td>
<td>
<select id="basketTypeId">
<option value=""></option>
<c:forEach items="${basketTypeALL}" var="item"><option value="${item.basketTypeId}">${item.basketType}</option></c:forEach>
</select>
</td>
<td></td>
</tr>
<tr>
<td>Rate Plan Recurring Amount:</td>
<td>
<select id="ratePlanId">
<option value=""></option>
<c:forEach items="${ratePlanALL}" var="item"><option value="${item.ratePlanId}">${item.ratePlan}</option></c:forEach>
</select>
</td>
<td></td>
</tr>
<tr>
<td>Brand / Model:</td>
<td>
<select id="brandModelId">
<option value=""></option>
<c:forEach items="${brandModelALL}" var="item"><option value="${item.brandId}_${item.modelId}">${item.brandModelDesc}</option></c:forEach>
</select>
</td>
<td><input type="button" id="searchBasketButton" value="Search"><input type="button" id="clearBasketButton" value="Clear"></td>
</tr>
<tr>
<td colspan="3">
<table class="basketSelectResultTable">
<colgroup width="25px"></colgroup>
<colgroup width="100px"></colgroup>
<colgroup width="750px"></colgroup>
<thead>
<tr class="blueHeader"><th></th><th>Basket ID</th><th>Basket Desc</th></tr>
</thead>
</table>
</td>
</tr>
<tr>
<td colspan="3">
<div class="basketSelectResult">
<table class="basketSelectResultTable basketSelectResultTableContext">
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
<div style="text-align: center; padding: 5px 0" class="basketSelectResultTable">
	<span class="control">
		<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
		<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
		<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
	</span>
</div>
</td>
</tr>
<tr>
<td>Compare basket<br>(Max 3)</td>
<td>
<form method="post" id="compareForm" action="mobccsbasketcomparedetail.html">
<table style="width:100%">
<thead>
<tr class="blueHeader">
<td>
<div style="padding: 3px 0">
<span style="width: 25px;vertical-align: top;display: inline-block;text-align: center;font-weight: bold">&nbsp;</span>
<span style="width: 100px;vertical-align: top;display: inline-block;text-align: center;font-weight: bold">Basket ID</span>
<span style="width: 600px;vertical-align: top;display: inline-block;text-align: center;font-weight: bold">Basket Desc</span>
</div>
</td>
</tr>
</thead>
<tbody><tr><td id="selectedBasket"></td></tr></tbody>
<tfoot><tr><td><input type="submit" name="compareButton" value="Compare" style="display:none"><input type="button" name="removeButton" value="Clear" style="display:none"></td></tr></tfoot>
</table>
</form>
</td>
</tr>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>