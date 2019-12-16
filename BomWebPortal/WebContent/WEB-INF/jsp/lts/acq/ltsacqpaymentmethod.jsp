<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/web/lts/acq/ltsacqpaymentmethod.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="7" />
</jsp:include>

<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.css" rel="stylesheet" />

<c:set var="isOrderSubmitted"
	value="${sessionScope.sessionLtsAcqOrderCapture.sbOrder != null}" />

<form:form method="POST" action="#" id="paymentmethodform"	name="ltsPaymentMethodForm" commandName="ltspaymentmethod" autocomplete="off">
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<input type="hidden" id="errorMsg" />
	<form:hidden path="submitInd" id="submitInd" />
	<form:hidden path="custName" id="custName" />
	<form:hidden path="custDocType" id="custDocType" />
	<form:hidden path="custDocNum" id="custDocNum" />
		<table width="100%" class="paper_w2 round_10">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="1" class="paper_w2 round_10">
						<tr><td>
							<div id="s_line_text"><spring:message code="lts.acq.paymentMethod.paymentMethod" htmlEscape="false"/></div>
						</td></tr>
						<tr><td>
							<table width="100%" cellspacing="1" border="0" >
							<c:if test="${not empty param.outQuoErrMsgList}">
								<tr>
									<td>
										<div id="error_div" class="ui-widget" style="visibility: visible;">
											<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
												<c:forEach items="${param.outQuoErrMsgList}" var="outQuoErr">
													<p>
														<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
													</p>
													<div id="error_msg" class="contenttext">
														${outQuoErr}		
													</div>
													<p></p>
												</c:forEach>
											</div>
										</div>
									</td>
								</tr>
							</c:if>
							<tr>
								<td>
									<c:forEach items="${ltspaymentmethod.paymentMethodDtlList}" var="payMtdDtl" varStatus="status">
									<br/><br/>   
									<table width="100%" cellspacing="1" border="0" class="contenttext" >
									<tr>        
				              			 <c:choose>
									     <c:when test='${dummyAccount}'>        
				              			 <td> <span style="color:DarkBlue;"> <b>&nbsp;<spring:message code="lts.acq.paymentMethod.accountNotAssigned" htmlEscape="false"/> </b></span></td>
				              			 </c:when>
				              			 <c:otherwise>        
				              			 <td> <span style="color:DarkBlue;"> <b>&nbsp;<spring:message code="lts.acq.paymentMethod.accountNumber" htmlEscape="false"/> ${payMtdDtl.acctNum} </b></span></td>
				              			 </c:otherwise>
				              			 </c:choose>
				              			 <c:if test='${payMtdDtl.acctChrgTypeI}'>
											<input type="hidden" name="acctTypeI" id="acctTypeI${status.index}" value="true" />
										 </c:if> 
						    		</tr>
						    	    <tr>
						    			 <td colspan="3"> <hr style="color: darkblue;"></td>
						    		</tr>
								    <tr>
										<td>
											<table width="100%" border="0" cellspacing="1" class="contenttext">
											<tr>
												<c:if test='${payMtdDtl.allowCash}'>
													<td width="25%">
														<form:radiobutton path="paymentMethodDtlList[${status.index}].newPayMethodType" id="payMethodTypeM${status.index}" value="M" />
														<c:if test="${payMtdDtl.existingPayMethodType == 'M'}">
															<b><spring:message code="lts.acq.paymentMethod.cash" htmlEscape="false"/></b><span class="contenttext_red">*</span>
														 </c:if> 
														<!-- <c:if test="${payMtdDtl.existingPayMethodType == 'M'}">
															<b>Keep Existing PCCW Telephone Payment Method</b><span class="contenttext_red">*</span>
														</c:if> -->
													</td>
												</c:if>
												<c:if test='${payMtdDtl.allowAutoPay}'>
													<td width="25%">
														<form:radiobutton path="paymentMethodDtlList[${status.index}].newPayMethodType" id="payMethodTypeA${status.index}" value="A" />
														<c:if test="${payMtdDtl.existingPayMethodType != 'A'}">
															<b><spring:message code="lts.acq.paymentMethod.bankAutoPay" htmlEscape="false"/></b><span class="contenttext_red">*</span>
														</c:if>
														<c:if test="${payMtdDtl.existingPayMethodType == 'A'}">
															<b><spring:message code="lts.acq.paymentMethod.keepExistingPaymentMethod" htmlEscape="false"/></b><span class="contenttext_red">*</span>
														</c:if>
													</td>
												</c:if>
												<c:if test='${payMtdDtl.allowCreditCard}'>
													<td width="25%">
														<form:radiobutton path="paymentMethodDtlList[${status.index}].newPayMethodType" id="payMethodTypeC${status.index}" value="C" />
														<!--<c:if test="${payMtdDtl.existingPayMethodType != 'C'}">-->
															<b><spring:message code="lts.acq.paymentMethod.creditCard" htmlEscape="false"/></b><span class="contenttext_red">*</span>
														<!--</c:if>-->
														<c:if test="${payMtdDtl.existingPayMethodType == 'C'}">
															<b><spring:message code="lts.acq.paymentMethod.keepExistingPaymentMethod" htmlEscape="false"/></b><span class="contenttext_red">*</span>
														</c:if>
													</td>
												</c:if>
												<c:if test='${payMtdDtl.allowAwaitPayment}'>
												<td width="25%">
													<form:radiobutton path="paymentMethodDtlList[${status.index}].newPayMethodType" id="payMethodTypeN${status.index}" value="N" />
														<b><spring:message code="lts.acq.paymentMethod.awaitPaymentMethod" htmlEscape="false"/></b><span class="contenttext_red">*</span>
												</td>
												</c:if>	
											</tr>
											<tr>
												<td><form:errors path="paymentMethodDtlList[${status.index}].newPayMethodType" cssClass="error" /></td>
											</tr>
											</table>
										</td>
									</tr>
									<tr>
									<td>
										<div id="existingPaymentMethod${status.index}">
											<form:hidden path="paymentMethodDtlList[${status.index}].existingPayMethodType" id="exPaymentMethodType${status.index}"/>
											<table width="100%" border="0" class="contenttext" cellspacing="3" style="padding-top:20px">	
												<c:if test="${payMtdDtl.existingPayMethodType != 'M'}">
												<tr>
												<td width="5%">&nbsp;</td>
												<td width="20%" align="right">
													<b><spring:message code="lts.acq.paymentMethod.existingPaymentMethod" htmlEscape="false"/> </b>
												</td>
												<td width="30%" align="left">
													<form:input path="paymentMethodDtlList[${status.index}].existingPayMethodDisplay" id="exPaymentMethod${status.index}" readonly="true"/>
												</td>
												</tr>
												</c:if>
											<!-- <c:if test="${payMtdDtl.existingPayMethodType == 'C'}">
												<tr>
												<td width="5%">&nbsp;</td>
												<td width="20%" align="right">
													<b>Card Num : </b>
												</td>
												<td width="30%" align="left">	
													<form:input path="paymentMethodDtlList[${status.index}].cardNum" id="exCardNum${status.index}" readonly="true"/>
												</td>
												</tr>
												</c:if> -->	
												<tr>
												<td width="5%">&nbsp;</td>
												<td>
												<c:if test='${!payMtdDtl.allowKeepExistPayMtd}'>
													<span class="contenttext_red"><spring:message code="lts.acq.paymentMethod.cannotKeepExistingPaymentMethod" htmlEscape="false"/></span>
												</c:if>
												</td>
												</tr>
											</table>
										</div>
									</td>
									</tr>
									<tr>
									<td valign="top" style="padding-left:30px">
					
									<div id="creditCardDiv${status.index}">
										<hr width="90%"/>
										<table width="100%" border="0" class="contenttext" cellspacing="3">	
											<!-- credit card info -->
											<tr>
												<td width="5%">&nbsp;</td>
												<td width="30%"><b><spring:message code="lts.acq.paymentMethod.thirdPartyCreditCard" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
												<td>
													<form:radiobutton path="paymentMethodDtlList[${status.index}].thirdPartyCard" id="thirdPartyCardTrue${status.index}" value="true" /> <spring:message code="lts.acq.common.yes" htmlEscape="false"/>
													<form:radiobutton path="paymentMethodDtlList[${status.index}].thirdPartyCard" id="thirdPartyCardFalse${status.index}" value="false" /> <spring:message code="lts.acq.common.no" htmlEscape="false"/>
													<form:errors path="paymentMethodDtlList[${status.index}].thirdPartyCard" cssClass="error" />
												</td>
											</tr>
											
											<tr>
												<td width="5%">&nbsp;</td>
												<td width="30%"><b><spring:message code="lts.acq.paymentMethod.cardHolderName" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
												<td>
													<form:input path="paymentMethodDtlList[${status.index}].cardHolderName" id="cardHolderName${status.index}" size="30" maxlength="30" />
													<form:errors path="paymentMethodDtlList[${status.index}].cardHolderName" cssClass="error" />
												</td>
											</tr>
											<tr id="not3rdpartycc${status.index}">
												<td>&nbsp;</td>
												<td><b><spring:message code="lts.acq.paymentMethod.cardHolderDocumentType" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
												<td>
													<form:select path="paymentMethodDtlList[${status.index}].cardHolderDocType" id="cardHolderDocType${status.index}" >
														<form:option value=""><spring:message code="lts.acq.paymentMethod.selectInSelector" htmlEscape="false"/></form:option>
														<form:option value="HKID"><spring:message code="lts.acq.paymentMethod.HKID" htmlEscape="false"/></form:option>
														<form:option value="PASS"><spring:message code="lts.acq.paymentMethod.passport" htmlEscape="false"/></form:option>
														<form:option value="BS"><spring:message code="lts.acq.paymentMethod.HKBR" htmlEscape="false"/></form:option>
													</form:select>
													<form:errors path="paymentMethodDtlList[${status.index}].cardHolderDocType" cssClass="error" />
												</td>
											</tr>
											<tr id="not3rdpartyccdocnum${status.index}">
												<td>&nbsp;</td>
												<td><b><spring:message code="lts.acq.paymentMethod.cardHolderDocumentNo" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
												<td>
													<form:input path="paymentMethodDtlList[${status.index}].cardHolderDocNum" id="cardHolderDocNum${status.index}" size="20" maxlength="20" />
													<form:errors path="paymentMethodDtlList[${status.index}].cardHolderDocNum" cssClass="error" />
												</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td ><b><spring:message code="lts.acq.paymentMethod.cardNumber" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
												<td>
													<form:input path="paymentMethodDtlList[${status.index}].cardNum" id="cardnum${status.index}" size="30" maxlength="16" readonly="true" />							
					        						<input type="hidden" name="token" id="token${status.index}" />
													<input type="hidden" name="ceksSubmit" value="N" />
													<a href="#" class="nextbutton" onclick="javascript:openCEKSWindow(${status.index})">
														<div class="func_button"><spring:message code="lts.acq.paymentMethod.inputCreditCardNo" htmlEscape="false"/></div>
													</a>
													<form:errors path="paymentMethodDtlList[${status.index}].cardNum" cssClass="error" />								
												</td>
											</tr>						
											<tr>
												<td>&nbsp;</td>
												<td><b><spring:message code="lts.acq.paymentMethod.creditCardType" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
												<td>
													<form:select path="paymentMethodDtlList[${status.index}].cardType" id="cardtype${status.index}">
														<form:option value=""><spring:message code="lts.acq.paymentMethod.selectInSelector" htmlEscape="false"/></form:option>
														<form:options items="${creditCardTypeList}" itemValue="itemKey" itemLabel="itemValue" />
													</form:select>
													<form:errors path="paymentMethodDtlList[${status.index}].cardType" cssClass="error" />
												</td>
											</tr>						
											<tr>
												<td>&nbsp;</td>
												<td><b><spring:message code="lts.acq.paymentMethod.expireDate" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
												<td><form:select path="paymentMethodDtlList[${status.index}].expireMonth">
														<c:forEach var="i" begin="1" end="12" step="1">
															<form:option value="${i}"><fmt:formatNumber minIntegerDigits="2" value="${i}" /></form:option>
														</c:forEach>																																						
													</form:select>
													<form:select path="paymentMethodDtlList[${status.index}].expireYear">
														<form:options items="${yearList}"/>
													</form:select>
													<form:errors path="paymentMethodDtlList[${status.index}].expireYear" cssClass="error" />
												</td>
											</tr>
											<c:if test="${!sessionScope.sessionAcqLtsOrderCapture.channelCs}">
											<tr id="not3rdpartyccverified${status.index}">
												<td>&nbsp;</td>
												<td valign="top"> <b><spring:message code="lts.acq.paymentMethod.creditCardVerified" htmlEscape="false"/><span class="contenttext_red">*</span> : </b>  </td>
												<td>
													<form:checkbox path="paymentMethodDtlList[${status.index}].cardVerified"/>
													<form:errors path="paymentMethodDtlList[${status.index}].cardVerified" cssClass="error" />
												</td>
											</tr>
											</c:if>
											<!-- end of credit card info -->
											
										</table>
										<br>
										</div>
										<div id="creditCardPrePayDiv${status.index}">
										<c:if test="${not empty payMtdDtl.creditCardPrePayItem}">
											<table width="100%" border="0" cellspacing="1" class="table_style_sb">	
													<tr>
													<th valign="top"  width="5%"></th>
													<th valign="top" width="75%"><spring:message code="lts.acq.paymentMethod.prepaymentInformation" htmlEscape="false"/></td>
													<th align="right" valign="top" width="20%">
														<spring:message code="lts.acq.paymentMethod.amount" htmlEscape="false"/>
													</th>
													</tr>
													<tr>
														<td valign="top">
														<form:checkbox path="paymentMethodDtlList[${status.index}].creditCardPrePayItem.selected" id="prepayItemSelectedC${status.index}" disabled="${payMtdDtl.creditCardPrePayItem.mdoInd == 'M'}"/>
													</td>
													<td valign="top" >
														${payMtdDtl.creditCardPrePayItem.itemDisplayHtml}
													</td>
													<td align="right" valign="top" class="itemPrice" >
														${payMtdDtl.creditCardPrePayItem.oneTimeAmtTxt}
													</td>
													</tr>
													<!-- <tr>
													<td >
														&nbsp;
													</td>
													<td align="left" colspan="2">
													<b><span>Customer’s existing Account No. of PCCW Bill :</span></b>
													<input type="text" id="existBillAccNumC${status.index}" readonly="readonly" />
					 								</td>
													</tr> -->
												</table>
										</c:if>
									</div>
									<div id="creditCardPrePayForTnGDiv${status.index}">
										<c:if test="${not empty payMtdDtl.cashPrePayItem}">
											<table width="100%" border="0" cellspacing="1" class="table_style_sb">	
													<tr>
													<th valign="top"  width="5%"></th>
													<th valign="top" width="75%"><spring:message code="lts.acq.paymentMethod.prepaymentInformation" htmlEscape="false"/></td>
													<th align="right" valign="top" width="20%">
														<spring:message code="lts.acq.paymentMethod.amount" htmlEscape="false"/>
													</th>
													</tr>
													<tr>
														<td valign="top">
														<form:checkbox path="paymentMethodDtlList[${status.index}].cashPrePayItem.selected" id="prepayItemSelectedC${status.index}" disabled="${payMtdDtl.cashPrePayItem.mdoInd == 'M'}"/>
													</td>
													<td valign="top" >
														${payMtdDtl.cashPrePayItem.itemDisplayHtml}
													</td>
													<td align="right" valign="top" class="itemPrice" >
														${payMtdDtl.cashPrePayItem.oneTimeAmtTxt}
													</td>
													</tr>
													<!-- <tr>
													<td >
														&nbsp;
													</td>
													<td align="left" colspan="2">
													<b><span>Customer’s existing Account No. of PCCW Bill :</span></b>
													<input type="text" id="existBillAccNumC${status.index}" readonly="readonly" />
					 								</td>
													</tr> -->
												</table>
										</c:if>
									</div>
				
									<div id="autoPayDiv${status.index}">
										<hr width="90%"/>
											<table width="100%" border="0" class="contenttext" cellspacing="3"> 
												<%-- <tr>
													<td width="5%">&nbsp;</td>
													<td width="30%"><b>Third Party Bank Account<span class="contenttext_red">*</span> : </b></td>
													<td>
														<form:radiobutton path="paymentMethodDtlList[${status.index}].thirdPartyBankAccount" id="thirdPartyBankAccountTrue${status.index}" value="true" /> Yes
														<form:radiobutton path="paymentMethodDtlList[${status.index}].thirdPartyBankAccount" id="thirdPartyBankAccountFalse${status.index}" value="false" /> No
														<form:errors path="paymentMethodDtlList[${status.index}].thirdPartyBankAccount" cssClass="error" />
													</td>
												</tr> --%>
												<tr>
													<td width="5%">&nbsp;</td>
													<td width="30%"><b><spring:message code="lts.acq.paymentMethod.bankAccountHolderName" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
													<td>
														<form:input path="paymentMethodDtlList[${status.index}].bankAccHolderName" id="bankAccHolderName${status.index}" size="30" maxlength="30" />
														<form:errors path="paymentMethodDtlList[${status.index}].bankAccHolderName" cssClass="error" />
													</td>
												</tr>
																
												<tr>
													<td>&nbsp;</td>
													<td><b><spring:message code="lts.acq.paymentMethod.bankAccountHolderDocType" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
													<td>
														<form:select path="paymentMethodDtlList[${status.index}].bankAccHolderDocType" id="bankAccHolderDocType${status.index}">
															<form:option value=""><spring:message code="lts.acq.paymentMethod.selectInSelector" htmlEscape="false"/></form:option>
															<form:option value="HKID"><spring:message code="lts.acq.paymentMethod.HKID" htmlEscape="false"/></form:option>
															<form:option value="PASS"><spring:message code="lts.acq.paymentMethod.passport" htmlEscape="false"/></form:option>
															<form:option value="BS"><spring:message code="lts.acq.paymentMethod.HKBR" htmlEscape="false"/></form:option>
														</form:select>
														<form:errors path="paymentMethodDtlList[${status.index}].bankAccHolderDocType" cssClass="error" />
													</td>
												</tr>
												<tr>
													<td>&nbsp;</td>
													<td><b><spring:message code="lts.acq.paymentMethod.bankAccountHolderDocNo" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
													<td><form:input path="paymentMethodDtlList[${status.index}].bankAccHolderDocNum" id="bankAccHolderDocNum${status.index}" size="20" maxlength="20" />
														<form:errors path="paymentMethodDtlList[${status.index}].bankAccHolderDocNum" cssClass="error" />
													</td>
												</tr>
											<tr>
												<td width="5%">&nbsp;</td>
												<td width="20%"><b><spring:message code="lts.acq.paymentMethod.bankCode" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
												<td>
													<form:select path="paymentMethodDtlList[${status.index}].bankCode" id="bankCode${status.index}">
														<form:option value=""><spring:message code="lts.acq.paymentMethod.selectInSelector" htmlEscape="false"/></form:option>
														<form:options items="${autopayIssueBankList}" itemValue="itemKey" itemLabel="itemValue" />
													</form:select>
												<form:errors path="paymentMethodDtlList[${status.index}].bankCode" cssClass="error" />
												</td>
											</tr>
											<tr>
												<td width="5%">&nbsp;</td>
													<td width="20%"><b><spring:message code="lts.acq.paymentMethod.branchCode" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
													<td>
														<form:select path="paymentMethodDtlList[${status.index}].branchCode" id="branchCode${status.index}">
															<form:option value=""><spring:message code="lts.acq.paymentMethod.selectInSelector" htmlEscape="false"/></form:option>
															<form:options items="${branchList}" itemValue="itemKey" itemLabel="itemValue" />
														</form:select>
														<form:hidden path="paymentMethodDtlList[${status.index}].branchCodeHidden" id="branchCodeHidden${status.index}" />
														<form:errors path="paymentMethodDtlList[${status.index}].branchCode" cssClass="error" />
													</td>
											</tr>
											<tr>
												<td width="5%">&nbsp;</td>
													<td width="20%"><b><spring:message code="lts.acq.paymentMethod.bankAccountNo" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
													<td><form:input path="paymentMethodDtlList[${status.index}].bankAccNum" id="bankAccNum${status.index}" maxlength="9"/>
														<form:errors path="paymentMethodDtlList[${status.index}].bankAccNum" cssClass="error" />
													</td>
											</tr>
											<tr>
												<td width="5%">&nbsp;</td>
													<td width="20%"><b><spring:message code="lts.acq.paymentMethod.autoPayUpperLimit" htmlEscape="false"/> </b></td>
													<td><form:input path="paymentMethodDtlList[${status.index}].autoPayUpperLimit" id="autoPayUpperLimit${status.index}"/>
														<form:errors path="paymentMethodDtlList[${status.index}].autoPayUpperLimit" cssClass="error" />
													</td>
											</tr>
											<tr>
												<td width="5%">&nbsp;</td>
													<td width="20%"><b><spring:message code="lts.acq.paymentMethod.applicationDate" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></td>
													<td><form:input path="paymentMethodDtlList[${status.index}].applicationDate" id="applicationDate${status.index}" readonly="true"/>
													</td>
											</tr>																																															
										</table>
										<br>
										<c:if test="${not empty payMtdDtl.autopayPrePayItem}">
											<table width="100%" border="0" cellspacing="1" class="table_style_sb">	
												<tr>
												<th valign="top"  width="5%"></th>
												<th valign="top" width="75%"><spring:message code="lts.acq.paymentMethod.prepaymentInformation" htmlEscape="false"/></td>
												<th align="right" valign="top" width="20%">
													<spring:message code="lts.acq.paymentMethod.amount" htmlEscape="false"/>
												</th>
												</tr>
												<tr>
													<td valign="top">
													<form:checkbox path="paymentMethodDtlList[${status.index}].autopayPrePayItem.selected" id="prepayItemSelectedA${status.index}" disabled="${payMtdDtl.autopayPrePayItem.mdoInd == 'M'}"/>
												</td>
												<td valign="top" >
													${payMtdDtl.autopayPrePayItem.itemDisplayHtml}
												</td>
												<td align="right" valign="top" class="itemPrice" >
													${payMtdDtl.autopayPrePayItem.oneTimeAmtTxt}
												</td>
												</tr>
												<!-- <tr>
												<td >
													&nbsp;
												</td>
												<td align="left" colspan="2">
												<b><span>Customer’s existing Account No. of PCCW Bill :</span></b>
												<input type="text" id="existBillAccNumC${status.index}" readonly="readonly" />
				 								</td>
												</tr> -->
											</table>
										</c:if>
									</div>	
									
									<div id="existingAcctDiv${status.index}">
										<c:if test="${not empty payMtdDtl.creditCardPrePayItem}">
											<table width="100%" border="0" cellspacing="1"
												class="table_style_sb" id="existingAcctTblC${status.index}">
												<tr>
													<th valign="top" width="5%"></th>
													<th valign="top" width="75%">
														<spring:message code="lts.acq.paymentMethod.prepaymentInformation" htmlEscape="false"/>
													</th>
													<th align="right" valign="top" width="20%">
														<spring:message code="lts.acq.paymentMethod.amount" htmlEscape="false"/>
													</th>
												</tr>
												<tr>
													<td valign="top"><form:checkbox
															path="paymentMethodDtlList[${status.index}].creditCardPrePayItem.selected"
															id="prepayItemSelectedC${status.index}"
															disabled="${payMtdDtl.creditCardPrePayItem.mdoInd == 'M'}" />
													</td>
													<td valign="top">
														${payMtdDtl.creditCardPrePayItem.itemDisplayHtml}</td>
													<td align="right" valign="top" class="itemPrice">
														${payMtdDtl.creditCardPrePayItem.oneTimeAmtTxt}</td>
												</tr>
											</table>
										</c:if>
										<c:if test="${not empty payMtdDtl.autopayPrePayItem}">
											<table width="100%" border="0" cellspacing="1"
												class="table_style_sb" id="existingAcctTblA${status.index}">
												<tr>
													<th valign="top" width="5%"></th>
													<th valign="top" width="75%">
														<spring:message code="lts.acq.paymentMethod.prepaymentInformation" htmlEscape="false"/>
													</th>
													<th align="right" valign="top" width="20%">
														<spring:message code="lts.acq.paymentMethod.amount" htmlEscape="false"/>
													</th>
												</tr>
												<tr>
													<td valign="top"><form:checkbox
															path="paymentMethodDtlList[${status.index}].autopayPrePayItem.selected"
															id="prepayItemSelectedA${status.index}"
															disabled="${payMtdDtl.autopayPrePayItem.mdoInd == 'M'}" />
													</td>
													<td valign="top">
														${payMtdDtl.autopayPrePayItem.itemDisplayHtml}</td>
													<td align="right" valign="top" class="itemPrice">
														${payMtdDtl.autopayPrePayItem.oneTimeAmtTxt}</td>
												</tr>
											</table>
										</c:if>
										<c:if test="${not empty payMtdDtl.cashPrePayItem}">
											<br>
											<table width="100%" border="0" cellspacing="1" class="table_style_sb" id="existingAcctTblM${status.index}">	
												<tr>
													<th valign="top"  width="5%"></th>
													<th valign="top" width="75%">
														<spring:message code="lts.acq.paymentMethod.prepaymentInformation" htmlEscape="false"/>
													</th>
													<th align="right" valign="top" width="20%">
														<spring:message code="lts.acq.paymentMethod.amount" htmlEscape="false"/>
													</th>
												</tr>
												<tr>
													<td valign="top">
													<form:checkbox path="paymentMethodDtlList[${status.index}].cashPrePayItem.selected" id="prepayItemSelectedM${status.index}" disabled="${payMtdDtl.cashPrePayItem.mdoInd == 'M'}"/>
												</td>
												<td valign="top" >
													${payMtdDtl.cashPrePayItem.itemDisplayHtml}
												</td>
												<td align="right" valign="top" class="itemPrice" >
													${payMtdDtl.cashPrePayItem.oneTimeAmtTxt}
												</td>
												</tr>
												<!-- <tr>
												<td >
													&nbsp;
												</td>
												<td align="left" colspan="2">
												<b><span>Customer’s existing Account No. of PCCW Bill :</span></b>
												<input type="text" id="existBillAccNumC${status.index}" readonly="readonly" />
				 								</td>
												</tr> -->
											</table>
										</c:if>
										<c:if test="${not empty payMtdDtl.iddDepositItem}">
											<br>
											<table width="100%" border="0" cellspacing="1" class="table_style_sb">	
												<tr>
												<th valign="top"  width="5%"></th>
												<th valign="top" width="75%"><spring:message code="lts.acq.paymentMethod.IDDDepositInformation" htmlEscape="false"/></th>
												<th align="right" valign="top" width="20%">
													<spring:message code="lts.acq.paymentMethod.amount" htmlEscape="false"/>
												</th>
												</tr>
												<tr>
												<td valign="top">
													<form:checkbox path="paymentMethodDtlList[${status.index}].iddDepositItem.selected" id="iddDepositItem${status.index}" disabled="${payMtdDtl.iddDepositItem.mdoInd == 'M'}"/>
												</td>
												<td valign="top" >
													${payMtdDtl.iddDepositItem.itemDisplayHtml}
												</td>
												<td align="right" valign="top" class="itemPrice" >
													${payMtdDtl.iddDepositItem.oneTimeAmtTxt}
												</td>
												</tr>
												<!--<tr>
												<td >
													&nbsp;
												</td>
												<td align="left" colspan="2">
												<b><span>Customer’s existing Account No. of PCCW Bill :</span></b>
												<input type="text" id="existBillAccNumC${status.index}" readonly="readonly" />
				 								</td>
												</tr> -->
											</table>
										</c:if>
									</div>
									<c:if test="${ltspaymentmethod.salesMemoNumRequired}">
										<div id="salesMemoNumDiv${status.index}" style="display:none;">
							            	<table width="100%" border="0" cellspacing="1" class="table_style_sb">	
											<tr>
											<td width="10%">
												&nbsp;
											</td>
								            <td width="90%" align="left" colspan="2">
												<b><span><spring:message code="lts.acq.paymentMethod.salesMemoNumber" htmlEscape="false"/> </span><span class="contenttext_red">*</span> :</b>
												<form:input path="paymentMethodDtlList[${status.index}].salesMemoNum" id="salesMemoNum${status.index}" maxlength="14"/>
												<form:errors path="paymentMethodDtlList[${status.index}].salesMemoNum" cssClass="error"/>
											</td>
											</tr>
											</table>
										</div>
									</c:if>
								
									
									</td>
								</tr>
						    	</table>
								</c:forEach>
						    </td>
						    </tr>
						</table></td></tr>
					</table>
<!--					<table width="100%" border="0" cellspacing="1" class="paper_w2 round_10">
						<tr><td>
							<div id="s_line_text">Suspend Remark</div>
						</td></tr>
						<tr><td>
						<table width="100%" cellspacing="1" border="0" class="contenttext" >
							<tr>  
								<td width="10%">
								&nbsp;
								</td>
								<td>
							    <form:textarea path="suspendRemarks" rows="10" cols="100%" cssStyle="resize:none;"></form:textarea>		
							    <form:errors path="suspendRemarks" cssClass="error"/>
							    </td>
						    </tr>
					    </table>	
					    </td>
					    </tr>	
				   </table>  -->
				</td>
			</tr>
		</table>
	
<table width="100%" border="0" cellspacing="0" class="paper_w2">
	<tr>
		<td align="right">
			<a id="submit" href="#" ><div class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a>
		</td>
	</tr>
</table>

<div id="tapAndGoPolicyDialog" title="Pre-payment policy changed" style="display:none">
  <p>
    Pre-payment policy was changed and follow cash policy
  </p>
</div>
</form:form>
<script language="javascript">
	var isRetail = "${sessionScope.bomsalesuser.channelId}" == "1";
	var isCc = "${sessionScope.bomsalesuser.channelId}" == "2";
	
/* 	var prePayItemEInd = "${prePayItemEInd}";
	var prePayItemAInd = "${prePayItemAInd}";
	var prePayItemCInd = "${prePayItemCInd}";
	var existPayMethod = "${existPayMethod}"; 
	var itemEMdo = "${prePayItemE.mdoInd}";
	var itemAMdo = "${prePayItemA.mdoInd}";
	var itemCMdo = "${prePayItemC.mdoInd}";
	var prepayment = "${prepayment}";*/
	var iddDeposit = "${iddDeposit}";
	var isCallCenter = "${sessionScope.sessionLtsAcqOrderCapture.channelCs}" == "true";
	var length = parseInt("${fn:length(ltspaymentmethod.paymentMethodDtlList)}");
	
	$("#submit").click(function(event) {
		event.preventDefault();
		$("#submitInd").val("SUBMIT");
		for(var index = 0; index < length; index++){
			if($("#payMethodTypeM"+index).is(':checked')){
				$("#prepayItemSelectedM"+index).removeAttr('disabled');
				if($("#acctTypeI"+index).val() == 'true'){
					if(iddDeposit){
						alert('<spring:message code="lts.acq.paymentMethod.IDDDepositRequired" htmlEscape="false"/>');
					}
				}
				if($("#prepayItemSelectedM"+index).is(':checked')){
					alert('<spring:message code="lts.acq.paymentMethod.prepaymentRequired" htmlEscape="false"/>');
				}
			}else if($("#payMethodTypeA"+index).is(':checked')){
				$("#prepayItemSelectedA+index").removeAttr('disabled');
				if($("#prepayItemSelectedA"+index).is(':checked')){
					alert('<spring:message code="lts.acq.paymentMethod.prepaymentRequired" htmlEscape="false"/>');
				}
			}else if($("#payMethodTypeC"+index).is(':checked')){
				$("#prepayItemSelectedC"+index).removeAttr('disabled');
				if($("#prepayItemSelectedC"+index).is(':checked')){
					alert('<spring:message code="lts.acq.paymentMethod.prepaymentRequired" htmlEscape="false"/>');
				}
			}
		}
		
		$("[id^=payMethodTypeA]").removeAttr('disabled');
		$("[id^=payMethodTypeC]").removeAttr('disabled');
		$("[id^=bankAccHolderDocType]").removeAttr('disabled');
		$("[id^=cardHolderDocType]").removeAttr('disabled');
		$("[id^=cardtype]").removeAttr('disabled');
		$("#paymentmethodform").submit();
	});

</script>
<c:forEach items="${ltspaymentmethod.paymentMethodDtlList}"
	var="payMtdDtl2" varStatus="status">
<script>
	paymentmethodform.actionPerform("${status.index}"); 
	$("#existingAcctTblC${status.index}").hide();
	$("#existingAcctTblA${status.index}").hide();
	$("#existingAcctTblM${status.index}").show();
	<c:if test="${payMtdDtl2.existingPayMethodType == 'C' && payMtdDtl2.cardNum != '5599'}">
	    $("#existingAcctTblC${status.index}").show();
		$("#existingAcctTblA${status.index}").hide();
		$("#existingAcctTblM${status.index}").hide();
    </c:if>
	<c:if test="${payMtdDtl2.existingPayMethodType == 'C' && payMtdDtl2.cardNum == '5599'}">
	    $("#existingAcctTblC${status.index}").hide();
		$("#existingAcctTblA${status.index}").hide();
		$("#existingAcctTblM${status.index}").show();
    </c:if>
	<c:if test="${payMtdDtl2.existingPayMethodType == 'A'}">
		$("#existingAcctTblC${status.index}").hide();
		$("#existingAcctTblA${status.index}").show();
		$("#existingAcctTblM${status.index}").hide();
	</c:if>
</script>
</c:forEach>

 <%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>