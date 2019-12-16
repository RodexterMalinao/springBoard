<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript"
	src="js/web/lts/acq/ltsacqaddressrollout.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script
	src="js/jquery.lts.autocomplete.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="1" />
</jsp:include>
<div class="cosContent">

	<form:form method="POST" id="ltsAddressRolloutForm"
		name="ltsAddressRolloutForm" commandName="ltsAcqAddressRolloutCmd"
		autocomplete="off">
		<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
		<form:hidden path="serviceBoundary" />
		<form:hidden path="streetCatgCode" />
		<form:hidden path="lotHouseInd" />
		<form:hidden path="ponSbRequired" />
		<table class="paper_w2 round_10" width="100%" align="center">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="6"
						class="contenttext">
						<tr>
							<td colspan="2" width="100%">
								<div id="s_line_text"><spring:message code="lts.acq.address.addressRollout" htmlEscape="false"/></a></div>
								<div id="erIaDiv"width: 100%;">
									<table width="100%" border="0" cellpadding="1" cellspacing="1"
										class="contenttext">
										<tr>
											<td width="2%">&nbsp;</td>
											<td width="35%"><b><spring:message code="lts.acq.address.enterInstallAddress" htmlEscape="false"/></b></td>
											<td align="left"><form:errors path="quickSearch"
													cssClass="error" /></td>
										</tr>
									</table>
									<table width="100%" border="0" cellpadding="1" cellspacing="1"
										class="contenttext">
										<tr>
											<td width="14%">&nbsp;</td>
											<td width="15%">&nbsp;</td>
											<td width="15%">&nbsp;</td>
											<td width="15%">&nbsp;</td>
											<td width="21%">&nbsp;</td>
											<td width="21%">&nbsp;</td>
										</tr>
										<tr height="15px"></tr>
										<tr>
											<td align="right"><spring:message code="lts.acq.address.key" htmlEscape="false"/> :</td>
											<td align="left"><select id="areaCdSelect"
												style="width: 150px;"></select></td>
											<td align="left"><select id='distNoSelect'
												style="width: 150px;"></select></td>

											<td align="left"><select id='sectCdSelect'
												style="width: 150px;"></select></td>
										</tr>
									</table>
									<table width="100%" border="0" cellpadding="1" cellspacing="1"
										class="contenttext">
										<tr>
											<td colspan="2">&nbsp;</td>
										</tr>

										<tr>
											<td align="right" width="14%"><span
												class="contenttext_red">*</span><spring:message code="lts.acq.address.quickSearch" htmlEscape="false"/> :</td>
											<td align="left"><form:input path="quickSearch"
													size="80" /> &nbsp; <input name="clearInputAddress"
												type="button" value="<spring:message code="lts.acq.address.clear" htmlEscape="false"/>"></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><spring:message code="lts.acq.address.inputBuilding" htmlEscape="false"/></td>
										</tr>
									</table>
									<table width="100%" border="0" cellpadding="1" cellspacing="1"
										class="contenttext">
										<tr>
											<td width="14%">&nbsp;</td>
											<td>&nbsp;</td>
											<td width="15%">&nbsp;</td>
											<td>&nbsp;</td>
											<td width="19%">&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td align="right"><spring:message code="lts.acq.address.flat" htmlEscape="false"/> :</td>
											<td><form:input maxlength="5" path="flat"
													cssClass="addressInput" /></td>
											<td align="right"><spring:message code="lts.acq.address.floor" htmlEscape="false"/> :</td>
											<td colspan="3"><form:input maxlength="3" path="floor"
													cssClass="addressInput" /> <form:errors path="floor"
													cssClass="error" /></td>
										</tr>
										<tr>
											<td align="right"><spring:message code="lts.acq.address.lotNo" htmlEscape="false"/> :</td>
											<td><form:input maxlength="8" path="lotNum"
													readonly="true" /></td>
											<td align="right"><spring:message code="lts.acq.address.building" htmlEscape="false"/> :</td>
											<td colspan="3"><form:input maxlength="50" size="50"
													path="buildingName" readonly="true" /></td>
										</tr>
										<tr>
											<td align="right"><spring:message code="lts.acq.address.streetNo" htmlEscape="false"/> :</td>
											<td><form:input maxlength="11" path="streetNum"
													readonly="true" /></td>
											<td align="right"><spring:message code="lts.acq.address.streetName" htmlEscape="false"/> :</td>
											<td><form:input maxlength="23" path="streetName"
													readonly="true" /></td>
											<td align="right"><spring:message code="lts.acq.address.streetCategory" htmlEscape="false"/> :</td>
											<td><form:input path="streetCatgDesc" readonly="true" />
											</td>
										</tr>
										<tr>
											<td align="right"><spring:message code="lts.acq.address.section" htmlEscape="false"/> :</td>
											<td><form:hidden path="sectionCode" /> <form:input
													path="sectionDesc" readonly="true" /></td>
											<td align="right"><spring:message code="lts.acq.address.district" htmlEscape="false"/> :</td>
											<td><form:hidden path="districtCode" /> <form:input
													path="districtDesc" readonly="true" /></td>
											<td align="right"><spring:message code="lts.acq.address.area" htmlEscape="false"/> :</td>
											<td><form:hidden path="areaCode" /> <form:input
													path="areaDesc" readonly="true" /></td>
										</tr>
										<tr>
											<td colspan="6">&nbsp;</td>
										</tr>
										<!-- <tr>
									<td align="right">
										BA & CA<span class="contenttext_red">*</span> :
									</td>
									<td colspan="5">
										<form:radiobutton path="baCaAction" value="SAME_AS_NEW_IA"/> Same as new I/A
										&nbsp;
										<form:radiobutton path="baCaAction" value="REMAIN_UNCHANGE"/> Remain unchange
										&nbsp;
										<form:radiobutton path="baCaAction" value="DIFFERENT_FROM_NEW_OLD_IA"/> Different from new/old I/A
										<br/>
										<form:errors path="baCaAction" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td align="right">
									</td>
									<td colspan="5">
										<form:checkbox id="instantUpdateBa" path="instantUpdateBa"/> Instant Update
									</td>
								</tr>
								<tr>
									<td colspan="6">&nbsp;</td>
								</tr> -->
									</table>
									<div id="erBaDiv" style="display: none; width: 100%;">
										<table width="100%" border="0" cellpadding="1" cellspacing="1"
											class="contenttext">
											<tr>
												<td width="2%">&nbsp;</td>
												<td width="35%"><b><spring:message code="lts.acq.address.enterBillAddress" htmlEscape="false"/> </b></td>
												<td align="left"><form:errors path="baQuickSearch"
														cssClass="error" /></td>
											</tr>
										</table>
										<table width="100%" border="0" cellpadding="1" cellspacing="1"
											class="contenttext">
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												<td align="right" width="15%"><span
													class="contenttext_red">*</span><spring:message code="lts.acq.address.quickSearch" htmlEscape="false"/> :</td>
												<td align="left"><form:input path="baQuickSearch"
														size="80" /> &nbsp; <input name="clearBaInputAddress"
													type="button" value="Clear"></td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td><spring:message code="lts.acq.address.inputBuildingOfBillAddr" htmlEscape="false"/></td>
											</tr>
											<tr>
												<td colspan="2">&nbsp;</td>
											</tr>
											<tr>
												<td align="right" valign="top" width="15%"><span
													class="contenttext_red">*</span><spring:message code="lts.acq.address.billingAddress" htmlEscape="false"/> :&nbsp;</td>
												<td>
													<div style="float: left">
														<form:textarea id="billingAddressTextArea"
															path="billingAddress" rows="6" cols="40"
															cssStyle="resize: none;" />
													</div>
													<div style="width: 30%; float: left; padding-left: 10px;">
														<form:errors path="billingAddress" cssClass="error" />
													</div>
												</td>
											</tr>
										</table>

									</div>
								</div>

								<div id="rolloutErrorDiv" style="display: none;">
									<br>
									<table width="90%" border="0">
										<tr>
											<td width="10%">&nbsp;</td>
											<td width="80%" align="left">
												<div id="rollout_Error_Msg_div" class="ui-widget"
													style="visibility: visible;">
													<div class="ui-state-error ui-corner-all"
														style="margin-top: 20px; padding: 0 .7em;">
														<p>
															<span class="ui-icon ui-icon-alert"
																style="float: left; margin-right: .3em;"></span>
														</p>
														<div class="contenttext">
															<span id="rolloutErrorMsg"></span>
														</div>
														<p></p>
													</div>
												</div>
											</td>
											<td width="10%">&nbsp;</td>
										</tr>
									</table>
								</div>
								<div id="rolloutDiv" style="display: none;">
									<br>
									<table width="90%" border="0">
										<tr>
											<td width="10%">&nbsp;</td>
											<td width="90%" align="center">
												<div id="warning_addr" class="ui-widget"
													style="visibility: visible;">
													<div class="ui-state-highlight ui-corner-all"
														style="margin-top: 20px; padding: 0 .7em;">
														<p>
															<span class="ui-icon ui-icon-info"
																style="float: left; margin-right: .3em;"></span>
														</p>
														<div id="warning_addr_msg" class="contenttext">
															<table width="100%" cellspacing="3" cellpadding="3"
																border="0">
																<tr>
																	<td width="40%" align="right"><spring:message code="lts.acq.address.ltsHousingType" htmlEscape="false"/> :</td>
																	<td width="60%" align="left"><span id="rolloutLtsHousingCat"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr id="rolloutPhRow">
																	<td align="right"><spring:message code="lts.acq.address.ptMarker" htmlEscape="false"/> :</td>
																	<td align="left"><span id="rolloutPh"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.eyeCoverage" htmlEscape="false"/> :</td>
																	<td align="left"><span id="rolloutEyeCoverage"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.peCoverage" htmlEscape="false"/> :</td>
																	<td align="left"><span id="rolloutPeCoverage"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.2NBuilding" htmlEscape="false"/> :</td>
																	<td align="left"><span id="rollout2nBuilding"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.bandwidthCoverage" htmlEscape="false"/> :</td>
																	<td align="left"><span
																		id="rolloutBandwidthCoverage"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.resourceShortage" htmlEscape="false"/> :</td>
																	<td align="left"><span id="rolloutRsourceShortage"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.LTSAddressProof" htmlEscape="false"/> :</td>
																	<td align="left"><span
																		id="rolloutLtsBlackListAddr"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.IMSAddressProof" htmlEscape="false"/> :</td>
																	<td align="left"><span
																		id="rolloutImsBlackListAddr"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.fieldWorkPermit" htmlEscape="false"/> :</td>
																	<td align="left"><span id="rolloutFieldPermit"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.FTTC" htmlEscape="false"/> :</td>
																	<td align="left"><span id="rolloutFttc"
																		style="font-weight: bold;"></span></td>
																</tr>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.fiberBlockage" htmlEscape="false"/> :</td>
																	<td align="left"><span id="rolloutFiberBlockage"
																		style="font-weight: bold;"></span></td>
																</tr>
																<c:if test="${!isChannelDS}">
																	<tr id="rolloutSuggestSrdReasonRow">
																		<td align="right"><spring:message code="lts.acq.address.suggestedEarliestSRD" htmlEscape="false"/> :</td>
																		<td align="left"><span
																			id="rolloutSuggestSrdReason"
																			style="font-weight: bold;"></span></td>
																	</tr>
																</c:if>
																<tr>
																	<td align="right"><spring:message code="lts.acq.address.numOfEye" htmlEscape="false"/> :</td>
																	<td align="left"><span id="rolloutNumOfEye"
																		style="font-weight: bold;"></span></td>
																</tr>
															</table>
														</div>
														<p></p>
													</div>
												</div>
											</td>
										</tr>
									</table>
								</div>
								<div id="rolloutBlockageDiv" class="ui-widget" style="display: none; visibility: visible;">
									<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
										<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
										<div id="rolloutUimBlockage" "class="contenttext" style="font-weight:bold; margin-left: 1.5em; text-align: left;">
										</div>
										
										<p></p>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form:form>
</div>
<c:if test="${showDrgDownTimeMsg}">
	<div id="warning_drgDownTime" class="ui-widget"
		style="visibility: visible;">
		<div class="ui-state-highlight ui-corner-all"
			style="padding: 0 .7em; margin-left: 25%; width: 50%;">
			<p>
				<span class="ui-icon ui-icon-info"
					style="float: left; margin-right: .3em;"></span>
			</p>
			<div id="drgDownTimeMsg" class="contenttext">
				<spring:message code="lts.acq.address.DRGDowntime" htmlEscape="false"/>:<br />
				<ul>
					<li><spring:message code="lts.acq.address.notAllowSignOff" htmlEscape="false"/></li>
					<li><spring:message code="lts.acq.address.salesNeedRecall" htmlEscape="false"/></li>
				</ul>
			</div>
			<p></p>
		</div>
	</div>
</c:if>

<c:if test="${ not empty requestScope.errorMsgList }">
	<div id="errorMsgDiv">
		<br>
		<table width="90%" border="0">
			<tr>
				<td width="10%">&nbsp;</td>
				<td width="80%" align="left">
					<div id="error_Msg_div" class="ui-widget"
						style="visibility: visible;">
						<div class="ui-state-error ui-corner-all"
							style="margin-top: 20px; padding: 0 .7em;">
							<c:forEach items="${requestScope.errorMsgList }" var="errorMsg">
								<p>
									<span class="ui-icon ui-icon-alert"
										style="float: left; margin-right: .3em;"></span>
								</p>
								<div class="contenttext">
									<c:out value="${errorMsg}"></c:out>
								</div>
								<p></p>
							</c:forEach>
						</div>
					</div>
				</td>
				<td width="10%">&nbsp;</td>
			</tr>
		</table>
	</div>
</c:if>

<c:if test="${notAllowContinue}">
	<div id="error_profile" class="ui-widget"
		style="visibility: visible; padding-left: 25%; width: 50%;">
		<div class="ui-state-error ui-corner-all"
			style="margin-top: 1px; padding: 0 .7em;">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin-right: .3em;"></span>
			</p>
			<div id="cancel_msg" class="contenttext">
				<b>${notAllowContinueMsg}</b>
			</div>
			<p></p>
		</div>
	</div>
</c:if>
<br>
<c:if test="${!notAllowContinue}">
	<div id="continueDiv">
		<table width="100%" border="0" cellspacing="0">
			<tr>
				<td align="right"><br> <a class="nextbutton" id="submit"
					href="#"><div class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a></td>
			</tr>
		</table>
	</div>
</c:if>

<script type="text/javascript">
    var staffId = "${sessionScope.bomsalesuser.username}";
    var isDs = "${sessionScope.bomsalesuser.channelId}" == "12" || "${sessionScope.bomsalesuser.channelId}" == "13" ;
    var sessionTimedOutMsg = '<spring:message code="lts.acq.address.sessionTimedOut" htmlEscape="false"/>';
    var notFoundAddressMsg = '<spring:message code="lts.acq.address.notFoundAddress" htmlEscape="false"/>';
    var flatEmptyMsg = '<spring:message code="lts.acq.address.flatEmpty" htmlEscape="false"/>';
    
	$(ltsacqaddressrollout.actionPerform);		
</script>

<script type="text/javascript" charset="utf-8">
	var areaSearchInput = "";
	var districtSearchInput = "";
	var sectionSearchInput = "";
	var searchInput = "";
	
	String.prototype.trim = function() {
	    return this.replace(/^\s+|\s+$/g,"");
	};
	String.prototype.ltrim = function() {
	    return this.replace(/^\s+/,"");
	};
	String.prototype.rtrim = function() {
	    return this.replace(/\s+$/,"");
	};
	
	
	$(document).ready(function() {			


		function loadDistrict(parentid) {
			$.ajax({
				url : 'imsaddressdistrictlookup.html?T=DISTRICT&ID=' + parentid,
				type : 'POST',
				dataType : 'json',
				timeout : 25000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				     if(textStatus=='parsererror'){
						alert("Your session has been timed out, please re-login."); 
				     } else {
				    	 alert("Error loading district data!");
				     }
				},
				success : function(msg) {
					$("#distNoSelect").empty();
					$.each(eval(msg), function(i, item) {
						if (item.id == "${installAddress.distNo}") {
							$("<option value='" + item.id + "' selected='selected'>"
							+ item.name + "</option>").appendTo($("#distNoSelect"));
						} else {
							$("<option value='" + item.id + "' >"
							+ item.name + "</option>").appendTo($("#distNoSelect"));
						}
					});
					$('#distNoSelect').trigger("change");
				}
			});
		}

		function loadSection(parentid) {
			var varual = '';
			varual = 'imsaddressdistrictlookup.html?T=SECTION&ID=' + parentid + '&SECTIONALL=false';
			$.ajax({
				url : varual,
				type : 'POST',
				dataType : 'json',
				timeout : 25000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				     if(textStatus=='parsererror'){
						alert("Your session has been timed out, please re-login."); 
				     } else {
				    	alert("Error loading section data!");
				     }
				},
				success : function(msg) {
					$("#sectCdSelect").empty();
					$.each(eval(msg), function(i, item) {
						if (item.id == "${installAddress.sectCd}") {
							$("<option value='" + item.id + "' selected='selected'>"
							+ item.name + "</option>").appendTo($("#sectCdSelect"));
						} else {
							$("<option value='" + item.id + "' >"
							+ item.name + "</option>").appendTo($("#sectCdSelect"));
						}
					});
					$('#sectCdSelect').trigger("change");
				},
				complete : function() {
// 					$("#loaderImagePlaceholder").empty();
				},
				beforeSend : function() {
// 					$("#loaderImagePlaceholder").empty().html(
// 						"<font color='red'>Please wait... </font>");
				}
			});
		}
		
		$("#areaCdSelect").change(
			function() {
				if ($("#areaCdSelect").val() == "") {
					//alert("area empty");
					$("#distNoSelect").empty();
					$("#sectCdSelect").empty();
					areaSearchInput = " ";
					districtSearchInput = " ";
					sectionSearchInput = " ";
				} else {
					loadDistrict($("#areaCdSelect").val());
					$("#installAddress.areaCd").val($("#areaCdSelect").val());
					$("#installAddress.areaDesc").val($("#areaCdSelect option:selected").text());
					areaSearchInput = $("#areaCdSelect option:selected").text() + " ";
					$('input[name="quickSearch"]').val(areaSearchInput);
				}
		});

		$("#distNoSelect").change(
			function() {
				sectionSearchInput = " ";
				if (($("#distNoSelect").val()) == "") {
					//alert("district empty");
					$("#sectCdSelect").empty();
					districtSearchInput = " ";
					sectionSearchInput = " ";
				} else {
					loadSection($("#distNoSelect").val());
					$("#installAddress.distNo").val($("#distNoSelect").val());
					$("#installAddress.distDesc").val($("#distNoSelect option:selected").text());
					districtSearchInput = $("#distNoSelect option:selected").text() + " ";
					searchInput = districtSearchInput + areaSearchInput;
					$('input[name="quickSearch"]').val(searchInput);
					$('input[name="quickSearch"]').trigger("focus");
					$('input[name="quickSearch"]').trigger("click");
				}
			});

		$("#sectCdSelect").change(
			function() {
				$("#installAddress.sectCd").val($("#sectCdSelect").val());
				$("#installAddress.sectDesc").val($("#sectCdSelect option:selected").text());
				sectionSearchInput = $("#sectCdSelect option:selected").text() + " ";
				searchInput = sectionSearchInput + districtSearchInput + areaSearchInput;
				$('input[name="quickSearch"]').val(searchInput);
				$('input[name="quickSearch"]').trigger("focus");
				$('input[name="quickSearch"]').trigger("click");				
		});
		
		
	});
	
	// END AJAX
	




	//STATR AJAX
	$(document).ready(function() {
//	$(function() {
		$.ajax({
			url : "imsaddressdistrictlookup.html?T=AREA",
			type : 'POST',
			dataType : 'json',
			timeout : 25000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			     if(textStatus=='parsererror'){
					alert("Your session has been timed out, please re-login."); 
			     } else {
			    	alert("Error loading area data!");
			     }
			},
			success : function(msg) {
				$("#areaCdSelect").empty();
				$.each(eval(msg), function(i, item) {
					if (item.id == "${installAddress.areaCd}") {
						$("<option value='" + item.id + "' selected='selected'>"
						+ item.name + "</option>").appendTo($("#areaCdSelect"));
					} else {
						$("<option value='" + item.id + "' >" 
						+ item.name	+ "</option>").appendTo($("#areaCdSelect"));
					}
				});

				$('#areaCdSelect').trigger("change"); //for assign value
			}
		});
		

		
	});
	//END AJAX

</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>