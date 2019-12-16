<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tr>
	<td>
		<c:forEach items="${signCaptureAllOrdDocDTOList}" var="allOrdDoc" varStatus="status">
			<div>
				<c:choose>
					<c:when test="${param.directFrom == 'summary' && allOrdDoc.docType == 'M001'}">
						<a href="reportdownload.html?orderId=${allOrdDoc.orderId}&filePath=${allOrdDoc.url}&directFrom=${param.directFrom}&sales=${param.sales}" target="_blank" class="generate_form_a">${allOrdDoc.docName}</a>
						<span class="digital_signature_div2" style="color:red;">
							&nbsp;&nbsp;&nbsp;Digital Signature function will enable after Preview Application Form. 
						</span>
					</c:when>
					<c:when test="${param.directFrom == 'signcapture'}">
						<a href="reportdownload.html?orderId=${allOrdDoc.orderId}&filePath=${allOrdDoc.url}&directFrom=${param.directFrom}&reqId=${param.reqId}&sales=${param.sales}" target="_blank">${allOrdDoc.docName}</a>
					</c:when>
					<c:otherwise>
						<a href="reportdownload.html?orderId=${allOrdDoc.orderId}&filePath=${allOrdDoc.url}&directFrom=${param.directFrom}&sales=${param.sales}" target="_blank">${allOrdDoc.docName}</a>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>
	</td>
</tr>