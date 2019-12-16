<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.LinkedHashMap,java.util.Map;"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	String sbuid = StringUtils.defaultIfEmpty((String)request.getSession().getAttribute(LtsConstant.SESSION_SBUID), "") ;
  	Map progressMap = new LinkedHashMap();
	progressMap.put(1, new String[] {"lts.acq.topmenu.address", "ltsacqaddressrollout.html?sbuid=" + sbuid});
	progressMap.put(2, new String[] {"lts.acq.topmenu.basket", "ltsacqbasketselection.html?sbuid=" + sbuid});
	progressMap.put(3, new String[] {"lts.acq.topmenu.sales", "ltsacqsalesinfo.html?sbuid=" + sbuid});
	progressMap.put(4, new String[] {"lts.acq.topmenu.cust", "ltsacqpersonalinfo.html?sbuid=" + sbuid});
	progressMap.put(5, new String[] {"lts.acq.topmenu.modem", "ltsacqmodemarrangement.html?sbuid=" + sbuid});
	progressMap.put(6, new String[] {"lts.acq.topmenu.acct", "ltsacqaccountselection.html?sbuid=" + sbuid});
	progressMap.put(7, new String[] {"lts.acq.topmenu.payment", "ltsacqpaymentmethod.html?sbuid=" + sbuid});
	progressMap.put(8, new String[] {"lts.acq.topmenu.number", "ltsacqnumselectionandpipb.html?sbuid=" + sbuid});
	progressMap.put(9, new String[] {"lts.acq.topmenu.appointment", "ltsacqappointment.html?sbuid=" + sbuid});
	progressMap.put(10, new String[] {"lts.acq.topmenu.distrubutionMethod", "ltsacqsupportdoc.html?sbuid=" + sbuid});
	progressMap.put(11, new String[] {"lts.acq.topmenu.summary", "ltsacqsummary.html?sbuid=" + sbuid});
	progressMap.put(12, new String[] {"lts.acq.topmenu.done", ""});
	pageContext.setAttribute("progressMap", progressMap);  

%>
<script type="text/javascript">
function clubDetail(event){
	window.open('ltsclubmembershipenq.html', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=384,width=1024');
	if (event.preventDefault) {
        event.preventDefault();
  	} else {
        event.returnValue = false;
  	}
}
</script>
<div id="progressBar">
	<table width="100%">
		<tr>
			<td width="1%"></td>
			<td width="99%">
				<div class="wizard-steps">
					<c:if test='${not empty param.step}'>
						<c:forEach var="entry" items="${progressMap}" varStatus="status">
							<c:choose>
								<c:when test="${param.step < entry.key}">
									<div>
										<a href="#"><span> ${entry.key} </span><spring:message code="${entry.value[0]}" htmlEscape="false"/></a>
									</div>
								</c:when>
								<c:when test="${param.step == entry.key}">
									<div class="active-step">
										<a href="${entry.value[1]}"><span>${entry.key}</span>
											<spring:message code="${entry.value[0]}" htmlEscape="false"/></a>
									</div>
								</c:when>
								<c:when test="${param.step > entry.key}">
									<c:choose>
										<c:when test="${((entry.key < 7 || entry.key == 12) 
															&& sessionScope.sessionLtsAcqOrderCapture.sbOrder != null)
															|| sessionScope.sessionLtsAcqOrderCapture.orderAction == 'RECALL_N_UPDATE_PREPAYMENT'}">
											<div class="completed-step">
												<a href="#"><span>${entry.key}</span><spring:message code="${entry.value[0]}" htmlEscape="false"/></a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="completed-step">
												<a href="${entry.value[1]}"><span>${entry.key}</span>
													<spring:message code="${entry.value[0]}" htmlEscape="false"/></a>
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
							</c:choose>
						</c:forEach>
					</c:if>
				</div>
		</tr>
		<c:if test='${not empty param.step && param.step > 4}'>
		<tr>
			<td width="1%"></td>
			<td align="right">
				<br>
				<a id="clubDetailButton" onclick="clubDetail(event)" class="greenbutton" href="#" ><spring:message code="lts.acq.clubMemEnq.clubMemEnq" arguments="" htmlEscape="false"/></a>
			</td>
		</tr>
		</c:if>
	</table>
</div>
<br>

