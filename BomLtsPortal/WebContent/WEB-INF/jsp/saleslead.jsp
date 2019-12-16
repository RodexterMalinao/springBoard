<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
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
<title>Online Sales</title>
<meta name="format-detection" content="telephone=no" />
<script src="js/jquery-1.8.2.js"></script> 
<script type="text/javascript" charset="utf-8" src="./js/service_coverage.js"></script>
<link rel="stylesheet" type="text/css" href="./css/salesleadform.css" /> 
<script>
function formSubmit(){
	var errorCount = 0;
	$("span[id$='err']").hide();
	errorCount += validate("#title_err", $("input[name=title]").is(":checked"));
	errorCount += validate("#floor_err", $("#floor").val() != "");
	errorCount += validate("#name_err", $("#name").val() != "");
	errorCount += validate("#cont_phone_no_err", $("#cont_phone_no").val() != "");
	errorCount += validate("#email_addr_err", $("#email_addr").val() != "");

	if(errorCount == 0){
		document.salesleadform.submit();
	}
}

function validate(errorfield, isvalid){
	if(isvalid){
		return 0;
	}else{
		$(errorfield).show();
		return 1;
	}
}

var liveChatUrl = "<c:out value='${live_chat_url}' escapeXml='false'/>";
var srvType = "<c:out value='${live_chat_srv_type}' escapeXml='false'/>"; 

</script>
</head> 
<!-- <body style="height:=510px;"> -->
<body>
<c:set var="srv" value="${sessionOnlineOrderCapture.serviceTypeInd}"/>
<c:set var="lang" value="${not empty sessionOnlineOrderCapture.lang? sessionOnlineOrderCapture.lang: 'en'}"/>

<!--Promotion and contact message-->
<c:set var="housingType" value="${salesleadform.housingType}"/>
<!--//Promotion and contact message end-->

<div id="container">
		
			<div class="formheader">
           	  <div class="right_header">
              <!-- <a href="#" onclick="parent.hideform();"><span class="top_close_bn">&nbsp;</span></a> -->
            <div class="eye_text">
            	<!--
            	<c:if test="${srv=='EYE'}"><spring:message code="sales.header.eye"/></c:if>
				<c:if test="${srv=='DEL'}"><spring:message code="sales.header.del"/></c:if> 
				-->
				
				<!--header-->
				<c:choose>
					<c:when test="${(srv=='EYE') and (housingType =='MASS')}"><spring:message code="sales.header.eye"/></c:when>
					<c:when test="${(srv=='DEL') and (housingType =='MASS')}"><spring:message code="sales.header.del"/></c:when>
					<c:otherwise><spring:message code="sales.header.pt"/></c:otherwise>
				</c:choose>
				<!--header end-->
				
			</div>
              
            </div>
				<div class="eye_logo">
				<c:if test="${srv=='EYE'}"><img src = "<spring:message code="reg.nav.logo.eye"/>"/></c:if>
				<c:if test="${srv=='DEL'}"><img src = "<spring:message code="reg.nav.logo.del"/>"/></c:if>
			</div>
				
			<div style="clear: both;"></div>
			</div>
			
            <form:form id="salesleadform" name="salesleadform" method="POST" commandName="salesleadform" action="salesleadform.html">
			<c:if test="${!salesleadform.submitted}">
			<form:hidden path="marker_idx" />
			<form:hidden path="reason" />
			<!--formbody_l-->
			<div class="formbody_l">
            <!--form-->
            <div class="formbody">
				<div class="welcome">
				<c:if test="${srv=='EYE'}"><spring:message code="sales.info.eye"/></c:if>
				<c:if test="${srv=='DEL'}"><spring:message code="sales.info.del"/></c:if>
				</div>
			
			<!--contact message-->			
			<div class="promotion_message">${salesleadform.contactMsg}</div>
			<!--contact message(End)-->
			
			<c:if test="${not empty salesleadform.markerDTO }">
				<div class="formrow">
					<div class="colA"><spring:message code="sales.addr"/> </div>
					<div class="colB">${salesleadform.installAddress }</div>
	                <div class="clearboth"></div>
				</div>
			</c:if>
			<div class="formrow">
			  <div class="colA"><spring:message code="sales.service"/> </div>
			  <div class="colB">${salesleadform.service}</div>
              <div class="clearboth"></div>
			</div>
				
			<div class="formrow">
			  <div class="colA"><spring:message code="sales.title"/> </div>
			  <div class="colB">
			  	<label for="title1">
			  	<form:radiobutton id="title1" value="MR" path="title"/>
				Mr.</label>
			  	<label for="title2">
			  	<form:radiobutton id="title2" value="MS" path="title"/>
				Ms.</label>
				<span id="title_err" class="err_msg">* Please select your Title.</span>
			  </div>
                <div class="clearboth"></div>
			</div>
            <c:if test="${not empty salesleadform.markerDTO }">
			<div class="formrow">
				<div class="colA"><spring:message code="sales.flat"/> </div>
				<div class="colD "><form:input cssClass="inputfieldflatfloor" id="flat" path="s_flat" maxlength="5"/></div>
				<div class="colC "><spring:message code="sales.floor"/></div>
				<div class="colE "><form:input cssClass="inputfieldflatfloor" id="floor" path="s_floor" maxlength="3"/>
			  		<span id="floor_err" class="err_msg" >* Please enter the floor.</span>
			  	</div>
				<div class="clearboth"></div>
			</div>
			</c:if>
			<div class="formrow">
				<div class="colA"><spring:message code="sales.name"/> </div>
			  <div class="colB"><form:input cssClass="inputfield" id="name" path="name" /><span id="name_err" class="err_msg" >* Please enter your name.</span></div>
				<div class="clearboth"></div>
			</div>

			<div class="formrow">
				<div class="colA"><spring:message code="sales.phone"/> </div>
				<div class="colB"><form:input cssClass="inputfield" id="cont_phone_no" path="contactNum" /><span id="cont_phone_no_err" class="err_msg">* Please enter your contact phone no.</span></div>
				<div class="clearboth"></div>
			</div>
            
            <div class="formrow">
				<div class="colA"><spring:message code="sales.email"/> </div>
				<div class="colB"><form:input cssClass="inputfield" id="email_addr" path="emailAddr" /><span id="email_addr_err" class="err_msg">* Please enter your email address.</span></div>
				<div class="clearboth"></div>
			</div>

			<div class="formrow">	
				<div class="colA"><spring:message code="sales.serviceDn"/> </div>
				<div class="colB"><form:input cssClass="inputfield" id="service_dn" path="serviceDN" /></div>
				<div class="clearboth"></div>
			</div>
			
			<!--Promotion message-->
			<div class="formrow">
				<div class="promotion_message">${salesleadform.promotionMsg}</div>
				
			</div>
			<!--Promotion message(End)-->
			
			<div class="formrow">
				<div class="colA">&nbsp;</div>
				<div class="colB"><div class="btn_normal">
					<img src="./images/${lang}/slf_sub_btn.gif" onclick="formSubmit()" />
				</div>	
				<div class="btn_live_chat">
				<a onclick="reg_open_chat(liveChatUrl,srvType)" class="cbutton mapbuttons">
					<img src="./images/${lang}/live_chat_btn.png" />	
				</a>
				</div></div>
				<div class="clearboth"></div>
			</div>	
			
		  	      					
	  		</div>
      <!--end of form-->
      </div>
	  <!--end dof formbody_l-->
	  </c:if>  
	  <c:if test="${salesleadform.submitted}">
	  <!-- submitted -->
	  <div class="formbody_l">
	  	<div class="formbody">
		<p>&nbsp;</p>
		<c:if test="${srv=='EYE'}"><div class="eye_Acknowledgement_one"> <spring:message code="sales.submitted1"/> </div></c:if>
		<c:if test="${srv=='DEL'}"><div class="del_Acknowledgement_one"> <spring:message code="sales.submitted1"/> </div></c:if>
		<br/>
		<div class="net_Acknowledgement_two"> <spring:message code="sales.submitted2"/> </div>
		<p>&nbsp;</p>
		</div>
	  </div>
	  <!--end of submitted-->
	  </c:if>
	</form:form> 
</div>
<div class="footer">
	<spring:message code="sales.remark"/> 
</div>
</body></html>