<jsp:include page="/WEB-INF/jsp/header.jsp">
	<jsp:param name="enableOnloadJScript" value="Y" />
</jsp:include>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<% String mobuidParameter= (String)request.getParameter("sbuid");%>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="customerprofile" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>
<br>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<link href="css/c.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.msgbox { margin-bottom: 1em; background-color: white; padding: 0.5em; border-radius: 0.5em }
.msgbox_error { background-color: #FF8080 }
h3.table_title { margin: 0; font-size: 16px }
.clearInput { font-weight: bold; color: black; cursor: pointer }
.brand, img, input {
  display: inline-block;
  vertical-align: middle;
}
</style>
<c:if test="${fn:contains(header['User-Agent'], 'iPad')}">
<style type="text/css">
.ac_results li { padding-top: 1em; padding-bottom: 1em }
</style>
</c:if>
<style>
  input[readonly] {
    background-color:#ebebeb;
    color:#606060;
  }
</style>
<pccw-util:codelookup var="mipDisableCSimMap" codeType="MIP_DISABLE_C_SIM"  asEntryList="true" />
<c:set var="mipDisableCSim" value="${mipDisableCSimMap[0].key}" />

<pccw-util:codelookup var="mipDisable1010Map" codeType="MIP_DISABLE_1010_BRAND"  asEntryList="true" />
<c:set var="mipDisable1010" value="${mipDisable1010Map[0].key}" />


<script src="js/jquery-migrate-1.2.1.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script src="js/carecash.js"></script>
<script src="js/combineaccount.js"></script>
<script>
  var depositLkup = <sb-util:json value="${depositLkup}"/>;
	var sectionLoadAll = false;
	var billingSectionLoadAll = false;
	var alrResHKT = "N";
	var alrResClub = "N";
	var refcustaddr = <sb-util:json value="${origCustAddress}"/>;
	var bomAddrDto = null;
	
	var dummyTextInd = "N";
	var authorizeType = "";
	var careCashSubscribedButton="${customerProfile.careOptInd}";
	var careCashOrderSignOffInd="${customerProfile.careCashOrderSignOffInd}";
	var careCashRegisterTimeInd="${customerProfile.careCashRegisterTimeInd}";	
	$('#careCashSubscribedButtonInd').val(careCashSubscribedButton);
	$('#careCashRegisterTimeInd').val(careCashRegisterTimeInd);
	$("#careCashButtonInd").val(careCashSubscribedButton);
	function selectOneChkBox(selected) { //Paper bill
		if (!$(selected).hasClass("blur")){
		$('.billMediaCB').each(function() {
				if ($(this).val() == "PAP_SUM_10" && selected.val()=="PAP_SUM_RW"){ //PAP_SUM_RW
					$(this).attr('checked',false);
				}else if($(this).val() == "PAP_SUM_RW" && selected.val()=="PAP_SUM_10"){
					$(this).attr('checked',false);
				}		
			});
		}
	}
	
	function authorized() { //Paper bill
		$('#authDialog').dialog('close');
		if (authorizeType == 'AU06'){
			 $(".auth_button").hide(); 
			$('.billMediaCB').removeClass("blur");
			  $('.billMediaCB').each(function() {
					$(this).unbind('click');
				}); 
			$('.billMediaCB').click(function() {selectOneChkBox($(this));});
		} else if (authorizeType == 'AU02'){
			$("#mrtThresholdOverflowAuthorize").hide();
			$("input[name=mrtThresholdOverflowAuthInd]").val("Y");
		}
	}
	
 	function checkCsPortalId() {
 		
 		dummyTextInd = "N";
 		alrResHKT="N";
		alrResClub="N";
		
		if ($("#idDocNum").val()==null || $("#idDocNum").val()==""||$("#docType").val()=="BS"){
			alrResHKT="N";
			alrResClub="N";
			$("#csPortalDiv").hide('slow');
			$("#csPortalReg").hide('slow');
			$('input[name=csPortalBool]').val(false);
			$('input[name=csPortalBool]').attr("checked", false);
			return;
		} else if ($("#docType").val()=="PASS") {
			var idDocNum =$("#idDocNum").val();
		 	<c:forEach items="${dummyTextList}" var="item">
				if (idDocNum.toUpperCase().indexOf('<c:out value="${item}"/>') >= 0 && dummyTextInd == "N") {
					dummyTextInd = "Y";					
				}  
			</c:forEach> 
			
			if (dummyTextInd == "Y"){
				$('input[name=csPortalBool]').click(function() { 
					return false;}
				);
				$("#csPortalReg").css({"opacity":.5});
	
			} else {
				$('input[name=csPortalBool]').unbind('click');
				$("#csPortalReg").css({"opacity":1});
			}
		} else {
			$('input[name=csPortalBool]').unbind('click');
			$("#csPortalReg").css({"opacity":1});
		}
		
		$.ajax({
			url : "csportal.html?type=idck&dt="+$("#docType").val()+"&dn="+ $("#idDocNum").val().trim(), //idck
			cache: false,
			dataType : 'json',
			timeout : 5000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert('Error when calling My HKT/The Club registration, My HKT/The Club will not be registered at this moment. Please proceed your order.');
	            $('input[name=csPortalBool]').val(false);
	            $('input[name=csPortalBool]').attr("checked", false);
			},
			success : function(data) {
				var result = jQuery.parseJSON(data.gson);
				
				$("#csPortalStatus").val('');
				if (result == null || result.reply=="RC_CUST_IVDOCTY"
						|| result.reply=="RC_CUST_ILDOCNUM"
						|| result.reply=="RC_CUST_IVDOCNUM") {
					alert("My HKT/The Club registration checking failed due to invalid document number.");
					$("#csPortalDiv").hide('slow');
					$("#csPortalReg").hide('slow');
					$('input[name=csPortalBool]').val(false);
					$('input[name=csPortalBool]').attr("checked", false);
					$("#csPortalStatus").val('');
					return;
				}
				
				if (result.oIdStatus=="RC_CUS_ALDY_REG") {
					$("#csPortalStatus").val('H');
				}
				
				if (result.oCorrClubLi != null &&  result.oCorrClubLi != "") {
					$("#csPortalStatus").val($("#csPortalStatus").val()+'C');
				}
				
				if ($("#csPortalStatus").val()=="HC") {
					$("#regMsg").text("The CLUB ("+result.oCorrClubLi+") and MyHKT ("+result.oCorrMyHktLi+") accounts are already registered.");
					$("#csPortalDiv").show('slow');
					$("#csPortalReg").hide('slow');
					$('input[name=csPortalBool]').val(false);
					$('input[name=csPortalBool]').attr("checked", false);
					$("#optOutClub").css('display','none');
					$("#optOutHKT").css('display','none');
					$("#optOutHKTAndClub").css('display','none');
					$(".clubOptOutReasonRemark").hide();
					$('#hktClubOptOut').attr('checked',false);
					$('#hktOptOut').attr('checked',false);
					$('#clubOptOut').attr('checked',false);
					
				} else if ($("#csPortalStatus").val()=="H") {
					if(result.oPhantomAcc=='Y') {
						$("#regMsg").text("MyHKT ("+result.oCorrMyHktLi+") account is already registered. A phantom account was created for the Club.");
					} else {
						$("#regMsg").text("MyHKT ("+result.oCorrMyHktLi+") account is already registered.");
					}
					$("#csPortalRegMsg").html("I would like to register \"The Club\" account by using above Contact Email Address.<br>(我希望用以上聯絡電郵地址登記「The Club」賬戶。)");
					$("#csPortalDiv").show('slow');
					$("#csPortalReg").show('slow');
					$("#optOutClub").css('display','block');
					$("#optOutHKT").css('display','none');
					$("#optOutHKTAndClub").css('display','none');
					$('input[name=csPortalBool]').val(true);
					$('input[name=csPortalBool]').attr("checked", true);
					$(".clubOptOutReasonRemark").show();
					$('#hktClubOptOut').attr('checked',false);
					$('#hktOptOut').attr('checked',false);
				} else if ($("#csPortalStatus").val()=="C") {
					$("#regMsg").text("The Club ("+result.oCorrClubLi+") account is already registered");
					$("#csPortalRegMsg").html("I would like to register \"My HKT\" account by using above Contact Email Address.<br>(我希望用以上聯絡電郵地址登記「My HKT」賬戶。)");
					$("#csPortalDiv").show('slow');
					$("#csPortalReg").show('slow');
					$("#optOutHKT").css('display','block');
					$("#optOutClub").css('display','none');
					$("#optOutHKTAndClub").css('display','none');
					$('input[name=csPortalBool]').val(true);
					$('input[name=csPortalBool]').attr("checked", true);
					$(".clubOptOutReasonRemark").hide();
					$('#hktClubOptOut').attr('checked',false);
					$('#clubOptOut').attr('checked',false);
				} else if ($("#csPortalStatus").val()=="") {
					if(result.oPhantomAcc=='Y') {
						$("#regMsg").text("A phantom account was created for the Club.");
					} else {
						$("#regMsg").text("");	
					}
					$("#csPortalRegMsg").html("I would like to register \"My HKT\" and \"The Club\" account by using above Contact Email Address.<br>(我希望用以上聯絡電郵地址登記「My HKT」以及「The Club」賬戶。)");
					$("#optOutHKTAndClub").css('display','block');
					$("#optOutClub").css('display','none');
					$("#optOutHKT").css('display','none');
					$("#csPortalDiv").show('slow');
					$("#csPortalReg").show('slow');
					$('input[name=csPortalBool]').val(true);
					$('input[name=csPortalBool]').attr("checked", true);
					$(".clubOptOutReasonRemark").show();
					$('#clubOptOut').attr('checked',false);
					$('#hktOptOut').attr('checked',false);
				}
				$('#oBiptStatus').val(result.oBiptStatus);
				if (result.oBiptStatus=="O" || result.oBiptStatus=="G" ||result.oBiptStatus=="I"){
				$('.iGuardTable').hide();
				$('#careStatus').val('N');
				if (result.oBiptStatus=="I" ){
					$("#regCareCashMsg").text("CARECash service registered");
				}else if(result.oBiptStatus=="G" || result.oBiptStatus=="O"){
					$("#regCareCashMsg").text("CARECash service opt-out ");
				}
					
				}else{
					$("#regCareCashMsg").text("");
					var oCareVisit = result.oCareVisit;
					if (typeof oCareVisit != 'undefined'){
						var oCareVisitYear=oCareVisit.substring(0,4);
						var oCareVisitMonth=oCareVisit.substring(4,6);
						var oCareVisitDay=oCareVisit.substring(6,8);
						var today = new Date();
						var oCareVisitDate = new Date();
						var dateOffset = (24*60*60*1000) * 7; //7 days
						oCareVisitDate.setFullYear(oCareVisitYear,oCareVisitMonth-1,oCareVisitDay);
						today.setTime(today.getTime()-dateOffset);
						if (today.getTime()>oCareVisitDate.getTime()){
							$('#oCareVisitInd').val(true);
							careCashTableControl("${channelId}", "${workStatus}")
						} 
						else{
							$('#oCareVisitInd').val(false);
						}
					}
					else{
						$('#oCareVisitInd').val(true);
						careCashTableControl("${channelId}", "${workStatus}")
					
					}
				
				}
				
				// if (result.oCorrClubLi != null && result.oCorrClubLi.length > 0)
					$('#theClubLogin').val(result.oCorrClubLi); // for club earn point 20170227
				// if (result.oCorrClubMbrId != null && result.oCorrClubMbrId.length > 0)
					$('#clubMemberId').val(result.oCorrClubMbrId); // for club earn point 20170322
			}
		}); 
	}
	
 	function disableBlock() {
 		if($('#hktOptOut').attr('checked') || $('#clubOptOut').attr('checked') || $('#hktClubOptOut').attr('checked')){
			 $('#clubOptRea').attr('disabled',false);
			 $('#clubOptRmk').attr('disabled',false);
		 }else{
			 $('#clubOptRea').attr('disabled',true);
			 $('#clubOptRmk').attr('disabled',true);
		 }		
 	}
 	
	$(document).ready(function() {
		var combineAcctInd="${customerProfile.combineAccountRegisterTimeInd}";
		if (combineAcctInd === "false"){
		
			$('.acctInfoTable').hide();
		} 
		
		 $("input[name=acctType]").change(function() {
			
			 switchcase($("input[name='acctType']:checked").val());
      
    });
	$("input[name=acctType]:checked").change();
			  
		var orderID = "<c:out value='${orderDto.orderId}'/>";
		if (orderID != null  && orderID != "" ){
		retrieveAccountInfo();
		$('input[type=radio][name=acctType]').attr("disabled", true);
		$('#activeMobileNum,#acctInfo').attr('readonly', true);
		$('#retrieve').attr('disabled', true);
		}
		
		 if ($('#careStatus').val()=='N'){
				$('.iGuardTable').hide();
		}

		 if (careCashOrderSignOffInd==='true'){
		 disableCareCashButton();
		
		 }
		if ($('input[type=radio][name=simType][checked=checked]').val() == 'H') {
        	$('#smsOptOutFirstRoam').hide();
        	$('#smsOptOutRoamHu').hide();       
        	$('#smsOptOutNote').show();
        }
        else if ($('input[type=radio][name=simType][checked=checked]').val() == 'C') {
        	$('#smsOptOutFirstRoam').show();
        	$('#smsOptOutRoamHu').show();       
        	$('#smsOptOutNote').hide();
        }
		
		//vincy
		 $('#hktOptOut').click(function() { 
			 disableBlock();
		}); 
		$('#clubOptOut').click(function() { 
			disableBlock();
		});
		$('#hktClubOptOut').click(function() { 
			disableBlock();
		});
			
		disableBlock();	
		
		$("input[name=csPortalBool]").attr('disabled',true);
		
		$("#clubOptRea").val("${customerProfile.clubOptRea}");
		
		$("input[name=addrProofRadio]").each(function() {
			if ($(this).val() == "${customerProfile.addrProofInd}") {
				$(this).attr("checked", true);
			}
		});

		$("input#quickSearch").autocomplete("addresslookup.html?sbuid=${param.sbuid}", {
			minChars : 3,
			delay : 600,
			selectFirst : false,
			max : 12,
			matchSubset : false,
			highlight : false,
			cacheLength: 0,
			
			formatItem : function(row, i, max) {
				return row;
			}
		}).result(function(event, item) {
			$.ajax({
				url : "addressdistrictlookup.html?sbuid=${param.sbuid}"
				, data: { "T": "SERVICEBOUNDARYNUM", "ID": $("#serviceBoundaryNum").val() }
				, type : 'POST'
				, dataType : 'json'
				, timeout : 5000
				, error : function() {
					alert('Public Housing Searching & HKT Premier are not available.');
					$("#phInd").val("N");
					$("#hktPremierAddr").val("N");
				}
				, success : function(data) {
					$("#phInd").val("");
					$.each(data, function(i, item) {
						$("#phInd").val(item.phInd);
						$("#hktPremierAddr").val(item.hktPremierAddr);
					});

					
				}
			});
		});
		//add 20110513
		$("input#billingQuickSearch").autocomplete("addresslookup.html?sbuid=${param.sbuid}", {
			minChars : 3,
			delay : 600,
			selectFirst : false,
			max : 12,
			matchSubset : false,
			highlight : false,
			cacheLength: 0,
			
			formatItem : function(row, i, max) {
				return row;
			}
		});

		noBillingAddressClick1();
		setCompanyCustomerDisplay();

		$(".clearInput").click(function(e) {
			$(this).parent().find(".ac_input").val("").flushCache();
		});
		
		$("form[name=custForm]").submit(function(e) {
			var error = false;
			if ($("#custAddressFlag1").attr('checked')) {
				$("#districtCode").val($("#districtCodeSelect").val());
				$("#districtDesc").val(
						$("#districtCodeSelect option:selected").text());
				$("#areaCode").val($("#areaCodeSelect").val());
				$("#areaDesc").val($("#areaCodeSelect option:selected").text());
				$("#sectionCode").val($("#sectionCodeSelect").val());
				$("#sectionDesc").val(
						$("#sectionCodeSelect option:selected").text());
				$("#streetCatgDesc").val(
						$("#streetCatgCode option:selected").text());
			}
			if ($("#billingCustAddressFlag1").attr('checked')) {
				$("#billingDistrictCode")
						.val($("#billingDistrictCodeSelect").val());
				$("#billingDistrictDesc").val(
						$("#billingDistrictCodeSelect option:selected").text());
				$("#billingAreaCode").val($("#billingAreaCodeSelect").val());
				$("#billingAreaDesc").val(
						$("#billingAreaCodeSelect option:selected").text());
				$("#billingSectionCode").val($("#billingSectionCodeSelect").val());
				$("#billingSectionDesc").val(
						$("#billingSectionCodeSelect option:selected").text());
				$("#billingStreetCatgDesc").val(
						$("#billingStreetCatgCode option:selected").text());
			}
			
			if ($("#hktPremierAddr").val() == "Y") {
				alert("Customer address belongs to HKT Premier, not allow to create order through Springboard");
				error = true;
			}
			
			// for DS order support
			var category = "<c:out value='${category}'/>";
			var channelId = "<c:out value='${channelId}'/>";
			if (channelId == 11 && category == 'ORDER SUPPORT'){
				$(".auth_button").hide();
				$('.billMediaCB').removeClass("blur");
				$('.billMediaCB').each(function() {
						$(this).unbind('click');
					});
				$('.billMediaCB').click(function() {selectOneChkBox($(this));});
			}
			
			if ($('input[type=radio][name=addrProofRadio]:checked').val() == '5') {
				if (bomAddrDto !== null && !compareAddress(bomAddrDto)) {
					$("input[name=bomCustAddrOverrideInd]").val("Y");
				}
			}
						
			$("input[name=addrProofInd]").val($("input[name=addrProofRadio]:checked").val());

			if (error) {
				e.preventDefault();
				return false;
			}
			$("input[name=csPortalBool]").attr('disabled',false);
		});
		
		$(".continue_button").click(function(e) {
			$('#mob0060OptOutInd1').prop('disabled', false);
			$('.careCash').prop('disabled', false);
			$('#idDocNum').prop('disabled', false);
			$('#docType').prop('disabled', false);
			$('#customercareDmSupInd').prop('disabled', false);
			$("#noBillingAddressFlag1").attr('disabled', false);
			$("input:radio[name=simType][value=H]").attr("disabled",false);
			$("input:radio[name=simType][value=C]").attr("disabled",false); 
			$("#checkAcctInfo").val("Y") ;
			$("form[name=custForm]").submit();
			$("input:radio[name=brand],#docType,#idDocNum").off("change");
		});
		

		var idDocType = "<c:out value="${customerProfile.idDocType}"/>";
		if (idDocType == "BS") {
			$("#dateOfBirthDiv").hide();
			$("#dateOfBirthInputFieldDiv").hide();
			$("#CompanyNameDiv").show();
			$("#CompanyNameInputFiledDiv").show();
			$('input[type=radio][name=addrProofRadio][value=5]').attr("disabled", true);
		} else {
			$("#dateOfBirthDiv").show();
			$("#dateOfBirthInputFieldDiv").show();
			$("#CompanyNameDiv").hide();
			$("#CompanyNameInputFiledDiv").hide();
		}
		$("#idDocNum").focusout(function(){
			$('input[name=csPortalBool]').val(true);
			$('input[name=csPortalBool]').attr("checked", true);
			checkCsPortalId();
			preActivationChecked();
		});
		
		var actualUserDocType = "<c:out value="${customerProfile.actualUserDTO.subDocType}"/>";
		if (actualUserDocType == "BS") {
			$("#actualUserCompanyNameDiv").show();
			$("#actualUserCompanyNameInputFiledDiv").show();
		} else {
			$("#actualUserDateOfBirthDiv").show();
			$("#actualUserDateOfBirthInputFieldDiv").show();
		}
		
		if (<c:out value='${not empty orderDto.ocid}'/>) {
			$("#csPortalReg").hide('slow');
			$('input[name=csPortalBool]').click(function() {return false;});
			if (<c:out value="${customerProfile.csPortalInd eq 'Y'}"/> && channelId != '10' && channelId != '11') {
				$("#emailAddr").attr('readonly',true);
				$("#noEmailFlag").attr('disabled',true);
			}
		}
		if ($('.billMediaCB').length > 0) {
			$('.billMediaCB').click(function() {selectOneChkBox($(this));});
			$('.billMediaCB').each(function() {
				if ($(this).hasClass("blur")){
					$(".auth_button").show();
					$(this).click(function() {return false;});
				}
				});
		}
		$('.billMediaCB').each(function() {
				if ($(this).hasClass("blur") && $(this).attr('checked')){
					$(".auth_button").hide();
					$('.billMediaCB').unbind('click');
					$(this).removeClass("blur");
					$('.billMediaCB').click(function() {selectOneChkBox($(this));});
					return false;
				}
				});
		
		$(".auth_button").click(function(e) {
			e.preventDefault();
			var sURL;
			var orderID = "<c:out value='${orderDto.orderId}'/>";
			authorizeType = "AU06";
			
			if(orderID != null && orderID !="")	{
				sURL = "mobccsauthorize.html?" + $.param({"action": "AU06", "orderId": orderID});
			} else {
				sURL= "mobccsauthorize.html?" + $.param({"action": "AU06"});
			}
			$('#authDialog').html('<iframe style="border: 0px; " src="' + sURL + '" width="100%" height="100%"></iframe>')
            .dialog({
                autoOpen: false,
                modal: true,
                height: 300,
                width: 500,
                title: "Authorize"
            });
			$('#authDialog').dialog('open');
			return false;
		}); 
		
		$("#mrtThresholdOverflowAuthorize").click(function(e) {
			authorizeType = "AU02";
			e.preventDefault();
			var sURL = "mobccsauthorize.html?" + $.param({"action": "AU02"});
			$('#authDialog').html('<iframe style="border: 0px; " src="' + sURL + '" width="100%" height="100%"></iframe>')
            .dialog({
                autoOpen: false,
                modal: true,
                height: 300,
                width: 500,
                title: "Authorize"
            });
			$('#authDialog').dialog('open');
			return false;
		}); 
		
		
		preActivationChecked();
		
		sameAsCustClick();
		setActualUserCompanyCustomerDisplay();
		sameAsEbillAddrClick()
		
			$(".careCash").change(function(){
				var self=$(this).val();
				if (self=='O'|| self=='P'){
					$(".careCashPersonal").hide();
				}
				if (self=='I'){
					$(".careCashPersonal").show();
				}

			});
			
		 	$(".careCashField").change(function(){
				careCashTableControl("${channelId}", "${workStatus}");
			}); 
		 	
		 	 if (careCashSubscribedButton=='O'||careCashSubscribedButton=='P'){
				$(".careCashPersonal").hide();
		 	}
	});

	$(function() {
		$('#dobDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,

			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', 
			yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		});
		
		$('#actualUserDobDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,

			showButtonPanel : true,
			dateFormat : 'dd/mm/yy',  
			minDate : "-100Y+1D",
			maxDate : "-1D",  
			yearRange : "c-100:c+100" 
		});
		
	});

	function noEmailFlagClick() {
		if (document.custForm.noEmailFlag.checked) {
			document.custForm.emailAddr.value ="";
			document.custForm.emailAddr.disabled = true;
            $('#mob0060OptOutInd1').attr('checked', true); 
		} else {
			document.custForm.emailAddr.disabled = false;
			$('#mob0060OptOutInd1').attr('checked', false); 
		}
	}
	
   /*  function mob0060OptClick(){
		if($('#mob0060OptOutInd1').prop( 'checked' )){
			$('#noEmailFlag').attr('checked', true); 
			$('#emailAddr').prop( 'disabled', true );
		}else{
			$('#noEmailFlag').attr('checked', false); 
			$('#emailAddr').prop( 'disabled', false);
		}
	} */
    
    function checkProfileClick() {
		if ($("#idDocNum").val() == "") {
			alert("Please input document number first");
		} else {
			var docType = $("#docType").val();
			var idDocNum = $("#idDocNum").val();
			var url = "./svcmobacq.html?idDocType=" + docType + "&idDocNum=" + idDocNum;
			location.href = url;
		}
	}
    
	function custAddressFlagClick() {
		if (document.custForm.custAddressFlag1.checked) {

			document.custForm.buildingName.readOnly = false;
			document.custForm.streetNum.readOnly = false;
			document.custForm.streetName.readOnly = false;
			document.custForm.quickSearch.readOnly = true;
			document.custForm.quickSearch.value = "";

			$("#sectionCodeSelect").show();
			$("#districtCodeSelect").show();
			$("#areaCodeSelect").show();
			$("#unlinkDiv").show();
			
			setTimeout(function() {
				$("#areaCodeSelect").val($("#areaCode").val());
				$("#districtCodeSelect").val($("#districtCode").val()).change();
				
			}, ($.browser.msie && parseInt($.browser.version, 10) <= 6) ? 500 : 0);
		

			$("#streetCatgDesc").hide();
			$("#sectionDesc").hide();
			$("#districtDesc").hide();
			$("#areaDesc").hide();

			$("#streetCatgDesc").hide();
			$("#streetCatgCode").show();
			
			$("#phInd").val("N");
			$("#serviceBoundaryNum").val("");
			$("#hktPremierAddr").val("N");

		} else {
			sectionLoadAll = false;
			document.custForm.buildingName.readOnly = true;
			document.custForm.streetNum.readOnly = true;
			document.custForm.streetName.readOnly = true;
			document.custForm.streetCatgDesc.readOnly = true;
			document.custForm.quickSearch.readOnly = false;
			$("#districtCodeSelect").hide();
			$("#areaCodeSelect").hide();
			$("#unlinkDiv").hide();
		
			$("#sectionCodeSelect").hide();

			$("#sectionDesc").show();
			$("#districtDesc").show();
			$("#areaDesc").show();
			$("#streetCatgDesc").show();
			$("#streetCatgCode").hide();

			document.custForm.sectionDesc.readOnly = true;
			document.custForm.districtDesc.readOnly = true;
			document.custForm.areaDesc.readOnly = true;

			//for reacall order
			if ($("#quickSearch").val() == '') {

				
				document.custForm.sectionDesc.value = "";
				document.custForm.districtDesc.value = "";
				document.custForm.areaDesc.value = "";
				document.custForm.streetCatgDesc.value = "";

				document.custForm.sectionCode.value = "";
				document.custForm.districtCode.value = "";
				document.custForm.areaCode.value = "";
				document.custForm.streetCatgCode.value = "";

				document.custForm.buildingName.value = "";
				document.custForm.streetNum.value = "";
				document.custForm.streetName.value = "";
				
				$("#phInd").val("");
				$("#serviceBoundaryNum").val("");
				$("#hktPremierAddr").val("");
			}

		}
		
		if ("${customerProfile.addrProofInd == '5' && orderDto != null && orderDto.ocid != null}" == "true") {
			$("input[type=text]", "#registered_addr_table").attr("readonly", true);
			$("select", "#registered_addr_table").attr("disabled", true);
			$("#custAddressFlag1").attr("disabled", true);
			$("#linkSectionId").attr("disabled", true);
		}
	}

	function setCurrentSearchFrom(input) {
		document.custForm.currentSearchFrom.value = input;
	}

	function noBillingAddressClick1() {
		billingCustAddressFlagClick();
		if (document.custForm.noBillingAddressFlag1.checked) {
			
			$("#BillingDiv").hide();
		} else {
			
			$("#BillingDiv").show();
		}
	}

	//add by eliot 20110608
	function setCompanyCustomerDisplay() {
		if (document.custForm.docType.options[document.custForm.docType.selectedIndex].value == "BS") {
			$("#dateOfBirthDiv").hide();
			$("#dateOfBirthInputFieldDiv").hide();
			$("input[name=dobStr]").val("");
			$("#CompanyNameDiv").show();
			$("#CompanyNameInputFiledDiv").show();
			document.custForm.foreignDomesticHelperInd.value = 'false';
			$("#domHelper").hide();
			checkCsPortalId();
			$("#titleLabel").text('<spring:message code="label.representative.title"/>');
			$("#custName").text('<spring:message code="label.representative.nameInEng"/>');
			$("#repDocTypeTr").show();
			$("#repDocNumTr").show();
			$("#companyDocTr").show();
		} else if (document.custForm.docType.options[document.custForm.docType.selectedIndex].value == "HKID") {
			$("#dateOfBirthDiv").show();
			$("#dateOfBirthInputFieldDiv").show();
			$("#CompanyNameDiv").hide();
			$("#CompanyNameInputFiledDiv").hide();
			$("input[name=companyName]").val("");
			$("#domHelper").show();
			checkCsPortalId();
			$("#titleLabel").text('<spring:message code="label.title"/>');
			$("#custName").text('<spring:message code="label.nameInEng"/>');
			$("#repDocTypeTr").hide();
			$("#repDocNumTr").hide();
			$("#companyDocTr").hide();
			$("select[name=repIdDocType]").val("");
			$("input[name=repIdDocNum]").val("");
			$("input[name=companyDoc]").removeAttr("checked");
		} else if (document.custForm.docType.options[document.custForm.docType.selectedIndex].value == "PASS") {
			$("#dateOfBirthDiv").show();
			$("#dateOfBirthInputFieldDiv").show();
			$("#CompanyNameDiv").hide();
			$("#CompanyNameInputFiledDiv").hide();
			$("input[name=companyName]").val("");
			document.custForm.foreignDomesticHelperInd.value = 'false';
			$("#domHelper").hide();
			checkCsPortalId();
			$("#titleLabel").text('<spring:message code="label.title"/>');
			$("#custName").text('<spring:message code="label.nameInEng"/>');
			$("#repDocTypeTr").hide();
			$("#repDocNumTr").hide();
			$("#companyDocTr").hide();
			$("select[name=repIdDocType]").val("");
			$("input[name=repIdDocNum]").val("");
			$("input[name=companyDoc]").removeAttr("checked");
		}
	}
	
	function setActualUserCompanyCustomerDisplay() {
		if (document.custForm.actualUserDocType.options[document.custForm.actualUserDocType.selectedIndex].value == "BS") {
			$("#actualUserCompanyNameDiv").show();
			$("#actualUserCompanyNameInputFiledDiv").show();
		} else if (document.custForm.actualUserDocType.options[document.custForm.actualUserDocType.selectedIndex].value == "HKID") {
			$("#actualUserCompanyNameDiv").hide();
			$("#actualUserCompanyNameInputFiledDiv").hide();
			$("#actualUserCompanyName").val("");
		} else if (document.custForm.actualUserDocType.options[document.custForm.actualUserDocType.selectedIndex].value == "PASS") {
			$("#actualUserCompanyNameDiv").hide();
			$("#actualUserCompanyNameInputFiledDiv").hide();
			$("#actualUserCompanyName").val("");
		}
	}
	
	//STATR AJAX		
	$(function() {
		$.ajax({
			url : "addressdistrictlookup.html"
			, data: { T: "AREA", sbuid: "${param.sbuid}" } 
			, type : 'POST'
			, dataType : 'JSON'
			, timeout : 5000
			, error : function() {
				alert('Error loading area data!');
			}
			, success : function(msg) {
				$("#areaCodeSelect").empty();
				$.each(eval(msg), function(i, item) {
					var $option = $("<option/>").text(item.name).val(item.id);
					if (!$("#areaCodeSelect").data("init") && item.id == "${customerProfile.areaCode}") {
						$option.attr("selected", true);
					}
					$("#areaCodeSelect").append($option);
				});

				$('#areaCodeSelect').trigger("change"); //for assign value
			}
			, complete: function() {
				$("#areaCodeSelect").data("init", true);
			}
		});
		
		$("#areaCodeSelect").change(function() {
			loadDistrict($("#areaCodeSelect").val());
			if ($("#custAddressFlag1").attr('checked')) {
				$("#areaCode").val($(this).val());
				$("#areaDesc").val($(this).find("option:selected").text());
			}
			custAddressFlagClick();//change readonly
		  overrideAddress();

		});

		$("#districtCodeSelect").change(function() {
			loadSection($("#districtCodeSelect").val());
			if ($("#custAddressFlag1").attr('checked')) {
				$("#districtCode").val($(this).val());
				$("#districtDesc").val($(this).find("option:selected").text());
			}
		});

		$("#sectionCodeSelect").change(function() {
			if ($("#custAddressFlag1").attr('checked')) {
				$("#sectionCode").val($(this).val());
				$("#sectionDesc").val($(this).find("option:selected").text());
			}
		});
		$("#streetCatgCode").change(function() {
			if ($("#custAddressFlag1").attr('checked')) {
				$("#streetCatgDesc").val($(this).find("option:selected").text());
			}
		});

		$("#linkSectionId").change(function() {
			if ($(this).is(":checked")) {
				loadSection(0);//load all
				sectionLoadAll = false;//0315

			} else {
				loadSection($("#districtCodeSelect").val());
				
			}
		});

		function loadDistrict(parentid) {
			$.ajax({
				url : "addressdistrictlookup.html"
				, data: { T: "DISTRICT", ID: parentid, sbuid: "${param.sbuid}" }
				, type : 'POST'
				, dataType : 'JSON'
				, timeout : 5000
				, error : function() {
					alert('Error loading data b!');
				}
				, success : function(msg) {
					$("#districtCodeSelect").empty();
					$.each(eval(msg), function(i, item) {
						var $option = $("<option/>").text(item.name).val(item.id);
						if (!$("#districtCodeSelect").data("init") && item.id == "${customerProfile.districtCode}") {
							$option.attr("selected", true);
						}
						$("#districtCodeSelect").append($option);
					});
					$('#districtCodeSelect').trigger("change");
				}
				, complete: function() {
					$("#districtCodeSelect").data("init", true);
				}
			});
		}

		function loadSection(parentid) {
			$.ajax({
				url : "addressdistrictlookup.html"//'addressdistrictlookup.html?T=SECTION&ID='+ parentid+'&SECTIONALL='+sectionLoadAll
				, data: { T: "SECTION", ID: parentid, SECTIONALL: $("#linkSectionId").is(':checked'), sbuid: "${param.sbuid}" } 
				, type : 'POST'
				, dataType : 'JSON'
				, timeout : 5000
				, error : function() {
					alert('Error loading Section data!');
				}
				, success : function(msg) {
					$("#sectionCodeSelect").empty();
					var options = new Array();
					var optionZZZZPresent = false;
					$.each(eval(msg), function(i, item) {
						var $option = $("<option/>").text(item.name).val(item.id);
						if ((!$("#sectionCodeSelect").data("init") && item.id == "${customerProfile.sectionCode}")
								|| $("#sectionCode").val() == item.id) {
							$option.attr("selected", true);
						}
						if (item.id == "ZZZZ") {
							optionZZZZPresent = true;
							options.unshift($option);
						} else {
							options.push($option);
						}
					});
					if (!$("#linkSectionId").is(':checked') && !optionZZZZPresent) {
						var $optionZZZZ = $("<option/>").text("").val("ZZZZ");
						if ((!$("#sectionCodeSelect").data("init") && "ZZZZ" == '${customerProfile.sectionCode}')) {
							$optionZZZZ.attr("selected", true);
						}
						options.unshift($optionZZZZ);
					}
					for (var i = 0; i < options.length; i++) {
						$("#sectionCodeSelect").append(options[i]);
					}
					$('#sectionCodeSelect').trigger("change");
					sectionLoadAll = true;//loaded
				}
				, complete : function() {
					$("#loaderImagePlaceholder").empty();
					if ($("#custAddressFlag1").attr('checked')) {
						$("#sectionCodeSelect").show();
					}
					$("#sectionCodeSelect").data("init", true);
				}
				, beforeSend : function() {
					if ($("#custAddressFlag1").attr('checked')) {
						$("#sectionCodeSelect").hide();
					}
					$("#loaderImagePlaceholder").empty().html("<font color='red'>Please wait... </font>");

				}
			});
		}

	});
	//END AJAX

	//billing/////////////////////////////////////////////
	//20110314, for set ,set readOnly of buildingName,streetNum, streetName readonly
	function billingCustAddressFlagClick() {

		

		if (document.custForm.billingCustAddressFlag1.checked) {

			document.custForm.billingBuildingName.readOnly = false;
			document.custForm.billingStreetNum.readOnly = false;
			document.custForm.billingStreetName.readOnly = false;
			document.custForm.billingQuickSearch.readOnly = true;
			document.custForm.billingQuickSearch.value = "";

			$("#billingSectionCodeSelect").show();
			$("#billingDistrictCodeSelect").show();
			$("#billingAreaCodeSelect").show();
			$("#billingUnlinkDiv").show();
			
			setTimeout(function() {
				$("#billingAreaCodeSelect").val($("#billingAreaCode").val());
				$("#billingDistrictCodeSelect").val($("#billingDistrictCode").val());
				
				loadBillingSection($("#billingDistrictCodeSelect").val());
				if ($("#billingCustAddressFlag1").attr('checked')) {
					$("#billingDistrictCode").val($(this).val());
					$("#billingDistrictDesc").val($(this).find("option:selected").text());
				}
				
			}, ($.browser.msie && parseInt($.browser.version, 10) <= 6) ? 500 : 0);
			// IE6: give some time before change the select box value 
			
			
			$("#billingStreetCatgDesc").hide();
			$("#billingSectionDesc").hide();
			$("#billingDistrictDesc").hide();
			$("#billingAreaDesc").hide();

			$("#billingStreetCatgDesc").hide();
			$("#billingStreetCatgCode").show();

		} else {
			billingSectionLoadAll = false; //check wilson
			document.custForm.billingBuildingName.readOnly = true;
			document.custForm.billingStreetNum.readOnly = true;
			document.custForm.billingStreetName.readOnly = true;
			document.custForm.billingStreetCatgDesc.readOnly = true;
			document.custForm.billingQuickSearch.readOnly = false;
			$("#billingDistrictCodeSelect").hide();
			$("#billingAreaCodeSelect").hide();
			$("#billingUnlinkDiv").hide();
			$("#billingSectionCodeSelect").hide();

			$("#billingSectionDesc").show();
			$("#billingDistrictDesc").show();
			$("#billingAreaDesc").show();
			$("#billingStreetCatgDesc").show();
			$("#billingStreetCatgCode").hide();

			document.custForm.billingSectionDesc.readOnly = true;
			document.custForm.billingDistrictDesc.readOnly = true;
			document.custForm.billingAreaDesc.readOnly = true;

			//for reacall order
			if ($("#billingQuickSearch").val() == '') {

				
				document.custForm.billingSectionDesc.value = "";
				document.custForm.billingDistrictDesc.value = "";
				document.custForm.billingAreaDesc.value = "";
				document.custForm.billingStreetCatgDesc.value = "";

				document.custForm.billingSectionCode.value = "";
				document.custForm.billingDistrictCode.value = "";
				document.custForm.billingAreaCode.value = "";
				document.custForm.billingStreetCatgCode.value = "";

				document.custForm.billingBuildingName.value = "";
				document.custForm.billingStreetNum.value = "";
				document.custForm.billingStreetName.value = "";

			}
		}
	}

	//STATR AJAX
	$(function() {
		$.ajax({
			url : "addressdistrictlookup.html"
			, data: { T: "AREA", sbuid: "${param.sbuid}" }
			, type : 'POST'
			, dataType : 'JSON'
			, timeout : 5000
			, error : function() {
				alert('Error loading area data!');
			}
			, success : function(msg) {
				$("#billingAreaCodeSelect").empty();
				$.each(eval(msg), function(i, item) {
					var $option = $("<option/>").text(item.name).val(item.id);
					if (!$("#billingAreaCodeSelect").data("init") && item.id == "${customerProfile.billingAreaCode}") {
						$option.attr("selected", true);
					}
					$("#billingAreaCodeSelect").append($option);
				});

				$('#billingAreaCodeSelect').trigger("change"); //for assign value
			}
			, complete: function() {
				$("#billingAreaCodeSelect").data("init", true);
			}
		});

		$("#billingAreaCodeSelect").change(function() {
			loadBillingDistrict($("#billingAreaCodeSelect").val());
			if ($("#billingCustAddressFlag1").attr('checked')) {
				$("#billingAreaCode").val($(this).val());
				$("#billingAreaDesc").val($(this).find("option:selected").text());
			}
			
			billingCustAddressFlagClick();
			

		});

		$("#billingDistrictCodeSelect").change(function() {
			loadBillingSection($("#billingDistrictCodeSelect").val());
			if ($("#billingCustAddressFlag1").attr('checked')) {
				$("#billingDistrictCode").val($(this).val());
				$("#billingDistrictDesc").val($(this).find("option:selected").text());
			}
		});

		$("#billingSectionCodeSelect").change(function() {
			if ($("#billingCustAddressFlag1").attr('checked')) {
				$("#billingSectionCode").val($(this).val());
				$("#billingSectionDesc").val($(this).find("option:selected").text());
			}
		});

		$("#billingStreetCatgCode").change(function() {
			if ($("#billingCustAddressFlag1").attr('checked')) {
				$("#billingStreetCatgDesc").val($(this).find("option:selected").text());
			}
		});

		$("#billingUnlinkSectionFlag1").change(function() {
			if ($(this).is(":checked")) {
				loadBillingSection(0);//load all
				billingSectionLoadAll = false;//0315

			} else {
				loadBillingSection($("#billingDistrictCodeSelect").val());
				
			}
		});

		function loadBillingDistrict(parentid) {
			$.ajax({
				url : "addressdistrictlookup.html"
				, data: { sbuid: "${param.sbuid}", T: "DISTRICT", ID: parentid }
				, type : 'POST'
				, dataType : 'JSON'
				, timeout : 5000
				, error : function() {
					alert('Error loading data b!');
				}
				, success : function(msg) {
					$("#billingDistrictCodeSelect").empty();
					$.each(eval(msg), function(i, item) {
						var $option = $("<option/>").text(item.name).val(item.id);
						if (!$("#billingDistrictCodeSelect").data("init") && item.id == "${customerProfile.billingDistrictCode}") {
							$option.attr("selected", true);
						}
						$("#billingDistrictCodeSelect").append($option);
					});
					$('#billingDistrictCodeSelect').trigger("change");
				}
				, complete: function() {
					$("#billingDistrictCodeSelect").data("init", true);
				}
			});
		}

		function loadBillingSection(parentid) {
			$.ajax({
				url : "addressdistrictlookup.html"//'addressdistrictlookup.html?T=SECTION&ID='+ parentid+'&SECTIONALL='+billingSectionLoadAll,
				, data: { sbuid: "${param.sbuid}", T: "SECTION", ID: parentid, SECTIONALL: $("#billingUnlinkSectionFlag1").is(':checked') }
				, type : 'POST'
				, dataType : 'JSON'
				, timeout : 5000
				, error : function() {
					alert('Error loading Section data!');
				}
				, success : function(msg) {
					$("#billingSectionCodeSelect").empty();
					var options = new Array();
					var optionZZZZPresent = false;
					$.each(eval(msg), function(i, item) {
						var $option = $("<option/>").text(item.name).val(item.id);
						if ((!$("#billingSectionCodeSelect").data("init") && item.id == "${customerProfile.billingSectionCode}")
								|| $("#billingSectionCode").val() == item.id) {
							$option.attr("selected", true);
						}
						if (item.id == "ZZZZ") {
							optionZZZZPresent = true;
							options.unshift($option);
						} else {
							options.push($option);
						}
					});
					if (!$("#billingUnlinkSectionFlag1").is(':checked') && !optionZZZZPresent) {
						var $optionZZZZ = $("<option/>").text("").val("ZZZZ");
						if (!$("#billingSectionCodeSelect").data("init") && "ZZZZ" == '${customerProfile.billingSectionCode}') {
							$optionZZZZ.attr("selected", true);
						}
						options.unshift($optionZZZZ);
					}
					for (var i = 0; i < options.length; i++) {
						$("#billingSectionCodeSelect").append(options[i]);
					}
					$('#billingSectionCodeSelect').trigger("change");
					billingSectionLoadAll = true;//loaded
				},
				complete : function() {
					$("#billingLoaderImagePlaceholder").empty().html();
					if ($("#noBillingAddressFlag1").attr('checked')) {
						$("#billingSectionCodeSelect").show();
					}
					$("#billingSectionCodeSelect").data("init", true);
				},
				beforeSend : function() {
					if ($("#noBillingAddressFlag1").attr('checked')) {
						$("#billingSectionCodeSelect").hide();
					}
					$("#billingLoaderImagePlaceholder").empty().html("<font color='red'>Please wait... </font>");

				}
			});
		}

	});
	
	//END AJAX
	function privacyIndClick() {
		 if ($("#privacyInd1").attr('checked')) {
			alert(" Customer don't want to receive any promotional gifts, discounts, offers or materials");
		}else{
			alert(" Customer want to receive any promotional gifts, discounts, offers or materials.");
		} 
	};

	//end billing/////////////////////////////////////////////



  // For Deposit Handling ...
  
  function alertDepositRequired(code) {
    alert(depositLkup[code].depositDesc + "  $" + depositLkup[code].depositAmount);
  }
  
  function updateAddrProofIndState() {
	  if ($("input:radio[name=addrProofRadio]:checked").val() == "2") {
        $("#custAddressFlag1").attr("checked",false);
        $("#noBillingAddressFlag1").attr("checked",true);
        custAddressFlagClick();
        noBillingAddressClick1();
        $("#custAddressFlag1").attr('disabled', 'disabled');
        $("#flat").attr('readonly', true);
        $("#floor").attr('readonly', true);
        $("#lotNum").attr('readonly', true);
    } else {
        $("#custAddressFlag1").attr('disabled', false);
        custAddressFlagClick();
        noBillingAddressClick1();
        $("#flat").attr('readonly', false);
        $("#floor").attr('readonly', false);
        $("#lotNum").attr('readonly', false);
    }
  }
  
  function nonReferAddressClicked() {
    updateAddrProofIndState();

  }
  function referAddressClicked() {
    var override = confirm("Register Address and Billing Address override by selected LOB's Register Address");
    if (!override) {
    	$("input:radio[name=addrProofRadio][value='1']").attr('checked',true);
    }else{
      doReferAddress();
    }
  }
  function doReferAddress() {
    updateAddrProofIndState();

    overrideAddress();

  }
  
  function preActivationChecked() {
	  updateAddrProofIndState();
	  if ($('input[type=radio][name=addrProofRadio]:checked').val() == '5') {
		  if ($("#docType").val() != "" && $("#idDocNum").val() != "") {
			getBomCustAddr($("#docType").val(), $("#idDocNum").val());
	  	} else {
	  		$(".bom_addr_tr").hide("slow");
	  		$("#bom_addr_override_div").hide("slow");
	  	}
	  }
  }
  
  function popUpMRTSelection(url){
  	var selectedNumType =  $("input:radio[name=simType]:checked").val();
	url=url+"&numType="+selectedNumType;
	window.open(url, 'NewMRT', 'height=500,width=700,resizable=yes,scrollbars=yes');
  }

  function mobOnChange(url) {		
	$(".originalMrt").show();
	popUpMRTSelection(url);
  }
	
  $(function() {
    // safari bug which need a timeout to prevent frozen ...
    $("#docType").change(
        function() {
              
          preActivationChecked();
          var doctype= $(this).val();
          setTimeout(function() {
            if (doctype == "PASS") alertDepositRequired("DEP0007");
          }, 200);
          
          if (doctype == "BS") {
  			$('input[type=radio][name=addrProofRadio][value=5]').attr("disabled", true);
  			if ($('input[type=radio][name=addrProofRadio]:checked').val() == '5') {
    				$("<p>Not allow to select Pre-Activation.<br>Please select new address proof.</p>").dialog({
    					resizable: false,
    					height:"200",
    			        width:"400",
    					title:'Pre-Activation',
    				    modal:true,
    				    buttons:{
    					    "Confirm":function(){
    					    	$(this).dialog("close");
    					    }
    				    }
    			    });
  				}
  			} else {
  				$('input[type=radio][name=addrProofRadio][value=5]').attr("disabled", false);
  			}
          
      	//HKBR hide student plan
          if (doctype == "BS") {
        	$('#studentPlan').hide();
        	
        	if ($('#studentPlanSubInd').is(":checked")){
          		$('#studentPlanSubInd').attr("checked", false);					
        	}
        	
          	
          }else{
          	$('#studentPlan').show();
          }
      	
        }
    );
    
    $("input:radio[name=addrProofRadio]").change(function() {
      $(".bom_addr_tr").hide("slow");
      $("#bom_addr_override_div").hide("slow");
      if ($(this).val() == "0") {
        alertDepositRequired("DEP0001");
        nonReferAddressClicked();
      } else if ($(this).val() == "1") {
        nonReferAddressClicked();
      } else if ($(this).val() == "2") {
        referAddressClicked();
      } else if ($(this).val() == '5') {
    	preActivationChecked();
      }
    });

    if (refcustaddr == null) {
      $("#referAddressProof").attr('disabled', 'disabled');
    } 
  });
  
  //to override the register and billing address by selected LOB's register address
  function overrideAddress(){
	if ($("input:radio[name=addrProofRadio]:checked").val() != "2") return;
    if (refcustaddr==null) return;
    
    $("input#quickSearch").val('-');
    document.custForm.quickSearch.readOnly = true;
    //override the register address by LOB's Register Address
      $("#flat").val(refcustaddr.flat);
      $("#floor").val(refcustaddr.floor);
      $("#lotNum").val(refcustaddr.lotNum);
      $("#buildingName").val(refcustaddr.buildingName);
      $("#streetNum").val(refcustaddr.streetNum);
      $("#streetName").val(refcustaddr.streetName);
      $("#streetCatgCode").val(refcustaddr.streetCatgCode);
      $("#streetCatgDesc").val(refcustaddr.streetCatgDesc);
      $("#areaDesc").val(refcustaddr.areaDesc);
      $("#areaCode").val(refcustaddr.areaCode);
      $("#areaCodeSelect").val(refcustaddr.areaCode);
      $("#sectionCode").val(refcustaddr.sectionCode);
      $("#sectionCodeSelect").val(refcustaddr.sectionCode);
      $("#sectionDesc").val(refcustaddr.sectionDesc);
      $("#districtDesc").val(refcustaddr.districtDesc);
      $("#districtCode").val(refcustaddr.districtCode);
      $("#districtCodeSelect").val(refcustaddr.districtCode);
      $("#phInd").val(refcustaddr.phInd)
      
        //override the billing address by LOB's Register Address
      $("#billingFlat").val(refcustaddr.flat);
      $("#billingFloor").val(refcustaddr.floor);
      $("#billingLotNum").val(refcustaddr.lotNum);
      $("#billingBuildingName").val(refcustaddr.buildingName);
      $("#billingStreetNum").val(refcustaddr.streetNum);
      $("#billingStreetName").val(refcustaddr.streetName);
      $("#billingStreetCatgCode").val(refcustaddr.streetCatgCode);
      $("#billingStreetCatgDesc").val(refcustaddr.streetCatgDesc);
      $("#billingAreaDesc").val(refcustaddr.areaDesc);
      $("#billingAreaCode").val(refcustaddr.areaCode);
      $("#billingAreaCodeSelect").val(refcustaddr.areaCode);
      $("#billingSectionCode").val(refcustaddr.sectionCode);
      $("#billingSectionCodeSelect").val(refcustaddr.sectionCode);
      $("#billingSectionDesc").val(refcustaddr.sectionDesc);
      $("#billingDistrictDesc").val(refcustaddr.districtDesc);
      $("#billingDistrictCode").val(refcustaddr.districtCode);
      $("#billingDistrictCodeSelect").val(refcustaddr.districtCode);

      
      
      
        
          
  }
  
  $(function() {
      // disable the backspace button .....
    function suppressBackspace(evt) {
          evt = evt || window.event;
          var target = evt.target || evt.srcElement;

          if (evt.keyCode == 8 && !(/input|textarea/i.test(target.nodeName) && !target.readOnly)) {
              return false;
          }
          
    }
    document.onkeydown = suppressBackspace;
    document.onkeypress = suppressBackspace;
    // dont allow to focus on readonly text field (for IE)
    $("input[type='text']").focus(function() {
      if ($(this).attr('readonly')) {
        $(this).blur();
      }
    });
    
    updateAddrProofIndState();

  });
  
   //Dennis MIP3
  $(function() {
	  	
	  var orderID = "<c:out value='${orderDto.orderId}'/>";	
	  var ocid = "<c:out value='${orderDto.ocid}'/>";	
	  
	  if(orderID != null && orderID !="")	{
		  $("input:radio[name=brand]").attr("disabled",true);
	  }
	  
	
	  if(ocid != null &&  ocid !="")	{
	  	  $("input:radio[name=simType][value=H]").attr("disabled",true);
	  	  $("input:radio[name=simType][value=C]").attr("disabled",true); 
	  }

	  
	   $("input:radio[name=brand]").change(function() {
		  var brandId= $("input:radio[name=brand]:checked").val();
		  if (brandId == "0") {
				$('input[type=radio][name=simType][value=H]').attr("disabled", true);
				$('input[type=radio][name=simType][value=C]').prop('checked', true);

				$("#numType").val("C");
				$(".auth_button").hide();
				$('.billMediaCB').removeClass("blur");
				$('.billMediaCB').each(function() {
						$(this).unbind('click');
					});
				$('.billMediaCB').click(function() {selectOneChkBox($(this));});

		  }else{
			  if (ocid == null || ocid ==""){
				  $('input[type=radio][name=simType][value=H]').attr("disabled", false);
			  }
			  $('input[type=checkbox][name=selectedBillMedia][value=PAP_SUM_RW]').attr("checked",false);
				$('input[type=checkbox][name=selectedBillMedia][value=PAP_SUM_RW]').addClass("blur");
				$('.billMediaCB').each(function() {
					if ($(this).hasClass("blur")){
						$(".auth_button").show();
						$(this).click(function() {return false;});
					}
					});
		  }
       
      });	

	  $("input:radio[name=simType]").change(function() {
		  var simType= $("input:radio[name=simType]:checked").val();
		  if (simType == "H") {
			  $('input[type=radio][name=brand][value=0]').attr("disabled", true);
			  
			  $('#smsOptOutFirstRoam').hide();
	        	$('#smsOptOutRoamHu').hide();
	          
	        	$('#smsOptOutNote').show();
			  
		  }else{
			  if ("<c:out value='${mipDisable1010}'/>" != 'Y'){
				  if(orderID == null || orderID == "")	{
				  	$('input[type=radio][name=brand][value=0]').attr("disabled", false);
				  }
		  	  }
			  
	        	$('#smsOptOutFirstRoam').show();
	        	$('#smsOptOutRoamHu').show();
	        	
	        	$('#smsOptOutNote').hide()
			  
		  }
		  
		 $("#numType").val(simType);
       
      });
	  
	
	  $("input:radio[name=brand]").trigger("change");
	  
	  //combine account clear 
	  $("input:radio[name=brand],#docType,#idDocNum").on("change",function() {	
			$('#activeMobileNum,#acctInfo').val("");	
	
		});
	  
  });
  
   
	function getBomAddrSingleLine(bomAddrDto) {
	  if (bomAddrDto == null) {
		  return "";
	  }
	  var bomAddrSingleLine = "";
	  if (bomAddrDto.unitNum != null) {
		  bomAddrSingleLine = "Flat " + bomAddrDto.unitNum + ", ";
	  }
	  if (bomAddrDto.floorNum != null) {
		  bomAddrSingleLine += "Floor " + bomAddrDto.floorNum + ", ";
	  }
	  if (bomAddrDto.buildName != null) {
		  bomAddrSingleLine += bomAddrDto.buildName + ", ";
	  }
	  if (bomAddrDto.hlotNum != null) {
		  bomAddrSingleLine += "Lot/House " + bomAddrDto.hlotNum + ", ";
	  }
	  if (bomAddrDto.streetNum != null) {
		  bomAddrSingleLine += bomAddrDto.streetNum + " " + bomAddrDto.streetName + " " + bomAddrDto.stCatgDesc + ", ";
	  }
	  if (bomAddrDto.sectDesc != null && bomAddrDto.sectDesc != "null") {
		  bomAddrSingleLine += bomAddrDto.sectDesc + ", ";
	  }
	  if (bomAddrDto.distrDesc != null) {
		  bomAddrSingleLine += bomAddrDto.distrDesc + ", ";
	  }
	  if (bomAddrDto.areaDesc != null) {
		  bomAddrSingleLine += bomAddrDto.areaDesc;
	  }
	  return bomAddrSingleLine;
  }
   
  function compareAddress(bomAddrDto) {
	  var isEqualAddr = true;
	  
	  if ((bomAddrDto.unitNum) != null || $("#flat").val() != "") {
		  if (bomAddrDto.unitNum != $("#flat").val()) {
			  isEqualAddr = false;
		  }
	  }
	  if ((bomAddrDto.floorNum) != null || $("#floor").val() != "") {
		  if (bomAddrDto.floorNum != $("#floor").val()) {
			  isEqualAddr = false;
		  }
	  }
	  if ((bomAddrDto.buildName) != null || $("#buildingName").val() != "") {
		  if (bomAddrDto.buildName != $("#buildingName").val()) {
			  isEqualAddr = false;
		  }
	  }
	  if ((bomAddrDto.hlotNum) != null || $("#lotNum").val() != "") {
		  if (bomAddrDto.hlotNum != $("#lotNum").val()) {
			  isEqualAddr = false;
		  }
	  }
	  if ((bomAddrDto.streetNum) != null || $("#streetNum").val() != "") {
		  if (bomAddrDto.streetNum != $("#streetNum").val()) {
			  isEqualAddr = false;
		  }
	  }
	  if ((bomAddrDto.streetName) != null || $("#streetName").val() != "") {
		  if (bomAddrDto.streetName != $("#streetName").val()) {
			  isEqualAddr = false;
		  }
	  }
	  if ((bomAddrDto.stCatCd) != null || $("#streetCatgCode").val() != "") {
		  if (bomAddrDto.stCatCd != $("#streetCatgCode").val()) {
			  isEqualAddr = false;
		  }
	  }
	  if ((bomAddrDto.stCatCd) != null || $("#streetCatgCode").val() != "") {
		  if (bomAddrDto.stCatCd != $("#streetCatgCode").val()) {
			  isEqualAddr = false;
		  }
	  }
	  if ((bomAddrDto.sectCd) != null || $("#sectionCode").val() != "") {
		  if (bomAddrDto.sectCd != $("#sectionCode").val()) {
			  isEqualAddr = false;
		  }
	  }
	  if ((bomAddrDto.distrNum) != null || $("#districtCode").val() != "") {
		  if (bomAddrDto.distrNum != $("#districtCode").val()) {
			  isEqualAddr = false;
		  }
	  }
	  return isEqualAddr;
  }
  	
	function getBomCustAddr(idDocType, idDocNum) {
	  $.ajax({
			url : "bomaddressdtl.html"
			, data: { idDocType: idDocType, idDocNum: idDocNum }
			, type : 'POST'
			, dataType : 'json'
			, timeout : 5000
			, error : function() {
				alert('Error loading bom address!');
				$("#bom_addr").text("");
				$(".bom_addr_tr").hide("slow");
			}
			, success : function(msg) {
				bomAddrDto = msg.bomAddr;
				if (bomAddrDto == null) {
					$("#bom_addr_override_div").hide('slow');
					$(".bom_addr_tr").hide('slow');
				} else {
					$("#bom_addr_override_div").show('slow');
					$(".bom_addr_tr").show('slow');
					$("#bom_addr").text(getBomAddrSingleLine(bomAddrDto));
				}
			}
		});
	}

	
	function sameAsCustClick() {
		if ($('#sameAsCust1').is(':checked')) {

			$("#actualUserDiv").hide();
			
			//clear data
			
		} else {
			$("#actualUserDiv").show();

		}
	}
	
	function sameAsEbillAddrClick() {
		if ($('#sameAsEbillAddr').is(':checked')) {

			$("#pcrfAlertEmail").val($('#emailAddr').val());
			$('#pcrfAlertEmail').prop('disabled', true)
			//clear data
			
		} else {
			$('#pcrfAlertEmail').prop('disabled', false)

		}
	}
	
	
	  $(function() {
		 
		  studentPlanSubControl();
		
		  
		  $('#studentPlanSubInd').click(function() { 
			  studentPlanSubControl();
 
			  
			  var dobMsg ="";
 			  if ($(this).is(":checked")) { 
 				 dobMsg = "Age 11-17 Student Plan Sub selected. Please input/re-input date of birth.(Within 11-17 years old)";
			  }else{
				 dobMsg = "Age 11-17 Student Plan Sub unchecked. Please input/re-input date of birth.(Aged 18 or above)";
			  }
 			  
 			 $("<p>"+dobMsg+"</p>").dialog({
					resizable: false,
					height:"200",
			        width:"550",
					title:'Age 11-17 Student Plan Sub',
				    modal:true,
				    buttons:{
					    "Confirm":function(){
					    	$(this).dialog("close");
					    }
				    }
			  });
		  }); 
	
		  
	  });
	  
	  
	  function studentPlanSubControl(){
		 
		  if ($('#studentPlanSubInd').is(":checked")) { 
				  
			  
			
	        var minDate = $.datepicker.parseDate('dd/mm/yy', '${appDateString}');
	        minDate.setFullYear(minDate.getFullYear()-18);
	        minDate.setDate(minDate.getDate()+1);
	        
	        var maxDate = $.datepicker.parseDate('dd/mm/yy', '${appDateString}');
	        maxDate.setFullYear(maxDate.getFullYear()-11);
	        
	        
	        $('#dobDatepicker').datepicker('option', 'minDate', minDate);
	        $('#dobDatepicker').datepicker('option', 'maxDate', maxDate); 
	        $('#mob0060OptOutInd1').attr('checked', true); 
	        $('#mob0060OptOutInd1').prop('disabled', true);
	        
		  }else{
			
			        	
	        	
	        $('#dobDatepicker').datepicker('option', 'minDate', '-100Y+1D');
	        $('#dobDatepicker').datepicker('option', 'maxDate', '-18Y');    
	        //$('#mob0060OptOutInd1').attr('checked', false); 
	        $('#mob0060OptOutInd1').prop('disabled', false);
	  		
		  }
	  }

	 	
	 	
	 	function disableCareCashButton(){
	 		$('.careCash').prop('disabled', true);
	 		$('#customercareDmSupInd').prop('disabled', true);
	 	}
  
</script>

<form:form method="POST" name="custForm" commandName="customerProfile">
	<!-- customer info table -->
	<table style="width: 100%" class="tablegrey">
	<tr>
		<td bgcolor="#FFFFFF"><!--content-->
			<table style="width: 100%" border="0" cellspacing="0" rules="none">
			<tr>
				<td class="table_title">Customer's information</td>
				<c:if test="${channelId == '2'}">
				<td class="table_title" align="right">
				<c:choose>
				<c:when test="${workStatus == 'recall' &&(orderDto.busTxnType == 'PEND' ||  orderDto.busTxnType == 'PRE')}">
					<form:checkbox path="byPassValidation" disabled="true" label="By-pass validation(save as Draft only)"/>
				</c:when>
				<c:otherwise>
					<form:checkbox path="byPassValidation" label="By-pass validation(save as Draft only)"/>
				</c:otherwise>
				</c:choose>
				</td>
				</c:if>
			</tr>
			</table>
	
			<table style="width: 100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">

			<tr>
				<td colspan="3" style="height:25px">
					<c:if test="${channelId == '1' && empty MNP}">
						<div align="right">	
						<c:url var="popupMrtSelectionUrl" value="/mrtselection.html">
										<c:param name="type" value="customerprofile"/>
						</c:url>	
						<a href="#" onClick="popUpMRTSelection('${popupMrtSelectionUrl}');return false;" class="nextbutton">MRT Search</a>
						
						</div>
					</c:if>
				
				</td>
			</tr>
			
			<tr>
				<td width="30%">
					<div align="right">
					<spring:message code="label.brand"/>
					<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="20%" colspan="1">
					<div class="brand" align="left">			
					<form:radiobutton path="brand" value="1"/><label for="csllogo"><img src="images/csl_logo.jpg"/></label>								
					</div>				
				</td>
				<td width="50%" colspan="1">
					<div class="brand" align="left">
					<form:radiobutton path="brand" value="0" disabled="${mipDisable1010 eq 'Y' }"/><label for="1010logo"><img src="images/1010_logo.jpg"/></label>
					<form:errors path="brand" cssClass="error brand" />								
					</div>					
				</td>
			</tr>
			<tr style="height:60px">
				<td width="30%">
					<div align="right">
					<spring:message code="label.simType"/>
					<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="20%" colspan="1"><!--modify by eliot 20110609-->
					<div align="left">				
					<form:radiobutton path="simType" value="C" disabled="${mipDisableCSim eq 'Y' }" label="C-SIM" />								
					</div>				
				</td>
				<td width="50%" colspan="1">
					<div align="left">				
					<form:radiobutton path="simType" value="H" label="H-SIM" />
					<form:errors path="simType" cssClass="error" />	
					<form:errors path="numType" cssClass="error" />												
					</div>					
				</td>
			</tr>
			
			<tr>
				<td width="30%">
					<div align="right">
					<spring:message code="label.idDocType"/>
					<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2"><!--modify by eliot 20110609-->
					<div align="left">
					<c:if test="${workStatus != 'recall' || orderDto.busTxnType == 'DRAF'}">
						<form:select id="docType" path="idDocType" onchange="setCompanyCustomerDisplay()" cssClass="careCashField" >
							<form:option value="HKID" label="HKID" />
							<form:option value="PASS" label="Passport" />
							<!--add by eliot 20110608-->
							<form:option value="BS" label="HKBR" />
						</form:select>
					</c:if>
					<c:if test="${workStatus == 'recall' && orderDto.busTxnType != 'DRAF'}">
						<form:select id="docType" path="idDocType" onchange="setCompanyCustomerDisplay()" cssClass="careCashField" disabled="true">
							<form:option value="HKID" label="HKID" />
							<form:option value="PASS" label="Passport" />
							<!--add by eliot 20110608-->
							<form:option value="BS" label="HKBR" />
						</form:select>
					</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td width="30%">
					<div align="right">
						<spring:message code="label.idDocNum"/>
						<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2">
					<c:if test="${workStatus != 'recall' || orderDto.busTxnType == 'DRAF'}">
						<form:input path="idDocNum" maxlength="30" cssClass="careCashField" />
					</c:if>
					<c:if test="${workStatus == 'recall' && orderDto.busTxnType != 'DRAF'}">
						<form:input path="idDocNum" maxlength="30" cssClass="careCashField" disabled="true"/>
					</c:if>
					<label hidden="true" id="domHelper" for="foreignDomesticHelperInd">
						<form:checkbox path="foreignDomesticHelperInd" id="foreignDomesticHelperInd" cssClass="careCashField" />Domestic Helpers
					</label>
					<label id="studentPlan" for="studentPlanSubInd">
					
						<form:checkbox path="studentPlanSubInd" id="studentPlanSubInd" cssClass="careCashField" disabled="${not empty orderDto.orderId}"/>Age 11-17 Student Plan Sub
					</label>
					<c:if test="${customerProfile.mrtThresholdOverflow }">
				    	<a href="#" id="mrtThresholdOverflowAuthorize" class="nextbutton3" style="float:right">Authorize</a>
				    	<form:hidden path="mrtThresholdOverflowAuthInd" />
				    </c:if>
				    <input type="button" id="checkProfile" value="Check Profile" onclick="checkProfileClick()" />
					<form:errors path="idDocNum" cssClass="error" /> 
					<form:errors path="idDocType" cssClass="error" />
					<form:hidden path="csPortalStatus" />
				</td>
			</tr>
			<tr>
				<td width="30%"></td>
				<td width="70%" colspan="2" class="smalltextgray">
					<strong>Note 1:
					<span class="contenttext_red">*</span> 
					If HKID, please enter the ID in A999999(A)/AA999999(A) format
					</strong>
				</td>
			</tr>
			<!--add by eliot 20110608-->
			<tr>
				<td width="30%"></td>
				<td width="70%" colspan="2" class="smalltextgray">
					<strong>Note 2:
					<span class="contenttext_red">*</span> 
					If HKBR, please enter the BR in 12345678-123/12345678-ABC format
					</strong>
				</td>
			</tr>
		      <!--add on 20131024-->
		    <tr>
		        <td>&nbsp;</td>
		        <td colspan="3" class="smalltextgray">
		            <strong>Note 3:
		            <span class="contenttext_red">*</span> 
		            If PASSPORT, Deposit induces in further
		            </strong>
		        </td>
		    </tr>
		    <!--cs portal-->
			<tr>
				<td width="30%"></td>
				<td  width="70%" colspan="2">
					<c:if test='${not (customerProfile.ignoreCustomerCheck==false&&customerProfile.isBomWsAvailable==true)}'>
						<form:checkbox path="ignoreCustomerCheck" />
						<span class="error">
							Customer checking service is unavailable, tick the checkbox if you want to ignore it.
						</span>
					</c:if>
				</td>
			</tr>
			<tr>
				 <td width="30%"></td>
				 <td width="70%" colspan="2" >
					 <div id="csPortalDiv" style="display:none;">
						<table  border="0" >
							<tr>
								<td style="color: blue">
									<label id="regMsg"></label>
								</td>
								
							</tr>
							
							<tr>
								<td style="color: blue">
									<label id="regCareCashMsg"></label>
								</td>
								
							</tr>
						</table>
					</div>
				</td>	
			</tr> 
		    <tr>
		    	<td width="30%">
					<div align="right" id="CompanyNameDiv">
						<spring:message code="label.companyName"/>
						<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2">
					<div id="CompanyNameInputFiledDiv">
						<form:input path="companyName" maxlength="40" /> 
						<form:errors path="companyName" cssClass="error" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="30%">
					<div align="right">
					<span id="titleLabel"><spring:message code="label.title"/></span>
					<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
						<span class="contenttext_red">*</span>
					</c:if>
					<c:if test="${channelId == '2'}">
						<span class="contenttext_red">*#</span>
					</c:if>
					</div>
				</td>
				<td colspan="2" width="70%">
					<form:select path="title">
						<form:option value="NONE" label="Select" />
						<form:options items="${titleList}" />
					</form:select> 
					<form:errors path="title" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td width="30%">
					<div align="right">
					<span id="custName"><spring:message code="label.nameInEng"/></span>
					<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
						<span class="contenttext_red">*</span>
					</c:if>
					<c:if test="${channelId == '2'}">
						<span class="contenttext_red">*#</span>
					</c:if>
					</div>
				</td>
				<td width="20%"><form:input path="custLastName" id="custLastName" maxlength="40" /></td> 
				<td  width="50%"><form:input path="custFirstName" id="custFirstName" maxlength="40" /></td>
			</tr>
			<tr>
				<td width="30%"></td>
				<td width="20%" class="contenttext">(<spring:message code="label.familyName"/></td>
				<td width="50%" class="contenttext" colspan="2">(<spring:message code="label.givenName"/>)</td>
			</tr>
			<tr>
				<td width="30%"></td>
				<td width="20%" class="contenttext"><form:errors path="custLastName" cssClass="error" /></td>	
				<td width="50%" class="contenttext"><form:errors path="custFirstName" cssClass="error" /></td>
			</tr>
			<tr>
				<td width="30%"></td>
				<td width="70%" colspan="2" ></td>
			</tr>
			
			<!-- Start Company representative div -->
			<tr id="repDocTypeTr" style="display:none">
				<td width="30%">
					<div align="right">
					<spring:message code="label.representative.idDocType"/>
					<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2">
					<div align="left">
					<c:if test="${workStatus != 'recall' || orderDto.busTxnType == 'DRAF'}">
						<form:select path="repIdDocType" onchange="setCompanyCustomerDisplay()" >
							<form:option value="" label="Select" />
							<form:option value="HKID" label="HKID" />
							<form:option value="PASS" label="Passport" />
						</form:select>
					</c:if>
					<c:if test="${workStatus == 'recall' && orderDto.busTxnType != 'DRAF'}">
						<form:select path="repIdDocType" onchange="setCompanyCustomerDisplay()" disabled="true">
							<form:option value="HKID" label="HKID" />
							<form:option value="PASS" label="Passport" />
						</form:select>
					</c:if>
					<form:errors path="repIdDocType" cssClass="error" />
					</div>
				</td>
			</tr>
			<tr id="repDocNumTr" style="display:none">
				<td width="30%">
					<div align="right">
						<spring:message code="label.representative.idDocNum"/>
						<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2">
					<c:if test="${workStatus != 'recall' || orderDto.busTxnType == 'DRAF'}">
						<form:input path="repIdDocNum" maxlength="30" />
					</c:if>
					<c:if test="${workStatus == 'recall' && orderDto.busTxnType != 'DRAF'}">
						<form:input path="repIdDocNum" maxlength="30" disabled="true"/>
					</c:if>
					<label hidden="true" id="domHelper" for="foreignDomesticHelperInd">
						<form:checkbox path="foreignDomesticHelperInd" id="foreignDomesticHelperInd"/>Domestic Helpers
					</label>
					<form:errors path="repIdDocNum" cssClass="error" /> 
				</td>
			</tr>
			<tr id="companyDocTr" style="display:none">
				<td width="30%">
					<div align="right">
						<spring:message code="label.companyDoc"/>
						<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2">
					<form:radiobutton path="companyDoc" value="C" label="Company Name Card" />
					<form:radiobutton path="companyDoc" value="L" label="Authorized Letter" />
					<form:errors path="companyDoc" cssClass="error" />
				</td>
			</tr>
			<!-- End Company representative div -->
			
			<tr>
				<!--add by eliot 20110608-->
				<td width="30%">
					<div align="right" id="dateOfBirthDiv">
						<spring:message code="label.dateOfBirth"/>
						<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2">
					<div id="dateOfBirthInputFieldDiv">
						<c:choose>
							<c:when test="${channelId != '10' and channelId != '11'}">						
								<form:input path="dobStr" maxlength="10" id="dobDatepicker" cssClass="careCashField"  readonly="true" />
							</c:when>
							<c:otherwise>
								<form:input path="dobStr" maxlength="10" readonly="false" cssClass="careCashField"/>(Date format:dd/mm/yyyy)
							</c:otherwise>
						</c:choose>
						<form:errors path="dobStr" cssClass="error" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="30%">
					<div align="right">
						<spring:message code="label.contactPhone"/>
						<c:if test="${channelId == '1' or channelId == '3'}">
							<span class="contenttext_red">*</span>
						</c:if>
						<c:if test="${channelId == '2'}">
							<span class="contenttext_red">*#</span>
						</c:if>
						<c:if test="${channelId == '10' or channelId == '11'}">
							<span class="contenttext_red">*</span>
						</c:if>
					</div>
				</td>
				<td width="70%" colspan="2">
					<form:input maxlength="8" path="contactPhone" />
					<form:errors path="contactPhone" cssClass="error" />
				</td>
			</tr>
					<%-- add by herbert 20110720 start--%>
			<tr>
				<td width="30%">
					<div align="right">
						<spring:message code="label.otherContactPhone"/>
					</div>
				</td>
				<td width="70%" colspan="2">
					<form:input maxlength="64" path="otherContactPhone" />
					<form:errors path="otherContactPhone" cssClass="error" />
				</td>
			</tr>
					<%-- add by herbert 20110720 end--%>	
			<tr>
				<td width="30%">
					<div align="right">
						<spring:message code="label.emailAddr"/>
						<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2"><!--  -->
				 	<form:input path="emailAddr" cssClass="careCashField"  maxlength="64" size="40" disabled="${customerProfile.noEmailFlag}" readonly="${orderDto.ocid != null && customerProfile.csPortalBool}"/> 
					<form:checkbox path="noEmailFlag" onclick="noEmailFlagClick()" id="noEmailFlag" cssClass="careCashField"  label="No Email" disabled="${orderDto.ocid != null && customerProfile.csPortalBool}"/>
					<form:hidden path="dummyEmail"/>
					<form:errors path="emailAddr" cssClass="error" />
				</td>
			</tr>
			<tr><td width="30%"></td>
				<td width="70%" colspan="2"><!--cs portal-->
				<div id="csPortalReg" style="display:none;">
					<table border="0" >
						<tr>
							<td>
								<form:checkbox path="csPortalBool" id="csPortalBool"/><span id="csPortalRegMsg"></span>
												
							</td>
							<td><br><form:errors path="csPortalBool" cssClass="error" />	</td>
						</tr>
	
						<tr id="optOutHKT" style="display:none">
							<td>
								<form:checkbox path="hktOptOut" id="hktOptOut"/><span id="optOutHKT" disabled="true">Opt-out Digital Marketing of MyHKT</span>
							</td>
						</tr>
						<tr id="optOutClub" style="display:none">
							<td>
								<form:checkbox path="clubOptOut" id="clubOptOut"/>Opt-out Digital Marketing of The Club
							</td>
						</tr>
						<tr id="optOutHKTAndClub" style="display:none">
							<td>
								<form:checkbox path="hktClubOptOut" id="hktClubOptOut"/>Opt-out Digital Marketing of MyHKT And The Club
							</td>
						</tr>
						<tr class="clubOptOutReasonRemark">
							<td>
								Opt-out Reasons: 
								<form:select path="clubOptRea" disabled="true">
									<c:forEach var="clubOptoutReason" items="${clubOptReaList}">
										<option value="${clubOptoutReason.codeId}">${clubOptoutReason.codeDesc}</option>
									</c:forEach>
						       </form:select>
						     <form:errors path="clubOptRea" cssClass="error" />
							</td>
						</tr>
						<tr class="clubOptOutReasonRemark">
							<td>
								Opt-out Remarks: <form:input path="clubOptRmk" maxlength="64" size="50" disabled="true" /> 
								<form:errors path="clubOptRmk" cssClass="error" />
							</td>
						</tr>

					</table>
				</div>
			</td>	
			</tr>
			
			<tr>
				<td width="30%"></td>
				<td width="70%" colspan="2" class="smalltextgray">
				<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
					<strong>
						Note: <span class="contenttext_red">*</span> Mandatory Field
					</strong>
				</c:if>
				<c:if test="${channelId == '2'}">
					<strong>
						Note 3: <span class="contenttext_red">*</span> Pre, Pend Order Mandatory Field
					</strong>
					<br>
					<strong>
						Note 4: <span class="contenttext_red">#</span> Draft Order Mandatory Field
					</strong>
				</c:if>					
				</td>
			</tr>
			<tr>
				<td width="30%"><div align="right"></div></td>
				<td width="70%" colspan="2">&nbsp;</td>
			</tr>
			</table>

		</td>
	</tr>
	</table>
	<!-- end customer info table -->
	
	<!-- actual user info table -->
	<table style="width: 100%" class="tablegrey">
	<tr>
		<td bgcolor="#FFFFFF"><!--content-->
			<table style="width: 100%" border="0" cellspacing="0" rules="none">
			<tr>
				<td class="table_title">Actual User</td>
			</tr>
			</table>
			
			<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="80%" colspan="3">
						<form:checkbox path="sameAsCust" onclick="sameAsCustClick()" />
							Same as Registered User
						</td>
					</tr>

					<tr>
					</tr>
			</table>
			
			<div id="actualUserDiv" style="display: block;">
			<table style="width: 100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
			<tr>
				<td width="30%">
					<div align="right">
					<spring:message code="label.idDocType"/>
					<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2"><!--modify by eliot 20110609-->
					<div align="left">
					<c:if test="${workStatus != 'recall' || orderDto.busTxnType == 'DRAF'}">
						<form:select id="actualUserDocType" path="actualUserDTO.subDocType" onchange="setActualUserCompanyCustomerDisplay()">
							<form:option value="HKID" label="HKID" />
							<form:option value="PASS" label="Passport" />
							<!--add by eliot 20110608-->
							<form:option value="BS" label="HKBR" />
						</form:select>
					</c:if>
					<c:if test="${workStatus == 'recall' && orderDto.busTxnType != 'DRAF'}">
						<form:select id="actualUserDocType" path="actualUserDTO.subDocType" onchange="setActualUserCompanyCustomerDisplay()" disabled="true">
							<form:option value="HKID" label="HKID" />
							<form:option value="PASS" label="Passport" />
							<!--add by eliot 20110608-->
							<form:option value="BS" label="HKBR" />
						</form:select>
					</c:if>
					</div>
					
				</td>
			</tr>
			<tr>
				<td width="30%">
					<div align="right">
						<spring:message code="label.idDocNum"/>
						<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2">
					<c:if test="${workStatus != 'recall' || orderDto.busTxnType == 'DRAF'}">
						<form:input path="actualUserDTO.subDocNum" maxlength="30" />
					</c:if>
					<c:if test="${workStatus == 'recall' && orderDto.busTxnType != 'DRAF'}">
						<form:input path="actualUserDTO.subDocNum" maxlength="30" disabled="true"/>
					</c:if>
					<form:errors path="actualUserDTO.subDocNum" cssClass="error" /> 
					<form:errors path="actualUserDTO.subDocType" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td width="30%"></td>
				<td width="70%" colspan="2" class="smalltextgray">
					<strong>Note 1:
					<span class="contenttext_red">*</span> 
					If HKID, please enter the ID in A999999(A)/AA999999(A) format
					</strong>
				</td>
			</tr>
			<!--add by eliot 20110608-->
			<tr>
				<td width="30%"></td>
				<td width="70%" colspan="2" class="smalltextgray">
					<strong>Note 2:
					<span class="contenttext_red">*</span> 
					If HKBR, please enter the BR in 12345678-123/12345678-ABC format
					</strong>
				</td>
			</tr>
		    <tr>
		    	<td width="30%">
					<div align="right" id="actualUserCompanyNameDiv">
						<spring:message code="label.companyName"/>
						<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2">
					<div id="actualUserCompanyNameInputFiledDiv">
						<form:input id="actualUserCompanyName" path="actualUserDTO.subCompanyName" maxlength="40" /> 
						<form:errors path="actualUserDTO.subCompanyName" cssClass="error" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="30%">
					<div align="right">
					<span id="actualUserTitleLabel"><spring:message code="label.title"/></span>
					<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
						<span class="contenttext_red">*</span>
					</c:if>
					<c:if test="${channelId == '2'}">
						<span class="contenttext_red">*#</span>
					</c:if>
					</div>
				</td>
				<td colspan="2" width="70%">
					<form:select path="actualUserDTO.subTitle">
						<form:option value="NONE" label="Select" />
						<form:options items="${titleList}" />
					</form:select> 
					<form:errors path="actualUserDTO.subTitle" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td width="30%">
					<div align="right">
					<span id="actualUserName"><spring:message code="label.nameInEng"/></span>
					<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
						<span class="contenttext_red">*</span>
					</c:if>
					<c:if test="${channelId == '2'}">
						<span class="contenttext_red">*#</span>
					</c:if>
					</div>
				</td>
				<td width="20%"><form:input path="actualUserDTO.subLastName" maxlength="40" /></td> 
				<td width="50%"><form:input path="actualUserDTO.subFirstName" maxlength="40" /></td>
			</tr>
			<tr>
				<td width="30%"></td>
				<td width="20%" class="contenttext">(<spring:message code="label.familyName"/></td>
				<td width="50%" class="contenttext" colspan="2">(<spring:message code="label.givenName"/>)</td>
			</tr>
			<tr>
				<td width="30%"></td>
				<td width="20%" class="contenttext"><form:errors path="actualUserDTO.subLastName" cssClass="error" /></td>	
				<td width="50%" class="contenttext"><form:errors path="actualUserDTO.subFirstName" cssClass="error" /></td>
			</tr>
			<tr>
				<td width="30%"></td>
				<td width="70%" colspan="2" ></td>
			</tr>
			
			<tr>
				<!--add by eliot 20110608-->
				<td width="30%">
					<div align="right" id="actualUserDateOfBirthDiv">
						<spring:message code="label.dateOfBirth"/>
						<span class="contenttext_red">*</span>
					</div>
				</td>
				<td width="70%" colspan="2">
					<div id="actualUserDateOfBirthInputFieldDiv">
						<c:choose>
							<c:when test="${channelId != '10' and channelId != '11'}">						
								<form:input path="actualUserDTO.subDateOfBirthStr" maxlength="10" id="actualUserDobDatepicker" readonly="true" />
							</c:when>
							<c:otherwise>
								<form:input path="actualUserDTO.subDateOfBirthStr" maxlength="10" id="actualUserDob" readonly="false" />(Date format:dd/mm/yyyy)
							</c:otherwise>
						</c:choose>
						<form:errors path="actualUserDTO.subDateOfBirthStr" cssClass="error" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="30%">
					<div align="right">
						<spring:message code="label.contactPhone"/>
						<c:if test="${channelId == '1' or channelId == '3'}">
							<span class="contenttext_red">*</span>
						</c:if>
						<c:if test="${channelId == '2'}">
							<span class="contenttext_red">*#</span>
						</c:if>
						<c:if test="${channelId == '10' or channelId == '11'}">
							<span class="contenttext_red">*</span>
						</c:if>
					</div>
				</td>
				<td width="70%" colspan="2">
					<form:input maxlength="8" path="actualUserDTO.subContactTel" />
					<form:errors path="actualUserDTO.subContactTel" cssClass="error" />
				</td>
			</tr>
					
			<tr>
				<td width="30%">
					<div align="right">
						<spring:message code="label.emailAddr"/>
						<span class="contenttext_red">&nbsp;</span>
					</div>
				</td>
				<td width="70%" colspan="2"><!--  -->
				 	<form:input id="actualUserEmailAddr" path="actualUserDTO.subEmailAddr"  maxlength="64" size="40" /> 

					<form:errors path="actualUserDTO.subEmailAddr" cssClass="error" />
				</td>
			</tr>
			
			<tr>
				<td width="30%"></td>
				<td width="70%" colspan="2" class="smalltextgray">
				<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
					<strong>
						Note: <span class="contenttext_red">*</span> Mandatory Field
					</strong>
				</c:if>
				<c:if test="${channelId == '2'}">
					<strong>
						Note 3: <span class="contenttext_red">*</span> Pre, Pend Order Mandatory Field
					</strong>
					<br>
					<strong>
						Note 4: <span class="contenttext_red">#</span> Draft Order Mandatory Field
					</strong>
				</c:if>					
				</td>
			</tr>
			<tr>
				<td width="30%"><div align="right"></div></td>
				<td width="70%" colspan="2">&nbsp;</td>
			</tr>
			</table>
			</div>

		</td>
	</tr>
	</table>
	<!-- end actual user table -->
		
	<!-- register address bolck -->
	<table class="tablegrey" style="width: 100%">
	<tr style="background-color: white">
		<td><!--content-->
		<h3 class="table_title">Registered Address</h3>
		<!-- Address Table -->
		<table id="registered_addr_table" style="width: 100%" border="0" cellspacing="0" class="contenttext" background="images/background2.jpg">
			<colgroup style="width: 20%"></colgreoup>
			<colgroup></colgreoup>
			<colgroup></colgreoup>
			<colgroup></colgreoup>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="right">
					<span class="contenttext_red">*</span>
					<spring:message code="label.quickSearch"/>
				</td>
				<td colspan="3" align="left">
					<form:input size="100%" path="quickSearch" readonly="${customerProfile.custAddressFlag}" onclick="setCurrentSearchFrom('quickSearch')" />
					<span class="clearInput" title="Clear Input">x</span>
					<div style="clear: both"></div>
					<form:errors path="quickSearch" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td align="right">&nbsp;</td>
				<td colspan="3">
					<span class="contenttext">Simply input the estate name, 
					block no. or the building name of your installation address, 
					separate with commas</span>
				</td>
			</tr>
			<tr height="15px">
				<td colspan="4"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="3">
					<form:checkbox path="custAddressFlag" onclick="custAddressFlagClick()" label="Please tick the checkbox if you want to input the address manually."/>
					<br>
					<form:errors path="custAddressFlag" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<spring:message code="label.flat"/>
				</td>
				<td>
					<form:input maxlength="5" path="flat" id="flat"/>
				</td>
				<td align="right">
					<spring:message code="label.floor"/>
				</td>
				<td>
					<form:input maxlength="3" path="floor" id="floor"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<form:errors path="flat" cssClass="error" />
				</td>
				<td></td>
				<td>
					<form:errors path="floor" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					<spring:message code="label.lotNum"/>
				</td>
				<td>
					<form:input maxlength="8" path="lotNum" id="lotNum"/>
					<br>
					<form:errors path="lotNum" cssClass="error" />
				</td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td align="right">
					<spring:message code="label.buildingName"/>
				</td>
				<td colspan="3" align="left">
					<form:input maxlength="30" size="100%" readonly="${!customerProfile.custAddressFlag}" path="buildingName" id="buildingName"/>
					<div style="clear:both"></div>
					<form:errors path="buildingName" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					<spring:message code="label.streetNum"/>
				</td>
				<td>
					<form:input maxlength="11" readonly="${!customerProfile.custAddressFlag}" path="streetNum" id="streetNum"/>
				</td>
				<td align="right">
					<spring:message code="label.streetName"/>
				</td>
				<td>
					<form:input maxlength="23" readonly="${!customerProfile.custAddressFlag}" path="streetName" id="streetName"/>
				</td>
			</tr>

			<tr>
				<td></td>
				<td>
					<form:errors path="streetNum" cssClass="error" />
				</td>
				<td></td>
				<td>
					<form:errors path="streetName" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<spring:message code="label.streetCatgCode"/>
				</td>
				<td colspan="3">
					<form:input path="streetCatgDesc" readonly="${customerProfile.custAddressFlag}" id="streetCatgDesc"/>
					<form:select path="streetCatgCode">
						<form:option value="" label="" />
						<form:options items="${streetCatgList}" itemValue="categoryCode" itemLabel="categoryDesc" />
					</form:select>
					<form:errors path="streetCatgCode" cssClass="error" />
				</td>
			</tr>
			<!-- area district Section-->
			<tr>
				<td align="right">
					<spring:message code="label.sectionCode"/>
				</td>
				<td colspan="3">
				<table cellspacing="0">
					<tr>
						<td>
							<div id='loaderImagePlaceholder'></div>
						</td>
						<td>
							<select id='sectionCodeSelect' style="width: 250px;"></select>
						</td>
						<td>
							<form:input path="sectionDesc" readonly="${customerProfile.custAddressFlag}" id="sectionDesc"/>
						</td>
						<td>
							<form:hidden path="sectionCode" />
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td align="right">
					<spring:message code="label.districtCode"/>
				</td>
				<td colspan="3">
					<form:input path="districtDesc" readonly="${customerProfile.custAddressFlag}" id="districtDesc"/>
					<div id="unlinkDiv">
						<select id='districtCodeSelect' style="width: 250px;"></select> 
						<form:checkbox id="linkSectionId" path="unlinkSectionFlag" label="Unlink District and Section Code"/>
					</div>
					<form:hidden path="districtCode" /> 
					<form:errors path="districtCode" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<spring:message code="label.areaCode"/>
				</td>
				<td colspan="3">
					<select id="areaCodeSelect" style="width: 250px;"></select> 
					<form:input path="areaDesc" readonly="${customerProfile.custAddressFlag}" id="areaDesc"/> 
					<form:hidden path="areaCode" /> 
					<form:errors path="areaCode" cssClass="error" />
				</td>					
			</tr>
			<tr>
				<td align="right" style="color: blue">
					<spring:message code="label.phInd"/>
				</td>
				<td>
					<form:input path="phInd" readonly="true"/>
					<form:hidden path="serviceBoundaryNum"/>
				</td>
				<td align="right" ><!--<font color="blue">HKT Premier</font>--></td>
				<td>
					<form:input path="hktPremierAddr" readonly="true" cssStyle="display:none"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<span class="smalltextgray" style="font-weight:bold">
						Note: Only can determine "Public Housing Ind" <!-- &amp; "HKT Premier"--> by Quick search
					</span>
				</td>
			</tr>
			<!--  added for pre-activation -->
			<tr height="15px">
				<td colspan="4"></td>
			</tr>
			<tr class="bom_addr_tr" style="display:none">
				
				<td align="right" style="color: blue">Current BOM customer register address: </td>
				<td>
					<div id="bom_addr"></div>
				</td>
			</tr>
			<tr id="bom_addr_override_div" style="display:none; color: blue">
				<td></td>
				<td>
					<form:hidden path="bomCustAddrOverrideInd" />
					<spring:message code="label.bomAddrOverride"/>
				</td>
			</tr>
			<!--  end added for pre-activation -->
		</table>
		</td>
	</tr>
	</table>
	<!-- end register address bolck -->

<!--Account Information  ----------------------------------------------------------------------------->
	<table class="tablegrey acctInfoTable" style="width: 100%">
		<tr style="background-color: white">
			<td>
				<!--content-->
				<h3 class="table_title">Account Information</h3>
				<table id="registered_addr_table" style="width: 100%" border="0"
					cellspacing="0" class="contenttext"
					background="images/background2.jpg">
					<tr>
						<td>
							<div align="left">
								<span class="contenttext_red">*</span>
								<form:radiobutton path="acctType" id="acctType" value="new"
									label="New Account" onclick="radioSelection()" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div align="left">
								<span class="contenttext_red">*</span>
								<form:radiobutton path="acctType" id="acctType" value="current"
									label="Current Account" onclick="radioSelection()" />
							</div>
						</td>
					</tr>
					<tr>
						<td>Active Mobile No <form:input path="activeMobileNum" id="activeMobileNum"
								maxlength="8" onselect='resetCombineAcctInfo()' />
							<form:hidden path="acctNum" />
							<form:hidden path="billPeriod" /> <form:hidden path="mobCustNum" />
							<form:hidden path="bomCustNum" /> <form:hidden path="acctName" />
							<form:hidden path="acctName" />
							<form:hidden path="srvNum" />
							 <input type="button"
							id="retrieve" name="retrieve" value="retrieve"
							onclick="retrieveAccountInfo()" /> <form:errors
								path="activeMobileNum" cssClass="error" />
					</tr>
					<tr>
						<td>Account Info <form:input path="acctInfo" size="70%" /> <form:errors
								path="acctInfo" cssClass="error" />
						</td>
					</tr>
				</table>
	</table>
	<!--End Account Information  -->
	
	<!-- billing address bolck -->
	<table class="tablegrey" style="width: 100%">
	<tr>
		<td bgcolor="#FFFFFF"><!--content-->
			<h3 class="table_title">Billing Information</h3><!-- add paper bill medium (Start)-->
			<table style="width: 100%" border="0" cellspacing="1">
					<tr>
					<td class="contenttext">
						<table style="width: 100%" border="0" cellspacing="0" class="contenttext" background="images/background2.jpg">
						<tr><td colspan=2><form:errors path="selectedBillMedia" cssClass="error" /></td></tr>
						<c:if test="${not empty billMediaList}">
						<tr>
						<td width="20%"><label>
							<spring:message code="label.freebillMedium"/> 
						</label></td>
						<td width="80%">  
							<input type="checkbox" disabled=true checked=true />SMS
							 <c:forEach items="${billMediaList}" var="billMedia">
								 <c:if test="${billMedia.codeId == 'EML_SUM_RC'}">
									 <form:checkbox value="${billMedia.codeId}" label="EMAIL" path="selectedBillMedia" cssClass="billMediaCB"/>
									
								 </c:if> 
							 </c:forEach>
						</td>
						</tr>
						<tr>
						<td></td><td><span class="smalltextgray" style="font-weight:bold">Note1: SMS bill by default</span></td>
						</tr>
						
						<tr>
						<td></td><td><span class="smalltextgray" style="font-weight:bold">Note2: IDD 0060 bill media: default as Email bill
						</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						Addtion Bill Media align with mobile service subscribed</span></td>
						</tr>
						
						<tr>
						<td><label>
							<spring:message code="label.billMedium"/> 
							
						</label></td>
						<td>  
							 <c:forEach items="${billMediaList}" var="billMedia">
							 	<c:if test="${billMedia.codeId != 'EML_SUM_RC'}">
								 <c:if test="${billMedia.authorizeInd == 'Y'}">
									 <form:checkbox value="${billMedia.codeId}" label="${billMedia.codeDesc}" path="selectedBillMedia" cssClass="billMediaCB blur"/>
								 </c:if> 
								 <c:if test="${billMedia.authorizeInd == 'N'}">
									 <form:checkbox value="${billMedia.codeId}" label="${billMedia.codeDesc}" path="selectedBillMedia" cssClass="billMediaCB"/>
								 </c:if> 
								 </c:if>
							 </c:forEach>
											
							<a href="#" class="nextbutton3 auth_button" style="vertical-align: middle;display:none;">Authorize</a>
						</td>
						</tr>
						</c:if>	
						</table>
					</td>
				</tr>
				<tr>
					<td class="contenttext">
						<label>
							<form:checkbox path="noBillingAddressFlag" onclick="noBillingAddressClick1()"/>
							<spring:message code="label.noBillingAddressFlag"/>
						</label> 
					</td>
					</tr>
			</table>
			<div id="BillingDiv"><!-- Address Table -->
				<table style="width: 100%" border="0" cellspacing="0" class="contenttext" background="images/background2.jpg">
				<colgroup style="width: 20%"></colgreoup>
			<colgroup></colgreoup>
			<colgroup></colgreoup>
			<colgroup></colgreoup>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						<span class="contenttext_red">*</span>
					<spring:message code="label.quickSearch"/>
					</td>
					<td colspan="3" align="left">
						<form:input size="100%" path="billingQuickSearch" readonly="${customerProfile.billingCustAddressFlag}" onclick="setCurrentSearchFrom('billingQuickSearch')" />
						<span class="clearInput" title="Clear Input">x</span>
						<div style="clear: both"></div>
						<form:errors path="billingQuickSearch" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td align="right">&nbsp;</td>
					<td colspan="3">
						<span class="contenttext"> 
						Simply input the estate name, 
						block no. or the building name of your installation address, 
						separate with commas
						</span>
					</td>
				</tr>		
				<tr height="15px">
					<td colspan="4"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="3">
						<form:checkbox path="billingCustAddressFlag" onclick="billingCustAddressFlagClick()" label="Please tick the checkbox if you want to input the address manually."/> 
						<br>
						<form:errors path="billingCustAddressFlag" cssClass="error" />
					</td>
				</tr>
				<tr>
						<td align="right">
							<spring:message code="label.flat"/>
						</td>
						<td>
							<form:input maxlength="5" path="billingFlat" id="billingFlat"/>
						</td>		
						<td align="right">
							<spring:message code="label.floor"/>
						</td>
						<td>
							<form:input maxlength="3" path="billingFloor" id="billingFloor"/>
						</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<form:errors path="billingFlat" cssClass="error" />
					</td>
					<td></td>
					<td>
						<form:errors path="billingFloor" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td align="right">
							<spring:message code="label.lotNum"/>
					</td>
					<td>
						<form:input maxlength="8" path="billingLotNum" id="billingLotNum"/>
						<div style="clear:both"></div>
						<form:errors path="billingLotNum" cssClass="error" />
					</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						<spring:message code="label.buildingName"/>
					</td>
					<td colspan="3" align="left">
						<form:input maxlength="30" size="100%" readonly="${!customerProfile.billingCustAddressFlag}" path="billingBuildingName" id="billingBuildingName"/>
						<div style="clear:both"></div>
						<form:errors path="billingBuildingName" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td align="right">
							<spring:message code="label.streetNum"/>
						</td>
					<td>
						<form:input maxlength="11" readonly="${!customerProfile.billingCustAddressFlag}" path="billingStreetNum" id="billingStreetNum"/>
					</td>
					<td align="right">
						<spring:message code="label.streetName"/>
					</td>
					<td>
						<form:input maxlength="23" readonly="${!customerProfile.billingCustAddressFlag}" path="billingStreetName" id="billingStreetName"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<form:errors path="billingStreetNum" cssClass="error" />
					</td>
					<td></td>
					<td>
						<form:errors path="billingStreetName" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<spring:message code="label.streetCatgCode"/>
					</td>
					<td colspan="3">
						<form:input path="billingStreetCatgDesc" readonly="${customerProfile.billingCustAddressFlag}" id="billingStreetCatgDesc"/> 
						<form:select path="billingStreetCatgCode">
							<form:option value="" label="" />
							<form:options items="${streetCatgList}" itemValue="categoryCode" itemLabel="categoryDesc" />
						</form:select>
						<form:errors path="billingStreetCatgCode" cssClass="error" />
					</td>
				</tr>
				<!-- area district Section-->
				<tr>
					<td align="right">
						<spring:message code="label.sectionCode"/>
					</td>
					<td colspan="3">
					<table cellspacing="0">
					<tr>
						<td>
							<div id='loaderImagePlaceholder'></div>
						</td>
						<td>
							<select id='billingSectionCodeSelect' style="width: 250px;"></select>
						</td>
						<td>
							<form:input path="billingSectionDesc" readonly="${customerProfile.billingCustAddressFlag}" id="billingSectionDesc"/>
						</td>
						<td>
							<form:hidden path="billingSectionCode" />
						</td>
					</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td align="right">
						<spring:message code="label.districtCode"/>
					</td>
					<td colspan="3">
						<form:input path="billingDistrictDesc" readonly="${customerProfile.billingCustAddressFlag}" id="billingDistrictDesc"/>
						<div id="billingUnlinkDiv">
							<select id='billingDistrictCodeSelect' style="width: 250px;"></select> 
							<form:checkbox path="billingUnlinkSectionFlag" label="Unlink District and Section Code"/> 
						</div>
						<form:hidden path="billingDistrictCode" /> 
						<form:errors path="billingDistrictCode" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<spring:message code="label.areaCode"/>
					</td>
					<td colspan="3">
						<select id="billingAreaCodeSelect" style="width: 250px;"></select> 
						<form:input path="billingAreaDesc" readonly="${customerProfile.billingCustAddressFlag}" id="billingAreaDesc"/> 
						<form:hidden path="billingAreaCode" /> 
						<form:errors path="billingAreaCode" cssClass="error" />
					</td>
				</tr>
				</table>
			</div>
		</td>
	</tr>
	</table>

	<!-- end billing address block -->

	<!-- Address Proof table  -->
	<table class="tablegrey" style="width: 100%">
		<tr style="background-color: white">
			<td><!--content-->
				<h3 class="table_title">Address Proof</h3>
				<table style="width: 100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>
						<form:errors path="addrProofInd" cssClass="error" />
						<form:hidden path="addrProofInd" />
					</td>
				</tr>
        <tr>
          <td>
            <div align="left">
              <span class="contenttext_red">*</span>
              <input type="radio" name="addrProofRadio" value="0" <c:if test="${not empty orderDto.ocid}">disabled</c:if> />No Address Proof
            </div>
          </td>     
        </tr>
        <tr>
          <td>
            <div align="left">
              <span class="contenttext_red">*</span>
              <input type="radio" name="addrProofRadio" value="1" <c:if test="${not empty orderDto.ocid}">disabled</c:if> />With Address Proof
            </div>
          </td>     
        </tr>       

        <tr>
          <td>
            <span class="contenttext_red">*</span> 
            <label>
              <input type="radio" name="addrProofRadio" value="2" id="referAddressProof" <c:if test="${not empty orderDto.ocid}">disabled</c:if> />
              <spring:message code="label.addrProofInd.Refer"/>
            </label>
          </td>
        </tr>
        
				<tr>
					<td style="padding-left: 3.5em">
            <%--<form:input path="lob" maxlength="15"  disabled="${empty selectServiceLine}"/> --%>
            <input type="text" value="${customerProfile.lob}" disabled="disabled"/>
            <form:hidden path="lob"/>
            <!-- 
            <form:select path="lob" id="loblist" disabled="${empty selectServiceLine}">
              <form:option value="" label="Select" />
              <form:options items="${lobList}" />
            </form:select>
            <form:errors path="lob" cssClass="error" />
             -->
					</td>
          <td>
            <spring:message code="label.serviceNum"/> 
            <%--<form:input path="serviceNum" maxlength="15"  disabled="${empty selectServiceLine}"/> --%>
            <input type="text" value="${customerProfile.serviceNum}" disabled="disabled"/>
            <form:hidden path="serviceNum"/>
            <form:errors path="serviceNum" cssClass="error" />
          </td>
				</tr>
        <tr>
          <td colspan="2" class="smalltextgray">
            <strong>Note 1: LOB and service account no. or tel no. are required for refer address proof.</strong>
            <c:if test="${channelId == '2' }">
              <BR><strong>Note 2: Waived Address proof.</strong>
            </c:if>
          </td>
        </tr>
        
        <tr>
          <td>
            <div align="left">
              <span class="contenttext_red">*</span>
            	<input type="radio" name="addrProofRadio" value="5" <c:if test="${not empty orderDto.ocid or customerProfile.idDocType eq 'BS'}">disabled</c:if> />Pre - activation
            </div>
          </td>
        </tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
		</tr>
	</table>
	<!-- end Address Proof table  -->
	<!-- Billing Language table -->
	<table class="tablegrey" style="width: 100%">
		<tr style="background-color: white">
			<td><!--content-->
				<h3 class="table_title">Language Selection</h3>
				<table style="width: 100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td width="25%">
						<spring:message code="label.billlang"/>
						<span class="contenttext_red">*</span>
					</td>
					<td>
						<form:select path="billLang">
							<form:option value="BILN" label="Bilingual" />
							<form:option value="CHN" label="Traditional Chinese" />
							<form:option value="ENG" label="English" />
						</form:select> 
						<form:errors path="billLang" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td width="25%">
						<spring:message code="label.smslang"/> 
						<span class="contenttext_red">*</span>
					</td>
					<td>
						<form:select path="smsLang">
							<form:option value="00" label="Traditional Chinese" />
<!--						<form:option value="01" label="Simplified Chinese" />-->
							<form:option value="02" label="English" />
						</form:select> 
						<form:errors path="smsLang" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<!-- end Billing Language table -->

	<!-- Privacy table -->
	<table class="tablegrey" style="width: 100%">
		<tr style="background-color: white">
			<td>
				<!--content-->
				<h3 class="table_title">
					<spring:message code="label.privacyIndTitle" />
				</h3>
				<table style="width: 100%" border="0" cellspacing="1" class="contenttext"
					background="images/background2.jpg">

					<tr>
						<td>
						<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
								<form:checkbox path="privacyInd" onclick="privacyIndClick()"
									disabled="${orderDtoOriginal.ocid != null}" /><spring:message code="label.privacyInd" />
							</c:if> 
							
							<c:if test="${channelId == '2'}"> 
								<form:checkbox path="privacyInd" onclick="privacyIndClick()"
									disabled="${orderDto.ocid != null}" /><spring:message code="label.privacyInd" />
							</c:if> 
							
							</td>
					</tr>
				</table></td>
		</tr>
	</table>
	<!-- end Privacy table -->
	
	<!-- Suppress Top Up table -->
	<table class="tablegrey" style="width: 100%">
		<tr style="background-color: white">
			<td>
				<!--content-->
				<h3 class="table_title">
					<spring:message code="label.suppressTopUpTitle" />
				</h3>
				<table style="width: 100%" border="0" cellspacing="1" class="contenttext"
					background="images/background2.jpg">

					<tr>
						<td>
						
						<form:checkbox path="suppressLocalTopUpInd" 
						/><spring:message code="label.suppressLocalTopUpInd" />
						
						</td>
					</tr>
					
					<tr>
						<td>
						
						<form:checkbox path="suppressRoamTopUpInd" 
						/><spring:message code="label.suppressRoamTopUpInd" />
									
						</td>
					</tr>
					
					<tr>
						<td>
						<!-- onclick = "mob0060OptClick()" -->
						<form:checkbox path="mob0060OptOutInd"  id ="mob0060OptOutInd1"  
						/><spring:message code="label.mob0060OptOutInd" />
									
						</td>
					</tr>
					
				</table>
				</td>
		</tr>
	</table>
	<!-- end Suppress Top Up table -->


	<!-- Local/Roaming Data Alert Settings table -->
	<sb-util:codelookup var="pcrfAlertTypeList" codeType="PCRF_ALERT_TYPE"  asEntryList="true" />
	<sb-util:codelookup var="pcrfSmsRecipientList" codeType="PCRF_SMS_RECIPIENT"  asEntryList="true" />
	
	<table class="tablegrey" style="width: 100%">
		<tr style="background-color: white">
			<td>
				<!--content-->
				<h3 class="table_title">
					<spring:message code="label.localRoamingDataAlertSettingTitle" />
				</h3>
				<table style="width: 100%" border="0" cellspacing="1" class="contenttext"
					background="images/background2.jpg">
					
					<tr>
						<td width="25%">
						
							<spring:message code="label.pcrfAlertType" />
							
						</td>
						
						<td>
						
							<form:select path="pcrfAlertType" >
								<c:forEach var="pcrfAlertType" items="${pcrfAlertTypeList}">
					
									<form:option value="${pcrfAlertType.key}" label="${pcrfAlertType.value}" />
	
								</c:forEach>
							</form:select> 
									
						</td>
					</tr>
					
					<tr>
						<td width="25%">
						
							<spring:message code="label.pcrfAlertEmail" />
							
						</td>
						
						<td>
						
							<form:input path="pcrfAlertEmail" maxlength="64" size="40" />
						
							<form:checkbox path="sameAsEbillAddr" id="sameAsEbillAddr" onclick="sameAsEbillAddrClick()" 
							/><spring:message code="label.sameAsEbillAddrInd" />
							
							<form:errors path="pcrfAlertEmail" cssClass="error" />
						
						</td>
					</tr>
					
					<tr>
						<td width="25%">
						
							<spring:message code="label.pcrfSmsRecipient" />
							
						</td>
						
						<td>
						
							<form:select path="pcrfSmsRecipient" >
								<c:forEach var="pcrfSmsRecipient" items="${pcrfSmsRecipientList}">
					
									<form:option value="${pcrfSmsRecipient.key}" label="${pcrfSmsRecipient.value}" />
	
								</c:forEach>
							</form:select> 
									
						</td>
					</tr>
										
					<tr>
						<td width="25%">
						
							<spring:message code="label.pcrfSmsNum" />
							
						</td>
						
						<td>
						
							<form:input path="pcrfSmsNum" maxlength="20" />
							<form:errors path="pcrfSmsNum" cssClass="error" />
									
						</td>
					</tr>
					
					<tr id="smsOptOutFirstRoam">
						<td colspan="2">
						
							<form:checkbox path="smsOptOutFirstRoam" value="Y" />
							<spring:message code="label.smsOptOutFirstRoam" />						
									
						</td>
					</tr>
					
					<tr id="smsOptOutRoamHu">
						<td colspan="2">
						
						<form:checkbox path="smsOptOutRoamHu" value="Y" />
						<spring:message code="label.smsOptOutRoamHu" />
									
						</td>
					</tr>
					
					<tr id="smsOptOutNote">
						<td class="smalltextgray" colspan="2">
						
						<strong>Please go to MBQ to support H-SIM customer for Data roaming usage alert opt-out</strong>
									
						</td>
					</tr>
					
				</table>
				</td>
		</tr>
	</table>
	<!-- end Local/Roaming Data Alert Settings table -->
	<!-- iGuash Care Cash table  -->
	<div class="iGuardTable">
		<table class="tablegrey" style="width: 100%">
			<tr style="background-color: white">
				<td>
					<!--content-->
					<h3 class="table_title">
						<spring:message code="label.iGuardCareCashTitle" />
					</h3>
					<table style="width: 100%" border="0" cellspacing="1" class="contenttext"
						background="images/background2.jpg">
					</td>
					</tr>
					<tr>
					<td>
					<br> 
					<form:radiobutton path="careOptInd" id="careCashSubscried" cssClass="careCash" value="I" />
					<spring:message code="label.iGuardSubscribedCareCash" />
					<form:radiobutton path="careOptInd" id="careCashUnsubscribed" cssClass="careCash" value="O"/>
					<spring:message code="label.iGuardUnsubscribedCareCash" />
					<form:radiobutton path="careOptInd" id="careCashNotConsider" cssClass="careCash" value="P"/>
					<spring:message code="label.iGuardNotConsiderableCareCash" />
					<form:errors path="careOptInd" cssClass="error" />
					<br>
					<br>
	
					</td>
					
					<tr class="careCashPersonal">
					<td>
					<h3 class="table_title">
						<spring:message code="label.iGuardPersonalInformationTitle" />
					</h3>
					</td>
					</tr>
					<tr class="careCashPersonal">
					<td>
					<spring:message code="label.iGuardPersonalInformationDescription" />
					<br><br>
					<form:checkbox path="customercareDmSupInd" id="customercareDmSupInd"/><spring:message code="label.iGuardPersonalInformationPrivacy" />
							<br> <br>
					</td>
					</tr>
					
					</table>
					</td>
			</tr>
		</table>
	</div>			


	<!-- button table  -->
	<div class="buttonmenubox_R" id="buttonArea">
		<a href="#" class="nextbutton3 continue_button">continue &gt;</a>
	</div>

	<!-- end button table -->
	<input type="hidden" id="currentSearchFrom" value="" />
	<input type="hidden" name="currentView" value="customerprofile" />
	<form:hidden path="mrtSelection" id="mrtSelection"/>
	<form:hidden path="isBomWsAvailable" />
	<form:hidden path="lotHouseInd" />
	<form:hidden path="billingLotHouseInd" />
	<form:hidden path="csPortalInd" />	<!--  cs portal-->
	<form:hidden path="numType" /> <!--  Dennis MIP3 -->
	<form:hidden path="oBiptStatus" id="oBiptStatus" />
	<input type="hidden" name="oCareVisitInd"  id="oCareVisitInd" />
	<form:hidden path="checkAcctInfo" id="checkAcctInfo" />
	<input type="hidden" name="careCashButtonInd"  id="careCashButtonInd" />
	<form:hidden path="careCashRegisterTimeInd" id="careCashRegisterTimeInd" />
	<form:hidden path="careStatus" />
	<form:hidden path="theClubLogin" />
	<form:hidden path="clubMemberId" />
	
</form:form>
<div id="authDialog"></div>
<!---------------------------------------  end content ------------------------------------------->

<%@ include file="/WEB-INF/jsp/footer.jsp"%>