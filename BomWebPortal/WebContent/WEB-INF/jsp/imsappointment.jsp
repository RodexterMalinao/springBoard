<%@ include file="header.jsp" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<%@ page import="java.util.*" %>
<%@ include file="imsloadingpanel.jsp" %>
<%-- <%@ include file="imsvalidwindow.jsp" %> --%>

<script language="Javascript">

	var instdate = "${appointment.targetInstallDate}";
	var insttime = "${appointment.timeSlot}";
	
	var	earsrd = "${appointment.estimatedSrd }";
	var	earsrd7 = "${esrd7}"; 
	var appdate = "${appdate}";
	var minDateReq = "${minDate}";
	var maxMonthReq = "${maxMonth}";
	var isDoorKnock = ${order.dsType == 'Door Knocked' && ims_direct_sales == true}; 
	
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
		
		/*
		if("${appointment.blackListInd}"=="Y"){
			$("tr#estsrdcols").hide();
			$("tr#targetdatecols").hide();
			$("tr#bookcols").hide();
		}*/
		
		if("${appointment.preInstOrder}"=="N"){
			$("tr#commcols1").hide();
			$("tr#commcols2").hide();
		}else{

			var targetMinDate = new Date($("#instdate").val());
			var targetMaxDate = new Date(appdate);
			
			targetMinDate.setDate(targetMinDate.getDate()+parseInt(minDateReq));
			targetMaxDate.setMonth(targetMaxDate.getMonth()+parseInt(maxMonthReq));
			
			$( "#targetCommDate" ).datepicker({
				changeMonth : true,
				showButtonPanel : true,
				dateFormat : 'yy/mm/dd', 
				minDate : '${appointment.estimatedSrd }'
			});

			$( "#targetCommDate" ).datepicker( "option", "minDate", targetMinDate);	
 			$( "#targetCommDate" ).datepicker( "option", "maxDate", targetMaxDate);
		}
		
		if("${appointment.bookNotAllowed}"=="Y"){
			$("tr#bookcols").hide();
			$("tr#timeslotcols").hide();
			$("#instdate").attr("readonly", true);			
		}else{
			$( "#instdate" ).datepicker({
				changeMonth : true,
				showButtonPanel : true,
				dateFormat : 'yy/mm/dd', 
				minDate : '${appointment.estimatedSrd }'
			});						
			if("${appointment.preInstOrder}"=="Y"){
				var maxAppDate = new Date(appdate);
				maxAppDate.setDate(maxAppDate.getDate()+30);
				$( "#instdate" ).datepicker( "option", "maxDate", maxAppDate);	
			}
		}
		
		BrowserDetect.init();
		colorTimeSlot();
		
		if("${imsSubmitTag}"=="S"){
// 			alert("Order is suspended. Order ID: ${imsOrderId}");
			alert('<spring:message code="ims.pcd.imsappointment.msg.022"/>' + '${imsOrderId}');

			window.location = "welcome.html";
		}else if("${imsSubmitTag}"=="C"){			
// 			alert("Order is cancelled. Order ID: ${imsOrderId}");
			alert('<spring:message code="ims.pcd.imsappointment.msg.023"/>' + '${imsOrderId}');
			window.location = "welcome.html";
		}
		
		if("${appointment.actionInd}"=="R"){
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
		
		if("${appointment.hosOrPhInd}"=="Y"){
			$("#warning_hos").show();
		}
		
		if("${appointment.privateInd}"=="Y"){
			$("#warning_pri").show();
		}
		
		if("${appointment.errorMsg}"!=null && "${appointment.errorMsg}"!=""){
			$("#timeSlot").attr("disabled", true);
			alert("${appointment.errorMsg}");
		}

		onChangeWaiveCoolingOffPeriod(); 
		$("[name='waiveCoolingOffPeriod']").change(function(){
			onChangeWaiveCoolingOffPeriod(); 
			}); 
	});
	
	function checkSrdUnder(){
		if(document.getElementById('targetInstallDate').value!=null && document.getElementById('targetInstallDate').value!=""){
			var esrd = new Date(
					"${appointment.estimatedSrd}".substring(0, 4),
					"${appointment.estimatedSrd}".substring(5, 7)-1,
					"${appointment.estimatedSrd}".substring(8, 10));
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
	
	function checkGiftInstallDate(){		
		if(document.getElementById('targetInstallDate').value!=null && document.getElementById('targetInstallDate').value!=""){
			var giftDate = new Date();
			var srd = new Date(
					document.getElementById('targetInstallDate').value.substring(0, 4),
					document.getElementById('targetInstallDate').value.substring(5, 7)-1,
					document.getElementById('targetInstallDate').value.substring(8, 10));
			var numberOfDaysToAdd = $('#giftCommDate').val();
		    numberOfDaysToAdd = parseInt(numberOfDaysToAdd,10);
			giftDate.setDate(giftDate.getDate() + numberOfDaysToAdd);
			var dd = giftDate.getDate();
			var mm = giftDate.getMonth() + 1;
			var yyyy = giftDate.getFullYear();
			var giftDateFormatted = yyyy + '/'+ mm + '/'+ dd;
			var giftday = new Date(giftDateFormatted);
			if(srd<=giftday){				
				return true;
			}else{
				return false;
			}			
		}
	}
	
	function getTimeSlot(){
		
		var targetMinDate = new Date($("#instdate").val());
		var targetMaxDate = new Date(appdate);
		
		targetMinDate.setDate(targetMinDate.getDate()+parseInt(minDateReq));
		targetMaxDate.setMonth(targetMaxDate.getMonth()+parseInt(maxMonthReq));
		
		$( "#targetCommDate" ).datepicker( "option", "minDate", targetMinDate);
 		$( "#targetCommDate" ).datepicker( "option", "maxDate", targetMaxDate);	
		
		$.ajax({
			url : 'imstimeslot.html?date=' + $("#instdate").val()
					+ '&time=' + $("#timeSlot").val()
					+ '&type=' + $("#slotType").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus=='parsererror'){
// 					alert("Your session has been timed out, please re-login.");	
					alert('<spring:message code="ims.pcd.imsappointment.msg.020"/>');
					
				}else if(textStatus == 'timeout'){
// 			    	alert("Server response timeout, please try again.");
			    	alert('<spring:message code="ims.pcd.imsappointment.msg.021"/>');
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
// 				        alert("Your session has been timed out, please re-login."); 
				        alert('<spring:message code="ims.pcd.imsappointment.msg.020"/>');
				    }
				    else if(textStatus == 'timeout')
				    {
// 				    	alert("Server response timeout, please try again.");
				    	alert('<spring:message code="ims.pcd.imsappointment.msg.021"/>');
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
										'<spring:message code="ims.pcd.imsappointment.015"/>'+'\n'+
										'<spring:message code="ims.pcd.imsappointment.016"/>'+document.getElementById('targetInstallDate').value+'\n'+
										'<spring:message code="ims.pcd.imsappointment.017"/>'+document.getElementById('timeSlotDisplay').value							
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
					'<spring:message code="ims.pcd.imsappointment.015"/>'+'\n'+
					'<spring:message code="ims.pcd.imsappointment.016"/>'+document.getElementById('targetInstallDate').value+'\n'+
					'<spring:message code="ims.pcd.imsappointment.017"/>'+document.getElementById('timeSlotDisplay').value							
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
// 					alert("please select timeslot");
					alert('<spring:message code="ims.pcd.imsappointment.msg.001"/>');
				}else if(document.getElementById("slotType").value=="NS"||document.getElementById("slotType").value=="NA"){
// 					alert("please select valid timeslot");
					alert('<spring:message code="ims.pcd.imsappointment.msg.002"/>');
				}else if(document.getElementById("slotType").value=="CURRENT_SELECTED"){
// 					alert("Currently booked time slot");			
					alert('<spring:message code="ims.pcd.imsappointment.msg.003"/>');
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
// 								alert("Your session has been timed out, please re-login.");	
								alert('<spring:message code="ims.pcd.imsappointment.msg.020"/>');
							}else if(textStatus == 'timeout'){
// 						    	alert("Server response timeout, please try again.");
						    	alert('<spring:message code="ims.pcd.imsappointment.msg.021"/>');
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
			if("${appointment.preInstOrder}"=="Y" && 
					(document.getElementById('targetCommDate')==null || document.getElementById('targetCommDate').value=='')){
// 				alert("please select target commencement date");
				alert('<spring:message code="ims.pcd.imsappointment.msg.025"/>');
			}else{
				if("${appointment.bookNotAllowed}"=="Y"){
					if(checkBeforeSubmit())
					{
						if(!checkGiftInstallDate()){
// 							alert("Installation must be within ["+$('#giftCommDate').val()+"] days for orders with gift selection. Please choose an earlier installation date or deselect the selected gift");
							alert('<spring:message code="ims.pcd.imsappointment.msg.004"/>'+$('#giftCommDate').val()+'<spring:message code="ims.pcd.imsappointment.msg.004a"/>');
						}else{
							checkMobile(true,false);
						}
					}
				}else{
					if(document.getElementById('serialNum')==null || document.getElementById('serialNum').value==''){
// 						alert("please complete the appointment booking");
						alert('<spring:message code="ims.pcd.imsappointment.msg.024"/>');
					}else if(document.getElementById("timeSlotDisplay").value!= document.getElementById("timeSlot").options[document.getElementById("timeSlot").selectedIndex].text){
// 						alert("please confirm your selected timeslot");
						alert('<spring:message code="ims.pcd.imsappointment.msg.029"/>');
					}					
					else{
						if(checkBeforeSubmit())
						{
							if(!checkSrdOver()){
								if(!checkSrdUnder()){
									if(		isDoorKnock 
											&& $("[name='waiveCoolingOffPeriod']:checked").val()=='N' 
											&& checkDaysDelta(appdate, document.getElementById('targetInstallDate').value) < 7 ){
// 										alert ("appointment date must be at least T+7");
										alert('<spring:message code="ims.pcd.imsappointment.msg.005"/>');
								}else{
										if(!checkGiftInstallDate()){
// 											alert("Installation must be within ["+$('#giftCommDate').val()+"] days for orders with gift selection. Please choose an earlier installation date or deselect the selected gift");
											alert('<spring:message code="ims.pcd.imsappointment.msg.004"/>'+$('#giftCommDate').val()+'<spring:message code="ims.pcd.imsappointment.msg.004a"/>');
										}else{
											checkMobile(true, true);
										}
									}
								}
								else
								{									
// 									alert("appointment date must not be earlier than estimated, please make a new booking");
									alert("<spring:message code="ims.pcd.imsappointment.msg.006"/>");
								}								
							}else{
// 								alert("appointment date is over, please make a new booking");
								alert("<spring:message code="ims.pcd.imsappointment.msg.007"/>");
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
					if(confirm("<spring:message code="ims.pcd.imsappointment.msg.008"/>"+
							$("#cancelCd").find(":selected").text())){
						document.getElementById("submitTag").value="C";
						submitShowLoading();
						document.submitForm.submit();
					}				
				}				
			}else{
// 				alert("please select a cancel reason");	
				alert("<spring:message code="ims.pcd.imsappointment.msg.026"/>");
			}	
		}		
	}
	function submitSuspend(){
		if(checkOrderStatus()){
			if($("#suspendCd").get(0).selectedIndex>0){
				if(checkRemarkLength()){
					if(confirm("<spring:message code="ims.pcd.imsappointment.msg.012"/>"+
							$("#suspendCd").find(":selected").text())){
						document.getElementById("submitTag").value="S";
						submitShowLoading();
						document.submitForm.submit();	
					}					
				}				
			}else{
// 				alert("please select a suspend reason");
				alert("<spring:message code="ims.pcd.imsappointment.msg.009"/>");
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
		if("${appointment.actionInd}"=="R"){
// 			alert("order has been cancelled!");
			alert("<spring:message code="ims.pcd.imsappointment.msg.027"/>");
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
</script> 

<table width="100%" height="30">
	<tr>
		<td class="contenttextgreen" align="center">
		 	 <spring:message code="ims.pcd.imsappointment.027"/>
		</td>
	</tr>
</table>

<c:choose>
<c:when test='${ImsOrder.nowTvFormType == null}'>
<input type="hidden" value="no" id="CanBuyNowTv" />
</c:when>
<c:when test='${ImsOrder.nowTvFormType == ""}'>
<input type="hidden" value="no" id="CanBuyNowTv" />
</c:when>
<c:otherwise>
<input type="hidden" value="yes" id="CanBuyNowTv" />
</c:otherwise>
</c:choose> 

<link href="css/imsds.css" rel="stylesheet" type="text/css">
<%@ include file="/WEB-INF/jsp/imssubheader.jsp" %>

<form:form name="submitForm" action="imsappointment.html" method="POST" commandName="appointment">

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
<table width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="1" rules="none">
		<tr>
			<td class="table_title" ><spring:message code="ims.pcd.imsappointment.001"/></td>
		</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<c:if test="${ims_direct_sales eq true and bomsalesuser.salesType eq 'Door Knocked'}">   
			<tr>
				<td  valign="top" align="left"><spring:message code="ims.pcd.imsappointment.002"/></td>
				<td align="left" >
					<form:radiobutton path="waiveCoolingOffPeriod" value="Y"/> Waive											
					&nbsp;&nbsp;&nbsp;&nbsp;
					<form:radiobutton path="waiveCoolingOffPeriod" value="N"/> Not waive
				</td>
				<td>&nbsp;</td>	
			</tr>	
		</c:if> 
		<tr id="estsrdcols">
			<th align="left"><spring:message code="ims.pcd.imsappointment.003"/></th>
			<th align="left">${appointment.estimatedSrd} (Ref: ${appointment.estSrdReason })</th>
			<td>&nbsp;</td>
		</tr>
		<tr id="bookcols">
        	<td align="left"><spring:message code="ims.pcd.imsappointment.004"/></td>
			<td align="left">
				<form:input path="serialNum" readonly="true"/>  
			</td>
		</tr>	
		<tr id="targetdatecols">
			<td align="left">
				<spring:message code="ims.pcd.imsappointment.005"/>
			</td>
			<td align="left">				
				<input id="instdate" value="${appointment.targetInstallDate }"  readonly="true"/>          
			</td>
			<form:hidden path="targetInstallDate"/>
			<form:hidden path="giftCommDate"/>
			<td>&nbsp;</td>
		</tr>
		<tr id="timeslotcols">
			<td align="left"><spring:message code="ims.pcd.imsappointment.006"/></td>
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
				&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <a href="#" class="nextbutton" onclick="timeConfirm()"><spring:message code="ims.pcd.imsappointment.007"/></a>
			</td>			
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td valign="top" align="left"><spring:message code="ims.pcd.imsappointment.008"/></td>
			<td colspan="2">
				<form:textarea path="bmoRmk" rows="3" cols="40" readonly="true"/>				
			</td>
		</tr>		
		<tr id="commcols1">
			<td align="left">
				<spring:message code="ims.pcd.imsappointment.009"/>
			</td>
			<td align="left">				
				<form:input path="targetCommDate" />
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr id="commcols2">
			<td align="left">
				<spring:message code="ims.pcd.imsappointment.010"/>
			</td>
		</tr>
		<tr>
			<td align="left">
				<spring:message code="ims.pcd.imsappointment.011"/>
			</td>
			<td align="left">				
				<form:input path="instContactName" maxlength="25" onkeyup="keyUpOnInstContactName()"/>
				<span id="instContactNameError" class="error" style="display: none"><spring:message code="ims.pcd.imsappointment.020"/></span>
				<span id="instContactNameVoid" class="error" style="display: none"><spring:message code="ims.pcd.imsappointment.021"/></span>
				<span id="instContactNameNotEng" class="error" style="display: none"><spring:message code="ims.pcd.imsappointment.022"/></span>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="left">
				<spring:message code="ims.pcd.imsappointment.012"/>
			</td>
			<td align="left">
				<form:input path="instContactNum" maxlength="8" />
				<span id="instContactNumError" class="error" style="display: none"><spring:message code="ims.pcd.imsappointment.014"/></span>
				<span id="instContactNumVoid" class="error InstNumVoid" style="display: none"><spring:message code="ims.pcd.imsappointment.019"/>
				Please enter phone number or</span>
			</td>
			<td>&nbsp;</td>
		</tr>
				<tr>
			<td align="left">
				<spring:message code="ims.pcd.imsappointment.013"/>
			</td>
			<td align="left">			
				<form:input path="instSmsNum" maxlength="8" />
				<span id="instSmsNumError" class="error" style="display: none"><spring:message code="ims.pcd.imsappointment.msg.011"/></span>
				<span id="instSmsNumVoid" class="error InstNumVoid" style="display: none"><spring:message code="ims.pcd.imsappointment.msg.014"/></span>
			</td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td valign="top" align="left">
			<spring:message code="ims.pcd.imsappointment.018"/>
			</td>
			<td align="left">
				<form:textarea path="additionRemarks" rows="3" cols="40" />				
			</td>
			<td align="left">&nbsp;</td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="additionRemarks" cssClass="error" /></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td align="left" class="error">
				<span id="remarkError" style="display: none"><spring:message code="ims.pcd.imsappointment.msg.017"/></span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td align="left" class="error">
				<span id="remarkError2" style="display: none"><spring:message code="ims.pcd.imsappointment.018"/></span>
			</td>
			<td></td>
		</tr>

		<!--
		<tr>
			<td colspan ="3" align="left"> 
				<div id="warning_hos" class="ui-widget" style="display: none">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="warning_login_msg">
							<strong>PH/ HOS estate list without evening appointment (PON Acq order)</strong>
							<table width="300">								
								<tr>
									<td></td>
									<td><strong>Estate</strong></td>
									<td></td>
								</tr>												
								<tr>
									<td><strong>HK</strong></td>
									<td><spring:message code="ims_ext_ph_hos_en_1" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_1" text="" /></td>
								</tr>
								<tr><td colspan="3">&nbsp;</td></tr>								
								<tr>
									<td><strong>NT</strong></td>
									<td><spring:message code="ims_ext_ph_hos_en_2" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_2" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_3" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_3" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_4" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_4" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_5" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_5" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_6" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_6" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_7" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_7" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_8" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_8" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_9" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_9" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_10" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_10" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_11" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_11" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_12" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_12" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_13" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_13" text="" /></td>
								</tr>
								<tr>
									<td></td>
									<td><spring:message code="ims_ext_ph_hos_en_14" text="" /></td>
									<td><spring:message code="ims_ext_ph_hos_cn_14" text="" /></td>
								</tr>
							</table>							
						</div>						
					</div>
				</div>
				<div id="warning_pri" class="ui-widget" style="display: none">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="warning_login_msg">							
							<table border="1">								
								<tr>
									<td><strong>Installation Address</strong></td>
									<td><strong>SRD Leadtime</strong></td>
									<td><strong>Appointment Date & Timeslot</strong></td>
									<td><strong>Sunday & Public Holiday</strong></td>
								</tr>
								<tr valign="top">
									<td><spring:message code="ims_ext_private_en_1" text="" /></td>
									<td>=/ > T+6</td>
									<td>
										Mon - Fri (excluding PH)<br/>
										AM (9-13)<br/>
										PM (14-18)<br/>
										WD (whole date 9-18)<br/>
										<br/>
										Sat (excluding PH)<br/>
										AM (9-13)
									</td>
									<td>Not available</td>
								</tr>
								<tr valign="top">
									<td>
										<spring:message code="ims_ext_private_en_2" text="" /><br/>
										<spring:message code="ims_ext_private_en_3" text="" /><br/>
										<spring:message code="ims_ext_private_en_4" text="" /><br/>
										<spring:message code="ims_ext_private_en_5" text="" /><br/>
										<spring:message code="ims_ext_private_en_6" text="" /><br/>
									</td>
									<td>=/ > T+ 9</td>
									<td>
										Mon - Fri (excluding Sat. Sun & PH)<br/>
										AM (9-13)<br/>
										1) ONLY AM session (9-13 to be made)<br/>
										2) Allowed working hour for Monday to Friday: 10:00 -17:00<br/>
										(Order appointment no later than 2pm)<br/>
									</td>
									<td>Not available</td>
								</tr>
							</table>							
						</div>						
					</div>
				</div>				
			</td>
		</tr>
		-->			
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
        	<td colspan="1" align="right" width="55%">
        		<spring:message code="ims.pcd.imsappointment.019"/> 		
        		<form:select path="suspendCd">
        			<form:option value=""></form:option>
        			<form:options items="${suspendlist}" itemLabel="value" itemValue="key"/>
				</form:select>
       		</td>
       		<td align="right" width="10%">
       			<a href="#" class="nextbutton" onclick="submitSuspend()"><spring:message code="ims.pcd.imsappointment.023"/> </a>
    		</td>
       	</tr>
       	<tr><td><br></td></tr>
       	<tr id="canceltr">
			<td>&nbsp;</td>
        	<td colspan="1" align="right" width="55%">
        		<spring:message code="ims.pcd.imsappointment.024"/>
        		<form:select path="cancelCd">
        			<form:option value=""></form:option>
        			<form:options items="${cancellist}" itemLabel="value" itemValue="key"/>
				</form:select>
       		</td>
       		<td align="right" width="10%">
       			<a href="#" class="nextbutton" onclick="submitCancel()">	<spring:message code="ims.pcd.imsappointment.025"/> </a>
    		</td>
       	</tr>
       	<tr><td><br></td></tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="right">
				<div>
					<a href="#" class="nextbuttonS" onclick="formSubmit()">	<spring:message code="ims.pcd.imsappointment.026"/> </a>
				</div>				
			</td>
		</tr>
		</table>
	</td>
   </tr>
</table>
<form:hidden path="submitTag"/>
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>