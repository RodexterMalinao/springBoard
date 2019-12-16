var ltsrecontract = function() {

	function custLkup(docType, docId){
		var sbuid = $("[name='sbuid']").val();
		$.ajax({
			url : "ltsrecontractcustlookup.html?sbuid=" + sbuid + "&reqType=FIND_CUST&docType=" + docType + "&docId=" + docId,
			type : 'POST',
			success : function(data){
				var obj = jQuery.parseJSON(data);
				$("#idError").hide();
				if(obj.state == 1){
					if (docType != 'BS') {
						$("#firstName").val(obj.firstName).attr("readonly", true);
						$("#lastName").val(obj.lastName).attr("readonly", true);
						$("#title").val(obj.title).attr("disabled", true);
					}
					$("#companyName").val(obj.companyName).attr("readonly", true);
					$("#blacklist").html(obj.blacklist ? 'Y' : 'N');
					$("#profileNotFound").hide();
					$("#profileFound").show();
					$("#profileInfo").show();
							
					if (obj.updateDnProfile == 'M') {
						$("#updateDnProfileMRadio").attr('checked','checked');
						$("#updateDnProfileNRadio").attr('disabled',true);
						$("#recontractModeB").attr('disabled',true);
						$("#recontractModeS").attr('disabled',true);
					}
					
					else if (obj.updateDnProfile != 'M') {
						$("#updateDnProfileMRadio").attr('disabled',true);
					}
					
				}else {
					$("#firstName").val('').attr("readonly", false);
					$("#lastName").val('').attr("readonly", false);
					$("#companyName").val('').attr("readonly", false);
					$("#title").val('').attr("disabled", false);
					if(obj.state == 2){
						$("#profileInfo").hide();
						if($("[id=docId.errors]").val() == null){
							$("#idError").show();
						}
					}else{
						$("#profileFound").hide();
						$("#profileNotFound").show();
						$("#profileInfo").show();
					}
				}
			},
			complete : function() {
				 $.unblockUI(); 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}
		});
	}
	
	function checkCustTypeAndId(){
		var docType = $("#docType").val();
		var docId = $("#docId").val();
		if(docType != '' && docId != ''){
			custLkup(docType, docId);
		}
		
	}

	function checkBrFields(){
		if($("#docType").val() == 'BS'){
			$(".nonHkbrField").hide();
			$(".hkbrField").show();
		}else{
			$(".nonHkbrField").show();
			$(".hkbrField").hide();
		}
	}
	
	function checkDeceasedCase() {
		if($("#deceasedCase").is(':checked')){
			$("input[name='recontractMode']").attr('disabled', 'disabled');
			$("#recontractModeS").attr('checked', 'checked');
			$(".nonDeceasedCase").hide();
			$(".deceasedCase").show();
		}else{
			$(".nonDeceasedCase").show();
			$(".deceasedCase").hide();
			$("input[name='recontractMode']").attr('disabled', '');
		}
	}
	
	function checkRefundType() {
		if ($("#refundType").val() == 'N' || $("#refundType").val() == '') {
			$(".refundNewCust").hide();
			$("#newBillingName").val('');
		}
		else {
			$(".refundNewCust").show();
		}
	}
	
	function actionPerform() {

		checkCustTypeAndId();
		checkBrFields();
		checkDeceasedCase();
		checkRefundType();
		
		$(".toUpper").keyup(function(){
	    	$(this).val($(this).val().toUpperCase());
	    });
		
		$("a#continueBtn").click(function (event) {
			event.preventDefault();
			$("#title").attr("disabled", false);
			$('input[name="recontractMode"]').removeAttr('disabled');
			$("form#ltsRecontractForm").submit();
		});

		$("#docType").change(function(){
			checkCustTypeAndId();
			checkBrFields();
		});
		
		$("#docId").change(function(){
			checkCustTypeAndId();
		});
		
		$("#deceasedCase").change(function(){
			checkDeceasedCase();
		});
		
		$("#refundType").change(function(){
			checkRefundType();
		});
		
		 $('#clearBaInputAddress').click(function(event) {
	    	$('#billingAddressTextArea').val("");
			$('input[name="newBillingAddress"]').val("");
			$('input[name="baQuickSearch"]').val("");
			$('input[name="baQuickSearch"]').attr('readonly', '');
		});
		 
	    $("#billingAddressTextArea").blur(function(){
	    	$("#billingAddressTextArea").val($("#billingAddressTextArea").val().toUpperCase());
	    }).blur(function(){
	    	$("#billingAddressTextArea").val($("#billingAddressTextArea").val().toUpperCase());
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

	    
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	        $(this).find(':input').removeAttr('disabled');
	    });
	}

	return {
		actionPerform : actionPerform
	};
}();