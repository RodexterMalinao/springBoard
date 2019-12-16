<%@ include file="/WEB-INF/jsp/header2.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>


<c:set var="edit" value="${param['action'] ne 'ENQUIRY' }"/>
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:110px"></colgroup>
<tr class="contenttextgreen">
<td class="table_title" colspan="2">Staff Sponsorship</td>
</tr>
<tr>
<td>
<form:form name="mobSponsorshipForm" method="POST" commandName="mobSponsorshipDTO">

	<table width="100%">
		<colgroup style="width:200px"></colgroup>
		<colgroup style="width:350px"></colgroup>
		<tr>
			<td>Staff Id:</td>
			<td><form:input path="staffId" readonly="${not edit}"/>
			<div style="clear:both"></div>
            <form:errors path="staffId" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td>CCC:</td>
			<td><form:input path="ccc" readonly="${not edit}"/>
			<div style="clear:both"></div>
            <form:errors path="ccc" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td>Sponsorship Level:</td>
			<td><sb-util:codelookup codeType="SPONSOR_LEVEL" codeId="${mobSponsorshipDTO.sponsorLevel }"/></td>
		</tr>
		<tr>
			<td colspan="2" align="right">
			<c:choose>
			   <c:when test="${edit }">
			     <input type="submit" value="Save and Exit"/>
			   </c:when>
			   <c:otherwise>
			     <button type="button" onclick="window.close();">Cancel</button>
			   </c:otherwise>
			</c:choose>

			</td>
		</tr>	
	</table>
	
</form:form>
</td>
</tr>
</table>