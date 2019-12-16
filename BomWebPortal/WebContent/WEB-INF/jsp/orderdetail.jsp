<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<%@ taglib prefix="pccw-ui" tagdir="/WEB-INF/tags/ui"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils,com.bomwebportal.dto.BomSalesUserDTO,com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO,com.bomwebportal.bean.LookupTableBean,com.bomwebportal.mob.ccs.dto.CodeLkupDTO,com.bomwebportal.util.ConfigProperties,java.util.*,java.util.Locale"
%>

<%@ page import="com.bomwebportal.util.Utility"%>
<%
request.setAttribute("deviceType", Utility.getDeviceType(request));
%>
<%
String mobCosUrl = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");
request.setAttribute("mobCosUrl", mobCosUrl);
%>
<%
	Map<String, List<CodeLkupDTO>> codeLkupList = LookupTableBean
			.getInstance().getCodeLkupList();
	List<CodeLkupDTO> dtos = codeLkupList.get("IPAD_VERSION");
	String ipadVersion = "";
	if (dtos != null && dtos.size() > 0) {
		ipadVersion = dtos.get(0).getCodeId();
	}

	String scheme = ConfigProperties
			.getPropertyByEnvironment("orddoc.ipad.uri.scheme");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>PCCW Mobile</title>
<link href="css/ssc.css" rel="stylesheet" type="text/css">
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<link href="css/imsds.css" rel="stylesheet" type="text/css"> 
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<sb-util:codelookup var="vipKeyMap" codeType="PRJ_7_VIP"  asEntryList="true" />
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">	
<script src="js/common.js" language="javascript"></script>
<script src="js/jquery-1.10.2.js"></script>
	<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.min.css"
		  rel="stylesheet" />
		<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
		<script src="<c:url value='/js/jquery.validate.js'/>"></script>
<%
	Locale locale = RequestContextUtils.getLocale(request);
	request.setAttribute("language", locale.toString());
	BomSalesUserDTO bomsalesuser = (BomSalesUserDTO) request
			.getSession().getAttribute("orderdetailBomsalesuser");
	String salesChannelId = (String) request
			.getSession().getAttribute("salesChannelId");
	String dsApprovable = (String) request
			.getSession().getAttribute("dsApprovable");
	//String enableOnloadJScript =  request.getParameter("enableOnloadJScript");
%>
<%@ include file="loadingpanel.jsp" %>
<script language="Javascript">
	function submitToWindow(form) {
		
		if ($(form).valid()) {
		  var target = form.target;
	      var w = window.open('about:blank', target, 'height=' + (screen.height - 70) + ',width=' + (screen.width - 10) + '=1,resizable=yes,scrollbars=yes');
	      form.submit();
		}
	
	  return false;
	}
	
	function formSubmit(actionType){
		window.location ="welcome.html";
	}
	
	function mobDsFuncRight(func) {
		var orderStatus = "${orderDto.orderStatus}";
		var role = "${salesUserDto.category}";
		if (func == "ResendEmail") {
			if (orderStatus == "REVIEWING" || orderStatus == "SUCCESS") {
				if (role == "FRONTLINE" || role == "SUPERVISOR" || role == "ORDER SUPPORT") {
					return true;
				}
			}
		} else if (func == "Remark") {
			return true;
		} else if (func == "CaptureProof") {
			if (orderStatus == "REVIEWING" || orderStatus == "SUCCESS" || orderStatus == "REJECTED" || orderStatus == "FALLOUT") {
				if (role == "FRONTLINE" || role == "SUPERVISOR" || role == "ORDER SUPPORT") {
					return true;
				}
			}
			if (orderStatus == "INITIAL") {
				if (role == "FRONTLINE" || role == "SUPERVISOR") {
					return true;
				}
			}
			if (orderStatus == "FAILED") {
				if (role == "FRONTLINE") {
					return true;
				}
			}
		} else if (func == "Fallout") {
			if (orderStatus == "INITIAL") {
				if (role == "ORDER SUPPORT") {
					return true;
				}
			}
			if (orderStatus == "REVIEWING" || orderStatus == "SUCCESS" || orderStatus == "FALLOUT") {
				if (role == "ORDER SUPPORT" || role == "QA TEAM") {
					return true;
				}
			}
			if (orderStatus == "CANCELLED" || orderStatus == "REJECTED") {
				if (role == "QA TEAM") {
					return true;
				}
			}
		} else if (func == "QARecord") {
			if (orderStatus == "REVIEWING" || orderStatus == "SUCCESS") {
				if (role == "ORDER SUPPORT") {
					return true;
				}
			}
			if (orderStatus == "QAPROCESS") {
				if (role == "ORDER SUPPORT" || role == "QA TEAM") {
					return true;
				}
			}
		} else if (func == "SupportDoc") {
			if (orderStatus == "INITIAL" || orderStatus == "FAILED" || orderStatus == "REVIEWING" || orderStatus == "REJECTED" || orderStatus == "FALLOUT") {
				return true;
			}
			if (orderStatus == "CANCELLED" || orderStatus == "SUCCESS") {
				if (role == "SUPERVISOR" || role == "ORDER SUPPORT" || role == "QA TEAM") {
					return true;
				}
			}
			if (orderStatus == "QAPROCESS") {
				if (role == "ORDER SUPPORT" || role == "QA TEAM") {
					return true;
				}
			}
		} else if (func == "Upfront") {
			if (orderStatus == "INITIAL" || orderStatus == "FAILED" || orderStatus == "REJECTED") {
				if (role == "FRONTLINE") {
					return true;
				}
			}
			if (orderStatus == "REVIEWING") {
				if (role == "SUPERVISOR" || role == "ORDER SUPPORT" || role == "FRONTLINE") {
					return true;
				}
			}
			if (orderStatus == "SUCCESS" || orderStatus == "FALLOUT" || orderStatus == "QAPROCESS") {
				if (role == "ORDER SUPPORT") {
					return true;
				}
			}
		} else if (func == "SBPOS") {
			if (orderStatus == "SUCCESS") {
				if (role == "ORDER SUPPORT") {
					return true;
				}
			}
		}
		return false;
	}
	
	function orderReviewFormSubmit(actionType) {
		if (actionType == "REJECT") {
			<%if ("SUPERVISOR".equals(bomsalesuser.getCategory())){ %>
				var reason = prompt("Reject Reason: ", "");
				if (reason) {
					document.orderReviewForm.reason.value = reason;
					document.orderReviewForm.actionType.value = actionType;
					document.orderReviewForm.submit();
				} else {
					alert("Please input the reject reason first!");
				}
			<% } else {%>
		if (confirm('Confirm to ' + actionType + ' the order?')) {
			document.orderReviewForm.actionType.value = actionType;
			document.orderReviewForm.submit();
		}
			<% } %>
		} else if (actionType == "APPROVE"){
			showLoading();
			$.ajax({
				url : 'mobdsapprove.html'
				, type : 'POST'
				, dataType : 'JSON'
				, timeout : 5000
				, error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert('Error in checking review order.');
				  }
				, success : function(msg) {
					hideLoading();
					if (msg == null || msg.dsApproveMessage != null) {
						alert(msg.dsApproveMessage + "\nNot allow to approve.");
					} else {
						if(document.getElementById("paymentCheckBox")){// check the existence of the check box
							if ($('#paymentCheckBox').is(':checked')) {
								<c:choose>
								<c:when test="${empty orderDto.paymentCheck || orderDto.paymentCheck == 'N'}">
									alert("Please save the payment check first!");
								</c:when>
								<c:otherwise>
									if (confirm('Confirm to ' + actionType + ' the order?')) {
										document.orderReviewForm.actionType.value = actionType;
										document.orderReviewForm.submit();
									}
								</c:otherwise>
								</c:choose>
							} else {
								alert("Please check the payment first!");
							}
						} else {
							if (confirm('Confirm to ' + actionType + ' the order?')) {
								document.orderReviewForm.actionType.value = actionType;
								document.orderReviewForm.submit();
							}
						}
					}
					
				}
			});
			
		}
	}
	
	function saveCheck() {
		var paymentCheck = "N";
		var supportDocCheck = "N";
		if ($('#paymentCheckBox').is(':checked')) {
			paymentCheck = "Y";
		}

		if ($('#supportDocCheckBox').is(':checked')) {
			supportDocCheck = "Y";
		}
		window.location = "orderdetail.html?orderId=${orderId}&action=<%=request.getParameter("action")%>&appInd=<%=request.getParameter("appInd")%>&paymentCB="+paymentCheck+"&supportDocCB="+supportDocCheck;
	}
	
	function amendAF(orderId){
		if (confirm('Confirm to add "Amend AF" support doc?')) {
			$.ajax({
				url: "amendaf.html"
				, data: {
					orderId: orderId
				}
				, cache: false
				, dataType: "json"
				, success: function(result) {
					$.each(eval(result),
						function(index, item) {
							if (item.insertResult == "2") {
								alert('"Amend AF" support doc is added successfully!');
								window.location = "orderdetail.html?sbuid=&orderId=" + orderId + "&action=ENQUIRY";
							} else {
								alert('"Amend AF" support doc is NOT added!');
							}
						}
					);
				}
				, error: function() {
					alert("Add Amend AF support doc error found, please retry!");
				}
			});
		}
	}
	$(document).ready(function() {
		<c:if test="${orderDto.paymentCheck == 'Y'}">
			$('#paymentCheckBox').attr("checked", true);
		</c:if>
		<c:if test="${orderDto.paymentCheck == 'N'}">
			$('#paymentCheckBox').attr("checked", false);
		</c:if>
		<c:if test="${orderDto.supportDocCheck == 'Y'}">
		    $('#supportDocCheckBox').attr("checked", true);
	   </c:if>
	   <c:if test="${orderDto.supportDocCheck == 'N'}">
	        $('#supportDocCheckBox').attr("checked", false);
       </c:if> 
       
		var totalMonthPay = 0;
		$('.monthPay').each(function() {
		var monthPay = parseFloat($(this).text());
		totalMonthPay += monthPay;
		});
		$("#totalMonthPay").html(totalMonthPay);	
		
		var url = "<%=scheme%>://${orderId}/${salesUserDto.username}/${currentSessionId}/<%=ipadVersion%>";

		$('#qrCode').qrcode({
					width: 250, height: 250,
					render: "table",
					text: url
				});
		<c:if test="${salesUserDto.channelId == '10' || salesUserDto.channelId == '11'}">
		if (!mobDsFuncRight("ResendEmail")) {$('.btnResend').hide();}
		if (!mobDsFuncRight("Remark")) {$('.btnRemark').hide();}
		if (!mobDsFuncRight("Upfront")) {$('.btnUpfront').hide();}
		if (!mobDsFuncRight("CaptureProof")) {$('.btnCaptureProof').hide();}
		if (!mobDsFuncRight("Fallout")) {$('.btnFallout').hide();}
		if (!mobDsFuncRight("QARecord")) {$('.btnQARecord').hide();}
		if (!mobDsFuncRight("SBPOS")) {$('.btnSBPos').hide();}
		if (!mobDsFuncRight("SupportDoc")) {$('.btnSupportDoc').hide();}
		</c:if>
	});
	
	function showQRCodeDialog() {
	
		$("#qrCodeDialog").dialog({ 
			modal: true
			, width: 350
			, resizable: false
			, draggable: false
			
		});
		
	}
	
	function upfrontPayment(upfrontPaymentUrl){
		$('#upfrontPaymentDialog').dialog({
		        autoOpen: false,
		        modal: true,
		        height: 550,
		        width: 850,
		        title: "Upfront Payment",
		        open: function(ev, ui){ 
		        	$('#upfrontPaymentIframe').attr('src',upfrontPaymentUrl);
		        	  $("#upfrontPaymentIframe").scrollTop(0);

		        }
		    });
		$('#upfrontPaymentDialog').dialog('open');	
		event.preventDefault(); 
	}
	
</script> 
<body > 
<div id="container">
<div id="head">
<div id="header">
	<table width="100%" border="0" cellspacing="0">  			
          	 
  					<tr>
        				<td><img src="images/topbar_left.png" width="470" height="58"></td>
        				<%-- <%
        					if (bomsalesuser != null) {
        				%>
        				<td align="right"> <a href="welcome.html">Home</a> | <a href="logout.html">Logout</a></td>
        				<%
        					}
        				%>
          				<td align="right">
  						<div class="lang"> English</div>
  					   	</td> --%>
  					</tr>
	</table>
</div>
<div id="wrapper">
<form name="orderdetail" method="post" action="orderdetail.html?sbuid=${sbuid}">

 <!-- start table-->
 <table width="100%" border="1">
 <tr>
							<td class="table_title">Order Detail</td>
						</tr>
		<tr>
			<td><!--content-->
			<!-- Title table-->
			<table width="100%" border="0" cellspacing="0">
				<tr>
					<td class="contenttextgreen" height="40">
					Order Id: ${orderDto.orderId} / Application Date: <fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${orderDto.appInDate}"/></td>
					<td class="contenttextgary"></td>
				</tr>
			</table>
			
			<!-- Customer's Information table -->

			<table width="100%" border="0" cellspacing="0" class="contenttext">

				<tr>
					<td colspan="4" class="table_title">Customer's Information</td>
				</tr>
				<tr >
				<td valign="top" bgcolor="#FFFFFF">
				
				
				<table width="100%" border="0" cellspacing="1">
				
				<c:if test='${not empty customerInfoDto.brand }'> 
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td width="25%"><b>Brand Type</b></td>
					<td colspan="2" class="contenttext_blk"> 
						<c:choose>
							<c:when test='${customerInfoDto.brand eq "1"}'>
								<label for="logo"><img style="height:30px;vertical-align:middle" src="images/csl_logo.jpg"/></label>
							</c:when>
							<c:when test='${customerInfoDto.brand eq "0"}'>
								<label for="logo"><img style="height:30px;vertical-align:middle" src="images/1010_logo.jpg"/></label>
							</c:when>
							<c:otherwise>
								${customerInfoDto.brand}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				</c:if>
				
				<c:if test='${not empty customerInfoDto.simType  }'> 
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td width="25%"><b>SIM Type</b></td>
					<td colspan="2" class="contenttext_blk"> 
						<pccw-util:codelookup codeType="MIP_SIM_TYPE" codeId="${customerInfoDto.simType}" />	
					</td>
				</tr>
				</c:if>
				
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td width="25%"><b>Document Type</b></td>
					<td colspan="2" class="contenttext_blk"> <!--add by eliot 20110609--> <c:choose>
						<c:when test='${customerInfoDto.idDocType == "BS"}'>
						HKBR
						</c:when>
						<c:when test='${customerInfoDto.idDocType == "PASS"}'>
						Passport
						</c:when>
						<c:otherwise>
						HKID
						</c:otherwise>
					</c:choose></td>
				</tr>

				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td><b>Document Number</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.idDocNum}</td>
				</tr>
				
				<c:choose>
				<c:when test='${customerInfoDto.idDocType == "BS"}'>
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td><b>Company Name</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.companyName}</td>
				</tr>
				
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td width="25%"><b>Name of Authorized Representative</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.title} ${customerInfoDto.contactName}</td>
				</tr>
				
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td width="25%"><b>Document No. of Authorized Representative</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.repIdDocNum}</td>
				</tr>
				</c:when>
				
				<c:otherwise>
				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td><b>Title</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.title}
					</td>
				</tr>

				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td width="25%"><b>Name in English</b></td>
					<!--add by eliot 20110609-->

					<td colspan="2" class="contenttext_blk">${customerInfoDto.custLastName}
					${customerInfoDto.custFirstName}</td>
				</tr>				

				<tr class="contenttext">
					<td width="6%">&nbsp;</td>
					<td><b>Date of birth</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.dobStr}</td>
				</tr>
				
				<tr class="contenttext">
									<td width="6%">&nbsp;</td>
									<td><b>Student Plan Sub</b></td>
									<td colspan="2" class="contenttext_blk">
										<c:choose>
											<c:when test='${customerInfoDto.studentPlanSubInd}'>
												Yes
											</c:when>
											<c:otherwise>
												No
											</c:otherwise>
										</c:choose>
									</td>
				</tr>
								
				</c:otherwise>
				</c:choose>
				
				<tr>
					<td width="6%">&nbsp;</td>
					<td><b>Contact phone no.</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.contactPhone}</td>
				</tr>

				<%-- add by herbert 20110720 start--%>
				<tr>
					<td width="6%">&nbsp;</td>
					<td><b>Other contact no.</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.otherContactPhone}</td>
				</tr>
				<%-- add by herbert 20110720 end--%>

				<tr>
					<td width="6%">&nbsp;</td>
					<td><b>Email address</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.emailAddr}</td>
				</tr>
				<c:if test="${customerInfoDto.csPortalInd == 'Y' || customerInfoDto.csPortalInd == 'D'}">
				<tr>
						<td></td>
						<td></td>
						<c:choose>
							<c:when test="${customerInfoDto.csPortalStatus == 'C'}">
								<td class="contenttext_blk"><spring:message code="label.csPortalHKT" />
							</c:when>
							<c:when test="${customerInfoDto.csPortalStatus == 'H'}">
								<td class="contenttext_blk"><spring:message code="label.csPortalClub" />
							</c:when>
							<c:otherwise>
								<td class="contenttext_blk"><spring:message code="label.csPortalAll" />
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</c:if>
				<tr>
					<td width="6%">&nbsp;</td>
					<td><b>Registered Address</b></td>
					<td colspan="2" class="contenttext_blk">${customerInfoDto.address}</td>
				</tr>
				<c:if test='${customerInfoDto.addrProofInd == "5"}'> 
					<tr>
						<td width="6%">&nbsp;</td>
						<td><b>Pre-Activation</b></td>
						<td colspan="2" class="contenttext_blk">
							<c:if test='${customerInfoDto.bomCustAddrOverrideInd == "Y"}'> 
								System will override the BOM customer register address during order sign-off.
							</c:if>
						</td>
					</tr>
				</c:if>
				<!--Start of Combine Account Info  -->
				<tr>
					<td width="6%">&nbsp;</td>
					<td><b>Account Type</b></td>
					<c:choose>
						<c:when test='${customerInfoDto.acctType == "current"}'>
							<td colspan="2" class="contenttext_blk">Current
								Account</td>
							<tr>
								<td width="6%">&nbsp;</td>
								<td><b>Active Mobile no.</b></td>
								<td colspan="2" class="contenttext_blk">${customerInfoDto.srvNum}</td>
							</tr>
							<!--customerInfoDto.activeMobileNum  -->
							<tr>
								<td width="6%">&nbsp;</td>
								<td><b>Account no.</b></td>
								<td colspan="2" class="contenttext_blk">${customerInfoDto.acctNum}</td>
							</tr>
						</c:when>
						<c:otherwise>
							<td colspan="2" class="contenttext_blk">New Account</td>
						</c:otherwise>
					</c:choose>
				</tr>

				<!--End of Combine Account Info  -->
												<tr>
					<td width="6%">&nbsp;</td>
					<td><b>Correspondence / Billing Address</b></td>
					<td colspan="2" class="contenttext_blk"><c:if
						test='${customerInfoDto.noBillingAddressFlag!=true }'> 
		                	${customerInfoDto.billingAddress}
		               	</c:if> <c:if
						test='${customerInfoDto.noBillingAddressFlag == true}'> 
			                 	same as above address
			            </c:if></td>
				</tr>
				<tr>
					<td width="6%">&nbsp;</td><!-- Paper bill Athena 20130925 start-->
					<td><b>Bill Medium</b></td>
					<td colspan="2" class="contenttext_blk"> SMS
					             <c:forEach var="billMedia" items="${selectedBillMediaList}">    
		 							 /<c:out value="${billMedia.codeDesc}"/>
								</c:forEach>				
					</td><!-- Paper bill Athena 20130925 end -->
				</tr>
				</table>
				</td>
				</tr>

				
			</table>
			
	  <!-- ------------------ Actual User Table ----------------------------- -->
	  
      	<sb-ui:actualuserinfo value="${customerInfoDto.actualUserDTO}" sameascust="${customerInfoDto.sameAsCust}" />
      
      <!-- ------------------ Actual User Table ----------------------------- -->



			<!-- package Summary table -->
			<table width="100%" border="1" cellspacing="0">
				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1" class="contenttext">
						<tr>
							<td class="table_title">Package Summary</td>
						</tr>
						<tr>
							<td><!-- image and price table -->
							<table cellspacing="0" class="contenttext" width="100%">
								<tr>
									<td class="contenttextgary" valign="middle"><!-- image here -->
									<p class="contenttextgreen"><c:if
										test='${mobileDetail.modelName != null}'>
										<td class="contenttextgary" valign="middle"><!-- image here -->
										<p class="contenttextgreen"><img
											src="${mobileDetail.modelImagePath}"
											alt="${mobileDetail.modelName}" width="80" /></p>
										<span class="posttext">${mobileDetail.modelName}</span> <!-- end of image --></td>
									</c:if> <!-- end of image -->
									</td>
									<td><!-- price table here -->
									<table cellspacing="1" class="contenttext" style="width: 100%">
										<colgroup></colgroup>
										<colgroup style="width: 85px; text-align: right; vertical-align: top"></colgroup>
										<colgroup style="width: 75px; text-align: right; vertical-align: top"></colgroup>
										<tr>
											<td class="table_title"></td>
											<td class="table_title" style="text-align: center">Monthly</td>
											<td class="table_title" style="text-align: center">Upfront</td>
										</tr>
										<c:set var="vasOnetimeAmt" value="0"/>
										<c:set var="vasOnetimeHSAmt" value="0"/>
										<c:set var="simOneTimeAmt" value="0"/>
										<c:set var="rpOneTimeAmt" value="0"/>
										<c:set var="rpOneTimeAmtWithoutWaive" value="0"/>
										
										<c:forEach items="${vasHSRPList}" var="vasHSRP">
											<tr>
												<td class="contenttext">${vasHSRP.itemHtml}</td>
												<td class="BGgreen2" align="right">
													<c:if test='${not empty vasHSRP.itemRecurrentAmt && vasHSRP.itemRecurrentAmt != 0}'>
														$<span class="monthPay"><fmt:formatNumber value="${vasHSRP.itemRecurrentAmt}" pattern="#,###.####" /></span>/month
													</c:if>
												</td>
												<td class="BGgreen2" align="right">
													<c:if test='${not empty vasHSRP.itemOnetimeAmt && vasHSRP.itemOnetimeAmt != 0}'>
														$<fmt:formatNumber value="${vasHSRP.itemOnetimeAmt}" pattern="#,###.####" />
														<c:if test="${vasHSRP.itemType == 'VAS'}">
							                              <c:choose>
							                              <c:when test="${vasHSRP.itemPaymentGroup == 'HANDSET'}">
							                                <c:set var="vasOnetimeHSAmt" value="${vasOnetimeHSAmt + vasHSRP.itemOnetimeAmt}"/>
							                              </c:when>
							                              <c:otherwise>
							                                <c:set var="vasOnetimeAmt" value="${vasOnetimeAmt + vasHSRP.itemOnetimeAmt}"/>
							                              </c:otherwise>
							                              </c:choose>
							                            </c:if>
							                            <c:if test="${vasHSRP.itemType == 'SIM'}">
															<c:if test="${empty mobileSimInfo.simWaiveReason}">
																<c:set var="simOneTimeAmt" value="${simOneTimeAmt + vasHSRP.itemOnetimeAmt}"/>
															</c:if>
														</c:if>
														<c:if test="${vasHSRP.itemType == 'RP'}">
																<c:set var="rpOneTimeAmtWithoutWaive" value="${rpOneTimeAmtWithoutWaive + vasHSRP.itemOnetimeAmt}"/>
																<c:if test="${empty mobileSimInfo.rpWaiveReason}">
																	<c:set var="rpOneTimeAmt" value="${rpOneTimeAmt + vasHSRP.itemOnetimeAmt}"/>
																</c:if>
														</c:if>
													</c:if>
												</td>
											</tr>
										</c:forEach>
					
									</table>

									<!-- end of price table  --></td>
								</tr>
							</table>
							<!-- end of image and price table --></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>

		<!-- Vas Attribute Table (start) -->
			<c:set var="vasAttbCount" value="0"/>	
			<c:forEach items="${componentList}" var="componentDTO">
						<c:if test="${componentDTO.compAttbId ne 'this.window.GUID'}">
							<c:set var="vasAttbCount" value="${vasAttbCount + 1 }"/>
						</c:if>
			</c:forEach>
			<c:if test="${vasAttbCount gt 0 }">
			<table width="100%" border="0" cellspacing="0" class="contenttext">	
					<tr>
						<td class="table_title">Vas Attribute Summary</td>
					</tr>
					<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1">
					<c:forEach items="${componentList}" var="componentDTO" varStatus="itemStatus">
						<c:if test="${componentDTO.compAttbId ne 'this.window.GUID'}">
						<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><sb-util:attblookup  codeType="ATTB_LKUP" codeId="${componentDTO.compAttbId }"  local="en"  /></b>
									</td>
									<td>
									<span class="contenttext_blk">
									<c:set var="containsVipKey" value="false" />
									<c:forEach var="vipKey" items="${vipKeyMap}">
									  <c:if test="${componentDTO.compAttbVal eq vipKey.key}">
									    <c:set var="containsVipKey" value="true" />
									  </c:if>
									</c:forEach>
									<c:choose>
									<c:when test="${not containsVipKey}">
									<sb-util:attblookup  codeType="ATTB_INFO_DIC" codeId="${componentDTO.compAttbId }"   attbInfoValue ="${componentDTO.compAttbVal }" local="en"  />
									</c:when>
									<c:otherwise>
									****
									</c:otherwise>
									</c:choose>
									</span>
									</td>
						</tr>					
						</c:if>
					</c:forEach>
					</table>
					</td>
					</tr>				
			</table>
			</c:if>
			<!-- end of Vas Attribute -->

		<!-- Multi Sim Info -->
		<c:if test='${not empty MultiSimInfoList}'>
				<table width="100%" border="0" cellspacing="0" class="contenttext">
					<tr>
						<td class="table_title">Multi Sim Info</td>
					</tr>
					<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table width="100%" border="0" cellspacing="1">
								<tr>
									<td class="table_title table_title_2row" style="text-align: left">
										<span style="text-align: left">
											<spring:message code="label.basket.desc"/>
											<br><spring:message code="label.basket.desc.1"/>
										</span>
									</td>
									<td class="table_title table_title_2row">
										<span>
											<spring:message code="label.basket.monthly"/>
											<br><spring:message code="label.basket.monthly.1"/>
										</span>
									</td>
								</tr>
								<c:forEach items="${MultiSimInfoList}" var="msi">
									<c:forEach items="${msi.bundleItemList}" var="ptList" varStatus="ptRow">
										<tr style="background-color: #F0F0F0"><td width="69%">${ptList.itemHtml}</td>
										<td style="width:8%; background-color: #F0F0F0; text-align:right"><c:if test='${not empty ptList.itemRecurrentAmt && ptList.itemRecurrentAmt != 0}'>
											$<fmt:formatNumber value="${ptList.itemRecurrentAmt}" pattern="#,###.####" /> / month
										</c:if></td>
										</tr>
									</c:forEach>
								</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table width="100%" border="0" cellspacing="1">
								<c:forEach items="${MultiSimInfoList}" var="msi">
									<c:forEach items="${msi.members}" var="msim">
										<tr class="table_title">
											<td width="25%"><b>Multi SIM MRT ${msim.memberNum}</b></td>
											<td style="float: right">
												<c:if test='${not empty msim.ocid}'>OCID: <span style="color: red">${msim.ocid}</span><span style="word-spacing:30px">&nbsp;</span></c:if>
												<c:if test='${not empty msim.memberStatus}'>Status: <span style="color: red">
												<c:if test='${msim.memberStatus == 400}'>Pending</c:if>
												<c:if test='${msim.memberStatus == 449}'>Process</c:if>
												<c:if test='${msim.memberStatus == 500}'>Success</c:if>
												<c:if test='${msim.memberStatus == 800}'>Cancelling</c:if>
												<c:if test='${msim.memberStatus == 899}'>Cancelled</c:if>
												<c:if test='${msim.memberStatus == 999}'>Fail</c:if></span>
												<span style="word-spacing:30px">&nbsp;</span></c:if>
												<c:if test='${not empty msim.errMsg}'>Error: <span style="color: red">${msim.errMsg}</span><span style="word-spacing:30px">&nbsp;</span></c:if>
											</td>
										</tr>
										<c:if test='${msim.memberOrderType == "MUPS01"}'>
											<c:if test='${msim.mnpInd != null}'>
												<c:if test='${msim.mnpInd != "Y"}'>
												<tr>
													<td><b>New Number</b></td>
												</tr>
												<tr>
													<td>Sim Number</td>
													<td><span class="contenttext_blk">${msim.sim.iccid}</span></td>
												</tr>
												<tr>
													<td>New Number</td>
													<td><span class="contenttext_blk">${msim.msisdn}</span></td>
												</tr>
												</c:if>
											</c:if>
											<c:if test='${msim.mnpInd == "A"}'>
												<tr>
													<td><b>New Number + Further MNP</b></td>
												</tr>
												<tr>
													<td>MNP</td>
													<td><span class="contenttext_blk">${msim.mnpNumber}</span></td>
												</tr>
												<tr>
													<td>MNP Name</td>
													<td><span class="contenttext_blk">${msim.mnpCustName}</span></td>
												</tr>
												<tr>
													<td>Prepaid sim</td>
													<c:choose>
														<c:when test='${msim.mnpCustName == "Prepaid Sim"}'>
															<td><span class="contenttext_blk">Y</span></td>
														</c:when>
														<c:otherwise>
															<td><span class="contenttext_blk">N</span></td>
														</c:otherwise>
													</c:choose>
												</tr>
												<tr>
													<td>MNP Doc No/Prepaid Sim No</td>
													<td><span class="contenttext_blk">${msim.mnpDocNo}</span></td>
												</tr>
											
												<tr>
													<td>Cut-over date</td>
													<td><span class="contenttext_blk">${msim.mnpCutOverDate}</span></td>
												</tr>
												<tr>
													<td>Cut-over time</td>
													<c:if test='${msim.mnpCutOverTime == "0"}'>
														<td><span class="contenttext_blk">1:00 - 4:00</span></td>
													</c:if>
													<c:if test='${msim.mnpCutOverTime == "1"}'>
														<td><span class="contenttext_blk">12:00 - 14:00</span></td>
													</c:if>
												</tr>
											</c:if>
										</c:if>
										
										<c:if test='${msim.memberOrderType == "MUPS02"}'>
											<c:if test='${msim.mnpInd != null}'>
												<c:if test='${msim.mnpInd != "Y"}'>
												<tr>
													<td><b>New Number + Further MNP</b></td>
												</tr>
												<tr>
													<td>Sim Number</td>
													<td><span class="contenttext_blk">${msim.sim.iccid}</span></td>
												</tr>
												<tr>
													<td>New Number</td>
													<td><span class="contenttext_blk">${msim.msisdn}</span></td>
												</tr>
												</c:if>
											</c:if>
											<c:if test='${msim.mnpInd == "A"}'>
												<tr>
													<td>MNP</td>
													<td><span class="contenttext_blk">${msim.mnpNumber}</span></td>
												</tr>
												<tr>
													<td>MNP Name</td>
													<td><span class="contenttext_blk">${msim.mnpCustName}</span></td>
												</tr>
												<tr>
													<td>Prepaid sim</td>
													<c:choose>
														<c:when test='${msim.mnpCustName == "Prepaid Sim"}'>
															<td><span class="contenttext_blk">Y</span></td>
														</c:when>
														<c:otherwise>
															<td><span class="contenttext_blk">N</span></td>
														</c:otherwise>
													</c:choose>
												</tr>
												<tr>
													<td>MNP Doc No/Prepaid Sim No</td>
													<td><span class="contenttext_blk">${msim.mnpDocNo}</span></td>
												</tr>
											
												<tr>
													<td>Cut-over date</td>
													<td><span class="contenttext_blk">${msim.mnpCutOverDate}</span></td>
												</tr>
												<tr>
													<td>Cut-over time</td>
													<c:if test='${msim.mnpCutOverTime == "0"}'>
														<td><span class="contenttext_blk">1:00 - 4:00</span></td>
													</c:if>
													<c:if test='${msim.mnpCutOverTime == "1"}'>
														<td><span class="contenttext_blk">12:00 - 14:00</span></td>
													</c:if>
												</tr>
											</c:if>
										</c:if>
										
										<c:if test='${msim.memberOrderType == "MUPS04"}'>
											<tr>
												<td><b>Migrate Current Line</b></td>
											</tr>
											<tr>
												<td>Current Mobile No: </td>
												<td>
													<span class="contenttext_blk">${msim.msisdn}</span>
													<a href="#" class="nextbutton" onClick="javascript:window.open('<%=mobCosUrl%>	natureselect/subscriberpreview.html?msisdn=${msim.msisdn}&_al=new', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">Sub Info</a>
												</td>
											</tr>
											<tr>
												<td><b>Current Profile</b></td>
											</tr>
											<tr>
												<td>Current Customer Name: </td>
												<td><span class="contenttext_blk">${msim.curTitle}</span> <span class="contenttext_blk">${msim.curLastName}</span> <span class="contenttext_blk">${msim.curFirstName}</span></td>
											</tr>
											<tr>
												<td>Current ID Doc Type: </td>
												<td><span class="contenttext_blk">${msim.curIdDocType}</span></td>
											</tr>
											<tr>
												<td>Current ID Doc No: </td>
												<td><span class="contenttext_blk">${msim.curIdDocNum}</span></td>
											</tr>
											<tr>
												<td>BOM Customer No: </td>
												<td><span class="contenttext_blk">${msim.curCustNo}</span></td>
											</tr>
											<tr>
												<td>Account No: </td>
												<td><span class="contenttext_blk">${msim.curAcctNo}</span></td>
											</tr>
											<tr>
												<td>Current SIM Info: </td>
												<td>
													<span class="contenttext_blk">${msim.curSimIccid}</span> / <span class="contenttext_blk">${msim.curSimItemDesc}</span> (<span class="contenttext_blk">${msim.curSimItemCd}</span>) / <span class="contenttext_blk">${msim.sim.simType}</span> 
												</td>
											</tr>
											<tr width="100%">
												<td colspan="2">
													<table>
														<tr width="100%">
															<td colspan="5"><b>Current Active Contract</b></td>
														</tr>
														<tr>
															<td width="20%">Type</td>
															<td width="50%">Contract Description</td>
															<td width="10%">Duration</td>
															<td width="10%">Start Date</td>
															<td width="10%">End Date</td>
														</tr>
														<c:choose>
															<c:when test='${not empty msim.curActiveContractparentProdType}'>
																<tr>
																	<td><span class="contenttext_blk">${msim.curActiveContractparentProdType}</span></td>
																	<td><span class="contenttext_blk">${msim.curActiveContracttcDesc}</span></td>
																	<td><span class="contenttext_blk">${msim.curActiveContractduration}</span></td>
																	<td><span class="contenttext_blk">${msim.curActiveContracteffStartDate}</span></td>
																	<td><span class="contenttext_blk">${msim.curActiveContracteffEndDate}</span></td>
																</tr>
															</c:when>
															<c:otherwise>
																<tr>
																	<td colspan="5">
																		N/A
																	</td>
																</tr>
															</c:otherwise>
														</c:choose>
													</table>
												</td>
											</tr>
											<tr>
												<td>SIM No.:</td>
												<td><span class="contenttext_blk">${msim.sim.iccid}</span></td>
											</tr>
											<tr>
												<td>SIM Type:</td>
												<td><span class="contenttext_blk">${msim.sim.itemCode}</span></td>
											</tr>
											<tr>
												<td>Admin Charge: </td>
												<td><span class="contenttext_blk">$<fmt:formatNumber value="${msim.too1AdminCharge}" pattern="#,###.####" /></td>
											</tr>
											<tr>
												<td>Charge / Waive:</td>
												<td><span class="contenttext_blk">${msim.too1AdminChargeInd}</span></td>
											</tr>
										</c:if>
										
										<c:if test='${msim.memberOrderType == "MUPS05"}'>
											<tr>
												<td><b>Direct MNP</b></td>
											</tr>
											<tr>
												<td>MNP Number</td>
												<td><span class="contenttext_blk">${msim.mnpNumber}</span></td>
											</tr>
											<tr>
												<td>MNP Name</td>
												<td><span class="contenttext_blk">${msim.mnpCustName}</span></td>
											</tr>
											<tr>
												<td>Prepaid sim</td>
												<c:choose>
													<c:when test='${msim.mnpCustName == "Prepaid Sim"}'>
														<td><span class="contenttext_blk">Y</span></td>
													</c:when>
													<c:otherwise>
														<td><span class="contenttext_blk">N</span></td>
													</c:otherwise>
												</c:choose>
											</tr>
											<tr>
												<td>MNP Doc No/Prepaid Sim No</td>
												<td><span class="contenttext_blk">${msim.mnpDocNo}</span></td>
											</tr>
										
											<tr>
												<td>Cut-over date</td>
												<td><span class="contenttext_blk">${msim.mnpCutOverDate}</span></td>
											</tr>
											<tr>
												<td>Cut-over time</td>
												<c:if test='${msim.mnpCutOverTime == "0"}'>
													<td><span class="contenttext_blk">1:00 - 4:00</span></td>
												</c:if>
												<c:if test='${msim.mnpCutOverTime == "1"}'>
													<td><span class="contenttext_blk">12:00 - 14:00</span></td>
												</c:if>
											</tr>
										</c:if>
										
										<c:if test='${msim.memberOrderType == "MUPS99"}'>
											<tr>
												<td><b>DO NOT CREATE</b></td>
											</tr>
										</c:if>
										
										<c:if test='${msim.mnpInd == "Y"}'>
											<tr>
												<td>Cut-over date</td>
												<td><span class="contenttext_blk">${msim.mnpCutOverDate}</span></td>
											</tr>
											<tr>
												<td>Cut-over time</td>
												<c:if test='${msim.mnpCutOverTime == "0"}'>
													<td><span class="contenttext_blk">1:00 - 4:00</span></td>
												</c:if>
												<c:if test='${msim.mnpCutOverTime == "1"}'>
													<td><span class="contenttext_blk">12:00 - 14:00</span></td>
												</c:if>
											</tr>
										</c:if>
										<c:if test='${not empty msim.selectedVasItemList}'>
											<tr><td>Optional VAS</td>
											<td valign="top" bgcolor="#FFFFFF">
											<table style="width:100%" border="0" cellspacing="1">
											<tr>
												<td class="table_title table_title_2row" style="text-align: left">
													<span style="text-align: left">
														<spring:message code="label.basket.desc"/>
														<br><spring:message code="label.basket.desc.1"/>
													</span>
												</td>
												<td class="table_title table_title_2row">
													<span>
														<spring:message code="label.basket.monthly"/>
														<br><spring:message code="label.basket.monthly.1"/>
													</span>
												</td>
											</tr>
											<c:forEach items="${msim.selectedVasItemList}" var="selectedVas">
												<c:forEach items="${msi.optionalItemList}" var="vas">
													<c:if test="${selectedVas == vas.itemId}">
														<tr>
															<td class="contenttext">${vas.itemHtml}</td>
															<td class="BGgreen2">
																<c:if test='${not empty vas.itemRecurrentAmt && vas.itemRecurrentAmt != 0}'>
																	$<span class="monthPay"><fmt:formatNumber value="${vas.itemRecurrentAmt}" pattern="#,###.####" /></span>/month
																</c:if>
															</td>
														</tr>
													</c:if>
												</c:forEach>
											</c:forEach>
											</table></td></tr>
										</c:if>								
										<!-- ------------------ Actual User Table ----------------------------- -->
	  									<c:if test='${msim.memberOrderType != "MUPS99"}'>
		  									<c:if test='${msim.mnpInd != null}'>
			  									<tr><td colspan = '2'>
		      									<sb-ui:actualuserinfo value="${msim.actualUserDTO}" sameascust="${msim.sameAsCust}" />
		      									</td></tr>
	      									</c:if>
	      								</c:if>
      									<!-- ------------------ Actual User Table ----------------------------- -->
									</c:forEach>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</c:if>
			
			<!--  Quota Plan Info -->
            <c:if test="${ not empty quotaPlanInfoMapSession}">
					<table width="100%" border="0" cellspacing="0" class="contenttext">
						<tr>
							<td class="table_title">Quota Plan Info</td>
						</tr>
						<tr>
							<td valign="top" bgcolor="#FFFFFF">
								<table width="100%" border="0" cellspacing="1">
									<tr class="table_title">
										<td >Auto Top up Service Pack</td>
										<td> Enable Auto Top-up</td>
										<td> Top-up Option </td>
										<td> Max. Top-up(Times)</td>
									</tr>
									<c:forEach items="${quotaPlanInfoMapSession}" var="quotaplan">
										<tr>
											<td>${quotaplan.value.itemDisplayDTO.description}</td>
											<td>${quotaplan.value.autoTopUpInd}</td>
											<td>${quotaplan.value.engDesc}</td>
											<td>${quotaplan.value.maxTopUpTimes}</td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
					</table>
			</c:if>


			<!--  Quota Plan Info End -->
				
			
<!-- Service Detail -->
				<table width="100%" border="0" cellspacing="0" class="contenttext">
					
					<tr>
						<td class="table_title">Service Detail</td>
					</tr>
					<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table width="100%" border="0" cellspacing="1">
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>Service Type</b>
									</td>
									<td><span class="contenttext_blk"> 
									<c:choose>
										<c:when test="${vasMrtSelectionSession.chinaInd == 'Y'}">
											1C2N
											<c:choose>
												<c:when test="${mnpDto.mnp == 'N'}">
													(New MRT)
												</c:when>
												<c:when test="${mnpDto.mnp == 'Y'}">
													 (MNP)
												</c:when>
											</c:choose>
										</c:when>
										
										<c:when test='${mnpDto.mnp == "Y"}'>
											<c:choose>
												<c:when test='${mnpDto.mnp == "Y" and mnpDto.custName !="Prepaid Sim"  and mnpDto.custIdDocNum != customerInfoDto.idDocNum}'>
													MNP (with Transfer of Ownership)
												</c:when>
												<c:when test='${mnpDto.mnp == "Y" }'>
													MNP 
												</c:when>
											</c:choose>
										</c:when>
									
										<c:when test='${mnpDto.mnp == "N"}'>
											<c:if test='${mnpDto.futherMnp}'>
												New Num + MNP
										</c:if>
									<c:if test='${!mnpDto.futherMnp}'>
									New Mobile Number
									</c:if>
									
											
										</c:when>
									</c:choose> 
												
											
											</span>
									</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>Service Mobile Number</b>
									</td>
									<td>
										<span class="contenttext_blk">
											<c:choose>
											<c:when test="${vasMrtSelectionSession.chinaInd == 'Y'}">
												<c:choose>								
													<c:when test="${mnpDto.mnp == 'Y'}">${mnpDto.msisdn}(MNP Number) / ${vasMrtSelectionSession.msisdn} (China Number)</c:when>
													<c:when test="${mnpDto.mnp == 'N'}">${mnpDto.msisdn} (New Mobile Number) / ${vasMrtSelectionSession.msisdn} (China Number)</c:when>
												</c:choose>
											</c:when>
											<c:otherwise>
												${mnpDto.msisdn}
											</c:otherwise>
											</c:choose>
										</span>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Application Date</b>
									</td>

									<td><span class="contenttext_blk"><fmt:formatDate
												pattern="dd/MM/yyyy HH:mm:ss" value="${orderDto.appInDate}" />
									</span>
									</td>

								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Service Request Date</b>
									</td>
									<td><span class="contenttext_blk"> <c:choose>
												<c:when test='${mnpDto.mnp == "Y"}'>
									 ${mnpDto.cutoverDateStr}
									</c:when>
												<c:otherwise>
									 ${mnpDto.serviceReqDateStr}
									</c:otherwise>
											</c:choose> </span>
									</td>
								</tr>
								<c:if test='${mnpDto.mnp == "Y"}'>
									<tr>
										<td>&nbsp;</td>
										<td><b>Cutover Time</b>
										</td>
										<c:if test='${mnpDto.cutoverTime == "0"}'>
											<td><span class="contenttext_blk">1:00 - 4:00</span></td>
										</c:if>
										<c:if test='${mnpDto.cutoverTime == "1"}'>
											<td><span class="contenttext_blk">12:00 - 14:00</span></td>
										</c:if>
									</tr>	
								</c:if>
								

<c:if test='${mnpDto.mnp == "Y"}'>
								<tr>
									<td>&nbsp;</td>
									<td><b>Old Customer Name</b>
									</td>
									<td><span class="contenttext_blk">
											${mnpDto.custName} </span>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Old Customer Name in Chinese</b>
									</td>
									<td class="contenttext_blk">${mnpDto.custNameChi}</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Old Customer Doc No. / prepard SIM No.</b>
									</td>
									<td><span class="contenttext_blk">
											${mnpDto.custIdDocNum} </span>
									</td>
								</tr>
								</c:if>
								
								<c:if test='${mnpDto.futherMnp}'>
								<tr>
									<td>&nbsp;</td>
									<td><b>Further MNP Number</b>
									</td>
									<td class="contenttext_blk">${mnpDto.futherMnpNumber}</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Further MNP Cutover Date</b>
									</td>
									<td><span class="contenttext_blk">
											${mnpDto.futherCutoverDateStr} </span>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Further MNP Cutover Time</b>
									</td>
									<c:if test='${mnpDto.futherCutoverTime == "0"}'>
										<td><span class="contenttext_blk">1:00 - 4:00</span></td>
									</c:if>
									<c:if test='${mnpDto.futherCutoverTime == "1"}'>
										<td><span class="contenttext_blk">12:00 - 14:00</span></td>
									</c:if>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Old Customer Name</b>
									</td>
									<td><span class="contenttext_blk">
											${mnpDto.futhercustName} </span>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Old Customer Name in Chinese</b>
									</td>
									<td class="contenttext_blk">${mnpDto.futhercustNameChi}</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Old Customer Doc No. / prepard SIM No.</b>
									</td>
									<td><span class="contenttext_blk">
											${mnpDto.futhercustIdDocNum} </span>
									</td>
								</tr>
								</c:if>
								
								<c:if test='${!empty mnpDto.dno}'>
								<tr>
									<td>&nbsp;</td>
									<td><b>DNO</b>
									</td>
									<td><span class="contenttext_blk">
											${mnpDto.dno} </span>
									</td>
								</tr>
								</c:if>
								<c:if test='${!empty mnpDto.origActDateStr}'>
								<tr>
									<td>&nbsp;</td>
									<td><b>Original Activation Date</b>
									</td>
									<td><span class="contenttext_blk">
											${mnpDto.origActDateStr} </span>
									</td>
								</tr>
								</c:if>


								<%-- add by herbert 20110707 start--%>
								<tr>
									<td>&nbsp;</td>
									<td><b>Advance Order</b>
									</td>
									<td><span class="contenttext_blk"> <c:choose>
												<c:when test='${orderDto.aoInd == "Y"}'>
									 Yes
									</c:when>
												<c:otherwise>
									 No
									</c:otherwise>
											</c:choose> </span>
									</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Sim Number</b></td>
									<td><span class="contenttext_blk">
											${mobileSimInfo.iccid} </span></td>
								</tr>
								
								<tr>
									<td>&nbsp;</td>
									<td><b><spring:message code="label.billDate" /></b></td>
									<td><span class="contenttext_blk">${billDate}</span></td>
								</tr>
								
								<c:if test='${!empty mobileSimInfo.imei}'>
								<tr>
									<td>&nbsp;</td>
									<td><b>IMEI</b></td>
									<td><span class="contenttext_blk">
											${mobileSimInfo.imei} </span></td>
								</tr>
								</c:if>
								<c:forEach items="${mobileSimInfo.stockAssgnList}" var="saList" varStatus="saRow">
								<c:if test="${mobileSimInfo.stockAssgnList[saRow.index].itemType == '01'}">
								<tr>
									<td>&nbsp;</td>
									<td><b>Handset</b></td>
									<td width="40%"><span class="contenttext_blk">
											${mobileSimInfo.stockAssgnList[saRow.index].itemDesc} </span></td>
									<c:if test="${mobileSimInfo.stockAssgnList[saRow.index].aoInd == 'Y'}">
										<td><span class="contenttext_blk">AO</span></td>
									</c:if>
									<c:if test="${mobileSimInfo.stockAssgnList[saRow.index].aoInd != 'Y'}">
										<td><span class="contenttext_blk">
											${mobileSimInfo.stockAssgnList[saRow.index].itemSerial} </span></td>
									</c:if>									
								</tr>
								</c:if>
								</c:forEach>
								
									<c:if test='${ssMrtSelectionSession.ssSubscribed}'>
								<tr>
									<td>&nbsp;</td>
									<td><b>Secretarial Service Number</b></td>
									<td><span class="contenttext_blk">
											${ssMrtSelectionSession.secSrvNum} </span></td>
								</tr>
								</c:if>
							</table></td>

					</tr>
				</table>
				<!-- End Service Detail -->


			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">Payment Method</td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1">
<%if ("10".equals(salesChannelId)) { %>
			<tr><td width="6%">&nbsp;</td>
				<td width="25%"><b>Upfront Payment Method </b></td>
				</tr>
				<c:forEach items="${paymentUpfrontDto.paymentTransList}" var="paymentUpfrontList" varStatus="ptRow">
					<tr>
						<td width="6%">&nbsp;</td><td></td>
						<td><table width="100%" border="0" cellspacing="1"><hr/>
						<c:if test="${paymentUpfrontList.paymentType == 'M' }">
							<tr>
								<td width="32%"><b>Cash Payment</b></td>
								<td valign="top" width="20%">Payment Amount:</td><td> HKD${paymentUpfrontList.paymentAmount}</td></tr>
							<tr><td></td><td valign="top">Transaction Date:</td><td> ${paymentUpfrontList.transDate}</td></tr>
							<tr><td></td><td valign="top">Invoice Num.:</td><td> ${paymentUpfrontList.invoiceNo}</td></tr>
							<tr><td></td><td valign="top">Remark:</td><td> ${paymentUpfrontList.remark}</td></tr>
						</c:if>
						<c:if test="${paymentUpfrontList.paymentType == 'C' }">
							<tr>
								<td width="32%"><b>Credit Card Payment</b></td>
								<td valign="top" width="20%">Payment Amount:</td><td> HKD${paymentUpfrontList.paymentAmount}</td></tr>
							<tr><td></td><td valign="top">Credit Card Number:</td><td> ${paymentUpfrontList.ccNum}</td></tr>
							<tr><td></td><td valign="top">Expire Date:</td><td> ${paymentUpfrontList.ccExpiryMonth} / ${paymentUpfrontList.ccExpiryYear}</td></tr>
							<tr><td></td><td valign="top">Issue Bank:</td><td> <sb-util:issuebanklkup bankCd="${paymentUpfrontList.ccIssueBank}"/></td></tr>
							<tr><td></td><td valign="top">Transaction Date:</td><td> ${paymentUpfrontList.transDate}</td></tr>
							<tr><td></td><td valign="top" >Approve Code:</td><td> ${paymentUpfrontList.approveCode}</td></tr>
							<tr><td></td><td valign="top">Remark:</td><td> ${paymentUpfrontList.remark}</td></tr>
						</c:if>
						<c:if test="${paymentUpfrontList.paymentType == 'I' }">
							<tr>
								<td width="32%"><b>Credit Card Installment Payment</b></td>
								<td valign="top" width="20%">Payment Amount:</td><td> HKD${paymentUpfrontList.paymentAmount}</td></tr>
							<tr><td></td><td valign="top">Credit Card Number:</td><td> ${paymentUpfrontList.ccNum}</td></tr>
							<tr><td></td><td valign="top">Expire Date:</td><td> ${paymentUpfrontList.ccExpiryMonth} / ${paymentUpfrontList.ccExpiryYear}</td></tr>
							<tr><td></td><td valign="top">Issue Bank:</td><td> <sb-util:issuebanklkup bankCd="${paymentUpfrontList.ccIssueBank}"/></td></tr>
							<tr><td></td><td valign="top">Transaction Date:</td><td> ${paymentUpfrontList.transDate}</td></tr>
							<tr><td></td><td valign="top">Approve Code:</td><td> ${paymentUpfrontList.approveCode}</td></tr>
							<tr><td></td><td valign="top">Remark:</td><td> ${paymentUpfrontList.remark}</td></tr>
						</c:if>
					</table></td></tr>
				</c:forEach>						
<% } %>


						<c:choose>
							<c:when test='${paymentDto.payMethodType == "M"}'>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>Monthly Payment Method </b></td>
									<td class="contenttext_blk">Cash: HK$<span id="totalMonthPay"></span></td>
								</tr>
							</c:when>
							<c:when test='${paymentDto.payMethodType == "A"}'>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>Monthly Payment Method </b></td>
									<td class="contenttext_blk">AutoPay: HK$<span id="totalMonthPay"></span></td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Bank A/C Owner Doc. Type</b></td>
									<!--modify by eliot 20110616  -->
									<td class="contenttext_blk"><c:choose>
										<c:when test='${paymentDto.bankAcctHolderIdType == "BS"}'>
									HKBR
								</c:when>
										<c:when test='${paymentDto.bankAcctHolderIdType == "PASS"}'>
									Passport
								</c:when>
										<c:otherwise>
									HKID
								</c:otherwise>
									</c:choose></td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Bank A/C Owner Doc. No.</b></td>
									<td class="contenttext_blk">${paymentDto.bankAcctHolderIdNum}</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Bank Holder Name</b></td>
									<td class="contenttext_blk">${paymentDto.bankAcctHolderName}</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Bank Code</b></td>
									<td class="contenttext_blk">${paymentDto.bankName}</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Branch Code</b></td>
									<td class="contenttext_blk">${paymentDto.branchName}</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Bank Account No.</b></td>
									<td class="contenttext_blk">${paymentDto.bankAcctNum}</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Autopay Upper Limit</b></td>
									<td class="contenttext_blk">${paymentDto.autopayUpperLimitAmt}</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Application Date</b></td>
									<td class="contenttext_blk">${paymentDto.autopayApplDateStr}</td>
								</tr>
							</c:when>

							<c:when test='${paymentDto.payMethodType == "C"}'>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="25%"><b>Payment Method </b></td>
									<td class="contenttext_blk">Credit Card</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Credit Card Number</b></td>
									<td class="contenttext_blk">${paymentDto.creditCardNum}</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td><b>Expire Date</b></td>
									<td class="contenttext_blk">${paymentDto.creditExpiryMonth}
									/ ${paymentDto.creditExpiryYear}</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><b>Card Holder's Name</b></td>
									<td class="contenttext_blk">${paymentDto.creditCardHolderName}</td>
								</tr>
							</c:when>



						</c:choose>

					</table>
					</td>
				</tr>
			</table>
			
			
      <!-- ------------------ Deposit Table ----------------------------- -->

      <sb-ui:depositinfo value="${depositInfo.depositDTOList }" var="depositAmount"/>
      
      <!-- ------------------ Deposit Table ----------------------------- -->

        <!-- Payment  Summary -->

      <table width="100%" border="0" cellspacing="0" class="contenttext">
          <colgroup style="width: 25%"></colgroup>
          <colgroup style="width: 75%"></colgroup>
            <tr>
              <td class="table_title" ><spring:message code="label.mobccspreview.header.paymentTransHist"/></td>
              <td class="table_title" >Amount</td>
            </tr>
        
        <tr>
              <td><spring:message code="label.mobccspreview.paymentTransHist.upfrontAmt"/></td>
              <td>
                $<fmt:formatNumber value="${basket.upfrontAmt + vasOnetimeHSAmt}" pattern="#,###.####" />
              </td>
            </tr>
            <tr>
              <td><spring:message code="label.mobccspreview.paymentTransHist.prePaymentAmt"/></td>
              <td>
                $<fmt:formatNumber value="${basket.prePaymentAmt + vasOnetimeAmt - basket.adminCharge - rpOneTimeAmtWithoutWaive}" pattern="#,###.####" />
                <!--
                  basket.prePaymentAmt: ${basket.prePaymentAmt}
                  vasOnetimeAmt: ${vasOnetimeAmt}
                -->
              </td>
            </tr>
            <tr>
              <td>RP Pre-payment</td>
              <td>
                $<fmt:formatNumber value="${rpOneTimeAmt}" pattern="#,###.####" />
                <c:if test="${not empty mobileSimInfo.rpWaiveReason}">
                	(Waived: <sb-util:reasonlkup  codeType="WAIVE_OT" codeId="${mobileSimInfo.rpWaiveReason}" appDate="${orderDto.appInDate }" />)
                </c:if>
              </td>
            </tr>
            <tr>
              <td><spring:message code="label.mobccspreview.paymentTransHist.adminCharge"/></td>
              <td>
                $<fmt:formatNumber value="${0+basket.adminCharge}" pattern="#,###.####" />
              </td>
            </tr>           
            <tr>
              <td>Deposit Charge</td>
              <td>
                $<fmt:formatNumber value="${depositAmount}" pattern="#,###.####" />
              </td>
            </tr>           
			<tr>
              <td>SIM Charge</td>
              <td>
                $<fmt:formatNumber value="${simOneTimeAmt}" pattern="#,###.####" />
              </td>
            </tr>
            <tr>
              <td colspan="2"><hr></td>
            </tr>
            <tr>
              <td>Total Payment</td>
              <td>
                $<fmt:formatNumber value="${basket.upfrontAmt + basket.prePaymentAmt + vasOnetimeAmt + simOneTimeAmt + vasOnetimeHSAmt + depositAmount + (rpOneTimeAmt-rpOneTimeAmtWithoutWaive)}" pattern="#,###.####" />
              </td>
            </tr>
          </table>

        <!-- Payment  Summary -->









			
			
			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">Document Attachement</td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table style="width:100%">
					<colgroup style="width:6%"></colgroup>
					<tr>
						<td></td>
						<td>
							<ul style="padding:0;margin:0;list-style-type:none">
							<c:forEach items="${supportDoc.generateSpringboardForms}" var="form">
							<c:if test="${form.required}">
							<li><c:out value="${form.springboardForm.label}"/></li>
							</c:if>
							</c:forEach>
							</ul>
						</td>
					</tr>
					</table>
					</td>
				</tr>
				<c:if test="${(salesUserDto.channelId == '10') || (salesUserDto.channelId == '11')}">
					<tr>
						<td colspan="2"><hr></td>
					</tr>
					<tr>
						<td>Missing Doc Reason:
						<c:choose>
							<c:when test="${empty customerInfoDto.dsMissDoc}">
								N/A
							</c:when>
							<c:otherwise>
								${customerInfoDto.dsMissDoc}
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</c:if>
			</table>
			

			
			
			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">Distribution Mode</td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table style="width:100%">
					<colgroup style="width:6%"></colgroup>
					<colgroup style="width:30%"></colgroup>
					<tr>
						<td></td>
						<th style="text-align:left">Document attachement generated by</th>
						<td>
						<c:choose>
						<c:when test="${supportDoc.disMode == 'E'}">Digital Signature</c:when>
						<c:when test="${supportDoc.disMode == 'P'}">Paper</c:when>
						</c:choose>
						</td>
					</tr>
					<c:if test="${supportDoc.disMode == 'E'}">
					<tr>
						<td></td>
						<th style="text-align:left">Email</th>
						<td class="contenttext_red"> <b><c:out value="${supportDoc.esigEmailAddr}"/></b></td>
					</tr>
					<tr>
						<td></td>
						<th style="text-align:left">Email Language</th>
						<td>
						<c:choose>
						<c:when test="${supportDoc.esigEmailLang == 'CHN'}">Traditional Chinese</c:when>
						<c:when test="${supportDoc.esigEmailLang == 'ENG'}">English</c:when>
						</c:choose>
						</td>
					</tr>
					</c:if>
					<tr>
						<td></td>
						<th style="text-align:left">Required Proof Copy Collect by</th>
						<td>
						<c:choose>
						<c:when test="${supportDoc.collectMethod == 'P'}">Paper</c:when>
						<c:when test="${supportDoc.collectMethod == 'D'}">Digital</c:when>
						</c:choose>
						</td>
					</tr>
					</table>
					</td>
				</tr>
			</table>
			
			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">Required Proof</td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table style="width:100%">
					<colgroup style="width:6%"></colgroup>
					<tr>
						<th></th>
						<th>Proof Type</th>
						<th>Waive Reason</th>
						<th>Collected</th>
					</tr>
					<c:forEach items="${supportDoc.allOrdDocAssgnDTOs}" var="s">
									<tr>
										<td></td>
										<td style="text-align: center"><c:out
												value="${s.docName}" /></td>
										<td style="text-align: center">
											<c:forEach	items="${waiveReasons[s.docType]}" var="r">
												<c:if test="${r.waiveReason == s.waiveReason}">
													<c:out value="${r.waiveDesc}" />
												</c:if>
											</c:forEach>
											<c:choose>
											
												<c:when test="${empty s.waiveReason && s.collectedInd == 'N'}">N/A </c:when>
												<c:when test="${empty s.waiveReason && s.collectedInd == 'Y'}">N/A</c:when>
											</c:choose>
											
											</td>
										<td style="text-align: center"><c:choose>
												<c:when test="${not empty s.waiveReason}">Waived</c:when>
												<c:when test="${s.collectedInd == 'Y'}">Y</c:when>
												<c:when test="${s.collectedInd == 'N'}">N</c:when>
											</c:choose></td>
									</tr>
								</c:forEach>
					</table>
					</td>
				</tr>
			</table>
			
			<!-- Manual AF Information -->
				<table width="100%" border="0" cellspacing="0" class="contenttext">

					<tr>
						<td class="table_title">Manual AF</td>
					</tr>

					<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table style="width: 100%">
								<colgroup style="width: 6%"></colgroup>
								<colgroup style="width: 30%"></colgroup>
								<tr>
									<td></td>
									<th style="text-align: left">Manual AF #</th>
									<td>${(empty supportDoc.manualAfNo)?'n/a':supportDoc.manualAfNo}</td>
								</tr>

							</table>


						</td>
					</tr>
				</table> 

			
			<!-- Sales Information -->
			<table width="100%" border="0" cellspacing="0" class="contenttext">
				
				<tr>
					<td class="table_title">Sales Information</td>
				</tr>

				<tr>
					<td valign="top" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="1">

								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Sales Code</b></td>
									<td class="contenttext_blk">${mobileSimInfo.salesCd}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Sales Name</b></td>
									<td class="contenttext_blk">${mobileSimInfo.salesName}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Shop Code</b></td>
									<td class="contenttext_blk">${mobileSimInfo.shopCd}</td>
								</tr>
								
								<c:if test="${salesUserDto.channelId == '10' || salesUserDto.channelId == '11'}">
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Sales Location</b></td>
									<td class="contenttext_blk">${salesLocation}</td>
								</tr>
								</c:if>
								<c:if test="${mobileSimInfo.refSalesId!=''&&mobileSimInfo.refSalesId!=null}">
									<tr>
										<td width="6%">&nbsp;</td>
										<td width="30%"><b>Ref. Sales Name</b></td>
										<td class="contenttext_blk">${mobileSimInfo.refSalesName}</td>
									</tr>
									<tr>
										<td width="6%">&nbsp;</td>
										<td width="30%"><b>Ref. Sales ID</b></td>
										<td class="contenttext_blk">${mobileSimInfo.refSalesId}</td>
									</tr>
									<tr>
										<td width="6%">&nbsp;</td>
										<td width="30%"><b>Ref. Sales Centre</b></td>
										<td class="contenttext_blk">${mobileSimInfo.refSalesCentre}</td>
									</tr>
									<tr>
										<td width="6%">&nbsp;</td>
										<td width="30%"><b>Ref. Sales Team</b></td>
										<td class="contenttext_blk">${mobileSimInfo.refSalesTeam}</td>
									</tr>
								</c:if>
					</table>
					</td>
				</tr>
			</table>
			<!-- iGuard information -->
				<c:if test='${!empty orderDto.iGuardSerialNo}'>
				<table width="100%" border="0" cellspacing="0" class="contenttext">

					<tr>
						<td class="table_title">Lost/Damaged Beyond Repair/Stolen Handset Reimbursement Plan Information<br>
						Accidental Damage Plus Repair Plan </td>
					</tr>

					<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table width="100%" border="0" cellspacing="1">

								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Serial No.</b></td>
									<td class="contenttext_blk">${iGuardDto.serialNo}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>LDS Service Plan Monthly Fee</b></td>
									<c:if test='${!empty iGuardDto.ldsSrvPlanFee}'>
									    <td class="contenttext_blk">${iGuardDto.ldsSrvPlanFee}</td>
									</c:if>
									<c:if test='${empty iGuardDto.ldsSrvPlanFee}'>
									    <td class="contenttext_blk">N/A</td>
									</c:if>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>AD Service Plan Monthly Fee</b></td>
									<c:if test='${!empty iGuardADDto.adSrvPlanFee}'>
									    <td class="contenttext_blk">${iGuardADDto.adSrvPlanFee}</td>
									</c:if>
									<c:if test='${empty iGuardADDto.adSrvPlanFee}'>
									    <td class="contenttext_blk">N/A</td>
									</c:if>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>PCCW Mobile Number </b></td>
									<td class="contenttext_blk">${iGuardDto.msisdn}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Mobile Handset Brand/Model </b></td>
									<td class="contenttext_blk">${iGuardDto.handsetDeviceDescription}</td>
								</tr>
								
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>IMEI No. </b></td>
									<td class="contenttext_blk">${iGuardDto.imei}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Target Effect Date </b></td>
									<td class="contenttext_blk">${iGuardDto.tgtEffDate}</td>
								</tr>
								
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Contract Period</b></td>
									<td class="contenttext_blk">${iGuardDto.contractPeriod}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Handset Purchase Price </b></td>
									<td class="contenttext_blk">
									<fmt:formatNumber value="${iGuardDto.hsPurchasePrice}"	pattern="$#,###.####" />
									</td>
								</tr>

							</table>
						</td>
					</tr>
				</table>
				</c:if>
				
					<c:if test="${enableUAD == 'Y'}">
				<table width="100%" border="0" cellspacing="0" class="contenttext">

					<tr>
						<td class="table_title">i-GUARD Phone & Tablet Accidental Damage Repair Plan  </td>
					</tr>

					<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table width="100%" border="0" cellspacing="1">

								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>PCCW Mobile Number </b></td>
									<td class="contenttext_blk">${iGuardUadDto.msisdn}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Mobile Handset Brand/Model </b></td>
									<td class="contenttext_blk">${iGuardUadDto.handsetDeviceDescription}</td>
								</tr>
								
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>IMEI No. </b></td>
									<td class="contenttext_blk">${iGuardUadDto.imei}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Target Effect Date </b></td>
									<td class="contenttext_blk">${iGuardUadDto.tgtEffDate}</td>
								</tr>
								
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Contract Period</b></td>
									<td class="contenttext_blk">${iGuardUadDto.contractPeriod}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="30%"><b>Handset Purchase Price </b></td>
									<td class="contenttext_blk">
									<fmt:formatNumber value="${iGuardUadDto.hsPurchasePrice}"	pattern="$#,###.####" />
									</td>
								</tr>

							</table>
						</td>
					</tr>
				</table>
				</c:if>
				<!-- END iGuard information -->
				
				<!-- start Privacy table information -->
				<table width="100%" border="0" cellspacing="0" class="contenttext">

					<tr>
						<td class="table_title"> <spring:message code="label.privacyIndTitle"/></td>
					</tr>

					<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table width="100%" border="0" cellspacing="1">

								<tr>

									<td class="contenttext_blk"><c:if
											test="${customerInfoDto.privacyInd == true}">
											<spring:message code="label.privacyIndY" />
										</c:if> <c:if test="${customerInfoDto.privacyInd == false}">
											<spring:message code="label.privacyIndN" />
										</c:if></td>
								</tr>


							</table></td>
					</tr>
				</table> 
				<!-- end Privacy table information -->

				<!--  Suppress Top Up table information -->
				
				<table width="100%" border="0" cellspacing="0" class="contenttext">
					
						<tr>
							<td class="table_title" ><spring:message code="label.suppressTopUpTitle"/></td>
						</tr>
						
						<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table style="width: 100%">						
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.suppressLocalTopUpInd"/></b></td>
									<td><c:choose>
										<c:when test="${customerInfoDto.suppressLocalTopUpInd == true}">Y</c:when>
										<c:when test="${customerInfoDto.suppressLocalTopUpInd == false}">N</c:when>
										</c:choose>
										</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.suppressRoamTopUpInd"/></b></td>
									<td><c:choose>
										<c:when test="${customerInfoDto.suppressRoamTopUpInd == true}">Y</c:when>
										<c:when test="${customerInfoDto.suppressRoamTopUpInd == false}">N</c:when>
										</c:choose></td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.mob0060OptOutInd"/></b></td>
									<td><c:choose>
										<c:when test="${customerInfoDto.mob0060OptOutInd == true}">Y</c:when>
										<c:when test="${customerInfoDto.mob0060OptOutInd == false}">N</c:when>
										</c:choose></td>
								</tr>
								<c:if test="${(customerInfoDto.csPortalStatus=='C' || empty customerInfoDto.csPortalStatus) && !(empty customerInfoDto.emailAddr) && !(customerInfoDto.csPortalInd=='N')}">
								
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.optOutHKT"/></b></td>
									<td><c:choose>
										<c:when test="${customerInfoDto.hktOptOut == true}">Y</c:when>
										<c:when test="${customerInfoDto.hktOptOut == false}">N</c:when>
										</c:choose></td>
								</tr>
								</c:if>	
								<c:if test="${(customerInfoDto.csPortalStatus=='H'|| empty customerInfoDto.csPortalStatus) && !(customerInfoDto.csPortalInd=='N') }">
								<tr>									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.optOutClub"/></b></td>
									<td><c:choose>
										<c:when test="${customerInfoDto.clubOptOut == true}">Y (Reason: <sb-util:codelookup codeId="${customerInfoDto.clubOptRea }" codeType="OPT_OUT_REASON"  /> ${customerInfoDto.clubOptRmk})</c:when>
										<c:when test="${customerInfoDto.clubOptOut == false}">N</c:when>
										</c:choose></td>
								</tr>
								</c:if>	
								
								
							</table></td>
						</tr>
		
				</table>
			
				<!--  Suppress Top Up table information -->
				
				<!--  Local/Roaming Data Alert Settings table information -->
				
				<table width="100%" border="0" cellspacing="0" class="contenttext">
					
						<tr>
							<td class="table_title" ><spring:message code="label.localRoamingDataAlertSettingTitle"/></td>							
						</tr>
						
						<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table style="width: 100%">						
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.pcrfAlertType"/></b> </td>
									<td><pccw-util:codelookup codeType="PCRF_ALERT_TYPE" codeId="${customerInfoDto.pcrfAlertType}" /></td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.pcrfAlertEmail"/></b></td>
									<td><c:choose>
										<c:when test="${customerInfoDto.sameAsEbillAddr == true}"><spring:message code="label.sameAsEbillAddrInd" /></c:when>
										<c:when test="${customerInfoDto.sameAsEbillAddr == false}">${customerInfoDto.pcrfAlertEmail}</c:when>
										</c:choose></td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.pcrfSmsRecipient"/></b></td>
									<td><pccw-util:codelookup codeType="PCRF_SMS_RECIPIENT" codeId="${customerInfoDto.pcrfSmsRecipient}" /></td>
								</tr>		
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.pcrfSmsNum"/></b></td>
									<td>${customerInfoDto.pcrfSmsNum}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.smsOptOutFirstRoam"/></b></td>
									<td>${customerInfoDto.smsOptOutFirstRoam}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.smsOptOutRoamHu"/></b></td>
									<td>${customerInfoDto.smsOptOutRoamHu}</td>
								</tr>
								
										<c:if test="${customerInfoDto.pcrfMupAlert ne '0' and not empty customerInfoDto.pcrfMupAlert}">
										<tr>
											<td width="6%">&nbsp;</td>
											<td width="50%"><b><spring:message code="label.pcrfMupAlert"/></b></td>
											<td>
											<c:if test="${customerInfoDto.pcrfMupAlert eq '1'}">N</c:if>
											<c:if test="${customerInfoDto.pcrfMupAlert eq '2'}">Y</c:if>
											</td>
										</tr>
										</c:if>
																		
							</table></td>
						</tr>
		
				</table>
					<c:choose>
						<c:when test="${orderdetailCustomer.careStatus=='Y' }">
						<table width="100%" border="0" cellspacing="0" class="contenttext">
					
						<tr>
							<td class="table_title" ><spring:message code="label.iGuardCareCashTitle"/></td>							
						</tr>
						
						<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table style="width: 100%">						
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b>Customer subscribed i-GuardCARECash Service Plan</b> </td>
									<td>
									<c:choose>
										<c:when test="${orderdetailCustomer.careOptInd=='I'}">
											Confirm Apply
										</c:when>
										<c:when test="${orderdetailCustomer.careOptInd=='O'}">
											Declined
										</c:when>
										<c:when test="${orderdetailCustomer.careOptInd=='P'}">
											To Be Decided
										</c:when>
									</c:choose>
								</td>
								</tr>
								<c:choose>
								<c:when test="${orderdetailCustomer.careOptInd=='I'}">
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b>Customer don't want to receive any promotion of product service or special offers from i-Guard</b></td>
									<c:choose>
									<c:when test="${customerInfoDto.careDmSupInd=='I'}">
									<td>Y</td>
									</c:when>
									<c:when test="${customerInfoDto.careDmSupInd=='O'}">
									<td>N</td>
									</c:when>
									</c:choose>
								</tr>
								</c:when>
								</c:choose>									
							</table>
							</td>
							</tr>
							</table>
							</c:when>
							</c:choose>
							
				<!--  Local/Roaming Data Alert Settings table information -->
				
				<c:if test="${not empty clubPointDetailSession}">
					<table width="100%" border="0" cellspacing="0" class="contenttext">
						<tr>
							<td class="table_title"><spring:message code="label.clubPointInfoTitle"/></td>
						</tr>
						<tr>
							<td valign="top" bgcolor="#FFFFFF">
								<table style="width: 100%">						
									<tr>
										<td width="6%">&nbsp;</td>
										<td>
											<pccw-ui:clubpointinfo clubPointValue="${clubPointDetailSession}" theClubLogin="${theClubLogin}" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>	
				</c:if>
					
			<!--content--></td>
		</tr>
	</table>
	
 <!-- end table -->

					
						<!-- NEW NEW NEW NEW NEW NEW  -->
						<!-- empty table -->
						<table width="100%" border="0" bgcolor="#FFFFFF">
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>
						<!-- end empty table -->

						<!--  Control Table  -->
						<table width="100%" border="1" bgcolor="#FFFFFF">
							<!-- print form -->
							<c:if test='${orderDto.orderStatus != "INITIAL" }'>
							<!-- Mass BR commented -->
							<%-- <c:if test='${orderDto.orderStatus != "INITIAL" 
								or (customerInfoDto.idDocType == "BS" and customerInfoDto.companyDoc == "L")}'> --%>
							<tr>
								<td>
								
									<!-- print form table -->
									<table width="100%" border="0" cellspacing="0"
										class="contenttext">

										<tr>
											<td bgcolor="#7CBDC3" class="table_subtitle_blue">Preview / Print
												Form</td>
										</tr>

										<tr>
											<td valign="top" bgcolor="#FFFFFF">
												<table width="100%" border="0" cellspacing="1">

													<tr>
														<td width="6%">&nbsp;</td>
														<td class="contenttext_blk">
															<!-- print form  --> <c:choose>
																<c:when
																	test='${orderDto.disMode == "E" and orderDto.orderStatus != "INITIAL" }'>
	  																<c:choose>
	  																	<c:when test='${orderDto.dmsInd=="Y" }'>
	  																		The AF has been transferred to DMS, please contact Retail admin to retrieve AF.<br/>
	  																	</c:when>
	  																	<c:otherwise>
																			<a name="areport"
																				href="reportdownload.html?sbuid=${sbuid}"
																				target="_blank"> Application Form </a>
																			<br/>
																		
																	<c:forEach items="${iGuardList}" var="iGuardType">
								                                        <c:if test="${!empty orderDto.iGuardSerialNo&&(iGuardType=='LDS')}">
																			<div>
																				<a name="areport" href="reportdownload.html?pdfLang=en&iGuardType=LDS&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()"> i-Guard LDS
																					Cust info Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																		<c:if test="${iGuardType=='AD'}">
																			<div>
																				<a name="areport" href="reportdownload.html?pdfLang=en&iGuardType=AD&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()"> i-Guard AD
																					Cust info Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																		
																		<c:if test="${iGuardType=='UAD'}">
																			<div>
																				<a name="areport" href="reportdownload.html?pdfLang=en&iGuardType=UAD&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()"> i-GUARD Phone & Tablet Accidental Damage Repair Plan
																					Cust info Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																	</c:forEach>
																		<c:if test="${isTravelInsurance}">
																			<div>
												  								<a name="areport" href="reportdownload.html?genericForm=TravelInsuranceForm&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()">Travel Insurance Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																		<c:if test="${isHelperCareInsurance}">
																			<div>
																			  <a name="areport" href="reportdownload.html?genericForm=HelperCareInsuranceForm&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()"> HKT Care 2-year Helper Insurance Coupon Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																		
																		<!-- PROJECT EAGLE START -->
																		<c:if test="${isProjectEagle}">
																		<div>
																		  <a name="areport" href="reportdownload.html?formName=ProjectEagle&sbuid=${sbuid}"
																				target="_blank" onClick="enableConfirm()">Restart Service Form (Eng/Chi)</a>
																		</div>
																		</c:if>
																		<!-- PROJECT EAGLE END -->
																		
																	<c:if test="${orderDto.mobileSafetyPhone}">
																		<a name="areport"
																			href="reportdownload.html?mobileSafetyPhone=Y&sbuid=${sbuid}"
																			target="_blank"> Mobile Safety Phone Service Form</a>
																		<br/>
																	</c:if>
																	
																	<c:if test='${!empty orderDto.nfcInd and orderDto.nfcInd ne "0" and not customerInfoDto.studentPlanSubInd}'>
																		<a name="areport"
																			href="reportdownload.html?nfcInd=${orderDto.nfcInd}&sbuid=${sbuid}"
																			target="_blank"> NFC SIM Service Consent Form</a>
																		<br/>
																	</c:if>
																	<c:if test ="${orderdetailCustomer.careOptInd=='I'}">
																	<a name="areport" href="reportdownload.html?pdfLang=en&iGuardType=CARECASH&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()"> i-Guard CARECASH
																					Cust info Form (Eng/Chi)</a>
																	</c:if>
																	
																	</c:otherwise>
																	</c:choose>
																</c:when>

																<c:when
																	test='${orderDto.disMode == "P" and orderDto.orderStatus != "INITIAL" }'>
	   View Form:<br/>
																	<a name="areport"
																		href="report.html?printFrom=orderdetail&pdfLang=en&sbuid=${sbuid}"
																		target="_blank"> Application Form(Eng) </a>
																	<br/>

																	<a name="areport"
																		href="report.html?printFrom=orderdetail&pdfLang=zh&sbuid=${sbuid}"
																		target="_blank"> Application Form(Chi) </a>
																	<br/>
																	
																	<c:forEach items="${iGuardList}" var="iGuardType">
								                                        <c:if test="${!empty orderDto.iGuardSerialNo&&(iGuardType=='LDS')}">
																			<div>
																				<a name="areport" href="report.html?printFrom=orderdetail&pdfLang=en&iGuardType=LDS&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()"> i-Guard LDS
																					Cust info Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																		<c:if test="${iGuardType=='AD'}">
																			<div>
																				<a name="areport" href="report.html?printFrom=orderdetail&pdfLang=en&iGuardType=AD&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()"> i-Guard AD
																					Cust info Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																		
																		<c:if test="${iGuardType=='UAD'}">
																			<div>
																				<a name="areport" href="report.html?printFrom=orderdetail&pdfLang=en&iGuardType=UAD&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()"> i-GUARD Phone & Tablet Accidental Damage Repair Plan
																					Cust info Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																	</c:forEach>
																	<c:if test="${isTravelInsurance}">
																			<div>
												  								<a name="areport" href="report.html?printFrom=orderdetail&genericForm=TravelInsuranceForm&pdfLang=en&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()">Travel Insurance Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																		<c:if test="${isHelperCareInsurance}">
																			<div>
																			  <a name="areport" href="report.html?printFrom=orderdetail&genericForm=HelperCareInsuranceForm&sbuid=${sbuid}"
																					target="_blank" onClick="enableConfirm()"> HKT Care 2-year Helper Insurance Coupon Form (Eng/Chi)</a>
																			</div>
																		</c:if>
																		<!-- PROJECT EAGLE START -->
																		<c:if test="${isProjectEagle}">
																		<div>
																		  <a name="areport" href="report.html?printFrom=orderdetail&formName=ProjectEagle&sbuid=${sbuid}"
																				target="_blank" onClick="enableConfirm()">Restart Service Form (Eng/Chi)</a>
																		</div>
																		</c:if>
																		<!-- PROJECT EAGLE END -->
																	<c:if test="${orderDto.mobileSafetyPhone}">
																		<a name="areport"
																			href="report.html?printFrom=orderdetail&pdfLang=en&mobileSafetyPhone=Y&sbuid=${sbuid}"
																			target="_blank"> Mobile Safety Phone Service Form</a>
																		<br/>
																	</c:if>
																	
																	<c:if test='${!empty orderDto.nfcInd and orderDto.nfcInd ne "0" and not customerInfoDto.studentPlanSubInd}'>
																		<a name="areport"
																			href="report.html?printFrom=orderdetail&pdfLang=en&nfcInd=${orderDto.nfcInd}&sbuid=${sbuid}"
																			target="_blank"> NFC SIM Service Consent Form</a>
																		<br/>
																	</c:if>
																	<c:if test="${customerInfoDto.careStatus=='Y'&&customerInfoDto.careOptInd=='I'}">
																		<div>
																			<a name="areport" href="report.html?printFrom=orderdetail&pdfLang=en&iGuardType=CARECASH&sbuid=${sbuid}"
																				target="_blank" onClick="enableConfirm()"> i-Guard CareCash Form (Eng)</a>
																		</div>
																		<div>
																			<a name="areport" href="report.html?printFrom=orderdetail&pdfLang=zh&iGuardType=CARECASH&sbuid=${sbuid}"
																				target="_blank" onClick="enableConfirm()"> i-Guard CareCash Form (Chi)</a>
																		</div>
																	</c:if>
														
																</c:when>
																<c:otherwise>


																</c:otherwise>
															</c:choose> <!-- print form -->
															<!-- Mass BR commented -->
															<%-- <c:if test='${customerInfoDto.idDocType == "BS" and customerInfoDto.companyDoc == "L"}'>
																<a name="areport"
																		href="reportdownload.html?authorizedLetter=Y&sbuid=${sbuid}"
																		target="_blank"> Authorized Letter </a>
															</c:if> --%>
														</td>
													</tr>


												</table>
											</td>
										</tr>
									</table> <!-- end print form table -->
									
								</td>
							</tr>
							</c:if>
							<% if (request.getParameter("action").equals("REVIEW")) {%>
								<c:if test="${(salesUserDto.channelId == 10 and salesUserDto.category == 'SUPERVISOR')
													or (salesUserDto.channelId == 11 and salesUserDto.category == 'ORDER SUPPORT')}">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0"
										class="contenttext" align="left">
										<tr>
											<td colspan="2">&nbsp;</td>
										</tr>
										<c:if test="${salesUserDto.category == 'ORDER SUPPORT'}">
										<% if (request.getParameter("appInd").equals("N")) { %>
										<tr>
										<td>Payment Check:
										<input id="paymentCheckBox" type="checkbox" value="Y" />
										</br>
										Original Support Doc Received:
										<input id="supportDocCheckBox" type="checkbox" value="Y" />
										<a href="#" class="nextbutton3"
												onclick="javascript:saveCheck();">Save</a></td>
										</tr><tr><td>&nbsp;</td></tr>
										<% } %>
										</c:if>
										<tr align="left">
											<td><% if (request.getParameter("appInd").equals("N")) { %>
													<% if ("Y".equals(dsApprovable)) { %>
														<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
														onclick="javascript:orderReviewFormSubmit('APPROVE');">Approve</a></div>
													<% } %>	
													<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
													onclick="javascript:orderReviewFormSubmit('REJECT');">Reject</a></div>
												<% } %>
											</td>
											<!-- <td>
												<div style="display:inline-block;height:30px"><a href="mobdsordersearch.html" class="nextbutton3">Order Search</a></div>
											</td> -->
										</tr>
										<tr>
											<td colspan="2">&nbsp;</td>
										</tr>
									</table>
								</td>
								</tr>
								</c:if>
								<% } %>
								<tr>
								<td>
									<table width="100%" border="0" cellspacing="0"
										class="contenttext" align="left">

										<tr>
											<td colspan="2">&nbsp;</td>
										</tr>
										<tr align="left">
											<td>
												<c:if test='${orderDto.disMode == "E" and orderDto.orderStatus != "INITIAL" and orderDto.dmsInd!="Y" }'>
													<div style="display:inline-block;height:30px">
														<c:choose>
														<c:when test="${salesUserDto.channelId == '10' || salesUserDto.channelId == '11'}">
															<span class="btnResend">
																<a
																	href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT008&sbuid=${sbuid}"
																	class="nextbutton3" target="_blank"> Resend/Email History 
																</a>
															</span>
														</c:when>
														<c:otherwise>
															<a
																href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT001&sbuid=${sbuid}"
																class="nextbutton3" target="_blank"> Resend/Email History 
															</a>
														</c:otherwise>
														</c:choose>
													</div>
													<span class="btnResend">
														<c:if test='${!empty orderDto.nfcInd and orderDto.nfcInd ne "0"}'>
															<div style="display:inline-block;height:30px"><a
															href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT006&sbuid=${sbuid}"
															class="nextbutton3" target="_blank"> Resend/Email History (NFC Form)
														</a></div>
														</c:if>
														<c:if test='${orderdetailCustomer.careStatus=="Y" &&orderdetailCustomer.careOptInd=="I"}'>
															<div style="display:inline-block;height:30px"><a
															href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT017&sbuid=${sbuid}"
															class="nextbutton3" target="_blank"> Resend/Email History (IGuardCareCash)
														</a></div>
														
														<div style="display:inline-block;height:30px"><a
															href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT018&sbuid=${sbuid}"
															class="nextbutton3" target="_blank"> Resend/Email History (IGuardCareCash Company)
														</a></div>
														<br></br>
														</c:if>
														
														<c:if test="${enableUAD == 'Y'}">
															<div style="display:inline-block;height:30px"><a
															href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT020&sbuid=${sbuid}"
															class="nextbutton3" target="_blank"> Resend/Email History (i-GUARD Phone & Tablet Accidental Damage Repair Plan Form)
														</a></div>
														
														<div style="display:inline-block;height:30px"><a
															href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT021&sbuid=${sbuid}"
															class="nextbutton3" target="_blank"> Resend/Email History (i-GUARD Company)
														</a></div>
														<br></br>
														</c:if>
														
														<!-- HKT Care -->
														<c:if test="${enableHKTCareEmail}">
															<c:if test="${isTravelInsurance}">
																<div style="display:inline-block;height:30px"><a
																	href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT023_${travelInsuranceDays}&sbuid=${sbuid}"
																	class="nextbutton3" target="_blank"> Resend/Email History (Travel Insurance)
																</a></div>
																<br></br>
															</c:if>
															
															<c:if test="${isHelperCareInsurance}">
																<div style="display:inline-block;height:30px"><a
																	href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT024&sbuid=${sbuid}"
																	class="nextbutton3" target="_blank"> Resend/Email History (HKT Care 2-year Helper Insurance Coupon)
																</a></div>
															</c:if>
														</c:if>
														<!-- HKT Care -->
														<!-- Project Eagle -->
														<c:if test="${isProjectEagle}">
															<div style="display:inline-block;height:30px"><a
																href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT025&sbuid=${sbuid}"
																class="nextbutton3" target="_blank"> Resend/Email History (Restart Service)
															</a></div>
														</c:if>
														<!-- Project Eagle -->
													</span>
												</c:if>  
												
												<c:if test="${salesUserDto.channelId == '3' }">
													<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
														onclick="javascript:window.open('orderremark.html?orderId=${orderId}&sbuid=${sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')">
														Delivery Information
													</a></div>
												</c:if>
												<c:if test="${salesUserDto.channelId == '10' || salesUserDto.channelId == '11'}">
													<span class="btnRemark">
													<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
														onclick="javascript:window.open('orderremark.html?orderId=${orderId}&sbuid=${sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')">
														Remarks
													</a></div>
													</span>
													<span class="btnUpfront">
														<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
														onclick="submitToWindow($('#upfrontpayment')[0]);" value="Upfront payment" >
														Upfront Payment</a></div>
													</span>
												</c:if>
												<span class="btnCaptureProof">
												<c:if test="${supportDoc.collectMethod == 'D'}">
													<c:choose>
													<c:when test = "${(deviceType eq 'ipad') }">
														<div style="display:inline-block;height:30px"><a
															href="<%=scheme%>://${orderId}/${salesUserDto.username}/${currentSessionId}/<%=ipadVersion%>"
															class="nextbutton3">Capture Proof by iPad
														</a></div>
														
														<!-- <div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
															onclick="javascript:showQRCodeDialog();return false;">
															Capture Proof (QR Code)
														</a></div> -->
													</c:when>
													<c:otherwise>
														<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
															onclick="javascript:showQRCodeDialog();return false;">
															Capture Proof (Capture Proof by iPad)
														</a></div>
														
														<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
															onclick="javascript:window.open('doccapture.html?orderId=${orderId}&sbuid=${sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')">
															Capture Proof (upload via PC only)
														</a></div>
													</c:otherwise>
													</c:choose>
										
												</c:if>
												</span>
												<c:if test='${toggleAmendAF}'>
													<div style="display:inline-block;height:30px"><a
														href="javascript:amendAF('${orderDto.orderId}');"
														class="nextbutton3" > Add Amend AF
													</a></div>
												</c:if> 
												<c:if test="${salesUserDto.channelId == 10 || salesUserDto.channelId == 11}">
													<span class="btnFallout"><div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
															onclick="javascript:window.open('mobdsfalloutrecord.html?orderId=${orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')">
															Fallout Record
														</a></div>
													</span>
													<span class="btnQARecord">
														<c:choose>
															<c:when test='${salesUserDto.category == "QA TEAM"}'>
																<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
																	onclick="window.location='mobdsqarecord.html?orderId=${orderId}&seq=0&createInd=Y' ">QA Record</a></div>
															</c:when>
															<c:otherwise>
																<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
																	onclick="window.location='mobdsqarecord.html?orderId=${orderId}&seq=0' ">QA Record</a></div>
															</c:otherwise>
														</c:choose>
													</span>
													<span class="btnSupportDoc">																
														<div style="display:inline-block;height:30px"><a href="#" class="nextbutton3"
															onclick="javascript:window.open('requiredproofadmin.html?orderId=${orderId}&actionType=DsREVIEW', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')">
															Support Doc</a></div>
													</span>
													<span class="btnSBPos">
														<div style="display:inline-block;height:30px">
															<sb-util:sso url="${mobCosUrl}posorderpayment.html?orderId=${orderId}" var="pospaymentsettlelUrl"/>
															<a href="${pospaymentsettlelUrl}" class="nextbutton3">Payment Tender Settlement</a>
														</div>
													</span>
												</c:if>
												<c:if test="${(not empty orderDto.ocid and salesUserDto.channelId == 1 and orderDto.shopCode == salesUserDto.shopCd)}">
													<div style="display:inline-block;height:30px">
													<sb-util:sso url="${mobCosUrl}posorderpayment.html?orderId=${orderId}" var="pospaymentsettlelUrl"/>
													<a href="${pospaymentsettlelUrl}" class="nextbutton3">Payment Tender Settlement</a>
													</div>
												</c:if>
											<div style="display:inline-block;height:30px">
												<c:choose>
												<c:when test="${(salesUserDto.channelId == '10') || (salesUserDto.channelId == '11')}">
													<a href="mobdsordersearch.html" class="nextbutton3">Order Search</a>
												</c:when>
												<c:otherwise>
													<a href="welcome.html" class="nextbutton3">HOME</a>
													<c:if test='${empty orderDto.ocid and salesUserDto.channelId == 1 and orderDto.orderStatus != "INITIAL" and orderDto.orderStatus != "VOID"}'>
														<a href="ordersummary.html?orderIdStr=${orderId}&lob=MOB&action=search" class="nextbutton3">Proceed to POS Automation</a>
													</c:if>
												</c:otherwise>
												</c:choose>
											</div>
											<a href="#" class="nextbutton3" onClick="javascript:window.open('<%=mobCosUrl%>	mobapprovallog.html?orderId=${orderId}&_al=new', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');" >Approval Log</a>
											</td>

										</tr>
										<tr>
											<td colspan="2">&nbsp;</td>
										</tr>


									</table> <!-- end button table -->
								</td>
							</tr>
						</table>
						<!-- NEW NEW NEW NEW NEW NEW -->

						<!--content-->
						<!-- end table -->
					


					<input type="hidden" name="currentView" value="confirmation"/>
</form>
<form name="orderReviewForm" action="mobdsorderreview.html" method="post">
<input type="hidden" name="reason" value="" />
<input type="hidden" name="actionType" value="" />
<input type="hidden" name="orderId" value="${orderId}" />													
</form>	

<div id="qrCodeDialog" class="dialog" title="QR Code" style="display:none;font-size: smaller">
		 <div id="qrCode" align="center"  style="margin: 10px">           	
		 </div>
</div>
<div id="upfrontPaymentDialog" class="dialog" style="display:none;"> 
	<iframe id="upfrontPaymentIframe" height="100%" width="100%" frameBorder="0"></iframe> 
</div>

<form id="upfrontpayment" action="${mobCosUrl}upfrontpayment.html" target="upfrontpayment">
	<input type="hidden" name="orderId" value="${orderId}" />
	<input type="hidden" name="staffId" value="${bomsalesuser.username}" />
	<input type="hidden" name="_al" value="new" />
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>