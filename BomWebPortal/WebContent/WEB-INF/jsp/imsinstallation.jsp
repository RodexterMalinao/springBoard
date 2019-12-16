	<%@ include file="header.jsp" %>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.imsautocomplete2.js"></script>
<script type="text/javascript" charset="utf-8" src="js/inputInterceptor.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.imsautocomplete.js"></script> 
<script type="text/javascript" charset="utf-8" src="js/inputInterceptorIms.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script><script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog_1ams.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<%@ include file="imsloadingpanel.jsp" %>
<%-- <%@ include file="imsvalidwindow.jsp" %> --%>
<script type="text/javascript" charset="utf-8">
	var areaSearchInput;
	var districtSearchInput;
	var sectionSearchInput;
	var jsphasBBDialUp=false;
	var isReserved=false;
	var hktMobileNumEdited=false;
	var hktLoginIdEdited=false;
	var clubLoginIdEdited=false;
	var hktClubLoginIdEdited=false;
	var isValid = false;
	var firstReserve =true;
	var isDS = false;
	var clubCutOff = '<%= session.getAttribute("clubCutOff") %>';
	var careCashVisitDate = '<%= session.getAttribute("careCashVisitDate") %>';
	var careCashShowInd = '<%= session.getAttribute("careCashShowInd") %>';
	var careVisit = null;
	var careEnable = null;
	var careBip = null;
	var mobileOfferInd =  '<%= session.getAttribute("mobileOfferInd") %>';
	
	$(document).ready(function() {
		
		init();	
		
		
		var blCust = document.getElementById('blacklistCustInfo').value;
		if(blCust == ""){
			document.getElementById('warning_blacklist').style.display = "none";
		}else{
			document.getElementById('warning_blacklist_msg').innerHTML = document.getElementById('blacklistCustInfo').value;
			document.getElementById('warning_blacklist').style.display = "inline";
			document.getElementById('warning_blacklist').style.visibility = "visible";
		}
		
		if(document.getElementById('hktLoginId.errors')!=null){
			if(!(document.getElementById('hktLoginId.errors').innerHTML == '')){
				document.getElementById('error_hkt_login').style.display="";
			}else{
				document.getElementById('error_hkt_login').style.display="none";
			}
		}
		
		if(document.getElementById('clubLoginId.errors')!=null){
			if(!(document.getElementById('clubLoginId.errors').innerHTML == '')){
				document.getElementById('error_club_login').style.display="";
			}else{
				document.getElementById('error_club_login').style.display="none";
			}
		}
		
		if(document.getElementById('nowId.errors')!=null){
			if(!(document.getElementById('nowId.errors').innerHTML == '')){
				document.getElementById('error_now_login').style.display="";
			}else{
				document.getElementById('error_now_login').style.display="none";
			}
		}
		
		if(document.getElementById('hktClubLoginId.errors')!=null){
			if(!(document.getElementById('hktClubLoginId.errors').innerHTML == '')){
				document.getElementById('error_hktClub_login').style.display="";
			}else{
				document.getElementById('error_hktClub_login').style.display="none";
			}
		}
			
		
		$("#custLastName").val($("#custLastName").val().ltrim().rtrim());
		$("#custFirstName").val($("#custFirstName").val().ltrim().rtrim());
		
		if(mobileOfferInd == "Y"){
			ValidateIDNUM();
		}
		
		if("${ims_direct_sales}" == "true") {
			isDS = true;
			$("#custLastName, #custFirstName").blur(function(){ 
				document.getElementById('warning_hkid').style.display = "none";
				document.getElementById('warning_hkid_msg').innerHTML = "";
				getImsCustInfo(); 
			});
			if ($("#existingCustomerConflictWithName").val() == "Y") { 
				document.getElementById('warning_hkid').style.display = "block";
				document.getElementById('warning_hkid_msg').innerHTML = "<spring:message code='ims.pcd.imsinstallation.msg.036' />";
			}
			if($("#lob").val() == "IMS") document.getElementById('optInfoButton').innerHTML = ""; 
// 			if ($("#custNo").val() != null && $("#custNo").val().length >0){  
// 				checkCreateCOrder();     
// 			}
		}
		
		checkCreateCOrder(); 
		
		if (("${imsInstallationUI.isCC}" == "Y" && "${imsInstallationUI.isPT}" != "Y")
				||("${imsInstallationUI.isPT}" == "Y" && "${imsInstallationUI.mode}" != "R") )
		{
			$("#idVerified").attr("checked", true);
		}
		
		if ( "${imsInstallationUI.idDocType}" != "BS" )
		{
			change2Business(false);
		}
		
		$("input#billingQuickSearch").autocomplete("imsaddresslookup.html?type=address", {
			//minChars: 4,
			minChars : 1,
			delay : 600,
			selectFirst : false,
			max : 30,
			matchSubset : false,
			highlight : false,
			lob : "IMS",
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}
		});
		if($("#orderActionInd").val() == "W" && $("#approvalRequested").val() == "Y"){ //($("#orderStatus").val() == "31" || $("#orderStatus").val() == "32")){
			$(":input").attr("disabled", true);
		}				
		
// 		if(careEnable != null && careEnable != "N"){
		if(careCashShowInd !=null && careCashShowInd =="Y"){
			var careCash = $("#careCashInd").val();
			if(careCash!= null && careCash != "" && careCash!= "A" && careCash != "X" && careCash != "O" && isCareCashEnable(careVisit,careBip) ){
				showCARECashBlock();			
				careCashCheckBox();
			}else
				hideCARECashBlock();
		}
		
// 		else{
// 			hideCARECashBlock();
// 			$("#careCashInd").val("");
// 			$("#careCashOptOutInd").val("");
// 		}
		
		if ("${imsInstallationUI.isPT}" == "Y"){
			initModeBox();
		}
		
		
		if(("${ImsOrder.preRegSubmitted}" != null && "${ImsOrder.preRegSubmitted}" == "Y" )|| ($("#isPreReged").val()!= null && $("#isPreReged").val()=="Y")){
			 $("#preregbtn").removeClass("nextbutton");
			 $("#preregbtn").addClass("nextbuttonPrereg");
			 $("#preregbtn").attr("disabled", false);
// 			document.getElementById("preregBtn").disabled = true;
		}
		
	});
	
	$( function() {
		$( "#DOB" ).datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "-100Y",
			maxDate : "-18Y", //Y is year, M is month, D is day 
			yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		});
	
		$("#DOB").change(function(e) {
// 			if(isReserved && careEnable != null && careEnable != "N"){
			if(isReserved && careCashShowInd !=null && careCashShowInd =="Y"){		
				if(isCARECashValidAge($("#DOB").val())){
					if($("#careCashInd").val()!= "O" && $("#careCashInd").val()!= "A" &&  $("#docType").val() == "HKID"){	
						if(isCareCashEnable(careVisit,careBip)){
							$("#careCashInd").val("I");
							$("#careCashOptOutInd").val("I");
							document.getElementById('careCashEmail').value = "";
							document.getElementById('careCashMobile').value = "";
							showCARECashBlock();
							careCashCheckBox();
						}else{//-only applicable for pending "to be decide"
							hideCARECashBlock();							
						}		
						
					}
				}else{
					if(document.getElementById("CARECash").style.display == ""){
						hideCARECashBlock();
						$("#careCashInd").val("X"); //- customer not applicable to enroll	
						$("#careCashOptOutInd").val("");
					}
				}
			}
	
// 			else{
// 				hideCARECashBlock();
// 				$("#careCashInd").val(""); //- customer not applicable to enroll	
// 				$("#careCashOptOutInd").val("");
// 			}
				
				
		});	

	});
	
	
	function checkMobileOrFixed()
	{
		if ( 
				document.getElementById("warning_mobile").style.display == "" && $("#mobileNum").val() != "" 
			|| document.getElementById("warning_fixline").style.display == "" && document.getElementById('cntFixLineNum').value != ""
			|| document.getElementById('cntFixLineNum').value == "" && $("#mobileNum").val() == ""
		)
		{
			return true;
		}
		document.getElementById("warning_mobile").style.display == "none";
		document.getElementById("warning_fixline").style.display == "none";
		return false;
	}
	
	
	function formSubmit(){
		
	if(	careCashShowInd !=null && careCashShowInd =="Y"){
		if($('#CARECash').is(':visible')){
			if($('input[name=careCashCheckBox]:checked').val()=="I"){
				if($("#careCashEmail").val()==null || $("#careCashEmail").val()== ""){
					document.getElementById('CARECash2_error').style.display = "";
					document.getElementById('CARECash2_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.001"/>";	
					return false;
				}
				if($("#careCashMobile").val()==null || $("#careCashMobile").val()== ""){
					document.getElementById('CARECash3_error').style.display = "";
					document.getElementById('CARECash3_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.002"/>";					
				}
				if($('#CARECash2_error').is(':visible') ||$('#CARECash3_error').is(':visible') )
					return false;
			}else{
				document.getElementById('CARECash2_error').style.display = "none";
				document.getElementById('CARECash2_error_msg').innerHTML = 	"";
			}
		}else{
			if($("#careCashInd").val()==null || $("#careCashInd").val()== "" ||  $("#careCashInd").val()== "I"){
				$("#careCashInd").val("X");
				$("#careCashOptOutInd").val();
			}			
		}				
	}
	if(careBip !=null && careBip =="O"){
		$("#careCashInd").val("X");
		$("#careCashOptOutInd").val();
	}
	
		if($('#myHktReg13').is(':visible')){
			if(document.getElementById("optoutaN").checked == true){
				$("#csPortalTheClubOptOutInd").val($("#optoutaN").val());
			}
			if(document.getElementById("optoutaY").checked == true){
				$("#csPortalTheClubOptOutInd").val($("#optoutaY").val());
			}
		}
		
		if($('#myHktReg10').is(':visible')){
			if(document.getElementById("optoutbN").checked == true){
				$("#csPortalOptOutInd").val($("#optoutbN").val());
			}
			if(document.getElementById("optoutbY").checked == true){
				$("#csPortalOptOutInd").val($("#optoutbY").val());
			}
			$("#csPortalTheClubOptOutInd").val('');
		}
		
		if($('#myHktReg11').is(':visible')){
			if(document.getElementById("optoutcN").checked == true){
				$("#theClubOptOutInd").val($("#optoutcN").val());
			}
			if(document.getElementById("optoutcY").checked == true){
				$("#theClubOptOutInd").val($("#optoutcY").val());
			}
			$("#csPortalTheClubOptOutInd").val('');
		}
		
		if(document.getElementById("reservedId").value!=document.getElementById("loginName").value){
			alert("<spring:message code="ims.pcd.imsinstallation.msg.066"/>");		
			return;
			//Gary added to verify CS portal inputs
		}else if(document.getElementById('isRegHKTLoginId').value == "Y" && document.getElementById('isRegClubLoginId').value == "Y"){
			
			if(document.getElementById("hktLoginName_warning").style.display == ""){
				document.getElementById('hktLoginName_warning').style.display = "";
				document.getElementById('hktLoginName_warning_msg').innerHTML ="<spring:message code="ims.pcd.imsinstallation.msg.003"/>";
				return;
			}
		
			if(document.getElementById("hktClubLoginName").value == ""){
				document.getElementById('hktClubLoginName_error').style.display = "";
				document.getElementById('hktClubLoginName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.004"/>";
				return;
			}
			
			if(document.getElementById("noEmailInd").value == "Y"){
				
			}else{
				if(document.getElementById("hktClubLoginName").value != ""){
					if (document.getElementById("hktClubLoginName").value.toLowerCase().indexOf("@theclub.com.hk") > 0) {
						document.getElementById('hktClubLoginName_error').style.display = "";
						document.getElementById('hktClubLoginName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.005"/>";
						return;
					}
				}
			}
			
			if($("#csPortalTheClubOptOutInd").val() == "Y"){
				var reason = document.getElementById('clubOptoutSelect').value;
				if (reason == ""){
					alert( "<spring:message code="ims.pcd.imsinstallation.msg.006"/>");
					return;
				}
				var rmk = document.getElementById('optoutTheClubRmk').value;
				if(document.getElementById('clubOptoutSelect').value=="51"){
					if (rmk == ""){
						alert( "<spring:message code="ims.pcd.imsinstallation.msg.007"/>");
						return;
					}
				}
			}else{
				document.getElementById('clubOptoutSelect').value = "";
				document.getElementById('optoutTheClubRmk').value = "";
			}
			
			if(document.getElementById("hktMobileNum_warning").style.display == "" ){	
				document.getElementById('hktMobileNum_warning').style.display = "";
				document.getElementById('hktMobileNum_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.008"/>";
				return;
			}else if (document.getElementById("hktMobileNum").value == "" ){
				document.getElementById('hktMobileNum_error').style.display = "";
				document.getElementById('hktMobileNum_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.009"/>";
				return;
			}
		}else if(document.getElementById('isRegHKTLoginId').value == "Y"){
			
			if(document.getElementById("hktLoginName_warning").style.display == ""){
				document.getElementById('hktLoginName_warning').style.display = "";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.010"/>";
				return;
			}
		
			if(document.getElementById("hktLoginName").value == ""){
				document.getElementById('hktLoginName_error').style.display = "";
				document.getElementById('hktLoginName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.011"/>";
				return;
			}
			
			if(document.getElementById("hktMobileNum_warning").style.display == "" ){	
				document.getElementById('hktMobileNum_warning').style.display = "";
				document.getElementById('hktMobileNum_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.008"/>";
				return;
			}else if (document.getElementById("hktMobileNum").value == "" ){
				document.getElementById('hktMobileNum_error').style.display = "";
				document.getElementById('hktMobileNum_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.012"/>";
				return;
			}
			
		//}else if(document.getElementById('isRegHKTLoginId').value == "A"){			
		}else if(document.getElementById('isRegClubLoginId').value == "Y"){
			
			if(document.getElementById("hktLoginName_warning").style.display == ""){
				document.getElementById('hktLoginName_warning').style.display = "";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.013"/>";
				return;
			}
		
			if(document.getElementById("clubLoginName").value == ""){
				document.getElementById('clubLoginName_error').style.display = "";
				document.getElementById('clubLoginName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.014"/>";
				return;
			}
			
			if(document.getElementById("noEmailInd").value == "Y"){
				
			}else{
				if(document.getElementById("clubLoginName").value != ""){
					if (document.getElementById("clubLoginName").value.toLowerCase().indexOf("@theclub.com.hk") > 0) {
						document.getElementById('clubLoginName_error').style.display = "";
						document.getElementById('clubLoginName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.005"/>";
						return;
					}
				}
			}
			
			if($("#theClubOptOutInd").val() == "Y"){
				var reason = document.getElementById('clubOptoutSelect').value;
				if (reason == ""){
					alert("<spring:message code="ims.pcd.imsinstallation.msg.006"/>");
					return;
				}
				var rmk = document.getElementById('optoutTheClubRmk').value;
				if(document.getElementById('clubOptoutSelect').value=="51"){
					if (rmk == ""){
						alert("<spring:message code="ims.pcd.imsinstallation.msg.007"/>");
						return;
					}
				}
			}else{
				document.getElementById('clubOptoutSelect').value = "";
				document.getElementById('optoutTheClubRmk').value = "";
			}
			
			if(document.getElementById("hktMobileNum_warning").style.display == "" ){	
				document.getElementById('hktMobileNum_warning').style.display = "";
				document.getElementById('hktMobileNum_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.008"/>";
				return;
			}else if (document.getElementById("hktMobileNum").value == "" ){
				document.getElementById('hktMobileNum_error').style.display = "";
				document.getElementById('hktMobileNum_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.012"/>";
				return;
			}
		}
		//Gary end
		
		if(document.getElementById('isRegNowId').value == "Y"){
			
			if(document.getElementById("nowIdName").value == ""){
				document.getElementById('nowIdName_error').style.display = "";
				document.getElementById('nowIdName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.016"/>";
				return;
			}
			
			if(document.getElementById("nowIdName_error").style.display == ""){
				document.getElementById('nowIdName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.017"/>";
				return;
			}
		}
		
		if(document.getElementById("warning_login").style.visibility == "visible"){
			
		}else if(document.getElementById("warning_hkid").style.display == ""){
			
		}else if(document.getElementById("warning_mobile").style.display == ""){
		
		}else if(document.getElementById("warning_fixline").style.display == ""){
		
		}else if(document.getElementById("warning_email").style.display == ""){
			
		//}else if ( checkMobileOrFixed () ){
			

		}else if(document.getElementById("warning_pass").style.display == ""){
			
		
		}else if(document.getElementById("error_clubmemberid").style.visibility == "visible"){
			
		
		}else{
			
			if( $("#docType").val() != "HKID" && "${ImsOrder.tngRebateInd}"=="Y"){
				alert('Tap & Go Rebate VAS is for HKID only. Please remove Tap & Go Rebate VAS in VAS page.');
				return;
			}
		
			if ($("#multiFSAInd").val()=="Y"&&"${ImsOrder.tngRebateInd}"=="Y"){
			alert('Share FSA case. Please remove Tap & Go Rebate VAS in VAS page.');
			return;
		}
			
		submitShowLoading();
		document.getElementById("docType").disabled = false;
		document.getElementById("docNum").disabled = false;
		document.getElementById("txt_title").disabled = false;
		document.getElementById("custLastName").disabled = false;
		document.getElementById("custFirstName").disabled = false;
		document.getElementById("companyName").disabled = false;
		document.getElementById("DOB").disabled = false;
		document.getElementById('clubLoginName').disabled = false;
		document.getElementById('hktClubLoginName').disabled = false;
		document.getElementById('hktMobileNum').disabled = false;
		
		if($("#orderActionInd").val() == "W" && $("#approvalRequested").val() == "Y"){ //($("#orderStatus").val() == "31" || $("#orderStatus").val() == "32")){
			$(":input").attr("disabled", false);
		}
		
		if(document.getElementById('noBillingAddressFlag').checked == false){
			$("#noBillingAddress").val("N");
			checkingBillingAddress();
		}else{
			$("#noBillingAddress").val("Y");
		}
		
		if(document.getElementById("idVerified").checked == true){
			$("#idVerified").val("Y");
		}else{
			$("#idVerified").val("N");
		}
		
		$("#loginId").val($("#loginName").val());
		
		if(document.getElementById('noBillingAddressFlag').checked == false
				&& (document.getElementById('billingQuickSearch').value == ""
						|| document.getElementById('billingQuickSearch').value == null)){
			if($("#billingSectionCodeSelect option:selected").text() != ""){
				$("[id=billingAddress.sectCd]").val($("#billingSectionCodeSelect").val());
				$("[id=billingAddress.sectDesc]").val($("#billingSectionCodeSelect option:selected").text());
			}
			if($("#billingDistrictCodeSelect option:selected").text() != ""){
				$("[id=billingAddress.distNo]").val($("#billingDistrictCodeSelect").val());
				$("[id=billingAddress.distDesc]").val($("#billingDistrictCodeSelect option:selected").text());
			}
			if($("#billingAreaCodeSelect option:selected").text() != ""){
				$("[id=billingAddress.areaCd]").val($("#billingAreaCodeSelect").val());
				$("[id=billingAddress.areaDesc]").val($("#billingAreaCodeSelect option:selected").text());
			}
		}
		if($("#lob").val() != "IMS"|| ($("#custNo").val() == null || $("#custNo").val() == "") ||jsphasBBDialUp==false){
			count_tick =0;
			if(document.getElementById('cb_directMailing').checked == true){count_tick++;};
			if(document.getElementById('cb_outbound').checked == true){count_tick++;};
			if(document.getElementById('cb_sms').checked == true){count_tick++;};
			if(document.getElementById('cb_email').checked == true){count_tick++;};
			if(document.getElementById('cb_webBill').checked == true){count_tick++;};
			if(document.getElementById('cb_nonSalesSms').checked == true){count_tick++;};
			if(document.getElementById('cb_onlinePortal').checked == true){count_tick++;};
			if(count_tick==1 || count_tick==7){
				allTick();
			}
			else{
				allUnTick();
			}
		}
		
		if ($("#createCOrderInd").val()=="Y"){
			alert('Share with FSA '+$("#relatedFSA").val());
		}
		document.imsinstallationForm.submit();
		}
	}
	
		
		function checkOptOut(){
			<c:choose>
				<c:when test='${imsInstallationUI.isCC == "Y" || ims_direct_sales eq true}'>
					if (  document.getElementById('AllOpt').checked )
					{
						allTick();
					}
					else
					{
						allUnTick();
					}
				</c:when>
				<c:otherwise>
					if($("#lob").val() != "IMS"|| ($("#custNo").val() == null || $("#custNo").val() == "") ||jsphasBBDialUp==false)
					{
						count_tick =0;
						if(document.getElementById('cb_directMailing').checked == true){count_tick++;};
						if(document.getElementById('cb_outbound').checked == true){count_tick++;};
						if(document.getElementById('cb_sms').checked == true){count_tick++;};
						if(document.getElementById('cb_email').checked == true){count_tick++;};
						if(document.getElementById('cb_webBill').checked == true){count_tick++;};
						if(document.getElementById('cb_nonSalesSms').checked == true){count_tick++;};
						if(document.getElementById('cb_onlinePortal').checked == true){count_tick++;};
						if(count_tick==1 || count_tick==7){
							allTick();
						}
						else{
							allUnTick();
						}
					}
					</c:otherwise>
			</c:choose>
		}
		
		function allTick(){
			document.getElementById('cb_directMailing').checked = true;
			document.getElementById('cb_outbound').checked = true;
			document.getElementById('cb_sms').checked = true;
			document.getElementById('cb_email').checked = true;
			document.getElementById('cb_webBill').checked = true;
			document.getElementById('cb_nonSalesSms').checked = true;
			document.getElementById('cb_onlinePortal').checked = true;
		
			$("#cb_directMailing").val("Y");
			$("[id=custOptInfo.directMailing]").val($("#cb_directMailing").val());
			$("#cb_onlinePortal").val("Y");
			$("[id=custOptInfo.internet]").val($("#cb_onlinePortal").val());
			$("#cb_nonSalesSms").val("Y");
			$("[id=custOptInfo.nonsalesSms]").val($("#cb_nonSalesSms").val());
			$("#cb_webBill").val("Y");
			$("[id=custOptInfo.webBill]").val($("#cb_webBill").val());
			$("#cb_email").val("Y");
			$("[id=custOptInfo.email]").val($("#cb_email").val());
			$("#cb_sms").val("Y");
			$("[id=custOptInfo.sms]").val($("#cb_sms").val());
			$("#cb_outbound").val("Y");
			$("[id=custOptInfo.outbound]").val($("#cb_outbound").val());
		}
	
	function allUnTick(){
		document.getElementById('cb_directMailing').checked = false;
		document.getElementById('cb_outbound').checked = false;
		document.getElementById('cb_sms').checked = false;
		document.getElementById('cb_email').checked = false;
		document.getElementById('cb_webBill').checked = false;
		document.getElementById('cb_nonSalesSms').checked = false;
		document.getElementById('cb_onlinePortal').checked = false;
		$("[id=custOptInfo.internet]").val("N");
		$("[id=custOptInfo.nonsalesSms]").val("N");
		$("[id=custOptInfo.webBill]").val("N");
		$("[id=custOptInfo.outbound]").val("N");
		$("[id=custOptInfo.directMailing]").val("N");
		$("[id=custOptInfo.sms]").val("N");
		$("[id=custOptInfo.email]").val("N");
	}
	
	function disableAdd(){
		document.getElementById('billingLotNum').readOnly = true;
		document.getElementById('billingBuildingName').readOnly = true;			
		document.getElementById('billingStreetNum').readOnly = true;
		document.getElementById('billingStreetName').readOnly = true;
    	document.getElementById('billingStreetCategory').readOnly = true;
    	document.getElementById('billingSectionCodeSelect').readOnly = true;
    	document.getElementById('billingDistrictCodeSelect').readOnly = true;
    	document.getElementById('billingAreaCodeSelect').readOnly = true;
	}
	
	function enableAdd(){
		document.getElementById('billingLotNum').readOnly = false;
		document.getElementById('billingBuildingName').readOnly = false;			
		document.getElementById('billingStreetNum').readOnly = false;
		document.getElementById('billingStreetName').readOnly = false;
    	document.getElementById('billingStreetCategory').readOnly = false;
    	document.getElementById('billingSectionCodeSelect').readOnly = false;
    	document.getElementById('billingDistrictCodeSelect').readOnly = false;
    	document.getElementById('billingAreaCodeSelect').readOnly = false;
	}
	
	function sameAddrCheck(){
		if(document.getElementById('noBillingAddressFlag').checked == false){
			document.getElementById('quickSearch').style.display = "";
			document.getElementById('quickSearchInput').style.display = "";
			document.getElementById('quickSearchClear').style.display = "";
			document.getElementById('BillingDiv').style.display = "";
			if(document.getElementById('quickSearchFlag').checked == false){
				/*
				document.getElementById('AddSearchRow').innerHTML = 
					"<td>" +
		    		"<a href='#' class='nextbutton' onclick='setCurrentSearchFrom('billingQuickSearch')'>Search</a>" +
		    		"</td>";
		    	*/
		    	allClear();
				enableAdd();
				document.imsinstallationForm.billingQuickSearch.readOnly = true;
				document.getElementById('billingFlat').readOnly = false;
				document.getElementById('billingFloor').readOnly = false;
			}
			else if ( document.getElementById('quickSearchFlag').checked == true )
			{    	
		    	allClear();
				disableAdd();
				document.imsinstallationForm.billingQuickSearch.readOnly = false;
				document.getElementById('billingFlat').readOnly = false;
				document.getElementById('billingFloor').readOnly = false;
				//document.getElementById('AddSearchRow').innerHTML = "<div></div>";
			}
		}else{
			document.getElementById('quickSearch').style.display = "none";
			document.getElementById('quickSearchInput').style.display = "none";
			document.getElementById('quickSearchClear').style.display = "none";
			document.getElementById('BillingDiv').style.display = "none";
	    	allClear();
	    	disableAdd();
	    	document.imsinstallationForm.billingQuickSearch.readOnly = true;
			document.getElementById('billingFlat').readOnly = true;
			document.getElementById('billingFloor').readOnly = true;
			//document.getElementById('AddSearchRow').innerHTML = "<div></div>";
		}
	}
	
	//Tony added
	function checkCreateCOrder(){

		$.ajax({
			url : 'imscheckcorder.html',
			//async: false, by jacky 20141215
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert('error: '+textStatus); 	
			
				if(textStatus=='parsererror'){
			        alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
			    }else if(textStatus == 'timeout'){
			    	alert("<spring:message code="ims.pcd.imsinstallation.msg.029"/>");
			    }
				document.getElementById('warning_login').style.visibility = "hidden";
				document.getElementById('error_login').style.visibility = "hidden";
			},	
			success : function(msg) {
				
				$.each(eval(msg), function(i, item) {
				//	alert(item.relatedFSA);
					if(item.createCOrderInd == "Y"){
				//	alert('Share with FSA '+item.relatedFSA);	
						$("#createCOrderInd").val("Y");
						$("#relatedFSA").val(item.relatedFSA);
						
					
					}else{
						//alert('no c order');
						$("#createCOrderInd").val("N");
						$("#relatedFSA").val("");
					}
					$("#multiFSAInd").val(item.multiFSAInd);
				});
			}
		});
	}
	
	//Gary Part
	function csPortalCheck(reqType){
		$.ajax({
			url : 'imscsportal.html?loginName=' + $("#loginName").val().replace("&", "*") + '&docType=' + $("#docType").val() + '&idDocNum=' + $("#docNum").val() + '&custLastName=' + $("#custLastName").val() + '&custFirstName=' + $("#custFirstName").val() +'&reqType=' + reqType,
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus=='parsererror'){
			        alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
			    }else if(textStatus == 'timeout'){
			    	alert("<spring:message code="ims.pcd.imsinstallation.msg.029"/>");
			    }
				document.getElementById('warning_login').style.visibility = "hidden";
				document.getElementById('error_login').style.visibility = "hidden";
			},	
			success : function(msg) {
				$.each(eval(msg), function(i, item) {
					
// 					careEnable = item.oCareEnable;
					careVisit  = item.oCareVisit;
					careBip    = item.oCareBip;
					
					if(item.isClubMemberIDValid == false){
						document.getElementById('error_clubmemberid').style.visibility = "visible";
						document.getElementById('error_clubmemberid_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.070"/>";	
					}else{
						document.getElementById('error_clubmemberid').style.visibility = "hidden";
						document.getElementById('error_clubmemberid_msg').innerHTML = "";	
					}
					
					if( document.getElementById('docType').value != "BS" && !(item.isCsPortalReg == null || item.isCsPortalReg == "null")
						&& !(item.isTheClubReg == null || item.isTheClubReg == "null"))
					{	
						
						if(item.isCsPortalReg == "true" && item.isTheClubReg == "true"){
							document.getElementById('isRegHKTClubLoginId').value = "A";								
							document.getElementById('isRegHKTLoginId').value = "";						
							document.getElementById('isRegClubLoginId').value = "";
							document.getElementById('registeredMyHktLoginId').value = item.retrieveLoginId;
							document.getElementById('registeredTheClubLoginId').value = item.retrieveClubLoginId;
						}else if(item.isCsPortalReg == "true" && item.isTheClubReg == "false"){								
							document.getElementById('isRegHKTLoginId').value = "A";
							document.getElementById('registeredMyHktLoginId').value = item.retrieveLoginId;
						}else if(item.isTheClubReg == "true" && item.isCsPortalReg == "false" ){						
							document.getElementById('isRegClubLoginId').value = "A";
							document.getElementById('registeredTheClubLoginId').value = item.retrieveClubLoginId;
						}
						
						//alert("zzz "+document.getElementById('isRegHKTLoginId').value);
							
						if (document.getElementById('isRegHKTClubLoginId').value=="A"|| document.getElementById('isRegHKTClubLoginId').value=="a"){
							//alert("already reg myhkt and club");
							
							document.getElementById('myHktReg').style.display = "";
							document.getElementById('myHktReg4').style.display = "";
							document.getElementById('myHktReg5').style.display = "";
							document.getElementById('registeredMyHktLoginId').disabled=true;
							document.getElementById('registeredTheClubLoginId').disabled=true;
							
							if (document.getElementById('registeredMyHktLoginId').value == "null" || document.getElementById('registeredMyHktLoginId').value == ""){
								document.getElementById('registeredMyHktLoginId').value = "";
								document.getElementById('registeredMyHktLoginId_err').style.display="";
								document.getElementById('registeredMyHktLoginId_errMsg').innerHTML="<spring:message code="ims.pcd.imsinstallation.msg.018"/>";
							}
							
							if (document.getElementById('registeredTheClubLoginId').value == "null" || document.getElementById('registeredTheClubLoginId').value == ""){
								document.getElementById('registeredTheClubLoginId').value = "";
								document.getElementById('registeredTheClubLoginId_err').style.display="";
								document.getElementById('registeredTheClubLoginId_errMsg').innerHTML="<spring:message code="ims.pcd.imsinstallation.msg.019"/>";
							}
							
							if(item.phantomInd == "Y" ){	
								document.getElementById('phantomAcc_warning').style.display = "";
								document.getElementById('phantomAcc_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.020"/>";
							}else{
								document.getElementById('phantomAcc_warning').style.display = "none";
								document.getElementById('phantomAcc_warning_msg').innerHTML = "";
							}

							document.getElementById('myHktReg1').style.display = "none";	
							document.getElementById('myHktReg3').style.display = "none";
							document.getElementById('myHktReg6').style.display = "none";	
							document.getElementById('myHktReg7').style.display = "none";	
							document.getElementById('myHktReg2').style.display = "none";
							document.getElementById('myHktReg8').style.display = "none";
							document.getElementById('myHktReg9').style.display = "none";
							document.getElementById('myHktReg10').style.display = "none";
							document.getElementById('myHktReg11').style.display = "none";
							document.getElementById('myHktReg12').style.display = "none";
							document.getElementById('myHktReg13').style.display = "none";
							document.getElementById('hktClubLoginName_error').style.display = "none";
							document.getElementById('clubLoginName_error').style.display = "none";
							document.getElementById('hktLoginName_error').style.display = "none";
										
							
						}else if(document.getElementById('isRegHKTLoginId').value=="A" || document.getElementById('isRegHKTLoginId').value=="a"){
							//alert("already reg myhkt");
							
							document.getElementById('myHktReg').style.display = "";////alert("6");
	
							document.getElementById('myHktReg6').style.display = "";	
							document.getElementById('myHktReg8').style.display = "";	
							document.getElementById('myHktReg3').style.display = "";
							document.getElementById('myHktReg4').style.display = "";
							document.getElementById('myHktReg11').style.display = "";
							document.getElementById('myHktReg12').style.display = "";
							if(document.getElementById('isRegClubLoginId').value != "N"){ 
 								document.getElementById('isRegClubLoginId').value = "Y";
							}
							document.getElementById('isRegHKTClubLoginId').value = "";

							if(document.getElementById('noEmailInd').value=="Y"){
								document.getElementById('copyNetEmail1').style.display = "none";
								document.getElementById('noEmail1').style.display = "none";
								document.getElementById('noEmailClear1').style.display = "";
								document.getElementById('clubLoginName').disabled = true;
							}else{
								document.getElementById('copyNetEmail1').style.display = "";
								document.getElementById('noEmail1').style.display = "none";
								document.getElementById('noEmailClear1').style.display = "none";
								document.getElementById('clubLoginName').disabled = false;
							}

							if(document.getElementById('noMobileInd').value=="Y"){
								sysGenMobile();
							}else{
								clearSysGenMobile();
							}

							if(reqType == "nlogin"){
								if (item.nloginResult != "NA"){
									document.getElementById('copyNetEmail1').style.display = "";
									document.getElementById('noEmail1').style.display = "none";
									document.getElementById('noEmailClear1').style.display = "none";
									document.getElementById('hktClubLoginName').disabled = false;
								}else{
									document.getElementById('copyNetEmail1').style.display = "none";
									document.getElementById('noEmail1').style.display = "";
									document.getElementById('noEmailClear1').style.display = "none";
									document.getElementById('hktClubLoginName').disabled = false;
								}
							}
							
							if(reqType == "sysgen"){
								document.getElementById('clubLoginName').value = item.sysGenEmail; 
								document.getElementById('clubLoginName').disabled = true; 
								document.getElementById('copyNetEmail1').style.display = "none";
								document.getElementById('noEmail1').style.display = "none";
								document.getElementById('noEmailInd').value = "Y"; 
								document.getElementById('noEmailClear1').style.display = "";
								document.getElementById('hktLoginName_warning').style.display = "none";
							}
							
							if(item.phantomInd == "Y" ){	
								document.getElementById('phantomAcc_warning').style.display = "";
								document.getElementById('phantomAcc_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.020"/>";
							}else{
								document.getElementById('phantomAcc_warning').style.display = "none";
								document.getElementById('phantomAcc_warning_msg').innerHTML = "";
							}
							
							document.getElementById('myHktReg1').style.display = "none";	
							document.getElementById('myHktReg2').style.display = "none";	
							document.getElementById('myHktReg7').style.display = "none";
							document.getElementById('myHktReg9').style.display = "none";
							document.getElementById('myHktReg5').style.display = "none";
							document.getElementById('myHktReg10').style.display = "none";
							document.getElementById('myHktReg13').style.display = "none";
							document.getElementById('hktClubLoginName_error').style.display = "none";
							document.getElementById('hktLoginName_error').style.display = "none";
							document.getElementById('registeredMyHktLoginId').disabled=true;
							document.getElementById('isRegHKTLoginId').value = "A";
							document.getElementById("hktLoginName").value = "";
							
							if (document.getElementById('registeredMyHktLoginId').value == "null" || document.getElementById('registeredMyHktLoginId').value == ""){
								document.getElementById('registeredMyHktLoginId').value = "";
								document.getElementById('registeredMyHktLoginId_err').style.display="";
								document.getElementById('registeredMyHktLoginId_errMsg').innerHTML="<spring:message code="ims.pcd.imsinstallation.msg.018"/>";
							}
							
							if(clubCutOff == "N"){
								if(document.getElementById('isRegClubLoginId').value == "Y" || document.getElementById('isRegClubLoginId').value == "y"){
									document.getElementById('isRegClubLoginIdBox').checked = true;
								}
								if(document.getElementById('isRegClubLoginId').value == "N" || document.getElementById('isRegClubLoginId').value == "n"){
					 				document.getElementById('isRegClubLoginId').value ="N";
									document.getElementById('isRegClubLoginIdBox').checked = false;
									controlMyHKTRegistration();
								}
							}
							
							controlClubOptout();
							
						}else if(document.getElementById('isRegClubLoginId').value=="A" || document.getElementById('isRegClubLoginId').value=="a"){
							//alert("already reg club");

							document.getElementById('myHktReg').style.display = "";
							
							document.getElementById('myHktReg1').style.display = "";	
							document.getElementById('myHktReg2').style.display = "";	
							document.getElementById('myHktReg3').style.display = "";
							document.getElementById('myHktReg5').style.display = "";
							document.getElementById('myHktReg10').style.display = "";
							if (document.getElementById('isRegHKTLoginId').value != 'N') {
								document.getElementById('isRegHKTLoginIdBox').checked = true;
								document.getElementById('isRegHKTLoginId').value = "Y";
								document.getElementById('hktLoginName').disabled = false;
								document.getElementById('hktMobileNum').disabled = false;
								document.getElementById('optoutbN').disabled = false;
								document.getElementById('optoutbY').disabled = false;
							}else{
								document.getElementById('isRegHKTLoginIdBox').checked = false;
								document.getElementById('hktLoginName').disabled = true;
								document.getElementById('hktMobileNum').disabled = true;
								document.getElementById('optoutbN').disabled = true;
								document.getElementById('optoutbY').disabled = true;
							}
							document.getElementById('isRegHKTClubLoginId').value = "";
							
							document.getElementById('noMobile').style.display = "none";	
							document.getElementById('noMobileClear').style.display = "none";

							
							if(item.phantomInd == "Y" ){	
								document.getElementById('phantomAcc_warning').style.display = "";
								document.getElementById('phantomAcc_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.020"/>";
							}else{
								document.getElementById('phantomAcc_warning').style.display = "none";
								document.getElementById('phantomAcc_warning_msg').innerHTML = "";
							}
							
							document.getElementById('myHktReg4').style.display = "none";
							document.getElementById('myHktReg6').style.display = "none";	
							document.getElementById('myHktReg7').style.display = "none";	
							document.getElementById('myHktReg8').style.display = "none";	
							document.getElementById('myHktReg9').style.display = "none";
							document.getElementById('myHktReg11').style.display = "none";
							document.getElementById('myHktReg12').style.display = "none";
							document.getElementById('myHktReg13').style.display = "none";
							document.getElementById('hktClubLoginName_error').style.display = "none";
							document.getElementById('clubLoginName_error').style.display = "none";
							document.getElementById('registeredTheClubLoginId').disabled=true;
							document.getElementById('isRegClubLoginId').value = "A";
							document.getElementById("clubLoginName").value = "";
							
							if (document.getElementById('registeredTheClubLoginId').value == "null" || document.getElementById('registeredTheClubLoginId').value == ""){
								document.getElementById('registeredTheClubLoginId').value = "";
								document.getElementById('registeredTheClubLoginId_err').style.display="";
								document.getElementById('registeredTheClubLoginId_errMsg').innerHTML="<spring:message code="ims.pcd.imsinstallation.msg.019"/>";
							}	
							
						}else{
							//alert("not reg myhkt and club");
							document.getElementById('myHktReg').style.display = "";////alert("7");
							document.getElementById('myHktReg7').style.display = "";	
							document.getElementById('myHktReg9').style.display = "";	
							document.getElementById('myHktReg3').style.display = "";
							document.getElementById('myHktReg4').style.display = "none";
							document.getElementById('myHktReg5').style.display = "none";
							document.getElementById('myHktReg10').style.display = "none";
							document.getElementById('myHktReg11').style.display = "none";
							document.getElementById('myHktReg12').style.display = "";
							document.getElementById('myHktReg13').style.display = "";
							
							document.getElementById('registeredMyHktLoginId_err').style.display = "none";
							document.getElementById('registeredTheClubLoginId_err').style.display = "none";
							if(document.getElementById('isRegHKTClubLoginId').value != "N"){ 
	 							document.getElementById('isRegHKTLoginId').value = "Y";
	 							document.getElementById('isRegClubLoginId').value = "Y";
								document.getElementById('isRegHKTClubLoginId').value = "Y";
							}
							document.getElementById('hktClubLoginName').disabled = false;
							document.getElementById('hktLoginName').disabled = false;
							document.getElementById('clubLoginName').disabled = false;
							document.getElementById('hktMobileNum').disabled = false;

							if(document.getElementById('noEmailInd').value=="Y"){
								document.getElementById('copyNetEmail2').style.display = "none";
								document.getElementById('noEmail2').style.display = "none";
								document.getElementById('noEmailClear2').style.display = "";
								document.getElementById('hktClubLoginName').disabled = true;
							}else{
								document.getElementById('copyNetEmail2').style.display = "";
								document.getElementById('noEmail2').style.display = "none";
								document.getElementById('noEmailClear2').style.display = "none";
								document.getElementById('hktClubLoginName').disabled = false;
							}

							
							if(item.phantomInd == "Y" ){	
								document.getElementById('phantomAcc_warning').style.display = "";
								document.getElementById('phantomAcc_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.020"/>";
							}else{
								document.getElementById('phantomAcc_warning').style.display = "none";
								document.getElementById('phantomAcc_warning_msg').innerHTML = "";
							}

							if(document.getElementById('noMobileInd').value=="Y"){
								sysGenMobile();
							}else{
								clearSysGenMobile();
							}
							
							if(reqType == "nlogin"){
								if (item.nloginResult != "NA"){
									document.getElementById('copyNetEmail2').style.display = "";
									document.getElementById('noEmail2').style.display = "none";
									document.getElementById('noEmailClear2').style.display = "none";
									document.getElementById('hktClubLoginName').disabled = false;
								}else{
									document.getElementById('copyNetEmail2').style.display = "none";
									document.getElementById('noEmail2').style.display = "";
									document.getElementById('noEmailClear2').style.display = "none";
									document.getElementById('hktClubLoginName').disabled = false;
								}
							}
							
							if(reqType == "sysgen"){
								document.getElementById('hktClubLoginName').value = item.sysGenEmail; 
								document.getElementById('hktClubLoginName').disabled = true;
								document.getElementById('copyNetEmail2').style.display = "none";
								document.getElementById('noEmail2').style.display = "none";
								document.getElementById('noEmailInd').value = "Y"; 
								document.getElementById('noEmailClear2').style.display = "";
								document.getElementById('hktLoginName_warning').style.display = "none";
							}
							
							if(clubCutOff == "N"){
								if(document.getElementById('isRegHKTClubLoginId').value == "N" || document.getElementById('isRegHKTClubLoginId').value == "n"){
					 				document.getElementById('isRegClubLoginId').value ="N";
					 				document.getElementById('isRegHKTLoginId').value ="N";
									document.getElementById('isRegClubHKTLoginIdBox').checked = false;
									controlMyHKTRegistration();
								}else{
									document.getElementById('isRegClubHKTLoginIdBox').checked = true;
					 				document.getElementById('isRegClubLoginId').value ="Y";
					 				document.getElementById('isRegHKTLoginId').value ="Y";
					 				document.getElementById('isRegHKTClubLoginId').value ="Y";
								}
							}
							
							controlClubOptout();
						} 
						
						//document.getElementById('hktLoginName').value= document.getElementById("reservedId").value + "@netvigator.com";
						//document.getElementById('hktMobileNum').value= document.getElementById('mobileNum').value;
						
						if(document.getElementById('hktMobileNum').value==""){
						document.getElementById('hktMobileNum').value= document.getElementById('mobileNum').value.substring(0,8);
						validHktMobileNo();
						document.getElementById('hktMobileNum_error').style.display = "none";
						hktMobileNumEdited=false;
						}
						
						
					}else{//alert("here comes");
						//Gary control existence of CS portal in the page
						hideMyHKTTheClubBlock();
						document.getElementById('registeredMyHktLoginId_err').style.display = "none";
						document.getElementById('registeredTheClubLoginId_errMsg').style.display = "none";
						document.getElementById('isRegHKTLoginId').value = "X";
						document.getElementById('isRegClubLoginId').value = "X";
						document.getElementById('isRegHKTClubLoginId').value = "X";
						document.getElementById('theClubOptOutInd').value = "";
						document.getElementById('csPortalOptOutInd').value = "";
						document.getElementById('csPortalTheClubOptOutInd').value = "";
						document.getElementById("hktLoginName").value = "";
						document.getElementById("clubLoginName").value = "";
						document.getElementById("hktMobileNum").value = "";							
					}
			
			});
				
	}
		});
	}
	
	//Terrence Part
	function loginNameCheck()
	{		
		$("#loginName").val($("#loginName").val().toLowerCase());

		if(document.getElementById("loginName").disabled==true){			
			return;
		}
		$.ajax({
			url : 'checkloginname.html?loginName=' + $("#loginName").val().replace("&", "*") + '&docType=' + $("#docType").val() + '&idDocNum=' + $("#docNum").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus=='parsererror'){
			        alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
			    }else if(textStatus == 'timeout'){
			    	alert("<spring:message code="ims.pcd.imsinstallation.msg.029"/>");
			    }
				document.getElementById('warning_login').style.visibility = "hidden";
				document.getElementById('error_login').style.visibility = "hidden";
			},			
			success : function(msg) {
				var count=0;
				document.getElementById("loginOK").style.display = "none";
				$.each(eval(msg), function(i, item) {
					//modified by Gary 8 Aug,13
				
					if(item.valid == true){
						if(item.reserveResult == "-7"){
							document.getElementById('error_login').style.visibility = "hidden";						
							document.getElementById('warning_login').style.visibility = "visible";
							document.getElementById('warning_login_msg').innerHTML = item.errorText;
							//Gary control existence of CS portal in the page
							hideMyHKTTheClubBlock();
							isReserved=false;
							//Gary end
							hideCARECashBlock();						
						}else if(item.isExist == true && item.loginName != item.oldLoginName && (item.loginName != null && item.loginName != "")){
							document.getElementById('warning_login').style.visibility = "visible";
							document.getElementById('warning_login_msg').innerHTML = "Please try " + item.hintOne + ", " + item.hintSecond + ", " + item.hintThird + "";
							document.getElementById('error_login').style.visibility = "visible";
							document.getElementById('error_login_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.021"/>";							
							//Gary control existence of CS portal in the page
							hideMyHKTTheClubBlock();
							//Gary end
							
							isReserved=false;
							//window.tempEl = document.getElementById('loginName');
					        //setTimeout("window.tempEl.focus();",1);
					        hideCARECashBlock();
						}else if(item.isExist == false){
							//reserve ok
							isReserved=true;
// 							careEnable = item.oCareEnable;
							careVisit  = item.oCareVisit;
							careBip  = item.oCareBip;
							
// 						if(careEnable!=null && careEnable != "N"){
						if(careCashShowInd !=null && careCashShowInd =="Y"){
							if( $("#docType").val() == "HKID" &&  isCARECashValidAge($("#DOB").val()) ){
								$("#careCashInd").val(item.oCareApplyInd);								
								$("#careCashOptOutInd").val(item.oCareOptOutInd);
								if(item.oCareApplyInd != null && item.oCareApplyInd != "A"&&$("#careCashInd").val()!= "O" &&isCareCashEnable(careVisit,careBip)){ //- customer not already enrolled									
										showCARECashBlock();	
										careCashCheckBox();
									
								}//- only applicable for pending
								else
									hideCARECashBlock();	
								
							}	
							else{
								$("#careCashInd").val("X"); //- customer not applicable to enroll	
								$("#careCashOptOutInd").val("");
								hideCARECashBlock();	
							}
						}
// 						}
// 						else{
// 							hideCARECashBlock();	
// 							$("#careCashInd").val(""); //- customer not applicable to enroll	
// 							$("#careCashOptOutInd").val("");
// 						}
						
							document.getElementById("reservedId").value = item.loginName;
							document.getElementById("loginOK").style.display = "";							
							document.getElementById('warning_login').style.visibility = "hidden";
							document.getElementById('error_login').style.visibility = "hidden";
							////alert(item.isCsPortalReg);
							//Gary control existence of CS portal in the page
							if(item.isClubMemberIDValid == "false"){
								document.getElementById('error_clubmemberid').style.visibility = "visible";
								document.getElementById('error_clubmemberid_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.070"/>";	
							}else{
								document.getElementById('error_clubmemberid').style.visibility = "hidden";
								document.getElementById('error_clubmemberid_msg').innerHTML = "";	
							}
							
							
							if(document.getElementById('isRegHKTLoginId').value != "N"){
								document.getElementById('isRegHKTLoginId').value="Y";
							}
							
							if(document.getElementById('isRegClubLoginId').value != "N"){
								document.getElementById('isRegClubLoginId').value="Y";
							}
							
// 							if (firstReserve){
// 								document.getElementById('isRegHKTLoginId').value="Y";
// 								document.getElementById('isRegClubLoginId').value="Y";
// 								firstReserve = false;
// 							}else{
// 								isValid = true;
// 							}
							//alert(document.getElementById('isRegHKTLoginId').value+"     "+item.isCsPortalReg );
							////alert("ddddddd");
							if(document.getElementById('docNum').value != "" && document.getElementById('docType').value != "BS" && !(item.isCsPortalReg == null || item.isCsPortalReg == "null")
									&& !(item.isTheClubReg == null || item.isTheClubReg == "null")){
								if(item.isCsPortalReg == "true" && item.isTheClubReg == "false"){
									//alert('already reg myhkt');
									document.getElementById('isRegHKTLoginId').value = "A";
									document.getElementById('isRegHKTClubLoginId').value = "N";	
									document.getElementById('registeredMyHktLoginId').value = item.retrieveLoginId;
								}
								if(item.isTheClubReg == "true" && item.isCsPortalReg == "false" ){
									//alert('already reg club');								
									document.getElementById('isRegClubLoginId').value = "A";
									document.getElementById('isRegHKTClubLoginId').value = "N";	
									document.getElementById('registeredTheClubLoginId').value = item.retrieveClubLoginId;
								}
								
								if(item.isTheClubReg == "true" && item.isCsPortalReg == "true" ){
									//alert('already reg hkt and club');																	
									document.getElementById('isRegHKTClubLoginId').value = "A";							
									document.getElementById('isRegHKTLoginId').value = "A";						
									document.getElementById('isRegClubLoginId').value = "A";
									document.getElementById('registeredMyHktLoginId').value = item.retrieveLoginId;
									document.getElementById('registeredTheClubLoginId').value = item.retrieveClubLoginId;
								}
								
								if (document.getElementById('isRegHKTClubLoginId').value=="A"|| document.getElementById('isRegHKTClubLoginId').value=="a"){
									//alert('already reg hkt and club');
									document.getElementById('myHktReg').style.display = "";
									document.getElementById('myHktReg4').style.display = "";
									document.getElementById('myHktReg5').style.display = "";
									document.getElementById('registeredMyHktLoginId').disabled=true;
									document.getElementById('registeredTheClubLoginId').disabled=true;
									
									if (document.getElementById('registeredMyHktLoginId').value == "null" || document.getElementById('registeredMyHktLoginId').value == ""){
										document.getElementById('registeredMyHktLoginId').value = "";
										document.getElementById('registeredMyHktLoginId_err').style.display="";
										document.getElementById('registeredMyHktLoginId_errMsg').innerHTML="<spring:message code="ims.pcd.imsinstallation.msg.018"/>";
									}
									
									if (document.getElementById('registeredTheClubLoginId').value == "null" || document.getElementById('registeredTheClubLoginId').value == ""){
										document.getElementById('registeredTheClubLoginId').value = "";
										document.getElementById('registeredTheClubLoginId_err').style.display="";
										document.getElementById('registeredTheClubLoginId_errMsg').innerHTML="<spring:message code="ims.pcd.imsinstallation.msg.019"/>";
									}
												
									
								}else if((document.getElementById('isRegHKTLoginId').value=="A" || document.getElementById('isRegHKTLoginId').value=="a")){
									//alert('already reg myhkt wish to reg club');
									document.getElementById('myHktReg').style.display = "";
	
									document.getElementById('myHktReg6').style.display = "";
									document.getElementById('myHktReg8').style.display = "";
									document.getElementById('myHktReg3').style.display = "";
									document.getElementById('myHktReg11').style.display = "";
									document.getElementById('myHktReg12').style.display = "";
 									document.getElementById('isRegClubLoginId').value = "Y";
									document.getElementById('clubLoginName').disabled = false;
									
									if(clubCutOff == "N"){
										if(document.getElementById('isRegClubLoginId').value == "Y" || document.getElementById('isRegClubLoginId').value == "y"){
											document.getElementById('isRegClubLoginIdBox').checked = true;
										}
										if(document.getElementById('isRegClubLoginId').value == "N" || document.getElementById('isRegClubLoginId').value == "n"){
											document.getElementById('isRegClubLoginIdBox').checked = false;
											controlMyHKTRegistration();
										}
									}

									if(document.getElementById('noMobileInd').value == "Y"){
										document.getElementById('hktMobileNum').disabled = true;
										document.getElementById('noMobile').style.display = "none";
										document.getElementById('noMobileClear').style.display = "";
									}else{
										document.getElementById('hktMobileNum').disabled = false;
										document.getElementById('noMobile').style.display = "";
										document.getElementById('noMobileClear').style.display = "none";
									}
									
									document.getElementById('myHktReg4').style.display = "";
									document.getElementById('registeredMyHktLoginId').disabled=true;
									
									if(item.phantomInd == "Y" ){	
										document.getElementById('phantomAcc_warning').style.display = "";
										document.getElementById('phantomAcc_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.020"/>";
									}else{
										document.getElementById('phantomAcc_warning').style.display = "none";
										document.getElementById('phantomAcc_warning_msg').innerHTML = "";
									}
									
									if (document.getElementById('registeredMyHktLoginId').value == "null" || document.getElementById('registeredMyHktLoginId').value == ""){
										document.getElementById('registeredMyHktLoginId').value = "";
										document.getElementById('registeredMyHktLoginId_err').style.display="";
										document.getElementById('registeredMyHktLoginId_errMsg').innerHTML="<spring:message code="ims.pcd.imsinstallation.msg.018"/>";
									}
									
									if (item.nloginResult != "NA"){
										document.getElementById('copyNetEmail1').style.display = "";
										document.getElementById('noEmail1').style.display = "none";
										document.getElementById('noEmailClear1').style.display = "none";
									}else{
										document.getElementById('copyNetEmail1').style.display = "none";
										document.getElementById('noEmail1').style.display = "";
										document.getElementById('noEmailClear1').style.display = "none";
									}
										
									document.getElementById('noEmailInd').value = "N"; 
									document.getElementById('clubLoginName').value = "";
									document.getElementById('clubLoginName').disabled = false;

									document.getElementById('noMobile').style.display = "";
									document.getElementById('noMobileClear').style.display = "none";
									
									controlClubOptout();
																		
								}else if ((document.getElementById('isRegClubLoginId').value=="A"|| document.getElementById('isRegClubLoginId').value=="a")
										&& (document.getElementById('isRegHKTLoginId').value=="Y"|| document.getElementById('isRegHKTLoginId').value=="y")){
									//alert('already reg club wish to reg myhkt');
									document.getElementById('myHktReg').style.display = "";
									
									document.getElementById('myHktReg1').style.display = "";	
									document.getElementById('myHktReg2').style.display = "";	
									document.getElementById('myHktReg3').style.display = "";
									document.getElementById('myHktReg10').style.display = "";
									document.getElementById('isRegHKTLoginIdBox').checked = true;
 									document.getElementById('isRegHKTLoginId').value = "Y";
									document.getElementById('hktLoginName').disabled = false;
									document.getElementById('hktMobileNum').disabled = false;

									document.getElementById('noMobile').style.display = "none";	
									document.getElementById('noMobileClear').style.display = "none";	
									
									
									document.getElementById('myHktReg5').style.display = "";
									document.getElementById('registeredTheClubLoginId').disabled=true;
									
									if(item.phantomInd == "Y" ){	
										document.getElementById('phantomAcc_warning').style.display = "";
										document.getElementById('phantomAcc_warning_msg').innerHTML ="<spring:message code="ims.pcd.imsinstallation.msg.020"/>";
									}else{
										document.getElementById('phantomAcc_warning').style.display = "none";
										document.getElementById('phantomAcc_warning_msg').innerHTML = "";
									}
									
									if (document.getElementById('registeredTheClubLoginId').value == "null" || document.getElementById('registeredTheClubLoginId').value == ""){
										document.getElementById('registeredTheClubLoginId').value = "";
										document.getElementById('registeredTheClubLoginId_err').style.display="";
										document.getElementById('registeredTheClubLoginId_errMsg').innerHTML="<spring:message code="ims.pcd.imsinstallation.msg.019"/>";
									}
									
								}else{
									//alert('wish to reg myhkt and club');
									document.getElementById('myHktReg').style.display = "";
									
									document.getElementById('myHktReg7').style.display = "";	
									document.getElementById('myHktReg9').style.display = "";	
									document.getElementById('myHktReg3').style.display = "";
									document.getElementById('myHktReg13').style.display = "";
									document.getElementById('myHktReg12').style.display = "";
 									document.getElementById('isRegHKTLoginId').value = "Y";
 									document.getElementById('isRegClubLoginId').value = "Y";
 									document.getElementById('isRegHKTClubLoginId').value = "Y";
									document.getElementById('hktClubLoginName').disabled = false;
									
									if(clubCutOff == "N"){
										if(document.getElementById('isRegHKTClubLoginId').value == "N" || document.getElementById('isRegHKTClubLoginId').value == "n"){
											document.getElementById('isRegClubHKTLoginIdBox').checked = false;
											controlMyHKTRegistration();
										}else{
											document.getElementById('isRegClubHKTLoginIdBox').checked = true;
							 				document.getElementById('isRegClubLoginId').value ="Y";
							 				document.getElementById('isRegHKTLoginId').value ="Y";
							 				document.getElementById('isRegHKTClubLoginId').value ="Y";
										}
									}
									
									if(document.getElementById('noMobileInd').value == "Y"){
										document.getElementById('hktMobileNum').disabled = true;
										document.getElementById('noMobile').style.display = "none";
										document.getElementById('noMobileClear').style.display = "";
									}else{
										document.getElementById('hktMobileNum').disabled = false;
										document.getElementById('noMobile').style.display = "";
										document.getElementById('noMobileClear').style.display = "none";
									}
									
									if (item.nloginResult != "NA"){
										document.getElementById('copyNetEmail2').style.display = "";
										document.getElementById('noEmail2').style.display = "none";
										document.getElementById('noEmailClear2').style.display = "none";
									}else{
										document.getElementById('copyNetEmail2').style.display = "none";
										document.getElementById('noEmail2').style.display = "";
										document.getElementById('noEmailClear2').style.display = "none";
									}
									
									if(item.phantomInd == "Y" ){	
										document.getElementById('phantomAcc_warning').style.display = "";
										document.getElementById('phantomAcc_warning_msg').innerHTML ="<spring:message code="ims.pcd.imsinstallation.msg.020"/>";
									}else{
										document.getElementById('phantomAcc_warning').style.display = "none";
										document.getElementById('phantomAcc_warning_msg').innerHTML = "";
									}

									document.getElementById('noEmailInd').value = "N"; 
									document.getElementById('hktClubLoginName').value = "";
									document.getElementById('hktClubLoginName').disabled = false;
									
									controlClubOptout();
									
								}
								
								//document.getElementById('hktLoginName').value= document.getElementById("reservedId").value + "@netvigator.com";
								//document.getElementById('hktMobileNum').value= document.getElementById('mobileNum').value;
								
								
								if(document.getElementById('hktMobileNum').value==""){
								document.getElementById('hktMobileNum').value= document.getElementById('mobileNum').value.substring(0,8);
								validHktMobileNo();
								document.getElementById('hktMobileNum_error').style.display = "none";
								hktMobileNumEdited=false;
								}
								
								//idReserved=true;
								//fillHktRegDetails();
							}else{
								//alert('xxxx');
								document.getElementById('isRegHKTLoginId').value="X";	
								document.getElementById('isRegClubLoginId').value="X";
								document.getElementById('isRegHKTClubLoginId').value="X";	
								document.getElementById('theClubOptOutInd').value="";
								document.getElementById('csPortalOptOutInd').value="";
								document.getElementById('csPortalTheClubOptOutInd').value="";							
							}
						}//Gary end
					}else if(item.valid == false){
						document.getElementById('error_login').style.visibility = "hidden";						
						document.getElementById('warning_login').style.visibility = "visible";
						document.getElementById('warning_login_msg').innerHTML = item.errorText;
						//Gary start
						hideMyHKTTheClubBlock();
						isReserved=false;
						//Gary end
						//window.tempEl = document.getElementById('loginName');
				        //setTimeout("window.tempEl.focus();",1);
						hideCARECashBlock();
					}
					count++;
				});				
				if(count==0){
					alert("Server error occurs");					
					document.getElementById('warning_login').style.visibility = "hidden";
					document.getElementById('error_login').style.visibility = "hidden";
				}
			}
		});
	}
	
	
	//Added by Eric Ng on 2012-12-14
	function ValidateIDNUM(){
		
		var doctype = $("#docType").val();
		var docNum = $("#docNum").val();
		
		if (doctype == "PASS" ){
			if (docNum.length > 0){
				var patternString = "^[A-Z0-9]*$";
				if (docNum.length < 6){
					document.getElementById('warning_pass').style.display = "";
					document.getElementById('warning_pass_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.022"/>";

					document.getElementById('error_clubmemberid').style.visibility = "hidden";
					document.getElementById('error_clubmemberid_msg').innerHTML = "";
					
				}else if (docNum.match(patternString)){
					document.getElementById('warning_pass').style.display = "none";
					document.getElementById('warning_pass_msg').innerHTML = "";
				}else{
					document.getElementById('warning_pass').style.display = "";
					document.getElementById('warning_pass_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.023"/>";

					document.getElementById('error_clubmemberid').style.visibility = "hidden";
					document.getElementById('error_clubmemberid_msg').innerHTML = "";
				}	
			}
		}else{
			document.getElementById('warning_pass').style.display = "none";
			document.getElementById('warning_pass_msg').innerHTML = "";
		}
		
		change2Business(doctype == "BS");
		isValid = false;	
		getImsCustInfo();
// 		checkCreateCOrder();
	}
	
	function change2Business(boo)
	{
		if (boo)
		{
			$(".personalInfoRow").hide();
			$(".personalInfoErrorRow").hide();
			$(".BirthCol").hide();
			//$("#optInfoButton").hide();
			$(".CompanyMode").show();
			
		}
		else
		{
			$(".personalInfoRow").show();
			$(".personalInfoErrorRow").show();
			$(".BirthCol").show();
			//$("#optInfoButton").show();
			$(".CompanyMode").hide();
		}
		
	}
	
	
	//Terrence Part
	function getImsCustInfo(){
		
		if("${ims_direct_sales}" == 'true'){
		insertPageTrack();
		}
		
		$.ajax({
			url : 'custinfo.html?docType=' + $("#docType").val() + '&idDocNum=' + $("#docNum").val() + '&loginName=' + $("#loginName").val()+ '&t=' + new Date(),
			type : 'POST',
			//async: false, by jacky 20141215
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			//    alert(XMLHttpRequest+textStatus+errorThrown);
				if(textStatus=='parsererror'){
			        alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
			    }else if(textStatus == 'timeout'){
			    	alert("<spring:message code="ims.pcd.imsinstallation.msg.029"/>");
			    	}
			    else {
// 			    	alert(" Error in validating customer ("+document.getElementById('docType').value + "/" + document.getElementById('docNum').value+"), please try again");
			    	alert("<spring:message code="ims.pcd.imsinstallation.msg.031"/>"+document.getElementById('docType').value + "/" + document.getElementById('docNum').value+"<spring:message code="ims.pcd.imsinstallation.msg.031a"/>");
			    	var t = document.getElementById("docNum");
				    t.focus();
				    }
				document.getElementById('warning_hkid').style.display = "none";
				document.getElementById('warning_hkid_msg').innerHTML = "";
				document.getElementById('warning_blacklist').style.display = "none";
				document.getElementById('warning_blacklist_msg').innerHTML = "";
			},
			success : function(msg) {	
				var count=0;
				var idDocNum = document.getElementById('docNum').value;
				$("#isFromBOM").val("N");
				if(isDS){
					$("#existingCustomerConflictWithName").val("N");
					$("#custNo").val("");
				}
				$.each(eval(msg), function(i, item) {
					if(item.isValid == true && mobileOfferInd == "Y"){	
						document.getElementById("docType").disabled = true;
						document.getElementById("docNum").disabled = true;
						document.getElementById('warning_hkid').style.display = "none";
						document.getElementById('warning_hkid_msg').innerHTML = "";
						document.getElementById('warning_mobile').style.display = "none";
						document.getElementById('warning_mobile_msg').innerHTML = "";
						document.getElementById('warning_fixline').style.display = "none";
						document.getElementById('warning_fixline_msg').innerHTML = "";
						if(item.title=="MS" || item.title=="MISS" ){
							document.getElementById('txt_title').selectedIndex = 2;
							$("#title").val(item.title);
						}
						else{
							document.getElementById('txt_title').selectedIndex = 1;
							$("#title").val(item.title);
						}
						document.getElementById('txt_title').disabled = true;
						document.getElementById('custLastName').value = item.lastName;
						document.getElementById('custLastName').disabled = true;
						document.getElementById('custFirstName').value = item.firstName;
						document.getElementById('custFirstName').disabled = true;
						document.getElementById('companyName').value = item.companyName;
						document.getElementById('companyName').disabled = true;
						document.getElementById('contactPersonName').value = 
							item.contactPersonName==null||item.contactPersonName.search("null")>-1?"":item.contactPersonName;
						document.getElementById('DOB').value = item.DOB;	//Used DOB
						if(item.DOB != null && item.DOB != ""){
							document.getElementById('DOB').disabled = true;
						}else{
							document.getElementById('DOB').disabled = false;
						}
						document.getElementById('mobileNum').value = item.mobile;
						if(document.getElementById('hktMobileNum').value==""){
							document.getElementById('hktMobileNum').value = item.mobile.substring(0,8);//GaryTony
							validHktMobileNo();
						}
						document.getElementById('cntFixLineNum').value = item.fixLine;
						//Tony
						if(item.isExisting == true){
							document.getElementById('optInfoButton').innerHTML = "";
							$(".optInfo").css("display", "none");
							jsphasBBDialUp=true;//steven 20130711
							$("#hasBBDailUp").val("Y");
						}else{
							jsphasBBDialUp=false;//steven 20130711
							$("#hasBBDailUp").val("N");
						}
						//Tony End
						document.getElementById('docType').disabled = true;
						document.getElementById('docNum').disabled = true;
						$("#lob").val("IMS");
						$("#custNo").val(item.custNo);
						if(item.isImsBlacklist == true){
							$("#blacklistInd").val("Y");
							document.getElementById('warning_blacklist').style.display = "";
							if(item.j == 0){
								document.getElementById('warning_blacklist_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.032"/>";
							}
							document.getElementById('warning_blacklist_msg').innerHTML = //"Blacklist Customer<br>" + 
								document.getElementById('warning_blacklist_msg').innerHTML + "<spring:message code="ims.pcd.imsinstallation.066"/>" + item.acctNo + "<spring:message code="ims.pcd.imsinstallation.067"/>" + item.osAmt + "<br>";
						}else if(item.isImsBlacklist == false){
							$("#blacklistInd").val("N");
							document.getElementById('warning_blacklist').style.display = "none";
							document.getElementById('warning_blacklist_msg').innerHTML = "";
						}
						if(item.reserveResult == "-7"){
							document.getElementById('error_login').style.visibility = "hidden";						
							document.getElementById('warning_login').style.visibility = "visible";
							document.getElementById('warning_login_msg').innerHTML = item.reserveErrorText;
						}
						$("#isFromBOM").val("Y");
					}
					if(item.isValid == true && isDS){
						document.getElementById('warning_hkid').style.display = "none";
						document.getElementById('warning_hkid_msg').innerHTML = "";
						document.getElementById('warning_mobile').style.display = "none";
						document.getElementById('warning_mobile_msg').innerHTML = "";
						document.getElementById('warning_fixline').style.display = "none";
						document.getElementById('warning_fixline_msg').innerHTML = "";
						
						if ($("#custLastName").attr("value").length>0 && $("#custFirstName").attr("value").length>0){  
							var validNameCheck = $("#custLastName").attr("value") == item.lastName.ltrim().rtrim()  && $("#custFirstName").attr("value") == item.firstName.ltrim().rtrim();
							if (validNameCheck) {
								$("#existingCustomerConflictWithName").val("N");
								document.getElementById('warning_hkid').style.display = "none";
								document.getElementById('warning_hkid_msg').innerHTML = "";
							} else {
								$("#existingCustomerConflictWithName").val("Y");
								document.getElementById('warning_hkid').style.display = "block";
								document.getElementById('warning_hkid_msg').innerHTML = "<spring:message code='ims.pcd.imsinstallation.msg.036' />";
							}
						}
						
						if(item.isExisting == true){
							document.getElementById('optInfoButton').innerHTML = "";
							$(".optInfo").css("display", "none");
							jsphasBBDialUp=true;//steven 20130711
							$("#hasBBDailUp").val("Y");
						}else{
							jsphasBBDialUp=false;//steven 20130711
							$("#hasBBDailUp").val("N");
						}
						$("#lob").val("IMS");
						if($("#existingCustomerConflictWithName").val()=="Y"){
							$("#custNo").val("");
						}else{
							$("#custNo").val(item.custNo);
						}
						if(item.isImsBlacklist == true){
							$("#blacklistInd").val("Y");
							document.getElementById('warning_blacklist').style.display = "";
							if(item.j == 0){
								document.getElementById('warning_blacklist_msg').innerHTML = "BL";
							}
						}else if(item.isImsBlacklist == false){
							$("#blacklistInd").val("N");
							document.getElementById('warning_blacklist').style.display = "none";
							document.getElementById('warning_blacklist_msg').innerHTML = "";
						}
						if(item.reserveResult == "-7"){
							document.getElementById('error_login').style.visibility = "hidden";						
							document.getElementById('warning_login').style.visibility = "visible";
							document.getElementById('warning_login_msg').innerHTML = item.reserveErrorText;
						}
						$("#isFromBOM").val("Y");
						count++;
						isValid = item.isValid; 
					}
					else if(item.isValid == true){
						document.getElementById('warning_hkid').style.display = "none";
						document.getElementById('warning_hkid_msg').innerHTML = "";
						document.getElementById('warning_mobile').style.display = "none";
						document.getElementById('warning_mobile_msg').innerHTML = "";
						document.getElementById('warning_fixline').style.display = "none";
						document.getElementById('warning_fixline_msg').innerHTML = "";
						if(item.title=="MS" || item.title=="MISS" ){
							document.getElementById('txt_title').selectedIndex = 1;
							$("#title").val(item.title);
						}
						else{
							document.getElementById('txt_title').selectedIndex = 0;
							$("#title").val(item.title);
						}
						document.getElementById('txt_title').disabled = true;
						document.getElementById('custLastName').value = item.lastName;
						document.getElementById('custLastName').disabled = true;
						document.getElementById('custFirstName').value = item.firstName;
						document.getElementById('custFirstName').disabled = true;
						document.getElementById('companyName').value = item.companyName;
						document.getElementById('companyName').disabled = true;
						document.getElementById('contactPersonName').value = 
							item.contactPersonName==null||item.contactPersonName.search("null")>-1?"":item.contactPersonName;
						document.getElementById('DOB').value = item.DOB;	//Used DOB
						if(item.DOB != null && item.DOB != ""){
							document.getElementById('DOB').disabled = true;
						}else{
							document.getElementById('DOB').disabled = false;
						}
// 						document.getElementById('mobileNum').value = item.mobile;
// 						if(document.getElementById('hktMobileNum').value==""){
// 							document.getElementById('hktMobileNum').value = item.mobile.substring(0,8);//GaryTony
// 							validHktMobileNo();
// 						}
						document.getElementById('cntFixLineNum').value = item.fixLine;
						//Tony
						if(item.isExisting == true){
							document.getElementById('optInfoButton').innerHTML = "";
							$(".optInfo").css("display", "none");
							jsphasBBDialUp=true;//steven 20130711
							$("#hasBBDailUp").val("Y");
						}else{
							jsphasBBDialUp=false;//steven 20130711
							$("#hasBBDailUp").val("N");
						}
						//Tony End
						document.getElementById('docType').disabled = true;
						document.getElementById('docNum').disabled = true;
						$("#lob").val("IMS");
						$("#custNo").val(item.custNo);
						if(item.isImsBlacklist == true){
							$("#blacklistInd").val("Y");
							document.getElementById('warning_blacklist').style.display = "";
							if(item.j == 0){
								document.getElementById('warning_blacklist_msg').innerHTML = "Blacklist Customer<br>";
							}
							document.getElementById('warning_blacklist_msg').innerHTML = //"Blacklist Customer<br>" + 
								document.getElementById('warning_blacklist_msg').innerHTML + "Account No.: " + item.acctNo + " Outstanding Balance: " + item.osAmt + "<br>";
						}else if(item.isImsBlacklist == false){
							$("#blacklistInd").val("N");
							document.getElementById('warning_blacklist').style.display = "none";
							document.getElementById('warning_blacklist_msg').innerHTML = "";
						}
						if(item.reserveResult == "-7"){
							document.getElementById('error_login').style.visibility = "hidden";						
							document.getElementById('warning_login').style.visibility = "visible";
							document.getElementById('warning_login_msg').innerHTML = item.reserveErrorText;
						}
						$("#isFromBOM").val("Y");
					}else if(item.isValid == false){
					 
						document.getElementById('warning_hkid').style.display = "";
						document.getElementById('warning_hkid_msg').innerHTML = item.errorText;
						
						document.getElementById('error_clubmemberid').style.visibility = "hidden";
						document.getElementById('error_clubmemberid_msg').innerHTML = "";
						//window.tempEl = document.getElementById('docNum');
				        //setTimeout("window.tempEl.focus();",1);
						}else if(item.custNo == null || item.custNo == ""){

				
						document.getElementById('docType').disabled = false;
						document.getElementById('docNum').disabled = false;
						document.getElementById('txt_title').selectedIndex = 0;
						document.getElementById('txt_title').disabled = false;
						document.getElementById('custLastName').value = "";
						document.getElementById('custLastName').disabled = false;
						document.getElementById('custFirstName').value = "";
						document.getElementById('custFirstName').disabled = false;
						document.getElementById('companyName').value = "";
						document.getElementById('companyName').disabled = false;
						document.getElementById('contactPersonName').value = "";
						document.getElementById('DOB').value = "";
						document.getElementById('DOB').disabled = false;
						document.getElementById('mobileNum').value = "";
						document.getElementById('emailAddress').value = "";
						document.getElementById('cntFixLineNum').value = "";
						$("#lob").val("");
						$("#custNo").val("");
						$("#blacklistInd").val("N");
						document.getElementById('warning_blacklist').style.display = "none";
						document.getElementById('warning_blacklist_msg').innerHTML = "";
						document.getElementById('warning_hkid').style.display = "none";
						document.getElementById('warning_hkid_msg').innerHTML = "";
						document.getElementById('warning_mobile').style.display = "none";
						document.getElementById('warning_mobile_msg').innerHTML = "";
						document.getElementById('warning_fixline').style.display = "none";
						document.getElementById('warning_fixline_msg').innerHTML = "";
						if(item.reserveResult == "-7"){
							document.getElementById('error_login').style.visibility = "hidden";						
							document.getElementById('warning_login').style.visibility = "visible";
							document.getElementById('warning_login_msg').innerHTML = item.reserveErrorText;
						}
					}
					count++;
					//Gary
					
					isValid = item.isValid;
					////alert("isValid111:"+isValid);
					
					if(item.createCOrderInd == "Y"){
				//	alert('Share with FSA '+item.relatedFSA);	
						$("#createCOrderInd").val("Y");
						$("#relatedFSA").val(item.relatedFSA);
					}else{
						//alert('no c order');
						$("#createCOrderInd").val("N");
						$("#relatedFSA").val("");
					}
					$("#multiFSAInd").val(item.multiFSAInd);
				});

				if(count==0 && (idDocNum==null || idDocNum=="")){
					
				}
				else if(count==0 && mobileOfferInd == "Y"){
					document.getElementById("docType").disabled = true;
					document.getElementById("docNum").disabled = true;
// 					document.getElementById('txt_title').selectedIndex = 0;
					document.getElementById('txt_title').disabled = false;
					if("${imsInstallationUI.mobileCutomerInfo.lastName}"!=""){
						document.getElementById('custLastName').value = "${imsInstallationUI.mobileCutomerInfo.lastName}";
						document.getElementById('custLastName').disabled = true;
					}else{
						document.getElementById('custLastName').disabled = false;
					}
					if("${imsInstallationUI.mobileCutomerInfo.firstName}"!=""){
						document.getElementById('custFirstName').value = "${imsInstallationUI.mobileCutomerInfo.firstName}";
						document.getElementById('custFirstName').disabled = true;
					}else{
						document.getElementById('custFirstName').disabled = false;
					}
// 					document.getElementById('companyName').value = "";
// 					document.getElementById('companyName').disabled = false;
					document.getElementById('contactPersonName').value = "";
					
					jsphasBBDialUp=false;
					$("#hasBBDailUp").val("N");
					
					
					if("${imsInstallationUI.mobileCutomerInfo.dob}"!=""){
					var dob = new Date("${imsInstallationUI.mobileCutomerInfo.dob}");
					var dd = dob.getDate();
					var mm = dob.getMonth()+1; //January is 0!

					var yyyy = dob.getFullYear();
					if(dd<10){
					    dd='0'+dd;
					} 
					if(mm<10){
					    mm='0'+mm;
					} 
					var dobStr = dd+'/'+mm+'/'+yyyy;
					
					document.getElementById('DOB').value = dobStr;
					document.getElementById('DOB').disabled = true;
					}else{
						document.getElementById('DOB').disabled = false;
					}
// 					document.getElementById('mobileNum').value = "";
// 					document.getElementById('cntFixLineNum').value = "";
					$("#lob").val("IMS");
					$("#custNo").val("");
					$("#blacklistInd").val("N");
					document.getElementById('warning_blacklist').style.display = "none";
					document.getElementById('warning_blacklist_msg').innerHTML = "";
					document.getElementById('warning_hkid').style.display = "none";
					document.getElementById('warning_hkid_msg').innerHTML = "";
					document.getElementById('warning_mobile').style.display = "none";
					document.getElementById('warning_mobile_msg').innerHTML = "";
					document.getElementById('warning_fixline').style.display = "none";
					document.getElementById('warning_fixline_msg').innerHTML = "";
					//Gary
					isValid = true;
				}
				else if(count==0 && isDS){
				
					$("#lob").val("");
					$("#custNo").val("");
					$("#blacklistInd").val("N");
					jsphasBBDialUp=false;
					$("#hasBBDailUp").val("N");
					document.getElementById('warning_blacklist').style.display = "none";
					document.getElementById('warning_blacklist_msg').innerHTML = "";
					document.getElementById('warning_hkid').style.display = "none";
					document.getElementById('warning_hkid_msg').innerHTML = "";
					document.getElementById('warning_mobile').style.display = "none";
					document.getElementById('warning_mobile_msg').innerHTML = "";
					document.getElementById('warning_fixline').style.display = "none";
					document.getElementById('warning_fixline_msg').innerHTML = "";
					document.getElementById('optInfoButton').innerHTML =
			    		"<a href='#' class='nextbutton' type='button' onClick='optInfoCheck()'>Opt Out Info</a><br><br>"; 
					//Gary
					isValid = true;
				}
				else if(count==0){ 
					document.getElementById('txt_title').selectedIndex = 0;
					document.getElementById('txt_title').disabled = false;
					document.getElementById('custLastName').value = "";
					document.getElementById('custLastName').disabled = false;
					document.getElementById('custFirstName').value = "";
					document.getElementById('custFirstName').disabled = false;
					document.getElementById('companyName').value = "";
					document.getElementById('companyName').disabled = false;
					document.getElementById('contactPersonName').value = "";
					document.getElementById('DOB').value = "";
					document.getElementById('DOB').disabled = false;
					document.getElementById('mobileNum').value = "";
					document.getElementById('emailAddress').value = "";
					document.getElementById('cntFixLineNum').value = "";
					$("#lob").val("");
					$("#custNo").val("");
					$("#blacklistInd").val("N");
					jsphasBBDialUp=false;
					$("#hasBBDailUp").val("N");
					document.getElementById('warning_blacklist').style.display = "none";
					document.getElementById('warning_blacklist_msg').innerHTML = "";
					document.getElementById('warning_hkid').style.display = "none";
					document.getElementById('warning_hkid_msg').innerHTML = "";
					document.getElementById('warning_mobile').style.display = "none";
					document.getElementById('warning_mobile_msg').innerHTML = "";
					document.getElementById('warning_fixline').style.display = "none";
					document.getElementById('warning_fixline_msg').innerHTML = "";
					//Gary
					isValid = true;
					////alert("isValid222:"+isValid);
/*					if(item.reserveResult == "-7"){
						document.getElementById('error_login').style.visibility = "hidden";						
						document.getElementById('warning_login').style.visibility = "visible";
						document.getElementById('warning_login_msg').innerHTML = item.reserveErrorText;
					}
*/
				}
				//alert("isValid123: "+isValid +" isReserved: "+ isReserved);
				if(document.getElementById("loginOK").style.display == ""){
					isReserved=true;
				}
				if (isReserved && isValid){
					if(document.getElementById('noEmailInd').value=="Y"){
						csPortalCheck('normal');
					}else{
						csPortalCheck('nlogin');
					}
					
					if(careCashShowInd !=null && careCashShowInd =="Y"){
						setTimeout(function(){careCashReload(); }, 1000);
					}
					
// 					if(careEnable != null && careEnable != "N"){
						
						
// 					}
// 					else{
// 						hideCARECashBlock();	
// 						$("#careCashInd").val("");
// 						$("#careCashOptOutInd").val("");
// 					}

				}else{
					hideCARECashBlock();
					document.getElementById('registeredMyHktLoginId_err').style.display = "none";
					document.getElementById("hktLoginName").value = "";
					document.getElementById("hktMobileNum").value = "";	
				}
				
			}
		});
	}
	
	function careCashReload(){
		

		if(	$("#docType").val() == "HKID" &&  isCARECashValidAge($("#DOB").val()) ){		
			if($("#careCashInd").val()!= "O" &&$("#careCashInd").val() != "A" && isCareCashEnable(careVisit,careBip)){
			
				showCARECashBlock();
				careCashCheckBox();
			}else
				hideCARECashBlock();							
		}
		else{

			hideCARECashBlock();
			$("#careCashInd").val("X");
			$("#careCashOptOutInd").val("");
			document.getElementById('careCashEmail').value = "";
			document.getElementById('careCashMobile').value = "";
		}	
		
	}

	function mouseOver(rowNum){
 		document.getElementById(rowNum).style.backgroundColor = "#abd078";
 	}
 	
 	function mouseOut(rowNum){
 		document.getElementById(rowNum).style.backgroundColor = "";
 	}
 	
 	//Terrence
	function infoClear(){
		if(mobileOfferInd == "Y"){
			document.getElementById('mobileNum').value = "";
			document.getElementById('emailAddress').value = "";
			document.getElementById('cntFixLineNum').value = "";
			if(document.getElementById('txt_title').disabled == false){
				document.getElementById('txt_title').selectedIndex = 0;
			}
			if(document.getElementById('custLastName').disabled == false){
				document.getElementById('custLastName').value = "";
			}
			if(document.getElementById('custFirstName').disabled == false){
				document.getElementById('custFirstName').value = "";
			}
			if(document.getElementById('DOB').disabled == false){
				document.getElementById('DOB').value = "";	
			}
			if ( "${imsInstallationUI.isCC}" != "Y" || $("#mode").val() == "R")
			{
				document.getElementById('idVerified').checked = false;
			}
			document.getElementById('warning_mobile').style.display = "none";
			document.getElementById('warning_mobile_msg').innerHTML = "";
			document.getElementById('warning_fixline').style.display = "none";
			document.getElementById('warning_fixline_msg').innerHTML = "";
			
			document.getElementById('error_clubmemberid').style.visibility = "hidden";
			document.getElementById('error_clubmemberid_msg').innerHTML = "";	
			
		}else{
			
		$("#lob").val("");
		$("#custNo").val("");
		$("#blacklistInd").val("N");
		document.getElementById('docNum').value = "";
		document.getElementById('custLastName').value = "";
		document.getElementById('custFirstName').value = "";
		document.getElementById('DOB').value = "";			
		document.getElementById('mobileNum').value = "";	
		document.getElementById('emailAddress').value = "";
		document.getElementById('cntFixLineNum').value = "";
		document.getElementById('companyName').value = "";
		document.getElementById('contactPersonName').value = "";
		document.getElementById('docType').selectedIndex = 0;
		document.getElementById('txt_title').selectedIndex = 0;
		if ( "${imsInstallationUI.isCC}" != "Y" || $("#mode").val() == "R")
		{
			document.getElementById('idVerified').checked = false;
		}
        document.getElementById('docNum').disabled = false;
	   	document.getElementById('custLastName').disabled = false;
        document.getElementById('custFirstName').disabled = false;
        document.getElementById('DOB').disabled = false;
        document.getElementById('mobileNum').disabled = false;
        document.getElementById('cntFixLineNum').disabled = false;
    	document.getElementById('docType').disabled = false;
    	document.getElementById('txt_title').disabled = false;
		document.getElementById('companyName').disabled = false;
    	document.getElementById('warning_blacklist').style.display = "none";
		document.getElementById('warning_blacklist_msg').innerHTML = "";
		document.getElementById('warning_hkid').style.display = "none";
		document.getElementById('warning_hkid_msg').innerHTML = "";
		document.getElementById('warning_mobile').style.display = "none";
		document.getElementById('warning_mobile_msg').innerHTML = "";
		document.getElementById('warning_fixline').style.display = "none";
		document.getElementById('warning_fixline_msg').innerHTML = "";
		document.getElementById('warning_fixline').style.display = "none";
		document.getElementById('warning_fixline_msg').innerHTML = "";
		//document.getElementById('optInfo').style.display="none"; //Tony
		
		if(document.getElementById('emailAddress_error')!=null){
			document.getElementById('emailAddress_error').style.display = "none";
		}
		
		document.getElementById('error_clubmemberid').style.visibility = "hidden";
		document.getElementById('error_clubmemberid_msg').innerHTML = "";	
		
		$(".optInfo").css("display", "none");
		//Gary
		hideMyHKTTheClubBlock();
		hideCARECashBlock();
		$("#careCashInd").val("");
		$("#careCashOptOutInd").val("");
		$("#careCashEmail").val("");
		$("#careCashMobile").val("");
		careCashCheckBox();
		if(document.getElementById('hktLoginId.errors')!=null){
			document.getElementById('hktLoginId.errors').style.display = "none";
		}
		if(document.getElementById('clubLoginId.errors')!=null){
			document.getElementById('clubLoginId.errors').style.display = "none";
		}
		if(document.getElementById('hktClubLoginId.errors')!=null){
			document.getElementById('hktClubLoginId.errors').style.display = "none";
		}
		document.getElementById('registeredMyHktLoginId_err').style.display = "none";
		document.getElementById('registeredTheClubLoginId_err').style.display = "none";
		document.getElementById('isRegHKTLoginId').value = "";
		document.getElementById('isRegClubLoginId').value = "";
		document.getElementById('isRegHKTClubLoginId').value = "";	
		document.getElementById("hktLoginName").value = "";
		document.getElementById("clubLoginName").value = "";
		document.getElementById("hktMobileNum").value = "";	
		document.getElementById("noEmailInd").value = "N";	
		
		if(document.getElementById('hktLoginId.errors')!=null){
			document.getElementById('error_hkt_login').style.display="none";
		}
		
		if(document.getElementById('clubLoginId.errors')!=null){
			document.getElementById('error_club_login').style.display="none";
		}
		
		if(document.getElementById('hktClubLoginId.errors')!=null){
			document.getElementById('error_hktClub_login').style.display="none";
		}
		
		if(document.getElementById('nowId.errors')!=null){
			document.getElementById('error_now_login').style.display="none";
		}
		
    	if($("[id=addrInventory.n2BuildingInd]").val() == "Y"
    		&& $("[id=installAddress.addrInventory.technology]").val() != "PON"){
    		document.getElementById('fixLineNum').value = "";
    		document.getElementById('fixLineNum').disabled = false;
    		document.getElementById("PCCWcheckbox").checked = false;
    		$("#pccwCheck").val("");
    	}
    	document.getElementById('optInfoButton').innerHTML =
    		"<a href='#' class='nextbutton' type='button' onClick='optInfoCheck()'>Opt Out Info</a><br><br>";
    		
    	change2Business( $("#docNum").val() == "BS");	
		
		}
    	
	}
	
	function optInfoCheck(){
		var answer = confirm("<spring:message code="ims.pcd.imsinstallation.msg.033"/>");
		if (answer){
			<c:choose>
				<c:when test='${imsInstallationUI.isCC == "Y" || ims_direct_sales eq true}'>$(".CC").css("display", "");</c:when>
				<c:otherwise>$(".nonCC").css("display", "");</c:otherwise>
			</c:choose>
			<c:if test='${imsInstallationUI.optInInd == "Y"}'>
			$(".nonCC").find('input:checkbox').attr("disabled",true);
			$(".CC").find('input:checkbox').attr("disabled",true);
			</c:if>
		}
	}
	
	function addClear(){
		document.getElementById('AddressRow').innerHTML = 
			"<div></div>";

		$("#billingSectionCodeSelect").show();
		$("#billingDistrictCodeSelect").show();
		$("#billingAreaCodeSelect").show();
		document.getElementById('billingSectionCodeSelect').disabled = false;
		document.getElementById('billingSectionCodeSelect').selectedIndex = 0;
		document.getElementById('billingDistrictCodeSelect').disabled = false;
		document.getElementById('billingDistrictCodeSelect').selectedIndex = 0;
		document.getElementById('billingAreaCodeSelect').disabled = false;
		document.getElementById('billingAreaCodeSelect').selectedIndex = 0;
		document.getElementById('billingAddress.sectDesc').value = "";
		document.getElementById('billingAddress.distDesc').value = "";
		document.getElementById('billingAddress.areaDesc').value = "";
		document.getElementById('billingAddress.sectDesc').style.visibility = "hidden";
		document.getElementById('billingAddress.distDesc').style.visibility = "hidden";
		document.getElementById('billingAddress.areaDesc').style.visibility = "hidden";

		$("#billingQuickSearch").val(null);
		document.imsinstallationForm.billingQuickSearch.readOnly = false;
		
		if((document.getElementById('noBillingAddressFlag').checked == false)&&
			(document.getElementById('quickSearchFlag').checked == false)){
				enableAdd();
				document.imsinstallationForm.billingQuickSearch.readOnly = true;
				document.getElementById('billingFlat').readOnly = false;
				document.getElementById('billingFloor').readOnly = false;
		}else if((document.getElementById('noBillingAddressFlag').checked == false)&&
				(document.getElementById('quickSearchFlag').checked == true)){
				disableAdd();
				document.imsinstallationForm.billingQuickSearch.readOnly = false;
				document.getElementById('billingFlat').readOnly = false;
				document.getElementById('billingFloor').readOnly = false;
		}else{
				disableAdd();
				document.imsinstallationForm.billingQuickSearch.readOnly = true;
				document.getElementById('billingFlat').readOnly = true;
				document.getElementById('billingFloor').readOnly = true;
		}
		
		document.getElementById('billingLotNum').value = "";
		document.getElementById('billingBuildingName').value = "";
		document.getElementById('billingStreetNum').value = "";
		document.getElementById('billingStreetName').value = "";
		document.getElementById('billingFlat').value = "";
		document.getElementById('billingFloor').value = "";
		document.getElementById('billingStreetCategory').value = "";
		document.getElementById('billingAddress.sectCd').value = "";
		document.getElementById('billingAddress.distNo').value = "";
		document.getElementById('billingAddress.areaCd').value = "";
		
		areaSearchInput = "";
		districtSearchInput = "";
		sectionSearchInput = "";

		document.getElementById('warning_addr').style.visibility = "hidden";
    	document.getElementById('error_addr').style.visibility = "hidden";

	}
	
	function allClear(){
		addClear();
		//document.getElementById('quicksearch').value = "";
	}
	
	function init() {
		document.imsinstallationForm.billingQuickSearch.readOnly = true;
		if((document.getElementById('noBillingAddressFlag').checked == false)&&
			(document.getElementById('quickSearchFlag').checked == true)){
				document.imsinstallationForm.billingQuickSearch.readOnly = false;
		}
	
	if((document.getElementById('noBillingAddressFlag').checked == false)&&
		(document.getElementById('quickSearchFlag').checked == false)){
			document.getElementById('billingFlat').readOnly = false;
			document.getElementById('billingFloor').readOnly = false;
			document.getElementById('billingLotNum').readOnly = false;
			document.getElementById('billingBuildingName').readOnly = false;
			document.getElementById('billingStreetNum').readOnly = false;
			document.getElementById('billingStreetName').readOnly = false;
			document.getElementById('billingStreetCategory').readOnly = false;
	}else if((document.getElementById('noBillingAddressFlag').checked == false)&&
			(document.getElementById('quickSearchFlag').checked == true)){
		document.getElementById('billingFlat').readOnly = false;
		document.getElementById('billingFloor').readOnly = false;
		document.getElementById('billingLotNum').readOnly = true;
		document.getElementById('billingBuildingName').readOnly = true;
		document.getElementById('billingStreetNum').readOnly = true;
		document.getElementById('billingStreetName').readOnly = true;
		document.getElementById('billingStreetCategory').readOnly = true;
	}else{
		document.getElementById('billingFlat').readOnly = true;
		document.getElementById('billingFloor').readOnly = true;
		document.getElementById('billingLotNum').readOnly = true;
		document.getElementById('billingBuildingName').readOnly = true;
		document.getElementById('billingStreetNum').readOnly = true;
		document.getElementById('billingStreetName').readOnly = true;
		document.getElementById('billingStreetCategory').readOnly = true;
	}
		$("#billingSectionCodeSelect").show();
		$("#billingDistrictCodeSelect").show();
		$("#billingAreaCodeSelect").show();
		document.getElementById('billingAddress.sectDesc').style.visibility = "hidden";
		document.getElementById('billingAddress.distDesc').style.visibility = "hidden";
		document.getElementById('billingAddress.areaDesc').style.visibility = "hidden";
			
	//Gary init CS portal on the page
		if(document.getElementById('isRegHKTLoginId').value == "X" || document.getElementById('isRegClubLoginId').value == "X"){	
			isReserved = true;
			document.getElementById("loginOK").style.display = "";
		}else if(document.getElementById('isRegHKTClubLoginId').value == "A" || document.getElementById('isRegHKTClubLoginId').value == "a"){
			//alert('already reg myhkt and club');
			isReserved = true;
			document.getElementById("loginOK").style.display = "";	
			document.getElementById('myHktReg').style.display = "";
			document.getElementById('myHktReg4').style.display = "";
			document.getElementById('myHktReg5').style.display = "";
			csPortalCheck('normal');
		}else if((document.getElementById('isRegHKTLoginId').value == "A" || document.getElementById('isRegHKTLoginId').value == "a")){
			
			//alert('already reg myhkt and wish to reg club');
			isReserved = true;
			document.getElementById("loginOK").style.display = "";	
			document.getElementById('myHktReg').style.display = "";
			document.getElementById('myHktReg4').style.display = "";
			document.getElementById('myHktReg11').style.display = "";
			document.getElementById('myHktReg12').style.display = "";
			csPortalCheck('normal');
			
			if(document.getElementById('clubLoginName').value!="") {
				clubLoginIdEdited = true;
				document.getElementById("loginOK").style.display = "";	
				document.getElementById('myHktReg').style.display = "";
				document.getElementById('myHktReg6').style.display = "";	
				document.getElementById('myHktReg8').style.display = "";	
				if(document.getElementById('noEmailInd').value=="Y"){
					document.getElementById('copyNetEmail1').style.display = "none";
					document.getElementById('noEmail1').style.display = "none";
					document.getElementById('noEmailClear1').style.display = "";
					document.getElementById('clubLoginName').disabled = true;
				}else{
					csPortalCheck('nlogin');
				}
				if(document.getElementById('noMobileInd').value=="Y"){
					sysGenMobile();
				}else{
					clearSysGenMobile();
				}
				document.getElementById('myHktReg3').style.display = "";
				document.getElementById('myHktReg5').style.display = "none";
				document.getElementById('registeredTheClubLoginId_err').style.display = "none";
				if(document.getElementById('isRegClubLoginId').value !="N"){
					document.getElementById('isRegClubLoginId').value ="Y";
				}
				firstReserve = false;
			}
	
			if(document.getElementById('hktMobileNum').value!=""){									
				hktMobileNumEdited=true;
				document.getElementById("loginOK").style.display = "";	
				document.getElementById('myHktReg').style.display = "";
				document.getElementById('myHktReg6').style.display = "";	
				document.getElementById('myHktReg8').style.display = "";
				document.getElementById('myHktReg3').style.display = "";
				document.getElementById('myHktReg5').style.display = "none";
				document.getElementById('registeredTheClubLoginId_err').style.display = "none";
			}
			
			if(clubCutOff == "N"){
				if(document.getElementById('isRegClubLoginId').value == "Y" || document.getElementById('isRegClubLoginId').value == "y"){
					document.getElementById('isRegClubLoginIdBox').checked = true;
				}
				if(document.getElementById('isRegClubLoginId').value == "N" || document.getElementById('isRegClubLoginId').value == "n"){
	 				document.getElementById('isRegClubLoginId').value ="N";
					document.getElementById('isRegClubLoginIdBox').checked = false;
					controlMyHKTRegistration();
				}
			}
			
			controlClubOptout();
			
		}else if((document.getElementById('isRegClubLoginId').value == "A" || document.getElementById('isRegClubLoginId').value == "a")
				&& (document.getElementById('isRegHKTLoginId').value == "Y" || document.getElementById('isRegHKTLoginId').value == "y")){									
			//alert('already reg club and wish to reg myhkt');
			isReserved = true;
			document.getElementById("loginOK").style.display = "";	
			document.getElementById('myHktReg').style.display = "";
			document.getElementById('myHktReg5').style.display = "";
			document.getElementById('myHktReg10').style.display = "";
			csPortalCheck('normal');
			
			if(document.getElementById('hktLoginName').value!="") {
				hktLoginIdEdited = true;
				document.getElementById("loginOK").style.display = "";	
				document.getElementById('myHktReg').style.display = "";
				document.getElementById('myHktReg1').style.display = "";	
				document.getElementById('myHktReg2').style.display = "";	
				document.getElementById('myHktReg3').style.display = "";
				document.getElementById('myHktReg4').style.display = "none";
				document.getElementById('registeredMyHktLoginId_err').style.display = "none";
				document.getElementById('isRegHKTLoginIdBox').checked = true;
 				document.getElementById('isRegHKTLoginId').value ="Y";
				firstReserve = false;
			}
	
			if(document.getElementById('hktMobileNum').value!=""){									
				hktMobileNumEdited=true;
				document.getElementById("loginOK").style.display = "";	
				document.getElementById('myHktReg').style.display = "";
				document.getElementById('myHktReg1').style.display = "";	
				document.getElementById('myHktReg2').style.display = "";	
				document.getElementById('myHktReg3').style.display = "";
				document.getElementById('myHktReg4').style.display = "none";
				document.getElementById('registeredMyHktLoginId_err').style.display = "none";
				document.getElementById('isRegHKTLoginIdBox').checked = true;
 				document.getElementById('isRegHKTLoginId').value = "Y";
			}
			
		}else if ((document.getElementById('isRegHKTLoginId').value == "N" || document.getElementById('isRegHKTLoginId').value == "n")
				&& (document.getElementById('isRegClubLoginId').value == "A" || document.getElementById('isRegClubLoginId').value == "a")){
			//alert('already reg club and not wish to reg myhkt');
			isReserved = true;
			hktMobileNumEdited=true;
			document.getElementById("loginOK").style.display = "";
			var docNumPattern =new RegExp(".*[\\d].*[\\d].*[\\d].*[\\d].*[\\d].*[\\d].*");
			if (docNumPattern .test(document.getElementById('docNum').value) && document.getElementById('docType').value != "BS"){
				document.getElementById('myHktReg').style.display = "";
				document.getElementById('myHktReg5').style.display = "";
				csPortalCheck('normal');
				document.getElementById('myHktReg').style.display = "";
				document.getElementById('myHktReg1').style.display = "";	
				document.getElementById('myHktReg2').style.display = "";	
				document.getElementById('myHktReg3').style.display = "";
				document.getElementById('isRegHKTLoginIdBox').checked = false;
				document.getElementById('isRegHKTLoginId').value = "N";
				document.getElementById('isRegClubLoginId').value = "A";
				document.getElementById('isRegHKTClubLoginId').value = "N";
				
				document.getElementById('hktMobileNum').value= document.getElementById("mobileNum").value.substring(0,8);
				validHktMobileNo();
			}
			controlMyHKTRegistration();
			
			
		}else{ 
			//alert('wish to reg myhkt and club');
			if(document.getElementById('hktClubLoginName').value!=""
					|| clubCutOff == "N" && (document.getElementById('isRegHKTClubLoginId').value == "N" || document.getElementById('isRegHKTClubLoginId').value == "n")) {	
				hktClubLoginIdEdited = true;
				document.getElementById("loginOK").style.display = "";	
				document.getElementById('myHktReg').style.display = "";
				document.getElementById('myHktReg7').style.display = "";	
				document.getElementById('myHktReg9').style.display = "";
				if(document.getElementById('noEmailInd').value=="Y"){
					document.getElementById('copyNetEmail2').style.display = "none";
					document.getElementById('noEmail2').style.display = "none";
					document.getElementById('noEmailClear2').style.display = "";
					document.getElementById('hktClubLoginName').disabled = true;
				}else{
					csPortalCheck('nlogin');
				}
				if(document.getElementById('noMobileInd').value=="Y"){
					sysGenMobile();
				}else{
					clearSysGenMobile();
				}
				document.getElementById('myHktReg3').style.display = "";
				document.getElementById('myHktReg13').style.display = "";
				document.getElementById('myHktReg12').style.display = "";
				document.getElementById('myHktReg4').style.display = "none";
				document.getElementById('myHktReg5').style.display = "none";
				document.getElementById('registeredMyHktLoginId_err').style.display = "none";
				document.getElementById('registeredTheClubLoginId_err').style.display = "none";
 				document.getElementById('isRegClubLoginId').value ="Y";
 				document.getElementById('isRegHKTLoginId').value ="Y";
				firstReserve = false;
			}
			if(document.getElementById('hktMobileNum').value!=""){									
				hktMobileNumEdited=true;
				document.getElementById("loginOK").style.display = "";	
				document.getElementById('myHktReg').style.display = "";
				document.getElementById('myHktReg7').style.display = "";	
				document.getElementById('myHktReg9').style.display = "";
				document.getElementById('myHktReg3').style.display = "";
				document.getElementById('myHktReg4').style.display = "none";
				document.getElementById('myHktReg5').style.display = "none";
				document.getElementById('registeredMyHktLoginId_err').style.display = "none";
				document.getElementById('registeredTheClubLoginId_err').style.display = "none";
			}
			
			if(clubCutOff == "N"){
				if(document.getElementById('isRegHKTClubLoginId').value == "N" || document.getElementById('isRegHKTClubLoginId').value == "n"){
	 				document.getElementById('isRegClubLoginId').value ="N";
	 				document.getElementById('isRegHKTLoginId').value ="N";
					document.getElementById('isRegClubHKTLoginIdBox').checked = false;
					controlMyHKTRegistration();
				}
				else{
					document.getElementById('isRegClubHKTLoginIdBox').checked = true;
					controlMyHKTRegistration();
				}
			}
			
			controlClubOptout();
			
		}
		//Gary end

		//NowID
		//initial
		if(document.getElementById('isValidForNowId').value == "Y"){
				document.getElementById('nowIdReg').style.display = "";
				document.getElementById('nowIdReg1').style.display = "";	
				document.getElementById('nowIdReg2').style.display = "";	
				if(document.getElementById('isRegNowId').value == "Y"){
					document.getElementById('isRegNowIdBox').checked = true;
				}else{
					document.getElementById('isRegNowIdBox').checked = false;
				}
			controlNowIdRegistration();
		}else{
				document.getElementById('nowIdReg').style.display = "none";
				document.getElementById('nowIdReg1').style.display = "none";	
				document.getElementById('nowIdReg2').style.display = "none";	
				document.getElementById('isRegNowId').value = "X";
		}
		
		
		
		/*
		//when docType on change
		$("#docType").change(function(){
			alert("docType is change");
			if(!($("#docType").val() == "PASS" || $("#docType").val() == "HKID")){
				alert("neither PASS nor HKID");
				document.getElementById('nowIdReg').style.display = "none";
				document.getElementById('nowIdReg1').style.display = "none";	
				document.getElementById('nowIdReg2').style.display = "none";	
			}else{
				if(document.getElementById('isValidForNowId').value == "Y"){
					document.getElementById('nowIdReg').style.display = "";
					document.getElementById('nowIdReg1').style.display = "";	
					document.getElementById('nowIdReg2').style.display = "";	
					if(document.getElementById('isRegNowId').value == "Y"){
						document.getElementById('isRegNowIdBox').checked = true;
					}else{
						document.getElementById('isRegNowIdBox').checked = false;
					}
				controlNowIdRegistration();
				}
			}
		});
		*/
		

		//for reacall order
		/*
		if ($("#billingQuickSearch").val() == '') {
			//alert('clear');
			document.getElementById('billingAddress.sectDesc').value = "";
			document.getElementById('billingAddress.distDesc').value = "";
			document.getElementById('billingAddress.areaDesc').value = "";
			document.getElementById('billingStreetCategory').value = "";

			document.getElementById('billingAddress.sectCd').value = "";
			document.getElementById('billingAddress.distNo').value = "";
			document.getElementById('billingAddress.areaCd').value = "";

			document.getElementById('billingBuildingName').value = "";
			document.getElementById('billingStreetNum').value = "";
			document.getElementById('billingStreetName').value = "";
			document.getElementById('billingFlat').value = "";
			document.getElementById('billingFloor').value = "";
		}
		*/
	}
	
	function setCurrentSearchFrom(input) {
		document.imsinstallationForm.imsSearchFrom.value = input;
	}
	
			
	//Gary's flow for CS portal
	function controlMyHKTRegistration(){	

		if($('#myHktReg1').is(':visible')){
			if(document.getElementById('isRegHKTLoginIdBox').checked == true){
				document.getElementById('hktLoginName').disabled = false;
				document.getElementById('hktMobileNum').disabled = false;
				document.getElementById('isRegHKTLoginId').value = "Y";
				document.getElementById('optoutbN').disabled = false;
				document.getElementById('optoutbY').disabled = false;
				if(careCashShowInd !=null && careCashShowInd =="Y"){
					if(isCareCashEnable(careVisit,careBip)&&$("#careCashInd").val()!="A"&&$("#careCashInd").val()!= "O" &&$("#docType").val() == "HKID" &&  isCARECashValidAge($("#DOB").val())){
						
						if($("#careCashInd").val()=="X"||$("#careCashInd").val()==""){						
							$("#careCashInd").val("I");
							$("#careCashOptOutInd").val("I");
						}
					showCARECashBlock();
					careCashCheckBox();
				}
				}
			}else{
				document.getElementById('hktLoginName').disabled = true;
				document.getElementById('hktMobileNum').disabled = true;
				document.getElementById('hktMobileNum_warning').style.display = "none";
				document.getElementById('hktLoginName_warning').style.display = "none";
				if(document.getElementById('isRegHKTLoginId').value != "A"){
					document.getElementById('isRegHKTLoginId').value = "N";
				}
				document.getElementById('optoutbN').disabled = true;
				document.getElementById('optoutbY').disabled = true;
				if($('#CARECash').is(':visible')){
					
// 					if($("#careCashInd").val()=="I"){
						$("#careCashInd").val("");
						$("#careCashOptOutInd").val("");
// 					}
					hideCARECashBlock();
				}
			}
		}
		if($('#myHktReg6').is(':visible')){
			
			if(careCashShowInd !=null && careCashShowInd =="Y"){
				if(isCareCashEnable(careVisit,careBip)&&$("#careCashInd").val()!="A"&&$("#careCashInd").val()!= "O" &&$("#docType").val() == "HKID" &&  isCARECashValidAge($("#DOB").val())){
					if($("#careCashInd").val()=="X"||$("#careCashInd").val()==""){
			
					$("#careCashInd").val("I");
					$("#careCashOptOutInd").val("I");
					}
					showCARECashBlock();
					careCashCheckBox();
				}
			}
			if(document.getElementById('isRegClubLoginIdBox').checked == true){
				document.getElementById('clubLoginName').disabled = false;
				document.getElementById('hktMobileNum').disabled = false;
				document.getElementById('isRegClubLoginId').value = "Y";
				document.getElementById('optoutcN').disabled = false;
				document.getElementById('optoutcY').disabled = false;
			}else{
				document.getElementById('clubLoginName').disabled = true;
				document.getElementById('hktMobileNum').disabled = true;
				document.getElementById('hktMobileNum_warning').style.display = "none";
				document.getElementById('hktLoginName_warning').style.display = "none";
				if(document.getElementById('isRegClubLoginId').value != "A"){
					document.getElementById('isRegClubLoginId').value = "N";
				}
				document.getElementById('optoutcN').disabled = true;
				document.getElementById('optoutcY').disabled = true;
				document.getElementById('clubOptoutSelect').disabled = true;
				document.getElementById('optoutTheClubRmk').disabled = true;
				document.getElementById('clubOptoutSelect').value="";
				document.getElementById('optoutTheClubRmk').value="";
			}
		}
		if($('#myHktReg7').is(':visible')){
			if(document.getElementById('isRegClubHKTLoginIdBox').checked == true){
				document.getElementById('hktClubLoginName').disabled = false;
				document.getElementById('hktMobileNum').disabled = false;
				document.getElementById('isRegHKTClubLoginId').value = "Y";
				document.getElementById('isRegClubLoginId').value = "Y";
				document.getElementById('isRegHKTLoginId').value = "Y";
				document.getElementById('optoutaN').disabled = false;
				document.getElementById('optoutaY').disabled = false;
				if(careCashShowInd !=null && careCashShowInd =="Y"){
					if(isCareCashEnable(careVisit,careBip)&&$("#careCashInd").val()!="A"&&$("#careCashInd").val()!= "O" &&$("#docType").val() == "HKID" &&  isCARECashValidAge($("#DOB").val())){					
						if($("#careCashInd").val()=="X"||$("#careCashInd").val()==""){
						
							$("#careCashInd").val("I");
							$("#careCashOptOutInd").val("I");
						}
						showCARECashBlock();
						careCashCheckBox();
					}
				}
			}else{
				document.getElementById('hktClubLoginName').disabled = true;
				document.getElementById('hktMobileNum').disabled = true;
				document.getElementById('hktMobileNum_warning').style.display = "none";
				document.getElementById('hktLoginName_warning').style.display = "none";
				if(document.getElementById('isRegHKTClubLoginId').value != "A"){
					document.getElementById('isRegHKTClubLoginId').value = "N";
					document.getElementById('isRegClubLoginId').value = "N";
					document.getElementById('isRegHKTLoginId').value = "N";
				}
				document.getElementById('optoutaN').disabled = true;
				document.getElementById('optoutaY').disabled = true;
				document.getElementById('clubOptoutSelect').disabled = true;
				document.getElementById('optoutTheClubRmk').disabled = true;
				document.getElementById('clubOptoutSelect').value="";
				document.getElementById('optoutTheClubRmk').value="";
				if($('#CARECash').is(':visible')){
// 					if($("#careCashInd").val()=="I"){
						$("#careCashInd").val("");
						$("#careCashOptOutInd").val("");
// 					}

					hideCARECashBlock();
				}
			}
		}
	}
	
	function sysGenEmail(){
		if(document.getElementById('custLastName').value == "" || document.getElementById('custFirstName').value ==""){
			alert('Please input Customer name.');
		}else{
			csPortalCheck('sysgen');
		}
	}
	
	function copyNetEmail(){
		if(document.getElementById('clubLoginName')!=null){
			document.getElementById('clubLoginName').value = document.getElementById("loginName").value + "@netvigator.com";
			document.getElementById('hktLoginName_warning').style.display = "none";
			document.getElementById('clubLoginName_error').style.display = "none";
		}
		if(document.getElementById('hktClubLoginName')!=null){
			document.getElementById('hktClubLoginName').value = document.getElementById("loginName").value + "@netvigator.com";
			document.getElementById('hktLoginName_warning').style.display = "none";
			document.getElementById('hktClubLoginName_error').style.display = "none";
		}
	}
	
	function clearSysGenEmail(){
		
		if($('#noEmailClear2').is(':visible')){
			document.getElementById('hktClubLoginName').value = "";
			document.getElementById('hktClubLoginName').disabled = false;
			document.getElementById('noEmail2').style.display = "";
			document.getElementById('noEmailInd').value = "N"; 
			document.getElementById('noEmailClear2').style.display = "none";
			document.getElementById('hktLoginName_warning').style.display = "none";
		}
		
		if($('#noEmailClear1').is(':visible')){
			document.getElementById('clubLoginName').value = "";
			document.getElementById('clubLoginName').disabled = false;
			document.getElementById('noEmail1').style.display = "";
			document.getElementById('noEmailInd').value = "N"; 
			document.getElementById('noEmailClear1').style.display = "none";
			document.getElementById('hktLoginName_warning').style.display = "none";
		}
	}
	
	function sysGenMobile(){
		document.getElementById('noMobileInd').value = "Y";
		document.getElementById('noMobile').style.display = "none";
		document.getElementById('noMobileClear').style.display = "";
		document.getElementById('hktMobileNum').value = "00000000";
		document.getElementById('hktMobileNum').disabled = true;
		document.getElementById('hktMobileNum_warning').style.display = "none";
		if($('#CARECash').is(':visible')){
// 			if($("#careCashInd").val()=="I"){
				$("#careCashInd").val("");
				$("#careCashOptOutInd").val("");
// 			}

			hideCARECashBlock();
		}
	}
	
	function clearSysGenMobile(){
		document.getElementById('noMobileInd').value = "N";
		document.getElementById('noMobile').style.display = "";
		document.getElementById('noMobileClear').style.display = "none";
//		document.getElementById('hktMobileNum').value = "";
		document.getElementById('hktMobileNum').disabled = false;
		if(careCashShowInd !=null && careCashShowInd =="Y"){
			if(isCareCashEnable(careVisit,careBip)&&$("#careCashInd").val()!="A"&&$("#careCashInd").val()!= "O" &&$("#docType").val() == "HKID" &&  isCARECashValidAge($("#DOB").val())){
				if($("#careCashInd").val()=="X"||$("#careCashInd").val()==""){
				
					$("#careCashInd").val("I");
					$("#careCashOptOutInd").val("I");
				}
				showCARECashBlock();
				careCashCheckBox();
			}
		}	
	}
	
	function clearSysGenMobileB(){
		clearSysGenMobile();
		document.getElementById('hktMobileNum').value = "";
	}
	
	function controlClubOptout(){
		if(document.getElementById('theClubOptOutInd').value == "Y" || document.getElementById('csPortalTheClubOptOutInd').value == "Y"){
			document.getElementById('clubOptoutSelect').disabled = false;
			document.getElementById('optoutTheClubRmk').disabled = true;
			if(document.getElementById('clubOptoutSelect').value=="51"){
				document.getElementById('optoutTheClubRmk').disabled = false;
			}else{
				document.getElementById('optoutTheClubRmk').value="";
			}
		}else{
			document.getElementById('clubOptoutSelect').disabled = true;
			document.getElementById('optoutTheClubRmk').disabled = true;
			document.getElementById('clubOptoutSelect').value="";
			document.getElementById('optoutTheClubRmk').value="";
		}
	}
	
	function clubOptoutChange(){
		if(($('#myHktReg13').is(':visible') && document.getElementById('optoutaY').checked == true) || ($('#myHktReg11').is(':visible') && document.getElementById('optoutcY').checked == true)){
			document.getElementById('clubOptoutSelect').disabled = false;
			document.getElementById('optoutTheClubRmk').disabled = true;
			if(document.getElementById('clubOptoutSelect').value=="51"){
				document.getElementById('optoutTheClubRmk').disabled = false;
			}else{
				document.getElementById('optoutTheClubRmk').value="";
			}
		}else{
			document.getElementById('clubOptoutSelect').disabled = true;
			document.getElementById('optoutTheClubRmk').disabled = true;
			document.getElementById('clubOptoutSelect').value="";
			document.getElementById('optoutTheClubRmk').value="";
		}
	}
	
	function validHktMobileNo() {
		hktMobileNumEdited=true;
		var hktMobileNum = $("#hktMobileNum").val();
		var hktMobilePattern=/[456789][0-9][0-9][0-9][0-9][0-9][0-9][0-9]/;
		if (hktMobileNum.match(hktMobilePattern)){
			document.getElementById('hktMobileNum_warning').style.display = "none";
			document.getElementById('hktMobileNum_warning_msg').innerHTML = "";
			document.getElementById('hktMobileNum_error').style.display = "none";
			document.getElementById('hktMobileNum_error_msg').innerHTML = "";
		}else if(hktMobileNum==""){
			document.getElementById('hktMobileNum_error').style.display = "";
			document.getElementById('hktMobileNum_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.012"/>";
			document.getElementById('hktMobileNum_warning').style.display = "none";
			document.getElementById('hktMobileNum_warning_msg').innerHTML = "";
		}else{
			document.getElementById('hktMobileNum_warning').style.display = "";
			document.getElementById('hktMobileNum_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.008"/>";
			document.getElementById('hktMobileNum_error').style.display = "none";
			document.getElementById('hktMobileNum_error_msg').innerHTML = "";
		}
		/*if(document.getElementById('hktMobileNum').value != document.getElementById("mobileNum").value){
			hktMobileNumEdited=true;
		}else {
			hktMobileNumEdited=false;
		}*/
	}
	
	
	function validHktLoginName(type) {
		//hktLoginIdEdited=true;
		
		var hktLoginNamePattern= /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		
		if(type=="1"){
			var hktLoginName = $("#hktLoginName").val();
			var result = hktLoginNamePattern.test(hktLoginName);
			if (result){
				document.getElementById('hktLoginName_warning').style.display = "none";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "";
				document.getElementById('hktLoginName_error').style.display = "none";
				document.getElementById('hktLoginName_error_msg').innerHTML = "";
			}else if(hktLoginName==""){
				document.getElementById('hktLoginName_error').style.display = "";
				document.getElementById('hktLoginName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.011"/>";
				document.getElementById('hktLoginName_warning').style.display = "none";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "";
			}else{
				document.getElementById('hktLoginName_warning').style.display = "";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.013"/>";
				document.getElementById('hktLoginName_error').style.display = "none";
				document.getElementById('hktLoginName_error_msg').innerHTML = "";
			}	
		}else if(type=="2"){
			var clubLoginName = $("#clubLoginName").val();
			var result = hktLoginNamePattern.test(clubLoginName);
			if (result){
				document.getElementById('hktLoginName_warning').style.display = "none";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "";
				document.getElementById('clubLoginName_error').style.display = "none";
				document.getElementById('clubLoginName_error_msg').innerHTML = "";
			}else if(clubLoginName==""){
				document.getElementById('clubLoginName_error').style.display = "";
				document.getElementById('clubLoginName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.014"/>";
				document.getElementById('hktLoginName_warning').style.display = "none";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "";
			}else{
				document.getElementById('hktLoginName_warning').style.display = "";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.013"/>";
				document.getElementById('clubLoginName_error').style.display = "none";
				document.getElementById('clubLoginName_error_msg').innerHTML = "";
			}	
		}else if(type=="3"){
			var hktClubLoginName = $("#hktClubLoginName").val();
			var result = hktLoginNamePattern.test(hktClubLoginName);
			if (result){
				document.getElementById('hktLoginName_warning').style.display = "none";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "";
				document.getElementById('hktClubLoginName_error').style.display = "none";
				document.getElementById('hktClubLoginName_error_msg').innerHTML = "";
			}else if(hktClubLoginName==""){
				document.getElementById('hktClubLoginName_error').style.display = "";
				document.getElementById('hktClubLoginName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.004"/>";
				document.getElementById('hktLoginName_warning').style.display = "none";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "";
			}else{
				document.getElementById('hktLoginName_warning').style.display = "";
				document.getElementById('hktLoginName_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.013"/>";
				document.getElementById('hktClubLoginName_error').style.display = "none";
				document.getElementById('hktClubLoginName_error_msg').innerHTML = "";
			}	
		}else if(type=="4"){
			var careCashEmail = $("#careCashEmail").val();
		
			var result = hktLoginNamePattern.test(careCashEmail);
			var login = document.getElementById("loginName").value + "@netvigator.com";
			if($('input[name=careCashCheckBox]:checked').val()=="I"){
			if(careCashEmail == login){
				document.getElementById('CARECash2_error').style.display = "";
				document.getElementById('CARECash2_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.034"/>";	
			}else if (result){
				document.getElementById('CARECash2_error').style.display = "none";
				document.getElementById('CARECash2_error_msg').innerHTML = "";
			}else if(careCashEmail==""){
				document.getElementById('CARECash2_error').style.display = "";
				document.getElementById('CARECash2_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.001"/>";	
			}else{
				document.getElementById('CARECash2_error').style.display = "";
				document.getElementById('CARECash2_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.035"/>";
			}	
			}else{
				document.getElementById('CARECash2_error').style.display = "none";
				document.getElementById('CARECash2_error_msg').innerHTML = "";
			}
		}
		
		if(document.getElementById('hktLoginName').value != document.getElementById("loginName").value+"@netvigator.com"){
			hktLoginIdEdited=true;
		}/*else {
			hktLoginIdEdited=false;
		}*/
	}
	
	
		//Gary's part end
		
		
		
	function validNowId(){
		
		var nowIdNamePattern= /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

		var nowIdName = $("#nowIdName").val();
		var result = nowIdNamePattern.test(nowIdName);

		document.getElementById('error_now_login').style.display="none";
		
		if (result){
			document.getElementById('nowIdName_error').style.display = "none";
			document.getElementById('nowIdName_error_msg').innerHTML = "";
		}else if(nowIdName==""){
			document.getElementById('nowIdName_error').style.display = "";
			document.getElementById('nowIdName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.016"/>";
		}else{
			document.getElementById('nowIdName_error').style.display = "";
			document.getElementById('nowIdName_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.017"/>";
		}	
	}
		
	function controlNowIdRegistration(){
		
		if(document.getElementById('isRegNowIdBox').checked == true){
			document.getElementById('nowIdName').disabled = false;
			document.getElementById('isRegNowId').value = "Y";
		}else{
			document.getElementById('nowIdName').disabled = true;
			document.getElementById('isRegNowId').value = "N";
		}
	}
		
	//STATR AJAX
	$(function() {
		$.ajax({
			url : "imsaddressdistrictlookup.html?T=AREA",
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
			        alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
			    }else if(textStatus == 'timeout'){
			    	alert("<spring:message code="ims.pcd.imsinstallation.msg.029"/>");
			    }
			},
			success : function(msg) {
				$("#billingAreaCodeSelect").empty();
				$.each(eval(msg), function(i, item) {
					if (item.id == "${imsInstallationUI.billingAddress.areaCd}") {
						$("<option value='" + item.id + "' selected='selected'>"
						+ item.name + "</option>").appendTo($("#billingAreaCodeSelect"));
					} else {
						$("<option value='" + item.id + "' >" 
						+ item.name	+ "</option>").appendTo($("#billingAreaCodeSelect"));
					}
				});

				$('#billingAreaCodeSelect').trigger("change"); //for assign value
			}
		});

		$("#billingAreaCodeSelect").change(
			function() {
				loadDistrict($("#billingAreaCodeSelect").val());
				$("#billingAddress.areaCd").val($("#billingAreaCodeSelect").val());
				$("#billingAddress.areaDesc").val($("#billingAreaCodeSelect option:selected").text());
				areaSearchInput = " " + $("#billingAreaCodeSelect option:selected").text();
				
				//init();//change readonly
			});

		$("#billingDistrictCodeSelect").change(
			function() {
				loadSection($("#billingDistrictCodeSelect").val());
				$("#billingAddress.distNo").val($("#billingDistrictCodeSelect").val());
				$("#billingAddress.distDesc").val($("#billingDistrictCodeSelect option:selected").text());
				districtSearchInput = " " + $("#billingDistrictCodeSelect option:selected").text();
			});

		$("#billingSectionCodeSelect").change(
			function() {
				$("#billingAddress.sectCd").val($("#billingSectionCodeSelect").val());
				$("#billingAddress.sectDesc").val($("#billingSectionCodeSelect option:selected").text());
				sectionSearchInput = " " + $("#billingSectionCodeSelect option:selected").text();
			});

		function loadDistrict(parentid) {
			$.ajax({
				url : 'imsaddressdistrictlookup.html?T=DISTRICT&ID=' + parentid,
				type : 'POST',
				dataType : 'json',
				timeout : 60000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				    if(textStatus=='parsererror'){
				        alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
				    }else if(textStatus == 'timeout'){
				    	alert("<spring:message code="ims.pcd.imsinstallation.msg.029"/>");
				    }     
				},
				success : function(msg) {
					$("#billingDistrictCodeSelect").empty();
					$.each(eval(msg), function(i, item) {
						if (item.id == "${imsInstallationUI.billingAddress.distNo}") {
							$("<option value='" + item.id + "' selected='selected'>"
							+ item.name + "</option>").appendTo($("#billingDistrictCodeSelect"));
						} else {
							$("<option value='" + item.id + "' >"
							+ item.name + "</option>").appendTo($("#billingDistrictCodeSelect"));
						}
					});
					$('#billingDistrictCodeSelect').trigger("change");
				}
			});
		}

		function loadSection(parentid) {
			var varual = '';
			varual = 'imsaddressdistrictlookup.html?T=SECTION&ID=' + parentid + '&SECTIONALL=false';
			$.ajax({
				url : varual,
				type : 'POST',
				dataType : 'json',
				timeout : 60000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				    if(textStatus=='parsererror'){
				        alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
				    }else if(textStatus == 'timeout'){
				    	alert("<spring:message code="ims.pcd.imsinstallation.msg.029"/>");
				    }
				},
				success : function(msg) {
					$("#billingSectionCodeSelect").empty();
					$.each(eval(msg), function(i, item) {
						if (item.id == "${imsInstallationUI.billingAddress.sectCd}") {
							$("<option value='" + item.id + "' selected='selected'>"
							+ item.name + "</option>").appendTo($("#billingSectionCodeSelect"));
						} else {
							$("<option value='" + item.id + "' >"
							+ item.name + "</option>").appendTo($("#billingSectionCodeSelect"));
						}
					});
					$('#billingSectionCodeSelect').trigger("change");
				},
				complete : function() {
					$("#loaderImagePlaceholder").empty();
					$("#billingSectionCodeSelect").show();
				},
				beforeSend : function() {
					$("#billingSectionCodeSelect").hide();
					$("#loaderImagePlaceholder").empty().html(
						"<font color='red'>Please wait... </font>");
				}
			});
		}
	});
	//END AJAX

	function setFocus(){
        window.tempEl = document.getElementById('fixLineNum');
        setTimeout("window.tempEl.focus();",1);	     
	}

	function checkPCCWNum(){
		$.ajax({
			url : 'validatefixedline.html?fixedLineNo=' + $("#fixLineNum").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
			        alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
			    }else if(textStatus == 'timeout'){
			    	alert("<spring:message code="ims.pcd.imsinstallation.msg.029"/>");
			    }
			},
			success : function(msg) {
				var count=0;
				var checkValue = document.getElementById('fixLineNum').value;
				$.each(eval(msg), function(i, item) {
					if(item.valid == "Y"){
						document.getElementById('PCCWcheckbox').checked = true;
						$("#pccwCheck").val("Y");
					}else if(item.valid == ""){
						document.getElementById('PCCWcheckbox').checked = false;
						$("#pccwCheck").val("N");
					}else if(item.valid == "N"){
						document.getElementById('PCCWcheckbox').checked = false;
						$("#pccwCheck").val("N");
						var answer = confirm("<spring:message code="ims.pcd.imsinstallation.msg.024"/>");
						if(answer){		
							document.getElementById('fixLineNum').value = "";
							document.getElementById('PCCWcheckbox').checked = false;
							$("#pccwCheck").val("");
						}else{
							setFocus();
						}
					}
					count++;
				});
				if(count==0){ //&& idDocNum!=null){
					
				}
			}
		});
	}
	
	function checkMobile()
	{
		if ( $("#mobileNum").val() != "" )
		{
			$.ajax({
				url : 'checkmobilenum.html?mobileNum=' + $("#mobileNum").val(),
				type : 'POST',
				dataType : 'json',
				timeout : 60000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				    if(textStatus=='parsererror'){
				        alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
				    }else if(textStatus == 'timeout'){
				    	alert("<spring:message code="ims.pcd.imsinstallation.msg.029"/>");
				    }
					document.getElementById('warning_mobile').style.display = "none";
					document.getElementById('warning_mobile_msg').innerHTML = "";
				},
				success : function(msg) {
					var count=0;
					$.each(eval(msg), function(i, item) {
						if(item.valid == true){
							document.getElementById('warning_mobile').style.display = "none";
							document.getElementById('warning_mobile_msg').innerHTML = "";
							if(document.getElementById('hktMobileNum').value==""){//Gary
								document.getElementById('hktMobileNum').value=document.getElementById('mobileNum').value.substring(0,8);
								validHktMobileNo();
							}
// 							else if(!hktMobileNumEdited)//Tonyyy
// 								document.getElementById('hktMobileNum').value=document.getElementById('mobileNum').value.substring(0,8);
							//fillHktRegDetails();//Gary
						}else if(item.valid == false){
							validHktMobileNo();
							document.getElementById('warning_mobile').style.display = "";
							document.getElementById('warning_mobile_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.008"/>";
							//window.tempEl = document.getElementById('mobileNum');
					        //setTimeout("window.tempEl.focus();",1);
						}
						count++;
					});
					if(count==0){ //&& idDocNum!=null){
						document.getElementById('warning_mobile').style.display = "none";
						document.getElementById('warning_mobile_msg').innerHTML = "";
					}
				}
			});
		}
		else if ( document.getElementById('warning_mobile_msg').innerHTML == "<spring:message code="ims.pcd.imsinstallation.msg.008"/>")
		{
			document.getElementById('warning_mobile').style.display = "none";
			document.getElementById('warning_mobile_msg').innerHTML = "";
		}
	}

	function checkFixLine(){
		if(document.getElementById('cntFixLineNum').value != null && document.getElementById('cntFixLineNum').value != "")
		{
			var regex = /[238][0-9][0-9][0-9][0-9][0-9][0-9][0-9]/;
			if($("#cntFixLineNum").val().length < 8)
			{
				document.getElementById('warning_fixline').style.display = "";
				document.getElementById('warning_fixline_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.025"/>";
			}
			else if($("#cntFixLineNum").val().match(regex)==null)
			{
				document.getElementById('warning_fixline').style.display = "";
				document.getElementById('warning_fixline_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.025"/>";
			}
			else
			{
				document.getElementById('warning_fixline').style.display = "none";
				document.getElementById('warning_fixline_msg').innerHTML = "";
			}
		}else{
			document.getElementById('warning_fixline').style.display = "none";
			document.getElementById('warning_fixline_msg').innerHTML = "";
		}
	}
	
	function checkingBillingAddress()
	{
		$("[id='billingAddress.lotNoStreetNo']").val("Y");
		$("[id='billingAddress.streetCatStreetName']").val("Y");
		$("[id='billingAddress.streetCatStreetNameStreetNo']").val("Y");
		$("[id='billingAddress.buildingNameStreetNoLotNo']").val("Y");
		
		
		if ( 
				$("#billingLotNum").val() == ""
			&&	$("#billingStreetNum").val() == ""
			&&	$("#billingBuildingName").val() == ""
		)
		{
			$("[id='billingAddress.buildingNameStreetNoLotNo']").val("");
		}
		
		if ( $("#billingLotNum").val() != "" && $("#billingStreetNum").val() != "")
		{
			$("[id='billingAddress.lotNoStreetNo']").val("");
		}
		
		if ( 
				( $("#billingStreetCategory").val() != "" || $("#billingStreetName").val() != "" )
			&&  ( $("#billingStreetCategory").val() == "" || $("#billingStreetName").val() == "" )
		)
		{
			$("[id='billingAddress.streetCatStreetName']").val("");
		}
		else if (
				( $("#billingStreetCategory").val() != "" || $("#billingStreetName").val() != "" || $("#billingStreetNum").val() != "" )
			&&  ( $("#billingStreetCategory").val() == "" || $("#billingStreetName").val() == "" || $("#billingStreetNum").val() == "" )
		)
		{
			$("[id='billingAddress.streetCatStreetNameStreetNo']").val("");
		}
	}
	
	function keyUpOnDocNum(){		
		document.getElementById('docNum').value = document.getElementById('docNum').value.toUpperCase();
	}
	
	function keyUpOnCustLastName(){		
		document.getElementById('custLastName').value = document.getElementById('custLastName').value.toUpperCase();
	}
	
	function keyUpOnCustFirstName(){		
		document.getElementById('custFirstName').value = document.getElementById('custFirstName').value.toUpperCase();
	}
	
	function keyUpOnCompanyName(){				
		document.getElementById('companyName').value = document.getElementById('companyName').value.toUpperCase();
	}
	
	function keyUpOnBillingQuickSearch(){		
		//document.getElementById('billingQuickSearch').value = document.getElementById('billingQuickSearch').value.toUpperCase();
	}
	
	function keyUpOnBillingFlat(){		
		document.getElementById('billingFlat').value = document.getElementById('billingFlat').value.toUpperCase();
	}
	
	function keyUpOnBillingFloor(){		
		document.getElementById('billingFloor').value = document.getElementById('billingFloor').value.toUpperCase();
	}
	
	function keyUpOnBillingLotNum(){		
		document.getElementById('billingLotNum').value = document.getElementById('billingLotNum').value.toUpperCase();
	}
	
	function keyUpOnBillingBuildingName(){		
		document.getElementById('billingBuildingName').value = document.getElementById('billingBuildingName').value.toUpperCase();
	}
	
	function keyUpOnBillingStreetNum(){		
		document.getElementById('billingStreetNum').value = document.getElementById('billingStreetNum').value.toUpperCase();
	}
	
	function keyUpOnBillingStreetName(){		
		document.getElementById('billingStreetName').value = document.getElementById('billingStreetName').value.toUpperCase();
	}
	
	function keyUpOnBillingStreetCategory(){		
		document.getElementById('billingStreetCategory').value = document.getElementById('billingStreetCategory').value.toUpperCase();
	}
	
	
	function insertPageTrack(){
		var staffId = "<c:out value="${bomsalesuser.username}"/>";
			$.ajax({
				url : "pagetrack.html?page=imsinstallation&func=customer&staffId="+staffId,
				type : 'POST',
				dataType : 'json',
				timeout : 25000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				     if(textStatus=='parsererror'){
						alert("<spring:message code="ims.pcd.imsinstallation.msg.028"/>"); 
				     }
				},
				success: function(data){				
				return;
				}
			});	
		
		}
	
	
	
	function onblurCustLastNameTrim(){
		document.getElementById('custLastName').value = document.getElementById('custLastName').value.ltrim().rtrim();
	}
	
	function onblurCustFirstNameTrim(){
		document.getElementById('custFirstName').value = document.getElementById('custFirstName').value.ltrim().rtrim();
	}
	
	String.prototype.ltrim = function( chars ) {
	    chars = chars || "\\s*";
	    return this.replace( new RegExp("^[" + chars + "]+", "g"), "" );
	};

	String.prototype.rtrim = function( chars ) {
	    chars = chars || "\\s*";
	    return this.replace( new RegExp("[" + chars + "]+$", "g"), "" );
	};
	
	function hideMyHKTTheClubBlock(){
		document.getElementById('myHktReg').style.display = "none";
		document.getElementById('myHktReg1').style.display = "none";	
		document.getElementById('myHktReg2').style.display = "none";	
		document.getElementById('myHktReg3').style.display = "none";
		document.getElementById('myHktReg4').style.display = "none";
		document.getElementById('myHktReg5').style.display = "none";
		document.getElementById('myHktReg6').style.display = "none";
		document.getElementById('myHktReg7').style.display = "none";
		document.getElementById('myHktReg8').style.display = "none";
		document.getElementById('myHktReg9').style.display = "none";
		document.getElementById('myHktReg10').style.display = "none";
		document.getElementById('myHktReg11').style.display = "none";
		document.getElementById('myHktReg12').style.display = "none";
		document.getElementById('myHktReg13').style.display = "none";
		document.getElementById('hktClubLoginName_error').style.display = "none";
		document.getElementById('clubLoginName_error').style.display = "none";
		document.getElementById('hktLoginName_error').style.display = "none";
		document.getElementById('phantomAcc_warning').style.display = "none";
	}
	
	function copyCareCashEmail(){
		if(document.getElementById('loginName')!=null){
		document.getElementById('careCashEmail').value = document.getElementById("loginName").value + "@netvigator.com";
		validHktLoginName('4');
	
}}
	function copyCareCashMobile(){
		if(document.getElementById('mobileNum').value!=null && document.getElementById('mobileNum').value!="" ){
			document.getElementById('careCashMobile').value = document.getElementById('mobileNum').value ;
			varifyCARECashMobile();
		}else if(document.getElementById('cntFixLineNum').value!=null && document.getElementById('cntFixLineNum').value!=""){
			document.getElementById('careCashMobile').value = document.getElementById('cntFixLineNum').value ;
			varifyCARECashMobile();
		}
		
}
					
	function isCARECashValidAge(temp){
		
	if(temp == null || temp.length<10)
		return false;
	
		var chosenDate = new Date(temp.substring(3,5)+"/"+temp.substring(0,2)+"/"+temp.substring(6));
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1; //January is 0!
		var yyyyMax = today.getFullYear()-80;
		var yyyyMin = today.getFullYear()-18;
		var validDateMax = new Date(yyyyMax,mm,dd);
		var validDateMin = new Date(yyyyMin,mm,dd);
		if(validDateMax>chosenDate || validDateMin<chosenDate)
			return false;
		else
			return true;		
	}
	
	function hideCARECashBlock(){
		
		document.getElementById("CARECash").style.display = "none";	
		document.getElementById("CARECash1").style.display = "none";	
		document.getElementById("CARECash2").style.display = "none";	
		document.getElementById("CARECash3").style.display = "none";	
		document.getElementById("CARECash4").style.display = "none";								
		document.getElementById("CARECash5").style.display = "none";	
		document.getElementById("CARECash6").style.display = "none";
	}
	function showCARECashBlock(){
		
		document.getElementById("CARECash").style.display = "";	
		document.getElementById("CARECash1").style.display = "";	
		document.getElementById("CARECash2").style.display = "";	
		document.getElementById("CARECash3").style.display = "";	
		document.getElementById("CARECash4").style.display = "";								
		document.getElementById("CARECash5").style.display = "";	
		document.getElementById("CARECash6").style.display = "";	
	}
	
	function varifyCARECashMobile() {
		var careCashMobile = $("#careCashMobile").val();
		var Pattern=/[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]/;
		if (careCashMobile.match(Pattern)){
			document.getElementById('CARECash3_error').style.display = "none";
			document.getElementById('CARECash3_error_msg').innerHTML = "";
		}else if(careCashMobile==""){
			document.getElementById('CARECash3_error').style.display = "";
			document.getElementById('CARECash3_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.026"/>";
		}else{
			document.getElementById('CARECash3_error').style.display = "";
			document.getElementById('CARECash3_error_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.027"/>";
		}
	}
	
	function applyCareCash(){
		
		if($('input[name=careCashCheckBox]:checked').val()=="I"){
			document.getElementById('careCashEmail').disabled = false;
			document.getElementById('careCashMobile').disabled = false;
			document.getElementById('receivePromoBox').disabled = false;
		}else{
			document.getElementById('careCashEmail').disabled = true;
			document.getElementById('careCashMobile').disabled = true;
			$("#careCashEmail").val("");
			$("#careCashMobile").val("");
			document.getElementById('careCashEmail').value = "";
			document.getElementById('careCashMobile').value = "";
			$("#careCashOptOutInd").val("I");
			document.getElementById('receivePromoBox').disabled = true;
			document.getElementById('receivePromoBox').checked = false;
		}
		$("#careCashInd").val($('input[name=careCashCheckBox]:checked').val());
	}
	
	function careCashOptOut(){
		if(document.getElementById('receivePromoBox').checked == true)
			$("#careCashOptOutInd").val("O");
		else
			$("#careCashOptOutInd").val("I");
	}
	
	function careCashCheckBox(){
	var careCashInd = $("#careCashInd").val();
	var careCashOptOutInd = $("#careCashOptOutInd").val();
	
		if(careCashInd == null || careCashInd == "" || careCashInd == "I"){
			document.getElementById('applyCARECashY').checked = true;
			document.getElementById('careCashEmail').disabled = false;
			document.getElementById('careCashMobile').disabled = false;
			document.getElementById('receivePromoBox').disabled = false;
		}else{			
			document.getElementById('receivePromoBox').disabled = true;
			document.getElementById('careCashEmail').disabled = true;
			document.getElementById('careCashMobile').disabled = true;
			$("#careCashEmail").val("");
			$("#careCashMobile").val("");
			document.getElementById('careCashEmail').value = "";
			document.getElementById('careCashMobile').value = "";		
			if(careCashInd == "O")		
				document.getElementById('applyCARECashN').checked = true;			
			else if(careCashInd == "P")
				document.getElementById('applyCARECashU').checked = true;
		}
		
		if(careCashOptOutInd == "O")
			document.getElementById('receivePromoBox').checked = true;
		else
			document.getElementById('receivePromoBox').checked = false;
				
	}
	
	function isCareCashEnable(careVisit,careBip){
		
		if(careBip==null||careBip==""||careBip=="N"){
			if(careVisit != null && careVisit.length!=0){
				var releaseDate = new Date(careVisit.substring(0,4)+"/"+careVisit.substring(4,6)+"/"+careVisit.substring(6,8));
				var today = new Date();
				
				releaseDate.setDate(releaseDate.getDate()+parseInt(careCashVisitDate)+1);
				
				if(releaseDate<today)
					return true;
				else
					return false;
			}else
				return true;
		}else{
			return false;
		}
			
	}
	
	function modeChange(){
		if($('input[name=modeCheckBox]:checked').val()=="R"){
			$('#verifiedBox').css("visibility", "visible");
			$("#idVerified").attr("checked", false);
		}else{
			$('#verifiedBox').css("visibility", "hidden");
			$("#idVerified").attr("checked", true);
		}
		$("#mode").val($('input[name=modeCheckBox]:checked').val());
	}
	
	function initModeBox(){
		var mode = $("#mode").val();
	
		if(mode == "R"){
			$('#verifiedBox').css("visibility", "visible");
			$('#modeRetail').attr("checked", true);
			
		}else{	
			$('#verifiedBox').css("visibility", "hidden");
			$('#modeCC').attr("checked", true);
		}
				
	}
	function checkEmail(){

		document.getElementById('warning_email').style.display = "none";
		document.getElementById('warning_email_msg').innerHTML = "";
		
		if ($("[id='emailAddress']").val().length != 0 ){
			
		if (!(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/).test($("[id='emailAddress']").val())){
			
			document.getElementById('warning_email').style.display = "";
			document.getElementById('warning_email_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.067"/>";
			
		}else {
			document.getElementById('warning_email').style.display = "none";
			document.getElementById('warning_email_msg').innerHTML = "";
		}
		
		}
	}
	
	function preReg(){
		if(("${ImsOrder.preRegSubmitted}" != null && "${ImsOrder.preRegSubmitted}" == "Y" )|| ($("#isPreReged").val()!= null && $("#isPreReged").val()=="Y")){
			 $("#preregbtn").removeClass("nextbutton");
			 $("#preregbtn").addClass("nextbuttonPrereg");
			 $("#preregbtn").attr("disabled", false);
// 			document.getElementById("preregBtn").disabled = true;
		}else
			openModalDialog( "imspreregistration.html?dialogMode=true", "PON to Village Enquiry");

	}
	function hide_Form() {		
		closeModalDialog();
		$('.ui-widget-overlay').css("display","none");
		alert("Your Pre-registration request has been submitted.");	
		$("#isPreReged").val("Y");
		 $("#preregbtn").removeClass("nextbutton");
		 $("#preregbtn").addClass("nextbuttonPrereg");
//			$('#preregistration_form').dialog('close');
// 		  $("#preregistration_form").css("display","none");
// 		$('.ui-icon-closethick').click();
// 		

	}
	
</script> 

<table width="100%" height="30">
	<tr>
		<td class="contenttextgreen" align="center">
		 	<spring:message code="ims.pcd.imsinstallation.068"/>
		</td>
	</tr>
</table>


<c:choose>
<c:when test='${ImsOrder.nowTvFormType == null}'>
<input type="hidden" value="no" id="CanBuyNowTv" />
</c:when>
<c:when test='${ImsOrder.nowTvFormType == ""}'>
<input type="hidden" value="no" id="CanBuyNowTv" />
</c:when>
<c:otherwise>
<input type="hidden" value="yes" id="CanBuyNowTv" />
</c:otherwise>
</c:choose> 
<link href="css/imsds.css" rel="stylesheet" type="text/css">
<%@ include file="/WEB-INF/jsp/imssubheader.jsp" %>

<form:form name="imsinstallationForm" method="POST" commandName="imsInstallationUI">
<input type="hidden" name="IMS_UID" value="<%= request.getAttribute("IMS_UID") %>" />
   			<div id="preregform" style="display:none">
        	<iframe id="imspreregistration_form" src="" frameBorder="0" scrolling="no"></iframe>
        	</div>
<br>
<table width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="0" rules="none">
		<tr>
			<td class="table_title" ><spring:message code="ims.pcd.imsinstallation.001"/></td>
		</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
		<!-- Customer Info. -->
		<form:hidden path="hasBBDailUp" />
		<form:hidden path="lob" />
		<form:hidden path="custNo" />
		<form:hidden path="blacklistInd" />
		<form:hidden path="blacklistCustInfo" />
		<form:hidden path="orderActionInd" />
		<form:hidden path="orderStatus" />
		<form:hidden path="approvalRequested" />
		<form:hidden path="isCC" />
		<form:hidden path="isFromBOM" />
		<form:hidden path="mode" />
		<c:if test='${imsInstallationUI.isPT == "Y"}'>
		<tr>
			<td class="table_title" colspan="8">Mode</td>
		</tr>
		<tr>
		<td width="20%">
		<input type="radio" id="modeCC" name="modeCheckBox" value="C" checked="checked" onchange = "modeChange()"/> Call Center
		</td>
		<td width="20%">
		<input type="radio" id="modeRetail" name="modeCheckBox" value="R" onchange = "modeChange()"/> Retail
		</td>
		</tr>
		<tr> <td>&nbsp;</td></tr>
		<tr> <td>&nbsp;</td></tr>
		</c:if>
		
		
		
		<tr>
			<td class="table_title" colspan="8"><spring:message code="ims.pcd.imsinstallation.002"/></td>
		</tr>
		<!-- Terrence Part.. + form:.. + path.. mod. c:disabled rm value PARTLY[NOT COMPLETE] -->
		<tr>
			<td><div align="left"><spring:message code="ims.pcd.imsinstallation.003"/></div></td>
			<td>
				<div align="left">
				<c:choose>
					<c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
						<form:select id="docType" path="idDocType" disabled="true" onchange="ValidateIDNUM()">
							<form:option value="HKID" label="HKID" >HKID</form:option>
							<form:option value="PASS" label="Passport" >Passport</form:option>
							<c:if test='${imsInstallationUI.isCC == "Y"}'>
								<form:option value="BS" label="HKBR" >HKBR</form:option>
							</c:if>
						</form:select>
					</c:when>
					<c:otherwise>
						<form:select id="docType" path="idDocType" disabled="false" onchange="ValidateIDNUM()">
							<form:option value="HKID" label="HKID" >HKID</form:option>
							<form:option value="PASS" label="Passport" >Passport</form:option>
							<c:if test='${imsInstallationUI.isCC == "Y"}'>
								<form:option value="BS" label="HKBR" >HKBR</form:option>
							</c:if>
						</form:select>
					</c:otherwise>
				</c:choose>
				</div>
			</td>
			<td align="left"><spring:message code="ims.pcd.imsinstallation.004"/></td>
			<td>
				<c:choose>
					<c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
						<form:input id="docNum" path="idDocNum" maxlength="30" disabled="true" onkeyup="keyUpOnDocNum()" onblur="ValidateIDNUM()" cssClass="noChinese"/>
					</c:when>
					<c:otherwise>
						<form:input id="docNum" path="idDocNum" maxlength="30" disabled="false" onkeyup="keyUpOnDocNum()" onblur="ValidateIDNUM()" cssClass="noChinese"/>
					</c:otherwise>
				</c:choose>
			</td>
			
			<td id="verifiedBox" <c:if test='${imsInstallationUI.isCC == "Y"}'>style="visibility:hidden"</c:if>>
				<form:checkbox id="idVerified" path="idVerified" value="Y" /><spring:message code="ims.pcd.imsinstallation.005"/>
			</td>
			<td></td>
			<c:choose>
			<c:when test='${imsInstallationUI.approvalRequested == "Y"}'>
			<td align="right"><a href='#' id="clear" class='nextbutton'><spring:message code="ims.pcd.imsinstallation.006"/></a></td>
			</c:when>
			<c:otherwise>
			<td align="right"><a href='#' id="clear" class='nextbutton' onclick='infoClear()'><spring:message code="ims.pcd.imsinstallation.006"/></a></td>
			</c:otherwise></c:choose>
		</tr>
		<tr>
		<td colspan="2"><form:errors path="idDocType" cssClass="error" /></td>
		<td colspan="2"><form:errors path="idDocNum" cssClass="error" /></td>
		<td colspan="3"><form:errors path="idVerified" cssClass="error" /></td>
		</tr>
		<tr class="personalInfoRow" 
			<c:if test='${imsInstallationUI.idDocType == "BS"}'>style="display:none"</c:if>
			>
			<td align="left"><spring:message code="ims.pcd.imsinstallation.007"/></td>
			<td>
			<c:choose>
				<c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
					<form:select id="txt_title" path="title" disabled="true">
						<form:option value="MR" label="MR">MR</form:option>
						<form:option value="MS" label="MS">MS</form:option>
					</form:select>
				</c:when>
				<c:otherwise>
					<form:select id="txt_title" path="title" disabled="false">
						<c:if test="${ims_direct_sales eq true}">
						<form:option value="" label=""></form:option> 
						</c:if>
						<form:option value="MR" label="MR">MR</form:option> 
						<form:option value="MS" label="MS">MS</form:option>
					</form:select>
				</c:otherwise>
			</c:choose>
			</td>
			
			<td align="left"><spring:message code="ims.pcd.imsinstallation.008"/></td>
			<td>
			<c:choose>
				<c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
					<form:input id="custLastName" path="lastName" maxlength="40" disabled="true" onblur="onblurCustLastNameTrim()" onkeyup="keyUpOnCustLastName()"/>
						</c:when>
				<c:otherwise>
					<form:input id="custLastName" path="lastName" maxlength="40" disabled="false" onblur="onblurCustLastNameTrim()" onkeyup="keyUpOnCustLastName()"/>
				</c:otherwise>
			</c:choose>
			</td>
			<td align="left" width="10%"><spring:message code="ims.pcd.imsinstallation.009"/></td>
			<td>
			<c:choose>
				<c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
					<form:input id="custFirstName" path="firstName" maxlength="40" disabled="true" onblur="onblurCustFirstNameTrim()" onkeyup="keyUpOnCustFirstName()"/>
				</c:when>
				<c:otherwise>
					<form:input id="custFirstName" path="firstName" maxlength="40" disabled="false" onblur="onblurCustFirstNameTrim()" onkeyup="keyUpOnCustFirstName()"/>
				</c:otherwise>
			</c:choose>
			</td>
		</tr>
		<tr class="personalInfoErrorRow" 
			<c:if test='${imsInstallationUI.idDocType == "BS"}'>style="display:none"</c:if>
		>
			<td colspan="2"><form:errors path="title" cssClass="error" /></td>
			<td colspan="2"><form:errors path="lastName" cssClass="error" /></td>
			<td colspan="2"><form:errors path="firstName" cssClass="error" /></td>
		</tr>
		<tr class="CompanyMode" 
			<c:if test='${imsInstallationUI.idDocType != "BS"}'>style="display:none"</c:if>
			> 
			<td align="left"><spring:message code="ims.pcd.imsinstallation.010"/></td>
			<td>
				
				<c:choose>
					<c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
						<form:input path="companyName" maxlength="40" disabled="true" onkeyup="keyUpOnCompanyName()"/>
					</c:when>
					<c:otherwise>
						<form:input path="companyName" maxlength="40" disabled="false" onkeyup="keyUpOnCompanyName()"/>
					</c:otherwise>
				</c:choose>
			
			</td>
			
			<td align="left"><spring:message code="ims.pcd.imsinstallation.011"/></td>
			<td><form:input path="contactPersonName"/></td>
		</tr>
		<tr class="CompanyMode" 
			<c:if test='${imsInstallationUI.idDocType != "BS"}'>style="display:none"</c:if>
			>
			<td colspan="2"><form:errors path="companyName" cssClass="error" /></td>
			<td colspan="2"><form:errors path="contactPersonName" cssClass="error" /></td>
		</tr>
		<tr>
			<td align="left" class="BirthCol" 
			<c:if test='${imsInstallationUI.idDocType == "BS"}'>style="display:none"</c:if>
			><spring:message code="ims.pcd.imsinstallation.061"/></td>
			<td class="BirthCol" 
			<c:if test='${imsInstallationUI.idDocType == "BS"}'>style="display:none"</c:if>
			>
				<c:choose>
					<c:when test='${imsInstallationUI.dob != null}'>
						<c:choose><c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
						<form:input id="DOB" path="dobStr" disabled="true" readonly="true" />
						</c:when>
						<c:otherwise>
						<form:input id="DOB" path="dobStr" disabled="false" readonly="true" />
						</c:otherwise></c:choose>
					</c:when>
					<c:otherwise>
						<form:input id="DOB" path="dobStr" disabled="false" readonly="true" />
					</c:otherwise>
				</c:choose>
				<!-- Terrence rm type="text" -->
			</td>
			
			
			<!-- Terrence Part -->
			<td align="left"><spring:message code="ims.pcd.imsinstallation.012"/></td>
			<td>
				<form:input id="mobileNum" path="contact.contactPhone" maxlength="8" onblur="checkMobile()"/>
			</td>
			<td align="left"><spring:message code="ims.pcd.imsinstallation.014"/></td>
			<td><form:input id="cntFixLineNum" path="contact.otherPhone" maxlength="8" onblur="checkFixLine()"/></td>
		</tr>
		
		
		<tr>
		<td colspan="2" class="BirthCol"
		<c:if test='${imsInstallationUI.idDocType == "BS"}'>style="display:none"</c:if>
		><form:errors path="dobStr" cssClass="error" /></td>
		<td colspan="3"><form:errors path="contact.contactPhone" cssClass="error" /></td>
		</tr>
		
		
			<tr><td align="left"><spring:message code="ims.pcd.imsinstallation.080"/></td>
			<td>
				<form:input id="emailAddress" path="contact.emailAddr"  onblur="checkEmail()"/>				
			</td>

			</tr>
			<tr>
				<td>
				<form:errors path="contact.emailAddr" cssClass="error" />
				</td>
			</tr>
			
 		<c:choose>
			<c:when test='${imsInstallationUI.installAddress.addrInventory.n2BuildingInd == "Y"}'>
				<c:choose>
				<c:when test='${imsInstallationUI.installAddress.addrInventory.technology != "PON"}'>
				<tr>
					<td align="left" colspan="4"><spring:message code="ims.pcd.imsinstallation.013"/>
						<form:input id="fixLineNum" path="fixedLineNo" maxlength="8" onblur="checkPCCWNum()"/>
						<c:choose><c:when test='${imsInstallationUI.pccwCheck == "Y"}'>
							<input type="checkbox" id="PCCWcheckbox" checked disabled>PCCW
							<form:hidden path="pccwCheck"/>
						</c:when>
						<c:otherwise>
							<input type="checkbox" id="PCCWcheckbox" disabled>PCCW
							<form:hidden path="pccwCheck"/>
						</c:otherwise></c:choose>
					</td>
				</tr>
				</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>
			
			</c:otherwise>
		</c:choose>
		
		
		<c:if test='${ImsOrder.preRegInd == "Y"}'>
		<tr id = "high_speed_pre_reg" >
		<td align="right" colspan="7" ><a href='#' id="preregbtn" class='nextbutton' onclick='preReg()()'><spring:message code="ims.pcd.prereg.020"/></a></td>
		</tr>
		</c:if>
		
			
		
		<tr>
		<td colspan ="3">
			<div id="warning_hkid" class="ui-widget" style="display:none">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_hkid_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>
		</tr>
		<tr>
		<td colspan ="3">
			<div id="warning_pass" class="ui-widget" style="display:none">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_pass_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>
		</tr>
		<tr>
		<td colspan ="4">
			<div id="warning_blacklist" class="ui-widget" style="display:none">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_blacklist_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>
		</tr>		
		<tr>
		<td colspan ="3">
			<div id="warning_mobile" class="ui-widget" style="display:none">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_mobile_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>
		</tr>
		<tr>
		<td colspan ="3">
			<div id="warning_fixline" class="ui-widget" style="display:none">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_fixline_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>
		</tr>		
		<tr>
		<td colspan ="3">
			<div id="warning_email" class="ui-widget" style="display:none">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_email_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>
		</tr>
		<tr>
		<td colspan ="3">
			<div class="ui-widget" id="error_clubmemberid" style="visibility:hidden">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="error_clubmemberid_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>
		</tr>
		<tr>
			<td colspan="7" align="right">
			<div id="optInfoButton">
				<c:if  test='${imsInstallationUI.hasBBDailUp != "Y"}'>
				<a href='#' class='nextbutton' type='button' onClick='optInfoCheck()'><spring:message code="ims.pcd.imsinstallation.069"/></a><br><br>
				</c:if>
			</div>
			</td>
		</tr>
		<tr>
			<td colspan="7">
				<div class="optInfo nonCC" style="display:none">
					<table width='100%'><tr class='table_title'><td align='center' width='10%'><spring:message code="ims.pcd.imsinstallation.070"/></td>
					<td align='center' width='10%'>Outbound</td><td align='center' width='10%'><spring:message code="ims.pcd.imsinstallation.071"/></td>
					<td align='center' width='10%'><spring:message code="ims.pcd.imsinstallation.072"/></td>
					<td align='center' width='10%'><spring:message code="ims.pcd.imsinstallation.073"/></td>
					<td align='center' width='10%'><spring:message code="ims.pcd.imsinstallation.074"/></td>
					<td align='center' width='10%'><spring:message code="ims.pcd.imsinstallation.075"/></td></tr>
					<tr><td align='center'>
					<c:choose>
						<c:when test='${imsInstallationUI.custOptInfo.directMailing == "Y"}'>
							<input type='checkbox' id='cb_directMailing' value="Y" onClick="checkOptOut()" checked />
						</c:when>
						<c:otherwise>
							<input type='checkbox' id='cb_directMailing' value="N" onClick="checkOptOut()"/>
						</c:otherwise>
					</c:choose></td>
					<td align='center'>
					<c:choose>
						<c:when test='${imsInstallationUI.custOptInfo.outbound == "Y"}'>
							<input type='checkbox' id='cb_outbound' value="Y" onClick="checkOptOut()" checked/>
						</c:when>
						<c:otherwise>
							<input type='checkbox' id='cb_outbound' value="N" onClick="checkOptOut()"/>
						</c:otherwise>
					</c:choose></td>
				<td align='center'>
				<c:choose><c:when test='${imsInstallationUI.custOptInfo.sms == "Y"}'><input type='checkbox' id='cb_sms' value="Y" onClick="checkOptOut()"  checked/></c:when>
				<c:otherwise><input type='checkbox' id='cb_sms' value="N" onClick="checkOptOut()"/></c:otherwise></c:choose></td>
				<td align='center'>
				<c:choose><c:when test='${imsInstallationUI.custOptInfo.email == "Y"}'><input type='checkbox' id='cb_email' value="Y" onClick="checkOptOut()"  checked/></c:when>
				<c:otherwise><input type='checkbox' id='cb_email' value="N" onClick="checkOptOut()"/></c:otherwise></c:choose></td>
				<td align='center'>
				<c:choose><c:when test='${imsInstallationUI.custOptInfo.webBill == "Y"}'><input type='checkbox' id='cb_webBill' value="Y" onClick="checkOptOut()"  checked/></c:when>
				<c:otherwise><input type='checkbox' id='cb_webBill' value="N" onClick="checkOptOut()"/></c:otherwise></c:choose></td>
				<td align='center'>
				<c:choose><c:when test='${imsInstallationUI.custOptInfo.nonsalesSms == "Y"}'><input type='checkbox' id='cb_nonSalesSms' value="Y" onClick="checkOptOut()"  checked/></c:when>
				<c:otherwise><input type='checkbox' id='cb_nonSalesSms' value="N" onClick="checkOptOut()"/></c:otherwise></c:choose></td>
				<td align='center'>
				<c:choose><c:when test='${imsInstallationUI.custOptInfo.internet == "Y"}'><input type='checkbox' id='cb_onlinePortal' value="Y" onClick="checkOptOut()" checked/></c:when>
				<c:otherwise><input type='checkbox' id='cb_onlinePortal' value="N" onClick="checkOptOut()"/></c:otherwise></c:choose></td></tr></table>
					
					<form:hidden path='custOptInfo.directMailing'/>
					<form:hidden path='custOptInfo.outbound'/>
					<form:hidden path='custOptInfo.sms'/>
					<form:hidden path='custOptInfo.email'/>
					<form:hidden path='custOptInfo.webBill'/>
					<form:hidden path='custOptInfo.nonsalesSms'/>
					<form:hidden path='custOptInfo.internet'/>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="7">
				<div class="optInfo CC" style="display:none">
					<table width='100%'>
						<tr class='table_title'>
							<td align='center' width='80%'><spring:message code="ims.pcd.imsinstallation.076"/></td>
						</tr>
						<tr>
							<td align='center'><input type='checkbox' id='AllOpt' value="Y" onClick="checkOptOut()"/></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>

		<tr><td class="table_title" colspan="7"><spring:message code="ims.pcd.imsinstallation.015"/></td></tr>
		<tr>
		<td align="left"><spring:message code="ims.pcd.imsinstallation.016"/></td>
		<td>
<%-- 			<form:input maxlength="15" id="loginName" path="loginId" onblur="loginNameCheck()"/>@netvigator.com			 --%>
			<form:input maxlength="15" id="loginName" path="loginId" cssStyle="text-transform:lowercase;"/>@netvigator.com
			<input type="hidden" id="reservedId" value="${imsInstallationUI.loginId}"/>
		</td>
		<td align="left" colspan="3">
			<a href="#" class="nextbutton" onClick="loginNameCheck()"><spring:message code="ims.pcd.imsinstallation.017"/></a>
			<form:errors path="loginId" cssClass="error" />
		</td>
		</tr>		
		<tr>
		<td colspan ="3">
			<div id="loginOK" class="ui-widget" style="display:none">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" style="font-weight:bold;">
						<spring:message code="ims.pcd.imsinstallation.079"/>
					</div>
					</p>
				</div>
			</div>
			<div class="ui-widget" id="error_login" style="visibility:hidden">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="error_login_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>

		</tr>
		<tr>
		<td colspan ="3">
			<div id="warning_login" class="ui-widget" style="visibility:hidden">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_login_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>
		</tr>


<!-- 		now ID Registration -->

		<tr><td class="table_title " id="nowIdReg" colspan="7" style="display:none"><spring:message code="ims.pcd.imsinstallation.018"/></td></tr>
		
		<tr id='nowIdReg1' style="display:none">
		<td colspan="2">
			<input type="checkbox"   id='isRegNowIdBox'   onclick="controlNowIdRegistration()"/> <spring:message code="ims.pcd.imsinstallation.062"/>
			<form:hidden  path="isRegNowId" ></form:hidden>
		</td>
		</tr >
		<tr id='nowIdReg2' style="display:none">
		<td align="left"><span class="now">now</span> ID:</td>		
			<td>
				<form:input size="40" maxlength="40" id="nowIdName" path="nowId" onchange="validNowId()"/>				
			</td>
			
			<td colspan ="3">
					<div id="nowIdName_error"  style="display:none">					
							<p>						
							<div class="error" id="nowIdName_error_msg" ></div>
							</p>					
					</div>
			</td>
			
		</tr>
		
		<tr id="error_now_login" style="display:none">
			<td colspan ="2">
				<div class="ui-widget">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="error_addr_msg" style="font-weight:bold; color:#000000; "><form:errors path="nowId" /></div>
						</p>
					</div>
				</div>
			</td>
		</tr>
		
		<tr> <td>&nbsp;</td></tr>
		<tr> <td>&nbsp;</td></tr>
		
<!-- 		now ID Registration end-->


<!--Gary CS Portal-->
		<tr><td class="table_title " id="myHktReg" colspan="7" style="display:none"><spring:message code="ims.pcd.imsinstallation.019"/></td></tr>
		<tr id='myHktReg1' style="display:none">
		<td colspan="2">
			<input type="checkbox"   id='isRegHKTLoginIdBox'   onclick="controlMyHKTRegistration()"/><spring:message code="ims.pcd.imsinstallation.020"/> 
			<form:hidden  path="isRegHKTLoginId" ></form:hidden>
		</td>
		</tr >
		<tr id='myHktReg6' style="display:none">
		<td colspan="2">
			<c:if test='${clubCutOff == "N"}'><input type="checkbox"   id='isRegClubLoginIdBox'   onclick="controlMyHKTRegistration()"/></c:if> <spring:message code="ims.pcd.imsinstallation.021"/>
			<form:hidden  path="isRegClubLoginId" ></form:hidden>
		</td>
		</tr >
		<tr id='myHktReg7' style="display:none">
		<td colspan="2">
			<c:if test='${clubCutOff == "N"}'><input type="checkbox"   id='isRegClubHKTLoginIdBox'   onclick="controlMyHKTRegistration()"/></c:if> <spring:message code="ims.pcd.imsinstallation.022"/>
			<form:hidden  path="isRegHKTClubLoginId" ></form:hidden>
		</td>
		</tr >
		<tr id='myHktReg2' style="display:none">
		<td align="left"><spring:message code="ims.pcd.imsinstallation.077"/></td>		
			<td>
				<form:input size="40" maxlength="40" id="hktLoginName" path="hktLoginId" onchange="validHktLoginName('1')"/>				
			</td>
		</tr>
		
		<tr id="hktLoginName_error" style="display:none">
			<td colspan ="2">
					<div>					
							<p>						
							<div class="error" id="hktLoginName_error_msg" ></div>
							</p>					
					</div>
			</td>
		</tr>
		
		<tr id="error_hkt_login" style="display:none">
			<td colspan ="2">
				<div class="ui-widget">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="error_addr_msg" style="font-weight:bold; color:#000000; "><form:errors path="hktLoginId" /></div>
						</p>
					</div>
				</div>
			</td>
		</tr>
		
		<tr id='myHktReg8' style="display:none">
		<td align="left"><spring:message code="ims.pcd.imsinstallation.063"/></td>		
			<td>
				<form:input size="40" maxlength="40" id="clubLoginName" path="clubLoginId" onchange="validHktLoginName('2')"/>				
			</td>
			<td id='copyNetEmail1' style="display:none"><a href="#" class="nextbutton" onClick="copyNetEmail()"><spring:message code="ims.pcd.imsinstallation.023"/></a></td>
			<td id='noEmail1' style="display:none"><a href="#" class="nextbutton" onClick="sysGenEmail()"><spring:message code="ims.pcd.imsinstallation.024"/></a></td>
			<td id='noEmailClear1' style="display:none"><a href="#" class="nextbutton" onClick="clearSysGenEmail()"><spring:message code="ims.pcd.imsinstallation.078"/></a></td>
		</tr>
		
		<tr id="clubLoginName_error"  style="display:none">
			<td colspan ="2">
					<div>					
							<p>						
							<div class="error" id="clubLoginName_error_msg" ></div>
							</p>					
					</div>
			</td>
		</tr>
		
<%-- 		<tr><td><form:errors path="clubLoginId" cssClass="error" element="div" /></td></tr> --%>
		
		<tr id="error_club_login" style="display:none">
			<td colspan ="2">
				<div class="ui-widget">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="error_addr_msg" style="font-weight:bold; color:#000000; "><form:errors path="clubLoginId" /></div>
						</p>
					</div>
				</div>
			</td>
		</tr>
		
		
		<tr id='myHktReg9' style="display:none">
		<td align="left"><spring:message code="ims.pcd.imsinstallation.026"/></td>		
			<td>
				<form:input size="40" maxlength="40" id="hktClubLoginName" path="hktClubLoginId" onchange="validHktLoginName('3')"/>				
			</td>
			<td id='copyNetEmail2' style="display:none"><a href="#" class="nextbutton" onClick="copyNetEmail()"><spring:message code="ims.pcd.imsinstallation.023"/></a></td>
			<td id='noEmail2' style="display:none"><a href="#" class="nextbutton" onClick="sysGenEmail()"><spring:message code="ims.pcd.imsinstallation.024"/></a></td>
			<td id='noEmailClear2' style="display:none"><a href="#" class="nextbutton" onClick="clearSysGenEmail()"><spring:message code="ims.pcd.imsinstallation.078"/></a></td>

			
		</tr>
		
        <form:hidden path="noEmailInd" />
		<tr id="hktClubLoginName_error" style="display:none">
			<td colspan ="2">
				<div>					
						<p>						
						<div class="error" id="hktClubLoginName_error_msg" ></div>
						</p>					
				</div>
			</td>
		</tr>
		
		
		<tr id="phantomAcc_warning" style="display:none">
			<td colspan ="2">
				<div>					
						<p>						
						<div class="error" id="phantomAcc_warning_msg" ></div>
						</p>					
				</div>
			</td>
		</tr>
					
		<tr id="error_hktClub_login" style="display:none">
			<td colspan ="2">
				<div class="ui-widget">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="error_addr_msg" style="font-weight:bold; color:#000000; "><form:errors path="hktClubLoginId" /></div>
						
						</p>
					</div>
				</div>
			</td>
		</tr>
		
		<tr id='myHktReg3' style="display:none">
			<td align="left"><spring:message code="ims.pcd.imsinstallation.065"/></td>
			<td>
				<form:input  id="hktMobileNum" path="hktMobileNum" maxlength="8"  onchange="validHktMobileNo()"  />
			</td>
			<td id='noMobile' style="display:none"><a href="#" class="nextbutton" onClick="sysGenMobile()"><spring:message code="ims.pcd.imsinstallation.025"/></a></td>
			<td id='noMobileClear' style="display:none"><a href="#" class="nextbutton" onClick="clearSysGenMobileB()"><spring:message code="ims.pcd.imsinstallation.078"/></a></td>
			<td colspan ="3">
					<div id="hktMobileNum_error"  style="display:none">					
							<p>						
							<div class="error" id="hktMobileNum_error_msg" ></div>
							</p>					
					</div>
			</td>
		</tr>
        <form:hidden path="noMobileInd" />
		<tr><td colspan=3><form:errors path="hktMobileNum" cssClass="error" /></td></tr> 
		<tr id='myHktReg4' style="display:none">
			<td align="left"><spring:message code="ims.pcd.imsinstallation.027"/></td>
			<td >
				<input  id="registeredMyHktLoginId" size="40"/>
				
			</td>
		</tr>
		<tr id='myHktReg5' style="display:none">
			<td align="left"><spring:message code="ims.pcd.imsinstallation.028"/></td>
			<td >
				<input  id="registeredTheClubLoginId" size="40"/>
				
			</td>
		</tr>
		<tr id='myHktReg13' style="display:none">
			<td align="left"><spring:message code="ims.pcd.imsinstallation.031"/></td>
			<c:choose><c:when test='${imsInstallationUI.csPortalTheClubOptOutInd == "Y"}'>
        			<td>        				
        				<input type="radio" id="optoutaN" name="csPortalTheClubOptOut" value="N" onclick="clubOptoutChange()" /><spring:message code="ims.pcd.imsinstallation.030"/>
        			</td>
      			  	<td>
        				<input type="radio" id="optoutaY" name="csPortalTheClubOptOut" value="Y" onclick="clubOptoutChange()" checked/><spring:message code="ims.pcd.imsinstallation.029"/>
        			</td>
        			</c:when>
        			<c:otherwise>
        			<td>
        				<input type="radio" id="optoutaN" name="csPortalTheClubOptOut" value="N" onclick="clubOptoutChange()" checked/><spring:message code="ims.pcd.imsinstallation.030"/>
        			</td>
        			<td>        				
        				<input type="radio" id="optoutaY" name="csPortalTheClubOptOut" value="Y" onclick="clubOptoutChange()" /><spring:message code="ims.pcd.imsinstallation.029"/>
        			</td>
        	</c:otherwise></c:choose>
        	<form:hidden path="csPortalTheClubOptOutInd" />
		</tr>
		<tr id='myHktReg10' style="display:none">
			<td align="left"><spring:message code="ims.pcd.imsinstallation.032"/></td>
			<c:choose><c:when test='${imsInstallationUI.csPortalOptOutInd == "Y"}'>
        			<td>        				
        				<input type="radio" id="optoutbN" name="csPortalOptOut" value="N" /><spring:message code="ims.pcd.imsinstallation.030"/>
        			</td>
      			  	<td>
        				<input type="radio" id="optoutbY" name="csPortalOptOut" value="Y" checked/><spring:message code="ims.pcd.imsinstallation.029"/>
        			</td>
        			</c:when>
        			<c:otherwise>
        			<td>
        				<input type="radio" id="optoutbN" name="csPortalOptOut" value="N" checked/><spring:message code="ims.pcd.imsinstallation.030"/>
        			</td>
        			<td>        				
        				<input type="radio" id="optoutbY" name="csPortalOptOut" value="Y" /><spring:message code="ims.pcd.imsinstallation.029"/>
        			</td>
        	</c:otherwise></c:choose>
        	<form:hidden path="csPortalOptOutInd" />
		</tr>
		<tr id='myHktReg11' style="display:none">
			<td align="left"><spring:message code="ims.pcd.imsinstallation.033"/></td>
			<c:choose><c:when test='${imsInstallationUI.theClubOptOutInd == "Y"}'>
        			<td>        				
        				<input type="radio" id="optoutcN" name="theClubOptOut" value="N" onclick="clubOptoutChange()" /><spring:message code="ims.pcd.imsinstallation.030"/>
        			</td>
      			  	<td>
        				<input type="radio" id="optoutcY" name="theClubOptOut" value="Y" onclick="clubOptoutChange()" checked/><spring:message code="ims.pcd.imsinstallation.029"/>
        			</td>
        			</c:when>
        			<c:otherwise>
        			<td>
        				<input type="radio" id="optoutcN" name="theClubOptOut" value="N" onclick="clubOptoutChange()" checked/><spring:message code="ims.pcd.imsinstallation.030"/>
        			</td>
        			<td>        				
        				<input type="radio" id="optoutcY" name="theClubOptOut" value="Y" onclick="clubOptoutChange()" /><spring:message code="ims.pcd.imsinstallation.029"/>
        			</td>
        	</c:otherwise></c:choose>
        	<form:hidden path="theClubOptOutInd" />
		</tr>
		<tr id='myHktReg12' style="display:none">
			<td align="left"><spring:message code="ims.pcd.imsinstallation.034"/></td>
			<td>
        		<form:select id="clubOptoutSelect" path="optoutTheClubReason" onchange="clubOptoutChange()" >
        			<form:option value=""></form:option>
        			<form:options items="${optoutTheClubList}" itemLabel="value" itemValue="key"/>
				</form:select>
			</td>
			<td>
				<form:input size="40" maxlength="40" id="optoutTheClubRmk" path="optoutTheClubRmk"/>				
			</td>
		</tr>
		
		
<!--		<form:hidden path="hktMobileNum" />-->
<!--		<form:hidden path="hktLoginId" />-->
<!--		<form:hidden path="isRegHKTLoginId" />-->
		
		<tr >
			<td colspan ="3">
				<div id="hktLoginName_warning"  style="display:none">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="hktLoginName_warning_msg" style="font-weight:bold;"></div>
						</p>
					</div>
				</div>
			</td>
		</tr>
		
		<tr >
			<td colspan ="3">
				<div id="hktMobileNum_warning" class="ui-widget" style="display:none">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="hktMobileNum_warning_msg" style="font-weight:bold;"></div>
						</p>
					</div>
				</div>
			</td>
		</tr>
		
		<tr >
			<td colspan ="3">
				<div id="registeredMyHktLoginId_err" class="ui-widget" style="display:none">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="registeredMyHktLoginId_errMsg" style="font-weight:bold;"></div>
						</p>
					</div>
				</div>
			</td>
		</tr>
		<tr >
			<td colspan ="3">
				<div id="registeredTheClubLoginId_err" class="ui-widget" style="display:none">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="registeredTheClubLoginId_errMsg" style="font-weight:bold;"></div>
						</p>
					</div>
				</div>
			</td>
		</tr>
		
		<tr> <td>&nbsp;</td></tr>
		<tr> <td>&nbsp;</td></tr>
<!-- Gary end-->	

<!-- celia -->
<form:hidden path="careCashInd" />
<form:hidden path="careCashOptOutInd" />
<tr id = "CARECash" style="display:none">	<td  class="table_title" colspan="7"><spring:message code="ims.pcd.imsinstallation.035"/></td></tr>
<tr id = "CARECash1" style="display:none">   
    <td  colspan="3">
    <input type="radio" id="applyCARECashY" name="careCashCheckBox" value="I" checked="checked" onclick = "applyCareCash()"/><spring:message code="ims.pcd.imsinstallation.036"/>
		</td>   
</tr>
  	
<tr id = "CARECash2" style="display:none">
	<td></td>
	<td align="left" ><span ><spring:message code="ims.pcd.imsinstallation.037"/></span></td>		
	<td><form:input size="40" maxlength="40" id="careCashEmail" path="careCashEmail" onchange="validHktLoginName('4')"/></td>
	<td></td>
<!-- 	<td align="left" colspan="3"><a href="#" class="nextbutton" onClick="copyCareCashEmail()">copy</a></td> -->
			<td >
					<div id="CARECash2_error"  style="display:none">					
							<p>						
							<div class="error" id="CARECash2_error_msg" ></div>
							</p>					
					</div>
			</td>
</tr>     	
  	
<tr id = "CARECash3" style="display:none">
	<td></td>
	<td align="left"><spring:message code="ims.pcd.imsinstallation.064"/></td>		
	<td><form:input size="40" maxlength="8" id="careCashMobile" path="careCashMobile" onchange="varifyCARECashMobile()"/></td>
	<td align="left" ><a href="#" class="nextbutton" onClick="copyCareCashMobile()"><spring:message code="ims.pcd.imsinstallation.038"/></a></td>
			<td >
					<div id="CARECash3_error"  style="display:none">					
							<p>						
							<div class="error" id="CARECash3_error_msg" ></div>
							</p>					
					</div>
			</td>
</tr>

<tr id = "CARECash4" style="display:none">
	<td></td>
	<td colspan="3"><input type="checkbox"   id='receivePromoBox'  onchange = "careCashOptOut()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="ims.pcd.imsinstallation.msg.030"/></td>
<tr>

<tr id = "CARECash5" style="display:none">   
    <td  colspan="3">
    <input type="radio" id="applyCARECashN" name="careCashCheckBox" value="O" onclick = "applyCareCash()"/><spring:message code="ims.pcd.imsinstallation.039"/>
   </td>   		
</tr>

<tr id = "CARECash6" style="display:none">   
    <td  colspan="3">
    <input type="radio" id="applyCARECashU" name="careCashCheckBox" value="P" onclick = "applyCareCash()" /><spring:message code="ims.pcd.imsinstallation.040"/>
   </td>   			
</tr>

<!-- celia(end) -->
		
		<!-- Table part 2, Installation Address -->
		<tr><td class="table_title" colspan="7"><spring:message code="ims.pcd.imsinstallation.041"/></td></tr>
		<tr height="30px">
			<td align="left" ><spring:message code="ims.pcd.imsinstallation.042"/></td>
			<td><div id="flat" >${imsInstallationUI.installAddress.unitNo}</div></td>
			<td align="left"><spring:message code="ims.pcd.imsinstallation.055"/></td>
			<td><div id="floor" >${imsInstallationUI.installAddress.floorNo}</div></td>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr height="30px">
			<td align="left"><spring:message code="ims.pcd.imsinstallation.043"/></td>
			<td><div id="lotNum" >${imsInstallationUI.installAddress.hiLotNo}</div>
			</td>
			<td align="left"><spring:message code="ims.pcd.imsinstallation.044"/></td>
			<td colspan="3" align="left">
				<div id="buildingName" >${imsInstallationUI.installAddress.buildNo}</div>
			</td>
		</tr>
		<tr height="30px">
			<td align="left"><spring:message code="ims.pcd.imsinstallation.045"/></td>
			<td><div id="streetNum" >${imsInstallationUI.installAddress.strNo}</div>
			</td>
			<td align="left"><spring:message code="ims.pcd.imsinstallation.046"/></td>
			<td><div id="streetName" >${imsInstallationUI.installAddress.strName}</div>
			</td>
			<td align="left"><spring:message code="ims.pcd.imsinstallation.047"/></td>
			<td><div id="streetCategory">${imsInstallationUI.installAddress.strCatDesc}</div>
			</td>
		</tr>
		<tr height="30px">
			<td align="left" valign="top"><spring:message code="ims.pcd.imsinstallation.048"/></td>
			<td valign="top">
				<div id='section'>${imsInstallationUI.installAddress.sectDesc}</div>
			</td>
			<td align="left" valign="top"><spring:message code="ims.pcd.imsinstallation.049"/></td>
			<td valign="top">
				<div id="district">${imsInstallationUI.installAddress.distDesc}</div>
			</td>

			<td align="left" valign="top"><spring:message code="ims.pcd.imsinstallation.050"/></td>
			<td valign="top">
				<div id="area">${imsInstallationUI.installAddress.areaDesc}</div>
			</td>
		</tr>
		<!-- End Installation Address -->

		<!-- Billing Address -->
		<tr>
			<td class="table_title" colspan="7"><spring:message code="ims.pcd.imsinstallation.051"/>
			<c:choose>
				<c:when test='${imsInstallationUI.noBillingAddress == "N"}'>
					<input type="checkbox" id="noBillingAddressFlag" value="N" onclick="sameAddrCheck()" />
				</c:when>
				<c:when test='${imsInstallationUI.noBillingAddress == "Y"}'>
					<input type="checkbox" id="noBillingAddressFlag" value="Y" onclick="sameAddrCheck()" checked />
				</c:when>
				<c:when test='${imsInstallationUI.noBillingAddress == ""}'>
					<input type="checkbox" id="noBillingAddressFlag" value="Y" onclick="sameAddrCheck()" checked />
				</c:when>
				<c:when test='${imsInstallationUI.noBillingAddress == null}'>
					<input type="checkbox" id="noBillingAddressFlag" value="Y" onclick="sameAddrCheck()" checked />
				</c:when>
			</c:choose> 
			<spring:message code="ims.pcd.imsinstallation.052"/>
			<form:hidden path="noBillingAddress" />
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
		<c:choose>
			<c:when test='${imsInstallationUI.noBillingAddress == "N"}'>
			<th align="left">
			<div id="quickSearch">
			<c:choose>
					<c:when test='${imsInstallationUI.orderActionInd == "W"}'>
						<c:choose><c:when test='${imsInstallationUI.billingQuickSearch == null}'>
						<input id="quickSearchFlag" name="quickSearchFlag" onClick="sameAddrCheck()" type="checkbox"/>
						</c:when>
						<c:when test='${imsInstallationUI.billingQuickSearch == ""}'>
							<input id="quickSearchFlag" name="quickSearchFlag" onClick="sameAddrCheck()" type="checkbox" />
						</c:when>
						<c:otherwise>
							<input id="quickSearchFlag" name="quickSearchFlag" onClick="sameAddrCheck()" type="checkbox" checked/>
						</c:otherwise></c:choose>
					</c:when>
					<c:otherwise>
						<c:choose>
						<c:when test='${imsInstallationUI.billingQuickSearch == ""}'>
							<input id="quickSearchFlag" name="quickSearchFlag" onClick="sameAddrCheck()" type="checkbox" />
						</c:when>
						<c:when test='${imsInstallationUI.billingQuickSearch == null}'>
							<input id="quickSearchFlag" name="quickSearchFlag" onClick="sameAddrCheck()" type="checkbox" checked/>
						</c:when>
						<c:otherwise>
							<input id="quickSearchFlag" name="quickSearchFlag" onClick="sameAddrCheck()" type="checkbox" checked/>
						</c:otherwise>
						</c:choose>
					</c:otherwise>
			</c:choose>
			<spring:message code="ims.pcd.imsinstallation.053"/></div></th>
			<td colspan="5">
				<div id="quickSearchInput">
					<form:input size="100%" id="billingQuickSearch" path="billingQuickSearch" onkeyup="keyUpOnBillingQuickSearch()" onclick="setCurrentSearchFrom('billingQuickSearch')" />
				</div>
			</td>
			<!-- <input id="quicksearch" name="quicksearch" size="100%" onClick="setCurrentSearchFrom('quickSearch')"/></td> -->
			<!-- onFocus="checkaddr()" -->
			<c:choose>
				<c:when test='${imsInstallationUI.approvalRequested == "Y"}'>
					<td colspan="1" align="right"><div id="quickSearchClear"><a href='#' class='nextbutton'><spring:message code="ims.pcd.imsinstallation.006"/></a></div></td>
				</c:when>
				<c:otherwise>
					<td colspan="1" align="right"><div id="quickSearchClear"><a href='#' class='nextbutton' onclick='allClear()'><spring:message code="ims.pcd.imsinstallation.006"/></a></div></td>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<th align="left">
			<div id="quickSearch" style="display:none">
			<c:choose>
				<c:when test='${imsInstallationUI.billingQuickSearch == ""}'>
					<input id="quickSearchFlag" name="quickSearchFlag" onClick="sameAddrCheck()" type="checkbox" />
				</c:when>
				<c:when test='${imsInstallationUI.billingQuickSearch == null}'>
					<input id="quickSearchFlag" name="quickSearchFlag" onClick="sameAddrCheck()" type="checkbox" checked/>
				</c:when>
				<c:otherwise>
					<input id="quickSearchFlag" name="quickSearchFlag" onClick="sameAddrCheck()" type="checkbox" checked/>
				</c:otherwise>
			</c:choose>
			<spring:message code="ims.pcd.imsinstallation.053"/></div></th>
			<td colspan="5">
			<div id="quickSearchInput" style="display:none">
			<form:input size="100%" id="billingQuickSearch" path="billingQuickSearch" onkeyup="keyUpOnBillingQuickSearch()" onclick="setCurrentSearchFrom('billingQuickSearch')" />
			</div></td>
			<!-- <input id="quicksearch" name="quicksearch" size="100%" onClick="setCurrentSearchFrom('quickSearch')"/></td> -->
			<!-- onFocus="checkaddr()" -->
			<td colspan="1" align="right"><div id="quickSearchClear" style="display:none"><a href='#' class='nextbutton' onclick='allClear()'><spring:message code="ims.pcd.imsinstallation.006"/></a></div></td>
		</c:otherwise>
		</c:choose>
		</tr>
		<tr>
			<td align="left" colspan=2><form:errors path="billingAddress.floorNo" cssClass="error" /></td>
		</tr>
		<tr>
			<td align="left" colspan=2><form:errors path="billingAddress.distDesc" cssClass="error" /></td>
		</tr>
		<tr>
			<td align="left" colspan=2><form:errors path="billingAddress.areaCd" cssClass="error" /></td>
		</tr>
		<tr>
			<td align="left" colspan=2><form:errors path="billingAddress.buildingNameStreetNoLotNo" cssClass="error" /></td>
		</tr>
		<tr>
			<td align="left" colspan=2><form:errors path="billingAddress.lotNoStreetNo" cssClass="error" /></td>
		</tr>
		<tr>
			<td align="left" colspan=2><form:errors path="billingAddress.streetCatStreetName" cssClass="error" /></td>
		</tr>
		<tr>
			<td align="left" colspan=2><form:errors path="billingAddress.streetCatStreetNameStreetNo" cssClass="error" /></td>
		</tr>
			<form:hidden path="billingAddress.lotNoStreetNo"/>
			<form:hidden path="billingAddress.streetCatStreetName"/>
			<form:hidden path="billingAddress.streetCatStreetNameStreetNo"/>
			<form:hidden path="billingAddress.buildingNameStreetNoLotNo"/>
		<c:choose><c:when test='${imsInstallationUI.noBillingAddress == "N"}'>
		<tr height="30px">
			<td colspan="7">
			<!-- Billing Address -->
				<div id="BillingDiv">
				<table width="100%" border="0" cellspacing="0" class="contenttext">
				<tr height="30px">
					<td align="left"><spring:message code="ims.pcd.imsinstallation.042"/></td>
					<td><form:input maxlength="5" id="billingFlat" path="billingAddress.unitNo" onkeyup="keyUpOnBillingFlat()"/></td>
					<td align="left">Floor:</td>
					<td><form:input maxlength="3" id="billingFloor" path="billingAddress.floorNo" onkeyup="keyUpOnBillingFloor()"/></td>
					<td colspan="2">&nbsp;</td>
				</tr>
 				<tr height="30px">
					<td align="left"><spring:message code="ims.pcd.imsinstallation.043"/></td>
					<td><form:input maxlength="8" id="billingLotNum" path="billingAddress.hiLotNo" onkeyup="keyUpOnBillingLotNum()"/></td>
					<td align="left"><spring:message code="ims.pcd.imsinstallation.044"/></td>
					<td colspan="3" align="left">
						<form:input maxlength="30" size="60%" id="billingBuildingName" path="billingAddress.buildNo" onkeyup="keyUpOnBillingBuildingName()"/>
					</td>
				</tr>
				<tr height="30px">
					<td align="left"><spring:message code="ims.pcd.imsinstallation.045"/></td>
					<td><form:input maxlength="11" id="billingStreetNum" path="billingAddress.strNo" onkeyup="keyUpOnBillingStreetNum()"/></td>
					<td align="left"><spring:message code="ims.pcd.imsinstallation.046"/></td>
					<td><form:input maxlength="23" id="billingStreetName" path="billingAddress.strName" onkeyup="keyUpOnBillingStreetName()"/></td>
					<td align="left"><spring:message code="ims.pcd.imsinstallation.047"/></td>
					<td><form:input id="billingStreetCategory" path="billingAddress.strCatDesc" onkeyup="keyUpOnBillingStreetCategory()"/></td>
					<form:hidden path="billingAddress.strCatCd"/>
				</tr>
				<tr height="30px">
				
					<td align="left" valign="top"><spring:message code="ims.pcd.imsinstallation.048"/></td>
					<td valign="top">
					<select id='billingSectionCodeSelect' style="width: 150px;" ></select>
					<form:input path="billingAddress.sectDesc" />
					<form:hidden path="billingAddress.sectCd" />
					<div id='loaderImagePlaceholder'></div>
					</td>
					<td align="left" valign="top"><spring:message code="ims.pcd.imsinstallation.049"/></td>
					<td>
					<select id='billingDistrictCodeSelect'	style="width: 150px;"></select> 
					<form:input path="billingAddress.distDesc"	/>
					<form:hidden path="billingAddress.distNo" /> 
					<div id='loaderImagePlaceholder'></div>
					</td>
					<td align="left" valign="top"><spring:message code="ims.pcd.imsinstallation.050"/></td>
					<td>
					<select id="billingAreaCodeSelect"	style="width: 150px;"></select> 
					<form:input path="billingAddress.areaDesc"	/>
					<form:hidden path="billingAddress.areaCd" /> 
					<div id='loaderImagePlaceholder'></div>
					</td>
				</tr>
				</table>
				</div>
			<!-- End Billing Address -->
			</td>
		</tr>
		</c:when>
		<c:otherwise>
		<tr height="30px">
			<td colspan="7">
			<!-- Billing Address -->
				<div id="BillingDiv" style="display:none">
				<table width="100%" border="0" cellspacing="0" class="contenttext">
				<tr height="30px">
					<td align="left"><spring:message code="ims.pcd.imsinstallation.042"/></td>
					<td><form:input maxlength="5" id="billingFlat" path="billingAddress.unitNo" onkeyup="keyUpOnBillingFlat()"/></td>
					<td align="left"><spring:message code="ims.pcd.imsinstallation.055"/></td>
					<td><form:input maxlength="3" id="billingFloor" path="billingAddress.floorNo" onkeyup="keyUpOnBillingFloor()"/></td>
					<td colspan="2">&nbsp;</td>
				</tr>
 				<tr height="30px">
					<td align="left"><spring:message code="ims.pcd.imsinstallation.043"/></td>
					<td><form:input maxlength="8" id="billingLotNum" path="billingAddress.hiLotNo" onkeyup="keyUpOnBillingLotNum()"/></td>
					<td align="left"><spring:message code="ims.pcd.imsinstallation.044"/></td>
					<td colspan="3" align="left">
						<form:input maxlength="30" size="60%" id="billingBuildingName" path="billingAddress.buildNo" onkeyup="keyUpOnBillingBuildingName()"/>
					</td>
				</tr>
				<tr height="30px">
					<td align="left"><spring:message code="ims.pcd.imsinstallation.045"/></td>
					<td><form:input maxlength="11" id="billingStreetNum" path="billingAddress.strNo" onkeyup="keyUpOnBillingStreetNum()"/></td>
					<td align="left"><spring:message code="ims.pcd.imsinstallation.046"/></td>
					<td><form:input maxlength="23" id="billingStreetName" path="billingAddress.strName" onkeyup="keyUpOnBillingStreetName()"/></td>
					<td align="left"><spring:message code="ims.pcd.imsinstallation.047"/></td>
					<td><form:input id="billingStreetCategory" path="billingAddress.strCatDesc" onkeyup="keyUpOnBillingStreetCategory()"/></td>
					<form:hidden path="billingAddress.strCatCd"/>
				</tr>
				<tr height="30px">
				
					<td align="left" valign="top"><spring:message code="ims.pcd.imsinstallation.048"/></td>
					<td valign="top">
					<select id='billingSectionCodeSelect' style="width: 150px;" ></select>
					<form:input path="billingAddress.sectDesc" />
					<form:hidden path="billingAddress.sectCd" />
					<div id='loaderImagePlaceholder'></div>
					</td>
					<td align="left" valign="top"><spring:message code="ims.pcd.imsinstallation.049"/></td>
					<td>
					<select id='billingDistrictCodeSelect'	style="width: 150px;"></select> 
					<form:input path="billingAddress.distDesc"	/>
					<form:hidden path="billingAddress.distNo" /> 
					<div id='loaderImagePlaceholder'></div>
					</td>
					<td align="left" valign="top"><spring:message code="ims.pcd.imsinstallation.050"/></td>
					<td>
					<select id="billingAreaCodeSelect"	style="width: 150px;"></select> 
					<form:input path="billingAddress.areaDesc"	/>
					<form:hidden path="billingAddress.areaCd" /> 
					<div id='loaderImagePlaceholder'></div>
					</td>
				</tr>	 
				<tr><form:errors path="billingAddress.floorNo" cssClass="error" />
				</table>
				</div>
			<!-- End Billing Address -->
			</td>
		</tr>
		</c:otherwise>
		</c:choose>
		<tr>
			<td colspan="7" align="right">
				<div id="AddSearchRow"> 
				</div>
		   	</td>
        </tr>
		<tr>
		    <td colspan="7"><hr/>
        		<div id="AddressRow">
        	    	<table width="100%" border="0" cellspacing="1" class="contenttext">
        			</table>	
        		</div>
        	</td>
        </tr>
        <!-- message -->
    	    <table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF"">
			<tr>
				<td colspan="2">
	 			<div class="ui-widget" id="warning_addr" style="visibility:hidden">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="warning_addr_msg" style="font-weight:bold;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
	 			<div class="ui-widget" id="error_addr" style="visibility:hidden">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="error_addr_msg" style="font-weight:bold;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
		<tr>			
			<td colspan="3" >&nbsp;</td>
			<td align="right">
				<div>
					<a href="#" class="nextbuttonS" onClick="formSubmit()"><spring:message code="ims.pcd.imsinstallation.060"/></a>
				</div>
				<input type="hidden" name="currentView" value="imsinstallation"/>
			</td>
		</tr>
			</table>
		<!-- end mesage -->
		</table>	     
      </td>
  </tr>
</table>
<form:hidden id="createCOrderInd" path="createCOrderInd"/>
<form:hidden id="relatedFSA" path="relatedFSA"/>
<form:hidden path="existingCustomerConflictWithName"/>
<form:hidden path="isValidForNowId"/>
<form:hidden path="isPreReged"/>
<form:hidden id="multiFSAInd" path="multiFSAInd"/>

<input type="hidden" id="imsSearchFrom" value="" />
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>