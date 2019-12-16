<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<style>.selectionDisplayNone {display:none;}</style>
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript"
	src="js/web/lts/acq/ltsacqnumselectionandpipb.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script
	src="js/jquery.lts.autocomplete.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="8" />
</jsp:include>

<form:form method="POST" id="ltsAcqNumSelectionAndPipbForm"
	name="ltsAcqNumSelectionAndPipbForm"
	commandName="ltsacqnumselectionandpipbCmd" autocomplete="off">
	<form:hidden path="formAction" />
	<form:hidden path="numSelection" />
	<form:hidden path="searchMethod" />
	<form:hidden path="dnStatus" />
	<form:hidden path="pipbInfo.dnStatus" />
	<form:hidden path="pipbInfo.duplexDnStatus" />
	<form:hidden path="pipbInfo.portBack" />
	<form:hidden path="pipbInfo.pipbAction" />
	<form:hidden path="pipbInfo.duplexAction" />
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<table width="100%" class="paper_w2 round_10">
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td><div id="s_line_text"><spring:message code="lts.acq.numberSelection.numberSelection" arguments="" htmlEscape="false"/></div></td>
					</tr>
				</table>
				<div id="options">
					<table width="100%" border="0" cellspacing="4" cellpadding="6"
						class="contenttext">
						<tr>
							<td width="10%"></td>
							<td width="20%"><div align="left">
									<b><form:radiobutton path="numSelectionRadio"
											value="useNewDn" cssClass="searchCriteria"
											disabled="${isRecallOrder || isContainPreInstallVAS || !isDNOptionND}" /> <spring:message code="lts.acq.numberSelection.useNewDN" arguments="" htmlEscape="false"/></b>
								</div></td>
							<td width="30%"><div align="left">
									<b><form:radiobutton path="numSelectionRadio"
											value="useNewDnAndPipbDn" cssClass="searchCriteria"
											disabled="${isRecallOrder || (!isContainPreInstallVAS && !isDNOptionNP)}" /> <spring:message code="lts.acq.numberSelection.useNewDNFirstThenPIPBDN" arguments="" htmlEscape="false"/></b>
								</div></td>
							<td><div align="left">
									<b><form:radiobutton path="numSelectionRadio"
											value="usePipbDn" cssClass="searchCriteria"
											disabled="${isRecallOrder || isContainPreInstallVAS || !isDNOptionPD}" /> <spring:message code="lts.acq.numberSelection.usePIPBDN" arguments="" htmlEscape="false"/></b>
								</div></td>
						</tr>
						<tr><td colspan=4 align="center"><form:errors path="selectionErrMsg" cssClass="error" /></td></tr>
					</table>
				</div>
				<div id="numberSelection">
					<table width="100%" border="0" cellspacing="1" rules="none">
						<tr>
							<td><div id="s_line_text"><spring:message code="lts.acq.numberSelection.newServiceNumber" arguments="" htmlEscape="false"/></div></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="4" cellpadding="6"
						class="contenttext">
						<tr>
							<td width="50%" valign="top">
								<table border="0" cellspacing="4" cellpadding="6"
									class="contenttext">
									<tr>
										<td width="10%"></td>
										<td width="19%"><div align="left">
												<b><form:radiobutton path="searchMethodRadio"
														value="noCriteriaRadio" cssClass="searchCriteria" /> <spring:message code="lts.acq.numberSelection.noCriteria" arguments="" htmlEscape="false"/></b>
											</div></td>
										<td width="22%"></td>
									</tr>
									<tr>
										<td></td>
										<td><div align="left">
												<b><form:radiobutton path="searchMethodRadio"
														value="first5to8Radio" cssClass="searchCriteria" />
													<spring:message code="lts.acq.numberSelection.preferredFirst5to8Digits" arguments="" htmlEscape="false"/> </b>
											</div></td>
										<td><div align="left">
												<form:input maxlength="8" path="first5To8Digits" />
											</div></td>
									</tr>
									<tr>
										<td></td>
										<td><div align="left">
												<b><form:radiobutton path="searchMethodRadio"
														value="last1to3Radio" cssClass="searchCriteria" />
													<spring:message code="lts.acq.numberSelection.preferredLast1to3Digits" arguments="" htmlEscape="false"/> </b>
											</div></td>
										<td><div align="left">
												<form:input maxlength="3" path="last1To3Digits" />
											</div></td>
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
												<a id="searchBtn" style="color: white"
													class="searchCriteria" href="#"><spring:message code="lts.acq.numberSelection.search" arguments="" htmlEscape="false"/></a>
											</div>
										</td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td align="left"><form:errors path="searchErrMsg"
												cssClass="error" /></td>
										<td></td>
									</tr>
								</table>
							</td>
							<td width="50%" valign="top">
								<table width="100%" border="0" cellspacing="4" cellpadding="6"
									class="contenttext">
									<tr>
										<td width="35%"><div align="left">
												<b><form:radiobutton path="searchMethodRadio"
														value="reservedDnRadio" cssClass="searchCriteria" />
													<spring:message code="lts.acq.numberSelection.reservedDN" arguments="" htmlEscape="false"/></b>
											</div></td>
										<td width="60%"></td>
										<td width="5%"></td>
									</tr>
									<tr>
										<td><div align="right">
												<b><spring:message code="lts.acq.numberSelection.serviceNumber" arguments="" htmlEscape="false"/> </b>
											</div></td>
										<td><div align="left">
												<form:input maxlength="8" path="reservedSrvNum" />
											</div></td>
									</tr>
									<tr>
										<td><div align="right">
												<b><spring:message code="lts.acq.numberSelection.accountCode" arguments="" htmlEscape="false"/> 
											</div></td>
										<td><div align="left">
												<form:input path="reservedAccountCd" cssClass="toUpper"
													maxlength="3" />
											</div></td>
									</tr>
									<tr>
										<td><div align="right">
												<b><spring:message code="lts.acq.numberSelection.BOC" arguments="" htmlEscape="false"/> 
											</div></td>
										<td><div align="left">
												<form:input path="reservedBoc" cssClass="toUpper"
													maxlength="3" />
											</div></td>
									</tr>
									<tr>
										<td><div align="right">
												<b><spring:message code="lts.acq.numberSelection.projectCode" arguments="" htmlEscape="false"/> 
											</div></td>
										<td><div align="left">
												<form:input path="reservedProjectCd" cssClass="toUpper"
													maxlength="20" />
											</div></td>
									</tr>
									<tr>
										<td></td>
										<td><form:errors path="reservedSrvNum" cssClass="error" /></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<div id="s_line_text"></div>
					<table width="100%" border="0" cellspacing="4" cellpadding="6"
						class="contenttext">
						<tr>
							<td width="50%" valign="top">
								<table border="0" cellspacing="4" cellpadding="6"
									class="contenttext">
									<tr>
										<td width="26%" valign="top">
											<table width="100%" border="0" cellspacing="4"
												cellpadding="6" class="contenttext">
												<tr>
													<td width="100%"><div align="right">
															<b><spring:message code="lts.acq.numberSelection.plsSelectNumber" arguments="" htmlEscape="false"/> </b>
														</div></td>
												</tr>
												<tr>
													<td align="right">
														<div class="func_button" align="right">
															<a id="lockBtn" style="color: white"
																class="searchCriteria" href="#"><spring:message code="lts.acq.numberSelection.lock" arguments="" htmlEscape="false"/></a>
														</div>
													</td>
												</tr>
											</table>
										</td>
										<td width="20%" valign="top"><c:if
												test="${not empty ltsacqnumselectionandpipbCmd.numSelectionList}">
												<c:forEach
													items="${ltsacqnumselectionandpipbCmd.numSelectionList}"
													var="itemList" varStatus="itemListStatus">
													<div align="left">
														<table border="0" cellspacing="1" cellpadding="1"
															class="contenttext">
															<tr>
																<td><form:checkbox path="numSelectionStringList"
																		value="${itemList.srvNum}" />
																	${itemList.displaySrvNum}</td>
															</tr>
														</table>
													</div>
												</c:forEach>
											</c:if></td>
									</tr>
								</table>
							</td>
							<td width="50%" valign="top">
								<table border="0" cellspacing="4" cellpadding="6"
									class="contenttext">
									<tr>
										<td width="20%" valign="top">
											<table width="100%" border="0" cellspacing="4"
												cellpadding="6" class="contenttext">
												<tr>
													<td width="100%"><div align="right">
															<b><spring:message code="lts.acq.numberSelection.lockedNumber" arguments="" htmlEscape="false"/> </b>
														</div></td>
												</tr>
												<tr>
													<td align="right">
														<div class="func_button" align="right">
															<a id="releaseBtn" style="color: white"
																class="searchCriteria" href="#"><spring:message code="lts.acq.numberSelection.release" arguments="" htmlEscape="false"/></a>
														</div>
													</td>
												</tr>
											</table>
										</td>
										<td width="35%" valign="top"><c:if
												test="${not empty ltsacqnumselectionandpipbCmd.reservedDnList}">
												<c:forEach
													items="${ltsacqnumselectionandpipbCmd.reservedDnList}"
													var="itemList" varStatus="itemListStatus">
													<div align="left">
														<table border="0" cellspacing="1" cellpadding="1"
															class="contenttext">
															<tr>
																<td><form:checkbox path="reservedDnStringList"
																		value="${itemList.srvNum}" />
																	${itemList.displaySrvNum}</td>
															</tr>
														</table>
													</div>
												</c:forEach>
											</c:if></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align=center><form:errors path="lockNumErrMsg"
									cssClass="error" /></td>
							<td></td>
						</tr>
					</table>
				</div>

				<div id="pipbInfo">
					<table width="100%" border="0" cellspacing="1" rules="none">
						<tr>
							<td><div id="s_line_text"><spring:message code="lts.acq.numberSelection.PIPBInformation" arguments="" htmlEscape="false"/></div></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="4" cellpadding="6"
						class="contenttext">
						<tr>
							<td width="10%"></td>
							<td width="40%"><b>*<spring:message code="lts.acq.numberSelection.2NPortingFrom" arguments="" htmlEscape="false"/> </b> <form:select
									path="pipbInfo.portingFrom" id="portingFrom">
									<form:option value=""><spring:message code="lts.acq.paymentMethod.selectInSelector" htmlEscape="false"/></form:option>
									<form:options items="${pipb2NPortingList}" itemValue="itemKey"
										itemLabel="itemValue" />
								</form:select> &nbsp;<form:errors path="pipbInfo.portingFrom" cssClass="error" />
							</td>
							<td width="18%"></td>
							<td width="22%"></td>
							<td width="28%"></td>
						</tr>
						<tr>
							<td width="10%"></td>
							<td width="22%" colspan=4><div align="left">
									<b>*<spring:message code="lts.acq.numberSelection.PIPBDN" arguments="" htmlEscape="false"/> </b>
									<form:input maxlength="8" path="pipbInfo.pipbSrvNum" />
									<br>
									<form:errors path="pipbInfo.pipbSrvNum" cssClass="error" /></td>
						</tr>
						<tr id="pipbAcctCdDiv">
							<td></td>
							<td colspan=4><div align="left">
									<b><spring:message code="lts.acq.numberSelection.accountCode" arguments="" htmlEscape="false"/> <form:input cssClass="toUpper"
											path="pipbInfo.pipbAccountCd" maxlength="3" /> &nbsp;&nbsp;<b><spring:message code="lts.acq.numberSelection.BOC" arguments="" htmlEscape="false"/> <form:input cssClass="toUpper" path="pipbInfo.pipbBoc"
												maxlength="3" /> &nbsp;&nbsp;<b><spring:message code="lts.acq.numberSelection.projectCode" arguments="" htmlEscape="false"/> <form:input
													cssClass="toUpper" path="pipbInfo.pipbProjectCd"
													maxlength="20" /></td>
						</tr>
						<tr id="reuseExSocketDiv">
							<td></td>
							<td colspan=4><div align="left">
									<form:checkbox id="reuseExSocket" path="pipbInfo.reuseExSocket" />
									<b> <spring:message code="lts.acq.numberSelection.reuseSocket" arguments="" htmlEscape="false"/></b> &nbsp;
									<form:errors path="pipbInfo.reuseSocketType" cssClass="error" /></td>
						</tr>
						<tr id="socketTypeDiv">
							<td></td>
							<td colspan=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<form:radiobutton path="pipbInfo.reuseSocketType" value="P" />
								<spring:message code="lts.acq.numberSelection.reuseOurParallelPhoneLineSocket" arguments="" htmlEscape="false"/>
							</td>
						</tr>
						<tr id="socketTypeDiv2">
							<td></td>
							<td colspan=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<form:radiobutton path="pipbInfo.reuseSocketType" value="F" />
								<spring:message code="lts.acq.numberSelection.reuseYourFormerFixedLineOperatorSocket" arguments="" htmlEscape="false"/>
							</td>
						</tr>
						<tr id="waiveDnChrgDiv">
							<td></td>
							<td colspan=4><div align="left">
									<form:checkbox id="waiveDnChrg" path="pipbInfo.waiveDnChrg" />
									<b> <spring:message code="lts.acq.numberSelection.waiveChargeForDNChange" arguments="" htmlEscape="false"/></td>
						</tr>
						<tr>
							<td></td>
							<td><div align="left">
									<form:checkbox id="portFromDiffCust"
										path="pipbInfo.portFromDiffCust" />
									<b> <spring:message code="lts.acq.numberSelection.portFromDifferentCustomer" arguments="" htmlEscape="false"/></td>
						</tr>
						<tr id="custDiv">
							<td></td>
							<td colspan=4>
								<table width="100%" border="0" class="contenttext">
									<tr>
										<td align="left">
											<div align="left">
												<table width="100%" border="0" cellpadding="1"
													cellspacing="1" class="contenttext">
													<tr>
														<td align="right" width="12%"><span
															class="contenttext_red">*</span><spring:message code="lts.acq.numberSelection.documentType" arguments="" htmlEscape="false"/></td>
														<td align="left"><form:select id="docType"
																path="pipbInfo.docType">
																<form:option value=""><spring:message code="lts.acq.numberSelection.TYPE" arguments="" htmlEscape="false"/></form:option>
																<form:option value="HKID"><spring:message code="lts.acq.numberSelection.HKID" arguments="" htmlEscape="false"/></form:option>
																<form:option value="PASS"><spring:message code="lts.acq.numberSelection.passport" arguments="" htmlEscape="false"/></form:option>
																<form:option value="BS"><spring:message code="lts.acq.numberSelection.HKBR" arguments="" htmlEscape="false"/></form:option>
															</form:select> <form:input cssClass="toUpper" id="docNum"
																path="pipbInfo.docNum" /> <form:errors
																path="pipbInfo.docType" cssClass="error" /> <form:errors
																path="pipbInfo.docNum" cssClass="error" /></td>
													</tr>
													<tr class="nonHkbrName">
														<td align="right"><span class="contenttext_red">*</span><spring:message code="lts.acq.numberSelection.title" arguments="" htmlEscape="false"/>
														</td>
														<td align="left"><form:select path="pipbInfo.title"
																id="title">
																<form:option value=""><spring:message code="lts.acq.personalInfo.titleInSelector" htmlEscape="false"/></form:option>
																<form:options items="${titleList}" itemValue="itemKey"
																	itemLabel="itemValue" />
															</form:select> <form:errors path="pipbInfo.title" cssClass="error" /></td>
													</tr>
													<tr class="nonHkbrName">
														<td align="right"><span class="contenttext_red">*</span><spring:message code="lts.acq.numberSelection.familyName" arguments="" htmlEscape="false"/></td>
														<td align="left"><form:input cssClass="toUpper"
																path="pipbInfo.familyName" maxlength="40" /> <form:errors
																path="pipbInfo.familyName" cssClass="error" /></td>
													</tr>
													<tr class="nonHkbrName">
														<td align="right"><span class="contenttext_red">*</span><spring:message code="lts.acq.numberSelection.givenName" arguments="" htmlEscape="false"/></td>
														<td align="left"><form:input cssClass="toUpper"
																path="pipbInfo.givenName" maxlength="40" /> <form:errors
																path="pipbInfo.givenName" cssClass="error" /></td>
													</tr>
													<tr class="hkbrName">
														<td align="right"><span class="contenttext_red">*</span><spring:message code="lts.acq.numberSelection.companyName" arguments="" htmlEscape="false"/></td>
														<td align="left"><form:input cssClass="toUpper"
																path="pipbInfo.companyName" maxlength="40" /> <form:errors
																path="pipbInfo.companyName" cssClass="error" /></td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td></td>
							<td width="100%"><div align="left">
									<b>*<spring:message code="lts.acq.numberSelection.portFromDifferentAddress" arguments="" htmlEscape="false"/> </b>
									<form:radiobutton path="pipbInfo.portFromDiffAddrStr" value="Y" />
									<spring:message code="lts.acq.personalInfo.yes" htmlEscape="false"/>
									<form:radiobutton path="pipbInfo.portFromDiffAddrStr" value="N" />
									<spring:message code="lts.acq.personalInfo.no" htmlEscape="false"/> &nbsp;
									<form:errors path="pipbInfo.address.errMsg" cssClass="error" /></td>

						</tr>
						<tr id="baDiv">
							<td></td>
							<td colspan=4>
								<table width="100%" border="0" class="addrTable">
									<tr>
										<td align="left">
											<div align="left">
												<table width="100%" border="0" cellpadding="1"
													cellspacing="1" class="contenttext">
													<tr id="baDiv2">
														<td width="3%">&nbsp;</td>
														<td width="35%"><b><spring:message code="lts.acq.numberSelection.plsEnterExistingInstallationAddress" arguments="" htmlEscape="false"/> </b></td>
														<td align="left"></td>
													</tr>
												</table>
												<table class="contenttext" width="100%" border="0"
													cellpadding="1" cellspacing="1">
													<tr>
														<td colspan="2">&nbsp;</td>
													</tr>
													<tr id="baDiv3">
														<td align="right" width="20%"><span
															class="contenttext_red"></span><spring:message code="lts.acq.numberSelection.quickSearch" arguments="" htmlEscape="false"/></td>

														<td align="left" colspan="5"><form:input
																path="pipbInfo.quickSearch" id="quickSearch" size="80" />
															<div class="func_button" align="right">
																<a id="clearBtn" style="color: white"
																	class="searchCriteria" href="#"><spring:message code="lts.acq.numberSelection.clear" arguments="" htmlEscape="false"/></a>
															</div></td>
													</tr>
													<tr id="baDiv4">
														<td>&nbsp;</td>
														<td colspan="5"><spring:message code="lts.acq.numberSelection.inputBuilding" arguments="" htmlEscape="false"/></td>
													</tr>
													<tr id="baDiv5">
														<td colspan="2">&nbsp;</td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.acq.numberSelection.flat" arguments="" htmlEscape="false"/> :</td>
														<td><form:hidden path="pipbInfo.rolloutAddress.flat" />
															<form:input maxlength="5" path="pipbInfo.address.flat"
																id="flat" /></td>
														<td align="right"><spring:message code="lts.acq.numberSelection.floor" arguments="" htmlEscape="false"/> :</td>
														<td><form:hidden path="pipbInfo.rolloutAddress.floor" />
															<form:input maxlength="3" path="pipbInfo.address.floor"
																id="floor" /></td>
														<td align="right"><spring:message code="lts.acq.numberSelection.block" arguments="" htmlEscape="false"/> :</td>
														<td><form:input maxlength="5"
																path="pipbInfo.address.block" id="block" /></td>

													</tr>
													<tr>
														<td align="right"><spring:message code="lts.acq.numberSelection.lotNo" arguments="" htmlEscape="false"/> :</td>
														<td><form:hidden
																path="pipbInfo.rolloutAddress.lotNum" /> <form:input
																maxlength="8" path="pipbInfo.address.lotNum" id="lotNum" />
														</td>
														<td align="right"><spring:message code="lts.acq.numberSelection.building" arguments="" htmlEscape="false"/> :</td>
														<td colspan="3"><form:hidden
																path="pipbInfo.rolloutAddress.buildingName" /> <form:input
																maxlength="50" path="pipbInfo.address.buildingName"
																id="buildingName" /></td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.acq.numberSelection.streetNo" arguments="" htmlEscape="false"/> :</td>
														<td><form:hidden
																path="pipbInfo.rolloutAddress.streetNum" /> <form:input
																maxlength="11" path="pipbInfo.address.streetNum"
																id="streetNum" /></td>
														<td align="right"><spring:message code="lts.acq.numberSelection.streetName" arguments="" htmlEscape="false"/> :</td>
														<td><form:hidden
																path="pipbInfo.rolloutAddress.streetName" /> <form:input
																maxlength="80" path="pipbInfo.address.streetName"
																id="streetName" /></td>
														<td align="right"><spring:message code="lts.acq.numberSelection.streetCategory" arguments="" htmlEscape="false"/> :</td>
														<td><form:hidden
																path="pipbInfo.rolloutAddress.streetCatgCode" /> <form:hidden
																path="pipbInfo.rolloutAddress.streetCatgDesc" /> <form:hidden
																path="pipbInfo.address.streetCatgCode"
																id="streetCatgCode" /> <form:input maxlength="50"
																path="pipbInfo.address.streetCatgDesc"
																id="streetCatgDesc" /></td>
													</tr>
													<tr>
														<td align="right"><spring:message code="lts.acq.numberSelection.section" arguments="" htmlEscape="false"/> :</td>
														<td><form:hidden
																path="pipbInfo.rolloutAddress.sectionCode" /> <form:hidden
																path="pipbInfo.rolloutAddress.sectionDesc" /> <form:hidden
																path="pipbInfo.address.sectionCode" id="sectionCode" />
															<form:input maxlength="50"
																path="pipbInfo.address.sectionDesc" id="sectionDesc" />
														</td>
														<td align="right"><spring:message code="lts.acq.numberSelection.district" arguments="" htmlEscape="false"/> :</td>
														<td><form:hidden
																path="pipbInfo.rolloutAddress.districtCode" /> <form:hidden
																path="pipbInfo.rolloutAddress.districtDesc" /> <form:hidden
																path="pipbInfo.address.districtCode" id="districtCode" />
															<form:input maxlength="50"
																path="pipbInfo.address.districtDesc" id="districtDesc" />
														</td>
														<td align="right"><spring:message code="lts.acq.numberSelection.area" arguments="" htmlEscape="false"/> :</td>
														<td><form:hidden
																path="pipbInfo.rolloutAddress.areaCode" /> <form:hidden
																path="pipbInfo.rolloutAddress.areaDesc" /> <form:hidden
																path="pipbInfo.address.areaCode" id="areaCode" /> <form:input
																maxlength="50" path="pipbInfo.address.areaDesc"
																id="areaDesc" /></td>
													</tr>
													<tr>
														<td colspan="2"><form:hidden
																path="pipbInfo.rolloutAddress.serviceBoundary" /> <form:hidden
																path="pipbInfo.address.serviceBoundary"
																id="serviceBoundary" /></td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td></td>
							<td><div align="left">
									<b>*<spring:message code="lts.acq.numberSelection.anyDuplexat2N" arguments="" htmlEscape="false"/> </b>
									<form:radiobutton path="pipbInfo.duplexIndStr" value="Y"
										disabled="${isRecallOrder}" />
									<spring:message code="lts.acq.personalInfo.yes" htmlEscape="false"/>
									<form:radiobutton path="pipbInfo.duplexIndStr" value="N"
										disabled="${isRecallOrder}" />
									<spring:message code="lts.acq.personalInfo.no" htmlEscape="false"/> &nbsp;
									<form:errors path="pipbInfo.duplexIndStr" cssClass="error" /></td>
						</tr>

						<tr id="duplexDiv1">
							<td></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <form:radiobutton
									path="pipbInfo.duplexRadio" value="disconnect"
									disabled="${isRecallOrder}" /> <spring:message code="lts.acq.numberSelection.disconnect" arguments="" htmlEscape="false"/>
							</td>
						</tr>
						<tr id="duplexDiv2">
							<td></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <form:radiobutton
									path="pipbInfo.duplexRadio" value="retain"
									disabled="${isRecallOrder}" /> <spring:message code="lts.acq.numberSelection.retain" arguments="" htmlEscape="false"/>
							</td>
						</tr>
						<tr id="duplexDiv3">
							<td></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <form:radiobutton
									id="portInTogether" path="pipbInfo.duplexRadio"
									value="portInTogether" disabled="${isRecallOrder}" /> <spring:message code="lts.acq.numberSelection.portInTogether" arguments="" htmlEscape="false"/>
							</td>
						</tr>
						<tr id="duplexDiv4">
							<td></td>
							<td colspan=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<spring:message code="lts.acq.numberSelection.duplexDN" arguments="" htmlEscape="false"/> <form:input path="pipbInfo.duplexDn" maxlength="8"
									readonly="${isRecallOrder}" /> <br>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <form:errors
									path="pipbInfo.duplexRadio" cssClass="error" /> <form:errors
									path="pipbInfo.duplexDn" cssClass="error" />
							</td>
						</tr>

					</table>

				</div>
				<div id="s_line_text"></div>
				<div id="divIncludeSrvNumOnExDir">
				<table width="100%" border="0" cellspacing="4" cellpadding="6"
					class="contenttext">
					<tr>
						<td width="10%"></td>
						<td><div align="left">
								<form:checkbox id="includeSrvNumOnExDir"
									path="includeSrvNumOnExDir" />
								<b> <spring:message code="lts.acq.numberSelection.exDirectory" arguments="" htmlEscape="false"/> </b>
							</div></td>
					</tr>
					<c:if test="${isBrCust}">
						<tr>
							<td width="10%"></td>
							<td><div align="left">
									<b><spring:message code="lts.acq.numberSelection.directoryName" arguments="" htmlEscape="false"/> <form:input id="directoryName"
											path="directoryName" /></b>
									<form:errors path="directoryName" cssClass="error" />
								</div></td>
						</tr>
					</c:if>
				</table>
				</div>
			</td>
		</tr>
	</table>
</form:form>
<br>
<c:if test="${isNowDrgDownTime=='Y'}">
	<div id="warning_drgDownTime" class="ui-widget"
		style="visibility: visible;">
		<div class="ui-state-highlight ui-corner-all"
			style="padding: 0 .7em; margin-left: 25%; width: 50%;">
			<p>
				<span class="ui-icon ui-icon-info"
					style="float: left; margin-right: .3em;"></span>
			</p>
			<div id="drgDownTimeMsg" class="contenttext">
				<spring:message code="lts.acq.address.DRGDowntime" htmlEscape="false"/>:<br />
				<ul>
					<li><spring:message code="lts.acq.address.notAllowSignOff" htmlEscape="false"/></li>
					<li><spring:message code="lts.acq.address.salesNeedRecall" htmlEscape="false"/></li>
				</ul>
			</div>
			<p></p>
		</div>
	</div>
</c:if>
<br>
<c:if test="${not empty requestScope.errorMsgList}">
	<div id="errorMsgDiv">
		<br>
		<table width="90%" border="0">
			<tr>
				<td width="10%">&nbsp;</td>
				<td width="80%" align="left">
					<div id="error_Msg_div" class="ui-widget"
						style="visibility: visible;">
						<div class="ui-state-error ui-corner-all"
							style="margin-top: 20px; padding: 0 .7em;">
							<c:if test="${not empty requestScope.errorMsgList}">
								<c:forEach items="${requestScope.errorMsgList }" var="errorMsg">
									<p>
										<span class="ui-icon ui-icon-alert"
											style="float: left; margin-right: .3em;"></span>
									</p>
									<div class="contenttext">
										<c:out value="${errorMsg}"></c:out>
									</div>
									<p></p>
								</c:forEach>
							</c:if>
						</div>
					</div>
				</td>
				<td width="10%">&nbsp;</td>
			</tr>
		</table>
	</div>
</c:if>

<div id="errorMsgDiv2" style="display:none;">
	<br>
	<table width="90%" border="0">
		<tr>
			<td width="10%">&nbsp;</td>
			<td width="80%" align="left">
				<div id="error_Msg_div2" class="ui-widget"
					style="visibility: visible;">
					<div class="ui-state-error ui-corner-all"
						style="margin-top: 20px; padding: 0 .7em;">						
						<p>
							<span class="ui-icon ui-icon-alert"
								style="float: left; margin-right: .3em;"></span>
						</p>
						<div class="contenttext" id="errorMsg2">
						</div>
						<p></p>
					</div>
				</div>
			</td>
			<td width="10%">&nbsp;</td>
		</tr>
	</table>
</div>
	
<div id="continueDiv">
	<table width="100%" border="0" cellspacing="0">
		<tr>
			<td align="right"><br> <a id="continueBtn" href="#"><div
						class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a></td>
		</tr>
	</table>
</div>

<script type="text/javascript">
var sbOrderId = "${sbOrderId}";
var isChannelDS = "${isChannelDS}";
var isNowDrgDownTime = "${isNowDrgDownTime}";
var isContainPreInstallVAS = "${isContainPreInstallVAS}";

var plsInputReservedDN = '<spring:message code="lts.acq.numberSelection.plsInputReservedDN" arguments="" htmlEscape="false"/>';
var invalidReservedDN = '<spring:message code="lts.acq.numberSelection.invalidReservedDN" arguments="" htmlEscape="false"/>';
var plsInputPIPBDN = '<spring:message code="lts.acq.numberSelection.plsInputPIPBDN" arguments="" htmlEscape="false"/>';
var invalidPIPBDN = '<spring:message code="lts.acq.numberSelection.invalidPIPBDN" arguments="" htmlEscape="false"/>';
var invalidDuplexDN = '<spring:message code="lts.acq.numberSelection.invalidDuplexDN" arguments="" htmlEscape="false"/>';

var invalidServiceNumber = '<spring:message code="lts.acq.numberSelection.invalidServiceNumber" arguments="" htmlEscape="false"/>';
var reuseSocketMandatory = '<spring:message code="lts.acq.numberSelection.reuseSocketMandatory" arguments="" htmlEscape="false"/>';
var waiveChargeForDNChangeMan = '<spring:message code="lts.acq.numberSelection.waiveChargeForDNChangeMan" arguments="" htmlEscape="false"/>';


$(ltsacqnumselectionandpipb.actionPerform);		

function GetURLParameter()
{
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
	vars[key] = value;
	});
	return vars;
}

var the_error_msg = GetURLParameter()["errorMsgList"];
$(document).ready(function(){	
	if(the_error_msg!=null)
	{
		$("#errorMsgDiv2").show();
		$("#errorMsg2").text(decodeURIComponent(the_error_msg.replace(/\+/g, '%20')));
	}
	else
	{
		$("#errorMsgDiv2").hide();
	}
});
</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>
