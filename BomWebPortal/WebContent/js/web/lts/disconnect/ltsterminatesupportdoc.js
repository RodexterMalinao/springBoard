var ltsterminatesupportdoc = function() {
	
	function handleDistributeMode() {
		// PAPER
		
		var distributeMode = $('input[name="distributionMode"]:checked').val();
		
		if(distributeMode == 'P') {
			$('.digital').val('');
			$('.digital').attr('checked', '');
			$('.digital').attr('disabled', 'disabled');
		}
		else {
			$('input[name="distributeSms"]').attr('disabled', '');
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
	
	function actionPerform() {
		
		handleDistributeMode();
		handleWaiveReason();
		$('div#errorDiv').hide();
		$('div#reason_name').html('');
		
		$('input[name="distributionMode"]').change(function(event){
			handleDistributeMode();
		});
		
		
		$("#next").click(function(event) {
			event.preventDefault();
			if (validateWaiveReason() == false) {
				$.unblockUI();
				return;
			}else{
				$('input[name="formAction"]').val("SUBMIT");
				$('#ltsTerminateSupportDocForm').submit();
			}
			
		});
		
		$("#suspend").click(function(event) {
			$('input[name="formAction"]').val("SUSPEND");
			$('#ltsTerminateSupportDocForm').submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
		
	    
	    $("#")
	    
	}
	
	return {
		actionPerform : actionPerform
	};
	
	
}();
