var ltsacqnumconfirmation = function() {
	
	function actionPerform() {		
		
		$('#continueBtn').click(function (event) {
			event.preventDefault();
			$('input[name="formAction"]').val("SUBMIT");
			$('#ltsacqnumconfirmationForm').submit();
		});
		
		$('#suspend').click(function(event) {
			if($('#suspendReason').val()=="DRG_DOWN")
			{
				alert("Cannot choose DRG Downtime as the reason in this page.");
				return false;
			}
			$('input[name="formAction"]').val("SUSPEND");
			$('#ltsacqnumconfirmationForm').submit();
		});
		
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();