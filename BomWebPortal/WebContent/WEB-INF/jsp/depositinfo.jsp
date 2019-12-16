<!-- to display the detailed deposit information (Between Support Doc and Summary) 
Last Modified on Oct 21 2013-->

<jsp:include page="/WEB-INF/jsp/header.jsp">
	<jsp:param name="enableOnloadJScript" value="Y" />
</jsp:include>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<% String mobuidParameter= (String)request.getParameter("sbuid");%>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="depositinfo" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>
<br>

<%@ include file="loadingpanel.jsp" %>

<style type="text/css">
.wrapContent { display: inline-block; width: 100%; padding: 10px 0 }
.depositForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-top: 21px; margin-left: 1px }
.depositForm  h3 { margin: 0; font-size: medium; color: white; background-color: #6CA9F5; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.depositForm  legend { color: #7F7F7F }
.depositForm  .content { margin-left: 2em }
.depositForm  .half { margin: 3px 0; width: 45%; display: inline-block; float: left }
.depositForm  table { border-width: 1px; border-spacing: 0px; border-color: #7F7F7F; border-collapse: collapse }
.depositForm  table th { border-width: 1px; padding: 3px; border-style: inset; border-color: #7F7F7F; background-color: #6CA9F5; color: white }
.depositForm  table td { border-width: 1px; padding: 3px; border-style: inset; border-color: #7F7F7F; text-align: center }
.depositForm  .springboardForms { display: block }
/*.depositForm  .depositinfo { display: block }*/
.depositForm  .depositinfo .note { color: #7F7F7F }
.depositForm  .buttons { padding-top: 1em; text-align: right }
.clear2 { clear: both }
</style>

<script>
$(document).ready(function() {
	$(".continueBtn").click(function() {
		$("#depositForm").submit();
	});
	
	<c:if test="${user.channelId ne '10'}">	
	$(".auth_button").show();

	
	$(".auth_button").click(function(e) {
		e.preventDefault();
		var sURL;
		var orderID = "<c:out value='${orderDTO.orderId}'/>";
		if(orderID != null && orderID !="")			
			sURL = "mobccsauthorize.html?" + $.param({"action": "AU07", "orderId": "<c:out value='${orderDTO.orderId}'/>"});
		else
			sURL= "mobccsauthorize.html?" + $.param({"action": "AU07"});
		
		$('#authDialog').html('<iframe style="border: 0px; " src="' + sURL + '" width="100%" height="100%"></iframe>')
        .dialog({
            autoOpen: false,
            modal: true,
            height: 300,
            width: 500,
            title: "Authorize"
        });
		$('#authDialog').dialog('open');
		return false;
		
	});
	</c:if>
});

function authorized() {
	$('#authDialog').dialog('close');
	$("select.reasonCd").removeAttr("disabled");
}

</script>


<div class="depositForm contenttext">
<form:form action="depositinfo.html?sbuid=${param.sbuid}" method="post"
	commandName="depositInfo" id="depositForm">

<h3>Deposit Info</h3>
<div class="wrapContent">
	<div class="content">
		<table class="depositinfo" style="width:90%;">
		<colgroup style="width:25%"></colgroup>
		<colgroup style="width:25%"></colgroup>
		<colgroup style="width:100%"></colgroup>
		<thead>
		<tr>
			<th>Deposit Item</th>
			<th>Deposit Amount</th>
			<th>Waive Reason</th>
		</tr>
		</thead>

		<tbody>
		<c:choose>
		<c:when test="${not empty depositInfo.depositDTOList }">
		<c:forEach items="${depositInfo.depositDTOList}" var="deposit"  varStatus="status">
		<tr> 
			<td> 
				<c:out value="${deposit.depositDesc}"/>
			</td> 
			<td> 
				<c:out value="${deposit.depositAmount}"/>
			</td> 		
           	<td>
           		<c:choose>
           		<c:when test="${deposit.waivable eq 'Y'}">
				<form:select path="depositDTOList[${status.index}].reasonCd" cssStyle="width:80%" disabled="true" cssClass="reasonCd">
					<form:option value="" label="- N/A -"/>
					<form:options items="${waiveReasons }" itemValue="reasonCd" itemLabel="reasonDesc"/>
				</form:select>
				</c:when>
				<c:otherwise>
					N/A
				</c:otherwise>
				</c:choose>
			</td> 
 		</tr>
		</c:forEach>
		<c:if test="${user.channelId ne '10' && (empty orderDTO.ocid)}">
		<tr>
			<td colspan="3" style="padding: 20px; text-align: right;">
				<a href="#" class="nextbutton3 auth_button" style="vertical-align: middle;display:none;">Authorize</a>
			</td>
		</tr>
		</c:if>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="3" style="padding: 20px; text-align: center;">N/A</td>
			</tr>
		</c:otherwise>
		</c:choose>
		</tbody>
		</table>
	</div>
	
</div>


<div class="buttonmenubox_R">
	<input type="hidden" name="currentView" value="depositinfo"/>
	<a href="#" class="nextbutton3 continueBtn">Continue</a>
</div>

</form:form>
</div>
<div id="authDialog"></div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
