<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<script type="text/javascript" src="js/web/lts/disconnect/ltsterminatecustomerinquiry.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<br/>

<form:form method="POST" id="ltsTerminateCustomerInquiryForm" name="ltsTerminateCustomerInquiryForm" commandName="ltsTerminateCustomerInquiryCmd" autocomplete="off">
<form:hidden path="formAction"/>
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<c:set var="profile" value="${sessionScope.sessionLtsTerminateOrderCapture.ltsServiceProfile}" />
<c:set var="profileAddressRollout" value="${profile.address.addressRollout}" />	

<table width="98%" class="paper_w2 round_10" align="center">
	<tr>
		<td>
			<table width="98%" border="0" cellspacing="1" rules="none" align="center">
				<tr>
					<td><div id="s_line_text">LTS Customer Inquiry</div></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="1" cellpadding="4" class="contenttext" align="center">
				<tr>
					<td width="35%">&nbsp;</td>
					<td width="25%">&nbsp;</td>
					<td width="50%">&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						Service Number* : 
					</td>
					<td>
						<form:input path="serviceNum" id="serviceNum" size="23" cssClass="searchCriteria" />
						<br>
						<form:errors path="serviceNum" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>		
<table width="100%" border="0" cellspacing="0" class="contenttext">
	<tr>
		<td>
			&nbsp;
		</td>
		<td colspan="2">
			<br>
			<div class="func_button">
				<a id="submitSeachBtn" style="color:white"  class="searchCriteria" href="#">Search</a>
			</div>
			&nbsp;&nbsp;
			<div class="func_button">
				<a id="clearSearchBtn" style="color:white" class="searchCriteria" href="#">Clear</a>
			</div>
		</td>
	</tr>
</table>
<table>
	<tr>
		<td colspan="3">
			<table width="100%" >
				<tr>
					<td width="20%">
						&nbsp;
					</td>
					<td width="60%">
						<spring:hasBindErrors name="ltsTerminateCustomerInquiryCmd">
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
					<td width="20%">
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form:form>

<br>

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
var isMsgListEmpty = ${sessionScope.sessionLtsSrvProfileMsg == null };
	$(ltsterminatecustomerinquiry.actionPerform);
</script>


<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>