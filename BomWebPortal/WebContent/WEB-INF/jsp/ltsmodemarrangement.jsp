<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<script type="text/javascript" src="js/web/lts/ltsmodemarrangement.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="4" />
</jsp:include>


<form:form method="POST" id="ltsModemArrangementForm" name="ltsModemArrangementForm" commandName="ltsModemArrangementCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="formAction"/>
<form:hidden path="preSelectFsa"/>
<form:hidden path="abnormalAddrCoverageInd" id="abnormalAddrCoverageInd"/>
<input type="hidden" id="profileFsa" value="${sessionScope.sessionLtsOrderCapture.ltsServiceProfile.eyeFsaProfile.fsa}"/>
<table width="100%">
	<tr>
		<td>
			<div id="s_line_text"><spring:message code="lts.modemArrangememt.modArrg" text="Modem Arrangement"/></div>
			<table width="100%" border="0" cellspacing="0">
				<tr>
					<td>
						<c:if test="${not empty ltsModemArrangementCmd.modemTypeSelectionList}">
							<table width="100%" border="0" cellspacing="1" class="contenttext">
								<tr>
									<c:forEach items="${ltsModemArrangementCmd.modemTypeSelectionList}" var="modemTypeSelection">
										<td>
											<form:radiobutton path="modemType" value="${modemTypeSelection.name}"/>
											<b><spring:message code="${modemTypeSelection.desc}" text="${modemTypeSelection.engDesc}"/></b><span class="contenttext_red">*</span>
										</td>
									</c:forEach>
								</tr>
							</table>
							<br>
						</c:if>
					</td>
				</tr>

				<tr>
					<td valign="top" >
					<div id="targetIaDiv">	
						<table width="100%" border="0" cellspacing="0" class="contenttext">	
							<tr>
								<td colspan="4">&nbsp;</td>
							</tr>	
							<tr>
								<td width="1%">&nbsp;</td>
								<td colspan="3">
									<spring:message code="lts.modemArrangememt.targetIA" text="Target Installation Address"/> :  
										<b><c:out value="${sessionScope.sessionLtsOrderCapture.newAddressRollout.fullAddress}" /></b>
								</td>
							</tr>
							<tr>
								<td colspan="4">&nbsp;</td>
							</tr>					
						</table>
					</div>
					<br>					
					<c:if test="${ltsModemArrangementCmd.otherFsaExistInSameIa}">
						<div id="diffFsaUnderIaDiv" style="display: none;">
							<table width="100%" >
								<tr>
									<td width="5%">&nbsp;</td>
									<td width="60%" align="left">
									<div id="warning_addr" class="ui-widget" style="visibility: visible;">
										<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
											<p>
												<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
											</p>
											<div id="warning_addr_msg" class="contenttext">
												<b><spring:message code="lts.modemArrangememt.otherFSAexist" text="Other FSA exist under same installation address"/></b>
											</div>
											<p></p>
										</div>
									</div>
									</td>
									<td width="55%">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
							</table>
						</div>
						<br>
					</c:if>

					<c:if test="${not empty ltsModemArrangementCmd.lineTypeSelectionList}">
					<div id="lineTypeSelectionDiv" class="paper_w2 round_10">
						<br/>
						<table width="98%" border="0" cellspacing="0" class="contenttext" align="center">																							
							<tr>
								<td colspan="4">
									<div id="s_line_text"><spring:message code="lts.modemArrangement.lineTypeSelection" text="Line Type Selection"/></div>
									<table width="100%" border="0" cellspacing="0" class="contenttext">	
										<tr>
											<td width="100%" colspan="4">&nbsp;</td>
										</tr>																	
										<tr>
											<td width="100%" colspan="4">
											
												<table class="table_style" width="100%" border="1" cellspacing="0">
													<tr height="30">
														<th width="1%" align="center">&nbsp;</th>
														<th width="30%" align="center"><spring:message code="lts.modemArrangement.lineType" text="Line Type"/></th>
														<th width="70%" align="center"><spring:message code="lts.modemArrangement.status" text="Status"/></th>
													</tr>
													<c:forEach items="${ ltsModemArrangementCmd.lineTypeSelectionList }" var="lineType" varStatus="lineTypeStatus">
														
														<tr bgcolor="#FFFFFF" height="50">
															<td align="center">
																<form:checkbox path="lineTypeSelectionList[${lineTypeStatus.index}].selected" />
															</td>
															<td align="center">
																<c:choose>
																	<c:when test="${'A' == lineType.technology}">
																		ADSL
																	</c:when>
																	<c:when test="${'V' == lineType.technology}">
																		VDSL
																	</c:when>
																	<c:when test="${'P' == lineType.technology}">
																		PON
																	</c:when>
																</c:choose>
															</td>
															<td align="center">
																<c:out value="${lineType.status }" />
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
						<br/>
						</div>
					</c:if>
					<div id="existingFsaDiv" style="display: none;" class="paper_w2 round_10">
						<br/>
						<table width="98%" border="0" cellspacing="0" class="contenttext" align="center">																							
							<tr>
								<td colspan="4">
									<div id="s_line_text"><spring:message code="lts.modemArrangememt.fsaSelection" text="FSA selection"/></div>
									<table width="100%" border="0" cellspacing="0" class="contenttext">	
										<tr>
											<td width="100%" colspan="4">&nbsp;</td>
										</tr>																	
										<tr>
											<td width="100%" colspan="4">
												<c:if test="${not empty ltsModemArrangementCmd.errorMsgList}">
													<div id="retrieveExistingFsaErrorDiv">
														<table width="100%" border="0" cellspacing="0" class="contenttext">	
															<tr>
																<td width="70%" align="left">
																	<div id="warning" class="ui-widget" style="visibility: visible;">
																		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
																			<c:forEach items="${ltsModemArrangementCmd.errorMsgList}" var="errorMsg">
																			<p>
																				<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																			</p>
																			<div id="warning_msg" class="contenttext">
																				${errorMsg} 
																			</div>
																			<p></p>
																			</c:forEach>
																		</div>
																	</div>		
																</td>
																<td>&nbsp;</td>
															</tr>
														</table>
														<br>
													</div>
												</c:if>	
												<c:if test="${not empty ltsModemArrangementCmd.existingFsaList}">
													<table class="table_style" width="100%" border="1" cellspacing="0">
														<tr height="30">
															<th width="1%" align="center">&nbsp;</th>
															<th width="5%" align="center"><spring:message code="lts.modemArrangememt.fsa" text="FSA  "/></th>
															<th width="10%" align="center"><spring:message code="lts.modemArrangememt.existSrv" text="Existing Service"/></th>
															<th width="10%" align="center"><spring:message code="lts.modemArrangememt.loginID" text="Login ID"/></th>
															<th width="10%" align="center"><spring:message code="lts.modemArrangememt.effDate" text="Effective Date"/></th>
															<th width="5%" align="center"><spring:message code="lts.modemArrangememt.nowTVUpg" text="nowTV Upgrade"/></th>
															<th width="5%" align="center"><spring:message code="lts.modemArrangememt.pcdUpg" text="PCD Upgrade"/></th>
															<th width="5%" align="center"><spring:message code="lts.modemArrangememt.nowTVChannel" text="nowTV Channel"/></th>
															<th width="5%" align="center"><spring:message code="lts.modemArrangememt.newModArrg" text="New Modem Arrange"/></th>
															<th width="40%" align="center"><spring:message code="lts.modemArrangememt.difIA" text="Different I/A"/></th>
															<th width="5%" align="center"><spring:message code="lts.modemArrangememt.pendingOrd" text="Pending Order"/></th>
														</tr>
														<c:forEach items="${ ltsModemArrangementCmd.existingFsaList }" var="existingFsa" varStatus="existingFsaStatus">
															<form:hidden id="fsaRouterGrp${existingFsa.fsa}" path="existingFsaList[${existingFsaStatus.index}].routerGrp"/>
															<form:hidden id="fsaMeshRouterGrp${existingFsa.fsa}" path="existingFsaList[${existingFsaStatus.index}].meshRouterGrp"/>
															<tr bgcolor="#FFFFFF" title="${existingFsa.notAllowToShare ? existingFsa.notAllowToShareReason : ''}" height="50" style="<c:out value="${existingFsa.notAllowToShare ? 'color: graytext;' : '' }"/>"  >
																<td align="center">
																	<c:choose>
																	<c:when test="${existingFsa.notAllowToShare}">
																		<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																	</c:when>
																	<c:otherwise>
																		<form:checkbox id="${existingFsa.fsa}" path="existingFsaList[${existingFsaStatus.index}].selected" disabled="${existingFsa.notAllowToShare}" cssClass="existingFsaCheckbox ${existingFsa.differentIa ? 'isDifferentIa' : ''} ${existingFsa.allowConfirmSameIa ? 'allowConfirmSameIa' : ''}"/>
																	</c:otherwise>																	
																	</c:choose>
																</td>
																<td align="center">
																	<c:out default="--" value="${existingFsa.fsa}"/>
																</td>
																<td align="center">
																	<form:hidden id="existingFsa.existingService${existingFsa.fsa}" path="existingFsaList[${existingFsaStatus.index}].existingService.name" />
																	<c:out default="--" value="${existingFsa.existingService.desc }"/>
																</td>
																<td align="center">
																	<c:out default="--" value="${existingFsa.loginId}"/>
																</td>
																<td align="center">
																	<c:out default="--" value="${existingFsa.effectiveDate}"/>
																</td>
																<td align="center">
																	<form:select id="existingFsa.newService${existingFsa.fsa}" cssClass="existingFsaNewService existingFsa${existingFsa.fsa}" path="existingFsaList[${existingFsaStatus.index}].newService" disabled="true">
																		<form:option value="" label="" />
																		<form:options items="${existingFsa.futureFsaServiceList}" itemValue="name" itemLabel="desc" />
																	</form:select>
																	
																</td>
																<td align="center">
																	<form:select id="existingFsa.upgradeBandwidth${existingFsa.fsa}" cssClass="existingFsaUpgradeBandwidth existingFsa${existingFsa.fsa}" path="existingFsaList[${existingFsaStatus.index}].upgradeBandwidth" disabled="true">
																		<form:option value="" label="" />
																		<form:options items="${existingFsa.upgradeBandwidthMap}" />
																	</form:select>
																</td>						
																<td align="center">
																	<c:choose>
																		<c:when test="${existingFsa.nowTvServiceDetail != null && !existingFsa.notAllowToShare}">
																			<a href="ltsnowtvchannel.html?sbuid=${sessionScope.sbuid}&fsa=${existingFsa.fsa}" onclick="window.open(this.href, 'nowTV Channel List', 'width=600, height=400, scrollbars=yes'); return false;">Details</a>
																		</c:when>
																		<c:otherwise>
																			--
																		</c:otherwise>
																	</c:choose>
																</td>
																<td align="center">
																	<span id="display.existingFsa.modemArrange${existingFsa.fsa}" class="existingFsa.modemArrange" >
																		<c:if test="${!existingFsa.notAllowToShare }">
																			<c:choose>
																				<c:when test="${ existingFsa.newModemArrange != null && existingFsa.newModemArrange != '' }">
																					<b><c:out value="${existingFsa.newModemArrange}"/></b>
																				</c:when>
																				<c:otherwise>
																					<c:out default="--" value="${existingFsa.modemArrange}"/>
																				</c:otherwise>
																			</c:choose>
																		</c:if>
																	</span>
																	
																	<form:hidden id="existingFsa.modemArrange${existingFsa.fsa}" path="existingFsaList[${existingFsaStatus.index}].modemArrange" />
																	<form:hidden id="existingFsa.newModemArrange${existingFsa.fsa}" path="existingFsaList[${existingFsaStatus.index}].newModemArrange" />
																</td>
																<td align="center">
																	<c:choose>
																		<c:when test="${existingFsa.differentIa }">
																			<span style="color: red;">${existingFsa.addressDetail.fullAddress}</span>
																		</c:when>
																		<c:otherwise>
																			--
																		</c:otherwise>
																	</c:choose>
																</td>
																<td align="center">
																	<c:choose>
																		<c:when test="${existingFsa.pendingOrderExist }">
																			<span style="color: red;">Y</span>
																		</c:when>
																		<c:otherwise>
																			--
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
														</c:forEach>														
													</table>
													<div id="existingFsaDiffIaDiv" style="display: none;">
														<table width="100%" border="0" cellspacing="0" class="contenttext">	
															<tr>
																<td width="70%" align="left">
																	<div id="warning" class="ui-widget" style="visibility: visible;">
																		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
																			<p>
																				<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																			</p>
																			<div id="warning_msg" class="contenttext">
																				<spring:message code="lts.modemArrangememt.notSameIA" text="Selected FSA is not under same installation address!"/>
																			</div>
																			<p></p>
																		</div>
																	</div>		
																</td>
																<td>&nbsp;</td>
															</tr>
														</table>
														<br>
														<table width="100%" border="0" cellspacing="4" class="contenttext">															
															<tr>
																<td width="35%">
																	<spring:message code="lts.modemArrangememt.externalRelocationIMStoEyeIA" text="External relocation of selected IMS service to Target eye IA"/><span class="contenttext_red">*</span> :   
																</td>
																<td>
																	<form:radiobutton path="existingFsaER" value="true"/> <b>Yes</b>
																	&nbsp;
																	<form:radiobutton path="existingFsaER" value="false"/> <b>No</b>
																</td>
															</tr>
														</table>
														<div id="existingFsaConfirmSameIaDiv">
															<table width="100%" border="0" cellspacing="4" class="contenttext">															
																<tr>
																	<td width="60%">
																		<b><spring:message code="lts.modemArrangememt.confirmOrderUnderSameAddress" text="Confirm selected FSA under same installation address"/><span class="contenttext_red">*</span> : </b>
																		<form:checkbox path="existingFsaConfirmSameIa"/>   
																	</td>
																</tr>
															</table>	
														</div>
													</div>
												</c:if>
											</td>
										</tr>
										<tr>
											<td width="100%" colspan="4"><b>&nbsp;</b></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
					
					
					<div id="otherFsaDiv"  style="display: none;" class="paper_w2 round_10">
						<br/>
						<table width="98%" border="0" cellspacing="0" class="contenttext" align="center">																							
							<tr>
								<td colspan="4">
									<div id="s_line_text"><spring:message code="lts.modemArrangememt.fsaSelection" text="FSA Selection"/></div>
									<table width="100%" border="0" cellspacing="0" class="contenttext">
										<tr>
											<td colspan="4">
												<span style="color: red;"><spring:message code="lts.modemArrangement.otherFsaWarnMsg" text="* This function for same customer only"/></span>
											</td>
										</tr>						
										<tr>
											<td colspan="4">
												<table width="100%" border="0" class="contenttext">
													<tr>
														<td width="15%" align="right"><spring:message code="lts.modemArrangememt.docTypeNum" text="Doc. Type & Num"/><span class="contenttext_red">*</span> : </td>
														<td colspan="3">
															<form:select path="inputDocType">
																<form:option value="HKID" label="HKID" />
																<form:option value="PASS" label="Passport" />
															</form:select>
															<form:input path="inputDocNum"/>
														</td>
													</tr>								
													<tr>
														<td align="right"><spring:message code="lts.modemArrangememt.fsa" text="FSA"/> : </td>
														<td colspan="3">
															<form:input path="inputOtherFsa"/>
														</td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.modemArrangememt.pcdLoginId" text="PCD Login ID"/> : </td>
														<td colspan="2">
															<form:input path="inputPcdLoginId" />
															<form:select path="inputPcdLoginDomain">
																<form:option value="N" label="@netvigator.com"/>
																<form:option value="S" label="@hkstar.com"/>
															</form:select>
														</td>
														<td width="55%">
															<div class="func_button">
																<a id="searchOtherFsaBtn" href="#"><spring:message code="lts.modemArrangememt.search" text="Search"/></a>
															</div>
															&nbsp;&nbsp;
															<div class="func_button">
																<a id="clearOtherFsaBtn" href="#"><spring:message code="lts.modemArrangememt.clear" text="Clear"/></a>
															</div>
														</td>
													</tr>
													
												</table>
											</td>
										</tr>
										<tr>
											<td colspan="4">&nbsp;</td>
										</tr>							
										<tr>											
											<td colspan="4">
												<c:if test="${ not empty requestScope.retrieveOtherFsaError }">
													<div id="otherFsaNotFoundDiv">
														<table width="100%" border="0" cellspacing="0" class="contenttext">
															<tr>
																<td width="40%">
																	<div id="warning" class="ui-widget" style="visibility: visible;">
																		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
																			<p>
																				<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																			</p>
																			<div id="warning_msg" class="contenttext">
																				${requestScope.retrieveOtherFsaError}
																			</div>
																			<p></p>
																		</div>
																	</div>		
																</td>
																<td width="60%">
																	&nbsp;
																</td>
															</tr>
														</table>
													</div>
												</c:if>
												<c:if test="${not empty ltsModemArrangementCmd.otherFsaList}">																
													<div id="otherFsaTableDiv" >
														<table class="table_style" width="100%" border="1" cellspacing="0">
															<tr height="30">
																<th width="1%" align="center">&nbsp;</th>
																<th width="5%" align="center"><spring:message code="lts.modemArrangememt.fsa" text="FSA  "/></th>
																<th width="10%" align="center"><spring:message code="lts.modemArrangememt.existSrv" text="Existing Service"/></th>
																<th width="10%" align="center"><spring:message code="lts.modemArrangememt.loginID" text="Login ID"/></th>
																<th width="10%" align="center"><spring:message code="lts.modemArrangememt.effDate" text="Effective Date"/></th>
																<th width="5%" align="center"><spring:message code="lts.modemArrangememt.nowTVUpg" text="nowTV Upgrade"/></th>
																<th width="5%" align="center"><spring:message code="lts.modemArrangememt.pcdUpg" text="PCD Upgrade"/></th>
																<th width="5%" align="center"><spring:message code="lts.modemArrangememt.nowTVChannel" text="nowTV Channel"/></th>
																<th width="5%" align="center"><spring:message code="lts.modemArrangememt.newModArrg" text="New Modem Arrange"/></th>
																<th width="40%" align="center"><spring:message code="lts.modemArrangememt.difIA" text="Different I/A"/></th>
																<th width="5%" align="center"><spring:message code="lts.modemArrangememt.pendingOrd" text="Pending Order"/></th>
															</tr>
															<c:forEach items="${ltsModemArrangementCmd.otherFsaList}" var="otherFsa" varStatus="otherFsaStatus">
																<tr bgcolor="#FFFFFF" title="${otherFsa.notAllowToShare ? otherFsa.notAllowToShareReason : ''}" height="50" style="<c:out value="${otherFsa.notAllowToShare ? 'color: graytext;' : '' }"/>"  >
																	<td align="center">
																		<c:choose>
																		<c:when test="${otherFsa.notAllowToShare}">
																			<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																		</c:when>
																		<c:otherwise>
																			<form:checkbox id="${otherFsa.fsa}" path="otherFsaList[${otherFsaStatus.index}].selected" disabled="${otherFsa.notAllowToShare}" cssClass="otherFsaCheckbox ${otherFsa.differentIa ? 'isDifferentIa' : ''} ${otherFsa.allowConfirmSameIa ? 'allowConfirmSameIa' : ''}"/>
																		</c:otherwise>																	
																		</c:choose>
																		
																	</td>
																	<td align="center">
																		<c:out default="--" value="${otherFsa.fsa}"/>
																	</td>
																	<td align="center">
																		<form:hidden id="otherFsa.existingService${otherFsa.fsa}" path="otherFsaList[${otherFsaStatus.index}].existingService.name" />
																		<c:out default="--" value="${otherFsa.existingService.desc }"/>
																	</td>
																	<td align="center">
																		<c:out default="--" value="${otherFsa.loginId}"/>
																	</td>
																	<td align="center">
																		<c:out default="--" value="${otherFsa.effectiveDate}"/>
																	</td>
																	<td align="center">
																		<form:select id="otherFsa.newService${otherFsa.fsa}" cssClass="otherFsaNewService otherFsa${otherFsa.fsa}" path="otherFsaList[${otherFsaStatus.index}].newService" disabled="true" >
																			<form:option value="" label="" />
																			<form:options items="${otherFsa.futureFsaServiceList}" itemValue="name" itemLabel="desc" />
																		</form:select>
																	</td>
																	<td align="center">
																		<form:select id="otherFsa.upgradeBandwidth${otherFsa.fsa}"  cssClass="otherFsaUpgradeBandwidth otherFsa${otherFsa.fsa}" path="otherFsaList[${otherFsaStatus.index}].upgradeBandwidth" disabled="true">
																			<form:option value="" label="" />
																			<form:options items="${otherFsa.upgradeBandwidthMap}" />
																		</form:select>
																	</td>						
																	<td align="center">
																		<c:choose>
																			<c:when test="${otherFsa.nowTvServiceDetail != null && !otherFsa.notAllowToShare}">
																				<a href="ltsnowtvchannel.html?sbuid=${sessionScope.sbuid}&fsa=${otherFsa.fsa}" onclick="window.open(this.href, 'nowTV Channel List', 'width=600, height=400, scrollbars=yes'); return false;">Details</a>
																			</c:when>
																			<c:otherwise>
																				--
																			</c:otherwise>
																		</c:choose>
																	</td>
																	<td align="center">
																		<span id="display.otherFsa.modemArrange${otherFsa.fsa}" class="otherFsa.modemArrange">
																			<c:if test="${!otherFsa.notAllowToShare}">
																				<c:choose>
																					<c:when test="${ otherFsa.newModemArrange != null && otherFsa.newModemArrange != '' }">
																						<b><c:out value="${otherFsa.newModemArrange}"/></b>
																					</c:when>
																					<c:otherwise>
																						<c:out default="--" value="${otherFsa.modemArrange}"/>
																					</c:otherwise>
																				</c:choose>
																			</c:if>
																		</span>
																		
																		<form:hidden id="otherFsa.modemArrange${otherFsa.fsa}" path="otherFsaList[${otherFsaStatus.index}].modemArrange" />
																		<form:hidden id="otherFsa.newModemArrange${otherFsa.fsa}" path="otherFsaList[${otherFsaStatus.index}].newModemArrange" />
																	</td>
																	<td align="center">
																		<c:choose>
																			<c:when test="${otherFsa.differentIa }">
																				<span style="color: red;">${otherFsa.addressDetail.fullAddress}</span>
																			</c:when>
																			<c:otherwise>
																				--
																			</c:otherwise>
																		</c:choose>
																	</td>
																	<td align="center">
																		<c:choose>
																			<c:when test="${otherFsa.pendingOrderExist }">
																				<span style="color: red;">Y</span>
																			</c:when>
																			<c:otherwise>
																				--
																			</c:otherwise>
																		</c:choose>
																	</td>
																</tr>
															</c:forEach>
														</table>
													</div>
													<div id="otherFsaDiffIaDiv" style="display: none;">
														<table width="100%" border="0" cellspacing="0" class="contenttext">	
															<tr>
																<td width="70%" align="left">
																	<div id="warning" class="ui-widget" style="visibility: visible;">
																		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
																			<p>
																				<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																			</p>
																			<div id="warning_msg" class="contenttext">
																				<spring:message code="lts.modemArrangememt.notSameIA" text="Selected FSA is not under same installation address!"/>
																			</div>
																			<p></p>
																		</div>
																	</div>		
																</td>
																<td>&nbsp;</td>
															</tr>
														</table>
														<br>
														<table width="100%" border="0" cellspacing="4" class="contenttext">															
															<tr>
																<td width="35%">
																	<spring:message code="lts.modemArrangememt.externalRelocationIMStoEyeIA" text="External relocation of selected IMS service to Target eye IA"/><span class="contenttext_red">*</span> :   
																</td>
																<td>
																	<form:radiobutton path="otherFsaER" value="true"/> <b>Yes</b>
																	&nbsp;
																	<form:radiobutton path="otherFsaER" value="false"/> <b>No</b>
																</td>
															</tr>
														</table>
														<div id="otherFsaConfirmSameIaDiv">
															<table width="100%" border="0" cellspacing="4" class="contenttext">															
																<tr>
																	<td width="100%">
																		<spring:message code="lts.modemArrangememt.confirmOrderUnderSameAddress" text="Confirm selected FSA under same installation address"/><span class="contenttext_red">*</span> :
																		<form:checkbox path="otherFsaConfirmSameIa"/>   
																	</td>
																</tr>
															</table>	
														</div>
													</div>
													<br>
													<table width="100%" border="0" cellspacing="4" class="contenttext">															
														<tr>
														<td width="100%">
																<spring:message code="lts.modemArrangement.confirmOrderUnderSameCustomer" text="Confirm selected FSA under same customer"/><span class="contenttext_red">*</span> :
																<form:checkbox path="otherFsaConfirmSameCust" />   
															</td>
														</tr>
													</table>
												</c:if>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td width="100%" colspan="4">&nbsp;</td>
							</tr>
						</table>
					</div>
					
					<div id="nowTvOrderDiv" style="display: none;" class="paper_w2 round_10">	
						<br/>
						<table width="98%" border="0" cellspacing="0" class="contenttext" align="center">	
							<tr>
								<td colspan="4">
									<div id="s_line_text"><spring:message code="lts.modemArrangememt.nowTvOrdInfo" text="nowTV Order Information"/></div>							
									<table width="100%" border="0" cellspacing="1" class="contenttext">
										<tr>
											<td colspan="4">&nbsp;</td>
										</tr>									
										<tr>
											<td align="right" width="25%"><spring:message code="lts.modemArrangememt.nowTvSrvType" text="nowTV Service Type"/><span class="contenttext_red">*</span> : </td>
											<td width="20%"> 
												<form:select path="nowTvServiceType" >
													<form:option value="" label="-- SELECT --"/>
													<form:options items="${ltsModemArrangementCmd.tvTypeList}" />
												</form:select>
											</td>
											<td colspan="2">&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<div id="writtenFormRemindDiv">
							<table width="100%" cellpadding="3" border="0">
								<tr>
									<td width="5%">&nbsp;</td>
									<td width="45%">
										<div id="warning" class="ui-widget" style="visibility: visible;">
											<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
												<p>
													<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
												</p>
												<div id="warning_msg" class="contenttext">
													<spring:message code="lts.modemArrangememt.nowTvSub" text="Please use written contract for nowTV subscrption."/>
												</div>
												<p></p>
											</div>
										</div>
									</td>
									<td width="50%">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="3">&nbsp;</td>
								</tr>
							</table>
						</div>
					</div>
					
					<div id="pcdSbOrderDiv" style="display: none;" class="paper_w2 round_10">
						<br/>
						<table width="98%" border="0" cellspacing="0" class="contenttext" align="center">																							
							<tr>
								<td colspan="4">
									<div id="s_line_text"><spring:message code="lts.modemArrangememt.pcdOrdInfo" text="PCD Order Information"/></div>
									<table width="100%" border="0" cellspacing="1" class="contenttext">
										<tr>
											<td colspan="4">&nbsp;</td>
										</tr>
										<tr>
											<td width="25%" align="right">
												<spring:message code="lts.modemArrangememt.pcdSbOrd" text="With PCD Springboard Order"/><span class="contenttext_red">*</span> :  
											</td>
											<td>
												<form:radiobutton path="pcdSbOrderExist" value="true"/> <b>Yes</b>
												<form:radiobutton path="pcdSbOrderExist" value="false"/> <b>No</b>
											</td>
										</tr>
										<tr>
											<td colspan="4">
												<div id="withPcdOrderDiv">
													<table width="100%" border="0" cellspacing="1" class="contenttext">
														<tr>
															<td colspan="4" width="100%">&nbsp;</td>	
														</tr>									
														<tr>
															<td width="25%" align="right"><spring:message code="lts.modemArrangememt.sbOrderId" text="Springboard Order ID"/><span class="contenttext_red">*</span> : </td>
															<td width="20%"> 
																<form:input path="inputPcdSbOrderId" />
															</td>
															<td colspan="2" align="left">
																<div class="func_button">
																	<a id="searchPcdOrderBtn" href="#"><spring:message code="lts.modemArrangememt.search" text="Search"/></a>
																</div>
																&nbsp;&nbsp;
																<div class="func_button">
																	<a id="clearPcdOrderBtn" href="#"><spring:message code="lts.modemArrangememt.clear" text="Clear"/></a>
																</div>
																
															</td>
														</tr>
														<tr>
															<td colspan="4">
																<c:if test="${not empty requestScope.retrievePcdSbOrderError}">
																	<div id="newPcdOrderNotFoundDiv" style="display: none;">
																		<table width="100%" border="0" cellpadding="3">
																			<tr>
																				<td width="70%" align="left">
																					<div class="ui-widget" style="visibility: visible;">
																						<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
																							<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
																							<div class="contenttext">
																								${requestScope.retrievePcdSbOrderError}
																							</div>
																							<p></p>
																						</div>
																					</div>		
																				</td>
																				<td>&nbsp;</td>
																			</tr>
																		</table>	
																	</div>																	
																</c:if>
																<c:if test="${not empty sessionScope.sessionLtsOrderCapture.relatedPcdOrder}">
																	<c:set var="pcdSbOrder" value="${sessionScope.sessionLtsOrderCapture.relatedPcdOrder}" />
																	<input type="hidden" id="pcdOrderRentalRouter" value="${pcdSbOrder.hasRentalRouter}"/>
																	<input type="hidden" id="pcdOrderMeshRouter" value="${pcdSbOrder.hasMeshRouter}"/>
																	<c:choose>
																		<c:when test="${!pcdSbOrder.custNotMatch && !pcdSbOrder.iaNotMatch}">
																			<div id="newPcdOrderRetrievedDiv" style="display: none;">
																				<table width="100%" border="0" cellpadding="3">
																					<tr>
																						<td width="70%" align="left">
																							<div id="warning" class="ui-widget" style="visibility: visible;">
																								<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
																									<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
																									<div id="warning_msg" class="contenttext">
																										<b>Springboard order (${pcdSbOrder.orderId}) retrieved.</b>
																										<br>
																										<br>
																										<b>Installation Address : ${pcdSbOrder.fullAddress} </b>
																										<br>
																										<br>
																										<b>Subscribed Bandwidth : ${pcdSbOrder.bandwidth != null ? pcdSbOrder.bandwidth : (pcdSbOrder.technology == "P" ? "PON" : "")}${pcdSbOrder.bandwidth != null ? "M" : ""}</b>
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
																							<div id="warning" class="ui-widget" style="visibility: visible;">
																								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
																									<c:if test="${pcdSbOrder.custNotMatch}">
																										<p>
																											<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																										</p>
																										<div id="warning_msg" class="contenttext">
																											<spring:message code="lts.modemArrangement.orderNotUnderSameCustomer" arguments="${pcdSbOrder.orderId}" htmlEscape="false"/>
																										</div>
																										<p></p>
																									</c:if>
																									<c:if test="${pcdSbOrder.iaNotMatch}">
																										<p>
																											<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																										</p>
																										<div id="warning_msg" class="contenttext">																				
																											<spring:message code="lts.modemArrangement.orderNotUnderSameAddress" arguments="${pcdSbOrder.orderId}" htmlEscape="false"/></div>
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
																						<td width="100%" align="left">
																							<b><spring:message code="lts.modemArrangement.subscribedBandwidth" htmlEscape="false"/> : ${pcdSbOrder.bandwidth != null ? pcdSbOrder.bandwidth : (pcdSbOrder.technology == "P" ? "PON" : "")}${pcdSbOrder.bandwidth != null ? "M" : ""}</b>
																						</td>
																					</tr>
																					<c:if test="${pcdSbOrder.custNotMatch && pcdSbOrder.allowConfirmSameCust}">
																						<tr>
																							<td width="100%" align="left">
																								<spring:message code="lts.modemArrangement.confirmOrderUnderSameCustomer" htmlEscape="false"/><span class="contenttext_red">*</span> :
																								&nbsp; 
																								<form:checkbox path="confirmSameCustWithPcdOrder"/>
																							</td>
																						</tr>
																					
																					</c:if>																								
																					<c:if test="${pcdSbOrder.iaNotMatch && pcdSbOrder.allowConfirmSameIa}">
																						<tr>
																							<td width="100%" align="left">
																								<spring:message code="lts.modemArrangememt.targetIA" htmlEscape="false"/> : <b>${pcdSbOrder.fullAddress}</b> 
																							</td>
																						</tr>																					
																						<tr>
																							<td width="100%" align="left">
																								<spring:message code="lts.modemArrangement.confirmOrderUnderSameAddress" htmlEscape="false"/><span class="contenttext_red">*</span> : 
																								&nbsp; 
																								<form:checkbox path="confirmSameIaWithPcdOrder"/>
																							</td>
																						</tr>
																					</c:if>
																				</table>
																			</div>	
																			</c:otherwise>
																	</c:choose>
																</c:if>
															</td>
														</tr>			
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="4">&nbsp;</td>
										</tr>										
									</table>
								</td>
							</tr>
						</table>
					</div>
					
					<c:if test="${sessionScope.bomsalesuser.channelId == 2 || sessionScope.bomsalesuser.channelId == 3 || sessionScope.bomsalesuser.channelId == 10 || sessionScope.bomsalesuser.channelId == 11}">
						<div id="edfRefDiv" class="paper_w2 round_10">
							<br/>
							<table width="98%" border="0" cellspacing="0" class="contenttext" align="center">																							
								<tr>
									<td colspan="4">
										<div id="s_line_text"><spring:message code="lts.modemArrangement.EDFReference" htmlEscape="false"/></div>
										<table width="100%" border="0" cellspacing="1" class="contenttext">
											<tr>
												<td colspan="4">&nbsp;</td>
											</tr>
											<tr>
												<td width="25%" align="right">
													<spring:message code="lts.modemArrangement.withEDFReference" htmlEscape="false"/><span class="contenttext_red">*</span> :  
												</td>
												<td class="bold">
													<form:radiobutton path="edfRefExist" value="true"/> Yes
													<form:radiobutton path="edfRefExist" value="false"/> No
												</td>
											</tr>
											<tr><td colspan="4">
											<div id="withEdfRefDiv">
												<table width="100%" border="0" cellspacing="1" class="contenttext">
													<tr>
														<td colspan="4">&nbsp;</td>
													</tr>
													<tr>
														<td width="25%" align="right">
															 <spring:message code="lts.modemArrangement.EDFReference" htmlEscape="false"/> :    
														</td>
														<td>
															<form:input path="edfRefNum" />
														</td>
													</tr>
												</table>
											</div>
											</td></tr>
											<tr>
												<td colspan="4">&nbsp;</td>
											</tr>										
										</table>
									</td>
								</tr>
							</table>
						</div>
					</c:if>
					
					<spring:hasBindErrors name="ltsModemArrangementCmd">
						<table width="100%">
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="70%">
									<div id="error_div" class="ui-widget" style="visibility: visible;">
										<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
											<p>
												<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
											</p>
											<div id="error_msg" class="contenttext">
												<form:errors path="*"  />				
											</div>
											<p></p>
										</div>
									</div>
								</td>
								<td>&nbsp;</td>
							</tr>
						</table>
						<br>
					</spring:hasBindErrors>	
				</td>
			</tr>
			
			<tr>
				<td>
					<div id="rentalRouterDiv">
					<table width="98%" cellspacing="0" border="0" align="center" class="contenttext">
					    <tbody><tr><td><div id="s_line_text"><spring:message code="lts.modemArrangememt.modemSelection" text="Modem Selection"/></div></td></tr>
						<tr>
						<td>
							<table width="100%" border="0" style="margin: 1em 0" class="contenttext">
								<tbody>
									<tr>
										<td width="25%" align="right"><b><spring:message code="lts.modemArrangement.HnRentalRouterSharing" text="HN Rental Router Sharing"/></b><span class="contenttext_red">*</span></td>
										<td style="line-height: 2em;">
											<form:radiobutton path="rentalRouterInd" id="rentalRouter" value="SHARE_RENTAL_ROUTER"/><spring:message code="lts.modemArrangement.yes"/>
											<form:radiobutton path="rentalRouterInd" id="BRM" value="BRM"/><spring:message code="lts.modemArrangement.no"/>
										</td>
									</tr>
								</tbody>
							</table>
							<div style="visibility: visible; margin: 0px 10%; display: none;" class="ui-widget" id="rentalRouterAlertMsgDiv">
								<div style="padding: 0px 1em; margin: 1em;" class="ui-state-error ui-corner-all">
									<p>
										<span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-alert"></span>
									</p>
									<div id="warning_msg" class="contenttext">
										<u><spring:message code="lts.modemArrangement.rentalRouter.alert.title"/></u>
										<br><br>
										<b><spring:message code="lts.modemArrangement.rentalRouter.alert.content1"/></b>
										<spring:message code="lts.modemArrangement.rentalRouter.alert.content2"/>
									</div>
									<br>
								</div>
							</div>
						</td>
						</tr>
						</tbody>
					</table>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<div id="modemDiv">
					<table width="98%" cellspacing="0" border="0" align="center" class="contenttext">
					    <tbody><tr><td><div id="s_line_text"><spring:message code="lts.modemArrangememt.modem" text="Modem"/></div></td></tr>
						<tr>
						<td>
							<table width="100%" border="0" class="contenttext" style="margin: 1em 0 ">
								<tbody>
									<tr>
										<td width="25%" align="right"><b><spring:message code="lts.modemArrangememt.lostModem" text="Lost Modem"/></b></td>
										<td>
											<form:checkbox id="lostModemCheckbox" path="lostModem" />
										</td>
									</tr>
								</tbody>
							</table>
						</td>
						</tr>
						</tbody>
					</table>
					</div>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
</form:form>

<c:if test="${not empty errorMsgList && errorMsgList != null}">
<div id="customErrorDiv">
	<table width="100%">
		<tr>
			<td width="5%">&nbsp;</td>
			<td width="70%">
				<div class="ui-widget" style="visibility: visible;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<c:forEach items="${errorMsgList}" var="errorMsg">
						<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						</p>
						<div id="" class="contenttext">
							${errorMsg}				
						</div>
						<p></p>
						</c:forEach>
					</div>
				</div>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>
</c:if>


<c:choose>
<c:when test="${(not empty sessionScope.sessionLtsTeamFunction && sessionScope.sessionLtsTeamFunction.enquiryOnly == 'Y')
					|| sessionScope.sessionLtsOrderCapture.ltsCustomerInquiryForm.upgradeToStaffPlan
					|| sessionScope.sessionLtsOrderCapture.ltsCustomerInquiryForm.bocEmpty}">
	<div id="continueDiv">
		<table width="100%" border="0" cellspacing="0">
		<tr>
			<td align="right"> 
			<br>
			<div class="button">
				<a href="#" id="homeBtn">Home</a>
			</div> 
			</td>
		</tr>
		</table>
	</div>
</c:when>
<c:otherwise>
	<div id="continueDiv">
		<table width="100%" border="0" cellspacing="0">
		<tr>
			<td align="right"> 
			<br>
			<div class="button">
				<a href="#" id="continueBtn"><spring:message code="lts.modemArrangememt.next" text="Next"/></a>
			</div> 
			</td>
		</tr>
		</table>
	</div>
</c:otherwise>
</c:choose>

<script type="text/javascript">
	var ponShortageMsg = '<spring:message code="lts.modemArrangement.abnormalAddr.PONShortageMsg"/>';
	var ponBlockageMsg = '<spring:message code="lts.modemArrangement.abnormalAddr.PONBlockageMsg"/>';

	$(ltsmodemarrangement.actionPerform);
	$(document).ready(function() {
		$(ltsmodemarrangement.showPanel);
	});
	
	
</script>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>