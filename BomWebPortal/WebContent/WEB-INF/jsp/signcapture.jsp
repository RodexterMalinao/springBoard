<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%><%@ page contentType="text/html;charset=UTF-8" 
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"
%><%@taglib prefix="form" uri="http://www.springframework.org/tags/form" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Springboard</title>
<link href="css/ssc.css" rel="stylesheet" type="text/css" />
<link href="css/ssc2.css" rel="stylesheet" type="text/css" />
<link href="css/imsds.css" rel="stylesheet" type="text/css" /> 
<link href="css/typographyEN.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="js/common.js" language="javascript"></script>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.signaturepad.min.js"></script>
<script type="text/javascript" src="js/excanvas.js"></script>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils,com.bomwebportal.dto.BomSalesUserDTO,com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO,com.bomwebportal.bean.LookupTableBean,com.bomwebportal.mob.ccs.dto.CodeLkupDTO,com.bomwebportal.util.ConfigProperties,java.util.*,java.util.Locale"
%>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
			response.setHeader("Pragma", "no-cache"); //HTTP 1.0
			response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%
	Map<String, List<CodeLkupDTO>> codeLkupList = LookupTableBean
			.getInstance().getCodeLkupList();
	List<CodeLkupDTO> dtos = codeLkupList.get("IPAD_VERSION");
	String ipadVersion = "";
	if (dtos != null && dtos.size() > 0) {
		ipadVersion = dtos.get(0).getCodeId();
	}

	String scheme = ConfigProperties
			.getPropertyByEnvironment("orddoc.ipad.uri.scheme");
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="loadingpanel.jsp"%>
		<script type="text/javascript">
			var api;
			
			$(document).ready(function(){
				var options = {drawOnly:true, penWidth:'3', lineTop:0, lineWidth:0};
				api = $('.sigPad').signaturePad(options);
				$("div#header").hide();
				$("div#footer").hide();
				$("div#container").addClass('bottomDiv');
				
			});
			
			function onSubmit() {
				//alert('onSubmitonSubmit');
				document.getElementById("signature").value=api.getSignatureString();
				//document.signoffForm.submit();
			}

			function onReset() {
				alert('onReset');
				document.signCaptureForm.submit();
			}
			function submitform()
			{
			  onSubmit();
			  $('#signCaptureForm').submit();
			  document.signCaptureForm.submit();
			}

		</script>
<style type="text/css">

.bottomDiv {
    margin: auto;
    position: absolute;
    bottom: 0;
}

</style>
</head>
<body>
<form:form id="signCaptureForm" name="signCaptureForm" commandName="signCaptureModel" method="post" action="#">
	<div>
	<table border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
		
		<tr class="contenttextgreen">
			<td height="40">Please capture signature for your order (${signCaptureDTO.orderId}) / Customer Name: ${contactName} / MRT Number: ${orderDto.msisdn} / Sales: ${signCaptureDTO.lastUpdBy}
			</td>
		</tr>	

		<tr class="table_title">
			<td>${signCaptureDTO.remark}</td>
		</tr>

      	<tr>
		    <td>
		    <c:choose>
		    <c:when test="${success == true}">
		    	<div style="width: 800px; height: 350px;" class="sigPad">
                    <div class="sig sigWrapper" style="width: 800px; height: 300px; border: 3px solid black; background-color:LightGrey;">
                   		<center><h1>Signature Captured</h1><br>
                   		<a href="?reqId=${signCaptureDTO.reqId}" class="nextbutton3" name="confirm"> Click here to continue</a>
                   		</center>
                   </div>
                   <br> 
				
            </div>
		    </c:when>
	
		    <c:otherwise>
			    <div style="width: 800px; height: 350px;" class="sigPad">
	                    <div class="sig sigWrapper" style="width: 800px; height: 300px; border: 3px solid black; background-color:LightGrey;">
	                   	<canvas id="canvasSignature2" width="800" height="300"></canvas>
	                   </div>
	                   <br> 
					<a href="#" class="clearButton nextbutton3" name="clear">Clear </a> 
					<a href="#" class="nextbutton3" name="confirm"	onclick="javascript:submitform();"> Confirm </a>
	            </div>
            </c:otherwise>
            </c:choose>
			</td>
	  	</tr>
	  	<tr>
	  	<td>
	  	 	<div align="right">
            	<a href="<%=scheme%>://${signCaptureDTO.orderId}/${signCaptureDTO.createBy}/${currentSessionId}/<%=ipadVersion%>" class="nextbutton3">Capture Proof by iPad </a>            
       	 	</div>  
	  	</td>
	  	</tr>
	  	
	  	<jsp:include page="digitalFormPreview.jsp">
	        <jsp:param name="directFrom" value="signcapture"/>
	        <jsp:param name="reqId" value="${signCaptureDTO.reqId}"/>
	        <jsp:param name="sales" value="${signCaptureDTO.lastUpdBy}"/>
	    </jsp:include>
	  	
    </table>
    </div>  
	<form:hidden path="signature" id="signature"/>
</form:form>
</body>
</html>
