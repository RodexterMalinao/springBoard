var ltssupportdoc = function() {
	
	function handleDistributeMode() {
		// PAPER
		
		var distributeMode = $('input[name="distributionMode"]:checked').val();
		
		if(distributeMode == 'P') {
			$('.digitalSignature').val('');
			$('.digitalSignature').attr('checked', '');
			$('.digitalSignature').attr('disabled', 'disabled');
		}
		// ESIGNATURE
		else {
			$('.digitalSignature').attr('disabled', '');
			
			if ($('input[name="sendSms"]').is(':checked')){
				$('input[name="distributeSms"]').attr('disabled', '');
			}
			else {
				$('input[name="distributeSms"]').val('');
				$('input[name="distributeSms"]').attr('disabled', 'disabled');	
			}
			
			if ($('input[name="sendEmail"]').is(':checked')){
				$('input[name="distributeEmail"]').attr('disabled', '');
			}
			else {
				$('input[name="distributeEmail"]').val('');
				$('input[name="distributeEmail"]').attr('disabled', 'disabled');	
			}
		}

		
	}
	
	function handleWaiveReason() {
		
		var distributeMode = $('input[name="distributionMode"]:checked').val();
		
		$('.waiveReasonList').children().each(function() {
			
			$(this).attr('selected', '');
			$(this).attr('disabled', '');
			
			if ($(this).attr('class') == distributeMode) {
				$(this).attr('selected', 'selected');
			}
			else if ($(this).attr('class') != distributeMode && $(this).attr('class') != '' && $(this).attr('class') != 'Y') {
				$(this).attr('disabled', 'disabled');
			}
			
		});
		
		// default waive
		$('.waiveReasonList').each(function() {
			if ($(this).val() == '') {
				$(this).children().each(function() {
					if ($(this).attr('class') == 'Y') {
						$(this).attr('selected', 'selected');
					}
				});	
			}
		});
		
	}

	function validateWaiveReason() {
		var distributeMode = $('input[name="distributionMode"]:checked').val();
		var isValid = true;
		$('.waiveReasonList').children().each(function() {
			if ($(this).is(':selected') && $(this).attr('class') != distributeMode && $(this).attr('class') != '' && $(this).attr('class') != 'Y') {
				$('div#errorDiv').show();
				$('div#reason_name').html($(this).attr('label'));
				isValid = false;
			}
		});

		return isValid;
	}
	
	function promptConfirm(messge, title, yesFunc, noFunc){
		if(isIE() == 6){
			$("select").css("visibility", "hidden");
		}
		$('<div></div>').appendTo('body').html(
				'<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>'+messge+'</p>'
			).dialog({
			 resizable: false,
			 title: title,
			 modal: true,
			 zIndex: 10000, 
			 autoOpen: true,
			 height: 'auto',
			 width: 'auto', 
			 buttons: {
				 "Yes": yesFunc,
				 "No": noFunc
			 }
		 });
		 $("select").css("visibility", "visible");
	}
	
	function updateDistributionModePaperLabel(){
		if($("[name=signoffMode]:checked").val() == "R"){
			$("#distributionModePaperRadio").html("Paper(Print)");
		}else if($("[name=signoffMode]:checked").val() == "C"){
			$("#distributionModePaperRadio").html("Paper(Postal)");
		}
	}
	
	function actionPerform() {
		
		handleDistributeMode();
		handleWaiveReason();
		updateDistributionModePaperLabel();
		$('div#errorDiv').hide();
		$('div#reason_name').html('');
		
		$('input[name="distributionMode"]').change(function(event){
//			handleDistributeMode();
//			handleWaiveReason();
			
			$('input[name="formAction"]').val("CHANGE_DIS_MODE");
			$('#ltsSupportDocForm').submit();
			
		});
		
		$('input[name="sendSms"]').change(function(event){
			handleDistributeMode();
		});
		
		$('input[name="sendEmail"]').change(function(event){
			handleDistributeMode();
		});
		
		$("[name=signoffMode]").change(function(event){
			updateDistributionModePaperLabel();
			$('input[name="formAction"]').val("CHANGE_DIS_MODE");
			$('#ltsSupportDocForm').submit();
		});
				
//		
		
		$('a#submit').click(function(event) {
			event.preventDefault();
			if (validateWaiveReason() == false) {
				$.unblockUI();
				return;
			}
			
			
			if($("input[name=distributionMode]:checked").val() == "E" && $("#distributeEmail").val() == ""){
				
				var result = confirm('Are you sure no email address?');
				if (!result) {
					return;
				}
				$('input[name="formAction"]').val("SUBMIT");
				$('#ltsSupportDocForm').submit();
							 
			}else if($("input[name=distributionMode]:checked").val() == "P" && !isBrCust && !isPt){
				
				var result = confirm('Are you sure to use the paper mode?');
				if (!result) {
					$("input[name=distributionMode][value=E]").attr("checked", "checked")
					 $('input[name="formAction"]').val("CHANGE_DIS_MODE");
					 $('#ltsSupportDocForm').submit();
				}
				else {
					$('input[name="formAction"]').val("SUBMIT");
					$('#ltsSupportDocForm').submit();	
				}
			}else{
				$('input[name="formAction"]').val("SUBMIT");
				$('#ltsSupportDocForm').submit();
			}
			
		});
		
		$('a#suspend').click(function(event) {
			$('input[name="formAction"]').val("SUSPEND");
			$('#ltsSupportDocForm').submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
		
	}
	
	return {
		actionPerform : actionPerform
	};
	
	
}();
