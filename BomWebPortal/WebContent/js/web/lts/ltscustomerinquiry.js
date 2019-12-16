var ltscustomerinquiry = function() {
	
	function checkStaffPlan(){
		if($("[name=upgradeToStaffPlan]:checked").val() == "true"){
			$("#staffIdTr").show();
		}else{
			$("#staffIdTr").hide();
			$("#staffId").val("");
		}
	}
	
	function actionPerform() {
		
		$('#tabs').tabs();
		
		checkStaffPlan();
		
		//hover states on the static widgets
		$('#dialog_link, ul#icons li').hover(
			function() { $(this).addClass('ui-state-hover'); }, 
			function() { $(this).removeClass('ui-state-hover'); }
		);
			
		$('a#submitSeachBtn').click(function (event) {
			$('input[name="formAction"]').val('SEARCH_PROFILE');
			$('form[name="ltsCustomerInquiryForm"]').submit();
		});
		
		$('a#clearSearchBtn').click(function (event) {
			$('input[name="formAction"]').val('CLEAR_PROFILE');
			$('form[name="ltsCustomerInquiryForm"]').submit();
		});
		
		$('a#upgradeBtn').click(function (event) {
			$('input[name="formAction"]').val('SUBMIT_UPG');
			$('form[name="ltsCustomerInquiryForm"]').submit();
		});
		
		$('a#retentionBtn').click(function (event) {
			$('input[name="formAction"]').val('SUBMIT_RET');
			$('form[name="ltsCustomerInquiryForm"]').submit();
		});
		
		$('a#standaloneVasBtn').click(function(){
			$('input[name="formAction"]').val('SUBMIT_STANDALONE_VAS');
			$('form[name="ltsCustomerInquiryForm"]').submit();
		});
		
		$("[name=upgradeToStaffPlan]").click(function (event){
			checkStaffPlan();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	        $(this).find(':input').removeAttr('disabled');
	    });
		
		$("a#custInformationNextBtn").click(function (event) {
			event.preventDefault();
			$(window).scrollTop(100);
			$("li#custRemarkTab").show();
			$("div#custRemarkNextDiv").show();
			$("a#showCustRemark").trigger('click');
			$("div#custInformationNextDiv").hide();
			$("div#continueBtnDiv").show();
		});

	}
	
	return {
		actionPerform : actionPerform
	};
	
	
}();
