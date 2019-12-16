<%@ include file="/WEB-INF/jsp/header.jsp" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />	



<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.signaturepad.min.js"></script>
		<script type="text/javascript" src="js/excanvas.js"></script>
		<script type="text/javascript">
			var api;
			
			$(document).ready(function(){
				var options = {drawOnly:true, penWidth:'5', lineTop:0, lineWidth:0};
				api = $('.sigPad').signaturePad(options);
				
				$("#closeButton").click(function(e) {
					window.close();
				});
				window.close();
			});
			
			function onSubmit() {
				//alert('onSubmitonSubmit');
				document.getElementById("signatureString").value=api.getSignatureString();
				//document.signoffForm.submit();
			}

			function onReset() {
				alert('onReset');
				document.signoffForm.submit();
			}

		</script>


<form:form method="POST" name="signoffForm" commandName="signoff" onsubmit="onSubmit();">

<!-- --------------------------------- -->
<!-- 
<a href="intent:#Intent;action=biz.binarysolutions.signature.CAPTURE;S.biz.binarysolutions.signature.Title=Signature%20Capture;i.biz.binarysolutions.signature.StrokeWidth=12;S.biz.binarysolutions.signature.InvoiceId=;S.biz.binarysolutions.signature.UploadURL=http%3A%2F%2F192.168.0.106%3A8080%2FYMCA_MRS_WEB%2Fpdf%2FSignOffPdfForm.action%3FinvoiceId%3D12345678;end">Android Sign Off</a>
 -->	  
	      <table border="0" cellspacing="0" cellpadding="0">
	      	<tr>
			    <td>
			    <div style="width: 800px; height: 350px;" class="sigPad">
			     
                     <div class="sig sigWrapper" style="width: 800px; height: 300px; border: 1px solid black;">
                    	<canvas id="canvasSignature2" width="800" height="300"></canvas>
                    </div><br/>
                     
                    
                    <input type="button" class="clearButton" value="clear"/>
                    <input type="submit"  value="signoff"/>
                     
                     <input type="button" value="Close" id="closeButton">
                    <br/>
                </div>
				</td>
		  	</tr>
	      	
	      </table>
 
<!-- --------------------------------- -->

 
	<form:hidden path="orderId" />    
	<form:hidden path="signatureString" />
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>