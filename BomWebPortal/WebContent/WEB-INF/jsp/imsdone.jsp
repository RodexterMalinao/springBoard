<%if ("Y".equals(request.getParameter("dM"))){%>
	<%@ include file="/WEB-INF/jsp/dialogheader.jsp"%>
<%}else{%>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%} %>

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
<script language="Javascript">	
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

	function formSubmit(){
		//window.location = "basketdetails.html";
		//document.summary.submit();
	}

	function imsDsSubmit(){


		if(document.getElementById('shopCode')==null || document.getElementById('shopCode').value==''){
// 			alert("Please enter the Shop Code");
			alert('<spring:message code="ims.pcd.done.msg.001"/>');
			return;
		}
		
		else if(document.getElementById('transactionCd')==null || document.getElementById('transactionCd').value==''){
// 			alert("Please enter the Transaction Code");
			alert('<spring:message code="ims.pcd.done.msg.002"/>');
			return;
		}
		
		else if(document.getElementById('serialNum')!=null && "${imsDoneUI.appntUI.bookNotAllowed}"!="Y") {
			if(document.getElementById('serialNum')==null || document.getElementById('serialNum').value==''){
// 				alert("please complete the appointment booking");
				alert('<spring:message code="ims.pcd.done.msg.003"/>');
			}else{
				if(!checkSrdOver()){
					if(!checkSrdUnder()){
						if(		isDoorKnock 
								&& $("[name='waiveCoolingOffPeriod']:checked").val()=='N' 
								&& checkDaysDelta(appdate, document.getElementById('targetInstallDate').value) < 7 ){
// 							alert ("appointment date must be at least T+7");
							alert ("<spring:message code="ims.pcd.done.msg.004"/>");
						}
						else if("${imsDoneUI.appntUI.preInstOrder}"=="Y"){
							if(document.getElementById('commdate')!=null){
								if(document.getElementById('commdate').value==''){
// 									alert("please select target commencement date");
									alert ("<spring:message code="ims.pcd.done.msg.005"/>");
								}else{
									document.getElementById('targetCommDate').value =
										document.getElementById('commdate').value;	
									document.welcomeForm.submit();
								}
							}else{	
								document.welcomeForm.submit();
							}
						}
						else document.welcomeForm.submit();
					}
					else
					{
// 						alert("appointment date must not be earlier than estimated, please make a new booking");
						alert("<spring:message code="ims.pcd.summary.msg.006"/>");
					}								
				}else{
// 					alert("appointment date is over, please make a new booking");
					alert("<spring:message code="ims.pcd.summary.msg.007"/>");
				}							
			}
		} 
		
		else if("${imsDoneUI.appntUI.preInstOrder}"=="Y"){
			if(document.getElementById('commdate')!=null){
				if(document.getElementById('commdate').value==''){
					alert("please select target commencement date");
					alert("<spring:message code="ims.pcd.done.msg.005"/>");
				}else{
					document.getElementById('targetCommDate').value =
						document.getElementById('commdate').value;
					document.welcomeForm.submit();
				}
			}else{	
				document.welcomeForm.submit();
			}
		}
		
		else {
			document.welcomeForm.submit();
		}
	}
	
	function cancel(){
		var cancel = document.getElementById('cancelSelect').value;
		if (cancel == ""){
// 			alert("Please select a cancel reason code!");
			alert("<spring:message code="ims.pcd.done.msg.009"/>");
		}else{
			//submitShowLoading();
			$("#submitTag").val("C");
			document.welcomeForm.submit();
		}
	}
	
	function CapturePC(){
		var url = "imsdoccapture.html?orderId=${imsDoneUI.orderId}&disMode=${imsDoneUI.disMode}&SignOffed=true";
		window.open(url, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
	}
	
	function CaptureiPad(){
		var link = "<%=scheme %>://${imsDoneUI.orderId}/${salesUserDto.username}/${currentSessionId}/<%=ipadVersion%>";
		var url = "imsdoccapture.html?orderId=${imsDoneUI.orderId}&SignOffed=true&disMode=" + $('input[name=disMode]:checked').val()+"&iPadLink="+link;
		window.open(url);
	}
	
	//kinman
	function dummyprintLangForm(){
		if(BrowserDetect.OS == "iPad" || BrowserDetect.OS == "iPhone/iPod" || BrowserDetect.OS == "Linux") {
			if (document.getElementById("langPreference").value=='ENG'){
				window.open('imsreport.html?pdfLang=en', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			}else {
				window.open('imsreport.html?pdfLang=zh', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			} 
		}else{
		if (document.getElementById("langPreference").value=='ENG'){
			window.location.replace('imsreport.html?pdfLang=en', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			}else {
			window.location.replace('imsreport.html?pdfLang=zh', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			} 
		}
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

<form:form name="welcomeForm" method="POST" commandName="imsDoneUI">
<input type="hidden" name="IMS_UID" value="<%= request.getAttribute("IMS_UID") %>" />
<script>
	$(document).ready(function(){
		BrowserDetect.init();
	      
		if($("#cancelReason").val() != null && $("#cancelReason").val() != "" && $("#submitTag").val() == "C"
			&& $("#orderId").val() != null && $("#orderId").val() != ""){
			$(":input").attr("disabled", true);
			alert("Order is cancelled. Order ID: " + $("#orderId").val());
			window.location = "welcome.html";
		}else if($("#cancelReason").val() != null && $("#cancelReason").val() != "" && $("#submitTag").val() == "C"){
			$(":input").attr("disabled", true);
		}
		
		});
</script> 

<br>
<table width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="1" rules="none">
		<tr>
			<td class="table_title" ><spring:message code="ims.pcd.done.076"/></td>
		</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" class="contenttext">
		<tr>
			<td class="contenttextgreen" height="40">
				<c:choose>
					<c:when test='${imsDoneUI.orderStatus == "10" 
					or imsDoneUI.orderStatus == "20"
					or imsDoneUI.orderStatus == "21"}'>
						<c:choose>
							<c:when test='${imsDoneUI.orderStatus == "10"}'><spring:message code="ims.orderStatus.10"></spring:message>&nbsp;-&nbsp;</c:when>
							<c:when test='${imsDoneUI.orderStatus == "20"}'><spring:message code="ims.orderStatus.20"></spring:message>&nbsp;-&nbsp;</c:when>
							<c:when test='${imsDoneUI.orderStatus == "21"}'><spring:message code="ims.orderStatus.21"></spring:message>&nbsp;-&nbsp;</c:when>
						</c:choose>
						<c:choose>
							<c:when test='${imsDoneUI.reasonCd == "C001"}'><spring:message code="ims.orderCancelCode.C001"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "C002"}'><spring:message code="ims.orderCancelCode.C002"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S001"}'><spring:message code="ims.orderSuspendCode.S001"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S002"}'><spring:message code="ims.orderSuspendCode.S002"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S003"}'><spring:message code="ims.orderSuspendCode.S003"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S000"}'><spring:message code="ims.orderSuspendCode.S000"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S101"}'><spring:message code="ims.orderSuspendCode.S101"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S102"}'><spring:message code="ims.orderSuspendCode.S102"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S103"}'><spring:message code="ims.orderSuspendCode.S103"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S104"}'><spring:message code="ims.orderSuspendCode.S104"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S105"}'><spring:message code="ims.orderSuspendCode.S105"></spring:message></c:when>
							<c:when test='${imsDoneUI.reasonCd == "S106"}'><spring:message code="ims.orderSuspendCode.S106"></spring:message></c:when>
						</c:choose>
					</c:when>
					<c:otherwise>
					<spring:message code="ims.pcd.done.002"/>
					</c:otherwise>
				</c:choose>
				<br>
				<spring:message code="ims.pcd.done.003"/> ${imsDoneUI.orderId}
				<c:if test='${not empty imsDoneUI.signOffDate }'>	
					<br/>		
				<spring:message code="ims.pcd.done.004"/> ${imsDoneUI.signOffDate}
				</c:if>
				<c:if test='${not empty imsDoneUI.submitDate }'>
					<br/>		
				<spring:message code="ims.pcd.done.005"/> ${imsDoneUI.submitDate} 
				</c:if>
				<c:if test='${not empty imsDoneUI.callDate}'>
					<br/>
				<spring:message code="ims.pcd.done.006"/> ${imsDoneUI.callDate}
				</c:if>
				
				<c:if test='${not empty imsDoneUI.callTime}'>
					<br/>
				<spring:message code="ims.pcd.done.007"/> ${imsDoneUI.callTime}
				</c:if>
				
				<c:if test='${not empty imsDoneUI.positionNo}'>
					<br/>
				<spring:message code="ims.pcd.done.008"/> ${imsDoneUI.positionNo}
				</c:if>
				
				<c:if test='${not empty imsDoneUI.FAXno}'>
					<br/>
				<spring:message code="ims.pcd.done.009"/>	 ${imsDoneUI.FAXno}
				</c:if>
				
								
				<c:if test='${not empty imsDoneUI.l1ordnum}'>
					<br/>
					<br/>
				<spring:message code="ims.pcd.done.010"/>	 ${imsDoneUI.l1ordnum} 
				</c:if> 
				
			</td>
		</tr>
		<tr>
			
			<c:choose>
				<c:when test='${imsDoneUI.disMode == "E"} '>
					<c:choose>
						<c:when test='${imsDoneUI.esigEmailAddr != null}'>
				<c:choose>
					<c:when test='${emailReqResult == "SUCCESS"}'>
						<td class="contenttextgreen"><spring:message code="ims.pcd.done.011"/></td>
					</c:when>
					<c:when test='${emailReqResult == null}'>
						
					</c:when>
					<c:otherwise>
					<td  class="contenttext_red"><b><spring:message code="ims.pcd.done.012"/>			
						</br>
						<b><c:out value="${emailReqResultMsg}"></c:out></b>
	<%-- 					<c:choose> --%>
	<%-- 						<c:when test='${emailReqResult == "FAIL"}'> --%>
	<!-- 						<b>Please resend email by using "Resent email", or print the application form to customer if applicable.</b> -->
	<%-- 						</c:when> --%>
	<%-- 						<c:when test='${emailReqResult == "INVALID_EMAIL_ADDR"}'> --%>
	<!-- 						<b>Error: invalid email address</b> -->
	<%-- 						</c:when> --%>
	<%-- 						<c:when test='${emailReqResult == "NOT_ESIGN_ORDER"}'> --%>
	<!-- 						<b>Error: not e-signature order</b> -->
	<%-- 						</c:when> --%>
	<%-- 						<c:when test='${emailReqResult == "INVALID_ENCRYPT_PASSWORD"}'> --%>
	<!-- 						<b>Error: invalid PDF password</b> -->
	<%-- 						</c:when> --%>
	<%-- 						<c:when test='${emailReqResult == "MAIL_SEND_EXCEPTION"}'> --%>
	<!-- 						<b>Error: connection failed</b> -->
	<%-- 						</c:when> --%>
	<%-- 						<c:when test='${emailReqResult == "ATTACHMENT_NOT_FOUND"}'> --%>
	<!-- 						<b>Error: attachment not found</b> -->
	<%-- 						</c:when> --%>
	<%-- 					</c:choose>					 --%>
						</td>
					</c:otherwise>
				</c:choose>
				</c:when>
			</c:choose>
			</c:when>
			</c:choose>
			
			
		
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.done.013"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<!-- Terrence Part -->
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<c:choose>
					<c:when test='${imsDoneUI.idDocType != "BS"}'>
						<tr align="left" height="20px"><th align="left" width="250px"><spring:message code="ims.pcd.done.077"/></th><td>${imsDoneUI.title}</td></tr>
						<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.done.014"/></th><td>${imsDoneUI.lastName} ${imsDoneUI.firstName}</td></tr>
					</c:when>
					<c:otherwise>
						<tr align="left" height="20px"><th align="left" width="250px"><spring:message code="ims.pcd.done.015"/></th><td>${imsDoneUI.companyName}</td></tr>
					</c:otherwise>
				</c:choose>
				
				
				<tr align="left" height="20px">
					<th align="left">
						<c:choose>
							<c:when test='${imsDoneUI.idDocType == "PASS"}'>
								PASSPORT:
							</c:when>
							<c:when test='${imsDoneUI.idDocType == "BS"}'>
								HKBR:
							</c:when>
							<c:otherwise>
								${imsDoneUI.idDocType}:
							</c:otherwise>
						</c:choose>
					</th>
					<td>${imsDoneUI.idDocNum}</td>
				</tr>
				<c:if test='${imsDoneUI.idDocType != "BS"}'>
					<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.done.016"/></th><td>${imsDoneUI.dobStr}</td></tr>
				</c:if>
				<c:choose><c:when test='${imsDoneUI.contact.contactPhone != null}'>
				<c:choose><c:when test='${imsDoneUI.contact.contactPhone != ""}'>
				<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.done.017"/></th><td>${imsDoneUI.contact.contactPhone}</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				<c:choose><c:when test='${imsDoneUI.contact.otherPhone != null}'>
				<c:choose><c:when test='${imsDoneUI.contact.otherPhone != ""}'>
				<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.done.018"/></th><td>${imsDoneUI.contact.otherPhone}</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				<c:choose><c:when test='${imsDoneUI.fixedLineNo != null}'>
				<c:choose><c:when test='${imsDoneUI.fixedLineNo != ""}'>
				<tr align="left" height="20px"><th align="left"><spring:message code="ims.pcd.done.019"/></th><td>${imsDoneUI.fixedLineNo}(PCCW)</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				
				
				<!-- NowID -->
				<c:if test='${imsDoneUI.isRegNowId == "Y" && imsDoneUI.nowId != "" && imsDoneUI.nowId != null}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.done.020"/></th><td>${imsDoneUI.nowId}</td></tr>
				</c:if>
				
				
				
				
				
				
				<!-- Gary -->
				<c:choose><c:when test='${imsDoneUI.csPortalInd == "Y" && imsDoneUI.theClubInd == "Y"}'>
				<c:if test='${imsDoneUI.csPortalLogin != null && imsDoneUI.csPortalLogin != "" && imsDoneUI.csPortalMobile != null && imsDoneUI.csPortalMobile != ""}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.done.021"/></th><td>${imsDoneUI.csPortalLogin}<c:choose><c:when test='${imsDoneUI.noMobileInd eq "Y" || imsDoneUI.csPortalMobile eq "00000000"}'></c:when><c:otherwise>/${imsDoneUI.csPortalMobile}</c:otherwise></c:choose></td></tr>
				<c:if test='${imsDoneUI.csPortalTheClubOptOutInd == "Y"}'> 
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.done.022"/></th><td><input type="checkbox" id='optoutMyHktTheClub' disabled checked /> All</td></tr>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.done.023"/></th><td><c:if test='${imsDoneUI.csPortalTheClubOptOutInd == "Y"}'>${imsDoneUI.clubOptOutReasonDesc}<c:if test='${imsDoneUI.optoutTheClubRmk != null && imsDoneUI.optoutTheClubRmk != ""}'> - ${imsDoneUI.optoutTheClubRmk}</c:if></c:if></td></tr>
				</c:if>
				</c:if> 
				</c:when>
				<c:when test='${imsDoneUI.csPortalInd == "Y"}'>
				<c:if test='${imsDoneUI.csPortalLogin != null && imsDoneUI.csPortalLogin != "" && imsDoneUI.csPortalMobile != null && imsDoneUI.csPortalMobile != ""}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.done.024"/></th><td>${imsDoneUI.csPortalLogin}/${imsDoneUI.csPortalMobile}</td></tr>
				<c:if test='${imsDoneUI.csPortalOptOutInd == "Y"}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.done.025"/></th><td><input type="checkbox" id='optoutMyHkt' disabled checked /> All</td></tr>
				</c:if>
				</c:if>
				</c:when>
				<c:when test='${imsDoneUI.theClubInd == "Y"}'>
				<c:if test='${imsDoneUI.theClubLogin != null && imsDoneUI.theClubLogin != "" && imsDoneUI.theClubMoblie != null && imsDoneUI.theClubMoblie != ""}'>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.done.026"/></th><td>${imsDoneUI.theClubLogin}<c:choose><c:when test='${imsDoneUI.noMobileInd == "Y" || imsDoneUI.theClubMoblie eq "00000000"}'></c:when><c:otherwise>/${imsDoneUI.theClubMoblie}</c:otherwise></c:choose></td></tr>
				<c:if test='${imsDoneUI.theClubOptOutInd == "Y"}'> 
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.done.027"/></th><td><input type="checkbox" id='optoutTheClub' disabled checked /> All</td></tr>
				<tr align="left" height="20px"><th align="left" width="400"><spring:message code="ims.pcd.done.028"/></th><td><c:if test='${imsDoneUI.theClubOptOutInd == "Y"}'>${imsDoneUI.clubOptOutReasonDesc}<c:if test='${imsDoneUI.optoutTheClubRmk != null && imsDoneUI.optoutTheClubRmk != ""}'> - ${imsDoneUI.optoutTheClubRmk}</c:if></c:if></td></tr>
				</c:if>
				</c:if> 
				</c:when>
				</c:choose>
				
				<c:if test='${imsDoneUI.careCashInd != null}'>
					<c:choose>
					<c:when test='${imsDoneUI.careCashInd == "I"}'>					
						<tr align="left" height="20px">
						<th align="left" width="400"><spring:message code="ims.pcd.done.029"/></th>
						<td><c:if test='${imsDoneUI.careCashOptOutInd == "O"}'><spring:message code="ims.pcd.done.030"/></c:if></td>
						</tr>				
					</c:when>
					<c:when test='${imsDoneUI.careCashInd == "O"}'>
						<tr align="left" height="20px">
						<th align="left" width="400"><spring:message code="ims.pcd.done.031"/></th>
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
				<td class="table_title" ><spring:message code="ims.pcd.done.032"/></td>
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
					<td class="table_title" ><spring:message code="ims.pcd.done.033"/></td>
					<td class="table_title"><spring:message code="ims.pcd.done.034"/></td>
				</tr>
				<tr valign="top">
					<td><c:if test='${imsDoneUI.basketDtl.imagePath != null}'>
						<img src="basket/${imsDoneUI.basketDtl.imagePath}" width="148">
					</c:if></td>
					<td>
						<div class="basket_title">
						<c:if test='${imsDoneUI.basketDtl.bandwidth != null}'>
							${imsDoneUI.basketDtl.bandwidth_desc}
						</c:if>
						${imsDoneUI.basketDtl.basketTitle}</div><br>
						<div>
						<dir>${imsDoneUI.servPlanDto.coreServiceDetail.progItem.itemDetails}</dir>
						</div>
						<div class="tableOrangetext"><spring:message code="ims.pcd.done.035"/> ${imsDoneUI.warrPeriod} <spring:message code="ims.pcd.done.035a"/><br>
						</div>
					</td>
					<td class="BGgreen2" >${imsDoneUI.servPlanDto.coreServiceDetail.progItem.itemMthFix}</td>
					<td class="BGgreen2" >${imsDoneUI.servPlanDto.coreServiceDetail.progItem.itemMth2Mth}</td>
				</tr>
				<c:forEach items="${imsDoneUI.servPlanDto.coreServiceDetail.bvasMandItemList}" var="bvasMandItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${bvasMandItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${bvasMandItemList.itemMthFix}</td>
					<td class="BGgreen2" >${bvasMandItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<c:forEach items="${imsDoneUI.servPlanDto.coreServiceDetail.bvasNonMItemList}" var="bvasNonMItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${bvasNonMItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${bvasNonMItemList.itemMthFix}</td>
					<td class="BGgreen2" >${bvasNonMItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<c:forEach items="${imsDoneUI.servPlanDto.giftList}" var="giftList" varStatus="status">
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
				<c:forEach items="${imsDoneUI.servPlanDto.optServiceDetail.optPremItemList}" var="optPremItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${optPremItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${optPremItemList.itemMthFix}</td>
					<td class="BGgreen2" >${optPremItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<!-- <c:forEach items="${imsDoneUI.servPlanDto.optServiceDetail.wlBbItemList}" var="wlBbItemList" varStatus="status">
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
				<c:forEach items="${imsDoneUI.servPlanDto.optServiceDetail.antiVirItemList}" var="antiVirItemList" varStatus="status">
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
				<c:forEach items="${imsDoneUI.servPlanDto.optServiceDetail.optServItemList}" var="optServItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${optServItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${optServItemList.itemMthFix}</td>
					<td class="BGgreen2" >${optServItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<c:forEach items="${imsDoneUI.servPlanDto.optServiceDetail.mediaItemList}" var="mediaItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${mediaItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${mediaItemList.itemMthFix}</td>
					<td class="BGgreen2" >${mediaItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<c:if test='${imsDoneUI.ntvPgmItemList != null}'>
					<tr><td>&nbsp;</td><td class="item_title_rp"><br><spring:message code="ims.pcd.done.078"/></td>
						<td class="BGgreen2" ></td>
						<td class="BGgreen2" ></td>
					</tr>
				</c:if>
				<c:forEach items="${imsDoneUI.servPlanDto.ntvServiceDetail.ntvFreeItemList}" var="ntvFreeItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td>
						<span class="tableOrangetext">${ntvFreeItemList.itemTitle}</span>
						<!-- <dir><span class="item_detail">
						${ntvFreeItemList.itemDetails}
						</span></dir> -->
						<dir><span class="item_detail">
						<c:forEach items="${imsDoneUI.servPlanDto.ntvServiceDetail.ntvFreeSPChannelList}" var="ntvFreeSPChannelList" varStatus="status">
						${ntvFreeSPChannelList.channelDesc}
						</c:forEach>
						</span></dir>
					</td>
					
					<td class="BGgreen2" >${ntvFreeItemList.itemMthFix}</td>
					<td class="BGgreen2" >${ntvFreeItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<c:forEach items="${imsDoneUI.servPlanDto.ntvServiceDetail.ntvFreeEPChannelList}" var="ntvFreeEPChannelList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${ntvFreeEPChannelList.channelDesc}
					</td>
					
					<td class="BGgreen2" ></td>
					<td class="BGgreen2" ></td>
					<tr></tr>
				</tr>
				</c:forEach>
				<c:if test='${fn:length(imsDoneUI.servPlanDto.ntvServiceDetail.ntvFreeHDChannelList) != 0}'>
				<c:set var="chGroupDesc" value=""/>
				<c:set var="chGroupDescStart" value=""/>
				<tr valign="top">
				<td>&nbsp;</td>
				<td class="tableOrangetext">	
				<c:forEach items="${imsDoneUI.servPlanDto.ntvServiceDetail.ntvFreeHDChannelList}" var="ntvFreeHDChannelList" varStatus="status">
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
				
				<c:if test="${imsDoneUI.newtvpricingind eq 'Y'}"> <!-- martin -->
					<!-- FTA Campaign -->
						<c:set var="newtvtitle" value="0" />
						<c:forEach var="ftaPack" items="${imsDoneUI.nowTVAddUI.ftaCampaign.tvPacks}" varStatus="i">
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
							<td class="BGgreen2">${imsDoneUI.nowTVAddUI.ftaCampaign.fix_term_rate}</td>
							<td class="BGgreen2">${imsDoneUI.nowTVAddUI.ftaCampaign.mth_to_mth_rate}</td>
						</tr>
						</c:if>
						</c:forEach>
					<!-- FTA Campaign (end)-->
					<!-- Hard Campaign -->
						<c:set var="newtvtitle" value="0" />
						<c:forEach var="hardPack" items="${imsDoneUI.nowTVAddUI.hardCampaign.tvPacks}" varStatus="i">
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
									<c:if test="${newtvtitle eq imsDoneUI.nowTVAddUI.hardCampaign.numOfSelectedPack}">
									<c:forEach var="topUp" items="${imsDoneUI.nowTVAddUI.hardCampaign.topUps}" varStatus="k">
									<li class="summarycontenttext" style="color:#0099ff">
										<c:if test="${topUp.selected}">
											<c:if test="${newtvtitle eq imsDoneUI.nowTVAddUI.hardCampaign.numOfSelectedPack}">
<!-- 												Gift &amp; Premium: -->
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
							<c:if test="${newtvtitle eq 1}">
							<td rowspan="${imsDoneUI.nowTVAddUI.hardCampaign.numOfSelectedPack}" class="BGgreen2">&nbsp;</td>
							<td rowspan="${imsDoneUI.nowTVAddUI.hardCampaign.numOfSelectedPack}" class="BGgreen2">&nbsp;</td>
							</c:if>
						</tr>
						</c:if>
						</c:forEach>
						
						<!-- Hard Campaign (end)-->
						<!-- Pay Bundle -->
						<c:set var="newtvtitle" value="0" />
						<c:forEach var="payTv" items="${imsDoneUI.nowTVAddUI.payTvCampaign}" varStatus="i">
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
													<c:if test="${imsDoneUI.nowTVAddUI.ntvConnType >= channel.readRight}">
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
					</c:if>
				<c:forEach items="${imsDoneUI.servPlanDto.ntvServiceDetail.ntvPayItemList}" var="ntvPayItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<c:choose><c:when test='${ntvPayItemList.itemTvType == "SD"}'>
					<td>
						<span class="tableOrangetext">${ntvPayItemList.itemTitle}</span>
						<c:set var="parentCh" value=""/>
						<ul>
						<c:forEach items="${imsDoneUI.servPlanDto.ntvServiceDetail.ntvPayChannelList}" var="ntvPayChannelList" varStatus="status">
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
						<%-- <c:set var="parentCh" value=""/>
						<ul>
						<c:forEach items="${imsDoneUI.servPlanDto.ntvServiceDetail.ntvPayChannelList}" var="ntvPayChannelList" varStatus="status">
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
						</ul> --%>
						</td>
					</c:when></c:choose>
					<td class="BGgreen2" >${ntvPayItemList.itemMthFix}</td>
					<td class="BGgreen2" >${ntvPayItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<c:forEach items="${imsDoneUI.servPlanDto.ntvServiceDetail.ntvOtherItemList}" var="ntvOtherItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td class="tableOrangetext">
						${ntvOtherItemList.itemTitle}
					</td>
					
					<td class="BGgreen2" >${ntvOtherItemList.itemMthFix}</td>
					<td class="BGgreen2" >${ntvOtherItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<!-- <tr><td colspan="4"/><hr/></tr>
				<tr valign="top">
					<td width="60%" class="black_text_16" colspan="2">Total:</td>
					<td class="BGgreen2" >$${imsDoneUI.totalRecurrentAmt}</td>
					<td class="BGgreen2" >$${imsDoneUI.totalMthToMthRate}</td>
				</tr> -->
				<c:if test='${ImsOrder.HDDPurchased eq true}'>
				<tr valign="top">
					<td>&nbsp;</td>
					<td>
				<spring:message code="ims.pcd.done.036"/>	
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
				<td class="table_title" ><spring:message code="ims.pcd.done.079"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<!-- Terrence Part -->
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px"><spring:message code="ims.pcd.done.037"/></th><td>
					<c:choose><c:when test='${imsDoneUI.installAddress.unitNo == null}'></c:when><c:when test='${imsDoneUI.installAddress.unitNo == ""}'></c:when><c:otherwise>Flat </c:otherwise></c:choose>
					${imsDoneUI.installAddress.unitNo}<c:choose><c:when test='${imsDoneUI.installAddress.unitNo == null}'></c:when><c:when test='${imsDoneUI.installAddress.unitNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					Floor ${imsDoneUI.installAddress.floorNo}, ${imsDoneUI.installAddress.hiLotNo}<c:choose><c:when test='${imsDoneUI.installAddress.hiLotNo == null}'></c:when><c:when test='${imsDoneUI.installAddress.hiLotNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsDoneUI.installAddress.buildNo}<c:choose><c:when test='${imsDoneUI.installAddress.buildNo == null}'></c:when><c:when test='${imsDoneUI.installAddress.buildNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsDoneUI.installAddress.strNo} ${imsDoneUI.installAddress.strName} ${imsDoneUI.installAddress.strCatDesc}<c:choose><c:when test='${imsDoneUI.installAddress.strName == null}'></c:when><c:when test='${imsDoneUI.installAddress.strName == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsDoneUI.installAddress.sectDesc}<c:choose><c:when test='${imsDoneUI.installAddress.sectDesc == null}'></c:when><c:when test='${imsDoneUI.installAddress.sectDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsDoneUI.installAddress.distDesc}<c:choose><c:when test='${imsDoneUI.installAddress.distDesc == null}'></c:when><c:when test='${imsDoneUI.installAddress.distDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsDoneUI.installAddress.areaDesc}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.done.080"/></th><td><c:choose>
																	<c:when test='${imsDoneUI.billingAddress != null}'>
																		<c:choose><c:when test='${imsDoneUI.billingAddress.unitNo == null}'></c:when><c:when test='${imsDoneUI.billingAddress.unitNo == ""}'></c:when><c:otherwise>Flat </c:otherwise></c:choose>
																		${imsDoneUI.billingAddress.unitNo}<c:choose><c:when test='${imsDoneUI.billingAddress.unitNo == null}'></c:when><c:when test='${imsDoneUI.billingAddress.unitNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		Floor ${imsDoneUI.billingAddress.floorNo}, ${imsDoneUI.billingAddress.hiLotNo}<c:choose><c:when test='${imsDoneUI.billingAddress.hiLotNo == null}'></c:when><c:when test='${imsDoneUI.billingAddress.hiLotNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.billingAddress.buildNo}<c:choose><c:when test='${imsDoneUI.billingAddress.buildNo == null}'></c:when><c:when test='${imsDoneUI.billingAddress.buildNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.billingAddress.strNo} ${imsDoneUI.billingAddress.strName} ${imsDoneUI.billingAddress.strCatDesc}<c:choose><c:when test='${imsDoneUI.billingAddress.strName == null}'></c:when><c:when test='${imsDoneUI.billingAddress.strName == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.billingAddress.sectDesc}<c:choose><c:when test='${imsDoneUI.billingAddress.sectDesc == null}'></c:when><c:when test='${imsDoneUI.billingAddress.sectDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.billingAddress.distDesc}<c:choose><c:when test='${imsDoneUI.billingAddress.distDesc == null}'></c:when><c:when test='${imsDoneUI.billingAddress.distDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.billingAddress.areaDesc}
																	</c:when>
																	<c:when test='${imsDoneUI.billingAddress == null}'>
																		<c:choose><c:when test='${imsDoneUI.installAddress.unitNo == null}'></c:when><c:when test='${imsDoneUI.installAddress.unitNo == ""}'></c:when><c:otherwise> </c:otherwise></c:choose>
																		${imsDoneUI.installAddress.unitNo}<c:choose><c:when test='${imsDoneUI.installAddress.unitNo == null}'></c:when><c:when test='${imsDoneUI.installAddress.unitNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		Floor ${imsDoneUI.installAddress.floorNo}, ${imsDoneUI.installAddress.hiLotNo}<c:choose><c:when test='${imsDoneUI.installAddress.hiLotNo == null}'></c:when><c:when test='${imsDoneUI.installAddress.hiLotNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.installAddress.buildNo}<c:choose><c:when test='${imsDoneUI.installAddress.buildNo == null}'></c:when><c:when test='${imsDoneUI.installAddress.buildNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.installAddress.strNo} ${imsDoneUI.installAddress.strName} ${imsDoneUI.installAddress.strCatDesc}<c:choose><c:when test='${imsDoneUI.installAddress.strName == null}'></c:when><c:when test='${imsDoneUI.installAddress.strName == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.installAddress.sectDesc}<c:choose><c:when test='${imsDoneUI.installAddress.sectDesc == null}'></c:when><c:when test='${imsDoneUI.installAddress.sectDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.installAddress.distDesc}<c:choose><c:when test='${imsDoneUI.installAddress.distDesc == null}'></c:when><c:when test='${imsDoneUI.installAddress.distDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsDoneUI.installAddress.areaDesc}
																	</c:when>
																  </c:choose></td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.done.038"/></th><td>${imsDoneUI.loginId}@netvigator.com</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.done.039"/></th><td>						
						<c:choose>						
							<c:when test='${imsDoneUI.noBooking == "Y"}'>
								${imsDoneUI.appntStartDateDisplay}
							</c:when>
							<c:otherwise>
								${imsDoneUI.appntStartDateDisplay} - ${imsDoneUI.appntEndDateDisplay}	
							</c:otherwise>
						</c:choose>						
					</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.done.040"/></th><td>${imsDoneUI.appDate}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left"><spring:message code="ims.pcd.done.041"/><br>
						<c:choose>						
							<c:when test='${ImsOrder.preUseInd == "Y"}'>
							<spring:message code="ims.pcd.done.081"/>
							</c:when>
							<c:otherwise>
								<spring:message code="ims.pcd.done.082"/>
							</c:otherwise>
						</c:choose>
					</th><td>${imsDoneUI.targetCommDate}</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.done.042"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px"><spring:message code="ims.pcd.done.042"/>:</th><td>
					<c:choose><c:when test='${imsDoneUI.account.payment.payMtdType == "M"}'><spring:message code="ims.pcd.done.083"/></c:when>
							<c:when test='${imsDoneUI.account.payment.payMtdType == "C"}'><spring:message code="ims.pcd.done.084"/></c:when>
					</c:choose>
									<c:choose>
										<c:when test='${imsDoneUI.account.payment.payMtdType == "C"}'>
															<c:choose>
																<c:when test='${imsDoneUI.account.payment.thirdPartyInd == "Y"}'>
																	<spring:message code="ims.pcd.done.085"/>(Third Party)
																</c:when>
																<c:otherwise>
																	<spring:message code="ims.pcd.done.086"/>
																</c:otherwise>
															</c:choose>
										</c:when>
										<c:otherwise>
										
										</c:otherwise>
									</c:choose></td>
				</tr>
				<c:choose>
					<c:when test='${imsDoneUI.account.payment.payMtdType == "C"}'>
						<tr align="left" height="20px">
							<th align="left"><spring:message code="ims.pcd.done.087"/></th>
							<c:choose><c:when test='${imsDoneUI.account.payment.ccType == "V"}'>
								<td>VISA</td>
							</c:when>
							<c:when test='${imsDoneUI.account.payment.ccType == "M"}'>
								<td>MASTER CARD</td>
							</c:when>
							<c:when test='${imsDoneUI.account.payment.ccType == "A"}'>
								<td>American Express</td>
							</c:when></c:choose></tr>
						<tr align="left" height="20px">
							<th align="left">
								<spring:message code="ims.pcd.done.043"/></th><td>${imsDoneUI.account.payment.ccNum}</td></tr>
						<c:choose>
							<c:when test='${imsDoneUI.account.payment.thirdPartyInd == "Y" and not empty imsDoneUI.account.payment.ccIdDocNo}'>
								<tr align="left" height="20px">
									<th align="left"><spring:message code="ims.pcd.done.044"/></th><td>
									<c:choose><c:when test='${imsDoneUI.account.payment.ccIdDocType == "PASS"}'>
									PASSPORT</c:when>
									<c:when test='${imsDoneUI.account.payment.ccIdDocType == "BS"}'>
									HKBR</c:when>
									<c:otherwise>
									${imsDoneUI.account.payment.ccIdDocType}
									</c:otherwise></c:choose>
									</td></tr>
								<tr align="left" height="20px">
									<th align="left">
									<spring:message code="ims.pcd.done.045"/>	</th><td>${imsDoneUI.account.payment.ccIdDocNo}</td></tr>
							</c:when>
							<c:otherwise>
							
							</c:otherwise>
						</c:choose>
						<tr align="left" height="20px">
							<th align="left">
							<spring:message code="ims.pcd.done.046"/>	<br>(in English)</th><td>${imsDoneUI.account.payment.ccHoldName}
									</td>
						</tr>
						<tr align="left" height="20px">
							<th align="left">
							<spring:message code="ims.pcd.done.047"/></th><td>${imsDoneUI.account.payment.ccExpDate}</td></tr>
						<tr align="left" height="20px">
							<tr align="left" height="20px">
							<th align="left">
							<spring:message code="ims.pcd.done.048"/></th><td>$${imsDoneUI.prepayAmt}<c:if test='${ImsOrder.waivedPrepay == "Y"}'>(Waived)</c:if></td></tr>
					</c:when>
					<c:when test='${imsDoneUI.account.payment.payMtdType == "M"}'>
						<c:choose><c:when test='${imsDoneUI.cashFsPrepay == "N"}'>
						<tr align="left" height="20px">
							<th align="left">
							<spring:message code="ims.pcd.done.049"/>	</th><td>$${imsDoneUI.prepayAmt}<c:if test='${ImsOrder.waivedPrepay == "Y"}'>(Waived)</c:if></td></tr>
						</c:when>
						<c:when test='${imsDoneUI.cashFsPrepay == "Y"}'>
						<tr align="left" height="20px">
							<th align="left">
							<spring:message code="ims.pcd.done.050"/>	</th><td>$${imsDoneUI.prepayAmt}<c:if test='${ImsOrder.waivedPrepay == "Y"}'>(Waived)</c:if></td></tr>
						</c:when></c:choose>
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				</c:choose>
				<tr align="left" height="20px">
					<th align="left">
						<c:choose>
							<c:when test='${imsDoneUI.otChrgType == "A"}'><spring:message code="ims.pcd.done.051"/> </c:when>
							<c:otherwise><spring:message code="ims.pcd.done.088"/> </c:otherwise>
						</c:choose>
						 Service Charge:</th>
								<td>${imsDoneUI.waivedOtInstallChrg}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">
				<spring:message code="ims.pcd.done.089"/></th><td><c:choose>
											<c:when test='${imsDoneUI.account.billMedia == "P"}'>
												<spring:message code="ims.pcd.done.052"/>
											</c:when>
											<c:when test='${imsDoneUI.account.billMedia == "E"}'>
												<spring:message code="ims.pcd.done.053"/> ${imsDoneUI.account.emailAddr}
											</c:when>
										</c:choose></td></tr>
			</table>
			</td>
		</tr>
		
<!--	kinman	-->
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.done.054"/></td>
				</tr>
			</table>
			</td>
		</tr>
		
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px"><spring:message code="ims.pcd.done.055"/></th><td>
					<c:choose><c:when test='${imsDoneUI.disMode == "E"}'><spring:message code="ims.pcd.done.090"/> </c:when>
							<c:when test='${imsDoneUI.disMode == "P"}'><spring:message code="ims.pcd.done.092"/></c:when>
							<c:when test='${imsDoneUI.disMode == "S"}'><spring:message code="ims.pcd.done.091"/></c:when>
							<c:when test='${imsDoneUI.disMode == "I"}'><spring:message code="ims.pcd.done.056"/></c:when>
							<c:when test='${imsDoneUI.disMode == "M"}'><spring:message code="ims.pcd.done.093"/></c:when>
					</c:choose>
					</td>
					
				</tr>
				
				<c:if test='${ims_direct_sales eq true}'>
					<tr align="left" height="20px">
						<th align="left" width="250px"><spring:message code="ims.pcd.done.056"/></th>
						<td><b style="color:red">${imsDoneUI.esigEmailAddr}</b></td>
					</tr>
					<tr align="left" height="20px">
						<th align="left" width="250px"><spring:message code="ims.pcd.done.057"/></th> 
						<td><b style="color:red">${ImsOrder.SMSno}</b></td> 
					</tr>
				</c:if>
				
				<c:if test='${imsDoneUI.disMode == "E" and ims_direct_sales ne true}'>	
					<tr align="left" height="20px" <c:if test='${imsDoneUI.isCC == "Y"}'>style="display:none;"</c:if>>
						<th align="left" width="250px"><spring:message code="ims.pcd.done.056"/></th>
						<td><b style="color:red">${imsDoneUI.esigEmailAddr}</b></td>
					</tr>
				</c:if>
					
				<tr align="left" height="20px">
					<th align="left" width="250px"><spring:message code="ims.pcd.done.058"/></th><td>
					<c:choose><c:when test='${imsDoneUI.langPreference == "CHI"}'><spring:message code="ims.pcd.done.059"/></c:when>
							<c:when test='${imsDoneUI.langPreference == "ENG"}'><spring:message code="ims.pcd.done.060"/></c:when>
					</c:choose>
					</td>
				</tr>
				
			</table>
			</td>
		</tr>	
		
<!--		ims direct sales only-->
	<c:if test="${ims_direct_sales eq true 
	and ImsOrder.customer.account.payment.payMtdType eq 'M' 
	and ImsOrder.cashFsPrepay eq 'N'
	and ImsOrder.waivedPrepay ne 'Y'
	and ImsOrder.orderStatus eq '06'
	and awaitCash eq 'Y'}">   
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
					<td class="table_title" ><spring:message code="ims.pcd.done.061"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr> 
					<td>
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<th align="left" width="250px"> 
								<spring:message code="ims.pcd.done.062"/>QR Code : 
						</th> 
						<td>
								<img src="getqrjpg.html?str=${ImsOrder.orderId}1234567"/>
						</td>
					</tr>
					<tr>
						<th align="left" width="250px"><spring:message code="ims.pcd.done.063"/></th>
						<td >${ImsOrder.primaryAcctNo}</td>
					</tr>
					<tr>
						<th align="left" width="250px"><spring:message code="ims.pcd.done.064"/> </th>
						<td ><form:input path="shopCode"/></td>
					</tr>
					<tr>
						<th align="left" width="250px"><spring:message code="ims.pcd.done.065"/> </th>
						<td ><form:input path="transactionCd"/></td>
					</tr>
					<c:if test="${imsDoneUI.srdAvailble eq 'N'}"> 
						<tr> 
							<td colspan=2>
								<%@ include file="/WEB-INF/jsp/imsappointmentsimple.jsp"%>
							</td>
						</tr>
					</c:if>
					<tr>
					<td>&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
					<td>&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
        				<td colspan=2>
        					<spring:message code="ims.pcd.done.066"/>
        					<form:select id="cancelSelect" path="cReasonCd">
        						<form:option value=""></form:option>
        						<form:options items="${cancelList}" itemLabel="value" itemValue="key"/>
							</form:select>
       						<a href="#" class="nextbutton" onclick="cancel()"><spring:message code="ims.pcd.done.094"/></a>
       							&nbsp;&nbsp;&nbsp;
    					</td>
       				</tr>
					<tr>
						<td></td> 
						<td>
							<div id="submit" align="right" >
								<a href="#" class="nextbuttonS" onclick="imsDsSubmit();"><spring:message code="ims.pcd.done.095"/></a> 
							</div> 
						</td>
					</tr>	
				</table>
			</td>
		</tr>
		
	</c:if>
<!--		ims direct sales only (end)-->

<!--awaitSignOff only-->
	<c:if test="${ims_direct_sales eq true 
	and ImsOrder.customer.account.payment.payMtdType eq 'M' 
	and ImsOrder.cashFsPrepay eq 'N'
	and ImsOrder.waivedPrepay ne 'Y'
	and ImsOrder.orderStatus eq '11'
	and awaitSignOff eq 'Y'}">   
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
					<td class="table_title" ><spring:message code="ims.pcd.done.061"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr> 
					<td>
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<th align="left" width="250px"> 
								<spring:message code="ims.pcd.done.062"/>QR Code : 
						</th> 
						<td>
								<img src="getqrjpg.html?str=${ImsOrder.orderId}1234567"/>
						</td>
					</tr>
					<tr>
						<th align="left" width="250px"><spring:message code="ims.pcd.done.064"/></th>
						<td ><form:input path="shopCode"/></td>
					</tr>
					<tr>
						<th align="left" width="250px"><spring:message code="ims.pcd.done.065"/></th>
						<td ><form:input path="transactionCd"/></td>
					</tr>
					<c:if test="${imsDoneUI.srdAvailble eq 'N'}"> 
						<tr> 
							<td colspan=2>
								<%@ include file="/WEB-INF/jsp/imsappointmentsimple.jsp"%>
							</td>
						</tr>
					</c:if>
					<tr>
					<td>&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
					<td>&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
        				<td colspan=2>
        					<spring:message code="ims.pcd.done.066"/>
        					<form:select id="cancelSelect" path="cReasonCd">
        						<form:option value=""></form:option>
        						<form:options items="${cancelList}" itemLabel="value" itemValue="key"/>
							</form:select>
       						<a href="#" class="nextbutton" onclick="cancel()"><spring:message code="ims.pcd.done.094"/></a>
       							&nbsp;&nbsp;&nbsp;
    					</td>
       				</tr>
					<tr>
						<td></td> 
						<td>
							<div id="submit" align="right" >
								<a href="#" class="nextbuttonS" onclick="imsDsSubmit();"><spring:message code="ims.pcd.done.095"/></a> 
							</div> 
						</td>
					</tr>	
				</table>
			</td>
		</tr>
		
	</c:if>
<!--awaitSignOff only (end)-->
		
		<tr <c:if test='${imsDoneUI.isCC == "Y" or ims_direct_sales eq true}'>style="display:none"</c:if>>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" ><spring:message code="ims.pcd.done.096"/></td>
				</tr>
			</table>
			</td>
		</tr>
		
		<tr <c:if test='${imsDoneUI.isCC == "Y" or ims_direct_sales eq true}'>style="display:none"</c:if>>
					<td>
					<table width="100%" border="0" cellspacing="1" class="contenttext"
						background="images/background2.jpg">
						<tr class="contenttextgreen2">
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.done.067"/></td>
							<td colspan="2" style="text-align: center; width: 50%;"
								class="table_title"><spring:message code="ims.pcd.done.068"/></td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.done.069"/></td>
						</tr>

	<c:forEach items="${imsDoneUI.allOrdDocAssgnDTOs}" var="dto" varStatus="status">
		<tr>
					<td colspan="1" style="text-align: center; width: 25%;">
						<c:out value="${dto.docName}"/>
						<form:hidden path="allOrdDocAssgnDTOs[${status.index}].docType"/>
					</td>
					<td colspan="2" style="text-align: center; width: 50%;">
						<c:choose> 
							<c:when test="${not empty dto.waiveReason}">
											<c:forEach	items="${waiveReasons[dto.docType]}" var="r">
												<c:if test="${r.waiveReason == dto.waiveReason}">
													<c:out value="${r.waiveDesc}" />
												</c:if>
											</c:forEach>	
							</c:when>					
						<c:otherwise>
							N/A
						</c:otherwise>			
						</c:choose>
					</td>
					<td colspan="1" style="text-align: center; width: 25%;">
						<span class="note"></span>
						<c:choose>
							<c:when test="${not empty dto.waiveReason}">Waived</c:when>
							<c:when test="${dto.collectedInd == 'Y'}">Y</c:when>
							<c:when test="${dto.collectedInd == 'N'}">N</c:when>
						</c:choose>				
					</td>
		</tr>
		</c:forEach>



					</table>
					</td>
				</tr>
				
		<tr><td></td></tr>
				
				
				<c:if test='${imsDoneUI.isCC=="Y"}'>
				<c:if test='${not empty imsDoneUI.imsCollectDocDTOs }'>
				

				<tr>
					<td>
					<table width="100%" border="0" cellspacing="1" rules="none">
						<tr>
						<td class="table_title" ><spring:message code="ims.pcd.done.070"/></td>
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
								class="table_title"><spring:message code="ims.pcd.done.067"/></td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.done.068"/></td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.done.069"/></td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title"><spring:message code="ims.pcd.done.097"/></td>
						</tr>

						<c:forEach items="${imsDoneUI.imsCollectDocDTOs}" var="dto" varStatus="status">
							<tr>
										<td colspan="1" style="text-align: center; width: 25%;">
											<c:out value="${dto.docTypeDisplay}"/>
										</td>
										<td colspan="1" style="text-align: center; width: 25%;">
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
										<td colspan="1" style="text-align: center; width: 25%;">
											<c:out value="${dto.collectedInd != 'Y'? 'N': dto.collectedInd}"/>
										</td>
										<td colspan="1" style="text-align: center; width: 25%;">
											${dto.faxSerial}
										</td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				</c:if>
				<c:if test='${imsDoneUI.edfRef!="" }'>
				<br>
					<tr><td>
					<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
						<tr align="left" height="20px"></tr>
						<tr align="left" height="20px">
							<th align="left" width="250px">EDF Ref</th>
							<td>${imsDoneUI.edfRef}</td>
						</tr>
					</table>
					</td></tr>
				</c:if>
				</c:if>
				
		
		
		
<!--		kinman			-->
		<tr>
  		<td>
  		<form:hidden id="langPreference" path="langPreference"/>
		<div id="print" align="right">			
				<a href="#" id="captureIPad" class="nextbutton" onclick="CaptureiPad();" <c:if test='${imsDoneUI.isCC == "Y" or ims_direct_sales eq true or imsDoneUI.disMode == "P"}'>style="display:none"</c:if>><spring:message code="ims.pcd.done.071"/></a>			 
			<c:if test='${imsDoneUI.signoffedOrder == "Y"}'>
<!--				<a href="imsreport.html?pdfLang=en" class="nextbutton">Print S&S form (Eng)</a>-->
				<a href="#"	class="nextbuttonS"	onclick="CapturePC();" style="display:none;"><spring:message code="ims.pcd.done.072"/></a>
				<a href="#" id="captureDS" class="nextbutton" onclick="CaptureiPad();captureIpadButtonCheck();"  <c:if test="${ims_ds_mismatch_hkid eq false}">style="display:none"</c:if>><spring:message code="ims.pcd.done.073"/></a>
				
				<c:choose>
				<c:when test="${ImsOrder.imsOrderType eq 'DS'}">
					<a href="#" class="nextbutton" onClick="javascript:dummyprintLangForm();"><spring:message code="ims.pcd.done.074"/> </a>
				</c:when>
				<c:otherwise>
					<a href="#" class="nextbutton" onClick="javascript:dummyprintLangForm();"><spring:message code="ims.pcd.done.075"/> </a>
				</c:otherwise>
				</c:choose>
<!--				<a href="imsreport.html?pdfLang=zh" class="nextbutton">Print S&S form (Chi)</a>-->
			</c:if>			
		</div>
		<input type="hidden" name="currentView" value="imsDone"/>
		</td>
  		</tr>
		</table>	     	     
      </td>
  </tr>
  <form:hidden id="submitTag" path="submitTag"/>
  <form:hidden id="cancelReason" path="cancelReason"/>
  <form:hidden id="orderId" path="orderId"/>
  <form:hidden id="srdAvailble" path="srdAvailble"/><!-- use for imsdsqcprocessdetail -->
</table>
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>