	<%@ include file="header.jsp" %>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<!-- <script type="text/javascript" charset="utf-8" src="js/jquery-1.4.4.js"></script>-->
<script type="text/javascript" charset="utf-8" src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.imsautocomplete2.js"></script>
<script type="text/javascript" charset="utf-8" src="js/inputInterceptor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog_1ams.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

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
	var isValid = false;
	var firstReserve =true;
	
	$(document).ready(function() {
		if("${ims_direct_sales}" == "true") {
			
			$("#custLastName, #custFirstName").blur(function(){ 
				document.getElementById('warning_hkid').style.display = "none";
				document.getElementById('warning_hkid_msg').innerHTML = "";
				getImsCustInfoDS(); 
			});
		}
		
		if ("${imsInstallationUI.isCC}" == "Y")
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
		//getImsCustInfoDS();  
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
		if(document.getElementById("reservedId").value!=document.getElementById("loginName").value){
			alert("Please reserve Login ID");		
			return;
			//Gary added to verify CS portal inputs
		}else if(document.getElementById('isRegHKTLoginId').value == "Y"){
			if(document.getElementById("hktLoginName_warning").style.display == "" || document.getElementById("hktLoginName").value == "" ){
				if(document.getElementById("hktLoginName_warning").style.display == ""){
					document.getElementById('hktLoginName_warning').style.display = "";
					document.getElementById('hktLoginName_warning_msg').innerHTML = "Invalid Login Name format.";
				}else{
					document.getElementById('hktLoginName_error').style.display = "";
					document.getElementById('hktLoginName_error_msg').innerHTML = "Please input My HKT Login ID.";
				}
				if(document.getElementById("hktMobileNum_warning").style.display == "" ){	
					document.getElementById('hktMobileNum_warning').style.display = "";
					document.getElementById('hktMobileNum_warning_msg').innerHTML = "Invalid Mobile number.";
				}else if (document.getElementById("hktMobileNum").value == "" ){
					document.getElementById('hktMobileNum_error').style.display = "";
					document.getElementById('hktMobileNum_error_msg').innerHTML = "Please input mobile no.";
				}
				return;
			
			}else if(document.getElementById("hktMobileNum_warning").style.display == "" ){	
				document.getElementById('hktMobileNum_warning').style.display = "";
				document.getElementById('hktMobileNum_warning_msg').innerHTML = "Invalid Mobile number.";
				return;
			}
			/*else if(document.getElementById("hktMobileNum").value == "" ){	
				document.getElementById('hktMobileNum_error').style.display = "";
				document.getElementById('hktMobileNum_error_msg').innerHTML = "Please input mobile no.";
				return; 
			}*/ 
			
		//}else if(document.getElementById('isRegHKTLoginId').value == "A"){			
		}else if(document.getElementById('isRegHKTLoginId').value!="Y"){
			document.getElementById('hktLoginName').disabled = false;
			document.getElementById('hktMobileNum').disabled = false;
			document.getElementById("hktLoginName").value = "";
			document.getElementById("hktMobileNum").value = "";
		}
		
		//setIsCurrMyHktCus("Y");
		//Gary end
		
		if(document.getElementById("warning_login").style.visibility == "visible"){
			
		}else if(document.getElementById("warning_hkid").style.display == ""){
			
		}else if(document.getElementById("warning_mobile").style.display == ""){
		
		}else if(document.getElementById("warning_fixline").style.display == ""){
			
		//}else if ( checkMobileOrFixed () ){
			

		}else if(document.getElementById("warning_pass").style.display == ""){
			
		
		}else{
		submitShowLoading();
		document.getElementById("docType").disabled = false;
		document.getElementById("docNum").disabled = false;
		document.getElementById("txt_title").disabled = false;
		document.getElementById("custLastName").disabled = false;
		document.getElementById("custFirstName").disabled = false;
		document.getElementById("companyName").disabled = false;
		document.getElementById("DOB").disabled = false;
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
			async: false,
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert('error: '+textStatus); 	
			
				if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
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
				});
			}
		});
	}
	
	//Gary Part
	function csPortalCheck(){
		$.ajax({
			url : 'imscsportal.html?loginName=' + $("#loginName").val().replace("&", "*") + '&docType=' + $("#docType").val() + '&idDocNum=' + $("#docNum").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
			    }
				document.getElementById('warning_login').style.visibility = "hidden";
				document.getElementById('error_login').style.visibility = "hidden";
			},	
			success : function(msg) {
				$.each(eval(msg), function(i, item) {
					
					//alert(item.isCsPortalReg+" 23 "+document.getElementById('docType').value);
					
					if( document.getElementById('docType').value != "BS" && !(item.isCsPortalReg == null || item.isCsPortalReg == "null"))
					{
						//alert("item.isCsPortalReg:"+item.isCsPortalReg);							
						if(item.isCsPortalReg == "true"){								
							document.getElementById('isRegHKTLoginId').value = "A";
							//alert(document.getElementById('isRegHKTLoginId').value);
							document.getElementById('registeredMyHktLoginId').value = item.retrieveLoginId;
							////alert(item.retrieveLoginId);
						}else{
							document.getElementById('isRegHKTLoginId').value = "Y";
						}
						
						
						
						////alert("abc   " +document.getElementById('isRegHKTLoginId').value+ "   "+document.getElementById('docNum').value);	
						if(document.getElementById('isRegHKTLoginId').value=="A" || document.getElementById('isRegHKTLoginId').value=="a"){
							////alert("c");
							document.getElementById('myHktReg').style.display = "";////alert("6");
							document.getElementById('myHktReg4').style.display = "";
							document.getElementById('myHktReg1').style.display = "none";	
							document.getElementById('myHktReg2').style.display = "none";	
							document.getElementById('myHktReg3').style.display = "none";
							document.getElementById('registeredMyHktLoginId').disabled=true;
							document.getElementById('isRegHKTLoginId').value = "A";
							document.getElementById("hktLoginName").value = "";
							document.getElementById("hktMobileNum").value = "";	
							
							if (document.getElementById('registeredMyHktLoginId').value == "null" || document.getElementById('registeredMyHktLoginId').value == ""){
								document.getElementById('registeredMyHktLoginId').value = "";
								document.getElementById('registeredMyHktLoginId_err').style.display="";
								document.getElementById('registeredMyHktLoginId_errMsg').innerHTML="Cannot retrieve Login ID";
							}
							
							
						}else{//if (document.getElementById('isRegHKTLoginId').value=="Y"|| document.getElementById('isRegHKTLoginId').value=="y"){
							////alert("ab");
							document.getElementById('myHktReg').style.display = "";////alert("7");
							document.getElementById('myHktReg1').style.display = "";	
							document.getElementById('myHktReg2').style.display = "";	
							document.getElementById('myHktReg3').style.display = "";
							document.getElementById('myHktReg4').style.display = "none";
							document.getElementById('registeredMyHktLoginId_err').style.display = "none";
							document.getElementById('isRegHKTLoginIdBox').checked = true;
							document.getElementById('isRegHKTLoginId').value = "Y";
							document.getElementById('hktLoginName').disabled = false;
							document.getElementById('hktMobileNum').disabled = false;
						} 
						
						//document.getElementById('hktLoginName').value= document.getElementById("reservedId").value + "@netvigator.com";
						//document.getElementById('hktMobileNum').value= document.getElementById('mobileNum').value;
						
						
						if(document.getElementById('hktLoginName').value=="") {
							document.getElementById('hktLoginName').value= document.getElementById("reservedId").value + "@netvigator.com";
							hktLoginIdEdited = false;
							validHktLoginName();
						}
						else if(document.getElementById('hktLoginName').value.indexOf( "@netvigator.com")>=0 && !hktLoginIdEdited) {
							document.getElementById('hktLoginName').value= document.getElementById("reservedId").value + "@netvigator.com";
						}
						if(document.getElementById('hktMobileNum').value==""){
						document.getElementById('hktMobileNum').value= document.getElementById('mobileNum').value.substring(0,8);
						document.getElementById('hktMobileNum_error').style.display = "none";
						hktMobileNumEdited=false;
						}
						
						
					}else{//alert("here comes");
						//Gary control existence of CS portal in the page
						document.getElementById('myHktReg').style.display = "none";////alert("8");
						document.getElementById('myHktReg1').style.display = "none";	
						document.getElementById('myHktReg2').style.display = "none";	
						document.getElementById('myHktReg3').style.display = "none";
						document.getElementById('myHktReg4').style.display = "none";
						document.getElementById('registeredMyHktLoginId_err').style.display = "none";
						document.getElementById('isRegHKTLoginId').value = "X";
						document.getElementById("hktLoginName").value = "";
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
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
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
							document.getElementById('myHktReg').style.display = "none";
							document.getElementById('myHktReg1').style.display = "none";	
							document.getElementById('myHktReg2').style.display = "none";	
							document.getElementById('myHktReg3').style.display = "none";
							document.getElementById('myHktReg4').style.display = "none";
							isReserved=false;
							//Gary end
													
						}else if(item.isExist == true && item.loginName != item.oldLoginName && (item.loginName != null && item.loginName != "")){
							document.getElementById('warning_login').style.visibility = "visible";
							document.getElementById('warning_login_msg').innerHTML = "Please try " + item.hintOne + ", " + item.hintSecond + ", " + item.hintThird + "";
							document.getElementById('error_login').style.visibility = "visible";
							document.getElementById('error_login_msg').innerHTML = "Login ID & E-mail address already used";							
							//Gary control existence of CS portal in the page
							document.getElementById('myHktReg').style.display = "none";
							document.getElementById('myHktReg1').style.display = "none";	
							document.getElementById('myHktReg2').style.display = "none";	
							document.getElementById('myHktReg3').style.display = "none";
							document.getElementById('myHktReg4').style.display = "none";
							//Gary end
							
							isReserved=false;
							//window.tempEl = document.getElementById('loginName');
					        //setTimeout("window.tempEl.focus();",1);
						}else if(item.isExist == false){
							//reserve ok
							isReserved=true;
							document.getElementById("reservedId").value = item.loginName;
							document.getElementById("loginOK").style.display = "";							
							document.getElementById('warning_login').style.visibility = "hidden";
							document.getElementById('error_login').style.visibility = "hidden";
							////alert(item.isCsPortalReg);
							//Gary control existence of CS portal in the page
							
							if (firstReserve){
								document.getElementById('isRegHKTLoginId').value="Y";
								firstReserve = false;
							}
							//alert(document.getElementById('isRegHKTLoginId').value+"     "+item.isCsPortalReg );
							////alert("ddddddd");
							if(document.getElementById('docType').value != "BS" && !(item.isCsPortalReg == null || item.isCsPortalReg == "null")  ){
								////alert("def");							
								if(item.isCsPortalReg == "true"){								
									document.getElementById('isRegHKTLoginId').value = "A";
									////alert(document.getElementById('isRegHKTLoginId').value);
									document.getElementById('registeredMyHktLoginId').value = item.retrieveLoginId;
									////alert(item.retrieveLoginId);
								}
								////alert("item.isCsPortalReg"+item.isCsPortalReg);
								
								if (document.getElementById('isRegHKTLoginId').value=="Y"|| document.getElementById('isRegHKTLoginId').value=="y"){
									
									document.getElementById('myHktReg').style.display = "";
									document.getElementById('myHktReg1').style.display = "";	
									document.getElementById('myHktReg2').style.display = "";	
									document.getElementById('myHktReg3').style.display = "";
									document.getElementById('isRegHKTLoginIdBox').checked = true;
									document.getElementById('isRegHKTLoginId').value = "Y";
									document.getElementById('hktLoginName').disabled = false;
									document.getElementById('hktMobileNum').disabled = false;
									
								}else if(document.getElementById('isRegHKTLoginId').value=="A" || document.getElementById('isRegHKTLoginId').value=="a"){
									
									document.getElementById('myHktReg').style.display = "";
									document.getElementById('myHktReg4').style.display = "";
									document.getElementById('registeredMyHktLoginId').disabled=true;
									
									if (document.getElementById('registeredMyHktLoginId').value == "null" || document.getElementById('registeredMyHktLoginId').value == ""){
										document.getElementById('registeredMyHktLoginId').value = "";
										document.getElementById('registeredMyHktLoginId_err').style.display="";
										document.getElementById('registeredMyHktLoginId_errMsg').innerHTML="Cannot retrieve Login ID";
									}
																		
								}else if(document.getElementById('isRegHKTLoginId').value=="N" || document.getElementById('isRegHKTLoginId').value=="n"){
									isReserved = true;
									hktMobileNumEdited=true;
									document.getElementById("loginOK").style.display = "";	
									document.getElementById('myHktReg').style.display = "";
									document.getElementById('myHktReg1').style.display = "";	
									document.getElementById('myHktReg2').style.display = "";	
									document.getElementById('myHktReg3').style.display = "";
									document.getElementById('isRegHKTLoginIdBox').checked = false;
									document.getElementById('isRegHKTLoginId').value = "N";
									controlMyHKTRegistration();
								}
								
								//document.getElementById('hktLoginName').value= document.getElementById("reservedId").value + "@netvigator.com";
								//document.getElementById('hktMobileNum').value= document.getElementById('mobileNum').value;
								
								
								if(document.getElementById('hktLoginName').value=="") {
									document.getElementById('hktLoginName').value= document.getElementById("reservedId").value + "@netvigator.com";
									hktLoginIdEdited = false;
									validHktLoginName();
								}
								else if(document.getElementById('hktLoginName').value.indexOf( "@netvigator.com")>=0 && !hktLoginIdEdited) {
									document.getElementById('hktLoginName').value= document.getElementById("reservedId").value + "@netvigator.com";
								}
								if(document.getElementById('hktMobileNum').value==""){
								document.getElementById('hktMobileNum').value= document.getElementById('mobileNum').value.substring(0,8);
								document.getElementById('hktMobileNum_error').style.display = "none";
								hktMobileNumEdited=false;
								}
								
								//idReserved=true;
								//fillHktRegDetails();
							}else{
								document.getElementById('isRegHKTLoginId').value="X";								
							}
						}//Gary end
					}else if(item.valid == false){
						document.getElementById('error_login').style.visibility = "hidden";						
						document.getElementById('warning_login').style.visibility = "visible";
						document.getElementById('warning_login_msg').innerHTML = item.errorText;
						//Gary start
						document.getElementById('myHktReg').style.display = "none";
						document.getElementById('myHktReg1').style.display = "none";	
						document.getElementById('myHktReg2').style.display = "none";	
						document.getElementById('myHktReg3').style.display = "none";
						document.getElementById('myHktReg4').style.display = "none";
						isReserved=false;
						//Gary end
						//window.tempEl = document.getElementById('loginName');
				        //setTimeout("window.tempEl.focus();",1);
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
					document.getElementById('warning_pass_msg').innerHTML = "Passport number must include at least 6 characters.";
				}else if (docNum.match(patternString)){
					document.getElementById('warning_pass').style.display = "none";
					document.getElementById('warning_pass_msg').innerHTML = "";
				}else{
					document.getElementById('warning_pass').style.display = "";
					document.getElementById('warning_pass_msg').innerHTML = "Invalid Passport number.";
				}	
			}
		}else{
			document.getElementById('warning_pass').style.display = "none";
			document.getElementById('warning_pass_msg').innerHTML = "";
		}
		
		change2Business(doctype == "BS");
		isValid = false;	
		getImsCustInfoDS();
		//checkCreateCOrder();
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
		$.ajax({
			url : 'custinfo.html?docType=' + $("#docType").val() + '&idDocNum=' + $("#docNum").val() + '&loginName=' + $("#loginName").val(),
			type : 'POST',
			async: false,
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			//    alert(XMLHttpRequest+textStatus+errorThrown);
				if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
			    	}
			    else {
			    	alert(" Error in validating customer ("+document.getElementById('docType').value + "/" + document.getElementById('docNum').value+"), please try again");
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
				$.each(eval(msg), function(i, item) {	
					if(item.isValid == true){
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
						document.getElementById('mobileNum').value = item.mobile;
						document.getElementById('hktMobileNum').value = item.mobile.substring(0,8);//Gary
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
					}else if(item.isValid == false){ 
						document.getElementById('warning_hkid').style.display = "";
						document.getElementById('warning_hkid_msg').innerHTML = item.errorText;
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
				});
				if(count==0 && (idDocNum==null || idDocNum=="")){
					
				}else if(count==0){
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
				if (isReserved && isValid){
					
					csPortalCheck();
				}else{
					document.getElementById('myHktReg').style.display = "none";////alert("222");
					document.getElementById('myHktReg1').style.display = "none";	
					document.getElementById('myHktReg2').style.display = "none";	
					document.getElementById('myHktReg3').style.display = "none";
					document.getElementById('myHktReg4').style.display = "none";
					document.getElementById('registeredMyHktLoginId_err').style.display = "none";
					document.getElementById('isRegHKTLoginId').value = "N";
					document.getElementById("hktLoginName").value = "";
					document.getElementById("hktMobileNum").value = "";	
				}
				
			}
		});
	}

	function resetMsgs() {
		$("#existingCustomerConflictWithName").val("N");
		document.getElementById('warning_hkid').style.display = "none";
		document.getElementById('warning_hkid_msg').innerHTML = "";

		document.getElementById('optInfoButton').innerHTML = "<a href='#' class='nextbutton' type='button' onClick='optInfoCheck()'>Opt Out Info</a><br><br>"; 
	}
	
	function getImsCustInfoDS(){
		$.ajax({
			url : 'custinfo.html?docType=' + $("#docType").val() + '&idDocNum=' + $("#docNum").val() + '&loginName=' + $("#loginName").val(),
			type : 'POST',
			async: false,
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			//    alert(XMLHttpRequest+textStatus+errorThrown);
				if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
			    	}
			    else {
			    	alert(" Error in validating customer ("+document.getElementById('docType').value + "/" + document.getElementById('docNum').value+"), please try again");
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
 
				resetMsgs();

				if ($("#custLastName").attr("value").length>0 && $("#custFirstName").attr("value").length>0) {	//check only when both first name/ last name are input  
					$.each(eval(msg), function(i, item) {	
						if(item.isValid == true){
							 $("#isFromBOM").val("Y"); 
							var validNameCheck = $("#custLastName").attr("value") == item.lastName && $("#custFirstName").attr("value") == item.firstName; 
							
							if (validNameCheck) {
								$("#existingCustomerConflictWithName").val("N");
								document.getElementById('warning_hkid').style.display = "none";
								document.getElementById('warning_hkid_msg').innerHTML = "";
							} else {
								$("#existingCustomerConflictWithName").val("Y");
								document.getElementById('warning_hkid').style.display = "block";
								document.getElementById('warning_hkid_msg').innerHTML = "<spring:message code='ims.directsales.01' />";
							} 
							document.getElementById('warning_mobile').style.display = "none";
							document.getElementById('warning_mobile_msg').innerHTML = "";
							document.getElementById('warning_fixline').style.display = "none";
							document.getElementById('warning_fixline_msg').innerHTML = "";
							/*if(item.title=="MS" || item.title=="MISS" ){
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
							document.getElementById('mobileNum').value = item.mobile;
							document.getElementById('hktMobileNum').value = item.mobile.substring(0,8);//Gary
							document.getElementById('cntFixLineNum').value = item.fixLine;*/
							//Tony
							if(item.isExisting == true){
								document.getElementById('optInfoButton').innerHTML = "";
								$(".optInfo").css("display", "none");
								jsphasBBDialUp=true;//steven 20130711
								$("#hasBBDailUp").val("Y");
							}else{
								//$(".optInfoButton").css("display", "block");
								jsphasBBDialUp=false;//steven 20130711
								$("#hasBBDailUp").val("N");
							}
							//Tony End
							//document.getElementById('docType').disabled = true;
							//document.getElementById('docNum').disabled = true;
							$("#lob").val("IMS");
							$("#custNo").val(item.custNo);
							if(item.isImsBlacklist == true){
								$("#blacklistInd").val("Y");
								document.getElementById('warning_blacklist').style.display = "";
								/*if(item.j == 0){
									document.getElementById('warning_blacklist_msg').innerHTML = "Blacklist Customer<br>";
								}
								document.getElementById('warning_blacklist_msg').innerHTML = //"Blacklist Customer<br>" + 
									document.getElementById('warning_blacklist_msg').innerHTML + "Account No.: " + item.acctNo + " Outstanding Balance: " + item.osAmt + "<br>";*/
								document.getElementById('warning_blacklist_msg').innerHTML = "BL";
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
						}else if(item.isValid == false){ 
							document.getElementById('warning_hkid').style.display = "";
							document.getElementById('warning_hkid_msg').innerHTML = item.errorText;
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
					});
				}
				if(count==0 && (idDocNum==null || idDocNum=="")){
					
				}else if(count==0){
					/*document.getElementById('txt_title').selectedIndex = 0;
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
					document.getElementById('warning_fixline_msg').innerHTML = "";*/
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
				if (isReserved && isValid){
					
					csPortalCheck();
				}else{
					document.getElementById('myHktReg').style.display = "none";////alert("222");
					document.getElementById('myHktReg1').style.display = "none";	
					document.getElementById('myHktReg2').style.display = "none";	
					document.getElementById('myHktReg3').style.display = "none";
					document.getElementById('myHktReg4').style.display = "none";
					document.getElementById('registeredMyHktLoginId_err').style.display = "none";
					document.getElementById('isRegHKTLoginId').value = "N";
					document.getElementById("hktLoginName").value = "";
					document.getElementById("hktMobileNum").value = "";	
				} 
			}
		}); 
	}
	
	function mouseOver(rowNum){
 		document.getElementById(rowNum).style.backgroundColor = "#abd078";
 	}
 	
 	function mouseOut(rowNum){
 		document.getElementById(rowNum).style.backgroundColor = "";
 	}
 	
 	//Terrence
	function infoClear(){
		$("#lob").val("");
		$("#custNo").val("");
		$("#blacklistInd").val("N");
		document.getElementById('docNum').value = "";
		document.getElementById('custLastName').value = "";
		document.getElementById('custFirstName').value = "";
		document.getElementById('DOB').value = "";			
		document.getElementById('mobileNum').value = "";
		document.getElementById('cntFixLineNum').value = "";
		document.getElementById('companyName').value = "";
		document.getElementById('contactPersonName').value = "";
		document.getElementById('docType').selectedIndex = 0;
		document.getElementById('txt_title').selectedIndex = 0;
		if ( "${imsInstallationUI.isCC}" != "Y")
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
		document.getElementById('warning_id_and_name_miss_match').style.display = "none";
		document.getElementById('warning_id_and_name_miss_match_msg').innerHTML = ""; 
		//document.getElementById('optInfo').style.display="none"; //Tony
		$(".optInfo").css("display", "none");
		//Gary
		document.getElementById('myHktReg').style.display = "none";
		document.getElementById('myHktReg1').style.display = "none";	
		document.getElementById('myHktReg2').style.display = "none";	
		document.getElementById('myHktReg3').style.display = "none";
		document.getElementById('myHktReg4').style.display = "none";
		document.getElementById('registeredMyHktLoginId_err').style.display = "none";
		document.getElementById('isRegHKTLoginId').value = "N";
		document.getElementById("hktLoginName").value = "";
		document.getElementById("hktMobileNum").value = "";	
		
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
	
	function optInfoCheck(){
		var answer = confirm("Confirm to go to Opt Out Info section?");
		if (answer){
			<c:choose>
				<c:when test='${imsInstallationUI.isCC == "Y" or ims_direct_sales eq true}'>$(".CC").css("display", "");</c:when>
				<c:otherwise>$(".nonCC").css("display", "");</c:otherwise>
			</c:choose>
			<c:if test='${imsInstallationUI.optInInd == "Y"}'>
			$(".nonCC").find('input:checkbox').attr("disabled",true);
			$(".CC").find('input:checkbox').attr("disabled",true);
			</c:if>
		}else{
			
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
		if(document.getElementById('isRegHKTLoginId').value == "Y" || document.getElementById('isRegHKTLoginId').value == "y"){									
			////alert(document.getElementById('isRegHKTLoginId').value+"\n"+document.getElementById('hktLoginName').value+"\n"+document.getElementById('hktMobileNum').value);
			
			isReserved = true;
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
			
		}else if(document.getElementById('isRegHKTLoginId').value == "A" || document.getElementById('isRegHKTLoginId').value == "a"){
			
			isReserved = true;
			document.getElementById("loginOK").style.display = "";	
			document.getElementById('myHktReg').style.display = "";
			document.getElementById('myHktReg4').style.display = "";
			csPortalCheck();
			
		}else if (document.getElementById('isRegHKTLoginId').value == "N" || document.getElementById('isRegHKTLoginId').value == "n"||document.getElementById('isRegHKTLoginId').value == "X" || document.getElementById('isRegHKTLoginId').value == "x"){
			
			isReserved = true;
			hktMobileNumEdited=true; 
			if (document.getElementById("loginName")!=null && document.getElementById("loginName").length > 0)  //jacky 20141106
			document.getElementById("loginOK").style.display = "";
			var docNumPattern =new RegExp(".*[\\d].*[\\d].*[\\d].*[\\d].*[\\d].*[\\d].*");
			if ( docNumPattern .test(document.getElementById('docNum').value) && document.getElementById('docType').value != "BS"){
						
				document.getElementById('myHktReg').style.display = "";
				document.getElementById('myHktReg1').style.display = "";	
				document.getElementById('myHktReg2').style.display = "";	
				document.getElementById('myHktReg3').style.display = "";
				document.getElementById('isRegHKTLoginIdBox').checked = false;
				document.getElementById('isRegHKTLoginId').value = "N";
			}
			controlMyHKTRegistration();
			
			
		}
		//Gary end

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
		if(document.getElementById('isRegHKTLoginIdBox').checked == true){
			document.getElementById('hktLoginName').disabled = false;
			document.getElementById('hktMobileNum').disabled = false;
			document.getElementById('isRegHKTLoginId').value = "Y";
		}	
		else{
			document.getElementById('hktLoginName').disabled = true;
			document.getElementById('hktMobileNum').disabled = true;
			document.getElementById('hktMobileNum_warning').style.display = "none";
			document.getElementById('hktLoginName_warning').style.display = "none";
			if(document.getElementById('isRegHKTLoginId').value != "A"){
				document.getElementById('isRegHKTLoginId').value = "N";
			}
		}
	}
	
	function validHktMobileNo() {
		hktMobileNumEdited=true;
		var hktMobileNum = $("#hktMobileNum").val();
		var hktMobilePattern=/[569][0-9][0-9][0-9][0-9][0-9][0-9][0-9]/;
		if (hktMobileNum.match(hktMobilePattern)){
			document.getElementById('hktMobileNum_warning').style.display = "none";
			document.getElementById('hktMobileNum_warning_msg').innerHTML = "";
			document.getElementById('hktMobileNum_error').style.display = "none";
			document.getElementById('hktMobileNum_error_msg').innerHTML = "";
		}else if(hktMobileNum==""){
			document.getElementById('hktMobileNum_error').style.display = "";
			document.getElementById('hktMobileNum_error_msg').innerHTML = "Please input mobile no.";
			document.getElementById('hktMobileNum_warning').style.display = "none";
			document.getElementById('hktMobileNum_warning_msg').innerHTML = "";
		}else{
			document.getElementById('hktMobileNum_warning').style.display = "";
			document.getElementById('hktMobileNum_warning_msg').innerHTML = "Invalid Mobile number.";
			document.getElementById('hktMobileNum_error').style.display = "none";
			document.getElementById('hktMobileNum_error_msg').innerHTML = "";
		}
		/*if(document.getElementById('hktMobileNum').value != document.getElementById("mobileNum").value){
			hktMobileNumEdited=true;
		}else {
			hktMobileNumEdited=false;
		}*/
	}
	
	
	function validHktLoginName() {
		//hktLoginIdEdited=true;
		var hktLoginName = $("#hktLoginName").val();
		var hktLoginNamePattern= /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var result = hktLoginNamePattern.test(hktLoginName);
		if (result){
			document.getElementById('hktLoginName_warning').style.display = "none";
			document.getElementById('hktLoginName_warning_msg').innerHTML = "";
			document.getElementById('hktLoginName_error').style.display = "none";
			document.getElementById('hktLoginName_error_msg').innerHTML = "";
		}else if(hktLoginName==""){
			document.getElementById('hktLoginName_error').style.display = "";
			document.getElementById('hktLoginName_error_msg').innerHTML = "Please input My HKT Login ID.";
			document.getElementById('hktLoginName_warning').style.display = "none";
			document.getElementById('hktLoginName_warning_msg').innerHTML = "";
		}else{
			document.getElementById('hktLoginName_warning').style.display = "";
			document.getElementById('hktLoginName_warning_msg').innerHTML = "Invalid Login Name format.";
			document.getElementById('hktLoginName_error').style.display = "none";
			document.getElementById('hktLoginName_error_msg').innerHTML = "";
		}	
		if(document.getElementById('hktLoginName').value != document.getElementById("loginName").value+"@netvigator.com"){
			hktLoginIdEdited=true;
		}/*else {
			hktLoginIdEdited=false;
		}*/
	}
	
	
		//Gary's part end
		
	//STATR AJAX
	$(function() {
		$.ajax({
			url : "imsaddressdistrictlookup.html?T=AREA",
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
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
				        alert("Your session has been timed out, please re-login."); 
				    }else if(textStatus == 'timeout'){
				    	alert("Server response timeout, please try again.");
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
				        alert("Your session has been timed out, please re-login."); 
				    }else if(textStatus == 'timeout'){
				    	alert("Server response timeout, please try again.");
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
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
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
						var answer = confirm("Cannot match PCCW fixed line with same installation address. Clear input?");
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
				        alert("Your session has been timed out, please re-login."); 
				    }else if(textStatus == 'timeout'){
				    	alert("Server response timeout, please try again.");
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
							}else if(!hktMobileNumEdited)
								document.getElementById('hktMobileNum').value=document.getElementById('mobileNum').value.substring(0,8);
							//fillHktRegDetails();//Gary
						}else if(item.valid == false){
							document.getElementById('warning_mobile').style.display = "";
							document.getElementById('warning_mobile_msg').innerHTML = "Invalid mobile number";
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
		else if ( document.getElementById('warning_mobile_msg').innerHTML == "Invalid mobile number")
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
				document.getElementById('warning_fixline_msg').innerHTML = "Invalid fixed line no.";
			}
			else if($("#cntFixLineNum").val().match(regex)==null)
			{
				document.getElementById('warning_fixline').style.display = "";
				document.getElementById('warning_fixline_msg').innerHTML = "Invalid fixed line no.";
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
	});
	

	function openDialog1AMS(){
		//var serbdyno1ams = document.getElementById('installAddress.serbdyno').value;
		//var floorNo1ams = document.getElementById('installAddress.floorNo').value.trim();
		//var flatNo1ams = document.getElementById('installAddress.unitNo').value.trim();
		//var hiLotNo1ams = document.getElementById('installAddress.hiLotNo').value.trim();
		
		//alert(serbdyno1ams);
		//alert(floorNo1ams);
		openModalDialog("ims1ams.html?dialogMode=true", "1AMS Search");
		
	}
	
</script> 

<table width="100%" height="30">
	<tr>
		<td class="contenttextgreen" align="center">
		 	PCD Acquisition
		</td>
	</tr>
</table>

<link href="css/imsds.css" rel="stylesheet" type="text/css">
<%@ include file="/WEB-INF/jsp/imssubheader.jsp" %>

<form:form name="imsinstallationForm" method="POST" commandName="imsInstallationUI">
<br><br>
<table width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="0" rules="none">
		<tr>
			<td class="table_title" >Customer Info./ Installation Address/ Login</td>
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
		<tr>
			<td class="table_title" colspan="8">Customer Info.</td>
		</tr>
		<!-- Terrence Part.. + form:.. + path.. mod. c:disabled rm value PARTLY[NOT COMPLETE] -->
		<tr>
			<td><div align="left">ID DOC TYPE:</div></td>
			<td>
				<div align="left">
				<c:choose>
					<c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
						<form:select id="docType" path="idDocType" disabled="true" onchange="ValidateIDNUM()">
							<form:option value="HKID" label="HKID" >HKID</form:option>
							<form:option value="PASS" label="Passport" >Passport</form:option>
							<c:if test='${imsInstallationUI.isCC == "Y" || ims_direct_sales eq true}'>
								<form:option value="BS" label="HKBR" >HKBR</form:option>
							</c:if>
						</form:select>
					</c:when>
					<c:otherwise>
						<form:select id="docType" path="idDocType" disabled="false" onchange="ValidateIDNUM()">
							<form:option value="HKID" label="HKID" >HKID</form:option>
							<form:option value="PASS" label="Passport" >Passport</form:option> 
							<c:if test='${imsInstallationUI.isCC == "Y" || ims_direct_sales eq true}'> 
								<form:option value="BS" label="HKBR" >HKBR</form:option>
							</c:if>
						</form:select>
					</c:otherwise>
				</c:choose>
				</div>
			</td>
			<td align="left">ID DOC NUM:</td>
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
			
			<td <c:if test='${imsInstallationUI.isCC == "Y"}'>style="visibility:hidden"</c:if>>
				<form:checkbox id="idVerified" path="idVerified" value="Y" />Verified
			</td>
			<td></td>
			<c:choose>
			<c:when test='${imsInstallationUI.approvalRequested == "Y"}'>
			<td align="right"><a href='#' id="clear" class='nextbutton'>Clear</a></td>
			</c:when>
			<c:otherwise>
			<td align="right"><a href='#' id="clear" class='nextbutton' onclick='infoClear()'>Clear</a></td>
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
			<td align="left">Title:</td>
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
						<form:option value="MR" label="MR">MR</form:option>
						<form:option value="MS" label="MS">MS</form:option>
					</form:select>
				</c:otherwise>
			</c:choose>
			</td>
			
			<td align="left">Family Name:</td>
			<td>
			<c:choose>
				<c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
					<form:input id="custLastName" path="lastName" maxlength="40" disabled="true" onkeyup="keyUpOnCustLastName()"/>
				</c:when>
				<c:otherwise>
					<form:input id="custLastName" path="lastName" maxlength="40" disabled="false" onkeyup="keyUpOnCustLastName()"/>
				</c:otherwise>
			</c:choose>
			</td>
			<td align="left" width="10%">Given Name:</td>
			<td>
			<c:choose>
				<c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
					<form:input id="custFirstName" path="firstName" maxlength="40" disabled="true" onkeyup="keyUpOnCustFirstName()"/>
				</c:when>
				<c:otherwise>
					<form:input id="custFirstName" path="firstName" maxlength="40" disabled="false" onkeyup="keyUpOnCustFirstName()"/>
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
			<td align="left">Company name:</td>
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
			
			<td align="left">Contact Person name:</td>
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
			>Date Of Birth:</td>
			<td class="BirthCol" 
			<c:if test='${imsInstallationUI.idDocType == "BS"}'>style="display:none"</c:if>
			>
				<c:choose>
					<c:when test='${imsInstallationUI.dob != null}'>
						<c:choose><c:when test='${imsInstallationUI.lob == "IMS" and ims_direct_sales ne true}'>
						<form:input id="DOB" path="dobStr" disabled="true" readonly="true"/>
						</c:when>
						<c:otherwise>
						<form:input id="DOB" path="dobStr" disabled="false" readonly="true"/>
						</c:otherwise></c:choose>
					</c:when>
					<c:otherwise>
						<form:input id="DOB" path="dobStr" disabled="false" readonly="true"/>
					</c:otherwise>
				</c:choose>
				<!-- Terrence rm type="text" -->
			</td>
			
			
			<!-- Terrence Part -->
			<td align="left">Mobile no.:</td>
			<td>
				<form:input id="mobileNum" path="contact.contactPhone" maxlength="8" onblur="checkMobile()"/>
			</td>
			<td align="left">Fixed line no.:</td>
			<td><form:input id="cntFixLineNum" path="contact.otherPhone" maxlength="8" onblur="checkFixLine()"/></td>
		  
		</tr>
		<tr>
		<td colspan="2" class="BirthCol"
		<c:if test='${imsInstallationUI.idDocType == "BS"}'>style="display:none"</c:if>
		><form:errors path="dobStr" cssClass="error" /></td>
		<td colspan="3"><form:errors path="contact.contactPhone" cssClass="error" /></td>
		</tr>
 		<c:choose>
			<c:when test='${imsInstallationUI.installAddress.addrInventory.n2BuildingInd == "Y"}'>
				<c:choose>
				<c:when test='${imsInstallationUI.installAddress.addrInventory.technology != "PON"}'>
				<tr>
					<td align="left" colspan="4">Existing PCCW Fixed Line No.:
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
		<td colspan ="4">
			<div id="warning_id_and_name_miss_match" class="ui-widget" style="display:none">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_id_and_name_miss_match_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
		</td>
		</tr>
		
		<tr>
			<td colspan="7" align="right">
			<div id="optInfoButton">
				<c:choose>
					<c:when test='${imsInstallationUI.hasBBDailUp == "Y"}'>

					</c:when>
					<c:otherwise>
						<a href='#' class='nextbutton' type='button' onClick='optInfoCheck()'>Opt Out Info</a><br><br>
					</c:otherwise>
				</c:choose>
			</div>
			</td>
		</tr>
		
		<tr>
			<td colspan="7">
				<div class="optInfo nonCC" style="display:none">
					<table width='100%'><tr class='table_title'><td align='center' width='10%'>Direct Mailing</td>
					<td align='center' width='10%'>Outbound</td><td align='center' width='10%'>SMS</td>
					<td align='center' width='10%'>Email</td>
					<td align='center' width='10%'>Web Bill</td>
					<td align='center' width='10%'>Non-Sales SMS</td>
					<td align='center' width='10%'>Online Portal</td></tr>
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
							<td align='center' width='80%'>Opt Out All</td>
						</tr>
						<tr>
							<td align='center'><input type='checkbox' id='AllOpt' value="Y" onClick="checkOptOut()"/></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>

		<tr><td class="table_title" colspan="7">Login ID & Email Address Registration</td></tr>
		<tr>
		<td align="left">Login ID & E-mail address:</td>
		<td>
<%-- 			<form:input maxlength="15" id="loginName" path="loginId" onblur="loginNameCheck()"/>@netvigator.com			 --%>
			<form:input maxlength="15" id="loginName" path="loginId" cssStyle="text-transform:lowercase;"/>@netvigator.com
			<input type="hidden" id="reservedId" value="${imsInstallationUI.loginId}"/>
		</td>
		<td align="left" colspan="3">
			<a href="#" class="nextbutton" onClick="loginNameCheck()">Reserve</a>
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
						Confirm reserved
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

<!--Gary CS Portal-->
		<tr><td class="table_title " id="myHktReg" colspan="7" style="display:none">My HKT Registration</td></tr>
		<tr id='myHktReg1' style="display:none">
		<td colspan="2">
			<input type="checkbox"   id='isRegHKTLoginIdBox'   onclick="controlMyHKTRegistration()"/> Customer agreed to register My HKT account
			<form:hidden  path="isRegHKTLoginId" ></form:hidden>
		</td>
		</tr >
		<tr id='myHktReg2' style="display:none">
		<td align="left">My HKT Login ID:</td>		
			<td>
				<form:input size="40" maxlength="40" id="hktLoginName" path="hktLoginId" onchange="validHktLoginName()"/>				
			</td>
			
			<td colspan ="3">
					<div id="hktLoginName_error"  style="display:none">					
							<p>						
							<div class="error" id="hktLoginName_error_msg" ></div>
							</p>					
					</div>
			</td>
			
		</tr>
		<tr><td><form:errors path="hktLoginId" cssClass="error" /></td></tr>
		
		<tr id='myHktReg3' style="display:none">
			<td align="left">Mobile Contact no.:</td>
			<td>
				<form:input  id="hktMobileNum" path="hktMobileNum" maxlength="8"  onchange="validHktMobileNo()"  />
			</td>
			<td colspan ="3">
					<div id="hktMobileNum_error"  style="display:none">					
							<p>						
							<div class="error" id="hktMobileNum_error_msg" ></div>
							</p>					
					</div>
			</td>
		</tr>
		 <tr><td colspan=3><form:errors path="hktMobileNum" cssClass="error" /></td></tr>
		<tr id='myHktReg4' style="display:none">
			<td align="left">Registered My HKT Login ID:</td>
			<td >
				<input  id="registeredMyHktLoginId" size="40"/>
				
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
		
		<tr> <td>&nbsp;</td></tr>
		<tr> <td>&nbsp;</td></tr>
<!-- Gary end-->	

		

		<!-- Table part 2, Installation Address -->
		<tr><td class="table_title" colspan="7">Installation Address</td></tr>
		<tr height="30px">
			<td align="left" >Flat/Room:</td>
			<td><div id="flat" >${imsInstallationUI.installAddress.unitNo}</div></td>
			<td align="left">Floor:</td>
			<td><div id="floor" >${imsInstallationUI.installAddress.floorNo}</div></td>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr height="30px">
			<td align="left">Lot No.:</td>
			<td><div id="lotNum" >${imsInstallationUI.installAddress.hiLotNo}</div>
			</td>
			<td align="left">Building/Estate:</td>
			<td colspan="3" align="left">
				<div id="buildingName" >${imsInstallationUI.installAddress.buildNo}</div>
			</td>
		</tr>
		<tr height="30px">
			<td align="left">Street No.:</td>
			<td><div id="streetNum" >${imsInstallationUI.installAddress.strNo}</div>
			</td>
			<td align="left">Street Name:</td>
			<td><div id="streetName" >${imsInstallationUI.installAddress.strName}</div>
			</td>
			<td align="left">Street Category:</td>
			<td><div id="streetCategory">${imsInstallationUI.installAddress.strCatDesc}</div>
			</td>
		</tr>
		<tr height="30px">
			<td align="left" valign="top">Section:</td>
			<td valign="top">
				<div id='section'>${imsInstallationUI.installAddress.sectDesc}</div>
			</td>
			<td align="left" valign="top">District:</td>
			<td valign="top">
				<div id="district">${imsInstallationUI.installAddress.distDesc}</div>
			</td>

			<td align="left" valign="top">Area:</td>
			<td valign="top">
				<div id="area">${imsInstallationUI.installAddress.areaDesc}</div>
			</td>
		</tr>
		<!-- End Installation Address -->

		<!-- Billing Address -->
		<tr>
			<td class="table_title" colspan="7">Billing & Correspondence Address &nbsp &nbsp
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
			Same with Installation Address
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
			Quick Search:</div></th>
			<td colspan="5">
				<div id="quickSearchInput">
					<form:input size="100%" id="billingQuickSearch" path="billingQuickSearch" onkeyup="keyUpOnBillingQuickSearch()" onclick="setCurrentSearchFrom('billingQuickSearch')" />
				</div>
			</td>
			<!-- <input id="quicksearch" name="quicksearch" size="100%" onClick="setCurrentSearchFrom('quickSearch')"/></td> -->
			<!-- onFocus="checkaddr()" -->
			<c:choose>
				<c:when test='${imsInstallationUI.approvalRequested == "Y"}'>
					<td colspan="1" align="right"><div id="quickSearchClear"><a href='#' class='nextbutton'>Clear</a></div></td>
				</c:when>
				<c:otherwise>
					<td colspan="1" align="right"><div id="quickSearchClear"><a href='#' class='nextbutton' onclick='allClear()'>Clear</a></div></td>
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
			Quick Search:</div></th>
			<td colspan="5">
			<div id="quickSearchInput" style="display:none">
			<form:input size="100%" id="billingQuickSearch" path="billingQuickSearch" onkeyup="keyUpOnBillingQuickSearch()" onclick="setCurrentSearchFrom('billingQuickSearch')" />
			</div></td>
			<!-- <input id="quicksearch" name="quicksearch" size="100%" onClick="setCurrentSearchFrom('quickSearch')"/></td> -->
			<!-- onFocus="checkaddr()" -->
			<td colspan="1" align="right"><div id="quickSearchClear" style="display:none"><a href='#' class='nextbutton' onclick='allClear()'>Clear</a></div></td>
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
					<td align="left">Flat/Room:</td>
					<td><form:input maxlength="5" id="billingFlat" path="billingAddress.unitNo" onkeyup="keyUpOnBillingFlat()"/></td>
					<td align="left">Floor:</td>
					<td><form:input maxlength="3" id="billingFloor" path="billingAddress.floorNo" onkeyup="keyUpOnBillingFloor()"/></td>
					<td colspan="2">&nbsp;</td>
				</tr>
 				<tr height="30px">
					<td align="left">Lot No.:</td>
					<td><form:input maxlength="8" id="billingLotNum" path="billingAddress.hiLotNo" onkeyup="keyUpOnBillingLotNum()"/></td>
					<td align="left">Building/Estate:</td>
					<td colspan="3" align="left">
						<form:input maxlength="30" size="60%" id="billingBuildingName" path="billingAddress.buildNo" onkeyup="keyUpOnBillingBuildingName()"/>
					</td>
				</tr>
				<tr height="30px">
					<td align="left">Street No.:</td>
					<td><form:input maxlength="11" id="billingStreetNum" path="billingAddress.strNo" onkeyup="keyUpOnBillingStreetNum()"/></td>
					<td align="left">Street Name:</td>
					<td><form:input maxlength="23" id="billingStreetName" path="billingAddress.strName" onkeyup="keyUpOnBillingStreetName()"/></td>
					<td align="left">Street Category:</td>
					<td><form:input id="billingStreetCategory" path="billingAddress.strCatDesc" onkeyup="keyUpOnBillingStreetCategory()"/></td>
					<form:hidden path="billingAddress.strCatCd"/>
				</tr>
				<tr height="30px">
				
					<td align="left" valign="top">Section:</td>
					<td valign="top">
					<select id='billingSectionCodeSelect' style="width: 150px;" ></select>
					<form:input path="billingAddress.sectDesc" />
					<form:hidden path="billingAddress.sectCd" />
					<div id='loaderImagePlaceholder'></div>
					</td>
					<td align="left" valign="top">District:</td>
					<td>
					<select id='billingDistrictCodeSelect'	style="width: 150px;"></select> 
					<form:input path="billingAddress.distDesc"	/>
					<form:hidden path="billingAddress.distNo" /> 
					<div id='loaderImagePlaceholder'></div>
					</td>
					<td align="left" valign="top">Area:</td>
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
					<td align="left">Flat/Room:</td>
					<td><form:input maxlength="5" id="billingFlat" path="billingAddress.unitNo" onkeyup="keyUpOnBillingFlat()"/></td>
					<td align="left">Floor:</td>
					<td><form:input maxlength="3" id="billingFloor" path="billingAddress.floorNo" onkeyup="keyUpOnBillingFloor()"/></td>
					<td colspan="2">&nbsp;</td>
				</tr>
 				<tr height="30px">
					<td align="left">Lot No.:</td>
					<td><form:input maxlength="8" id="billingLotNum" path="billingAddress.hiLotNo" onkeyup="keyUpOnBillingLotNum()"/></td>
					<td align="left">Building/Estate:</td>
					<td colspan="3" align="left">
						<form:input maxlength="30" size="60%" id="billingBuildingName" path="billingAddress.buildNo" onkeyup="keyUpOnBillingBuildingName()"/>
					</td>
				</tr>
				<tr height="30px">
					<td align="left">Street No.:</td>
					<td><form:input maxlength="11" id="billingStreetNum" path="billingAddress.strNo" onkeyup="keyUpOnBillingStreetNum()"/></td>
					<td align="left">Street Name:</td>
					<td><form:input maxlength="23" id="billingStreetName" path="billingAddress.strName" onkeyup="keyUpOnBillingStreetName()"/></td>
					<td align="left">Street Category:</td>
					<td><form:input id="billingStreetCategory" path="billingAddress.strCatDesc" onkeyup="keyUpOnBillingStreetCategory()"/></td>
					<form:hidden path="billingAddress.strCatCd"/>
				</tr>
				<tr height="30px">
				
					<td align="left" valign="top">Section:</td>
					<td valign="top">
					<select id='billingSectionCodeSelect' style="width: 150px;" ></select>
					<form:input path="billingAddress.sectDesc" />
					<form:hidden path="billingAddress.sectCd" />
					<div id='loaderImagePlaceholder'></div>
					</td>
					<td align="left" valign="top">District:</td>
					<td>
					<select id='billingDistrictCodeSelect'	style="width: 150px;"></select> 
					<form:input path="billingAddress.distDesc"	/>
					<form:hidden path="billingAddress.distNo" /> 
					<div id='loaderImagePlaceholder'></div>
					</td>
					<td align="left" valign="top">Area:</td>
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
			</table>
		<!-- end mesage -->
		<tr>
			<td colspan="9" align="right">
				<div>
					<a href="#" class="nextbutton" onClick="formSubmit()">Continue</a>
				</div>
				<input type="hidden" name="currentView" value="imsinstallation"/>
			</td>
		</tr>
		</table>	     
      </td>
  </tr>
</table>
<form:hidden id="createCOrderInd" path="createCOrderInd"/>
<form:hidden id="relatedFSA" path="relatedFSA"/>
<form:hidden path="existingCustomerConflictWithName"/>
<input type="hidden" id="imsSearchFrom" value="" />
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>