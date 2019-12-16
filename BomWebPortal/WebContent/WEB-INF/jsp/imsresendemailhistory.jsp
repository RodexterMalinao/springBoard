<%@ include file="header.jsp" %>
<%-- <%@ include file="imsvalidwindow.jsp" %> --%>

<script language="Javascript">	
	function formSubmit(){
		//window.location = "basketdetails.html";
		
		$(".error").hide();
		$("input[name=esigEmailAddr]").val($.trim($("input[name=esigEmailAddr]").val()));
		if ($("input[name=esigEmailAddr]").val().length == 0) {
			$(".error_esigEmailAddr").text("Require Email").show();
		} else if (!(/^\S+@\S+$/).test($("input[name=esigEmailAddr]").val())) {
			$(".error_esigEmailAddr").text("Invalid Email format").show();
		}
		if ($(".error:visible").length > 0) {
			return false;
		}
		if (!confirm("Email will be sent to " + $("input[name=esigEmailAddr]").val() + ". Confirm to continue?")) {
			return false;
		}
		$("input[name=emailSendConfirm]").val("true");
		document.welcomeForm.submit();
	}
	

	//Init email history item
	$(document).ready(function() {
		$("input[name=emailSendConfirm]").val("false");
		$("#emailContent").html($("#emailContentSrc").text());
		$(".sendBtn").click(function(e) {
			e.preventDefault();
			formSubmit();
			return false;
		});
		
		var $trs = $(".histList tbody tr");
		$(".control").show().data("index", 0);
		$(".control .left a").hide();
		if ($trs.length > 20) {
			$(".control .right a").show();
		}
		$(".control .label").text("1 - " + $(".histList tbody tr:visible").length + " / " + $trs.length);
		$(".control .left a").click(function(e) {
			e.preventDefault();
			$trs.hide();
			var start = $(".control").data("index") - 20;
			if (start < 0) {
				start = 0;
			}
			var end = start + 20;
			if (end > $trs.length) {
				end = $trs.length;
			}
			if (start > 0) {
				$(".control .left a").show();
			} else {
				$(".control .left a").hide();
			}
			if (end < $trs.length) {
				$(".control .right a").show();
			} else {
				$(".control .right a").hide();
			}
			$trs.slice(start, end).show();
			$(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
			$(".control").data("index", start);
			return false;
		});
		$(".control .right a").click(function(e) {
			e.preventDefault();
			$trs.hide();
			var start = $(".control").data("index") + 20;
			if (start > $trs.length) {
				start = $trs.length;
			}
			var end = start + 20;
			if (end > $trs.length) {
				end = $trs.length;
			}
			if (start > 0) {
				$(".control .left a").show();
			} else {
				$(".control .left a").hide();
			}
			if (end < $trs.length) {
				$(".control .right a").show();
			} else {
				$(".control .right a").hide();
			}
			$trs.slice(start, end).show();
			$(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
			$(".control").data("index", start);
			return false;
		});
	});
</script> 

<!-- For resend email -->
<style type="text/css">
.row { height: 24px }
.label { display: inline-block; font-weight: bold; width: 120px }
.input { display: inline-block }
.input label { display: inline-block; width: 50px }
.wrapContent { display: inline-block; width: 100%; padding: 10px 0 }
.previewForm .content { margin-left: 2em }
.previewForm .half { width: 40%; float: left }
.even { background-color: #E8FFE8 }
.success { color: blue }
.fail { color: red }
.histList { width: 100%; clear: both; background-color: white }
.histList td { text-align: center }
#emailContent { padding: 1em; border: 1px solid #F2F2F2; margin-top: 1em; margin-right: 2em }
</style>






<form:form name="welcomeForm" method="POST" commandName="imsResendEmailHistoryUI">
<br><br>
<c:choose>
<c:when test="${not empty param.emailReqResult}">
	<c:choose>
	<c:when test="${param.emailReqResult == 'SUCCESS'}">
		<h2 class="success">Email sent on <c:out value="${param.sentDate}"/></h2>
	</c:when>
	<c:otherwise>
		<h2 class="fail">Failure in send email, <c:out value="${param.emailReqResultMsg}"/></h2>
	</c:otherwise>
	</c:choose>
</c:when>
<c:when test="${param.emailSendConfirm == 'false'}"><h2 class="fail">Please confirm to send email</h2></c:when>
</c:choose>
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
				Your order has been submitted. Thank you for your order.
				<br>
				The order number is ${imsResendEmailHistoryUI.orderId}
				<br>
				Sign off date time: ${imsResendEmailHistoryUI.signOffDate}</td>
		</tr>
		<tr>
			
			<c:choose>
				<c:when test='${imsResendEmailHistoryUI.disMode == "E"}'>
				<c:choose>
					<c:when test='${emailReqResult == "SUCCESS"}'>
						<td class="contenttextgreen">Email has been sent successfully to customer.</td>
					</c:when>
					<c:when test='${emailReqResult == null}'>
						
					</c:when>
					<c:otherwise>
					<td  class="contenttext_red"><b>Failure in sending email.			
						</br>
						<b><c:out value="${emailReqResultMsg}"></c:out></b>
						</td>
					</c:otherwise>
				</c:choose>
				</c:when>
			</c:choose>
			
			
		
		</tr>
		
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" >Customer Information</td>
				</tr>
			</table>
			</td>
		</tr>
		
		<!-- Terrence Part -->
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px"><th align="left" width="250px">Title:</th><td>${imsResendEmailHistoryUI.title}</td></tr>
				<tr align="left" height="20px"><th align="left">Full Name:</th><td>${imsResendEmailHistoryUI.lastName} ${imsResendEmailHistoryUI.firstName}</td></tr>
				<tr align="left" height="20px"><th align="left">
				<c:choose><c:when test='${imsResendEmailHistoryUI.idDocType == "PASS"}'>
				PASSPORT:</c:when>
				<c:otherwise>
				${imsResendEmailHistoryUI.idDocType}:
				</c:otherwise></c:choose></th><td>${imsResendEmailHistoryUI.idDocNum}</td></tr>
				<tr align="left" height="20px"><th align="left">Date of Birth:</th><td>${imsResendEmailHistoryUI.dobStr}</td></tr>
				<c:choose><c:when test='${imsResendEmailHistoryUI.contact.contactPhone != null}'>
				<c:choose><c:when test='${imsResendEmailHistoryUI.contact.contactPhone != ""}'>
				<tr align="left" height="20px"><th align="left">Mobile No.:</th><td>${imsResendEmailHistoryUI.contact.contactPhone}</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				<c:choose><c:when test='${imsResendEmailHistoryUI.contact.otherPhone != null}'>
				<c:choose><c:when test='${imsResendEmailHistoryUI.contact.otherPhone != ""}'>
				<tr align="left" height="20px"><th align="left">Fixed line no.:</th><td>${imsResendEmailHistoryUI.contact.otherPhone}</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				<c:choose><c:when test='${imsResendEmailHistoryUI.fixedLineNo != null}'>
				<c:choose><c:when test='${imsResendEmailHistoryUI.fixedLineNo != ""}'>
				<tr align="left" height="20px"><th align="left">Existing PCCW Fix Line No.:</th><td>${imsResendEmailHistoryUI.fixedLineNo}(PCCW)</td></tr>
				</c:when></c:choose>
				</c:when><c:otherwise></c:otherwise></c:choose>
				<!-- <tr align="left" height="20px"><th>Contact Email:</th><td>taiman@netvigator.com</td></tr> -->
				<!-- <tr align="left" height="20px"><th>Language Preference:</th><td>Written(Chinese), Speaking(Chinese)</td></tr> -->
			</table>
			</td>
		</tr>
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" rules="none">
				<tr>
				<td class="table_title" >Package Summary</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext">
				<tr valign="middle" align="center">
					<td>&nbsp;</td>
					<td width="70%">&nbsp;</td>
					<td class="table_title" >Monthly Fixed Term Rate</td>
					<td class="table_title">Month-to-Month Rate</td>
				</tr>
				<tr valign="top">
					<td><c:if test='${imsResendEmailHistoryUI.basketDtl.imagePath != null}'>
						<img src="basket/${imsResendEmailHistoryUI.basketDtl.imagePath}" width="148">
					</c:if></td>
					<td>
						<div class="basket_title">
						${imsResendEmailHistoryUI.basketDtl.basketTitle}</div><br>
						<div>
						<dir>${imsResendEmailHistoryUI.servPlanDto.coreServiceDetail.progItem.itemDetails}</dir>
						</div>
						<div class="tableOrangetext">Contract Period: ${imsResendEmailHistoryUI.warrPeriod} Month<br>
						</div>
					</td>
					<td class="BGgreen2" >${imsResendEmailHistoryUI.servPlanDto.coreServiceDetail.progItem.itemMthFix}</td>
					<td class="BGgreen2" >${imsResendEmailHistoryUI.servPlanDto.coreServiceDetail.progItem.itemMth2Mth}</td>
				</tr>
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.coreServiceDetail.bvasMandItemList}" var="bvasMandItemList" varStatus="status">
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
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.coreServiceDetail.bvasNonMItemList}" var="bvasNonMItemList" varStatus="status">
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
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.optServiceDetail.optPremItemList}" var="optPremItemList" varStatus="status">
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
				<!-- <c:forEach items="${imsResendEmailHistoryUI.servPlanDto.optServiceDetail.wlBbItemList}" var="wlBbItemList" varStatus="status">
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
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.optServiceDetail.antiVirItemList}" var="antiVirItemList" varStatus="status">
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
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.optServiceDetail.optServItemList}" var="optServItemList" varStatus="status">
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
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.optServiceDetail.mediaItemList}" var="mediaItemList" varStatus="status">
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
				<c:if test='${imsResendEmailHistoryUI.ntvPgmItemList != null}'>
					<tr><td>&nbsp;</td><td class="item_title_rp"><br>nowTV bundle Service :</td>
						<td class="BGgreen2" ></td>
						<td class="BGgreen2" ></td>
					</tr>
				</c:if>
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.ntvServiceDetail.ntvFreeItemList}" var="ntvFreeItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<td>
						<span class="tableOrangetext">${ntvFreeItemList.itemTitle}</span>
						<!-- <dir><span class="item_detail">
						${ntvFreeItemList.itemDetails}
						</span></dir> -->
						<dir><span class="item_detail">
						<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.ntvServiceDetail.ntvFreeSPChannelList}" var="ntvFreeSPChannelList" varStatus="status">
						${ntvFreeSPChannelList.channelDesc}
						</c:forEach>
						</span></dir>
					</td>
					
					<td class="BGgreen2" >${ntvFreeItemList.itemMthFix}</td>
					<td class="BGgreen2" >${ntvFreeItemList.itemMth2Mth}</td>
					<tr></tr>
				</tr>
				</c:forEach>
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.ntvServiceDetail.ntvFreeEPChannelList}" var="ntvFreeEPChannelList" varStatus="status">
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
				<c:if test='${fn:length(imsResendEmailHistoryUI.servPlanDto.ntvServiceDetail.ntvFreeHDChannelList) != 0}'>
				<c:set var="chGroupDesc" value=""/>
				<c:set var="chGroupDescStart" value=""/>
				<tr valign="top">
				<td>&nbsp;</td>
				<td class="tableOrangetext">	
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.ntvServiceDetail.ntvFreeHDChannelList}" var="ntvFreeHDChannelList" varStatus="status">
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
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.ntvServiceDetail.ntvPayItemList}" var="ntvPayItemList" varStatus="status">
				<tr valign="top">
					<td>&nbsp;</td>
					<c:choose><c:when test='${ntvPayItemList.itemTvType == "SD"}'>
					<td>
						<span class="tableOrangetext">${ntvPayItemList.itemTitle}</span>
						<c:set var="parentCh" value=""/>
						<ul>
						<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.ntvServiceDetail.ntvPayChannelList}" var="ntvPayChannelList" varStatus="status">
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
						<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.ntvServiceDetail.ntvPayChannelList}" var="ntvPayChannelList" varStatus="status">
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
				<c:forEach items="${imsResendEmailHistoryUI.servPlanDto.ntvServiceDetail.ntvOtherItemList}" var="ntvOtherItemList" varStatus="status">
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
					<td class="BGgreen2" >$${imsResendEmailHistoryUI.totalRecurrentAmt}</td>
					<td class="BGgreen2" >$${imsResendEmailHistoryUI.totalMthToMthRate}</td>
				</tr> -->
			</table>
			</td>
		</tr>
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" >Service Details</td>
				</tr>
			</table>
			</td>
		</tr>
		<!-- Terrence Part -->
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px"> Service Installation Address:</th><td>
					<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.unitNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.unitNo == ""}'></c:when><c:otherwise>Flat </c:otherwise></c:choose>
					${imsResendEmailHistoryUI.installAddress.unitNo}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.unitNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.unitNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					Floor ${imsResendEmailHistoryUI.installAddress.floorNo}, ${imsResendEmailHistoryUI.installAddress.hiLotNo}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.hiLotNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.hiLotNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsResendEmailHistoryUI.installAddress.buildNo}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.buildNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.buildNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsResendEmailHistoryUI.installAddress.strNo} ${imsResendEmailHistoryUI.installAddress.strName} ${imsResendEmailHistoryUI.installAddress.strCatDesc}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.strName == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.strName == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsResendEmailHistoryUI.installAddress.sectDesc}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.sectDesc == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.sectDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsResendEmailHistoryUI.installAddress.distDesc}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.distDesc == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.distDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
					${imsResendEmailHistoryUI.installAddress.areaDesc}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">Billing & Correspondence Address:</th><td><c:choose>
																	<c:when test='${imsResendEmailHistoryUI.billingAddress != null}'>
																		<c:choose><c:when test='${imsResendEmailHistoryUI.billingAddress.unitNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.billingAddress.unitNo == ""}'></c:when><c:otherwise>Flat </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.billingAddress.unitNo}<c:choose><c:when test='${imsResendEmailHistoryUI.billingAddress.unitNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.billingAddress.unitNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		Floor ${imsResendEmailHistoryUI.billingAddress.floorNo}, ${imsResendEmailHistoryUI.billingAddress.hiLotNo}<c:choose><c:when test='${imsResendEmailHistoryUI.billingAddress.hiLotNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.billingAddress.hiLotNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.billingAddress.buildNo}<c:choose><c:when test='${imsResendEmailHistoryUI.billingAddress.buildNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.billingAddress.buildNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.billingAddress.strNo} ${imsResendEmailHistoryUI.billingAddress.strName} ${imsResendEmailHistoryUI.billingAddress.strCatDesc}<c:choose><c:when test='${imsResendEmailHistoryUI.billingAddress.strName == null}'></c:when><c:when test='${imsResendEmailHistoryUI.billingAddress.strName == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.billingAddress.sectDesc}<c:choose><c:when test='${imsResendEmailHistoryUI.billingAddress.sectDesc == null}'></c:when><c:when test='${imsResendEmailHistoryUI.billingAddress.sectDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.billingAddress.distDesc}<c:choose><c:when test='${imsResendEmailHistoryUI.billingAddress.distDesc == null}'></c:when><c:when test='${imsResendEmailHistoryUI.billingAddress.distDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.billingAddress.areaDesc}
																	</c:when>
																	<c:when test='${imsResendEmailHistoryUI.billingAddress == null}'>
																		<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.unitNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.unitNo == ""}'></c:when><c:otherwise>Flat </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.installAddress.unitNo}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.unitNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.unitNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		Floor ${imsResendEmailHistoryUI.installAddress.floorNo}, ${imsResendEmailHistoryUI.installAddress.hiLotNo}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.hiLotNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.hiLotNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.installAddress.buildNo}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.buildNo == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.buildNo == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.installAddress.strNo} ${imsResendEmailHistoryUI.installAddress.strName} ${imsResendEmailHistoryUI.installAddress.strCatDesc}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.strName == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.strName == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.installAddress.sectDesc}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.sectDesc == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.sectDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.installAddress.distDesc}<c:choose><c:when test='${imsResendEmailHistoryUI.installAddress.distDesc == null}'></c:when><c:when test='${imsResendEmailHistoryUI.installAddress.distDesc == ""}'></c:when><c:otherwise>, </c:otherwise></c:choose>
																		${imsResendEmailHistoryUI.installAddress.areaDesc}
																	</c:when>
																  </c:choose></td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">NETVIGATOR Broadband Login ID / E-mail Address Registration:</th><td>${imsResendEmailHistoryUI.loginId}@netvigator.com</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">Target Installation Date:</th><td>						
						<c:choose>						
							<c:when test='${imsResendEmailHistoryUI.noBooking == "Y"}'>
								${imsResendEmailHistoryUI.appntStartDateDisplay}
							</c:when>
							<c:otherwise>
								${imsResendEmailHistoryUI.appntStartDateDisplay} - ${imsResendEmailHistoryUI.appntEndDateDisplay}	
							</c:otherwise>
						</c:choose>						
					</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">Application Date:</th><td>${imsResendEmailHistoryUI.appDate}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">Target Commencement Date:<br>(for Pre-Installation Option only)</th><td>${imsResendEmailHistoryUI.targetCommDate}</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" >Payment Method</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px">Payment Method:</th><td>
					<c:choose><c:when test='${imsResendEmailHistoryUI.account.payment.payMtdType == "M"}'>Cash</c:when>
							<c:when test='${imsResendEmailHistoryUI.account.payment.payMtdType == "C"}'>Credit Card</c:when>
					</c:choose>
									<c:choose>
										<c:when test='${imsResendEmailHistoryUI.account.payment.payMtdType == "C"}'>
															<c:choose>
																<c:when test='${imsResendEmailHistoryUI.account.payment.thirdPartyInd == "Y"}'>
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
					<c:when test='${imsResendEmailHistoryUI.account.payment.payMtdType == "C"}'>
						<tr align="left" height="20px">
							<th align="left">Credit Card Type:</th>
							<c:choose><c:when test='${imsResendEmailHistoryUI.account.payment.ccType == "V"}'>
								<td>VISA</td>
							</c:when>
							<c:when test='${imsResendEmailHistoryUI.account.payment.ccType == "M"}'>
								<td>MASTER CARD</td>
							</c:when>
							<c:when test='${imsResendEmailHistoryUI.account.payment.ccType == "A"}'>
								<td>American Express</td>
							</c:when></c:choose></tr>
						<tr align="left" height="20px">
							<th align="left">
								Credit Card No.:</th><td>${imsResendEmailHistoryUI.account.payment.ccNum}</td></tr>
						<c:choose>
							<c:when test='${imsResendEmailHistoryUI.account.payment.thirdPartyInd == "Y"}'>
								<tr align="left" height="20px">
									<th align="left">Credit Card Holder Doc Type:</th><td>
									<c:choose><c:when test='${imsResendEmailHistoryUI.account.payment.ccIdDocType == "PASS"}'>
									PASSPORT</c:when>
									<c:when test='${imsResendEmailHistoryUI.account.payment.ccIdDocType == "BS"}'>
									HKBR</c:when>
									<c:otherwise>
									${imsResendEmailHistoryUI.account.payment.ccIdDocType}
									</c:otherwise></c:choose>
									</td></tr>
								<tr align="left" height="20px">
									<th align="left">
										Credit Card Holder Doc No.:</th><td>${imsResendEmailHistoryUI.account.payment.ccIdDocNo}</td></tr>
							</c:when>
							<c:otherwise>
							
							</c:otherwise>
						</c:choose>
						<tr align="left" height="20px">
							<th align="left">
								Cardholder Name:<br>(in English)</th><td>${imsResendEmailHistoryUI.account.payment.ccHoldName}
									</td>
						</tr>
						<tr align="left" height="20px">
							<th align="left">
								Expiry Date:</th><td>${imsResendEmailHistoryUI.account.payment.ccExpDate}</td></tr>
						<tr align="left" height="20px">
							<tr align="left" height="20px">
							<th align="left">
								Credit Card Prepayment of:</th><td>$${imsResendEmailHistoryUI.prepayAmt}<c:if test='${ImsOrder.waivedPrepay == "Y"}'>(Waived)</c:if></td></tr>
					</c:when>
					<c:when test='${imsResendEmailHistoryUI.account.payment.payMtdType == "M"}'>
						<c:choose><c:when test='${imsResendEmailHistoryUI.cashFsPrepay == "N"}'>
						<tr align="left" height="20px">
							<th align="left">
								Cash Prepayment of:</th><td>$${imsResendEmailHistoryUI.prepayAmt}<c:if test='${ImsOrder.waivedPrepay == "Y"}'>(Waived)</c:if></td></tr>
						</c:when>
						<c:when test='${imsResendEmailHistoryUI.cashFsPrepay == "Y"}'>
						<tr align="left" height="20px">
							<th align="left">
								FS Collection of Cash:</th><td>$${imsResendEmailHistoryUI.prepayAmt}<c:if test='${ImsOrder.waivedPrepay == "Y"}'>(Waived)</c:if></td></tr>
						</c:when></c:choose>
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				</c:choose>
				<tr align="left" height="20px">
					<th align="left">
						Installation /Activation Service Charge:</th>
								<td>$${imsResendEmailHistoryUI.waivedOtInstallChrg}</td>
				</tr>
				<tr align="left" height="20px">
					<th align="left">
					Bill Media:</th><td><c:choose>
											<c:when test='${imsResendEmailHistoryUI.account.billMedia == "P"}'>
												By Paper
											</c:when>
											<c:when test='${imsResendEmailHistoryUI.account.billMedia == "E"}'>
												By E-Mail ${imsResendEmailHistoryUI.account.emailAddr}
											</c:when>
										</c:choose></td></tr>
			</table>
			</td>
		</tr>
		
<!--	kinman	-->
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
				<td class="table_title" >Distribution Mode</td>
				</tr>
			</table>
			</td>
		</tr>
		
		<tr style="display: none;">
			<td>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
				<tr align="left" height="20px">
					<th align="left" width="250px">Document Attachment Generated by</th><td>
					<c:choose><c:when test='${imsResendEmailHistoryUI.disMode == "E"}'>Digital Signature</c:when>
							<c:when test='${imsResendEmailHistoryUI.disMode == "P"}'>Paper</c:when>
					</c:choose>
					</td>
					
				</tr>
				
				<c:if test='${imsResendEmailHistoryUI.disMode == "E"}'>	
				<tr align="left" height="20px">
					<th align="left" width="250px">Email</th>
					<td><b style="color:red">${imsResendEmailHistoryUI.esigEmailAddr}</b></td>
				</tr>
				</c:if>
					
				<tr align="left" height="20px">
					<th align="left" width="250px">Application Form Language</th><td>
					<c:choose><c:when test='${imsResendEmailHistoryUI.langPreference == "CHI"}'>Tranditional Chinese</c:when>
							<c:when test='${imsResendEmailHistoryUI.langPreference == "ENG"}'>English</c:when>
					</c:choose>
					</td>
				</tr>
				
			</table>
			</td>
		</tr>	
		
		<tr><td></td></tr><tr><td></td></tr>
		
		
		<c:if test='${imsResendEmailHistoryUI.disMode == "E"}'>
			<!--   		Sending Email History -->
		<tr>
			<td class="table_title" >Order Send Email History</td>
		</tr>	
  		<tr>
  			<td>
  			<div class="previewForm sendForm contenttext">
  				<table class="histList">
  					<colgroup style="width:45px"></colgroup>
					<colgroup style="width:250px"></colgroup>
					<colgroup style="width:200px"></colgroup>
					<colgroup style="width:125px"></colgroup>
					<thead class="header">
					<tr>
						<th>Seq #</th>
						<th>Email Address</th>
						<th>Request Date</th>
						<th>Request By</th>
						<th>Result</th>
					</tr>
					</thead>
					<tbody>
						<c:choose>
						<c:when test="${emailHistoryList == null}"></c:when>
						<c:when test="${empty emailHistoryList}">
						<tr>
							<td colspan="5" style="text-align: left">No records</td>
						</tr>
						</c:when>
						<c:otherwise>
						<c:forEach items="${emailHistoryList}" var="record" varStatus="status">
						<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 20}"> style="display:none"</c:if>>
							<td><c:out value="${record.seqNo}"/></td>
							<td><c:out value="${record.emailAddr}"/></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.requestDate}"/></td>
							<td><c:out value="${record.createBy}"/></td>
							<td style="text-align:left">
								<c:choose>
									<c:when test="${record.sentDate == null}">
										<c:out value="${record.errMsg}"/>
									</c:when>
									<c:otherwise>
										Sent on <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.sentDate}"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:forEach>
						</c:otherwise>
						</c:choose>
					</tbody>
  				</table>
  				
  				<c:if test="${not empty emailHistoryList}">
				<div style="text-align: center; padding: 5px 0">
					<span class="control">
						<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
						<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
						<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
					</span>
				</div>
				
  				
  				<div style="clear:both"></div>
  				
  				<tr>
					<td class="table_title" >Preview Resend Email Content</td>
				</tr>	
  				<tr><td>
  				
					<c:choose>
						<c:when test="${emailException == null}">
						
						<div class="wrapContent">
							<div class="content">
								<div class="row">
									<span class="label">Subject:</span>
									<span class="input"><c:out value="${emailSubject}"/></span>
								</div>
								<div class="row">
									<span class="label">To:</span>
									<span class="input">
										<form:input path="esigEmailAddr" maxlength="64" cssStyle="width: 20em"/>						
										<span class="error error_esigEmailAddr" style="display:none"></span>
									</span>
								</div>
								<div style="clear:both"></div>
								<div id="emailContent"></div>
								<pre id="emailContentSrc" style="display:none"><c:out value="${emailContent}"/></pre>
								
							</div>
						</div>
						
							<c:if test="${imsResendEmailHistoryUI.saleResendEmailAllowed}">
								<div class="buttonmenubox_R">			
		<!-- 							action -->
									<input type="hidden" name="action" value="SEND">
		<!-- 							emailSendConfirm -->
									<form:hidden path="emailSendConfirm"/>
									<a href="#" class="nextbutton3 sendBtn">Send Email</a>
									
								</div>
							</c:if>
						</c:when>
						
						<c:otherwise>
<!-- 						in case exception generated from groovy -->
						
						<div class="wrapContent">
							<div class="content">
								<pre><c:out value="${emailException.message}"/>
								<c:forEach var="s" items="${emailException.stackTrace}">
								<c:out value="${s}"/></c:forEach></pre>
							</div>
						</div>
						</c:otherwise>
					</c:choose>

				
				</td></tr>
				</c:if>
  			</div>
  			</td>
  		</tr>
<!--   		 Sending Email History End-->
		
		</c:if>
		

		</table>	     	     
      </td>
  </tr>
</table>
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>