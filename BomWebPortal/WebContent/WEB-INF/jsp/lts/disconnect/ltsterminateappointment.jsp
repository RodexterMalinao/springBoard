<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/lts/disconnect/ltsterminateprogressbar.jsp" >
    <jsp:param name="step" value="4" />
</jsp:include>

<script>
function showExternalAddress(){
	if ($("#externalCollection").is(':checked')){
		$(".externalAddress").fadeIn();
	}else{
		$(".externalAddress").hide();
	}
}
</script>

<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script src="js/jquery-ui-1.8.16.custom.min.js"></script>

<script src="js/jquery.lts.autocomplete.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<form:form method="POST" action="#" id="terminateAppointmentForm" commandName="ltsterminateappointmentCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden id="submitInd" path="submitInd" />
<form:hidden id="confirmedInd" path="confirmedInd" />
<form:hidden id="fieldVisitRequired" path="fieldVisitRequired" />
<form:hidden id="allowBackDate" path="allowBackDate" />

<c:set var="profile" value="${sessionScope.sessionLtsTerminateOrderCapture.ltsServiceProfile}" />
<c:set var="profileAddressRollout" value="${profile.address.addressRollout}" />
<table width="98%" class="paper_w2 round_10" align="center">
	<tr>
		<td>
			<table width="98%" border="0" cellspacing="1" rules="none" align="center">
				<tr>
					<td>
					<br/>
					<div id="s_line_text">Appointment Details</div></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="1" cellpadding="4" class="contenttext" align="center">
				<tr>
					<td width="35%">&nbsp;</td>
					<td width="35%">&nbsp;</td>
					<td width="50%">&nbsp;</td>
				</tr>
				<tr class="fiveNa" style="display:show">
					<td align="right">
						5NA (Mass) / 3NA (PT)* : 
					</td>
					<td>
						<form:checkbox path="fiveNa" disabled="${sessionScope.sessionLtsTerminateOrderCapture.ltsTerminateServiceSelectionForm.disconnectReason == '4012'}"/>
						<form:hidden path="allowFiveNa"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						<c:if test="${ltsterminateappointmentCmd.fieldVisitRequired}">Appointment Date* :</c:if> 
						<c:if test="${!ltsterminateappointmentCmd.fieldVisitRequired}">SRD* :</c:if> 
					</td>
					<td>
						<form:input path="appntDate" id="appntDate" maxlength="10" readonly="true"/>
						<br/><form:errors path="appntDate" cssClass="error"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<c:if test="${ltsterminateappointmentCmd.fieldVisitRequired}">
					<c:if test="${not empty imsPendingOrderSrd || ltsterminateappointmentCmd.confirmedInd || ltsterminateappointmentCmd.forceAppntTime}">
						<tr>
							<td align="right">
								Appointment Time* : 
							</td>
							<td>
								<form:input path="appntTimeSlot" readonly="true"/>
								<br/><form:errors path="appntTimeSlotAndType" cssClass="error"/>
							</td>
							<td>&nbsp;</td>
						</tr>
					</c:if>
					<c:if test="${empty imsPendingOrderSrd && !ltsterminateappointmentCmd.confirmedInd && !ltsterminateappointmentCmd.forceAppntTime}">
						<tr>
							<td align="right">
								Appointment Time* : 
							</td>
							<td>
								<form:select id="appntTime" path="appntTimeSlotAndType">
									<form:option value=""> -- SELECT -- </form:option>
									<c:forEach var="slot" items="${appntTimeSlot}">
										<form:option value="${slot.apptTimeSlot}=${slot.apptTimeSlotType}" label="${slot.apptTimeSlot}" />
									</c:forEach>
								</form:select>
								<br/><form:errors path="appntTimeSlotAndType" cssClass="error"/>
							</td>
							<td>&nbsp;</td>
						</tr>
					</c:if>
					<tr>
						<td align="right">
							Pre-book Serial Number* : 
						</td>
						<td>
							<form:input path="preBookSerialNum" id="preBookSerialNum" maxlength="10" readonly="true"/>
							<br/><form:errors path="preBookSerialNum" cssClass="error"/>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">
						</td>
						<td>
							<div id="confirmMsgBlock" style="display:none;">
								<div id="warning_addr" class="ui-widget" style="visibility: visible;">
									<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
										<p>
											<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
										</p>
										<div id="comfirm_msg" class="contenttext">
											<b>Appointment Confirmed</b>
										</div>
										<p></p>
									</div>
								</div>
							</div>
							<div id="cancelMsgBlock" style="display: none;">
								<div id="error_profile" class="ui-widget" style="visibility: visible;">
									<div class="ui-state-error ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
										<p>
											<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
										</p>
										<div id="cancel_msg" class="contenttext">
											<b>${not empty ltsterminateappointmentCmd.appntErrorMsg? ltsterminateappointmentCmd.appntErrorMsg : 'Appointment Cancelled' }</b>
										</div>
										<p></p>
									</div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">
						</td>
						<td>
							<input type="button" value="Confirm" id="confirmAppntBtn"/>
							<input type="button" value="Cancel" id="cancelAppntBtn" />
							<br/><form:errors path="confirmedInd" cssClass="error"/>
						</td>
						<td>&nbsp;</td>
					</tr>
				
				</c:if>
				<c:if test="${not empty ltsterminateappointmentCmd.errorMsg}">
				<tr>
					<td align="right">
					</td>
					<td>
						<div class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
								<div class="contenttext">
									<b>${ltsterminateappointmentCmd.errorMsg}</b>
								</div>
								<p></p>
							</div>
						</div>
					</td>
				</tr>
				</c:if>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table width="98%" class="paper_w2 round_10"  align="center">
	<tr>
		<td>
			<table width="98%" border="0" cellspacing="1" rules="none"  align="center">
				<tr>
					<td><br><div id="s_line_text">Customer Contact Details</div></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="1" cellpadding="4" class="contenttext"  align="center">
				<tr>
					<td width="35%">&nbsp;</td>
					<td width="35%">&nbsp;</td>
					<td width="50%">&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						Customer Contact Name* : 
					</td>
					<td>
						<form:input path="custContactName" id="custContactName" />
						<br/><form:errors path="custContactName" cssClass="error"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						Customer Contact Number* : 
					</td>
					<td>
						<form:input path="custContactNum" id="custContactNum" /> (enter either one)
						<br/><form:errors path="custContactNum" cssClass="error"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						Customer Contact Mobile Number* : 
					</td>
					<td>
						<form:input path="custContactMobileNum" id="custContactMobileNum" /> (enter either one)
						<br/><form:errors path="custContactMobileNum" cssClass="error"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<c:if test="${ltsterminateappointmentCmd.fieldVisitRequired && !sessionScope.sessionLtsTerminateOrderCapture.ltsTerminateServiceSelectionForm.lostModem}">
					<tr>
						<td align="right">
							Collect Equipment at Another Place : 
						</td>
						<td>
							<form:checkbox id="externalCollection" path="externalCollection" onclick="showExternalAddress()"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table width="98%" border="0" class="paper_w2 round_10" align="center">
	<tr class="externalAddress" style="display:none">
		<td>
			<table width="98%" border="0" cellspacing="1" rules="none" align="center">
				<tr class="externalAddress" style="display:none">
					<td><br><div id="s_line_text">Change Equipment Collection Address</div></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="1" cellpadding="4" class="contenttext" align="center">
				<tr class="externalAddress" style="display:none">
					<td width="35%">&nbsp;</td>
					<td width="35%">&nbsp;</td>
					<td width="50%">&nbsp;</td>
				</tr>
				<tr class="externalAddress" style="display:none">
					<td align="right">
						Quick Search* : 
					</td>
					<td>
						<form:input path="baQuickSearch" size="80"/>
					</td>
					<td>
						<div class="func_button">
							<a href="#" id="clearBaInputAddress">Clear</a>
						</div>
					</td>
				</tr>
				<tr class="externalAddress" style="display:none">
					<td>&nbsp;</td>
					<td align="left">
						Simply input the building name of your billing address, separate with a comma (,)
					</td>
				</tr>
				<tr class="externalAddress" style="display:none">
					<td>&nbsp;</td>
				</tr>					
				<tr class="externalAddress" style="display:none">
					<td align="right">
						Equipment Collection Address* : 
					</td>
					<td>
						<form:textarea id="billingAddressTextArea" path="alternateAddressDetails" rows="6" cols="40" cssStyle="resize: none;"/>
						<form:errors path="alternateAddressDetails" cssClass="error" />
					</td>
				</tr>
				<tr class="externalAddress" style="display:none">
					<td colspan="6">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table width="98%" border="0" class="paper_w2 round_10" align="center">
	<tr>
		<td>
			<table width="98%" border="0" cellspacing="1" rules="none" align="center">
				<tr>
					<td><br><div id="s_line_text">Field Service Add-on Remarks</div></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="1" cellpadding="4" class="contenttext">
				<tr>
					<td width="35%">&nbsp;</td>
					<td width="35%">&nbsp;</td>
					<td width="50%">&nbsp;</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
						<form:errors path="remarks" cssClass="error"/>
						<form:textarea path="remarks" rows="10" cols="100%"/>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>		
			</table>
		</td>
	</tr>
</table>
<br>
<c:if test="${ltsterminateappointmentCmd.allowProceed}">
<table width="100%" border="0" cellspacing="0" class="contenttext">
	<tr>
		<td>
			&nbsp;
		</td>
		<td align="right">
			<form:errors path="suspendReason" cssClass="error" /> 
				<b>Suspend Reason : </b> 
			<form:select path="suspendReason" id="suspendReason">
				<form:option value="NONE">-- SELECT --</form:option>
				<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
			</form:select>
			<div class="button">
				<a href="#" id="suspend" class="nextbutton">Suspend</a>
			</div>
			<div class="button">
				<a href="#" id="next" class="nextbutton">Continue</a>
			</div>
		</td>
	</tr>
</table>
</c:if>
</form:form>

<script>
var srvSelectApplicationDate = "${applicationDate}";
var isDeathCase = "${isDeathCase}" == "true";
var isEyeSrv = "${not empty profile.existEyeType}" == "true";
var imsPendingOrderExist = "${not empty imsPendingOrderSrd}" == "true";
var fieldVisitRequired = $("#fieldVisitRequired").val() == "true";
var allowBackDate = $("#allowBackDate").val() == "true";
var orderCreateDate = "${not empty sessionScope.sessionLtsTerminateOrderCapture.sbOrder? fn:substring(sessionScope.sessionLtsTerminateOrderCapture.sbOrder.appDate, 0, 10): ''}";
var isTerminateWithPCD = "${isTerminateWithPCD}" == "true";

function pushTimeSlotOptions(json, defaultValue){
	var slot = '#appntTime';
	$('option', slot).remove();
	$(slot).append(new Option(' -- SELECT --', '', true, false));

	$.each(eval(json), function(i, item) {
		var timeslotoptions = $(slot).attr('options');
		if(item.tsvalue == defaultValue){
			timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, true);
		}else{
			timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
		}
	});

	colorTimeSlot();
}
function colorTimeSlot(){
	var timeslotlist=document.getElementById('appntTime');
	var i;
	for (i=1;i<timeslotlist.length;i++){//skip first element
		var slotOption = timeslotlist.options[i];
		var slotType = slotOption.value.split("=")[1];
		if(slotType != null){
			if(slotType=="NS"){ //indicate no resource
				slotOption.style.color="red";
			}else if(slotType.value=="RS"){//restricted
				slotOption.style.color="silver";
			}else{
				slotOption.style.color="black";
			}
		}
	}		
}
function resetFormDisplay(){
	$("#preBookSerialNum").attr('disabled', false);
	$("#appntDate").attr('disabled', false);
	$("#appntTime").attr('disabled', false);

}
function confirmDisplay(val){
	if(val == "confirm"){
		$('#confirmMsgBlock').show();
		$('#cancelMsgBlock').hide();
		$('#confirmAppntBtn').attr('disabled', true);
		$('#cancelAppntBtn').attr('disabled', false);
		$("#appntDate").attr('disabled', true);
		$("#appntTime").attr('disabled', true);
	}
	else if(val == "cancel"){
		$('#preBookSerialNum').val("");
		$('#confirmMsgBlock').hide();
		$('#cancelMsgBlock').show();
		$('#confirmAppntBtn').attr('disabled', false);
		$('#cancelAppntBtn').attr('disabled', true);
		resetFormDisplay();
	}
}
function timeSlotLookup(defaultValue){
	if(!fieldVisitRequired){
		return;
	}
	var sbuid = $('input[name="sbuid"]').val();
	if($("#appntDate").val().length == 10){
		$.ajax({
			url : "ltsappointmenttimeslotlookup.html?sbuid=" + sbuid,
			type : 'POST',
			data : "date=" + $("#appntDate").val() + "&type=D",
			dataType : 'json',
			//timeout : 5000,
			success : function(data){
				//var obj = jQuery.parseJSON(data);
				pushTimeSlotOptions(data, defaultValue);
				return 1;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				return textStatus;	
			},
			complete : function() {
				 $.unblockUI(); 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}
		});
	}
}


function resetApptDatePicker(minDate, maxDate){
	if(imsPendingOrderExist){
		return;
	}
	if(minDate==''){
		if(allowBackDate){
			minDate=srvSelectApplicationDate;
		}
		if(isTerminateWithPCD){
			minDate=plusDate('', 2);
		}
		if(isEyeSrv){
			minDate=plusDate('', 3);
		}
		if(minDate == ''){
			minDate=plusDate('', 0);
		}
	}
	if(maxDate==""){
		maxDate = plusDate(orderCreateDate, 0);
		maxDate.setDate(1);
		maxDate.setMonth(maxDate.getMonth() + 4);
		maxDate.setDate(0);
	}
	$('#appntDate').datepicker("destroy");
	$('#appntDate').datepicker({
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate: minDate, maxDate: maxDate, //Y is year, M is month, D is day  
		yearRange: "0:+100" //the range shown in the year selection box
	});
}

$(document).ready(function() {
	resetFormDisplay();
	
	var confirmedInd = "${ltsterminateappointmentCmd.confirmedInd}";
	resetApptDatePicker("", "");
	if($("#appntTime").val() == "" && $("#appntDate").val() != ""
		&& confirmedInd == "false"){
		timeSlotLookup();
	}
	
	if($("#confirmedInd").val() == "true"){
		confirmDisplay("confirm");
	}else{
		$('#cancelAppntBtn').attr('disabled', true);
		if($("#submitInd").val() == "CONFIRM"
				|| $("#submitInd").val() == "CANCEL"){
			confirmDisplay("cancel");
		}
	}
	$("#newBillingAddress").blur(function(){
    	$("#newBillingAddress").val($("#newBillingAddress").val().toUpperCase());
    }).blur(function(){
    	$("#newBillingAddress").val($("#newBillingAddress").val().toUpperCase());
    });
	$('input[name="baQuickSearch"]').autocomplete("imsaddresslookup.html?type=address", {
		//minChars: 4,
		minChars : 1,
		delay : 600,
		selectFirst : false,
		max : 12,
		matchSubset : false,
		highlight : false,
		ba : true,
		//extraParams: {random: (function(){return Math.random()})},
		formatItem : function(row, i, max) {
			return row;
		}
	});
	
	
	$("#clearBaInputAddress").click(function(event) {
		$("#baQuickSearch").val("");
		$("#billingAddressTextArea").val("");
	});
    $('input[name="clearBaInputAddress"]').click(function(event) {
		$("#newBillingAddress").val("");
		$('input[name="baQuickSearch"]').val("");
		$('input[name="baQuickSearch"]').attr('readonly', '');
		$('input[name="newBillingAddress"]').val("");
	});
    $("input#confirmAppntBtn").click(function(event) {
		event.preventDefault();
		$("#submitInd").val("CONFIRM");
		$("#confirmedInd").val("true");
		$("#appntDate").attr('disabled', false);
		$("#appntTime").attr('disabled', false);
		$("#terminateAppointmentForm").submit();
	});
	$("input#cancelAppntBtn").click(function(event) {
		$("#submitInd").val("CANCEL");
		$("#preBookSerialNum").attr('disabled', false);
		$("#appntDate").attr('disabled', false);
		$("#appntTime").attr('disabled', false);
		$("#confirmedInd").val("false");
		$("#terminateAppointmentForm").submit();
	});
	
	$("#next").click(function(event) {
		event.preventDefault();
		$("#submitInd").val("SUBMIT");
		$("#appntDate").attr('disabled', false);
		$("#appntTime").attr('disabled', false);
		$("#terminateAppointmentForm").submit();
	});
	$("#suspend").click(function(event){
		event.preventDefault();
		$("#submitInd").val("SUSPEND");
		$("#terminateAppointmentForm").submit();
	});
	
    $('form').bind('submit', function() {
    	$.blockUI({ message: null });
    });
    
	$("#appntDate").change(function(){
		$("#appntDate").css('color', 'black');
		if("${ltsterminateappointmentCmd.forceAppntTime}" != "true"){
			timeSlotLookup($('#appntDate').val(), 'D');
		}
	});
	
	
});

function plusDate(date, val){
	var newDate;
	if(date != ''){
		newDate = $.datepicker.parseDate('dd/mm/yy', date);
	}else{
		newDate = $.datepicker.parseDate('dd/mm/yy', $.datepicker.formatDate('dd/mm/yy', new Date()));
	}
	if(val != ''){
		newDate.setDate(newDate.getDate() + val);
	}
	return newDate;
}

</script>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>