<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ include file="loadingpanel.jsp" %>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*,
					java.util.Calendar
				"
%>
<%int expiryYear = Calendar.getInstance().get(Calendar.YEAR);%>
<script src="js/jquery-1.4.4.js"></script>
<script type="text/javascript">
	function submit() {
		$("#cashpaymentForm").submit();
	}
	
	
	$(document).ready(function() {
		/* $("form[name=cashpaymentForm]").submit(function(e) {
			alert("ready");
		}); */
	});
</script>
<%@ page import="com.bomwebportal.dto.OrderDTO" %>

<br/>
<form:form method="POST" name="cashpaymentForm" id="cashpaymentForm" commandName="paymentUpfrontDto">
	<input type="hidden" name="currentView" value="paymentUpfrontDto" />
	<c:if test="${actionType == 'success'}">
		The remark is saved successfully.
    </c:if>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td class="table_title">Upfront Payment </td>
				</tr>
			</table>
			<br/>
			<div class="row" style="font-weight: bold; color:blue">
				Note: please enter the date in dd/mm/yyyy format
			</div>
			<br/>
			<fieldset>
				<legend><b>Cash</b></legend>
				
				<table width="100%" border="0" cellspacing="5" margin="0" class="contenttext">
				<% int i = 1; %>
				<c:forEach items="${paymentUpfrontDto.paymentTransList}" var="ptList" varStatus="ptRow">
				<c:if test="${ptList.paymentType == 'M' }">
					<tr>
					   <td><%=i%>.</td>
					   <td align="right">Amount(HK$)<span class="contenttext_red">*</span>:</td>
					   <td><form:input path="paymentTransList[${ptRow.index}].paymentAmount"  maxlength="8" cssClass="amount" disabled="true" /></td>
					   <td align="right">Transaction date<span class="contenttext_red">*</span>:</td>
					   <td><form:input path="paymentTransList[${ptRow.index}].transDate" maxlength="10" size="10" disabled="true" /> </td>
					</tr>
					<tr>
					    <td></td>
						<td align="right">7-11 Shop Code:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].paymentStoreCd" maxlength="20" />
						<td></td>
					</tr>
					<tr>
					    <td></td>
						<td align="right">Invoice No.:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].invoiceNo" maxlength="15"/> </td>
					</tr>	
					<tr>
					    <td></td>
						<td align="right">Remark:</td>
						<td colspan="3"><form:textarea path="paymentTransList[${ptRow.index}].remark" rows="2" cols="60" /></textarea></td>
					</tr>
					<% i++; %>
					</c:if>
				</c:forEach>
				</table>			
			</fieldset>
			<br/>
			<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3" onClick="submit()">save &gt;</a></div>
			</td>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>