var ltsterminateappointment = function() {
	function pushTimeSlotOptions(json, lkupType, defaultValue){
		var slot = '';
		var type = '';
		if(lkupType == "D"){
			slot = '#apptTime';
			type = '#apptType';
		}
		$('option', slot).remove();
		$('option', type).remove();
		$(slot).append(new Option(' -- SELECT --', '', true, false));
		$(type).append(new Option('', '', true, false));

		$.each(eval(json), function(i, item) {
			var timeslotoptions = $(slot).attr('options');
			var timetypeoptions = $(type).attr('options');
			if(item.tsvalue == defaultValue){
				timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, true);
			}else{
				timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
			}
			timetypeoptions[timetypeoptions.length] = new Option(item.tstype, item.tstype, true, false);
		});

		colorTimeSlot(lkupType);
		if(lkupType = "D"){
			refreshTimeSlotType();
		}
	}
	function refreshTimeSlot(changedSlot){
		var successInd = "";
		var instDate = $('#apptDate').val();
		var instTime = $('#apptTime').val();
		timeSlotLookup(instDate, "I", instTime);
		refreshTimeSlotType();
		if(successInd=='parsererror'){
			alert("Your session has been timed out, please re-login.");	
		}
	}
	function refreshTimeSlotType(){
		var sharePCD = ${showPcdOrderId}; //$("#sharePCDInd").val();
		$("#apptType").get(0).selectedIndex = $("#apptTime").get(0).selectedIndex;
	}
	function colorTimeSlot(lkupType){
		var slot = '';
		var type = '';
		if(lkupType == "D"){
			slot = 'apptTime';
			type = 'apptType';
		}
		var timeslotlist=document.getElementById(slot);
		var slottypelist=document.getElementById(type);
		var i;
		for (i=1;i<timeslotlist.length;i++){//skip first element			
			/*if(document.getElementById("instdate").value==instdate &&
					timeslotlist.options[i].value==insttime){
				slottypelist.options[i].value="CURRENT_SELECTED";
				timeslotlist.options[i].selected=true;
				slottypelist.options[i].selected=true;
			}else */
			if(slottypelist.options[i].value=="NS"){ //indicate no resource
				timeslotlist.options[i].style.color="red";
			}else if(slottypelist.options[i].value=="RS"){//restricted
				timeslotlist.options[i].style.color="silver";
			}else{
				timeslotlist.options[i].style.color="black";
			}
		}
	}
	function resetFormDisplay(){
		var allowAppt = true;
		var tentativeInd = "${ltsterminateappointmentCmd.tentativeInd}";
		var confirmedInd = "${ltsterminateappointmentCmd.confirmedInd}";
		$("#preBookSerialNum").attr('disabled', false);
		$("#apptDate").attr('disabled', false);
		$("#apptTime").attr('disabled', false);
		$("#backApptDate").attr('disabled', false);
		$("#ceaseRentalDate").attr('disabled', false);
		$("#srDate").attr('disabled', false);
		console.log("before");
		if(tentativeInd == "true"){
			$("#preBookSerialNum").attr('disabled', true);
			$("#apptDate").attr('disabled', true);
			$("#apptTime").attr('disabled', true);
			$("#backApptDate").attr('disabled', true);
			$("#ceaseRentalDate").attr('disabled', true);
			$("#srDate").attr('disabled', true);
		}
		if(!allowAppt){
			console.log("!allowAppt");
			$("#preBookSerialNum").attr('disabled', true);
			$("#apptDate").attr('disabled', true);
			$("#apptTime").attr('disabled', true);
			if($("#allowCustConfirmInd").val() == "true"){
				$("div#pcdOrderConfirmDiv").show();
				$("#confirmCust").attr('disabled', false);
			}
			else{
				$("#confirmCust").attr('disabled', true);
			}
			if($("#allowIaConfirmInd").val() == "true"){
				$("div#pcdOrderConfirmDiv").show();
				$("#confirmIa").attr('disabled', false);
			}
			else{
				$("#confirmIa").attr('disabled', true);
			}
			if($("#allowCustConfirmInd").val() == "false"
					&& $("#allowIaConfirmInd").val() == "false"){
				$("div#pcdOrderConfirmDiv").hide();
			}
		}
		else{
			resetBackApptDatePicker("", "");
			resetCeaseRentalDatePicker("", "");
			resetApptDatePicker("", "");
			if($("#apptTime").val() == "" && $("#apptDate").val() != ""
				&& tentativeInd == "false" && confirmedInd == "false"){
				timeSlotLookup($("#apptDate").val(), "D");
			}
		}
	}
	function confirmDisplay(val){
		if(val == "confirm"){
			$('#confirmMsgBlock').show();
			$('#cancelMsgBlock').hide();
			$('#confirmAppntBtn').attr('disabled', true);
			$('#cancelAppntBtn').attr('disabled', false);
			$("#apptDate").attr('disabled', true);
			$("#apptTime").attr('disabled', true);
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
	function timeSlotLookup(date, type, defaultValue){
		console.log("Begin");
		var sbuid = $('input[name="sbuid"]').val();
		if(date.length == 10){
			$.ajax({
				url : "ltsappointmenttimeslotlookup.html?sbuid=" + sbuid,
				type : 'POST',
				data : "date=" + date + "&type=" + type ,
				dataType : 'json',
				//timeout : 5000,
				success : function(data){
					//var obj = jQuery.parseJSON(data);
					
					pushTimeSlotOptions(data, type, defaultValue);
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
	function resetCeaseRentalDatePicker(minDate, maxDate){
		if(minDate == ""){
			minDate = "-29D";
		}
		if(maxDate == ""){
			maxDate = "+100Y";
		}
		$('#ceaseRentalDate').datepicker("destroy");
		$('#ceaseRentalDate').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate: "-29D", maxDate: "+100Y", //Y is year, M is month, D is day  
			yearRange: "0:+100" //the range shown in the year selection box
		});
	}
	function resetBackApptDatePicker(minDate, maxDate){
		if(minDate==""){
			minDate="-120D";
		}
		if(maxDate==""){
			maxDate="-1D";
		}
		$('#backApptDate').datepicker( "destroy");
		$('#backApptDate').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate: "-120D", maxDate: "-1D", //Y is year, M is month, D is day  
			yearRange: "0:+100" //the range shown in the year selection box
		});
	}
	function resetApptDatePicker(minDate, maxDate){
		if(minDate==""){
			minDate="+1D";
		}
		if(maxDate==""){
			maxDate="+90D";
		}
		$('#apptDate').datepicker( "destroy");
		$('#apptDate').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate: "+1D", maxDate: "+90D", //Y is year, M is month, D is day  
			yearRange: "0:+100" //the range shown in the year selection box
		});
	}
	function actionPerform(){
		resetFormDisplay();
		if($("#confirmedInd").val() == "true"){
			confirmDisplay("confirm");
		}else{
			colorTimeSlot("I");
			$('#cancelAppntBtn').attr('disabled', true);
			if($("#submitInd").val() == "CONFIRM"
					|| $("#submitInd").val() == "CANCEL"){
				confirmDisplay("cancel");
			}
			refreshTimeSlotType();
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
		$("#apptDate").change(function(event){
			var date = $("#apptDate").val();
			$("#apptDate").attr('disabled', false);
			resetApptDatePicker($("#apptDate").datepicker('getDate'), "");
			timeSlotLookup(date, "D");
		});
		$("#apptTime").change(function(event){
			if($("#apptTime").val() != ""){
				refreshTimeSlotType();
			}
		});
		$("#backApptDate").change(function(event){
			var date = $("#backApptDate").val();
			$("#backApptDate").attr('disabled', false);
			resetBackApptDatePicker($("#backApptDate").datepicker('getDate'), "");
		});
		$("#ceaseRentalDate").change(function(event){
			var date = $("#ceaseRentalDate").val();
			$("#ceaseRentalDate").attr('disabled', false);
			resetCeaseRentalDatePicker($("#ceaseRentalDate").datepicker('getDate'), "");
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
			$("#apptDate").attr('disabled', false);
			$("#apptTime").attr('disabled', false);
			$("#confirmedInd").val("true");
			$("#ltsTerminateAppointmentForm").submit();
		});
		$("input#cancelAppntBtn").click(function(event) {
			$("#submitInd").val("CANCEL");
			$("#preBookSerialNum").attr('disabled', false);
			$("#apptDate").attr('disabled', false);
			$("#apptTime").attr('disabled', false);
			$("#confirmedInd").val("false");
			$("#ltsTerminateAppointmentForm").submit();
		});
		$("#submit").click(function(event) {
			event.preventDefault();
			$("#submitInd").val("SUBMIT");
			$("#apptDate").attr('disabled', false);
			$("#apptTime").attr('disabled', false);
			$("#ltsTerminateAppointmentForm").submit();
		});
		$("#suspend").click(function(event){
			event.preventDefault();
			$("#submitInd").val("SUSPEND");
			$("#ltsTerminateAppointmentForm").submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	}
	return {
		actionPerform : actionPerform
	};
	
}();