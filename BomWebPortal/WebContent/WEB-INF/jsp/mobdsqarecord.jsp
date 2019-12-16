<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util"%>
<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	/*$("input[name=createButton]").click(function() {
		window.location.href = "<c:url value="mobdsqarecordcreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="seqNo" value="${sessionSeqNo}"/></c:url>";
	});*/
});

function formSubmit(actType) {
	var errorFound = false;
	if ($("textarea[name=remark]") > 0 && $("textarea[name=remark]").val().length > 4000) {
		$(".error_remark").show();
		errorFound = true;
	} 
	if (errorFound) {
		e.preventDefault();
		return false;
	} else {
		$("#actionType").val(actType);
		document.qaRecordFrom.submit();
	}
}
</script>

<div style="clear:both;padding-top:5px"></div>

<form:form name="qaRecordFrom" method="POST" commandName="qaRecord">
<div class="row" style="font-weight: bold; color:red">
<c:if test="${actionType == 'success'}">
	The record is saved successfully.
</c:if>
<c:if test="${error == 'qaOptionError'}">
	Please select QA Option Status.
</c:if>
</div>
<table width="100%" border="0" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:120px"></colgroup>
	<colgroup></colgroup>
	<tr>
	<td colspan="13">QA Record (Order ID : <c:out value="${param.orderId}" />)</td>
	</tr>
</table>

<div style="clear:both;padding-top:5px"></div>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"%>
<%
	String scheme = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");
%>

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="resultListTable">
	<thead>
		<tr>
		<td class="table_title">Seq No</td>
		<td class="table_title">Date Time</td>
		<td class="table_title">Staff ID</td>
		<td class="table_title">Must Q Option</td>
		<td class="table_title">QA Status Option</td>
		<td class="table_title">Remarks</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${empty recordList}">
			<tr>
			<td colspan="13">No record</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach items="${recordList}" var="record">
				<tr>
				<%-- <td><a href='<c:url value="mobdsqarecord.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="seq" value="${record.seqNo}"/></c:url>'><c:out value="${record.seqNo}" /></a></td> --%>
				<td><c:out value="${record.seqNo}" /></td>
				<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${record.dateTime}" /></td>
				<td><c:out value="${record.staffId}" /></td>
				<td><c:out value="${record.mustQ}" /></td>
				<c:choose>
					<c:when test="${record.qaOption == 1}">
						<td>CROSS OUT</td>
					</c:when>
					<c:when test="${record.qaOption == 2}">
						<td>RNA</td>
					</c:when>
					<c:when test="${record.qaOption == 3}">
						<td>PASS QC</td>
					</c:when>
					<c:when test="${record.qaOption == 4}">
						<td>RE-QC</td>
					</c:when>
					<c:when test="${record.qaOption == 5}">
						<td>RE-VERIFICATION</td>
					</c:when>
					<c:when test="${record.qaOption == 6}">
						<td>QA PROBLEM</td>
					</c:when>
					<c:when test="${record.qaOption == 7}">
						<td>WRONG INFO</td>
					</c:when>
					<c:otherwise>
						<td>Error!</td>
					</c:otherwise>
				</c:choose>
				<td><c:out value="${record.remark}" /></td>
				</tr>
			</c:forEach>
		</c:otherwise>
		</c:choose>
	</tbody>
	<tfoot>
	<c:if test='${actionType != "CREATE"}'>
		<tr>
			<td colspan="13">
				<a href="#" class="nextbutton" onClick="javascript:formSubmit('CREATE');" style="float:right">Create QA Request</a>
			</td>
		</tr>
	</c:if>
	</tfoot>
</table>

<div style="clear:both;padding-top:10px"></div>

<c:if test="${actionType == 'CREATE'}">
	<table width="100%" border="0" cellspacing="5" class="contenttext" bgcolor="#FFFFFF">
	<thead>
	<tr>
		<td  class="table_title" colspan="2">Create new QA Request</td>
	</tr>
	</thead>
	<tbody>
		<tr>
			<td style="float:right;">Seq No.: </td>
			<td>
				<form:input path="seqNo" id="seqNo" readonly="true" />
			</td>
		</tr>
		<tr>
			<td style="float:right;">Staff ID: </td>
			<td>
				<form:input path="staffId" id="staffId" readonly="true" />
			</td>
		</tr>
		<c:if test="${user.category == 'ORDER SUPPORT'}">
			<tr>
				<td style="float:right;">Must Q Option: </td>
				<td><form:checkbox path="mustQ" value="Y" /></td>
			</tr>
		</c:if>
		<tr>
			<td style="float:right;">QA Status Option: </td>
			<td>
				<form:select path="qaOption">
					<form:option value="0" label="Select" />
					<form:options items="${optionList}" itemValue="qaOption" itemLabel="optionDesc" />
				</form:select>
			</td>
		</tr>
			<tr>
				<td style="float:right;">Remarks: </td>
				<td><form:textarea path="remark" rows="5" cols="80" />
				<span class="error error_msg error_remark" style="display:none">Remark over 4000 characters</span>
				</td>
			</tr>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="13">
				<a href="#" class="nextbutton" onClick="javascript:formSubmit('QUIT');" style="float:right">Quit</a>
				<a href="#" class="nextbutton" onClick="javascript:formSubmit('SAVE');" style="float:right">Save</a>
			</td>
		</tr>
	</tfoot>
	</table>
</c:if>
	<input type="hidden" name="oderId" id="oderId" />
	<input type="hidden" name="actionType" id="actionType" />
	<table width="100%" border="0" cellspacing="0" class="contenttext" >
	<tr>
	<td align="center">
		<c:set var="neworderType" value="${fn:substring(param.orderId, 5, 7)}"></c:set>
		<c:set var="mobcosbaseurl" value="<%=scheme %>"></c:set>
		<c:if test="${user.category == 'ORDER SUPPORT'}">
			<a href="#" class="nextbutton" 
					onClick="window.location='mobdsorderreview.html' ">Back to Review</a>
			<sb-util:sso url="${mobcosUrl}enquiryorder.html?orderId=${orderId}&amp;staffId=${bomsalesuser.username}&amp;action=ENQUIRY" var="cosOrderReviewUrl"/>
		 	<a href="${cosOrderReviewUrl}" title="Order Enquiry for MOB">${orderId}</a>
		</c:if>
		
		<c:if test="${user.category == 'SUPERVISOR'}">
			
			<a href="orderdetail.html?orderId=${param.orderId}&action=REVIEW&appInd=N" 
					class="nextbutton">Back to Order Review</a>
		</c:if>
		<c:choose>
			<c:when test="${createInd == 'Y' && user.category == 'ORDER SUPPORT'}">
			  <c:choose>
			  	<c:when test="${neworderType == 'MR' || neworderType == 'MU' || neworderType == 'MC' || neworderType == 'MB' || neworderType == 'MT'}">
			  			<sb-util:sso url="${mobcosbaseurl}enquiryorder.html?orderId=${param.orderId}&staffId=${bomsalesuser.username}&action=ENQUIRY" var="cosOrderPreviewUrl"/>
			  			<a href="${cosOrderPreviewUrl}" class="nextbutton">Back to Order Detail</a>
				</c:when>
				<c:otherwise>
						<a href="orderdetail.html?orderId=${param.orderId}&action=REVIEW&appInd=N" 
						class="nextbutton">Back to Order Detail</a>
				</c:otherwise>
			  </c:choose>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${neworderType == 'MR' || neworderType == 'MU' || neworderType == 'MC' || neworderType == 'MB' || neworderType == 'MT'}">
						<sb-util:sso url="${mobcosbaseurl}enquiryorder.html?orderId=${param.orderId}&staffId=${bomsalesuser.username}&action=ENQUIRY" var="cosOrderPreviewUrl"/>
			  			<a href="${cosOrderPreviewUrl}" class="nextbutton">Back to Order Detail</a>
					</c:when>
					<c:otherwise>
						<a href="orderdetail.html?orderId=${param.orderId}&action=ENQUIRY" 
							class="nextbutton">Back to Order Detail</a>
					</c:otherwise>
				</c:choose>	
			</c:otherwise>
		</c:choose>
	</td>
	</tr>
</table> 
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>