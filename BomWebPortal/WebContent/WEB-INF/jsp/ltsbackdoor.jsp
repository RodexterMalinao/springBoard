
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
<title>PCCW-HKT - LTS Backdoor</title>

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
	
	
	String orderEnquiryUrl="ltsccordersearch.html?locale=en"; 
	String orderEnquirySimpleUrl="ltssimpleordersearch.html?locale=en"; 
	String orderEnquiryRetUrl="ordersummary.html?locale=en"; 
	String ltsACQUrl = "ltsorder.html?action=create&type=I&locale=en&mode=BD";
	String ltsUpgradeRetentionUrl = "ltsorder.html?action=create&type=C&locale=en&mode=BD";
	String ltsTerminationUrl = "ltsorder.html?action=create&type=D&locale=en&mode=BD"; 
	String ltsACQDSUrl = "welcome.html";
	String alertMessageUrl = "ltsalertmessage.html?srvType=TEL&locale=en";
	String afResendUrl = "ltsresendaf.html?locale=en";
/* 	if(channelId == 1) orderEnquiryUrl = "ordersummary.html";
	else if (channelId == 2 || channelId == 3) orderEnquiryUrl = "ccltsimsorderenquiry.html";
	else if (channelId == 12 || channelId == 13) orderEnquiryUrl = "imsdsorderenquiry.html"; */
%>

</head>

<style type="text/css">
table {
	width: 80%;
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
		<td style="text-align: left; width: 80%; overflow:hidden"><span class="bottom"><img src="images/hkt_logo2.png" alt="" width="200" height="50"></span></td>
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
<tr><td style="overflow:hidden"><div id="s_line_text_2" style="font-size:large">Springboard LTS</div></td>
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

$(document).ready(function() {

});
</script>
<style>
label.error {
  display: block;
  color: red;
}
</style>



<table>
<tbody>
<tr><td style="overflow:hidden">
<div id="s_line_text_orange">Order Enquiry</div></td>
</tr>
</tbody>
</table>
<table>
<tbody id="search_table" class="table_style_sb">

  <tr>
  <td>LTS Order Inquiry</td>
  <td>

    <table >
      <tr>
        <td width="80%">Redirect to LTS Call Centre Order Search</td>
        <td>
           <input type="button" id="create" onclick="javascript:window.location.assign('<%= orderEnquiryUrl %>');" style="width: 105px;" value="Order Enquiry">     

      </tr>
      <tr>
        <td width="80%">Redirect to LTS Call Centre Order Search (Simplified)</td>
        <td>
           <input type="button" id="create" onclick="javascript:window.location.assign('<%= orderEnquirySimpleUrl %>');" style="width: 105px;" value="Order Enquiry">     

      </tr>
      <tr>
        <td width="80%">Redirect to LTS Retail Order Search</td>
        <td>
             <input type="button" id="create" onclick="javascript:window.location.assign('<%= orderEnquiryRetUrl %>');" style="width: 105px;" value="Order Enquiry">
        </td>
      </tr>
    </table>    
  </td> 
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


<table id="search_table" class="table_style_sb">
  <tbody>
  <colgroup style="width:200px"></colgroup>
  <colgroup></colgroup>
  <colgroup style="width:300px"></colgroup>

  <tr>
  <td>LTS ACQ</td>
  <td>

    <table>
      <tr>
        <td width="80%">Redirect to LTS Direct Sales New Acquisition</td>
        <td>
           <input type="button" id="create" onclick="javascript:window.location.assign('<%= ltsACQDSUrl %>');" style="width: 160px;" value="LTS ACQ DS">
        </td>
      </tr>
      
      <tr>
        <td width="80%">Redirect to LTS New Acquisition (Non direct sales)</td>
        <td>
          <input type="button" id="create" onclick="javascript:window.location.assign('<%= ltsACQUrl %>');" style="width: 160px;" value="LTS ACQ">
        </td>
      </tr>
   </table>
  </td> 
  </tr>
  
    <tr>
  <td>LTS Upgrade</td>
  <td>


    <table>
      <tr>
        <td width="80%">Redirect to LTS Upgrade / Retention               </td>
        <td>
          <input type="button" id="create" onclick="javascript:window.location.assign('<%= ltsUpgradeRetentionUrl %>');" style="width: 160px;" value="LTS Upgrade / Retention">
        </td>
      </tr>
      </table>
      
   
  </td> 
  </tr>
  
<tr>
  <td>LTS Termination</td>
  <td>

     <table>
      <tr>
        <td width="80%">Redirect to LTS Termination                        </td>
        <td>
          <input type="button" id="create" onclick="javascript:window.location.assign('<%= ltsTerminationUrl %>');" style="width: 160px;" value="LTS Termination">
        </td>
      </tr>
      </table>

   
  </td> 
  </tr>
  
  </tbody>
</table>

<table>
<tbody>
<tr><td style="overflow:hidden">
<div id="s_line_text_orange">Other Function</div></td>
</tr>
</tbody>
</table>


<tr>

  <td>

    <table id="search_table" class="table_style_sb">
      <tr>
        <td>Redirect to LTS Alert Message</td>
        <td>
           <input type="button" id="create" onclick="javascript:window.location.assign('<%= alertMessageUrl %>');" style="width: 105px;" value="Alert Message">     

      </tr>
      
      <tr>
        <td>Redirect to LTS AF Resend</td>
        <td>
             <input type="button" id="create" onclick="javascript:window.location.assign('<%= afResendUrl %>');" style="width: 105px;" value="AF Resend">
        </td>
      </tr>
    </table>    
  </td> 
  </tr>

		<div class="clear"></div>
		<div class="clear"></div>
	


</body></html>
<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>