var ltsbasketservice = function() {
	
	function actionPerform() {
		
		$('a.nextbutton').click(function(event){
			event.preventDefault();
			$('form[name="ltsBasketServiceForm"]').submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();