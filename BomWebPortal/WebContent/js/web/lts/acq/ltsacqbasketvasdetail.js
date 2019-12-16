var ltsacqbasketvasdetail = function() {
	
	function showOtherVas() {
		if ($(".otherVasItem").length >= 1) {
			$(".otherVasItem").each(function(event){
				if ($(this).is(":checked") == true) {
					$("#otherVasTable").show();
					$("#showOtherVasBtnDiv").hide();
					$("#hideOtherVasBtnDiv").show();
				}
			});
		}
		
	}
	
	function isIddVasSelected(){
		var result = false;
		$(".iddItem").each(function(event){
			if ($(this).is(":checked") == true) {
				result = true;
			}
		});
		
		$(".e0060Item").each(function(event){
			if ($(this).is(":checked") == true) {
				result = true;
			}
		});

		if(result == true){
			enableFfpVas();
		}
		else{
			disableFfpVas();
		}
	}
	
	function enableFfpVas(){
		$(".ffpHotVasItem").each(function(event){
			$(this).attr('disabled', false);			
		});
		$(".ffpOtherVasItem").each(function(event){
			$(this).attr('disabled', false);	
		});
		$(".ffpStaffVasItem").each(function(event){
			$(this).attr('disabled', false);	
		});
	}
	
	function disableFfpVas(){
		$(".ffpHotVasItem").each(function(event){
			$(this).attr("checked", false);
			$(this).attr('disabled', true);
			
		});
		$(".ffpOtherVasItem").each(function(event){
			$(this).attr("checked", false);
			$(this).attr('disabled', true);
		});
		$(".ffpStaffVasItem").each(function(event){
			$(this).attr("checked", false);
			$(this).attr('disabled', true);
		});
	}
	
	function enablePrewiringVas(){
		$(".prewireItem").each(function(event){
			$(this).attr('disabled', false);			
		});
	}
	
	function disablePrewiringVas(){
		$(".prewireItem").each(function(event){
			$(this).attr("checked", false);
			$(this).attr('disabled', true);
			
		});
	}
	
	function enablePreinstallVas(){
		$(".preinstallItem").each(function(event){
			$(this).attr('disabled', false);			
		});
	}
	
	function disablePreinstallVas(){
		$(".preinstallItem").each(function(event){
			$(this).attr("checked", false);
			$(this).attr('disabled', true);
			
		});
	}
	
	function disableOtherPreinstallVas(){
		$(".preinstallItem").each(function(event){
			if ($(this).is(":checked") == false) {
				$(this).attr('disabled', true);
			}
		});
	}
	
	function actionPerform() {

		showOtherVas();
		
		if($(".iddItem").length == 0 && $(".e0060Item").length == 0)
		{
			disableFfpVas();
		}
		
		$(".prewireItem").change(function (event){
			if ($(this).is(":checked") == true) {
				disablePreinstallVas();
			}
			else {
				enablePreinstallVas();
			}
		});
		
		$(".preinstallItem").change(function (event){
			if ($(this).is(":checked") == true) {
				disablePrewiringVas();
				disableOtherPreinstallVas();
			}
			else {
				enablePrewiringVas();
				enablePreinstallVas();
			}
		});
		
		$(".e0060Item").change(function (event){
			if ($(this).is(":checked") == false) {
				if (confirm(cancelMsg) == false) {
					$(this).attr("checked", "checked");
				}
				else {
					$('input[name="optOut0060e"]').val(true);
				}
			}
			else {
				$('input[name="optOut0060e"]').val(false);
			}

			isIddVasSelected();
		});
		
		$(".iddItem").change(function (event){
			if ($(this).is(":checked") == false) {
				if (confirm(cancelMsg) == false) {
					$(this).attr("checked", "checked");	
				}
				else {
					$('input[name="optOutIdd"]').val(true);
				}
			}
			else {
				$('input[name="optOutIdd"]').val(false);
			}

			isIddVasSelected();
		});	
		
		$("a#showOtherVasBtn").click(function(event) {
			event.preventDefault();
			$("#otherVasTable").show();
			$("#showOtherVasBtnDiv").hide();
			$("#hideOtherVasBtnDiv").show();
		});
		
		$("a#hideOtherVasBtn").click(function(event) {
			event.preventDefault();
			$("#otherVasTable").hide();
			$("#showOtherVasBtnDiv").show();
			$("#hideOtherVasBtnDiv").hide();
		});
		
		$("a#showOtherComtPeriodVasBtn").click(function(event) {
			event.preventDefault();
			$("#otherComtPeriodVasTable").show();
			$("#showOtherComtPeriodVasBtnDiv").hide();
			$("#hideOtherComtPeriodVasBtnDiv").show();
		});
		
		$("a#hideOtherComtPeriodVasBtn").click(function(event) {
			event.preventDefault();
			$("#otherComtPeriodVasTable").hide();
			$("#showOtherComtPeriodVasBtnDiv").show();
			$("#hideOtherComtPeriodVasBtnDiv").hide();
		});		
		
		$("a#cancelVasBtn").click(function (event) {
			event.preventDefault();
			$("div#cancelVasDiv").show();	
			
		});
		
		$('a#submitBtn').click(function(event){
			event.preventDefault();
			$('input[name="formAction"]').val("SUBMIT");
			$('form[name="ltsBasketVasDetailForm"]').submit();
		});
		
		$(".mthFfpFilter").change(function (event){
			event.preventDefault();
			$('input[name="formAction"]').val("FILTER");
			$('form[name="ltsBasketVasDetailForm"]').submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();