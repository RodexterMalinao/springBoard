<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<%@ page contentType="text/html;charset=UTF-8"%>

<c:set var="enquiry" value="${sessionScope.sessionLtsAcqOrderCapture.orderAction}" />

<c:if test="${enquiry != 'ENQUIRY' && enquiry != 'ENQUIRY_N_CANCEL' 
				&& enquiry != 'UPDATE_APPOINTMENT_FOR_QC' && enquiry != 'AFTER_SUBMIT'}">
	<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
		<jsp:param name="step" value="11" />
	</jsp:include>
</c:if>

<c:if test="${enquiry == 'AFTER_SUBMIT'}">
	<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
		<jsp:param name="step" value="12" />
	</jsp:include>
</c:if>

<c:if test="${enquiry == 'ENQUIRY_N_CANCEL'}">
	<script type="text/javascript" src="js/jquery.blockUI.js"></script>
</c:if>

<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%
	Map<String, List<CodeLkupDTO>> codeLkupList = LookupTableBean.getInstance().getCodeLkupList();
	List<CodeLkupDTO> dtos = codeLkupList.get("IPAD_VERSION");
	String ipadVersion = "";
	if (dtos != null && dtos.size() > 0) {
		ipadVersion = dtos.get(0).getCodeId();
	}
	
	String scheme = ConfigProperties.getPropertyByEnvironment("orddoc.ipad.uri.scheme");
%>

<div class="cosContent">

<form:form id="summary" method="POST" commandName="serviceSummary">
<form:hidden path="orderGeneratePenalty" />
<form:hidden id="dsOrderSubmit" path="dsOrderSubmit" />
<input type="hidden" id="orderAction" value="${enquiry}" />
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden id="preEnquiry" path="preEnquiry" />
<form:hidden id="prepayStatus" path="prepayStatus" />
<c:if test="${enquiry == 'AFTER_SUBMIT'}">
	<div id="s_line_text"><spring:message code="lts.acq.summary.done" arguments="" htmlEscape="false"/></div>
</c:if>
<c:if test="${enquiry != 'AFTER_SUBMIT'}">
	<div id="s_line_text"><spring:message code="lts.acq.summary.orderSummary" arguments="" htmlEscape="false"/></div>
</c:if>

<table width="100%" class="paper_w2" style="border-collapse: collapse; border-width: 1px; padding: 3px;" border="1" >
<tr>
<td width="100%">
	<table width="100%" border="0">
		<tr>
			<td>
				<div id="summary_tabs">
				<table width="100%" border="0" cellspacing="0">
					<c:if test="${ (enquiry != 'ENQUIRY' && enquiry != 'ENQUIRY_N_CANCEL' && enquiry != 'AFTER_SUBMIT') && !(sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13) }">
						<tr>
							<td><span class="title_a"><spring:message code="lts.acq.summary.plsReviewOrder" arguments="" htmlEscape="false"/></span></td>
						</tr>
						<tr>
							<td><span class="title_b"><spring:message code="lts.acq.summary.orderNo" arguments="" htmlEscape="false"/>  ${serviceSummary.orderId}</span></td>
						</tr>
					</c:if>
					<c:choose>
						<c:when test="${ (enquiry == 'ENQUIRY' || enquiry == 'ENQUIRY_N_CANCEL' || enquiry == 'AFTER_SUBMIT') && !(sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13) }">
						<tr>
							<td class="title_a" >
								<spring:message code="lts.acq.summary.orderNumberIs" arguments="${serviceSummary.orderId}" htmlEscape="false"/><BR>
								<c:forEach items="${serviceSummary.statusHistList}" var="statusHist">
									<c:if test="${empty statusHist.reasonCd}">	
										<spring:message code="lts.acq.summary.statusHist.order" arguments="" htmlEscape="false"/>${statusHist.sbStatus}<spring:message code="lts.acq.summary.statusHist.on" arguments="" htmlEscape="false"/>${statusHist.statusDate}<BR>
									</c:if>
									<c:if test="${not empty statusHist.reasonCd}">	
										<spring:message code="lts.acq.summary.statusHist.order" arguments="" htmlEscape="false"/>${statusHist.sbStatus}<spring:message code="lts.acq.summary.statusHist.on" arguments="" htmlEscape="false"/>${statusHist.statusDate}<spring:message code="lts.acq.summary.statusHist.withReason" arguments="" htmlEscape="false"/>${statusHist.reasonCd}<BR>
									</c:if>
								</c:forEach>
							</td>
							<td class="contenttextgary"></td>
						</tr>
						</c:when>
					</c:choose>
					<c:if test="${sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13}">
						<c:if test="${empty statusHistory}">
							<tr>
								<td><span class="title_a"><spring:message code="lts.acq.summary.plsReviewOrder" arguments="" htmlEscape="false"/></span></td>
							</tr>
							<tr>
								<td><span class="title_b"><spring:message code="lts.acq.summary.orderNo" arguments="" htmlEscape="false"/>  ${serviceSummary.orderId}</span></td>
							</tr>
							<tr>
								<td id="prepaymentSignoffDate"></td>
							</tr>
						</c:if>	
						<c:if test="${not empty statusHistory}">	
							<tr>
								<td class="title_a" id="statusDsHistoryDiv">
									<spring:message code="lts.acq.summary.orderNumberIs" arguments="${serviceSummary.orderId}" htmlEscape="false"/><BR>
									<c:forEach items="${statusHistory}" var="statusHist">
											${statusHist}<BR>
									</c:forEach>
									<div id="statusHistory"></div>
								</td>
								<td class="contenttextgary"></td>
							</tr>							
						</c:if>	
					</c:if>
					
					<c:if test="${not empty sessionScope.emailResult}">
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
				</table> 
				<c:forEach items="${serviceSummary.serviceSummaryList}" var="Summary">
					<c:if test="${not empty Summary.prepaymentItemList && serviceSummary.containPrepayment == true}">	
						<c:set var="containPrepay" value="true" />
					</c:if>
					<c:if test="${Summary.srvType == 'EYE'}">
								<c:set var="servicetype" value="${newAcqEyeServiceLabel}" />	
					</c:if>
					<c:if test="${Summary.srvType == 'DEL'}">
								<c:set var="servicetype" value="${newAcqFixedLineServiceLabel}" />	
					</c:if>
					<c:if test="${Summary.srvType == '2DEL'}">	
								<c:set var="servicetype" value="${summaryFor2ndDELLabel}" />
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
							<td colspan="4" class="table_title" id="s_line_text" ><spring:message code="lts.acq.summary.custDeails" arguments="" htmlEscape="false"/></td>
						</tr>					
						<c:choose>
  							<c:when test="${Summary.recontractInd == 'Y'}">
  								<c:if test="${not empty Summary.transfereeTitle}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td><b><spring:message code="lts.acq.summary.familyTitle" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.title}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.transfereeName}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.acq.summary.customerName" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.transfereeName}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.transfereeBlackListInd}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.acq.summary.blacklistCustomer" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.transfereeBlackListInd}</td>
									</tr>
								</c:if>								
  							</c:when>
  							<c:otherwise>
  								<c:if test="${not empty Summary.title}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td><b><spring:message code="lts.acq.summary.familyTitle" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.title}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.name}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.acq.summary.customerName" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.name}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.companyName}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.acq.summary.companyName" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.companyName}</td>
									</tr>
								</c:if>
								<c:if test="${empty Summary.companyName}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.acq.summary.dateOfBirth" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.birthday}</td>
									</tr>
								</c:if>
  							</c:otherwise>
  						</c:choose>
  						<c:choose>
							<c:when test="${Summary.recontractInd == 'Y'}">
								<c:if test="${not empty Summary.transfereeContactNum}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.acq.summary.customerContactNo" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.transfereeContactNum}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.transfereeEmail}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.acq.summary.customerContactEmail" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.transfereeEmail}</td>
									</tr>
								</c:if>
							</c:when>
							<c:otherwise>
							    <tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.customerContactEmail" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.custContactEmail}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.customerContactMobileNo" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.custMobileContactNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.customerContactFixedLineNo" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.custFixContactNum}</td>
								</tr>
							</c:otherwise>
						</c:choose>
  						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.documentTypeID" arguments="" htmlEscape="false"/> </b></td>
							<c:choose>
  								<c:when test="${Summary.recontractInd == 'Y'}">
  									<td colspan="2" class="">${Summary.transfereeDocType}: ${Summary.transfereeDocNum}</td>
  								</c:when>
  								<c:otherwise>
  									<td colspan="2" class="">${Summary.docType}: ${Summary.docNum}</td>
								</c:otherwise>
  							</c:choose>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.installContactName" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.apptContactName}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.installContactNo" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.apptContactNum}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.installContactMobileNo" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.apptMobileContactNum}</td>
						</tr>

						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.installAddress" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.installAddr}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"/>
					<!-- 	<c:choose>
								<c:when test="${Summary.extRelInd == 'Y'}">
									<td colspan="2" class="">External relocation</td>
								</c:when>
								<c:otherwise>
									<td colspan="2" class="">Same as existing installation address</td>
								</c:otherwise>
							</c:choose> -->	
						</tr>
						<c:forEach items="${Summary.summaryBillAddrDtlList}" var="billingAddr" varStatus="baStatus">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.billingAddress" arguments="" htmlEscape="false"/> ${baStatus.count}: </b></td>
							<c:choose>
								<c:when test="${billingAddr.billAddrChangeInd == 'I'}">
									<td colspan="2" class=""><spring:message code="lts.acq.summary.alignWithInstallAddress" arguments="" htmlEscape="false"/></td>
									<c:if test="${billingAddr.billingAddrInstantUpdateInd == 'Y'}">
										<tr class="contenttext">
											<td width="6%"/>
											<td width="25%"/>
											<td colspan="2" class=""><spring:message code="lts.acq.summary.instantUpdateBillAddress" arguments="" htmlEscape="false"/></td>
										</tr>
									</c:if>
								</c:when>
								<c:when test="${billingAddr.billAddrChangeInd == 'N'}">
									<td colspan="2" class=""><spring:message code="lts.acq.summary.newBillAddress" arguments="" htmlEscape="false"/></td><p>
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"/>
										<td colspan="2" class="">${billingAddr.billingAddress}</td>
									</tr>
									<c:if test="${billingAddr.billingAddrInstantUpdateInd == 'Y'}">
										<tr class="contenttext">
											<td width="6%"/>
											<td width="25%"/>
											<td colspan="2" class=""><spring:message code="lts.acq.summary.instantUpdateBillAddress" arguments="" htmlEscape="false"/></td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
										<td colspan="2" class="">${billingAddr.billingAddress}</td>
								</c:otherwise>
							</c:choose>
						</tr>
						</c:forEach>
					</table>
					<br>
					<c:if test="${Summary.recontractInd == 'Y'}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text">RECONTRACT DETAILS</td>
							</tr>					
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td><b>Transferor Family Title: </b></td>
								<td colspan="2" class="">${Summary.transferorTitle}</td>
							</tr>
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b>Transferor Customer Name: </b></td>
								<td colspan="2" class="">${Summary.transferorName}</td>
							</tr>
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b>Transferor Document Type / ID: </b></td>
								<td colspan="2" class="">${Summary.transferorDocType}: ${Summary.transferorDocNum}</td>
  							</tr>
  							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b>Relationship: </b></td>
								<td colspan="2" class="">${Summary.transfereeRelationship}</td>
  							</tr>
  							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b>Transfer Mode: </b></td>
								<td colspan="2" class="">${Summary.recontractMode}</td>
  							</tr>  							
  							<c:if test="${not empty Summary.recontractOptCallCardInd}">
  								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>Global Calling Card Service: </b></td>
									<td colspan="2" class="">${Summary.recontractOptCallCardInd}</td>
  								</tr>
  							</c:if>
  							<c:if test="${not empty Summary.recontractOptMobIddInd}">
  								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>Mobile IDD0060 Service: </b></td>
									<td colspan="2" class="">${Summary.recontractOptMobIddInd}</td>
  								</tr>
  							</c:if>
  							<c:if test="${not empty Summary.recontractOptFixedIddInd}">
  								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>IDD0060 Fixed Fee Service: </b></td>
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
							<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.coreServices" arguments="" htmlEscape="false"/></td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.fixedTerm" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.contractPeriod}</td>
						</tr>
						<c:if test="${not empty Summary.prewiringDate}">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.preWiringDate" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.prewiringDate}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.preWiringTime" arguments="" htmlEscape="false"/></b></td>
							<td colspan="2" class="">${Summary.prewiringTime}</td>
						</tr>
						</c:if>
						<c:if test="${not empty Summary.preinstallItemList}">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.targetPreinstallDate" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.installDate}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.targetPreinstallTime" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.installTime}</td>
						</tr>
						</c:if>
						<c:if test="${empty Summary.preinstallItemList}">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.targetInstallDate" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.installDate}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.targetInstallTime" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.installTime}</td>
						</tr>
						</c:if>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.targetTelephoneSwitchingDate" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.switchDate}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.applicationDate" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.applDate}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.serviceNumber" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.srvNum}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.exDirectory" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.exDirInd}</td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.directoryName" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.exDirName}</td>
						</tr>
					</table>
					<br>
					<c:if test="${not empty Summary.srvPlanItemList}">
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
												<th width="20%" class="" align="center"><b><spring:message code="lts.acq.summary.monthlyFixedTermRate" arguments="" htmlEscape="false"/></b></th>
												<th width="20%" class="" align="center"><b><spring:message code="lts.acq.summary.monthToMonthRate" arguments="" htmlEscape="false"/></b></th>
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
							<td class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.premiumNServicesNVAS" arguments="" htmlEscape="false"/></td>
						</tr>
					</table>
					<table cellspacing="1" class="table_style_sb" width="100%" >
						<tr>
							<th colspan="2" width="56%" class="contenttext">&nbsp;</th>
							<th width="20%" class="" align="center"><b><spring:message code="lts.acq.summary.monthlyFixedTermRate" arguments="" htmlEscape="false"/></b></th>
							<th width="20%" class="" align="center"><b><spring:message code="lts.acq.summary.monthToMonthRate" arguments="" htmlEscape="false"/></b></th>
						</tr>
						<c:if test="${not empty Summary.bbRentalItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.broadbandLineRental" arguments="" htmlEscape="false"/></b></td>
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
						<c:if test="${not empty Summary.erChargeList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.externalRelocationCharges" arguments="" htmlEscape="false"/></b></td>
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.otherCharges" arguments="" htmlEscape="false"/></b></td>
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.parallelPhoneLine" arguments="" htmlEscape="false"/></b></td>
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
											Number of Socket(s): ${socketItem.itemQty}
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.optionalAccessories" arguments="" htmlEscape="false"/></b></td>
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.infoServicesAvailableOnEye" arguments="" htmlEscape="false"/></b></td>
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.MOOVonEyeHomeSmartphone" arguments="" htmlEscape="false"/></b></td>
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.nowTVservicesForNonNowTVCust" arguments="" htmlEscape="false"/></b></td>
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.channel" arguments="" htmlEscape="false"/> </b></td>
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
						<c:if test="${not empty Summary.billItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.billingOption" arguments="" htmlEscape="false"/></b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.billItemList}" var="billItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${billItem.itemDisplayHtml}
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
						</c:if>
						
						<c:if test="${not empty Summary.nowtvBillItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.billingOptionOfNowTV" arguments="" htmlEscape="false"/></b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.nowtvBillItemList}" var="nowtvBillItem">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${nowtvBillItem.itemDisplayHtml}
										<c:if test="${nowtvBillItem.itemType == 'TV-EMAIL'}">
											<br><spring:message code="lts.acq.summary.emailAddress" arguments="" htmlEscape="false"/>: ${Summary.emailBillAddress}
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.VASForEyeDevice" arguments="" htmlEscape="false"/></b></td>
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.newVAS" arguments="" htmlEscape="false"/></b></td>
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.iddService" arguments="" htmlEscape="false"/></b></td>
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
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.optionalPremium" arguments="" htmlEscape="false"/></b></td>
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
						<c:if test="${not empty Summary.prewiringItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.prewiring" arguments="" htmlEscape="false"/></b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.prewiringItemList}" var="prewiringItemList">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${prewiringItemList.itemDisplayHtml}
										<c:if test="${not empty prewiringItemList.itemAttbs}">
											<c:forEach items="${prewiringItemList.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${prewiringItemList.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${prewiringItemList.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.preinstallItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.preInstallationVAS" arguments="" htmlEscape="false"/></b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.preinstallItemList}" var="preinstallItemList">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${preinstallItemList.itemDisplayHtml}
										<c:if test="${not empty preinstallItemList.itemAttbs}">
											<c:forEach items="${preinstallItemList.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${preinstallItemList.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${preinstallItemList.mthToMthAmtTxt}</td>
								</tr>
							</c:forEach>
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.ffpItemList}">
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.fixedFeePlan" arguments="" htmlEscape="false"/></b></td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
								<td width="20%" class=" bg_grey" align="center">&nbsp;</td>
							</tr>
							<c:forEach items="${Summary.ffpItemList}" var="ffpItemList">
								<tr valign="top">
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext">${ffpItemList.itemDisplayHtml}
										<c:if test="${not empty ffpItemList.itemAttbs}">
											<c:forEach items="${ffpItemList.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
									</td>
									<td width="20%" class=" bg_grey" align="center">${ffpItemList.recurrentAmtTxt}</td>
									<td width="20%" class=" bg_grey" align="center">${ffpItemList.mthToMthAmtTxt}</td>
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
							<td colspan="4"></td>
						   </tr>
						   <tr>
							<td class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.smartWarranty" arguments="" htmlEscape="false"/></td>
						   </tr>
					     </table>						
					      <table cellspacing="1" class="table_style_sb" width="100%" >
							
							<tr>
							 <th colspan="2" width="56%" class="contenttext">&nbsp;</th>
							 <th width="20%" class="" align="center"><b><spring:message code="lts.acq.summary.monthlyFixedTermRate" arguments="" htmlEscape="false"/></b></th>
							<th width="20%" class="" align="center"><b><spring:message code="lts.acq.summary.monthToMonthRate" arguments="" htmlEscape="false"/></b></th>
						    </tr>							
							<tr valign="top">
								<td width="6%">&nbsp;</td>
								<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.deviceWarranty" arguments="" htmlEscape="false"/></b></td>
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
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.giftsNPremiums" arguments="" htmlEscape="false"/></td>
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
							<c:if test="${not empty serviceSummary.pcdSbid}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="55%"><b><spring:message code="lts.acq.summary.pCDSBID" arguments="" htmlEscape="false"/></b></td>
								<td colspan="2" class="">${serviceSummary.pcdSbid}</td>
							</tr>
							</c:if>
						</table>
						<br>
					</c:if>
					<c:if test="${not empty Summary.redemMedia}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.redemptionMediaAllCap" arguments="" htmlEscape="false"/></td>
							</tr>
							<tr><td colspan="4">&nbsp;</td></tr>
							<tr>
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.redemptionMedia" arguments="" htmlEscape="false"/></b></td> 
								<td colspan="2">
								<c:if test="${Summary.redemMedia == 'P'}">
									<spring:message code="lts.acq.summary.postal" arguments="" htmlEscape="false"/>
								</c:if>
								<c:if test="${Summary.redemMedia == 'S'}">
									<spring:message code="lts.acq.summary.email" arguments="" htmlEscape="false"/>: ${Summary.redemptionEmail}
								</c:if>
								<c:if test="${Summary.redemMedia == 'M'}">
									<spring:message code="lts.acq.summary.SMS" arguments="" htmlEscape="false"/>: ${Summary.redemptionSms}
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
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.billPreferences" arguments="" htmlEscape="false"/></td>
							</tr>
							<c:if test="${not empty Summary.billItemList}">
								<tr>
									<td width="6%">&nbsp;</td>
									<td><b><spring:message code="lts.acq.summary.billingOption" arguments="" htmlEscape="false"/></b></td>
								</tr>
								<c:forEach items="${Summary.billItemList}" var="billItem">
									<tr>
										<td width="6%">&nbsp;</td>
										<td>${billItem.itemDisplayHtml}
										<c:if test="${billItem.itemType == 'EMAILBILL'}">
										    <c:forEach items="${Summary.ltsEmailBillAddr}" var="billEmail" varStatus="emailStatus">
											    <br><spring:message code="lts.acq.summary.emailAddress" arguments="" htmlEscape="false"/> ${emailStatus.count}: ${billEmail}
											</c:forEach>
										</c:if>
										<c:if test="${billItem.itemType == 'PAPER-BILL' || billItem.itemType == 'PAPER-W' || billItem.itemType == 'PAPER-G'}">
										    <c:if test="${not empty Summary.paperBillCharge}">
										            <br><br><spring:message code="lts.acq.summary.paperBillCharing" arguments="" htmlEscape="false"/> ${Summary.paperBillCharge}
										    </c:if>
											<c:if test="${not empty Summary.paperBillWaiveRea}">
													<br><spring:message code="lts.acq.summary.paperBillWaiveReason" arguments="" htmlEscape="false"/> ${Summary.paperBillWaiveRea}
											</c:if>										    
											<c:if test="${not empty Summary.paperBillWaiveRea}">
													<br><spring:message code="lts.acq.summary.paperBillWaiveRemarks" arguments="" htmlEscape="false"/> ${Summary.paperBillWaiveRemarks}
											</c:if>										</c:if>
										<c:if test="${billItem.itemType == 'MYHKT-BILL' || billItem.itemType == 'THE-CLUB' || billItem.itemType == 'MYHKT-CLUB' }">
										   <c:if test="${not empty Summary.cspEmail}">
										   		<br><spring:message code="lts.acq.summary.emailAddress" arguments="" htmlEscape="false"/>: ${Summary.cspEmail}
										   	</c:if>
										   	<c:if test="${not empty Summary.cspMobile}">
												<br><spring:message code="lts.acq.summary.mobileNumber" arguments="" htmlEscape="false"/> ${Summary.cspMobile}
											</c:if>
										</c:if>
										<c:if test="${not empty billItem.itemAttbs}">
											<c:forEach items="${billItem.itemAttbs}" var="attb">
												<br>${attb.attbDesc}: ${attb.attbValue} 
											</c:forEach>
										</c:if>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td width="6%">&nbsp;</td>
									<td>
										<c:if test="${serviceSummary.carecashInd == 'I'}">
											<spring:message code="lts.acq.summary.registerIguardCarecash" arguments="" htmlEscape="false"/><br/>
											<spring:message code="lts.acq.summary.emailAddress" arguments="" htmlEscape="false"/>: ${serviceSummary.carecashEmail}<br/>
											<spring:message code="lts.acq.summary.mobileNumber" arguments="" htmlEscape="false"/> ${serviceSummary.carecashContactNum}<br/>
										</c:if>
										<c:if test="${serviceSummary.carecashInd == 'O'}">
											<spring:message code="lts.acq.summary.notRegisterIguardCarecash" arguments="" htmlEscape="false"/><br/>
										</c:if>
										<c:if test="${serviceSummary.carecashInd == 'P'}">
											<spring:message code="lts.acq.summary.decideRegIguardCarecashLater" arguments="" htmlEscape="false"/><br/>
										</c:if>
									</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td class="contenttext">&nbsp;</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.nowtvBillItemList}">
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%" class="contenttext"><b><spring:message code="lts.acq.summary.billingOptionOfNowTV" arguments="" htmlEscape="false"/></b></td>
								</tr>
								<c:forEach items="${Summary.nowtvBillItemList}"
									var="nowtvBillItem">
									<tr>
										<td width="6%">&nbsp;</td>
										<c:choose>
											<c:when test="${nowtvBillItem.itemType == 'TV-EMAIL'}">
												<td width="50%" class="contenttext">- ${nowtvBillItem.itemDisplayHtml}<BR><spring:message code="lts.acq.summary.emailAddress" arguments="" htmlEscape="false"/>: ${Summary.emailBillAddress}</td>
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
							<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.paymentInfo" arguments="" htmlEscape="false"/></td>
						</tr>
						<c:forEach items="${Summary.summaryPayMtdDtlList}" var="summaryPayMtdDtlList" varStatus="payMtdstatus">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.accountNo" arguments="" htmlEscape="false"/> ${payMtdstatus.count}: </b></td>
							<td colspan="2" class="">${summaryPayMtdDtlList.acctNum}</td>
						</tr>
						
						<c:if test="${summaryPayMtdDtlList.paymentChangeInd=='Y'}">
							<c:if test="${summaryPayMtdDtlList.paymentMethod=='C'}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.cardHolderName" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.holderName}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.creditCardType" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.credCardType}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.cardHolderDocTypeNNo" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.holderIdType}: ${summaryPayMtdDtlList.holderIdNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.creditCardNo" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.credCardNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.expiryDate" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.credCardExpDate}</td>
								</tr>
							</c:if>
							<c:if test="${summaryPayMtdDtlList.paymentMethod=='A'}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.bankAccountHolderName" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.holderName}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.bankAccountHolderDocTypeNNo" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.holderIdType}: ${summaryPayMtdDtlList.holderIdNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.bankCode" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.bankCd}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.branchCode" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.bankBranchCd}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.bankAccountNo" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.bankAcctNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.autopayUpperLimit" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.autoPayLimit}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.applicationDate" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${summaryPayMtdDtlList.bankApplDate}</td>
								</tr>
							</c:if>
							<c:if test="${summaryPayMtdDtlList.paymentMethod=='M'}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.billSelection" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class=""><spring:message code="lts.acq.summary.cash" arguments="" htmlEscape="false"/></td>
								</tr>
							</c:if>
						</c:if>
						<c:if test="${summaryPayMtdDtlList.paymentChangeInd=='N'}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.existingPCCWPaymentMethod" arguments="" htmlEscape="false"/> </b></td>
								<c:if test="${summaryPayMtdDtlList.paymentMethod=='C'}">
									<td colspan="2" class=""><spring:message code="lts.acq.summary.creditCard" arguments="" htmlEscape="false"/></td>
								</c:if>
								<c:if test="${summaryPayMtdDtlList.paymentMethod=='A'}">
									<td colspan="2" class=""><spring:message code="lts.acq.summary.bankAutopay" arguments="" htmlEscape="false"/></td>
								</c:if>
								<c:if test="${summaryPayMtdDtlList.paymentMethod=='M'}">
									<td colspan="2" class=""><spring:message code="lts.acq.summary.cash" arguments="" htmlEscape="false"/></td>
								</c:if>
							</tr>
						</c:if>
						<c:if test="${summaryPayMtdDtlList.thirdPtyPayment == true}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.thirdPartyPayment" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">Yes</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.prepaymentItemList}">
							<c:forEach items="${Summary.prepaymentItemList}" var="prepaymentItem">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%" valign="top"><b><spring:message code="lts.acq.summary.prepayment" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${prepaymentItem.itemDisplayHtml}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.prepaymentAccount" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.racctNum}</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${not empty Summary.memoNum}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.salesMemoNumber" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.memoNum}</td>
							</tr>
						</c:if>
						</c:forEach>
					</table>
					<br>
					<c:if test="${not empty Summary.thirdPtyName || not empty Summary.thirdPtyDocId || not empty Summary.thirdPtyDocType || not empty Summary.thirdPtyContactNum || not empty Summary.thirdPtyRelation}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3"
							class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.thirdParty" arguments="" htmlEscape="false"/></td>
							</tr>
							<c:if test="${not empty Summary.thirdPtyName}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.thirdPartyName" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.thirdPtyName}</td>
								</tr>
							</c:if>
							<c:if
								test="${not empty Summary.thirdPtyDocType && not empty Summary.thirdPtyDocId}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.documentTypeNID" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.thirdPtyDocType}:
										${Summary.thirdPtyDocId}</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.thirdPtyContactNum}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.thirdPartyContactNum" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.thirdPtyContactNum}</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.thirdPtyRelation}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.relationship" arguments="" htmlEscape="false"/> </b></td>
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
								<td colspan="4" class="table_title" id="s_line_text">Effective Price Comparison</td>
							</tr>
							<tr>
								<td colspan="4">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4" align="center">
									<table width="100%" border="1" cellpadding="0" cellspacing="0" >
										<tr bgcolor="#848484">
											<td width="50%" align="center"><b>Effective Price Before Upgrade</b></td>
											<td width="50%" align="center"><b>Future Effective Price</b></td>
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
					<c:if test="${Summary.pipb}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
							<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.PIPBInfo" arguments="" htmlEscape="false"/></td>
							</tr>
							<c:if test="${Summary.pipbReuseExSocketInd == 'Y'}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.reuseExistingSocket" arguments="" htmlEscape="false"/></b></td>
								<c:if test="${Summary.pipbReuseExSocketType == 'F'}">
								<td colspan="2" class=""><spring:message code="lts.acq.summary.reuseYourFormerFixedLineSocket" arguments="" htmlEscape="false"/></td>
								</c:if>
								<c:if test="${Summary.pipbReuseExSocketType == 'P'}">
								<td colspan="2" class=""><spring:message code="lts.acq.summary.reuseOurParallelPhoneLineSocket" arguments="" htmlEscape="false"/></td>
								</c:if>
							</tr>
						    </c:if>
						    <c:if test="${Summary.pipbWaiveDnChangeInd == 'Y'}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b>&nbsp;</b></td>
								<td colspan="2" class=""><spring:message code="lts.acq.summary.waiveChargeForDnChange" arguments="" htmlEscape="false"/></td>
							</tr>
						    </c:if>							
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.PIPBDN" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.pipbDn}</td>
							</tr>
							<c:if test="${not empty Summary.pipbOperator2n}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.2NPortingFrom" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.pipbOperator2n}</td>
							</tr>
						    </c:if>
						    <c:if test="${not empty Summary.preinstallItemList}">
						    <tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.targetInstallActDate" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.pipbInstallDate}</td>
						    </tr>
						    <tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.targetInstallActTime" arguments="" htmlEscape="false"/></b></td>
								<td colspan="2" class="">${Summary.pipbInstallTime}</td>
						    </tr>
						    </c:if>
						    <c:if test="${empty Summary.preinstallItemList}">
						    <tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.targetInstallDate" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.pipbInstallDate}</td>
						    </tr>
						    <tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.targetInstallTime" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.pipbInstallTime}</td>
						    </tr>
						    </c:if>
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.targetTelephoneSwitchingDate" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.pipbSwitchDate}</td>
							</tr>
						    <c:if test="${Summary.pipbFromDiffCustInd == 'Y'}">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.documentTypeOrID" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.pipbIdDocType}: ${Summary.pipbIdDocNum}</td>
								</tr>
								<c:if test="${not empty Summary.pipbTitle}">
										<tr class="contenttext">
											<td width="6%">&nbsp;</td>
											<td><b><spring:message code="lts.acq.summary.familyTitle" arguments="" htmlEscape="false"/> </b></td>
											<td colspan="2" class="">${Summary.pipbTitle}</td>
										</tr>
								</c:if>
								<c:if test="${not empty Summary.pipbName}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.acq.summary.customerName" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.pipbName}</td>
									</tr>
								</c:if>
								<c:if test="${not empty Summary.pipbCompanyName}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%"><b><spring:message code="lts.acq.summary.companyName" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.pipbCompanyName}</td>
									</tr>
								</c:if>
						    </c:if>
						    <c:if test="${Summary.pipbFromDiffAddrInd == 'Y'}">
						        <c:if test="${not empty Summary.pipbAddr}">
									<tr class="contenttext">
										<td width="6%">&nbsp;</td>
										<td width="25%" valign="top"><b><spring:message code="lts.acq.summary.PIPBAddress" arguments="" htmlEscape="false"/> </b></td>
										<td colspan="2" class="">${Summary.pipbAddr}</td>
									</tr>
							    </c:if>
						    </c:if>
						    <c:if test="${Summary.pipbDuplexInd == 'Y'}">
						    <tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.pipbDuplexAction" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.pipbDuplexAction}</td>
							</tr>
							<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.pipbDuplexDn" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.pipbDuplexDn}</td>
							</tr>
						    </c:if>
						
						</table>
					</c:if>
					<c:if test="${not empty Summary.epdItemList}">
						<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
						<tr></tr>
							<tr>
								<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.EDP" arguments="" htmlEscape="false"/></td>
							</tr>
							<c:forEach items="${Summary.epdItemList}" var="epdItemList">
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.epdOption" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${epdItemList.itemDesc}</td>
								</tr>
								<c:forEach items="${epdItemList.itemAttbs}" var="itemAttbs">
								    <c:if test="${not empty itemAttbs.attbValue}">
										<tr class="contenttext">
											<td width="6%">&nbsp;</td>
											<td width="25%"><b>${itemAttbs.attbDesc}:</b></td>
											<td colspan="2" class="">${itemAttbs.attbValue}</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:forEach>
						</table>					
					</c:if>
					<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
						<tr></tr>
						<tr>
							<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.internalUseForSerAllCap" arguments="" htmlEscape="false"/></td>
						</tr>
						<c:if test="${not empty Summary.eyeArrangement}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.eyeArrangement" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.eyeArrangement}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.fsaArrangement}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.FSAArrangement" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.fsaArrangement}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.relatedFSA}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.sharedFSA" arguments="" htmlEscape="false"/> </b></td>
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
								<td width="25%"><b><spring:message code="lts.acq.summary.relatedFSASBOrderID" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.imsSbOrder}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.edfNo}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.EDFNo" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.edfNo}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.modemArrangement}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.modemArrangement" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.modemArrangement}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.mirrorFsa}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.mirrorFSA" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.mirrorFsa}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.nowtvSrvType}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.nowTVServiceType" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.nowtvSrvType}</td>
							</tr>
						</c:if>
						
						
						<c:if test="${not empty Summary.srvTypeChange}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.serviceType" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.srvTypeChange}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.bandwidthChange}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.bandwidth" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.bandwidthChange}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.technologyChange}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.technology" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.technologyChange}</td>
							</tr>
						</c:if>
						
						<c:if test="${not empty Summary.optOutItemList}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.optOutItemDesc" arguments="" htmlEscape="false"/> </b></td>
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
								<td width="25%"><b><spring:message code="lts.acq.summary.workQueueType" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.workQueueType}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.pendingLtsOcid}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.pendingLTSOCID" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.pendingLtsOcid}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.pendingImsOcid}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.pendingIMSOCID" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.pendingImsOcid}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.cancelDuplexType}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.cancelX" arguments="${Summary.cancelDuplexType}" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.cancelDuplexDn}</td>
							</tr>
						</c:if>
						<c:if test="${Summary.blacklistAddrInd == true}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.blacklistAddress" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">Yes</td>
							</tr>
						</c:if>
						<c:if test="${Summary.fsaExtRelInd == true}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.otherCustomerRequest" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class=""><spring:message code="lts.acq.summary.fsaRelocation" arguments="" htmlEscape="false"/></td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.dummyDocNum}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.dummyDocumentTypeID" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.dummyDocType} : ${Summary.dummyDocNum}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.staffPlanApplicantId}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.staffPlanApplicantID" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class="">${Summary.staffPlanApplicantId}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.outLtsItemList}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%" valign="top"><b><spring:message code="lts.acq.summary.outLTSItemDesc" arguments="" htmlEscape="false"/> </b></td>
								<td valign="top" align="left">
									<table class="table_style " border="1">
										<tr class="contenttext">
											<th colspan="2" width="25%" ><b><spring:message code="lts.acq.summary.itemDescription" arguments="" htmlEscape="false"/></b></th>
											<th colspan="2" width="25%" ><b><spring:message code="lts.acq.summary.penaltyHandling" arguments="" htmlEscape="false"/></b></th>
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
								<td width="25%"><b><spring:message code="lts.acq.summary.outIMSItemDesc" arguments="" htmlEscape="false"/> </b></td>
								<td valign="top" align="left">
									<table id="table_style1" class="paper_no ">
										<tr class="contenttext">
											<th colspan="2" width="25%" ><b><spring:message code="lts.acq.summary.itemDescription" arguments="" htmlEscape="false"/></b></th>
											<th colspan="2" width="25%" ><b><spring:message code="lts.acq.summary.penaltyHandling" arguments="" htmlEscape="false"/></b></th>
										</tr>
										<c:forEach items="${Summary.outImsItemList}" var="outItem">
											<tr class="contenttext">
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
								<td width="25%"><b><spring:message code="lts.acq.summary.orderRemarks" arguments="" htmlEscape="false"/> </b></td>
								<tr>
									<td align="center" colspan="4" width="100%">
										<textarea rows="10" cols="100%" readonly="readonly">${Summary.orderRemarks}</textarea>
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.addOnRemarks}">
								<td width="25%"><b><spring:message code="lts.acq.summary.fieldServiceAddonRemarks" arguments="" htmlEscape="false"/> </b></td>
								<tr>
									<td align="center" colspan="4" width="100%">
										<textarea rows="10" cols="100%" readonly="readonly">${Summary.addOnRemarks}</textarea>
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.custRemarks}">
								<td width="25%"><b><spring:message code="lts.acq.summary.customerRemarks" arguments="" htmlEscape="false"/> </b></td>
								<tr>
									<td align="center" colspan="4" width="100%">
										<textarea rows="10" cols="100%" readonly="readonly">${Summary.custRemarks}</textarea>
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.earliestSrdReasonRemarks}">
								<td width="25%"><b><spring:message code="lts.acq.summary.earliestSRDReason" arguments="" htmlEscape="false"/> </b></td>
								<tr>
									<td align="center" colspan="4" width="100%">
										<textarea rows="10" cols="100%" readonly="readonly">${Summary.earliestSrdReasonRemarks}</textarea>
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty Summary.fsRemark}">
								<td width="25%"><b><spring:message code="lts.acq.summary.FandSInstructions" arguments="" htmlEscape="false"/> </b></td>
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
						<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.internalUseAllCap" arguments="" htmlEscape="false"/></td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.acq.summary.staffNo" arguments="" htmlEscape="false"/> </b></td>
						<td colspan="2" class="">${serviceSummary.staffNum}</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.acq.summary.salesmenCode" arguments="" htmlEscape="false"/> </b></td>
						<td colspan="2" class="">${serviceSummary.salesCd}</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.acq.summary.salesChannel" arguments="" htmlEscape="false"/> </b></td>
						<td colspan="2" class="">${serviceSummary.salesChannel}</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.acq.summary.teamCode" arguments="" htmlEscape="false"/> </b></td>
						<td colspan="2" class="">${serviceSummary.salesTeam}</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.acq.summary.salesContactNo" arguments="" htmlEscape="false"/> </b></td>
						<td colspan="2" class="">${serviceSummary.salesContactNum}</td>
					</tr>
					<c:if test="${not empty serviceSummary.salesPosition}">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.position" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${serviceSummary.salesPosition}</td>
						</tr>
					</c:if>
					<c:if test="${not empty serviceSummary.salesJob}">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.job" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${serviceSummary.salesJob}</td>
						</tr>
					</c:if>
					<c:if test="${not empty serviceSummary.salesProcessDate}">
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.processDate" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${serviceSummary.salesProcessDate}</td>
						</tr>
					</c:if>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.acq.summary.orderCreateBy" arguments="" htmlEscape="false"/> </b></td>
						<td colspan="2" class="">${serviceSummary.createBy}</td>
					</tr>
					<tr class="contenttext">
						<td width="6%">&nbsp;</td>
						<td width="25%"><b><spring:message code="lts.acq.summary.legacyID" arguments="" htmlEscape="false"/> </b></td>
						<td colspan="2" class="">${serviceSummary.legacyId}</td>
					</tr>
				</table> <br>
				<c:forEach items="${serviceSummary.serviceSummaryList}" var="Summary">
				<c:if test="${Summary.srvType == 'EYE' || Summary.srvType == 'DEL'}">
				<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
				<tr></tr>
						<tr>
							<td colspan="5" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.eSignInfoAllCap" arguments="" htmlEscape="false"/></td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.distributionMode" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.disMode == 'E'? disModeDigital: 
																		Summary.disMode == 'P'? disModePaper:
																		Summary.disMode}</td>
						</tr>
						<c:if test="${not empty Summary.esigEmailAddr}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b>Email : </b></td>
								<td colspan="2" class="">
									<span id="esigEmailAddr" style="color: red; font-weight: bold;">${Summary.esigEmailAddr}</span>
									</td>
							</tr>
						</c:if>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%"><b><spring:message code="lts.acq.summary.applicationFormLanguage" arguments="" htmlEscape="false"/> </b></td>
							<td colspan="2" class="">${Summary.esigEmailLang == 'ENG'? esigEmailLangEng: 
																		Summary.esigEmailLang == 'CHN'? esigEmailLangChi: 
																		Summary.esigEmailLang}</td>
						</tr>
						<c:if test="${not empty serviceSummary.smsNo}">
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="25%"><b><spring:message code="lts.acq.summary.SMSNo" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class=""><span id="smsNo">${serviceSummary.smsNo}</span></td>
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
												<tr>
													<th width="20%"><spring:message code="lts.acq.summary.documentType" arguments="" htmlEscape="false"/></th>
													<th width="40%"><spring:message code="lts.acq.summary.waiveReason" arguments="" htmlEscape="false"/></th>
													<th width="20%"><spring:message code="lts.acq.summary.collected" arguments="" htmlEscape="false"/></th>
													<th width="15%"><spring:message code="lts.acq.summary.mandatoryBeforeSignoff" arguments="" htmlEscape="false"/></th>
													<th width="20%"><spring:message code="lts.acq.summary.faxSerialNumber" arguments="" htmlEscape="false"/></th>
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
					
					<c:if test="${ (sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13) }">
						<table id="qcInfo" width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext" style="display:block">
							<tr></tr>
							<tr>
								<td colspan="5" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.qcRemark" arguments="" htmlEscape="false"/></td>
							</tr>					
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<form:errors path="qcCallTimePeriod" cssClass="error" /> 
								<td width="25%"><b><spring:message code="lts.acq.summary.qcCallingTimePeriod" arguments="" htmlEscape="false"/> </b></td>
								<td colspan="2" class=""><form:textarea id="qcCallTimePeriod" path="qcCallTimePeriod" rows="6" cols="100" cssStyle="resize:none;"></form:textarea></td>
								<!--<td colspan="2" class=""><form:input id="qcCallTimePeriod" path="qcCallTimePeriod" /></td>-->
							</tr>
							<tr class="contenttext">
								<td width="6%">&nbsp;</td>
								<td width="15%"><b><spring:message code="lts.acq.summary.waiveQC" arguments="" htmlEscape="false"/> </b></td>
								<td class="">
									<form:select path="waiveQc" id="waiveQc">
									   <form:option value="" label='--- Select ---'/>
									   <form:option value="V" label='VIP' />
									   <form:option value="F" label='Foreign Language' />
									   <form:option value="M" label='Mute' />
									   <form:option value="D" label='Deaf' />
									<%-- <form:option value="" label="${statusHist.sbStatus}" /> --%>
									</form:select>								
								</td>
							</tr>
						</table>
						<c:if test="${not empty Summary.prepaymentItemList && serviceSummary.containPrepayment == true}">				
							<table id="prepaymentInfo" width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext" >
								<tr></tr>
								<tr>
									<td colspan="5" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.cashPrepayInfoAllCap" arguments="" htmlEscape="false"/></td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.accountNumber" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${Summary.racctNum}</td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.amount" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class="">${serviceSummary.prepayAmount}</td>
								</tr>							
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="25%"><b><spring:message code="lts.acq.summary.shopCode" arguments="" htmlEscape="false"/> </b></td>
									<td colspan="2" class=""><form:input id="prepayShopCode" path="prepayShopCode" /></td>
								</tr>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td width="15%"><b><spring:message code="lts.acq.summary.transactionCode" arguments="" htmlEscape="false"/> </b></td>
									<td class=""><form:input id="prepayTransactionCode" path="prepayTransactionCode" /></td>
									<td width="10%"><b><spring:message code="lts.acq.summary.cancelReasonCode" arguments="" htmlEscape="false"/> </b></td>
									<td class="">
										<form:select path="prepayCancelReasonCode" id="prepayCancelReasonCode">
										   <form:option value="" label="--- Select ---"/>
										   <form:options items="${cancelReasonCodeList}" itemValue="itemKey" itemLabel="itemValue"/>
										</form:select>								
									</td>
								</tr>
								
								<c:choose>
									<c:when test="${ not empty serviceSummary.prepayDate && serviceSummary.prepayDate != '' }">		
											<tr class="contenttext">
											<td width="6%">&nbsp;</td>
											<td width="25%"><b><spring:message code="lts.acq.summary.prepaySettleDate" arguments="" htmlEscape="false"/> </b></td>									
											<td> ${serviceSummary.prepayDate} </td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr class="contenttext">
											<td width="6%">&nbsp;</td>
											<td width="25%"><b><spring:message code="lts.acq.summary.prepaySettleDateIn24HrFormat" arguments="" htmlEscape="false"/> </b></td>									
											<td>
												<form:input id="prepayDateYear" path="prepayDateYear" size="4" maxlength="4" /><b>/</b><form:input id="prepayDateMonth" path="prepayDateMonth" size="2" maxlength="2"/><b>/</b><form:input id="prepayDateDay" path="prepayDateDay" size="2" maxlength="2"/>
												<b>(YYYY/MM/DD)</b>
											</td>
										</tr>
										<tr class="contenttext">
											<td width="6%">&nbsp;</td>
											<td width="25%">&nbsp;</td>									
											<td>
												<form:input id="prepayTimeHour" path="prepayTimeHour" size="2" maxlength="2" /><b>:</b><form:input id="prepayTimeMin" path="prepayTimeMin" size="2" maxlength="2"/><b>:</b><form:input id="prepayTimeSec" path="prepayTimeSec" size="2" maxlength="2"/>
												<b>(HH:MI:SS)</b>
											</td>
										</tr>										
									</c:otherwise>
								</c:choose>
								<tr class="contenttext">
									<td width="6%">&nbsp;</td>
								</tr>								
								<tr class="contenttext">	
									<td width="6%">&nbsp;</td>
									<td colspan="3">							
										<a id="printPS" name="printPS" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.viewPaymentSlip" arguments="" htmlEscape="false"/></div></a>
									</td>
								</tr>
							</table>
						</c:if>
					</c:if>
					</c:forEach>
					<br>
					<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext" style="display:block">
				    <tr></tr>
						<tr>
							<td colspan="5" class="table_title" id="s_line_text"><spring:message code="lts.acq.summary.suspendRemarkAllCap" arguments="" htmlEscape="false"/></td>
						</tr>
						<tr class="contenttext">
							<td width="6%">&nbsp;</td>
							<td width="25%">&nbsp;</td>
							<td colspan="2" class="">
								<form:textarea path="suspendRemarks" rows="6" cols="100" cssStyle="resize:none;"></form:textarea>
						    </td>
						</tr>
						<tr>
						    <td width="6%">&nbsp;</td>
							<td width="25%">&nbsp;</td>
						    <td colspan="2" class="">
							<form:errors path="suspendRemarks" cssClass="error"/>
							</td>
						</tr>
					</table>										
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
	<c:set var="btnName" value='${signoffBtnName}' />
	<c:if test="${serviceSummary.requireButton == '1'}">
		<c:set var="btnName" value='${requestApprovalBtnName}' />
	</c:if>
	<c:if test="${serviceSummary.requireButton == '4'}">
		<c:set var="btnName" value='${suspendBtnName}' />
	</c:if>
	<c:if test="${sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13}">	
		<c:set var="btnName" value='${submitBtnName}' />
	</c:if>
	
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
	
	
	<form:hidden path="retypeDocNum" id="retypeDocNum" />
	
	<form:hidden path="buttonPressed" id="buttonPressed" />
	<input type="hidden" name="isFormPrinted" value="false">
	<input type="hidden" name="isFormPreviewed" value="false">
	<input type="hidden" name="isFormConfirmed" value="false">
	<input type="hidden" name="isPsReviewed" value="false">
	<input type="hidden" name="isSignatureSigned" value="false">
	<input type="hidden" name="isDsDoubleChecked" id="isDsDoubleChecked" value="false">
	
	<c:if test="${enquiry == 'ENQUIRY' || enquiry == 'ENQUIRY_N_CANCEL' || enquiry == 'AFTER_SUBMIT'}">
	    <c:forEach items="${serviceSummary.serviceSummaryList}" var="Summary">
	        <c:if test="${Summary.srvType == 'EYE'}">
	            <table width="100%" border="0" cellspacing="0" align="left">
	                <tr>
		                <td align="left">
		                    <a href="http://www.sc-workshop.com/" onclick="window.open(this.href, '<spring:message code="lts.acq.summary.eyeTrainWorkshopEnroll" arguments="" htmlEscape="false"/>', 'width=600, height=400, scrollbars=yes'); return false;"><spring:message code="lts.acq.summary.eyeTrainWorkshopEnroll" arguments="" htmlEscape="false"/></a>						
		                </td>
	    	        </tr>    
	            </table>
	        </c:if>
	    </c:forEach>
	</c:if>
	
	<c:if test="${enquiry != 'ENQUIRY' && enquiry != 'ENQUIRY_N_CANCEL' && enquiry != 'UPDATE_APPOINTMENT_FOR_QC' && enquiry != 'AFTER_SUBMIT'}">
		<c:if test="${(!(sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13)) || serviceSummary.dsOrderSubmit == false}">
			<table id="suspendReasonTbl" width="100%" border="0" cellspacing="0" align="right">
				<tr>
					<td align="right">
						<form:errors path="suspendReason" cssClass="error" /> 
							<b><spring:message code="lts.acq.common.suspendReason" htmlEscape="false"/> </b> 
						<form:select path="suspendReason" id="suspendReason">
							<form:option value="NONE"><spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/></form:option>
							<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
						</form:select> 
						<a id="suspend" href="#" class="nextbutton"><div class="button"><spring:message code="lts.acq.common.suspend" htmlEscape="false"/></div></a> &nbsp; 
					</td>
				</tr>
			</table>
			<br><br>
		</c:if>
	</c:if>
	<br>
	<table width="100%" border="0" cellspacing="0" align="right">
		<tr align="right">
			<td align="right">
				<!-- <a id="refresh" href="#"class="nextbutton">Refresh</a> -->
				
				<c:if test="${!(sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13)}">	
					<a id="showRemarks" href="#"class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.showRemarks" arguments="" htmlEscape="false"/></div></a> 
				</c:if>
				
<!-- 				<a id="compareExSrv" href="#"class="nextbutton">Compare Existing Service</a>	 --> 
				
				<c:if test="${!(sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13 || sessionScope.bomsalesuser.channelId == 1)}">
					<c:if test="${serviceSummary.containPrepayment}">
						<a id="printPS" name="printPS" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.viewPaymentSlip" arguments="" htmlEscape="false"/></div></a>
					</c:if>
				</c:if>				
				
				<c:if test="${!(sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13)}">								
					<a id="printAF" name="areport" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.printAF" arguments="" htmlEscape="false"/></div></a>
				</c:if>
				<c:if test="${enquiry != 'UPDATE_APPOINTMENT_FOR_QC' }">
					<a id="previewAF" name="areport" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.previewAF" arguments="" htmlEscape="false"/></div></a>
				</c:if>
				<c:if test="${!serviceSummary.onlineAccqOrder && enquiry == 'ENQUIRY'}">
					<c:if test="${serviceSummary.distributeMode == 'E' && (sessionScope.bomsalesuser.channelId == 1 || sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13)}">
						<a id="captureDociPad" href="${captureDocIpadAppUri}" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofByIPad" arguments="" htmlEscape="false"/></div></a>						
						<c:if test="${ sessionScope.bomsalesuser.channelId == 1 }">
							<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofViaPC" arguments="" htmlEscape="false"/></div></a>
						</c:if>
					</c:if>
					<c:if test="${serviceSummary.distributeMode == 'P' && (sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13)}">
						<a id="captureDociPad" href="${captureDocIpadAppUri}" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofByIPad" arguments="" htmlEscape="false"/></div></a>
					</c:if>
					<c:if test="${serviceSummary.distributeMode == 'P' && sessionScope.bomsalesuser.channelId == 1}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofViaPC" arguments="" htmlEscape="false"/></div></a>
					</c:if>
				</c:if>
				<c:if test="${enquiry != 'ENQUIRY' && enquiry != 'ENQUIRY_N_CANCEL' && enquiry != 'UPDATE_APPOINTMENT_FOR_QC' && enquiry != 'AFTER_SUBMIT'}">
					<!-- E: ESIGNATURE -->
					<c:if test="${serviceSummary.distributeMode == 'E' && (sessionScope.bomsalesuser.channelId == 1 || sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13)}">
						<c:if test="${sessionScope.bomsalesuser.channelId == 1 }">
							<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofViaPC" arguments="" htmlEscape="false"/></div></a>
						</c:if>				
						<a id="captureDociPad" href="${captureDocIpadAppUri}" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofByIPad" arguments="" htmlEscape="false"/></div></a>
						<a id="digitalSignature" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.digitalSignature" arguments="" htmlEscape="false"/></div></a>
					</c:if>
					<c:if test="${serviceSummary.distributeMode == 'P' && (sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13)}">
						<a id="captureDociPad" href="${captureDocIpadAppUri}" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofByIPad" arguments="" htmlEscape="false"/></div></a>
						<a id="digitalSignature" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.digitalSignature" arguments="" htmlEscape="false"/></div></a>
					</c:if>
					<c:if test="${serviceSummary.distributeMode == 'P' && sessionScope.bomsalesuser.channelId == 1}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofViaPC" arguments="" htmlEscape="false"/></div></a>
					</c:if>
					<c:if test="${sessionScope.bomsalesuser.channelId == 3 && serviceSummary.signoffMode == 'R'}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofViaPC" arguments="" htmlEscape="false"/></div></a>
						<a id="captureDociPad" href="${captureDocIpadAppUri}" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofByIPad" arguments="" htmlEscape="false"/></div></a>
						<br/><div style="height:5px"></div>
						<c:if test="${serviceSummary.distributeMode == 'E'}">
							<a id="digitalSignature" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.digitalSignature" arguments="" htmlEscape="false"/></div></a>
						</c:if>
					</c:if>
					<c:if test="${sessionScope.bomsalesuser.channelId == 3 && serviceSummary.signoffMode != 'R'}">
						<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.captureProofViaPC" arguments="" htmlEscape="false"/></div></a>
					</c:if>			
					<c:if test="${sessionScope.bomsalesuser.channelId == 2}">
						<a id="captureDocSerial" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.updateFaxSerialNumber" arguments="" htmlEscape="false"/></div></a>
					</c:if>
					<c:if test="${sessionScope.bomsalesuser.channelId == 2 || sessionScope.bomsalesuser.channelId == 3}">
						<c:if test="${serviceSummary.allowUpdateEdfRef }">
							<a id="updateEdfRef" href="#" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.updateEDFReference" arguments="" htmlEscape="false"/></div></a>
						</c:if>
					</c:if>
					<br/><br/><br/>
					<c:if test="${serviceSummary.requireButton != '3'}">
						<a id="signoff" href="#" class="nextbutton" name="asignoff"><div class="button">${btnName}</div></a>
					</c:if>
					<a id="cancel" href="#" class="nextbutton" name="acancel"><div class="button"><spring:message code="lts.acq.summary.cancel" arguments="" htmlEscape="false"/></div></a>
				</c:if>
				<c:if test="${serviceSummary.onlineAccqOrder}">
					<a href="ltsamendhist.html?orderId=${serviceSummary.orderId}" class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.amendmentHistory" arguments="" htmlEscape="false"/></div></a>
					<a href="sboordersearch.html" class="nextbutton"><div class="button"><spring:message code="lts.acq.summary.back" arguments="" htmlEscape="false"/></div></a> 
				</c:if>
				<c:if test="${enquiry == 'ENQUIRY_N_CANCEL'}">
					<a id="cancelOrder" href="#" class="nextbutton"><div class="button"><spring:message code="lts.acq.summary.cancelOrder" arguments="" htmlEscape="false"/></div></a>
					<a id="exit" href="welcome.html?sbuid=${param.sbuid}" onclick="return confirm('Exit without Save. Sure to continue?');" class="nextbutton"><div class="button"><spring:message code="lts.acq.summary.exitWithoutSave" arguments="" htmlEscape="false"/></div></a>
				</c:if>
				<c:if test="${enquiry == 'UPDATE_APPOINTMENT_FOR_QC'}">
					<a id="signoff" href="#" class="nextbutton" name="asignoff"><div class="button">${btnName}</div></a>
				</c:if>
				<c:if test="${enquiry != 'UPDATE_APPOINTMENT_FOR_QC' && (sessionScope.bomsalesuser.channelId == 12 || sessionScope.bomsalesuser.channelId == 13)}">	
					<a id="confirmAF" href="#"class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.confirmedAF" arguments="" htmlEscape="false"/></div></a> 
					<c:if test="${serviceSummary.allowResendNameNotMatchWQ}">
						<br/><br/><a id="resendNameNotMatchWQ" href="#"class="nextbutton"><div class="func_button"><spring:message code="lts.acq.summary.resendNameNotMatchApprovalWQ" arguments="" htmlEscape="false"/></div></a>
					</c:if> 
				</c:if>
			</td>
		</tr>
</table>
</form:form>
<br/><br/>
</div>
<script type="text/javascript">
	var selectInSelectVal = '<spring:message code="lts.acq.summary.select" arguments="" htmlEscape="false"/>';
	var vipVal = '<spring:message code="lts.acq.summary.VIP" arguments="" htmlEscape="false"/>';
	var foreignLangVal = '<spring:message code="lts.acq.summary.foreignLanguage" arguments="" htmlEscape="false"/>';
	var muteVal = '<spring:message code="lts.acq.summary.mute" arguments="" htmlEscape="false"/>';
	var deafVal = '<spring:message code="lts.acq.summary.deaf" arguments="" htmlEscape="false"/>';

	var waiveQcElement = document.getElementById("waiveQc");
	var waiveQcCount;
	if (waiveQcElement != null)
	{
		for (waiveQcCount = 0; waiveQcCount < waiveQcElement.length; waiveQcCount++) {
			if(waiveQcElement.options[waiveQcCount].text=='--- Select ---')
			{
				waiveQcElement.options[waiveQcCount].text=selectInSelectVal;
			}
			else if (waiveQcElement.options[waiveQcCount].text=='VIP')
			{
				waiveQcElement.options[waiveQcCount].text=vipVal;
			}
			else if (waiveQcElement.options[waiveQcCount].text=='Foreign Language')
			{
				waiveQcElement.options[waiveQcCount].text=foreignLangVal;
			}
			else if (waiveQcElement.options[waiveQcCount].text=='Mute')
			{
				waiveQcElement.options[waiveQcCount].text=muteVal;
			}
			else if (waiveQcElement.options[waiveQcCount].text=='Deaf')
			{
				waiveQcElement.options[waiveQcCount].text=deafVal;
			}
		}
	}	
	
	var swaitAppointmentMakingVal = '<spring:message code="lts.acq.common.suspendReason.swaitAppointmentMaking" htmlEscape="false"/>';
	var userMissingSubmitOrderVal = '<spring:message code="lts.acq.common.suspendReason.userMissingSubmitOrder" htmlEscape="false"/>';
	var awaitDocumentVal = '<spring:message code="lts.acq.common.suspendReason.awaitDocument" htmlEscape="false"/>';
	var DRGDowntimeVal = '<spring:message code="lts.acq.common.suspendReason.DRGDowntime" htmlEscape="false"/>';
	var NACaseVal = '<spring:message code="lts.acq.common.suspendReason.5NACase" htmlEscape="false"/>';
	var OtherVal = '<spring:message code="lts.acq.common.suspendReason.Other" htmlEscape="false"/>';
	var pendingOrderExistVal = '<spring:message code="lts.acq.common.suspendReason.pendingOrderExist" htmlEscape="false"/>';
	var awaitPaymentVal = '<spring:message code="lts.acq.common.suspendReason.awaitPayment" htmlEscape="false"/>';
	var manualCancellationVal = '<spring:message code="lts.acq.common.suspendReason.manualCancellation" htmlEscape="false"/>';
	var TOSServiceNumberVal = '<spring:message code="lts.acq.common.suspendReason.TOSServiceNumber" htmlEscape="false"/>';
	var awaitCreditCardOrAutoPayPaymentVal = '<spring:message code="lts.acq.common.suspendReason.awaitCreditCardOrAutoPayPayment" htmlEscape="false"/>';

	var suspendReasonEle = document.getElementById("suspendReason");
	var a;
	if(suspendReasonEle!=null)
	{
		for (a = 0; a < suspendReasonEle.length; a++) {
			
			if(suspendReasonEle.options[a].text=='Await Appointment Making (cancel after 7 day)')
			{
				suspendReasonEle.options[a].text=swaitAppointmentMakingVal;
			}
			else if (suspendReasonEle.options[a].text=='User Missing Submit Order (cancel after 2 days)')
			{
				suspendReasonEle.options[a].text=userMissingSubmitOrderVal;
			}
			else if (suspendReasonEle.options[a].text=='Await Document (cancel after 7 day)')
			{
				suspendReasonEle.options[a].text=awaitDocumentVal;
			}
			else if (suspendReasonEle.options[a].text=='DRG Downtime')
			{
				suspendReasonEle.options[a].text=DRGDowntimeVal;
			}
			else if (suspendReasonEle.options[a].text=='5NA Case (cancel after 30 days)')
			{
				suspendReasonEle.options[a].text=NACaseVal;
			}
			else if (suspendReasonEle.options[a].text=='Other (cancel after 7 day)')
			{
				suspendReasonEle.options[a].text=OtherVal;
			}
			else if (suspendReasonEle.options[a].text=='Pending Order Exist (cancel after SRD + 30 days)')
			{
				suspendReasonEle.options[a].text=pendingOrderExistVal;
			}
			else if (suspendReasonEle.options[a].text=='Await Payment (cancel after 7 days)')
			{
				suspendReasonEle.options[a].text=awaitPaymentVal;
			}
			else if (suspendReasonEle.options[a].text=='Manual Cancellation')
			{
				suspendReasonEle.options[a].text=manualCancellationVal;
			}
			else if (suspendReasonEle.options[a].text=='TOS Service Number (cancel after 7 day)')
			{
				suspendReasonEle.options[a].text=TOSServiceNumberVal;
			}
			else if (suspendReasonEle.options[a].text=='Await Credit Card / Bank Auto Pay Payment')
			{
				suspendReasonEle.options[a].text=awaitCreditCardOrAutoPayPaymentVal;
			}
		}
	}
	
	var prepayCancelReasonCodeEle = document.getElementById("prepayCancelReasonCode");
	var b;
	if(prepayCancelReasonCodeEle!=null)
	{
		for (b = 0; b < prepayCancelReasonCodeEle.length; b++) {
			
			if(prepayCancelReasonCodeEle.options[b].text=='--- Select ---')
			{
				prepayCancelReasonCodeEle.options[b].text=selectInSelectVal;
			}
			else if(prepayCancelReasonCodeEle.options[b].text=='Await Appointment Making (cancel after 7 day)')
			{
				prepayCancelReasonCodeEle.options[b].text=swaitAppointmentMakingVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='User Missing Submit Order (cancel after 2 days)')
			{
				prepayCancelReasonCodeEle.options[b].text=userMissingSubmitOrderVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='Await Document (cancel after 7 day)')
			{
				prepayCancelReasonCodeEle.options[b].text=awaitDocumentVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='DRG Downtime')
			{
				prepayCancelReasonCodeEle.options[b].text=DRGDowntimeVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='5NA Case (cancel after 30 days)')
			{
				prepayCancelReasonCodeEle.options[b].text=NACaseVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='Other (cancel after 7 day)')
			{
				prepayCancelReasonCodeEle.options[b].text=OtherVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='Pending Order Exist (cancel after SRD + 30 days)')
			{
				prepayCancelReasonCodeEle.options[b].text=pendingOrderExistVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='Await Payment (cancel after 7 days)')
			{
				prepayCancelReasonCodeEle.options[b].text=awaitPaymentVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='Manual Cancellation')
			{
				prepayCancelReasonCodeEle.options[b].text=manualCancellationVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='TOS Service Number (cancel after 7 day)')
			{
				prepayCancelReasonCodeEle.options[b].text=TOSServiceNumberVal;
			}
			else if (prepayCancelReasonCodeEle.options[b].text=='Await Credit Card / Bank Auto Pay Payment')
			{
				prepayCancelReasonCodeEle.options[b].text=awaitCreditCardOrAutoPayPaymentVal;
			}
		}
	}
	
	if("${serviceSummary.preEnquiry}" != null && "${serviceSummary.preEnquiry}" != "")
	{
		$('#preEnquiry').val("${serviceSummary.preEnquiry}");
	}
	else
	{
		$('#preEnquiry').val($('#orderAction').val());
	}
	
	if('${confirmReuseMsg}' != ''){
		if (confirm('${confirmReuseMsg}')) {
			$("#prepayStatus").val("R");
			$("#buttonPressed").val("0");
			$("#summary").submit();
		}
	}
	
	if("${alertMessage}" != ''){
		alert("${alertMessage}");
	}	
	
	var dsStatusHistory = $('#statusDsHistoryDiv').text();
	if(dsStatusHistory.indexOf("Prepayment settled") >= 0 && "${serviceSummary.containPrepayment}" == "true") {
		$('#prepaymentInfo').css("display","block");	
		$('#qcCallTimePeriod').attr('disabled', 'disabled');
		$('#waiveQc').attr('disabled', 'disabled');
		$('#prepayShopCode').attr('disabled', 'disabled');
		$('#prepayTransactionCode').attr('disabled', 'disabled');
	} else if(dsStatusHistory.indexOf("Order signoff") >= 0 && "${serviceSummary.containPrepayment}" == "true"){
		$('#prepaymentInfo').css("display","block");	
		$('#qcCallTimePeriod').attr('disabled', 'disabled');
		$('#waiveQc').attr('disabled', 'disabled');
		$('#prepayShopCode').attr('disabled', '');
		$('#prepayTransactionCode').attr('disabled', '');
	} else if(dsStatusHistory.indexOf("Order signoff") >= 0){
		$('#prepaymentInfo').css("display","none");	
		$('#qcCallTimePeriod').attr('disabled', 'disabled');
		$('#waiveQc').attr('disabled', 'disabled');
	} else {
		if($('#dsOrderSubmit').val() == "true"){
			$('#prepaymentInfo').css("display","block");	
			$('#qcCallTimePeriod').attr('disabled', 'disabled');
			$('#waiveQc').attr('disabled', 'disabled');
		}
		else{
			$('#prepaymentInfo').css("display","none");	
			$('#qcCallTimePeriod').attr('disabled', '');
			$('#waiveQc').attr('disabled', '');
		}
	}
		
	function updPrepaymentOrdSts() {					
		var url = "ltsacqprepaymentorderstatus.html?isPrepaymentRequired=Y";	
		$.ajax({
			url : url,
			type : 'POST',
			dataType : 'json',
			timeout : 15000,
			error : function(e) {
			},
			success : function(result) {
				if (result.status == 'true') {
					// here, trigger prepayment screen 
				} else {					
					var errorMsg = result.errorMsg;					
					alert(errorMsg);					
				}
			},
			complete : function() {
				 $.unblockUI(); 
			},
			
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}
		});
	}
	
	function resendNameNotMatchApprovalWQ(){
		
		var answer = confirm('<spring:message code="lts.acq.summary.confirmResendNameNotMatchWQ" arguments="" htmlEscape="false"/>');
		
		if(answer != true){
			return;
		}
		
		var url = "ltsdsorderservice.html";	
		var orderId = "${serviceSummary.orderId}";
		var userId = "${sessionScope.bomsalesuser.username}";
		
		$.ajax({
			url : url,
			type : 'POST',
			//async: false, 
			data : "orderId=" + orderId + "&reqType=RESEND_DS_NAME_APR_WQ&userId=" + userId + "&sbuid=${sessionScope.sbuid}" ,
			dataType : 'json',
			timeout : 15000,
			error : function(e) { 

			},
			success : function(obj) {
				if (obj.result != '') {
					var message = obj.result;									
					alert(message);
				} 				
				
			},
			complete : function() {
				 $.unblockUI(); 
			},
			
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}
		});
		
	}
	
	function checkAndUpdateSbOrderStatus() {					
		var url = "ltsdsorderservice.html";	
		var orderId = "${serviceSummary.orderId}";
		var userId = "${sessionScope.bomsalesuser.username}";
		var mustQcInd = "${serviceSummary.mustQc}";
		var wqType = "";
		var prepayInd = "";
		if("${serviceSummary.containPrepayment}" == "true"){
			prepayInd = "Y";
		}		
		var qcCallTimePeriod = $('#qcCallTimePeriod').val();
		var waiveQc = $('#waiveQc').val();
		
		$.ajax({
			url : url,
			type : 'POST',

			data : "orderId=" + orderId + "&reqType=" + "SUBMIT_FOR_PREPAY&userId=" + userId + 
					"&prepay=" + prepayInd + "&mustQc=" + mustQcInd + "&wqType=" + wqType + 
					"&waiveQc=" + waiveQc + "&qcCallTimePeriod=" + qcCallTimePeriod + "&sbuid=${sessionScope.sbuid}",
			dataType : 'json',
			timeout : 15000,
			error : function(e) { 

			},
			success : function(obj) {
				if (obj.result != '') {
					var message = obj.result;									
					alert(message);
				} 				
				
				if(obj.status == 'W'){
					$('#dsOrderSubmit').val('true');				
					$('#prepaymentInfo').css("display","block");	
					$('#suspendReason').css("display","none");
					$('#qcCallTimePeriod').attr('disabled', 'disabled');
					$('#waiveQc').attr('disabled', 'disabled');					
				}
				else{
					$('#prepaymentInfo').css("display","none");	
					$('#qcCallTimePeriod').attr('disabled', '');
					$('#waiveQc').attr('disabled', '');
				}
				$('#prepaymentSignoffDate').html(obj.statusHistory);
			},
			complete : function() {
				 $.unblockUI(); 
			},
			
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}
		});
	}

	$(document).ready(function() {

		$("a#signoff").click(function(event) {
			event.preventDefault();
			var isFormPrinted = $('input[name="isFormPrinted"]').val();
			var isFormPreviewed = $('input[name="isFormPreviewed"]').val();
			var isDsDoubleChecked = $('#isDsDoubleChecked').val();
			
			var dsOrderSubmitVal = $('#dsOrderSubmit').val();
			var statusDsHistoryDivVal = $('#statusDsHistoryDiv').text();
			
			if("${enquiry == 'UPDATE_APPOINTMENT_FOR_QC'}" == 'true'){
				$("#buttonPressed").val("${serviceSummary.requireButton}");
				$("#summary").submit();
				return;
			}
			
			if ("${serviceSummary.payMtdTypeN}" == 'true') {
				alert('<spring:message code="lts.acq.summary.plsSelectPaymentMethod" arguments="${btnName}" htmlEscape="false"/>');
				return;
			} 
			
			if("${sessionScope.bomsalesuser.channelId}" == '12' || "${sessionScope.bomsalesuser.channelId}" == '13'){
				var isFormConfirmed = $('input[name="isFormConfirmed"]').val();
				var isPsReviewed = $('input[name="isPsReviewed"]').val();
				
				if (isFormConfirmed != 'true') {
					alert('<spring:message code="lts.acq.summary.plsConfirmAF" arguments="${btnName}" htmlEscape="false"/>');
					return;
				} 				
				else if (isPsReviewed != 'true' && $('#dsOrderSubmit').val() == "true") {
					alert('<spring:message code="lts.acq.summary.plsReviewPaymentSlip" arguments="${btnName}" htmlEscape="false"/>');
					return;

				}
				
				if(statusDsHistoryDivVal.indexOf("Prepayment settled") >= 0 && "${serviceSummary.containPrepayment}" == "true") {
					isDsDoubleChecked = 'true';
				}
				else if(statusDsHistoryDivVal.indexOf("Order signoff") >= 0 && "${serviceSummary.containPrepayment}" == "true") {
					isDsDoubleChecked = 'true';
				}
				else if(dsOrderSubmitVal == "true") {
					isDsDoubleChecked = 'true';
				}
				
				if (isDsDoubleChecked != 'true') 
				{
					var doc_num = prompt('<spring:message code="lts.acq.summary.plsEnterDocumentNumAgain" arguments="" htmlEscape="false"/>', '');
				    if (doc_num != null) {
				    	doc_num = doc_num.toUpperCase();
				        $('#retypeDocNum').val(doc_num);			        
				    	alert('<spring:message code="lts.acq.summary.docNumEnterAgainIs" arguments="" htmlEscape="false"/>'+doc_num);
				    	
				    	if("${serviceSummary.serviceSummaryList[0].recontractInd}" == 'Y')
			    		{
				    		if("${serviceSummary.serviceSummaryList[0].transfereeDocNum}" != doc_num)
				    		{
				    			alert('<spring:message code="lts.acq.summary.docNumEnterAgainIsDiffBefore1" arguments="" htmlEscape="false"/>'+doc_num+'<spring:message code="lts.acq.summary.docNumEnterAgainIsDiffBefore2" arguments="" htmlEscape="false"/>'+'${serviceSummary.serviceSummaryList[0].transfereeDocNum}'+'<spring:message code="lts.acq.summary.docNumEnterAgainIsDiffBefore3" arguments="" htmlEscape="false"/>');
				    			return;
				    		}
			    		}
				    	else
			    		{
				    		if("${serviceSummary.serviceSummaryList[0].docNum}" != doc_num)
				    		{
				    			alert('<spring:message code="lts.acq.summary.docNumEnterAgainIsDiffBefore1" arguments="" htmlEscape="false"/>'+doc_num+'<spring:message code="lts.acq.summary.docNumEnterAgainIsDiffBefore2" arguments="" htmlEscape="false"/>'+'${serviceSummary.serviceSummaryList[0].docNum}'+'<spring:message code="lts.acq.summary.docNumEnterAgainIsDiffBefore3" arguments="" htmlEscape="false"/>');
				    			return;
				    		}
			    		}

						$('#isDsDoubleChecked').val("true");
						
				    }
				    else {
				    	return;
				    }
				}			    
			}
			else{
				if (isFormPrinted != 'true' && "${serviceSummary.distributeMode}" == 'P') {
					alert('<spring:message code="lts.acq.summary.plsPrintAF" arguments="${btnName}" htmlEscape="false"/>');
					return;
				} 
	
				if (isFormPreviewed != 'true' && "${serviceSummary.distributeMode}" == 'E') {
					alert('<spring:message code="lts.acq.summary.plsPreviewAF" arguments="${btnName}" htmlEscape="false"/>');
					return;
				}	
				
				if (isFormPrinted != 'true' && "${serviceSummary.distributeMode}" == 'E' && $('#esigEmailAddr').html() == null) {
					alert('<spring:message code="lts.acq.summary.plsPrintAF" arguments="${btnName}" htmlEscape="false"/>');
					return;
				}				
			}
			
			if ("${btnName}" == "${signoffBtnName}") {
				
				if ($('input[name="orderGeneratePenalty"]').val() == "true") {
					var confirmPenalty = confirm('<spring:message code="lts.acq.summary.penaltyGenerated" arguments="" htmlEscape="false"/>');
					if (!confirmPenalty){
						return;
					}
				}
				
				if ("${serviceSummary.distributeMode}" == 'E' && $('#esigEmailAddr').html() != null) {
					var email = $('#esigEmailAddr').html();
					var answer = confirm('<spring:message code="lts.acq.summary.emailSentToConfirm1" arguments="" htmlEscape="false"/>'+' ' + email + ' ' + '<spring:message code="lts.acq.summary.emailSentToConfirm2" arguments="" htmlEscape="false"/>');
					if (!answer){
						return;
					}
				}
			} else if ("${btnName}" == "${submitBtnName}") {
				if(($('#dsOrderSubmit').val() == "true") || "${serviceSummary.containPrepayment}" != "true"){ 
					if("${serviceSummary.containPrepayment}" == "true"){
						var errorMsg = "";
						if($('#prepayShopCode').val() == ""){
							errorMsg += '<spring:message code="lts.acq.summary.plsEnterShopCode" arguments="" htmlEscape="false"/>'+'\n';
						}
						if($('#prepayTransactionCode').val() == ""){
							errorMsg += '<spring:message code="lts.acq.summary.plsEnterTransactionCode" arguments="" htmlEscape="false"/>'+'\n';
						}
						if($('#prepayDateYear').val() == "" || $('#prepayDateMonth').val() == "" || $('#prepayDateDay').val() == ""
								|| $('#prepayTimeHour').val() == "" || $('#prepayTimeMin').val() == "" || $('#prepayTimeSec').val() == ""){
							errorMsg += '<spring:message code="lts.acq.summary.plsEnterPrepaymentSettleDate" arguments="" htmlEscape="false"/>'+'\n';
						}
						
						if(errorMsg != ""){
							alert(errorMsg);
							return;
						}
					}
					else{
						if ($('input[name="orderGeneratePenalty"]').val() == "true") {
							var confirmPenalty = confirm('<spring:message code="lts.acq.summary.penaltyGenerated" arguments="" htmlEscape="false"/>');
							if (!confirmPenalty){
								return;
							}
						}									
						else{
							if ("${serviceSummary.distributeMode}" == 'E' && $('#esigEmailAddr').html() != null && $('#smsNo').html() != null) {
								var email = $('#esigEmailAddr').html();
								var sms = $('#smsNo').html();
								var answer = confirm('<spring:message code="lts.acq.summary.emailNSMSSentToConfirm1" arguments="" htmlEscape="false"/> ' + email + '\n' + '<spring:message code="lts.acq.summary.emailNSMSSentToConfirm2" arguments="" htmlEscape="false"/> ' + sms + '\n' + '<spring:message code="lts.acq.summary.emailNSMSSentToConfirm3" arguments="" htmlEscape="false"/>');
								if (!answer){
									return;
								}
							}
							else if ("${serviceSummary.distributeMode}" == 'E' && $('#esigEmailAddr').html() != null) {
								var email = $('#esigEmailAddr').html();
								var answer = confirm('<spring:message code="lts.acq.summary.emailSentToConfirm1" arguments="" htmlEscape="false"/> ' + email + ' <spring:message code="lts.acq.summary.emailSentToConfirm2" arguments="" htmlEscape="false"/>');
								if (!answer){
									return;
								}
							}
							else if ("${serviceSummary.distributeMode}" == 'E' && $('#smsNo').html() != null) {
								var sms = $('#smsNo').html();
								var answer = confirm('<spring:message code="lts.acq.summary.SMSSentToConfirm1" arguments="" htmlEscape="false"/> ' + sms + ' <spring:message code="lts.acq.summary.SMSSentToConfirm2" arguments="" htmlEscape="false"/>');
								if (!answer){
									return;
								}
							}
						}
					}
				}
				else{
					if ("${serviceSummary.distributeMode}" == 'E' && $('#esigEmailAddr').html() != null && $('#smsNo').html() != null) {
						var email = $('#esigEmailAddr').html();
						var sms = $('#smsNo').html();
						var answer = confirm('<spring:message code="lts.acq.summary.emailNSMSSentToConfirm1" arguments="" htmlEscape="false"/> ' + email + '\n' + '<spring:message code="lts.acq.summary.emailNSMSSentToConfirm2" arguments="" htmlEscape="false"/> ' + sms + '\n' + '<spring:message code="lts.acq.summary.emailNSMSSentToConfirm3" arguments="" htmlEscape="false"/>');
						if (!answer){
							return;
						}
					}
					else if ("${serviceSummary.distributeMode}" == 'E' && $('#esigEmailAddr').html() != null) {
						var email = $('#esigEmailAddr').html();
						var answer = confirm('<spring:message code="lts.acq.summary.emailSentToConfirm1" arguments="" htmlEscape="false"/> ' + email + ' <spring:message code="lts.acq.summary.emailSentToConfirm2" arguments="" htmlEscape="false"/>');
						if (!answer){
							return;
						}
					}
					else if ("${serviceSummary.distributeMode}" == 'E' && $('#smsNo').html() != null) {
						var sms = $('#smsNo').html();
						var answer = confirm('<spring:message code="lts.acq.summary.SMSSentToConfirm1" arguments="" htmlEscape="false"/> ' + sms + ' <spring:message code="lts.acq.summary.SMSSentToConfirm2" arguments="" htmlEscape="false"/>');
						if (!answer){
							return;
						}
					}
					checkAndUpdateSbOrderStatus();
					$('#prepaymentInfo').css("display","block");					
					$('#prepaymentInfo').focus();
					$(".completed-step>a").each(function(i, e){$(e).attr("href", '#');});
					return;
				}				
			} else {
				alert("${btnName} Completed");
			}
			$("#buttonPressed").val("${serviceSummary.requireButton}");
			$("#summary").submit();
		});

		$("a#digitalSignature").click(function(event) {
			event.preventDefault();
			
			if ($('input[name="isFormPreviewed"]').val() == 'false') {
				alert('<spring:message code="lts.acq.summary.plsPreviewAppFormBeforeSign" arguments="" htmlEscape="false"/>');
				return;
			}
			$('input[name="isSignatureSigned"]').val('true');
			window.open('ltsacqdigitalsignature.html?sbuid=${sessionScope.sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
		});
		
		$("a#captureDocSerial").click(function(event) {
			event.preventDefault();
			var win = window.open('ltsacqcollectdoc.html?sbuid=${sessionScope.sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=512,width=1024');
			//win.onclose(function(){location.reload(true); });
		});
		
		$("a#updateEdfRef").click(function(event) {
			event.preventDefault();
			var win = window.open('ltsacqedfref.html?sbuid=${sessionScope.sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=512,width=1024');
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
			if (confirm('<spring:message code="lts.acq.summary.plsConfirmCancelOrder" arguments="" htmlEscape="false"/>')) {
				$("#buttonPressed").val("2");
				$("#summary").submit();
			}
		});

		$("a#cancelOrder").click(function(event) {
			if (confirm('<spring:message code="lts.acq.summary.plsConfirmCancelOrder" arguments="" htmlEscape="false"/>')) {
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
			window.open('ltsacqreport.html?sbuid='+ sbuid + '&ltsRptAction=PRINT_AF');	
			$('input[name="isFormPrinted"]').val('true');
		});
		
		$("a#previewAF").click(function(event) {
			event.preventDefault();		
			var sbuid = $('input[name="sbuid"]').val();
			window.open('ltsacqreport.html?sbuid='+ sbuid +'&ltsRptAction=PREVIEW_AF');
			$('input[name="isFormPreviewed"]').val('true');
		});
		
		$("a#printPS").click(function(event) {
			event.preventDefault();
			$('input[name="isPsReviewed"]').val('true');
			var sbuid = $('input[name="sbuid"]').val();
			window.open('ltsacqreport.html?sbuid='+ sbuid + '&ltsRptAction=PRINT_PS');	
		});
		
		$("a#confirmAF").click(function(event) {
			event.preventDefault();		
			var confirmAF = confirm('<spring:message code="lts.acq.summary.confirmedAF" arguments="" htmlEscape="false"/>'+'?');
			if (confirmAF){
				var sbuid = $('input[name="sbuid"]').val();
				window.open('ltsacqreport.html?sbuid='+ sbuid +'&ltsRptAction=PREVIEW_AF');
				$('input[name="isFormPreviewed"]').val('true');				
				$('input[name="isFormConfirmed"]').val('true');
			}			
			
		});
		
		$("a#resendNameNotMatchWQ").click(function(event) {
			resendNameNotMatchApprovalWQ();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
		
	});
</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>