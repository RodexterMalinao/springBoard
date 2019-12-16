<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.LinkedHashMap,java.util.Map;" %>


<%
	String sbuid = StringUtils.defaultIfEmpty((String)request.getSession().getAttribute(LtsConstant.SESSION_SBUID), "") ;
  	Map progressMap = new LinkedHashMap();
	progressMap.put(1, new String[] {"Service&nbsp;Selection", "ltsterminateserviceselection.html?sbuid=" + sbuid});
	progressMap.put(2, new String[] {"Sales&nbsp;Info", "ltsterminatesalesinfo.html?sbuid=" + sbuid});
	progressMap.put(3, new String[] {"Billing&nbsp;Info", "ltsterminatebillinginfo.html?sbuid=" + sbuid});
	progressMap.put(4, new String[] {"Appointment", "ltsterminateappointment.html?sbuid=" + sbuid});
	progressMap.put(5, new String[] {"Support Doc", "ltsterminatesupportdoc.html?sbuid=" + sbuid});
	progressMap.put(6, new String[] {"Summary", "ltsterminatesummary.html?sbuid="+ sbuid});
	pageContext.setAttribute("progressMap", progressMap);  
%>

<div id="progressBar">
<table width="100%"><tr><td width="1%"></td><td width="99%">
<div class="wizard-steps">
	<c:if test='${not empty param.step}'>
		<c:forEach var="entry" items="${progressMap}" varStatus="status">
			<c:choose>
				<c:when test="${param.step < entry.key}">
	      			<div><a href="#"><span> ${entry.key} </span>${entry.value[0]}</a></div>
	      		</c:when>
	      		<c:when test="${param.step == entry.key}">
	      			<div class="active-step"><a href="${entry.value[1]}"><span>${entry.key}</span> ${entry.value[0]}</a></div>
	      		</c:when>
				<c:when test="${param.step > entry.key}">
					<c:choose>
						<c:when test="${sessionScope.sessionLtsTerminateOrderCapture.sbOrder != null 
										&& ((entry.key < 2 && sessionScope.bomsalesuser.category == 'SUPPORT')
											|| (entry.key < 3 && sessionScope.bomsalesuser.category != 'SUPPORT'))}">
							<div class="completed-step"><a href="#"><span>${entry.key}</span> ${entry.value[0]}</a></div>
						</c:when>
						<c:otherwise>
							<div class="completed-step"><a href="${entry.value[1]}"><span>${entry.key}</span> ${entry.value[0]}</a></div>
						</c:otherwise>
					</c:choose>
	      		</c:when>	      		
			</c:choose>
		</c:forEach>
	</c:if>
</div>
</td>
</tr></table>  
</div>
<br>
