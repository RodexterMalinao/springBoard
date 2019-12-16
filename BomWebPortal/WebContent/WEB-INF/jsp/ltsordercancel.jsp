<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<form:form method="POST" id="ltsOrderCancelForm" name="ltsOrderCancelForm" commandName="ltsOrderCancelCmd" autocomplete="off">
<table width="100%" class="tablegrey" >
	<tr>
		<td width="100%" >
				<div id="orderCancelDiv" >
				<table width="100%" cellspacing="0" border="0" bgcolor="white">
					<tr> 
                 		<td colspan="4" class="table_title">Order Cancellation<br></td>
          			</tr>
          			<tr> 
                 		<td colspan="4">&nbsp;</td>
          			</tr>
          			<tr>
           				<td width="5%">&nbsp;</td>
           				<td width="15%">Cancel Reason<span class="contenttext_red">*</span>: </td>
           				<td width="70%" colspan="2">
           					<form:select id="cancelReason" path="cancelReason">
           						<form:option value=""> -- SELECT -- </form:option>
								<form:options items="${cancelReasonList}" itemValue="itemKey" itemLabel="itemValue" />
							</form:select>
           					<form:errors path="cancelReason" cssClass="error"/>
           				</td>
           			</tr>
           			<tr>
           				<td width="5%">&nbsp;</td>
           				<td width="15%">Cancel Remark<span class="contenttext_red">*</span> : </td>
           				<td width="70%" colspan="2">
	                   		<form:textarea path="cancelRemark" rows="5" cols="80" />
	                   		<form:errors path="cancelRemark" cssClass="error"/>
	                   	</td>
           			</tr>
           			<tr> 
                 		<td colspan="4">&nbsp;</td>
          			</tr>
          		</table>
			</div>
			</td>
			</tr>
		</table>
</form:form>

<br>
<div id="submitBtnDiv">
	<table width="100%">
		<tr><td align="right">
			<a href="#" class="nextbutton" id="submit">Submit</a>
			<a href="sboordersearch.html" class="nextbutton" id="back">Back</a>
		</td></tr>
	</table>
</div>

<script type="text/javascript">
	$("#submit").click(function(event) {
		event.preventDefault();
		$("#ltsOrderCancelForm").submit();
	});
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
