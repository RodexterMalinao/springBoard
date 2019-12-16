<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltsdeviceselection.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="5" />
</jsp:include>

<form:form method="POST" id="ltsDeviceSelectionForm" name="ltsDeviceSelectionForm" commandName="ltsDeviceSelectionCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="selectedDeviceType" />

<div id="s_line_text">Device Selection</div>
<table width="98%" border="0" align="center" cellspacing="10" cellpadding="10" >
	<tr>
		<td align="left">
			<c:forEach items="${eyeDeviceList}" var="device">
				<div class="imgcaption round_10" >
					<p align="left">&nbsp;<b><c:out value="${device.desc}" /></b></p> 
					<p align="center"><a href="${device.type}" class="imgdetail selectDevice"><img class="imgleft" src="${device.imagePath}" width="180" alt="${device.desc}"/></a></p>
					<br>
				</div>
			</c:forEach>
		</td>
	</tr>
</table>
</form:form>

<script type="text/javascript">
	$(ltsdeviceselection.actionPerform);		
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>