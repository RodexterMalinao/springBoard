<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript" src="js/web/lts/ltscollectdoc.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>


<form:form method="POST" id="ltsCollectDocForm" name="ltsCollectDocForm" commandName="ltsCollectDocCmd" autocomplete="off">
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<input type="hidden" name="warningMsg" id="warningMsg" value="${sessionScope.faxWarningMsg}" />
	
	<table width="100%" border="0" cellspacing="1" cellpadding="3" class="paper_w2">
		<tr></tr>
		<tr>
			<td colspan="5" class="table_title" id="s_line_text">Support Document</td>
		</tr>
		<tr>
		<td colspan="4">
		<table width="100%" border="0" class="contenttext">
				<tr bgcolor="#FFFFFF">
					<td width="90%">
						<div class="supportDocDiv">
							<table width="100%" border="1" class="table_style">
								<tr  >
									<th width="20%">Document Type</th>
									<th width="40%">Waive Reason</th>
									<th width="20%">Collected</th>
									<th width="20%">Fax Serial No.</th>
								</tr>
								<c:choose>
									<c:when test="${not empty ltsCollectDocCmd.collectDocDtoList}">
										<c:forEach items="${ltsCollectDocCmd.collectDocDtoList}" var="collectDoc" varStatus="status">
											<c:if test="${collectDoc.markDelInd != 'Y'}">
											<tr align="center">
												<td>${collectDoc.docTypeDisplay}</td>
												<td><c:out value="${collectDoc.waiveReasonDisplay}" default="--" /></td>
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
				<c:if test="${not empty ltsCollectDocCmd.collectDocDtoList}">
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
	<c:if test="${not empty ltsCollectDocCmd.collectDocDtoList}">
		<table width="100%" border="0" cellspacing="0" class="paper_w2">
			<tr>
				<td align="right" > 
					<br>
					<a href="#" id="submit" class="nextbutton"><div class="button">Submit</div></a> 
				</td>
			</tr>
		</table>
	</c:if>
</form:form>

<script type='text/javascript'>
	$(document).ready(function() {
		if(location.search.indexOf('submit=true') !== -1) {
			if ( $('input[name="warningMsg"]').val() != "") {
				window.alert( $('input[name="warningMsg"]').val() );				
			}
			else {
				window.opener.$.blockUI({ message: null });
				window.opener.location.reload(true);
				window.close();	
			}
		}
	});
	$(ltscollectdoc.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>