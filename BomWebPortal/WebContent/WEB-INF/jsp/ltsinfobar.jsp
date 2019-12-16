<%@page import="com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@page import="com.bomwebportal.lts.util.LtsSessionHelper"%>
<%@page import="com.bomwebportal.lts.dto.OrderCaptureDTO"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	
	String title = "lts.infobar.sblts";
	OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
	TerminateOrderCaptureDTO terminateOrderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
	
	if (orderCapture != null) {
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())) {
			title = "lts.infobar.eyeUpgrade";
		}
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			if (LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())){
				title = "lts.infobar.standalonevas";	
			}else{
				if (StringUtils.isNotBlank(orderCapture.getLtsServiceProfile().getExistEyeType())) {
					title = "lts.infobar.eyeRenew";	
				}
				else {
					title = "lts.infobar.delRenew";
				}
			}
		}
	}
	else if (terminateOrderCapture != null) {
		if (StringUtils.isNotBlank(terminateOrderCapture.getLtsServiceProfile().getExistEyeType())) {
			title = "lts.infobar.eyeTerminate";	
		}
		else {
			title = "lts.infobar.delTerminate";
		}
	}

	pageContext.setAttribute("title", title);  
%>

<script type="text/javascript">
	
	$(document).ready(function() {
		$("a#quitBtn").click(function(event) {
			var quitPromptTxt = $("#quitPromptTxt").val();
			if (!confirm(quitPromptTxt)) {
				event.preventDefault();
				return;
			}
			window.opener=null;
			window.close();
			
		});
		
	});
	

	function clubDetail(event){
		window.open('ltsclubmembershipenq.html', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=384,width=1024');
		if (event.preventDefault) {
	        event.preventDefault();
	  	} else {
	        event.returnValue = false;
	  	}
	}
	
	</script>
<table width="100%">
<c:set var="quitPromptTxt"><spring:message code="lts.infobar.quitPrompt" text="All inputted information will be cleared. Sure to continue?"/></c:set>
<input type="hidden" id="quitPromptTxt" value="${quitPromptTxt}" />
<tr>
	<td><div id="line_text"><spring:message code="${title}" text="Springboard LTS"/></div></td>
	<td width="8%">
		<div class="func_button"><a id="quitBtn" href="#"><spring:message code="lts.custenq.quit" /></a></div>
	</td>
</table>	
			

<div id="infoBar">
<table width="100%" border="0" class="paper_g">

<c:set var="profile" value="${sessionScope.sessionLtsOrderCapture != null ? sessionScope.sessionLtsOrderCapture.ltsServiceProfile : 
				sessionScope.sessionLtsTerminateOrderCapture != null ? sessionScope.sessionLtsTerminateOrderCapture.ltsServiceProfile : null}"></c:set>

<tr>
	<c:if test='${not empty profile}'>
		<td width="20%"><spring:message code="lts.custenq.custName" />: 
			<span class="bold">
			<c:choose>
				<c:when test="${ profile.primaryCust.docType == 'BS' }">
					<c:out value="${profile.primaryCust.companyName}"/> 
				</c:when>
				<c:otherwise>
					<c:out value="${profile.primaryCust.title}"/>&nbsp;<c:out value="${profile.primaryCust.lastName}"/>&nbsp;<c:out value="${profile.primaryCust.firstName}"/>
				</c:otherwise>
			</c:choose>
			</span>
		</td>
		<td align="left" width="60%">
			<spring:message code="lts.custenq.dn" />: <span class="bold"><c:out value="${fn:substring(profile.searchCriteriaDn, 4, 12)}"/></span>
		</td>	
		<td align="right">
			<a id="clubDetailButton" onclick="clubDetail(event)" class="greenbutton" href="#" >Club Member Enq</a>
		</td>
	</c:if>
</tr>
</table>
</div>