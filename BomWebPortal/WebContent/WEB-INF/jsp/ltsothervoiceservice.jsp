<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltsothervoiceservice.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 3 : 5}" />
</jsp:include>



<form:form method="POST" id="ltsOtherVoiceServiceForm" name="ltsOtherVoiceServiceForm"  commandName="ltsOtherVoiceServiceCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="formAction" />
<form:hidden path="duplexProfile" />
<form:hidden path="secondDelDiffCust" />
<form:hidden path="secondDelDiffAddress" />
<form:hidden path="allowSecondDelConfirmSameAddress" />
<form:hidden path="secondDelProfileValid" />
<form:hidden path="secondDelMsg" />
<form:hidden path="showSecondDelRedeemPremium" />
<form:hidden path="secondDelOptOutIdd"/>
<form:hidden path="removeRenewalDuplex"/>
<form:hidden path="retainRenewalDuplex"/>
<input type="hidden" name="isRenewalOrder" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR'}" />


<div id="s_line_text"><spring:message code="lts.offerDtl.otherVoiSrv" text="Others Voice Service"/></div>

<table width="98%" align="center">
	<tr>
		<td>
			<div class="paper_w2 round_10">
				<table width="98%" align="center">
					<tr>
						<td>
							<br/>
							<div id="s_line_text"><spring:message code="lts.offerDtl.2ndDELSrv" text="2nd DEL Service"/></div>
				

			<table width="100%" border="0" cellspacing="6" class="contenttext">
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" align="right">
						<spring:message code="lts.offerDtl.mass" text="Create 2nd DEL Service (Mass)"/> &nbsp;&nbsp;&nbsp;&nbsp;  
						<br/>
						<spring:message code="lts.offerDtl.PT" text="Create DEL Retention Service (PT)"/> <span class="contenttext_red">*</span> : 
					</td>
					<td width="60%" colspan="3" align="left">
						<c:choose>
							<c:when test="${sessionScope.sessionLtsOrderCapture.ltsMiscellaneousForm.recontract 
														&& not empty sessionScope.sessionLtsOrderCapture.ltsRecontractForm
														&& ltsOtherVoiceServiceCmd.onlyAllowWorking}">
								<input type="radio" disabled="disabled" /><b><spring:message code="lts.offerDtl.yes" text="Yes"/></b>
							</c:when>
							<c:otherwise>
								<form:radiobutton path="create2ndDel" value="true"/><b><spring:message code="lts.offerDtl.yes" text="Yes"/></b>
							</c:otherwise>
						</c:choose>
						&nbsp;
						<form:radiobutton path="create2ndDel" value="false" /><b><spring:message code="lts.offerDtl.no" text="No"/></b>
						&nbsp;&nbsp;<form:errors path="create2ndDel" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<c:if test="${ltsOtherVoiceServiceCmd.duplexProfile}">
							<c:if test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBU' 
										|| (sessionScope.sessionLtsOrderCapture.orderType == 'SBR' && ltsOtherVoiceServiceCmd.removeRenewalDuplex)  }">
								<div id="duplexSrvDiv">
									<table width="100%" cellspacing="6">
										<tr>
											<td width="40%" align="right">
												DNX (${fn:substring(sessionLtsOrderCapture.ltsServiceProfile.srvNum,4,12)}<c:if test="${not empty ltsOtherVoiceServiceCmd.dnChangeNumX}">
												, New DN ${fn:substring(ltsOtherVoiceServiceCmd.dnChangeNumX,4,12)}
												</c:if>)<span class="contenttext_red">*</span> : 
												
												
											</td>
											<td width="65%" colspan="2" align="left">
												<form:radiobutton cssClass="duplexSrv" path="duplexXSrvType" value="NEW_2ND_DEL" id="duplexXSrvType_NEW_2DEL" disabled="true"/> <b><spring:message code="lts.offerDtl.chgNew2DEL" text="Change to new 2nd DEL"/></b>
												&nbsp;
												<c:choose>
													<c:when test="${ltsOtherVoiceServiceCmd.removeRenewalDuplex }">
														<form:radiobutton cssClass="duplexSrv" path="duplexXSrvType" value="RETAIN" id="duplexXSrvType_RETAIN" /> <b><spring:message code="lts.offerDtl.retain" text="Retain"/></b>
													</c:when>
													<c:otherwise>
														<form:radiobutton cssClass="duplexSrv" path="duplexXSrvType" value="UPGRADE" id="duplexXSrvType_UPGRADE" /> <b><spring:message code="lts.offerDtl.upgToEye" text="Upgrade to eye"/></b>	
													</c:otherwise>
												</c:choose>
												&nbsp;
												<form:radiobutton cssClass="duplexSrv" path="duplexXSrvType" value="CANCEL" id="duplexXSrvType_CANCEL" /> <b><spring:message code="lts.offerDtl.cxl" text="Cancel"/></b>
											</td>
										</tr>
										<tr>
											<td width="40%" align="right">
												DNY (${fn:substring(sessionLtsOrderCapture.ltsServiceProfile.duplexNum,4,12)}<c:if test="${not empty ltsOtherVoiceServiceCmd.dnChangeNumY}">
												, New DN ${fn:substring(ltsOtherVoiceServiceCmd.dnChangeNumY,4,12) }
												</c:if>)<span class="contenttext_red">*</span> :
											</td>
											<td colspan="2" align="left">
												<form:radiobutton cssClass="duplexSrv" path="duplexYSrvType" value="NEW_2ND_DEL" id="duplexYSrvType_NEW_2DEL" disabled="true"/> <b><spring:message code="lts.offerDtl.chgNew2DEL" text="Change to new 2nd DEL"/></b>
												&nbsp;
												<c:choose>
													<c:when test="${ltsOtherVoiceServiceCmd.removeRenewalDuplex }">
														<form:radiobutton cssClass="duplexSrv" path="duplexYSrvType" value="RETAIN" id="duplexYSrvType_RETAIN" /> <b><spring:message code="lts.offerDtl.retain" text="Retain"/></b>
													</c:when>
													<c:otherwise>
														<form:radiobutton cssClass="duplexSrv" path="duplexYSrvType" value="UPGRADE" id="duplexYSrvType_UPGRADE" /> <b><spring:message code="lts.offerDtl.upgToEye" text="Upgrade to eye"/></b>	
													</c:otherwise>
												</c:choose>
												&nbsp;
												<form:radiobutton cssClass="duplexSrv" path="duplexYSrvType" value="CANCEL" id="duplexYSrvType_CANCEL" /> <b><spring:message code="lts.offerDtl.cxl" text="Cancel"/></b>
											</td>
										</tr>
										<tr>
											<td>&nbsp;
											</td>
											<td colspan="2" align="left">
												<form:errors path="duplexXSrvType" cssClass="error"/></br>
												<form:errors path="duplexDnChange" cssClass="error"/>
											</td>
										</tr>
									</table>
								</div>
							</c:if>						
						</c:if>
	
						<div id="new2ndDelDiv" style="display: none;">
							<table width="100%" cellpadding="2" cellspacing="6">
								<tr>
									<td width="40%" align="right"><spring:message code="lts.offerDtl.new2ndSrvNum" text="New 2nd Service Number"/><span class="contenttext_red">*</span> : </td>
									<td width="60%" colspan="2" align="left">
										<form:select path="new2ndDelSrvStatus" cssClass="new2ndDel searchCriteria">
											<c:if test="${!sessionScope.sessionLtsOrderCapture.ltsMiscellaneousForm.recontract &&
													empty sessionScope.sessionLtsOrderCapture.ltsRecontractForm}">
												<form:option value="20"><spring:message code="lts.offerDtl.working" text="Working"/></form:option>
											</c:if>
											<c:if test="${!ltsOtherVoiceServiceCmd.onlyAllowWorking}">
												<form:option value="70"><spring:message code="lts.offerDtl.reserved" text="Reserved"/></form:option>
												<form:option value="08"><spring:message code="lts.offerDtl.dnPool" text="DN Pool"/></form:option>
											</c:if>
										</form:select>
										&nbsp;
										<form:input path="new2ndDelDn" cssClass="new2ndDel searchCriteria"/>
										 &nbsp; 
										 <div class="func_button">
										 	<a href="#" id="searchDn" class="new2ndDel searchCriteria"><spring:message code="lts.offerDtl.search" text="Search"/></a>
										 </div>
										 <div class="func_button">
										 	<a href="#" id="clearDn" class="new2ndDel"><spring:message code="lts.offerDtl.clear" text="Clear"/></a>
										 </div>
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
										<c:if test="${not empty ltsOtherVoiceServiceCmd.secondDelMsg}">
											 <c:choose>
											 	<c:when test="${ltsOtherVoiceServiceCmd.secondDelProfileValid}">
												 	<div id="info_profile" class="ui-widget" style="visibility: visible;">
														<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
															<p>
																<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
															</p>
															<div id="info_profile_msg" class="contenttext">
																${ltsOtherVoiceServiceCmd.secondDelMsg}
															</div>
															<p></p>
														</div>
													</div>
											 	</c:when>
											 	<c:otherwise>
											 		<div id="error_profile" class="ui-widget" style="visibility: visible;">
														<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
															<p>
																<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
															</p>
															<div id="error_profile_msg" class="contenttext">
																${ltsOtherVoiceServiceCmd.secondDelMsg}
															</div>
															<p></p>
														</div>
													</div>
											 	</c:otherwise>
											 </c:choose>
										</c:if>								
									</td>
									<td> &nbsp;</td>
								</tr>
								<tr>
									<td colspan="4">
									
										<c:if test="${ltsOtherVoiceServiceCmd.secondDelProfileValid && sessionScope.sessionLtsOrderCapture.secondDelServiceProfile != null}">
											<c:set var="secDelServiceProfile" value="${sessionScope.sessionLtsOrderCapture.secondDelServiceProfile}" />
											<div id="secondDelServiceOverviewDiv">
												<table width="100%" cellspacing="4" cellpadding="6">
													<tr>
														<td colspan="4" align="left">
															<table width="100%" cellspacing="0" border="0" bgcolor="white">
																<tr> 
																	<td colspan="4" >
																		<div id="s_line_text"><spring:message code="lts.offerDtl.2DELSrvOverview" text="2nd DEL Service Overview"/></div>
															    	</td>
															    </tr>
														  	</table>
														</td>
													</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.offerDtl.famTitle" text="Family Title"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.primaryCust.title}" />
											 				 </span>
											 			</td>
											 		</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.offerDtl.custName" text="Customer Name"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.primaryCust.lastName}"/>&nbsp;<c:out value="${secDelServiceProfile.primaryCust.firstName}"/>
											 				 </span>
											 			</td>
											 		</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.offerDtl.ia" text="Installation Address"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.address.fullAddress}" />
											 				 </span>
											 			</td>
											 		</tr>
											 		<tr>
														<td width="10%">&nbsp;</td>
														<td width="25%"><spring:message code="lts.offerDtl.idVerifyInd" text="ID Verified Ind"/> :</td>
														<td colspan="2">
															<span class="bold">
																<c:out value="${secDelServiceProfile.primaryCust.idVerifyInd ? 'Y' : 'N'}" />
															</span>
														</td>
													</tr>	
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.offerDtl.tariff" text="Tariff"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.tariff == 'R' ? 'Residential' : 'Business'}"/>
											 				 </span>
											 			</td>
											 		</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.offerDtl.srvType" text="Service Type"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
																<c:out value="${secDelServiceProfile.srvType }" /> / <c:out value="${secDelServiceProfile.datCd }" />
															</span> 
											 			</td>
											 		</tr>
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.offerDtl.dupRing" text="Duplex Ringing"/> :</td>
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
											 			<td width="25%"><spring:message code="lts.offerDtl.srvLineSts" text="Service Line Status"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.inventStsDesc}" />
											 				 </span>
											 			</td>
											 		</tr>	
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.offerDtl.wip" text="WIP"/> :</td>
											 			<td colspan="2">
											 				 <span class="bold">
											 				 	<c:out value="${secDelServiceProfile.primaryCust.wipInd}" />
											 				 </span>
											 			</td>
											 		</tr>	
											 		<tr>
											 			<td width="10%">&nbsp;</td>
											 			<td width="25%"><spring:message code="lts.offerDtl.wipMsg" text="WIP Reminder Message"/> :</td>
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
			    <td><div id="s_line_text"><spring:message code="lts.offerDtl.numSelect" text="Number Selection (New Service Number)"/></div></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		<tr><td width="50%" valign="top">	
			<table border="0" cellspacing="4" cellpadding="6" class="contenttext">
				<tr>
				    <td width="10%"></td>
					<td width="19%"><div align="left"><b><form:radiobutton path="searchMethodRadio" value="noCriteriaRadio"/> <spring:message code="lts.offerDtl.noCrit" text="No Criteria"/></b></div></td>
					<td width="22%"></td>				
				</tr>
				<tr>
				    <td></td>
				    <td><div align="left"><b><form:radiobutton path="searchMethodRadio" value="first5to8Radio"/> <spring:message code="lts.offerDtl.prefFirst58" text="Preferred First 5-8 Digits"/>: </b></div></td>
	                <td><div align="left"><form:input maxlength="8" path="first5To8Digits" /></div></td> 
				</tr>				
				<tr>
				    <td></td>
				    <td><div align="left"><b><form:radiobutton path="searchMethodRadio" value="last1to3Radio"/> <spring:message code="lts.offerDtl.prefLast13" text="Preferred Last 1-3 Digits"/>: </b></div></td>
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
                    <a id="searchDnPool" style="color:white" href="#"><spring:message code="lts.offerDtl.search" text="Search"/></a>
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
				<td width="100%"><div align="right"><b><spring:message code="lts.offerDtl.selectNum" text="Please Select a Number"/> : </b></div></td>
			</tr>			
			<tr>
			    <td align="right">
			    <div class="func_button" align="right">
                    <a id="lockBtn" style="color:white" href="#"><spring:message code="lts.offerDtl.lock" text="Lock"/></a>
                </div>
			    </td>
			</tr>
			</table>
			</td>
			<td width="20%" valign="top">			
			<c:if test="${not empty ltsOtherVoiceServiceCmd.numSelectionList}">
			<c:forEach items="${ltsOtherVoiceServiceCmd.numSelectionList}" var="itemList" varStatus="itemListStatus">
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
														    		<div id="s_line_text"><spring:message code="lts.offerDtl.2DELCustVerify" text="2nd DEL Customer Verification"/></div>
														    	</td>
														    	
														    </tr>
													  	</table>
													</td>
												</tr>
																	 	
										 		<tr>
										 			<td width="10%">&nbsp;</td>
										 			<td width="25%">
										 				<spring:message code="lts.offerDtl.docTypeAndNum" text="Document Type & Number"/><span class="contenttext_red">*</span> : 
										 			</td>
										 			<td colspan="2">
										 				<form:select path="secondDelDocType">
										 					<form:option value="HKID"><spring:message code="lts.offerDtl.HKID" text="HKID"/></form:option>
										 					<form:option value="PASS"><spring:message code="lts.offerDtl.PASS" text="Passport"/></form:option>
										 				</form:select>
										 				&nbsp;
										 				<form:input path="secondDelDocNum" />
										 				<form:errors path="secondDelDocNum" cssClass="error" /> 
										 			</td>
										 		</tr>										 		
										 		<tr>
										 			<td width="10%">&nbsp;</td>
										 			<td width="25%">
										 				<spring:message code="lts.offerDtl.dummyDoc" text="Dummy Document"/><span class="contenttext_red">*</span> : 
										 			</td>
										 			<td colspan="2">
										 				<form:radiobutton path="secondDelDummyDoc" value="true" /> <b><spring:message code="lts.offerDtl.yes" text="Yes"/></b>
										 				&nbsp;
										 				<form:radiobutton path="secondDelDummyDoc" value="false" /> <b><spring:message code="lts.offerDtl.no" text="No"/></b>
										 				&nbsp;&nbsp;<form:errors path="secondDelDummyDoc" cssClass="error" />
										 			</td>
										 		</tr>
										 		<tr>
										 			<td width="10%">&nbsp;</td>
										 			<td width="25%">
										 				<spring:message code="lts.offerDtl.3rdApp" text="Third Party Application"/><span class="contenttext_red">*</span> : 
										 			</td>
										 			<td colspan="2">
										 				<form:radiobutton path="secondDelThirdPartyAppl" value="true" /> <b><spring:message code="lts.offerDtl.yes" text="Yes"/></b>
										 				&nbsp;
										 				<form:radiobutton path="secondDelThirdPartyAppl" value="false" /> <b><spring:message code="lts.offerDtl.no" text="No"/></b>
										 				&nbsp;&nbsp;<form:errors path="secondDelThirdPartyAppl" cssClass="error" />
										 			</td>
										 		</tr>
										 		<tr>
										 			<td width="10%">&nbsp;</td>
										 			<td width="25%">&nbsp;</td>
										 			<td width="20%">&nbsp;</td>
										 			<td>
										 				<div class="func_button"> 
										 					<a href="#" id="confirmCust"><spring:message code="lts.offerDtl.verify" text="Verify"/></a>
										 				</div>
										 			</td>
										 		</tr>
										 	</table>
										</div>
										
										<c:if test="${ltsOtherVoiceServiceCmd.showSecondDelRedeemPremium}">
											 <div id="secondDelRedeemPremiumDiv">
											 	<table width="100%" cellpadding="5" cellspacing="6">
											 		<tr>
														<td colspan="4" align="left">
															<table width="100%" cellspacing="0" border="0" bgcolor="white">
																<tr> 
															    	<td colspan="4">
															    		<div id="s_line_text"><spring:message code="lts.offerDtl.2DELMis" text="2nd DEL Miscellaneous"/></div>
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
																		<td width="25%"><spring:message code="lts.offerDtl.redeemPrevPrem" text="Redeem Previous Premium"/><span class="contenttext_red">*</span> : </td>
																		<td width="65%" colspan="2" align="left">
																			<form:radiobutton path="secondDelRedeemPremium" value="true" /> <b><spring:message code="lts.offerDtl.yes" text="Yes"/></b>
																			&nbsp;
																			<form:radiobutton path="secondDelRedeemPremium" value="false" /> <b><spring:message code="lts.offerDtl.no" text="No"/></b>
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
										
										<c:if test="${ltsOtherVoiceServiceCmd.secondDelDiffAddress}">
											 <div id="secondDelDiffAddressDiv">
											 	<table width="100%" cellpadding="5" cellspacing="6">
											 		<tr>
														<td colspan="4" align="left">
															<table width="100%" cellspacing="0" border="0" bgcolor="white">
																<tr> 
															    	<td colspan="4">
															    		<div id="s_line_text"><spring:message code="lts.offerDtl.2DELAddrInfo" text="2nd DEL Address Information"/></div>
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
															 				<spring:message code="lts.offerDtl.targetIA" text="Target Installation Address"/> : 
															 			</td>
															 			<td colspan="2">
															 				<b>${sessionLtsOrderCapture.newAddressRollout.fullAddress}</b>
															 			</td>
															 		</tr>
													 			</table>
												 			</div>
											 			</td>
											 		</tr>
											 		<tr>
											 			<td colspan="4">
											 				<div>
												 				<table cellpadding="0" cellspacing="6" width="100%">
														 			<tr>
														 				<td width="10%">&nbsp;</td>
															 			<td width="25%">
															 				<spring:message code="lts.offerDtl.er" text="External Relocation"/> <span class="contenttext_red">*</span> : 
															 			</td>
															 			<td colspan="2">
															 				<form:radiobutton path="secondDelEr" value="true" /> <b><spring:message code="lts.offerDtl.yes" text="Yes"/></b>
															 				&nbsp;
															 				<form:radiobutton path="secondDelEr" value="false" /> <b><spring:message code="lts.offerDtl.no" text="No"/></b>
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
															 				<spring:message code="lts.offerDtl.baca" text="BA & CA"/> <span class="contenttext_red">*</span> :
															 			</td>
															 			<td colspan="2">
															 				<form:radiobutton path="secondDelBaCaChange" value="true" /> <b><spring:message code="lts.offerDtl.sameAsNewIA" text="Same as new I/A"/></b>
															 				&nbsp;
															 				<form:radiobutton path="secondDelBaCaChange" value="false" /> <b><spring:message code="lts.offerDtl.unchange" text="Remain unchange"/></b>
															 				&nbsp;&nbsp;<form:errors path="secondDelBaCaChange" cssClass="error"/>
															 			</td>
														 			</tr>											 			
													 			</table>
													 		</div>
													 		<c:if test="${ltsOtherVoiceServiceCmd.allowSecondDelConfirmSameAddress }">
																<div id="secondDelConfirmIaDiv" style="display: none;">
														 			<table cellpadding="0" cellspacing="6" width="100%">
															 			<tr>
																 			<td width="10%">&nbsp;</td>
																 			<td width="25%">
																 				<spring:message code="lts.offerDtl.confirmSameIA" text="Confirm Same I/A"/> <span class="contenttext_red">*</span> :
																 			</td>
																 			<td colspan="2">
																 				<form:radiobutton path="secondDelConfirmSameIa" value="true" /> <b><spring:message code="lts.offerDtl.yes" text="Yes"/></b>
																 				&nbsp;
																 				<form:radiobutton path="secondDelConfirmSameIa" value="false" /> <b><spring:message code="lts.offerDtl.no" text="No"/></b>
																 				&nbsp;&nbsp;<form:errors path="secondDelConfirmSameIa" cssClass="error"/>
																 			</td>
															 			</tr>											 			
														 			</table>
														 		</div>
													 		</c:if>		 				
											 			</td>
											 		</tr>										 		
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
						<c:if test="${not empty ltsOtherVoiceServiceCmd.secondDelHotVasItemList || not empty ltsOtherVoiceServiceCmd.secondDelOtherVasItemList}">
							<div id="2ndDelVasDiv" style="display: none;">
								<table width="100%" cellspacing="0" border="0">
									<tr> 
								    	<td colspan="4">
								    		<div id="s_line_text"><spring:message code="lts.offerDtl.2DELSrvPlanAddVAS" text="2nd DEL Service Plan & Additional VAS"/></div>
								    	</td>
								    </tr>
								    <tr>
								    	<td colspan="4">
	     									<table width="100%" class="table_style_sb">							
										    	<tr>
													<th colspan="2" width="60%" align="left">
														<c:if test="${not empty ltsOtherVoiceServiceCmd.secondDelHotVasItemList}">
															<form:errors path="secondDelHotVasItemList[0].itemId" cssClass="error"/>	
														</c:if>
													</th>
													<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
													<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
												</tr>
												<c:if test="${not empty ltsOtherVoiceServiceCmd.secondDelHotVasItemList}">
													<c:forEach items="${ltsOtherVoiceServiceCmd.secondDelHotVasItemList}" varStatus="status" var="secondDelHotVasItem">
														<tr>
															<td valign="top">
																<form:checkbox cssClass="secondDelHotVasItem" path="secondDelHotVasItemList[${status.index}].selected" disabled="${secondDelHotVasItem.mdoInd == 'M'}" />
															</td>
															<td valign="top" class="item_detail" width="58%">
																${secondDelHotVasItem.itemDisplayHtml}
																<c:if test="${not empty secondDelHotVasItem.itemAttbs }">
																	<c:forEach items="${secondDelHotVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
																	<br>
																	${itemAttb.attbDesc}
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
												<c:if test="${not empty ltsOtherVoiceServiceCmd.duplex2ndDelAutoInVasList}">
													<c:forEach items="${ltsOtherVoiceServiceCmd.duplex2ndDelAutoInVasList}" varStatus="status" var="duplex2ndDelAutoInVas">
														<tr class="duplex2ndDelAutoInVasList">
															<td valign="top">
																<form:checkbox cssClass="duplex2ndDelAutoInVas" path="duplex2ndDelAutoInVasList[${status.index}].selected" disabled="${duplex2ndDelAutoInVas.mdoInd == 'M'}" />
															</td>
															<td valign="top" class="item_detail duplex2ndDelAutoInVasDisplay" width="58%">
																${duplex2ndDelAutoInVas.itemDisplayHtml}
																<c:if test="${not empty duplex2ndDelAutoInVas.itemAttbs && duplex2ndDelAutoInVas.itemAttbs != null}">
																	<c:forEach items="${duplex2ndDelAutoInVas.itemAttbs }" var="itemAttb" varStatus="attbStatus">
																	<br>
																	${itemAttb.attbDesc}
																	<c:if test="${itemAttb.inputMethod == 'INPUT'}">
																		<form:input path="duplex2ndDelAutoInVasList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
																	</c:if>
																	
																	<c:if test="${itemAttb.inputMethod == 'SELECT'}">
																		<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
																			<form:select path="duplex2ndDelAutoInVasList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
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
																${duplex2ndDelAutoInVas.recurrentAmtTxt}
															</td>
															<td align="center" valign="top" class="itemPrice">
																${duplex2ndDelAutoInVas.mthToMthAmtTxt}
															</td>									
														</tr>
													</c:forEach>							    
											    </c:if>
												<c:if test="${not empty ltsOtherVoiceServiceCmd.new2ndDelAutoChangeTpList}">
													<c:forEach items="${ltsOtherVoiceServiceCmd.new2ndDelAutoChangeTpList}" varStatus="status" var="new2ndDelAutoChangeTp">
														<tr class="new2ndDelAutoChangeTpList">
															<td valign="top">
																<form:checkbox cssClass="new2ndDelAutoChangeTp" path="new2ndDelAutoChangeTpList[${status.index}].selected" disabled="${new2ndDelAutoChangeTp.mdoInd == 'M'}" />
															</td>
															<td valign="top" class="item_detail new2ndDelAutoChangeTpDisplay" width="58%">
																${new2ndDelAutoChangeTp.itemDisplayHtml}
																<c:if test="${not empty new2ndDelAutoChangeTp.itemAttbs && new2ndDelAutoChangeTp.itemAttbs != null }">
																	<c:forEach items="${new2ndDelAutoChangeTp.itemAttbs }" var="itemAttb" varStatus="attbStatus">
																	<br>
																	${itemAttb.attbDesc}
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
												<c:if test="${not empty ltsOtherVoiceServiceCmd.secondDelOtherVasItemList}">
													<c:forEach items="${ltsOtherVoiceServiceCmd.secondDelOtherVasItemList}" varStatus="status" var="secondDelOtherVasItem">
														<tr>
															<td valign="top">
																<form:checkbox cssClass="secondDelOtherVasItem" path="secondDelOtherVasItemList[${status.index}].selected" disabled="${secondDelOtherVasItem.mdoInd == 'M'}" />
															</td>
															<td valign="top" class="item_detail" width="58%">
																${secondDelOtherVasItem.itemDisplayHtml}
																<c:if test="${not empty secondDelOtherVasItem.itemAttbs }">
																	<c:forEach items="${secondDelOtherVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
																		<br>
																	${itemAttb.attbDesc}
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
												<c:if test="${not empty ltsOtherVoiceServiceCmd.secondDelStandaloneVasItemList}">
													<c:forEach items="${ltsOtherVoiceServiceCmd.secondDelStandaloneVasItemList}" varStatus="status" var="secondDelStandaloneVasItem">
														<tr>
															<td valign="top">
																<form:checkbox cssClass="secondDelStandaloneVasItem" path="secondDelStandaloneVasItemList[${status.index}].selected" disabled="${secondDelStandaloneVasItem.mdoInd == 'M'}" />
															</td>
															<td valign="top" class="item_detail" width="58%">
																${secondDelStandaloneVasItem.itemDisplayHtml}
																<c:if test="${not empty secondDelStandaloneVasItem.itemAttbs }">
																	<c:forEach items="${secondDelStandaloneVasItem.itemAttbs }" var="itemAttb" varStatus="attbStatus">
																		<br>
																	${itemAttb.attbDesc}
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
							<c:if test="${not empty ltsOtherVoiceServiceCmd.secondDelIddItemList}">
								<div id="2ndDelIddDiv" style="display: none;">
									<table width="100%" cellspacing="0" border="0">
										<tr> 
									    	<td colspan="4">
									    		<div id="s_line_text"><spring:message code="lts.offerDtl.2DELSrvPlanAddVAS" text="2nd DEL Service Plan & Additional VAS"/></div>
									    	</td>
									    </tr>
    								    <tr>
									    	<td colspan="4">
		     									<table width="100%" class="table_style_sb">							
											    	<tr>
														<th colspan="2" width="60%" align="left"></th>
														<th width="20%"><spring:message code="lts.offerDtl.mnhFixTermRate" text="Monthly Fixed Term Rate"/></th>
														<th width="20%"><spring:message code="lts.offerDtl.mnhToMnhRate" text="Month-To-Month Rate"/></th>
													</tr>
													<c:forEach items="${ltsOtherVoiceServiceCmd.secondDelIddItemList}" var="iddVasItem" varStatus="status">
														<tr>
															<td valign="top">
																<form:checkbox path="secondDelIddItemList[${status.index}].selected" cssClass="iddItem" disabled="${iddVasItem.mdoInd == 'M'}"/>
															</td>
															<td valign="top" width="58%" class="item_detail">
																${iddVasItem.itemDisplayHtml}
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
						<div id="2ndDelWqRemarkDiv" style="display: none;">
							<br>
							<table width="100%" cellspacing="0" border="0">
								<tr> 
									<td colspan="4"><div id="s_line_text"><spring:message code="lts.offerDtl.internalRmk2DEL" text="Internal Use Remark / 2nd DEL Additional Order Action"/></div></td>
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
				</table>
				</td>
				</tr>
				</table>
				</div>
			</td>
		</tr>
	</table>
	
	
<c:if test="${sessionScope.sessionLtsOrderCapture.secondDelServiceProfile != null
				&& not empty ltsOtherVoiceServiceCmd.secondDelCancelVasItemList}">
	<br>
	<div id="cancelVasDiv" style="display: none;">	
		<table class="tablegrey" width="100%">
			<tr>
				<td bgcolor="#FFFFFF">
					<table width="100%" cellspacing="0" border="0" bgcolor="white">
						<tr> 
                  			<td colspan="3">
                  				<div id="s_line_text"><spring:message code="lts.offerDtl.cxlExistVas" text="Cancel Existing VAS"/></div>
                  			</td>
           				</tr>
       					<tr>
							<td colspan="3">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="3">
								
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
									</tr>
									<c:forEach items="${ltsOtherVoiceServiceCmd.secondDelCancelVasItemList}" var="profileVas" varStatus="status"> 
										<c:choose>
								            <c:when test="${status.count % 2 != 0}">
								                <c:set var="rowColor" value="#FFFFFF"  />
								            </c:when>
								            <c:otherwise>
								                <c:set var="rowColor" value="#C3FDB8"  />
								            </c:otherwise>
										</c:choose>
										<c:if test="${ profileVas.itemDetail != null }">
											<tr align="center" bgcolor='<c:out value="${rowColor}" />'>
												<td>
													<form:checkbox path="secondDelCancelVasItemList[${status.index}].itemDetail.selected" />
												</td>
												<td>${profileVas.itemDetail.itemDisplayHtml}</td>
												<td><c:out value="${profileVas.conditionEffStartDate}" default="-"/></td>
												<td><c:out value="${profileVas.conditionEffEndDate}" default="-"/></td>
												<td><c:out value="${profileVas.promotionStartDate}" default="-" /></td>
												<td><c:out value="${profileVas.promotionEndDate}" default="-" /></td>
												<td>$<c:out value="${profileVas.paidVas ? profileVas.itemDetail.grossEffPrice : '0'}" default="-" /></td>
												<td>$<c:out value="${profileVas.paidVas ? profileVas.itemDetail.netEffPrice : '0'}" default="-" /></td>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>					
		</table>
	</div>
</c:if>
	
</form:form>

<br>

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
			<c:if test="${sessionScope.sessionLtsOrderCapture.secondDelServiceProfile != null
				&& not empty ltsOtherVoiceServiceCmd.secondDelCancelVasItemList}" >
				<div class="func_button">
					<a href="#" id="cancelVasBtn"><spring:message code="lts.offerDtl.cxlVas" text="Cancel VAS"/></a>
				</div> 
			</c:if>
			<div class="button">
				<a href="#" id="submitBtn"><spring:message code="lts.mis.next" text="Next"/></a>
			</div> 
		</td>
	</tr>
</table>


<script language="javascript">
	$(ltsothervoiceservice.actionPerform);
</script>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>