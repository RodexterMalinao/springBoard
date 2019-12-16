<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script type="text/javascript">
	function formSubmit(actType) {
		if (confirm("Apply Insert / Update Action?")) {
			document.stockModelDetailsForm.actionType.value = "INSERT";
			document.stockModelDetailsForm.submit();
		}
	}
	
	function simTypeEnable() {
		if ($("#type").val() == "01" || $("#type").val() == "04") {
			$("#simType").attr("disabled", false);
		} else {
			$("#simType").val(0);
			$("#simType").attr("disabled", true);
		}
	}
	
	function onSearch() {
		document.stockModelDetailsForm.actionType.value = "SEARCH";
		document.stockModelDetailsForm.submit();
	}
	
	$(document).ready(function() {
		$("form input").keypress(function(e) {
			switch (e.keyCode) {
			case 13:
				formSubmit('INSERT');
				break;
			}
		});
		
		$("select[name=type]").change(function() {
			//$("select[name=hsExtraFunction]")[0].selectedIndex = 0;
			switch ($(this).val()) {
			case "01": // HANDSET
				$("select[name=hsExtraFunction]").attr("disabled", false);
				$("#simType").attr("disabled", false);
				break;
			case "04":
				$("#simType").attr("disabled", false);
				break;
			default:
				$("select[name=hsExtraFunction]").attr("disabled", true);
				$("#simType").val(0);
				$("#simType").attr("disabled", true);
			}
		});
		$("select[name=type]").change();
	});
	
</script>

<form:form name="stockModelDetailsForm" method="POST"
	commandName="stockModelDetails">
	
	<form:errors path="type" cssClass="error" />
	<form:errors path="itemCode" cssClass="error" />
	<form:errors path="model" cssClass="error" />
	<form:errors path="assignMode" cssClass="error" />
	
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
					<td class="table_title">Stock Inventory Maintenance (Model Details)</td>
			</tr>
			</table>
			<%-- ********************   SEARCHING KEY --- ROW 1 (ID DOC + search)   ***************************** --%>
			<table width="100%" border="0" cellspacing="1" class="contenttext">
			<colgroup style="width: 120px"></colgroup>
			<colgroup></colgroup>
			<tr>
				<td>Type:</td>
				<td>
					<form:select path="type">
						<form:option value="NONE" label="Select" />
						<form:options items="${typeList}" itemValue="codeId" itemLabel="codeDesc"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td>Item Code:</td>
				<td>
					<form:input id="itemCode" path="itemCode" maxlength="7"/>
					<input type="button" value="Search" onclick="onSearch()"/>
				</td>
			</tr>
			<tr>
				<td>Model:</td>
				<td><form:input path="model" cssStyle="width:800px;"/></td>
			</tr>
			<tr>
				<td colspan="2">
				ACQ Assign Mode:
					<form:select path="assignMode">
						<form:option value="NONE" label="Select" />
						<form:options items="${assignModeList}" itemValue="codeId" itemLabel="codeDesc"/>
					</form:select>
				
				<span style="margin-left: 100px">RET Assign Mode:
					<form:select path="assignModeRet">
						<form:option value="NONE" label="Select" />
						<form:options items="${assignModeList}" itemValue="codeId" itemLabel="codeDesc"/>
					</form:select>
					</span>
				</td>
			</tr>
			<tr>
				<td>SIM Type:</td>
				<td>
					<form:select path="simType">
						<form:option value="NONE" label="Select" />
						<form:option value="01" label="NORMAL SIM" />
						<form:option value="02" label="MICRO SIM" />
						<form:option value="21" label="NANO SIM" /><!-- Athena 20131218 -->
					</form:select>
				</td>
			</tr>
			<tr>
				<td>Extra Function:</td>
				<td>
					<form:select path="hsExtraFunction">
						<form:options items="${hsExtraFunctionList}" itemValue="codeId" itemLabel="codeDesc"/>
					</form:select>
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
			<td align="center" width="50%">
			<div><a href="#" class="nextbutton" onClick="javascript:formSubmit('INSERT')">Save</a></div>
			</td>

			<td align="center" width="50%">
			<c:choose>
				<c:when test="${bomsalesuser.channelId == 10 or bomsalesuser.channelId == 11}">
					<div><a href="mobdsstock.html" class="nextbutton">Quit</a></div>
				</c:when>
				<c:otherwise>
					<div><a href="mobccsstock.html" class="nextbutton">Quit</a></div>
				</c:otherwise>
			</c:choose>
			</td>
		</tr>
	</table>

	<input type="hidden" name="actionType" id="actionType" />
</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>