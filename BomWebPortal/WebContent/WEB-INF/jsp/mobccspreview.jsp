<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<%@ taglib prefix="pccw-ui" tagdir="/WEB-INF/tags/ui"%>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%@ page import="com.bomwebportal.util.ConfigProperties"%>


<%
String mobCosUrl = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");
%>
<sb-util:codelookup var="vipKeyMap" codeType="PRJ_7_VIP"  asEntryList="true" />
<script>
	
	function checkStockQty(orderId) {
		$.ajax({
			url: "mobccscheckstockqtyajax.html"
			, data: {
				orderId: orderId
			}
			, cache: false
			, dataType: "json"
			, success: function(data) {
				var displayText = "Item Code          Location         Qty\n";
				$.each(data, function(index, item) {
					displayText += item.stock_qty + "\n";
				});
				alert(displayText);
			}
			, error: function() {
				alert("Cannot retrieve data. Please re-try later.");
			}
		});
	}
	
	function orderFormSubmit(orderType,event){
		 confirmSave(orderType,event);
	}
	
	function paymentTrxn() {
		var sURL = "mobccspaymenttranshistory.html?orderId="+"${orderId}";
		var vArguments = new Object();
		var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
		window.open(sURL, '_blank',sFeatures );
		//window.showModalDialog(sURL, vArguments, sFeatures);
	}
	
	function authorized() {
		document.mobccspreviewForm.orderType.value = "PEND";
		showLoading();
		document.mobccspreviewForm.submit();		
	}
	
	
	function confirmSave(orderType,event) {
		(event.preventDefault) ? event.preventDefault() : event.returnValue = false; 
		$("<p>Confirm to save order?</p>").dialog({
			resizable: false,
			height:"200",
	        width:"400",
			title:'Confirm to save order??',
		    modal:true,
		    buttons:{
		    "Yes":function(){
		    	$(this).dialog("close");
		    	document.mobccspreviewForm.orderType.value = orderType;
				showLoading();
				document.mobccspreviewForm.submit();			
				
		    },
		    "No":function(){
		    	$(this).dialog("close");
		    }
		           }
		    });
		/* var answer = confirm("Confirm to save order?");
		if (answer){
			document.mobccspreviewForm.orderType.value = orderType;
			showLoading();
			document.mobccspreviewForm.submit();
		}		 */
	}
	
  function showPopup(url) {
	    var sURL = url;
	    /*
	    var vArguments = new Object();
	    var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
	    window.showModalDialog(sURL, vArguments, sFeatures);
	    */
	    window.open(sURL, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');

	  }
  function reloadcallback() {
		window.location.reload();
	}
</script>
<style type="text/css">
.contenttextgary{
	font-family:    "Helvetica", "Verdana", "Arial", "sans-serif";
	padding-left: 5px;
	font-size: 14px; 
	color: #616161; 
		}
.contenttextgreen{
	font-family:    "Helvetica", "Verdana", "Arial", "sans-serif";
	padding-left: 5px;
	font-size: 14px;
	color: #64a903;
	font-weight: bold;
}
.orderRemarkRemark { font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; margin: 0 }
.table_title_2row { text-align: center }
.table_title_2row span { display: inline-block; white-space: nowrap; text-align: center }
</style>

<%
String orderId = (String) request.getSession().getAttribute("orderIdSession");
String appDate = (String) request.getSession().getAttribute("appDate"); 
%>

<c:if test='${empty action}'>
	<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
		<jsp:param name="currentPageName" value="mobccspreview" />
	</jsp:include>
</c:if>




<form:form method="POST" name="mobccspreviewForm"
	commandName="mobccspreview">
	<form:errors path="topErrorPath" cssClass="error" />
	<spring:bind path="ignoreQuotaCheck">
		<c:choose>
		<c:when test="${status.error}">
			<form:checkbox path="ignoreQuotaCheck" />
			<span class="error"><form:errors path="ignoreQuotaCheck" cssClass="error" />Tick the checkbox if you want to ignore it.</span>
		</c:when>
		<c:otherwise>
			<form:hidden path="ignoreQuotaCheck"/>
		</c:otherwise>
		</c:choose>
	</spring:bind>
			
	<spring:bind path="ignoreHSRMCheck">
		<c:choose>
		<c:when test="${status.error}">
			<form:checkbox path="ignoreHSRMCheck" />
			<span class="error"><form:errors path="ignoreHSRMCheck" cssClass="error" /> If you want to ignore it, please tick the checkbox and continue to submit/sign-off your order.</span>
		</c:when>
		<c:otherwise>
			<form:hidden path="ignoreHSRMCheck"/>
		</c:otherwise>
		</c:choose>
	</spring:bind>

	<table width="100%" border="1" style="background-color: white">
	<c:if test='${not empty action }'>
		<tr>
			<td class="contenttextgreen" height="30">
			Order Id : <% out.print(orderId);%> 
		/ Application Date :  <% out.print(appDate);%> / Order Status : 
		<c:if test='${orderDTO.orderStatus == "99"}'>
								<my:code id="${orderDTO.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
							</c:if>
							<c:if test='${orderDTO.orderStatus == "01"}'>
								<my:code id="${orderDTO.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
								<c:if test="${fn:startsWith(orderDTO.orderId, 'CSBOM')}">
									<c:if test='${orderDTO.checkPoint < 399}'>
										<span style="color:red">(Incomplete)</span>
									</c:if>
								</c:if>
							</c:if>
							<c:if test='${orderDTO.orderStatus == "02"}'>
								Completed
							</c:if>
							<c:if test='${orderDTO.orderStatus == "03"}'>
								Cancelling
							</c:if>
							<c:if test='${orderDTO.orderStatus == "04"}'>
								Cancelled
							</c:if>
			</td>
		</tr>
		</c:if>
			
		<tr>
			<td>
				<!--content-->

				<table width="100%" border="0" cellspacing="0">
					<c:if test='${empty action}'> 
					<tr>
						<td class="contenttextgreen" height="40">Please review your order.</td>
						<td class="contenttextgary"></td>
					</tr>
					</c:if>
					<c:if test='${action == "LOCK"}'> 
					<tr>
						<td class="contenttext_red">Order is locked by ${lockOrderDTO.lastUpdateBy}. Order cannot be recalled.</td>
					</tr>
					<tr>
						<td class="contenttextgreen">Please review your order.</td>
					</tr>
					</c:if>
					<c:if test='${action != "LOCK" && not empty action && action != "ENQUIRY"}'>
						<tr>
							<td class="contenttext_red">${action}</td>
						</tr>
					</c:if> 
				</table>
				
				<div style="padding-left:6px;_padding-left:0;*padding-left:0;padding-bottom:1px">
				<div class="table_title toggleOrderRemark" style="font-size:12px; cursor: pointer" onclick="javascript:$('.orderRemarkResultList').toggle(); $('.toggleOrderRemarkResultListStatus').text($('.orderRemarkResultList').is(':visible') ? '[-]' : '[+]');">
					<span class="toggleOrderRemarkResultListStatus" style="float:right">[+]</span>
					<spring:message code="label.mobccspreview.header.orderRemark"/>
				</div>
				<table width="100%" border="1" cellspacing="0" class="orderRemarkResultList contenttext" bgcolor="#FFFFFF" style="display:none">
				<thead>
					<tr class="contenttextgreen">
						<td class="table_title">Remark</td>
						<td class="table_title">Create By</td>
						<td class="table_title">Create Date</td>
					</tr>
				</thead>
				<tbody>
					<c:choose>
					<c:when test="${empty orderRemarkResultList}">
					<tr>
						<td colspan="3">No record</td>
					</tr>
					</c:when>
					<c:otherwise>
					<c:forEach items="${orderRemarkResultList}" var="r">
					<tr>
						<td><pre class="orderRemarkRemark"><c:out value="${r.remark}"/></pre></td>
						<td><c:out value="${r.createBy}"/></td>
						<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${r.createDate}"/></td>
					</tr>
					</c:forEach>
					</c:otherwise>
					</c:choose>
				</tbody>
				</table>
				</div>

				<!-- Customer Information -->
				<table width="100%" border="0" cellspacing="0" class="contenttext">
				<colgroup style="width: 30%"></colgroup>
				<colgroup style="width: 70%"></colgroup>
					<tr>
						<tr>
						<c:if test="${customer.byPassValidation == false}">
							<td class="table_title" colspan="2">
								<spring:message code="label.customerprofile.header.custInfo"/>
							</td>
						</c:if>
						<c:if test="${customer.byPassValidation == true}">
							<td class="table_title" colspan="2">Customer Information (By-Passed Validator)</td>
						</c:if>
						
					<c:if test='${not empty customer.brand }'> 
						<tr>
						<td>Brand Type</td>
						<td>
							<c:choose>
							<c:when test='${customer.brand eq "1"}'>
								<label for="logo"><img style="height:30px;vertical-align:middle" src="images/csl_logo.jpg"/></label>
							</c:when>
							<c:when test='${customer.brand eq "0"}'>
								<label for="logo"><img style="height:30px;vertical-align:middle" src="images/1010_logo.jpg"/></label>
							</c:when>
							<c:otherwise>
								${customer.brand}
							</c:otherwise>
							</c:choose>
						</td>
						</tr>	
					</c:if>	
					
					<c:if test='${not empty customer.simType }'> 
						<tr>
						<td>SIM Type</td>
						<td>
							<pccw-util:codelookup codeType="MIP_SIM_TYPE" codeId="${customer.simType}" />	
						</td>
						</tr>	
					</c:if>	
						
					<tr>
					<td>Document Type</td>
					<td>
						<c:choose>
						<c:when test="${customer.idDocType == 'HKID'}">HKID</c:when>
						<c:when test="${customer.idDocType == 'PASS'}">Passport</c:when>
						<c:when test="${customer.idDocType == 'BS'}">HKBR</c:when></c:choose>
						</td>
					</tr>	
					<tr>
						<td>Document Number</td>
						<td>${customer.idDocNum}</td>
					</tr>
					
					<c:choose>
						<c:when test="${customer.idDocType == 'HKID' || customer.idDocType == 'PASS'}">
					<tr>
					  <td>Title</td>
					  <td>${customer.title}</td></tr>
					<tr> 
					 <td>Name in English</td>
					  <td>${customer.custLastName} ${customer.custFirstName}</td>
					</tr> 
					<tr>
					  <td>Date of birth</td>
					  <td>${customer.dobStr}</td></tr>
					  
					<tr>
					  <td>Student Plan Sub</td>
					  <td>
					  <c:choose>
						<c:when test='${customer.studentPlanSubInd}'>
							Yes
						</c:when>
						<c:otherwise>
							No
						</c:otherwise>
					  </c:choose>
					  </td></tr> 
					  
					 
					
					</c:when>
					<c:otherwise>
					
					<tr>
					  <td>Company Name</td>
					  <td>${customer.companyName}</td></tr>
					 <tr>
					  <td>Name of Authorized Representative</td>
					  <td>${customer.custLastName} ${customer.custFirstName}</td></tr> 
					 <tr>
					  <td>Document No. of Authorized Representative</td>
					  <td>${customer.repIdDocNum}</td></tr> 
					  </c:otherwise>
					  </c:choose>
					 
					<tr>
						<td><spring:message code="label.contactPhone"/></td>
						<td>${customer.contactPhone}</td>
					</tr>
					<tr>
						<td><spring:message code="label.otherContactPhone"/></td>
						<td>${customer.otherContactPhone}</td>
					</tr>
					<tr>
						<td><spring:message code="label.emailAddr"/></td>
						<td>${customer.emailAddr}</td>
					</tr>
						<c:if test="${customer.csPortalInd == 'Y' || customer.csPortalInd == 'D'}">
							<tr>
								<td></td>
								<c:choose>
									<c:when test="${customer.csPortalStatus == 'C'}">
										<td class="contenttext_blk"><spring:message code="label.csPortalHKT" />
									</c:when>
									<c:when test="${customer.csPortalStatus == 'H'}">
										<td class="contenttext_blk"><spring:message code="label.csPortalClub" />
									</c:when>
									<c:otherwise>
										<td class="contenttext_blk"><spring:message code="label.csPortalAll" />
									</c:otherwise>
								</c:choose>
							</tr>
						 </c:if>
					<tr>
						<td><spring:message code="label.mobccspreview.address"/></td>
						<td>
						<c:set var="displayAddress" value=""/>
						<c:if test="${! empty customer.flat}"><c:set var="displayAddress">Flat ${customer.flat}</c:set></c:if>
						<c:if test="${! empty customer.floor}"><c:set var="displayAddress">${displayAddress}<c:if test="${fn:length(displayAddress) > 0}">, </c:if>Floor ${customer.floor}</c:set></c:if>
						<c:if test="${! empty customer.lotNum}"><c:set var="displayAddress">${displayAddress}<c:if test="${fn:length(displayAddress) > 0}">, </c:if>Lot/Hse ${customer.lotNum}</c:set></c:if>
						<c:if test="${! empty customer.buildingName}"><c:set var="displayAddress">${displayAddress}<c:if test="${fn:length(displayAddress) > 0}">, </c:if>${customer.buildingName}</c:set></c:if>
						<c:if test="${! empty customer.streetNum}"><c:set var="displayAddress">${displayAddress}<c:if test="${fn:length(displayAddress) > 0}">, </c:if>${customer.streetNum}</c:set></c:if>
						<c:if test="${! empty customer.streetName}"><c:set var="displayAddress">${displayAddress}<c:if test="${fn:length(displayAddress) > 0}">, </c:if>${customer.streetName}</c:set></c:if>
						<c:if test="${! empty customer.streetCatgDesc}"><c:set var="displayAddress">${displayAddress}<c:if test="${fn:length(displayAddress) > 0}">, </c:if>${customer.streetCatgDesc}</c:set></c:if>
						<c:if test="${! empty customer.sectionCode && customer.sectionCode != 'ZZZZ' && ! empty customer.sectionDesc}"><c:set var="displayAddress">${displayAddress}<c:if test="${fn:length(displayAddress) > 0}">, </c:if>${customer.sectionDesc}</c:set></c:if>
						<c:if test="${! empty customer.districtDesc}"><c:set var="displayAddress">${displayAddress}<c:if test="${fn:length(displayAddress) > 0}">, </c:if>${customer.districtDesc}</c:set></c:if>
						<c:if test="${! empty customer.areaDesc}"><c:set var="displayAddress">${displayAddress}<c:if test="${fn:length(displayAddress) > 0}">, </c:if>${customer.areaDesc}</c:set></c:if>

						${displayAddress}
						</td>
					</tr>
					<c:if test='${customer.addrProofInd == "5"}'> 
					<tr>
						<td>Pre-Activation</td>
						<td>
							<c:if test='${customer.bomCustAddrOverrideInd == "Y"}'> 
								System will override the BOM customer register address during order sign-off.
							</c:if>
						</td>
					</tr>
				</c:if>
				<!--Start of Combine Account Info  -->
								<tr>
									<td><spring:message code="label.mobccspreview.acctType"/></td>																															
									<c:choose>
									
								    <c:when test='${customer.acctType == "current"}'>
								     	<td>Current Account </td>								      
								 <tr>
								   <td><spring:message code="label.mobccspreview.activeMobileNum"/></td> 
								     	<td>${customer.srvNum}</td>	
								     
							       </tr>							    		
							        <tr>
								    <td><spring:message code="label.mobccspreview.acctNum"/></td>    
								         <td>${customer.acctNum}</td>	
							      </tr>									   			      
								    </c:when>    
								    <c:otherwise>
								        <td>New Account </td>								   
								    </c:otherwise>
								</c:choose>								
								</tr>								
								  									
								<!--End of Combine Account Info  -->
					<tr>
						<td><spring:message code="label.mobccspreview.billingAddress"/></td>
						<td>
						<c:set var="displayBillingAddress" value=""/>
						<c:if test="${! empty customer.billingFlat}"><c:set var="displayBillingAddress">Flat ${customer.billingFlat}</c:set></c:if>
						<c:if test="${! empty customer.billingFloor}"><c:set var="displayBillingAddress">${displayBillingAddress}<c:if test="${fn:length(displayBillingAddress) > 0}">, </c:if>Floor ${customer.billingFloor}</c:set></c:if>
						<c:if test="${! empty customer.billingLotNum}"><c:set var="displayBillingAddress">${displayBillingAddress}<c:if test="${fn:length(displayBillingAddress) > 0}">, </c:if>Lot/Hse ${customer.billingLotNum}</c:set></c:if>
						<c:if test="${! empty customer.billingBuildingName}"><c:set var="displayBillingAddress">${displayBillingAddress}<c:if test="${fn:length(displayBillingAddress) > 0}">, </c:if>${customer.billingBuildingName}</c:set></c:if>
						<c:if test="${! empty customer.billingStreetNum}"><c:set var="displayBillingAddress">${displayBillingAddress}<c:if test="${fn:length(displayBillingAddress) > 0}">, </c:if>${customer.billingStreetNum}</c:set></c:if>
						<c:if test="${! empty customer.billingStreetName}"><c:set var="displayBillingAddress">${displayBillingAddress}<c:if test="${fn:length(displayBillingAddress) > 0}">, </c:if>${customer.billingStreetName}</c:set></c:if>
						<c:if test="${! empty customer.billingStreetCatgDesc}"><c:set var="displayBillingAddress">${displayBillingAddress}<c:if test="${fn:length(displayBillingAddress) > 0}">, </c:if>${customer.billingStreetCatgDesc}</c:set></c:if>
						<c:if test="${! empty customer.billingSectionCode && customer.billingSectionCode != 'ZZZZ' && ! empty customer.billingSectionDesc}"><c:set var="displayBillingAddress">${displayBillingAddress}<c:if test="${fn:length(displayBillingAddress) > 0}">, </c:if>${customer.billingSectionDesc}</c:set></c:if>
						<c:if test="${! empty customer.billingDistrictDesc}"><c:set var="displayBillingAddress">${displayBillingAddress}<c:if test="${fn:length(displayBillingAddress) > 0}">, </c:if>${customer.billingDistrictDesc}</c:set></c:if>
						<c:if test="${! empty customer.billingAreaDesc}"><c:set var="displayBillingAddress">${displayBillingAddress}<c:if test="${fn:length(displayBillingAddress) > 0}">, </c:if>${customer.billingAreaDesc}</c:set></c:if>
						
						${displayBillingAddress}
						</td>
					</tr>

					<!-- Bill Medium -->					
						<tr>
							<td>Bill Medium</td>
							<td>	
									SMS
						             <c:forEach var="billMedia" items="${selectedBillMediaList}">    
			 							 	 
			 							 <c:if test="${! empty customer.selectedBillMedia}">	
			 							 /<c:out value="${billMedia.codeDesc}"/>  
			 							 </c:if>
									</c:forEach>
							</td>
						</tr>
				</table>
				
	  <!-- ------------------ Actual User Table ----------------------------- -->
	  
      	<sb-ui:actualuserinfo value="${customer.actualUserDTO}" sameascust="${customer.sameAsCust}" />
      
      <!-- ------------------ Actual User Table ----------------------------- -->
				
				<!-- Package Summary -->
			
					<!-- package Summary table -->
		<table width="100%" border="0" cellspacing="0">
			<tr>
				<td valign="top" bgcolor="#FFFFFF" >
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td class="table_title" colspan="5">Package Summary</td>
					</tr>
					
					<tr>
						<td >
							Basket ID 
						</td >
						
						<td  >
						${basketSession}
						</td >
						
					</tr>
					<tr>
						<td>
							Basket Description 
						</td>
						
						<td>
							 ${basket.description} 
						</td>
						
					</tr>
					
					<tr>
						<td colspan="5"><!-- image and price table -->
						<table cellspacing="0" class="contenttext" width="100%" > 
							<tr >
								<c:if
									test='${mobileDetail.modelName != null}'>
									<td style="font-family:sans-serif;font-size: 14px" valign="top"><!-- image here -->
									<p class="contenttextgreen" ><img
										src="${mobileDetail.modelImagePath}"
										alt="${mobileDetail.modelName}" width="80" /></p>
									<span class="posttext">${mobileDetail.modelName}</span>
									 <!-- end of image --></td>
								</c:if> <!-- end of image -->

								<td><!-- price table here -->
								<table cellspacing="1" class="contenttext" style="width: 100%" >
								<colgroup></colgroup>
								<colgroup style="width: 85px; text-align: right; vertical-align: top"></colgroup>
								<colgroup style="width: 75px; text-align: right; vertical-align: top"></colgroup>
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
										<td class="table_title table_title_2row">
											<span>
												<spring:message code="label.basket.upfront"/>
												<br><spring:message code="label.basket.upfront.1"/>
											</span>
										</td>
									</tr>
									<!-- sum only 'RP', 'BFEE', 'BVAS' -->
									<c:set var="vasOnetimeAmt" value="0"/>
									<c:set var="vasOnetimeHSAmt" value="0"/>
									<c:set var="simOneTimeAmt" value="0"/>
									<c:set var="rpOneTimeAmtWithoutWaive" value="0"/>
									<c:set var="rpOneTimeAmt" value="0"/>
                  <c:if test="${not empty mobSponsorshipDTO.staffId }">
                    <tr>
                      <td class="contenttext" align="right" >
                        <button type="button" onclick="showPopup('mobstaffsponsorshipinfo.html?action=ENQUIRY')">Staff Sponsorship</button>
                      </td>
                      <td class="BGgreen2">&nbsp;</td>
                      <td class="BGgreen2">&nbsp;</td>
                    </tr>
                  </c:if>
									
									<c:forEach items="${vasHSRPList}" var="vasHSRP">
									<tr>
										<!-- vasHSRP.itemType: ${vasHSRP.itemType} -->
										<td class="contenttext">
											<c:if test="${(ocidStatus == 'Pending' || ocidStatus == 'Completed') 
											&& (orderDTO.orderStatus == '02' || (orderDTO.orderStatus == '99' && (fn:substring(orderDTO.reasonCd, 0, 1) == 'J' || orderDTO.reasonCd == 'K001')))}">
												<c:if test='${not empty MultiSimInfoList}'>
													<c:forEach items="${MultiSimInfoList}" var="msi">
														<c:if test='${msi.itemId == vasHSRP.itemId}'>
																<a  href="#" class="nextbutton3" style="float:right;"
																onclick="javascript:window.open('multisiminfo.html?action=amend&vasitem=${msi.itemId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')">
																Update Multi-SIM MNP</a>
														</c:if>
													</c:forEach>
												</c:if>
											</c:if>
											${vasHSRP.itemHtml}
										</td>
										<td class="BGgreen2">
											<c:if test='${not empty vasHSRP.itemRecurrentAmt && vasHSRP.itemRecurrentAmt != 0}'>
												$<fmt:formatNumber value="${vasHSRP.itemRecurrentAmt}" pattern="#,###.####" />/month
											</c:if>
										</td>
										<td class="BGgreen2">
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
													<c:if test="${empty vasDetail.simWaiveReason}">
														<c:set var="simOneTimeAmt" value="${simOneTimeAmt + vasHSRP.itemOnetimeAmt}"/>
													</c:if>
												</c:if>
												<c:if test="${vasHSRP.itemType == 'RP'}">
													<c:set var="rpOneTimeAmtWithoutWaive" value="${rpOneTimeAmtWithoutWaive + vasHSRP.itemOnetimeAmt}"/>
													<c:if test="${empty vasDetail.rpWaiveReason}">
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
									<td width="25%"><b><sb-util:attblookup  codeType="ATTB_LKUP" codeId="${componentDTO.compAttbId }"  local="en"  /></b>
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
			
		
		<!-- Stock Assignment Information -->
		<c:if test="${! empty stockList && orderDTO.busTxnType != 'DRAF'}">
		<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="white">
		<colgroup style="width: 25%"></colgroup>
		<colgroup style="width: 25%"></colgroup>
		<colgroup style="width: 25%"></colgroup>
		<colgroup style="width: 25%"></colgroup>
			<tr>
				<td class="table_title" colspan="4">
					<spring:message code="label.mobccspreview.header.stockList"/>
				</td>
			</tr>
			<tr>
				<td class="table_title"><spring:message code="label.mobccspreview.stockList.itemType"/></td>
				<td class="table_title"><spring:message code="label.mobccspreview.stockList.itemCode"/></td>
				<td class="table_title"><spring:message code="label.mobccspreview.stockList.itemDesc"/></td>
				<td class="table_title"><spring:message code="label.mobccspreview.stockList.itemSerial"/></td>
			</tr>
			<c:forEach items="${stockList}" var="stockList">
			<tr>
				<td>${stockList.type}</td>
				<td>${stockList.itemCode}</td>
				<td>${stockList.itemDesc}</td>
				<td>${stockList.itemSerial}</td>
			<tr>
			</c:forEach>
			<tr>
				<td>
					<input type="button" id="checkStockQtyButton" 
							value="Check Stock Quantity" 
							onClick="checkStockQty('<c:out value='${orderId}'/>')">
				</td>
			<tr>
		</table>
		</c:if>
		
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
			
			<!-- Quota Plan Info -->
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
		<!-- Quota Plan Info END -->
		
		<!-- Service Details -->
		<c:if test="${orderDTO.busTxnType != 'DRAF'}">
		<table width="100%" border="0" cellspacing="0" class="contenttext">
		<colgroup style="width: 25%"></colgroup>
		<colgroup style="width: 75%"></colgroup>
			<tr>
				<td class="table_title" colspan="2"><spring:message code="label.mobccspreview.header.serviceDetail"/></td>
			</tr>
			<tr>
				<td><spring:message code="label.mobccspreview.serviceDetail.serviceType"/></td>
				<td>
					<c:choose>
						<c:when test="${vasMrtSelectionSession.chinaInd == 'Y'}">
							1C2N
							<c:choose>
								<c:when test="${mrt.goldenInd == 'Y'}">
									(New MRT with Golden MRT request)
								</c:when>
								<c:when test="${mrt.goldenInd == 'N' && mrt.mnpInd == 'N'}">
									(New MRT)
								</c:when>
								<c:when test="${mrt.goldenInd == 'N' && mrt.mnpInd == 'Y'}">
									 (MNP)
								</c:when>
							</c:choose>
						</c:when>
						<c:when test="${mrt.mrtInd == 'O'}">
							<c:choose>
								<c:when test="${mrt.goldenInd == 'Y'}">
									New MRT with Golden MRT request
								</c:when>
								<c:when test="${mrt.goldenInd == 'N' && mrt.mnpInd == 'N'}">
									New MRT
								</c:when>
								<c:when test="${mrt.goldenInd == 'N' && mrt.mnpInd == 'Y'}">
									MNP
								</c:when>
							</c:choose>
						</c:when>
						<c:when test="${mrt.mrtInd == 'A'}">
							New Number + MNP
						 </c:when>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td><spring:message code="label.mobMsisdn"/></td>
				<td>
					<c:choose>
						<c:when test="${vasMrtSelectionSession.chinaInd == 'Y'}">
							<c:choose>								
								<c:when test="${mrt.goldenInd == 'Y' && ! empty mrt.mobMsisdn}">${mrt.mobMsisdn} (Golden Number) / ${vasMrtSelectionSession.msisdn} (China Number)</c:when>
								<c:when test="${mrt.goldenInd == 'N' && mrt.mnpInd == 'Y'}">${mrt.mnpMsisdn}(MNP Number) / ${vasMrtSelectionSession.msisdn} (China Number)</c:when>
								<c:when test="${mrt.goldenInd == 'N' && mrt.mnpInd == 'N'}">${mrt.mobMsisdn} (New MRT Number) / ${vasMrtSelectionSession.msisdn} (China Number)</c:when>
							</c:choose>
						</c:when>				
						<c:when test="${mrt.mrtInd == 'O'}">
							<c:choose>
								<c:when test="${mrt.goldenInd == 'Y' && ! empty mrt.mobMsisdn}">${mrt.mobMsisdn}</c:when>
								<c:when test="${mrt.goldenInd == 'N' && mrt.mnpInd == 'Y'}">${mrt.mnpMsisdn}</c:when>
								<c:when test="${mrt.goldenInd == 'N' && mrt.mnpInd == 'N'}">${mrt.mobMsisdn}</c:when>
							</c:choose>
						</c:when>
						<c:when test="${mrt.mrtInd == 'A'}">${mrt.mobMsisdn} (New MRT Number) / ${mrt.mnpMsisdn} (MNP Number)</c:when>
					</c:choose>					
				</td>
			</tr>
			<c:choose>
				<c:when test="${! empty mrt.msisdnLvl}">				
					<tr>
						<td><spring:message code="label.msisdnlvl"/></td>
						<td>${mrt.msisdnLvl}</td>
					</tr>
				</c:when>
			</c:choose>			
			<c:choose>
				<c:when test="${vasMrtSelectionSession.chinaInd == 'Y'}">				
					<tr>
						<td>China Mobile Number Level</td>
						<td>${vasMrtSelectionSession.msisdnLvl}</td>
					</tr>
				</c:when>
			</c:choose>				
			<c:choose>
				<c:when test="${! empty mrt.serviceReqDateStr}">				
					<tr>
						<td><spring:message code="label.serviceReqDate"/></td>
						<td>${mrt.serviceReqDateStr}</td>
					</tr>
				</c:when>
			</c:choose>	
			<c:choose>
			   	<c:when test="${! empty customer.billPeriod}">				
					<tr>
						<td><spring:message code="label.billDate"/></td>
						<td>${billDate}</td>
					</tr>
				</c:when> 	
			</c:choose>
			<c:choose>
				<c:when test="${! empty mrt.cutOverDateStr}">				
					<tr>
						<td><spring:message code="label.cutOverDate"/></td>
						<td>${mrt.cutOverDateStr}</td>
					</tr>
				</c:when>
			</c:choose>
			<c:if test='${!empty mrt.origActDateStr}'>
				<tr>
					<td>Original Activation Date</td>
					<td>${mrt.origActDateStr} </td>
				</tr>
			</c:if>
			
			<c:if test='${ssMrtSelectionSession.ssSubscribed}'>
				<tr>
					<td>Secretarial Service Number</td>
					<td>${ssMrtSelectionSession.secSrvNum} </td>
				</tr>							
			</c:if>
		</table>
		</c:if>
				
      <!-- ------------------ Deposit Table ----------------------------- -->
      <sb-ui:depositinfo value="${depositInfo.depositDTOList }" var="depositAmount"/>
        
      <!-- ------------------ Deposit Table ----------------------------- -->       
				

				<!-- Upfront Payment Method -->
				<c:if test="${orderDTO.busTxnType != 'DRAF'}">
				<table width="100%" border="0" cellspacing="0" class="contenttext">
				<colgroup style="width: 25%"></colgroup>
				<colgroup style="width: 75%"></colgroup>
					<tr>
						<td class="table_title" colspan="2">
							<spring:message code="label.mobccspayment.header.upfront"/>
							<c:if test="${paymentUpFront.byPassValidation == true}">
								(By-Passed Validator)
							</c:if>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccspreview.payMethod"/>
						</td>
						<td>
						<c:choose>
						<c:when test="${paymentUpFront.payMethodType  == 'M'}">Cash</c:when>
						<c:when test="${paymentUpFront.payMethodType  == 'C'}">Credit Card</c:when>
						<c:when test="${paymentUpFront.payMethodType  == 'I'}">Credit Card Installment</c:when>
						</c:choose>
						</td>
					</tr>
					<c:choose>
					<c:when test="${paymentUpFront.payMethodType == 'M'}">
					</c:when>
					<c:when test="${paymentUpFront.payMethodType == 'C' || paymentUpFront.payMethodType == 'I'}">

					<tr>
						<td><spring:message code="label.creditCardNum"/></td>
						<td>${paymentUpFront.creditCardNum}  </td>
					</tr>
					<tr>
						<td><spring:message code="label.creditCard.expiry"/></td>
						<td>${paymentUpFront.creditExpiryMonth} / ${paymentUpFront.creditExpiryYear}</td>
					</tr>
					<tr>
						<td><spring:message code="label.creditCardHolderName"/></td>
						<td>${paymentUpFront.creditCardHolderName}</td>
					</tr>
					<tr>
						<td><spring:message code="label.creditCardIssueBankCd"/></td>
						<td>${paymentUpFront.creditCardIssueBankCd} ${paymentUpFront.creditCardIssueBankName}</td>
					</tr>
					</c:when>
					</c:choose>
					<c:choose>
					<c:when test="${paymentUpFront.payMethodType == 'I'}">
					<tr>
						<td><spring:message code="label.ccInstSchedule"/></td>
						<td>${paymentUpFront.ccInstSchedule}  </td>
					</tr>
					</c:when>
					</c:choose>
				</table>
				</c:if>
				
				<!-- Payment Transaction Summary -->
				<c:if test="${orderDTO.busTxnType != 'DRAF'}">
				<c:if test="${! empty orderDTO.paidAmt}">
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
								$<fmt:formatNumber value="${basket.prePaymentAmt + vasOnetimeAmt - basket.adminCharge - rpOneTimeAmtWithoutWaive }" pattern="#,###.####" />
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
			                <c:if test="${not empty vasDetail.rpWaiveReason}">
			                	(Waived: <sb-util:reasonlkup  codeType="WAIVE_OT" codeId="${vasDetail.rpWaiveReason}" appDate="${orderDTO.appInDate }" />)
			                </c:if>
			              </td>
			            </tr>
						<tr>
							<td><spring:message code="label.mobccspreview.paymentTransHist.adminCharge"/></td>
							<td>
								$<fmt:formatNumber value="${basket.adminCharge + mupAdminCharge}" pattern="#,###.####" />
							</td>
						</tr>
						<tr>
							<td>SIM Charge</td>
							<td>
								$<fmt:formatNumber value="${simOneTimeAmt}" pattern="#,###.####" />
								<c:if test="${not empty vasDetail.simWaiveReason}">
				                	(Waived)
				                </c:if>
							</td>
						</tr>
            <tr>
              <td>Deposit Charge</td>
              <td>
                $<fmt:formatNumber value="${depositAmount}" pattern="#,###.####" />
              </td>
            </tr>						
						<tr>
							<td><spring:message code="label.mobccspreview.paymentTransHist.paidAmt"/></td>
							<td>
								$<fmt:formatNumber value="${orderDTO.paidAmt}" pattern="#,###.####" />
								<a href="#" onClick="paymentTrxn();" class="nextbutton3" style="margin-left: 150px">Payment Transaction</a>
							</td>
						</tr>
						<tr>
							<td colspan="2"><hr></td>
						</tr>
						<tr>
							<td><spring:message code="label.mobccspreview.paymentTransHist.osAmt"/></td>
							<td>
								$<fmt:formatNumber value="${basket.upfrontAmt + basket.prePaymentAmt + vasOnetimeAmt + vasOnetimeHSAmt + depositAmount + simOneTimeAmt + (rpOneTimeAmt-rpOneTimeAmtWithoutWaive) + mupAdminCharge - orderDTO.paidAmt}" pattern="#,###.####" />
							</td>
						</tr>
					</table>
				</c:if>
				</c:if>

				<!-- Monthly Payment Method -->
				<c:if test="${orderDTO.busTxnType != 'DRAF'}">
				<table width="100%" border="0" cellspacing="0" class="contenttext">
				<colgroup style="width: 25%"></colgroup>
				<colgroup style="width: 75%"></colgroup>
					<tr>
						<td class="table_title" colspan="2">
							<spring:message code="label.mobccspayment.header.monthly"/>
							<c:if test="${paymentMonthy.byPassValidation == true}">
								(By-Passed Validator)
							</c:if>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccspreview.payMethod"/>
						</td>
						<td>
						<c:choose>
						<c:when test="${paymentMonthy.payMethodType == 'M'}">Cash</c:when>
						<c:when test="${paymentMonthy.payMethodType == 'A'}">Auto</c:when>
						<c:when test="${paymentMonthy.payMethodType == 'C'}">Credit Card</c:when>
						</c:choose>
						</td>
					</tr>
					<c:choose>
					<c:when test="${paymentMonthy.payMethodType == 'M'}">
					</c:when>
					<c:when test="${paymentMonthy.payMethodType == 'A'}">
					<tr>
						<td>Bank Name</td>
						<td>${paymentMonthy.bankName}</td>
					</tr>
					<tr>
						<td><spring:message code="label.bankAcctNum"/></td>
						<td>${paymentMonthy.bankAcctNum}</td>
					</tr>
					<tr>
						<td><spring:message code="label.bankAcctHolderName"/></td>
						<td>${paymentMonthy.bankAcctHolderName}</td>
					</tr>
					</c:when>
					<c:when test="${paymentMonthy.payMethodType == 'C'}">
					<tr>
						<td><spring:message code="label.creditCardNum"/></td>
						<td>${paymentMonthy.creditCardNum}</td>
					</tr>
					<tr>
						<td><spring:message code="label.creditCard.expiry"/></td>
						<td>${paymentMonthy.creditExpiryMonth} / ${paymentMonthy.creditExpiryYear}</td>
					</tr>
					<tr>
						<td><spring:message code="label.creditCardHolderName"/></td>
						<td>${paymentMonthy.creditCardHolderName}</td>
					</tr>
					<tr>
						<td><spring:message code="label.creditCardIssueBankCd"/></td>
						<td>${paymentMonthy.creditCardIssueBankCd} ${paymentMonthy.creditCardIssueBankName}</td>
					</tr>
					</c:when>
					</c:choose>
				</table>
				</c:if>

				<!-- Delivery Information -->
				<c:if test="${orderDTO.busTxnType != 'DRAF'}">
				<table width="100%" border="0" cellspacing="0" class="contenttext">
				<colgroup style="width: 25%"></colgroup>
				<colgroup style="width: 75%"></colgroup>
					<tr>
						<td class="table_title" colspan="2">
							<spring:message code="label.mobccsdelivery.header"/>
							<c:if test="${delivery.byPassValidation == true}">
								(By-Passed Validator)
							</c:if>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccspreview.deliveryMethod"/>
						</td>
						<td>
						<c:choose>
						<c:when test="${delivery.deliveryInd == 'D'}">Delivery by Courier</c:when>
						<c:when test="${delivery.deliveryInd == 'P'}">Pick Up at Centre</c:when>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.delivery.urgent"/>
						</td>
						<td>
						<c:choose>
						<c:when test="${delivery.urgentInd == true}">Yes</c:when>
						<c:when test="${delivery.urgentInd  == false}">No</c:when>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccsdelivery.custName"/>
						</td>
						<td>${delivery.primaryContact.title} ${delivery.primaryContact.contactName}</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccsdelivery.contactPhone.1st"/>
						</td>
						<td>${delivery.primaryContact.contactPhone}</td>
					</tr>
					<c:choose>
					<c:when test="${! delivery.recieveByThirdPartyInd}">
					<tr>
						<td><spring:message code="label.mobccspreview.delivery.receiverName"/></td>
						<td>${delivery.primaryContact.title} ${delivery.primaryContact.contactName}</td>
					</tr>
					<tr>
						<td><spring:message code="label.mobccspreview.delivery.receiverContactPhone"/></td>
						<td>${delivery.primaryContact.contactPhone}</td>
					</tr>
					</c:when>
					<c:otherwise>
					<tr>
						<td><spring:message code="label.mobccspreview.delivery.receiverName"/></td>
						<td>${delivery.thirdPartyContact.title} ${delivery.thirdPartyContact.contactName}</td>
					</tr>
					<tr>
						<td><spring:message code="label.mobccspreview.delivery.receiverContactPhone"/></td>
						<td>${delivery.thirdPartyContact.contactPhone}</td>
					</tr>
					</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${delivery.deliveryInd == 'D'}">
							<c:choose>
								<c:when test="${delivery.dummyDeliveryDateInd == false}">
									<tr>
										<td><spring:message code="label.delivery.date"/></td>
										<td><fmt:formatDate pattern="dd/MM/yyyy" value="${delivery.deliveryDate}"/></td>
									</tr>
									<tr>
										<td><spring:message code="label.delivery.time"/></td>
										<td>
											<c:if test="${not empty delivery.timeSlotFrom && not empty delivery.timeSlotTo}">
												${delivery.timeSlotFrom}-${delivery.timeSlotTo}
											</c:if>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td><spring:message code="label.delivery.date"/></td>
										<td>Dummy Delivery Mode</td>
									</tr>
									<tr>
										<td><spring:message code="label.delivery.time"/></td>
										<td>Dummy Delivery Mode</td>
									</tr>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test="${delivery.custAddressFlag2 == true}">
									<tr>
										<td><spring:message code="label.mobccsdelivery.header.deliveryAddr"/></td>
										<c:set var="displayDeliveryAddress" value=""/>
										<td>
											<c:set var="displayDeliveryAddress">${delivery.address1}</c:set>
											<c:if test="${! empty delivery.address2}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.address2}</c:set></c:if>
											<c:if test="${! empty delivery.address3}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.address3}</c:set></c:if>
											<c:if test="${! empty delivery.districtDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.districtDesc}</c:set></c:if>
											<c:if test="${! empty delivery.areaDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.areaDesc}</c:set></c:if>
											${displayDeliveryAddress}
										</td>
								</c:when>
								<c:otherwise>
									<tr>
										<td><spring:message code="label.mobccsdelivery.header.deliveryAddr"/></td>
										<c:set var="displayDeliveryAddress" value=""/>
										<td>
											<c:if test="${! empty delivery.flat}"><c:set var="displayDeliveryAddress">Flat ${delivery.flat}</c:set></c:if>
											<c:if test="${! empty delivery.floor}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>Floor ${delivery.floor}</c:set></c:if>
											<c:if test="${! empty delivery.lotNum}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>Lot/Hse ${delivery.lotNum}</c:set></c:if>
											<c:if test="${! empty delivery.buildingName}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.buildingName}</c:set></c:if>
											<c:if test="${! empty delivery.streetNum}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.streetNum}</c:set></c:if>
											<c:if test="${! empty delivery.streetName}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.streetName}</c:set></c:if>
											<c:if test="${! empty delivery.streetCatgDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.streetCatgDesc}</c:set></c:if>
											<c:if test="${! empty delivery.sectionCode && delivery.sectionCode != 'ZZZZ' && ! empty delivery.sectionDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.sectionDesc}</c:set></c:if>
											<c:if test="${! empty delivery.districtDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.districtDesc}</c:set></c:if>
											<c:if test="${! empty delivery.areaDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.areaDesc}</c:set></c:if>
											${displayDeliveryAddress}
										</td>
							    	</tr>
								</c:otherwise>
							</c:choose>
							<c:if test='${!empty delivery.actualDeliveryDate}'>
								<tr>
									<td><spring:message code="label.mobccspreview.actualDeliveryDate"/></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy" value="${delivery.actualDeliveryDate}"/></td>
								</tr>
							</c:if>
							<c:if test='${!empty delivery.location}'>
								<tr>
									<td>
										<spring:message code="label.mobccspreview.delivery.warehouse"/>
									</td>
									<td><my:code id="${delivery.location}" codeType="STOCK_LOC" source=""></my:code></td>
								</tr>
							</c:if>
							<c:if test='${!empty delivery.cvId}'>
								<tr>
									<td>
										<spring:message code="label.mobccspreview.delivery.courierCompany"/>
									</td>
									<td>(${delivery.cvId}) <my:code id="${delivery.cvId}" codeType="COUR_VENDOR" source=""></my:code></td>
								</tr>
							</c:if>
						</c:when>
						<c:when test="${delivery.deliveryInd == 'P'}">
							<tr>
								<td>
									<spring:message code="label.mobccspreview.delivery.deliveryCentre"/>
								</td>
								<td>${delivery.deliveryCentre}</td>
							</tr>
						</c:when>
					</c:choose>
				</table>
				</c:if>
				
				<!-- Support Document -->
				<c:if test="${orderDTO.busTxnType != 'DRAF'}">
				<table width="100%" border="0" cellspacing="0" class="contenttext">
				<colgroup style="width: 25%"></colgroup>
				<colgroup style="width: 37%"></colgroup>
				<colgroup style="width: 37%"></colgroup>
					<tr>
						<td class="table_title" style="text-align: left; white-space: nowrap">
							<spring:message code="label.mobccssupportdoc.header"/>
							<spring:message code="label.mobccssupportdoc.header.1"/>
						</td>
						<td class="table_title" style="text-align: left; white-space: nowrap">
							<spring:message code="label.mobccssupportdoc.faxServerSerialNo"/>
							<spring:message code="label.mobccssupportdoc.faxServerSerialNo.1"/>
						</td>
						<td class="table_title" style="text-align: left; white-space: nowrap">
							<spring:message code="label.mobccssupportdoc.receivedByFax"/>
							<spring:message code="label.mobccssupportdoc.receivedByFax.1"/>
						</td>
					</tr>
						<c:forEach items="${supportingDoc.mobCcsSupportDocDTOList}" var="doc">
						<c:if test="${doc.mandatory==true}">
							<tr>
								<td class="contenttext">${doc.docDesc}</td>
								<td class="contenttext">${doc.faxServerSerialNo}</td>
								<td class="contenttext">
								<c:choose>
								<c:when test="${doc.receivedByFax}">Y</c:when>
								<c:otherwise>N</c:otherwise>
								</c:choose>
								</td>
							<tr>
						</c:if>
						</c:forEach>
				</table>
				</c:if>
				
				<table width="100%" border="0" cellspacing="0" class="contenttext">
				<colgroup style="width: 25%"></colgroup>
				<colgroup style="width: 75%"></colgroup>
					<tr>
						<td class="table_title" colspan="2">
							<spring:message code="label.mobccsstaffinfo.header"/>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccspreview.sales"/>
						</td>
						<td>${staffInfo.salesName}&nbsp;(${staffInfo.salesId})</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccsstaffinfo.salesCentre"/>
						</td>
						<td>${staffInfo.salesCentre}</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccsstaffinfo.salesTeam"/>
						</td>
						<td>${staffInfo.salesTeam}</td>
					</tr>
					
					<tr>
						<td>
							<spring:message code="label.mobccsstaffinfo.contactPhone"/>
						</td>
						<td>${staffInfo.contactPhone}</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccsstaffinfo.position"/>
						</td>
						<td>${staffInfo.position}</td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.mobccspreview.callDate"/>
						</td>
						<td>${staffInfo.callDateStr}&nbsp;${staffInfo.callTimeStr}</td>
					</tr>
					<c:if test='${!empty staffInfo.callListDesc}'>
						<tr>
							<td>
								<spring:message code="label.mobccsstaffinfo.callList"/>
							</td>
							<td>${staffInfo.callListDesc}</td>
						</tr>
					</c:if>
					
					<c:if test='${!empty staffInfo.refSalesName}'>
						<tr>
							<td>
								<spring:message code="label.mobccsstaffinfo.refSalesName"/>
								<br><spring:message code="label.mobccsstaffinfo.refSalesId"/>
							</td>
							<td>${staffInfo.refSalesName}&nbsp;(${staffInfo.refSalesId})</td>
						</tr>
						<tr>
							<td>
								<spring:message code="label.mobccsstaffinfo.refSalesCentre"/>
							</td>
							<td>${staffInfo.refSalesCentre}</td>
						</tr>
						<tr>
							<td>
								<spring:message code="label.mobccsstaffinfo.refSalesTeam"/>
							</td>
							<td>${staffInfo.refSalesTeam}</td>
						</tr>
					</c:if>
				</table>
					<!-- iGuard information --> 
				<c:if test='${!empty orderDTO.iGuardSerialNo}'>
					<table width="100%" border="0" cellspacing="0" class="contenttext">
						<colgroup style="width: 25%"></colgroup>
						<colgroup style="width: 75%"></colgroup>
						<tr>
							<td class="table_title" colspan="2">Lost/Damaged Beyond
								Repair/Stolen Handset Reimbursement Plan Information<br>
								Accidental Damage Plus Repair Plan Information</td>
						</tr>



						<tr>

							<td>Serial No.</td>
							<td>${iGuardDto.serialNo}</td>
						</tr>
						<tr>

							<td>LDS Service Plan Monthly Fee</td>
							<c:if test='${!empty iGuardDto.ldsSrvPlanFee}'>
							    <td>${iGuardDto.ldsSrvPlanFee}</td>
							</c:if>
							<c:if test='${empty iGuardDto.ldsSrvPlanFee}'>
							    <td>N/A</td>
							</c:if>
						</tr>
						<tr>

							<td>AD Service Plan Monthly Fee</td>
							<c:if test='${!empty iGuardADDto.adSrvPlanFee}'>
							    <td>${iGuardADDto.adSrvPlanFee}</td>
							</c:if>
							<c:if test='${empty iGuardADDto.adSrvPlanFee}'>
							    <td>N/A</td>
							</c:if>
						</tr>
						<tr>

							<td>PCCW Mobile Number</td>
							<td>${iGuardDto.msisdn}</td>
						</tr>
						<tr>

							<td>Mobile Handset Brand/Model</td>
							<td>${iGuardDto.handsetDeviceDescription}</td>
						</tr>

						<tr>

							<td>IMEI No.</td>
							<td>${iGuardDto.imei}</td>
						</tr>
						<tr>

							<td>Target Effect Date</td>
							<td>${iGuardDto.tgtEffDate}</td>
						</tr>

						<tr>

							<td>Contract Period</td>
							<td>${iGuardDto.contractPeriod}</td>
						</tr>
						<tr>

							<td>Handset Purchase Price</td>
							<td><fmt:formatNumber value="${iGuardDto.hsPurchasePrice}"
									pattern="$#,###.####" />
							</td>

						</tr>
					</table>
				</c:if> <!-- END iGuard information -->
				
				
				<!-- start Privacy table information -->
				<table width="100%" border="0" cellspacing="0" class="contenttext">

					<tr>
						<td class="table_title"><spring:message code="label.privacyIndTitle" /></td>
					</tr>

					<tr>
						<td valign="top" bgcolor="#FFFFFF">
							<table width="100%" border="0" cellspacing="1">

								<tr>

									<td class="contenttext_blk"><c:if
											test="${customer.privacyInd == true}">
											<spring:message code="label.privacyIndY" />
										</c:if> <c:if test="${customer.privacyInd == false}">
											<spring:message code="label.privacyIndN" />
										</c:if></td>
								</tr>


							</table></td>
					</tr>
				</table> <!-- end Privacy table information -->

					
				<!--  Suppress Top Up table information -->
				
				<table width="100%" border="0" cellspacing="0" class="contenttext">
					<colgroup style="width: 60%"></colgroup>
					<colgroup style="width: 40%"></colgroup>
						<tr>
							<td class="table_title" ><spring:message code="label.suppressTopUpTitle"/></td>
							<td class="table_title" >Yes/No</td>
						</tr>
						
						<tr>
							<td><spring:message code="label.suppressLocalTopUpInd"/></td>
							<td>
								<c:choose>												
									<c:when test="${customer.suppressLocalTopUpInd == true}">Y</c:when>
									<c:when test="${customer.suppressLocalTopUpInd == false}">N</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td><spring:message code="label.suppressRoamTopUpInd"/></td>
							<td>
								<c:choose>												
									<c:when test="${customer.suppressRoamTopUpInd == true}">Y</c:when>
									<c:when test="${customer.suppressRoamTopUpInd == false}">N</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td><spring:message code="label.mob0060OptOutInd"/></td>
							<td>
								<c:choose>												
									<c:when test="${customer.mob0060OptOutInd == true}">Y</c:when>
									<c:when test="${customer.mob0060OptOutInd == false}">N</c:when>
								</c:choose>
							</td>
						</tr>
						<c:if test="${(customer.csPortalStatus=='C' || empty customer.csPortalStatus) && !(empty customer.emailAddr) && !(customer.csPortalInd=='N') }">
								
							<tr>
								<td width="50%"><b><spring:message code="label.optOutHKT"/></b></td>
								<td><c:choose>
									<c:when test="${customer.hktOptOut == true}">Y</c:when>
									<c:when test="${customer.hktOptOut == false}">N</c:when>
									</c:choose></td>
							</tr>
							</c:if>	
							<c:if test="${(customer.csPortalStatus=='H'|| empty customer.csPortalStatus) && !(customer.csPortalInd=='N') }">
							<tr>
								<td width="50%"><b><spring:message code="label.optOutClub"/></b></td>
								<td><c:choose>
									<c:when test="${customer.clubOptOut == true}">Y (Reason: <sb-util:codelookup codeId="${customer.clubOptRea }" codeType="OPT_OUT_REASON"  /> ${customerInfoDto.clubOptRmk})</c:when>
									<c:when test="${customer.clubOptOut == false}">N</c:when>
									</c:choose></td>
							</tr>
						</c:if>
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
									<td><pccw-util:codelookup codeType="PCRF_ALERT_TYPE" codeId="${customer.pcrfAlertType}" /></td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.pcrfAlertEmail"/></b></td>
									<td><c:choose>
										<c:when test="${customer.sameAsEbillAddr == true}"><spring:message code="label.sameAsEbillAddrInd" /></c:when>
										<c:when test="${customer.sameAsEbillAddr == false}">${customer.pcrfAlertEmail}</c:when>
										</c:choose></td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.pcrfSmsRecipient"/></b></td>
									<td><pccw-util:codelookup codeType="PCRF_SMS_RECIPIENT" codeId="${customer.pcrfSmsRecipient}" /></td>
								</tr>		
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.pcrfSmsNum"/></b></td>
									<td>${customer.pcrfSmsNum}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.smsOptOutFirstRoam"/></b></td>
									<td>${customer.smsOptOutFirstRoam}</td>
								</tr>
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b><spring:message code="label.smsOptOutRoamHu"/></b></td>
									<td>${customer.smsOptOutRoamHu}</td>
								</tr>
								
										<c:if test="${customer.pcrfMupAlert ne '0' and not empty customer.pcrfMupAlert}">
										<tr>
											<td width="6%">&nbsp;</td>
											<td width="50%"><b><spring:message code="label.pcrfMupAlert"/></b></td>
											<td>
											<c:if test="${customer.pcrfMupAlert eq '1'}">N</c:if>
											<c:if test="${customer.pcrfMupAlert eq '2'}">Y</c:if>
											</td>
										</tr>
										</c:if>
																	
							</table></td>
						</tr>
		
				</table>
							
				<!--  Local/Roaming Data Alert Settings table information -->
				
				<c:if test="${not empty clubPointDetailDTO}">
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
											<pccw-ui:clubpointinfo clubPointValue="${clubPointDetailDTO}" theClubLogin="${theClubLogin}" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>		
				</c:if>
				
			<c:choose>
				<c:when test="${customer.careStatus=='Y'}">
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
										<c:when test="${customer.careOptInd=='I'}">
										<spring:message code="label.iGuardSubscribedCareCash" />
										</c:when>
										<c:when test="${customer.careOptInd=='O'}">
										<spring:message code="label.iGuardUnsubscribedCareCash" />
										</c:when>
										<c:when test="${customer.careOptInd=='P'}">
										<spring:message code="label.iGuardNotConsiderableCareCash" />
										</c:when>
									</c:choose>
								</td>
								</tr>
								<c:choose>
								<c:when test="${customer.careOptInd=='I'}">
								<tr>
									<td width="6%">&nbsp;</td>
									<td width="50%"><b>Customer don't want to receive any promotion of product service or special offers from i-Guard</b></td>
									<c:choose>
									<c:when test="${customer.careDmSupInd=='I'}">
									<td>Y</td>
									</c:when>
									<c:when test="${customer.careDmSupInd=='O'}">
									<td>N</td>
									</c:when>
									</c:choose>
								</tr>
								</c:when>
								</c:choose>			
							</table>
						</td>
					</table>
				</c:when>
			</c:choose> 
				<!--  iGuard Care cash Table -->		
							
				<!--content--></td>
		</tr>
	</table>
	
		<!-- button -->
		<div class="buttonmenubox_R" id="buttonArea" style="text-align: right ;height:60px">
			<div id="buttonY">
						<c:if test="${action=='ENQUIRY'}">
						<c:if test="${customer.careStatus=='Y'&&customer.careOptInd=='I'}">
								
						<a name="areport" href="reportdownload.html?pdfLang=en&iGuardType=CARECASH&sbuid=${sbuid}&pLang=${pLang}"
																					target="_blank" onClick="enableConfirm()" class="nextbutton3"> i-Guard CARECASH
																					Cust info Form (Eng/Chi)</a>
																					
						<a href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT017&sbuid=${sbuid}&pLang=${pLang}"
																class="nextbutton3" target="_blank"> Resend/Email History to Customer
						</a>
						<a href="ordersendemailhist.html?orderId=${orderId}&action=PREVIEW&hideToolbar=true&templateId=RT018&sbuid=${sbuid}&pLang=${pLang}"
																class="nextbutton3" target="_blank"> Resend/Email History to iGuard Company
						</a>
						<br>
						<br>
						</c:if>
						</c:if>
						<c:if test='${bomsalesuser.channelId == 66}'>
						 <a href="#" class="nextbutton3" onClick="javascript:window.open('<%=mobCosUrl%>fulfillment/fulfillmentSupport?orderId=${orderId}&_al=new', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">Fulfillment Support</a>
						</c:if>
						
						<c:if test="${not empty orderId}">
							<a href="#" class="nextbutton3" onClick="javascript:window.open('mobccsfallouthistory.html?orderId=${orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">Fallout History</a>
						</c:if>
						
						<c:if test="${not empty orderId}">
							<a href="#" class="nextbutton3" onClick="javascript:window.open('mobccscsicase.html?orderId=${orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">CSI Case</a>
						</c:if>

						<c:if test="${not empty orderId}">
							<a href="#" class="nextbutton3" onClick="javascript:window.open('mobccscalllog.html?orderId=${orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">Call Log</a>
						</c:if>
						
						<c:if test="${not empty orderId}">
							<a href="#" class="nextbutton3" onClick="javascript:window.open('<%=mobCosUrl%>	mobapprovallog.html?orderId=${orderId}&_al=new', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">Approval Log</a>
						</c:if>
						<%-- 	<c:if test="${orderDTO.orderStatus == '02' || orderDTO.reasonCd == 'N000'}">  --%>
						<c:if test="${orderDTO.orderStatus == '02' || fn:startsWith(orderDTO.reasonCd, 'N')}">
						<%-- <a href="#" class="nextbutton3" onClick="javascript:window.open('mobccsdoarequest.html?orderId=${orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">DOA Request</a> --%>
						 <a href="#" class="nextbutton3" onClick="javascript:window.open('<%=mobCosUrl%>doarequest.html?orderId=${orderId}&_al=new', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">DOA Request</a>
						</c:if>
								
						<c:if test="${user.channelCd=='CFM' && (orderDTO.reasonCd == 'N002' || orderDTO.reasonCd == 'N003' || orderDTO.reasonCd == 'G002' || orderDTO.reasonCd == 'G003')}">
							<a href="#" class="nextbutton3" onClick="javascript:window.open('mobccsdoadelivery.html', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">DOA Delivery</a>
						</c:if>
						
						<c:if test="${(ocidStatus == 'Pending' || (ocidStatus == 'Completed' && mrt.mrtInd == 'A')) 
						&& (orderDTO.orderStatus == '02' || (orderDTO.orderStatus == '99' && (fn:substring(orderDTO.reasonCd, 0, 1) == 'J' || orderDTO.reasonCd == 'K001')))}">				
							<a  href="#" class="nextbutton3" onClick="javascript:window.open('mobccsdoaupdatemrt.html?orderId=${orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">Update SRD / MNP Info.</a>
						</c:if>
						<c:set var="orderRescueFound" value="false"/>
						<c:forEach items="${orderRescueList}" var="orderRescueDto">
							<c:if test="${not empty orderRescueDto.incidentNo}">
								<c:set var="orderRescueFound" value="true"/>
							</c:if>
						</c:forEach>
						<c:if test="${orderRescueFound}">
							<a  href="#" class="nextbutton3" onClick="javascript:window.open('mobccsorderrescuesearch.html?orderId=${orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">View Order Incident</a>
						</c:if>
						
						<%-- <c:if test="${customer.idDocType == 'BS' and customer.companyDoc == 'L'}">
							<a href="reportdownload.html?authorizedLetter=Y" class="nextbutton3" >Print Authorized Letter</a>
						</c:if> --%>
						<c:if test='${not empty orderDTO.ocid}'>
							<c:if test="${orderDTO.sboOrderInd =='N' }">
								
								<a href="mobccsreport.html?orderId=${orderDTO.orderId}&pdfLang=en&printSeq=SDGCIRHPTNFA&upDateStatus=N" class="nextbutton3" >View S&S Form(Eng)</a>
								<a href="mobccsreport.html?orderId=${orderDTO.orderId}&pdfLang=zh_HK&printSeq=SDGCIRHPTNFA&upDateStatus=N" class="nextbutton3" >View S&S Form(Chi)</a>
							</c:if>
						</c:if>
						<!-- online sales button -->
						<c:if test="${orderDTO.sboOrderInd =='Y' }">
							<a href="ordersendemailhist.html?orderId=${orderDTO.orderId}&templateId=RT004&action=PREVIEW&hideToolbar=true" target="_blank" class="nextbutton3" >Resend Email(Original Copy Only)</a>
							
							<a href="reportdownload.html" target="_blank" class="nextbutton3" >Retrieve S&S Form</a>
							<a href="mobccsreport.html?orderId=${orderDTO.orderId}&pdfLang=en&printSeq=SDGCIRHPTNEA&upDateStatus=N" class="nextbutton3" >View S&S Form(Eng)</a>
							<a href="mobccsreport.html?orderId=${orderDTO.orderId}&pdfLang=zh_HK&printSeq=SDGCIRHPTNEA&upDateStatus=N" class="nextbutton3" >View S&S Form(Chi)</a>
						</c:if>
						
						<c:if test='${empty action}'>
							<c:if test='${showDraftOrderButtonInd=="Y"}'>
								<a href="#" class="nextbutton3" name="asignoff"
								onclick="javascript:orderFormSubmit('DRAF',event);">Save As Draft Order</a>
							</c:if>
							<c:if test='${showPreOrderButtonInd=="Y"}'>
								<a href="#" class="nextbutton3" name="asignoff"
									onclick="javascript:orderFormSubmit('PRE',event);">Save As Pre-Order</a>
								</c:if>
	
							<c:if test='${showPendOrderButtonInd=="Y"}'>
							<a href="#" class="nextbutton3" name="asignoff"
								onclick="javascript:orderFormSubmit('PEND',event);">Save As Pend-Order</a>
							</c:if>
						</c:if>
						<c:if test='${bomsalesuser.channelId == 66}'>
							<c:choose>
								<c:when test='${orderDTO.orderStatus == "01" && orderDTO.checkPoint >= 400}'>
									Not allow to use "Change Courier Vendor" function[01-400].
								</c:when>
								<c:when test='${orderDTO.orderStatus == "02" || orderDTO.orderStatus == "03" || orderDTO.orderStatus == "04"}'>
									Not allow to use "Change Courier Vendor" function[02/03/04].
								</c:when>
								<c:otherwise>
									<a href="#" class="nextbutton3" onClick="javascript:window.open('<%=mobCosUrl%>courier/changecouriervendor?orderId=${orderId}&_al=new', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">Change Courier Vendor</a>
								</c:otherwise>
							</c:choose>
						</c:if>
			</div>
		</div>
	

	<!-- end of button -->
	<input type="hidden" name="currentView" value="mobccspreview" />
	<input type="hidden" name="orderType" value="" />
	<input type="hidden" name="OrderButtonControl" value="${showDraftOrderButtonInd}/${showPreOrderButtonInd}/${showPendOrderButtonInd}" />

</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>