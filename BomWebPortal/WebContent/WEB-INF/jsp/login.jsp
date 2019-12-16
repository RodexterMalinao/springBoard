<%@ include file="/WEB-INF/jsp/header.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	$(".login_button").click(function() {
		$("form[name=loginForm]").submit();
	});
	$("#bomsalesuser input[name=username],#bomsalesuser input[name=password]").keypress(function(e) {
		switch (e.keyCode ? e.keyCode : e.which) {
		case 13:
			$("form[name=loginForm]").submit();
			break;
		}
	});
	$("#username").focusout(function(){

	      $(this).val($(this).val().toUpperCase());

	});
});
</script>

<style style="text/css">
.supportDocForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px }
.supportDocForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.supportDocForm .error { padding-left: 12px; display: inline-block }
.supportDocForm .row { margin: 3px 0; overflow: hidden }
.supportDocForm .label { display: inline-block; width: 25%; margin-right: 5px; text-align: right; vertical-align: middle }
.supportDocForm input { width: 150px; vertical-align: middle }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<div style="height: 41px"></div>

<form:form method="POST" action="login.html" name="loginForm" commandName="bomsalesuser">
<div class="supportDocForm">
	<h3 class="table_title">BOM Login</h3>
	<c:if test="${exception != null}">
	<div class="row error">
		<span>${exception.customMessage}</span>
	</div>
	</c:if>
	<div class="row">
		<span class="label contenttext">User Name<span class="contenttext_red">*</span></span>
		<form:input path="username"/>          
		<form:errors path="username" cssClass="error"/>
	</div>
	<div class="row">
		<span class="label contenttext">Password<span class="contenttext_red">*</span></span>
		<form:password path="password"/>          
		<form:errors path="password" cssClass="error"/>
	</div>
	<div class="buttonmenubox_R" id="buttonArea">
		<input type="hidden" name="appMode" value="shop">
		<input type="hidden" name="currentView" value="login">
		<a href="#" class="nextbutton login_button">Login</a>
	</div>
</div>
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>