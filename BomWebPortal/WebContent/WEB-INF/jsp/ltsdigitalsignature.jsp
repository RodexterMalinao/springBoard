<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/jquery.signaturepad.min.js"></script>
<script type="text/javascript" src="js/excanvas.js"></script>
<script type="text/javascript" src="js/web/lts/ltsdigitalsignature.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<form:form method="POST" id="ltsDigitalSignatureForm" name="ltsDigitalSignatureForm" commandName="ltsDigitalSignatureCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="bankSignRequired" />
<form:hidden path="secDelCustSignRequired" />
<form:hidden path="recontractToSignRequired" />
<form:hidden path="signatureIndex" />

<c:set var="showSignature" value="false" />
<c:if test="${not empty ltsDigitalSignatureCmd.signatureList }">
	<c:forEach items="${ltsDigitalSignatureCmd.signatureList}" var="signature" varStatus="status">
		<c:if test="${status.index == ltsDigitalSignatureCmd.signatureIndex}">
		<table border="0" cellspacing="0" cellpadding="0" width="100%" class="paper_w2">
			<tr><td>
			<div id="s_line_text"></div>
			<table border="0" cellspacing="0" cellpadding="0" width="98%" align="center">
				<tr class="title_a">
					<td colspan="2">
						${signature.titleEng}
						<br> 
						${signature.titleChi}
						<c:if test="${signature.signed }">
							<br>
							<span style="color: blue; " > (This signature has been signed before) </span>
							<br>
							<span style="color: blue; " > (已簽署) </span>
						</c:if>
					</td>
				</tr>
			</table>
			<br>
			<div class="${signature.signed ? 'resignDiv' : ''}">
				<table width="98%" align="center" border="0" cellspacing="0" cellpadding="0" class="title_c">
					<tr >
						<td colspan="2">
							${signature.noteEng}					
							<br>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							${signature.noteChi}
							<br>
						</td>
					</tr>
				</table>
				<table border="0" cellspacing="0" cellpadding="0" width="98%" align="center">
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					
					<c:if test="${ltsDigitalSignatureCmd.secDelCustSignRequired 
									&& (signature.signType == 'EYEX_CUST_SIGN' ||  signature.signType == 'EYEX_THIRD_PARTY_SIGN'
											|| signature.signType == 'REL_DEL_CUST_SIGN' ||  signature.signType == 'REL_DEL_THIRD_PARTY_SIGN') }">
						<tr>
							<td width="1%">
								<form:checkbox path="sameAsSecDelCustSign" />
							</td>
							<td class="bold">
								Replicated to Residential Telephone Line Application Form?
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td  class="bold">
								複製到家居電話線申請書?
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>		
					</c:if>
									
					<c:if test="${ltsDigitalSignatureCmd.bankSignRequired 
									&& (signature.signType == 'EYEX_CUST_SIGN' ||  signature.signType == 'EYEX_THIRD_PARTY_SIGN'
									       || signature.signType == 'REL_DEL_CUST_SIGN' ||  signature.signType == 'REL_DEL_THIRD_PARTY_SIGN') }">
						<tr>
							<td width="1%">
								<form:checkbox path="sameAsBankSign" />
							</td>
							<td class="bold">
								Replicated to Credit Card/Bank Autopay Authorization? (*Same as Bank/Credit Card A/C Signature)
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td  class="bold">
								複製到信用卡/銀行戶口自動轉賬授權? (*須與銀行賬戶/信用咭簽署相同)
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>		
					</c:if>
					<c:if test="${ltsDigitalSignatureCmd.recontractToSignRequired 
									&& (signature.signType == 'EYEX_CUST_SIGN' ||  signature.signType == 'EYEX_THIRD_PARTY_SIGN'
									       || signature.signType == 'REL_DEL_CUST_SIGN' ||  signature.signType == 'REL_DEL_THIRD_PARTY_SIGN') }">
						<tr>
							<td width="1%">
								<form:checkbox path="sameAsRecontractToSign" />
							</td>
							<td class="bold">
								Replicated to Change of Registered Customer Application Form? 
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td class="bold">
								複製到更改登記客戶申請表?
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>		
					</c:if>					
				</table>
				<table border="0" cellspacing="0" cellpadding="0"  bgcolor="#FFFFFF" class="contenttext" width="98%" align="center">				
					<tr>
						<td colspan="2">
							<form:errors path="signatureList[${status.index}].signatureString" cssClass="errors" />
							<div class="sigPadError" style="display: none;">
								<span style="color: red; font-weight: bold;">Please sign the document.</span>
							</div>
							<div style="width: 800px; height: 350px;" class="sigPad">
								<div class="sig sigWrapper"
									style="width: 800px; height: 300px; border: 1px solid black;">
									<canvas id="canvasSignature2" width="800" height="300"></canvas>
									<form:hidden id="signatureString" path="signatureList[${status.index}].signatureString" />
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<table border="0" cellspacing="0" cellpadding="0" class="paper_w2" width="98%" align="center">
				<tr>
					<td width="50%">
						<div class="${signature.signed ? 'resignDiv' : ''}" style="${signature.signed ? 'display: none;' : ''}">
							<div class="func_button">
								<a href="#" class="clearButton nextbutton3" name="clear"> Clear </a>
							</div> 
							<div class="func_button">
								<a href="#" class="nextbutton3" name="confirm"> Confirm </a>
							</div>
						</div>
						<c:if test="${signature.signed}">							
							<div class="resignButtonDiv">
								<div class="button">
									<a href="#" class="nextbutton3" name="skip"> Continue </a>
								</div>
								<div class="button">
									<a href="#" class="resignBtn nextbutton3" name="resignBtn"> Re-Sign </a>
								</div>
							</div>
						</c:if>
					</td>
				</tr>
			</table>
			</td></tr>			
			</table>
		</c:if>
	</c:forEach>
</c:if>
</form:form>		

<script type='text/javascript'>
	$(ltsdigitalsignature.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>