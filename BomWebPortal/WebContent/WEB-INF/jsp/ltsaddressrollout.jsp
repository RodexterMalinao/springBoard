<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/web/lts/ltsaddressrollout.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="3" />
</jsp:include>
<script src="js/jquery.lts.autocomplete.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<form:form method="POST" id="ltsAddressRolloutForm" name="ltsAddressRolloutForm" commandName="ltsAddressRolloutCmd" autocomplete="off" onsubmit = "erCheck()">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="serviceBoundary" />
<form:hidden path="streetCatgCode" />
<form:hidden path="lotHouseInd" />
<form:hidden path="ponSbRequired"/>
<form:hidden path="dnchange"/>

<table width="100%">
	<tr>
		<td>
			<div id="s_line_text"><spring:message code="lts.address.address" text="Address"/></div>
			<table width="100%" border="0" cellspacing="1" cellpadding="6" >
				<tr>
					<td width="50%">
						<form:radiobutton path="externalRelocate" value="false" />
						<b><spring:message code="lts.address.existInstallAddress" text="Existing Installation Address"/></b><span class="contenttext_red">*</span>

					</td>
					<td width="50%">
						<form:radiobutton path="externalRelocate" value="true" />
						<b><spring:message code="lts.address.exRelocation" text="External Relocation"/></b><span class="contenttext_red">*</span>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="6" >
				<tr>
					<td colspan="2" width="100%" align="center">
						<br>
						<div id="existIaDiv"  class="paper_w2 round_10" style="width: 95%">
							<br>
							<table width="98%" border="0" align="center">
								<tr>
									<td colspan="2">
										<div id="s_line_text"><spring:message code="lts.address.addressRollout" text="Address Rollout"/></div>												
									</td>
								</tr>
							</table>
							<c:if test="${not empty sessionLtsOrderCapture.ltsServiceProfile.address}">
								<br>
								<table width="98%" align="center">
									<tr>
										<td width="10%">&nbsp;</td>
										<td width="60%"><b><c:out value="${sessionLtsOrderCapture.ltsServiceProfile.address.fullAddress}" /></b></td>
										<td width="10%">&nbsp;</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${not empty sessionLtsOrderCapture.ltsServiceProfile.address.addressRollout}">
								<c:set var="profileAddressRollout" value="${sessionLtsOrderCapture.ltsServiceProfile.address.addressRollout}" />
								<div id="existRolloutDiv">
									<br/>
									<table width="98%" border="0" align="center">
										<tr>
											<td width="80%">
												<table width="100%" cellspacing="5" cellpadding="5" border="0"><tr>
													<td width="20%" align="right"><spring:message code="lts.address.ltsHseType" text="LTS Estate Type"/> : </td>
														<td width="30%">
															<span style="font-weight: bold;">
																<c:out value="${profileAddressRollout.ltsHousingCatDesc}" />
															</span>
														</td>
													</tr>
													<tr>
														<td width="20%" align="right"><spring:message code="lts.address.ptMarker" text="Premier"/> : </td>
														<td width="30%">
															<span style="font-weight: bold;">
																<c:if test="${not empty profileAddressRollout.hktPremier}">
																	<c:out value="${profileAddressRollout.hktPremier}" />
																</c:if>
																<c:if test="${empty profileAddressRollout.hktPremier}">
																	-
																</c:if>
															</span>
														</td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.address.eyeCoverage" text="EYE Coverage"/> : </td>
														<td>
															<c:choose>
																<c:when test="${profileAddressRollout.eyeCoverage}" >
																	<span style="font-weight: bold;">Y</span>
																</c:when>
																<c:otherwise>
																	<span style="color: red; font-weight: bold;">N</span>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.address.peCoverage" text="PE Coverage"/> : </td>
														<td>
															<c:choose>
																<c:when test="${profileAddressRollout.peCoverage}" >
																	<span style="font-weight: bold;">Y</span>
																</c:when>
																<c:otherwise>
																	<span style="font-weight: bold;">N</span>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>																
													<tr>
														<td align="right"><spring:message code="lts.address.2nBuild" text="2N Building"/> : </td>
														<td>
															<span style="font-weight: bold;"><c:out value="${profileAddressRollout.is2nBuilding ? 'Y' : 'N'}" /></span>
														</td>
													</tr>
													<tr>
														<td align="right" valign="top"><spring:message code="lts.address.bwCoverage" text="Bandwidth Coverage"/> : </td>
														<td>
															<span style="font-weight: bold;">
																${profileAddressRollout.availableBandwidth == "" ? "N/A" : profileAddressRollout.availableBandwidth}
															</span>
														</td>
													</tr>																
													<tr>
														<td align="right"><spring:message code="lts.address.rsCoverage" text="Resource Shortage"/> : </td>
														<td>
														<c:choose>
														<c:when test="${profileAddressRollout.pcdResourceShortage != null}">
															<span style="font-weight: bold; color: red;">
																<c:out value="${profileAddressRollout.pcdResourceShortage}"/>
															</span>
														</c:when>
														<c:otherwise>
															<span style="font-weight: bold;">N</span>
														</c:otherwise>		
														</c:choose>
														
														</td>
																
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.address.ltsProof" text="LTS Address Proof Required"/> : </td>
														<td>
															<span style="font-weight: bold; ${profileAddressRollout.ltsAddressBlacklist ? 'color: red;' : ''}">
																<c:out value="${profileAddressRollout.ltsAddressBlacklist ? 'Y' : 'N'}" />
															</span>
														</td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.address.imsProof" text="IMS Address Proof Required"/> : </td>
														<td>
															<span style="font-weight: bold; ${profileAddressRollout.imsAddressBlacklist ? 'color: red;' : ''}">
															<c:out value="${profileAddressRollout.imsAddressBlacklist ? 'Y' : 'N'}" />
															</span>
														</td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.address.fieldWorkPermit" text="Field Work Permit"/> : </td>
														<td>
															<span style="font-weight: bold;">
															<c:out value="${profileAddressRollout.fieldWorkPermit}" />
															</span>
														</td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.address.FTTC" text="FTTC"/> : </td>
														<td>
															<span style="font-weight: bold;">
															<c:out value="${profileAddressRollout.fttcInd ? 'Y' : 'N'}" />
															</span>
														</td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.address.fiberBlockage" text="Fiber Blockage"/> : </td>
														<td>
															<span style="font-weight: bold;">
															<c:out value="${profileAddressRollout.fiberBlockageInd ? 'Y' : 'N'}" />
															</span>
														</td>
													</tr>
													<c:if test="${not empty earliestSRD}">
														<tr>
															<td align="right" valign="top"><spring:message code="lts.address.sugEarliestSRD" text="Suggested Earliest SRD"/> : </td>
															<td align="left"><span id="rolloutSuggestSrd" style="font-weight: bold;">${earliestSRDReason}</span></td>
														</tr>
													</c:if>
												</table>
												<br/>
											</td>
										</tr>
									</table>
									
									<c:if test="${not empty profileAddressRollout.uimBlockage}">
										<div id="existBlockageDiv" class="ui-widget" style=" visibility: visible;">
											<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
												<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
												<div "class="contenttext" style="font-weight:bold; margin-left: 1.5em; text-align: left;">
													<c:forEach items="${profileAddressRollout.uimBlockage}" var="existBlockage" varStatus="existBlockageCount">
														Blockage Code ${existBlockage.blockageCd}, ${existBlockage.blockageDesc}, Blockage Date: ${existBlockage.blockageDate}
													</c:forEach>
												</div>
												<p></p>
											</div>
										</div>
									</c:if>
								</div>
 							</c:if>
						</div>
						
						<c:if test="${ltsAddressRolloutCmd.ponSbRequired && requestScope.ponSbList}">
							<div id="ponSbDiv" style="display: none; width: 100%;">
								<table width="100%" border="0" cellpadding="1" cellspacing="1" class="contenttext">
									<tr>
										<td width="10%">&nbsp;</td>
										<td width="80%">
											<table width="100%" border="1" cellpadding="0" cellspacing="0">
												<tr>
													<td width="2%">&nbsp;</td>
													<td width="98%" align="center"><b>Address Information</b></td>
												</tr>
												<c:forEach items="${requestScope.ponSbList}" var="ponSb">
													<tr>
														<td width="2%"><form:radiobutton path="ponSbNum" value="${ponSb.ponSbNum}" /></td>
														<td width="98%">${ponSb.desc}</td>
													</tr>
												</c:forEach>
											</table>
										</td>
										<td width="10%">&nbsp;</td>
									</tr>
								</table>
							</div>
						</c:if>

						<div id="erIaDiv" class="paper_w2 round_10" style="display: none; width: 95%;">
							<br/>
							<table width="98%" align="center">
								<tr>
									<td><div id="s_line_text">New Installation Address</div></td>
								</tr>
							</table>
							<table width="100%" border="0" cellpadding="1" cellspacing="1" class="contenttext">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>							
								<tr>
									<td align="right" width="15%">
										<span class="contenttext_red">*</span>Quick Search :
									</td>
									<td align="left">
										<form:input path="quickSearch" size="80" />
										&nbsp;
										<div class="func_button">
											<a href="#" id="clearInputAddress">Clear</a>
										</div>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>
										Simply input the building name of your installation address, separate with a comma (,)
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>
										<form:errors path="quickSearch" cssClass="error" />
									</td>
								</tr>	
														
							</table>
							<table width="100%" border="0" cellpadding="1" cellspacing="1" class="contenttext">								
								<tr>
									<td width="14%">&nbsp;</td>
									<td>&nbsp;</td>
									<td width="15%">&nbsp;</td>
									<td>&nbsp;</td>
									<td width="19%">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td align="right">Flat / Room : </td>
									<td>
										<form:input maxlength="5" path="flat" cssClass="addressInput"/>
									</td>
									<td align="right">Floor : </td>
									<td colspan="3">
										<form:input maxlength="3" path="floor" cssClass="addressInput"/>
										<form:errors path="floor" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td align="right">Lot No. : </td>
									<td>
										<form:input maxlength="8" path="lotNum" readonly="true"/>
									</td>
									<td align="right">Building : </td>
									<td colspan="3">
										<form:input maxlength="50" size="50" path="buildingName" readonly="true"/>
									</td>
								</tr>
								<tr>
									<td align="right">Street No : </td>
									<td>
										<form:input maxlength="11" path="streetNum" readonly="true"/>
									</td>
									<td align="right">Street Name : </td>
									<td>
										<form:input maxlength="23" path="streetName" readonly="true"/>
									</td>
									<td align="right">Street Category : </td>
									<td>
										<form:input path="streetCatgDesc" readonly="true"/>
									</td>
								</tr>
								<tr>
									<td align="right">Section : </td>
									<td>
										<form:hidden path="sectionCode" />
										<form:input path="sectionDesc" readonly="true" />
									</td>
									<td align="right">District : </td>
									<td>
										<form:hidden path="districtCode" />
										<form:input path="districtDesc" readonly="true" />
									</td>
									<td align="right">Area : </td>
									<td>
										<form:hidden path="areaCode" />
										<form:input path="areaDesc" readonly="true" />
									</td>
								</tr>
								<tr>
									<td colspan="6"><br/></td>
								</tr>
								<tr>
									<td align="right">
										BA & CA<span class="contenttext_red">*</span> :
									</td>
									<td colspan="5" class="bold">
										<form:radiobutton path="baCaAction" value="SAME_AS_NEW_IA"/> Same as new I/A
										&nbsp;
										<form:radiobutton path="baCaAction" value="REMAIN_UNCHANGE"/> Remain unchange
										&nbsp;
										<form:radiobutton path="baCaAction" value="DIFFERENT_FROM_NEW_OLD_IA"/> Different from new/old I/A
										<br/>
										<form:errors path="baCaAction" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td align="right">
									</td>
									<td colspan="5" class="bold">
										<form:checkbox id="instantUpdateBa" path="instantUpdateBa"/> Instant Update
									</td>
								</tr>
								<tr>
									<td colspan="6">&nbsp;</td>
								</tr>
							</table>
							<br/>
							<div id="erBaDiv" style="display: none; width: 98%;">
								<table width="100%" border="0" cellpadding="1" cellspacing="1" class="contenttext">
									<tr>
										<td><div id="s_line_text">New Billing Address</div></td>
									</tr>
								</table>
								<table width="100%" border="0" cellpadding="1" cellspacing="1" class="contenttext">
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>							
									<tr>
										<td align="right" width="15%">
											<span class="contenttext_red">*</span>Quick Search :
										</td>
										<td align="left">
											<form:input path="baQuickSearch" size="80" />
											&nbsp;
											<div class="func_button">
												<a href="#" id="clearBaInputAddress">Clear</a>
											</div>
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>
											Simply input the building name of your billing address, separate with a comma (,)
										</td>
									</tr>
									<tr>
										<td colspan="2"><form:errors path="baQuickSearch" cssClass="error" /></td>
									</tr>
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td align="right" valign="top" width="15%">
											<span class="contenttext_red">*</span>Billing Address :&nbsp;
										</td>
										<td>
											<div style="float:left">
												<form:textarea id="billingAddressTextArea" path="billingAddress" rows="6" cols="40" cssStyle="resize: none;"/>
											</div>
											<div style="width: 30%; float: left; padding-left: 10px;">
												<form:errors path="billingAddress" cssClass="error" />
											</div>
										</td>
									</tr>						
								</table>
							</div>
							<br/>
							<div id="rolloutErrorDiv" style="display: none;">
								<br>
								<table width="90%" border="0">
									<tr>
										<td width="10%">&nbsp;</td>											
										<td width="80%" align="left">
											<div id="rollout_Error_Msg_div" class="ui-widget" style="visibility: visible;">
												<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
													<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
													<div  class="contenttext">
														<span id="rolloutErrorMsg"></span>
													</div>
													<p></p>
												</div>
											</div>													
										</td>
										<td width="10%">&nbsp;</td>
									</tr>
								</table>
							</div>													
							<div id="rolloutDiv" style="display: none;">
								<br>
								<table width="98%" border="0" align="center">
									<tr>
										<td>
											<div id="s_line_text"><spring:message code="lts.address.addressRollout" text="Address Rollout"/></div>
										</td>
									</tr>
									<tr>
										<td width="90%" align="center">
											<table width="100%" cellspacing="3" cellpadding="3" border="0">
												<tr>
													<td width="40%" align="right"><spring:message code="lts.address.ltsHseType" text="LTS Estate Type"/> : </td>
													<td width="60%" align="left"><span id="rolloutLtsHousingCat" style="font-weight: bold;"></span></td>
												</tr>
												<tr id="rolloutPhRow">
													<td width="40%" align="right"><spring:message code="lts.address.ptMarker" text="Premier"/> : </td>
													<td width="60%" align="left"><span id="rolloutPh" style="font-weight: bold;"></span></td>
												</tr>
												<tr>
													<td align="right"><spring:message code="lts.address.eyeCoverage" text="EYE Coverage"/> : </td>
													<td align="left"><span id="rolloutEyeCoverage" style="font-weight: bold;"></span></td>
												</tr>
												<tr>
													<td align="right"><spring:message code="lts.address.peCoverage" text="PE Coverage"/> : </td>
													<td align="left"><span id="rolloutPeCoverage" style="font-weight: bold;"></span></td>
												</tr>																														
												<tr>
													<td align="right"><spring:message code="lts.address.2nBuild" text="2N Building"/> : </td>
													<td align="left"><span id="rollout2nBuilding" style="font-weight: bold;"></span></td>
												</tr>												
												<tr>
													<td align="right" valign="top"><spring:message code="lts.address.bwCoverage" text="Bandwidth Coverage"/> : </td>
													<td align="left"><span id="rolloutBandwidthCoverage" style="font-weight: bold;"></span></td>
												</tr>
												<tr>
													<td align="right"><spring:message code="lts.address.rsCoverage" text="Resource Shortage"/> : </td>
													<td align="left"><span id="rolloutRsourceShortage" style="font-weight: bold;"></span></td>
												</tr>
												<tr>
													<td align="right"><spring:message code="lts.address.ltsProof" text="LTS Address Proof Required"/> : </td>
													<td align="left"><span id="rolloutLtsBlackListAddr" style="font-weight: bold;"></span></td>
												</tr>
												<tr>
													<td align="right"><spring:message code="lts.address.imsProof" text="IMS Address Proof Required"/> : </td>
													<td align="left"><span id="rolloutImsBlackListAddr" style="font-weight: bold;"></span></td>
												</tr>
												<tr>
													<td align="right"><spring:message code="lts.address.fieldWorkPermit" text="Field Work Permit"/> : </td>
													<td align="left"><span id="rolloutFieldPermit" style="font-weight: bold;"></span></td>
												</tr>
												<tr>
													<td align="right"><spring:message code="lts.address.FTTC" text="FTTC"/> : </td>
													<td align="left"><span id="rolloutFttc" style="font-weight: bold;"></span></td>
												</tr>
												<tr>
													<td align="right"><spring:message code="lts.address.fiberBlockage" text="Fiber Blockage"/> : </td>
													<td align="left"><span id="rolloutFiberBlockage" style="font-weight: bold;"></span></td>
												</tr>																												
												<tr id="rolloutSuggestSrdReasonRow">
													<td align="right" valign="top"><spring:message code="lts.address.sugEarliestSRD" text="Suggested Earliest SRD"/> : </td>
													<td align="left"><span id="rolloutSuggestSrdReason" style="font-weight: bold;"></span></td>
												</tr>
												<tr>
													<td align="right"><spring:message code="lts.address.numOfEye" htmlEscape="false"/> :</td>
													<td align="left"><span id="rolloutNumOfEye" style="font-weight: bold;"></span></td>
												</tr>
											</table>
											<br/>
										</td>
									</tr>
								</table>
							</div>	
						</div>
						<div id="rolloutBlockageDiv" class="ui-widget" style="display: none; visibility: visible;">
								<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
									<div id="rolloutUimBlockage" "class="contenttext" style="font-weight:bold; margin-left: 1.5em; text-align: left;">
									</div>
									
									<p></p>
								</div>
							</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form:form>


<c:if test="${ not empty requestScope.errorMsgList }">
<div id="errorMsgDiv">
<br>
	<table width="90%" border="0">
		<tr>
			<td width="10%">&nbsp;</td>											
			<td width="80%" align="left">
				<div id="error_Msg_div" class="ui-widget" style="visibility: visible;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<c:forEach items="${requestScope.errorMsgList }" var="errorMsg">
							<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
								<div  class="contenttext">
									<c:out value="${errorMsg}"></c:out>
								</div>
							<p></p>
						</c:forEach>
					</div>
				</div>													
			</td>
			<td width="10%">&nbsp;</td>
		</tr>
	</table>
</div>
</c:if>

<div id="continueDiv">
	<table width="100%" border="0" cellspacing="0">
	<tr>
		<td align="right"> 
		<br>
		<div class="button">
				<a href="#" id="continueBtn"><spring:message code="lts.mis.next" text="Next"/></a>
			</div> 
		</td>
	</tr>
	</table>
</div>


<script type="text/javascript">
	$(ltsaddressrollout.actionPerform);	
	function erCheck(){
		if ($('input[name="externalRelocate"]:checked').val() == "true" && $('input[name="dnchange"]').val() == "true") {
			alert("The charge of DN change will be auto waived. No approval required.");			
		}

	}
	</script>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>