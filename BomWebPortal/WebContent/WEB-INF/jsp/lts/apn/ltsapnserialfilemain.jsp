<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<br/>
<form:form method="POST" id="ltsApnSerialFileMainForm" name="ltsApnSerialFileMainForm" commandName="ltsApnSerialFileMainCmd" autocomplete="off">
<form:hidden path="formAction" />
<table width="98%" align="center">
	<tr>
		<td>
		<div class="paper_w2 round_10">
		<br/>
			<table width="98%" align="center">
				<tr>
					<td><div id="s_line_text">APN Serial File</div></td>
				</tr>
				<tr>
					<td><a href="#" id="fileUpload">Upload APN serial batch file</a></td>
				</tr>
				<tr>
					<td><a href="#" id="fileEnquiry">APN serial batch file enquiry</a></td>
				</tr>	
			</table>
			<br/>
			</div>
		</td>
	</tr>
</table>
</form:form>

<br/>
<script type="text/javascript">
	$('a#fileUpload').click(function(event){
		event.preventDefault();
		$('input[name="formAction"]').val('UPLOAD');
		$('form[name="ltsApnSerialFileMainForm"]').submit();
	});
	
	$('a#fileEnquiry').click(function(event){
		$('input[name="formAction"]').val('ENQUIRY');
		$('form[name="ltsApnSerialFileMainForm"]').submit();
	});
</script>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>