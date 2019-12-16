var ltsdigitalsignature = function() {

	function submitForm() {
		$('#ltsDigitalSignatureForm').submit();
	}
	
	function initUI() {
		$('.resignDiv').hide();
	}
	
	function actionPerform() {
		
		initUI();
		
		var options = {drawOnly:true, penWidth:'2', lineTop:0, lineWidth:0, validateFields:false};
		var api = $('.sigPad').signaturePad(options);
		
		$('a[name="clear"]').click(function(event){
			api.clearCanvas();
			$('.sigPadError').hide();
		});
		
		$('a[name="confirm"]').click(function(event){
			var signatureString = api.getSignatureString();
			var output = api.getSignature();
			
			if (output.length < 1) {
				$('.sigPadError').show();
			}
			else {
				$('#signatureString').val(signatureString);
				submitForm();
			}
		});
		
		$('a[name="resignBtn"]').click(function(event){
			$('.resignDiv').show(200);
			$('.resignButtonDiv').hide();
		});
		
		$('a[name="skip"]').click(function(event){
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
