var ltsbasketvasdetail = function() {
	
	function promptRemoveItemAlert(itemCssClass) {
		var itemDisplay = $('.'+itemCssClass).html();
		if (itemDisplay != undefined && itemDisplay != null && itemDisplay != ""){
			alert(itemDisplay + " will be removed");
		}
	}
	
	function handleWqRemark() {
		var show = false;
		$("select.penalty").each(function(event){
			if($(this).val() == "MW") {
				$("#approvalRemarkDiv").show();
				show = true;
			}
		});
		
		if (!show) {
			$("#approvalRemarkDiv").hide();
		}
	}
	
	
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
	
	function showFfpOther() {
		if ($(".ffpOtherItem").length >= 1) {
			$(".ffpOtherItem").each(function(event){
				if ($(this).is(":checked") == true) {
					$("#otherVasTable").show();
					$("#showOtherFfpBtnDiv").hide();
					$("#hideOtherFfpBtnDiv").show();
				}
			});
		}
	}
	
	function actionPerform() {
		
		handleWqRemark();
		showOtherVas();
		
		$("select.penalty").change(function(event) {
			handleWqRemark();
		});
		
		$("#approvalRemarkBtn").click(function(event) {
			event.preventDefault();
			var win = window.open('ltswqremark.html', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=600,width=1024');
		}); 
		
		$(".teamVasItem").change(function (event){
			if ($(this).is(":checked") == true) {
				$(".hotVasItem").attr('checked', '');
			}
			else {
				promptRemoveItemAlert("teamVasItemDisplay");
			}
			$('input[name="formAction"]').val("UPDATE");
			$('form[name="ltsBasketVasDetailForm"]').submit();
		});
		
		$(".profileAutoChangeTpItem").change(function (event){
			if ($(this).is(":checked") == true) {
				$(".hotVasItem").attr('checked', '');
			}
			else {
				promptRemoveItemAlert("profileAutoChangeTpItemDisplay");
			}
			$('input[name="formAction"]').val("UPDATE");
			$('form[name="ltsBasketVasDetailForm"]').submit();
		});
		
		$(".hotVasItem").change(function (event){
			if ($(this).is(":checked") == true && $(".profileAutoChangeTpItem").is(':checked')) {
				promptRemoveItemAlert("profileAutoChangeTpItemDisplay");
				$(".profileAutoChangeTpItem").attr('checked', '');
			}
			if ($(this).is(":checked") == true && $(".teamVasItem").is(':checked')) {
				promptRemoveItemAlert("teamVasItemDisplay");
				$(".teamVasItem").attr('checked', '');
			}
			$('input[name="formAction"]').val("UPDATE");
			$('form[name="ltsBasketVasDetailForm"]').submit();
		});
		
		
		$(".otherVasItem").change(function (event){
			$('input[name="formAction"]').val("UPDATE");
			$('form[name="ltsBasketVasDetailForm"]').submit();
		});
		
		
		$(".bundleVasItem").change(function (event){
			$('input[name="formAction"]').val("UPDATE");
			$('form[name="ltsBasketVasDetailForm"]').submit();
		});
		
		
		$(".e0060Item").change(function (event){
			if ($(this).is(":checked") == false) {
				if (confirm("Are you sure to cancel selected service ?") == false) {
					$(this).attr("checked", "checked");
				}
				else {
					$('input[name="optOut0060e"]').val(true);
				}
			}
			else {
				$('input[name="optOut0060e"]').val(false);
			}
		});
		
		$(".iddItem").change(function (event){
			if ($(this).is(":checked") == false) {
				if (confirm("Are you sure to cancel selected service ?") == false) {
					$(this).attr("checked", "checked");	
				}
				else {
					$('input[name="optOutIdd"]').val(true);
				}
			}
			else {
				$('input[name="optOutIdd"]').val(false);
			}
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
		
		$("a#showOtherFfpBtn").click(function(event) {
			event.preventDefault();
			$(".otherFfpItem").show();
			$("#showOtherFfpBtnDiv").hide();
			$("#hideOtherFfpBtnDiv").show();
		});
		
		$("a#hideOtherFfpBtn").click(function(event) {
			event.preventDefault();
			$(".otherFfpItem").hide();
			$("#showOtherFfpBtnDiv").show();
			$("#hideOtherFfpBtnDiv").hide();
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