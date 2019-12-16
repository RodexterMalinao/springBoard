<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobpayment" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>
<%@page import="java.util.Calendar;" %>
<%int expiryYear = Calendar.getInstance().get(Calendar.YEAR);%>
<script type="text/javascript" src="js/jquery.maskedinput.min.js"></script>
<script type="text/javascript">
	function formSubmit() {
		
			var selectVal=$('input[name=payMethodType]:checked').val();
			if (selectVal=="A"){
				
				document.paymentForm.bankName.value = document.paymentForm.bankCode.options[document.paymentForm.bankCode.selectedIndex].text;
				document.paymentForm.branchName.value = document.paymentForm.branchCode.options[document.paymentForm.branchCode.selectedIndex].text;
			}
			
			//add by herbert 20110721 ---start
			if (selectVal=="C"){
				document.paymentForm.creditCardIssueBankName.value = document.paymentForm.creditCardIssueBankCd.options[document.paymentForm.creditCardIssueBankCd.selectedIndex].text;
			}
			//add by herbert 20110721 ---end
			
			//document.paymentForm.submit();
			$("#paymentForm").submit();
	}

	function assignBankName(selBox){
		document.paymentForm.creditCardIssueBank.value = selBox.options[selBox.selectedIndex].text;
	}
	
	function ceksFormSubmit() {
		document.paymentForm.ceksSubmit.value = "Y";
		document.paymentForm.submit();
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
		
		document.paymentForm.ceksSubmit.value = "N";
		
		alert(document.paymentForm.ceksSubmit.value 
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
	    }
		if (which=='C'){
			$("#commonDiv").show();
			$("#autoPayDiv").hide();
			$("#creditCardDiv").show();
		}
		if (which=='M'){
			$("#commonDiv").hide();
			$("#autoPayDiv").hide();
			$("#creditCardDiv").hide();
			
		}
	}

	var model=new Array()
	${bankScriptArrayString}


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
		
		
		var creditCardInd = document.paymentForm.creditCardInd.value;
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
		
		 //value from other jsp, can see controller 
		 var acctType ='${acctType}';
		if (acctType =="current"){
			
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
			$('input[name=creditCardIssueBankCd]').attr('disabled', true);
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
			
			
			
		}
		// set acccount in digit and max length = 9
		$("input[name=bankAcctNum]").mask("?999999999", { placeholder: "" });
	});
</script>

<br> 
<%-- 
<div id=progress bar>
<a class="progresspast" href="customerprofile.html"><span><b>1</b>Customer
Profile</span></a> <a class="progresspast" href="mnp.html"><span><b>2</b>MNP/New Phone#</span></a>
<a class="progresspast" href="serviceselection.html"><span><b>3</b>Offer Detail</span></a> <a
	class="progress"><span><b>4</b>Payment Method</span></a> <a
	class="progressfuture"><span><b>5</b>Staff, SIM & HS Info</span></a> <a
	class="progressfuture"><span><b>6</b>Summary</span></a> <a
	class="progressfuture"><span><b>7</b>Done</span></a> <br>
<br>
<br> 
<br>
</div>--%>

<form:form method="POST" name="paymentForm" id="paymentForm" commandName="payment">
	<form:errors path="payMethodType" cssClass="error" />
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td class="table_title">Payment Information</td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0">
			<tr>
			<td>
			<!-- cash table -->

					<table width="100%" border="0" cellspacing="1" class="contenttext">

						<tr>
							<td>
								<label>
									<form:radiobutton id="payMethodTypeM" path="payMethodType" value="M" onclick="show('M')"/> 
									<B>Cash</B>
								</label>
								<span class="contenttext_red">*</span>
							</td>
							<td>
								<label>
									<form:radiobutton id="payMethodTypeA" path="payMethodType" value="A" onclick="show('A')"/> 
									<B>AutoPay</B>
								</label>
								<span class="contenttext_red">*</span>
							</td>
							<td>
								<label>
									<form:radiobutton id="payMethodTypeC" path="payMethodType" value="C" onclick="show('C')"/> 
									<b>Credit Card / Tap&Go card</b>
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

				<tr>
					<td valign="top" >
					<!--  table here  -->
					<!-- credit card table -->
					<div id="commonDiv">	
					<table width="100%" border="0" cellspacing="1" class="contenttext">

				   		<!-- common -->
				   		
						<tr>
							<td width="5%">&nbsp;</td>
							<td width="30%">Self Third Party<span class="contenttext_red">*</span></td>
							<td>
								<form:select path="thirdPartyInd">								
								<form:option value="N" label="Self" />
								<form:option value="Y" label="Third Party" />
								</form:select>
								<form:errors path="thirdPartyInd" cssClass="error" />
							</td>

						</tr>
						<!-- common end -->
					</table>
					</div>	
					
					<div id="autoPayDiv">	
					<table width="100%" border="0" cellspacing="1" class="contenttext">	
						<!-- autoPay bank info -->
						
						<tr>
							<td width="5%">&nbsp;</td>
							<td width="30%">Bank A/C Owner Doc. Type<span class="contenttext_red">*</span></td>
														
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
							<td>Bank A/C Owner Doc. No.<span class="contenttext_red">*</span></td>
														
							<td><form:input path="bankAcctHolderIdNum" size="30" maxlength="30"/>
							<form:errors path="bankAcctHolderIdNum" cssClass="error" />
							</td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
							<td>Bank Code<span class="contenttext_red">*</span></td>
														
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
							<td>Branch Code<span class="contenttext_red">*</span></td>
														
							<td>
							
							<form:select path="branchCode" >
									<form:option value="NONE" label="Select.." />
								
									
								</form:select>
							<form:errors path="branchCode" cssClass="error" />
							</td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
							<td>Bank Holder Name<span class="contenttext_red">*</span></td>
														
							<td><form:input path="bankAcctHolderName" size="40" maxlength="40"/>
							<form:errors path="bankAcctHolderName" cssClass="error" />
							</td>
						</tr>
						
					<%-- 	<tr>
							<td>&nbsp;</td>
							<td>Autopay Upper Limit</td>
														
							<td><form:input path="autopayUpperLimitAmt" size="8" maxlength="8"/>
							<form:errors path="autopayUpperLimitAmt" cssClass="error" />
							</td>
						</tr> --%>
						<tr>
							<td>&nbsp;</td>
							<td>Bank Account No.<span class="contenttext_red">*</span></td>
														
							<td><form:input path="bankAcctNum" size="40" maxlength="40"/>
							<form:errors path="bankAcctNum" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>Application Date (DD/MM/YYYY)<span class="contenttext_red">*</span></td>
														
							<td><form:input path="autopayApplDateStr" id="autopayApplDateStr" size="10" maxlength="10"/>
							<form:errors path="autopayApplDateStr" cssClass="error" />
							</td>
						</tr>
					</table>
					</div>
						<!-- autoPay bank info -->
					<div id="creditCardDiv">	
					<table width="100%" border="0" cellspacing="1" class="contenttext">	
						<!-- credit card info -->
						<tr>
							<td width="5%">&nbsp;</td>
							<td width="30%">Card Holder Name<span class="contenttext_red">*</span></td>
							<td><form:input path="creditCardHolderName" size="30" maxlength="40"/>
							<form:errors path="creditCardHolderName" cssClass="error" />
							
							</td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
							<td>Card Holder Document Type<span class="contenttext_red">*</span></td>
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
							<td>Card Holder Document No.<span class="contenttext_red">*</span></td>
							<td><form:input path="creditCardDocNum" size="20" maxlength="20"/>
							<form:errors path="creditCardDocNum" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>Credit Card Type<span class="contenttext_red">*</span></td>
							<td>
								<form:select path="creditCardType">
									<form:option value="NONE" label="Select.." />
									<form:option value="01" label="VISA" />
									<form:option value="02" label="MASTER" />
									<form:option value="04" label="AMEX" />
								</form:select>
								<form:errors path="creditCardType" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td >Card Number<span class="contenttext_red">*</span></td>
							<td><form:input path="creditCardNum" size="30" maxlength="16" readonly="true" />
							
							<input type="hidden" name="token" id="token" />
							
							<input type="hidden" name="ceksSubmit" value="N" /> 
								<c:if test="${acctType ne 'current'}">							
								<a href="#" class="nextbutton3" onClick="javascript:ceksFormSubmit();" name="creditCardNumbtn">Input Credit Card No.</a>
								</c:if>								
								<form:errors path="creditCardNum" cssClass="error" />
								</td>

						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>Expire Date<span class="contenttext_red">*</span></td>
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
							<td>Issue Bank Name<span class="contenttext_red">*</span></td>
							<td>
								<form:select path="creditCardIssueBankCd" onchange="javascript:assignBankName(this);">
									<form:option value="NONE" label="Select.." />
									<form:options items="${issueBankList}" itemValue="bankCd" itemLabel="bankName" />
								</form:select>
								<form:errors path="creditCardIssueBankCd" cssClass="error" />
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
					<a href="#" class="nextbutton3" onClick="javascript:formSubmit();">continue ></a></div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>	
	<form:hidden path="creditCardIssueBank" />
	<form:hidden path="creditCardIssueBankName" /> <%-- add by herbert 20110721 --%>
		<form:hidden path="bankName"/>
	<form:hidden path="branchName"/>
	<form:hidden path="creditCardInd" />
	<input type="hidden" name="currentView" value="payment" />
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>