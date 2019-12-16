<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<style type="text/css">
.sbOrderHistDiv { overflow-y:auto; display: inline-block; width: 100% }
.searchRow { padding: 15px 10px }
.label { display: inline-block; width: 85px; font-size: 12px; font-family: "Verdana", "Helvetica", "Arial", "sans-serif" }
.labelFirst { width: 110px }
.input { display: inline-block; width: 120px }
.searchColHalf { overflow: hidden; width: 48%; display: inline-block; float: left  }
.even { background-color: #E8FFE8 }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px; font-family: "Helvetica", "Arial", "sans-serif" }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078 !important; padding: 5px 10px; position: relative }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.custPremier { font-weight: bold; color: red }
.inputGroup { display: inline-block; overflow: hidden; vertical-align: middle }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.sbOrderHistDiv { padding-bottom: 1em }
</style>
<![endif]-->

<script type="text/javascript">
	$(document).ready(function() {
		
		$(document).ready(function(){
		    $('form').attr('autocomplete', 'off');
		});
		
		
		$("#customerInformation input[type=text]").keypress(function(event) {
			switch(event.keyCode) {
			case 13:
				custInfoSearch();
				break;
			}
		});
			
		
			$('a#createEyeUpgradeOrderBtn')
			.click(
					function(event) {
						if("${sessionScope.bomsalesuser.channelId}" == 1){
							alert("<Sales> Remind Customer that his/her PCCW info of Fixed Line / Netvigator / NOW TV will be viewed during the process");
						}
						document.customerInformationForm.actionType.value = "CREATELTS";
						document.customerInformationForm.submit();
					});
			
			
			// control PCD & eyex button display
			$("#PCD").show();
			$("#PCD2").hide();
			$("#createEyeUpgradeOrderBtn").show();
			$("#createEyeUpgradeOrderBtn2").hide();
			
			if ($("#imsLtsButtonInd").val() == 'Y') {
				if ( "${isCC}" != "Y")
				{
					$("#PCD").hide();
					$("#PCD2").show();
				}
			}
			
			if ($("#ltsButtonInd").val() == 'Y') {
				$("#createEyeUpgradeOrderBtn").hide();
				$("#createEyeUpgradeOrderBtn2").show();
			}
			
	});

	function hidePCD() {
		
		if (document.customerInformationForm.idDocType.options[document.customerInformationForm.idDocType.selectedIndex].value == "BR"
		) {
			$("#PCD").hide();
			$("#PCD2").show();
			$("#createEyeUpgradeOrderBtn").show();
			$("#createEyeUpgradeOrderBtn2").hide();
		} else if (document.customerInformationForm.idDocType.options[document.customerInformationForm.idDocType.selectedIndex].value == "Certificate No"
		) {
			$("#PCD").hide();
			$("#PCD2").show();
			$("#createEyeUpgradeOrderBtn").hide();
			$("#createEyeUpgradeOrderBtn2").show();
		} else {
			$("#PCD").show();
			$("#PCD2").hide();
			$("#createEyeUpgradeOrderBtn").show();
			$("#createEyeUpgradeOrderBtn2").hide();
		}
	}
	
	function printErrMsg() {
		alert("BR and Certificate Customer is not supported in Springboard, please use written contract.");
	}

	function printLtsErrMsg() {
		alert("Certificate Customer is not supported in Springboard, please use written contract.");
	}
	
	function clickIdDocType() {
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";

		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
	}

	function clickIdDocNum() {
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";

		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
		//		document.customerInformationForm.idDocNum.select();
	}

	function clickServNumType() {
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";

		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
	}

	function clickServNum() {
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";

		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
	}

	function clickLoginIdPrefix() {
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";

		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
	}

	function clickLoginIdSuffix() {
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";

		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
	}

	function createMobOrder() {
		document.customerInformationForm.actionType.value = "CREATE";
		document.customerInformationForm.submit();
	}
	function createLtsOrder() {
		document.customerInformationForm.actionType.value = "CREATELTS";
		document.customerInformationForm.submit();
		document.customerInformationForm.submit();
	}
	function custInfoSearch() {
		$("#loginIdPrefix").val($("#loginIdPrefix").val().toLowerCase());
		document.customerInformationForm.actionType.value = "REFRESH";
		document.customerInformationForm.submit();
	}
	function custInfoClear() {
		document.customerInformationForm.actionType.value = "CLEAR";
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";
		document.customerInformationForm.submit();
	}
</script>

<form:form name="customerInformationForm" method="POST" commandName="customerInformation">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
<div class="previewForm">
<h3>Searching Key</h3> 
<div class="row searchRow">
	<div class="searchColHalf searchColHalfLeft"> 
		<span class="inputGroup">
			<span class="label labelFirst">ID Doc Type:</span>
			
			<span class="input">
				<form:select path="idDocType" onchange="hidePCD()" onclick="clickIdDocType()">
					<form:option value="NONE" label="Select" />
					<form:options items="${idDocTypeList}" />
				</form:select>
			</span>
		</span>
		<span class="inputGroup">
			<span class="label">ID Doc Num:</span>
			<form:input path="idDocNum" maxlength="20" onclick="clickIdDocNum()" />
		</span>
	</div>
	<div class="searchColHalf searchColHalfRight">
		 <span class="inputGroup">
		<span class="label">Company Name:</span>
<!--			<form:input path="" maxlength="20" onclick="" />-->
			<input type="text"/> 
		</span>
	</div>
	<div style="clear:both"></div>
</div>
<div class="row searchRow">
	<div class="searchColHalf searchColHalfLeft">
		<span class="label labelFirst">Service Number:</span>
		<span class="inputGroup">
			<span class="input">
				<form:select path="serviceNumberType" onclick="clickServNumType()">
					<form:option value="NONE" label="Select" />
					<form:options items="${serviceNumberTypeList}" />
				</form:select>
			</span>
			<form:input path="serviceNumber" maxlength="20" onclick="clickServNum()" />
		</span>
	</div>
	<div class="searchColHalf searchColHalfRight">
		<span class="label">Login ID: </span>
		<span class="inputGroup">
			<form:input onclick="clickLoginIdPrefix()" path="loginIdPrefix" cssStyle="text-transform:lowercase;" /> 
			<form:select path="loginIdSuffix" onclick="clickLoginIdSuffix()">
				<form:option value="NONE" label="Select" />
				<form:options items="${loginIdSuffixList}" />
			</form:select>
		</span>
	</div>
	<div style="clear:both"></div>
</div>
<div class="searchError">
	<form:errors path="customerInformationError" cssClass="error" /> 
	<form:errors path="idDocType" cssClass="error" /> 
	<form:errors path="idDocNum" cssClass="error" />
	<form:errors path="serviceNumberType" cssClass="error" /> 
	<form:errors path="serviceNumber" cssClass="error" /> 
	<form:errors path="loginIdSuffix" cssClass="error" /> 
	<form:errors path="loginIdPrefix" cssClass="error" />
	<div style="clear:both"></div>
</div>
<div class="row buttonmenubox_R">
	<a href="#" class="nextbutton" onClick="javascript:custInfoSearch();">Search</a>
	<a href="#" class="nextbutton" onClick="javascript:custInfoClear();">Clear</a>
	<div style="clear:both"></div>
</div>
</div>

	
	<br>

	<c:if test='${actionType == "REFRESH"}'>
	<c:choose>
	<c:when test='${!(errorSeverity == "3")}'>
		<%-- ***************************   CUSTOMER SEARCH RESULT   ********************************* --%>
		<c:if
			test='${empty custSearchResultList && empty serviceLineDTOList && empty custOrderHistoryList}'>

			<table width="100%" class="tablegrey">
				<tr>
					<td bgcolor="#FFFFFF">
					<table width="100%" border="" cellspacing="1" rules="none">
						<tr>
							<td class="table_title">Customer Search Result</td>
						</tr>
					</table>
					<%-- ***************************   BASIC CUSTOMER INFORMATION   ****************************** --%>
					<table width="100%" class="tablegrey">
						<tr>
							<td bgcolor="#FFFFFF">
							<table width="100%" border="" cellspacing="1" rules="none">
								<c:if test='${!(errorSeverity == "1")}'>
									<tr>
										<td><font face='Helvetica' >No Record Found.</font></td>
									</tr>
								</c:if>
								<c:if test='${errorSeverity == "1"}'>
								<tr>
									<td><font face='Helvetica' >Customer has more than 30 service records. Please check customer details in BOM.</font></td>
								</tr>
								<tr>
									<td><font face='Helvetica' >For new SB order creation, please proceed.</font></td>
								</tr>
								</c:if>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>

		</c:if>
		<c:if
			test='${!empty custSearchResultList || !empty serviceLineDTOList || !empty custOrderHistoryList}'>

					<%-- ***************************   BASIC CUSTOMER INFORMATION   ****************************** --%>
					<table width="100%" class="tablegrey">
						<tr>
							<td bgcolor="#FFFFFF"><%--content--%>
							<table style="width: 100%; margin: 0; border: none">
								<tr>
									<td class="table_title">Basic Customer Information</td>
								</tr>
							</table>
							</td>
						</tr>
						<c:choose>
						<c:when test="${empty custSearchResultList}">
						<tr>
							<td><font face='Helvetica' >No Record Found.</font></td>
						</tr>
						</c:when>
						<c:otherwise>
						<tr>
							<td>
							<table class="contenttext" style="width: 100%; background-color: #FFFFFF; padding: 0">
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr class="contenttextgreen">
									<td class="table_title">LOB</td>
									<td class="table_title">ID Doc Type</td>
									<td class="table_title">ID Doc Num</td>
									<td class="table_title">Premier Customer</td>
									<td class="table_title">Title</td>
									<td class="table_title">Family Name</td>
									<td class="table_title">Given Name</td>
									<td class="table_title">Company Name</td>
									<td class="table_title">Customer Blacklist</td>
									<td class="table_title">ID Verify</td>
								</tr>

									<c:forEach items="${custSearchResultList}" var="basicCustInfo" varStatus="status">
										<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 10}"> style="display:none"</c:if>>
											<c:if test='${basicCustInfo.systemId == "DRG"}'>
												<td>LTS&nbsp;</td>
											</c:if>
											<c:if test='${!(basicCustInfo.systemId == "DRG")}'>
												<td>${basicCustInfo.systemId}&nbsp;</td>
											</c:if>
											<td>
												<c:choose>
													<c:when test='${basicCustInfo.idDocType == "HKID"}'><spring:message code="idDocType.HKID" text="" /></c:when>
													<c:when test='${basicCustInfo.idDocType == "BS"}'><spring:message code="idDocType.BS" text="" /></c:when>
													<c:when test='${basicCustInfo.idDocType == "PASS"}'><spring:message code="idDocType.PASS" text="" /></c:when>
													<c:when test='${basicCustInfo.idDocType == "CERT"}'><spring:message code="idDocType.CERT" text="" /></c:when>
													<c:otherwise>${basicCustInfo.idDocType}</c:otherwise>
												</c:choose>
												&nbsp;
											 </td>
											
											<td>${basicCustInfo.idDocNum}&nbsp;</td>
											
											<td>
											<c:choose>
											<c:when test="${basicCustInfo.custTagDTO == null}">
												N/A
											</c:when>
											<c:when test="${basicCustInfo.custTagDTO.premierInd == 'Y'}">
												<span class="custPremier"><c:out value="${basicCustInfo.custTagDTO.premierCustDesc}"/></span>
											</c:when>
											<c:otherwise>
												N
											</c:otherwise>
											</c:choose>
											</td>

											<td>${basicCustInfo.title}&nbsp;</td>

											<td>${basicCustInfo.lastName}&nbsp;</td>

											<td>${basicCustInfo.firstName}&nbsp;</td>

											<td>${basicCustInfo.companyName}&nbsp;</td>

											<td>${basicCustInfo.blackListInd}&nbsp;</td>

											<td>${basicCustInfo.idVerifyInd}&nbsp;</td>
										</tr>
									</c:forEach>
							</table>
							</td>
						</tr>
						</c:otherwise>
						</c:choose>
					</table>
					
					<br>
					
					<%-- *******************************   SERVICE IN USE   *************************************** --%>
					<table width="100%" class="tablegrey">
						<tr>
							<td bgcolor="#FFFFFF"><%--content--%>
							<table width="100%" rules="none">
								<tr>
									<td class="table_title">Service Line Information</td>
								</tr>
							</table>
							</td>
						</tr>
						<c:if test='${!empty serviceLineDTOList}'>
							<tr>
								<td>
								<table class="contenttext" style="width: 100%; background-color: #FFFFFF; padding: 0">

									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr class="contenttextgreen">

										<td class="table_title">&nbsp;</td>
										<td class="table_title">LOB</td>
										<td class="table_title">Service Number</td>
										<td class="table_title">Service Start Date</td>
										<td class="table_title">Service End Date</td>
										<td class="table_title">Installation/ Registration
										Address</td>
										<td class="table_title">Eye Group Number</td>
										<td class="table_title">Eye Group Type</td>

										<c:forEach items="${serviceLineDTOList}" var="servInUse" varStatus="counter">
											<tr<c:if test="${counter.index % 2 == 0}"> class="even"</c:if>>
												
												<td>
													<c:if test='${servInUse.systemId == "DRG"}'>
														<form:radiobutton path="selectedNum" value="${servInUse.serviceNum}" />  
													</c:if>
													&nbsp;
												</td>
												
												
												<c:if test='${servInUse.serviceEndDate == "--"}'>
													<c:if test='${servInUse.systemId == "DRG"}'>
														<td>LTS&nbsp;</td>
													</c:if>
													<c:if test='${!(servInUse.systemId == "DRG")}'>
														<td>${servInUse.systemId}&nbsp;</td>
													</c:if>
												</c:if>
												<c:if test='${!(servInUse.serviceEndDate == "--")}'>
													<c:if test='${servInUse.systemId == "DRG"}'>
														<td>
															<font color="#FF0000">
																LTS&nbsp;
															</font>
														</td>
													</c:if>
													<c:if test='${!(servInUse.systemId == "DRG")}'>
														<td>
															<font color="#FF0000">
																${servInUse.systemId}&nbsp;
															</font>
														</td>
													</c:if>
												</c:if>

												<c:if test='${servInUse.serviceEndDate == "--"}'>
													<c:if test='${servInUse.systemId == "MOB"}'>
														<td>MRT: ${servInUse.serviceNum}&nbsp;</td>
													</c:if>
													<c:if test='${servInUse.systemId == "IMS"}'>
														<td>FSA: ${servInUse.serviceNum}&nbsp;</td>
													</c:if>
													<c:if test='${servInUse.systemId == "DRG"}'>
														<td>TEL: ${servInUse.serviceNum}&nbsp;</td>
													</c:if>
												</c:if>
												<c:if test='${!(servInUse.serviceEndDate == "--")}'>
													<c:if test='${servInUse.systemId == "MOB"}'>
														<td>
															<font color="#FF0000">
																MRT: ${servInUse.serviceNum}&nbsp;
															</font>
														</td>
													</c:if>
													<c:if test='${servInUse.systemId == "IMS"}'>
														<td>
															<font color="#FF0000">
																FSA: ${servInUse.serviceNum}&nbsp;
															</font>
														</td>
													</c:if>
													<c:if test='${servInUse.systemId == "DRG"}'>
														<td>
															<font color="#FF0000">
																TEL: ${servInUse.serviceNum}&nbsp;
															</font>
														</td>
													</c:if>
												</c:if>

												<c:if test='${servInUse.serviceEndDate == "--"}'>
													<td>${servInUse.serviceStartDate}&nbsp;</td>
												</c:if>
												<c:if test='${!(servInUse.serviceEndDate == "--")}'>
													<td>
														<font color="#FF0000">
															${servInUse.serviceStartDate}&nbsp;
														</font>
													</td>
												</c:if>

												<c:if test='${servInUse.serviceEndDate == "--"}'>
													<td>${servInUse.serviceEndDate}&nbsp;</td>
												</c:if>
												<c:if test='${!(servInUse.serviceEndDate == "--")}'>
													<td>
														<font color="#FF0000">
															${servInUse.serviceEndDate}&nbsp;
														</font>
													</td>
												</c:if>

												<c:if test='${servInUse.serviceEndDate == "--"}'>
													<td>${servInUse.validationRulesID}&nbsp;</td>
												</c:if>
												<c:if test='${!(servInUse.serviceEndDate == "--")}'>
													<td>
														<font color="#FF0000">
															${servInUse.validationRulesID}&nbsp;
														</font>
													</td>
												</c:if>

												<c:if test='${servInUse.serviceEndDate == "--"}'>
													<td>${servInUse.eyeGroupNum}&nbsp;</td>
												</c:if>
												<c:if test='${!(servInUse.serviceEndDate == "--")}'>
													<td>
														<font color="#FF0000">
															${servInUse.eyeGroupNum}&nbsp;
														</font>
													</td>
												</c:if>
												
												<c:if test='${servInUse.serviceEndDate == "--"}'>
													<td>${servInUse.eyeGroupType}&nbsp;</td>
												</c:if>
												<c:if test='${!(servInUse.serviceEndDate == "--")}'>
													<td>
														<font color="#FF0000">
															${servInUse.eyeGroupType}&nbsp;
														</font>
													</td>
												</c:if>
												
											</tr>
										</c:forEach>
									</tr>

								</table>
								</td>
							</tr>
						</c:if>
						<c:if test='${empty serviceLineDTOList}'>
							<c:if test='${errorSeverity == "1"}'>
								<tr>
									<td><font face='Helvetica' >Customer has more than 30 service records. Please check customer details in BOM.</font></td>
								</tr>
								<tr>
									<td><font face='Helvetica' >For new SB order creation, please proceed.</font></td>
								</tr>
							</c:if>
							<c:if test='${!(errorSeverity == "1")}'>
								<tr>
									<td><font face='Helvetica' >No Record Found.</font></td>
								</tr>
							</c:if>
						</c:if>
						
					</table>
					
					<br>
					
					<%-- *******************************   ORDER HISTORY   *************************************** --%>
					<table width="100%" class="tablegrey">
						<tr>
							<td bgcolor="#FFFFFF"><%--content--%>
							<table width="100%" border="0" cellspacing="1" rules="none">
								<tr>
									<td class="table_title">SB Order History</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<div class="overflowDiv sbOrderHistDiv">
					<table width="100%" class="tablegrey">
						<c:if test='${!empty custOrderHistoryList}'>
							<tr>
								<td>
								<table class="contenttext" style="width: 100%; background-color: #FFFFFF; padding: 0">

									<tr class="contenttextgreen">

										<td class="table_title">Order ID</td>
										<td class="table_title">OCID</td>
										<td class="table_title">LOB</td>
										<td class="table_title">Customer Name</td>
										<td class="table_title">Service Num</td>
										<td class="table_title">IMS Login id</td>
										<td class="table_title">Application Date</td>
										<td class="table_title">Order Status</td>
										<td class="table_title">Error Message</td>

									</tr>

									<c:forEach items="${custOrderHistoryList}" var="orderHist" varStatus="status">
										<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if>>
											<td>
												<c:choose>
													<c:when test='${orderHist.lob == "MOB"}'>
														<c:choose>
															<c:when test='${fn:length(orderHist.orderId) == 11}'>
																<c:choose>
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "R"}'>
																		<a href="orderdetail.html?orderId=${orderHist.orderId}"
																			title="Order Enquiry for MOB (Retail)">${orderHist.orderId}&nbsp;
																		</a>
																	</c:when>
																	<c:when test="${fn:startsWith(orderHist.orderId, 'CSBS') }">
																		<a href="sbsorderdetail.html?orderId=${orderHist.orderId}"	title="Order Enquiry for MOB">${orderHist.orderId}&nbsp;</a>
																	</c:when>
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "P"}'>
																		<a href="orderdetail.html?orderId=${orderHist.orderId}"
																			title="Order Enquiry for MOB (Premier)">${orderHist.orderId}&nbsp;
																		</a>
																	</c:when>
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "C"}'>
																		<a href="mobccspreview.html?orderId=${orderHist.orderId}&action=ENQUIRY"
																			title="Order Enquiry for MOB (CCS)">${orderHist.orderId}&nbsp;
																		</a>
																	</c:when>
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "D"}'>
																		<a href="orderdetail.html?orderId=${orderHist.orderId}&action=ENQUIRY"
																			title="Order Enquiry for MOB (DS)">${orderHist.orderId}&nbsp;
																		</a>
																	</c:when>
																	<c:otherwise>
																		<a href="orderdetail.html?orderId=${orderHist.orderId}"
																			title="Order Enquiry for MOB (Retail)">${orderHist.orderId}&nbsp;
																		</a>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:if test='${fn:substring(orderHist.orderId, 0, 1) == "R"}'>
																<a href="orderdetail.html?orderId=${orderHist.orderId}"
																	title="Order Enquiry for MOB (Retail)">${orderHist.orderId}&nbsp;
																</a>
																</c:if>
																<c:if test='${fn:substring(orderHist.orderId, 0, 1) == "P"}'>
																<a href="orderdetail.html?orderId=${orderHist.orderId}"
																	title="Order Enquiry for MOB (Premier)">${orderHist.orderId}&nbsp;
																</a>
																</c:if>
																<c:if test='${fn:substring(orderHist.orderId, 0, 1) == "D"}'>
																		<a href="orderdetail.html?orderId=${orderHist.orderId}&action=ENQUIRY"
																			title="Order Enquiry for MOB (DS)">${orderHist.orderId}&nbsp;
																		</a>
																</c:if>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test='${orderHist.lob == "LTS"}'>
														<a href="ltsupgradeeyeorder.html?sbuid=${sessionScope.sbuid}&action=ENQUIRY&orderId=${orderHist.orderId}"
															title="Order Enquiry for LTS">${orderHist.orderId}&nbsp;
														</a>
													</c:when>
													<c:when test='${orderHist.lob == "IMS"}'>
														<a href="imsorderdetail.html?orderId=${orderHist.orderId}"
															title="Order Enquiry for IMS">${orderHist.orderId}&nbsp;
														</a>
													</c:when>
													<c:otherwise>
														${orderHist.orderId}&nbsp;
													</c:otherwise>
												</c:choose>
											</td>

											<td>${orderHist.ocid}&nbsp;</td>

											<td>${orderHist.lob}&nbsp;</td>
											
											<td>${orderHist.orderHistCustName}&nbsp;</td>

											<c:if test='${orderHist.lob == "MOB" && !(empty orderHist.serviceNum)}'>
												<td>MRT: ${orderHist.serviceNum}&nbsp;</td>
											</c:if>
											<c:if test='${orderHist.lob == "IMS" && !(empty orderHist.serviceNum)}'>
												<td>FSA: ${orderHist.serviceNum}&nbsp;</td>
											</c:if>
											<c:if test='${orderHist.lob == "LTS" && !(empty orderHist.serviceNum)}'>
												<c:choose>
													<c:when test="${fn:length(orderHist.serviceNum) == 12}">
														<td>${fn:substring(orderHist.serviceNum, 4, 12)}&nbsp;</td>
													</c:when>
													<c:otherwise>
														<td>${orderHist.serviceNum}&nbsp;</td>	
													</c:otherwise>
												</c:choose>
											</c:if>
											
											
											<c:if test='${empty orderHist.serviceNum || orderHist.lob == null}'>
												<td>&nbsp;</td>
											</c:if>

											<td>${orderHist.imsLoginId}&nbsp;</td>
	
											<td>
												<c:choose>
													<c:when test='${orderHist.lob == "IMS" }'>
														<fmt:formatDate pattern="dd/MM/yyyy"
															value="${orderHist.appDate}" />
													</c:when>
													<c:otherwise>
														<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"
															value="${orderHist.appDate}" />
													</c:otherwise>											
												</c:choose>
												&nbsp;
											</td>
											
											<td>
												<c:choose>
													<c:when test='${orderHist.lob == "MOB"}'>
														<c:choose>
															<c:when test='${fn:length(orderHist.orderId) == 11}'>
																<c:choose>
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "R" || fn:substring(orderHist.orderId, 0, 1) == "P"}'>
																		<c:choose>
																			<c:when test='${orderHist.orderStatus == "INITIAL"}'>
																				<spring:message code="orderStatus.INITIAL" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "SIGNOFF"}'>
																				<spring:message code="orderStatus.SIGNOFF" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "PENDING"}'>
																				<spring:message code="orderStatus.PENDING" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "PROCESS"}'>
																				<spring:message code="orderStatus.PROCESS" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "SUCCESS"}'>
																				<spring:message code="orderStatus.SUCCESS" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "FAILED"}'>
																				<spring:message code="orderStatus.FAILED" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "VOID"}'>
																				<spring:message code="orderStatus.VOID" text="" />
																			</c:when>
																			<c:otherwise>
																				${orderHist.orderStatus}
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "C"}'>
																		<c:choose>
																			<c:when test='${orderHist.orderStatus == "99"}'>
																				<my:code id="${orderHist.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "01"}'>
																				<my:code id="${orderHist.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "02"}'>
																				Completed
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "03"}'>
																				Cancelling
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "04"}'>
																				Cancelled
																			</c:when>
																			<c:otherwise>
																				${orderHist.orderStatus}
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
                                                                        <c:choose>
                                                                            <c:when test='${orderHist.orderStatus == "INITIAL"}'>
                                                                                <spring:message code="orderStatus.INITIAL" text="" />
                                                                            </c:when>
                                                                            <c:when test='${orderHist.orderStatus == "SIGNOFF"}'>
                                                                                <spring:message code="orderStatus.SIGNOFF" text="" />
                                                                            </c:when>
                                                                            <c:when test='${orderHist.orderStatus == "PENDING"}'>
                                                                                <spring:message code="orderStatus.PENDING" text="" />
                                                                            </c:when>
                                                                            <c:when test='${orderHist.orderStatus == "PROCESS"}'>
                                                                                <spring:message code="orderStatus.PROCESS" text="" />
                                                                            </c:when>
                                                                            <c:when test='${orderHist.orderStatus == "SUCCESS"}'>
                                                                                <spring:message code="orderStatus.SUCCESS" text="" />
                                                                            </c:when>
                                                                            <c:when test='${orderHist.orderStatus == "FAILED"}'>
                                                                                <spring:message code="orderStatus.FAILED" text="" />
                                                                            </c:when>
                                                                            <c:when test='${orderHist.orderStatus == "VOID"}'>
                                                                                <spring:message code="orderStatus.VOID" text="" />
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                ${orderHist.orderStatus}
                                                                            </c:otherwise>
                                                                        </c:choose>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test='${orderHist.orderStatus == "INITIAL"}'>
																		<spring:message code="orderStatus.INITIAL" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "SIGNOFF"}'>
																		<spring:message code="orderStatus.SIGNOFF" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "PENDING"}'>
																		<spring:message code="orderStatus.PENDING" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "PROCESS"}'>
																		<spring:message code="orderStatus.PROCESS" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "SUCCESS"}'>
																		<spring:message code="orderStatus.SUCCESS" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "FAILED"}'>
																		<spring:message code="orderStatus.FAILED" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "VOID"}'>
																		<spring:message code="orderStatus.VOID" text="" />
																	</c:when>
																	<c:otherwise>
																		${orderHist.orderStatus}
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test='${orderHist.lob == "IMS"}'>
														<spring:message code="ims.orderStatus.${orderHist.orderStatus}" text="${orderHist.orderStatus}" />
													</c:when>
													<c:when test='${orderHist.lob == "LTS"}'>
														<c:choose>
															<c:when test='${orderHist.orderStatus == "S" && orderHist.errMsg != null}'>
																<spring:message code="lts.orderStatus.${orderHist.orderStatus}.${orderHist.errMsg}" text="${orderHist.errMsg}" />
															</c:when>
															<c:otherwise>
																<spring:message code="lts.orderStatus.${orderHist.orderStatus}" text="${orderHist.orderStatus}" />
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														${orderHist.orderStatus}&nbsp;
													</c:otherwise>
												</c:choose>
											</td>

											<td>									
												<c:choose>
													<c:when test='${orderHist.lob == "IMS" || orderHist.lob == "LTS"}'>N/A</c:when>										
													<c:otherwise>${orderHist.errMsg}</c:otherwise>
												</c:choose>
												&nbsp;
											</td>

										</tr>

									</c:forEach>


								</table>
								</td>


							</tr>
						</c:if>
						<c:if test='${empty custOrderHistoryList}'>
							<tr>
								<td><font face='Helvetica' >No Record Found.</font></td>
							</tr>
						</c:if>
					</table>
					</div>
<%--					</td>--%>
<%--				</tr>--%>
<%--			</table>--%>
		</c:if>
		</c:when>
		<c:otherwise>
			<table width="100%" class="tablegrey">
				<tr>
					<td bgcolor="#FFFFFF"><%--content--%>
					<table width="100%" border="0" cellspacing="1" rules="none">
						<tr>
							<td class="table_title">Customer Search Result</td>
						</tr>
					</table>
					<table width="100%" class="tablegrey">
						<tr>
							<td bgcolor="#FFFFFF"><%--content--%>
							<table width="100%" border="0" cellspacing="1" rules="none">
								<tr>
									<td>
										<font color="#FF0000" face='Helvetica' >
											Customer Search Service is unavailable from BOM.
										</font>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					
					<br>
					
					<%-- *******************************   ORDER HISTORY   *************************************** --%>
					<table width="100%" class="tablegrey">
						<tr>
							<td bgcolor="#FFFFFF"><%--content--%>
							<table width="100%" border="0" cellspacing="1" rules="none">
								<tr>
									<td class="table_title">SB Order History</td>
								</tr>
							</table>
							</td>
						</tr>
						<c:if test='${!empty custOrderHistoryList}'>
							<tr>
								<td>
								<table class="contenttext" style="width: 100%; background-color: #FFFFFF; padding: 0">

									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									
									<tr class="contenttextgreen">

										<td class="table_title">Order ID</td>
										<td class="table_title">OCID</td>
										<td class="table_title">LOB</td>
										<td class="table_title">Customer Name</td>
										<td class="table_title">Service Num</td>
										<td class="table_title">IMS Login id</td>
										<td class="table_title">Application Date</td>
										<td class="table_title">Order Status</td>
										<td class="table_title">Error Message</td>

									</tr>

									<c:forEach items="${custOrderHistoryList}" var="orderHist">
										<tr>
											<td>
												<c:if test='${orderHist.lob == "MOB"}'>
													<c:choose>
														<c:when test="${fn:startsWith(orderHist.orderId, 'CSBS') }">
															<a href="sbsorderdetail.html?orderId=${orderHist.orderId}"	title="Order Enquiry for MOB">${orderHist.orderId}&nbsp;</a>
														</c:when>
														<c:otherwise>
															<a href="orderdetail.html?orderId=${orderHist.orderId}"
																title="Order Enquiry for MOB">${orderHist.orderId}&nbsp;
															</a>
														</c:otherwise>
													</c:choose>

												</c:if>
												<c:if test='${orderHist.lob == "LTS"}'>
													<a href="ltsupgradeeyeorder.html?sbuid=${sessionScope.sbuid}&action=ENQUIRY&orderId=${orderHist.orderId}"
														title="Order Enquiry for LTS">${orderHist.orderId}&nbsp;
													</a>
												</c:if>
												<c:if test='${orderHist.lob == "IMS"}'>
													<a href="imsorderdetail.html?orderId=${orderHist.orderId}"
														title="Order Enquiry for IMS">${orderHist.orderId}&nbsp;
													</a>
												</c:if>
												<c:if test='${orderHist.lob == null}'>
													${orderHist.orderId}&nbsp;
												</c:if>
											</td>

											<td>${orderHist.ocid}&nbsp;</td>

											<td>${orderHist.lob}&nbsp;</td>
											
											<td>${orderHist.orderHistCustName}&nbsp;</td>

											<c:if test='${orderHist.lob == "MOB" && !(empty orderHist.serviceNum)}'>
												<td>MRT: ${orderHist.serviceNum}&nbsp;</td>
											</c:if>
											<c:if test='${orderHist.lob == "IMS" && !(empty orderHist.serviceNum)}'>
												<td>FSA: ${orderHist.serviceNum}&nbsp;</td>
											</c:if>
											<c:if test='${orderHist.lob == "LTS" && !(empty orderHist.serviceNum)}'>
												<c:choose>
													<c:when test="${fn:length(orderHist.serviceNum) == 12}">
														<td>${fn:substring(orderHist.serviceNum, 4, 12)}&nbsp;</td>
													</c:when>
													<c:otherwise>
														<td>${orderHist.serviceNum}&nbsp;</td>	
													</c:otherwise>
												</c:choose>
											</c:if>
											
											
											<c:if test='${empty orderHist.serviceNum || orderHist.lob == null}'>
												<td>&nbsp;</td>
											</c:if>

											<td>${orderHist.imsLoginId}&nbsp;</td>
	
											<td>
												<c:choose>
													<c:when test='${orderHist.lob == "IMS" }'>
														<fmt:formatDate pattern="dd/MM/yyyy"
															value="${orderHist.appDate}" />
													</c:when>
													<c:otherwise>
														<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"
															value="${orderHist.appDate}" />
													</c:otherwise>												
												</c:choose>
												&nbsp;
											</td>
											
											<td>
												<c:if test='${orderHist.lob == "MOB"}'>
													<c:choose>
														<c:when test='${orderHist.orderStatus == "INITIAL"}'>
															<spring:message code="orderStatus.INITIAL" text="" />
														</c:when>
														<c:when test='${orderHist.orderStatus == "SIGNOFF"}'>
															<spring:message code="orderStatus.SIGNOFF" text="" />
														</c:when>
														<c:when test='${orderHist.orderStatus == "PENDING"}'>
															<spring:message code="orderStatus.PENDING" text="" />
														</c:when>
														<c:when test='${orderHist.orderStatus == "PROCESS"}'>
															<spring:message code="orderStatus.PROCESS" text="" />
														</c:when>
														<c:when test='${orderHist.orderStatus == "SUCCESS"}'>
															<spring:message code="orderStatus.SUCCESS" text="" />
														</c:when>
														<c:when test='${orderHist.orderStatus == "FAILED"}'>
															<spring:message code="orderStatus.FAILED" text="" />
														</c:when>
														<c:when test='${orderHist.orderStatus == "VOID"}'>
															<spring:message code="orderStatus.VOID" text="" />
														</c:when>
														<c:otherwise>
															${orderHist.orderStatus}
														</c:otherwise>
													</c:choose>
												</c:if>
												<c:if test='${orderHist.lob == "IMS"}'>
													<spring:message code="ims.orderStatus.${orderHist.orderStatus}" text="${orderHist.orderStatus}" />
												</c:if>
												<c:if test='${orderHist.lob == "LTS"}'>
													<c:choose>
														<c:when test='${orderHist.orderStatus == "S" && orderHist.errMsg != null}'>
															<spring:message code="lts.orderStatus.${orderHist.orderStatus}.${orderHist.errMsg}" text="${orderHist.errMsg}" />
														</c:when>
														<c:otherwise>
															<spring:message code="lts.orderStatus.${orderHist.orderStatus}" text="${orderHist.orderStatus}" />
														</c:otherwise>
													</c:choose>
												</c:if>
												&nbsp;
											</td>

											<td>									
												<c:choose>
													<c:when test='${orderHist.lob == "IMS" || orderHist.lob == "LTS"}'>N/A</c:when>										
													<c:otherwise>${orderHist.errMsg}</c:otherwise>
												</c:choose>
												&nbsp;
											</td>

										</tr>

									</c:forEach>


								</table>
								</td>


							</tr>
						</c:if>
						
						
						<c:if test='${empty custOrderHistoryList}'>
							<tr>
								<td><font face='Helvetica' >No Record Found.</font></td>
							</tr>
						</c:if>
					</table>
					</td>
				</tr>
			</table>
		</c:otherwise>
		</c:choose>
	</c:if>
	
	<br>
	<br>
	
	<%-- ********************************   BOTTOM BUTTONS   ************************************* --%>
	<table width="100%" border="0" cellspacing="0">
		<tr>
			<c:if test="${bomsalesuser.pilotStatus == 'A'}">
			<td align="center" style="padding: 10px 0">
				<div id="PCD">
					<a href="imsorderdetail.html" class="nextbutton">
						PCD Acquisition
					</a>
				</div>
				<div id="PCD2" style="display: block;">
					<a href="#" class="nextbutton" name="asignoff3" onclick="printErrMsg()">
						PCD Acquisition
					</a>
				</div>
			</td>
			</c:if>
			
			<c:if test="${bomsalesuser.ltsPilotStatus == 'A'}">			
				<td align="center" style="padding: 10px 0">
					<div>
						<a id="createEyeUpgradeOrderBtn" href="#" class="nextbutton">
							LTS Acquisition
						</a>
					</div>
					<div id="createEyeUpgradeOrderBtn2" style="display: block;">
						<a href="#" class="nextbutton" name="asignoff3" onclick="printLtsErrMsg()">
							LTS Acquisition
						</a>
					</div>
				</td>	
			</c:if>			
		</tr>
	</table>

	<form:hidden path="imsLtsButtonInd" />
	<form:hidden path="ltsButtonInd" />
	<input type="hidden" name="actionType" id="actionType" />
</form:form>


<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>