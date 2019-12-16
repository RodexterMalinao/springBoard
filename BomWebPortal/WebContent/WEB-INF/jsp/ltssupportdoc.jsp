<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/web/lts/ltssupportdoc.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 9 : 10}" />
</jsp:include>


<div id="s_line_text"><spring:message code="lts.supportdoc.supDoc" text="Supporting Document"/></div>

<div class="cosContent">
<form:form method="POST" id="ltsSupportDocForm" name="ltsSupportDocForm" commandName="ltsSupportDocCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="formAction"/>
<form:hidden path="collectMethod" />


<c:if test="${not empty ltsSupportDocCmd.generatedFormList}">
	<table width="100%" class="paper_w2 round_10">
		<tr>
			<td >
				<br>
				<c:if test="${sessionScope.sessionLtsOrderCapture.channelPremier == true}">
					<table width="98%" align="center">
						<tr>
							<td colspan="4" ><div id="s_line_text"> <spring:message code="lts.supportdoc.signoffMode" text="Order Signoff Mode"/> </div><br></td>
						</tr>
						<tr>
							<td width="1%">&nbsp;</td>
							<td width="25%">
								<div>
									<form:radiobutton path="signoffMode" value="C" /> <b> <spring:message code="lts.supportdoc.ccMode" text="Call Center Mode"/> </b> 
								</div>
							</td>
							<td width="25%">
								<div>
									<form:radiobutton path="signoffMode" value="R" /> <b> <spring:message code="lts.supportdoc.rtMode" text="Retail Mode"/> </b> 
								</div>
							</td>
							<td width="49%">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4"> <form:errors path="signoffMode" cssClass="error"/> &nbsp; </td>
						</tr>
					</table>
				</c:if>
				<table width="98%" cellspacing="0" border="0" align="center">
					<tr>
						<td colspan="4" ><div id="s_line_text"> <spring:message code="lts.supportdoc.sbAFGen" text="Springboard Form Generation"/> </div><br></td>
					</tr>
					<tr>
						<td width="1%">&nbsp;</td>
						<td width="50%">
							<div>
								<c:forEach items="${ltsSupportDocCmd.generatedFormList}" var="generatedForm" varStatus="status">
								<c:if test="${status.index %2 == 0}">
									<form:checkbox path="generatedFormList[${status.index}].selected" disabled="true" />&nbsp; <b>${generatedForm.docName}</b>
								</c:if>
								<br>
								</c:forEach>
							</div>
						</td>
						<td width="50%">
							<div>
							<c:forEach items="${ltsSupportDocCmd.generatedFormList}"  var="generatedForm" varStatus="status">
								<c:if test="${status.index %2 != 0}">
									<form:checkbox path="generatedFormList[${status.index}].selected" disabled="true" />&nbsp; <b>${generatedForm.docName}</b>
								</c:if>
								<br>
							</c:forEach>
							</div>
						</td>
						<td width="1%">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4"> &nbsp; </td>
					</tr>
				</table>
				<table width="98%" cellspacing="0" border="0" align="center">
					<tr>
						<td colspan="2">
							<table width="100%" cellspacing="0" border="0" >
								<tr> 
                    				<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.supportdoc.distributeMode" text="Distribution Mode"/></td>
             					</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" class="contenttext">
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
								<c:if test="${!isBrCust}">
									<tr>
										<td width="1%">&nbsp;</td>
										<td width="60%">
											<form:radiobutton path="distributionMode" value="E" /> <b><spring:message code="lts.supportdoc.digiSign" text="Digital Signature"/></b>
											<form:errors path="distributionMode" cssClass="error"/>	
										</td>
										<td width="2%">&nbsp;</td>
									</tr>
									<tr>
										<td colspan="4">
											<div id="distributeMethodDiv">
												<table width="100%" cellspacing="2" border="0" >
													<tr>
														<td width="7%">&nbsp;</td>
														<td width="7%"><form:checkbox path="sendEmail" cssClass="digitalSignature"/> <spring:message code="lts.supportdoc.email" text="Email"/>: </td>
														<td width="85%">
															<form:input cssClass="digitalSignature" path="distributeEmail" size="25" />
															<form:errors path="distributeEmail" cssClass="error"/>													
														</td>
													</tr>
													<tr>
														<td width="10">&nbsp;</td>
														<td><form:checkbox path="sendSms" cssClass="digitalSignature"/> <spring:message code="lts.supportdoc.sms" text="SMS"/>: </td>
														<td>
															<form:input cssClass="digitalSignature" path="distributeSms" size="25" />
															<form:errors path="distributeSms" cssClass="error"/>													
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>														
												</table>				
											</div>										
										</td>
									</tr>
								</c:if>
								<c:if test="${ltsSupportDocCmd.allowDistributePaper}">
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="95%" colspan="2">
										<form:radiobutton path="distributionMode" value="P" /> <b><label id="distributionModePaperRadio" ><spring:message code="lts.supportdoc.paper" text="Paper"/></label></b>
									</td>
									<td width="2%">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
								</c:if>
							</table>
						</td>
					</tr>
				</table>
				
				<table width="98%" cellspacing="0" border="0" align="center">
					<tr>
						<td colspan="2">
							<table width="100%" cellspacing="0" border="0" >
								<tr> 
                    				<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.supportdoc.afLang" text="Application Form Language"/></td>
             					</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" class="contenttext">
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="95%">
										<table width="100%" border="0" class="contenttext">
											<tr>
												<td width="1%">&nbsp;</td>
												<td width="90%">
													<spring:message code="lts.supportdoc.lang" text="Language"/>:&nbsp;
													<form:select path="distributeLang">
														<form:option label="Select..." value=""/>
														<form:option value="CHN"><spring:message code="lts.supportDoc.tradChn" text="Traditional Chinese"/></form:option>
														<form:option value="ENG"><spring:message code="lts.supportDoc.eng" text="English"/></form:option>
													</form:select> 
													<form:errors path="distributeLang" cssClass="error"/> 
												</td>
												<td width="1%">&nbsp;</td>
											</tr>
										</table>
									</td>
									<td width="2%">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table width="98%" cellspacing="0" border="0" align="center">
					<tr>
						<td colspan="2">
							<table width="100%" cellspacing="0" border="0" >
								<tr> 
                    				<td colspan="4" class="table_title" id="s_line_text"><spring:message code="lts.supportdoc.reqSupDoc" text="Required Supporting Document"/></td>
             					</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" class="contenttext">
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="90%">
										<div class="supportDocDiv">
											<table class="table_style" width="100%" border="1">
												<tr>
													<th width="35%"><spring:message code="lts.supportdoc.docType" text="Docement Type"/></th>
													<th width="45%"><spring:message code="lts.supportdoc.waiveReason" text="Waive Reason"/></th>
													<th width="20%"><spring:message code="lts.supportdoc.mandatory" text="Mandatory Before Signoff"/></th>
												</tr>
												<c:choose>
													<c:when test="${not empty ltsSupportDocCmd.supportDocumentList }">
														<c:forEach items="${ltsSupportDocCmd.supportDocumentList}" var="supportDocument" varStatus="status">
															<tr align="center">
																<td>${supportDocument.docName}</td>
																<td>
																	<c:choose>
																		<c:when test="${not empty supportDocument.waiveReasonList}">
																			<form:select path="supportDocumentList[${status.index}].waiveReasonCd" cssClass="waiveReasonList">
																				<form:option value="" label="Select..."/>
																				<c:forEach items="${supportDocument.waiveReasonList}" var="waiveReason" varStatus="status">
																					<form:option value="${waiveReason.waiveReasonCd}" label="${waiveReason.waiveReasonDesc}" cssClass="${waiveReason.defaultWaiveInd}" />
																				</c:forEach>
																			</form:select>
																		</c:when>
																		<c:otherwise>
																			N/A
																		</c:otherwise>
																	</c:choose>
																</td>
																<td>
																	<c:out value="${supportDocument.mdoInd == 'M' ? 'Y' : 'N'}" />
																</td>
															</tr>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr>
															<td colspan="3">NIL</td>
														</tr>
													</c:otherwise>
												</c:choose>
											</table>
										</div>
									</td>
									<td width="1%">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>									
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<div id="errorDiv" style="width: 70%;">
							<div class="ui-widget" style="visibility: visible;">
								
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
									</p>
									<div id="error_msg" class="contenttext">
										<spring:message code="lts.supportdoc.invalidWaive" text="Invalid Waive Reason"/> :
									</div>
									&nbsp;&nbsp;
									<div id="reason_name" class="contenttext" style="font-weight: bold;">
									
									</div>
									<p></p>
								</div>
			
							</div>
						</div> 
						 </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
</c:if>

<br>
<c:if test="${not empty requestScope.errorMsgList || not empty param.errorMsg}">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="contenttext">
		<tr>
			<td width="2%">&nbsp;</td>
			<td>
				<div id="error" class="ui-widget" style="visibility: visible; width: 70%;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<c:forEach items="${requestScope.errorMsgList}" var="errorMsg">
							<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							</p>
							<div id="error_msg" class="contenttext">
								${errorMsg}
							</div>
							<p></p>										
						</c:forEach>
						<c:if test="${not empty param.errorMsg}">
							<p>
								<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							</p>
							<div id="error_msg" class="contenttext">
								${param.errorMsg}
							</div>
							<p></p>										
						</c:if>
					</div>
				</div>
			</td>
			<td width="5%">&nbsp;</td>
		</tr>
	</table>
</c:if>
		
<br>			
<table width="100%" border="0" cellspacing="0" class="contenttext">
<tr>
	<td align="right" > 
		<form:errors path="suspendReason" cssClass="error"/>
			<b><spring:message code="lts.payment.suspendReason" text="Suspend Reason"/> : </b>
		<form:select path="suspendReason" id="suspendReason">
			<form:option value="NONE"><spring:message code="lts.appointment.select" text="--- Select ---"/></form:option>
			<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
		</form:select>
		<a href="#" id="suspend" class="nextbutton"><div class="button"><spring:message code="lts.payment.suspend" text="Suspend"/></div></a>
		<a href="#" id="submit" class="nextbutton"><div class="button"><spring:message code="lts.mis.next" text="Next"/></div></a> 
	</td>
	
</tr>
</table>
</form:form>
</div>
<script type='text/javascript'>
	var isBrCust = "${isBrCust}" == "true";
	var isPt = "${sessionScope.sessionLtsOrderCapture.channelPremier}" == "true";
	$(ltssupportdoc.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>