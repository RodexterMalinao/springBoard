<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@page import="com.bomwebportal.lts.util.LtsSessionHelper"%>
<%@ page
	import="org.springframework.web.servlet.support.RequestContextUtils,com.bomwebportal.dto.BomSalesUserDTO,java.util.Locale"%>
<head>
	<script src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
	<script src="js/jquery.lts.autocomplete.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
	<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
</head>


<c:set var="isCcPt" value="<%=com.bomwebportal.lts.util.LtsSessionHelper.isChannelCs(request)%>"></c:set>
<c:set var="isDs" value="<%=com.bomwebportal.lts.util.LtsSessionHelper.isChannelDirectSales(request)%>"></c:set>
<br/>


<table width="98%" align="center">
<tr><td>

<form:form method="POST" action="#" id="orderamendform" commandName="orderamend">
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<input type="hidden" id="channelCd" name="channelCd" value="<%=channelCd%>" />
	<form:hidden id="sharePCDInd" path="sharePCDInd" />
	<form:hidden id="bbShortageInd" path="bbShortageInd" />
	<form:hidden id="pcdOrderExistInd" path="pcdOrderExistInd" />
	<form:hidden id="submitInd" path="submitInd" />
	<form:hidden id="confirmedInd" path="confirmedInd" />
	<form:hidden id="amendedInd" path="amendedInd" />
	<form:hidden id="serviceType" path="serviceType" />
	<form:hidden id="acqOrderCompleted" path="acqOrderCompleted" />
	<input type="hidden" id="isPreInstall" name="isPreInstall" value="${isPreInstall}" />

<div id="s_line_text">Create Amendment</div>
<br>
<table width="100%" border="0" cellspacing="1">
	<tr>
		<td width="5%">&nbsp;</td>
		<td width="20%"><b>Service Number:</b></td>
		<td>			
			<%-- ${orderamend.serNum} --%>
			<form:select id="serNum" path="serNum">
				<form:options items="${srvNumList}" itemValue="itemKey" itemLabel="itemValue" />
			</form:select>
		</td>
	</tr>
</table>
<br><br>
<div id="s_line_text">Order Amendment Taken Sales Info.</div>
<br>
<table width="100%" cellpadding="2" cellspacing="2">
	<tr>
		<td width="5%">&nbsp;</td>
		<td width="20%">Staff No.<span class="contenttext_red">*</span>
			:
		</td>
		<td colspan="2"><form:input path="staffNum" id="staffNum" readonly="true" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Salesmen Code / CMRID<span class="contenttext_red">*</span> :
		</td>
		<td colspan="2"><form:input maxlength="10" path="salesId" id="salesId" readonly="true" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Sales Channel<span
			class="contenttext_red">*</span> :
		</td>
		<td colspan="2"><form:input path="salesChannel" id="salesChannel" readonly="true" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Team / Shop Code<span
			class="contenttext_red">*</span> :
		</td>
		<td colspan="2"><form:input path="shopCode" id="shopCode" readonly="true" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Sales Contact No.<span
			class="contenttext_red">*</span> :
		</td>
		<td colspan="2"><form:input path="salesContactNum" id="salesContactNum" readonly="true" /></td>
	</tr>
</table>
<br><br>
<div id="s_line_text">Applicant Info.</div>
<br>
<table width="100%" cellpadding="2" cellspacing="2">
	<tr>
		<td width="5%">&nbsp;</td>
		<td width="20%">Applicant Name<span class="contenttext_red">*</span> :</td>
		<td colspan="2">
			<form:input path="name" id="name" />
			&nbsp;
			<form:errors path="name" cssClass="error" />
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Applicant Contact No.<span class="contenttext_red">*</span> :</td>
		<td colspan="2">
			<form:input path="contactNum" id="contactNum" />
			&nbsp;
			<form:errors path="contactNum" cssClass="error" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Applicant Relationship<span class="contenttext_red">*</span> :</td>
		<td colspan="2">
			<form:select path="relationship" id="relationship">
				<form:option value="">-- SELECT --</form:option>
				<form:option value="SU">Sub</form:option>
				<form:options items="${relationshipCodeList}" itemValue="itemKey" itemLabel="itemValue" />
			</form:select>
			&nbsp; 
			<form:errors path="relationship" cssClass="error" /></td>
	</tr>
	<c:if test="${isDs}">
		<tr>
			<td>&nbsp;</td>
			<td>Notice SMS No. :</td>
			<td colspan="2">
				<form:input path="noticeSmsNum"id="noticeSmsNum" />
				&nbsp; 
				<form:errors path="noticeSmsNum"cssClass="error" />
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>Notice Email Address :
			</td>
			<td colspan="2">
				<form:input path="noticeEmailAddr"id="noticeEmailAddr" />
				&nbsp; 
				<form:errors path="noticeEmailAddr" cssClass="error" />
			</td>
		</tr>
	</c:if>
</table>
<c:if test="${not empty orderamend.alertMsgList}">
	<c:set var="alertMsgList" value="${orderamend.alertMsgList}" />
	<br>
	<div id="alert_msg_bubble" class="ui-widget" style="visibility: visible;">
		<div class="ui-state-error ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
			<p>
				<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			</p>
			<div id="alert_msg" class="contenttext">
				<c:forEach items="${alertMsgList}" var="alertMsg" varStatus="status">
					<c:if test="${status.index > 0}"> <br/> </c:if>
					<b>${alertMsg} </b>
				</c:forEach>
			</div>
			<p></p>
		</div>
	</div>
</c:if>

<br><br>			
<div id="s_line_text">Amendment Options.</div>
<br>
<table width="100%" cellpadding="2" cellspacing="2">
	<tr>
		<td colspan="4" align="center"><form:errors path="categorySelected" cssClass="error" /></td>
	</tr>
	<tr>
		<td width="5%">&nbsp;</td>
		<td colspan="2" width="90%">
		<div style="overflow-x: auto;border-color:#BEBEBE "  class="ui-widget-content">
			<table width="100%">
				<tr class="ui-widget-header" height="40">
					<th width="5">&nbsp;</th>
					<th width="20%" align="center" style="font-size: 1.1em;">Category</th>
					<th width="80%" align="center" style="font-size: 1.1em;">Amendment Remark</th>
				</tr>
				<tr>
					<td><form:checkbox path="cancelOrderInd" id="cancelOrderInd" /></td>
					<td align="center"><b>Order Cancellation</b></td>
					<td><form:errors path="cancelOrderInd" cssClass="error" /></td>
				</tr>
				<tr>
					<td></td>
					<td align="center" colspan="2">
						<div id="orderCancelDiv" style="overflow-x: auto;border-color:#000000 "  class="ui-widget-content">
							<table class="contenttext" width="98%" cellpadding="5"  cellspacing="5" border="0" align="center">
								<c:if test="${not empty orderamend.pipbInfo && not empty orderamend.secDelAppntDtl}">
									<tr>
										<td colspan="4">
										<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em; width: 75%">
											<p>
												<span class="ui-icon ui-icon-info"
													style="float: left; margin-right: .3em;"></span>
											</p>
											<div class="contenttext">
												<b>2nd DEL Service will be cancelled together.</b>
											</div>
											<p></p>
										</div>
										</td>
									</tr>
								</c:if>
								<tr>
									<td width="20%">Cancel Type<span class="contenttext_red">*</span>:</td>
									<td colspan="3" style="text-align:left">
										<form:select id="cancelType" path="cancelType">
											<form:option value=""> -- SELECT -- </form:option>
											<form:options items="${cancelCodeList}" itemValue="itemKey" itemLabel="itemValue" />
										</form:select>
										<form:errors path="cancelType" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td>Cancel Reason<span class="contenttext_red">*</span>:
									</td>
									<td colspan="3" style="text-align:left">
										<form:select id="cancelReason" path="cancelReason">
											<form:option value=""> -- SELECT -- </form:option>
											<form:options items="${cancelReasonList}" itemValue="itemValue" itemLabel="itemValue" />
										</form:select>
										<form:errors path="cancelReason" cssClass="error" />
									</td>										
								</tr>
								<c:if test="${orderamend.firstMonthCcPrepaymentInd == true}">
									<c:if test="${orderamend.showPrepaymentRejectInd == true}">
										<tr>
											<td>&nbsp;</td>
											<td colspan="3">
												<br /> 
												<form:radiobutton id="prepayRejectBtm" path="prepaymentRejectInd" value="true" /> 
												<label>Prepayment Rejected</label>
												<form:radiobutton id="prepayNotRejectBtm" path="prepaymentRejectInd" value="false" /> 
												<label>Prepayment Not Rejected</label> 
												<br />
											</td>
										</tr>
									</c:if>
								</c:if>
								<tr>
									<td valign="top">Cancel Remark<span class="contenttext_red">*</span> :</td>
									<td colspan="3">
										<form:textarea path="cancelRemark" rows="10" cols="50" />
										<form:errors path="cancelRemark" cssClass="error" /> 
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td><form:checkbox path="amendAppointmentInd" id="amendAppointmentInd" /></td>
					<td align="center"><b>Appointment</b></td>
					<td><form:errors path="amendAppointmentInd" cssClass="error" /></td>
				</tr>
				<tr>
					<td></td>
					<td align="center" colspan="2">
						<div id="appointmentDiv" style="overflow-x: auto;border-color:#000000 "  class="ui-widget-content">
							<table id="booking" width="98%" border="0" cellpadding="5"  cellspacing="5" align="center" class="contenttext">
								<c:if test="${sessionScope.sessionLtsDummySbOrder.orderType != 'SBD'}">
									<tr>
										<td colspan="4">
											<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
												<p>
													<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
												</p>
												<div class="contenttext">
													<b>Estimated Earliest SRD </b> : <b>${orderamend.coreSrvAppntDtl.earliestSRDReason}</b>
												</div>
												
												<c:if test="${not empty orderamend.preWiringAppntDtl}">
													<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
													<div class="contenttext">
														<b>Estimated Earliest Prewiring Date : ${orderamend.preWiringAppntDtl.earliestSRDReason}</b>
													</div>
												</c:if>
												<c:if test="${not empty orderamend.pipbTwoNBWSrdReason}">
													<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
													<div class="contenttext">
														<b>Estimated Earliest SRD for PIPB Flat/Floor Amendment : ${orderamend.pipbTwoNBWSrdReason}</b>
													</div>
												</c:if>
												<c:if test="${orderamend.portLaterAppntDtl != null && not empty orderamend.portLaterAppntDtl.earliestSRDReason}">
													<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
													<div class="contenttext">
														<b>Estimated Earliest Porting SRD (PIPB) : ${orderamend.portLaterAppntDtl.earliestSRDReason}</b>
													</div>
												</c:if>
												<c:if test="${orderamend.penaltyWaivedCaseInd == 'true'}">
												 	<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
												 	<div class="contenttext">
												  		<b>System does not support waive/generate penalty option during amendment</b>
												  	</div>
												</c:if>
												<c:if test="${not empty orderamend.pcdActivationDate}">
													<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
													<div class="contenttext">
														<b>PCD Activation Date : ${orderamend.pcdActivationDate}</b>
													</div>
												</c:if>
												<p></p>
											</div>
											<div id="bbShortage_warning" style="display: none;">
												<table width="350">
													<tr>
														<td>
															<div id="error_profile" class="ui-widget" style="visibility: visible;">
																<div class="ui-state-error ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
																	<p>
																		<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																	</p>
																	<div id="error_profile_msg" class="contenttext">
																		<b>Warning: BB Shortage. SRD default to T+60.</b>
																	</div>
																	<p></p>
																</div>
															</div>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</c:if>
								<tr>
									<td colspan="2" width="45%">
										<table width="100%" border="0" cellspacing="2" cellpadding="3">
											<tr id="serial">
												<td width="50%">Pre-book Serial No. :</td>
												<td style="text-align:left">
													<form:input path="preBookSerialNum" id="preBookSerialNum" readonly="true" /> 
													<br />
													<form:errors path="preBookSerialNum" cssClass="error" />
												</td>
											</tr>
											<c:if test="${not empty orderamend.preWiringAppntDtl}">
												<tr id="prewiring1">
													<td>Pre-wiring Date :</td>
													<td>
														<form:input path="preWiringAppntDtl.appntDate" maxlength="10" id="preWiringDate" readonly="true" /> 
														<br /> 
														<form:errors path="preWiringAppntDtl.appntDate" cssClass="error" />
													</td>
												</tr>
												<tr id="prewiring2">
													<td>Pre-wiring Time :</td>
													<td>
														<c:if test="${orderamend.confirmedInd != 'true' && orderamend.acqOrderCompleted != 'true'}">
															<form:select path="preWiringAppntDtl.appntTimeSlotAndType" id="preWiringTime">
																<form:option value=""> -- SELECT -- </form:option>
																<c:forEach var="slot" items="${preWiringTimeSlot}">
																	<form:option value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}" label="${slot.apptTimeSlot}" />
																</c:forEach>
															</form:select>
														</c:if>
														<c:if test="${orderamend.confirmedInd == 'true' || orderamend.acqOrderCompleted == 'true'}">
															<form:input path="preWiringAppntDtl.appntTimeSlot" readonly="true"/>
														</c:if>
														<br /> 
														<form:errors path="preWiringAppntDtl.appntTimeSlotAndType" cssClass="error" />
													</td>
												</tr>
											</c:if>
											<tr>
												<td>
													<c:out value="${(sessionScope.sessionLtsDummySbOrder.orderType == 'SBR' && !orderamend.coreSrvAppntDtl.timeSlotRequiredInd)? 'New Contract Effective Date' : 'Target Installation Date'}" /><span class="contenttext_red">*</span> :
												</td>
												<td style="text-align:left">
													<form:input path="coreSrvAppntDtl.appntDate" id="installationDate" maxlength="10" readonly="true" /> 
													<br /> 
													<form:errors path="coreSrvAppntDtl.appntDate" cssClass="error" />
												</td>
											</tr>
											<c:if test="${orderamend.coreSrvAppntDtl.timeSlotRequiredInd}">
												<tr>
													<td>
														Target Installation Time<span class="contenttext_red">*</span> :
													</td>
													<td style="text-align:left">
														<c:if test="${orderamend.confirmedInd != 'true' && orderamend.acqOrderCompleted != 'true'}">
															<form:select id="installationTime" path="coreSrvAppntDtl.appntTimeSlotAndType">
																<form:option value=""> -- SELECT -- </form:option>
																<c:forEach var="slot" items="${installationTimeSlot}">
																	<form:option value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}" label="${slot.apptTimeSlot}" />
																</c:forEach>
															</form:select>
														</c:if>
														<c:if test="${orderamend.confirmedInd == 'true' || orderamend.acqOrderCompleted == 'true'}">
															<form:input path="coreSrvAppntDtl.appntTimeSlot" readonly="true"/>
															<c:if test="${orderamend.acqOrderCompleted}">
																<br/>
																<span style="font-size: 11px; color: red"><spring:message code="lts.amend.pipb.acq.order.complete"/></span>
															</c:if>
														</c:if>
														<br /> 
														<form:errors path="coreSrvAppntDtl.appntTimeSlotAndType" cssClass="error" />
													</td>
												</tr>
											</c:if>
										</table>
									</td>
									<td colspan="2" width="55%" valign="bottom">
										<c:if test="${orderamend.coreSrvAppntDtl.cutOverInd}">
											<div id="cutOver">
												<table width="100%" border="0" cellspacing="1" cellpadding="3">
													<tr>
														<td width="65%">
															Target Date for telephone number switching<span class="contenttext_red">*</span> :
														</td>
														<td>
															<form:input path="coreSrvAppntDtl.cutOverDate" id="cutOverDate" readonly="true"/> 
															<form:errors path="coreSrvAppntDtl.cutOverDate" cssClass="error" />
														</td>
													</tr>
													<tr>
														<td>
															Target Time for telephone number switching<span class="contenttext_red">*</span> :
														</td>
														<td>
															<form:input path="coreSrvAppntDtl.cutOverTime" maxlength="10" id="cutOverTime" readonly="true" disabled=""/>
															<c:if test="${isAcqPipbOrder && orderamend.pipbAmendAfterCutOver && !orderamend.pipbWqRejectedOrNotExist}">
																<br/>
																<span style="font-size: 11px; color: red"><spring:message code="lts.amend.pipb.cutOver.not.update"/></span>
															</c:if>
															<form:errors path="coreSrvAppntDtl.cutOverTime" cssClass="error" />
														</td>
													</tr>
												</table>
											</div>
										</c:if>
									</td>
								</tr>
								<c:if test="${not empty orderamend.secDelAppntDtl}">
									<tr>
										<td colspan="2" width="45%">
											<div id="secDel">
												<table width="100%" border="0" cellspacing="1" cellpadding="3">
													<tr>
														<td width="50%">
															2nd Del Installation Date<span class="contenttext_red">*</span> :
														</td>
														<td style="text-align:left">
															<form:input path="secDelAppntDtl.appntDate" id="secDelInstallationDate" readonly="true" />
															<form:errors path="secDelAppntDtl.appntDate" cssClass="error" />
														</td>
													</tr>
													<c:if test="${orderamend.secDelAppntDtl.timeSlotRequiredInd}">
														<tr>
															<td>
																2nd Del Installation Time<span class="contenttext_red">*</span> :
															</td>
															<td style="text-align:left">
																<c:if test="${orderamend.confirmedInd != 'true'}">
																	<form:select id="secDelInstallationTime" path="secDelAppntDtl.appntTimeSlotAndType">
																		<form:option value=""> -- SELECT -- </form:option>
																			<c:forEach var="slot" items="${secDelTimeSlot}">
																				<form:option value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}" label="${slot.apptTimeSlot}" />
																			</c:forEach>
																	</form:select> 
																</c:if>
																<c:if test="${orderamend.confirmedInd == 'true'}">
																	<form:input path="secDelAppntDtl.appntTimeSlot" readonly="true"/>
																</c:if>
															</td>
														</tr>
													</c:if>
												</table>
											</div>
										</td>
										<td colspan="2" width="55%">
											<c:if test="${orderamend.secDelAppntDtl.cutOverInd}">
												<div id="secDelCutOver">
													<table width="100%" border="0" cellspacing="1" cellpadding="3">
														<tr>
															<td width="65%">
																Target Date for 2nd Del telephone number switching<span class="contenttext_red">*</span> :
															</td>
															<td>
																<form:input path="secDelAppntDtl.cutOverDate" id="secDelCutOverDate" readonly="true"/> 
																<form:errors path="secDelAppntDtl.cutOverDate" cssClass="error" />
															</td>
														</tr>
														<tr>
															<td>
																Target Time for 2nd Del telephone number switching<span class="contenttext_red">*</span> :
															</td>
															<td>
																<form:input path="secDelAppntDtl.cutOverTime" maxlength="10" id="secDelCutOverTime" readonly="true"/>
																<c:if test="${isAcqPipbOrder && orderamend.pipbAmendAfterCutOver && !orderamend.pipbWqRejectedOrNotExist}">
																	<br/>
																	<span style="font-size: 11px; color: red">
																		<spring:message code="lts.amend.pipb.cutOver.not.update"/>
																	</span>
																</c:if>
																<form:errors path="secDelAppntDtl.cutOverTime" cssClass="error" />
															</td>
														</tr>
													</table>
												</div>
											</c:if>
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty orderamend.portLaterAppntDtl}">
									<tr>
										<td colspan="2" width="45%">
											<div id="secDel">
												<table width="100%" border="0" cellspacing="1" cellpadding="3">
													<tr>
														<td width="50%">
															Porting (PIPB) Date<span class="contenttext_red">*</span> :
														</td>
														<td style="text-align:left">
															<form:input path="portLaterAppntDtl.appntDate" id="portLaterDate" readonly="true" />
															<br><form:errors path="portLaterAppntDtl.appntDate" cssClass="error" />
														</td>
													</tr>
													<c:if test="${orderamend.portLaterAppntDtl.timeSlotRequiredInd}">
														<tr>
															<td>
																Porting (PIPB) Time<span class="contenttext_red">*</span> :
															</td>
															<td>
																<c:if test="${orderamend.confirmedInd != 'true'}">
																	<form:select id="portLaterTime" path="portLaterAppntDtl.appntTimeSlotAndType">
																		<form:option value=""> -- SELECT -- </form:option>
																		<c:forEach var="slot" items="${portLaterTimeSlot}">
																			<form:option value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}" label="${slot.apptTimeSlot}" />
																		</c:forEach>
																	</form:select> 
																</c:if>
																<c:if test="${orderamend.confirmedInd == 'true'}">
																	<form:input path="portLaterAppntDtl.appntTimeSlot" readonly="true"/>
																</c:if>
																<form:errors path="portLaterAppntDtl.appntTimeSlot" cssClass="error" />
															</td>
														</tr>
													</c:if>
												</table>
											</div>
										</td>
										<td colspan="2" width="55%">
											<c:if test="${orderamend.portLaterAppntDtl.cutOverInd}">
												<div id="secDelCutOver">
													<table width="100%" border="0" cellspacing="1" cellpadding="3">
														<tr>
															<td width="65%">
																Target Date for telephone number switching<span class="contenttext_red">*</span> :
															</td>
															<td>
																<form:input path="portLaterAppntDtl.cutOverDate" id="portLaterCutOverDate" readonly="true"/>
																<form:errors path="portLaterAppntDtl.cutOverDate" cssClass="error" />
															</td>
														</tr>
														<tr>
															<td>
																Target Time for telephone number switching<span class="contenttext_red">*</span> :
															</td>
															<td>
																<form:input path="portLaterAppntDtl.cutOverTime" maxlength="10" id="portLaterCutOverTime" readonly="true"/> 
																<c:if test="${isAcqPipbOrder && orderamend.pipbAmendAfterCutOver && !orderamend.pipbWqRejectedOrNotExist}">
																	<br/>
																	<span style="font-size: 11px; color: red"><spring:message code="lts.amend.pipb.cutOver.not.update"/></span>
																</c:if>
																<form:errors path="portLaterAppntDtl.cutOverTime" cssClass="error" />
															</td>
														</tr>
													</table>
												</div>
											</c:if>
										</td>
									</tr>
								</c:if>
								<tr id="bmo">
									<td colspan="4">
										<div id="bmoAlertDiv" style="display: none;">
											<div id="warning_addr" class="ui-widget" style="visibility: visible;">
												<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
													<p>
														<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
													</p>
													<div id="comfirm_msg" class="contenttext">
														<b>${orderamend.bmoRemark}</b><br />
													</div>
													<p></p>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</table>
							<table id="delayreason" width="98%" border="0" cellpadding="5" align="center" class="contenttext">
								<tr>
									<td>
										<table width="100%" border="0" cellspacing="2" cellpadding="3">
											<tr>
												<td width="22%">
													Delay Reason Code<span class="contenttext_red">*</span>:
												</td>
												<td style="text-align:left">
													<form:select id="delayReasonCode" path="delayReasonCode" >
														<form:option value="">-- SELECT --</form:option>
														<form:options items="${delayReasonCodeList}" itemValue="itemKey" itemLabel="itemValue" />
													</form:select> 
													<form:errors path="delayReasonCode" cssClass="error" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
								<table width="98%" border="0" cellpadding="5" align="center" class="contenttext">
									<tr>
										<td width="22%"></td>
										<td width="400">
											<div id="confirmMsgBlock" style="display: none;">
												<div id="warning_addr" class="ui-widget" style="visibility: visible;">
													<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
														<p>
															<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
														</p>
														<div id="comfirm_msg" class="contenttext">
															<b>Appointment Confirmed</b>
														</div>
														<p></p>
													</div>
												</div>
											</div>
											<div id="cancelMsgBlock" style="display: none;">
												<div id="error_profile" class="ui-widget" style="visibility: visible;">
													<div class="ui-state-error ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
														<p>
															<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
														</p>
														<div id="cancel_msg" class="contenttext">
															<b>${orderamend.errorMsg}</b>
														</div>
														<p></p>
													</div>
												</div>
											</div>
										</td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td>
											<input type="button" value="Amend" id="confirmAppntBtn" /> 
											<input type="button" value="Cancel" id="cancelAppntBtn" /> 
											<br /> 
											<form:errors path="confirmedInd" cssClass="error" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<c:if test="${orderamend.creditCard}"> 
							<tr class="amendCreditCardRow">
								<td><form:checkbox path="amendCreditCardInd" id="amendCreditCardInd" /></td>
								<td align="center"><b>Credit Card Information</b></td>
								<td></td>
							</tr>
							<tr class="amendCreditCardRow">
								<td></td>
								<td align="center" colspan="2">
									<div id="creditCardDiv" style="overflow-x: auto;border-color:#000000 "  class="ui-widget-content">
										<table width="98%" border="0" cellpadding="5" cellspacing="5" align="center" class="contenttext">
											<tr>
												<td width="20%">
													Third Party Credit Card<span class="contenttext_red">*</span> :
												</td>
												<td>
													<form:radiobutton path="thirdPartyCard" id="thirdPartyCardTrue" value="true" /> Yes 
													<form:radiobutton path="thirdPartyCard" id="thirdPartyCardFalse" value="false" /> No
												</td>
											</tr>
											
											<tr id="faxSerialDiv">
												<td>Fax Serial No. :</td>
												<td>
													<form:input path="faxSerialNum" id="faxSerial"/>
												</td>
											</tr>
											<tr>
												<td>
													Card Holder Name<span class="contenttext_red">*</span> :
												</td>
												<td>
													<form:input path="cardHolderName" id="cardHolderName" size="30" maxlength="60" /> 
													<form:errors path="cardHolderName" cssClass="error" />
												</td>
											</tr>
											<tr class="not3rdpartycc">
												<td>
													Card Holder Document Type<span class="contenttext_red">*</span> :
												</td>
												<td>
													<form:select path="cardHolderDocType" id="cardHolderDocType">
														<form:option value="">-- SELECT --</form:option>
														<form:option value="HKID">HKID</form:option>
														<form:option value="PASS">Passport</form:option>
														<form:option value="HKBR">HKBR</form:option>
													</form:select> 
													<form:errors path="cardHolderDocType" cssClass="error" />
												</td>
											</tr>
											<tr class="not3rdpartycc">
												<td>
													Card Holder Document No.<span class="contenttext_red">*</span> :
												</td>
												<td>
													<form:input path="cardHolderDocNum" id="cardHolderDocNum" size="20" maxlength="20" /> 
													<form:errors path="cardHolderDocNum" cssClass="error" />
												</td>
											</tr>
											<tr>
												<td>
													Card Number<span class="contenttext_red">*</span> :
												</td>
												<td>
													<form:input path="cardNum" id="cardnum" size="30" maxlength="16" readonly="true" /> 
													<input type="hidden" name="token" id="token" /> 
													<input type="hidden" name="ceksSubmit" value="N" /> 
													<a href="#" class="nextbutton" onclick="javascript:openCEKSWindow()">
														<div class="func_button">Input Credit Card No.</div>
													</a>
													<form:errors path="cardNum" cssClass="error" />
												</td>
											</tr>
											<tr>
												<td>
													Credit Card Type<span class="contenttext_red">*</span> :
												</td>
												<td>
													<form:select path="cardType" id="cardtype">
														<form:option value="">-- SELECT --</form:option>
														<form:options items="${creditCardTypeList}" itemValue="itemKey" itemLabel="itemValue" />
													</form:select> <form:errors path="cardType" cssClass="error" />
												</td>
											</tr>
											<tr>
												<td>
													Expire Date<span class="contenttext_red">*</span> :
												</td>
												<td>
													<form:select path="expireMonth">
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
										</table>
									</div>
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty orderamend.pipbInfo}">
							<tr class="amendAcqPipbRow">
								<td>
									<form:checkbox path="amendAcqPipbInd" id="amendAcqPipbInd" disabled="${orderamend.pipbAmendAfterCutOver}"/>
								</td>
								<td align="center"><b>PIPB information amendment</b></td>
								<td><form:errors path="amendAcqPipbInd" cssClass="error"/></td>
							</tr>
							<tr class="amendAcqPipbRow">
								<td></td> 
								<td align="center" colspan="2">
									<div id="amendAcqPipbDiv" style="overflow-x: auto;border-color:#000000 "  class="ui-widget-content">
										<table width="98%" border="0" cellpadding="5" cellspacing="5" align="center" class="contenttext">
										<tr>
											<td width="20%">
												<c:if test="${orderamend.brCust}">
													Change Company Name :
												</c:if>
												<c:if test="${!orderamend.brCust}">	
													Change Customer Name :
												</c:if>
											</td>
											<td>
												<form:checkbox id="pipbAmendCustNameInd" path="pipbAmendCustNameInd" />
											</td>
											<td>
												Change Flat/Floor :
											</td>
											<td>
												<form:checkbox id="pipbAmendFlatFloorInd" path="pipbAmendFlatFloorInd" />
												<form:errors path="pipbAmendFlatFloorInd" cssClass="error"/>
											</td>
										</tr>
										<tr>
											<c:if test="${orderamend.brCust}">	
												<td>Company Name :</td>
												<td>
													<form:input path="pipbCompanyName" readonly="true" cssClass="pipbName"/>
													<form:errors path="pipbCompanyName" cssClass="error"/>
												</td>
											</c:if>
											<c:if test="${!orderamend.brCust}">	
												<td>Title :</td>
												<td>
													<form:select path="pipbTitle" id="pipbTitle" cssClass="pipbName">
														<form:option value="">-- TITLE --</form:option>
														<form:options items="${titleList}" itemValue="itemKey" itemLabel="itemValue" />
													</form:select>
													<br/>
													<form:errors path="pipbTitle" cssClass="error"/>
												</td>
											</c:if>
											<td>
												Flat :
											</td>
											<td>
												<form:input path="pipbFlat" readonly="true"/>
												<form:errors path="pipbFlat" cssClass="error"/>
											</td>
										</tr>
										<tr>
											<td>
												<c:if test="${!orderamend.brCust}">	
												First Name :
												</c:if>
											</td>
											<td>
												<c:if test="${!orderamend.brCust}">	
												<form:input path="pipbFirstName" readonly="true" cssClass="pipbName"/>
												<form:errors path="pipbFirstName" cssClass="error"/>
												</c:if>
											</td>
											<td>
												Floor :
											</td>
											<td>
												<form:input path="pipbFloor" readonly="true"/>
												<form:errors path="pipbFloor" cssClass="error"/>
											</td>
										</tr>
										<tr>
											<td>
												<c:if test="${!orderamend.brCust}">	
												Last Name :
												</c:if>
											</td>
											<td colspan="2">
												<c:if test="${!orderamend.brCust}">	
												<form:input path="pipbLastName" readonly="true" cssClass="pipbName"/>
												<form:errors path="pipbLastName" cssClass="error"/>
												</c:if>
											</td>
											<td>
												<c:if test="${not empty orderamend.pipbTwoNBWSrdReason}">
													<span style="font-size: 11px; color: red">
													2N Blockwiring case, please check the appointment date.
													<%-- <spring:message code="lts.amend.pipb.cutOver.not.update"/> --%>
													</span>
												</c:if>
											</td>
										</tr>
									</table>
									</div>
								</td>
							</tr>
							<tr class="amendDocRow">
								<td><form:checkbox path="amendDocInd" id="amendDocInd" disabled="${orderamend.pipbAmendAfterCutOver}"/></td>
								<td align="center"><b>Revised Document Upload</b></td>
								<td><form:errors path="amendDocInd" cssClass="error"/></td>
							</tr>
							<tr class="amendDocRow">
								<td></td>
								<td colspan=2>
									<div id="amendDocDiv" style="overflow-x: auto;border-color:#000000 "  class="ui-widget-content">
										<p style="padding-left: 10px">Please upload the documents after <b>Submit</b>.</p>
									</div>
								</td>
							</tr>
							</c:if>
							
							<tr class="categoryRow" id="category1Row">
								<td><form:checkbox path="category1Ind" id="category1Ind" /></td>
								<td align="left" colspan="2" >
									<form:select id="category1Opt" path="category1" disabled="true" cssStyle="width:auto">
										<form:option value="">-- SELECT --</form:option>
										<form:options items="${categoryCodeList}" itemValue="itemKey" itemLabel="itemValue" />
									</form:select>
									<br />
									<form:errors path="category1" cssClass="error" />
								</td>
							</tr>
							<tr class="categoryRow">
								<td></td>
								<td align="left" colspan="2">
									<div id="category1Div">
										<form:textarea path="content1" rows="5" cols="90" disabled="true" />
										<br />
										<form:errors path="content1" cssClass="error" />
									</div>
								</td>
							</tr>
							<tr class="categoryRow" id="category2Row">
								<td><form:checkbox path="category2Ind" id="category2Ind" /></td>
								<td align="left" colspan="2" >
									<form:select id="category2Opt" path="category2"	disabled="true" cssStyle="width:auto">
										<form:option value="">-- SELECT --</form:option>
										<form:options items="${categoryCodeList}" itemValue="itemKey" itemLabel="itemValue" />
									</form:select>
									<br />
									<form:errors path="category2" cssClass="error" />
								</td>
							</tr>
							<tr class="categoryRow">
								<td></td>
								<td align="left" colspan="2">
									<div id="category2Div">
										<form:textarea path="content2" rows="5" cols="90" disabled="true" />
										<br />
										<form:errors path="content2" cssClass="error" />
									</div>
								</td>
							</tr>
							<tr class="categoryRow" id="category3Row">
								<td><form:checkbox path="category3Ind" id="category3Ind" /></td>
								<td align="left" colspan="2" >
									<form:select id="category3Opt" path="category3"	disabled="true" cssStyle="width:auto">
										<form:option value="">-- SELECT --</form:option>
										<form:options items="${categoryCodeList}" itemValue="itemKey" itemLabel="itemValue" />
									</form:select>
									<br />
									<form:errors path="category3" cssClass="error" />
									</td>
							</tr>
							<tr class="categoryRow">
								<td></td>
								<td align="left" colspan="2">
									<div id="category3Div">
										<form:textarea path="content3" rows="5" cols="90" disabled="true" />
										<br />
										<form:errors path="content3" cssClass="error" />
									</div>
								</td>
							</tr>
						</table>
					</div>
					</td>
					<td width="5%">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

	<div id="submitBtnDiv">
		<table width="100%">
			<tr>
				<td align="right">
					<!-- <a href="#" class="nextbutton" id="showhistory">Amend History</a> -->
					<a href="#" class="nextbutton" id="submitBtn" onclick="submitForm()"><div class="button">Submit</div></a>
				</td>
			</tr>
		</table>
	</div>
	
	<div style="display:none">
		<c:forEach var="categoryRemark" items="${categoryRemarkList}">
			<input type="hidden" id="remark${categoryRemark.itemKey}" value="${categoryRemark.itemValue}"/>
		</c:forEach>
	</div>
	
</form:form>
</td></tr>
</table>



<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>

<script type="text/javascript">
	var orderType = "${sessionScope.sessionLtsDummySbOrder.orderType}";
	var preWiringRequired = "${orderamend.preWiringAppntDtl != null}" == "true";
	var installRequired = "${orderamend.coreSrvAppntDtl != null}" == "true";
	var secDelRequired = "${orderamend.secDelAppntDtl != null}" == "true";
	var portLaterRequired = "${orderamend.portLaterAppntDtl != null}" == "true";
	var isAcqPipbOrder = "${isAcqPipbOrder}" == "true";
	var portLaterLeadtime = parseInt("${orderamend.pipbPortLaterleadtime}");
	
	var pipbSrdDaysEnough = "${orderamend.pipbSrdDaysEnough}" == "true";
	var pipbAfterCutOver = "${orderamend.pipbAmendAfterCutOver}" == "true";
	var pipbWqRejectedOrNotExist = "${orderamend.pipbWqRejectedOrNotExist}" == "true";
	var pipbMaxLeadTime = parseInt("${orderamend.pipbMaxLeadTime}");
	
	var pipb2NBWEarliestSrd = "${orderamend.pipbTwoNBWSrd.dateString}";
	
	var installTimeRequired = "${orderamend.coreSrvAppntDtl.timeSlotRequiredInd}" == "true";
	

	function pushTimeSlotOptions(json, lkupType, defaultValue) {
		var slot = '';
		if (lkupType == "P") {
			slot = 'preWiringTime';
		} else if (lkupType == "I") {
			if(orderType == 'SBR' && !installTimeRequired){
				return;
			}
			slot = 'installationTime';
		} else if (lkupType == "S") {
			slot = 'secDelInstallationTime';
		} else if (lkupType == "PORTLATER"){
			slot = 'portLaterTime';
		}
		
		if($('#'+slot).val() == undefined){
			return;
		}

		$('option', '#'+slot).remove();
		$('#'+slot).append(new Option(' -- SELECT --', '', true, false));

		$.each(eval(json), function(i, item) {
			var timeslotoptions = $('#'+slot).attr('options');
			if (item.tsvalue == defaultValue) {
				timeslotoptions[timeslotoptions.length] = new Option(
						item.tsdisplay, item.tsvalue, true, true);
			} else {
				timeslotoptions[timeslotoptions.length] = new Option(
						item.tsdisplay, item.tsvalue, true, false);
			}
		});

		colorTimeSlot(slot);
	}

	function colorTimeSlot(slot) {
		if($("#"+slot).val() == undefined){
			return;
		}
		
		var timeslotlist = document.getElementById(slot);
		var i;
		for (i = 1; i < timeslotlist.length; i++) {//skip first element			
			var slotOption = timeslotlist.options[i];
			var slotType = slotOption.value.split("=")[1];
			if(slotType != null){
				if(slotType=="NS"){ //indicate no resource
					slotOption.style.color="red";
				}else if(slotType=="RS"){//restricted
					slotOption.style.color="silver";
				}else{
					slotOption.style.color="black";
				}
			}
		}
	}

	function refreshTimeSlot(changedSlot) {
		var successInd = "";
		var instDate = $('#installationDate').val();
		var instTime = $('#installationTime').val();
		if (secDelRequired) {
			var secDelInstDate = $('#secDelInstallationDate').val();
			var secDelInstTime = $('#secDelInstallationTime').val();
			if (instDate != "" && (orderType!='SBR' || installTimeRequired)) {
				if (instDate == secDelInstDate) {
					successInd = timeSlotLookup(instDate, "IA", instTime);
					successInd = timeSlotLookup(secDelInstDate, "SA", instTime);
				} else {
					if (secDelInstDate == "") {
						$('#secDelInstallationDate').val(instDate);
						successInd = timeSlotLookup(instDate, "IA", instTime);
						successInd = timeSlotLookup(instDate, "SA", instTime);
					} else {
						successInd = timeSlotLookup(instDate, "I", instTime);
						successInd = timeSlotLookup(secDelInstDate, "S",
								secDelInstTime);
					}
				}
			} else {
				successInd = timeSlotLookup(secDelInstDate, "S", secDelInstTime);
			}
		} else {
			timeSlotLookup(instDate, "I", instTime);
		}
		refreshCutOverTime();
		if (successInd == 'parsererror') {
			alert("Your session has been timed out, please re-login.");
		}
	}

	function timeSlotLookup(date, type, defaultValue) {
		if(orderType == 'SBR' && !installTimeRequired && (type == 'I' || type == 'IA')){
			return;
		}
		
		var requestType = type;
		
		if(orderType == 'SBD'){
			requestType = 'D';
		}
		
		if(type == 'PORTLATER'){
			requestType = 'I';
		}
		
		if (date.length == 10) {
			$.ajax({
				url : "ltsappointmenttimeslotlookup.html",
				type : 'POST',
				data : "date=" + date + "&type=" + requestType + "&amend=true",
				dataType : 'json',
				timeout : 10000,
				success : function(data) {
					//var obj = jQuery.parseJSON(data);
					if (type == "IA" || type == 'D') {
						pushTimeSlotOptions(data, "I", defaultValue);
					} else if (type == "SA") {
						pushTimeSlotOptions(data, "S", defaultValue);
					} else {
						pushTimeSlotOptions(data, type, defaultValue);
					}
					return 1;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					return textStatus;
				},
				complete : function() {
					$.unblockUI();
				},
				beforeSend : function() {
					$.blockUI({
						message : null
					});
				}
			});
		}
	}

	/*function refreshTimeSlotType() {
		if(orderType == 'SBR'){
			return;
		}
		$("#installationTimeType").get(0).selectedIndex = $("#installationTime")
				.get(0).selectedIndex;
		refreshCutOverTime();
	}*/

	function refreshCutOverTime() {
		if(isAcqPipbOrder && pipbAfterCutOver && !pipbWqRejectedOrNotExist){
			return;
		}
		
		var successInd = "";
		var cutOverInd = "${orderamend.coreSrvAppntDtl.cutOverInd}";
		var secDelCutOverInd = "${orderamend.secDelAppntDtl.cutOverInd}";
		if (cutOverInd == "true" && $("#installationDate").val() != ""
				&& $("#installationTime").val() != "") {
			successInd = findCutOverTimeSlot($("#installationDate").val(), $(
					"#installationTime").val(), "C");
		}
		if (secDelRequired) {
			/*$("#secDelInstallationTimeType").get(0).selectedIndex = $(
					"#secDelInstallationTime").get(0).selectedIndex;*/
			if (secDelCutOverInd == "true"
					&& $("#secDelInstallationDate").val() != ""
					&& $("#secDelInstallationTime").val() != "") {
				successInd = findCutOverTimeSlot($("#secDelInstallationDate")
						.val(), $("#secDelInstallationTime").val(), "SC");
			}
		}
		if (successInd == 'parsererror') {
			alert("Your session has been timed out, please re-login.");
		}
	}

	function findCutOverTimeSlot(date, timeSlot, type) {
		if(isAcqPipbOrder && pipbAfterCutOver && !pipbWqRejectedOrNotExist){
			return;
		}
		
		$.ajax({
			url : "ltsappointmenttimeslotlookup.html",
			type : 'POST',
			data : "date=" + date + "&timeSlot=" + timeSlot + "&type=C" + "&amend=true",
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (type == "C") {
					$("#cutOverDate").val(obj.date);
					$("#cutOverTime").val(obj.timeSlot);
				} else if (type == "SC") {
					$("#secDelCutOverDate").val(obj.date);
					$("#secDelCutOverTime").val(obj.timeSlot);
				} else if (type == "PORTLATER"){
					$("#portLaterCutOverDate").val(obj.date);
					$("#portLaterCutOverTime").val(obj.timeSlot);
				}
			},
			complete : function() {
				$.unblockUI();
			},
			beforeSend : function() {
				$.blockUI({
					message : null
				});
			}
		});
	}

	
	function resetInstallationDatePicker(min, max){
		var minDate;
		var maxDate;
		
		if(min == "" || min == undefined){
			minDate = "${orderamend.coreSrvAppntDtl.earliestSRD}";
			if($("#pipbAmendFlatFloorInd").is(":checked") && pipb2NBWEarliestSrd != ''){
				minDate = pipb2NBWEarliestSrd;
			}
		}else{
			minDate = min;
		}
		if(max == "" || max == undefined){
			if(isAcqPipbOrder && !isNaN(pipbMaxLeadTime)){
				maxDate = plusDate('', pipbMaxLeadTime);
			}else{
				maxDate = "+100Y";
			}
		}else{
			maxDate = max;
		}
		
		$('#installationDate').datepicker("destroy");
		$('#installationDate').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : minDate,
			maxDate : maxDate, //Y is year, M is month, D is day  
			yearRange : "0:+100" //the range shown in the year selection box
		});
	}

	function resetPreWiringDatePicker() {
		var preWiringMax;
		var preWiringMin = '${orderamend.preWiringAppntDtl.earliestSRD.leadTime}';
		if ($('#installationDate').val() != "") {
			preWiringMax = $('#installationDate').datepicker('getDate');
		} else {
			preWiringMax = '+100Y';
		}
		$('#preWiringDate').datepicker("destroy");
		$('#preWiringDate').datepicker({
			changeMonth : true,
			changeYear : true,
			defaultDate : preWiringMin,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : preWiringMin,
			maxDate : preWiringMax, //Y is year, M is month, D is day  
			yearRange : '0:+100' //the range shown in the year selection box
		});
	}

	function confirmDisplay(val) {
		if (val == "confirm") {
			$('#cancelMsgBlock').hide();
			if ($("#amendedInd").val() == "false") {
				$('#confirmMsgBlock').hide();
				$('#confirmAppntBtn').attr('disabled', false);
				$('#cancelAppntBtn').attr('disabled', false);
			} else {
				$('#confirmMsgBlock').show();
				$('#confirmAppntBtn').attr('disabled', true);
				$('#cancelAppntBtn').attr('disabled', false);
				$("#installationDate").attr('disabled', true);
				$("#installationTime").attr('disabled', true);
				$("#secDelInstallationDate").attr('disabled', true);
				$("#secDelInstallationTime").attr('disabled', true);
				$("#cutOverDate").attr('disabled', true);
				$("#cutOverTime").attr('disabled', true);
				$("#secDelCutOverDate").attr('disabled', true);
				$("#secDelCutOverTime").attr('disabled', true);
				$("#preWiringDate").attr('disabled', true);
				$("#preWiringTime").attr('disabled', true);
				$("#delayReasonCode").attr('disabled', true);
			}
		} else {
			if (val == "cancel") {
				$('#preBookSerialNum').val("");
			}
			$('#confirmMsgBlock').hide();
			var errorMsg = "${orderamend.errorMsg}";
			if (errorMsg != "") {
				$('#cancelMsgBlock').show();
			}
			$('#confirmAppntBtn').attr('disabled', false);
			$('#cancelAppntBtn').attr('disabled', true);
			$("#secDelInstallationDate").attr('disabled', false);
			$("#secDelInstallationTime").attr('disabled', false);
			$("#cutOverDate").attr('disabled', false);
			$("#cutOverTime").attr('disabled', false);
			$("#secDelCutOverDate").attr('disabled', false);
			$("#secDelCutOverTime").attr('disabled', false);
			$("#preWiringDate").attr('disabled', false);
			$("#preWiringTime").attr('disabled', false);
			$("#delayReasonCode").attr('disabled', false);
			resetFormDisplay();
		}
	}

	function resetFormDisplay() {
		var bbShortage = $("#bbShortageInd").val();
		var bmo = "${orderamend.bmoRemark}";
		if (bbShortage == "true") {
			$("#preBookSerialNum").attr('disabled', true);
			$("#installationDate").attr('disabled', true);
			$("#installationTime").attr('disabled', true);
		} else {
			$("#preBookSerialNum").attr('disabled', false);
			$("#installationDate").attr('disabled', false);
			$("#installationTime").attr('disabled', false);
		}
		if($("#confirmedInd").val() != 'true'){
			if($("#acqOrderCompleted").val() != 'true'){
				resetInstallationDatePicker();
			}
			$('#secDelInstallationDate').datepicker({
				changeMonth : true,
				changeYear : true,
				showButtonPanel : true,
				dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
				minDate : "${orderamend.coreSrvAppntDtl.earliestSRD}",
				maxDate : "+100Y", //Y is year, M is month, D is day  
				yearRange : "0:+100" //the range shown in the year selection box
			});
			resetPortLaterDatePicker();
			resetPreWiringDatePicker();
		}

		if (bmo != "") {
			$("#bmoAlertDiv").show();
		} else {
			$("#bmoAlertDiv").hide();
		}

	}
	
	function checkOtherAmendDisplay() {
		if ($("#category1Ind").is(':checked')) {
			$("#category1Opt").attr('disabled', false);
			$("#content1").attr('disabled', false);
			$("#category1Div").show();
		} else {
			$("#category1Opt").attr('disabled', true);
			$("#content1").attr('disabled', true);
			$("#category1Div").hide();
		}
		if ($("#category2Ind").is(':checked')) {
			$("#category2Opt").attr('disabled', false);
			$("#content2").attr('disabled', false);
			$("#category2Div").show();
		} else {
			$("#category2Opt").attr('disabled', true);
			$("#content2").attr('disabled', true);
			$("#category2Div").hide();
		}
		if ($("#category3Ind").is(':checked')) {
			$("#category3Opt").attr('disabled', false);
			$("#content3").attr('disabled', false);
			$("#category3Div").show();
		} else {
			$("#category3Opt").attr('disabled', true);
			$("#content3").attr('disabled', true);
			$("#category3Div").hide();
		}
	}

	function secDelDateValidator(val) {
		var secDelDate = $("#secDelInstallationDate").val();
		var secDelTime = $("#secDelInstallationTime").val();
		if ($("#installationDate").val() == secDelDate) {
			var selectedTimeSlot = $("#installationTime").val();
			$("#secDelInstallationTime").children().remove();
			$("#secDelInstallationTime").html(
					'<option value="'+ selectedTimeSlot +'" selected=true>'
							+ selectedTimeSlot + '</option>');
		} else if ((secDelDate != "" && secDelTime == "") || val == "SDID") {
			timeSlotLookup(secDelDate, "S");
		}
	}

	function resetPortLaterDatePicker(date){
		var min = plusDate("${orderamend.coreSrvAppntDtl.earliestSRD}", portLaterLeadtime);
		var maxDate = plusDate("", 90);
		
		if(date != '' && date != undefined){
			min = date;
		}else if($("#installationDate").val() != ''){
			if(plusDate($("#installationDate").val(), 0) > plusDate("", 0)){
				min = plusDate($("#installationDate").val(), portLaterLeadtime);
			}else{
				min = plusDate("", portLaterLeadtime);
			}
		}
		
		if(pipbMaxLeadTime != ''){
			maxDate = plusDate("", pipbMaxLeadTime);
		}
		
		$('#portLaterDate').datepicker("destroy");
		$('#portLaterDate').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : min,
			maxDate : maxDate, //Y is year, M is month, D is day  
			yearRange : "0:+100" //the range shown in the year selection box
		});
	}
	
	function resetSecDelDatePicker() {
		$('#secDelInstallationDate').datepicker("destroy");
		var Min = "${orderamend.coreSrvAppntDtl.earliestSRD}";
		if ($('#installationDate').val() != "") {
			Min = $('#installationDate').val();
		}
		$('#secDelInstallationDate').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : Min,
			maxDate : "+100Y", //Y is year, M is month, D is day  
			yearRange : "0:+100" //the range shown in the year selection box
		});
	}

	function openCEKSWindow() {
		//var form = document.forms['createForm'];
		var ceksLink = "ltsceks.html";
		if (ceksLink != null && ceksLink != "") {
			window
					.open(
							ceksLink,
							"_blank",
							"width=550, height=400, resizable=no, toolbar=yes, location=yes, menubar=yes, status=yes, left=100, top=100");
			form.elements['ceksUrl'].value = "";
		}
	}

	function refreshForm(cardnum) {
		document.getElementById('cardnum').value = cardnum;
		if (cardnum.substring(0, 1) == "4") {
			document.getElementById('cardtype').selectedIndex = 1;
		} else if (cardnum.substring(0, 2) == "51"
				|| cardnum.substring(0, 2) == "52"
				|| cardnum.substring(0, 2) == "53"
				|| cardnum.substring(0, 2) == "54"
				|| cardnum.substring(0, 2) == "55") {
			document.getElementById('cardtype').selectedIndex = 2;
		} else if (cardnum.substring(0, 2) == "34"
				|| cardnum.substring(0, 2) == "37") {
			document.getElementById('cardtype').selectedIndex = 3;
		} else {
			document.getElementById('cardtype').selectedIndex = 0;
		}
	}

	function checkOrderCancel() {
		if ($("#cancelOrderInd").is(':checked')) {
			$('#orderCancelDiv').show();
			setAllowAppointment(false);
			setAllowCreditCard(false);
			setAllowAcqPipb(false);
			setAllowDocument(false);
			setAllowOtherCategory(false);
			if($("#isPreInstall").val()=='Y')
			{
				alert("CANCEL PRE-INSTALL + DEL ORDER, IF PRE-INSTALL ORDER COMPLETED, PRE-INSTALL PENALTY WILL BE GENERATED");
			}
		} else {
			$('#orderCancelDiv').hide();
			setAllowAppointment(true);
			setAllowCreditCard(true);
			setAllowAcqPipb(true);
			setAllowDocument(true);
			setAllowOtherCategory(true);
		}

	}
	
	function srvNumChange(){
		if(orderType == 'SBD'){
			$("#serviceType").val("CORE");
			return;
		}
		if ($("#serNum option:selected").text().indexOf("2DEL") != -1){ //Del
			$("#serviceType").val("2DEL");
			$("#installationDate").attr("disabled", true);	
			$("#installationTime").attr("disabled", true);	
			$("#secDelInstallationDate").attr("disabled", false);
			$("#secDelInstallationTime").attr("disabled", false);
		}else if ($("#serNum option:selected").text().indexOf("EYE") != -1
					|| $("#serNum option:selected").text().indexOf("DEL") != -1){ // Eye
			$("#serviceType").val("CORE");
			$("#installationDate").attr("disabled", false);
			$("#installationTime").attr("disabled", false);
			$("#secDelInstallationDate").attr("disabled", false);
			$("#secDelInstallationTime").attr("disabled", false);				
			
		}
	}

	function submitForm() {
		$.blockUI({
			message : null
		});
		$("#submitInd").val("SUBMIT");
		$("#installationDate").attr('disabled', false);
		$("#installationTime").attr('disabled', false);
		$("#preWiringDate").attr('disabled', false);
		$("#preWiringTime").attr('disabled', false);
		$("#secDelInstallationDate").attr('disabled', false);
		$("#secDelInstallationTime").attr('disabled', false);
        $("#thirdPartyCardTrue").attr('disabled', false);
        $("#thirdPartyCardFalse").attr('disabled', false);
		$("#orderamendform").submit();
	}
	
	function setFaxSerialDisplay(){
		if ($("#thirdPartyCardTrue").is(':checked')){
			$("#faxSerial").attr('disabled', false);
			$("#faxSerialDiv").show();
		}
		if ($("#thirdPartyCardFalse").is(':checked')){
			$("#faxSerial").attr('disabled', true);
			$("#faxSerialDiv").hide();
		}
	}

	function setAcqPipbAmendDiffCustName(){
		$("#pipbDiffCustTitle").attr("disabled", !$("#pipbAmendDiffCustInd").is(":checked"));
		$("#pipbDiffCustFirstName").attr("readonly", !$("#pipbAmendDiffCustInd").is(":checked"));
		$("#pipbDiffCustLastName").attr("readonly", !$("#pipbAmendDiffCustInd").is(":checked"));
	}
	
	function setAcqPipbAmendName(){
		/* $("#pipbTitle").attr("disabled", !$("#pipbAmendCustNameInd").is(":checked"));
		$("#pipbFirstName").attr("readonly", !$("#pipbAmendCustNameInd").is(":checked"));
		$("#pipbLastName").attr("readonly", !$("#pipbAmendCustNameInd").is(":checked")); */
		$(".pipbName").attr("readonly", !$("#pipbAmendCustNameInd").is(":checked"));
	}
	
	function setAcqPipbAmendFlatFloor(){
		if($("#confirmedInd").val() != 'true' && $("#acqOrderCompleted").val() != 'true'){
			resetInstallationDatePicker();
		}
		if($("#pipbAmendFlatFloorInd").is(":checked") && pipb2NBWEarliestSrd != ''){
			if($("#installationDate").val() != ''){
				if(plusDate(pipb2NBWEarliestSrd, '') > plusDate($("#installationDate").val(), '')){
					$("#installationDate").css('color', 'red');
				}
			}
		}
		
		$("#pipbFlat").attr("readonly", !$("#pipbAmendFlatFloorInd").is(":checked"));
		$("#pipbFloor").attr("readonly", !$("#pipbAmendFlatFloorInd").is(":checked"));
	}
	
	function checkAmendDocDisplay(){
		if($("#amendDocInd").is(":checked")){
			$("#amendDocDiv").show();
		}else{
			$("#amendDocDiv").hide();
		}
	}
	
	function plusDate(date, val){
		var newDate;
		if(date != ''){
			newDate = $.datepicker.parseDate('dd/mm/yy', date);
		}else{
			newDate = $.datepicker.parseDate('dd/mm/yy', $.datepicker.formatDate('dd/mm/yy', new Date()));
		}
		if(val != ''){
			val = parseInt(val);
			if(isNaN(val)){
				val = 0;
			}
			newDate.setDate(newDate.getDate() + val);
		}
		return newDate;
	}

	function categorySelected(optionId, textAreaId){
		if(isAcqPipbOrder){
			if($("#"+optionId).val() == '305'){
				$("#"+textAreaId).val('(The change of HKT customer should be limited to same document no. only)');
			}
			if($("#"+optionId).val() == '306'){
				$("#"+textAreaId).val('(The change of HKT address should be limited to Flat/Floor only)');
			}
		}
		
		var wqNature = $("#"+optionId).val();
		if($("#remark"+wqNature).length > 0){
			$("#"+textAreaId).val($("#remark"+wqNature).val());
		}
	};
	
	function setAllowAmend(checkboxId, isAllow, defaultAllow, callback){
		if(!defaultAllow){
			isAllow = false;
		}

		$(checkboxId).attr("disabled", !isAllow);
		if($(checkboxId).is(':checked')){
			$(checkboxId).attr("checked", isAllow); 
		}
		if(callback != undefined){
			callback();
		}
	}
	
	function setAllowCancel(isAllow){
		var defaultAllow = "${orderamend.cancelAllowInd}" == "true";
		setAllowAmend("#cancelOrderInd", isAllow, defaultAllow, checkOrderCancel);
	}
	
	function setAllowAppointment(isAllow){
		var defaultAllow = "${orderamend.allowAppointmentAmendInd}" == "true";
		setAllowAmend("#amendAppointmentInd", isAllow, defaultAllow, checkAppointmentDisplay);
	}

	function setAllowCreditCard(isAllow){
		var defaultAllow = "${orderamend.allowCreditCardAmendInd}" == "true";
		setAllowAmend("#amendCreditCardInd", isAllow, defaultAllow, checkCreditCardDisplay);
	}
	
	function setAllowAcqPipb(isAllow){
		var defaultAllow = "${orderamend.allowAcqPipbAmendInd}" == "true";
		setAllowAmend("#amendAcqPipbInd", isAllow, defaultAllow, checkAcqPipbDisplay);
	}
	
	function setAllowDocument(isAllow){
		var defaultAllow = "${orderamend.allowDocAmendInd}" == "true";
		setAllowAmend("#amendDocInd", isAllow, defaultAllow, checkAmendDocDisplay);
	}
	
	function setAllowOtherCategory(isAllow){
		var defaultAllow = "${orderamend.allowOtherCategoryAmendInd}" == "true";
		setAllowAmend("#category1Ind", isAllow, defaultAllow, checkOtherAmendDisplay);
		setAllowAmend("#category2Ind", isAllow, defaultAllow, checkOtherAmendDisplay);
		setAllowAmend("#category3Ind", isAllow, defaultAllow, checkOtherAmendDisplay);
	}
	
	function checkAppointmentDisplay(){
		if ($("#amendAppointmentInd").is(':checked')) {
			$('#appointmentDiv').show();
		} else {
			$('#appointmentDiv').hide();
		}
	}
	
	function checkCreditCardDisplay(){
		if ($("#amendCreditCardInd").is(':checked')) {
			$('#creditCardDiv').show();
		} else {
			$('#creditCardDiv').hide();
		}
	}
	
	function checkAcqPipbDisplay(){
		if($("#amendAcqPipbInd").is(":checked")){
			$("#amendAcqPipbDiv").show();
		}else {
			$("#amendAcqPipbDiv").hide();
		}
	}
	
	$(document).ready(
			function() {			
				resetFormDisplay();
				if($("#confirmedInd").val() != 'true'){
					colorTimeSlot("installationTime");
					if(preWiringRequired){ colorTimeSlot("preWiringTime");}
					if(secDelRequired){ colorTimeSlot("secDelInstallationTime");}
					if(portLaterRequired){ colorTimeSlot("portLaterTime");}
				}
				
				checkOrderCancel();
				
				checkAppointmentDisplay();
				checkCreditCardDisplay();
				checkAcqPipbDisplay();
				
				setAcqPipbAmendName();
				setAcqPipbAmendFlatFloor();
				setAcqPipbAmendDiffCustName();
				checkAmendDocDisplay();
				
				checkOtherAmendDisplay();
				
				if ($("#bbShortageInd").val() == "true") {
					$("#bbShortage_warning").show();
				} else {
					$("#bbShortage_warning").hide();
				}

				if ($("#confirmedInd").val() == "true"
						&& $("#amendedInd").val() == "true") {
					confirmDisplay("confirm");
				} else {
					if ($("#submitInd").val() == "APPOINTMENT_CANCEL") {
						confirmDisplay("cancel");
					} else {
						confirmDisplay("nochange");
					}
				}
				
				if($("#confirmedInd").val() != 'true'){
					refreshCutOverTime();
				}
				
				setAllowCancel(true);
				setAllowAppointment(true);
				setAllowCreditCard(true);
				setAllowOtherCategory(true);
				setAllowAcqPipb(true);
				setAllowDocument(true);
				
				if (!pipbSrdDaysEnough && !pipbAfterCutOver && !pipbWqRejectedOrNotExist) {
					setAllowCancel(false);
					setAllowAppointment(false);
					setAllowCreditCard(false);
					setAllowOtherCategory(false);

				}
				if((!pipbSrdDaysEnough || pipbAfterCutOver) && !pipbWqRejectedOrNotExist){
					setAllowAcqPipb(false);
					setAllowDocument(false);
				}
				

				$("#installationDate").change(function(event) {
					$("#installationDate").css('color', 'black');
					refreshTimeSlot();
					if (secDelRequired) {
						resetSecDelDatePicker();
					}
					if (portLaterRequired){
						resetPortLaterDatePicker();
					}
					if(preWiringRequired){
						resetPreWiringDatePicker();
					}
				});

				$("#preWiringDate").change(function(event) {
					var date = $("#preWiringDate").val();
					$("#preWiringTime").attr('disabled', false);
					resetInstallationDatePicker($("#preWiringDate").datepicker('getDate'), "");
					timeSlotLookup(date, "P");
				});

				$("#secDelInstallationDate").change(function(event) {
					$("#secDelInstallationTime").attr('disabled', false);
					refreshTimeSlot();
				});
				
				$("#portLaterDate").change(function(event) {
					timeSlotLookup($("#portLaterDate").val(), "PORTLATER");
				});
					

				$("#installationTime").change(
						function(event) {
							if (secDelRequired) {
								if ($("#installationDate").val() == $(
										"#secDelInstallationDate").val()) {
									$('#secDelInstallationTime').val(
											$("#installationTime").val());
								}
							}
							if($("#cutOverDate").val() != undefined){
								refreshCutOverTime();
							}
						});

				$("#secDelInstallationTime").change(
						function(event) {
							if ($("#installationDate").val() == $(
									"#secDelInstallationDate").val()) {
								$('#installationTime').val(
										$("#secDelInstallationTime").val());
							}
							if($("#secDelCutOverDate").val() != undefined){
								refreshCutOverTime();
							}
						});

				$("#portLaterTime").change(function(event) {
					findCutOverTimeSlot($("#portLaterDate").val(), $("#portLaterTime").val(), 'PORTLATER');
				});
				
				$("#cutOverTime").change(function(event) {
					$("#secDelCutOverDate").val($("#cutOverDate").val());
					$("#secDelCutOverTime").val($("#cutOverTime").val());
				});

				$("input#confirmAppntBtn").click(function(event) {
					event.preventDefault();
					$("#submitInd").val("APPOINTMENT_CONFIRM");
					$("#installationDate").attr('disabled', false);
					$("#installationTime").attr('disabled', false);
					$("#preWiringDate").attr('disabled', false);
					$("#preWiringTime").attr('disabled', false);
					$("#secDelInstallationDate").attr('disabled', false);
					$("#secDelInstallationTime").attr('disabled', false);
					$("#orderamendform").submit();

				});

				$("input#cancelAppntBtn").click(function(event) {
					$("#preBookSerialNum").attr('disabled', false);
					$("#confirmedInd").val("false");
					$("#submitInd").val("APPOINTMENT_CANCEL");
					$("#orderamendform").submit();
				});

				$("#submit").click(function(event) {
					event.preventDefault();
					$("#submitInd").val("SUBMIT");
					$("#installationDate").attr('disabled', false);
					$("#installationTime").attr('disabled', false);
					$("#preWiringDate").attr('disabled', false);
					$("#preWiringTime").attr('disabled', false);
					$("#secDelInstallationDate").attr('disabled', false);
					$("#secDelInstallationTime").attr('disabled', false);
					$("#orderamendform").submit();
				});

				$($("#cancelOrderInd")).change(function(event) {
					checkOrderCancel();
				});

				$("#amendAppointmentInd").change(function(event) {
					checkAppointmentDisplay();
				});

				$("#amendCreditCardInd").change(function(event) {
					checkCreditCardDisplay();
				});

				$("#amendAcqPipbInd").change(function(event) {
					checkAcqPipbDisplay();
				});
				
				$("#pipbAmendCustNameInd").change(function(event){
					setAcqPipbAmendName();
				});
				
				$("#pipbAmendFlatFloorInd").change(function(event){
					setAcqPipbAmendFlatFloor();
				});
				
				$("#pipbAmendDiffCustInd").change(function(event){
					setAcqPipbAmendDiffCustName();
				});
				
				$("#amendDocInd").change(function(event){
					checkAmendDocDisplay();
				});
				
				$("#category1Ind").change(function(event) {
					checkOtherAmendDisplay();
				});

				$("#category2Ind").change(function(event) {
					checkOtherAmendDisplay();
				});

				$("#category3Ind").change(function(event) {
					checkOtherAmendDisplay();
				});

 				if ($("#channelCd").val()=="RS"){
			        $("#thirdPartyCardTrue").attr('checked', false);
			        $("#thirdPartyCardFalse").attr('checked', true);
			        $("#thirdPartyCardTrue").attr('disabled', true);
			        $("#thirdPartyCardFalse").attr('disabled', true);
				}
				
				if(orderType == 'SBD'){
					$(".amendCreditCardRow").hide();
				}
				
				if(orderType != 'SBA'){
					$(".amendAcqPipbRow").hide();
				}
				
				if ($("#installationDate\\.errors").length >0){
					$('#appointmentDiv').show();
					$("#amendAppointmentInd").attr('checked', true);					
					$("#amendAppointmentInd").attr('disabled', false);
				}
				srvNumChange();
				$("#serNum").change(function(event) {
					srvNumChange();
				});

				var isCallCenterOrPremierTeam = "${isCcPt}" == "true";
				
				if($("#thirdPartyCardFalse").is(':checked')){
					if(isCallCenterOrPremierTeam){
						$(".not3rdpartycc").show();
					}
				}else if(isCallCenterOrPremierTeam){
					$(".not3rdpartycc").hide();
				}
				
				$("#thirdPartyCardTrue").click(function(event) {
					if(isCallCenterOrPremierTeam){
						$(".not3rdpartycc").hide();
					}
				});

				$("#thirdPartyCardFalse").click(function(event) {
					if(isCallCenterOrPremierTeam){
						$(".not3rdpartycc").show();
					}
				});
			    
			    $("#category1Opt").change(function(){
				    categorySelected('category1Opt', 'content1');
			    });

			    $("#category2Opt").change(function(){
				    categorySelected('category2Opt', 'content2');
			    });
			    
			    $("#category3Opt").change(function(){
				    categorySelected('category3Opt', 'content3');
			    });
			});
	
</script>