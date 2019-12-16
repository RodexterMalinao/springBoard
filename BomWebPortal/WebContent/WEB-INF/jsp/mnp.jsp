<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.min.css"
      rel="stylesheet" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="js/json2.min.js"></script>
<%@ include file="loadingpanel.jsp" %>
<%@ page import="com.bomwebportal.dto.CustomerProfileDTO" %>
<%@ page import="com.bomwebportal.dto.OrderDTO" %>
<%@ page import="com.bomwebportal.dto.MnpDTO" %>
<%
	String custName = "";
	String idDocNum = "";
	String custNameChi = "";
	CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
	
	if (sessionCustomer != null) {
		if ("BS".equals(sessionCustomer.getIdDocType()))					
			custName = sessionCustomer.getCompanyName();
		else
			custName = sessionCustomer.getContactName();
		idDocNum = sessionCustomer.getIdDocNum();
	}
	
	MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
	if (mnpDto != null) {
		if ("Y".equals(mnpDto.getMnp())){
			custName = mnpDto.getCustName();
			custNameChi = mnpDto.getCustNameChi();
			idDocNum = mnpDto.getCustIdDocNum();
		} else if (mnpDto.isFutherMnp()){
			custName = mnpDto.getFuthercustName();
			custNameChi = mnpDto.getFuthercustNameChi();
			idDocNum = mnpDto.getFuthercustIdDocNum();
		}
	}
%>

<pccw-util:codelookup var="mipEnableMNPTicketMap" codeType="MIP_SHOW_MNP_TICKET_BTN"  asEntryList="true" />
<c:set var="mipEnableMNPTicket" value="${mipEnableMNPTicketMap[0].key}" />


<style>
.ui-widget {
  font-size: 0.8em;
}
</style>
<script language="javascript">
	var authorizeType = "";
	var newNumPage = 0;
	var newNumPageMax = 5;
	function show(which) {
		//    document.getElementById('mnpY').style.display = ( which == 'mnpY') ? 'block' : 'none';
		//    document.getElementById('mnpN').style.display = ( which == 'mnpN') ? 'block' : 'none';

		if (which == 'mnpY') {
			$('#mrtCheckBtn').attr('disabled', 'disabled');
			$("#mnpY").show();
			$("#mnpN").hide();
			
		}
		if (which == 'mnpN') {
			$('#mrtCheckBtn').removeAttr('disabled');
		
			
			$("#mnpY").hide();
			$("#mnpN").show();
		}
	}

	function formSubmit(actionType) {
		//$("input[name='radioName']:checked").val()

		if ($('input[name=mnp]:checked').val() == 'Y') {
			//alert($('input[name=mnp]:checked').val());
			if ($('#cutoverDateDatepicker').val().length == 0
					/* && !$('#cutoverDateStrCheckBox').is(':checked') */) {
				alert('Please input Cut Over Date');
				return false;
			}

		}
		if ($('input[name=mnp]:checked').val() == 'N') {
			//alert($('input[name=mnp]:checked').val());

			if ($('#serviceReqDateDatepicker').val().length == 0
					/* && !$('#serviceReqDateStrCheckBox').is(':checked') */) {
				alert('Please input Service Reqest Date');
				return false;
			}
		}
		//add by herbert 20111018
		document.mnpForm.actionType.value = actionType;
		$("#mnpForm").submit();
	}

	$(document).ready(function() {
		
		if ($('#custName').val() == 'Prepaid Sim') {
			$('#prepaySimCust').attr('checked', true);
			prepaySimClick('');
		} else {
			$('#prepaySimCust').attr('checked', false);
			//	prepaySimClick();

		}
		
		if ($('#futhercustName').val() == 'Prepaid Sim') {
			$('#futherprepaySimCust').attr('checked', true);
			prepaySimClick('futher');
		} else {
			$('#futherprepaySimCust').attr('checked', false);
			//	prepaySimClick();

		}
		//alert($('#serviceReqDateDatepicker').val().length);
		
		
		//alert($('#mnpRadioN').is(":checked"));

		if ($('#mnpRadioN').is(':checked')) {
			$('#mrtCheckBtn').removeAttr('disabled');
			 show('mnpN');
		}
		if ($('#mnpRadioY').is(':checked')) {
			$('#mrtCheckBtn').attr('disabled', 'disabled');
			 show('mnpY');
		}
		
		
		if ($('#mnpExtendAuthorizeButton').length) {
			$("#mnpExtendAuthorizeButton").click(function(e) {
				e.preventDefault();
				authorizeType = "AU19";
				
				var orderID = "<c:out value='${orderDto.orderId}'/>";
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
		
	});
	
	function prepaySimClick(prex) {
		if ($('#'+ prex + 'prepaySimCust').is(':checked')) {//set prepaySim
			$('#'+ prex + 'custName').val("Prepaid Sim");
			$('#'+ prex + 'custName').attr('readonly', true);

			if ($('#'+ prex + 'custIdDocNum').val().length > 20) {
				//alert('long then 20');
				var temp = $('#'+ prex + 'custIdDocNum').val();
				$('#'+ prex + 'custIdDocNum').val(temp.substr(0, 20));
			}
			$('#'+ prex + 'custIdDocNum').attr("maxlength", 20); //set max length

			$('#'+ prex + 'prePaidSimDocDiv').show();
			//$('#prePaidSimDocWithCert1').attr('checked', false);
			//$('#prePaidSimDocWithoutCert1').attr('checked', false);

		} else {
			$('#'+ prex + 'custName').val("<%=custName%>");
			$('#'+ prex + 'custName').attr('readonly', false);
			$('#'+ prex + 'custIdDocNum').val("<%=idDocNum%>");
			$('#'+ prex + 'custIdDocNum').attr("maxlength", 20); //set max length
			$('#'+ prex + 'prePaidSimDocDiv').hide();
		}
	}


	function futherMnpClick() {
		//alert('futherMnpClick function call');
		if ($('#futherMnp1').is(':checked')) {

			//alert('futherMnpClick SHOW SHOW');
			$("#futherMnpDiv").show();
			prepaySimClick('futher');
		} else {
			//alert('futherMnpClick HIDE HIDE');
			$("#futherMnpDiv").hide();
			//clear data
			
			$('#futherMnpNumber').val("");
			$('#futherCutoverDateStr').val("");
			$('#futherCutoverDateDatepicker').val("");

		}
	}
	
	function patternMrtClick(){
		$('input[name=newNumSelection]').attr('checked', false);
		//$('#newMsisdn').val("");
		
		if ($('#checkPatternMrt').is(":checked")) {

			//alert('patternMrtClick SHOW SHOW');
			$("#patternMrtDiv").show();
		} else {
			//alert('patternMrtClick HIDE HIDE');
			$("#patternMrtDiv").hide();
			$('#patternMrtSearchText').val("");
			//$(".patternSearchResultTableContext:last tbody").empty();

		}
	}

	function prePaidSimDocClick(inputA,prex) {
		//alert('prePaidSimDocClick called');
		//alert(inputA);

		if (inputA == 'Y') {
			//alert('enter Y');
			$('#prePaidSimDocWithCert1').attr('checked', true);
			$('#prePaidSimDocWithoutCert1').attr('checked', false);
			
			
			$('#' + prex + 'prePaidSimDocWithCert').attr('checked', true);
			$('#' + prex + 'prePaidSimDocWithoutCert').attr('checked', false);

		} else {
			//alert('enter N');
			$('#prePaidSimDocWithCert1').attr('checked', false);
			$('#prePaidSimDocWithoutCert1').attr('checked', true);
			
			$('#' + prex + 'prePaidSimDocWithCert').attr('checked', false);
			$('#' + prex + 'prePaidSimDocWithoutCert').attr('checked', true);

		}
	}
	
	function getMnpTicketNum(cutoverDateString, cutoverTimeString){
		if (cutoverDateString == null) {
			return "";
	    }
		// check valid date
	    var d = cutoverDateString[1];
	    var m = cutoverDateString[2] - 1;
	    var y = cutoverDateString[3];
	    var composedDate = new Date(y, m, d);
	    var val_flag = composedDate.getDate() == d &&
	            composedDate.getMonth() == m &&
	            composedDate.getFullYear() == y;		    	
	    if (!val_flag) {
			return "";
	    }
	    //check date range
	    var currentDate = new Date();
	    var comparedDate = new Date();
	    comparedDate.setDate(currentDate.getDate() + 30);
	    if (composedDate.getTime() > comparedDate.getTime()){
			return "";
	    }
	    // amend with leading 0s
	    cutoverDateString[1] = ("0" + cutoverDateString[1]).substr(("0" + cutoverDateString[1]).length - 2);
		cutoverDateString[2] = ("0" + cutoverDateString[2]).substr(("0" + cutoverDateString[2]).length - 2);
		var ticketNumFirst5 = cutoverDateString[2] + cutoverDateString[1] + cutoverTimeString;
		return ticketNumFirst5;
	}
	
	function nextNewNumPage() {
		$('div.newNumPage').hide();
		$('div#newNumPage' + newNumPage).show();
		newNumPage = newNumPage + 1;
		if ((newNumPage >= newNumPageMax) || ($('div#newNumPage' + newNumPage).length==0)) {
			newNumPage = 0;
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
	
	function popUpMRTSelection(url){
		window.open(url, 'NewMRT', 'height=500,width=700,resizable=yes,scrollbars=yes');
	}

	function mobOnChange(url) {
		
		$(".originalMrt").show();
		popUpMRTSelection(url);
	}

	
	function patternSearch() {
		var str= $("#patternMrtSearchText").val();
		if(str.replace(/[^0-9]/g, '').length >= 2 && str.replace(/[^0-9]/g, '').length <= 8){
			var rdiv = $("#patternResultDiv");
			rdiv.html("Loading...");
			
			$.ajax({
				url : "mobccsmrtajax.html",
				data : {
					type : "patternMRTSearch",
					searchMsisdn : $("#patternMrtSearchText").val(),
					appDate : $("#appDate").val()
				},
				cache : false,
				dataType : "json",
				success : function(data) {
					if (!data || data.length == 0) {
						rdiv.html("No result");
					} else {
						rdiv.html("");
						$.each(data, function(index, item) {
							rdiv.html(rdiv.html() + " <input type='radio' class='newNumSelection' name='newNumSelection' value=" +  item.msisdn + " onclick='selectNewNumber("+ item.msisdn+")'/>" + item.msisdn);
							if (index % 5 == 4) {
								rdiv.html(rdiv.html() + "<br/>");
							}
							
						});
					}
				},
				error : function() {
					rdiv.html("Error found, please retry");
				}
			});
		}else{
			alert("Please input at least 2 valid digit numbers");
		}
	}
	
	function selectNewNumber(value) {
		$('#newMsisdn').val(value);
	    $("input[name=isReserveMrt]").attr("checked", false).trigger("change");
	}
	
	function authorized() {
		$('#authDialog').dialog('close');
		if (authorizeType == 'AU19') {
			$("#mnpExtendNotAuthorizedDiv").hide();
			$("#mnpExtendAuthorizedDiv").show();
			$("input[name=mnpExtendAuthInd]").val("Y");
		}
	}
	
	
	$(function() {
		var ocid ='<c:out value="${OrderDto.ocid}"/>';
			
		
		var enable= '<c:out value="${enableCsl}"/>';
		if ("Y"==enable){
			
			cslDivChange();
			$("#mnpMsisdn").focusout(function(){
				cslDivChange();
			});
			$("#origActDateStrClear").click(function(){
				$("#origActDateDatepicker").val('');
			});
			<% if (bomsalesuser.getChannelId() != 10 && bomsalesuser.getChannelId() != 11) { %>
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
			<% } %>
		}

		// Datepicker
		<% if (bomsalesuser.getChannelId() != 10 && bomsalesuser.getChannelId() != 11) { %>
		$('#cutoverDateDatepicker').attr('readonly', true);
		$('#cutoverDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			//showOn: "button",
			//buttonImage: "images/calendar.gif",
			//buttonImageOnly: true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "+0",
			maxDate : "+100Y", //Y is year, M is month, D is day  
			yearRange : "0:+100" //the range shown in the year selection box

		});
		<% } %>
		
		
		<% if (bomsalesuser.getChannelId() != 10 && bomsalesuser.getChannelId() != 11) { %>
		$('#serviceReqDateDatepicker').attr('readonly', true);
		$('#serviceReqDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			//showOn: "button",
			//buttonImage: "images/calendar.gif",
			//buttonImageOnly: true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "+0",
			maxDate : "+100Y", //Y is year, M is month, D is day  
			yearRange : "0:+100" //the range shown in the year selection box

		});
		<% } else { %>
		$('#cutoverDateDatepicker').change(function() {
			var cutoverDateString = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/
				.exec($('#cutoverDateDatepicker').val().toString());
			var cutoverTimeString = $('#cutoverTime').val();
			$('#mnpTicketNum').val(getMnpTicketNum(cutoverDateString, cutoverTimeString));
		});
		
		$('#cutoverTime').change(function() {
			if ($('#cutoverDateDatepicker').val() != null && $('#cutoverDateDatepicker').val() != "") {
				var cutoverDateString = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/
					.exec($('#cutoverDateDatepicker').val().toString());	
				var cutoverTimeString = $('#cutoverTime').val();
				$('#mnpTicketNum').val(getMnpTicketNum(cutoverDateString, cutoverTimeString));
			};						
		});
		$("input[name='newNumSelection']").change(function() {
			selectNewNumber($(this).val());
		});
		nextNewNumPage();
		<% } %>
		

		<% if (bomsalesuser.getChannelId() != 10 && bomsalesuser.getChannelId() != 11) { %>
		$('#futherCutoverDateDatepicker').attr('readonly', true);
		$('#futherCutoverDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			//showOn: "button",
			//buttonImage: "images/calendar.gif",
			//buttonImageOnly: true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "+0",
			maxDate : "+100Y", //Y is year, M is month, D is day  
			yearRange : "0:+100" //the range shown in the year selection box

		});
		<% } else {%>
		$('#futherCutoverDateDatepicker').change(function() {
			var futherCutoverDateString = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/
				.exec($('#futherCutoverDateDatepicker').val().toString());
			var futherCutoverTimeString = $('#futherCutoverTime').val();
			$('#futherMnpTicketNum').val(getMnpTicketNum(futherCutoverDateString, futherCutoverTimeString));
		});
		
		$('#futherCutoverTime').change(function() {
			if ($('#futherCutoverDateDatepicker').val() != null && $('#futherCutoverDateDatepicker').val() != "") {
				var futherCutoverDateString = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/.exec($('#futherCutoverDateDatepicker').val().toString());
				var futherCutoverTimeString = $('#futherCutoverTime').val();
				$('#futherMnpTicketNum').val(getMnpTicketNum(futherCutoverDateString, futherCutoverTimeString));
			};						
		});
		<% } %>
		prepaySimClick('');
		futherMnpClick();

		$("input[name=isReserveMrt]").change(function() {
			if ($(this).is(":checked")) {
				$("#reserveMrtRow").show();
			} else {
				$("#reserveMrtRow").hide();
				$("input[name=reserveId]").val("");
			}
		}).trigger("change");
	});

</script>

<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobmnp" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>



<form:form name="mnpForm" id="mnpForm" method="POST" commandName="mnp">
	<table width="100%" class="tablegrey" border="0">
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="1">
					<tr >
						<td class="table_title" colspan="2">Mobile Number</td>
					</tr>
					<tr >
						<td class="contenttext" colspan="2">Would you like to
							transfer the mobile number from other network operator to PCCW? </td>
					</tr>
						
					
					<tr>
					<td class="contenttext">					
					
						
					<form:radiobutton path="mnp" value="Y"
								id="mnpRadioY" onclick="javascript:show('mnpY');" />Yes <b>(MNP)</b>
								<span class="contenttext_red">*</span> 
							<form:errors path="mnp"	cssClass="error" />
					
					</td>
					
					<td class="contenttext">
					<form:radiobutton path="mnp" id="mnpRadioN"
								value="N" onclick="javascript:show('mnpN');" />No thanks <b>(New Mobile Number)</b>
								<span class="contenttext_red">*</span>
								<span class="contenttext_red"><b>(
								<label for="numTypeLabel">
								<pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${customer.numType}"/>
									<c:if test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
										& <pccw-util:codelookup codeType="MIP_SIM_TYPE" codeId="${customer.simType}" />
									</c:if>
								</label>								
								)</b></span>
					
					
					</td>
					</tr>
					
					
				</table>


				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td class="contenttext" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
						<!-- MNP BLOCK -->
						
						
				<div id="mnpY" style="display: block;">
					<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
					<td colspan="3"  class="table_title"> MNP information</td>
					</tr>
				
					
					<tr>
							<td width="30%">Transfer my number</td>
							<td colspan="2"><form:input
								maxlength="8" path="mnpMsisdn" /> &nbsp; to PCCW <form:errors
								path="mnpMsisdn" cssClass="error" />
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
						<tr>
							<c:choose>
							<c:when test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
								<td width="30%">Cutover Date (dd/mm/yyyy)</td>
							</c:when>
							<c:otherwise>
								<td width="30%">Cutover Date</td>
							</c:otherwise>
							</c:choose>
							<td colspan="1"><form:input path="cutoverDateStr" 
									maxlength="10" id="cutoverDateDatepicker"  /><form:errors
									path="cutoverDateStr" cssClass="error" /></td>
							<td>	
							<!-- DENNIS201606 -->
									<span style="text-align:right">
									<c:choose>
									<c:when test="${mnp.mnpExtendAuthInd == 'Y'}">
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
							<td width="30%">Cutover Time</td>
							<td colspan="2"><form:select path="cutoverTime">
									<form:option value="0" label="01:00-04:00" />
									<form:option value="1" label="12:00-14:00" />
								</form:select> <form:errors path="cutOeverTimeError" cssClass="error" /></td>
						</tr>
	
	
						<!-- dennis enable mnp -->
						<c:if test="${ empty mipEnableMNPTicket   or 	mipEnableMNPTicket eq 'Y'}">
						<tr>
							<td width="30%">MNP Ticket Number</td>
							<td colspan="2"><form:input path="mnpTicketNum"
									maxlength="10" /> <form:errors path="mnpTicketNum"
									cssClass="error" />
									
									<!-- 201607 C MNP Ticket -->
									
								   	<a href="http://ecs.hkcsl.com/cts/cts_login.jsp?login=csimcts&passwd=csim" target="_blank" class="nextbutton3 auth_button" style="vertical-align: middle">Get C MNP Ticket</a>
									<span class="smalltextgray" style="font-weight:bold">Note: This is C-Web CTS system, for login, please consult C-system IT. It can only be accessed via intranet.</span>
									
								
									
									<!-- 201607 C MNP Ticket -->
							</td>
						</tr>
						</c:if>

						<tr>
							<td width="30%">Old Customer Name</td>
							<td colspan="2"><form:input path="custName" /><form:checkbox path="prepaidSimInd"
								id="prepaySimCust" onclick="prepaySimClick('')"/>
								Prepaid Sim Customer <form:errors path="custName"
									cssClass="error" /></td>
						</tr>
						<!-- add by wilson 20120719 , paperless  -->
						<tr>
							<td width="30%">Old Customer Name in Chinese</td>
							<td colspan="2"><form:input path="custNameChi" /> <form:errors
									path="custNameChi" cssClass="error" /></td>
						</tr>
						<tr>
							<td width="30%">Old Customer Doc No. / Prepaid SIM No.</td>
							<td colspan="2"><form:input path="custIdDocNum"
									maxlength="20" /> <form:errors path="custIdDocNum"
									cssClass="error" /></td>
						</tr>
					</table>
					<div id="clsDnoDiv" style="display: none;">
					<table  width="100%" border="0" cellspacing="1" class="contenttext">
					<!-- CSL NDO  -->
						
					
						
						<tr>
							<c:choose>
							<c:when test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
								<td width="30%">Original Activation Date (dd/mm/yyyy)</td>
							</c:when>
							<c:otherwise>
								<td width="30%">Original Activation Date</td>
							</c:otherwise>
							</c:choose>
							<td colspan="2"><form:input path="origActDateStr"
									maxlength="10" id="origActDateDatepicker" />
									<a href="#" id ="origActDateStrClear">Clear</a>
									<form:errors path="origActDateStr" cssClass="error" />

							</td>
						</tr>

						<!-- end CSL NDO -->
					</table>
					</div>
					
					<div id="prePaidSimDocDiv" style="display: block;">
					<table width="100%" border="0" cellspacing="1" class="contenttext">
						<tr>
							<td>&nbsp;<form:errors path="prePaidSimDocWithCert"	cssClass="error" /></td>
						</tr>
						<tr>
							<td>Declaration by Customer of Prepaid SIM Service user:
								(tick as appropiate)</td>
						</tr>
						<tr>
							<td><form:checkbox path="prePaidSimDocWithCert"
									onclick="prePaidSimDocClick('Y','')" />We are / I am the holder of
								the Cardholder Certificate for the Mobile Number, a copy of
								which is attached.</td>
						</tr>
						<tr>
							<td><form:checkbox path="prePaidSimDocWithoutCert"
									onclick="prePaidSimDocClick('N','')" />We / I have lost our / my
								Cardholder Certificate for the Prepaid SIM Service associated
								with the Mobile Number allocated to us / me by the DNO.</td>
						</tr>
					</table>

				</div>
				</div>
						<!-- END MNP BLOCK -->
						
						
						
						<!-- NEW NUMBER BLOCK -->
						
						<div id="mnpN" style="display: block;">
					<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
					<td colspan="3"  class="table_title"> New Mobile Number information</td>
					</tr>
					
					<tr>
							<td width="30%">I want to choose </td>
							<td colspan="2">
								<form:input maxlength="8" path="newMsisdn" />
								<c:if test="${bomsalesuser.channelId == 1 || bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
								<c:url var="popupMrtSelectionUrl" value="/mrtselection.html">
										<c:param name="type" value="primary"/>
								</c:url>														
								<c:choose>
									<c:when test="${not empty orderDto.orderId}">																													
										<input type="button" name="newNumSearchBtn" id="newNumSearchBtn"
											onClick="mobOnChange('${popupMrtSelectionUrl}')" value="MRT Search"/>																
									</c:when>
									<c:otherwise>
										<input type="button" name="newNumSearchBtn" id="newNumSearchBtn"
											onClick="popUpMRTSelection('${popupMrtSelectionUrl}')" value="MRT Search"/>																
									</c:otherwise>
								</c:choose>		
								<span class="originalMrt" style="display:none ; color: blue">Original inputted MRT: ${mnp.newMsisdn}</span>		
								</c:if>
							<c:if test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
								<tr><td></td><td><form:checkbox path="isReserveMrt" value="Y"/><label for="isReserveMrt1">Reserve MRT?</label></td></tr>
								<tr id="reserveMrtRow" style="display: none">
									<td><form:label path="reserveId">Reserve ID:</form:label></td>
									<td><form:input path="reserveId" /><form:errors path="reserveId" cssClass="error" /></td>
								</tr>
							</c:if>
							<form:errors path="newMsisdn" cssClass="error" /><form:errors path="numType" cssClass="error" /></td>
							
						</tr>
						<tr>
							<c:choose>
							<c:when test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
								<td width="30%">Service Request Date (dd/mm/yyyy)</td>
							</c:when>
							<c:otherwise>
								<td width="30%">Service Request Date</td>
							</c:otherwise>
							</c:choose>
							<td colspan="2"><form:input path="serviceReqDateStr"
									maxlength="10" id="serviceReqDateDatepicker" /><form:errors
									path="serviceReqDateStr" cssClass="error" /></td>
						</tr>
						<tr>
							<td>MRT Status</td>
							<td colspan="2"><form:hidden path="cnmStatus" /> <c:choose>
									<c:when test='${mnp.cnmStatus == 0}'>
										<spring:message code="mrtStatus.INITIAL" text="" />
									</c:when>
									<c:when test='${mnp.cnmStatus == 2}'>
										<spring:message code="mrtStatus.NORMAL" text="" />
									</c:when>
									<c:when test='${mnp.cnmStatus == 3}'>
										<spring:message code="mrtStatus.LOCK" text="" />
									</c:when>
									<c:when test='${mnp.cnmStatus == 4}'>
										<spring:message code="mrtStatus.ACTIVE" text="" />
									</c:when>
									<c:when test='${mnp.cnmStatus == 5}'>
										<spring:message code="mrtStatus.FROZEN" text="" />
									</c:when>
									<c:when test='${mnp.cnmStatus == 18}'>
										<spring:message code="mrtStatus.RESERVED" text="" />
									</c:when>
									<c:when test='${mnp.cnmStatus == 19}'>
										<spring:message code="mrtStatus.PREASGN" text="" />
									</c:when>
									<c:when test='${mnp.cnmStatus == 99}'>
										<spring:message code="mrtStatus.PORTOUT" text="" />
									</c:when>
									<c:otherwise>${mnp.cnmStatus}</c:otherwise>
								</c:choose> <input id="mrtCheckBtn" type="submit" value="Refresh"
								onClick="javascript:formSubmit('REFRESH') " disabled="true" />
							</td>
						</tr>
						<tr>
							<td>MRT Level</td>
							<td colspan="2"><form:input path="msisdnLvl" readonly="true" />
								<form:errors path="msisdnLvl" cssClass="error" /></td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3">
						<form:checkbox path="futherMnp"	onclick="futherMnpClick()" />
						
						Change of Mobile No. by MNP in
							further</td>
					</tr>

					<tr>
					</tr>
				</table>
				
				<div id="futherMnpDiv" style="display: block;">
					<table width="100%" border="0" cellspacing="1" class="contenttext">


						<tr>
							<td width="30%">Transfer my number</td>
							<td colspan="2"><form:input
									maxlength="8" path="futherMnpNumber" /> &nbsp; to PCCW 
									<form:errors path="futherMnpNumber" cssClass="error" />
									<spring:bind path="ignoreFutherPostpaidcheckbox">
									<c:choose>
									<c:when test="${status.error}">
										<form:checkbox path="ignoreFutherPostpaidcheckbox" />
										<span class="error"><form:errors path="ignoreFutherPostpaidcheckbox" cssClass="error" /> If you want to ignore it, please tick the checkbox and continue.</span>
									</c:when>
									<c:otherwise>
										<form:hidden path="ignoreFutherPostpaidcheckbox"/>
									</c:otherwise>
									</c:choose>
									
									</spring:bind>
									</td>
						</tr>
						<tr>
							<c:choose>
							<c:when test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
								<td width="30%">Cutover Date (dd/mm/yyyy)</td>
							</c:when>
							<c:otherwise>
								<td width="30%">Cutover Date</td>
							</c:otherwise>
							</c:choose>
							<td colspan="2"><form:input path="futherCutoverDateStr"
									maxlength="10" id="futherCutoverDateDatepicker" />
									<form:errors path="futherCutoverDateStr" cssClass="error" />

							</td>
						</tr>
						
						<tr>
							<td width="30%">Cutover Time</td>
							<td colspan="2"><form:select path="futherCutoverTime">
									<form:option value="0" label="01:00-04:00" />
									<form:option value="1" label="12:00-14:00" />
								</form:select> <form:errors path="futherCutOeverTimeError" cssClass="error" /></td>
						</tr>

						<!-- dennis enable mnp -->
						<c:if test="${ empty mipEnableMNPTicket   or 	mipEnableMNPTicket eq 'Y'}">					
						<tr>
							<td width="30%">MNP Ticket Number</td>
							<td colspan="2"><form:input path="futherMnpTicketNum"
									maxlength="10" /> <form:errors path="futherMnpTicketNum"
									cssClass="error" />
	
								
								<!-- 201607 C MNP Ticket -->
							   	<a href="http://ecs.hkcsl.com/cts/cts_login.jsp?login=csimcts&passwd=csim" target="_blank" class="nextbutton3 auth_button" style="vertical-align: middle">Get C MNP Ticket</a>
								<span class="smalltextgray" style="font-weight:bold">Note: This is C-Web CTS system, for login, please consult C-system IT. It can only be accessed via intranet.</span>
								<!-- 201607 C MNP Ticket -->
							</td>
						</tr>  
						</c:if>

						<tr>
							<td width="30%">Old Customer Name</td>
							<td colspan="2"><form:input path="futhercustName" /><form:checkbox path="futherPrepaidSimInd"
								 id="futherprepaySimCust" onclick="prepaySimClick('futher')"/>
								Prepaid Sim Customer <form:errors path="futhercustName"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td width="30%">Old Customer Name in Chinese</td>
							<td colspan="2"><form:input path="futhercustNameChi" /> <form:errors
									path="futhercustNameChi" cssClass="error" /></td>
						</tr>
						<tr>
							<td width="30%">Old Customer Doc No. / Prepaid SIM No.</td>
							<td colspan="2"><form:input path="futhercustIdDocNum"
									maxlength="20" /> <form:errors path="futhercustIdDocNum"
									cssClass="error" /></td>				
						</tr>
						
					</table>
					
					<div id="futherprePaidSimDocDiv" style="display: block;">
					<table width="100%" border="0" cellspacing="1" class="contenttext">
						<tr>
							<td>&nbsp;<form:errors path="futherprePaidSimDocWithCert"	cssClass="error" /></td>
						</tr>
						<tr>
							<td>Declaration by Customer of Prepaid SIM Service user:
								(tick as appropiate)</td>
						</tr>
						<tr>
							<td><form:checkbox path="futherprePaidSimDocWithCert" id="futherprePaidSimDocWithCert"
									onclick="prePaidSimDocClick('Y','futher')" />We are / I am the holder of
								the Cardholder Certificate for the Mobile Number, a copy of
								which is attached.</td>
						</tr>
						<tr>
							<td><form:checkbox path="futherprePaidSimDocWithoutCert" id="futherprePaidSimDocWithoutCert"
									onclick="prePaidSimDocClick('N','futher')" />We / I have lost our / my
								Cardholder Certificate for the Prepaid SIM Service associated
								with the Mobile Number allocated to us / me by the DNO.</td>
						</tr>
					</table>
					</div>
					
				</div>
				
				</div>
						<!-- END NEW NUMBER BLOCK -->
					
								
								</td>
					</tr>
					
				</table>

				 <input type="hidden" name="appMode" value="shop" /> <input
				type="hidden" name="currentView" value="mnp" /> <input type="hidden"
				name="actionType" id="actionType" />
				<form:hidden path="prePaidSimDocInd" />
				
				
				</td>
		</tr>
	</table>
	
	

	<table width="100%" border="0" cellspacing="0" class="contenttext">
		<tr>
			<td>
				<form:checkbox path="ignoreSameMRTinSBcheckbox" />Ignore same MRT checking in springboard order. <br>
				<font color="blue">
					<form:errors path="ignoreSameMRTinSBcheckbox" cssClass="contenttext_blue"  />
				</font>
				<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3"
						onClick="javascript:formSubmit('SUBMIT');">continue</a>
				</div>
			</td>
		</tr>
	</table>
	<form:hidden path="msisdnLvl" id="msisdnlvl2" />
	<form:hidden path="numType" id="numType" />
</form:form>
<div id="authDialog"></div>

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>