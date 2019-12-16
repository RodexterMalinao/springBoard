var ltsterminatebillinginfo = function() {

	function showAddress(){
		if ($("#changeBa").is(':checked')){
			$(".newBa").fadeIn();
		}else{
			$(".newBa").hide();
		}
	}
	
	function actionPerform(){
		
		
		showAddress();
		
		$("#submitBtn").click(function(event) {
			event.preventDefault();
			$("#terminatebillinginfoform").submit();
		});
		
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	        $(this).find(':input').removeAttr('disabled');
	    });
		
	    $('#clearBaInputAddress').click(function(event) {
	    	$('#billingAddressTextArea').val("");
			$('input[name="newBillingAddress"]').val("");
			$('input[name="baQuickSearch"]').val("");
			$('input[name="baQuickSearch"]').attr('readonly', '');
			$("#instantUpdateBa").attr('checked', false);
		});

	    $("#billingAddressTextArea").blur(function(){
	    	$("#billingAddressTextArea").val($("#billingAddressTextArea").val().toUpperCase());
	    }).blur(function(){
	    	$("#billingAddressTextArea").val($("#billingAddressTextArea").val().toUpperCase());
	    });
	    
	    $("#changeBa").change(function(event){
	    	showAddress();
		});
	    
	    $('input[name="baQuickSearch"]').autocomplete("imsaddresslookup.html?type=address", {
			//minChars: 4,
			minChars : 1,
			delay : 600,
			selectFirst : false,
			max : 12,
			matchSubset : false,
			highlight : false,
			ba :true,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}
		});
		
	}return {
		actionPerform : actionPerform
	};
}();