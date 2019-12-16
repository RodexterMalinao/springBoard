<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltscustomeridentification.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 5 : 6}" />
</jsp:include>

<div id="s_line_text"><spring:message code="lts.custIdent.custInfo" text="Customer Info"/></div>

<div class="cosContent">

<c:set var="isBrProfile" value="${sessionScope.sessionLtsOrderCapture.ltsServiceProfile.primaryCust.docType == 'BS'}"/>

<form:form method="POST" action="#" id="customeridentification" name="ltsCustomerIdentificationForm" commandName="ltscustomeridentification" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />

<table width="100%" class="paper_w2 round_10">
	<tr>
		<td>
			<br/>
			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td class="table_title" id="s_line_text"><spring:message code="lts.custIdent.docIdVerification" text="Document ID Verification"/></td>
				</tr>
			</table>
			<br/>
			<table width="100%" border="0" cellspacing="6" cellpadding="4" class="contenttext">
				<tr>
					<td width="5%">&nbsp;</td>
					<td width="20%" valign="top">
						<spring:message code="lts.custIdent.docTypeAndId" text="Doc. Type & ID"/><span class="contenttext_red">*</span> :
					</td>
					<td colspan="2">
						<form:select id="docType" path="docType">
							<form:option value="" ><spring:message code="lts.custIdent.type" text="-- TYPE --"/></form:option>
							<c:if test="${!isBrProfile}">
								<form:option value="HKID" ><spring:message code="lts.custIdent.hkid" text="HKID"/></form:option>
								<form:option value="PASS" ><spring:message code="lts.custIdent.passport" text="Passport"/></form:option>
							</c:if>
							<form:option value="BS" ><spring:message code="lts.custIdent.hkbr" text="HKBR"/></form:option>
						</form:select>
						<form:input path="id" id="docId" readonly="false"/>
						<form:errors path="id" cssClass="error"/>
						<br>
						<span class="smalltextgray"><spring:message code="lts.custIdent.notePrintAFMsg" text="Note: This Doc. Type & ID will be printed on AF Form"/> </span>
					</td>
				</tr>
				<tr>
				    <td>&nbsp;</td>
					<td >
					   <spring:message code="lts.custIdent.DOB" text="Date of birth"/> :
					</td>
					<td colspan="2">
						<form:input id="dateOfBirth" path="dateOfBirth" maxlength="8" readonly="false"/>	
						<form:errors path="dateOfBirth" cssClass="error"/>
     				</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<spring:message code="lts.custIdent.dummyDoc" text="Dummy Document"/> : 
					</td>
					<td colspan="2">
						<form:checkbox id="dummyInd" path="dummy" disabled="${sessionScope.sessionLtsOrderCapture.ltsCustomerInquiryForm.dummyDoc}"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<spring:message code="lts.custIdent.docIDVerified" text="Document ID Verified"/><span class="contenttext_red">*</span> :
					</td>
					<td colspan="2">
						<form:checkbox path="verified" id="docVerified"/>
						<form:errors path="verified" cssClass="error"/>
					</td>
				</tr>

				<tr class="nonRetailElement">
					<td>&nbsp;</td>
					<td>
						<spring:message code="lts.custIdent.3rdParty" text="Third Party Application"/><span class="contenttext_red">*</span> :
					</td>
					<td colspan="2">
						<form:checkbox path="thirdPartyApplication" id="thirdPartyInd" disabled="${sessionScope.sessionLtsOrderCapture.ltsCustomerInquiryForm.thirdPartyApplication}"/>
					</td>
				</tr>
			</table>
			<br>
		</td>
	</tr>
</table>
<br>

<div style="display:none;" class="thirdPartyDiv">
<table width="100%" class="paper_w2 round_10">
	<tr>
		<td>
			<br/>
			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td class="table_title" id="s_line_text"><spring:message code="lts.custIdent.3rdParty" text="Third Party Application"/></td>
				</tr>
			</table>
			<br/>
			<table width="100%" border="0" cellspacing="6" class="contenttext">
				<tr>
				<td colspan="4">
					<div id="thirdPartyDiv">
						<table width="100%" cellspacing="6" cellpadding="4">
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="20%" valign="top">
									<spring:message code="lts.custIdent.applicantName" text="Applicant Name"/><span class="contenttext_red">*</span> : 
								</td>
								<td colspan="2" align="left">
								<form:select path="applicantTitle" id="applicantTitle">
									<form:option value=""><spring:message code="lts.custIdent.titleSelect" text="-TITLE-"/></form:option>
									<form:options items="${titleList}" itemValue="itemKey" itemLabel="itemValue" />
								</form:select>
								<form:errors path="applicantTitle" cssClass="error"/>
							</tr>
							<tr>
								<td>&nbsp;</td>								
								<td><label><spring:message code="lts.custIdent.firstName" text="Given Name"/> :</label></td>
								<td colspan="2"><form:input path="applicantFirstName"/></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><label><spring:message code="lts.custIdent.lastName" text="Family Name"/> :</label></td>
								<td colspan="2">
									<form:input path="applicantLastName"/>
									<form:errors path="applicantLastName" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>
									<spring:message code="lts.custIdent.applicantDocTypeId" text="Applicant Doc. Type & ID"/>
									<c:if test="${sessionScope.bomsalesuser.channelId == 1}">
										<span class="contenttext_red">*</span>
									</c:if>
									  : 
								</td>
								<td colspan="2">
									<form:select id="applicantDocType" path="applicantDocType">
										<form:option value="" ><spring:message code="lts.custIdent.type" text="-- TYPE --"/></form:option>
										<form:option value="HKID"><spring:message code="lts.custIdent.hkid" text="HKID"/></form:option>
										<form:option value="PASS" ><spring:message code="lts.custIdent.passport" text="Passport"/></form:option>
									</form:select>
									<form:input path="applicantId"/>
									<form:errors path="applicantId" cssClass="error"/>
								</td>
							</tr>														
							<tr>
								<td>&nbsp;</td>
								<td>
									<spring:message code="lts.custIdent.applicantVerified" text="Applicant ID Verified Ind"/><span class="contenttext_red">*</span>  :
								</td>
								<td colspan="2">
									<form:checkbox path="applicantVerified"/>
									<form:errors path="applicantVerified" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>
									<spring:message code="lts.custIdent.relationship" text="Relationship with Customer"/><span class="contenttext_red">*</span>  :
								</td>
								<td colspan="2">
									<form:select path="relationship"> 
										<form:option value=""><spring:message code="lts.custIdent.select" text="-- Select --"/></form:option>
										<c:if test="${ltscustomeridentification.dummyDocType != 'BS'}">
											<form:options items="${relationshipCodeList}" itemValue="itemKey" itemLabel="itemValue" />
											
										</c:if>
										<c:if test="${ltscustomeridentification.dummyDocType == 'BS'}">
											<form:options items="${relationshipBrCodeList}" itemValue="itemKey" itemLabel="itemValue" />
										</c:if>
									</form:select>
									<form:errors path="relationship" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>
									<spring:message code="lts.custIdent.applicantContactNo" text="Applicant Contact No."/><span class="contenttext_red">*</span>  : 
								</td>
								<td colspan="2" align="left">
									<form:input path="contactNum"/>
									<form:errors path="contactNum" cssClass="error"/>
								</td>
							</tr>							
							</table>
						</div>
					</td>
				</tr>				
			</table>
		</td>
	</tr>
</table>
</div>
<br>

<c:if test='${createSecDel}'>
<table width="100%" class="paper_w2 round_10">
	<tr>
		<td>
			<br/>
			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td class="table_title" id="s_line_text"><spring:message code="lts.custIdent.2DELDocVerify" text="2nd DEL Document ID Verification"/></td>
				</tr>
			</table>
			<br/>
			<table width="100%" border="0" cellspacing="6" cellpadding="4" class="contenttext">
				<tr>
					<td width="5%">&nbsp;</td>
					<td width="20%">
						<spring:message code="lts.custIdent.docTypeAndId" text="Doc. Type & ID"/>
						<c:if test="${sessionScope.bomsalesuser.channelId == 1}">
							<span class="contenttext_red">*</span>
						</c:if>
						 :
					</td>
					<td colspan="2">
						<form:select id="secDelDocType" path="secDelDocType">
							<form:option value="" ><spring:message code="lts.custIdent.type" text="-- TYPE --"/></form:option>
							<form:option value="HKID" ><spring:message code="lts.custIdent.hkid" text="HKID"/></form:option>
							<form:option value="PASS" ><spring:message code="lts.custIdent.passport" text="Passport"/></form:option>
							<form:option value="BS" ><spring:message code="lts.custIdent.hkbr" text="HKBR"/></form:option>
						</form:select>
						<form:input path="secDelId"/>
						<form:errors path="secDelId" cssClass="error"/>
						<br/>
						<span class="smalltextgray"><spring:message code="lts.custIdent.notePrintAFMsg" text="Note: This Doc. Type & ID will be printed on AF Form."/></span>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<spring:message code="lts.custIdent.dummyDoc" text="Dummy Document"/> :
					</td>
					<td colspan="2">
						<form:checkbox id="secDelDummyInd" path="secDelDummy" disabled="${sessionScope.sessionLtsOrderCapture.ltsOtherVoiceServiceForm.secondDelDummyDoc}"/>
					</td>
				</tr>							
				<tr>
					<td>&nbsp;</td>
					<td>
						<spring:message code="lts.custIdent.docIDVerified" text="Document ID Verified"/><span class="contenttext_red">*</span> :
					</td>
					<td colspan="2">
						<form:checkbox path="secDelVerified"/>
						<form:errors path="secDelVerified" cssClass="error"/>
					</td>
				</tr>
				<tr class="nonRetailElement">
					<td>&nbsp;</td>
					<td>
						<spring:message code="lts.custIdent.2DEL3rdParty" text="2nd Del Third Party Application"/><span class="contenttext_red">*</span> :
					</td>
					<td colspan="2">
						<form:checkbox path="secDelThirdPartyApplication" id="secDelThirdPartyInd" disabled="${sessionScope.sessionLtsOrderCapture.ltsOtherVoiceServiceForm.secondDelThirdPartyAppl}"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<br>

<c:if test='${secDelThirdPartyApplication}'>
</c:if>
<div class="secDelThirdPartyDiv">
<table width="100%" class="paper_w2 round_10">
	<tr>
		<td>
			<br/>
			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td class="table_title" id="s_line_text"><spring:message code="lts.custIdent.2DEL3rdParty" text="2nd Del Third Party Application"/></td>
				</tr>
			</table>
			<br/>
			<table width="100%" border="0" cellspacing="6" cellpadding="4" class="contenttext">
				<tr>
				<td colspan="4">
					<div id="secDelThirdPartyDiv">
						<table width="100%" cellspacing="6" cellpadding="4">
							<tr>
								<td width="5%">&nbsp;</td>
								<td width="20%" valign="top">
									<spring:message code="lts.custIdent.applicantName" text="Applicant Name"/><span class="contenttext_red">*</span>  : 
								</td>
								<td colspan="2" align="left">
									<form:select path="secDelApplicantTitle" id="secDelApplicantTitle">
										<form:option value=""><spring:message code="lts.custIdent.titleSelect" text="-TITLE-"/></form:option>
										<form:options items="${titleList}" itemValue="itemKey" itemLabel="itemValue" />
									</form:select>
									<form:errors path="secDelApplicantTitle" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><label><spring:message code="lts.custIdent.firstName" text="Given Name"/> :</label></td>
								<td colspan="2"><form:input path="secDelApplicantFirstName"/></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><label><spring:message code="lts.custIdent.lastName" text="Family Name"/> :</label> </td>
								<td colspan="2">
									<form:input path="secDelApplicantLastName"/>
									<form:errors path="secDelApplicantLastName" cssClass="error"/>
								</td>
							</tr>							
							<tr>
								<td>&nbsp;</td>
								<td>
									<spring:message code="lts.custIdent.applicantDocTypeId" text="Applicant Doc. Type & ID"/><span class="contenttext_red">*</span>  : 
								</td>
								<td colspan="2">
									<form:select path="secDelApplicantDocType">
										<form:option value="" ><spring:message code="lts.custIdent.type" text="-- TYPE --"/></form:option>
										<form:option value="HKID"><spring:message code="lts.custIdent.hkid" text="HKID"/></form:option>
										<form:option value="PASS"><spring:message code="lts.custIdent.passport" text="Passport"/></form:option>
									</form:select>
									<form:input path="secDelApplicantId"/>
									<form:errors path="secDelApplicantId" cssClass="error"/>
								</td>
							</tr>														
							<tr>
								<td>&nbsp;</td>
								<td>
									<spring:message code="lts.custIdent.applicantVerified" text="Applicant ID Verified Ind"/><span class="contenttext_red">*</span>  :
								</td>
								<td colspan="2">
									<form:checkbox path="secDelApplicantVerified" />
									<form:errors path="secDelApplicantVerified" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>
									<spring:message code="lts.custIdent.relationship" text="Relationship with Customer"/><span class="contenttext_red">*</span>  :
								</td>
								<td colspan="2">
									<form:select path="secDelRelationship">
										<form:option value=""><spring:message code="lts.custIdent.select" text="-- Select --"/></form:option>
										<c:if test="${ltscustomeridentification.dummyDocType != 'BS'}">
											<form:options items="${relationshipCodeList}" itemValue="itemKey" itemLabel="itemValue" />
										</c:if>
										<c:if test="${ltscustomeridentification.dummyDocType == 'BS'}">
											<form:options items="${relationshipBrCodeList}" itemValue="itemKey" itemLabel="itemValue" />
										</c:if>
									</form:select>
									<form:errors path="secDelRelationship" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>
									<spring:message code="lts.custIdent.applicantContactNo" text="Applicant Contact No."/><span class="contenttext_red">*</span>  : 
								</td>
								<td colspan="2" align="left">
									<form:input path="secDelContactNum"/>
									<form:errors path="secDelContactNum" cssClass="error"/>
								</td>
							</tr>			
							</table>
						</div>
					</td>
				</tr>		
			</table>
		</td>
	</tr>
</table>
<br>
</div>

</c:if>

<div id="contactInfoDiv">
	<table width="100%" border="0" cellspacing="1">
		<tr>
			<td class="table_title" id="s_line_text"><spring:message code="lts.custIdent.custContactInfo" text="Customer Contact Information"/></td>
		</tr>
	</table>
	<br/>
	<table id="contact" width="100%" border="0" cellspacing="4" cellpadding="4" class="contenttext">
			<tr>
				<td width="5%">&nbsp;</td>
				<td width="20%">
					<spring:message code="lts.custIdent.custContactEmail" text="Contact Email Address"/> :
				</td>
				<td colspan="2">
					<input type="hidden" id="customerOrgContactEmail" value="${ltscustomeridentification.customerOrgContactEmail}" />
					<form:input id="customerContactEmail" path="customerContactEmail" />
					<form:errors path="customerContactEmail" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<spring:message code="lts.custIdent.custContactMobNo" text="Contact Mobile No."/> :
				</td>
				<td colspan="2">
					<input type="hidden" id="customerOrgContactMobileNum" value="${ltscustomeridentification.customerOrgContactMobileNum}" />
					<form:input id="customerContactMobileNum" path="customerContactMobileNum" />
					<form:errors path="customerContactMobileNum" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<spring:message code="lts.custIdent.custContactFixLineNo" text="Contact Fixed Line No. (Day&Night)"/> :
				</td>
				<td colspan="2">
					<form:input id="customerContactFixLineNum" path="customerContactFixLineNum" />
					<form:errors path="customerContactFixLineNum" cssClass="error"/>
				</td>
			</tr>
		</table>
</div>

<br/>
<br/>

<div id="pdpoDiv">
	<table width="100%" border="0" cellspacing="1">
		<tr>
			<td class="table_title" id="s_line_text"><spring:message code="lts.custIdent.PDPO" text="Personal Information Collection Statement"/></td>
		</tr>
	</table>
	<br/>
	<table width="100%" border="0" cellspacing="6" cellpadding="6" class="contenttext" align="center">
		<tr>
			<td width="5%">&nbsp;</td>
			<td width="20%">
				<spring:message code="lts.custIdent.ltsStatus" text="LTS Status"/>: 
			</td>
			<td colspan="2">
				<form:hidden path="existLtsPdpoStatus"/>
				<c:choose>
					<c:when test="${empty ltscustomeridentification.existLtsPdpoStatus }">
						<c:set var="optinoutval">BLANK</c:set>
					</c:when>
					<c:when test="${ltscustomeridentification.existLtsPdpoStatus == 'OOA'}">
						<c:set var="optinoutval"><spring:message code="lts.custIdent.optOutAllCd" text="OPT-OUT-ALL"/></c:set>
					</c:when>
					<c:when test="${ltscustomeridentification.existLtsPdpoStatus == 'OIA'}">
						<c:set var="optinoutval"><spring:message code="lts.custIdent.optInAllCd" text="OPT-IN-ALL"/></c:set>
					</c:when>
					<c:when test="${ltscustomeridentification.existLtsPdpoStatus == 'OOP'}">
						<c:set var="optinoutval"><spring:message code="lts.custIdent.optOutPartialCd" text="OPT-OUT-PARTIAL"/></c:set>
					</c:when>
				</c:choose>
				<input type="text" disabled="disabled" value="${optinoutval}" size="50">
			</td>
		</tr>
		<tr class="updateOptoutStatus">
			<td>&nbsp;</td>
			<td>
				<spring:message code="lts.custIdent.optInoptOut" text="Opt-In / Opt-Out"/>:
			</td>
			<td colspan="2">
				<form:select id="newLtsPdpoStatus" path="newLtsPdpoStatus">
					<form:option value=""><spring:message code="lts.custIdent.select" text="-- Select --"/></form:option>
					<form:option value="OIA"><spring:message code="lts.custIdent.optInAll" text="Opt-In All"/></form:option>
					<form:option value="OOP"><spring:message code="lts.custIdent.optOut" text="Opt-Out"/></form:option>
				</form:select>
				<form:errors path="newLtsPdpoStatus" cssClass="error"/>
			</td>
		</tr>
		<tr class="partialOptOut">
			<td>&nbsp;</td>
			<td valign="top">
				<spring:message code="lts.custIdent.optoutService" text="Opt-Out Service"/>:
			</td>
			<td colspan="2">
				<table class="table_style" width="100%" border="1">
					<tr>
						<th align="center"><spring:message code="lts.custIdent.directMailing" text="Direct Mailing"/></th>
						<th align="center"><spring:message code="lts.custIdent.outbound" text="Outbound"/></th>
						<th align="center"><spring:message code="lts.custIdent.sms" text="SMS"/></th>
						<th align="center"><spring:message code="lts.custIdent.email" text="Email"/></th>
						<th align="center"><spring:message code="lts.custIdent.webBIll" text="Web Bill"/></th>
						<th align="center"><spring:message code="lts.custIdent.billInsert" text="Bill Insert"/></th>
						<th align="center"><spring:message code="lts.custIdent.cdOutdail" text="CD Outdail"/></th>
						<th align="center"><spring:message code="lts.custIdent.bm" text="BM"/></th>
						<th align="center"><spring:message code="lts.custIdent.smseye" text="SMS(EYE)"/></th>
						<th align="center"><spring:message code="lts.custIdent.optoutAll" text="Opt-Out(All)"/></th>
					</tr>
					<tr>
						<td align="center"><form:checkbox cssClass="pdpoMean" path="ltsPdpoDirectMailing"/></td>
						<td align="center"><form:checkbox cssClass="pdpoMean" path="ltsPdpoOutbound"/></td>
						<td align="center"><form:checkbox cssClass="pdpoMean" path="ltsPdpoSms"/></td>
						<td align="center"><form:checkbox cssClass="pdpoMean" path="ltsPdpoEmail"/></td>
						<td align="center"><form:checkbox cssClass="pdpoMean" path="ltsPdpoWebBill"/></td>
						<td align="center"><form:checkbox cssClass="pdpoMean" path="ltsPdpoBillInsert"/></td>
						<td align="center"><form:checkbox cssClass="pdpoMean" path="ltsPdpoCdOutdial"/></td>
						<td align="center"><form:checkbox cssClass="pdpoMean" path="ltsPdpoBm"/></td>
						<td align="center"><form:checkbox cssClass="pdpoMean" path="ltsPdpoSmsEye"/></td>
						<td align="center"><form:checkbox cssClass="optOutAll" path="ltsPdpoOptOutAll"/></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
</div>

<br/>

<form:hidden id="dummyDocType" path="dummyDocType"/>
<form:hidden id="dummyId" path="dummyId"/>
<form:hidden id="secDelDummyDocType" path="secDelDummyDocType"/>
<form:hidden id="secDelDummyId" path="secDelDummyId"/>
<form:hidden id="updateContactMsgPrompted" path="updateContactMsgPrompted" />

</form:form>
<div id="flowButton" style="float:right">
<table width="100%" border="0" cellspacing="0" class="contenttext">
<tr>
	<td align="right"> 
	<a id="submit" href="#" ><div class="button"><spring:message code="lts.mis.next" text="Next"/></div></a> 
	</td>
</tr>
</table>
</div>
</div>
<script type='text/javascript'>
	/*var dummy = ${sessionScope.sessionLtsOrderCapture.ltsCustomerInquiryForm.dummyDoc};
	//var secDeldummy = "${ltscustomeridentification.secDelDummy}";
	if(dummy){
		$("#dummyInd").attr('disabled', true);
	}else{
		$("#dummyInd").attr('disabled', false);
	}*/
	/*if(secDeldummy == "true"){
		$("#secDelDummyInd").attr('disabled', true);
	}else{
		$("#secDelDummyInd").attr('disabled', false);
	}*/
	if("${sessionScope.bomsalesuser.channelId == 1}" == "true"){
		$(".nonRetailElement").hide();
	}else{
		$(".nonRetailElement").show();
	}
	
	if($("#dateOfBirth").val() !== ''){
		$("#dateOfBirth").attr('readonly', 'readonly');
	}
	
	//var contactAlertBlankMsg = '<spring:message code="lts.custIdent.alertContactBlank" />';
	var contactAlertUpdateMsg = '<spring:message code="lts.custIdent.alertUpdateContact" htmlEscape="true"/>';
	
	var isContactEmailOverDue ="${ltscustomeridentification.contactEmailOverDue}" == "true";
	var isContactMobileNumOverDue = "${ltscustomeridentification.contactMobileNumOverDue}" == "true";
	var isContactOverDue = "${ltscustomeridentification.contactEmailOverDue || ltscustomeridentification.contactMobileNumOverDue }" == "true";
	var isContactBlank = "${empty ltscustomeridentification.customerOrgContactEmail || empty ltscustomeridentification.customerOrgContactMobileNum }" == "true";
	
	var isMsgPromptedFlag = false;
	
	$(ltscustomeridentification.actionPerform);
</script>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>