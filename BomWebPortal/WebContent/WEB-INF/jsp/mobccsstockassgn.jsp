<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script language="javascript">
	document.onkeypress = processKey;
	function processKey(e) {
		if (null == e)
			e = window.event;
		if (e.keyCode == 13) {
			insert();
		}
	}

	function insert() {
		//		document.stockInventoryForm.actionType.value = "REFRESH";
		document.stockModelDetailsForm.submit();
	}
</script>

<form:form name="stockAssgnForm" method="POST"
	commandName="stockAssgn">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><!--content-->
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td class="table_title">Stock Inventory Maintenance (Assigned Serial Number - with stock change function)</td>
				</tr>
			</table>
			<%-- ********************   SEARCHING KEY --- ROW 1 (ID DOC + search)   ***************************** --%>
			<table width="100%" border="0" cellspacing="1" class="contenttext"
				background="images/background2.jpg">
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td width="10%">
						<div align="left">Serial Number:</div>
					</td>
					<td width="40%">
						<form:input path="imei" />
					</td>
					<td width="10%">
						<div align="left">Type:</div>
					</td>
					<td width="40%">
						<form:input path="type" />
					</td>
				</tr>
				<tr>
					<td width="10%">
						<div align="left">Model:</div>
					</td>
					<td width="40%">
						<form:input path="model" />
					</td>
					<td width="10%">
						<div align="left">Item Code:</div>
					</td>
					<td width="40%">
						<form:input path="itemCode" />
					</td>
				</tr>
				<tr>
					<td width="10%">
						<div align="left">Location:</div>
					</td>
					<td width="40%">
						<form:input path="location" />
					</td>
					<td width="10%">
						<div align="left">Status:</div>
					</td>
					<td width="40%">
						<form:input path="status" />
					</td>
				</tr>
				<tr>
					<td width="10%">
						<div align="left">Order Id:</div>
					</td>
					<td width="40%">
						<form:input path="orderId" />
					</td>
					<td width="10%">
						<div align="left">MRT:</div>
					</td>
					<td width="40%">
						<form:input path="mrt" />
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
			<div><a href="#" class="nextbutton" onClick="insert()">Change Serial Number</a></div>
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