var ltsacqpersonalinfo = function () {

	$(document).ready(function(){
		$("#personalInfo").hide();});
		
		
	function setThirdParty(){
		if($("[name='thirdPartyInd']:checked").val() == "true"){
			$("#personalInfo").show();
			$(".thirdPartyApp").show();
			if(isDs){
				custSearch(); 
				$('#docNum').attr("readonly",false);
			    $('#familyName').attr("readonly",false);
			    $('#givenName').attr("readonly",false);
			    $('#documentType').attr("readonly",false);
			}
			if($("#title").val() == ""){
				resetOptionTitle();
			}
			displayPersonalInformationCollectionStatement();
		}
		else{
			if($("[name='thirdPartyInd']:checked").val() == "false"){
				$("#personalInfo").show();
				$(".thirdPartyApp").hide();
				if(isDs){
					custSearch();
					$('#docNum').attr("readonly",false);
				    $('#familyName').attr("readonly",false);
				    $('#givenName').attr("readonly",false);
				    $('#documentType').attr("readonly",false);
				}
				if($("#title").val() == ""){
					resetOptionTitle();
				}
				displayPersonalInformationCollectionStatement();
			}
		}
	};
	
	function setSecondDel(){
		if($("[name='secondDelDummyDoc']:checked").val() == "true"){
			$('.secondDel').show();
		}else {
			$('.secondDel').hide();	
		}
	};
	
	function checkBr(){
		if($("[name='documentType']").val() == 'BS'){
			$('.brField').show();
			$('.personField').hide();
			$('#thirdParty1').attr("checked",true);
			$('input[name=thirdPartyInd]').attr("disabled",true);
		}else{
			$('.brField').hide();
			$('.personField').show();
			if(allowThirdParty){
				$('input[name=thirdPartyInd]').attr("disabled",false);
			}else{
				$('#thirdParty2').attr("disabled",false);
				$('#thirdParty2').attr("checked",true);
			}
			
		}
		setThirdParty();
	}
	
	if($('input[name=secondDelDummyDoc]:checked').val() == true){
		$('.secondDel').show();
	}else $('.secondDel').hide();
	
	
	function setEnable(){

		$('#docNum').attr("disabled",false);
	    $('#compnayName').attr("disabled",false);
	    $('#familyName').attr("disabled",false);
	    $('#givenName').attr("disabled",false);
	    $('#dateOfBirth').attr("disabled",false);
	    $('#documentType').attr("disabled",false);
		$('#titleDiv').show();
		$('#titleLabel').hide();
	};		  
	function setDisable(){
		$('#docNum').attr("disabled",true);
	    $('#compnayName').attr("disabled",true);
	    $('#familyName').attr("disabled",true);
	    $('#givenName').attr("disabled",true);
	    $('#dateOfBirth').attr("disabled",true);
	    $('#documentType').attr("disabled",true);
		$('#titleDiv').hide();
		$('#titleLabel').show();
	};
	
	function custSearch(){
		var docNum = $("#docNum").val();
		var docType = $('#documentType').val();
		var sbuid = $('input[name="sbuid"]').val();
		$.ajax({
			url : "ltscustacctlookup.html?sbuid=" + sbuid,
			type : 'POST',
			data : "docType=" + docType + "&docNum=" + docNum + "&sales=" + salesName + "&reqType=" + "FIND_CUST" ,
			success : function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.state == 1){					
					if(!isDs){
						$('#alertUpdContactMsg').val('');
						$('#alertUpdEmailMsg').val('');
						$('#alertUpdMobileNoMsg').val('');
						$("#compnayName").val(obj.companyName);
						isAlertUpdContactMsg = true;
						
						$("#title").empty();
						if(obj.title != null){
							if(docType == "HKID" || docType == "PASS"){						
								$("#title").append($("<option></option>").attr("value", obj.title).text(obj.title));
							}
						}else{
							if(docType == "HKID" || docType == "PASS"){	
								resetOptionTitle();
							}
						}
						
						$("#familyName").val(obj.lastName);
						$("#givenName").val(obj.firstName);
						$("#contactEmail").val(obj.contactEmail);
						$("#mobileNo").val(obj.mobileNo);
						$("#oldContactEmail").val(obj.contactEmail);
						$("#oldMobileNo").val(obj.mobileNo);
						$("#oldContactEmailDate").val(obj.contactEmailDate);
						$("#oldMobileNoDate").val(obj.mobileNoDate);
						$("#contactUpdAlert").val(obj.contactUpdAlert);
											
//						if(obj.alertContactMsg != null || obj.contactEmail == null || obj.mobileNo == null){
//							if(obj.alertContactMsg != null){
//								$("#alertUpdContactMsg").val(alertUpdContact);
//								//var contactMsg = alertUpdContact;
//								//alert(contactMsg);
//							}else{
//								$("#alertUpdContactMsg").val(alertUpdContact);
//								//var contactMsg = alertContactBlank;
//								//alert(contactMsg);
//							}
//							
//						}
						
						if(obj.alertUpdEmailMsg != null || obj.contactEmail == null){
							$("#alertUpdEmailMsg").val(alertUpdContact);
							$("#alertUpdContactMsg").val(alertUpdContact);
						}
						
						if(obj.alertUpdMobileNoMsg != null || obj.mobileNo == null){
							$("#alertUpdMobileNoMsg").val(alertUpdContact);
							$("#alertUpdContactMsg").val(alertUpdContact);
						}
	
						$("#companyName").val(obj.companyName);
						
						$('#wipMsg').val('');
						if(isFrontline && !isRetail){
							var wipInds = ['P', 'W', 'Y'];
							if(obj.wip != null &&  $.inArray(obj.wip, wipInds) != -1 ){ //wipInds.indexOf(obj.wip) != -1){
								var wipMsg = cannotWIPprofile;
								alert(wipMsg);
								$('#wipMsg').val(wipMsg);
							}
						}
					    
						if(obj.custSpecialRemarks != null){
							alert(specialCustomerRemark+ obj.custSpecialRemarks);
						}
						
						if(obj.blacklist){
							alert(customerBlacklisted);
						}
						
						if($("#warningCustomerSpecialRemarkMessage").val() != null && $("#warningCustomerSpecialRemarkMessage").val() != ''){
							alert($("#warningCustomerSpecialRemarkMessage").val());
							$("#warningCustomerSpecialRemarkMessage").val("");	
							$("#warningCustomerSpecialRemarkMessage").empty();
						}
						
						
						$("#disable").val("true");
						$("#familyName").attr('readonly', true);
						$("#givenName").attr('readonly', true);
						$('input[name=createNewCust]').val(false);
						
						$('#documentType').attr("disabled",false);					
						$('#docNum').attr("disabled",false);
						$('#documentType').attr("readonly",false);
						$('#docNum').attr("readonly",false);
						
						$('#dateOfBirth').val(obj.dateOfBirth);
					}
					
					
					if(isDs){
						$('#wipMsg').val('');
						$('#alertUpdContactMsg').val('');
						$('#alertUpdEmailMsg').val('');
						$('#alertUpdMobileNoMsg').val('');
						$("#oldContactEmailDate").val(obj.contactEmailDate);
						$("#oldMobileNoDate").val(obj.mobileNoDate);
						$("#contactUpdAlert").val(obj.contactUpdAlert);
						isAlertUpdContactMsg = true;
						var wipInds = ['P', 'W', 'Y'];
						if(obj.wip != null &&  $.inArray(obj.wip, wipInds) != -1 ){ //wipInds.indexOf(obj.wip) != -1){
							var wipMsg = warningWIPprofile;
							alert(wipMsg);
							$('#wipMsg').val(wipMsg);
						}
						if(obj.custSpecialRemarks != null){
							alert(specialCustomerRemark+ obj.custSpecialRemarks);
						}
						
						if(obj.blacklist){
							alert(customerBlacklisted);
						}
					}
				
					$("#custNum").val(obj.custNum);
					if($("#warningCustomerNotMatchMessage").val() != null && $("#warningCustomerNotMatchMessage").val() != ''){
						alert($("#warningCustomerNotMatchMessage").val());
						$("#warningCustomerNotMatchMessage").val("");	
						$("#warningCustomerNotMatchMessage").empty();
					}
					
					displayPersonalInformationCollectionStatement();
					
				}else if(obj.state == 0){
					setEnable();
					resetOptionTitle();
					if(!isDs){
					alert(noCustomerInfoWithDocumentID1 + docNum + noCustomerInfoWithDocumentID2);
					
					$('input[name=familyName]').val("");
					$('input[name=givenName]').val("");
					$("#familyName").attr('readonly', false);
					$("#givenName").attr('readonly', false);
					
					$('#documentType').attr("disabled",false);
					$('#docNum').attr("disabled",false);
					$('#documentType').attr("readonly",false);
					$('#docNum').attr("readonly",false);
					
					$("#custNum").val("");
					$('#wipMsg').val('');
					$('#alertUpdContactMsg').val('');
					$('#alertUpdEmailMsg').val('');
					$('#alertUpdMobileNoMsg').val('');
					}
					$('input[name=createNewCust]').val(true);
					displayPersonalInformationCollectionStatement();
				}else if(obj.state == 2 && !isDs){
					alert(invalidDocumentId+"[" + $('#documentType').val() + "]");
				}
			},
			complete : function() {
				 $.unblockUI(); 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
				 $("#errorMsg").val('');
			}
		});
	}
	
	function custVerifi(){
		var docNum = $("#docNum").val();
		var docType = $('#documentType').val();
		var firstName = $('#givenName').val();
		var lastName = $('#familyName').val();
		var basketType = $('#basketType').val();
		var sbuid = $('input[name="sbuid"]').val();
		var alertMessage = null;
		$.ajax({
			url : "ltscustacctlookup.html?sbuid=" + sbuid,
			type : 'POST',
			async: false, 
			data : "docType=" + docType + "&docNum=" + docNum + "&firstName=" + firstName + "&lastName=" + lastName + "&basketType=" + basketType + "&reqType=" + "VALID_CUST" ,
			success : function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.state == 1){
					$("#isMatchWithBomName").val(obj.isMatchWithBomName);
					
					if(!obj.isMatchWithBomName){
						if($('#basketType').val()=="EYE"){
							if(!obj.isMatchWithLtsName){
								if(!obj.isMatchWithImsName){
									alertMessage = LTSandIMSnameNOTmatch;
								}else{
									alertMessage = LTSnameNOTmatch;
								}								
							}else{
								if(!obj.isMatchWithImsName){
									alertMessage = IMSnameNOTmatch;									
								}
							}
							if(confirm(alertMessage)){
								if($("#documentType").val() == 'BS' && $("jobName") == ''){
									$("#errorMsg").val(missingUser);
								}
								if($("#errorMsg").val() != ''){
									alert($("#errorMsg").val());
									return;
								}
								submitForm();
							}
						}else {
							if($('#basketType').val()!="EYE"){
								if(confirm(LTSnameNOTmatch)){
									if($("#documentType").val() == 'BS' && $("jobName") == ''){
										$("#errorMsg").val(missingUser);
									}
									if($("#errorMsg").val() != ''){
										alert($("#errorMsg").val());
										return;
									}
									submitForm();
								}
							}
						}
					}else{
						if($("#documentType").val() == 'BS' && $("jobName") == ''){
							$("#errorMsg").val(missingUser);
						}						
						
						if($("#errorMsg").val() != ''){
							alert($("#errorMsg").val());
							return;
						}
						
						submitForm();
					}
				}else if(obj.state == 2){
					alert(invalidDocumentNo);
				}
			},
			complete : function() {
				 $.unblockUI(); 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
				 $("#errorMsg").val('');
			}
		});
	}
	
	function displayPersonalInformationCollectionStatement(){
		if( $("#docNum").val() == '' || $("#docNum").val() == null){
			$("#PICS_CONTENT").hide();
		}
		else{
			$.ajax({
				url : "ltsacqcustomerinfolkup.html?cust_num=" + $("#custNum").val(),
				type : 'POST',
				success : function(data){
					var obj = jQuery.parseJSON(data);
					if( obj.result ) {
						$("#PICS_CONTENT").show();
						$("#displayPICS").val(true);
						$("#ltsStatus").val(obj.ltsStatus);
						$("#iddStatus").val(obj.iddStatus);
					}else {
						$("#PICS_CONTENT").hide();
						$("#displayPICS").val(false);
					}
				},
				complete : function() {
					 $.unblockUI(); 
				},
				beforeSend : function() {
					 $.blockUI({ message: null }); 
					 $("#errorMsg").val('');
				}
			});
		}
	}
	
	
	
	function resetOptionDocType(){
		$("#documentType").empty();
		$("#documentType").append($("<option></option>").attr("value", "").text(typeInSelectVal));
		$("#documentType").append($("<option></option>").attr("value", "HKID").text(hkidVal));
		$("#documentType").append($("<option></option>").attr("value", "PASS").text(passVal));
		$("#documentType").append($("<option></option>").attr("value", "BS").text(hkbrVal));
		$("#documentType").attr("readonly",false);
		$("#documentType").attr("disabled",false);
	}
	
	function resetOptionTitle(){
		$("#title").empty();
		$("#title").append($("<option></option>").attr("value", "").text(titleInSelectVal));
		$("#title").append($("<option></option>").attr("value", "Mr").text(mrVal));
		$("#title").append($("<option></option>").attr("value", "Ms").text(msVal));
		$("#title").attr("readonly",false);
		$("#title").attr("disabled",false);
	}
	
	function setDocFieldEnableForDummyDoc(){
		if($('input[name=dummyDoc]:checked').val() == "true"){
			$("#documentType").attr("disabled",false);
			$("#docNum").attr("disabled",false);
			$("#documentType").attr("readonly",false);
			$("#docNum").attr("readonly",false);
		}else {
			if($("#custNum").val() != ''){
				//$("#documentType").attr("disabled",true);
				//$("#docNum").attr("disabled",true);
			}
		}
	};
	
	function setupDobDatepicker(){
		$('#dateOfBirth').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			minDate : "-100Y",
			maxDate : "-18Y", //Y is year, M is month, D is day 
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			yearRange: "-100:-18" //the range shown in the year selection box
		});
	}
	
	function setupRdPtyDobDatepicker(){
		$('#thirdPartyDateOfBirth').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			minDate : "-64Y",
			maxDate : "-18Y", //Y is year, M is month, D is day 
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			yearRange: "-64:-18" //the range shown in the year selection box
		});
	}
	
	
	function submitForm(){		
		$("#title").attr('disabled', false);
		$('input[name=thirdPartyInd]').attr("disabled",false);
		$("#personalinfoform").submit();
	}
	
	function actionPerform() {

		if($("#acctNumNotMatchMsg").val() != null && $("#acctNumNotMatchMsg").val() != ''){
			alert($("#acctNumNotMatchMsg").val());
			$("#acctNumNotMatchMsg").val("");	
			$("#acctNumNotMatchMsg").empty();
			resetOptionDocType();
	    }
		checkBr();
		setupDobDatepicker();
		setupRdPtyDobDatepicker();
		
		$("#documentType").change(function(){
			$("#docNum").val("");
			$("#compnayName").val("");
			$("#familyName").val("");
			$("#givenName").val("");
			$("#contactEmail").val("");
			$("#mobileNo").val("");		
			$("#oldContactEmail").val("");
			$("#oldMobileNo").val("");
			checkBr();
			resetOptionTitle();
			$("#warningCustomerSpecialRemarkMessage").val("");	
			$("#warningCustomerSpecialRemarkMessage").empty();
			$("#warningCustomerNotMatchMessage").val("");	
			$("#warningCustomerNotMatchMessage").empty();
			$('#alertUpdContactMsg').val("");
			$('#alertUpdContactMsg').empty();
			$('#alertUpdEmailMsg').val("");
			$('#alertUpdEmailMsg').empty();
			$('#alertUpdMobileNoMsg').val("");
			$('#alertUpdEmailMsg').empty();
		});
		
		$('input[name=docNum]').change(function(){
			if($("#docNum").val() != ''){
				custSearch();
			}

			if($("#docNum").val() == ''){
				$("#compnayName").val("");
				$("#title").val("");
				$("#familyName").val("");
				$("#givenName").val("");
				$("#contactEmail").val("");
				$("#mobileNo").val("");	
				$("#oldContactEmail").val("");
				$("#oldMobileNo").val("");
				resetOptionDocType();
				resetOptionTitle();
				$("#familyName").attr('readonly', false);
				$("#givenName").attr('readonly', false);
				displayPersonalInformationCollectionStatement();
				$("#warningCustomerSpecialRemarkMessage").val("");
				$("#warningCustomerSpecialRemarkMessage").empty();
				$("#warningCustomerNotMatchMessage").val("");	
				$("#warningCustomerNotMatchMessage").empty();
				$('#alertUpdContactMsg').val("");
				$('#alertUpdContactMsg').empty();
				$('#alertUpdEmailMsg').val("");
				$('#alertUpdEmailMsg').empty();
				$('#alertUpdMobileNoMsg').val("");
				$('#alertUpdEmailMsg').empty();
			}
		});
		
		$('input[name=thirdPartyInd]').change(function(){
			setThirdParty();
		});
		
		$('input[name=secondDelDummyDoc]').change(function(){
			setSecondDel();
		});
		
		$('input[name=dummyDoc]').change(function(){
			setDocFieldEnableForDummyDoc();
		});
		
		$("#submit").click(function(event) {

			event.preventDefault();
			if(isDs){
//				custVerifi();
				if(isAlertUpdContactMsg && ($("#contactEmail").val()=='' || $("#mobileNo").val()=='')){
					isAlertUpdContactMsg = false;
					$("#alertUpdContactMsg").val(alertUpdContact);
					$("#contactUpdAlert").val("Y");
					if(confirm($("#alertUpdContactMsg").val())){
						return;
					}else{
						custVerifi();
					}			
				}else{
					custVerifi();
				}
			}
			else{
				if($("#documentType").val() == 'BS' && $("jobName") == ''){
					$("#errorMsg").val(missingUser);
				}
				
				if($('#wipMsg').val() != ''){
					alert($("#wipMsg").val());
					return;
				}
				
				if($("#errorMsg").val() != ''){
					alert($("#errorMsg").val());
					return;
				}
//				if($("#alertUpdContactMsg").val() != '' && $("#contactEmail").val()==$("#oldContactEmail").val() && $("#mobileNo").val()==$("#oldMobileNo").val()){
				if(isAlertUpdContactMsg &&
						(($("#alertUpdEmailMsg").val() != '' && $("#contactEmail").val()==$("#oldContactEmail").val()) || ($("#alertUpdMobileNoMsg").val() != '' && $("#mobileNo").val()==$("#oldMobileNo").val()))){
					$("#contactUpdAlert").val("Y");
					if(confirm($("#alertUpdContactMsg").val())){
						isAlertUpdContactMsg = false;
						return;
					}else{
						submitForm();
					}			
				}else{
					submitForm();
				}
			}
		});
		
	    $(".toUpper").keyup(function(){
	    	$(this).val($(this).val().toUpperCase());
	    });
	    
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	    
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();

