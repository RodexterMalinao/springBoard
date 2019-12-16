<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/lts/disconnect/ltsterminateprogressbar.jsp" >
    <jsp:param name="step" value="5" />
</jsp:include>

<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/web/lts/disconnect/ltsterminatesupportdoc.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<form:form method="POST" action="#" id="ltsTerminateSupportDocForm" commandName="ltsTerminateSupportDocCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="formAction"/>
<form:hidden path="collectMethod" />

	<table width="98%" class="paper_w2 round_10" align="center">
		<tr>
			<td>
				<table width="98%" border="0" cellspacing="1" align="center">
					<tr>
						<td><br>
						<div id="s_line_text">LTS Termination Supporting Documents</div></td>
					</tr>
				</table>
				<table width="98%" border="0" cellspacing="1" class="contenttext" align="center">
					<tr>
						<td colspan="2">
							<table width="100%" cellspacing="0" border="0">
								<tr> 
                    				<td colspan="4"><div id="s_sub_line_text">Distribution Mode</div></td>
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
									<td width="95%" colspan="2">
										<form:radiobutton path="distributionMode" value="P"/> Paper
									</td>
									<td width="2%">&nbsp;</td>
								</tr>
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="20%">
										<form:radiobutton path="distributionMode" value="E"/> SMS
									</td>
									<td width="75%">
										<form:input cssClass="digital" path="distributeSms" size="25" />
										<form:errors path="distributeSms" cssClass="error"/>
									</td>
									<td width="2%">&nbsp;</td>
								</tr>
								
								<tr>
									<td colspan="4"> &nbsp; </td>
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
									<td  width="95%" colspan="2">Language:
									<form:select path="distributeLang">
									        <form:option label="Select..." value="" />
											<form:option label="Traditional Chinese" value="CHN" />
											<form:option label="English" value="ENG" />
										</form:select></td>
									<td>&nbsp;<form:errors path="distributeLang" cssClass="error" /></td>
								</tr>
								<tr>
									<td colspan="4"> &nbsp; </td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<table width="98%" class="paper_w2 round_10" align="center">
		<tr>
			<td>
				<table width="98%" border="0" cellspacing="1" align="center">
					<tr>
						<td><br><div id="s_line_text">Supporting Documents Required</div></td>
					</tr>
				</table>
				<table width="98%" border="0" cellspacing="1" class="contenttext" align="center">
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
											<table width="100%" border="1" class="table_style">
												<tr>
													<th width="35%">Document Type</th>
													<th width="45%">Waive Reason</th>
													<th width="20%">Mandatory Before Signoff</th>
												</tr>
												<c:choose>
													<c:when test="${not empty ltsTerminateSupportDocCmd.supportDocumentList }">
														<c:forEach items="${ltsTerminateSupportDocCmd.supportDocumentList}" var="supportDocument" varStatus="status">
															<tr>
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
															<td colspan="3" align="center"><b>NIL</b></td>
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
										Invalid Waive Reason :
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
<br>

<table width="100%" border="0" cellspacing="0" class="contenttext">
<tr>
	<td align="right" > 
		<form:errors path="suspendReason" cssClass="error"/>
			<b>Suspend Reason : </b>
		<form:select path="suspendReason" id="suspendReason">
			<form:option value="NONE">-- SELECT --</form:option>
			<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
		</form:select>
		<div class="button">
			<a href="#" id="suspend" class="nextbutton">Suspend</a>
		</div>
		<div class="button">
			<a href="#" id="next" class="nextbutton">Continue</a>
		</div>
	</td>
</tr>
</table>
</form:form>
<br>
<script type='text/javascript'>
	$(ltsterminatesupportdoc.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>