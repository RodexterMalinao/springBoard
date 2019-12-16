<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltsnowtvservice.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 3 : 5}" />
</jsp:include>

<form:form method="POST" id="ltsNowTvServiceForm" name="ltsNowTvServiceForm"  commandName="ltsNowTvServiceCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="existingNowTvCust"/>
<form:hidden path="selectedBasketBundleTv"/>

<div id="s_line_text">nowTV Service</div>

<table width="98%" align="center">
	<tr>
		<td>
			<div class="paper_w2 round_10">
			<c:if test="${not empty ltsNowTvServiceCmd.nowTvFreeItemList }">
				<br>
				<table width="98%" border="0" align="center">
				<tr>
					<td>
						<div id="s_line_text">Bundle Channel</div>
							<table border="1" class="table_style" align="center">
								<tr>						
									<th valign="top" width="17%">&nbsp;</th>
									<th valign="top" width="53%">Channel Name</th>
									<th valign="top" width="10%">Monthly Fixed Term Rate</th>
									<th valign="top" width="10%">Month-to-Month Rate </th>
								</tr>
								<c:forEach items="${ltsNowTvServiceCmd.nowTvFreeItemList}" var="nowTvFreeItem" varStatus="status">
									<tr>
										<td valign="middle" class="">
											<form:hidden path="nowTvFreeItemList[${status.index}].selected" />
											${nowTvFreeItem.itemDesc}
										</td>
										<td valign="top" class="">
											${nowTvFreeItem.itemDisplayHtml}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${nowTvFreeItem.recurrentAmtTxt}
										</td>
										<td align="center" valign="top" class="itemPrice">
											${nowTvFreeItem.mthToMthAmtTxt}
										</td>
									</tr>				
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</c:if>
			<br>
			<table width="98%" border="0" align="center">
				<tr>
					<td colspan="3">
						<div id="s_line_text">Additional nowTV Channel Selection</div>
					</td>
				</tr>
				
				<c:if test="${not empty ltsNowTvServiceCmd.additionalTvTypeSelectionList }">
					<tr>
						<td width="2%">&nbsp;</td>
						<td width="95%">
							<br>
							<table border="0" cellpadding="1" cellspacing="1" width="100%">
								<tr>
									<c:forEach items="${ltsNowTvServiceCmd.additionalTvTypeSelectionList}" var="additionalTvTypeSelection">
										<td>
											<form:radiobutton path="additionalTvType"  value="${additionalTvTypeSelection.name }" /><b>${additionalTvTypeSelection.desc}</b><span class="contenttext_red">*</span>
											&nbsp;&nbsp;			
											<c:if test="${additionalTvTypeSelection.name == 'MIRROR' && not empty ltsNowTvServiceCmd.mirrorTvFsaList && ltsNowTvServiceCmd.allowPcdMirror}">
													<form:select path="mirrorFsa">
														<form:option value="">-- SELECT --</form:option>
			                        					<form:options items="${ltsNowTvServiceCmd.mirrorTvFsaList}" itemValue="fsa" itemLabel="fsa" />
			                    					</form:select>
			                    					<form:errors path="mirrorFsa" cssClass="error"/>
	                    					</c:if>
                    					</td>
									</c:forEach>		
								</tr>
							</table>
						</td>
						<td width="2%">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3">
							<br>
							<c:if test="${not empty ltsNowTvServiceCmd.nowTvSpecItemList }"> 
								<div id="specialTvDiv" style="display: none;">	
									<table width="100%" border="0" cellspacing="1" class="contenttext">
										<tr>
											<td width="2%">&nbsp;</td>
											<td width="80%" colspan="2">
												<table width="100%" border="1" class="table_style_sb"> 
													<tr>
														<th valign="top" width="60%">&nbsp;</th>
														<th align="center" width="20%">Monthly Fixed Term Rate</th>
														<th align="center" width="20%">Month-to-Month Rate</th>
													</tr>
				
													<c:forEach items="${ltsNowTvServiceCmd.nowTvSpecItemList}" var="nowTvSpecItem" varStatus="status">
														<tr>
															<td valign="top" class="item_detail">
																<form:checkbox cssClass="nowTvSpecItem" path="nowTvSpecItemList[${status.index}].selected" disabled="${nowTvSpecItem.mdoInd == 'M'}" />
																${nowTvSpecItem.itemDisplayHtml}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${nowTvSpecItem.recurrentAmtTxt}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${nowTvSpecItem.mthToMthAmtTxt}
															</td>									
														</tr>
													</c:forEach>
												</table>
											<td width="3%">&nbsp;</td>
										</tr>
									</table>
								</div>
								<c:if test="${not empty ltsNowTvServiceCmd.nowTvCeleItemList}"> 
									<br>
									<div id="celestialMoivesDiv" style="display: none;">
										<table width="100%" border="0" cellspacing="1" class="contenttext">
											<tr>
												<td width="2%">&nbsp;</td>
												<td width="80%" colspan="2">
													<table width="100%" border="1" class="table_style_sb"> 
														<tr>
															<th valign="top" width="60%">&nbsp;</th>
															<th align="center" width="20%">Monthly Fixed Term Rate</th>
															<th align="center" width="20%">Month-to-Month Rate</th>
														</tr>
					
														<c:forEach items="${ltsNowTvServiceCmd.nowTvCeleItemList}" var="nowTvCeleItem" varStatus="status">
															<tr>
																<td valign="top" class="item_detail">
																	<form:checkbox path="nowTvCeleItemList[${status.index}].selected" disabled="${nowTvCeleItem.mdoInd == 'M'}" />
																	${nowTvCeleItem.itemDisplayHtml}
																</td>
																<td align="center" valign="top" class="itemPrice">
																	${nowTvCeleItem.recurrentAmtTxt}
																</td>
																<td align="center" valign="top" class="itemPrice">
																	${nowTvCeleItem.mthToMthAmtTxt}
																</td>									
															</tr>
														</c:forEach>
													</table>
												</td>
												<td width="3%">&nbsp;</td>
											</tr>
										</table>
									</div>
								</c:if>
							</c:if>
							<c:if test="${not empty ltsNowTvServiceCmd.nowTvPayItemList }"> 
								<div id="payTvDiv" style="display: none;">	
									<table width="100%" border="0" cellspacing="1" class="contenttext">
										<tr>
											<td width="2%">&nbsp;</td>
											<td width="80%" colspan="2">
												<table width="100%" border="1" class="table_style_sb"> 
													<tr>
														<th valign="top" width="60%">&nbsp;</th>
														<th align="center" width="20%">Monthly Fixed Term Rate</th>
														<th align="center" width="20%">Month-to-Month Rate</th>
													</tr>
				
													<c:forEach items="${ltsNowTvServiceCmd.nowTvPayItemList}" var="nowTvPayItem" varStatus="status">
														<tr>
															<td valign="top" class="item_detail">
																<form:checkbox cssClass="nowTvPayItem" path="nowTvPayItemList[${status.index}].selected" disabled="${nowTvPayItem.mdoInd == 'M'}" />
																${nowTvPayItem.itemDisplayHtml}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${nowTvPayItem.recurrentAmtTxt}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${nowTvPayItem.mthToMthAmtTxt}
															</td>									
														</tr>
													</c:forEach>
												</table>
											</td>
											<td width="3%">&nbsp;</td>
										</tr>
									</table>
								</div>
							</c:if>
							<c:if test="${not empty ltsNowTvServiceCmd.nowTvSportItemList }">
								<br>
								<div id="nowTvSportDiv" style="display: none;">	
									<table width="100%" border="0" cellspacing="1" class="contenttext">
										<tr>
											<td width="2%">&nbsp;</td>
											<td width="80%" colspan="2">
												<table width="100%" border="1" class="table_style_sb"> 
													<tr>
														<th valign="top" width="60%">&nbsp;</th>
														<th align="center" width="20%">Monthly Fixed Term Rate</th>
														<th align="center" width="20%">Month-to-Month Rate</th>
													</tr>
				
													<c:forEach items="${ltsNowTvServiceCmd.nowTvSportItemList}" var="nowTvSportItem" varStatus="status">
														<tr>
															<td valign="top" class="item_detail">
																<form:checkbox cssClass="nowTvSportItem" path="nowTvSportItemList[${status.index}].selected" disabled="${nowTvSportItem.mdoInd == 'M'}" />
																${nowTvSportItem.itemDisplayHtml}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${nowTvSportItem.recurrentAmtTxt}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${nowTvSportItem.mthToMthAmtTxt}
															</td>									
														</tr>
													</c:forEach>
												</table>
											</td>
											<td width="3%">&nbsp;</td>
										</tr>
									</table>
								</div>
							</c:if>
							
							<c:if test="${ not empty ltsNowTvServiceCmd.nowTvMirrorItemList}"> 
								<br>
								<div id="mirrorTvDiv" style="display: none;">
									<table width="100%" border="0" cellspacing="1" class="contenttext">
										<tr>
											<td width="80%" colspan="2">
												<table width="100%" border="1" class="table_style_sb"> 
													<tr>
														<th valign="top" width="60%">&nbsp;</th>
														<th align="center" width="20%">Monthly Fixed Term Rate</th>
														<th align="center" width="20%">Month-to-Month Rate</th>
													</tr>
				
													<c:forEach items="${ltsNowTvServiceCmd.nowTvMirrorItemList}" var="nowTvMirrorItem" varStatus="status">
														<tr>
															<td valign="top" class="item_detail">
																<form:checkbox cssClass="nowTvMirrorItem" path="nowTvMirrorItemList[${status.index}].selected" disabled="${nowTvMirrorItem.mdoInd == 'M'}" />
																${nowTvMirrorItem.itemDisplayHtml}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${nowTvMirrorItem.recurrentAmtTxt}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${nowTvMirrorItem.mthToMthAmtTxt}
															</td>									
														</tr>
													</c:forEach>
												</table>
											</td>
										</tr>
									</table>
								</div>
							</c:if>						
							<c:if test="${not empty ltsNowTvServiceCmd.payChannelGroupList }">
								<c:set var="halfCount" value="${fn:length(ltsNowTvServiceCmd.payChannelGroupList) / 2 }" />
								<br>
								<div id="payChannelGroupListDiv" style="display: none;">
									<table width="100%" border="0" cellspacing="1" class="contenttext">
										<tr valign="top">
											<td width="50%">
												<table width="100%" border="0" cellspacing="1">
						   							<tr>
														<td valign="top" width="20">&nbsp;</td>
														<td valign="top" width="10">&nbsp;</td>
														<td valign="top" width="10%">
															<span>Ch No.</span>
														</td>
														<td valign="top" width="80%">
															<span>Channel Name</span>				
														</td>
													</tr>
													<c:forEach items="${ltsNowTvServiceCmd.payChannelGroupList}" varStatus="payChannelGroupStatus" var="payChannelGroup">
														<c:if test="${payChannelGroupStatus.index < halfCount }">
															<tr>
																<td valign="top" width="20">
																	&nbsp;
																</td>
																<td valign="top" width="60%" bgcolor="#EAEAEA" colspan="3">
																	<span class="item_title_vas">${payChannelGroup.channelGroupHtml}</span>				
																</td>
															</tr>
															<c:forEach items="${payChannelGroup.channelDetails }" var="channelDetail" varStatus="channelDetailStatus">
																<tr>
																	<td valign="top" width="20">&nbsp;</td>
																	<td valign="top" width="10">
																		<form:checkbox id="${channelDetail.channelId}" cssClass="channelCheckbox" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].selected" disabled="${channelDetail.mdoInd == 'M'}"/>
																	</td>
																	<td valign="top" width="10%">
																		<span class="item_detail">${channelDetail.channelCd}</span>
																	</td>
																	<td valign="top" width="80%">
																		<span class="item_detail">${channelDetail.channelHtml}</span>		
																	</td>
																</tr>
																<c:if test="${not empty channelDetail.channelAttbs}"> 
																    <c:set var="displaySeq" value="0" />
																	<c:forEach items="${channelDetail.channelAttbs }" var="channelAttb" varStatus="channelAttbStatus">
																		<c:if test="${displaySeq != '0' && channelAttb.displaySeq != displaySeq}">
																			    </td>
																		    </tr>
																		</c:if>
																		<c:choose>
																			<c:when test="${channelAttb.displaySeq != displaySeq }">
																				<tr>
																			        <td valign="top" width="20">&nbsp;</td>
																				    <td valign="top" width="10">&nbsp;</td>
																				    <td valign="top" width="10%">&nbsp;</td>
																				    <td valign="top" width="80%">
																				        <c:if test="${channelAttb.inputMethod == 'TEXT'}">
																				        	${channelAttb.attbDesc}
																				        </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'INPUT'}">
																					        ${channelAttb.attbDesc}<form:input cssClass="${channelDetail.channelId}" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].attbValue" /> 
																					    </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'CHECK'}">
																					       <form:checkbox cssClass="${channelDetail.channelId}" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].selected" /> ${channelAttb.attbDesc}
																					    </c:if>
																			</c:when>
																		    <c:otherwise>
																		    			<c:if test="${channelAttb.inputMethod == 'TEXT'}">
																				        	${channelAttb.attbDesc}
																				        </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'INPUT'}">
																					        ${channelAttb.attbDesc}<form:input cssClass="${channelDetail.channelId}" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].attbValue" /> 
																					    </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'CHECK'}">
																					        <form:checkbox cssClass="${channelDetail.channelId}" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].selected" /> ${channelAttb.attbDesc}
																					    </c:if>
																		    </c:otherwise>
																		</c:choose>
																	 	<c:set var="displaySeq" value="${channelAttb.displaySeq}" />
																		</c:forEach>
																</c:if>
															</c:forEach>
														</c:if>
													</c:forEach>
												</table>
											</td>
											<td width="50%">
												<c:if test="${fn:length(ltsNowTvServiceCmd.payChannelGroupList) > 1 }">
													<table width="100%" border="0" cellspacing="1">
							   							<tr>
															<td valign="top" width="20">&nbsp;</td>
															<td valign="top" width="10">&nbsp;</td>
															<td valign="top" width="10%">
																<span>Ch No.</span>
															</td>
															<td valign="top" width="80%">
																<span>Channel Name</span>				
															</td>
														</tr>
														<c:forEach items="${ltsNowTvServiceCmd.payChannelGroupList}" varStatus="payChannelGroupStatus" var="payChannelGroup">
															<c:if test="${payChannelGroupStatus.index >= halfCount }">
																<tr>
																	<td valign="top" width="20">
																		&nbsp;
																	</td>
																	<td valign="top" width="60%" bgcolor="#EAEAEA" colspan="3">
																		<span class="item_title_vas">${payChannelGroup.channelGroupHtml}</span>				
																	</td>
																</tr>
																<c:forEach items="${payChannelGroup.channelDetails }" var="channelDetail" varStatus="channelDetailStatus">
																	<tr>
																		<td valign="top" width="20">&nbsp;</td>
																		<td valign="top" width="10">
																			<form:checkbox id="${channelDetail.channelId}" cssClass="channelCheckbox" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].selected" disabled="${channelDetail.mdoInd == 'M'}"/>
																		</td>
																		<td valign="top" width="10%">
																			<span class="item_detail">${channelDetail.channelCd}</span>
																		</td>
																		<td valign="top" width="80%">
																			<span class="item_detail">${channelDetail.channelHtml}</span>				
																		</td>
																	</tr>
																<c:if test="${not empty channelDetail.channelAttbs}"> 
																    <c:set var="displaySeq" value="0" />
																	<c:forEach items="${channelDetail.channelAttbs }" var="channelAttb" varStatus="channelAttbStatus">
																		<c:if test="${displaySeq != '0' && channelAttb.displaySeq != displaySeq}">
																			    </td>
																		    </tr>
																		</c:if>
																		<c:choose>
																			<c:when test="${channelAttb.displaySeq != displaySeq }">
																				<tr>
																			        <td valign="top" width="20">&nbsp;</td>
																				    <td valign="top" width="10">&nbsp;</td>
																				    <td valign="top" width="10%">&nbsp;</td>
																				    <td valign="top" width="80%">
																				        <c:if test="${channelAttb.inputMethod == 'TEXT'}">
																				        	${channelAttb.attbDesc}
																				        </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'INPUT'}">
																					        ${channelAttb.attbDesc}<form:input cssClass="${channelDetail.channelId}" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].attbValue" /> 
																					    </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'CHECK'}">
																					       <form:checkbox cssClass="${channelDetail.channelId}" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].selected" /> ${channelAttb.attbDesc}
																					    </c:if>
																			</c:when>
																		    <c:otherwise>
																		    			<c:if test="${channelAttb.inputMethod == 'TEXT'}">
																				        	${channelAttb.attbDesc}
																				        </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'INPUT'}">
																					        ${channelAttb.attbDesc}<form:input cssClass="${channelDetail.channelId}" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].attbValue" /> 
																					    </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'CHECK'}">
																					        <form:checkbox cssClass="${channelDetail.channelId}" path="payChannelGroupList[${payChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].selected" /> ${channelAttb.attbDesc}
																					    </c:if>
																		    </c:otherwise>
																		</c:choose>
																	 	<c:set var="displaySeq" value="${channelAttb.displaySeq}" />
																		</c:forEach>
																	</c:if>	
																</c:forEach>
															</c:if>
														</c:forEach>
													</table>
												</c:if>
											</td>
										</tr>
									</table>
								</div>
							</c:if>
							<c:if test="${not empty ltsNowTvServiceCmd.specChannelGroupList }">
								<c:set var="halfCount" value="${fn:length(ltsNowTvServiceCmd.specChannelGroupList) / 2 }" />
								<br>
								<div id="specChannelGroupListDiv" style="display: none;">
									<table width="100%" border="0" cellspacing="1" class="contenttext">
										<tr valign="top">
											<td width="50%">
												<table width="100%" border="0" cellspacing="1">
						   							<tr>
														<td valign="top" width="20">&nbsp;</td>
														<td valign="top" width="10">&nbsp;</td>
														<td valign="top" width="10%">
															<span>Ch No.</span>
														</td>
														<td valign="top" width="80%">
															<span>Channel Name</span>				
														</td>
													</tr>
													<c:forEach items="${ltsNowTvServiceCmd.specChannelGroupList}" varStatus="specChannelGroupStatus" var="specChannelGroup">
														<c:if test="${specChannelGroupStatus.index < halfCount }">
															<tr>
																<td valign="top" width="20">
																	&nbsp;
																</td>
																<td valign="top" width="60%" bgcolor="#EAEAEA" colspan="3">
																	<span class="item_title_vas">${specChannelGroup.channelGroupHtml}</span>				
																</td>
															</tr>
															<c:forEach items="${specChannelGroup.channelDetails }" var="channelDetail" varStatus="channelDetailStatus">
																<tr>
																	<td valign="top" width="20">&nbsp;</td>
																	<td valign="top" width="10">
																		<form:checkbox id="${channelDetail.channelId}" cssClass="channelCheckbox" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].selected" disabled="${channelDetail.mdoInd == 'M'}"/>
																	</td>
																	<td valign="top" width="10%">
																		<span class="item_detail">${channelDetail.channelCd}</span>
																	</td>
																	<td valign="top" width="80%">
																		<span class="item_detail">${channelDetail.channelHtml}</span>				
																	</td> 
																</tr>
																<c:if test="${not empty channelDetail.channelAttbs}"> 
																    <c:set var="displaySeq" value="0" />
																	<c:forEach items="${channelDetail.channelAttbs }" var="channelAttb" varStatus="channelAttbStatus">
																		<c:if test="${displaySeq != '0' && channelAttb.displaySeq != displaySeq}">
																			    </td>
																		    </tr>
																		</c:if>
																		<c:choose>
																			<c:when test="${channelAttb.displaySeq != displaySeq }">
																				<tr>
																			        <td valign="top" width="20">&nbsp;</td>
																				    <td valign="top" width="10">&nbsp;</td>
																				    <td valign="top" width="10%">&nbsp;</td>
																				    <td valign="top" width="80%">
																				        <c:if test="${channelAttb.inputMethod == 'TEXT'}">
																				        	${channelAttb.attbDesc}
																				        </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'INPUT'}">
																					         ${channelAttb.attbDesc}<form:input cssClass="${channelDetail.channelId}" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].attbValue" /> 
																					    </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'CHECK'}">
																					       <form:checkbox cssClass="${channelDetail.channelId}" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].selected" /> ${channelAttb.attbDesc}
																					    </c:if>
																			</c:when>
																		    <c:otherwise>
																		    			<c:if test="${channelAttb.inputMethod == 'TEXT'}">
																				        	${channelAttb.attbDesc}
																				        </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'INPUT'}">
																					        ${channelAttb.attbDesc}<form:input cssClass="${channelDetail.channelId}" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].attbValue" /> 
																					    </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'CHECK'}">
																					        <form:checkbox cssClass="${channelDetail.channelId}" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].selected" /> ${channelAttb.attbDesc}
																					    </c:if>
																		    </c:otherwise>
																		</c:choose>
																	 	<c:set var="displaySeq" value="${channelAttb.displaySeq}" />
																	</c:forEach>
																</c:if>
															</c:forEach>
														</c:if>
													</c:forEach>
												</table>
											</td>
											<td width="50%">
												<c:if test="${fn:length(ltsNowTvServiceCmd.specChannelGroupList) > 1}">
													<table width="100%" border="0" cellspacing="1">
							   							<tr>
															<td valign="top" width="20">&nbsp;</td>
															<td valign="top" width="10">&nbsp;</td>
															<td valign="top" width="10%">
																<span>Ch No.</span>
															</td>
															<td valign="top" width="80%">
																<span>Channel Name</span>				
															</td>
														</tr>
														<c:forEach items="${ltsNowTvServiceCmd.specChannelGroupList}" varStatus="specChannelGroupStatus" var="specChannelGroup">
															<c:if test="${specChannelGroupStatus.index >= halfCount }">
																<tr>
																	<td valign="top" width="20">
																		&nbsp;
																	</td>
																	<td valign="top" width="60%" bgcolor="#EAEAEA" colspan="3">
																		<span class="item_title_vas">${specChannelGroup.channelGroupHtml}</span>				
																	</td>
																</tr>
																<c:forEach items="${specChannelGroup.channelDetails }" var="channelDetail" varStatus="channelDetailStatus">
																	<tr>
																		<td valign="top" width="20">&nbsp;</td>
																		<td valign="top" width="10">
																			<form:checkbox id="${channelDetail.channelId}" cssClass="channelCheckbox" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].selected" disabled="${channelDetail.mdoInd == 'M'}"/>
																		</td>
																		<td valign="top" width="10%">
																			<span class="item_detail">${channelDetail.channelCd}</span>
																		</td>
																		<td valign="top" width="80%">
																			<span class="item_detail">${channelDetail.channelHtml}</span>			
																		</td>
																	</tr>
																	<c:if test="${not empty channelDetail.channelAttbs}"> 
																    <c:set var="displaySeq" value="0" />
																	<c:forEach items="${channelDetail.channelAttbs }" var="channelAttb" varStatus="channelAttbStatus">
																		<c:if test="${displaySeq != '0' && channelAttb.displaySeq != displaySeq}">
																			    </td>
																		    </tr>
																		</c:if>
																		<c:choose>
																			<c:when test="${channelAttb.displaySeq != displaySeq }">
																				<tr>
																			        <td valign="top" width="20">&nbsp;</td>
																				    <td valign="top" width="10">&nbsp;</td>
																				    <td valign="top" width="10%">&nbsp;</td>
																				    <td valign="top" width="80%">
																				        <c:if test="${channelAttb.inputMethod == 'TEXT'}">
																				        	${channelAttb.attbDesc}
																				        </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'INPUT'}">
																					         ${channelAttb.attbDesc}<form:input cssClass="${channelDetail.channelId}" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].attbValue" /> 
																					    </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'CHECK'}">
																					       <form:checkbox cssClass="${channelDetail.channelId}" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].selected" /> ${channelAttb.attbDesc}
																					    </c:if>
																			</c:when>
																		    <c:otherwise>
																		    			<c:if test="${channelAttb.inputMethod == 'TEXT'}">
																				        	${channelAttb.attbDesc}
																				        </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'INPUT'}">
																					        ${channelAttb.attbDesc}<form:input cssClass="${channelDetail.channelId}" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].attbValue" /> 
																					    </c:if>
																					    <c:if test="${channelAttb.inputMethod == 'CHECK'}">
																					        <form:checkbox cssClass="${channelDetail.channelId}" path="specChannelGroupList[${specChannelGroupStatus.index}].channelDetails[${channelDetailStatus.index }].channelAttbs[${channelAttbStatus.index }].selected" /> ${channelAttb.attbDesc}
																					    </c:if>
																		    </c:otherwise>
																		</c:choose>
																	 	<c:set var="displaySeq" value="${channelAttb.displaySeq}" />
																	</c:forEach>
																</c:if>
																</c:forEach>
															</c:if>
														</c:forEach>
													</table>
												</c:if>
											</td>
										</tr>
									</table>
								</div>
							</c:if>
						</td>
					</tr>
					<spring:bind path="ltsNowTvServiceCmd.additionalTvType">
						  <c:if test="${status.error}">
						   	<tr>
								<td width="2%">&nbsp;</td>
								<td>
									<div id="error" class="ui-widget" style="visibility: visible; width: 70%;">
										<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
											<p>
												<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
											</p>
											<div id="error_msg" class="contenttext">
												<form:errors path="additionalTvType"/>
											</div>
											<p></p>
										</div>
									</div>
								</td>
								<td width="5%">&nbsp;</td>
							</tr>
						  </c:if>
					</spring:bind>
				</c:if>	
			</table>
			
			<div id="nowTvAdultDiv" style="display: none;">
				<br>
				<table width="98%" border="0" align="center">
					<tr>
						<td colspan="3">
							<div id="s_line_text"> nowTV Service Information</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td width="2%">&nbsp;</td>
						<td>
							<form:checkbox path="confirmAvAdultChannel" disabled="${ltsNowTvServiceCmd.existingNowTvCust  && !ltsNowTvServiceCmd.allow2L2B}"/>
							I wish to view AV On-Demand and adult channel content previews on nowTV
							<br>
							<form:errors path="confirmAvAdultChannel" cssClass="error"/>
						</td>
						<td width="2%">&nbsp;</td>
					</tr>
					<c:if test="${ltsNowTvServiceCmd.showDateOfBirth}">
						<tr>
							<td width="2%">&nbsp;</td>
							<td>
								<br>
								<div id="dateOfBirthDiv">
									<table width="100%" border="0">
										<tr>
											<td width="10%">Date of Birth<span class="contenttext_red">*</span> :</td>
											<td colspan="2">
												<form:input path="dateOfBirth" id="dateOfBirthDatepicker" readonly="true" cssClass="bold"/>
												<form:errors path="dateOfBirth" cssClass="error"/>
											</td>		 
										</tr>
									</table>
								</div>
							</td>
							<td width="2%">&nbsp;</td>
						</tr>
					</c:if>
				</table>
			</div>
			<br>

			<div id="nowTvBillOptionDiv">
				<table width="98%" border="0" align="center">
					<tr>
						<td colspan="3">
							<div id="s_line_text"> nowTV Service Information</div>
						</td>
					</tr>
					<c:choose>
						<c:when test="${not empty ltsNowTvServiceCmd.nowTvEmailItemList ||  not empty ltsNowTvServiceCmd.nowTvPrintItemList || not empty ltsNowTvServiceCmd.nowTvDeviceItemList}" >
							<tr>
								<td colspan="3">Please choose one of the following billing options in case you have to pay charges for subscription to the other additional nowTV channels : </td>
							</tr>
							<tr>
								<td width="100%" colspan="3" >
									<table width="100%" border="0" class="table_style_sb">
										<tr>
											<th colspan="2">&nbsp;</th>
											<th width="20%" align="center">Amount</th>									
										</tr>
										<c:forEach items="${ltsNowTvServiceCmd.nowTvEmailItemList }" var="nowTvEmailItem" varStatus="nowTvEmailItemStatus">
											<tr>
												<td width="3%">
													<form:checkbox cssClass="nowTvBillOption emailBill" path="nowTvEmailItemList[${nowTvEmailItemStatus.index}].selected" disabled="${nowTvEmailItem.mdoInd == 'M'}" />
												</td>
												<td width="75%" class="item_detail">
													${nowTvEmailItem.itemDisplayHtml}
													&nbsp;&nbsp; <form:input path="nowTvEmail" /> &nbsp;&nbsp; <form:errors path="nowTvEmail" cssClass="error"/>
												</td>
												<td class="itemPrice">
													${nowTvEmailItem.recurrentAmtTxt}
												</td>									
											</tr>
										</c:forEach>
										<c:forEach items="${ltsNowTvServiceCmd.nowTvPrintItemList }" var="nowTvPrintItem" varStatus="nowTvPrintItemStatus">
											<tr>
												<td width="3%">
													<form:checkbox cssClass="nowTvBillOption printBill" path="nowTvPrintItemList[${nowTvPrintItemStatus.index}].selected" disabled="${nowTvPrintItem.mdoInd == 'M'}" />
												</td>
												<td width="75%" class="item_detail">
													${nowTvPrintItem.itemDisplayHtml}
													<c:if test="${not empty nowTvPrintItem.itemAttbs}"> 
														<c:forEach items="${nowTvPrintItem.itemAttbs }" var="itemAttb" varStatus="itemAttbStatus">
															<br> ${itemAttb.attbDesc}
															&nbsp;<form:input path="nowTvPrintItemList[${nowTvPrintItemStatus.index}].itemAttbs[${itemAttbStatus.index}].attbValue"/>
														</c:forEach>
														<br>
													</c:if>
												</td>
												<td class="itemPrice">
													${nowTvPrintItem.recurrentAmtTxt}
												</td>									
											</tr>
										</c:forEach>
										<c:forEach items="${ltsNowTvServiceCmd.nowTvDeviceItemList }" var="nowTvDeviceItem" varStatus="nowTvDeviceItemStatus">
											<tr>
												<td width="3%">
													<form:checkbox cssClass="nowTvBillOption deviceBill" path="nowTvDeviceItemList[${nowTvDeviceItemStatus.index}].selected" disabled="${nowTvDeviceItem.mdoInd == 'M'}" />
												</td>
												<td width="75%" class="item_detail">
													${nowTvDeviceItem.itemDisplayHtml}
													<c:if test="${not empty nowTvDeviceItem.itemAttbs}"> 
														<c:forEach items="${nowTvDeviceItem.itemAttbs }" var="itemAttb" varStatus="itemAttbStatus">
															<br> ${itemAttb.attbDesc}
															&nbsp;<form:input path="nowTvDeviceItemList[${nowTvDeviceItemStatus.index}].itemAttbs[${itemAttbStatus.index}].attbValue"/>
														</c:forEach>
														<br>
													</c:if>
												</td>
												<td class="itemPrice">
													${nowTvDeviceItem.recurrentAmtTxt}
												</td>									
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td width="100%" colspan="3">
									<table width="100%" cellpadding="1" cellspacing="1" class="contenttext">
										<tr>
											<td width="10">&nbsp;</td>
											<td class="item_title_rp">Follow existing TV payment and bill media</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
				</table>
			</div>
			</div>					
		</td>
	</tr>
</table>


<c:if test="${not empty requestScope.errorMsgList}">
	<br>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="contenttext">
		<tr>
			<td width="2%">&nbsp;</td>
			<td>
				<div id="error" class="ui-widget" style="visibility: visible; width: 70%;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<c:forEach items="${requestScope.errorMsgList}" var="errorMsg">
							<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							</p>
							<div id="error_msg" class="contenttext">
								${errorMsg}
							</div>
							<p></p>										
						</c:forEach>
					</div>
				</div>
			</td>
			<td width="5%">&nbsp;</td>
		</tr>
		</table>
	</c:if>
</form:form>

<br>

<table width="100%" border="0" cellspacing="0">
	<tr>
		<td align="right"> 
			<div class="button">
				<a id="submitBtn" href="#">Next</a>
			</div> 
		</td>
	</tr>
</table>

<script language="javascript">
	$(ltsnowtvservice.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>