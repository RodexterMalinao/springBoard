<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${!ltsAcqCollectDocCmd.amend}"> --%>
<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%-- </c:if> --%>
<script type="text/javascript" src="js/web/lts/acq/ltsacqcollectdoc.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>


<form:form method="POST" id="ltsAcqCollectDocForm" name="ltsAcqCollectDocForm" commandName="ltsAcqCollectDocCmd" autocomplete="off">

	<c:set var="amend" value='${ltsAcqCollectDocCmd.amend}' />
	<c:set var="submit" value='<%="true".equals(request.getParameter("submit"))%>' />
	
	<form:hidden path="submitType"/>
	<input type="hidden" name="warning" id="warning" value="${sessionScope.warning}" />
	<input type="hidden" name="readAlready" id="readAlready" value="${sessionScope.readAlready}" />
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<table width="100%" border="0" cellspacing="1" cellpadding="3" class="paper_w2">
		<tr></tr>
		<tr>
			<td colspan="5" class="table_title" id="s_line_text">Document Fax Serial</td>
		</tr>
		<tr>
		<td colspan="4">
		<c:if test="${amend && submit && empty sessionScope.warning}">
			<div class="ui-widget" style="visibility: visible;">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
					<div class="contenttext"><p><b>Fax Serial Number Updated.</b></p></div><p></p>
				</div>
			</div>
			<br/>
		</c:if>
		<c:if test="${not empty sessionScope.warning}">
			<div class="ui-widget" style="visibility: visible;">
				<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
					<div class="contenttext"><p>${sessionScope.warning}</p></div><p></p>
				</div>
			</div>
			<br/>
		</c:if>
		<form:errors path="*" cssClass="error"/>
		<table width="100%" border="0" class="contenttext">
				<tr bgcolor="#FFFFFF">
					<td width="90%">
						<div class="supportDocDiv">
							<table width="100%" border="1" class="table_style">
								<tr  >
									<th width="20%">Document Type</th>
									<c:if test="${amend}">
									<th width="20%">Mandatory</th>
									</c:if>
									<c:if test="${!amend}">
									<th width="40%">Waive Reason</th>
									</c:if>
									<th width="20%">Collected</th>
									<th width="20%">Fax Serial No.</th>
								</tr>
								<c:choose>
									<c:when test="${not empty ltsAcqCollectDocCmd.collectDocDtoList}">
										<c:forEach items="${ltsAcqCollectDocCmd.collectDocDtoList}" var="collectDoc" varStatus="status">
											<c:if test="${collectDoc.markDelInd != 'Y'}">
											<tr align="center">
												<td>${collectDoc.docTypeDisplay}</td>
												<c:if test="${amend}">
												<td>${collectDoc.mandatory?'<b>Y</b>':'N'}</td>
												</c:if>
												<c:if test="${!amend}">
												<td><c:out value="${collectDoc.waiveReasonDisplay}" default="--" /></td>
												</c:if>
												<td>${collectDoc.collectedInd != 'Y'? 'N': collectDoc.collectedInd}</td>
												<td><form:input cssClass="faxSerialInput" path="collectDocDtoList[${status.index}].faxSerial"/></td>
											</tr>
											</c:if>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td align="center" colspan="4">NIL</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
					</td>
				</tr>
				<c:if test="${not empty ltsAcqCollectDocCmd.collectDocDtoList}">
					<tr>
						<td>
							<br>
							<div class="func_button" style="float: right;"><a href="#" id="copySerialBtn" >Copy to All</a></div>
						</td>
					</tr>
				</c:if>
		</table>
		</tr>
	</table>
	<c:if test="${not empty ltsAcqCollectDocCmd.collectDocDtoList}">
		<table width="100%" border="0" cellspacing="0" class="paper_w2">
			<tr>
				<td align="right" > 
					<br>
					<a href="#" id="submit" class="nextbutton"><div class="button">Submit Serial</div></a> 
				</td>
			</tr>
			<c:if test="${amend}">
				<tr>
					<td align="right" > 
						<br>
						<a href="#" id="amend_continue" class="nextbutton"><div class="button">Continue</div></a> 
					</td>
				</tr>
			</c:if>
		</table>
	</c:if>
</form:form>

<script type='text/javascript'>
	$(document).ready(function() {
		if($('input[name="warning"]').val() != '' && $('input[name="warning"]').val() != 'null'){
			if($('input[name="readAlready"]').val() != "true"){
				window.alert($('input[name="warning"]').val());
			}
		}else if(location.search.indexOf('submit=true') !== -1
				&& location.search.indexOf('isAmend=true') == -1){
			window.opener.$.blockUI({ message: null });
			window.opener.location.reload(true);
			window.close();
		}
	});
	$(ltsacqcollectdoc.actionPerform);
</script>

<c:if test="${!ltsAcqCollectDocCmd.amend}">
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</c:if>