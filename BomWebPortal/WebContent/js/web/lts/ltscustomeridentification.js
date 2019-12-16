var ltscustomeridentification = function () {

	function checkDummy(){
		if($("#dummyInd").is(':checked')){
			$("#docId").attr('readonly', false);
			$("#docType").attr('disabled', false);
		}else{
			$("#docId").attr('readonly', true);
			$("#docType").attr('disabled', true);
		}

		if($("#secDelDummyInd").is(':checked')){
			$("#secDelId").attr('readonly', false);
		}else{
			$("#secDelId").attr('readonly', true);
		}
	}
	
	function checkThirdParty(){
		if($("#thirdPartyInd").is(':checked')){
			$(".thirdPartyDiv").fadeIn();
		}else{
			$(".thirdPartyDiv").hide();
		}
	}
	
	function checkSecDelThirdParty(){
		if($("#secDelThirdPartyInd").is(':checked')){
			$(".secDelThirdPartyDiv").fadeIn();
		}else{
			$(".secDelThirdPartyDiv").hide();
		}
	}
	
	function setupDobDatepicker(){
		if ( $('#dateOfBirth').is('[readonly]') ) { 
			return false;
		}else{
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
	}
	
	
	function checkExistOptOutStatus() {
        if ($('input[name="existLtsPdpoStatus"]').val() == 'OIA'
                        || $('input[name="existLtsPdpoStatus"]').val() == 'OOP') {
                $('.updateOptoutStatus').hide();
        }
        else {
                $('.updateOptoutStatus').show();
        }

        $('.partialOptOut').hide();
	}

	function checkNewOptOutStatus() {
        if ($('#newLtsPdpoStatus').val() == 'OOP') {
                $('.partialOptOut').show();
        }
        else if ($('#newLtsPdpoStatus').val() == 'OIA') {
                $('.partialOptOut').hide();
                $('.pdpoMean').attr('checked', '');
                $('.optOutAll').attr('checked', '');
        }
        else if ($('#newLtsPdpoStatus').val() == '') {
                $('.partialOptOut').hide();
        }
	}
	
	function actionPerform() {

		checkDummy();
		setupDobDatepicker();
		checkThirdParty();
		checkSecDelThirdParty();
		checkExistOptOutStatus();
		checkNewOptOutStatus();
		
		$("#submit").click(function(event) {
			event.preventDefault();
			
			if(!isMsgPromptedFlag){
				if(isContactBlank){
					if(($("#customerOrgContactEmail").val() == '' && $("#customerContactEmail").val() == '')
						|| ($("#customerOrgContactMobileNum").val() == '' && $("#customerContactMobileNum").val() == '')){
						$("#updateContactMsgPrompted").val('Y');
						isMsgPromptedFlag = true;
						if(confirm(contactAlertUpdateMsg)){
							return;
						}
					}
				}else if((isContactEmailOverDue && $("#customerOrgContactEmail").val() == $("#customerContactEmail").val())
						|| (isContactMobileNumOverDue && $("#customerOrgContactMobileNum").val() == $("#customerContactMobileNum").val())){
						$("#updateContactMsgPrompted").val('Y');
						isMsgPromptedFlag = true;
						if(confirm(contactAlertUpdateMsg)){
							return;
						}
				}
			}
			
			$("#docType").attr('disabled', false);
			$("#dummyInd").attr('disabled', false);
			$("#secDelDocType").attr('disabled', false);
			$("#secDelDummyInd").attr('disabled', false);
			$("form#customeridentification").submit();
		});
		
		$("#dummyInd").click(function(event){
			checkDummy();
		});

		$("#secDelDummyInd").click(function(event){
			checkDummy();
		});		

		$("#thirdPartyInd").click(function(event){
			checkThirdParty();
		});
		
		$("#secDelThirdPartyInd").click(function(event){
			checkSecDelThirdParty();
		});
		
		$("#newLtsPdpoStatus").change(function(event){
			checkNewOptOutStatus();	
		});
		
		$("input[name='ltsPdpoOptOutAll']").change(function(event){
			if ($("input[name='ltsPdpoOptOutAll']").attr('checked')) {
				$('.pdpoMean').attr('checked', 'checked');
				$('.pdpoMean').attr('disabled', 'disabled');
			}
			else {
				$('.pdpoMean').removeAttr('disabled');
			}
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	        $(this).find(':input').removeAttr('disabled');
	    });
	    
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();