<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript">

function checkStockQty(orderId) {
	$.ajax({
		url: "mobccscheckstockqtyajax.html"
		, data: {
			orderId: orderId
		}
		, cache: false
		, dataType: "json"
		, success: function(data) {
			var displayText = "Item Code          Location         Qty\n";
			$.each(data, function(index, item) {
				displayText += item.stock_qty + "\n";
			});
			alert(displayText);
		}
		, error: function() {
			alert("Cannot retrieve data. Please re-try later.");
		}
	});
}

function paymentTrxn() {
	var sURL = "<c:url value="mobccspaymenttranshistory.html"><c:param name="orderId" value="${orderId}"/></c:url>";
	var vArguments = new Object();
	var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
	window.showModalDialog(sURL, vArguments, sFeatures);
}

$(document).ready(function() {
	
	
	<c:if test="${not empty cancellationUI}">
		window.location = "customerprofile.html";//if cancel and recreate order, force start from customerprofile.html
	</c:if>
	$(".orderPreview").load("<c:url value="mobccspreview.html"><c:param name="orderId" value="${orderId}"/><c:param name="action" value="ENQUIRY"/></c:url> #wrapper"
				, function() {
					$(".orderPreview > div").removeAttr("id");
				});

});
function orderFormSubmit(){
	window.location = "welcome.html";
}
function customerProfilePage(){
	window.location = "customerprofile.html";
}
</script>

<form:form action="mobccssummary.html" name="submitForm">

</form:form>

<div style="clear:both;padding-top:5px"></div>

<c:if test="${not empty cancellationUI}">
<hr>
<div class="buttonmenubox_R" id="buttonArea" align="left">


	<table width="800" border="0" cellspacing="0" align="left">
		<tr align="left">	
		<td class="contenttextgreen">
		Order Copy from OrderID: ${cancellationUI.orderId}
		</td>							
			<td align="left">
			  
			<a href="#" class="nextbutton3" name="customerprofilepage"
						onclick="javascript:customerProfilePage();">Customer Profile</a>
						
			</td>
		</tr>
	</table>

</div>
<hr>
</c:if>
<br>

<table>

	<c:if test='${not empty messageMrtPkg }'>
		<tr align="left">
			<td class="contenttext_red">MRT checking Error, please capture
				screen and inform HELPDESK for fault reporting : ${messageMrtPkg}</td>
		</tr>
	</c:if>
	<c:if test='${not empty messageOrderPkg }'>

		<tr align="left">
			<td class="contenttext_red">Order status process Error, please
				capture screen and inform HELPDESK for fault reporting :
				${messageOrderPkg}</td>
		</tr>
	</c:if>
	<c:if test='${not empty frontEndDisplayWarningMessage }'>
		<tr align="left">
			<td class="contenttext_red">${frontEndDisplayWarningMessage}</td>
		</tr>
	</c:if>
	<c:if test='${not empty emailReqResultIGuardToCust }'>
				<tr>
				<c:choose>
						<c:when test='${emailReqResultIGuardToCust == "SUCCESS"}'>
							
							<td class="contenttextgreen"> IGuard Email has been sent successfully to customer.</td>
						</c:when>
						<c:otherwise>
							
							<td  class="contenttext_red"><b>Failure in sending IGuard email.  ${emailReqResultIGuardToCust.message}
</br>
Please resend IGuard email by using "Resent email" if applicable.</b></td>
						</c:otherwise>
				</c:choose>	
				</tr>
				</c:if>
				
				<c:if test="${emailReqResultIGuardToComp!= null}">
				<tr>
				<c:choose>
						<c:when test='${emailReqResultIGuardToComp!= null && emailReqResultIGuardToComp == "SUCCESS"}'>
							
							<td class="contenttextgreen"> IGuard Email has been sent successfully to iGuard Company..</td>
						</c:when>
						<c:otherwise>
							
							<td  class="contenttext_red"><b>Failure in sending IGuard Company email.  ${emailReqResultIGuardToComp.message}
</br>
Please resend IGuard email by using "Resent email" to IGuard Company if applicable.</b></td>
						</c:otherwise>
				</c:choose>	
					</tr>
				</c:if>	
	
	<tr align="left">
		<td class="contenttext_red"></td>
	</tr>

</table>





<div class="orderPreview"><span class="contenttextgreen">Loading...</span></div>
<div class="buttonmenubox_R" id="buttonArea" align="right">
	<table width="800" border="0" cellspacing="0" align="right">
		<tr align="right">								
			<td align="right">
			<a href="#" class="nextbutton3" name="home"
						onclick="javascript:orderFormSubmit();">Home</a>
						
			</td>
		</tr>
	</table>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>