<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link href="css/ssc2.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.customComboBox.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog_1ams.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script> 

<!-- <link rel="stylesheet" type="text/css" href="./css/ims_prereg.css" /> -->

<%@ include file="imsloadingpanel.jsp" %>
<%@ page import="			
					org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.ims.dto.ui.VASDetailUI,
					com.bomwebportal.ims.dto.ui.BasketDetailUI,
					com.bomwebportal.ims.dto.BasketDetailsDTO,
					java.util.*
					"
%>

<style>

</style>
<script type="text/javascript">
var mobileOfferInd =  '<%= session.getAttribute("mobileOfferInd") %>';
var isValid;
// $(document).ready(function() {		
// 	var bTable = $(".preRegSearchRecds").dataTable({
// 		"sScrollY": "140px",
// 		"iDisplayLength" : -1, 
// 		"bPaginate": false,
// 		"sDom": 'lrt',
// 		"bSort" : false 
// 	});
	
// 	$("#tabs a").removeClass("ui-widget-content a").css('color', '#222222');
// 	$("#tabs a").addClass("table_button").css('color','#f3f3f3');		
// 	$("#tabs a").hover(function() {
// 	        $(this).css("cursor","hand");
// 	        $(this).css("cursor","pointer");
// 	});
	
// 	$("#header").hide();
	
// });



// function searchSubmit(){
// 	if($("#docType").val()==null ||$("#docType").val()==""||$("#docNum").val()==null ||$("#docNum").val()==""){
// 		alert("Please input the id doc type and num for search record.");
// 		return false;
// 	}else if ("${ims_direct_sales}" == "true" && ($("#custLastName")==null ||$("#custLastName").val()==""||$("#custFirstName").val()==null ||$("#custFirstName").val()=="")){
// 		alert("Please fillin the cust name for search record.");
// 		return false;
// 	}
// 	$("#searched").val("Y");	
// 	$("#action").val("SEARCH");
// 	formSubmit();

// }


function preRegSubmit(){


	if($("#submitted").val()!=null && $("#submitted").val()=="Y" ){
		alert("You've aleady submit Pre-registration successfully before.");
		return false;
	}
// 	if($("#searched").val()==null ||$("#searched").val()!="Y"){

// 		alert("Please search the pre reg records before firstly.");
// 		return false;
// 	}

	if((document.getElementById('mr').checked==false&&document.getElementById('ms').checked==false)||
	   isEmpty(document.getElementById('onlineSalesReq.first_name').value)||
	   isEmpty(document.getElementById('onlineSalesReq.last_name').value)||
// 	   isEmpty(document.getElementById('onlineSalesReq.staff_id').value)||
	   isEmpty(document.getElementById('onlineSalesReq.cont_phone_no').value)||
// 	   isEmpty(document.getElementById('onlineSalesReq.email_addr').value)||
	   isEmpty(document.getElementById('onlineSalesReq.doc_type').value)||
	   isEmpty(document.getElementById('onlineSalesReq.doc_num').value)||
	  ( "${ims_direct_sales}" == 'true' && isEmpty(document.getElementById('onlineSalesReq.staff_id').value))
	   ){
		alert("Please fullfill the customer information.");
		return false;
	}
	if(document.getElementById('cust_name_error_msg').innerHTML!="" ||
			   document.getElementById('PhoneNum_error_msg').innerHTML!="" 
					)
		return false;
		
	var answer = confirm("Are you sure to go Pre-reg?");
	
	if (answer) {
	
		document.getElementById("onlineSalesReq.doc_num").value = document.getElementById("onlineSalesReq.doc_num").value.toUpperCase();
		document.getElementById("onlineSalesReq.doc_type").disabled = false;
		document.getElementById("onlineSalesReq.doc_num").disabled = false;
		document.getElementById('onlineSalesReq.last_name').disabled = false;
		document.getElementById('onlineSalesReq.first_name').disabled = false;
// 		$('#submitform').submit();
		
	$("#action").val("SUBMIT");
	formSubmit();
	parent.hide_Form();
// 		setTimeout(function(){parent.hide_Form();},3000);
	}
}

function closeWindow(){
	parent.hide_Form();
}

function isEmpty(val){
	if(val==null || val == "")
		return true;
	else
		return false;
}

	
	function formSubmit() {

			document.imspreregistrationForm.submit();		
			

	}
	
	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	
	function checkSearchDoc(){
		if(checkDoc(document.getElementById('docType').value,document.getElementById('docNum').value)){
			document.getElementById('name_error').display = "none";
			document.getElementById('name_error_msg').innerHTML = "";
			
		}else{
			document.getElementById('name_error').display = "";
			document.getElementById('name_error_msg').innerHTML = "Invalid document number, please enter again.";
		}
		
	}
	function checkSubmitDoc(){
		if(checkDoc(document.getElementById('onlineSalesReq.doc_type').value,document.getElementById('onlineSalesReq.doc_num').value)){
			document.getElementById('cust_name_error').display = "none";
			document.getElementById('cust_name_error_msg').innerHTML = "";
		}else{
			document.getElementById('cust_name_error').display = "";
			document.getElementById('cust_name_error_msg').innerHTML = "Invalid document number, please enter again.";
		}
		
	}
	  function trimString(x) {
			while (x.substring(0, 1) == " ") {
				templength = x.length;
				x = x.substring(1, templength);
			}
	
			while (x.substring(x.length - 1, x.length) == " ") {
				x = x.substring(0, x.length - 1);
			}
	
			x = x.replace(/[\W\s]/g, "");
			return x;
		}
		function toAscii(ip_char_inputValue) {
			var symbols = " !\"#$%&'()*+'-./0123456789:;<=>?@";
			var loAZ = "abcdefghijklmnopqrstuvwxyz";

			symbols += loAZ.toUpperCase();
			symbols += "[\\]^_`";
			symbols += loAZ;
			symbols += "{|}~";
			var loc;
			loc = symbols.indexOf(ip_char_inputValue);

			if (loc > -1) {
				Ascii_Decimal = 32 + loc;
				return (32 + loc);
			}
			return (0); // If not in range 32-126 return ZERO 
		}

	function checkDoc(type,num) {
		var checkInput = num;	
		
		if ( checkInput==null ||  checkInput.trim() == "")
		{
			
// 			return false;
		}
		var docNum = num.replace('(','');		
		docNum = docNum.replace(')','');
		if (type=="HKID") {
			
			if(docNum.length<8)
				return false;
			else{
			
			var docl = docNum.substring(0,docNum.length-1);
			var cl = docNum.substring(docNum.length-1,docNum.length);
			var attName = docl + cl;
			var mult = new Array(3, 8, 7, 6, 5, 4, 3, 2);
			var x = 11;
			var checkdigit = -1;
			var ch;
			var endValue;
			var z = 0;
			var idlength;
			var i;
			var idx;
			var alpha_count;
			var hkid = trimString(attName);
// 			alert(hkid);
			idlength = hkid.toString().length;
			
			if ( docl.toString().length != 0 && cl.toString().length != 0 ) 
			{
				
				if (idlength == 8) {
					alpha_count = 1;
					idx = 1;
				} else if (idlength == 9) {
					alpha_count = 2;
					idx = 0;
				} else {
// 					alert(idlength);
					return false;
				}
				for (i = 0; i < alpha_count; i++) {
					ch = hkid.charAt(i);
					z = z + (toAscii(ch) - toAscii('A') + 1) * mult[idx];
					idx = idx + 1;
				}

				for (i = alpha_count; i < idlength - 1; i++) {
					ch = hkid.charAt(i);
					z = z + ch * mult[idx];

					idx = idx + 1;
				}
				endValue = z % x;
				if (endValue == 0) {
					checkdigit = 0;
				} else {
					if (x - endValue < 10) {
						checkdigit = x - endValue;
					} else if (x - endValue == 10) {
						checkdigit = "A";
					} else if (x - endValue == 11) {
						checkdigit = "B";
					} else if (x - endValue == 12) {
						checkdigit = "C";
					} else {
						checkdigit = x - endValue + toAscii('A') - 10;
					}
				}
// 				alert(checkdigit+cl);
				if (checkdigit == cl) {
					
					attName.value = hkid;
					return true;					
				} else {

					return false;
				}
			} else {
					return false;
			}
			
			
		}
		} else if ( checkInput.length == 0 ) {
				return false;
		} else {
			//Passport validation
			var patternString = /^[A-Z0-9]*$/;
// 			alert(checkInput);
			if ( checkInput.length < 6){
// 				alert('a');
// 				document.getElementById("idNo_warning").innerHTML = "Passport no. must include at least 6 characters.";
				return false;
			}else if (!checkInput.toUpperCase().match(patternString)){
// 				alert(checkInput.toUpperCase());
// 				document.getElementById("idNo_warning").innerHTML = "Invalid document number, please enter again.";
				return false;
			}			
// 			document.getElementById("idNo_warning").innerHTML = "";
		}
		return true;
	}
	
	
	function checkMobile() {
		var MobileNum = document.getElementById('onlineSalesReq.cont_phone_no').value;
		var MobilePattern=/[569][0-9][0-9][0-9][0-9][0-9][0-9][0-9]/;
		if (MobileNum.match(MobilePattern)){
			document.getElementById('PhoneNum_error').style.display = "none";
			document.getElementById('PhoneNum_error_msg').innerHTML = "";
		}else if(MobileNum==""){
			document.getElementById('PhoneNum_error').style.display = "";
			document.getElementById('PhoneNum_error_msg').innerHTML = "Please input mobile no.";

		}else{
			document.getElementById('PhoneNum_error').style.display = "";
			document.getElementById('PhoneNum_error_msg').innerHTML = "Invalid Mobile number.";
		}
	}
	
	function checkEmail() {
		var email = document.getElementById('onlineSalesReq.email_addr').value;
		var emailPattern=/^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,4})$/;
		if (email.match(emailPattern)){
			document.getElementById('Email_error').style.display = "none";
			document.getElementById('Email_error_msg').innerHTML = "";
		}else if(MobileNum==""){
			document.getElementById('Email_error').style.display = "";
			document.getElementById('Email_error_msg').innerHTML = "Please input email address.";

		}else{
			document.getElementById('Email_error').style.display = "";
			document.getElementById('Email_error_msg').innerHTML = "Invalid Email address.";
		}
	}
	function proceedPrereg(){
		$(".preRegSubmit").css('display','inline');
	}
	
	function ValidateIDNUM(){
		
		document.getElementById('onlineSalesReq.doc_num').value = document.getElementById('onlineSalesReq.doc_num').value.toUpperCase();
		
		var doctype = document.getElementById('onlineSalesReq.doc_type').value;
		var docNum = document.getElementById('onlineSalesReq.doc_num').value;
		
		if (doctype == "PASS" ){
			if (docNum.length > 0){
				var patternString = "^[A-Z0-9]*$";
				if (docNum.length < 6){
					document.getElementById('cust_name_error').display = "";
					document.getElementById('cust_name_error_msg').innerHTML = "Invalid document number, please enter again.";
					
				}else if (docNum.match(patternString)){
					document.getElementById('cust_name_error').display = "none";
					document.getElementById('cust_name_error_msg').innerHTML = "";
				}else{
					document.getElementById('cust_name_error').display = "";
					document.getElementById('cust_name_error_msg').innerHTML = "Invalid document number, please enter again.";
				}	
			}
		}else{
			document.getElementById('cust_name_error').display = "none";
			document.getElementById('cust_name_error_msg').innerHTML = "";
		}
		
// 		change2Business(doctype == "BS");
		isValid = false;	
		getImsCustInfo();
// 		checkCreateCOrder();
	}
	
	function getImsCustInfo(){
		
		if("${ims_direct_sales}" == 'true'){
// 		insertPageTrack();
		}
		
		$.ajax({
			url : 'preregcustinfo.html?docType=' + document.getElementById('onlineSalesReq.doc_type').value + '&idDocNum=' + document.getElementById('onlineSalesReq.doc_num').value.toUpperCase()
 										   + '&t=' + new Date(),
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
			    	alert("<spring:message code="ims.pcd.imsinstallation.msg.031"/>"+document.getElementById('onlineSalesReq.doc_type').value + "/" + document.getElementById('onlineSalesReq.doc_num').value+"<spring:message code="ims.pcd.imsinstallation.msg.031a"/>");
			    	var t = document.getElementById("onlineSalesReq.doc_num");
				    t.focus();
				    }
				document.getElementById('cust_name_error').display = "none";
				document.getElementById('cust_name_error_msg').innerHTML = "";
				document.getElementById('PhoneNum_error').style.display = "none";
				document.getElementById('PhoneNum_error_msg').innerHTML = "";
			},
			success : function(msg) {	
				var count=0;
				var idDocNum = document.getElementById('onlineSalesReq.doc_num').value;

				if("${ims_direct_sales}" == "true"){
// 					$("#existingCustomerConflictWithName").val("N");
// 					$("#custNo").val("");
				}
				$.each(eval(msg), function(i, item) {
					if(item.isValid == true && mobileOfferInd == "Y"){	
						document.getElementById("onlineSalesReq.doc_type").disabled = true;
						document.getElementById("onlineSalesReq.doc_num").disabled = true;
						document.getElementById('cust_name_error').display = "none";
						document.getElementById('cust_name_error_msg').innerHTML = "";
						document.getElementById('PhoneNum_error').style.display = "none";
						document.getElementById('PhoneNum_error_msg').innerHTML = "";
						if(item.title=="MS" || item.title=="MISS" ){
							document.getElementById('ms').checked=true;
						}
						else{
							document.getElementById('mr').checked=true;
						}
// 						document.getElementById('onlineSalesReq.title').disabled = true;
						document.getElementById('onlineSalesReq.last_name').value = item.lastName;
						document.getElementById('onlineSalesReq.last_name').disabled = true;
						document.getElementById('onlineSalesReq.first_name').value = item.firstName;
						document.getElementById('onlineSalesReq.first_name').disabled = true;
// 						document.getElementById('companyName').value = item.companyName;
// 						document.getElementById('companyName').disabled = true;
// 						document.getElementById('contactPersonName').value = item.contactPersonName==null||item.contactPersonName.search("null")>-1?"":item.contactPersonName;

						document.getElementById('onlineSalesReq.cont_phone_no').value = item.mobile;

// 						document.getElementById('cntFixLineNum').value = item.fixLine;
						//Tony
// 						if(item.isExisting == true){
// 							document.getElementById('optInfoButton').innerHTML = "";
// 							$(".optInfo").css("display", "none");
// 							jsphasBBDialUp=true;//steven 20130711
// 							$("#hasBBDailUp").val("Y");
// 						}else{
// 							jsphasBBDialUp=false;//steven 20130711
// 							$("#hasBBDailUp").val("N");
// 						}
						//Tony End
						document.getElementById('onlineSalesReq.doc_type').disabled = true;
						document.getElementById('onlineSalesReq.doc_num').disabled = true;
// 						$("#lob").val("IMS");
// 						$("#custNo").val(item.custNo);
						if(item.isImsBlacklist == true){
// 							$("#blacklistInd").val("Y");
							document.getElementById('common_warning').style.display = "";
							if(item.j == 0){
								document.getElementById('common_warning_msg').innerHTML = "<spring:message code="ims.pcd.imsinstallation.msg.032"/>";
							}
// 							document.getElementById('common_warning_msg').innerHTML = //"Blacklist Customer<br>" + 
// 								document.getElementById('common_warning_msg').innerHTML + "<spring:message code="ims.pcd.imsinstallation.066"/>" + item.acctNo + "<spring:message code="ims.pcd.imsinstallation.067"/>" + item.osAmt + "<br>";
						}else if(item.isImsBlacklist == false){
// 							$("#blacklistInd").val("N");
							document.getElementById('common_warning').style.display = "none";
							document.getElementById('common_warning_msg').innerHTML = "";
						}
// 						if(item.reserveResult == "-7"){
// 							document.getElementById('error_login').style.visibility = "hidden";						
// 							document.getElementById('warning_login').style.visibility = "visible";
// 							document.getElementById('warning_login_msg').innerHTML = item.reserveErrorText;
// 						}
// 						$("#isFromBOM").val("Y");
					}
					if(item.isValid == true && "${ims_direct_sales}" == "true" ){
						document.getElementById('cust_name_error').display = "none";
						document.getElementById('cust_name_error_msg').innerHTML = "";
						document.getElementById('PhoneNum_error').style.display = "none";
						document.getElementById('PhoneNum_error_msg').innerHTML = "";
						
						if (document.getElementById('onlineSalesReq.first_name').value.length>0 && document.getElementById('onlineSalesReq.last_name').value.length>0){  
							var validNameCheck = document.getElementById('onlineSalesReq.last_name').value == item.lastName.ltrim().rtrim()  && document.getElementById('onlineSalesReq.first_name').value == item.firstName.ltrim().rtrim();
							if (validNameCheck) {
// 								$("#existingCustomerConflictWithName").val("N");
								document.getElementById('common_warning').style.display = "none";
								document.getElementById('common_warning_msg').innerHTML = "";
							} else {
// 								$("#existingCustomerConflictWithName").val("Y");
								document.getElementById('common_warning').style.display = "block";
								document.getElementById('common_warning_msg').innerHTML = "<spring:message code='ims.pcd.imsinstallation.msg.036' />";
							}
						}
						
// 						if(item.isExisting == true){
// 							document.getElementById('optInfoButton').innerHTML = "";
// 							$(".optInfo").css("display", "none");
// 							jsphasBBDialUp=true;//steven 20130711
// 							$("#hasBBDailUp").val("Y");
// 						}else{
// 							jsphasBBDialUp=false;//steven 20130711
// 							$("#hasBBDailUp").val("N");
// 						}
// 						$("#lob").val("IMS");
// 						if($("#existingCustomerConflictWithName").val()=="Y"){
// 							$("#custNo").val("");
// 						}else{
// 							$("#custNo").val(item.custNo);
// 						}
						if(item.isImsBlacklist == true){
// 							$("#blacklistInd").val("Y");
							document.getElementById('common_warning').style.display = "";
							if(item.j == 0){
								document.getElementById('common_warning_msg').innerHTML = "BL";
							}
						}else if(item.isImsBlacklist == false){
// 							$("#blacklistInd").val("N");
							document.getElementById('common_warning').style.display = "none";
							document.getElementById('common_warning_msg').innerHTML = "";
						}
// 						if(item.reserveResult == "-7"){
// 							document.getElementById('error_login').style.visibility = "hidden";						
// 							document.getElementById('warning_login').style.visibility = "visible";
// 							document.getElementById('warning_login_msg').innerHTML = item.reserveErrorText;
// 						}
// 						$("#isFromBOM").val("Y");
						count++;
						isValid = item.isValid; 
					}
					else if(item.isValid == true){
						document.getElementById('cust_name_error').display = "none";
						document.getElementById('cust_name_error_msg').innerHTML = "";
						document.getElementById('PhoneNum_error').style.display = "none";
						document.getElementById('PhoneNum_error_msg').innerHTML = "";
						if(item.title=="MS" || item.title=="MISS" ){
							document.getElementById('ms').checked=true;
						}
						else{
							document.getElementById('mr').checked=true;
						}
						
// 						document.getElementById('onlineSalesReq.title').disabled = true;
						document.getElementById('onlineSalesReq.last_name').value = item.lastName;
						document.getElementById('onlineSalesReq.last_name').disabled = true;
						document.getElementById('onlineSalesReq.first_name').value = item.firstName;
						document.getElementById('onlineSalesReq.first_name').disabled = true;
						
						//Tony
// 						if(item.isExisting == true){
// 							document.getElementById('optInfoButton').innerHTML = "";
// 							$(".optInfo").css("display", "none");
// 							jsphasBBDialUp=true;//steven 20130711
// 							$("#hasBBDailUp").val("Y");
// 						}else{
// 							jsphasBBDialUp=false;//steven 20130711
// 							$("#hasBBDailUp").val("N");
// 						}
						//Tony End
						document.getElementById("onlineSalesReq.doc_type").disabled = true;
						document.getElementById("onlineSalesReq.doc_num").disabled = true;

						if(item.isImsBlacklist == true){
// 							$("#blacklistInd").val("Y");
							document.getElementById('common_warning').style.display = "";
							if(item.j == 0){
								document.getElementById('common_warning_msg').innerHTML = "Blacklist Customer<br>";
							}
// 							document.getElementById('common_warning_msg').innerHTML = //"Blacklist Customer<br>" + 
// 								document.getElementById('common_warning_msg').innerHTML + "Account No.: " + item.acctNo + " Outstanding Balance: " + item.osAmt + "<br>";
						}else if(item.isImsBlacklist == false){
// 							$("#blacklistInd").val("N");
							document.getElementById('common_warning').style.display = "none";
							document.getElementById('common_warning_msg').innerHTML = "";
						}
// 						if(item.reserveResult == "-7"){
// 							document.getElementById('error_login').style.visibility = "hidden";						
// 							document.getElementById('warning_login').style.visibility = "visible";
// 							document.getElementById('warning_login_msg').innerHTML = item.reserveErrorText;
// 						}
// 						$("#isFromBOM").val("Y");
					}else if(item.isValid == false){
					 
						document.getElementById('cust_name_error').display = "";
						document.getElementById('cust_name_error_msg').innerHTML = "Invalid document number, please enter again.";
						
						}else if(item.custNo == null || item.custNo == ""){

				
						document.getElementById('onlineSalesReq.doc_type').disabled = false;
						document.getElementById('onlineSalesReq.doc_num').disabled = false;

// 						document.getElementById('onlineSalesReq.title').disabled = false;
						document.getElementById('onlineSalesReq.last_name').value = "";
						document.getElementById('onlineSalesReq.last_name').disabled = false;
						document.getElementById('onlineSalesReq.first_name').value = "";
						document.getElementById('onlineSalesReq.first_name').disabled = false;

						document.getElementById('onlineSalesReq.cont_phone_no').value = "";

						document.getElementById('common_warning').style.display = "none";
						document.getElementById('common_warning_msg').innerHTML = "";
						document.getElementById('cust_name_error').display = "none";
						document.getElementById('cust_name_error_msg').innerHTML = "";
						document.getElementById('PhoneNum_error').style.display = "none";
						document.getElementById('PhoneNum_error_msg').innerHTML = "";
// 						if(item.reserveResult == "-7"){
// 							document.getElementById('error_login').style.visibility = "hidden";						
// 							document.getElementById('warning_login').style.visibility = "visible";
// 							document.getElementById('warning_login_msg').innerHTML = item.reserveErrorText;
// 						}
					}
					count++;
					//Gary
					
					isValid = item.isValid;
					////alert("isValid111:"+isValid);
					
// 					if(item.createCOrderInd == "Y"){
				//	alert('Share with FSA '+item.relatedFSA);	
// 						$("#createCOrderInd").val("Y");
// 						$("#relatedFSA").val(item.relatedFSA);
// 					}else{
						//alert('no c order');
// 						$("#createCOrderInd").val("N");
// 						$("#relatedFSA").val("");
// 					}
				});

				if(count==0 && (idDocNum==null || idDocNum=="")){
					
				}
				else if(count==0 && mobileOfferInd == "Y"){
					document.getElementById("onlineSalesReq.doc_type").disabled = true;
					document.getElementById("onlineSalesReq.doc_num").disabled = true;
// 					document.getElementById('txt_title').selectedIndex = 0;
// 					document.getElementById('onlineSalesReq.title').disabled = false;

					
					document.getElementById('common_warning').style.display = "none";
					document.getElementById('common_warning_msg').innerHTML = "";
// 					document.getElementById('cust_name_error').display = "none";
// 					document.getElementById('cust_name_error_msg').innerHTML = "";
					document.getElementById('PhoneNum_error').style.display = "none";
					document.getElementById('PhoneNum_error_msg').innerHTML = "";
					//Gary
					isValid = true;
				}
				else if(count==0 &&  "${ims_direct_sales}" == "true"){
				
					document.getElementById('common_warning').style.display = "none";
					document.getElementById('common_warning_msg').innerHTML = "";
// 					document.getElementById('cust_name_error').display = "none";
// 					document.getElementById('cust_name_error_msg').innerHTML = "";
					document.getElementById('PhoneNum_error').style.display = "none";
					document.getElementById('PhoneNum_error_msg').innerHTML = "";
	
					isValid = true;
				}
				else if(count==0){ 
					document.getElementById('onlineSalesReq.doc_type').disabled = false;
					document.getElementById('onlineSalesReq.doc_num').disabled = false;

// 					document.getElementById('onlineSalesReq.title').disabled = false;
					document.getElementById('onlineSalesReq.last_name').value = "";
					document.getElementById('onlineSalesReq.last_name').disabled = false;
					document.getElementById('onlineSalesReq.first_name').value = "";
					document.getElementById('onlineSalesReq.first_name').disabled = false;

					document.getElementById('onlineSalesReq.cont_phone_no').value = "";

					document.getElementById('common_warning').style.display = "none";
					document.getElementById('common_warning_msg').innerHTML = "";
// 					document.getElementById('cust_name_error').display = "none";
// 					document.getElementById('cust_name_error_msg').innerHTML = "";
					document.getElementById('PhoneNum_error').style.display = "none";
					document.getElementById('PhoneNum_error_msg').innerHTML = "";
					//Gary
					isValid = true;

				}


				
			}
		});
	}
</script>





	<form:form name="imspreregistrationForm" commandName="preRegistrationUI" method="POST" >
	<form:hidden path="onlineSalesReq.item_id" />

<!-- <div class="preRegSearch" > -->


<!-- 	<table > -->
									
<!-- 									<tr> -->
<!-- 										<td ><b>ID Doc Num</b></td> -->
<!-- 										<td > -->
<%-- 										<form:select id="docType" path="docType" > --%>
<%-- 											<form:option value="HKID" label="HKID"/> --%>
<%-- 											<form:option value="PASS" label="PASSPORT"/> --%>
<%-- 										</form:select> --%>
<%-- 										<form:input id="docNum" path="docNum" cssStyle="text-transform:uppercase;"onblur="checkSearchDoc()"/> --%>
<!-- 										</td> -->
<!-- 										<td id="name_error"><span id="name_error_msg" style="color:#c00000"></span></td> -->
<!-- 									</tr>		 -->
									
									
<!-- 			<tr> -->
<!-- 			<td align="left">Family Name </td> -->
<!-- 			<td> -->
<%-- 					<form:input id="custLastName" path="lastName" maxlength="40" disabled="false" /> --%>

<!-- 			</td> -->
<!-- 			<td align="left">Given Name</td> -->
<!-- 			<td> -->

<%-- 					<form:input id="custFirstName" path="firstName" maxlength="40" disabled="false"/> --%>
				
<!-- 			</td> -->
<!-- 		</tr> -->

<!-- 	</table> -->
<!-- 	</div> -->

<!-- 		<div class="preRegSearchButton"> -->
<!-- 							<a class="nextbutton" onclick="searchSubmit();" href="javascript:void(0)">Search</a> -->
<!-- 		</div> -->
<!-- 		<br> -->

<div class="preRegSearchTable" id="tabs" >
<table height="30" style="width: 50%;">
	<tr>
		<td class="contenttextgreen" align="center">
		 	PON to Village Enquiry Details
		</td>
	</tr>
</table>
	

		<table   bgcolor="#FFFFFF" id="basketGrid" class = "preRegSearchRecds" style="width: 50%; border: 1px solid #aaaaaa;">
			<thead>
				<tr class="contenttextgreen " >
					<td class="table_title">Log ID</td>
					<td class="table_title">App Date</td>
					<td class="table_title">Flat</td>
					<td class="table_title">Floor</td>
					<td class="table_title">SB address</td>
					<td class="table_title">ONU Status</td>


				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${empty preRegistrationUI.preRegSearchList}">
				<tr>
					<td colspan="6" style="text-align: left">No record can be found</td>
					</tr>
					</c:when>
			<c:otherwise>
				<c:forEach items="${preRegistrationUI.preRegSearchList}" var="record" varStatus="status">
			
			
				<tr <c:if test="${status.index >= 20}"> style="display:none"</c:if>>
					<td style="border: 1px solid #aaaaaa;">${record.preRegId}</td>
					<td style="border: 1px solid #aaaaaa;">${record.preRegDate}</td>
					<td style="border: 1px solid #aaaaaa;">${record.flat}</td>
					<td style="border: 1px solid #aaaaaa;">${record.floor}</td>
					<td style="border: 1px solid #aaaaaa;">${preRegistrationUI.serbdynoAddr}</td>
					<td style="border: 1px solid #aaaaaa;">${record.onuStatus}</td>
<%-- 					<td>${record.custName}</td> --%>
<%-- 					<td>${record.fsa}</td> --%>
<%-- 					<td>${record.mobNum}</td> --%>
<%-- 					<td>${record.onuRemarks}</td> --%>
				</tr>
				</c:forEach>
			</c:otherwise>
			</c:choose>
					
			</tbody>
			
			</table>
			

</div>
<br><br>
		<div class="preRegProceedButton">
		<table>
		<tr>
		<td colspan="5"></td>
									<a class="nextbutton" onclick="proceedPrereg();" href="javascript:void(0)" style = "align:right" >PON to Village Register</a>
		</tr>
		</table>
		</div>
		
<br><br>
<%-- 	</form:form> --%>
<%-- <form:form method="post" action="dsordersendemailhist.html" id="sendForm"> --%>
<%-- <form:form name="imspreregistrationForm" commandName="preRegistrationUI" method="POST"  id = "submitform"> --%>
<div class ="preRegSubmit" style = "display:none">

	<table>

		<tr>
			<td><spring:message code="ims.pcd.prereg.010" /></td>
			
			<td >${preRegistrationUI.instAddr}</td>
			
		</tr>
		

		<tr>
			<td>ID Doc Type:</td>
			<td>
			<form:select  path="onlineSalesReq.doc_type" onchange="ValidateIDNUM()">
				<form:option value="HKID" label="HKID"/>
				<form:option value="PASS" label="PASSPORT"/>
<%-- 				<form:option value="BS" label="HKBR"/> --%>
			</form:select>
			</td>
										

		</tr>		
	<tr>
			<td>ID Doc No.</td>
			<td>
			<form:input  path="onlineSalesReq.doc_num" cssStyle="text-transform:uppercase;" onblur="ValidateIDNUM()"/>
			</td>
			<td id="cust_name_error"><span id="cust_name_error_msg" style="color:#c00000"></span></td>
	</tr>

		<tr>
			<td><spring:message code="ims.pcd.prereg.011" /></td>
			<td>
				<div >
				<form:radiobutton path="onlineSalesReq.title" value="MR" id="mr"  /><label for="mr">
				<spring:message code="ims.pcd.prereg.012" /></label> &nbsp; &nbsp; &nbsp; &nbsp; 
				<form:radiobutton path="onlineSalesReq.title" value="MS" id="ms"  />
				<label for="ms"><spring:message code="ims.pcd.prereg.013" /></label>
			</div>
			</td>
			<td>&nbsp;</td>
		</tr>

		<tr>
			<td>Family Name:</td>
			<td >
				<form:input path="onlineSalesReq.first_name"  />
			</td>
			<c:if  test="${ims_direct_sales eq true}">
			<td>Staff Num:</td>
			<td >
				<form:input path="onlineSalesReq.staff_id"  maxlength="8" />
			</td>
			</c:if>
		</tr>
		
		<tr >
			<td>First Name:</td>
			<td >
				<form:input path="onlineSalesReq.last_name"  />

			</td>
			
		</tr>
		
		<tr>
			<td>Contact Mobile Number</td>
			<td ><form:input path="onlineSalesReq.cont_phone_no" maxlength="8" onblur="checkMobile()"/>
			</td>
			<td id="PhoneNum_error" style="display:none"><span id="PhoneNum_error_msg" style="color:#c00000"></span></td>
		</tr>
<!-- 		<tr> -->
<%-- 			<td><spring:message code="ims.pcd.prereg.016" /></td> --%>
<%-- 			<td colspan="2"><form:input path="onlineSalesReq.email_addr" onblur="checkEmail()" /> --%>
<!-- 			<td id="Email_error" style="display:none"><span id="Email_error_msg" style="color:#c00000"></span></td> -->
<!-- 		</tr> -->

		<tr>
		<td>Remark</td>
			<td colspan="2">
				<form:textarea path="onlineSalesReq.remark" rows="5" cols="75" />
			</td>
		</tr>

<tr>
<td id="common_error" style="display:none"><span id="common_error_msg" style="color:#c00000"></span></td>
<td id="common_warning" style="display:none"><span id="common_warning_msg" style="color:#64a903"></span></td>
</tr>


	</table>

			<div class="preRegSubmitButton">
<!-- 	<input type="hidden" name="action" value="SUBMIT"> -->
			<a class="nextbutton" onclick="preRegSubmit();" href="javascript:void(0)">Submit</a>
		</div>

</div>


	<form:hidden  path = "action"/>
	<form:hidden  path = "searched"/>
	<form:hidden  path = "submitted"/>
	
<!-- 	<input type="hidden" name="action" > -->
</form:form>








