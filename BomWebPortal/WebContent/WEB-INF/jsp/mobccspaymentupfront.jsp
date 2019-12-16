<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<%@page import="java.util.Calendar;" %>
<%int expiryYear = Calendar.getInstance().get(Calendar.YEAR);%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<style type="text/css">
.payment_mask { position: absolute; background-color: #f2f2f2; font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; z-index: 9999; opacity: 0.4; filter: alpha(opacity=40); }
.payment_mask_note { padding-left: 1em; font-size: small; color: red; font-weight: bold;  font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; z-index: 10000 }
.paymentMethod_sel label { font-weight: bold }
</style>
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript">
function resizePaymentMask() {
	var $paymentSel = $(".payment_sel");
	$(".payment_mask").css({ width: $paymentSel.width(), height: $paymentSel.height() });
}
	function formSubmit(actType) {
		
			var selectVal=$('input[name=payMethodType]:checked').val();
			/*if (selectVal=="A"){
				
				document.mobCcsPaymentUpFrontForm.bankName.value = document.mobCcsPaymentUpFrontForm.bankCode.options[document.mobCcsPaymentUpFrontForm.bankCode.selectedIndex].text;
				document.mobCcsPaymentUpFrontForm.branchName.value = document.mobCcsPaymentUpFrontForm.branchCode.options[document.mobCcsPaymentUpFrontForm.branchCode.selectedIndex].text;
			}*/
			
			//add by herbert 20110721 ---start
			if (selectVal=="C"){
				document.mobCcsPaymentUpFrontForm.creditCardIssueBankName.value = document.mobCcsPaymentUpFrontForm.creditCardIssueBankCd.options[document.mobCcsPaymentUpFrontForm.creditCardIssueBankCd.selectedIndex].text;
			}
			//add by herbert 20110721 ---end
			if (selectVal=="I"){
				document.mobCcsPaymentUpFrontForm.creditCardIssueBankName.value = document.mobCcsPaymentUpFrontForm.creditCardIssueBankCd.options[document.mobCcsPaymentUpFrontForm.creditCardIssueBankCd.selectedIndex].text;
			}
			
			document.mobCcsPaymentUpFrontForm.actionType.value = actType;
			//document.mobCcsPaymentUpFrontForm.submit();//edit by wilson
			$("#mobCcsPaymentUpFrontForm").submit();
	}

	function assignBankName(selBox){
		document.mobCcsPaymentUpFrontForm.creditCardIssueBank.value = selBox.options[selBox.selectedIndex].text;
		var selectVal=$('input[name=payMethodType]:checked').val();
		if  (selectVal=="I"){
			var bankCd=document.mobCcsPaymentUpFrontForm.creditCardIssueBankCd.value;
			var upfrontAmt=document.mobCcsPaymentUpFrontForm.upfrontAmt.value;
			//alert(bankCd +":" +upfrontAmt);
			loadCcInstList(bankCd,upfrontAmt);
		}
	}
	
	function loadCcInstList(bankCd,upfrontAmt){
		//alert("A:"+bankCd);
		$.ajax({				
			url : 'mobccspaymentupfrontajax.html',
			type : 'POST',
			cache : false,
			data : {
				type : 'CcInstList',
				bankCd : bankCd,
				upfrontAmt : upfrontAmt
			},
			dataType : 'JSON',
			timeout : 5000,
			error : function() {
				alert('Error loading Credit Card Installment Schedule!');
			},
			success : function(msg) {
				if($('#orderStatus').val() == 'recall'){
					$('#ccInstSchedule').empty();
					$("<option value='NONE'>Select...</option>").appendTo($("#ccInstSchedule"));
					$.each(eval(msg), function(i, item) {
						if (item.instSchedule == "${mobccspaymentupfront.ccInstSchedule}") {
							$("<option value='" + item.instSchedule + "' selected='selected'>"
											+ item.instSchedule
											+ "</option>").appendTo(
											$('#ccInstSchedule'));
						} else {
							$("<option value='" + item.instSchedule + "'>"
											+ item.instSchedule
											+ "</option>").appendTo(
											$('#ccInstSchedule'));
						}
					});
				}else{
					$('#ccInstSchedule').empty();
						$("<option value='NONE'>Select...</option>").appendTo($("#ccInstSchedule"));
					$.each(eval(msg), function(i, item) {
						if (item.instSchedule == "${mobccspaymentupfront.ccInstSchedule}") {
							$("<option value='" + item.instSchedule + "' selected='selected'>"
											+ item.instSchedule
											+ "</option>").appendTo(
											$('#ccInstSchedule'));
						} else {
							$("<option value='" + item.instSchedule + "'>"
											+ item.instSchedule
											+ "</option>").appendTo(
											$('#ccInstSchedule'));
						}
					});
				}
			}
		});
	}
	
	function loadCcInstBankList(payMtdType,upfrontAmt){
		//alert("B:"+payMtdType);
		$.ajax({				
			url : 'mobccspaymentupfrontajax.html',
			type : 'POST',
			cache : false,
			data : {
				type : 'CcInstBankList',
				payMtdType : payMtdType,
				upfrontAmt : upfrontAmt
			},
			dataType : 'JSON',
			timeout : 5000,
			error : function() {
				alert('Error loading Credit Card Bank List!');
			},
			success : function(msg) {
				if($('#orderStatus').val() == 'recall'){
					$('#creditCardIssueBankCd').empty();
					$("<option value='NONE'>Select...</option>").appendTo($("#creditCardIssueBankCd"));
					$.each(eval(msg), function(i, item) {
						if (item.bankCd == "${mobccspaymentupfront.creditCardIssueBankCd}") {
							$("<option value='" + item.bankCd + "' selected='selected'>"
											+ item.bankName
											+ "</option>").appendTo(
											$('#creditCardIssueBankCd'));
						} else {
							$("<option value='" + item.bankCd + "'>"
											+ item.bankName
											+ "</option>").appendTo(
											$('#creditCardIssueBankCd'));
						}
					});
				}else{
					$('#creditCardIssueBankCd').empty();
						$("<option value='NONE'>Select...</option>").appendTo($("#creditCardIssueBankCd"));
					$.each(eval(msg), function(i, item) {
						if (item.bankCd == "${mobccspaymentupfront.creditCardIssueBankCd}") {
							$("<option value='" + item.bankCd + "' selected='selected'>"
											+ item.bankName
											+ "</option>").appendTo(
											$('#creditCardIssueBankCd'));
						} else {
							$("<option value='" + item.bankCd + "'>"
											+ item.bankName
											+ "</option>").appendTo(
											$('#creditCardIssueBankCd'));
						}
					});
				}
			}
		});
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
		
		document.mobCcsPaymentUpFrontForm.ceksSubmit.value = "N";
		
		alert(document.mobCcsPaymentUpFrontForm.ceksSubmit.value 
				+ " token " + v5
				+ " mask_token " + maskedToken);
		
	}
	

	function show( which )
	{
		//M - Cash, C - Credit Card, I Credit Card Installement
		if (which=='M'){
			$("#creditCardCommonDiv").hide();
			$("#creditCardDiv").hide();
			$("#creditCardInstallmentDiv").hide();
			$("#creditCardCommonDiv2").hide();
	    }
		if (which=='C'){
			var upfrontAmt = document.mobCcsPaymentUpFrontForm.upfrontAmt.value;
			$("#creditCardCommonDiv").show();
			$("#creditCardDiv").show();
			$("#creditCardInstallmentDiv").hide();
			$("#creditCardCommonDiv2").show();
			loadCcInstBankList(which,upfrontAmt);
		}
		if (which=='I'){
			var upfrontAmt = document.mobCcsPaymentUpFrontForm.upfrontAmt.value;
			//alert ("ABJ:" + document.mobCcsPaymentUpFrontForm.creditCardIssueBankCd.value);
			$("#creditCardCommonDiv").show();
			$("#creditCardDiv").hide();
			$("#creditCardInstallmentDiv").show();
			$("#creditCardCommonDiv2").show();
			loadCcInstBankList(which,upfrontAmt);
		}
	}

	var model=new Array();
	${bankScriptArrayString};

	function refreshParty(){
		var party = document.mobCcsPaymentUpFrontForm.thirdPartyInd.value;
		if (party == 'Y') {
			$("input[name=creditCardHolderName]").val("");
			$("#btnCopyFromCustInfo").hide();
		} else {
			$("#btnCopyFromCustInfo").show();
		}
	}
	
	function paymentTrxn() {
		var sURL = "mobccspaymenttranshistory.html?orderId="+"${mobccspaymentupfront.orderId}";;
		var vArguments = new Object();
		var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
		window.showModalDialog(sURL, vArguments, sFeatures);
	}
	
	$(document).ready(function() {//add 20110603
		//M - Cash, C - Credit Card, I Credit Card Installement
		var selectVal=$('input[name=payMethodType]:checked').val();
		show( selectVal );
		
		if (selectVal=='I') {
			var upfrontAmt = document.mobCcsPaymentUpFrontForm.upfrontAmt.value;
			var creditCardIssueBankCd = document.mobCcsPaymentUpFrontForm.creditCardIssueBankCd.value;
			//alert("B:"+ creditCardIssueBankCd + ":" +upfrontAmt);
			//loadCcInstList(creditCardIssueBankCd,upfrontAmt);
		}
		//move up, by wilson 2012/04/18
	/* 	var creditCardInd = document.mobCcsPaymentUpFrontForm.creditCardInd.value;
		//alert("creditCardInd:" + creditCardInd);
		if ( creditCardInd == null || creditCardInd == ''){
			$('#payMethodTypeM').attr('disabled', false);
		} else {
			$('#payMethodTypeM').attr('disabled', true);
		} */
		
		var upfrontCCInd = document.mobCcsPaymentUpFrontForm.upfrontCCInd.value;
		if ( upfrontCCInd == 'Y'){
			/* if ($("input[name=payMethodType]:checked").val() == 'M'){
				$('input[name="payMethodType"][value=C]').prop('checked', true);
			} */
			$('#payMethodTypeM').attr('disabled', true);
		} else {
			$('#payMethodTypeM').attr('disabled', false);
		} 
		
		var ccLockFlag = document.mobCcsPaymentUpFrontForm.ccLockFlag.value;
		//alert("ccLockFlag" + ccLockFlag);
		if (ccLockFlag == 'Y'){
			$('#payMethodTypeM').attr('disabled', true);
			$('#payMethodTypeC').attr('disabled', true);
			$('#payMethodTypeI').attr('disabled', true);
			$('#thirdPartyInd').attr('disabled', true);
			$('#creditCardType').attr('disabled', true);
			$('#creditExpiryMonth').attr('disabled', true);
			$('#creditExpiryYear').attr('disabled', true);
			$('#creditCardHolderName').attr('disabled', true);
			$('#creditCardIssueBankCd').attr('disabled', true);
			$('#paymentCombination').attr('disabled', true);
			$('#ccInstSchedule').attr('disabled', false);
			
// 			var recallCCInstWarnInd = document.mobCcsPaymentUpFrontForm.recallCCInstWarnInd.value;
// 			alert ("recallCCInstWarnInd:" + recallCCInstWarnInd);
// 			if (recallCCInstWarnInd =='Y'){
// 				$('#ccInstSchedule').attr('disabled', false);
// 			} else {
// 				$('#ccInstSchedule').attr('disabled', false);
// 			}
		}
		
		var creditCardVerifiedInd = document.mobCcsPaymentUpFrontForm.creditCardVerifiedInd.value;
		if ( creditCardVerifiedInd == 'N'){
			alert("Credit Card Verification Fail!");
		}
		
		//var defaultValue ='${creditCardIssueBankCd}';
		//$("select#creditCardIssueBankCd option[value="+defaultValue+"]").prop("selected", true); //update creditCardIssueBankCd
		//alert("creditCardIssueBankCd" + document.mobCcsPaymentUpFrontForm.creditCardIssueBankCd.value);
		
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
	});
</script>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobccspayment" />
</jsp:include>

<form:form method="POST" name="mobCcsPaymentUpFrontForm" id="mobCcsPaymentUpFrontForm"
	commandName="mobccspaymentupfront">
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
						<td class="table_title"><spring:message code="label.mobccspayment.header.upfront"/></td>
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
											<form:radiobutton id="payMethodTypeM" path="payMethodType" value="M" onclick="show('M')"/>
											<spring:message code="label.payMethodType.M"/>
										</label>
										<span class="contenttext_red">*</span>
									</td>
									<td>
										<label>
										<c:choose>
										<c:when test='${mobccspaymentupfront.ccFlag == "Y"}'>
											<form:radiobutton id="payMethodTypeC" path="payMethodType" value="C" onclick="show('C')"/>
										</c:when>
										<c:otherwise>
											<form:radiobutton id="payMethodTypeC3" path="payMethodType" value="C" onclick="show('C')"  disabled="true"/>
										</c:otherwise>
										</c:choose>
											<spring:message code="label.payMethodType.C"/>
										</label>
										<span class="contenttext_red">*</span>
									</td>
									<td>
										<label>
											<c:choose>
											<c:when test='${mobccspaymentupfront.ccInstScheduleFlag == "Y"}'>
												<form:radiobutton id="payMethodTypeI" path="payMethodType" value="I" onclick="show('I')"/>
											</c:when>
											<c:otherwise>
												<form:radiobutton id="payMethodType3" path="payMethodType" value="I" onclick="show('I')" disabled="true"/>
											</c:otherwise>
											</c:choose>
											<spring:message code="label.payMethodType.I"/>
										</label>
										<span class="contenttext_red">*</span>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table> <!-- end cash table -->
						<form:errors path="recallCCInstWarnInd" cssClass="error" />
						<form:errors path="payMethodType" cssClass="error" />
						</td>
					</tr>

					<tr class="payment_detail">
						<td valign="top">
							<!--  table here  --> <!-- credit card table -->
							<div id="creditCardCommonDiv">
								<table width="100%" border="0" cellspacing="1"
									class="contenttext">
									<tr>
										<td width="5%">&nbsp;</td>
										<td width="30%">
											<spring:message code="label.thirdPartyInd"/>
											<span class="contenttext_red">*</span>
										</td>
										<td>
											<form:select id="thirdPartyInd" path="thirdPartyInd" onchange="javascript:refreshParty(this);">
												<form:option value="N" label="Self" />
												<form:option value="Y" label="Third Party" />
											</form:select>
											&nbsp;
											
											<a id="btnCopyFromCustInfo" href="#" class="nextbutton3" onClick="javascript:formSubmit('REFRESH_SELF');">Copy from Cust. Info.</a>
											
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>
											<spring:message code="label.creditCardType"/>
											<span class="contenttext_red">*</span>
										</td>
										<td>
											<form:select id="creditCardType" path="creditCardType">
												<form:option value="NONE" label="Select.." />
												<form:options items="${creditCardTypeList}" itemValue="codeId" itemLabel="codeDesc"/>
												<form:errors path="creditCardType" cssClass="error" />
											</form:select>				
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>
											<spring:message code="label.creditCardNum"/>
											<span class="contenttext_red">*</span>
										</td>
										<td>
											<form:input path="creditCardNum" size="30" maxlength="16" readonly="true" /> 
											<input type="hidden" name="token" id="token" /> 
											<input type="hidden" name="ceksSubmit" value="N" /> 
											<c:choose>
												<c:when test="${mobccspaymentupfront.ccLockFlag == 'N'}">
													<a href="#" class="nextbutton3" onClick="javascript:openCEKSWindow();">Input
														Credit Card No.</a>
												</c:when>
											</c:choose>
											<form:errors path="creditCardNum" cssClass="error" />
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>
											<spring:message code="label.creditCard.expiry"/>
											<span class="contenttext_red">*</span>
										</td>
										<td>
											<form:select id="creditExpiryMonth" path="creditExpiryMonth">
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
											<form:select id="creditExpiryYear" path="creditExpiryYear">
											<%for (int temp=0;temp<15;temp++){ %>
												<form:option value="<%=expiryYear+temp%>" label="<%=Integer.toString(expiryYear+temp)%>" />
											<%} %>	
											</form:select><form:errors path="creditExpiryYear" cssClass="error" />
										</td>
									</tr>
									<tr>
										<td width="5%">&nbsp;</td>
										<td width="30%">
											<spring:message code="label.creditCardHolderName"/>
											<span class="contenttext_red">*</span></td>
										<td>
											<form:input id="creditCardHolderName" path="creditCardHolderName" size="30" maxlength="40" />
											<form:errors path="creditCardHolderName" cssClass="error" />
										</td>
									</tr>
									 
									<tr>
										<td width="5%">&nbsp;</td>
										<td width="30%">
											<spring:message code="label.creditCardIssueBankCd"/>
											<span class="contenttext_red">*</span>
										</td>
										<td>
											<form:select id="creditCardIssueBankCd" path="creditCardIssueBankCd" onchange="javascript:assignBankName(this);">
												<form:option value="NONE" label="Select..." />
												<!-- <form:options items="${issueBankList}" itemValue="bankCd" itemLabel="bankName" /> -->
											</form:select><form:errors path="creditCardIssueBankCd" cssClass="error" />
										</td>
									</tr>
									<!-- common end -->
								</table>
							</div>

							<div id="creditCardDiv">
								<table width="100%" border="0" cellspacing="1"
									class="contenttext">
									<tr>
										<td width="5%">&nbsp;</td>
										<td width="30%">
											<spring:message code="label.paymentCombination"/>
											<span class="contenttext_red">*</span>
										</td>
										<td>
											<form:select id="paymentCombination" path="paymentCombination">
												<form:options items="${paymentCombinationList}" itemValue="codeId" itemLabel="codeDesc"/>
											</form:select><form:errors path="paymentCombination" cssClass="error" />
										</td>
									</tr>
									<%--<tr>
										<td>&nbsp;</td>
										<td>In Advance Amount</td>
										<td><form:input path="inAdvanceAmount" size="30" maxlength="60" />
										</td>
									</tr>--%>
								</table>
							</div>

							<div id="creditCardInstallmentDiv">
								<table width="100%" border="0" cellspacing="1" class="contenttext">
								 <tr>
									<td width="5%">&nbsp;</td>
									<td width="30%">
										<spring:message code="label.ccInstSchedule"/>
										<span class="contenttext_red">*</span>
									</td>
									<td>
										<form:select id="ccInstSchedule" path="ccInstSchedule">
											<form:option value="NONE" label="Select..." />
											<form:options items="${creditCardInstList}" itemValue="instSchedule" itemLabel="instSchedule"/>
										</form:select>months for HKD$ ${mobccspaymentupfront.upfrontAmt}
										<form:errors path="ccInstSchedule" cssClass="error" /> 										
									</td>
								</tr>					
								</table>
							</div>
							<div id="creditCardCommonDiv2">
							<table width="100%" border="0" cellspacing="1" class="contenttext">
								
								<!-- <c:choose>
									<c:when test='${not empty mobccspaymentupfront.orderId}'>
										<tr>
											<td width="5%">&nbsp;</td>
											<td width="30%">Payment History</td>
											<td><br/><a href="#" onClick="paymentTrxn();" class="nextbutton3" >Payment Transaction</a></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td width="5%">&nbsp;</td>
											<td width="30%">Payment History</td>
											<td>No Payment Transaction History</td>
										</tr>
									</c:otherwise>
								</c:choose> -->
									<tr>
										<td width="5%">&nbsp;</td>
										<td width="30%"><spring:message code="label.creditCard.verifyResult"/></td>
										<td>
										<c:choose>
											<c:when test = "${mobccspaymentupfront.creditCardVerifiedInd == 'Y'}">
												Success
											</c:when>
											<c:when test = "${mobccspaymentupfront.creditCardVerifiedInd == 'N'}">
												<font color="red"><b>Fail</b></font>
											</c:when>
											<c:otherwise>
												In Process
											</c:otherwise>
										</c:choose>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<div style="padding: 10px 0 10px 5%">
					<c:choose>
					<c:when test="${not empty mobccspaymentupfront.orderId}">
						<a href="#" onClick="paymentTrxn();" class="nextbutton3" >Payment Transaction History</a>
					</c:when>
					<c:otherwise>
						<span style="color:blue">No Payment Transaction History</span>
					</c:otherwise>
					</c:choose>
				</div>
				<table width="100%" border="0" cellspacing="0">
					<tr>
						<td>
							<div class="buttonmenubox_R" id="buttonArea">
								<a href="#" class="nextbutton3"
									onClick="javascript:formSubmit('SUBMIT');">continue &gt;</a>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<form:hidden path="creditCardVerifiedInd" />
	<form:hidden path="creditCardIssueBank" />
	<form:hidden path="creditCardIssueBankName" />
	<%-- add by herbert 20110721 --%>
	<form:hidden path="bankName" />
	<form:hidden path="orderId" />
	<form:hidden path="branchName" />
	<form:hidden path="upfrontAmt" />
	<form:hidden path="ccLockFlag" />
	<%-- <form:hidden path="creditCardInd" /> --%>
	<form:hidden path="upfrontCCInd" />
	<form:hidden path="workStatus" />
	<form:hidden path="recallCCInstWarnInd" />
	<form:hidden path="actionType" />
	<input type="hidden" name="ccInstScheduleSelect" id="ccInstScheduleSelect" value="${mobccspaymentupfront.ccInstSchedule}"/>
	<input type="hidden" name="orderStatus" id="orderStatus" value="${workStatus}" />
	<input type="hidden" name="currentView" value="mobccspaymentupfront" />
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>