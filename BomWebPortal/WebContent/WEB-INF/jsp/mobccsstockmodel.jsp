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
			formSubmit('REFRESH');
		}
	}
	
	function formSubmit(actType) {
		document.stockModelForm.actionType.value = actType;
		document.stockModelForm.submit();
	}

</script>

<form:form name="stockModelForm" method="POST"
	commandName="stockModel">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><!--content-->
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td class="table_title">Stock Inventory Maintenance (Stock Model)</td>
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
					<div align="left">Type:</div>
					</td>
					<td width="40%"><form:select path="type">
						<form:option value="NONE" label="Select" />
						<form:options items="${typeList}" itemValue="codeId" itemLabel="codeDesc"/>
					</form:select></td>
					<td width="10%">
					<div align="left">Model:</div>
					</td>
					<td width="30%"><form:input path="model" /></td>
					<td width="10%" align="right">
					<div><a href="#" class="nextbutton"
						onClick="javascript:formSubmit('REFRESH')"> Search </a></div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<c:if test='${actionType == "REFRESH"}'>
	<!-- ******************** STOCK SEARCH RESULT ************************ -->
	<c:if test='${empty resultList}'>

		<table width="100%" class="tablegrey">
			<tr>
				<td bgcolor="#FFFFFF"><!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Stock Search Result</td>
					</tr>
				</table>
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
				</td>
			</tr>
		</table>

	</c:if>
	<c:if test='${!empty resultList}'>

		<table width="100%" class="tablegrey">
			<tr>
				<td bgcolor="#FFFFFF"><!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Stock Search Result</td>
					</tr>
				</table>
				<%-- ***************************   RESULT TABLE   ****************************** --%>
				<table width="100%" class="tablegrey">
					<tr>
						<td>
						<table width="100%" border="1" cellspacing="0" class="contenttext"
							bgcolor="#FFFFFF">

							<tr class="contenttextgreen">

								<td class="table_title">Item Code</td>
								<td class="table_title">Model</td>
								<td class="table_title">Mode</td>

								<c:forEach items="${resultList}" var="result">
									<tr>
										<td>
											&nbsp;
											<a href="<c:url value="mobccsstockmodeldetailsupdate.html">
														<c:param name="itemCode" value="${result.itemCode}"/>
													</c:url>">${result.itemCode}
											</a>
										</td>

										<td>${result.model}&nbsp;</td>

<!--										<td>${result.mode}&nbsp;</td>-->
										<c:choose>
											<c:when test='${result.mode == "01"}'>
												<td>AUTO</td>
											</c:when>
											<c:when test='${result.mode == "02"}'>
												<td>MANUAL</td>
											</c:when>
											<c:otherwise>
												<td>${result.mode}</td>
											</c:otherwise>
										</c:choose>

									</tr>
								</c:forEach>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
	</c:if>
	</c:if>
	
	<br>
	
	<%-- ********************************   BOTTOM BUTTONS   ************************************* --%>
	<table width="100%" border="0" cellspacing="0">
		<tr>
			<td align="center" width="50%">
			<div><a href="#" class="nextbutton" onClick="javascript:formSubmit('DIRECT')">New Model</a></div>
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