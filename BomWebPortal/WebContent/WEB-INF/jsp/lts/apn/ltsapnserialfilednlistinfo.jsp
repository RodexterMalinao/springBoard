<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<br/>
<form:form method="POST" id="ltsApnSerialFileDnListInfoForm" name="ltsApnSerialFileDnListInfoForm" commandName="ltsApnSerialFileDnListInfoCmd" autocomplete="off">
<table width="98%" align="center">
	<tr>
		<td>
		<div class="paper_w2 round_10">
			<br/>
			<table width="98%" align="center">
				<tr>
					<td><div id="s_line_text">APN Serial File DN Records:</div></td>
				</tr>
				<tr>
					<td>
						<c:choose>
							<c:when test="${not empty ltsApnSerialFileDnListInfoCmd.dnList}"> 
								<table>
									<tr>
										<td style="background-color:#BBBBBB"><b>Batch Sequence</b></td>
										<td style="background-color:#BBBBBB;"><b>Detail Sequence</b></td>
										<td style="background-color:#BBBBBB;"><b>Type of Document</b></td>
										<td style="background-color:#BBBBBB;"><b>Application Date</b></td>
										<td style="background-color:#BBBBBB;"><b>Batch Date</b></td>
										<td style="background-color:#BBBBBB;"><b>APN Serial Number</b></td>
										<td style="background-color:#BBBBBB;"><b>Service Number</b></td>
										<td style="background-color:#BBBBBB;"><b>Service NN</b></td>
										<td style="background-color:#BBBBBB;"><b>Charge Over Start Date</b></td>
										<td style="background-color:#BBBBBB;"><b>Charge Over End Date</b></td>
										<td style="background-color:#BBBBBB;"><b>Order ID</b></td>
										<td style="background-color:#BBBBBB;"><b>Detail ID</b></td>
										<td style="background-color:#BBBBBB;"><b><i>Is DN Match</i></b></td>
										<td style="background-color:#BBBBBB;"><b><i>Is NN Match</i></b></td>
										<td style="background-color:#BBBBBB;"><b><i>Is Date-time Match</i></b></td>
										<td style="background-color:#BBBBBB;"><b>Status</b></td>
										<td style="background-color:#BBBBBB;"><b>Message</b></td>
									</tr>
									<c:forEach items="${ltsApnSerialFileDnListInfoCmd.dnList}" var="result">
										<tr>
											<td style="background-color:#DDDDDD;"> ${ result.batchSeq } </td>
											<td style="background-color:#FFFFFF;"> ${ result.dtlSeq } </td>	
											<td style="background-color:#DDDDDD;"> ${ result.typeOfDoc } </td>
											<td style="background-color:#FFFFFF;"> ${ result.appDate } </td>
											<td style="background-color:#DDDDDD;"> ${ result.batchDate } </td>
											<td style="background-color:#FFFFFF;"> ${ result.apnSerial } </td>
											<td style="background-color:#DDDDDD;"> ${ result.srvNum } </td>
											<td style="background-color:#FFFFFF;"> ${ result.srvNn } </td>
											<td style="background-color:#DDDDDD;"> ${ result.chgoverStartTime } </td>
											<td style="background-color:#FFFFFF;"> ${ result.chgoverEndTime } </td>
											<td style="background-color:#DDDDDD;"> ${ result.orderId } </td>
											<td style="background-color:#FFFFFF;"> ${ result.dtlId } </td>
											<td style="background-color:#DDDDDD;"> ${ result.isDnMatch } </td>
											<td style="background-color:#FFFFFF;"> ${ result.isNnMatch } </td>
											<td style="background-color:#DDDDDD;"> ${ result.isDateTimeMatch } </td>
											<td style="background-color:#FFFFFF;"> ${ result.status } </td>
											<td style="background-color:#DDDDDD;"> ${ result.statusMessage } </td>
										</tr>							
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
								No DN record is found!
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>	
		</div>
		</td>
	</tr>
</table>
</form:form>

<br/>

<script type="text/javascript">
	$('a#match').click(function(event){
		$('input[name="formAction"]').val('MATCH');
		$('form[name="ltsApnSerialFileSummaryForm"]').target='_blank';
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
	
	$('a#matchproblem').click(function(event){
		$('input[name="formAction"]').val('PROBLEM');
		$('form[name="ltsApnSerialFileSummaryForm"]').target='_blank';
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
	
	$('a#notmatch').click(function(event){
		$('input[name="formAction"]').val('NOTMATCH');
		$('form[name="ltsApnSerialFileSummaryForm"]').target='_blank';
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
	
	$('a#ignore').click(function(event){
		$('input[name="formAction"]').val('IGNORE');
		$('form[name="ltsApnSerialFileSummaryForm"]').target='_blank';
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
	
	$('a#total').click(function(event){
		$('input[name="formAction"]').val('ALL');
		$('form[name="ltsApnSerialFileSummaryForm"]').target='_blank';
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>