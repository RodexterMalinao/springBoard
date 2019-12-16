<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<script type="text/javascript"
	src="js/web/lts/ltsbasketservice.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="2" />
</jsp:include>

<div class="cosContent">
	<form:form method="POST" id="ltsBasketServiceForm" name="ltsBasketServiceForm"  commandName="ltsAcqBasketServiceCmd" autocomplete="off">
		<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
		<table width="100%" border="0">
			<tr>
				<c:if test="${not empty requestScope.selectedDevice }">
					<td class="paper_w2 round_10" rowspan="4" valign="top"><img
						src="${requestScope.selectedDevice.imagePath}" width="140">
					</td>
				</c:if>
				<td>
					<table class="paper_w2 round_10" width="100%" border="0">
						<tr>
							<td colspan="2"><c:if
									test="${not empty requestScope.selectedBasket}">
							${requestScope.selectedBasket.html}
						</c:if></td>
						</tr>
						<tr>
							<td>
								<table class="table_style_sb" width="100%" border="0">
									<tr>
										<th width="60%" valign="top">&nbsp;
										</td>
										<th width="20%" valign="top" class="BGgreen2 contenttext"><b><spring:message code="lts.acq.basketService.monthlyFixedTermRate" htmlEscape="false"/></b></th>
										<th width="20%" valign="top" class="BGgreen2 contenttext"><b><spring:message code="lts.acq.basketService.monthToMonthRate" htmlEscape="false"/></b></th>
									</tr>
									<c:if test="${not empty ltsAcqBasketServiceCmd.planItemList}">
										<c:forEach items="${ltsAcqBasketServiceCmd.planItemList}"
											var="planItem" varStatus="status">
											<tr>
												<td valign="top"><form:hidden
														path="planItemList[${status.index}].selected" /> <span
													class="item_detail">${planItem.itemDisplayHtml}</span> <c:if
														test="${not empty planItem.itemAttbs }">
														<c:forEach items="${planItem.itemAttbs }" var="itemAttb"
															varStatus="attbStatus">
															<br>
															<c:choose>
																<c:when test="${itemAttb.attbDesc == 'Password'}">
																	<spring:message code="lts.acq.common.password" htmlEscape="false"/>
																</c:when>
																<c:otherwise>
																	${itemAttb.attbDesc}
																</c:otherwise>
															</c:choose>
													<c:if test="${itemAttb.inputMethod == 'INPUT'}">
																<form:input
																	path="planItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
															</c:if>

															<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																<c:if
																	test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">
																	<form:select
																		path="planItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue">
																		<c:forEach items="${itemAttb.itemAttbInfoList}"
																			var="itemAttbInfo">
																			<form:option value="${itemAttbInfo.attbValue}"
																				label="${itemAttbInfo.attbDesc}" />
																		</c:forEach>
																	</form:select>
																</c:if>
															</c:if>
														</c:forEach>
													</c:if></td>
												<td valign="top" class="itemPrice">
													${planItem.recurrentAmtTxt}</td>
												<td valign="top" class="itemPrice">
													${planItem.mthToMthAmtTxt}</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if
										test="${not empty ltsAcqBasketServiceCmd.installItemList}">
										<c:forEach items="${ltsAcqBasketServiceCmd.installItemList}"
											var="installItem" varStatus="status">
											<tr>
												<td valign="top"><form:hidden
														path="installItemList[${status.index}].selected" /> <span
													class="item_detail">${installItem.itemDisplayHtml}</span></td>
												<td valign="top" class="itemPrice">
													${installItem.recurrentAmtTxt}</td>
												<td valign="top" class="itemPrice">
													${installItem.mthToMthAmtTxt}</td>
											</tr>
										</c:forEach>
									</c:if>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<c:if test="${not empty ltsAcqBasketServiceCmd.contItemSetList}">
				<c:forEach items="${ltsAcqBasketServiceCmd.contItemSetList}"
					var="contItemSet" varStatus="contItemSetStatus">
					<tr>
						<td width="100%" colspan="2">
							<table class="paper_w2 round_10" width="100%" border="0">
								<tr>
									<td>
										<table class="tableGreytext" width="100%" cellspacing="0"
											border="0" bgcolor="white">
											<tr>
												<td colspan="3" class="table_title"><spring:message code="lts.acq.basketService.bundleService" htmlEscape="false"/>&nbsp;&nbsp; <form:errors
														path="contItemSetList[${contItemSetStatus.index}].itemSetId"
														cssClass="error" />
												</td>
											</tr>
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="2">
														<tr>
															<td colspan="2" width="60%" valign="top"
																class="item_title_rp"><c:out
																	value="${contItemSet.displayHtml}" /> <br></td>
															<td width="20%" valign="top" class="BGgreen2 contenttext"><b><spring:message code="lts.acq.basketService.monthlyFixedTermRate" htmlEscape="false"/></b></td>
															<td width="20%" valign="top" class="BGgreen2 contenttext"><b><spring:message code="lts.acq.basketService.monthToMonthRate" htmlEscape="false"/></b></td>
														</tr>
														<c:if test="${not empty contItemSet.itemDetails}">
															<c:forEach items="${contItemSet.itemDetails}"
																var="itemDetail" varStatus="itemDetailStatus">
																<tr>
																	<td valign="top" width="10"><form:checkbox
																			path="contItemSetList[${contItemSetStatus.index}].itemDetails[${itemDetailStatus.index }].selected"
																			disabled="${itemDetail.mdoInd == 'M'}" /></td>
																	<td valign="top" width="60%" class="item_detail">
																		<span class="item_detail">${itemDetail.itemDisplayHtml}</span>
																	</td>
																	<td valign="top" class="itemPrice">
																		${itemDetail.recurrentAmtTxt}</td>
																	<td valign="top" class="itemPrice">
																		${itemDetail.mthToMthAmtTxt}</td>
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
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if
				test="${not empty ltsAcqBasketServiceCmd.moovItemList || not empty ltsAcqBasketServiceCmd.contentItemList}">
				<tr>
					<td width="100%" colspan="2">
						<table class="paper_w2 round_10" width="100%" border="0">
							<tr>
								<td>
									<table class="tableGreytext" width="100%" cellspacing="0"
										border="0">
										<tr>
											<td colspan="3" class="table_title"><div
													id="s_line_text"><spring:message code="lts.acq.basketService.optionalService" htmlEscape="false"/></div>
												<br></td>
										</tr>
										<tr>
											<td>
												<table class="table_style_sb" width="100%" border="0"
													cellspacing="2">
													<tr>
														<th valign="top" width="10">&nbsp;</th>
														<th width="60%" valign="top">&nbsp;</th>
														<th width="20%" valign="top" class="BGgreen2 contenttext"><b><spring:message code="lts.acq.basketService.monthlyFixedTermRate" htmlEscape="false"/></b></th>
														<th width="20%" valign="top" class="BGgreen2 contenttext"><b><spring:message code="lts.acq.basketService.monthToMonthRate" htmlEscape="false"/></b></th>
													</tr>
													<c:if
														test="${not empty ltsAcqBasketServiceCmd.moovItemList}">
														<c:forEach items="${ltsAcqBasketServiceCmd.moovItemList}"
															var="moovItem" varStatus="moovItemStatus">
															<tr>
																<td valign="top" width="10"><form:checkbox
																		path="moovItemList[${moovItemStatus.index }].selected"
																		disabled="${moovItem.mdoInd == 'M'}" /></td>
																<td valign="top"><span class="item_detail">${moovItem.itemDisplayHtml}</span></td>
																<td valign="top" class="itemPrice">
																	${moovItem.recurrentAmtTxt}</td>
																<td valign="top" class="itemPrice">
																	${moovItem.mthToMthAmtTxt}</td>
															</tr>
														</c:forEach>
													</c:if>
													<c:if
														test="${not empty ltsAcqBasketServiceCmd.contentItemList}">
														<c:forEach
															items="${ltsAcqBasketServiceCmd.contentItemList}"
															var="contentItem" varStatus="contentItemStatus">
															<tr>
																<td valign="top" width="10"><form:checkbox
																		path="contentItemList[${contentItemStatus.index }].selected"
																		disabled="${contentItem.mdoInd == 'M'}" /></td>
																<td valign="top"><span class="item_detail">${contentItem.itemDisplayHtml}</span></td>
																<td valign="top" class="itemPrice">
																	${contentItem.recurrentAmtTxt}</td>
																<td valign="top" class="itemPrice">
																	${contentItem.mthToMthAmtTxt}</td>
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
					</td>
				</tr>
			</c:if>
		</table>
	</form:form>

	<c:if test='${not empty requestScope.errorMsgList}'>
		<div id="errorDiv" style="width: 70%;">
			<div id="error_profile" class="ui-widget"
				style="visibility: visible;">

				<div class="ui-state-error ui-corner-all"
					style="margin-top: 20px; padding: 0 .7em;">
					<c:forEach items="${requestScope.errorMsgList}" var="errorMsg">
						<p>
							<span class="ui-icon ui-icon-alert"
								style="float: left; margin-right: .3em;"></span>
						</p>
						<div id="error_profile_msg" class="contenttext">${errorMsg}
						</div>
						<p></p>
					</c:forEach>
				</div>

			</div>
		</div>
	</c:if>


	<table width="100%" border="0" cellspacing="0" class="contenttext">
		<tr>
			<td align="right"><a class="nextbutton" id="submit" href="#"><div
						class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a></td>
		</tr>
	</table>

</div>


<script type='text/javascript'>
	$(ltsbasketservice.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>