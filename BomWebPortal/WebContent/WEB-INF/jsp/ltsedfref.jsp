<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<form:form method="POST" id="ltsEdfRefForm" name="ltsEdfRefForm" commandName="ltsEdfRefCmd" autocomplete="off">
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<table width="100%" border="0" cellspacing="1" cellpadding="3" class="paper_w2">
		<tr></tr>
		<tr>
			<td colspan="5" class="table_title" id="s_line_text">EDF Reference</td>
		</tr>
		<tr>
			<td colspan="5">
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td width="30%" align="right">
							EDF Reference :   
						</td>
						<td width="10%">
							<form:input path="edfRef" />
						</td>
						<td colspan="2">
							<a href="#" id="submit" class="nextbutton"><div class="button">Submit</div></a> 
						</td>
					</tr>
				</table>
				<br>
				<spring:hasBindErrors name="ltsEdfRefCmd">
						<table width="100%">
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="70%">
									<div id="error_div" class="ui-widget" style="visibility: visible;">
										<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
											<p>
												<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
											</p>
											<div id="error_msg" class="contenttext">
												<form:errors path="*"  />				
											</div>
											<p></p>
										</div>
									</div>
								</td>
								<td>&nbsp;</td>
							</tr>
						</table>
						<br>
					</spring:hasBindErrors>
			</td>
		</tr>
	</table>
</form:form>

<script type='text/javascript'>
	$(document).ready(function() {
		if(location.search.indexOf('submit=true') !== -1){
			window.opener.$.blockUI({ message: null });
			var form = window.opener.document.getElementById("summary");
			var buttonPressed = window.opener.document.getElementById("buttonPressed");
			buttonPressed.value = '6';
			form.submit();
			window.close();
		}
	});
	
	$('a#submit').click(function(event){
		$('#ltsEdfRefForm').submit();
	});
	
	$('form').bind('submit', function() {
    	$.blockUI({ message: null });
    });
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>