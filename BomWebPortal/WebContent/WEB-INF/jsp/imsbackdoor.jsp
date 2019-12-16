
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="us">
<head>
	<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"rel="stylesheet" />

	<script src="js/jquery-1.4.4.js"></script>
	<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
	
	<script type="text/javascript" src="js/jquery-1.4.4.js">
</script>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="			
					org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.dto.BomSalesUserDTO,
					java.util.Locale
					"
%>
<title>PCCW-HKT - IMS Ret</title>

<%
	Locale locale = RequestContextUtils.getLocale(request);
	request.setAttribute("language", locale.toString());
	String enableOnloadJScript =  request.getParameter("enableOnloadJScript");
	BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
	
	String shopCd = "";
	String areaCd = "";
	String channelCd = "";
	int channelId = 0;
	String username = "";
	String pilotStatus = "";
	String ltsPilotStatus = "";
	String mobPilotStatus = "";
	if (bomsalesuser != null) {
	  shopCd = bomsalesuser.getShopCd();
	  areaCd = bomsalesuser.getAreaCd();
	  channelCd = bomsalesuser.getChannelCd();
	  channelId = bomsalesuser.getChannelId();
	  username = bomsalesuser.getUsername();
	  pilotStatus = bomsalesuser.getPilotStatus();
	  ltsPilotStatus = bomsalesuser.getLtsPilotStatus();
	  mobPilotStatus = bomsalesuser.getMobPilotStatus();
	}
	
	
	String orderEnquiryUrl=""; 
	if(channelId == 1) orderEnquiryUrl = "ordersummary.html";
	else if (channelId == 2 || channelId == 3 || channelId == 99) orderEnquiryUrl = "ccltsimsorderenquiry.html";
	else if (channelId == 12 || channelId == 13) orderEnquiryUrl = "imsdsorderenquiry.html";
	
	String resendAFUrl=""; 
	if(channelId == 1) resendAFUrl = "ordersendemailhist.html";
	else if (channelId == 2 || channelId == 3 || channelId == 99) resendAFUrl = "ccordersendemailhist.html";
	else if (channelId == 12 || channelId == 13) resendAFUrl = "dsordersendemailhist.html";
	
	String alertMsgUrl = "ltsalertmessage.html?srvType=IMS";
%>

</head>

<style type="text/css">
table {
	width: 75%;
}

#s_line_text_2 {
    overflow: hidden;
    text-align: left;
   	font-size: 14px;
	  font-style:italic;
  	color:#5d9de4;
  	
  	padding-bottom:5px;
  	padding-top:20px;
}

#s_line_text_2:after {
    background-color: #5d9de4;
    content: "";
    display: inline-block;
    height: 1px;
    position: relative;
    vertical-align: middle;
    width: 100%;
    overflow: hidden;
}

#s_line_text_2:after {
    left: 0.5em;
    margin-right: -50%;
}


.table_style_sb td, .table_style_sb th {
	font-size:14px;
	border:1px solid #FFFFFF;
	background-color:#F8F8F8;
	padding:3px 7px 2px 7px;
}
.table_style_sb td.itemPrice {
	background-color:#8dcdf4;
	text-align:center;
}
.table_style_sb th {
	text-align:center;
	padding-top:2px;
	padding-bottom:1px;
	background-color:#5d9de4 ;
	color:#fff;
}
.table_style_sb tr.alt td {
	color:#000;
	background-color:#e8e8e8;
}
.table_style_sb tr.alt td.itemPrice {
	background-color:#84c4f0;
	text-align:center;
}
.table_style_sb .off{
	display:none;
}



#s_line_text_red {
    overflow: hidden;
    text-align: left;
   	font-size: 14px;
	font-style:italic;
  	color:#FF0000;
  	font-weight: bold;
  	padding-bottom:10px;
}

#s_line_text_red:after {
    background-color: #FF0000;
    content: "";
    display: inline-block;
    height: 1px;
    position: relative;
    vertical-align: middle;
    width: 100%;
    overflow: hidden;
}

#s_line_text_red:after {
    left: 0.5em;
    margin-right: -50%;
}


#s_line_text_orange {
    overflow: hidden;
    text-align: left;
   	font-size: 14px;
	font-style:italic;
  	color:#EC7A00;
  	padding-bottom:5px;
  	padding-top:25px;
}

#s_line_text_orange:after {
    background-color: #EC7A00;
    content: "";
    display: inline-block;
    height: 1px;
    position: relative;
    vertical-align: middle;
    width: 100%;
    overflow: hidden;
}

#s_line_text_orange:after {
    left: 0.5em;
    margin-right: -50%;
}

.func_button
{
	display:inline;
	padding: 1px 15px 0px 15px;
	color:#f3f3f3;
	background-color:#ec7a00;

	margin-left:3px;
	margin-right:3px;
	border:none;
	font-size:1.2em;
	-moz-border-radius: 6px;
    -webkit-border-radius: 6px;
    -khtml-border-radius: 6px;
    border-radius: 6px;
	
	}
.func_button
{
	display:inline;
	padding: 1px 15px 0px 15px;
	color:#f3f3f3;
	background-color:#ec7a00;

	margin-left:3px;
	margin-right:3px;
	border:none;
	font-size:1.2em;
	-moz-border-radius: 6px;
    -webkit-border-radius: 6px;
    -khtml-border-radius: 6px;
    border-radius: 6px;
	
}
.func_button:hover {
	background-color:#eda14f;
}
	.demoHeaders {
		color: #0254EB;
	}
h3 {
	display: block;
	font-size: 14px;
	font-weight: bold;
}
	body{
		font-family: "Trebuchet MS", sans-serif;
		font-size: 13px;
		margin: 5px;
		width:98%;
    margin-left:auto;
    margin-right:auto;
	}
</style>

<script type="text/javascript">
function showLoading(){		
    $("#loading").show();
}
function hideLoading(){
    $("#loading").hide();
}

$(document).ready(function(){
	$("form").submit(function() {
		timer = setTimeout(function() {
 			showLoading();
 		}, 250);
	});
	
	var timer = setTimeout("", 0 ); //for IE
	$(document).ajaxStart(function() {
 		timer = setTimeout(function() {
 			showLoading();
 		}, 250);
		
	}).ajaxStop(function() {
		clearTimeout(timer);
		hideLoading();
	});
});
</script>


 <script>
function Quit(){
		if(confirm("Are you sure to exit?"))
			self.close();	
}


	function changeStatus(wqWpAssgnId,wqStatus,bomOcId,wqSerial){
		var page = "/qm/ShowWQ";
  		page = page + "?loginId=" + $('#loginId').val();
  		page = page + "&sbSessionId=" + $('#sessionId').val();
  		 
		
		window.open(page, 'newWin', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
		//alert(page);
	}

function scrollButtonControl() {
	if ($("body").height() > $(window).height()) {
		$("#scrollButton").click(function() {
			$("html, body").animate({scrollTop: $(document).height()-$(window).height()}, 300);
		}).show();
	} else {
		$("#scrollButton").hide();
	}
};

$(window).load(function() {
	scrollButtonControl();
});

$(document).ajaxComplete(function() {
	scrollButtonControl();
});
</script>


	
	

<div id="headerDiv">
<table border="0">
	<tbody><tr>
		<td style="text-align: left; width: 70%; overflow:hidden"><span class="bottom"><img src="images/hkt_logo2.png" alt="" width="200" height="50"></span></td>
		<td style="text-align: right; white-space: nowrap">
		  
		<%if(bomsalesuser!=null){ %>		
		    <h3 class="demoHeaders"> <%= channelCd %> / <%= areaCd %> / <%= shopCd %> / <%= username %></h3>
       <%}%>
		  
		</td>
		
  </tr>

</tbody></table>

<input type="hidden" class="l" value="en">

<table>
<tbody>
<tr><td style="overflow:hidden"><div id="s_line_text_2" style="font-size:large">Springboard IMS</div></td>
	<td style="width: 50px; overflow: visible ;vertical-align:bottom">
	<div style="display:block">
	<a href="" onclick="Quit()">
	<div class="func_button">Close</div>
	</a>
	</div>
	</td>
</tr>
</tbody>
</table>

<input id="scrollButton" type="button" value="V" style="display: none;">
</div>	


<!-- End of Header -->
			<div class="clear"></div>
			<div id="subscriber">
			<!-- ------------------ blank -------------------- -->
			</div>
			<div class="clear"></div>
			<div id="progressbar">
			<!-- ------------------ blank -------------------- -->
			</div>
			<div class="clear"></div>
		
		

<script type="text/javascript">


function submitToWindow(action) {
// 	alert(action);
			$("#backDoorFunction").val(action);				
    document.ccLtsImsOrderEnquiryForm.submit();

  return false;
}

function keyUpOnDocNum() {
	 	document.getElementById('docNum').value = document.getElementById('docNum').value.toUpperCase();
}
function redirectToRET(){
	window.location.replace("https://sbims.pccw.com/NowTVSales/ntvorderdetail.html?_al=new&backDoorAcq=Y");
}

$(document).ready(function() {

});
</script>
<style>
label.error {
  display: block;
  color: red;
}
</style>


<form:form name="ccLtsImsOrderEnquiryForm" method="POST" commandName="CcLtsImsOrderEnquiryUI">
<table>
<tbody>
<tr><td style="overflow:hidden">
<div id="s_line_text_orange">Order Enquiry</div></td>
</tr>
</tbody>
</table>
<table>
<tbody>
<tr><td style="overflow:hidden">
<input type="button" id="create" onclick="javascript:window.location.replace('<%= orderEnquiryUrl %>');" style="width: 150px;" value="Order Enquiry">     
 	  <input type="button" id="create" onclick="submitToWindow('NTV');" style="width: 85px;" value="NowTVSales">
 	  <input type="button" id="create" onclick="redirectToRET();" style="width: 195px;" value="NTV ACQ (Retail / Call Centre)">
 	  <input type="button" id="create" onclick="javascript:window.location.replace('<%= resendAFUrl %>');" style="width: 150px;" value="Resend AF">
 	  <input type="button" id="create" onclick="javascript:window.location.replace('<%= alertMsgUrl %>');" style="width: 150px;" value="IMS Alert Msg"> 
 	  <input type="button" id="create" onClick="changeStatus('','','','');"" style="width: 150px;" value="IMS QM"> 
</tr>

</tbody>
</table>


<table>
<tbody>
<tr><td style="overflow:hidden">
<div id="s_line_text_orange">Create Order</div></td>
</tr>
</tbody>
</table>

<form:hidden path="backDoorFunction"/>
<table id="search_table" class="table_style_sb">
  <tbody>
  <colgroup style="width:200px"></colgroup>
  <colgroup></colgroup>
  <colgroup style="width:100px"></colgroup>
  
  <tr>
  <td>IMS ACQ</td>
  <td>

    <table>
      <tr>
        <td>ID Doc Type</td>
        <td>
          <form:select id="docType" path="docType" >
											<form:option value="HKID" label="HKID"/>
											<form:option value="PASS" label="PASSPORT"/>
											<form:option value="BS" label="HKBR"/>
										</form:select>
        </td>
      </tr>
      <tr>
        <td>ID Doc Num</td>
        <td><form:input id="docNum" path="docNum" onkeyup="keyUpOnDocNum()"  cssStyle="text-transform:uppercase;"/></td>
      </tr>
    </table>
    
  </td>
  <td><input type="button" id="clearButton" onclick="submitToWindow('ACQ');" style="width: 81px;" value="Acquisition">
  </td>
  </tr>
  
  <tr>
  
  <td>IMS RET</td>
  <td>
    <table>
      <tr>
        <td>Service Num</td>
        <td><form:input id="serNum" path="serNum" /></td>
      </tr>
    </table>
  </td>
  <td>
  	<input type="button" id="create" onclick="submitToWindow('RET');" style="width: 100px;" value="Retention">
	<input type="button" id="create" onclick="submitToWindow('TERM');" style="width: 100px;" value="Termination">
	<input type="button" id="create" onclick="submitToWindow('NTVRET');" style="width: 100px;" value="Now Retention">
  </td>
  </tr>
  </tbody>
</table>
		<div class="clear"></div>
		<div class="clear"></div>
	
</form:form>

</body></html>
<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>