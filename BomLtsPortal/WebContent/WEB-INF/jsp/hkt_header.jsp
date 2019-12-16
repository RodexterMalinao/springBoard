<%
	response.setHeader("Cache-Control", "no-store,no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("P3P","CP=CAO PSA OUR");
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page
	import="org.springframework.web.servlet.support.RequestContextUtils,java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<c:set var="srv" value="${sessionOnlineOrderCapture.serviceTypeInd}"/>
<c:set var="lang" value="${not empty sessionOnlineOrderCapture.lang? sessionOnlineOrderCapture.lang: 'en'}"/>
<c:set var="topNavInd" value="${not empty sessionOnlineOrderCapture.topNavInd? sessionOnlineOrderCapture.topNavInd: 'N'}"/>

<title>
PCCW-HKT - 
<c:if test="${srv == 'EYE'}">
eye
</c:if>
<c:if test="${srv == 'DEL'}">
Fixed-Line
</c:if>
Online Sales</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="format-detection" content="telephone=no"/>
<!-- <link rel="stylesheet" type="text/css" href="./css/form_layout.css" />
<script type="text/javascript" src="js/jquery-1.4.4.js"></script> -->
<link rel="stylesheet" type="text/css" href="./css/jquery-ui-1.9.1.custom.css" />
<script src="js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.9.0.custom.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script src="js/jquery-scrolltofixed.js"></script>
<script src="js/jquery.floatinglayer.js"></script>
<script type="text/javascript" charset="utf-8" src="./js/service_coverage.js"></script>
<link href="./js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<!--  <link rel="stylesheet" type="text/css" href="./css/onlinesales.css" />-->
<link rel="stylesheet" type="text/css" href="./css/livechat.css" /> 

<script type="text/javascript" charset="utf-8" src="js/jquery.colorbox-min.js"></script>


<link rel="stylesheet" type="text/css" href="./css/style_common.css" /> 
<c:if test="${srv=='EYE'}">
	<link rel="stylesheet" type="text/css" href="./css/style.css" /> 
	<link href="css/colorbox.css" rel="stylesheet" type="text/css" />
</c:if>
<c:if test="${srv=='DEL'}">
	<link rel="stylesheet" type="text/css" href="./css/style_del.css" /> 
	<link href="css/colorbox_del.css" rel="stylesheet" type="text/css" />
</c:if>
<c:if test="${srv=='ALL'}">
	<link rel="stylesheet" type="text/css" href="./css/style_all.css" /> 
	<link href="css/colorbox.css" rel="stylesheet" type="text/css" />
</c:if>
<c:if test="${srv==null}">
	<link rel="stylesheet" type="text/css" href="./css/style.css" /> 
	<link href="css/colorbox.css" rel="stylesheet" type="text/css" />
</c:if>

<script>
	var viewportHeight = $(window).height();
	var lang = '${pageContext.response.locale}';
	var is_safari = navigator.userAgent.indexOf("Safari") > -1;
	if ((navigator.userAgent.indexOf('Chrome') > -1) && (is_safari)) {
		is_safari = false;
	}
	var is_ie8 = $.browser.msie && $.browser.version >= 8;
	var is_andriod = navigator.userAgent.toLowerCase().indexOf("android") > -1;
	var is_ios = ( navigator.userAgent.match(/(iPad|iPhone|iPod)/g) ? true : false );
	
	$(window).resize(function() {
		var viewportHeight = $(window).height(); 
		$("#middle_content").css("min-height",viewportHeight-450+"px");
		if(is_ie8){
			//ie8 bottom bar position fix
			$("#floating_bar").attr('style', 'position: fixed;');
		}
	}); 
  

	$(window).scroll(function(event) {
	   $("#floating_bar").css("margin-left", -$(document).scrollLeft());
	});
	 	
	$(function() { 
		if("<c:out value='${lang}'/>"=="ZH") $(".logo").css('top','10px');
		$("#nextview").attr('onclick','formSubmit()');
		$("#middle_content").css("min-height",viewportHeight-450+"px");
		$("#middle_content").css("visibility","visible");
	}); 
	
      var liveChatUrl = "<c:out value='${live_chat_url}' escapeXml='false'/>";
      var srvType = "<c:out value='${live_chat_srv_type}' escapeXml='false'/>"; 

      function screenMask(){
    	  $("#waiting_icon").show();
      }

      function screenMaskRemove(){
    	  $("#waiting_icon").hide();
      }

      $(document).ready(function() {
		  //append hidden UID field to every form
    	  $('<input type="hidden">').attr({id:'osuid', name:'osuid', value: '${osuid}'}).appendTo('form'); 
		  
    	  //exclude addresslookup and amendment page
    	  var page = "<%= request.getServletPath() %>";
    	  if(!($("#addressLookupForm").length || $("#amendmentForm").length)){
			  $(document).ajaxStart(function(){screenMask();}).ajaxStop(function(){screenMaskRemove();});
			  $.ajaxSetup({
				  async: false,
	        	  beforeSend: function(jqXHR, setting){
	        		  //add uid as request data for every ajax call
	        		  if(setting.data==null){
	        			  setting.data = "osuid="+$("#osuid").val();
	        		  }else{
	            		  setting.data = setting.data+"&osuid="+$("#osuid").val();
	        		  }
	        	  }
			  });
    	  }
    	  
    	  $.ajaxSetup({
        	  timeout: 30000, //timeout after 30 sec
          	  error: function(jqXHR, textStatus, errorThrown){
          		 if(textStatus == 'timeout'){
             		//redirect to timeout message if timeout
       			 	top.location = 'message.html';
       		 	 }else{
       		 		//prompt sales lead form with reason "SYSTEM_ERROR" if other error
       		 		$.colorbox({innerWidth:800, innerHeight:510, fixed:true, iframe: true,
       	      			href: "salesleadform.html?reason=SYSTEM_ERROR",
       	      			opacity:"0.6"});
       		 	 }
          	  }
          });
    	  

			$("#winClose").click(function(){
    			$("#noticeBoard").css('display', 'none');
			});
      });
      
      function popLiveChatNotice(){
    	  $('html, body').animate({scrollTop: 0}, 250);
    	  $("#noticeBoard").show().effect( "bounce", {times:5}, 1000 );
      }
      
      function hideLiveChatNotice(){
    	  $("#noticeBoard").hide();
      }
      
</script> 

</head> 
<body>
<div id="waiting_icon" class="waiting_icon"></div>
<!-- <img id="waitingIcon" width="50" height="50"  src="./images/loading_icon.gif"/> -->
<!--wrapper-->
<div style="height:auto;width:950px;margin-right:auto;margin-left:auto;position: relative;z-index:5;background-color: #FFFFFF;">
<!--header-->
<c:if test="${empty HideLogo }">
<div id="header">
	<div id="topnav">
		<c:if test="${srv=='EYE' || srv==null}">
			<!--div class="topnav_logo2" ><img src = "<spring:message code="reg.nav.logo.eye"/>"/></div-->
			<c:if test="${topNavInd!='A' && topNavInd!='S' && topNavInd!='R'}">
			<img class="topbar_logo" src = "./images/${lang}/eye_top_bar_0.png" />
		</c:if>		
			
			<c:if test="${topNavInd=='A'}">
				<img class="topbar_logo" src = "./images/${lang}/eye_top_bar_1.png" />
				
			</c:if>
			<c:if test="${topNavInd=='S'}">
				<img class="topbar_logo" src = "./images/${lang}/eye_top_bar_2.png" />
				
			</c:if>
			<c:if test="${topNavInd=='R'}">
				<img class="topbar_logo" src = "./images/${lang}/eye_top_bar_3.png" />
				
			</c:if>
		</c:if>
		<c:if test="${srv=='DEL'}">
			<!--div class="topnav_logo2" ><img src = "<spring:message code="reg.nav.logo.del"/>"/></div-->
			
	        <c:if test="${topNavInd!='A' && topNavInd!='S' && topNavInd!='R'}">
	        <img class="topbar_logo" src = "./images/${lang}/del_top_bar_0.png" />	         	       
			</c:if>
			
			<c:if test="${topNavInd=='A'}">
				<img class="topbar_logo" src = "./images/${lang}/del_top_bar_1.png" />
				
			</c:if>
			<c:if test="${topNavInd=='S'}">
				<img class="topbar_logo" src = "./images/${lang}/del_top_bar_2.png" />
				
			</c:if>
			<c:if test="${topNavInd=='R'}">
				<img class="topbar_logo" src = "./images/${lang}/del_top_bar_3.png" />
			</c:if>
			
		</c:if>
	</div>
	
</div>
</c:if>
	<c:if test="${topNavInd != ''}">
		<div id="topborder">
		<div class="livechart">
			<a href="#" onclick="reg_open_chat(liveChatUrl,srvType)">
				<img id="live_chat_icon" src="./images/${lang}/live_chat_btn.png" style="width: 115px;position: relative; right: 20px;" />
				<!--<div class="livecharticon">
					<div class="icon_left"></div>
					<div class="icon_right">Live Chart</div>
				</div>-->
			</a>
		</div>
		<!--bounce box-->
        <div id="noticeBoard">
			<div id="winClose"></div>
			<div class="notL"></div>
			<div class="notR"><spring:message code="livechat.popup.notice" /></div>
        </div>
        <!--end of bounce box-->
		<div class="header">
		<c:if test="${topNavInd == 'S' || topNavInd == 'R'}">
			<c:if test="${srv=='EYE'}"><img id="srv_application" src="./images/${lang}/eye_service_application.png" style="padding-top:5px"/></c:if>
			<c:if test="${srv=='DEL'}"><img id="srv_application" src="./images/${lang}/fixed_line_service_application.png" style="padding-top:5px"/></c:if>
		</c:if>
		</div>
		<br/>
		</div>
	</c:if>
</div>
</body>
</html>

