<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>

<html>
<head>
<link href="js/datePicker.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function formSubmit() {
	if(checkAppointmentDatepick()){
		$("#action").val('SUBMIT');
		document.amendmentForm.submit();
	}else{
		$("#action").val('');
	}
}

$(document).ready(function() {
	
	setupInstDatepicker("${earliestSrd}", "${maxDate}");
	
	$(function() {
		$("#nextview").click(formSubmit);
		$("#middle_content").css("min-height",
				viewportHeight - 450 + "px");
		$("#middle_content").css("visibility", "visible");
		$("#content").css("min-height", "500px");
	});
	
	$("#instdate").change(function(event){
		$("#warning_appointment_msg").html("");
		var date = $("#instdate").val();
		timeSlotLookup(date);
	});
});

function timeSlotLookup(date){
	//var sbuid = $('input[name="sbuid"]').val();
	if(date.length == 10){
		$.ajax({
			url : "ajaxappointment.html",
			type : 'POST',
			data : "date=" + date +
					"&onp=" + $("euse_num").is(":checked") +
					"&type=AMEND",
			dataType : 'json',
			//timeout : 5000,
			success : function(data){
				//var obj = jQuery.parseJSON(data);
				pushTimeSlotOptions(data, "");
				return 1;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				return textStatus;	
			}
			/*complete : function() {
				 $.unblockUI(); 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}*/
		});
	}
}

function pushTimeSlotOptions(json, defaultValue){
	var slot = '#apptimeSlot';
	
	$('option', slot).remove();
	//$(slot).append(new Option(' -- SELECT --', '', true, false));

	$.each(eval(json), function(i, item) {
			//$(slot).append(new Option(item.tsdisplay, item.tsvalue, true, false));
			$(slot).append("<option value='"+item.tsvalue+"'>"+item.tsdisplay+"</option>");
	});
}

function setupInstDatepicker(minSRD, maxSRD){
	$("#instdate").datepicker("destroy");
	var beforeShowDayVar = null;
	var minDateVar = '${earliestSrd}';
	var maxDateVar = '';
	if(minSRD){
		minDateVar = minSRD;
	}
	if(maxSRD){
		maxDateVar = maxSRD;
	}
	$("#instdate").datepicker({
		changeMonth : true,
		changeYear : true,
		// 	            maxDate: new Date( (today.getFullYear() - 18),  today.getMonth(), today.getDate() )
		dateFormat : 'yy/mm/dd', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : minDateVar,
		maxDate : maxDateVar,
		yearRange : "c-100:c+100", //the range shown in the year selection box, c= inpu date
		beforeShowDay: beforeShowDayVar
	});
}

function checkAppointmentDatepick(){
	if($('#instdate').val() != ""){
		var appt = $('#instdate');
		var minDateOpt = $(appt).datepicker("option", "minDate");
		var minDate = $.datepicker.formatDate("yy/mm/dd", $.datepicker._determineDate($(appt).data('datepicker'), minDateOpt, new Date()));
		var instDate = $("#instdate").val();
		if(instDate < minDate){
			$("#warning_appointment_msg").html("<spring:message code="reg.iden.inst.warn4" />");
			return false;
		}
		return true;
	}
	$("#warning_appointment_msg").html("<spring:message code="reg.iden.inst.warn4" />");
	return false;
}


</script>

</head>

<body>
<form:form id="amendmentForm" name="amendmentForm" method="POST" commandName="amendmentForm" action="amendment.html">
<div id="wrapper">
<!--content-->
<div id="content">
<!--flow nav-->
<div class="flow">
	<ul>
	<li class="flow_active"><spring:message code="amend.title"/></li>
	</ul>
</div>

<div id="main" style="min-height: 200px !important;">

<div id="middle_content" style="visibility: visible;padding:0px 10px 10px 10px;">

<c:if test="${amendmentForm.allowAmend}">
	<table cellpadding="3" cellspacing="10" style="padding:10px 0px 0px 0px " >
		<input type="hidden" id="action" name="action"/>
		<tr>
			<td width=200><spring:message code="amend.field.title" /> :</td>
			<td width=250>${amendmentForm.title}</td>
		</tr>
		<tr>
			<td><spring:message code="amend.field.lastname" /> :</td>
			<td>${amendmentForm.familyName}</td>
		</tr>
		<tr>
			<td><spring:message code="amend.field.addr" /> :</td>
			<td>${amendmentForm.addr}</td>
		</tr>
		<tr>
			<td><spring:message code="amend.field.instdate" /> :</td>
			<td><form:input id="instdate" path="installDate" autocomplete="off" /></td>
		</tr>
		<tr>
			<td><spring:message code="amend.field.insttime" /> :</td>
			<td>
				<form:select id="apptimeSlot" path="installTimeAndType">
					<form:option value=""> -- SELECT -- </form:option>
					<c:forEach var="slot" items="${timeSlots}">
						<form:option value="${slot.apptTimeSlot}||${slot.apptTimeSlotType}"> ${slot.apptTimeSlot} </form:option>
					</c:forEach>
					<!--<form:options items="${timeSlots}" />-->
				</form:select> </td>
		</tr>
		<tr>
			<td colspan=2><span id="warning_appointment_msg" style="color: #FF0000"></span></td>
		</tr>
	</table>
</c:if>

<c:if test="${!amendmentForm.allowAmend}">
	${amendmentForm.notAllowMsg}
</c:if>
</div>

</div>

</div>
<div id="floating_bar" style="margin-left: 0px;">
				<div class="grad"></div>
					<div id="bottom_content">
                     <table width="100%" style="bottom: -8pt;position: relative;">  
						<tbody>
							<tr> 
								<td width="60%">
								<c:if test="${amendmentForm.allowAmend}">
									<a>
										<span id="nextview" class="grey_btn" style="margin-top: auto">
											<spring:message code="bottom.button.submit" />
										</span>
									</a>
								</c:if>
                                </td>
							</tr>
						</tbody>
                    </table>
					</div>  
					<img src="images/${lang}/bottom_bar.png" />
</div>
<!--end of wrapper-->
</div>
</form:form>
</body>
</html>