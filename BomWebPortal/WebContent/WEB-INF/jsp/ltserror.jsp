<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<table width="100%" border="0" height="480">
	<tr valign="middle" >
		<td align="center">
		<div id="errorDiv" style="width: 50%;">
			<div id="error_profile" class="ui-widget" style="visibility: visible;" >
				<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
					<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					</p>
					<div id="error_profile_msg" class="contenttext">
						Session time out. Please re-issue order again.
					</div>
					<p></p>
				</div>
			</div>
		</div>
		</td>
	</tr>
</table>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>