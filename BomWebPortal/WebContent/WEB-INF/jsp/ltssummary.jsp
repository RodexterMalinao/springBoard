<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<%@ page contentType="text/html;charset=UTF-8"%>

<c:set var="enquiry" value="${sessionScope.sessionLtsOrderCapture.orderAction}" />

<c:set var="isStandaloneVasOrder" value="${sessionScope.sessionLtsOrderCapture.orderSubType == 'SB_STANDALONE_VAS'}" />

<c:if test="${enquiry != 'ENQUIRY' && enquiry != 'ENQUIRY_N_CANCEL'}">
	<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
	<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp">
		<jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 10 : 11}" />
	</jsp:include>
</c:if>
<c:if test="${enquiry == 'ENQUIRY_N_CANCEL'}">
	<script type="text/javascript" src="js/jquery.blockUI.js"></script>
</c:if>

<div class="cosContent">

<form:form id="summary" method="POST" commandName="serviceSummary">
<form:hidden path="orderGeneratePenalty" />
<input type="hidden" id="isSignoffStarted" value="N" />
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<div id="s_line_text"><spring:message code="lts.summary.ordSummary" text="Order Summary"/></div>

<table width="100%" class="paper_w2" style="border-collapse: collapse; border-width: 1px; padding: 3px;" border="1" >
<tr>
<td width="100%">
	<table width="100%" border="0">
		<tr>
			<td>
				<div id="summary_tabs">
				<table width="100%" border="0" cellspacing="0">
					<c:if test="${enquiry != 'ENQUIRY' && enquiry != 'ENQUIRY_N_CANCEL'}">
						<tr>
							<td><span class="title_a"><spring:message code="lts.summary.reviewMSg" text="Please review your order"/>:</span></td>
						</tr>
						<tr>
							<td><span class="title_b"><spring:message code="lts.summary.ordNo" text="Order no."/>:  ${serviceSummary.orderId}</span></td>
						</tr>
					</c:if>
					<c:if test="${enquiry == 'ENQUIRY' || enquiry == 'ENQUIRY_N_CANCEL'}">
						<tr>
							<td class="title_a" >
								The order number is ${serviceSummary.orderId}<BR>
								<c:forEach items="${serviceSummary.statusHistList}" var="statusHist">
									<c:if test="${empty statusHist.reasonCd}">	
										Order ${statusHist.sbStatus} on ${statusHist.statusDate}<BR>
									</c:if>
									<c:if test="${not empty statusHist.reasonCd}">	
								      <c:choose>
									   <c:when test="${statusHist.sbStatus == 'cancel' && statusHist.reasonCd == 'Manual Cancellation'}">
									       Order ${statusHist.sbStatus} on ${statusHist.statusDate} with reason ${statusHist.reasonCd} by ${statusHist.lastUpdBy}<BR>
									   </c:when>
									   <c:otherwise>
									       Order ${statusHist.sbStatus} on ${statusHist.statusDate} with reason ${statusHist.reasonCd}<BR>
									   </c:otherwise>
									  </c:choose>
									</c:if>
									
								</c:forEach>
							</td>
							<td class="contenttextgary"></td>
						</tr>
						
					</c:if>
				 <c:if test="${not empty sessionScope.emailResult && sessionScope.emailResult != null && sessionScope.emailResult != 'null'}">
					<tr>
						<td>
							<div id="error_div" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_msg" class="contenttext">
										<c:out value="${sessionScope.emailResult}"></c:out>			
									</div>
									<p></p>
								</div>
							</div>
							
							<c:set var="emailResult" scope="session" value="" />
						</td>
						<td class="contenttextgary"></td>
					</tr>
					</c:if>
					<c:if test="${not empty requestScope.errorMsgList || not empty param.errorMsg}">
					<tr>
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
									<c:if test="${not empty param.errorMsg}">
										<p>
											<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
										</p>
										<div id="error_msg" class="contenttext">
											${param.errorMsg}
										</div>
										<p></p>										
									</c:if>
								</div>
							</div>
						</td>
						<td width="5%">&nbsp;</td>
					</tr>
					</c:if>
				</table> 
				<c:forEach items="${serviceSummary.serviceSummaryList}" var="Summary">
					<c:if test="${Summary.srvType == 'EYE'}">
						<c:choose>
							<c:when test="${serviceSummary.onlineAccqOrder }">
								<c:set var="servicetype" ><spring:message code="lts.summary.newEyeService" text="New eye Service"/></c:set>
							</c:when>
							<c:when test="${isStandaloneVasOrder}">
								<c:set var="servicetype"><spring:message code="lts.summary.subOfVASSrv" text="Subscription of Values Added Service"/></c:set>
							</c:when>
							<c:when test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR'}">
								<c:set var="servicetype"><spring:message code="lts.summary.renewalEyeSrv" text="Renewal of eye Service"/></c:set>
							</c:when>
							<c:otherwise>
								<c:set var="servicetype"><spring:message code="lts.summary.upgradeEyeSrv" text="Upgrade eye Service"/></c:set>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${Summary.srvType == 'DEL'}">
						<c:choose>
							<c:when test="${serviceSummary.onlineAccqOrder }">
								<c:set var="servicetype"><spring:message code="lts.summary.newFLSrv" text="New Fixed Line Service"/></c:set>
							</c:when>
							<c:when test="${isStandaloneVasOrder}">
								<c:set var="servicetype"><spring:message code="lts.summary.subOfVASSrv" text="Subscription of Values Added Service"/></c:set>
							</c:when>
							<c:when test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR'}">
								<c:set var="servicetype"><spring:message code="lts.summary.renewalFLSrv" text="Renewal of Fixed Line Service"/></c:set>	
							</c:when>
						</c:choose>
					</c:if>
					<c:if test="${Summary.srvType == '2DEL'}">
						<c:set var="servicetype" value="Summary for 2nd DEL" />
					</c:if>					
					<table width="100%" border="0" cellspacing="0">
						<tr>
							<td id="line_text" class="contenttextgreen" height="40" valign="bottom">${servicetype}</td>
							<td class="contenttextgary"></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						class="contenttext">
						<tr></tr>
						<tr>
							<td colspan="4" class="table_title" id="s_line_text" ><spring:message code="lts.summary.custDtls" text="CUSTOMER DETAILS"/></td>
						</tr>		
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.docTypeId" text="Document Type / ID"/>: </b></td>
							<c:choose>
  								<c:when test="${Summary.recontractInd == 'Y'}">
  									<td colspan="2" class="">${Summary.transfereeDocType}: ${Summary.transfereeDocNum}</td>
  								</c:when>
  								<c:otherwise>
  									<td colspan="2" class="">${Summary.docType}: ${Summary.docNum}</td>
								</c:otherwise>
  							</c:choose>
						</tr>
						<c:choose>
  							<c:when test="${Summary.recontractInd == 'Y'}">
  								<c:if test="${not empty Summary.transfereeTitle}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td><b><spring:message code="lts.summary.familyTitle" text="Family Title"/>: </b></td>
										<td colspan="2" class="">${Summary.title}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.transfereeName}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.custName" text="Customer Name"/>: </b></td>
										<td colspan="2" class="">${Summary.transfereeName}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.transfereeCompanyName}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.companyName" text="Company Name"/>: </b></td>
										<td colspan="2" class="">${Summary.transfereeCompanyName}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.transfereeBlackListInd}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.blacklistCust" text="Blacklist Customer"/>: </b></td>
										<td colspan="2" class="">${Summary.transfereeBlackListInd}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.transfereeContactNum}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.custContactNo" text="Customer Contact No."/>: </b></td>
										<td colspan="2" class="">${Summary.transfereeContactNum}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.transfereeEmail}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.custContactEmail" text="Customer Contact Email"/>: </b></td>
										<td colspan="2" class="">${Summary.transfereeEmail}</td>
									</tr>
								</c:if>								
  							</c:when>
  							<c:otherwise>
  								<c:if test="${not empty Summary.title}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td><b><spring:message code="lts.summary.familyTitle" text="Family Title"/>: </b></td>
										<td colspan="2" class="">${Summary.title}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.name}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.custName" text="Customer Name"/>: </b></td>
										<td colspan="2" class="">${Summary.name}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.companyName}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.companyName" text="Company Name"/>: </b></td>
										<td colspan="2" class="">${Summary.companyName}</td>
									</tr>
								</c:if>
								<c:if test="${empty Summary.companyName}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.dob" text="Date of Birth"/>: </b></td>
										<td colspan="2" class="">${Summary.birthday}</td>
									</tr>
								</c:if>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.custContactEmail" text="Customer Contact Email"/>: </b></td>
									<td colspan="2" class="">${Summary.custContactEmail}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.custContactMobNo" text="Customer Contact Mobile No."/>: </b></td>
									<td colspan="2" class="">${Summary.custMobileContactNum}</td>
								</tr>								
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.custContactFLNo" text="Customer Contact Fixed Line No."/>: </b></td>
									<td colspan="2" class="">${Summary.custFixContactNum}</td>
								</tr>
  							</c:otherwise>
  						</c:choose>
  						
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b>
							<c:if test="${Summary.fieldVisitRequired}">
								<spring:message code="lts.summary.inst" text="Installation"/> 
							</c:if>
							<spring:message code="lts.summary.contactName" text="Contact Name"/>: </b></td>
							<td colspan="2" class="">${Summary.apptContactName}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b>
							<c:if test="${Summary.fieldVisitRequired}">
								<spring:message code="lts.summary.inst" text="Installation"/> 
							</c:if> 
							<spring:message code="lts.summary.contactNo" text="Contact No."/>: </b></td>
							<td colspan="2" class="">${Summary.apptContactNum}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b>
							<c:if test="${Summary.fieldVisitRequired}">
								<spring:message code="lts.summary.inst" text="Installation"/> 
							</c:if>
							 <spring:message code="lts.summary.contactMobNo" text="Contact Mobile No."/>: </b></td>
							<td colspan="2" class="">${Summary.apptMobileContactNum}</td>
						</tr>

						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.instAddr" text="Installation Address"/>: </b></td>
							<td colspan="2" class="">${Summary.installAddr}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"/>
							<c:choose>
								<c:when test="${Summary.extRelInd == 'Y'}">
									<td colspan="2" class=""><spring:message code="lts.summary.er" text="External relocation"/></td>
								</c:when>
								<c:otherwise>
									<td colspan="2" class=""><spring:message code="lts.summary.sameAsExistInstAddr" text="Same as existing installation address"/></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.billAddr" text="Billing address"/>: </b></td>
							<c:choose>
								<c:when test="${Summary.billAddrChangeInd == 'I'}">
									<td colspan="2" class=""><spring:message code="lts.summary.alignInstAddr" text="Align with installation address"/></td>
									<c:if test="${Summary.billingAddrInstantUpdateInd == 'Y'}">
										<tr class="contenttext">
											<td width="6%"/>
											<td width="25%"/>
											<td colspan="2" class=""><spring:message code="lts.summary.instantUpdBillAddr" text="(Instant Update Billing Address)"/></td>
										</tr>
									</c:if>
								</c:when>
								<c:when test="${Summary.billAddrChangeInd == 'N'}">
									<td colspan="2" class=""><spring:message code="lts.summary.newBillAddr" text="New billing address"/></td><p>
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"/>
										<td colspan="2" class="">${Summary.billingAddress}</td>
									</tr>
									<c:if test="${Summary.billingAddrInstantUpdateInd == 'Y'}">
										<tr class="contenttext">
											<td width="6%"/>
											<td width="25%"/>
											<td colspan="2" class="">(Instant Update Billing Address)</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<td colspan="2" class=""><spring:message code="lts.summary.sameAsExistBillAddr" text="Same as existing billing address"/></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
					<br>
					<c:if test="${Summary.recontractInd == 'Y'}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.recontractDtl" text="RECONTRACT DETAILS"/></td>
							</tr>					
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td><b><spring:message code="lts.summary.transferorFamilyTitle" text="Transferor Family Title"/>: </b></td>
								<td colspan="2" class="">${Summary.transferorTitle}</td>
							</tr>
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.transferorCustName" text="Transferor Customer Name"/>: </b></td>
								<td colspan="2" class="">${Summary.transferorName}</td>
							</tr>
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.transferorDocTypeId" text="Transferor Document Type / ID"/>: </b></td>
								<td colspan="2" class="">${Summary.transferorDocType}: ${Summary.transferorDocNum}</td>
  							</tr>
  							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.relation" text="Relationship"/>: </b></td>
								<td colspan="2" class="">${Summary.transfereeRelationship}</td>
  							</tr>
  							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.transferMode" text="Transfer Mode"/>: </b></td>
								<td colspan="2" class="">${Summary.recontractMode}</td>
  							</tr>  	
  							<c:if test="${not empty Summary.deceasedType}">
	  							<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.deceaseCase" text="Deceased case"/>: </b></td>
									<td colspan="2" class="">
									<c:choose>
										<c:when test="${Summary.deceasedType == 'N'}">
											<spring:message code="lts.summary.refundToExistCust" text="Refund to Existing Customer"/>
										</c:when>
										<c:when test="${Summary.deceasedType == 'S'}">
											<spring:message code="lts.summary.refundToNewAppWthRelationSup" text="Refund to New Applicant (O/S &lt;/=$400 with Relationship Supporting)"/>
										</c:when>
										<c:when test="${Summary.deceasedType == 'I'}">
											<spring:message code="lts.summary.refundToNewAppWthLetter" text="Refund to New Applicant (with Probate / Letters of Administration )"/>
										</c:when>
									</c:choose>
									</td>
	  							</tr>
	  							<c:if test="${not empty Summary.transferorNewBillingName}">
	  								<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.newBillNameOfTransferor" text="New Billing Name of Transferor"/>: </b></td>
										<td colspan="2" class="">${Summary.transferorNewBillingName}</td>
	  								</tr>
	  							</c:if>
	  							<c:if test="${not empty Summary.transferorNewBillingAddr}">
	  								<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.summary.newBillAddrOfTransferor" text="New Billing Address of Transferor"/>: </b></td>
										<td colspan="2" class="">${Summary.transferorNewBillingAddr}</td>
	  								</tr>
	  							</c:if>
  							</c:if>
  													
  							<c:if test="${not empty Summary.recontractOptCallCardInd}">
  								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.globalCallingCardSrv" text="Global Calling Card Service"/>: </b></td>
									<td colspan="2" class="">${Summary.recontractOptCallCardInd}</td>
  								</tr>
  							</c:if>
  							<c:if test="${not empty Summary.recontractOptMobIddInd}">
  								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.mobIDD0060Srv" text="Mobile IDD0060 Service"/>: </b></td>
									<td colspan="2" class="">${Summary.recontractOptMobIddInd}</td>
  								</tr>
  							</c:if>
  							<c:if test="${not empty Summary.recontractOptFixedIddInd}">
  								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.IDD0060FFSrv" text="IDD0060 Fixed Fee Service"/>: </b></td>
									<td colspan="2" class="">${Summary.recontractOptFixedIddInd}</td>
  								</tr>
  							</c:if>
  						</table>
					<br>
					</c:if>
					<table width="100%" border="0" cellspacing="1" cellpadding="3"
						class="contenttext">
						<tr></tr>
												
						<tr>
							<td colspan="4" class="table_title" id="s_line_text">
								<c:choose>
	  								<c:when test="${isStandaloneVasOrder}">
	  									<spring:message code="lts.summary.VAS" text="VALUES ADDED SERVICE"/>
	  								</c:when>
	  								<c:otherwise>
	  									<spring:message code="lts.summary.coreSrv" text="CORE SERVICE"/>
									</c:otherwise>
	  							</c:choose>
  							</td>
						</tr>
						<c:if test="${!isStandaloneVasOrder}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.commitPeriod" text="Commitment Period"/>: </b></td>
								<td colspan="2" class="">${Summary.contractPeriod}</td>
							</tr>			
						</c:if>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b>
							<c:choose>
  								<c:when test="${Summary.fieldVisitRequired}">
  									<spring:message code="lts.summary.targetInstDate" text="Target Installation Date"/>:
  								</c:when>
  								<c:otherwise>
  									<spring:message code="lts.summary.targetEffDate" text="Target Effective Date"/>:
								</c:otherwise>
  							</c:choose>
							</b></td>
							<td colspan="2" class="">${Summary.installDate}</td>
						</tr>
						<c:if  test="${Summary.fieldVisitRequired}"> 
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.targetInstTime" text="Target Installation Time"/>: </b></td>
								<td colspan="2" class="">${Summary.installTime}</td>
							</tr>
						</c:if>
<!-- 
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b>Pre-wiring Date: </b></td>
							<td colspan="2" class="">${Summary.prewiringDate}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b>Pre-wiring Time:</b></td>
							<td colspan="2" class="">${Summary.prewiringTime}</td>
						</tr>
 -->
						<c:if test="${!isStandaloneVasOrder}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.targetTelSwitchDate" text="Target telephone switching Date"/>: </b></td>
								<td colspan="2" class="">${Summary.switchDate}</td>
							</tr>
						</c:if>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.appDate" text="Application Date"/>: </b></td>
							<td colspan="2" class="">${Summary.applDate}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.srvNum" text="Service Number"/>: </b></td>
							<td colspan="2" class="">${Summary.srvNum}
							</td>
						</tr>
						<c:if test="${not empty Summary.newSrvNum}">
							<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.newSrvNum" text="New Service Number"/>: </b></td>
							<td colspan="2" class="">${Summary.newSrvNum} 
							
							</td>
						</tr>
						</c:if>
						<c:if test="${not empty Summary.duplexNum}">
						    <tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.duplexNum" text="Duplex Number"/>: </b></td>
							<td colspan="2" class="">${Summary.duplexNum}
							</td>
						    </tr>	  
						</c:if>
						<c:if test="${not empty Summary.newDuplexNum}">
						   <tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.newDuplexNum" text="New Duplex Number"/>: </b></td>
							<td colspan="2" class="">${Summary.newDuplexNum}
							</td>
						   </tr>
						</c:if>
					</table>
					<br>
					<c:if test="${not empty Summary.srvPlanItemList && !isStandaloneVasOrder}">
						<table cellspacing="0" class="paper_no  round_20" width="100%">
							<c:forEach items="${Summary.srvPlanItemList}" var="planItem">
								<tr>
									<td class="contenttextgary" valign="middle">
										<c:if test="${not empty planItem.imagePath}">
											<p class="contenttextgreen">
												<img src="${planItem.imagePath}" width="80" />
											</p>
											<span class="posttext"></span>
										</c:if>
									</td>
									<td>
										<table cellspacing="1" class="table_style_sb" width="100%">
											<tr>
												<th width="60%" class="table_title" ></th>
												<th width="20%" align="center"><b><spring:message code="lts.summary.mthRateInCommitPeriod" text="Monthly Rate within Commitment Period"/></b></th>
												<th width="20%" align="center"><b><spring:message code="lts.summary.mth2mthRate" text="Month-to-Month Rate"/></b></th>
											</tr>
											<tr>
												<td width="60%" class="contenttext"><span class="basket_title">${planItem.itemDisplayHtml}</span>
													<c:if test="${not empty planItem.itemAttbs}">
														<c:forEach items="${planItem.itemAttbs}" var="attb">
															<br>${attb.attbDesc}: ${attb.attbValue} 
														</c:forEach>
													</c:if>
												</td>
												<td width="20%" class=" bg_grey" valign="top" align="center">${planItem.recurrentAmtTxt}</td>
												<td width="20%" class=" bg_grey" valign="top" align="center">${planItem.mthToMthAmtTxt}</td>
											</tr>
											<tr>
												<td width="60%" class="contenttext">&nbsp;</td>
												<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
												<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
											</tr>
										</table>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					<br>
					<table width="100%" border="0" cellspacing="0" class="contenttext">
						<tr>
							<td colspan="4"><img src="../images/x_graphic.gif" height="1"></td>
						</tr>
						<tr>
							<td class="table_title" id="s_line_text"><spring:message code="lts.summary.optPremSrvVAS" text="OPTIONAL PREMIUM, OPTIONAL SERVICES AND VALUES ADDED SERVICES"/></td>
						</tr>
					</table>
					<table cellspacing="1" class="table_style_sb" width="100%" >
						<tr>
							<th colspan="2" width="56%" class="contenttext">&nbsp;</th>
							<th width="20%" align="center"><b><spring:message code="lts.summary.mthRateInCommitPeriod" text="Monthly Rate within Commitment Period"/></b></th>
							<th width="20%" align="center"><b><spring:message code="lts.summary.mth2mthRate" text="Month-to-Month Rate"/></b></th>
						</tr>
						<c:if test="${not empty Summary.bbRentalItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.bbInstChrg" text="Broadband Line Rental / Installation Charge"/>:</b></td>
								<td width="20%" class="bg_grey" align="center">&nbsp;</td>
								<td width="20%" class="bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.bbRentalItemList}" var="bbRentalItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${bbRentalItem.itemDisplayHtml}
										<c:if test="${not empty bbRentalItem.itemAttbs}">
											<c:forEach items="${bbRentalItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${bbRentalItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${bbRentalItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.dnchangeChargeList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.dnChgOneTimeChrg" text="DN Change One Time Charge"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.dnchangeChargeList}" var="dnchangeCharge">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${dnchangeCharge.itemDisplayHtml} ${dnchangeCharge.oneTimeAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${dnchangeCharge.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${dnchangeCharge.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.dnYchangeChargeList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.dnChgOneTimeChrg" text="DN Change One Time Charge"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.dnYchangeChargeList}" var="dnYchangeCharge">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${dnYchangeCharge.itemDisplayHtml} ${dnYchangeCharge.oneTimeAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${dnYchangeCharge.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${dnYchangeCharge.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.erChargeList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.erChrg" text="External Relocation Charges"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.erChargeList}" var="erChargeItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${erChargeItem.itemDisplayHtml} (${erChargeItem.penaltyHandling})</td>
									<td width="20%" class=" bg_grey" align="center">${erChargeItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${erChargeItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.otherChargeList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.otherChrg" text="Other Charges"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.otherChargeList}" var="otherChargeItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${otherChargeItem.itemDisplayHtml}
										<c:if test="${not empty otherChargeItem.itemAttbs}">
											<c:forEach items="${otherChargeItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${otherChargeItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${otherChargeItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.peItemList || not empty Summary.socketItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.parallelPhoneLine" text="Parallel Phone Line"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:if test="${not empty Summary.peItemList}">
								<c:forEach items="${Summary.peItemList}" var="peItem">
									<tr valign="top">
										<td width="6%">&nbsp;</td>
										<td width="50%" class="contenttext">${peItem.itemDisplayHtml}
											<c:if test="${not empty peItem.itemAttbs}">
												<c:forEach items="${peItem.itemAttbs}" var="attb">
													<br>${attb.attbDesc}: ${attb.attbValue} 
												</c:forEach>
											</c:if>
										</td>
										<td width="20%" class=" bg_grey" align="center">${peItem.recurrentAmtTxt}</td>
										<td width="20%" class=" bg_grey" align="center">${peItem.mthToMthAmtTxt}</td>
									</tr>
								</c:forEach>
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">&nbsp;</td>
									<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
									<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.socketItemList}">
								<c:forEach items="${Summary.socketItemList}" var="socketItem">
									<tr valign="top">
										<td width="6%">&nbsp;</td>
										<td width="50%" class="contenttext">
											${socketItem.itemDisplayHtml}<br/>
											<spring:message code="lts.summary.numOfSocket" text="Number of Socket(s)"/>: ${socketItem.itemQty}
											<c:if test="${not empty socketItem.itemAttbs}">
												<c:forEach items="${socketItem.itemAttbs}" var="attb">
													<br>${attb.attbDesc}: ${attb.attbValue} 
												</c:forEach>
											</c:if>
										</td>
										<td width="20%" class=" bg_grey" align="center">${socketItem.recurrentAmtTxt}</td>
										<td width="20%" class=" bg_grey" align="center">${socketItem.mthToMthAmtTxt}</td>
									</tr>
								</c:forEach>
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">&nbsp;</td>
									<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
									<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								</tr>
							</c:if>
						</c:if>
						<c:if test="${not empty Summary.optAccItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.optAccessoy" text="Optional Accessories"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.optAccItemList}" var="optAccItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${optAccItem.itemDisplayHtml}
										<c:if test="${not empty optAccItem.itemAttbs}">
											<c:forEach items="${optAccItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${optAccItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${optAccItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.contentItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.infoSrvAvailOnEye" text="Information services available on eye Device"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.contentItemList}" var="contentItem">
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${contentItem.itemDisplayHtml}
										<c:if test="${not empty contentItem.itemAttbs}">
											<c:forEach items="${contentItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${contentItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${contentItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.moovItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.moovOnEye" text="MOOV on eye Home Smartphone"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.moovItemList}" var="moovItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${moovItem.itemDisplayHtml}
										<c:if test="${not empty moovItem.itemAttbs}">
											<c:forEach items="${moovItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${moovItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${moovItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.nowtvItemList || not empty Summary.nowtvSpecialItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.nowTvViaEyeForNowCust" text="Now TV services via eye Device for non Now TV customer only"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:if test="${not empty Summary.nowtvItemList}">
								<c:forEach items="${Summary.nowtvItemList}" var="nowtvItem">
									<tr valign="top">
										<td width="6%">&nbsp;</td>
										<td width="50%" class="contenttext">${nowtvItem.itemDisplayHtml}
											<c:if test="${not empty nowtvItem.itemAttbs}">
												<c:forEach items="${nowtvItem.itemAttbs}" var="attb">
													<br>${attb.attbDesc}: ${attb.attbValue} 
												</c:forEach>
											</c:if>
										</td>
										<td width="20%" class=" bg_grey" align="center">${nowtvItem.recurrentAmtTxt}</td>
										<td width="20%" class=" bg_grey" align="center">${nowtvItem.mthToMthAmtTxt}</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${not empty Summary.nowtvSpecialItemList}">
								<c:forEach items="${Summary.nowtvSpecialItemList}"
									var="nowtvSpecialItem">
									<tr valign="top">
										<td width="6%">&nbsp;</td>
										<td width="50%" class="contenttext">${nowtvSpecialItem.itemDisplayHtml}
											<c:if test="${not empty nowtvSpecialItem.itemAttbs}">
												<c:forEach items="${nowtvSpecialItem.itemAttbs}" var="attb">
													<br>${attb.attbDesc}: ${attb.attbValue} 
												</c:forEach>
											</c:if>
										</td>
										<td width="20%" class=" bg_grey" align="center">${nowtvSpecialItem.recurrentAmtTxt}</td>
										<td width="20%" class=" bg_grey" align="center">${nowtvSpecialItem.mthToMthAmtTxt}</td>
									</tr>
								</c:forEach>
							</c:if>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.channelList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.channel" text="Channel(s)"/>: </b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.channelList}" var="channel">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${channel}</td>

									<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
									<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<!-- <c:if test="${not empty Summary.billItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b>Billing option:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.billItemList}" var="billItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${billItem.itemDisplayHtml}
										<c:if test="${billItem.itemType == 'EMAILBILL'}">
											<br>Email Address: ${Summary.ltsEmailBillAddr}
										</c:if>
										<c:if test="${not empty billItem.itemAttbs}">
											<c:forEach items="${billItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${billItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${billItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>-->
						
						<c:if test="${not empty Summary.nowtvBillItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.billOptOfNewTV" text="Billing option of Now TV"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.nowtvBillItemList}" var="nowtvBillItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${nowtvBillItem.itemDisplayHtml}
										<c:if test="${nowtvBillItem.itemType == 'TV-EMAIL'}">
											<br>Email Address: ${Summary.emailBillAddress}
										</c:if>
										<c:if test="${not empty nowtvBillItem.itemAttbs}">
											<c:forEach items="${nowtvBillItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${nowtvBillItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${nowtvBillItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.vasEyeItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.vasForEye" text="Value-added Service for eye Device"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.vasEyeItemList}" var="vasEyeItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${vasEyeItem.itemDisplayHtml}
										<c:if test="${not empty vasEyeItem.itemAttbs}">
											<c:forEach items="${vasEyeItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${vasEyeItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${vasEyeItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.vasItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.newVAS" text="New Value-added Service"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.vasItemList}" var="vasItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${vasItem.itemDisplayHtml}
										<c:if test="${not empty vasItem.itemAttbs}">
											<c:forEach items="${vasItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${vasItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${vasItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.idd0060ItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.idd0060Srv" text="IDD 0060 / IDD 001 / 0060 everywhere Service"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.idd0060ItemList}" var="idd0060Item">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${idd0060Item.itemDisplayHtml}
										<c:if test="${not empty idd0060Item.itemAttbs}">
											<c:forEach items="${idd0060Item.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${idd0060Item.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${idd0060Item.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.optionalPremiumItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.optPrem" text="Optional Premium"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.optionalPremiumItemList}" var="optionalPremiumItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${optionalPremiumItem.itemDisplayHtml}
										<c:if test="${not empty optionalPremiumItem.itemAttbs}">
											<c:forEach items="${optionalPremiumItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${optionalPremiumItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${optionalPremiumItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
					</table>
					<br>
				<c:if test="${not empty Summary.smartWrtyList}">
						<table width="100%" border="0" cellspacing="0" class="contenttext">
						   <tr>
							<td class="table_title" id="s_line_text"><spring:message code="lts.summary.smartWarranty" text="SMART WARRANTY"/></td>
						   </tr>
					     </table>						
					      <table cellspacing="1" class="table_style_sb" width="100%" >
							
							<tr>
							 <th colspan="2" width="56%" class="contenttext">&nbsp;</th>
						    <th width="20%" align="center"><b><spring:message code="lts.summary.mthRateInCommitPeriod" text="Monthly Rate within Commitment Period"/></b></th>
							<th width="20%" align="center"><b><spring:message code="lts.summary.mth2mthRate" text="Month-to-Month Rate"/></b></th>
						    </tr>							
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.summary.deviceWarranty" text="DEVICE WARRANTY"/>:</b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.smartWrtyList}" var="vasItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${vasItem.itemDisplayHtml}
										<c:if test="${not empty vasItem.itemAttbs}">
											<c:forEach items="${vasItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${vasItem.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${vasItem.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						  </table>
						</c:if>
					<c:if test="${not empty Summary.premiumItemList}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.giftsPrem" text="GIFTS AND PREMIUMS"/></td>
							</tr>
							<c:forEach items="${Summary.premiumItemList}" var="premiumItem">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="55%"><b>${premiumItem.itemDisplayHtml}</b>
										<c:if test="${not empty premiumItem.itemAttbs}">
											<c:forEach items="${premiumItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td colspan="2" class="">${premiumItem.penaltyAmtTxt}</td>
								</tr>
							</c:forEach>
						</table>
						<br>
					</c:if>
					
					<c:if test="${not empty Summary.bundle2gNum }">
						<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.pccwMob2gSrv" text="PCCW mobile 2G service"/></td>
							</tr>
							<tr>
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.2gMobNum" text="2G Mobile Number"/>:</b></td>
								<td colspan="2">
									 ${Summary.bundle2gNum}
								</td>
							</tr>
						</table>
						<br>
					</c:if>
					<c:if test="${not empty Summary.redemMedia}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.redemMedia" text="REDEMPTION MEDIA"/></td>
							</tr>
							<tr><td colspan="4">&nbsp;</td></tr>
							<tr>
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.redemMediaLower" text="Redemption Media"/>:</b></td> 
								<td colspan="2">
								<c:if test="${Summary.redemMedia == 'P'}">
									<spring:message code="lts.summary.postal" text="Postal"/>
								</c:if>
								<c:if test="${Summary.redemMedia == 'S'}">
									<spring:message code="lts.summary.email" text="Email"/>: ${Summary.redemptionEmail}
								</c:if>
								<c:if test="${Summary.redemMedia == 'M'}">
									<spring:message code="lts.summary.sms" text="SMS"/>: ${Summary.redemptionSms}
								</c:if>
								</td>
							</tr>
						</table>
						<br>
					</c:if>
					<c:if test="${not empty Summary.billItemList || not empty Summary.nowtvBillItemList}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.billPref" text="BILL PREFERENCES"/></td>
							</tr>
							<c:if test="${not empty Summary.billItemList}">
								<tr>
									<td width="6%">&nbsp;</td>
									<td><b><spring:message code="lts.summary.billOpt" text="Billing option"/>:</b></td>
								</tr>
								<c:forEach items="${Summary.billItemList}" var="billItem">
									<tr>
										<td width="6%">&nbsp;</td>
										<td>${billItem.itemDisplayHtml}
										<c:if test="${billItem.itemType == 'EMAILBILL'}">
											<br><spring:message code="lts.summary.emailAddr" text="Email Address"/>: ${Summary.ltsEmailBillAddr}
										</c:if>
										<c:if test="${billItem.itemType == 'PAPER-BILL' || billItem.itemType == 'PAPER-W' 
										                                                || billItem.itemType == 'PAPER-G'}">
										
										    <c:if test="${not empty Summary.paperBillCharge}">
										            <br><br><spring:message code="lts.summary.paperBillChrg" text="Paper Bill Charing"/>: ${Summary.paperBillCharge}
										    </c:if>
											
											<c:if test="${not empty Summary.paperBillWaiveRea}">
													<br><spring:message code="lts.summary.paperBillWaiveReason" text="Paper Bill Waive Reason"/>: ${Summary.paperBillWaiveRea}
											</c:if>
											<c:if test="${not empty Summary.paperBillWaiveRemarks}">
													<br><spring:message code="lts.summary.paperBillWaiveRmk" text="Paper Bill Waive Remarks"/>: ${Summary.paperBillWaiveRemarks}
											</c:if>									    
										</c:if>
										<c:choose>
											<c:when test="${billItem.itemType == 'MYHKT-BILL' || billItem.itemType == 'THE-CLUB' || billItem.itemType == 'MYHKT-CLUB' }">
										   	<c:if test="${not empty Summary.cspEmail}">
										   			<br><spring:message code="lts.summary.emailAddr" text="Email Address"/>: ${Summary.cspEmail}
										   		</c:if>
										   		<c:if test="${not empty Summary.cspMobile}">
													<br><spring:message code="lts.summary.mobNum" text="Mobile Number"/>: ${Summary.cspMobile}
												</c:if>
											</c:when>
											<c:otherwise>
												<c:if test="${not empty billItem.itemAttbs}">
													<c:forEach items="${billItem.itemAttbs}" var="attb">
														<br>${attb.attbDesc}: ${attb.attbValue} 
													</c:forEach>
												</c:if>
											</c:otherwise>
										</c:choose>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td width="6%">&nbsp;</td>
									<td class="contenttext">&nbsp;</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.nowtvBillItemList}">
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext"><b>Billing option of now TV:</b></td>
								</tr>
								<c:forEach items="${Summary.nowtvBillItemList}"
									var="nowtvBillItem">
									<tr>
										<td width="6%">&nbsp;</td>
										<c:choose>
											<c:when test="${nowtvBillItem.itemType == 'TV-EMAIL'}">
												<td width="50%" class="contenttext">- ${nowtvBillItem.itemDisplayHtml}<BR><spring:message code="lts.summary.emailAddr" text="Email Address"/>: ${Summary.emailBillAddress}</td>
											</c:when>
											<c:otherwise>
												<td width="50%" class="contenttext">- ${nowtvBillItem.itemDisplayHtml}</td>
											</c:otherwise>
										</c:choose>
										<td colspan="2" class=""></td>
									</tr>
								</c:forEach>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">&nbsp;</td>
								</tr>
							</c:if>
						</table>
						<br>
					</c:if>
					<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
						<tr></tr>
						<tr>
							<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.payInfo" text="PAYMENT INFORMATION"/></td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.accNo" text="Account No."/>: </b></td>
							<td colspan="2" class="">${Summary.acctNum}</td>
						</tr>
						<c:if test="${Summary.paymentChangeInd=='Y'}">
							<c:if test="${Summary.paymentMethod=='C'}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.cardHolderName" text="Card Holder Name"/>: </b></td>
									<td colspan="2" class="">${Summary.holderName}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.ccType" text="Credit Card Type"/>: </b></td>
									<td colspan="2" class="">${Summary.credCardType}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.ccHolderDocTypeNo" text="Card Holder Holder Doc. Type / Doc. No."/>: </b></td>
									<td colspan="2" class="">${Summary.holderIdType}: ${Summary.holderIdNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.ccNo" text="Credit Card No."/>: </b></td>
									<td colspan="2" class="">${Summary.credCardNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.expDate" text="Expiry Date"/>: </b></td>
									<td colspan="2" class="">${Summary.credCardExpDate}</td>
								</tr>
							</c:if>
							<c:if test="${Summary.paymentMethod=='A'}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.bankAccHolderName" text="Bank Account Holder Name"/>: </b></td>
									<td colspan="2" class="">${Summary.holderName}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.bankAccHolderDocTypeNo" text="Bank Account Holder Doc. Type / Doc. No."/>: </b></td>
									<td colspan="2" class="">${Summary.holderIdType}: ${Summary.holderIdNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.bankCd" text="Bank Code"/>: </b></td>
									<td colspan="2" class="">${Summary.bankCd}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.branchCd" text="Branch Code"/>: </b></td>
									<td colspan="2" class="">${Summary.bankBranchCd}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.bankAccNo" text="Bank Account No."/>: </b></td>
									<td colspan="2" class="">${Summary.bankAcctNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.autopayLimit" text="Auto-pay Upper Limit"/>: </b></td>
									<td colspan="2" class="">${Summary.autoPayLimit}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.bankAppDate" text="Application Date"/>: </b></td>
									<td colspan="2" class="">${Summary.bankApplDate}</td>
								</tr>
							</c:if>
							<c:if test="${Summary.paymentMethod=='M'}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.billSelection" text="Bill Selection"/>: </b></td>
									<td colspan="2" class=""><spring:message code="lts.summary.cash" text="Cash"/></td>
								</tr>
							</c:if>
						</c:if>
						<c:if test="${Summary.paymentChangeInd=='N'}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.existPayMtd" text="Existing PCCW Payment Method"/>: </b></td>
								<c:if test="${Summary.paymentMethod=='C'}">
									<td colspan="2" class=""><spring:message code="lts.summary.cc" text="Credit Card"/></td>
								</c:if>
								<c:if test="${Summary.paymentMethod=='A'}">
									<td colspan="2" class=""><spring:message code="lts.summary.autopay" text="Bank Auto-pay"/></td>
								</c:if>
								<c:if test="${Summary.paymentMethod=='M'}">
									<td colspan="2" class=""><spring:message code="lts.summary.cash" text="Cash"/></td>
								</c:if>
							</tr>
						</c:if>
						<c:if test="${Summary.thirdPtyPayment == true}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.3rdPartyPay" text="Third Party Payment"/>: </b></td>
								<td colspan="2" class="">Yes</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.prepaymentItemList}">
							<c:forEach items="${Summary.prepaymentItemList}" var="prepaymentItem">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%" valign="top"><b><spring:message code="lts.summary.prepay" text="Prepayment"/>: </b></td>
									<td colspan="2" class="">${prepaymentItem.itemDisplayHtml}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.prepayAcc" text="Prepayment Account"/>: </b></td>
									<td colspan="2" class="">${Summary.acctNum}</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${not empty Summary.memoNum}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.salesMemoNum" text="Sales Memo Number"/>: </b></td>
								<td colspan="2" class="">${Summary.memoNum}</td>
							</tr>
						</c:if>
					</table>
					<br>
					<c:if test="${not empty Summary.thirdPtyName || not empty Summary.thirdPtyDocId || not empty Summary.thirdPtyDocType || not empty Summary.thirdPtyContactNum || not empty Summary.thirdPtyRelation}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3"
							class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.3rdParty" text="THIRD PARTY"/></td>
							</tr>
							<c:if test="${not empty Summary.thirdPtyName}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.3rdPartyName" text="Third-Party Name"/>: </b></td>
									<td colspan="2" class="">${Summary.thirdPtyName}</td>
								</tr>
							</c:if>
							<c:if
								test="${not empty Summary.thirdPtyDocType && not empty Summary.thirdPtyDocId}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.docTypeId" text="Document Type & ID"/>: </b></td>
									<td colspan="2" class="">${Summary.thirdPtyDocType}:
										${Summary.thirdPtyDocId}</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.thirdPtyContactNum}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.3rdPartyContactNum" text="Third-Party Contact Number"/>: </b></td>
									<td colspan="2" class="">${Summary.thirdPtyContactNum}</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.thirdPtyRelation}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.summary.relation" text="Relationship"/>: </b></td>
									<td colspan="2" class="">${Summary.thirdPtyRelation}</td>
								</tr>
							</c:if>
						</table>
						<br>
					</c:if>
					<div id="compareExSrvDiv" style="display: none;">
						<table width="100%" border="0" cellspacing="0" class="contenttext">
							<tr>
								<td colspan="4">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.effPriceCompare" text="Effective Price Comparison"/></td>
							</tr>
							<tr>
								<td colspan="4">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4" align="center">
									<table width="100%" border="1" cellpadding="0" cellspacing="0" >
										<tr bgcolor="#848484">
											<td width="50%" align="center"><b><spring:message code="lts.summary.effPriceBeforeUpg" text="Effective Price Before Upgrade"/></b></td>
											<td width="50%" align="center"><b><spring:message code="lts.summary.futureEffPrice" text="Future Effective Price"/></b></td>
										</tr>
										<td width="50%" align="left" valign="top">
											<table width="100%" border="0">
												<c:forEach items="${Summary.currentProfileItemList}" var="currentItem">
													<tr>
														<td width="6%">&nbsp;</td>
														<td width="75%" class="contenttext">${currentItem.itemDesc}</td>
														<td width="20%" class="" align="right">$${currentItem.recurrentAmt}</td>
													</tr>
												</c:forEach>
											</table>
										</td>
										<td width="50%" align="left" valign="top">
											<table width="100%" border="0">
												<c:forEach items="${Summary.futureProfileItemList}" var="futureItem">
													<tr>
														<td width="6%">&nbsp;</td>
														<td width="75%" class="contenttext">${futureItem.itemDesc}</td>
														<td width="20%" class="" align="right">$${futureItem.recurrentAmt}</td>
													</tr>
												</c:forEach>
											</table>
										</td>
										<tr bgcolor="#E6E6E6">
											<td width="50%" align="center"><b>Total: $${Summary.currentChargeTotal}</b></td>
											<td width="50%" align="center"><b>Total: $${Summary.futureChargeTotal}</b></td>
										</tr>
									</table>
								</tr>
						</table>
					</div>
					<br/>
					<!-- BOM2018061 -->
					<c:if test="${not empty Summary.epdItem}">
					<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
						<tr></tr>
						<tr>
							<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.weee" text="Waste Electrical and Electronic Equipment"/></td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.option" text="Option"/>: </b></td>
							<td colspan="2" class="">${Summary.epdItem.itemDesc}</td>
						</tr>
						<c:if test="${not empty Summary.epdItem.itemAttbs}">
							<c:forEach items="${Summary.epdItem.itemAttbs}" var="itemAttb" varStatus="attbStatus">
								<c:if test="${not empty itemAttb.attbValue}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>${itemAttb.attbDesc} : </b></td>
									<td colspan="2" class="">${itemAttb.attbValue}</td>
								</tr>
								</c:if>
							</c:forEach>
						</c:if>
					</table>
					<br/>
					<br/>
					</c:if>
					<!-- BOM2018061 END -->
					
					<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
						<tr></tr>
						<tr>
							<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.PICS" text="Personal Information Collection Statement"/></td>
						</tr>
					
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.optInOutStatus" text="Opt-In / Opt-Out Status"/>: </b></td>
							<td colspan="2" class="">
								<c:choose>
									<c:when test="${empty Summary.updBomStatus || Summary.updBomStatus == 'I'}">
										<spring:message code="lts.summary.remainUnchange" text="Remain unchanged"/>
									</c:when>
									<c:when test="${Summary.custOptOutInd == 'OOP'}">
										<spring:message code="lts.summary.optOutPartial" text="Opt-Out Partial"/>
									</c:when>
									<c:when test="${Summary.custOptOutInd == 'OOA'}">
										<spring:message code="lts.summary.optOutAll" text="Opt-Out All"/>
									</c:when>
									<c:when test="${Summary.custOptOutInd == 'OIA'}">
										<spring:message code="lts.summary.optIn" text="Opt-In"/>
									</c:when>
									<c:otherwise>
										<spring:message code="lts.summary.blank" text="Blank"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<c:if test="${not empty Summary.updBomStatus && Summary.updBomStatus != 'I' && Summary.custOptOutInd == 'OOP'}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"></td>
								<td colspan="2" class="">
									<table class="table_style" width="100%" border="1">
										<tr>
											<th align="center"><spring:message code="lts.summary.directMailing" text="Direct Mailing"/></th>
											<th align="center"><spring:message code="lts.summary.outbound" text="Outbound"/></th>
											<th align="center"><spring:message code="lts.summary.sms" text="SMS"/></th>
											<th align="center"><spring:message code="lts.summary.email" text="Email"/></th>
											<th align="center"><spring:message code="lts.summary.webBIll" text="Web Bill"/></th>
											<th align="center"><spring:message code="lts.summary.billInsert" text="Bill Insert"/></th>
											<th align="center"><spring:message code="lts.summary.cdOutdail" text="CD Outdail"/></th>
											<th align="center"><spring:message code="lts.summary.bm" text="BM"/></th>
											<th align="center"><spring:message code="lts.summary.smseye" text="SMS(EYE)"/></th>
										</tr>
										<tr>
											<td align="center"><input type="checkbox" ${Summary.ltsPdpoDirectMailing ? 'checked="checked"' : ''} disabled="disabled"/></td>
											<td align="center"><input type="checkbox" ${Summary.ltsPdpoOutbound ? 'checked="checked"' : ''} disabled="disabled"/></td>
											<td align="center"><input type="checkbox" ${Summary.ltsPdpoSms ? 'checked="checked"' : ''}disabled="disabled"/></td>
											<td align="center"><input type="checkbox" ${Summary.ltsPdpoEmail ? 'checked="checked"' : ''} disabled="disabled"/></td>
											<td align="center"><input type="checkbox" ${Summary.ltsPdpoWebBill ? 'checked="checked"' : ''} disabled="disabled"/></td>
											<td align="center"><input type="checkbox" ${Summary.ltsPdpoBillInsert ? 'checked="checked"' : ''} disabled="disabled"/></td>
											<td align="center"><input type="checkbox" ${Summary.ltsPdpoCdOutdial ? 'checked="checked"' : ''} disabled="disabled"/></td>
											<td align="center"><input type="checkbox" ${Summary.ltsPdpoBm ? 'checked="checked"' : ''} disabled="disabled"/></td>
											<td align="center"><input type="checkbox" ${Summary.ltsPdpoSmsEye ? 'checked="checked"' : ''} disabled="disabled"/></td>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
					</table>
					<br/>
					<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
						<tr></tr>
						<tr>
							<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.internalUseForSrv" text="INTERNAL USE FOR SERVICE"/></td>
						</tr>
						<c:if test="${not empty Summary.eyeArrangement}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.eyeArrange" text="eye Arrangement"/>: </b></td>
								<td colspan="2" class="">${Summary.eyeArrangement}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.fsaArrangement}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.fsaArrange" text="FSA Arrangement"/>: </b></td>
								<td colspan="2" class="">${Summary.fsaArrangement}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.relatedFSA}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.shareFSA" text="Shared FSA"/>: </b></td>
								<td colspan="2" class="">${Summary.relatedFSA}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.lostModem}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.lostModem" text="Lost Modem"/>: </b></td>
								<td colspan="2" class="">${Summary.lostModem}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.modemSelection}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.modemSelection" text="Modem Selection"/>: </b></td>
								<td colspan="2" class="">${Summary.modemSelection}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.imsSbOrder}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.relateFSASBOrd" text="Related FSA SB Order ID"/>: </b></td>
								<td colspan="2" class="">${Summary.imsSbOrder}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.edfNo}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.edfNo" text="EDF No."/>: </b></td>
								<td colspan="2" class="">${Summary.edfNo}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.modemArrangement}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.modemArrange" text="Modem Arrangement"/>: </b></td>
								<td colspan="2" class="">${Summary.modemArrangement}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.mirrorFsa}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.mirrorFSA" text="Mirror FSA"/>: </b></td>
								<td colspan="2" class="">${Summary.mirrorFsa}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.nowtvSrvType}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.nowTVSrvType" text="Now TV Service Type"/>: </b></td>
								<td colspan="2" class="">${Summary.nowtvSrvType}</td>
							</tr>
						</c:if>
						
						
						<c:if test="${not empty Summary.srvTypeChange}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.srvType" text="Service Type"/>: </b></td>
								<td colspan="2" class="">${Summary.srvTypeChange}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.bandwidthChange}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.bandwidth" text="Bandwidth"/>: </b></td>
								<td colspan="2" class="">${Summary.bandwidthChange}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.technologyChange}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.tech" text="Technology"/>: </b></td>
								<td colspan="2" class="">${Summary.technologyChange}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.allowSelfPickup}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.allowSelfPickup" text="Allow Self-Pickup Device"/>: </b></td>
								<td colspan="2" class="">${Summary.allowSelfPickup}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.optOutItemList}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.optOutItemDesc" text="Opt-Out Item Description"/>: </b></td>
								<td valign="top" align="left">
									<ul>
										<c:forEach items="${Summary.optOutItemList}" var="optOutItem">
											<li><span class="">${optOutItem.itemDisplayHtml}</span></li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.workQueueType}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.wqType" text="Work Queue Type"/>: </b></td>
								<td colspan="2" class="">${Summary.workQueueType}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.pendingLtsOcid}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.pendingLtsOCID" text="Pending LTS OCID"/>: </b></td>
								<td colspan="2" class="">${Summary.pendingLtsOcid}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.pendingImsOcid}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.pendingImsOCID" text="Pending IMS OCID"/>: </b></td>
								<td colspan="2" class="">${Summary.pendingImsOcid}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.cancelDuplexType}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.cxl" text="Cancel"/> ${Summary.cancelDuplexType}: </b></td>
								<td colspan="2" class="">${fn:substring(Summary.cancelDuplexDn,4,12)}</td>
							</tr>
						</c:if>
						<c:if test="${Summary.blacklistAddrInd == true}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.blacklistAddr" text="Blacklist Address"/>: </b></td>
								<td colspan="2" class="">Yes</td>
							</tr>
						</c:if>
						<c:if test="${Summary.fsaExtRelInd == true}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.otherCustReq" text="Other Customer Request"/>: </b></td>
								<td colspan="2" class=""><spring:message code="lts.summary.fsaRelocation" text="FSA Relocation"/></td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.dummyDocNum}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.dummyDocTypeId" text="Dummy Document Type / ID"/>: </b></td>
								<td colspan="2" class="">${Summary.dummyDocType} : ${Summary.dummyDocNum}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.staffPlanApplicantId}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.staffPlanAppId" text="Staff Plan Applicant ID"/>: </b></td>
								<td colspan="2" class="">${Summary.staffPlanApplicantId}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.outLtsItemList}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%" valign="top"><b><spring:message code="lts.summary.outLtsItemDesc" text="Out LTS Item Description"/>: </b></td>
								<td valign="top" align="left">
									<table class="table_style " border="1">
										<tr class="contenttext">
											<th colspan="2" width="25%" ><b><spring:message code="lts.summary.itemDesc" text="Item Description"/></b></th>
											<th colspan="2" width="25%" ><b><spring:message code="lts.summary.penalty" text="Penalty Handling"/></b></th>
										</tr>
										<c:forEach items="${Summary.outLtsItemList}" var="outItem">
											<tr class="contenttext" align="center">
												<td colspan="2" >${outItem.itemDisplayHtml}</td>
												<td colspan="2" >${outItem.penaltyHandling}</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.outImsItemList}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%" valign="top"><b><spring:message code="lts.summary.outImsItemDesc" text="Out IMS Item Description"/>: </b></td>
								<td valign="top" align="left">
									<table class="table_style" border="1">
										<tr class="contenttext">
											<th colspan="2" width="25%" ><b><spring:message code="lts.summary.itemDesc" text="Item Description"/></b></th>
											<th colspan="2" width="25%" ><b><spring:message code="lts.summary.penalty" text="Penalty Handling"/></b></th>
										</tr>
										<c:forEach items="${Summary.outImsItemList}" var="outItem">
											<tr class="contenttext" align="center">
												<td colspan="2" >${outItem.itemDisplayHtml}</td>
												<td colspan="2" >${outItem.penaltyHandling}</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</c:if>
					</table>
					<div id="showRemarksDiv" style="display: none;" class="remarkDiv">
						<table width="100%" border="0" cellspacing="0" class="contenttext">
							<c:if test="${not empty Summary.orderRemarks}">
								<td width="25%"><b><spring:message code="lts.summary.ordRmk" text="Order Remarks"/>: </b></td>
								<tr>
									<td align="center" colspan="4" width="100%">
										<textarea rows="10" cols="100%" readonly="readonly">${Summary.orderRemarks}</textarea>
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.addOnRemarks}">
								<td width="25%"><b><spring:message code="lts.summary.fieldSrvAddonRmk" text="Field Service Add-on Remarks"/>: </b></td>
								<tr>
									<td align="center" colspan="4" width="100%">
										<textarea rows="10" cols="100%" readonly="readonly">${Summary.addOnRemarks}</textarea>
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.custRemarks}">
								<td width="25%"><b><spring:message code="lts.summary.custRmk" text="Customer Remarks"/>: </b></td>
								<tr>
									<td align="center" colspan="4" width="100%">
										<textarea rows="10" cols="100%" readonly="readonly">${Summary.custRemarks}</textarea>
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.earliestSrdReasonRemarks}">
								<td width="25%"><b><spring:message code="lts.summary.earlistSrdReason" text="Earliest SRD Reason"/>: </b></td>
								<tr>
									<td align="center" colspan="4" width="100%">
										<textarea rows="10" cols="100%" readonly="readonly">${Summary.earliestSrdReasonRemarks}</textarea>
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.fsRemark}">
								<td width="25%"><b><spring:message code="lts.summary.fsInstruction" text="F&S Instructions"/>: </b></td>
								<tr>
									<td align="center" colspan="4" width="100%">
										<textarea rows="10" cols="100%" readonly="readonly">${Summary.fsRemark}</textarea>
									</td>
								</tr>
							</c:if>
						</table>
					</div>
					<br>
				</c:forEach>
				<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
					<tr></tr>
					<tr>
						<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.summary.internalUse" text="INTERNAL USE"/></td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.summary.staffNo" text="Staff No."/>: </b></td>
						<td colspan="2" class="">${serviceSummary.staffNum}</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.summary.salesmenCd" text="Salesmen Code / CMRID"/>: </b></td>
						<td colspan="2" class="">${serviceSummary.salesCd}</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.summary.salesChannel" text="Sales Channel"/>: </b></td>
						<td colspan="2" class="">${serviceSummary.salesChannel}</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.summary.teamShopCd" text="Team / Shop Code"/>: </b></td>
						<td colspan="2" class="">${serviceSummary.salesTeam}</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.summary.salesContactNo" text="Sales Contact No."/>: </b></td>
						<td colspan="2" class="">${serviceSummary.salesContactNum}</td>
					</tr>
					<c:if test="${not empty serviceSummary.salesPosition}">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.position" text="Position"/>: </b></td>
							<td colspan="2" class="">${serviceSummary.salesPosition}</td>
						</tr>
					</c:if>
					<c:if test="${not empty serviceSummary.salesJob}">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.job" text="Job"/>: </b></td>
							<td colspan="2" class="">${serviceSummary.salesJob}</td>
						</tr>
					</c:if>
					<c:if test="${not empty serviceSummary.salesProcessDate}">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.processDate" text="Process Date"/>: </b></td>
							<td colspan="2" class="">${serviceSummary.salesProcessDate}</td>
						</tr>
					</c:if>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.summary.orderCreateBy" text="Order create by"/>: </b></td>
						<td colspan="2" class="">${serviceSummary.createBy}</td>
					</tr>
				</table> <br>
				<c:forEach items="${serviceSummary.serviceSummaryList}" var="Summary">
				<c:if test="${Summary.srvType == 'EYE' || Summary.srvType == 'DEL'}">
				<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
				<tr></tr>
						<tr>
							<td colspan="5" class="table_title" id="s_line_text"><spring:message code="lts.summary.esignInfo" text="E-SIGNATURE INFORMATION"/></td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.distMode" text="Distribution Mode"/> : </b></td>
							<td colspan="2" class="">
								<c:if test="${Summary.disMode == 'E'}">
									<spring:message code="lts.summary.digital" text="Digital"/>
								</c:if>
								<c:if test="${Summary.disMode == 'P'}">
									<spring:message code="lts.summary.paper" text="Paper"/>
								</c:if>
								<c:if test="${Summary.disMode != 'P' && Summary.disMode != 'E'}">
									${Summary.disMode}
								</c:if>
							</td>
						</tr>
						<c:if test="${not empty Summary.esigEmailAddr}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.email" text="Email"/> : </b></td>
								<td colspan="2" class="">
									<span id="esigEmailAddr" style="color: red; font-weight: bold;">${Summary.esigEmailAddr}</span>
									</td>
							</tr>
						</c:if>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.summary.afLang" text="Application Form Language"/> : </b></td>
							<td colspan="2" class="">
								<c:if test="${Summary.esigEmailLang == 'ENG'}">
									<spring:message code="lts.summary.eng" text="English"/>
								</c:if>
								<c:if test="${Summary.esigEmailLang == 'CHN'}">
									<spring:message code="lts.summary.tradChi" text="Traditional Chinese"/>
								</c:if>
								<c:if test="${Summary.esigEmailLang != 'ENG' && Summary.esigEmailLang != 'CHN'}">
									${Summary.esigEmailLang}
								</c:if>
						</tr>
						<c:if test="${not empty serviceSummary.smsNo}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.summary.smsNo" text="SMS No."/>: </b></td>
								<td colspan="2" class="">${serviceSummary.smsNo}</td>
							</tr>
						</c:if>
						<tr>
						<td colspan="4">
						<table width="100%" border="0" class="contenttext">
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="90%">
											<div class="supportDocDiv">
											<table class="table_style" width="100%" border="1">
												<tr >
													<th width="45%"><spring:message code="lts.summary.docType" text="Document Type"/></th>
													<th width="20%"><spring:message code="lts.summary.waiveRea" text="Waive Reason"/></th>
													<th width="8%"><spring:message code="lts.summary.collected" text="Collected"/></th>
													<th width="15%"><spring:message code="lts.summary.mandatoryBeforeSignoff" text="Mandatory Before Signoff"/></th>
													<th width="12%"><spring:message code="lts.summary.faxSerialNum" text="Fax Serial Number"/></th>
												</tr>
												<c:choose>
													<c:when test="${not empty Summary.collectDocList}">
														<c:forEach items="${Summary.collectDocList}" var="supportDocument" varStatus="status">
															<c:if test="${supportDocument.markDelInd != 'Y'}">
															<tr align="center">
																<td>${supportDocument.docTypeDisplay}</td>
																<td><c:out value="${supportDocument.waiveReasonDisplay}" default="--" /></td>
																<td>${supportDocument.collectedInd != 'Y'? 'N': supportDocument.collectedInd}</td>
																<td>${supportDocument.mandatory ? 'Y' : 'N'}</td>
																<td>${supportDocument.faxSerial}</td>
															</tr>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr>
															<td align="center" colspan="5">NIL</td>
														</tr>
													</c:otherwise>
												</c:choose>
											</table>
										</div>
									</td>
								</tr>
						</table>
						<td>
						</tr>
					</table>
					</c:if>
					</c:forEach>
					</div>
			</td>
		</tr>

		<c:if test="${not empty serviceSummary.messageList}">
			<tr>
 				<td>
					<div id="error_profile" class="ui-widget" style="visibility: visible;">
						<div class="ui-state-error ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
							<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							</p>
							<div id="error_profile_msg" class="contenttext">
								<c:forEach items="${serviceSummary.messageList}" var="message">
									${message}<BR>
								</c:forEach>
							</div>
							<p></p>
						</div>
					</div>
				</td>
			</tr>
		</c:if>
	</table>	
	<br>
	
	</td>
</tr>
</table>
<br><br>
	
	<c:set var="btnNameCd" value="${sessionScope.bomsalesuser.channelId == 1 
									|| sessionScope.sessionLtsOrderCapture.sbOrder.signoffMode == 'R' ? 'lts.summary.signoff' : 'lts.summary.submit'}"/>
	<c:if test="${serviceSummary.requireButton == '1'}">
		<c:set var="btnNameCd" value="lts.summary.requestApproval" />
	</c:if>
	<c:if test="${serviceSummary.requireButton == '4'}">
		<c:set var="btnNameCd" value="lts.summary.suspend" />
	</c:if>
	<c:set var="btnName"><spring:message code="${btnNameCd}"/></c:set>
	<spring:hasBindErrors name="serviceSummary">
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
	<form:hidden path="buttonPressed" id="buttonPressed" />
	<input type="hidden" name="isFormPrinted" value="false">
	<input type="hidden" name="isFormPreviewed" value="false">
	<input type="hidden" name="isSignatureSigned" value="false">
	
	<c:if test="${enquiry != 'ENQUIRY' && enquiry != 'ENQUIRY_N_CANCEL'}">
		<table width="100%" border="0" cellspacing="0" align="right">
			<tr>
				<td align="right">
					<form:errors path="suspendReason" cssClass="error" /> 
						<b><spring:message code="lts.summary.suspendRea" text="Suspend Reason"/> : </b> 
					<form:select path="suspendReason" id="suspendReason">
						<form:option value="NONE"><spring:message code="lts.summary.select" text="-- SELECT --"/></form:option>
						<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
					</form:select> 
					<a id="suspend" href="#" class="nextbutton"><div class="button"><spring:message code="lts.summary.suspend" text="Suspend"/></div></a> &nbsp; 
				</td>
			</tr>
		</table>
		<br><br>
	</c:if>
	<br>
	<table width="100%" border="0" cellspacing="0" align="right">
		<tr align="right">
			<td align="right">
				<!-- <a id="refresh" href="#"class="nextbutton">Refresh</a> -->
				<a id="showRemarks" href="#"class="nextbutton"><div class="func_button"><spring:message code="lts.summary.showRmks" text="Show Remarks"/></div></a> 
<!-- 				<a id="compareExSrv" href="#"class="nextbutton">Compare Existing Service</a>	 --> 
				
				<c:if test="${serviceSummary.containPrepayment == true}">
					<a id="printPS" name="areport" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.viewPaySlip" text="View Payment Slip"/></div></a>
				</c:if>								
				<a id="printAF" name="areport" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.printAF" text="Print AF"/></div></a>
				<a id="previewAF" name="areport" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.previewAF" text="Preview AF"/></div></a>
				<c:if test="${!serviceSummary.onlineAccqOrder && enquiry == 'ENQUIRY'}">
					<c:if test="${serviceSummary.distributeMode == 'E' && sessionScope.bomsalesuser.channelId == 1}">
						<a id="captureDociPad" href="${captureDocIpadAppUri}" class="func_button"><div class="button"><spring:message code="lts.summary.proofByIpad" text="Capture Proof by iPad"/></div></a>
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.proofByPCUpload" text="Capture Proof (upload via PC only)"/></div></a>
					</c:if>
					<c:if test="${serviceSummary.distributeMode == 'P' && sessionScope.bomsalesuser.channelId == 1}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.proofByPCUpload" text="Capture Proof (upload via PC only)"/></div></a>
					</c:if>
					<c:if test="${sessionScope.bomsalesuser.channelId == 3}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.proofByPCUpload" text="Capture Proof (upload via PC only)"/></div></a>
					</c:if>
				</c:if>
				<c:if test="${enquiry != 'ENQUIRY' && enquiry != 'ENQUIRY_N_CANCEL'}">
					<!-- E: ESIGNATURE -->
					<c:if test="${serviceSummary.distributeMode == 'E' && sessionScope.bomsalesuser.channelId == 1}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.proofByPCUpload" text="Capture Proof (upload via PC only)"/></div></a>
						<a id="captureDociPad" href="${captureDocIpadAppUri}" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.proofByIpad" text="Capture Proof by iPad"/></div></a>
						<a id="digitalSignature" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.digiSign" text="Digital Signature"/></div></a>
					</c:if>
					<c:if test="${serviceSummary.distributeMode == 'P' && sessionScope.bomsalesuser.channelId == 1}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.proofByPCUpload" text="Capture Proof (upload via PC only)"/></div></a>
					</c:if>
					<c:if test="${sessionScope.bomsalesuser.channelId == 3 && serviceSummary.signoffMode == 'R'}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.proofByPCUpload" text="Capture Proof (upload via PC only)"/></div></a>
						<a id="captureDociPad" href="${captureDocIpadAppUri}" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.proofByIpad" text="Capture Proof by iPad"/></div></a>
						<br/><div style="height:5px"></div>
						<c:if test="${serviceSummary.distributeMode == 'E'}">
							<a id="digitalSignature" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.digiSign" text="Digital Signature"/></div></a>
						</c:if>
					</c:if>
					<c:if test="${sessionScope.bomsalesuser.channelId == 3 && serviceSummary.signoffMode != 'R'}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.proofByPCUpload" text="Capture Proof (upload via PC only)"/></div></a>
					</c:if>
					<c:if test="${sessionScope.bomsalesuser.channelId == 2}">
						<a id="captureDocSerial" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.updateFaxSerialNum" text="Update Fax Serial Number"/></div></a>
					</c:if>
					<c:if test="${sessionScope.bomsalesuser.channelId == 2 || sessionScope.bomsalesuser.channelId == 3}">
						<c:if test="${serviceSummary.allowUpdateEdfRef }">
							<a id="updateEdfRef" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.updateEDFRef" text="Update EDF Reference"/></div></a>
						</c:if>
					</c:if>
					<br/><br/><br/>
					<c:if test="${serviceSummary.requireButton != '3'}">
						<a id="signoff" href="#" class="nextbutton" name="asignoff"><div class="button">${btnName}</div></a>
					</c:if>
					<a id="cancel" href="#" class="nextbutton" name="acancel"><div class="button"><spring:message code="lts.summary.cxl" text="Cancel"/></div></a>
				</c:if>
				<c:if test="${serviceSummary.onlineAccqOrder}">
					<a href="ltsamendhist.html?orderId=${serviceSummary.orderId}" class="nextbutton"><div class="func_button"><spring:message code="lts.summary.amendHist" text="Amendment History"/></div></a>
					<a href="sboordersearch.html" class="nextbutton"><div class="button"><spring:message code="lts.summary.back" text="Back"/></div></a> 
				</c:if>
				<c:if test="${enquiry == 'ENQUIRY_N_CANCEL'}">
					<a id="cancelOrder" href="#" class="nextbutton"><div class="button"><spring:message code="lts.summary.cxlOrd" text="Cancel Order"/></div></a>
					<a id="exit" href="welcome.html?sbuid=${param.sbuid}" onclick="return confirm('Exit without Save. Sure to continue?');" class="nextbutton"><div class="button"><spring:message code="lts.summary.exitNoSave" text="Exit Without Save"/></div></a>
				</c:if>
			</td>
		</tr>
</table>
</form:form>
<br/><br/>
</div>
<script type="text/javascript">
	
	$(document).ready(function() {
		
		if("${signoffCompletedMsg}" != ""){
			alert("${signoffCompletedMsg}");
		}

		$("a#signoff").click(function(event) {
			event.preventDefault();
			var isFormPrinted = $('input[name="isFormPrinted"]').val();
			var isFormPreviewed = $('input[name="isFormPreviewed"]').val();
			
			
			if (isFormPrinted != 'true' && "${serviceSummary.distributeMode}" == 'P' && "${sessionScope.bomsalesuser.channelId}" == '1') {
				alert('Please print AF before ${btnName}.');
				return;
			} 

			if (isFormPreviewed != 'true' && "${serviceSummary.distributeMode}" == 'E') {
				alert("Please preview AF before ${btnName}.");
				return;
			}	
			
			if (isFormPrinted != 'true' && "${serviceSummary.distributeMode}" == 'E' && $('#esigEmailAddr').html() == null && "${sessionScope.bomsalesuser.channelId}"  == '1') {
				alert("Please print AF before ${btnName}.");
				return;
			}
			
			if ("${btnName}" == "Signoff") {
				
				if ($('input[name="orderGeneratePenalty"]').val() == "true") {
					var confirmPenalty = confirm("Penalty will be generated. Continue ?");
					if (!confirmPenalty){
						return;
					}
				}
				
				if ("${serviceSummary.distributeMode}" == 'E' && $('#esigEmailAddr').html() != null) {
					var email = $('#esigEmailAddr').html();
					var answer = confirm("Email will be sent to " + email + " Confirm to continue?");
					if (!answer){
						return;
					}
				}
			} 
			$("#buttonPressed").val("${serviceSummary.requireButton}");

			
			if($("#isSignoffStarted").val() == "N"){
				$("#isSignoffStarted").val("Y");
				$("#summary").submit();
			}
		});

		$("a#digitalSignature").click(function(event) {
			event.preventDefault();
			
			if ($('input[name="isFormPreviewed"]').val() == 'false') {
				alert("Please preview the application form before sign signature.");
				return;
			}
			$('input[name="isSignatureSigned"]').val('true');
			window.open('ltsdigitalsignature.html?sbuid=${sessionScope.sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
		});
		
		$("a#captureDocSerial").click(function(event) {
			event.preventDefault();
			var win = window.open('ltscollectdoc.html?sbuid=${sessionScope.sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=512,width=1024');
			//win.onclose(function(){location.reload(true); });
		});
		
		$("a#updateEdfRef").click(function(event) {
			event.preventDefault();
			var win = window.open('ltsedfref.html?sbuid=${sessionScope.sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=512,width=1024');
		});
		
		$("a#showRemarks").click(function(event) {
			event.preventDefault();
			$("div.remarkDiv").show();
		});
	
	//	$("a#compareExSrv").click(function(event) {
	//		event.preventDefault();
	//		$("div#compareExSrvDiv").show();
//	});
		
		$("a#cancel").click(function(event) {
			if (confirm("Please confirm cancel the order.")) {
				$("#buttonPressed").val("2");
				$("#summary").submit();
			}
		});

		$("a#cancelOrder").click(function(event) {
			if (confirm("Please confirm cancel the order.")) {
				$("#buttonPressed").val("2");
				$("#summary").submit();
			}
		});
		
		$("a#suspend").click(function(event) {
			$("#buttonPressed").val("5");
			$("#summary").submit();
		});
		
		$("a#refresh").click(function(event) {
			$("#buttonPressed").val("6");
			$("#summary").submit();
		});
		
		$("a#printAF").click(function(event) {
			event.preventDefault();
			var sbuid = $('input[name="sbuid"]').val();
			window.open('ltsreport.html?sbuid='+ sbuid + '&ltsRptAction=PRINT_AF');	
			$('input[name="isFormPrinted"]').val('true');
		});
		
		$("a#previewAF").click(function(event) {
			event.preventDefault();		
			var sbuid = $('input[name="sbuid"]').val();
			window.open('ltsreport.html?sbuid='+ sbuid +'&ltsRptAction=PREVIEW_AF');
			$('input[name="isFormPreviewed"]').val('true');
		});
		
		$("a#printPS").click(function(event) {
			event.preventDefault();
			var sbuid = $('input[name="sbuid"]').val();
			window.open('ltsreport.html?sbuid='+ sbuid + '&ltsRptAction=PRINT_PS');	
		});		
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
		
	});
</script>


<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>
