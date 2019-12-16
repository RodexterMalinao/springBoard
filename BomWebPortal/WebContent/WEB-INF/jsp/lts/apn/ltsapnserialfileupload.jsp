<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<br/>
<form:form method="POST" id="ltsApnSerialFileUploadForm" name="ltsApnSerialFileUploadForm" commandName="ltsApnSerialFileUploadCmd" autocomplete="off" enctype="multipart/form-data">
<form:hidden path="formAction" />
<table width="98%" align="center">
	<tr>
		<td>
		<div class="paper_w2 round_10">
		<br/>
			<table width="98%" align="center">
				<tr>
					<td><div id="s_line_text">APN Serial File Upload</div></td>
				</tr>
				<tr>
					<td><input type="file" name="batchFile" id="batchFile" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<div class="button" style="float:left;">
							<a href="#" id="returnButton">Return</a>
						</div>	
					</td>
					<td>
						<div class="button" style="float:left;">
							<a href="#" id="uploadButton">Upload</a>
						</div>	
					</td>
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
	$('a#returnButton').click(function(event){
		event.preventDefault();
		$('input[name="formAction"]').val('RETURN');
		$('form[name="ltsApnSerialFileUploadForm"]').submit();
	});
	
	$('a#uploadButton').click(function(event){
		$('input[name="formAction"]').val('UPLOAD');
		$('form[name="ltsApnSerialFileUploadForm"]').submit();
	});
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>