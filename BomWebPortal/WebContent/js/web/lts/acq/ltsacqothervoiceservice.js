var ltsothervoiceservice = function() {
	
	
	function promptRemoveFreeVas(vasName) {
		if (vasName != undefined && vasName != null && vasName != ""){
			alert(vasName + willBeRmMsg);
		}
	}
	
	function handle2ndDelEr() {
		if ($('input[name="secondDelEr"]:checked').val() == "true") {
			$('#secondDelBaCaDiv').show();
			$('#secondDelConfirmIaDiv').hide();
			$('input[name="secondDelConfirmSameIa"]').attr('checked', '');
		}
		else if ($('input[name="secondDelEr"]:checked').val() == "false") {
			$('#secondDelConfirmIaDiv').show();
			$('#secondDelBaCaDiv').hide();
			$('input[name="secondDelBaCaChange"]').attr('checked', '');
		}
		else {
			$('#secondDelBaCaDiv').hide();
			$('#secondDelConfirmIaDiv').hide();
			$('input[name="secondDelConfirmSameIa"]').attr('checked', '');
			$('input[name="secondDelBaCaChange"]').attr('checked', '');
		}
	}
	
	function handleCreate2ndDel() {
		if ($('input[name="create2ndDel"]:checked').val() == "true") {
//			$('#2ndDelVasDiv').show();
//			$('#2ndDelIddDiv').show();
//			$('#new2ndDelDiv').show();
//			$('#2ndDelWqRemarkDiv').show();
			$(".2ndDelDiv").show();
			$('input#duplexXSrvType_NEW_2DEL').attr('disabled', '');
			$('input#duplexYSrvType_NEW_2DEL').attr('disabled', '');
		}
		else if ($('input[name="create2ndDel"]:checked').val() == "false") {
//			$('#2ndDelVasDiv').hide();
//			$('#2ndDelIddDiv').hide();
//			$('#new2ndDelDiv').hide();
//			$('#2ndDelWqRemarkDiv').hide();
			$(".2ndDelDiv").hide();
			$('input#duplexXSrvType_NEW_2DEL').attr('disabled', 'disabled');
			$('input#duplexYSrvType_NEW_2DEL').attr('disabled', 'disabled');
			$('input#duplexXSrvType_NEW_2DEL').attr('checked', '');
			$('input#duplexYSrvType_NEW_2DEL').attr('checked', '');
		}
	}
	
	
	function handle2ndDelDiffCust () {
		if ($('input[name="secondDelDiffCust"]').val() == "true") {
			$('div#secondDelDiffCustDiv').show();
		}
	}
	
	function handleDnPool () {
		$('.searchCriteria').each(function() {
			if (this.value == '05') {
				$('div#numberSelectionDiv').show();
				//$("#new2ndDelSrvStatus").attr('disabled', true);
				$("#new2ndDelDn").attr('readonly', true);
			}
		});
	}
	
	function actionPerform() {
		
		handleCreate2ndDel();
		handle2ndDelEr();
		handle2ndDelDiffCust();
		handleDnPool();
		
		$(".duplex2ndDelAutoInVas").change(function (event){
			if ($(this).is(":checked") == true) {
				$(".secondDelOtherVasItem").attr('checked', '');
			}
			else {
				var vasName = $('.duplex2ndDelAutoInVasDisplay').html();
				promptRemoveFreeVas(vasName);
			}
		});
		
		$(".new2ndDelAutoChangeTp").change(function (event){
			if ($(this).is(":checked") == true) {
				$(".secondDelOtherVasItem").attr('checked', '');
			}
			else {
				var vasName = $('.new2ndDelAutoChangeTpDisplay').html();
				promptRemoveFreeVas(vasName);
			}
		});
		
		
		$(".secondDelOtherVasItem").change(function (event){
			if ($(this).is(":checked") == true) {
				if ($(".duplex2ndDelAutoInVas").is(':checked') && $('.duplex2ndDelAutoInVasList').is(':visible')) {
					var vasName = $('.duplex2ndDelAutoInVasDisplay').html();
					promptRemoveFreeVas(vasName);
					$(".duplex2ndDelAutoInVas").attr('checked', '');	
				}
				if ($(".new2ndDelAutoChangeTp").is(':checked') && $('.new2ndDelAutoChangeTpList').is(':visible')) {
					var vasName = $('.new2ndDelAutoChangeTpDisplay').html();
					promptRemoveFreeVas(vasName);
					$(".new2ndDelAutoChangeTp").attr('checked', '');
				}
			}
		});
		
		
		$(".iddItem").change(function (event){
			if ($(this).is(":checked") == false) {
				if (confirm(cancelMsg) == false) {
					$(this).attr("checked", "checked");	
				}
				else {
					$('input[name="secondDelOptOutIdd"]').val(true);
				}
			}
			else {
				$('input[name="secondDelOptOutIdd"]').val(false);
			}
		});	
		
		$('input[name="create2ndDel"]').change(function(event) {
			handleCreate2ndDel();
		});
		
		$('input[name="secondDelEr"]').change(function(event) {
			handle2ndDelEr();
		});
		
		$("a#showOtherVasBtn").click(function(event) {
			event.preventDefault();
			$("#otherVasTable").show();
			$("#showOtherVasBtnDiv").hide();
			$("#hideOtherVasBtnDiv").show();
		});
		
		$("a#hideOtherVasBtn").click(function(event) {
			event.preventDefault();
			$("#otherVasTable").hide();
			$("#showOtherVasBtnDiv").show();
			$("#hideOtherVasBtnDiv").hide();
		});
				
		$('a#submitBtn').click(function(event){
			event.preventDefault();
			$('input[name="formAction"]').val('SUBMIT');
			$('form[name="ltsOtherVoiceServiceForm"]').submit();
		});
		
		$('a#confirmCust').click(function(event){
			$('input[name="formAction"]').val('CUST_VERIFY');
			$('form[name="ltsOtherVoiceServiceForm"]').submit();
		});
		
		$('a#searchDn').click(function(event){
			$('input[name="formAction"]').val('SEARCH_DN');
			$('form[name="ltsOtherVoiceServiceForm"]').submit();
		});
		
		$('a#clearDn').click(function(event){
			$('input[name="formAction"]').val('CLEAR_DN');
			$('form[name="ltsOtherVoiceServiceForm"]').submit();
		});
		
		$('a#searchDnPool').click(function (event) {
			var method = $('input[name="searchMethodRadio"]:checked').val();
			if (method == 'noCriteriaRadio') {
				$('input[name="formAction"]').val('SEARCH_BY_NO_CRITERIA');
			}	
			if (method == 'first5to8Radio') {
				$('input[name="formAction"]').val('SEARCH_BY_FIRST_8_DIGITS');
			}
			if (method == 'last1to3Radio') {
				$('input[name="formAction"]').val('SEARCH_BY_LAST_3_DIGITS');
			}
			$('input[name="searchMethod"]').val('DN_POOL');
			$('form[name="ltsOtherVoiceServiceForm"]').submit();
		});
		
		$('a#lockBtn').click(function (event) {
			$('input[name="formAction"]').val('LOCK_NUMBER');
			$('form[name="ltsOtherVoiceServiceForm"]').submit();
		});
		
		$('.secondDelHotVasItem').change(function(event) {
			$('.secondDelHotVasItem').attr('checked', '');
			$(this).attr('checked', 'checked');
		});
		
		$("a#cancelVasBtn").click(function (event) {
			event.preventDefault();
			$("div#cancelVasDiv").show();	
			
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	    
	    if ($('input[name="searchMethodRadio"]:checked').val()==undefined) {
			$('input[value="noCriteriaRadio"]').attr('checked',true);
		}
	    
	    $(".searchCriteria").change(function (event){
			if (this.value == '05') {
			    $('div#numberSelectionDiv').show();
				//$("#new2ndDelSrvStatus").attr('disabled', true);
				$("#new2ndDelDn").attr('readonly', true);
			} else {
				$('div#numberSelectionDiv').hide();
				$("#new2ndDelDn").attr('readonly', false);
			}	
		});
		
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();