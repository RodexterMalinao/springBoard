<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />

<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobccsstaffinfo" />
</jsp:include>

<%
String workStatus = (String) request.getAttribute("workStatus"); 
%>

<style type="text/css"> 
	/* css for timepicker */
	.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
	.ui-timepicker-div dl { text-align: left; }
	.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
	.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
	.ui-timepicker-div td { font-size: 90%; }
	.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
	
	/*
	 * jQuery UI Slider 1.8.17
	 *
	 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
	 * Dual licensed under the MIT or GPL Version 2 licenses.
	 * http://jquery.org/license
	 *
	 * http://docs.jquery.com/UI/Slider#theming
	 */

	.ui-slider { position: relative; text-align: left; }
	.ui-slider .ui-slider-handle { position: absolute; z-index: 2; width: 1.2em; height: 1.2em; cursor: default; }
	.ui-slider .ui-slider-range { position: absolute; z-index: 1; font-size: .7em; display: block; border: 0; background-position: 0 0; }
	 
	.ui-slider-horizontal { height: .8em; }
	.ui-slider-horizontal .ui-slider-handle { top: -.3em; margin-left: -.6em; }
	.ui-slider-horizontal .ui-slider-range { top: 0; height: 100%; }
	.ui-slider-horizontal .ui-slider-range-min { left: 0; }
	.ui-slider-horizontal .ui-slider-range-max { right: 0; }
	
	.ui-slider-vertical { width: .8em; height: 100px; }
	.ui-slider-vertical .ui-slider-handle { left: -.3em; margin-left: 0; margin-bottom: -.6em; }
	.ui-slider-vertical .ui-slider-range { left: 0; width: 100%; }
	.ui-slider-vertical .ui-slider-range-min { bottom: 0; }
	.ui-slider-vertical .ui-slider-range-max { top: 0; }
	
</style>

<script src="js/jquery.ui.slider.js"></script>
<script src="js/jquery-ui-timepicker-addon.js"></script>

<script language="javascript">

	document.onkeypress = processKey;
	function processKey(e) {
		if (null == e)
			e = window.event;
		if (e.keyCode == 13) {
			insert();
		}
	}

	function initialManualInput(){
		
		if ($("input[id=manualInputBool]").is(":checked")) {
			$("#refCentreCd").removeAttr('readonly');
			$("#refTeamCd").removeAttr('readonly');
			$("#refSalesName").removeAttr('readonly');		
			$("#refreshButton").attr('disabled','disabled');
		}
		
	}

	
	
	function clearRefSalesInfo(){
		$("input[name=refSalesId]").val("");
		$("#refSalesName").val("");		
		$("#refCentreCd").val("");
		$("#refTeamCd").val("");
			
	}
	
	function formSubmit(actType) {
		document.staffInfoForm.actionType.value = actType;
		//document.staffInfoForm.submit();
		$("#staffInfoForm").submit();
	}
	
	$(document).ready(
		function() {
			
			initialManualInput();
			
			$('#callDatepicker').datepicker({
				changeMonth: true,
				changeYear: true,
				showButtonPanel: true,
				dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
				minDate: "+0", maxDate: "+100Y", //Y is year, M is month, D is day  
				yearRange: "-8:+2" //the range shown in the year selection box
			});
			
			$('#staffInfoTimepicker').timepicker({
				
			});
			
			$("input[id=manualInputBool]").change(function(){
				if ($(this).is(":checked")) {
					$("#refCentreCd").removeAttr('readonly');
					$("#refTeamCd").removeAttr('readonly');
					$("#refSalesName").removeAttr('readonly');
					$("#refreshButton").attr('disabled','disabled');
					
				}else{
					$("#refCentreCd").attr('readonly',true);
					$("#refTeamCd").attr('readonly',true);
					$("#refSalesName").attr('readonly',true);
					$("#refreshButton").removeAttr('disabled');
					
				}
				clearRefSalesInfo();
			});

		}
		
		
		
	);

</script>

<form:form name="staffInfoForm" id="staffInfoForm" method="POST"
	commandName="staffInfo">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><%--content--%>
			<table width="100%" border="0" cellspacing="1" rules="none">
				<tr>
					<td class="table_title"><spring:message code="label.mobccsstaffinfo.header"/></td>
				</tr>
			</table>
			<%-- ********************   SEARCHING KEY --- ROW 1 (ID DOC + search)   ***************************** --%>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
			<colgroup style="width: 230px"></colgroup>
			<colgroup></colgroup>
			<colgroup style="width: 230px"></colgroup>
			<colgroup></colgroup>
				<tr>
					<td colspan="4">
						<form:errors path="salesId" cssClass="error" />
						<form:errors path="salesName" cssClass="error" />
						<form:errors path="refSalesId" cssClass="error" />
						<form:errors path="refSalesName" cssClass="error" />
						<form:errors path="refSalesCentre" cssClass="error" />
						<form:errors path="refSalesTeam" cssClass="error" />
						<form:errors path="contactPhone" cssClass="error" />
						<form:errors path="position" cssClass="error" />
						<form:errors path="callDateStr" cssClass="error" />
						<form:errors path="callTimeStr" cssClass="error" />
						<form:errors path="errormsg" cssClass="error" />
						<form:errors path="salesCentre" cssClass="error" />
						<form:errors path="salesTeam" cssClass="error" />
			
					</td>
				</tr>
				<tr>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.salesId"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input path="salesId" maxlength="8" disabled="true"/>
						<% } else { %>
							<form:input path="salesId" maxlength="8" disabled="false"/>
						<% } %>
					</td>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.refSalesId"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input id="refSalesId" path="refSalesId" maxlength="8" disabled="true"/>
							
						<% } else { %>
							<form:input id="refSalesId" path="refSalesId" maxlength="8" disabled="false"/>
							<form:checkbox id="manualInputBool" path="manualInputBool" /> Manual Input
						<% } %>
					</td>
				</tr>
				<tr>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.salesName"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input path="salesName" disabled="true"/>
							<input type="submit" value="Refresh" onClick="javascript:formSubmit('REFRESH') " disabled="disabled"/>
						<% } else { %>
							<form:input path="salesName" disabled="true"/>
							<input type="submit" value="Refresh" onClick="javascript:formSubmit('REFRESH') " />
						<% } %>
					</td>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.refSalesName"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input id="refSalesName" path="refSalesName" readonly="true"/>
							<input id="refreshButton" type="submit" value="Refresh" maxlength="80" onClick="javascript:formSubmit('REFRESH') " disabled="disabled"/>
						<% } else { %>
							<form:input id="refSalesName" path="refSalesName" readonly="true"/>
							<input id="refreshButton" type="submit" value="Refresh" maxlength="80" onClick="javascript:formSubmit('REFRESH') " />
						<% } %>
					</td>
				</tr>
				<tr>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.salesCentre"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input path="salesCentre" disabled="true"/>
						<% } else { %>
							<form:input path="salesCentre" disabled="true"/>
						<% } %>
					</td>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.refSalesCentre"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input id="refCentreCd" path="refSalesCentre" maxlength="40" readonly="true"/>
						<% } else { %>
							<form:input id="refCentreCd" path="refSalesCentre" maxlength="40" readonly="true"/>
						<% } %>
					</td>
				</tr>
				<tr>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.salesTeam"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input path="salesTeam" disabled="true"/>
						<% } else { %>
							<form:input path="salesTeam" disabled="true"/>
						<% } %>
					</td>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.refSalesTeam"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input id="refTeamCd" path="refSalesTeam" maxlength="40" readonly="true"/>
						<% } else { %>
							<form:input id="refTeamCd" path="refSalesTeam" maxlength="40" readonly="true"/>
						<% } %>
					</td>
				</tr>
				<tr>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.contactPhone"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input path="contactPhone" disabled="true" />
						<% } else { %>
							<form:input path="contactPhone" disabled="false" maxlength="8"/>
						<% } %>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.position"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input path="position" disabled="true"/>
						<% } else { %>
							<form:input path="position" disabled="false"/>
						<% } %>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.callDate"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:input path="callDateStr" maxlength="10" id="callDatepicker" readonly="true" disabled="true"/>
						<% } else { %>
							<form:input path="callDateStr" maxlength="10" id="callDatepicker" readonly="true" />
						<% } %>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.callTime"/>:</div>
					</td>
					<td>
						<% if ("recall".equalsIgnoreCase(workStatus)) { %>
							<form:select path="callTimeHour" disabled="true">
								<form:options items="${hourList}" />
							</form:select>
							<form:select path="callTimeMin" disabled="true">
								<form:options items="${minList}" />
							</form:select>
						<% } else { %>
							<form:select path="callTimeHour"  >
								<form:options items="${hourList}"/>
							</form:select>
							<form:select path="callTimeMin" >
								<form:options items="${minList}"/>
							</form:select>
						<% } %>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<div align="left"><spring:message code="label.mobccsstaffinfo.callList"/>:</div>
					</td>
					<td>
						<form:input path="callListDesc" disabled="true"/>&nbsp;
						<form:hidden path="callList" />
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<br>
	
	<%-- ********************************   BOTTOM BUTTONS   ************************************* --%>
	<table width="100%" border="0" cellspacing="0">
		<tr>
			<td align="right">
				<div>
					<a href="#" class="nextbutton3" onClick="javascript:formSubmit('CONTINUE')">
						continue&nbsp;&gt;
					</a>
				</div>
			</td>
		</tr>
	</table>

	<form:hidden path="actionType" />
	<input type="hidden" name="currentView" value="mobccsstaffinfo" />
</form:form>


<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>