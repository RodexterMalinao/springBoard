<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="6" />
</jsp:include>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<c:set var="isOrderSubmitted"
	value="${sessionScope.sessionLtsAcqOrderCapture.sbOrder != null}" />

<form:form method="POST" action="#" id="billmediabilllangform"	name="ltsBillMediaBillLangForm" commandName="ltsbillmediabilllang" autocomplete="off">
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<input type="hidden" id="errorMsg" />
	<form:hidden id="dummyEmailMobInd" path="dummyEmailMobInd" />
	<form:hidden id="carecashExistOpt" path="carecashExistOpt" />
	<form:hidden id="showIguard" path="showIguard" />
	<form:hidden id="oIdStatus" path="oIdStatus" />
	
	<table width="100%" class="paper_w2 round_10">
		<tr>
			<td>
				<div id="s_line_text"><spring:message code="lts.acq.billingSelection.billingSelection" htmlEscape="false"/></div>
			</td>
		</tr>
		<tr>
			<td>
				<c:if test="${not empty ltsbillmediabilllang.billMediaDtlList}">
					<c:forEach items="${ltsbillmediabilllang.billMediaDtlList}" var="billMedia" varStatus="status">
						<table width="100%" border="0" cellspacing="1" class="paper_w2 round_10">
							<tr>
							<td>
							<input type="hidden" id="contactEmail" value="${billMedia.contactEmail}" />
							<input type="hidden" id="existingCspEmail" value="${billMedia.existingCspEmail}" />
							<input type="hidden" id="contactMobileNo" value="${billMedia.contactMobileNo}" />
							<input type="hidden" id="contactFixedLineNo" value="${billMedia.contactFixedLineNo}" />
							<input type="hidden" id="sysGenEmail" value="${billMedia.sysGenEmail}" />
							<form:hidden id="dummyEmailInd${status.index}" path="billMediaDtlList[${status.index}].dummyEmailInd"/>
							<form:hidden id="dummyMobileInd${status.index}" path="billMediaDtlList[${status.index}].dummyMobileInd" />
							<table width="100%" border="0" cellspacing="1" >
							<tr>            
				               <td><span style="color: DarkBlue;"><b>&nbsp;<spring:message code="lts.acq.billingSelection.accountNumber" htmlEscape="false"/> </b> ${billMedia.acctNum}</span></td>
				               <td>&nbsp;</td>
							</tr>
						    <tr>
								<td> <hr></td>
						    </tr>
						    <tr><td></td><td></td></tr>
						    <tr><td></td><td></td></tr>
							<tr>
					            <td align="left"><b><spring:message code="lts.acq.billingSelection.billMedia" htmlEscape="false"/></b></td>
					            <td></td>
							</tr>
						    <tr>
								<td>
								<table width="100%" border="0" cellspacing="1" class="contenttext">	
								<tr>
									<td valign="top"  width="10"></td>
									<td valign="top" width="75%"></td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										<spring:message code="lts.acq.billingSelection.amount" htmlEscape="false"/>
									</td>
								</tr>
								
								<tr >
									<td valign="top"  width="10"><br>
									</td>
									<td valign="top" width="75%">
									<form:errors path="billMediaDtlList[${status.index}].emailBillItem" cssClass="error" />
									</td>
								</tr>
							
								<tr >									
									<td valign="top"  width="10">
										<form:radiobutton cssClass="billListRadio${status.index}" id="paperBillItem${status.index}" path="billMediaDtlList[${status.index}].selectedBillItem" value="${billMedia.paperBillItem.itemId}" disabled="${billMedia.profileBillMedia == 'S'}"/>											 
									</td>
									<td valign="top" width="75%">
										${billMedia.paperBillItem.itemDisplayHtml}
									</td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										${billMedia.paperBillItem.mthToMthAmt}
									</td>
								</tr>
								<tr >									
									<td valign="top"  width="10">
										
									</td>
									<td valign="top" width="75%">
									<div style="display: inline-block; vertical-align:top">
										<spring:message code="lts.acq.billingSelection.paperBillCharging" htmlEscape="false"/> 
										<form:hidden id="paperBillCharging${status.index}" path="billMediaDtlList[${status.index}].selectedBillCharging" />
										<select id="paperBillChargingSel${status.index}" disabled="${not empty billMedia.custExistingPaperBillInd}">
										    <option value=""><spring:message code="lts.acq.billingSelection.selectInSelector" htmlEscape="false"/></option>
										    <option value="Y"><spring:message code="lts.acq.billingSelection.charge" htmlEscape="false"/></option>
										    <option value="W"><spring:message code="lts.acq.billingSelection.waive" htmlEscape="false"/></option>
									    </select> <spring:message code="lts.acq.billingSelection.waiveReason" htmlEscape="false"/> 									    
									    <form:select id="waiveReason${status.index}" disabled="${billMedia.selectedBillCharging != 'W' || not empty billMedia.custExistingPaperBillInd}" path="billMediaDtlList[${status.index}].selectedWaiveReason" >
										    <form:option value=""><spring:message code="lts.acq.billingSelection.selectInSelector" htmlEscape="false"/></form:option>
										    <form:options items="${ltsPaperWaiveLkupList}" itemValue="itemKey" itemLabel="itemValue" />
									    </form:select>
									</div>
									<div style="display: inline-block;">
									    <form:input id="paperBillWaiveOtherStaffId${status.index}" path="billMediaDtlList[${status.index}].paperBillWaiveOtherStaffId" maxlength="10"/>
									    <br><span style="font-size:10px"><spring:message code="lts.acq.billingSelection.inputUMorAboveStaffIDForApproval" htmlEscape="false"/> </span>
									</div>
									    <br/>
									    <form:errors path="billMediaDtlList[${status.index}].selectedBillCharging" cssClass="error" />
									    <form:errors path="billMediaDtlList[${status.index}].selectedWaiveReason" cssClass="error" />
									    <form:errors path="billMediaDtlList[${status.index}].paperBillWaiveOtherStaffId" cssClass="error" />							        

									</td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										
									</td>
								</tr>
								<tr >
									<td valign="top"  width="10">
										<form:radiobutton cssClass="billListRadio${status.index}" id="emailBillItem${status.index}" path="billMediaDtlList[${status.index}].selectedBillItem" value="${billMedia.emailBillItem.itemId}" disabled="${billMedia.profileBillMedia == 'S'}"/> 
									</td>
									<td valign="top" width="75%">
										${billMedia.emailBillItem.itemDisplayHtml} 
									   	<form:input cssClass="emailBillAttb${status.index}" path="billMediaDtlList[${status.index}].billMediaEmail" cssStyle="width:200px" id="billMediaEmail{status.index}" readonly="${billMedia.profileBillMedia == 'S'}"/>
									    <form:errors path="billMediaDtlList[${status.index}].billMediaEmail" cssClass="error" />
									</td>										
									<td align="right" valign="top" class="BGgreen2" width="20%">
										${billMedia.emailBillItem.mthToMthAmt}											
									</td>										
								</tr>
								</table>
								</td>
							</tr>
							
							<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						    <c:if test="${not empty billMedia.csPortalItem && status.index==0 && !isBrCust && isDocValidCsp}">
							<tr>
					            <td align="left"><b><spring:message code="lts.acq.billingSelection.theClubMyHKT" htmlEscape="false"/></b></td>
					            <td>&nbsp;</td>
							</tr>
							<tr>
					            <td><c:if test="${!is6Digits}"><font color="red"><spring:message code="lts.acq.billingSelection.documentNoLessThan6Digits" htmlEscape="false"/></font></c:if></td>
					            <td>&nbsp;</td>
							</tr>
							</c:if>
						    <tr>
								<td>	
								<table width="100%" border="0" cellspacing="1" class="contenttext">	
								<tr>
									<td valign="top"  width="10">&nbsp;</td>
									<td valign="top" width="75%">&nbsp;</td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										&nbsp;
									</td>
								</tr>	
									
								<c:if test="${not empty billMedia.csPortalItem && status.index==0 && !isBrCust && isDocValidCsp}">
									<tr>
										<td valign="top"  width="10">
										<form:checkbox id="cspCheckbox${status.index}" path="billMediaDtlList[${status.index}].csPortalItem.selected" disabled="${billMedia.csPortalExist || billMedia.csPortalItem.mdoInd == 'M' || !isRegHktOnly || !is6Digits || isDelFree}"/>
										</td>
										<td valign="top" width="75%">
											${billMedia.csPortalItem.itemDisplayHtml}
											<c:if test="${!billMedia.csPortalExist && isRegClubOnly}">
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.theClubEmailAddress" htmlEscape="false"/>  </span>
													<form:input cssClass="cspAttb${status.index}" path="billMediaDtlList[${status.index}].clubEmail" id="clubEmail${status.index}" disabled="true"/>
												    <c:if test="${is6Digits}">
												    <div class="func_button">
													    <c:if test="${isEmailExist}">
							                                <a id="copyClubEmailAddrBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.copy" htmlEscape="false"/></a>
							                                <a id="genClubEmailAddrBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.noEmail" htmlEscape="false"/></a>
							                                <a id="clearClubEmailAddrBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.clear" htmlEscape="false"/></a>
						                                </c:if>
						                                <c:if test="${!isEmailExist}">
							                                <a id="genClubEmailAddrBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.noEmail" htmlEscape="false"/></a>
							                                <a id="clearClubEmailAddrBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.clear" htmlEscape="false"/></a>
						                                </c:if>
					                                </div>
					                                </c:if>
					                                <c:if test="${isPhantomAcc}">
					                                <font color="red"><spring:message code="lts.acq.billingSelection.phantomAccountExist" htmlEscape="false"/></font>
					                                </c:if>
					                                <form:errors path="billMediaDtlList[${status.index}].clubEmail" cssClass="error" />
												</div>
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.theClubMobileNumber" htmlEscape="false"/>	</span>
													<form:input cssClass="cspAttb${status.index}" path="billMediaDtlList[${status.index}].clubMobile" id="clubMobile${status.index}" disabled="true"/>												    
												    <c:if test="${is6Digits}">
												    <div class="func_button">
												        <c:if test="${isMobNoExist}">
							                                <a id="copyClubMobileNoBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.copy" htmlEscape="false"/></a>
						                                </c:if>
						                                <c:if test="${!isMobNoExist}">
							                                <a id="genClubMobileNoBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.noMobile" htmlEscape="false"/></a>
							                                <a id="clearClubMobileNoBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.clear" htmlEscape="false"/></a>
						                                </c:if>
					                                </div>
					                                </c:if>
					                                <form:errors path="billMediaDtlList[${status.index}].clubMobile" cssClass="error" />
												</div>
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.myHKTEmailAddress" htmlEscape="false"/>  </span>
													<form:input path="billMediaDtlList[${status.index}].cspEmail" disabled="true"/>
												</div>
											</c:if>
											
											<c:if test="${!billMedia.csPortalExist && isRegHktOnly}">
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.myHKTEmailAddress" htmlEscape="false"/>  </span>
													<form:input cssClass="cspAttb${status.index}" path="billMediaDtlList[${status.index}].cspEmail" id="cspEmail${status.index}" disabled="${!is6Digits}"/>
												    <c:if test="${is6Digits}">
												    <div class="func_button">
												        <c:if test="${isEmailExist}">
							                                <a id="copyCspEmailAddrBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.copy" htmlEscape="false"/></a>
							                                <a id="genCspEmailAddrBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.noEmail" htmlEscape="false"/></a>
							                                <a id="clearCspEmailAddrBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.clear" htmlEscape="false"/></a>
						                                </c:if>
						                                <c:if test="${!isEmailExist}">
							                                <a id="genCspEmailAddrBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.noEmail" htmlEscape="false"/></a>
							                                <a id="clearCspEmailAddrBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.clear" htmlEscape="false"/></a>
						                                </c:if>
					                                </div>
					                                </c:if>
					                                <c:if test="${isPhantomAcc}">
					                                <font color="red"><spring:message code="lts.acq.billingSelection.phantomAccountExist" htmlEscape="false"/></font>
					                                </c:if>
					                                <form:errors path="billMediaDtlList[${status.index}].cspEmail" cssClass="error" />
												</div>
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.myHKTMobileNumber" htmlEscape="false"/>  </span>
													<form:input cssClass="cspAttb${status.index}" path="billMediaDtlList[${status.index}].cspMobile" id="cspMobile${status.index}" disabled="${!is6Digits}"/>												    
												    <c:if test="${is6Digits}">
												    <div class="func_button">
												        <c:if test="${isMobNoExist}">
							                                <a id="copyCspMobileNoBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.copy" htmlEscape="false"/></a>
						                                </c:if>
						                                <c:if test="${!isMobNoExist}">
							                                <a id="genCspMobileNoBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.noMobile" htmlEscape="false"/></a>
							                                <a id="clearCspMobileNoBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.clear" htmlEscape="false"/></a>
						                                </c:if>
					                                </div>
					                                </c:if>
					                                <form:errors path="billMediaDtlList[${status.index}].cspMobile" cssClass="error" />
												</div>
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px">The Club Email Address  </span>
													<form:input path="billMediaDtlList[${status.index}].clubEmail" disabled="true"/>
												</div>
											</c:if>
											
											<c:if test="${!billMedia.csPortalExist && isRegHktAndClub}">
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.theClubMyHKTEmailAddress" htmlEscape="false"/>  </span>
													<form:input cssClass="cspAttb${status.index}" path="billMediaDtlList[${status.index}].cspEmail" id="cspEmail${status.index}" disabled="${!is6Digits}"/>
												    <c:if test="${is6Digits}">
												    <div class="func_button">
						                                <c:if test="${isEmailExist}">
							                                <a id="copyCspEmailAddrBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.copy" htmlEscape="false"/></a>
							                                <a id="genCspEmailAddrBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.noEmail" htmlEscape="false"/></a>
						                                    <a id="clearCspEmailAddrBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.clear" htmlEscape="false"/></a>
						                                </c:if>
						                                <c:if test="${!isEmailExist}">
							                                <a id="genCspEmailAddrBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.noEmail" htmlEscape="false"/></a>
						                                    <a id="clearCspEmailAddrBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.clear" htmlEscape="false"/></a>
						                                </c:if>
					                                </div>
					                                </c:if>
					                                <c:if test="${isPhantomAcc}">
					                                <font color="red"><spring:message code="lts.acq.billingSelection.phantomAccountExist" htmlEscape="false"/></font>
					                                </c:if>
					                                <form:errors path="billMediaDtlList[${status.index}].cspEmail" cssClass="error" />
												</div>
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.theClubMyHKTMobileNumber" htmlEscape="false"/>  </span>
													<form:input cssClass="cspAttb${status.index}" path="billMediaDtlList[${status.index}].cspMobile" id="cspMobile${status.index}" disabled="${!is6Digits}"/>												    
												    <c:if test="${is6Digits}">
												    <div class="func_button">
												        <c:if test="${isMobNoExist}">
							                                <a id="copyCspMobileNoBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.copy" htmlEscape="false"/></a>
						                                </c:if>
						                                <c:if test="${!isMobNoExist}">
							                                <a id="genCspMobileNoBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.noMobile" htmlEscape="false"/></a>
							                                <a id="clearCspMobileNoBtn${status.index}" href="#" hidden="true"><spring:message code="lts.acq.billingSelection.clear" htmlEscape="false"/></a>
						                                </c:if>
					                                </div>
					                                </c:if>
					                                <form:errors path="billMediaDtlList[${status.index}].cspMobile" cssClass="error" />
												</div>
											</c:if>
											
											<c:if test="${billMedia.csPortalExist}">
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.myHKTEmailAddress" htmlEscape="false"/>  </span>
													<form:input path="billMediaDtlList[${status.index}].cspEmail" disabled="true"/>
												</div>
												<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px">The Club Email Address  </span>
													<form:input path="billMediaDtlList[${status.index}].clubEmail" disabled="true"/>												    
												</div>
											</c:if>
											
										</td>
										<td align="right" valign="top" class="BGgreen2" width="20%">
											${billMedia.csPortalItem.mthToMthAmt}
										</td>
										
									</tr>
									
									<tr>
									<td valign="top"  width="10">&nbsp;</td>
									<td valign="top" width="75%">&nbsp;</td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										&nbsp;
									</td>
								    </tr>
								    <tr>
								    <c:if test="${!billMedia.csPortalExist}">
									<tr>
									<td valign="top"  width="10">&nbsp;</td>
									<td valign="top" width="75%"><b><spring:message code="lts.acq.billingSelection.personalInfoCollectionStatement" htmlEscape="false"/></b></td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										&nbsp;
									</td>
								    </tr>
								    </c:if>
								    <tr>
									<td valign="top"  width="10">&nbsp;</td>	
								    <td valign="top" width="75%">
								    <table width="100%" border="0" cellspacing="1" class="paper_w2 round_10">
								    <tr>
									<td valign="top" width="25%">&nbsp;</td>
									<td valign="top" width="25%">&nbsp;</td>
									<td valign="top" width="25%">&nbsp;</td>
									<td valign="top" width="25%">&nbsp;</td>
								    </tr>
								    <c:if test="${!billMedia.csPortalExist}">
									<tr>
										<td>
										<form:radiobutton id="oia${status.index}" path="billMediaDtlList[${status.index}].optInOutInd" value="OIA" disabled="${!is6Digits}"/><spring:message code="lts.acq.billingSelection.optInAll" htmlEscape="false"/>
										</td>
										<td>
										<form:radiobutton id="ooa${status.index}" path="billMediaDtlList[${status.index}].optInOutInd" value="OOA" disabled="${!is6Digits}"/><spring:message code="lts.acq.billingSelection.optOutAll" htmlEscape="false"/>
									    </td>
									    <td valign="top" width="25%">&nbsp;</td>
									    <td valign="top" width="25%">&nbsp;</td>
									</tr>
									
									<tr>
										<td valign="top" width="25%">&nbsp;</td>
										<td valign="top" width="25%">&nbsp;</td>
										<td valign="top" width="25%">&nbsp;</td>
										<td valign="top" width="25%">&nbsp;</td>
								    </tr>
								    </c:if>
								    <c:if test="${!billMedia.csPortalExist && !isRegHktOnly}">
										<tr>
											<td>
											<spring:message code="lts.acq.billingSelection.theClubOptOutReason" htmlEscape="false"/>
											</td>
											<td>
											<form:select path="billMediaDtlList[${status.index}].optOutReason" id="optOutReason${status.index}" disabled="${!is6Digits}">
										    <form:option value=""><spring:message code="lts.acq.billingSelection.selectInSelector" htmlEscape="false"/></form:option>
										    <form:options items="${optOutReasonList}" itemValue="itemKey" itemLabel="itemValue" />
									        </form:select>
											</td>
											<td valign="top" width="25%"><form:errors path="billMediaDtlList[${status.index}].optOutReason" cssClass="error"/></td>
											<td valign="top" width="25%">&nbsp;</td>
										</tr>
										<tr>
											<td valign="top" width="25%">&nbsp;</td>
											<td valign="top" width="25%">&nbsp;</td>
											<td valign="top" width="25%">&nbsp;</td>
											<td valign="top" width="25%">&nbsp;</td>
									    </tr>
										<tr>
											<td valign="top" width="25%">
											<spring:message code="lts.acq.billingSelection.theClubOptOutRemarks" htmlEscape="false"/>
											</td>
											<td>
											<form:textarea path="billMediaDtlList[${status.index}].optOutRemarks" rows="6" cols="50" cssStyle="resize:none;" id="optOutRemarks${status.index}" disabled="${!is6Digits}"></form:textarea>
										    </td>
										    <td>
										    <form:errors path="billMediaDtlList[${status.index}].optOutRemarks" cssClass="error"/>
											</td>
											<td valign="top" width="25%">&nbsp;</td>
										</tr>
									</c:if>
									</table>
									</td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										&nbsp;
									</td>
								    </tr>

									
								</c:if>
								
								</table>
								</td>
							</tr>
							<tr><td><br><br></td><td></td></tr>
							</table></td>
							</tr>
						<tr><td></td></tr>
						
						<c:if test="${not empty showIguard && showIguard && status.index==0}"><tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
							
						<tr>
							<td align="left"><b><spring:message code="lts.acq.billingSelection.iGUARDCARECash" htmlEscape="false"/></b><br/>
								<span class="error" id="carecashRegErr" style="display:none;">MyHKT registration is necessary for i-GUARD CARECash registration.</span>
							</td>
							<td>&nbsp;</td>
						</tr>
												
						<tr>
							<td>	
								<table width="100%" border="0" cellspacing="1" class="contenttext">	
								<tr>
									<td valign="top"  width="10">&nbsp;</td>
									<td valign="top" width="75%">&nbsp;</td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										&nbsp;
									</td>
								</tr>
								
								<tr>
									<td valign="top"  width="10">&nbsp;</td>
									<td valign="top" width="75%">
									<form:radiobutton id="carecashRegisterOptionR${status.index}" path="billMediaDtlList[${status.index}].carecashRegisterOption" value="R" cssClass="carecashRegRadio${status.index}"/><spring:message code="lts.acq.billingSelection.confirmToApplyCARECash" htmlEscape="false"/>&nbsp;&nbsp;&nbsp;
									<form:radiobutton id="carecashRegisterOptionN${status.index}" path="billMediaDtlList[${status.index}].carecashRegisterOption" value="N" cssClass="carecashRegRadio${status.index}"/><spring:message code="lts.acq.billingSelection.decline" htmlEscape="false"/>&nbsp;&nbsp;&nbsp;
									<form:radiobutton id="carecashRegisterOptionT${status.index}" path="billMediaDtlList[${status.index}].carecashRegisterOption" value="T" cssClass="carecashRegRadio${status.index}"/><spring:message code="lts.acq.billingSelection.toBeDecided" htmlEscape="false"/>
									</td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										&nbsp;
									</td>
								</tr>
								
								<tr>
									<td valign="top"  width="10">
									</td>
									<td valign="top" width="75%">
										<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.iGUARDCARECashEmailAddress" htmlEscape="false"/>  </span>
											<form:input cssClass="" path="billMediaDtlList[${status.index}].carecashEmail" id="carecashEmail${status.index}" maxlength="64" />
											<div class="func_button">
												<a id="copyCarecashEmailAddrBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.copy" htmlEscape="false"/></a>
											</div>
											<form:errors path="billMediaDtlList[${status.index}].carecashEmail" cssClass="error" />
										</div>
										<div style="padding-left:15px ;padding:5px"><span style="display:inline-block; min-width:110px"><spring:message code="lts.acq.billingSelection.iGUARDCARECashContactNumber" htmlEscape="false"/>  </span>
											<form:input cssClass="" path="billMediaDtlList[${status.index}].carecashContactNo" id="carecashContactNo${status.index}" maxlength="8" />
											<div class="func_button">
												<a id="copyCarecashMobileNoBtn${status.index}" href="#"><spring:message code="lts.acq.billingSelection.copy" htmlEscape="false"/></a>
											</div>
											<form:errors path="billMediaDtlList[${status.index}].carecashContactNo" cssClass="error" />
										</div>
									</td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
									
									</td>
									
								</tr>
														
								<tr>
									<td valign="top"  width="10">&nbsp;</td>
									<td valign="top" width="75%">&nbsp;</td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										&nbsp;
									</td>
								</tr>
								
								<tr>
									<td valign="top"  width="10">&nbsp;</td>
									<td valign="top" width="75%"><b><spring:message code="lts.acq.billingSelection.personalInfoCollectionStatement" htmlEscape="false"/></b></td>
									<td align="right" valign="top" class="BGgreen2" width="20%">
										&nbsp;
									</td>
								</tr>
									
								<tr>
									<td valign="top"  width="10">&nbsp;</td>	
									<td valign="top" width="75%">
										<table width="100%" border="0" cellspacing="1" class="paper_w2 round_10">
											<tr>
												<td valign="top" width="25%">&nbsp;</td>
												<td valign="top" width="25%">&nbsp;</td>
												<td valign="top" width="25%">&nbsp;</td>
												<td valign="top" width="25%">&nbsp;</td>
											</tr>
											<tr>
												<td>
												<form:radiobutton id="carecashOptInOutIndI${status.index}" path="billMediaDtlList[${status.index}].carecashOptInOutInd" value="I" cssClass="carecashOptRadio${status.index}"/> <spring:message code="lts.acq.billingSelection.optInAll" htmlEscape="false"/>
												</td>
												<td>
												<form:radiobutton id="carecashOptInOutIndO${status.index}" path="billMediaDtlList[${status.index}].carecashOptInOutInd" value="O" cssClass="carecashOptRadio${status.index}"/> <spring:message code="lts.acq.billingSelection.optOutAll" htmlEscape="false"/>
												</td>
												<td valign="top" width="25%">&nbsp;</td>
												<td valign="top" width="25%">&nbsp;</td>
											</tr>
											
											<tr>
												<td valign="top" width="25%">&nbsp;</td>
												<td valign="top" width="25%">&nbsp;</td>
												<td valign="top" width="25%">&nbsp;</td>
												<td valign="top" width="25%">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
								
								<tr><td><br><br></td><td></td></tr>
								</table>
							</td>
						</tr>
						<tr><td></td></tr>
						</c:if>
						
		                 <tr><td align="left"><b><spring:message code="lts.acq.billingSelection.billLanguage" htmlEscape="false"/></b></td> <td></td></tr>
		                 <tr><td align="left">
			                  <form:radiobutton id="billLang1" path="billMediaDtlList[${status.index}].billLang" value="C"  /><spring:message code="lts.acq.billingSelection.traditionalChinese" htmlEscape="false"/>
							  <form:radiobutton id="billLang2" path="billMediaDtlList[${status.index}].billLang" value="E"  /><spring:message code="lts.acq.billingSelection.english" htmlEscape="false"/>
		                      <form:errors path="billMediaDtlList[${status.index}].billLang" cssClass="error" />
		                 </td></tr>
		                 <tr><td><br><br></td><td></td></tr>
		                 <c:if test="${not empty sessionScope.sessionLtsAcqOrderCapture.ltsPremiumSelectionForm.premiumSetList}">
		                 <tr><td align="left"><b><spring:message code="lts.acq.billingSelection.redemptionMedia" htmlEscape="false"/></b></td> <td></td></tr>
		                 <tr><td align="left">
		                 <c:if test="${ltsbillmediabilllang.allowRedemPaper}">
		                 <div style="width: 100px; float: left;">
		                 	 <form:radiobutton id="redemPaper${status.index}" path="billMediaDtlList[${status.index}].redemMedia" value="P"  /><spring:message code="lts.acq.billingSelection.paper" htmlEscape="false"/>
		                 </div>
		                 </c:if>
		                 <div style="width:300px; float: left; display:none;">
		                 <div width="200px">
		                     <form:radiobutton id="redemEmail${status.index}" path="billMediaDtlList[${status.index}].redemMedia" value="S" disabled="true"/><spring:message code="lts.acq.billingSelection.email" htmlEscape="false"/>
			                 <form:input path="billMediaDtlList[${status.index}].redemMediaEmail" id="redemMediaEmail" cssStyle="width:200px" disabled="true"/>
			             </div>
			             <div>
			                 <form:errors path="billMediaDtlList[${status.index}].redemMediaEmail" cssClass="error" />
			           	 </div>
			         	<div width="200px">
			                 <spring:message code="lts.acq.billingSelection.mustSameEmailBill" htmlEscape="false"/>
		                </div>
		                </div>
		                 <div style="width: 350px; float: left;">
		                     <form:radiobutton id="redemSms${status.index}" path="billMediaDtlList[${status.index}].redemMedia" value="M"/><spring:message code="lts.acq.billingSelection.SMSMobileNumber" htmlEscape="false"/>
			                 <form:input path="billMediaDtlList[${status.index}].redemSms" id="redemSms"/>
			                 <form:errors path="billMediaDtlList[${status.index}].redemSms" cssClass="error" />
		                 </div>
		                 <div>
			                 <form:errors path="billMediaDtlList[${status.index}].redemMedia" cssClass="error" />
			           	 </div>
		                 </td></tr>
		                 </c:if>
		                 
		              </table>
		              <br>
		              <br>
					</c:forEach>
				</c:if>
				</td></tr>
</table> 
<br>
<table width="100%" border="0" cellspacing="0" class="paper_w2 round_10">
<tr>
	<td align="right"><br /> <a id="submit" href="#"><div class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a></td>
</tr>
</table> 
</td></tr></table></form:form>

<c:forEach items="${ltsbillmediabilllang.billMediaDtlList}" var="billMedia" varStatus="status">

<script language="javascript">
	
	$('#carecashExistOpt').val('${carecashExistOpt}');
	$('#showIguard').val('${showIguard}');
	$('#oIdStatus').val('${oIdStatus}');
	
	if('${ltsbillmediabilllang.billMediaDtlList[status.index].carecashRegisterOption}'=='' || '${ltsbillmediabilllang.billMediaDtlList[status.index].carecashRegisterOption}'== null)
	{
		$('#carecashRegisterOptionR${status.index}').attr("checked","checked");
		$('#carecashOptInOutIndI${status.index}').attr("checked","checked");
	}
	

    checkCsp();
    checkBillMediaEmail();
    checkPaperWaiveReaCd();
    $('#paperBillChargingSel${status.index}').val($('#paperBillCharging${status.index}').val());
    
    if($('#oia${status.index}').is(":checked")){
    	$('#optOutReason${status.index}').attr("disabled", "disabled");
        $('#optOutRemarks${status.index}').attr("disabled", "disabled");
    }else{
    	$('#optOutReason${status.index}').removeAttr("disabled");
        $('#optOutRemarks${status.index}').removeAttr("disabled");
    }
    
    if($('#cspEmail${status.index}').val() != ''){
    	if ($('#dummyEmailInd${status.index}').val() == 'Y'){
    		$('#genCspEmailAddrBtn${status.index}').hide();
			$('#clearCspEmailAddrBtn${status.index}').show();
			$('input[name="billMediaDtlList[${status.index}].cspEmail"]').attr("readonly", "readonly");
    	}else{
			$('input[name="billMediaDtlList[${status.index}].cspEmail"]').removeAttr("readonly");
    	}
	}
    
    if($('#cspMobile${status.index}').val() != ''){
    	if ($('#dummyMobileInd${status.index}').val() == 'Y'){
    		$('#genCspMobileNoBtn${status.index}').hide();
			$('#clearCspMobileNoBtn${status.index}').show();
			$('input[name="billMediaDtlList[${status.index}].cspMobile"]').attr("readonly", "readonly");
    	}else{
			$('input[name="billMediaDtlList[${status.index}].cspMobile"]').removeAttr("readonly");
    	}
	}
    
    if($('#clubEmail${status.index}').val() != ''){
    	if ($('#dummyEmailInd${status.index}').val() == 'Y'){
    		$('#genClubEmailAddrBtn${status.index}').hide();
			$('#clearClubEmailAddrBtn${status.index}').show();
			$('input[name="billMediaDtlList[${status.index}].clubEmail"]').attr("readonly", "readonly");
    	}else{
			$('input[name="billMediaDtlList[${status.index}].clubEmail"]').removeAttr("readonly");
    	}
	}
    
    if($('#clubMobile${status.index}').val() != ''){
    	if ($('#dummyMobileInd${status.index}').val() == 'Y'){
    		$('#genClubMobileNoBtn${status.index}').hide();
			$('#clearClubMobileNoBtn${status.index}').show();
			$('input[name="billMediaDtlList[${status.index}].clubMobile"]').attr("readonly", "readonly");
    	}else{
			$('input[name="billMediaDtlList[${status.index}].clubMobile"]').removeAttr("readonly");
    	}
	}
       
    if($('#oIdStatus').val() == 'RC_NO_CUSTOMER' ){
    	var isNoCspMob = false;
    	var isNoCspEmail = false;
    	if($('#cspMobile${status.index}').val() == "" || $('#dummyEmailMobInd').val() == 'Y' || $('#dummyMobileInd${status.index}').val() == 'Y')
   		{
    		isNoCspMob = true;
   		}
    	if($('#cspEmail${status.index}').val() == "" || $('#dummyEmailMobInd').val() == 'Y' || $('#dummyEmailInd${status.index}').val() == 'Y')
   		{
    		isNoCspEmail = true;
   		}
    	
    	if(isNoCspMob || isNoCspEmail)
   		{
    		dimCarecash();
   		}    		
    }
    
    function dimCarecash() {
    	$('#carecashRegErr').show();
    	$('.carecashRegRadio${status.index}').attr("disabled", "disabled");
    	$('.carecashOptRadio${status.index}').attr("disabled", "disabled");
    	$('#carecashEmail${status.index}').attr("disabled", "disabled");
    	$('#carecashContactNo${status.index}').attr("disabled", "disabled");
    	$('.carecashRegRadio${status.index}').removeAttr('checked');
    	$('.carecashOptRadio${status.index}').removeAttr('checked');
    }
    
	function brightCarecash() {
		$('#carecashRegErr').hide();
    	$('.carecashRegRadio${status.index}').removeAttr("disabled");
    	$('.carecashOptRadio${status.index}').removeAttr("disabled");
    	$('#carecashEmail${status.index}').removeAttr("disabled");
    	$('#carecashContactNo${status.index}').removeAttr("disabled");
    	if(!$('#carecashRegisterOptionR${status.index}').attr("checked") && !$('#carecashRegisterOptionN${status.index}').attr("checked") && !$('#carecashRegisterOptionT${status.index}').attr("checked"))
    	{
    		$('#carecashRegisterOptionR${status.index}').attr("checked","checked");
    		$('#carecashOptInOutIndI${status.index}').attr("checked","checked");
    	}
    }
	
	$("#cspEmail${status.index}").change(function(){
		if( $('#cspMobile${status.index}').val() != "" && $('#dummyEmailMobInd').val() != 'Y' && $('#dummyMobileInd${status.index}').val() != 'Y' )
		{
			brightCarecash();
		}		
    });
		
	$("#cspMobile${status.index}").change(function(){
		if( $('#cspEmail${status.index}').val() != "" && $('#dummyEmailMobInd').val() != 'Y' && $('#dummyEmailInd${status.index}').val() != 'Y' )
		{
			brightCarecash();
		}		
    });
	

    

    function checkCsp() {
    	if($("#cspCheckbox${status.index}").is(":checked")){
			//$(".billListRadio${status.index}").removeAttr("checked");
			$(".cspAttb${status.index}").removeAttr("disabled");
		}else{
			$(".cspAttb${status.index}").val('');
			$(".cspAttb${status.index}").attr("disabled", "disabled");
		}
	    if($("#hiddenSelectBillItem${status.index}").val() != ''){
			$(".billListRadio${status.index}[value=" + $("#hiddenSelectBillItem${status.index}").val() +"]").attr("checked", true);
	    }
	    
    } 
    
    function checkPaperWaiveReaCd(){
    	if($("#waiveReason${status.index}").val() == '9'){ //Others
        	$("#paperBillWaiveOtherStaffId${status.index}").removeAttr("disabled");
    	}else{
        	$("#paperBillWaiveOtherStaffId${status.index}").attr("disabled", "disabled");
    	}
    }
    
    $("#waiveReason${status.index}").change(function(){
    	checkPaperWaiveReaCd();
    });

    $('#oia${status.index}').click(function(){
    	$('#optOutReason${status.index}').attr("disabled", "disabled");
        $('#optOutRemarks${status.index}').attr("disabled", "disabled");
        $('#optOutReason${status.index}').val('');
        $('#optOutRemarks${status.index}').val('');
    });
    
    $('#ooa${status.index}').click(function(){
    	$('#optOutReason${status.index}').removeAttr("disabled");
        $('#optOutRemarks${status.index}').removeAttr("disabled");
    });

	$("#cspCheckbox${status.index}").click(function(){
		checkCsp();
	});
	
	$(".billListRadio${status.index}").click(function(event){
		/*$(".billListRadio${status.index}").not(this).removeAttr('checked');
		$(this).val('true');
		$(".billListRadio${status.index}").not(this).val('false');*/
		
	});
	
	/*$(".billListRadio${status.index}").click(function(){
		$("#hiddenSelectBillItem${status.index}").val(this.value);
	});*/
	
	function checkBillMediaEmail(){
		if ($('#emailBillItem${status.index}').is(':checked')){
	   		var email = $('#contactEmail').val();
            if(email != '' && $('#billMediaEmail${status.index}').val() == ''){
            	$('#billMediaEmail${status.index}').val(email);
            }
            if("${empty billMedia.custExistingPaperBillInd}" == "true"){
				$('#paperBillChargingSel${status.index}').val('Y');
                $('#paperBillCharging${status.index}').val('Y');
                $('#paperBillChargingSel${status.index}').attr("disabled", "disabled");
                checkBillCharging();
            }
	    }else{
	    	$('#billMediaEmail${status.index}').val('');
            if("${empty billMedia.custExistingPaperBillInd}" == "true"){
	            $('#paperBillChargingSel${status.index}').removeAttr("disabled");
	            checkBillCharging();
            }
	    }
	}
	
	
	$('input[name="billMediaDtlList[${status.index}].selectedBillItem"]').change(function(event){
		 checkBillMediaEmail();
	});
	
	function checkBillCharging(){
		$('#paperBillCharging${status.index}').val($('#paperBillChargingSel${status.index}').val());
		if ($('#paperBillChargingSel${status.index}').val()=='W'){
		 	$('#waiveReason${status.index}').removeAttr("disabled");
	    }else{
	    	$('#waiveReason${status.index}').attr("disabled", "disabled");
	    	$('#waiveReason${status.index}').val('');
	    }
	}
	
	$('#paperBillChargingSel${status.index}').change(function(event){
		checkBillCharging();
	});
	
	$('input[name="billMediaDtlList[${status.index}].redemMedia"]').change(function(event){
		 $('input[name="billMediaDtlList[${status.index}].redemMediaEmail"]').removeAttr("readonly"); 
		 if ($('#redemEmail${status.index}').is(':checked')){
	   		if ($('#emailBillItem${status.index}').is(':checked')){
                var email = $('input[name="billMediaDtlList[${status.index}].billMediaEmail"]').val();
	   			$('input[name="billMediaDtlList[${status.index}].redemMediaEmail"]').attr("readonly", "readonly");
	   		}else{
	   			var email = $('#contactEmail').val();
	   		}
	   		$('input[name="billMediaDtlList[${status.index}].redemMediaEmail"]').val(email);
	   		}else{
	   			$('input[name="billMediaDtlList[${status.index}].redemMediaEmail"]').val('');	
	   		}
		 if (!$('#redemSms${status.index}').is(':checked')){
			 $('input[name="billMediaDtlList[${status.index}].redemSms"]').val('');
		 }
	   	});
	
	 
	    $('#copyCspEmailAddrBtn${status.index}').click(function(event) {
			event.preventDefault();
			var email = $('#existingCspEmail').val();
			$('#cspEmail${status.index}').val(email);
			$('#copyCspEmailAddrBtn${status.index}').hide();
			$('#clearCspEmailAddrBtn${status.index}').show();
			if( $('#cspMobile${status.index}').val() != "" && $('#dummyEmailMobInd').val() != 'Y' && $('#dummyMobileInd${status.index}').val() != 'Y' )
			{
				brightCarecash();
			}
		});
	    
	    $('#copyCspMobileNoBtn${status.index}').click(function(event) {
			event.preventDefault();
			var MobileNo = $('#contactMobileNo').val();
			$('#cspMobile${status.index}').val(MobileNo);
			if( $('#cspEmail${status.index}').val() != "" && $('#dummyEmailMobInd').val() != 'Y' && $('#dummyEmailInd${status.index}').val() != 'Y' )
			{
				brightCarecash();
			}
		});

	    $('#copyClubEmailAddrBtn${status.index}').click(function(event) {
			event.preventDefault();			
			var email = $('#existingCspEmail').val();
			$('#clubEmail${status.index}').val(email);
			$('#copyClubEmailAddrBtn${status.index}').hide();
			$('#clearClubEmailAddrBtn${status.index}').show();
		});
	    
	    $('#copyClubMobileNoBtn${status.index}').click(function(event) {
			event.preventDefault();
			var MobileNo = $('#contactMobileNo').val();
			$('#clubMobile${status.index}').val(MobileNo);
		});
	    
	    $('#copyCarecashEmailAddrBtn${status.index}').click(function(event) {
			event.preventDefault();			
			var email = $('#contactEmail').val();
			$('#carecashEmail${status.index}').val(email);
		});
	    
	    $('#copyCarecashMobileNoBtn${status.index}').click(function(event) {
			event.preventDefault();
			var MobileNo = $('#contactMobileNo').val();
			if(MobileNo==null || MobileNo=='')
			{
				MobileNo = $('#contactFixedLineNo').val();
			}			
			
			$('#carecashContactNo${status.index}').val(MobileNo);
		});
	    
	    $('#genCspEmailAddrBtn${status.index}').click(function(event) {
			event.preventDefault();
			var email = $('#sysGenEmail').val();
			$('#cspEmail${status.index}').val(email);
			$('#dummyEmailInd${status.index}').val("Y");
			$('#dummyEmailMobInd').val("Y");
			$('input[name="billMediaDtlList[${status.index}].cspEmail"]').attr("readonly", "readonly");
			$('#genCspEmailAddrBtn${status.index}').hide();
			$('#clearCspEmailAddrBtn${status.index}').show();
			if($('#oIdStatus').val() == 'RC_NO_CUSTOMER')
			{
				dimCarecash();
			}
		});
	    
	    $('#clearCspEmailAddrBtn${status.index}').click(function(event) {
			event.preventDefault();
			$('#cspEmail${status.index}').val("");
			$('#dummyEmailInd${status.index}').val("N");
			$('#dummyEmailMobInd').val("N");
			$('input[name="billMediaDtlList[${status.index}].cspEmail"]').removeAttr("readonly");
			$('#clearCspEmailAddrBtn${status.index}').hide();
			$('#genCspEmailAddrBtn${status.index}').show();
		});
	    
	    $('#genCspMobileNoBtn${status.index}').click(function(event) {
			event.preventDefault();
			$('#cspMobile${status.index}').val("00000000");
			$('#dummyMobileInd${status.index}').val("Y");
			$('#dummyEmailMobInd').val("Y");
			$('input[name="billMediaDtlList[${status.index}].cspMobile"]').attr("readonly", "readonly");
			$('#genCspMobileNoBtn${status.index}').hide();
			$('#clearCspMobileNoBtn${status.index}').show();
			if($('#oIdStatus').val() == 'RC_NO_CUSTOMER')
			{
				dimCarecash();
			}
		});
	    
	    $('#clearCspMobileNoBtn${status.index}').click(function(event) {
			event.preventDefault();
			$('#cspMobile${status.index}').val("");
			$('#dummyMobileInd${status.index}').val("N");
			$('#dummyEmailMobInd').val("N");
			$('input[name="billMediaDtlList[${status.index}].cspMobile"]').removeAttr("readonly");
			$('#clearCspMobileNoBtn${status.index}').hide();
			$('#genCspMobileNoBtn${status.index}').show();
		});
	    

	    $('#genClubEmailAddrBtn${status.index}').click(function(event) {
			event.preventDefault();			
			var email = $('#sysGenEmail').val();
			$('#clubEmail${status.index}').val(email);
			$('#dummyEmailInd${status.index}').val("Y");
			$('#dummyEmailMobInd').val("Y");
			$('input[name="billMediaDtlList[${status.index}].clubEmail"]').attr("readonly", "readonly");
			$('#genClubEmailAddrBtn${status.index}').hide();
			$('#clearClubEmailAddrBtn${status.index}').show();
		});
	    
	    $('#clearClubEmailAddrBtn${status.index}').click(function(event) {
			event.preventDefault();			
			$('#clubEmail${status.index}').val("");
			$('#dummyEmailInd${status.index}').val("N");
			$('#dummyEmailMobInd').val("N");
			$('input[name="billMediaDtlList[${status.index}].clubEmail"]').removeAttr("readonly");
			$('#clearClubEmailAddrBtn${status.index}').hide();
			$('#genClubEmailAddrBtn${status.index}').show();
		});
	    
	    $('#genClubMobileNoBtn${status.index}').click(function(event) {
			event.preventDefault();
			$('#clubMobile${status.index}').val("00000000");
			$('#dummyMobileInd${status.index}').val("Y");
			$('#dummyEmailMobInd').val("Y");
			$('input[name="billMediaDtlList[${status.index}].clubMobile"]').attr("readonly", "readonly");
			$('#genClubMobileNoBtn${status.index}').hide();
			$('#clearClubMobileNoBtn${status.index}').show();
	    });
	    
	    
	    $('#clearClubMobileNoBtn${status.index}').click(function(event) {
			$('#clubMobile${status.index}').val("");
			$('#dummyEmailInd${status.index}').val("N");
			$('#dummyEmailMobInd').val("N");
			$('input[name="billMediaDtlList[${status.index}].clubMobile"]').removeAttr("readonly");
			$('#clearClubMobileNoBtn${status.index}').hide();
			$('#genClubMobileNoBtn${status.index}').show();
		});
	    

	
	
	if($("#hiddenSelectBillItem${status.index}").val() != ''){
		if($("#cspCheckbox${status.index}").is(":checked")){
	//		$(".billListCheckbox[value=" + $("#hiddenSelectBillItem${status.index}").val() +"]").attr("checked", true);
		}else{
			$(".billListRadio${status.index}[value=" + $("#hiddenSelectBillItem${status.index}").val() +"]").attr("checked", true);
		}
	}
	
	</script>

</c:forEach>

<c:if test="${ not empty requestScope.errorMsgList }">
<div id="errorMsgDiv">
<br>
	<table width="90%" border="0">
		<tr>
			<td width="10%">&nbsp;</td>											
			<td width="80%" align="left">
				<div id="error_Msg_div" class="ui-widget" style="visibility: visible;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<c:forEach items="${requestScope.errorMsgList }" var="errorMsg">
							<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
								<div  class="contenttext">
									<c:out value="${errorMsg}"></c:out>
								</div>
							<p></p>
						</c:forEach>
					</div>
				</div>													
			</td>
			<td width="10%">&nbsp;</td>
		</tr>
	</table>
</div>
</c:if>

<script language="javascript">
	var confirmMsg1 = '<spring:message code="lts.acq.billingSelection.dummyEmailNotRegisterMyHKTAndiGUARDCARECash" htmlEscape="false"/>';
	var confirmMsg2 = '<spring:message code="lts.acq.billingSelection.dummyEmailNotRegisterMyHKT" htmlEscape="false"/>';

	$("#submit").click(function(event) {
		event.preventDefault();
		if($("#errorMsg").val() != ''){
			alert($("#errorMsg").val());
			return;
		}
	if ($('#dummyEmailMobInd').val() == 'Y'){
			if($('#oIdStatus').val() == 'RC_NO_CUSTOMER'){
				if(confirm(confirmMsg1)){
					$("#billmediabilllangform").submit();
				}  	
		    }
			else
			{
				if(confirm(confirmMsg2)){
					$("#billmediabilllangform").submit();
				}
			}			

		}else{
			$("#billmediabilllangform").submit();
		}
	});
	
    $('form').bind('submit', function() {
    	$.blockUI({ message: null });
    });
    
    

</script> <%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>