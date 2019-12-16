var ltsacqcollectdoc = function() {

	function submitForm() {
		$('#ltsAcqCollectDocForm').submit();
	}
	
	function actionPerform() {

		$('#copySerialBtn').click(function(event){
			event.preventDefault();
			if($('.faxSerialInput')[0] != null){
				var serial = $('.faxSerialInput')[0].value;
				$('.faxSerialInput').val(serial);
			}
		});
		
		$('a#submit').click(function(event){
			$("#submitType").val('');
			submitForm();
		});

		$('a#amend_continue').click(function(event){
			$("#submitType").val('AMEND_CONT');
			submitForm();
		});
		
		$('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
		
	}
		
	return {
		actionPerform : actionPerform
	};
}();