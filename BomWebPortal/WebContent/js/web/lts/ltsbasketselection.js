var ltsbasketselection = function() {
	
	function actionPerform() {
		
		$('#tabs').tabs();

//		var basketTabIndex =  $('input[name="basketTabIndex"]').val();
//		$("#tabs").tabs('option', 'active', basketTabIndex);
		
		$('a.selectBasket').click(function(event){
			event.preventDefault();
			var basketId = $(this).attr('href');
			$('input[name="selectedBasketId"]').val(basketId);
			
			var selectedBasketTabIndex = $("#tabs").tabs('option', 'active');
			$('input[name="basketTabIndex"]').val(selectedBasketTabIndex);
			
			$('input[name="formAction"]').val('SUBMIT');
			$('form[name="ltsBasketSelectionForm"]').submit();
		});
		
		
		$('#searchBtn').click(function(event){
			event.preventDefault();
			
			var selectedBasketTabIndex = $("#tabs").tabs('option', 'active');
			$('input[name="basketTabIndex"]').val(selectedBasketTabIndex);
			
			$('input[name="formAction"]').val('FILTER');
			$('form[name="ltsBasketSelectionForm"]').submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();