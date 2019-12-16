<%if ("Y".equals(request.getParameter("dM"))){%>
	<%@ include file="/WEB-INF/jsp/dialogheader.jsp"%>
<%}else{%>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%} %>
<%@ page import="com.bomwebportal.service.OrderEsignatureService.EmailReqResult"%>
<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<script type="text/javascript" src="js/jquery.pajinate.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>

<script type="text/javascript">
var mobileChecked = false;

function openDialog(){
	if ($("input[name=orderId]:checked").length == 0) {
		alert("Please select one record");
		return false;
	}
	var $td = $("input[name=orderId]:checked").parent();
	var seqno = $("input[name=orderId]:checked").parent().parent().find('.seqno').text();
	var tempId = $td.find("input[name=templateId]}").val();
	$("input[name=templateId]").attr("disabled", true);
	$td.find("input[name=templateId]}").attr("disabled", false);
	openModalDialog("dsordersendemailhist.html?dM=Y&action=PREVIEW" +
			"&orderId=" + $("input[name=orderId]:checked").val() +
			"&templateId=" + tempId +
			"&seqNo=" + seqno
			, "Order Id :"+$("input[name=orderId]:checked").val());
}

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
	
	if ( $("#teamCd option").length == 1 )
	{
		$("#teamCd").attr("disabled",true);
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
		
		if ( $("#teamCd option").length == 1 )
		{
			$("#teamCd").val("");
		}
		
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
				$("#searchForm input[name=orderId]").val($("#searchForm input[name=orderId]").val().toUpperCase());
				var orderId = $.trim($("#searchForm input[name=orderId]").val());
				var orderType = $('#orderType').val();
				
				if (orderId == null || orderId.length == 0) 
				{
					$(".error_orderId").text("Requires Order ID").show();
				} 
				else if(orderType == 'NTVAO' || orderType == 'NTVUS' || orderType == 'NTVRE'){
					if(orderId.match(/[A-Z0-9]{4}[MEPV][AUR][0-9]{6}/) == null){
						$(".error_orderId").text("Invalid Order ID pattern").show();
					}
				}
				else if (orderId.match(/[A-Z0-9]{4}[MEPV][0-9]{6}/) == null) 
				{
					if(orderType == null || orderType == ''){
						if(orderId.match(/[A-Z0-9]{4}[MEPV][AUR][0-9]{6}/) == null){
							$(".error_orderId").text("Invalid Order ID pattern").show();
						}
					}
					else{
						$(".error_orderId").text("Invalid Order ID pattern").show();
					}
				} 
// 				else if (orderId.substring(0, 1) != 'C' && orderId.substring(0, 1) != 'P') 
// 				{
// 					$(".error_orderId").text("Require Order ID starts with C or P").show();
// 				} 
				else 
				{
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
		$("#searchForm [name=teamCd]").val("");
		$("#searchForm [name=orderType]").val("");
		$("input.searchByOther").attr("checked", true);
// 		$("#searchForm [name=teamCd]").attr("selectedIndex",0);
// 		$("#searchForm input[name=lob]:first").attr("checked", true);
		$("#searchForm #requestDateDatepicker").val($.datepicker.formatDate("dd/mm/yy", new Date()));
		$("#searchForm input[name=orderId]").val("");
// 		$("#searchForm input[name=orderType]:first").attr("checked", true);
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
		//$("#previewForm").submit();
		openDialog();
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

function chooseMethod()
{
	$("[name=action]").val("PREVIEW");
	$("#sendForm").submit();	
}

function chooseMethod2()
{	$(".email").show();
	$(".SMS").show();
	
	checkEmail();
	checkMobile();
	if ($(".error:visible").length == 0) {
	 if ($("input[name=emailAddr]").val().length != 0 && "${SMSInd}"=="Y" && $("input[id='SMSno']").val().length != 0){
	  	$("#method").val("DS");
	 }else if ($("input[name=emailAddr]").val().length != 0){
		 $("#method").val("E");
	 }else if ("${SMSInd}"=="Y" && $("input[id='SMSno']").val().length != 0){
		 $("#method").val("S");
	 }
	}

// 	$(".error_emailAddr").html("").hide();
// 	$(".error_SMSno").html("").hide();
	
}

function checkMobile(boo)
{
	if("${SMSInd}"=="Y"){
	if ($("input[id='SMSno']").val().length == 0) 
	{
// 	$(".error_SMSno").text("Require SMSno.").show();	
		$(".error_SMSno").text("").hide();
	} 
	
// 	if ($("input[id='SMSno']").val().length != 0){
else{
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
						$("#updateBOMInd").val("N");
						if ("${pcdOrder}"=="Y"){
							alert("Email / mobile number will overwrite existing customer's contact info for future communication.");
							$("#updateBOMInd").val("Y");
							$("#sendForm").submit();
						}else{
							$("#sendForm").submit();
						}
					}
				}
				else if (!item.valid )
				{
					$(".error_SMSno").text("Invalid mobile number").show();
				}
				count++;
			}
			);
			if(count==0)
			{
				$(".error_SMSno").text("").hide();
			}
		}
	});
	}
	}
}
function checkEmail(){
	if ($("input[name=emailAddr]").val().length == 0) 
	{
// 		$(".error_emailAddr").text("Require Email.").show();
		$(".error_emailAddr").text("").hide();
	}
	else if (!(/^\S+@\S+$/).test($("input[name=emailAddr]").val())) 
	{
		$(".error_emailAddr").text("Invalid Email format").show();
	}
	else{
		$(".error_emailAddr").text("").hide();
	}
}
function initActionPreview() {
	
	chooseMethod2();
	
	$("input[name=emailSendConfirm]").val("false");
	
	$("#sendForm").submit(function(e) 
	{
		if ( $("[name=action]").val() != "PREVIEW")			
		{
			chooseMethod2();
			if ( !mobileChecked )
			{
// 				$(".error").hide();
				
				if ( $("#method").val() == "E"  )
                  {
					$("input[name=emailAddr]").val($.trim($("input[name=emailAddr]").val()));

					if ($(".error:visible").length > 0) {
						return false;
					}
					if ($("input[name=emailAddr]").val().length  == 0)
						return false;
					if (!confirm("Email will be sent to " + $("input[name=emailAddr]").val() + ". Confirm to continue?")) {
						return false;
					}
					$("input[name=emailSendConfirm]").val("true");
				}
				else if ( $("#method").val() == "S" ){
	
					if ($(".error:visible").length > 0) {
						return false;
					}
					if ( $("input[id='SMSno']").val().length == 0)
						return false;
					if (!confirm("SMS will be sent to " + $("input[id='SMSno']").val() + ". Confirm to continue?")) {
						return false;
					}
					checkMobile(true);		
					return false;
				}
				else if ($("#method").val() == "DS" ){
					$("input[name=emailAddr]").val($.trim($("input[name=emailAddr]").val()));
					
					if ($("input[name=emailAddr]").val().length == 0) 
					{
// 						$(".error_emailAddr").text("Require Email.").show();
						return false;
					} 
					if ($("input[id='SMSno']").val().length == 0) 
					{
// 						$(".error_SMSno").text("Require SMSno.").show();	
						return false;
					} 
					if ($(".error:visible").length > 0) {
						return false;
					}
					if (!confirm("Email will be sent to " + $("input[name=emailAddr]").val() + ". Confirm to continue?")) {
						return false;
					}
					if (!confirm("SMS will be sent to " + $("input[id='SMSno']").val() + ". Confirm to continue?")) {
						return false;
					}
					checkMobile(true);	
					return false;
				}
			}
			else
			{
				$("input[name=emailSendConfirm]").val("true");
			}
		}
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
		window.location = "dsordersendemailhist.html";
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

function refreshPreview(seqno, templateId){

	var orderId = document.getElementById('selectedOrder').value;
	
	window.location.assign("dsordersendemailhist.html?dM=Y&action=PREVIEW"+"&orderId=" + orderId +"&seqNo="+seqno+"&templateId="+templateId);
	
}

</script>

<style type="text/css">
.ui-widget-header {
border: 1px solid #6CA9F5;
background: #6CA9F5;
color: #ffffff;
font-weight: bold;
}
.ui-widget-content {
border: 1px solid #6CA9F5;
background: #ffffff  50% bottom repeat-x;
color: #222222;
}
.contenttextgreen {
font-family: "Helvetica", "Verdana", "Arial", "sans-serif", ·s²Ó©úÅé;;
padding-left: 5px;
font-size: 14px;
color: #6CA9F5;
font-weight: bold;
}
.label { display: inline-block; font-weight: bold; width: 120px }
.row { height: 24px }
.input { display: inline-block }
.input label { display: inline-block; width: 50px }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #6CA9F5; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.wrapContent { display: inline-block; width: 100%; padding: 10px 0 }
.previewForm .content_detail { margin-left: 2em }
.previewForm .half { width: 40%; float: left }
.header tr { background-color: #E8F2FE }
/* .even { background-color: #E8FFE8 } */
.success { color: blue }
.fail { color: red }
h2 { margin: 0 }
.histList { width: 100%; clear: both; background-color: white}
.histList td { text-align: center }
.mainContent { padding: 1em; border: 1px solid #F2F2F2; margin-top: 1em; margin-right: 2em }
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
		<h2 class="success">AF sent on <c:out value="${param.sentDate}"/></h2>
	</c:when>
	<c:otherwise>
		<%
		try {
			pageContext.setAttribute("emailReqResult", EmailReqResult.valueOf(request.getParameter("result")));
		} catch (Exception e) {}
		%>
		<h2 class="fail">Failure in send AF, <c:out value="${emailReqResult.message}"/></h2>
	</c:otherwise>
	</c:choose>
</c:when>
<c:when test="${param.emailSendConfirm == 'false'}"><h2 class="fail">Please confirm to send AF</h2></c:when>
</c:choose>

<c:choose>
<c:when test="${command.action == 'PREVIEW'}">

<input id="selectedOrder" type="hidden" name="orderId" value="${list[0].orderId}"/>

<div class="previewForm sendForm contenttext">
<h3>Order Send AF History (Order ID: <c:out value="${param.orderId}"/>)</h3>

<div class="paging_table">
<table class="histList">
<colgroup style="width:45px"></colgroup>
<colgroup style="width:250px"></colgroup>
<colgroup style="width:200px"></colgroup>
<colgroup style="width:125px"></colgroup>
<thead class="header">
<tr>
	<th>Seq #</th>
	<th>Method</th>
	<th>Destination</th>
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
<c:forEach items="${list2}" var="record" varStatus="status"> 

	<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 20}"> style="display:none"</c:if>>
					<td ><a href="###"  onclick="refreshPreview(${record.seqNo},'${record.templateId}')"><c:out value="${record.seqNo}" /></a></td>

               
		<td>
			<c:choose>			
				<c:when test='${record.method == "E"}'>Email</c:when>	
				<c:when test='${record.method == "S"}'>SMS</c:when>
				<c:when test='${record.method == "DS"}'>Email / SMS</c:when>
			</c:choose>
		</td>
		<td>
			<c:choose>			
				<c:when test='${record.method == "E"}'><c:out value="${record.emailAddr}"/></c:when>
			    <c:when test='${record.method == "S"}'><c:out value="${record.SMSno}"/></c:when>
				<c:when test='${record.method == "DS"}'><c:out value="${record.emailAddr}"/>/<br><c:out value="${record.SMSno}"/></c:when>
			</c:choose>
		</td>
		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.requestDate}"/></td>
		<td><c:out value="${record.createBy}"/></td>
		<td style="text-align:left">
			<c:choose>
				<c:when test="${record.errMsg != null}">
					<c:out value="${record.errMsg}"/>
					</c:when>
				<c:otherwise>
					Sent on <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.sentDate}"/>
				</c:otherwise>
					
			</c:choose>
		</td>
	</tr>
</c:forEach>
 
 </c:otherwise></c:choose>
</tbody>
</table>

<div class="page_navigation"></div>
<span class="info_text"></span>

</div>



<form:form method="post" action="dsordersendemailhist.html" id="sendForm">
<h3>Preview Resend AF Content (Order ID: <c:out value="${param.orderId}"/>)</h3>

<c:choose>
<c:when test="${emailException == null}">

<div class="wrapContent">
	<div class="content_detail">
		<div class="row">		
		<span class="label">Method: Email</span>
			<form:hidden path="method"/>
		</div>
		<div class="row">
			<span class="label">Subject:</span>
			<span class="input email"><c:out value="${emailSubject}"/></span>
		</div>
		<div class="row">	
			<span class="label">To:</span>
			<span class="input">
				<form:input path="emailAddr" maxlength="64" cssClass="email" cssStyle="width: 20em" onblur="checkEmail()"/>
				<span class="error error_emailAddr" style="display:none"></span>
			</span>
		</div>
		<div class="mainContent email">${emailContent}</div>
			<pre id="emailContentSrc" ></pre>
		<c:if test="${SMSInd=='Y'}">
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
				<form:input path="SMSno" maxlength="8"  cssClass="SMS" cssStyle="width:20em; display:none;" onblur="checkMobile()" />
				<span class="error error_SMSno" style="display:none"></span>
			</span>
		</div>
		<div class="mainContent SMS" style="display:none">${SMSContent}</div>
			<pre id="SMSContentSrc" ></pre>	
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
	<c:if test="${SaleResendEmailAllowed == 'Y'}">
		<input type="hidden" name="hideToolbar" value="${param.hideToolbar}"> 
		<a href="#" class="nextbutton3 sendBtn" >Send AF</a>
	</c:if>
	<c:if test="${not empty isLtsOnlineAcqOrder && isLtsOnlineAcqOrder }">
		<a href="sboordersearch.html" class="nextbutton3">Back</a>
	</c:if>
	<c:if test="${empty isLtsOnlineAcqOrder}">
<!-- 		<a href="#" class="nextbutton3 closeBtn ui-dialog-titlebar-close">Close</a> -->
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
<h3>Search Order Send AF History</h3>
<form:form method="post" action="dsordersendemailhist.html" id="searchForm">
<form:hidden path="reset"/>
<div class="wrapContent">
	<div class="content_detail">
		<!-- shopCd -->
		<div class="row">
			<span class="label">Team Code:</span>
			<span class="input"> 
				<form:select path="teamCd">
					<c:if test='${fn:length(teamCds)>1}'><option value="">ALL</option></c:if>
					<form:options items="${teamCds}"/>
				</form:select>
			</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="label">Order Type:</span>
			<span class="input"> 
				<form:select path="orderType">
					<form:option value="" label="Select..." />
					<form:option value="PCD-A" label="PCD-ACQ" />
					<form:option value="NTV-A" label="NTV-ACQ" />
					<form:option value="NTVAO" label="NTV-AddOn" />
					<form:option value="NTVUS" label="NTV-Upsell" />
					<form:option value="NTVRE" label="NTV-Ret" />
				</form:select>
			</span>
		</div>
		<!-- lob -->
		<div class="row" style="display:none;">
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
	</div>
</div>

<div class="buttonmenubox_R">
	<!-- action -->
	<input type="hidden" name="action" value="SEARCH_SUBMIT">
	<a class="nextbutton searchButton" href="#">Search</a>
	<a class="nextbutton clearButton" href="#">Clear</a>
</div>
</form:form>

<c:if test="${list != null}">
<form:form method="get" action="dsordersendemailhist.html" id="previewForm">
<div class="paging_table">
<table class="histList">
<colgroup style="width:125px"></colgroup>
<colgroup style="width:45px"></colgroup>
<colgroup style="width:250px"></colgroup>
<colgroup style="width:200px"></colgroup>
<colgroup style="width:125px"></colgroup>
<thead class="header">
<tr>
	<th width=135>Order ID</th>
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
<c:when test="${empty list}">
<tr>
	<td colspan="6" style="text-align: left">No records</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach items="${list3}" var="record" varStatus="status">
<%-- <tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 20}"> style="display:none"</c:if>> --%>
<tr<c:if test="${status.index >= 20}"> style="display:none"</c:if>>
	<td style="text-align:left">
		<form:radiobutton path="orderId" value="${record.orderId}" label="${record.orderId}" />
		<input type="hidden" name="templateId" value="${record.templateId}" disabled="disabled">
	</td>
	<td class="seqno"><c:out value="${record.seqNo}"/></td>
	<td><c:out value="${record.emailType}"/></td>
	<td>
		<c:choose>
			<c:when test='${record.method == "S"}'>SMS</c:when>
			<c:when test='${record.method == "E"}'>Email</c:when>
			<c:when test='${record.method == "DS"}'>Email / SMS</c:when>
		</c:choose>
	</td>
	<td>
		<c:choose>			
			<c:when test='${record.method == "E"}'><c:out value="${record.emailAddr}"/></c:when>
			<c:when test='${record.method == "S"}'><c:out value="${record.SMSno}"/></c:when>
			<c:when test='${record.method == "DS"}'><c:out value="${record.emailAddr}"/>/<br><c:out value="${record.SMSno}"/></c:when>
		</c:choose>
	</td>
	<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.requestDate}"/></td>
	<td><c:out value="${record.createBy}"/></td>
	<td style="text-align:left">
		<c:choose>
			<c:when test="${record.errMsg != null}">
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
		<a href="#" class="nextbutton previewBtn">Preview</a>
	</div>
</c:if>
</form:form>
</c:if>
</div>

</c:otherwise>
</c:choose>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>