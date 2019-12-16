<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@page import="java.util.List, net.sf.json.*, com.bomwebportal.mob.ccs.dto.StockDTO"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
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
		var selectedStoreCode = $("select[name=storeCode]").val();
		$("select[name=teamCode] option:gt(0)").remove();
		$.each(teamListJson, function(index, team) {
			if (isDefault(selectedStoreCode) || team.storeCode == selectedStoreCode) {
				var $option = $("<option/>").text(team.teamCode).val(team.teamCode);
				$("select[name=teamCode]").append($option);
			}
		});
	}

	function formSubmit(actType) {
		document.stockInForm.actionType.value = actType;
		document.stockInForm.submit();
		$("#imei").val("");
		$("#batchRef").val("");
		$("#remarks").val("");
	}
	
	function onClear() {
		$("form[name=stockInForm] select").attr("selectedIndex", 0);
		
		$("#itemCode").val("");
		$("#imei").val("");
		$("#staffId").val("");
		$("#batchRef").val("");
		$("#startDateDatepicker").val("");
		$("#remarks").val("");
		$(".selectResultTable:last tbody").empty();
		
		formSubmit('CLEAR');
	};
	
	$(document).ready(function() {
		var selectedStoreCode = "<c:out value="${selectedStoreCode}"/>";
		var selectedTeamCode = "<c:out value="${selectedTeamCode}"/>";
		var sessionStoreCode = "<c:out value="${stockIn.storeCode}"/>";
		var sessionTeamCode = "<c:out value="${stockIn.teamCode}"/>";
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
		
		//datePicker
		$('#startDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "d"
			//, maxDate : "+90d"
		//yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		//yearRange: "1911:1993" //the range shown in the year selection box
		});
		
		$("#stockIn input").keypress(function(e) {
			switch (e.keyCode) {
			case 13:
				formSubmit('INSERT');
				break;
			}
		});
	});
</script>

<form:form name="stockInForm" method="POST" commandName="stockIn">
	<c:if test='${actionType == "SUCCESS"}'>
		This record is saved successfully!
	</c:if>
	<c:if test='${actionType == "UNSUCCESS"}'>
		This record is NOT saved!
	</c:if>
	
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><!--content-->
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td class="table_title">Stock Inventory Maintenance (In stock)</td>
				</tr>
			</table>
			<%-- ********************   SEARCHING KEY --- ROW 1 (ID DOC + search)   ***************************** --%>
			<table width="100%" border="0" cellspacing="1" class="contenttext"
				background="images/background2.jpg">
				<colgroup style="width: 110px"></colgroup>
				<colgroup></colgroup>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<!-- **************************** ITEM CODE ************************** -->
				<tr>
					<td>
						<div align="left">Item Code:<span class="contenttext_red">*</span></div>
					</td>
					<td>
						<form:input path="itemCode" maxlength="7"/>
						<input type="submit" value="Refresh" onClick="javascript:formSubmit('REFRESH') " />
						<form:errors path="itemCode" cssClass="error" />
					</td>
				</tr>
				<!-- **************************** TYPE ************************** -->
				<tr>
					<td>
						<div align="left">Type:</div>
					</td>
					<td>
						<form:select path="type" disabled="${true}">
							<form:option value="NONE" label="Select" />
							<form:options items="${typeList}" itemValue="codeId" itemLabel="codeDesc"/>
						</form:select>
					</td>
				</tr>
				<!-- **************************** MODEL ************************** -->
				<tr>
					<td>
						<div align="left">Model:</div>
					</td>
					<td>
						<form:input path="model" disabled="${true}" cssStyle="width:800px;"/>
					</td>
				</tr>
				<%-- **************************** IMEI ************************** --%>
				<tr>
					<td>
						<div align="left">Serial Number:<span class="contenttext_red">*</span></div>
					</td>
					<td>
						<form:input path="imei" maxlength="20" />
						<form:errors path="imei" cssClass="error" />
					</td>
				</tr>
				<!-- **************************** Stock Pool ************************** -->
				<tr>
					<td>Stock Pool:</td>
					<td>
						<form:select path="stockPool">
							<%-- <form:option value="" label=""/> --%>
							<form:options items="${stockPoolList}" itemValue="parmValue" itemLabel="parmValue"/>
						</form:select>
						<form:errors path="stockPool" cssClass="error" />
					</td>
				</tr>
				<!-- **************************** STATUS ************************** -->
				<tr>
					<td>
						<div align="left">Status:<span class="contenttext_red">*</span></div>
					</td>
					<td>
						<form:select path="status">
							<form:option value="NONE" label="Select" />
							<form:options items="${statusList}" itemValue="codeId" itemLabel="codeDesc"/>
						</form:select>
						<form:errors path="status" cssClass="error" />
					</td>
				</tr>
				<!-- **************************** Shop ************************** -->
				<tr>
					<td>
						<div align="left">Store Code:<span class="contenttext_red">*</span></div>
					</td>
					<td>
						<form:select path="storeCode">
							<form:option value="" label="Select" />
						</form:select>
						<form:errors path="storeCode" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>
						<div align="left">Team Code:<span class="contenttext_red">*</span></div>
					</td>
					<td>
						<form:select path="teamCode">
							<form:option value="" label="Select" />
						</form:select>
						<form:errors path="teamCode" cssClass="error" />
					</td>
				</tr>
				<!-- **************************** Event ************************** -->
				<tr>
					<td>
						<div align="left">Event Code:</div>
					</td>
					<td>
						<form:select path="eventCode">
							<form:option value="" label="Select" />
							<form:options items="${eventList}" itemValue="eventCode" itemLabel="eventCode"/>
						</form:select>
						<form:errors path="eventCode" cssClass="error" />
					</td>
				</tr>
				<%-- **************************** Staff ************************** --%>
				<tr>
					<td>
						<div align="left">Staff ID:</div>
					</td>
					<td>
						<form:input path="staffId" maxlength="20" />
						<form:errors path="staffId" cssClass="error" />
					</td>
				</tr>
				<%-- **************************** IMEI ************************** --%>
				<tr>
					<td>
						<div align="left">Book Out Date:</div>
					</td>
					<td>
						<form:input path="bookOutDate" id="startDateDatepicker" readonly="true"/>
						<%-- <form:input path="bookOutDate" maxlength="8" /> --%>
					</td>
				</tr>
				<%-- **************************** Batch No. ************************** --%>
				<tr>
					<td>
						<div align="left">Batch No.:</div>
					</td>
					<td>
						<form:input path="batchRef" />
						<form:errors path="batchRef" cssClass="error" />
					</td>
				</tr>
				<%-- ************************ REMARKS ************************ --%>
				<tr>
					<td>
						<div align="left">Remarks:</div>
					</td>
					<td>
						<form:textarea path="remarks" rows="5" cols="30" />
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<br>
	
	<%-- ********************************   BOTTOM BUTTONS   ************************************* --%>
	<table width="100%" border="0" cellspacing="0">
		<tr>
			<td align="center" width="33%">
			<div><a href="#" class="nextbutton" onClick="formSubmit('INSERT')">Save</a></div>
			</td>

			<td align="center" width="33%">
			<div><a href="#" class="nextbutton" onClick="onClear()">Clear</a></div>
			</td>

			<td align="center" width="33%">
			<div><a href="mobdsstock.html" class="nextbutton">Quit</a></div>
			</td>
		</tr>
	</table>

	<input type="hidden" name="actionType" id="actionType" />
</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>