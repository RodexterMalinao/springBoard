<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>

<html>
<head>
<script language="javascript" src="js/jquery-1.8.2.js"></script>
<script type="text/javascript">
function toggleDiv(divId) {
   //$("#"+divId).slideToggle("5000");
   $("#"+divId).fadeToggle("5000");
   //$("#"+divId).toggle("5000");
}
</script>
<script language="javascript" src="js/function.js"></script>
<c:if test="${sessionOnlineOrderCapture.applicantInfoForm.csPortalConfirm}">
	<script type="text/javascript">
		$(document).ready(function() {  
			if(is_andriod){
				$("#my_HKT_link").attr("href", "http://goo.gl/9nhuW");
				$("#my_HKT_mobile_text").show();
				$("#my_HKT_pc_text").hide();
			}else if(is_ios){
				$("#my_HKT_link").attr("href", "http://goo.gl/ABEBe");
				$("#my_HKT_mobile_text").show();
				$("#my_HKT_pc_text").hide();
			}else{
				$("#my_HKT_mobile_text").hide();
				$("#my_HKT_pc_text").show();
			}
		});
</script>
</c:if>
<script type="text/javascript">
	
	$(document).ready(function() {  
		$("#nextview").click(function(){
			//submitForm();
			top.location = "registration.html?srv=${srv}&lang=${lang}";
		});
		
	});
	
	function submitForm()
	{
		document.getElementById('acknowledgementForm').submit();
	}
    
</script>
</head>

<body>

<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='EYE'}">
	<!--
	Start of DoubleClick Floodlight Tag: Please do not remove
	Activity name of this tag: (New structure) Eye Search_Chi_Eye3 Registration Thank You Page Conv.
	URL of the webpage where the tag is expected to be placed: https://shop.hkt.com/lts/thankyoupage.html
	This tag must be placed between the <body> and </body> tags, as close as possible to the opening tag.
	Creation Date: 10/20/2017
	-->
	<script type="text/javascript">
	var axel = Math.random() + "";
	var a = axel * 10000000000000;
	document.write('<iframe src="https://962321.fls.doubleclick.net/activityi;src=962321;type=newst0;cat=newst00e;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;ord=' + a + '?" width="1" height="1" frameborder="0" style="display:none"></iframe>');
	</script>
	<noscript>
	<iframe src="https://962321.fls.doubleclick.net/activityi;src=962321;type=newst0;cat=newst00e;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;ord=1?" width="1" height="1" frameborder="0" style="display:none"></iframe>
	</noscript>
	<!-- End of DoubleClick Floodlight Tag: Please do not remove -->
	
	<!-- BEGIN: Marin Software Tracking Script (Confirmation Page) -->
	<script type='text/javascript'>
	var _mTrack = _mTrack || [];
	_mTrack.push(['addTrans', {
	    currency :'HKD',
	    items : [{
	        convType : 'Eye3_registration_ty',
	    }]
	}]);
	_mTrack.push(['processOrders']);
	(function() {
	    var mClientId = 'l2uf8fb490';
	    var mProto = (('https:' == document.location.protocol) ? 'https://' : 'http://');
	    var mHost = 'tracker.marinsm.com';
	    var mt = document.createElement('script'); mt.type = 'text/javascript'; mt.async = true; mt.src = mProto + mHost + '/tracker/async/' + mClientId + '.js';
	    var fscr = document.getElementsByTagName('script')[0]; fscr.parentNode.insertBefore(mt, fscr);
	})();
	</script>
	<noscript>
	<img width="1" height="1" src="https://tracker.marinsm.com/tp?act=2&cid=l2uf8fb490&script=no" />
	</noscript>
	<!-- END: Copyright Marin Software -->
</c:if>

<form:form id="acknowledgementForm" method="POST" commandName="acknowledgementForm" action="acknowledgement.html">
<!--wrapper-->
<div id="wrapper">
<!--content-->
<div id="content">
<!--flow nav-->
<div class="flow">
<ul>
<li class="flow_active"><spring:message code="acknowledgement.title" /></li>
</ul>
</div>
<!-- end of flow nav-->
<div id="frame_main">

<div id="middle_content" style="padding:15px">
	<div class="finish_right"> <!--span class="boldstyle16px">DBS credit card, entitle a $200 WellCome Coupon.</span>
	<span class="deep"><spring:message code="acknowledgement.title" /></span>-->
	<div style="width:80%">
		<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='EYE'}">
			<spring:message code="acknowledgement.content1"/>
		</c:if>
		<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='DEL'}">
			<spring:message code="acknowledgement.content1.del"/>
		</c:if>
		<spring:message code="acknowledgement.content2" />
		<a href="report.html?orderId=${sessionOnlineOrderCapture.sbOrder.orderId}&action=SIGNOFF_AF"><spring:message code="acknowledgement.content2.link" /></a>
		<spring:message code="acknowledgement.content3" />
		[${sessionOnlineOrderCapture.sbOrder.orderId}]
	</div>
	<br />
	<span class="deep"><spring:message code="acknowledgement.title2"/></span>
	<table>
    	<tr>
        <td ><spring:message code="acknowledgement.title.addr"/> </td><td>:</td>
        <td>${acknowledgementForm.installAddress}
        </td>
   		</tr>
    	<tr>
        <td ><spring:message code="acknowledgement.title.date"/>  </td><td>:</td>
        <td>${acknowledgementForm.installDate}</td>
    	</tr>
    	<tr>
        <td ><spring:message code="acknowledgement.title.time"/>  </td><td>:</td>
       	<td>${acknowledgementForm.installTime}</td>
		</tr>
	</table>
	<br />
	<c:if test="${sessionOnlineOrderCapture.applicantInfoForm.csPortalConfirm}">
		<div style="width:80%">
			<spring:message code="acknowledgement.content.csp1" />
			<br/><br/>
			<div id="my_HKT_mobile_text" style="display:none">
				<spring:message code="acknowledgement.content.csp2" />
				<a id="my_HKT_link" target="_blank" href=""><img width="30px" height="30px" src="./images/myHKT.png" /></a>
				<spring:message code="acknowledgement.content.csp3" /><br/><br/>
			</div>
			<div id="my_HKT_pc_text" style="display:none">
				<spring:message code="acknowledgement.content.csp4" />
			</div>
		</div>
	</c:if>
	<span>
		<spring:message code="acknowledgement.content4" />
	</span>
	<br/>
	<span>
		${sessionScope.regMsg}
	</span>
	</div>
</div>

</div>
<div class="clearboth"></div>
</div>
<!--end of content-->
<div id="floating_bar" style="margin-left: 0px;">
				<div class="grad"></div>
					<div id="bottom_content">
                     <table width="100%" style="bottom: -8pt;position: relative;">  
						<tbody>
							<tr align="center"> 
								<td width="60%">
								<img id="nextview" src="./images/${lang}/finish_btn.png" 
											onmouseover="this.src='./images/${lang}/finish_btn_mo.png'" 
											onmouseout="this.src='./images/${lang}/finish_btn.png'" 
											style="margin-left:0pt;cursor: pointer;"/>
								</td>
							</tr>
						</tbody>
                    </table>
					</div>  
					<img src="images/${lang}/bottom_bar.png" />
</div>
</div>
</form:form>
<!--end of wrapper-->
</body>
</html>
