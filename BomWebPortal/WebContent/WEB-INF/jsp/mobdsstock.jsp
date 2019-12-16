<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<%@page import="java.util.List, net.sf.json.*, com.bomwebportal.mob.ccs.dto.StockDTO"%>

<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

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

function updatePaging(tableClass) {
	var $trs = $("table." + tableClass + " tbody tr");
	var $control = $("div." + tableClass);
	$control.find(".control").show().data("index", 0);
	$control.find(".control .left a").hide();
	if ($trs.length > 10) {
		$control.find(".control .right a").show();
	} else {
		$control.find(".control .right a").hide();
	}
	$control.find(".control .label").text("1 - " + $trs.filter(":visible").length + " / " + $trs.length);
	$control.find(".control .left a").unbind("click").click(function(e) {
		e.preventDefault();
		$trs.hide();
		var start = $control.find(".control").data("index") - 10;
		if (start < 0) {
			start = 0;
		}
		var end = start + 10;
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
		var start = $control.find(".control").data("index") + 10;
		if (start > $trs.length) {
			start = $trs.length;
		}
		var end = start + 10;
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
	function formSubmit(actType) {
		$("#actionType").val(actType);
		document.stockInventoryForm.submit();
	}

	function onClear() {
		$("form[name=stockInventoryForm] input").val("");
		$("form[name=stockInventoryForm] select").attr("selectedIndex", 0);
		$("#smsr").empty();
		$("#sisr").empty();
		formSubmit('CLEAR1');
	}
	
	function onClear2() {
		$("#sisr").empty();
		formSubmit('CLEAR2');
	}
	
	function isDefault(str) {
		return isBlank(str) || str == "NONE" || str == "";
	}
$(document).ready(function() {
	var selectedStoreCode = $("select[name=storeCode]").val();
	var selectedTeamCode = $("select[name=teamCode]").val();
	var sessionStoreCode = "<c:out value="${stockInventory.storeCode}"/>";
	var sessionTeamCode = "<c:out value="${stockInventory.teamCode}"/>";
	
	$("form[name=stockInventoryForm] input[type=text],form[name=stockInventoryForm] select[name=type]").click(function() {
		var $parent = $(this);
		$("form[name=stockInventoryForm] input[type=text]").not(this).filter(function() {
			return !($parent.attr("name") == "type" && $(this).attr("name") == "model");
		}).val("");
		$("form[name=stockInventoryForm] select[name=type]").not(this).filter(function() {
			return !($parent.attr("name") == "model" && $(this).attr("name") == "type");
		}).attr("selectedIndex", 0);
	});
	updatePaging("tempResultList");
	updatePaging("resultList");
	// switch to selectedNumList page
	var $input = $("table.tempResultList input[name=selectedNumList]:checked");
	var index = $("table.tempResultList input[name=selectedNumList]").index($input);
	if (index != -1) {
		var i = 0;
		while (i < parseInt(index / 10, 10)) {
			$("div.tempResultList .control .right a").click();
			i++;
		}
		$input.attr("checked", true);
	}
	
	$.each(storeListJson, function(index, store) {
		var $option = $("<option/>").text(store.storeCode).val(store.storeCode);
		if ((!isDefault(selectedStoreCode) && store.storeCode == selectedStoreCode) || store.storeCode == sessionStoreCode) {
			$option.attr("selected", true);
		}
		$("select[name=storeCode]").append($option);
	});
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
	
	if (selectedStoreCode == "" && sessionStoreCode == "") {
		$("select[name=teamCode]").attr("disabled", true);
		$("select[name=teamCode] option:eq(0)").attr("selected", true);
	} else {
		$("select[name=teamCode]").attr("disabled", false);
	}
});
</script>

<form:form name="stockInventoryForm" method="POST" commandName="stockInventory">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<form:errors path="type" cssClass="error" />
	<form:errors path="searchItemCode" cssClass="error" />
	<form:errors path="selectedNum" cssClass="error" />
	<form:errors path="status" cssClass="error" />
	<form:errors path="model" cssClass="error" />

	<table class="contenttext" style="width: 100%; background-color: white; border: 2px solid #bebebe">
	<colgroup style="width: 100px"></colgroup>
	<colgroup></colgroup>
	<colgroup style="width: 100px"></colgroup>
	<colgroup></colgroup>
	<tr>
		<td class="table_title" colspan="4" style="font-size: medium">Stock Inventory Maintenance</td>
	</tr>
	<%-- ********************   SEARCHING KEY --- ROW 1 (ID DOC + search)   ***************************** --%>
	<tr>
		<td>
			Type:
		</td>
		<td>
			<form:select path="type">
				<form:option value="NONE" label="Select" />
				<form:options items="${typeList}" itemValue="codeId" itemLabel="codeDesc" />
			</form:select>
		</td>
		<td>
			Model:
		</td>
		<td>
			<form:input path="model" id="stockModelField" cssStyle="width: 300px"/>
		</td>
	</tr>
	<tr>
		<td>
			Item Code:
		</td>
		<td>
			<form:input path="searchItemCode" cssStyle="width: 150px" id="stockItemCodeField" maxlength="7" />
		</td>
	</tr>
	<tr>
		<td>
			Item Serial:
		</td>
		<td>
			<form:input path="itemSerial" cssStyle="width: 150px" id="stockItemSerialField" maxlength="20" />
		</td>
	</tr>
	<tr>
		<td>
			Order Id:
		</td>
		<td>
			<form:input path="orderId" cssStyle="width: 150px" id="stockOrderIdField" maxlength="12" />
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<div class="buttonmenubox_R">
				<a href="#" class="nextbutton" onClick="javascript:formSubmit('SEARCH1');">Search</a> 
				<a href="#" class="nextbutton" onClick="onClear()">Clear</a>
			</div>
		</td>
	</tr>
	</table>

	<c:if test='${(actionType == "SEARCH1" && (sk == 1 || sk == 2)) || actionType == "SEARCH2"}'>
		<!-- ******************************* TEMP TABLE ************************** -->
		<table id="smsr" width="100%" class="tablegrey">
			<tr>
				<td bgcolor="#FFFFFF" width="100%" colspan="4"><!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Stock Model Search Result</td>
					</tr>
				</table>
				<c:choose>
					<c:when test='${empty tempResultList}'>
						<%-- ***************************   NO RECORD FOUND   ****************************** --%>
						<table width="100%" class="tablegrey">
						<tr>
							<td>No Record Found.</td>
						</tr>
						</table>
					</c:when>
					<c:otherwise>
						<%-- ***************************   TEMP RESULT FOUND   ****************************** --%>

								<table width="100%" border="1" cellspacing="0" class="contenttext tempResultList" bgcolor="#FFFFFF">
								<thead>
									<tr class="contenttextgreen">
										<td class="table_subtitle_blue" width="5%">&nbsp;</td>
										<td class="table_subtitle_blue">Type</td>
										<td class="table_subtitle_blue">Item Code</td>
										<td class="table_subtitle_blue" width="65%">Model</td>
									</tr>
								</thead>
								<tbody>
										<c:forEach items="${tempResultList}" var="tempResult"
											varStatus="counter">
											<tr<c:if test="${counter.index >= 10}"> style="display:none"</c:if>>
												<td width="3%" align="center"><form:checkbox
													path="selectedNumList" value="${counter.index}" /></td>
												
												<c:choose>
													<c:when test='${tempResult.scItemType == "01"}'>
														<td>&nbsp;HANDSET</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "02"}'>
														<td>&nbsp;SIM</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "03"}'>
														<td>&nbsp;GIFT-PC</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "04"}'>
														<td>&nbsp;TABLET</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "05"}'>
														<td>&nbsp;ANS</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "06"}'>
														<td>&nbsp;GIFT-MISC</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "07"}'>
														<td>&nbsp;SALES MEMO</td>
													</c:when>
													<c:otherwise>
														<td>&nbsp;${tempResult.scItemType}</td>
													</c:otherwise>
												</c:choose>
												<td>&nbsp;${tempResult.scItemCode}</td>
												<td width="65%">&nbsp;${fn:toUpperCase(tempResult.scItemDesc)}</td>
											</tr>
										</c:forEach>
								</tbody>
								</table>

<c:if test="${not empty tempResultList}">
<div style="text-align: center; padding: 5px 0" class="tempResultList">
	<span class="control">
		<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
		<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
		<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
	</span>
</div>
</c:if>

<hr>

				<table name="stockInventorySearch2Form" width="100%" border="0" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
						<colgroup style="width: 100px"></colgroup>
						<colgroup></colgroup>
						<tr>
							<td>Stock Pool:</td>
							<td>
								<form:select path="stockPool">
									<form:options items="${stockPoolList}" itemValue="parmValue" itemLabel="parmValue"/>
								</form:select>
							</td>
						</tr>
						<c:if test="${bomsalesuser.category != 'FRONTLINE'}">
							<tr>
								<td>Store Code:</td>
								<td>
									<form:select path="storeCode">
										<form:option value="" label="Select" />
									</form:select>
								</td>
							</tr>
							<tr>
								<td>Team Code:</td>
								<td>
									<form:select path="teamCode">
										<form:option value="" label="Select" />
									</form:select>
								</td>
							</tr>
						</c:if>
						<tr>
							<td>
								Event:
							</td>
							<td>
								<form:select path="eventCode">
									<form:option value="" label=""/>
									<form:options items="${eventList}" itemValue="eventCode" itemLabel="eventCode" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td>
								Status:
							</td>
							<td>
								<form:select path="status">
									<form:options items="${statusList}" itemValue="codeId" itemLabel="codeDesc" />
									<%-- <form:options items="${statusList}" itemValue="parmValue" itemLabel="parmValue" /> --%>
								</form:select></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="buttonmenubox_R">
									<a href="#" class="nextbutton" onClick="javascript:formSubmit('SEARCH2');">Search</a>
									<a href="#" class="nextbutton" onClick="onClear2()">Clear</a>
								</div>
							</td>
						</tr>
						</table>
					</c:otherwise>
				</c:choose></td>
			</tr>

		</table>
	</c:if>

	<!-- ******************** STOCK ITEM RESULT ************************ -->

	<c:if test='${(actionType == "SEARCH1" && (sk == 3 || sk == 4)) || actionType == "SEARCH2"}'>
		<table id="sisr" width="100%" class="tablegrey">
			<tr>
				<td bgcolor="#FFFFFF"><!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Stock Item Search Result</td>
					</tr>
				</table>

				<c:if test='${empty resultList}'>
					<%-- ***************************   NO RECORD FOUND   ****************************** --%>
					<table width="100%" class="tablegrey">
						<tr>
							<td bgcolor="#FFFFFF"><!--content-->
							<table width="100%" border="0" cellspacing="1" rules="none">
								<tr>
									<td>No Record Found.</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</c:if> 
				<c:if test='${!empty resultList}'>
					<%-- ***************************   RESULT TABLE   ****************************** --%>

							<table width="100%" border="1" cellspacing="0" class="contenttext resultList" bgcolor="#FFFFFF">
							<thead>
								<tr class="contenttextgreen">
									<td class="table_subtitle_blue">Type</td>
									<td class="table_subtitle_blue">Item Code</td>
									<td class="table_subtitle_blue">Model</td>
									<td class="table_subtitle_blue">Serial Number</td>
									<td class="table_subtitle_blue">Stock Pool</td>
									<td class="table_subtitle_blue">Status</td>
									<td class="table_subtitle_blue">Order Id</td>
									<td class="table_subtitle_blue">Event Code</td>
									<td class="table_subtitle_blue">Store Code</td>
									<td class="table_subtitle_blue">Team Code</td>
									<td class="table_subtitle_blue">Staff ID</td>
									<td class="table_subtitle_blue">Stock In Date</td>
									<td class="table_subtitle_blue">Batch No.</td>
									<td class="table_subtitle_blue">Remarks</td>
								</tr>
							</thead>
							<tbody>
									<c:forEach items="${resultList}" var="result" varStatus="status">
										<tr<c:if test="${status.index >= 10}"> style="display:none"</c:if>>
											<td>&nbsp;${result.type}&nbsp;</td>
										
											<td>&nbsp;${result.itemCode}&nbsp;</td>
											
											<td>&nbsp;${result.model}&nbsp;</td>

											<td>
												<a href="mobdsstockupdate.html?serialNumber=${result.imei}">
													&nbsp;${result.imei}&nbsp; 
												</a>
											</td>
											
											<td>
												${result.stockPool}
											</td>
													
											<c:choose>
												<c:when test='${result.status == "19"}'>
													<td>&nbsp;ASSIGN</td>
												</c:when>
												<c:when test='${result.status == "20"}'>
													<td>&nbsp;SOLD</td>
												</c:when>
												<c:when test='${result.status == "26"}'>
													<td>&nbsp;FAULTY</td>
												</c:when>
												<c:when test='${result.status == "27"}'>
													<td>&nbsp;SOS</td>
												</c:when>
												<c:when test='${result.status == "28"}'>
													<td>&nbsp;RSS</td>
												</c:when>
												<c:when test='${result.status == "29"}'>
													<td>&nbsp;RWH</td>
												</c:when>
												<c:when test='${result.status == "30"}'>
													<td>&nbsp;ABANDON</td>
												</c:when>
												<c:when test='${result.status == "31"}'>
													<td>&nbsp;DRN</td>
												</c:when>
												<c:otherwise>
													<td>&nbsp;${result.status}</td>
												</c:otherwise>
											</c:choose>

											<td>&nbsp;${result.orderId}&nbsp;</td>
											
											<td>&nbsp;${result.eventCode}</td>
											<td>&nbsp;${result.storeCode}</td>
											<td>&nbsp;${result.teamCode}</td>
											<td>&nbsp;${result.staffID}</td>

											<td>&nbsp; <fmt:formatDate
												pattern="dd/MM/yyyy" value="${result.stockInDate}" />
											&nbsp;</td>

											<td>&nbsp;${fn:toUpperCase(result.batchRef)}&nbsp;</td>
											
											<td>&nbsp;${fn:toUpperCase(result.remarks)}&nbsp;</td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
<c:if test="${not empty resultList}">
<div style="text-align: center; padding: 5px 0" class="resultList">
	<span class="control">
		<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
		<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
		<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
	</span>
</div>
</c:if>
				</c:if></td>
			</tr>
		</table>
	</c:if>

	<br>

	<%-- ********************************   BOTTOM BUTTONS   ************************************* --%>
	<table width="100%" border="0" cellspacing="0">
		<tr>
			<c:if test="${bomsalesuser.areaCd == 'WHT'}" >
			<td align="center" width="25%">
				<div>
					<a href="mobdsstockmanual.html" class="nextbutton">Manual Assign</a>
				</div>
			</td>
			</c:if>
			<td align="center" width="25%">
				<div>
					<a href="mobdsstockquantityenquiry.html" class="nextbutton">
						Quantity Enquiry
					</a>
				</div>
			</td>
			<c:if test="${bomsalesuser.areaCd == 'WHT' || bomsalesuser.category == 'SUPERVISOR'}">
				<td align="center" width="25%">
					<div>
						<a href="#" class="nextbutton" onClick="javascript:formSubmit('INSTOCK');">In Stock</a>
					</div>
				</td>
			</c:if>
		</tr>
	</table>
	<input type="hidden" name="actionType" id="actionType" />

</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>