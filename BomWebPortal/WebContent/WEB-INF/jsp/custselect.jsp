<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script language="javascript">
	document.onkeypress = processKey;

	function processKey(e) {
		if (null == e)
			e = window.event;
		if (e.keyCode == 13) {
			cont();
		}
	}

	function cont() {
		document.custSelectForm.actionType.value = "CONTINUE";
		showLoading();
		document.custSelectForm.submit();
	}
</script>

<form:form name="custSelectForm" method="POST" commandName="custSelect">

	<div>
		<c:if test="${custSearchException != null}">
			<span class="error">Customer Search is not available: <c:out value="${custSearchException}" /></span>
		</c:if>
		<form:errors path="customerInformationError" cssClass="error" />
	</div>

	<div>
		Please select existing profile to create new order
	</div>
	
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><%--content--%>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td class="table_title">Basic Customer Information</td>
				</tr>
			</table>
			</td>
		</tr>
		<c:if test='${!empty custSearchResultList}'>
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
					</tr>
									
					<tr class="contenttextgreen">

						<td class="table_title">&nbsp;</td>
						<td class="table_title">LOB</td>
						<td class="table_title">ID Doc Type</td>
						<td class="table_title">ID Doc Num</td>
						<td class="table_title">Title</td>
						<td class="table_title">Family Name</td>
						<td class="table_title">Given Name</td>
						<td class="table_title">Company Name</td>
						<td class="table_title">Customer Blacklist</td>
						<td class="table_title">ID Verify</td>

						<c:forEach items="${custSearchResultList}" var="basicCustInfo" varStatus="counter1">
							<tr>
							
								<td>
									<c:choose>
										<c:when test='${!(mobInd == "true")}'>
											<form:radiobutton path="selectedCust" value="${counter1.index}" disabled="false"/> 
											&nbsp;
										</c:when>
										<c:when test='${(mobInd == "true") && (basicCustInfo.systemId == "MOB")}'>
											<form:radiobutton path="selectedCust" value="${counter1.index}" disabled="false" /> 
											&nbsp;
										</c:when>
										<c:otherwise>
											<form:radiobutton path="selectedCust" value="${counter1.index}" disabled="true"/>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</td>
							
								<td>${basicCustInfo.systemId}&nbsp;</td>
								
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

								<td>${basicCustInfo.title}&nbsp;</td>

								<td>${basicCustInfo.lastName}&nbsp;</td>

								<td>${basicCustInfo.firstName}&nbsp;</td>

								<td>${basicCustInfo.companyName}&nbsp;</td>

								<td>${basicCustInfo.blackListInd}&nbsp;</td>

								<%--<td>${basicCustInfo.overdueInd}&nbsp;</td>--%>

								<td>${basicCustInfo.idVerifyInd}&nbsp;</td>
							</tr>
						</c:forEach>
					</tr>
				</table>
				</td>
			</tr>
		</c:if>
		<c:if test='${empty custSearchResultList}'>
			<tr>
				<td>No Record Found.</td>
			</tr>
		</c:if>

	</table>
	
	<br>
	
	<%-- *******************************   SERVICE IN USE   *************************************** --%>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><%--content--%>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td class="table_title">Service Line Information</td>
				</tr>
			</table>
			</td>
		</tr>
		<c:if test='${!empty serviceLineDTOList}'>
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

						<c:forEach items="${serviceLineDTOList}" var="servInUse"
							varStatus="counter2">
							<tr>
								<td>
									<c:choose>
										<c:when test='${!(srvLnMobInd == "true")}'>
											<form:radiobutton path="selectedNum" value="${counter2.index}" disabled="false"/> 
											&nbsp;
										</c:when>
										<c:when test='${(srvLnMobInd == "true") && (servInUse.systemId == "MOB")}'>
											<form:radiobutton path="selectedNum" value="${counter2.index}" disabled="false" /> 
											&nbsp;
										</c:when>
										<c:otherwise>
											<form:radiobutton path="selectedNum" value="${counter2.index}" disabled="true"/>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</td>
								
								<c:if test='${servInUse.serviceEndDate == "--"}'>
									<td>${servInUse.systemId}&nbsp;</td>
								</c:if>
								<c:if test='${!(servInUse.serviceEndDate == "--")}'>
									<td>
										<font color="#FF0000">
											${servInUse.systemId}&nbsp;
										</font>
									</td>
								</c:if>

								<c:if test='${servInUse.serviceEndDate == "--"}'>
									<td>${servInUse.serviceNum}&nbsp;</td>
								</c:if>
								<c:if test='${!(servInUse.serviceEndDate == "--")}'>
									<td>
										<font color="#FF0000">
											${servInUse.serviceNum}&nbsp;
										</font>
									</td>
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
					<td>No Customer detail display because service lines have more than 30 records.</td>
				</tr>
			</c:if>
			<c:if test='${!(errorSeverity == "1")}'>
				<tr>
					<td>No Record Found.</td>
				</tr>
			</c:if>
		</c:if>
		
	</table>
	
	* Selected address will be used as Registration Address (can be changed in Registration page)
	
	<br>
	<%-- ********************************   BOTTOM BUTTONS   ************************************* --%>
	<table width="100%" border="0" cellspacing="0">
		<tr align="right">
			<td>
				<a href="#" onclick="cont()" class="nextbutton">
					Continue&nbsp;&gt;
				</a>
			</td>
		</tr>
	</table>
	
	<input type="hidden" name="actionType" id="actionType" />
	<form:hidden path="mobInd" />
	<form:hidden path="srvLnMobInd" />
	
</form:form>


<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>