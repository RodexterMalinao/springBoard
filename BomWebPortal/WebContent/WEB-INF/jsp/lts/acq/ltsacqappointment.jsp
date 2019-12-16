<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="9" />
</jsp:include>

<script src="js/jquery-ui-1.8.16.custom.min.js"></script>
<style type="text/css">
div .ap_row {
	padding: 10px 0 10px 100px;
}

.ap_col1 {
	display: inline-block;
	width: 18%;
	text-align: right;
}

.ap_col2 {
	display: inline-block;
	vertical-align: middle;
}

.desc {
	font-size: 9px;
}
</style>

<div class="cosContent">
	<form:form method="POST" action="#" id="appointmentform"
		name="ltsAcqAppointmentForm" commandName="ltsAcqAppointmentCmd"
		autocomplete="off">
		<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
		<form:hidden id="bbShortageInd" path="bbShortageInd" />
		<form:hidden id="confirmedInd" path="confirmedInd" />
		<form:hidden id="pcdOrderExistInd" path="pcdOrderExistInd" />
		<form:hidden id="submitInd" path="submitInd" />
		<form:hidden id="sharePcdInd" path="sharePcdInd" />
		<form:hidden id="tentativeInd" path="tentativeInd" />
		<form:hidden id="allowAppntInd" path="allowAppntInd" />
		<form:hidden id="dsDoorKnockedInd" path="dsDoorKnockedInd" />
		<input type="hidden" id="isPcdResourceShortage" name="isPcdResourceShortage" value="${isPcdResourceShortage}" />
		<input type="hidden" id="isEyeOrder" name="isEyeOrder" value="${isEyeOrder}" />
		<input type="hidden" id="isDelFree" name="isDelFree" value="${isDelFree}" />
		<input type="hidden" id="isPcdBundleBasket" name="isPcdBundleBasket" value="${isPcdBundleBasket}" />
		<input type="hidden" id="isPcdBundlePremium" name="isPcdBundlePremium" value="${isPcdBundlePremium}" />
		<input type="hidden" id="isPreWiring" name="isPreWiring" value="${isPreWiring}" />
		
		
		<table class="paper_w2 round_10" width="100%" align="center">
			<tr>
				<td>
					<!--content-->
					<div id="s_line_text"><spring:message code="lts.acq.appointment.appointment" arguments="" htmlEscape="false"/></div> <c:if
						test="${sessionScope.sessionLtsAcqOrderCapture.channelDirectSales && !ltsAcqAppointmentCmd.qcPassedInd}">
						<div id="coolingOffDiv" class="ap_row">
							<span class="ap_col1"><spring:message code="lts.acq.appointment.coolingOffPeriod" arguments="" htmlEscape="false"/></span> <span
								class="ap_col2"> <c:if
									test="${!ltsAcqAppointmentCmd.confirmedInd }">
									<form:radiobutton path="waiveCoolingOffInd" value="true"
										disabled="${!ltsAcqAppointmentCmd.dsDoorKnockedInd}"
										label="${waiveVal}" />
									<form:radiobutton path="waiveCoolingOffInd" value="false"
										disabled="${!ltsAcqAppointmentCmd.dsDoorKnockedInd}"
										label="${notWaiveVal}" />
								</c:if> <c:if test="${ltsAcqAppointmentCmd.confirmedInd }">
									<b>
									<spring:message code="${ltsAcqAppointmentCmd.waiveCoolingOffInd? 'lts.acq.appointment.waive' : 'lts.acq.appointment.notWaive'}" arguments="" htmlEscape="false"/>
									</b>
								</c:if> <br> 
									<span class="desc">
									<c:choose>
										<c:when test="${empty ltsAcqAppointmentCmd.earliestCoolOffSRD}">
											<spring:message code="lts.acq.appointment.SRDmustT7forCoolingOffPeriod" arguments=" " htmlEscape="false"/>
										</c:when>
										<c:otherwise>
											<spring:message code="lts.acq.appointment.SRDmustT7forCoolingOffPeriod" arguments="${ltsAcqAppointmentCmd.earliestCoolOffSRD}" htmlEscape="false"/>
										</c:otherwise>
									</c:choose>
									</span>
							</span>
						</div>
						<div id="peLinkDiv" class="ap_row">
							<span class="ap_col1"><spring:message code="lts.acq.appointment.PELink" arguments="" htmlEscape="false"/><span class="contenttext_red">${ltsAcqAppointmentCmd.peLinkMandatory?
									'*' : '' }</span>:
							</span> <span class="ap_col2"> <c:if
									test="${!ltsAcqAppointmentCmd.confirmedInd }">
									<form:radiobutton path="peLinkInd" value="true" label="${yesVal}" />
									<form:radiobutton path="peLinkInd" value="false" label="${noVal}" />
								</c:if> <c:if test="${ltsAcqAppointmentCmd.confirmedInd }">
									<b>${(empty ltsAcqAppointmentCmd.peLinkInd)? 'Not
										Selected': ltsAcqAppointmentCmd.peLinkInd? yesVal : noVal}</b>
								</c:if> <br> 
									<span class="desc">
									<c:choose>
										<c:when test="${empty ltsAcqAppointmentCmd.earliestPeLinkSRD}">
											<spring:message code="lts.acq.appointment.SRDmustT30forPELink" arguments=" " htmlEscape="false"/>										
										</c:when>
										<c:otherwise>
											<spring:message code="lts.acq.appointment.SRDmustT30forPELink" arguments="${ltsAcqAppointmentCmd.earliestPeLinkSRD}" htmlEscape="false"/>										
										</c:otherwise>
									</c:choose>
									</span>
							</span>
						</div>
					</c:if> <c:if
						test="${ltsAcqAppointmentCmd.sharePcdInd && ltsAcqAppointmentCmd.pcdOrderExistInd}">
						<div id="pcdOrderDiv" style="padding: 20px;">
							<table width="80%" border="0" cellspacing="1" cellpadding="3">
								<tr>
									<td width="25%">
										<div align="right"><spring:message code="lts.acq.appointment.springboardPCDOrderID" arguments="" htmlEscape="false"/></div>
									</td>
									<td width="25%"><form:input path="pcdOrderId"
											id="pcdOrderId"
											readonly="${ltsAcqAppointmentCmd.pcdOrderExistInd}" /> <c:if
											test="${!ltsAcqAppointmentCmd.pcdOrderExistInd}">
											<div class="func_button">
												<a id="searchPcdOrderBtn" href="#"><spring:message code="lts.acq.numberSelection.search" arguments="" htmlEscape="false"/></a>
											</div>
											<div class="func_button">
												<a id="clearPcdOrderBtn" href="#"><spring:message code="lts.acq.numberSelection.clear" arguments="" htmlEscape="false"/></a>
											</div>
										</c:if> <br />
									<form:errors path="pcdOrderId" cssClass="error" /></td>
								</tr>
								<tr>
									<td><c:if
											test="${not empty requestScope.retrievePcdSbOrderError}">
											<div id="error_profile" class="ui-widget"
												style="visibility: visible;">
												<div class="ui-state-error ui-corner-all"
													style="margin-top: 20px; padding: 0 .7em;">
													<p>
														<span class="ui-icon ui-icon-alert"
															style="float: left; margin-right: .3em;"></span>
													</p>
													<div id="error_profile_msg" class="contenttext">
														<b>${requestScope.retrievePcdSbOrderError}</b>
													</div>
													<p></p>
												</div>
											</div>
										</c:if></td>
								</tr>
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
									<td colspan="2"><c:if
											test="${not empty sessionScope.sessionLtsAcqOrderCapture.relatedPcdOrder && !ltsAcqAppointmentCmd.pcdOrderExistInd}">
											<c:set var="pcdSbOrder"
												value="${sessionScope.sessionLtsAcqOrderCapture.relatedPcdOrder}" />
											<c:choose>
												<c:when
													test="${!pcdSbOrder.custNotMatch && !pcdSbOrder.iaNotMatch}">
													<div id="newPcdOrderRetrievedDiv" style="display: none;">
														<table width="100%" border="0" cellpadding="3">
															<tr>
																<td width="70%" align="left">
																	<div id="warning" class="ui-widget"
																		style="visibility: visible;">
																		<div class="ui-state-highlight ui-corner-all"
																			style="margin-top: 20px; padding: 0 .7em;">
																			<p>
																				<span class="ui-icon ui-icon-info"
																					style="float: left; margin-right: .3em;"></span>
																			</p>
																			<div id="warning_msg" class="contenttext">
																				<b>Springboard order (${pcdSbOrder.orderId})
																					retrieved.</b> <br> <br> <b>Installation
																					Address : ${pcdSbOrder.fullAddress} </b> <br> <br>
																				<b>Subscribed Bandwidth : ${pcdSbOrder.bandwidth
																					!= null ? pcdSbOrder.bandwidth :
																					(pcdSbOrder.technology == "P" ? "PON" :
																					"")}${pcdSbOrder.bandwidth != null ? "M" : ""}</b>
																			</div>
																			<p></p>
																		</div>
																	</div>
																</td>
																<td>&nbsp;</td>
															</tr>
														</table>
													</div>
												</c:when>
												<c:otherwise>
													<div id="newPcdOrderErrorDiv" style="display: none;">
														<table width="100%" border="0" cellpadding="3">
															<tr>
																<td width="65%" align="left">
																	<div id="warning" class="ui-widget"
																		style="visibility: visible;">
																		<div class="ui-state-error ui-corner-all"
																			style="margin-top: 20px; padding: 0 .7em;">
																			<c:if test="${pcdSbOrder.custNotMatch}">
																				<p>
																					<span class="ui-icon ui-icon-alert"
																						style="float: left; margin-right: .3em;"></span>
																				</p>
																					<div id="warning_msg" class="contenttext">
																						<spring:message code="lts.acq.appointment.orderNotUnderSameCustomer" arguments="${pcdSbOrder.orderId}" htmlEscape="false"/>
																					</div>
																				<p></p>
																			</c:if>
																			<c:if test="${pcdSbOrder.iaNotMatch}">
																				<p>
																					<span class="ui-icon ui-icon-alert"
																						style="float: left; margin-right: .3em;"></span>
																				</p>
																				<div id="warning_msg" class="contenttext">
																					<spring:message code="lts.acq.appointment.orderNotUnderSameAddress" arguments="${pcdSbOrder.orderId}" htmlEscape="false"/>																					
																				</div>
																				<p></p>
																			</c:if>
																		</div>
																	</div>
																</td>
																<td>&nbsp;</td>
															</tr>
														</table>
														<table width="100%" border="0" cellpadding="3">
															<tr>
																<td width="100%" align="left"><b><spring:message code="lts.acq.appointment.subscribedBandwidth" arguments="" htmlEscape="false"/>
																		 ${pcdSbOrder.bandwidth != null ?
																		pcdSbOrder.bandwidth : (pcdSbOrder.technology == "P" ?
																		"PON" : "")}${pcdSbOrder.bandwidth != null ? "M" : ""}</b>
																</td>
															</tr>
															<c:if
																test="${pcdSbOrder.custNotMatch && pcdSbOrder.allowConfirmSameCust}">
																<tr>
																	<td width="100%" align="left">
																		<spring:message code="lts.acq.appointment.confirmOrderUnderSameCustomer" arguments="" htmlEscape="false"/>
																		<span class="contenttext_red">*</span>
																		: &nbsp; <form:checkbox
																			path="confirmSameCustWithPcdOrder" />
																	</td>
																</tr>

															</c:if>
															<c:if
																test="${pcdSbOrder.iaNotMatch && pcdSbOrder.allowConfirmSameIa}">
																<tr>
																	<td width="100%" align="left">
																		<spring:message code="lts.acq.appointment.targetInstallAddress" arguments="" htmlEscape="false"/>
																	 <b>${pcdSbOrder.fullAddress}</b>
																	</td>
																</tr>
																<tr>
																	<td width="100%" align="left"><spring:message code="lts.acq.appointment.confirmOrderUnderSameAddress" arguments="" htmlEscape="false"/><span
																		class="contenttext_red">*</span> : &nbsp; <form:checkbox
																			path="confirmSameIaWithPcdOrder" />
																	</td>
																</tr>
															</c:if>
														</table>
													</div>
												</c:otherwise>
											</c:choose>
										</c:if></td>
								</tr>
							</table>
						</div>
					</c:if>
					
					<c:if test="${technologyChange}">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
							<p>
								<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							</p>
							<div class="contenttext">
								<b><spring:message code="lts.acq.appointment.existingStandaloneEye" arguments="" htmlEscape="false"/></b>
							</div>
							<p></p>
						</div>
					</c:if>					
					<div>
						<div class="ui-widget ui-state-highlight ui-corner-all"
							style="margin-top: 1px; padding: 0 .7em; width: 80%">
							<c:if test="${ltsAcqAppointmentCmd.chkPcdActivationDate}">
								<p>
									<span class="ui-icon ui-icon-info"
										style="float: left; margin-right: .3em;"></span>
								</p>
								<div class="contenttext">
									<b><spring:message code="lts.acq.appointment.pcdActivationDate" arguments="${pcdActivationDate}" htmlEscape="false"/></b>
								</div>
								<p>
								<span class="ui-icon ui-icon-info"
									style="float: left; margin-right: .3em;"></span>
							    </p>
							</c:if>
							<c:if test="${isPreInstall}">
								<p>
									<span class="ui-icon ui-icon-info"
										style="float: left; margin-right: .3em;"></span>
								</p>
								<div class="contenttext">
									<b><spring:message code="lts.acq.appointment.preInstallPortDateSameAsActDate" arguments="" htmlEscape="false"/></b>
								</div>
							</c:if>
							<p>
								<span class="ui-icon ui-icon-info"
									style="float: left; margin-right: .3em;"></span>
							</p>							
							<div class="contenttext">
								<c:if
									test="${!sessionScope.sessionLtsAcqOrderCapture.channelDirectSales}">
									<b><spring:message code="lts.acq.appointment.estimatedEarliestSRD" arguments="" htmlEscape="false"/>
										${ltsAcqAppointmentCmd.installAppntDtl.earliestSRDReason}</b>
								</c:if>
								<c:if
									test="${sessionScope.sessionLtsAcqOrderCapture.channelDirectSales}">
									<b><spring:message code="lts.acq.appointment.estimatedEarliestSRD" arguments="" htmlEscape="false"/>
										${ltsAcqAppointmentCmd.installAppntDtl.earliestSrdDesc}</b>
								</c:if>
							</div>
							<c:if
								test="${ltsAcqAppointmentCmd.portLaterAppntDtl != null && not empty ltsAcqAppointmentCmd.portLaterAppntDtl.earliestSRDReason}">
								<p>
									<span class="ui-icon ui-icon-info"
										style="float: left; margin-right: .3em;"></span>
								</p>
								<div class="contenttext">
									<b><spring:message code="lts.acq.appointment.estimatedEarliestPortingSRD" arguments="" htmlEscape="false"/>
										${ltsAcqAppointmentCmd.portLaterAppntDtl.earliestSRDReason}</b>
								</div>
							</c:if>
							<c:if
								test="${!sessionScope.sessionLtsAcqOrderCapture.channelDirectSales}">
								<c:if
									test="${ltsAcqAppointmentCmd.secDelInstallAppntDtl != null && not empty ltsAcqAppointmentCmd.secDelInstallAppntDtl.earliestSRDReason}">
									<p>
										<span class="ui-icon ui-icon-info"
											style="float: left; margin-right: .3em;"></span>
									</p>
									<div class="contenttext">
										<b><spring:message code="lts.acq.appointment.estimatedEarliest2ndDelSRD" arguments="" htmlEscape="false"/>
											${ltsAcqAppointmentCmd.secDelInstallAppntDtl.earliestSRDReason}</b>
									</div>
								</c:if>
							</c:if>
							<c:if
								test="${ltsAcqAppointmentCmd.preWiringAppntDtl != null && not empty ltsAcqAppointmentCmd.preWiringAppntDtl.earliestSRDReason}">
								<p>
									<span class="ui-icon ui-icon-info"
										style="float: left; margin-right: .3em;"></span>
								</p>
								<div class="contenttext">
									<b><spring:message code="lts.acq.appointment.estimatedEarliestPreWiringDate" arguments="" htmlEscape="false"/>
										${ltsAcqAppointmentCmd.preWiringAppntDtl.earliestSRDReason}</b>
								</div>
							</c:if>
							<p></p>
						</div>
						<c:if test="${ltsAcqAppointmentCmd.bbShortageInd}">
							<div id="bbShortage_warning" style="width: 400px">
								<div id="error_profile" class="ui-widget"
									style="visibility: visible;">
									<div class="ui-state-error ui-corner-all"
										style="margin-top: 1px; padding: 0 .7em;">
										<p>
											<span class="ui-icon ui-icon-alert"
												style="float: left; margin-right: .3em;"></span>
										</p>
										<div id="error_profile_msg" class="contenttext">
											<b><spring:message code="lts.acq.appointment.warningBBShortage" arguments="${bbShortageLeadtime}" htmlEscape="false"/></b>
										</div>
										<p></p>
									</div>
								</div>
							</div>
						</c:if>
					</div>
					<div id="appointmentDiv" style="padding: 20px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr id="serial">
								<td width="25%">
									<div align="right"><spring:message code="lts.acq.appointment.preBookSerialNo" arguments="" htmlEscape="false"/></div>
								</td>
								<td width="25%"><form:input path="preBookSerialNum"
										id="preBookSerialNum" readonly="true" /> <br />
								<form:errors path="preBookSerialNum" cssClass="error" /></td>
							</tr>
							<c:if test="${not empty ltsAcqAppointmentCmd.preWiringAppntDtl}">
								<tr class="preWiring">
									<td>
										<div align="right"><spring:message code="lts.acq.appointment.preWiringDate" arguments="" htmlEscape="false"/></div>
									</td>
									<td><form:input path="preWiringAppntDtl.appntDate"
											maxlength="10" id="preWiringDate" /> <br />
									<form:errors path="preWiringAppntDtl.appntDate"
											cssClass="error" /></td>
								</tr>

								<tr class="preWiring">
									<td>
										<div align="right"><spring:message code="lts.acq.appointment.preWiringTime" arguments="" htmlEscape="false"/></div>
									</td>
									<td colspan="3"><c:if
											test="${!ltsAcqAppointmentCmd.confirmedInd && ltsAcqAppointmentCmd.allowAppntInd }">
											<form:select path="preWiringAppntDtl.appntTimeSlotAndType"
												id="preWiringTime">
												<form:option value=""> <spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/> </form:option>
												<c:forEach var="slot" items="${preWiringTimeSlot}">
													<form:option
														value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}"
														label="${slot.apptTimeSlot}" />
												</c:forEach>
											</form:select>
											<br />
											<form:errors path="preWiringAppntDtl.appntTimeSlotAndType"
												cssClass="error" />
										</c:if> <c:if
											test="${ltsAcqAppointmentCmd.confirmedInd || !ltsAcqAppointmentCmd.allowAppntInd }">
											<form:input path="preWiringAppntDtl.appntTimeSlot"
												id="preWiringTime" readonly="true" />
											<form:hidden path="preWiringAppntDtl.appntTimeSlotAndType"
												id="preWiringTimeType" />
											<br />
											<form:errors path="preWiringAppntDtl.appntTimeSlotAndType"
												cssClass="error" />
										</c:if></td>
								</tr>
							</c:if>
							<tr class="install">
								<td width="25%">
									<div align="right">
										<spring:message code="lts.acq.appointment.targetInstallationDate" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span>:
									</div>
								</td>
								<td><form:input path="installAppntDtl.appntDate"
										id="installationDate" maxlength="10" readonly="true" /> <br />
								<form:errors path="installAppntDtl.appntDate" cssClass="error" />
								</td>
								<c:if test="${ltsAcqAppointmentCmd.installAppntDtl.cutOverInd}">
									<td width="25%">
										<div align="right">
											<spring:message code="lts.acq.appointment.targetDateForSwitching" arguments="" htmlEscape="false"/><span
												class="contenttext_red">*</span> :
										</div>
									</td>
									<td><form:input path="installAppntDtl.cutOverDate"
											id="cutOverDate" readonly="true" /> <form:errors
											path="installAppntDtl.cutOverDate" cssClass="error" /></td>
								</c:if>
							</tr>
							<tr class="install">
								<td width="25%">
									<div align="right">
										<spring:message code="lts.acq.appointment.targetInstallationTime" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span>:
									</div>
								</td>
								<td><c:if
										test="${!ltsAcqAppointmentCmd.confirmedInd && ltsAcqAppointmentCmd.allowAppntInd && !ltsAcqAppointmentCmd.pcdOrderExistInd}">
										<form:select id="installationTime"
											path="installAppntDtl.appntTimeSlotAndType">
											<form:option value=""> <spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/> </form:option>
											<c:forEach var="slot" items="${installationTimeSlot}">
												<form:option
													value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}"
													label="${slot.apptTimeSlot}" />
											</c:forEach>
										</form:select>
										<br />
										<form:errors path="installAppntDtl.appntTimeSlotAndType"
											cssClass="error" />
									</c:if> 
									<c:if
										test="${!ltsAcqAppointmentCmd.confirmedInd && ltsAcqAppointmentCmd.allowAppntInd && ltsAcqAppointmentCmd.pcdOrderExistInd && (isPcdResourceShortage || isPreWiring || isDelFree) }">
										<form:select id="installationTime"
											path="installAppntDtl.appntTimeSlotAndType">
											<form:option value=""> <spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/> </form:option>
											<c:forEach var="slot" items="${installationTimeSlot}">
												<form:option
													value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}"
													label="${slot.apptTimeSlot}" />
											</c:forEach>
										</form:select>
										<br />
										<form:errors path="installAppntDtl.appntTimeSlotAndType"
											cssClass="error" />
									</c:if>
									<c:if
										test="${ltsAcqAppointmentCmd.confirmedInd || !ltsAcqAppointmentCmd.allowAppntInd || ltsAcqAppointmentCmd.pcdOrderExistInd && !isPcdResourceShortage && !isPreWiring && !isDelFree}">
										<form:input path="installAppntDtl.appntTimeSlot"
											id="installationTime" readonly="true" />
										<form:hidden path="installAppntDtl.appntTimeSlotAndType"
											id="installationTimeType" />
										<br />
										<form:errors path="installAppntDtl.appntTimeSlotAndType"
											cssClass="error" />
									</c:if></td>
								<c:if test="${ltsAcqAppointmentCmd.installAppntDtl.cutOverInd}">
									<td width="25%">
										<div align="right">
											<spring:message code="lts.acq.appointment.targetTimeForSwitching" arguments="" htmlEscape="false"/><span
												class="contenttext_red">*</span> :
										</div>
									</td>
									<td><form:input path="installAppntDtl.cutOverTime"
											maxlength="10" id="cutOverTime" readonly="true" /> <form:errors
											path="installAppntDtl.cutOverTime" cssClass="error" /></td>
								</c:if>
							</tr>
							<c:if test="${not empty ltsAcqAppointmentCmd.portLaterAppntDtl}">
								<tr class="portLater">
									<td width="25%">
										<div align="right">
											<spring:message code="lts.acq.appointment.targetPortingDate" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span>
											:
										</div>
									</td>
									<td><form:input path="portLaterAppntDtl.appntDate"
											id="portLaterDate" maxlength="10" readonly="true" /> <br />
									<form:errors path="portLaterAppntDtl.appntDate"
											cssClass="error" /></td>
									<c:if
										test="${ltsAcqAppointmentCmd.portLaterAppntDtl.cutOverInd}">
										<td width="25%">
											<div align="right">
												<spring:message code="lts.acq.appointment.targetDateForSwitching" arguments="" htmlEscape="false"/><span
													class="contenttext_red">*</span> :
											</div>
										</td>
										<td><form:input path="portLaterAppntDtl.cutOverDate"
												id="portLaterCutOverDate" readonly="true" /> <form:errors
												path="portLaterAppntDtl.cutOverDate" cssClass="error" /></td>
									</c:if>
								</tr>
								<tr class="portLater">
									<td width="25%">
										<div align="right">
											<spring:message code="lts.acq.appointment.targetPortingTime" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span>
											:
										</div>
									</td>
									<td><c:if test="${!ltsAcqAppointmentCmd.confirmedInd}">
											<form:select id="portLaterTime"
												path="portLaterAppntDtl.appntTimeSlotAndType">
												<form:option value=""> <spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/> </form:option>
												<c:forEach var="slot" items="${portLaterTimeSlot}">
													<form:option
														value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}"
														label="${slot.apptTimeSlot}" />
												</c:forEach>
											</form:select>
											<br />
											<form:errors path="portLaterAppntDtl.appntTimeSlotAndType"
												cssClass="error" />
										</c:if> <c:if test="${ltsAcqAppointmentCmd.confirmedInd}">
											<form:input path="portLaterAppntDtl.appntTimeSlot"
												id="portLaterTime" readonly="true" />
											<form:hidden path="portLaterAppntDtl.appntTimeSlotAndType"
												id="portLaterTimeType" />
											<br />
											<form:errors path="portLaterAppntDtl.appntTimeSlotAndType"
												cssClass="error" />
										</c:if></td>
									<c:if
										test="${ltsAcqAppointmentCmd.portLaterAppntDtl.cutOverInd}">
										<td width="25%">
											<div align="right">
												<spring:message code="lts.acq.appointment.targetTimeForSwitching" arguments="" htmlEscape="false"/><span
													class="contenttext_red">*</span> :
											</div>
										</td>
										<td><form:input path="portLaterAppntDtl.cutOverTime"
												maxlength="10" id="portLaterCutOverTime" readonly="true" />
											<form:errors path="portLaterAppntDtl.cutOverTime"
												cssClass="error" /></td>
									</c:if>
								</tr>
							</c:if>
							<c:if
								test="${not empty ltsAcqAppointmentCmd.secDelInstallAppntDtl}">
								<tr class="secDel">
									<td colspan="4"><hr></td>
								</tr>
								<tr class="secDel">
									<td width="25%">
										<div align="right">
											<spring:message code="lts.acq.appointment.2ndDelInstallationDate" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span>
											:
										</div>
									</td>
									<td><form:input path="secDelInstallAppntDtl.appntDate"
											id="secDelInstallationDate" maxlength="10" readonly="true" />
										<br />
									<form:errors path="secDelInstallAppntDtl.appntDate"
											cssClass="error" /></td>
									<c:if
										test="${ltsAcqAppointmentCmd.secDelInstallAppntDtl.cutOverInd}">
										<td width="25%">
											<div align="right">
												<spring:message code="lts.acq.appointment.targetDateFor2ndDelSwitching" arguments="" htmlEscape="false"/><span
													class="contenttext_red">*</span> :
											</div>
										</td>
										<td><form:input path="secDelInstallAppntDtl.cutOverDate"
												id="secDelCutOverDate" readonly="true" /> <form:errors
												path="secDelInstallAppntDtl.cutOverDate" cssClass="error" />
										</td>
									</c:if>
								</tr>
								<tr class="secDel">
									<td width="25%">
										<div align="right">
											<spring:message code="lts.acq.appointment.2ndDelInstallationTime" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span>
											:
										</div>
									</td>
									<td><c:if test="${!ltsAcqAppointmentCmd.confirmedInd }">
											<form:select id="secDelInstallationTime"
												path="secDelInstallAppntDtl.appntTimeSlotAndType">
												<form:option value=""> <spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/> </form:option>
												<c:forEach var="slot" items="${secDelInstallationTimeSlot}">
													<form:option
														value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}"
														label="${slot.apptTimeSlot}" />
												</c:forEach>
											</form:select>
											<br />
											<form:errors
												path="secDelInstallAppntDtl.appntTimeSlotAndType"
												cssClass="error" />
										</c:if> <c:if test="${ltsAcqAppointmentCmd.confirmedInd }">
											<form:input path="secDelInstallAppntDtl.appntTimeSlot"
												id="secDelInstallationTime" readonly="true" />
											<br />
											<form:errors
												path="secDelInstallAppntDtl.appntTimeSlotAndType"
												cssClass="error" />
										</c:if></td>
									<c:if
										test="${ltsAcqAppointmentCmd.secDelInstallAppntDtl.cutOverInd}">
										<td width="25%">
											<div align="right">
												<spring:message code="lts.acq.appointment.targetTimeFor2ndDelSwitching" arguments="" htmlEscape="false"/><span
													class="contenttext_red">*</span> :
											</div>
										</td>
										<td><form:input path="secDelInstallAppntDtl.cutOverTime"
												maxlength="10" id="secDelCutOverTime" readonly="true" /> <form:errors
												path="secDelInstallAppntDtl.cutOverTime" cssClass="error" />
										</td>
									</c:if>
								</tr>
							</c:if>
							<tr>
								<td colspan="2">
									<div id="confirmMsgBlock"
										style="float: right; min-width: 75%; display: none;">
										<div id="warning_addr" class="ui-widget"
											style="visibility: visible;">
											<div class="ui-state-highlight ui-corner-all"
												style="margin-top: 1px; padding: 0 .7em;">
												<p>
													<span class="ui-icon ui-icon-info"
														style="float: left; margin-right: .3em;"></span>
												</p>
												<div id="comfirm_msg" class="contenttext">
													<b><spring:message code="lts.acq.appointment.appointmentConfirmed" arguments="" htmlEscape="false"/></b>
												</div>
												<p></p>
											</div>
										</div>
									</div>
									<div id="cancelMsgBlock"
										style="float: right; min-width: 75%; display: none;">
										<div id="error_profile" class="ui-widget"
											style="visibility: visible;">
											<div class="ui-state-error ui-corner-all"
												style="margin-top: 1px; padding: 0 .7em;">
												<p>
													<span class="ui-icon ui-icon-alert"
														style="float: left; margin-right: .3em;"></span>
												</p>
												<div id="cancel_msg" class="contenttext">
													<c:choose>
  														<c:when test="${ltsAcqAppointmentCmd.errorMsg == 'Appointment Failed'}">
  															<b><spring:message code="lts.acq.appointment.appointmentFailed" arguments="" htmlEscape="false"/></b>
  														</c:when>
  														<c:otherwise>
  															<b>${ltsAcqAppointmentCmd.errorMsg}</b>
  														</c:otherwise>
  													</c:choose>													
												</div>
												<p></p>
											</div>
										</div>
									</div>
								</td>
								<td colspan="2">
									<div id="cutOverDiffDiv" class="ui-widget"
										style="display: none;">
										<div class="ui-state-highlight ui-corner-all"
											style="margin-top: 1px; padding: 0 .7em;">
											<p>
												<span class="ui-icon ui-icon-info"
													style="float: left; margin-right: .3em;"></span>
											</p>
											<div class="contenttext">
												<b><spring:message code="lts.acq.appointment.telNoSwitchingDateDiffPortDate" arguments="" htmlEscape="false"/></b>
											</div>
											<p></p>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td></td>
								<td colspan="3">
									<div>
										<input type="button" value='<spring:message code="lts.acq.appointment.confirm" arguments="" htmlEscape="false"/>' id="confirmAppntBtn" />
										<input type="button" value='<spring:message code="lts.acq.appointment.cancel" arguments="" htmlEscape="false"/>' id="cancelAppntBtn" />
										<!-- <div class="func_button">
								<a id="confirmAppntBtn" href="#">Confirm</a>
							</div>
							<div class="func_button">
								<a id="cancelAppntBtn" href="#">Cancel</a>
							</div> -->
										<br />
										<form:errors path="confirmedInd" cssClass="error" />
									</div>
								</td>
							</tr>
						</table>
					</div> <br>
					<table id="bmoRemarkTable" width="100%" border="0" cellspacing="4" cellpadding="4" class="contenttext">
						<tr>
								<td width="25%">
									<div align="right">
										<spring:message code="lts.acq.appointment.BMORemarks" arguments="" htmlEscape="false"/>
									</div>
								</td>
								<td colspan="3">
									<form:textarea path="bmoRemark" rows="5" cols="50%" readonly="true" cssStyle="resize:none;"></form:textarea>
								</td>
							</tr>
						</table>
					
					<div id="s_line_text"><spring:message code="lts.acq.appointment.contactInformation" arguments="" htmlEscape="false"/></div>
					<div>
						<table width="100%" cellspacing="4" cellpadding="4" border="0"
							class="contenttext" id="contact">
							<tbody>
								<tr>
									<td width="25%">
										<div align="right">
											<spring:message code="lts.acq.appointment.installationContactName" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span>
											:
										</div>
									</td>
									<td colspan="3"><form:input path="installationContactName"
											id="installationContactName" maxlength="100" /> <form:errors
											path="installationContactName" cssClass="error" /></td>
								</tr>

								<tr>
									<td width="25%">
										<div align="right">
											<spring:message code="lts.acq.appointment.installationContactNo" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span>
											:
										</div>
									</td>
									<td colspan="3"><form:input path="installationContactNum"
											id="installationContactNum" maxlength="8" /> <form:errors
											path="installationContactNum" cssClass="error" /></td>
									<!-- <td colspan="2">
					<div class="func_button">
						<a id="copyContactNumBtn" href="#">Copy to All</a>
					</div>
				</td> -->
								</tr>
								<tr>
									<td width="25%">
										<div align="right"><spring:message code="lts.acq.appointment.installationContactMobileNo" arguments="" htmlEscape="false"/></div>
									</td>
									<td colspan="3"><form:input
											id="installationMobileSMSAlert"
											path="installationMobileSMSAlert" maxlength="8" /> <form:errors
											path="installationMobileSMSAlert" cssClass="error" /></td>
								</tr>
							</tbody>
						</table>
					</div> <br>
						<!-- BOM2018061 -->
	<c:if test="${not empty ltsAcqAppointmentCmd.epdItemList}">
		<table id="EdpTable"  width="100%" border="0" cellspacing="4" cellpadding="4" class="contenttext">
			<tr>
				<div id="s_line_text"><spring:message code="lts.acq.appointment.weee" arguments="" htmlEscape="false"/></div>
				<td>
				<table width="100%" border="0" cellspacing="1" cellpadding="3">
					<tr>
						<td colspan="4">
							<form:hidden id="defaultContactName" path="defaultContactName"/>
							<form:hidden id="defaultContactNum" path="defaultContactNum"/>
							<div style="margin: auto; width: 100em;">
								<c:if test='${not empty requestScope.errorMsgList}'> 
									<div id="errorDiv" style="width: 50em; padding: 0px 10%;">
									<div id="error_profile" class="ui-widget" style="visibility: visible;">
									<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
										<c:forEach items="${requestScope.errorMsgList}" var="errorMsg">
											<p>
												<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
											</p>
											<div id="error_profile_msg" class="contenttext">
												${errorMsg}
											</div>
											<p></p>
										</c:forEach>
									</div>
									</div>
									</div>
								</c:if>
								<form:errors path="epdItemList" cssClass="error"/>
								<c:forEach items="${ltsAcqAppointmentCmd.epdItemList}" var="epdItem" varStatus="status">
									<div style="padding: 5px 10%">
										<form:checkbox id="${epdItem.itemId}" 
											path="epdItemList[${status.index}].selected" 
											cssClass="epdCheckbox"
											onclick="onClickEpdOption('${epdItem.itemId}')"/>
										${epdItem.itemDesc}
										<br/>
									</div>
									<c:if test="${not empty epdItem.itemAttbs }">
										<div id="attbDiv${epdItem.itemId}" class="epdAttbDiv" style="${epdItem.selected? '' : 'display: none;'} ">
										<c:forEach items="${epdItem.itemAttbs}" var="itemAttb" varStatus="attbStatus">
											<c:if test="${itemAttb.visualInd != 'N'}">
												<div style="padding: 3px 15%">
												
												<span style="display: inline-block; vertical-align: top; margin: 0.3em 0px; width: 12em;">
													${itemAttb.attbDesc}
												</span>
												
												<c:if test="${itemAttb.inputMethod == 'INPUT'}">
													<c:choose>
														<c:when test="${itemAttb.inputFormat == 'DATE'}">
															<form:input path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" 
																id="datepicker${epdItem.itemId}${itemAttb.attbID}"  
																onmousedown="addEpdDatePicker('datepicker${epdItem.itemId}${itemAttb.attbID}', 'installationDate', ${ltsAcqAppointmentCmd.epdLeadDay})"
																readonly="true"/>
														</c:when>
														<c:when test="${itemAttb.inputFormat == 'CONTACT_NAME'}">
															<form:input id="epdContactName" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
															<div class="func_button">
																<a href="#" id="copyContactEpdBtn"><spring:message code="lts.acq.appointment.copy" htmlEscape="false"/></a>
															</div>
														</c:when>
														<c:when test="${itemAttb.inputFormat == 'CONTACT_NUM'}">
															<form:input id="epdContactNum" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
														</c:when>
														<c:when test="${itemAttb.inputFormat == 'REMARK'}">
															<form:textarea rows="2" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
														</c:when>
														<c:otherwise>
															<form:input path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" maxlength="${itemAttb.maxLength}"/>
														</c:otherwise>
													</c:choose>
												</c:if>
												<c:if test="${itemAttb.inputMethod == 'SELECT'}">
													<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
														<form:select path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
															<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
															</c:forEach>
														</form:select>
													</c:if>
												</c:if>
												</div>
											</c:if>
										</c:forEach>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
	</c:if>
	<!-- BOM2018061 END -->
					<div id="s_line_text"><spring:message code="lts.acq.appointment.fieldServiceAddonRemark" arguments="" htmlEscape="false"/></div>
					<div class="ap_row">
						<form:textarea path="remarks" rows="6" cols="100"
							cssStyle="resize:none;"></form:textarea>
						<form:errors path="remarks" cssClass="error" />
					</div> <%-- <c:if test="${sessionScope.sessionLtsAcqOrderCapture.channelDirectSales}">
		<br>
			<div id="s_line_text">QC Remark</div>
			<div class="ap_row">

				<form:textarea path="qcRemarks" rows="10" cols="100%" cssStyle="resize:none;"></form:textarea>
				<form:errors path="qcRemarks" cssClass="error"/>
			</div>
		</c:if> --%> <br>
					<div id="s_line_text"><spring:message code="lts.acq.common.suspendRemark" htmlEscape="false"/></div>
					<div class="ap_row">
						<form:textarea path="suspendRemarks" rows="6" cols="100"
							cssStyle="resize:none;"></form:textarea>
						<form:errors path="suspendRemarks" cssClass="error" />
					</div>
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" class="contenttext">
			<tr>
				<td align="right"><form:errors path="suspendReason"
						cssClass="error" /> <b><spring:message code="lts.acq.common.suspendReason" htmlEscape="false"/> </b> <form:select
						path="suspendReason" id="suspendReason">
						<form:option value="NONE"><spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/></form:option>
						<form:options items="${suspendReasonList}" itemValue="itemKey"
							itemLabel="itemValue" />
					</form:select> <a class="nextbutton" id="suspend" href="#"><div
							class="button"><spring:message code="lts.acq.common.suspend" htmlEscape="false"/></div></a> <a class="nextbutton" id="submit"
					href="#"><div class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a></td>

			</tr>
		</table>

	</form:form>
</div>
<script type="text/javascript">
var preWiringRequired = "${ltsAcqAppointmentCmd.preWiringAppntDtl != null}" == "true";
var installRequired = "${ltsAcqAppointmentCmd.installAppntDtl != null}" == "true";
var secDelRequired = "${ltsAcqAppointmentCmd.secDelInstallAppntDtl != null}" == "true";
var portLaterRequired = "${ltsAcqAppointmentCmd.portLaterAppntDtl != null}" == "true";
var earliestSRDDateString = "${ltsAcqAppointmentCmd.installAppntDtl.earliestSRD.dateString}";
var secDelEarliestSRDDateString = "${ltsAcqAppointmentCmd.secDelInstallAppntDtl.earliestSRD.dateString}";
var prewiringEarliestSRDDateString = preWiringRequired ? "${ltsAcqAppointmentCmd.preWiringAppntDtl.earliestSRD.dateString}" : "";
var portLaterEarliestSRDDateString = portLaterRequired ? earliestSRDDateString : ""; //portLaterSRD based date
var portLaterLeadtime = parseInt("${acqPortLaterleadtime}");
var secDelMustSameDateFlag = "${ltsAcqAppointmentCmd.secDelMustSameDateInd}" == "true";
var maxAppointmentDate = "${ltsAcqAppointmentCmd.maxDate}";
var isChannelDS = "${sessionScope.sessionLtsAcqOrderCapture.channelDirectSales}" == "true";
var dsMinAppointmentDate = "${ltsAcqAppointmentCmd.dsMinDate}";
var prewiringMinDateString = preWiringRequired ? "${ltsAcqAppointmentCmd.preWiringAppntDtl.appntDate}" : "";
var srdMinDateString = installRequired ? "${ltsAcqAppointmentCmd.installAppntDtl.appntDate}" : "";

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

if(dsMinAppointmentDate != "" && isChannelDS){
	earliestSRDDateString = dsMinAppointmentDate;
	secDelEarliestSRDDateString = dsMinAppointmentDate;
}

/* var sharePcdOrderFlag = "${ltsAcqAppointmentCmd.sharePcdInd}" == "true";
var tentativeFlag = "${ltsAcqAppointmentCmd.tentativeInd }" == "true"; */
var sameSecDelDateFlag = false;
var samePreWiringDateFlag = false;

//BOM2018061
function onClickEpdOption(itemId){
		$(".epdCheckbox").not('#'+itemId).attr('checked', false);
		$(".epdAttbDiv").not('#attbDiv'+itemId).hide();
		hideShow("attbDiv"+itemId);
	}
	
function hideShow(id){
	if($("#"+id).css("display") == "none"){
		$("#"+id).show();
	}else{
		$("#"+id).hide();
	}
}

function addEpdDatePicker(id, minDateRefId, minDateLeadDay){
	$('#'+id).datepicker( "destroy");
	
	var minDate = $("#"+minDateRefId).val();
	var maxDate = "+100Y";
	
	if(minDateLeadDay != ''){
		minDate = plusDate(minDate, minDateLeadDay);
	}
	
	$('#'+id).datepicker({
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate: minDate, maxDate: maxDate, //Y is year, M is month, D is day  
		yearRange: "0:+100" //the range shown in the year selection box
	});
}

function plusDate(date, val){
	var newDate;
	if(date != ''){
		newDate = $.datepicker.parseDate('dd/mm/yy', date);
	}else{
		newDate = $.datepicker.parseDate('dd/mm/yy', $.datepicker.formatDate('dd/mm/yy', new Date()));
	}
	if(val != ''){
		newDate.setDate(newDate.getDate() + val);
	}
	return newDate;
}
//BOM2018061 END

function updateFlag(){
	if(secDelRequired && preWiringRequired
			&& $("#installationDate").val() == $("#secDelInstallationDate").val()
			&& $("#installationDate").val() == $("#preWiringDate").val() ){
		sameSecDelDateFlag = true;
		samePreWiringDateFlag = true;
	}else if(secDelRequired
			&& $("#installationDate").val() == $("#secDelInstallationDate").val()){
		sameSecDelDateFlag = true;
		samePreWiringDateFlag = false;
	}else if(preWiringRequired 
			&& $("#installationDate").val() == $("#preWiringDate").val() ){
		sameSecDelDateFlag = false;
		samePreWiringDateFlag = true;
	}else{
		sameSecDelDateFlag = false;
		samePreWiringDateFlag = false;
	}
}

function pushTimeSlotOptions(json, defaultValue, slot){
	console.log(json);
	console.log(slot);
	$('option', '#'+slot).remove();
	//$('option', type).remove();
	$("#"+slot).append(new Option(' <spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/>', '', true, false));
	//$("#"+type).append(new Option('', '', true, false));

	$.each(eval(json), function(i, item) {
		var timeslotoptions = $("#"+slot).attr('options');
		//var timetypeoptions = $("#"+type).attr('options');
		if(item.tsvalue == defaultValue){
			//timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, true);
			console.log("1 " + item.tsdisplay);
			$("#"+slot).append(new Option(item.tsdisplay, item.tsvalue, true, true));
		}else{
			//timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
			console.log(item.tsdisplay);
			console.log(item.tsvalue);
			$("#"+slot).append(new Option(item.tsdisplay, item.tsvalue, true, false));
		}
		//timetypeoptions[timetypeoptions.length] = new Option(item.tstype, item.tstype, true, false);
	});

	colorTimeSlot(slot);
	
	/*if(lkupType = "S"){
		refreshTimeSlotType();
	}*/
}

function colorTimeSlot(slot){
	var timeslotlist=document.getElementById(slot);
	//var slottypelist=document.getElementById(type);
	var i;
	for (i=1;i<timeslotlist.length;i++){//skip first element
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

function timeSlotLookup(date, type, defaultValue){
	var sbuid = $('input[name="sbuid"]').val();
	updateFlag();
	var requestType = (type == 'PORTLATER')? 'NEWACQ' : (type == 'PREWIRING') ? type : (sameSecDelDateFlag || samePreWiringDateFlag)? "ALL":type;
	
	if(date.length == 10){
		$.ajax({
			url : "ltsacqappointmenttimeslotlkup.html?sbuid=" + sbuid,
			type : 'POST',
			data : "date=" + date + "&type=" + requestType ,
			dataType : 'json',
			//timeout : 5000,
			success : function(data){
				if(sameSecDelDateFlag){
					pushTimeSlotOptions(data, defaultValue, "secDelInstallationTime");
					if($("#allowAppntInd").val() == 'true'){
						pushTimeSlotOptions(data, defaultValue,  "installationTime");
					}else{
						$("#secDelInstallationTime").val($("#installationTimeType").val()).attr("disabled", true);
					}
				}else{
					var timeSlotField = '';
					if(type == "NEWACQ"){
						timeSlotField =  "installationTime";
					}else if(type == "PREWIRING"){
						timeSlotField =  "preWiringTime";
					}else if(type == "PORTLATER"){
						timeSlotField =  "portLaterTime";
					}else if(type == "SECDEL"){
						$("#secDelInstallationTime").attr("disabled", false);
						timeSlotField =  "secDelInstallationTime";
					}
					pushTimeSlotOptions(data, defaultValue, timeSlotField);
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
				 $.blockUI({ message: null }); 
			}
		});
	}
}

function findCutOverTimeSlot(date, timeSlot, type){
	var sbuid = $('input[name="sbuid"]').val();
	$.ajax({
		url : "ltsacqappointmenttimeslotlkup.html?sbuid=" + sbuid,
		type : 'POST',
		data : "date=" + date + "&timeSlot=" + timeSlot + "&type=" + type,
		timeout : 5000,
		success : function(data){
			var obj = jQuery.parseJSON(data);
			if(type == "C"){
				$("#cutOverDate").val(obj.date);
				$("#cutOverTime").val(obj.timeSlot);
				checkCutOverDiff();
			}else if(type == "SC"){
				$("#secDelCutOverDate").val(obj.date);
				$("#secDelCutOverTime").val(obj.timeSlot);
				checkCutOverDiff();
			}else if(type == "PC"){
				$("#portLaterCutOverDate").val(obj.date);
				$("#portLaterCutOverTime").val(obj.timeSlot);
				checkCutOverDiff();
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
			 $.blockUI({ message: null }); 
		}
	});
}

function checkCutOverDiff(){
	var diffInd = false;
	if($("#cutOverDate").val() != undefined){
		if($("#cutOverDate").val() != $("#installationDate").val()){
			diffInd = true;
		}
	}
	if($("#portLaterCutOverDate").val() != undefined){
		if($("#portLaterCutOverDate").val() != $("#portLaterDate").val()){
			diffInd = true;
		}
	}
	
	if(diffInd){
		$("#cutOverDiffDiv").show();
	}else{
		$("#cutOverDiffDiv").hide();
	}
}

function resetDatePicker(datepickerFieldId, min, max, defaultDate){
	$('#'+datepickerFieldId).datepicker( "destroy");
	$('#'+datepickerFieldId).datepicker({
		changeMonth: true,	
		changeYear: true,
		defaultDate: defaultDate,
		showButtonPanel: true,
		dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate: min , maxDate: max, //Y is year, M is month, D is day  
		yearRange: '0:+100' //the range shown in the year selection box
	});
}

function plusDate(date, val){
	var newDate;
	if(date != ''){
		newDate = $.datepicker.parseDate('dd/mm/yy', date);
	}else{
		newDate = $.datepicker.parseDate('dd/mm/yy', $.datepicker.formatDate('dd/mm/yy', new Date()));
	}
	if(val != ''){
		newDate.setDate(newDate.getDate() + val);
	}
	return newDate;
}

function setupDateAndDatePicker(){
	if($("#pcdOrderExistInd").val() == "false" && $("#tentativeInd").val() != "true" || $("#pcdOrderExistInd").val() == "true" && $("#isPcdResourceShortage").val() == "true" && $("#tentativeInd").val() != "true" || $("#isDelFree").val() == "true" || preWiringRequired ){
		var minDate = plusDate(earliestSRDDateString, 0); //type=Date
		var earlisetSRD = $.datepicker.parseDate("dd/mm/yy", earliestSRDDateString);
		
		if($('#installationDate').val() != ''){
			if($.datepicker.parseDate("dd/mm/yy", $('#installationDate').val()) < $.datepicker.parseDate("dd/mm/yy",srdMinDateString) && $("#isDelFree").val() == "true"){
				 $('#installationDate').val(srdMinDateString);
			}
			
		}
		
		if( srdMinDateString!='' && $("#isDelFree").val() == "true")
		{
			minDate = earlisetSRD > $.datepicker.parseDate("dd/mm/yy", srdMinDateString) ? earlisetSRD : $.datepicker.parseDate("dd/mm/yy", srdMinDateString);
		}
		
		/*DS: if PE Link min = T+30*/
		if($("[name=peLinkInd]:checked").val() == "true"){
			var peLinkMinDate = plusDate('', 30);
			minDate = earlisetSRD > peLinkMinDate ? earlisetSRD : peLinkMinDate; //type=Date
		}
		else if($("#isEyeOrder").val() == "true" && $("#isPcdResourceShortage").val() == "true" || $("#isDelFree").val() == "true" && $("#isPcdResourceShortage").val() == "true" ){
			var resourceShortageMinDate = plusDate('', 30);
			minDate = earlisetSRD > resourceShortageMinDate ? earlisetSRD : resourceShortageMinDate; //type=Date
		}
		/*DS: if Door knocked and not waive cooling off period, min = T+7*/
		else if($("#dsDoorKnockedInd").val() == "true" && $("[name=waiveCoolingOffInd]:checked").val() == "false"){
			var nonWaiveMinDate = plusDate('', 7);
			minDate = earlisetSRD > nonWaiveMinDate ? earlisetSRD : nonWaiveMinDate; //type=Date
		}
		resetDatePicker('installationDate', minDate, maxAppointmentDate, $('#installationDate').val());
		$("#installationDate").css('color', 'black');
		if($('#installationDate').val() != ''){
			if($.datepicker.parseDate("dd/mm/yy", $('#installationDate').val()) < minDate){
				$("#installationDate").css('color', 'red');
			}
		}
	}
	
	if(secDelRequired){
		if(!secDelMustSameDateFlag){
			var min = earliestSRDDateString;// = $('#installationDate').val() != ''? $('#installationDate').val(): secDelEarliestSRDDateString != ''? secDelEarliestSRDDateString : earliestSRDDateString;
			var parsedInstallDate = $.datepicker.parseDate("dd/mm/yy", $('#installationDate').val());
			var parsedSecDelEarliestSRDDate = $.datepicker.parseDate("dd/mm/yy", secDelEarliestSRDDateString);
			
			if(parsedInstallDate != null && parsedSecDelEarliestSRDDate != null){
				min = parsedInstallDate > parsedSecDelEarliestSRDDate? $('#installationDate').val(): secDelEarliestSRDDateString;
			}else{ 
				if(parsedInstallDate == null){
					min = parsedSecDelEarliestSRDDate != null? secDelEarliestSRDDateString: earliestSRDDateString;
				}else if(parsedSecDelEarliestSRDDate == null){
					min = $('#installationDate').val();
				}
			}
		
			resetDatePicker('secDelInstallationDate', 
					min, 
					maxAppointmentDate, 
					$('#secDelInstallationDate').val());
			//If date out of range set color to red
			$("#secDelInstallationDate").css('color', 'black');
			if($('#secDelInstallationDate').val() != ''){
				if($.datepicker.parseDate("dd/mm/yy", $('#secDelInstallationDate').val()) < $.datepicker.parseDate("dd/mm/yy", min)){
					$("#secDelInstallationDate").css('color', 'red');
				}
			}
		}
		
	}
	if(preWiringRequired && $("#pcdOrderExistInd").val() != "true" ||
			preWiringRequired &&  $("#isDelFree").val() == "true" &&  $("#pcdOrderExistInd").val() == "true" ||
			preWiringRequired &&  $("#isPcdBundleBasket").val() == "true" &&  $("#pcdOrderExistInd").val() == "true" ||
			preWiringRequired &&  $("#isPcdBundlePremium").val() == "true" &&  $("#pcdOrderExistInd").val() == "true" 
			){
		var installDate = '';
		var preWiringMinDate = '';
		
		$("#preWiringDate").css('color', 'black');
		
		if($('#installationDate').val() != ''){
			installDate = $.datepicker.parseDate("dd/mm/yy", $('#installationDate').val());
			installDate.setDate(installDate.getDate() - 1);

			//If date out of range set color to red
			if($.datepicker.parseDate("dd/mm/yy", $('#preWiringDate').val()) >= $.datepicker.parseDate("dd/mm/yy", $('#installationDate').val())){
				$("#preWiringDate").css('color', 'red');
			}
			
		}
		
		if($('#preWiringDate').val() != ''){
			if($.datepicker.parseDate("dd/mm/yy", $('#preWiringDate').val()) < $.datepicker.parseDate("dd/mm/yy",prewiringEarliestSRDDateString) ){
				 $('#preWiringDate').val(prewiringEarliestSRDDateString);
			}
			
			if($.datepicker.parseDate("dd/mm/yy", $('#preWiringDate').val()) < $.datepicker.parseDate("dd/mm/yy",prewiringMinDateString) && $("#isDelFree").val() == "true"){
				 $('#preWiringDate').val(prewiringMinDateString);
			}
		}
		
		preWiringMinDate = prewiringEarliestSRDDateString != '' ? prewiringEarliestSRDDateString : earliestSRDDateString;
		if( prewiringMinDateString!='' && $("#isDelFree").val() == "true" && $.datepicker.parseDate("dd/mm/yy",preWiringMinDate) < $.datepicker.parseDate("dd/mm/yy",prewiringMinDateString))
		{
			preWiringMinDate = $.datepicker.parseDate("dd/mm/yy", prewiringMinDateString);
		}
		
		resetDatePicker('preWiringDate', 
				preWiringMinDate, 
				installDate,//$('#installationDate').val(), 
				$('#preWiringDate').val());
	}
	
	if(portLaterRequired){
		//T+14+2, T=next day of SRD
		var min = $('#installationDate').val() != ''
						? plusDate($('#installationDate').val(), portLaterLeadtime+1)
								: plusDate(portLaterEarliestSRDDateString, portLaterLeadtime+1);
		
		resetDatePicker('portLaterDate', 
				min, 
				maxAppointmentDate, 
				$('#portLaterDate').val());
		//If date out of range set color to red
		$("#portLaterDate").css('color', 'black');
		if($('#portLaterDate').val() != ''){
			if($.datepicker.parseDate("dd/mm/yy", $('#portLaterDate').val()) < min){
				$("#portLaterDate").css('color', 'red');
			}
		}
	}
}

function confirmDisplay(){
	if($("#confirmedInd").val() == "true"){
		$('#confirmMsgBlock').show();
		$('#cancelMsgBlock').hide();
		$('#confirmAppntBtn').attr('disabled', true);
		$('#cancelAppntBtn').attr('disabled', false);
		//$('#confirmAppntBtn').parent().hide();
		//$('#cancelAppntBtn').parent().show();
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
		$("#searchPcdOrderBtn").attr('disabled', true);
		$("#pcdOrderId").attr('disabled', true);
	}else{
		setupDateAndDatePicker();
		if (preWiringRequired && $("#pcdOrderExistInd").val() == "false") colorTimeSlot("preWiringTime");
		if (installRequired) colorTimeSlot("installationTime");
		if (secDelRequired) colorTimeSlot("secDelInstallationTime");
		$('#cancelAppntBtn').attr('disabled', true);
		//$('#cancelAppntBtn').parent().hide();
		if($("#submitInd").val() == "CONFIRM"
			|| $("#submitInd").val() == "CANCEL"){
			//$('#preBookSerialNum').val("");
			$('#confirmMsgBlock').hide();
			$('#cancelMsgBlock').show();
			$('#confirmAppntBtn').attr('disabled', false);
			$('#cancelAppntBtn').attr('disabled', true);
			//$('#confirmAppntBtn').parent().show();
			//$('#cancelAppntBtn').parent().hide();
			$("#secDelInstallationDate").attr('disabled', false);
			$("#secDelInstallationTime").attr('disabled', false);
			$("#cutOverDate").attr('disabled', false);
			$("#cutOverTime").attr('disabled', false);
			$("#secDelCutOverDate").attr('disabled', false);
			$("#secDelCutOverTime").attr('disabled', false);
			$("#searchPcdOrderBtn").attr('disabled', false);
			$("#pcdOrderId").attr('disabled', false);
			//resetFormDisplay();
		}
	}
}

$(document).ready(function() {
	updateFlag();
	confirmDisplay();
	
	
	
	if ($("#sharePcdInd").val() == "true") {
		$('#withPcdOrderDiv').show();
		$('#newPcdOrderRetrievedDiv').show();
		$("#newPcdOrderNotFoundDiv").show();
		$("#newPcdOrderErrorDiv").show();
	}
	else {
		$('#withPcdOrderDiv').hide();
		$('#newPcdOrderRetrievedDiv').hide();
		$("#newPcdOrderNotFoundDiv").hide();
		$("#newPcdOrderErrorDiv").hide();
	}
	
	$("#installationDate").change(function(){
		if(secDelMustSameDateFlag && $("#secDelInstallationDate").val() != undefined){
			$("#secDelInstallationDate").val($("#installationDate").val());
			updateFlag();
		}
		if(sameSecDelDateFlag){
			timeSlotLookup($("#secDelInstallationDate").val(), "SECDEL", '' );
		}
		console.log("change");
		timeSlotLookup($("#installationDate").val(), "NEWACQ", '' );
		setupDateAndDatePicker();
	});
	
	$("#installationTime").change(function(event){
		if(sameSecDelDateFlag){
			$('#secDelInstallationTime').val($("#installationTime").val());
			if($("#secDelCutOverDate").val() != undefined){
				findCutOverTimeSlot($("#secDelInstallationDate").val(), $("#secDelInstallationTime").val(), "SC");
			}
		}
		if($("#cutOverDate").val() != undefined){
			findCutOverTimeSlot($("#installationDate").val(), $("#installationTime").val(), "C");
		}
	});

	$("#secDelInstallationDate").change(function(){
		if(sameSecDelDateFlag && $("#allowAppntInd").val() == 'true'){
			timeSlotLookup($("#installationDate").val(), "NEWACQ", '' );
		}
		timeSlotLookup($("#secDelInstallationDate").val(), "SECDEL", '' );
		setupDateAndDatePicker();
	});

	$("#secDelInstallationTime").change(function(event){
		if(sameSecDelDateFlag){
			if($("#allowAppntInd").val() == 'true'){
				$('#installationTime').val($("#secDelInstallationTime").val());
			}else{
				$("#secDelInstallationTime").val($("#installationTimeType").val()).attr("disabled", true);
			}
			
			if($("#cutOverDate").val() != undefined){
				findCutOverTimeSlot($("#installationDate").val(), $("#installationTime").val(), "C");
			}
		}
		if($("#secDelCutOverDate").val() != undefined){
			findCutOverTimeSlot($("#secDelInstallationDate").val(), $("#secDelInstallationTime").val(), "SC");
		}
	});

	$("#portLaterDate").change(function(){
		timeSlotLookup($("#portLaterDate").val(), "PORTLATER", '' );
		setupDateAndDatePicker();
	});
	
	$("#portLaterTime").change(function(event){
		if($("#portLaterCutOverDate").val() != undefined){
			findCutOverTimeSlot($("#portLaterDate").val(), $("#portLaterTime").val(), "PC");
		}
	});
	
	$("#preWiringDate").change(function(){
		if(preWiringRequired && $("#pcdOrderExistInd").val() != "true" ||
				preWiringRequired &&  $("#isDelFree").val() == "true" &&  $("#pcdOrderExistInd").val() == "true" ||
				preWiringRequired &&  $("#isPcdBundleBasket").val() == "true" &&  $("#pcdOrderExistInd").val() == "true" ||
				preWiringRequired &&  $("#isPcdBundlePremium").val() == "true" &&  $("#pcdOrderExistInd").val() == "true" 
		){
			timeSlotLookup($("#preWiringDate").val(), "PREWIRING", '' );
			setupDateAndDatePicker();
		}
	});
	
	$("#confirmAppntBtn").click(function(event) {
		event.preventDefault();
		$("#submitInd").val("CONFIRM");
		$("#installationDate").attr('disabled', false);
		$("#installationTime").attr('disabled', false);
		$("#preWiringDate").attr('disabled', false);
		$("#preWiringTime").attr('disabled', false);
		$("#secDelInstallationDate").attr('disabled', false);
		$("#secDelInstallationTime").attr('disabled', false);
		$("#appointmentform").submit();
		
	});
	
	$("#cancelAppntBtn").click(function(event) {
		event.preventDefault();
		$("#preBookSerialNum").attr('disabled', false);
		$("#confirmedInd").val("false");
			$("#submitInd").val("CANCEL");
		$("#appointmentform").submit();
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
		$("#pcdOrderId").attr('disabled', false);
		$("#appointmentform").submit();
	});
	
	$("#suspend").click(function(event){
		event.preventDefault();
		$("#submitInd").val("SUSPEND");
		$("#appointmentform").submit();
	});

	$('#copyContactNumBtn').click(function(event) {
		event.preventDefault();
		var num = $('#installationContactNum').val();
		$('#installationMobileSMSAlert').val(num);
		$('#customerContactMobileNum').val(num);
	});
	
	$("#searchPcdOrderBtn").click(function(event){
		event.preventDefault();
		$("#submitInd").val("SEARCHPCD");
		$("#appointmentform").submit();
	});
	
	$("#clearPcdOrderBtn").click(function(event){
		event.preventDefault();
		$("#submitInd").val("CLEARPCD");
		$("#appointmentform").submit();
	});

	$("[name=waiveCoolingOffInd]").change(function(event){
		setupDateAndDatePicker();
		/* if($("[name=waiveCoolingOffInd]:checked").val() == "false"){
			alert("SRD must > T+7 for not waive Cooling-off Period case");
		} */
	});
	
	$("[name=peLinkInd]").change(function(event){
		setupDateAndDatePicker();
		/* if($("[name=peLinkInd]:checked").val() == "true"){
			alert("SRD must > T+30 for PE Link case");
		} */
	});
	
	//BOM2018061
	$('a#copyContactEpdBtn').click(function(event) {
		event.preventDefault();
		
		if(confirm('Are you sure to copy customer name and contact number?')){
			if($('#defaultContactName').val() != ''){
				$('#epdContactName').val($('#defaultContactName').val());
			}
			if($('#defaultContactNum').val() != ''){
				$('#epdContactNum').val($('#defaultContactNum').val());
			}
		}
		
	});
	//BOM2018061 END
});



</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>