<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<script>
	function orderFormSubmit(orderType) {
		var answer = confirm("Confirm to save?");
		var sum = 0;
		$('#payTranSummary tbody tr').each(function(index, tr) {
			
			var amount = $(tr).find("td:eq(1)").text();
			sum = sum + parseFloat(amount);
			
			
		});
		
		if (sum < 0) {
			alert('Reminder: Payment has refund amount');
		}
		
		if (answer) {
			document.mobccsurgentordermanagementForm.actionType.value = orderType;
			showLoading();
			document.mobccsurgentordermanagementForm.submit();

		}
	}
	
	function paymentTrxn() {
		var sURL = "mobccspaymenttranshistory.html?orderId="+"${orderId}";
		var vArguments = new Object();
		var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
		window.showModalDialog(sURL, vArguments, sFeatures);
	}
	
	$(document).ready(function () {
		if('${mobccsurgentordermanagement.orderLockInd}' == 'true') {
			$('#submitButton').attr('disabled', true);
			$('#falloutButton').attr('disabled', true);
			$('#falloutSelection').attr('disabled', true);
			$('#errorOutRow').text('Order Locked').css('color', 'red');
		}
	}) ;
	
</script>
<style type="text/css">
.contenttext tr td:first-child {
	width: 25%
}

.contenttextgary {
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif";
	padding-left: 5px;
	font-size: 14px;
	color: #616161;
}

.contenttextgreen {
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif";
	padding-left: 5px;
	font-size: 14px;
	color: #64a903;
	font-weight: bold;
}
</style>

<table>
	<tr>
		<td class="contenttextgreen" height="30">Order Id : ${orderId} / Application Date : ${appDate}</td>
	</tr>
</table>
<!--[if IE 6]>
<style type="text/css">
.contenttext tr td { width: expression(this.previousSibling==null?'25%':'');}
</style>
<![endif]-->
<form:form method="POST" name="mobccsurgentordermanagementForm"
	commandName="mobccsurgentordermanagement">


	<table width="100%" border="1">
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="1">
					<tr>
						<td class="contenttextgreen" height="40">Urgent Order
							Management</td>
						<td class="contenttextgary">
						</td>
					</tr>
					<tr><td id="errorOutRow"></td></tr>
				</table> 
				
				<!-- STEP1 -->
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td class="table_title">Step 1 : Stock Assignment (Manual
							Assign)</td>
					</tr>
					<tr>
						<td class="contenttext">Manual Assign stock/fall out order if
							out of stock</td>
					</tr>
				</table> 
				<!-- STEP2 Support Document --> 
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td class="table_title" colspan="4">Step 2 : Support Document</td>
						
					</tr>
					<tr>
						<td class="table_title"><span style="display:inline-block;width: 350px">Support Document</span></td>
						<td class="table_title"><span style="display:inline-block;width: 70px">Received By Fax Serial</span></td>
						<td class="table_title"><span style="display:inline-block;width: 300px">Fax Server Serial No.</span></td>
						<td class="table_title"><span style="display:inline-block;width: 70px">Verified</span></td>
					</tr>
					<c:forEach items="${supportingDoc.mobCcsSupportDocDTOList}" var="doc">
						<c:if test="${doc.mandatory==true}">
							<tr>
								<td class="contenttext">${doc.docDesc}</td>
								<c:if test="${doc.receivedByFax==true}">
									<td class="contenttext">Yes</td>
								</c:if>
								<c:if test="${doc.receivedByFax==false}">
									<td class="contenttext">No</td>
								</c:if>
								<td class="contenttext">${doc.faxServerSerialNo}</td>
								<c:if test="${doc.verified=='Y'}">
									<td class="contenttext">Yes</td>
								</c:if>
								<c:if test="${doc.verified=='N'}">
									<td class="contenttext">No</td>
								</c:if>
							<tr>
						</c:if>
					</c:forEach>
					<tr>
						<td>
							<c:if test="${isSupportDocVerified==true}">
								<form:checkbox path="supportDocVerifiedInd" disabled="true"/>Verified
							</c:if>
							<c:if test="${isSupportDocVerified==false}">
								<form:checkbox path="supportDocVerifiedInd"/>Verified
							</c:if>
							<form:errors path="supportDocVerifiedInd" cssClass="error" /></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" class="contenttext">
					<tr>
						<td class="table_title">Step 3 : Payment Settlement</td>
					</tr>
					<tr>
						<td>
						 <!-- Monthly Payment Method --> 			
					<table width="100%" border="0" cellspacing="0" class="contenttext">
						<tr>
							<td class="table_title" colspan="2">Monthly Payment Method</td>
						</tr>
						<c:if test="${not empty paymentMonthy }">
						<tr>
							<td>Payment Method</td>
							<td><c:choose>
									<c:when test="${paymentMonthy.payMethodType == 'M'}">Cash</c:when>
									<c:when test="${paymentMonthy.payMethodType == 'A'}">Auto</c:when>
									<c:when test="${paymentMonthy.payMethodType == 'C'}">Credit Card</c:when>
								</c:choose>
							</td>
						</tr>
						<c:choose>
							<c:when test="${paymentMonthy.payMethodType == 'M'}">
							</c:when>
							<c:when test="${paymentMonthy.payMethodType == 'A'}">
								<tr>
									<td>Bank Name</td>
									<td>${paymentMonthy.bankName}</td>
								</tr>
								<tr>
									<td>Account No</td>
									<td>${paymentMonthy.bankAcctNum}</td>
								</tr>
								<tr>
									<td>Account Holder's Name</td>
									<td>${paymentMonthy.bankAcctHolderName}</td>
								</tr>
							</c:when>
							<c:when test="${paymentMonthy.payMethodType == 'C'}">
								<tr>
									<td>Credit Card Number</td>
									<td>${paymentMonthy.creditCardNum}</td>
								</tr>
								<tr>
									<td>Expire Date</td>
									<td>${paymentMonthy.creditExpiryMonth} /
										${paymentMonthy.creditExpiryYear}</td>
								</tr>
								<tr>
									<td>Card Holder's Name</td>
									<td>${paymentMonthy.creditCardHolderName}</td>
								</tr>
								<tr>
									<td>Credit Card Issue Bank</td>
									<td>${paymentMonthy.creditCardIssueBankCd} ${paymentMonthy.creditCardIssueBankName}</td>
								</tr>
							</c:when>
						</c:choose>
						</c:if>
						<tr>
							<td>
								<c:if test="${isPaymantMonthlyVerified == true}">
									<form:checkbox path="paymantMonthlyVerifiedInd" disabled="true"/>Verified
									<form:errors path="paymantMonthlyVerifiedInd" cssClass="error" />
								</c:if>
								<c:if test="${isPaymantMonthlyVerified == false}">
									<form:checkbox path="paymantMonthlyVerifiedInd"/>Verified
									<form:errors path="paymantMonthlyVerifiedInd" cssClass="error" />
								</c:if>
							</td>
						</tr>
					</table>
						</td>
						
					</tr>
					<tr>
					<td>
					<!-- Upfront Payment Method --> 
					<table width="100%" border="0" cellspacing="0" class="contenttext">
						<tr>
							<td class="table_title" colspan="2">Upfront Payment Method</td>
						</tr>
						<c:if test="${not empty paymentUpFront }">
						<tr>
							<td>Payment Method</td>
							<td><c:choose>
									<c:when test="${paymentUpFront.payMethodType  == 'M'}">Cash</c:when>
									<c:when test="${paymentUpFront.payMethodType  == 'C'}">Credit Card</c:when>
									<c:when test="${paymentUpFront.payMethodType  == 'I'}">Credit Card Installment</c:when>
								</c:choose>
							
							</td>
						</tr>
						<c:choose>
							<c:when
								test="${paymentUpFront.payMethodType == 'C' || paymentUpFront.payMethodType == 'I'}">

								<tr>
									<td>Credit Card Number</td>
									<td>${paymentUpFront.creditCardNum}</td>
								</tr>
								<tr>
									<td>Expire Date</td>
									<td>${paymentUpFront.creditExpiryMonth} /
										${paymentUpFront.creditExpiryYear}</td>
								</tr>
								<tr>
									<td>Card Holder's Name</td>
									<td>${paymentUpFront.creditCardHolderName}</td>
								</tr>
								<tr>
									<td>Credit Card Issue Bank</td>
									<td>${paymentUpFront.creditCardIssueBankCd} ${paymentUpFront.creditCardIssueBankName}</td>
								</tr>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${paymentUpFront.payMethodType == 'I'}">
								<tr>
									<td>Installment Schedule</td>
									<td>${paymentUpFront.ccInstSchedule}</td>
								</tr>
							</c:when>
						</c:choose>
						
						 <!-- Payment Transaction Summary --> 
						<table width="50%" border="1" cellspacing="0" class="contenttext" id="payTranSummary">
							<thead>
							<div>
								<input type="button" value="Payment Transaction" onclick="paymentTrxn();">
								</div>
							<tr>
								<td class="table_title" width="20%">Type</td>
								<td class="table_title" width="10%">HKD</td>
								<td class="table_title" width="10%">Approval Code</td>
								<td class="table_title" width="10%">Batch Number</td>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${mobccsurgentordermanagement.paymentTransList}" var="payList" varStatus="payStatus">
								<tr>
								<td>${payList.payTypeDesc}</td>
								<td>${payList.pay_amount}</td>
								<c:if test="${payList.approveFlag == false}">
							 		<td/>
								 </c:if>
								<c:if test="${payList.approveFlag == true}">
							 		<td align="center">
							 			<form:input path="paymentTransList[${payStatus.index}].approveCode" />
										<form:errors path="paymentTransList[${payStatus.index}].approveCode" cssClass="error" />
									</td>
								 </c:if>
								 <c:if test="${payList.batchNumFlag == false}">
							 		<td/>
								 </c:if>
								<c:if test="${payList.batchNumFlag == true}">
							 		<td align="center">
							 			<form:input path="paymentTransList[${payStatus.index}].batchNum" maxlength="20"/>
										<form:errors path="paymentTransList[${payStatus.index}].batchNum" cssClass="error" />
									</td>
								 </c:if>
								 </tr>
							</c:forEach>	
							</tbody>
						</table>
						</c:if>
							<tr >
								<td colspan="3">
									<c:if test="${isPaymantUpfrontVerified == true}">
										<form:checkbox path="paymantUpfrontVerifiedInd" disabled="true"/>Verified
										<form:errors path="paymantUpfrontVerifiedInd" cssClass="error" />
									</c:if>
									<c:if test="${isPaymantUpfrontVerified == false}">
										<form:checkbox path="paymantUpfrontVerifiedInd"/>Verified
										<form:errors path="paymantUpfrontVerifiedInd" cssClass="error" />
									</c:if>
								</td>
							</tr>
					</table>
					</td>
					</tr>
				</table> 				
				<!--content-->
			</td>
		</tr>
	</table>

	<!-- button -->
	<table width="100%" border="0" cellspacing="0" class="contenttext">
		<tr align="left">
			<td align="left">
				<input type="button" value="Frozen to Warehouse" id="submitButton" onclick="javascript:orderFormSubmit('SUBMITTOBOM');">
				<form:errors path="submitErrorPrint" cssClass="error" />
			</td>
		</tr>
		<tr align="left">
			<td align="left"><form:select path="falloutReasonCd" id="falloutSelection">
					<form:option value="" label="Select" />
					<form:options items="${falloutCodeList}" itemValue="codeId"
						itemLabel="codeDesc" />

				</form:select>
				<form:errors path="falloutReasonCd" cssClass="error" /> 
				<input type="button" value="Fallout Order" id="falloutButton"
				onclick="javascript:orderFormSubmit('FALLOUT');"></td>
		</tr>
	</table>

	<!-- end of button -->
	<input type="hidden" name="currentView"
		value="mobccsurgentordermanagement" />
	
	<form:hidden path="actionType" />
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>