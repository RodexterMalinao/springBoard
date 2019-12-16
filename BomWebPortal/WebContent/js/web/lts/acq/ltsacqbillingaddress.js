function checkRadio(table){
	var selectedRadioVal = $(table).find('input[type="radio"]:checked').val();
	
	if(selectedRadioVal == 'E'){
		var orgAddr = $(table).find('.originalBillAddr').val();
		$(table).find('textarea').val(orgAddr).attr('readonly', true);
		$(table).find('.baQuickSearch').attr('readonly', true);
		$(table).find('.copyBtn').hide();
	}else if(selectedRadioVal == 'I'){
		$(table).find('textarea').val(iA).attr('readonly', true);
		$(table).find('.baQuickSearch').attr('readonly', true);
		$(table).find('.copyBtn').hide();
	}else if(selectedRadioVal == 'N'){
		$(table).find('textarea').val('').attr('readonly', false);
		$(table).find('.baQuickSearch').attr('readonly', false);
		$(table).find('.copyBtn').show();
	}
	
}

var ltsbillingaddress = function() {
	
	function actionPerform() {

		$('.billingAddrTable').each(function(i ,el){
			checkRadio(el);
		});
		
		$('#clearBaInputAddress').click(function(event) {
			$("#billingAddressTextArea").val("");
			$('#baQuickSearch').val("");
			$('#baQuickSearch').attr('readonly', '');
		});
		
		$('a#continueBtn').click(function(event) {
			event.preventDefault();
			$("form#ltsBillingAddressForm").submit();
		});
		
		$('div.copyBtn a').click(function(event) {
			event.preventDefault();
			$('#'+ $(this).attr('href')).val($("#billingAddressTextArea0").val());
		});
		
		$('.clearBtn').click(function(event) {
			event.preventDefault();
			$('#'+ $(this).attr('href')).val('');
		});
		
		$('.remainRadio').click(function(event){
			checkRadio($(this).parents('.billingAddrTable'));
		});
		
		$('.iAradio').click(function(event){
			checkRadio($(this).parents('.billingAddrTable'));
		});
		
		$('.diffBaRadio').click(function(event){
			checkRadio($(this).parents('.billingAddrTable'));
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	    
	    $(".billingAddressTextArea").blur(function(){
	    	$(this).val($(this).val().toUpperCase());
	    });
	    
	    
	    $('.baQuickSearch').each(function(i ,el){
	    	el = $(el);
	    	el.autocomplete("ltsaddresslookup.html?type=includeLtsOnly", {
				//minChars: 4,
				minChars : 1,
				delay : 600,
				selectFirst : false,
				max : 30,
				matchSubset : false,
				highlight : false,
				ba :true,
				baTextArea : el.parents('.billingAddrTable').find('textarea').attr('id'),
				//extraParams: {random: (function(){return Math.random()})},
				formatItem : function(row, i, max) {
					return row;
				}
			});
	    });
	    
//		$('.baQuickSearch').autocomplete("imsaddresslookup.html?type=address", {
//			//minChars: 4,
//			minChars : 1,
//			delay : 600,
//			selectFirst : false,
//			max : 30,
//			matchSubset : false,
//			highlight : false,
//			ba :true,
//			baTextArea : $(this.element).attr('id'),
//			//extraParams: {random: (function(){return Math.random()})},
//			formatItem : function(row, i, max) {
//				return row;
//			}
//		});
		
	$("#submit").click(function(event) {
		event.preventDefault();
		if($("#errorMsg").val() != ''){
			alert($("#errorMsg").val());
			return;
		}
		$("#billingaddressform").submit();
	});
	
    $('form').bind('submit', function() {
    	$.blockUI({ message: null });
    });
    
}
	
	return {
		actionPerform : actionPerform
	};
	
	
}();
