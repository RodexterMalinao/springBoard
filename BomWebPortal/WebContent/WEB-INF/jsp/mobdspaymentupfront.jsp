<%@ include file="/WEB-INF/jsp/header.jsp"%>
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
	function formSubmit(actionType) {
		//modify by eliot 20110622
		document.paymentForm.actionType.value = actionType;
		//document.paymentForm.submit();
		$("#paymentForm").submit();
	}
	
	function deleteRow(i) {
		document.paymentForm.ceksSubmit.value = i;
		document.paymentForm.actionType.value = 'DELETEROW';
		document.paymentForm.submit();
	}
	
	function ceksFormSubmit(i) {
		document.paymentForm.actionType.value = 'ceksSubmit';
		document.paymentForm.ceksSubmit.value = i;
		document.paymentForm.submit();
	}
	
	function loadItemCd(i, payType) {
		var storeCd = $('#storeCd_' + i).val();
		$("#itemCd_" + i).empty();
		$("#itemCd_" + i).append('<option value="NONE">Select...</option>');
		$(".installHide_" + i).show();
		$(".installShow_" + i).hide();
		 switch(storeCd){
	        case 'JSM': 
	        	$("#itemCd_" + i).append('<option value="5191">Mobile Product</option>');
	        	$("#itemCd_" + i).append('<option value="5336">NE/ANS/PCD/EYE/DEL</option>');
	        	$("#itemCd_" + i).append('<option value="9999">Others</option>');
	        	break;
	        case 'SUG':
	        	$("#itemCd_" + i).append('<option value="200011490">Mobile Service</option>');
	        	$("#itemCd_" + i).append('<option value="200011491">Service Bundle with Mobile Handset</option>');
	        	$("#itemCd_" + i).append('<option value="200011492">PKWF/ANS/PCD</option>');
	        	$("#itemCd_" + i).append('<option value="9999">Others</option>');
	        	break;
	        case 'YAM':
	        	$("#itemCd_" + i).append('<option value="CONL20C010">Mobile Product</option>');
	        	$("#itemCd_" + i).append('<option value="CONL20E005">NE/ANS/PCD/EYE/DEL</option>');
	        	$("#itemCd_" + i).append('<option value="CONL20G008">NE Bundle Service</option>');
	        	$("#itemCd_" + i).append('<option value="9999">Others</option>');
	        	break;
	        case 'YUM':
	        	$("#itemCd_" + i).append('<option value="265181 1000000">Mobile Product</option>');
	        	$("#itemCd_" + i).append('<option value="9999">Others</option>');
				break;
	        case 'YPM':
	        	if (payType != 'I') {
	        		//$("#itemCd_" + i).append('<option value="214553">PCCW Mobile Products</option>');
	        	} else {
	        		$(".installHide_" + i).hide();
	        		$(".installShow_" + i).show();
	        	}
				break;
		 };
	}
	
	function refreshInstList(i) {
		if ($('#bankCd_' + i).val() != "NONE" && $('#amount_' + i).val() > 0) {
			loadCcInstList($('#bankCd_' + i).val(), $('#amount_' + i).val(), i);
		} else {
			$('#installmentSchedule_' + i).empty();
			$("<option value='0'>Select...</option>").appendTo($('#installmentSchedule_' + i));
		}
	}
	
	function loadCcInstList(bankCd,upfrontAmt, entry){
		$.ajax({				
			url : 'mobccspaymentupfrontajax.html',
			type : 'POST',
			cache : false,
			data : {
				type : 'CcInstList',
				bankCd : bankCd,
				upfrontAmt : upfrontAmt
			},
			dataType : 'JSON',
			timeout : 5000,
			error : function() {
				alert('Error loading Credit Card Installment Schedule!');
			},
			success : function(msg) {
					$('#installmentSchedule_' + entry).empty();
					$("<option value='0'>Select...</option>").appendTo($('#installmentSchedule_' + entry));
					$.each(eval(msg), function(i, item) {
						$("<option value='" + item.instSchedule + "'>"
											+ item.instSchedule
											+ "</option>").appendTo(
											$('#installmentSchedule_' + entry));
						<c:forEach items="${mobDsPaymentUpfront.paymentTransList}" var="ptList" varStatus="ptRow">
							if (entry == "${ptRow.index}") {
								$('#installmentSchedule_' + entry).val("${ptList.ccInstSchedule}");
							}
						</c:forEach>
					});
			}
		});
	}
	
	function refreshBankList(i) {
		if($('#bankCd_' + i).display != 'none') {
			loadCcInstBankList($('#payType_' + i).val(), $('#amount_' + i).val(), i);
			if($('#bankCd_' + i) == "NONE") {
				$('#installmentSchedule_' + i).empty();
				$("<option value='0'>Select...</option>").appendTo($('#installmentSchedule_' + i));
			} else {
				loadCcInstList($('#bankCd_' + i).val(), $('#amount_' + i).val(), i);
				$('#installmentSchedule_' + i).val("NONE");
			}
		}
	}
	
	function loadCcInstBankList(payMtdType,upfrontAmt, entry){
		$.ajax({				
			url : 'mobccspaymentupfrontajax.html',
			type : 'POST',
			cache : false,
			data : {
				type : 'CcInstBankList',
				payMtdType : payMtdType,
				upfrontAmt : upfrontAmt
			},
			dataType : 'JSON',
			timeout : 5000,
			error : function() {
				alert('Error loading Credit Card Bank List! (' + payMtdType + ", " + upfrontAmt + ")");
			},
			success : function(msg) {
					$('#bankCd_' + entry).empty();
					$("<option value='NONE'>Select...</option>").appendTo($('#bankCd_' + entry));
					$.each(eval(msg), function(i, item) {
						$("<option value='" + item.bankCd + "'>"
								+ item.bankName
								+ "</option>").appendTo(
								$('#bankCd_' + entry));
						
					<c:forEach items="${mobDsPaymentUpfront.paymentTransList}" var="ptList" varStatus="ptRow">
						if (entry == "${ptRow.index}") {
							$('#bankCd_' + entry).val("${ptList.ccIssueBank}");
						}
					</c:forEach>
				});						
			}
		});
	}
	
	function setTotalPaid() {
		var totalpaid = 0;
		$('.amount').each(function(){
			var amountval = parseFloat($(this).val());
			amountval = (isNaN(amountval)? 0 : amountval);
			totalpaid += amountval;
			$(this).val(amountval);
		});
		$("#totalPaidAmt").html(totalpaid);
	}
	
	$(document).ready(function() {
		$('.amount').change(function(){
			setTotalPaid();
		});
		setTotalPaid();	
		
		<c:forEach items="${mobDsPaymentUpfront.paymentTransList}" var="ptList" varStatus="ptRow">
			var i = "${ptRow.index}";
			if ($('#storeCd_' + i).val() != "NONE") {
				loadItemCd(i, "${ptList.paymentType}");
				$("#itemCd_" + i).val("${ptList.paymentItemCd}");
			}
			if($('#storeCd_' + i).filter('.installStoreCd').val() == "YPM"){
				$(".installHide_" + i).hide();
        		$(".installShow_" + i).show();
			} else {
				$(".installHide_" + i).show();
        		$(".installShow_" + i).hide();
			}
			if ("${ptList.paymentType}" != 'M'){
				loadCcInstBankList("${ptList.paymentType}", "${ptList.paymentAmount}", i);
				if ("${ptList.paymentType}" == 'I' && "${ptList.paymentAmount}" > 0) {
					loadCcInstList("${ptList.ccIssueBank}", "${ptList.paymentAmount}", i);
				}
			}
		</c:forEach>
	});
</script>
<%@ page import="com.bomwebportal.dto.OrderDTO" %>
<%
String orderStatus = "INITIAL";
OrderDTO orderDto = (OrderDTO) request.getSession().getAttribute("orderDtoOriginal");

if (orderDto != null) {
	orderStatus = orderDto.getOrderStatus();
}
%>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobpayment" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>
<br/>
<form:form method="POST" name="paymentForm" id="paymentForm" commandName="mobDsPaymentUpfront">
	<input type="hidden" name="currentView" value="mobdspaymentupfront" />
	<input type="hidden" name="ceksSubmit" value="" /> 
	<input type="hidden" name="actionType" id="actionType"/>
	
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td class="table_title">Upfront Payment </td>
				</tr>
			</table>
	<c:choose>
	<c:when test="${sessionScope.orderDto.orderStatus != null && (sessionScope.orderDto.orderStatus != 'INITIAL') && (sessionScope.orderDto.orderStatus != 'REJECTED') && (sessionScope.bomsalesuser.category != 'ORDER SUPPORT')}" >
			<table width="100%"  class="tablegrey" background="images/background2.jpg">
			<tr>
	   			<td class="contenttextgreen" colspan=3>
					<div class="buttonmenubox_L" id="buttonArea">
					<% if (!"ORDER SUPPORT".equals(bomsalesuser.getCategory())) { %>
						<font>Not allow change Payment : <a href="supportdoc.html" class="nextbutton3">Proceed with Support Doc page </a>
						</font>
					<% } else { %>
						<font>Not allow change Payment : <a href="#" class="nextbutton3" onclick="javascript:formSubmit('');">Proceed with updated information </a>
						</font>
					<% } %>
					</div>
				</td>   	 
	   		</tr>
	   		<tr><td>
	   		<table border="0" cellspacing="5" margin="0" class="contenttext" width="100%">
	   		<% int i = 1; %>
			<c:forEach items="${mobDsPaymentUpfront.paymentTransList}" var="ptList" varStatus="ptRow">
			<% if (i % 3 == 1) { %><tr><% } %>
			<c:if test="${ptList.paymentType == 'M' }">
				<td valign="top"><fieldset style="background-color:#ffffff;" >
					<legend><b><%=i%>. Cash Payment</b></legend>
					<table cellspacing="5">
					
					<c:if test="${salesChannelCd == 'MDV' }">
					<% if ("ORDER SUPPORT".equals(bomsalesuser.getCategory())) { %>
					<tr><td>&nbsp;</td><td align="right">7-11 Shop Code:</td><td><form:input path="paymentTransList[${ptRow.index}].paymentStoreCd" maxlength="20" /></td></tr>
					<% } else { %>
					<tr><td>&nbsp;</td><td align="right">7-11 Shop Code:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].paymentStoreCd}</td></tr>
					<% } %>
					</c:if>
					<c:if test="${salesChannelCd == 'SIS' }">
					<tr><td>&nbsp;</td><td align="right">Store Code:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].storeCd}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Payment Item Code:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].paymentItemCd}</td></tr>
					</c:if>
					<tr><td>&nbsp;</td><td align="right">Payment Amount:</td><td>HKD${mobDsPaymentUpfront.paymentTransList[ptRow.index].paymentAmount}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Transaction Date:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].transDate}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Invoice No.:</td>
					<% if (!"ORDER SUPPORT".equals(bomsalesuser.getCategory())) { %>
						<td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].invoiceNo}</td>
					<% } else { %>
						<td><form:input path="paymentTransList[${ptRow.index}].invoiceNo" maxlength="15" /> </td>
					<% } %>
					</tr>
					<tr><td>&nbsp;</td><td align="right">Remark:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].remark}</td></tr>
					</table></fieldset></td>
			</c:if>
			<c:if test="${ptList.paymentType == 'C' }">
				<td valign="top"><fieldset style="background-color:#ffffff;" >
					<legend><b><%=i%>. Credit Card Payment</b></legend>
					<table cellspacing="5">
					<c:if test="${salesChannelCd == 'SIS' }">
					<tr><td>&nbsp;</td><td align="right">Store Code:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].storeCd}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Payment Item Code:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].paymentItemCd}</td></tr>
					</c:if>
					<c:if test="${salesChannelCd == 'MDV' }">
					<tr><td>&nbsp;</td><td align="right">Card Holder Name:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccHolderName}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Self/Third Party:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].thirdPartyInd}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Credit Card Type:</td><td>
					<c:choose>
						<c:when test="${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccType == '01'}">
						01 - VISA
						</c:when>
						<c:when test="${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccType == '02'}">
						02 - MASTER CARD
						</c:when>
						<c:when test="${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccType == '04'}">
						04 - AMERICAN EXPRESS
						</c:when>
						<c:otherwise>
						${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccType}
						</c:otherwise>
					</c:choose>
					</td></tr>
					<tr><td>&nbsp;</td><td align="right">Expiry Date:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccExpiryMonth} / ${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccExpiryYear}<form:errors path="paymentTransList[${ptRow.index}].ccExpiryYear" cssClass="error" /></td></tr>
					<tr><td>&nbsp;</td><td align="right">Bank of issue:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccIssueBank}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Card Number:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccNum}</td></tr>
					</c:if>
					<tr><td>&nbsp;</td><td align="right">Payment Amount:</td><td>HKD${mobDsPaymentUpfront.paymentTransList[ptRow.index].paymentAmount}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Transaction Date:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].transDate}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Approve Code:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].approveCode}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Remark:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].remark}</td></tr>
					</table></fieldset></td>
			</c:if>
			<c:if test="${ptList.paymentType == 'I' }">
				<td valign="top"><fieldset style="background-color:#ffffff;" >
					<legend><b><%=i%>. Credit Card Installment Payment</b></legend>
					<table cellspacing="5">
					<c:if test="${salesChannelCd == 'SIS' }">
					<tr><td>&nbsp;</td><td align="right">Store Code:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].storeCd}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Payment Item Code:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].paymentItemCd}</td></tr>
					</c:if>
					<tr><td>&nbsp;</td><td align="right">Card Holder Name:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccHolderName}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Self/Third Party:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].thirdPartyInd}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Credit Card Type:</td><td>
					<c:choose>
						<c:when test="${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccType == '01'}">
						01 - VISA
						</c:when>
						<c:when test="${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccType == '02'}">
						02 - MASTER CARD
						</c:when>
						<c:when test="${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccType == '04'}">
						04 - AMERICAN EXPRESS
						</c:when>
						<c:otherwise>
						${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccType}
						</c:otherwise>
					</c:choose>
					</td></tr>
					<tr><td>&nbsp;</td><td align="right">Expiry Date:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccExpiryMonth} / ${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccExpiryYear}<form:errors path="paymentTransList[${ptRow.index}].ccExpiryYear" cssClass="error" /></td></tr>
					<tr><td>&nbsp;</td><td align="right">Bank of issue:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccIssueBank}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Card Number:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccNum}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Installment Period:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].ccInstSchedule}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Payment Amount:</td><td>HKD${mobDsPaymentUpfront.paymentTransList[ptRow.index].paymentAmount}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Transaction Date:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].transDate}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Approve Code:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].approveCode}</td></tr>
					<tr><td>&nbsp;</td><td align="right">Remark:</td><td>${mobDsPaymentUpfront.paymentTransList[ptRow.index].remark}</td></tr>
					</table></fieldset></td>
			</c:if>
			<% if (i % 3 == 0) { %></tr><% } %>
			<% i++; %>
			</c:forEach>
	   		
	   		</table></td></tr>
	   		</table>
	</c:when>
	<c:otherwise>
			<br/>
			<div class="row" style="font-weight: bold; color:blue">
				Note: please enter the date in dd/mm/yyyy format
			</div>
			<br/>
			<fieldset>
				<legend><b>Cash</b></legend>
				<table width="100%" border="0" cellspacing="5" margin="0" class="contenttext">
					<% int i = 1; %>
					<c:forEach items="${mobDsPaymentUpfront.paymentTransList}" var="ptList" varStatus="ptRow">
					<c:if test="${ptList.paymentType == 'M' }">
					<tr><td colspan="5"><hr align="center" /></td></tr>
					<tr>
						<td><%=i%>.</td>
						<td align="right">Amount(HK$)<span class="contenttext_red">*</span>:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].paymentAmount" maxlength="8" cssClass="amount"/> </td>
						<td align="right">Transaction date<span class="contenttext_red">*</span>:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].transDate" maxlength="10" size="10"/> </td>
						</tr>
					<tr>
						<td></td><td></td>
						<td><form:errors path="paymentTransList[${ptRow.index}].paymentAmount" cssClass="error" /> </td><td></td>
						<td><form:errors path="paymentTransList[${ptRow.index}].transDate" cssClass="error" /> </td>
					</tr>
					<c:if test="${salesChannelCd == 'SIS'}">
						<tr>
						<td></td>
							<td align="right">Store Code<span class="contenttext_red">*</span>:</td>
							<td><form:select path="paymentTransList[${ptRow.index}].storeCd" id="storeCd_${ptRow.index}" onchange="javascript:loadItemCd(${ptRow.index}, 'M');" > 
								<form:option value="NONE" label="Select.."></form:option>
								<form:option value="JSM" label="AEON"></form:option>
								<form:option value="SUG" label="SUNING"></form:option>
								<form:option value="YAM" label="DENKIYA"></form:option>
								<form:option value="YPM" label="PRICERITE"></form:option>
								<form:option value="YUM" label="UNY"></form:option>
								</form:select></td>
							<td align="right">Payment Item Code<span class="contenttext_red">*</span>:</td>
							<td><form:select path="paymentTransList[${ptRow.index}].paymentItemCd" id="itemCd_${ptRow.index}">
								<form:option value="NONE" label="Select.."></form:option>
								</form:select></td>
					</tr><tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].storeCd" cssClass="error" /> </td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].paymentItemCd" cssClass="error" /> </td>
							</tr>
					</c:if>
					<c:if test="${salesChannelCd == 'MDV'}">
						<tr>
							<td></td>
							<td align="right">7-11 Shop Code:</td>
							<td><form:input path="paymentTransList[${ptRow.index}].paymentStoreCd" maxlength="20" />
					</tr><tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].paymentStoreCd" cssClass="error" /> </td>
						</tr>
					</c:if>			
					<tr>
						<td></td>	
						<td align="right">Invoice No.:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].invoiceNo" maxlength="15" /> </td>
					</tr>
					<tr>
						<td></td><td></td>
						<td><form:errors path="paymentTransList[${ptRow.index}].invoiceNo" cssClass="error" /> </td>
					</tr>
					<tr>
						<td></td>
						<td align="right">Remark:</td><td colspan="3"><form:textarea path="paymentTransList[${ptRow.index}].remark" rows="2" cols="60"/></td>
					</tr>
					<% i++; %>
					<tr>
						<td colspan="5" align="center"><input type="button" value="Remove" onClick="javascript:deleteRow(${ptRow.index});"/></td>
					</tr>
					</c:if>
					</c:forEach>
				</table>			
				<input type="button" value="Add Cash Payment" onClick="javascript:formSubmit('AddCash')"/>
			</fieldset>
			<br/>
			<fieldset>
				<legend><b>Credit Card</b></legend>
				<table width="100%" border="0" cellspacing="5" margin="0" class="contenttext">
					<% i = 1; %>
					<c:forEach items="${mobDsPaymentUpfront.paymentTransList}" var="ptList" varStatus="ptRow">
					<c:if test="${ptList.paymentType == 'C' }">
					<tr><td colspan="5"><hr align="center" /></td></tr>
					<tr>
						<td><%=i%>.</td>
						<td align="right">Amount(HK$)<span class="contenttext_red">*</span>:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].paymentAmount" maxlength="8" cssClass="amount"
								onchange="javascript:refreshBankList(${ptRow.index});" id="amount_${ptRow.index}" /> </td>
						<td align="right">Transaction date<span class="contenttext_red">*</span>:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].transDate" maxlength="10" size="10"/> </td>
						</tr>
					<tr>
						<td></td><td></td>
						<td><form:errors path="paymentTransList[${ptRow.index}].paymentAmount" cssClass="error" /> </td><td></td>
						<td><form:errors path="paymentTransList[${ptRow.index}].transDate" cssClass="error" /> </td>
					</tr>
					<c:if test="${salesChannelCd == 'SIS'}">
						<tr>
						<td></td>
							<td align="right">Store Code<span class="contenttext_red">*</span>:</td>
							<td><form:select path="paymentTransList[${ptRow.index}].storeCd" id="storeCd_${ptRow.index}" onchange="javascript:loadItemCd(${ptRow.index}, 'C');"> 
								<form:option value="NONE" label="Select.."></form:option>
								<form:option value="JSM" label="AEON"></form:option>
								<form:option value="SUG" label="SUNING"></form:option>
								<form:option value="YAM" label="DENKIYA"></form:option>
								<form:option value="YPM" label="PRICERITE"></form:option>
								<form:option value="YUM" label="UNY"></form:option>
								</form:select></td>
							<td align="right">Payment Item Code<span class="contenttext_red">*</span>:</td>
							<td><form:select path="paymentTransList[${ptRow.index}].paymentItemCd" id="itemCd_${ptRow.index}">
								<form:option value="NONE" label="Select.."></form:option>
								</form:select> </td>
						</tr><tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].storeCd" cssClass="error" /> </td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].paymentItemCd" cssClass="error" /> </td>
						</tr>
					</c:if>
					<c:if test="${salesChannelCd == 'MDV'}">
						<tr>
							<td></td>
							<td align="right">Card Holder Name<span class="contenttext_red">*</span>:</td>
							<td><form:input path="paymentTransList[${ptRow.index}].ccHolderName" size="30" maxlength="50"/> </td>
							<td align="right">Self/Third Party<span class="contenttext_red">*</span>:</td>
							<td>
								<form:select path="paymentTransList[${ptRow.index}].thirdPartyInd">								
								<form:option value="N" label="Self" />
								<form:option value="Y" label="Third Party" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].ccHolderName" cssClass="error" /> </td>
						</tr>
						<tr>
							<td></td>
							<td align="right">Credit Card Type<span class="contenttext_red">*</span>:</td>
							<td>
								<form:select path="paymentTransList[${ptRow.index}].ccType">
									<form:option value="NONE" label="Select.." />
									<form:option value="01" label="VISA" />
									<form:option value="02" label="MASTER" />
									<form:option value="04" label="AMEX" />
								</form:select>
							</td>
							<td align="right">Expiry Date<span class="contenttext_red">*</span>:</td>
							<td><form:select path="paymentTransList[${ptRow.index}].ccExpiryMonth">						
								<form:option value="01" label="01" />
								<form:option value="02" label="02" />
								<form:option value="03" label="03" />
								<form:option value="04" label="04" />
								<form:option value="05" label="05" />
								<form:option value="06" label="06" />
								<form:option value="07" label="07" />
								<form:option value="08" label="08" />
								<form:option value="09" label="09" />
								<form:option value="10" label="10" />
								<form:option value="11" label="11" />
								<form:option value="12" label="12" />																																					
								</form:select>
								<form:select path="paymentTransList[${ptRow.index}].ccExpiryYear">						
								<%for (int temp=0;temp<15;temp++){ %>
									<form:option value="<%=expiryYear+temp%>" label="<%=Integer.toString(expiryYear+temp)%>" />
								<%} %>	
								</form:select> </td>
						</tr>
						<tr>
							<td></td>
							<td></td><td><form:errors path="paymentTransList[${ptRow.index}].ccType" cssClass="error" /></td>
							<td></td><td><form:errors path="paymentTransList[${ptRow.index}].ccExpiryYear" cssClass="error" /></td>	
						</tr>
						<tr>
							<td></td>
							<td align="right">Bank of issue<span class="contenttext_red">*</span>:</td>
							<td colspan=3>
								<form:select path="paymentTransList[${ptRow.index}].ccIssueBank" id="bankCd_${ptRow.index}">
									<form:option value="NONE" label="Select.." />
								</form:select>
							</td>						
						<tr>
							<td></td>
							<td></td><td colspan=3><form:errors path="paymentTransList[${ptRow.index}].ccIssueBank" cssClass="error" /></td>
						</tr>
						<tr>
							<td></td>
							<td align="right">Card Number<span class="contenttext_red">*</span>:</td>
							<td colspan=3><form:input path="paymentTransList[${ptRow.index}].ccNum" size="30" maxlength="16" readonly="false" />
								<a href="#" class="nextbutton3" onClick="javascript:ceksFormSubmit(${ptRow.index});">Input Credit Card No.</a> </td>
							
						</tr>
						<tr>
							<td></td><td></td>
							<td colspan=3><form:errors path="paymentTransList[${ptRow.index}].ccNum" cssClass="error" /> </td><td></td>
								
						</tr>
					</c:if>
					<tr>
						<td></td>
						<td align="right">Approve Code<span class="contenttext_red">*</span>:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].approveCode" maxlength="20" /> </td>
					</tr>
					<tr>
						<td></td><td></td>
						<td><form:errors path="paymentTransList[${ptRow.index}].approveCode" cssClass="error" /> </td>
					</tr>
					<tr>
						<td></td>
						<td align="right">Remark: </td>
						<td colspan=3><form:textarea path="paymentTransList[${ptRow.index}].remark" rows="2" cols="60"/></td>
						<td><input type="hidden" name="payType" value="C" id="payType_${ptRow.index}" class="payType"/></td>
					</tr>
					<% i++; %>
					<tr>
						<td colspan="5" align="center"> <input type="button" value="Remove" onClick="javascript:deleteRow(${ptRow.index});"/></td>
					</tr>
					</c:if>
					</c:forEach>
				</table>
				<input type="button" value="Add Credit Card Payment" onClick="javascript:formSubmit('AddCC')"/>
			</fieldset>
			<br/>
			<fieldset>
				<legend><b>Credit Card Installment</b></legend>
				<table width="100%" border="0" cellspacing="5" margin="0" class="contenttext">
					<% i = 1; %>
					<c:forEach items="${mobDsPaymentUpfront.paymentTransList}" var="ptList" varStatus="ptRow">
					<c:if test="${ptList.paymentType == 'I' }">
					<tr><td colspan="7"><hr align="center" /></td></tr>
					<tr>
						<td><%=i%>.<br/></td>
						<td align="right">Amount(HK$)<span class="contenttext_red">*</span>:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].paymentAmount" maxlength="8" cssClass="amount"
								onchange="javascript:refreshBankList(${ptRow.index});" id="amount_${ptRow.index}" /> </td>
						<td align="right">Transaction date<span class="contenttext_red">*</span>:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].transDate" maxlength="10" size="10"/> </td>
						</tr>
					<tr>
						<td></td><td></td>
						<td><form:errors path="paymentTransList[${ptRow.index}].paymentAmount" cssClass="error" /> </td><td></td>
						<td><form:errors path="paymentTransList[${ptRow.index}].transDate" cssClass="error" /> </td>
					</tr>
					<c:if test="${salesChannelCd == 'SIS'}">
						<tr>
						<td></td>
							<td align="right">Store Code<span class="contenttext_red">*</span>:</td>
							<td><form:select path="paymentTransList[${ptRow.index}].storeCd" id="storeCd_${ptRow.index}" onchange="javascript:loadItemCd(${ptRow.index}, 'I');" cssClass="installStoreCd"> 
								<form:option value="NONE" label="Select.."></form:option>
								<form:option value="JSM" label="AEON"></form:option>
								<form:option value="SUG" label="SUNING"></form:option>
								<form:option value="YAM" label="DENKIYA"></form:option>
								<form:option value="YPM" label="PRICERITE"></form:option>
								<form:option value="YUM" label="UNY"></form:option>
								</form:select></td>
						</tr><tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].storeCd" cssClass="error" /></td>
						</tr>
						<tbody class="installHide_${ptRow.index}"><tr>
						<td></td>
							<td align="right">Payment Item Code<span class="contenttext_red">*</span>:</td>
							<td><form:select path="paymentTransList[${ptRow.index}].paymentItemCd" id="itemCd_${ptRow.index}">
								<form:option value="NONE" label="Select.."></form:option>
								</form:select></td>
						</tr><tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].paymentItemCd" cssClass="error" /></td>
						</tr></tbody>
						<tbody class="installShow_${ptRow.index}">
						<tr>
							<td></td>
							<td align="right">Card Holder Name<span class="contenttext_red">*</span>:</td>
							<td><form:input path="paymentTransList[${ptRow.index}].ccHolderName" size="30" maxlength="50"/></td>
							<td align="right">Self/Third Party <span class="contenttext_red">*</span>:</td>
							<td>
								<form:select path="paymentTransList[${ptRow.index}].thirdPartyInd">								
								<form:option value="N" label="Self" />
								<form:option value="Y" label="Third Party" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].ccHolderName" cssClass="error" /> </td>
						</tr>
						<tr>
							<td></td>
							<td align="right">Credit Card Type<span class="contenttext_red">*</span>:</td>
							<td>
								<form:select path="paymentTransList[${ptRow.index}].ccType">
									<form:option value="NONE" label="Select.." />
									<form:option value="01" label="VISA" />
									<form:option value="02" label="MASTER" />
									<form:option value="04" label="AMEX" />
								</form:select>
							</td>
							<td align="right">Expiry Date<span class="contenttext_red">*</span>:</td>
							<td><form:select path="paymentTransList[${ptRow.index}].ccExpiryMonth">						
								<form:option value="01" label="01" />
								<form:option value="02" label="02" />
								<form:option value="03" label="03" />
								<form:option value="04" label="04" />
								<form:option value="05" label="05" />
								<form:option value="06" label="06" />
								<form:option value="07" label="07" />
								<form:option value="08" label="08" />
								<form:option value="09" label="09" />
								<form:option value="10" label="10" />
								<form:option value="11" label="11" />
								<form:option value="12" label="12" />																																					
								</form:select>
								<form:select path="paymentTransList[${ptRow.index}].ccExpiryYear">						
								<%for (int temp1=0;temp1<15;temp1++){ %>
									<form:option value="<%=expiryYear+temp1%>" label="<%=Integer.toString(expiryYear+temp1)%>" />
								<%} %>	
								</form:select> </td>	
						</tr>
						<tr>
							<td></td>
							<td></td><td><form:errors path="paymentTransList[${ptRow.index}].ccType" cssClass="error" /></td>
							<td></td><td><form:errors path="paymentTransList[${ptRow.index}].ccExpiryYear" cssClass="error" /></td><td></td>
						</tr>
						<tr>
							<td></td>
							<td align="right">Bank of issue<span class="contenttext_red">*</span>:</td>
							<td colspan=3>
								<form:select path="paymentTransList[${ptRow.index}].ccIssueBank" 
									onchange="javascript:refreshInstList(${ptRow.index});" id="bankCd_${ptRow.index}">
									<form:option value="NONE" label="Select.." />
								</form:select>
							</td>
						<tr>
							<td></td><td></td>
							<td colspan=3><form:errors path="paymentTransList[${ptRow.index}].ccIssueBank" cssClass="error" /></td><td></td>
						</tr>
						<tr>
							<td></td>
							<td align="right">Card Number<span class="contenttext_red">*</span>:</td>
							<td colspan=3><form:input path="paymentTransList[${ptRow.index}].ccNum" size="30" maxlength="16" readonly="false" />
								<a href="#" class="nextbutton3" onClick="javascript:ceksFormSubmit(${ptRow.index});">Input Credit Card No.</a> </td>
						</tr>
						<tr>
							<td></td><td></td>
							<td colspan=3><form:errors path="paymentTransList[${ptRow.index}].ccNum" cssClass="error" /></td><td></td>
							
						</tr>
						<tr>
							<td></td>
							<td align="right">Installment Period<span class="contenttext_red">*</span>:</td>
							<td>
								<form:select path="paymentTransList[${ptRow.index}].ccInstSchedule" id="installmentSchedule_${ptRow.index}">
								<form:option value="0" label="Select.." />
								</form:select> months
							</td>
						</tr>
						<tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].ccInstSchedule" cssClass="error" /></td>
						</tr></tbody>
					</c:if>
					<c:if test="${salesChannelCd == 'MDV'}">
						<tr>
							<td></td>
							<td align="right">Card Holder Name<span class="contenttext_red">*</span>:</td>
							<td><form:input path="paymentTransList[${ptRow.index}].ccHolderName" size="30" maxlength="50"/> </td>
							<td align="right">Self/Third Party <span class="contenttext_red">*</span>:</td>
							<td>
								<form:select path="paymentTransList[${ptRow.index}].thirdPartyInd">								
								<form:option value="N" label="Self" />
								<form:option value="Y" label="Third Party" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].ccHolderName" cssClass="error" /> </td>
						</tr>
						<tr>
							<td></td>
							<td align="right">Credit Card Type<span class="contenttext_red">*</span>:</td>
							<td>
								<form:select path="paymentTransList[${ptRow.index}].ccType">
									<form:option value="NONE" label="Select.." />
									<form:option value="01" label="VISA" />
									<form:option value="02" label="MASTER" />
									<form:option value="04" label="AMEX" />
								</form:select>
							</td>
							<td align="right">Expiry Date<span class="contenttext_red">*</span>:</td>
							<td><form:select path="paymentTransList[${ptRow.index}].ccExpiryMonth">						
								<form:option value="01" label="01" />
								<form:option value="02" label="02" />
								<form:option value="03" label="03" />
								<form:option value="04" label="04" />
								<form:option value="05" label="05" />
								<form:option value="06" label="06" />
								<form:option value="07" label="07" />
								<form:option value="08" label="08" />
								<form:option value="09" label="09" />
								<form:option value="10" label="10" />
								<form:option value="11" label="11" />
								<form:option value="12" label="12" />																																					
								</form:select>
								<form:select path="paymentTransList[${ptRow.index}].ccExpiryYear">						
								<%for (int temp2=0;temp2<15;temp2++){ %>
									<form:option value="<%=expiryYear+temp2%>" label="<%=Integer.toString(expiryYear+temp2)%>" />
								<%} %>	
								</form:select> </td>	
						</tr>
						<tr>
							<td></td>
							<td></td><td><form:errors path="paymentTransList[${ptRow.index}].ccType" cssClass="error" /></td>
							<td></td><td><form:errors path="paymentTransList[${ptRow.index}].ccExpiryYear" cssClass="error" /></td><td></td>
						</tr>
						<tr>
							<td></td>
							<td align="right">Bank of issue<span class="contenttext_red">*</span>:</td>
							<td colspan=3>
								<form:select path="paymentTransList[${ptRow.index}].ccIssueBank" 
									onchange="javascript:refreshInstList(${ptRow.index});" id="bankCd_${ptRow.index}">
									<form:option value="NONE" label="Select.." />
								</form:select>
							</td>
						<tr>
							<td></td><td></td>
							<td colspan=3><form:errors path="paymentTransList[${ptRow.index}].ccIssueBank" cssClass="error" /></td><td></td>
						</tr>
						<tr>
							<td></td>
							<td align="right">Card Number<span class="contenttext_red">*</span>:</td>
							<td colspan=3><form:input path="paymentTransList[${ptRow.index}].ccNum" size="30" maxlength="16" readonly="false" />
								<a href="#" class="nextbutton3" onClick="javascript:ceksFormSubmit(${ptRow.index});">Input Credit Card No.</a> </td>
						</tr>
						<tr>
							<td></td><td></td>
							<td colspan=3><form:errors path="paymentTransList[${ptRow.index}].ccNum" cssClass="error" /></td><td></td>
							
						</tr>
						<tr>
							<td></td>
							<td align="right">Installment Period<span class="contenttext_red">*</span>:</td>
							<td>
								<form:select path="paymentTransList[${ptRow.index}].ccInstSchedule" id="installmentSchedule_${ptRow.index}">
								<form:option value="0" label="Select.." />
								</form:select> months
							</td>
						</tr>
						<tr>
							<td></td><td></td>
							<td><form:errors path="paymentTransList[${ptRow.index}].ccInstSchedule" cssClass="error" /></td>
						</tr>
					</c:if>
					<tr>
						<td></td>
						<td align="right">Approve Code<span class="contenttext_red">*</span>:</td>
						<td><form:input path="paymentTransList[${ptRow.index}].approveCode" maxlength="20" /> </td>
					</tr>
					<tr>
						<td></td><td></td>
						<td><form:errors path="paymentTransList[${ptRow.index}].approveCode" cssClass="error" /> </td>
					</tr>
					<tr>
						<td></td>
						<td align="right">Remark: </td><td colspan="3"><form:textarea path="paymentTransList[${ptRow.index}].remark" rows="2" cols="60" /></td>
						<td><input type="hidden" name="payType" value="I" id="payType_${ptRow.index}" class="payType"/></td>
					</tr>
					<% i++; %>
					<tr>
						<td colspan="7" align="center"> <input type="button" value="Remove" onClick="javascript:deleteRow(${ptRow.index});"/></td>
					</tr>
					</c:if>
					</c:forEach>
				</table>
				<input type="button" value="Add Credit Card Installment Payment" onClick="javascript:formSubmit('AddCCInstallment')"/></td></tr>
			</fieldset>
			<br/>
			<table border="0" cellspacing="0" class="contenttext">
			<tr><td align="right"><b>Upfront Payment Required:</b> </td>
				<td>$${mobDsPaymentUpfront.totalUpfrontAmount}</td>
			</tr>
			<tr><td align="right"><b>Upfront HS Payment Amount:</b> </td>
				<td>$${mobDsPaymentUpfront.hsUpfrontAmount}
				<form:errors path="hsUpfrontAmount" cssClass="error" /> </td>
			</tr>
			<tr><td align="right"><b>Total Paid Amount:</b> </td>
				<td>$<span id="totalPaidAmt">${totalPaidAmount}</span>
				<form:errors path="totalPaidAmount" cssClass="error" /> </td>
			</tr>
			</table>
			<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3" onClick="javascript:formSubmit('continue');">continue &gt;</a></div>
	</c:otherwise>
	</c:choose>
			</td>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>