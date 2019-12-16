<%@ include file="/WEB-INF/jsp/header.jsp" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />	



<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.signaturepad.min.js"></script>
		<script type="text/javascript" src="js/excanvas.js"></script>
		<script type="text/javascript">
			var api;
			
			$(document).ready(function(){
				var options = {drawOnly:true, penWidth:'2', lineTop:0, lineWidth:0};
				api = $('.sigPad').signaturePad(options);
			});
			
			function onSubmit() {
				document.getElementById("signatureString").value=api.getSignatureString();
			}

			function onReset() {
				alert('onReset');
				document.signoffForm.submit();
			}
			
			function submitform()
			{
			  onSubmit();
			  document.customersignForm.submit();
			}

		</script>


<form:form method="POST" name="customersignForm" commandName="imscustomersignmoovtdo" >

	<table border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">

		<tr class="table_title">
			<td >
<%-- 			${titleString} --%>
			<spring:message code="imsSignMoov.title"/>
			</td>
		</tr>
		<tr class="contenttext">
			<td >
<%-- 				${noteString} --%>
			<spring:message code="imsSignMoov.note"/>
			</td>
		</tr>
		
<%-- 		<c:if test='${copyToCreditCardShowInd == "Y"}'> --%>
<!-- 		<tr class="contenttext"> -->
<!-- 			<td > -->
<%-- 			<form:checkbox path="sameAsCreditCardSign" /> --%>
<%-- 				${copyToCreditCardSignString} --%>
<!-- 			</td> -->
<!-- 		</tr> -->
<%-- 		</c:if> --%>
		
		<tr>
			<td>
				<div style="width: 800px; height: 350px;" class="sigPad">
					<div class="sig sigWrapper"
						style="width: 800px; height: 300px; border: 3px solid black; background-color:LightGrey;">
						<canvas id="canvasSignature2" width="800" height="300"></canvas>
					</div>
					<br>
					<a href="#" class="clearButton nextbutton3" name="clear">
						Clear </a> <a href="#" class="nextbutton3" name="confirm"
						onclick="javascript:submitform();"> Confirm </a>
				</div>
				</td>
				
		</tr>
		
	</table>

 
	<form:hidden path="orderId" />    
	<form:hidden path="signatureString" />
</form:form>
  

<!----------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>