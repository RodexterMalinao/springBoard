<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>
<html>
<head>

<script type="text/javascript" charset="utf-8" src="js/jquery.colorbox-min.js"></script>

<script type="text/javascript">
var srvType = "${srv}";

function toggleDiv(divId) {
   //$("#"+divId).slideToggle("5000");
   $("#"+divId).fadeToggle("5000");
   //$("#"+divId).toggle("5000");
}

function checkFloor(){
	if($("#floor").val() == ""){
		$("#warning_floor_msg").html("<spring:message code="addr.floor.warn" />");
		return 0;
	}else{
		$("#warning_floor_msg").html("");
		//callajaxaddressrollout();
		return 1;
	}
}

function formSubmit() {
	/*validation*/
	if(checkFloor()){
		document.addressRolloutForm.submit();
	}
	/*submit*/
}

function callajaxaddressrollout(){
	if($("#floor").val() != ""){
		$.ajax({
			url : "ajaxaddressrollout.html",
			type : 'POST',
			data : "floor=" + $("#floor").val() + "&flat=" + $("#flat").val(),
			dataType : 'json',
			//timeout : 5000,
			success : function(data){
				
				return 1;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				return textStatus;	
			}
			/*complete : function() {
				 $.unblockUI(); 
			},
			beforeSend : function() {
				 $.blockUI({ message: null }); 
			}*/
		});
	}
}

$(document).ready(function() {
	$(function() {

		$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
		
		$("#nextview").attr('onclick', 'formSubmit()');
		$("#middle_content").css("min-height",
				viewportHeight - 450 + "px");
		$("#middle_content").css("visibility", "visible");
	});
	
	if("${isShortage}"){
		var service = "";
		if(srvType == 'EYE' || srvType == 'DEL'){
			service = "<spring:message code='map.${fn:toLowerCase(srv)}.srvtype1'/>";
		}
		showSalesLeadForm("${marker_idx}", "PCD_SHORTAGE", service);
	}
	
	$("#floor").change(function(){
		$("#floor").val(trimString($("#floor").val()));
		checkFloor();
	});
	
	$("#flat").change(function(){
		$("#flat").val(trimString($("#flat").val()));
		//callajaxaddressrollout();
	});
	
});

function changetxt(){
	//alert(document.getElementById("captchaimg"));
	document.getElementById("captchaimg").src = "captcha.html?t="+new Date().getTime();
	$(".captchaerror").hide();
	document.getElementById("captchaInput").value = "";
	//$('#captchaimg').attr('src','captcha.html');
}

function trimString(x) {
	while (x.substring(0, 1) == " ") {
		templength = x.length;
		x = x.substring(1, templength);
	}

	while (x.substring(x.length - 1, x.length) == " ") {
		x = x.substring(0, x.length - 1);
	}

	x = x.replace(/[\W\s]/g, "");
	return x;
}

function showSalesLeadForm(marker_idx, reason, service){
	$.colorbox({innerWidth:800, innerHeight:510, iframe:true, fixed:true, 
		href:"salesleadform.html?marker_idx="+marker_idx+"&reason="+reason+"&service="+service+"&flat="+$("#flat").val()+"&floor="+$("#floor").val(),
		opacity:"0.6"});
}
</script>
</head>

<body>

<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='EYE' && sessionOnlineOrderCapture.lang=='zh_HK'}">
	<!--
	Start of DoubleClick Floodlight Tag: Please do not remove
	Activity name of this tag: Eye3 Registration Confirm Address Page
	URL of the webpage where the tag is expected to be placed: https://shop.hkt.com/lts/addressrollout.html
	This tag must be placed between the <body> and </body> tags, as close as possible to the opening tag.
	Creation Date: 11/17/2015
	-->
		<script type="text/javascript">
		var axel = Math.random() + "";
		var a = axel * 10000000000000;
		document.write('<iframe src="https://962321.fls.doubleclick.net/activityi;src=962321;type=eyebr0;cat=eye3r00;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;ord=' + a + '?" width="1" height="1" frameborder="0" style="display:none"></iframe>');
		</script>
		<noscript>
		<iframe src="https://962321.fls.doubleclick.net/activityi;src=962321;type=eyebr0;cat=eye3r00;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;ord=1?" width="1" height="1" frameborder="0" style="display:none"></iframe>
		</noscript>
	<!-- End of DoubleClick Floodlight Tag: Please do not remove -->

	<!-- BEGIN: Marin Software Tracking Script (Confirmation Page) -->
		<script type='text/javascript'>
		var _mTrack = _mTrack || [];
		_mTrack.push(['addTrans', {
		    currency :'HKD',
		    items : [{
		        convType : 'Eye3_confirmaddress_page'
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

<!--wrapper-->
<div id="wrapper">
<!--content-->
<div id="content">
<!--flow nav-->
<div class="flow">
<script>
	$( ".cart_icon" ).click(function() {
	$( this ).toggleClass("cart_icon_open").fadeIn(10000);
	});
</script>
<ul>
<li class="flow_active"><spring:message code="addr.title" /></li>

</ul>
</div>
<!-- end of flow nav-->
<div id="frame_main">

<form:form id="addressRolloutForm" name="addressRolloutForm" method="POST" commandName="addressRolloutForm" action="addressrollout.html">
<div id="middle_content" style="visibility: visible;padding:15px;">
					<!--div id="form_bg"></div>
						<div id="form_">
							<iframe id="service_reg_form" src="" frameborder="0"></iframe>
					</div-->

				<div id="middle_content" style="visibility: visible; min-height: 226px;"> 
				 
                    <table border="0" bgcolor="#FFFFFF">
	                    <tbody>
	                    	<tr>
		                        <th width=200 align="left"><spring:message code="addr.flat" /></th>
		                        <td width=160>
			                        <form:input id="flat" path="flat" maxlength="5" />
		                        </td>
		                        <td><span style="display: inline-block;"><font class="address_remarks"><spring:message code="addr.flat.alert"/></font></span></td>
		                    </tr>
	                    	<tr>
		                        <th width=200 align="left"><spring:message code="addr.floor" /></th>
		                        <td width=160>
		                        	<form:input id="floor" path="floor" maxlength="3"/>
			                        
		                        </td>
		                        <td><span style="display: inline-block;"><font class="address_remarks"><spring:message code="addr.floor.alert"/></font></span></td>
		                    </tr>
		                    <tr>
		                        <td height="5" colspan="3"><span id="warning_floor_msg" style="color: #FF0000"></span></td>
		                    </tr>
	                    	<tr>
		                        <th width=200 align="left"><spring:message code="addr.inst.addr" /></th>
		                        <td colspan=2 width=160>${addressRolloutForm.address}</td>
		                    </tr>
	                    </tbody>
                    </table>
                    
                    <br />
                    <div style="margin-left:0pt;font:8pt;"> 
						<spring:message code="addr.captcha1"/><br />
						<img id="captchaimg" src="captcha.html" style="width: 60pt;top: 11pt;position: relative;"/>&nbsp;&nbsp;&nbsp;
						<input id="captchaInput" name="captchaInput" style="width:50pt;" onkeyup="javascript:$('.captchaerror').hide();" onblur="javascript:$('.captchaerror').hide();" type="text" value="" maxlength="4" autocomplete="off" />&nbsp;&nbsp;&nbsp;<spring:message code="addr.captcha2"/>
						<form:errors cssClass="captchaerror" cssStyle="color:red;margin-left:30pt;" path="captchaInput"/>
                    </div>
                    <br />
	 				<div class="address_disclaimer"> 	
		 				<a class="strong_"># </a><spring:message code="addr.alert1"/><br /><br />
		 				<!--  The above data is collected by HKT as part of the application for service process only.  For access to, or correction of the data, please contact our Privacy Compliance Officer at
		 				<a href="#" style="text-decoration: underline;color:  #00478E;font-weight:bold;">privacy@pccw.com</a>
		 				.-->
	 				</div>
	 				<br /><br /> 
	 				<div id="floorWarning" style="color:red;margin-left:35px;display:none;">Please enter the floor field.</div>
	 				<div id="invalidfloor" style="color:red;margin-left:35px;display:none;">Invalid Floor!</div>
	 				<div id="invalidflat" style="color:red;margin-left:35px;display:none;">Invalid Flat!</div>
                    
				</div>
		</div>
</form:form>

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
							<tr align="center"> 
								<td width="60%">
								<img id="nextview" src="./images/${lang}/confirm_btn.png" 
									onmouseover="this.src='./images/${lang}/confirm_btn_mo.png'" 
									onmouseout="this.src='./images/${lang}/confirm_btn.png'" 
									style="margin-left:0pt;cursor: pointer;"/>
								</td>
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
