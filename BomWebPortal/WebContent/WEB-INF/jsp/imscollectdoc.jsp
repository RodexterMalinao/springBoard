<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />	

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.signaturepad.min.js"></script>
<script type="text/javascript" src="js/excanvas.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/web/lts/ltscollectdoc.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<style type="text/css">
.supportDocDiv table { border-collapse: collapse }
.supportDocDiv table td { border-width: 1px; padding: 3px; border-style: inset; text-align: center }
</style>

<form:form method="POST" id="imsCollectDocForm" name="imsCollectDocForm" commandName="imsCollectDocCmd">
<%-- 	<input type="hidden" name="orderid" value="${sessionScope.sbuid}" /> --%>
	<table width="100%" border="0" cellspacing="1" cellpadding="3" class="contenttext">
		<tr></tr>
		<tr>
			<td colspan="5" class="table_title">Support Document</td>
		</tr>
		<tr>
		<td colspan="4">
		<table width="100%" border="0" class="contenttext">
				<tr bgcolor="#FFFFFF">
					<td width="90%">
						<div class="supportDocDiv">
							<table width="100%" border="1">
								<tr bgcolor="#99C68E" >
									<td width="20%">Document Type</td>
									<td width="40%">Waive Reason</td>
									<td width="20%">Collected</td>
									<td width="20%">Fax Serial No.</td>
								</tr>
								<c:choose>
									<c:when test="${not empty imsCollectDocCmd.imsCollectDocDtoList}">
										<c:forEach items="${imsCollectDocCmd.imsCollectDocDtoList}" var="collectDoc" varStatus="status">
											<c:if test="${collectDoc.markDelInd != 'Y'}">
											<tr>
												<td>${collectDoc.docTypeDisplay}</td>
												<td><c:out value="${collectDoc.waiveReasonDisplay}" default="--" /></td>
												<td>${collectDoc.collectedInd != 'Y'? 'N': collectDoc.collectedInd}</td>
												<td><form:input id="fsno-${status.index}" cssClass="faxSerialInput" maxlength="6" path="imsCollectDocDtoList[${status.index}].faxSerial"/>
												<span id="fsnoError-${status.index}" class="error" style="display: none">Invalid Fax Serial No.</span></td>
											</tr>
											</c:if>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td align="center" colspan="4">NIL</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
					</td>
				</tr>
<%-- 				<c:if test="${not empty imsCollectDocCmd.imsCollectDocDtoList}"> --%>
<!-- 					<tr> -->
<!-- 						<td> -->
<!-- 							<input type="button" id="copySerialBtn" value="Copy to All" style="float: right;"/> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<%-- 				</c:if> --%>
		</table>
		</tr>
	</table>
	<c:if test="${not empty imsCollectDocCmd.imsCollectDocDtoList}">
		<table width="100%" border="0" cellspacing="0" class="contenttext">
			<tr>
				<td align="right" > 
					<br>
					<a href="#" id="submit" class="nextbutton" onClick="checkSubmit()">Submit</a> 
				</td>
			</tr>
		</table>
	</c:if>
</form:form>

<script type='text/javascript'>
	$(document).ready(function() {
		if(location.search.indexOf('submit=true') !== -1){
			//alert('aa');
			//window.opener.$.blockUI({ message: null });
			//window.opener.location.reload(true);
			window.close();
		}
	});
	
	$(".faxSerialInput").change(
		function(){
			var id = this.id;
			var n = id.indexOf("-");
			var eid = "fsnoError-"+id.substring(n+1);
			checkFaxSerialNumber(this.value, eid);	
		}
	);
	
	function checkFaxSerialNumber(phoneno, errorid){
		if(phoneno.length>0 && phoneno.match("[0-9][0-9][0-9][0-9][0-9][0-9]")==null){
			$("#"+errorid).show();			
			return false;
		}else{
			$("#"+errorid).hide();
			return true;
		}
	}	
	

	
	function checkSubmit(){	

		var test = false;
		$('.faxSerialInput').each(function(){
			var faxnum = this.value;
			if (faxnum){
				if(checkFaxSerialNumber(faxnum,"fsnoError")){
					test = true;
				}else{
					test = false;
					return;
				}
			}else{
				test = true;
			}
		});	
		
		if(test){
			formSubmit();
		}
	}
	
	function formSubmit(){	
		document.imsCollectDocForm.submit();
		//window.opener.location.reload(true);
		window.opener.refreshFS();
		window.close();					
	}
// 	$(ltscollectdoc.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>