<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<script src="js/jquery-ui-1.8.16.custom.min.js"></script>

<br/>
<form:form method="POST" id="ltsApnSerialFileEnquiryForm" name="ltsApnSerialFileEnquiryForm" commandName="ltsApnSerialFileEnquiryCmd" autocomplete="off">
<form:hidden path="formAction" />
<form:hidden path="searchFileDnDtlList" />
<table width="98%" align="center">
	<tr>
		<td>
		<div class="paper_w2 round_10">
		<br/>
			<table width="98%">
				<tr>
					<td><div id="s_line_text">APN Serial File Enquiry</div></td>
				</tr>
			</table>
			<table width="60%" align="center">
				<tr>
					<td>Upload Date: </td> 
					<td>From: <form:input id="searchUploadDateFrom" path="searchUploadDateFrom"/></td>
					<td>To: <form:input id="searchUploadDateTo" path="searchUploadDateTo"/></td>
				</tr>
				<tr><td> </td></tr>
				<tr>
					<td>Select</td>
					<td>
						<form:select id="searchByStatus" path="searchByStatus">
							<form:option value="" >-- TYPE --</form:option>
							<form:option value="P">Upload in Progress</form:option>
							<form:option value="C">Upload Completed</form:option>
							<form:option value="R">Rejected</form:option>
						</form:select>
					</td>
				</tr>
				<tr><td> </td></tr>	
				<tr>
					<td>
						<div class="button" style="float:left;">
							<a href="#" id="returnButton">Return</a>
						</div>	
					</td>
					<td>
						<div class="button" style="float:right;">
							<a href="#" id="clearButton">Clear</a>
						</div>	
					</td>
					<td>
						<div class="button" style="float:left;">
							<a href="#" id="searchButton">Search</a>
						</div>	
					</td>
				</tr>				
			</table>
			<br/>
			
			<c:if test="${ ltsApnSerialFileEnquiryCmd.searchPerformed }">
				<c:choose>
					<c:when test="${ empty ltsApnSerialFileEnquiryCmd.searchResult }}">
						<table width="98%" align="center">
							<tr>
								<td><div id="s_line_text">APN Serial File</div></td>
							</tr>
							
							<tr>
								<td>
									<table>
										<tr><td>No result is being found!</td></tr>	
									</table>
								</td>
							</tr>
						</table>					
					</c:when>
					<c:otherwise>
						<table width="98%" align="center">
							<tr>
								<td><div id="s_line_text">APN Serial File</div></td>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<td style="background-color:#BBBBBB;"><b>File Name</b></td>
											<td style="background-color:#BBBBBB;"><b>Status</b></td>
											<td style="background-color:#BBBBBB;"><b>Upload Date</b></td>
											<td style="background-color:#BBBBBB;"><b>Number of DN Matched (Without problem)</b></td>
											<td style="background-color:#BBBBBB;"><b>Number of DN Matched (With problem)</b></td>
											<td style="background-color:#BBBBBB;"><b>Number of DN Not Matched (but NN matched)</b></td>
											<td style="background-color:#BBBBBB;"><b>Number of ignored</b></td>
											<td style="background-color:#BBBBBB;"><b>Total Number of Records</b></td>
											<td style="background-color:#BBBBBB;"><b>Fail Reason</b></td>											
										</tr>
										
										<c:forEach items="${ltsApnSerialFileEnquiryCmd.searchResult}" var="result" varStatus="rNum">
											<tr>
												<td style="background-color:#FFFFFF;">${ result.fileName }</td>
												<td style="background-color:#DDDDDD;">${ result.status }</td>
												<td style="background-color:#FFFFFF;">${ result.uploadDate }</td>
												<td style="background-color:#DDDDDD;">
													<c:if test="${ not empty result.dnMatch}">
														<a class="dnListDtlMatch" href="${ rNum.index }" id="match">
													</c:if>
													${ fn:length(result.dnMatch) }
													<c:if test="${ not empty result.dnMatch}">
														</a>
													</c:if>													
												</td>
												<td style="background-color:#FFFFFF;">
													<c:if test="${ not empty result.dnMatchWithProblem}">
														<a class="dnListDtlProblem" href="${ rNum.index }" id="matchproblem">
													</c:if>
													${ fn:length(result.dnMatchWithProblem) }
													<c:if test="${ not empty result.dnMatchWithProblem}">
														</a>
													</c:if>
												</td>
												<td style="background-color:#DDDDDD;">
													<c:if test="${ not empty result.dnNotMatchNnMatch}">
														<a class="dnListDtlNotMatch" href="${ rNum.index }" id="notmatch">
													</c:if>
													${ fn:length(result.dnNotMatchNnMatch) }
													<c:if test="${ not empty result.dnNotMatchNnMatch}">
														</a>
													</c:if>
												</td>
												<td style="background-color:#FFFFFF;">
													<c:if test="${ not empty result.dnIgnored}">
														<a class="dnListDtlIgnore" href="${ rNum.index }" id="ignore">
													</c:if>
													${ fn:length(result.dnIgnored) }
													<c:if test="${ not empty result.dnIgnored}">
														</a>
													</c:if>
												</td>
												<td style="background-color:#DDDDDD;">
													<c:if test="${ not empty result.allDnRecord}">
														<a class="dnListDtlAll" href="${ rNum.index }" id="total">
													</c:if>
													${ fn:length(result.allDnRecord) }
													<c:if test="${ not empty result.allDnRecord}">
														</a>
													</c:if>
												</td>
												<td style="background-color:#FFFFFF; min-width: 300px">${ result.failedReason }</td>											
											</tr>
										</c:forEach>
										
										<tr>
											<td style="background-color:#BBBBBB;" colspan="9"><b>Total Record(s):</b> ${ ltsApnSerialFileEnquiryCmd.totalNumOfResults }</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>	
					</c:otherwise>
				</c:choose>
			</c:if>
			</div>
		</td>
	</tr>
</table>
</form:form>

<br/>

<script type="text/javascript">
	setupDatepicker();
	
	$('a#clearButton').click(function(event){
		$('#searchByStatus').val('');
		$('#searchUploadDateFrom').val('');
		$('#searchUploadDateTo').val('');
	});
	
	$('a#searchButton').click(function(event){
		$('input[name="formAction"]').val('SEARCH');
		$('form[name="ltsApnSerialFileEnquiryForm"]').attr('target','_self');
		$('form[name="ltsApnSerialFileEnquiryForm"]').submit();
	});
	
	$('a#returnButton').click(function(event){
		$('input[name="formAction"]').val('RETURN');
		$('form[name="ltsApnSerialFileEnquiryForm"]').attr('target','_self');
		$('form[name="ltsApnSerialFileEnquiryForm"]').submit();
	});
	
	$('a#updateButton').click(function(event){
		$('input[name="formAction"]').val('UPDATE');
		$('form[name="ltsApnSerialFileEnquiryForm"]').submit();
	});
	
	function setupDatepicker(){
		$('#searchUploadDateFrom').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			minDate : "-3Y",
			maxDate : "+0D", //Y is year, M is month, D is day 
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			yearRange: "-3:+0", //the range shown in the year selection box
			onSelect: function(selected) {
		    	$("#searchUploadDateTo").datepicker("option","minDate", selected)
		    }
		});
		
		$('#searchUploadDateTo').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			minDate : "-3Y",
			maxDate : "+0D", //Y is year, M is month, D is day 
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			yearRange: "-3:+0", //the range shown in the year selection box
			onSelect: function(selected) {
		    	$("#searchUploadDateFrom").datepicker("option","maxDate", selected)
		    }
		});		
	}
	
	$('a.dnListDtlMatch').click(function(event){
		event.preventDefault();
		var dnListNum = $(this).attr('href');
		$('input[name="searchFileDnDtlList"]').val(dnListNum);
		$('input[name="formAction"]').val('MATCH');
		$('form[name="ltsApnSerialFileEnquiryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileEnquiryForm"]').submit();
	});
	
	$('a.dnListDtlProblem').click(function(event){
		event.preventDefault();
		var dnListNum = $(this).attr('href');
		$('input[name="searchFileDnDtlList"]').val(dnListNum);
		$('input[name="formAction"]').val('PROBLEM');
		$('form[name="ltsApnSerialFileEnquiryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileEnquiryForm"]').submit();
	});
	
	$('a.dnListDtlNotMatch').click(function(event){
		event.preventDefault();
		var dnListNum = $(this).attr('href');
		$('input[name="searchFileDnDtlList"]').val(dnListNum);
		$('input[name="formAction"]').val('NOTMATCH');
		$('form[name="ltsApnSerialFileEnquiryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileEnquiryForm"]').submit();
	});
	
	$('a.dnListDtlIgnore').click(function(event){
		event.preventDefault();
		var dnListNum = $(this).attr('href');
		$('input[name="searchFileDnDtlList"]').val(dnListNum);
		$('input[name="formAction"]').val('IGNORE');
		$('form[name="ltsApnSerialFileEnquiryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileEnquiryForm"]').submit();
	});
	
	$('a.dnListDtlAll').click(function(event){
		event.preventDefault();
		var dnListNum = $(this).attr('href');
		$('input[name="searchFileDnDtlList"]').val(dnListNum);
		$('input[name="formAction"]').val('ALL');
		$('form[name="ltsApnSerialFileEnquiryForm"]').attr('target','_blank');
		$('form[name="ltsApnSerialFileEnquiryForm"]').submit();
	});
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>