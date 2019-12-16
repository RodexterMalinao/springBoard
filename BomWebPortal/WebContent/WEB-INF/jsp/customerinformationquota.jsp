<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />

<script type="text/javascript">
/* function authorized() {
	$(".auth_button").hide().after($("<span/>").addClass("contenttextgreen").text("Submitting..."));
	pageRedirect($(".auth_button").attr("id"));
} */

$(document).ready(function() {
	/* $(".auth_button").click(function(e) {
		e.preventDefault();
		// http://msdn.microsoft.com/en-us/library/ie/ms536759%28v=vs.85%29.aspx
		var sURL = "mobccsauthorize.html?" + $.param({"action": "AU02"});
		var vArguments = self;
		var sFeatures = "dialogHeight:230px;dialogLeft:0;dialogTop:0;dialogWidth:400px;resizable=yes;scroll=yes;status=no";
		window.showModalDialog(sURL, vArguments, sFeatures);
		return false;
	}); */
	
	$("#quota_check_div").load(
		"mobquotacheck.html?"
			+ $.param({
				idDocType: "${selectedCustInfoSession.idDocType}",
				idDocNum: "${selectedCustInfoSession.idDocNum}"
	}));
});


	function pageRedirect(orderStatus) {
		$('#orderStatus').val(orderStatus.toUpperCase());
		document.customerInformationQuotaForm.submit();
	}
</script>
<form:form name="customerInformationQuotaForm" method="POST"
	commandName="customerInformationQuota">
	<table width="100%" border="0" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="0" rules="none">
					<tr>
						<td class="table_title">Customer Information</td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="0" class="contenttext"
					background="images/background2.jpg">
					<colgroup width="50%"></colgroup>
					<tr>
						<td>
							<spring:message code="label.title"/>
						</td>
						<td>
							<div align="left">
								<c:out value="${customerInformationQuota.title}">N/A</c:out>
							</div></td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.familyName"/>
						<td>
							<div align="left">
								<c:out value="${customerInformationQuota.familyName}">N/A</c:out>
							</div></td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.givenName"/>
						</td>
						<td>
							<div align="left">
								<c:out value="${customerInformationQuota.givenName}">N/A</c:out>
							</div></td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.docId"/>
						</td>
						<td>
							<div align="left">
								<c:out value="${customerInformationQuota.docId}">N/A</c:out>
							</div></td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.dateOfBirth"/>
						</td>
						<td>
							<div align="left">
								<c:out value="${customerInformationQuota.dateOfBirth}">N/A</c:out>
							</div></td>
					</tr>
					<tr>
						<td>
							<spring:message code="label.companyName"/>
						</td>
						<td>
							<div align="left">
								<c:out value="${customerInformationQuota.companyName}">N/A</c:out>
							</div></td>
					</tr>

					<c:choose>
						<c:when
							test='${empty customerInformationQuota.eligibilityDTOList}'>
							<tr>
								<td>
									<spring:message code="label.eligibility"/>
								</td>
								<td>
									<div align="left">N/A</div></td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${customerInformationQuota.eligibilityDTOList}"
								var="eligibilityList" varStatus="eList">
								<tr>
									<c:if test="${eList.first}">
										<td>
											<spring:message code="label.eligibility"/>
										</td>
										<td>
											<div align="left">
												<c:out value="${eligibilityList.customerTierDesc}">N/A</c:out>
											</div></td>
									</c:if>
									<c:if test="${!eList.first}">
										<td>&nbsp;</td>
										<td>
											<div align="left">
												<c:out value="${eligibilityList.customerTierDesc}">N/A</c:out>
											</div></td>
									</c:if>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					<tr>
						<td>
							<spring:message code="label.creditStatus"/>
						</td>
						<td >
							<div align="left">
								<c:choose>
									<c:when test="${customerInformationQuota.creditStatus == '0' || customerInformationQuota.creditStatus == '4'}">
										<font color="black"> <c:out
												value="${customerInformationQuota.creditStatusDesc}">N/A</c:out>
										</font>
									</c:when>
									<c:otherwise>
										<font color="red"> <c:out
												value="${customerInformationQuota.creditStatusDesc}">N/A</c:out>
										</font>
									</c:otherwise>
								</c:choose>
								<c:if
									test="${customerInformationQuota.creditStatusException != null}">
									<span style="color: red"> (Exception found: <c:out
											value="${customerInformationQuota.creditStatusException.message}" />)
									</span>
								</c:if>
							</div></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
				<c:choose>
				<c:when test="${customerInformationDTOSession.mobInd eq 'true'}">
					<div id="quota_check_div"></div>
				</c:when>
				<c:otherwise>
					<table width="100%" border="0" cellspacing="0">
						<tr>
							<td class="table_title">
								<spring:message code="label.quotaInUse"/>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="1" cellspacing="0"
									class="contenttext" bgcolor="#FFFFFF">
									<tr class="contenttextgreen">
										<td class="table_title"><spring:message code="label.quotaType"/></td>
										<td class="table_title"><spring:message code="label.quotaEntitle"/></td>
										<td class="table_title"><spring:message code="label.quotaInUse"/></td>
										<td class="table_title"><spring:message code="label.quotaId"/></td>
									</tr>
									<c:choose>
										<c:when
											test='${empty customerInformationQuota.customerInformationQuotaDTOList}'>
											<tr>
												<td colspan="4">
													<div align="left">No Record Found.</div></td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach
												items="${customerInformationQuota.customerInformationQuotaDTOList}"
												var="quotaList">
												<tr>
													<td>${quotaList.quotaDesc}</td>
													<td>${quotaList.quotaCeiling}</td>
													<td>${quotaList.quotaInUse}</td>
													<c:choose>
														<c:when
															test="${quotaList.quotaCeiling > quotaList.quotaInUse}">
															<td>N/A</td>
														</c:when>
														<c:otherwise>
															<td>${quotaList.quotaId}</td>
														</c:otherwise>
													</c:choose>
	
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</table>
							</td>
						</tr>
					</table>
				</c:otherwise>
				</c:choose>
			</td>
		</tr>

	</table>

	<table width="100%">
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<c:if test="${customerInformationQuota.channelId == 2 || customerInformationQuota.channelId == 1 ||
							customerInformationQuota.channelId == 3 || customerInformationQuota.channelId == 10}">
				<c:choose>
					<c:when test="${customerInformationQuota.channelId == 2}">
						<c:set var="createLabel" value=" Create Order new " />
					</c:when>
					<c:when test="${customerInformationQuota.channelId == 1}">
						<c:set var="createLabel" value=" Retail Order " />
					</c:when>
					<c:when test="${customerInformationQuota.channelId == 3}">
						<c:set var="createLabel" value=" Premier Order " />
					</c:when>
					<c:when test="${customerInformationQuota.channelId == 10}">
						<c:set var="createLabel" value=" Direct Sales Order " />
					</c:when>
				</c:choose>
				<td>
					<c:choose>
						<c:when
							test="${selectedCustInfoSession == null || customerInformationQuota.creditStatus == '0' || customerInformationQuota.creditStatus == '3' || customerInformationQuota.creditStatus == '4'}">
							<div align="right">
								<a href="#" class="nextbutton" id="DRAF"
									onClick="javascript:pageRedirect(this.id);">${createLabel}</a>
							</div>
						</c:when>
						<c:when test="${customerInformationQuota.creditStatus == null}">
							<div align="right">
								<span style="color: red">Credit check is not available: <c:out value="${customerInformationQuota.creditStatusException}" /></span><br/>
								Please bypass and continue order creation.
								<a href="#" class="nextbutton" id="DRAF"
									onClick="javascript:pageRedirect(this.id);">${createLabel}</a>
							</div>
						</c:when>
					</c:choose>
				</td>
			</c:if>	
		</tr>
	</table>

	<input type="hidden" name="orderStatus" id="orderStatus" />
</form:form>