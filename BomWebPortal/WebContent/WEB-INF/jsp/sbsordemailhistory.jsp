<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript">

function closeAndNotify(notify) {
	// Firefox lose window.dialogArugments after post form
	if (notify == true) {
		var callback = (window.dialogArguments ? window.dialogArguments : window.opener).reloadcallback;
		if (callback) callback();
	}
	self.close();
};


$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("#closeButton").click(function(e) {
		//window.close();
		closeAndNotify(false);
	});
	


});
</script>
<style type="text/css">
.label { display: inline-block; font-weight: bold; width: 120px }
.row { height: 24px }
.input { display: inline-block }
.input label { display: inline-block; width: 50px }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.wrapContent { display: inline-block; width: 100%; padding: 10px 0 }
.previewForm .content_detail { margin-left: 2em }
.previewForm .half { width: 40%; float: left }
.header tr { background-color: #E8F2FE }
.even { background-color: #E8FFE8 }
.success { color: blue }
.fail { color: red }
h2 { margin: 0 }
.histList { width: 100%; clear: both; background-color: white }
.histList td { text-align: center }
#emailContent { padding: 1em; border: 1px solid #F2F2F2; margin-top: 1em; margin-right: 2em }
.page_navigation { text-align: center }
.page_navigation .first_link, .page_navigation .previous_link, .page_navigation .next_link, .page_navigation .last_link { color: black; padding: 0 0.5em }
.info_text { font-weight: bold; padding: 0 3em }
</style>

<base target="_self"/>

<span class="contenttextgreen">Order ID: <c:out value="${param.orderId}" /></span>

<div class="clear:both"></div>

<br/><br/>
<form:form  id="form"  method="post" action="#">

<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<tr>
		<td class="table_title">Order Email Request</td>
	</tr>
	<tr>
		<td>
		
		
<table class="histList">
<colgroup style="width:125px"></colgroup>
<colgroup style="width:45px"></colgroup>
<colgroup style="width:250px"></colgroup>
<colgroup style="width:200px"></colgroup>
<colgroup style="width:125px"></colgroup>
<thead class="header">
<tr>
	<th>Order ID</th>
	<th>Seq #</th>
	<th>E-mail Type</th>
	<th>Email Address</th>
	<th>Request Date</th>
	<th>Request By</th>
	<th>Result</th>
	<th>&nbsp;</th>
</tr>
</thead>
<c:choose>
	<c:when test="${empty ordEmailReqList }">
		<tr>
			<td colspan="7">No Record</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${ordEmailReqList}" var="record" varStatus="status">
		
			<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 20}"> style="display:none"</c:if>>
				<td style="text-align:left">
					${record.orderId }
				</td>
				<td><c:out value="${record.seqNo}"/></td>
				<td><c:out value="${record.emailType}"/></td>
				<td><c:out value="${record.emailAddr}"/></td>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.requestDate}"/></td>
				<td><c:out value="${record.createBy}"/></td>
				<td style="text-align:left">
					<c:choose>
						<c:when test="${record.sentDate == null}">
							<c:out value="${record.errMsg}"/>
						</c:when>
						<c:otherwise>
							Sent on <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${record.sentDate}"/>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:if test="${record.templateId ne 'RT011'}">
						<a href="ordersendemailhist.html?orderId=${record.orderId}&templateId=${record.templateId}&action=PREVIEW&hideToolbar=true">Resend</a>
					</c:if>&nbsp;
				</td>
			</tr>
		
		</c:forEach>
	</c:otherwise>
</c:choose>
</table>

		</td>
	</tr>
</table>
</form:form>
</form>
<br/>



<div style="clear:both"></div>

<div style="float:right;padding:10px 0">
<input type="button" id="closeButton" value="Close">
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>