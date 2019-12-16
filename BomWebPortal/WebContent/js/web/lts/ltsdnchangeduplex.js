var ltsdnchangeduplex = function() {
	
	function chkReservedDn() {
		var sbuid = $('input[name="sbuid"]').val();
		var srvNum = $("#reservedSrvNum").val();
		var acctCd = $("#reservedAccountCd").val();
		var boc = $("#reservedBoc").val();
		var projectCd = $("#reservedProjectCd").val();
		var isdnchange = "true";
		var url = "ltsacqreserveddnlkup.html?sbuid=" + sbuid + "&srvNum=" + srvNum + "&acctCd=" 
		          + acctCd + "&boc=" + boc + "&projectCd=" + projectCd + "&isdnchange=" + isdnchange;;
		if (srvNum=='') {
			alert('Please input the Reserved DN');
		} else if (srvNum.length<8) {
			alert('Invalid service number');	
		} else {	
			$.ajax({
				url : url,
				type : 'POST',
				dataType : 'json',
				timeout : 15000,
				error : function(e) {
				},
				success : function(result) {
					if (result.status == 'true') {
						$("#reservedDnInd").attr('checked', 'checked');
						$('form[name="ltsDnChangeDuplexForm"]').submit();
					} else {					
						var errorMsg = result.errorMsg;					
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
	}		

	function baCheck(){
		if($("[name=portFromDiffAddr]").is(":checked")){
			$("#baDiv").show();
		}else{
			$("#baDiv").hide();
		}
	}

	function custCheck(){
		if($("[name=portFromDiffCust]").is(":checked")){
			$("#custDiv").show();
		}else{
			$("#custDiv").hide();
		}
	}
		
	function docTypeCheck(){
		if($("#docType").val() == "BR"){
			$(".hkbrName").show();
			$(".nonHkbrName").hide();
		}else{
			$(".hkbrName").hide();
			$(".nonHkbrName").show();
		}
	}
	
	function setNumSelection(){
		val = $('input[name="numSelectionRadio"]:checked').val();
		if (val == 'useNewDn') {				
			$('input[name="numSelection"]').val('USE_NEW_DN');
		}
		if (val == 'keepExistDn') {				
			$('input[name="numSelection"]').val('KEEP_EXIST_DN');
		}
	}
	
	function setSearchMethod(){
		val = $('input[name="searchMethodRadio"]:checked').val();		
		if (val == 'reservedDnRadio') {				
			$('input[name="searchMethod"]').val('RESERVED_DN');
		} else {
			$('input[name="searchMethod"]').val('DN_POOL');
		}	
	}
	
	
	function checkOption(){
		var val = $('input[name="numSelectionRadio"]:checked').val();
		if(val == 'useNewDn'){
			$("#numberSelection").show();
			$("#chargeItems").show();
		}
		if(val == 'keepExistDn'){
			$("#numberSelection").hide();
			$("#chargeItems").hide();
		}
	}
	
	function defaultExDir() {
		$('input[name="includeSrvNumOnExDir"]').attr('checked',true);
	}
	
	function checkNumSelection(){
		if($('input[name="numSelectionRadio"]:checked').val() == "useNewDn"){
			if($('input[name="searchMethodRadio"]:checked').val() != "reservedDnRadio"){
				if($("#reservedDnStringList1").val()  === undefined){
					  alert("Please select new DN.");
					  return false;
				}
		}		
	  }
		return true;
	}
	
	
	function approvalCheck(){
		if ($("#penalty").val() == "AG") {
			$("#approveBtn").hide();
		}
		if($("#penalty").val() == "MW" && $("#channel").val() != 1){
			$("#approveBtn").hide();
		}
		
		$("#penalty").change(function(event) {
			if($("#channel").val() == 1){
				if ($("#penalty").val() == "MW") {
					$("#approveBtn").show();
					console.log("he");
				}
				else {
					if ($(".restrict:checked").length == 0) {
						$("#approveBtn").hide();	
					}
				}
			}						
		});
		
		if($("#penaltyApprovalInd").val() == "true"){
			$("#penalty").hide();
			$("#approveBtn").show();
			$("#approveBtn").text("WAIVE PENALTY APPROVED");
		}
	}
	
	function checkRebate(){
		var result_style = document.getElementById('rebateItem').style;
		$("#penalty").change(function(event) {

			if ($("#penalty").val() == "MW" || $("#penalty").val() == "AW" || $("#penaltyApprovalInd").val() == "true") {				
				result_style.display = 'table-row';
			}else{
				result_style.display = 'none';
			}
		});
		
		if ($("#penalty").val() == "MW" || $("#penalty").val() == "AW" || $("#penaltyApprovalInd").val() == "true") {				
			result_style.display = 'table-row';
		}else{
			result_style.display = 'none';
		}
	}
	
	
	function actionPerform() {

		$("[name=numSelectionRadio]").click(function(){
			checkOption();
		});
		
		$('a#searchBtn').click(function (event) {
			
			var method = $('input[name="searchMethodRadio"]:checked').val();
			if (method == 'noCriteriaRadio') {
				$('input[name="formAction"]').val('SEARCH_BY_NO_CRITERIA');
			}	
			if (method == 'first5to8Radio') {
				$('input[name="formAction"]').val('SEARCH_BY_FIRST_8_DIGITS');
			}
			if (method == 'last1to3Radio') {
				$('input[name="formAction"]').val('SEARCH_BY_LAST_3_DIGITS');
			}
			setNumSelection();
			setSearchMethod();
			if (method != 'reservedDnRadio') {
				$('form[name="ltsDnChangeDuplexForm"]').submit();
			} else {
				alert('DN Search is not for reserved DN, please click continue');
			}
		});
		
		$('a#lockBtn').click(function (event) {
			setNumSelection();
			setSearchMethod();
			$('input[name="formAction"]').val('LOCK_NUMBER');
			$('form[name="ltsDnChangeDuplexForm"]').submit();
		});
		
		$('a#approvalBtn').click(function (event) {
			$('input[name="formAction"]').val('WAIVE_APPROVED');
			$('form[name="ltsDnChangeDuplexForm"]').submit();
		});
		
		$('a#releaseBtn').click(function (event) {
			setNumSelection();
			setSearchMethod();
			$('input[name="formAction"]').val('RELEASE_NUMBER');
			$('form[name="ltsDnChangeDuplexForm"]').submit();
		});
				
		$('a#continueBtn').click(function (event) {
			if(!checkNumSelection()){
				return false;
			}
			event.preventDefault();
			setNumSelection();
			setSearchMethod();
			$('input[name="formAction"]').val('SUBMIT');
			var selection = $('input[name="numSelectionRadio"]:checked').val();
			var method = $('input[name="searchMethodRadio"]:checked').val();			
			if ($('input[name="isfirstDelConfirmed"]').val()=='true') {			
				$('form[name="ltsDnChangeDuplexForm"]').submit();
			} else {	
				if (selection == 'useNewDn' && method == 'reservedDnRadio') {
					chkReservedDn();
				} 
				else {
					$('form[name="ltsDnChangeDuplexForm"]').submit();
				}
			}
		});
		
		if ($('input[name="numSelectionRadio"]:checked').val()==undefined) {
			$('input[value="useNewDn"]').attr('checked',true);
		}
		
		if ($('input[name="searchMethodRadio"]:checked').val()==undefined) {
			$('input[value="noCriteriaRadio"]').attr('checked',true);
		}
		
		$("[name=portFromDiffAddr]").click(function(event){
			baCheck();
		});

		$("[name=portFromDiffCust]").click(function(event){
			custCheck();
		});		

		$("#docType").change(function(){
			docTypeCheck();
		});
	
	
		approvalCheck();
		checkRebate();
		baCheck();
		custCheck();
		docTypeCheck();		
		checkOption();
		defaultExDir();
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();

//
  function chooseOne(cb){
	  var obj = document.getElementsByName("numSelectionStringList");
	  for (i=0; i<obj.length; i++){
	
		if (obj[i]!=cb)	obj[i].checked = false;
	
		else	obj[i].checked = cb.checked;

	}
}