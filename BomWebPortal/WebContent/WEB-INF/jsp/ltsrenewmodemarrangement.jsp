<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="4" />
</jsp:include>

<form:form method="POST" id="ltsModemArrangementForm" name="ltsModemArrangementForm" commandName="ltsModemArrangementCmd" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />


<table width="100%">
	<tr>
		<td>
			<div id="s_line_text"><spring:message code="lts.modemArrangememt.modArrg" text="Modem Arrangement"/></div>
			<table width="100%" border="0" cellspacing="0">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="1" class="contenttext">
							<tr>
								<td>
									<input type="radio" value="" checked="checked"/>
									<b><spring:message code="lts.modemArrangememt.shareExistFSA" text="Share Existing FSA"/></b><span class="contenttext_red">*</span>
								</td>
							</tr>
						</table>
						<br>
					</td>
				</tr>
				<tr>
				<div id="existingFsaDiv" style="display: none;" class="paper_w2 round_10">
						<br/>
						<table width="98%" border="0" cellspacing="0" class="contenttext" align="center">																							
							<tr>
								<td colspan="4">
									<div id="s_line_text"><spring:message code="lts.modemArrangememt.fsaSelection" text="FSA selection"/></div>
									<table width="50%" border="0" cellspacing="0" class="contenttext">	
										<tr>
											<td width="100%" colspan="4">&nbsp;</td>
										</tr>																	
										<tr>
											<td width="100%" colspan="4">
												<form:hidden id="isImsVasEyeRentalRouter" path="rentalRouterExistFsaVas"/>
												<c:if test="${not empty ltsModemArrangementCmd.existingFsaList}">
													<table class="table_style" width="100%" border="1" cellspacing="0">
														<tr height="30">
															<th width="5%" align="center"><spring:message code="lts.modemArrangememt.fsa" text="FSA  "/></th>
															<th width="10%" align="center"><spring:message code="lts.modemArrangememt.existSrv" text="Existing Service"/></th>
															<th width="10%" align="center"><spring:message code="lts.modemArrangememt.loginID" text="Login ID"/></th>
															<th width="10%" align="center"><spring:message code="lts.modemArrangememt.effDate" text="Effective Date"/></th>
														</tr>
														<c:forEach items="${ ltsModemArrangementCmd.existingFsaList }" var="existingFsa" varStatus="existingFsaStatus">
															<c:if test="${existingFsa.selected}">
																<form:hidden id="selectedFsaRouterGrp" path="existingFsaList[${existingFsaStatus.index}].routerGrp"/>
																<form:hidden id="selectedFsaMeshRouterGrp" path="existingFsaList[${existingFsaStatus.index}].meshRouterGrp"/>
																<tr bgcolor="#FFFFFF" height="50">
																	<td align="center">
																		<c:out default="--" value="${existingFsa.fsa}"/>
																	</td>
																	<td align="center">
																		<form:hidden id="existingFsa.existingService${existingFsa.fsa}" path="existingFsaList[${existingFsaStatus.index}].existingService.name" />
																		<c:out default="--" value="${existingFsa.existingService.desc }"/>
																	</td>
																	<td align="center">
																		<c:out default="--" value="${existingFsa.loginId}"/>
																	</td>
																	<td align="center">
																		<c:out default="--" value="${existingFsa.effectiveDate}"/>
																	</td>
																</tr>
															</c:if>
														</c:forEach>														
													</table>
												</c:if>
											</td>
										</tr>
										<tr>
											<td width="100%" colspan="4"><b>&nbsp;</b></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</tr>
			
			<tr>
				<td>
					<div id="rentalRouterDiv">
					<table width="98%" cellspacing="0" border="0" align="center" class="contenttext">
					    <tbody><tr><td><div id="s_line_text"><spring:message code="lts.modemArrangememt.modemSelection" text="Modem Selection"/></div></td></tr>
						<tr>
						<td>
							<table width="100%" border="0" style="margin: 1em 0" class="contenttext">
								<tbody>
									<tr>
										<td width="25%" align="right"><b><spring:message code="lts.modemArrangement.HnRentalRouterSharing" text="HN Rental Router Sharing"/></b><span class="contenttext_red">*</span></td>
										<td style="line-height: 2em;">
											<form:radiobutton path="rentalRouterInd" id="rentalRouter" value="SHARE_RENTAL_ROUTER"/><spring:message code="lts.modemArrangement.yes"/>
											<form:radiobutton path="rentalRouterInd" id="BRM" value="BRM"/><spring:message code="lts.modemArrangement.no"/>
										</td>
									</tr>
								</tbody>
							</table>
							<div style="visibility: visible; margin: 0px 10%; display: none;" class="ui-widget" id="rentalRouterAlertMsgDiv">
								<div style="padding: 0px 1em; margin: 1em;" class="ui-state-error ui-corner-all">
									<p>
										<span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-alert"></span>
									</p>
									<div id="warning_msg" class="contenttext">
										<u><spring:message code="lts.modemArrangement.rentalRouter.alert.title"/></u>
										<br><br>
										<b><spring:message code="lts.modemArrangement.rentalRouter.alert.content1"/></b>
										<spring:message code="lts.modemArrangement.rentalRouter.alert.content2"/>
									</div>
									<br>
								</div>
							</div>
						</td>
						</tr>
						</tbody>
					</table>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<div id="modemDiv">
					<table width="98%" cellspacing="0" border="0" align="center" class="contenttext">
					    <tbody><tr><td><div id="s_line_text"><spring:message code="lts.modemArrangememt.modem" text="Modem"/></div></td></tr>
						<tr>
						<td>
							<table width="100%" border="0" class="contenttext" style="margin: 1em 0 ">
								<tbody>
									<tr>
										<td width="25%" align="right"><b><spring:message code="lts.modemArrangememt.lostModem" text="Lost Modem"/></b></td>
										<td>
											<form:checkbox path="lostModem" disabled="true"/>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
						</tr>
						</tbody>
					</table>
					</div>
				</td>
			</tr>
			</table>
		</td>
	</tr>
</table>

</form:form>

<div id="continueDiv">
	<table width="100%" border="0" cellspacing="0">
	<tr>
		<td align="right"> 
		<br>
		<div class="button">
			<a href="#" id="continueBtn">Next</a>
		</div> 
		</td>
	</tr>
	</table>
</div>
<br/>

<script type="text/javascript">
	function checkExistingFsaRouter(){
		var routerGrp = $("#selectedFsaRouterGrp").val();
		var meshRouterGrp = $("#selectedFsaMeshRouterGrp").val();
		var isImsVasEyeRentalRouter = $("#isImsVasEyeRentalRouter").val();
		if(isRentalRouter(routerGrp, meshRouterGrp, isImsVasEyeRentalRouter)){
			$("#rentalRouter").attr('disabled', false);
			$("#BRM").attr('disabled', false);
			if($("[name=rentalRouterInd]:checked").val() == undefined){
				$("#rentalRouter").attr('checked', true);
		    	$("#rentalRouterAlertMsgDiv").show();
			}
		}else{
			$("#rentalRouter").attr('disabled', true).attr('checked', false);
			$("#BRM").attr('disabled', true).attr('checked', false);
		}
		$("#rentalRouterDiv").show();
	}
	
	function isRentalRouter(routerGrp, meshRouterGrp, isImsVasEyeRentalRouter){
		var rentalRouterGrps = ['4', '5', '6', '7', '9'];
		
		if(jQuery.inArray( routerGrp, rentalRouterGrps ) != -1&& isImsVasEyeRentalRouter != 'true'){
			return true;
		}
		
		if(meshRouterGrp == 'true' && isImsVasEyeRentalRouter != 'true'){
			return true;
		}
		
		return false;
	}

	function submitForm() {
		$('#ltsModemArrangementForm').submit();
	}
	
	$(document).ready(function() {
		checkExistingFsaRouter();
		$('#continueBtn').click(function (event) {
			event.preventDefault();
			submitForm();
		});

		$("[name=rentalRouterInd]").change(function(event){
		    if($("[name=rentalRouterInd]:checked").val() == "SHARE_RENTAL_ROUTER"){
		    	//$("#lostModemCheckbox").attr("disabled", true).attr("checked", false);
		    	$("#rentalRouterAlertMsgDiv").show();
		    }else if($("[name=rentalRouterInd]:checked").val() == "BRM"){
		    	//$("#lostModemCheckbox").attr("disabled", false);
		    	$("#rentalRouterAlertMsgDiv").hide();
		    }
		 });
		
	});
	
	
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>