<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/lts/disconnect/ltsterminateprogressbar.jsp" >
	<jsp:param name="step" value="1" />
</jsp:include>

<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/web/lts/disconnect/ltsterminateserviceselection.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script src="js/jquery-ui-1.8.16.custom.min.js"></script>

<script>
	var defaultValueMap = new Object();
</script>

<form:form method="POST" id="ltsTerminateServiceSelectionForm" commandName="ltsTerminateServiceSelectionCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<c:set var="profile" value="${sessionScope.sessionLtsTerminateOrderCapture.ltsServiceProfile}" />
<c:set var="profileAddressRollout" value="${profile.address.addressRollout}" />
<form:hidden path="formAction"/>
<form:hidden path="terminatePCDind" id="terminatePCDind"/>
<form:hidden path="terminateHDTVind" id="terminateHDTVind"/>
<form:hidden path="hasBillableAcct" id="hasBillableAcct"/>
<form:hidden path="hasOtherSrvSameAcc" id="hasOtherSrvSameAcc"/>
<form:hidden path="srvAcctNum" id="srvAcctNum"/>
<table width="98%" align="center"  class="paper_w2 round_10">
	<tr>
		<td>
			<table width="98%" border="0" cellspacing="1" align="center">
				<tr>
					<td>
					<br/>
					<div id="s_line_text">LTS Termination Service Details</div>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="1" cellpadding="4" align="center">
				<tr>
					<td width="35%">&nbsp;</td>
					<td width="55%">&nbsp;</td>
					<td width="10%">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3">
						<div id="s_sub_line_text">Applicant Details</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						Application Date* :  
					</td>
					<td>
						<form:input id="appDate" path="appDate" readonly="true" maxlength="10"/>
						<form:errors path="appDate" cssClass="error" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<c:if test="${not empty profile.existEyeType}">
					<tr>
						<td align="right">
							Services to be Terminated* : 
						</td>
						<td>
						  <form:checkbox id="removeNowtv" path="removeNowtv" /><b>Will remove with Now TV</b>
						  <br>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
					  <td align="right">&nbsp;</td>
					  <td width="80%">
						 <form:checkbox id="removeBoardband" path="removeBoardband" /><b>Will remove with Netvigator broadband services</b>
						 <br>
					  </td>
					  <td>&nbsp;</td>
					</tr>
					<tr><td align="right">Lost Modem Case :</td>
						<td width="80%">
							<form:checkbox id="lostModem" path="lostModem" disabled="${!ltsTerminateServiceSelectionCmd.fsaWgInd}" />
							<form:select id="lostModemUsageInd" path="lostModemUsageInd">
								<form:option value="HU">Have eye usage</form:option>
								<form:option value="NU">No eye usage</form:option>
							</form:select>
							<form:errors path="lostModem" cssClass="error" />
						</select>
						</td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
				<tr>
					<td align="right">Disconnect Reason* : </td>
					<td>
						<form:select path="disconnectReason" >
							<form:option value="">-- SELECT --</form:option>
							<form:options items="${not empty profile.existEyeType ? eyeDisconenctReasonList : delDisconenctReasonList}" itemValue="itemKey" itemLabel="itemValue"/>
						</form:select>
						<form:errors path="disconnectReason" cssClass="error" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">D-Form Serial* : </td>
					<td>
						<form:input path="disconnectFormSerial"  id="dFormSn"/>
						<form:errors path="disconnectFormSerial" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td align="right">Waive D-Form Reason* : </td>
					<td>
						<form:select path="waiveDFormReason" id="waiveDFormReason" >
							<form:option value="" label="--SELECT--" />
							<form:options items="${waiveDFormReasonList}" itemValue="itemKey" itemLabel="itemValue"/>
						</form:select>
						<form:errors path="waiveDFormReason" cssClass="error" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<%-- <tr>
					<td align="right">
						Lost Equipment Penalty Handling* : 
					</td>
					<td>
						<form:select path="lostEquipmentPenalty"  >
							<form:option value="" label="-- SELECT --" />
							<form:options items="${waiveLossPenaltyReasonList}" itemValue="itemKey" itemLabel="itemValue"/>
						</form:select>
					</td>
					<td>&nbsp;</td>
				</tr> --%>
				<c:if test="${ltsTerminateServiceSelectionCmd.fsaWgInd}">
					<tr>
						<td align="right">
							FCH  : 
						</td>
						<td>
							<form:input path="fch" id="fch"/>
						</td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
				<tr class="deceasedInd" style="display:show">
					<td align="right" valign="top">
						Deceased* : 
					</td>
					<td>
						<form:select path="deceasedType">
							<form:option value="">--SELECT--</form:option>
							<form:option value="N">Refund to Existing Customer</form:option>
							<form:option value="S">Refund to New Applicant (O/S &lt;/=$400 with Relationship Supporting) </form:option>
							<form:option value="I">Refund to New Applicant (with Probate / Letters of Administration ) </form:option>
							<form:option value="U">Unreached Case</form:option>
						</form:select>
						<br/>
						<form:errors path="deceasedType" cssClass="error" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						Third Party Application* : 
					</td>
					<td>
						<form:checkbox id="thirdAppCheck" path="thirdParty" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr class="TPInfo" style="display:none">
					<td>
						<div id="s_sub_line_text">Third Party Application</div>
					</td>
				</tr>
				<tr class="TPInfo" style="display:none">
					<td align="right">
						Title* : 
					</td>
					<td>
						<form:select id="thirdTitle"  path="thirdTitle">
							<form:option value="" label="-- SELECT --" />
							<form:options items="${titleList}" itemValue="itemKey" itemLabel="itemValue" />
						</form:select>
						<form:errors path="thirdTitle" cssClass="error" />
					</td>
				</tr>
				<tr class="TPInfo" style="display:none">
					<td align="right">
						Given Name* : 
					</td>
					<td>
						<form:input path="thirdFirstName" id="thirdFirstName" />
						<form:errors path="thirdFirstName" cssClass="error" />
					</td>
				</tr>
				<tr class="TPInfo" style="display:none">
					<td align="right">
						Family Name* : 
					</td>
					<td>
						<form:input path="thirdLastName" id="thirdLastName" />
						<form:errors path="thirdLastName" cssClass="error" />
					</td>
				</tr>
				<tr class="TPInfo" style="display:none">
					<td align="right">
						Document Type and Number : 
					</td>
					<td>
						<form:select id="thirdDocType"  path="thirdDocType">
							<form:option value="" label="-- SELECT --" />
							<form:option value="HKID" label="HKID" />
							<form:option value="PASS" label="Passport" />
						</form:select>
						<form:input path="thirdDocNum" id="thirdDocNum" />
						<form:errors path="thirdDocType" cssClass="error" />
						<form:errors path="thirdDocNum" cssClass="error" />
					</td>
				</tr>
				<tr class="TPInfo" style="display:none">
					<td align="right">
						Document Verified : 
					</td>
					<td>
						<form:checkbox path="thirdIdVerify" id="thirdIdVerify"/>
						<form:errors path="thirdIdVerify" cssClass="error"/>
					</td>
				</tr>
				<tr class="TPInfo" style="display:none">
					<td align="right">
						Relationship with Applicant* : 
					</td>
					<td>
						<form:select path="thirdRelationship" id="thirdRelationship" > 
							<form:option value="">-- SELECT --</form:option>
							<form:options items="${profile.primaryCust.docType != 'BS' ? relationshipCodeList : relationshipBrCodeList}" itemValue="itemKey" itemLabel="itemValue" />
							<form:option value="NP" label="OTHERS" />
						</form:select>
						<form:input path="thirdOtherRelationship" id="thirdOtherRelationship" cssStyle="display:none"/>
						<form:errors path="thirdRelationship" cssClass="error"/>
					</td>
				</tr>
				<tr class="TPInfo" style="display:none">
					<td align="right">
						Contact Number : 
					</td>
					<td>
						<form:input path="thirdContactNum" id="thirdContactNum" />
						<form:errors path="thirdContactNum" cssClass="error" />
					</td>
				</tr>
				<tr class="TPInfo" style="display:none">
					<td align="right">
						<div class="func_button">
							<a href="#" id="clearThirdParty">Clear</a>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table width="98%" border="0" class="paper_w2 round_10" align="center">
	<tr>
		<td>
			<table width="98%" border="0" cellspacing="1" rules="none" align="center">
				<tr>
					<td>
					<br/>
					<div id="s_line_text">Service Profile</div></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="1" cellpadding="4" class="contenttext" align="center">
				<tr>
					<td width="35%">&nbsp;</td>
					<td width="55%">&nbsp;</td>
					<td width="10%">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3">
						<div id="s_sub_line_text">LTS Customer Profile</div>
					</td>
				</tr>
				<c:if test="${profile.primaryCust.docType == 'BS'}">
					<tr>
						<td align="right">Company Name : </td>
						<td class="bold"> 
							<c:out value="${profile.primaryCust.companyName}"/>
						</td>
						<td>&nbsp;</td>
					</tr>
				</c:if>	
				<c:if test="${profile.primaryCust.docType != 'BS'}">										
					<tr>
						<td align="right">Family Title : </td>
						<td class="bold">
							<c:out value="${profile.primaryCust.title}"/>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Customer Name : </td>
						<td class="bold">
							<c:out value="${profile.primaryCust.lastName}"/>&nbsp;<c:out value="${profile.primaryCust.firstName}"/>
						</td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
				<tr>
					<td align="right">Document ID : </td>
					<td class="bold">
						(<c:out value="${profile.primaryCust.docType}"/>)&nbsp;<c:out value="${profile.primaryCust.docNum}"/>
					</td>
					<td>&nbsp;</td>
				</tr>									
				<tr>
					<td align="right">Installation Address : </td>
					<td class="bold">
						<c:out value="${profileAddressRollout.fullAddress}" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3">
						<div id="s_sub_line_text">LTS Pending Order</div>
					</td>
				</tr>	
				<tr>
					<td align="right">Pending LTS Order Exist : </td>
					<td class="bold">
						<c:out value="${profile.pendingOcid != null ? 'Y' : 'N' }" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">Pending LTS Order ID : </td>
					<td class="bold">
						<c:out value="${profile.pendingOcid}" default="--"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">Pending LTS Order SRD : </td>
					<td class="bold">
						<c:if test="${profile.pendingOcSrd != null}">
							<span style="color: ${ profile.pendingOrderOverdue ? 'red;' : 'blue;' }">
								<c:out value="${profile.pendingOcSrd}"/>
							</span>
						</c:if>
						<c:if test="${profile.pendingOcSrd == null}">
							<span style="color: blue;">
								--
							</span>
						</c:if>
					</td>
					<td>&nbsp;</td>
				</tr>
				<c:set var="imsPendingOrder" value="${sessionScope.sessionLtsTerminateOrderCapture.imsPendingOrder}" />
				<c:if test="${not empty profile.existEyeType}">
				<tr>
					<td colspan="3">
						<div id="s_sub_line_text">IMS and PCD Pending Order</div>
					</td>
				</tr>
				<tr>
					<td align="right">Related FSA : </td>
					<td class="bold">
						<c:out value="${profile.relatedEyeFsa}" default="--"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">Pending IMS Order Exist : </td>
					<td class="bold">
						<c:out value="${not empty imsPendingOrder ? 'Y' : 'N' }" default="--"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">Pending IMS Order ID : </td>
					<td class="bold">
						<c:out value="${not empty imsPendingOrder ? imsPendingOrder.ocId : '--' }" default="--"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">Pending IMS Springboard ID : </td>
					<td class="bold">
						<c:out value="${not empty imsPendingOrder ? imsPendingOrder.orderId : '--' }" default="--"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">Pending IMS Order SRD : </td>
					<td class="bold">
						<c:out value="${imsPendingOrder.srdStart}" />
						&nbsp;--&nbsp;
						<c:out value="${imsPendingOrder.srdEnd}" />
					</td>
					<td>&nbsp;</td>
				</tr>
				</c:if>
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>

<table width="98%" border="0" class="paper_w2 round_10" align="center">
	<tr>
		<td>
			<table width="98%" border="0" cellspacing="1" rules="none" align="center">
				<tr>
					<td>
						<br/>
						<div id="s_line_text">Service Plan</div>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="1" cellpadding="4" class="contenttext" align="center">
				<tr>
					<td width="35%">&nbsp;</td>
					<td width="35%">&nbsp;</td>
					<td width="50%">&nbsp;</td>
				</tr>
				<tr>
					<td width="30%">
						<div id="s_sub_line_text">Service Plan</div>
					</td>
				</tr>
				<c:if test="${not empty ltsTerminateServiceSelectionCmd.srvPlanItemDetails}">
				<tr>
					<td colspan="5">
						<table width="100%">
							<tr>
								<td colspan="3">
									<table width="100%" border="0">
										<tr>
											<td>
												<table class="table_style" width="100%" border="1">
													<tr>
														<th align="center">TP Description</th>
														<th align="center">TP Categories</th>
														<th align="center">IDD Minutes</th>
														<th align="center">Effective Date</th>
														<th align="center">End Date</th>
														<th align="center">Remain Contract Month</th>
														<th align="center">Gross Effective Price</th>
														<th align="center">Penalty Handling</th>
													</tr>
													<c:set var="rowCount" value="0" />
													
													<c:forEach items="${ltsTerminateServiceSelectionCmd.srvPlanItemDetails}" var="srvPlanItem" varStatus="status">
														<c:set var="rowCount" value="${rowCount + 1 }"/>
														<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''} " >
															<td align="center"><c:out value="${srvPlanItem.itemDetail.itemDisplayHtml}" default="-" /></td>
															<td align="center"><c:out value="${srvPlanItem.itemDetail.tpCatg}" default="-" /></td>
															<td align="center"><c:out value="${srvPlanItem.iddFreeMin == '0' ? '-' : srvPlanItem.iddFreeMin}" default="-" /></td>
															<td align="center"><c:out value="${srvPlanItem.conditionEffStartDate}" default="-" /></td>
															<td align="center"><c:out value="${srvPlanItem.conditionEffEndDate}" default="-" /></td>
															<td align="center"><c:out value="${srvPlanItem.remainContractMonth}" default="-" /></td>
															<td align="center">$<c:out value="${srvPlanItem.itemDetail.grossEffPrice}" default="-"/></td>
<%-- 															<td class="waiveHandling" align="center" style="display:none"><form:label path="srvPlanItemDetails[${status.index}].itemDetail.penaltyHandling" value="WaivePenalty">Waive Penalty</form:label></td>
 --%>															<td class="penaltyHandling" align="center">
															<c:choose>
																<c:when test="${srvPlanItem.itemDetail.penaltyHandling == 'AW' }">
																	Auto Waived
																</c:when>
																<c:when test="${srvPlanItem.itemDetail.penaltyHandling == 'AG' || srvPlanItem.itemDetail.penaltyHandling == 'MW'}">
																	<form:select cssClass="penalty" path="srvPlanItemDetails[${status.index}].itemDetail.penaltyHandling" >
																		<form:options items="${penaltyMap}" />
																	</form:select>
																</c:when>
																<c:when test="${srvPlanItem.remainContractMonth == '-'}">
																	Free To Go
																</c:when>
																<c:otherwise>
																	Free To Go
																</c:otherwise>
															</c:choose>
															</td>
														</tr>
													</c:forEach>
													<c:if test="${rowCount == 0}">
														<tr>
															<td colspan="8" align="center"><b>NIL</b></td>
														</tr>
													</c:if>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</c:if>
				<tr>
					<td width="30%">
						<div id="s_sub_line_text">Other VAS Offer</div>
					</td>
				</tr>
				<c:if test="${not empty ltsTerminateServiceSelectionCmd.vasPlanItemDetails}">	
				<tr>
					<td colspan="5">
						<table width="100%">
							<tr>
								<td colspan="3">
									<table width="100%" border="0">
										<tr>
											<td width="100%">
												<table class="table_style" width="100%" border="1">
													<tr>
														<th align="center">Offer Description</th>
														<th align="center">Effective Date</th>
														<th align="center">End Date</th>
														<th align="center">Gross Effective Price</th>
														<th align="center">Net Effective Price</th>
														<th align="center">Penalty Handling</th>
													</tr>
													<c:set var="rowCount" value="0" />
													<c:forEach items="${ltsTerminateServiceSelectionCmd.vasPlanItemDetails}" var="vasPlanItem" varStatus="status">
														<c:set var="rowCount" value="${rowCount + 1 }"/>
													<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''} " >
														<td align="center">${vasPlanItem.itemDetail.itemDisplayHtml}</td>
														<td align="center"><c:out value="${vasPlanItem.conditionEffStartDate}" default="-" /></td>
														<td align="center"><c:out value="${vasPlanItem.conditionEffEndDate}" default="-" /></td>
														<td align="center">$<c:out value="${vasPlanItem.paidVas ? vasPlanItem.itemDetail.grossEffPrice : '0'}" default="-" /></td>
														<td align="center">$<c:out value="${vasPlanItem.paidVas ? vasPlanItem.itemDetail.netEffPrice : '0'}" default="-" /></td>
<%-- 														<td class="waiveHandling" align="center" style="display:none"><form:label path="vasPlanItemDetails[${status.index}].itemDetail.penaltyHandling">Waive Penalty</form:label></td>
 --%>														<td class="penaltyHandling" align="center">
														<c:choose>
															<c:when test="${vasPlanItem.itemDetail.penaltyHandling == 'AW' }">
																Auto Waived
															</c:when>
															<c:when test="${vasPlanItem.itemDetail.penaltyHandling == 'AG' || vasPlanItem.itemDetail.penaltyHandling == 'MW'}">
																<form:select cssClass="penalty" path="vasPlanItemDetails[${status.index}].itemDetail.penaltyHandling" >
																	<form:options items="${penaltyMap}" />
																</form:select>
															</c:when>
															<c:when test="${vasPlanItem.conditionEffEndDate ge today}">
																Free To Go
															</c:when>
															<c:otherwise>
																Free To Go
															</c:otherwise>
														</c:choose>
														</td>
													</tr>
													</c:forEach>
													<c:if test="${rowCount == 0}">
													<tr>
														<td colspan="6" align="center"><b>NIL</b></td>
													</tr>
													</c:if>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					</c:if>
					<c:if test="${not empty ltsTerminateServiceSelectionCmd.fsaVasPlanItemDetails}">	
						<tr>
							<td colspan="4">
								<div id="s_sub_line_text">Related FSA VAS Offer</div>
								<table width="100%" border="0">
									<tr>
										<td width="100%">
											<table class="table_style" width="100%" border="1">
												<tr>
													<th align="center">Offer Description</th>
													<th align="center">Effective Date</th>
													<th align="center">End Date</th>
													<th align="center">Gross Effective Price</th>
													<th align="center">Net Effective Price</th>
													<th align="center">Penalty Handling</th>
													<th align="center">Action</th>
												</tr>
												<c:set var="rowCount" value="0" />
													<c:forEach items="${ltsTerminateServiceSelectionCmd.fsaVasPlanItemDetails}" var="profileItemDetail" varStatus="status">
															<c:set var="rowCount" value="${rowCount + 1 }"/>
															<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" >
																<td align="center"><span class="bold"><c:out value="${profileItemDetail.itemDetail.itemDisplayHtml}" default="-" /></span></td>
																<td align="center"><span class="bold"><c:out value="${profileItemDetail.conditionEffStartDate}" default="-" /></span></td>
																<td align="center"><span class="bold"><c:out value="${profileItemDetail.conditionEffEndDate}" default="-" /></span></td>
																<td align="center"><span class="bold">$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.grossEffPrice : '0'}" default="-" /></span></td>
																<td align="center"><span class="bold">$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.netEffPrice : '0'}" default="-" /></span></td>
<%-- 																<td class="waiveHandling" align="center" style="display:none"><form:label path="fsaVasPlanItemDetails[${status.index}].itemDetail.penaltyHandling">Waive Penalty</form:label></td>
 --%>															<td class="penaltyHandling" align="center"><span class="bold">
																<c:choose>
																	<c:when test="${profileItemDetail.itemDetail.penaltyHandling == 'AW' }">
																		Auto Waived
																	</c:when>
																	<c:when test="${profileItemDetail.itemDetail.penaltyHandling == 'AG' }">
																		Generate
																	</c:when>
																	<c:when test="${profileItemDetail.itemDetail.penaltyHandling == 'MW' }">
																		Waived
																	</c:when>
																	<%-- <c:when test="${profileItemDetail.itemDetail.penaltyHandling == 'AG' || profileItemDetail.itemDetail.penaltyHandling == 'MW' }">
																		<form:select cssClass="penalty" path="fsaVasPlanItemDetails[${status.index}].itemDetail.penaltyHandling" >
																			<form:options items="${penaltyMap}" />
																		</form:select>
																	</c:when> --%>
																	<c:otherwise>
																		Free To Go
																	</c:otherwise>
																</c:choose>
																</span></td>
																<td align="center"><span class="bold">		
																<c:choose>
																<c:when test="${not empty profileItemDetail.itemActions}">																				
																	<c:forEach items="${profileItemDetail.itemActions}" var="itemAction" varStatus="itemActionStatus">
																		<c:if test="${itemAction.toProd == 'eye3a' || itemAction.toProd == 'EYE3A'}">
																			<c:choose>
																				<c:when test="${itemAction.action == 'O' }">
																					Auto Out 
																					<c:if test="${itemAction.penaltyHandle != null}">
																						(<c:out value="${itemAction.penaltyHandle == 'AW' ? 'Auto Waived' : 'Generate Penalty'}" />)
																					</c:if>			
																				</c:when>
																				<c:otherwise>
																					Carry Forward	
																				</c:otherwise>
																			</c:choose>
																		</c:if>
																	</c:forEach>																						
																</c:when>
																<c:otherwise>
																	Carry Forward	
																</c:otherwise>
																</c:choose>
																</span></td>
															</tr>
													</c:forEach>
													<c:if test="${rowCount == 0}">
														<tr bgcolor="#FFF">
															<td colspan="6" align="center"><b>NIL</b></td>
														</tr>
													</c:if>																																	
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
					<tr>
						<td>&nbsp;</td>
					</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table width="98%" border="0" class="paper_w2 round_10" align="center">
	<tr>
		<td>
			<table width="98%" border="0" cellspacing="1" rules="none" align="center">
				<tr>
					<td>
						<br>
						<div id="s_line_text">Other Related Services</div></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="1" cellpadding="4" class="contenttext" align="center">
				<tr>
					<td width="35%">&nbsp;</td>
					<td width="35%">&nbsp;</td>
					<td width="50%">&nbsp;</td>
				</tr>
				<tr>
					<td width="30%" colspan="3">
						<div id="s_sub_line_text">IDD 0060</div>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<table width="100%" border="0">
							<tr>
								<td width="100%">
									<table class="table_style" width="100%" border="1">
										<tr>
											<th align="center" colspan="2" width="25%">Remove ? </th>
											<th align="center">Service Type</th>
											<th align="center">Phone Number</th>
										</tr>
										<c:set var="rowCount" value="0"/>
										<c:forEach items="${ltsTerminateServiceSelectionCmd.idd0060ProfileList}" var="iddDetails" varStatus="status">
											<c:set var="rowCount" value="${rowCount + 1 }"/>
											<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}">
												<td align="center">
												<form:radiobutton path="idd0060ProfileList[${status.index}].action" id="idd0060retain,${iddDetails.srvNum},${iddDetails.acctNum}" cssClass="iddRadio${status.index} ${iddDetails.sameAcct? 'isSameAcc': 'isNotSameAcc'}" value="RETAIN" /> Retain
												</td>
												<td align="center">
												<form:radiobutton path="idd0060ProfileList[${status.index}].action" id="idd0060remove,${iddDetails.srvNum},${iddDetails.acctNum}" cssClass="iddRadio${status.index} ${iddDetails.sameAcct? 'isSameAcc': 'isNotSameAcc'}" value="REMOVE"/> Remove
												</td>
												<td align="center">
													${iddDetails.srvType} / ${iddDetails.datCode}
												</td>
												<td align="center">
													<c:out value="${iddDetails.srvNum}" default="-"/>
												</td>
											</tr>
											
											<script>
												defaultValueMap[".iddRadio${status.index}"] = "${iddDetails.action}";

												/* $(".iddRadio${status.index}").change(function(e){
													defaultValueMap[".iddRadio${status.index}"] = $(".iddRadio${status.index}:checked").val();
												}); */
												
												
											</script>
										</c:forEach>
										<c:if test="${rowCount == 0}">
											<tr>
												<td colspan="4" align="center"><b>NIL</b></td>
											</tr>
										</c:if> 
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td width="30%" colspan="3">
						<div id="s_sub_line_text">Calling Card</div>
					</td>
				</tr>
				<c:choose>
					<c:when test="${ltsTerminateServiceSelectionCmd.callingCardInd}">
					<tr>
							<td align="left">
							<div class="callingCardInd1" style="display:inline">
								<form:checkbox path="callingCardInd" id="callingCardInd" disabled="true" />	Exist
								<form:hidden path="callingCardSameAcct" id="callingCardSameAcct"/>
								<form:hidden path="callingCardAcctList" id="callingCardAcctList"/>
							</div>
							<div class="callingCardDetailsBlk" style="display:inline">
								<form:select id="callingCardDetailsHandling"  path="callingCardDetailsHandling" >
									<form:option value="RETAIN" label="Retain Service" />
									<form:option value="DISCONNECT" label="Disconnect Service" />
								</form:select>
								<form:errors path="callingCardDetailsHandling" cssClass="error"/>
							</div>
							</td>
					</tr>
					</c:when>
					<c:otherwise>
					<tr>
							<td align="left" class="bold callingCardInd1" >
							Not Applicable
							</td>
					</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="30%">
						<div id="s_sub_line_text">IDD Fixed Plan</div>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<table width="100%" border="0">
							<tr>
								<td width="100%">
									<form:errors path="iddCallPlanProfileList" cssClass="error"/>
									<table class="table_style" width="100%" border="1">
										<tr>
											<th align="center" id="actionHeader" colspan="2" width="20%">Action</th>
											<th align="center">Plan Code</th>
											<th align="center">Holder Type</th>
											<th align="center">Service Description</th>
											<th align="center">Effective Start Date</th>
											<th align="center">Contract Start Date</th>
											<th align="center">Contract End Date</th>
											<th align="center">Remain Contract</th>
											<th align="center">Commitment Fee</th>
											<th align="center">Effective End Date</th>
											<th align="center" width="10%">Penalty Handling</th>
											<th align="center" style="min-width: 130px;">New DCA</th>
										</tr>
 										<c:set var="rowCount" value="0"/>
										<input type="hidden" id="callPlanRowCount" value="${fn:length(ltsTerminateServiceSelectionCmd.iddCallPlanProfileList)}" />
										<c:forEach items="${ltsTerminateServiceSelectionCmd.iddCallPlanProfileList}" var="callPlanDetail" varStatus="status">
											<form:hidden path="iddCallPlanProfileList[${status.index}].dca" id="dca${status.index}"/>
											<c:set var="rowCount" value="${rowCount + 1 }"/>
											<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}">
												<td align="center" class="actionNonRemoveColumn ${callPlanDetail.dca}">
													<div id="noOtherSrvInCurAccAction${status.index}" class="noOtherSrvInCurAccAction ${callPlanDetail.dca}">
														<c:if test="${callPlanDetail.planHolderType == 'A' }">
															<input type="radio" disabled="disabled"/> 
															Change DCA
														</c:if>
														<c:if test="${callPlanDetail.planHolderType == 'C' }">
															<form:radiobutton id="chgDcaRadio${status.index}" path="iddCallPlanProfileList[${status.index}].action" 
																				cssClass="callPlanRadio${status.index} chgDcaRadio ${callPlanDetail.dca}" value="CHG_DCA"
																				onclick="changeAcctAndOrDca(${status.index})"/> 
															Change DCA
														</c:if>
													</div>
													<div id="hasOtherSrvInCurAccAction${status.index}" class="hasOtherSrvInCurAccAction ${callPlanDetail.dca}">
														<form:radiobutton id="retainCallPlanRadio${status.index}" path="iddCallPlanProfileList[${status.index}].action" 
																				cssClass="callPlanRadio${status.index} retainRadio ${callPlanDetail.dca}" value="RETAIN"
																				onclick="retainCallPlan(${status.index})"/> 
														Retain
													</div>
													
												</td>
												<td align="center" class="actionRemoveColumn ${callPlanDetail.dca}">
													<form:radiobutton id="removeCallPlanRadio${status.index}" path="iddCallPlanProfileList[${status.index}].action" 
																		cssClass="callPlanRadio${status.index} removeRadio ${callPlanDetail.dca}" value="REMOVE"
																		onclick="removeCallPlan(${status.index})"/> 
													Remove
												</td>
											<td align="center"><c:out value="${callPlanDetail.planCd}" /></td>
												<td align="center"><c:out value="${callPlanDetail.planHolderType}" /></td>
												<td align="center"><c:out value="${callPlanDetail.description}" /></td>
												<td align="center"><c:out value="${callPlanDetail.effStartDateForDisplay}" /></td>
												<td align="center"><c:out value="${callPlanDetail.contractStartDateForDisplay}" /></td>
												<td align="center"><c:out value="${callPlanDetail.contractEndDateForDisplay}" /></td>
												<td align="center"><c:out value="${callPlanDetail.remainContract}" /></td>
												<td align="center"><c:out value="$${callPlanDetail.grossEffPrice}" /></td>
												<td align="center"><c:out value="${callPlanDetail.effEndDateForDisplay}" /></td>
												<td class="penaltyHandling" align="center">
													<c:set var="today" value="${ltsTerminateServiceSelectionCmd.today}"></c:set>
	 												<div id="retainCallPlanPenaltyHanding${status.index}"> -- </div>
	 												<div id="removeCallPlanPenaltyHanding${status.index}">
													<c:choose>
														<c:when test="${callPlanDetail.effStartDate gt today}" >
																Plan Not Yet Start
														</c:when>
														<c:when test="${callPlanDetail.penaltyHandling == 'OTHER_PARTY_HNDL' }">
																Other Party Handled
														</c:when>
														<c:when test="${today gt callPlanDetail.contractEndDate 
																			|| today == callPlanDetail.contractEndDate 
																			|| empty callPlanDetail.contractStartDate}">
																Free To Go
																<input type="hidden" name="iddCallPlanProfileList[${status.index}].penaltyHandling" value="FREE_TO_GO"/>
														</c:when>
														<c:when test="${callPlanDetail.penaltyHandling == 'AW' }">
																Auto Waived
														</c:when>
														<c:when test="${callPlanDetail.penaltyHandling != 'AW' }">
															<form:select cssClass="penalty" path="iddCallPlanProfileList[${status.index}].penaltyHandling" >
																<form:option label="Generate" value="G"/>
																<c:if test="${callPlanDetail.giftType == true}">
																	<form:option label="Waived (Special Approval by Marketing)" value="MW"/>
																</c:if>
																<c:if test="${callPlanDetail.giftType == false}">
																	<c:if test="${callPlanDetail.remainContractGt3Mths == true}">
																		<form:option label="Waived (Speical Approval by SM)" value="SM"/>
																	</c:if>
																	<c:if test="${callPlanDetail.remainContractGt3Mths == false}">
																		<form:option label="Waived (Speical Approval by UM)" value="UM"/>
																	</c:if>
																</c:if>
															</form:select>
														</c:when>
														<c:otherwise>
															Free To Go
														</c:otherwise>
													</c:choose>
													</div>
												</td>
												<td align="center">
													<div id="newDcaDiv${status.index}" style="display:none">
														<form:input id="newDca${status.index}" path="iddCallPlanProfileList[${status.index}].newDca" size="16" readonly="true"/>
														<div class="func_button"><a href="#" onclick="promptChooseAcct(${status.index}, event)">Choose Account</a></div>
													</div>
													<div id="noNewDcaDiv${status.index}">
														--
													</div>
												</td>
											</tr>
											
											<script>
												defaultValueMap[".callPlanRadio${status.index}"] = "${callPlanDetail.action}";
												
												/* $(".callPlanRadio${status.index}").change(function(e){
													defaultValueMap[".callPlanRadio${status.index}"] = $(".callPlanRadio${status.index}:checked").val();
												}); */
												
												if($(".callPlanRadio${status.index}:checked").val() != 'REMOVE'){
													$("#retainCallPlanPenaltyHanding${status.index}").show();
													$("#removeCallPlanPenaltyHanding${status.index}").hide();
												}else{
													$("#retainCallPlanPenaltyHanding${status.index}").hide();
													$("#removeCallPlanPenaltyHanding${status.index}").show();
												}
												
												$("#retainCallPlanRadio${status.index}").click(function(event){
													$("#retainCallPlanPenaltyHanding${status.index}").show();
													$("#removeCallPlanPenaltyHanding${status.index}").hide();
												});

												$("#removeCallPlanRadio${status.index}").click(function(event){
													$("#retainCallPlanPenaltyHanding${status.index}").hide();
													$("#removeCallPlanPenaltyHanding${status.index}").show();
												});
											</script>
											
										</c:forEach>
										<c:if test="${rowCount == 0}">
											<tr>
												<td colspan="11" align="center"><b>NIL</b></td>
											</tr>
										</c:if> 
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>			
</table>
<br>
<c:if test="${ltsTerminateServiceSelectionCmd.splitAcctInd}">
	<table width="100%" id="splitedAccAlert">
		<tr>
			<td width="5%">&nbsp;</td>
			<td width="70%">
				<div id="error_div" class="ui-widget" style="visibility: visible;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						</p>
						<div id="error_msg" class="contenttext">
							Alert: Deceased case with Splited R/I account. Not allow to proceed.
						</div>
						<p></p>
					</div>
				</div>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<br>
</c:if>
<table width="100%" border="0" cellspacing="0" class="contenttext" id="submitButton">
	<tr>
		<td align="right">
			<div class="button">
				<a href="#" id="submitBtn">Continue</a>
			</div>
		</td>
	</tr>
</table>
</form:form>

<script type='text/javascript'>
var splitAcc = "${ltsTerminateServiceSelectionCmd.splitAcctInd}" == "true";

function promptChooseAcct(ffpIdx, e){
	e.preventDefault();
	var sbuid = $('input[name="sbuid"]').val();
	var removeSrv = '';
	$("[class^=iddRadio][value=REMOVE]:checked").each(
		function(i, e){
			removeSrv = removeSrv + (removeSrv!=''?",":'') + $(e).attr('id').split(',')[1];
		});
	var removeCallingCardAcct = $("#callingCardDetailsHandling").val() == 'DISCONNECT' ?$("#callingCardAcctList").val(): '';
	var url = "ltsterminateacctselection.html?sbuid=" + sbuid + "&ffpIdx=" + ffpIdx + "&rmvSrv=" + removeSrv + "&rmvCallingCardAcct=" + removeCallingCardAcct;
	window.open(url, "_blank", "width=800, height=600, resizable=no, toolbar=no, location=yes, menubar=no, status=no, left=100, top=100");
}



function putSelectedAcct(ffpIdx, acctNum){
	$("#newDca"+ffpIdx).val(acctNum);
}

function changeAcctAndOrDca(idx){
	$("#newDcaDiv"+idx).show();
	$("#retainCallPlanPenaltyHanding"+idx).show();
	$("#removeCallPlanPenaltyHanding"+idx).hide();
	$("#noNewDcaDiv"+idx).hide();
}

function retainCallPlan(idx){
	$("#newDcaDiv"+idx).hide();
	$("#retainCallPlanPenaltyHanding"+idx).show();
	$("#removeCallPlanPenaltyHanding"+idx).hide();
	$("#noNewDcaDiv"+idx).show();
	$("#newDcaDiv"+idx).val("");
}

function removeCallPlan(idx){
	$("#newDcaDiv"+idx).hide();
	$("#retainCallPlanPenaltyHanding"+idx).hide();
	$("#removeCallPlanPenaltyHanding"+idx).show();
	$("#newDcaDiv"+idx).hide();
	$("#noNewDcaDiv"+idx).show();
}

$(ltsterminateserviceselection.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>
