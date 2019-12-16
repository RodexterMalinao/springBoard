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
	function formSubmit(){
		//window.location = "basketdetails.html";
		//document.summary.submit();
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
		if (document.getElementById("langPreference").value=='ENG'){
			window.location.replace('imsreport.html?pdfLang=en', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			}else {
			window.location.replace('imsreport.html?pdfLang=zh', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			} 
		}
</script> 
<div id="progressbar">
<a class="progresspast"><span><b>1</b>Customer Info.</span></a> 
<a class="progresspast"><span><b>2</b>Basket Selection</span></a> 
<a class="progresspast"><span><b>3</b>Basket</span></a> 
<a class="progresspast"><span><b>4</b>Now TV</span></a> 
<a class="progresspast"><span><b>5</b>Customer Info./Login</span></a> 
<a class="progresspast"><span><b>6</b>Payment Method/Sales Info.</span></a> 
<a class="progresspast"><span><b>7</b>Appointment</span></a>
<a class="progresspast"><span><b>8</b>Summary</span></a> 
<a class="progress"><span><b>9</b>Done</span></a>
<br>
</div>

<form:form name="welcomeForm" method="POST" commandName="imsDoneUI">

<script>
$(document).ready(function(){
	$("#header").remove();
	$("#progressbar").remove();
	}); 
</script> 


<br><br>
<table width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="1" rules="none">
		<tr>
			<td class="table_title" >Done</td>
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
						Your order has been submitted. Thank you for your order.
					</c:otherwise>
				</c:choose>
				<br>
				The order number is ${imsDoneUI.orderId}
				<c:if test='${not empty imsDoneUI.signOffDate }'>	
					<br/>		
					Sign off date time: ${imsDoneUI.signOffDate}
				</c:if>
				
				<c:if test='${not empty imsDoneUI.callDate}'>
					<br/>
					Call Date: ${imsDoneUI.callDate}
				</c:if>
				
				<c:if test='${not empty imsDoneUI.callTime}'>
					<br/>
					Call Time: ${imsDoneUI.callTime}
				</c:if>
				
				<c:if test='${not empty imsDoneUI.positionNo}'>
					<br/>
					Position No.: ${imsDoneUI.positionNo}
				</c:if>
				
				<c:if test='${not empty imsDoneUI.FAXno}'>
					<br/>
					FAX Serial: ${imsDoneUI.FAXno}
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
						<td class="contenttextgreen"> Email has been sent successfully to customer.</td>
					</c:when>
					<c:when test='${emailReqResult == null}'>
						
					</c:when>
					<c:otherwise>
					<td  class="contenttext_red"><b>Failure in sending email.			
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
				<td class="table_title" >Customer Information</td>
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
						<tr align="left" height="20px"><th align="left" width="250px">Title:</th><td>${imsDoneUI.title}</td></tr>
						<tr align="left" height="20px"><th align="left">Full Name:</th><td>${imsDoneUI.lastName} ${imsDoneUI.firstName}</td></tr>
					</c:when>
					<c:otherwise>
						<tr align="left" height="20px"><th align="left" width="250px">Company Name</th><td>${imsDoneUI.companyName}</td></tr>
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
					<tr align="left" height="20px"><th align="left">Date of Birth:</th><td>${imsDoneUI.dobStr}</td></tr>
				</c:if>
				<c:choose><c:when test='${imsDoneUI.contact.contactPhone != null}'>
				<c:choose><c:when test='${imsDoneUI.contact.contactPhone != ""}'>
				<tr align="left" height="20px"><th align="left">Mobile No.:</th><td>${imsDoneUI.contact.contactPhone}</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				<c:choose><c:when test='${imsDoneUI.contact.otherPhone != null}'>
				<c:choose><c:when test='${imsDoneUI.contact.otherPhone != ""}'>
				<tr align="left" height="20px"><th align="left">Fixed line no.:</th><td>${imsDoneUI.contact.otherPhone}</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				<c:choose><c:when test='${imsDoneUI.fixedLineNo != null}'>
				<c:choose><c:when test='${imsDoneUI.fixedLineNo != ""}'>
				<tr align="left" height="20px"><th align="left">Existing PCCW Fix Line No.:</th><td>${imsDoneUI.fixedLineNo}(PCCW)</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				
				<!-- Gary -->
				<c:choose><c:when test='${imsDoneUI.hktLoginId != null}'>
				<c:choose><c:when test='${imsDoneUI.hktLoginId != ""}'>
				<c:choose><c:when test='${imsDoneUI.hktMobileNum != null}'>
				<c:choose><c:when test='${imsDoneUI.hktMobileNum != ""}'>
				<tr align="left" height="20px"><th align="left" width="400">My HKT Account Registration Login ID & Mobile No.:</th><td>${imsDoneUI.hktLoginId}/${imsDoneUI.hktMobileNum}</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				
				<!-- <tr align="left" height="20px"><th>Contact Email:</th><td>taiman@netvigator.com</td></tr> -->
				<!-- <tr align="left" height="20px"><th>Language Preference:</th><td>Written(Chinese), Speaking(Chinese)</td></tr> -->
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" rules="none">
				<tr>
				<td class="table_title" >Package Summary</td>
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
					<td class="table_title" >Monthly Fixed Term Rate</td>
					<td class="table_title">Month-to-Month Rate</td>
				</tr>
				<tr valign="top">
					<td><c:if test='${imsDoneUI.basketDtl.imagePath != null}'>
						<img src="basket/${imsDoneUI.basketDtl.imagePath}" width="148">
					</c:if></td>
					<td>
						<div class="basket_title">
						<c:if test='${imsDoneUI.basketDtl.bandwidth != null}'>
							${imsDoneUI.basketDtl.bandwidth}M 
						</c:if>
						${imsDoneUI.basketDtl.basketTitle}</div><br>
						<div>
						<dir>${imsDoneUI.servPlanDto.coreServiceDetail.progItem.itemDetails}</dir>
						</div>
						<div class="tableOrangetext">Contract Period: ${imsDoneUI.warrPeriod} Month<br>
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
					<tr><td>&nbsp;</td><td class="item_title_rp"><br>nowTV bundle Service :</td>
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
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" >Service Details</td>
				</tr>
			</table>
			</td>
		</tr>
		<!-- Terrence Part -->
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px"> Service Installation Address:</th><td>
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
					<th align="left">Billing & Correspondence Address:</th><td><c:choose>
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
																		<c:choose><c:when test='${imsDoneUI.installAddress.unitNo == null}'></c:when><c:when test='${imsDoneUI.installAddress.unitNo == ""}'></c:when><c:otherwise>Flat </c:otherwise></c:choose>
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
					<th align="left">NETVIGATOR Broadband Login ID / E-mail Address Registration:</th><td>${imsDoneUI.loginId}@netvigator.com</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">Target Installation Date:</th><td>						
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
					<th align="left">Application Date:</th><td>${imsDoneUI.appDate}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">Target Commencement Date:<br>(for Pre-Installation Option only)</th><td>${imsDoneUI.targetCommDate}</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" >Payment Method</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px">Payment Method:</th><td>
					<c:choose><c:when test='${imsDoneUI.account.payment.payMtdType == "M"}'>Cash</c:when>
							<c:when test='${imsDoneUI.account.payment.payMtdType == "C"}'>Credit Card</c:when>
					</c:choose>
									<c:choose>
										<c:when test='${imsDoneUI.account.payment.payMtdType == "C"}'>
															<c:choose>
																<c:when test='${imsDoneUI.account.payment.thirdPartyInd == "Y"}'>
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
					<c:when test='${imsDoneUI.account.payment.payMtdType == "C"}'>
						<tr align="left" height="20px">
							<th align="left">Credit Card Type:</th>
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
								Credit Card No.:</th><td>${imsDoneUI.account.payment.ccNum}</td></tr>
						<c:choose>
							<c:when test='${imsDoneUI.account.payment.thirdPartyInd == "Y" and not empty imsDoneUI.account.payment.ccIdDocNo}'>
								<tr align="left" height="20px">
									<th align="left">Credit Card Holder Doc Type:</th><td>
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
										Credit Card Holder Doc No.:</th><td>${imsDoneUI.account.payment.ccIdDocNo}</td></tr>
							</c:when>
							<c:otherwise>
							
							</c:otherwise>
						</c:choose>
						<tr align="left" height="20px">
							<th align="left">
								Cardholder Name:<br>(in English)</th><td>${imsDoneUI.account.payment.ccHoldName}
									</td>
						</tr>
						<tr align="left" height="20px">
							<th align="left">
								Expiry Date:</th><td>${imsDoneUI.account.payment.ccExpDate}</td></tr>
						<tr align="left" height="20px">
							<tr align="left" height="20px">
							<th align="left">
								Credit Card Prepayment of:</th><td>$${imsDoneUI.prepayAmt}<c:if test='${ImsOrder.waivedPrepay == "Y"}'>(Waived)</c:if></td></tr>
					</c:when>
					<c:when test='${imsDoneUI.account.payment.payMtdType == "M"}'>
						<c:choose><c:when test='${imsDoneUI.cashFsPrepay == "N"}'>
						<tr align="left" height="20px">
							<th align="left">
								Cash Prepayment of:</th><td>$${imsDoneUI.prepayAmt}<c:if test='${ImsOrder.waivedPrepay == "Y"}'>(Waived)</c:if></td></tr>
						</c:when>
						<c:when test='${imsDoneUI.cashFsPrepay == "Y"}'>
						<tr align="left" height="20px">
							<th align="left">
								FS Collection of Cash:</th><td>$${imsDoneUI.prepayAmt}<c:if test='${ImsOrder.waivedPrepay == "Y"}'>(Waived)</c:if></td></tr>
						</c:when></c:choose>
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				</c:choose>
				<tr align="left" height="20px">
					<th align="left">
						<c:choose>
							<c:when test='${imsDoneUI.otChrgType == "A"}'>Activation </c:when>
							<c:otherwise>Installation </c:otherwise>
						</c:choose>
						 Service Charge:</th>
								<td>$${imsDoneUI.waivedOtInstallChrg}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">
					Bill Media:</th><td><c:choose>
											<c:when test='${imsDoneUI.account.billMedia == "P"}'>
												By Paper
											</c:when>
											<c:when test='${imsDoneUI.account.billMedia == "E"}'>
												By E-Mail ${imsDoneUI.account.emailAddr}
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
				<td class="table_title" >Distribution Mode</td>
				</tr>
			</table>
			</td>
		</tr>
		
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px">Document Attachment Generated by</th><td>
					<c:choose><c:when test='${imsDoneUI.disMode == "E"}'>Digital Signature</c:when>
							<c:when test='${imsDoneUI.disMode == "P"}'>Paper</c:when>
							<c:when test='${imsDoneUI.disMode == "S"}'>SMS</c:when>
							<c:when test='${imsDoneUI.disMode == "I"}'>Email</c:when>
							<c:when test='${imsDoneUI.disMode == "M"}'>Postal</c:when>
					</c:choose>
					</td>
					
				</tr>
				
				<c:if test='${imsDoneUI.disMode == "E"}'>	
					<tr align="left" height="20px" <c:if test='${imsDoneUI.isCC == "Y"}'>style="display:none;"</c:if>>
						<th align="left" width="250px">Email</th>
						<td><b style="color:red">${imsDoneUI.esigEmailAddr}</b></td>
					</tr>
				</c:if>
					
				<tr align="left" height="20px">
					<th align="left" width="250px">Application Form Language</th><td>
					<c:choose><c:when test='${imsDoneUI.langPreference == "CHI"}'>Tranditional Chinese</c:when>
							<c:when test='${imsDoneUI.langPreference == "ENG"}'>English</c:when>
					</c:choose>
					</td>
				</tr>
				
			</table>
			</td>
		</tr>	
		
		
		<tr <c:if test='${imsDoneUI.isCC == "Y"}'>style="display:none"</c:if>>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" >Required Proof</td>
				</tr>
			</table>
			</td>
		</tr>
		
		
		
		<tr <c:if test='${imsDoneUI.isCC == "Y"}'>style="display:none"</c:if>>
					<td>
					<table width="100%" border="0" cellspacing="1" class="contenttext"
						background="images/background2.jpg">
						<tr class="contenttextgreen2">
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title">Supporting Doc Type</td>
							<td colspan="2" style="text-align: center; width: 50%;"
								class="table_title">Waive Reason</td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title">Collected</td>
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
						<td class="table_title" >Required Supporting Document</td>
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
								class="table_title">Supporting Doc Type</td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title">Waive Reason</td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title">Collected</td>
							<td colspan="1" style="text-align: center; width: 25%;"
								class="table_title">Fax Serial Number</td>
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
				<a href="#" id="captureIPad" class="nextbutton3" onclick="CaptureiPad();" style="display:none;">Capture Proof by iPad</a>
			<c:if test='${imsDoneUI.signoffedOrder == "Y"}'>
<!--				<a href="imsreport.html?pdfLang=en" class="nextbutton">Print S&S form (Eng)</a>-->
				<a href="#"	class="nextbutton3"	onclick="CapturePC();" style="display:none;">Capture Proof (upload via PC only)</a>
				<a href="#" class="nextbutton3" onClick="javascript:dummyprintLangForm();"> Print AF </a>
<!--				<a href="imsreport.html?pdfLang=zh" class="nextbutton">Print S&S form (Chi)</a>-->
			</c:if>			
		</div>
		<input type="hidden" name="currentView" value="imsDone"/>
		</td>
  		</tr>
		</table>	     	     
      </td>
  </tr>
</table>
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>