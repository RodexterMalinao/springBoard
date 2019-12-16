<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/web/lts/ltsrecontract.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script src="js/jquery.lts.autocomplete.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<style type="text/css">
.table_left {width: 23%;}
.table_right {}
</style>


<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="2" />
</jsp:include>

<div class="cosContent ">
<form:form method="POST" id="ltsRecontractForm" name="ltsRecontractForm" commandName="ltsRecontractCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<input type="hidden" id="profileExist" value="${not empty sessionScope.sessionLtsOrderCapture.ltsRecontractForm.custDetailProfile}" />
<form:hidden path="existingBillingName"/>
<table width="100%" border="0" class=" paper_w2 round_10" align="center">
	<tr>
		<td width="98%" align="center">
			<br>
			<div id="s_line_text">Recontract</div>
		</td>
	</tr>
</table>
<table width="95%" border="0"  align="center">
	<tr>
		<td width="100%" align="center">
			<br>
			<div id="s_line_text">Transferee (New Customer) Information</div>
		</td>
	</tr>
</table>
<table width="80%" border="0" cellspacing="5" cellpadding="5" align="center">			
	<tr>
		<td class="table_left">Document ID<span class="contenttext_red">*</span> :</td>
		<td class="table_right">
			<form:select path="docType" cssClass="searchCriteria">
				<form:option value="HKID" label="HKID" />
				<form:option value="PASS" label="Passport" />
				<form:option value="BS" label="BR" />
			</form:select>
			<form:input path="docId" cssClass="toUpper"/>
			<span style="padding-left: 10px">
				<c:if test="${sessionScope.bomsalesuser.channelId == 1}">
					<form:checkbox path="newCustVerified"/>
				</c:if>
				<c:if test="${sessionScope.bomsalesuser.channelId != 1}">
					<input type="checkbox" disabled="disabled"/> 
				</c:if>
				Verified
			</span>
			<form:errors path="docId" cssClass="error"/>
			<form:errors path="docType" cssClass="error"/>
			<form:errors path="newCustVerified" cssClass="error"/>
			<span id="idError" class="error" style="display:none"><spring:message code="invalid.hkidCheckDigit" /></span>
		</td>
	</tr>
	<tr class="hkbrField" style="display:none">
		<td class="table_left">Company Name<span class="contenttext_red">*</span> :</td>
		<td class="table_right">
			<form:input path="companyName"/>
			<form:errors path="companyName" cssClass="error"/>
		</td>
	</tr>
</table>
<table width="95%" border="0" align="center" class="hkbrField">
	<tr>
		<td width="100%" align="center">
			<br>
			<div id="s_line_text">Authorized Person Information</div>
		</td>
	</tr>
</table>
<table width="80%" border="0" cellspacing="5" cellpadding="5" align="center">
	<tr>
		<td class="table_left">Title<span class="contenttext_red">*</span> :</td>
		<td class="table_right">
			<form:select path="title" id="title">
				<form:option value=""> -TITLE- </form:option>
				<form:options items="${titleList}" itemValue="itemKey" itemLabel="itemValue" />
			</form:select>
			<form:errors path="title" cssClass="error"/>
		</td>
	</tr>				
	<tr>
		<td class="table_left">Given Name<span class="contenttext_red">*</span> :</td>
		<td class="table_right">
			<form:input path="firstName"/>
			<form:errors path="firstName" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td class="table_left">Family Name<span class="contenttext_red">*</span> :</td>
		<td class="table_right">
			<form:input path="lastName"/>
			<form:errors path="lastName" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td class="table_left">Mobile Number :</td>
		<td class="table_right">
			<form:input path="mobileNum"/>
			<form:errors path="mobileNum" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td class="table_left">Email Address :</td>
		<td class="table_right">
			<form:input path="emailAddr"/>
			<form:errors path="emailAddr" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td class="table_left">Deceased Case :</td>
		<td class="table_right" >
			<form:checkbox path="deceasedCase" id="deceasedCase" disabled="${ltsRecontractCmd.splitAccount}"/>
			<c:if test="${ltsRecontractCmd.splitAccount }">
				<span  style="font-size: 0.8em;"> (Cannot choose deceased case due to split R/I account)</span>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="table_left">
		<span class="nonHkbrField">Relationship</span>
		<span class="hkbrField">Post</span>
		<span class="deceasedCase contenttext_red">*</span>  :</td>
		<td class="table_right" style="font-size: 0.8em;">
			<form:select path="relationship">
				<form:option value="">-- SELECT --</form:option>
				<form:options cssClass="nonHkbrField" items="${relationshipCodeList}" itemValue="itemKey" itemLabel="itemValue" />
				<form:options cssClass="hkbrField" items="${relationshipBrCodeList}" itemValue="itemKey" itemLabel="itemValue" />
			</form:select>
			<span class="nonDeceasedCase">(Optional)</span><span class="deceasedCase">(Mandatory)</span>
			<form:errors path="relationship" cssClass="error"/>
		</td>
	</tr>
	<tr>
		<td colspan="2"> 
			<br/>
			<span class="contenttext_red">*</span>
			<form:radiobutton id="recontractModeB" path="recontractMode" value="B" /> Transferee and transferor application together
			&nbsp;&nbsp;&nbsp;
			<form:radiobutton id="recontractModeS" path="recontractMode" value="S" /> Transferee application (with transferor authorization letter)
			<br/>
			
			<br>
			<div id="updateDnProfileM" style="display: inline;">
			<span class="contenttext_red">&nbsp;</span>
			<form:radiobutton id="updateDnProfileMRadio" path="recontractMode" value="M" /> Update DN Profile (Customer name is exactly matched)
			&nbsp;&nbsp;&nbsp;
			</div>
			
			<div id="updateDnProfileN" style="display: inline;">
			<span class="contenttext_red">&nbsp;</span>
			<form:radiobutton id="updateDnProfileNRadio" path="recontractMode" value="N" /> Update DN Profile (Customer name is not matched)
			<br/>
			</div> 
			<br/>
			<form:errors path="recontractMode" cssClass="error"/>
			<br/>
		</td>
	</tr>
	<tr>
		<td class="table_left">Transferor (Old Customer) Document:</td>
		<td class="table_right">
			<span>
				<c:if test="${sessionScope.bomsalesuser.channelId == 1}">
					<form:checkbox path="oldCustVerified"/>
				</c:if>
				<c:if test="${sessionScope.bomsalesuser.channelId != 1}">
					<input type="checkbox" disabled="disabled"/> 
				</c:if>
				Verified
			</span>
			<form:errors path="oldCustVerified" cssClass="error"/>
		</td>
	</tr>	
	<tr>
		<td colspan="2">
			<br/>
			<div id="profileInfo" class="ui-state-highlight ui-corner-all" style="margin-left: 15%; padding: 0 0.7em 0.7em; width: 50%; min-height: 35px; display:none">
				<p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span></p>
				<div id="profileFound">
					<span style="font-weight:bold">Existing Customer.<br/><br/>Blacklist Ind : <span id="blacklist"><br/></span></span>
				</div>
				<div id="profileNotFound">
					<span style="font-weight:bold">New Customer<br/></span>
				</div>
			</div>
		</td>
	</tr>	
</table>

<div style="width: 100%" class="deceasedCase">
	<table width="95%" border="0" align="center">
		<tr>
			<td width="100%" align="center">
				<br>
				<div id="s_line_text">Deceased Case</div>
			</td>
		</tr>
	</table>
	<table width="80%" border="0" cellspacing="5" cellpadding="5" align="center">
		<tr>
			<td valign="top">Refund Type <span class="contenttext_red">*</span> : </td>
			<td>
				<form:select path="refundType">
					<form:option value="">--SELECT--</form:option>
					<form:option value="N">Refund to Existing Customer</form:option>
					<form:option value="S">Refund to New Applicant (O/S &lt;/=$400 with Relationship Supporting) </form:option>
					<form:option value="I">Refund to New Applicant (with Probate / Letters of Administration ) </form:option>
				</form:select>
				<form:errors path="refundType" cssClass="error"/>
			</td>
		</tr>
		<tr class="refundNewCust">
			<td>
				Update Billing Name for Existing Customer <span class="contenttext_red">*</span> :
			</td>
			<td>
				<form:input path="newBillingName" size="40"/>
				<br>
				<form:errors path="newBillingName" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td>
				Quick Search <span class="contenttext_red">*</span> : 
			</td>
			<td>
				<form:input path="baQuickSearch" id="baQuickSearch" size="80" />
				&nbsp;
				<div class="func_button">
					<a href="#" id="clearBaInputAddress">Clear</a>
				</div>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="left" style="font-size: 0.8em;" valign="top">
				Simply input the building name of your billing address, separate with a comma (,)
			</td>
		</tr>
		<tr>
			<td valign="top">
				Update Billing Address for Existing Customer<span class="contenttext_red">*</span> : 
			</td>
			<td>
				<form:textarea id="billingAddressTextArea" path="newBillingAddress" rows="6" cols="40" cssStyle="resize: none;"/>
				<br>
				<form:errors path="newBillingAddress" cssClass="error" />
			</td>
		</tr>
	</table>
	
</div>
<table width="95%" border="0" align="center">
	<tr>
		<td width="100%" align="center">
			<br>
			<div id="s_line_text">Recontract Service</div>
		</td>
	</tr>
</table>		
<table width="80%" border="0" cellspacing="5" cellpadding="5" align="center">
		<tr>
			<td class="table_left" colspan="2"> 
				Transfer the below Service (only limited to Service associated with the account of the above service number) to the new Customer:
			</td>
		</tr>
		<tr>
			<td colspan="2"> 
				<form:errors path="globalCallCardService" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td class="table_left" > 
				Global Calling Card Service<span class="contenttext_red">*</span> :
			</td>
			<td class="table_right">
				<b>
				<form:radiobutton path="globalCallCardService" value="N" label="Service not exist"/>
				<form:radiobuttons path="globalCallCardService" items="${srvHandleCodeList}" itemLabel="itemValue" itemValue="itemKey" delimiter="&nbsp;"/>
				</b>
			</td>
		</tr>
		<tr>
			<td class="table_left" > 
				Mobile IDD0060 Service<span class="contenttext_red">*</span> :
			</td>
			<td class="table_right">
				<b>
				<form:radiobutton path="mobileIdd0060Service" value="N" label="Service not exist"/>
				<form:radiobuttons path="mobileIdd0060Service" items="${srvHandleCodeList}" itemLabel="itemValue" itemValue="itemKey" delimiter="&nbsp;"/>
				</b>
			</td>
		</tr>
		<tr>
			<td class="table_left" > 
				IDD0060 Fixed Fee Plan<span class="contenttext_red">*</span> :
			</td>
			<td class="table_right">
				<b><form:radiobutton path="idd0060FixedFeePlan" value="N" label="Service not exist"/>
				<form:radiobuttons path="idd0060FixedFeePlan" items="${srvHandleCodeList}" itemLabel="itemValue" itemValue="itemKey" delimiter="&nbsp;"/>
				</b>
			</td>
		</tr>
	</table>
	<br>
	<table width="100%" border="0" cellspacing="0" class="contenttext">
		<tr><td align="right">
			<div class="button"><a href="#" id="continueBtn">Next</a></div>
		</td></tr>
	</table>
</form:form>
</div>

<script type="text/javascript">
	$(ltsrecontract.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>