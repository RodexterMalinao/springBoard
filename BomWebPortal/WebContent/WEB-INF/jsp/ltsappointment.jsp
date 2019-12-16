<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 8 : 9}" />
</jsp:include>

<script src="js/jquery-ui-1.8.16.custom.min.js"></script>

<div class="cosContent">
<form:form method="POST" action="#" id="appointmentform" name="ltsAppointmentForm" commandName="ltsappointment" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />

	<form:hidden id="sharePCDInd" path="sharePCDInd" />
	<form:hidden id="cutOverInd" path="cutOverInd" />
	<form:hidden id="secDelInd" path="secDelInd" />
	<form:hidden id="secDelCutOverInd" path="secDelCutOverInd" />
	<form:hidden id="bbShortageInd" path="bbShortageInd" />
	<form:hidden id="pcdOrderExistInd" path="pcdOrderExistInd" />
	<form:hidden id="custDiffInd" path="custDiffInd" />
	<form:hidden id="iaDiffInd" path="iaDiffInd" />
	<form:hidden id="allowCustConfirmInd" path="allowCustConfirmInd" />
	<form:hidden id="allowIaConfirmInd" path="allowIaConfirmInd" />
	<form:hidden id="submitInd" path="submitInd" />
	<form:hidden id="confirmedInd" path="confirmedInd" />
	<form:hidden id="secDelFieldVisitRequired" path="secDelFieldVisitRequired" />
	<form:hidden id="fieldVisitRequired" path="fieldVisitRequired" />

<div id="adjustmentCalDiv" class="paper_w2 round_10" style="display: none;">
<table width="98%" align="center">
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td>
						<br>
						<div id="s_line_text"><spring:message code="lts.appointment.adjustCalc" text="Adjustment Calculator"/></div>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td width="5%">&nbsp;</td>
					<td width="20%">
						<spring:message code="lts.appointment.startDate" text="Start Date"/> :
					</td>
					<td colspan="2" width="75%">
						<form:input path="adjStartDate" id="adjStartDate" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						 <spring:message code="lts.appointment.endDate" text="End Date"/> :
					</td>
					<td colspan="2" >
						<form:input path="adjEndDate" id="adjEndDate" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						 <spring:message code="lts.appointment.amount" text="Amount"/> :
					</td>
					<td colspan="2" >
						<form:input path="adjAmount" id="adjAmount"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						 <spring:message code="lts.appointment.adjustAmount" text="Adjustment Amount"/> :
					</td>
					<td colspan="2">
						<form:input path="adjResult" id="adjResult" readonly="true"/>
						&nbsp;
						<a href="#" id="adjCal"><div class="func_button"><spring:message code="lts.appointment.calculate" text="Calculate"/></div></a>
						&nbsp;
						<a href="#" id="adjCalClear"><div class="func_button"><spring:message code="lts.appointment.clear" text="Clear"/></div></a>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>				
			</table>
			</td>
		</tr>
	</table>
	</div>
<br>

<div id="appointmentDiv" class="paper_w2 round_10">
	<br>
	
	<table width="98%" align="center">
	<tr>
		<td>
		<div id="s_line_text"><spring:message code="lts.appointment.appnt" text="Appointment"/></div>
		
		<c:if test='${showPcdOrderId}'>
		<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<c:if test='${showPcdOrderId}'>
				<tr>
					<td width="25%">
						<div align="right">
							<spring:message code="lts.appointment.prdOrdId" text="Springboard PCD Order ID"/><span class="contenttext_red">*</span> :
						</div>
					</td>
					<td width="25%" >
						<form:input path="PCDOrderId" id="pcdOrderId" />
					</td>
					<td colspan="2">
						<input type="button" id="searchPcdOrderBtn" value="Search">
						<input type="button" id="clearPcdOrderBtn" value="Clear">
					</td>
				</tr>
				<tr>
					<td width="25%">&nbsp;</td>
					<td width="40%" colspan="2">
						<div id="pcdOrderNotFoundDiv" style="display: none;">
							<div id="error_profile" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_profile_msg" class="contenttext">
										<b><spring:message code="lts.appointment.sbOrdNotFound" text="Springboard order not found."/></b>
									</div>
									<p></p>
								</div>
							</div>
						</div>
						<div id="pcdOrderNotSameCustDiv" style="display: none;">
							<div id="error_profile" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_profile_msg" class="contenttext">
										<b><spring:message code="lts.appointment.sbOrdNotSameCust" text="Springboard Order Not Under Same Customer"/></b>
									</div>
									<p></p>
								</div>
							</div>
						</div>
						<div id="pcdOrderNotSameAddrDiv" style="display: none;">
							<div id="error_profile" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_profile_msg" class="contenttext">
										<b><spring:message code="lts.appointment.sbOrdNotSameAddr" text="Springboard Order Not At Same Address"/></b>
									</div>
									<p></p>
								</div>
							</div>
						</div>
						<div id="pcdOrderRetrievedDiv" style="display:none;">
							<div id="warning_addr" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="warning_addr_msg" class="contenttext">
									</div>
								<p></p>
							</div>
						</div>
					</div>
					<div id="pcdOrderConfirmDiv" style="display:none;">
						<p>
							<spring:message code="lts.appointment.custConfrim" text="Customer Confirm"/>: 
							<form:checkbox path="confirmCust" id="confirmCust"/>
							<spring:message code="lts.appointment.addrConfirm" text="Address Confirm"/>: 
							<form:checkbox path="confirmIa" id="confirmIa"/>
							<form:errors path="confirmCust" cssClass="error"/>
						</p>
					</div>
				</td>
				<td>&nbsp;</td>
			</tr>
			
			</c:if>
		</table>
		</c:if>
		
		<c:if test="${technologyChange}">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
				<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				</p>
				<div class="contenttext">
					<b><spring:message code="lts.appointment.chgVDSLPONMsg" text="Existing \"standalone eye\" or \"share with PCD / TV\" will be changed to VDSL / PON modem, please remind customer may needs to change modem"/></b>
				</div>
				<p></p>
			</div>
		</c:if>
		
		<table id="booking" width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
			<tr>
			<td colspan="4">
				<table width="85%">
					<tr id="earliest_SRD">
					<td>
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
							<p>
								<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							</p>
							<div class="contenttext">
								<b><spring:message code="lts.appointment.estEarliestSrd" text="Estimated Earliest SRD"/> : ${ltsappointment.earliestSrdReason}</b>
							</div>
							<c:if test="${ltsappointment.secDelEarliestSRD != null}">
									<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
									</p>
									<div class="contenttext">
									<b><spring:message code="lts.appointment.estEarliest2DELSrd" text="Estimated Earliest 2ndDel SRD"/> : ${ltsappointment.secDelEarliestSrdReason}</b>
									</div>
								</c:if>
							<p></p>
						</div>
					</td>
					</tr>
				</table>
				<div id="bbShortage_warning" style="display: none;">
				<table width="350">
					<tr><td>
						<div id="error_profile" class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="error_profile_msg" class="contenttext">
									<b><spring:message code="lts.appointment.bbShortageMsg" text="Warning: BB Shortage. SRD default to T+X."/></b>
								</div>
								<p></p>
							</div>
						</div>
					</td></tr>
				</table>
				</div>
			</td>
			</tr>
			<tr>
			<td colspan="2" width="45%">
			<table width="100%"  border="0" cellspacing="4" cellpadding="4">
			<tr id="serial">
				<td width="50%">
					<div align="right">
						<spring:message code="lts.appointment.prebookSerial" text="Pre-book Serial No."/> :
					</div>
				</td>
				<td>
					<form:input path="preBookSerialNum" id="preBookSerialNum" readonly="true"/>
					<br/><form:errors path="preBookSerialNum" cssClass="error"/>
				</td>
			</tr>

			<tr id="prewiring1" style="display: none;">
				<td>
					<div align="right">
						<spring:message code="lts.appointment.prewiringDate" text="Pre-wiring Date"/> :
					</div>
				</td>
				<td>
					<form:input path="preWiringDate" maxlength="10" id="preWiringDate" />
					<br/><form:errors path="preWiringDate" cssClass="error"/>
				</td>
			</tr>			

			<tr id="prewiring2" style="display: none;">
				<td>
					<div align="right">
						<spring:message code="lts.appointment.prewiringTime" text="Pre-wiring Time"/> :
					</div>
				</td>
				<td>
					<form:select path="preWiringTime" id="preWiringTime">
						<form:option value=""> <spring:message code="lts.appointment.select" text="--- Select ---"/> </form:option>
						<form:options items="${preWiringTimeSlot}" itemValue="apptTimeSlot" itemLabel="apptTimeSlot"/>
					</form:select>
					<br/><form:errors path="preWiringTime" cssClass="error"/>
				</td>
			</tr>

			<c:if test='${allowAppt}'>
			<tr>
				<td>
					<div align="right">
					<c:choose>
						<c:when test="${ltsappointment.fieldVisitRequired}">
							<spring:message code="lts.appointment.targetInstDate" text="Target Installation Date"/>
						</c:when>
						<c:otherwise>
							<spring:message code="lts.appointment.newContractEffDate" text="New Contract Effective Date"/>
						</c:otherwise>
					</c:choose>
						<span class="contenttext_red">*</span> :
					</div>
				</td>
				<td>
					<form:input path="installationDate" id="installationDate" maxlength="10" readonly="true"/>
					<br/><form:errors path="installationDate" cssClass="error"/>
				</td>
			</tr>		
			<tr style="${ltsappointment.fieldVisitRequired ?  '' : 'display: none;'}">
				<td>
					<div align="right">
					<c:choose>
						<c:when test="${ltsappointment.fieldVisitRequired}">
							<spring:message code="lts.appointment.targetinstTime" text="Target Installation Time"/>
						</c:when>
						<c:otherwise>
							<spring:message code="lts.appointment.newContractEffTime" text="New Contract Effective Time"/>
						</c:otherwise>
					</c:choose>
						<span class="contenttext_red">*</span> :
					</div>
				</td>
				<td>
					<c:choose>
						<c:when test="${!ltsappointment.fieldVisitRequired}">
							<form:hidden path="installationTime"/>
							<form:hidden path="installationType"/>
						</c:when>
						<c:when test="${ltsappointment.confirmedInd}">
							<input value="${ltsappointment.installationTime}" readonly="readonly">
						</c:when>
						<c:otherwise>
							<form:select id="installationTime" path="installationTime">
								<form:option value=""> <spring:message code="lts.appointment.select" text="--- Select ---"/> </form:option>
								<form:options items="${installationTimeSlot}" itemValue="apptTimeSlot" itemLabel="apptTimeSlot"/>
							</form:select>
							<div id="instTimeTypeDiv" style="display: none;">
							<form:select id="installationTimeType" path="installationType">
								<form:option value="" />
								<form:options items="${installationTimeSlot}" itemValue="apptTimeSlotType" itemLabel="apptTimeSlotType"/>
							</form:select>
							</div>
							<!--<form:hidden id="installationType" path="installationType"/>-->
							<br/><form:errors path="installationTime" cssClass="error"/>
						
						</c:otherwise>
					
					</c:choose>
				
							
				</td>
			</tr>
			</c:if>

			<c:if test="${!allowAppt}">
			<tr>
				<td>
					<div align="right">
						<c:choose>
							<c:when test="${ltsappointment.fieldVisitRequired}">
								<spring:message code="lts.appointment.targetInstDate" text="Target Installation Date"/>
							</c:when>
							<c:otherwise>
								<spring:message code="lts.appointment.newContractEffDate" text="New Contract Effective Date"/>
							</c:otherwise>
						</c:choose>
						<span class="contenttext_red">*</span> :
					</div>
				</td>
				<td>
					<form:input path="installationDate" id="installationDate" maxlength="10" readonly="true"/>
					<br/><form:errors path="installationDate" cssClass="error"/>
				</td>
			</tr>						
			<tr>
				<td>
					<div align="right">
						<c:choose>
							<c:when test="${ltsappointment.fieldVisitRequired}">
								<spring:message code="lts.appointment.targetinstTime" text="Target Installation Time"/>
							</c:when>
							<c:otherwise>
								<spring:message code="lts.appointment.newContractEffTime" text="New Contract Effective Time"/>
							</c:otherwise>
						</c:choose>
						<span class="contenttext_red">*</span> :
					</div>
				</td>
				<td>
					<form:input id="installationTime" path="installationTime" readonly="true"/>
					<form:hidden id="installationType" path="installationType"/>
					<br/><form:errors path="installationTime" cssClass="error"/>
					
				</td>
			</tr>
			</c:if>
			</table>
			</td>
			<td colspan="2" width="45%" valign="bottom">
			<div id="cutOver" style="display: none;">
			<table width="100%"  border="0" cellspacing="1" cellpadding="3">
			<tr>
				<td width="60%">
					<div align="right">
						<spring:message code="lts.appointment.targetNumSwitchDate" text="Target Date for telephone number switching"/><span class="contenttext_red">*</span> :
					</div>
				</td>
				<td>
					<form:input path="cutOverDate" id="cutOverDate" readonly="true"/>
					<form:errors path="cutOverDate" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<td >
					<div align="right">
						<spring:message code="lts.appointment.targetNumSwitchTime" text="Target Time for telephone number switching"/><span class="contenttext_red">*</span> :
					</div>
				</td>
				<td>
					<form:input path="cutOverTime" maxlength="10" id="cutOverTime" readonly="true"/>
					<form:errors path="cutOverTime" cssClass="error"/>
				</td>
			</tr>
			</table>
			</div>
			</td>
			</tr>
			<tr>
			<td colspan="2" width="45%">
			<div id="secDel" style="display: none;">
			<table width="100%"  border="0" cellspacing="1" cellpadding="3">
			<tr>
				<td width="50%">
					<div align="right">
						<c:choose>
							<c:when test="${ltsappointment.secDelFieldVisitRequired}">
								<spring:message code="lts.appointment.2DELInstDate" text="2nd Del Installation Date"/>
							</c:when>
							<c:otherwise>
								<spring:message code="lts.appointment.2DELEffDate" text="2nd Del Effective Date"/>
							</c:otherwise>
						</c:choose>
						<span class="contenttext_red">*</span> :
					</div>
				</td>
				<td>
					<form:input path="secDelInstallationDate" id="secDelInstallationDate" readonly="true"/>
					<form:errors path="secDelInstallationDate" cssClass="error"/>
				</td>
			</tr>
			<c:choose>
			<c:when test="${ltsappointment.secDelFieldVisitRequired }">
				<tr>
					<td width="50%">
						<div align="right">
							<spring:message code="lts.appointment.2DELInstTime" text="2nd Del Installation Time"/><span class="contenttext_red">*</span> :
						</div>
					</td>
					<td>
					<form:select id="secDelInstallationTime" path="secDelInstallationTime">
							<form:option value=""> <spring:message code="lts.appointment.select" text="--- Select ---"/> </form:option>
							<form:options items="${secDelTimeSlot}" itemValue="apptTimeSlot" itemLabel="apptTimeSlot"/>
					</form:select>
					
					<div id="secDelInstTimeTypeDiv" style="display: none;">
					<form:select id="secDelInstallationTimeType" path="secDelInstallationType">
							<form:option value="" />
							<form:options items="${secDelTimeSlot}" itemValue="apptTimeSlotType" itemLabel="apptTimeSlotType"/>
					</form:select>
					</div>
					<form:errors path="secDelInstallationTime" cssClass="error"/>
					</td>
				</tr>		
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="2">
						<form:hidden id="secDelInstallationTime" path="secDelInstallationTime"/>
						<form:hidden id="secDelInstallationTimeType" path="secDelInstallationType"/>
					</td>
				</tr>
			</c:otherwise>
			</c:choose>			
			</table>
			</div>
			</td>
			
			<td colspan="2" width="45%">
			<div id="secDelCutOver" style="display: none;">
			<table width="100%"  border="0" cellspacing="1" cellpadding="3">
			<tr>
				<td width="60%">
					<div align="right">
						<spring:message code="lts.appointment.2DELtargetNumSwitchDate" text="Target Date for 2nd Del telephone number switching"/><span class="contenttext_red">*</span> :
					</div>
				</td>
				<td>
					<form:input path="secDelCutOverDate" id="secDelCutOverDate" readonly="true"/>
					<form:errors path="secDelCutOverDate" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<td>
					<div align="right">
						<spring:message code="lts.appointment.2DELtargetNumSwitchTime" text="Target Time for 2nd Del telephone number switching"/><span class="contenttext_red">*</span> :
					</div>
				</td>
				<td>
				<form:input path="secDelCutOverTime" maxlength="10" id="secDelCutOverTime" readonly="true"/>
				<form:errors path="secDelCutOverTime" cssClass="error"/>
				</td>
			</tr>		
			
			</table>
			</div>
			</td>
			</tr>
			
			<tr>
			<td colspan="2" width="45%">
			<table width="100%"  border="0" cellspacing="1" cellpadding="3">
			<tr>
				<td width="50%">
				</td>
				<td>
				
				<div id="confirmMsgBlock" style="display:none;">
					<div id="warning_addr" class="ui-widget" style="visibility: visible;">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
							<p>
								<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							</p>
							<div id="comfirm_msg" class="contenttext">
								<b><spring:message code="lts.appointment.appntConfirm" text="Appointment Confirmed"/></b>
							</div>
							<p></p>
						</div>
					</div>
				</div>
				<div id="cancelMsgBlock" style="display: none;">
					<div id="error_profile" class="ui-widget" style="visibility: visible;">
						<div class="ui-state-error ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
							<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							</p>
							<div id="cancel_msg" class="contenttext">
								<b>${ltsappointment.errorMsg}</b>
							</div>
							<p></p>
						</div>
					</div>
				</div>
				</td>
			</tr>
			<tr>
				<td width="50%">
				</td>
				<td>
					<input type="button" value="Confirm" id="confirmAppntBtn" />
					<input type="button" value="Cancel" id="cancelAppntBtn" />
					<br/>
					<form:errors path="confirmedInd" cssClass="error"/>
				</td>
			</tr>
			<c:if test="${ltsappointment.allowAdjustment }">
			<tr>
				<td width="50%">
				</td>
				<td>
					<a href="#" id="showAdjCalBtn"><div class="func_button"><spring:message code="lts.appointment.showAdjustCalc" text="Show Adjustment Calculator"/></div></a>
					<a href="#" id="hideAdjCalBtn" style="display: none;"><div class="func_button"><spring:message code="lts.appointment.hideAdjustCalc" text="Hide Adjustment Calculator"/></div></a>
				</td>
			</tr>
			</c:if>
			</table>
			</td>
			<td colspan="2" width="45%">
			<table width="80%"  border="0" cellspacing="1" cellpadding="3">
			<tr>
			<td>
			<div id="cutOverDiffDiv" style="display: none;">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
						<p>
							<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						</p>
						<div class="contenttext">
							<b><spring:message code="lts.appointment.switchDateDiffMsg" text="Tel No. Switching Date/Time is different from EYE Upgrade Installation."/></b>
						</div>
						<p></p>
					</div>
				</div>
			</td>
			</tr>
			</table>
			</td>
			</tr>
			
			<tr id="bmo">
				<td>
				<div id="bmoAlertDiv" style="display:none;">
					<div id="warning_addr" class="ui-widget" style="visibility: visible;">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
							<p>
								<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							</p>
							<div id="comfirm_msg" class="contenttext">
								<b>${ltsappointment.bmoRemark}</b><br/>
							</div>
							<p></p>
						</div>
					</div>
				</div>
				</td>
			</tr>
		</table>
		
		<table id="bmoRemarkTable" width="100%" border="0" cellspacing="4" cellpadding="4" class="contenttext">
		<tr>
				<td width="25%">
					<div align="right">
						<spring:message code="lts.appointment.bmoRmk" text="BMO Remarks :"/> : 
					</div>
				</td>
				<td colspan="3">
					<form:textarea path="bmoRemark" rows="5" cols="50%" readonly="true" cssStyle="resize:none;"></form:textarea>
				</td>
			</tr>
		</table>
		<table id="installationContact" width="100%" border="0" cellspacing="4" cellpadding="4" class="contenttext">
			<tr>
				<td width="25%">
					<div align="right">
						<spring:message code="lts.appointment.instContactName" text="Installation Contact Name"/><span class="contenttext_red">*</span> : 
					</div>
				</td>
				<td colspan="3">
					<form:input path="installationContactName" id="installationContactName"/>
					<form:errors path="installationContactName" cssClass="error"/>
				</td>
			</tr>
			
			<tr>
				<td width="25%">
					<div align="right">
						<spring:message code="lts.appointment.instContactNo" text="Installation Contact No."/><span class="contenttext_red">*</span> :
					</div>
				</td>
				<td width="10%">
					<form:input path="installationContactNum" id="installationContactNum" />
					<form:errors path="installationContactNum" cssClass="error"/>
				</td>
				<td colspan="2">
					<div class="func_button">
						<a href="#" id="copyContactNumBtn"><spring:message code="lts.appointment.copyToAll" text="Copy to All"/></a>
					</div>
				</td>
			</tr>
			<tr>
				<td width="25%">
					<div align="right">
						<spring:message code="lts.appointment.instContactMobNo" text="Installation Contact Mobile No. :"/> :
					</div>
				</td>
				<td colspan="3">
					<form:input id="installationMobileSMSAlert" path="installationMobileSMSAlert" />
					<form:errors path="installationMobileSMSAlert" cssClass="error"/>
				</td>
			</tr>
		</table>
		<br>
		</td>
	</tr>
	</table>
	<br>
	
	<!-- BOM2018061 -->
	<c:if test="${not empty ltsappointment.epdItemList}">
		<table id="Remark" width="98%" align="center">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td>
							<div id="s_line_text"><spring:message code="lts.appointment.weee" text="Waste Electrical and Electronic Equipment"/></div>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="1" cellpadding="3">
					<tr>
						<td colspan="4">
							<form:hidden id="defaultContactName" path="defaultContactName"/>
							<form:hidden id="defaultContactNum" path="defaultContactNum"/>
							<div style="margin: auto; width: 100em;">
								<c:if test='${not empty requestScope.epdErrorMsgList}'> 
									<div id="errorDiv" style="width: 50em; padding: 0px 10%;">
									<div id="error_profile" class="ui-widget" style="visibility: visible;">
									<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
										<c:forEach items="${requestScope.epdErrorMsgList}" var="errorMsg">
											<p>
												<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
											</p>
											<div id="error_profile_msg" class="contenttext">
												${errorMsg}
											</div>
											<p></p>
										</c:forEach>
									</div>
									</div>
									</div>
								</c:if>
								<form:errors path="epdItemList" cssClass="error"/>
								<c:forEach items="${ltsappointment.epdItemList}" var="epdItem" varStatus="status">
									<div style="padding: 5px 10%">
										<form:checkbox id="${epdItem.itemId}" 
											path="epdItemList[${status.index}].selected" 
											cssClass="epdCheckbox"
											onclick="onClickEpdOption('${epdItem.itemId}')"/>
										${epdItem.itemDesc}
										<br/>
									</div>
									<c:if test="${not empty epdItem.itemAttbs }">
										<div id="attbDiv${epdItem.itemId}" class="epdAttbDiv" style="${epdItem.selected? '' : 'display: none;'} ">
										<c:forEach items="${epdItem.itemAttbs}" var="itemAttb" varStatus="attbStatus">
											<c:if test="${itemAttb.visualInd != 'N'}">
												<div style="padding: 3px 15%">
												
												<span style="display: inline-block; vertical-align: top; margin: 0.3em 0px; width: 12em;">
													${itemAttb.attbDesc}
												</span>
												
												<c:if test="${itemAttb.inputMethod == 'INPUT'}">
													<c:choose>
														<c:when test="${itemAttb.inputFormat == 'DATE'}">
															<form:input path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" 
																id="datepicker${epdItem.itemId}${itemAttb.attbID}"  
																onmousedown="addEpdDatePicker('datepicker${epdItem.itemId}${itemAttb.attbID}', 'installationDate', ${ltsappointment.epdLeadDay})"
																readonly="true"/>
														</c:when>
														<c:when test="${itemAttb.inputFormat == 'CONTACT_NAME'}">
															<form:input id="epdContactName" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
															<div class="func_button">
																<a href="#" id="copyContactEpdBtn"><spring:message code="lts.appointment.copy" text="Copy"/></a>
															</div>
														</c:when>
														<c:when test="${itemAttb.inputFormat == 'CONTACT_NUM'}">
															<form:input id="epdContactNum" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
														</c:when>
														<c:when test="${itemAttb.inputFormat == 'REMARK'}">
															<form:textarea rows="2" path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" />
														</c:when>
														<c:otherwise>
															<form:input path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" maxlength="${itemAttb.maxLength}"/>
														</c:otherwise>
													</c:choose>
												</c:if>
												<c:if test="${itemAttb.inputMethod == 'SELECT'}">
													<c:if test="${not empty itemAttb.itemAttbInfoList && itemAttb.itemAttbInfoList != null}">	
														<form:select path="epdItemList[${status.index}].itemAttbs[${attbStatus.index}].attbValue" >
															<c:forEach items="${itemAttb.itemAttbInfoList}" var="itemAttbInfo">
																<form:option value="${itemAttbInfo.attbValue}" label="${itemAttbInfo.attbDesc}"/>
															</c:forEach>
														</form:select>
													</c:if>
												</c:if>
												</div>
											</c:if>
										</c:forEach>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
	</c:if>
	<!-- BOM2018061 END -->
	<c:if test="${not empty requestScope.errorMsgList || not empty param.errorMsg}">
		<table width="100%" border="0" cellspacing="1" cellpadding="1" class="contenttext">
			<tr>
				<td width="2%">&nbsp;</td>
				<td>
					<div id="error" class="ui-widget" style="visibility: visible; width: 70%;">
						<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
							<c:forEach items="${requestScope.errorMsgList}" var="errorMsg">
								<p>
									<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="error_msg" class="contenttext">
									${errorMsg}
								</div>
								<p></p>										
							</c:forEach>
							<c:if test="${not empty param.errorMsg}">
								<p>
									<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="error_msg" class="contenttext">
									${param.errorMsg}
								</div>
								<p></p>										
							</c:if>
						</div>
					</div>
				</td>
				<td width="5%">&nbsp;</td>
			</tr>
		</table>
	</c:if>
	<table id="Remark" width="98%" align="center">
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td>
						<div id="s_line_text"><spring:message code="lts.appointment.fieldSrvAddonRmk" text="Field Service Add-On Remark"/></div>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td width="10%">&nbsp;</td>
					<td colspan="3" width="100%">
						<form:errors path="remarks" cssClass="error"/>
						<form:textarea path="remarks" rows="10" cols="100%" cssStyle="resize:none;"></form:textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>				
			</table>
			</td>
		</tr>
	</table>
</div>

<br>
<table width="100%" border="0" cellspacing="0" class="contenttext">
<tr>
	<td align="right" > 
		<form:errors path="suspendReason" cssClass="error"/>
			<b><spring:message code="lts.payment.suspendReason" text="Suspend Reason"/> : </b>
		<form:select path="suspendReason" id="suspendReason">
			<form:option value="NONE"><spring:message code="lts.appointment.select" text="--- Select ---"/></form:option>
			<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
		</form:select>
		<a href="#" id="suspend" class="nextbutton"><div class="button"><spring:message code="lts.payment.suspend" text="Suspend"/></div></a>
		<a href="#" id="submit" class="nextbutton"><div class="button"><spring:message code="lts.mis.next" text="Next"/></div></a> 
	</td>
	
</tr>
</table>
</form:form>
</div>

<script type="text/javascript">

	//BOM2018061
	function onClickEpdOption(itemId){
		$(".epdCheckbox").not('#'+itemId).attr('checked', false);
		$(".epdAttbDiv").not('#attbDiv'+itemId).hide();
		hideShow("attbDiv"+itemId);
	}
	
	function hideShow(elementId){
		if($("#"+elementId).css("display") == "none"){
			$("#"+elementId).show();
		}else{
			$("#"+elementId).hide();
		}
	}

	function addEpdDatePicker(id, minDateRefId, minDateLeadDay){
		$('#'+id).datepicker( "destroy");
		
		var minDate = $("#"+minDateRefId).val();
		var maxDate = "+100Y";
		
		if(minDateLeadDay != ''){
			minDate = plusDate(minDate, minDateLeadDay);
		}
		
		$('#'+id).datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate: minDate, maxDate: maxDate, //Y is year, M is month, D is day  
			yearRange: "0:+100" //the range shown in the year selection box
		});
	}

	function plusDate(date, val){
		var newDate;
		if(date != ''){
			newDate = $.datepicker.parseDate('dd/mm/yy', date);
		}else{
			newDate = $.datepicker.parseDate('dd/mm/yy', $.datepicker.formatDate('dd/mm/yy', new Date()));
		}
		if(val != ''){
			newDate.setDate(newDate.getDate() + val);
		}
		return newDate;
	}
	//BOM2018061 END

	function pushTimeSlotOptions(json, lkupType, defaultValue){
		var slot = '';
		var type = '';
		if(lkupType == "P"){
			slot = '#installationTime';
			type = '#installationTimeType';
		}else if(lkupType == "I"){
			slot = '#installationTime';
			type = '#installationTimeType';
		}else if(lkupType == "S"){
			slot = '#secDelInstallationTime';
			type = '#secDelInstallationTimeType';
		}
		
		$('option', slot).remove();
		$('option', type).remove();
		$(slot).append(new Option(' -- SELECT --', '', true, false));
		$(type).append(new Option('', '', true, false));

		$.each(eval(json), function(i, item) {
			var timeslotoptions = $(slot).attr('options');
			var timetypeoptions = $(type).attr('options');
			if(item.tsvalue == defaultValue){
				timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, true);
			}else{
				timeslotoptions[timeslotoptions.length] = new Option(item.tsdisplay, item.tsvalue, true, false);
			}
			timetypeoptions[timetypeoptions.length] = new Option(item.tstype, item.tstype, true, false);
		});

		colorTimeSlot(lkupType);
		
		if(lkupType = "S"){
			refreshTimeSlotType();
		}
	}
	
	function colorTimeSlot(lkupType){
		var slot = '';
		var type = '';
		if(lkupType == "P"){
			slot = 'installationTime';
			type = 'installationTimeType';
		}else if(lkupType == "I"){
			slot = 'installationTime';
			type = 'installationTimeType';
		}else if(lkupType == "S"){
			slot = 'secDelInstallationTime';
			type = 'secDelInstallationTimeType';
		}
		
		var timeslotlist=document.getElementById(slot);
		var slottypelist=document.getElementById(type);
		var i;
		for (i=1; timeslotlist != null && i<timeslotlist.length;i++){//skip first element			
			/*if(document.getElementById("instdate").value==instdate &&
					timeslotlist.options[i].value==insttime){
				slottypelist.options[i].value="CURRENT_SELECTED";
				timeslotlist.options[i].selected=true;
				slottypelist.options[i].selected=true;
			}else */
			if(slottypelist.options[i].value=="NS"){ //indicate no resource
				timeslotlist.options[i].style.color="red";
			}else if(slottypelist.options[i].value=="RS"){//restricted
				timeslotlist.options[i].style.color="silver";
			}else{
				timeslotlist.options[i].style.color="black";
			}
		}		
	}
	
	function refreshTimeSlot(changedSlot){
		var successInd = "";
		var instDate = $('#installationDate').val();
		var instTime = $('#installationTime').val();
		if($('#secDelInd').val() == "true"){
			var secDelInstDate = $('#secDelInstallationDate').val();
			var secDelInstTime = $('#secDelInstallationTime').val();
			if(instDate != ""){
				if(instDate == secDelInstDate){
					successInd = timeSlotLookup(instDate, "IA", instTime);
					successInd = timeSlotLookup(secDelInstDate, "SA", instTime);			
				}else{
					if(secDelInstDate == ""){
						var date_instDate = $.datepicker.parseDate('dd/mm/yy', instDate); 
						var date_secDelSRD = $.datepicker.parseDate('dd/mm/yy', "${ltsappointment.secDelEarliestSRD}"); 
						if(date_instDate > date_secDelSRD){
							$('#secDelInstallationDate').val(instDate);
							successInd = timeSlotLookup(instDate, "IA", instTime);
							successInd = timeSlotLookup(instDate, "SA", instTime);
						}else{
							$('#secDelInstallationDate').val(date_secDelSRD);
							successInd = timeSlotLookup(instDate, "IA", date_secDelSRD);
							successInd = timeSlotLookup(instDate, "SA", date_secDelSRD);
						}
					}else{
						successInd = timeSlotLookup(instDate, "I", instTime);
						successInd = timeSlotLookup(secDelInstDate, "S", secDelInstTime);
					}
				}
			}else{
				successInd = timeSlotLookup(secDelInstDate, "S", secDelInstTime);
			}
		}else{
			timeSlotLookup(instDate, "I", instTime);
		}
		refreshTimeSlotType();
		if(successInd=='parsererror'){
			alert("Your session has been timed out, please re-login.");	
		}
	}
	
	function refreshTimeSlotType(){
		var sharePCD = ${showPcdOrderId}; //$("#sharePCDInd").val();
		if(!sharePCD || ${allowAppt}){ 
			if ($("#installationTimeType option").length > 1) {
				$("#installationTimeType").get(0).selectedIndex = $("#installationTime").get(0).selectedIndex;	
			}
			
			
		}
		refreshCutOverTime();
	}
	
	function refreshCutOverTime(){
		var successInd = "";
		var cutOverInd = $('#cutOverInd').val();
		var secDelCutOverInd = $('#secDelCutOverInd').val();
		if(cutOverInd == "true" && $("#installationDate").val() != "" && $("#installationTime").val() != ""){
			successInd = findCutOverTimeSlot($("#installationDate").val(), $("#installationTime").val(), "C");
		}
		if($('#secDelInd').val() == "true"){
			$("#secDelInstallationTimeType").get(0).selectedIndex = $("#secDelInstallationTime").get(0).selectedIndex;
			if(secDelCutOverInd == "true" && $("#secDelInstallationDate").val() != "" && $("#secDelInstallationTime").val() != ""){
				successInd = findCutOverTimeSlot($("#secDelInstallationDate").val(), $("#secDelInstallationTime").val(), "SC");
			}
		}
		if(successInd=='parsererror'){
			alert("Your session has been timed out, please re-login.");	
		}	
	}
	
	function timeSlotLookup(date, type, defaultValue){
		var sbuid = $('input[name="sbuid"]').val();
		if(date.length == 10){
			$.ajax({
				url : "ltsappointmenttimeslotlookup.html?sbuid=" + sbuid,
				type : 'POST',
				data : "date=" + date + "&type=" + type ,
				dataType : 'json',
				//timeout : 5000,
				success : function(data){
					//var obj = jQuery.parseJSON(data);
					if(type == "IA"){
						pushTimeSlotOptions(data, "I", defaultValue);
					}else if(type == "SA"){
						pushTimeSlotOptions(data, "S", defaultValue);
					}else{
						pushTimeSlotOptions(data, type, defaultValue);
					}
					return 1;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					return textStatus;	
				},
				complete : function() {
					 $.unblockUI(); 
				},
				beforeSend : function() {
					 $.blockUI({ message: null }); 
				}
			});
		}
	}
	
	function findCutOverTimeSlot(date, timeSlot, type){
		var sbuid = $('input[name="sbuid"]').val();
		$.ajax({
			url : "ltsappointmenttimeslotlookup.html?sbuid=" + sbuid,
			type : 'POST',
			data : "date=" + date + "&timeSlot=" + timeSlot + "&type=" + type,
			timeout : 5000,
			success : function(data){
				var obj = jQuery.parseJSON(data);
				if(type == "C"){
					$("#cutOverDate").val(obj.date);
					$("#cutOverTime").val(obj.timeSlot);
					checkCutOverDiff();
				}else if(type == "SC"){
					$("#secDelCutOverDate").val(obj.date);
					$("#secDelCutOverTime").val(obj.timeSlot);
					checkCutOverDiff();
				}
				return 1;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				return textStatus;	
			},
			complete : function() {
				 $.unblockUI(); 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}
		});
	}
	
	function checkCutOverDiff(){
		var diffInd = false;
		var cutOverInd = $('#cutOverInd').val();
		var secDelCutOverInd = $('#secDelCutOverInd').val();
		
		if(cutOverInd == "true"){
			if($("#cutOverDate").val() != $("#installationDate").val()
					|| $("#cutOverTime").val() != $("#installationTime").val()){
				diffInd = true;
			}
		}
		if(secDelCutOverInd == "true"){
			if($("#secDelCutOverDate").val() != $("#secDelInstallationDate").val()
					|| $("#secDelCutOverTime").val() != $("#secDelInstallationTime").val()){
				diffInd = true;
			}
		}
		
		if(diffInd){
			$("#cutOverDiffDiv").show();
		}else{
			$("#cutOverDiffDiv").hide();
		}
		
	}
	
	// format dd/mm/yyyy
	function formattedDate(formatDate) {
	    var fromdate = new Date(formatDate);
	        var dd = fromdate.getDate();
	        var mm = fromdate.getMonth()+1; //January is 0!
	        var yyyy = fromdate.getFullYear();
	        if(dd < 10)
	        {
	            dd = '0'+ dd;
	        }
	        if(mm < 10)
	        {
	            mm = '0' + mm;
	        }
	     return dd+'/'+mm+'/'+yyyy;
	}
	
	function resetpreWiringDatePicker(){
		var preWiringMax;
		//var leadtime = parseInt('${leadtime}');
		//var skippedtime = parseInt('${skippedTime}');
		//var preWiringMin = leadtime + skippedtime;
		var srdDiff = parseInt('${srdDiffence}');
		if(srdDiff < 5){
			preWiringMin = 5;
		}else{
			preWiringMin = '${earliestSRD}';
		}
		if($('#installationDate').val() != ""){
			preWiringMax = $('#installationDate').datepicker('getDate')-1;
		}else{
			preWiringMax = '+100Y';
		}
		$('#preWiringDate').datepicker( "destroy");
		$('#preWiringDate').datepicker({
			changeMonth: true,	
			changeYear: true,
			defaultDate: preWiringMin,
			showButtonPanel: true,
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate: preWiringMin , maxDate: preWiringMax, //Y is year, M is month, D is day  
			yearRange: '0:+100' //the range shown in the year selection box
		});
	}

	function confirmDisplay(val){
		if(val == "confirm"){
			$('#confirmMsgBlock').show();
			$('#cancelMsgBlock').hide();
			$('#confirmAppntBtn').attr('disabled', true);
			$('#cancelAppntBtn').attr('disabled', false);
			$("#installationDate").attr('disabled', true);
			$("#installationTime").attr('disabled', true);
			$("#secDelInstallationDate").attr('disabled', true);
			$("#secDelInstallationTime").attr('disabled', true);
			$("#cutOverDate").attr('disabled', true);
			$("#cutOverTime").attr('disabled', true);
			$("#secDelCutOverDate").attr('disabled', true);
			$("#secDelCutOverTime").attr('disabled', true);
			$("#preWiringDate").attr('disabled', true);
			$("#preWiringTime").attr('disabled', true);
			$("#searchPcdOrderBtn").attr('disabled', true);
			$("#pcdOrderId").attr('disabled', true);
		}else if(val == "cancel"){
			$('#preBookSerialNum').val("");
			$('#confirmMsgBlock').hide();
			$('#cancelMsgBlock').show();
			$('#confirmAppntBtn').attr('disabled', false);
			$('#cancelAppntBtn').attr('disabled', true);
			$("#secDelInstallationDate").attr('disabled', false);
			$("#secDelInstallationTime").attr('disabled', false);
			$("#cutOverDate").attr('disabled', false);
			$("#cutOverTime").attr('disabled', false);
			$("#secDelCutOverDate").attr('disabled', false);
			$("#secDelCutOverTime").attr('disabled', false);
			$("#searchPcdOrderBtn").attr('disabled', false);
			$("#pcdOrderId").attr('disabled', false);
			resetFormDisplay();
		}
	}
	
	function resetFormDisplay(){
		//var bbShortage = $("#bbShortageInd").val();
		var er ="${externalRelocate}";
		var pcdOrderExist = $("#pcdOrderExistInd").val();
		var sharePCD = ${showPcdOrderId}; //$("#sharePCDInd").val();
		var allowAppt = ${allowAppt};
		var bmo = "${ltsappointment.bmoRemark}";
		//var srdReasonCd = "${ltsappointment.earliestSRD.reasonCd}";
		var tentativeInd = "${ltsappointment.tentativeInd}";
		var confirmedInd = "${ltsappointment.confirmedInd}";
		var backDateAppln = "${ltsappointment.earliestSRD.backDateAppln}";
		
		$("#preBookSerialNum").attr('disabled', false);
		$("#installationDate").attr('disabled', false);
		$("#installationTime").attr('disabled', false);
		$("#searchPcdOrderBtn").attr('disabled', false);
		$("#installationDate").attr('disabled', !backDateAppln);
		
		if(tentativeInd == "true"){
			$("#preBookSerialNum").attr('disabled', true);
			$("#installationDate").attr('disabled', true);
			$("#installationTime").attr('disabled', true);
			$("#secDelInstallationDate").attr('disabled', true);
			$("#secDelInstallationTime").attr('disabled', true);
		}
		if(!er){
			$("#preWiringDate").attr('disabled', true);
			$("#preWiringTime").attr('disabled', true);
		}else{
			$("#preWiringDate").removeAttr('disabled');
			if($("#preWiringDate").val() == ""){
				$("#preWiringTime").attr('disabled', true);
			}else{
				$("#preWiringTime").removeAttr('disabled');
				if($("#preWiringTime").val() == ""){
					timeSlotLookup($("#preWiringDate").val(), "P");
				}
			}
		}
		if(sharePCD && !allowAppt){
			$("#preBookSerialNum").attr('disabled', true);
			$("#installationDate").attr('disabled', true);
			$("#installationTime").attr('disabled', true);
			if(pcdOrderExist == "true"){
				$("#searchPcdOrderBtn").attr('disabled', true);
				$("#pcdOrderId").attr('disabled', true);
				$("#clearPcdOrderBtn").attr('disabled', true);
				
			}else{
				$('#searchPcdOrderBtn').attr('disabled', false);
				$("#pcdOrderId").attr('disabled', false);
				$("#clearPcdOrderBtn").attr('disabled', false);
				if($("#allowCustConfirmInd").val() == "true"){
					$("div#pcdOrderConfirmDiv").show();
					$("#confirmCust").attr('disabled', false);
				}else{
					$("#confirmCust").attr('disabled', true);
				}
				if($("#allowIaConfirmInd").val() == "true"){
					$("div#pcdOrderConfirmDiv").show();
					$("#confirmIa").attr('disabled', false);
				}else{
					$("#confirmIa").attr('disabled', true);
				}
				if($("#allowCustConfirmInd").val() == "false"
						&& $("#allowIaConfirmInd").val() == "false"){
					$("div#pcdOrderConfirmDiv").hide();
				}
			}
		}else{
			resetInstallationDatePicker("", "");
			//resetpreWiringDatePicker();
			if($("#installationTime").val() == "" && $("#installationDate").val() != ""
					&& tentativeInd == "false" && confirmedInd == "false"){
				timeSlotLookup($("#installationDate").val(), "I");
			}
		}
		if(bmo != ""){
			$("#bmoAlertDiv").show();
		}else{
			$("#bmoAlertDiv").hide();
		}
		
	}
	
	function resetInstallationDatePicker(minDate, maxDate){
		if(minDate == ""){
			minDate = "${earliestSRD}";
		}
		if(maxDate == ""){
			maxDate = "+100Y";
		}
		
		if ("${sessionScope.sessionLtsOrderCapture.orderType == 'SBR'}" == "true") {
			var appDate = "${appDate}";
			if(appDate != ""){
				var currentTime = $.datepicker.parseDate("dd/mm/yy", appDate.substring(0, 10));
				maxDate = new Date(currentTime.getFullYear(), currentTime.getMonth() +4, 0);
			}
		}
		
		$('#installationDate').datepicker( "destroy");
		$('#installationDate').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate: minDate, maxDate: maxDate, //Y is year, M is month, D is day  
			yearRange: "0:+100" //the range shown in the year selection box
		});
	}
	
	function secDelDateValidator(val){
		var secDelDate = $("#secDelInstallationDate").val();
		var secDelTime = $("#secDelInstallationTime").val();
			if($("#installationDate").val() == secDelDate){
				var selectedTimeSlot = $("#installationTime").val(); 
				var selectedTimeSlotType = $("#installationTimeType").val(); 
				$("#secDelInstallationTime").children().remove();
				$("#secDelInstallationTime").html('<option value=""> -- SELECT --</option>'+'<option value="'+ selectedTimeSlot +'" selected=true>'+ selectedTimeSlot +'</option>');
				$("#secDelInstallationTimeType").html('<option value=""></option>'+'<option value="'+ selectedTimeSlotType +'" selected=true>'+ selectedTimeSlotType +'</option>');
				colorTimeSlot("S");
			}else if((secDelDate != "" && secDelTime == "") || val == "SDID" ){
				timeSlotLookup(secDelDate, "S");
			}
	}
	
	function resetSecDelDatePicker(){
		$('#secDelInstallationDate').datepicker( "destroy");
			var secDelEarliestSRD = "${ltsappointment.secDelEarliestSRD}";
			var Min = secDelEarliestSRD;
			if($('#installationDate').val() != ""){
				installationDate = $('#installationDate').val();
				var date_secDelEarliestSrd = $.datepicker.parseDate('dd/mm/yy', secDelEarliestSRD); 
				var date_installationDate = $.datepicker.parseDate('dd/mm/yy', installationDate); 
				if(date_secDelEarliestSrd > date_installationDate){
					//date_secDelEarliestSrd > installationDate
					Min = secDelEarliestSRD;
				}else{
					Min = installationDate;
				}
			}
			$('#secDelInstallationDate').datepicker({
				changeMonth: true,
				changeYear: true,
				showButtonPanel: true,
				dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
				minDate: Min, maxDate: "+100Y", //Y is year, M is month, D is day  
				yearRange: "0:+100" //the range shown in the year selection box
			});
	}
	
	function getDaysInMonth(m, y) {
		   return /4|6|9|11/.test(m)?30:m==2?(!(y%4)&&y%100)||!(y%400)?29:28:31;
		}
	
	function calulateAdjAmount() {
		var date_startDate = ($.datepicker.parseDate('dd/mm/yy', $("#adjStartDate").val()));
		var date_endDate = ($.datepicker.parseDate('dd/mm/yy', $("#adjEndDate").val()));
		
		var date_startDate_month = date_startDate.getMonth() + 1;
		var date_startDate_year = date_startDate.getFullYear();
		var date_endDate_month = date_endDate.getMonth() + 1;
		var date_endDate_year = date_endDate.getFullYear();
		var ans = 0;
		var quantity = 1;

		var rate = $("#adjAmount").val();
		
		if (date_startDate_month == date_endDate_month && date_startDate_year == date_endDate_year) {
			var ret1 = getDaysInMonth(date_startDate_month, date_startDate_year);	
			ans = ((date_endDate.getDate() - date_startDate.getDate() + 1) / ret1 ) * rate;
		}
		else {
			var ret1 = getDaysInMonth(date_startDate_month, date_startDate_year);	
			var ret2 = getDaysInMonth(date_endDate_month, date_endDate_year);	
			var ret3 = date_endDate_month - date_startDate_month + (12 * (date_endDate_year - date_startDate_year)) - 1;
		      ans = (((ret1 - date_startDate.getDate() + 1) / ret1) ) + (((date_endDate.getDate()) / ret2));
		      ans = (ans + ret3) * rate;
		}
		
		$("#adjResult").val(isNaN(ans) ? 0 : ans.toFixed(2) );
	}
	
	function changeAdjEndDate() {
		var instDate = $("#installationDate").val();
		if (instDate != '' && instDate != null) {
			var date_instDate = ($.datepicker.parseDate('dd/mm/yy', instDate));
			date_instDate.setDate(date_instDate.getDate() - 1);
			$("input[name='adjEndDate']").val(formattedDate(date_instDate));	
		}
		
	}
	
	$(document).ready(function() {
		resetFormDisplay();
		changeAdjEndDate();
		

		$('#adjStartDate').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate: "${ltsappointment.adjStartDate}", 
			yearRange: "0:+100" //the range shown in the year selection box
		});
		
		$('#adjEndDate').datepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			minDate: "${ltsappointment.adjStartDate}", 
			dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			yearRange: "0:+100" //the range shown in the year selection box
		});
		
		
		if($("#confirmedInd").val() == "true"){
			confirmDisplay("confirm");
		}else{
			colorTimeSlot("I");
			colorTimeSlot("S");
			$('#cancelAppntBtn').attr('disabled', true);
			if($("#submitInd").val() == "CONFIRM"
					|| $("#submitInd").val() == "CANCEL"){
				confirmDisplay("cancel");
			}
			refreshTimeSlotType();
		}
		if($("#bbShortageInd").val() == "true"){
			$("#bbShortage_warning").show();
		}else{
			$("#bbShortage_warning").hide();
		}
		
		var cutOverInd = $('#cutOverInd').val();
		var secDelInd = $('#secDelInd').val();
		var secDelCutOverInd = $('#secDelCutOverInd').val();
		
		if(cutOverInd == "true"){
			$("#cutOver").show();
		}
		if(secDelInd == "true"){
			resetSecDelDatePicker();
			$("#secDel").show();
			if(secDelCutOverInd == "true"){
				$("#secDelCutOver").show();
			}
		}

		var iaDiffInd = $('#iaDiffInd').val();
		if(iaDiffInd == "true"){
			$("#pcdOrderNotSameAddrDiv").show();
		}
		var custDiffInd = $('#custDiffInd').val();
		if(custDiffInd == "true"){
			$("#pcdOrderNotSameCustDiv").show();
		}

		
		$("#adjCal").click(function(event){
			event.preventDefault();
			calulateAdjAmount();	
		});
		
		$("#adjCalClear").click(function(event){
			event.preventDefault();
			$("#adjAmount").val('');
			$("#adjResult").val('');
		});
		
		$("#showAdjCalBtn").click(function(event){
			event.preventDefault();
			$("#adjustmentCalDiv").show(10);
			$("#hideAdjCalBtn").show();
			calulateAdjAmount();
			$(this).hide();
		});
		
		$("#hideAdjCalBtn").click(function(event){
			event.preventDefault();
			$("#adjustmentCalDiv").hide(10);
			$("#showAdjCalBtn").show();
			$(this).hide();
		});
		
		$("#installationDate").change(function(event){
			
			<c:if test="${ ltsappointment.fieldVisitRequired }">
				refreshTimeSlot();
			</c:if>
			
			
			if(secDelInd == "true"){
				resetSecDelDatePicker();
			}
			
			changeAdjEndDate();
		});
		
		$("#preWiringDate").change(function(event){
			var date = $("#preWiringDate").val();
			$("#preWiringTime").attr('disabled', false);
			resetInstallationDatePicker($("#preWiringDate").datepicker('getDate'), "");
			timeSlotLookup(date, "P");
		});
		
		$("#secDelInstallationDate").change(function(event){
			
		
			//secDelDateValidator("SDID");
			
			<c:if test="${ltsappointment.secDelFieldVisitRequired }">
				$("#secDelInstallationTime").attr('disabled', false);
				refreshTimeSlot();
				refreshTimeSlotType();
			</c:if>
			
		});

		$("#installationTime").change(function(event){
			if($('#secDelInd').val() == "true"){
				if($("#installationDate").val() == $("#secDelInstallationDate").val()){
					$('#secDelInstallationTime').val($("#installationTime").val());
				}
			}
			if($("#installationTime").val() != ""){
				refreshTimeSlotType();
			}
		});

		$("#secDelInstallationTime").change(function(event){
			if($("#installationDate").val() == $("#secDelInstallationDate").val()){
				$('#installationTime').val($("#secDelInstallationTime").val());
			}
			if($("#secDelInstallationTime").val() != ""){
				refreshTimeSlotType();
			}
		});
		
		$("#cutOverTime").change(function(event){
			$("#secDelCutOverDate").val($("#cutOverDate").val());	
			$("#secDelCutOverTime").val($("#cutOverTime").val());			
		});
		
		
		$('a#copyContactNumBtn').click(function(event) {
			event.preventDefault();
			var num = $('#installationContactNum').val();
			$('#installationMobileSMSAlert').val(num);
		});

		//BOM2018061
		$('a#copyContactEpdBtn').click(function(event) {
			event.preventDefault();
			
			if(confirm('Are you sure to copy customer name and contact number?')){
				if($('#defaultContactName').val() != ''){
					$('#epdContactName').val($('#defaultContactName').val());
				}
				if($('#defaultContactNum').val() != ''){
					$('#epdContactNum').val($('#defaultContactNum').val());
				}
			}
			
		});
		//BOM2018061 END
		
		$("input#confirmAppntBtn").click(function(event) {
			event.preventDefault();
			$("#submitInd").val("CONFIRM");
			$("#installationDate").attr('disabled', false);
			$("#installationTime").attr('disabled', false);
			$("#preWiringDate").attr('disabled', false);
			$("#preWiringTime").attr('disabled', false);
			$("#secDelInstallationDate").attr('disabled', false);
			$("#secDelInstallationTime").attr('disabled', false);
			$("#appointmentform").submit();
			
		});

		$("input#cancelAppntBtn").click(function(event) {
			$("#preBookSerialNum").attr('disabled', false);
			$("#confirmedInd").val("false");
 			$("#submitInd").val("CANCEL");
			$("#appointmentform").submit();
		});
		
		$("#clearPcdOrderBtn").click(function(event){
			$("#pcdOrderId").val('');
			$("#installationDate").val('');
			$("#installationTime").val('');
			$("#installationType").val('');
			
			$("div#pcdOrderNotFoundDiv").hide();
			$("div#pcdOrderNotSameCustDiv").hide();
			$("div#pcdOrderNotSameAddrDiv").hide();
			$("div#pcdOrderRetrievedDiv").hide();
			$("div#pcdOrderConfirmDiv").hide();
			$("#custDiffInd").val('');
			$("#iaDiffInd").val('');
			$("#allowCustConfirmInd").val('');
			if($("#preBookSerialNum").val() != ''){
				$("input#cancelAppntBtn").trigger('click');
			}else{
	 			$("#submitInd").val("CLEAR");
				$("#appointmentform").submit();
			}
			resetFormDisplay();
		});
		
		$("#searchPcdOrderBtn").click(function(event) {
			var pcdOrderId= $("#pcdOrderId").val();
			var sbuid = $('input[name="sbuid"]').val();
			$.ajax({
				url : "ltspcdorderlookup.html?sbuid=" + sbuid,
				type : 'POST',
				data : "parm=" + pcdOrderId,
				success : function(data){
					var obj = jQuery.parseJSON(data);
					if (obj.success == true) {
						$("div#pcdOrderNotFoundDiv").hide();
		 				$("div#pcdOrderNotSameCustDiv").hide();
		 				$("div#pcdOrderNotSameAddrDiv").hide();
		 				$("div#pcdOrderRetrievedDiv").show();
	 					$("div#pcdOrderConfirmDiv").hide();
		 				
		 				$("#installationDate").attr('disabled', false);
		 				$("#installationTime").attr('disabled', false);
		 				$("#installationDate").val(obj.installDate);
		 				$("#installationTime").val(obj.installTime);
		 				$("#installationType").val(obj.installTimeType);
		 				refreshCutOverTime();
		 				$("#warning_addr_msg").html("<b>Springboard Order (" + pcdOrderId + ") Retrieved</b>");
		 				resetpreWiringDatePicker();
		 			}else if (obj.success == false) {
		 				$("div#pcdOrderRetrievedDiv").hide();
		 				$("div#pcdOrderNotSameCustDiv").hide();
		 				$("div#pcdOrderNotSameAddrDiv").hide();
		 				$("div#pcdOrderNotFoundDiv").show();
	 					$("div#pcdOrderConfirmDiv").hide();
		 				$("#preBookSerialNum").val("");
		 				
		 				$("#installationDate").val("");
		 				$("#preWiringDate").val("");
		 				$("#installationTime").val("");
		 				$("#preWiringTime").val("");
		 				
		 				$("#installationDate").attr('disabled', true);
		 				$("#preWiringDate").attr('disabled', true);
		 				$("#installationTime").attr('disabled', true);
		 				$("#preWiringTime").attr('disabled', true);
		 			}
		 			if (obj.cusNotMatch == true){
			 			$("div#pcdOrderRetrievedDiv").hide();
			 			$("div#pcdOrderNotSameCustDiv").show();
			 			$("div#pcdOrderNotFoundDiv").hide();
		 				$("div#pcdOrderConfirmDiv").show();
		 				$("#custDiffInd").val("true");
			 			if(obj.allowCustConfirm == 1){
			 				$("#confirmCust").attr('disabled', false);
			 				$("#allowCustConfirmInd").val("true");
			 				$("div#pcdOrderConfirmDiv").show();
			 			}else{
			 				$("#confirmCust").attr('disabled', true);
			 				$("#allowCustConfirmInd").val("false");
			 			}
		 			}else{
			 			$("div#pcdOrderNotSameCustDiv").hide();
		 				$("#custDiffInd").val("false");
		 			}
		 			if (obj.iaNotMatch == true){
			 			$("div#pcdOrderRetrievedDiv").hide();
			 			$("div#pcdOrderNotSameAddrDiv").show();
			 			$("div#pcdOrderNotFoundDiv").hide();
		 				$("div#pcdOrderConfirmDiv").show();
		 				$("#iaDiffInd").val("true");
			 			if(obj.allowIaConfirm == 1){
			 				$("#confirmIa").attr('disabled', false);
			 				$("#allowIaConfirmInd").val("true");
			 				$("div#pcdOrderConfirmDiv").show();
			 			}else{
			 				$("#confirmIa").attr('disabled', true);
			 				$("#allowIaConfirmInd").val("false");
			 			}
		 			}else{
			 			$("div#pcdOrderNotSameAddrDiv").hide();
		 				$("#iaDiffInd").val("false");
		 			}
				},
				complete : function() {
					 resetFormDisplay();
					 $.unblockUI(); 
				},
				beforeSend : function() {
					 $.blockUI({ message: null }); 
				}
			});
		});
		
		$("#submit").click(function(event) {
			event.preventDefault();
			$("#submitInd").val("SUBMIT");
			$("#installationDate").attr('disabled', false);
			$("#installationTime").attr('disabled', false);
			$("#preWiringDate").attr('disabled', false);
			$("#preWiringTime").attr('disabled', false);
			$("#secDelInstallationDate").attr('disabled', false);
			$("#secDelInstallationTime").attr('disabled', false);
			$("#pcdOrderId").attr('disabled', false);
			$("#appointmentform").submit();
		});
		
		$("#suspend").click(function(event){
			event.preventDefault();
			$("#submitInd").val("SUSPEND");
			$("#appointmentform").submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
		
	});
	
	
</script>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>