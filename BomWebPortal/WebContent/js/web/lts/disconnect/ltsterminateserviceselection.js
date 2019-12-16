var ltsterminateserviceselection = function() {
	
	function showTPInfo(){
		if ($("#thirdAppCheck").is(':checked')){
			$(".TPInfo").fadeIn();
		}else{
			$(".TPInfo").hide();
		}
	}

	function isNaChecked(){
		if ($("#na").is(':checked')){
			$(".deceasedInd").hide();
		}else{
			$(".deceasedInd").fadeIn();
		}
	}
	function handleCallingCard(){
		if ($("#callingCardInd").is(':checked')){
			$(".callingCardDetailsBlk").fadeIn();
		}
		else{
			$(".callingCardDetailsBlk").hide();
		}
	}
	function brCheck() {
		$("#thirdAppCheck").attr('checked', $("input[name='thirdParty']").is(':checked'));
		showTPInfo();
	}
	
	function checkLostModem(){
		if ($("#lostModem").is(':checked')){
			$("#lostModemUsageInd").attr('disabled', false);
		}else{
			$("#lostModemUsageInd").attr('disabled', true);
		}
	}
	
	function initializeForm(){
		$("#waiveDFormReason").attr("disabled",false);
		$("#dFromSn").attr("disabled",false);
		
		if ($("#waiveDFormReason").find('option:selected').text() != '--SELECT--' ){
			$("#dFormSn").attr("disabled",true);
		}
		if($("#dFormSn").val() != "" && $("#dFormSn").val() != " ")
		{
			$("#waiveDFormReason").attr("disabled",true);
		}
		
		if($("#terminateHDTVind").val() != "true"){
			$("#removeNowtv").attr("disabled", true);
		};
		if($("#terminatePCDind").val() != "true"){
			$("#removeBoardband").attr("disabled", true);
		};
	}
	
	function submitForm(action) {
		$('input[name="formAction"]').val(action);
		$('#ltsTerminateServiceSelectionForm').submit();
	}
	
	function isDeceasedCase() {
		return ($("#disconnectReason").val() == '4012');
	}
	
	function deceaseCheck() {
		
		if(isDeceasedCase()){
			if(splitAcc){
				$("#splitedAccAlert").show();
				$("#submitButton").hide();
			}
			
			$("input[type=radio][value=REMOVE]").click();
			//$("input[type=radio][value=RETAIN]").attr("disabled", true);
			
			$('#deceasedType').removeAttr('disabled');
			
			if ($('#deceasedType').val() == 'N' || $('#deceasedType').val() == 'S' || $('#deceasedType').val() == 'I') {
				$("#thirdAppCheck").attr('checked', true);
				$("#thirdAppCheck").attr('disabled', true);
			}
			else {
				$("#thirdAppCheck").attr('checked', false);
				$("#thirdAppCheck").attr('disabled', false);
			}
			
			
			showTPInfo();
			$(".fiveNa").hide();
		}
		else {
			if(splitAcc){
				$("#splitedAccAlert").hide();
				$("#submitButton").show();
			}
			
			//$("input[type=radio][value=RETAIN]").removeAttr("disabled");
			$.each(defaultValueMap, function(v){ 
				$(v).removeAttr("disabled");
				$(v+"[value="+defaultValueMap[v]+"]").attr("selected", true).click();
			});
			
			$('#deceasedType').attr('disabled', true);
			$('#deceasedType').val('');
			$(".fiveNa").show();
		}
		
	}
	
	function resetAppDatePicker(){

	$('#appDate').datepicker("destroy");
	$('#appDate').datepicker({
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate: "-120D", maxDate: "0D", //Y is year, M is month, D is day  
		yearRange: "0:+100" //the range shown in the year selection box
	});
	}
	
	function checkThirdPartyRelationship(){
		if($("#thirdRelationship").val() == 'NP'){
			$("#thirdOtherRelationship").show();
		}else{
			$("#thirdOtherRelationship").hide();
		}
	}
	
	
	
	function checkIfAllSrvTerminate(){
		var hasOtherSrvSameAcc = $("#hasOtherSrvSameAcc").val() == 'true';
		var isAllIddTerm = $("[class^=iddRadio].isSameAcc").size() == 0 || $("[class^=iddRadio][value=RETAIN].isSameAcc:checked").size() == 0;
		var isAllCallCardTerm = $("#callingCardDetailsHandling").size() == 0 || 
									($("#callingCardDetailsHandling").val() != undefined && $("#callingCardDetailsHandling").val() == 'DISCONNECT' && $("#callingCardSameAcct").val() == 'true');
		var hasBillableAcct = $("#hasBillableAcct").val() == 'true';
		
		//check valid actions and set default
		if(isAllIddTerm && isAllCallCardTerm && !hasOtherSrvSameAcc){
			if(hasBillableAcct){
				$(".actionRemoveColumn").attr('colspan', '1');
//				$("#actionHeader").attr('colspan', '2');
				$(".actionNonRemoveColumn").show();

				$("[class^=callPlanRadio]").attr('disabled', false);
				for(var i = 0; i < $("#callPlanRowCount").val(); i++){
					if($("#dca"+i).val() == $("#srvAcctNum").val()){
						$("#hasOtherSrvInCurAccAction" + i ).hide();
						$("#noOtherSrvInCurAccAction" + i ).show();
						$(".callPlanRadio" + i + "[value!=REMOVE]").attr('checked', false);
						
						//default to change dca if none option selected
						if($(".callPlanRadio" + i + "[value=REMOVE]:checked").size() == 0){
							$(".callPlanRadio" + i + "[value=CHG_DCA]").attr('checked', true);
						}
						
						//default to remove if no change dca option
						if($("#chgDcaRadio"+i).val() == undefined){
							$(".callPlanRadio" + i + "[value=REMOVE]").attr('checked', true);
						}
						
					}else{
						$("#hasOtherSrvInCurAccAction" + i ).show();
						$("#noOtherSrvInCurAccAction" + i ).hide();
					}
				}
			}else{
				//$("#actionHeader").attr('colspan', '1');
				$(".actionRemoveColumn").attr('colspan', '2');
				$(".actionNonRemoveColumn").hide();
				$(".noOtherSrvInCurAccAction").hide();
				$(".hasOtherSrvInCurAccAction").hide();

				$("[class^=callPlanRadio][value!=REMOVE]").attr('disabled', true).attr('checked', false);
				$("[class^=callPlanRadio][value=REMOVE]").attr('checked', true);
				$("[id^=retainCallPlanPenaltyHanding]").hide();
				$("[id^=removeCallPlanPenaltyHanding]").show();
			}
		}else{
			$(".actionRemoveColumn").attr('colspan', '1');
//			$("#actionHeader").attr('colspan', '2');
			$(".actionNonRemoveColumn").show();
			$(".noOtherSrvInCurAccAction").hide();
			$(".hasOtherSrvInCurAccAction").show();
			$("[class^=callPlanRadio]").attr('disabled', false);
			$("[class^=callPlanRadio][value!=REMOVE]").attr('checked', false);
			
			for(var i = 0; i < $("#callPlanRowCount").val(); i++){
				if($(".callPlanRadio" + i + "[value=REMOVE]:checked").size() == 0 || $("#chgDcaRadio"+i).val() == undefined){
					$(".callPlanRadio" + i + "[value=RETAIN]").attr('checked', true);
				}
			}
			
		}
	}
	
	function checkIfAllSrvTerminateAjax(){
		
		if($("[id^=idd0060remove]").length == 0 && $("#callingCardDetailsHandling").length == 0){
			return
		}
		
		var idd0060remove = [];
		$("[id^=idd0060remove]:checked").each(function(i, e){
			idd0060remove.push($(e).attr('id').split(',')[1]);
		});
		
		var removeCallingCardAcct = $("#callingCardDetailsHandling").val() == 'DISCONNECT' ?$("#callingCardAcctList").val(): '';
		
		$.ajax({
			url : 'ltsterminateserviceajax.html',
			data : {
				acctNum : 'ALL',
				rmvSrv : idd0060remove.join(','),
				rmvCallingCardAcct : removeCallingCardAcct
			},
			type : 'POST',
			dataType : 'JSON',
			timeout : 5000,
			async : false,
			error : function() {
				 alert("Error occured. Please try again later.");
			},
			complete : function() {
				 $.unblockUI(); 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			},
			success : function(msg) {
				var tempJsonContent = JSON.parse(JSON.parse(msg).json);
				tempJsonContent.forEach(function(e){
					if(e.action == 'RETAIN' || e.action == 'CHANGE_DCA'){
//						$("#actionHeader").attr('colspan', 2);
						$(".actionRemoveColumn."+ e.acctNum).attr('colspan', 1);
						$(".actionNonRemoveColumn." + e.acctNum).show();

						if(e.action == 'CHANGE_DCA'){
							$(".noOtherSrvInCurAccAction."+ e.acctNum).show();
							$(".hasOtherSrvInCurAccAction."+ e.acctNum).hide();
							$(".removeRadio." + e.acctNum).attr('checked', true);
							$(".chgDcaRadio." + e.acctNum).attr('checked', true);
						}

						if(e.action == 'RETAIN'){
							$(".noOtherSrvInCurAccAction."+ e.acctNum).hide();
							$(".hasOtherSrvInCurAccAction."+ e.acctNum).show();
							$(".removeRadio." + e.acctNum).attr('checked', true);
							$(".retainRadio." + e.acctNum).attr('checked', true);
						}

					}
					if(e.action == 'REMOVE'){
//						$("#actionHeader").attr('colspan', 2);
						$(".actionRemoveColumn."+ e.acctNum).attr('colspan', 2);
						$(".actionNonRemoveColumn." + e.acctNum).hide();
						$(".removeRadio."+e.acctNum).attr('checked', true);
					}
				});
			}
		});
		
	}
	
	
	function ffpTableRefreshByAction(){
		for(var i = 0; i < $("#callPlanRowCount").val(); i++){
			if($("#chgDcaRadio"+i+":checked").val() != undefined){
				$("#newDcaDiv" + i).show();
				$("#noNewDcaDiv" + i).hide();
			}else{
				$("#newDcaDiv" + i).hide();
				$("#noNewDcaDiv" + i).show();
			}

			if($("#removeCallPlanRadio"+i+":checked").val() != undefined){
				$("#retainCallPlanPenaltyHanding" + i).hide();
				$("#removeCallPlanPenaltyHanding" + i).show();
			}else{
				$("#retainCallPlanPenaltyHanding" + i).show();
				$("#removeCallPlanPenaltyHanding" + i).hide();
			}
			
		}
	}
	
	function actionPerform() {
		
		brCheck();
		initializeForm();
		deceaseCheck();
		resetAppDatePicker();
		checkThirdPartyRelationship();
		checkLostModem();
		checkIfAllSrvTerminate();
		checkIfAllSrvTerminateAjax();
		//check actions to display other fields
		ffpTableRefreshByAction();
//		alterPenalty();
		
		$("#thirdAppCheck").change(function(event){
			showTPInfo();
		});
		
		$("#dFormSn").change(function(event){
			
			if ($(this).val() ==''){
				$("#waiveDFormReason").attr("disabled",false);
			}
			else{
				$("#waiveDFormReason").attr("disabled",true);
			}
		});
		$("#waiveDFormReason").change(function(event){
			
			if ($(this).find('option:selected').text() == '--SELECT--' ){
				$("#dFormSn").attr("disabled",false);
			}
			else {
				$("#dFormSn").attr("disabled",true);
			}
			
		});
		
		$('#deceasedType').change(function(event){
			if ($(this).val() == 'N' || $(this).val() == 'S' || $(this).val() == 'I') {
				$("#thirdAppCheck").attr('checked', true);
				$("#thirdAppCheck").attr('disabled', true);
			}
			else {
				$("#thirdAppCheck").attr('checked', false);
				$("#thirdAppCheck").attr('disabled', false);
			}
			showTPInfo();
		});
		
		$('#dReason').change(function(){
		    if ($('option:selected', this).val() == 4012) {
		    	$(".fiveNa").hide();
		    }
		    if ($('option:selected', this).val() != 4012) {
		    	$(".fiveNa").fadeIn();
		    }
		});
		$("#na").change(function(event){
			isNaChecked();
		});
		$("#callingCardInd").change(function(event){
			handleCallingCard();
		});
		//hover states on the static widgets
		$('#dialog_link, ul#icons li').hover(
			function() { $(this).addClass('ui-state-hover'); }, 
			function() { $(this).removeClass('ui-state-hover'); }
		);
		$('.callCard').click(function () {
	        checkedState = $(this).attr('checked');
	        $('.callCard:checked').each(function () {
	            $(this).attr('checked', false);
	        });
	        $(this).attr('checked', checkedState);
	    });

		$('#clearThirdParty').click(function(event) {
			$('input[name="thirdContactNum"]').val("");
			$('input[name="thirdRelationship"]').val("");
			$('input[name="thirdIdVerify"]').val("");
			$('input[name="thirdDocNum"]').val("");
			$('input[name="thirdDocType"]').val("");
			$('input[name="thirdLastName"]').val("");
			$('input[name="thirdFirstName"]').val("");
			$('input[name="thirdTitle"]').val("");
		});

		$("#thirdRelationship").change(function(event){
			checkThirdPartyRelationship();
		});
		
		$("#lostModem").change(function(event){
			checkLostModem();
		});
		
		$("[class^=iddRadio]").change(function(event){
			checkIfAllSrvTerminate();
			checkIfAllSrvTerminateAjax();
			
			//check actions to display other fields
			ffpTableRefreshByAction();
		});
		
		$("#callingCardDetailsHandling").change(function(event){
			checkIfAllSrvTerminate();
			checkIfAllSrvTerminateAjax();
			
			//check actions to display other fields
			ffpTableRefreshByAction();
		});

		$("#disconnectReason").change(function(event){
			submitForm("UPDATE_LIST");	
		});
		
		$("a#submitBtn").click(function(event) {
			submitForm("SUBMIT");
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