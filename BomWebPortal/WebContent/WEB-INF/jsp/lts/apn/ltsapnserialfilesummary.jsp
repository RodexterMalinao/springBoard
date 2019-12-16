<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<br/>
<form:form method="POST" id="ltsApnSerialFileSummaryForm" name="ltsApnSerialFileSummaryForm" commandName="ltsApnSerialFileSummaryCmd" autocomplete="off">
<form:hidden id="formAction" path="formAction" />
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
					<td>
						<table>
							<tr>
								<td> File Name: </td>
								<td> ${ ltsApnSerialFileSummaryCmd.apnFileSummary.fileName } </td>
							</tr>
							<tr>
								<td>Status</td>
								<td> ${ ltsApnSerialFileSummaryCmd.apnFileSummary.status } </td>
							</tr>
							<tr>
								<td>Upload Date</td>
								<td> ${ ltsApnSerialFileSummaryCmd.apnFileSummary.uploadDate } </td>
							</tr>
							<tr>
								<td>Number of DN Matched (Without problem)</td>
								<td>
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.dnMatch }">
										<a href="#" id="match">
									</c:if>	
										
									${ ltsApnSerialFileSummaryCmd.numDnMatchedWithoutProblem }
									
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.dnMatch }">
										</a>
									</c:if>	
								</td>
							</tr>
							<tr>
								<td>Number of DN Matched (With problem)</td>
								<td>
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.dnMatchWithProblem }">
										<a href="#" id="matchproblem">
									</c:if>
									
									${ ltsApnSerialFileSummaryCmd.numDnMatchedWithProblem }
									
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.dnMatchWithProblem }">
										</a>
									</c:if>
								</td>
							</tr>
							<tr>
								<td>Number of DN Not Matched (but NN matched)</td>
								<td>
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.dnNotMatchNnMatch }">
										<a href="#" id="notmatch">
									</c:if>
									
									${ ltsApnSerialFileSummaryCmd.numDnNotMatchedButNN }
									
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.dnNotMatchNnMatch }">
										</a>
									</c:if>
								</td>
							</tr>
							<tr>
								<td>Number of ignored</td>
								<td>
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.dnIgnored }">
										<a href="#" id="ignore">
									</c:if>
									
									${ ltsApnSerialFileSummaryCmd.numIgnored }
									
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.dnIgnored }">
										</a>
									</c:if>
								</td>
							</tr>
							<tr>
								<td>Total Number of Records</td>
								<td>
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.allDnRecord }">
										<a href="#" id="total">
									</c:if>
									
									${ ltsApnSerialFileSummaryCmd.totalNumOfRecords }
									
									<c:if test="${ not empty ltsApnSerialFileSummaryCmd.apnFileSummary.allDnRecord }">
										</a>
									</c:if>
								</td>
							</tr>
							<tr>		
								<td>Fail Reason</td>						
								<td> ${ ltsApnSerialFileSummaryCmd.apnFileSummary.failedReason } </td>			
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><a href="#" id="return">Return to main menu</a></td>
				</tr>	
			</table>	
		</div>
		</td>
	</tr>
</table>
</form:form>

<br/>

<script type="text/javascript">
	var newpage;

	$('a#return').click(function(event){
		$('input[name="formAction"]').val('RETURN');
		$('form[name="ltsApnSerialFileSummaryForm"]').attr('target','_self');
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
	
	$('a#match').click(function(event){
		$('input[name="formAction"]').val('MATCH');
		$('form[name="ltsApnSerialFileSummaryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
	
	$('a#matchproblem').click(function(event){
		$('input[name="formAction"]').val('PROBLEM');
		$('form[name="ltsApnSerialFileSummaryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
	
	$('a#notmatch').click(function(event){
		$('input[name="formAction"]').val('NOTMATCH');
		$('form[name="ltsApnSerialFileSummaryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
	
	$('a#ignore').click(function(event){
		$('input[name="formAction"]').val('IGNORE');
		$('form[name="ltsApnSerialFileSummaryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
	
	$('a#total').click(function(event){
		$('input[name="formAction"]').val('ALL');
		$('form[name="ltsApnSerialFileSummaryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileSummaryForm"]').submit();
	});
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>