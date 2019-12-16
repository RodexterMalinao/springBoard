<%@ include file="/WEB-INF/jsp/header.jsp"%>
<script>
	function formSubmit() {
		var toSave = confirm("Confirm to save?");
		
		if (toSave) {
			document.falloutForm.submit();
	
		}
	}
	
	function callLogPopup(orderId) {
		var url = "mobccscalllog.html?orderId=" + orderId;
		window.open(url, 'CallLogHistory', 'height=500,width=1000,resizable=yes,scrollbars=yes')
	}
	
	function textCounter( field, maxlimit ) {
		  if ( field.value.length > maxlimit )
		  {
		    field.value = field.value.substring( 0, maxlimit );
		    return false;
		  }
		}
</script>
<form:form name="falloutForm" method="POST" commandName="fallout">

	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Fallout</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td>
							Fallout Reason
						</td>
						<td>
							<form:select path="falloutCode" >
								<form:options items="${falloutCodeList}" itemLabel="codeDesc" itemValue="codeId" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td>
							Fallout Remarks
						</td>
						<td>
							<form:textarea path="remarks" cols="43" rows="4" onkeyup="textCounter(this, 200);" />
						</td>
					</tr>
					<tr>
						<td>
							Fallout By
						</td>
						<td>
							<form:input path="staffId" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="Save" onClick="javascript:formSubmit();">
							<input type="button" value="Create/View Call Log" onClick="javascript:callLogPopup('${orderId}');">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
</form:form>