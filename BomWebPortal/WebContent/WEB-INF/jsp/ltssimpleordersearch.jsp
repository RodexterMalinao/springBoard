<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript">

function toUpperSetup(id){
	$("#"+id).blur(function(){
		$("#"+id).val($("#"+id).val().toUpperCase());
	});
}

$(document).ready(function() {
	
	toUpperSetup("orderId");
	toUpperSetup("idDocNum");
	toUpperSetup("staffNum");
	
	
	searchLts();
});


function searchSubmit() {
	document.ltsCcOrderSearchForm.action.value = "SEARCH";
	document.ltsCcOrderSearchForm.submit();
}
function searchClear() {
	window.location.href="ltssimpleordersearch.html";
}

function searchLts() {
	$("#ltsOrderSearchResult").children("center").html("<image title='Searching...' src='images/large-loading.gif'/>");
	$("#ltsOrderSearchResult").load("ltsccordersearchresult.html?_t="+ (new Date().getTime()));
}

function wqInquiry(){
	var page = "/qm/ShowWQ.action";

		page = page + "?loginId=" + $('#loginId').val();
		page = page + "&sbSessionId=" + $('#sessionId').val();

	window.open(page, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
	//alert(page);
}

</script>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.orderSearch { padding-left: 0 }
.orderSearchResult { padding-bottom: 1em }
</style>
<![endif]-->


<br>
<table width="98%" align="center">
<tr><td>
<div id="s_line_text">LTS Order Search</div>

<div class="orderSearch">
	<form:form name="ltsCcOrderSearchForm" method="POST" commandName="ltsCcOrderSearchCmd" autocomplete="off">
	<form:hidden path="action"/>
	<input type="hidden" id="loginId" name="loginId" value="<%=username%>" />
	<input type="hidden" id="sessionId" name="sessionId" value="${pageContext.session.id}" />
	<table width="98%" align="center" class="contexttext" cellpadding="3">
		
		<tr>
			<td>Order ID:</td>
			<td>
				<form:input path="orderId" maxlength="20"/>
			</td>
			<td>&nbsp;</td>
			<td>ID Doc Num:</td>
			<td>
				<form:select path="idDocType">
					<form:option value="" label="Select" />
					<form:options items="${idDocTypeList}" />
				</form:select>
				<form:input path="idDocNum" maxlength="20" />
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>Service Number:</td>
			<td>
				<form:input path="serviceNumber" maxlength="20"/>
			</td>
			<td>&nbsp;</td>
			<td>Contact Email Address:</td>
			<td>
				<form:input path="contactEmail" maxlength="40" />
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="6">
				<form:errors path="*" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td colspan="6" align="right">
				<a id="searchButton" style="color:white" href="#" onClick="javascript:searchSubmit();">
					<div class="func_button">Search</div>
				</a>
				<a id="clearButton" style="color:white" href="#" onClick="javascript:searchClear();">
					<div class="func_button">Clear</div>
				</a>			
				<c:if test="{$channelCd != 'QCC'}">
					<a id="btnWqInquiry" style="color:white" href="#" onClick="wqInquiry();">
						<div class="func_button">Work Queue Inquiry</div>
					</a>	
				</c:if>
			</td>
		</tr>					
</table>
</form:form>
</div>

<!-- LTS Order Search -->
<div class="orderSearch" style="min-height: 80px; background-color: white;">
	<div id="s_line_text">LTS Orders</div>
	<div id="ltsOrderSearchResult"><center>No Records</center></div>
</div>

</td>
</tr>
</table>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%> 