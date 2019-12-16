<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<style type="text/css">
.sbOrderHistDiv { height:200px; overflow-x:auto; overflow-y:auto; display: inline-block; width: 100% }
</style>

<script language="javascript">
	document.onkeypress = processKey;

	function processKey(e) {
		if (null == e)
			e = window.event;
		if (e.keyCode == 13) {
			custInfoSearch();
		}
	}
	
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

	$(document).ready(
		function() {
			$('a#createEyeUpgradeOrderBtn')
					.click(
							function(event) {
								alert("<Sales> Remind Customer that his/her PCCW info of Fixed Line / Netvigator / NOW TV will be viewed during the process");
								document.customerInformationForm.actionType.value = "CREATELTS";
								document.customerInformationForm.submit();
							});
			
			// add by Joyce 20111118
			// control PCD & eyex button display
			$("#PCD").show();
			$("#PCD2").hide();
			$("#createEyeUpgradeOrderBtn").show();
			$("#createEyeUpgradeOrderBtn2").hide();
			
			if ($("#imsLtsButtonInd").val() == 'Y') {
				$("#PCD").hide();
				$("#PCD2").show();
				$("#createEyeUpgradeOrderBtn").hide();
				$("#createEyeUpgradeOrderBtn2").show();
			}
		});

	function hidePCD() {
		if (document.customerInformationForm.idDocType.options[document.customerInformationForm.idDocType.selectedIndex].value == "BR") {
			$("#PCD").hide();
			$("#PCD2").show();
			$("#createEyeUpgradeOrderBtn").hide();
			$("#createEyeUpgradeOrderBtn2").show();
		} else if (document.customerInformationForm.idDocType.options[document.customerInformationForm.idDocType.selectedIndex].value == "Certificate No") {
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

	function clickIdDocType() {
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";
		document.customerInformationForm.emailAddress.value = "";
		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
	}

	function clickIdDocNum() {
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";
		document.customerInformationForm.emailAddress.value = "";
		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
		//		document.customerInformationForm.idDocNum.select();
	}

	function clickServNumType() {
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";
		document.customerInformationForm.emailAddress.value = "";
		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
	}

	function clickServNum() {
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";
		document.customerInformationForm.emailAddress.value = "";
		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
	}

	function clickLoginIdPrefix() {
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";
		document.customerInformationForm.emailAddress.value = "";
		//		document.customerInformationForm.actionType.value = "CLEAR";
		//		document.customerInformationForm.submit();
	}

	function clickLoginIdSuffix() {
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";
		document.customerInformationForm.emailAddress.value = "";
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
		document.customerInformationForm.emailAddress.value = "";
		document.customerInformationForm.submit();
	}
	function clickEmailAddress(){
		document.customerInformationForm.actionType.value = "CLEAR";
		document.customerInformationForm.idDocType.value = "NONE";
		document.customerInformationForm.idDocNum.value = "";
		document.customerInformationForm.serviceNumberType.value = "NONE";
		document.customerInformationForm.serviceNumber.value = "";
		document.customerInformationForm.loginIdPrefix.value = "";
		document.customerInformationForm.loginIdSuffix.value = "NONE";
	}
	function viewForm(orderId){
		//alert(orderId);
		window.location.replace('imsreport.html?OrderIdForViewPDF='+orderId, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
	}
</script>

<form:form name="customerInformationForm" method="POST"
	commandName="customerInformation">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><%--content--%>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td class="table_title">Searching Key</td>
				</tr>
			</table>
			<%-- ********************   SEARCHING KEY --- ROW 1 (ID DOC + search)   ***************************** --%>
			<table width="100%" class="contenttext">
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="1">
						<div>
							ID Doc Type: 
							<form:select path="idDocType" onchange="hidePCD()" onclick="clickIdDocType()">
								<form:option value="NONE" label="Select" />
								<form:options items="${idDocTypeList}" />
							</form:select>
							ID Doc Num: 
							<form:input path="idDocNum" maxlength="20" onclick="clickIdDocNum()" />
						</div>
					</td>
					<td colspan="1">
						<div>Contact Email Address: 
							<form:input onclick="clickEmailAddress()" path="emailAddress" /> 
						</div>
					</td>
				</tr>

				<%-- ***************   SEARCHING KEY --- ROW 2 (SERVICE NUM + login id + clear)   ***************************** --%>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="1">
						<div>Service Number: 
							<form:select path="serviceNumberType" onclick="clickServNumType()">
								<form:option value="NONE" label="Select" />
								<form:options items="${serviceNumberTypeList}" />
							</form:select>
							<form:input path="serviceNumber" maxlength="20" onclick="clickServNum()" />
						</div>
					</td>
					<td colspan="1">
						<div>Login ID: 
							<form:input onclick="clickLoginIdPrefix()" path="loginIdPrefix" /> 
							<form:select path="loginIdSuffix" onclick="clickLoginIdSuffix()">
<%-- 								<form:option value="NONE" label="Select" /> --%>
								<form:options items="${loginIdSuffixList}" />
							</form:select>
						</div>
					</td>
					
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr><td align="left">
					<form:radiobutton path="orderType" value="TV_I" id="TV_I" cssStyle="border:none"/>
					<label for="searchSQL">IMS Now TV</label>
					<form:radiobutton path="orderType" value="PCD" id="PCD" cssStyle="border:none"/>
					<label for="searchSQL">IMS PCD</label>
        		</td></tr>
				<tr>
					<td colspan="2">
						<form:errors path="customerInformationError" cssClass="error" /> 
						<form:errors path="idDocType" cssClass="error" /> 
						<form:errors path="idDocNum" cssClass="error" />
						<form:errors path="serviceNumberType" cssClass="error" /> 
						<form:errors path="serviceNumber" cssClass="error" /> 
						<form:errors path="loginIdSuffix" cssClass="error" /> 
						<form:errors path="loginIdPrefix" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td colspan="1">&nbsp;</td>
					<td colspan="1" align="right">
						<div>
							<a href="#" class="nextbutton" onClick="javascript:custInfoSearch();"> Search </a>
							<a href="#" class="nextbutton" onClick="javascript:custInfoClear();"> Clear </a>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="1">&nbsp;</td>
					<td colspan="1">&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>

	</table>
	
	<br>

	<c:if test='${actionType == "REFRESH"}'>
	<c:choose>
	<c:when test='${!(errorSeverity == "3")}'>
		<c:if test='${!empty custOrderHistoryList}'>

					<%-- *******************************   ORDER HISTORY   *************************************** --%>
					<div class="sbOrderHistDiv" style="height:400px;">
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

								<table width="100%" 
									class="contenttext" bgcolor="#FFFFFF">

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
										<td class="table_title">Contact Email Address</td>										
										<td class="table_title">Email Sent</td>
										<td class="table_title">Error Message</td>
										<td class="table_title">AF</td>

									</tr>

									<c:forEach items="${custOrderHistoryList}" var="orderHist">
										<tr>
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
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "C"}'>
																		<a href="mobccspreview.html?orderId=${orderHist.orderId}&action=ENQUIRY"
																			title="Order Enquiry for MOB (CCS)">${orderHist.orderId}&nbsp;
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
																<a href="orderdetail.html?orderId=${orderHist.orderId}"
																	title="Order Enquiry for MOB (Retail)">${orderHist.orderId}&nbsp;
																</a>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test='${orderHist.lob == "LTS"}'>
														<a href="ltsupgradeeyeorder.html?sbuid=${sessionScope.sbuid}&action=ENQUIRY&orderId=${orderHist.orderId}"
															title="Order Enquiry for LTS">${orderHist.orderId}&nbsp;
														</a>
													</c:when>
													<c:when test='${orderHist.lob == "IMS"}'>
														<c:choose>
															<c:when test='${orderHist.emailSent == "Y"  }'>
															<a href="imsorderdetail.html?orderId=${orderHist.orderId}&imsOrderEnquiry=Y"
																title="Order Enquiry for IMS">${orderHist.orderId}&nbsp;
															</a>
															</c:when>
															<c:otherwise>  
															${orderHist.orderId}
															</c:otherwise>
														</c:choose>
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
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "R"}'>
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
														<spring:message code="ims.orderStatus.${orderHist.orderStatus}" text="${orderHist.orderStatus}" />${orderHist.suspendReason}
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

											<td >									
												${orderHist.emailAddress}
											</td>
																						
											<td style="text-align: center;">									
												${orderHist.emailSent}
											</td>
											
											<td>									
												<c:choose>
													<c:when test='${orderHist.lob == "IMS" || orderHist.lob == "LTS"}'>N/A</c:when>										
													<c:otherwise>${orderHist.errMsg}</c:otherwise>
												</c:choose>
												&nbsp;
											</td>
											
											<td>									
												<c:choose>
													<c:when test='${orderHist.lob == "IMS"}'>
<%-- 													<a href="#" class="nextbutton2" style="padding:4px;position:relative;left:2px;top:2px;" onClick="javascript:viewForm('${orderHist.orderId}');">View</a> --%>
													<a href="#" class="nextbutton" style="padding:4px;left:2px;top:2px;" onClick="javascript:viewForm('${orderHist.orderId}');">View</a>
													</c:when>										
													<c:otherwise>Not IMS</c:otherwise>
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
								<table width="100%" border="1" cellspacing="0"
									class="contenttext" bgcolor="#FFFFFF">

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
													<a href="orderdetail.html?orderId=${orderHist.orderId}"
														title="Order Enquiry for MOB">${orderHist.orderId}&nbsp;
													</a>
												</c:if>
												<c:if test='${orderHist.lob == "LTS"}'>
													<a href="ltsupgradeeyeorder.html?sbuid=${sessionScope.sbuid}&action=ENQUIRY&orderId=${orderHist.orderId}"
														title="Order Enquiry for LTS">${orderHist.orderId}&nbsp;
													</a>
												</c:if> 
												<c:if test='${orderHist.lob == "IMS"}'>
													<a href="imsorderdetail.html?orderId=${orderHist.orderId}&imsOrderEnquiry=Y"
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
	<form:hidden path="imsLtsButtonInd" />
	<input type="hidden" name="actionType" id="actionType" />
</form:form>


<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>