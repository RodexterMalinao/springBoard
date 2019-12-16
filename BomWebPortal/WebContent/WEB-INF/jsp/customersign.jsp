<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/header2.jsp" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />	

<style style="text/css">
.supportDocForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px; font-size: 12px }
.supportDocForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.supportDocForm .error { padding-left: 12px; display: inline-block }
.supportDocForm .row { margin: 3px 0; overflow: hidden; vertical-align: top }
.supportDocForm .label { display: inline-block; width: 25%; margin-right: 5px; text-align: right; vertical-align: middle }
.supportDocForm input { vertical-align: middle }
.supportDocForm ul { list-style: none; padding: 0; padding-left: 1em; margin: 0 }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->


<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.signaturepad.min.js"></script>
		<script type="text/javascript" src="js/excanvas.js"></script>
		<script type="text/javascript">
			var api;
			
			$(document).ready(function(){
				var options = {drawOnly:true, penWidth:'3', lineTop:0, lineWidth:0};
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


<form:form method="POST" name="customersignForm" commandName="customersign" >
<div class="supportDocForm">
	<h3>Customer Signature<br>客戶簽署</h3>
	
	<div class="row">This signature applies to:<br>此簽名適用於：</div>
	<div class="row">
		<ul>
			<li>- PCCW mobile Application Form 電訊盈科流動通訊服務申請書 / NETVIGATOR Everywhere Application 網上行Everywhere服務申請書</li>
			<c:if test="${isCos}">
			<li>- PCCW mobile Change of Service / Customer Information &amp; Refund Application 電訊盈科流動通訊更改服務 / 客戶資料及退款申請書</li>
			</c:if>
			<c:if test="${isSecretarialService}">
			<li>- Mobile Secretarial Service Supplement Form 申請或更改流動秘書服務</li>
			</c:if>
			<c:if test="${isMnpNotTransOwnership}">
			<li>- Mobile Number Portability Application Form 流動電話號碼可攜服務申請表格</li>
			</c:if>
			<c:if test="${isMobileSafetyPhone}">
			<li>- Mobile Safety Phone Service Plan Application 平安手機&reg;服務計劃申請書 </li>
			</c:if>
			<c:if test="${isNFCSim}">
			<li>- NFC Mobile Payment服務客戶資料披露同意書 / Agreement for Consent to Disclosure of Customer Information for NFC mobile Payment Service</li>
			</c:if>
			<%-- <c:if test="${isOctopusSim}">
			<li>- Octopus SIM Service Application 八達通SIM服務計劃申請書 </li>
			</c:if> --%>
			<%-- <c:if test="${hasProductInfo}">
			<li>- Product Information 產品資訊</li>
			</c:if> --%>
		</ul>
	</div>
	<c:if test="${copyToBankShowInd}">
	<div class="row">
		<label>
		<form:checkbox path="sameAsBankSign"/>
			<span style="display: inline-block; vertical-align: top">
			Replicated to Credit Card/Bank Autopay Authorization? (*Same as Bank/Credit Card A/C Signature)
			<br>複製到信用卡/銀行戶口自動轉賬授權 (*須與銀行賬戶/信用咭簽署相同)
			</span>
		</label>
	</div>
	</c:if>

	<div style="width: 800px; height: 350px; " class="sigPad" >
		<div class="sig sigWrapper" style="width: 800px; height: 300px; border: 3px solid black; background-color:LightGrey;">
			<canvas id="canvasSignature2" width="800" height="300"></canvas>
		</div>

		<div class="buttonmenubox_R">
			<a href="#" class="clearButton nextbutton3" name="clear"> Clear </a>
			<a href="#" class="nextbutton3" name="confirm" onclick="javascript:submitform();"> Confirm </a>
		</div>
	</div>
		
	
	<form:hidden path="orderId" />    
	<form:hidden path="signatureString" />
</div>
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>