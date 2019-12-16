<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript"
	src="js/web/lts/acq/ltsacqsalesinfo.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="3" />
</jsp:include>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-ui-1.8.16.custom.min.js"></script>
<c:set var="isOrderSubmitted"
	value="${sessionScope.sessionLtsOrderCapture.sbOrder != null}" />

<form:form method="POST" action="#" id="salesinfoform" name="ltsSalesInfoForm" commandName="ltssalesinfo" autocomplete="off">
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<input type="hidden" id="errorMsg" />
	<form:hidden path="boc" />
	<form:hidden path="salesCenter" />
	<table width="100%" class="paper_w2 round_10">
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="1"
					class="paper_w2 round_10">
					<tr>
						<div id="s_line_text"><spring:message code="lts.acq.salesInfo.salesInfo" htmlEscape="false"/></div>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="4" class="paper_w2">
					<tr>
						<td width="30%">
							<div align="right"></div>
						</td>
						<td width="20%">&nbsp;</td>
						<td width="20%">&nbsp;</td>
						<td width="30%">&nbsp;</td>
					</tr>
					<tr>
						<td>
							<div align="right">
								<b><spring:message code="lts.acq.salesInfo.channelApplicationMethod" htmlEscape="false"/><span class="contenttext_red">*</span>
									:
								</b>
							</div>
						</td>
						<td><form:input id="salesChannel" cssClass="retailField"
								path="salesChannel" readonly="true" /> <form:select
								id="imsApplicationMethod" cssClass="ccField"
								path="imsApplicationMethod">
								<form:options items="${imsApplMthds}" itemValue="itemKey"
									itemLabel="itemKey" />
							</form:select> <form:errors path="salesChannel" cssClass="error" /></td>
						<c:if test="${ ltssalesinfo.displayReferee }">
							<td>
								<div align="right">
									<b><spring:message code="lts.acq.salesInfo.refStaffID" htmlEscape="false"/></b>
								</div>
							</td>
							<td><form:input id="refStaffId" cssClass="retailField"
									path="refStaffId" readonly="true" /> <%-- <form:input cssClass="refSales" id="refStaffId"  path="refStaffId" readonly="true"/> --%>
								<form:errors path="refStaffId" cssClass="error" /></td>
						</c:if>
					</tr>
					<tr>
						<td>
							<div align="right">
								<b><spring:message code="lts.acq.salesInfo.teamSourceShopCode" htmlEscape="false"/><span class="contenttext_red">*</span>
									:
								</b>
							</div>
						</td>
						<td><form:input id="salesTeam" cssClass="retailField"
								path="salesTeam" readonly="true" /> <input type="hidden"
							id="sourceCode" value="${ltssalesinfo.sourceCode }" /> <form:select
								id="imsSource" cssClass="ccField" path="imsSource">
								<c:choose>
									<c:when test="${empty ltssalesinfo.sourceCode}">
										<form:option value="" label="-- SELECT --" />
									</c:when>
									<c:otherwise>
										<form:option value="${ltssalesinfo.sourceCode }"
											label="${ltssalesinfo.sourceCode }" />
									</c:otherwise>
								</c:choose>
								<form:options items="${defaultImsSources}" />
							</form:select> <form:errors path="salesTeam" cssClass="error" /></td>
						<c:if test="${ ltssalesinfo.displayReferee }">
							<td>
								<div align="right">
									<b><spring:message code="lts.acq.salesInfo.refName" htmlEscape="false"/> :</b>
								</div>
							</td>
							<td><form:input cssClass="refSales" id="refStaffName"
									path="refStaffName" readonly="false" /> <!--input type="button" value="Refresh" id="refStaffNameRfshBtn"/-->

							</td>
						</c:if>
					</tr>
					<tr>
						<td>
							<div align="right">
								<b><spring:message code="lts.acq.salesInfo.staffNo" htmlEscape="false"/><span class="contenttext_red">*</span> :
								</b>
							</div>
						</td>
						<td><form:input id="staffId" path="staffId"
								readonly="${isOrderSubmitted}" /> <!-- <input type="button" value="Refresh" id="salesLkupBtn"/> -->
							<form:errors path="staffId" cssClass="error" /></td>
						<c:if test="${ ltssalesinfo.displayReferee }">
							<td>
								<div align="right">
									<b><spring:message code="lts.acq.salesInfo.refSalesTeam" htmlEscape="false"/> :</b>
								</div>
							</td>
							<td><form:input cssClass="refSales" id="refSalesTeam"
									path="refSalesTeam" readonly="false" /> <form:errors
									path="refSalesTeam" cssClass="error" /></td>
						</c:if>
					</tr>
					<tr>
						<td>
							<div align="right">
								<b><spring:message code="lts.acq.salesInfo.staffName" htmlEscape="false"/><span class="contenttext_red">*</span> :
								</b>
							</div>
						</td>
						<td><form:input id="staffName" path="staffName"
								readonly="true" /> <form:errors path="staffName"
								cssClass="error" /></td>
						<c:if test="${ ltssalesinfo.displayReferee }">
							<td>
								<div align="right">
									<b><spring:message code="lts.acq.salesInfo.refSalesCenter" htmlEscape="false"/> :</b>
								</div>
							</td>
							<td><form:input cssClass="refSales" id="refSalesCenter"
									path="refSalesCenter" readonly="false" /> <form:errors
									path="refSalesCenter" cssClass="error" /></td>
						</c:if>
					</tr>
					<tr>
						<td>
							<div align="right">
								<b><spring:message code="lts.acq.salesInfo.salesmenCode" htmlEscape="false"/><c:if
										test="${!sessionScope.sessionLtsOrderCapture.channelCs}">
										<span class="contenttext_red">*</span>
									</c:if> :
								</b>
							</div>
						</td>
						<td><form:input id="salesCode" path="salesCode"
								maxlength="10" readonly="true" /> <form:errors path="salesCode"
								cssClass="error" /></td>
					</tr>
					<tr>
						<td>
							<div align="right">
								<b><spring:message code="lts.acq.salesInfo.salesContactNo" htmlEscape="false"/><span class="contenttext_red">*</span> :
								</b>
							</div>
						</td>
						<td><form:input id="salesContact" path="salesContact"
								readonly="${!(sessionScope.sessionLtsOrderCapture.channelCs || sessionScope.sessionLtsOrderCapture.channelPremier)}" />
							<form:errors path="salesContact" cssClass="error" /></td>
					</tr>
					<c:if test="${!ltssalesinfo.directSales}">
						<tr class="ccRow">
							<td>
								<div align="right">
									<b><spring:message code="lts.acq.salesInfo.callDate" htmlEscape="false"/> :</b>
								</div>
							</td>
							<td><form:input id="date" path="date" readonly="true" />
								<div class="func_button">
									<a id="clearDateBtn" style="color: white" class="clearDateBtn"
										href="#">Clear</a>
								</div> <form:errors path="date" cssClass="error" /></td>
						</tr>
						<tr class="ccRow">
							<td>
								<div align="right">
									<b><spring:message code="lts.acq.salesInfo.callTime" htmlEscape="false"/> :</b>
								</div>
							</td>
							<td><form:input id="time" path="time" /> <form:errors
									path="time" cssClass="error" /></td>
						</tr>
						<tr class="ccRow">
							<td>
								<div align="right">
									<b><spring:message code="lts.acq.salesInfo.position" htmlEscape="false"/> :</b>
								</div>
							</td>
							<td><form:input id="position" path="position"
									maxlength="40" /> <form:errors path="position" cssClass="error" />
							</td>
						</tr>
						<tr class="ccRow">
							<td>
								<div align="right">
									<b><spring:message code="lts.acq.salesInfo.jobName" htmlEscape="false"/> :</b>
								</div>
							</td>
							<td><form:input id="jobName" path="jobName" maxlength="40" />
								<form:errors path="jobName" cssClass="error" /></td>
						</tr>
					</c:if>
					<!-- 
				<tr>
					<td >
						<div align="right"><b>DNIS  :</b></div>
					</td>
					<td >
					    <form:select id="dnis"  path="dnis">
							<form:option value="" label="-- SELECT --" />
							<form:options items="${svcDnisList}" itemLabel="itemValue" itemValue="itemKey"/>
						</form:select>
						<form:errors path="dnis" cssClass="error"/>	
					</td>
				</tr>
				-->
					<tr>
						<td>
							<div align="right"></div>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<table width="100%" border="0" cellspacing="0"
		class="paper_w2 round_10">
		<tr>
			<td align="right"><br /> <a id="submit" href="#"><div
						class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a></td>
		</tr>
	</table>

</form:form>

<script language="javascript">
	var isRetail = ${sessionScope.bomsalesuser.channelId == 1};
	var isCc = ${sessionScope.bomsalesuser.channelId == 2};
	var isDs = ${ltssalesinfo.directSales};
	var modifySalesInfo = ${ltssalesinfo.modifySalesInfo};
	$(".refSales").val("");
	$(ltssalesinfo.actionPerform);
</script>


<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>
