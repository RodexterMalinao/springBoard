<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script> 
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.inputPagination.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script> 

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script> 

<style type="text/css">
.sbOrderHistDiv { overflow-y:auto; display: inline-block; width: 100% }
.searchRow { padding: 15px 10px }
.label { display: inline-block; width: 105px; font-size: 12px; font-family: "Verdana", "Helvetica", "Arial", "sans-serif" }
.labelFirst { width: 110px }
.input { display: inline-block; width: 120px }
#familyName, #givenName,#companyName {width: 250px} 
.searchColHalf { overflow: hidden; width: 48%; display: inline-block; float: left  }
.even { background-color: #E8FFE8 }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px; font-family: "Helvetica", "Arial", "sans-serif" }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078; padding: 5px 10px; position: relative } 
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
		
			$('a#ltsAcqOrderBtn')
			.click(
					function(event) {
						if("${sessionScope.bomsalesuser.channelId}" == 1){
							alert("<Sales> Remind Customer that his/her PCCW info of Fixed Line / Netvigator / NOW TV will be viewed during the process");
						}
						document.customerInformationForm.actionType.value = "DSCREATELTS";
						document.customerInformationForm.submit();
					});
			
			
			// add by Joyce 20111118
			// control PCD & eyex button display
			$("#PCD").show();
			$("#PCD2").hide();
			$("#ltsAcqOrderBtn").show();
			$("#ltsAcqOrderBtn2").hide();
			
			if ($("#imsLtsButtonInd").val() == 'Y') {
				if ( "${isCC}" != "Y")
				{
					$("#PCD").hide();
					$("#PCD2").show();
				}
			}
			
			if ($("#ltsButtonInd").val() == 'Y') {
				$("#ltsAcqOrderBtn").hide();
				$("#ltsAcqOrderBtn2").show();
			}
			
			$("#familyName,#givenName,#companyName").blur(function(e){
				e.target.value = e.target.value.toUpperCase(); 
			}); 
			$("#familyName,#givenName,#companyName").keyup(function(e){
				e.target.value = e.target.value.toUpperCase(); 
			});
			checkSearching();
	});

	function checkSearching(){
		// for ims direct sales
		$(".searchingCretia").hide();
		if (document.customerInformationForm.idDocType.options[document.customerInformationForm.idDocType.selectedIndex].value == "BR") {
			$("#familyName,#givenName").val("");
			$("#companyName").parents(".searchingCretia").show();
		} else { 
			$("#companyName").val("");
			$("#familyName").parents(".searchingCretia").show();
			$("#givenName").parents(".searchingCretia").show();
		}
		// for ims direct sales (end) 
		}
	
	function hidePCD() {

		checkSearching();
				
		if (document.customerInformationForm.idDocType.options[document.customerInformationForm.idDocType.selectedIndex].value == "BR"
		) {
			$("#PCD").hide();
			$("#PCD2").show();
			$("#ltsAcqOrderBtn").show();
			$("#ltsAcqOrderBtn2").hide();
		} else if (document.customerInformationForm.idDocType.options[document.customerInformationForm.idDocType.selectedIndex].value == "Certificate No"
		) {
			$("#PCD").hide();
			$("#PCD2").show();
			$("#ltsAcqOrderBtn").hide();
			$("#ltsAcqOrderBtn2").show();
		} else {
			$("#PCD").show();
			$("#PCD2").hide();
			$("#ltsAcqOrderBtn").show();
			$("#ltsAcqOrderBtn2").hide();
		}
	}
	
	function printErrMsg() {
		alert("BR and Certificate Customer is not supported in Springboard, please use written contract.");
	}

	function printLtsErrMsg() {
		alert("Certificate Customer is not supported in Springboard, please use written contract.");
	}
	
	function createLtsAcqDsOrder() {
		document.customerInformationForm.actionType.value = "DSCREATELTS";
		document.customerInformationForm.submit();
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
	
	function insertPageTrack(){
		var staffId = "<c:out value="${bomsalesuser.username}"/>";
			$.ajax({
				url : "pagetrack.html?page=customerinformation&func=customer&staffId="+staffId,
				type : 'POST',
				dataType : 'json',
				timeout : 25000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				     if(textStatus=='parsererror'){
						alert("Your session has been timed out, please re-login."); 
				     } 
				},
				success: function(data){	
				custInfoSearch();	
				return;
				}
			});	
		
		}
	function getNtvInfo(fsa){
		openModalDialog("imsdsntvinfo.html?fsa=" + fsa , "Existing nowTV campaigns ", "DSNTV",900,400);
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
	<div class="searchColHalf searchColHalfRight searchingCretia">
			<span class="label">Company Name: </span>
			<span class="inputGroup">
			<form:input path="companyName"/>  
		</span>
	</div>
	<div style="clear:both"></div>
</div>
<div class="row searchRow">
	<div class="searchColHalf searchColHalfLeft searchingCretia">
			<span class="label">Family Name: </span>
			<span class="inputGroup">
			<form:input path="familyName"/>  
		</span>
	</div>
	<div class="searchColHalf searchColHalfRight searchingCretia">
			<span class="label">Given Name: </span>
			<span class="inputGroup">
			<form:input path="givenName"/>  
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
	<form:errors path="companyName" cssClass="error" />
	<form:errors path="familyName" cssClass="error" />
	<form:errors path="givenName" cssClass="error" />
	<div style="clear:both"></div>
</div>
<div class="row buttonmenubox_R">
	<a href="#" class="nextbutton" onClick="javascript:insertPageTrack();">Search</a>
	<a href="#" class="nextbutton" onClick="javascript:custInfoClear();">Clear</a>
	<div style="clear:both"></div>
</div>
</div>

<c:if test="${custProfileMismatch eq true}">
 			<div id="warning_addr_3" class="ui-widget"> 
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="warning_addr_msg3" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"><spring:message code="ims.directsales.01" /></div> 
						</p>
					</div>
				</div>
</c:if>	
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
									<td class="table_title">BL</td>
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
									</tr>
									
									<tr class="contenttextgreen">

										<td class="table_title">LOB</td>
										<td class="table_title">Installation/ Registration Address</td>
										<td class="table_title">Service Start Date</td>
										<td class="table_title">Service Termination Date</td>
										<td class="table_title">nowTV info.</td>
									</tr>
									
										<c:forEach items="${serviceLineDTOList}" var="servInUse" varStatus="counter">
											<tr<c:if test="${counter.index % 2 == 0}"> class="even"</c:if>>
												
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

												<td>${servInUse.validationRulesID}&nbsp;</td>

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
												<td>										
													<c:choose> 
														<c:when test="${serviceLine1amsList[counter.index].isVI eq 'Y'}"> 												
															<div>
																<a href="#" id="ntvinfo" class="nextbutton" onclick ="getNtvInfo('${servInUse.serviceId}') ">
																nowTV info</a>
															</div>
														</c:when>
														<c:otherwise>
															--
														</c:otherwise>
													</c:choose>
												</td>
											</tr> 
										</c:forEach>
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
			<td  align="center" style="padding: 10px 0">
				<div>
					<a id="ltsAcqOrderBtn" href="#" class="nextbutton" onClick="javascript:createLtsAcqDsOrder();">
						LTS Acquisition
					</a>
				</div>
				<div id="ltsAcqOrderBtn2" style="display: block;">
					<a href="#" class="nextbutton" name="asignoff3" onclick="javascript:createLtsAcqDsOrder();printLtsErrMsg();">
						LTS Acquisition
					</a>
				</div>			
			</td>	
			<td align="center" style="padding: 10px 0">
				<div id="NTV">
					<a href="${ntvDomain}ntvorderdetail.html?_al=new&salesType=${bomsalesuser.salesType}&location=${bomsalesuser.location}" class="nextbutton">
						Now TV Acquisition
					</a>
				</div>
			</td>	
			<td align="center" style="padding: 10px 0">
				<div id="NTVRET">
					<a href="${ntvDomain}ntvorderdetail.html?salesType=${bomsalesuser.salesType}&location=${bomsalesuser.location}&isRet=true&familyName=${customerInformationDTOSession.familyName}&idDocType=${customerInformationDTOSession.idDocType}&idDocNum=${customerInformationDTOSession.idDocNum}&givenName=${customerInformationDTOSession.givenName}&_al=new&companyName=${customerInformationDTOSession.companyName}" class="nextbutton">
						Now TV UpSell/Ret
					</a>
				</div>
			</td>
		</tr>
	</table>

	<form:hidden path="imsLtsButtonInd" />
	<form:hidden path="ltsButtonInd" />
	<input type="hidden" name="actionType" id="actionType" />
	
	<form:hidden path="serviceNumberType" />
	<form:hidden path="serviceNumber" />
	<form:hidden path="loginIdPrefix" />
	<form:hidden path="loginIdSuffix" />
</form:form>


<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>