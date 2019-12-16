<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltsmiscellaneous.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="2" />
</jsp:include>

<form:form method="POST" id="ltsMiscellaneousForm" name="ltsMiscellaneousForm" commandName="ltsMiscellaneousCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="profilePremiumTp"/>
<form:hidden path="maxBackDateDays"/>
<form:hidden path="barUpgradeEye"/>
<form:hidden path="termPlanBarSlot"/>

<table width="98%" align="center">
	<tr>
		<td>
		<div class="paper_w2 round_10">
		<br/>
		<table width="98%" align="center">
		<tr>
			<td>
				<div id="s_line_text"><spring:message code="lts.mis.misc" text="Miscellaneous"/></div>
			</td>
		</tr>
		</table>
		<table width="90%" border="0" cellspacing="4" cellpadding="6" align="center">
			<tr>
				<td width="15%">&nbsp;</td>
				<td width="35%"><spring:message code="lts.mis.appDate" text="Application Date"/><span class="contenttext_red">*</span> : </td>
				<td width="50%" colspan="3">
					<form:input path="applicationDate" readonly="true"/>
				</td>
			</tr>
			
			<c:if test="${ltsMiscellaneousCmd.allowBackDateOrder}">
				<tr>
					<td>&nbsp;</td>
					<td>Back Date Order : </td>
					<td colspan="3" class="bold">
						<form:radiobutton path="backDateOrder" value="true" label="Yes" />
						&nbsp;
						<form:radiobutton path="backDateOrder" value="false" label="No"/>
					</td>
				</tr>
				<tr id="backDateApplicationDate" style="display: none;">
					<td>&nbsp;</td>
					<td><spring:message code="lts.mis.backAppDate" text="Back Date Application Date"/> : </td>
					<td colspan="3" class="bold">
						<form:input id="backDate" path="backDate" readonly="true"/>
						<form:errors path="backDate" cssClass="error" />
					</td>
				</tr>
			</c:if>
			<c:if test="${ltsMiscellaneousCmd.allowSubmitDisForm }">
				<tr>
					<td>&nbsp;</td>
					<td><spring:message code="lts.mis.submitDForm" text="Submit D-Form"/> : </td>
					<td colspan="3" class="bold">
						<form:radiobutton path="submitDisForm" value="true" label="Yes"/>
						&nbsp;
						<form:radiobutton path="submitDisForm" value="false" label="No"/>
					</td>
				</tr>
				<tr class="dForm" style="display: none;">
					<td>&nbsp;</td>
					<td><spring:message code="lts.mis.dFormSerial" text="D-Form Serial"/> : </td>
					<td class="bold">
						<form:input path="disFormSerial" />
					</td>
					<td colspan="2" ><form:errors path="disFormSerial" cssClass="error" /></td>
				</tr>
				<tr class="dForm" style="display: none;">
					<td>&nbsp;</td>
					<td><spring:message code="lts.mis.waiveDFormReason" text="Waive D-Form Reason"/> : </td>
					<td class="bold">
						<form:select path="waiveDisFormReason"  >
							<form:option value="" label="--SELECT--" />
							<form:options items="${waiveDFormReasonList}" itemValue="itemKey" itemLabel="itemValue"/>
						</form:select>
						
					</td>
					<td colspan="2" >
						<form:errors path="waiveDisFormReason" cssClass="error" />
					</td>
				</tr>
			</c:if>
			<!-- 
			<c:if test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' && !sessionScope.sessionLtsOrderCapture.channelRetail }">
				<tr>
					<td>&nbsp;</td>
					<td>Change Plan + Waive Penalty (Complaint Case): </td>
					<td colspan="3" class="bold">
						<form:radiobutton path="switchTp" value="true" label="Yes"/>
						&nbsp;
						<form:radiobutton path="switchTp" value="false" label="No"/>
					</td>
				</tr>
			</c:if>
			 -->		
			<tr>
				<td>&nbsp;</td>
				<td><spring:message code="lts.mis.recontract" text="Recontract"/> : </td>
				<td colspan="3" class="bold">
					<c:if test="${isEyeUpgradeToEye || isBrCustomer}" >
						<form:radiobutton path="recontract" value="true" label="Yes" cssClass="restrict" />
					</c:if> 
					<c:if test="${!isEyeUpgradeToEye && !isBrCustomer}">
						<form:radiobutton path="recontract" value="true" label="Yes" />
					</c:if>
					&nbsp;
					<form:radiobutton path="recontract" value="false" label="No"/>
					<form:errors path="recontract" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><spring:message code="lts.mis.dnChn" text="DN Change"/> : </td>
				<td colspan="3" class="bold">					
					<form:radiobutton path="dnChange" value="true" label="Yes" />
					&nbsp;
					<form:radiobutton path="dnChange" value="false" label="No"/>
					<form:errors path="dnChange" cssClass="error"/>
				</td>
			</tr>
			<!-- 
			<tr>
				<td align="right">BA/CA Change : </td>
				<td colspan="3" class="bold">
					<form:radiobutton path="baCaChange" value="true" label="Yes" cssClass="restrict"/>
					<form:radiobutton path="baCaChange" value="false" label="No"/>
				</td>
			</tr>
			 -->
			 <c:if test="${!(sessionScope.sessionLtsOrderCapture.orderType == 'SBR' && empty sessionScope.sessionLtsOrderCapture.ltsServiceProfile.existEyeType) }">
			<tr>
				<td>&nbsp;</td>
				<td><spring:message code="lts.mis.useDup" text="Use Duplex No. for Eye installation"/> : </td>
				<td colspan="3" class="bold">
					<form:radiobutton path="useDuplexNumAsNewEye" value="true" label="Yes" cssClass="restrict"/>
					&nbsp;
					<form:radiobutton path="useDuplexNumAsNewEye" value="false" label="No"/>
				</td>
			</tr>
			</c:if>
			<c:if test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBU'}">
				<c:choose>
					<c:when test="${ltsMiscellaneousCmd.profilePremiumTp && empty sessionScope.sessionLtsOrderCapture.ltsServiceProfile.existEyeType }">
						<tr>
						<td>&nbsp;</td>
						<td>
							<spring:message code="lts.mis.redeemPrevPremium" text="Redeem Previous Premium"/> : 
						</td>
						<td colspan="3" class="bold">
							<form:radiobutton path="redeemPrevPremium" value="true" label="Yes"/>
							&nbsp;
							<form:radiobutton path="redeemPrevPremium" value="false" label="No"/>
						</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty sessionScope.sessionLtsOrderCapture.ltsServiceProfile.existEyeType && sessionScope.bomsalesuser.channelCd == 'CSS'}">
							<tr>
							<td>&nbsp;</td>
							<td>
								No eye stock case / Repeated faults case :
								<br>
								(For FLTS only, selection “No” to SM approval) 
							</td>
							<td colspan="3" class="bold">
								<form:radiobutton path="redeemPrevPremium" value="true" label="Yes"/>
								&nbsp;
								<form:radiobutton path="redeemPrevPremium" value="false" label="No"/>
							</td>
							</tr>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:if>
			<tr>
				<td colspan="5" width="70%">
					<div id="errorMsgDiv" class="ui-widget" style="visibility: visible; display: none;">
						<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
							<div id="error_profile_msg" class="contenttext">
								<div id="recontractError" style="display: none;">
									<c:if test="${isBrCustomer}" >
										<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
										</p>
										<span style="color: red;">Recontract is not allowed for BR Customer.</span>
									</c:if>
									<c:if test="${isEyeUpgradeToEye}" >
										<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
										</p>
										<span style="color: red;">Recontract is not allowed for eye retention or eye upgrade to eye.</span>
									</c:if>
								</div>
								<div id="dnChangeError" style="display: none;">
								<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
									<span style="color: red;">DN change is not supported in Springboard, please use written contract.</span>
								</div>
								<div id="baCaChangeError" style="display: none;">
								<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
									<span style="color: red;">Please use BOM to change BA/CA first.</span>
								</div>
								<div id="duplexNewEyeError" style="display: none;">
								<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
									<span style="color: red;">Duplex No. for eye installation is not supported in Springboard, please use written contract.</span>
								</div>
								
								<div id="termPlanBarError" style="display: none;">
								<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
									<span style="color: red;">Not allowed to upgrade to eye due to current DEL/ eye term plan with start date within ${ltsMiscellaneousCmd.termPlanBarSlot} months.
									</span>
								</div>
								
								<div id="changeExist123TvOrMirrorError" style="display: none;">
								<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
									<span style="color: red;">Change Existing 1/2/3 TV or Mirror is not supported in Springboard.</span>
								</div>
							</div>
							<p></p>
						</div>
					</div>
					<div id="warningMsgDiv" class="ui-widget" style="visibility: visible; display: none;">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
						<div id="warning_addr_msg" class="contenttext">
							Approval required for 24 months contract. Please don't sign off the order.
							</div>
							<p></p>
						</div>
					</div>						
				</td>
				<td width="40%">&nbsp;</td>
			</tr>		
		</table>
		</div>		
		</td>
	</tr>
</table>
<br>
</form:form>

<br>
<div id="continueButton" >
	<table width="100%">
		<tr><td align="right">
			<div id="approvalRemarkDiv" style="display: none;" class="func_button">
				<a id="approvalRemarkBtn" href="#" class="nextbutton"><spring:message code="lts.mis.approvalRemark" text="Approval Remark"/></a>
			</div> 
			<div class="button">
				<a href="#" id="continueBtn"><spring:message code="lts.mis.next" text="Next"/></a>
			</div>
		</td></tr>
	</table>
</div>
<br>
<script type="text/javascript">
	$(ltsmiscellaneous.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>