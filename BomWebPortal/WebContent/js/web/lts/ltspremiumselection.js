var ltspremiumselection = function() {
	
	function actionPerform() {
		
		$('a#submitBtn').click(function(event){
			event.preventDefault();
			$('form[name="ltsPremiumSelectionForm"]').submit();
		});
		
		$('input[id*="membershipNum"]').focus(function( event ) {
			var premiumSetId = $( this ).attr('class');
			$('#'+ premiumSetId + 'creditCardPrefix').val('');
			$('#'+ premiumSetId + 'creditCardSuffix').val('');
		});
		
		$('input[id*="creditCard"]').focus(function( event ) {
			var premiumSetId = $( this ).attr('class');
			$('#'+ premiumSetId + 'membershipNum').val('');
		});
		
		$('input[id*="creditCard"]').keyup(function () { 
		    this.value = this.value.replace(/[^0-9\.]/g,'');
		});
		
		// Erase MemeberShip ID if Credit Card Num is not empty. 
		$('input[id*="membershipNum"]').each(function( event ) {
			var premiumSetId = $( this ).attr('class');
			if ($('#'+ premiumSetId + 'creditCardPrefix').val() != ""
				|| $('#'+ premiumSetId + 'creditCardSuffix').val() != "") {
				$(this).val('');
			}
		});
		
		$('a#giftBtn').click(function(event){
			$('#action').val('GIFT_SEARCH');
			$('form[name="ltsPremiumSelectionForm"]').submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
		
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();