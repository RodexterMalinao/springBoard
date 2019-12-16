<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />	

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>

<form:form method="POST" id="imsEdfRefForm" name="imsEdfRefForm" commandName="imsEdfRefCmd">
<%-- 	<input type="hidden" name="orderid" value="${sessionScope.sbuid}" /> --%>
	<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
		<tr></tr>
		<tr>
			<td colspan="5" class="table_title">EDF Reference</td>
		</tr>
		<tr>
			<td colspan="5">
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td width="25%" align="right">
							EDF Reference :   
						</td>
						<td>
							<form:input id ='edfRef' path="edfRef" />
						</td>
					</tr>
				</table>
				<spring:hasBindErrors name="imsEdfRefCmd">
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
		<table width="100%" border="0" cellspacing="0" class="contenttext">
			<tr>
				<td align="right" > 
					<br>
					<a href="#" id="submit" class="nextbutton" onClick="formSubmit()">Submit</a> 
				</td>
			</tr>
		</table>
</form:form>

<script type='text/javascript'>
	$(document).ready(function() {
		if(location.search.indexOf('submit=true') !== -1){
			//alert('aa');
			//window.opener.$.blockUI({ message: null });
			//window.opener.location.reload(true);
			window.close();
		}
	});
	
	function formSubmit(){	
		document.imsEdfRefForm.submit();
		//window.opener.location.reload(true);
		
		window.opener.refreshEDF(document.getElementById('edfRef').value);
		//window.close();					
	}
// 	$(ltscollectdoc.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>