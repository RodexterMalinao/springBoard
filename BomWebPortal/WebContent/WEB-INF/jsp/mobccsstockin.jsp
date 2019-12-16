<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script type="text/javascript">
	function formSubmit(actType) {
		document.stockInForm.actionType.value = actType;
		document.stockInForm.submit();
		$("#location").val("NONE");
		$("#status").val("NONE");
		$("#imei").val("");
		$("#quantity").val("");
		$("#batchRef").val("");
		$("#remarks").val("");
	}
	
	function quantityEnable() {
		if ($("#type").val() == "06") {
			$("#quantity").attr("disabled", false);
			$("#imei").attr("disabled", true);
			$("#imei").val("");
		} else {
			$("#quantity").attr("disabled", true);
			$("#quantity").val("");
			$("#imei").attr("disabled", false);
		}
	}
	
	$(document).ready(function() {
		quantityEnable();
		$("#stockIn input").keypress(function(e) {
			switch (e.keyCode) {
			case 13:
				formSubmit('INSERT');
				break;
			}
		});
	});
	
	$("#type").click(
		function() {
			quantityEnable();
		}
	);
</script>

<form:form name="stockInForm" method="POST" commandName="stockIn">
	
	<form:errors path="stockPool" cssClass="error" />
	<form:errors path="itemCode" cssClass="error" />
	<form:errors path="imei" cssClass="error" />
	<form:errors path="location" cssClass="error" />
	<form:errors path="status" cssClass="error" />
	<form:errors path="quantity" cssClass="error" />
	<form:errors path="batchRef" cssClass="error" />
	
	<c:if test='${param.result == "SUCCESS"}'>
		This record is saved successfully!
		<c:if test='${(!empty param.startSerial) && (!empty param.endSerial)}' >
			&nbsp;The serial numbers assigned starts from "<c:out value='${param.startSerial}' />"
			 to "<c:out value='${param.endSerial}' />".
		</c:if>
	</c:if>
	<c:if test='${param.result == "UNSUCCESS"}'>
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
						<div align="left">Item Code:</div>
					</td>
					<td>
						<form:input path="itemCode" maxlength="7"/>
						<input type="submit" value="Refresh" onClick="javascript:formSubmit('REFRESH') " />
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
				<!-- **************************** LOCATION ************************** -->
				<tr>
					<td>
						<div align="left">Location:</div>
					</td>
					<td>
						<form:select path="location">
							<form:option value="NONE" label="Select" />
							<form:options items="${locationList}" itemValue="codeId" itemLabel="codeDesc"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td>Stock Pool:</td>
					<td>
						<form:select path="stockPool">
							<form:option value="" label=""/>
							<form:options items="${stockPoolList}" itemValue="parmValue" itemLabel="parmValue"/>
						</form:select>
					</td>
				</tr>
				<!-- **************************** STATUS ************************** -->
				<tr>
					<td>
						<div align="left">Status:</div>
					</td>
					<td>
						<form:select path="status">
							<form:option value="NONE" label="Select" />
							<form:options items="${statusList}" itemValue="codeId" itemLabel="codeDesc"/>
						</form:select>
					</td>
				</tr>
				<%-- **************************** IMEI ************************** --%>
				<tr>
					<td>
						<div align="left">Serial Number:</div>
					</td>
					<td>
						<form:input path="imei" maxlength="20" />
					</td>
				</tr>
				<%-- **************************** Quantity ************************** --%>
				<tr>
					<td>
						<div align="left">Quantity:</div>
					</td>
					<td>
						<form:input path="quantity" maxlength="3"/>
					</td>
				</tr>
				<%-- **************************** Batch No. ************************** --%>
				<tr>
					<td>
						<div align="left">Batch No.:</div>
					</td>
					<td>
						<form:input path="batchRef" />
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
			<td align="center" width="50%">
			<div><a href="#" class="nextbutton" onClick="formSubmit('INSERT')">Save</a></div>
			</td>

			<td align="center" width="50%">
			<div><a href="mobccsstock.html" class="nextbutton">Quit</a></div>
			</td>
		</tr>
	</table>

	<input type="hidden" name="actionType" id="actionType" />
<%-- <input type="hidden" name="typeDesc" id="typeDesc"/> --%>	
</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>