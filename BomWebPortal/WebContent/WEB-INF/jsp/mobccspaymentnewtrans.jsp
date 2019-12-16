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
			paymentTransQuery();
		}
	}
	$(function() {
		// Datepicker

		$('#transDateDatepicker').datepicker({
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
		$('#actionType').val("QUERY");
		document.mobCcsPaymentNewTransForm.submit();
	}
	function paymentTransInsert() {
		$('#actionType').val("INSERT");
		document.mobCcsPaymentNewTransForm.submit();
	}	
</script>
<form:form name="mobCcsPaymentNewTransForm" method="POST"
	commandName="mobCcsPaymentNewTrans">
	
	<table width="100%" class="tablegrey" border="0">
		<tr>
			<td class="table_title" colspan="4">Payment Admin (New Transaction)</td>			
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="0" class="contenttext"
					background="images/background2.jpg">
					<td colspan="1">
						<div align="right">Order ID</div>
					</td>
					<td colspan="1">
						<div align="left">
							<form:input path="order_id" />
						</div>
					</td>
					<td colspan="1">&nbsp;</td>
					<td colspan="1">
						<div>
							<a href="#" class="nextbutton"
								onClick="javascript:paymentTransQuery();"> Search </a>
						</div>
					</td>
				</table>

			</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">

				<table width="100%" border="0" cellspacing="0" class="contenttext"
					background="images/background2.jpg">
					<tr>
						<td class="table_title" colspan="4">Upfront Payment Method</td>			
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">MRT</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="mrt" disabled="true"/>
							</div>
						</td>						
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Credit Card Type</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="cc_type" disabled="true"/>
							</div>
						</td>
						<td colspan="1">
							<div align="right">Payment Combination</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="pay_method" disabled="true"/>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Credit Card No.</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="cc_num" disabled="true"/>
							</div>
						</td>
						<td colspan="1">
							<div align="right">In Advance Amount</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="ad_amount" disabled="true"/>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Card Holder Name</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="cc_hold_name" disabled="true"/>
							</div>
						</td>
						<td colspan="1">
							<div align="right">Upfront Payment Amount</div>
						</td>
						<td colspan="1">
							<div align="left">
								<form:input path="up_amount" disabled="true"/>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Valid Thru</div>
						</td>
						<td>
							<div align="left">
								<form:input path="cc_exp_date" disabled="true"/>
							</div>
						</td>
						<td colspan="1">
							<div align="right">Outstanding Amount</div>
						</td>
						<td>
							<div align="left">
								<form:input path="out_amout" disabled="true"/>
							</div>
						</td>						
					</tr>
					<c:if test='${!empty mobCcsPaymentNewTrans.msg && mobCcsPaymentNewTrans.actionType == "QUERY"}'>
						<tr>
							<td colspan="4">
								<div align="left" style="color:red">
									<c:out value="${mobCcsPaymentNewTrans.msg}" />								
								</div>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="table_title" colspan="4">Payment Transaction Update</td>			
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Ref. No.</div>
						</td>
						<td>
							<div align="left">
								<form:input path="ref_no" />
							</div>
						</td>
						<td colspan="1">&nbsp;</td>				
						<td colspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Transaction Date</div>
						</td>
						<td>
							<div align="left">
								<form:input path="transDateStr" id="transDateDatepicker" readonly="true" maxlength="20" />
							</div>
						</td>
						<td colspan="1">&nbsp;</td>				
						<td colspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Payment Combination</div>
						</td>
						<td>
							<div align="left">							
								<form:select path="pay_comb">								
									<form:options items="${mobCcsPaymentNewTrans.payMethodList}" itemValue="codeId" itemLabel="codeDesc" />
								</form:select>							
							</div>
						</td>
						<td colspan="1">&nbsp;</td>				
						<td colspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Payment Amount</div>
						</td>
						<td>
							<div align="left">
								<form:input path="pay_amount" />
							</div>
						</td>
						<td colspan="1">&nbsp;</td>			
						<td colspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">
							<div align="right">Handled By</div>
						</td>
						<td>
							<div align="left">
								<form:input path="lastUpdateBy" />
							</div>
						</td>
						<td colspan="1">&nbsp;</td>				
						<td colspan="1">
							<div><a href="#" class="nextbutton" onClick="javascript:paymentTransInsert();"> Save </a></div>
						</td>
					</tr>
					<c:if test='${!empty mobCcsPaymentNewTrans.msg && mobCcsPaymentNewTrans.actionType == "INSERT"}'>
						<tr>
							<td colspan="4">
								<div align="left" style="color:red">
									<c:out value="${mobCcsPaymentNewTrans.msg}" />								
								</div>
							</td>
						</tr>
					</c:if>
					<tr>
						<td colspan="4">			
							<form:errors path="mrt" cssClass="error" />
							<form:errors path="order_id" cssClass="error" />
							<form:errors path="cc_type" cssClass="error" />
							<form:errors path="cc_num" cssClass="error" />
							<form:errors path="cc_hold_name" cssClass="error" />
							<form:errors path="cc_exp_date" cssClass="error" />
							<form:errors path="pay_comb" cssClass="error" />
						</td>
					</tr>
				</table>			
			</td>
		</tr>		
	</table>
	<table width="100%" border="0">
		<tr>
			<td colspan="1">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="4">
				<div align="right">
					<a href="#" class="nextbutton"> Quit </a>
				</div>
			</td>
		</tr>
	</table>
	<input type="hidden" name="actionType" id="actionType"/>
</form:form>
<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>