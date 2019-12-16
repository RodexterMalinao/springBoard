<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/acq/ltsacqnumconfirmation.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp" >
<jsp:param name="step" value="8" />
</jsp:include>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<form:form method="POST" id="ltsacqnumconfirmationForm" name="ltsacqnumconfirmationForm" commandName="ltsacqnumconfirmationCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="formAction"/>
<table width="100%" class="paper_w2 round_10">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
			    <td><div id="s_line_text"><spring:message code="lts.acq.numConfirm.numConfirm" htmlEscape="false"/></div></td>
			</tr> 
		</table>
		
		<c:if test="${not empty ltsacqnumconfirmationCmd.reservedDnList && !singleChoiceForDnPoolCase}">
		<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">		   
		   <tr>
		   <td width="30%" valign="top">
		   <table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		   <tr>
			    <td width="100%"><div align="right"><spring:message code="lts.acq.numConfirm.plsSelectNConfirmNewDNyouLocked" htmlEscape="false"/></b></div></td>
		   </tr>
		   </table>
		   </td>		   
		   <td width="70%" valign="top">
			<c:forEach items="${ltsacqnumconfirmationCmd.reservedDnList}" var="itemList" varStatus="itemListStatus">
		   <div id="srvNumDiv">
		   <table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		   <tr>
		   <td width="100%">
		   <form:radiobutton path="newDn" value="${itemList.srvNum}" cssClass="searchCriteria" />${itemList.displaySrvNum}
		   </td>		   
		   </tr>
		   </table>
		   </div>
		   </c:forEach>		    		    
		   </td>
		   </tr>   
		</table>
		</c:if>
		
		<c:if test="${not empty ltsacqnumconfirmationCmd.reservedDnList && singleChoiceForDnPoolCase}">
		<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">		   
		   <tr>
		   <td width="30%" valign="top">
		   <table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		   <tr>
			    <td width="100%"><div align="right"><b><spring:message code="lts.acq.numConfirm.plsConfirmNewDNyouLocked" htmlEscape="false"/></b></div></td>
		   </tr>
		   </table>
		   </td>		   
		   <td width="70%" valign="top">
			<div id="srvNumDiv" align="left">
		   <table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		   <tr>
		   <td width="100%">${ltsacqnumconfirmationCmd.newDn}</td>		   
		   </tr>
		   </table>
		   </div>  		    
		   </td>
		   </tr>   
		</table>
		</c:if>
		
		<c:if test="${empty ltsacqnumconfirmationCmd.reservedDnList && not empty ltsacqnumconfirmationCmd.newDn}">
		<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">		   
		   <tr>
		   <td width="30%" valign="top">
		   <table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		   <tr>
			    <td width="100%"><div align="right"><b><spring:message code="lts.acq.numConfirm.plsConfirmNewReservedDN" htmlEscape="false"/></b></div></td>
		   </tr>
		   </table>
		   </td>		   
		   <td width="70%" valign="top">
			<div id="srvNumDiv" align="left">
		   <table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		   <tr>
		   <td width="100%">${ltsacqnumconfirmationCmd.newDn}</td>		   
		   </tr>
		   </table>
		   </div>  		    
		   </td>
		   </tr>   
		</table>
		</c:if>
		
		<c:if test="${not empty ltsacqnumconfirmationCmd.pipbDn}">
		<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">		   
		   <tr>
		   <td width="30%" valign="top">
		   <table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		   <tr>
			    <td width="100%"><div align="right"><b><spring:message code="lts.acq.numConfirm.plsConfirmPIPBDN" htmlEscape="false"/></b></div></td>
		   </tr>
		   </table>
		   </td>		   
		   <td width="70%" valign="top">
			<div id="srvNumDiv" align="left">
		   <table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		   <tr>
		   <td width="100%">${ltsacqnumconfirmationCmd.pipbDn}
		   <c:if test="${not empty ltsacqnumconfirmationCmd.duplexDn}"> (Duplex DN: ${ltsacqnumconfirmationCmd.duplexDn})</c:if>		   
		   </td>		   
		   </tr>
		   </table>
		   </div>  		    
		   </td>
		   </tr>   
		</table>
		</c:if>
		   
</table>


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

<c:if test="${ not empty param.outQuoErrMsgList }">
<div id="outQuoErrMsgDiv">
<br>
	<table width="90%" border="0">
		<tr>
			<td width="10%">&nbsp;</td>											
			<td width="80%" align="left">
				<div id="error_Msg_div" class="ui-widget" style="visibility: visible;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<c:forEach items="${param.outQuoErrMsgList }" var="outQuoErr">
							<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
								<div  class="contenttext">
									<c:out value="${outQuoErr}"></c:out>
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


<table width="100%" class="paper_w2 round_10">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
			    <td><div id="s_line_text"><spring:message code="lts.acq.common.suspendRemark" htmlEscape="false"/></div></td>
			</tr> 
			<table width="100%" cellspacing="1" border="0" class="contenttext" >
				 <tr>
			     <td>&nbsp;</td>
			     </tr>
				 <tr>
				    <td width="10%">&nbsp;</td>
					<td>
						<form:textarea path="suspendRemarks" rows="6" cols="100" cssStyle="resize:none;"></form:textarea>
					    <form:errors path="suspendRemarks" cssClass="error"/>
					</td>
				</tr>
				<tr>
			    <td>&nbsp;</td>
			    </tr>
			</table>
		</table>
		</td>
	</tr>
</table>

<br>
<div id="continueDiv">
<table width="100%" border="0" cellspacing="0" class="contenttext">
<tr>
	<td align="right" > 
		<form:errors path="suspendReason" cssClass="error"/>
			<b><spring:message code="lts.acq.common.suspendReason" htmlEscape="false"/> </b>
		<form:select path="suspendReason" id="suspendReason">
			<form:option value="NONE"><spring:message code="lts.acq.common.selectInSelector" htmlEscape="false"/></form:option>
			<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
		</form:select>
		<a href="#" id="suspend" class="nextbutton"><div class="button"><spring:message code="lts.acq.common.suspend" htmlEscape="false"/></div></a>
		<a href="#" id="continueBtn" class="nextbutton"><div class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a> 
	</td>
</tr>
</table>
</div>

</form:form>

<script type="text/javascript">
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

	
	$(ltsacqnumconfirmation.actionPerform);		
</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp" %>