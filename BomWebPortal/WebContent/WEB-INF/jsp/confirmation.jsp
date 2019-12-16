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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>PCCW Mobile</title>
<link href="css/ssc.css" rel="stylesheet" type="text/css">
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<link href="css/imsds.css" rel="stylesheet" type="text/css"> 
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">
<script src="js/common.js" language="javascript"></script>
<%
	Locale locale = RequestContextUtils.getLocale(request);
	request.setAttribute("language", locale.toString());
	BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
	//String enableOnloadJScript =  request.getParameter("enableOnloadJScript");
%>
<script src="js/jquery-1.4.4.js"></script>
<script language="Javascript">

	$(document).ready(function() {
		
		$(".orderPreview").load("<c:url value="orderdetail.html"><c:param name="orderId" value="${orderId}"/><c:param name="action" value="ENQUIRY"/><c:param name="sbuid" value="${param.sbuid}"/></c:url> #wrapper"
					, function() {
						$(".orderPreview > div").removeAttr("id");
					});

	});

</script> 
<body> 
<div id="container">
<div id="head">
<div id="header">
	<table width="100%" border="0" cellspacing="0">  			
          
  					<tr>
        				<td><img src="images/topbar_left.png" width="470" height="58"></td>
        				<%-- <%if(bomsalesuser!=null){ %>
        				<td align="right"> <a href="welcome.html">Home</a> | <a href="logout.html">Logout</a></td>
        				<%}%>
          				<td align="right">
  						<div class="lang"> English </div>
  					   	</td> --%>
  					</tr>

	</table>
</div>
<div id="wrapper">
<form name="confirmation" method="post" action="confirmation.html">
<!-- end of button -->
<table width="100%"  class="tablegrey" background="images/background2.jpg">
  <tr> 
  	<td> 
	  <table width="100%"  border="0" cellspacing="1" >
	   	<tr>
	   	
	   	 <td  class="contenttextgreen" height="40">Your order has been submitted. 
				Thank you for your order. (${orderId})
		</td>
	
	   	</tr>
	   
	   		<c:if test="${emailReqResult!= null}">
	   			<tr>
	   			<c:choose>
						<c:when test='${emailReqResult!= null &&  emailReqResult == "SUCCESS"}'>
							
							<td class="contenttextgreen"> Email has been sent successfully to customer.</td>
						</c:when>
						<c:otherwise>
							
							<td  class="contenttext_red"><b>Failure in sending email. ${emailReqResult.message}
							</br>
							Please resend email by using "Resent email", or print the application form to customer if applicable.</b></td>
						</c:otherwise>
				</c:choose>	
				</tr>
				</c:if>
				
				<c:if test="${emailReqResultIGuardToCust!= null}">
				<tr>
				<c:choose>
						<c:when test='${emailReqResultIGuardToCust!= null && emailReqResultIGuardToCust == "SUCCESS"}'>
							
							<td class="contenttextgreen"> IGuard Email has been sent successfully to customer.</td>
						</c:when>
						<c:otherwise>
							
							<td  class="contenttext_red"><b>Failure in sending IGuard email.  ${emailReqResultIGuardToCust.message}
</br>
Please resend IGuard email by using "Resent email", or print the application form to customer if applicable.</b></td>
						</c:otherwise>
				</c:choose>	
				</tr>
				</c:if>
				
				<c:if test="${emailReqResultIGuardToComp!= null}">
				<tr>
				<c:choose>
						<c:when test='${emailReqResultIGuardToComp!= null && emailReqResultIGuardToComp == "SUCCESS"}'>
							
							<td class="contenttextgreen"> IGuard Email has been sent successfully to iGuard Company..</td>
						</c:when>
						<c:otherwise>
							
							<td  class="contenttext_red"><b>Failure in sending IGuard Company email.  ${emailReqResultIGuardToComp.message}
</br>
Please resend IGuard email by using "Resent email" to IGuard Company if applicable.</b></td>
						</c:otherwise>
				</c:choose>	
					</tr>
				</c:if>		
				
				<!-- HKT Care -->
				<c:if test="${enableHKTCareEmail}">
					<c:if test="${emailReqResultTravelInsuranceToCust != null}">
						<tr>
							<c:choose>
								<c:when test='${emailReqResultTravelInsuranceToCust == "SUCCESS"}'>
									<td class="contenttextgreen">Travel Insurance Email has been sent successfully to customer.</td>
								</c:when>
								<c:otherwise>
									<td class="contenttext_red">
										<b>Failure in sending Travel Insurance email. ${emailReqResultTravelInsuranceToCust.message}</br>
										Please resend Travel Insurance email by using "Resent email", or print the application form to customer if applicable.</b>
									</td>
								</c:otherwise>
							</c:choose>	
						</tr>
					</c:if>	
					
					<c:if test="${emailReqResultHelperCareToCust != null}">
						<tr>
							<c:choose>
								<c:when test='${emailReqResultHelperCareToCust == "SUCCESS"}'>
									<td class="contenttextgreen">HKT Care 2-year Helper Insurance Coupon Email has been sent successfully to customer.</td>
								</c:when>
								<c:otherwise>
									<td class="contenttext_red">
										<b>Failure in sending HKT Care 2-year Helper Insurance Coupon email. ${emailReqResultHelperCareToCust.message}</br>
										Please resend HKT Care 2-year Helper Insurance Coupon email by using "Resent email", or print the application form to customer if applicable.</b>
									</td>
								</c:otherwise>
							</c:choose>	
						</tr>
					</c:if>
				</c:if>
				
				<c:if test="${emailReqResultProjectEagleInsuranceToCust != null}">
					<tr>
						<c:choose>
							<c:when test='${emailReqResultProjectEagleInsuranceToCust == "SUCCESS"}'>
								<td class="contenttextgreen">Restart Service Email has been sent successfully to customer.</td>
							</c:when>
							<c:otherwise>
								<td class="contenttext_red">
									<b>Failure in sending Restart Service email. ${emailReqResultProjectEagleInsuranceToCust.message}</br>
									Please resend Restart Service email by using "Resent email", or print the application form to customer if applicable.</b>
								</td>
							</c:otherwise>
						</c:choose>	
					</tr>
				</c:if>	
				<c:if test="${emailReqResultMobileSafetyPhone != null}">
				<tr>
				<c:choose>
				<c:when test='${emailReqResultMobileSafetyPhone == "SUCCESS"}'>
					<td class="contenttextgreen"> 
						Mobile Safety Phone Service Email has been sent successfully to customer.
					</td>
				</c:when>
				<c:otherwise>
					<td class="contenttext_red" style="font-weight: bold">
						Failure in sending Mobile Safety Phone Service email.  ${emailReqResultMobileSafetyPhone.message}
						<br/>
						Please resend Mobile Safety Phone Service email by using "Resent email", or print the application form to customer if applicable.
					</td>
				</c:otherwise>
				</c:choose>	
				</tr>
				</c:if>
				
				<c:if test="${emailReqResultNFCSim != null}">
				<tr>
				<c:choose>
				<c:when test='${emailReqResultNFCSim == "SUCCESS"}'>
					<td class="contenttextgreen"> 
						NFC Sim Service Email has been sent successfully to customer.
					</td>
				</c:when>
				<c:otherwise>
					<td class="contenttext_red" style="font-weight: bold">
						Failure in sending NFC Sim Service email.  ${emailReqResultNFCSim.message}
						<br/>
						Please resend NFC Sim Service email by using "Resent email", or print the application form to customer if applicable.
					</td>
				</c:otherwise>
				</c:choose>	
				</tr>
				</c:if>
				
				<c:if test="${emailReqResultOctopusSim != null}">
				<tr>
				<c:choose>
				<c:when test='${emailReqResultOctopusSim == "SUCCESS"}'>
					<td class="contenttextgreen"> 
						Octopus Sim Service Email has been sent successfully to customer.
					</td>
				</c:when>
				<c:otherwise>
					<td class="contenttext_red" style="font-weight: bold">
						Failure in sending Octopus Sim Service email.  ${emailReqResultOctopusSim.message}
						<br/>
						Please resend Octopus Sim Service email by using "Resent email", or print the application form to customer if applicable.
					</td>
				</c:otherwise>
				</c:choose>	
				</tr>
				</c:if>	  
	  </table>
	 </td>
  </tr>
 </table>	
 
<div class="orderPreview"><span class="contenttextgreen">Loading...</span></div>
 

	<input type="hidden" name="currentView" value="confirmation"/>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>