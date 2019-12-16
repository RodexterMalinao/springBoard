var ltsmodemarrangement = function() {
	
	function performModemLkup(fsa, fsaType, newService) {

		
		var existingService = $('input[id="'+fsaType+'.existingService'+fsa+'"]').val();
		
		if (newService == "") {
			newService = existingService;
		}
		
		var sbuid = $('input[name="sbuid"]').val();
		
		var url = 'ltsacqmodemlkup.html?sbuid=' + sbuid + '&existingService=' + existingService + '&newService=' + newService;

		$.ajax({
			url : url,
			type : 'POST',
			dataType : 'json',
			timeout : 15000,
			error : function(e) {
			},
			success : function(result) {
				if (result.status == 'true') {
					var newModemArrangment = result.modemArrangment;
					$('input[id="'+fsaType+'.newModemArrange'+fsa+'"]').val(newModemArrangment);
					$('span[id="display.'+fsaType+'.modemArrange'+fsa+'"]').html(newModemArrangment);

					if (newService != existingService) {
						$('span[id="display.'+fsaType+'.modemArrange'+fsa+'"]').css('font-weight', 'bold');
					}
					else {
						$('span[id="display.'+fsaType+'.modemArrange'+fsa+'"]').css('font-weight', 'normal');
					}
					
				}
				else {
					var errorMsg = result.errorMsg;
					
					if (errorMsg == undefined || errorMsg == null || errorMsg == '' ) {
						errorMsg = "Cannot found modem arrangment.";
					}
					
					$('span[id="display.'+fsaType+'.modemArrange'+fsa+'"]').html("--");
					$('span[id="display.'+fsaType+'.modemArrange'+fsa+'"]').css('font-weight', 'bold');
					
					alert(errorMsg);
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
	
	function handleExistingFsa() {
		if ($('input[name="modemType"]:checked').val() != "SHARE_EX_FSA"
				|| $('input.existingFsaCheckbox:checked').length != 1) {
			return;
		}
		
		if ($('input.existingFsaCheckbox:checked').is('.isDifferentIa')) {
			$('#existingFsaDiffIaDiv').show();
			showExistingFsaAllowConfirmSameIa();
		}
		
		var selectedFsa = $('input.existingFsaCheckbox:checked').attr('id');
		
		$('.existingFsa' + selectedFsa).each(function(event){ 
			// $(this).attr('disabled', ''); //commented, temporarily for avoiding frontline submit order wrongly
			if($('#isDsOrder').val() != "yes" )
			{
				 $(this).attr('disabled', '');
			}
		});
		
		if ($('input[id="existingFsa.newModemArrange'+selectedFsa+'"]').val() != "") {
			$('span[id=display.existingFsa.modemArrange'+selectedFsa+']').css('font-weight', 'bold');
		}
	}
	
	function handleOtherFsa() {
		if ($('input[name="modemType"]:checked').val() != "SHARE_OTHER_FSA"
				|| $('input.otherFsaCheckbox:checked').length != 1) {
			return;
		}
		
		if ($('input.otherFsaCheckbox:checked').is('.isDifferentIa')) {
			$('#otherFsaDiffIaDiv').show();
			showOtherFsaAllowConfirmSameIa();
		}
		
		var selectedFsa = $('input.otherFsaCheckbox:checked').attr('id');
		
		$('.otherFsa' + selectedFsa).each(function(event){ 
			$(this).attr('disabled', '');
		});
		
		if ($('input[id="otherFsa.newModemArrange'+selectedFsa+'"]').val() != "") {
			$('span[id=display.otherFsa.modemArrange'+selectedFsa+']').css('font-weight', 'bold');
		}
	}
	
	function showExistingFsaOption(selectedFsa) {
		$('.existingFsaNewService').each(function(event){ 
			$(this).attr('value',  '');
			$(this).attr('disabled', 'disabled');
		});
		
		$('.existingFsaUpgradeBandwidth').each(function(event){ 
			$(this).attr('value',  '');
			$(this).attr('disabled', 'disabled');
		});
		
		$('.existingFsa' + selectedFsa).each(function(event){ 
			// $(this).attr('disabled', ''); //commented, temporarily for avoiding frontline submit order wrongly
			if($('#isDsOrder').val() != "yes" )
			{
				 $(this).attr('disabled', '');
			}
		});
		
		$('input[name*="existingFsa.newModemArrange"]').each(function(event){
			$(this).val('');
		});
		
		$('span[id*="existingFsa.modemArrange"]').each(function(event){
			var profileModemArrange = $(this).parent('td').find('input[name*="modemArrange"]').val();
			$(this).html(profileModemArrange);
			$(this).css('font-weight', 'normal');
		});
	}
	
	function showOtherFsaOption(selectedFsa) {
		
		$('.otherFsaNewService').each(function(event){ 
			$(this).attr('value',  '');
			$(this).attr('disabled', 'disabled');
		});
		
		$('.otherUpgradeBandwidth').each(function(event){ 
			$(this).attr('value',  '');
			$(this).attr('disabled', 'disabled');
		});
		
		$('.otherFsa' + selectedFsa).each(function(event){ 
			$(this).attr('disabled', '');
		});
		
		$('input[name*="otherFsa.newModemArrange"]').each(function(event){
			$(this).val('');
		});
		
		$('span[id*="otherFsa.modemArrange"]').each(function(event){
			var profileModemArrange = $(this).parent('td').find('input[name*="modemArrange"]').val();
			$(this).html(profileModemArrange);
			$(this).css('font-weight', 'normal');
		});
		

	}
	
	
	
	function hideAllPanel() {
		$("#newPcdOrderRetrievedDiv").hide();
		$("#newPcdOrderNotFoundDiv").hide();
		$("#newPcdOrderErrorDiv").hide();

		$("#diffFsaUnderIaDiv").hide();
		$("#nowTvOrderDiv").hide();
		$("#pcdSbOrderDiv").hide();
		$("#existingFsaDiv").hide();
		$("#otherFsaDiv").hide();
		$("#lineTypeSelectionDiv").hide();
		
		$("#lostModemDiv").hide();
		$("#rentalRouterDiv").hide();
	}
	
	function showPanel() {
		hideAllPanel();
		showEdfRefDiv();
		var modemType = $('input[name="modemType"]:checked').val(); 
		showRentalRouterDiv();
		showLostModemDiv();
		switch (modemType)  {
			case "STANDALONE" :
				$("#diffFsaUnderIaDiv").show();
				$("#lineTypeSelectionDiv").show();
				break;
			case "SHARE_EX_FSA" :
				$("#existingFsaDiv").show();
				$("#diffFsaUnderIaDiv").show();
				break;
			case "SHARE_PCD" :
				$("#pcdSbOrderDiv").show();
				showPcdOrder();
				break;
			case "SHARE_TV" :
				$("#nowTvOrderDiv").show();
				break;
			case "SHARE_BUNDLE" :
				$("#nowTvOrderDiv").show();
				$("#pcdSbOrderDiv").show();
				showPcdOrder();
				break;
			case "SHARE_OTHER_FSA" :
				$("#otherFsaDiv").show();
				break;
		}
	}
	
	/* =======Lost Modem handling START ======= */
	function showLostModemDiv(){
		var modemType = $('input[name="modemType"]:checked').val(); 
		switch (modemType)  {
			case "SHARE_EX_FSA" :
				if($("[name=rentalRouterInd]:checked").val() == "SHARE_RENTAL_ROUTER"){
					$("#lostModemCheckbox").attr('disabled', true);
				}else{
					$("#lostModemCheckbox").attr('disabled', false);
				}
				$("#modemDiv").show();
				break;
			case "SHARE_OTHER_FSA" :
				if($("[name=rentalRouterInd]:checked").val() == "SHARE_RENTAL_ROUTER"){
					$("#lostModemCheckbox").attr('disabled', true);
				}else{
					$("#lostModemCheckbox").attr('disabled', false);
				}
				$("#modemDiv").show();
				break;
			default :
				$("#lostModemCheckbox").attr('disabled', true).attr('checked', false);
				$("#modemDiv").hide();
				break;
		}
	}
	/* =======Lost Modem handling END ======= */
	
	/* =======Rental Router handling START======= */
	function showRentalRouterDiv(){
		var modemType = $('input[name="modemType"]:checked').val(); 
		switch (modemType)  {
		case "SHARE_EX_FSA" :
			checkExistingFsaRouter();
			break;
		case "SHARE_PCD" :
		case "SHARE_BUNDLE" :
			checkPcdOrderRouter();
			break;
		case "STANDALONE" :
		case "SHARE_OTHER_FSA" :
			$("#rentalRouterDiv").hide();
			$("#rentalRouter").attr('disabled', true);
			$("#BRM").attr('disabled', true);
			break;
		}
	}
	
	function checkPcdOrderRouter(){
		if($('input[name="pcdSbOrderExist"]:checked').val() == "true" && $("#inputPcdSbOrderId").val() != ''){
			var hasRentalRouter = $("#pcdOrderRentalRouter").val();
			var hasMeshRouter = $("#pcdOrderMeshRouter").val();
			if (hasRentalRouter == undefined || hasMeshRouter == undefined ) {
				return;
			}
			if(hasRentalRouter == 'Y' || hasMeshRouter == 'Y'){
				$("#rentalRouter").attr('disabled', false);
				$("#BRM").attr('disabled', false);
				if($("[name=rentalRouterInd]:checked").val() == undefined){
					$("#rentalRouter").attr('checked', true);
			    	$("#rentalRouterAlertMsgDiv").show();
					$("#lostModemCheckbox").attr("disabled", true).attr("checked", false);
				}
				$("#rentalRouterDiv").show();
			}else{
				$("#rentalRouter").attr('disabled', true).attr('checked', false);
				$("#BRM").attr('disabled', true).attr('checked', false);
				$("#rentalRouterDiv").hide();
			}
		}else{
			$("#rentalRouterDiv").hide();
		}
	}
	
	function checkExistingFsaRouter(){
		var selectedFsa = $('.existingFsaCheckbox:checked').attr('id');
		if (selectedFsa == undefined || selectedFsa == "") {
			return;
		}
		var routerGrp = $("#fsaRouterGrp"+selectedFsa).val();
		var meshRouterGrp = $("#fsaMeshRouterGrp"+selectedFsa).val();
		if(isRentalRouter(routerGrp, meshRouterGrp)){
			$("#rentalRouter").attr('disabled', false);
			$("#BRM").attr('disabled', false);
			if($("[name=rentalRouterInd]:checked").val() == undefined){
				$("#rentalRouter").attr('checked', true);
		    	$("#rentalRouterAlertMsgDiv").show();
				$("#lostModemCheckbox").attr("disabled", true).attr("checked", false);
			}
			$("#rentalRouterDiv").show();
		}else{
			$("#rentalRouter").attr('disabled', true).attr('checked', false);
			$("#BRM").attr('disabled', true).attr('checked', false);
			$("#rentalRouterDiv").hide();
		}
	}
	
	function isRentalRouter(routerGrp, meshRouterGrp){
		var rentalRouterGrps = ['4', '5', '6', '7', '9'];
		
		if(jQuery.inArray( routerGrp, rentalRouterGrps ) != -1){
			return true;
		}
		
		if(meshRouterGrp == 'true'){
			return true;
		}
		
		return false;
	}
	
	/* ======Rental Router handling END======= */
	
	
	function showEdfRefDiv() {
		// For call center and premier team
		if ($('#edfRefDiv').length) {
			
			
			if ($('input[name="edfRefExist"]:checked').val() == "true") {
				$('#withEdfRefDiv').show();
			}
			else {
				$('input[name="edfRefNum"]').val('');
				$('#withEdfRefDiv').hide();
			}
			
			
			
			var modemType = $('input[name="modemType"]:checked').val(); 
			switch (modemType)  {
				case "STANDALONE" :
					$('#edfRefDiv').hide();
					break;
				case "SHARE_EX_FSA" :
					
					var selectedFsa = $('.existingFsaCheckbox:checked').attr('id');
					
					if (selectedFsa == undefined || selectedFsa == "") {
						$("#edfRefDiv").hide();
					}
					else if ($('.existingFsaNewService.existingFsa' + selectedFsa).val() != ""
						|| $('.existingFsaUpgradeBandwidth.existingFsa' + selectedFsa).val() != "") {
						$("#edfRefDiv").show();
					}
					else if ($('input[id="existingFsa.existingService' + selectedFsa + '"]').val() != "WG" 
								&& $("input[name='existingFsaER']:checked").val() == 'true') {
						$("#edfRefDiv").show();
					}
					else {
						$("#edfRefDiv").hide();
					}		
					
					break;
				case "SHARE_OTHER_FSA" :
					var selectedOtherFsa = $('.otherFsaCheckbox:checked').attr('id');
					if (selectedOtherFsa == undefined || selectedOtherFsa == "") {
						$("#edfRefDiv").hide();
					}
					else if ($('.otherFsaNewService.existingFsa' + selectedOtherFsa).val() != ""
						|| $('.otherFsaUpgradeBandwidth.existingFsa' + selectedOtherFsa).val() != "") {
						$("#edfRefDiv").show();
					}
					else if ($('input[id="otherFsa.existingService' + selectedOtherFsa + '"]').val() != "WG" 
						&& $("input[name='otherFsaER']:checked").val() == 'true') {
						$("#edfRefDiv").show();
					}
					else {
						$("#edfRefDiv").hide();
					}
					break;					
				case "SHARE_PCD" :
				case "SHARE_TV" :
				case "SHARE_BUNDLE" :
					$("#edfRefDiv").show();
					break;

			}
			$('input[name="edfRefNum"]').val('');
		}
	}
	function showPcdOrder() {
		if ($('input[name="pcdSbOrderExist"]:checked').val() == "true") {
			$('#withPcdOrderDiv').show();
			$('#newPcdOrderRetrievedDiv').show();
			$("#newPcdOrderNotFoundDiv").show();
			$("#newPcdOrderErrorDiv").show();
		}
		else {
			$('#withPcdOrderDiv').hide();
			$('#newPcdOrderRetrievedDiv').hide();
			$("#newPcdOrderNotFoundDiv").hide();
			$("#newPcdOrderErrorDiv").hide();
		}
	}

	function showOtherFsaAllowConfirmSameIa() {
		var allowConfirmSameIa = $('input.otherFsaCheckbox:checked').is('.allowConfirmSameIa');
		if (allowConfirmSameIa) {
			$('#otherFsaConfirmSameIaDiv').show();
		}
		else {
			$('#otherFsaConfirmSameIaDiv').hide();
		}
	}
	
	
	function showExistingFsaAllowConfirmSameIa() {
		var allowConfirmSameIa = $('input.existingFsaCheckbox:checked').is('.allowConfirmSameIa');
		if (allowConfirmSameIa) {
			$('#existingFsaConfirmSameIaDiv').show();
		}
		else {
			$('#existingFsaConfirmSameIaDiv').hide();
		}
	}
	
	function submitForm(action) {
		$('input[name="formAction"]').val(action);
		$('#ltsModemArrangementForm').submit();
	}
	
	function promptUpgradeFsaAlert() {
		var nowTvUpgradeMsg = "Upgrade nowTV service is selected, please submit nowTV / PCD AF";
		var pcdUpgradeMsg = "Upgrade PCD service is selected, please submit PCD upgrade AF";
		
		var selectedFsa = ""; 
		if ($('input[name="modemType"]:checked').val() == "SHARE_EX_FSA") {
			selectedFsa = $('.existingFsaCheckbox:checked').attr('id');
			if (selectedFsa == undefined || selectedFsa == "") {
				return;
			}
			
			if ($('.existingFsaNewService.existingFsa' + selectedFsa).val() != "") {
				alert(nowTvUpgradeMsg);
			}
			
			if ($('.existingFsaUpgradeBandwidth.existingFsa' + selectedFsa).val() != "") {
				alert(pcdUpgradeMsg);
			}
		}
		
		if ($('input[name="modemType"]:checked').val() == "SHARE_OTHER_FSA") {
			selectedFsa = $('.otherFsaCheckbox:checked').attr('id');
			if (selectedFsa == undefined || selectedFsa == "") {
				return;
			}
			
			if ($('.otherFsaNewService.otherFsa' + selectedFsa).val() != "") {
				alert(nowTvUpgradeMsg);
			}
			
			if ($('.otherFsaUpgradeBandwidth.otherFsa' + selectedFsa).val() != "") {
				alert(pcdUpgradeMsg);
			}
		}
	}
	
	function checkFsaParmMsg(selectedFsa) {
		
		var sbuid = $('input[name="sbuid"]').val();
		var url = 'ltsfsaproductparmlkupajax.html?sbuid=' + sbuid + '&fsa=' + selectedFsa;
		var message = '';
		$.ajax({
			async: false,
			url : url,
			type : 'POST',
			dataType : 'json',
			timeout : 15000,
			error : function(e) {
			},
			success : function(result) {
				message = result[0].Message;
			},
			complete : function() {
			},
			
			beforeSend : function() {
			}
		});
		return message;
	}
	
	
	function actionPerform() {
		
		handleExistingFsa();
		handleOtherFsa();
		
		$('#continueBtn').click(function (event) {
			event.preventDefault();
			promptUpgradeFsaAlert();
			submitForm("SUBMIT");
		});
		
		$('#homeBtn').click(function (event) {
			event.preventDefault();
			if(confirm('All inputted information will be cleared. Sure to continue?')){
				submitForm("HOME");
			}
		});
		
		$('a#searchOtherFsaBtn').click(function(event){
			submitForm("SEARCH_OTHER_FSA");
		});
		
		$('a#clearOtherFsaBtn').click(function(event){
			submitForm("CLEAR_OTHER_FSA");
		});
		
		$('a#searchPcdOrderBtn').click(function(event) {
			submitForm("SEARCH_PCD_ORDER");
		});
		
		$('a#clearPcdOrderBtn').click(function(event) {
			submitForm("CLEAR_PCD_ORDER");
		});
		
		
		$('input[name="modemType"]').change(function(event){
			showPanel();
			$('#error_div').hide();
		});
		
		$('input[name="pcdSbOrderExist"]').change(function(event){
			showPcdOrder();
		});	
		
		$('input[name="edfRefExist"]').change(function(event){
			showEdfRefDiv();
		});	
		
		
		$('select.existingFsaNewService').change(function(event){
			var selectedFsa = $('input.existingFsaCheckbox:checked').attr('id');
			var newService = $(this).val();
			showEdfRefDiv();
			performModemLkup(selectedFsa, "existingFsa", newService);
		});

		$('select.otherFsaNewService').change(function(event){
			var selectedFsa = $('input.otherFsaCheckbox:checked').attr('id');
			var newService = $(this).val();
			showEdfRefDiv();
			performModemLkup(selectedFsa, "otherFsa", newService);
		});
		
		
		$('input.lineTypeCheckBox').change(function(event) {
			$('input.lineTypeCheckBox').attr('checked', '');
			$(this).attr('checked', 'checked');
		});
		
		$('input.otherFsaCheckbox').change(function(event) {
			
			$('input.otherFsaCheckbox').attr('checked', '');
			var selectedFsa = $(this).attr('id');
			var message = checkFsaParmMsg(selectedFsa);
			
			if (message != 'NULL' && message != '') {
//				var option = confirm(message);
//				if (option != true) { 
//					return;
//				}
				alert (message);
			}
			
			$(this).attr('checked', 'checked');	
			var isDifferentIa = $('input.otherFsaCheckbox:checked').is('.isDifferentIa');
			
			if (isDifferentIa == true && $('input[id="otherFsa.existingService' + selectedFsa + '"]').val() != "WG") {
				$('#otherFsaDiffIaDiv').show();
				$('#otherFsaConfirmSameIaDiv').show();
				$('input[name="otherFsaER"][value="false"]').attr('checked', true);
				$('input[name="otherFsaConfirmSameIa"]').attr('checked', false);
			}
			else {
				$('#otherFsaDiffIaDiv').hide();
			}
			
			showOtherFsaAllowConfirmSameIa();
			showOtherFsaOption(selectedFsa);
			showEdfRefDiv();
		});

		$('input[name="otherFsaER"]').change(function (event) {
			if ($('input[name="otherFsaER"]:checked').val() == 'false') {
				showOtherFsaAllowConfirmSameIa();
			}
			else {
				$('#otherFsaConfirmSameIaDiv').hide();
				$('input[name="otherFsaConfirmSameIa"]').attr('checked','');
			}
			
			
		});
		
		$('input.existingFsaCheckbox').change(function(event) {
			$('input.existingFsaCheckbox').attr('checked', '');
			var selectedFsa = $(this).attr('id');
			var message = checkFsaParmMsg(selectedFsa);
			
			if (message != 'NULL' && message != '') {
//				var option = confirm(message);
//				if (option != true) { 
//					return;
//				}
				alert (message);
			}
			
			$(this).attr('checked', 'checked');			
			var isDifferentIa = $(this).is('.isDifferentIa');
			if (isDifferentIa && $('input[id="existingFsa.existingService' + selectedFsa + '"]').val() != "WG") {
				$('#existingFsaDiffIaDiv').show();
				$('input[name="existingFsaER"][value="false"]').attr('checked', true);
				$('input[name="existingFsaConfirmSameIa"]').attr('checked', false);
			}
			else {
				$('#existingFsaDiffIaDiv').hide();
			}
			
			showExistingFsaAllowConfirmSameIa(); 

			 showExistingFsaOption(selectedFsa);
			 showEdfRefDiv();
			 
			 checkExistingFsaRouter();
		});
		
		$('input[name="existingFsaER"]').change(function (event) {
			if ($('input[name="existingFsaER"]:checked').val() == 'false') {
				showExistingFsaAllowConfirmSameIa();
			}
			else {
				$('#existingFsaConfirmSameIaDiv').hide();
				$('input[name="existingFsaConfirmSameIa"]').attr('checked','');
			}
			showEdfRefDiv();
		});

		$("[name=rentalRouterInd]").change(function(event){
		    if($("[name=rentalRouterInd]:checked").val() == "SHARE_RENTAL_ROUTER"){
		    	$("#lostModemCheckbox").attr("disabled", true).attr("checked", false);
		    	$("#rentalRouterAlertMsgDiv").show();
		    }else if($("[name=rentalRouterInd]:checked").val() == "BRM"){
		    	$("#lostModemCheckbox").attr("disabled", false);
		    	$("#rentalRouterAlertMsgDiv").hide();
		    }
		 });
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
	    
	    if($('#isPcdBundle').val()=="Yes")
	    {
	    	$('input[name="modemType"]').attr('checked', true);
	    	$('input[name="modemType"]:checked').val("SHARE_PCD");
	    }
	}
	
	return {
		actionPerform : actionPerform,
		showPanel : showPanel
	};
	
	
}();
