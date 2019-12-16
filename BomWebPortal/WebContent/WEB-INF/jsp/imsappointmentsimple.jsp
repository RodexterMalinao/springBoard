<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="imsloadingpanel.jsp" %>
<%-- <%@ include file="imsvalidwindow.jsp" %> --%>
<script language="Javascript">

	var instdate = "${imsDoneUI.appntUI.targetInstallDate}";
	var insttime = "${imsDoneUI.appntUI.timeSlot}";
	
	var	earsrd = "${imsDoneUI.appntUI.estimatedSrd }";
	var	earsrd7 = "${esrd7}"; 
	var appdate = "${appdate}";	
	var minDateReq = "${minDate}";
	var maxMonthReq = "${maxMonth}";
	var isDoorKnock = ${bomsalesuser.salesType == 'Door Knocked' && ims_direct_sales == true};  
	
	/************ Detect client platform *********************/ 
	var BrowserDetect = {
			init: function () {
				this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
				this.version = this.searchVersion(navigator.userAgent)
					|| this.searchVersion(navigator.appVersion)
					|| "an unknown version";
				this.OS = this.searchString(this.dataOS) || "an unknown OS";
			},
			searchString: function (data) {
				for (var i=0;i<data.length;i++)	{
					var dataString = data[i].string;
					var dataProp = data[i].prop;
					this.versionSearchString = data[i].versionSearch || data[i].identity;
					if (dataString) {
						if (dataString.indexOf(data[i].subString) != -1)
							return data[i].identity;
					}
					else if (dataProp)
						return data[i].identity;
				}
			},
			searchVersion: function (dataString) {
				var index = dataString.indexOf(this.versionSearchString);
				if (index == -1) return;
				return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
			},
			dataBrowser: [
				{
					string: navigator.userAgent,
					subString: "Chrome",
					identity: "Chrome"
				},
				{ 	string: navigator.userAgent,
					subString: "OmniWeb",
					versionSearch: "OmniWeb/",
					identity: "OmniWeb"
				},
				{
					string: navigator.vendor,
					subString: "Apple",
					identity: "Safari",
					versionSearch: "Version"
				},
				{
					prop: window.opera,
					identity: "Opera",
					versionSearch: "Version"
				},
				{
					string: navigator.vendor,
					subString: "iCab",
					identity: "iCab"
				},
				{
					string: navigator.vendor,
					subString: "KDE",
					identity: "Konqueror"
				},
				{
					string: navigator.userAgent,
					subString: "Firefox",
					identity: "Firefox"
				},
				{
					string: navigator.vendor,
					subString: "Camino",
					identity: "Camino"
				},
				{		// for newer Netscapes (6+)
					string: navigator.userAgent,
					subString: "Netscape",
					identity: "Netscape"
				},
				{
					string: navigator.userAgent,
					subString: "MSIE",
					identity: "Explorer",
					versionSearch: "MSIE"
				},
				{
					string: navigator.userAgent,
					subString: "Gecko",
					identity: "Mozilla",
					versionSearch: "rv"
				},
				{ 		// for older Netscapes (4-)
					string: navigator.userAgent,
					subString: "Mozilla",
					identity: "Netscape",
					versionSearch: "Mozilla"
				}
			],
			dataOS : [
				{
					string: navigator.platform,
					subString: "Win",
					identity: "Windows"
				},
				{
					string: navigator.platform,
					subString: "Mac",
					identity: "Mac"
				},
				{
					   string: navigator.userAgent,
					   subString: "iPhone",
					   identity: "iPhone/iPod"
			    },
			    {
					   string: navigator.platform,
					   subString: "iPad",
					   identity: "iPad"
			    },
				{
					string: navigator.platform,
					subString: "Linux",
					identity: "Linux"
				}
			]

		};
	/************ eric ng testing *********************/
	
	
	
	

	$(document).ready(function() {
						
		$("#slotType").get(0).selectedIndex = $("#timeSlot").get(0).selectedIndex;
		
		document.getElementById("timeSlotDisplay").value=
			document.getElementById("timeSlot").options[document.getElementById("timeSlot").selectedIndex].text;
		
		document.getElementById("emailAddr").value="${imsDoneUI.esigEmailAddr}";
		document.getElementById("SMSno").value="${imsDoneUI.SMSno}";
		
		/*
		if("${appointment.blackListInd}"=="Y"){
			$("tr#estsrdcols").hide();
			$("tr#targetdatecols").hide();
			$("tr#bookcols").hide();
		}*/
		
		if("${imsDoneUI.appntUI.preInstOrder}"=="N"){
			$("tr#commcols1").hide();
			$("tr#commcols2").hide();
		}else{

			var targetMinDate = new Date($("#instdate").val());
			var targetMaxDate = new Date($("#instdate").val());
			
			targetMinDate.setDate(targetMinDate.getDate()+parseInt(minDateReq));
			targetMaxDate.setMonth(targetMaxDate.getMonth()+parseInt(maxMonthReq));
			
			$( "#commdate" ).datepicker({
				changeMonth : true,
				showButtonPanel : true,
				dateFormat : 'yy/mm/dd', 
				minDate : '${imsDoneUI.appntUI.estimatedSrd }'
			});
			
			$( "#commdate" ).datepicker( "option", "minDate", targetMinDate);	
 			$( "#commdate" ).datepicker( "option", "maxDate", targetMaxDate);
 			
		}
		
		if("${imsDoneUI.appntUI.bookNotAllowed}"=="Y"){
			$("tr#bookcols").hide();
			$("tr#timeslotcols").hide();
			$("#instdate").attr("readonly", true);			
		}else{
			$( "#instdate" ).datepicker({
				changeMonth : true,
				showButtonPanel : true,
				dateFormat : 'yy/mm/dd', 
				minDate : '${imsDoneUI.appntUI.estimatedSrd }'
			});									
			if("${imsDoneUI.appntUI.preInstOrder}"=="Y"){
				var maxAppDate = new Date(appdate);
				maxAppDate.setDate(maxAppDate.getDate()+30);
				$( "#instdate" ).datepicker( "option", "maxDate", maxAppDate);	
			}	
		}
		
		BrowserDetect.init();
		colorTimeSlot();
		
		if("${imsSubmitTag}"=="S"){
			alert("Order is suspended. Order ID: ${imsOrderId}");
			window.location = "welcome.html";
		}else if("${imsSubmitTag}"=="C"){			
			alert("Order is cancelled. Order ID: ${imsOrderId}");
			window.location = "welcome.html";
		}
		
		if("${imsDoneUI.appntUI.actionInd}"=="R"){
			$(":input").attr("disabled", true);
		}
		
		if("${ImsOrder.orderActionInd}"!="W"){
			$("#canceltr").hide();
		}
		
		if(checkSrdOver()){
			$("#timeSlot").attr("disabled", true);	
		}
		
		if(checkSrdUnder()){
			$("#timeSlot").attr("disabled", true);	
		}
		
		if("${imsDoneUI.appntUI.hosOrPhInd}"=="Y"){
			$("#warning_hos").show();
		}
		
		if("${imsDoneUI.appntUI.privateInd}"=="Y"){
			$("#warning_pri").show();
		}
		
		if("${imsDoneUI.appntUI.errorMsg}"!=null && "${imsDoneUI.appntUI.errorMsg}"!=""){
			$("#timeSlot").attr("disabled", true);
			alert("${imsDoneUI.appntUI.errorMsg}");
		}

		onChangeWaiveCoolingOffPeriod(); 
		$("[name='waiveCoolingOffPeriod']").change(function(){
			onChangeWaiveCoolingOffPeriod(); 
			}); 
	});
	
	function checkSrdUnder(){
		if(document.getElementById('targetInstallDate').value!=null && document.getElementById('targetInstallDate').value!=""){
			var esrd = new Date(
					"${imsDoneUI.appntUI.estimatedSrd}".substring(0, 4),
					"${imsDoneUI.appntUI.estimatedSrd}".substring(5, 7)-1,
					"${imsDoneUI.appntUI.estimatedSrd}".substring(8, 10));
			var srd = new Date(
					document.getElementById('targetInstallDate').value.substring(0, 4),
					document.getElementById('targetInstallDate').value.substring(5, 7)-1,
					document.getElementById('targetInstallDate').value.substring(8, 10));
			if(srd<esrd){				
				return true;
			}else{
				return false;
			}			
		}
	}
	
	function checkSrdOver(){		
		if(document.getElementById('targetInstallDate').value!=null && document.getElementById('targetInstallDate').value!=""){
			var today = new Date();
			var srd = new Date(
					document.getElementById('targetInstallDate').value.substring(0, 4),
					document.getElementById('targetInstallDate').value.substring(5, 7)-1,
					document.getElementById('targetInstallDate').value.substring(8, 10));
			if(srd<=today){				
				return true;
			}else{
				return false;
			}			
		}
	}
	
	function getTimeSlot(){

		var targetMinDate = new Date($("#instdate").val());
		var targetMaxDate = new Date($("#instdate").val());
		
		targetMinDate.setDate(targetMinDate.getDate()+parseInt(minDateReq));
		targetMaxDate.setMonth(targetMaxDate.getMonth()+parseInt(maxMonthReq));
		
		$( "#commdate" ).datepicker( "option", "minDate", targetMinDate);
 		$( "#commdate" ).datepicker( "option", "maxDate", targetMaxDate);	
		
		$.ajax({
			url : 'imstimeslot.html?date=' + $("#instdate").val()
					+ '&time=' + $("#timeSlot").val()
					+ '&type=' + $("#slotType").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus=='parsererror'){
					alert("Your session has been timed out, please re-login.");	
				}else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
			    }					
			},
			success : function(msg) {
				$('option', '#timeSlot').remove();
				$('option', '#slotType').remove();
				
				$('#timeSlot').append(new Option('', '', true, true));
				$('#slotType').append(new Option('', '', true, true));
				
				$.each(eval(msg), function(i, item) {
					
					if((item.errorMsg != null && item.errorMsg != "") && item.i == "0"){
						var timeslotoptions = $('#timeSlot').attr('options');
						var slottypeoptions = $('#slotType').attr('options');							
						timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
						slottypeoptions[slottypeoptions.length] = new Option(item.tsdisplay, item.tstype, true, false);
						$("#timeSlot").attr("disabled", true);
						alert(item.errorMsg);
					}else{
						var timeslotoptions = $('#timeSlot').attr('options');
						var slottypeoptions = $('#slotType').attr('options');							
						timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
						slottypeoptions[slottypeoptions.length] = new Option(item.tsdisplay, item.tstype, true, false);
					}
				});
				
				colorTimeSlot();
			}
		});				
	}
	
	$( function() {
		
		$("#timeSlot").change(
			function(){
				$("#slotType").get(0).selectedIndex = this.selectedIndex;
			}
		);
		
		$("#instdate").change(
			function(){
				getTimeSlot();
				$("#timeSlot").attr("disabled", false);
			}							
		);
		
		$("#additionRemarks").keyup(
			function(){
				checkRemarkLength();
				checkRemarkIsEng();
			}
		);
		
		$("#instContactName").keyup(
			function(){
				cardHolderNameIsEng();
			}
		);
		
		
		
		
		$("#instContactNum").change(
			function(){
				checkPhoneNumber(this.value, "instContactNumError");	
			}
		);
		
		$("#instSmsNum").change(
			function(){
				checkMobile(false,false);
			}
		);
		
	});
	
	function checkRemarkLength(){
		if(document.getElementById("additionRemarks").value.length>100)
		{
			$("#remarkError").show();
			return false;
		}
		else
		{
			$("#remarkError").hide();
			return true;
		}
	}


	function   cardHolderNameIsEng()   {   
		  var   noCharOver127   =   true;   
		  var   s   =   document.getElementById("instContactName").value;
		  for (var   i=0;   i<s.length;   i++)   {           
		  if (s.charCodeAt(i)>127)
			  noCharOver127 = false; 
		  }
		  if (noCharOver127){
			  $("#instContactNameNotEng").hide();
			  return true;
		  }
		  else {
			  $("#instContactNameNotEng").show();
			  return false;
		  }
		}
	
	function   checkRemarkIsEng()   {   
		  var   noCharOver127   =   true;   
		  var   s   =   document.getElementById("additionRemarks").value;
		  for (var   i=0;   i<s.length;   i++)   {           
		  if (s.charCodeAt(i)>127)
			  noCharOver127 = false; 
		  }
		  if (noCharOver127){
			  $("#remarkError2").hide();
			  return true;
		  }
		  else {
			  $("#remarkError2").show();
			  return false;
		  }
		} 
	
	function checkPhoneNumber(phoneno, errorid){
		if(phoneno.length>0 && phoneno.match("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]")==null){
			$("#"+errorid).show();
			$(".InstNumVoid").hide();
			return false;
		}else{
			$("#"+errorid).hide();
			if ( phoneno.length>0 )
			{
				$(".InstNumVoid").hide();
			}
			
			return true;
		}
	}
	
	function keyUpOnInstContactName(){		
		document.getElementById('instContactName').value = document.getElementById('instContactName').value.toUpperCase();
	}
	
	function checkBeforeSubmit(){
		var canSubmit = true;
		
		if(!checkRemarkLength()){
			canSubmit = false;
		}
		
		if(!checkRemarkIsEng()){
			canSubmit = false;
		}

		if(!cardHolderNameIsEng()){
			canSubmit = false;
		}
		
		if(!checkPhoneNumber($("#instSmsNum").val(), "instSmsNumError")){
			canSubmit = false;
		}
	
		if(!checkPhoneNumber($("#instContactNum").val(), "instContactNumError")){
			canSubmit = false;
		}
		
		if ( "${isCC}" == "Y" )
			{
			if ( $("#instSmsNum").val() == "" && $("#instContactNum").val() == "")
			{
				canSubmit = false;
				$("#instContactNumVoid").show();
				$("#instSmsNumVoid").show();
			}
			else
			{
				$("#instContactNumVoid").hide();
				$("#instSmsNumVoid").hide();
			}
			
			if ( $("#instContactName").val() == "" )
			{
				canSubmit = false;
				$("#instContactNameVoid").show();
			}
			else
			{
				$("#instContactNameVoid").hide();
			}
		}
		
		return canSubmit;
	}
	
	function checkMobile(boo, confirmed)
	{
		if ('${isCC}' == 'Y' && $("#instSmsNum").val() != "" )
		{
			$(".InstNumVoid").hide();
			$.ajax({
				url : 'checkmobilenum.html?mobileNum=' + $("#instSmsNum").val(),
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
				    document.getElementById('instSmsNumError').style.display = "none";
				},
				success : function(msg) {
					var count=0;
					$.each(eval(msg), function(i, item) 
					{
						if( item.valid )
						{
							document.getElementById('instSmsNumError').style.display = "none";
							if ( boo && !confirmed )
							{
								submitShowLoading();
								document.submitForm.submit();
							}
							else if ( boo && confirmed )
							{
								var ans = confirm( 
										'Please confirm\n'+
										'Installation date:'+document.getElementById('targetInstallDate').value+'\n'+
										'Time:'+document.getElementById('timeSlotDisplay').value							
										);
								if(ans){
									submitShowLoading();
									document.submitForm.submit();
								}	
							}
						}
						else if( !item.valid )
						{
							document.getElementById('instSmsNumError').style.display = "";
						}
						count++;
					});
					if(count==0)
					{ 
						document.getElementById('instSmsNumError').style.display = "none";
					}
				}
			});
		}
		else if ( boo && !confirmed )
		{
			submitShowLoading();
			document.submitForm.submit();
		}
		else if ( boo && confirmed )
		{
			if(confirm( 
					'Please confirm\n'+
					'Installation date:'+document.getElementById('targetInstallDate').value+'\n'+
					'Time:'+document.getElementById('timeSlotDisplay').value							
					)){
				submitShowLoading();
				document.submitForm.submit();
			}	
		}
	}
	
	function timeConfirm(){
		if(checkOrderStatus()){
			if(document.getElementById("timeSlot").disabled==false){
				if(document.getElementById("timeSlot").selectedIndex==0){
					alert("please select timeslot");
				}else if(document.getElementById("slotType").value=="NS"||document.getElementById("slotType").value=="NA"){
					alert("please select valid timeslot");
				}else if(document.getElementById("slotType").value=="CURRENT_SELECTED"){
					alert("Currently booked time slot");			
				}else{			
					
					$.ajax({
						url : 'imsreserveappointment.html?date=' + $("#instdate").val()
								+ '&time=' + $("#timeSlot").val()
								+ '&type=' + $("#slotType").val(),
						type : 'POST',
						dataType : 'json',
						timeout : 60000,
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							if(textStatus=='parsererror'){
								alert("Your session has been timed out, please re-login.");	
							}else if(textStatus == 'timeout'){
						    	alert("Server response timeout, please try again.");
						    }					
						},
						success : function(msg) {					
							$.each(eval(msg), function(i, item) {						
								if(item.returnValue == -1){
									alert(item.errorMsg);
								}else{
									document.getElementById('serialNum').value = item.serial;
								}
							});
							document.getElementById('targetInstallDate').value =
							document.getElementById('instdate').value;
							document.getElementById("timeSlotDisplay").value=
							document.getElementById("timeSlot").options[document.getElementById("timeSlot").selectedIndex].text;
							instdate = document.getElementById('targetInstallDate').value;
							insttime = document.getElementById('timeSlot').value;						
							//colorTimeSlot();
							getTimeSlot();
							document.getElementById('timeSlot').value = insttime;						
						}
					});					
				}	
			}	
		}
	}
	
	function formSubmit(){
		if(checkOrderStatus()){
			if("${imsDoneUI.appntUI.preInstOrder}"=="Y" && 
					(document.getElementById('targetCommDate')==null || document.getElementById('targetCommDate').value=='')){
				alert("please select target commencement date");
			}else{
				if("${imsDoneUI.appntUI.bookNotAllowed}"=="Y"){
					if(checkBeforeSubmit())
					{
						checkMobile(true,false);
					}
				}else{
					if(document.getElementById('serialNum')==null || document.getElementById('serialNum').value==''){
						alert("please complete the appointment booking");
					}else{
						if(checkBeforeSubmit())
						{
							if(!checkSrdOver()){
								if(!checkSrdUnder()){
									if(		isDoorKnock 
											&& $("[name='waiveCoolingOffPeriod']:checked").val()=='N' 
											&& checkDaysDelta(appdate, document.getElementById('targetInstallDate').value) < 7 )
										alert ("appointment date must be at least T+7");
									else checkMobile(true, true);
								}
								else
								{
									alert("appointment date must not be earlier than estimated, please make a new booking");
								}								
							}else{
								alert("appointment date is over, please make a new booking");
							}							
						}
					}
				}	
			}			
		}
	}
	function submitCancel(){
		if(checkOrderStatus()){
			if($("#cancelCd").get(0).selectedIndex>0){
				if(checkRemarkLength()){
					if(confirm("order will be cancelled with reason\n"+
							$("#cancelCd").find(":selected").text())){
						document.getElementById("submitTag").value="C";
						submitShowLoading();
						document.submitForm.submit();
					}				
				}				
			}else{
				alert("please select a cancel reason");	
			}	
		}		
	}
	function submitSuspend(){
		if(checkOrderStatus()){
			if($("#suspendCd").get(0).selectedIndex>0){
				if(checkRemarkLength()){
					if(confirm("order will be suspended with reason\n"+
							$("#suspendCd").find(":selected").text())){
						document.getElementById("submitTag").value="S";
						submitShowLoading();
						document.submitForm.submit();	
					}					
				}				
			}else{
				alert("please select a suspend reason");
			}
		}
	}
	function colorTimeSlot(){				
		var timeslotlist=document.getElementById("timeSlot");
		var slottypelist=document.getElementById("slotType");
		var i;
		for (i=1;i<timeslotlist.length;i++){//skip first element			
			if(document.getElementById("instdate").value==instdate &&
					timeslotlist.options[i].value==insttime){
				slottypelist.options[i].value="CURRENT_SELECTED";
				timeslotlist.options[i].selected=true;
				slottypelist.options[i].selected=true;
			}
			else if (slottypelist.options[i].value=="NS"){ //indicate no resource
				
				if (BrowserDetect.OS == "iPad" || BrowserDetect.OS == "iPhone/iPod" || BrowserDetect.OS == "Linux") 
				{
					timeslotlist.options.remove(i);
					slottypelist.options.remove(i);
						i--;
				}else
				timeslotlist.options[i].style.color="red";
					
			}else if(slottypelist.options[i].value=="NA"){
				if (BrowserDetect.OS == "iPad" || BrowserDetect.OS == "iPhone/iPod" || BrowserDetect.OS == "Linux") 
				{
					timeslotlist.options.remove(i);
					slottypelist.options.remove(i);
					i--;
				}else
				timeslotlist.options[i].style.color="silver";
				
				
				
				
			}
		}		
	} 	
	function checkOrderStatus(){
		if("${imsDoneUI.appntUI.actionInd}"=="R"){
			alert("order has been cancelled!");
			return false;
		}else{
			return true;
		}
	}
	
	function checkDaysDelta(str1, str2){
		var date1 = new Date(str1);
		var date2 = new Date(str2);
		var timeDiff = Math.abs(date2.getTime() - date1.getTime());
		return (Math.ceil(timeDiff / (1000 * 3600 * 24))); 
	}
	
	function onChangeWaiveCoolingOffPeriod(){
		
		if ($("[name='waiveCoolingOffPeriod']:checked").val()=='N') {
			if(checkDaysDelta(appdate, earsrd) < 7){
				$( "#instdate" ).datepicker( "option", "minDate", earsrd7);
			}
			if(checkDaysDelta(appdate, document.getElementById('targetInstallDate').value) < 7){
				document.getElementById('targetInstallDate').value = earsrd7;
			}
		} else {
			$( "#instdate" ).datepicker( "option", "minDate", earsrd);
		}
	}
	
	function checkMobile() {

		$(".error_SMSno").text("").hide();
		if ($("input[name=SMSno]").val().length == 0) {
			$(".error_SMSno").text(
					"Missing mobile no. for sending notification.").show();
		} else {

			$.ajax({
						url : 'checkmobilenum.html?SMS=' + $("#SMSno").val(),
						type : 'POST',
						dataType : 'json',
						timeout : 60000,
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							if (textStatus == 'parsererror') {
								alert("Your session has been timed out, please re-login.");
							} else if (textStatus == 'timeout') {
								alert("Server response timeout, please try again.");
							}
							$(".error_SMSno").text("").hide();
						},
						success : function(msg) {
							var count = 0;
							$.each(eval(msg), function(i, item) {
								if (item.valid) {
									$(".error_SMSno").text("").hide();
								} else if (!item.valid) {
									$(".error_SMSno").text(
											"Invalid mobile number").show();
								}
								count++;
							});
							if (count == 0) {
								$(".error_SMSno").text("").hide();
							}
						}
					});
		}

	}
	function checkEmail() {

		if ($("input[name=emailAddr]").val().length == 0) {
			$(".error_emailAddr").text(
					"Missing Email address for sending notification.").show();
		} else if (!(/^\S+@\S+$/).test($("input[name=emailAddr]").val())) {
			$(".error_emailAddr").text("Invalid Email format").show();
		} else {
			$(".error_emailAddr").text("").hide();
		}

	}
</script> 

<script>
	<c:if test='${isCC == "Y"}'>
		$(document).ready(function(){
			$("#additionRemarks").keypress
			(
				function(e){
					if(document.getElementById("additionRemarks").value.length>=100) 
						e.preventDefault();
			});
		});
		
	</c:if>
</script>
<br><br>
<form:hidden path="errorMsg"/>
<div id="warning_addr_3" class="ui-widget" style="width: 40%;"> 
	<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
		<p>
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<div id="warning_addr_msg3" class="contenttext" style="font-weight:bold; margin-left: 1.5em;">The SRD (${imsDoneUI.appntUI.targetInstallDate}) is no longer availble, please select a new SRD.</div>  
		</p>
	</div>
</div>
<table width="100%"> 
  <tr>
	<td bgcolor="#FFFFFF"> 
		<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr id="estsrdcols" style="display:none;">
			<th align="left"> Estimated Earliest SRD:</th>
			<th align="left">${imsDoneUI.appntUI.estimatedSrd} (Ref: ${imsDoneUI.appntUI.estSrdReason })</th>
			<td>&nbsp;</td>
		</tr>
		<tr id="bookcols">
        	<td align="left">Pre-book Serial no.:</td>
			<td align="left">
				<form:input path="serialNum" readonly="true"/>  
			</td>
		</tr>	
		<tr id="targetdatecols">
			<td align="left">
				Target Installation Date:
			</td>
			<td align="left">				
				<input id="instdate" value="${imsDoneUI.appntUI.targetInstallDate }"  readonly="true"/>          
			</td>
			<form:hidden path="targetInstallDate"/>
			<td>&nbsp;</td>
		</tr>
		<tr id="timeslotcols">
			<td align="left">Timeslot:</td>
			<td align="left">				
				
				<form:select path="timeSlot" cssStyle="width:100px">
					<form:option value=""/>
					<form:options items="${timeSlots}" itemValue="timeSlot" itemLabel="timeSlotDisplay"/>
				</form:select>
				<form:hidden path="timeSlotDisplay"/>									
				<div style="display: none;">				
					<form:select id="slotType" path="">				
					<form:option value=""/>
					<form:options items="${timeSlots}" itemValue="slotType" itemLabel="timeSlot"/>
				</form:select></div>												
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <a href="#" class="nextbutton" onclick="timeConfirm()">Confirm</a>
			</td>			
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td valign="top" align="left">BMO Remarks:</td>
			<td colspan="2">
				<form:textarea path="bmoRmk" rows="3" cols="40" readonly="true"/>				
			</td>
		</tr>	
				
		<tr id="commcols1">
			<td align="left">
				Target Commencement Date:
			</td>
			<td align="left">	
				<input id="commdate" value="" />  			
				<form:hidden path="targetCommDate" />
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr id="commcols2">
			<td align="left">
				(For Pre-installation Only)
			</td>
		</tr>	

		<tr style="display:none;">
			<td  valign="top" align="left">Cooling Off Period:</td>
			<td align="left">
				<form:radiobutton path="waiveCoolingOffPeriod" value="Y"/> Waive											
				&nbsp;&nbsp;&nbsp;&nbsp;
				<form:radiobutton path="waiveCoolingOffPeriod" value="N"/> Not waive
			</td>
			<td>&nbsp;</td>	
		</tr>	
		
		<tr style="display:none;"> 
			<td valign="top" align="left">
				Additional Order Remark:
			</td>
			<td align="left"> 
				<form:textarea path="additionRemarks" rows="3" cols="40" />				
			</td>
			<td align="left">&nbsp;</td>
		</tr>
				<c:if test="${ims_direct_sales eq true}">
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" rules="none">
								<tr>
									<td class="table_title">Recall Notification Information</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr>
					<td>					
						<table>
							<tbody>
									<tr>
										<td width="100%">
											<div id="emailAddrDiv" style="margin-left: 10px;">
												Email:
												<form:input path="emailAddr" maxlength="64"
													cssStyle="width:20em; color:red ; font-weight:bold"
													onblur="checkEmail()" />
												<div class="clear2"></div>
												<span class="error error_emailAddr" style="display: none"></span>
												<form:errors path="emailAddr" cssClass="error" />
											</div>
										</td>
										<td width="5%">&nbsp;<br></br></td>
									</tr>
									<tr>
										<td width="100%">
											<div id="SMSnoDiv" style="margin-left: 10px;">
												SMS :
												<form:input path="SMSno" maxlength="64"
													cssStyle="width:20em; color:red ; font-weight:bold"
													onblur="checkMobile()" />
												<div class="clear2"></div>
												<span class="error error_SMSno" style="display: none"></span>
												<form:errors path="SMSno" cssClass="error" />
											</div>
										</td>
										<td width="5%">&nbsp;</td>
									</tr>
									<tr>
										<td width="5%">&nbsp;</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</c:if>

			</table>
	</td>
   </tr>

   
</table>
