<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<style type="text/css">
.supportDocDiv table { border-collapse: collapse }
.supportDocDiv table td { border-width: 1px; padding: 3px; border-style: inset; text-align: center }
</style>

<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/web/lts/acq/ltsacqsupportdoc.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp" >
    <jsp:param name="step" value="10" />
</jsp:include>
<br>
<!--<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" >
    <jsp:param name="internalUse" value="true" />
    <jsp:param name="custInfo" value="true" />
</jsp:include>-->

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<form:form method="POST" id="ltsAcqSupportDocForm" name="ltsAcqSupportDocForm" commandName="ltsAcqSupportDocCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="formAction"/>
<form:hidden path="collectMethod" />

<c:if test="${not empty ltsAcqSupportDocCmd.generatedFormList}">
	<!--<table width="100%" class="tablegrey">  -->
	<table width="100%" class="paper_w2 round_10">
		<tr>
			<!--<td bgcolor="#FFFFFF">  -->
			<td>
				<c:if test="${sessionScope.sessionLtsAcqOrderCapture.channelPremier == true}">
					<table width="100%" border="0" cellspacing="1">
						<tr>
							<td colspan="4" ><div id="s_line_text"> <spring:message code="lts.acq.supportDoc.orderSignoffMode" arguments="" htmlEscape="false"/> </div><br></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="1" class="contenttext">
						<tr>
							<td width="1%">&nbsp;</td>
							<td width="25%">
								<div>
									<form:radiobutton path="signoffMode" value="C" /> <b> <spring:message code="lts.acq.supportDoc.callCenterMode" arguments="" htmlEscape="false"/> </b> 
								</div>
							</td>
							<td width="25%">
								<div>
									<form:radiobutton path="signoffMode" value="R" /> <b> <spring:message code="lts.acq.supportDoc.retailMode" arguments="" htmlEscape="false"/> </b> 
								</div>
							</td>
							<td width="49%">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4"> <form:errors path="signoffMode" cssClass="error"/> &nbsp; </td>
						</tr>
					</table>
				</c:if>
				<table width="100%" border="0" cellspacing="1">
					<tr>
					<!--<td class="table_title">Springboard Form Generation</td>  -->	
					     <div id="s_line_text"><spring:message code="lts.acq.supportDoc.supportDoc" arguments="" htmlEscape="false"/></div>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<c:if test="${not empty param.outQuoErrMsgList}">
					<tr>
						<td colspan="4">
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
						<td colspan="4"> &nbsp; </td>
					</tr>
					<tr>
						<td width="1%">&nbsp;</td>
						<td width="50%">
							<div>
								<c:forEach items="${ltsAcqSupportDocCmd.generatedFormList}" var="generatedForm" varStatus="status">
								<c:if test="${status.index %2 == 0}">
									<form:checkbox path="generatedFormList[${status.index}].selected" disabled="true" />&nbsp; <b>${language == 'en_US' || language == 'en' ? generatedForm.docName : generatedForm.docNameChi}</b>
									<!--<form:checkbox path="generatedFormList[${status.index}].selected" disabled="true" />&nbsp;${generatedForm.docName}-->
								</c:if>
								<br>
								</c:forEach>
							</div>
						</td>
						<td width="50%">
							<div>
							<c:forEach items="${ltsAcqSupportDocCmd.generatedFormList}"  var="generatedForm" varStatus="status">
								<c:if test="${status.index %2 != 0}">
									<form:checkbox path="generatedFormList[${status.index}].selected" disabled="true" />&nbsp; <b>${language == 'en_US' || language == 'en' ? generatedForm.docName : generatedForm.docNameChi}</b>
									<!--<form:checkbox path="generatedFormList[${status.index}].selected" disabled="true" />&nbsp;${generatedForm.docName}-->
								</c:if>
								<br>
							</c:forEach>
							</div>
						</td>
						<td width="1%">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4"> &nbsp; </td>
					</tr>
				</table>
				<table width="100%" cellspacing="0" border="0" >
					<tr>
						<td colspan="2">
							<table width="100%" cellspacing="0" border="0">
								<tr> 
									<td>
                    				<!--<td colspan="4" class="table_title">Distribution Mode<br></td>  -->
                    				<div id="s_line_text"><spring:message code="lts.acq.supportDoc.distributionMode" arguments="" htmlEscape="false"/></div>
									</td>
             					</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" class="contenttext">
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
								<c:if test="${!isBrCust}">
									<tr>
										<td width="1%">&nbsp;</td>
										<td width="60%" colspan="2">
											<form:radiobutton path="distributionMode" value="E" /> <b><spring:message code="lts.acq.supportDoc.digital" arguments="" htmlEscape="false"/></b>
											<!--<form:radiobutton path="distributionMode" value="E" /> Digital Signature-->
											<form:errors path="distributionMode" cssClass="error"/>	
										</td>
										<td width="2%">&nbsp;</td>
									</tr>
									<tr>
										<td colspan="4">
											<div id="distributeMethodDiv">
												<table width="100%" cellspacing="2" border="0">
													<tr>
														<td width="5%">&nbsp;</td>
														<td width="15%"><form:checkbox path="sendEmail" cssClass="digitalSignature"/> <spring:message code="lts.acq.supportDoc.email" arguments="" htmlEscape="false"/>: </td>
														<td width="80%">
															<form:input cssClass="digitalSignature" path="distributeEmail" size="25" />
															<form:errors path="distributeEmail" cssClass="error"/>													
														</td>
													</tr>
													
													<tr>
														<td width="5%">&nbsp;</td>
														<td width="15%"><form:checkbox path="sendSms" cssClass="digitalSignature"/> <spring:message code="lts.acq.supportDoc.SMSMobileNumber" arguments="" htmlEscape="false"/>: </td>
														<td width="80%">
															<form:input cssClass="digitalSignature" path="distributeSms" size="25" />
															<form:errors path="distributeSms" cssClass="error"/>													
														</td>
													</tr>
													
													<tr>
														<td>&nbsp;</td>
													</tr>														
												</table>				
											</div>										
										</td>
									</tr>
								</c:if>
								<c:if test="${ltsAcqSupportDocCmd.allowDistributePaper}">
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="95%" colspan="2">
									<c:if test="${ltsAcqSupportDocCmd.channelRetail}">
										<form:radiobutton path="distributionMode" value="P" /> <b><label id="distributionModePaperRadio" ><spring:message code="lts.acq.supportDoc.paper" arguments="" htmlEscape="false"/></label></b>
									</c:if>
									<c:if test="${!ltsAcqSupportDocCmd.channelRetail}"> 
										<form:radiobutton path="distributionMode" value="P" /> <b><label id="distributionModePaperRadio" ><spring:message code="lts.acq.supportDoc.paperByPost" arguments="" htmlEscape="false"/></label></b>
									</c:if>
										
										<!--<form:radiobutton path="distributionMode" value="P" /> Paper-->
									</td>
									<td width="2%">&nbsp;</td>
								</tr>
								</c:if>
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				
				<table width="100%" cellspacing="0" border="0">
					<tr>
						<td colspan="2">
							<table width="100%" cellspacing="0" border="0">
								<tr> 
                    				<!--<td colspan="4" class="table_title">Application Form Language<br></td>  -->
                    				<div id="s_line_text"><spring:message code="lts.acq.supportDoc.applicationFormLang" arguments="" htmlEscape="false"/></div>
             					</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" class="contenttext">
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="95%">
										<table width="100%" border="0" class="contenttext">
											<tr>
												<td width="1%">&nbsp;</td>
												<td width="90%">
													<spring:message code="lts.acq.supportDoc.language" arguments="" htmlEscape="false"/>:&nbsp;
													<form:select path="distributeLang">
														<form:option label='Select...' value=""/>
														<form:option label='Traditional Chinese' value="CHN"/>
														<form:option label='English' value="ENG"/>
													</form:select> 
													<form:errors path="distributeLang" cssClass="error"/> 
												</td>
												<td width="1%">&nbsp;</td>
											</tr>
										</table>
									</td>
									<td width="2%">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table width="100%" cellspacing="0" border="0" >
					<tr>
						<td colspan="2">
							<table width="100%" cellspacing="0" border="0" >
								<tr> 
                    				<!--<td colspan="4" class="table_title">Required Supporting Document<br></td>  -->
                    				<div id="s_line_text"><spring:message code="lts.acq.supportDoc.requiredSupportDoc" arguments="" htmlEscape="false"/></div>
             					</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" class="contenttext">
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="90%">
										<div class="supportDocDiv">
											<table width="100%" class="table_style_sb" border="1" >
												<!--<tr bgcolor="#99C68E" >  -->
												<tr>
													<th width="35%"><spring:message code="lts.acq.supportDoc.documentType" arguments="" htmlEscape="false"/></th>
													<th width="45%"><spring:message code="lts.acq.supportDoc.waiveReason" arguments="" htmlEscape="false"/></th>
													<th width="20%"><spring:message code="lts.acq.supportDoc.mandatoryBeforeSignoff" arguments="" htmlEscape="false"/></th>
												</tr>
												<c:choose>
													<c:when test="${not empty ltsAcqSupportDocCmd.supportDocumentList }">
														<c:forEach items="${ltsAcqSupportDocCmd.supportDocumentList}" var="supportDocument" varStatus="status">
															<tr align="center">
																<td>${language == 'en_US' || language == 'en' ? supportDocument.docName : supportDocument.docNameChi}</td>
																<td>
																	<c:choose>
																		<c:when test="${not empty supportDocument.waiveReasonList}">
																			<form:select path="supportDocumentList[${status.index}].waiveReasonCd" cssClass="waiveReasonList">
																				<form:option value="" label="Select..."/>
																				<c:forEach items="${supportDocument.waiveReasonList}" var="waiveReason" varStatus="status">
																					<form:option value="${waiveReason.waiveReasonCd}" label="${waiveReason.waiveReasonDesc}" cssClass="${waiveReason.defaultWaiveInd}" />
																				</c:forEach>
																			</form:select>
																		</c:when>
																		<c:otherwise>
																			<spring:message code="lts.acq.supportDoc.NA" arguments="" htmlEscape="false"/>
																		</c:otherwise>
																	</c:choose>
																</td>
																<td>
																	<c:out value="${supportDocument.mdoInd == 'M' ? 'Y' : 'N'}" />
																</td>
															</tr>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr>
															<td colspan="3"><spring:message code="lts.acq.supportDoc.NIL" arguments="" htmlEscape="false"/></td>
														</tr>
													</c:otherwise>
												</c:choose>
											</table>
										</div>
									</td>
									<td width="1%">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>									
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<div id="errorDiv" style="width: 70%;">
							<div class="ui-widget" style="visibility: visible;">
								
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_msg" class="contenttext">
										<spring:message code="lts.acq.supportDoc.invalidWaiveReason" arguments="" htmlEscape="false"/>
									</div>
									&nbsp;&nbsp;
									<div id="reason_name" class="contenttext" style="font-weight: bold;">
									
									</div>
									<p></p>
								</div>
			
							</div>
						</div> 
						 </td>
					</tr>
				</table>
				<table width="100%" cellspacing="0" border="0" >
					<tr>
						<td colspan="2">
							<table width="100%" cellspacing="0" border="0">
								<tr> 
                    				<div id="s_line_text"><spring:message code="lts.acq.common.suspendRemark" htmlEscape="false"/></div>
             					</tr>
							</table>
						</td>
						</tr>
						<tr>
						<td colspan="2">
							<table width="100%" border="0" class="contenttext">
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="15%">
										<form:textarea path="suspendRemarks" rows="6" cols="100" cssStyle="resize:none;"></form:textarea>
									    <form:errors path="suspendRemarks" cssClass="error"/>
									</td>
							    </tr>
							</table>
						</td>	
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
</c:if>

<br>

<table width="100%" border="0" cellspacing="0" class="contenttext">
<tr>
	<td align="right" > 
		<form:errors path="suspendReason" cssClass="error"/>
			<b><spring:message code="lts.acq.common.suspendReason" htmlEscape="false"/> </b>
		<form:select path="suspendReason" id="suspendReason">
			<form:option value="NONE"><spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/></form:option>
			<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
		</form:select>
		<!--<a href="#" id="suspend" class="nextbutton">Suspend</a>
		<a href="#" id="submit" class="nextbutton">Continue ></a>   -->
		<a href="#" id="suspend" class="nextbutton"><div class="button"><spring:message code="lts.acq.common.suspend" htmlEscape="false"/></div></a>
		<a href="#" id="submit" class="nextbutton"><div class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a> 
	</td>
	
</tr>
</table>
</form:form>

<script type='text/javascript'>
	var isBrCust = "${isBrCust}" == "true";
	
	var selectVal = '<spring:message code="lts.acq.supportDoc.select" arguments="" htmlEscape="false"/>';
	var chiVal = '<spring:message code="lts.acq.supportDoc.traditionalChinese" arguments="" htmlEscape="false"/>';
	var engVal = '<spring:message code="lts.acq.supportDoc.english" arguments="" htmlEscape="false"/>';
	
	var x = document.getElementById("distributeLang");
	var txt = "All options: ";
	var i;
	for (i = 0; i < x.length; i++) {
		if(x.options[i].text=='Select...')
		{
		   x.options[i].text=selectVal;
		}
		else if (x.options[i].text=='Traditional Chinese')
		{
			x.options[i].text=chiVal;
		}
		else if (x.options[i].text=='English')
		{
			x.options[i].text=engVal;
		}
	}
	
	var noEmailAlert = '<spring:message code="lts.acq.supportDoc.confirmNoEmailAddress" arguments="" htmlEscape="false"/>';
	var paperModeAlert = '<spring:message code="lts.acq.supportDoc.confirmUsePaperMode" arguments="" htmlEscape="false"/>';
	
	var swaitAppointmentMakingVal = '<spring:message code="lts.acq.common.suspendReason.swaitAppointmentMaking" htmlEscape="false"/>';
	var userMissingSubmitOrderVal = '<spring:message code="lts.acq.common.suspendReason.userMissingSubmitOrder" htmlEscape="false"/>';
	var awaitDocumentVal = '<spring:message code="lts.acq.common.suspendReason.awaitDocument" htmlEscape="false"/>';
	var DRGDowntimeVal = '<spring:message code="lts.acq.common.suspendReason.DRGDowntime" htmlEscape="false"/>';
	var NACaseVal = '<spring:message code="lts.acq.common.suspendReason.5NACase" htmlEscape="false"/>';
	var OtherVal = '<spring:message code="lts.acq.common.suspendReason.Other" htmlEscape="false"/>';
	var pendingOrderExistVal = '<spring:message code="lts.acq.common.suspendReason.pendingOrderExist" htmlEscape="false"/>';
	var awaitPaymentVal = '<spring:message code="lts.acq.common.suspendReason.awaitPayment" htmlEscape="false"/>';
	var manualCancellationVal = '<spring:message code="lts.acq.common.suspendReason.manualCancellation" htmlEscape="false"/>';
	var TOSServiceNumberVal = '<spring:message code="lts.acq.common.suspendReason.TOSServiceNumber" htmlEscape="false"/>';
	var awaitCreditCardOrAutoPayPaymentVal = '<spring:message code="lts.acq.common.suspendReason.awaitCreditCardOrAutoPayPayment" htmlEscape="false"/>';

	var suspendReasonEle = document.getElementById("suspendReason");
	var a;
	if(suspendReasonEle!=null)
	{
		for (a = 0; a < suspendReasonEle.length; a++) {
			
			if(suspendReasonEle.options[a].text=='Await Appointment Making (cancel after 7 day)')
			{
				suspendReasonEle.options[a].text=swaitAppointmentMakingVal;
			}
			else if (suspendReasonEle.options[a].text=='User Missing Submit Order (cancel after 2 days)')
			{
				suspendReasonEle.options[a].text=userMissingSubmitOrderVal;
			}
			else if (suspendReasonEle.options[a].text=='Await Document (cancel after 7 day)')
			{
				suspendReasonEle.options[a].text=awaitDocumentVal;
			}
			else if (suspendReasonEle.options[a].text=='DRG Downtime')
			{
				suspendReasonEle.options[a].text=DRGDowntimeVal;
			}
			else if (suspendReasonEle.options[a].text=='5NA Case (cancel after 30 days)')
			{
				suspendReasonEle.options[a].text=NACaseVal;
			}
			else if (suspendReasonEle.options[a].text=='Other (cancel after 7 day)')
			{
				suspendReasonEle.options[a].text=OtherVal;
			}
			else if (suspendReasonEle.options[a].text=='Pending Order Exist (cancel after SRD + 30 days)')
			{
				suspendReasonEle.options[a].text=pendingOrderExistVal;
			}
			else if (suspendReasonEle.options[a].text=='Await Payment (cancel after 7 days)')
			{
				suspendReasonEle.options[a].text=awaitPaymentVal;
			}
			else if (suspendReasonEle.options[a].text=='Manual Cancellation')
			{
				suspendReasonEle.options[a].text=manualCancellationVal;
			}
			else if (suspendReasonEle.options[a].text=='TOS Service Number (cancel after 7 day)')
			{
				suspendReasonEle.options[a].text=TOSServiceNumberVal;
			}
			else if (suspendReasonEle.options[a].text=='Await Credit Card / Bank Auto Pay Payment')
			{
				suspendReasonEle.options[a].text=awaitCreditCardOrAutoPayPaymentVal;
			}
		}
	}
	
	$(ltsacqsupportdoc.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>