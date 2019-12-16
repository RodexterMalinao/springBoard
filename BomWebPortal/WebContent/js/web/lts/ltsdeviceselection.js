var ltsdeviceselection = function() {
	
	function actionPerform() {
		
		$('a.selectDevice').click(function(event){
			event.preventDefault();
			var deviceType = $(this).attr('href');
			$('input[name="selectedDeviceType"]').val(deviceType);
			$('form[name="ltsDeviceSelectionForm"]').submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();