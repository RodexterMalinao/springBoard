var ltsterminatecustomerinquiry = function() {
	
	function actionPerform() {
		
		$('a#submitSeachBtn').click(function (event) {
			event.preventDefault();
			$('input[name="formAction"]').val('SUBMIT');
			$('form[name="ltsTerminateCustomerInquiryForm"]').submit();
		});
		
		$('a#clearSearchBtn').click(function (event) {
			event.preventDefault();
			$('input[name="formAction"]').val('CLEAR_PROFILE');
			$('form[name="ltsTerminateServiceSelectionForm"]').submit();
		});
	   
		$('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	        $(this).find(':input').removeAttr('disabled');
	    });	
		
		if(!isMsgListEmpty){
			$('#serviceNum').attr("disabled",true);
			$('.func_button').hide();
		}
	}
	return {
		actionPerform : actionPerform
	};
	
	
}();
