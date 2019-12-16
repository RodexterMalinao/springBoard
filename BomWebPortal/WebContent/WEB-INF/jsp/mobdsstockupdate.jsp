<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@page import="java.util.List, net.sf.json.*, com.bomwebportal.mob.ccs.dto.StockDTO"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<c:set var="disableUpdate" value="${bomsalesuser.areaCd == 'OST' 
	or (bomsalesuser.channelId == 10 and bomsalesuser.category != 'SUPERVISOR')}" />

<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
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
		document.stockUpdateForm.actionType.value = actType;
		document.stockUpdateForm.submit();
	}
	
	function dsRwhStatusDisable() {
		if ($("#status").val() == "29") {
			$("#eventCode").attr("disabled", true);
			$("#status").attr("disabled", true);
			$("#teamCode").attr("disabled", true);
			$("#staffId").attr("disabled", true);
		}
	}
	
	function dsEventCodeDisable() {
		if ($("#status").val() != "28") {
			$("#eventCode").attr("disabled", true);
		}
	}
	
	function dsDisableChange() {
		if ($("#status").val() != "27" && 
				$("#status").val() != "28" &&
				$("#status").val() != "29" ) {
			$("#eventCode").attr("disabled", true);
			$("#status").attr("disabled", true);
			$("#staffId").attr("disabled", true);
			$("#stockPool").attr("disabled", true);
		} else {
			var isWhSupport = <c:out value="${bomsalesuser.category == 'WAREHOUSE SUPPORT'}"/>;
			if (isWhSupport) {
				$("#teamCode").attr("disabled", false);
				var allowStoreCode = <c:out value="${bomsalesuser.shopCd == 'SIS'}"/>;
				if (allowStoreCode) {
					$("#storeCode").attr("disabled", false);
				}
			}
		}
	}
	
	function dsDisableAll() {
		$("#eventCode").attr("disabled", true);
		$("#status").attr("disabled", true);
		$("#teamCode").attr("disabled", true);
		$("#staffId").attr("disabled", true);
		$("#stockPool").attr("disabled", true);
		$("#remarks").attr("disabled", true);
	}
	
	$(document).ready(function() {
		dsRwhStatusDisable();
		dsEventCodeDisable();
		dsDisableChange();
		
		var disableUpdate = <c:out value="${disableUpdate}"/>;
		if (disableUpdate){
			dsDisableAll();
		}
		
		var selectedStoreCode = "<c:out value="${selectedStoreCode}"/>";
		var selectedTeamCode = "<c:out value="${selectedTeamCode}"/>";
		var sessionStoreCode = "<c:out value="${stockUpdate.storeCode}"/>";
		var sessionTeamCode = "<c:out value="${stockUpdate.teamCode}"/>";
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
		
		
		$("#stockUpdate input").keypress(function(e) {
			switch (e.keyCode) {
			case 13:
				formSubmit('UPDATE');
				break;
			}
		});
	});
</script>

<form:form name="stockUpdateForm" method="POST" commandName="stockUpdate">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>

	<c:choose>
		<c:when test='${actionType == "SUCCESS"}'>
			This record is saved successfully! 
		</c:when>
		<c:when test='${actionType == "UNSUCCESS"}'>
			This record is NOT saved!
		</c:when>
		<c:when test='${dsError == 1}'>
			<div class = "error">Mismatch Store/Team to update this stock.</div>
		</c:when>
		<c:when test='${dsError == 2}'>
			<div class = "error">SIS not allow to modify RSS item</div>
		</c:when>
	</c:choose>
	<form:errors path="itemCode" cssClass="error" />
	<form:errors path="imei" cssClass="error" />
	<form:errors path="eventCode" cssClass="error" />
	<form:errors path="storeCode" cssClass="error" />
	<form:errors path="teamCode" cssClass="error" />
	<form:errors path="staffId" cssClass="error" />
	<form:errors path="status" cssClass="error" />
	
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><!--content-->
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td class="table_title">Stock Inventory Maintenance (Serial Number Details)</td>
				</tr>
			</table>
			<%-- ********************   SEARCHING KEY --- ROW 1 (ID DOC + search)   ***************************** --%>
			<table width="100%" border="0" cellspacing="1" class="contenttext"
				background="images/background2.jpg">
				
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="1" width="10%">
						<div align="left">Serial Number:</div>
					</td>
					<td colspan="1" width="30%">
						<form:input path="imei" disabled="true"/>
					</td>
					<td colspan="1" width="30%">
						<div align="left">Type:
						<form:select path="type" disabled="${true}">
							<form:option value="NONE" label="Select" />
							<form:options items="${typeList}" itemValue="codeId" itemLabel="codeDesc" />
						</form:select>
						</div>
					</td>
					<td colspan="1" width="30%">
						<div align="left">Item Code:
						<form:input path="itemCode" disabled="true" maxlength="7"/>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="1" width="10%">
						<div align="left">Model:</div>
					</td>
					<td colspan="3" width="90%">
						<form:input path="model" disabled="true" cssStyle="width:90%;"/>
					</td>
				</tr>
				<tr>
					<td>Stock Pool</td>
					<td>
						<form:select path="stockPool">
							<form:options items="${stockPoolList}" itemValue="parmValue" itemLabel="parmValue"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td colspan="1" width="10%">
						<div align="left">Event Code:</div>
					</td>
					<td colspan="1" width="30%">
						<form:select path="eventCode">
							<form:option value="" label="" />
							<form:options items="${eventCodeList}" itemValue="eventCode" itemLabel="eventCode" />
						</form:select>
					</td>
					<td colspan="1" width="30%">
						<div align="left">Status:
						<form:select path="status">
							<form:options items="${statusList}" itemValue="codeId" itemLabel="codeDesc" />
						</form:select>
						</div>
					</td>
					<td colspan="1" width="30%">
						<div align="left">Batch No.:
						<form:input path="batchRef" disabled="true" />
						</div>
					</td>
				</tr>
					<tr>
						<td colspan="1" width="10%">
							<div align="left">Store Code:</div>
						</td>
						<td colspan="1" width="30%">
							<form:select path="storeCode" disabled="true">
								<form:option value="" label="Select" />
							</form:select>
						</td>
						<td colspan="1" width="30%">
							<div align="left">Team Code:
							<form:select path="teamCode" disabled="true">
								<form:option value="" label="Select" />
							</form:select>
							</div>
						</td>
						<td colspan="1" width="30%">
							<div align="left">Staff ID:
							<form:input path="staffId"/>
							</div>
						</td>
					</tr>
				<tr>
					<td colspan="1" width="10%">
						<div align="left">Remarks:</div>
					</td>
					<td colspan="1" width="30%">
						<form:textarea path="remarks" rows="5" cols="30" />
					</td>
					<td colspan="1" width="30%">
						<div align="left">Order Id:
						<form:input path="orderId" disabled="true" />
						</div>
					</td>
					<td colspan="1" width="30%">
						&nbsp;
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
			<c:if test='${bomsalesuser.areaCd == "WHT" || bomsalesuser.category == "SUPERVISOR"}'>
				<td align="center" width="50%">
				<div><a href="#" class="nextbutton" onClick="formSubmit('UPDATE')">Save</a></div>
				</td>
			</c:if>
			<td align="center" width="50%">
			<div><a href="mobdsstock.html" class="nextbutton">Quit</a></div>
			</td>
		</tr>
	</table>

	<input type="hidden" name="actionType" id="actionType" />

</form:form>

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>