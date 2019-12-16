<%@ include file="/WEB-INF/jsp/header.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	$(".login_button").click(function() {
		$("input[name=confirm]").val("true");
		$("form[name=loginForm]").submit();
	});
	$(".reject_button").click(function() {
		$("input[name=confirm]").val("false");
		$("form[name=loginForm]").submit();
	});
});
</script>

<style style="text/css">
.supportDocForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px }
.supportDocForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.supportDocForm .error { padding-left: 12px }
.supportDocForm .row { margin: 3px 0; overflow: hidden }
.supportDocForm .info { padding-left: 12px; font-size: 12px; color: #7F7F7F; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.supportDocForm .label { display: inline-block; width: 15em }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<div style="height: 41px"></div>

<form:form method="POST" action="login.html" name="loginForm" commandName="bomsalesuser">
<div class="supportDocForm">
	<h3>BOM Login</h3>
	<div class="row error">
		<c:out value="${bomsalesuser.username}"/> already login, do you want to replace current login?
	</div>
	<div class="row info">
		<span class="label">Current login's IP address:</span> <c:out value="${loginLogDto.ipAddress}"/> (last update date: <c:out value="${loginLogDto.lastUpdDate}"/>)
		<div style="clear: both"></div>
		<span class="label">This computer's IP address:</span> <c:out value="${pageContext.request.remoteAddr}"/>
	</div>
	<div class="buttonmenubox_R" id="buttonArea">
		<form:hidden path="username"/>
		<form:hidden path="password"/>
		<input type="hidden" name="appMode" value="shop">
		<input type="hidden" name="currentView" value="login">
		<input type="hidden" name="confirm" value="false">
		<input type="hidden" name="dbRecordSessionId" value="<c:out value="${dbRecordSessionId}"/>">
		<a href="#" class="nextbutton login_button">Yes</a>
		<a href="#" class="nextbutton reject_button">No</a>
	</div>
</div>
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>