var ltsmiscellaneous = function() {
	
	
	function showRedeemPremiumDiv() {
		if ($("input[name='redeemPrevPremium']:checked").val() == "false") {
			$("div#approvalRemarkDiv").show();
			$("div#warningMsgDiv").show();
		}
		else {
			$("div#approvalRemarkDiv").hide();
			$("div#warningMsgDiv").hide();
		}
	}
	
	function showBackDateOrder() {
		if ($("input[name='backDateOrder']:checked").val() == "true") {
			$("#backDateApplicationDate").show(10);
		}
		else {
			$("#backDateApplicationDate").hide(10);
		}
	}
	
	function showDForm() {
		if ($("input[name='submitDisForm']:checked").val() == "false") {
			$(".dForm").hide();
		}
		else {
			$(".dForm").show();
		}
	}
	function showRecontract() {
		if ($("input[name='recontract'].restrict:checked").val() == "true") {
			$("div#errorMsgDiv").show();
			$("div#recontractError").show();
		}
		else {
			if ($(".restrict:checked").length == 0) {
				$("div#errorMsgDiv").hide();	
			}
			$("div#recontractError").hide();
		}
	}
	function actionPerform() {
		
		showRedeemPremiumDiv();
		showDForm();
		showRecontract();
		showBackDateOrder();
		var maxBackDateDays = $("input[name='maxBackDateDays']").val();
		$('#backDate').datepicker({
			changeMonth: true,	
			changeYear: true,
			defaultDate: -1,
			showButtonPanel: true,
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate:  maxBackDateDays, 
			maxDate: -1, //Y is year, M is month, D is day  
			yearRange: '0:+100' //the range shown in the year selection box
		});
		
		//bar TP effective < 3 month
		
		$(document).ready(function() {
			if($('#barUpgradeEye').val() == "true"){
				$("div#errorMsgDiv").show();
				$("div#termPlanBarError").show();
			 }else{	
				$("div#errorMsgDiv").hide();	
				$("div#termPlanBarError").hide();
			 }
		});
		
		$("input[name='recontract']").change(function(event) {
			showRecontract();
		});
		
		$("input[name='submitDisForm']").change(function(event) {
			showDForm();
		});
		
		$("input[name='switchTp']").change(function(event) {
			if ($("input[name='switchTp']:checked").val() == "false") {
				$("div#approvalRemarkDiv").hide();
			}
			else {
				$("div#approvalRemarkDiv").show();
			}
		});
		
		$("input[name='backDateOrder']").change(function(event) {
			showBackDateOrder();
		});
		
//		$("input[name='dnChange']").change(function(event) {
//			if ($("input[name='dnChange']:checked").val() == "true") {
//				$("div#errorMsgDiv").show();
//				$("div#dnChangeError").show();
//			}
//			else {
//				if ($(".restrict:checked").length == 0) {
//					$("div#errorMsgDiv").hide();	
//				}
//				$("div#dnChangeError").hide();
//			}
//		});
		
		$("input[name='baCaChange']").change(function(event) {
			if ($("input[name='baCaChange']:checked").val() == "true") {
				$("div#errorMsgDiv").show();
				$("div#baCaChangeError").show();
			}
			else {
				if ($(".restrict:checked").length == 0) {
					$("div#errorMsgDiv").hide();	
				}
				$("div#baCaChangeError").hide();
			}
		});
		
		$("input[name='useDuplexNumAsNewEye']").change(function(event) {
			if ($("input[name='useDuplexNumAsNewEye']:checked").val() == "true") {
				$("div#errorMsgDiv").show();
				$("div#duplexNewEyeError").show();
			}
			else {
				if ($(".restrict:checked").length == 0) {
					$("div#errorMsgDiv").hide();	
				}
				$("div#duplexNewEyeError").hide();
			}
		});				

		$("input[name='changeExisting123TvOrMirror']").change(function(event) {
			if ($("input[name='changeExisting123TvOrMirror']:checked").val() == "true") {
				$("div#errorMsgDiv").show();
				$("div#changeExist123TvOrMirrorError").show();
			}
			else {
				if ($(".restrict:checked").length == 0) {
					$("div#errorMsgDiv").hide();	
				}
				$("div#changeExist123TvOrMirrorError").hide();
			}
		});
		
		$("input[name='redeemPrevPremium']").change(function(event) {
			showRedeemPremiumDiv();
		});	
		
		$("#approvalRemarkBtn").click(function(event) {
			event.preventDefault();
			var win = window.open('ltswqremark.html', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=600,width=1024');
		}); 
		
		$("a#continueBtn").click(function (event) {
			event.preventDefault();
			if ($(".restrict:checked").length == 0 && $('#barUpgradeEye').val() != "true") {
				$("form#ltsMiscellaneousForm").submit();
			}
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	}
	
	return {
		actionPerform : actionPerform
	};
	
	
}();