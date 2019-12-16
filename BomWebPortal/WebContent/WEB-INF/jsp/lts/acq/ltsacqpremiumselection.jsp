<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<script type="text/javascript"
	src="js/web/lts/acq/ltsacqpremiumselection.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="2" />
</jsp:include>

<div class="cosContent">
	<form:form method="POST" id="ltsPremiumSelectionForm"
		name="ltsPremiumSelectionForm" commandName="ltsAcqPremiumSelectionCmd" autocomplete="off">
		<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
		<form:hidden path="action" />
		<div id="s_line_text"><spring:message code="lts.acq.premiumSelection.premiumSelection" htmlEscape="false"/></div>

		<table width="98%" align="center">
			<tr>
				<td>
					<table width="99%" align="center">
						<tr>
							<td>
								<table width="100%" class="table_style_sb">
									<tr height="25">
										<th colspan="3"><spring:message code="lts.acq.premiumSelection.promotionGift" htmlEscape="false"/></th>
										<th width="20%">&nbsp;</th>
									</tr>

									<tr>
										<td colspan="4">
											<table width="100%" class="table_style_sb">
												<tr>
													<td colspan="2" valign="top" width="80%"
														class="item_title_rp"><spring:message code="lts.acq.premiumSelection.giftCode" htmlEscape="false"/> &nbsp;&nbsp; <form:input
															path="giftCode" /> <a class="nextbutton" id="giftBtn"
														href="#"><div class="button"><spring:message code="lts.acq.premiumSelection.search" htmlEscape="false"/></div></a>
													</td>

													<td valign="top" class="itemPrice" width="20%">&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<c:forEach items="${ltsAcqPremiumSelectionCmd.premiumSetList }"
										varStatus="premiumSetStatus" var="premiumSet">
										<c:if
											test="${not empty premiumSet.itemDetails && premiumSet.itemSetType == 'PREM-GIFT' }">
											<tr>
												<td colspan="4">
													<table width="100%" class="table_style_sb">
														<tr>
															<td colspan="2" valign="top" width="80%"
																class="item_title_rp"><c:if
																	test="${premiumSet.maxQty != 0}">
														<spring:message code="lts.acq.premiumSelection.selectNPremiums" arguments="${premiumSet.maxQty}" htmlEscape="false"/>
														&nbsp;&nbsp; <form:errors
																		path="premiumSetList[${premiumSetStatus.index}].itemSetId"
																		cssClass="error" />
																</c:if></td>
															<td valign="top" class="itemPrice" width="20%">&nbsp;</td>
														</tr>
														<c:forEach items="${premiumSet.itemDetails}"
															var="itemDetail" varStatus="itemDetailStatus">
															<tr>
																<td valign="top" width="10"><form:checkbox
																		path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].selected"
																		disabled="${itemDetail.mdoInd == 'M'}" /></td>
																<td valign="top" width="80%" class="item_detail">
																	${itemDetail.itemDisplayHtml}</td>
																<td valign="top" class="itemPrice" width="20%">
																	${itemDetail.penaltyAmtTxt}</td>
															</tr>
															<c:if test="${not empty itemDetail.itemAttbs }">
																<tr>
																	<td colspan="3"><form:errors path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].itemId" cssClass="error" /></td>
																</tr>
																<c:forEach items="${itemDetail.itemAttbs }" var="itemAttb" varStatus="attbStatus">
																	<tr>
																	<td></td>
																	<td colspan="2">
																		${itemAttb.attbDesc}
																	
																	<c:if test="${itemAttb.inputMethod == 'INPUT'}">
																		<form:input path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].itemAttbs[${attbStatus.index}].attbValue" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].itemAttbs[${attbStatus.index}].attbValue" >
																				<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																					<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																				</c:forEach>
																			</form:select>
																		</c:if>
																	</c:if>
																</c:forEach>
															</c:if>
														</c:forEach>
														<c:if test="${not empty premiumSet.itemSetAttbs}">
															<c:forEach items="${premiumSet.itemSetAttbs}"
																var="itemSetAttb" varStatus="itemSetAttbStatus">
																<c:if test="${ itemSetAttb.attbCode != 'GIFT_CODE' }">
																	<tr>
																		<td colspan="2" valign="top" width="80%"
																			class="item_detail">
																			<table width="100%" border="0" cellspacing="1">
																				<tr>
																					<td width="20%">${itemSetAttb.attbDesc}</td>
																					<td><c:choose>
																							<c:when test="${ itemSetAttb.attbCode == 'MEMBERSHIP' }">
																								<table width="100%" border="0" cellspacing="1">
																									<tr>
																										<td><form:input
																												id="${premiumSet.itemSetId}membershipNum"
																												cssClass="${premiumSet.itemSetId}"
																												path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbValue"
																												maxlength="12" />
																										<td>
																									</tr>
																									<tr>
																										<td><form:input size="4"
																												id="${premiumSet.itemSetId}creditCardPrefix"
																												cssClass="${premiumSet.itemSetId}"
																												path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbValueCcPrefix"
																												maxlength="4" /> &nbsp;XXXXXXXX&nbsp; <form:input
																												size="4"
																												id="${premiumSet.itemSetId}creditCardSuffix"
																												cssClass="${premiumSet.itemSetId}"
																												path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbValueCcSuffix"
																												maxlength="4" /> <form:errors
																												path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbCode"
																												cssClass="error" />
																										<td>
																									</tr>
																								</table>
																							</c:when>
																							<c:when test='${itemSetAttb.attbCode == "CODE"}'>
																								<form:input id="${premiumSet.itemSetId}code"
																									cssClass="${premiumSet.itemSetId}"
																									path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbValue" />
																								<form:errors path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbCode" cssClass="error" />
																							</c:when>
																						</c:choose></td>
																				</tr>
																			</table>
																		</td>

																		<td valign="top" class="itemPrice" width="20%">
																			&nbsp;</td>
																	</tr>
																</c:if>
															</c:forEach>
														</c:if>
													</table>
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td>
					<table width="99%" align="center">
						<tr>
							<td>
								<table width="100%" class="table_style_sb">
									<tr height="25">
										<th colspan="3"><spring:message code="lts.acq.premiumSelection.PCDPremium" htmlEscape="false"/></th>
										<th width="20%">&nbsp;</th>
									</tr>
									<tr>
										<td colspan="4">
											<table width="100%" class="table_style_sb">
												<tr>
													<td colspan="2" valign="top" width="80%"
														class="item_title_rp"><spring:message code="lts.acq.premiumSelection.PCDSBID" htmlEscape="false"/> &nbsp;&nbsp; <form:input cssClass="toUpper" 
															path="pcdSbid" id="pcdSbid" /><a class="nextbutton" id="pcdSbidBtn"
														href="#"><div class="button"><spring:message code="lts.acq.premiumSelection.search" htmlEscape="false"/></div></a> <span class="error">${pcdSbidErrMsg}</span>
													</td>
						
													<td valign="top" class="itemPrice" width="20%">&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
									<c:set var="counter" value="0" />
									
									<c:choose>
						<c:when test="${not empty ltsAcqPremiumSelectionCmd.premiumSetList}">
							<c:forEach items="${ltsAcqPremiumSelectionCmd.premiumSetList }"
								varStatus="premiumSetStatus" var="premiumSet">
								<c:if test="${not empty premiumSet.itemDetails && premiumSet.itemSetType == 'PREM-PCD' }">
													<tr>
														<td colspan="4">
															<table width="100%" class="table_style_sb">
															<c:if test="${counter == 0}">
																<tr>
																	<td colspan="2" valign="top" width="80%"
																		class="item_title_rp"><c:if
																			test="${premiumSet.maxQty != 0}">
														<spring:message code="lts.acq.premiumSelection.selectNPremiums" arguments="${premiumSet.maxQty}" htmlEscape="false"/>
														&nbsp;&nbsp; <form:errors
																				path="premiumSetList[${premiumSetStatus.index}].itemSetId"
																				cssClass="error" />
																		</c:if></td>
																	<td valign="top" class="itemPrice" width="20%">&nbsp;</td>
																</tr>
																<c:set var="counter" value="${counter+1}" /> 
															</c:if>
																<c:forEach items="${premiumSet.itemDetails}"
																	var="itemDetail" varStatus="itemDetailStatus">
																	<tr>
																		<td valign="top" width="10"><form:checkbox
																				path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].selected"
																				disabled="${itemDetail.mdoInd == 'M'}" /></td>
																		<td valign="top" width="80%" class="item_detail">
																			${itemDetail.itemDisplayHtml}</td>
																		<td valign="top" class="itemPrice" width="20%">
																			${itemDetail.penaltyAmtTxt}</td>
																	</tr>
																	
																	<c:if test="${not empty itemDetail.itemAttbs }">
																		<tr>
																			<td colspan="3"><form:errors path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].itemId" cssClass="error" /></td>
																		</tr>
																	
																		<c:forEach items="${itemDetail.itemAttbs }" var="itemAttb" varStatus="attbStatus">
																			<tr>
																			<td></td>
																			<td colspan="2">
																				${itemAttb.attbDesc}
																			
																			<c:if test="${itemAttb.inputMethod == 'INPUT'}">
																				<form:input path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].itemAttbs[${attbStatus.index}].attbValue" />
																			</c:if>
																			
																			<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																				<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																					<form:select path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].itemAttbs[${attbStatus.index}].attbValue" >
																						<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																							<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																						</c:forEach>
																					</form:select>
																				</c:if>
																			</c:if>
																		</c:forEach>
																	</c:if>
																	
																</c:forEach>
																<c:if test="${not empty premiumSet.itemSetAttbs}">
																	<c:forEach items="${premiumSet.itemSetAttbs}"
																		var="itemSetAttb" varStatus="itemSetAttbStatus">
																		<tr class="table_tr">
																			<td colspan="2" valign="top" width="80%"
																				class="item_detail">
																				<table width="100%" border="0" cellspacing="1">
																					<tr>
																						<td width="20%">${itemSetAttb.attbDesc}</td>
																						<td>
																						<c:if test="${ itemSetAttb.attbCode == 'NEW_PCD' }">
																									<form:input
																										id="${premiumSet.itemSetId}pcdSbOrderId"
																										path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbValue" cssClass="pcdSbOrderId"  />
																									<form:input
																										path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbCode" cssClass="error" 
																										 />
																						</c:if>
																						</td>
																					</tr>
																				</table>
																			</td>

																			<td valign="top" class="itemPrice" width="20%">
																				&nbsp;</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</table>
														</td>
													</tr>
		
								</c:if>
							</c:forEach>
						</c:when>
					</c:choose>
									
									
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
					
			<tr>
				<td><c:choose>
						<c:when
							test="${not empty ltsAcqPremiumSelectionCmd.premiumSetList}">
							<c:forEach items="${ltsAcqPremiumSelectionCmd.premiumSetList }"
								varStatus="premiumSetStatus" var="premiumSet">
								<c:if
									test="${not empty premiumSet.itemDetails && premiumSet.itemSetType != 'PREM-GIFT' && premiumSet.itemSetType != 'PREM-PCD' }">
									<table width="99%" align="center">
										<tr>
											<td>
												<table width="100%" class="table_style_sb">
													<tr height="25">
														<th colspan="3"><c:out
																value="${premiumSet.displayHtml}" /></th>
														<th width="20%"><spring:message code="lts.acq.premiumSelection.recommendRetailPrice" htmlEscape="false"/></th>
													</tr>
													<tr>
														<td colspan="4">
															<table width="100%" class="table_style_sb">
																<tr>
																	<td colspan="2" valign="top" width="80%"
																		class="item_title_rp"><c:if
																			test="${premiumSet.maxQty != 0}">
														<spring:message code="lts.acq.premiumSelection.selectNPremiums" arguments="${premiumSet.maxQty}" htmlEscape="false"/>
														&nbsp;&nbsp; <form:errors
																				path="premiumSetList[${premiumSetStatus.index}].itemSetId"
																				cssClass="error" />
																		</c:if></td>
																	<td valign="top" class="itemPrice" width="20%">&nbsp;</td>
																</tr>
																<c:forEach items="${premiumSet.itemDetails}"
																	var="itemDetail" varStatus="itemDetailStatus">
																	<tr>
																		<td valign="top" width="10"><form:checkbox
																				path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].selected"
																				disabled="${itemDetail.mdoInd == 'M'}" /></td>
																		<td valign="top" width="80%" class="item_detail">
																			${itemDetail.itemDisplayHtml}</td>
																		<td valign="top" class="itemPrice" width="20%">
																			${itemDetail.penaltyAmtTxt}</td>
																	</tr>
																																
																	<c:if test="${not empty itemDetail.itemAttbs }">
																		<tr>
																			<td colspan="3"><form:errors path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].itemId" cssClass="error" /></td>
																		</tr>
																	
																		<c:forEach items="${itemDetail.itemAttbs }" var="itemAttb" varStatus="attbStatus">
																			<tr>
																			<td></td>
																			<td colspan="2">
																				${itemAttb.attbDesc}
																			
																			<c:if test="${itemAttb.inputMethod == 'INPUT'}">
																				<form:input path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].itemAttbs[${attbStatus.index}].attbValue" />
																			</c:if>
																			
																			<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																				<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																					<form:select path="premiumSetList[${premiumSetStatus.index}].itemDetails[${itemDetailStatus.index }].itemAttbs[${attbStatus.index}].attbValue" >
																						<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																							<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																						</c:forEach>
																					</form:select>
																				</c:if>
																			</c:if>
																		</c:forEach>
																	</c:if>
																	
																</c:forEach>
																<c:if test="${not empty premiumSet.itemSetAttbs}">
																	<c:forEach items="${premiumSet.itemSetAttbs}"
																		var="itemSetAttb" varStatus="itemSetAttbStatus">
																		<tr>
																			<td colspan="2" valign="top" width="80%"
																				class="item_detail">
																				<table width="100%" border="0" cellspacing="1">
																					<tr>
																						<td width="20%">${itemSetAttb.attbDesc}</td>
																						<td>
																						<c:choose>
																								<c:when
																									test="${ itemSetAttb.attbCode == 'MEMBERSHIP' }">
																									<table width="100%" border="0" cellspacing="1">
																										<tr>
																											<td><form:input
																													id="${premiumSet.itemSetId}membershipNum"
																													cssClass="${premiumSet.itemSetId}"
																													path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbValue"
																													maxlength="12" />
																											<td>
																										</tr>
																										<tr>
																											<td><form:input size="4"
																													id="${premiumSet.itemSetId}creditCardPrefix"
																													cssClass="${premiumSet.itemSetId}"
																													path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbValueCcPrefix"
																													maxlength="4" /> &nbsp;XXXXXXXX&nbsp; <form:input
																													size="4"
																													id="${premiumSet.itemSetId}creditCardSuffix"
																													cssClass="${premiumSet.itemSetId}"
																													path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbValueCcSuffix"
																													maxlength="4" /> <form:errors
																													path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbCode"
																													cssClass="error" />
																											<td>
																										</tr>
																									</table>
																								</c:when>
																								<c:when test='${itemSetAttb.attbCode == "CODE"}'>
																									<form:input id="${premiumSet.itemSetId}code"
																										cssClass="${premiumSet.itemSetId}"
																										path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbValue" />
																									<form:errors path="premiumSetList[${premiumSetStatus.index}].itemSetAttbs[${itemSetAttbStatus.index }].attbCode" cssClass="error" />
																								</c:when>
																							</c:choose>
																						</td>
																					</tr>
																				</table>
																			</td>

																			<td valign="top" class="itemPrice" width="20%">
																				&nbsp;</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									<br>
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>

							<div id="errorDiv" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-highlight ui-corner-all"
									style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-info"
											style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_msg" class="contenttext"><spring:message code="lts.acq.premiumSelection.noPremiumThisBasket" htmlEscape="false"/></div>
									<p></p>
								</div>
							</div>

						</c:otherwise>
					</c:choose></td>
			</tr>
		</table>

	</form:form>

<c:if test='${not empty requestScope.errorMsgList}'> 
	<div id="errorDiv" style="width: 70%;">
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


	<table width="100%" border="0" cellspacing="0" class="contenttext">
		<tr>
			<td align="right"><a class="nextbutton" id="submitBtn" href="#"><div
						class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a></td>
		</tr>
	</table>

</div>

<style>
.pcdSbidErrMsg {
	width: 200px;
    border: 0;
    background-color: #F8F8F8;
}
</style>

<script type="text/javascript">
	$(ltsacqpremiumselection.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>