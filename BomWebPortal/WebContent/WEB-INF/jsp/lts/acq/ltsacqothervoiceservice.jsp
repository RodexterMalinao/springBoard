<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<script type="text/javascript" src="js/web/lts/acq/ltsacqothervoiceservice.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp" >
    <jsp:param name="step" value="8" />
</jsp:include>

<div class="cosContent">
<form:form method="POST" id="ltsOtherVoiceServiceForm" name="ltsOtherVoiceServiceForm"  commandName="ltsAcqOtherVoiceServiceCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="formAction" />
<form:hidden path="secondDelDiffCust" />
<form:hidden path="secondDelDiffAddress" />
<form:hidden path="allowSecondDelConfirmSameAddress" />
<form:hidden path="secondDelProfileValid" />
<form:hidden path="secondDelMsg" />
<form:hidden path="showSecondDelRedeemPremium" />
<form:hidden path="secondDelOptOutIdd"/>
<form:hidden path="searchMethod"/>

<div id="s_line_text"> <spring:message code="lts.acq.othersVoiceService.othersVoiceService" arguments="" htmlEscape="false"/> </div>
<table class="paper_w2 round_10" width="100%" class="tablegrey">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="6" class="contenttext">
				<tr>
					<td colspan="4"><div id="s_line_text"> <spring:message code="lts.acq.othersVoiceService.2ndDELService" arguments="" htmlEscape="false"/> </div></td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" align="right">
						<spring:message code="lts.acq.othersVoiceService.create2ndDELService" arguments="" htmlEscape="false"/> &nbsp;&nbsp;&nbsp;&nbsp;  
						<br/>
						<spring:message code="lts.acq.othersVoiceService.createDELRetentionServicePt" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span> : 
					</td>
					<td width="60%" colspan="3" align="left">
						<form:radiobutton path="create2ndDel" value="true" disabled="${sessionScope.sessionLtsAcqOrderCapture.containPrewiringVAS || sessionScope.sessionLtsAcqOrderCapture.containPreInstallVAS || !isArpuAllow2Del}"/><b><spring:message code="lts.acq.common.yes" htmlEscape="false"/></b>
						&nbsp;
						<form:radiobutton path="create2ndDel" value="false" disabled="${ltsAcqOtherVoiceServiceCmd.portInDuplexDn}"/><b><spring:message code="lts.acq.common.no" htmlEscape="false"/></b>
						&nbsp;&nbsp;<form:errors path="create2ndDel" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${sessionScope.sessionLtsAcqOrderCapture.containPrewiringVAS}">
					<tr>
						<td></td>
						<td>
							<div id="error_profile" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_profile_msg" class="contenttext">
										 <spring:message code="lts.acq.othersVoiceService.notAllowCreate2ndDELforPrewiring" arguments="" htmlEscape="false"/>
									</div>
									<p></p>
								</div>
							</div>
						</td>
					</tr>
				</c:if>
				<c:if test="${sessionScope.sessionLtsAcqOrderCapture.containPreInstallVAS}">
					<tr>
						<td></td>
						<td>
							<div id="error_profile" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_profile_msg" class="contenttext">
										 <spring:message code="lts.acq.othersVoiceService.notAllowCreate2ndDELforPreinstall" arguments="" htmlEscape="false"/>
									</div>
									<p></p>
								</div>
							</div>
						</td>
					</tr>
				</c:if>
				<c:if test="${!isArpuAllow2Del}">
					<tr>
						<td></td>
						<td>
							<div id="error_profile" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_profile_msg" class="contenttext">
										 <spring:message code="lts.acq.othersVoiceService.1stDELARPULess992ndDELnotAllow" arguments="" htmlEscape="false"/>
									</div>
									<p></p>
								</div>
							</div>
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="4">
						<div id="new2ndDelDiv" class="2ndDelDiv" style="display: none;">
							<table width="100%" cellpadding="2" cellspacing="6">
								<tr>
									<td width="40%" align="right"><spring:message code="lts.acq.othersVoiceService.new2ndServiceNum" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span> : </td>
									<td width="60%" colspan="2" align="left">
										<form:select path="new2ndDelSrvStatus" cssClass="new2ndDel searchCriteria">											
											<c:if test="${!ltsAcqOtherVoiceServiceCmd.portInDuplexDn && !ltsAcqOtherVoiceServiceCmd.dnPoolSelectionMethod}">
											<form:option value="20"><spring:message code="lts.acq.othersVoiceService.working" arguments="" htmlEscape="false"/></form:option>
											<form:option value="70"><spring:message code="lts.acq.othersVoiceService.reserved" arguments="" htmlEscape="false"/></form:option>
											<form:option value="05"><spring:message code="lts.acq.othersVoiceService.DNPool" arguments="" htmlEscape="false"/></form:option>
											</c:if>
											<c:if test="${ltsAcqOtherVoiceServiceCmd.portInDuplexDn}">
											<form:option value=""><spring:message code="lts.acq.othersVoiceService.PIPB" arguments="" htmlEscape="false"/></form:option>
											</c:if>
											<c:if test="${ltsAcqOtherVoiceServiceCmd.dnPoolSelectionMethod}">
											<form:option value="05"><spring:message code="lts.acq.othersVoiceService.DNPool" arguments="" htmlEscape="false"/></form:option>
											</c:if>
										</form:select>
										&nbsp;										
										<form:input path="new2ndDelDn" cssClass="new2ndDel searchCriteria" readonly="${ltsAcqOtherVoiceServiceCmd.portInDuplexDn}"/>
										 &nbsp;
										 <c:if test="${!ltsAcqOtherVoiceServiceCmd.portInDuplexDn && !ltsAcqOtherVoiceServiceCmd.dnPoolSelectionMethod}">
										 <div class="func_button">
										 	<a href="#" id="searchDn" class="new2ndDel searchCriteria" ><spring:message code="lts.acq.othersVoiceService.search" arguments="" htmlEscape="false"/></a>
										 </div>
										 </c:if>
										 <c:if test="${!ltsAcqOtherVoiceServiceCmd.portInDuplexDn}">
										 <div class="func_button">
										 	<a href="#" id="clearDn" class="new2ndDel"><spring:message code="lts.acq.othersVoiceService.clear" arguments="" htmlEscape="false"/></a>
										 </div>
										 </c:if>
									</td>
								</tr>
								<tr>
									<td width="25%">&nbsp;</td>
									<td colspan="2" align="left">
										<form:errors path="new2ndDelDn" cssClass="error"/>
										<form:errors path="new2ndDelSrvStatus" cssClass="error"/>
										<form:errors path="secondDelProfileValid" cssClass="error"/>
									</td>
								</tr>
								<tr>
									<td colspan="2" width="80%"> 
										<c:if test="${not empty ltsAcqOtherVoiceServiceCmd.secondDelMsg}">
										       <c:if test="${!isChannelDS}">												 	
												 	<c:if test="${ltsAcqOtherVoiceServiceCmd.secondDelProfileValid}">
													 	<div id="info_profile" class="ui-widget" style="visibility: visible;">
															<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
																<p>
																	<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
																</p>
																<div id="info_profile_msg" class="contenttext">
																	${ltsAcqOtherVoiceServiceCmd.secondDelMsg}
																</div>
																<p></p>
															</div>
														</div>
												 	</c:if>
											 	</c:if>
											 	<c:if test="${isChannelDS}">												 	
												 	<c:if test="${ltsAcqOtherVoiceServiceCmd.secondDelProfileValid || ltsAcqOtherVoiceServiceCmd.secondDelDiffAddress}">
													 	<div id="error_profile" class="ui-widget" style="visibility: visible;">
															<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
																<p>
																	<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
																</p>
																<div id="error_profile_msg" class="contenttext">
																	${ltsAcqOtherVoiceServiceCmd.secondDelMsg}
																</div>
																<p></p>
															</div>
														</div>
												 	</c:if>
											 	</c:if>
											 	<c:if test="${!ltsAcqOtherVoiceServiceCmd.secondDelProfileValid}">
											 		<div id="error_profile" class="ui-widget" style="visibility: visible;">
														<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
															<p>
																<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
															</p>
															<div id="error_profile_msg" class="contenttext">
																${ltsAcqOtherVoiceServiceCmd.secondDelMsg}
															</div>
															<p></p>
														</div>
													</div>
											 	</c:if>
										</c:if>										
									</td>
									<td> &nbsp;</td>
								</tr>
								<tr>
									<td colspan="4">
									
										<c:if test="${ltsAcqOtherVoiceServiceCmd.secondDelProfileValid && sessionScope.sessionLtsAcqOrderCapture.secondDelServiceProfile != null}">
											<c:set var="secDelServiceProfile" value="${sessionScope.sessionLtsAcqOrderCapture.secondDelServiceProfile}" />
											<div id="secondDelServiceOverviewDiv">
												<table width="100%" cellspacing="4" cellpadding="6">
													<tr>
														<td colspan="4" align="left">
															<table width="100%" cellspacing="0" border="0" bgcolor="white">
																<tr> 
																	<td colspan="4" >
																		<div id="s_line_text"><spring:message code="lts.acq.othersVoiceService.2ndDELServiceOverview" arguments="" htmlEscape="false"/></div>
															    	</td>
															    </tr>
														  	</table>
														</td>
													</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.acq.othersVoiceService.familyTitle" arguments="" htmlEscape="false"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.primaryCust.title}" />
											 				 </span>
											 			</td>
											 		</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.acq.othersVoiceService.customerName" arguments="" htmlEscape="false"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.primaryCust.lastName}"/>&nbsp;<c:out value="${secDelServiceProfile.primaryCust.firstName}"/>
											 				 </span>
											 			</td>
											 		</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.acq.othersVoiceService.installationAddress" arguments="" htmlEscape="false"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.address.fullAddress}" />
											 				 </span>
											 			</td>
											 		</tr>
											 		<tr>
														<td width="10%">&nbsp;</td>
														<td width="25%"><spring:message code="lts.acq.othersVoiceService.IDVerifiedInd" arguments="" htmlEscape="false"/> :</td>
														<td colspan="2">
															<span class="bold">
																<c:out value="${secDelServiceProfile.primaryCust.idVerifyInd ? 'Y' : 'N'}" />
															</span>
														</td>
													</tr>	
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.acq.othersVoiceService.tariff" arguments="" htmlEscape="false"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.tariff == 'R' ? 'Residential' : 'Business'}"/>
											 				 </span>
											 			</td>
											 		</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.acq.othersVoiceService.serviceType" arguments="" htmlEscape="false"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
																<c:out value="${secDelServiceProfile.srvType }" /> / <c:out value="${secDelServiceProfile.datCd }" />
															</span> 
											 			</td>
											 		</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.acq.othersVoiceService.duplexRinging" arguments="" htmlEscape="false"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 <c:choose>
																<c:when test="${secDelServiceProfile.duplexNum != null}">
																	DNX : <c:out value="${fn:substring(secDelServiceProfile.srvNum, 4, 12)}" />
																	<br>
																	DNY : <c:out value="${fn:substring(secDelServiceProfile.duplexNum, 4, 12)}" />	
																</c:when>
																<c:otherwise>
																	N
																</c:otherwise>
															</c:choose>
															</span> 
											 			</td>
											 		</tr>											 													 			
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.acq.othersVoiceService.serviceLineStatus" arguments="" htmlEscape="false"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.inventStsDesc}" />
											 				 </span>
											 			</td>
											 		</tr>	
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.acq.othersVoiceService.WIP" arguments="" htmlEscape="false"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.primaryCust.wipInd}" />
											 				 </span>
											 			</td>
											 		</tr>	
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.acq.othersVoiceService.WIPReminderMessage" arguments="" htmlEscape="false"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.primaryCust.wipMessage}" />
											 				 </span>
											 			</td>
											 		</tr>												 													 													 													 													 													 													 																
												</table>
											</div>
										</c:if>
									
									<div id="numberSelectionDiv"  style="display: none;">		
		<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
			    <td><div id="s_line_text"><spring:message code="lts.acq.othersVoiceService.numberSelectionNewSerNum" arguments="" htmlEscape="false"/></div></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		<tr><td width="50%" valign="top">	
			<table border="0" cellspacing="4" cellpadding="6" class="contenttext">
				<tr>
				    <td width="10%"></td>
					<td width="19%"><div align="left"><b><form:radiobutton path="searchMethodRadio" value="noCriteriaRadio"/><spring:message code="lts.acq.othersVoiceService.noCriteria" arguments="" htmlEscape="false"/></b></div></td>
					<td width="22%"></td>				
				</tr>
				<tr>
				    <td></td>
				    <td><div align="left"><b><form:radiobutton path="searchMethodRadio" value="first5to8Radio"/><spring:message code="lts.acq.othersVoiceService.preferredFirst58Digits" arguments="" htmlEscape="false"/>: </b></div></td>
	                <td><div align="left"><form:input maxlength="8" path="first5To8Digits" /></div></td> 
				</tr>				
				<tr>
				    <td></td>
				    <td><div align="left"><b><form:radiobutton path="searchMethodRadio" value="last1to3Radio"/><spring:message code="lts.acq.othersVoiceService.preferredLast13Digits" arguments="" htmlEscape="false"/>: </b></div></td>
	                <td><div align="left"><form:input maxlength="3" path="last1To3Digits" /></div></td>
				</tr>
				<tr>
				    <td></td>
				    <td></td>
	                <td></td>
				</tr>
				<tr>
				    <td></td>
				    <td>
				    <div class="func_button">
                    <a id="searchDnPool" style="color:white" href="#"><spring:message code="lts.acq.othersVoiceService.search" arguments="" htmlEscape="false"/></a>
                    </div>
				    </td>
	                <td ></td> 
				</tr>
				<tr>
				    <td></td>
				    <td align="left"><form:errors path="searchErrMsg" cssClass="error" /></td>
	                <td></td>
				</tr>													
			</table></td>
			<td width="50%" valign="top">				
			</td></tr>				
		</table>
		<div id="s_line_text"></div>
		<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		<tr><td width="50%" valign="top">		
		<table border="0" cellspacing="4" cellpadding="6" class="contenttext">			
			<tr><td width="26%" valign="top">
			<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">	
			<tr>			   
				<td width="100%"><div align="right"><b><spring:message code="lts.acq.othersVoiceService.plsSelectaNumber" arguments="" htmlEscape="false"/> : </b></div></td>
			</tr>			
			<tr>
			    <td align="right">
			    <div class="func_button" align="right">
                    <a id="lockBtn" style="color:white" href="#"><spring:message code="lts.acq.othersVoiceService.lock" arguments="" htmlEscape="false"/></a>
                </div>
			    </td>
			</tr>
			</table>
			</td>
			<td width="20%" valign="top">			
			<c:if test="${not empty ltsAcqOtherVoiceServiceCmd.numSelectionList}">
			<c:forEach items="${ltsAcqOtherVoiceServiceCmd.numSelectionList}" var="itemList" varStatus="itemListStatus">
			<div align="left">
			<table border="0" cellspacing="1" cellpadding="1" class="contenttext">	
		    <tr><td>
		    <form:checkbox path="numSelectionStringList" value="${itemList.srvNum}" /> ${itemList.displaySrvNum}		    	      
		    </td></tr></table></div>
			</c:forEach>
		    </c:if>							
			</td>
			</tr>			
		</table></td>
		<td width="50%" valign="top">		
		</td></tr>
		<tr>
			 <td align=center><form:errors path="lockNumErrMsg" cssClass="error" /></td>
	         <td></td>
		</tr>
		</table>		
		</div>
		
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
									
										 <div id="secondDelDiffCustDiv" style="display: none;">
										 	<table width="100%" cellspacing="3" cellpadding="4">
												<tr>
													<td colspan="4" align="left">
														<table width="100%" cellspacing="0" border="0" bgcolor="white">
															<tr> 
														    	<td colspan="4">
														    		<div id="s_line_text"><spring:message code="lts.acq.othersVoiceService.2ndDELCustomerVerification" arguments="" htmlEscape="false"/></div>
														    	</td>
														    	
														    </tr>
													  	</table>
													</td>
												</tr>
																	 	
										 		<tr>
										 			<td width="10%">&nbsp;</td>
										 			<td width="25%">
										 				<spring:message code="lts.acq.othersVoiceService.documentTypeNNumber" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span> : 
										 			</td>
										 			<td colspan="2">
										 				<form:select path="secondDelDocType">
										 					<form:option value="HKID"><spring:message code="lts.acq.othersVoiceService.HKID" arguments="" htmlEscape="false"/></form:option>
										 					<form:option value="PASS"><spring:message code="lts.acq.othersVoiceService.passport" arguments="" htmlEscape="false"/></form:option>
										 				</form:select>
										 				&nbsp;
										 				<form:input path="secondDelDocNum" />
										 				<form:errors path="secondDelDocNum" cssClass="error" /> 
										 			</td>
										 		</tr>										 		
										 		<tr>
										 			<td width="10%">&nbsp;</td>
										 			<td width="25%">
										 				<spring:message code="lts.acq.othersVoiceService.dummyDocument" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span> : 
										 			</td>
										 			<td colspan="2">
										 				<form:radiobutton path="secondDelDummyDoc" value="true" /> <b><spring:message code="lts.acq.common.yes" htmlEscape="false"/></b>
										 				&nbsp;
										 				<form:radiobutton path="secondDelDummyDoc" value="false" /> <b><spring:message code="lts.acq.common.no" htmlEscape="false"/></b>
										 				&nbsp;&nbsp;<form:errors path="secondDelDummyDoc" cssClass="error" />
										 			</td>
										 		</tr>
										 		<tr>
										 			<td width="10%">&nbsp;</td>
										 			<td width="25%">
										 				<spring:message code="lts.acq.othersVoiceService.thirdPartyApplication" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span> : 
										 			</td>
										 			<td colspan="2">
										 				<form:radiobutton path="secondDelThirdPartyAppl" value="true" /> <b><spring:message code="lts.acq.common.yes" htmlEscape="false"/></b>
										 				&nbsp;
										 				<form:radiobutton path="secondDelThirdPartyAppl" value="false" /> <b><spring:message code="lts.acq.common.no" htmlEscape="false"/></b>
										 				&nbsp;&nbsp;<form:errors path="secondDelThirdPartyAppl" cssClass="error" />
										 			</td>
										 		</tr>
										 		<tr>
										 			<td width="10%">&nbsp;</td>
										 			<td width="25%">&nbsp;</td>
										 			<td width="20%">&nbsp;</td>
										 			<td>
										 				<div class="func_button"> 
										 					<a href="#" id="confirmCust"><spring:message code="lts.acq.othersVoiceService.verify" arguments="" htmlEscape="false"/></a>
										 				</div>
										 			</td>
										 		</tr>
										 	</table>
										</div>
										
										<c:if test="${ltsAcqOtherVoiceServiceCmd.showSecondDelRedeemPremium}">
											 <div id="secondDelRedeemPremiumDiv">
											 	<table width="100%" cellpadding="5" cellspacing="6">
											 		<tr>
														<td colspan="4" align="left">
															<table width="100%" cellspacing="0" border="0" bgcolor="white">
																<tr> 
															    	<td colspan="4">
															    		<div id="s_line_text"><spring:message code="lts.acq.othersVoiceService.2ndDELMiscellaneous" arguments="" htmlEscape="false"/></div>
															    	</td>
															    </tr>
														  	</table>
														</td>
													</tr>
											 		
											 		<tr>
											 			<td colspan="4">
											 				<div>
												 				<table cellpadding="0" cellspacing="6" width="100%">
														 			<tr>
																		<td width="10%">&nbsp;</td>
																		<td width="25%"><spring:message code="lts.acq.othersVoiceService.redeemPreviousPremium" arguments="" htmlEscape="false"/><span class="contenttext_red">*</span> : </td>
																		<td width="65%" colspan="2" align="left">
																			<form:radiobutton path="secondDelRedeemPremium" value="true" /> <b><spring:message code="lts.acq.common.yes" htmlEscape="false"/></b>
																			&nbsp;
																			<form:radiobutton path="secondDelRedeemPremium" value="false" /> <b><spring:message code="lts.acq.common.no" htmlEscape="false"/></b>
																			&nbsp;&nbsp;<form:errors path="secondDelRedeemPremium" cssClass="error"/>
																		</td>
																	</tr>
													 			</table>
												 			</div>
											 			</td>
											 		</tr>
											 	</table>
											</div>
										</c:if>
										
										<c:if test="${ltsAcqOtherVoiceServiceCmd.secondDelDiffAddress}">
											 <div id="secondDelDiffAddressDiv">
											 	<table width="100%" cellpadding="5" cellspacing="6">
											 		<tr>
														<td colspan="4" align="left">
															<table width="100%" cellspacing="0" border="0" bgcolor="white">
																<tr> 
															    	<td colspan="4">
															    		<div id="s_line_text"><spring:message code="lts.acq.othersVoiceService.2ndDELAddressInformation" arguments="" htmlEscape="false"/></div>
															    	</td>
															    </tr>
														  	</table>
														</td>
													</tr>
											 		
											 		<tr>
											 			<td colspan="4">
											 				<div>
												 				<table cellpadding="0" cellspacing="6" width="100%">
														 			<tr>
															 			<td width="10%">&nbsp;</td>
															 			<td width="25%">
															 				<spring:message code="lts.acq.othersVoiceService.targetInstallationAddress" arguments="" htmlEscape="false"/> : 
															 			</td>
															 			<td colspan="2">
															 				<b>${sessionLtsAcqOrderCapture.addressRollout.fullAddress}</b>
															 			</td>
															 		</tr>
													 			</table>
												 			</div>
											 			</td>
											 		</tr>
											 		<c:if test="${!isChannelDS}">											 		
											 		<tr>
											 			<td colspan="4">
											 				<div>
												 				<table cellpadding="0" cellspacing="6" width="100%">
														 			<tr>
														 				<td width="10%">&nbsp;</td>
															 			<td width="25%">
															 				<spring:message code="lts.acq.othersVoiceService.externalRelocation" arguments="" htmlEscape="false"/> <span class="contenttext_red">*</span> : 
															 			</td>
															 			<td colspan="2">
															 				<form:radiobutton path="secondDelEr" value="true" /> <b><spring:message code="lts.acq.common.yes" htmlEscape="false"/></b>
															 				&nbsp;
															 				<form:radiobutton path="secondDelEr" value="false" /> <b><spring:message code="lts.acq.common.no" htmlEscape="false"/></b>
															 				&nbsp;&nbsp;<form:errors path="secondDelEr" cssClass="error"/>
															 			</td>
														 			</tr>
													 			</table>
												 			</div>
											 			</td>
											 		</tr>
											 		<tr>
											 			<td colspan="4">
													 		<div id="secondDelBaCaDiv" style="display: none;">
													 			<table cellpadding="0" cellspacing="6" width="100%">
														 			<tr>
															 			<td width="10%">&nbsp;</td>
															 			<td width="25%">
															 				BA & CA <span class="contenttext_red">*</span> :
															 			</td>
															 			<td colspan="2">
															 				<form:radiobutton path="secondDelBaCaChange" value="true" /> <b><spring:message code="lts.acq.othersVoiceService.sameAsNewIA" arguments="" htmlEscape="false"/></b>
															 				&nbsp;
															 				<form:radiobutton path="secondDelBaCaChange" value="false" /> <b><spring:message code="lts.acq.othersVoiceService.remainunchange" arguments="" htmlEscape="false"/></b>
															 				&nbsp;&nbsp;<form:errors path="secondDelBaCaChange" cssClass="error"/>
															 			</td>
														 			</tr>											 			
													 			</table>
													 		</div>
													 		<c:if test="${ltsAcqOtherVoiceServiceCmd.allowSecondDelConfirmSameAddress }">
																<div id="secondDelConfirmIaDiv" style="display: none;">
														 			<table cellpadding="0" cellspacing="6" width="100%">
															 			<tr>
																 			<td width="10%">&nbsp;</td>
																 			<td width="25%">
																 				<spring:message code="lts.acq.othersVoiceService.confirmSameIA" arguments="" htmlEscape="false"/> <span class="contenttext_red">*</span> :
																 			</td>
																 			<td colspan="2">
																 				<form:radiobutton path="secondDelConfirmSameIa" value="true" /> <b><spring:message code="lts.acq.common.yes" htmlEscape="false"/></b>
																 				&nbsp;
																 				<form:radiobutton path="secondDelConfirmSameIa" value="false" /> <b><spring:message code="lts.acq.common.no" htmlEscape="false"/></b>
																 				&nbsp;&nbsp;<form:errors path="secondDelConfirmSameIa" cssClass="error"/>
																 			</td>
															 			</tr>											 			
														 			</table>
														 		</div>
													 		</c:if>		 				
											 			</td>
											 		</tr>
											 		</c:if>										 		
											 	</table>
											</div>
										</c:if>	
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<c:if test="${not empty ltsAcqOtherVoiceServiceCmd.secondDelHotVasItemList || not empty ltsAcqOtherVoiceServiceCmd.secondDelOtherVasItemList}">
							<div id="2ndDelVasDiv" class="2ndDelDiv" style="display: none;">
								<table width="100%" cellspacing="0" border="0">
									<tr> 
								    	<td colspan="4">
								    		<div id="s_line_text"><spring:message code="lts.acq.othersVoiceService.2ndDELServicePlanNAdditionalVAS" arguments="" htmlEscape="false"/></div>
								    	</td>
								    </tr>
									<tr>
								    	<td colspan="4">
	     									<table width="100%" class="table_style_sb">	
	     										<c:if test="${not empty ltsAcqOtherVoiceServiceCmd.secondDelHotVasItemList}">			
											    	<tr>
														<th colspan="2" width="60%" align="left"></th>
														<th width="20%"><spring:message code="lts.acq.othersVoiceService.monthlyRateWithinCommitPeriod" arguments="" htmlEscape="false"/></th>
														<th width="20%"><spring:message code="lts.acq.othersVoiceService.monthToMonthRate" arguments="" htmlEscape="false"/></th>
													</tr>
													<c:forEach items="${ltsAcqOtherVoiceServiceCmd.secondDelHotVasItemList}" varStatus="status" var="secondDelHotVasItem">
														<tr>
															<td valign="top">
																<form:checkbox cssClass="secondDelHotVasItem" path="secondDelHotVasItemList[${status.index}].selected" disabled="${secondDelHotVasItem.mdoInd == 'M'}" />
															</td>
															<td valign="top" class="item_detail" width="58%">
																${secondDelHotVasItem.itemDisplayHtml}
																<c:if test="${not empty secondDelHotVasItem.itemAttbs }">
																	<c:forEach items="${secondDelHotVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
																		<form:input path="secondDelHotVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="secondDelHotVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
																				<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																					<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
																				</c:forEach>
																			</form:select>
																		</c:if>
																	</c:if>
																	</c:forEach>
																</c:if>
																<form:errors path="secondDelHotVasItemList[0].itemId" cssClass="error" />
															</td>
															<td align="center" valign="top" class="itemPrice">
																${secondDelHotVasItem.recurrentAmtTxt}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${secondDelHotVasItem.mthToMthAmtTxt}
															</td>									
														</tr>
													</c:forEach>							    
											    </c:if>		
											    <c:if test="${not empty ltsAcqOtherVoiceServiceCmd.new2ndDelAutoChangeTpList}">
													<c:forEach items="${ltsAcqOtherVoiceServiceCmd.new2ndDelAutoChangeTpList}" varStatus="status" var="new2ndDelAutoChangeTp">
														<tr class="new2ndDelAutoChangeTpList">
															<td valign="top">
																<form:checkbox cssClass="new2ndDelAutoChangeTp" path="new2ndDelAutoChangeTpList[${status.index}].selected" disabled="${new2ndDelAutoChangeTp.mdoInd == 'M'}" />
															</td>
															<td valign="top" class="item_detail new2ndDelAutoChangeTpDisplay" width="58%">
																${new2ndDelAutoChangeTp.itemDisplayHtml}
																<c:if test="${not empty new2ndDelAutoChangeTp.itemAttbs && new2ndDelAutoChangeTp.itemAttbs != null }">
																	<c:forEach items="${new2ndDelAutoChangeTp.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
																		<form:input path="new2ndDelAutoChangeTpList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="new2ndDelAutoChangeTpList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
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
																${new2ndDelAutoChangeTp.recurrentAmtTxt}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${new2ndDelAutoChangeTp.mthToMthAmtTxt}
															</td>									
														</tr>
													</c:forEach>							    
											    </c:if>											    
												<c:if test="${not empty ltsAcqOtherVoiceServiceCmd.secondDelOtherVasItemList}">
													<c:forEach items="${ltsAcqOtherVoiceServiceCmd.secondDelOtherVasItemList}" varStatus="status" var="secondDelOtherVasItem">
														<tr>
															<td valign="top">
																<form:checkbox cssClass="secondDelOtherVasItem" path="secondDelOtherVasItemList[${status.index}].selected" disabled="${secondDelOtherVasItem.mdoInd == 'M'}" />
															</td>
															<td valign="top" class="item_detail" width="58%">
																${secondDelOtherVasItem.itemDisplayHtml}
																<c:if test="${not empty secondDelOtherVasItem.itemAttbs }">
																	<c:forEach items="${secondDelOtherVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
																		<form:input path="secondDelOtherVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="secondDelOtherVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
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
																${secondDelOtherVasItem.recurrentAmtTxt}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${secondDelOtherVasItem.mthToMthAmtTxt}
															</td>									
														</tr>
													</c:forEach>							    
											    </c:if>
												<c:if test="${not empty ltsAcqOtherVoiceServiceCmd.secondDelStandaloneVasItemList}">
													<c:forEach items="${ltsAcqOtherVoiceServiceCmd.secondDelStandaloneVasItemList}" varStatus="status" var="secondDelStandaloneVasItem">
														<tr>
															<td valign="top">
																<form:checkbox cssClass="secondDelStandaloneVasItem" path="secondDelStandaloneVasItemList[${status.index}].selected" disabled="${secondDelStandaloneVasItem.mdoInd == 'M'}" />
															</td>
															<td valign="top" class="item_detail" width="58%">
																${secondDelStandaloneVasItem.itemDisplayHtml}
																<c:if test="${not empty secondDelStandaloneVasItem.itemAttbs }">
																	<c:forEach items="${secondDelStandaloneVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
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
																		<form:input path="secondDelStandaloneVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="secondDelStandaloneVasItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
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
																${secondDelStandaloneVasItem.recurrentAmtTxt}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${secondDelStandaloneVasItem.mthToMthAmtTxt}
															</td>									
														</tr>
													</c:forEach>							    
											    </c:if>					
	     									</table>
	     								</td>
	     							</tr>
								</table>
							</div>
						</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<c:if test="${not empty ltsAcqOtherVoiceServiceCmd.secondDelIddItemList}">
							<div id="2ndDelIddDiv" class="2ndDelDiv" style="display: none;">
								<table width="100%" cellspacing="0" border="0">
									<tr> 
								    	<td colspan="4">
								    		<div id="s_line_text"><spring:message code="lts.acq.othersVoiceService.2ndDELServicePlanNAdditionalVAS" arguments="" htmlEscape="false"/></div>
								    	</td>
								    </tr>
   								    <tr>
								    	<td colspan="4">
	     									<table width="100%" class="table_style_sb">							
										    	<tr>
													<th colspan="2" width="60%" align="left"></th>
													<th width="20%"><spring:message code="lts.acq.othersVoiceService.monthlyRateWithinCommitPeriod" arguments="" htmlEscape="false"/></th>
													<th width="20%"><spring:message code="lts.acq.othersVoiceService.monthToMonthRate" arguments="" htmlEscape="false"/></th>
												</tr>
												<c:forEach items="${ltsAcqOtherVoiceServiceCmd.secondDelIddItemList}" var="iddVasItem" varStatus="status">
													<tr>
														<td valign="top">
															<form:checkbox path="secondDelIddItemList[${status.index}].selected" cssClass="iddItem" disabled="${iddVasItem.mdoInd == 'M'}"/>
														</td>
														<td valign="top" width="58%" class="item_detail">
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
																				<form:input path="secondDelIddItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" maxlength="${itemAttb.maxLength}" />
																			</c:when>
																			<c:otherwise>
																				<form:input path="secondDelIddItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
																			</c:otherwise>
																		</c:choose>
																		<form:errors path="secondDelIddItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" cssClass="error" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="secondDelIddItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
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
															${iddVasItem.recurrentAmtTxt}
														</td>												
														<td align="center" valign="top" class="itemPrice">
															${iddVasItem.mthToMthAmtTxt}
														</td>
													</tr>	
												</c:forEach>
											</table>
										</td>
									</tr>
								</table>
							</div>
						</c:if>
					</td>
				</tr>
			
				<tr>
					<td colspan="4">
						<div id="2ndDelWqRemarkDiv" class="2ndDelDiv" style="display: none;">
							<br>
							<table width="100%" cellspacing="0" border="0">
								<tr> 
									<td colspan="4"><div id="s_line_text"><spring:message code="lts.acq.othersVoiceService.internalUseRemark2ndDELAddOrderAction" arguments="" htmlEscape="false"/></div></td>
							    </tr>
							    <tr>
							    	<td colspan="4">
										<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
											<tr>
												<td align="center" colspan="4" width="100%">
													<form:errors path="secondDelWqRemark" cssClass="error"/>
													<form:textarea path="secondDelWqRemark" rows="10" cols="120" cssStyle="resize:none;"></form:textarea>
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
					</td>
					</tr>
					<c:if test="${!sessionScope.sessionLtsAcqOrderCapture.channelPremier}">
					<tr>
						<td colspan="4">
							<div class="2ndDelDiv" id="info_profile" class="ui-widget" style="display: none;">
								<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="info_profile_msg" class="contenttext">
										<spring:message code="lts.acq.othersVoiceService.if1stDELARPULess99" arguments="" htmlEscape="false"/>
									</div>
									<p></p>
								</div>
							</div>
							
							<br>
						</td>
					</tr>
					</c:if>
			</table>
		</td>
	</tr>
</table>

</form:form>

<br>
<table width="100%" border="0" cellspacing="0">
	<tr>
		<td align="right"> 
			<c:if test="${sessionScope.sessionLtsAcqOrderCapture.secondDelServiceProfile != null
				&& not empty ltsAcqOtherVoiceServiceCmd.secondDelCancelVasItemList}" >
				<div class="func_button">
					<a href="#" id="cancelVasBtn"><spring:message code="lts.acq.othersVoiceService.cancelVAS" arguments="" htmlEscape="false"/></a>
				</div> 
			</c:if>
			<div class="button">
				<a href="#" id="submitBtn"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></a>
			</div> 
		</td>
	</tr>
</table>

</div>
<script type="text/javascript">

var cancelMsg = '<spring:message code="lts.acq.othersVoiceService.cancelSelectedService" arguments="" htmlEscape="false"/>';
var willBeRmMsg = '<spring:message code="lts.acq.othersVoiceService.willBeRemoved" arguments="" htmlEscape="false"/>';

$(ltsothervoiceservice.actionPerform);
$('a#submit').click(function(event) {
		event.preventDefault();
		$("#formAction").val('SUBMIT');
		$("form#ltsOtherVoiceServiceForm").submit();
});

</script>
<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>