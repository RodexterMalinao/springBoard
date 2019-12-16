<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/lts/disconnect/ltsterminateprogressbar.jsp" >
    <jsp:param name="step" value="3" />
</jsp:include>

<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/web/lts/disconnect/ltsterminatebillinginfo.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<%@ page contentType="text/html;charset=UTF-8" %>

<script src="js/jquery.lts.autocomplete.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<form:form method="POST" action="#" id="terminatebillinginfoform" commandName="ltsterminatebillinginfoCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />

<table width="98%" class="paper_w2 round_10" align="center">
<tr>
	<td>
	<table width="98%" border="0" cellspacing="1" align="center">
		<tr>
			<td>
			<br/>
			<div id="s_line_text">Billing Information</div></td>
		</tr>
	</table>
	<table width="98%" border="0" cellspacing="4" cellpadding="4" class="contenttext" align="center">
		<tr>
			<td>
				<div id="s_sub_line_text">Existing Billing Method</div>
			</td>
		</tr>
		<tr>
			<td align="right" width="30%">
				Existing Billing Name : 
			</td>
			<td>
				<b>${ltsterminatebillinginfoCmd.existingBillingName}</b>
				<form:hidden path="existingBillingName" />
			</td>
		</tr>		
		<tr>
			<td align="right" width="30%">
				Existing Billing Address : 
			</td>
			<td>
				<b>${ltsterminatebillinginfoCmd.billingAddress}</b>
				<form:hidden path="billingAddress" />
			</td>
		</tr>
		<tr>
			<td align="right" >
				Existing Payment Method : 
			</td>
			<td>
				<b>
				<c:if test="${ltsterminatebillinginfoCmd.existingPayMethodType == 'M'}">
					Cash
				</c:if>	
				<c:if test="${ltsterminatebillinginfoCmd.existingPayMethodType == 'A'}">
					Bank Auto-Pay
				</c:if>	
				<c:if test="${ltsterminatebillinginfoCmd.existingPayMethodType == 'C'}">
					Credit Card
				</c:if>	
				</b>
				<form:hidden path="existingPayMethodType" />
			</td>
		</tr>
		<tr>
			<td align="right" >
				Existing Billing Media : 
			</td>
			<td>
				<b>
				<c:if test="${ltsterminatebillinginfoCmd.existingBillMedia == 'P'}">
					Paper
				</c:if>	
				<c:if test="${ltsterminatebillinginfoCmd.existingBillMedia == 'S'}">
					Email : ${ltsterminatebillinginfoCmd.existingBillingEmail}
				</c:if>	
				</b>
				<form:hidden path="existingPayMethodType" />
			</td>
		</tr>						
		<tr>
			<td align="right" class="baChange">
				Change Billing Address :  
			</td>
			<td>
				<form:checkbox path="changeBa" id="changeBa"/>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		</table>
		</td>
	</tr>
</table>
<br>
<table width="98%" class="newBa paper_w2 round_10" align="center" style="display:none" >
	<tr>
		<td>
			<table width="98%" border="0" cellspacing="1" rules="none" align="center">
				<tr>
					<td>
					<br/>
					<div id="s_line_text">New Billing Information</div></td>
				</tr>
			</table>
			<table width="98%" border="0" cellpadding="1" cellspacing="1" class="contenttext" align="center">
				<tr>
					<td>
						<div id="s_sub_line_text">Enter New Billing Information : </div>
					</td>
				</tr>
				<tr>
					<td align="right">
						New Billing Name:
					</td>
					<td>
						<form:input path="newBillingName" size="40" disabled="${ltsterminatebillinginfoCmd.changeBillingNameInd == 'D'}"/>
						<br>
						<form:errors path="newBillingName" cssClass="error"/>
						<form:hidden path="changeBillingNameInd"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>				
				<tr>
					<td align="right">
						Quick Search* : 
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
					<td align="left">
						Simply input the building name of your billing address, separate with a comma (,)
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						Billing Address* : 
					</td>
					<td>
						<form:textarea id="billingAddressTextArea" path="newBillingAddress" rows="6" cols="40" cssStyle="resize: none;"/>
						<br>
						<form:errors path="newBillingAddress" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Instant Update : 
					</td>
					<td>
						<form:checkbox path="instantUpdateBa" id="instantUpdateBa"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>						
			</table>
			
		</td>
	</tr>
</table>
<br>
<table width="100%" border="0" cellspacing="0" class="contenttext">
	<tr>
		<td align="right">
			<div class="button">
				<a href="#" id="submitBtn">Continue</a>
			</div>
		</td>
	</tr>
</table>
</form:form>

<script type='text/javascript'>
$(ltsterminatebillinginfo.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>