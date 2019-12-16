<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />

<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script type="text/javascript">

	function formSubmit(actType) {
		document.stockUpdateForm.actionType.value = actType;
		document.stockUpdateForm.submit();
	}
	
	function locNstatusDisable() {
		if ($("#status").val() == "19" || $("#status").val() == "20"
			|| $("#status").val() == "22" || $("#status").val() == "25") {
			$("#location").attr("disabled", true);
			$("#status").attr("disabled", true);
		}
		if ($("#status").val() == "26") {
			$("#location").attr("disabled", true);
		}
	}
	
	$(document).ready(function() {
		locNstatusDisable();
		$("#stockUpdate input").keypress(function(e) {
			switch (e.keyCode) {
			case 13:
				formSubmit('UPDATE');
				break;
			}
		});
		
		$("#stockPool").change(function(e){
			if (!$('#imei').is(':empty')) {
				$("#status").attr("disabled",true);
			}
		});
		
		$("#stockPool").change(function(e){
			if (!$('#imei').is(':empty')) {
				$("#status").attr("disabled",true);
			}
		});
		
		$("#status").change(function(e){
			if (!$('#imei').is(':empty')) {
				$("#stockPool").attr("disabled",true);
				$("#location").attr("disabled",true);
			}
		});
	});
</script>

<form:form name="stockUpdateForm" method="POST"
	commandName="stockUpdate">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	
	<form:errors path="stockPool" cssClass="error" />
	<form:errors path="status" cssClass="error" />
	<form:errors path="itemCode" cssClass="error" />
	<form:errors path="type" cssClass="error" />
	<form:errors path="location" cssClass="error" />

	<c:choose>
		<c:when test='${actionType == "SUCCESS"}'>
			<span style="color:red; padding-left:230px"><b>This record is saved successfully! </b></span>	
		</c:when>
		<c:when test='${actionType == "UNSUCCESS" && dsError == 0}'>
			This record is NOT saved!
		</c:when>
		<c:when test='${actionType == "UNSUCCESS"}'>
			<span style="color:red; padding-left:230px"><b>${errorMsg}</b></span>	
		</c:when>
		<c:when test='${dsError == 1}'>
			Mismatch Store/Team to update this stock.
		</c:when>
		<c:when test='${dsError == 2}'>
			Invalid Staff ID/Team Code!
		</c:when>
		<c:when test='${dsError == 3}'>
			Not allowed to transfer RSS Stock.
		</c:when>
		<c:when test='${dsError == 4}'>
			Event Code cannot be empty!
		</c:when>
		<c:when test='${dsError == 5}'>
			Invalid Event Transfer!
		</c:when>
	</c:choose>

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
							<form:option label="" value=""/>
							<form:options items="${stockPoolList}" itemValue="parmValue" itemLabel="parmValue"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td colspan="1" width="10%">
						<div align="left">Location:</div>
					</td>
					<td colspan="1" width="30%">
						<form:select path="location">
							<form:option value="NONE" label="Select" />
							<form:options items="${locationList}" itemValue="codeId" itemLabel="codeDesc" />
						</form:select>
					</td>
					<td colspan="1" width="30%">
						<div align="left">Status:
						<form:select path="status">
							<form:option value="NONE" label="Select" />
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
						<div align="left">Real item serial :
						<form:input path="itemSerialReal" disabled="true" />
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
			<td align="center" width="50%">
				<div><a href="#" class="nextbutton" onClick="formSubmit('UPDATE')">Save</a></div>
			</td>
			<td align="center" width="50%">
				<div><a href="mobccsstock.html" class="nextbutton">Quit</a></div>
			</td>
		</tr>
	</table>

	<input type="hidden" name="actionType" id="actionType" />

</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>