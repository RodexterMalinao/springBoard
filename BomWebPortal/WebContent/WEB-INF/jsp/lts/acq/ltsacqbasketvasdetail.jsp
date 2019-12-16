<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<script type="text/javascript" src="js/web/lts/acq/ltsacqbasketvasdetail.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp" >
    <jsp:param name="step" value="2" />
</jsp:include>

<div class="cosContent">
<form:form method="POST" id="ltsBasketVasDetailForm" name="ltsBasketVasDetailForm"  commandName="ltsAcqBasketVasDetailCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="formAction" />
<div id="s_line_text"><spring:message code="lts.acq.basketVasDetail.valueAddedService" htmlEscape="false"/></div>
<table class="paper_w2 round_10" width="100%">
	<tr> 
		<td>
			<div id="approvalRemarkDiv" style="display: none;">
				<table width="100%" border="0" class="contenttext" cellspacing="20">
					<tr>
						<td align="right">
							<a id="approvalRemarkBtn" href="#" class="nextbutton">Approval Remark</a> 
						</td>
					</tr>
				</table>
			</div>
			
			<c:if test="${ not empty ltsAcqBasketVasDetailCmd.bvasItemList}">
			<table width="100%" border="0" class="contenttext">
				<tr>
					<td colspan="3">
						<div id="s_line_text"><spring:message code="lts.acq.basketVasDetail.basketBundleVAS" htmlEscape="false"/></div>
						<table width="100%" cellspacing="0" border="0" >
							<tr>
						 		<td>
		  							<table width="100%" border="0" cellspacing="1" cellpadding="1" class="table_style_sb">							
										<tr>
											<th valign="top" width="10">&nbsp;</th>
											<th valign="top" width="60%">&nbsp;</th>
											<th align="center" class="BGgreen2 contenttext" width="20%">
												<b><spring:message code="lts.acq.basketVasDetail.monthlyFixedTermRate" htmlEscape="false"/></b>
											</th>
											<th align="center" class="BGgreen2 contenttext" width="20%">
												<b><spring:message code="lts.acq.basketVasDetail.monthToMonthRate" htmlEscape="false"/></b> 
											</th>
										</tr>
										<c:forEach items="${ltsAcqBasketVasDetailCmd.bvasItemList}" var="bvasItem" varStatus="status">
											<tr>
												<td valign="top">
													&nbsp;
												</td>
												<td valign="top">
													${bvasItem.itemDisplayHtml}
													
													<c:if test="${not empty bvasItem.itemAttbs }">
														<c:forEach items="${bvasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
																<form:input path="bvasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
															</c:if>
															
															<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																	<form:select path="bvasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
																		<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																			<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																		</c:forEach>
																	</form:select>
																</c:if>
															</c:if>
														</c:forEach>
													</c:if>
															
												</td>
												<td align="center" valign="top" class="itemPrice">
													<c:out value="${bvasItem.recurrentAmtTxt}" default="NA"/>
												</td>
												<td align="center" valign="top" class="itemPrice">
													<c:out value="${bvasItem.mthToMthAmtTxt}" default="NA"/>
												</td>									
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<br>
			</c:if>
			<table width="100%" border="0" class="contenttext">
				<tr>
					<td colspan="3">
						<c:if test="${ not empty ltsAcqBasketVasDetailCmd.hotVasItemList || not empty ltsAcqBasketVasDetailCmd.otherVasItemList}">
							<div id="s_line_text"><spring:message code="lts.acq.basketVasDetail.additionalVAS" htmlEscape="false"/></div>
							<table width="100%" cellspacing="0" border="0" >
								<tr>
							 		<td>
			  							<table width="100%" border="0" cellspacing="1" cellpadding="1" class="table_style_sb">							
											<tr>
												<th valign="top" width="10">&nbsp;</th>
												<th valign="top" width="60%">&nbsp;</th>
												<th align="center" class="BGgreen2 contenttext" width="20%">
													<b><spring:message code="lts.acq.basketVasDetail.monthlyFixedTermRate" htmlEscape="false"/></b>
												</th>
												<th align="center" class="BGgreen2 contenttext" width="20%">
													<b><spring:message code="lts.acq.basketVasDetail.monthToMonthRate" htmlEscape="false"/></b> 
												</th>
											</tr>
											
											<c:if test="${not empty ltsAcqBasketVasDetailCmd.hotVasItemList}">
												<c:forEach items="${ltsAcqBasketVasDetailCmd.hotVasItemList}" var="hotVasItem" varStatus="status">
													<tr>
														<td valign="top">
															<form:checkbox cssClass="hotVasItem" path="hotVasItemList[${status.index}].selected" disabled="${hotVasItem.mdoInd == 'M'}" />
														</td>
														<td valign="top" class="item_detail">
															${hotVasItem.itemDisplayHtml}
															
															<c:if test="${not empty hotVasItem.itemAttbs }">
																<c:forEach items="${hotVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
																		<form:input path="hotVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="hotVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
																				<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																					<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																				</c:forEach>
																			</form:select>
																		</c:if>
																	</c:if>
																</c:forEach>
															</c:if>
															
														</td>
														<td align="center" valign="top" class="itemPrice">
															${hotVasItem.recurrentAmtTxt}
														</td>
														<td align="center" valign="top" class="itemPrice">
															${hotVasItem.mthToMthAmtTxt}
														</td>									
													</tr>
												</c:forEach>
											</c:if>
										</table>
										<c:if test="${ not empty ltsAcqBasketVasDetailCmd.otherVasItemList}">
											<table width="100%" border="0" cellspacing="1" cellpadding="1" class="contenttext">
												<tr>
													<td colspan="4">													
														<div id="showOtherVasBtnDiv" style="text-align: right;">
															<div class="func_button">
									                        	<a id="showOtherVasBtn" style="color:white" href="#"><spring:message code="lts.acq.basketVasDetail.showOtherVAS" htmlEscape="false"/></a>
									                        </div>
														</div>
														<div id="hideOtherVasBtnDiv" style="text-align: right; display: none;">
															<div class="func_button">
									                        	<a id="hideOtherVasBtn" style="color:white" href="#"><spring:message code="lts.acq.basketVasDetail.hideOtherVAS" htmlEscape="false"/></a>
									                        </div>
														</div>
													</td>
												</tr>
											</table>
											<table id="otherVasTable" width="100%" border="0" cellspacing="1" cellpadding="1" class="contenttext"  style="display: none;">
												<c:forEach items="${ltsAcqBasketVasDetailCmd.otherVasItemList}"  var="otherVasItem" varStatus="status" >
													<tr>
														<td valign="top" width="10">
															<form:checkbox cssClass="otherVasItem" path="otherVasItemList[${status.index}].selected" disabled="${otherVasItem.mdoInd == 'M'}"/>
														</td>
														<td valign="top" width="60%" class="item_detail">
															${otherVasItem.itemDisplayHtml}
															<c:if test="${not empty otherVasItem.itemAttbs }">
																<c:forEach items="${otherVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
																		<form:input path="otherVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="otherVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
																				<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																					<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																				</c:forEach>
																			</form:select>
																		</c:if>
																	</c:if>
																</c:forEach>
															</c:if>
														</td>
														<td align="center" valign="top" class="itemPrice" width="20%">
															${otherVasItem.recurrentAmtTxt}
														</td>												
														<td align="center" valign="top" class="itemPrice" width="20%">
															${otherVasItem.mthToMthAmtTxt}
														</td>
													</tr>	
												</c:forEach>
											</table>
										</c:if>
										<c:if test="${ not empty ltsAcqBasketVasDetailCmd.otherVasComtPeriodItemList}">
											<table width="100%" border="0" cellspacing="1" cellpadding="1" class="contenttext">
												<tr>
													<td colspan="4">													
														<div id="showOtherComtPeriodVasBtnDiv" style="text-align: right;">
															<div class="func_button">
									                        	<a id="showOtherComtPeriodVasBtn" style="color:white" href="#"><spring:message code="lts.acq.basketVasDetail.showOtherCommitPeriodVAS" htmlEscape="false"/></a>
									                        </div>
														</div>
														<div id="hideOtherComtPeriodVasBtnDiv" style="text-align: right; display: none;">
															<div class="func_button">
									                        	<a id="hideOtherComtPeriodVasBtn" style="color:white" href="#"><spring:message code="lts.acq.basketVasDetail.hideOtherCommitPeriodVAS" htmlEscape="false"/></a>
									                        </div>
														</div>
													</td>
												</tr>
											</table>
											<table id="otherComtPeriodVasTable" width="100%" border="0" cellspacing="1" cellpadding="1" class="contenttext"  style="display: none;">
												<c:forEach items="${ltsAcqBasketVasDetailCmd.otherVasComtPeriodItemList}"  var="otherComtPeriodVasItem" varStatus="status" >
													<tr>
														<td valign="top" width="10">
															<form:checkbox cssClass="otherVasItem" path="otherVasComtPeriodItemList[${status.index}].selected" disabled="${otherComtPeriodVasItem.mdoInd == 'M'}"/>
														</td>
														<td valign="top" width="60%" class="item_detail">
															${otherComtPeriodVasItem.itemDisplayHtml}
															<c:if test="${not empty otherComtPeriodVasItem.itemAttbs }">
																<c:forEach items="${otherComtPeriodVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
																		<form:input path="otherVasComtPeriodItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="otherVasComtPeriodItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
																				<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																					<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																				</c:forEach>
																			</form:select>
																		</c:if>
																	</c:if>
																</c:forEach>
															</c:if>
														</td>
														<td align="center" valign="top" class="itemPrice" width="20%">
															${otherComtPeriodVasItem.recurrentAmtTxt}
														</td>												
														<td align="center" valign="top" class="itemPrice" width="20%">
															${otherComtPeriodVasItem.mthToMthAmtTxt}
														</td>
													</tr>	
												</c:forEach>
											</table>
										</c:if>
									</td>
								</tr>
							</table>
						</c:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<c:if test="${not empty ltsAcqBasketVasDetailCmd.iddVasItemList || not empty ltsAcqBasketVasDetailCmd.e0060VasItemList}">
	<br>
	<form:hidden path="optOutIdd" />
	<form:hidden path="optOut0060e" />
	<table class="paper_w2 round_10" width="100%">
		<tr>
			<td >
				<div id="s_line_text"><spring:message code="lts.acq.basketVasDetail.IDDeverywhere" htmlEscape="false"/></div>
				<table width="100%" cellspacing="0" border="0" >
					<tr>
						 <td width="1%">&nbsp;</td>
						 <td>
	   						<table width="100%" border="0" cellspacing="1" cellpadding="1" class="table_style_sb">
								<tr>
									<th valign="top" width="10">&nbsp;</th>
									<th valign="top" width="60%">&nbsp;</th>
									<th align="center" valign="top" class="BGgreen2 contenttext" width="20%">
										<b><spring:message code="lts.acq.basketVasDetail.monthlyFixedTermRate" htmlEscape="false"/></b>
									</th>
									<th align="center" valign="top" class="BGgreen2 contenttext" width="20%">
										<b><spring:message code="lts.acq.basketVasDetail.monthToMonthRate" htmlEscape="false"/></b> 
									</th>
								</tr>
								
								<c:forEach items="${ltsAcqBasketVasDetailCmd.iddVasItemList}" var="iddVasItem" varStatus="status">
									<tr>
										<td valign="top" width="10">
											<form:checkbox path="iddVasItemList[${status.index}].selected" cssClass="iddItem" disabled="${iddVasItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" width="60%" class="item_detail">
											${iddVasItem.itemDisplayHtml}
											
											<c:if test="${not empty iddVasItem.itemAttbs }">
												<c:forEach items="${iddVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
														<c:choose>
															<c:when test="${not empty itemAttb.maxLength}"	>												
																<form:input path="iddVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" maxlength="${itemAttb.maxLength}" />
															</c:when>
															<c:otherwise>
																<form:input path="iddVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
															</c:otherwise>
														</c:choose>
														<form:errors path="iddVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" cssClass="error" />
													</c:if>
													
													<c:if test="${itemAttb.inputMethod == 'SELECT'}">
														<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
															<form:select path="iddVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
																<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																	<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																</c:forEach>
															</form:select>
														</c:if>
													</c:if>
												</c:forEach>
											</c:if>												
										</td>
										<td align="center" valign="top" class="itemPrice" width="20%">
											${iddVasItem.recurrentAmtTxt}
										</td>												
										<td align="center" valign="top" class="itemPrice" width="20%">
											${iddVasItem.mthToMthAmtTxt}
										</td>
									</tr>									
								</c:forEach>
								
								<c:forEach items="${ltsAcqBasketVasDetailCmd.e0060VasItemList}" var="e0060VasItem" varStatus="status">
									<tr>
										<td valign="top" width="10">
											<form:checkbox path="e0060VasItemList[${status.index}].selected" cssClass="e0060Item" disabled="${e0060VasItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" width="60%" class="item_detail">
											${e0060VasItem.itemDisplayHtml}
											
											<c:if test="${not empty e0060VasItem.itemAttbs }">
												<c:forEach items="${e0060VasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
														<c:choose>
															<c:when test="${not empty itemAttb.maxLength}"	>												
																<form:input path="e0060VasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" maxlength="${itemAttb.maxLength}" />
															</c:when>
															<c:otherwise>
																<form:input path="e0060VasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
															</c:otherwise>
														</c:choose>
													</c:if>
													
													<c:if test="${itemAttb.inputMethod == 'SELECT'}">
														<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
															<form:select path="e0060VasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
																<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																	<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																</c:forEach>
															</form:select>
														</c:if>
													</c:if>
												</c:forEach>
											</c:if>												
										</td>
										<td align="center" valign="top" class="itemPrice" width="20%">
											${e0060VasItem.recurrentAmtTxt}
										</td>												
										<td align="center" valign="top" class="itemPrice" width="20%">
											${e0060VasItem.mthToMthAmtTxt}
										</td>
									</tr>	
								</c:forEach>																
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty ltsAcqBasketVasDetailCmd.smartWrtySetList}">
	<br>
	
	<table class="paper_w2 round_10" width="100%">
		<tr> 
			<td>
			     <div id="s_line_text"><spring:message code="lts.acq.basketVasDetail.smartWarranty" htmlEscape="false"/></div>
		   		 <table width="100%" border="0" cellspacing="1" cellpadding="1" class="table_style_sb">
		   		 <tr>
						<td width="1%">&nbsp;</td>
					 	<td>
		   		        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="table_style_sb">
	 					<tr>
	 					            <th valign="top" width="10">&nbsp;</th>
	 					            <th valign="top" width="60%">&nbsp;</th>
	 					            <th align="center" valign="top" class="BGgreen2 contenttext" width="20%"><spring:message code="lts.acq.basketVasDetail.monthlyFixedTermRate" htmlEscape="false"/></th> 
									<th align="center" valign="top" class="BGgreen2 contenttext" width="20%"><spring:message code="lts.acq.basketVasDetail.monthToMonthRate" htmlEscape="false"/></th>
					     </tr>
								<c:forEach items="${ltsAcqBasketVasDetailCmd.smartWrtySetList}" var="smartWrtySet" varStatus="setStatus">						
									<c:if test="${not empty smartWrtySet.itemDetails}">
										<tr>
											<td colspan="2" valign="top" class="item_title_rp">
												<c:if test="${smartWrtySet.maxQty != 0}">
													<spring:message code="lts.acq.basketVasDetail.plsSelectNVASItem" arguments="${smartWrtySet.maxQty}" htmlEscape="false"/>
													&nbsp;&nbsp; <form:errors path="smartWrtySetList[${setStatus.index}].itemSetId" cssClass="error" />
												</c:if>
											</td>
 											<td align="center" valign="top" class="itemPrice"></td> 
											<td align="center" valign="top" class="itemPrice"></td>
										</tr>
										<c:forEach items="${smartWrtySet.itemDetails}" var="smartWrtyItem" varStatus="itemStatus">
											<tr>
												<td valign="top">
													<form:checkbox path="smartWrtySetList[${setStatus.index}].itemDetails[${itemStatus.index }].selected"/>
												</td>
												<td valign="top" class="item_detail">
													${smartWrtyItem.itemDisplayHtml}
												</td>
 												<td align="center" valign="top" class="itemPrice">
													<c:out value="${smartWrtyItem.recurrentAmtTxt}" default=""/>
 												</td> 
												<td align="center" valign="top" class="itemPrice">
													<c:out value="${smartWrtyItem.mthToMthAmtTxt} " default=""/>
												</td>									
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</table>
							</td>
						</tr>
						</table>
						</td>
						</tr>
					</table>
</c:if>

<c:if test="${not empty ltsAcqBasketVasDetailCmd.bundleVasSetList}">
	<br>
	
	<table width="98%" align="center">
		<tr> 
			<td>
				<div class="paper_w2 round_20">
					<br>
					<table width="98%" border="0" align="center">
						<tr> 
			                <td>
			                	<div id="s_line_text"><spring:message code="lts.acq.basketVasDetail.bundleVAS" htmlEscape="false"/></div>
			                </td>
			         	</tr>
						<tr>
							 <td>
		   						<table width="100%" border="0" class="table_style_sb">
	 							<tr>
									<th colspan="2" width="60%">&nbsp;</th>
 									<th width="20%"><spring:message code="lts.acq.basketVasDetail.monthlyFixedTermRate" htmlEscape="false"/></th>
									<th width="20%"><spring:message code="lts.acq.basketVasDetail.monthToMonthRate" htmlEscape="false"/></th>
								</tr>
								<c:forEach items="${ltsAcqBasketVasDetailCmd.bundleVasSetList}" var="bundleVasSet" varStatus="setStatus">						
									<c:if test="${not empty bundleVasSet.itemDetails}">
										<tr>
											<td colspan="2" valign="top" class="item_title_rp">
												<c:if test="${bundleVasSet.maxQty != 0}">
													<spring:message code="lts.acq.basketVasDetail.plsSelectNVASItem" arguments="${bundleVasSet.maxQty}" htmlEscape="false"/>
													&nbsp;&nbsp; <form:errors path="bundleVasSetList[${setStatus.index}].itemSetId" cssClass="error" />
												</c:if>
											</td>
 											<td align="center" valign="top" class="itemPrice"></td> 
											<td align="center" valign="top" class="itemPrice"></td>
										</tr>
										<c:forEach items="${bundleVasSet.itemDetails}" var="bundleVasItem" varStatus="itemStatus">
											<tr>
												<td valign="top" width="2%">
													<form:checkbox cssClass="bundleVasItem" path="bundleVasSetList[${setStatus.index}].itemDetails[${itemStatus.index }].selected"/>
												</td>
												<td valign="top" class="item_detail">
													${bundleVasItem.itemDisplayHtml}
													<c:if test="${not empty bundleVasItem.itemAttbs }">
														<c:forEach items="${bundleVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
																<form:input path="bundleVasSetList[${setStatus.index}].itemDetails[${itemStatus.index }].itemAttbs[${attbStatus.index}].attbValue" />
															</c:if>
															
															<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																	<form:select path="bundleVasSetList[${setStatus.index}].itemDetails[${itemStatus.index }].itemAttbs[${attbStatus.index}].attbValue" >
																		<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																			<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																		</c:forEach>
																	</form:select>
																</c:if>
															</c:if>
														</c:forEach>
													</c:if>
												</td>
												<td align="center" valign="top" class="itemPrice">
													<c:out value="${bundleVasItem.recurrentAmtTxt}" default=""/>
 												</td> 
												<td align="center" valign="top" class="itemPrice">
													<c:out value="${bundleVasItem.mthToMthAmtTxt} " default=""/>
												</td>									
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</table>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty ltsAcqBasketVasDetailCmd.peFreeItemList || not empty ltsAcqBasketVasDetailCmd.peSocketItemList}">
	<br>
	<table class="paper_w2 round_10" width="100%">
		<tr>
			<td >
				<div id="s_line_text"> <spring:message code="lts.acq.basketVasDetail.parallelPhoneLine" htmlEscape="false"/> </div>
				<table width="100%" cellspacing="0" border="0" >
					<tr>
						<td width="1%">&nbsp;</td>
					 	<td>
	  						<table width="100%" border="0" cellspacing="1" cellpadding="1" class="table_style_sb">
	  							<tr>
	  								<th valign="top" width="10">&nbsp;</th>
									<th valign="top" width="60%">&nbsp;</th>
									<th align="center" valign="top" class="BGgreen2 contenttext" width="20%">
										<b><spring:message code="lts.acq.basketVasDetail.monthlyFixedTermRate" htmlEscape="false"/></b>
									</th>
									<th align="center" valign="top" class="BGgreen2 contenttext" width="20%">
										<b><spring:message code="lts.acq.basketVasDetail.monthToMonthRate" htmlEscape="false"/></b>
									</th>
								</tr>
								<c:forEach items="${ltsAcqBasketVasDetailCmd.peFreeItemList}" var="peFreeItem" varStatus="status">
									<tr>
										<td valign="top">
											<form:checkbox path="peFreeItemList[${status.index}].selected" disabled="${peFreeItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											${peFreeItem.itemDisplayHtml}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${peFreeItem.recurrentAmtTxt}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${peFreeItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>
								<c:forEach items="${ltsAcqBasketVasDetailCmd.peSocketItemList}" var="peSocketItem" varStatus="status">
									<tr>
										<td valign="top">
											<form:checkbox path="peSocketItemList[${status.index}].selected" disabled="${peSocketItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											${peSocketItem.itemDisplayHtml} 
											
											<form:select path="peSocketItemList[${status.index}].selectedQty">
												<form:option value="1" label="1" />
												<form:option value="2" label="2" />
												<form:option value="3" label="3" />
											</form:select>
										</td>
										<td align="center" valign="top" class="itemPrice">
											${peSocketItem.recurrentAmtTxt}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${peSocketItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>													
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty ltsAcqBasketVasDetailCmd.preinstallVasItemList || not empty ltsAcqBasketVasDetailCmd.prewireVasItemList || not empty ltsAcqBasketVasDetailCmd.ffpVasHotItemList || not empty ltsAcqBasketVasDetailCmd.ffpVasOtherItemList || not empty ltsAcqBasketVasDetailCmd.ffpVasStaffItemList}">
	<br>
	<table class="paper_w2 round_10" width="100%">
		<tr>
			<td >
				<div id="s_line_text"><spring:message code="lts.acq.basketVasDetail.additionalVASItems" htmlEscape="false"/></div>
				<table width="40%" cellspacing="0" border="0" style="background:#DDDDDD; margin-left: 1%">
					<tr>
						<td width="1%">&nbsp;</td>
						<td width="10%"> <b><spring:message code="lts.acq.basketVasDetail.FFPFilter" htmlEscape="false"/></b> </td>
						<td width="5%"><form:checkbox path="mth12Ffp" cssClass="mthFfpFilter"/> 12 </td>
						<td width="5%"><form:checkbox path="mth18Ffp" cssClass="mthFfpFilter"/> 18 </td>
						<td width="5%"><form:checkbox path="mth24Ffp" cssClass="mthFfpFilter"/> 24 </td>
					</tr>				
				</table>
				<table width="100%" cellspacing="0" border="0" >
					<tr>
						<td width="1%">&nbsp;</td>
					 	<td>
	  						<table width="100%" border="0" cellspacing="1" cellpadding="1" class="table_style_sb">
	  							<tr>
	  								<th valign="top" width="10">&nbsp;</th>
									<th valign="top" width="60%">&nbsp;</th>
									<th align="center" valign="top" class="BGgreen2 contenttext" width="20%">
										<b><spring:message code="lts.acq.basketVasDetail.monthlyFixedTermRate" htmlEscape="false"/></b>
									</th>
									<th align="center" valign="top" class="BGgreen2 contenttext" width="20%">
										<b><spring:message code="lts.acq.basketVasDetail.monthToMonthRate" htmlEscape="false"/></b>
									</th>
								</tr>
								<c:forEach items="${ltsAcqBasketVasDetailCmd.preinstallVasItemList}" var="preinstallItem" varStatus="status">
									<tr>
										<td valign="top">
											<form:checkbox id="preinstallVasItemList${status.index}" path="preinstallVasItemList[${status.index}].selected" cssClass="preinstallItem" disabled="${preinstallItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											${preinstallItem.itemDisplayHtml}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${preinstallItem.recurrentAmtTxt}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${preinstallItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>
								<c:forEach items="${ltsAcqBasketVasDetailCmd.ffpVasHotItemList}" var="ffpHotItem" varStatus="status">
									<tr>
										<td valign="top">
											<form:checkbox id="ffpVasHotItemList${status.index}" path="ffpVasHotItemList[${status.index}].selected" cssClass="ffpHotVasItem" disabled="${ffpHotItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											${ffpHotItem.itemDisplayHtml}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${ffpHotItem.recurrentAmtTxt}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${ffpHotItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>	
								<c:forEach items="${ltsAcqBasketVasDetailCmd.ffpVasOtherItemList}" var="ffpOtherItem" varStatus="status">
									<tr>
										<td valign="top">
											<form:checkbox id="ffpVasOtherItemList${status.index}" path="ffpVasOtherItemList[${status.index}].selected" cssClass="ffpOtherVasItem" disabled="${ffpOtherItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											${ffpOtherItem.itemDisplayHtml}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${ffpOtherItem.recurrentAmtTxt}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${ffpOtherItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>	
								<c:forEach items="${ltsAcqBasketVasDetailCmd.ffpVasStaffItemList}" var="ffpStaffItem" varStatus="status">
									<tr>
										<td valign="top">
											<form:checkbox id="ffpVasStaffItemList${status.index}" path="ffpVasStaffItemList[${status.index}].selected" cssClass="ffpStaffVasItem" disabled="${ffpStaffItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											${ffpStaffItem.itemDisplayHtml}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${ffpStaffItem.recurrentAmtTxt}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${ffpStaffItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>
								<c:forEach items="${ltsAcqBasketVasDetailCmd.prewireVasItemList}" var="prewireItem" varStatus="status">
									<tr>
										<td valign="top">
											<form:checkbox id="prewireVasItemList${status.index}" path="prewireVasItemList[${status.index}].selected" cssClass="prewireItem" disabled="${prewireItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											${prewireItem.itemDisplayHtml}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${prewireItem.recurrentAmtTxt}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${prewireItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty ltsAcqBasketVasDetailCmd.optAccessaryItemList}">
	<br>
	<table class="paper_w2 round_10" width="100%">
		<tr>
			<td >
				<div id="s_line_text"> <spring:message code="lts.acq.basketVasDetail.optionalAccessary" htmlEscape="false"/> </div>
				<table width="100%" cellspacing="0" border="0" >
					<tr>
						<td width="1%">&nbsp;</td>
					 	<td>
	  						<table width="100%" border="0" cellspacing="1" cellpadding="1" class="table_style_sb">
	  							<tr>
	  								<th valign="top" width="10">&nbsp;</th>
									<th valign="top" width="60%">&nbsp;</th>
									<th align="center" valign="top" class="BGgreen2 contenttext" width="20%">
										<b><spring:message code="lts.acq.basketVasDetail.monthlyFixedTermRate" htmlEscape="false"/></b>
									</th>
									<th align="center" valign="top" class="BGgreen2 contenttext" width="20%">
										<b><spring:message code="lts.acq.basketVasDetail.monthToMonthRate" htmlEscape="false"/></b>
									</th>
								</tr>
								<c:forEach items="${ltsAcqBasketVasDetailCmd.optAccessaryItemList}" var="optAccessaryItem" varStatus="status">
									<tr>
										<td valign="top">
											<form:checkbox path="optAccessaryItemList[${status.index}].selected" disabled="${optAccessaryItem.mdoInd == 'M'}"/>
										</td>
										<td valign="top" class="item_detail">
											${optAccessaryItem.itemDisplayHtml}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${optAccessaryItem.recurrentAmtTxt}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${optAccessaryItem.mthToMthAmtTxt}
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</c:if>


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
			<a id="submitBtn" href="#" ><div class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a>
		</td>
	</tr>
</table>

</div>

<script type="text/javascript">
	var cancelMsg = '<spring:message code="lts.acq.basketVasDetail.cancelSelectedService" htmlEscape="false"/>';

	$(ltsacqbasketvasdetail.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>