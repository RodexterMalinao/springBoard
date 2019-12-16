var ltsnowtvservice = function() {
	
	function handleAdditionalTv() {
		hideAllAdditionalTv();
		
		var additionalTvType = $('input[name="additionalTvType"]:checked').val();
		switch (additionalTvType) {
		case "MIRROR":
			$('div#mirrorTvDiv').show();
			$('div#nowTvAdultDiv').show();
			break;
		case "SD_PAY_CHANNEL":
			$('div#payTvDiv').show();
			$('div#celestialMoivesDiv').show();
			$('div#nowTvSportDiv').show();
			$('div#payChannelGroupListDiv').show();
			$('div#nowTvAdultDiv').show();
			$('.nowTvSportItem').attr('checked', '');
			break;
		case "SD_SPECIAL_CHANNEL":
			$('div#specialTvDiv').show();
			$('div#celestialMoivesDiv').show();
			$('div#nowTvSportDiv').show();
			$('div#specChannelGroupListDiv').show();
			$('div#nowTvAdultDiv').show();
			$('.nowTvSportItem').attr('checked', '');
			break;
		case "ADDITIONAL_CHANNELS":
			$('div#nowTvSportDiv').show();
			$('.nowTvSportItem').attr('checked', 'checked');
			break;
		default:
			break;
		}
	}
	
	function hideAllAdditionalTv() {
		$('div#mirrorTvDiv').hide();
		$('div#celestialMoivesDiv').hide();
		$('div#payTvDiv').hide();
		$('div#payChannelGroupListDiv').hide();
		$('div#specialTvDiv').hide();
		$('div#specChannelGroupListDiv').hide();
		$('div#nowTvAdultDiv').hide();
		$('div#nowTvSportDiv').hide();
	}
	
	
	
	function actionPerform() {
		
		handleAdditionalTv();
		
		$("input#dateOfBirthDatepicker").datepicker({
			changeMonth: true,
			changeYear: true,

			showButtonPanel: true,
		    defaultDate: "-18y",
		    minDate: new Date(1900, 1, 1, 0, 0, 0, 0),
		    maxDate: "-18y",
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		    yearRange: '1900:-18'

		});
		
		$('.nowTvSpecItem').change(function(event) {
			$('.nowTvSpecItem').attr('checked', '');
			$(this).attr('checked', 'checked');
		});
		
		$('.nowTvPayItem').change(function(event) {
			$('.nowTvPayItem').attr('checked', '');
			$(this).attr('checked', 'checked');
		});
		
		$('.nowTvViewItem').change(function(event) {
			$('.nowTvViewItem').attr('checked', '');
			$(this).attr('checked', 'checked');
		});
		
		$('.nowTvMirrorItem').change(function(event) {
			$('.nowTvMirrorItem').attr('checked', '');
			$(this).attr('checked', 'checked');
		});
		
		$('.nowTvBillOption').change(function(event) {
			
			if (!$(this).hasClass('emailBill')) {
				$('input[name="nowTvEmail"]').val('');
				$('input[name="nowTvEmail"]').attr('disabled', 'disabled');
			}
			else {
				$('input[name="nowTvEmail"]').attr('disabled', '');
			}
			
			$('.nowTvBillOption').attr('checked', '');
			$(this).attr('checked', 'checked');
		});
		
		$('input[name="additionalTvType"]').change(function(event) {
			handleAdditionalTv();
		});
		
		$('a#submitBtn').click(function(event){
			event.preventDefault();
			$('form').submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	    
	    $(".channelCheckbox").each(function(){
	        var chID = $(this).attr("id");
	        var chAttb = "."+chID;
	        $(chAttb).attr("disabled", true);

	        if($(this).is(':checked')){
	        	$(chAttb).attr("disabled", false);
	        }
	        
	        $(this).click(function(){
	            if($(this).is(":checked")){
	                $(chAttb).attr("disabled", false);
	            }else{
	            	$(chAttb).each(function(){
	            		var attbType = $(this).attr("type");
		                switch (attbType){
			                case 'checkbox': $(this).removeAttr('checked'); break;
			                case 'radio': $(this).removeAttr('checked'); break;
			                case 'text': $(this).val(''); break;
			                default: $(this).val(''); break;
		                }
		                $(this).attr("disabled", true);
	            	});
	            	
	            }
	        });
	        
	        
	    });
	    
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();