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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Reminder</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="format-detection" content="telephone=no"/>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
 
function formSubmit() {
	//validation
	
	//submit
	parent.document.summaryForm.submit();
}

</script>
</head>

<body>
<form:form id="reminderForm" name="reminderForm" method="POST" commandName="reminderForm" action="reminder.html">
<c:if test="${not empty reminderForm.premItemSetList}" >
	<c:forEach items="${reminderForm.premItemSetList}" var="premiumImageSet" varStatus="itemSetStatus">
		<div class="dbs_middle2">
		  <div class="coupon">
		  <c:if test="${not empty premiumImageSet.itemDetails}">
				<c:forEach items="${premiumImageSet.itemDetails}" var="itemDetail" varStatus="itemStatus">
					<div class="card_remind ">
						<c:if test="${not empty itemDetail.imagePath}">
							<img src="getimage.html?id=${itemDetail.imagePath}" width="100%" height="100%" />
						</c:if>
					</div>
				</c:forEach>
			</c:if>
		  </div>
		  <div class="couponText2">
		  <div class="close_btn"><a href="#"><spring:message code="reminder.close" /></a></div>
		  <div class="reminder_content"><spring:message code="reminder.title" /></div>
		  <div class="couponTextB">${premiumImageSet.itemSetDesc}<br />
		<!--<span class="small10px">(Eligible if payment by DBS bank credit card)</span>-->
		
		
		  </div>
		
		  </div>
		<a href="#" onclick="formSubmit();"><span class="grey_btn"><spring:message code="reminder.continue" /></span></a>      
		    
		  
		  <div class="clearboth"></div>
		</div>
	</c:forEach>
</c:if>
</form:form>
</body>
</html>
