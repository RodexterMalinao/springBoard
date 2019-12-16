var ltscollectdoc = function() {

	function submitForm() {
		$('#ltsCollectDocForm').submit();
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