<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page import="com.bomwebportal.service.OrderEsignatureService.EmailReqResult"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="js/jquery.pajinate.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript">

function checkMobile(boo)
{
	$.ajax({
		url : 'checkmobilenum.html?SMS=' + $("#SMSno").val(),
		type : 'POST',
		dataType : 'json',
		timeout : 60000,
		error : function(XMLHttpRequest, textStatus, errorThrown) 
		{
		    if(textStatus=='parsererror')
		    {
		        alert("Your session has been timed out, please re-login."); 
		    }
		    else if(textStatus == 'timeout')
		    {
		    	alert("Server response timeout, please try again.");
		    }
		    $(".error_emailAddr").text("").hide();
		},
		success : function(msg) 
		{
			var count=0;
			$.each(eval(msg), function(i, item) 
			{
				if(item.valid)
				{
					$(".error_emailAddr").text("").hide();
					if (boo)
					{
						if (!confirm("SMS will be sent to " + $("input[name='SMSno']").val() + ". Confirm to continue?"))
						{
							e.preventDefault();
							return false;
						}
					}
				}
				else if (!item.valid )
				{
					$(".error_emailAddr").text("Invalid mobile number").show();
				}
				count++;
			});
			if(count==0)
			{
				$(".error_emailAddr").text("").hide();
			}
		}
	});
}

function changeResendMethod() {
	var resendMethod = $('input[name="resendMethod"]:checked').val();
	
	if (resendMethod == 'E') {
		$('.SMS').hide();
		$('.email').show();
		$('.paper').hide();
		$('#subject').show();
	}
	else if (resendMethod == 'S'){
		$('.SMS').show();
		$('.email').hide();
		$('.paper').hide();
		$('#subject').show();
	}
	else if (resendMethod == 'P'){
		$('.SMS').hide();
		$('.email').hide();
		$('.paper').show();
		$('#subject').hide();
	}
}

function isDisconnectOrder() {
	var isDisconnectOrder = "${isDisconnectOrder}" == "true";	
	if (isDisconnectOrder) {
		$('input[name="resendMethod"][value="P"]').attr("checked", true);
		changeResendMethod();
	}
}

function caps(element){
	element.value = element.value.toUpperCase();
}

$(document).ready(function() {
	
	changeResendMethod();
	
	isDisconnectOrder();
	
	$('input[name="resendMethod"]').change(function(event) {
		changeResendMethod();
		
	});
	
	$('input[name="searchMethod"]').change(function(event) {
		var searchMethod = $('input[name="searchMethod"]:checked').val();
		$('input[name="searchRequestDate"]').attr("disabled", searchMethod == "ORDER_ID");
		$('input[name="searchOrderId"]').attr("disabled", searchMethod == "REQ_DATE");
	});
	
	$(".searchButton").click(function(e) {
		e.preventDefault();
		$('input[name="formAction"]').val('SEARCH');
		$('form').submit();
		return false;
	});

	$(".previewBtn").click(function(e) {
		e.preventDefault();
		if ($("input[name=previewOrderId]:checked").length == 0) {
			alert("Please select one record");
			return false;
		}
		$('input[name="formAction"]').val('PREVIEW');
		if($("#preResendEmail").val()==$("#resendEmail").val())
		{
			$("#resendEmail").val("");
		}
		$('form').submit();
		return false;
	});
	
	$(".closeBtn").click(function(e) {
		window.location = "ltsresendaf.html";
	});
	
	$(".sendBtn").click(function(e) {
		e.preventDefault();
		$('input[name="formAction"]').val('SEND');
		$("form").submit();
		return false;
	});
	
	$(".clearButton").click(function(e) {
		e.preventDefault();
		$('input[name="formAction"]').val('CLEAR');
		$("form").submit();
		return false;
	});
	
	$('#requestDateDatepicker').datepicker({
		changeMonth : true
		, changeYear : true
		, showButtonPanel : true
		, dateFormat : 'dd/mm/yy'
	});
	
    $('form').bind('submit', function() {
    	$.blockUI({ message: null });
    	$(this).find(':input').removeAttr('disabled');
    	
    });

	$(".previewForm").pajinate({ 
		num_page_links_to_display: 0
		, nav_label_first: '|<'
		, nav_label_prev: '<'
		, nav_label_next: '>'
		, nav_label_last: '>|'
		, nav_label_info: '{0} - {1} / {2}'
	});
	$(".previewForm .ellipse").remove();
	$(".previous_link").after($(".info_text").detach());
});
</script>

<style type="text/css">
.label { display: inline-block; font-weight: bold; width: 120px; vertical-align: top; }
.row { height: 24px }
.input { display: inline-block }
.input label { display: inline-block; width: 50px }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.wrapContent { display: inline-block; width: 100%; padding: 10px 0 }
.previewForm .content_detail { margin-left: 2em }
.previewForm .half { width: 40%; float: left }
.header tr { background-color: #E8F2FE }
.even { background-color: #E8FFE8 }
.success { color: blue }
.fail { color: red }
.errors { color: red }
h2 { margin: 0 }
.histList { width: 100%; clear: both; background-color: white }
.histList td { text-align: center }
.mainContent { padding: 1em; border: 1px solid #F2F2F2; margin-top: 1em; margin-right: 2em }
.page_navigation { text-align: center }
.page_navigation .first_link, .page_navigation .previous_link, .page_navigation .next_link, .page_navigation .last_link { color: black; padding: 0 0.5em }
.info_text { font-weight: bold; padding: 0 3em }
</style>


<c:choose>
<c:when test="${not empty result}">
	<c:choose>
	<c:when test="${result == 'SUCCESS'}">
		<h2 class="success">AF sent on <c:out value="${sentDate}"/></h2>
	</c:when>
	<c:otherwise>
		<%
		try {
			pageContext.setAttribute("emailReqResult", EmailReqResult.valueOf(request.getParameter("result")));
		} catch (Exception e) {}
		%>
		<h2 class="fail"><c:out value="${emailReqResult != null ? 'Failure in send AF, ' + emailReqResult.message : result}"/></h2>
	</c:otherwise>
	</c:choose>
</c:when>
</c:choose>


<form:form method="POST" id="ltsResendAfForm" name="ltsResendAfForm" commandName="ltsResendAfCmd" autocomplete="off">
<form:hidden path="formAction"/>

<c:if test="${ltsResendAfCmd.formAction == 'INITIAL' || ltsResendAfCmd.formAction == 'SEARCH'}">
	
	<div id="searchCriteriaDiv" class="previewForm contenttext">
		<h3>Search LTS Order Send AF History</h3>
		<div class="wrapContent">
			<div class="content_detail">
				<c:if test="${not empty teamCdList}">
					<div class="row">
						<span class="label">Team Code:</span>
						<span class="input"> 
							<form:select path="teamCd" disabled="${fn:length(teamCdList) == 1}">
								<c:if test='${fn:length(teamCdList) > 1}'>
									<option value="">ALL</option>
								</c:if>
								<form:options items="${teamCdList}" />
							</form:select>
						</span>
					</div>
				</c:if>
				
				<!-- requestDate -->
				<div class="row">
					<span class="label"><form:radiobutton path="searchMethod" value="REQ_DATE"/> Request Date: </span>
					<span class="input">
						<form:input path="searchRequestDate" readonly="true" id="requestDateDatepicker"/>
						<form:errors path="searchRequestDate" cssClass="errors" />
					</span>
				</div>
				
				<!-- orderId -->
				<div class="row">
					<span class="label"><form:radiobutton path="searchMethod" value="ORDER_ID"/> Order ID: </span>
					<span class="input"> 
						<form:input path="searchOrderId" maxlength="13" onkeyup="caps(this)"/>
						<form:errors path="searchOrderId"  cssClass="errors" />
					</span>
				</div>
			</div>
		</div>
		
		<div class="buttonmenubox_R">
			<a class="nextbutton searchButton" href="#">Search</a>
			<a class="nextbutton clearButton" href="#">Clear</a>
		</div>
	</div>
	
</c:if>

<c:if test="${ltsResendAfCmd.formAction == 'SEARCH'}">

	<div class="paging_table">
		<table class="histList">
			<colgroup style="width:140px"></colgroup>
			<colgroup style="width:45px"></colgroup>
			<colgroup style="width:250px"></colgroup>
			<colgroup style="width:125"></colgroup>
			<colgroup style="width:200px"></colgroup>
			<colgroup style="width:180px"></colgroup>
			<colgroup style="width:100px"></colgroup>
			<colgroup style="width:200px"></colgroup>
			<thead class="header">
				<tr>
					<th>Order ID</th>
					<th>Seq #</th>
					<th>AF Type</th>
					<th>Method</th>
					<th>Destination</th>
					<th>Request Date</th>
					<th>Request By</th>
					<th>Result</th>
				</tr>
			</thead>
			<tbody class="content">
			
				<c:choose>
				<c:when test="${empty orderSendRequestList}">
					<tr>
						<td colspan="6" style="text-align: left">No records</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${orderSendRequestList}" var="record" varStatus="status">
						<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 20}"> style="display:none"</c:if>>
							<td style="text-align:left">
								
								<c:choose>
								<c:when test="${record.disMode == 'E' || record.disMode == 'P'}">
									<form:radiobutton path="previewOrderId" value="${record.orderId}_${record.templateId}_${record.seqNo}" label="${record.orderId}"/>
								</c:when>
								<c:otherwise>
									${record.orderId}
								</c:otherwise>
								</c:choose>
								
							</td>
							<td><c:out value="${record.seqNo}"/></td>
							<td><c:out value="${record.templateDesc}"/></td>
							<td>
								<c:choose>
									<c:when test='${record.method == "S"}'>SMS</c:when>
									<c:when test='${record.method == "I" || record.method == "E"}'>Email</c:when>
									<c:when test='${record.method == "P"}'>Paper</c:when>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test='${record.method == "S"}'><c:out value="${record.smsNo}"/></c:when>
									<c:when test='${record.method == "I" || record.method == "E"}'><c:out value="${record.email}"/></c:when>
									<c:when test='${record.method == "P"}'><c:out value="${record.billingAddress}"/></c:when>
								</c:choose>
							</td>
							<td><c:out value="${record.requestDate}"/></td>
							<td><c:out value="${record.createBy}"/></td>
							<td style="text-align:left">
								<c:if test="${not empty record.printRequestStatus }">
									<c:out value="${record.printRequestStatus}"/>   <c:out value="${record.printDate}"/>  
								</c:if>
							
								<c:choose>
									<c:when test="${record.sentDate == null}">
										<c:out value="${record.errMsg}"/>
									</c:when>
									<c:otherwise>
										Sent on <c:out value="${record.sentDate}"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<form:errors path="previewOrderId"  cssClass="errors" />	
	<c:if test="${not empty orderSendRequestList}">
		<div class="buttonmenubox_R">
			<a href="#" class="nextbutton3 previewBtn">Preview</a>
		</div>
	</c:if>

</c:if>

<c:if test="${ltsResendAfCmd.formAction == 'PREVIEW'}">
	<form:hidden path="searchOrderId"/>
	<form:hidden path="smsTemplateId"/>
	<form:hidden path="emailTemplateId"/>
	<input id="preResendEmail" name="preResendEmail" type="hidden" value="${ltsResendAfCmd.resendEmail}" >
	
	<div class="previewForm sendForm contenttext">
	<h3>LTS Order Send AF History (Order ID: <c:out value="${ltsResendAfCmd.searchOrderId}"/>)</h3>
		<div class="paging_table">
			<table class="histList">
				<colgroup style="width:75px"></colgroup>
				<colgroup style="width:175px"></colgroup>
				<colgroup style="width:45px"></colgroup>
				<colgroup style="width:250px"></colgroup>
				<colgroup style="width:200px"></colgroup>
				<colgroup style="width:125px"></colgroup>
				<colgroup style="width:250px"></colgroup>
				<thead class="header">
					<tr>
						<th>Seq #</th>
						<th>AF Type</th>
						<th>Method</th>
						<th>Destination</th>
						<th>Request Date</th>
						<th>Request By</th>
						<th>Result</th>
					</tr>
				</thead>
				<tbody class="content">
				
					<c:choose>
					<c:when test="${empty orderSendRequestList}">
						<tr>
							<td colspan="7" style="text-align: left">No records</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${orderSendRequestList}" var="record" varStatus="status">
							<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 20}"> style="display:none"</c:if>>
								<td>
									<c:if test="${record.disMode == 'E' || record.disMode == 'P'}">
										<form:radiobutton path="previewOrderId" value="${record.orderId}_${record.templateId}_${record.seqNo}" />
									</c:if> <c:out value="${record.seqNo}"/>
								</td>
								<td><c:out value="${record.templateDesc}"/></td>
								<td>
									<c:choose>
										<c:when test='${record.method == "S"}'>SMS</c:when>
										<c:when test='${record.method == "I" || record.method == "E"}'>Email</c:when>
										<c:when test='${record.method == "P"}'>PAPER</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test='${record.method == "S"}'><c:out value="${record.smsNo}"/></c:when>
										<c:when test='${record.method == "I" || record.method == "E"}'><c:out value="${record.email}"/></c:when>
										<c:when test='${record.method == "P"}'><c:out value="${record.billingAddress}"/></c:when>
									</c:choose>
								</td>
								<td><c:out value="${record.requestDate}"/></td>
								<td><c:out value="${record.createBy}"/></td>
								<td style="text-align:left">
								<c:if test='${record.method != "P"}'>
									<c:choose>
									    <c:when test="${record.sentDate == null}">
									       <c:out value="${record.errMsg}"/>
										</c:when>
										<c:otherwise>
										   Sent on <c:out value="${record.sentDate}"/>
										</c:otherwise>
									</c:choose>
								</c:if>	
									<c:if test='${record.method == "P"}'>
									<c:choose>
									    <c:when test="${record.printRequestStatus == null}">
									       <c:out value="${record.errMsg}"/>
										</c:when>
										<c:otherwise>
										   <c:out value="${record.printRequestStatus}"/>   <c:out value="${record.printDate}"/>
										</c:otherwise>
									</c:choose>
								</c:if>	
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<div class="page_navigation"></div>
			<span class="info_text"></span>			
		</div>
		<c:if test="${not empty orderSendRequestList}">
			<div class="buttonmenubox_R">
				<a href="#" class="nextbutton3 previewBtn">Preview</a>
			</div>
		</c:if>
		<div style="clear:both"></div>
	
		<h3>Preview Resend AF Content (Order ID: <c:out value="${ltsResendAfCmd.searchOrderId}"/>)</h3>
		<c:choose>
		<c:when test="${resendException == null}">
		
			<div class="wrapContent">
				<div class="content_detail">
					<div class="row">
						<span class="label">Method:</span>
						<span class="input">
						    <c:if test='${isDisconnectOrder == false}'>
							    <form:radiobutton path="resendMethod" value="E" /> Email
							</c:if>
							<c:if test='${!isIguard}'>
								<form:radiobutton path="resendMethod" value="S" /> SMS
								
								<c:if test='${isPaperShow}'>
								    <form:radiobutton path="resendMethod" value="P" /> Paper
								</c:if>
							</c:if>
						</span>
					</div>
					<div class="row" id="subject" >
						<span class="label">Subject:</span>
						<span class="input email"><c:out value="${emailSubject}"/></span>
						<span class="input SMS" style="display:none;"><c:out value="${smsSubject}"/></span>
						<span class="input paper" style="display:none;"><c:out value="${paperSubject}"/></span>
					</div>
					<div >
						<span class="label">To:</span>
						<span class="input">
							<form:input path="resendEmail" maxlength="64" cssClass="email" cssStyle="width: 20em"/>
							<form:errors path="resendEmail"  cssClass="errors"/>
							<form:input path="resendSms" maxlength="8"  cssClass="SMS" cssStyle="width:20em; display:none;" onblur="checkMobile()"/>
							<form:errors path="resendSms"  cssClass="errors"/>
							<form:textarea path="billAddress" cssClass="paper" cssStyle="width: 40em; height: 8em;" readonly="true"/>
							<form:errors path="billAddress"  cssClass="errors"/>
						</span>
					</div>
					<div style="clear:both"></div>
					<div class="mainContent email">${emailContent}</div>
					
					<div class="mainContent SMS" style="display:none">${smsContent}</div>
					<pre id="emailContentSrc" style="display:none"></pre>
					<pre id="SMSContentSrc" style="display:none"></pre>
				</div>
			</div>
		
			<div class="buttonmenubox_R">
				
				<!-- hideToolbar -->
				<c:if test="${ltsResendAfCmd.allowResend}">
					<a href="#" class="nextbutton3 sendBtn" >Send AF</a>
				</c:if>
				
				<a href="#" class="nextbutton3 closeBtn">Close</a>
					
			</div>
		</c:when>
		<c:otherwise>
			<!-- in case exception generated from groovy -->
			<div class="wrapContent">
				<div class="content_detail">
					<pre><c:out value="${resendException.message}"/>
					<c:forEach var="s" items="${resendException.stackTrace}">
					<c:out value="${s}"/></c:forEach></pre>
				</div>
			</div>
		</c:otherwise>
		</c:choose>
	</div>
</c:if>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>