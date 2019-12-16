var ltsacqnumselectionandpipb = function() {
	
	function chkReservedDn() {
		var sbuid = $('input[name="sbuid"]').val();
		var srvNum = $('input[name="reservedSrvNum"]').val();
		var acctCd = $('input[name="reservedAccountCd"]').val();
		var boc = $('input[name="reservedBoc"]').val();
		var projectCd = $('input[name="reservedProjectCd"]').val();		
		var url = "ltsacqreserveddnlkup.html?sbuid=" + sbuid + "&srvNum=" + srvNum + "&acctCd=" 
		          + acctCd + "&boc=" + boc + "&projectCd=" + projectCd + "&sbOrderId=" 
		          + sbOrderId + "&isNowDrgDownTime=" + isNowDrgDownTime;
		if (srvNum=='') {
			alert(plsInputReservedDN);
		} else if (srvNum.length<8) {
			alert(invalidReservedDN);	
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
						$('form[name="ltsAcqNumSelectionAndPipbForm"]').submit();
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
	
	function chkPipbDn() {		
		var sbuid = $('input[name="sbuid"]').val();
		var pipbDn = $('input[name="pipbInfo.pipbSrvNum"]').val();
		var acctCd = $('input[name="pipbInfo.pipbAccountCd"]').val();
		var boc = $('input[name="pipbInfo.pipbBoc"]').val();
		var projectCd = $('input[name="pipbInfo.pipbProjectCd"]').val();
		var action = $('input[name="pipbInfo.pipbAction"]').val();
		var duplexDn = $('input[name="pipbInfo.duplexDn"]').val();
		var url = "ltsacqpipbdnlkup.html?sbuid=" + sbuid + "&srvNum=" + pipbDn + "&acctCd=" 
		          + acctCd + "&boc=" + boc + "&projectCd=" + projectCd + "&action=" + action 
		          + "&duplexInd=N" + "&sbOrderId=" + sbOrderId + "&isNowDrgDownTime=" + isNowDrgDownTime;				
		if (pipbDn=='') {
			alert(plsInputPIPBDN);		
		} else if (pipbDn.length<8) {
			alert(invalidPIPBDN);		
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
						$('input[name="pipbInfo.dnStatus"]').val(result.serviceInventSts);
						$('input[name="pipbInfo.portBack"]').val(result.isPortBack);
						if ($('input[name="pipbInfo.duplexIndStr"]:checked').val()=='Y' && duplexDn!='') {
							chkDuplexDn();
						} else {
							$('form[name="ltsAcqNumSelectionAndPipbForm"]').submit();
						}
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
	
	function chkDuplexDn() {
		var sbuid = $('input[name="sbuid"]').val();
		var duplexDn = $('input[name="pipbInfo.duplexDn"]').val();
		var action = $('input[name="pipbInfo.pipbAction"]').val();		
		var url = "ltsacqpipbdnlkup.html?sbuid=" + sbuid + "&srvNum=" + duplexDn 
		+ "&action=" + action + "&duplexInd=Y" + "&sbOrderId=" + sbOrderId + "&isNowDrgDownTime=" + isNowDrgDownTime;
		if (duplexDn!='' && duplexDn.length<8) {
			alert(invalidDuplexDN);
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
						$('input[name="pipbInfo.duplexDnStatus"]').val(result.serviceInventSts);
						$('form[name="ltsAcqNumSelectionAndPipbForm"]').submit();
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
	
	function chkReservedDnAndPipbDn() {
		var sbuid = $('input[name="sbuid"]').val();
		var srvNum = $('input[name="reservedSrvNum"]').val();
		var acctCd = $('input[name="reservedAccountCd"]').val();
		var boc = $('input[name="reservedBoc"]').val();
		var projectCd = $('input[name="reservedProjectCd"]').val();
		var url = "ltsacqreserveddnlkup.html?sbuid=" + sbuid + "&srvNum=" + srvNum + "&acctCd=" 
		          + acctCd + "&boc=" + boc + "&projectCd=" + projectCd;		
		if (srvNum=='') {
			alert(plsInputReservedDN);
		} else if (srvNum.length<8) {
			alert(invalidServiceNumber);
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
						chkPipbDn();
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
	
	function reuseSocketCheck(){		
		if($("#reuseExSocket").is(":checked") == false){
			alert(reuseSocketMandatory);
			defaultReuseSocket();
			if(isContainPreInstallVAS =='true')
			{
				$("#socketTypeDiv2").hide();
			}
		} else {
			$("#socketTypeDiv").show();
			if(isContainPreInstallVAS !='true')
			{
				$("#socketTypeDiv2").show();
			}
			else
			{
				$("#socketTypeDiv2").hide();
			}
		}			
	}
	
	function custCheck(){
		if($("#portFromDiffCust").is(":checked") == true){
			$("#custDiv").show();
		}else{
			$("#custDiv").hide();
		}
	}
	
	function baCheck(){
		if($('input[name="pipbInfo.portFromDiffAddrStr"]:checked').val()=='N') {
			$("#baDiv").show();
			$("#baDiv3").hide();
			$("#baDiv4").hide();
			$("#baDiv4").hide();
		} else if($('input[name="pipbInfo.portFromDiffAddrStr"]:checked').val()=='Y') {
			$("#baDiv").show();
			$("#baDiv3").show();
			$("#baDiv4").show();
			$("#baDiv5").show();
		} else {
			$("#baDiv").hide();
		}	
	}
	
	function baChange(){
		if($('input[name="pipbInfo.portFromDiffAddrStr"]:checked').val()=='N') {
			$("#baDiv").show();
			$("#baDiv3").hide();
			$("#baDiv4").hide();
			$("#baDiv4").hide();
			$('input[name="pipbInfo.quickSearch"]').val('');			
			$('input[name="pipbInfo.address.flat"]').val($('input[name="pipbInfo.rolloutAddress.flat"]').val());			
			$('input[name="pipbInfo.address.floor"]').val($('input[name="pipbInfo.rolloutAddress.floor"]').val());
			$('input[name="pipbInfo.address.block"]').val('');
			$('input[name="pipbInfo.address.lotNum"]').val($('input[name="pipbInfo.rolloutAddress.lotNum"]').val());
			$('input[name="pipbInfo.address.buildingName"]').val($('input[name="pipbInfo.rolloutAddress.buildingName"]').val());
			$('input[name="pipbInfo.address.streetNum"]').val($('input[name="pipbInfo.rolloutAddress.streetNum"]').val());
			$('input[name="pipbInfo.address.streetName"]').val($('input[name="pipbInfo.rolloutAddress.streetName"]').val());
			$('input[name="pipbInfo.address.streetCatgCode"]').val($('input[name="pipbInfo.rolloutAddress.streetCatgCode"]').val());
			$('input[name="pipbInfo.address.streetCatgDesc"]').val($('input[name="pipbInfo.rolloutAddress.streetCatgDesc"]').val());			
			$('input[name="pipbInfo.address.districtCode"]').val($('input[name="pipbInfo.rolloutAddress.districtCode"]').val());
			$('input[name="pipbInfo.address.districtDesc"]').val($('input[name="pipbInfo.rolloutAddress.districtDesc"]').val());			
			$('input[name="pipbInfo.address.sectionCode"]').val($('input[name="pipbInfo.rolloutAddress.sectionCode"]').val());
			$('input[name="pipbInfo.address.sectionDesc"]').val($('input[name="pipbInfo.rolloutAddress.sectionDesc"]').val());
			$('input[name="pipbInfo.address.areaCode"]').val($('input[name="pipbInfo.rolloutAddress.areaCode"]').val());
			$('input[name="pipbInfo.address.areaDesc"]').val($('input[name="pipbInfo.rolloutAddress.areaDesc"]').val());
			$('input[name="pipbInfo.address.serviceBoundary"]').val($('input[name="pipbInfo.rolloutAddress.serviceBoundary"]').val());				
		} else if($('input[name="pipbInfo.portFromDiffAddrStr"]:checked').val()=='Y') {
			$("#baDiv").show();
			$("#baDiv3").show();
			$("#baDiv4").show();
			$("#baDiv5").show();
			clearba();
		} else {
			$("#baDiv").hide();
		}	
	}
	
	function clearba(){
		$('input[name="pipbInfo.quickSearch"]').val('');			
		$('input[name="pipbInfo.address.flat"]').val('');			
		$('input[name="pipbInfo.address.floor"]').val('');			
		$('input[name="pipbInfo.address.block"]').val('');
		$('input[name="pipbInfo.address.lotNum"]').val('');
		$('input[name="pipbInfo.address.buildingName"]').val('');
		$('input[name="pipbInfo.address.streetNum"]').val('');
		$('input[name="pipbInfo.address.streetName"]').val('');
		$('input[name="pipbInfo.address.streetCatgCode"]').val('');
		$('input[name="pipbInfo.address.streetCatgDesc"]').val('');			
		$('input[name="pipbInfo.address.districtCode"]').val('');
		$('input[name="pipbInfo.address.districtDesc"]').val('');			
		$('input[name="pipbInfo.address.sectionCode"]').val('');
		$('input[name="pipbInfo.address.sectionDesc"]').val('');
		$('input[name="pipbInfo.address.areaCode"]').val('');
		$('input[name="pipbInfo.address.areaDesc"]').val('');
		$('input[name="pipbInfo.address.serviceBoundary"]').val('');
	}	
	
	function duplexCheck(){
		var val = $('input[name="numSelectionRadio"]:checked').val();
		if($('input[name="pipbInfo.duplexIndStr"]:checked').val()=='Y') {
			$("#duplexDiv1").show();
			$("#duplexDiv2").show();
			$("#duplexDiv4").show();
			if (val == 'usePipbDn') {
				$("#duplexDiv3").show();
			}
		}else{
			$("#duplexDiv1").hide();
			$("#duplexDiv2").hide();
			$("#duplexDiv3").hide();
			$("#duplexDiv4").hide();
		}	
	}
	
	function docTypeCheck(){
		if($("#docType").val() == "BS"){
			$(".hkbrName").show();
			$(".nonHkbrName").hide();
		} else if($("#docType").val() == "HKID" || $("#docType").val() == "PASS"){
			$(".hkbrName").hide();
			$(".nonHkbrName").show();
		} else {
			$(".hkbrName").hide();
			$(".nonHkbrName").hide();
		}
	}
	
	function waiveDnChrgCheck(){
		if($("#waiveDnChrg").is(":checked") == false){
			alert(waiveChargeForDNChangeMan);
			defaultWaiveDnChrg();
		}			
	}
	
	function setNumSelection(){
		var val = $('input[name="numSelectionRadio"]:checked').val();
		if (val == 'useNewDn') {				
			$('input[name="numSelection"]').val('USE_NEW_DN');
		}	
		if (val == 'useNewDnAndPipbDn') {				
			$('input[name="numSelection"]').val('USE_NEW_DN_AND_PIPB_DN');
		}
		if (val == 'usePipbDn') {				
			$('input[name="numSelection"]').val('USE_PIPB_DN');
		}
	}
	
	function setPipbAction(){
		var val = $('input[name="numSelectionRadio"]:checked').val();		
		if (val == 'useNewDnAndPipbDn') {				
			$('input[name="pipbInfo.pipbAction"]').val('NEW_DN');
		}
		if (val == 'usePipbDn') {				
			$('input[name="pipbInfo.pipbAction"]').val('PIPB_DN');
		}
	}
	
	function setDuplexAction(){		
		var val = $('input[name="pipbInfo.duplexRadio"]:checked').val();
		if (val == 'disconnect') {				
			$('input[name="pipbInfo.duplexAction"]').val('DISCONNECT');
		}	
		if (val == 'retain') {				
			$('input[name="pipbInfo.duplexAction"]').val('RETAIN');
		}
		if (val == 'portInTogether') {				
			$('input[name="pipbInfo.duplexAction"]').val('PORT_IN_TOGETHER');
		}
	}
	
	function setSearchMethod(){
		var method = $('input[name="searchMethodRadio"]:checked').val();
		var selection = $('input[name="numSelectionRadio"]:checked').val();
		if (selection == 'useNewDn') {				
			if (method == 'reservedDnRadio') {
				$('input[name="searchMethod"]').val('RESERVED_DN');
		    } else {
			    $('input[name="searchMethod"]').val('DN_POOL');
		    }
		}
		if (selection == 'useNewDnAndPipbDn') {
			if (method == 'reservedDnRadio') {
			    $('input[name="searchMethod"]').val('RESERVED_DN_THEN_PIPB');
		    } else {
			    $('input[name="searchMethod"]').val('DN_POOL_THEN_PIPB');
		    }
		}
		if (selection == 'usePipbDn') {				
			$('input[name="searchMethod"]').val('PIPB_DN');
		}
	}
	
	function checkOption(){
		var val = $('input[name="numSelectionRadio"]:checked').val();
		if(val == 'useNewDn'){
			$("#numberSelection").show();
			$("#pipbInfo").hide();
		}
		if(val == 'useNewDnAndPipbDn'){
			$("#numberSelection").show();
			$("#pipbInfo").show();
			$("#reuseExSocketDiv").show();
			reuseSocketCheck();
			$("#waiveDnChrgDiv").show();
			$("#pipbAcctCdDiv").hide();
			$("#duplexDiv3").hide();
			if ($('input[name="pipbInfo.duplexRadio"]:checked').val()=='portInTogether') {
				$('input[value="disconnect"]').attr('checked',false);
				$('input[value="retain"]').attr('checked',false);
				$('input[value="portInTogether"]').attr('checked',false);
			}
		}
		if(val == 'usePipbDn'){
			$("#numberSelection").hide();
			$("#reuseExSocketDiv").hide();
			$("#socketTypeDiv").hide();
			$("#socketTypeDiv2").hide();
			$("#waiveDnChrgDiv").hide();
			$("#pipbInfo").show();
			if (isChannelDS=='true') {
				$("#pipbAcctCdDiv").hide();
			} else {
				$("#pipbAcctCdDiv").show();
			}			
			if($('input[name="pipbInfo.duplexIndStr"]:checked').val()=='Y'){
				$("#duplexDiv3").show();
			}					
		}
	}
	
	function defaultPipb() {
		var acctCd = $('input[name="pipbInfo.pipbAccountCd"]').val();
		var boc = $('input[name="pipbInfo.pipbBoc"]').val();
		var projectCd = $('input[name="pipbInfo.pipbProjectCd"]').val();
		if (acctCd=='' && boc=='' && projectCd=='') {
			$('input[name="pipbInfo.pipbAccountCd"]').val('2N');
			$('input[name="pipbInfo.pipbBoc"]').val('000');
			$('input[name="pipbInfo.pipbProjectCd"]').val('2N NUMBER');
		}
	}
	
	function defaultExDir() {
		$('input[name="includeSrvNumOnExDir"]').attr('checked',true);
	}
	
	function defaultWaiveDnChrg() {
		$('input[name="pipbInfo.waiveDnChrg"]').attr('checked',true);
	}
	
	function defaultReuseSocket() {
		$('input[name="pipbInfo.reuseExSocket"]').attr('checked',true);
	}
	
	function actionPerform() {
		
		$("[name=numSelectionRadio]").click(function(){
			selectionDisplayNoneShow();
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
				$('form[name="ltsAcqNumSelectionAndPipbForm"]').submit();
			} else {
				alert('DN Search is not for reserved DN, please click continue');
			}
		});
		
		$('a#lockBtn').click(function (event) {
			setNumSelection();
			setSearchMethod();
			$('input[name="formAction"]').val('LOCK_NUMBER');
			$('form[name="ltsAcqNumSelectionAndPipbForm"]').submit();
		});
		
		$('a#releaseBtn').click(function (event) {
			setNumSelection();
			setSearchMethod();
			$('input[name="formAction"]').val('RELEASE_NUMBER');
			$('form[name="ltsAcqNumSelectionAndPipbForm"]').submit();
		});
				
		$('a#continueBtn').click(function (event) {
			event.preventDefault();
			setNumSelection();
			setSearchMethod();
			setPipbAction();
			setDuplexAction();
			$('input[name="formAction"]').val('SUBMIT');
			var selection = $('input[name="numSelectionRadio"]:checked').val();
			var method = $('input[name="searchMethodRadio"]:checked').val();			
			if (selection == 'useNewDn' && method == 'reservedDnRadio') {
				$('input[name="dnStatus"]').val('70');
				chkReservedDn();
			} else if (selection == 'useNewDnAndPipbDn' && method == 'reservedDnRadio') {
				$('input[name="dnStatus"]').val('70');
				chkReservedDnAndPipbDn();
			} else if (selection == 'useNewDnAndPipbDn' && method != 'reservedDnRadio') {
				$('input[name="dnStatus"]').val('05');
				chkPipbDn();
			} else if (selection == 'usePipbDn') {
				$('input[name="pipbInfo.reuseExSocket"]').attr('checked',false);
				$('input[name="pipbInfo.waiveDnChrg"]').attr('checked',false);
				chkPipbDn();				
			} else {
				$('input[name="dnStatus"]').val('05');
				$('form[name="ltsAcqNumSelectionAndPipbForm"]').submit();
			}
		});
		
		if ($('input[name="numSelectionRadio"]:checked').val()==undefined) {
			if(isContainPreInstallVAS =='true')
			{
				$('input[value="useNewDnAndPipbDn"]').attr('checked',true);
			}
			else
			{
				if($('input[value="useNewDn"]').is(':enabled')){
					$('input[value="useNewDn"]').attr('checked',true);
				} else if($('input[value="useNewDnAndPipbDn"]').is(':enabled')){
					$('input[value="useNewDnAndPipbDn"]').attr('checked',true);
				} else if($('input[value="usePipbDn"]').is(':enabled')){
					$('input[value="usePipbDn"]').attr('checked',true);
				} else {
					selectionDisplayNoneHide();
				}
			}			
		}
		else
		{
			if(isContainPreInstallVAS =='true')
			{
				$('input[value="useNewDn"]').attr('checked',false);
				$('input[value="useNewDnAndPipbDn"]').attr('checked',true);
				$('input[value="usePipbDn"]').attr('checked',false);
			}
		}
	
		if ($('input[name="searchMethodRadio"]:checked').val()==undefined) {
			$('input[value="noCriteriaRadio"]').attr('checked',true);
		}
		
		$("#reuseExSocket").change(function(){
			reuseSocketCheck();
		});
	
		$("#portFromDiffCust").change(function(){
			custCheck();
		});
		
		$("[name=pipbInfo.portFromDiffAddrStr]").click(function(){			
			baChange();
		});
		
		$("[name=pipbInfo.duplexIndStr]").click(function(){
			duplexCheck();
		});
				
		$("#docType").change(function(){
			docTypeCheck();
		});
		
		$("#waiveDnChrg").change(function(){
			waiveDnChrgCheck();
		});
			
		$('input[name="pipbInfo.address.flat"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.floor"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.block"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.lotNum"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.buildingName"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.streetName"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.streetNum"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.streetCatgDesc"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.sectionDesc"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.districtDesc"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.address.areaDesc"]').keyup(function() {
			$(this).val($(this).val().toUpperCase());
		});
		
		$('input[name="pipbInfo.quickSearch"]').each(function(i ,el){	    	
			el = $(el);
	    	el.autocomplete("ltsaddresslookup.html?type=includeLtsOnly", {
				minChars : 1,
				delay : 600,
				selectFirst : false,
				max : 30,
				matchSubset : false,
				highlight : false,
				pipbAddr : true,
				formatItem : function(row, i, max) {
					return row;
				}
			});
	    });
	
		$('a#clearBtn').click(function(event) {
			clearba();
		});
				
		$(".toUpper").keyup(function(){
	    	$(this).val($(this).val().toUpperCase());
	    });
		
		
		defaultReuseSocket();
		custCheck();
		baCheck();
		docTypeCheck();		
		checkOption();
		defaultPipb();
		defaultExDir();
		duplexCheck();
		defaultWaiveDnChrg();
				
	}
	
	function selectionDisplayNoneShow(){
		$('#pipbInfo').removeClass("selectionDisplayNone");
		$('#numberSelection').removeClass("selectionDisplayNone");
		$('#divIncludeSrvNumOnExDir').removeClass('selectionDisplayNone');		
	}
	
	function selectionDisplayNoneHide(){
		$('#pipbInfo').addClass("selectionDisplayNone");
		$('#numberSelection').addClass("selectionDisplayNone");
		$('#divIncludeSrvNumOnExDir').addClass('selectionDisplayNone');		
	}

	return {
		actionPerform : actionPerform
	};
	
}();