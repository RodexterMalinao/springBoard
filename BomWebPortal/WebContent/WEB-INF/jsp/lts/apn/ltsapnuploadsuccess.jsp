<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>

<script type="text/JavaScript">
redirectTime = "5000";
redirectURL = "ltsapnserialfilemain.html";
setTimeout("location.href = redirectURL;",redirectTime);
</script>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<table width="100%" border="0" height="480">
	<tr valign="middle" >
		<td align="center">
		<div id="errorDiv" style="width: 50%;">
			<div id="error_profile" class="ui-widget" style="visibility: visible;" >
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
					<p>
						<span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
					</p>
					<div id="msg" class="contenttext">
						<b>APN file has been uploaded. The file will be processed later on...</b>
					</div>
					<p></p>
				</div>
			</div>
		</div>
		</td>
	</tr>
</table>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>