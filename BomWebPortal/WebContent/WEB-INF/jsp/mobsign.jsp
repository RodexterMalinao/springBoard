<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%
Map<String, List<CodeLkupDTO>> codeLkupList = LookupTableBean.getInstance().getCodeLkupList();
List<CodeLkupDTO> dtos = codeLkupList.get("IPAD_VERSION");
String ipadVersion = "";
if (dtos != null && dtos.size() > 0) {
	ipadVersion = dtos.get(0).getCodeId();
}

String scheme = ConfigProperties.getPropertyByEnvironment("orddoc.ipad.uri.scheme");
%>
<%@ page import="
          com.bomwebportal.util.Utility
        "
%>
<%
request.setAttribute("deviceType", Utility.getDeviceType(request));
%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, req.contextPath)}" />
<c:url var="signCaptureUrl" value="${baseURL }/signcapture.html?reqId=${signCaptureDTO.reqId}" />					
<script type="text/javascript" src="js/jquery.min.js"></script>
		
		<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.min.css"
		  rel="stylesheet" />
		<script src="js/jquery-1.10.2.js"></script>
		<script src="js/jquery-ui-1.10.3.custom.min.js"></script>

		<script type="text/javascript" src="js/excanvas.js"></script>
        <script type="text/javascript" src="js/json2.min.js"></script>

        <script type="text/javascript" src="js/jquery.signaturepad.min.js"></script>
        <script type="text/javascript" src="js/jquery.qrcode.min.js"></script>

		<script type="text/javascript">
			var api;
			
			$(document).ready(function(){
				
				setTimeout(initSig, 500);
                
			});
			
            function initSig() {
            	
                var options = {drawOnly:true, penWidth:'3', lineTop:0, lineWidth:0};
                api = $('.sigPad').signaturePad(options);
                $("div#header").hide();
                $("div#footer").hide();
                $("div#container").addClass('bottomDiv');

                
                var ua = window.navigator.userAgent;
                var msie = ua.indexOf("MSIE ");

                if (msie > 0){      // If Internet Explorer
                	$('#qrCode').qrcode({
                        width: 250, height: 250,
                        render: "table",
                        text: "${signCaptureUrl}"
               	 });
                }
                else{              // If another browser           
                    $('#qrCode').qrcode("${signCaptureUrl}");
                }
       		}

			
			
			function onSubmit() {
				//alert('onSubmitonSubmit');
				document.getElementById("signatureString").value=api.getSignatureString();
				//document.signoffForm.submit();
			}

			function onReset() {
				alert('onReset');
				document.mobSignForm.submit();
			}
			function submitform()
			{
			  onSubmit();
			  document.mobSignForm.submit();
			}
			function getSignature(reqId) {
				var url = "<c:url value='/getsignature.html?reqId='/>"+reqId ;
			    $.ajax({
			        url: url,
			        type: "GET",
			        dataType: "json",
			        cache: false,
			        error:  function(XMLHttpRequest, textStatus, errorThrown) {
						alert('Error when get signature, please re-try! [CS-2]');
						alert(XMLHttpRequest.status);
			            alert(errorThrown);			            
					},
					success : function(data) {
						$.each(eval(data), function(i, item) {	
					    	api.regenerate(item.signature);
						});					
					}
			    
			      });
			    
				
			}
			function generateQRCode() {

				$("#qrCodeDialog").dialog({ 
					modal: true
					, width: 350
					, resizable: false
					, draggable: false
					
				});
				
			}
			
			
			function showQRCodeUsage(){
				
				var url = "<c:url value='qrcodeusage.jsp'/>";
				
				$("<div/>").append($("<iframe width=\"100%\" height=\"100%\"/>").attr("src", url)).dialog({
					
					modal: true
					, width: 600
					, height: 400
					, resizable: false
					, draggable: false
					, title: "QR Code Instruction"
				});
			}

		</script>
<style type="text/css">

.bottomDiv {
   /*  margin: auto;
    position: absolute;
    bottom: 0; */
    /*margin: auto;*/
    position: absolute;
    bottom: 0;
    left: 0;
    margin: 10px;
}

</style>
<form:form method="POST" name="mobSignForm" commandName="mobsign" >
	<div>
	<table border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
		<tr class="table_title">
			<td>${titleString}</td>
		</tr>
		<tr class="contenttext">
			<td>${noteString}</td>
		</tr>
      	<tr>
		    <td>
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
			<c:if test="${signMode==4&&iGuardUAD}">
				<div class="row">
				<label>
				<form:checkbox path="iGuard1"/>
				<span style="display: inline-block; vertical-align: top;font-size:12px">
					本人確認以上資料正確無誤。
				</span>
				</label>
				</div>
				
				<div class="row">
				<label>
				<form:checkbox path="iGuard2"/>
				<span style="display: inline-block; vertical-align: top;font-size:12px">
				本人確認已經清楚閱讀此嘉保手機及平板電腦維修計劃的條款及細則(「該條款及細則」)及本表格所附的個人資料收集(「該聲明」)，<br>並同意遵守該條款及細則及聲明並受其約束。<br>本人亦同意本人的個人資料會根據該條款及細則和該聲明所使用。
				</span>
				</label>
				</div>
				
				<div class="row">
				<label>
				<form:checkbox path="iGuard3"/>
				<span style="display: inline-block; vertical-align: top;font-size:12px">
					本人反對按照上文所述使用本人的資料作直接促銷用途。
				</span>
				</label>
				</div>
			</c:if>
			
			<!-- START TRAVEL INSURANCE -->
			<c:if test="${signMode==6 && isTravelInsurance}">
				<div class="row" style="display: inline-block; vertical-align: top; font-size: 12px; font-family:Verdana, Arial, Helvetica, sans-serif">
					<table>
						<tr><td><form:checkbox path="travel1"/></td><td>1. 本人確認以上資料正確無誤。</td></tr>
						<tr><td></td><td>2. 本人同意HKT Care好友共享${travelInsuranceDays}日旅遊保險套票的費用會由CSL Mobile Limited作為HKT Financial Services (IA) Limited之收款代理收取，並安排繳付相關保費予富衛保險有限公司。</td></tr>
						<tr><td></td><td>3. 本人確認已經清楚閱讀此HKT Care 好友共享${travelInsuranceDays}日旅遊保險套票的條款及細則(「該條款及細則」)及本表格所附的個人資料收集聲明(「該聲明」)，並同意遵守該條款及細則及該聲明並受其約束。本人亦同意本人的個人資料會根據該條款及細則及該聲明所使用。</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td></td><td>1. I confirm that the details provided above are correct.</td></tr>
						<tr><td></td><td>2.	I agree the fee for HKT Care EasyShare ${travelInsuranceDays}-day Travel Insurance Package to be charged by CSL Mobile Limited as the billing agent of HKT Financial Services (IA) Limited to arrange for the payment of the premium to FWD General Insurance Company Limited. </td></tr>
						<tr><td></td><td>3. I have read, and agree to, the terms and conditions of this HKT Care EasyShare ${travelInsuranceDays}-day Travel Insurance Package (“Terms and Conditions”) and the Personal Information Collection Statement (“PICS”) attached with this form, and agree to comply and be bound by such. I also consent to the use of my personal information in accordance with the Terms and Conditions and the PICS. </td></tr>
					</table>
				</div>
			</c:if>
			<!-- END TRAVEL INSURANCE -->
			
			<!-- START HELPER INSURANCE -->
			<c:if test="${signMode==7 && isHelperCareInsurance}">
				<div class="row" style="display: inline-block; vertical-align: top; font-size: 12px; font-family:Verdana, Arial, Helvetica, sans-serif">
					<table>
						<tr><td><form:checkbox path="helperCare1"/></td><td>1. 本人確認以上資料正確無誤。</td></tr>
						<tr><td></td><td>2. 本人同意HKT Care 2年家傭保險優惠券的費用會由CSL Mobile Limited作為HKT Financial Services (IA) Limited之收款代理收取，並安排繳付相關保費予富衛保險有限公司。</td></tr>
						<tr><td></td><td>3. 本人確認已經清楚閱讀此HKT Care 2年家傭保險優惠券的條款及細則(「該條款及細則」)及本表格所附的個人資料收集聲明(「該聲明」)，並同意遵守該條款及細則及該聲明並受其約束。本人亦同意本人的個人資料會根據該條款及細則及該聲明所使用。</td></tr>
						<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						<tr><td></td><td>1. I confirm that the details provided above are correct.</td></tr>
						<tr><td></td><td>2. I agree the fee for HKT Care 2-year Helper Insurance Coupon to be charged by CSL Mobile Limited as the billing agent of HKT Financial Services (IA) Limited to arrange for the payment of the premium to FWD General Insurance Company Limited. </td></tr>
						<tr><td></td><td>3. I have read, and agree to, the terms and conditions of this HKT Care 2-year Helper Insurance Coupon (“Terms and Conditions”) and the Personal Information Collection Statement (“PICS”) attached with this form, and agree to comply and be bound by such. I also consent to the use of my personal information in accordance with the Terms and Conditions and the PICS. </td></tr>
					</table>
				</div>
			</c:if>
			<!-- END HELPER INSURANCE -->
			
			<!-- START PROJECT EAGLE INSURANCE -->
			<c:if test="${signMode==8 && isProjectEagleInsurance}">
				<div class="row" style="display: inline-block; vertical-align: top; font-size: 11px; font-family:Verdana, Arial, Helvetica, sans-serif">
					<table>
						<tr><td><form:checkbox path="projectEagle"/></td><td>I, the Customer named under this Application Form, apply to you, Reconnects Services (Hong Kong) Limited (Reconnects) (the Program provider), for subscription to the Program under this Application Form, as an Optional Service to the csl / 1O1O Service I have subscribed to. </td></tr>
						<tr><td></td><td>I understand the Program is provided by Reconnects upon the terms and conditions set out in the csl / 1O1O Mobile Service Application and this Application Form, and CSL Mobile Limited is the billing agent for Reconnects for the Program.  </td></tr>
						<tr><td></td><td>I authorize CSL Mobile Limited as my agent to complete this Application Form on my instructions, to act in accordance to my further instructions in relation to the Program and to pass this Application Form to Reconnects for processing for and on my behalf. </td></tr>
						<tr><td></td><td>I agree to be bound by all the terms and conditions of the Program, and to pay all fees and charges for the Program. I have attained the age of 18 and all information provided by me is up-to-date, complete, true and correct. </td></tr>
						<tr><td></td><td>本人（亦即本申請表所列的顧客）向您（亦即 Reconnects Services (Hong Kong) Limited）（Reconnects）（計劃供應商）申請訂閱本申請表所載的計劃，作為我已訂閱的 csl / 1O1O 服務的自選服務。</td></tr>
						<tr><td></td><td>本人明白計劃由 Reconnects 按照 csl / 1O1O 流動通訊服務申請書及本申請表所列的條款及條件提供，而香港移動通訊有限公司（CSL Mobile Limited）就著計劃擔任 Reconnects 的收款代理。</td></tr>
						<tr><td></td><td>本人授權香港移動通訊有限公司作為本人的代理，以依照本人的指示填妥本申請表，按照本人就著計劃提供的進一步指示而行事，並將本申請表交予 Reconnects 以為及代表本人處理。</td></tr>
						<tr><td></td><td>本人同意受計劃的所有條款及條件約束，並支付計劃的一切收費及費用。本人已年滿 18 歲，而且本人提供的所有資料均屬完整、真實而正確的最新資料。</td></tr>	
					</table>
				</div>
			</c:if>
			<!-- END PROJECT EAGLE INSURANCE -->
			
			<div class="row">
				<form:errors path="signatureString" cssClass="error"/>
			</div>
		    <div style="width: 800px; height: 350px;" class="sigPad">
                    <div class="sig sigWrapper" style="width: 800px; height: 300px; border: 3px solid black; background-color:LightGrey;">
                   	<canvas id="canvasSignature2" width="800" height="300"></canvas>
                   </div>
                    
                   <br> 
				<a href="#" class="clearButton nextbutton3" name="clear">Clear </a> 
				<a href="#" class="nextbutton3" name="confirm"	onclick="javascript:submitform();"> Confirm </a>        
			</div>			                 
			</td>
	  	</tr>
	  	<c:if test = "${(deviceType ne 'ipad') }">
	  	<tr>
	  	<td>
		  	<div align="right">
	            <a href="#" class="nextbutton3" name="refresh" onclick="javascript:getSignature('${signCaptureDTO.reqId}');">Refresh </a> 
				<a href="#" class="nextbutton3" name="refresh" onclick="javascript:showQRCodeUsage();">QR Code Instruction</a>
				<a href="#" class="nextbutton3" name="refresh" onclick="javascript:generateQRCode();">Generate QR Code</a>  
	                   
	        </div> 
	  	</td>
	  	</tr>
	  	</c:if>
    </table>
    </div>
    <div id="qrCodeDialog" class="dialog" title="QR Code" style="display:none;font-size: smaller">
		 <div id="qrCode" align="center"  style="margin: 10px">        	
		 </div>
	</div>

  
	<form:hidden path="orderId" />    
	<form:hidden path="signatureString" />
	
</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>