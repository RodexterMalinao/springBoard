var ltspayment = function() {

	function alertErPremium() {
		if ($("select.penalty").val() == "RW" || $("select.penalty").val() == "1W" || $("select.penalty").val() == "PW") {
			alert('Please select 1 premium from the section of Relocation Charge rebate premium');
		}
	}
	
	function validateAcct(acctNo){
		if(acctNo != ''){
			acctLkup(acctNo);
		}else{
			$("#recontractAccountNoError").html("Please input your account number.");
			return false;
		}
	}
	
	function acctLkup(acctNo){
		var sbuid = $("[name='sbuid']").val();
		$.ajax({
			url : "ltsrecontractcustlookup.html?sbuid=" + sbuid + "&reqType=VALID_ACCT&acctNo=" + acctNo,
			type : 'POST',
			async : false,
			success : function(data){
				var obj = jQuery.parseJSON(data);
				if(obj.state == 1){
					$("#recontractAccountNoError").html("");
				}else if(obj.errMsg != undefined && obj.errMsg != ''){
					$("#recontractAccountNoError").html(obj.errMsg);
				}else{
					$("#recontractAccountNoError").html("Please input the correct account number.");
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
		$("#branchCode").children().remove();
		output.push('<option value=""> -- SELECT --</option>');
		for (var key in json) {
			sortedKeys.push(key);
		}
		sortedKeys.sort();
		for (var i = 0; i< sortedKeys.length; i++) {
			output.push('<option value="'+ sortedKeys[i] +'">'+ json[sortedKeys[i]] +'</option>');
		}
		$("#branchCode").html(output.join(''));
		$.unblockUI(); 
		
		if($("#branchCodeHidden").val() != '' && $("#branchCode").val() == ''){
			var a = $("#branchCodeHidden").val();
			$("#branchCode").val(a);
		}
	}
	
	
	function setToken(v1, v2, v3, v5){
		var maskedToken = "";
		
		if(v5.length > 0){
			maskedToken = v5.substring(0,4) + "********" + v5.substring(12);
			document.getElementById("token").value = v5;
		} else {
			maskedToken = "";
			document.getElementById("token").value = "";
		}
			
		document.getElementById("mask_token").value = maskedToken;
		
		document.paymentForm.ceksSubmit.value = "N";
		
		alert(document.paymentForm.ceksSubmit.value 
				+ " token " + v5
				+ " mask_token " + maskedToken);
	}
	
	function checkBillOptions(){
		if($("#cspCheckbox").is(":checked") || $("#cspExistCheckbox").is(":checked")){
			//$(".billListRadio").removeAttr("checked");
			$(".cspAttb").attr("disabled",false);
			
			if(isHKT){
				$("#cspEmail").attr("disabled",true);
			}
			if(isClub){
				$("#clubEmail").attr("disabled",true);
			}
	    	
		}else{
					
			if(!isHKT){
				$("#cspEmail").val('');
				$("#cspMobile").val('');
			}
			if(!isClub){
				$("#clubEmail").val('');
			}
			$("#optOut").val("N");
			$(".cspAttb").attr("disabled", "disabled");
		}
	    if($("#hiddenSelectBillItem").val() != ''){
    		$(".billListRadio[value=" + $("#hiddenSelectBillItem").val() +"]").attr("checked", true);
	    }
	}
	
	function copyEmailBillValue(){
		if($('input[name="emailBillAddress"]').val() != undefined && $('input[name="emailBillAddress"]').val() != '' ){
			if($('#cspEmail').val()== ''){
				$('#cspEmail').val($('input[name="emailBillAddress"]').val());
			}
		}
		
	}
	
	function baCheck(){
		if($("[name=changeBa]").is(":checked")){
			$("#baQuickSearch").attr("readonly", $("#baQuickSearch").val() != '' && $("#billingAddressTextArea").val() != '');
			$("#instantUpdateBaCheckbox").removeAttr("disabled");
			$("#baDiv").show();
		}else{
			$("#instantUpdateBaCheckbox").attr("disabled", true);
			$("#instantUpdateBaCheckbox").removeAttr("checked");
			$("#baDiv").hide();
		}
	}
	
	function handleWqRemark() {
		var show = false;
		$("select.penalty").each(function(event){
			if($(this).val() == "MW" || $(this).val() == "PA") {
				$("#approvalRemarkDiv").show();
				show = true;
			}
		});
		
		if (!show) {
			$("#approvalRemarkDiv").hide();
		}
	}
	
	function checkOptOut(){
		
    	if($("#optOut").is(":checked")){
    		$("#optOutReason").attr("disabled", false);
    		$("#optOutRemark").attr("disabled", false);	
    	}
    	if($("#optIn").is(":checked")){
    		$("#optOutReason").attr("disabled", true);
    		$("#optOutRemark").attr("disabled", true);	
    	}		
	}
	
	function checkCspDummy(){
		if($('#clubEmail').val() != undefined && $('#clubEmail').val().indexOf("theclub.com.hk")> -1 ){
		
			$('#clubEmail').attr('disabled', true);
			$('#clearEmailBtn').show();
			$('#noEmailBtn').hide();
		}else{
			if(!isClub){
				$('#clubEmail').attr('disabled', false);
			}
			$('#clearEmailBtn').hide();
			$('#noEmailBtn').show();
		}
		
		if($('#clubMobile').val() != undefined && $('#clubMobile').val() == '00000000'){
			
			$('#clubMobile').attr('disabled', true);
			$('#clearMobileBtn').show();
			$('#noMobileBtn').hide();
		}else{
			if(!isClub){
				$('#clubMobile').attr('disabled', false);
			}
			$('#clearMobileBtn').hide();
			$('#noMobileBtn').show();
		}
		
		if($('#cspEmail').val() != undefined && $('#cspEmail').val() != "" && $('#cspEmail').val().indexOf("theclub.com.hk")> -1 ){
			$('#cspEmail').attr('disabled', true);
			$('#clearEmailBtn').show();
			$('#noEmailBtn').hide();
		}else{
			if(!isHKT && $("#cspCheckbox").is(":checked") ){
				$('#cspEmail').attr('disabled', false);
			}
			$('#clearEmailBtn').hide();
			$('#noEmailBtn').show();
		}
		
		if($('#cspMobile').val() != "" && $('#cspMobile').val() == '00000000'){		
			$('#cspMobile').attr('disabled', true);
			$('#clearMobileBtn').show();
			$('#noMobileBtn').hide();
		}else{
			if(!isHKT && $("#cspCheckbox").is(":checked")){
				$('#cspMobile').attr('disabled', false);
			}
			$('#clearMobileBtn').hide();
			$('#noMobileBtn').show();
		}
	}
	
	function handleRecontract(){
		if($('#isRecontractCase').val() =='true'){
			$('#paperBillCharge option[value = ""]').attr('disabled',true);
			$('#paperBillCharge option[value = "Y"]').attr('disabled',false);
		}
	}
	
	function handleDocTypeBR(){
		if($('#isDocTypeBR').val() =='true' 
			&& ($('#paperBillCharge_bom').val() == null || $('#paperBillCharge_bom').val() == '' || $('#paperBillCharge_bom').val() == 'NONE')){
			$('#paperBillChargeDiv').hide();
			$('#waiveReasonDiv').hide();			
		}else{
			$('#paperBillChargeDiv').show();
			$('#waiveReasonDiv').show();
		}
	}
	
	function handleWaiveReason(){
		if($('#paperBillCharge_bom').val() != 'G' 
			&& $('#paperBillCharge_bom').val() != '' 
			&& $('#paperWaiveReason_bom').val() != null
			&& $('#paperBillCharge_bom').val() != 'E'
			&& $('#paperBillCharge').val() != 'Y'
			&& $('#paperBillCharge').val() != 'NONE'){
			$('#waiveReasonDiv').show();
		}else{
			$('#waiveReasonDiv').hide();
		}
		$('#paperBillCharge').change(function () {
			if($('#paperBillCharge').val() == 'W'){
				$('#waiveReasonDiv').show();
			}else{
				$('#waiveReasonDiv').hide();
				 $('#paperWaiveReason').val(null);
			}
		});
	
	}

	function checkPaperWaiveReaCd(){
    	if($("#paperWaiveReason").val() == '9'){ //Others
        	$("#paperBillWaiveOtherStaffId").removeAttr("disabled");
    	}else{
        	$("#paperBillWaiveOtherStaffId").attr("disabled", "disabled");
    	}
    }
	
	function bomChargeCheck(){
	   if($('#allowModifyInd').val() == 'true'){
		   $('#paperBillCharge').attr("disabled", false); 
			$('#paperWaiveReason').attr("disabled", false);
	   }else{
		   $('#paperBillCharge').attr("disabled", true); 
			$('#paperWaiveReason').attr("disabled", true);
	   }	
	}
	
	function actionPerform(){
		
		var sbuid = $('input[name="sbuid"]').val();
		handleDocTypeBR();
		handleWaiveReason();
		checkPaperWaiveReaCd();
		bomChargeCheck();
		baCheck();
	
		handleRecontract();
		handleWqRemark();
		

		$("#autoPayDiv").hide();
		$("#creditCardDiv").hide();
		if($("#exPaymentMethod").val() == "Cash"){
			$("#changeReason").attr("disabled", "disabled");
		}
		if($("#payMethodTypeA").is(':checked')){
			show("A");
		}else if($("#payMethodTypeC").is(':checked')){
			show("C");
		}else{
			show("E");
		}
		
		$("select.penalty").change(function(event){
			handleWqRemark();
		});
		
		$('#approvalRemarkBtn').click(function(event){
			event.preventDefault();
			var win = window.open('ltswqremark.html', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=600,width=1024');
		});
		
		copyEmailBillValue();
		
		$('#emailBillAddress').change(function(event){
			copyEmailBillValue();
		});
		
		$("#bankAccHolderName").val($("#custName").val());
		$("#bankAccHolderDocType").val($("#custDocType").val());
		$("#bankAccHolderDocNum").val($("#custDocNum").val());
		$("#bankAccHolderDocType").attr('disabled', 'disabled');
		$("#bankAccHolderDocNum").attr('readonly', 'readonly');

		if($("#thirdPartyCardFalse").is(':checked')){
			if(isCallCenter){
				$(".not3rdpartycc").show();
			}
			$("#cardHolderDocType").attr('disabled','disabled');
			$("#cardHolderDocNum").attr('readonly','readonly');
		}else if(isCallCenter){
			$(".not3rdpartycc").hide();
		}
		
		if($("#bankCode").val()!= '' && $("#branchCode").val()==''){
			getBranch($("#bankCode").val());
			
		};
		
		$("#payMethodTypeE").click(function(event) {
			show("E");
		});

		$("#payMethodTypeA").click(function(event) {
			show("A");
		});

		$("#payMethodTypeC").click(function(event) {
			show("C");
		});
		
		$("#submit").click(function(event) {
			event.preventDefault();
			if($("#recontractDiv").val() != null){
				var acctNo = $("#recontractAccountNo").val();
				validateAcct(acctNo);
				if($("#recontractAccountNoError").html() != ''){
					return;
				}
			}
			
			if($('#cspEmail').val() != undefined && $('#cspEmail').val().indexOf("theclub.com.hk")> -1 || $('#cspMobile').val() == '00000000'){
				alert('Dummy email address / mobile number is in used, will not register My HKT');
			}
		
			alertErPremium();
			
			$("#hiddenSelectBillItem").val($('.billListRadio:checked').val());
			
			
			$("#submitInd").val("SUBMIT");
			if($("#payMethodTypeE").is(':checked')){
				$("#prepayItemSelectedE").removeAttr('disabled');
			}else if($("#payMethodTypeA").is(':checked')){
				$("#prepayItemSelectedA").removeAttr('disabled');
			}else if($("#payMethodTypeC").is(':checked')){
				$("#prepayItemSelectedC").removeAttr('disabled');
			}
			$("#payMethodTypeA").removeAttr('disabled');
			$("#payMethodTypeC").removeAttr('disabled');
			$("#bankAccHolderDocType").removeAttr('disabled');
			$("#cardHolderDocType").removeAttr('disabled');
			$(".cspAttb").removeAttr('disabled');
			$("#ltspaymentform").submit();
		});
		
		$("#suspend").click(function(event){
			event.preventDefault();
			$("#submitInd").val("SUSPEND");
			$("#ltspaymentform").submit();
		});
		
		$("#thirdPartyCardTrue").click(function(event) {
			$("#cardHolderName").val("");
			$("#cardHolderDocType").removeAttr('disabled');
			$("#cardHolderDocNum").removeAttr('readonly');
			if(isCallCenter){
				$(".not3rdpartycc").hide();
			}
			$("#cardHolderDocType").val("");
			$("#cardHolderDocNum").val("");
		});

		$("#thirdPartyCardFalse").click(function(event) {
			$("#cardHolderName").val($("#custName").val());
			$("#cardHolderDocType").val($("#custDocType").val());
			$("#cardHolderDocNum").val($("#custDocNum").val());
			if(isCallCenter){
				$(".not3rdpartycc").show();
			}
			$("#cardHolderDocType").attr('disabled', 'disabled');
			$("#cardHolderDocNum").attr('readonly', 'readonly');
		});
		
		$("#bankCode").change(function(event){
			getBranch($("#bankCode").val());
		});
		
		$("#branchCode").change(function(event){
			$("#branchCodeHidden").val($("#branchCode").val());
		});

	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
		
	    checkBillOptions();
	    
	    $("#cspCheckbox").click(function(event){
	    	checkBillOptions();
	    });


	// CSP 
	  
	    checkCspDummy();
	    checkOptOut();
    
	    if(isHKT && !isClub){
	    	
	    	if( $('#clubEmail').val() != undefined && $('#clubEmail').val() !=""){
	    		$('#copyInfoBtn').hide();
	    		$('#clearEmailBtn').show();
	    	}else{
	    		$('#copyInfoBtn').show();
	    		$('#clearEmailBtn').hide();
	    	}
		}
	    
	    $(".cspRadio").click(function(){
	    	checkOptOut();
	    });
		
		$('#copyInfoBtn').click(function(event){
			event.preventDefault();
			if(isHKT){
				$('#clubEmail').val($('#cspEmail').val());
				$('#copyInfoBtn').hide();
				$('#clearEmailBtn').show();
			}
			else if(isClub){
				$('#cspEmail').val($('#clubEmail').val());
			}
		});		
		
		$('#noEmailBtn').click(function(event){
			if(isHKT && !isClub){
				$('#clubEmail')[0].size = $('#dummyClubEmail').val().length+1;
				$('#clubEmail').val($("#dummyClubEmail").val());
				$('#clubEmail').attr('disabled', true);
				
			}else{			
				$('#cspEmail')[0].size = $('#dummyClubEmail').val().length+1;
				$('#cspEmail').val($("#dummyClubEmail").val());
				$('#cspEmail').attr('disabled', true);
			}
			$('#clearEmailBtn').show();
			$('#noEmailBtn').hide();
		});
		
		$('#noMobileBtn').click(function(event){
			if(isClub || (!isClub && !isHKT)){
				$('#cspMobile').val("00000000");
				$('#cspMobile').attr('disabled', true);
			}else{
				$('#clubMobile').val("00000000");
				$('#clubMobile').attr('disabled', true);
			}
			$('#clearMobileBtn').show();
			$('#noMobileBtn').hide();
			
		});
		
		$('#clearEmailBtn').click(function(event){
			if(isHKT && !isClub){
				$('#clubEmail').val("");
				$('#clubEmail').attr('disabled', false);
				$('#noEmailBtn').show();
				$('#clearEmailBtn').hide();
			}else{
				$('#cspEmail').val("");
				$('#cspEmail').attr('disabled', false);
				$('#clearEmailBtn').hide();
				$('#noEmailBtn').show();
			}
		});
		
		$('#clearMobileBtn').click(function(event){
			if(isClub || (!isClub && !isHKT)){
				$('#cspMobile').val("");
				$('#cspMobile').attr('disabled', false);
			}else{
				$('#clubMobile').val("");
				$('#clubMobile').attr('disabled', false);
			}
			$('#clearMobileBtn').hide();
			$('#noMobileBtn').show();
		});
		
    // CSP END
	    
	    $(".billListRadio").click(function(){
	    	$("#hiddenSelectBillItem").val(this.value);
	    });
	    
	    if($("#hiddenSelectBillItem").val() != ''){
	    	if($("#cspCheckbox").is(":checked")){
//	    		$(".billListCheckbox[value=" + $("#hiddenSelectBillItem").val() +"]").attr("checked", true);
	    	}else{
	    		$(".billListRadio[value=" + $("#hiddenSelectBillItem").val() +"]").attr("checked", true);
	    	}
	    }
	    
	    if($("#recontractDiv").val() != null){ 
	    	$("#recontractAccountNo").change(function(){
	    		var acctNo = $("#recontractAccountNo").val();
	    		validateAcct(acctNo);
	    		if($("#recontractAccountNoError").html() == ''){
					location.reload();
				}
	    	});
	    }

		$('a#clearBaInputAddress').click(function(event) {
			$("#billingAddressTextArea").val("");
			$('input[name="baQuickSearch"]').val("");
			$('input[name="baQuickSearch"]').attr('readonly', '');
		});

	    $("[name=changeBa]").click(function(event){
	    	baCheck();
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
		
		
		
		$("#paperWaiveReason").change(function(){
			checkPaperWaiveReaCd();
		});

	}return {
		actionPerform : actionPerform
	};
}();