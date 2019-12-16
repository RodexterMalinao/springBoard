<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>
<html>
<head>
<link href="js/datePicker.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#num_select_cont,#num_input_cont{padding:15px 35px 15px;display:none;}
#reuse_num{margin-top:5px;}
#num_select_cont .lbl{margin-bottom:20px;height:24px;padding-bottom:4px;font-size:12px;color:#fff;background:#535353;}
.nameof{font-size:0.8em;}
.exist_num{border-style:inset;width:200px}
</style>
<script type="text/javascript" charset="utf-8" src="./js/shoppingcart.js"></script>
<script type="text/javascript">
 

function toggleDiv(divId) {
	   //$("#"+divId).slideToggle("5000");
	   $("#"+divId).fadeToggle("5000");
	   //$("#"+divId).toggle("5000");
}

var turnOnBirth = false;
var submitPressed;
var instdateDatepickertemp = "";
var confirmCount = 0;
var regCsPortal = false;
var regTheClub = false;
var regCsPortalTheClub = false;
var lcErrorCount = 0;

function errorCounting(){
	lcErrorCount += 1;
	if(lcErrorCount == 3){
		popLiveChatNotice();
	}
}

function formSubmit() {
	//validation
	var errorCount = 0;
	var errorSect = ".flow";
	
	/*if($("#wip").is(":checked")){
		errorCount ++;
		reg_open_chat(liveChatUrl,srvType);
	}*/

	if($("#wip").is(":checked") || $("#sh_cust").is(":checked")){
		errorCount ++;
		reg_open_chat(liveChatUrl,srvType);
	}

	$("span[id^='warning_']").each(function() {
		this.innerHTML = "";
	});
	
	submitPressed = true;
	toUpper("custLastName");
	toUpper("custFirstName");
	if(!checkName("custLastName")){
		errorCount++;
	}
	if(!checkName("custFirstName")){
		errorCount++;
	}

	if(!$("[name='title']").is(":checked")){
		updateErrorMsgDisplay("warning_title_msg", "<spring:message code="reg.iden.tit.warn" />");
		errorCount++;
	}

	if(!$("[name='docType']").is(":checked")){
		updateErrorMsgDisplay("warning_docType_msg", "<spring:message code="reg.iden.idt.warn" />");
		errorCount++;
	}
	
	if(isHKID){
		if(!checkHkid()){
			errorCount++;
		}
	}else{
		if(!checkPassport()){
			updateErrorMsgDisplay("warning_idNo", "<spring:message code="reg.iden.idno.warn" />");
			errorCount++;
		}
	}

	if($("#Birth").val() == ""){
		updateErrorMsgDisplay("warning_Birth_msg", "<spring:message code="reg.iden.dob.warn1" />");
		errorCount++;
	}
	
	if(errorCount == 0){
		errorSect = "#ContactEmailAddress";
	}

	if(!validateEmail(true)){
		errorCount++;
	}
	
	if($("#csDocError").is(":checked")){
		errorCount++;
		reg_open_chat(liveChatUrl,srvType);
	}

	if($("#csEmailError").val() != ""){
		updateErrorMsgDisplay('warning_email_msg', $("#csEmailError").val());
		errorCount++;
	}
	
	if(!checkMobile('mobileNum')){
		errorCount++;
	}
	
	if(regCsPortalTheClub && !$("#csPortalTheClubConfirm").is(":checked")){
		updateErrorMsgDisplay("warning_csp_msg", "<spring:message code='reg.iden.csportal.theclub.warn' />");
		errorCount++;
	}
	else if(regCsPortal && !$("#csPortalConfirm").is(":checked")){
		updateErrorMsgDisplay("warning_csp_msg", "<spring:message code='reg.iden.csportal.warn' />");
		errorCount++;
	}
	else if(regTheClub && !$("#theClubConfirm").is(":checked")){
		updateErrorMsgDisplay("warning_csp_msg", "<spring:message code='reg.iden.theclub.warn' />");
		errorCount++;
	}

	if(errorCount == 0){
		errorSect = "#sect_srvnum";
	}
	
	if(!$("[name='newNum']").is(":checked")){
		updateErrorMsgDisplay("warning_srvnumopt_msg", "<spring:message code="reg.iden.srvnumopt.warn" />");
		errorCount++;
	}else if( $("#new_num").is(":checked")){
		if($('[name="selectedNum"]') == "" || !$('[name="selectedNum"]').is(":checked")){
			errorCount++;
			updateErrorMsgDisplay("warning_srvnum_msg", "<spring:message code="reg.iden.srvnum.warn1" />");
		}
	}else if( $("#reuse_num").is(":checked")){
		if(!checkImportNum()){
			errorCount++;
		}
	}

	if(errorCount == 0){
		errorSect = "#sect_appt";
	}

	if($("#instdate").val() == "" || $("#apptimeSlot").val() == ""){
		errorCount++;
		updateErrorMsgDisplay("warning_appointment_msg", "<spring:message code="reg.iden.inst.warn1" />");
	}

	if(!checkAppointmentDatepick()){
		errorCount++;
	}
	
	if(errorCount == 0){
		errorSect = "#sub7";
	}
	
	if(!$("[name='weeeOptions']").is(":checked") && $("[name='weeeOptions']").is(":visible")){
		updateErrorMsgDisplay("warning_weeeOptions_msg", "<spring:message code="reg.iden.weee.warn1" />");
		errorCount++;
	}
	
	if($("#weeeNumber").is(":visible") && !checkMobile('weeeNumber')){
		errorCount++;
	}

	if($("#weeePerson").is(":visible") && !checkName("weeePerson")){
		errorCount++;
	}
	
	if($("#weeeDate").is(":visible") && ($("#weeeDate").val() == "" || $("#weeeTime").val() == "")){
		errorCount++;
		updateErrorMsgDisplay("warning_collectDate_msg", "<spring:message code="reg.iden.weee.warn2" />");
	}
	
	if(!$(".billMethod").is(":checked")){
		updateErrorMsgDisplay("warning_billMedia_msg", "<spring:message code="reg.iden.bill.warn" />");
		errorCount++;
	}
	
	//submit
	if(errorCount == 0){
		if($("#custExist").is(":checked")){
			checkProfileName();
			if(!$("#custNameMatch").is(":checked")){
				if(confirmCount < 3){
					$('html, body').animate({
				         scrollTop: $("#custLastName").offset().top
				    }, 500);
				}else{
					document.applicantInfoForm.submit();
				}
			}else{
				document.applicantInfoForm.submit();
			}
		}else{
			document.applicantInfoForm.submit();
		}
		
	}else{
		if(confirmCount == 4){
			popLiveChatNotice();
		}else{
			if(confirmCount > 4){
				hideLiveChatNotice();
			}
			if(errorSect != ""){
				$('html, body').animate({
			         scrollTop: $(errorSect).offset().top
			    }, 500);
			}
		}
		submitPressed = false;
	}
	confirmCount++;
}

function updateErrorMsgDisplay(elementId, msg){
	if(elementId == ""){
		return;
	}
	document.getElementById(elementId).innerHTML = msg;
	//if(msg != ""){
	//	errorCounting();
	//}
}

function turnOnBirther()
{
	turnOnBirth = true;
}

function checkReuseNum() {
	var isChecked = $('#reuse_num').prop('checked');
	if (isChecked) {
		$("#sub3_2").show();
		$("#sub3").hide();
		$('#num_select_cont').hide();
		$('#num_input_cont').show();
		$("#tentative_checkbox").attr("checked", "checked");
		checkName("custLastName");
		checkName("custFirstName");
		$('#instdate').removeAttr("disabled");
		updateErrorMsgDisplay('warning_srvnum_msg', "");
		updateErrorMsgDisplay('warning_srvnumopt_msg', "");
		$("#whitepage").show();
		updatePaymentRemind();
		checkEarliestSrd();
	}
} 

function checkNewNum(){
	var isChecked = $('#new_num').prop('checked');
	if (isChecked) {
		$("#sub3").show();
		$("#sub3_2").hide();
		$('#num_input_cont').hide();
		$('#num_select_cont').show();
		$("#tentative_checkbox").removeAttr("checked");
		$('#instdate').removeAttr("disabled");
		$("#importNum").val("");
		updateErrorMsgDisplay('warning_srvnum_msg', "");
		updateErrorMsgDisplay('warning_srvnumopt_msg', "");
		$("#whitepage").show();
		updatePaymentRemind();
		checkEarliestSrd();
	}
}

function removeSrvNumWarn(){
	updateErrorMsgDisplay('warning_srvnum_msg', "");
}

var isHKID = false;

$(document).ready(function() {

	$(".weeeOptions").hide();
	$("[name='weeeOptions']").each(function(index) {
		if($(this).attr("checked") == "checked"){
			$(this).click();
	}
	});
	checkReuseNum();
	checkNewNum();
	checkDocType();
	updateFullName();
	
	checkPics();
	checkCsPortalCustExist();
	
	//checkEarliestSrd();

	/*$( ".cart_icon" ).click(function() {
	$( this ).toggleClass("cart_icon_open").fadeIn(10000);
	});*/
	
	if(!$("#billLangC").prop('checked') && !$("#billLangE").prop('checked') ){
		if ('${locale}' == "en"){
			$("#billLangE").attr('checked', 'checked');
		}else if ('${locale}' == "zh"){
			$("#billLangC").attr('checked', 'checked');
		}
	}
	
	$("[name='title']").click(function(){
		updateErrorMsgDisplay("warning_title_msg", "");
	});
	
	$("[name='docType']").click(function(){
		updateErrorMsgDisplay("warning_docType_msg", "");
	});

	$("#new_num").click(function() {
		checkNewNum();
	});
	
	$("#reuse_num").click(function(){
		checkReuseNum();
	});
	
	$(".csportalPICSTitle").click(function(){
		$(".csportalPICSContent").toggle();
	});
	
	$(function() {
		$("#nextview").attr('onclick', 'formSubmit()');
		$("#middle_content").css("min-height",
				viewportHeight - 450 + "px");
		$("#middle_content").css("visibility", "visible");
	});

	//$.noConflict();
	$(function() {
		$("#Birth").datepicker({
			changeMonth : true,
			changeYear : true,
			// 	            maxDate: new Date( (today.getFullYear() - 18),  today.getMonth(), today.getDate() )
			dateFormat : 'yy/mm/dd', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "-100Y",
			maxDate : "-18Y", //Y is year, M is month, D is day 
			yearRange : "c-100:c+100", //the range shown in the year selection box, c= inpu date
			onClose : function () 
			{
			}
		});
	});
	
	$("#importNum").change(function(event){
		importNumChange();
	});

	checkPics();
	$("#csPortalConfirm").click(function(){
		//checkPics();
		updateErrorMsgDisplay("warning_csp_msg", "");
	});
});

function toUpper(id) {
	 		document.getElementById(id).value = document.getElementById(id).value.toUpperCase();
}

function trimString(x) {
	while (x.substring(0, 1) == " ") {
		templength = x.length;
		x = x.substring(1, templength);
	}

	while (x.substring(x.length - 1, x.length) == " ") {
		x = x.substring(0, x.length - 1);
	}

	x = x.replace(/[\W\s]/g, "");
	return x;
}

function trimStringForName(x) {
	while (x.substring(0, 1) == " ") {
		templength = x.length;
		x = x.substring(1, templength);
	}

	while (x.substring(x.length - 1, x.length) == " ") {
		x = x.substring(0, x.length - 1);
	}

	return x;
}

function setCustFullName(){
	var lastName = document.getElementById("custLastName").value;
	var firstName = document.getElementById("custFirstName").value;
	$("custFullName").val(lastName + " " + firstName);
}

function checkName(id) {
	var name = null;
	toUpper(id);
	name = document.getElementById(id).value;
	var patt = /[^A-z\s\056]/g;
	var warnmsgid =  "warning_"+ id +"_msg";
	
	if (name != null && name.length != 0) {
		name = trimStringForName(name);
		document.getElementById(id).value = name;
		if (patt.test(name)) 
		{
			if(id == "custLastName"){
				updateErrorMsgDisplay(warnmsgid, "<spring:message code="reg.iden.lsna.warn" />");
			}else if(id == "custFirstName"){
				updateErrorMsgDisplay(warnmsgid, "<spring:message code="reg.iden.fsna.warn" />");
			}else if(id == "weeePerson"){
				updateErrorMsgDisplay(warnmsgid, "<spring:message code="reg.iden.weee.person.warn" />");
			}
			return false;
		}
		name = updateErrorMsgDisplay(warnmsgid, "");
		setCustFullName();
		return true;
	} else { //if (submitPressed) {
		if(id == "custLastName"){
			updateErrorMsgDisplay(warnmsgid, "<spring:message code="reg.iden.lsna.warn" />");
		}else if(id == "custFirstName"){
			updateErrorMsgDisplay(warnmsgid, "<spring:message code="reg.iden.fsna.warn" />");
		}else if(id == "weeePerson"){
			updateErrorMsgDisplay(warnmsgid, "<spring:message code="reg.iden.weee.person.warn" />");
		}
	}
}

function CheckInput(ID) {
	var temp = document.getElementById(ID).value;
	
	var warn = "";
	switch (ID) {
	case "idDocType":
		warn = "<spring:message code="reg.iden.idt.warn" />";
		break;
	case "billMedia":
		warn = "<spring:message code="reg.iden.bill.warn" />";
		break;
	case "payMtdType":
		warn = "<spring:message code="reg.iden.pay.warn" />";
		break;
	case "title":
		warn = "<spring:message code="reg.iden.tit.warn" />";
		break;
	case "Birth":
		warn = "<spring:message code="reg.iden.dob.warn1" />";
		break;
	}
	
	var patt =/\d{4}\057\d{2}\057\d{2}/;
	
	if (temp == null || temp == "") {
		updateErrorMsgDisplay("warning_" + ID + "_msg", warn);
		
		//document.getElementById("warning_" + ID + "_msg").focus();
		return false;
	}
	else if ( ID == "Birth" &&  !patt.test(temp))
	{
		updateErrorMsgDisplay("warning_" + ID + "_msg", "<spring:message code="reg.iden.dob.warn2" />");
		
		return false;
	}
	else 
	{
		updateErrorMsgDisplay("warning_" + ID + "_msg", "");
		return true;
	}
}

function onFocusClear()
{
	updateErrorMsgDisplay("warning_idNo", "");
}

function toAscii(ip_char_inputValue) {
	var symbols = " !\"#$%&'()*+'-./0123456789:;<=>?@";
	var loAZ = "abcdefghijklmnopqrstuvwxyz";

	symbols += loAZ.toUpperCase();
	symbols += "[\\]^_`";
	symbols += loAZ;
	symbols += "{|}~";
	var loc;
	loc = symbols.indexOf(ip_char_inputValue);

	if (loc > -1) {
		Ascii_Decimal = 32 + loc;
		return (32 + loc);
	}
	return (0); // If not in range 32-126 return ZERO 
}

function checkPassport() {
	var checkInput = document.getElementById('docNum').value;
	updateErrorMsgDisplay("warning_idNo", "");
	if(checkInput.length < 6){
		updateErrorMsgDisplay("warning_idNo", "<spring:message code="reg.iden.idno.warn" />");
		return 0;
	}
	if (!checkSixDigit(checkInput)) {
		updateErrorMsgDisplay("warning_idNo", "<spring:message code="reg.iden.idno.six.digit.warn" />");
		return 0;
	}
	
	var found = (/(DUMMY|DUM|TEST|ILSP|BGCA|TEST|TEMP|\#|\@|\*|\?|\!)/).test(checkInput);
	if (found) {
		updateErrorMsgDisplay("warning_idNo", "<spring:message code="reg.iden.idno.warn" />");
		return 0;
	}
	return 1;
}

function checkSixDigit(input){   
    var count = 0;
    for(var i=0; i<input.length; i++){      
    	if ($.isNumeric(input.charAt(i))) {
    		count++;
    	}
    }
    return (count >=6) ;
}



function checkHkid() {
	//document.getElementById('docNum').value = document.getElementById('docNum').value.toUpperCase();
	var checkInput = document.getElementById('docNum').value;
	var patt = /\W/g;
	if ( patt.test(checkInput) )
	{
		updateErrorMsgDisplay("warning_idNo", "<spring:message code="reg.iden.idno.warn" />");
		
		return false;
	}
	if (isHKID) {
		document.getElementById('docNum').value = document
				.getElementById('docNum').value.toUpperCase();
		document.getElementById('CheckingD').value = document
				.getElementById('CheckingD').value.toUpperCase();
		var docl = trimString(document.getElementById('docNum').value);
		var cl = document.getElementById('CheckingD').value;
		var attName = docl + cl;
		var mult = new Array(9, 8, 7, 6, 5, 4, 3, 2);
		var x = 11;
		var checkdigit = -1;
		var ch;
		var endValue;
		var z = 0;
		var idlength;
		var i;
		var idx;
		var alpha_count;
		var hkid = trimString(attName);

		idlength = hkid.toString().length;

		if ( docl.toString().length != 0 && cl.toString().length != 0 ) {
			if (idlength == 8) {
				alpha_count = 1;
				idx = 1;
				z = 324;
			} else if (idlength == 9) {
				alpha_count = 2;
				idx = 0;
			} else {
				updateErrorMsgDisplay("warning_idNo", "<spring:message code="reg.iden.idno.warn" />");
				
				return false;
			}
			for (i = 0; i < alpha_count; i++) {
				ch = hkid.charAt(i);
				z = z + (toAscii(ch) - toAscii('A') + 10) * mult[idx];
				idx = idx + 1;
			}

			for (i = alpha_count; i < idlength - 1; i++) {
				ch = hkid.charAt(i);
				z = z + ch * mult[idx];

				idx = idx + 1;
			}
			endValue = z % x;
			if (endValue == 0) {
				checkdigit = 0;
			} else {
				if (x - endValue < 10) {
					checkdigit = x - endValue;
				} else if (x - endValue == 10) {
					checkdigit = "A";
				} else if (x - endValue == 11) {
					checkdigit = "B";
				} else if (x - endValue == 12) {
					checkdigit = "C";
				} else {
					checkdigit = x - endValue + toAscii('A') - 10;
				}
			}
			//hkid = hkid.substring(0,idlength-1) + '(' + hkid.charAt(idlength-1) + ')';

			document.getElementById('docNum').value = attName.substring(0,
					attName.length - 1);

			if (checkdigit == document.getElementById('CheckingD').value) {
				attName.value = hkid;
				updateErrorMsgDisplay("warning_idNo", "");
				$("#idDocNum")
						.val(
								attName.substring(0, attName.length - 1)
										+ "("
										+ document
												.getElementById('CheckingD').value
										+ ")");
				////alert(document.getElementById('idDocNum').value);
				if ( submitPressed )
				{

					return true;
				}
				else
				{
					var name1 = document.getElementById("custLastName").value;
					var name2 = document.getElementById("custFirstName").value;
					if ( name1 != null && name1.length != 0 && 
							name2 != null && name2.length != 0)
					{
						if ( checkName("custLastName") && checkName("custFirstName")){
							//validImsCustInfo();
						}
					}

					return true;
				}
			} else {
				updateErrorMsgDisplay("warning_idNo", "<spring:message code="reg.iden.idno.warn" />");
				
				return false;
			}
		} else /* if (submitPressed) */ {
			updateErrorMsgDisplay("warning_idNo", "<spring:message code="reg.iden.idno.warn" />");
			
			return false;
		}
	} else if (checkInput.length < 8 && submitPressed) {
		updateErrorMsgDisplay("warning_idNo", "<spring:message code="reg.iden.idno.warn" />");
		
		return false;
	} else {
		updateErrorMsgDisplay("warning_idNo", "");
	}

	if ( submitPressed )
	{
		return true;
	}
	else if ( checkInput != null && checkInput != "" )
	{
		var name1 = document.getElementById("custLastName").value;
		var name2 = document.getElementById("custFirstName").value;
		if ( name1 != null && name1.length != 0 && 
				name2 != null && name2.length != 0)
		{
			if ( checkName("custLastName") && checkName("custFirstName")){
				//validImsCustInfo();
			}
		}
	}
}

function checkMobile(id) {

	var msg = '';
	if(id == 'mobileNum'){
		msg = 'warning_mobile_msg';
	} else if(id == 'weeeNumber'){
		msg = 'warning_contact_mobile_msg';
	}
	
	var mob = document.getElementById(id).value;
	if (mob != null && mob != "") {
		var patt = /\D/g;

		if (patt.test(mob) || mob.length != 8) {
			updateErrorMsgDisplay(msg, "<spring:message code="reg.iden.monum.warn" />");
			
			return false;
		}
		var ch = mob.charAt(0);

		if (ch != 5 && ch != 6 && ch != 9) {
			updateErrorMsgDisplay(msg, "<spring:message code="reg.iden.monum.warn" />");
			
			return false;
		}
		updateErrorMsgDisplay(msg, "");
		return true;
	} else if (submitPressed) {
		updateErrorMsgDisplay(msg, "<spring:message code="reg.iden.monum.warn" />");
		
	}
}

function checkImportNum() {
	var importNum = document.getElementById('importNum').value;
	if (importNum != null && importNum != "") {
		var patt = /\D/g;

		if (patt.test(importNum) || importNum.length != 8) {
			updateErrorMsgDisplay('warning_srvnum_msg', "<spring:message code="reg.iden.srvnum.warn3" />");
			
			return false;
		}
		var ch = importNum.charAt(0);

		if (ch != 2 && ch !=3) {
			updateErrorMsgDisplay('warning_srvnum_msg', "<spring:message code="reg.iden.srvnum.warn3" />");
			
			return false;
		}
		updateErrorMsgDisplay('warning_srvnum_msg', "");
		return true;
	} else if (submitPressed) {
		updateErrorMsgDisplay('warning_srvnum_msg', "<spring:message code="reg.iden.srvnum.warn3" />");
		
		return false;
		//document.getElementById('mobileNum').focus();
	}
}

function validateEmail() {
	validateEmail(false);
}

function validateEmail(isSubmit) {
	var email = document.getElementById("ContactEmailAddress").value;
	if (email != null && email != "") {
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var result = re.test(email);
		if (result) {
			updateErrorMsgDisplay('warning_email_msg', "");
			if (!isSubmit) {
				checkCsPortalCustExist();
			}
			return true;
		} else {
			updateErrorMsgDisplay('warning_email_msg', "<spring:message code="reg.iden.email.warn" />");
			
			return false;
			//document.getElementById("ContactEmailAddress").focus();
		}
		return re.test(email);
	} else if (submitPressed) {
		updateErrorMsgDisplay('warning_email_msg', "<spring:message code="reg.iden.email.warn" />");
		
	}
}

function timeSlotLookup(date){
	if(date.length == 10){
		$.ajax({
			url : "ajaxappointment.html",
			type : 'POST',
			data : "date=" + date +
					"&onp=" + $("#reuse_num").is(":checked") + 
					"&type=APPT",
			dataType : 'json',
			success : function(data){
				pushTimeSlotOptions(data, "");
				return 1;
			}
			/*error : function(XMLHttpRequest, textStatus, errorThrown) {
				return textStatus;	
			},*/
		});
	}
}

function pushTimeSlotOptions(json, defaultValue){
	var slot = '#apptimeSlot';
	
	$('option', slot).remove();

	$.each(eval(json), function(i, item) {
			$(slot).append("<option value='"+item.tsvalue+"'>"+item.tsdisplay+"</option>");
			//$(slot).append(new Option(item.tsdisplay, item.tsvalue));
	});
}

function checkEarliestSrd(){
	$.ajax({
		url : "ajaxappointment.html",
		type : 'POST',
		data : "onp=" + $("#reuse_num").is(":checked") + 
				"&nameMatch=" + $("#custNameMatch").is(":checked") +
				"&type=ESRD",
		dataType : 'json',
		success : function(data){
			if(data.ealiestSrd && data.lastSrd){
				setupInstDatepicker(data.ealiestSrd, data.lastSrd);
			}else if(data.ealiestSrd){
				setupInstDatepicker(data.ealiestSrd);
			}
			return data.ealiestSrd;
		}
		/*error : function(XMLHttpRequest, textStatus, errorThrown) {
			return textStatus;	
		}, */
	});
}

function checkProfileName(){
	if($('#custFirstName').val() && $('#custLastName').val() && $("#custExist").is(":checked")){
		$.ajax({
			url : "ajaxprofilelookup.html",
			type : 'POST',
			data : "first=" + $('#custFirstName').val() + 
					"&last="+ $('#custLastName').val() + 
					"&checkOpt=NAME",
			dataType : 'json',
			//timeout : 5000,
			success : function(data){
				//var obj = jQuery.parseJSON(data);
				if(data.name_match){
						$("#custNameMatch").attr("checked", "checked");
						$("#warning_custLastName_msg").html("");
					}else{
						$("#custNameMatch").removeAttr("checked");
						$("#warning_custLastName_msg").html("<spring:message code="reg.iden.exna.warn" />");
						
					}
				liveChatUrl = data.live_chat_url;
				checkEarliestSrd();
				updatePaymentRemind();
				return 1;
			}
			/*error : function(XMLHttpRequest, textStatus, errorThrown) {
				return textStatus;	
			},
			complete : function() {
				 $.unblockUI(); 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}*/
		});
	}
};

function updatePaymentRemind(){
	/*if((!$("#custNameMatch").is(":checked") &&  $("#custExist").is(":checked") )|| $("#reuse_num").is(":checked")){
		$("#pay_remind_div").hide();
		$("#nopay_remind_div").show();
	}else{
		$("#nopay_remind_div").hide();
		$("#pay_remind_div").show();
	}*/
	$("#pay_remind_div").show();
}

function checkProfile(docNum){
	if(docNum){
		$.ajax({
			url : "ajaxprofilelookup.html",
			type : 'POST',
			data : "first=" + $('#custFirstName').val() + 
					"&last="+ $('#custLastName').val() + 
					"&docId="+ docNum + 
					"&isHKID="+ isHKID+
					"&checkOpt=DOC",
			dataType : 'json',
			async: false,
			//timeout : 5000,
			success : function(data){
				//var obj = jQuery.parseJSON(data);
				$("#wip").removeAttr("checked");
				$("#sh_cust").removeAttr("checked");
				if(data.cust_exist){
					$("#custExist").attr("checked", "checked");
					$("#showPdpoStatement").val(data.showPdpoStatement);//==PDPO==
					if(data.name_match){
						$("#custNameMatch").attr("checked", "checked");
						$("#warning_custLastName_msg").html("");
					}else{
						$("#custNameMatch").removeAttr("checked");
						$("#warning_custLastName_msg").html("<spring:message code="reg.iden.exna.warn" />");
						
					}
					if(data.wip){
						$("#wip").attr("checked", "checked");
					}
					if(data.sh_cust){
						$("#sh_cust").attr("checked", "checked");
					}
				}else{
					$("#custExist").removeAttr("checked");
					$("#custNameMatch").removeAttr("checked");
					$("#warning_custLastName_msg").html("");
				}
				updatePaymentRemind();
				liveChatUrl = data.live_chat_url;
				checkEarliestSrd();
				checkPics();
				return 1;
			}
			/*error : function(XMLHttpRequest, textStatus, errorThrown) {
				return textStatus;	
			},
			complete : function() {
				 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}*/
		});
	}
};

function docNumUpdate(){
	toUpper("docNum");
	$("#wip").removeAttr("checked");
	var checkInput = document.getElementById('docNum').value;
	document.getElementById('docNum').value = trimStringForName(checkInput);
	if(isHKID){
		if($('#docNum').val() && $('#CheckingD').val() != ''){
			$("#idDocNum").val($('#docNum').val()+$('#CheckingD').val());
			if(checkHkid()){
				checkProfile($('#idDocNum').val());
				if($("#ContactEmailAddress").val() != ''){
					validateEmail();
				}
			}
		}
	}else{
		$("#idDocNum").val($('#docNum').val());
		if(checkPassport()){
			checkProfile($('#docNum').val());
			if($("#ContactEmailAddress").val() != ''){
				validateEmail();
			}
		}
	}

	if($("#wip").is(":checked") || $("#sh_cust").is(":checked")){
		reg_open_chat(liveChatUrl,srvType);
	}
	
}

function changeDocType(){
	$("#docNum").val("");
	$("#CheckingD").val("");
	$("#idDocNum").val("");
	if($("docNum").val() != ''){
		checkDocType();
	}
}

function checkDocType(){
	if ($("#docTypeID").attr('checked')) {
		isHKID = true;
		$("#docNum").attr("maxlength", "8");
		$("#docNum").css("width", "120px");
		$("#CheckingDPart").css ( "display", "inline");
		//$("#idDocNum").val($("#CheckingD").val())
		if($("#idDocNum").val() != "" && $("#docNum").val() == ""){
			//hkid = $("#docNum").val();
			$("#docNum").val($("#idDocNum").val().substr(0, $("#idDocNum").val().length -3));
			$("#CheckingD").val($("#idDocNum").val().substr($("#idDocNum").val().length -2, 1));
		}
	}else if ($("#docTypePass").attr('checked')) {
		isHKID = false;
		$("#docNum").attr("maxlength", "15");
		$("#docNum").css("width", "180px");
		$("#CheckingDPart").css ( "display", "none");
		//$("#idDocNum").val($("#docNum").val());
		if($("#idDocNum").val() != "" && $("#docNum").val() == ""){
			$("#docNum").val($("#idDocNum").val());
		}
	}
	
}

function setupInstDatepicker(minSRD, maxSRD){
	$("#instdatepicker").datepicker("destroy");
	var beforeShowDayVar = null;
	var minDateVar = '${earliestSrd}';
	var maxDateVar = '+30D';
	var collectDate = new Date(minDateVar);
	collectDate.setDate(collectDate.getDate() + 1);
	if(minSRD){
		minDateVar = minSRD;
	}
	if(maxSRD){
		maxDateVar = maxSRD;
	}
	/*if($("#reuse_num").is(":checked")){
		beforeShowDayVar = function(date){ return [date.getDay() != 0,""];};
		minDateVar = "+30D";
	}*/
	$("#instdatepicker").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'yy/mm/dd', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : minDateVar,
		maxDate : maxDateVar,
		yearRange : "c-100:c+100", //the range shown in the year selection box, c= inpu date
		beforeShowDay: beforeShowDayVar,
		//altField : "#instdate",
		onSelect : function(){
			$("#warning_appointment_msg").html("");
			$("#instdatepicker").datepicker( "option", "altField", "#instdate" );
			var date = $("#instdate").val();
			timeSlotLookup(date);
		},
	});
	if($("#weeedatepicker").length != 0){
		$("#weeedatepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'yy/mm/dd', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : collectDate,
			yearRange : "c-100:c+100", //the range shown in the year selection box, c= inpu date
			beforeShowDay: beforeShowDayVar,
			onSelect : function(){
				$("#warning_collectDate_msg").html("");
				$("#weeedatepicker").datepicker( "option", "altField", "#weeeDate" );
			}
		});
	}
	
	checkAppointmentDatepick();
}

function checkAppointmentDatepick(){
	if($('#instdate').val() != ""){
		var appt = $('#instdatepicker');
		var minDateOpt = $(appt).datepicker("option", "minDate");
		var maxDateOpt = $(appt).datepicker("option", "maxDate");
		var minDate = $.datepicker.formatDate("yy/mm/dd", $.datepicker._determineDate($(appt).data('datepicker'), minDateOpt, new Date()));
		var maxDate = $.datepicker.formatDate("yy/mm/dd", $.datepicker._determineDate($(appt).data('datepicker'), maxDateOpt, new Date()));
		if($("#instdate").val() < minDate || $("#instdate").val() > maxDate){
			$("#warning_appointment_msg").html("<spring:message code="reg.iden.inst.warn4" />");
			
			return 0;
		}else{
			$("#warning_appointment_msg").html("");
		}
		return 1;
	}
	return 0;
}

function updateFirstName(){
	updateFullName();
	checkName("custFirstName");
	if($("#custLastName").val() != '' && $("#docNum").val() != '' &&  docNumUpdate() && $("#custExist").is(":checked")){
		checkProfileName();
	}
}

function updateLastName(){
	updateFullName();
	checkName("custLastName");
	if($("#custExist").is(":checked")  && $("#docNum").val() != '' &&  docNumUpdate()){
		checkProfileName();
	}
}

function updateFullName(){
	$("#custFullName").html($("#custLastName").val() + " " + $("#custFirstName").val());
}

function importNumChange(){
	checkName("custFirstName");
	checkName("custLastName");
	checkImportNum();
}

function checkPics(){
	//if(checkCspPics() | checkPrivacyPics()){
	if(checkPrivacyPics()){
		$("#privacyBlock").show();
	}else{
		$("#privacyBlock").hide();
	}
	$(".pics").scrollTop(0);
}

function checkCspPics(){
	if($("#csPortalConfirm").is(":checked")){
		$("#pics_csportal").show();
		return true;
	}else{
		$("#pics_csportal").hide();
		return false;
	}
}

function checkPrivacyPics(){
	if($("#custExist").is(":checked") && $("#showPdpoStatement").val() == 'false'){ //==PDPO==
		$("#pics_privacy").hide();
		$("#pics_privacy_checkbox_span").hide();
		$("#pics_privacy_checkbox").removeAttr("checked");
		return false;
	}else{
		$("#pics_privacy").show();
		$("#pics_privacy_checkbox_span").show();
		return true;
	}
}

function clearCsPortalTheClub(){
	$(".csPortalConfirmRow").fadeOut();
	$(".csPortalTheClubConfirmRow").fadeOut();
	$(".theClubCsPortalRemindRow").fadeOut();
	$(".theClubConfirmRow").fadeOut();
	$("#csPortalConfirm").removeAttr("checked");
	$("#csPortalTheClubConfirm").removeAttr("checked");
	$("#theClubConfirm").removeAttr("checked");
	updateErrorMsgDisplay("warning_csp_msg", "");
	updateErrorMsgDisplay('warning_email_msg', '');
	$("#csDocError").removeAttr("checked");
	$("#csEmailError").val("");
	$("#pics_csportal").hide();
	
	$(".csPortalTheClubConfirmRow").hide();
	$(".csPortalConfirmRow").hide();
	$(".theClubConfirmRow").hide();
	csPortalExist = false;
	csPortalTheClubExist = false;
	theClubExist = false;
	regCsPortalTheClub = false;
	regTheClub = false;
	regCsPortal = false;
}

function checkCsPortalCustExist(){
	var email = $("#ContactEmailAddress").val();
	var docType = $("[name = 'docType']").is(":checked")?(isHKID?"HKID":"PASS"):'';
	var docNum = $("#idDocNum").val();
	if(email != '' && docType != '' && docNum != ''){
		$.ajax({
			url : "ajaxcsportal.html",
			type : 'POST',
			data : "docType=" + docType + 
					"&docNum="+ docNum + 
					"&email="+ email,
			dataType : 'json',
			async: false,
			//timeout : 5000,
			success : function(data){
				//if(data.sys_error){
				//	top.location="message.html";
				//}else 

				clearCsPortalTheClub();
				var isDocValid = data.isDocValid;
				var isCustAldyMyHkt = data.isCustAldyMyHkt;
				var isCustAldyTheClub = data.isCustAldyTheClub;
				var isClubLiInUse = data.isClubLiInUse;
				var isHktLiInUse = data.isHktLiInUse;
				var isLiInUse = data.isLiInUse;
		
				if(!isDocValid){
					reg_open_chat(liveChatUrl,srvType);
					$("#csDocError").attr("checked", "checked");
					checkPics();
					return 1;
				}
				
				// Going to register both MyHKT and The Club
				if (!isCustAldyMyHkt && !isCustAldyTheClub) {
					
					if (isLiInUse) {
						$("#csEmailError").val('<spring:message code="reg.iden.email.csportal.theclub.warn" />');
					}
					else {
						if(!$(".csPortalTheClubConfirmRow").is(":visible")) {
							regCsPortalTheClub = true;
							$(".theClubCsPortalRemindRow").fadeIn();
							$(".csPortalTheClubConfirmRow").fadeIn();
							$("#csPortalTheClubConfirm").attr("checked", "checked");
							$("#csPortalTheClubOptIn").attr("checked", "checked");
							//$("#pics_csportal").show();
						}
					}
				}
				// Going to register The Club
				else if (isCustAldyMyHkt && !isCustAldyTheClub) {
					if (isClubLiInUse) {
						$("#csEmailError").val('<spring:message code="reg.iden.email.theclub.warn" />');
					}
					else {
						if(!$(".theClubConfirmRow").is(":visible")){
							regTheClub = true;
							$(".theClubCsPortalRemindRow").fadeIn();
							$(".theClubConfirmRow").fadeIn();
							$("#theClubConfirm").attr("checked", "checked");
							$("#theClubOptIn").attr("checked", "checked");
							//$("#pics_csportal").show();
						}
					}
				}
				// Going to register MyHKT
				else if (isCustAldyTheClub && !isCustAldyMyHkt) {
					if (isHktLiInUse) {
						$("#csEmailError").val('<spring:message code="reg.iden.email.csportal.warn" />');
					}
					else {
						if(!$(".csPortalConfirmRow").is(":visible")){
							regCsPortal = true;
							$(".theClubCsPortalRemindRow").fadeIn();
							$(".csPortalConfirmRow").fadeIn();
							$("#csPortalConfirm").attr("checked", "checked");
							$("#csPortalOptIn").attr("checked", "checked");
							//$("#pics_csportal").show();
						}
					}
				}

				
//				if(!isError && (!data.isCsPortalCusExist || data.sys_error)) {
//					if(!$(".csPortalConfirmRow").is(":visible")){
//						$(".csPortalConfirmRow").fadeIn();
//						$("#csPortalConfirm").attr("checked", "checked");
//						csPortalExist = false;
//						$("#pics_csportal").show();
///					}
//				}else{
//					$(".csPortalConfirmRow").fadeOut();
//					$("#csPortalConfirm").removeAttr("checked");
//					updateErrorMsgDisplay("warning_csp_msg", "");
//					csPortalExist = true;
//					$("#pics_csportal").hide();
//				}
				
				updateErrorMsgDisplay('warning_email_msg', $("#csEmailError").val());
				checkPics();
				return 1;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// top.location="message.html";
				$(".csPortalTheClubConfirmRow").fadeIn();
				$("#csPortalTheClubConfirm").attr("checked", "checked");
				regCsPortalTheClub = true;
				$("#pics_csportal").show();
				checkPics();
			}
		});
	}
}

function copyContactName(){
	if(confirm("<spring:message code="reg.iden.weee.confirm" />")){
		var fullname = $("#custLastName").val() + " " + $("#custFirstName").val();
		$("#weeePerson").val(fullname);
		$("#weeeNumber").val($("#mobileNum").val());
		checkName('weeePerson');
		checkMobile('weeeNumber');
	}
}

function showOptionChild(id){
	$(".weeeOptions").hide('slow');
	$("#"+id).show('slow');
}

function addEpdDatePicker(id, minDateRefId, minDateLeadDay){
	$('#'+id).datepicker( "destroy");
	
	var minDate = $("#"+minDateRefId).val();
	var maxDate = "+100Y";
	
	if(minDateLeadDay != ''){
		plusDate(minDate, minDateLeadDay);
	}
	
	$('#'+id).datepicker({
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate: minDate, maxDate: maxDate, //Y is year, M is month, D is day  
		yearRange: "0:+100" //the range shown in the year selection box
	});
}

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
</head>
<body>
<form:form id="applicantInfoForm" name="applicantInfoForm" method="POST" commandName="applicantInfoForm" action="applicantinfo.html">
<div style="display:none">
	<form:checkbox id="custExist" path="custExist"/>	
	<form:checkbox id="custNameMatch" path="custNameMatch"/>
	<form:checkbox id="wip" path="wip"/>
	<form:checkbox id="sh_cust" path="shCust"/>
	<input type="checkbox" id="csDocError" />
	<input id="csEmailError" value=""/>
	<form:hidden id="showPdpoStatement" path="showPdpoStatement"/> <!--PDPO-->
</div>

<!--wrapper-->
<div id="wrapper">
<!--content-->
<div id="content">
<!--flow nav-->
<div class="flow">
<a href="javascript:toggleCart();"><span class="cart_icon"></span></a>
<ul>
<li class="flow_active"><spring:message code="reg.title.iden"/></li>
<li class="flowsep"></li>
<li class="flow_inactive"><spring:message code="reg.title.confirm"/></li>
</ul>
</div>
<!-- end of flow nav-->

<!--vas main-->
<div id="frame_main">

<!--vas-->
<div id="middle_content" style="visibility: visible;padding:0px 10px 10px 10px;">
			<span class="topic"><spring:message code="reg.iden.subtitle1" /></span>
			<table cellpadding="3" cellspacing="0" id="IDContent">
				<tbody><tr>
					<td width=200><spring:message code="reg.iden.tit" /></td>
					<td width=220 colspan="2">
						<label for="title1"><form:radiobutton id="title1" path="title" value="Mr" />
						<spring:message code="reg.iden.tit.1" /></label>&nbsp;&nbsp; 
						<label for="title2"><form:radiobutton id="title2" path="title" value="Ms" />
						<spring:message code="reg.iden.tit.2" /></label>
						<input type="hidden" id="title" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="warning_class">
							<span id="warning_title_msg" style="color: #FF0000"></span>
						</div></td>
				</tr>
				<tr>
					<td><spring:message code="reg.iden.lsna" /></td>
					<td width=200><form:input id="custLastName" cssClass="inputfield"
							path="familyName" maxlength="40" disabled="false"
							onkeyup="toUpper(this.id)" onchange="updateLastName()" />&nbsp;</td>
					<td><span><spring:message code="reg.iden.lsna.remind" /></span>
					</td>

				</tr>
				<tr>
					<td colspan="3">
						<div class="warning_class">
							<span id="warning_custLastName_msg" style="color: #FF0000"></span>
						</div></td>
				</tr>
				<tr>
				</tr><tr>
					<td><spring:message code="reg.iden.fsna" /></td>
					<td colspan="2"><form:input id="custFirstName" cssClass="inputfield"
							path="givenName" maxlength="40"	disabled="false" 
							onkeyup="toUpper(this.id)" onchange="updateFirstName()" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="warning_class">
							<span id="warning_custFirstName_msg" style="color: #FF0000"></span>
						</div></td>
				</tr>
				<tr>
				</tr><tr>
					<td><spring:message code="reg.iden.idt" /></td>
					<td colspan="2">
						<label for="docTypeID"><form:radiobutton id="docTypeID" path="docType" value="HKID" onchange="changeDocType()" />
						<spring:message code="reg.iden.idt.1" /></label>&nbsp;&nbsp; 
						<label for="docTypePass"><form:radiobutton id="docTypePass" path="docType" value="PASS" onchange="changeDocType()" />
						<spring:message code="reg.iden.idt.2" /> </label>
					</td>
				</tr>
				<tr>
					<td colspan="3"><span id="warning_docType_msg" style="color: #FF0000"></span></td>
				</tr>
				<tr>
					<td><spring:message code="reg.iden.idno" /></td>
					<td width=200>
						<input id="docNum" maxlength=15 class="inputfield" onchange="docNumUpdate()" style="display: inline;"/> &nbsp;
						<span id="CheckingDPart" style="display: none;">
							( <input id="CheckingD" style="width: 12px;" maxlength=1
								onchange="docNumUpdate()" /> )
						</span> 
						<form:hidden id="idDocNum" path="docNum" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="warning_class">
							<span id="warning_idNo" style="color: #FF0000"> </span>
						</div></td>
				</tr>
				<tr>
					<td><spring:message code="reg.iden.dob" /></td>
					<td width=200><form:input id="Birth" path="dateOfBirth" cssClass="inputfield"
						onchange='CheckInput("Birth")'
						onfocus='turnOnBirther()' autocomplete="off"/>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="warning_class">
							<span id="warning_Birth_msg" style="color: #FF0000"> </span>
						</div></td>
				</tr>
				<tr>
					<td><spring:message code="reg.iden.email" /></td>
					<td width=200><form:input id="ContactEmailAddress" cssClass="inputfield"
							path="contactEmailAddr" maxlength="64"
							onchange="validateEmail()" />&nbsp;</td>
					<td><span><spring:message code="reg.iden.email.remind" /></span>
					</td>
				</tr>
				<tr>
					<td colspan="3"><span id="warning_email_msg" style="color: #FF0000"></span></td>
				</tr>
				<tr>
					<td><spring:message code="reg.iden.monum" /></td>
					<td width=200><form:input id="mobileNum" cssClass="inputfield"
							path="contactMobileNum" maxlength="8"
							onblur="checkMobile(this.id)" />&nbsp;</td>
					<td><span><spring:message code="reg.iden.monum.remind" /></span>
					</td>
				</tr>
				<tr>
					<td colspan="3"><span id="warning_mobile_msg" style="color: #FF0000"></span></td>
				</tr>
				<tr class="theClubCsPortalRemindRow" style="display:none;">
					<td colspan=3 class="RemindStr" ><div style=""><spring:message code="reg.iden.theclub.remind" /></div></td>
				</tr>
				<tr class="theClubCsPortalRemindRow" style="display:none;">
					<td colspan=3 class="RemindStr" ><div style=""><spring:message code="reg.iden.csportal.remind" /></div></td>
				</tr>
				<tr class="csPortalConfirmRow" style="display:none; padding-left: 35px; vertical-align: top;">
					<td><p><form:checkbox id="csPortalConfirm" path="csPortalConfirm"/></p></td>
					<td colspan=2>
						<p style="padding-left: 35px;padding-right: 35px;margin-left:-212px; ">
							<spring:message code="reg.iden.csportal" />
						</p>
					</td>
				</tr>
				<tr class="csPortalConfirmRow" style="display:none; padding-left: 35px; vertical-align: top;">
					<td><p>&nbsp;</p></td>
					<td colspan=2>
						<span class="csportalPICSTitle" style="padding-left: 35px;padding-right: 35px;margin-left:-212px;"><b><u><spring:message code="reg.iden.csportal.title" /></u></b></span>
					</td>
				</tr>
				<tr class="csPortalConfirmRow" style="display:none; padding-left: 35px; vertical-align: top;">
					<td><p><form:checkbox id="csPortalOptIn" path="csPortalOptIn"/></p></td>
					<td colspan=2>
						<!--  <div class="csportalPICSContent" style="display:none">-->
						<div class="csportalPICSContent">
							<p style="padding-left: 35px;padding-right: 35px;margin-left:-212px; ">
								<spring:message code="reg.iden.csportal.agree" />
							</p>
						</div>
					</td>
				</tr>
				<tr class="csPortalTheClubConfirmRow" style="display:none; padding-left: 35px; vertical-align: top;">
					<td><p><form:checkbox id="csPortalTheClubConfirm" path="csPortalTheClubConfirm"/></p></td>
					<td colspan=2>
						<p style="padding-left: 35px;padding-right: 35px;margin-left:-212px; ">
							<spring:message code="reg.iden.csportal.theclub" />
						</p>
					</td>
				</tr>
				<tr class="csPortalTheClubConfirmRow" style="display:none; padding-left: 35px; vertical-align: top;">
					<td><p>&nbsp;</p></td>
					<td colspan=2>
						<span class="csportalPICSTitle" style="padding-left: 35px;padding-right: 35px;margin-left:-212px;"><b><u><spring:message code="reg.iden.csportal.title" /></u></b></span>						
					</td>
				</tr>
				<tr class="csPortalTheClubConfirmRow" style="display:none; padding-left: 35px; vertical-align: top;">
					<td><p><form:checkbox id="csPortalTheClubOptIn" path="csPortalTheClubOptIn"/></p></td>
					<td colspan=2>
						<!--  <div class="csportalPICSContent" style="display:none">-->
						<div class="csportalPICSContent">
							<p style="padding-left: 35px;padding-right: 35px;margin-left:-212px; ">
								<spring:message code="reg.iden.csportal.theclub.agree" />
							</p>
						</div>
					</td>
				</tr>
				<tr class="theClubConfirmRow" style="display:none; padding-left: 35px; vertical-align: top;">
					<td><p><form:checkbox id="theClubConfirm" path="theClubConfirm"/></p></td>
					<td colspan=2>
						<p style="padding-left: 35px;padding-right: 35px;margin-left:-212px; ">
							<spring:message code="reg.iden.theclub" />
						</p>
					</td>
				</tr>
				<tr class="theClubConfirmRow" style="display:none; padding-left: 35px; vertical-align: top;">
					<td><p>&nbsp;</p></td>
					<td colspan=2>
						<span class="csportalPICSTitle" style="padding-left: 35px;padding-right: 35px;margin-left:-212px;"><b><u><spring:message code="reg.iden.csportal.title" /></u></b></span>
					</td>
				</tr>
				<tr class="theClubConfirmRow" style="display:none; padding-left: 35px; vertical-align: top;">
					<td><p><form:checkbox id="theClubOptIn" path="theClubOptIn"/></p></td>
					<td colspan=2>
						<!--  <div class="csportalPICSContent" style="display:none">-->
						<div class="csportalPICSContent">
							<p style="padding-left: 35px;padding-right: 35px;margin-left:-212px; ">
								<spring:message code="reg.iden.theclub.agree" />
							</p>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="3"><span id="warning_csp_msg" style="color: #FF0000"></span></td>
				</tr>
			</tbody></table>
			<br />
			<hr />
			
		<span id="sect_srvnum" class="deep15px"><spring:message code="reg.iden.subtitle2" /></span>
			<table width="800">
				<tbody>
				<tr>
					<td>
						<form:radiobutton id="new_num" path="newNum" value="true" />
						<label for="new_num" ><spring:message code="reg.iden.srvnum.1" /></label>
					</td>
					<td>
						<!--
						<form:radiobutton id="reuse_num" path="newNum" value="false" />
						<label for="reuse_num"><spring:message code="reg.iden.srvnum.2" /> <span class="nameof">(<spring:message code="reg.iden.srvnum.2.sub" /><span id="custFullName"></span><spring:message code="reg.iden.srvnum.2.sub2" />)</span></label>
						-->
					</td>
				</tr>
				<tr>
					<td colspan="2"><span id="warning_srvnumopt_msg" style="color: #FF0000"></span></td>
				</tr>
				<tr>
				<td colspan=2>
						<div id='num_select_cont'>
						<spring:message code="reg.iden.srvnum.1.desc" />
							<br/><br/>
							<c:forEach var="srvnum" items="${applicantInfoForm.displayNewNum}" varStatus="status">
									<label onclick="removeSrvNumWarn()" for="${srvnum}">
										<span style="padding:3px"><form:radiobutton id="${srvnum}" path="selectedNum" value="${srvnum}" />
										${fn:substring(srvnum, 4, 12)}
										</span>
									</label>
									<c:if test="${(status.index+1)%6 == 0}">
									<br/>
									</c:if>							
							</c:forEach>
						</div>
						
						<div id='num_input_cont'>
							<spring:message code="reg.iden.srvnum.2.desc" />
							<form:input id="importNum" path="importNum" maxlength="8"/><br/>
						</div>
                        <div id="whitepage" style="display:none; padding-left: 35px;">
                        	<form:checkbox path="exDirectoryConfirm" />&nbsp;<spring:message code="reg.iden.whitepage.agree"></spring:message>
                        </div>
				  </td>
				</tr>
				<tr>
					<td colspan="2"><span id="warning_srvnum_msg" style="color: #FF0000"></span></td>
				</tr>
			</tbody></table>
			<br />
			<hr />
		<span id="sect_appt"></span>
		<span class="deep15px" id="sub3"><spring:message code="reg.iden.subtitle3" /></span>
		<span class="deep15px" id="sub3_2" style="display: none;"><spring:message code="reg.iden.subtitle3.2" /></span>
		<span style="display: none;"><form:checkbox id="tentative_checkbox" path="tentative" /></span>
            <table width=850>
				<tr id="targetdatecols">
					<td width="20%"><spring:message code="reg.iden.inst.date" /></td>
					<td width="30%">
						<form:input id="instdate" path="installDate" autocomplete="off" readonly="true" cssStyle="cursor:default"/>
						<input id="instdatemapping" type="hidden" />
					</td>
					<td width="20%"><spring:message code="reg.iden.inst.time" /></td>
					<td width="30%">
						<form:select id="apptimeSlot" path="installTimeAndType" cssStyle="width:100px">
							<form:option value=""></form:option>
							<!--<form:options itemValue="apptTimeSlot" itemLabel="apptTimeSlot" items="${timeSlots}" />-->
							<c:forEach var="slot" items="${timeSlots}">
								<form:option value="${slot.apptTimeSlot}||${slot.apptTimeSlotType}" label="${slot.apptTimeSlot}" />
							</c:forEach>
							<!--<form:options items="${timeSlots}" />-->
						</form:select> 
						<!--<form:hidden id="apptimeSlotDisplay"	path="installTime" />-->
						
						<div style="display: none;">
							<form:select id="slotType" path="">
								<form:option value="" />
								<form:options items="${timeSlots}" />
							</form:select>
						</div>
					</td>
				</tr>
				<tr id="timeslotcols">
					<td></td>
					<td><div id="instdatepicker"></div></td>
				</tr>
				<tr>
					<td colspan=4><span id="warning_appointment_msg"
						style="color: #FF0000"></span></td>
				</tr>
				<tr>
					<td style="padding-top: 10px" colspan=4><br/><spring:message code="reg.iden.inst.warn5" /></td>
				</tr>
			</table>
            
			<br />
			<hr />
	<c:if test="${not empty applicantInfoForm.epdItemList}">
		<span class="deep15px" id="sub7"><spring:message code="reg.iden.subtitle7" /></span>
            <table width=850>
  				<c:forEach var="epdItem" items="${applicantInfoForm.epdItemList}" varStatus="status">
            	<tr id="${epdItem.itemId}">
            		<td colspan=4>
                   		<label><form:radiobutton path="weeeOptions" onclick="showOptionChild('weeeOptionChild${epdItem.itemId}');" value="${epdItem.itemDesc}" />&nbsp;${epdItem.itemDesc}</label>
                    	<br/>
                    </td>
            	</tr>
				<tr>
					<td colspan="2" style="padding-bottom:5px;"></td>
				</tr>
            	<tbody id="weeeOptionChild${epdItem.itemId}" class="weeeOptions">
	  				<c:forEach var="itemAttb" items="${epdItem.itemAttbs}" varStatus="attbStatus">
					<c:if test="${itemAttb.visualInd != 'N'}">
  						<tr>
							<td colspan="2" style="padding-bottom:5px;"></td>
						</tr>
						<c:if test="${itemAttb.inputMethod == 'INPUT'}">
							<c:choose>
							<c:when test="${itemAttb.inputFormat == 'CONTACT_NAME'}">
								<tr>
									<td width="20%" style="padding-left: 35px;">${itemAttb.attbDesc}:</td>
									<td width="30%">
										<form:input id="weeePerson" cssClass="inputfield" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" maxlength="30" onkeyup="toUpper(this.id)" onchange="checkName(this.id)" autocomplete="off"/>
									</td>
									<td>
										<span id="weeeCopyBtn" onclick="copyContactName();"><spring:message code="bottom.button.copy" /></span>
									</td>
								</tr>
								<tr>
	 								<td colspan="2" style="padding-left: 35px; padding-bottom:5px;"><span id="warning_weeePerson_msg" style="color: #FF0000"></span></td> 
								</tr>
							</c:when>		
							<c:when test="${itemAttb.inputFormat == 'CONTACT_NUM'}">
								<tr>
									<td width="20%"  style="padding-left: 35px;">${itemAttb.attbDesc}:</td>
									<td width="30%">
										<form:input id="weeeNumber" cssClass="inputfield" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" maxlength="8" onblur="checkMobile(this.id)" autocomplete="off"/>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="padding-left: 35px;"><span id="warning_contact_mobile_msg" style="color: #FF0000"></span></td>
								</tr>
							</c:when>		
							<c:when test="${itemAttb.inputFormat == 'DATE'}">
								<tr>
									<td width="20%"  style="padding-left: 35px;">${itemAttb.attbDesc}:</td>
									<td width="30%">
										<form:input id="weeeDate" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" autocomplete="off" readonly="true" cssStyle="cursor:default" cssClass="inputfield"/>
										<input id="weeedatemapping" type="hidden" />
									</td>
								</tr>
								<tr>
									<td></td>
									<td><div id="weeedatepicker"></div></td>
								</tr>
								<tr> 
									<td colspan=4 style="padding-left: 35px;"><span id="warning_collectDate_msg"
										style="color: #FF0000"></span></td>
								</tr>
							</c:when>		
							<c:otherwise>
								<tr>
									<td width="20%" style="padding-left: 35px;">${itemAttb.attbDesc}:</td>
									<td width="30%">
										<form:input cssClass="inputfield" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" maxlength="30" onkeyup="toUpper(this.id)"/>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="padding-left: 35px;"><span id="warning_${itemAttb.inputFormat}_msg" style="color: #FF0000"></span></td>
								</tr>
							</c:otherwise>	
							</c:choose>
						</c:if>
						<c:if test="${itemAttb.inputMethod == 'SELECT'}">
						<tr>
							<td width="20%" style="padding-left: 35px;">${itemAttb.attbDesc}:</td>
							<td width="30%">
								<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
									<form:select path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
										<form:option value="" label="" />
										<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
											<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
										</c:forEach>
									</form:select>
								</c:if>
							</td>
							</tr>
						</c:if>
					</c:if>
					</c:forEach>
				</tbody>
				<tr>
					<td colspan="2" style="padding-bottom:5px;"></td>
				</tr>
				</c:forEach>
				<tr>
					<td colspan=4><span id="warning_weeeOptions_msg"
						style="color: #FF0000"></span></td>
				</tr>
				<tr>
					<td style="padding-top: 10px" colspan=4><spring:message code="reg.iden.weee.remind" /></td>
				</tr>
				<tr>
					<td colspan="2" style="padding-bottom:5px;"></td>
				</tr>
			</table>
			<br />
			<hr />
	</c:if>
		<span class="deep15px"><spring:message code="reg.iden.subtitle4" /></span>

			<table>
				<tr>
					<!--  <td><form:radiobutton id="billMethod" path="billMethod" value="E"/>
					</td>
					<td>
						<label for="billMethod">
						<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='EYE'}">
							<spring:message code="reg.iden.bill.eye" />
						</c:if>
						<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='DEL'}">
							<spring:message code="reg.iden.bill.del" />
						</c:if>
						</label>
					</td>-->
					
					<c:forEach var="billMethod" items="${applicantInfoForm.billMethodItemList}" varStatus="status">
					<td>
						<form:radiobutton id="${billMethod.itemId}" path="billMethodItemList[${status.index}].selected" cssClass="billMethod" value="true"/>
					</td>
					<td>
						<label for="${billMethod.itemId}">
							${billMethod.itemDisplayHtml}
						</label>
					</td>
					</c:forEach>
				</tr>
				<tr>
					<td colspan=2><span id="warning_billMedia_msg"
						style="color: #FF0000"></span></td>
				</tr>

			</table>
			
		<span class="deep15px"><spring:message code="reg.iden.subtitle5" /></span>
            <table>
            <tbody><tr>
					<td><form:radiobutton id="billLangC" path="billLang" value="C" /></td>
					<td><label for="billLangC"><spring:message code="reg.iden.bill.lang.chi" /></label></td>
					<td><form:radiobutton id="billLangE" path="billLang" value="E" /></td>
					<td><label for="billLangE"><spring:message code="reg.iden.bill.lang.eng" /></label></td>
				</tr>
				<tr>
					<td colspan=2><span id="warning_billLang_msg"
						style="color: #FF0000"></span></td>
				</tr>
            </tbody></table>
			<br />
			<hr />
		<span class="deep15px"><spring:message code="reg.iden.subtitle6" /></span>
			<table>
				<tbody>
                <tr><td></td><td class="highlight"><b><spring:message code="reg.iden.pay.cc" /></b></td></tr>
                <tr><td></td><td><span style="display: inline; color: rgb(119, 119, 119);"><spring:message code="reg.iden.pay.remind1" /></span><br /><br /></td></tr>
                <tr>
					<td></td><td><span id="CreditCard_Warning_prototype" style="display: inline; color: rgb(119, 119, 119); font-weight: bold;"><spring:message code="reg.iden.pay.remind2" /><br /><br />
					</span>
                    </td>
				</tr>
                <!--<tr>
					<td style="vertical-align: top;"></td>
					<td>
					
					<div id="calculated_prepay_div">
						<div style="display:none" id="pay_remind_div">
							<c:if test="${not empty applicantInfoForm.prepayItemList}">
								<c:forEach var="prepayItem" items="${applicantInfoForm.prepayItemList}" varStatus="status">
									<span style="display: inline; color: rgb(119, 119, 119);">
										${prepayItem.itemDisplayHtml};
									</span>
								</c:forEach>
							</c:if>
							<c:if test="${empty applicantInfoForm.prepayItemList}">
								<span id="CreditCard_Warning" style="display: inline; color: rgb(119, 119, 119);"><spring:message code="reg.iden.pay.remind3" /></span> 
		                        <span id="FirstMonthCharge" style="display: inline;" class="green_strong">$${applicantInfoForm.payAmount}</span>
								<span id="CreditCard_Warning2" style="display: inline; color: rgb(119, 119, 119);"> <spring:message code="reg.iden.pay.remind4" /></span>
							</c:if>
						</div> 
						<div style="display:none" id="nopay_remind_div">
							<span style="display: inline; color: rgb(119, 119, 119);"><spring:message code="reg.iden.pay.ca.remind1" /></span>
						</div>
					</div>
                    </td>
				</tr>-->
				<tr>
					<td colspan="2"><span id="warning_payMtdType_msg" style="color: #FF0000"></span></td>
				</tr>
			</tbody></table>
			<p>
				<!--<spring:message code="reg.iden.pay.remind5" />-->
			</p>
			<p>
				<span id="warning_general_msg" style="color: #FF0000"></span>
			</p>
			<br />
			<div id="privacyBlock">
				<hr />
				<span class="deep15px"><spring:message code="reg.iden.privacy.title" /></span>
				
				<p class="pics">
					<span id="pics_privacy">
						<b><u><spring:message code="reg.iden.privacy.srv.title" /></u></b><br/>
						<spring:message code="reg.iden.privacy.srv.c" />
					</span>
					<span id="pics_csportal">
						<b><u><spring:message code="reg.iden.privacy.cs.title" /></u></b><br/>
						<spring:message code="reg.iden.privacy.cs.c" />
					</span>
				</p>
				<span id="pics_privacy_checkbox_span" style="padding-left:20px">
					<form:checkbox id="pics_privacy_checkbox" path="privacyR"/>&nbsp;<spring:message code="reg.iden.privacy.agree" />
				</span>
	  		</div>
<!--end of vas-->


</div>
<!--end of vas main-->
<div class="clearboth"></div>
</div>
<!--end of content-->
<div id="floating_bar" style="margin-left: 0px;">
				<div class="grad"></div>
					<div id="bottom_content">
                     <table width="100%" style="bottom: -8pt;position: relative;">  
						<tbody>
							<tr align="center"> 
								<td width="60%">
								<img id="nextview" src="./images/${lang}/next_btn.png" 
									onmouseover="this.src='./images/${lang}/next_btn_mo.png'" 
									onmouseout="this.src='./images/${lang}/next_btn.png'" 
									style="margin-left:0pt;cursor: pointer;"/>
								</td>
							</tr>
						</tbody>
                    </table>
					</div>  
					<img src="images/${lang}/bottom_bar.png" />
</div>
</div>
<!--end of wrapper-->
</div>
</form:form>

<!--cart area-->
<iframe id="cartarea" src="">
</iframe>
<!--end of cart area-->

</body>
</html>
