<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltsbasketvasdetail.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 3 : 5}" />
</jsp:include>

<form:form method="POST" id="ltsBasketVasDetailForm" name="ltsBasketVasDetailForm"  commandName="ltsBasketVasDetailCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="formAction" />

<div id="s_line_text"><spring:message code="lts.offerDtl.VAS" text="Value Added Service"/></div>

<table width="98%" align="center">
	<tr> 
		<td>
			<div class="paper_w2 round_20">
				<br>
				<table width="98%" border="0" align="center">
					<tr> 
		                <td colspan="3">
		                	<div id="s_line_text"><spring:message code="lts.offerDtl.existProfVAS" text="Existing Profile VAS"/></div>
		                </td>
		         	</tr>
		         	
	    		    <c:if test="${not empty ltsBasketVasDetailCmd.existVasItemList }">
		           		<c:forEach items="${ltsBasketVasDetailCmd.existVasItemList }" var="existVasItem" varStatus="status">
							<tr>
							 	<td width="2%"><form:hidden path="existVasItemList[${status.index}].selected"/></td>
							 	<td width="90%" valign="top" class="item_detail">
									${existVasItem.itemDisplayHtml}
								</td>						
								<td width="8%" valign="top">
									&nbsp;
								</td>
							</tr>
						</c:forEach>
					</c:if>
		         </table>
		         
				<c:if test="${not empty ltsBasketVasDetailCmd.profileExistingTpItemList || not empty ltsBasketVasDetailCmd.profileAutoOutTpItemList }">
					<br/>
					<table width="94%" border="0" align="center">
						<tr> 
			                <td class="bold"><spring:message code="lts.offerDtl.srvLineTP" text="Service Line Term Plan"/></td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="1" class="table_style">
									<tr>
										<th width="35%"><spring:message code="lts.offerDtl.tpDesc" text="TP Description "/></th>
										<th><spring:message code="lts.offerDtl.tpCat" text="TP Categories"/></th>
										<th><spring:message code="lts.offerDtl.effDate" text="Effective Date"/></th>
										<th><spring:message code="lts.offerDtl.endDate" text="End Date"/></th>
										<th><spring:message code="lts.offerDtl.GEP" text="Gross Effective Price"/></th>
									</tr>
									<c:set var="rowCount" value="0" />
									<c:if test="${not empty ltsBasketVasDetailCmd.profileExistingTpItemList}">
										<c:forEach items="${ltsBasketVasDetailCmd.profileExistingTpItemList }" var="profileItemDetail" varStatus="status">
											<c:if test="${profileItemDetail.itemDetail != null}">
												<c:set var="rowCount" value="${rowCount + 1 }"/>
												<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" align="center">
													<td><c:out value="${profileItemDetail.itemDetail.itemDisplayHtml}" default="-" /></td>
													<td><c:out value="${profileItemDetail.itemDetail.tpCatg}" default="-" /></td>
													<td><c:out value="${profileItemDetail.conditionEffStartDate}" default="-" /></td>
													<td><c:out value="${profileItemDetail.conditionEffEndDate}" default="-" /></td>
													<td>$<c:out value="${profileItemDetail.itemDetail.grossEffPrice}" default="-"/></td>
												</tr>
											</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${not empty ltsBasketVasDetailCmd.profileAutoOutTpItemList}">
										<c:forEach items="${ltsBasketVasDetailCmd.profileAutoOutTpItemList }" var="profileItemDetail" varStatus="status">
											<c:if test="${profileItemDetail.itemDetail != null}">
												<c:set var="rowCount" value="${rowCount + 1 }"/>	
												<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" align="center">
													<td>${profileItemDetail.itemDetail.itemDisplayHtml}</td>
													<td><c:out value="${profileItemDetail.itemDetail.tpCatg}" default="-" /></td>
													<td><c:out value="${profileItemDetail.conditionEffStartDate}" default="-" /></td>
													<td><c:out value="${profileItemDetail.conditionEffEndDate}" default="-" /></td>
													<td>$<c:out value="${profileItemDetail.itemDetail.grossEffPrice}" default="-"/></td>
												</tr>
											</c:if>
										</c:forEach>
									</c:if>											
									<c:if test="${rowCount == 0}">
										<tr>
											<td colspan="7" align="center"><b>NIL</b></td>
										</tr>
									</c:if>																														
								</table>
							</td>
						</tr>
					</table>
					<br/>
				</c:if>
				
				<c:if test="${not empty ltsBasketVasDetailCmd.profileExistingVasItemList || not empty ltsBasketVasDetailCmd.profileAutoOutVasItemList }">
					<br/>
					<table width="94%" border="0" align="center">
						<tr>
							 <td class="bold"><spring:message code="lts.offerDtl.otherVASOff" text="Other VAS Offer"/></td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="1" class="table_style">
									<tr>
										<th width="35%"><spring:message code="lts.offerDtl.existVAS" text="Existing VAS "/></th>
										<th><spring:message code="lts.offerDtl.effDate" text="Effective Date"/></th>
										<th><spring:message code="lts.offerDtl.conEndDate" text="Contract End Date"/></th>
										<th><spring:message code="lts.offerDtl.proStartDate" text="Promotion Start Date"/></th>
										<th><spring:message code="lts.offerDtl.proEndDate" text="Promotion End Date"/></th>
										<th><spring:message code="lts.offerDtl.GP" text="Gross Price"/></th>
										<th><spring:message code="lts.offerDtl.EP" text="Effective Price"/></th>
										<c:choose>
											<c:when test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR'}">
												<th><spring:message code="lts.offerDtl.renewHandling" text="Renewal Handling"/></th>
											</c:when>
											<c:otherwise>
												<th><spring:message code="lts.offerDtl.upgHandling" text="Upgrade Handling"/></th>	
											</c:otherwise>
										</c:choose>
										<th><spring:message code="lts.offerDtl.penHandling" text="Penalty Handling"/></th>
									</tr>
									<c:set var="rowCount" value="0" />
									<c:if test="${not empty ltsBasketVasDetailCmd.profileExistingVasItemList}">
										<c:forEach items="${ltsBasketVasDetailCmd.profileExistingVasItemList }" var="profileItemDetail" varStatus="status">
											<c:if test="${profileItemDetail.itemDetail != null}">
												<c:set var="rowCount" value="${rowCount + 1 }"/>	
												<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" align="center">
													<td>${profileItemDetail.itemDetail.itemDisplayHtml}</td>
													<td><c:out value="${profileItemDetail.conditionEffStartDate}" default="-"/></td>
													<td><c:out value="${profileItemDetail.conditionEffEndDate}" default="-"/></td>
													<td><c:out value="${profileItemDetail.promotionStartDate}" default="-" /></td>
													<td><c:out value="${profileItemDetail.promotionEndDate}" default="-" /></td>
													<td>$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.grossEffPrice : '0'}" default="-" /></td>
													<td>$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.netEffPrice : '0'}" default="-" /></td>
													<td><spring:message code="lts.offerDtl.carryFwd" text="Carry Forward"/></td>
													<td>-</td>
												</tr>
											</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${not empty ltsBasketVasDetailCmd.profileAutoOutVasItemList}">
										<c:forEach items="${ltsBasketVasDetailCmd.profileAutoOutVasItemList }" var="profileItemDetail" varStatus="status">
											<c:if test="${profileItemDetail.itemDetail != null}">
												<c:set var="rowCount" value="${rowCount + 1 }"/>	
												<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" align="center">
													<td>${profileItemDetail.itemDetail.itemDisplayHtml}</td>
													<td><c:out value="${profileItemDetail.conditionEffStartDate}" default="-"/></td>
													<td><c:out value="${profileItemDetail.conditionEffEndDate}" default="-"/></td>
													<td><c:out value="${profileItemDetail.promotionStartDate}" default="-" /></td>
													<td><c:out value="${profileItemDetail.promotionEndDate}" default="-" /></td>
													<td>$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.grossEffPrice : '0'}" default="-" /></td>
													<td>$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.netEffPrice : '0'}" default="-" /></td>
													<td>
														<c:choose>
															<c:when test="${profileItemDetail.forceRetain}">
																<c:choose>
																	<c:when test="${sessionScope.sessionLtsOrderCapture.ltsMiscellaneousForm.recontract
																					&& not empty sessionScope.sessionLtsOrderCapture.ltsRecontractForm 
																					&& !sessionScope.sessionLtsOrderCapture.ltsRecontractForm.deceasedCase}">
																		<spring:message code="lts.offerDtl.autoOut" text="Auto Out"/>
																	</c:when>
																	<c:otherwise>
																		<spring:message code="lts.offerDtl.carryFwd" text="Carry Forward"/> (Force_Retain)
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<spring:message code="lts.offerDtl.autoOut" text="Auto Out"/>
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${profileItemDetail.forceRetain}">
																
																<c:choose>
																	<c:when test="${sessionScope.sessionLtsOrderCapture.ltsMiscellaneousForm.recontract
																					&& not empty sessionScope.sessionLtsOrderCapture.ltsRecontractForm 
																					&& !sessionScope.sessionLtsOrderCapture.ltsRecontractForm.deceasedCase}">
																		<spring:message code="lts.offerDtl.generate" text="Generate"/>
																	</c:when>
																	<c:otherwise>
																		-
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${profileItemDetail.itemDetail.penaltyHandling == 'AW' }">
																<spring:message code="lts.offerDtl.autoWaived" text="Auto Waived"/>
															</c:when>
															<c:when test="${profileItemDetail.itemDetail.penaltyHandling == 'AG' }">
																<form:select cssClass="penalty" path="profileAutoOutVasItemList[${status.index}].itemDetail.penaltyHandling" >
																	<form:options items="${penaltyMap}" />
																</form:select>
															</c:when>
															<c:otherwise>
																<spring:message code="lts.offerDtl.NA" text="N/A"/>
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
											</c:if>
										</c:forEach>
									</c:if>											
									<c:if test="${rowCount == 0}">
										<tr>
											<td colspan="9" align="center"><b>NIL</b></td>
										</tr>
									</c:if>																														
								</table>
							</td>
						</tr>
					</table>
					<br />
				</c:if>									
	
				<c:if test="${not empty ltsBasketVasDetailCmd.imsProfileExistingItemList || not empty ltsBasketVasDetailCmd.imsProfileAutoOutItemList }">
					<br>
					<table width="94%" border="0" align="center">
						<tr>
							 <td class="bold">Related FSA VAS Offer</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="1" class="table_style">
									<tr>
										<th width="50%"><spring:message code="lts.offerDtl.existVAS" text="Existing VAS "/></th>
										<th width="10%"><spring:message code="lts.offerDtl.effDate" text="Effective Date"/></th>
										<th width="10%"><spring:message code="lts.offerDtl.conEndDate" text="Contract End Date"/></th>
										<c:choose>
											<c:when test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR'}">
												<th width="10%"><spring:message code="lts.offerDtl.renewHandling" text="Renewal Handling"/></th>
											</c:when>
											<c:otherwise>
												<th width="10%"><spring:message code="lts.offerDtl.upgHandling" text="Upgrade Handling"/></th>	
											</c:otherwise>
										</c:choose>
										<th width="10%"><spring:message code="lts.offerDtl.penHandling" text="Penalty Handling"/></th>
									</tr>
									<c:set var="rowCount" value="0" />
									<c:if test="${not empty ltsBasketVasDetailCmd.imsProfileExistingItemList}">
										<c:forEach items="${ltsBasketVasDetailCmd.imsProfileExistingItemList }" var="profileItemDetail" varStatus="status">
											<c:if test="${profileItemDetail.itemDetail != null}">
												<c:set var="rowCount" value="${rowCount + 1 }"/>	
												<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" align="center">
													<td>${profileItemDetail.itemDetail.itemDisplayHtml}</td>
													<td><c:out value="${profileItemDetail.conditionEffStartDate}" default="-"/></td>
													<td><c:out value="${profileItemDetail.conditionEffEndDate}" default="-"/></td>
													<td><spring:message code="lts.offerDtl.carryFwd" text="Carry Forward"/></td>
													<td>-</td>
												</tr>
											</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${not empty ltsBasketVasDetailCmd.imsProfileAutoOutItemList}">
										<c:forEach items="${ltsBasketVasDetailCmd.imsProfileAutoOutItemList }" var="profileItemDetail" varStatus="status">
											<c:if test="${profileItemDetail.itemDetail != null}">
												<c:set var="rowCount" value="${rowCount + 1 }"/>	
												<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" align="center">
													<td>${profileItemDetail.itemDetail.itemDisplayHtml}</td>
													<td><c:out value="${profileItemDetail.conditionEffStartDate}" default="-"/></td>
													<td><c:out value="${profileItemDetail.conditionEffEndDate}" default="-"/></td>
													<td><spring:message code="lts.offerDtl.autoOut" text="Auto Out"/></td>
													<td>
														<c:choose>
															<c:when test="${profileItemDetail.itemDetail.penaltyHandling == 'AW' }">
																<spring:message code="lts.offerDtl.autoWaived" text="Auto Waived"/>
															</c:when>
															<c:when test="${profileItemDetail.itemDetail.penaltyHandling == 'AG' }">
																<form:select cssClass="penalty" path="imsProfileAutoOutItemList[${status.index}].itemDetail.penaltyHandling" >
																	<form:options items="${penaltyMap}" />
																</form:select>
															</c:when>
															<c:otherwise>
																<spring:message code="lts.offerDtl.NA" text="N/A"/>
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
											</c:if>
										</c:forEach>
									</c:if>											
									<c:if test="${rowCount == 0}">
										<tr>
											<td colspan="5" align="center"><b>NIL</b></td>
										</tr>
									</c:if>																														
								</table>
							</td>
						</tr>
					</table>
					<br>
				</c:if>		
				
				<div id="approvalRemarkDiv" style="display: none;">
					<table width="100%" border="0" class="contenttext" cellspacing="20">
						<tr>
							<td align="right">
								<div class="func_button">
									<a id="approvalRemarkBtn" href="#" class="nextbutton"><spring:message code="lts.offerDtl.apprvRmk" text="Approval Remark"/></a>
								</div> 
							</td>
						</tr>
					</table>
				</div>
				
			<c:if test="${ not empty ltsBasketVasDetailCmd.bvasItemList}">
				<table width="98%" border="0" align="center">
					<tr> 
		                <td colspan="3">
		                	<div id="s_line_text"><spring:message code="lts.offerDtl.bskBundleVas" text="Basket Bundle VAS"/></div>
		                </td>
		         	</tr>
					<tr>
				 		<td>
  							<table width="100%" border="0" class="table_style_sb">							
								<tr>
									<th colspan="2" width="60%">&nbsp;</th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
								</tr>
								<c:forEach items="${ltsBasketVasDetailCmd.bvasItemList}" var="bvasItem" varStatus="status">
									<tr>
										<td valign="top" colspan="2">
											${bvasItem.itemDisplayHtml}
											
											<c:if test="${not empty bvasItem.itemAttbs }">
												<c:forEach items="${bvasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
													<br>
													${itemAttb.attbDesc}
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
				<br>
			</c:if>
			
			<c:if test="${ not empty ltsBasketVasDetailCmd.teamVasSetList}">
				<table width="98%" border="0" align="center">
					<tr> 
		                <td colspan="3">
		                	<div id="s_line_text"><spring:message code="lts.offerDtl.freeVas" text="Free VAS"/></div>
		                </td>
		         	</tr>					
		         	<tr>
				 		<td>
	 						<table width="100%" border="0" class="table_style_sb">
	 							<tr>
									<th colspan="2" width="60%">&nbsp;</th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
								</tr>
								<c:forEach items="${ltsBasketVasDetailCmd.teamVasSetList}" var="teamVasSet" varStatus="setStatus">						
									<c:if test="${not empty teamVasSet.itemDetails}">
										<tr>
											<td colspan="2" valign="top" class="item_title_rp">
												<c:if test="${teamVasSet.maxQty != 0}">
													<spring:message code="lts.offerDtl.selection1" text="Please select"/> <c:out value="${teamVasSet.maxQty}" /> <spring:message code="lts.offerDtl.selectfreeVas2" text="free VAS item(s)"/> :
													&nbsp;&nbsp; <form:errors path="teamVasSetList[${setStatus.index}].itemSetId" cssClass="error" />
												</c:if>
											</td>
											<td align="center" valign="top" class="itemPrice"></td>
											<td align="center" valign="top" class="itemPrice"></td>
										</tr>
										<c:forEach items="${teamVasSet.itemDetails}" var="teamVasItem" varStatus="itemStatus">
											<tr>
												<td valign="top">
													<form:checkbox cssClass="teamVasItem" path="teamVasSetList[${setStatus.index}].itemDetails[${itemStatus.index }].selected"/>
												</td>
												<td valign="top" class="item_detail teamVasItemDisplay">
													${teamVasItem.itemDisplayHtml}
												</td>
												<td align="center" valign="top" class="itemPrice">
													<c:out value="${teamVasItem.recurrentAmtTxt}" default=""/>
												</td>
												<td align="center" valign="top" class="itemPrice">
													<c:out value="${teamVasItem.mthToMthAmtTxt}" default=""/>
												</td>									
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
				<br/>
			</c:if>
			<c:if test="${ not empty ltsBasketVasDetailCmd.hotVasItemList || not empty ltsBasketVasDetailCmd.otherVasItemList || not empty ltsBasketVasDetailCmd.profileAutoChangeTpItemList}">
				<table width="98%" border="0" align="center">
					<tr> 
		                <td colspan="3">
		                	<div id="s_line_text"><spring:message code="lts.offerDtl.addVAS" text="Additional VAS"/></div>
		                </td>
		         	</tr>					
		         	<tr>
				 		<td>
	 						<table width="100%" border="0" class="table_style_sb">
	 							<tr>
									<th colspan="2" width="60%">&nbsp;</th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
								</tr>						
								<c:if test="${not empty ltsBasketVasDetailCmd.profileAutoChangeTpItemList}">
									<c:forEach items="${ltsBasketVasDetailCmd.profileAutoChangeTpItemList}" var="profileAutoChangeTpItem" varStatus="status">
										<tr>
											<td valign="top">
												<form:checkbox cssClass="profileAutoChangeTpItem" path="profileAutoChangeTpItemList[${status.index}].itemDetail.selected"  />
											</td>
											<td valign="top" class="item_detail profileAutoChangeTpItemDisplay">
												${profileAutoChangeTpItem.itemDetail.itemDisplayHtml}
											</td>
											<td align="center" valign="top" class="itemPrice">
												<c:out value="${profileAutoChangeTpItem.itemDetail.recurrentAmtTxt}" default="Waived"/>
											</td>
											<td align="center" valign="top" class="itemPrice">
												<c:out value="${profileAutoChangeTpItem.itemDetail.mthToMthAmtTxt}" default="Waived"/>
											</td>									
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${not empty ltsBasketVasDetailCmd.hotVasItemList}">
									<c:forEach items="${ltsBasketVasDetailCmd.hotVasItemList}" var="hotVasItem" varStatus="status">
										<tr>
											<td valign="top">
												<form:checkbox cssClass="hotVasItem" path="hotVasItemList[${status.index}].selected" disabled="${hotVasItem.mdoInd == 'M'}" />
											</td>
											<td valign="top" class="item_detail">
												${hotVasItem.itemDisplayHtml}
												
												<c:if test="${not empty hotVasItem.itemAttbs }">
													<c:forEach items="${hotVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
														<br>
														${itemAttb.attbDesc}
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
							<c:if test="${ not empty ltsBasketVasDetailCmd.otherVasItemList}">
								<table id="otherVasTable" width="100%" class="table_style_sb"  style="display: none;">
									<c:forEach items="${ltsBasketVasDetailCmd.otherVasItemList}" var="otherVasItem" varStatus="status" >
										<tr>
											<td valign="top" width="2%">
												<form:checkbox cssClass="otherVasItem" path="otherVasItemList[${status.index}].selected" disabled="${otherVasItem.mdoInd == 'M'}"/>
											</td>
											<td valign="top" width="58%" class="item_detail">
												${otherVasItem.itemDisplayHtml}
												<c:if test="${not empty otherVasItem.itemAttbs }">
													<c:forEach items="${otherVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
														<br>
														${itemAttb.attbDesc}
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
								<table width="100%" border="0"  class="contenttext">
									<tr>
										<td colspan="4">
											<div id="showOtherVasBtnDiv" style="text-align: right;">
												<div class="func_button">
													<a href="#" id="showOtherVasBtn"><spring:message code="lts.offerDtl.showOtherVAS" text="Show Other VAS"/></a>
												</div>
											</div>
											<div id="hideOtherVasBtnDiv" style="text-align: right; display: none;" >
												<div class="func_button">
													<a href="#" id="hideOtherVasBtn"><spring:message code="lts.offerDtl.hideOtherVas" text="Hide Other VAS"/></a>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
						</td>
					</tr>
				</table>
			</c:if>
			</div>
		</td>
	</tr>
</table>

<c:if test="${not empty ltsBasketVasDetailCmd.contItemSetList || not empty ltsBasketVasDetailCmd.moovItemList || not empty ltsBasketVasDetailCmd.contentItemList}">
	<table width="100%" border="0" align="center" cellpadding="5">
	<c:if test="${not empty ltsBasketVasDetailCmd.contItemSetList}">
		<c:forEach items="${ltsBasketVasDetailCmd.contItemSetList}" var="contItemSet" varStatus="contItemSetStatus">
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
						<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
						<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
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
	<c:if test="${not empty ltsBasketVasDetailCmd.moovItemList || not empty ltsBasketVasDetailCmd.contentItemList}">
		<tr>
			<td width="100%" colspan="2">
				<div id="s_line_text"><spring:message code="lts.offerDtl.optionalSrv" text="Optional Service"/></div>
				<table class="table_style_sb" width="100%" border="0" >
					<tr>
						<th width="60%" valign="top" colspan="2">&nbsp;</th>	
						<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
						<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
					</tr>
					<c:if test="${not empty ltsBasketVasDetailCmd.moovItemList}" >
						<c:forEach items="${ltsBasketVasDetailCmd.moovItemList}" var="moovItem" varStatus="moovItemStatus">
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
					<c:if test="${not empty ltsBasketVasDetailCmd.contentItemList}" >
						<c:forEach items="${ltsBasketVasDetailCmd.contentItemList}" var="contentItem" varStatus="contentItemStatus">
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
</c:if>

<c:if test="${not empty ltsBasketVasDetailCmd.smartWrtySetList}">
	<br>
	
	<table width="98%" align="center">
		<tr> 
			<td>
				<div class="paper_w2 round_20">
					<br>
					<table width="98%" border="0" align="center">
						<tr> 
			                <td>
			                	<div id="s_line_text"><spring:message code="lts.offerDtl.SW" text="Smart Warranty"/></div>
			                </td>
			         	</tr>
						<tr>
							 <td>
		   						<table width="100%" border="0" class="table_style_sb">
	 							<tr>
									<th colspan="2" width="60%">&nbsp;</th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
								</tr>
								<c:forEach items="${ltsBasketVasDetailCmd.smartWrtySetList}" var="smartWrtySet" varStatus="setStatus">						
									<c:if test="${not empty smartWrtySet.itemDetails}">
										<tr>
											<td colspan="2" valign="top" class="item_title_rp">
												<c:if test="${smartWrtySet.maxQty != 0}">
													<spring:message code="lts.offerDtl.selection1" text="Please select"/> <c:out value="${smartWrtySet.maxQty}" /> <spring:message code="lts.offerDtl.selectVas2" text="VAS item(s)"/>:
													&nbsp;&nbsp; <form:errors path="smartWrtySetList[${setStatus.index}].itemSetId" cssClass="error" />
												</c:if>
											</td>
 											<td align="center" valign="top" class="itemPrice"></td> 
											<td align="center" valign="top" class="itemPrice"></td>
										</tr>
										<c:forEach items="${smartWrtySet.itemDetails}" var="smartWrtyItem" varStatus="itemStatus">
											<tr>
												<td valign="top" width="2%">
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
				</div>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty ltsBasketVasDetailCmd.bundleVasSetList}">
	<br>
	
	<table width="98%" align="center">
		<tr> 
			<td>
				<div class="paper_w2 round_20">
					<br>
					<table width="98%" border="0" align="center">
						<tr> 
			                <td>
			                	<div id="s_line_text"><spring:message code="lts.offerDtl.bundleVAS" text="Bundle VAS"/></div>
			                </td>
			         	</tr>
						<tr>
							 <td>
		   						<table width="100%" border="0" class="table_style_sb">
	 							<tr>
									<th colspan="2" width="60%">&nbsp;</th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
									<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
								</tr>
								<c:forEach items="${ltsBasketVasDetailCmd.bundleVasSetList}" var="bundleVasSet" varStatus="setStatus">						
									<c:if test="${not empty bundleVasSet.itemDetails}">
										<tr>
											<td colspan="2" valign="top" class="item_title_rp">
												<c:if test="${bundleVasSet.maxQty != 0}">
													<spring:message code="lts.offerDtl.selection1" text="Please select"/> <c:out value="${bundleVasSet.maxQty}" /> <spring:message code="lts.offerDtl.selectVas2" text="VAS item(s)"/> :
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
															${itemAttb.attbDesc}
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

<c:if test="${not empty ltsBasketVasDetailCmd.iddVasItemList || not empty ltsBasketVasDetailCmd.e0060VasItemList}">
	<br>
	<form:hidden path="optOutIdd" />
	<form:hidden path="optOut0060e" />
	
	<table width="98%" align="center">
		<tr> 
			<td>
				<div class="paper_w2 round_20">
					<br>
					<table width="98%" border="0" align="center">
						<tr> 
			                <td>
			                	<div id="s_line_text"><spring:message code="lts.offerDtl.IDD0060" text="IDD 0060/IDD001/0060 everywhere"/></div>
			                </td>
			         	</tr>
						<tr>
							 <td>
		   						<table width="100%" border="0" class="table_style_sb">
									<tr>
										<th colspan="2" width="60%">&nbsp;</th>
										<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
										<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
									</tr>
									
									<c:forEach items="${ltsBasketVasDetailCmd.iddVasItemList}" var="iddVasItem" varStatus="status">
										<tr>
											<td valign="top" width="2%">
												<form:checkbox path="iddVasItemList[${status.index}].selected" cssClass="iddItem" disabled="${iddVasItem.mdoInd == 'M'}"/>
											</td>
											<td valign="top" width="58%" class="item_detail">
												${iddVasItem.itemDisplayHtml}
											</td>
											<td align="center" valign="top" class="itemPrice" width="20%">
												${iddVasItem.recurrentAmtTxt}
											</td>												
											<td align="center" valign="top" class="itemPrice" width="20%">
												${iddVasItem.mthToMthAmtTxt}
											</td>
										</tr>	
									</c:forEach>
									
									<c:forEach items="${ltsBasketVasDetailCmd.e0060VasItemList}" var="e0060VasItem" varStatus="status">
										<tr>
											<td valign="top" width="2%">
												<form:checkbox path="e0060VasItemList[${status.index}].selected" cssClass="e0060Item" disabled="${e0060VasItem.mdoInd == 'M'}"/>
											</td>
											<td valign="top" width="58%" class="item_detail">
												${e0060VasItem.itemDisplayHtml}
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
				</div>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${ltsBasketVasDetailCmd.hasFfp}">
	<br>
	<table width="98%" align="center">
		<tr> 
			<td>
				<div class="paper_w2 round_20">
					<br>
					<table width="98%" border="0" align="center">
						<tr> 
			                <td>
			                	<div id="s_line_text"><spring:message code="lts.offerDtl.iddFixFeePlan" text="IDD Fixed Fee Plan"/></div>
			                </td>
			         	</tr>
						<tr>
							 <td>
							    <table width="40%" cellspacing="0" border="0" style="background:#DDDDDD; margin-left: 1%">
									<tr>
										<td width="1%">&nbsp;</td>
										<td width="10%"> <b><spring:message code="lts.acq.basketVasDetail.FFPFilter" htmlEscape="false"/></b> </td>
										<td width="5%"><form:checkbox path="mth12Ffp" cssClass="mthFfpFilter"/> 12 </td>
										<td width="5%"><form:checkbox path="mth18Ffp" cssClass="mthFfpFilter"/> 18 </td>
										<td width="5%"><form:checkbox path="mth24Ffp" cssClass="mthFfpFilter"/> 24 </td>
									</tr>				
								</table>
		   						<table width="100%" border="0" class="table_style_sb">
									<tr>
										<th colspan="2" width="60%">&nbsp;</th>
										<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
										<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
									</tr>	
							
									<c:if test="${not empty ltsBasketVasDetailCmd.ffpHotItemList}">
										<c:forEach items="${ltsBasketVasDetailCmd.ffpHotItemList}" var="ffpHotItem" varStatus="status">
											<tr>
												<td valign="top">
													<form:checkbox path="ffpHotItemList[${status.index}].selected" disabled="${ffpHotItem.mdoInd == 'M'}"/>
												</td>
												<td valign="top" width="58%" class="item_detail">
													${ffpHotItem.itemDisplayHtml}
												</td>
												<td align="center" valign="top" class="BGgreen2">
													${ffpHotItem.recurrentAmtTxt}
												</td>
												<td align="center" valign="top" class="BGgreen2">
													${ffpHotItem.mthToMthAmtTxt}
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${not empty ltsBasketVasDetailCmd.ffpStaffItemList}">
										<c:forEach items="${ltsBasketVasDetailCmd.ffpStaffItemList}" var="ffpStaffItem" varStatus="status">
											<tr>
												<td valign="top">
													<form:checkbox path="ffpStaffItemList[${status.index}].selected" disabled="${ffpStaffItem.mdoInd == 'M'}"/>
												</td>
												<td valign="top" width="58%" class="item_detail">
													${ffpStaffItem.itemDisplayHtml} 
												</td>
												<td align="center" valign="top" class="BGgreen2">
													${ffpStaffItem.recurrentAmtTxt}
												</td>
												<td align="center" valign="top" class="BGgreen2">
													${ffpStaffItem.mthToMthAmtTxt}
												</td>
											</tr>
										</c:forEach>										
									</c:if>
									<c:if test="${not empty ltsBasketVasDetailCmd.ffpOtherItemList}">
										<c:forEach items="${ltsBasketVasDetailCmd.ffpOtherItemList}" var="ffpOtherItem" varStatus="status">
											<tr class="otherFfpItem" style="display: none;">
												<td valign="top">
													<form:checkbox cssClass="ffpOtherItem" path="ffpOtherItemList[${status.index}].selected" disabled="${ffpOtherItem.mdoInd == 'M'}"/>
												</td>
												<td valign="top" width="58%" class="item_detail">
													${ffpOtherItem.itemDisplayHtml} 
												</td>
												<td align="center" valign="top" class="BGgreen2">
													${ffpOtherItem.recurrentAmtTxt}
												</td>
												<td align="center" valign="top" class="BGgreen2">
													${ffpOtherItem.mthToMthAmtTxt}
												</td>
											</tr>
										</c:forEach>	
									</c:if>
								</table>
								<c:if test="${not empty ltsBasketVasDetailCmd.ffpOtherItemList}">
									<table width="100%" border="0"  class="contenttext">
										<tr>
											<td colspan="4">
												<div id="showOtherFfpBtnDiv" style="text-align: right;">
													<div class="func_button">
														<a href="#" id="showOtherFfpBtn"><spring:message code="lts.offerDtl.showOtherFfp" text="Show Other FFP"/></a>
													</div>
												</div>
												<div id="hideOtherFfpBtnDiv" style="text-align: right; display: none;">
													<div class="func_button">
														<a href="#" id="hideOtherFfpBtn"><spring:message code="lts.offerDtl.hideOtherFfp" text="Hide Other FFP"/></a>
													</div>
												</div>
											</td>
										</tr>
									</table>
								</c:if>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</c:if>



<c:if test="${not empty ltsBasketVasDetailCmd.peFreeItemList || not empty ltsBasketVasDetailCmd.peSocketItemList}">
	<br>
	<table width="98%" align="center">
		<tr> 
			<td>
				<div class="paper_w2 round_20">
					<br>
					<table width="98%" border="0" align="center">
						<tr> 
			                <td>
			                	<div id="s_line_text"><spring:message code="lts.offerDtl.parallelPhoneLine" text="Parallel Phone Line"/></div>
			                </td>
			         	</tr>
						<tr>
							 <td>
		   						<table width="100%" border="0" class="table_style_sb">
									<tr>
										<th colspan="2" width="60%">&nbsp;</th>
										<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
										<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
									</tr>	
									<c:forEach items="${ltsBasketVasDetailCmd.peFreeItemList}" var="peFreeItem" varStatus="status">
										<tr>
											<td valign="top">
												<form:checkbox path="peFreeItemList[${status.index}].selected" disabled="${peFreeItem.mdoInd == 'M'}"/>
											</td>
											<td valign="top" width="58%" class="item_detail">
												${peFreeItem.itemDisplayHtml}
											</td>
											<td align="center" valign="top" class="BGgreen2">
												${peFreeItem.recurrentAmtTxt}
											</td>
											<td align="center" valign="top" class="BGgreen2">
												${peFreeItem.mthToMthAmtTxt}
											</td>
										</tr>
									</c:forEach>
									<c:forEach items="${ltsBasketVasDetailCmd.peSocketItemList}" var="peSocketItem" varStatus="status">
										<tr>
											<td valign="top">
												<form:checkbox path="peSocketItemList[${status.index}].selected" disabled="${peSocketItem.mdoInd == 'M' || notAllowPeSocket}"/>
											</td>
											<td valign="top" width="58%" class="item_detail">
												${peSocketItem.itemDisplayHtml} 
												
												<form:select path="peSocketItemList[${status.index}].selectedQty" disabled="${notAllowPeSocket}">
													<form:option value="1" label="1" />
													<form:option value="2" label="2" />
													<form:option value="3" label="3" />
												</form:select>
											</td>
											<td align="center" valign="top" class="BGgreen2">
												${peSocketItem.recurrentAmtTxt}
											</td>
											<td align="center" valign="top" class="BGgreen2">
												${peSocketItem.mthToMthAmtTxt}
											</td>
										</tr>
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


<c:if test="${not empty ltsBasketVasDetailCmd.optAccessaryItemList}">
<br>
	<table width="98%" align="center">
		<tr> 
			<td>
				<div class="paper_w2 round_20">
					<br>
					<table width="98%" border="0" align="center">
						<tr> 
			                <td>
			                	<div id="s_line_text"><spring:message code="lts.offerDtl.optionalAccessory" text="Optional Accessory"/></div>
			                </td>
			         	</tr>
						<tr>
							 <td>
		   						<table width="100%" border="0" class="table_style_sb">
									<tr>
										<th colspan="2" width="60%">&nbsp;</th>
										<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
										<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
									</tr>	
									<c:forEach items="${ltsBasketVasDetailCmd.optAccessaryItemList}" var="optAccessaryItem" varStatus="status">
										<tr>
											<td valign="top" >
												<form:checkbox path="optAccessaryItemList[${status.index}].selected" disabled="${optAccessaryItem.mdoInd == 'M'}"/>
											</td>
											<td valign="top" width="58%" class="item_detail">
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
				</div>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty sessionScope.sessionLtsOrderCapture.selectedBasket && not empty sessionScope.sessionLtsOrderCapture.selectedBasket.bundle2G}">
<br>
	<table width="98%" align="center">
		<tr> 
			<td>
				<div class="paper_w2 round_20">
					<br>
					<table width="98%" border="0" align="center">
						<tr> 
			                <td>
			                	<div id="s_line_text"><spring:message code="lts.offerDtl.pccwMob2gSrv" text="PCCW mobile 2G service"/></div>
			                </td>
			         	</tr>
						<tr>
							<td>
								<table width="95%" border="0" align="center">
									<tr>
										 <td width="90%">
				   							<spring:message code="lts.offerDtl.2gMobNum" text="2G Mobile Number"/> :  <form:input path="bundle2gNum"/>
										 </td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<br>
				</div>
			</td>
		</tr>
	</table>
</c:if>



<c:if test="${not empty ltsBasketVasDetailCmd.profileExistingVasItemList || not empty ltsBasketVasDetailCmd.imsProfileExistingItemList}">
<br>
	<div id="cancelVasDiv" style="display: none;">	
		
		<table width="98%" align="center">
			<tr> 
				<td>
					<div class="paper_w2 round_20">
						<br>
						<table width="98%" border="0" align="center">
							<tr> 
				                <td>
				                	<div id="s_line_text"><spring:message code="lts.offerDtl.cxlExistVas" text="Cancel Existing VAS"/></div>
				                </td>
				         	</tr>
				         	<c:if test="${not empty ltsBasketVasDetailCmd.profileExistingVasItemList}">
								<tr>
									<td>
										<br/>
										<table width="98%" align="center">
											<tr>
												<td class="bold"><spring:message code="lts.offerDtl.existVasOffer" text="Existing VAS Offer"/>:</td>
											</tr>
											<tr>
												<td>
													<table width="100%" border="1" class="table_style">
														<tr>
															<th width="10">&nbsp;</th>
															<th width="35%"><spring:message code="lts.offerDtl.existVAS" text="Existing VAS "/></th>
															<th><spring:message code="lts.offerDtl.effDate" text="Effective Date"/></th>
															<th><spring:message code="lts.offerDtl.conEndDate" text="Contract End Date"/></th>
															<th><spring:message code="lts.offerDtl.proStartDate" text="Promotion Start Date"/></th>
															<th><spring:message code="lts.offerDtl.proEndDate" text="Promotion End Date"/></th>
															<th><spring:message code="lts.offerDtl.GP" text="Gross Price"/></th>
															<th><spring:message code="lts.offerDtl.EP" text="Effective Price"/></th>
															<th><spring:message code="lts.offerDtl.tpPrem" text="TP Premium"/></th>
														</tr>
														<c:forEach items="${ltsBasketVasDetailCmd.profileExistingVasItemList}" var="profileVas" varStatus="status"> 
															<c:set var="isTpPremium" value="${profileVas.tpPremium != null &&  profileVas.tpPremium != '' }" />
															<c:if test="${ profileVas.itemDetail != null }">
																<c:set var="rowCount" value="${rowCount + 1 }"/>	
																<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" align="center">
																	<td align="center">
																		<form:checkbox path="profileExistingVasItemList[${status.index}].itemDetail.selected" disabled="${isTpPremium}"/>
																	</td>
																	<td>${profileVas.itemDetail.itemDisplayHtml}</td>
																	<td><c:out value="${profileVas.conditionEffStartDate}" default="-"/></td>
																	<td><c:out value="${profileVas.conditionEffEndDate}" default="-"/></td>
																	<td><c:out value="${profileVas.promotionStartDate}" default="-" /></td>
																	<td><c:out value="${profileVas.promotionEndDate}" default="-" /></td>
																	<td>$<c:out value="${profileVas.paidVas ? profileVas.itemDetail.grossEffPrice : '0'}" default="-" /></td>
																	<td>$<c:out value="${profileVas.paidVas ? profileVas.itemDetail.netEffPrice : '0'}" default="-" /></td>
																	<td><c:out value="${isTpPremium ? 'Y' : '-'}" default="-"/></td>
																</tr>
															</c:if>
														</c:forEach>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty ltsBasketVasDetailCmd.imsProfileExistingItemList}">
								<tr>
									<td>
										<br/>
										<table width="98%" align="center">
											<tr>
												<td class="bold"><spring:message code="lts.offerDtl.relateFsaVasOffer" text="Related FSA VAS Offer"/>:</td>
											</tr>
											<tr>
												<td>
													<table width="100%" border="1" class="table_style">
														<tr>
															<th width="10">&nbsp;</th>
															<th width="50%"><spring:message code="lts.offerDtl.existVAS" text="Existing VAS "/></th>
															<th><spring:message code="lts.offerDtl.effDate" text="Effective Date"/></th>
															<th><spring:message code="lts.offerDtl.conEndDate" text="Contract End Date"/></th>
														</tr>
														<c:forEach items="${ltsBasketVasDetailCmd.imsProfileExistingItemList}" var="profileVas" varStatus="status"> 
															<c:if test="${ profileVas.itemDetail != null }">
																<c:set var="rowCount" value="${rowCount + 1 }"/>	
																<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" align="center">
																	<td>
																		<form:checkbox path="imsProfileExistingItemList[${status.index}].itemDetail.selected" disabled="${(profileVas.itemType == 'MP' || profileVas.itemType == 'XMP')}"/>
																	</td>
																	<td>${profileVas.itemDetail.itemDisplayHtml}</td>
																	<td><c:out value="${profileVas.conditionEffStartDate}" default="-"/></td>
																	<td><c:out value="${profileVas.conditionEffEndDate}" default="-"/></td>
																</tr>
															</c:if>
														</c:forEach>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</c:if>
						</table>
					</div>
				</td>
			</tr>					
		</table>
	</div>
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
			<c:if test="${!sessionScope.sessionLtsOrderCapture.ltsMiscellaneousForm.backDateOrder}">
				<c:if test="${not empty ltsBasketVasDetailCmd.profileExistingVasItemList || not empty ltsBasketVasDetailCmd.imsProfileExistingItemList}">
					<div class="func_button">
						<a id="cancelVasBtn" href="#"><spring:message code="lts.offerDtl.cxlVas" text="Cancel VAS"/></a>
					</div>
				</c:if>
			</c:if>
			<div class="button">
				<a id="submitBtn" href="#"><spring:message code="lts.mis.next" text="Next"/></a>
			</div> 
		</td>
	</tr>
</table>


<script type="text/javascript">
	$(ltsbasketvasdetail.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
