<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<script type="text/javascript" src="js/web/lts/ltscustomerinquiry.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<c:if test="${not empty sessionScope.sessionLtsOrderCapture && not empty sessionScope.sessionLtsOrderCapture.orderType}">
	<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
	    <jsp:param name="step" value="1" />
	</jsp:include>
</c:if>


<br/>
<form:form method="POST" id="ltsCustomerInquiryForm" name="ltsCustomerInquiryForm" commandName="ltsCustomerInquiryCmd" autocomplete="off">
<form:hidden path="formAction"/>
<form:hidden path="allowCreateOrder" />
<form:hidden path="allowSearchProfile" />
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<c:set var="profile" value="${sessionScope.sessionLtsOrderCapture.ltsServiceProfile}" />
<c:set var="profileAddressRollout" value="${profile.address.addressRollout}" />	


<table width="98%" align="center">
	<tr>
		<td>
		<div class="paper_w2 round_10">
		<br/>
			<table width="98%" align="center">
				<tr>
					<td><div id="s_line_text"><spring:message code="lts.custenq.customerEnquiry" htmlEscape="false"/></div></td>
				</tr>
			</table>
			<br/>
			<table width="90%" border="0" cellspacing="4" cellpadding="4" class="contenttext" align="center">
				
				<!-- 
				<tr>
					<td width="15%" >
						Document Type & ID<span class="contenttext_red">*</span> :  
					</td>
					<td>
						<form:select path="docType" cssClass="searchCriteria">
							<form:option value="HKID" label="HKID" />
							<form:option value="PASS" label="Passport" />
							<form:option value="BS" label="BR" />
						</form:select>
						<form:input path="docNum" size="15" cssClass="searchCriteria" />
					</td>
					<td>&nbsp;</td>
				</tr>
				 -->
				<tr>
					<td width="20%" >
						<spring:message code="lts.custenq.srvNum" htmlEscape="false"/><span class="contenttext_red">*</span> :
					</td>
					<td>
						<form:input path="serviceNum" size="29" cssClass="searchCriteria" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<c:if test="${sessionScope.bomsalesuser.channelId == 1}">
					<tr>
						<td>
							<spring:message code="lts.custenq.thirdPartyApp" htmlEscape="false"/><span class="contenttext_red">*</span> : 
						</td>
						<td>
							<form:radiobutton path="thirdPartyApplication" value="true" /> <spring:message code="lts.custenq.yes" htmlEscape="false"/>
							<form:radiobutton path="thirdPartyApplication" value="false"  /> <spring:message code="lts.custenq.no" htmlEscape="false"/>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>
							<spring:message code="lts.custenq.dummyDoc" htmlEscape="false"/><span class="contenttext_red">*</span> :
						</td>
						<td>
							<form:radiobutton path="dummyDoc" value="true"  /> <spring:message code="lts.custenq.yes" htmlEscape="false"/>
							<form:radiobutton path="dummyDoc" value="false" /> <spring:message code="lts.custenq.no" htmlEscape="false"/>
						</td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
				<c:if test="${sessionScope.sessionLtsTeamFunction.displayStaffPlan == 'Y'}">
					<tr>
						<td>
							<spring:message code="lts.custenq.applyStaffPlan" htmlEscape="false"/><span class="contenttext_red">*</span> :
						</td>
						<td colspan="2">
							<form:radiobutton path="upgradeToStaffPlan" value="true" /> <spring:message code="lts.custenq.yes" htmlEscape="false"/>
							<form:radiobutton path="upgradeToStaffPlan" value="false"  /> <spring:message code="lts.custenq.no" htmlEscape="false"/>
						</td>
					</tr>
					<tr id="staffIdTr" style="display:none">
						<td>
							<spring:message code="lts.custenq.staffId" htmlEscape="false"/><span class="contenttext_red">*</span> :
						</td>
						<td>
							<form:input path="staffId" id="staffId" size="23" />
						</td>
						<td>
						</td>
					</tr>
				
				</c:if>
				
				<c:if test="${ltsCustomerInquiryCmd.allowSearchProfile}">
					<tr>
						<td >&nbsp;</td>
						<td colspan="2">
							<br>
							<div class="func_button">
								<a id="submitSeachBtn" style="color:white"  class="searchCriteria" href="#"><spring:message code="lts.custenq.search" htmlEscape="false"/></a>
							</div>
							&nbsp;&nbsp;
							<div class="func_button">
								<a id="clearSearchBtn" style="color:white" class="searchCriteria" href="#"><spring:message code="lts.custenq.clear" htmlEscape="false"/></a>
							</div>
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="3">
						<spring:hasBindErrors name="ltsCustomerInquiryCmd">
							<div id="error_form" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_msg" class="contenttext">
										<form:errors path="*" cssClass="error" />				
									</div>
									<p></p>
								</div>
							</div>
						</spring:hasBindErrors>		
					</td>
				</tr>			
			</table>
			</div>
		</td>
	</tr>
</table>

</form:form>

<br>

<c:if test='${not empty profile && profile != null }'>

	<script>
		$('.searchCriteria').attr('disabled', 'disabled');	
	</script>

<c:if test='${not empty profile.primaryCust.wipMessage }'>
	<script>
		alert("${profile.primaryCust.wipMessage}"); 
	</script>
</c:if>

<div id="customerProfileDiv">
	<table width="100%" border="0">
		<tr>
			<td width ="98%" align="center">
	  	 		<div id="tabs" class="paper_w2 ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible" style="width:98%; height:10%">
					<ul>
						<li id="custInformationTab">
							<a id="showCustInformation" href="#custInformation"><spring:message code="lts.custenq.custInfo" htmlEscape="false"/></a>
						</li>
						<!-- 
						<li id="custRemarkTab" style="display: none;">
							<a id="showCustRemark" href="#custRemark">Customer Remark</a>
						</li>
						 -->
					</ul>
					<div id="custInformation">
						<div id="custOverview">
							<div id="s_line_text"><spring:message code="lts.custenq.custOverview" htmlEscape="false"/></div>
							<br/>
							<table width="95%" border="0" cellspacing="5" cellpadding="5" align="center" >
								<tr>
									<td width="3%">&nbsp;</td>
									<td width="25%">
										<spring:message code="lts.custenq.srvNum" htmlEscape="false"/> :
									</td>
									<td>
										<span class="bold">
											<c:out value="${fn:substring(profile.searchCriteriaDn, 4, 12)}" />
										</span>
									</td>
									<td width="5%">&nbsp;</td>
								</tr>
								<c:if test="${profile.primaryCust.docType == 'BS'}">
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.companyName" htmlEscape="false"/> :</td>
										<td>	
											<span class="bold"> 
												<c:out value="${profile.primaryCust.companyName}"/>
											</span>
										</td>
										<td width="5%">&nbsp;</td>											
									</tr>
								</c:if>	
								<c:if test="${profile.primaryCust.docType != 'BS'}">										
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.famTitle" htmlEscape="false"/> :</td>
										<td>
											<span class="bold">
												<c:out value="${profile.primaryCust.title}"/>
											</span> 
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.custName" htmlEscape="false"/> :</td>
										<td>
											<span class="bold"> 
												<c:out value="${profile.primaryCust.lastName}"/>&nbsp;<c:out value="${profile.primaryCust.firstName}"/>
											</span>
										</td>
									</tr>
								</c:if>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.docId" htmlEscape="false"/> :</td>
									<td>
										<span class="bold"> 
											(<c:out value="${profile.primaryCust.docType}"/>)&nbsp;<c:out value="${profile.primaryCust.docNum}"/>
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.idVerified" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.primaryCust.idVerifyInd ? 'Y' : 'N'}" />
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.HKTPremier" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profileAddressRollout.hktPremier}" default="N" />
										</span>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.srvType" htmlEscape="false"/> :</td>
									<td> 
										<span class="bold">
											<c:out value="${profile.srvType }" /> / <c:out value="${profile.datCd }" />
										</span> 
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.srvLineStatus" htmlEscape="false"/> :</td> 
									<td>
										<span class="bold">
											<c:out value="${profile.inventStsDesc}" />
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.tariff" htmlEscape="false"/> :</td>
									<td> 
										<span class="bold">
										 	<c:out value="${profile.tariff == 'R' ? 'Residential' : 'Business'}"/>
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.WIP" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.primaryCust.wipInd}" />
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.WIPReMsg" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<b><c:out value="${profile.primaryCust.wipMessage}" /></b>
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<br/>
						</div>
						
						<div id="addrProfile">
							<div id="s_line_text"><spring:message code="lts.custenq.existAddrProfile" htmlEscape="false"/></div>
							<br/>
							<table width="95%" border="0" cellspacing="5" cellpadding="5" align="center">
								<c:choose>
									<c:when test="${profile.address.addressRollout != null }">
										<tr>
											<td width="3%">&nbsp;</td>
											<td width="25%"><spring:message code="lts.custenq.IA" htmlEscape="false"/> :</td>
											<td>
												<span class="bold">
													<c:out value="${profileAddressRollout.fullAddress}" />
												</span> 
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.ltsHousingType" htmlEscape="false"/> :</td>
											<td>
												<span class="bold">
													<c:out value="${profileAddressRollout.ltsHousingCatDesc}" />
												</span>
											</td>
											<td>&nbsp;</td>
										</tr>										
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.ptMarker" htmlEscape="false"/> :</td>
											<td>
												<span class="bold">
													<c:if test="${not empty profileAddressRollout.hktPremier}">
														<c:out value="${profileAddressRollout.hktPremier}" />
													</c:if>
													<c:if test="${empty profileAddressRollout.hktPremier}">
														-
													</c:if>								
												</span>
											</td>
											<td>&nbsp;</td>
										</tr>										
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.2nBuild" htmlEscape="false"/> :</td>
											<td>
												<span class="bold">
													<c:out value="${profileAddressRollout.is2nBuilding ? 'Y' : 'N'}" />
												</span>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.eyeCoverage" htmlEscape="false"/> :</td>
											<td> 
												<c:choose>
													<c:when test="${profileAddressRollout.eyeCoverage}" >
														<span class="bold">Y</span>
													</c:when>
													<c:otherwise>
														<span style="color: red;">N</span>
													</c:otherwise>
												</c:choose>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.peCoverage" htmlEscape="false"/> :</td>
											<td>
												<c:choose>
													<c:when test="${profileAddressRollout.peCoverage}" >
														<span class="bold">Y</span>
													</c:when>
													<c:otherwise>
														<span class="bold">N</span>
													</c:otherwise>
												</c:choose>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.fieldWork" htmlEscape="false"/> :</td>
											<td>
												<span class="bold">
													<c:out value="${profileAddressRollout.fieldWorkPermit}" />
												</span>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td valign="top"><spring:message code="lts.custenq.bwCoverage" htmlEscape="false"/> :</td>
											<td>
												<span class="bold">
													${profileAddressRollout.availableBandwidth == "" ? "N/A" : profileAddressRollout.availableBandwidth}  
												</span>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.rsCoverage" htmlEscape="false"/> :</td>
											<td>
												<c:choose>
												<c:when test="${profileAddressRollout.pcdResourceShortage != null}">
													<span class="bold" style="color: red;">
														<c:out value="${profileAddressRollout.pcdResourceShortage}"/>
													</span>
												</c:when>
												<c:otherwise>
													<span class="bold">N</span>
												</c:otherwise>												
												</c:choose>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.diffFSA" htmlEscape="false"/> :</td>
											<td>
												<span class="bold">
													<c:out value="${profileAddressRollout.diffCustFsaExist ? 'Y' : 'N'}" />
												</span>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.existEyeProfileNum" htmlEscape="false"/> :</td>
											<td>
												<span class="bold">
													<c:out value="${profileAddressRollout.numOfEyeProfile}" />
												</span>
											</td>
											<td>&nbsp;</td>
										</tr>			
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="4" align="center">
												<div id="info_profile" class="ui-widget" style="visibility: visible;">
													<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
														<p>
															<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
														</p>
														<div id="info_profile_msg" >
															Cannot Found Address Rollout Information
														</div>
														<p></p>
													</div>
												</div>
											</td>
										</tr>			
									</c:otherwise>
								</c:choose>
							</table>
							<br>
						</div>
												
						<div id="srvProfile">
							<div id="s_line_text"><spring:message code="lts.custenq.existSrvProfile" htmlEscape="false"/></div>
							<br/>
							<table width="95%" border="0" cellspacing="5" cellpadding="5" align="center">
								<tr>
									<td width="3%">&nbsp;</td>
									<td width="25%"><spring:message code="lts.custenq.pendingLTSOrdExist" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.pendingOcid != null ? 'Y' : 'N' }" />
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.pendingLTSOrdId" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.pendingOcid}" default="--"/>
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.pendingLTSOrdSRD" htmlEscape="false"/> :</td>
									<td>
										<c:if test="${profile.pendingOcSrd != null}">
											<span class="bold" style="color: ${ profile.pendingOrderOverdue ? 'red;' : 'blue;' }">
												<c:out value="${profile.pendingOcSrd}"/>
											</span>
										</c:if>
										<c:if test="${profile.pendingOcSrd == null}">
											<span class="bold">
												--
											</span>
										</c:if>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.froz" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.dnExchFrozen ? 'Add extra 5 days SRD leadtime due to frozen exchange' : 'N'}" />
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.2nNum" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.twoNBuildInd ? 'Y' : 'N'}" />
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>	
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.specER" htmlEscape="false"/> :</td> 
									<td>
										<span class="bold">
											<c:out value="${profile.waiveCount}" default="--"/>
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.lineEffDate" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.effStartDate}" />
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.ltsTenure" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.ltsTenure}" /> Month(s)
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.pcdTeunre" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.imsTenure}" /> Month(s)
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.exPaymentMethod" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:if test="${not empty profile.accounts}">
												<c:forEach items="${profile.accounts}" var="account">
													<c:if test="${account.primaryAcctInd == true}">
														<c:if test="${account.payMethod == 'M'}">
															<spring:message code="lts.custenq.cash" htmlEscape="false"/>
														</c:if>	
														<c:if test="${account.payMethod == 'A'}">
															<spring:message code="lts.custenq.bankAutoPay" htmlEscape="false"/>
														</c:if>	
														<c:if test="${account.payMethod == 'C'}">
															<spring:message code="lts.custenq.creditCard" htmlEscape="false"/>
														</c:if>	
													</c:if>
												</c:forEach>
											</c:if>
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>																	
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.twinLine" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.twinLineInd ? 'Y' : 'N' }" />
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td valign="top"><spring:message code="lts.custenq.dupRing" htmlEscape="false"/> :</td>
									<td valign="top">
										<span class="bold">
										<c:choose>
											<c:when test="${profile.duplexNum != null}">
												DNX : <c:out value="${fn:substring(profile.srvNum, 4, 12)}" />
												 <br/>
												DNY : <c:out value="${fn:substring(profile.duplexNum, 4, 12)}" />	
											</c:when>
											<c:otherwise>
												N
											</c:otherwise>
										</c:choose>
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								
								<tr>
									<td>&nbsp;</td>
									<td><spring:message code="lts.custenq.exEyeType" htmlEscape="false"/> :</td>
									<td>
										<span class="bold">
											<c:out value="${profile.existEyeTypeDisplay}" default="N" />
										</span>
									</td>
									<td>&nbsp;</td>
								</tr>
								<c:if test="${not empty profile.existEyeType}">
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.relatedFsa" htmlEscape="false"/> :</td>
										<td>
											<span class="bold">
												<c:out value="${profile.relatedEyeFsa}" default="--"/>
											</span>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.pendingPCDOrdId" htmlEscape="false"/> :</td>
										<td>
											<span class="bold">
												<c:choose>
												<c:when test="${not empty sessionScope.sessionLtsOrderCapture.imsPendingOrder}">
													<c:if test="${not empty sessionScope.sessionLtsOrderCapture.imsPendingOrder.orderId}">
														(SB:&nbsp;
														<c:out value="${sessionScope.sessionLtsOrderCapture.imsPendingOrder.orderId}" default="--"/>
														)
													</c:if>
													&nbsp;
													<c:if test="${not empty sessionScope.sessionLtsOrderCapture.imsPendingOrder.ocId}">
														(BOM:&nbsp;
														<c:out value="${sessionScope.sessionLtsOrderCapture.imsPendingOrder.ocId}" default="--"/>
														)
													</c:if>
												</c:when>
												<c:otherwise>
													<c:out value="--" default="--"/>
												</c:otherwise>
												</c:choose>
											</span>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.pendingPCDOrdSRD" htmlEscape="false"/> :</td>
										<td>
											<span class="bold">
												<c:choose>
												<c:when test="${not empty sessionScope.sessionLtsOrderCapture.imsPendingOrder}">
													<c:if test="${not empty sessionScope.sessionLtsOrderCapture.imsPendingOrder.srdStart
																	&& not empty sessionScope.sessionLtsOrderCapture.imsPendingOrder.srdEnd}">
														<c:out value="${sessionScope.sessionLtsOrderCapture.imsPendingOrder.srdStart}" default="--"/>
														&nbsp;-&nbsp;
														<c:out value="${sessionScope.sessionLtsOrderCapture.imsPendingOrder.srdEnd}" default="--"/>
													</c:if>
												</c:when>
												<c:otherwise>
													<c:out value="--" default="--"/>
												</c:otherwise>
												</c:choose>
											</span>
										</td>
										<td>&nbsp;</td>
									</tr>																	
								</c:if>
								<c:if test="${not empty profile.itemDetails}">			
									<tr>
										<td colspan="4">
											<div id="s_sub_line_text"><spring:message code="lts.custenq.srvlLineTP" htmlEscape="false"/></div>
											<table width="100%" border="0">
												<tr>
													<td width="100%">
														<table class="table_style" width="100%" border="1">
															<tr>
																<th align="center"><spring:message code="lts.custenq.tpDesc" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.tpCat" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.IDDMin" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.effDate" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.endDate" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.remainConMth" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.grossEffPrice" htmlEscape="false"/></th>
															</tr>
															<c:set var="rowCount" value="0" />
															<c:forEach items="${profile.itemDetails}" var="profileItemDetail">
																<c:if test="${profileItemDetail.itemType == 'S' && profileItemDetail.itemId != null}">
																	<c:set var="rowCount" value="${rowCount + 1 }"/>	
																	<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" >
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.itemDetail.itemDisplayHtml}" default="-" /></span></td>
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.itemDetail.tpCatg}" default="-" /></span></td>
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.iddFreeMin == '0' ? '-' : profileItemDetail.iddFreeMin}" default="-" /></span></td>
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.conditionEffStartDate}" default="-" /></span></td>
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.conditionEffEndDate}" default="-" /></span></td>
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.remainContractMonth}" default="-" /></span></td>
																		<td align="center"><span class="bold">$<c:out value="${profileItemDetail.itemDetail.grossEffPrice}" default="-"/></span></td>
																	</tr>
																</c:if>
															</c:forEach>
															<c:if test="${rowCount == 0}">
																<tr bgcolor="#FFFFFF">
																	<td colspan="7" align="center"><b>NIL</b></td>
																</tr>
															</c:if>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								
									<tr>
										<td colspan="4">
											<div id="s_sub_line_text"><spring:message code="lts.custenq.otherVASOff" htmlEscape="false"/></div>
											<table width="100%" border="0">
												<tr>
													<td width="100%">
														<table class="table_style" width="100%" border="1">
															<tr>
																<th align="center"><spring:message code="lts.custenq.otherCat" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.offDesc" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.effDate" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.endDate" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.proStartDate" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.proEndDate" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.grossEffPrice" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.netEffPice" htmlEscape="false"/></th>
																<!-- <th align="center">Upgrade eye2A</th>-->
																<th align="center"><spring:message code="lts.custenq.upgEye3A" htmlEscape="false"/></th>																		
															</tr>
															<c:set var="rowCount" value="0" />
															<c:forEach items="${profile.itemDetails}" var="profileItemDetail" varStatus="offerDetailCount">
																<c:if test="${profileItemDetail.itemType == 'V' && profileItemDetail.itemId != null}">
																	<c:set var="rowCount" value="${rowCount + 1 }"/>
																	<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" >
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.itemDetail.tpCatg}" default="-" /></span></td>
																		<td align="center"><span class="bold">${profileItemDetail.itemDetail.itemDisplayHtml}</span></td>
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.conditionEffStartDate}" default="-" /></span></td>
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.conditionEffEndDate}" default="-" /></span></td>
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.promotionStartDate}" default="-" /></span></td>
																		<td align="center"><span class="bold"><c:out value="${profileItemDetail.promotionEndDate}" default="-" /></span></td>
																		<td align="center"><span class="bold">$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.grossEffPrice : '0'}" default="-" /></span></td>
																		<td align="center"><span class="bold">$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.netEffPrice : '0'}" default="-" /></span></td>
																		<!-- <td align="center"><span class="bold">
																			<c:choose>
																			<c:when test="${not empty profileItemDetail.itemActions}">
																				<c:forEach items="${profileItemDetail.itemActions}" var="itemAction" varStatus="itemActionStatus">
																					<c:if test="${itemAction.toProd == 'eye2a' || itemAction.toProd == 'EYE2A'}">
																						<c:choose>
																							<c:when test="${itemAction.action == 'O' }">
																								Auto Out 
																								<c:if test="${itemAction.penaltyHandle != null}">
																									(<c:out value="${itemAction.penaltyHandle == 'AW' ? 'Auto Waived' : 'Generate Penalty'}" />)
																								</c:if>			
																							</c:when>
																							<c:otherwise>
																								Carry Forward	
																							</c:otherwise>
																						</c:choose>
																					</c:if>
																				</c:forEach>
																			</c:when>
																			<c:otherwise>
																				Carry Forward	
																			</c:otherwise>
																			</c:choose>
																			</span></td>-->
																			<td align="center"><span class="bold">		
																			<c:choose>
																			<c:when test="${profileItemDetail.forceRetain}">
																				<spring:message code="lts.offerDtl.carryFwd" text="Carry Forward"/>
																			</c:when>
																			<c:when test="${not empty profileItemDetail.itemActions}">																				
																				<c:forEach items="${profileItemDetail.itemActions}" var="itemAction" varStatus="itemActionStatus">
																					<c:if test="${itemAction.toProd == 'eye3a' || itemAction.toProd == 'EYE3A'}">
																						<c:choose>
																		
																							<c:when test="${itemAction.action == 'O' }">
																								Auto Out 
																								<c:if test="${itemAction.penaltyHandle != null}">
																									(<c:out value="${itemAction.penaltyHandle == 'AW' ? 'Auto Waived' : 'Generate Penalty'}" />)
																								</c:if>			
																							</c:when>
																							<c:otherwise>
																								<spring:message code="lts.offerDtl.carryFwd" text="Carry Forward"/>	
																							</c:otherwise>
																						</c:choose>
																					</c:if>
																				</c:forEach>																						
																			</c:when>
																			<c:otherwise>
																				Carry Forward	
																			</c:otherwise>
																			</c:choose>
																			</span></td>
																		</tr>
																	</c:if>
																</c:forEach>
																<c:if test="${rowCount == 0}">
																	<tr bgcolor="#FFFFFF">
																		<td colspan="10" align="center"><b>NIL</b></td>
																	</tr>
																</c:if>																																	
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
								</c:if>
								<c:if test="${not empty profile.eyeFsaProfile && not empty profile.eyeFsaProfile.items}">	
									<tr>
										<td colspan="4">
											<div id="s_sub_line_text"><spring:message code="lts.custenq.fsaVASOff" htmlEscape="false"/></div>
											<table width="100%" border="0">
												<tr>
													<td width="100%">
														<table class="table_style" width="100%" border="1">
															<tr>
																<th align="center"><spring:message code="lts.custenq.offDesc" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.effDate" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.endDate" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.grossEffPrice" htmlEscape="false"/></th>
																<th align="center"><spring:message code="lts.custenq.netEffPice" htmlEscape="false"/></th>
																<!-- <th align="center">Upgrade eye2A</th> -->
																<th align="center"><spring:message code="lts.custenq.upgEye3A" htmlEscape="false"/></th>
															</tr>
															<c:set var="rowCount" value="0" />
																<c:forEach items="${profile.eyeFsaProfile.items}" var="profileItemDetail" varStatus="offerDetailCount">
																	<c:if test="${profileItemDetail.itemType == 'MP' || profileItemDetail.itemType == 'XMP'}">
																		<c:set var="rowCount" value="${rowCount + 1 }"/>
																		<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" >
																			<td align="center"><span class="bold"><c:out value="${profileItemDetail.itemDetail.itemDisplayHtml}" default="-" /></span></td>
																			<td align="center"><span class="bold"><c:out value="${profileItemDetail.conditionEffStartDate}" default="-" /></span></td>
																			<td align="center"><span class="bold"><c:out value="${profileItemDetail.conditionEffEndDate}" default="-" /></span></td>
																			<td align="center"><span class="bold">$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.grossEffPrice : '0'}" default="-" /></span></td>
																			<td align="center"><span class="bold">$<c:out value="${profileItemDetail.paidVas ? profileItemDetail.itemDetail.netEffPrice : '0'}" default="-" /></span></td>
																			<!-- <td align="center"><span class="bold">
																			<c:choose>
																			<c:when test="${not empty profileItemDetail.itemActions}">
																				<c:forEach items="${profileItemDetail.itemActions}" var="itemAction" varStatus="itemActionStatus">
																					<c:if test="${itemAction.toProd == 'eye2a' || itemAction.toProd == 'EYE2A'}">
																						<c:choose>
																							<c:when test="${itemAction.action == 'O' }">
																								Auto Out 
																								<c:if test="${itemAction.penaltyHandle != null}">
																									(<c:out value="${itemAction.penaltyHandle == 'AW' ? 'Auto Waived' : 'Generate Penalty'}" />)
																								</c:if>			
																							</c:when>
																							<c:otherwise>
																								Carry Forward	
																							</c:otherwise>
																						</c:choose>
																					</c:if>
																				</c:forEach>
																			</c:when>
																			<c:otherwise>
																				Carry Forward	
																			</c:otherwise>
																			</c:choose>
																			</span></td>-->
																			<td align="center"><span class="bold">		
																			<c:choose>
																			<c:when test="${not empty profileItemDetail.itemActions}">																				
																				<c:forEach items="${profileItemDetail.itemActions}" var="itemAction" varStatus="itemActionStatus">
																					<c:if test="${itemAction.toProd == 'eye3a' || itemAction.toProd == 'EYE3A'}">
																						<c:choose>
																							<c:when test="${itemAction.action == 'O' }">
																								Auto Out 
																								<c:if test="${itemAction.penaltyHandle != null}">
																									(<c:out value="${itemAction.penaltyHandle == 'AW' ? 'Auto Waived' : 'Generate Penalty'}" />)
																								</c:if>			
																							</c:when>
																							<c:otherwise>
																								Carry Forward	
																							</c:otherwise>
																						</c:choose>
																					</c:if>
																				</c:forEach>																						
																			</c:when>
																			<c:otherwise>
																				Carry Forward	
																			</c:otherwise>
																			</c:choose>
																			</span></td>
																		</tr>
																	</c:if>
																</c:forEach>
																<c:if test="${rowCount == 0}">
																	<tr bgcolor="#FFFFFF">
																		<td colspan="7" align="center"><b>NIL</b></td>
																	</tr>
																</c:if>																																	
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
								</c:if>
							</table>
							<br/>
						</div>
					
						<c:if test="${not empty sessionScope.sessionLtsOrderCapture.upgradeEyeInfo}">
							<c:set var="upgradeInfo" value="${sessionScope.sessionLtsOrderCapture.upgradeEyeInfo}" />
							<div id="upgradeInfo">
								<div id="s_line_text"><spring:message code="lts.custenq.updEyeInfo" htmlEscape="false"/></div>
								<br/>
								<table width="100%" border="0" cellspacing="5" cellpadding="5">
									<tr>
										<td width="3%">&nbsp;</td>						
										<td width="25%"><spring:message code="lts.custenq.prepayReq" htmlEscape="false"/> :</td>
										<td>
											<span class="bold">
												<c:out value="${empty upgradeInfo.prepayment ? 'Not Required' : upgradeInfo.prepayment }" default="N/A"/>
											</span>
											&nbsp; 
											<span style="color: red;">
												<c:out value="${not empty upgradeInfo.prepayment ? upgradeInfo.prepaymentRemark : '' }"/>
											</span>
											&nbsp; 
											<span style="font-size: 9px; color: #616161;">(Note: According to existing payment method)</span>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.contPeriodReq" htmlEscape="false"/> :</td>
										<td>
											<span class="bold">
												<c:out value="${empty upgradeInfo.contractPeriod? 'N/A' : upgradeInfo.contractPeriod}"/>
											</span>
										</td>
										<td>&nbsp;</td>
									</tr>
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.custenq.suggEarSRD" htmlEscape="false"/> :</td>
											<td>
												<span class="bold">
													<c:out value="${upgradeInfo.suggestSrd}" /> - <c:out value="${upgradeInfo.suggestSrdReason}" />
												</span> 
											</td>
											<td>&nbsp;</td>
										</tr>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.admCharge" htmlEscape="false"/> :</td>
										<td>
											<span class="bold">
												<c:out value="${empty upgradeInfo.adminCharge? 'N/A': upgradeInfo.adminCharge}" />
											</span>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.cancelCharge" htmlEscape="false"/> :</td>
										<td>
											<span class="bold">
												<c:out value="${empty upgradeInfo.cancelCharge? 'N/A': upgradeInfo.cancelCharge}" />
											</span>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><spring:message code="lts.custenq.returnExDev" htmlEscape="false"/> :</td>
										<td>
											<span class="bold">
												<c:out value="${empty upgradeInfo.returnDeviceInd? 'N/A': upgradeInfo.returnDeviceInd}" />
											</span>
										</td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</div>
							<br>
						</c:if>
					</div>
			<!-- 			
						<div id="custInformationNextDiv">
							<table width="100%">
								<tr>
									<td colspan="4" align="right">
										<div class="func_button">
											<a href="#custRemark"  style="font: 100% Calibri, Trebuchet MS, sans-serif; color: white;" id="custInformationNextBtn">Next ></a>
										</div>
									</td>
								</tr>
							</table>		
						</div>
					</div>
					
					<div id="custRemark">
						<table width="100%" border="0" cellspacing="3" cellpadding="4" >
							<tr>
								<td colspan="4">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2">
									 <table class="table_style" width="90%" border="1" align="center">
										<tr>
											<th width="60%">Remarks</th>
											<th width="20%">Last Update Date</th>
											<th width="20%">Last Update By</th>
										</tr>
										
										<c:choose>
											<c:when test="${not empty profile.primaryCust.custRemarks}" >
												<c:set var="rowCount" value="0" />
												<c:forEach items="${profile.primaryCust.custRemarks}" var="custRemark">
													<c:set var="rowCount" value="${rowCount + 1 }" />
													<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}" >
														<td style="text-align: left;">
															<span class="bold" >
															${custRemark.remarks}
															</span>
														</td>
														<td>
															<span class="bold">
																<c:out value="${custRemark.lastUpdDate}"/>
															</span>
														</td>
														<td>
															<span class="bold">
																<c:out value="${custRemark.lastUpdBy}"/>
															</span>
														</td>
													</tr>			
												</c:forEach>
											</c:when>
											<c:otherwise>
													<tr>
														<td colspan="3" align="center">
															<b>No Customer Remark</b>
														</td>
													</tr>			
											</c:otherwise>
										</c:choose>
									</table>
								</td>
								<td width="5%">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4">&nbsp;</td>
							</tr>
						</table>						
					</div>
				-->
			</td>
		</tr>
	</table>
</div>
</c:if>


<c:if test='${ltsCustomerInquiryCmd.allowCreateOrder}'>
	<br/>
	<div id="continueBtnDiv">
		<table width="90%" align="center">
			<tr><td align="right">

			<c:if test="${ (not empty sessionScope.sessionLtsOrderMode && sessionScope.sessionLtsOrderMode == 'BD')
								|| (not empty sessionScope.sessionLtsTeamFunction && sessionScope.sessionLtsTeamFunction.upgradeOrder == 'Y') }">
				<div class="button">
					<a href="#" id="upgradeBtn"><spring:message code="lts.custenq.eyeUpg" htmlEscape="false"/></a>
				</div>
			</c:if>

			&nbsp;&nbsp;
			
			<c:if test="${ (not empty sessionScope.sessionLtsOrderMode && sessionScope.sessionLtsOrderMode == 'BD')
								|| (not empty sessionScope.sessionLtsTeamFunction && sessionScope.sessionLtsTeamFunction.renewalOrder == 'Y') }">
				<div class="button">
					<a href="#" id="retentionBtn"><spring:message code="lts.custenq.renewal" htmlEscape="false"/></a>
				</div>					
			</c:if>
			
			&nbsp;&nbsp;
			
			<c:if test="${ (not empty sessionScope.sessionLtsOrderMode && sessionScope.sessionLtsOrderMode == 'BD')
								|| (not empty sessionScope.sessionLtsTeamFunction && sessionScope.sessionLtsTeamFunction.standaloneVasOrder == 'Y') }">
				<div class="button">
					<a href="#" id="standaloneVasBtn"><spring:message code="lts.custenq.standaloneVAS" htmlEscape="false"/></a>
				</div>					
			</c:if>
			</td></tr>
		</table>
	</div>
</c:if>
<br/>

<c:if test='${not empty sessionScope.sessionLtsSrvProfileMsg}'> 
	<div id="errorDiv" style="width: 70%;">
		<div id="error_profile" class="ui-widget" style="visibility: visible;">
			
			<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
				<c:forEach items="${sessionScope.sessionLtsSrvProfileMsg}" var="errorMsg">
					<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					</p>
					<div id="error_profile_msg" class="contenttext">
						<c:out value="${errorMsg}"></c:out>
					</div>
					<p></p>
				</c:forEach>
			</div>
			
		</div>
	</div>
</c:if>

<script type='text/javascript'>
	$(ltscustomerinquiry.actionPerform);
	var alertMsg = '${alertMsg}';
	if(alertMsg != ''){
		alert(alertMsg);
	}
</script>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>