<%@page import="com.bomwebportal.lts.dto.AcqOrderCaptureDTO"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@page import="com.bomwebportal.lts.util.LtsSessionHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%
	
	String title = "Springboard LTS";

	AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
	
    if (acqOrderCapture != null) {
		if (acqOrderCapture.isEyeOrder()) {
			title = "Springboard eye Acquisition";	
		}
		else {
			title = "Springboard DEL Acquisition";
		}
	}

	pageContext.setAttribute("title", title);  
%>

<script type="text/javascript">
	
	$(document).ready(function() {
		$("a#quitBtn").click(function(event) {
			if (!confirm("All inputted information will be cleared. Sure to continue?")) {
				event.preventDefault();
				return;
			}
			window.opener=null;
			window.close();
			
		});
	});
	</script>
<table width="100%">
<tr>
	<td><div id="line_text">${title}</div></td>
	<td width="5%">
		<div class="func_button"><a id="quitBtn" href="#">Quit</a></div>
	</td>
</table>	
			

<div id="infoBar">
<table width="100%" border="0" class="paper_g">
				
<c:set var="acqprofile" value="${sessionScope.sessionLtsAcqOrderCapture != null ? sessionScope.sessionLtsAcqOrderCapture.customerDetailProfileLtsDTO : null}"></c:set>

<tr>

	<c:if test='${not empty acqprofile}'>
		<td width="20%">Customer Name: 
		<span class="bold">
		<c:choose>
			<c:when test="${ acqprofile.docType == 'BS' }">
				<c:out value="${acqprofile.companyName}"/> 
			</c:when>
			<c:otherwise>
				<c:out value="${acqprofile.title}"/>&nbsp;<c:out value="${acqprofile.lastName}"/>&nbsp;<c:out value="${acqprofile.firstName}"/>
			</c:otherwise>
		</c:choose>
		</span></td>
		<td align="left" width="80%">DN: <span class="bold"><c:out value="${fn:substring(sessionScope.sessionLtsAcqOrderCapture.ltsAcqNumConfirmationForm.confirmedDn, 4, 12)}"/></span></td>	
	</c:if>
</tr>
</table>
</div>