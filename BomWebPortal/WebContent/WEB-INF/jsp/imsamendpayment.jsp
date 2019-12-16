<%@ include file="header.jsp" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<%@ include file="imsloadingpanel.jsp" %>
<%-- <%@ include file="imsvalidwindow.jsp" %> --%>
<%@ page import="java.util.*" %>
<script language="Javascript">

$( function() {
	
	$("#appMethod").change(
		function(){
			$("#appMethodDesc").get(0).selectedIndex = this.selectedIndex;
		}
	);
});

function expiryDateCtrl()//Gary
{
	var d = new Date();
	iMonth = (1+d.getMonth())+ 2;
	iYear = d.getFullYear();
	if (iMonth>12){
		iMonth=iMonth-12;
		iYear=iYear+1;
	}
	
	for (var i=1;i<13;i++){
		if(iYear==parseInt(document.getElementById("expiryYear").value) && i<=iMonth){
			document.getElementById("epy"+i).disabled = true;
			if(parseInt(document.getElementById("expiryMonth").value)==i)
				document.getElementById("expiryMonth").value="";
		}else{
			document.getElementById("epy"+i).disabled = false;
		}
	}
		 return iYear;
}

	function formSubmit(actiontype){
		//alert($("#year").val());
		//alert($("#month").val());
		//window.location = "payment.html";
		OTCChange();
		//alert($("#installInstalmentInd").val());
		
		
		
		if(actiontype=="refresh"){
			$("#submitTag").val("U");
			errorCodeText();
			return;
		}
		
		errorCheckInstallment();
		
		if(document.getElementById("thirdparty").checked == true && document.getElementById("warning_hkid").style.display == ""){
			
//		}else if(document.getElementById("warning_cardholder").style.display == ""){
		
		}else if(document.getElementById("warning_email").style.display == ""){
		
		}else if(document.getElementById("cashPayRequest").style.display == ""
				&& document.getElementById("cashPayRequestBtn").style.display == ""){
			alert("Marketing approval is needed. Please submit the order for approval.");
//		}else if($("#orderActionInd").val() == "W" && $("#orderStatus").val() == "30"){
//			alert("Waiting marketing approval.");
//		}else if($("#orderActionInd").val() == "W" && $("#orderStatus").val() == "32"){
//			alert("Marketing approval is rejected.");
		}else if(document.getElementById("waivePrepay").checked==true && "${imsPaymentUI.waiveApproval}"!="Y"){			
			alert("Marketing approval is needed. Please submit the order for approval.");					
		}else if(document.getElementById("waiveOTC").checked==true && "${imsPaymentUI.waiveOtApproval}"!="Y"){			
			alert("Marketing approval is needed. Please submit the order for approval.");					
		}else if(document.getElementById("DiscountOTC").checked==true && "${imsPaymentUI.discountOTCApproval}"!="Y"){			
			alert("Marketing approval is needed. Please submit the order for approval.");					
		}else{
			submitShowLoading();		
		//if($("#orderActionInd").val() == "W" && $("#orderStatus").val() == "31"){
		if($("#orderActionInd").val() == "W" && "${imsPaymentUI.approvalRequested}" == "Y"){
			$(":input").attr("disabled", false);  
		}		
		if(document.getElementById("creditCard").checked == true){
			$("#payMtdType").val($("#creditCard").val());
			$("#processCreditCard").val("P");
			
			if ( $("#expiryMonth").val() != "" )
				$("#ccExpDate").val($("#expiryMonth").val()+"/"+$("#expiryYear").val());
		}
		if(document.getElementById("waivePrepay").checked == true){
			$("#processCreditCard").val(null);
		}
		if(document.getElementById("cash").checked == true){
			$("#processCreditCard").val(null);
			$("#payMtdType").val($("#cash").val());
		}
		//alert($("#payMtdType").val());
		if($("#payMtdType").val() == "C"){
			if(document.getElementById("thirdparty").checked == true){
				$("#thirdPartyInd").val($("#thirdparty").val());
			}
			if(document.getElementById("self").checked == true){
				$("#thirdPartyInd").val($("#self").val());
			}
			//alert($("#thirdPartyInd").val());
		}
		//if($("#thirdPartyInd").val() == "Y"){
			//$("#ccIdDocType").val($("#cardholderdoctype").val());
			//alert($("#ccIdDocType").val());
			//$("#ccIdDocNo").val($("#cardholderdocnum").val());
			//alert($("#ccIdDocNo").val());
		//}
		if(document.getElementById("email").checked == true){
			$("#billMedia").val($("#email").val());
		}
		if(document.getElementById("paper").checked == true){
			$("#billMedia").val($("#paper").val());
		}
		//alert($("#billMedia").val());
		if($("#payMtdType").val() == "M"){
			if(document.getElementById("fsPrepay").checked == true){
				$("#cashFsPrepay").val("Y");
			}else{
				$("#cashFsPrepay").val("N");
			}
			//alert($("#cashFsPrepay]").val());
		}
		if(document.getElementById("ccVerified").checked == true){
			$("#ccVerified").val("Y");
		}else{
			$("#ccVerified").val("N");
		}
		
		if ( "${imsPaymentUI.isCC}" == "Y" )
		{
			$("#ccVerified").val("Y");
			$("#ccVerified").attr("checked",true);
		}
	
		//alert($("#cashPrepay").val());
		if(document.getElementById("waivePrepay").checked == true){
			$("#waivedPrepay").val("Y");
		}
		document.getElementById("sourcecode").disabled = false;
		document.getElementById("staffnum").disabled = false;
		$("#submitTag").val("CS");
		errorCodeText();
		}
		
		
	}
		
	

	
	function errorCheckInstallment(){
		//alert("abc");
	
		if( "${imsPaymentUI.isValidForInstallInstallment}" == "Y" && "${imsPaymentUI.onlyOneInstallInstallmentPlanInd}" == "N" && $("#installInstalmentInd").val() == "Y" ){		
			//alert("aaa");
			var imsInstallmentPlanSelected = document.getElementById('imsInstallmentPlanSelection').selectedIndex;
			if(imsInstallmentPlanSelected == '0'){
			//	alert("cde");
				document.getElementById("installmentError").style.display="";
				hold();	
			}else return;
		}else return;	
	}
	
	
	
	
	function hold(){
		hold();
	}
	
	//Anthony
	function IOA(ioa)
	{
		if ( ioa == "I")
		{
			$(".installation").show();
			$(".activation").hide();
			
		}
		else if ( ioa == "A" )
		{
			$(".activation").show();
			$(".installation").hide();
		}
		else
		{
			$(".installation").show();
			$(".activation").hide();
		}
	}
	
	
	
	function selectPlan(){
		
		if (document.getElementById('imsInstallmentPlanSelection').selectedIndex != '0'){
		document.getElementById("OTC").checked = false;
		document.getElementById("OTCC").checked = true;
		}
	}
		
	//Terrence Part
	function ceksFormSubmit() {
		document.paymentForm.ceksSubmit.value = "Y";
		errorCodeText();
	}
	
	function openCEKSWindow(){
		//var form = document.forms['createForm'];
		var ceksLink = "imsceks.html";
		if (ceksLink != null && ceksLink != "") {
			window.open(ceksLink, "_blank", "width=550, height=400, resizable=no, toolbar=yes, location=yes, menubar=yes, status=yes, left=100, top=100");
			//form.elements['ceksUrl'].value = "";
		}
	}
	
	function refreshForm(cardnum){
		if(cardnum.substring(0, 1)=="4"){
			document.getElementById('cardtype').selectedIndex = 1;
		}else if(cardnum.substring(0, 2)=="51" || cardnum.substring(0, 2)=="52"
				 || cardnum.substring(0, 2)=="53" || cardnum.substring(0, 2)=="54"
				 || cardnum.substring(0, 2)=="55"){
			document.getElementById('cardtype').selectedIndex = 2;
		}else if(cardnum.substring(0, 2)=="34" || cardnum.substring(0, 2)=="37"){
			document.getElementById('cardtype').selectedIndex = 3;
		}else{
			document.getElementById('cardtype').selectedIndex = 0;
		}
		document.getElementById('cardnum').value = cardnum;
	}
	
	/*
	function inputCreditCardNum(){
		document.getElementById('cardnum').disabled = false;
	}
	*/
	
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

	
	
	function approve(request){
		//var suspend = document.getElementById('suspendSelect').value;
		//if (suspend == ""){
		//	alert("Please select a suspend reason code!");
		//}else{
			var subtype;
			
			if(request=="cashpay"){
				subtype = "R";
			}else if(request=="waiveprepay"){
				if(document.getElementById("waivePrepay").checked==false){
					alert("please select the option \"Waive prepayment\"");
					return;
				}else if($("#idDocType").val() == "PASS" && "${imsPaymentUI.cashApproval}" != "Y"){
					alert("please submit \"Use Cash payment\" approval first");
					return;
				}
				subtype = "W";
			}else if(request=="waiveot"){
				if(document.getElementById("waiveOTC").checked==false){
					alert("please select the option \"Waive One-time Charge\"");
				return;
				}
				subtype = "WO";
			}else if(request=="discountedot"){
				if(!document.getElementById("DiscountOTC").checked){
					alert("please select the option \"Discounted " + 
							<c:choose>
								<c:when test='${imsPaymentUI.otChrgType == "A"}'>"Activation " + </c:when>
								<c:otherwise>"Installation " + </c:otherwise>
							</c:choose>
							"Charge\"");
				return;
				}
				subtype = "DO";
			}else{
				return;
			}
			
			if(document.getElementById("thirdparty").checked == true && document.getElementById("warning_hkid").style.display == ""){
				
//			}else if(document.getElementById("warning_cardholder").style.display == ""){
				
			}else if(document.getElementById("warning_email").style.display == ""){
					
			}else{				
				submitShowLoading();
				if(document.getElementById("creditCard").checked == true){
					$("#payMtdType").val($("#creditCard").val());
					$("#processCreditCard").val("P");
					if ( $("#expiryMonth").val() != "" )
						$("#ccExpDate").val($("#expiryMonth").val()+"/"+$("#expiryYear").val());
				}
				if(document.getElementById("waivePrepay").checked == true){
					$("#processCreditCard").val(null);
				}
				if(document.getElementById("cash").checked == true){
					$("#processCreditCard").val(null);
					$("#payMtdType").val($("#cash").val());
				}
				if($("#payMtdType").val() == "C"){
					if(document.getElementById("thirdparty").checked == true){
						$("#thirdPartyInd").val($("#thirdparty").val());
					}
					if(document.getElementById("self").checked == true){
						$("#thirdPartyInd").val($("#self").val());
					}
				}
				if(document.getElementById("email").checked == true){
					$("#billMedia").val($("#email").val());
				}
				if(document.getElementById("paper").checked == true){
					$("#billMedia").val($("#paper").val());
				}
				if($("#payMtdType").val() == "M"){
					if(document.getElementById("fsPrepay").checked == true){
						$("#cashFsPrepay").val("Y");
					}else{
						$("#cashFsPrepay").val("N");
					}
				}
				if(document.getElementById("ccVerified").checked == true){
					$("#ccVerified").val("Y");
				}else{
					$("#ccVerified").val("N");
				}
				$("#submitTag").val(subtype);
				document.getElementById("sourcecode").disabled = false;
				document.getElementById("staffnum").disabled = false;
				//alert("Order is suspended.");	
				$(":input").attr("disabled", false); 
				document.paymentForm.submit();
			}
		//}
	}
	
	function suspend(){
		var suspend = document.getElementById('suspendSelect').value;
		if (suspend == ""){
			alert("Please select a suspend reason code!");
		}else{
			if(document.getElementById("thirdparty").checked == true && document.getElementById("warning_hkid").style.display == ""){
				
//			}else if(document.getElementById("warning_cardholder").style.display == ""){
				
			}else if(document.getElementById("warning_email").style.display == ""){
					
			}else{
				submitShowLoading();
				//if($("#orderActionInd").val() == "W" && $("#orderStatus").val() == "31"){
				if($("#orderActionInd").val() == "W" && "${imsPaymentUI.approvalRequested}" == "Y"){
					$(":input").attr("disabled", false);
				}
				if(document.getElementById("creditCard").checked == true){
					$("#payMtdType").val($("#creditCard").val());
					$("#processCreditCard").val("P");
					if ( $("#expiryMonth").val() != "" )
						$("#ccExpDate").val($("#expiryMonth").val()+"/"+$("#expiryYear").val());
				}
				if(document.getElementById("waivePrepay").checked == true){
					$("#processCreditCard").val(null);
				}
				if(document.getElementById("cash").checked == true){
					$("#processCreditCard").val(null);
					$("#payMtdType").val($("#cash").val());
				}
				if($("#payMtdType").val() == "C"){
					if(document.getElementById("thirdparty").checked == true){
						$("#thirdPartyInd").val($("#thirdparty").val());
					}
					if(document.getElementById("self").checked == true){
						$("#thirdPartyInd").val($("#self").val());
					}
				}
				if(document.getElementById("email").checked == true){
					$("#billMedia").val($("#email").val());
				}
				if(document.getElementById("paper").checked == true){
					$("#billMedia").val($("#paper").val());
				}
				if($("#payMtdType").val() == "M"){
					if(document.getElementById("fsPrepay").checked == true){
						$("#cashFsPrepay").val("Y");
					}else{
						$("#cashFsPrepay").val("N");
					}
				}
				if(document.getElementById("ccVerified").checked == true){
					$("#ccVerified").val("Y");
				}else{
					$("#ccVerified").val("N");
				}
				$("#submitTag").val("S");
				document.getElementById("sourcecode").disabled = false;
				document.getElementById("staffnum").disabled = false;
				//alert("Order is suspended.");
				document.paymentForm.submit();
			}
		}
	}
	
	function cancel(){
		var cancel = document.getElementById('cancelSelect').value;
		if (cancel == ""){
			alert("Please select a cancel reason code!");
		}else{
			if(document.getElementById("thirdparty").checked == true && document.getElementById("warning_hkid").style.display == ""){
				
//			}else if(document.getElementById("warning_cardholder").style.display == ""){
				
			}else if(document.getElementById("warning_email").style.display == ""){
					
			}else{
				submitShowLoading();
				//if($("#orderActionInd").val() == "W" && $("#orderStatus").val() == "31"){
				if($("#orderActionInd").val() == "W" && "${imsPaymentUI.approvalRequested}" == "Y"){
					$(":input").attr("disabled", false);
				}
				if(document.getElementById("creditCard").checked == true){
					$("#payMtdType").val($("#creditCard").val());
					$("#processCreditCard").val("P");
					if ( $("#expiryMonth").val() != "" )
						$("#ccExpDate").val($("#expiryMonth").val()+"/"+$("#expiryYear").val());
				}
				if(document.getElementById("waivePrepay").checked == true){
					$("#processCreditCard").val(null);
				}
				if(document.getElementById("cash").checked == true){
					$("#processCreditCard").val(null);
					$("#payMtdType").val($("#cash").val());
				}
				if($("#payMtdType").val() == "C"){
					if(document.getElementById("thirdparty").checked == true){
						$("#thirdPartyInd").val($("#thirdparty").val());
					}
					if(document.getElementById("self").checked == true){
						$("#thirdPartyInd").val($("#self").val());
					}
				}
				if(document.getElementById("email").checked == true){
					$("#billMedia").val($("#email").val());
				}
				if(document.getElementById("paper").checked == true){
					$("#billMedia").val($("#paper").val());
				}
				if($("#payMtdType").val() == "M"){
					if(document.getElementById("fsPrepay").checked == true){
						$("#cashFsPrepay").val("Y");
					}else{
						$("#cashFsPrepay").val("N");
					}
				}
				if(document.getElementById("ccVerified").checked == true){
					$("#ccVerified").val("Y");
				}else{
					$("#ccVerified").val("N");
				}
				$("#submitTag").val("C");
				document.getElementById("sourcecode").disabled = false;
				document.getElementById("staffnum").disabled = false;
				//alert("Order is canceled.");
				document.paymentForm.submit();
			}
		}
	}
	
	function more(){		
		document.getElementById('prepayMore').style.display="";
		document.getElementById('prepayMore2').style.display="";
		document.getElementById('prepayMore3').style.display="";
		document.getElementById('waivePrepayRequest').style.display="";
		document.getElementById('creditPrepay').style.display="";
	}
	
	//TT
	function more2(){		
		//document.getElementById('prepayMore').style.display="";
		//document.getElementById('OTCMore2').style.display="";
		//document.getElementById('prepayMore3').style.display="";
		document.getElementById('waiveOTCRequest').style.display="";
		document.getElementById("OtInstallChrgDiscountRow").style.display="";
		//document.getElementById('creditPrepay').style.display="";
	}
	
	function prepay(){
		if(document.getElementById('creditCard').checked == false){
			document.getElementById('prepayInfoCash').style.display="";
			document.getElementById('prepayInfoCreditCard').style.display="none";
		}else{
			document.getElementById('prepayInfoCash').style.display="none";
			document.getElementById('prepayInfoCreditCard').style.display="";
		}
	}
	
	//Terrence Part
	function cardCheck(){
		prepay();
		if(document.getElementById('creditCard').checked == false){
			document.getElementById('cardPayment').style.display="none";
			document.getElementById('waivePrepayRequestwhole').style.display="";
		}else{
			document.getElementById('cashPayRequest').style.display="none";
			document.getElementById('cashPayRequestBtn').style.display="none";
			document.getElementById('warning_cash').style.display="none";
			document.getElementById('cardPayment').style.display="";
			document.getElementById('waivePrepayRequestwhole').style.display="none";
		}
	}

	function cashPay(){
		if($("#isCcOffer").val() == "Y" || $("#idDocType").val() == "PASS"){
			if(document.getElementById('cash').checked == true){
				//var answer = confirm("Marketing approval is needed. Please submit the order for approval.");
				//if(answer){
					document.getElementById('cashPayRequest').style.display="";
					document.getElementById('cashPayRequestBtn').style.display="";
					document.getElementById('warning_cash').style.display="";
					//cardCheck();
				//}else{
					//document.getElementById('creditCard').checked = true;
					//document.getElementById('cash').checked = false;
					//cardCheck();
				//}
			}else{
				cardCheck();
			}
		}else{
			cardCheck();
		}
	}
	
	//Terrence Part
	function selfCheck(){
		if(document.getElementById('thirdparty').checked == false){
			document.getElementById('selfCard').style.display="none";
			document.getElementById('warning_hkid').style.display = "none";
			document.getElementById('warning_hkid_msg').innerHTML = "";
			checkCardHolderName();
		}else{
			document.getElementById('selfCard').style.display="";
			document.getElementById('warning_cardholder').style.display = "none";
			document.getElementById('warning_cardholder_msg').innerHTML = "";
		}
	}
	
	//Terrence Part
	function validateHKID(){
		$.ajax({
			url : 'validatehkid.html?docType=' + $(".cardholderdoctype").val() + '&idDocNum=' + $(".cardholderdocnum").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
			    }
				document.getElementById('warning_hkid').style.display = "none";
				document.getElementById('warning_hkid_msg').innerHTML = "";
			},
			success : function(msg) {
				var count=0;
				
				$.each(eval(msg), function(i, item) {
					if( item.isValid )
					{
						document.getElementById('warning_hkid').style.display = "none";
						document.getElementById('warning_hkid_msg').innerHTML = "";						
					}
					else if( !item.isValid )
					{
						document.getElementById('warning_hkid').style.display = "";
						document.getElementById('warning_hkid_msg').innerHTML = item.errorText;
						
						//window.tempEl = $('.cardholderdocnum');
				        //setTimeout("window.tempEl.focus();",1);
						//alert(item.errorText);
					}
					count++;
				});
				
				var idDocNum = $('.cardholderdocnum').val();
				if(count==0 && (idDocNum==null || idDocNum=="")){
					
				}else if(count==0){ //&& idDocNum!=null){
					document.getElementById('warning_hkid').style.display = "none";
					document.getElementById('warning_hkid_msg').innerHTML = "";
				}
			}
		});
	}
	
	function getSourceCode(init){
		
		
		if ( $("#appMethod").val() != null && $("#appMethod").val() != "" )
		{
			$("#appMethod").val($("#appMethod option")[$("#appMethod").attr("selectedIndex")].value);
			
// 			$("#sourcecode option").each(function(){
// 				if ( !init && $(this).val() == "" )
// 					$(this).remove();
					
// 				if ( $(this).hasClass($("#appMethod").val()))
// 					$(this).css("display","");
// 				else
// 					$(this).css("display","none");
// 			});
			
// 			return;
			
			
			$.ajax({
				url : 'getsourcecode.html?AppMethod=' + $("#appMethod").val(),
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
					$("#sourcecode option").remove();					
					var count=0;
					var list = $("#sourcecode").attr('options');
					
					$.each(eval(msg), function(i, item) 
					{
						//list[count] = new Option(item.label, item.code, true, false);
						count++;				
					});
					
					if(count>0){ 
						list[0] = new Option('', '', true, false);
						
						$.each(eval(msg), function(i, item) 
								{							     
									list[i+1] = new Option(item.label, item.code, true, false);							
								});
					}
					
					if(count==0){ //&& idDocNum!=null){				
					}
			
					if ( init )
					{
						$("#sourcecode").val("${imsPaymentUI.sourcecode}");
					}
				}
			});
		}
	}
	
	function getSalesInfo(){
		$.ajax({
			url : 'getsalesnameshopcontact.html?salesCd=' + $("#staffnum").val() + '&shopCd=' + $("#sourcecode").val(),
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
				$.each(eval(msg), function(i, item) {
					document.getElementById("staffname").value = item.salesName;
					if ( item.contactPhone != "" )
						document.getElementById("contactnum").value = item.contactPhone;
					count++;
				});
				if(count==0){ //&& idDocNum!=null){
					
				}
			}
		});
	}
	
// 	function getCCSalesInfo(){
// 		$.ajax({
// 			url : 'getsalesinfo.html?salesCd=' + $("#staffnum").val() + '&shopCd=' + $("#sourcecode").val(),
// 			type : 'POST',
// 			dataType : 'json',
// 			timeout : 60000,
// 			error : function(XMLHttpRequest, textStatus, errorThrown) {
// 			    if(textStatus=='parsererror'){
// 			        alert("Your session has been timed out, please re-login."); z
// 			    }else if(textStatus == 'timeout'){
// 			    	alert("Server response timeout, please try again.");
// 			    }
// 			},
// 			success : function(msg) {
// 				var count=0;
// 				$.each(eval(msg), function(i, item) {
// 					document.getElementById("staffname").value = item.salesName;
// 					document.getElementById("contactnum").value = item.contactPhone;
// 					count++;
// 				});
// 				if(count==0){ //&& idDocNum!=null){
					
// 				}
// 			}
// 		});
// 	}

	function getCCSalesInfo(){
		$.ajax({
			url : 'getsalesinfo.html?salesCd=' + $("#staffnum").val(),
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
				$.each(eval(msg), function(i, item) {
					document.getElementById("staffname").value = item.salesName;
					document.getElementById("contactnum").value = item.contactPhone;
					count++;
				});
				if(count==0){ //&& idDocNum!=null){
					
				}
			}
		});
	}
	
		
	function getCCNewSourceCdAppMth(){
		
		$("#appMethod option:selected").removeAttr('selected', 'selected');
		$("#sourcecode option:selected").removeAttr('selected', 'selected');
		//alert($("#staffnum").val());
		$.ajax({
			url : 'getccnewsourcecdappmth.html?staffNum=' + $("#staffnum").val(),
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
				$.each(eval(msg), function(i, item) 
				{
					
					if ( item.newSourceCd != 'null' && item.newAppMethod != 'null'){
				
						//alert('new app method: '+item.newAppMethod);
						//alert('new source code: '+item.newSourceCd);
						$("#appMethod").val(item.newAppMethod);
						
						
						setNewSourceCode(writeNewSourcecd, item.newSourceCd);
						//getSourceCode(writeNewSourcecd);
						//$("#sourcecode").val(item.newSourceCd);
						//alert('bcd');
						
						/*
						var myTimer = window.setTimeout(function() {
							$("#sourcecode").val(item.newSourceCd);
						}, 500);
						*/
						
					
					}else {
					
						$("#appMethod").val('null');					
						$("#sourcecode").val('null');
					}
				});
				
				
			}
		});
		
		
	}	
	
	function setNewSourceCode(callback, newSourceCode){
		
		
		if ( $("#appMethod").val() != null && $("#appMethod").val() != "" )
		{
			//alert('appMethod selectedIndex:'+$("#appMethod option")[$("#appMethod").attr("selectedIndex")].value);
			$("#appMethod").val($("#appMethod option")[$("#appMethod").attr("selectedIndex")].value);
	
			$.ajax({
				url : 'getsourcecode.html?AppMethod=' + $("#appMethod").val(),
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
					$("#sourcecode option").remove();					
					var count=0;
					var list = $("#sourcecode").attr('options');
					
					$.each(eval(msg), function(i, item) 
					{
						//list[count] = new Option(item.label, item.code, true, false);
						count++;				
					});
					
					if(count>0){ 
						list[0] = new Option('', '', true, false);
						
						$.each(eval(msg), function(i, item) 
								{							     
									list[i+1] = new Option(item.label, item.code, true, false);							
								});
					}
					
					callback(newSourceCode);
							
				}
			});
		}
		
	}
		
	function writeNewSourcecd(newSourceCode){
		$("#sourcecode").val(newSourceCode);
	}
	
	
	function checkEmail(){
		$.ajax({
			url : 'checkemailaddr.html?emailAddr=' + $("#emailAddr").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
			    }
				document.getElementById('warning_email').style.display = "none";
				document.getElementById('warning_email_msg').innerHTML = "";
			},
			success : function(msg) {
				var count=0;
				$.each(eval(msg), function(i, item) {
					if(item.valid == true){
						document.getElementById('warning_email').style.display = "none";
						document.getElementById('warning_email_msg').innerHTML = "";
					}else if(item.valid == false){
						document.getElementById('warning_email').style.display = "";
						document.getElementById('warning_email_msg').innerHTML = "Wrong Format!";
						window.tempEl = document.getElementById('emailAddr');
				        setTimeout("window.tempEl.focus();",1);
					}
					count++;
				});
				if(count==0){ //&& idDocNum!=null){
					document.getElementById('warning_email').style.display = "none";
					document.getElementById('warning_email_msg').innerHTML = "";
				}
			}
		});
	}
	
	function checkCardHolderName(){
		$.ajax({
			url : 'checkcardholdername.html?cardholdername=' + $("#cardholdername").val() + '&registerName=' + $("#registerName").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please re-login."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
			    }
				document.getElementById('warning_cardholder').style.display = "none";
				document.getElementById('warning_cardholder_msg').innerHTML = "";
			},
			success : function(msg) {
				var count=0;
				$.each(eval(msg), function(i, item) {
					if(item.same == true){
						document.getElementById('warning_cardholder').style.display = "none";
						document.getElementById('warning_cardholder_msg').innerHTML = "";
					}else if(item.same == false && document.getElementById("self").checked == true){
						document.getElementById('warning_cardholder').style.display = "";
						document.getElementById('warning_cardholder_msg').innerHTML = '<spring:message code="ccHoldName.diff" text="" />';
						//alert("Card holder name is different from register name. If this is 3rd party credit card, please click &quotYes&quot.");
						//window.tempEl = document.getElementById('cardholdername');
				        //setTimeout("window.tempEl.focus();",1);
					}
					count++;
				});
				if(count==0){ //&& idDocNum!=null){
					document.getElementById('warning_cardholder').style.display = "none";
					document.getElementById('warning_cardholder_msg').innerHTML = "";
				}
			}
		});
	}
		
	function creditPrepayChange(){		
			document.getElementById("creditPrepay").checked = true;					
			document.getElementById("waivePrepay").checked = false;
			document.getElementById('warning_cash').style.display="none";
	}

	function cashPrepayChange(){
		if(document.getElementById("cashPrepay").value == "N"){
			document.getElementById("cashPrepay").checked = true;
			document.getElementById("fsPrepay").checked = false;			
			document.getElementById("waivePrepay").checked = false;
			//document.getElementById('warning_cash').style.display="none";
		}
	}
		
	function fsPrepayChange(){
		if(document.getElementById("fsPrepay").value == "Y"){
			document.getElementById("cashPrepay").checked = false;
			document.getElementById("fsPrepay").checked = true;
			document.getElementById("waivePrepay").checked = false;
			//document.getElementById('warning_cash').style.display="none";
		}
	}
	
	function waivePrepayChange(){		
			document.getElementById("cashPrepay").checked = false;			
			//document.getElementById("creditPrepay").checked = false;			
			document.getElementById("fsPrepay").checked = false;			
			document.getElementById("waivePrepay").checked = true;
			if("${imsPaymentUI.waiveApproval}"!="Y"){
				document.getElementById('warning_cash').style.display="";	
			}			
	}
	
	//TT
// 	function waiveOTCChange(){			
// 		document.getElementById("waiveOTC").checked = true;
// 		document.getElementById("OTC").checked = false;
// 		document.getElementById("OTCC").checked = false;
// 		$("#installInstalmentInd").val("N");
		
// 		if("${imsPaymentUI.waiveOtApproval}"!="Y"){
// 			document.getElementById('warning_cash').style.display="";	
// 		}			
// 	}	
	
	//TT
// 	function OTCChange(){	
// 		if (document.getElementById("OTC").checked==true){
// 			$("#installInstalmentInd").val("N");
// 			document.getElementById("waiveOTC").checked = false;
// 		}else if (document.getElementById("OTCC").checked==true){
// 			$("#installInstalmentInd").val("Y");			
// 			document.getElementById("waiveOTC").checked = false;
// 		}
// 	}
	
	function OTCChange(){		
		
		if ( document.getElementById("waiveOTC").checked )
		{
			if("${imsPaymentUI.waiveOtApproval}"!="Y"){
				document.getElementById('warning_cash').style.display="";	
			}	
		}
		else if ( document.getElementById("DiscountOTC").checked ){
			if ("${imsPaymentUI.discountOTCApproval}"!="Y")
				document.getElementById('warning_cash').style.display="";
		}
		else if ( document.getElementById("OTC").checked ){
			
			document.getElementById('warning_cash').style.display="none";
		}
		
		if ( document.getElementById("OTCC") != null )
		{
			if ( document.getElementById("OTCC").checked )
			{
				$("#installInstalmentInd").val("Y");
				document.getElementById('warning_cash').style.display="none";
			}
			else
			{
				$("#installInstalmentInd").val("N");
			}
		}
	}
	
	function keyUpOnCardHolderDocNum(){
		$('.cardholderdocnum').val($('.cardholderdocnum').val().trim().toUpperCase());
	}
	
	function keyUpOnCardHolderName(){
		document.getElementById('cardholdername').value = document.getElementById('cardholdername').value.toUpperCase();
	}
	
	function keyUpOnStaffNum(){
		document.getElementById('staffnum').value = document.getElementById('staffnum').value.trim().toUpperCase();
	}
	
	function imposeMaxLength(Object, MaxLen){
		return (Object.value.length <= MaxLen);
	}

	
	
	function errorCodeText(){
		
		//alert($(imsPaymentUI.errorCode));
		//alert($("#installInstalmentInd").val());
		//alert($("#imsPaymentUI.payMtdType").val());
		//var errorCode= $("#imsPaymentUI.errorCode");
		//var errorText = $("#imsPaymentUI.errorText");
		//alert($("#imsPaymentUI.errorCode").val());
		//alert($("#imsPaymentUI.errorText").val());
		//alert(errorCode);
		//alert(errorText);
		if("${imsPaymentUI.errorCode}"<0){
			alert("Error: ${imsPaymentUI.errorCode}:\n${imsPaymentUI.errorText}");
			hideSubmitLoading();
		}else 
			document.paymentForm.submit();
		//alert("Error Code:" + errorCode + ",\n" + "Error Text:" + errorText);
		
	}
	
	function textareaStringHandler()
	{		
		$("#textAreaInfoDO").val( $("#textAreaInfoDOPreSrc").text() + $("#requestSelectDiscountOTC").val()+ "\n" + $("#textAreaInfoDOSufSrc").text());
	}

	
	
	$(document).ready(function(){
		IOA("${imsPaymentUI.otChrgType}");

		$("#appMethodDesc").get(0).selectedIndex = $("#appMethod").get(0).selectedIndex;
		var baseYear = expiryDateCtrl();
		textareaStringHandler();
/*		if(document.getElementById("cash").checked == true && ($("#isCcOffer").val() == "Y" || $("#idDocType").val() == "PASS")){
			document.getElementById('cashPayRequest').style.display="none";
			document.getElementById('cashPayRequestBtn').style.display="none";
		}
*/	if("${imsPaymentUI.errorCode}" < "0"){
	 alert("Error: ${imsPaymentUI.errorCode}:\n${imsPaymentUI.errorText}");	 
}

		
		if(document.getElementById("creditCard").checked == true){
			if($("#ccExpDate").val() != null && $("#ccExpDate").val() != ""){
				var selectedMonth = parseInt($("#ccExpDate").val().substring(0,2),10);
				var selectedYear = parseInt($("#ccExpDate").val().substring(3,7));				
				document.getElementById('expiryMonth').selectedIndex = (selectedMonth);
				document.getElementById('expiryYear').selectedIndex = (selectedYear-baseYear);
			}
		}
		//cashPay();
		if(document.getElementById("fsPrepay").checked == true){
			document.getElementById('prepayMore').style.display="";
			document.getElementById('prepayMore2').style.display="";
			document.getElementById('prepayMore3').style.display="";
		}
		//if($("#orderActionInd").val() == "W" && $("#orderStatus").val() == "31"){
		if($("#orderActionInd").val() == "W" && "${imsPaymentUI.cashApproval}" == "Y"){
			document.getElementById('cashPayRequest').style.display="none";
			document.getElementById('cashPayRequestBtn').style.display="none";
			document.getElementById('warning_cash').style.display="none";
			$(":input").attr("disabled", true);
			$("#suspendSelect").attr("disabled", false);
			$("#cancelSelect").attr("disabled", false);
		}	
		if("${imsPaymentUI.waiveApproval}" == "Y"){			
			document.getElementById('waivePrepayRequest').style.display="";
			$("#waiveRemarkTr").hide();
			//waivePrepayChange();
			$("#waivePrepay").attr("disabled", true);
			$("#cashPrepay").attr("disabled", true);
			$("#creditPrepay").attr("disabled", true);
			$("#fsPrepay").attr("disabled", true);
			$("#cash").attr("disabled", true);
			$("#creditCard").attr("disabled", true);
		}else if("${imsPaymentUI.waiveApproval}" == "N"){						
			$("#waiveRemarkTr").hide();
			$("#waivePrepay").attr("disabled", true);
		}else{		
			$("#waivePrepay").attr("disabled", false);
			$("#waiveRemark").attr("disabled", false);
		}		
		if("${imsPaymentUI.waiveOtApproval}" == "Y"){			
			document.getElementById('waiveOTCRequest').style.display="";
			$("#waiveOTCRemarkTr").hide();
			document.getElementById("waiveOTC").checked = true;
			OTCChange();
			$("#waiveOTC").attr("disabled", true);
			$("#cash").attr("disabled", true);
			$("#creditCard").attr("disabled", true);
		}else if("${imsPaymentUI.waiveOtApproval}" == "N"){						
			$("#waiveOTCRemarkTr").hide();
			$("#waiveOTC").attr("disabled", true);
		}else{		
			$("#waiveOTC").attr("disabled", false);
			$("#waiveOTCRemark").attr("disabled", false);
		}		
		//Tony for discount OTC
		if("${imsPaymentUI.discountOTCApproval}" == "Y"){			
			document.getElementById('OtInstallChrgDiscountRow').style.display="";
			$("#DiscountOTCRemarkTr").hide();
		//	waiveOTCChange();
			$("#DiscountOTC").attr("disabled", true);
			$("#cash").attr("disabled", true);
			$("#creditCard").attr("disabled", true);
		}else if("${imsPaymentUI.discountOTCApproval}" == "N"){						
			$("#DiscountOTCRemarkTr").hide();
			$("#DiscountOTC").attr("disabled", true);
		}else{		
			$("#DiscountOTC").attr("disabled", false);
			$("#textAreaInfoDO").attr("disabled", false);
		}
		//Tony end
		if("${imsPaymentUI.preInstallInd}" == "Y"){
			document.getElementById('waivePrepayRequestentire').style.display="none";
		}
		if($("#orderActionInd").val() == "W"){
			//$("#sourcecode").attr("disabled", true);
			$("#staffnum").attr("disabled", true);
		}
		//if($("#orderActionInd").val() == "W" && $("#orderStatus").val() == "32"){
		if($("#orderActionInd").val() == "W" && "${imsPaymentUI.cashApproval}" == "N"){
			$("#cash").attr("disabled", true);
			document.getElementById("creditCard").checked = true;
			//cashPay();
		}
		if($("#submitTag").val() == "S" && $("#orderId").val() != null && $("#orderId").val() != ""){
			
			if ( $("[id='appMethod.errors']").html()== null || "${imsPaymentUI.isCC}" != "Y")
			{
				alert("Order is suspended. Order ID: " + $("#orderId").val());
				window.location = "welcome.html";
			}
		}
		if( ($("#submitTag").val() == "R" || $("#submitTag").val() == "W" || $("#submitTag").val() == "WO" || $("#submitTag").val() == "DO") && $("#orderId").val() != null && $("#orderId").val() != ""){
			alert("Order is suspended. Order ID: " + $("#orderId").val());
			window.location = "welcome.html";
		}
		if($("#cancelSelect").val() != null && $("#cancelSelect").val() != "" && $("#submitTag").val() == "C"
			&& $("#orderId").val() != null && $("#orderId").val() != ""){
			$(":input").attr("disabled", true);
			alert("Order is cancelled. Order ID: " + $("#orderId").val());
			window.location = "welcome.html";
		}else if($("#cancelSelect").val() != null && $("#cancelSelect").val() != "" && $("#submitTag").val() == "C"){
			$(":input").attr("disabled", true);
		}
		if ( "${imsPaymentUI.isCC}" == "Y" )
		{
			$("#sourcecode").attr("disabled",false);
			$("#ccVerified").attr("checked",true);
			$("#ccVerified").val("Y");
			getSourceCode(true);
		}

		if("${ims_direct_sales} == 'true'"){ 
			$("#staffnum, #staffname").attr("disabled",true);
		}
		
	});
/*	
	onload = function(){
		if($("#cReasonCd").val() != null && $("#cReasonCd").val() != ""){
			$(":input").disabled = true;
		}
	}
*/
</script>

<table width="100%" height="30">
	<tr>
		<td class="contenttextgreen" align="center">
		 	PCD Acquisition
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

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<div id="warning_cash" class="ui-widget" style="display:none; width:60%;">
	<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
		<p>
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<div class="contenttext" id="warning_cash_msg" style="color:red; font-weight:bold;">
		Marketing approval is needed. Please submit the order for approval.</div>
		</p>
	</div>
</div>
<form:form name="paymentForm" method="POST" commandName="imsPaymentUI">
<br>
<table width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<!-- Header -->
		<table width="100%" border="0" cellspacing="1" rules="none">
		<tr>
			<td class="table_title" >Payment Method/ Sales Info.</td>
		</tr>
		</table>
		<!-- Content table -->
		<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
		<!-- Payment method -->
		<tr style="display:none;">
			<td class="table_title" colspan="4">Payment Method</td>
			<form:hidden path="year"/>
			<form:hidden path="month"/>
			<form:hidden path="isCcOffer"/>
			<form:hidden path="idDocType"/>
			<form:hidden id="registerName" path="registerName"/>
			<form:hidden id="submitTag" path="submitTag"/>
			<form:hidden id="orderId" path="orderId"/>
			<form:hidden id="orderActionInd" path="orderActionInd"/>
			<form:hidden id="orderStatus" path="orderStatus"/>
			<form:hidden path="isCC"/>
			<form:hidden path="isPT"/>
		</tr>
		<tr style="display:none;">
        	<c:choose><c:when test='${imsPaymentUI.payMtdType == "C" }'>
        		<td width=20% align="left">
        			<input type="radio" id="creditCard" name="paymentmethod" value="C" onClick="cashPay()" checked/>Credit Card
        		</td>
        		<td width=20%>  
       				<div <c:if test="${ims_direct_sales eq true and (imsPaymentUI.isCcOffer eq 'Y' or imsPaymentUI.idDocType eq 'PASS')}">style="display:none;"</c:if>>
       					<input type="radio" id="cash" name="paymentmethod" value="M" onClick="cashPay()" <c:if test="${ims_direct_sales eq true and (imsPaymentUI.isCcOffer eq 'Y' or imsPaymentUI.idDocType eq 'PASS')}">disabled</c:if>/>Cash
       				</div>
        		</td>
        	</c:when>
        	<c:when test='${imsPaymentUI.payMtdType == "M" }'>
        		<td width=20% align="left">
        			<input type="radio" id="creditCard" name="paymentmethod" value="C" onClick="cashPay()"/>Credit Card
        		</td>
        		<td width=20%>
        			<div <c:if test="${ims_direct_sales eq true and (imsPaymentUI.isCcOffer eq 'Y' or imsPaymentUI.idDocType eq 'PASS')}">style="display:none;"</c:if>>
        				<input type="radio" id="cash" name="paymentmethod" value="M" onClick="cashPay()" checked <c:if test="${ims_direct_sales eq true and (imsPaymentUI.isCcOffer eq 'Y' or imsPaymentUI.idDocType eq 'PASS')}">disabled</c:if>/>Cash
        			</div>
        		</td>
        	</c:when>
        	<c:otherwise>
        		<td width=20% align="left">
        			<input type="radio" id="creditCard" name="paymentmethod" value="C" onClick="cashPay()" checked/>Credit Card
        		</td>   
        		<td width=20%>  
        			<div <c:if test="${ims_direct_sales eq true and (imsPaymentUI.isCcOffer eq 'Y' or imsPaymentUI.idDocType eq 'PASS')}">style="display:none;"</c:if>>
        				<input type="radio" id="cash" name="paymentmethod" value="M" onClick="cashPay()" <c:if test="${ims_direct_sales eq true and (imsPaymentUI.isCcOffer eq 'Y' or imsPaymentUI.idDocType eq 'PASS')}">disabled</c:if>/>Cash
        			</div> 
        		</td>
        	</c:otherwise></c:choose>
        	<form:hidden path="processCreditCard"/>
        	<form:hidden path="payMtdType" />
        	<td></td>
        </tr>
        <c:choose><c:when test='${imsPaymentUI.submitTag == "R"}'>
        <tr style="display:none;">
        	<td colspan="3">
        	<div id="cashPayRequest">
        		Request for:
        		<select id="requestSelect" disabled>
				<option value="cashPay" label="Use Cash payment">Use Cash payment</option>
				</select>
				<form:textarea rows="5" cols="50" path="textAreaInfo" onkeypress="return imposeMaxLength(this, 1999);"></form:textarea>
				<a href="#" class="nextbutton" onclick="approve('cashpay')">Submit</a>
				</div>
       		</td>
       		<td>
       		<div id="cashPayRequestBtn">
       			
       		</div>
    		</td>
       	</tr>
        </c:when>
        <c:otherwise>
        <tr style="display:none;">
        	<td colspan="3">
        	<div id="cashPayRequest" style="display:none">
        		Request for:
        		<select id="requestSelect" disabled>
				<option value="cashPay" label="Use Cash payment">Use Cash payment</option>
				</select>
				<form:textarea rows="5" cols="50" path="textAreaInfo" onkeypress="return imposeMaxLength(this, 1999);"></form:textarea>
				<a href="#" class="nextbutton" onclick="approve('cashpay')">Submit</a>
				</div>
       		</td>
       		<td>
       		<div id="cashPayRequestBtn" style="display:none">
       			
       		</div>
    		</td>
       	</tr>
       	</c:otherwise></c:choose>
       	
        <tr style="display:none;">
        <c:choose><c:when test='${imsPaymentUI.payMtdType == "M"}'>
        	<td colspan="4">
        	<div id="cardPayment" style="display:none">
        	<table width="100%" cellspacing="0">
        		<!-- Terrence Part -->
        		<tr>
      			  	<td width=20% align="left">Third Party Credit Card</td>
      			  	<c:choose><c:when test='${imsPaymentUI.thirdPartyInd == "Y"}'>
      			  	<td width=20% align="left">
        				<input type="radio" id="thirdparty" name="selfthirdparty" value="Y" onClick="selfCheck()" checked/>Yes
        			</td>
        			<td>        				
        				<input type="radio" id="self" name="selfthirdparty" value="N" onClick="selfCheck()"/>No
        			</td>
        			</c:when>
        			<c:otherwise>
        			<td width=20% align="left">
        				<input type="radio" id="thirdparty" name="selfthirdparty" value="Y" onClick="selfCheck()"/>Yes
        			</td>
        			<td>        				
        				<input type="radio" id="self" name="selfthirdparty" value="N" onClick="selfCheck()" checked/>No
        			</td>
        			</c:otherwise></c:choose>
        			<form:hidden path="thirdPartyInd" />
        			<td>
        			<form:errors path="thirdPartyInd" cssClass="error" /></td>
        		</tr>
        		<!-- Terrence Part -->
        		<tr>
        			<td align="left" >Card Holder Name:</td>
        			<td align="left">
        				<form:input id="cardholdername" path="ccHoldName" onkeyup="keyUpOnCardHolderName()" onblur="checkCardHolderName()" cssStyle="width: 212px;"/>
        			</td>
        			<td colspan="2">
        				<form:errors path="ccHoldName" cssClass="error" />
        			
					<div id="warning_cardholder" class="ui-widget" style="display:none">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="warning_cardholder_msg" style="font-weight:bold;"></div>
						</p>
					</div>
					</div>
					</td>
        		</tr>
        		<!-- Terrence Part -->
        		<tr>
        		<c:choose><c:when test='${imsPaymentUI.thirdPartyInd == "Y"}'>
        		<td colspan="2">
        		<div id="selfCard">
        			<table width='100%' cellspacing='0'><tr><td width='50%'align='left'>Card Holder Doc Type:</td><td align='left'>
						<form:select cssClass="cardholderdoctype" path="ccIdDocType" onchange="validateHKID()">
							<form:option value='HKID' label='HKID' >HKID</form:option>
							<form:option value='PASS' label='Passport' >Passport</form:option>
							<form:option value='BS' label='HKBR' >HKBR</form:option>
						</form:select>
						</td>
						<td>
						<form:errors path="ccIdDocType" cssClass="error" /></td></tr>
						<tr><td align='left'>Card Holder Doc No.:</td><td align='left'>
							<form:input cssClass="cardholderdocnum" path="ccIdDocNo" onkeyup="keyUpOnCardHolderDocNum()" onblur="validateHKID()"/>
						</td>
						<td><form:errors path="ccIdDocNo" cssClass="error" /></td>
						</tr></table>
        		</div>
        		</td>
        		</c:when>
        		<c:otherwise>
        		<td colspan="2">
        		<div id="selfCard" style="display:none">
        			<table width='100%' cellspacing='0'><tr><td width='50%'align='left'>Card Holder Doc Type:</td><td align='left'>
						<form:select cssClass="cardholderdoctype" path="ccIdDocType" onchange="validateHKID()">
							<form:option value='HKID' label='HKID' >HKID</form:option>
							<form:option value='PASS' label='Passport' >Passport</form:option>
							<form:option value='BS' label='HKBR' >HKBR</form:option>
						</form:select>
						</td>
						<td>
						<form:errors path="ccIdDocType" cssClass="error" /></td></tr>
						<tr><td align='left'>Card Holder Doc No.:</td><td align='left'>
							<form:input cssClass="cardholderdocnum" path="ccIdDocNo" onkeyup="keyUpOnCardHolderDocNum()" onblur="validateHKID()"/>
						</td>
						<td><form:errors path="ccIdDocNo" cssClass="error" /></td>
						</tr></table>
        		</div>
        		</td>
        		</c:otherwise></c:choose>
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
        			<td align="left">Card Type</td>
        			<td align="left">
        				<form:select id="cardtype" path="ccType">
        				<form:option value=""></form:option>
						<form:option value="V" label="Visa">Visa</form:option>
						<form:option value="M" label="Mastercard">Master</form:option>
						<form:option value="A" label="AmericanExpress">American Express</form:option>
						</form:select>
					</td>
					<td colspan="2">
						<form:errors path="ccType" cssClass="error" />
        			</td>
        		</tr>
        		<tr>
        			<td align="left">Card Number</td>
        			<td align="left">
        				<!-- <input id="cardnum" disabled="disabled" /> -->
        				<form:input id="cardnum" path="ccNum" size="30" maxlength="16" readonly="true"/>
        				
        				<input type="hidden" name="token" id="token" />
							
						<input type="hidden" name="ceksSubmit" value="N" />
        			</td>
        			<td align="left" width="20%">
        				<!-- <a href="#" class="nextbutton" onclick="inputCreditCardNum()">
        				Input Credit Card Number </a> -->
        				<c:choose><c:when test='${imsPaymentUI.submitTag == "C"}'>
        				<a href="#" class="nextbutton">Input Credit Card No.</a>
        				</c:when>
        				<c:otherwise>
        				<a href="#" class="nextbutton" onclick="javascript:openCEKSWindow()">Input Credit Card No.</a>
						</c:otherwise></c:choose>
					</td>
					<td>
						<form:errors path="ccNum" cssClass="error" />
        			</td>
        		</tr>
        		<tr>
        		<% Calendar today = Calendar.getInstance();
						   int year = today.get(Calendar.YEAR);
						   int iMonth = today.get(Calendar.MONTH)+1;
						   if(iMonth+2>12){year=year+1;iMonth=iMonth-12;}
						%>
        			<td align="left">Expiry Date</td>
        			<td align="left">
        	    		<form:select path="expiryMonth">
        	    		<form:option value="" label=""></form:option>
						<form:option id="epy1" value="01" label="01">01</form:option>
						<form:option id="epy2" value="02" label="02">02</form:option>
						<form:option id="epy3" value="03" label="03">03</form:option>
						<form:option id="epy4" value="04" label="04">04</form:option>
						<form:option id="epy5" value="05" label="05">05</form:option>
						<form:option id="epy6" value="06" label="06">06</form:option>
						<form:option id="epy7" value="07" label="07">07</form:option>
						<form:option id="epy8" value="08" label="08">08</form:option>
						<form:option id="epy9" value="09" label="09">09</form:option>
						<form:option id="epy10" value="10" label="10">10</form:option>
						<form:option id="epy11" value="11" label="11">11</form:option>
						<form:option id="epy12" value="12" label="12">12</form:option>
						</form:select>
						 
        				<form:select path="expiryYear" onchange="expiryDateCtrl()">
						<form:option value="<%= year %>" label="<%= Integer.toString(year) %>"><%= year %></form:option>
						<form:option value="<%= year+1 %>" label="<%= Integer.toString(year+1) %>"><%= year+1 %></form:option>
						<form:option value="<%= year+2 %>" label="<%= Integer.toString(year+2) %>"><%= year+2 %></form:option>
						<form:option value="<%= year+3 %>" label="<%= Integer.toString(year+3) %>"><%= year+3 %></form:option>
						<form:option value="<%= year+4 %>" label="<%= Integer.toString(year+4) %>"><%= year+4 %></form:option>
						<form:option value="<%= year+5 %>" label="<%= Integer.toString(year+5) %>"><%= year+5 %></form:option>
						<form:option value="<%= year+6 %>" label="<%= Integer.toString(year+6) %>"><%= year+6 %></form:option>
						<form:option value="<%= year+7 %>" label="<%= Integer.toString(year+7) %>"><%= year+7 %></form:option>
						<form:option value="<%= year+8 %>" label="<%= Integer.toString(year+8) %>"><%= year+8 %></form:option>
						<form:option value="<%= year+9 %>" label="<%= Integer.toString(year+9) %>"><%= year+9 %></form:option>
						</form:select>
					</td>
					<td colspan="3">
						<form:errors path="expiryMonth" cssClass="error" />
						<form:hidden path="ccExpDate"/>
        				<form:errors path="ccExpDate" cssClass="error" />
        			</td>
        			
        		</tr>
        		<tr <c:if test='${imsPaymentUI.isCC == "Y"}'>style="display:none"</c:if>>
        			<td>
						<form:checkbox id="ccVerified" path="ccVerified" value="Y"/>Verified
					</td>
					<td colspan="2"><form:errors path="ccVerified" cssClass="error" />
					</td>
				</tr>
			</table>
			
		</div>
		</td>
        </c:when>
        <c:otherwise>
        	<td colspan="4">
        	<div id="cardPayment">
        	<table width="100%" cellspacing="0">
        		<!-- Terrence Part -->
        		<tr>
      			  	<td width=20% align="left">Third Party Credit Card</td>
      			  	<c:choose><c:when test='${imsPaymentUI.thirdPartyInd == "Y"}'>
      			  	<td width=20% align="left">
        				<input type="radio" id="thirdparty" name="selfthirdparty" value="Y" onClick="selfCheck()" checked/>Yes
        			</td>
        			<td>        				
        				<input type="radio" id="self" name="selfthirdparty" value="N" onClick="selfCheck()"/>No
        			</td>
        			</c:when>
        			<c:otherwise>
        			<td width=20% align="left">
        				<input type="radio" id="thirdparty" name="selfthirdparty" value="Y" onClick="selfCheck()"/>Yes
        			</td>
        			<td>        				
        				<input type="radio" id="self" name="selfthirdparty" value="N" onClick="selfCheck()" checked/>No
        			</td>
        			</c:otherwise></c:choose>
        			<form:hidden path="thirdPartyInd" />
        			<td>
        			<form:errors path="thirdPartyInd" cssClass="error" />
        			</td>
        		</tr>
        		<!-- Terrence Part -->
        		<tr>
        			<td align="left">Card Holder Name:</td>
        			<td align="left">
        				<form:input maxlength="40" id="cardholdername" path="ccHoldName" onkeyup="keyUpOnCardHolderName()" onblur="checkCardHolderName()" cssStyle="width: 212px;"/>
        			</td>
        			<td colspan="2"><form:errors path="ccHoldName" cssClass="error" />
        			
					<div id="warning_cardholder" class="ui-widget" style="display:none">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div class="contenttext" id="warning_cardholder_msg" style="font-weight:bold;"></div>
						</p>
					</div>
					</div>
					</td>
        		</tr>
        		<!-- Terrence Part -->
        		<tr>
        		<c:choose><c:when test='${imsPaymentUI.thirdPartyInd == "Y"}'>
        		<td colspan="2">
        		<div id="selfCard">
        			<table width='100%' cellspacing='0'><tr><td width='50%'align='left'>Card Holder Doc Type:</td><td align='left'>
						<form:select cssClass="cardholderdoctype" path="ccIdDocType" onchange="validateHKID()">
							<form:option value='HKID' label='HKID' >HKID</form:option>
							<form:option value='PASS' label='Passport' >Passport</form:option>
							<form:option value='BS' label='HKBR' >HKBR</form:option>
						</form:select>
						</td>
						<td>
						<form:errors path="ccIdDocType" cssClass="error" /></td></tr>
						<tr><td align='left'>Card Holder Doc No.:</td><td align='left'>
							<form:input cssClass="cardholderdocnum" path="ccIdDocNo" onkeyup="keyUpOnCardHolderDocNum()" onblur="validateHKID()"/>
							</td>
							<td><form:errors path="ccIdDocNo" cssClass="error" /></td>
						</tr></table>
        		</div>
        		</td>
        		</c:when>
        		<c:otherwise>
        		<td colspan="2">
        		<div id="selfCard" style="display:none">
        			<table width='100%' cellspacing='0'><tr><td width='50%'align='left'>Card Holder Doc Type:</td><td align='left'>
						<form:select cssClass="cardholderdoctype" path="ccIdDocType" onchange="validateHKID()">
							<form:option value='HKID' label='HKID' >HKID</form:option>
							<form:option value='PASS' label='Passport' >Passport</form:option>
							<form:option value='BS' label='HKBR' >HKBR</form:option>
						</form:select>
						</td>
						<td>
						<form:errors path="ccIdDocType" cssClass="error" /></td></tr>
						<tr><td align='left'>Card Holder Doc No.:</td><td align='left'>
							<form:input cssClass="cardholderdocnum" path="ccIdDocNo" onkeyup="keyUpOnCardHolderDocNum()" onblur="validateHKID()"/>
							</td>
							<td><form:errors path="ccIdDocNo" cssClass="error" /></td>
						</tr></table>
        		</div>
        		</td>
        		</c:otherwise></c:choose>
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
        			<td align="left">Card Type</td>
        			<td align="left">
        				<form:select id="cardtype" path="ccType">
        				<form:option value=""></form:option>
						<form:option value="V" label="Visa">Visa</form:option>
						<form:option value="M" label="Mastercard">Master</form:option>
						<form:option value="A" label="AmericanExpress">American Express</form:option>
						</form:select>
					</td>
					<td colspan="2">
						<form:errors path="ccType" cssClass="error" />
        			</td>
        		</tr>
        		<tr>
        			<td align="left">Card Number</td>
        			<td align="left">
        				<!-- <input id="cardnum" disabled="disabled" /> -->
        				<form:input id="cardnum" path="ccNum" size="30" maxlength="16" readonly="true"/>
        				
        				<input type="hidden" name="token" id="token" />
							
						<input type="hidden" name="ceksSubmit" value="N" />
        			</td>
        			<td align="left" width="20%">
        				<!-- <a href="#" class="nextbutton" onclick="inputCreditCardNum()">
        				Input Credit Card Number </a> -->
        				<c:choose><c:when test='${imsPaymentUI.submitTag == "C"}'>
        				<a href="#" class="nextbutton">Input Credit Card No.</a>
        				</c:when>
        				<c:otherwise>
        				<a href="#" class="nextbutton" onclick="javascript:openCEKSWindow()">Input Credit Card No.</a>
						</c:otherwise></c:choose>
					</td>
					<td>
						<form:errors path="ccNum" cssClass="error" />
        			</td>
        		</tr>
        		<tr>
        		<% Calendar today = Calendar.getInstance();
						   int year = today.get(Calendar.YEAR);
						   int iMonth = today.get(Calendar.MONTH)+1;
						   if(iMonth+2>12){year=year+1;iMonth=iMonth-12;}
						%>
        			<td align="left">Expiry Date</td>
        			<td align="left">
        	    		<form:select path="expiryMonth">
        	    		<form:option value="" label=""></form:option>
						<form:option id="epy1" value="01" label="01">01</form:option>
						<form:option id="epy2" value="02" label="02">02</form:option>
						<form:option id="epy3" value="03" label="03">03</form:option>
						<form:option id="epy4" value="04" label="04">04</form:option>
						<form:option id="epy5" value="05" label="05">05</form:option>
						<form:option id="epy6" value="06" label="06">06</form:option>
						<form:option id="epy7" value="07" label="07">07</form:option>
						<form:option id="epy8" value="08" label="08">08</form:option>
						<form:option id="epy9" value="09" label="09">09</form:option>
						<form:option id="epy10" value="10" label="10">10</form:option>
						<form:option id="epy11" value="11" label="11">11</form:option>
						<form:option id="epy12" value="12" label="12">12</form:option>
						</form:select>
						
        				<form:select path="expiryYear" onchange="expiryDateCtrl()">
						<form:option value="<%= year %>" label="<%= Integer.toString(year) %>"><%= year %></form:option>
						<form:option value="<%= year+1 %>" label="<%= Integer.toString(year+1) %>"><%= year+1 %></form:option>
						<form:option value="<%= year+2 %>" label="<%= Integer.toString(year+2) %>"><%= year+2 %></form:option>
						<form:option value="<%= year+3 %>" label="<%= Integer.toString(year+3) %>"><%= year+3 %></form:option>
						<form:option value="<%= year+4 %>" label="<%= Integer.toString(year+4) %>"><%= year+4 %></form:option>
						<form:option value="<%= year+5 %>" label="<%= Integer.toString(year+5) %>"><%= year+5 %></form:option>
						<form:option value="<%= year+6 %>" label="<%= Integer.toString(year+6) %>"><%= year+6 %></form:option>
						<form:option value="<%= year+7 %>" label="<%= Integer.toString(year+7) %>"><%= year+7 %></form:option>
						<form:option value="<%= year+8 %>" label="<%= Integer.toString(year+8) %>"><%= year+8 %></form:option>
						<form:option value="<%= year+9 %>" label="<%= Integer.toString(year+9) %>"><%= year+9 %></form:option>
						</form:select>
					</td>
					<td colspan="3">
						<form:errors path="expiryMonth" cssClass="error" />
						<form:hidden path="ccExpDate"/>
        				<form:errors path="ccExpDate" cssClass="error" />
        			</td>
        			
        		</tr>
        		
        		<tr <c:if test='${imsPaymentUI.isCC == "Y"}'>style="display:none"</c:if>>
        			<td>
						<form:checkbox id="ccVerified" path="ccVerified" value="Y"/>Verified
					</td>
					<td colspan="2">
						<form:errors path="ccVerified" cssClass="error" />
					</td>
				</tr>
			</table>
			
		</div>
		</td>
		</c:otherwise></c:choose>
		</tr>	
		<!-- End Payment Method -->
		<!--   One-time Charge  -->
		<tr>
			<td class="table_title" colspan="4">One-time Charge</td>
		</tr>

<!-- Tony original 	
		<tr><td align='left'>
			<input type='radio' id="OTC" value="N" onclick="OTCChange()" checked/>
				Installation / Activation Service Charge:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><form:input id="waivedotinstallchrgvalue" path="waivedOtInstallChrg" disabled="true" /></td>
-->	
	
<!-- Added by Kinman -->		
		<form:hidden path="installInstalmentInd"/>	
		<tr>
			<td align='left'>
				<input type='radio' id="OTC" name="OneTimeorMonthlyPay" value="N" onclick="OTCChange()" checked/>
					<font class="installation">One Time Installation Service Charge:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
					<font class="activation" style="display:none">One Time Activation Service Charge:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
			</td>
			
			<td>				
				<form:input id="waivedotinstallchrgvalue" path="waivedOtInstallChrg" disabled="true" size="50"/>
			</td>			

			<td align="left">
				<c:choose>
					<c:when test='${order.isPT ne "Y"}'>
						<div id="waiveOTCMore" style="display:none">
							<a href='#' class='nextbutton' onclick='more2()'>More</a>
						</div>
					</c:when>
					<c:otherwise>
						<div id="waiveOTCMore">
							<a href='#' class='nextbutton' onclick='more2()'>More</a>
						</div>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>	
				
		<c:if test='${(imsPaymentUI.isValidForInstallInstallment == "Y") && (imsPaymentUI.isValidForWaive != "Y" and fn:toLowerCase(imsPaymentUI.waivedOtInstallChrg) != "waived")}'>
		<tr>
			<td align='left'>
				<c:choose>
					<c:when test='${imsPaymentUI.installInstalmentInd == "Y"}'>
						<input type="radio" id="OTCC" name="OneTimeorMonthlyPay" value="Y" onclick="OTCChange()" checked/>
					</c:when>
					<c:otherwise>
						<input type="radio" id="OTCC" name="OneTimeorMonthlyPay" value="N" onclick="OTCChange()" />
					</c:otherwise>
				</c:choose>		
			<font class="installation">Monthly Installation Service Charge:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
			<font class="activation" style="display:none">Monthly Activation Service Charge:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
			
			</td>
			


			<c:choose>
				<c:when test='${imsPaymentUI.onlyOneInstallInstallmentPlanInd == "Y"}'>
					<td align="left">
						<form:input id="installInstallmentMonthlyChrg" size="4" path="installInstallmentAmt" disabled="true"  />X
						<form:input id="installInstallmentChrgMonthly" size="4" path="installInstallmentMth" disabled="true" />months
					</td>
				</c:when>
				<c:otherwise>
					<td align='left'>
					<form:select path="tempinstallmentplan" id="imsInstallmentPlanSelection" onchange="selectPlan()" >	
					<form:option label="--Please select--" value=""></form:option>
					<form:options items="${imsInstallationInstallmentPlanList1}" itemValue="installPlanValue" itemLabel="installPlanDisplay"/>
					</form:select>
					</td>	
					<td>
					<div id="installmentError" style="display:none;color:#d63c00"> Please select a installment Plan.</div>
					</td>	
				</c:otherwise>
			</c:choose>
		</tr>
		</c:if>
		
	

<!-- that's all -->				

<!-- 		
			<td align="left"><c:choose><c:when test='${imsPaymentUI.isValidForWaive == "Y"}'><div id="waiveOTCMore" style="display:none"><a href='#' class='nextbutton' onclick='more2()'>More</a></div>
			</c:when>
			<c:otherwise><div id="waiveOTCMore"><a href='#' class='nextbutton' onclick='more2()'>More</a></div></c:otherwise></c:choose></td></tr>	

 -->
 	
 		<tr id="OtInstallChrgDiscountRow" style="display:none;">
			<td colspan="3" <c:if test='${imsPaymentUI.isPT != "Y" or empty imsPaymentUI.discountedOtInstallChrgList }'>style="display:none"</c:if> >
				<table cellspacing="0">
				<tbody><tr>
					<td>
						<span class="DiscountOTCTitle"><input type="radio" id="DiscountOTC" value="Y" name="OneTimeorMonthlyPay" onclick="OTCChange()">
						<font class="installation">Discounted Installation Charge: </font>
						<font class="activation" style="display:none">Discounted Activation Charge: </font>
						</span>
					</td>						
				</tr>
				<tr id="DiscountOTCRemarkTr">						
					<td valign="bottom">
						Request for:
			        	<form:select path="requestSelectDiscountOTC" onchange="textareaStringHandler()">
							<form:options items="${imsPaymentUI.discountedOtInstallChrgList}" itemValue="discountedOtInstallChrgAmt" itemLabel="discountedOtInstallChrgDisplay" />
						</form:select>
					</td>
					<td>
						<form:textarea path="textAreaInfoDO" onkeypress="return imposeMaxLength(this, 1999);" rows="6" cols="50" ></form:textarea>
						<pre id="textAreaInfoDOPreSrc" style="display:none"><c:out value="${textAreaInfoDOPreSrc}"/></pre>
						<pre id="textAreaInfoDOSufSrc" style="display:none"><c:out value="${textAreaInfoDOSufSrc}"/></pre>
					</td>
					<td valign="bottom">
						<a href="#" class="nextbutton" onclick="approve('discountedot')">Submit</a>
					</td>
				</tr>									
				</tbody></table>	
			</td>
		</tr>
	
 
 
		<tr>
			<td colspan="3">
				<div id="waiveOTCRequestentire" >
				<div id="waiveOTCRequestwhole" >
				<div id="waiveOTCRequest" style="display:none">
				<table cellspacing="0">
				<tr>
					<td>
						<input type='radio' id="waiveOTC" value="Y" name="OneTimeorMonthlyPay" onclick="OTCChange()">Waive One-time Charge
					</td>						
				</tr>
				<tr id="waiveOTCRemarkTr">						
					<td valign="bottom">
						Request for:
			        	<select id="requestSelectWaivePrepay" disabled>
						<option value="waiveOTC" label="Waive One-time Charge" class="installation">Waive Installation Service Charge</option>
						<option value="waiveOTC" label="Waive One-time Charge" class="activation" style="display:none">Waive Activation Service Charge</option>
						</select>
					</td>
					<td>
						<form:textarea rows="5" cols="50" id="waiveOTCRemark" path="textAreaInfoWO" onkeypress="return imposeMaxLength(this, 1999);"></form:textarea>
					</td>
					<td valign="bottom">
						<a href="#" class="nextbutton" onclick="approve('waiveot')">Submit</a>
					</td>
				</tr>									
				</table>
				</div>
				</div>
				</div>        		
			</td>	
		</tr>
	  
			
		<!-- Prepayment -->
		<form:hidden path="cashFsPrepay"/>
		<tr style="display:none;">
			<td class="table_title" colspan="4">Prepayment</td>
		</tr>
		<tr style="display:none;">
		<c:choose>
			<c:when test='${imsPaymentUI.payMtdType == "M"}'>
				<td colspan="3">
				<div id="prepayInfoCash">
					<table cellspacing='0'>
			  		<c:choose>
			  			<c:when test='${imsPaymentUI.cashFsPrepay == "Y"}'>	
			  				<tr <c:if test='${imsPaymentUI.isCC == "Y"}'>style="display:none"</c:if>>
			  					<td align='left'>
			  						<input type='radio' id="cashPrepay" value="N" onclick="cashPrepayChange()"/>
				  				</td>
								<td align="left">		
									Cash Prepayment:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td><form:input id="prepaymentvalue" path="prepayCash" disabled="true" />
								    <c:if test='${imsPaymentUI.isCC != "Y"}'><a href='#' class='nextbutton' onclick='more()'>More</a></c:if>
								 </td>
							</tr>
							<tr>
								<td align="left"><div id='prepayMore'>
									<input type='radio' id="fsPrepay" value="Y" onclick="fsPrepayChange()" checked>
								</div></td>
								<td align="left">
								<div id='prepayMore2'>	
										FS Collection of Cash:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
								<td><div id='prepayMore3'><form:input id='fscollection' path="prepayCash" disabled="true" />
										<c:if test='${imsPaymentUI.isCC == "Y"}'><a href='#' class='nextbutton' onclick='more()'>More</a></c:if>
									</div>
								</td>
							</tr>
			  			</c:when>	
			  			<c:otherwise>	
			  				<tr <c:if test='${imsPaymentUI.isCC == "Y"}'>style="display:none"</c:if>>
				  				<td align='left'>
				  					<input type='radio' id="cashPrepay" value="N" onclick="cashPrepayChange()" <c:if test='${imsPaymentUI.isCC != "Y"}'>checked</c:if>/>
				  				</td>
				  				<td align="left">		
									Cash Prepayment:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><form:input id="prepaymentvalue" path="prepayCash" disabled="true" />
								</td>
								<c:if test='${imsPaymentUI.isCC != "Y"}'><td align="left"><a href='#' class='nextbutton' onclick='more()'>More</a></td></c:if>
							</tr>
							<tr>
								<td align="left"><div id='prepayMore' <c:if test='${imsPaymentUI.isCC != "Y"}'>style="display:none"</c:if>>
									<input type='radio' id="fsPrepay" value="Y" onclick="fsPrepayChange()" <c:if test='${imsPaymentUI.isCC == "Y"}'>checked</c:if>>
								</div></td>
								<td align="left">
									<div id='prepayMore2' <c:if test='${imsPaymentUI.isCC != "Y"}'>style="display:none"</c:if>>
										FS Collection of Cash:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
								<td><div id='prepayMore3' <c:if test='${imsPaymentUI.isCC != "Y"}'>style="display:none"</c:if>><form:input id='fscollection' path="prepayCash" disabled="true" />
										<c:if test='${imsPaymentUI.isCC == "Y"}'><a href='#' class='nextbutton' onclick='more()'>More</a></c:if>
									</div>
								</td>
								</tr>
			  			</c:otherwise>
			  		</c:choose>	
					</table>
				</div>
				<div id="prepayInfoCreditCard" style="display:none">
					<table cellspacing="0">
        				<tr>
      			  			<tr><td align='left'>
			  					<!--<input type='radio' id="creditPrepay" value="N" checked/>-->
			  				</td>
							<td align="left">
								Credit Card Prepayment:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><form:input id="prepaymentvalue" path="prepayCc" disabled="true" />
							<td align="left">
								&nbsp;
								<!--<a href='#' class='nextbutton' onclick='more()'>More</a>-->
							</td>							
        				</tr>
					</table>
				</div>
				</td>
			</c:when>

			<c:otherwise>
				<td colspan="3">
				<div id="prepayInfoCash" style="display:none">
					<table cellspacing='0'>
			  		<c:choose>
			  		<c:when test='${imsPaymentUI.cashFsPrepay == "Y"}'>	
			  			<tr <c:if test='${imsPaymentUI.isCC == "Y"}'>style="display:none"</c:if>>
			  				<td align='left'>
			  					<input type='radio' id="cashPrepay" value="N" onclick="cashPrepayChange()"/>
			  				</td>
			  				<td align="left">		
								Cash Prepayment:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><form:input id="prepaymentvalue" path="prepayCash" disabled="true" />
							</td>
							<td align="left"><c:if test='${imsPaymentUI.isCC != "Y"}'><a href='#' class='nextbutton' onclick='more()'>More</a></c:if></td>
						</tr>
							<tr>
								<td align="left"><div id='prepayMore'>
									<input type='radio' id="fsPrepay" value="Y" onclick="fsPrepayChange()" checked>
								</div></td>
								<td align="left">
									<div id='prepayMore2'>
										FS Collection of Cash:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
								<td><div id='prepayMore3'><form:input id='fscollection' path="prepayCash" disabled="true" />
									
									<c:if test='${imsPaymentUI.isCC == "Y"}'><a href='#' class='nextbutton' onclick='more()'>More</a></c:if></div>
								</td>
								</tr>
			  			</c:when>	
			  			<c:otherwise>	
			  			<tr <c:if test='${imsPaymentUI.isCC == "Y"}'>style="display:none"</c:if>>
			  				<td align='left'>
			  					<input type='radio' id="cashPrepay" value="N" onclick="cashPrepayChange()" <c:if test='${imsPaymentUI.isCC != "Y"}'>checked</c:if>/>
			  				</td>
			  				<td align="left">		
								Cash Prepayment:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><form:input id="prepaymentvalue" path="prepayCash" disabled="true" />
							</td>
							<td align="left"><c:if test='${imsPaymentUI.isCC != "Y"}'><a href='#' class='nextbutton' onclick='more()'>More</a></c:if></tr>
							<tr>
								<td align="left"><div id='prepayMore' <c:if test='${imsPaymentUI.isCC != "Y"}'>style="display:none"</c:if> >
									<input type='radio' id="fsPrepay" value="Y" onclick="fsPrepayChange()" <c:if test='${imsPaymentUI.isCC == "Y"}'>checked</c:if>>
								</div></td>
								<td align="left">
									<div id='prepayMore2' <c:if test='${imsPaymentUI.isCC != "Y"}'>style="display:none"</c:if>>
										FS Collection of Cash:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
								<td><div id='prepayMore3' <c:if test='${imsPaymentUI.isCC != "Y"}'>style="display:none"</c:if>><form:input id='fscollection' path="prepayCash" disabled="true" />
									
									<c:if test='${imsPaymentUI.isCC == "Y"}'><a href='#' class='nextbutton' onclick='more()'>More</a></c:if></div>
								</td>
								</tr>
			  			</c:otherwise>
			  		</c:choose>	
					</table>
				</div>
				<div id="prepayInfoCreditCard">
					<table cellspacing="0">
        				<tr>
      			  			<tr><td align='left'>
			  					<!--<input type='radio' id="creditPrepay" value="N" checked/>-->
			  				</td>
							<td align="left">	
								Credit Card Prepayment:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><form:input id="prepaymentvalue" path="prepayCc" disabled="true" />
							<td align="left">
								&nbsp;
								<!--<a href='#' class='nextbutton' onclick='more()'>More</a>-->
							</td>
        				</tr>
					</table>
				</div>
				</td>
			</c:otherwise>
		</c:choose>	
		</tr>
		
		<!-- waive prepay approval -->
		<form:hidden path="waivedPrepay"/>
		<tr <c:if test="${ims_direct_sales eq true}">style="display:none"</c:if>>
			<td colspan="3">
				<div id="waivePrepayRequestentire" >
				<div id="waivePrepayRequestwhole" >
				<div id="waivePrepayRequest" style="display:none">
				<table cellspacing="0">
					<tr>
						<td>
							<input type='radio' id="waivePrepay" value="Y" onclick="waivePrepayChange()">Waive prepayment
						</td>						
					</tr>
					<tr id="waiveRemarkTr">						
						<td valign="bottom">
							Request for:
			        		<select id="requestSelectWaivePrepay" disabled>
							<option value="waivePrepay" label="Waive Prepayment">Waive Prepayment</option>
							</select>
						</td>
						<td>
							<form:textarea rows="5" cols="50" id="waiveRemark" path="textAreaInfoWP" onkeypress="return imposeMaxLength(this, 1999);"></form:textarea>
						</td>
						<td valign="bottom">
							<a href="#" class="nextbutton" onclick="approve('waiveprepay')">Submit</a>
						</td>
					</tr>									
				</table>
				</div>
				</div>
				</div>        		
			</td>	
		</tr>
		

		<!-- Bill Medium -->
		<tr style="display:none;">
			<td class="table_title" colspan="4">Bill Media</td>
		</tr>
		<tr style="display:none;">
			<c:choose>
				<c:when test='${imsPaymentUI.billMedia == "P"}'>
				<td align="left">
					<input type="radio" id="email" name="payment" value="E"/>by Email
				</td>
				<td colspan="2">
					<input type="radio" id="paper" name="payment" value="P" checked/>by Paper ($20 per month)
				</td>
				</c:when>
				<c:otherwise>
				<td align="left">
					<input type="radio" id="email" name="payment" value="E" checked/>by Email
				</td>
				<td colspan="2">
					<input type="radio" id="paper" name="payment" value="P"/>by Paper ($20 per month)
				</td>
				</c:otherwise>
			</c:choose>
			<form:hidden path="billMedia"/>
	    </tr>
		<tr style="display:none;">
				<td colspan="2" align="left">
					ebill Email:
					<form:input path="emailAddr" onblur="checkEmail()" cssStyle="width:205px;"/>
				</td>
				<td colspan="2">
					<form:errors path="emailAddr" cssClass="error" />
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
		
		<tr style="display:none;">
        	<!-- Sales Info. -->
			<td class="table_title" colspan="4">Sales Info.</td>
		</tr>
		
		<tr style="display:none;">
        	<td align="left">Application method:</td>
        	<td align="left">
        		<form:select path="appMethod" onchange="getSourceCode()" >
        			<form:option value=""></form:option>
        			<form:options items="${AppMethodList}" itemLabel="key" itemValue="value"/>
				</form:select>							
				<div style="display: none;">				
					<form:select id="appMethodDesc" path="appMethodDesc">				
					<form:option value=""/>
					<form:options items="${AppMethodList}" itemValue="key" itemLabel="key"/>
					</form:select>
				</div>			
			</td>
			<td>
				<form:errors path="appMethod" cssClass="error" />
			</td>
       	</tr>
       	<tr style="display:none;">
        	<td align="left">Salesman code:</td>
        	<td align="left">
        		
        			<c:choose>
        				<c:when test='${imsPaymentUI.isCC == "Y"}'>
        				
        					<form:select path="sourcecode" onchange="" >

        					</form:select>
        				</c:when>
        				<c:otherwise>
        					<form:select id="sourcecode" path="shopCd" disabled="true" >
        						<form:options items="${shopCdList}" />
        					</form:select>
        				</c:otherwise>
        			</c:choose>
			</td>
			<td>
				<c:choose>
       				<c:when test='${imsPaymentUI.isCC == "Y"}'>
       					<form:errors path="sourcecode" cssClass="error" />
        			</c:when>
        			<c:otherwise>
        				<form:errors path="shopCd" cssClass="error" />
        			</c:otherwise>
        		</c:choose>
				
			</td>
       	</tr>
       	<tr style="display:none;">
        	<td align="left">Staff No.:</td>
        	<td align="left">
        		
        		<c:choose>
       				<c:when test='${imsPaymentUI.isCC == "Y"}'>
       					<form:input id="staffnum" path="salesCd" maxlength="10" onkeyup="keyUpOnStaffNum()" onchange="getCCSalesInfo(); getCCNewSourceCdAppMth();"/>
        			</c:when>
        			<c:otherwise>
        				<form:input id="staffnum" path="salesCd" maxlength="10" onkeyup="keyUpOnStaffNum()" onchange="getSalesInfo()"/>
        			</c:otherwise>
        		</c:choose>
        		
        	</td>
        	<td>
        		<form:errors path="salesCd" cssClass="error" />
        	</td>
       	</tr>
       	<tr style="display:none;">
        	<td align="left">Staff Name:</td>
        	<td align="left">
	        	<c:choose>
	        		<c:when test='${imsPaymentUI.isCC != "Y"}'>
	        			<form:input id="staffname" path="salesName" readonly="true"/>
	        		</c:when>
	        		<c:otherwise>
	        			<form:input id="staffname" path="salesName" />
	        		</c:otherwise>
	        	</c:choose>
        		
        		
        	</td>
        	<td>
        		<form:errors path="salesName" cssClass="error" />
        	</td>
       	</tr>
       	<tr style="display:none;">
        	<td align="left">Contact No.:</td>
        	<td align="left">
        		<c:choose>
	        		<c:when test='${imsPaymentUI.isCC != "Y" && ims_direct_sales ne true}'> 
	        			<form:input maxlength="8" id="contactnum" path="salesContactNum" readonly="true"/>
	        		</c:when>
	        		<c:otherwise>
	        			<form:input maxlength="8" id="contactnum" path="salesContactNum" />  
	        		</c:otherwise>
	        	</c:choose>
	        	<br/>  
	        	<form:errors path="salesContactNum" cssClass="error" />        		
        	</td>
        	<c:if test="${ims_direct_sales ne true}"> 
        	<td>
        		<c:choose>
       				<c:when test='${imsPaymentUI.isCC == "Y"}'>
       					<button type="button" onClick="getCCSalesInfo()" >Refresh</button>
        			</c:when>
        			<c:otherwise>
        				<button type="button" onClick="formSubmit('refresh')" >Refresh</button>
        			</c:otherwise>
        		</c:choose>
        	</td>
        	</c:if>
       	</tr>
       	
       	<c:if test='${imsPaymentUI.isCC =="Y" }'>
       		<tr style="display:none;">
	        	<td align="left">Call date:</td>
	        	<td align="left">
	        		<form:input path="callDate" />        		
	        	</td>
	        	<td>
	        		<form:errors path="callDate" cssClass="error" />
	        	</td>
	       	</tr>
	       	<tr style="display:none;">
	        	<td align="left">Call time:</td>
	        	<td align="left">
	        		<form:input path="callTime" />        		
	        	</td>
	        	<td>
	        		<form:errors path="callTime" cssClass="error" />
	        	</td>
	       	</tr>
	       	<tr style="display:none;">
	        	<td align="left">Position No.:</td>
	        	<td align="left">
	        		<form:input id="pno" path="pno" maxlength="8" />        		
	        	</td>
	        	<td>
	        		<form:errors path="pno" cssClass="error" />
	        	</td>
	       	</tr>
       	</c:if>
       	
		<!-- End Sales Info. -->
		<tr style="display:none;">
			<td>&nbsp;</td>
        	<td colspan="2" align="right" width="66%">
        		Suspend reason code:
        		<form:select id="suspendSelect" path="sReasonCd">
        			<form:option value=""></form:option>
        			<form:options items="${suspendList}" itemLabel="value" itemValue="key"/>
				</form:select>
       		</td>
       		<td align="right" width="10%">
       		<c:choose><c:when test='${imsPaymentUI.submitTag == "C"}'>
       			<a href="#" class="nextbutton">Suspend</a>
       		</c:when>
       		<c:otherwise>
       			<a href="#" class="nextbutton" onclick="suspend()">Suspend</a>
       		</c:otherwise></c:choose>
    		</td>
       	</tr>
       	<tr><td><br></td></tr>
       	<c:choose><c:when test='${imsPaymentUI.orderActionInd == "W"}'>
       	<tr style="display:none;">
			<td>&nbsp;</td>
        	<td colspan="2" align="right" width="66%">
        		Cancel reason code:
        		<form:select id="cancelSelect" path="cReasonCd">
        			<form:option value=""></form:option>
        			<form:options items="${cancelList}" itemLabel="value" itemValue="key"/>
				</form:select>
       		</td>
       		<td align="right" width="10%">
       		<c:choose><c:when test='${imsPaymentUI.submitTag == "C"}'>
       			<a href="#" class="nextbutton">Cancel</a>
       		</c:when>
       		<c:otherwise>
       			<a href="#" class="nextbutton" onclick="cancel()">Cancel</a>
       		</c:otherwise></c:choose>
    		</td>
       	</tr>
       	</c:when>
       	<c:otherwise></c:otherwise></c:choose>
       	<tr><td><br></td></tr>
       	<tr>
			<td colspan="3" >&nbsp;</td>
			<td align="right">
			<div>
					<c:choose>
						<c:when test='${imsPaymentUI.submitTag == "C"}'>
							<a href="#" class="nextbuttonS">Continue</a>
						</c:when>
						<c:otherwise>
							<a href="#" class="nextbuttonS" onClick="formSubmit();">Continue</a>				
						</c:otherwise>
					</c:choose>					
			</div>
				<input type="hidden" name="currentView" value="imspayment"/>
			</td>
		</tr>
		</table>	     
      </td>
  </tr>
</table>
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>