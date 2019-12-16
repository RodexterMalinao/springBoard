<%@ include file="header.jsp" %>
<%@ include file="imsloadingpanel.jsp" %>
<%-- <%@ include file="imsvalidwindow.jsp" %> --%>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%
Map<String, List<CodeLkupDTO>> codeLkupList = LookupTableBean.getInstance().getCodeLkupList();
List<CodeLkupDTO> dtos = codeLkupList.get("IPAD_VERSION");
String ipadVersion = "";
if (dtos != null && dtos.size() > 0) {
	ipadVersion = dtos.get(0).getCodeId();
}

//debug ipad version
//ipadVersion = "2.3";

String scheme = ConfigProperties.getPropertyByEnvironment("orddoc.ipad.uri.scheme");
%>


<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery-ui-1.8.16.custom.min.js"></script>

<style type="text/css">
.label { display: inline-block; font-weight: bold; width: 120px }
.row { height: 24px }
.input { display: inline-block }
.input label { display: inline-block; width: 50px }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.wrapContent { display: inline-block; width: 100%; padding: 10px 0 }
.previewForm .content { margin-left: 2em }
.previewForm .half { width: 40%; float: left }
.header tr { background-color: #E8F2FE }
.even { background-color: #E8FFE8 }
.success { color: green }
.fail { color: red }
h2 { margin: 0 }
.histList { width: 100%; clear: both }
.histList td { text-align: center } 
.ui-dialog-titlebar-close { float: right } 
</style>


<script language="Javascript">
//imsdirectsales
var isDS = ${ims_direct_sales == true};

/************ Detect client platform *********************/ 
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
/************ testing *********************/

	function EmailComfirmY(){
		document.getElementById("EmailComfirm1").checked = true;
        document.getElementById("EmailComfirm2").checked = false;
        $("#withoutEmailComfirmInd").val("Y");
	}

	function EmailComfirmN(){
		document.getElementById("EmailComfirm1").checked = false;
        document.getElementById("EmailComfirm2").checked = true;
        $("#withoutEmailComfirmInd").val("N");
	}
	
	
	function paperModeY(){
		document.getElementById("paperMode1").checked = true;
        document.getElementById("paperMode2").checked = false;  
        $("#paperModeConfirmInd").val("Y");
	}

	function paperModeN(){
		document.getElementById("paperMode1").checked = false;
        document.getElementById("paperMode2").checked = true;
        $("#paperModeConfirmInd").val("N");
	}
	
	

      function disModeDefaultValue(){           
		var operatingSys = BrowserDetect.OS;
				<c:choose>
					<c:when test='${imsSummaryUI.disMode == "P"}'>
						$("[name='disMode'][value='E']").attr("checked",false);
						$("[name='disMode'][value='P']").attr("checked",true);
				        DisModePaper();
					</c:when>
					<c:when test='${imsSummaryUI.disMode == "E"}'>
						$("[name='disMode'][value='E']").attr("checked",true);
						$("[name='disMode'][value='P']").attr("checked",false);
			        	DisModeEsign();
					</c:when>
					<c:when test='${imsSummaryUI.disMode == null}'>
						$("[name='disMode'][value='E']").attr("checked",true);
						$("[name='disMode'][value='P']").attr("checked",false);
			        	DisModeEsign();
					</c:when>
				</c:choose>
      }


	
	function formSubmit(){
		checkIfNoSupportingDoc();
		
		if(document.getElementById("warning_pregen").style.display == ""){
			
		}else{
/*		if($("[id=account.payment.payMtdType]").val() == "M" && ($("#primaryAcctNo").val() == "" || $("#primaryAcctNo").val() == null)){
			alert("Pregen account is not inputted.");
		}else if($("#langPreference").val() == null || $("#langPreference").val() == ""){
				alert("Please print AF form before sign-off.");
		}else{
*/
			submitShowLoading(); 
			document.summaryForm.submit();
		}
//		}
	}
	
// 	Email confirmation by Eric Ng on 2012-11-16
	function signoff(){
	
		if ($('input[name=disMode]:checked').val()== "P") { //Avoid confirmation if dismode is Paper
			
			if($("#printFormClickInd").val() == ""){
				document.getElementById('warning_printFormClickInd').style.visibility = "visible";
				return;
			}
				
			formSubmit();
			
		}else if ( $('input[name=disMode]:checked').val()== "E"){
			
		
			
			if($("#printPreviewFormSignedInd").val() == ""){
				document.getElementById('warning_printPreviewFormSignedInd').style.visibility = "visible";
				return;
			}
			
			if($("#clickDigitalSignButtonInd").val() == ""){
				document.getElementById('warning_clickDigitalSignButtonInd').style.visibility = "visible";
				return;
			}
			
// 			if($("#checkDigSignatureInd").val() == "" || $("#checkDigSignatureInd").val() == "N"){
				checkDigitalSignature();
// 			}
			
			if($("#checkDigSignatureInd").val() != "Y"){
				document.getElementById('warning_emptyDigitalSignature').style.visibility = "visible";
				$("#checkDigSignatureInd").val("N");
				return;
			}
			
			if( $('input[name=esigEmailAddr]').val() == "" && !isDS){ 
// 				EModeWithoutEmailDialog();
				formSubmit();
				
			}else
			{
				var emailaddress = document.getElementById('esigEmailAddr').value;
				if(emailaddress != null && emailaddress!= "") {
					var answer = confirm('<spring:message code="ims.pcd.summary.msg.002"/>' + emailaddress +'\n'+'<spring:message code="ims.pcd.summary.msg.002a"/>');
					if (answer){
	 					if($('input[name=SMSno]').val() != ""){
							var smsno = document.getElementById('SMSno').value;
							var answer2 = confirm('<spring:message code="ims.pcd.summary.msg.003"/>' + smsno +'\n'+ '<spring:message code="ims.pcd.summary.msg.003a"/>');
							if (answer2){
			 					
								var contactEmail = "${ImsOrder.customer.contact.emailAddr}"; 
			 					var contactMoblie = "${ImsOrder.customer.contact.contactPhone}"; 
			 					
			 					if(contactEmail!=emailaddress||contactMoblie!=smsno){
			 						alert("<spring:message code="ims.pcd.summary.msg.023"/>");
			 						formSubmit();
			 					}else{
			 						formSubmit();
			 					}
							}
	 					}
					}
				} else formSubmit();
			}
					
			
		}else if ( "${imsSummaryUI.disMode}" == "I" ){
			var contactEmail = "${ImsOrder.customer.contact.emailAddr}"; 
			var contactMoblie = "${ImsOrder.customer.contact.contactPhone}"; 
			var emailaddress = document.getElementById('emailAddr').value;
			var smsno = document.getElementById('SMSno').value;
				
			if(contactEmail!=emailaddress||contactMoblie!=smsno){
				alert("<spring:message code="ims.pcd.summary.msg.023"/>");
				formSubmit();
			}else{
				formSubmit();
			}
		}else {
	
			formSubmit();
		}
		
	}
	
	
	
	function cancel(){
		var cancel = document.getElementById('cancelSelect').value;
		if (cancel == ""){
// 			alert("Please select a cancel reason code!");
			alert('<spring:message code="ims.pcd.summary.msg.004"/>');
		}else{
			submitShowLoading();
			$("#submitTag").val("C");
			document.summaryForm.submit();
		}
	}
	
	//function dummyprintEng(){
		//alert($("#pregenAcc").val());
		//if($("[id=account.payment.payMtdType]").val() == "M" && ($("#pregenAcc").val() != "" || $("#pregenAcc").val() != null)){
		//	alert("Pregen account is not inputted.");
		//}else{
			//$("#langPreference").val("ENG");
			//alert("Your form is printed.");
		//}
	//}
	
	//function dummyprintChi(){
		//if($("[id=account.payment.payMtdType]").val() == "M" && ($("#pregenAcc").val() != "" || $("#pregenAcc").val() != null)){
		//	alert("Pregen account is not inputted.");
		//}else{
			//$("#langPreference").val("CHI");
			//alert("Your form is printed.");
		//}
	//} 
	

	//Tony
	function checkDigitalSignature(){

		$.ajax({
			url : 'imscheckdigitalsignature.html?dt=' + new Date().getTime(),
			async: false,
			cache: false,
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			
				if(textStatus=='parsererror'){
// 			        alert("Your session has been timed out, please re-login."); 
					alert('<spring:message code="ims.pcd.summary.msg.011"/>');
			    }else if(textStatus == 'timeout'){
// 			    	alert("Server response timeout, please try again.");
					alert('<spring:message code="ims.pcd.summary.msg.011"/>');
			    }else{
			    	alert('error: '+textStatus);
			    }
				
				document.getElementById('warning_login').style.visibility = "hidden";
				document.getElementById('error_login').style.visibility = "hidden";
			},	
			success : function(msg) {
				
				$.each(eval(msg), function(i, item) {
					if(item.signatureInd == "Y"){
						//alert('item.signatureInd'+item.signatureInd);
						//alert('digital signature signed!!');	
						$("#checkDigSignatureInd").val("Y");
					
					}else{
						//alert('item.signatureInd'+item.signatureInd);
						//alert('digital signature not signed!!');	
						$("#checkDigSignatureInd").val("N");
					}
				});
			}
		});
	}
	
	function dummyprintLangPreviewForm(){
		$("#printFormClickInd").val("p");
		if(isDS) $("#printPreviewFormSignedInd").val("P");	//jacky 20141202
		document.getElementById('warning_printFormClickInd').style.visibility = "hidden";
		if (BrowserDetect.OS == "iPad" || BrowserDetect.OS == "iPhone/iPod" || BrowserDetect.OS == "Linux") 
			window.open("imsreport.html?pdfLang="+$("#langPreference").val()+"&justPreview=true&disMode=" + $('input[name=disMode]:checked').val(), '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
		else
			window.location.replace("imsreport.html?pdfLang=" + $("#langPreference").val()+"&justPreview=true&disMode=" + $('input[name=disMode]:checked').val(), '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
		}

	function dummyprintLangPreviewFormSigned(){
		if ($("#checkDigSignatureInd").val() != "Y"){
			checkDigitalSignature();
			if ($("#checkDigSignatureInd").val() != "Y"){
				$("#checkDigSignatureInd").val("Z");
			}
		}
		$("#printFormClickInd").val("p");
		$("#printPreviewFormSignedInd").val("P");
		document.getElementById('warning_printPreviewFormSignedInd').style.visibility = "hidden";
		if (BrowserDetect.OS == "iPad" || BrowserDetect.OS == "iPhone/iPod" || BrowserDetect.OS == "Linux") 
			window.open("imsreport.html?pdfLang="+ $("#langPreference").val()+"&disMode=" + $('input[name=disMode]:checked').val() +"&SignedOrNot=true");
		else
			window.location.replace("imsreport.html?pdfLang="+ $("#langPreference").val()+"&disMode=" + $('input[name=disMode]:checked').val() +"&SignedOrNot=true");
		}
	
	
	function digitalSignButtonCheck(){
		
		document.getElementById('warning_emptyDigitalSignature').style.visibility = "hidden";
		$("#checkDigSignatureInd").val("N");
		
		if($("#printPreviewFormSignedInd").val() == "P"){
			$("#clickDigitalSignButtonInd").val("P");
// 			$("#printPreviewFormSignedInd").val("");
			//alert($("#clickDigitalSignButtonInd").val());
			//alert($("#printPreviewFormSignedInd").val());
			document.getElementById('warning_clickDigitalSignButtonInd').style.visibility = "hidden";
			var esigEmailAddr = document.getElementById('esigEmailAddr').value;
			var disMode = $('input[name=disMode]:checked').val();
			var langPreference = $("#langPreference").val();
			
			
//	 		window.open('imscustomersignpcdtdo.html?disMode=' + disMode 
//	 				+ '&esigEmailAddr=' + esigEmailAddr
//	 				+ '&langPreference=' + langPreference
//	 				, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			if (isDS){
				var SMSno =  document.getElementById('SMSno').value;
				window.open('imscustomersign.html?disMode=' + disMode 
						+ '&esigEmailAddr=' + esigEmailAddr + '&SMSno=' + SMSno
						+ '&langPreference=' + langPreference
						+ '&dM=Y'
						, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			}else
			window.open('imscustomersign.html?disMode=' + disMode 
					+ '&esigEmailAddr=' + esigEmailAddr
					+ '&langPreference=' + langPreference
					+ '&dM=Y'
					, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
		}else{
			document.getElementById('warning_printPreviewFormSignedInd').style.visibility = "visible";
		}
	}
	
	function captureIpadButtonCheck(){
		$("#clickCaptureIpadButtonInd").val("P");
	}
	
	function CapturePC(){
		var url = "imsdoccapture.html?orderId=${imsSummaryUI.orderId}&disMode=" + $('input[name=disMode]:checked').val();
		window.open(url, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
	}	
	 
	function CaptureiPad(){
		var link = "<%=scheme %>://${imsSummaryUI.orderId}/${salesUserDto.username}/${currentSessionId}/<%=ipadVersion%>";
		var url = "imsdoccapture.html?orderId=${imsSummaryUI.orderId}&disMode=" + $('input[name=disMode]:checked').val()+"&iPadLink="+link;
		window.open(url);
	}
	
	
	function setFocus(){
        window.tempEl = document.getElementById('pregenAcc');
        setTimeout("window.tempEl.focus();",1);	     
	}
	
	function DisModeEsign(){//steven modified for kinman
		document.getElementById("esigEmailAddr").disabled = "";
		document.getElementById("SMSno").disabled = "";
        document.getElementById("previewFormsigned").style.display="";
        if(!isDS) document.getElementById("captureIPad").style.display="";
          <c:forEach items="${imsSummaryUI.allOrdDocAssgnDTOs}" var="dto" varStatus="status">
			<c:choose>
				<c:when test="${not empty waiveReasonsDigital[dto.docType]}">	
					document.getElementById("${dto.docName}").disabled = false;  
					document.getElementById("${dto.docName}").selectedIndex = 0;
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${not empty waiveReasonsPaper[dto.docType]}">
		  			document.getElementById("${dto.docType}").selectedIndex = 0;	
		  			document.getElementById("${dto.docType}").disabled = true;
				</c:when>
			</c:choose>
		  </c:forEach>
		document.getElementById("paperSupDoc").style.display="none";
		document.getElementById("digitalSupDoc").style.display="";
	  		document.getElementById("digitalSignatureButton").style.display="";
	}

	function DisModePaper(){//steven modified for kinman
		document.getElementById("esigEmailAddr").disabled = "disabled";
		document.getElementById("SMSno").disabled = "disabled";
// 		document.getElementById("esigEmailAddr").value = "";
		document.getElementById("previewFormsigned").style.display="none";
		document.getElementById("captureIPad").style.display="none";
		document.getElementById("digitalSignatureButton").style.display="none";
		<c:forEach items="${imsSummaryUI.allOrdDocAssgnDTOs}" var="dto" varStatus="status">
			<c:choose>
				<c:when test="${not empty waiveReasonsDigital[dto.docType]}">  		
					document.getElementById("${dto.docName}").selectedIndex = 0;  	
  					document.getElementById("${dto.docName}").disabled = true;
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${not empty waiveReasonsPaper[dto.docType]}">		
	  				document.getElementById("${dto.docType}").disabled = false;  	
		  			document.getElementById("${dto.docType}").selectedIndex = 1;  
				</c:when>
			</c:choose>
		</c:forEach>
		document.getElementById("paperSupDoc").style.display="";
		document.getElementById("digitalSupDoc").style.display="none";
	}//steven modified for kinman end
	
	function DisModeSMS(){
		document.getElementById("SMSno").disabled = "";
		document.getElementById("emailAddr").disabled = "disabled";
		document.getElementById("emailAddr").value = "";
		document.getElementById("previewFormsigned").style.display="";
		document.getElementById("captureIPad").style.display="none";
		document.getElementById("digitalSignatureButton").style.display="none";
	}
	
	function DisModeEmail(){
		document.getElementById("SMSno").disabled = "disabled";
		document.getElementById("emailAddr").disabled = "";
		document.getElementById("SMSno").value = "";
		document.getElementById("previewFormsigned").style.display="";
		document.getElementById("captureIPad").style.display="none";
		document.getElementById("digitalSignatureButton").style.display="none";
	}
	
	function DisModePostal(){
		document.getElementById("SMSno").disabled = "disabled";
		document.getElementById("SMSno").value = "";
		document.getElementById("emailAddr").disabled = "disabled";
		document.getElementById("emailAddr").value = "";
		document.getElementById("previewFormsigned").style.display="none";
		document.getElementById("captureIPad").style.display="none";
		document.getElementById("digitalSignatureButton").style.display="none";
	}
	
	function DisModeM(){ 
		$("#printFormClickInd").val("");
		$("#printPreviewFormSignedInd").val("");
		$("#clickDigitalSignButtonInd").val("");
		$("#clickCaptureIpadButtonInd").val("");
		document.getElementById('warning_printFormClickInd').style.visibility = "hidden";
		document.getElementById('warning_printPreviewFormSignedInd').style.visibility = "hidden";
		document.getElementById('warning_clickDigitalSignButtonInd').style.visibility = "hidden";
		
		DisModePostal();
	}
	
	function DisModeI(){ 
		$("#printFormClickInd").val("");
		$("#printPreviewFormSignedInd").val("");
		$("#clickDigitalSignButtonInd").val("");
		$("#clickCaptureIpadButtonInd").val("");
		document.getElementById('warning_printFormClickInd').style.visibility = "hidden";
		document.getElementById('warning_printPreviewFormSignedInd').style.visibility = "hidden";
		document.getElementById('warning_clickDigitalSignButtonInd').style.visibility = "hidden";
		
		DisModeEmail();
	}
	
	function DisModeS(){
		$("[name='disMode'][value='S']").attr("checked",true);
		$("#printFormClickInd").val("");
		$("#printPreviewFormSignedInd").val("");
		$("#clickDigitalSignButtonInd").val("");
		$("#clickCaptureIpadButtonInd").val("");
		document.getElementById('warning_printFormClickInd').style.visibility = "hidden";
		document.getElementById('warning_printPreviewFormSignedInd').style.visibility = "hidden";
		document.getElementById('warning_clickDigitalSignButtonInd').style.visibility = "hidden";
		
		DisModeSMS();
	}
	
	function DisModeE(){ 
		$("#printFormClickInd").val("");
		$("#printPreviewFormSignedInd").val("");
		$("#clickDigitalSignButtonInd").val("");
		$("#clickCaptureIpadButtonInd").val("");
		document.getElementById('warning_printFormClickInd').style.visibility = "hidden";
		document.getElementById('warning_printPreviewFormSignedInd').style.visibility = "hidden";
		document.getElementById('warning_clickDigitalSignButtonInd').style.visibility = "hidden";
		
        DisModeEsign();
        $("#selectedDisModeInd").val("E");
	}
	
	function DisModeP(){
		$("#printFormClickInd").val("");
		$("#printPreviewFormSignedInd").val("");
		$("#clickDigitalSignButtonInd").val("");
		$("#clickCaptureIpadButtonInd").val("");
		document.getElementById('warning_printFormClickInd').style.visibility = "hidden";
		document.getElementById('warning_printPreviewFormSignedInd').style.visibility = "hidden";
		document.getElementById('warning_clickDigitalSignButtonInd').style.visibility = "hidden";
		
		DisModePaper();
	}
	
	//steven 
	function checkIfNoSupportingDoc(){
		//alert($('input[name=disMode]:checked').val());
		//alert("${imsSummaryUI.account.payment.payMtdType}");
		if(($('input[name=disMode]:checked').val()=="E") && (("${imsSummaryUI.account.payment.payMtdType}" == "C")||("${imsSummaryUI.cashFsPrepay}" == "Y")||("${imsSummaryUI.waivedPrepay}" == "Y"))){
			$("#noSupportingDoc").val("true");
		}else{
			$("#noSupportingDoc").val("false");
		}
		//alert($("#noSupportingDoc").val());
	}

	
	//kinman
	function SelectPaperModeDialog()
	{		
		
		
        document.getElementById("paperMode2").checked = true;
        document.getElementById("paperMode1").checked = false;
        $("#paperModeConfirmInd").val("N");
    
        
		var box= "#paperMode_alert_dialog";
	
		//alert($('input[name=selectedDisModeInd]').val());
		
		if (($('input[name=selectedDisModeInd]').val()=="P")){
		return;
		}else{
			$(box).dialog({
				width: 400,
				resizable: false,
				draggable: false,
				modal: true
				
				//,
				//open: function(event, ui) { 
				    //hide close button.
				  //  $(this).parent().children().children('.ui-dialog-titlebar-close').hide();
				//}
		
			})	;
			
		
		}
	}
	
	
	function paperModeConfirm(){
		
			if ($("#paperModeConfirmInd").val() == "Y"){
				$("[name='disMode'][value='E']").attr("checked", false);
				$("[name='disMode'][value='P']").attr("checked", true);
				DisModeP();
				 $("#selectedDisModeInd").val("P");			       
				$('#paperMode_alert_dialog').dialog('close');	}
			else {
				$("[name='disMode'][value='P']").attr("checked", false);
				$("[name='disMode'][value='E']").attr("checked", true);
		        DisModeE();
				$('#paperMode_alert_dialog').dialog('close');	
				};								
	}
	
	function paperModeCancel(){
		$("[name='disMode'][value='P']").attr("checked", false);
		$("[name='disMode'][value='E']").attr("checked", true);
        DisModeE();
		$('#paperMode_alert_dialog').dialog('close');
	}		
	
	//kinman
	function EModeWithoutEmailDialog()
	{		
		var box= "#email_alert_dialog";
		
		document.getElementById("EmailComfirm2").checked = true;
        document.getElementById("EmailComfirm1").checked = false;
        $("#withoutEmailComfirmInd").val("N");
       
		if(($('input[name=disMode]:checked').val()=="E") && $('input[name=esigEmailAddr]').val() == ""){
			
		
		$(box).dialog({
			width: 400,
			resizable: false,
			draggable: false,
			modal: true	
		
		});
		
		}
	}
	
	function withoutEmailConfirm(){
		
		if ($("#withoutEmailComfirmInd").val() == "Y"){
			$('#email_alert_dialog').dialog('close');
			formSubmit();
			}
		else {
			$('#email_alert_dialog').dialog('close');	
			};								
	}

	function withoutEmailCancel(){
		$('#email_alert_dialog').dialog('close');
	}		
	
	
	function pregenAcct(){
		$.ajax({
			url : 'validateaccountnum.html?pregenAcc=' + $("#pregenAcc").val(),
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
// 			        alert("Your session has been timed out, please re-login."); 
					alert('<spring:message code="ims.pcd.summary.msg.011"/>');
			    }else if(textStatus == 'timeout'){
// 			    	alert("Server response timeout, please try again.");
					alert('<spring:message code="ims.pcd.summary.msg.012"/>');
			    }
			},
			success : function(msg) {
				var count=0;
				$.each(eval(msg), function(i, item) {
					if(item.result != 0){
						//document.getElementById("submit").innerHTML = "<a href='#' class='nextbutton' onclick='dummyprintEng()'>Print S&S form (Eng)</a>" +
						//"<a href='#' class='nextbutton' onclick='dummyprintChi()'>Print S&S form (Chi)</a>" +
						//"<a href='#' class='nextbutton' onClick='formSubmit()'>Signoff</a>";
						document.getElementById('warning_pregen').style.display = "";
						document.getElementById('warning_pregen_msg').innerHTML = item.errorText;
						//alert(item.errorText);
						//setFocus();
					}else if(item.result == 0){
						document.getElementById('warning_pregen').style.display = "none";
						document.getElementById('warning_pregen_msg').innerHTML = "";
						//document.getElementById("submit").innerHTML = "<a href='imsreport.html?pdfLang=en' class='nextbutton' onclick='dummyprintEng()'>Print S&S form (Eng)</a>" +
						//"<a href='#' class='nextbutton' onclick='dummyprintChi()'>Print S&S form (Chi)</a>" +
						//"<a href='#' class='nextbutton' onClick='formSubmit()'>Signoff</a>";
					}
					count++;
				});
				if(count==0){ //&& idDocNum!=null){
					document.getElementById('warning_pregen').style.display = "none";
					document.getElementById('warning_pregen_msg').innerHTML = "";
				}
			}
		});
	}
		
	function refreshEDF(edfref){
		document.getElementById('edfref').innerHTML = edfref;
	}
		
	function refreshFS2(){

		$.ajax({
			url : 'imsrefreshfs.html?type=FS',
			//async: false,
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('error: '+textStatus);	
			
				if(textStatus=='parsererror'){
// 			        alert("Your session has been timed out, please re-login."); 
					alert('<spring:message code="ims.pcd.summary.msg.011"/>');
			    }else if(textStatus == 'timeout'){
// 			    	alert("Server response timeout, please try again.");
					alert('<spring:message code="ims.pcd.summary.msg.012"/>');
			    }
				document.getElementById('warning_login').style.visibility = "hidden";
				document.getElementById('error_login').style.visibility = "hidden";
			},	
			success : function(msg) {
				//alert('success');
				$.each(eval(msg), function(i, item) {
					var size = item.listSize;
					for (var i=0;i<size;i++){
						if(item.i==i){
							if(item.fsfsnum=="null"){
								item.fsfsnum = "";
							}
							document.getElementById('fsdoctype'+i).innerHTML = item.fsdoctype;
							document.getElementById('fswaivereason'+i).innerHTML = item.fswaivereason;
							document.getElementById('fscolind'+i).innerHTML = item.fscolind;
							document.getElementById('fsfsnum'+i).innerHTML = item.fsfsnum;
						}
					}
				});
			}
		});
	}
		
	function refreshEDF(){

		$.ajax({
			url : 'imsrefreshfs.html?type=edf',
			//async: false,
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('error: '+textStatus);	
			
				if(textStatus=='parsererror'){
// 			        alert("Your session has been timed out, please re-login."); 
			    	alert('<spring:message code="ims.pcd.summary.msg.011"/>');
			    }else if(textStatus == 'timeout'){
// 			    	alert("Server response timeout, please try again.");
			    	alert('<spring:message code="ims.pcd.summary.msg.012"/>');
			    }
				document.getElementById('warning_login').style.visibility = "hidden";
				document.getElementById('error_login').style.visibility = "hidden";
			},	
			success : function(msg) {
				//alert('success');
				$.each(eval(msg), function(i, item) {
							if(item.edf=="null"){
								item.edf = "";
							}
							document.getElementById('edfref').innerHTML = item.edf;
				});
			},
		    complete: function() {
		        //Schedule the next request when the current one's complete
		    	setTimeout(refreshEDF2, 500);
		    }
		});
	}
		
	function refreshEDF2(){

		$.ajax({
			url : 'imsrefreshfs.html?type=edf',
			//async: false,
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('error: '+textStatus);	
			
				if(textStatus=='parsererror'){
// 			        alert("Your session has been timed out, please re-login."); 
					alert('<spring:message code="ims.pcd.summary.msg.011"/>');
			    }else if(textStatus == 'timeout'){
// 			    	alert("Server response timeout, please try again.");
					alert('<spring:message code="ims.pcd.summary.msg.012"/>');
			    }
				document.getElementById('warning_login').style.visibility = "hidden";
				document.getElementById('error_login').style.visibility = "hidden";
			},	
			success : function(msg) {
				//alert('success');
				$.each(eval(msg), function(i, item) {
							if(item.edf=="null"){
								item.edf = "";
							}
							
							document.getElementById('edfref').innerHTML = item.edf;
				});
			},
		    complete: function() {
		        //Schedule the next request when the current one's complete
		        //setTimeout(refreshFS2, 500);
		    }
		});
	}

	$(document).ready(function(){
		BrowserDetect.init();
		disModeDefaultValue();
		
		document.getElementById("EmailComfirm2").checked = true;
        document.getElementById("EmailComfirm1").checked = false;
        $("#withoutEmailComfirmInd").val("N");
        document.getElementById("paperMode2").checked = true;
        document.getElementById("paperMode1").checked = false;
        $("#paperModeConfirmInd").val("N");
        $("#selectedDisModeInd").val("E");
       
        
        $("[name='disMode'][value='P']").click(function(event){
        	event.preventDefault();
          });
        
      
		if($("#cancelSelect").val() != null && $("#cancelSelect").val() != "" && $("#submitTag").val() == "C"
			&& $("#orderId").val() != null && $("#orderId").val() != ""){
			$(":input").attr("disabled", true);
			alert("Order is cancelled. Order ID: " + $("#orderId").val());
			window.location = "welcome.html";
		}else if($("#cancelSelect").val() != null && $("#cancelSelect").val() != "" && $("#submitTag").val() == "C"){
			$(":input").attr("disabled", true);
		}

		$("a#captureDocSerial").click(function(event) {
			event.preventDefault();
			var win = window.open('imscollectdoc.html?orderId=${imsSummaryUI.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=512,width=1024');
			//win.onclose(function(){location.reload(true); });
		});
		
		$("a#updateEdfRef").click(function(event) {
			event.preventDefault();
			var win = window.open('imsedfref.html?orderId=${imsSummaryUI.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=512,width=1024');
		});
		
	});
	
	
	
	function ds_collectedIndRefreshBtn(docType, index){
		$.ajax({
			url : 'imssupportdoccollectedindrefresh.html?orderId='+ $("#orderId").val() + '&docType='+ docType,
			type : 'POST',
			dataType : 'json',
			timeout : 60000, 
			error : function(XMLHttpRequest, textStatus, errorThown ){
						if(textStatus =='parsererror'){
// 							alert("Your session has been timed out, please re-login.");	
							alert('<spring:message code="ims.pcd.summary.msg.011"/>');
						}					
						else if(textStatus == 'timeout') {
// 							alert("Server response timeout, please try again.");
							alert('<spring:message code="ims.pcd.summary.msg.012"/>');
						}
					},
			success : function(msg){
				$.each(eval(msg), function(i, item) {
					$("div#ds_collectedInd_"+docType+"_"+index).html(item.isCollected);	
				});
			}			
		});	
	}
</script> 

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

<form:form name="summaryForm" method="POST" commandName="imsSummaryUI">
<input type="hidden" name="IMS_UID" value="<%= request.getAttribute("IMS_UID") %>" />
		<table>
		<tr>
			<td class="contenttextgreen">
				<br>
				<spring:message code="ims.pcd.summary.001"/> ${imsSummaryUI.orderId}
				<br>
			</td>
		</tr>
		</table>
<table width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
				<td class="table_title" ><spring:message code="ims.pcd.summary.002"/></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" class="contenttext">
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td class="table_title"><spring:message code="ims.pcd.summary.003"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<!-- Terrence Part -->
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<c:choose>
					<c:when test='${imsSummaryUI.idDocType != "BS"}'>
						<tr align="left" height="20px"><th align="left" width="250px"><spring:message code="ims.pcd.summary.083"/></th><td>${imsSummaryUI.title}</td></tr>
						<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.summary.084"/></th><td>${imsSummaryUI.lastName} ${imsSummaryUI.firstName}</td></tr>
					</c:when>
					<c:otherwise>
						<tr align="left" height="20px"><th align="left" width="250px"><spring:message code="ims.pcd.summary.085"/></th><td>${imsSummaryUI.companyName}</td></tr>
					</c:otherwise>
				</c:choose>
				
				<tr align="left" height="20px">
					<th align="left">
						<c:choose>
							<c:when test='${imsSummaryUI.idDocType == "PASS"}'>
							<spring:message code="ims.pcd.summary.033"/>
							</c:when>
							<c:when test='${imsSummaryUI.idDocType == "BS"}'>
							<spring:message code="ims.pcd.summary.034"/>
							</c:when>
							<c:when test='${imsSummaryUI.idDocType == "HKID"}'>
								<spring:message code="ims.pcd.summary.086"/>
							</c:when>
							<c:otherwise>
								${imsSummaryUI.idDocType}:
							</c:otherwise>
						</c:choose>
					</th>
					<td>${imsSummaryUI.idDocNum}</td>
				</tr>
				<c:if test='${imsSummaryUI.idDocType != "BS"}'>
					<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.summary.087"/></th><td>${imsSummaryUI.dobStr}</td></tr>
				</c:if>
				
				
<%-- 				<c:choose><c:when test='${imsSummaryUI.contact.contactPhone != null}'> --%>
<%-- 				<c:choose><c:when test='${imsSummaryUI.contact.contactPhone != ""}'> --%>
<%-- 				<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.summary.088"/></th><td>${imsSummaryUI.contact.contactPhone}</td></tr> --%>
<%-- 				</c:when></c:choose> --%>
<%-- 				</c:when><c:otherwise></c:otherwise></c:choose> --%>
<%-- 				<c:choose><c:when test='${imsSummaryUI.contact.otherPhone != null}'> --%>
<%-- 				<c:choose><c:when test='${imsSummaryUI.contact.otherPhone != ""}'> --%>
<%-- 				<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.summary.089"/></th><td>${imsSummaryUI.contact.otherPhone}</td></tr> --%>
<%-- 				</c:when></c:choose> --%>
<%-- 				</c:when><c:otherwise></c:otherwise></c:choose> --%>
<%-- 				<c:choose><c:when test='${imsSummaryUI.fixedLineNo != null}'> --%>
<%-- 				<c:choose><c:when test='${imsSummaryUI.fixedLineNo != ""}'> --%>
<%-- 				<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.summary.090"/></th><td>${imsSummaryUI.fixedLineNo}(PCCW)</td></tr> --%>
<%-- 				</c:when></c:choose> --%>
<%-- 				</c:when><c:otherwise></c:otherwise></c:choose> --%>
				
				<!-- NowID -->
				<c:if test='${imsSummaryUI.isRegNowId == "Y" && imsSummaryUI.nowId != "" && imsSummaryUI.nowId != null}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.summary.091"/></th><td>${imsSummaryUI.nowId}</td></tr>
				</c:if>
								
				<!-- Gary -->
				<c:choose><c:when test='${imsSummaryUI.csPortalInd == "Y" && imsSummaryUI.theClubInd == "Y"}'>
				<c:if test='${imsSummaryUI.csPortalLogin != null && imsSummaryUI.csPortalLogin != "" && imsSummaryUI.csPortalMobile != null && imsSummaryUI.csPortalMobile != ""}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.summary.004"/></th><td>${imsSummaryUI.csPortalLogin}<c:choose><c:when test='${imsSummaryUI.noMobileInd == "Y"}'></c:when><c:otherwise>/${imsSummaryUI.csPortalMobile}</c:otherwise></c:choose></td></tr>
				<c:if test='${imsSummaryUI.csPortalTheClubOptOutInd == "Y"}'> 
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.summary.092"/></th><td><input type="checkbox" id='optoutMyHktTheClub' disabled checked /> <spring:message code="ims.pcd.summary.006"/>All</td></tr>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.summary.009"/></th><td><c:if test='${imsSummaryUI.csPortalTheClubOptOutInd == "Y"}'>${imsSummaryUI.clubOptOutReasonDesc}<c:if test='${imsSummaryUI.optoutTheClubRmk != null && imsSummaryUI.optoutTheClubRmk != ""}'> - ${imsSummaryUI.optoutTheClubRmk}</c:if></c:if></td></tr>
				</c:if>
				</c:if> 
				</c:when>
				<c:when test='${imsSummaryUI.csPortalInd == "Y"}'>
				<c:if test='${imsSummaryUI.csPortalLogin != null && imsSummaryUI.csPortalLogin != "" && imsSummaryUI.csPortalMobile != null && imsSummaryUI.csPortalMobile != ""}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.summary.005"/></th><td>${imsSummaryUI.csPortalLogin}/${imsSummaryUI.csPortalMobile}</td></tr>
				<c:if test='${imsSummaryUI.csPortalOptOutInd == "Y"}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.summary.093"/></th><td><input type="checkbox" id='optoutMyHkt' disabled checked /><spring:message code="ims.pcd.summary.006"/> All</td></tr>
				</c:if>
				</c:if>
				</c:when>
				<c:when test='${imsSummaryUI.theClubInd == "Y"}'>
				<c:if test='${imsSummaryUI.theClubLogin != null && imsSummaryUI.theClubLogin != "" && imsSummaryUI.theClubMoblie != null && imsSummaryUI.theClubMoblie != ""}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.summary.007"/></th><td>${imsSummaryUI.theClubLogin}<c:choose><c:when test='${imsSummaryUI.noMobileInd == "Y"}'></c:when><c:otherwise>/${imsSummaryUI.theClubMoblie}</c:otherwise></c:choose></td></tr>
				<c:if test='${imsSummaryUI.theClubOptOutInd == "Y"}'> 
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.summary.008"/></th><td><input type="checkbox" id='optoutTheClub' disabled checked /> <spring:message code="ims.pcd.summary.006"/>All</td></tr>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.summary.009"/></th><td><c:if test='${imsSummaryUI.theClubOptOutInd == "Y"}'>${imsSummaryUI.clubOptOutReasonDesc}<c:if test='${imsSummaryUI.optoutTheClubRmk != null && imsSummaryUI.optoutTheClubRmk != ""}'> - ${imsSummaryUI.optoutTheClubRmk}</c:if></c:if></td></tr>
				</c:if>
				</c:if> 
				</c:when>
				</c:choose>
				
				<c:if test='${imsSummaryUI.careCashInd != null}'>
					<c:choose>
					<c:when test='${imsSummaryUI.careCashInd == "I"}'>					
						<tr align="left" height="20px">
						<th align="left" width="400"><spring:message code="ims.pcd.summary.010"/></th>
						<td><c:if test='${imsSummaryUI.careCashOptOutInd == "O"}'><spring:message code="ims.pcd.summary.011"/></c:if></td>
						</tr>				
					</c:when>
					<c:when test='${imsSummaryUI.careCashInd == "O"}'>
						<tr align="left" height="20px">
						<th align="left" width="400"><spring:message code="ims.pcd.summary.012"/></th>
						<td></td>
						</tr>
					</c:when>
					</c:choose>
				</c:if>
				
				<!-- <tr align="left" height="20px"><th>Contact Email:</th><td>taiman@netvigator.com</td></tr> -->
				<!-- <tr align="left" height="20px"><th>Language Preference:</th><td>Written(Chinese), Speaking(Chinese)</td></tr> -->
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.summary.013"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext">
				<tr valign="middle" align="center">
					<td>&nbsp;</td>
					<td width="70%">&nbsp;</td>
					<td class="table_title" ><spring:message code="ims.pcd.summary.014"/></td>
					<td class="table_title"><spring:message code="ims.pcd.summary.015"/></td>
				</tr>
				<tr valign="top">
					<td><c:if test='${imsSummaryUI.basketDtl.imagePath != null}'>
						<img src="basket/${imsSummaryUI.basketDtl.imagePath}" width="148">
					</c:if></td>
					<td>
						<div class="basket_title">
						<c:if test='${imsSummaryUI.basketDtl.bandwidth != null && imsSummaryUI.basketDtl.bandwidth != ""}'>
						${imsSummaryUI.basketDtl.bandwidth_desc}
						</c:if>
						${imsSummaryUI.basketDtl.basketTitle}</div><br>
						<div>
						<dir>${imsSummaryUI.servPlanDto.coreServiceDetail.progItem.itemDetails}</dir>
						</div>
						<div class="tableOrangetext"><spring:message code="ims.pcd.summary.094"/> ${imsSummaryUI.warrPeriod} <spring:message code="ims.pcd.summary.094a"/><br>
						</div>
					</td>
					<td class="BGgreen2" >${imsSummaryUI.servPlanDto.coreServiceDetail.progItem.itemMthFix}</td>
					<td class="BGgreen2" >${imsSummaryUI.servPlanDto.coreServiceDetail.progItem.itemMth2Mth}</td>
				</tr>
				<c:forEach items="${imsSummaryUI.servPlanDto.coreServiceDetail.bvasMandItemList}" var="bvasMandItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${bvasMandItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${bvasMandItemList.itemMthFix}</td>
					<td class="BGgreen2" >${bvasMandItemList.itemMth2Mth}</td>
				</tr>
				<tr></tr>
				</c:forEach>
				<c:forEach items="${imsSummaryUI.servPlanDto.coreServiceDetail.bvasNonMItemList}" var="bvasNonMItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${bvasNonMItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${bvasNonMItemList.itemMthFix}</td>
					<td class="BGgreen2" >${bvasNonMItemList.itemMth2Mth}</td>
					
				</tr>
				<tr></tr>
				</c:forEach>
				<c:forEach items="${imsSummaryUI.servPlanDto.giftList}" var="giftList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${giftList.gift_type} - ${giftList.giftTitle}
					</td>
					
					<td class="BGgreen2" >N/A</td>
					<td class="BGgreen2" >N/A</td>
					
				</tr>
				<tr></tr>
				</c:forEach>
				<c:forEach items="${imsSummaryUI.servPlanDto.optServiceDetail.optPremItemList}" var="optPremItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${optPremItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${optPremItemList.itemMthFix}</td>
					<td class="BGgreen2" >${optPremItemList.itemMth2Mth}</td>
					
				</tr>
				<tr></tr>
				</c:forEach>
				<!-- <c:forEach items="${imsSummaryUI.servPlanDto.optServiceDetail.wlBbItemList}" var="wlBbItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${wlBbItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${wlBbItemList.itemMthFix}</td>
					<td class="BGgreen2" >${wlBbItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<c:forEach items="${imsSummaryUI.servPlanDto.optServiceDetail.antiVirItemList}" var="antiVirItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${antiVirItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${antiVirItemList.itemMthFix}</td>
					<td class="BGgreen2" >${antiVirItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach> -->
				<c:forEach items="${imsSummaryUI.servPlanDto.optServiceDetail.optServItemList}" var="optServItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${optServItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${optServItemList.itemMthFix}</td>
					<td class="BGgreen2" >${optServItemList.itemMth2Mth}</td>
					
				</tr>
				<tr></tr>
				</c:forEach>
				<c:forEach items="${imsSummaryUI.servPlanDto.optServiceDetail.mediaItemList}" var="mediaItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${mediaItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${mediaItemList.itemMthFix}</td>
					<td class="BGgreen2" >${mediaItemList.itemMth2Mth}</td>
					
				</tr>
				<tr></tr>
				</c:forEach>
				<c:if test='${imsSummaryUI.ntvPgmItemList != null}'>
					<tr><td>&nbsp;</td><td class="item_title_rp"><br><spring:message code="ims.pcd.summary.095"/></td>
						<td class="BGgreen2" ></td>
						<td class="BGgreen2" ></td>
					</tr>
				</c:if>
				<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvFreeItemList}" var="ntvFreeItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td>
						<span class="tableOrangetext">${ntvFreeItemList.itemTitle}</span>
						<!-- <dir><span class="item_detail">
						${ntvFreeItemList.itemDetails}
						</span></dir> -->
						<dir><span class="item_detail">
						<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvFreeSPChannelList}" var="ntvFreeSPChannelList" varStatus="status">
						${ntvFreeSPChannelList.channelDesc}
						</c:forEach>
						</span></dir>
					</td>
					
					<td class="BGgreen2" >${ntvFreeItemList.itemMthFix}</td>
					<td class="BGgreen2" >${ntvFreeItemList.itemMth2Mth}</td>
					
				</tr>
				<tr></tr>
				</c:forEach>
				<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvFreeEPChannelList}" var="ntvFreeEPChannelList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${ntvFreeEPChannelList.channelDesc}
					</td>
					
					<td class="BGgreen2" ></td>
					<td class="BGgreen2" ></td>
					
				</tr>
				<tr></tr>
				</c:forEach>
				<c:if test='${fn:length(imsSummaryUI.servPlanDto.ntvServiceDetail.ntvFreeHDChannelList) != 0}'>
				<c:set var="chGroupDesc" value=""/>
				<c:set var="chGroupDescStart" value=""/>
				<tr valign="top">
				<td>&nbsp;</td>
				<td class="tableOrangetext">	
				<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvFreeHDChannelList}" var="ntvFreeHDChannelList" varStatus="status">
					<font color='#01A9DB'>
						<c:if test="${ntvFreeHDChannelList.channelGroupDesc != chGroupDesc}">
							${ntvFreeHDChannelList.channelGroupDesc}
							<c:set var="chGroupDesc" value="${ntvFreeHDChannelList.channelGroupDesc}"/>
							<ul>
						</c:if>
						<li>${ntvFreeHDChannelList.channelDesc}</li>
				</c:forEach>
				</ul></font>
				</td>
				<td class="BGgreen2" ></td>
				<td class="BGgreen2" ></td>
				</tr>
				</c:if>
				<c:if test="${imsSummaryUI.newtvpricingind ne 'Y'}">
				<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvPayItemList}" var="ntvPayItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<c:choose><c:when test='${ntvPayItemList.itemTvType == "SD"}'>
					<td>
						<span class="tableOrangetext">${ntvPayItemList.itemTitle}</span>
						<c:set var="parentCh" value=""/>
						<ul>
						<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvPayChannelList}" var="ntvPayChannelList" varStatus="status">
						<c:if test='${ntvPayChannelList.parentChannelId == null}'>
						<c:if test='${parentCh == null}'></li></c:if>
						<li><span><c:if test='${ntvPayChannelList.channelCd != null}'>
						Ch.</c:if>${ntvPayChannelList.channelCd}
						<c:if test='${ntvPayChannelList.channelCd != null}'>&nbsp;</c:if>${ntvPayChannelList.channelDesc}</span>
						<c:set var="parentCh" value="${ntvPayChannelList.parentChannelId}"/>
						</c:if>
						<c:if test='${ntvPayChannelList.parentChannelId != null}'>
						<span><c:if test='${ntvPayChannelList.channelCd != null}'>
						Ch.</c:if>${ntvPayChannelList.channelCd}
						<c:if test='${ntvPayChannelList.channelCd != null}'>&nbsp;</c:if>${ntvPayChannelList.channelDesc}</span></li>
						<c:set var="parentCh" value="${ntvPayChannelList.parentChannelId}"/>
						</c:if>
						</c:forEach>
						</ul>
						</td>
					</c:when><c:when test='${ntvPayItemList.itemTvType == "HD"}'>
					<td>
						<span class="tableOrangetext"><font color='#01A9DB'>${ntvPayItemList.itemTitle}</font></span>
						<%--<c:set var="parentCh" value=""/>
						<ul>
						<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvPayChannelList}" var="ntvPayChannelList" varStatus="status">
						<c:if test='${ntvPayChannelList.parentChannelId == null}'>
						<c:if test='${parentCh == null}'></li></c:if>
						<li><span><c:if test='${ntvPayChannelList.channelCd != null}'>
						Ch.</c:if>${ntvPayChannelList.channelCd}
						<c:if test='${ntvPayChannelList.channelCd != null}'>&nbsp;</c:if>${ntvPayChannelList.channelDesc}</span>
						<c:set var="parentCh" value="${ntvPayChannelList.parentChannelId}"/>
						</c:if>
						<c:if test='${ntvPayChannelList.parentChannelId != null}'>
						<span><c:if test='${ntvPayChannelList.channelCd != null}'>
						Ch.</c:if>${ntvPayChannelList.channelCd}
						<c:if test='${ntvPayChannelList.channelCd != null}'>&nbsp;</c:if>${ntvPayChannelList.channelDesc}</span></li>
						<c:set var="parentCh" value="${ntvPayChannelList.parentChannelId}"/>
						</c:if>
						</c:forEach>
						</ul>--%>
						</td>
					</c:when></c:choose>
					<td class="BGgreen2" >${ntvPayItemList.itemMthFix}</td>
					<td class="BGgreen2" >${ntvPayItemList.itemMth2Mth}</td>
					
				</tr>
				<tr></tr>
				</c:forEach>
				</c:if>
				
				<!-- new nowtv pricing -->
								
				<c:if test="${imsSummaryUI.newtvpricingind eq 'Y'}"> <!-- martin -->
				<!-- FTA Campaign -->
					<c:set var="newtvtitle" value="0" />
					<c:forEach var="ftaPack" items="${imsSummaryUI.nowTVAddUI.ftaCampaign.tvPacks}" varStatus="i">
					<c:if test="${ftaPack.selected}">
					<tr>
						<td>&nbsp;</td>
						<td>
							<c:if test="${newtvtitle eq 0}">
								<span class="basket_title">
<%-- 								${ftaPack.title} --%>
							<spring:message code="ims.nowtvpricing.IMS001"/>
								</span>
								<c:set var="newtvtitle" value="1" />
							</c:if>
							<ul style="list-style-type:none;">
								<li><span class="summarycontenttext">${ftaPack.displayDtails}</span></li>
							</ul>
						</td>
						<td class="BGgreen2">${imsSummaryUI.nowTVAddUI.ftaCampaign.fix_term_rate}</td>
						<td class="BGgreen2">${imsSummaryUI.nowTVAddUI.ftaCampaign.mth_to_mth_rate}</td>
					</tr>
					</c:if>
					</c:forEach>
				<!-- FTA Campaign (end)-->
				<!-- Hard Campaign -->
					<c:set var="newtvtitle" value="0" />
					<c:forEach var="hardPack" items="${imsSummaryUI.nowTVAddUI.hardCampaign.tvPacks}" varStatus="i">
					<c:if test="${hardPack.selected}">
					<c:set var="newtvtitle" value="${newtvtitle+1}" />
					<tr>
						<td>&nbsp;</td>
						<td valign="top">
							<c:if test="${newtvtitle eq 1}">
							<span class="basket_title">
<%-- 							${imsSummaryUI.nowTVAddUI.hardCampaign.title} --%>
							<spring:message code="ims.nowtvpricing.IMS002"/>
							</span>
							</c:if>
							<ul style="list-style-type:none;">
								<li><span class="summarycontenttext">${hardPack.displayDtails}</span></li>
								<c:if test="${newtvtitle eq imsSummaryUI.nowTVAddUI.hardCampaign.numOfSelectedPack}">
								<c:forEach var="topUp" items="${imsSummaryUI.nowTVAddUI.hardCampaign.topUps}" varStatus="k">
								<li class="summarycontenttext" style="color:#0099ff">
									<c:if test="${topUp.selected}">
										<c:if test="${newtvtitle eq imsSummaryUI.nowTVAddUI.hardCampaign.numOfSelectedPack}">
<!-- 											Gift &amp; Premium: -->
											<spring:message code="ims.nowtvpricing.IMS003"/> : 
											<c:set var="newtvtitle" value="${newtvtitle+1}" />
										</c:if>
										${topUp.detail}
									</c:if>
								</li>
								</c:forEach>
								</c:if>
							</ul>
						</td>
						<td class="BGgreen2">&nbsp;</td>
						<td class="BGgreen2">&nbsp;</td>
					</tr>
					</c:if>
					</c:forEach>
					
					<!-- Hard Campaign (end)-->
					<!-- Pay Bundle -->
					<c:set var="newtvtitle" value="0" />
					<c:forEach var="payTv" items="${imsSummaryUI.nowTVAddUI.payTvCampaign}" varStatus="i">
						<c:forEach var="pack" items="${payTv.tvPacks}" varStatus="j">
						<c:if test="${pack.selected}">
							<tr class="payTvCampaign">
								<td>&nbsp;</td>
								<td>
								<c:if test="${newtvtitle eq 0}">
								<span class="basket_title">
<%-- 								${imsSummaryUI.nowTVAddUI.payTvCampaign[0].title} --%>
									<spring:message code="ims.nowtvpricing.IMS004"/>
								</span>
								<c:set var="newtvtitle" value="1" />
								</c:if>
								<ul style="list-style-type:none;">
									<li><span style="text-decoration:underline;" class="summarycontenttext">${pack.title}</span></li>
									<li>
									<table style="width:100%;">
										<tr>
										<td style="width:50%;border-color:transparent;" valign="top">
											<table style="width:100%;">
											<c:forEach var="channel" items="${pack.tvChannels}" varStatus="k">
												<c:if test="${imsSummaryUI.nowTVAddUI.ntvConnType >= channel.readRight}">
												<tr>
													<td style="width:60px;border-color:transparent;"><span class="summarycontenttext">${channel.channelId}</span></td>
													<td style="border-color:transparent;"><span class="summarycontenttext">${channel.channelDisplayDetail}</span></td>
												</tr>
												</c:if>
											</c:forEach>
											</table>
										</td>
										</tr>
									</table>
									</li>
								</ul>
								<c:choose>
    							<c:when test="${newtvtitle eq 2 or newtvtitle eq 1}">
									<td class="BGgreen2">${payTv.fix_term_rate}</td>
									<td class="BGgreen2">${payTv.mth_to_mth_rate}</td>
									<c:set var="newtvtitle" value="3" />
							    </c:when>    
							    <c:otherwise>
									<td class="BGgreen2"></td>
									<td class="BGgreen2"></td>
							    </c:otherwise>
								</c:choose>
							</tr>
						</c:if>
						</c:forEach>
						<c:if test="${newtvtitle eq 3}">
						<tr class="payTvCampaign">
							<td>&nbsp;</td>
							<td>
								<ul style="list-style-type:none;">
									<c:forEach var="topUp" items="${payTv.topUps}" varStatus="k">
									<li class="summarycontenttext" style="color:#0099ff">
											<c:if test="${topUp.selected}">
<%-- 												<c:set var="newtvtitle" value="2" /> --%>
<!-- 												Gift &amp; Premium: -->
												<spring:message code="ims.nowtvpricing.IMS003"/> : 
												${topUp.detail}
											</c:if>
									</li>
									</c:forEach>
								</ul>
							</td>
							<td class="BGgreen2"></td>
							<td class="BGgreen2"></td>
						</tr>
						</c:if>
					</c:forEach>
					<!-- Pay Bundle (end)-->
			
				<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvPayItemList}" var="ntvPayItemList" varStatus="status">
					<tr valign="top">
						<td>&nbsp;</td>
						<td>
							<span class="basket_title">${ntvPayItemList.itemTitle}</span>
							<c:if test='${ntvPayItemList.type == "NTV_PAY" || fn:indexOf(ntvPayItemList.type,"NTV_SP")>=0  }'>

								<c:set var="parentCh" value=""/>
								<ul>
								<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvPayChannelList}" var="ntvPayChannelList" varStatus="status">
								<c:if test='${ntvPayChannelList.parentChannelId == null}'>
								<c:if test='${parentCh == null}'></li></c:if>
								<li><span><c:if test='${ntvPayChannelList.channelCd != null}'>
								Ch.</c:if>${ntvPayChannelList.channelCd}
								<c:if test='${ntvPayChannelList.channelCd != null}'>&nbsp;</c:if>${ntvPayChannelList.channelDesc}</span>
								<c:set var="parentCh" value="${ntvPayChannelList.parentChannelId}"/>
								</c:if>
								<c:if test='${ntvPayChannelList.parentChannelId != null}'>
								<span><c:if test='${ntvPayChannelList.channelCd != null}'>
								Ch.</c:if>${ntvPayChannelList.channelCd}
								<c:if test='${ntvPayChannelList.channelCd != null}'>&nbsp;</c:if>${ntvPayChannelList.channelDesc}</span></li>
								<c:set var="parentCh" value="${ntvPayChannelList.parentChannelId}"/>
								</c:if>
								</c:forEach>
								</ul>

							</c:if>
						</td>
						<td class="BGgreen2" >${ntvPayItemList.itemMthFix}</td>
						<td class="BGgreen2" >${ntvPayItemList.itemMth2Mth}</td>
					</tr>
					</c:forEach>
				</c:if>
				
				<!-- end new nowtv pricing  -->
				<c:forEach items="${imsSummaryUI.servPlanDto.ntvServiceDetail.ntvOtherItemList}" var="ntvOtherItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${ntvOtherItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${ntvOtherItemList.itemMthFix}</td>
					<td class="BGgreen2" >${ntvOtherItemList.itemMth2Mth}</td>
					
				</tr>
				<tr></tr>
				</c:forEach>
				<!-- <tr><td colspan="4"/><hr/></tr>
				<tr valign="top">
					<td width="60%" class="black_text_16" colspan="2">Total:</td>
					<td class="BGgreen2" >$${imsSummaryUI.totalRecurrentAmt}</td>
					<td class="BGgreen2" >$${imsSummaryUI.totalMthToMthRate}</td>
				</tr> -->
				<c:if test='${ImsOrder.HDDPurchased eq true}'>
				<tr valign="top">
					<td>&nbsp;</td>
					<td>
					
				- <spring:message code="ims.pcd.summary.018"/>
					</td>
					
					<td class="BGgreen2" ></td>
					<td class="BGgreen2" ></td>
				</tr>
				</c:if>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.summary.019"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<!-- Terrence Part -->
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px"><spring:message code="ims.pcd.summary.020"/> </th><td>
					<c:choose><c:when test='${imsSummaryUI.installAddress.unitNo == null}'></c:when>
							  <c:when test='${imsSummaryUI.installAddress.unitNo == ""}'></c:when>
							  <c:otherwise>Flat </c:otherwise></c:choose>
					${imsSummaryUI.installAddress.unitNo}<c:choose><c:when test='${imsSummaryUI.installAddress.unitNo == null}'></c:when><c:when test='${imsSummaryUI.installAddress.unitNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					Floor ${imsSummaryUI.installAddress.floorNo}, ${imsSummaryUI.installAddress.hiLotNo}<c:choose><c:when test='${imsSummaryUI.installAddress.hiLotNo == null}'></c:when><c:when test='${imsSummaryUI.installAddress.hiLotNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsSummaryUI.installAddress.buildNo}<c:choose><c:when test='${imsSummaryUI.installAddress.buildNo == null}'></c:when><c:when test='${imsSummaryUI.installAddress.buildNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsSummaryUI.installAddress.strNo} ${imsSummaryUI.installAddress.strName} ${imsSummaryUI.installAddress.strCatDesc}<c:choose><c:when test='${imsSummaryUI.installAddress.strName == null}'></c:when><c:when test='${imsSummaryUI.installAddress.strName == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsSummaryUI.installAddress.sectDesc}<c:choose><c:when test='${imsSummaryUI.installAddress.sectDesc == null}'></c:when><c:when test='${imsSummaryUI.installAddress.sectDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsSummaryUI.installAddress.distDesc}<c:choose><c:when test='${imsSummaryUI.installAddress.distDesc == null}'></c:when><c:when test='${imsSummaryUI.installAddress.distDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsSummaryUI.installAddress.areaDesc}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.summary.103"/></th><td><c:choose>
																	<c:when test='${imsSummaryUI.billingAddress != null}'>
																		<c:choose><c:when test='${imsSummaryUI.billingAddress.unitNo == null}'></c:when><c:when test='${imsSummaryUI.billingAddress.unitNo == ""}'></c:when><c:otherwise>Flat </c:otherwise></c:choose>
																		${imsSummaryUI.billingAddress.unitNo}<c:choose><c:when test='${imsSummaryUI.billingAddress.unitNo == null}'></c:when><c:when test='${imsSummaryUI.billingAddress.unitNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		Floor ${imsSummaryUI.billingAddress.floorNo}, ${imsSummaryUI.billingAddress.hiLotNo}<c:choose><c:when test='${imsSummaryUI.billingAddress.hiLotNo == null}'></c:when><c:when test='${imsSummaryUI.billingAddress.hiLotNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.billingAddress.buildNo}<c:choose><c:when test='${imsSummaryUI.billingAddress.buildNo == null}'></c:when><c:when test='${imsSummaryUI.billingAddress.buildNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.billingAddress.strNo} ${imsSummaryUI.billingAddress.strName} ${imsSummaryUI.billingAddress.strCatDesc}<c:choose><c:when test='${imsSummaryUI.billingAddress.strName == null}'></c:when><c:when test='${imsSummaryUI.billingAddress.strName == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.billingAddress.sectDesc}<c:choose><c:when test='${imsSummaryUI.billingAddress.sectDesc == null}'></c:when><c:when test='${imsSummaryUI.billingAddress.sectDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.billingAddress.distDesc}<c:choose><c:when test='${imsSummaryUI.billingAddress.distDesc == null}'></c:when><c:when test='${imsSummaryUI.billingAddress.distDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.billingAddress.areaDesc}
																	</c:when>
																	<c:when test='${imsSummaryUI.billingAddress == null}'>
																		<c:choose><c:when test='${imsSummaryUI.installAddress.unitNo == null}'></c:when><c:when test='${imsSummaryUI.installAddress.unitNo == ""}'></c:when><c:otherwise>Flat </c:otherwise></c:choose>
																		${imsSummaryUI.installAddress.unitNo}<c:choose><c:when test='${imsSummaryUI.installAddress.unitNo == null}'></c:when><c:when test='${imsSummaryUI.installAddress.unitNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		Floor ${imsSummaryUI.installAddress.floorNo}, ${imsSummaryUI.installAddress.hiLotNo}<c:choose><c:when test='${imsSummaryUI.installAddress.hiLotNo == null}'></c:when><c:when test='${imsSummaryUI.installAddress.hiLotNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.installAddress.buildNo}<c:choose><c:when test='${imsSummaryUI.installAddress.buildNo == null}'></c:when><c:when test='${imsSummaryUI.installAddress.buildNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.installAddress.strNo} ${imsSummaryUI.installAddress.strName} ${imsSummaryUI.installAddress.strCatDesc}<c:choose><c:when test='${imsSummaryUI.installAddress.strName == null}'></c:when><c:when test='${imsSummaryUI.installAddress.strName == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.installAddress.sectDesc}<c:choose><c:when test='${imsSummaryUI.installAddress.sectDesc == null}'></c:when><c:when test='${imsSummaryUI.installAddress.sectDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.installAddress.distDesc}<c:choose><c:when test='${imsSummaryUI.installAddress.distDesc == null}'></c:when><c:when test='${imsSummaryUI.installAddress.distDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsSummaryUI.installAddress.areaDesc}
																	</c:when>
																  </c:choose></td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.summary.021"/></th><td>${imsSummaryUI.loginId}@netvigator.com</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.summary.022"/></th><td>				
						<c:choose>						
							<c:when test='${imsSummaryUI.noBooking == "Y"}'>
								${imsSummaryUI.appntStartDateDisplay}
							</c:when>
							<c:otherwise>
								${imsSummaryUI.appntStartDateDisplay} - ${imsSummaryUI.appntEndDateDisplay}	
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.summary.101"/></th><td>${imsSummaryUI.appDate}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.summary.022"/><br>
						<c:choose>						
							<c:when test='${ImsOrder.preUseInd == "Y"}'>
								<spring:message code="ims.pcd.summary.096"/>
							</c:when>
							<c:otherwise>
								<spring:message code="ims.pcd.summary.097"/>
							</c:otherwise>
						</c:choose>
					</th><td>${imsSummaryUI.targetCommDate}</td>
				</tr>
		
<!-- 				Celia -->
	<c:if test="${ims_direct_sales eq true and ImsOrder.dsType eq 'Door Knocked'}"> 
		<tr align="left" height="20px">
			<th align="left"><spring:message code="ims.pcd.summary.024"/></th>
				<td align="left">${ImsOrder.dsWaiveCoolOff}</td>
		</tr>
	</c:if>

			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.summary.025"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<!-- Terrence Part -->
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px"><spring:message code="ims.pcd.summary.025"/></th><td>
					<form:hidden path="account.payment.payMtdType"/>
					<c:choose><c:when test='${imsSummaryUI.account.payment.payMtdType == "M"}'><spring:message code="ims.pcd.summary.026"/></c:when>
							<c:when test='${imsSummaryUI.account.payment.payMtdType == "C"}'><spring:message code="ims.pcd.summary.027"/></c:when>
					</c:choose>
									<c:choose>
										<c:when test='${imsSummaryUI.account.payment.payMtdType == "C"}'>
															<c:choose>
																<c:when test='${imsSummaryUI.account.payment.thirdPartyInd == "Y"}'>
																	(Third Party)
																</c:when>
																<c:otherwise>
																	(Self)
																</c:otherwise>
															</c:choose>
										</c:when>
										<c:otherwise>
										
										</c:otherwise>
									</c:choose></td>
				</tr>
				<c:choose>
					<c:when test='${imsSummaryUI.account.payment.payMtdType == "C"}'>
						<tr align="left" height="20px">
							<th align="left"><spring:message code="ims.pcd.summary.028"/></th>
							<c:choose><c:when test='${imsSummaryUI.account.payment.ccType == "V"}'>
								<td>VISA</td>
							</c:when>
							<c:when test='${imsSummaryUI.account.payment.ccType == "M"}'>
								<td>MASTER CARD</td>
							</c:when>
							<c:when test='${imsSummaryUI.account.payment.ccType == "A"}'>
								<td>American Express</td>
							</c:when></c:choose></tr>
						<tr align="left" height="20px">
							<th align="left">
						<spring:message code="ims.pcd.summary.031"/></th><td>${imsSummaryUI.account.payment.ccNum}</td></tr>
						<c:if test="${not empty imsSummaryUI.account.payment.ccIdDocNo}">
							<c:choose>
								<c:when test='${imsSummaryUI.account.payment.thirdPartyInd == "Y"}'>
									<tr align="left" height="20px">
										<th align="left"><spring:message code="ims.pcd.summary.032"/></th><td>
										<c:choose><c:when test='${imsSummaryUI.account.payment.ccIdDocType == "PASS"}'>
										PASSPORT</c:when>
										<c:when test='${imsSummaryUI.account.payment.ccIdDocType == "BS"}'>
										HKBR</c:when>
										<c:otherwise>
										${imsSummaryUI.account.payment.ccIdDocType}
										</c:otherwise></c:choose>
										</td></tr>
								
									<tr align="left" height="20px">
										<th align="left">
										<spring:message code="ims.pcd.summary.031"/></th><td>${imsSummaryUI.account.payment.ccIdDocNo}</td>
									</tr>
								
								</c:when>
								<c:otherwise>
								
								</c:otherwise>
							</c:choose>
						</c:if>
						<tr align="left" height="20px">
							<th align="left">
								<spring:message code="ims.pcd.summary.036"/></th><td>${imsSummaryUI.account.payment.ccHoldName}
									</td>
						</tr>
						<tr align="left" height="20px">
							<th align="left">
							<spring:message code="ims.pcd.summary.098"/></th><td>${imsSummaryUI.account.payment.ccExpDate}</td></tr>
						
						<tr align="left" height="20px">
							<th align="left">
								<spring:message code="ims.pcd.summary.037"/></th>
								<td>$${imsSummaryUI.prepayAmt}
									<c:if test='${imsSummaryUI.waivedPrepay == "Y"}'>(waived)</c:if>
								</td>
						</tr>
					</c:when>
					<c:when test='${imsSummaryUI.account.payment.payMtdType == "M"}'>
						<c:choose><c:when test='${imsSummaryUI.cashFsPrepay == "N"}'>
						<tr align="left" height="20px">
							<th align="left">
								<spring:message code="ims.pcd.summary.105"/></th><td>$${imsSummaryUI.prepayAmt}
								<c:if test='${imsSummaryUI.waivedPrepay == "Y"}'>(waived)</c:if>
								</td></tr>
						</c:when>
						<c:when test='${imsSummaryUI.cashFsPrepay == "Y"}'>
						<tr align="left" height="20px">
							<th align="left">
								<spring:message code="ims.pcd.summary.099"/></th><td>$${imsSummaryUI.prepayAmt}</td></tr>
						</c:when></c:choose>
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				</c:choose>
				<tr align="left" height="20px">
					<th align="left">
						<c:choose>
							<c:when test='${imsSummaryUI.otChrgType == "A"}'><spring:message code="ims.pcd.summary.038"/> </c:when>
							<c:otherwise><spring:message code="ims.pcd.summary.039"/> </c:otherwise>
						</c:choose>
						<spring:message code="ims.pcd.summary.040"/></th>
								<td>${imsSummaryUI.waivedOtInstallChrg}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">
				<spring:message code="ims.pcd.summary.102"/></th><td><c:choose>
											<c:when test='${imsSummaryUI.account.billMedia == "P"}'>
											<spring:message code="ims.pcd.summary.041"/>
											</c:when>
											<c:when test='${imsSummaryUI.account.billMedia == "E"}'>
											<spring:message code="ims.pcd.summary.042"/> ${imsSummaryUI.account.emailAddr}
											</c:when>
										</c:choose></td></tr>
			</table>
			</td>
		</tr>

<!-- Distribution Mode session in the form -->
		
		
<!--		<form:hidden path="disMode"/>-->

		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.summary.043"/></td>
				</tr>
			</table>
			</td>
		</tr>

<c:choose>
<c:when test="${ims_direct_sales eq true}">
		<tr>
			<td>	
				<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
					<tr>
						<td width="40%" style="display:none"> 
							<div align="left", style="margin-left:10px;width:200px;"> 	
								<form:radiobutton path="disMode" label="Digital Signature " value="E" onclick="DisModeE()"/>
							</div>
						</td>
						<td width="40%" <c:if test="${ims_direct_sales eq true}">style="display:none;"</c:if>> 
							<div align="left", style="margin-left:10px;width:200px;"> 	
									<spring:message code="ims.pcd.summary.044"/>
							</div>
						</td>
					
						<td width="60%" >				
							<div id="esigEmailAddrDiv" style="margin-left: 10px;"><spring:message code="ims.pcd.summary.046"/>
	<!-- 							<input id="esigEmailAddr" name="esigEmailAddr" style="width:20em" type="text" value="" maxlength="64";/> -->             
								
								<form:input path="esigEmailAddr" maxlength="64" cssStyle="width:20em; color:red ; font-weight:bold" />
								<div class="clear2"></div>
								<span class="error error_esigEmailAddr" style="display:none"></span>						
								<form:errors path="esigEmailAddr" cssClass="error"/>
								<br>
									<div id="SMSnoDiv" style="margin-left: 10px;"><spring:message code="ims.pcd.summary.045"/> 							
								<form:input path="SMSno" maxlength="8" cssStyle="width:20em; color:red ; font-weight:bold" />
								<div class="clear2"></div>
								<span class="error error_SMSno" style="display:none"></span>						
								<form:errors path="SMSno" cssClass="error"/>
							</div>

							</div>
						</td>
					</tr>	
					
				
					<tr><td>&nbsp;</td></tr>
				</table>
			</td>
		</tr>
</c:when>
<c:when test="${imsSummaryUI.isCC=='Y' and imsSummaryUI.mode!='R'}">
		<tr>
			<td>	
				<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
					<tr>
<!-- 						<td width="40%"> -->
<!-- 							<div align="left" style="margin-left:10px;width:200px;"> 	 -->
<%-- 								<form:radiobutton path="disMode" value="I" onclick="DisModeI()"/><spring:message code="ims.pcd.summary.046"/>	 --%>
<!-- 							</div> -->
<!-- 						</td> -->
						<td width="60%">				
							<div id="emailAddrDiv" style="margin-left: 10px;"><spring:message code="ims.pcd.summary.046"/>	
								<form:input path="emailAddr" maxlength="64" cssStyle="width:20em; color:red ; font-weight:bold" />
								<div class="clear2"></div>
								<span class="error error_emailAddr" style="display:none"></span>						
								<form:errors path="emailAddr" cssClass="error"/>
							</div>
						</td>
					</tr>
					
					
					
					<tr>					
<!-- 						<td width="40%"> -->
<!-- 							<div style="margin-left: 10px;">  -->
<%-- 								<form:radiobutton path="disMode"  value="S" onclick="DisModeS()"/><spring:message code="ims.pcd.summary.045"/>	 --%>
<!-- 							</div> -->
<!-- 						</td> -->
						<td width="60%">				
							<div id="SMSnoDiv" style="margin-left: 10px;"><spring:message code="ims.pcd.summary.045"/>							
								<form:input path="SMSno" maxlength="8" cssStyle="width:20em; color:red ; font-weight:bold" />
								<div class="clear2"></div>
								<span class="error error_SMSno" style="display:none"></span>						
								<form:errors path="SMSno" cssClass="error"/>
							</div>
						</td>
					</tr>
					
					
					<tr style="display:none">					
						<td width="40%">
							<div style="margin-left: 10px;"> 
								<form:radiobutton path="disMode"  value="M" onclick="DisModeM()"/><spring:message code="ims.pcd.summary.104"/>	
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
</c:when>
<c:when test="${imsSummaryUI.isCC!='Y' or (imsSummaryUI.isPT=='Y' and imsSummaryUI.mode=='R')}">
		<tr>
			<td>	
				<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
					<tr>
						<td width="40%">
							<div align="left", style="margin-left:10px;width:200px;"> 	
									<form:radiobutton path="disMode"  value="E" onclick="DisModeE()"/><spring:message code="ims.pcd.summary.044"/>	
							</div>
						</td>
						
					
						<td width="60%">				
							<div id="esigEmailAddrDiv" style="margin-left: 10px;"><spring:message code="ims.pcd.summary.046"/> 						
	<!-- 							<input id="esigEmailAddr" name="esigEmailAddr" style="width:20em" type="text" value="" maxlength="64";/> -->
								
								<form:input path="esigEmailAddr" maxlength="64" cssStyle="width:20em; color:red ; font-weight:bold" />
								<div class="clear2"></div>
								<span class="error error_esigEmailAddr" style="display:none"></span>						
								<form:errors path="esigEmailAddr" cssClass="error"/>
							</div>
						</td>
					</tr>	
					<tr>
						<td width="40%">
							<div align="left", style="margin-left:10px;width:200px;">
							</div>
						</td>
						
					
						
						<td width="60%">				
							<div id="SMSnoDiv" style="margin-left: 10px;"><spring:message code="ims.pcd.summary.045"/>							
								<form:input path="SMSno" maxlength="8" cssStyle="width:20em; color:red ; font-weight:bold" />
								<div class="clear2"></div>
								<span class="error error_SMSno" style="display:none"></span>						
								<form:errors path="SMSno" cssClass="error"/>
							</div>
						</td>
						
					</tr>	
					<tr>					
						<td>
							<div style="margin-left: 10px;"> 
	<!-- 							<input type="radio" id="disMode2" name="disMode" value="P" onclick="DisModeChange()" onclick="show('P')"/><label for="disMode2">Paper</label> -->
								<form:radiobutton path="disMode" value="P" onclick="SelectPaperModeDialog()"/><spring:message code="ims.pcd.summary.047"/>	
							</div>
						</td>
					</tr>
				
					<tr><td>&nbsp;</td></tr>
				</table>
			</td>
		</tr>
</c:when>
</c:choose>
		
<!-- Application Form Language session in the form -->		
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.summary.048"/></td>
				</tr>
			</table>
			</td>
		</tr>	
		
		<tr>
			<td>	
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr>
        			<td align="left"><spring:message code="ims.pcd.summary.049"/>
        				<form:select id="langPreference" path="langPreference" onchange="langPreferenceChange()">        				
  						<form:option value="zh" label="Traditional Chinese" ><spring:message code="ims.pcd.summary.050"/></form:option>
  						<form:option value="en" label="English" ><spring:message code="ims.pcd.summary.051"/></form:option>
						</form:select>
        			</td>        	
        		</tr>
        	</table>	
        	</td>
        </tr>
        




        
<!--Required Supporting Document session in the form--> 
<!-- Celia Support Doc start-->
		<tr <c:if test='${(imsSummaryUI.isCC == "Y" and imsSummaryUI.mode != "R") or ims_direct_sales eq true}'>style="display:none"</c:if>>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.summary.052"/></td>
				</tr>
			</table>
			</td>
		</tr>
		
	
			<tr <c:if test='${(imsSummaryUI.isCC == "Y" and imsSummaryUI.mode != "R") or ims_direct_sales eq true }'>style="display:none"</c:if>>
				<td>
					<table id ="paperSupDoc" width="100%" border="0" cellspacing="1" class="contenttext"
						background="images/background2.jpg">
						<tr class="contenttextgreen2">
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.summary.053"/></td>
							<td colspan="2" style="text-align: center; width: 50%;"
								class="table_title"><spring:message code="ims.pcd.summary.054"/></td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.summary.055"/></td>
						</tr>

						<c:forEach items="${imsSummaryUI.allOrdDocAssgnDTOs}" var="dto" varStatus="status">
							<tr>
										<td colspan="1" style="text-align: center; width: 25%;">
											<c:out value="${dto.docName}"/>
										</td>
										<td colspan="2" style="text-align: center; width: 50%;">
											<c:choose>
											<c:when test="${not empty waiveReasonsPaper[dto.docType]}">
											<form:select id="${dto.docType}" path="allOrdDocAssgnDTOs[${status.index}].waiveReason">
												<form:option value="" label="Select..."/>
												<form:options items="${waiveReasonsPaper[dto.docType]}" itemValue="waiveReason" itemLabel="waiveDesc"/>
											</form:select>
											</c:when>					
											<c:otherwise>
												<select id="${dto.docType}"  style="width:200px;" disabled> 
													<option selected="selected" value = "">Select...</option>
												</select>
											</c:otherwise>			
											</c:choose>
										</td>
										<td colspan="1" style="text-align: center; width: 25%;">
											<span class="note"></span>
											<c:out value="${dto.collectedInd}"/>
										</td>
							</tr>
						</c:forEach>
	
	
					</table>
		
					<table id ="digitalSupDoc" width="100%" border="0" cellspacing="1" class="contenttext"
						background="images/background2.jpg">
						<tr class="contenttextgreen2">
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.summary.053"/></td>
							<td colspan="2" style="text-align: center; width: 50%;"
								class="table_title"><spring:message code="ims.pcd.summary.054"/></td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.summary.055"/></td>
						</tr>

						<c:forEach items="${imsSummaryUI.allOrdDocAssgnDTOs}" var="dto" varStatus="status">
							<tr>				
								<c:choose>
										<c:when test='${dto.docType == "I004"}'></c:when>	
										<c:otherwise>
										<td colspan="1" style="text-align: center; width: 25%;">
											<c:out value="${dto.docName}"/>
										</td>
										<td colspan="2" style="text-align: center; width: 50%;">
											<c:choose>
												<c:when test="${not empty waiveReasonsDigital[dto.docType]}">
													<form:select id="${dto.docName}" path="allOrdDocAssgnDTOs[${status.index}].waiveReason">
														<form:option value="" label="Select..."/>
														<form:options items="${waiveReasonsDigital[dto.docType]}" itemValue="waiveReason" itemLabel="waiveDesc"/>
													</form:select>
												</c:when>					
												<c:otherwise>
													<select id="${dto.docName}" style="width:200px;" disabled> 
														<option selected="selected" value = "">Select...</option>
													</select>
												</c:otherwise>			
											</c:choose>
										</td>
										<td colspan="1" style="text-align: center; width: 25%;">
											<span class="note"></span>
											<c:out value="${dto.collectedInd}"/>
										</td>
										</c:otherwise>	
									</c:choose>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				
<!-- 			steven added for DS, mismatch start -->
			<tr <c:if test='${ims_direct_sales eq false or ims_ds_mismatch_hkid eq false}'>style="display:none"</c:if>>
				<td><table width="100%" border="0" cellspacing="1" rules="none">
					<tr><td class="table_title" ><spring:message code="ims.pcd.summary.052"/></td></tr>
				</table></td>
			</tr>
		
	
			<tr <c:if test='${ims_direct_sales eq false or ims_ds_mismatch_hkid eq false}'>style="display:none"</c:if>>
				<td>		
					<table  width="100%" border="0" cellspacing="1" class="contenttext" >
						<tr class="contenttextgreen2">
							<td colspan="1" style="text-align: center; width: 25%;" class="table_title"><spring:message code="ims.pcd.summary.053"/></td>
							<td colspan="2" style="text-align: center; width: 50%;" class="table_title"><spring:message code="ims.pcd.summary.054"/></td>
							<td style="text-align: center; width: 12.5%;" class="table_title"><spring:message code="ims.pcd.summary.055"/></td>
							<td style="text-align: center; width: 12.5%;" class="table_title"></td>
						</tr>

						<c:forEach items="${imsSummaryUI.allOrdDocAssgnDTOs}" var="dto" varStatus="status">
							<tr>				
								<c:choose>
										<c:when test='${dto.docType == "I007"}'>
										<td colspan="1" style="text-align: center; width: 25%;"><c:out value="${dto.docName}"/></td>
										<td colspan="2" style="text-align: center; width: 50%;">
											<c:choose>
												<c:when test="${not empty waiveReasonsDigital[dto.docType]}">
													<form:select id="${dto.docName}" path="allOrdDocAssgnDTOs[${status.index}].waiveReason">
														<form:option value="" label="Select..."/>
														<form:options items="${waiveReasonsDigital[dto.docType]}" itemValue="waiveReason" itemLabel="waiveDesc"/>
													</form:select>
												</c:when>					
												<c:otherwise>
													<select id="${dto.docName}" style="width:200px;" disabled> 
														<option selected="selected" value = "">Select...</option>
													</select>
												</c:otherwise>			
											</c:choose>
										</td>
										<td style="text-align: center; width: 12.5%;">
											<span class="note"></span>	
											<div id="ds_collectedInd_${dto.docType}_${status.index}">
												<c:out value="${dto.collectedInd}"/>
											</div>			
										</td>
										<td style="text-align: center; width: 12.5%;">
											<a href="#"  class="nextbutton" onclick="ds_collectedIndRefreshBtn('${dto.docType}', '${status.index}')"><spring:message code="ims.pcd.summary.062"/></a>
										</td>
										</c:when>	
									</c:choose>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
<!-- 			steven added for DS, mismatch end -->
				
				
				<c:if test='${imsSummaryUI.isCC=="Y" or imsSummaryUI.mode == "R"}'>
				<c:if test='${not empty imsSummaryUI.imsCollectDocDTOs }'>
				

				<tr>
					<td>
					<table width="100%" border="0" cellspacing="1" rules="none">
						<tr>
						<td class="table_title" ><spring:message code="ims.pcd.summary.052"/></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
				<td>
					<table id ="ccSupDoc" width="100%" border="0" cellspacing="1" class="contenttext"
						background="images/background2.jpg">
						<tr class="contenttextgreen2">
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.summary.053"/></td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.summary.054"/></td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.summary.055"/></td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.summary.063"/></td>
						</tr>

						<c:forEach items="${imsSummaryUI.imsCollectDocDTOs}" var="dto" varStatus="status">
							<tr>
										<td id="fsdoctype${status.index}" colspan="1" style="text-align: center; width: 25%;">
											<c:out value="${dto.docTypeDisplay}"/>
										</td>
										<td id="fswaivereason${status.index}" colspan="1" style="text-align: center; width: 25%;">
										<c:out value="${dto.waiveReason}" default="--" />
<%-- 											<c:choose> --%>
<%-- 											<c:when test="${not empty waiveReasonsPaper[dto.docType]}"> --%>
<%-- 											<form:select id="${dto.docType}" path="allOrdDocAssgnDTOs[${status.index}].waiveReason"> --%>
<%-- 												<form:option value="" label="Select..."/> --%>
<%-- 												<form:options items="${waiveReasonsPaper[dto.docType]}" itemValue="waiveReason" itemLabel="waiveDesc"/> --%>
<%-- 											</form:select> --%>
<%-- 											</c:when>					 --%>
<%-- 											<c:otherwise> --%>
<%-- 												<select id="${dto.docType}"  style="width:200px;" disabled>  --%>
<!-- 													<option selected="selected" value = "">Select...</option> -->
<!-- 												</select> -->
<%-- 											</c:otherwise>			 --%>
<%-- 											</c:choose> --%>
										</td>
										<td id="fscolind${status.index}" colspan="1" style="text-align: center; width: 25%;">
											<c:out value="${dto.collectedInd != 'Y'? 'N': dto.collectedInd}"/>
										</td>
										<td id="fsfsnum${status.index}" colspan="1" style="text-align: center; width: 25%;">
											${dto.faxSerial}
										</td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				</c:if>
<%-- 						<c:if test='${imsSummaryUI.edfRef!="" }'> --%>
						<tr><td>
						<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
						<tbody>
							<tr align="left" height="20px"></tr>
							<tr align="left" height="20px">
								<th align="left" width="250px">EDF Ref</th>
								<td id="edfref">${imsSummaryUI.edfRef}</td>
							</tr>
							</tbody>
						</table>
						</td></tr>
<%-- 						</c:if> --%>
						</c:if>

		<tr><td></td></tr>
		
  
		

		
		
<!-- 		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" >Terms &amp; Conditions</td>
				</tr>
			</table>
			</td>
		</tr> -->
	<!-- 	<tr>
			<td> -->
<!-- 			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr>
				<br>
					<td width="25%">&nbsp;</td>
					<td><label> 
                		<textarea name="textarea2" cols="80" rows="5" alignment="justify">Customer Confirmation and Personal Information Collection Statement: 
I/we acknowledge and confirm that 
(i) I/we have had the opportunity to access to PCCW mobile General Terms and Conditions (and PCCW Fair Usage Policy) via PCCW mobile's website (www.pccwmobile.com) or otherwise obtained a copy thereof from PCCW mobile upon my/our request.  I/we have read the PCCW mobile General Terms and Conditions (and PCCW Fair Usage Policy) , and I/we hereby agree to be bound by them; 
(ii) all information provided herein is true and correct; 
(iii) I am/we are satisfied with the condition of the handset/mobile device provided (if applicable);
(iv) I/we may replace the handset/mobile device within 10 days after signing this Application if there is inherent defect on the handset/mobile device;
(v) I/we understand I am/we are responsible for the risks of the handset/mobile device;
(vi) I/We agree that depending on the service or combination of services subscribed for in this application, the personal data and other information so provided are collected, used and retained by either one or more of the service providers of PCCW Group including Hong Kong Telecommunications (HKT) Limited, PCCW mobile and PCCW Media Limited (as the case may be) in accordance with the requirements in the Personal Data (Privacy) Ordinance and the Privacy Policy Statement which is accessible at http://www.pccw.com/legal/privacy.html which also governs, together with HKT's General Conditions of Service (if applicable), how such personal data and other information are used and to whom they may be disclosed. The main purpose for which the data and other information are used and/or disclosed is for the processing and provision of the subscribed and related services. The data may be disclosed to affiliates, related companies, debt collection agents, third party channel providers or other business partners for provision of the services as well as promotion of different goods and services. (vii) I/We hereby acknowledge that I/we have read and agreed to be bound by all the terms and conditions applicable to the service(s) subscribed including how the personal data may be used and to whom the data may be transferred. I am/We are aware of my/our right to access and correct my/our personal data by contacting the Privacy Compliance Officer by writing to GPO Box 9896 or privacy@pccw.com. A copy of the PCCW Privacy Policy Statement is available at http://www.pccw.com.
                
* In case of BR Customer, the BR Customer shall be responsible for all services consumed by its users.
* Copy of PCCW mobile General Terms and Conditions is available at http://www.pccwmobile.com              
                		</textarea>

                	</label></td>
                	<td width="25%">&nbsp;</td>
				</tr>
				<tr>
                    <td></td>
                    <td> <label> 
                      <input name="checkbox" type="checkbox" value="checkbox"  />

                      </label> <span class="contenttext">I have read and 
                      agree with all the terms and conditions</span> </td>
					<td width="25%"></td>
            	</tr>

<!--			</table> -->
<!--			</td>-->
<!--		</tr>-->






		<tr <c:if test='${imsSummaryUI.isCC == "Y" or ims_direct_sales eq true}'>style="display:none"</c:if>>
			<c:choose>
				<c:when test='${imsSummaryUI.account.payment.payMtdType == "M"}'>
					<c:choose>
						<c:when test='${imsSummaryUI.cashFsPrepay != "Y"}'>
			<td align="left" <c:if test='${imsSummaryUI.waivedPrepay == "Y"}'>style="display:none"</c:if> >
				Pregen account:<form:input id="pregenAcc" path="primaryAcctNo" maxlength="10" onblur="pregenAcct()"/>
			</td>
						</c:when>
					</c:choose>
				</c:when>
			</c:choose>
		</tr>
		
		
		
		
		
		
		<tr>
			<td align="left">
				<c:choose><c:when test='${imsSummaryUI.account.payment.payMtdType == "M"}'>
				<c:choose><c:when test='${imsSummaryUI.cashFsPrepay != "Y"}'>
				<c:if test='${imsSummaryUI.waivedPrepay != "Y" and imsSummaryUI.isCC != "Y" and ims_direct_sales ne true}'>(For Cash Only)</c:if>
				<form:errors path="primaryAcctNo" cssClass="error" />
				</c:when></c:choose>
				</c:when></c:choose>
<%-- 				<form:errors path="printFormClickInd" cssClass="error" /> --%>
				<span id="warning_printFormClickInd" class="error" style="visibility:hidden">
		<spring:message code="ims.pcd.summary.msg.010"/>
		</span>
		
				
				<div id="warning_pregen" class="ui-widget" style="display:none; width:40%;">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_pregen_msg" style="font-weight:bold;">
					</div>
					
					</p>
					</div>
				</div>
			</td>
		</tr>
		
		<c:if test="${ims_direct_sales eq true }">  
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="1" class="contenttext" rules="none">
					<tr>
					<td class="table_title" ><spring:message code="ims.pcd.summary.066"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="1" class="contenttext"> 
						<tr>
							<td width="40%" > 
								<div align="left", style="margin-left:10px;width:200px;"> 	
										<spring:message code="ims.pcd.summary.067"/>
								</div>
							</td>
							<td width="60%" >				
								<form:textarea path="qcCallingTimePeriod" rows="3" cols="40"/>
							</td>
						</tr>	
						<tr>
							<td width="40%" > 
								<div align="left", style="margin-left:10px;width:200px;"> 	
										<spring:message code="ims.pcd.summary.068"/>
								</div>
							</td>
							<td width="60%" >
								<form:select path="waiveQCReason">
								   <form:option value="" label="--- Select ---"/> 
								   <form:options items="${waiveQCReason}" />
								</form:select>		
							</td>
							
							
						</tr>	 
<!-- 						<tr> -->
<!-- 							<td width="40%" >  -->
<!-- 								<div align="left", style="margin-left:10px;width:200px;"> 	 -->
<!-- 										QR Code : -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 							<td width="60%" > -->
<%-- 									<img src="getqrjpg.html?str=${ImsOrder.orderId}1234567"/> --%>
<!-- 							</td> -->
<!-- 						</tr>	 -->
						<tr><td>&nbsp;</td></tr>
					</table>
				</td> 
			</tr>
		</c:if>				
				
		<tr>
		<td align="left">
		<span id="warning_clickDigitalSignButtonInd" class="error" style="visibility:hidden">
				<spring:message code="ims.pcd.summary.msg.008"/>
		</span>
		</td>
		</tr>
		
				
		<tr>
		<td align="left">
		<span id="warning_emptyDigitalSignature" class="error" style="visibility:hidden">
			<spring:message code="ims.pcd.summary.msg.009"/>
		</span>
		</td>
		</tr>
<!-- Celia ipad start		 --> 
		<tr>
<!-- 		<tr> -->
		<td align="left">
		<form:errors path="clickCaptureIpadButtonInd" cssClass="error" />
		</td>
		</tr> 
		
		
		
		<tr>
		<td align="left">
		<span id="warning_printPreviewFormSignedInd" class="error" style="visibility:hidden">
		<spring:message code="ims.pcd.summary.msg.007"/>
		</span>
		</td>
		</tr>
		
		
		<tr>
		<td colspan="2" bgcolor="#FFFFFF">
		<form:errors path="emptySignatureError" cssClass="error" />
		</td>
		</tr>
		
		<tr><td><br></td></tr>
		
       	<tr>
        	<td align="left">
        		<spring:message code="ims.pcd.summary.069"/>
        		<form:select id="cancelSelect" path="cReasonCd">
        			<form:option value=""></form:option>
        			<form:options items="${cancelList}" itemLabel="value" itemValue="key"/>
				</form:select>
       			<a href="#" class="nextbutton" onclick="cancel()"><spring:message code="ims.pcd.summary.082"/></a>
       			&nbsp;&nbsp;&nbsp;
<!--        			<div  -->
<%--        				<c:choose> --%>
<%--        					<c:when test='${imsSummaryUI.idDocType ne "BS" and imsSummaryUI.account.payment.thirdPartyInd ne "Y" or imsSummaryUI.isCC != "Y"}'>style="display:none"</c:when> --%>
<%--        					<c:otherwise>style="display:inline-block;"</c:otherwise> --%>
<%--        				</c:choose>       			 --%>
<!--        			> -->
<!-- 	       			FAX Serial: -->
<%-- 	    			<form:input path="FAXno"/> --%>
<!--     			</div> -->
    		</td>
       	</tr>
       	
       	
       	<tr>
			<td>
			<c:choose>
				<c:when test="${ims_direct_sales eq true}">
					<div id="submit" align="right" >
						<a href="#" id="" class="nextbutton"  onClick="javascript:dummyprintLangPreviewForm();"> <spring:message code="ims.pcd.summary.070"/> </a>
						<a href="#" id="digitalSignatureButton" class="nextbutton" onClick="digitalSignButtonCheck()"><spring:message code="ims.pcd.summary.044"/></a>		
						<a href="#" id="captureDS" class="nextbutton" onclick="CaptureiPad();captureIpadButtonCheck();"  <c:if test="${ims_ds_mismatch_hkid eq false}">style="display:none"</c:if>><spring:message code="ims.pcd.summary.072"/></a>
						<a href="#" id="previewFormsigned" class="nextbutton" onClick="javascript:dummyprintLangPreviewFormSigned();"> <spring:message code="ims.pcd.summary.073"/> </a> 
						<a href="#" class="nextbutton" onclick="signoff()"><spring:message code="ims.pcd.summary.074"/></a>
						<div style="display:none">
							<a href="#" id="digitalSignatureButton" class="nextbutton" onClick="digitalSignButtonCheck()"><spring:message code="ims.pcd.summary.044"/></a>																																	 
							<a href="#" id="captureIPad" class="nextbutton" onclick="CaptureiPad();captureIpadButtonCheck();"><spring:message code="ims.pcd.summary.075"/></a>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div id="submit" align="right" >
						<div 
							<c:choose>
		       					<c:when test='${(imsSummaryUI.isCC=="Y" and imsSummaryUI.mode!="R") or ims_direct_sales eq true}'>style="display:none"</c:when>
		       					<c:otherwise>style="display:inline-block;"</c:otherwise>
		       				</c:choose>
						>
							<a href="#" class="nextbutton" onClick="javascript:dummyprintLangPreviewForm();"> <spring:message code="ims.pcd.summary.076"/> </a>
						</div>
						
						<c:if test="${ims_direct_sales eq true}">
							<a href="#" class="nextbutton" onClick="javascript:dummyprintLangPreviewForm();"><spring:message code="ims.pcd.summary.077"/> </a>
						</c:if>
	
						<a href="#" id="previewFormsigned" class="nextbutton"  onClick="javascript:dummyprintLangPreviewFormSigned();" <c:if test="${ims_direct_sales eq true}">style="display:none"</c:if>><spring:message code="ims.pcd.summary.070"/> </a> 
						
						<div 
							<c:choose>
		       					<c:when test='${imsSummaryUI.isCC=="Y" and imsSummaryUI.mode!="R"}'>style="display:none"</c:when>
		       					<c:otherwise>style="display:inline-block;"</c:otherwise>
		       				</c:choose>
						>  
							<a href="#" id="digitalSignatureButton" class="nextbutton" onClick="digitalSignButtonCheck()"><spring:message code="ims.pcd.summary.044"/></a>																																	 
							<a href="#" id="captureIPad" class="nextbutton" onclick="CaptureiPad();captureIpadButtonCheck();"  <c:if test="${ims_direct_sales eq true}">style="display:none"</c:if>><spring:message code="ims.pcd.summary.075"/></a>				 
							<a href="#" id="captureDS" class="nextbutton" onclick="CaptureiPad();captureIpadButtonCheck();"  <c:if test="${ims_ds_mismatch_hkid eq false}">style="display:none"</c:if>><spring:message code="ims.pcd.summary.072"/></a>
						 
						</div>
	<!--					<a href="#"	class="nextbutton"	onclick="CapturePC();">Capture Proof (upload via PC only)</a>-->
						
					<!--  	Email confirmation by Eric Ng on 2012-11-16 -->
					<!--  	Tony-->
						<c:if test='${imsSummaryUI.isCC=="Y" or imsSummaryUI.mode=="R"}'>
							<c:if test='${not empty imsSummaryUI.imsCollectDocDTOs}'>
								<a id="captureDocSerial" href="#" class="nextbutton"><spring:message code="ims.pcd.summary.078"/></a>
							</c:if>
	<%-- 						<c:if test="${serviceSummary.allowUpdateEdfRef }"> --%>
								<a id="updateEdfRef" href="#" class="nextbutton"><spring:message code="ims.pcd.summary.079"/></a>
	<%-- 						</c:if> --%>
							
						</c:if>
					<!--  		Tony End-->
						<a href="#" class="nextbutton" onclick="signoff()"><spring:message code="ims.pcd.summary.080"/></a>
						
	<!--				prompt select paper mode confirmation dialog box, kinman on 2013-09-23 	-->
					</div>
				</c:otherwise>
			</c:choose>
				
					<div id="paperMode_alert_dialog" title="System Alert" style="display: none;"> 
						<p class="paperModeAlertTips" style="padding-left:5px;" ><spring:message code="ims.pcd.summary.msg.005"/></p>
						
						<div align="left", style="margin-left:150px;width:150px;padding-top:10px;"> 	
								<form:radiobutton id="paperMode1" path="" label="Yes"  onclick="paperModeY()"/>
								<form:radiobutton  id="paperMode2" path="" label="No"  onclick="paperModeN()"/>
						</div>
						
						<div class="pop_content">
          					<div class="pop_button" style="padding: 30px 0px 0px 240px;">
          						<a href="javascript:void(0);" id="paperModeConfirm" class="nextbutton" onClick="paperModeConfirm()"><spring:message code="ims.pcd.summary.081"/></a>
          						<a href="javascript:void(0);" id="paperModeCancel" class="nextbutton"  onClick="paperModeCancel()"><spring:message code="ims.pcd.summary.082"/></a>
          					</div>
						</div>
					</div>
					
					
<!--				prompt [selected digital mode, but without email address] confirmation dialog box, kinman on 2013-09-23 	-->
					<div id="email_alert_dialog" title="System Alert" style="display: none;"> 
						<p class="emailAlertTips" style="padding-left:5px;" ><spring:message code="ims.pcd.summary.msg.006"/></p>
						
						<div align="left", style="margin-left:150px;width:150px;padding-top:10px;"> 	
								<form:radiobutton id="EmailComfirm1" path="" label="Yes" onclick="EmailComfirmY()"/>
								<form:radiobutton  id="EmailComfirm2" path="" label="No" onclick="EmailComfirmN()"/>
						</div>
						
						<div class="pop_content">
          					<div class="pop_button" style="padding: 30px 0px 0px 240px;">
          						<a href="javascript:void(0);" id="email_alert_Confirm" class="nextbutton" onClick="withoutEmailConfirm()"><spring:message code="ims.pcd.summary.081"/></a>
          						<a href="javascript:void(0);" id="email_alert_Cancel" class="nextbutton"  onClick="withoutEmailCancel()"><spring:message code="ims.pcd.summary.082"/></a>
          					</div>
						</div>
					</div>					
					
			
				<form:hidden id="paperModeConfirmInd" path="paperModeConfirmInd"/>
				<form:hidden id="withoutEmailComfirmInd" path="withoutEmailComfirmInd"/>
				<form:hidden id="selectedDisModeInd" path="selectedDisModeInd"/>
				
				<form:hidden id="printFormClickInd" path="printFormClickInd"/>
				<form:hidden id="printPreviewFormSignedInd" path="printPreviewFormSignedInd"/>
				<form:hidden id="clickDigitalSignButtonInd" path="clickDigitalSignButtonInd"/>
				<form:hidden id="clickCaptureIpadButtonInd" path="clickCaptureIpadButtonInd"/>
				<form:hidden id="checkDigSignatureInd" path="checkDigSignatureInd"/>
				<form:hidden id="noSupportingDoc" path="noSupportingDoc"/>
				
				<form:hidden id="submitTag" path="submitTag"/>
				<form:hidden id="orderId" path="orderId"/>
				<form:hidden path="isCC"/>
				<form:hidden path="isPT"/>
				<input type="hidden" name="currentView" value="imsSummary"/>
			</td>
		</tr>
		</table>	     
      </td>
  </tr>
</table>
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>