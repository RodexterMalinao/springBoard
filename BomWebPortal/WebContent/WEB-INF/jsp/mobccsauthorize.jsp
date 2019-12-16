<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%><%@ page contentType="text/html;charset=UTF-8" 
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"
%><%@taglib prefix="form" uri="http://www.springframework.org/tags/form" 
%><%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%><%@ page import="			
					org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.dto.BomSalesUserDTO,
					java.util.Locale
					"
%><!doctype html>
<html>
<head>
<base target="_self" >
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<pccw-util:codelookup var="reasonRemarkInputInd" codeType="AUTH_ITEM_ENABLE_REASON_REAMRK_INPUT" codeId="${authorizeModel.action}"/>
<pccw-util:codelookup var="byPassAuthInd" codeType="BY_PASS_AUTH_ITEM" codeId="${authorizeModel.action}"/>
<pccw-util:codelookup var="authReasonCodeType" codeType="AUTH_ITEM_REASON_TYPE_MAP" codeId="${authorizeModel.action}"/>
<title>Authorize</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
function checkingAuth(){
	
	var reasonRemarkInputInd = "<c:out value="${reasonRemarkInputInd}" />";
	var byPassAuthInd = "<c:out value="${byPassAuthInd}" />";
	var authReasonCodeType = "<c:out value="${authReasonCodeType}" />";
	
	if (byPassAuthInd == "Y"){
		$("#byPassAuthInd").val("Y");
	}else{
		$("#byPassAuthInd").val("");
	}
	
	if (reasonRemarkInputInd == "Y"){
		$("#reasonRemarkInputInd").val("Y");
		$(".error_msg").hide();
		var hasError = false;
		if ($("select[name=reasonCd]")[0].selectedIndex == 0) {
			$(".error_codeId").show();
			hasError = true;
		}
		if ($("textarea[name=remark]").val().length > 200) {
			$(".error_remark").show();
			hasError = true;
		}
		if (hasError) {
			e.preventDefault();
			return false;
		}else{
			$("#reasonType").val(authReasonCodeType);		
			$("#authForm").submit();
		}
	}else{
		$("#reasonRemarkInputInd").val("");
		$("#authForm").submit();
	}
	
	
}
</script>
<style type="text/css">
body { margin: 0; }
.loginArea { background-color: white; font-family: sans-serif; overflow: hidden; }
.loginArea .title { background-color: #6CA9F5; color: white; padding: 5px 10px; overflow: hidden; font-weight: bold }
.loginArea .title2 { color: #6CA9F5 !important; overflow: hidden; font-weight: bold }
.loginArea .clear2 { clear: both; }
.loginArea .row { padding: 3px 10px; font-size: small; margin: auto; width: 600px; }
.loginArea .col { float: left; padding: 0 5px; }
.loginArea .label { width: 140px; text-align: right; }
.loginArea input { font-family: sans-serif; }
.loginArea .error { color: red; }
</style>
</head>
<body>
<form:form method="POST" id="authForm" action="mobccsauthorize.html" commandName="authorizeModel">
<form:hidden path="reasonType"/>
<form:hidden path="reasonRemarkInputInd"/>
<form:hidden path="byPassAuthInd"/>
<div class="loginArea">
<div class="title2"><pccw-util:codelookup codeType="AUTH_ITEM" codeId="${authorizeModel.action}"/></div>

<c:if test="${byPassAuthInd ne 'Y'}">
<div class="title">${authorizeModel.categoryLogin} AUTHORIZE</div>
<div class="clear2"></div>
<div class="row">
<div class="col label">User Name:</div>
<div class="col input">
<form:input path="username" autocomplete="false" />
<div class="clear2"></div>
<form:errors path="username" cssClass="error"/>
</div>
</div>
<div class="clear2"></div>
<div class="row">
<div class="col label">Password:</div>
<div class="col input">
<form:password path="password" autocomplete="false" />
<div class="clear2"></div>
<form:errors path="password" cssClass="error"/>
</div>
</div>
</c:if>
<c:if test="${reasonRemarkInputInd eq 'Y'}">
<div class="clear2"></div>
<div class="row">
<div class="col label">Authorize / By pass Reason:</div>
<div class="col input">

<sb-util:codelookup var="authReasons" codeType="${authReasonCodeType}"  asEntryList="true" />
			<form:select path="reasonCd">
								<form:option value="" label="Select..."/>
								<c:forEach var="authReason" items="${authReasons}">
					
									<form:option value="${authReason.key}" label="${authReason.value}" />
	
								</c:forEach>
			</form:select> 
			
<div style="clear:both"></div>
		<span class="error error_msg error_codeId" style="display:none">Authorize / By pass Reason is required</span>
<div class="clear2"></div>
<form:errors path="reasonCd" cssClass="error"/>
</div>
</div>
<div class="clear2"></div>
<div class="row">
<div class="col label">Remark:</div>
<div class="col input">
<form:textarea path="remark" rows="3" cols="35"/>
<div style="clear:both"></div>
<span class="error error_msg error_remark" style="display:none">Remark over 200 characters</span>
<div class="clear2"></div>
<form:errors path="remark" cssClass="error"/>
</div>
</div>
</c:if>

<div class="clear2"></div>
<div class="row">
<div class="col label">&nbsp;</div>
<div class="col input">
<form:hidden path="action"/>
<form:hidden path="orderId"/>
<form:hidden path="basketId"/>
<input type="button" value="Confirm" onclick="return checkingAuth();" >
</div>
</div>
<div class="clear2"></div>
<c:choose>
<c:when test="${param.userIsManager == false}">
<div class="row">
<div class="col label">&nbsp;</div>
<div class="col error">
This account doesn’t authorize for approval rights
<%-- <c:if test="${bomsalesuser.channelId == '1' ||bomsalesuser.channelId == '2'}">
	<c:out value="Not ${bomsalesuser.username}'s manager"/>
</c:if>
<c:if test="${bomsalesuser.channelId == '10'}">
	<c:out value="Not ${bomsalesuser.username}'s supervisor"/>
</c:if> --%>
</div>
</div>
<div class="clear2"></div>
</c:when>
<c:when test="${param.systemError == 'true'}">
<div class="row">
<div class="col label">&nbsp;</div>
<div class="col error">System error, please retry</div>
</div>
<div class="clear2"></div>
</c:when>
<c:when test="${not empty param.errorMsg}">
<div class="row">
<div class="col label">&nbsp;</div>
<div class="col error">	${param.errorMsg}</div>
</div>
<div class="clear2"></div>

</c:when>
</c:choose>
</div>
</form:form>
</body>
</html>