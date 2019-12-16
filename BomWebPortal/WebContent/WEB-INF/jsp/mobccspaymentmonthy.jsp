<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobccspayment" />
</jsp:include>
<%@page import="java.util.Calendar;" %>
<%int expiryYear = Calendar.getInstance().get(Calendar.YEAR);%>
<style type="text/css">
.payment_mask { position: absolute; background-color: #f2f2f2; font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; z-index: 9999; opacity: 0.4; filter: alpha(opacity=40); }
.payment_mask_note { padding-left: 1em; font-size: small; color: red; font-weight: bold;  font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; z-index: 10000 }
.paymentMethod_sel label { font-weight: bold }
</style>
<script type="text/javascript" src="js/jquery.maskedinput.min.js"></script>
<script type="text/javascript">
function resizePaymentMask() {
	var $paymentSel = $(".payment_sel");
	$(".payment_mask").css({ width: $paymentSel.width(), height: $paymentSel.height() });
}
	function formSubmit(actType) {
		
			var selectVal=$('input[name=payMethodType]:checked').val();
			if (selectVal=="A"){
				
				document.mobCcsPaymentMonthyForm.bankName.value = document.mobCcsPaymentMonthyForm.bankCode.options[document.mobCcsPaymentMonthyForm.bankCode.selectedIndex].text;
				document.mobCcsPaymentMonthyForm.branchName.value = document.mobCcsPaymentMonthyForm.branchCode.options[document.mobCcsPaymentMonthyForm.branchCode.selectedIndex].text;
			}
			
			//add by herbert 20110721 ---start
			if (selectVal=="C"){
				document.mobCcsPaymentMonthyForm.creditCardIssueBankName.value = document.mobCcsPaymentMonthyForm.creditCardIssueBankCd.options[document.mobCcsPaymentMonthyForm.creditCardIssueBankCd.selectedIndex].text;
			}
			//add by herbert 20110721 ---end
			document.mobCcsPaymentMonthyForm.actionType.value = actType;
			//document.mobCcsPaymentMonthyForm.submit();//edit by wilson
			$("#mobCcsPaymentMonthyForm").submit();
	}

	function assignBankName(selBox){
		document.mobCcsPaymentMonthyForm.creditCardIssueBank.value = selBox.options[selBox.selectedIndex].text;
	}
	
	function refreshForm(cardnum){
		if (typeof cardnum == "string" && cardnum.length > 0) {
			var ccType = "NONE";
			if (cardnum.substring(0, 1) == "4") {
				ccType = "01"; // VISA
			} else if (cardnum.substring(0, 2) == "51" || cardnum.substring(0, 2) == "52"
					 || cardnum.substring(0, 2) == "53" || cardnum.substring(0, 2) == "54"
					 || cardnum.substring(0, 2) == "55") {
				ccType = "02"; // MASTER
			} else if (cardnum.substring(0, 2) == "34" || cardnum.substring(0, 2) == "37") {
				ccType = "04"; // AMEX
			}
			$("#creditCardType")[0].selectedIndex = 0;
			$("#creditCardType").children().each(function() {
				if ($(this).val() == ccType) {
					$(this).attr("selected", true);
					return false;
				}
			});
			$("input[name=creditCardNum]").val(cardnum);
			$("input[name=ceksSubmit]").val("Y");
		}
	}

	function openCEKSWindow(){
		$("#creditCardType")[0].selectedIndex = 0;
		$("input[name=creditCardNum]").val("");
		$("input[name=ceksSubmit]").val("N");
		var ceksLink = "mobccsceks.html";
		window.open(ceksLink, "_blank", "width=550, height=400, resizable=no, toolbar=yes, location=yes, menubar=yes, status=yes, left=100, top=100");
	}
	
	function setToken(v1, v2, v3, v5){
		var maskedToken = "";
		
		if(v5.length > 0){
			maskedToken = v5.substring(0,4) + "********" + v5.substring(12);
			document.getElementById("token").value = v5;
		} else {
			maskedToken = "";
			document.getElementById("token").value = "";
		}
			
		document.getElementById("mask_token").value = maskedToken;
		
		document.mobCcsPaymentMonthyForm.ceksSubmit.value = "N";
		
		alert(document.mobCcsPaymentMonthyForm.ceksSubmit.value 
				+ " token " + v5
				+ " mask_token " + maskedToken);
		
	}
	

	function show( which )
	{
		//M - cashDiv, C - creditCardDiv, A-autoPayDiv
		if (which=='A'){
			$("#commonDiv").show();
			$("#autoPayDiv").show();
			$("#creditCardDiv").hide();
			$("#creditCardDiv2").hide();
	    }
		if (which=='C'){
			$("#commonDiv").show();
			$("#autoPayDiv").hide();
			$("#creditCardDiv").show();
			$("#creditCardDiv2").show();
		}
		if (which=='M'){
			$("#commonDiv").hide();
			$("#autoPayDiv").hide();
			$("#creditCardDiv").hide();
			$("#creditCardDiv2").hide();
		}
	}
	
	function refreshParty(){
		var party = document.mobCcsPaymentMonthyForm.thirdPartyInd.value;
		if (party == 'Y') {
			$("input[name=bankAcctHolderIdNum]").val("");
			$("input[name=bankAcctHolderName]").val("");
			$("input[name=creditCardHolderName]").val("");
			$("input[name=creditCardDocNum]").val("");
			$("#btnCopyFromCustInfo").hide();
		} else {
			$("#btnCopyFromCustInfo").show();
		}
	}
	

	var model=new Array();
	${bankScriptArrayString};

	function updatemodels(selectedIndex, selectedValue){
		var modellist = document.getElementById('branchCode');

		modellist.options.length   =   0;//remove all
		if (selectedIndex>=0){
			modellist.options[modellist.options.length]=new Option("-----Select the branch-----","NONE");
			for (i=0; i<model[selectedIndex].length; i++){
				modellist.options[modellist.options.length]=new Option(model[selectedIndex][i].split("|")[1], model[selectedIndex][i].split("|")[0]);
				
			}
		}
		
	}
	
	$(document).ready(function() {//add 20110603
		////M - Cash, C - Credit Card, A-AutoPay
		var selectVal=$('input[name=payMethodType]:checked').val();
		
		show( selectVal );
			
		var defaultValue ='${selectedBranchCode}';
		updatemodels($("select#bankCode").prop("selectedIndex"), '111');
		$("select#branchCode option[value="+defaultValue+"]").prop("selected", true); //update branchSelected
		
		var defaultValue ='${creditCardIssueBankCd}';
		$("select#creditCardIssueBankCd option[value="+defaultValue+"]").prop("selected", true); //update creditCardIssueBankCd
		
		var creditCardInd = document.mobCcsPaymentMonthyForm.creditCardInd.value;
		//alert("creditCardInd:" + creditCardInd);
		if ( creditCardInd == null || creditCardInd == ''){
			$('#payMethodTypeM').attr('disabled', false);
			$('#payMethodTypeA').attr('disabled', false);
		} else {
			$('#payMethodTypeM').attr('disabled', true);
			$('#payMethodTypeA').attr('disabled', true);
		}
		
		var studentPlanSubInd ='${studentPlanSubInd}';
		if (studentPlanSubInd == "Y") { 
			$('select[name=thirdPartyInd]').attr('disabled', true);
			$('input[name=creditCardHolderName]').attr('disabled', true);
			$('select[name=creditCardDocType]').attr('disabled', true);
			$('input[name=creditCardDocNum]').attr('disabled', true);
 		 } 
		
		
		var creditCardVerifiedInd = document.mobCcsPaymentMonthyForm.creditCardVerifiedInd.value;
		if ( creditCardVerifiedInd == 'N'){
			alert("Credit Card Verification Fail!");
		}
		
		var party = document.mobCcsPaymentMonthyForm.thirdPartyInd.value;
		if (party == 'Y') {
			$("#btnCopyFromCustInfo").hide();
		} else {
			$("#btnCopyFromCustInfo").show();
		}
		
		var ocid = "<c:out value="${orderDTO.ocid}"/>";
		$(".payment_sel").show();
		if (ocid != null && $.trim(ocid).length > 0) {
			$(".payment_mask").show();
			$(".payment_mask_note").show();
			resizePaymentMask();
			$(window).resize(resizePaymentMask);
			$(".paymentMethod_sel input:not(:checked)").attr("disabled", true);
			$(".payment_detail input").attr("readonly", true);
			$(".payment_detail select option:not(:selected)").remove();
		}
		
		 var acctType ='${acctType}';
		if (acctType == "current"){
			$('input[name=payMethodType]').attr('disabled', true);
		
			//credit card disabled field
			$('select[name=thirdPartyInd]').attr('disabled', true);
			$('input[name=creditCardHolderName]').attr('disabled', true);
			$('select[name=creditCardDocType]').attr('disabled', true);
			$('input[name=creditCardDocNum]').attr('disabled', true);
			$('select[name=creditCardType]').attr('disabled', true);
			$('input[name=creditCardNum]').attr('disabled', true);
			$('select[name=creditExpiryMonth]').attr('disabled', true);
			$('select[name=creditExpiryYear]').attr('disabled', true);
			$('select[name=creditCardIssueBankCd]').attr('disabled', true);
			
			//autoPay disabled field
			$('select[name=bankAcctHolderIdType]').attr('disabled', true);
			$('select[name=bankAcctHolderIdType]').attr('disabled', true);
			$('input[name=bankAcctHolderIdNum]').attr('disabled', true);
			$('select[name=bankCode]').attr('disabled', true);
			$('select[name=branchCode]').attr('disabled', true);
			$('input[name=bankAcctHolderName]').attr('disabled', true);
			//$('input[name=autopayUpperLimitAmt]').attr('disabled', true);
			$('input[name=bankAcctNum]').attr('disabled', true);
			$('input[name=autopayApplDateStr]').attr('disabled', true);
			$('#btnCopyFromCustInfo').attr('disabled', true);
			$('#btnCopyFromCredit').attr('disabled', true);
			$('#btnInputCreditCard').attr('disabled', true);
			
			
		}
		// set acccount in digit and max length = 9
		$("input[name=bankAcctNum]").mask("?999999999", { placeholder: "" });
	});
</script>

<form:form method="POST" name="mobCcsPaymentMonthyForm" id="mobCcsPaymentMonthyForm" commandName="mobccspaymentmonthy">
	<form:errors path="payMethodType" cssClass="error" />
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0">
				<tr>
					<td class="table_title"><spring:message code="label.mobccspayment.header"/></td>
					<td class="table_title" align="right">
						<c:if test="${orderDTO.busTxnType == 'PEND' && workStatus == 'recall'}">
							<form:checkbox path="byPassValidation" disabled="true"/>By-pass validation
						</c:if>
						<c:if test="${orderDTO.busTxnType != 'PEND'}">
							<form:checkbox path="byPassValidation"/>By-pass validation
						</c:if>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td class="table_title"><spring:message code="label.mobccspayment.header.monthly"/></td>
				</tr>
			</table>

			<div class="payment_mask_note" style="display:none">Remarks: Not allow to change payment information after BOM order creation (OCID: <c:out value="${orderDTO.ocid}"/>).</div>

			<div class="payment_mask" style="display:none"></div>

			<table width="100%" border="0" cellspacing="0" class="payment_sel" style="display:none">
			<tr>
			<td>
			<!-- cash table -->
					<table width="100%" border="0" cellspacing="1" class="contenttext">
						<tr class="paymentMethod_sel">
							<td>
								<label>
									<c:choose>
									<c:when test='${mobccspaymentmonthy.mFlag == "Y"}'>
									<form:radiobutton id="payMethodTypeM" path="payMethodType" value="M" onclick="show('M')"/>
									<spring:message code="label.payMethodType.M"/>
									</c:when>
									<c:otherwise>
									<form:radiobutton id="payMethodTypeM3" path="payMethodType" value="M"  disabled = "true"/>
									<spring:message code="label.payMethodType.M"/>
									</c:otherwise>
									</c:choose>
								</label>
								<span class="contenttext_red">*</span>
							</td>
							<td>
								<label>
									<c:choose>
									<c:when test='${mobccspaymentmonthy.aFlag == "Y"}'>
									<form:radiobutton id="payMethodTypeA" path="payMethodType" value="A" onclick="show('A')"/>
									<spring:message code="label.payMethodType.A"/>
									</c:when>
									<c:otherwise>
									<form:radiobutton id="payMethodTypeA3" path="payMethodType" value="A"  disabled = "true"/>
									<spring:message code="label.payMethodType.A"/>
									</c:otherwise>
									</c:choose>
									
								</label>
								<span class="contenttext_red">*</span>
							</td>
							<td>
								<label>
									<c:choose>
									<c:when test='${mobccspaymentmonthy.ccFlag == "Y"}'>
									<form:radiobutton id="payMethodTypeC" path="payMethodType" value="C" onclick="show('C')"/>
									<spring:message code="label.payMethodType.CTnG"/>
									</c:when>
									<c:otherwise>
									<form:radiobutton id="payMethodTypeC3" path="payMethodType" value="C" disabled = "true"/>
									<spring:message code="label.payMethodType.CTnG"/>
									</c:otherwise>
									</c:choose>
								</label>
								<span class="contenttext_red">*</span>
							</td>
						</tr>
						<tr>
						<td>&nbsp; </td>
						</tr>
					</table>
					
			<!-- end cash table -->
			
			</td>
			</tr>
			
				<!-- Credit card info button -->
				<tr><td>
						<div id="creditCardDiv2">
						<table width="100%" border="0" cellspacing="1" class="contenttext">						
							<tr>
								<td>
								<c:if test="${studentPlanSubInd ne 'Y' && acctType ne 'current'}">
									<p><a href="#" id="btnCopyFromCredit " class="nextbutton3" onClick="javascript:formSubmit('REFRESH');">Copy the Credit Card information from Upfront Payment</a></p>
								</c:if>
								</td>
							</tr>
						</table>
						</div>
					</td>
				</tr>
				<!-- end Credit card info button -->
				<tr class="payment_detail">
					<td valign="top" >
					<!--  table here  -->
					<!-- credit card table -->
					<div id="commonDiv">	
					<table width="100%" border="0" cellspacing="1" class="contenttext">
				   		<!-- common -->
						<tr>
							<td width="5%">&nbsp;</td>
							<td width="30%">
								<spring:message code="label.thirdPartyInd"/>
								<span class="contenttext_red">*</span>
							</td>
							<td>
								<form:select path="thirdPartyInd" onchange="javascript:refreshParty(this);">								
								<form:option value="N" label="Self" />
								<form:option value="Y" label="Third Party" />
								</form:select>
								<form:errors path="thirdPartyInd" cssClass="error" />
								&nbsp;
								<c:if test="${acctType ne 'current'}">
								<a id="btnCopyFromCustInfo " href="#" class="nextbutton3" onClick="javascript:formSubmit('REFRESH_SELF');">Copy from Cust. Info.</a>
								</c:if></td>
						</tr>
						<!-- common end -->
					</table>
					</div>	
					
					<div id="autoPayDiv">	
					<table width="100%" border="0" cellspacing="1" class="contenttext">	
						<!-- autoPay bank info -->
						
						<tr>
							<td width="5%">&nbsp;</td>
							<td width="30%">
								<spring:message code="label.bankAcctHolderIdType"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:select path="bankAcctHolderIdType">
								<form:option value="HKID" label="HKID" />
								<form:option value="PASS" label="Passport" />
								<!--add by eliot 20110610-->
								<form:option value="BS" label="HKBR" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.bankAcctHolderIdNum"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:input path="bankAcctHolderIdNum" size="30" maxlength="30"/>							
							<form:errors path="bankAcctHolderIdNum" cssClass="error" />
							</td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.bankCode"/>
								<span class="contenttext_red">*</span>
							</td>
							<td>
								<form:select path="bankCode"   onchange="updatemodels(this.selectedIndex, this.value)">
									<form:option value="NONE" label="Select.." />
									<form:options items="${autopayIssueBankList}" itemValue="bankCd" itemLabel="bankName" />
								</form:select>							
							<form:errors path="bankCode" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.branchCode"/>
								<span class="contenttext_red">*</span>
							</td>
							<td>
								<form:select path="branchCode" >
									<form:option value="NONE" label="Select.." />
								</form:select>	
								<form:errors path="branchCode" cssClass="error" />						
							</td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.bankAcctHolderName"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:input path="bankAcctHolderName" size="40" maxlength="40"/>							
							<form:errors path="bankAcctHolderName" cssClass="error" />
							</td>
						</tr>
						
					<%-- 	<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.autopayUpperLimitAmt"/>
							</td>
							<td><form:input path="autopayUpperLimitAmt" size="8" maxlength="8"/>							
							<form:errors path="autopayUpperLimitAmt" cssClass="error" />
							</td>
						</tr> --%>
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.bankAcctNum"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:input path="bankAcctNum" size="40" maxlength="40"/>							
							<form:errors path="bankAcctNum" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.autopayApplDate"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:input path="autopayApplDateStr" id="autopayApplDateStr" size="10" maxlength="10"/>							
							<form:errors path="autopayApplDateStr" cssClass="error" />
							</td>
						</tr>
					</table>
					</div>
					<!-- autoPay bank info END-->
					
					<!-- credit card info -->
					<div id="creditCardDiv">			
					<table width="100%" border="0" cellspacing="1" class="contenttext">	
						<tr>
							<td width="5%">&nbsp;</td>
							<td width="30%">
								<spring:message code="label.creditCardHolderName"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:input path="creditCardHolderName" size="30" maxlength="40"/>								
							<form:errors path="creditCardHolderName" cssClass="error" />
							</td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.creditCardDocType"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:select path="creditCardDocType">
									<form:option value="HKID" label="HKID" />
									<form:option value="PASS" label="Passport" />
									<!--add by eliot 20110610-->
									<form:option value="BS" label="HKBR" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.creditCardDocNum"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:input path="creditCardDocNum" size="20" maxlength="20"/>							
							<form:errors path="creditCardDocNum" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.creditCardType"/>
								<span class="contenttext_red">*</span>
							</td>
							<td>
								<form:select path="creditCardType">
									<form:option value="NONE" label="Select.." />
									<form:options items="${creditCardTypeList}" itemValue="codeId" itemLabel="codeDesc"/>
								</form:select>
								<form:errors path="creditCardType" cssClass="error" />							
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.creditCardNum"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:input path="creditCardNum" size="30" maxlength="16"/>
							
							<input type="hidden" name="token" id="token" />
							
							<input type="hidden" name="ceksSubmit" value="N" /> 
								<c:if test="${acctType ne 'current'}">								
								<a href="#" id="btnInputCreditCard" class="nextbutton3" onClick="javascript:openCEKSWindow();">Input Credit Card No.</a>
								</c:if>							
								<form:errors path="creditCardNum" cssClass="error" />
								</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.creditCard.expiry"/>
								<span class="contenttext_red">*</span>
							</td>
							<td><form:select path="creditExpiryMonth">						
								<form:option value="01" label="01" />
								<form:option value="02" label="02" />
								<form:option value="03" label="03" />
								<form:option value="04" label="04" />
								<form:option value="05" label="05" />
								<form:option value="06" label="06" />
								<form:option value="07" label="07" />
								<form:option value="08" label="08" />
								<form:option value="09" label="09" />
								<form:option value="10" label="10" />
								<form:option value="11" label="11" />
								<form:option value="12" label="12" />																																					
								</form:select>
								<form:select path="creditExpiryYear">
								<%for (int temp=0;temp<15;temp++){ %>
									<form:option value="<%=expiryYear+temp%>" label="<%=Integer.toString(expiryYear+temp)%>" />
								<%} %>	
								</form:select>	
								<form:errors path="creditExpiryYear" cssClass="error" />						
							</td>
						</tr>
					
						<tr>
							<td>&nbsp;</td>
							<td>
								<spring:message code="label.creditCardIssueBankCd"/>
								<span class="contenttext_red">*</span>
							</td>
							<td>
								<form:select path="creditCardIssueBankCd" onchange="javascript:assignBankName(this);">
									<form:option value="NONE" label="Select.." />
									<form:options items="${issueBankList}" itemValue="bankCd" itemLabel="bankName" />
								</form:select>	
								<form:errors path="creditCardIssueBankCd" cssClass="error" />							
							</td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
							<td><spring:message code="label.creditCard.verifyResult"/></td>
							<td>
							<c:choose>
								<c:when test = "${mobccspaymentmonthy.creditCardVerifiedInd == 'Y'}">
									Success
								</c:when>
								<c:when test = "${mobccspaymentmonthy.creditCardVerifiedInd == 'N'}">
									<font color="red"><b>Fail</b></font>
								</c:when>
								<c:otherwise>
									In Process
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
						<!-- end of credit card info -->
					</table>
					</div>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0">
				<tr>

					<td>
					<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3" onClick="javascript:formSubmit('SUBMIT');">continue ></a></div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>	
	<form:hidden path="creditCardVerifiedInd" />
	<form:hidden path="creditCardIssueBank" />
	<form:hidden path="creditCardIssueBankName" /> <%-- add by herbert 20110721 --%>
	<form:hidden path="bankName"/>
	<form:hidden path="branchName"/>
	<form:hidden path="actionType" />
	<form:hidden path="creditCardInd" />
	<input type="hidden" name="currentView" value="mobccspaymentmonthy" />
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>