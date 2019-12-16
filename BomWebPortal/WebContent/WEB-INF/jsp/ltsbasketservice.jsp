<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltsbasketservice.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
	<jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 3 : 5}" />
</jsp:include>


<form:form method="POST" id="ltsBasketServiceForm" name="ltsBasketServiceForm"  commandName="ltsBasketServiceCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<div id="s_line_text">Basket Service</div>
<table width="98%" border="0" align="center">
	<tr>
		<td>
		<div class="paper_w2 round_10">
		<table width="100%" border="0" align="center" cellpadding="5">
			<tr>
				<td rowspan="4" valign="top">
					<c:if test="${not empty requestScope.selectedDevice && not empty requestScope.selectedDevice.imagePath}">
						<img src="${requestScope.selectedDevice.imagePath}" width="140"> 
					</c:if>
			    </td>
				<td>
					<table width="100%" border="0" class="table_style_sb">
						<tr>
							<th width="60%" valign="top">&nbsp;</th>
							<th width="20%" valign="top"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
							<th width="20%" valign="top"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
						</tr>
						<c:if test="${not empty ltsBasketServiceCmd.planItemList}">
							<c:forEach items="${ltsBasketServiceCmd.planItemList}" var="planItem" varStatus="status">
								<tr>
									<td valign="top">
										<form:hidden path="planItemList[${status.index}].selected"/>
										<span class="item_detail">${planItem.itemDisplayHtml}</span>
										
										<c:if test="${not empty planItem.itemAttbs }">
												<c:forEach items="${planItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
													<br>
													${itemAttb.attbDesc}
													<c:if test="${itemAttb.inputMethod == 'INPUT'}">
														<form:input path="planItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
													</c:if>
													
													<c:if test="${itemAttb.inputMethod == 'SELECT'}">
														<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
															<form:select path="planItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
																<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																	<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																</c:forEach>
															</form:select>
														</c:if>
													</c:if>
												</c:forEach>
											</c:if>
										</td>
										<td valign="top" class="itemPrice">
											${planItem.recurrentAmtTxt}
										</td>
										<td valign="top" class="itemPrice">
											${planItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>
							</c:if>
						<c:if test="${not empty ltsBasketServiceCmd.bbRentalItemList}">
							<c:forEach items="${ltsBasketServiceCmd.bbRentalItemList}" var="bbRentalItem" varStatus="status">
								<tr>
									<td valign="top">
										<form:hidden path="bbRentalItemList[${status.index}].selected"/>
										<span class="item_detail">${bbRentalItem.itemDisplayHtml}</span>
									</td>
									<td valign="top" class="itemPrice">
										${bbRentalItem.recurrentAmtTxt}
									</td>
									<td valign="top" class="itemPrice">
										${bbRentalItem.mthToMthAmtTxt}
									</td>										
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${not empty ltsBasketServiceCmd.installItemList}">
							<c:forEach items="${ltsBasketServiceCmd.installItemList}" var="installItem" varStatus="status">
								<tr>
									<td valign="top">
										<form:hidden path="installItemList[${status.index}].selected"/>
										<span class="item_detail">${installItem.itemDisplayHtml}</span>
									</td>
									<td valign="top" class="itemPrice">
										${installItem.recurrentAmtTxt}
									</td>
									<td valign="top" class="itemPrice">
										${installItem.mthToMthAmtTxt}
									</td>										
								</tr>
							</c:forEach>
						</c:if>	
						<c:if test="${not empty ltsBasketServiceCmd.adminChargeItemList}">
							<c:forEach items="${ltsBasketServiceCmd.adminChargeItemList}" var="adminChargeItem" varStatus="status">
								<tr>
									<td valign="top">
										<form:hidden path="adminChargeItemList[${status.index}].selected"/>
										<span class="item_detail">${adminChargeItem.itemDisplayHtml}</span>
									</td>
									<td valign="top" class="itemPrice">
										${adminChargeItem.recurrentAmtTxt}
									</td>
									<td valign="top" class="itemPrice">
										${adminChargeItem.mthToMthAmtTxt}
									</td>										
								</tr>
							</c:forEach>
						</c:if>								
						<c:if test="${not empty ltsBasketServiceCmd.cancelChargeItemList}">
							<c:forEach items="${ltsBasketServiceCmd.cancelChargeItemList}" var="cancelChargeItem" varStatus="status">
								<tr>
									<td valign="top">
										<form:checkbox path="cancelChargeItemList[${status.index }].selected" disabled="${cancelChargeItem.mdoInd == 'M'}"/>
										<span class="item_detail">${cancelChargeItem.itemDisplayHtml}</span>
									</td>
									<td valign="top" class="itemPrice">
										${cancelChargeItem.recurrentAmtTxt}
									</td>
									<td valign="top" class="itemPrice">
										${cancelChargeItem.mthToMthAmtTxt}
									</td>										
								</tr>
							</c:forEach>
						</c:if>								
					</table>
				</td>
			</tr>
			<c:if test="${not empty ltsBasketServiceCmd.contItemSetList}">
				<c:forEach items="${ltsBasketServiceCmd.contItemSetList}" var="contItemSet" varStatus="contItemSetStatus">
				<tr>
					<td width="100%" colspan="2">
						<div id="s_line_text"><spring:message code="lts.offerDtl.bundleSrv" text="Bundle Service"/></div>
 						<table class="table_style_sb" width="100%" border="0" >
	 						<tr>
								<th width="60%" valign="top"  colspan="2">
									<c:out value="${contItemSet.displayHtml}"/>
									<br>
									<form:errors path="contItemSetList[${contItemSetStatus.index}].itemSetId" cssClass="error" />
								</th>
								<th width="20%" valign="top"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
								<th width="20%" valign="top"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
							</tr>
							<c:if test="${not empty contItemSet.itemDetails}">
								<c:forEach items="${contItemSet.itemDetails}" var="itemDetail" varStatus="itemDetailStatus">
									<tr>
										<td valign="top" width="2%">
											<form:checkbox path="contItemSetList[${contItemSetStatus.index}].itemDetails[${itemDetailStatus.index }].selected" disabled="${itemDetail.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											<span class="item_detail">${itemDetail.itemDisplayHtml}</span>
										</td>
										<td valign="top" class="itemPrice">
											${itemDetail.recurrentAmtTxt}
										</td>
										<td valign="top" class="itemPrice">
											${itemDetail.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
					</td>
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty ltsBasketServiceCmd.deviceRedemSetList}">
				<c:forEach items="${ltsBasketServiceCmd.deviceRedemSetList}" var="deviceRedemSet" varStatus="deviceRedemSetStatus">
				<tr>
					<td width="100%" colspan="2">
						<div id="s_line_text">
							<c:if test="${sessionScope.sessionLtsOrderCapture.selectedBasket.osType == 'IOS'}">
								<spring:message code="lts.offerDtl.iosRedemOption" text="IOS Option"/>
							</c:if>
							<c:if test="${sessionScope.sessionLtsOrderCapture.selectedBasket.osType != 'IOS'}">
								<spring:message code="lts.offerDtl.deviceRedem" text="Redemption Method"/>
							</c:if>
						</div>
 						<table class="table_style_sb" width="100%" border="0" >
	 						<tr>
								<th width="60%" valign="top"  colspan="2">
									<c:out value="${deviceRedemSet.displayHtml}"/>
									<br>
									<form:errors path="deviceRedemSetList[${deviceRedemSetStatus.index}].itemSetId" cssClass="error" />
								</th>
								<th width="20%" valign="top"></th>
								<th width="20%" valign="top"></th>
							</tr>
							<c:if test="${not empty deviceRedemSet.itemDetails}">
								<c:forEach items="${deviceRedemSet.itemDetails}" var="itemDetail" varStatus="itemDetailStatus">
									<tr>
										<td valign="top" width="2%">
											<form:checkbox path="deviceRedemSetList[${deviceRedemSetStatus.index}].itemDetails[${itemDetailStatus.index }].selected" disabled="${itemDetail.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											<span class="item_detail">${itemDetail.itemDisplayHtml}</span>
										</td>
										<td valign="top" class="itemPrice">
										</td>
										<td valign="top" class="itemPrice">
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
					</td>
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty ltsBasketServiceCmd.moovItemList || not empty ltsBasketServiceCmd.contentItemList}">
				<tr>
					<td width="100%" colspan="2">
						<div id="s_line_text"><spring:message code="lts.offerDtl.optionalSrv" text="Optional Service"/></div>
						<table class="table_style_sb" width="100%" border="0" >
							<tr>
								<th width="60%" valign="top" colspan="2">&nbsp;</th>	
								<th width="20%" valign="top"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
								<th width="20%" valign="top"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
							</tr>
							<c:if test="${not empty ltsBasketServiceCmd.moovItemList}" >
								<c:forEach items="${ltsBasketServiceCmd.moovItemList}" var="moovItem" varStatus="moovItemStatus">
									<tr>
										<td valign="top" width="2%">
											<form:checkbox path="moovItemList[${moovItemStatus.index }].selected" disabled="${moovItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top">
											<span class="item_detail">${moovItem.itemDisplayHtml}</span></td>	
										<td valign="top" class="itemPrice">
											${moovItem.recurrentAmtTxt}
										</td>
										<td valign="top" class="itemPrice">
											${moovItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${not empty ltsBasketServiceCmd.contentItemList}" >
								<c:forEach items="${ltsBasketServiceCmd.contentItemList}" var="contentItem" varStatus="contentItemStatus">
									<tr>
										<td valign="top" width="2%">
											<form:checkbox path="contentItemList[${contentItemStatus.index }].selected" disabled="${contentItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top"><span class="item_detail">${contentItem.itemDisplayHtml}</span></td>	
										<td valign="top" class="itemPrice">
											${contentItem.recurrentAmtTxt}
										</td>
										<td valign="top" class="itemPrice">
											${contentItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
					</td>
				</tr>
			</c:if>
		</table>
		</div>
	</td>
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

<table width="100%" border="0" cellspacing="0">
	<tr>
		<td align="right"> 
			<br>
			<div class="button">
				<a href="#" class="nextbutton"><spring:message code="lts.mis.next" text="Next"/></a>
			</div> 
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(ltsbasketservice.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>