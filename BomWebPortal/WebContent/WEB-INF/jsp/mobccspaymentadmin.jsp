<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script language="javascript">
	document.onkeypress = processKey;
	function processKey(e) {

		if (null == e)
			e = window.event;
		if (e.keyCode == 13) {
			paymentTransRetrieve();
		}
	}

	$(function() {
		// Datepicker

		$('#startDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "-100Y",
			maxDate : "+100Y", //Y is year, M is month, D is day  
			yearRange : "0:+100" //the range shown in the year selection box

		});
		$('#endDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "-100Y",
			maxDate : "+100Y", //Y is year, M is month, D is day  
			yearRange : "0:+100" //the range shown in the year selection box

		});

	});

	function paymentTransQuery() {
		document.mobCcsPaymentAdminForm.actionType.value = "QUERY";
		document.mobCcsPaymentAdminForm.validated.value = false;
		document.mobCcsPaymentAdminForm.submit();
	}
</script>
<form:form name="mobCcsPaymentAdminForm" method="POST"
	commandName="mobCcsPaymentAdmin">
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="0" rules="none">
					<tr>
						<td class="table_title">Payment Admin</td>
					</tr>
				</table>

				<table width="100%" border="0" cellspacing="1" class="tablegrey"
					background="images/background2.jpg">
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Payment Transaction Start Date</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="startDateStr" id="startDateDatepicker"
									readonly="true" maxlength="20" />
							</div>
						</td>
						<td colspan="1">
							<div align="right">Payment Transaction End Date</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="endDateStr" id="endDateDatepicker"
									readonly="true" maxlength="20" />
							</div>
						</td>
					</tr>

					<%-- ***************   SEARCHING KEY --- ROW 2 (Payment Method + MRT)   ***************************** --%>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Payment Method</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:select path="payMethod">
									<form:option value="" label="All" />
									<form:options items="${mobCcsPaymentAdmin.payMethodList}" itemValue="codeId"
										itemLabel="codeDesc" />
								</form:select>
							</div>
						</td>

						<td colspan="1">
							<div align="right">MRT</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="mrt" />
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
						<td colspan="1">
							<div>
								<a href="#" class="nextbutton"
									onClick="javascript:paymentTransQuery();"> Search </a>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4"><form:errors path="mobCcSPaymentAdminError"
								cssClass="error" /> <form:errors path="startDate"
								cssClass="error" /> <form:errors path="endDate"
								cssClass="error" /> <form:errors path="payMethodList"
								cssClass="error" /> <form:errors path="mrt" cssClass="error" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<c:if test='${mobCcsPaymentAdmin.validated}'>
		<table width="100%" class="tablegrey">
			<tr>
				<td bgcolor="#FFFFFF">
					<!--content-->
					<table width="100%" border="0" cellspacing="1" rules="none">
						<tr>
							<td class="table_title">Payment Transaction History</td>
						</tr>
					</table>
				</td>
			</tr>
			<c:choose>
				<c:when test='${empty mobCcsPaymentAdmin.mobCcsPaymentTransDTOList}'>
					<tr>
						<td bgcolor="#FFFFFF">No Record Found.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td bgcolor="#FFFFFF">
							<!--content-->
							<table width="100%" border="0" cellspacing="0" rules="none">
								<tr>
									<table width="100%" border="0" cellspacing="0"
										class="contenttext" bgcolor="#FFFFFF">
										<tr class="contenttextgreen">
											<td class="table_title">MRT</td>
											<td class="table_title">Order ID</td>
											<td class="table_title">Transaction Date</td>
											<td class="table_title">Card Type</td>
											<td class="table_title">Payment Method</td>
											<td class="table_title">Amount</td>
											<td class="table_title">Ref. No.</td>
											<td class="table_title">Handled by</td>
											<c:forEach
												items="${mobCcsPaymentAdmin.mobCcsPaymentTransDTOList}"
												var="transList">
												<tr>
													<td>${transList.mrt}&nbsp;</td>
													<td>${transList.order_id}&nbsp;</td>
													<td>${transList.trans_date}&nbsp;</td>
													<td>${transList.cc_type}&nbsp;</td>
													<td>${transList.pay_method}&nbsp;</td>
													<td>${transList.pay_amount}&nbsp;</td>
													<td>${transList.ref_no}&nbsp;</td>
													<td>${transList.handled_by}&nbsp;</td>
												</tr>
											</c:forEach>
										</tr>
									</table>
								</tr>
							</table>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</c:if>

	<table width="100%" border="0" cellspacing="0" class="contenttext">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<div>
					<a href="mobccspaymentnewtrans.html" class="nextbutton"> New
						Transaction </a>
				</div>
			</td>
			<td>
				<div>
					<a href="#" class="nextbutton" align="center"> Quit </a>
				</div>
			</td>
		</tr>
	</table>
	<input type="hidden" name="actionType" id="actionType" />
	<input type="hidden" name="validated" id="validated" />
</form:form>
<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>