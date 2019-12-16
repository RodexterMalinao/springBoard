<%@ include file="/WEB-INF/jsp/header.jsp"
%><%@ page import="com.bomwebportal.service.OrderEsignatureService.EmailReqResult"
%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="css/imsds.css" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="js/jquery.pajinate.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script type="text/javascript">
function searchByOther() {
	$(".searchByOther input").attr("disabled", false);
	$(".searchByOrderId input").attr("disabled", true);
}
function searchByOrderId() {
	$(".searchByOther input").attr("disabled", true);
	$(".searchByOrderId input").attr("disabled", false);
}
function initActionSearch() {
	// init form
	if ($("#searchForm input[name=orderId]").val().length == 0) {
		$("input.searchByOther").attr("checked", true);
		searchByOther();
	} else {
		$("input.searchByOrderId").attr("checked", true);
		searchByOrderId();
	}
	$("input[name=searchBy]").click(function() {
		switch ($("input[name=searchBy]:checked").val()) {
		case "other":
			searchByOther();
			break;
		case "orderId":
			searchByOrderId();
			break;
		}
	});
	
	$("#searchForm").submit(function(e) {
		$("#searchForm .error").hide();
		switch ($("input[name=searchBy]:checked").val()) {
		case "other":
			{
				var requestDate = $.trim($("#searchForm input[name=requestDateStr]").val());
				if (requestDate == null || requestDate.length == 0) {
					$(".error_requestDateStr").text("Requires Request Date").show();
				}
			}
			break;
		case "orderId":
			{
				var orderId = $.trim($("#searchForm input[name=orderId]").val());
				
				if (orderId == null || orderId.length == 0) {
					$(".error_orderId").text("Requires Order ID").show();
				} else if (orderId.match(/[A-Z0-9]{4}[MEDPV][0-9]{6}/) == null
						&& orderId.match(/[A-Z0-9]{4}[MEDPV][RUCSP][0-9]{5}/) == null
						&& orderId.match(/[A-Z0-9]{4}[MEDPV][RUCSP][0-9]{6}/) == null
						&& orderId.match(/[A-Z0-9]{4}[V][RUA][0-9]{6}/) == null) {
					$(".error_orderId").text("Invalid Order ID pattern").show();
				} else if (orderId.substring(0, 1) != 'R' && orderId.substring(0, 1) != 'P' && orderId.substring(0, 1) != 'O') {
					$(".error_orderId").text("Require Order ID starts with R, P or O").show();
				} else {
					var selShopCd;
					if ($("#searchForm input[name=shopCd]").filter("[type=hidden]").length > 0) {
						selShopCd = $("#searchForm input[name=shopCd]").val(); 
					} else {
						selShopCd = $("#searchForm input[name=shopCd]:checked").val();
					} 
					if (selShopCd != "" && orderId.substring(0, 1) == 'R' && orderId.substring(1, 1 + selShopCd.length) != selShopCd) {
						$(".error_orderId").text("Require Order ID starts with R" + selShopCd).show();
					} else {
						var selLob = $("#searchForm input[name=lob]:checked").val();
						var lobAbbr = null;
						switch (selLob) {
						case "MOB":
							lobAbbr = "M";
							break;
						case "LTS":
							lobAbbr = "ED";
							break;
						case "IMS":
							lobAbbr = "P";
							break;
						}
						if (lobAbbr != null && lobAbbr.indexOf(orderId.substring(4, 5)) == -1 && orderId.substring(4, 5) != "V") { 
							$(".error_orderId").text("Require Order ID 5th char = " + lobAbbr).show();
						}
					}
				}
			}
			break;
		}
		if ($("#searchForm .error:visible").length > 0) {
			e.preventDefault();
			return false;
		}
	});
	$(".clearButton").click(function(e) {
		$("input.searchByOther").attr("checked", true);
		$("#searchForm input[name=shopCd]:first").filter("[type!=hidden]").attr("checked", true);
		$("#searchForm input[name=lob]:first").attr("checked", true);
		$("#searchForm #requestDateDatepicker").val($.datepicker.formatDate("dd/mm/yy", new Date()));
		$("#searchForm input[name=orderId]").val("");
		$("#searchForm input[name=orderType]").val("");

		$("#reset").val("Y");
		$("#searchForm").submit();
	});
	$(".searchButton").click(function(e) {
		e.preventDefault();
		$("#searchForm").submit();
		return false;
	});
	$("#previewForm").submit(function(e) {
		if ($("input[name=orderId]:checked").length == 0) {
			alert("Please select one record");
			e.preventDefault();
			return false;
		}
		var $td = $("input[name=orderId]:checked").parent();
		$("input[name=templateId]").attr("disabled", true);
		$td.find("input[name=templateId]}").attr("disabled", false);
	});
	$(".previewBtn").click(function(e) {
		e.preventDefault();
		$("#previewForm").submit();
	});
	$('#requestDateDatepicker').datepicker({
		changeMonth : true
		, changeYear : true
		, showButtonPanel : true
		, dateFormat : 'dd/mm/yy'
	});
	if ($('#requestDateDatepicker').val().length == 0) {
		$('#requestDateDatepicker').val($.datepicker.formatDate("dd/mm/yy", new Date()));
	}
}
function initActionPreview() {
	$("input[name=emailSendConfirm]").val("false");
	$("#emailContent").html($("#emailContentSrc").text());
	$("#SMSContent").html($("#SMSContentSrc").text());
	$("#sendForm").submit(function(e) {
		$(".error").hide();
		$("input[name=emailAddr]").val($.trim($("input[name=emailAddr]").val()));
		if ($("input[name=emailAddr]").val().length == 0) {
			$(".error_emailAddr").text("Require Email").show();
		} else if (!(/^\S+@\S+$/).test($("input[name=emailAddr]").val())) {
			$(".error_emailAddr").text("Invalid Email format").show();
		}
		if ($(".error:visible").length > 0) {
			e.preventDefault();
			return false;
		}
		if (!confirm("Email will be sent to " + $("input[name=emailAddr]").val() + ". Confirm to continue?")) {
			e.preventDefault();
			return false;
		}
		$("#updateBOMInd").val("N");
		if ("${SMSInd}"=="Y"  && "${LOB}" == "IMS"){
			var hktMobileNum = $("input[id='SMSno']").val();
			var hktMobilePattern=/[456789][0-9][0-9][0-9][0-9][0-9][0-9][0-9]/;
			if (hktMobileNum.match(hktMobilePattern)){
				
				if (!confirm("SMS will be sent to " + $("input[id='SMSno']").val() + ". Confirm to continue?")) {
					return false;
				}
				alert("Email / mobile number will overwrite existing customer's contact info for future communication.");
				$("#updateBOMInd").val("Y");
				
			}else{
				$(".error_SMSno").text("Invalid mobile number").show();
				return false;
			}
		}
		$("input[name=emailSendConfirm]").val("true");
		refreshStates();
	});
	$(".sendBtn").click(function(e) {
		e.preventDefault();
		$("#sendForm").submit();
		return false;
	});
	<c:choose>
	<c:when test="${param.hideToolbar == 'true'}">
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	$(".closeBtn").click(function(e) {
		window.close();
	});
	</c:when>
	<c:otherwise>
	$(".closeBtn").click(function(e) {
		window.location = "ordersendemailhist.html";
	});
	</c:otherwise>
	</c:choose>
}
var action = "<c:out value="${command.action}"/>";
var shopCd = "<c:out value="${bomsalesuser.shopCd}"/>";
$(document).ready(function() {
	$("#reset").val("");	
	$.datepicker._gotoToday = function (id) { $(id).datepicker('setDate', new Date()).datepicker('hide').blur(); };
	 
	switch (action) {
	case "PREVIEW":
		initActionPreview();
		break;
	case "SEARCH_SUBMIT":
		hiddenSearchMenu(); 
		initActionSearch();
		break;
	case "SEARCH":
	default:
		initActionSearch();
	}

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
$(function() {
	$('input:radio[name=lob]').click(function(e) {
		refreshStates();
	});
	refreshStates();
});
function refreshStates() {
	var selLob = $("#searchForm input[name=lob]:checked").val();
	if (selLob == 'IMS') {
		$("#orderType").removeAttr('disabled');
		$("#orderType").removeAttr('disabled');
	} else {
		$('#orderType').attr('disabled', 'disabled');
		$('#orderType').attr('disabled', 'disabled');
		$('#orderType').val('');
	}
}
function hiddenSearchMenu(){
	var lob = '${param.lob}';
	var orderId = '${param.orderId}';
	var channelId = orderId.substring(0,1);
	if (lob=='mob' && channelId == 'C'){
		 $('.search_div').hide();
	}
}

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
		    $(".error_SMSno").text("").hide();
		},
		success : function(msg) 
		{
			var count=0;
			$.each(eval(msg), function(i, item) 
			{
				if(item.valid)
				{
					$(".error_SMSno").text("").hide();
					if ( boo )
					{
						mobileChecked = true;
						$("#sendForm").submit();
					}
				}
				else if (!item.valid )
				{
					$(".error_SMSno").text("Invalid mobile number").show();
				}
				count++;
			});
			if(count==0)
			{
				$(".error_SMSno").text("").hide();
			}
		}
	});
}

</script>

<style type="text/css">
.label { display: inline-block; font-weight: bold; width: 120px }
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
h2 { margin: 0 }
.histList { width: 100%; clear: both; background-color: white }
.histList td { text-align: center }
#emailContent { padding: 1em; border: 1px solid #F2F2F2; margin-top: 1em; margin-right: 2em }
#SMSContent { padding: 1em; border: 1px solid #F2F2F2; margin-top: 1em; margin-right: 2em }
.page_navigation { text-align: center }
.page_navigation .first_link, .page_navigation .previous_link, .page_navigation .next_link, .page_navigation .last_link { color: black; padding: 0 0.5em }
.info_text { font-weight: bold; padding: 0 3em }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:choose>
<c:when test="${not empty param.result}">
	<c:choose>
	<c:when test="${param.result == 'SUCCESS'}">
		<h2 class="success">Email sent on <c:out value="${param.sentDate}"/></h2>
	</c:when>
	<c:otherwise>
		<%
		try {
			pageContext.setAttribute("emailReqResult", EmailReqResult.valueOf(request.getParameter("result")));
		} catch (Exception e) {}
		%>
		<h2 class="fail">Failure in send email, <c:out value="${emailReqResult.message}"/></h2>
	</c:otherwise>
	</c:choose>
</c:when>
<c:when test="${param.emailSendConfirm == 'false'}"><h2 class="fail">Please confirm to send email</h2></c:when>
</c:choose>

<c:choose>
<c:when test="${command.action == 'PREVIEW'}">

<div class="previewForm sendForm contenttext">
<h3>Order Send Email History (Order ID: <c:out value="${param.orderId}"/>)</h3>

<div class="paging_table">
<table class="histList">
<colgroup style="width:45px"></colgroup>
<colgroup style="width:250px"></colgroup>
<colgroup style="width:200px"></colgroup>
<colgroup style="width:125px"></colgroup>
<thead class="header">
<tr>
	<th>Seq #</th>
	<th>Email Address</th>
	<th>Request Date</th>
	<th>Request By</th>
	<th>Result</th>
</tr>
</thead>
<tbody class="content">
<c:choose>
<c:when test="${list == null}"></c:when>
<c:when test="${empty list}">
<tr>
	<td colspan="5" style="text-align: left">No records</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${list}" var="record" varStatus="status">
<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 20}"> style="display:none"</c:if>>
	<td><c:out value="${record.seqNo}"/></td>
	<td><c:out value="${record.emailAddr}"/><c:if test="${record.lob == 'IMS' and record.SMSno != null}">/<br><c:out value="${record.SMSno}"/></c:if></td>
	<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.requestDate}"/></td>
	<td><c:out value="${record.createBy}"/></td>
	<td style="text-align:left">
		<c:choose>
			<c:when test="${record.sentDate == null}">
				<c:out value="${record.errMsg}"/>
			</c:when>
			<c:otherwise>
				Sent on <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.sentDate}"/>
			</c:otherwise>
		</c:choose>
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

<div style="clear:both"></div>

<form:form method="post" action="ordersendemailhist.html" id="sendForm">
<h3>Preview Resend Email Content (Order ID: <c:out value="${param.orderId}"/>)</h3>

<c:choose>
<c:when test="${emailException == null}">

<div class="wrapContent">
	<div class="content_detail">
		<c:if test="${SMSInd=='Y' && emailTemplateDto.lob=='IMS'}">
			<div class="row">
				<span class="label">Method: Email</span>
			</div>
		</c:if>
		<div class="row">
			<span class="label">Subject:</span>
			<span class="input"><c:out value="${emailSubject}"/></span>
		</div>
		<div class="row">
			<span class="label">To:</span>
			<span class="input">
				
				<c:choose>
				<c:when test="${emailTemplateDto.templateId == 'RT003' && emailTemplateDto.lob == 'MOB'}">
				<c:out value="${command.emailAddr}"/>
				<form:hidden path="emailAddr"/>
				</c:when>
				<c:otherwise>
				<form:input path="emailAddr" maxlength="64" cssStyle="width: 20em"/>
				</c:otherwise>
				</c:choose>
				<span class="error error_emailAddr" style="display:none"></span>
			</span>
		</div>
		<div style="clear:both"></div>
		<div id="emailContent"></div>
		<pre id="emailContentSrc" style="display:none"><c:out value="${emailContent}"/></pre>
		
			<c:if test="${SMSInd=='Y' && emailTemplateDto.lob=='IMS'}">
			<div class="row">
				<span class="label">Method: SMS</span>
			</div>
			<div class="row">
				<span class="label">Subject:</span>
				<span class="input SMS"><c:out value="${SMSSubject}"/></span>
			</div>
			<div class="row">
				<span class="label">To:</span>
				<span class="input">
					<form:input path="SMSno" maxlength="8"  cssClass="SMS" cssStyle="width:20em;" onblur="checkMobile()"/>
					<span class="error error_SMSno" style="display:none"></span>
				</span>
			</div>
			
			<div id="SMSContent"></div>
			<pre id="SMSContentSrc" style="display:none"><c:out value="${SMSContent}"/></pre>
			</c:if>
		
	</div>
	<form:hidden path="updateBOMInd"/>
</div>

<div class="buttonmenubox_R">
	<!-- orderId -->
	<form:hidden path="orderId"/>
	<form:hidden path="templateId"/>
	<!-- action -->
	<input type="hidden" name="action" value="SEND">
	<!-- emailSendConfirm -->
	<form:hidden path="emailSendConfirm"/>
	<!-- hideToolbar -->
	<input type="hidden" name="hideToolbar" value="${param.hideToolbar}"> 
	<c:choose>
	<c:when test="${SMSInd=='Y' && emailTemplateDto.lob=='IMS'}">
	<a href="#" class="nextbutton3 sendBtn">Send AF</a>
	</c:when>
	<c:otherwise>
	<a href="#" class="nextbutton3 sendBtn">Send Email</a>
	</c:otherwise>
	</c:choose>
	<c:if test="${not empty isLtsOnlineAcqOrder && isLtsOnlineAcqOrder }">
		<a href="sboordersearch.html" class="nextbutton3">Back</a>
	</c:if>
	<c:if test="${isMobOrder == true}">
			<a href="<c:url value="ordersendemailhist.html"><c:param name="orderId" value="${ccsOrderId}"/><c:param name="action" value="SEARCH_SUBMIT"/><c:param name="lob" value="mob"/></c:url>" class="nextbutton3 closeBtn">Close</a>	
	</c:if>
	<c:if test="${empty isLtsOnlineAcqOrder && isMobOrder==false}">
		<a href="#" class="nextbutton3 closeBtn">Close</a>
	</c:if>	
	
</div>

</c:when>
<c:otherwise>
<!-- in case exception generated from groovy -->

<div class="wrapContent">
	<div class="content_detail">
		<pre><c:out value="${emailException.message}"/>
		<c:forEach var="s" items="${emailException.stackTrace}">
		<c:out value="${s}"/></c:forEach></pre>
	</div>
</div>
</c:otherwise>
</c:choose>
</form:form>

</div>

</c:when>
<c:otherwise>

<div class="previewForm contenttext">
<h3>Search Order Send Email History</h3>
<form:form method="post" action="ordersendemailhist.html" id="searchForm">
<form:hidden path="reset"/>
<div class = "search_div">
	<div class="wrapContent">
		<div class="content_detail">
			<!-- shopCd -->
			<div class="row">
				<span class="label">Shop Code:</span>
				<span class="input"> 
					<form:radiobuttons path="shopCd" items="${shopCds}"/>
				</span>
			</div>
			<!-- lob -->
			<div class="row">
				<span class="label">LOB:</span>
				<span class="input"> 
					<form:radiobuttons path="lob" items="${lobs}"/>
				</span>
			</div>
			<!-- requestDate -->
			<div class="row">
				<span class="label"><label for="searchByOther"><input class="searchByOther" type="radio" name="searchBy" value="other" id="searchByOther">Request Date:</label></span>
				<span class="input searchByOther">
					<form:input path="requestDateStr" readonly="true" id="requestDateDatepicker"/>
					<span class="error error_requestDateStr" style="display:none"></span>
				</span>
			</div>
			<!-- orderId -->
			<div class="row">
				<span class="label"><label for="searchByOrderId"><input class="searchByOrderId" type="radio" name="searchBy" value="orderId" id="searchByOrderId">Order ID:</label></span>
				<span class="input searchByOrderId"> 
					<form:input path="orderId" maxlength="13"/>
					<span class="error error_orderId" style="display:none"></span>
				</span>
			</div>
			<div class="row">
				<span class="label">Order Type:</span>
				<span class="input"> 
					<form:select path="orderType">
						<form:option value="" label="Select..." />
						<form:option value="PCD-A" label="PCD-ACQ" />
						<form:option value="NTV-A" label="NTV-ACQ" />
					</form:select>
				</span> 
			</div>
		</div>
	</div>

	<div class="buttonmenubox_R">
		<!-- action -->
		<input type="hidden" name="action" value="SEARCH_SUBMIT">
		<a class="nextbutton searchButton" href="#">Search</a>
		<a class="nextbutton clearButton" href="#">Clear</a>
	</div>
</div>
</form:form>

<c:if test="${list != null}">
<form:form method="get" action="ordersendemailhist.html" id="previewForm">
<div class="paging_table">
<table class="histList">
<colgroup style="width:125px"></colgroup>
<colgroup style="width:45px"></colgroup>
<colgroup style="width:250px"></colgroup>
<colgroup style="width:200px"></colgroup>
<colgroup style="width:125px"></colgroup>
<thead class="header">
<tr>
	<th>Order ID</th>
	<th>Seq #</th>
	<th>E-mail Type</th>
	<th>Email Address</th>
	<th>Request Date</th>
	<th>Request By</th>
	<th>Result</th>
</tr>
</thead>
<tbody class="content">
<c:choose>
<c:when test="${empty list}">
<tr>
	<td colspan="6" style="text-align: left">No records</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${list}" var="record" varStatus="status">
<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 20}"> style="display:none"</c:if>>
	<td style="text-align:left">
		<form:radiobutton path="orderId" value="${record.orderId}" label="${record.orderId}"/>
		<input type="hidden" name="templateId" value="${record.templateId}" disabled="disabled">
	</td>
	<td><c:out value="${record.seqNo}"/></td>
	<td><c:out value="${record.emailType}"/></td>
	<td><c:out value="${record.emailAddr}"/><c:if test="${record.lob == 'IMS' and record.SMSno != null}">/<br><c:out value="${record.SMSno}"/></c:if></td>
	<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.requestDate}"/></td>
	<td><c:out value="${record.createBy}"/></td>
	<td style="text-align:left">
		<c:choose>
			<c:when test="${record.sentDate == null}">
				<c:out value="${record.errMsg}"/>
			</c:when>
			<c:otherwise>
				Sent on <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.sentDate}"/>
			</c:otherwise>
		</c:choose>
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

<c:if test="${not empty list}">
<div class="buttonmenubox_R">
	<input type="hidden" name="action" value="PREVIEW">
	<a href="#" class="nextbutton3 previewBtn">Preview Email</a>
</div>
</c:if>
</form:form>
</c:if>
</div>

</c:otherwise>
</c:choose>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>