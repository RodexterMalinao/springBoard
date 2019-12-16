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
	
	$('#startDate').datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920
		minDate : "-2Y",
		maxDate : "0", //Y is year, M is month, D is day
		inline : true,
		onSelect: function(dateText, inst) {
			var futureDate = $(this).datepicker('getDate');
			futureDate.setDate(futureDate.getDate() + 14);
			$('#endDate').attr("disabled", false);
			$('#endDate').datepicker( "option", "minDate", $(this).datepicker("getDate"));
			$('#endDate').datepicker( "option", "maxDate", futureDate);
			$('#endDate').datepicker( "setDate", $(this).datepicker("getDate"));
		}
	}); 
	
	$('#endDate').datepicker({
		changeMonth : true,
		changeYear : false,
		showButtonPanel : true,
		dateFormat : 'dd/mm/yy',
		maxDate : "0", 
		beforeShow: function(input, inst) {
			var currentDate = $('#startDate').datepicker("getDate");
			var now = new Date();
			if (currentDate != null) {
				currentDate.setDate(currentDate.getDate() + 14);
				$('#endDate').datepicker( "option", "minDate", $('#startDate').datepicker("getDate"));
				$('#endDate').datepicker( "option", "maxDate", currentDate > now? now: currentDate);
			}
		}
	}); 
	

	$('#srdStartDate').datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920
		//minDate : "-2Y",
		//maxDate : "0", //Y is year, M is month, D is day
		onSelect: function(dateText, inst) {
			var futureDate = $(this).datepicker("getDate");
			futureDate.setDate(futureDate.getDate() + 14);
			$('#srdEndDate').attr("disabled", false);
			$('#srdEndDate').datepicker( "option", "minDate", $(this).datepicker("getDate"));
			$('#srdEndDate').datepicker( "option", "maxDate", futureDate);
			$('#srdEndDate').datepicker( "setDate", $(this).datepicker("getDate"));
		}
	}); 

	$('#srdEndDate').datepicker({
		changeMonth : true,
		changeYear : false,
		showButtonPanel : true,
		dateFormat : 'dd/mm/yy',
		//maxDate : "0", 
		beforeShow: function(input, inst) {
			var currentDate = $('#srdStartDate').datepicker("getDate");
			var now = new Date();
			if (currentDate != null) {
				currentDate.setDate(currentDate.getDate() + 14);
				$('#srdEndDate').datepicker( "option", "minDate", $('#srdStartDate').datepicker("getDate"));
				$('#srdEndDate').datepicker( "option", "maxDate", currentDate);
			}
		}
	}); 
	
	searchLts();
});


function searchSubmit() {
	document.ltsCcOrderSearchForm.action.value = "SEARCH";
	document.ltsCcOrderSearchForm.submit();
}
function searchClear() {
	window.location.href="ltsccordersearch.html";
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
			<td>Team Code:</td>	
			<td colspan="5">
				<form:select path="teamCode">
					<form:option value="" label="--All--" />
					<form:options items="${teamCodeList}" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td>Staff Num:</td>
			<td colspan="5">
				<form:input path="staffNum" />
			</td>
		</tr>
		<tr>
			<td>Application Date:</td>
			<td>
				<form:input path="startDate" readonly="true"/>
			</td>
			<td>&nbsp;</td>
			<td>End Date:</td>
			<td>
				<form:input path="endDate" readonly="true"/>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>SRD:</td>
			<td>
				<form:input path="srdStartDate" readonly="true"/>
			</td>
			<td>&nbsp;</td>
			<td>End Date:</td>
			<td>
				<form:input path="srdEndDate" readonly="true"/>
			</td>
			<td>&nbsp;</td>
		</tr>
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
			<td>Order Status:</td>
			<td colspan="5">
				<form:select path="sbOrdStatus">
					<form:option value="" label="Select" />
					<form:options items="${sbOrdStatusList}" itemValue="itemKey" itemLabel="itemValue"/>
				</form:select>
			</td>
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