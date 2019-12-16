<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>
<html>
<head>
<script type="text/javascript">
function toggleDiv(divId) {
   $("#"+divId).fadeToggle("5000");
}
</script>
<script type="text/javascript">
	//var live_chat_link = "https://livechat4.pccw.com/chat/index.jsp?c_t=NTK&c_id=Guest&m_n=VIP_COVER_CHK_PCD&l=en&n_n=Guest";
// 	function formSubmit(){
// 		//document.Finishform.submit();
// 		window.location="http://www.netvigator.com";
		
// 	}
	function openthat(){
		//document.Finishform.submit();
		window.open('./Temp/RTKOP100214_EN.pdf',"_blank"," location=no, scrollbars=no, menubar=no, resizable=0, status=no,toolbar=no");
	}
	
	
	var viewportHeight = $(window).height();

	
	$(window).resize(function() {
		var viewportHeight = $(window).height();
		$("#middle_content").css("min-height",viewportHeight-450+"px");
	});

	
	$(document).ready(function()
		{
		
			var queryString = '<%= request.getQueryString()!= null?request.getQueryString():"" %>';
			var prepayInd = ${prepayInd};
		
			if(queryString != ''){
				queryString = decode(queryString);
				var responseCode = requestGet(queryString, "EPayLinkRespCode");
				var cardnum = requestGet(queryString, "TOK");
				var expiryDay = requestGet(queryString, "EX");
				if ( expiryDay != null )
				{
					expiryDay = expiryDay.substring(2) + expiryDay.substring(0 , 2);
				}
				if(!prepayInd){
					responseCode = requestGet(queryString, "ResponseCode");
					cardnum = requestGet(queryString, "V5");
					expiryDay = requestGet(queryString, "V6");
					var referenceNum = requestGet(queryString, "ReferenceNo");
					var amt = 0;
					var ePayLinkTxId = requestGet(queryString, "EPayLinkTxID");
					var merchantId = requestGet(queryString, "MerchantID");
					var locale = '';
					var currCode = '';
					var opCode = '';
					var authCode = '';
					var acqOrderId = '';
					formSubmit(cardnum, expiryDay, responseCode, queryString, merchantId, referenceNum, amt, locale, currCode,
							opCode, ePayLinkTxId, authCode, acqOrderId);
//					formSubmitCC(cardnum, expiryDay, responseCode, queryString);
				}else{
					var referenceNum = requestGet(queryString, "orderNumber");
					var amt = requestGet(queryString, "Amount");
					var ePayLinkTxId = requestGet(queryString, "EPayLinkTxID");
					var merchantId = '';
					var locale = '';
					var currCode = '';
					var opCode = '';
					var authCode = '';
					var acqOrderId = '';
					formSubmit(cardnum, expiryDay, responseCode, queryString, merchantId, referenceNum, amt, locale, currCode,
							opCode, ePayLinkTxId, authCode, acqOrderId);
				}
				
			}else{
				screenMask();
				
				//Javascript:location.replace('https://securepayuat.pccw.com/ceks/cusf/cappay.jsp?V1=SBOPCUAT&V2=7f430903858b72aa371dd0e17395e92a&V3=7e730a0674d92c554864dddb2ac5b9209dd36e5391bdaaea2a6324e707446418&V7=39800&V8=OSBOP00080200001&V9=Service fee')
				//var url = 'https://securepayuat.pccw.com/ceks/cusf/cappay.jsp?V1=SBOPCUAT&V2=7f430903858b72aa371dd0e17395e92a&V3=7e730a0674d92c554864dddb2ac5b9209dd36e5391bdaaea2a6324e707446418&V7=39800&V8=OSBOP00080200001&V9=Service fee';
				var url = '${ceksurl}';
				
//				if(is_safari){
					//top.location.replace(url);
					top.location.replace(url+"&AX5="+"${srv}");
//				}else{
//					var x=document.getElementById("ceksFrame");
//					var y=(x.contentWindow || x.contentDocument);
//					//if (y.parent)	y=y.parent;
//					
//					y.location.replace(url);
//					
//					$("#ceksFrame").load(function (){
//						screenMaskRemove();
//					});
//				}
			}
		}
	);
	
	  

	$(window).scroll(function(event) {
		   $("#floating_bar").css("margin-left", -$(document).scrollLeft());
		});
		
		$(function() { 
			$("#nextview").attr('onclick','formSubmit()');
			$("#middle_content").css("min-height",viewportHeight-450+"px");
			$("#middle_content").css("visibility","visible");
		});
		
		function formSubmitCC(ccnum, expiryDay, responseCode, queryString){
			screenMask();
			if ( ccnum != null && ccnum != ""){
				$("#ccNum").val(ccnum);
			}
			if ( expiryDay != null && expiryDay != ""){
				$("#ccExpDate").val(expiryDay);
			}
			if ( responseCode != null && responseCode != ""){
				$("#responseCode").val(responseCode);
			}
			if ( queryString != null && queryString != ""){
				$("#queryString").val(queryString);
			}
			$("#paymentInd").val("CC");
			
			document.creditPaymentForm.submit();
		}
		
		function formSubmit(ccnum, expiryDay, responseCode, queryString, merchantId, referenceNum, amt, locale, currCode,
				opCode, ePayLinkTxId, authCode, acqOrderId){
			screenMask();
			if ( ccnum != null && ccnum != ""){
				$("#ccNum").val(ccnum);
			}
			if ( expiryDay != null && expiryDay != ""){
				$("#ccExpDate").val(expiryDay);
			}
			if ( responseCode != null && responseCode != ""){
				$("#responseCode").val(responseCode);
			}
			if ( queryString != null && queryString != ""){
				$("#queryString").val(queryString);
			}
			if ( merchantId != null && merchantId != ""){
				$("#merchantId").val(merchantId);
			}
			if ( referenceNum != null && referenceNum != ""){
				$("#referenceNum").val(referenceNum);
			}
			if ( amt != null && amt != ""){
				$("#amt").val(amt);
			}
			if ( locale != null && locale != ""){
				$("#locale").val(locale);
			}
			if ( currCode != null && currCode != ""){
				$("#currCode").val(currCode);
			}
			if ( opCode != null && opCode != ""){
				$("#opCode").val(opCode);
			}
			if ( ePayLinkTxId != null && ePayLinkTxId != ""){
				$("#ePayLinkTxId").val(ePayLinkTxId);
			}
			if ( authCode != null && authCode != ""){
				$("#authCode").val(authCode);
			}
			if ( acqOrderId != null && acqOrderId != ""){
				$("#acqOrderId").val(acqOrderId);
			}
			$("#paymentInd").val("PC");
			document.creditPaymentForm.submit();
		}
	
	function formSubmitBack()
	{
		$("#failPayment").val("Y");
		document.creditPaymentForm.submit();
	}
	
	var co = 0;
	function red()
	{
		co ++;
		//alert(temp);
		
// 		var x=document.getElementById("ifchild");
// 		var y=(x.contentWindow || x.contentDocument);
// 		if (y.document)	y=y.document;
		
// 		if (y.title == 'title the server sends for 404') {
// 			alert("hi~!");
// 		}
	
	
		/*if ( co == 3 && '${CeksLoc}'!= null && '${CeksLoc}'!= "" )
		//if ( temp.substr(0,44) == "https://sbims.pccw.com/OnineSales/ceksCC.jsp" )
		{
//			document.getElementById("ifchild").src = "http://localhost:8080/OnlineSales/ceksCC.jsp?V3=d6bd92bab7b3baa521053379a13f215a&V5=411111AAAABm1111";
//			document.getElementById("ifchild").src = "http://sbreg.0060everywhere.com/OnlineSales/ceksCC.jsp?V3=d6bd92bab7b3baa521053379a13f215a&V5=411111AAAABm1111";
			document.getElementById("ifchild").src = '${CeksLoc}';
		}*/
		//alert(co);
	}
	
	function decode(str) {
	     return unescape(str.replace(/\+/g, " "));
	}
	
	function requestGet(rStr, key)
	{
		var requestStr = "";
		requestStr = rStr + "";
		var searching = true;
		var targetStr = "";
		while ( searching )
		{
			var startPoint = requestStr.search(key);
			if ( startPoint == -1 )
			{
				return null;
			}
			 
			if ( 	startPoint + key.length < requestStr.length 
					&& requestStr.charAt( startPoint + key.length ) == '=' 
					)
			{
				var i = startPoint + key.length + 1;
				while ( i < requestStr.length &&
						requestStr.charAt(i) != '&' )
				{
					targetStr += requestStr.charAt(i);
					i++;
				}
				return targetStr;
				searching = false;
			}
			else if ( startPoint + key.length < requestStr.length )
			{
				requestStr = requestStr.substring( 0, startPoint) + requestStr.substring(startPoint + key.length + 1 );
				searching = true;
			}
			else
			{
				return null;
			}
		}
	}
</script>
</head>

<body>
<!--wrapper-->
<div id="wrapper">

<!--content-->
<div id="content">
<div class="flow">
<!--<a href="javascript:toggleDiv('cartarea');"><span class="cart_icon"></span></a>-->
<ul>
<li class="flow_active">
<c:if test="${empty nopay|| !nopay}"> 
	<spring:message code="payment.title"/>
</c:if>
<c:if test="${nopay}"> 
	<spring:message code="payment.title2"/>
</c:if>
</li>
</ul>
</div>
<!--vas main-->
<div id="vas_main">

<!--vas-->
<div id="middle_content" style="visibility: visible;padding:15px;">
	<form:form name="creditPaymentForm" id="creditPaymentForm" method="POST" commandName="creditPaymentForm" action="creditpayment.html">
		<div id="Content" style="padding-left:15px; width:700px;">
	<form:hidden path="ccNum"/>
	<form:hidden path="ccExpDate"/>
	<form:hidden path="responseCode"/>
	<form:hidden path="queryString"/>
	<form:hidden path="failPayment" />
	
	<form:hidden path="merchantId"/>
	<form:hidden path="referenceNum"/>
	<form:hidden path="amt"/>
	<form:hidden path="locale" />
	<form:hidden path="currCode"/>
	<form:hidden path="opCode"/>
	<form:hidden path="ePayLinkTxId"/>
	<form:hidden path="authCode"/>
	<form:hidden path="acqOrderId" />
	
	<form:hidden path="paymentInd" />
	
			<iframe id="ceksFrame" onload="red()" src="" frameBorder=0 
				style="height:700px;
					border:0px none white; 
					margin-left: 20px;
    				margin-top: 10px;
    				width: 870px;" ></iframe>
		</div>
	</form:form>
</div>
<!--end of vas-->


</div>
<!--end of vas main-->
<div class="clearboth"></div>
</div>
<!--end of content-->
<div id="floating_bar" style="margin-left: 0px;">
				<div class="grad"></div>
					<div id="bottom_content">
                     <table width="100%" style="bottom: -8pt;position: relative;">  
						<tbody>
							<tr> 
								<td width="60%"></td>
                                <!--<td width="20%">&nbsp;</td>
								<td width="3%" align="center"><img src="./images/OnlineRegistration/grey_divider_bottom.png" /></td>
								<td width="20%" align="left"> 								
								  <a id="headermain" class="left" href="#" onclick="reg_open_chat(liveChatUrl)">
									<img id="live_chat_icon" src="./images/live_chat_btn_en.png" style="width: 100px;" />
								</a>
								</td>-->
							</tr>
						</tbody>
                    </table>
					</div>  
					<img src="images/${lang}/bottom_bar.png" />
</div>
</div>
<!--end of wrapper-->
</body>
</html>
