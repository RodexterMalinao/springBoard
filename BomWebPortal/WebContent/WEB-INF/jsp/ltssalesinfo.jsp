<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript" src="js/web/lts/ltssalesinfo.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 6 : 7}" />
</jsp:include>

<c:set var="isOrderSubmitted" value="${sessionScope.sessionLtsOrderCapture.sbOrder != null}" />

<div class="cosContent">
<form:form method="POST" action="#" id="salesinfoform" name="ltsSalesInfoForm" commandName="ltssalesinfo" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<input type="hidden" id="errorMsg" />
<form:hidden path="boc" />
<form:hidden path="salesCenter" />

<table width="100%" class="paper_w2 round_10">
		
		<tr>
			<td colspan="3">
				<br/>
				<div id="s_line_text"><spring:message code="lts.salesInfo.salesInfo" text="Sales Info"/> </div>
			</td>
		</tr>
		<tr>
			<td width="10%"></td>
			<td width="45%">
			<table width="100%" border="0" cellspacing="6" cellpadding="4">
				<tr>
					<td width="45%">
					<div align="left"><spring:message code="lts.salesInfo.channelAppMethod" text="Channel / Application Method"/><span class="contenttext_red">*</span> :</div>
					</td>
					<td >
						<form:input id="salesChannel" cssClass="retailField" path="salesChannel" readonly="true"/>
						<form:select id="imsApplicationMethod" cssClass="ccField" path="imsApplicationMethod">
							<form:options items="${imsApplMthds}" itemValue="itemKey" itemLabel="itemKey" />
						</form:select>
						<form:errors path="salesChannel" cssClass="error"/>		
					</td>
				</tr>
				<tr>
					<td >
					<div align="left"><spring:message code="lts.salesInfo.teamSourceShopCode" text="Team / Source / Shop Code"/> <span class="contenttext_red">*</span> :</div>
					</td>
					<td >
						<form:input id="salesTeam" cssClass="retailField" path="salesTeam" readonly="true" />
						<input type="hidden" id="sourceCode" value="${ltssalesinfo.sourceCode }" />
						<form:select id="imsSource" cssClass="ccField" path="imsSource">
							<c:choose>
                               <c:when test="${empty ltssalesinfo.sourceCode}">
                                   <form:option value="" label="-- SELECT --" />
                                </c:when>
                               <c:otherwise>
                                  <form:option value="${ltssalesinfo.sourceCode }" label="${ltssalesinfo.sourceCode }" />
                              </c:otherwise>
                            </c:choose>
					      <form:options items="${defaultImsSources}" />
						</form:select>
						<form:errors path="salesTeam" cssClass="error"/>						
					</td>
				</tr>
				<tr>
					<td >
					<div align="left"><spring:message code="lts.salesInfo.staffNo" text="Staff No."/><span class="contenttext_red">*</span> :</div>
					</td>
					<td >
						<form:input id="staffId" path="staffId" readonly="${isOrderSubmitted}"/>
						<!-- <input type="button" value="Refresh" id="salesLkupBtn"/> -->
						<form:errors path="staffId" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td >
					<div align="left"><spring:message code="lts.salesInfo.staffName" text="Staff Name"/><span class="contenttext_red">*</span> :</div>
					</td>
					<td >
						<form:input id="staffName" path="staffName" readonly="true"/>
						<form:errors path="staffName" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td >
					<div align="left"><spring:message code="lts.salesInfo.CMRID" text="Salesman Code / CMRID"/><c:if test="${!sessionScope.sessionLtsOrderCapture.channelCs}"><span class="contenttext_red">*</span></c:if> :</div>
					</td>
					<td >
						<form:input id="salesCode" path="salesCode" maxlength="10" readonly="true"/>
						<form:errors path="salesCode" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td >
					<div align="left"><spring:message code="lts.salesInfo.salesContactNo" text="Sales Contact No."/><span class="contenttext_red">*</span> :</div>
					</td>
					<td >
						<form:input id="salesContact" path="salesContact" readonly="${!(sessionScope.sessionLtsOrderCapture.channelCs || sessionScope.sessionLtsOrderCapture.channelPremier)}"/>	
						<form:errors path="salesContact" cssClass="error"/>						
					</td>
				</tr>
				<tr class="ccRow">
					<td >
					<div align="left"><spring:message code="lts.salesInfo.callDate" text="Call Date"/> :</div>
					</td>
					<td>
						<form:input id="date" path="date" readonly="true"/>	
						<a id="clearDateBtn" href="#">
							<div class="func_button"><spring:message code="lts.salesInfo.clear" text="Clear"/></div>
						</a>
						<form:errors path="date" cssClass="error"/>						
					</td>
				</tr>
				<tr class="ccRow">
					<td >
						<div align="left"><spring:message code="lts.salesInfo.callTime" text="Call Time"/> :</div>
					</td>
					<td>
						<form:input id="time" path="time"/>	
						<form:errors path="time" cssClass="error"/>						
					</td>
				</tr>
				<tr class="ccRow">
					<td >
					<div align="left"><spring:message code="lts.salesInfo.position" text="Position"/> :</div>
					</td>
					<td >
						<form:input id="position" path="position" maxlength="40"/>	
						<form:errors path="position" cssClass="error"/>	
					</td>
				</tr>
				<tr class="ccRow">
					<td >
					<div align="left"><spring:message code="lts.salesInfo.jobName" text="Job Name"/> :</div>
					</td>
					<td >
						<form:input id="jobName" path="jobName" maxlength="40"/>
						<form:errors path="jobName" cssClass="error"/>						
					</td>
				</tr>
				<tr>
					<td>
						<div align="right"></div>
					</td>
					<td >&nbsp;</td>
				</tr>
			</table>
		</td>
			<td width="45%">
				<table width="100%" border="0" cellspacing="6" cellpadding="4">
					<tr class="ccRow">
						<td width="35%">
							<div align="left"><spring:message code="lts.salesInfo.refSalesId" text="Referee Sales ID"/> :</div>
						</td>
						<td >
							<form:input path="refereeSalesId" />
							<form:errors path="refereeSalesId" cssClass="error"/>
						</td>
					</tr>
					<tr class="ccRow">
						<td>
							<div align="left"><spring:message code="lts.salesInfo.refSalesCentre" text="Referee Sales Center"/> :</div>
						</td>
						<td >
							<form:input path="refereeSalesName"  readonly="true" />
							<form:errors path="refereeSalesName" cssClass="error"/>		
						</td>
					</tr>
					<tr class="ccRow">
						<td>
							<div align="left"><spring:message code="lts.salesInfo.refSalesCentre" text="Referee Sales Center"/> :</div>
						</td>
						<td >
							<form:input path="refereeSalesCenter" readonly="true"/>
							<form:errors path="refereeSalesCenter" cssClass="error"/>		
						</td>
					</tr>
					<tr class="ccRow">
						<td>
							<div align="left"><spring:message code="lts.salesInfo.refSalesTeam" text="Referee Sales Team"/> :</div>
						</td>
						<td >
							<form:input path="refereeSalesTeam" readonly="true"/>
							<form:errors path="refereeSalesTeam" cssClass="error" />		
						</td>
					</tr>															
				</table>
			</td>
		
	</tr>
</table>
<br>
<table width="100%" border="0" cellspacing="0" class="contenttext">
	<tr>
		<td align="right">
			<a id="submit" href="#" ><div class="button"><spring:message code="lts.mis.next" text="Next"/></div></a> 
		</td>
	</tr>
</table>

</form:form> 
</div>

<script language="javascript">
	var isRetail = ${sessionScope.bomsalesuser.channelId == 1};
	var isCc = ${sessionScope.bomsalesuser.channelId == 2};
	$(ltssalesinfo.actionPerform);
</script>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>       			