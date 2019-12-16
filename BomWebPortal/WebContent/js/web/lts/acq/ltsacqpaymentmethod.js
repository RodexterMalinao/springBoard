var cardNumIndex = 0;

function openCEKSWindow(index){
		//var form = document.forms['createForm'];
		cardNumIndex = index;
		var sbuid = $('input[name="sbuid"]').val();
		var ceksLink = "ltsceks.html?sbuid=" + sbuid;
		if (ceksLink != null && ceksLink != "") {
			window.open(ceksLink, "_blank", "width=550, height=400, resizable=no, toolbar=yes, location=yes, menubar=yes, status=yes, left=100, top=100");
			//form.elements['ceksUrl'].value = "";
		}
}

function refreshForm(cardnum){
	document.getElementById('cardnum'+cardNumIndex).value = cardnum;
	if(cardnum.substring(0, 1)=="4"){
		document.getElementById('cardtype'+cardNumIndex).selectedIndex = 1;
	}else if(cardnum.substring(0, 2)=="51" || cardnum.substring(0, 2)=="52"
			 || cardnum.substring(0, 2)=="53" || cardnum.substring(0, 2)=="54"
			 || cardnum.substring(0, 2)=="55"){
		document.getElementById('cardtype'+cardNumIndex).selectedIndex = 2;
	}else if(cardnum.substring(0, 2)=="34" || cardnum.substring(0, 2)=="37"){
		document.getElementById('cardtype'+cardNumIndex).selectedIndex = 3;
	}else{
		document.getElementById('cardtype'+cardNumIndex).selectedIndex = 0;
	}
	// check Tap and Go 
	if(cardnum.substring(0, 4)!= "5599"){
		$("#creditCardPrePayForTnGDiv"+cardNumIndex).hide();
	    $("#creditCardPrePayDiv"+cardNumIndex).show();
	}else{
		$("#creditCardPrePayDiv"+cardNumIndex).hide();
		$("#creditCardPrePayForTnGDiv"+cardNumIndex).show();
		//alert("Pre-payment policy was changed and follow cash policy");
		$("#tapAndGoPolicyDialog").dialog({
		      modal: true,
		      buttons: {
		        Ok: function() {
		          $( this ).dialog( "close" );
		        }
		      }
		});
	}
	
}

var paymentmethodform = function() {
    
	
//	var index = 0;
	function actionPerform(i){
		var index = i;
		
		var sbuid = $('input[name="sbuid"]').val();
		
		if(!$("input[name='paymentMethodDtlList["+index+"].newPayMethodType']").is(':checked')){
			$("input[name='paymentMethodDtlList["+index+"].newPayMethodType']").attr('checked', true);
			show($("input[name='paymentMethodDtlList["+index+"].newPayMethodType']:checked").val());
	    }
		
		$("#autoPayDiv"+index).hide();
		$("#creditCardDiv"+index).hide();
		
		if($("#payMethodTypeA"+index).is(':checked')){
			show("A");
		}else if($("#payMethodTypeC"+index).is(':checked')){
			show("C");
		}else{
			show("E");
		}

		$("#cardtype"+index).attr('disabled', true);
		$( "form" ).bind( "submit", function( event ) {
			$("#cardtype"+index).attr('disabled', false);
		});
		
		/*if($("#thirdPartyBankAccountFalse"+index).is(':checked')){
			$("#bankAccHolderDocType"+index).attr('disabled','disabled');
			$("#bankAccHolderDocNum"+index).attr('readonly','readonly');
		}*/

		$("#bankAccHolderName"+index).val($("#custName").val());
		$("#bankAccHolderDocType"+index).val($("#custDocType").val());
		$("#bankAccHolderDocNum"+index).val($("#custDocNum").val());
		$("#bankAccHolderDocType"+index).attr('disabled', 'disabled');
		$("#bankAccHolderDocNum"+index).attr('readonly', 'readonly');
		

		if($("#thirdPartyCardFalse"+index).is(':checked')){
			if(isCallCenter){
				$("#not3rdpartycc"+index).show();
				$("#not3rdpartyccdocnum"+index).show();
			}
			$("#cardHolderDocType"+index).attr('disabled','disabled');
			$("#cardHolderDocNum"+index).attr('readonly','readonly');
		}else if(isCallCenter){
			$("#not3rdpartycc"+index).hide();
			$("#not3rdpartyccdocnum"+index).hide();
			$("#not3rdpartyccverified"+index).hide();
		}
		
		if($("#bankCode"+index).val()!= '' && $("#branchCode"+index).val()==''){
			getBranch($("#bankCode"+index).val());
			
		};
		
		$("#payMethodTypeM"+index).click(function(event) {
			show("E");
		});

		$("#payMethodTypeA"+index).click(function(event) {
			show("A");
		});

		$("#payMethodTypeC"+index).click(function(event) {
			show("C");
		});
		
		$("#payMethodTypeN"+index).click(function(event) {
			show("N");
		});

		$("#suspend").click(function(event){
			event.preventDefault();
			$("#submitInd").val("SUSPEND");
			$("#ltspaymentform").submit();
		});
		
		/*$("#thirdPartyBankAccountTrue"+index).click(function(event) {
			$("#bankAccHolderDocType"+index).removeAttr('disabled');
			$("#bankAccHolderDocNum"+index).removeAttr('readonly');
			$("#bankAccHolderName"+index).val("");
			$("#bankAccHolderDocType"+index).val("");
			$("#bankAccHolderDocNum"+index).val("");
		});
	
		$("#thirdPartyBankAccountFalse"+index).click(function(event) {
			$("#bankAccHolderName"+index).val($("#custName").val());
			$("#bankAccHolderDocType"+index).val($("#custDocType").val());
			$("#bankAccHolderDocNum"+index).val($("#custDocNum").val());
			$("#bankAccHolderDocType"+index).attr('disabled', 'disabled');
			$("#bankAccHolderDocNum"+index).attr('readonly', 'readonly');
		});*/
		
		$("#thirdPartyCardTrue"+index).click(function(event) {
			$("#cardHolderName"+index).val("");
			$("#cardHolderDocType"+index).removeAttr('disabled');
			$("#cardHolderDocNum"+index).removeAttr('readonly');
			if(isCallCenter){
				$("#not3rdpartycc"+index).hide();
				$("#not3rdpartyccdocnum"+index).hide();
				$("#not3rdpartyccverified"+index).hide();
			}
			$("#cardHolderDocType"+index).val("");
			$("#cardHolderDocNum"+index).val("");
		});

		$("#thirdPartyCardFalse"+index).click(function(event) {
			$("#cardHolderName"+index).val($("#custName").val());
			$("#cardHolderDocType"+index).val($("#custDocType").val());
			$("#cardHolderDocNum"+index).val($("#custDocNum").val());
			if(isCallCenter){
				$("#not3rdpartycc"+index).show();
				$("#not3rdpartyccdocnum"+index).show();
			}
			$("#cardHolderDocType"+index).attr('disabled', 'disabled');
			$("#cardHolderDocNum"+index).attr('readonly', 'readonly');
		});
		
		$("#bankCode"+index).change(function(event){
			getBranch($("#bankCode"+index).val());
		});
		
		$("#branchCode"+index).change(function(event){
			$("#branchCodeHidden"+index).val($("#branchCode"+index).val());
		});

	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	    
	    function getBranch(code){
			var sbuid = $('input[name="sbuid"]').val();
			
			$.ajax({
				url : "ltsbanklookup.html?sbuid=" + sbuid,
				type : 'POST',
				data : "parm=" + code,
				error: function(xhr, ajaxOptions, thrownError){
	                alert(xhr.status + ": " + ajaxOptions + ": "+ thrownError);
	            },
				success: function(data){
					var json = jQuery.parseJSON(data);
					pushBranchOptions(json);
				},
				beforeSend : function() {
					 $.blockUI({ message: null }); 
				}
			});
		}

		function pushBranchOptions(json){
			var output = [];
			var sortedKeys =[];
			$("#branchCode"+index).children().remove();
			output.push('<option value=""> -- SELECT --</option>');
			for (var key in json) {
				sortedKeys.push(key);
			}
			sortedKeys.sort();
			for (var i = 0; i< sortedKeys.length; i++) {
				output.push('<option value="'+ sortedKeys[i] +'">'+ json[sortedKeys[i]] +'</option>');
			}
			$("#branchCode"+index).html(output.join(''));
			$.unblockUI(); 
			
			if($("#branchCodeHidden"+index).val() != '' && $("#branchCode"+index).val() == ''){
				var a = $("#branchCodeHidden"+index).val();
				$("#branchCode"+index).val(a);
			}
		}
		
		
		function setToken(v1, v2, v3, v5){
			var maskedToken = "";
			
			if(v5.length > 0){
				maskedToken = v5.substring(0,4) + "********" + v5.substring(12);
				document.getElementById("token"+index).value = v5;
			} else {
				maskedToken = "";
				document.getElementById("token"+index).value = "";
			}
				
			document.getElementById("mask_token"+index).value = maskedToken;
			
			document.paymentForm.ceksSubmit.value = "N";
			
			alert(document.paymentForm.ceksSubmit.value 
					+ " token " + v5
					+ " mask_token " + maskedToken);
		}
		
		function showSalesMemo(show){
			if(show){
				$("#salesMemoNumDiv"+index).show();
			}else{
				$("#salesMemoNumDiv"+index).hide();
			}
		}
		
		function show( which )
		{
			$("#autoPayDiv"+index).hide();
			$("#creditCardDiv"+index).hide();
			$("#creditCardPrePayDiv"+index).hide();
			$("#creditCardPrePayForTnGDiv"+index).hide();
			$("#existingAcctDiv"+index).hide();
			
			//E - existingAcctDiv, C - creditCardDiv, A-autoPayDiv
			if(which == $("#exPaymentMethodType"+index).val()){
				$("#existingAcctDiv"+index).show();
				showSalesMemo(false);
				return;
			}
			
			if (which=='A'){
				$("#autoPayDiv"+index).show();
				if($("#prepayItemSelectedA"+index).val() != undefined){
					showSalesMemo(true);
				}else{
					showSalesMemo(false);
				}
		    }else if (which=='C'){
				$("#creditCardDiv"+index).show();
				//check Tap and Go
				if($("#cardnum"+index).val().substring(0, 4)!= '5599'){
				    $("#creditCardPrePayDiv"+cardNumIndex).show();
				}else{
					$("#creditCardPrePayForTnGDiv"+cardNumIndex).show();
				}
				showSalesMemo(false);
		    }else if (which=='N'){
		    	$("#autoPayDiv"+index).hide();
				$("#creditCardDiv"+index).hide();
				$("#existingAcctDiv"+index).hide();
				showSalesMemo(false);
			}else {
				$("#existingAcctDiv"+index).show();
				showSalesMemo(false);
				if($("#prepayItemSelectedM"+index).val() != undefined){
					if($("#exPaymentMethodType"+index).val() == 'M'
						|| $("#exPaymentMethodType"+index).val() == 'A'){
						$("#salesMemoNumDiv"+index).show();
					}
				}
			}
		}
	    
		
	}return {
		actionPerform : actionPerform
	};
}();