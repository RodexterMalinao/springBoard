<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript" src="js/web/lts/acq/ltsacqpersonalinfo.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<script src="js/jquery-ui-1.8.16.custom.min.js"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp" >
    <jsp:param name="step" value="4" />
</jsp:include>


<!-- <link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" /> -->

<c:set var="isOrderSubmitted" value="${sessionScope.sessionLtsOrderCapture.sbOrder != null}" />

<form:form method="POST" action="#" id="personalinfoform" name="ltsPersonalInfoForm" commandName="ltspersonalinfo" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<input type="hidden" id="errorMsg" />
<form:hidden id="wipMsg" path="wipMsg" />
<form:hidden id="warningCustomerSpecialRemarkMessage" path="warningCustomerSpecialRemarkMessage" />
<form:hidden id="warningCustomerNotMatchMessage" path="warningCustomerNotMatchMessage" />
<form:hidden id="acctNumNotMatchMsg" path="acctNumNotMatchMsg" />
<form:hidden id="alertUpdContactMsg" path="alertUpdContactMsg" />
<form:hidden id="alertUpdEmailMsg" path="alertUpdEmailMsg" />
<form:hidden id="alertUpdMobileNoMsg" path="alertUpdMobileNoMsg" />
<form:hidden id="oldMobileNo" path="oldMobileNo" />
<form:hidden id="oldContactEmail" path="oldContactEmail" />
<form:hidden id="oldMobileNoDate" path="oldMobileNoDate" />
<form:hidden id="oldContactEmailDate" path="oldContactEmailDate" />
<form:hidden id="contactUpdAlert" path="contactUpdAlert" />
<form:hidden id="disable" path="disable" />
<form:hidden id="createNewCust" path="createNewCust" />
<form:hidden id="custNum" path="custNum" />
<form:hidden id="validDocId" path="validDocId" />
<form:hidden id="displayPICS" path="displayPICS" />
<form:hidden id="basketType" path="basketType" />
<form:hidden id="docTypeNotMatchMsg" path="docTypeNotMatchMsg"/>
<table width="100%" class="paper_w2 round_10"> 
		<tr> 
			<td>
			<table width="100%" border="0" cellspacing="1" >
				<tr>
					<div id="s_line_text"><spring:message code="lts.acq.personalInfo.personalInfo" htmlEscape="false"/></div>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="3" class="paper_w2">
				<tr>
					<td width="30%">
						<div align="right"></div>
					</td>
					<td  width="30%" >&nbsp;
		                <c:if test="${not empty ltspersonalinfo.docTypeNotMatchMsg}">
		                	<div id="error_profile" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_profile_msg" class="contenttext">
										<b>${ltspersonalinfo.docTypeNotMatchMsg}</b>
									</div>
									<p></p>
								</div>
							</div>
		                </c:if>
					</td>
					<td  width="40%" >&nbsp;</td>	
				</tr>
			    <tr>
					<td >
						<div align="right"><b><spring:message code="lts.acq.personalInfo.thirdPartyApplication" htmlEscape="false"/>  :</b></div>
					</td>
					<td colspan="3">
					 	<form:radiobutton id="thirdParty1" path="thirdPartyInd" value="true" disabled="${!ltspersonalinfo.allowThirdParty}"/><spring:message code="lts.acq.personalInfo.yes" htmlEscape="false"/> 
					 	<form:radiobutton id="thirdParty2" path="thirdPartyInd" value="false"/><spring:message code="lts.acq.personalInfo.no" htmlEscape="false"/>
					 	<form:errors path="thirdPartyInd" cssClass="error"/>
					 	<form:errors path="wipMsg" cssClass="error"/>
                    </td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="3" class="paper_w2" id="personalInfo">
				<tr>
					<td width="30%">
						<div align="right"></div>
					</td>
					<td  width="30%" >&nbsp;</td>
					<td  width="40%" >&nbsp;</td>	
				</tr>
				<tr>
					<td align="right"><b><spring:message code="lts.acq.personalInfo.documentType" htmlEscape="false"/>  : </b></td>
					<td colspan="4">
						<c:if test="${empty sessionScope.sessionLtsAcqOrderCapture.customerDetailProfileLtsDTO || ltspersonalinfo.documentType=='' || !ltspersonalinfo.validDocId}">
						    <form:select id="documentType" path="documentType">
								<form:option value="" ><spring:message code="lts.acq.personalInfo.typeInSelector" htmlEscape="false"/></form:option>
								<form:option value="HKID"><spring:message code="lts.acq.personalInfo.HKID" htmlEscape="false"/></form:option>
								<form:option value="PASS"><spring:message code="lts.acq.personalInfo.passport" htmlEscape="false"/></form:option>
								<c:if test="${!ltspersonalinfo.channelDs}">
									<form:option value="BS"><spring:message code="lts.acq.personalInfo.HKBR" htmlEscape="false"/></form:option>
								</c:if>
							</form:select>
		                </c:if>
		                <c:if test="${not empty sessionScope.sessionLtsAcqOrderCapture.customerDetailProfileLtsDTO && ltspersonalinfo.validDocId && ltspersonalinfo.documentType!=''}">
		                	<form:select id="documentType" path="documentType">
		                		<form:option value="${ltspersonalinfo.documentType}">		
								<c:if test="${ltspersonalinfo.documentType=='HKID'}"><spring:message code="lts.acq.personalInfo.HKID" htmlEscape="false"/></c:if>
								<c:if test="${ltspersonalinfo.documentType=='PASS'}"><spring:message code="lts.acq.personalInfo.passport" htmlEscape="false"/></c:if>
								<c:if test="${ltspersonalinfo.documentType=='BS'}"><spring:message code="lts.acq.personalInfo.HKBR" htmlEscape="false"/></c:if>
		                		</form:option>
		                	</form:select>
		                </c:if>
						<form:errors path="documentType" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td >
					<div align="right"><b><spring:message code="lts.acq.personalInfo.documentNo" htmlEscape="false"/><span class="contenttext_red">*</span> :</b></div>
					</td>
					<td >
						<form:input cssClass="toUpper" id="docNum" path="docNum" />
	
						<form:checkbox path="verified" id="docVerified" disabled="${sessionScope.bomsalesuser.channelId == 2}"/><spring:message code="lts.acq.personalInfo.verified" htmlEscape="false"/>
						<br>
						<form:errors path="verified" cssClass="error"/>
	
						<form:errors path="docNum" cssClass="error"/>
					</td>
				</tr>
				<tr class="brField">
					<td >
					<div align="right"><b><spring:message code="lts.acq.personalInfo.companyName" htmlEscape="false"/><span class="contenttext_red">*</span> :</b></div>
					</td>
					<td >
					    <form:input id="companyName" path="companyName" maxlength="40" readonly="${not empty sessionScope.sessionLtsAcqOrderCapture.customerDetailProfileLtsDTO}"/>
					    <form:errors path="companyName" cssClass="error"/>
					</td>
				</tr>
				<tr class="personField">
					<td >
						<div align="right"><b><spring:message code="lts.acq.personalInfo.title" htmlEscape="false"/><span class="contenttext_red">*</span> :</b></div>
					</td>
					<td colspan="3">
						<div id="titleDiv">
							<c:if test="${empty sessionScope.sessionLtsAcqOrderCapture.customerDetailProfileLtsDTO || ltspersonalinfo.documentType==''}">
								<form:select path="title" id="title">
									<form:option value="">-TITLE-</form:option>
									<form:options items="${titleList}" itemValue="itemKey" itemLabel="itemValue" />
								</form:select>
								<br>
								<form:errors path="title" cssClass="error"/>
							</c:if>
						</div>
						<c:if test="${not empty sessionScope.sessionLtsAcqOrderCapture.customerDetailProfileLtsDTO && ltspersonalinfo.documentType!=''}">
							<form:select id="title" path="title">
								<form:option value="${ltspersonalinfo.title}">${ltspersonalinfo.title}</form:option>
							</form:select>
							<br>
							<form:errors path="title" cssClass="error"/>
						</c:if>
					</td>
				</tr>
				<tr class="personField">
					<td >
					<div align="right"><b><spring:message code="lts.acq.personalInfo.familyName" htmlEscape="false"/><span class="contenttext_red">*</span> :</b></div>
					</td>
					<td >
						<form:input cssClass="toUpper" id="familyName" path="familyName" maxlength="40" readonly="${not empty sessionScope.sessionLtsAcqOrderCapture.customerDetailProfileLtsDTO}"/>
						<form:errors path="familyName" cssClass="error"/>
					</td>
					<td><spring:message code="lts.acq.personalInfo.mustBeIdenticalAsHKIDcard" htmlEscape="false"/></td>
				</tr>
				<tr class="personField">
					<td >
					<div align="right"><b><spring:message code="lts.acq.personalInfo.givenName" htmlEscape="false"/><span class="contenttext_red">*</span> :</b></div>
					</td>
					<td >
						<form:input cssClass="toUpper" id="givenName" path="givenName" maxlength="40" readonly="${not empty sessionScope.sessionLtsAcqOrderCapture.customerDetailProfileLtsDTO}"/>		
						<form:errors path="givenName" cssClass="error"/>
												
					</td>
				</tr>
				<!-- <tr class="ccRow"> -->
				<tr class="personField">
					<td >
					<div align="right"><b><spring:message code="lts.acq.personalInfo.dateOfbirth" htmlEscape="false"/> :</b></div>
					</td>
					<td>
						<form:input id="dateOfBirth" path="dateOfBirth" maxlength="8" readonly="true"/>	
						<form:errors path="dateOfBirth" cssClass="error"/>
     				</td>
				</tr>
				<!-- <tr class="ccRow"> -->
				<!-- 
				<tr class="personField">
				  <td >
					 <div align="right">(YYYYMMDD)</div>
     		      </td>
				</tr>
				-->
				<tr class="ccRow">
					<td >
						<div align="right"><b><spring:message code="lts.acq.personalInfo.contactEmailAddress" htmlEscape="false"/> :</b></div>
					</td>
					<td>
						<form:input id="contactEmail" path="contactEmail" />
							<form:errors path="contactEmail" cssClass="error"/>	
     				</td>
     				<td> &nbsp; </td>
				</tr>
				<tr class="ccRow">
					<td >
					<div align="right"><b><spring:message code="lts.acq.personalInfo.contactMobilePhoneNo" htmlEscape="false"/> :</b></div>
					</td>
					<td >
						<form:input id="mobileNo" path="mobileNo" maxlength="8"/>
						<form:errors path="mobileNo" cssClass="error"/>		
					</td>
					<td> &nbsp; </td>
				</tr>
				<tr class="ccRow">
					<td >
					<div align="right"><b><spring:message code="lts.acq.personalInfo.contactFixedLineNo" htmlEscape="false"/> :</b></div>
					</td>
					<td >
						<form:input id="fixedLineNo" path="fixedLineNo" maxlength="8"/>
						<form:errors path="fixedLineNo" cssClass="error"/>		
					</td>
					<td> &nbsp; </td>
				</tr>
				
			<!--	
				<tr class="ccRow">
					<td >
					<div align="right"><b>User Name / Directory Name<span class="contenttext_red">*</span> :</b></div>
					</td>
					<td >
						<form:input id="jobName" path="jobName" maxlength="40"/>
												
					</td>
				</tr>
				
			-->
			<c:if test="${!ltspersonalinfo.channelDs}">
				<tr>
					<td >
					<div align="right"><b><spring:message code="lts.acq.personalInfo.DummyDocument" htmlEscape="false"/>  :</b></div>
					</td>
					<td colspan="3">
						<form:radiobutton id="dummyDoc1" path="dummyDoc" value="true"/><spring:message code="lts.acq.common.yes" htmlEscape="false"/>
						<form:radiobutton id="dummyDoc2" path="dummyDoc" value="false"/><spring:message code="lts.acq.common.no" htmlEscape="false"/>							
                    </td>
				</tr>
			</c:if>
				<c:if test="${not empty sessionScope.sessionLtsAcqOrderCapture.secondDelServiceProfile && !ltspersonalinfo.channelDs}">
					<tr>
						<td >
						<div align="right"><b><spring:message code="lts.acq.personalInfo.secondDELDummyDocument" htmlEscape="false"/>  :</b></div>
						</td>
						<td colspan="3">
						<form:radiobutton id="secondDeldummyDoc1" path="secondDelDummyDoc" value="true" /><spring:message code="lts.acq.common.yes" htmlEscape="false"/>
						 <form:radiobutton id="secondDeldummyDoc2" path="secondDelDummyDoc" value="false"/><spring:message code="lts.acq.common.no" htmlEscape="false"/>
	                    </td>
					</tr>
					<tr class="secondDel" style="display:none">
						<td >
						<div align="right" ><b><spring:message code="lts.acq.personalInfo.secondDELDocumentNo" htmlEscape="false"/><span class="contenttext_red">*</span> :</b></div>
						</td>
						<td >
							<form:input id="secondDelDocNum" path="secondDelDocNum" />
							<form:checkbox path="secDelDocNumVerified" id="docVerified"/><spring:message code="lts.acq.personalInfo.verified" htmlEscape="false"/>
							<form:errors path="secDelDocNumVerified" cssClass="error"/>
							<form:errors path="secondDelDocNum" cssClass="error"/>
						</td>
					</tr>
				</c:if>
				<tr>
				
				<tr>
					<td>
						<div align="right"></div>
					</td>
					<td >&nbsp;</td>
				</tr>
				<tr class="thirdPartyApp"><td colspan="3">
				<table width="100%" border="0" cellspacing="1" >
				<tr>
					<!-- <td colspan="3" class="table_title">Third Party Application </td> -->
					<div id="s_line_text"> <spring:message code="lts.acq.personalInfo.thirdPartyApplication" htmlEscape="false"/> </div>
				</tr>
				</table></td></tr>
				
				<tr class="thirdPartyApp">
					<td style="vertical-align: top">
					<div  align="right"><b><spring:message code="lts.acq.personalInfo.applicantName" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></div>
					</td>
					<td>
						<form:select id="thirdPartyTitle" path="thirdPartyTitle">
						  <form:option value="" label='-TITLE-' />
						  <form:options items="${thirdPartyTitleList}" itemValue="itemKey" itemLabel="itemKey" />
						 </form:select>
						 <form:errors path="thirdPartyTitle" cssClass="error"/>
                    </td>
				</tr>
				
				<tr class="thirdPartyApp">
					<td>
					<div align="right"><b><spring:message code="lts.acq.personalInfo.givenName" htmlEscape="false"/><span class="contenttext_red">*</span> :</b></div>
					</td>
					<td><form:input cssClass="toUpper" id="thirdPartyGivenName" path="thirdPartyGivenName" maxlength="40"/>
					<form:errors path="thirdPartyGivenName" cssClass="error"/></td>
				</tr>
				
				<tr class="thirdPartyApp">
					<td>
					<div  align="right"><b><spring:message code="lts.acq.personalInfo.familyName" htmlEscape="false"/><span class="contenttext_red">*</span> :</b></div>
					</td>
					<td><form:input cssClass="toUpper" id="thirdPartyFamilyName" path="thirdPartyFamilyName" maxlength="40"/>
					<form:errors path="thirdPartyFamilyName" cssClass="error"/></td></td>
				</tr>
				
				<tr class="thirdPartyApp">
				<td>
					<div  align="right"><b><spring:message code="lts.acq.personalInfo.applicantDocType" htmlEscape="false"/> :</b>
					<c:if test="${ltspersonalinfo.mustVerify}">
						<span class="contenttext_red">*</span>
					</c:if>
					</div>
				</td>
				<td>
				   <form:select id="thirdPartyDoctype" path="thirdPartyDoctype">
						<form:option value="" ><spring:message code="lts.acq.personalInfo.typeInSelector" htmlEscape="false"/></form:option>
						<form:option value="HKID"><spring:message code="lts.acq.personalInfo.HKID" htmlEscape="false"/></form:option>
						<form:option value="PASS"><spring:message code="lts.acq.personalInfo.passport" htmlEscape="false"/></form:option>
					</form:select>
					<form:input cssClass="toUpper" id="thirdPartyDocId" path="thirdPartyDocId" />
					<form:errors path="thirdPartyDoctype" cssClass="error"/>
					<form:errors path="thirdPartyDocId" cssClass="error"/>	
				</td>
				</tr>
				<c:if test="${sessionScope.bomsalesuser.channelId != 2}">
				<tr class="thirdPartyApp">
				  <td>	<div  align="right"><b><spring:message code="lts.acq.personalInfo.applicantIDVerifiedInd" htmlEscape="false"/> : </b>
					<c:if test="${ltspersonalinfo.mustVerify}">
						<span class="contenttext_red">*</span>
					</c:if></div> </td>
				  <td>
					<form:checkbox id="thirdPartyAppIdVerify" path="thirdPartyAppIdVerify"/>
					<form:errors path="thirdPartyAppIdVerify" cssClass="error"/>
				  </td> 
				</tr>
				</c:if>
				<tr class="thirdPartyApp">
				  <td>	<div  align="right"><b><spring:message code="lts.acq.personalInfo.relationshipWithCustomer" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></div> </td>
				  <td>
				    <form:select id="thirdPartyRelationship" path="thirdPartyRelationship">
				    	<form:option value="" label="-- SELECT --" />
					 	<form:options cssClass="personField" items="${relationshipCodeList}" itemValue="itemKey" itemLabel="itemValue" />
						<form:options cssClass="brField" items="${relationshipBrCodeList}" itemValue="itemKey" itemLabel="itemValue" />				    	
					 <!--  <form:options items="${thirdPartyRelationship}" itemValue="itemKey" itemLabel="itemKey" /> -->
					</form:select>
					<form:errors path="thirdPartyRelationship" cssClass="error"/>
				  </td>
				</tr>
				<tr class="thirdPartyApp">
				    <td>	<div  align="right"><b><spring:message code="lts.acq.personalInfo.applicantContactNo" htmlEscape="false"/><span class="contenttext_red">*</span> : </b></div> </td>
				    <td>
				     <form:input id="thirdPartyContactNum" path="thirdPartyContactNum" />
				    <form:errors path="thirdPartyContactNum" cssClass="error"/>
				    </td>
				</tr>
				<c:if test="${ltspersonalinfo.channelDs}">
				<tr class="thirdPartyApp">
				    <td>	
					    <div  align="right">
						    <b><spring:message code="lts.acq.personalInfo.dateOfbirth" htmlEscape="false"/> : </b>
					    </div> 
				    </td>
				    <td>
					    <form:input id="thirdPartyDateOfBirth" path="thirdPartyDateOfBirth" maxlength="8" readonly="true"/>	
					    <form:errors path="thirdPartyDateOfBirth" cssClass="error"/>
				    </td>
				</tr>
				</c:if>
	           	<tr class="thirdPartyApp">
					<td>
						<div align="right"></div>
					</td>
					<td >&nbsp;</td>
				</tr>
                
				<tr><td colspan="3">
              </td></tr>
</table></td></tr></table>

<br/>

<c:choose>	
	<c:when test="${ ltspersonalinfo.displayPICS }">
		<table width="100%" border="0" cellspacing="1"  class="paper_w2 round_10" style="display:block;" id="PICS_CONTENT">
	</c:when>	
	<c:otherwise>
		<table width="100%" border="0" cellspacing="1"  class="paper_w2 round_10" style="display:none;" id="PICS_CONTENT">
	</c:otherwise>
</c:choose>
				<tr>
					<!-- <td class="table_title">Personal Information Collection Statement </td> -->
					<td><div id="s_line_text"> <spring:message code="lts.acq.personalInfo.personalInfoCollectStatement" htmlEscape="false"/> </div></td>
				</tr>
				<tr><td colspan="3" >
				<table id="pics_tbl" width="100%" border="0" cellspacing="2" class="paper_w2" style="min-width: 800px;">
				<tr>
					<td width="20%">&nbsp;</td>
					<td  width="80%" >&nbsp;
					<div align="right"></div>
					</td>
				</tr>
				<tr>
					<td width="20%"> <spring:message code="lts.acq.personalInfo.LTSstatus" htmlEscape="false"/> </td>
					<td  width="80%" > <form:input path="ltsStatus" readonly="true"/> </td>
				</tr>
				<tr>
					<td width="20%"> <spring:message code="lts.acq.personalInfo.IDDstatus" htmlEscape="false"/> </td>
					<td  width="80%" > <form:input path="iddStatus" readonly="true"/> </td>
				</tr>
				<tr>
				    <td>
						<div align="left"></div>
					</td>
				</tr>	
				
				<!-- 
				<tr>
				   <td>&nbsp;</td>
					<td width="100%" align="left" colspan="4">
					    <textarea cols="100%" rows="10" name="remarks" id="remarks" style="width: 806px; height: 100px;">
Depending on the service or combination of services subscribed for in this Application, the personal data and other information ("Data") so provided is collected, used and retained by either one or more of the service providers of the PCCW Group, including Hong Kong Telecommunications (HKT) Limited ("HKT"), CSL Mobile Limited, PCCW Media Limited and HKT CSP Limited (as the case may be) in accordance with the requirements in the Personal Data (Privacy) Ordinance and the Privacy Policy Statement (which can be viewed at http://www.hkt.com/legal/privacy.html, http://www.pccw.com/legal/privacy.html or http://www.cs.hkt.com) which also governs, together with the applicable terms and conditions of the service(s), how the Data is used and to whom it is disclosed. For the purpose of processing of application and provision of the service(s), managing the service account and enabling the Portal service through which you may access and retrieve the account information in relation to some of your services subscribed with and provided by the concerned service providers, the Data could be used by and/or disclosed to affiliates or related companies of the PCCW Group, business partners and debt collection agents.

Subject to customer's right , the information in this Application, including customer's contact information, service number and service account number, may be used in sending to customer notice of gifts, discounts, privileged offers, benefits and any other promotions related either to this service being subscribed or to other kinds of goods and services including telecommunications network services, computer peripheral, accessories and software, secretarial services, personal assistance services, information services and the latest offers on various kinds of products or services including gaming, sports, music, beauty products, electronics, technology, e-commerce, cloud services, mobile payment, travelling, banking, investment, entertainment, transportation, household, fashion, food and beverages, alcohol and tobacco, insurance, education, health and wellness, social networking, media and high-end consumer products and services. Request for accessing or correction of personal data or any enquiry about using data for marketing activities can be made in writing to the Privacy Compliance Officer (CSL Mobile Limited : PO Box 9872, GPO, Hong Kong; Others: PO Box 9896, GPO, Hong Kong).		    				    
					    </textarea>
					</td>
				</tr>
				-->
				<tr>
				<td>&nbsp;</td>
				<td>
				<c:choose>	
					<c:when test="${(ltspersonalinfo.ltsStatus==null && ltspersonalinfo.iddStatus==null) || (ltspersonalinfo.ltsStatus=='NEW' && ltspersonalinfo.iddStatus=='NEW') || (ltspersonalinfo.ltsStatus=='' && ltspersonalinfo.iddStatus=='') }">
						<a href="#" onClick='optInfoCheck()'><div class="func_button"><spring:message code="lts.acq.personalInfo.optOutInfo" htmlEscape="false"/></div></a><br><br>
						</td>
					</tr>
					<tr>
						<td colspan=2>
							<table width="100%" border="0" cellspacing="1" cellpadding="3" style="display: none; text-align: center;" id="optInOutNewDiv">
							<tr>
								<td style="background-color: #6CA9F5;color: #fff;"><spring:message code="lts.acq.personalInfo.optOutAll" htmlEscape="false"/></td>
							</tr>
							<tr>
							<td>
								<div>
									<input id="optInOutIndBox" name="optInOutIndBox" type="checkbox" value="true">	
								</div>
								<form:hidden id="optInOutIndHidden" path="optInOutInd" />
							</td>
							</tr>
							<tr>
								<td style="background-color: #6CA9F5;color: #fff;">&nbsp;</td>
							</tr>
							</table>
					</c:when>	
					<c:otherwise>
						<div align="left">
							<form:radiobutton id="optInOutInd1" path="optInOutInd" value="out"/><spring:message code="lts.acq.personalInfo.optOutAll" htmlEscape="false"/>
							<form:radiobutton id="optInOutInd2" path="optInOutInd" value="in"/><spring:message code="lts.acq.personalInfo.optInAll" htmlEscape="false"/>							
							<div style="display: none;">
								<form:radiobutton id="optInOutInd3" path="optInOutInd" value=""/>
							</div>
							
							<form:errors path="optInOutInd" cssClass="error"/>	
						</div>
					</c:otherwise>
				</c:choose>
			
				<!-- 
				<div align="left">
				    <form:checkbox path="agreement" id="agreement"/>
				     I do not want to receive any future promotion gifts, discounts, offers or materials as stated in the customer's agreement.  
				</div>
				-->
				 </td>
				 </tr>
			   </table></td></tr>
			   </table>
<br>

<c:if test="${isNowDrgDownTime}">
	<div id="warning_drgDownTime" class="ui-widget" style="visibility: visible;">
			<div class="ui-state-highlight ui-corner-all" style="padding: 0 .7em; margin-left: 25%; width: 50%;">
				<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				</p>
				<div id="drgDownTimeMsg" class="contenttext">
					<spring:message code="lts.acq.address.DRGDowntime" htmlEscape="false"/>:<br/>
					<ul>
						<li><spring:message code="lts.acq.address.notAllowSignOff" htmlEscape="false"/></li>
						<li><spring:message code="lts.acq.address.salesNeedRecall" htmlEscape="false"/></li>
					</ul>
				</div>
			<p></p>
		</div>
	</div>
</c:if>

<br>
<table width="100%" border="0" cellspacing="0" class="paper_w2">
	<tr>
		<td align="right">
			<a id="submit" href="#" ><div class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a>
		</td>
	</tr>
</table>

</form:form> 

<c:if test="${ not empty requestScope.errorMsgList }">
<div id="errorMsgDiv">
<br>
	<table width="90%" border="0">
		<tr>
			<td width="10%">&nbsp;</td>											
			<td width="80%" align="left">
				<div id="error_Msg_div" class="ui-widget" style="visibility: visible;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<c:forEach items="${requestScope.errorMsgList }" var="errorMsg">
							<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
								<div  class="contenttext">
									<c:out value="${errorMsg}"></c:out>
								</div>
							<p></p>
						</c:forEach>
					</div>
				</div>													
			</td>
			<td width="10%">&nbsp;</td>
		</tr>
	</table>
</div>
</c:if>


<script language="javascript">
	var isRetail = "${sessionScope.bomsalesuser.channelId}" == "1";
	var isCc = "${sessionScope.bomsalesuser.channelId}" == "2";
	var isFrontline = "${sessionScope.bomsalesuser.category}" == "FRONTLINE";
	var isDs = "${sessionScope.bomsalesuser.channelId}" == "12" || "${sessionScope.bomsalesuser.channelId}" == "13" ;
	var allowThirdParty = "${ltspersonalinfo.allowThirdParty}" == "true";
	var isAlertUpdContactMsg = true;
	var salesName = "${sessionScope.bomsalesuser.username}";
	
	var titleInSelectVal = '<spring:message code="lts.acq.personalInfo.titleInSelector" htmlEscape="false"/>';
	var mrVal = '<spring:message code="lts.acq.personalInfo.mr" htmlEscape="false"/>';
	var msVal = '<spring:message code="lts.acq.personalInfo.ms" htmlEscape="false"/>';
	
	var typeInSelectVal = '<spring:message code="lts.acq.personalInfo.typeInSelector" htmlEscape="false"/>';
	var hkidVal = '<spring:message code="lts.acq.personalInfo.HKID" htmlEscape="false"/>';
	var passVal = '<spring:message code="lts.acq.personalInfo.passport" htmlEscape="false"/>';
	var hkbrVal = '<spring:message code="lts.acq.personalInfo.HKBR" htmlEscape="false"/>';
	
	var selectInSelectVal = '<spring:message code="lts.acq.personalInfo.selectInSelector" htmlEscape="false"/>';
	var daugtherVal = '<spring:message code="lts.acq.personalInfo.daugther" htmlEscape="false"/>';
	var fatherVal = '<spring:message code="lts.acq.personalInfo.father" htmlEscape="false"/>';
	var husbandVal = '<spring:message code="lts.acq.personalInfo.husband" htmlEscape="false"/>';
	var motherVal = '<spring:message code="lts.acq.personalInfo.mother" htmlEscape="false"/>';
	var sonVal = '<spring:message code="lts.acq.personalInfo.son" htmlEscape="false"/>';
	var wifeVal = '<spring:message code="lts.acq.personalInfo.wife" htmlEscape="false"/>';
	var companyInChargeVal = '<spring:message code="lts.acq.personalInfo.companyInCharge" htmlEscape="false"/>';
	var secretaryVal = '<spring:message code="lts.acq.personalInfo.secretary" htmlEscape="false"/>';
	
	var verifyDocAlert = '<spring:message code="lts.acq.personalInfo.verifyDocumentNumber" htmlEscape="false"/>';
	var docNumNullAlert = '<spring:message code="lts.acq.personalInfo.plsInputDocumentNumber" htmlEscape="false"/>';
	var familyNameNullAlert = '<spring:message code="lts.acq.personalInfo.plsInputFamilyName" htmlEscape="false"/>';
	var givenNameNullAlert = '<spring:message code="lts.acq.personalInfo.plsInputGivenName" htmlEscape="false"/>';
	
	var cannotWIPprofile = '<spring:message code="lts.acq.personalInfo.cannotWIPprofile" htmlEscape="false"/>';
	var specialCustomerRemark = '<spring:message code="lts.acq.personalInfo.specialCustomerRemark" htmlEscape="false"/>';
	var customerBlacklisted = '<spring:message code="lts.acq.personalInfo.customerBlacklisted" htmlEscape="false"/>';
	var warningWIPprofile = '<spring:message code="lts.acq.personalInfo.warningWIPprofile" htmlEscape="false"/>';
	var noCustomerInfoWithDocumentID1 = '<spring:message code="lts.acq.personalInfo.noCustomerInfoWithDocumentID1" htmlEscape="false"/>';
	var noCustomerInfoWithDocumentID2 = '<spring:message code="lts.acq.personalInfo.noCustomerInfoWithDocumentID2" htmlEscape="false"/>';
	var invalidDocumentId = '<spring:message code="lts.acq.personalInfo.invalidDocumentId" htmlEscape="false"/>';
	var LTSandIMSnameNOTmatch = '<spring:message code="lts.acq.personalInfo.LTSandIMSnameNOTmatch" htmlEscape="false"/>';
	var LTSnameNOTmatch = '<spring:message code="lts.acq.personalInfo.LTSnameNOTmatch" htmlEscape="false"/>';
	var IMSnameNOTmatch = '<spring:message code="lts.acq.personalInfo.IMSnameNOTmatch" htmlEscape="false"/>';
	var missingUser = '<spring:message code="lts.acq.personalInfo.missingUser" htmlEscape="false"/>';
	var invalidDocumentNo = '<spring:message code="lts.acq.personalInfo.invalidDocumentNo" htmlEscape="false"/>';
	var alertContactBlank = '<spring:message code="lts.acq.personalInfo.alertContactBlank" htmlEscape="false"/>';
	var alertUpdContact = '<spring:message code="lts.acq.personalInfo.alertUpdContact" htmlEscape="false"/>';

	
	
	$(ltsacqpersonalinfo.actionPerform);
	
	if(document.getElementById("verified.errors")!=null)
		document.getElementById("verified.errors").textContent = verifyDocAlert;
	if(document.getElementById("docNum.errors")!=null)
		document.getElementById("docNum.errors").textContent = docNumNullAlert;
	if(document.getElementById("familyName.errors")!=null)
		document.getElementById("familyName.errors").textContent = familyNameNullAlert;
	if(document.getElementById("givenName.errors")!=null)
		document.getElementById("givenName.errors").textContent = givenNameNullAlert;
	if(document.getElementById("thirdPartyFamilyName.errors")!=null)
		document.getElementById("thirdPartyFamilyName.errors").textContent = familyNameNullAlert;
	if(document.getElementById("thirdPartyGivenName.errors")!=null)
		document.getElementById("thirdPartyGivenName.errors").textContent = givenNameNullAlert;
	if(document.getElementById("thirdPartyDocId.errors")!=null)
		document.getElementById("thirdPartyDocId.errors").textContent = docNumNullAlert;
	
	var x = document.getElementById("title");
	var i;
	for (i = 0; i < x.length; i++) {
		if(x.options[i].text=='-TITLE-')
		{
		   x.options[i].text=titleInSelectVal;
		}
		else if (x.options[i].text=='Mr')
		{
			x.options[i].text=mrVal;
		}
		else if (x.options[i].text=='Ms')
		{
			x.options[i].text=msVal;
		}
	}
	
	var y = document.getElementById("thirdPartyTitle");
	var j; 
	for (j = 0; j < y.length; j++) {
		if(y.options[j].text=='-TITLE-')
		{
		   y.options[j].text=titleInSelectVal;
		}
		else if (y.options[j].text=='Mr')
		{
			y.options[j].text=mrVal;
		}
		else if (y.options[j].text=='Miss')
		{
			y.options[j].text=msVal;
		}
	}
	
	var z = document.getElementById("thirdPartyRelationship");
	var k;
	for (k = 0; k < z.length; k++) {
		if(z.options[k].text=='-- SELECT --')
		{
		   z.options[k].text=selectInSelectVal;
		}
		else if (z.options[k].text=='Daugther')
		{
			z.options[k].text=daugtherVal;
		}
		else if (z.options[k].text=='Father')
		{
			z.options[k].text=fatherVal;
		}
		else if (z.options[k].text=='Husband')
		{
			z.options[k].text=husbandVal;
		}
		else if (z.options[k].text=='Mother')
		{
			z.options[k].text=motherVal;
		}
		else if (z.options[k].text=='Son')
		{
			z.options[k].text=sonVal;
		}
		else if (z.options[k].text=='Wife')
		{
			z.options[k].text=wifeVal;
		}
		else if (z.options[k].text=='Company in-charge')
		{
			z.options[k].text=companyInChargeVal;
		}
		else if (z.options[k].text=='Secretary')
		{
			z.options[k].text=secretaryVal;
		}
	}
	
	function optInfoCheck(){
		var answer = confirm('<spring:message code="lts.acq.personalInfo.goOptOutSection" htmlEscape="false"/>');
		if (answer){
			$("#optInOutNewDiv").show();			
			return;
		}else{
			return;
		}
	}
	
	$("#optInOutIndBox").click(function() {
	   if($("#optInOutIndBox").attr('checked')) {
		   $("#optInOutIndHidden").val("out");
	   } else {
		   $("#optInOutIndHidden").val("in");
	   }
	});
	
	var documentWidth = $(document).width();
	$('#pics_tbl').width(documentWidth*0.98);
	
	$("#optInOutIndHidden").val("in");
		
</script>


<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%> 