<%@page import="com.bomwebportal.lts.util.LtsSessionHelper"%>
<%@page import="com.bomwebportal.lts.dto.OrderCaptureDTO"%>
<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.LinkedHashMap,java.util.Map;" %>

<%
	String sbuid = StringUtils.defaultIfEmpty((String)request.getSession().getAttribute(LtsConstant.SESSION_SBUID), "") ;

	OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
	Map progressMap = new LinkedHashMap();
	
	if (orderCapture == null) {
		return;
	}

	// RETENTION
	if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
		progressMap.put(1, new String[] {"lts.mis.custInq", "ltscustomerinquiry.html?sbuid=" + sbuid});
		progressMap.put(2, new String[] {"lts.mis.misc", "ltsmiscellaneous.html?sbuid=" + sbuid});
		progressMap.put(3, new String[] {"lts.mis.offDtls", "ltsbasketselection.html?sbuid=" + sbuid});
		progressMap.put(4, new String[] {"lts.mis.modArra", "ltsrenewmodemarrangement.html?sbuid=" + sbuid});
		progressMap.put(5, new String[] {"lts.mis.custIdifi", "ltscustomeridentification.html?sbuid=" + sbuid});
		progressMap.put(6, new String[] {"lts.mis.salesinfo", "ltssalesinfo.html?sbuid=" + sbuid});
		progressMap.put(7, new String[] {"lts.mis.payment", "ltspayment.html?sbuid=" + sbuid});
		progressMap.put(8, new String[] {"lts.mis.appointment", "ltsappointment.html?sbuid=" + sbuid});
		progressMap.put(9, new String[] {"lts.mis.supportDoc", "ltssupportdoc.html?sbuid=" + sbuid});
		progressMap.put(10, new String[] {"lts.mis.summary", "ltssummary.html?sbuid="+ sbuid});
	}
	
	if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())) {
		progressMap.put(1, new String[] {"lts.mis.custInq", "ltscustomerinquiry.html?sbuid=" + sbuid});
		progressMap.put(2, new String[] {"lts.mis.misc", "ltsmiscellaneous.html?sbuid=" + sbuid});
		progressMap.put(3, new String[] {"lts.mis.addr", "ltsaddressrollout.html?sbuid=" + sbuid});
		progressMap.put(4, new String[] {"lts.mis.modArra", "ltsmodemarrangement.html?sbuid=" + sbuid});
		progressMap.put(5, new String[] {"lts.mis.offDtls", "ltsdeviceselection.html?sbuid=" + sbuid});
		progressMap.put(6, new String[] {"lts.mis.custIdifi", "ltscustomeridentification.html?sbuid=" + sbuid});
		progressMap.put(7, new String[] {"lts.mis.salesinfo", "ltssalesinfo.html?sbuid=" + sbuid});
		progressMap.put(8, new String[] {"lts.mis.payment", "ltspayment.html?sbuid=" + sbuid});
		progressMap.put(9, new String[] {"lts.mis.appointment", "ltsappointment.html?sbuid=" + sbuid});
		progressMap.put(10, new String[] {"lts.mis.supportDoc", "ltssupportdoc.html?sbuid=" + sbuid});
		progressMap.put(11, new String[] {"lts.mis.summary", "ltssummary.html?sbuid="+ sbuid});	
	}

  	
	pageContext.setAttribute("progressMap", progressMap);  
%>
<div id="progressBar">
<table width="100%"><tr><td width="1%"></td><td width="99%">
<div class="wizard-steps">
	<c:if test='${not empty param.step}'>
		<c:forEach var="entry" items="${progressMap}" varStatus="status">
			<c:choose>
				<c:when test="${param.step < entry.key}">
	      			<div><a href="#"><span> ${entry.key} </span><spring:message code="${entry.value[0]}" htmlEscape="false"/></a></div>
	      		</c:when>
	      		<c:when test="${param.step == entry.key}">
	      			<div class="active-step"><a href="${entry.value[1]}"><span>${entry.key}</span> <spring:message code="${entry.value[0]}" htmlEscape="false"/> </a></div>
	      		</c:when>
				<c:when test="${param.step > entry.key}">
					<c:choose>
						<c:when test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBU' && entry.key < 8 && sessionScope.sessionLtsOrderCapture.sbOrder != null}">
							<div class="completed-step"><a href="#"><span>${entry.key}</span> <spring:message code="${entry.value[0]}" htmlEscape="false"/> </a></div>
						</c:when>
						<c:when test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' && entry.key < 7 && sessionScope.sessionLtsOrderCapture.sbOrder != null}">
							<div class="completed-step"><a href="#"><span>${entry.key}</span> <spring:message code="${entry.value[0]}" htmlEscape="false"/> </a></div>
						</c:when>
						<c:otherwise>
							<div class="completed-step"><a href="${entry.value[1]}"><span>${entry.key}</span> <spring:message code="${entry.value[0]}" htmlEscape="false"/></a></div>
						</c:otherwise>
					</c:choose>
	      		</c:when>	      		
			</c:choose>
		</c:forEach>
	</c:if>
</div>
</tr></table>  
</div>
<br>

