<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-migrate-1.2.1.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/web/lts/ltspayment.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script src="js/jquery.lts.autocomplete.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.css" rel="stylesheet" />

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 7 : 8}" />
</jsp:include>

<div id="s_line_text"><spring:message code="lts.payment.payment" text="Payment"/></div>
<div class="cosContent">
<form:form method="POST" action="#" id="ltspaymentform" commandName="ltspayment" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="requireRedemPremium"/>
<form:hidden path="allowModifyInd"/>
<input type="hidden" id="paperBillCharge_bom" value="${ltspayment.paperBillCharge}"/>
<input type="hidden" id="paperWaiveReason_bom" value="${ltspayment.paperWaiveReason}"/>
<input type="hidden" id="isRecontractCase" value="${ltspayment.recontractCase}"/>
<input type="hidden" id="isDocTypeBR" value="${ltspayment.docTypeBR}"/>


	<table width="98%" class="paper_w2 round_10" align="center">
		<tr>
			<td>
			<br/>
			<c:if test="${(not empty sessionScope.sessionLtsOrderCapture.ltsRecontractForm)}">
				<div id=recontractDiv>
					<table width="98%" min-height="100px" border="0" align="center">
						<tr> 
			               	<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.recontract" text="Recontract Info"/></td>
			        	</tr>
			        	<tr>
			   				<td>
			          		<br/>
			   				<table width="100%" border="0" cellspacing="1" class="contenttext">	
								<tr>
								<td width="10%">
									&nbsp;
								</td>
			           			<td width="90%" align="left">
									<span><spring:message code="lts.payment.transfereeAccNum" text="Transferee Account Number"/> </span><span class="contenttext_red">*</span> :
									<form:input path="recontractAccountNo" id="recontractAccountNo"/>
									<span id="recontractAccountNoError" class="error"></span>
								</td>
			          			</tr>
			          		</table>
			          		<br/>
			          		</td>
			          	</tr>
					</table>
				</div>
			</c:if>
			<table width="98%" border="0" align="center">
				<tr> 
	               	<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.payMethod" text="Payment Method"/></td>
	        	</tr>
				<tr>
				<td>
					<br>
				<!-- cash table -->
					<table width="98%" border="0" cellspacing="1" class="contenttext" align="center">
						<tr>
							<c:if test='${showKeepExistBtn}'>
								<td>
									<form:radiobutton path="selectButton" id="payMethodTypeE" value="" />
									<b><spring:message code="lts.payment.keepExistPayMtdMsg" text="Keep Existing PCCW Telephone Payment Method"/></b><span class="contenttext_red">*</span>
								</td>
							</c:if>
							<c:if test='${showABtn}'>
								<td>
									<form:radiobutton path="selectButton" id="payMethodTypeA" value="A" />
									<b><c:if test="${!isNewCust}"><spring:message code="lts.payment.chgTo1" text="Change to"/></c:if> <spring:message code="lts.payment.chgToAutopay2" text="Bank Auto-Pay"/></b><span class="contenttext_red">*</span>
								</td>
							</c:if>
							<c:if test='${showCBtn}'>
								<td>
									<form:radiobutton path="selectButton" id="payMethodTypeC" value="C" />
									<b><c:if test="${!isNewCust}"><spring:message code="lts.payment.chgTo1" text="Change to"/></c:if> <spring:message code="lts.payment.chgToCc2" text="Credit Card"/></b><span class="contenttext_red">*</span>
								</td>
							</c:if>	
						</tr>
						<tr>
						<form:errors path="selectButton" cssClass="error" />
						<td>&nbsp; </td>
						</tr>
					</table>
				<!-- end cash table -->
				</td>
			</tr>
			<c:if test="${!isNewCust}">
			<tr>
			<td valign="top" >
			<br>
			<div id="existingPaymentMethod">
				<table width="100%" border="0" class="contenttext" cellspacing="3">	
					<tr>
						<td width="10%">&nbsp;</td>
						<td width="20%">
							<spring:message code="lts.payment.existPayMtd" text="Existing Payment Method"/> :
						</td>
						<td>
							<form:input path="existingPayment" id="exPaymentMethod" readonly="true" cssClass="bold"/>
							<form:errors path="existingPayment" cssClass="error" />
							<%-- <c:if test="${showABtn || showCBtn}">
								<form:select path="changeReason" id="changeReason">
									<form:option value="">-- Change Reason --</form:option>
									<form:option value="1">REASON1</form:option>
									<form:option value="2">REASON2</form:option>
									<form:option value="3">REASON3</form:option>
									<form:option value="4">REASON4</form:option>
									<form:option value="5">REASON5</form:option>
								</form:select>
								<form:errors path="changeReason" cssClass="error" />
							</c:if> --%>
						</td>
					</tr>
					<tr>
						<td width="5%">&nbsp;</td>
						<td colspan="3">
						<c:if test='${!showKeepExistBtn}'>
							<span class="contenttext_red"><spring:message code="lts.payment.cantKeepExistMsg" text="Notice: Cannot keep the existing Payment Method."/></span>
						</c:if>
						</td>
					</tr>
				</table>
			</div>
			</td>
			</tr>
			</c:if>
			
			<tr>
				<td valign="top" style="padding-left:30px">
					<div id="creditCardDiv">
					<hr width="90%"/>
					<br/>
					<table width="100%" border="0" class="contenttext" cellspacing="3" cellpadding="4">	
						<!-- credit card info -->
						<tr>
							<td width="10%">&nbsp;</td>
							<td width="20%"><spring:message code="lts.payment.3rdCc" text="Third Party Credit Card"/><span class="contenttext_red">*</span> :</td>
							<td>
								<form:radiobutton path="thirdPartyCard" id="thirdPartyCardTrue" value="true" /> <b><spring:message code="lts.payment.yes" text="Yes"/></b>
								&nbsp;
								<form:radiobutton path="thirdPartyCard" id="thirdPartyCardFalse" value="false" /> <b><spring:message code="lts.payment.no" text="No"/></b>
								<form:errors path="thirdPartyCard" cssClass="error" />
							</td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
							<td><spring:message code="lts.payment.cardHolderName" text="Card Holder Name"/><span class="contenttext_red">*</span> :</td>
							<td>
								<form:input path="cardHolderName" id="cardHolderName" size="30" maxlength="60" />
								<form:errors path="cardHolderName" cssClass="error" />
							</td>
						</tr>
						<tr class="not3rdpartycc">
							<td>&nbsp;</td>
							<td><spring:message code="lts.payment.cardHolderDocType" text="Card Holder Document Type"/><span class="contenttext_red">*</span> :</td>
							<td>
								<form:select path="cardHolderDocType" id="cardHolderDocType" >
									<form:option value=""><spring:message code="lts.custIdent.type" text="-- TYPE --"/></form:option>
									<form:option value="HKID"><spring:message code="lts.custIdent.hkid" text="HKID"/></form:option>
									<form:option value="PASS"><spring:message code="lts.custIdent.passport" text="Passport"/></form:option>
									<form:option value="BS"><spring:message code="lts.custIdent.hkbr" text="HKBR"/></form:option>
								</form:select>
								<form:errors path="cardHolderDocType" cssClass="error" />
							</td>
						</tr>
						<tr class="not3rdpartycc">
							<td>&nbsp;</td>
							<td><spring:message code="lts.payment.cardHolderDocNo" text="Card Holder Document No.(Verified)"/><span class="contenttext_red">*</span> :</td>
							<td>
								<form:input path="cardHolderDocNum" id="cardHolderDocNum" size="20" maxlength="20" />
								<form:errors path="cardHolderDocNum" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><spring:message code="lts.payment.cardNo" text="Card Number"/><span class="contenttext_red">*</span> :</td>
							<td>
								<form:input path="cardNum" id="cardnum" size="30" maxlength="16" readonly="true" />							
        						<input type="hidden" name="token" id="token" />
								<input type="hidden" name="ceksSubmit" value="N" />
								<a href="#" onclick="javascript:openCEKSWindow()"><div class="func_button"><spring:message code="lts.payment.inputCcNoMsg" text="Input Credit Card No."/></div></a>
								<form:errors path="cardNum" cssClass="error" />								
							</td>
						</tr>						
						<tr>
							<td>&nbsp;</td>
							<td><spring:message code="lts.payment.ccType" text="Credit Card Type"/><span class="contenttext_red">*</span> :</td>
							<td>
								<form:select path="cardType" id="cardtype">
									<form:option value=""><spring:message code="lts.payment.select" text="--- Select ---"/></form:option>
									<form:options items="${creditCardTypeList}" itemValue="itemKey" itemLabel="itemValue" />
								</form:select>
								<form:errors path="cardType" cssClass="error" />
							</td>
						</tr>						
						<tr>
							<td>&nbsp;</td>
							<td><spring:message code="lts.payment.expDate" text="Expire Date"/><span class="contenttext_red">*</span> :</td>
							<td><form:select path="expireMonth">						
									<form:option value="1">01</form:option>
									<form:option value="2">02</form:option>
									<form:option value="3">03</form:option>
									<form:option value="4">04</form:option>
									<form:option value="5">05</form:option>
									<form:option value="6">06</form:option>
									<form:option value="7">07</form:option>
									<form:option value="8">08</form:option>
									<form:option value="9">09</form:option>
									<form:option value="10">10</form:option>
									<form:option value="11">11</form:option>
									<form:option value="12">12</form:option>																																	
								</form:select>
								<form:select path="expireYear">
									<form:options items="${yearList}"/>
								</form:select>
								<form:errors path="expireYear" cssClass="error" />
							</td>
						</tr>
						<c:if test="${!sessionScope.sessionLtsOrderCapture.channelCs}">
						<tr>
							<td>&nbsp;</td>
							<td valign="top"><spring:message code="lts.payment.ccVerify" text="Credit Card Verified"/><span class="contenttext_red">*</span> :</td>
							<td>
								<form:checkbox path="cardVerified"/>
								<form:errors path="cardVerified" cssClass="error" />
							</td>
						</tr>
						</c:if>
						<!-- end of credit card info -->
						
					</table>
					<c:if test="${not empty ltspayment.prePayItemC}">
						<div id="creditCardDivPrePayC">
						<br>
						<table width="100%" cellspacing="0" border="0">
							<tr> 
	                   			<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.prepayInfo" text="Prepayment Information"/></td>
	            			</tr>
	            			<tr>
	            			<td>
	            			<table width="100%" border="0" cellspacing="1" class="table_style_sb">	
								<tr>
									<th width="80%" colspan="2">&nbsp;</th>
									<th width="20%"><spring:message code="lts.payment.amt" text="Amount"/></th>
								</tr>
								<tr>
									<td valign="top"  width="2%">
										<form:checkbox path="prePayItemC.selected" id="prepayItemSelectedC" disabled="${ltspayment.prePayItemC.mdoInd == 'M'}"/>
									</td>
									<td valign="top">
										${ltspayment.prePayItemC.itemDisplayHtml}
									</td>
									<td valign="top" class="itemPrice">
										${ltspayment.prePayItemC.oneTimeAmtTxt}
									</td>
								</tr>
								<tr>
								<td>
									&nbsp;
								</td>
								<td width="90%" align="left" colspan="2">
								<span><spring:message code="lts.payment.custExistAcc" text="Customer’s existing Account No. of PCCW Bill"/> :</span>
								<input type="text" id="existBillAccNumC_C" value="${ltspayment.existBillAccNum}" readonly="readonly" />
 								</td>
								</tr>
							</table>
	            			</td>
	            			</tr>
	            		</table>
	            		</div>
					</c:if>
					<c:if test="${not empty ltspayment.prePayItemM}">
						<div id="creditCardDivPrePayM" style="display:none">
						<br>
						<table width="100%" cellspacing="0" border="0">
							<tr> 
	                   			<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.prepayInfo" text="Prepayment Information"/></td>
	            			</tr>
	            			<tr>
	            			<td>
	            			<table width="100%" border="0" cellspacing="1" class="table_style_sb">	
								<tr>
									<th width="80%" colspan="2">&nbsp;</th>
									<th width="20%"><spring:message code="lts.payment.amt" text="Amount"/></th>
								</tr>
								<tr>
									<td valign="top"  width="2%">
										<form:checkbox path="prePayItemM.selected" id="prepayItemSelectedM" disabled="${ltspayment.prePayItemM.mdoInd == 'M'}"/>
									</td>
									<td valign="top">
										${ltspayment.prePayItemM.itemDisplayHtml}
									</td>
									<td valign="top" class="itemPrice">
										${ltspayment.prePayItemM.oneTimeAmtTxt}
									</td>
								</tr>
								<tr>
								<td>
									&nbsp;
								</td>
								<td width="90%" align="left" colspan="2">
								<span><spring:message code="lts.payment.custExistAcc" text="Customer’s existing Account No. of PCCW Bill"/> :</span>
								<input type="text" id="existBillAccNumC_M" value="${ltspayment.existBillAccNum}" readonly="readonly" />
 								</td>
								</tr>
							</table>
	            			</td>
	            			</tr>
	            		</table>
	            		</div>
					</c:if>
					</div>

					<div id="autoPayDiv">
					<hr width="90%"/>
						<br>
						<table width="100%" border="0" class="contenttext" cellspacing="3" cellpadding="4"> 
							<%-- <tr>
								<td width="10%">&nbsp;</td>
								<td width="20%">Third Party Bank Account<span class="contenttext_red">*</span> :</td>
								<td>
									<form:radiobutton path="thirdPartyBankAccount" id="thirdPartyBankAccountTrue" value="true" /> <b>Yes</b>
									&nbsp;
									<form:radiobutton path="thirdPartyBankAccount" id="thirdPartyBankAccountFalse" value="false" /> <b>No</b>
									<form:errors path="thirdPartyBankAccount" cssClass="error" />
								</td>
							</tr> --%>
							<tr>
								<td>&nbsp;</td>
								<td><spring:message code="lts.payment.bankAccHolderName" text="Bank Account Holder Name"/><span class="contenttext_red">*</span> :</td>
								<td>
									<form:input path="bankAccHolderName" id="bankAccHolderName" size="30" maxlength="60" />
									<form:errors path="bankAccHolderName" cssClass="error" />
								</td>
							</tr>
											
							<tr>
								<td>&nbsp;</td>
								<td><spring:message code="lts.payment.bankAccHolderDocType" text="Bank Account Holder Doc. Type"/><span class="contenttext_red">*</span> :</td>
								<td>
									<form:select path="bankAccHolderDocType" id="bankAccHolderDocType">
										<form:option value=""><spring:message code="lts.payment.select" text="--- Select ---"/></form:option>
										<form:option value="HKID"><spring:message code="lts.custIdent.hkid" text="HKID"/></form:option>
										<form:option value="PASS"><spring:message code="lts.custIdent.passport" text="Passport"/></form:option>
										<form:option value="BS"><spring:message code="lts.custIdent.hkbr" text="HKBR"/></form:option>
									</form:select>
									<form:errors path="bankAccHolderDocType" cssClass="error" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><spring:message code="lts.payment.bankAccHolderDocNo" text="Bank Account Holder Doc. No."/><span class="contenttext_red">*</span> :</td>
								<td><form:input path="bankAccHolderDocNum" id="bankAccHolderDocNum" size="20" maxlength="20" />
									<form:errors path="bankAccHolderDocNum" cssClass="error" />
								</td>
							</tr>
						<tr>
							<td>&nbsp;</td>
							<td><spring:message code="lts.payment.bankCd" text="Bank Code"/><span class="contenttext_red">*</span> :</td>
							<td>
								<form:select path="bankCode" id="bankCode">
									<form:option value=""><spring:message code="lts.payment.select" text="--- Select ---"/></form:option>
									<form:options items="${autopayIssueBankList}" itemValue="itemKey" itemLabel="itemValue" />
								</form:select>
							<form:errors path="bankCode" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
								<td><spring:message code="lts.payment.branchCd" text="Branch Code"/><span class="contenttext_red">*</span> :</td>
								<td>
									<form:select path="branchCode" id="branchCode">
										<form:option value=""><spring:message code="lts.payment.select" text="--- Select ---"/></form:option>
										<form:options items="${branchList}" itemValue="itemKey" itemLabel="itemValue" />
									</form:select>
									<form:hidden path="branchCodeHidden" id="branchCodeHidden" />
									<form:errors path="branchCode" cssClass="error" />
								</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
								<td><spring:message code="lts.payment.bankAccNo" text="Bank Account No."/><span class="contenttext_red">*</span> :</td>
								<td><form:input path="bankAccNum" id="bankAccNum" maxlength="9"/>
									<form:errors path="bankAccNum" cssClass="error" />
								</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
								<td><spring:message code="lts.payment.autopayLimit" text="Auto Pay Upper Limit :"/> :</td>
								<td><form:input path="autoPayUpperLimit" id="autoPayUpperLimit"/>
									<form:errors path="autoPayUpperLimit" cssClass="error" />
								</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
								<td><spring:message code="lts.payment.appDate" text="Application Date"/><span class="contenttext_red">*</span> :</td>
								<td><form:input path="applicationDate" id="applicationDate" readonly="true"/>
								</td>
						</tr>																																															
					</table>
					<br>
					<c:if test="${not empty ltspayment.prePayItemA}">
						<br>
						<table width="98%" border="0" align="center">
							<tr> 
	                   			<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.prepayInfo" text="Prepayment Information"/></td>
	            			</tr>
	            			<tr>
	            			<td>
	            			<table width="100%" border="0" cellspacing="1" class="table_style_sb">	
								<tr>
									<th width="80%" colspan="2">&nbsp;</th>
									<th width="20%"><spring:message code="lts.payment.amt" text="Amount"/></th>
								</tr>
								<tr>
									<td valign="top"  width="2%">										
										<form:checkbox path="prePayItemA.selected" id="prepayItemSelectedA" disabled="${ltspayment.prePayItemA.mdoInd == 'M'}"/>
									</td>
									<td valign="top" width="75%">
										${ltspayment.prePayItemA.itemDisplayHtml}
									</td>
									<td align="right" valign="top" class="itemPrice">
										${ltspayment.prePayItemA.oneTimeAmtTxt}
									</td>
								</tr>
								<tr>
								<td>&nbsp;</td>
								<td width="90%" align="left" colspan="2">
								<span><spring:message code="lts.payment.custExistAcc" text="Customer’s existing Account No. of PCCW Bill"/>:</span>
								<input type="text" id="existBillAccNumA" value="${ltspayment.existBillAccNum}" readonly="readonly" />
 								</td>
								</tr>
							</table>
	            			</td>
	            			</tr>
	            		</table>
					</c:if>
					</div>	
					<div id="existingAcctDiv">
					<c:if test="${not empty ltspayment.prePayItemE}">
						<br>
						<table width="98%" border="0" align="center">
							<tr> 
	                   			<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.prepayInfo" text="Prepayment Information"/></td>
	            			</tr>
	            			<tr>
	            			<td>
	            			<table width="100%" border="0" cellspacing="1" class="table_style_sb">	
								<tr>
									<th width="80%" colspan="2">&nbsp;</th>
									<th width="20%"><spring:message code="lts.payment.amt" text="Amount"/></th>
								</tr>
								<tr>
									<td valign="top"  width="2%">
										<form:checkbox path="prePayItemE.selected" id="prepayItemSelectedE" disabled="${ltspayment.prePayItemE.mdoInd == 'M'}"/>
									</td>
									<td valign="top" width="75%">
										${ltspayment.prePayItemE.itemDisplayHtml}
									</td>
									<td align="right" valign="top" class="itemPrice" width="20%">
										${ltspayment.prePayItemE.oneTimeAmtTxt}
									</td>
								</tr>
								<tr>
								<td width="10">
									&nbsp;
								</td>
								<td align="left" colspan="2">
								<span><spring:message code="lts.payment.custExistAcc" text="Customer’s existing Account No. of PCCW Bill"/> :</span>
								<input type="text" id="existBillAccNumE" value="${ltspayment.existBillAccNum}" readonly="readonly" />
 								</td>
								</tr>
							</table>
	            			</td>
	            			</tr>
	            		</table>
					</c:if>
				</div>
				<c:if test="${ltspayment.salesMemoNumRequired}">
					<div id="salesMemoNumDiv" style="display:none;">
						<table width="98%" border="0" align="center">
						<tr>
			            <td>
			            	<table width="100%" border="0" cellspacing="1" class="contenttext">	
							<tr>
							<td width="10%">
								&nbsp;
							</td>
				            <td width="90%" align="left" colspan="2">
								<span><spring:message code="lts.payment.salesMemoNum" text="Sales Memo Number"/> </span><span class="contenttext_red">*</span> :
								<form:input path="salesMemoNum" id="salesMemoNum" maxlength="14"/>
								<form:errors path="salesMemoNum" cssClass="error"/>
							</td>
							</tr>
							</table>
						</td>
						</tr>
						</table>
					</div>
				</c:if>
			</tr>
		</table>
	<br/>
	<c:if test="${ltspayment.allowChangeBa}">
			<table width="98%" border="0" align="center">
				<tr> 
	               	<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.billingAddr" text="Billing Address"/></td>
	        	</tr>
	        	<tr> 
	               	<td colspan="3">&nbsp;</td>
	        	</tr>
				<tr>
					<td width="5%">&nbsp;</td>
					<td class="contenttext"><form:checkbox id="changeBaCheckbox" path="changeBa" /><b> <spring:message code="lts.payment.chgBillingAddr" text="Change Billing Address"/></b></td>
				</tr>
				<tr>
					<td width="5%">&nbsp;</td>
					<td class="contenttext"><form:checkbox id="instantUpdateBaCheckbox" path="instantUpdateBa" /><b> <spring:message code="lts.payment.instUpdate" text="Instant Update"/></b></td>
				</tr>
			</table>
			<div id="baDiv">
				<table width="98%" border="0" cellpadding="1" cellspacing="1" class="contenttext" align="center">
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td width="5%">&nbsp;</td>
						<td width="35%"><spring:message code="lts.payment.inputBillingAddrMsg" text="Please Enter the new billing address"/> :</td>
						<td align="left">
							<form:errors path="baQuickSearch" cssClass="error" />
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellpadding="1" cellspacing="1" class="contenttext">
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>							
					<tr>
						<td align="right" width="20%">
							<span class="contenttext_red">*</span><spring:message code="lts.payment.quickSearch" text="Quick Search"/> :
						</td>
						<td align="left">
							<form:input path="baQuickSearch" size="80" />
							&nbsp;
							<div class="func_button">
								<a href="#" id="clearBaInputAddress"><spring:message code="lts.payment.clear" text="Clear"/></a>
							</div>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<spring:message code="lts.payment.quickSearchMsg" text="Simply input the building name of your billing address, separate with a comma (,)"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td align="right" valign="top" width="15%">
							<span class="contenttext_red">*</span><spring:message code="lts.payment.billingAddr" text="Billing Address"/> :&nbsp;
						</td>
						<td>
							<div style="float:left">
								<form:textarea id="billingAddressTextArea" path="billingAddress" rows="6" cols="40" cssStyle="resize: none;"/>
							</div>
							<div style="width: 30%; float: left; padding-left: 10px;">
								<form:errors path="billingAddress" cssClass="error" />
							</div>
						</td>
					</tr>							
				</table>
			</div>
	</c:if>
			<br/>
	<table width="98%" border="0" align="center">
		<tr> 
			<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.billSelection" text="Bill Selection"/></td>
		</tr>
	    <tr>
			<td>
			<table width="100%" border="0" class="table_style_sb">	
			<tr>
				<th valign="top" width="75%" colspan="2">&nbsp;</th>
				<th valign="top" width="20%"><spring:message code="lts.payment.amt" text="Amount"/></th>
			</tr>
			<form:errors path="selectedBillItemId" cssClass="error" />
		<!-- BILL OPTIONS -->
			<c:forEach items="${ltspayment.billItemList}" var="billItem" varStatus="status">
				<tr id="${billItem.itemType}">
					<td valign="top"  width="10">
						<input type="radio" class="billListRadio" name="billListRadio" value="${billItem.itemId}" checked="${billItem.itemType == 'EMAILBILL' ? 'checked' : ''}"/>
					</td>
					<td valign="top" width="75%" class="" >
						<div>${billItem.itemDisplayHtml}</div>
						
						 <c:if test="${billItem.itemType == 'EMAILBILL'}">
							<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.payment.emailAddr" text="Email Address"/></span>								
								<form:input id="emailBillAddress" path="emailBillAddress" />
								<form:errors path="emailBillAddress" cssClass="error"/>
							</div>
						</c:if> 
                         <c:if test="${billItem.itemType == 'PAPER-BILL' || billItem.itemType == 'PAPER-W' || billItem.itemType == 'PAPER-G'}">
							<div id="paperBillChargeDiv" style="display: inline-block; padding-left:15px ;padding:5px; padding-right:30px ;float: left; vertical-align: top;"><span style="display:inline-block; min-width:110px"><spring:message code="lts.payment.paperBillChrg" text="Paper Bill Charging"/>   </span>
								<form:select id="paperBillCharge" path="paperBillCharge">
                                  <form:option label="--- Select ---" value="NONE" />
                                  <form:option value="Y" label="Charge"/>
                                  <form:option value="W" label="Waive"/>
                                  <form:option value="" label="Remain Unchange"/>
                                  <%-- <form:option value="G" label="Generate" disabled="true"/>
                                  <form:option value="E" label="Exempt" disabled="true"/> --%>
                                </form:select>
                                <form:errors path="paperBillCharge" cssClass="error"/>
							</div>
							<div id="waiveReasonDiv">
								<div style="display: inline-block; padding-left:15px ;padding:5px; float: left; vertical-align: top;"><span style="display:inline-block; min-width:110px"><spring:message code="lts.payment.waiveReason" text="Waive Reason"/>   </span>
									<form:select id="paperWaiveReason"  path="paperWaiveReason">
		                                <form:options items="${paperWaiveReasonList}" itemLabel="itemValue" itemValue="itemKey" /> 
		                            </form:select>
								</div>
	                            <div style="display: inline-block; padding: 5px;">
		                            <form:input maxlength="10" path="paperBillWaiveOtherStaffId" id="paperBillWaiveOtherStaffId" />
		                            <br><span style="font-size:10px"><spring:message code="lts.payment.approvalStaffIdMsg" text="(Please input UM or above staff ID for approval)"/> </span> 
	                            </div>
                            </div>
                            <br/>
                            <form:errors path="paperWaiveReason" cssClass="error"/>
						</c:if>
					</td>
					<td align="right" valign="top" class="itemPrice" width="20%">
						${billItem.mthToMthAmt}
					</td>
				</tr>
			</c:forEach>
		<!-- CS PORTAL -->	
		<c:choose>	
			<c:when test="${not empty ltspayment.csPortalItem}">
		       <c:if test="${!isDocValidCsp}">
				  <tr>
						<td colspan="3"><span style="color:red"><spring:message code="lts.payment.cspValidDocMsg" text="Please use a valid Document number for My HKT/ The Club registration."/></span></td>
				   </tr>
				</c:if>	
		       	<tr>
					<td valign="top"  width="10">
						<form:checkbox id="cspCheckbox" path="csPortalItem.selected" disabled="${ !ltspayment.enableCsp || ltspayment.csPortalItem.mdoInd == 'M'}"/>
					</td>
					<td valign="top" width="75%" class="">
						${ltspayment.csPortalItem.itemDisplayHtml}
						
						<c:choose>
					   <c:when test="${!ltspayment.csPortalExist && !ltspayment.clubMembExist && isDocValidCsp}">
					  	 <div>
							 <div style="padding-left:15px ;padding:5px ;display:inline-block"><span style="display:inline-block; min-width:155px"><spring:message code="lts.payment.cspEmailAddr" text="The Club & My HKT Email Address"/> 	</span></div>
						 	 <div style="padding-left:15px ;padding:5px ;display:inline-block"><form:input cssClass="cspAttb" id="cspEmail" path="cspEmail" disabled="${ltspayment.csPortalExist || !isDocValidCsp}"/></div>
						     <div style="padding-left:15px ;padding:5px ;display:inline-block;" id="noEmailBtn" class="func_button"><spring:message code="lts.payment.noEmail" text="No Email"/></div>
						     <div style="padding-left:15px ;padding:5px ;display:none" id="clearEmailBtn" class="func_button" ><spring:message code="lts.payment.clear" text="Clear"/></div>
						     <c:if test="${ltspayment.phantomAcct}">
						     	<font style="color:red"><spring:message code="lts.payment.phantomAccExist" text="Phantom Account Exist"/></font>
						     </c:if>
						 </div>
					  	<div>	
							<div style="padding-left:15px ;padding:5px ;display:inline-block"><span style="display:inline-block; min-width:155px"><spring:message code="lts.payment.cspMobNum" text="The Club & My HKT Mobile Number"/> 	</span></div>
							<div style="padding-left:15px ;padding:5px ; display:inline-block"><form:input cssClass="cspAttb" path="cspMobile" /></div>
							<div style="padding-left:15px ;padding:5px ;display:inline-block;" id="noMobileBtn" class="func_button"><spring:message code="lts.payment.noMob" text="No Mobile"/></div>
							<div style="padding-left:15px ;padding:5px ;display:none" id="clearMobileBtn" class="func_button" ><spring:message code="lts.payment.clear" text="Clear"/></div>
					   	</div>
	                  </c:when>
					   <c:otherwise>
							<% /* HKT */ %>
							<div>
								<div style="padding-left:15px ;padding:5px ;display:inline-block"><span style="display:inline-block; min-width:155px"><spring:message code="lts.payment.myHKTEmailAddr" text="My HKT Email Address"/>	</span></div>
								<div style="padding-left:15px ;padding:5px ;display:inline-block"><form:input cssClass="cspAttb" id="cspEmail" path="cspEmail" disabled="${ltspayment.csPortalExist || !isDocValidCsp}"/></div>
								<c:if test="${ltspayment.clubMembExist && isDocValidCsp}">
									<div style="padding-left:15px ;padding:5px ;display:inline-block;" id="copyInfoBtn" class="func_button"><spring:message code="lts.payment.copy" text="Copy"/></div>
								</c:if>
							</div>
							<c:if test="${!ltspayment.csPortalExist && isDocValidCsp}">
					    		<div>	
									<div style="padding-left:15px ;padding:5px ;display:inline-block"><span style="display:inline-block; min-width:155px"><spring:message code="lts.payment.myHKTMobNum" text="My HKT Mobile Number"/>	</span></div>
									<div style="padding-left:15px ;padding:5px ; display:inline-block"><form:input cssClass="cspAttb" id="cspMobile" path="cspMobile" /></div>
								</div>
	                    	</c:if>
	                   	
	                   		<% /* THE CLUB */ %>
	                   		<div>
	                    		<div style="padding-left:15px ;padding:5px ;display:inline-block""><span style="display:inline-block; min-width:155px"><spring:message code="lts.payment.theClubEmailAddr" text="The Club Email Address"/>	</span></div>
								<div style="padding-left:15px ;padding:5px ;display:inline-block"><form:input cssClass="cspAttb" id="clubEmail" path="clubEmail" disabled="${ltspayment.clubMembExist || !isDocValidCsp}"/></div>
								<c:if test="${ltspayment.csPortalExist && isDocValidCsp}">
									<div style="padding-left:15px ;padding:5px ;display:inline-block;" id="copyInfoBtn" class="func_button"><spring:message code="lts.payment.copy" text="Copy"/></div>
									<div style="padding-left:15px ;padding:5px ;display:none;" id="noEmailBtn" class="func_button"><spring:message code="lts.payment.noEmail" text="No Email"/></div>
							        <div style="padding-left:15px ;padding:5px ;display:none;" id="clearEmailBtn" class="func_button" ><spring:message code="lts.payment.clear" text="Clear"/></div>									
								</c:if>
								<c:if test="${ltspayment.phantomAcct}">
						     		<font style="color:red"><spring:message code="lts.payment.phantomAccExist" text="Phantom Account Exist"/></font>
						     </c:if>
							</div>							
							<c:if test="${!ltspayment.clubMembExist && isDocValidCsp}">
 								<div>	
 									<div style="padding-left:15px ;padding:4px ;display:inline-block"><span style="display:inline-block; min-width:155px"><spring:message code="lts.payment.theClubMobNum" text="The Club Mobile Number"/>	</span></div>
									<div style="padding-left:15px ;padding:5px ;display:inline-block"><form:input cssClass="cspAttb" id="clubMobile" path="clubMobile" /></div> 
									<c:if test="${ltspayment.csPortalExist && isDocValidCsp}">	
										<div style="padding-left:15px ;padding:5px ;display:inline-block;" id="noMobileBtn" class="func_button"><spring:message code="lts.payment.noMob" text="No Mobile"/></div>
										<div style="padding-left:15px ;padding:5px ;display:none" id="clearMobileBtn" class="func_button" ><spring:message code="lts.payment.clear" text="Clear"/></div>
									</c:if>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>
					</td>
					<td align="right" valign="top" class="itemPrice" width="20%">
						${ltspayment.csPortalItem.mthToMthAmt}
					</td>
				</tr>
				<c:if test="${!ltspayment.clubMembExist && ltspayment.csPortalExist && isDocValidCsp}">	
						<tr>	
							<td valign="top"  width="10">
								<form:checkbox id="viewBillCheckbox" path="viewBillItem.selected" disabled="true"/>
							</td>
							<td valign="top" width="75%" class="">
								${ltspayment.viewBillItem.itemDisplayHtml}
							</td>
						</tr>	
 				</c:if>
 			<c:if test="${(!ltspayment.clubMembExist || !ltspayment.csPortalExist )&& isDocValidCsp}">	
 				<tr>
 				<td></td>
 				<td>
 					<c:choose>
 				
 						<c:when test="${!ltspayment.csPortalExist && ltspayment.clubMembExist && isDocValidCsp}">
 							<b><spring:message code="lts.payment.myHKTDirMktOptInOut" text="My HKT Direct Marketing Opt in/Opt out information"/> </b>
 						</c:when>
 						<c:when test="${ltspayment.csPortalExist && !ltspayment.clubMembExist && isDocValidCsp}">
 							<b><spring:message code="lts.payment.theClubDirMktOptInOut" text="The Club Direct Marketing Opt in/Opt out information"/></b>
 						</c:when>
 						<c:otherwise>
 							<b><spring:message code="lts.payment.cspDirMktOptInOut" text="The Club & My HKT Direct Marketing Opt in/Opt out information"/></b>
 						</c:otherwise>
 					</c:choose>
 				</td>
 				<td></td>
 				</tr>
 					<tr>
 						<td></td>
 						<td>
 						<div>
 							<div style="margin-left: 20px ;padding-left:15px ;padding:10px ;display:inline-block"><form:radiobutton id="optIn" path="optOut" value="N" cssClass="cspAttb cspRadio" label="OptIn All" cssStyle="checked:true"/></div>
 							<div style="padding-left:15px ;padding:10px ;display:inline-block"><form:radiobutton id="optOut" path="optOut" value="Y" cssClass="cspAttb cspRadio" label="OptOut All"/> </div>
 						</div>
 					<c:if test="${!ltspayment.clubMembExist && isDocValidCsp}">
 						<div>
 							<div style="margin-left: 25px ;padding-left:15px ;padding:10px;display:inline-block"> <spring:message code="lts.payment.optOutReason" text="Opt Out Reason"/> </div>
	 						<div style="padding-left:30px ;padding:10px;display:inline-block">
								<form:select path="optOutReason" id="optOutReason" disabled="true">
									<form:option value=""><spring:message code="lts.payment.select" text="--- Select ---"/></form:option>
									<form:options items="${optOutReasonList}" itemValue="itemKey" itemLabel="itemValue" />
								</form:select>	
							</div>
							<form:errors path="optOutReason" cssClass="error"/>
						</div>
						<div>
 							<div style="margin-left: 25px ;padding-left:15px ;padding:10px;display:inline-block"> <spring:message code="lts.payment.optOutRmk" text="Opt Out Remarks"/> </div>
 							<div style="padding-left:15px ;padding:10px;display:inline-block">
 							<form:textarea cssStyle="vertical-align:middle" path="optOutRemark" id="optOutRemark" disabled="true"/>
 							</div>			
 							<form:errors path="optOutRemark" cssClass="error"/>			
 				 		</div>
 				 	</c:if>
 				 		</td>
 				 		<td></td>
 					</tr>
  			 </c:if>
			</c:when>
			<c:otherwise>
			<!-- Registered both the Club and MyHKT -->
				<c:if test="${not empty ltspayment.viewBillItem}">
					<tr>
						<td valign="top"  width="10">
							<form:checkbox id="viewBillCheckbox" path="viewBillItem.selected" disabled="true"/>
						</td>
						<td valign="top" width="75%" class="">
							${ltspayment.viewBillItem.itemDisplayHtml}
							
								<div>
									<div style="padding-left:15px ;padding:5px ;display:inline-block"><span style="display:inline-block; min-width:155px"><spring:message code="lts.payment.myHKTEmailAddr" text="My HKT Email Address"/> </span></div>
									<div style="padding-left:15px ;padding:5px ;display:inline-block"><form:input cssClass="cspAttb" id="cspEmail" path="cspEmail" disabled="true"/></div>
								</div>
								<div>
            	        			<div style="padding-left:15px ;padding:5px ;display:inline-block""><span style="display:inline-block; min-width:155px"><spring:message code="lts.payment.theClubEmailAddr" text="The Club Email Address"/> </span></div>
									<div style="padding-left:15px ;padding:5px ;display:inline-block"><form:input cssClass="cspAttb" id="clubEmail" path="clubEmail" disabled="true"/></div>
								</div>
						</td>
						<td align="right" valign="top" class="itemPrice" width="20%">
							${ltspayment.viewBillItem.mthToMthAmt}
						</td>
					</tr>
				 </c:if>
			</c:otherwise>
		</c:choose>
			
			<form:input id="hiddenSelectBillItem" path="selectedBillItemId" cssStyle="display:none"/>
			</table>
			</td>
		</tr>
	</table>
	<br>

	<c:if test="${ltspayment.requireRedemPremium }">
		<table width="98%" border="0" align="center" cellpadding="4" cellspacing="4">
			<tr> 
				<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.redemMedia" text="Redemption Media"/></td>
			</tr>
			<tr>
			 	<td width="10">&nbsp;</td>
				<td width="15%"><form:radiobutton path="redemptionMedia" value="M"/> <spring:message code="lts.payment.sms" text="SMS"/> </td>
				<td><spring:message code="lts.payment.mobileNum" text="Moblie Number"/>: <form:input path="redemMediaSmsNum"/> <form:errors path="redemMediaSmsNum" cssClass="error"/></td>
			</tr>
			<%-- <tr>
			 	<td width="10">&nbsp;</td>
				<td width="15%"><form:radiobutton path="redemptionMedia" value="S"/> Email </td>
				<td>Email Address: <form:input path="redemMediaEmailAddr"/> <form:errors path="redemMediaEmailAddr" cssClass="error"/></td>
			</tr>  --%>
			<c:if test="${ltspayment.allowRedemPaper }">
				<tr>
				 	<td width="10">&nbsp;</td>
					<td width="15%"><form:radiobutton path="redemptionMedia" value="P"/> <spring:message code="lts.payment.postal" text="Postal"/> </td>
					<td><form:errors path="redemptionMedia" cssClass="error"/></td>
				</tr>
			</c:if>
		</table>
		<br>
	</c:if>
	
	<c:if test="${not empty ltspayment.erChargeItemList }">
		<table width="98%" border="0" align="center">
			<tr> 
				<td colspan="3" class="table_title" id="s_line_text"><spring:message code="lts.payment.erChrg" text="External Relocation Charges:"/></td>
			</tr>
		    <tr>
				<td>
					<table width="100%" border="0" class="table_style_sb">	
						<tr>
							<th valign="top" width="75%" colspan="2">&nbsp;</th>
							<th valign="top" width="20%"><spring:message code="lts.payment.amt" text="Amount"/></th>
						</tr>
						<c:forEach items="${ltspayment.erChargeItemList}" var="erChargeItem" varStatus="status">
							<tr>
								<td valign="top" width="10">
									<form:hidden path="erChargeItemList[${status.index}].selected"/>
								</td>
								<td valign="top" width="75%">
									${erChargeItem.itemDisplayHtml}
									&nbsp;
									
									<c:choose>
										<c:when test="${ erChargeItem.penaltyHandling == 'MW'}">
											<form:select cssClass="penalty" path="erChargeItemList[${status.index}].penaltyHandling">
												<form:option value="MW" label="Special Waive (Marketing Approved)"/>
											</form:select>	
										</c:when>
									<c:otherwise>
										<form:select cssClass="penalty" path="erChargeItemList[${status.index}].penaltyHandling">
											<c:choose>
											<c:when test="${(not empty sessionScope.sessionLtsOrderCapture.ltsServiceProfile.existEyeType 
															|| not empty sessionScope.sessionLtsOrderCapture.ltsServiceProfile.srvGrp)
															|| not empty sessionScope.sessionLtsOrderCapture.newAddressRollout.hktPremier}">
												<c:forEach items="${erEyeChargeHandleList}" var="erEyeChargeOption" varStatus="erEyeChargeStatus">
													<c:if test="${ erEyeChargeOption.itemKey != 'PA'}">
														<form:option value="${erEyeChargeOption.itemKey}" label="${erEyeChargeOption.itemValue}" />	
													</c:if>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach items="${erDelChargeHandleList}" var="erDelChargeOption" varStatus="erDelChargeStatus">
													<c:if test="${ erDelChargeOption.itemKey != 'PA'}">
														<form:option value="${erDelChargeOption.itemKey}" label="${erDelChargeOption.itemValue}" />	
													</c:if>
												</c:forEach>
											</c:otherwise>
											</c:choose>
										</form:select>
									</c:otherwise>
									
									</c:choose>
								</td>
								<td align="right" valign="top" class="itemPrice" width="20%">
									${erChargeItem.oneTimeAmtTxt}
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	<div id="approvalRemarkDiv" style="display: none;">
		<table width="100%" border="0" class="contenttext" cellspacing="20">
			<tr>
				<td align="right">
					<a id="approvalRemarkBtn" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.payment.apprvalRmk" text="Approval Remark"/></div></a> 
				</td>
			</tr>
		</table>
	</div>
	</c:if>
	<br/>
	</td>
	</tr>
</table>
<c:if test="${not empty requestScope.errorMsgList || not empty param.errorMsg}">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="contenttext">
		<tr>
			<td width="2%">&nbsp;</td>
			<td>
				<div id="error" class="ui-widget" style="visibility: visible; width: 70%;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<c:forEach items="${requestScope.errorMsgList}" var="errorMsg">
							<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							</p>
							<div id="error_msg" class="contenttext">
								${errorMsg}
							</div>
							<p></p>										
						</c:forEach>
						<c:if test="${not empty param.errorMsg}">
							<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							</p>
							<div id="error_msg" class="contenttext">
								${param.errorMsg}
							</div>
							<p></p>										
						</c:if>
					</div>
				</div>
			</td>
			<td width="5%">&nbsp;</td>
		</tr>
	</table>
</c:if>
	
<br>
<table width="100%" border="0" cellspacing="0" class="contenttext">
<tr>
	<td align="right">
		<form:errors path="suspendReason" cssClass="error"/>
			<b><spring:message code="lts.payment.suspendReason" text="Suspend Reason"/> : </b>
		<form:select path="suspendReason" id="suspendReason">
			<form:option value="NONE"><spring:message code="lts.payment.select" text="--- Select ---"/></form:option>
			<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
		</form:select>
		<a id="suspend" href="#" class="nextbutton"><div class="button"><spring:message code="lts.payment.suspend" text="Suspend"/></div></a> &nbsp; 

	<a id="submit" href="#" class="nextbutton"><div class="button"><spring:message code="lts.mis.next" text="Next"/></div></a> 

	</td>
</tr>
</table>

	
<form:hidden path="submitInd" id="submitInd" />
<form:hidden path="existBillAccNum" id="existBillAccNum" />
<form:hidden path="custName" id="custName" />
<form:hidden path="custDocType" id="custDocType" />
<form:hidden path="custDocNum" id="custDocNum" />
<form:hidden path="dummyClubEmail" id="dummyClubEmail" />
</form:form>
</div>

<div id="tapAndGoPolicyDialog" title="Pre-payment policy changed" style="display:none">
  <p>
    Pre-payment policy was changed and follow cash policy
  </p>
</div>

<script language="javascript">
	//var cusName = "${custName}";
	//var docType = "${docType}";
	//var docNum = "${docNum}";

	var isRenewOrder = "${sessionScope.sessionLtsOrderCapture.orderType == 'SBR'}" == "true";
	var prePayItemEInd = "${not empty ltspayment.prePayItemE}" == "true";
	var prePayItemAInd = "${not empty ltspayment.prePayItemA}" == "true";
	var prePayItemCInd = "${not empty ltspayment.prePayItemC}" == "true";
	var prePayItemMInd = "${not empty ltspayment.prePayItemM}" == "true";
	var existPayMethod = "${existPayMethod}";
	var itemEMdo = "${ltspayment.prePayItemE.mdoInd}";
	var itemAMdo = "${ltspayment.prePayItemA.mdoInd}";
	var itemCMdo = "${ltspayment.prePayItemC.mdoInd}";
	
	var isHKT = "${ltspayment.csPortalExist}" == "true";
	var isClub = "${ltspayment.clubMembExist}" == "true";
	var isDummy = "$(ltspayment.dummy)" == "true";
	
	var isRecontract = "${sessionScope.sessionLtsOrderCapture.ltsRecontractForm}" != "";
	
	function openCEKSWindow(){
		//var form = document.forms['createForm'];
		
		var sbuid = $('input[name="sbuid"]').val();
		var ceksLink = "ltsceks.html?sbuid=" + sbuid;
		if (ceksLink != null && ceksLink != "") {
			window.open(ceksLink, "_blank", "width=550, height=400, resizable=no, toolbar=yes, location=yes, menubar=yes, status=yes, left=100, top=100");
			//form.elements['ceksUrl'].value = "";
		}
	}
	
	function refreshForm(cardnum){
		document.getElementById('cardnum').value = cardnum;
		if(cardnum.substring(0, 1)=="4"){
			//CODE_TYPE 01 : VISA
			document.getElementById('cardtype').selectedIndex = 1;  
		}else if(cardnum.substring(0, 2)=="51" || cardnum.substring(0, 2)=="52"
				 || cardnum.substring(0, 2)=="53" || cardnum.substring(0, 2)=="54"
				 || cardnum.substring(0, 2)=="55"){
			//CODE_TYPE 02 : MASTER
			document.getElementById('cardtype').selectedIndex = 2; 
		}else if(cardnum.substring(0, 2)=="34" || cardnum.substring(0, 2)=="37"){
			//CODE_TYPE 03 : AMEX
			document.getElementById('cardtype').selectedIndex = 3; 
		}else{
			document.getElementById('cardtype').selectedIndex = 0;
		}
		
		if(cardnum.substring(0, 4)=="5599"){
			if(!isRenewOrder){
				$("#tapAndGoPolicyDialog").dialog({
				      modal: true,
				      buttons: {
				        Ok: function() {
				          $( this ).dialog( "close" );
				        }
				      }
				});
			}
			$("#creditCardDivPrePayM").show();
			$("#creditCardDivPrePayC").hide();
		}else{
			$("#creditCardDivPrePayC").show();
			$("#creditCardDivPrePayM").hide();
		}
	}
	
	function showSalesMemo(show){
		if(show){
			$("#salesMemoNumDiv").show();
		}else{
			$("#salesMemoNumDiv").hide();
		}
	}
	
	function show( which )
	{
		//E - existingAcctDiv, C - creditCardDiv, A-autoPayDiv
		if (which=='A'){
			$("#autoPayDiv").show();
			$("#creditCardDiv").hide();
			$("#existingAcctDiv").hide();
			if(prePayItemAInd){
				showSalesMemo(true);
			}else{
				showSalesMemo(false);
			}
	    }else if (which=='C'){
			$("#autoPayDiv").hide();
			$("#existingAcctDiv").hide();
			$("#creditCardDiv").show();
			showSalesMemo(false);

			if($("#cardnum").val().substring(0, 4)=="5599"){
				$("#creditCardDivPrePayM").show();
				$("#creditCardDivPrePayC").hide();
			}else{
				$("#creditCardDivPrePayC").show();
				$("#creditCardDivPrePayM").hide();
			}
		}else {
			$("#autoPayDiv").hide();
			$("#creditCardDiv").hide();
			$("#existingAcctDiv").show();
			showSalesMemo(false);
			if(prePayItemEInd){
				if("${existPayMethod}" == 'M'
					|| "${existPayMethod}" == 'A'){
					showSalesMemo(true);
				}
			}
		}
	}
	var isCallCenter = "${sessionScope.sessionLtsOrderCapture.channelCs}" == "true";
	
	$(ltspayment.actionPerform);		
</script>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>