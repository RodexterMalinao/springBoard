<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<form:form method="POST" id="ltsWqRemarkForm" name="ltsWqRemarkForm" commandName="ltsWqRemarkCmd" autocomplete="off">
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<table width="98%" border="0" cellspacing="1" cellpadding="3" class="contenttext" align="center">
		<tr></tr>
		<tr>
			<td colspan="5"><div id="s_line_text">Approval Remark</div></td>
		</tr>
		<tr>
			<td colspan="5">
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4" width="100%">
							<form:textarea path="wqRemark" cols="115" rows="20" cssStyle="resize: none;"/>
						</td>
					</tr>
				</table>
				<spring:hasBindErrors name="ltsWqRemarkCmd">
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
					<div class="button">
						<a href="#" id="submit" class="nextbutton">Submit</a>
					</div>
					<div class="button">
						<a href="#" id="cancel" class="nextbutton">Cancel</a>
					</div> 
				</td>
			</tr>
		</table>
</form:form>

<script type='text/javascript'>
	$(document).ready(function() {
		if(location.search.indexOf('submit=true') !== -1){
			window.close();
		}
	});
	
	$('a#submit').click(function(event){
		$('#ltsWqRemarkForm').submit();
	});
	
	$('a#cancel').click(function(event){
		window.close();
	});
	
	$('form').bind('submit', function() {
    	$.blockUI({ message: null });
    });
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>