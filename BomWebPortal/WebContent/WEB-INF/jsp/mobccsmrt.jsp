<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.min.css"
      rel="stylesheet" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="js/json2.min.js"></script>

<style>
.ui-widget {
  font-size: 0.8em;
}
</style>

<pccw-util:codelookup var="mipEnableMNPTicketMap" codeType="MIP_SHOW_MNP_TICKET_BTN"  asEntryList="true" />
<c:set var="mipEnableMNPTicket" value="${mipEnableMNPTicketMap[0].key}" />

<script type="text/javascript">
var basket = { customerTierId: "<c:out value="${sessionCcsOrderHash.basket.customerTierId}"/>" };
var authorizeType = "";
	$(function() {
		var ocid ='<c:out value="${orderDto.ocid}"/>';
		var enable= '<c:out value="${enableCsl}"/>';
		if ("Y"==enable){
			
			cslDivChange();
			$("#mnpMsisdn").focusout(function(){
				cslDivChange();
			});
			$("#origActDateStrClear").click(function(){
				$("#origActDateDatepicker").val('');
			});
			
			$('#origActDateDatepicker').attr('readonly', true);
			if ( ocid.length <= 0){
			$('#origActDateDatepicker').datepicker({
				changeMonth : true,
				changeYear : true,
				showButtonPanel : true,
				dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
				maxDate : "+0m +0w -1d" //Y is year, M is month, D is day  
			});
			}
		}
		
		var calculatedCutOverDateEnd;
		if ('${mobccsmrt.mnpInd}' == 'A' ){
			$("#mnpExtendSpan").hide();
			calculatedCutOverDateEnd = '<c:out value="${activeFurtherCutOverDateExtendedEnd}"/>';
		}else if ('${mobccsmrt.mnpExtendAuthInd}' == 'Y'){
			calculatedCutOverDateEnd = '<c:out value="${activeCutOverDateExtendedEnd}"/>';
		}else {
			calculatedCutOverDateEnd = '<c:out value="${activeCutOverDateEnd}"/>';
		}
		
	
		$('#cutOverDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', 
			minDate : $.datepicker.parseDate( 'dd/mm/yy', '${activeCutOverDateStart}'),
			maxDate : $.datepicker.parseDate( 'dd/mm/yy', calculatedCutOverDateEnd),  
			yearRange : "0:+2"
		
		});
	
		$('#serviceReqDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', 
			minDate : $.datepicker.parseDate( 'dd/mm/yy', '${activeSrvReqDateStart}'),
			maxDate : $.datepicker.parseDate( 'dd/mm/yy', '${activeSrvReqDateEnd}'),  
			yearRange : "0:+1",
			onSelect: function(dateText, inst) {
				if ($('#Amnp').is(':checked')) {
					$('#cutOverDateDatepicker').datepicker('enable');
					
					var cutDateStart = $(this).datepicker("getDate");
					cutDateStart.setDate(cutDateStart.getDate());
					$('#cutOverDateDatepicker').datepicker( "option", "minDate", cutDateStart);
					
					/*if ($('#Amnp').is(':checked')) {
						var cutDateEnd = $.datepicker.parseDate("dd/mm/yy", $('#serviceReqDateDatepicker').val());
					} else {
						var cutDateEnd = $.datepicker.parseDate("dd/mm/yy", '${appDate}');
					}*/
					//var cutDateEnd = $.datepicker.parseDate("dd/mm/yy", '${appDate}');
					var cutDateEnd = $(this).datepicker("getDate");
					var ccsFurtherMnpMaxDate = ${ccsFurtherMnpMaxDate};
					cutDateEnd.setDate(cutDateEnd.getDate() + ccsFurtherMnpMaxDate);
					$('#cutOverDateDatepicker').datepicker( "option", "maxDate", cutDateEnd);	
				}
			}
	
		});
		
	
		if ($('#mnpExtendAuthorizeButton').length) {
			$("#mnpExtendAuthorizeButton").click(function(e) {
				e.preventDefault();
				authorizeType = "AU19";
				
				var orderID = "<c:out value='${orderDTO.orderId}'/>";
				var sURL;
				if(orderID != null && orderID !="")	{
					sURL = "mobccsauthorize.html?" + $.param({"action": "AU19", "orderId": orderID});
				} else {
					sURL= "mobccsauthorize.html?" + $.param({"action": "AU19"});
				}
				
				$('#authDialog').html('<iframe style="border: 0px; " src="' + sURL + '" width="100%" height="100%"></iframe>')
		        .dialog({
		            autoOpen: false,
		            modal: true,
		            height: 300,
		            width: 500,
		            title: "Authorize"
		        });
				$('#authDialog').dialog('open');
				return false;
			});
		}
		
		$("#goldenNumAuthorizeButton").click(function(e) {
			e.preventDefault();
			authorizeType = "AU21";
			var sURL = "mobccsauthorize.html?" + $.param({"action": "AU21"});
			$('#authDialog').html('<iframe style="border: 0px; " src="' + sURL + '" width="100%" height="100%"></iframe>')
	        .dialog({
	            autoOpen: false,
	            modal: true,
	            height: 300,
	            width: 500,
	            title: "Authorize"
	        });
			$('#authDialog').dialog('open');
			return false;
		});
		
	});
	
	function show(which) {
		
		$('#cutOverDateDatepicker').datepicker( "option", "minDate", '${activeCutOverDateStart}');
		
		//DENNIS201606
		if (which == 'A'){
			$("#mnpExtendSpan").hide();
		}else if ($("input[name=mnpExtendAuthInd]").val() == "Y"){
			$("#mnpExtendSpan").show();
			$('#cutOverDateDatepicker').datepicker( "option", "maxDate", '${activeCutOverDateExtendedEnd}');
		}else{
			$("#mnpExtendSpan").show();
			$('#cutOverDateDatepicker').datepicker( "option", "maxDate", '${activeCutOverDateEnd}');
		}
		
		
		$('#cutOverDateDatepicker').datepicker('enable');
		
		if (which == 'O') {
			
			document.getElementById('divNewMob').style.visibility = 'visible';
			document.getElementById('divMNP').style.visibility = 'visible';
			document.getElementById('divUnicom').style.visibility = 'hidden';

			unicomDivControl('disable');
			
			if ('${quota}' == 'Y' || '${basketTypeId}' == '6') {
				$('#mobcheckbox').show();
				$('#mnpcheckbox').hide();
				showMobMnp('quota');
			} else {
				$('#mobcheckbox').show();
				$('#mnpcheckbox').show();
				showMobMnp('mob');
			}
			
			document.getElementById('goldenNumSearchBtn').style.visibility = 'visible';
		
		}
		if (which == 'C') {
			
			document.getElementById('divNewMob').style.visibility = 'visible';
			document.getElementById('divMNP').style.visibility = 'visible';
			document.getElementById('divUnicom').style.visibility = 'visible';

			$('#mobcheckbox').show();
			$('#mnpcheckbox').show();

			showMobMnp('mob');
			
			unicomDivControl('enable');
			
			document.getElementById('goldenNumSearchBtn').style.visibility = 'visible';
		}
		if (which == 'A') {
						
			document.getElementById('divNewMob').style.visibility = 'visible';
			document.getElementById('divMNP').style.visibility = 'visible';
			document.getElementById('divUnicom').style.visibility = 'hidden';

			unicomDivControl('disable');

			mnpDivControl('enable');
			mobDivControl('enable');
			
			$('#mobcheckbox').hide();
			$('#mnpcheckbox').hide();
			
			if (($('#serviceReqDateDatepicker').val() == null || $('#serviceReqDateDatepicker').val() == '') && $('#Amnp').is(':checked')) {
				$('#cutOverDateDatepicker').datepicker('disable');
			} else {
				var cutDateStart = $.datepicker.parseDate("dd/mm/yy", $('#serviceReqDateDatepicker').val());
				cutDateStart.setDate(cutDateStart.getDate());
				$('#cutOverDateDatepicker').datepicker( "option", "minDate", cutDateStart);
									
				var cutDateEnd = $.datepicker.parseDate("dd/mm/yy", $('#serviceReqDateDatepicker').val());
				var ccsFurtherMnpMaxDate = ${ccsFurtherMnpMaxDate};
				cutDateEnd.setDate(cutDateEnd.getDate() + ccsFurtherMnpMaxDate);
				$('#cutOverDateDatepicker').datepicker( "option", "maxDate", cutDateEnd);
				
				$('#cutOverDateDatepicker').datepicker('enable');
			}
						
			document.getElementById('goldenNumSearchBtn').style.visibility = 'hidden';
			$('#specialNumSearchBtn').hide();
			
			
		}
	}

	function mobDivControl(which) {
		
		if (which == 'disable') {
			
			$('#mobMsisdn').attr('disabled', true);
			$('#newNumSearchBtn').attr('disabled', true);
			$('#goldenNumSearchBtn').attr('disabled', true);
			$('#specialNumSearchBtn').attr('disabled', true);
			$('#msisdnLvl').attr('disabled', true);
			$('#serviceReqDateDatepicker').attr('disabled', true);

		} else if (which == 'enable') {
			
			$('#mobMsisdn').attr('disabled', false);
			$('#newNumSearchBtn').attr('disabled', false);
			$('#goldenNumSearchBtn').attr('disabled', false);
			$('#specialNumSearchBtn').show();
			$('#specialNumSearchBtn').attr('disabled', false);
			$('#msisdnLvl').attr('disabled', false);
			$('#serviceReqDateDatepicker').attr('disabled', false);
		}
	}

	function mnpDivControl(which) {
		
		if (which == 'disable') {
			$('#mnpMsisdn').attr('disabled', true);
			$('#mnpTicketNum').attr('disabled', true);
			$('#cutOverDateDatepicker').attr('disabled', true);
			$('#cutOverTime').attr('disabled', true);
			$('#custName').attr('disabled', true);
			$('#docNum').attr('disabled', true);
			$('#prepaySimCust').attr('checked', false);
			$('#prepaySimCust').attr('disabled', true);

		} else if (which == 'enable') {

			$('#mnpMsisdn').attr('disabled', false);
			$('#mnpTicketNum').attr('disabled', false);
			$('#cutOverDateDatepicker').attr('disabled', false);
			$('#cutOverTime').attr('disabled', false);
			$('#custName').attr('disabled', false);
			$('#docNum').attr('disabled', false);
			$('#prepaySimCust').attr('disabled', false);
		}
	}

	function unicomDivControl(which) {

		if (which == 'disable') {

			$('#cityCd').attr('disabled', true);
			$('#unicomSearchBtn').attr('disabled', true);
			$('#unicomMobMsisdn').attr('disabled', true);
			$('#unicomMobGrade').attr('disabled', true);

		} else if (which == 'enable') {
			
			$('#cityCd').attr('disabled', false);
			$('#unicomSearchBtn').attr('disabled', false);
			$('#unicomMobMsisdn').attr('disabled', false);
			$('#unicomMobGrade').attr('disabled', false);
		}
	}

	function showMobMnp(which) {
		
		if ('mob' == which) {
			$('#mobcheckbox').attr('checked', true);
			$('#mnpcheckbox').attr('checked', false);
			mobDivControl('enable');
			mnpDivControl('disable');
		} else if ('mnp' == which) {
			$('#mobcheckbox').attr('checked', false);
			$('#mnpcheckbox').attr('checked', true);
			mobDivControl('disable');
			mnpDivControl('enable');
		} else if ('quota' == which) {
			$('#mobcheckbox').attr('checked', true);
			$('#mnpcheckbox').attr('checked', false);
			mobDivControl('enable');
			mnpDivControl('disable');
			$('#mnpcheckbox').hide();
		}
	}
	
function checkBasketCustomerTierMnp() {
	// only check for mnp tier basket
	if (basket.customerTierId != "30") {
		return true;
	}
	// MNP only
	if ($("input[name=mrtInd]:checked").val() == "O" && $("input[name=mnpInd]:checked").val() == "Y") {
		return true;
	}
	// New # + MNP
	if ($("input[name=mrtInd]:checked").val() == "A") {
		return true;
	}
	return false;
}	

	function formSubmit() {
		// requires MNP tierwhen number type = MNP / New Number + MNP
		$(".customerTierCheck").hide();
		if (!checkBasketCustomerTierMnp()) {
			$(".basketCustomerTierMnp").show();
			return;
		}
		
		var selected = $("input[name='mrtInd']:checked").val();
		
		if(selected == 'A' || (selected == 'O' && $('#mnpInd').val() == 'Y')){
			$.ajax({
				url : 'mobccsmrtajax.html',
				type : 'POST',
				cache : false,
				data : {
					type : 'mnpOrderExistCancellingStatus',
					msisdn : $('#mnpMsisdn').val(),
					orderStatus: $('#orderStatus').val(),
					orderId: $('#orderIdSession').val()
				},
				dataType : 'JSON',
				timeout : 50000,
				error : function() {
					alert('Error validator SB order exists with cancelling status!');
				},
				success : function(msg) {
					$.each(eval(msg), function(index, item) {
						if (item.orderIdExist == 'no') {
							document.mobCcsMRTForm.submit();							
						} else if (item.orderIdExist == 'yes') {
							alert('SB order exists with Cancelling status, order ID =' + item.orderIdList);
							//document.mobCcsMRTForm.submit();
							$("#mobCcsMRTForm").submit();
						}
					});
				}
			});
		}else{
			//document.mobCcsMRTForm.submit();
			$("#mobCcsMRTForm").submit();
		}
		
	}
	
	$(document).ready(function() {
		if ($('#Amnp').is(':checked') || $('#Omnp').is(':checked')) {
			if ('${mobccsmrt.mnpInd}' == 'N' && ($('#Amnp').is(':not(:checked)') || $('#Amnp').length <= 0)) {
				if ('${quota}' == 'Y' || '${basketTypeId}' == '6') {
					showMobMnp('quota');
				} else {
					showMobMnp('mob');	
				}
			} else if ('${mobccsmrt.mnpInd}' == 'Y' && ($('#Amnp').is(':not(:checked)') || $('#Amnp').length <= 0)) {
				showMobMnp('mnp');
			} else if ($('#Amnp').is(':checked')) {	
				show('A');				
			}
			document.getElementById('divUnicom').style.visibility = 'hidden';
			unicomDivControl('disable');
		} else {
			document.getElementById('divUnicom').style.visibility = 'visible';
			unicomDivControl('enable');
			if ('${mobccsmrt.mnpInd}' == 'N') {
				if ('${quota}' == 'Y' || '${basketTypeId}' == '6') {
					showMobMnp('quota');
				} else {
					showMobMnp('mob');	
				}
			} else if ('${mobccsmrt.mnpInd}' == 'Y') {
				showMobMnp('mnp');
			}
		}
		
		if ($('#custName').val() == 'Prepaid Sim') {
			$('#prepaySimCust').attr('checked', true);
			prepaySimClick();
		} else {
			$('#prepaySimCust').attr('checked', false);
			$('#divOldId').show();
			$('#divPrepaidSim').hide();
		}

	});

	function prepaySimClick() {
		if ($('#prepaySimCust').is(":checked")) {
			$('#custName').val("Prepaid Sim");
			$('#custName').attr('readonly', true);

			if ($('#docNum').val().length > 20) {
				var temp = $('#docNum').val();
				$('#docNum').val(temp.substr(0, 20));
			}
			$('#docNum').attr("maxlength", 20);

			$('#divOldId').hide();
			$('#divPrepaidSim').show();

		} else {
			$('#custName').val("");
			$('#custName').attr('readonly', false);
			$('#docNum').attr("maxlength", 20);

			$('#divOldId').show();
			$('#divPrepaidSim').hide();
		}
	}
	
	function popUpMRTNumSelection(url){
		window.open(url, 'NewMRT', 'height=500,width=700,resizable=yes,scrollbars=yes');
	}

	function mobOnChange(url) {
		
		var retVal = confirm("Mobile number change will be save after continue to next page.");
		if (retVal) {
			popUpMRTNumSelection(url);
		}
	}
	
	function cslDivChange(){
		$.ajax({
			url : "mrtajax.html?msisdn="+$("#mnpMsisdn").val(),
			cache: false,
			dataType : 'json',
			timeout : 5000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert('Error when calling mnp check, please re-try! [MNP-1]');
				alert(XMLHttpRequest.status);
	            alert(errorThrown);			            
			},
			success : function(dno) {
				//alert('dno:'+dno);
				var cslDnoString='<c:out value="${cslDnoString}"/>';
				if('ERROR'==dno){
					//do nothing
				}else{
					if (cslDnoString.indexOf(dno)>=0 ){
						 if ( $("#origActDateDatepicker").val().length <= 0){
							alert("CSL mobile number found and please input the \"Original Activation Date\"");
						}
						$("#clsDnoDiv").show();
					}else{
						$("#origActDateDatepicker").val("");
						$("#clsDnoDiv").hide();
					}
				}
			}
			
		});
		
	} 
	
	function authorized() {
		$('#authDialog').dialog('close');
		if (authorizeType == 'AU19') {
			$("#mnpExtendNotAuthorizedDiv").hide();
			$("#mnpExtendAuthorizedDiv").show();		
			
			
			if (!$('#Amnp').is(':checked')){
				$('#cutOverDateDatepicker').datepicker( "option", "maxDate", "${activeCutOverDateExtendedEnd}");	
			}
			
			$("input[name=mnpExtendAuthInd]").val("Y");
		} else if ( authorizeType = 'AU21') {
			$("#goldenNumAuthorizeButton").hide();
			$("input[name=byPassGoldenNum]").val("Y");
			$("#goldenNumNotAuthDiv").hide();
			$("#goldenNumAuthDiv").show();	
		}
	}
	
</script>

<style type="text/css">
.customerTierCheck { background-color: rgb(255, 240, 248); padding: 1em 2em; font-size: small; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.customerTierCheck ul { margin-top: 0; margin-bottom: 0 }
.customerTierCheck .tier { font-weight: bold; text-decoration: underline }
.customerTierCheck .mnpType { text-decoration: underline }
</style>


<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobccsmrt" />
</jsp:include>

<div class="basketCustomerTierMnp customerTierCheck" style="display: none">
	Selected basket belongs to "MNP" tier. Mobile number type should be "MNP" or "New Num + MNP". Please correct or check the basket selection.
</div>

<form:form name="mobCcsMRTForm" method="POST" commandName="mobccsmrt" id="mobCcsMRTForm">
	<table width="100%" class="tablegrey" class="contenttext" background="images/background2.jpg">
		<tr>
			<td class="table_title">Mobile Number</td>
		</tr>		
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="0" class="contenttext">
					<tr>
							<td align="center">
								<label>
									<form:radiobutton path="mrtInd" value="O" onclick="javascript:show('O');" id="Omnp"/>
									<spring:message code="label.mrtInd.O"/>
								</label>
							</td>
							
							<td align="center">
								<c:if test="${(orderDTO==null || orderDTO.sboOrderInd =='N') and vasMrtSelectionSession == null}">	
								<label>
									<form:radiobutton path="mrtInd" value="A" onclick="javascript:show('A');" id="Amnp" />
									<spring:message code="label.mrtInd.A"/>
								</label>
								</c:if>
							</td>
					</tr>
					<tr>
						<td colspan="5">
							<table width="100%" border="0" cellspacing="0"
								class="contenttext">
								<tr>
									<td class="table_title" colspan="5">
										<spring:message code="label.mobccsmrt.header.hk"/>
									</td>
								</tr>
								<tr>
									<td colspan="5" size="100%"><form:errors path="mnpInd" cssClass="error" /></td>
								</tr>
								<tr>
									<td>
										<div id="divNewMob">
											<table width="100%" border="0" cellspacing="0"
												class="contenttext">
												<tr>
													<td colspan="4">	<!-- Golden Num VAS offer Authorization -->
											
														<c:if test='${mobccsmrt.showGoldenNumAuth eq "Y"}' >
														
															<c:if test='${ mobccsmrt.byPassGoldenNum eq "N"}'>
																<div id="goldenNumNotAuthDiv" style="color: #d63c00">
																	<spring:message code="label.goldenVasNotAuthorized"/><a href="#" id="goldenNumAuthorizeButton"
																		class="nextbutton3">Authorize</a>
																</div>
															</c:if>
														<%-- 	
															<c:if test='${ mobccsmrt.byPassGoldenNum eq "Y"}'>
																<div id="goldenNumAuthDiv" style="color: #d63c00">
																	<spring:message code="label.goldenVasAuthorized"/>
																</div>
															</c:if> --%>
															
														</c:if>
														<form:hidden path="byPassGoldenNum" id="byPassGoldenNum"/>		
														</td>
												</tr>
												
												<tr>
													<!--  <td><input type="checkbox"
														name="mobcheckbox" id="mobcheckbox"
														onclick="showMobMnp('mob')" style=/>New Mobile Number</td>-->
														
														<td>
															<label>
																<form:radiobutton path="mnpInd" value="N" id="mobcheckbox" onclick="showMobMnp('mob')" />
															</label>
															<spring:message code="label.mnpInd.N"/>
														</td>
												</tr>
												<tr>
													<td><spring:message code="label.mobMsisdn"/></td>
													<td><form:input path="mobMsisdn" readonly="true" /></td>
													<td>
														<c:url var="popupMrtSearchUrl" value="/mobccsmrtnumselection.html">
															<c:param name="mrtType" value="newMRT"/>
														</c:url>														
														<c:choose>
															<c:when test="${workStatus == 'recall'}">																													
																<input type="button" name="newNumSearchBtn" id="newNumSearchBtn"
																	onClick="mobOnChange('${popupMrtSearchUrl}')" value="New Number Search"/>																
															</c:when>
															<c:when test="${empty workStatus}">
																<input type="button" name="newNumSearchBtn" id="newNumSearchBtn"
																	onClick="popUpMRTNumSelection('${popupMrtSearchUrl}')" value="New Number Search"/>																
															</c:when>
														</c:choose>														
													</td>
												</tr>
												<tr>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>
													
													<c:if test="${orderDTO==null || orderDTO.sboOrderInd =='N' }">		
													
																					
														<c:url var="popupGoldenSearchUrl" value="/mobccsmrtnumselection.html">
															<c:param name="mrtType" value="goldenMRT"/>
														</c:url>
														<c:choose>
															<c:when test="${workStatus == 'recall'}">
																<input type="button" name="goldenNumSearchBtn" id="goldenNumSearchBtn"
																	onClick="mobOnChange('${popupGoldenSearchUrl}')" value="Golden Number Search"/>
															</c:when>
															<c:when test="${empty workStatus}">
																<input type="button" name="goldenNumSearchBtn" id="goldenNumSearchBtn"
																	onClick="popUpMRTNumSelection('${popupGoldenSearchUrl}')" value="Golden Number Search"/>
															</c:when>
														</c:choose>	
														</c:if>															
													</td>
												</tr>
												<tr>
													<td><spring:message code="label.msisdnlvl"/></td>
													<td><form:input path="msisdnLvl" readonly="true" /></td>
													<td>
													<c:if test="${orderDTO==null || orderDTO.sboOrderInd =='N' }">															
														<c:url var="popupSpecialSearchUrl" value="/mobccsmrtnumselection.html">
															<c:param name="mrtType" value="specialMRT"/>
														</c:url>
														<c:choose>
															<c:when test="${workStatus == 'recall'}">
																<input type="button" name="SpecialNumSearchBtn" id="specialNumSearchBtn"
																	onClick="mobOnChange('${popupSpecialSearchUrl}')" value="Special Number Search"/>
															</c:when>
															<c:when test="${empty workStatus}">
																<input type="button" name="SpecialNumSearchBtn" id="specialNumSearchBtn"
																	onClick="popUpMRTNumSelection('${popupSpecialSearchUrl}')" value="Special Number Search"/>
															</c:when>
														</c:choose>	
														</c:if>													
													</td>
												</tr>												
												<tr>
													<td><spring:message code="label.serviceReqDate"/></td>
													<td><form:input path="serviceReqDateStr" maxlength="10" id="serviceReqDateDatepicker" readonly="true" />
													</td>												
												</tr>
												<tr>
													<td colspan="2"><form:errors path="mobMsisdn" cssClass="error" />&nbsp;</td>
												</tr>
												<tr>
													<td colspan="2"><form:errors path="msisdnLvl" cssClass="error" />&nbsp;</td>
												</tr>
												<tr>
													<td colspan="2"><form:errors path="serviceReqDateStr" cssClass="error" />&nbsp;</td>
												</tr>												
												<tr>
													<td>&nbsp;</td>
												</tr>
											</table>
										</div>
									</td>
									<td>
										<div id="divMNP">
											<table width="100%" border="0" cellspacing="0"
												class="contenttext">
												<tr>
													<td>
														<label>
															<form:radiobutton path="mnpInd" value="Y" id="mnpcheckbox" onclick="showMobMnp('mnp')" />
														</label>
														<spring:message code="label.mnpInd.Y"/>
													</td>
													<!-- <td><input type="checkbox" name="mnpcheckbox"
														id="mnpcheckbox" onclick="showMobMnp('mnp')" />MNP</td>		 -->											
												</tr>
												<tr>
													<td id="mnpMsisdnId"><spring:message code="label.mnpMsisdn"/></td>
													<td><form:input maxlength="8" path="mnpMsisdn" /> to PCCW<form:errors path="mnpMsisdn" cssClass="error" />
													<spring:bind path="ignorePostpaidcheckbox">
													<c:choose>
													<c:when test="${status.error}">
														<form:checkbox path="ignorePostpaidcheckbox" />
														<span class="error"><form:errors path="ignorePostpaidcheckbox" cssClass="error" /> If you want to ignore it, please tick the checkbox and continue.</span>
													</c:when>
													<c:otherwise>
														<form:hidden path="ignorePostpaidcheckbox"/>
													</c:otherwise>
													</c:choose>
													
													</spring:bind>
													</td>
												</tr>		
												
												<!-- dennis enable mnp -->
												<c:if test="${ empty mipEnableMNPTicket   or 	mipEnableMNPTicket eq 'Y'}">					
												<tr>
													<td><spring:message code="label.mnpTicketNum"/></td>
													<td>
													 <form:input path="mnpTicketNum" maxlength="10" />
													
													 <form:errors path="mnpTicketNum" cssClass="error" />
													 <!-- 201607 C MNP Ticket -->
												   	<a href="http://ecs.hkcsl.com/cts/cts_login.jsp?login=csimcts&passwd=csim" target="_blank" class="nextbutton3 auth_button" style="vertical-align: middle">Get C MNP Ticket</a>
													<span class="smalltextgray" style="font-weight:bold">Note: This is C-Web CTS system, for login, please consult C-system IT. It can only be accessed via intranet.</span>													
													<!-- 201607 C MNP Ticket -->
													</td>
												</tr>
												</c:if>
																					
												<tr>
													<td><spring:message code="label.cutOverDate"/></td>
													<td><form:input path="cutOverDateStr" maxlength="10"
															id="cutOverDateDatepicker" readonly="true"/>
															<form:errors path="cutOverDateStr" cssClass="error" />
															
															<!-- DENNIS201606 -->
																<span id="mnpExtendSpan" style="text-align:right">
																<c:choose>
																<c:when test="${mobccsmrt.mnpExtendAuthInd == 'Y'}">
																	<span style="color: #d63c00"><spring:message code="label.mnpExtendAuthorized"/></span>
																	 <form:errors path="mnpExtendAuthInd" cssClass="error" /> 
																</c:when>
																<c:otherwise>
																	<span id="mnpExtendNotAuthorizedDiv">
																		<a href="#" id="mnpExtendAuthorizeButton" class="nextbutton3"><spring:message code="label.mnpExtendNotAuthorized"/></a>									
																		 <form:errors path="mnpExtendAuthInd" cssClass="error" /> 
																	</span>
																	<span id="mnpExtendAuthorizedDiv" style="display:none">
																		<span style="color: #d63c00"><spring:message code="label.mnpExtendAuthorized"/></span>
																	</span>
																</c:otherwise>
																</c:choose>
																</span>
																<form:hidden path="mnpExtendAuthInd" />
													</td>
												</tr>												
												<tr>
													<td><spring:message code="label.cutOverTime"/></td>
													<td><form:select path="cutOverTime">															
															<form:option value="0" label="01:00-04:00" />
															<form:option value="1" label="12:00-14:00" />
														</form:select><form:errors path="cutOverTime" cssClass="error" />
													</td>
												</tr>												
												<tr>
													<td><spring:message code="label.custName"/></td>
													<td><form:input path="custName" id="custName"/><form:errors path="custName" cssClass="error" /></td>
												</tr>												
												<tr>
													<td>
														<label>
															<form:checkbox path="prepaidSimInd" id="prepaySimCust" onclick="prepaySimClick()"/>
															<spring:message code="label.prepaySimCust"/>
														</label>
													</td>
												</tr>
												<tr>
													<td>
														<div id="divOldId" style="vertical-align: top">
															<spring:message code="label.docNum.oldCust"/>
															<br><spring:message code="label.docNum.oldCust.1"/>
														</div>
														<div id="divPrepaidSim"><spring:message code="label.docNum.prepaySimCust"/></div>
													</td>
													<td>
														<form:input path="docNum" maxlength="20" cssStyle="vertical-align: top"/>
														<form:errors path="docNum" cssClass="error" />
													</td>
												</tr>												
											</table>
											<!-- CSL NDO -->
											<div id="clsDnoDiv" style="display: none;">
											<table width="100%" border="0" cellspacing="0" class="contenttext">
												<tr>
													<td width="55%">Original Activation Date</td>
													<td ><form:input path="origActDateStr"
															maxlength="10" id="origActDateDatepicker" />
															<a href="#" id ="origActDateStrClear">Clear</a>
															<form:errors path="origActDateStr" cssClass="error" />
													</td>
												</tr>
												
											</table>
											</div>
											<!-- end CSL NDO -->
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="5">
							<div id="divUnicom">
								<table width="100%" border="0" cellspacing="0"
									class="contenttext">
									<tr>
										<td class="table_title"><spring:message code="label.mobccsmrt.header.china"/></td>
									</tr>
									<tr>
										<td>
											<table width="100%" border="0" cellspacing="0" class="contenttext">
												<tr>
													<td><spring:message code="label.cityCd"/></td>
													<td><form:input path="cityCd" readonly="true" />
														<c:url var="popupMrtSearchUrl" value="/mobccsmrtnumselection.html">
															<c:param name="mrtType" value="unicom"/>
														</c:url>
														<c:choose>
															<c:when test="${workStatus == 'recall'}">																													
																<input type="button" name="unicomSearchBtn" id="unicomSearchBtn" 
																	onClick="mobOnChange('${popupMrtSearchUrl}')" value="Search"/>				
															</c:when>
															<c:when test="${empty workStatus}">
																<input type="button" name="unicomSearchBtn" id="unicomSearchBtn"
																	onClick="popUpMRTNumSelection('${popupMrtSearchUrl}')" value="Search"/>														
															</c:when>
														</c:choose>														
													</td>
												</tr>
												<tr>
													<td colspan="1"><form:errors path="cityCd" cssClass="error" /></td>
												</tr>
												<tr>
													<td><spring:message code="label.unicomMobMsisdn"/></td>
													<td><form:input path="unicomMobMsisdn" readonly="true" /><form:errors path="unicomMobMsisdn" cssClass="error" /></td>
												</tr>												
												<tr>
													<td><spring:message code="label.unicomMobGrade"/></td>
													<td><form:input path="unicomMobGrade" readonly="true" /><form:errors path="unicomMobGrade" cssClass="error" /></td>
												</tr>												
											</table>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<table width="100%" border="0" cellspacing="0" cellspacing="0"
		class="contenttext">
		<tr>
			<td>
				<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3" onClick="formSubmit()">continue &gt;</a>
				</div>
			</td>
		</tr>
	</table>
	<!-- end button table -->
	<input type="hidden" name="currentView" value="mobccsmrt" />
	<input type="hidden" name="orderStatus" id="orderStatus" value="${workStatus}" />
	<form:hidden path="contractPeriod"/>
	<form:hidden path="rpRecurChange"/>
	<form:hidden path="unicomMobStatus" />
	<form:hidden path="chinaInd" />
	<form:hidden path="specialNumInd" />
</form:form>
<div id="authDialog"></div>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>