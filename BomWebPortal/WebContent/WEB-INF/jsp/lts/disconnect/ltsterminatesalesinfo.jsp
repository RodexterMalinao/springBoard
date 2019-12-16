<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/lts/disconnect/ltsterminateprogressbar.jsp" >
    <jsp:param name="step" value="2" />
</jsp:include>
<script type="text/javascript" src="js/web/lts/disconnect/ltsterminatesalesinfo.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script src="js/jquery-ui-1.8.16.custom.min.js"></script>

<c:set var="isOrderSubmitted" value="${sessionScope.sessionLtsTerminateOrderCapture.sbOrder != null}" />
<form:form method="POST" action="#" id="terminatesalesinfoform" commandName="ltsTerminateSalesInfoCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<input type="hidden" id="errorMsg" />
<table width="98%" class="paper_w2 round_10" align="center">
		<tr>
			<td>
			<table width="98%" border="0" cellspacing="1" align="center">
				<tr>
					<td>
					<br>
					<div id="s_line_text">Sales Info</div></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="4" class="contenttext" align="center">
				<tr>
					<td width="30%">
						<div align="right"></div>
					</td>
					<td  width="70%" >&nbsp;</td>
				</tr>
				<tr>
					<td >
					<div align="right">Channel / Application Method* : </div>
					</td>
					<td >
						<form:select id="imsApplicationMethod" cssClass="ccField" path="imsApplicationMethod">
							<form:options items="${imsApplMthds}" itemValue="itemKey" itemLabel="itemKey" />
						</form:select>
						<form:input id="salesChannel" path="salesChannel" cssClass="retailField" readonly="${isOrderSubmitted || sessionScope.bomsalesuser.category == 'FRONTLINE'}"/>
						<form:errors path="salesChannel" cssClass="error"/>		
					</td>
				</tr>
				<tr>
					<td>
					<div align="right">Team / Source / Shop Code* : </div>
					</td>
					<td>
						<form:select id="imsSource" cssClass="ccField" path="imsSource">
							<form:option value="" label="-- SELECT --" />
							<form:options items="${defaultImsSources}" />
						</form:select>
						<form:input id="salesTeam" path="salesTeam" cssClass="retailField" readonly="${isOrderSubmitted || sessionScope.bomsalesuser.category == 'FRONTLINE'}"/>
						<form:errors path="salesTeam" cssClass="error"/>						
					</td>
				</tr>
				<tr>
					<td >
					<div align="right">Staff No.* : </div>
					</td>
					<td >
						<form:input id="staffId" path="staffId" readonly="${isOrderSubmitted || sessionScope.bomsalesuser.category == 'FRONTLINE'}"/>
						<!-- <input type="button" value="Refresh" id="salesLkupBtn"/> -->
						<form:errors path="staffId" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td >
					<div align="right">Staff Name* : </div>
					</td>
					<td >
						<form:input id="staffName" path="staffName" readonly="true"/>
						<form:errors path="staffName" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td >
					<div align="right">Salesmen Code / CMRID<c:if test="${!sessionScope.sessionLtsOrderCapture.channelCs}">*</c:if> : </div>
					</td>
					<td >
						<form:input id="salesCode" path="salesCode" maxlength="10" readonly="true"/>
						<form:errors path="salesCode" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td >
					<div align="right">Sales Contact No.* : </div>
					</td>
					<td >
						<form:input id="salesContact" path="salesContact" readonly="${!(sessionScope.sessionLtsOrderCapture.channelCs || sessionScope.sessionLtsOrderCapture.channelPremier)}"/>	
						<form:errors path="salesContact" cssClass="error"/>						
					</td>
				</tr>
				<tr class="ccRow">
					<td >
					<div align="right">Call Date : </div>
					</td>
					<td>
						<form:input id="date" path="date" readonly="true"/>	
						<div class="func_button">
							<a id="clearDateBtn" style="color:white" href="#">Clear</a>
						</div>
						<form:errors path="date" cssClass="error"/>						
					</td>
				</tr>
				<tr class="ccRow">
					<td >
						<div align="right">Call Time : </div>
					</td>
					<td>
						<form:input id="time" path="time"/>	
						<form:errors path="time" cssClass="error"/>						
					</td>
				</tr>
				<tr class="ccRow">
					<td >
					<div align="right">Position : </div>
					</td>
					<td >
						<form:input id="position" path="position" maxlength="40"/>	
						<form:errors path="position" cssClass="error"/>	
					</td>
				</tr>
				<tr class="ccRow">
					<td >
					<div align="right">Job Name : </div>
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
	</tr>
</table>
<br>
<table width="100%" border="0" cellspacing="0" class="contenttext">
	<tr>
		<td align="right">
			<div class="button">
				<a href="#" id="nextBtn">Continue</a>
			</div>
		</td>
	</tr>
</table>
</form:form>

<script language="javascript">
var isRetail = ${sessionScope.bomsalesuser.channelId == 1};
var isCc = ${sessionScope.bomsalesuser.channelId == 2};
$(ltsterminatesalesinfo.actionPerform);
</script>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>