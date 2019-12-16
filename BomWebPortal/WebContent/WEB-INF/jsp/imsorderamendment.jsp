<!-- copy from orderamend.jsp 20131121 -->
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
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page
	import="org.springframework.web.servlet.support.RequestContextUtils,com.bomwebportal.dto.BomSalesUserDTO,java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<!--
<link href="css/ssc.css" rel="stylesheet" type="text/css">
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">-->
<link href="css/ssc2.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.customComboBox.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialogIms.js"></script> 
<!-- copy from orderamend.jsp 20131121 end -->


<%-- <%@ include file="header.jsp"%> --%>
<%@ include file="imsloadingpanel.jsp"%>
<%-- <%@ include file="imsvalidwindow.jsp"%> --%>
<%@ page
	import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"%>
<%
Map<String, List<CodeLkupDTO>> codeLkupList = LookupTableBean.getInstance().getCodeLkupList();
List<CodeLkupDTO> dtos = codeLkupList.get("IPAD_VERSION");
String ipadVersion = "";
if (dtos != null && dtos.size() > 0) {
	ipadVersion = dtos.get(0).getCodeId();
}

//debug ipad version
//ipadVersion = "2.3";

/*steven remark
${amendOrderImsUI.orderImsUI.orderId} 	-- ${} has to be full name in jsp, cannot be  ${orderImsUI.orderId}
document.getElementById(' 				-- can be half name or short name, can it be full name?
$("#									-- can be id of jsp or variable of UI
		
		
		// can only use for jsp id / hidden path
    	//cannot alert(document.getElementById('amendOrderImsUI.orderImsUI.appointment.targetInstallDate').value+"\n");
		//cannot alert(document.getElementById('appointment.targetInstallDate').value+"\n");
		//cannot alert(document.getElementById('targetInstallDate').value);
		can            document.getElementById('orderImsUI.appointment.targetInstallDate').value

yes		alert("${amendOrderImsUI.orderImsUI.appointment.estimatedSrd}");
no		alert("${orderImsUI.appointment.estimatedSrd}");
no		alert("${appointment.estimatedSrd}");
no		alert("${estimatedSrd}");
	*/	
		
String scheme = ConfigProperties.getPropertyByEnvironment("orddoc.ipad.uri.scheme");
%>

<!--
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
-->

<style type="text/css">

.table_title {
color: #FFF;
background-color: #6CA9F5;
padding-top: 5px;
padding-right: 10px;
padding-bottom: 5px;
padding-left: 10px;
width: auto;
font-weight: bold;
font-family: "Helvetica", "Verdana", "Arial", "sans-serif", ·s²Ó©úÅé;
}

.label {
	display: inline-block;
	font-weight: bold;
	width: 120px
}

.row {
	height: 24px
}

.input {
	display: inline-block
}

.input label {
	display: inline-block;
	width: 50px
}

.previewForm {
	background-color: white;
	border: 2px solid #BEBEBE;
	padding: 2px;
	margin-left: 1px
}

.previewForm h3 {
	margin: 0;
	font-size: medium;
	color: white;
	background-color: #ABD078;
	padding: 5px 10px;
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif"
}

.wrapContent {
	display: inline-block;
	width: 100%;
	padding: 10px 0
}

.previewForm .content {
	margin-left: 2em
}

.previewForm .half {
	width: 40%;
	float: left
}

.header tr {
	background-color: #E8F2FE
}

.even {
	background-color: #E8FFE8
}

.success {
	color: green
}

.fail {
	color: red
}

h2 {
	margin: 0
}

.histList {
	width: 100%;
	clear: both
}

.histList td {
	text-align: center
}


.OrderAmendBGgreen2 {
	background-color: #e2f5d3;
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif", ·s²Ó©úÅé;
	vertical-align: middle;
	font-size: 12px;
	color: #000000;
}

.OrderAmendcontenttext {
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif", ·s²Ó©úÅé;
	vertical-align: top;
	font-size: 12px;
}

.OrderAmendtable_title {
	color: #FFF;
	background-color: #abd078;
	padding-top: 5px;
	padding-right: 10px;
	padding-bottom: 5px;
	padding-left: 10px;
	width: auto;
	font-weight: bold;
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif", ·s²Ó©úÅé;
}


</style>



<script language="Javascript">

var isDS = ${ims_direct_sales == true};

function   checkCreditCardHolderName(){   
	var a = document.getElementById("cardholdername").value.toLowerCase();
	var b = $("#registerName").val().toLowerCase();
// 	alert(a+"\n"+b);
		if (!isDS && $("#thirdparty").is(':checked')) {
			document.getElementById('warning_cardholder_msg').style.display = "none";
		}else if(a==b){
// 		  alert ("no error");
			document.getElementById('warning_cardholder_msg').style.display = "none";
	  }	  else{
// 		  alert ("error ");
			document.getElementById('warning_cardholder_msg').style.display = "";
	  }
} 

function   checkStringIsEng(s)   {   
	  var   noCharOver127   =   true;   
	  if (s.length==0){
		return true;  
	  }else{
		  for (var   i=0;   i<s.length;   i++)   {
			  if (s.charCodeAt(i)>127)
				  noCharOver127 = false;
			  <c:forEach items="${asciiReplaceList}" var="aCode">
			  if (s.charCodeAt(i) == parseInt("${aCode}",10)) noCharOver127 = false;
			  </c:forEach>
		  } 
		  if (noCharOver127){
			  return true;
		  }else {
			  alert('Please use English input for remarks.');
			  return false;
		  }
	}
	  
} 
	
	
function checkMobileOkay() {
	var mob = document.getElementById('appNum').value;
	if (mob != null && mob != "") { 
		var hasSmallLetter = /[a-z]/g;
		var hasCapitalLetter = /[A-Z]/g;
		var twoPhoneNum = /\d{8}\/\d{8}/g;
		var onePhoneNum = /\d{8}/g;
		
		if ( hasSmallLetter.test(mob)
				|| hasCapitalLetter.test(mob) 
				|| (mob.length !=8&&mob.length !=17)
				|| (mob.length !=8  && !twoPhoneNum.test(mob))
				|| (mob.length !=16 && !onePhoneNum.test(mob))
				|| (mob.length ==8 && mob.charAt(0) === '1')){
			alert("Please input valid phone number for Applicant Contact No.");
			return false;
		}
		return true;
	}
}

function checkFaxSerialOkay() {
	var fax = document.getElementById('faxSerialNum').value;
	if (fax != "") { 
		var patt3 = /\d{6,}/g;
		if (!patt3.test(fax) || fax.length !=6) {
			alert("Please input 6 dight number for fax serial.");
			return false;
		}
	}
	return true;
}

var BrowserDetect = {
		init: function () {
			this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
			this.version = this.searchVersion(navigator.userAgent)
				|| this.searchVersion(navigator.appVersion)
				|| "an unknown version";
			this.OS = this.searchString(this.dataOS) || "an unknown OS";
		},
		searchString: function (data) {
			for (var i=0;i<data.length;i++)	{
				var dataString = data[i].string;
				var dataProp = data[i].prop;
				this.versionSearchString = data[i].versionSearch || data[i].identity;
				if (dataString) {
					if (dataString.indexOf(data[i].subString) != -1)
						return data[i].identity;
				}
				else if (dataProp)
					return data[i].identity;
			}
		},
		searchVersion: function (dataString) {
			var index = dataString.indexOf(this.versionSearchString);
			if (index == -1) return;
			return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
		},
		dataBrowser: [
			{
				string: navigator.userAgent,
				subString: "Chrome",
				identity: "Chrome"
			},
			{ 	string: navigator.userAgent,
				subString: "OmniWeb",
				versionSearch: "OmniWeb/",
				identity: "OmniWeb"
			},
			{
				string: navigator.vendor,
				subString: "Apple",
				identity: "Safari",
				versionSearch: "Version"
			},
			{
				prop: window.opera,
				identity: "Opera",
				versionSearch: "Version"
			},
			{
				string: navigator.vendor,
				subString: "iCab",
				identity: "iCab"
			},
			{
				string: navigator.vendor,
				subString: "KDE",
				identity: "Konqueror"
			},
			{
				string: navigator.userAgent,
				subString: "Firefox",
				identity: "Firefox"
			},
			{
				string: navigator.vendor,
				subString: "Camino",
				identity: "Camino"
			},
			{		// for newer Netscapes (6+)
				string: navigator.userAgent,
				subString: "Netscape",
				identity: "Netscape"
			},
			{
				string: navigator.userAgent,
				subString: "MSIE",
				identity: "Explorer",
				versionSearch: "MSIE"
			},
			{
				string: navigator.userAgent,
				subString: "Gecko",
				identity: "Mozilla",
				versionSearch: "rv"
			},
			{ 		// for older Netscapes (4-)
				string: navigator.userAgent,
				subString: "Mozilla",
				identity: "Netscape",
				versionSearch: "Mozilla"
			}
		],
		dataOS : [
			{
				string: navigator.platform,
				subString: "Win",
				identity: "Windows"
			},
			{
				string: navigator.platform,
				subString: "Mac",
				identity: "Mac"
			},
			{
				   string: navigator.userAgent,
				   subString: "iPhone",
				   identity: "iPhone/iPod"
		    },
		    {
				   string: navigator.platform,
				   subString: "iPad",
				   identity: "iPad"
		    },
			{
				string: navigator.platform,
				subString: "Linux",
				identity: "Linux"
			}
		]

	};
	
var instdate = "${amendOrderImsUI.orderImsUI.appointment.targetInstallDate}";
var insttime = "${amendOrderImsUI.orderImsUI.appointment.timeSlot}";
var initialSerialNum = "${amendOrderImsUI.orderImsUI.appointment.serialNum}";
var justDone = "${amendOrderImsUI.imsAmendJustDone}";
var docType = "${amendOrderImsUI.orderImsUI.customer.idDocType}";
var docNum = "${amendOrderImsUI.orderImsUI.customer.idDocNum}";
var shownEyeGroupAttach = "N";
var isCC = "${amendOrderImsUI.orderImsUI.isCC}";
var isPT = "${amendOrderImsUI.orderImsUI.isPT}";  
var isDS = "${ims_direct_sales}" == "true"?"Y":"N"; 
var isChannelIdOne = ${isChannelIdOne == true};
var oldInstdate = "${amendOrderImsUI.orderImsUI.srvReqDate}";
var appdate = "${amendOrderImsUI.orderImsUI.appDate}";

var minDateReq = "${minDate}";
var maxMonthReq = "${maxMonth}";

var termDorderSelfReturn = "${amendOrderImsUI.termDorderSelfReturn}";
var isDorder = "${amendOrderImsUI.isDorder}";

	$(document).ready( 
					function() {
						checkReloadForm();//celia
						BrowserDetect.init();
						disModeDefaultValue();//celia add

						$("#termDOrderSelfSRD" ).datepicker({
							changeMonth : true,
							showButtonPanel : true,
							dateFormat : 'yy/mm/dd',
							minDate: 0
						});	
						$("#termDOrderSelfSRD").datepicker().datepicker("setDate", new Date());
						if(justDone=="Y"){
							alert("Amend Success");
							if (isDS == "Y") { 
								window.location.assign("orderamend.html?sbuid=${sessionScope.sbuid}&orderId=${ImsOrder.orderId}&dialogMode=true");
							} else {
								parent.location.reload();
							}
						}
						
						//from imsappointment jsp start		
						
						if("${CanAmendAppointment}" == "Y"){
							$("#instdate" ).datepicker({
								changeMonth : true,
								showButtonPanel : true,
								dateFormat : 'yy/mm/dd', 
								minDate : '${amendOrderImsUI.orderImsUI.appointment.estimatedSrd }'
							});	
							if("${IsPreInstOrder}"=="Y"&&"${IsPreInstThirdOrder}"!="Y"){
								var maxAppDate = new Date(
										appdate.substring(0, 4),
										appdate.substring(5, 7)-1,
										appdate.substring(8, 10));
								maxAppDate.setDate(maxAppDate.getDate()+30);
								$( "#instdate" ).datepicker( "option", "maxDate", maxAppDate);	
							}
						}else{
							$("#instdate" ).datepicker({
								changeMonth : true,
								showButtonPanel : true,
								dateFormat : 'yy/mm/dd', 
								minDate : '0'
							});	
							if("${IsPreInstOrder}"=="Y"&&"${IsPreInstThirdOrder}"!="Y"){
								var maxAppDate = new Date(
										appdate.substring(0, 4),
										appdate.substring(5, 7)-1,
										appdate.substring(8, 10));
								maxAppDate.setDate(maxAppDate.getDate()+30);
								$( "#instdate" ).datepicker( "option", "maxDate", maxAppDate);	
							}
							document.getElementById('earliest_SRD').style.display ="none";
							document.getElementById('existPreBookNum').style.display ="none";
							document.getElementById('bookcols').style.display ="none";
							document.getElementById('timeslotcols').style.display ="none"; 
							document.getElementById('delayReason').style.display ="none";
							document.getElementById('bomRemark').style.display ="none";							
							document.getElementById('fsTimeslotcols').style.display ="none"; 
							document.getElementById('fsDelayReason').style.display ="none";
							document.getElementById('fsBomRemarks').style.display ="none";
							document.getElementById('fsForceerial').style.display ="none";
							//document.getElementById('fsAdditionalRemarks').style.display = "none";
						}
						


						var targetMinDate;
						var targetMaxDate;
						
						if(!$("#instdate").is(":visible")||($("#instdate").is(":visible")&&$("#instdate").val().length <1)){
							targetMinDate = new Date(
									oldInstdate.substring(0, 4),
									oldInstdate.substring(5, 7)-1,
									oldInstdate.substring(8, 10));
						}else{
							targetMinDate = new Date($("#instdate").val());
						}
						
						targetMaxDate = new Date(
								appdate.substring(0, 4),
								appdate.substring(5, 7)-1,
								appdate.substring(8, 10));

						targetMinDate.setDate(targetMinDate.getDate()+parseInt(minDateReq));
						var today = new Date();
						if(targetMinDate<=today){
 							today.setDate(today.getDate()+1);
							targetMinDate = today;
						}
						targetMaxDate.setMonth(targetMaxDate.getMonth()+parseInt(maxMonthReq));
						
						$( "#targetCommDate" ).datepicker({
							changeMonth : true,
							showButtonPanel : true,
							dateFormat : 'yy/mm/dd'
						});

						$( "#targetCommDate" ).datepicker( "option", "minDate", targetMinDate);	
			 			$( "#targetCommDate" ).datepicker( "option", "maxDate", targetMaxDate);
						
						
						
						$("#fsInstdate" ).datepicker({
							changeMonth : true,
							showButtonPanel : true,
							dateFormat : 'yy/mm/dd', 
							minDate : '0',
							onSelect: function(){
								getFSTimeSlot(); 
							}
						});		
						
						if("${IsPreInstOrder}"=="Y"&&"${IsPreInstThirdOrder}"!="Y"){
							var maxAppDate = new Date(
									appdate.substring(0, 4),
									appdate.substring(5, 7)-1,
									appdate.substring(8, 10));
							maxAppDate.setDate(maxAppDate.getDate()+30);
							$( "#fsInstdate" ).datepicker( "option", "maxDate", maxAppDate);	
						}
						
						var fsTargetMinDate;
						var fsTargetMaxDate;
						
						if(document.getElementById("fsInstdate").value.length <1){
							fsTargetMinDate = new Date(oldInstdate);
							fsTargetMaxDate = new Date(oldInstdate);
						}else{
							fsTargetMinDate = new Date($("#fsInstdate").val());
							fsTargetMaxDate = new Date($("#fsInstdate").val());
						}

						fsTargetMinDate.setDate(fsTargetMinDate.getDate()+parseInt(minDateReq));
						fsTargetMaxDate.setMonth(fsTargetMaxDate.getMonth()+parseInt(maxMonthReq));
						
						$( "#fsCommDate" ).datepicker({
							changeMonth : true,
							showButtonPanel : true,
							dateFormat : 'yy/mm/dd'
						});

						$( "#fsCommDate" ).datepicker( "option", "minDate", fsTargetMinDate);	
			 			$( "#fsCommDate" ).datepicker( "option", "maxDate", fsTargetMaxDate);
						
						
						$("#instdate").change(
							function(){
								if("${CanAmendAppointment}" == "Y"){
									getTimeSlot();
									$("#timeSlot").attr("disabled", false);
									document.getElementById('preBkSerialNum').value ="";
								}
							}							
						);	

						if(checkSrdOver() || checkSrdUnder()){
							$("#timeSlot").attr("disabled", true);	
						}
						
						$("#delayReasonCode").change( 
								function(){
									$("#AutoDelayReason").get(0).selectedIndex = this.selectedIndex;
								}
							);
						if($("#delayReasonCode").is(":visible")){
							$("#AutoDelayReason").get(0).selectedIndex = $("#delayReasonCode").get(0).selectedIndex;
						}
						
						$("#cancelReason").change(
								function(){
									$("#cancelReasonCode").get(0).selectedIndex = this.selectedIndex;
								}
							);
						$("#cancelReasonCode").get(0).selectedIndex = $("#cancelReason").get(0).selectedIndex;
						
						$("#fsCancelReason").change(
								function(){
									$("#fsCancelReasonCode").get(0).selectedIndex = this.selectedIndex;
								}
							);
						$("#fsCancelReasonCode").get(0).selectedIndex = $("#fsCancelReason").get(0).selectedIndex;
						
						
						$("#timeSlot").change(
								function(){
									$("#slotType").get(0).selectedIndex = this.selectedIndex;
									document.getElementById('preBkSerialNum').value ="";
								}
							);
						if($("#timeSlot").is(":visible")){
							$("#slotType").get(0).selectedIndex = $("#timeSlot").get(0).selectedIndex;
						}

						//from imsappointment jsp end
						checkOrderCancel();
						
						checkReloadForm();

						$($("#dOrderSelfPickInd")).change(
								function(event) {
									checkDOrderSelfPick();
								});
						$($("input[type=radio][name=CollectMtd]")).change(
								function(event) {
									dOrderCollectMthChange();
								});

						$($("#cancelOrderInd")).change(
								function(event) {
									checkOrderCancel();
								});

						$("#appointmentInd").change(
								function(event) {
									if ($(this).is(':checked')) {
										document.getElementById('appointmentDiv').style.display ="block";
									} else {
										document.getElementById('appointmentDiv').style.display ="none";
									}
									showSubmitButton();
								});

						$("#updateCreditInd").change(
								function(event) {
									if ($(this).is(':checked')) {
										document.getElementById('updateCreditDiv').style.display ="block";
										expiryDateCtrl();
									} else {
										document.getElementById('updateCreditDiv').style.display ="none";
									}
									showSubmitButton();
								});

						$("#changeLoginId").change(
								function(event) {
									if ($(this).is(':checked')) {
										document.getElementById('changeLoginIdDiv').style.display ="block";
									} else {
										document.getElementById('changeLoginIdDiv').style.display ="none";
									}
									showSubmitButton();
								});

						$("#contactEmailInd").change(
								function(event) {
									if ($(this).is(':checked')) {
										document.getElementById('contactEmailDiv').style.display ="block";
									} else {
										document.getElementById('contactEmailDiv').style.display ="none";
									}
									showSubmitButton();
								});
						


						$("#thirdparty").change(
								function(event) {
									faxSerialShowOrNot();
								});

						$("#self").change(
								function(event) {
									faxSerialShowOrNot();
								});
						
						if(!isDS)faxSerialShowOrNot(); 
						
						if("${amendOrderImsUI.allowProgOfferChange}"=="N" && "${amendOrderImsUI.allowTvContentChange}"=="N") {
							$("#programOfferChangeRow").hide(); 
						}
						
						$("#fsAmendInd").change(
								function(event) {
									if ($(this).is(':checked')) {
										document.getElementById('fsAmendDiv').style.display ="block";
										appointmentDisable();
										updateLoinIdDisable();
										updateContactEmailDisable();
										//fsAmendment1Enable();
										//$("#fs2").attr('disabled', false);  
									} else {
										document.getElementById('fsAmendDiv').style.display ="none";
										appointmentEnable();
										updateLoinIdEnable();
										updateContactEmailEnable();
										fsAppDisable(); 
										$("#fsAmendNature1").get(0).selectedIndex=0;
										fsAmendment1Disable(); 
										fsAmendment2Disable();
										fsAmendment3Disable();
										$("#programOfferChange").attr("checked", false);
										$("#progOfferChangeDiv").hide(); 
										$("#progOfferChangeRemark").val(""); 
									}
									showSubmitButton();
								});

						$("#fs1").change(
								function(event) {
									if ($(this).is(':checked')) {
										fsAmendment1Enable();
										$("#fs2").attr('disabled', false);
									} else {
										$("#fsAmendNature1").get(0).selectedIndex=0;
										$("#fsAmendRemark1").val("");
										fsAmendment1Disable();  
										fsAmendment2Disable();
										fsAmendment3Disable();
										hideFsRemark1(); 
									}
								});
						
						$("#fs2").change(
								function(event) {
									if ($(this).is(':checked')) {
										fsAmendment2Enable();
										$("#fs3").attr('disabled', false);
									} else {
										$("#fsAmendNature2").get(0).selectedIndex=0;
										$("#fsAmendNature2").attr('disabled', true);
										$("#fsAmendRemark2").val("");
										fsAmendment3Disable();
										hideFsRemark2();
									}
								});

						$("#fs3").change(
								function(event) {
									if ($(this).is(':checked')) {
										fsAmendment3Enable();
									}else{
										$("#fsAmendNature3").get(0).selectedIndex=0;
										$("#fsAmendNature3").attr('disabled', true); 
										$("#fsAmendRemark3").val(""); 
										fsAmendment3Disable(); 
										hideFsRemark3();
										$("#fs3").attr('disabled', false);
									}
								});
						

						$("#fsAmendNature1").change(
								function(event) {
									checkFSAppOrCancel();
								});
						$("#fsAmendNature2").change(
								function(event) {
									checkFSAppOrCancel();
								});
						$("#fsAmendNature3").change(
								function(event) {
									checkFSAppOrCancel();
								});
						

						$("#fsInstdate").change(
								function(event) {
									getFSTimeSlot();
									document.getElementById('fsTargetInstallDate').value = document.getElementById('fsInstdate').value;
								});
						
						if($("#timeSlot").is(":visible")){
							colorTimeSlot();
						}
							
							
						$("#applicantRelationshipSelect").customComboBox({
							tipText:"Others",
							allowed:/[\u0000-\u00ff]/,
							notallowed : /[^\u0000-\u00ff]/,
							index : 'last',
							prefix:""
						});
						checkFieldVisitAndEyeGroupAttach();

						$("[name='progOfferChangeRemark']").attr("readonly", true);
						$("#programOfferChange").change(function(e){
								if($(this).attr("checked")== true) {
									$("#progOfferChangeDiv").show();
								} else {
									if (confirm("Are you sure to deselect? Remarks will be cleared. ")) {
										$("#progOfferChangeDiv").hide();
										$("#progOfferChangeRemark").val(""); 
									} else {
										$(this).attr("checked", true); 
									}
								}
						}); 

						if(isChannelIdOne){
							fsAmendmentDisable();
							if(document.getElementById('fsAmendTickBox')!=null){
								document.getElementById('fsAmendTickBox').style.display ="none";
							}
						}

						

					
					});

	function checkFieldVisitAndEyeGroupAttach() {
		if("${CanAmendAppointment}" == "N"){
// 			document.getElementById('autoFlowThroughAppointment').style.display ="none";
			orderCancelDisable();
// 			appointmentDisable();
		}
		if("${eyeGroupAttach}" == "Y"){
			document.getElementById('autoFlowThroughCancel').style.display ="none";
			document.getElementById('autoFlowThroughAppointment').style.display ="none";
			orderCancelDisable();
			appointmentDisable();
		}
	}


	function faxSerialShowOrNot() {
		checkCreditCardHolderName();
		if ($("#thirdparty").is(':checked')) {
			document.getElementById('faxSerial').style.visibility = "visible";
			$("#faxSerialNum").attr('disabled', false);
		} else {
			document.getElementById('faxSerial').style.visibility = "hidden";
			$("#faxSerialNum").attr('disabled', true);
		}
	}
	
	function checkFSAppOrCancel() {
		if ($("#fsAmendNature1").val()=="346" ||$("#fsAmendNature2").val()=="346"||$("#fsAmendNature3").val()=="346") {
			fsAppEnable();
			if("${eyeGroupAttach}" == "Y" && shownEyeGroupAttach == "N"){
				alert('EYE group in BOM order found, SRD for order details under same EYE group will be aligned. Please confirm.');
				shownEyeGroupAttach = "Y";
			}
		}else{
			fsAppDisable();
		}
		if ($("#fsAmendNature1").val()=="345" ||$("#fsAmendNature2").val()=="345"||$("#fsAmendNature3").val()=="345") {
			fsOrderCancelEnable();
		}else{
			fsOrderCancelDisable();
		}

		if ($("#fsAmendNature1").val()=="346"||$("#fsAmendNature1").val()=="345") {
			hideFsRemark1();
		}else if ($("#fsAmendInd").is(':checked')) {
			showFsRemark1();
		}
		if ($("#fsAmendNature2").val()=="346"||$("#fsAmendNature2").val()=="345") {
			hideFsRemark2();
		}else if ($("#fs2").is(':checked')) {
			showFsRemark2();
		}
		if ($("#fsAmendNature3").val()=="346"||$("#fsAmendNature3").val()=="345") {
			hideFsRemark3();
		}else if ($("#fs3").is(':checked')) {
			showFsRemark3();
		}
	}

	function keyUpOnCCName() {
		 	document.getElementById('cardholdername').value = document.getElementById('cardholdername').value.toUpperCase();
		 	checkCreditCardHolderName();
	}
	
	function showSubmitButton(){
		if ($("#fsAmendInd").is(':checked')       	||  
			$("#appointmentInd").is(':checked')   	||   
			$("#updateCreditInd").is(':checked')  	||   
			$("#cancelOrderInd").is(':checked')		|| 
			$("#changeLoginId").is(':checked')		|| 
			$("#contactEmailInd").is(':checked')		||
			$("#dOrderSelfPickInd").is(':checked') ||
			$("#programOfferChange").is(':checked'))	
		{ 
			document.getElementById('submitDiv').style.display ="block";
		}else{
			document.getElementById('submitDiv').style.display ="none";
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
			if(iYear==parseInt(document.getElementById("ccExpiryYear").value) && i<=iMonth){
				document.getElementById("epy"+i).disabled = true;
				if(parseInt(document.getElementById("ccExpiryMonth").value)==i)
					document.getElementById("ccExpiryMonth").value="";
			}else{
				document.getElementById("epy"+i).disabled = false;
			}
		}
			 return iYear;
	}
	
	function isInputOkayBeforeSubmit(){
		
		checkFieldVisitAndEyeGroupAttach();
		
		/// BOM2019022, nowtv isIncludeGoodsDelivery=Y is not allowed to proceed
		if ($("#nowtv_isIncludeGoodsDelivery").val()=="Y") {
			alert("Amendment is not allowed as NowTV pack is not allowed for Add/Remove.");
			$("#nowtv_isIncludeGoodsDelivery_msg").show();
			return false;
		}
		
		/// cancel order
		if ($("#cancelOrderInd").is(':checked')) {
			if(document.getElementById("cancelReason").selectedIndex==0){
				alert("Please select Cancel Reason");
				return false;
			}
			if(document.getElementById("cancelRemark").value.length>250){	
				alert("Cancel Remark cannot be longer than 250.");
				return false;
			}
			if(!checkStringIsEng(document.getElementById("cancelRemark").value)){
				return false;
			};
			if(document.getElementById("cancelRemark").value.length==0){	
				alert("Please input Cancel Remark.");
				return false;
			}
		}
		/// change srd
		if ($("#appointmentInd").is(':checked')) {

			if(!($("#preInstApptInd").is(':checked') || $("#preInstCommDateInd").is(':checked')) && "${IsPreInstOrder}" == "Y"
				&& "${amendOrderImsUI.amendModeDto.changeSRD}"=="Y" && "${amendOrderImsUI.amendModeDto.changeCommDate}"=="Y"){
				alert("please select Change Appointment Date / Change Target Commencement Date.");
				return false;
			}
			
			if("${IsPreInstOrder}" == "N" || ("${IsPreInstOrder}" == "Y" && 
					($("#preInstApptInd").is(':checked')||("${amendOrderImsUI.amendModeDto.changeSRD}"=="Y" && "${amendOrderImsUI.amendModeDto.changeCommDate}"!="Y")))){
			
			if(document.getElementById("instdate").value.length <1){
				alert("please select Target Installation Date");
				return false;
			}
			if("${CanAmendAppointment}" == "Y"){
				if(document.getElementById("timeSlot").selectedIndex==0){
					alert("please select timeslot");
					return false;
				}
				if(document.getElementById('preBkSerialNum')==null || 
						document.getElementById('preBkSerialNum').value=='' ||
							document.getElementById('preBkSerialNum').value==initialSerialNum){
					alert("Missing pre-book serial, please click confirm button to get pre-book serial no. then continue the order processing.");
					return false;
				}
				
				if(document.getElementById("delayReasonCode").selectedIndex==0){
					alert("Please select Delay Reason");
					return false;
				}
			}	
					
			var targetMinDateCheck;
			
			targetMinDateCheck = new Date($("#instdate").val());
			targetMinDateCheck.setDate(targetMinDateCheck.getDate()+parseInt(minDateReq));
					
			var oldCommDate = new Date(
						"${amendOrderImsUI.orderImsUI.targetCommDate}".substring(0, 4),
						"${amendOrderImsUI.orderImsUI.targetCommDate}".substring(5, 7)-1,
						"${amendOrderImsUI.orderImsUI.targetCommDate}".substring(8, 10));
			
			if(targetMinDateCheck>oldCommDate && !$("#preInstCommDateInd").is(':checked') && "${IsPreInstThirdOrder}"!="Y"){
				alert("commencement date must not be earlier than T+" + minDateReq + ", please select a new commencement date");
				return false;
			}
			
			document.getElementById('orderImsUI.appointment.targetInstallDate').value =	document.getElementById('instdate').value;
			
			}
			
			
			if("${IsPreInstOrder}" == "Y" && 
					($("#preInstCommDateInd").is(':checked')||("${amendOrderImsUI.amendModeDto.changeSRD}"!="Y" && "${amendOrderImsUI.amendModeDto.changeCommDate}"=="Y"))){
// 				alert('aa');
				if(document.getElementById("targetCommDate").value.length <1){
					alert("please select Target Commencement Date");
					return false;
				}
 				document.getElementById('orderImsUI.appointment.targetCommDate').value = document.getElementById('targetCommDate').value;
			}
			
		}
		// fs
		if ($("#fsAmendInd").is(':checked')) {
			//check remark eng okay
			if(!checkStringIsEng(document.getElementById("fsAmendRemark1").value)){
				return false;
			};
			if(!checkStringIsEng(document.getElementById("fsAmendRemark2").value)){
				return false;
			};
			if(!checkStringIsEng(document.getElementById("fsAmendRemark3").value)){
				return false;
			};

			if ($("#fsAmendInd").is(':checked') && !$("#programOfferChange").is(':checked') && !$("#fs1").is(':checked')) { 
				alert("Please select at least 1 F&S Amendment");
				return false;
			}
			
			if ($("#fs1").is(':checked') && document.getElementById("fsAmendNature1").selectedIndex==0) { 
				alert("Please select 1st F&S Amend Nature");
				return false;
			}
			if ($("#fs2").is(':checked') && document.getElementById("fsAmendNature2").selectedIndex==0) {
				alert("Please select 2nd F&S Amend Nature");
				return false;
			}
			if ($("#fs3").is(':checked') && document.getElementById("fsAmendNature3").selectedIndex==0) {
				alert("Please select 3rd F&S Amend Nature");
				return false;
			}
			
			
			if($("#fsAmendInd").is(':checked')&&$("#fs2").is(':checked')
					&&document.getElementById("fsAmendNature1").selectedIndex==document.getElementById("fsAmendNature2").selectedIndex){
				alert("Please select different F&S Amend Nature for 1 and 2 ");
				return false;
			}
			
			if($("#fs2").is(':checked')&&$("#fs3").is(':checked')
					&&document.getElementById("fsAmendNature2").selectedIndex==document.getElementById("fsAmendNature3").selectedIndex){
				alert("Please select different F&S Amend Nature for 2 and 3 ");
				return false;
			}
			
			if($("#fsAmendInd").is(':checked')&&$("#fs3").is(':checked')
					&&document.getElementById("fsAmendNature1").selectedIndex==document.getElementById("fsAmendNature3").selectedIndex){
				alert("Please select different F&S Amend Nature for 1 and 3 ");
				return false;
			}
			
			if(document.getElementById("fsAmendNature1").selectedIndex!=0){
				if(document.getElementById("fsAmendRemark1").value.length>250){	
					alert("Amend Remark cannot be longer than 250.");
					return false;
				}
				if(document.getElementById("fsAmendRemark1").value.length==0 && (!($("#fsAmendNature1").val()=="346")&&!($("#fsAmendNature1").val()=="345"))){	
					alert("Please input 1st Amend Remark.");
					return false;
				}
			}
			if(document.getElementById("fsAmendNature2").selectedIndex!=0){
				if(document.getElementById("fsAmendRemark2").value.length>250){	
					alert("Amend Remark cannot be longer than 250.");
					return false;
				}
				if(document.getElementById("fsAmendRemark2").value.length==0&& (!($("#fsAmendNature2").val()=="346")&&!($("#fsAmendNature2").val()=="345"))){	
					alert("Please input 2nd Amend Remark.");
					return false;
				}
			}
			if(document.getElementById("fsAmendNature3").selectedIndex!=0){
				if(document.getElementById("fsAmendRemark3").value.length>250){	
					alert("Amend Remark cannot be longer than 250.");
					return false;
				}
				if(document.getElementById("fsAmendRemark3").value.length==0&& (!($("#fsAmendNature3").val()=="346")&&!($("#fsAmendNature3").val()=="345"))){	
					alert("Please input 3rd Amend Remark.");
					return false;
				}
			}
			
			//Fs SRD
			if ($("#fsAmendNature1").val()=="346" && $("#fsAmendInd").is(':checked')){
				if(!checkFsSRDcanSubmit()){
					return false;
				}
			}
			if ($("#fsAmendNature2").val()=="346" && $("#fs2").is(':checked')){
				if(!checkFsSRDcanSubmit()){
					return false;
				}
			}
			if ($("#fsAmendNature3").val()=="346" && $("#fs3").is(':checked')){
				if(!checkFsSRDcanSubmit()){
					return false;
				}
			}
			

			//Fs cancel order
			if ($("#fsAmendNature1").val()=="345" && $("#fsAmendInd").is(':checked')){
				if(!checkFsCancelOrdercanSubmit()){
					return false;
				}
			}
			if ($("#fsAmendNature2").val()=="345" && $("#fs2").is(':checked')){
				if(!checkFsCancelOrdercanSubmit()){
					return false;
				}
			}
			if ($("#fsAmendNature3").val()=="345" && $("#fs3").is(':checked')){
				if(!checkFsCancelOrdercanSubmit()){
					return false;
				}
			}
					
		}
		
		// credit card
		if ($("#updateCreditInd").is(':checked')) {
			if (!$("#thirdparty").is(':checked') && !$("#self").is(':checked') && !isDS) {
				alert("Please select if is 3rd Party Card");
				return false;
			}
			
			if(!checkFaxSerialOkay()){
				return false;
			}
			if(document.getElementById("cardholdername").value.length<1){		
				alert("Please input card holder name");
				return false;
			}
			if(document.getElementById("cardnum").value.length<1){
				alert("Please input credit card number");
				return false;
			}
			if(	document.getElementById("cardtype").selectedIndex==0){
					alert("Please select credit card type");
					return false;
				}
			if(	document.getElementById("ccExpiryMonth").selectedIndex==0){
				alert("Please select credit card expiry month");
				return false;
			}
			var today = new Date();
			var threeMonthsLater = new Date(new Date(today).setMonth(today.getMonth()+3));

			var ccExpiry=new Date();
			ccExpiry.setFullYear($("#ccExpiryYear").val(),$("#ccExpiryMonth").val()-1,28);
			
			if (ccExpiry<threeMonthsLater)
			  {
// 			alert("threeMonthsLater:"+threeMonthsLater+"    ccExpiry:"+ccExpiry);
			  alert("Credit card expiry date have to be 3 months later then today.");
				return false;
			  }
		}
		
		// login id
		
		
		if ($("#changeLoginId").is(':checked')) {

			if(document.getElementById("loginName").value.length<1){		
				alert("Please input Login ID");
				return false;
			}

			if(document.getElementById("reservedId").value!=document.getElementById("loginName").value){
				alert("Please reserve Login ID");		
				return false;
			}
			
		}
		
		if ($("#contactEmailInd").is(':checked')) {

			if(document.getElementById("contactEmailName").value.length<1){		
				alert("Please input Contact Email Address");
				return false;
			}
			
		}
		
		
		// termination d order 
		if ($("#dOrderSelfPickInd").is(':checked')) {
			if(termDorderSelfReturn == "Y" && !checkFsSRDcanSubmit()){
				return false;
			}
			document.getElementById('fsTargetInstallDate').value = document.getElementById('fsInstdate').value;
			if ($('input[name=CollectMtd]:checked').val()== null) {			
				document.getElementById('collectMethodVoid').style.display='';	
				return false;
			}else {
				document.getElementById('collectMethodVoid').style.display='none';
				document.getElementById('notQualified').style.display='none';
				var selectedMtd = document.querySelector('input[name="CollectMtd"]:checked').value;
				if("${amendOrderImsUI.isQualifiedCategory}" != "Y" && selectedMtd=="FSWOC"){
					document.getElementById('notQualified').style.display='';		
					return false;		
				}
				$('#isCollectRequestClicked').val("N");
				$('#isVisitWithChargeClicked').val("N");
				$('#isVisitWithoutChargeClicked').val("N");				
				if(selectedMtd=="SR"){
					$('#isCollectRequestClicked').val("Y");			
					document.getElementById('orderImsUI.appointment.targetInstallDate').value =	document.getElementById('termDOrderSelfSRD').value;			
				}else if(selectedMtd=="FSWC"){
					$('#isVisitWithChargeClicked').val("Y");						
				}else if(selectedMtd=="FSWOC"){
					$('#isVisitWithoutChargeClicked').val("Y");					
				} 
			}
		}else{
			$('#isCollectRequestClicked').val("N");
			$('#isVisitWithChargeClicked').val("N");
			$('#isVisitWithoutChargeClicked').val("N");			
		}
		
		
		// app
		if(document.getElementById('appName')==null || 
				document.getElementById('appName').value=='') {
			alert("Please input Applicant Name.");
			return false;
		}

		if(document.getElementById('appNum')==null || 
				document.getElementById('appNum').value=='') {
			alert("Please input Applicant Contact No..");
			return false;
		}else{
			if(!checkMobileOkay()){
				return false;
			}
		}
		
		if(document.getElementById("applicantRelationshipSelect").selectedIndex==0){
			alert("Please select Applicant Relationship.");
			return false;
			
		}

		if ($("#programOfferChange").is(':checked') && $("[name='progOfferChangeRemark']").val().length ==0){
			alert("No changes of offers are detected.");
			return false;	
		}

		
		/*if($("#programOfferChange").is(':checked') && !($("#fsAmendNature1").val()=="346") && !($("#fsAmendNature2").val()=="346") && !($("#fsAmendNature3").val()=="346")) { 
			alert("Please re-select the srd in F&S Amendment."); 
			return false;
		}*/ 
		
//		Celia - Amendment Notification Distribution Mode
		<c:choose>
			<c:when test="${ims_direct_sales eq true}">
				<c:choose>
					<c:when test="${ImsOrder.orderType eq 'NTV-A' || ImsOrder.orderType eq 'NTVAO' || ImsOrder.orderType eq 'NTVRE' || ImsOrder.orderType eq 'NTVUS'}">
						if ($(".error:visible").length > 0){
							return false;
						}
						if($("input[name=emailAddr]").val().length != 0)
							if (!confirm("Email will be sent to " + $("input[name=emailAddr]").val() + ". Confirm to continue?")) {
								return false;
							}
						if ($("input[name=SMSno]").val().length != 0) 
							if (!confirm("SMS will be sent to " + $("input[name=SMSno]").val() + ". Confirm to continue?")) {
								return false;
							}
					</c:when>
					<c:otherwise>
						if ($(".error:visible").length > 0){
							return false;
						}
						if ($("input[name=emailAddr]").val().length == 0) 
		 				{
		 					alert("Missing Email address for sending notification.");	
		 					return false;
		 				} 
		 				if ($("input[name=SMSno]").val().length == 0) 
			 			{
			 				alert("Missing mobile no. for sending notification.");	
			 				return false;
			 			} 
						if (!confirm("Email will be sent to " + $("input[name=emailAddr]").val() + ". Confirm to continue?")) {
							return false;
						}
						if (!confirm("SMS will be sent to " + $("input[name=SMSno]").val() + ". Confirm to continue?")) {
							return false;
						}
					</c:otherwise>
				</c:choose>
			</c:when> 			
			<c:when test='${amendOrderImsUI.orderImsUI.isCC=="Y" and amendOrderImsUI.orderImsUI.isPT=="Y"}'>
				/*if($("input[name=SMSno]").val().length==0 && $("input[name=emailAddr]").val().length==0){
					if (!confirm("No Notification will be sent. Confirm to continue?")) 
 						return false;
				}else if ($('input[name=disMode]:checked').val()== null) {
					alert("Require to choose either distribution method.");
						return false;
				}else if ($("input[name=SMSno]").val().length == 0 && $('input[name=disMode]:checked').val()== 'S' ){
					alert("Missing mobile no. for sending notification. ");
						return false;
				}else if ($('input[name=disMode]:checked').val()== 'E' && $("input[name=emailAddr]").val().length == 0 ){
					alert("Missing Email address for sending notification. ");
						return false;
				}else if ($("input[name=emailAddr]").val().length != 0) {
					if (!confirm("Email will be sent to " + $("input[name=emailAddr]").val() + ". Confirm to continue?")) 
					    return false;
				}else if ($("input[name=SMSno]").val().length != 0) {
					if (!confirm("SMS will be sent to " + $("input[name=SMSno]").val() + ". Confirm to continue?")) 
						return false;
				}else {
						return false;
				}*/
			</c:when>
			<c:otherwise>
				if ($(".error:visible").length > 0){
					return false;
				}
				<c:if test='${amendOrderImsUI.orderImsUI.isCC ne "Y" and amendOrderImsUI.orderImsUI.isPT ne "Y"}'>
				if ($("input[name=emailAddr]").val().length != 0){
	 				if (!confirm("Email will be sent to " + $("input[name=emailAddr]").val() + ". Confirm to continue?")) 
						return false;	 				
				}else{			
					if (!confirm("No Notification will be sent. Confirm to continue?")) 
	 					return false;
				}
				</c:if>
			</c:otherwise>
		</c:choose> 
		
		return true;
	}
	
	function checkFsSRDcanSubmit(){
		if(document.getElementById("fsInstdate").value.length <1){
			alert("please select F&S Target Installation Date");
			return false;
		}
		if("${IsPreInstOrder}" == "Y" ){
			if(document.getElementById("fsCommDate").value.length <1){
				alert("please select F&S Target Commencement Date");
				return false;
			}
		}
		if("${CanAmendAppointment}" == "Y"){
			if(document.getElementById("fsTimeSlot").selectedIndex==0){
				alert("please select F&S timeslot");
				return false;
			}
			if(document.getElementById("fsDelayReasonCode").selectedIndex==0){
				alert("Please select F&S Delay Reason");
				return false;
			}
		}
		document.getElementById('fsTargetInstallDate').value = document.getElementById('fsInstdate').value;
		if("${IsPreInstOrder}" == "Y" && document.getElementById('fsCommDate')!=null){
			document.getElementById('fsTargetCommDate').value = document.getElementById('fsCommDate').value;
		}
		return true;
	}
	
	

	function checkFsCancelOrdercanSubmit(){
		if(document.getElementById("fsCancelType").selectedIndex==0){
			alert("please select F&S Cancel Type");
			return false;
		}
		if(document.getElementById("fsCancelReason").selectedIndex==0){
			alert("Please select F&S Cancel Reason");
			return false;
		}
		if(document.getElementById("fsCancelRemark").value.length>250){	
			alert("F&S Cancel Remark cannot be longer than 250.");
			return false;
		}
		if(document.getElementById("fsCancelRemark").value.length==0){	
			alert("Please input F&S Cancel Remark.");
			return false;
		}
		if(!checkStringIsEng(document.getElementById("fsCancelRemark").value)){
			return false;
		}
		return true;
	}
	
	function checkReloadForm() {
		if ($("#isCancelOrderEnabled").val()=="Y") {
			$('input[name=cancelOrderInd]').attr('checked', true);
			checkOrderCancel();
		}
		fsAmendmentEnable();
		if ($("#isFsAmendEnabled").val()=="Y") {
			$('input[name=fsAmendInd]').attr('checked', true);
			document.getElementById('fsAmendDiv').style.display ="block";
			appointmentDisable();
			fsAmendment1Enable();
			$("#fs2").attr('disabled', false);
		}
		ccUpdateEnable();
		if ($("#isUpdateCreditEnabled").val()=="Y") {
			document.getElementById('updateCreditDiv').style.display ="block";
			$('input[name=updateCreditInd]').attr('checked', true);
		}
		updateLoinIdEnable();
		if ($("#isLoginIdEnabled").val()=="Y") {
			document.getElementById('changeLoginIdDiv').style.display ="block";
			$('input[name=changeLoginId]').attr('checked', true);
		}
		updateContactEmailEnable();
		if ($("#isContactEmailEnabled").val()=="Y") {
			document.getElementById('contactEmailDiv').style.display ="block";
			$('input[name=contactEmailInd]').attr('checked', true);
		}
		appointmentEnable();
		if ($("#isAppointmentEnabled").val()=="Y") {
			document.getElementById('appointmentDiv').style.display ="block";
			$('input[name=appointmentInd]').attr('checked', true);
		}
		if($("#isChangeProgOfferEnabled").val()=="Y") {
			$('#programOfferChange').attr('checked', true);
			$("#progOfferChangeDiv").show(); 
		}
		
		checkFSAppOrCancel();
		showSubmitButton();
		checkOrderCancel();
		
		if(isChannelIdOne){
			fsAmendmentDisable();
			if(document.getElementById('fsAmendTickBox')!=null){
				document.getElementById('fsAmendTickBox').style.display ="none";
			}
		}
	}
	function preSubmit(){
		$("#errorDiv").hide(); 
		if(!isInputOkayBeforeSubmit()){
			return;
		}else if ($(".error:visible").length > 0){
			return;
		}else{
			var isCC= "${amendOrderImsUI.orderImsUI.isCC == 'Y' or amendOrderImsUI.orderImsUI.isPT =='Y'}";  
			if (isCC == "Y" ){
				NotificationSendAlertDialog();
			}
			else 		
				submitForm();				
		}		
	}//celia add 
	function submitForm() {		
			if ($("#dOrderSelfPickInd").is(':checked')) {
				$("#isDOrderSelfPickEnabled").val("Y");
			}else{
				$("#isDOrderSelfPickEnabled").val("N");
			}
			
			if ($("#cancelOrderInd").is(':checked')) {
				$("#isCancelOrderEnabled").val("Y");
				orderCancelEnable();
			}else{
				$("#isCancelOrderEnabled").val("N");
				orderCancelDisable();
			}
			
			if ($("#fsAmendInd").is(':checked')) {
				$("#isFsAmendEnabled").val("Y");
			}else{
				$("#isFsAmendEnabled").val("N");
				fsAmendmentDisable();
			}
			
			if ($("#updateCreditInd").is(':checked')) {
				$("#isUpdateCreditEnabled").val("Y");
				$("#ccExpDate").val($("#ccExpiryMonth").val()+"/"+$("#ccExpiryYear").val());
			}else{
				$("#isUpdateCreditEnabled").val("N");
				ccUpdateDisable();
			}
			
			if ($("#changeLoginId").is(':checked')) {
				$("#isLoginIdEnabled").val("Y");
			}else{
				$("#isLoginIdEnabled").val("N");
				updateLoinIdDisable();
			}
			
			if ($("#contactEmailInd").is(':checked')) {
				$("#isContactEmailEnabled").val("Y");
			}else{
				$("#isContactEmailEnabled").val("N");
				updateContactEmailDisable();
			}
			
			
			if ($("#appointmentInd").is(':checked')) {
				$("#isAppointmentEnabled").val("Y");
				if("${IsPreInstOrder}" == "Y"){
					if($("#preInstApptInd").is(':checked')){
						$("#isPreInstApptEnabled").val("Y");
					}else{
						if("${amendOrderImsUI.amendModeDto.changeSRD}"=="Y"&&"${amendOrderImsUI.amendModeDto.changeCommDate}"!="Y"){
							$("#isPreInstApptEnabled").val("Y");
						}else{
							$("#isPreInstApptEnabled").val("N");
						}
					}
					if($("#preInstCommDateInd").is(':checked')){
						$("#isPreInstCommDateEnabled").val("Y");
					}else{
						if("${amendOrderImsUI.amendModeDto.changeSRD}"!="Y"&&"${amendOrderImsUI.amendModeDto.changeCommDate}"=="Y"){
							$("#isPreInstCommDateEnabled").val("Y");
						}else{
							$("#isPreInstCommDateEnabled").val("N");
						}
					}
				}
				
			}else{
				$("#isAppointmentEnabled").val("N");
				appointmentDisable();
			}

			if($("#programOfferChange").is(':checked'))  
				$("#isChangeProgOfferEnabled").val("Y");
			else $("#isChangeProgOfferEnabled").val("N");
			submitShowLoading(); 
			document.imsOrderAmendForm.submit();
	}


	function checkDOrderSelfPick() {
		if ($("#dOrderSelfPickInd").is(':checked')) {
			dOrderSelfPickEnable();
			dOrderCollectMthChange();
		} else {
			dOrderSelfPickDisable();
			fsAppDisable();
		}
		showSubmitButton();
	}
	
	function dOrderCollectMthChange(){
		fsAppDisable();
		dOrderSelfSrdDisable();
		var selectedMtd = "";
		if(document.querySelector('input[name="CollectMtd"]:checked')!=null){
			selectedMtd = document.querySelector('input[name="CollectMtd"]:checked').value;
		}
		if(selectedMtd=="FSWC"){
			fsAppEnable();		
		}else if(selectedMtd=="FSWOC"){
			fsAppEnable();		
		}else if(selectedMtd=="SR"){
			dOrderSelfSrdEnable();		
		};}

	
	function checkOrderCancel() {
		if ($("#cancelOrderInd").is(':checked')) {
			appointmentDisable();
			fsAmendmentDisable();
			ccUpdateDisable();
			updateLoinIdDisable();
			updateContactEmailDisable();
			orderCancelEnable();
			progOfferChangeDisable();
			dOrderSelfPickIndDisable();
		} else {
			ccUpdateEnable();
			updateLoinIdEnable();
			updateContactEmailEnable();
			fsAmendmentEnable();
			appointmentEnable();
			orderCancelDisable();
			fsOrderCancelDisable();
			fsAppDisable();
			progOfferChangeEnable(); 
			dOrderSelfPickIndEnable();
		}

		if("${amendOrderImsUI.orderImsUI.imsOrderType}" == "PCD_T" && "${amendOrderImsUI.isDorder}" == "Y" && 
				"${amendOrderImsUI.orderImsUI.isTermDOrderSelfPickTargetCust}" == "Y") {
// 			alert('yes');
			appointmentDisable();
			fsAmendmentDisable();
			ccUpdateDisable();
			updateLoinIdDisable();
			updateContactEmailDisable();
			progOfferChangeDisable();
		}else{
// 			alert('no');
		}
// 		if($("#isPaymentMethodIsCC").val()=="N"){
// 			ccUpdateDisable();
// 		}
		showSubmitButton();
	}

	

	function showFsRemark1(){
		if ($("#fsAmendInd").is(':checked')){
			$("#fsAmendRemark1").attr('disabled', false);
			document.getElementById('fsAmendRemark1').style.display ="block";
		}else{
			hideFsRemark3();
		}
	}
	function showFsRemark2(){
		if ($("#fs2").is(':checked')){
			$("#fsAmendRemark2").attr('disabled', false);
			document.getElementById('fsAmendRemark2').style.display ="block";
		}else{
			hideFsRemark2();
		}
	}
	function showFsRemark3(){
		if ($("#fs3").is(':checked')){
			$("#fsAmendRemark3").attr('disabled', false);
			document.getElementById('fsAmendRemark3').style.display ="block";
		}else{
			hideFsRemark1();
		}
	}
	
	function hideFsRemark3(){
		$("#fsAmendRemark3").attr('disabled', true);
		document.getElementById('fsAmendRemark3').style.display ="none";
	}
	function hideFsRemark2(){
		$("#fsAmendRemark2").attr('disabled', true);
		document.getElementById('fsAmendRemark2').style.display ="none";
	}
	function hideFsRemark1(){
		$("#fsAmendRemark1").attr('disabled', true);
		document.getElementById('fsAmendRemark1').style.display ="none";
	}

	function dOrderSelfPickEnable() {
		document.getElementById('dOrderSelfPickDiv').style.display ="block";
	}
	function dOrderSelfPickDisable() {
		document.getElementById('dOrderSelfPickDiv').style.display ="none";
		
	}
	function orderCancelEnable() {
		document.getElementById('orderCancelDiv').style.display ="block";
		$("#cancelReason").attr('disabled', false);
		$("#cancelRemark").attr('disabled', false);
	}
	function orderCancelDisable() {
		document.getElementById('orderCancelDiv').style.display ="none";
		$("#cancelReason").attr('disabled', true);
		$("#cancelRemark").attr('disabled', true);
	}
	
	function fsOrderCancelEnable() {
		document.getElementById('fsOrderCancelDiv').style.display ="block";
		$("#fsCancelReason").attr('disabled', false);
		$("#fsCancelRemark").attr('disabled', false);
	}
	function fsOrderCancelDisable() {
		document.getElementById('fsOrderCancelDiv').style.display ="none";
		$("#fsCancelReason").attr('disabled', true);
		$("#fsCancelRemark").attr('disabled', true);
	}
	
	
	function appointmentEnable() {
		$("#appointmentInd").attr('disabled', false);
		$("#serial").attr('disabled', false);
		$("#delayReason").attr('disabled', false);
	}
	function appointmentDisable() {
		document.getElementById('appointmentDiv').style.display ="none";
		$("#appointmentInd").attr('checked', false);
		$("#appointmentInd").attr('disabled', true);
		$("#serial").attr('disabled', true);
		$("#delayReason").attr('disabled', true);
	}

	function progOfferChangeDisable(){
		$("#programOfferChange").attr("checked", false);
		$("#programOfferChange").attr("disabled", true);
		$("#progOfferChangeDiv").hide(); 
	};

	function dOrderSelfPickIndDisable(){
		$("#dOrderSelfPickInd").attr("checked", false);
		$("#dOrderSelfPickInd").attr("disabled", true);
		$("#dOrderSelfPickDiv").hide(); 
		fsAppDisable();
	};

	function dOrderSelfPickIndEnable(){
		$("#dOrderSelfPickInd").attr("checked", false);
		$("#dOrderSelfPickInd").attr("disabled", false);
		$("#dOrderSelfPickDiv").hide(); 
	};

	function progOfferChangeEnable(){
		$("#programOfferChange").attr("disabled", false);
	};
	
	
	function fsAppEnable() {
		document.getElementById('fsAppntmentDiv').style.display ="block";
		$("#fsDelayReason").attr('disabled', false);
		if("${CanAmendAppointment}" == "N"){
			
		}
	}
	function fsAppDisable() {
		document.getElementById('fsAppntmentDiv').style.display ="none";
		$("#fsDelayReason").attr('disabled', true);
	}

	function dOrderSelfSrdEnable() {
		document.getElementById('dOrderSelfSrdDiv').style.display ="block";
// 		$("#fsDelayReason").attr('disabled', false);
// 		if("${CanAmendAppointment}" == "N"){
			
// 		}
	}
	function dOrderSelfSrdDisable() {
		document.getElementById('dOrderSelfSrdDiv').style.display ="none";
// 		$("#fsDelayReason").attr('disabled', true);
	}
	function fsAmendmentEnable() {
		$("#fsAmendInd").attr('disabled', false);
		fsAmendment1Enable();
		fsAmendment2Enable();
		fsAmendment3Enable();
	}
	function fsAmendmentDisable() {
		document.getElementById('fsAmendDiv').style.display ="none";
		$("#fsAmendInd").attr('checked', false);
		$("#fsAmendInd").attr('disabled', true);
		fsAmendment1Disable();
		fsAmendment2Disable();
		fsAmendment3Disable();
	}
	
	function fsAmendment1Enable() {
		if ($("#fsAmendInd").is(':checked')) {
			$("#fsAmendNature1").attr('disabled', false);
			showFsRemark1();
		}else{
			fsAmendment1Disable();
		}
	}
	function fsAmendment2Enable() {
		if ($("#fs2").is(':checked')) {
			$("#fsAmendNature2").attr('disabled', false);
			showFsRemark2();
		}else{
			fsAmendment2Disable();
		}
	}
	function fsAmendment3Enable() {
		if ($("#fs3").is(':checked')) {
			$("#fsAmendNature3").attr('disabled', false);
			showFsRemark3();
		}else{
			fsAmendment3Disable();
		}
	}
	function fsAmendment1Disable() {
		$("#fsAmendNature1").attr('disabled', true);
		hideFsRemark1();
		$("#fs1").attr('checked', false); 
		$("#fsAmendNature1").get(0).selectedIndex=0;
		$("#fsAmendRemark1").val(""); 
	}
	function fsAmendment2Disable() {
		$("#fsAmendNature2").attr('disabled', true);
		hideFsRemark2();
		$("#fs2").attr('checked', false);
		$("#fs2").attr('disabled', true);
		$("#fsAmendNature2").get(0).selectedIndex=0;
		$("#fsAmendRemark2").val("");
	}
	function fsAmendment3Disable() {
		$("#fsAmendNature3").attr('disabled', true);
		hideFsRemark3();
		$("#fs3").attr('checked', false);
		$("#fs3").attr('disabled', true);  
		$("#fsAmendNature3").get(0).selectedIndex=0; 
		$("#fsAmendRemark3").val("");
	}
	
	function ccUpdateEnable() {
		$("#updateCreditInd").attr('disabled', false);
		$("#cardtype").attr('disabled', false);
		$("#cardholdername").attr('disabled', false);
		$("#ccExpiryYear").attr('disabled', false);
		$("#ccExpiryMonth").attr('disabled', false);
		$("#cardnum").attr('disabled', false);
		if($("#thirdparty").is(':checked')){
			document.getElementById('faxSerial').style.visibility = "visible";
			$("#faxSerialNum").attr('disabled', false);
		}
	}
	
	function ccUpdateDisable() {
		document.getElementById('updateCreditDiv').style.display ="none";
		$("#updateCreditInd").attr('checked', false);
		$("#updateCreditInd").attr('disabled', true);
		$("#cardtype").attr('disabled', true);
		$("#cardholdername").attr('disabled', true);
		$("#ccExpiryYear").attr('disabled', true);
		$("#ccExpiryMonth").attr('disabled', true);
		$("#cardnum").attr('disabled', true);
		$("#faxSerialNum").attr('disabled', true);
	}
	
	function updateLoinIdEnable() {
		$("#changeLoginId").attr('disabled', false);
		$("#loginName").attr('disabled', false);
	}
	function updateLoinIdDisable() {
		document.getElementById('changeLoginIdDiv').style.display ="none";
		$("#changeLoginId").attr('checked', false);
		$("#changeLoginId").attr('disabled', true);
		$("#loginName").attr('disabled', true);
	}
	
	function updateContactEmailEnable() {
		$("#contactEmailInd").attr('disabled', false);
		$("#contactEmailName").attr('disabled', false);
	}
	function updateContactEmailDisable() {
		document.getElementById('contactEmailDiv').style.display ="none";
		$("#contactEmailInd").attr('checked', false);
		$("#contactEmailInd").attr('disabled', true);
		$("#contactEmailName").attr('disabled', true);
	}



	function openCEKSWindow(){
		//var form = document.forms['createForm'];
		var ceksLink = "imsceks.html";
		if (ceksLink != null && ceksLink != "") {
			window.open(ceksLink, "_blank", "width=550, height=400, resizable=no, toolbar=yes, location=yes, menubar=yes, status=yes, left=100, top=100");
			//form.elements['ceksUrl'].value = "";
		}
	}
	
	//from imsappointment jsp start
	
	
	function checkSrdUnder(){
		if(document.getElementById('orderImsUI.appointment.targetInstallDate').value!=null && document.getElementById('orderImsUI.appointment.targetInstallDate').value!=""){
			var esrd = new Date(
					"${amendOrderImsUI.orderImsUI.appointment.estimatedSrd}".substring(0, 4),
					"${amendOrderImsUI.orderImsUI.appointment.estimatedSrd}".substring(5, 7)-1,
					"${amendOrderImsUI.orderImsUI.appointment.estimatedSrd}".substring(8, 10));
			var srd = new Date(
					document.getElementById('orderImsUI.appointment.targetInstallDate').value.substring(0, 4),
					document.getElementById('orderImsUI.appointment.targetInstallDate').value.substring(5, 7)-1,
					document.getElementById('orderImsUI.appointment.targetInstallDate').value.substring(8, 10));
			if(srd<esrd){				
				return true;
			}else{
				return false;
			}			
		}
	}
	
	function checkSrdOver(){	
		if(document.getElementById('orderImsUI.appointment.targetInstallDate').value!=null && document.getElementById('orderImsUI.appointment.targetInstallDate').value!=""){
			var today = new Date();
			var srd = new Date(
					document.getElementById('orderImsUI.appointment.targetInstallDate').value.substring(0, 4),
					document.getElementById('orderImsUI.appointment.targetInstallDate').value.substring(5, 7)-1,
					document.getElementById('orderImsUI.appointment.targetInstallDate').value.substring(8, 10));
			if(srd<=today){				
				return true;
			}else{
				return false;
			}			
		}
	}
	
	
	
	function getTimeSlot(){
// 		alert("getTimeSlot is called");
// 		$( "#targetCommDate" ).datepicker( "option", "minDate", $("#instdate").val());	

		var targetMinDate = new Date($("#instdate").val());
		
		var targetMaxDate = new Date(
				appdate.substring(0, 4),
				appdate.substring(5, 7)-1,
				appdate.substring(8, 10));
		
		targetMinDate.setDate(targetMinDate.getDate()+parseInt(minDateReq));
		targetMaxDate.setMonth(targetMaxDate.getMonth()+parseInt(maxMonthReq));
		
		$( "#targetCommDate" ).datepicker( "option", "minDate", targetMinDate);
 		$( "#targetCommDate" ).datepicker( "option", "maxDate", targetMaxDate);	


		$.ajax({
			url : 'imstimeslot.html?date=' + $("#instdate").val()
					+ '&time=' + $("#timeSlot").val()
					+ '&type=' + $("#slotType").val(),
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
				$('option', '#timeSlot').remove();
				$('option', '#slotType').remove();
				$('#timeSlot').append(new Option('', '', true, true));
				$('#slotType').append(new Option('', '', true, true));
				$.each(eval(msg), function(i, item) {
					if((item.errorMsg != null && item.errorMsg != "") && item.i == "0"){
						var timeslotoptions = $('#timeSlot').attr('options');
						var slottypeoptions = $('#slotType').attr('options');							
						timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
						slottypeoptions[slottypeoptions.length] = new Option(item.tsdisplay, item.tstype, true, false);
						$("#timeSlot").attr("disabled", true);
						alert(item.errorMsg);
					}else{
						var timeslotoptions = $('#timeSlot').attr('options');
						var slottypeoptions = $('#slotType').attr('options');	
						timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
						slottypeoptions[slottypeoptions.length] = new Option(item.tsdisplay, item.tstype, true, false);
					}
				});
				
				colorTimeSlot();
			}
		});				
	}

	function getFSTimeSlot(){
// 		alert("getTimeSlot is called");
// 		$( "#targetCommDate" ).datepicker( "option", "minDate", $("#instdate").val());	

		var fsTargetMinDate = new Date($("#fsInstdate").val());
		
		var fsTargetMaxDate = new Date(
				appdate.substring(0, 4),
				appdate.substring(5, 7)-1,
				appdate.substring(8, 10));
		
		fsTargetMinDate.setDate(fsTargetMinDate.getDate()+parseInt(minDateReq));
		fsTargetMaxDate.setMonth(fsTargetMaxDate.getMonth()+parseInt(maxMonthReq));
		
		$( "#fsCommDate" ).datepicker( "option", "minDate", fsTargetMinDate);
 		$( "#fsCommDate" ).datepicker( "option", "maxDate", fsTargetMaxDate);	

		$.ajax({
			url : 'imsgetamendsrdtimeslots.html?date=' + $("#fsInstdate").val(),
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
				$('option', '#fsTimeSlot').remove();
			//	$('option', '#slotType').remove();
				$('#fsTimeSlot').append(new Option('', '', true, true));
			//	$('#slotType').append(new Option('', '', true, true));
				$.each(eval(msg), function(i, item) {
					if((item.errorMsg != null && item.errorMsg != "") && item.i == "0"){
						var timeslotoptions = $('#fsTimeSlot').attr('options');
// 						var slottypeoptions = $('#slotType').attr('options');							
						timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
// 						slottypeoptions[slottypeoptions.length] = new Option(item.tsdisplay, item.tstype, true, false);
						$("#fsTimeSlot").attr("disabled", true);
						alert(item.errorMsg);
					}else{
						var timeslotoptions = $('#fsTimeSlot').attr('options');
// 						var slottypeoptions = $('#slotType').attr('options');	
						timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
// 						slottypeoptions[slottypeoptions.length] = new Option(item.tsdisplay, item.tstype, true, false);
					}
				});
				
// 				colorTimeSlot();
			}
		});				
	} 
	
	function colorTimeSlot(){		
// 		alert("colorTimeSlot is called");		
		var timeslotlist=document.getElementById("timeSlot");
		var slottypelist=document.getElementById("slotType");
		var i;
		for (i=1;i<timeslotlist.length;i++){//skip first element			
			if(document.getElementById("instdate").value==instdate &&
					timeslotlist.options[i].value==insttime){
				slottypelist.options[i].value="CURRENT_SELECTED";
				timeslotlist.options[i].selected=true;
				slottypelist.options[i].selected=true;
			}
			else if (slottypelist.options[i].value=="NS"){ //indicate no resource
				
				if (BrowserDetect.OS == "iPad" || BrowserDetect.OS == "iPhone/iPod" || BrowserDetect.OS == "Linux") 
				{
					timeslotlist.options.remove(i);
					slottypelist.options.remove(i);
						i--;
				}else
				timeslotlist.options[i].style.color="red";
					
			}else if(slottypelist.options[i].value=="NA"){
				if (BrowserDetect.OS == "iPad" || BrowserDetect.OS == "iPhone/iPod" || BrowserDetect.OS == "Linux") 
				{
					timeslotlist.options.remove(i);
					slottypelist.options.remove(i);
					i--;
				}else
				timeslotlist.options[i].style.color="silver";
			}
		}		
	} 	
	
	
	
	function timeConfirm(){
// 		alert("timeConfirm is called");
		if(checkOrderStatus()){
			if(document.getElementById("timeSlot").disabled==false){
				if(document.getElementById("instdate").value.length <1){
					alert("please select Target Installation Date");
				}else if(document.getElementById("timeSlot").selectedIndex==0){
					alert("please select timeslot");
				}else if(document.getElementById("slotType").value=="NS"||document.getElementById("slotType").value=="NA"){
					alert("please select valid timeslot");
				}else if(document.getElementById("slotType").value=="CURRENT_SELECTED"){
					alert("Currently booked time slot");			
				}else{			
					
					$.ajax({
						url : 'imsreserveappointment.html?date=' + $("#instdate").val()
								+ '&time=' + $("#timeSlot").val()
								+ '&type=' + $("#slotType").val(),
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
							$.each(eval(msg), function(i, item) {						
								if(item.returnValue == -1){
									alert(item.errorMsg);
								}else{
									document.getElementById('preBkSerialNum').value = item.serial;
								}
							});	
							
// 							alert("instdate:"+instdate+"\n"+
// 						"document.getElementById('orderImsUI.appointment.targetInstallDate').value:"+document.getElementById('orderImsUI.appointment.targetInstallDate').value+"\n"+
// 						"insttime:"+insttime+"\n"+
// 						"document.getElementById('instdate').value:"+document.getElementById('instdate').value+"\n"+
// 						"document.getElementById('timeSlotDisplay').value:"+document.getElementById('timeSlotDisplay').value+"\n"+
// 						"document.getElementById('timeSlot').value:"+document.getElementById('timeSlot').value);
							
							document.getElementById('orderImsUI.appointment.targetInstallDate').value =	document.getElementById('instdate').value;
							document.getElementById("timeSlotDisplay").value = document.getElementById("timeSlot").options[document.getElementById("timeSlot").selectedIndex].text;
							
							instdate = document.getElementById('orderImsUI.appointment.targetInstallDate').value;
							insttime = document.getElementById('timeSlot').value;			
// 							colorTimeSlot();
							getTimeSlot();
							document.getElementById('timeSlot').value = insttime;	
						}
					});					
				}	
			}	
		}
	}
	
	
	function checkOrderStatus(){
// 		alert("checkOrderStatus is called");
// 		alert("${amendOrderImsUI.orderImsUI.appointment.actionInd}");
		if("${amendOrderImsUI.orderImsUI.appointment.actionInd}"=="R"){
			alert("order has been cancelled!");
			return false;
		}else{
			return true;
		}
	}
	//from imsappointment jsp end
	
	
	
	function openOfferChangeDialog(){
		parent.parent.openModalDialog("imsorderdetail.html?orderId=${ImsOrder.orderId}&status=${ImsOrder.orderStatus}", "Order Id :${ImsOrder.orderId}","amendProgOfferPage");
	}
	
	function openOfferChangeDialog4Ntv(){
		parent.parent.openModalDialog("${ntvdomain}ntvorderdetail.html?_al=true&orderId=${ImsOrder.orderId}&status=${ImsOrder.orderStatus}", "Order Id :${ImsOrder.orderId}","amendProgOfferPage");
	}
	
	function openChildWindow(sbuid, ocid) {
		var win = window.open("${ntvdomain}ntvorderdetail.html?_al=true&orderId=${ImsOrder.orderId}&status=${ImsOrder.orderStatus}", '_blank'); 
		win.focus();
	}
	
	//Celia  -  Amendment Notification Distribution Mode
	
	function checkMobile()
	{if ($("input[name=SMSno]").val().length != 0) {
		$.ajax({
			url : 'checkmobilenum.html?SMS=' + $("#SMSno").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) 
			{
			    if(textStatus=='parsererror')
			    {
			        alert("Your session has been timed out, please re-login."); 
			    }
			    else if(textStatus == 'timeout')
			    {
			    	alert("Server response timeout, please try again.");
			    }
			    $(".error_SMSno").text("").hide();
			},
			success : function(msg) 
			{
				var count=0;
				$.each(eval(msg), function(i, item) 
				{
					if(item.valid)
					{
						$(".error_SMSno").text("").hide();				
					}
					else if (!item.valid )
					{
						$(".error_SMSno").text("Invalid mobile number").show();
					}
					count++;
				});
				if(count==0)
				{
					$(".error_SMSno").text("").hide();
				}
			}
		});
	}else
		$(".error_SMSno").text("").hide();
	}
	function checkEmail(){
			if ($("input[name=emailAddr]").val().length != 0 && !(/^\S+@\S+$/).test($("input[name=emailAddr]").val())) 
		{
			$(".error_emailAddr").text("Invalid Email format").show();
		}	else{
			$(".error_emailAddr").text("").hide();
		}
			
	}
	function checkContactEmail(){
		//document.getElementById('error_ContactEmailAddr').style.display ="none";
		$("#error_ContactEmailAddr").css("display", "none");
			if ($("input[name=contactEmail]").val().length != 0 && !(/^\S+@\S+$/).test($("input[name=contactEmail]").val())) 
		{
			$("#error_ContactEmailAddr").text("Invalid Email format");
			$("#error_ContactEmailAddr").css("display", "block");
// 			document.getElementById('error_ContactEmailAddr').style.display ="block";
		}	else{
			$("#error_ContactEmailAddr").text("").hide();
		}
		
	}
	
	//Terrence Part
	function loginNameCheck()
	{		
		$("#loginName").val($("#loginName").val().toLowerCase());
		if(document.getElementById("loginName").disabled==true){			
			return;
		}
		$.ajax({
			url : 'checkloginname.html?loginName=' + $("#loginName").val().replace("&", "*") + '&docType=' + docType + '&idDocNum=' + docNum,
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
				
					if(item.valid == true){
						if(item.reserveResult == "-7"){
							document.getElementById('error_login').style.visibility = "hidden";						
							document.getElementById('warning_login').style.visibility = "visible";
							document.getElementById('warning_login_msg').innerHTML = item.errorText;
							isReserved=false;
													
						}else if(item.isExist == true && item.loginName != item.oldLoginName && (item.loginName != null && item.loginName != "")){
							document.getElementById('warning_login').style.visibility = "visible";
							document.getElementById('warning_login_msg').innerHTML = "Please try " + item.hintOne + ", " + item.hintSecond + ", " + item.hintThird + "";
							document.getElementById('error_login').style.visibility = "visible";
							document.getElementById('error_login_msg').innerHTML = "Login ID & E-mail address already used";	
							
							isReserved=false;
						}else if(item.isExist == false){
							//reserve ok
							isReserved=true;
							document.getElementById("reservedId").value = item.loginName;
							document.getElementById("loginOK").style.display = "";							
							document.getElementById('warning_login').style.visibility = "hidden";
							document.getElementById('error_login').style.visibility = "hidden";
						}
					}else if(item.valid == false){
						document.getElementById('error_login').style.visibility = "hidden";						
						document.getElementById('warning_login').style.visibility = "visible";
						document.getElementById('warning_login_msg').innerHTML = item.errorText;
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
	
    function NotificationSendAlertDialog(){
    	
		var box= "#email_alert_dialog";
		
		$(box).dialog({
			width: 400,
			resizable: false,
			draggable: false,
			modal: true	
		
		});
	
	}
	function NotificationSendConfirm(){	
		$("#notificationSendConfirmInd").val("Y");
// 		if (document.getElementById('SendComfirmY').checked==true){
			if ($('input[name=disMode]:checked').val()== null) {
				$('#email_alert_dialog').dialog('close');
				alert("Require to choose either distribution method.");		
			}else if ($("input[name=SMSno]").val().length == 0 && $('input[name=disMode]:checked').val()== 'S' ){
				$('#email_alert_dialog').dialog('close');
				alert("Missing mobile no. for sending notification. ");			
			}else if ($("input[name=emailAddr]").val().length == 0 && $('input[name=disMode]:checked').val()== 'I'){
				$('#email_alert_dialog').dialog('close');
				alert("Missing Email address for sending notification. ");			
			}else
				submitForm();		
// 		}else {
// 			submitForm();	
// 		}
	}
	function NotificationSendCancel(){	
		$("#notificationSendConfirmInd").val("N");
		$('#email_alert_dialog').dialog('close');
		submitForm();
	}	
	

	function disModeDefaultValue(){
		<c:choose>
			<c:when test='${amendOrderImsUI.orderImsUI.isCC=="Y" or amendOrderImsUI.orderImsUI.isPT=="Y"}'>
				<c:choose>				
					<c:when test='${not empty amendOrderImsUI.emailAddr}'> 
						$("[name='disMode'][value='I']").attr("checked",true);
						$("[name='disMode'][value='S']").attr("checked",false);
					</c:when> 
					<c:when test='${not empty amendOrderImsUI.SMSno}'> 
						$("[name='disMode'][value='I']").attr("checked",false);
						$("[name='disMode'][value='S']").attr("checked",true);
					</c:when>
				</c:choose>
			</c:when>
			<c:when test ='${ImsOrder.orderType eq "NTV-A" || ImsOrder.orderType eq "NTVRE"|| ImsOrder.orderType eq "NTVAO" || ImsOrder.orderType eq "NTVUS" }'>	
				<c:choose>
					<c:when test='${not empty amendOrderImsUI.SMSno or not empty amendOrderImsUI.emailAddr }'>
						$("#emailAddr").attr("readonly","true");
						$("#SMSno").attr("readonly","true");									
					</c:when>
				</c:choose>						
			</c:when>
		</c:choose>

		if(isCC == "Y" || isPT == "Y") $("#disheader").hide();    //tmp by jacky
	}
</script>



<!-- <link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"rel="stylesheet" /> -->

<form:form name="imsOrderAmendForm" method="POST" commandName="amendOrderImsUI">
<input type="hidden" name="sbuid" value="8939db9f-c618-442b-84d2-293aed7dda19">

							        			<form:hidden path="isDOrderSelfPickEnabled"/>
							        			<form:hidden path="isCancelOrderEnabled"/>
							        			<form:hidden path="isAppointmentEnabled"/>
							        			<form:hidden path="isPreInstCommDateEnabled"/>
							        			<form:hidden path="isPreInstApptEnabled"/>
							        			<form:hidden path="isFsAmendEnabled"/>
							        			<form:hidden path="isUpdateCreditEnabled"/>
							        			<form:hidden path="isLoginIdEnabled"/>
							        			<form:hidden path="isContactEmailEnabled"/>
							        			<form:hidden path="isChangeProgOfferEnabled"/>
												<form:hidden id="registerName" path="registerName"/>
<%-- 							        			<form:hidden path="isPaymentMethodIsCC"/> --%>
												<form:hidden path="notificationSendConfirmInd"/> 
							        			
<table width="100%" class="tablegrey">
	<tbody><tr>
		<td bgcolor="#FFFFFF"><!--content-->
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tbody><tr>
					<td class="table_title">Order Amendment</td>
				</tr>
			</tbody></table>
			<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
				<tbody>
				<tr><td colspan="4">&nbsp;</td></tr>
				<tr>
					<td colspan="4">
						<table width="100%" cellspacing="0" border="0" bgcolor="white">
							<tbody>
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="25%">Service Number :<span class="contenttext_red"></span></td>
								<td class="contenttext_blue">${amendOrderImsUI.orderImsUI.customer.serviceNum}</td>
							</tr>												
							</tbody>
						</table>
					</td>
				
				
				</tr>
				<tr><td colspan="4">&nbsp;</td></tr>
				<tr>
					<td colspan="4">
						<table width="100%" cellspacing="0" border="0" bgcolor="white">
							<tbody><tr> 
                   				<td colspan="4" class="table_title">Order Amendment Taken Sales Info.<br></td>
            				</tr>
            				<tr><td colspan="4">&nbsp;</td></tr>
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="25%">Staff No.<span class="contenttext_red">*</span> : </td>
								<td colspan="2">
									<input id="staffNum" name="staffNum" readonly="readonly" type="text" value="${amendOrderImsUI.bomSalesUserDTO.username}">
								</td>
							</tr>
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="25%">Salesmen Code / CMRID<span class="contenttext_red">*</span> :</td>
								<td colspan="2">
								<c:choose>
								<c:when test = "${amendOrderImsUI.bomSalesUserDTO.salesCd eq null}">
								<input id="salesId" name="salesId" readonly="readonly" type="text" value="${amendOrderImsUI.orderImsUI.salesCd}" maxlength="10">
								</c:when>
								<c:otherwise>
								<input id="salesId" name="salesId" readonly="readonly" type="text" value="${amendOrderImsUI.bomSalesUserDTO.salesCd}" maxlength="10">
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="25%">Sales Channel<span class="contenttext_red">*</span> : </td>
								<td colspan="2">
									<input id="salesChannel" name="salesChannel" readonly="readonly" type="text" value="${amendOrderImsUI.bomSalesUserDTO.channelCd}">
								</td>
							</tr>
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="25%">Team / Shop Code<span class="contenttext_red">*</span> : </td>
								<td colspan="2">
									<input id="shopCode" name="shopCode" readonly="readonly" type="text" value="${amendOrderImsUI.bomSalesUserDTO.shopCd}">
								</td>
							</tr>
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="25%">Sales Contact No.<span class="contenttext_red">*</span> :</td>
								<td colspan="2">
								<c:choose>
								<c:when test = "${amendOrderImsUI.bomSalesUserDTO.shopContactPhone eq null}">
								<input id="salesContactNum" name="salesContactNum" readonly="readonly" type="text" value="${amendOrderImsUI.orderImsUI.salesContactNum}">
								</c:when>
								<c:otherwise>
								<input id="salesContactNum" name="salesContactNum" readonly="readonly" type="text" value="${amendOrderImsUI.bomSalesUserDTO.shopContactPhone}">
								</c:otherwise>
								</c:choose>
								</td>
							</tr>								
            				<tr><td colspan="4">&nbsp;</td></tr>							
						</tbody></table>
					</td>
				</tr>		
				
				
				
				
				
				<tr>
							<td colspan="4">
								<table width="100%" cellspacing="0" border="0" bgcolor="white">
									<tr>
										<td colspan="4" class="table_title">Applicant Info.<br /></td>
									</tr>
									<tr>
										<td colspan="4">&nbsp;</td>
									</tr>
									<tr>
										<td width="5%">&nbsp;</td>
										<td width="25%">Applicant Name<span
											class="contenttext_red">*</span> :
										</td>
										<td colspan="2">
													<form:textarea id="appName"  path="applicantName" rows="1" cols="30"/>	
											</td>
									</tr>
									<tr>
										<td width="5%">&nbsp;</td>
										<td width="25%">Applicant Contact No.<span
											class="contenttext_red">*</span> :
										</td>
										<td colspan="2">
													<form:textarea id="appNum"  path="applicantContactNo" rows="1" cols="30"/>	
											</td>
									</tr>
									<tr>
										<td width="5%">&nbsp;</td>
										<td width="25%">Applicant Relationship<span class="contenttext_red">*</span> :
										</td>
										<td colspan="1">
							        	    		<form:select id="applicantRelationshipSelect"  path="applicantRelationship">
														<form:option value="" label="-select-">-- SELECT --</form:option>
														<form:option value="Sub">Sub</form:option>
														<form:option value="Mother">Mother</form:option>
														<form:option value="Father">Father</form:option>
														<form:option value="Daughter">Daughter</form:option>
														<form:option value="Son">Son</form:option>
														<form:option value="Wife">Wife</form:option>
														<form:option value="Husband">Husband</form:option>
													</form:select>
										</td>
									</tr>
									<tr>
										<td colspan="4">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
				
				<tr>
				<td colspan="4">
<!-- celia : Amendment Notification Distribution Mode--> 
 <table width="100%" border="0" cellspacing="1" class="contenttext" id="disheader">
				            		<tbody>
				            			<tr>
				            				<td colspan="4" class="table_title">Amendment Notification Distribution Mode</td>
				            			</tr>
				      				</tbody>				   
				            		</table>
	<c:choose>
		<c:when test="${ims_direct_sales eq true}">
				        
									<table>
										<tbody>
										<tr>
										<td width="100%">										
										<div id="emailAddrDiv" style="margin-left: 10px;">Email:									
										<form:input path="emailAddr" maxlength="64"	cssStyle="width:20em; color:red ; font-weight:bold"	onblur="checkEmail()"/>
										<div class="clear2"></div>
										<span class="error error_emailAddr"	style="display: none"></span>
										<form:errors path="emailAddr" cssClass="error" />
										</div></td>
										<td width="5%">&nbsp;<br></br>
										</td>
										</tr>
										<tr>
										<td width="100%">
										<div id="SMSnoDiv" style="margin-left: 10px;">SMS :
										<form:input path="SMSno" maxlength="64"	cssStyle="width:20em; color:red ; font-weight:bold"	onblur="checkMobile()" />
										<div class="clear2"></div>
										<span class="error error_SMSno"	style="display: none"></span>
										<form:errors path="SMSno" cssClass="error" />
										</div></td>
										<td width="5%">&nbsp;</td>
										</tr>
										<tr>
										<td width="5%">&nbsp;</td>
										</tr>
										</tbody>
									</table>
									</c:when>
																
		<c:when test='${amendOrderImsUI.orderImsUI.isCC=="Y" or amendOrderImsUI.orderImsUI.isPT =="Y"}'>			
		 			<table style="display:none;"> 
					<tr>
						<td width="100%">
							<div align="left" style="margin-left:10px;"> 	
								<form:radiobutton path="disMode" label="Email" value="I" />									
							</div>
						</td>
						<td width="100%">				
							<div id="emailAddrDiv" style="margin-left: 10px;"> 	
								<form:input path="emailAddr" maxlength="64" cssStyle="width:20em; color:red ; font-weight:bold" onblur="checkEmail()"/>
								<div class="clear2"></div>
								<span class="error error_emailAddr" style="display:none"></span>						
								<form:errors path="emailAddr" cssClass="error"/>
							</div>
						</td>
					</tr>
						<tr>					
						<td width="100%" >
							<div style="margin-left: 10px;"> 
								<form:radiobutton path="disMode" label="SMS" value="S"  />
							</div>
						</td>
						<td width="100%">				
							<div id="SMSnoDiv" style="margin-left: 10px;">							
								<form:input path="SMSno" maxlength="64" cssStyle="width:20em; color:red ; font-weight:bold" onblur="checkMobile()" />
								<div class="clear2"></div>
								<span class="error error_SMSno" style="display:none"></span>						
								<form:errors path="SMSno" cssClass="error"/>
							</div>
						</td>
					</tr>
					</table>
					</c:when>		
		<c:otherwise>
					<table >
					<tr>	
						<td width="100%">				
							<div id="emailAddrDiv" style="margin-left: 10px;"> Email:														
								<form:input path="emailAddr" maxlength="64" cssStyle="width:20em; color:red ; font-weight:bold" onblur="checkEmail()"/>
								<div class="clear2"></div>
								<span class="error error_emailAddr" style="display:none"></span>						
								<form:errors path="emailAddr" cssClass="error"/>
							</div>
						</td>
					</tr>	
					<tr><td>&nbsp;</td></tr>
					</table>
		</c:otherwise>
	</c:choose>	
	</td>				
		</tr>		
								
				<tr>
					<td colspan="4">
						<table width="100%" cellspacing="0" border="0" bgcolor="white">
							<tbody>
							<tr><td colspan="4" class="table_title">Amendment Options</td></tr>
            				<tr><td colspan="4">&nbsp;</td></tr>
							<tr>
								<td colspan="4">
								<div >
									<table width="100%" cellspacing="0" border="0" class="contenttext">
										<tbody>
										<c:if  test='${amendOrderImsUI.amendModeDto.cancelOrder=="Y" }'>
										<tr id="autoFlowThroughCancel">
											<td width="10"><input id="cancelOrderInd" name="cancelOrderInd" type="checkbox" value="true"><input type="hidden" name="_cancelOrderInd" value="on"></td>
											<td width="20%"><b>Order Cancellation</b></td>
											<td></td>
										</tr>
										</c:if>
										<c:if  test='${amendOrderImsUI.amendModeDto.amendAppointment=="Y" }'>
										<tr id="autoFlowThroughAppointment">
											<td width="10"><input id="appointmentInd" name="appointmentInd" type="checkbox" value="true"><input type="hidden" name="_appointmentInd" value="on"></td>
											<td width="20%"><b>Appointment</b></td>
											<td></td>
										</tr>
										</c:if>
										<c:if  test='${amendOrderImsUI.amendModeDto.updateCreditCard=="Y" }'>
										<tr>
											<td width="10"><input id="updateCreditInd" name="updateCreditInd" type="checkbox" value="true"><input type="hidden" name="_updateCreditInd" value="on"></td>
											<td width="20%"><b>Update Credit Card</b></td>
											<td></td>
										</tr>
										</c:if>
									<c:if  test='${amendOrderImsUI.amendModeDto.changeLoginId=="Y" }'>
										<tr 
											<c:if test="${amendOrderImsUI.orderImsUI.orderType eq 'NTV-A' || amendOrderImsUI.orderImsUI.orderType eq 'NTVAO' 
												|| amendOrderImsUI.orderImsUI.orderType eq 'NTVRE' || amendOrderImsUI.orderImsUI.orderType eq 'NTVUS'}">
												style="display:none;"
											</c:if>
											>
											<td width="10"><input id="changeLoginId" name="changeLoginId" type="checkbox" value="true"><input type="hidden" name="_changeLoginId" value="on"></td>
											<td width="20%"><b>Login ID</b></td>
											<td></td>
										</tr>
										</c:if>
										<c:if  test='${amendOrderImsUI.amendModeDto.updateContactEmail=="Y" }'>
										<tr>
											<td width="10"><input id="contactEmailInd" name="contactEmailInd" type="checkbox" value="true"><input type="hidden" name="_contactEmailInd" value="on"></td>
											<td width="20%"><b>Contact Email Address</b></td>
											<td></td>
										</tr>
										</c:if>
										<c:if  test='${amendOrderImsUI.amendModeDto.fsAmendment=="Y" }'>
										<tr id="fsAmendTickBox">
											<td width="10"><input id="fsAmendInd" name="fsAmendInd" type="checkbox" value="true"><input type="hidden" name="_fsAmendInd" value="on"></td>
											<td width="20%"><b>F&amp;S Amendment</b></td>
											<td></td>
										</tr>
										</c:if>
										<c:if  test='${amendOrderImsUI.orderImsUI.imsOrderType eq "PCD_T"  && amendOrderImsUI.isDorder eq "Y" && amendOrderImsUI.orderImsUI.isTermDOrderSelfPickTargetCust eq "Y"}'>
										<td width="10"><input id="dOrderSelfPickInd" name="dOrderSelfPickInd" type="checkbox" value="true"><input type="hidden" name="_dOrderSelfPickInd" value="on"></td>
										<td width="20%"><b>D order self pick / FS</b></td>
										</c:if>
											
										<tr><td colspan="5"></td></tr>
									</tbody></table></div>
									
									
									<div >
									<table width="100%" cellspacing="0" border="0" class="contenttext">
										<tbody>
										<tr><td colspan="5"></td></tr>
									</tbody></table></div>
								
<!-- 								111111111111111111111111111111111111111  order cancel -->
									<div id="orderCancelDiv" style="display: none;">
										<table width="100%" cellspacing="0" border="0" bgcolor="white">
											<tbody>
											<tr><td colspan="4" class="table_title">Order Cancellation</td></tr>
				            				<tr><td colspan="4">&nbsp;</td></tr>
				            				<tr>
					            				<td width="5%">&nbsp;</td>
					            				<td width="20%">Cancel Reason<span class="contenttext_red">*</span>: </td>
					            				<td colspan="2">
													<c:choose>
													<c:when test="${not empty cancelReasons}">
													<form:select id="cancelReason" path="cancelReason">
														<form:option value="" label="Select..."/>
														<form:options items="${cancelReasons}" itemValue="delayReason" itemLabel="delayReason"/>
													</form:select>	
													<div style="display: none;">				
														<form:select id="cancelReasonCode" path="cancelReasonCode" >
															<form:option value="" label="Select..."/>
															<form:options items="${cancelReasons}" itemValue="delayReasonCode" itemLabel="delayReason"/>
														</form:select>
													</div>					
													</c:when>					
													<c:otherwise>
														<select id="cancelReason"  style="width:200px;" disabled> 
															<option selected="selected" value = "">Select...</option>
														</select>
													</c:otherwise>			
													</c:choose>					            					
					            				</td>
					            			</tr>
											
					            			<tr><td width="5%">&nbsp;</td></tr>
					            			<tr>
					            				<td width="5%">&nbsp;</td>
					            				<td width="20%">Cancel Remark<span class="contenttext_red">*</span> : </td>
					            				<td >
													<form:textarea id="cancelRemark" path="cancelRemark" rows="5" cols="100" />
							                   	</td>
					            			</tr>
					            			<tr><td colspan="4">&nbsp;</td></tr>
					            			
					            		<tr>
					            		<td width="5%">&nbsp;</td>
					            		<td colspan="3" style="color:red; font-weight:bold;">
					            		<c:if  test='${amendOrderImsUI.orderImsUI.imsOrderType eq "PCD_T"  && amendOrderImsUI.isDorder eq "Y" && amendOrderImsUI.orderImsUI.isTermDOrderSelfPickTargetCust eq "Y"}'>
											Before cancel order, please go to Equipment Collection Web  to ensure equipment are not yet returned to us.
										</c:if>
										<c:if  test='${amendOrderImsUI.orderImsUI.isTermDOrderSelfPickTargetCust eq "Y"}'>
<!-- 											target -->
										</c:if>
										<c:if  test='${amendOrderImsUI.isDorder eq "Y"}'>
<!-- 											dorder -->
										</c:if>
										<c:if  test='${amendOrderImsUI.orderImsUI.imsOrderType eq "PCD_T"}'>
<!-- 											pcd_t  -->
										</c:if>
				            			</td>
				            			</tr>
					            		<tr><td colspan="4">&nbsp;</td></tr>
					            		<tr><td colspan="4">&nbsp;</td></tr>
				            			</tbody></table>
									</div>
									
<!-- 								2222222222222222222222222222222222222222  appointment -->
									<div id="appointmentDiv" style="display: none;">
<!-- 										<table id="booking" width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext"> -->
										<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
										<tbody>
										<tr id="appointment_header" style=""> 
												<c:if test='${CanAmendAppointment == "Y"}'>
				                   					<td colspan="4" class="table_title">Appointment</td>
				                   				</c:if>
												<c:if test='${CanAmendAppointment == "N"}'>
				                   					<td colspan="4" class="table_title">SRD</td>
				                   				</c:if>
				            			</tr>
										<tr><td width="5%">&nbsp;</td></tr>
<!-- 										from imsappointment start -->
											<c:if  test='${IsPreInstOrder != "Y" || (IsPreInstOrder == "Y" && amendOrderImsUI.amendModeDto.changeSRD=="Y")}'>
												<tr id="earliest_SRD">
												<td>
													<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em; ">
														<p>
															<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em; margin-left: 50px;"></span>
														</p>
														<div class="contenttext">
															<b>Estimated Earliest SRD:${amendOrderImsUI.orderImsUI.appointment.estimatedSrd} </b> :
															<b>(Ref: ${amendOrderImsUI.orderImsUI.appointment.estSrdReason })</b>
														</div>
														<p></p>
													</div>
												</td>
												</tr>
												</c:if>
											</tbody>
												
									
									<form:hidden path="orderImsUI.appointment.targetInstallDate"/>
							        <tr>
							        	<td colspan="4">
							        	<table width="100%" cellspacing="0">
										
										<c:if  test='${IsPreInstOrder != "Y" || (IsPreInstOrder == "Y" && amendOrderImsUI.amendModeDto.changeSRD=="Y")}'>
										<c:if  test='${IsPreInstOrder == "Y" && amendOrderImsUI.amendModeDto.changeSRD=="Y" && amendOrderImsUI.amendModeDto.changeCommDate=="Y"}'>	
										<tr>
										<td width="5%">&nbsp;</td>
										<td width="25"><input id="preInstApptInd" name="preInstApptInd" type="checkbox" value="true"><input type="hidden" name="_preInstApptInd" value="on">
										Change Appointment Date</td>
										<td align="left"></td>
										<td>&nbsp;</td>
										</tr>	
										</c:if>
										
										<tr id="existPreBookNum">
					            				<td width="5%">&nbsp;</td>
								        	<td align="left" style="width:300px;">Existing Pre-book Serial no.:</td>
											<td align="left" ><form:input path="orderImsUI.appointment.serialNum" readonly="true"/> </td>
										</tr>	
										<tr id="bookcols">
					            				<td width="5%">&nbsp;</td>
								        	<td align="left" style="width:300px;">New Pre-book Serial no.:</td>
											<td align="left" >
												<form:input path="preBkSerialNum" readonly="true"/>  
											</td>
										</tr>	
												<tr id="targetdatecols">
					            				<td width="5%">&nbsp;</td>
												<c:if test='${CanAmendAppointment == "Y"}'>
													<td width="25%">Target Installation Date<span class="contenttext_red">*</span>: </td>
												</c:if>
												<c:if test='${CanAmendAppointment == "N"}'>
													<td align="right" width="25%">SRD<span class="contenttext_red">*</span>: </td>
												</c:if>
													<td align="left">				
														<input id="instdate" value=""  readonly="readonly"/>                 
													</td>
													<td>&nbsp;</td>
												</tr>
										<tr id="timeslotcols">
					            			<td width="5%">&nbsp;</td>
<!-- 											<td align="left"  style="width:300px;">Timeslot:</td> -->
											<td width="25%">Target Installation Time<span class="contenttext_red">*</span>: </td>
											<td align="left" style="padding-left: 3px;">				
												<form:select path="timeSlot" cssStyle="width:100px">
													<form:option value=""/>
													<form:options items="${timeSlots}" itemValue="timeSlot" itemLabel="timeSlotDisplay"/>
												</form:select>
												<form:hidden path="timeSlotDisplay"/>									
												<div style="display: none;">				
													<form:select id="slotType" path="">				
														<form:option value=""/>
														<form:options items="${timeSlots}" itemValue="slotType" itemLabel="timeSlot"/>
													</form:select>
												</div>												
												&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <a href="###;" class="nextbutton" onclick="timeConfirm()">Confirm</a>
											</td>			
											<td>&nbsp;</td>
										</tr>		
											
				            				<tr id="delayReason">
					            				<td width="5%">&nbsp;</td>
					            				<td width="25%">Delay Reason<span class="contenttext_red">*</span>: </td>
					            				<td colspan="2">
													<c:choose>
													<c:when test="${not empty delayReasons}">
													<form:select id="delayReasonCode" path="delayReasonCode">
														<form:option value="" label="Select..."/>
														<form:options items="${delayReasons}" itemValue="delayReasonCode" itemLabel="delayReason"/>
													</form:select>							
													<div style="display: none;">				
														<form:select id="AutoDelayReason" path="delayReason" >
															<form:option value="" label="Select..."/>
															<form:options items="${delayReasons}" itemValue="delayReason" itemLabel="delayReason"/>
														</form:select>
													</div>							
													</c:when>					
													<c:otherwise>
														<select id="delayReason"  style="width:200px;" disabled> 
															<option selected="selected" value = "">Select...</option>
														</select>
													</c:otherwise>			
													</c:choose>					            					
					            				</td>
					            			</tr>
					            			
					            			
											<tr id="bomRemark">
					            				<td width="5%">&nbsp;</td>
												<td valign="top" align="left">BMO Remarks:</td>
												<td colspan="2">
													<input readonly="readonly" style="width: 200px; height: 48px;"
													value="${amendOrderImsUI.orderImsUI.appointment.bmoRmk}"> 
													</input>			
												</td>
											</tr>		
											</c:if>
											
											
										
										<c:if  test='${IsPreInstOrder == "Y" && amendOrderImsUI.amendModeDto.changeCommDate=="Y"}'>	
										<c:if  test='${amendOrderImsUI.amendModeDto.changeSRD=="Y" && amendOrderImsUI.amendModeDto.changeCommDate=="Y"}'>
										<tr id="commcols1">
										<td width="5%">&nbsp;</td>
										<td width="25"><input id="preInstCommDateInd" name="preInstCommDateInd" type="checkbox" value="true"><input type="hidden" name="_preInstCommDateInd" value="on">
										Change Target Commencement Date</td>
										<td align="left"></td>
										<td>&nbsp;</td>
										</tr>	
										</c:if>		
										<tr id="existCommDate">
					            				<td width="5%">&nbsp;</td>
								        	<td align="left" style="width:300px;">Existing Commencement Date:</td>
											<td align="left" ><input readonly="readonly" value="<fmt:formatDate value='${amendOrderImsUI.orderImsUI.targetCommDate}' pattern='yyyy/MM/dd' />">
											</input></td>
										</tr>
										<tr id="commcols2">
										<td width="5%">&nbsp;</td>
										<td width="25%">
											Target Commencement Date: <br/>(For Pre-installation Only)
										</td>
										<td align="left">				
											<input id="targetCommDate" value=""  readonly="readonly"/>                 
										</td>
										<form:hidden path="orderImsUI.appointment.targetCommDate"/>
										<td>&nbsp;</td>
										</tr>							
										</c:if>
											
											<tr><td width="5%">&nbsp;</td></tr>
										</table></td>
										</tr>
										
<!-- 										from imsappointment end -->
										
										
										
										
										
									</table>
									</div>
									<!-- 		333333333333333333333333333333  credit card  -->
									<div id="updateCreditDiv" style="display: none">
										<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
											<tr> 
				                   				<td colspan="4" class="table_title">Update Credit Card</td>
				            				</tr>
											<tr>
							       	</tr>
							        <tr>
							        	<td colspan="4">
							        	<table width="100%" cellspacing="0">
											<tr id="faxSerial" style="display:none;">
					            				<td width="5%">&nbsp;</td>
												<td align="left" style="width: 195px;">Fax serial No.</td>
												<td>
														<form:input maxlength="6" id="faxSerialNum"  path="faxSerialNum" />	
												</td>
											</tr>
											<c:choose><c:when test="${ims_direct_sales eq false}">
								        	<tr>
					            				<td width="5%">&nbsp;</td>
							      			  	<td align="left" style="width: 195px;">Third Party Credit Card:</td>
							      			  	<td>
													<form:radiobutton id="thirdparty" path="thirdPartyInd" label="Yes" value="Y" />
													<form:radiobutton id="self" path="thirdPartyInd" label="No" value="N"/> 
							        			</td>
							        		</tr>
							        		</c:when></c:choose>
							        		<tr>
					            				<td width="5%">&nbsp;</td>
							        			<td align="left" style="width: 195px;">Card Holder Name:</td>
							        			<td align="left">
							        					<form:input size="20%"	id="cardholdername" path="ccHolderName" onkeyup="keyUpOnCCName()" cssStyle="text-transform:uppercase;"/>
							        			</td>
												<td colspan="1" id="warning_cardholder_msg" style="display:none;">
													<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
													<div class="ui-state-highlight ui-corner-all"  style="font-weight:bold;">
													Card holder name is different from register name.
													<c:choose><c:when test="${ims_direct_sales eq false}">
													</br>If this is 3rd party credit card, please click "Yes".
													</c:when></c:choose>
													</div>
							        			</td>
							        		</tr>
							        		
							        		<tr>
					            				<td width="5%">&nbsp;</td>
							        			<td align="left" style="width: 195px;">Card Type</td>
							        			<td align="left">
							        				<form:select id="cardtype" path="ccType">
													<form:option value="" label="-select-">-select-</form:option>
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
					            				<td width="5%">&nbsp;</td>
							        			<td align="left" style="width: 195px;">Card Number</td>
							        			<td align="left">
        											<form:input id="cardnum" path="ccNum" size="30" maxlength="16" readonly="true" />
							        				<input type="hidden" name="token" id="token">
													<input type="hidden" name="ceksSubmit" value="N">
														<a href="###" class="nextbutton" onclick="javascript:openCEKSWindow()">Input Credit Card No.</a>
												</td>
												<td>
													
							        			</td>
							        		</tr>
							        		<tr>
							        		<% Calendar today = Calendar.getInstance();
											   int year = today.get(Calendar.YEAR);
											   int iMonth = today.get(Calendar.MONTH)+1;
											   if(iMonth+2>12){year=year+1;iMonth=iMonth-12;}
											%>
					            				<td width="5%">&nbsp;</td>
							        			<td align="left" style="width: 195px;">Expiry Date</td>
							        			<td align="left">
							        	    		<form:select id="ccExpiryMonth"  path="expiryMonth">
													<form:option value="00" label="-select-">-select-</form:option>
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
													
							        				<form:select  id="ccExpiryYear" path="expiryYear" onchange="expiryDateCtrl()">
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
							        			</td>
							        			<form:hidden path="ccExpDate"/>
							        			<form:errors path="ccExpDate" cssClass="error" />
							        		</tr>
											<tr><td width="5%">&nbsp;</td></tr>
										</table></td>
										</tr>
									</table>
			
									</div>
									
									
									<!-- 		login id  -->
									<div id="changeLoginIdDiv" style="display: none">
										<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
											<tr> 
				                   				<td colspan="4" class="table_title">Login ID</td>
				            				</tr>
											<tr>
							       	</tr>
									<tr>
					            	<td width="5%">&nbsp;</td>
									<td style="width: 300px;">
										<form:input maxlength="15" id="loginName" path="loginId" cssStyle="text-transform:lowercase;"/>@netvigator.com
										<input type="hidden" id="reservedId" />
									</td>
									<td align="left" colspan="3">
										<a href="#" class="nextbutton" onClick="loginNameCheck()">Reserve</a>
										<form:errors path="loginId" cssClass="error" />
									</td>
									</tr>		
									<tr>
					            	<td width="5%">&nbsp;</td>
									<td style="width: 500px;">
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
					            	<td width="5%">&nbsp;</td>
									<td style="width: 500px;">
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
									</table>
			
									</div>
									
									<!-- 		contact email  -->
									<div id="contactEmailDiv" style="display: none">
										<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
											<tr> 
				                   				<td colspan="4" class="table_title">Contact Email Address</td>
				            				</tr>
											<tr>
							       	</tr>
		
									<tr>
					            	<td width="5%">&nbsp;</td>
										<td>
											<form:input size="40" maxlength="40" id="contactEmailName" path="contactEmail" onblur="checkContactEmail()"/>	
<!-- 											<div > -->
											<span id="error_ContactEmailAddr" class="error" style="display:none"></span>
<!-- 											</div>			 -->
										</td>
										
										
									</tr>
									
<!-- 									<tr> -->
									
<!-- 										<td> -->
<!-- 												<div id="hktClubLoginName_error"  style="display:none">					 -->
<!-- 														<p>						 -->
<!-- 														<div class="error" id="hktClubLoginName_error_msg" ></div> -->
<!-- 														</p>					 -->
<!-- 												</div> -->
<!-- 										</td> -->
<!-- 									</tr> -->
									</table>
									<br></br>
									</div>
									
									
									
									
									
<!-- 								44444444444444444444444 F&S amend  -->
									<div id="fsAmendDiv" style="display: none;">
										<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
											<tbody>
											<tr> 
				                   				<td colspan="4" class="table_title">F&amp;S Amendment<br></td>
				            				</tr>
				            				<tr><td colspan="4">&nbsp;</td></tr> 
					            			<tr id = "programOfferChangeRow"> 
												<td width="10"><input id="programOfferChange" name="programOfferChangeInd" type="checkbox" value="true"/><input type="hidden" name="_programOfferChangeInd" value="on"/></td>
												<td width="20%">
												<c:choose>
												<c:when test='${amendOrderImsUI.allowTvContentChange == "N"}'>
													<b>Program Offer & VAS/Gift & nowTV Bundle</b>
												</c:when>
												<c:otherwise>	
													<b>TV content change</b>
												</c:otherwise>
												</c:choose>
												</td>
												<td colspan="4">
													<!-- progOfferChangeDiv -->
													<div id="progOfferChangeDiv" style="display:none;">
														<table width="100%" cellspacing="0" border="0" bgcolor="white">
															<tbody> 
								            				<tr>
									            				<td colspan="4">
																	<c:choose>
																	<c:when test='${amendOrderImsUI.allowTvContentChange == "N"}'>
									            					<a href="###" id="continue" class="nextbutton" onClick="javascript:openOfferChangeDialog();" >
																		<b>Change Offer Details</b>
									            					</a>		
																	</c:when>
																	<c:otherwise>	
									            					<a href="###" id="continue" class="nextbutton" onClick="javascript:openChildWindow();" >
																		<b>TV content change</b>
									            					</a>		
																	</c:otherwise>
																	</c:choose>
									            				</td>
									            			</tr>
															
									            			<tr><td width="5%">&nbsp;</td></tr>
									            			<tr> 
									            				<td colspan="4">
																	<form:textarea path="progOfferChangeRemark" rows="15" cols="100"/> 
											                   	</td>
									            			</tr>
									            			<tr><td colspan="4">&nbsp;</td></tr>
								            			</tbody></table>
													</div>
												</td>
											</tr>
				            				
				            				<tr>
					            				<td width="10"><input id="fs1" name="2" type="checkbox"></td>  
					            				<td colspan="2">
					            					<c:choose>
													<c:when test="${not empty amendNatures}">
													<form:select id="fsAmendNature1" path="amendNature1">
														<form:option value="" label="Select..."/>
														<form:options items="${amendNatures}" itemValue="wqNatureId" itemLabel="wqNatureDesc"/>
													</form:select>
													</c:when>					
													<c:otherwise>
														<select id="fsAmendNature1"  style="width:200px;" disabled> 
															<option selected="selected" value = "">Select...</option>
														</select>
													</c:otherwise>			
													</c:choose>
					            					
					            				</td>
					            				<td>
													<form:textarea id="fsAmendRemark1" path="amendRemark1" rows="5" cols="75" />
							                   	</td>
					            			</tr>
				            				<tr>
					            				<td width="10"><input id="fs2" name="2" type="checkbox" value="true"><input type="hidden" name="_2" value="on"></td>
					            				<td colspan="2">
					            					<c:choose>
													<c:when test="${not empty amendNatures}">
													<form:select id="fsAmendNature2" path="amendNature2">
														<form:option value="" label="Select..."/>
														<form:options items="${amendNatures}" itemValue="wqNatureId" itemLabel="wqNatureDesc"/>
													</form:select>
													</c:when>					
													<c:otherwise>
														<select id="fsAmendNature2"  style="width:200px;" disabled> 
															<option selected="selected" value = "">Select...</option>
														</select>
													</c:otherwise>			
													</c:choose>
					            					
					            				</td>
					            				<td>
													<form:textarea id="fsAmendRemark2" path="amendRemark2" rows="5" cols="75" />
							                   	</td>
					            			</tr>
				            				<tr>
					            				<td width="10"><input id="fs3" name="3" type="checkbox" value="true"><input type="hidden" name="_3" value="on"></td>
					            				<td colspan="2">
					            					<c:choose>
													<c:when test="${not empty amendNatures}">
													<form:select id="fsAmendNature3" path="amendNature3">
														<form:option value="" label="Select..."/>
														<form:options items="${amendNatures}" itemValue="wqNatureId" itemLabel="wqNatureDesc"/>
													</form:select>
													</c:when>					
													<c:otherwise>
														<select id="fsAmendNature3"  style="width:200px;" disabled> 
															<option selected="selected" value = "">Select...</option>
														</select>
													</c:otherwise>			
													</c:choose>
					            					
					            				</td>
					            				<td>
													<form:textarea id="fsAmendRemark3" path="amendRemark3" rows="5" cols="75" />
							                   	</td>
					            			</tr>
											
											<tr><td width="5%">&nbsp;</td></tr>
				            			</tbody></table>

									
									</div>
									
									
									<!-- 	555555555555555555  F&S order cancel -->
									<div id="fsOrderCancelDiv" style="display: none;">
										<table width="100%" cellspacing="0" border="0" bgcolor="white">
											<tbody>
											<tr><td colspan="4" class="table_title">F&S Order Cancellation</td></tr>
				            				<tr><td colspan="4">&nbsp;</td></tr>
					            			<tr>
					            				<td width="5%">&nbsp;</td>
					            				<td width="20%">Cancel Type<span class="contenttext_red">*</span> : </td>
					            				<td >	
												<form:select path="fsCancelType" cssStyle="width:200px">
													<option value="" selected="">select...</option>
													<option value="Cancel PCD only" selected="">Cancel PCD only</option>
													<option value="Cancel PCD + eye order" selected="">Cancel PCD + eye order</option>
												</form:select>
							                   	</td>
					            			</tr>
				            				<tr>
					            				<td width="5%">&nbsp;</td>
					            				<td width="20%">Cancel Reason<span class="contenttext_red">*</span>: </td>
					            				<td colspan="2">
													<c:choose>
													<c:when test="${not empty cancelReasons}">
													<form:select id="fsCancelReason" path="fsCancelReason">
														<form:option value="" label="Select..."/>
														<form:options items="${cancelReasons}" itemValue="delayReason" itemLabel="delayReason"/>
													</form:select>	
													<div style="display: none;">				
														<form:select id="fsCancelReasonCode" path="fsCancelReasonCode" >
															<form:option value="" label="Select..."/>
															<form:options items="${cancelReasons}" itemValue="delayReasonCode" itemLabel="delayReason"/>
														</form:select>
													</div>					
													</c:when>					
													<c:otherwise>
														<select id="fsCancelReason"  style="width:200px;" disabled> 
															<option selected="selected" value = "">Select...</option>
														</select>
													</c:otherwise>			
													</c:choose>					            					
					            				</td>
					            			</tr>
											
					            			<tr><td width="5%">&nbsp;</td></tr>
					            			<tr>
					            				<td width="5%">&nbsp;</td>
					            				<td width="20%">Cancel Remark<span class="contenttext_red">*</span> : </td>
					            				<td >
													<form:textarea id="fsCancelRemark" path="fsCancelRemark" rows="5" cols="100" />
							                   	</td>
					            			</tr>
					            			<tr><td colspan="4">&nbsp;</td></tr>
				            			</tbody>
				            		</table>
								</div>
									
									
									<!-- 	666666666666   F&S appointment -->
									<div id="fsAppntmentDiv" style="display: none;">
										<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
										<tbody>
										<tr id="appointment_header" style=""> 
											<c:choose>			
											<c:when test=  '${amendOrderImsUI.isDorder == "Y"}'>																	
												<td colspan="4" class="table_title">D order swap self pick and FS</td>
											</c:when>
											<c:otherwise>
												<c:if test='${CanAmendAppointment == "Y"}'>
			                   					<td colspan="4" class="table_title">F&S Appointment<br></td>
												</c:if>
												<c:if test='${CanAmendAppointment == "N"}'>
			                   					<td colspan="4" class="table_title">F&S SRD<br></td>
												</c:if>
											</c:otherwise>
											</c:choose>
				            			</tr>
										<tr><td width="5%">&nbsp;</td></tr>
																					
							        <tr>
							        	<td colspan="4">
							        	<table width="100%" cellspacing="0">
												<tr id="targetdatecols">
					            				<td width="5%">&nbsp;</td>
													<c:if test='${CanAmendAppointment == "Y"}'>
													<td width="25%">Target Installation Date<span class="contenttext_red">*</span>: </td>
													</c:if>
													<c:if test='${CanAmendAppointment == "N"}'>
													<td align="right" width="25%">SRD: </td>
													</c:if>
													<td align="left">				
														<input id="fsInstdate" value=""  readonly="readonly"/>                 
													</td>
													<form:hidden path="fsTargetInstallDate"/>
													<td>&nbsp;</td>
												</tr>
										<tr id="fsTimeslotcols">
					            			<td width="5%">&nbsp;</td>
											<td width="25%">Target Installation Time<span class="contenttext_red">*</span>: </td>
											<td align="left" style="padding-left: 3px;">				
												<form:select path="fsTimeSlot" cssStyle="width:100px">
													<option value="" selected="">-please select-</option>
													<option value="14:00-16:00" selected="">14:00-16:00</option>
													<option value="16:00-18:00" selected="">16:00-18:00</option>
													<option value="18:00-20:00" selected="">18:00-20:00</option>
													<option value="20:00-22:00" selected="">20:00-22:00</option>
													<option value="09:00-13:00" selected="">09:00-13:00</option>
													<option value="14:00-18:00" selected="">14:00-18:00</option>
													<option value="09:00-18:00" selected="">09:00-18:00</option>
												</form:select>
												<form:hidden path="fsTimeSlotDisplay"/>									
												<div style="display: none;">				
													<form:select id="slotType" path="">				
														<form:option value=""/>
														<form:options items="${timeSlots}" itemValue="slotType" itemLabel="timeSlot"/>
													</form:select>
												</div>												
											</td>			
											<td>&nbsp;</td>
										</tr>										
											
											
				            				<tr id="fsDelayReason">
					            				<td width="5%">&nbsp;</td>
					            				<td width="25%">Delay Reason<span class="contenttext_red">*</span>: </td>
					            				<td colspan="2">
													<c:choose>
													<c:when test="${not empty delayReasons}">
													<form:select id="fsDelayReasonCode" path="fsDelayReasonCode">
														<form:option value="" label="Select..."/>
														<form:options items="${delayReasons}" itemValue="delayReasonCode" itemLabel="delayReason"/>
													</form:select>							
													<div style="display: none;">				
														<form:select id="fsDelayReason" path="fsDelayReason" >
															<form:option value="" label="Select..."/>
															<form:options items="${delayReasons}" itemValue="delayReason" itemLabel="delayReason"/>
														</form:select>
													</div>							
													</c:when>					
													<c:otherwise>
														<select id="fsDelayReason"  style="width:200px;" disabled> 
															<option selected="selected" value = "">Select...</option>
														</select>
													</c:otherwise>			
													</c:choose>					            					
					            				</td>
					            			</tr>
											<tr id="fsForceerial">
					            				<td width="5%">&nbsp;</td>
												<td align="left">UAMS / Force Serial:</td>
												<td>
														<form:input maxlength="8" id="fsPreBkSerialNum"  path="fsPreBkSerialNum" />	
												</td>
											</tr>
					            			
											<tr id="fsBomRemarks">
					            				<td width="5%">&nbsp;</td>
												<td valign="top" align="left">BMO Remarks:</td>
												<td colspan="2">
													<input readonly="readonly" style="width: 200px; height: 48px;"
													value="${amendOrderImsUI.orderImsUI.appointment.bmoRmk}"> 
													</input>
												</td>
											</tr>
											
											<tr id="fsAdditionalRemarks">
												<td width="5%">&nbsp;</td>
												<c:if test='${CanAmendAppointment == "Y"}'>
													<td valign="top" align="left">Additional Remarks:</td>
												</c:if>
												<c:if test='${CanAmendAppointment == "N"}'>
													<td valign="top" align="right">Additional Remarks:</td>
												</c:if>
												<td colspan="2">
													<form:textarea id="fsAddtionalRemarks" path="fsAddtionalRemarks"  rows="3" cols="45" />
												</td>
											</tr>
											
										
										<c:if  test='${IsPreInstOrder == "Y"}'>	
<!-- 										<tr id="fsCommDateCols"> -->
<!-- 										<td width="5%">&nbsp;</td> -->
<!-- 										<td width="25"><input id="preInstCommDateInd" name="preInstCommDateInd" type="checkbox" value="true"><input type="hidden" name="_preInstCommDateInd" value="on"> -->
<!-- 										Change Target Commencement Date</td> -->
<!-- 										<td align="left"></td> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 										</tr>			 -->
<!-- 										<tr id="existCommDate"> -->
<!-- 					            				<td width="5%">&nbsp;</td> -->
<!-- 								        	<td align="left" style="width:300px;">Existing Commencement Date:</td> -->
<%-- 											<td align="left" ><input readonly="readonly" value="${amendOrderImsUI.orderImsUI.targetCommDate}">  --%>
<!-- 											</input></td> -->
<!-- 										</tr> -->
										<tr id="fscommcols2">
										<td width="5%">&nbsp;</td>
										<td width="25%">
											Target Commencement Date: <br/>(For Pre-installation Only)
										</td>
										<td align="left">				
											<input id="fsCommDate" value=""  readonly="readonly"/>                 
										</td>
										<form:hidden path="fsTargetCommDate"/>
										<td>&nbsp;</td>
										</tr>							
										</c:if>
											
											<tr><td width="5%">&nbsp;</td></tr>
											
										</table></td>
										</tr>		
									</tbody></table>
									</div>
									
									
<!-- 								9999999  d order self pick -->
									<%@ include file="/WEB-INF/jsp/imsdorderselfpick.jsp" %>
									
									
<!-- 									7777777 submit button  -->
									<div id="submitDiv" style="display: none;">
										<table width="100%" cellspacing="0" border="0" bgcolor="white">
											<tbody>
				            				<tr>
				                   				<td align="left" >
				                   				<a href="###" id="continue" class="nextbutton" onClick="preSubmit();" >Submit</a> 
				                   				</td>
				                   			
				                   				<c:if  test='${not empty amendOrderImsUI.allowAmendStateAppear}'>	
												<td>
														<div class="ui-widget">
															<div class="ui-state-highlight ui-corner-all" style="padding: 0.1em;"> 
																<p>
																	<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
																	<div class="contenttext" style="color:red; font-weight:bold;">
																		${amendOrderImsUI.allowAmendStateAppear}
																	</div>
																</p>
															</div>
														</div>
							        			</td>
							        			</c:if>
							        			<td>
							        				<div id="nowtv_isIncludeGoodsDelivery_msg" style="display:none;">
						        						Amendment is not allowed as NowTV pack is not allowed for Add/Remove.
						        					</div>
							        			</td>
				            				</tr>
											<tr><td width="5%">&nbsp;</td></tr>
				            				</tbody>
				            			</table>
									</div>
<!-- 		Celia  -  Amendment Notification Distribution Mode								 -->
			<c:if  test='${amendOrderImsUI.orderImsUI.isCC=="Y" or amendOrderImsUI.orderImsUI.isPT =="Y"}'>	
					
					<div id="email_alert_dialog" title="System Alert" style="display: none;"> 
						<p class="emailAlertTips" style="padding-left:5px;" >Are you sure sending notification to customer? </p>
						
<!-- 						<div align="left", style="margin-left:150px;width:150px;padding-top:10px;"> 	 -->
<!-- 								<input id = "SendComfirmY" type="radio" name="_sendInd" value="Y" checked="checked" /> Yes -->
<!-- 								<input id = "SendComfirmN" type="radio" name="_sendInd" value="N" /> No						 -->
<!-- 						</div> -->
						<div class="pop_content">
          					<div class="pop_button" style="padding: 30px 0px 0px 240px;">
          						<a href="###" id="send_alert_Confirm" class="nextbutton" onclick="NotificationSendConfirm()">Yes</a>
          						<a href="###" id="send_alert_Cancel" class="nextbutton"  onclick="NotificationSendCancel()">No</a>
          					</div>
						</div>
					</div>			
			</c:if>			
<!-- 									8888888888 errorr area   -->
									<div id="errorDiv">
										<table width="100%" cellspacing="0" border="0" bgcolor="white">
											<tbody>
				            				<tr>
				                   				<td align="left" >
        										<form:errors path="commonErrorArea" cssClass="error" />
				                   				</td>
				            				</tr>
				            				</tbody>
				            			</table>
									</div>
									
									
									
																			
								
            			</tbody></table>
					</td>
				</tr>								
			</tbody></table>
		</td>
	</tr>
</tbody></table>
<table width="100%" class="tablegrey" id="historytable"  style="display: none;">
	<tbody><tr>
		<td>
		<table width="100%" border="0" cellspacing="1" rules="none">
		<tbody><tr>
			<td class="table_title">Amendment History</td>
		</tr>
		</tbody></table>
		</td>
	</tr>
	<tr>
		<td id="historycell" bgcolor="#FFFFFF" height="380" valign="top">
			
			
			
		</td>
	</tr>
</tbody></table>

<form:hidden path="isCollectRequestClicked" id="isCollectRequestClicked"/>
<form:hidden path="isVisitWithChargeClicked" id="isVisitWithChargeClicked"/>
<form:hidden path="isVisitWithoutChargeClicked" id="isVisitWithoutChargeClicked"/>
<input type="hidden" id="nowtv_isIncludeGoodsDelivery" />

</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%-- <%@ include file="/WEB-INF/jsp/footer.jsp"%> --%>