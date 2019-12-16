var ltsaddressrollout = function() {
	
	function showAddressPanel() {
		if ($('input[name="externalRelocate"]:checked').val() == "false") {
			$('div#existIaDiv').show();
			$('div#existRolloutDiv').show();
			$('div#erIaDiv').hide();
			
			$('a#clearInputAddress').trigger('click');
			$('a#clearBaInputAddress').trigger('click');
		}
		else {
			$('div#existRolloutDiv').hide();
			$('div#existIaDiv').hide();
			$('div#erIaDiv').show();
		}
		
		if ($('input[name="serviceBoundary"]').val() == "") {
			$('input[name="quickSearch"]').attr('readonly', '');
		}
		else {
			$('input[name="quickSearch"]').attr('readonly', 'true');
		}
	}

	
	function performRolloutCheck() {
		var sbuid = $('input[name="sbuid"]').val();
		var sb = $('input[name="serviceBoundary"]').val();
		var er = $('input[name="externalRelocate"]:checked').val();
		var floor = $('input[name="floor"]').val();
		var flat = $('input[name="flat"]') .val();
		
		if (sb == null || sb == '') {
			return;
		}
		
		var url = 'ltsrolloutcheck.html?sbuid=' + sbuid + '&sb=' + sb + '&floor=' + floor + '&flat=' + flat + '&er=' + er;
		$.ajax({
			url : url,
			type : 'POST',
			dataType : 'json',
			timeout : 15000,
			error : function(e) {
			},
			success : function(result) {
				result = result[0];
				if (result.status == 'true') {
					$('span#rolloutLtsHousingCat').html(result.addressRollout.ltsHousingCatDesc);
					$('span#rolloutPh').html(result.addressRollout.hktPremier == '' ? '-': result.addressRollout.hktPremier);
					$('span#rollout2nBuilding').html(result.addressRollout.is2nBuilding ? 'Y' : 'N');
					$('span#rolloutBandwidthCoverage').html(result.addressRollout.availableBandwidth == "" ? "N/A" : result.addressRollout.availableBandwidth);
					$('span#rolloutRsourceShortage').html(result.addressRollout.pcdResourceShortage == null || result.addressRollout.pcdResourceShortage == '' ? 'N' :result.addressRollout.pcdResourceShortage);
					$('span#rolloutLtsBlackListAddr').html(result.addressRollout.ltsAddressBlacklist ? 'Y' : 'N');
					$('span#rolloutImsBlackListAddr').html(result.addressRollout.imsAddressBlacklist ? 'Y' : 'N');
					$('span#rolloutFieldPermit').html(result.addressRollout.fieldWorkPermit);
					if(result.addressRollout.eyeCoverage){
						$('span#rolloutSuggestSrdReason').html(result.addressRollout.suggestedSrdReason);
						$("#rolloutSuggestSrdReasonRow").show();
					}else{
						$("#rolloutSuggestSrdReasonRow").hide();
					}
					$('span#rolloutEyeCoverage').html(result.addressRollout.eyeCoverage ? 'Y' : 'N');
					$('span#rolloutPeCoverage').html(result.addressRollout.peCoverage ? 'Y' : 'N');
					$('span#rolloutFttc').html(result.addressRollout.fttcInd ? 'Y' : 'N');
					$('span#rolloutFiberBlockage').html(result.addressRollout.fiberBlockageInd ? 'Y' : 'N');
					$('span#rolloutNumOfEye').html(result.numOfEye);
					
					if (result.addressRollout.eyeCoverage == false) {
						$('span#rolloutEyeCoverage').css('color', 'red');
					}
					else {
						$('span#rolloutEyeCoverage').css('color', 'black');
					}
					
					if (result.addressRollout.pcdResourceShortage != null && result.addressRollout.pcdResourceShortage != '') {
						$('span#rolloutRsourceShortage').css('color', 'red');
					}
					else {
						$('span#rolloutRsourceShortage').css('color', 'black');
					}
					
					if (result.addressRollout.ltsAddressBlacklist) {
						$('span#rolloutLtsBlackListAddr').css('color', 'red');
					}
					else {
						$('span#rolloutLtsBlackListAddr').css('color', 'black');
					}
					
					if (result.addressRollout.imsAddressBlacklist) {
						$('span#rolloutImsBlackListAddr').css('color', 'red');
					}
					else {
						$('span#rolloutImsBlackListAddr').css('color', 'black');
					}
					
					if (result.addressRollout.uimBlockage != null && result.addressRollout.uimBlockage.size != 0
							&& floor != "") {
						var blockageMessage = "";
						var blockage = result.addressRollout.uimBlockage;
						for (var i=0;  i<blockage.length; i++) {
						    blockageMessage += "Flat "+ blockage[i].flat + " - Blockage Code " + blockage[i].blockageCd + ", " + blockage[i].blockageDesc + ", Blockage Date: " + blockage[i].blockageDate;
						    blockageMessage += "<br/>";
						}
						if (blockageMessage.length != 0) {
							$('div#rolloutBlockageDiv').show();						
							$('div#rolloutUimBlockage').html(blockageMessage);	
						}
					}
					
					
					$('div#rolloutDiv').show();
					
				}
				else {
					var errorMsg = result[0].errorMsg;
					
					if (errorMsg == undefined || errorMsg == null || errorMsg == '' ) {
						errorMsg = "Cannot found address rollout information.";
					}
					
					$('span#rolloutErrorMsg').html(errorMsg);
					$('div#rolloutErrorDiv').show();
				}
			},
			complete : function() {
				 $.unblockUI(); 
			},
			
			beforeSend : function() {
				$('div#rolloutDiv').hide();
				$('div#rolloutErrorDiv').hide();
				$('div#rolloutBlockageDiv').hide();
				 $.blockUI({ message: null }); 
			}
		});
	}
	
	function erBaCheck(){
		if($("[name=baCaAction]:checked").val() == "DIFFERENT_FROM_NEW_OLD_IA"){
			$("#baQuickSearch").attr("readonly", $("#baQuickSearch").val() != '' && $("#billingAddressTextArea").val() != '');
			$("#erBaDiv").show();
//			$("#instantUpdateBa").removeAttr("disabled");
		}else{
			$("#erBaDiv").hide();
//			$("#instantUpdateBa").attr("disabled", true);
			
		}
	}
	
	function dnChangeAlert(){
		if ($('input[name="externalRelocate"]:checked').val() == "true") {
			alert("The charge of DN change will be auto waived. No approval required.");
		}
	}
	function actionPerform() {
		var sbuid = $('input[name="sbuid"]').val();

		showAddressPanel();
    	erBaCheck();
		
		if ($('input[name="serviceBoundary"]').val() != "") {
			performRolloutCheck();
		}
		
		$('input[name="quickSearch"]').autocomplete("imsaddresslookup.html?type=address", {
			//minChars: 4,
			minChars : 1,
			delay : 600,
			selectFirst : false,
			max : 30,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}
		});
		
		$('input[name="externalRelocate"]').change(function(event) {
			showAddressPanel();
			$('div#errorMsgDiv').hide();

		});

		$('input[name="serviceBoundary"]').change(function(event) {
			performRolloutCheck();
		});
		
		$('input[name="floor"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="floor"]').focusout(function(event) {
			performRolloutCheck();
		});
		
		$('input[name="flat"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="flat"]').focusout(function(event) {
			performRolloutCheck();
		});
		
		$('a#clearInputAddress').click(function(event) {
			$('input[name="quickSearch"]').val('');
			$('input[name="quickSearch"]').attr('readonly', '');
			
			$('input[name="flat"]').val('');
			$('input[name="flat"]').attr('readonly', '');
			
			$('input[name="floor"]').val('');
			$('input[name="floor"]').attr('readonly', '');
			
			$('input[name="lotNum"]').val('');
			$('input[name="lotHouseInd"]').val('');
			$('input[name="buildingName"]').val('');
			$('input[name="streetNum"]').val('');
			$('input[name="streetName"]').val('');
			$('input[name="streetCatgDesc"]').val('');
			$('input[name="streetCatgCode"]').val('');
			$('input[name="serviceBoundary"]').val('');
			
			$('input[name="districtDesc"]').val('');
			$('input[name="sectionDesc"]').val('');
			$('input[name="areaDesc"]').val('');
			
			$('select[name="sectionCode"]').val('');
			$('select[name="districtCode"]').val('');
			$('select[name="areaCode"]').val('');
			
			$('div#rolloutDiv').hide();
			$('div#rolloutErrorDiv').hide();
			$('div#errorMsgDiv').hide();
		});
		
		$('a#clearBaInputAddress').click(function(event) {
			$("#billingAddressTextArea").val("");
			$('input[name="baQuickSearch"]').val("");
			$('input[name="baQuickSearch"]').attr('readonly', '');
		});
		
		$('a#continueBtn').click(function(event) {
			event.preventDefault();
			$("form#ltsAddressRolloutForm").submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	    
	    $("[name=baCaAction]").change(function(event){
	    	erBaCheck();
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
			max : 30,
			matchSubset : false,
			highlight : false,
			ba :true,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}
		});
		
		
		//##todo## Max.r.meng
		//dnChangeAlert();
	}
	return {
		actionPerform : actionPerform
	};
	
	
}();