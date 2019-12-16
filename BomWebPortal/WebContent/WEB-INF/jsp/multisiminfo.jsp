<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.min.css"
      rel="stylesheet" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="js/json2.min.js"></script>
<%@ include file="loadingpanel.jsp" %>

<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>

<%
String mobCosUrl = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");
%>

<pccw-util:codelookup var="mipEnableMNPTicketMap" codeType="MIP_SHOW_MNP_TICKET_BTN"  asEntryList="true" />
<c:set var="mipEnableMNPTicket" value="${mipEnableMNPTicketMap[0].key}" />

<pccw-util:codelookup  codeType="MUP_TOO1_ADMIN_CHARGE" var="too1AdminChargeMap" />
<c:forEach var="item" items="${too1AdminChargeMap}">
    <c:set var="too1AdminCharge"  value="${item.key}"/>  
</c:forEach>

<style>
.ui-widget {
  font-size: 0.8em;
}
</style>
<style type="text/css">
tr.vasRow { background-color: #F0F0F0 }
.vasRow .pricing {text-align:center}
.vasRow td ul { margin:0 0 0 0;}
tr.vasHeaderRow { 
	background-color: #6CA9F5; 
	color: #ffffff; 
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; 
	font-size: 13px
}
td.pricing {background-color: #D3F0F5}
table.vasTable {background-color: #ffffff}
tr.mnpInfo { background-color: #E0E0E0 }
tr.numberInfo { background-color: #E0E0E0 }
td.tableItemTitle { text-align:right; font-weight:bold;}
td.tableItem {text-align:left }
div.vasError {
	color: #d63c00;
	font-size: 14px;
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif";}
</style>

<script type="text/javascript">
var newNumPage = new Array();
<c:forEach items="${multiSimInfo.members}" var="member" varStatus="memberStatus">
newNumPage["${memberStatus.index}"] = 0;
$('#memberOrderType${memberStatus.index}').attr('checked', true);
</c:forEach>
var newNumPageMax = 5;
function nextNewNumPage(memberNum) {
	$('div.newNumPage_' + memberNum).hide();
	$('div#newNumPage_' + memberNum + '_' + newNumPage[memberNum]).show();
	newNumPage[memberNum] = newNumPage[memberNum] + 1;
	if ((newNumPage[memberNum] >= newNumPageMax) || ($('div#newNumPage_' + memberNum + '_' + newNumPage[memberNum]).length==0)) {
		newNumPage[memberNum] = 0;
	}
}

function futherMnpClick(memberNum,type) {
	if (type != 'MUPS05'){
		if ($('#furtherMnp_'+memberNum).is(':checked')) {
			$("#FurtherMNP_div_"+memberNum).show();
		} else {
			$("#FurtherMNP_div_"+memberNum).hide();
		}
	}
}

function sameAsCustClick(memberNum) {
	if ($('#sameAsCust_'+memberNum).is(':checked')) {
		$("#actualUserDiv_"+memberNum).hide();
	} else {
		$("#actualUserDiv_"+memberNum).show();
	}
}

function prepaidCheckBoxClick(memberNum) {
	if ($('#prepaidCheckBox_'+memberNum).is(':checked')) {
		$("#custName_" + memberNum).val("Prepaid Sim").attr('readonly', true);
		$("#custDoc_" + memberNum).html("Prepaid SIM No.");
		$("#prepaidTab_" + memberNum).show();
	} else {
		$("#custName_" + memberNum).val("").attr('readonly', false);
		$("#custDoc_" + memberNum).html("Customer Doc Number:");
		$("#prepaidTab_" + memberNum).hide();
	}
}

function prePaidSimDocClick(inputA) {
	if (inputA == 'Y') {
		$('#prePaidSimDocWithCert').attr('checked', true);
		$('#prePaidSimDocWithoutCert').attr('checked', false);

	} else {
		$('#prePaidSimDocWithCert').attr('checked', false);
		$('#prePaidSimDocWithoutCert').attr('checked', true);
	}
}

function toggleVAS(memberNum) {
	$("#vasDiv_" + memberNum).toggle();
	if ($("#vasDiv_" + memberNum).css('display') == 'none') {
		$("#toggleVASbutton_" + memberNum).html("[ + ]");
	} else {
		$("#toggleVASbutton_" + memberNum).html("[ - ]");
	}
}

function popUpMRTSelection(url){
	window.open(url, 'NewMRT', 'height=500,width=700,resizable=yes,scrollbars=yes');
}

function mobOnChange(url) {
	
	$(".originalMrt").show();
	popUpMRTSelection(url);
	
}


function formSubmit(actionType) {
	/*
	<c:forEach items="${multiSimInfo.members}" var="member" varStatus="memberStatus">
	if ($('#furtherMnp_${memberStatus.index}').is(":checked")) {
		if ($('#cutoverDateDatepicker_${memberStatus.index}').val().length == 0
				&& !$('#cutoverDateStrCheckBox_${memberStatus.index}').is(":checked")) {
			alert('Please input Cut Over Date for Line #${memberStatus.index + 1}');
			return false;
		}

	}
	</c:forEach>
	*/
	showLoading();
	document.multiSimInfoForm.actionType.value = actionType;
	document.multiSimInfoForm.submit();
}

function cutoverDateStrCheckBoxClick(memberNum) {
	if ($('#cutoverDateStrCheckBox_' + memberNum).is(':checked')) {
		$('#cutoverDateDatepicker_' + memberNum).val("");
		//	$('#cutoverDateDatepicker').attr("disabled", true); 
	} else {
		// $('#cutoverDateDatepicker').removeAttr("disabled"); 
	}

}

function patternMrtClick(memberNum){
	$("input[name='newNumSelection_" + memberNum + "']").attr('checked', false);
	//$('#newMsisdn').val("");
	if ($('#checkPatternMrt_'+memberNum).is(":checked")) {
		//alert('patternMrtClick SHOW SHOW');
		$("#patternMrtDiv_"+memberNum).show();
	} else {
		//alert('patternMrtClick HIDE HIDE');
		$("#patternMrtDiv_"+memberNum).hide();
		$('#patternMrtSearchText_'+memberNum).val("");

	}
}

function patternSearch(memberNum) {
	var str=$("#patternMrtSearchText_"+memberNum).val();
	
	if(str.replace(/[^0-9]/g, '').length >= 2 && str.replace(/[^0-9]/g, '').length <= 8){
		var rdiv = $("#patternResultDiv_"+memberNum);
		rdiv.html("Loading...");
		
		$.ajax({
			url : "mobccsmrtajax.html",
			data : {
				<c:if test="${(channelId == 2)}">
					type : "patternMRTSearchCcsMul",
				</c:if>
				<c:if test="${(channelId == 10) || (channelId == 11)}">
					type : "patternMRTSearchMul",
				</c:if>
				searchMsisdn : $("#patternMrtSearchText_"+memberNum).val(),
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
						rdiv.html(rdiv.html() + " <input type='radio' class='newNumSelection_" + memberNum + "' name='newNumSelection_" + memberNum + "' value=" +  item.msisdn + " onclick='selectNewNumber("+ item.msisdn +","+ memberNum +")' />" + item.msisdn);
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

//id="msisdn_${memberStatus.index}"
function selectNewNumber(value,memberNum) {
	$('#msisdn_'+memberNum).val(value);
}

function actualUserNoEmailFlagClick(memberNum) {
	if ($('#actualUserNoEmailFlag_' + memberNum).is(":checked"))
	{
		$('#actualUserEmailAddr_' + memberNum).val("");
		$('#actualUserEmailAddr_' + memberNum).prop("disabled", true);

	} else {
		$('#actualUserEmailAddr_' + memberNum).prop("disabled", false);
	}
}

function setActualUserCompanyCustomerDisplay(memberNum) {
	if ($('#actualUserDocType_' + memberNum).find(":selected").val() == "BS") {
		$("#actualUserCompanyNameDiv_" + memberNum).show();
		$("#actualUserCompanyNameInputFiledDiv_" + memberNum).show();
	} else if ($('#actualUserDocType_' + memberNum).find(":selected").val() == "HKID") {
		$("#actualUserCompanyNameDiv_" + memberNum).hide();
		$("#actualUserCompanyNameInputFiledDiv_" + memberNum).hide();
		$("#actualUserCompanyName_" + memberNum).val("");
	} else if ($('#actualUserDocType_' + memberNum).find(":selected").val() == "PASS") {
		$("#actualUserCompanyNameDiv_" + memberNum).hide();
		$("#actualUserCompanyNameInputFiledDiv_" + memberNum).hide();
		$("#actualUserCompanyName_" + memberNum).val("");
	}
}

function clearMigrateMsisdn(index){
	$("#clearMigrateMsisdn"+index).attr('disabled', 'disabled');
	$("#checkMigrateMsisdn"+index).removeAttr("disabled");
	$("#curMsisdn"+index).removeAttr("readonly");
	$("#curMsisdn"+index).val("");
	$("#curCheckErrMsg"+index).val("");
	$("#curCheckResult"+index).val("");
	$("#curCheckResultSpan"+index).html($("#curCheckResult"+index).val());
	$("#curProfileDiv"+index).hide();
}

function setMigrateMsisdnResult(data, index) {
	var result = jQuery.parseJSON(data.gson);
	
	if (result.wsResult==="PASS") {
		$("#checkPass"+index).css("color", "green");
		$("#checkPassFH"+index).val(result.wsResult);
		$("#curProfileDiv"+index).show();  
	 	$("#curTitle"+index).text(result.title);
	 	$("#curTitleFH"+index).val(result.title);
		$("#curLastName"+index).text(result.custLastName);		
		$("#curLastNameFH"+index).val(result.custLastName);
		$("#curFirstName"+index).text(result.custFirstName);	
		$("#curFirstNameFH"+index).val(result.custFirstName);
		$("#curIdDocType"+index).text(result.idDocType);		
		$("#curIdDocTypeFH"+index).val(result.idDocType);
		$("#curIdDocNum"+index).text(result.idDocNum);			
		$("#curIdDocNumFH"+index).val(result.idDocNum);
		$("#curCustNo"+index).text(result.bomCustNum);	
		$("#curCustNoFH"+index).val(result.bomCustNum);
		$("#curAcctNo"+index).text(result.acctNum);	
		$("#curAcctNoFH"+index).val(result.acctNum);	
		$("#curSimIccid"+index).text(result.subSimIccid);		
		$("#curSimIccidFH"+index).val(result.subSimIccid);
		$("#curSimItemDesc"+index).text(result.subSimDesc);	
		$("#curSimItemDescFH"+index).val(result.subSimDesc);	
		$("#curSimItemCd"+index).text(result.subSimItemCd);
		$("#curSimItemCdFH"+index).val(result.subSimItemCd);
		$("#subSimType"+index).text(result.subSimType);
		$("#subSimTypeFH"+index).val(result.subSimType);
		$("#checkPass"+index).text(result.wsResult);
		$("#subBrandFH"+index).val(result.subBrand);
		$("#too1BrmOrderFH"+index).val(result.too1BrmOrder);
		$("#mnpOrderFH"+index).val(result.mnpOrder);

		if (result.oneNumber == true && result.too1BrmOrder == "BRM") {
			$("#oneNumberBRMAlert"+index).show();
		} else {
			$("#oneNumberBRMAlert"+index).hide();
		}
		
		if (result.oneNumber == true && result.too1BrmOrder == "TOO1") {
			$("#oneNumberTOO1Alert"+index).show();
		} else {
			$("#oneNumberTOO1Alert"+index).hide();
		}
		
		if (result.subBrand == "0") {
			$("#subBrand"+index).text("1O1O");
		} else if (result.subBrand == "1") {
			$("#subBrand"+index).text("csl.");
		}
		
		if (result.too1BrmOrder == "BRM") {
			if (result.mdoInd == "M") {
				$("#changeSimText" + index ).show();
				$("#changeSim" + index ).hide().attr("checked", true)
				$("#changeSimTab" + index ).show();		
			} else if (result.mdoInd == "D") {
				$("#changeSimText" + index ).show();
				$("#changeSim" + index ).show().attr("checked", true)
				$("#changeSimTab" + index ).show();
			} else {
				$("#changeSimText" + index ).show();
				$("#changeSim" + index ).show().attr("checked", false)
				$("#changeSimTab" + index ).hide();
			}
		} else {
			$("#changeSimText" + index ).hide();
			$("#changeSim" + index ).hide();
			$("#changeSimTab" + index ).hide();
		}
		
		if (result.mnpOrder == "Y") {
			$("#serviceRequestDateDiv" + index ).show();
		} else {
			$("#serviceRequestDateDiv" + index ).hide();
		}
		
		if (result.subActiveContract != null) {
			$("#curActiveContractparentProdType"+index).text(result.subActiveContract.parentProdType);
			$("#curActiveContractparentProdTypeFH"+index).val(result.subActiveContract.parentProdType);
			$("#curActiveContracttcDesc"+index).text(result.subActiveContract.tcDesc);
			$("#curActiveContracttcDescFH"+index).val(result.subActiveContract.tcDesc);
			$("#curActiveContractduration"+index).text(result.subActiveContract.duration);
			$("#curActiveContractdurationFH"+index).val(result.subActiveContract.duration);
			$("#curActiveContracteffStartDate"+index).text(result.subActiveContract.effStartDate);
			$("#curActiveContracteffStartDateFH"+index).val(result.subActiveContract.effStartDate);
			$("#curActiveContracteffEndDate"+index).text(result.subActiveContract.effEndDate);
			$("#curActiveContracteffEndDateFH"+index).val(result.subActiveContract.effEndDate);
			$("#penaltyDiv" + index ).show();	
		} else {
			$("#curActiveContractparentProdType"+index).text("N/A");
			$("#curActiveContractparentProdTypeFH"+index).val(null);
			$("#curActiveContracttcDesc"+index).text("");
			$("#curActiveContracttcDescFH"+index).val(null);
			$("#curActiveContractduration"+index).text("");
			$("#curActiveContractdurationFH"+index).val(null);
			$("#curActiveContracteffStartDate"+index).text("");
			$("#curActiveContracteffStartDateFH"+index).val(null);
			$("#curActiveContracteffEndDate"+index).text("");
			$("#curActiveContracteffEndDateFH"+index).val(null);
			$("#penaltyDiv" + index ).hide();	
		}
		
	} else {
		$("#serviceRequestDateDiv" + index ).hide();
		$("#changeSimTab" + index ).hide();
		$("#curProfileDiv"+index).hide();  
		$("#checkPass"+index).css("color", "red");
		$("#checkPass"+index).text(result.wsResult + " - "  + result.wsErrorMessage);
		$("#checkPassFH"+index).val(result.wsResult);
	}
	$("#too1AdminCharge"+index).val('${too1AdminCharge}');
	hideLoading();
}

function checkMigrateMsisdn(index){
	showLoading();
	var msisdn = $("#curMsisdn"+index).val();
	$.ajax({
		url : "migratecurrentlinemrtcheck.html?msisdn=" + msisdn,
		cache : false,
		dataType : "json",
		success : function(data) {
			setMigrateMsisdnResult(data, index);
		}
	}); 
}

function subInfoFunction(msisdn){
	url="<%=mobCosUrl%>natureselect/subscriberpreview.html?msisdn=" + msisdn + "&_al=new";
	window.open(url, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
}

function chgSimBoolClick(memberNum) {
	if ($('#changeSim' + memberNum).is(':checked')) {
		$("#changeSimTab" + memberNum).show();
	} else {
		$("#changeSimTab" + memberNum).hide();
	}
}

$(document).ready(function() {
	$("div#header").hide();
	$("div#footer").hide();
	
	if ( "<c:out value="${too1Alert}"/>" == 'Y'){
		var index = $('#checkMigrateMemberNum').val();
		var $scrollTo = $("#memberFormView"+index);	

		
		if ($scrollTo != null && $scrollTo != 'undefined'){
			var oldItemCd = $("#curSimItemCd"+index).val();
			var newItemCd = $("#curMsisdn"+index).val();
			
			$('html').animate({
				scrollTop: ($scrollTo.offset().top)
			}, {
			    duration: 100,
			    complete: function() {
			    	projectGoldTOO1Alert(oldItemCd,newItemCd);
			    }
			});
		}
	}
	
	/*<c:forEach items="${multiSimInfo.members}" var="member" varStatus="memberStatus">*/
		nextNewNumPage("${memberStatus.index}");
		$("input[name='newNumSelection_${memberStatus.index}']").change(function() {
		    $('#msisdn_${memberStatus.index}').val($(this).val());
		});
		
		$('#serviceRequestDatepicker${memberStatus.index}').attr('readonly', true);
		$('#serviceRequestDatepicker${memberStatus.index}').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy',
			minDate : "+0",
			maxDate : "+100Y",
			yearRange : "0:+100"
		})
		
		$('#cutoverDateDatepicker_${memberStatus.index}').attr('readonly', true);
		$('#cutoverDateDatepicker_${memberStatus.index}').datepicker({
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
		if ($('#cutoverDateDatepicker_${memberStatus.index}').val() != null) {
			if ($('#cutoverDateDatepicker_${memberStatus.index}').val().length == 0) {

				$('#cutoverDateStrCheckBox_${memberStatus.index}').attr('checked', true);
				cutoverDateStrCheckBoxClick("${memberStatus.index}");
			}
		}
		$('#cutoverDateDatepicker_${memberStatus.index}').focus(function(){
			//alert('cutoverDateDatepickeron change call');
			$('#cutoverDateStrCheckBox_${memberStatus.index}').attr('checked', false);

		});
		
		if ($("#custName_${memberStatus.index}").val() == "Prepaid Sim") {
			$('#prepaidCheckBox_${memberStatus.index}').attr('checked', true);
			$("#custName_${memberStatus.index}").val("Prepaid Sim").attr('readonly', true);
			$("#custDoc_${memberStatus.index}").html("Prepaid SIM No.");
			$("#prepaidTab_${memberStatus.index}").show();
		} else {
			$("#prepaidTab_${memberStatus.index}").hide();
		}
		
		futherMnpClick("${memberStatus.index}",null);
		
		$("input[id=memberOrderType${memberStatus.index}]").change(function() {
			var str = $(this).attr('id');
			var id = str.replace("memberOrderType", ""); 
			switch($(this).val()) {
				case 'MUPS01':									
			    	$("#MUP0102view" + id ).show();				
			    	$("#MUP0102NormalView" + id ).show();		
			    	$("#MUP0102NumberViewText" + id ).show();
	            	$("#MUP0102NumberView" + id ).show();
	            	$("#MUP0102SimView" + id ).show();
			    	$("#furtherMnp_" + id ).show();				
			    	$("#furtherMnp_text_" + id ).show();
			    	$("#FurtherMNP_div_" + id ).hide();			
			    	$("#MUP0102ActualUserView" + id ).show();	
			    	$("#MUP0304view" + id ).hide();				
			    	$("#noteMUP04" + id ).hide();				
			    	$("#vasForm" + id ).show();			
			    	$("#sameAsCustText_" + id ).show();	
	            	$("#sameAsCust_" + id ).show();	
	            	futherMnpClick(id,$(this).val());
		            break;
		        case 'MUPS04':									
	            	$("#MUP0102view" + id ).hide();				
	            	$("#MUP0102NormalView" + id ).hide();	
	            	$("#furtherMnp_" + id ).hide();			
	            	$("#furtherMnp_text_" + id ).hide();
	            	$("#FurtherMNP_div_" + id ).hide();		
	            	$("#MUP0102ActualUserView" + id ).hide();	
	            	$("#MUP0304view" + id ).show();				
	            	$("#noteMUP04" + id ).show();		
	            	$("#serviceRequestDateDiv" + index ).hide();
	            	$("#vasForm" + id ).show();					
	            	$("#curProfileDiv"+id).hide() 
	            	$("#sameAsCustText_" + id ).hide();	
	            	$("#sameAsCust_" + id ).hide();	
					break;
				case 'MUPS99':									
	            	$("#MUP0102view" + id ).hide();				
	            	$("#MUP0102NormalView" + id ).hide();
	            	$("#furtherMnp_" + id ).hide();		
	            	$("#furtherMnp_text_" + id ).hide();
	            	$("#FurtherMNP_div_" + id ).hide();		
	            	$("#MUP0102ActualUserView" + id ).hide();	
	            	$("#MUP0304view" + id ).hide();			
	            	$("#noteMUP04" + id ).hide();				
	            	$("#vasForm" + id ).hide();		
	            	$("#sameAsCustText_" + id ).hide();	
	            	$("#sameAsCust_" + id ).hide();	
					break;  
				case 'MUPS05': 								
					$("#MUP0102view" + id ).show();		
	            	$("#MUP0102NormalView" + id ).show();		
	            	$("#MUP0102NumberViewText" + id ).hide();
	            	$("#MUP0102NumberView" + id ).hide();
	            	$("#MUP0102SimView" + id ).show();
	            	$("#furtherMnp_" + id ).hide();				
	            	$("#furtherMnp_text_" + id ).hide();
	            	$("#FurtherMNP_div_" + id ).show();		
	            	$("#MUP0102ActualUserView" + id ).hide();	
	            	$("#MUP0304view" + id ).hide();		
	            	$("#noteMUP04" + id ).hide();			
	            	$("#vasForm" + id ).show();	
	            	$("#sameAsCustText_" + id ).show();	
	            	$("#sameAsCust_" + id ).show();	
	            	futherMnpClick(id,$(this).val());
		            break; 
				default:
					break;
			}
		}); 
		$("input[id=memberOrderType${memberStatus.index}]:checked").trigger("change");
		
		$('#actualUserDobDatepicker_${memberStatus.index}').attr('readonly', true);
		$('#actualUserDobDatepicker_${memberStatus.index}').datepicker({
			changeMonth : true,
			changeYear : true,
			//showOn: "button",
			//buttonImage: "images/calendar.gif",
			//buttonImageOnly: true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "-100Y+1D",
			maxDate : "-1D", //Y is year, M is month, D is day 
			yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
			//yearRange: "1911:1993" //the range shown in the year selection box
	
		});
		
		sameAsCustClick("${memberStatus.index}");
		actualUserNoEmailFlagClick("${memberStatus.index}");
		setActualUserCompanyCustomerDisplay("${memberStatus.index}");
	/*</c:forEach>*/
	
	/*<c:if test='${not empty order.ocid }'>*/
		$(".members_msisdn").attr('disabled', true);
		$(".vasCheckBox").attr("disabled", true);
	/*</c:if>*/
});
</script>

<form:form method="POST" name="multiSimInfoForm" commandName="multiSimInfo">
	<input type="hidden" name="actionType" id="actionType"/>
	
	<!-- VAS Table -->
	<table border=0 width="100%" class="vasTable">
		<tr class="vasHeaderRow"><th>Items</th>
			<th>Monthly Payment</td>
			<!-- <th>Upfront Payment</td> -->
		</tr>
		<c:forEach items="${multiSimInfo.bundleItemList}" var="ptList" varStatus="ptRow">
			<tr class="vasRow"><td>${ptList.itemHtml}</td>
				<td class="pricing">
					<c:if test='${not empty ptList.itemRecurrentAmt && ptList.itemRecurrentAmt != 0}'>
						$<fmt:formatNumber value="${ptList.itemRecurrentAmt}" pattern="#,###.####" /> / month
					</c:if>
				</td>
				<%-- <td class="pricing">
					<c:if test='${not empty ptList.itemOnetimeAmt && ptList.itemOnetimeAmt != 0}'>
						$<fmt:formatNumber value="${ptList.itemOnetimeAmt}" pattern="#,###.####" />
					</c:if>
				</td> --%>
			</tr>
		</c:forEach>
	</table>
	<!-- END VAS Table -->
	
	<!-- Local/Roaming Data Alert Settings -->
	<table border=0 class="tablegrey" style="width: 100%">
		<tr class="vasHeaderRow">
			<td>
				<h3 class="table_title">
					<spring:message code="label.localRoamingDataAlertSettingTitle" />
				</h3>
				<table style="width: 100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
					<tr>
						<td>
							<form:checkbox path="pcrfMupAlert" />
							<spring:message code="label.pcrfMupAlert"/>							
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<!-- END Local/Roaming Data Alert Settings -->
	
	<br/>
	<br/>
	<b>Number of member lines: <font color="red">${multiSimInfo.memberCount}</font></b>
	<span class="contenttext_red"><b>(
		<label for="numTypeLabel">
			<pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${sessionCustomer.numType}"/>							
			& 
			<pccw-util:codelookup codeType="MIP_SIM_TYPE" codeId="${sessionCustomer.simType}" />									
		</label>								
	)</b></span>
	<br/>
	<br/>
	
	<c:forEach items="${multiSimInfo.members}" var="member" varStatus="memberStatus">
		<hr width ="100%" />
		<b>Member Line #${memberStatus.index + 1}</b>
		
		<!-- Mebmer Order Type Option -->
		<div id="memberOrderTypeForm${memberStatus.index}">
			<table border=0 width="100%">
				<tbody>
					<tr>
						<td width="50%">
							<label><form:radiobutton id="memberOrderType${memberStatus.index}" path="members[${memberStatus.index}].memberOrderType" value="MUPS01" />New Number</label>
					        <label><form:radiobutton id="memberOrderType${memberStatus.index}" path="members[${memberStatus.index}].memberOrderType" value="MUPS04" />Migrate Current Line</label>
					        <label><form:radiobutton id="memberOrderType${memberStatus.index}" path="members[${memberStatus.index}].memberOrderType" value="MUPS99" />DO NOT CREATE</label>
					        <label><form:radiobutton id="memberOrderType${memberStatus.index}" path="members[${memberStatus.index}].memberOrderType" value="MUPS05" />Direct MNP</label>	 	 
							<form:errors path="members[${memberStatus.index}].memberOrderType" cssClass="error" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- END Mebmer Order Type Option -->
		
		<!-- MUPS01 New Number/MUPS02 New Number + Further MNP -->
		<div id="MUP0102view${memberStatus.index}" style="display:none;">
			<table border=0 width="100%">
				<tr id="MUP0102NormalView${memberStatus.index}" class="numberInfo">
					
					<!-- @ MUP01/MUPS02 Number View -->
					<td class="tableItemTitle" id="MUP0102NumberViewText${memberStatus.index}">Mobile No:</td>
					<td class="tableItem" id="MUP0102NumberView${memberStatus.index}">
						
						<!-- @@ Channel ID 1 Retail/Channel ID 3 Premier -->
						<c:if test="${(channelId == 1) || (channelId == 3) || (channelId == 10) || (channelId == 11)}">
							<c:if test='${empty order.ocid }'>
								<form:input path="members[${memberStatus.index}].msisdn" maxlength="8" id="msisdn_${memberStatus.index}" cssClass='members_msisdn'/>
								<c:if test="${(channelId == 1) || (channelId == 10) || (channelId == 11)}">
									<c:url var="popupMrtSelectionUrl" value="/mrtselection.html">
										<c:param name="type" value="secondary"/>
										<c:param name="seq" value="${memberStatus.index}"/>
									</c:url>														
									<c:choose>
										<c:when test="${not empty orderDto.orderId}">																										
											<input type="button" name="newNumSearchBtn" id="newNumSearchBtn" onClick="mobOnChange('${popupMrtSelectionUrl}')" value="MRT Search"/>																
										</c:when>
										<c:otherwise>
											<input type="button" name="newNumSearchBtn" id="newNumSearchBtn" onClick="popUpMRTSelection('${popupMrtSelectionUrl}')" value="MRT Search"/>																
										</c:otherwise>
									</c:choose>	
									<span class="originalMrt" style="display:none ; color: blue">
										<c:if test="${not empty multiSimInfo.members[memberStatus.index].originalMsisdn}">
											Original inputted MRT: ${multiSimInfo.members[memberStatus.index].originalMsisdn}
										</c:if>
									</span>
								</c:if>										
								<form:hidden path="members[${memberStatus.index}].numType" id="numType_${memberStatus.index}"/>
							</c:if>
							<c:if test='${not empty order.ocid }'>
								${member.msisdn}
							</c:if>
							<br/>
							<form:errors path="members[${memberStatus.index}].msisdn" cssClass="error" />
							<form:errors path="members[${memberStatus.index}].numType" cssClass="error" />
						</c:if>
						<!-- END @@ Channel ID 1 Retail/Channel ID 3 Premier -->
						
						<!-- @@ Channel ID 2 Call Center/Channel ID 10 Direct Sales/Channel ID 11 Direct Sales Support -->
						<c:if test="${(channelId == 2)}">
							<c:if test='${empty order.ocid }'>
								<c:forEach items="${numList}" var="num" varStatus="numRow">
									<c:if test="${(numRow.index mod 5) == 0}">
										<div id="newNumPage_${memberStatus.index}_${fn:substringBefore(numRow.index / 5, '.')}" class="newNumPage_${memberStatus.index}">
									</c:if>
									<input type="radio" name="newNumSelection_${memberStatus.index}" value="${num[0]}" class="newNumSelection_${memberStatus.index}">${num[0]}
									<c:if test="${(numRow.index mod 5 == 4) || (numRow.last)}">
										</div>
									</c:if>
								</c:forEach>
								<form:input maxlength="8" path="members[${memberStatus.index}].msisdn" id="msisdn_${memberStatus.index}" cssClass='members_msisdn'/>
								<input type="button" onClick="javascript:nextNewNumPage(${memberStatus.index});" value="Next 5 Numbers" />
								<br/>
								<input type="checkbox" id="checkPatternMrt_${memberStatus.index}" onclick="patternMrtClick(${memberStatus.index})"/>Search MRT by pattern
								<br/>
								<div id="patternMrtDiv_${memberStatus.index}" style="display: none;">
									<table width="100%" border="0" cellspacing="1" class="contenttext">
										<tr>
											<td>Pattern</td>
											<td><input type="text" id="patternMrtSearchText_${memberStatus.index}" name="patternMrtSearchText_${memberStatus.index}" maxlength="8" /> 
												<input type="button" onclick="patternSearch(${memberStatus.index})" value="Search" />
											</td>
										</tr>
										<tr>
											<td colspan="3" bgcolor="#FFFFFF">
												<div id="patternResultDiv_${memberStatus.index}">
												</div>
											</td>
										</tr>		
									</table>
								</div>
							</c:if>
							<c:if test='${not empty order.ocid }'>
								${member.msisdn}
							</c:if>
							<br/>
							<form:errors path="members[${memberStatus.index}].msisdn" cssClass="error" />
							<form:errors path="members[${memberStatus.index}].numType" cssClass="error" />
						</c:if>
						<!-- END @@ Channel ID 2 Call Center/Channel ID 10 Direct Sales/Channel ID 11 Direct Sales Support -->
						
					</td>
					<!-- END @ MUP01/MUPS02 Number View -->
					
					<!-- @ MUP01/MUPS02 SIM View -->
					<td class="tableItemTitle" id="MUP0102SimView${memberStatus.index}">
						
						<!-- @@ Channel ID 2 Call Center -->
						<c:if test="${channelId == 2}">
							SIM Type:
							<td class="tableItem">
								<c:if test='${empty order.ocid }'>
									<form:select path="members[${memberStatus.index}].selectedSimItemId">
										<c:forEach items="${multiSimInfo.simItemList}" var="simItem" varStatus="simRow">
											<form:option value="${simItem.itemId}" label="${simItem.posItemCd} - ${simItem.itemHtml2}"/>
										</c:forEach>
									</form:select>
								</c:if>
								<c:if test='${not empty order.ocid }'>
									<c:forEach items="${multiSimInfo.simItemList}" var="simItem" varStatus="simRow">
										<c:if test="${simItem.itemId eq member.selectedSimItemId}">
											${simItem.posItemCd} - ${simItem.itemHtml2}
										</c:if>
									</c:forEach>
								</c:if>
								<br/>
								<form:errors path="members[${memberStatus.index}].selectedSimItemId" cssClass="error" />
							</td>
						</c:if>
						<!-- END @@ Channel ID 2 Call Center -->
						
						<!-- @@ X Channel ID 2 Call Center -->
						<c:if test="${channelId != 2}">
							SIM Number:
							<td class="tableItem">
								<c:if test='${empty order.ocid }'>
									<form:input maxlength="19" path="members[${memberStatus.index}].sim.iccid" id="msisdn_${memberStatus.index}"/>
								</c:if>
								<c:if test='${not empty order.ocid }'>
									${member.sim.iccid}
								</c:if>
								<br/>
								<form:errors path="members[${memberStatus.index}].sim.iccid" cssClass="error" /></td>
							</td>
						</c:if>
						<!-- END @@ X Channel ID 2 Call Center -->
						
					</td>
					<!-- END @ MUP01/MUPS02 SIM View -->
					
					<!-- @ MUP01/MUPS02 Further MNP -->
					<tr class="numberInfo">
						<td colspan=4>
							<form:checkbox path="members[${memberStatus.index}].mnpInd" id="furtherMnp_${memberStatus.index}" value="A" onclick="futherMnpClick(${memberStatus.index})"/><b id="furtherMnp_text_${memberStatus.index}">Further MNP</b>
							
							<!-- @@ Further MNP View -->
							<div id="FurtherMNP_div_${memberStatus.index}">
								<table width="100%">
									<tr class="mnpInfo">
									
										<!-- @@@ Further MNP Number -->
										<td class="tableItemTitle">Transfer Mobile No:</td>
										<td class="tableItem">
											<form:input path="members[${memberStatus.index}].mnpNumber" maxlength="8" id="mnpNumber_${memberStatus.index}"/>
											<br/>
											<form:errors path="members[${memberStatus.index}].mnpNumber" cssClass="error" />
											<spring:bind path="members[${memberStatus.index}].ignorePostpaidcheckbox">
												<c:choose>
													<c:when test="${status.error}">
														<form:checkbox path="members[${memberStatus.index}].ignorePostpaidcheckbox" />
														<span class="error"><form:errors path="members[${memberStatus.index}].ignorePostpaidcheckbox" cssClass="error" /> If you want to ignore it, please tick the checkbox and continue.</span>
													</c:when>
													<c:otherwise>
														<form:hidden path="members[${memberStatus.index}].ignorePostpaidcheckbox"/>
													</c:otherwise>
												</c:choose>
											</spring:bind>
										</td>
										<!-- END @@@ Further MNP Number -->
		
										<!-- @@@ Enable MNP BY Dennis -->
										<c:if test="${empty mipEnableMNPTicket or mipEnableMNPTicket eq 'Y'}">	
											<td class="tableItemTitle">MNP Ticket Number:</td>
											<td class="tableItem">
												<form:input path="members[${memberStatus.index}].mnpTicketNum" maxlength="10" id="mnpTicketNum_${memberStatus.index }" />
		  
												<!-- @@@@ C MNP Ticket ON 20160700 -->
												<a href="http://ecs.hkcsl.com/cts/cts_login.jsp?login=csimcts&passwd=csim" target="_blank" class="nextbutton3 auth_button" style="vertical-align: middle">Get C MNP Ticket</a>
												<br/>
												<span class="smalltextgray" style="font-weight:bold">Note: This is C-Web CTS system, for login, please consult C-system IT. It can only be accessed via intranet.</span>
												<!-- END @@@@ C MNP Ticket ON 20160700 -->
												
												<br/>
												<form:errors path="members[${memberStatus.index}].mnpTicketNum" cssClass="error"/>
											</td>
										</c:if>
										<!-- END @@@ Enable MNP BY Dennis -->
										
									</tr>
									<tr class="mnpInfo">
										
										<!-- @@@ Further MNP Cutover Date -->
										<td class="tableItemTitle">Cutover Date:</td>
										<td class="tableItem">
											<form:input path="members[${memberStatus.index}].mnpCutOverDate" maxlength="10" id="cutoverDateDatepicker_${memberStatus.index}"  />
											<!-- <input type="checkbox" id="cutoverDateStrCheckBox_${memberStatus.index}" onclick="cutoverDateStrCheckBoxClick(${memberStatus.index})"> TBC -->
											<br/>
											<form:errors path="members[${memberStatus.index}].mnpCutOverDate" cssClass="error" />
										</td>
										<!-- END @@@ Further MNP Cutover Date -->
										
										<!-- @@@ Further MNP Cutover Time -->
										<td class="tableItemTitle">Cutover Time:</td>
										<td class="tableItem">
											<form:select path="members[${memberStatus.index}].mnpCutOverTime" id="mnpCutOverTime_${memberStatus.index }">
												<form:option value="0" label="01:00-04:00" />
												<form:option value="1" label="12:00-14:00" />
											</form:select>
											<br/>
											<form:errors path="members[${memberStatus.index}].mnpCutOverTime" cssClass="error" />
										</td>
										<!-- END @@@ Further MNP Cutover Time -->
										
									</tr>
									<tr class="mnpInfo">
									
										<!-- @@@ Further MNP Customer Name -->
										<td class="tableItemTitle">Customer Name:</td>
										<td class="tableItem">
											<form:input path="members[${memberStatus.index}].mnpCustName" id="custName_${memberStatus.index}"/>
											<input type="checkbox" id="prepaidCheckBox_${memberStatus.index}" onclick="prepaidCheckBoxClick(${memberStatus.index})"> is Prepaid SIM
											<br/>
											<form:errors path="members[${memberStatus.index}].mnpCustName" cssClass="error" />
										</td>
										<!-- END @@@ Further MNP Customer Name -->
										
										<!-- @@@ Further MNP Customer Doc Number -->
										<td class="tableItemTitle" id="custDoc_${memberStatus.index}">Customer Doc Number:</td>
										<td class="tableItem"><form:input path="members[${memberStatus.index}].mnpDocNo" maxlength="20" />
											<br/>
											<form:errors path="members[${memberStatus.index}].mnpDocNo" cssClass="error" />
										</td>
										<!-- END @@@ Further MNP Customer Doc Number -->
										
									</tr>
								</table>
								
								<!-- @@@ Further MNP Prepaid SIM Declaration -->
								<table width="100%" border="0" cellspacing="1" class="contenttext" id="prepaidTab_${memberStatus.index}">
									<tr>
										<td>
											&nbsp;
											<form:errors path="members[${memberStatus.index}].prePaidSimDocInd" cssClass="error" />
										</td>
									</tr>
									<tr>
										<td>Declaration by Customer of Prepaid SIM Service user: (tick as appropiate)</td>
									</tr>
									<tr>
										<td>
											<form:radiobutton path="members[${memberStatus.index}].prePaidSimDocInd" value="Y"/>
											We are / I am the holder of the Cardholder Certificate for the Mobile Number, a copy of which is attached.
										</td>
									</tr>
									<tr>
										<td>
											<form:radiobutton path="members[${memberStatus.index}].prePaidSimDocInd" value="N"/>
											We / I have lost our / my Cardholder Certificate for the Prepaid SIM Service associated with the Mobile Number allocated to us / me by the DNO.
										</td>
									</tr>
								</table>
								<!-- END @@@ Further MNP Prepaid SIM Declaration -->
							
							</div>
							<!-- END @@ Further MNP View -->
						
						</td>
					</tr>
					<!-- END @ MUP01/MUPS02 Further MNP -->					
				
				</tr>
			</table>
			
		</div>
		<!-- END MUPS01 New Number/MUPS02 New Number + Further MNP -->

		<!-- MUPS04 Migrate Current Line (Without Subscriber) -->
		<div id="MUP0304view${memberStatus.index}" style="display:none;">
			
			<!-- @ MUPS04 Current Number Checker -->
			<table border=1 width="100%">
				<tr>
					<td width="60%">
						<b>Current Mobile No: </b>
						<form:input path="members[${memberStatus.index}].curMsisdn" id="curMsisdn${memberStatus.index}" maxlength="8"/>
						<input type="button" id="checkMigrateMsisdn${memberStatus.index}" onClick="checkMigrateMsisdn('${memberStatus.index}')" value="Check"/>
					</td>
					<td width="40%">
						<b>Check Result: </b>
						<span id="checkPass${memberStatus.index}" style="color:green"></span>
						<form:hidden path="members[${memberStatus.index}].checkPass" id="checkPassFH${memberStatus.index}"/>
						<form:errors path="members[${memberStatus.index}].checkResult" cssClass="error" />
					</td>
				</tr>
			</table>
			<!-- END @ MUPS04 Current Number Checker -->
			
			<!-- @ MUPS04 Service Request Date View -->
			<div id="serviceRequestDateDiv${memberStatus.index}" style="display:none;">
				<table width="100%">
					<tr>
						<td width="60%">
							<b>Service Request Date: </b>
							<form:input path="members[${memberStatus.index}].serviceRequestDate" id="serviceRequestDatepicker${memberStatus.index}" maxlength="10"/>
							<form:errors path="members[${memberStatus.index}].serviceRequestDate" cssClass="error" />
						</td>
						<td width="40%"></td>
					</tr>
				</table>
				<form:hidden path="members[${memberStatus.index}].too1BrmOrder" id="too1BrmOrderFH${memberStatus.index}"/>
				<form:hidden path="members[${memberStatus.index}].mnpOrder" id="mnpOrderFH${memberStatus.index}"/>
			</div>
			<!-- END @ MUPS04 Service Request Date View -->
	
			<!-- @ MUPS04 Current Customer Profile View -->
			<div id="curProfileDiv${memberStatus.index}">                                                          
				<div id="s_line_text">
					<b>Current Profile</b>
				</div>
				<table>
					<tr>
						<td width="5%"></td>
						<td width="25%"><b>Current Customer Name: </b></td>
						<td width="25%">
							<span id="curTitle${memberStatus.index}"></span> <span id="curLastName${memberStatus.index}"></span> <span id="curFirstName${memberStatus.index}"></span>
							<form:hidden path="members[${memberStatus.index}].curTitle" id="curTitleFH${memberStatus.index}"/>
							<form:hidden path="members[${memberStatus.index}].curLastName" id="curLastNameFH${memberStatus.index}"/>
							<form:hidden path="members[${memberStatus.index}].curFirstName" id="curFirstNameFH${memberStatus.index}"/>
						</td>
						
						<td width="20%"></td>
						<td width="20%" align="right">
							<a href="#" class="nextbutton" onClick="javascript:subInfoFunction($('#curMsisdn${memberStatus.index}').val());">Sub Info</a>
												
						</td>
					</tr>
					<tr>
						<td></td>
						<td><b>Current ID Doc Type: </b></td>
						<td>
							<span id="curIdDocType${memberStatus.index}"></span>
							<form:hidden path="members[${memberStatus.index}].curIdDocType" id="curIdDocTypeFH${memberStatus.index}"/>
						</td>
						<td><b>Current ID Doc No.: </b></td>
						<td>
							<span id="curIdDocNum${memberStatus.index}"></span>
							<form:hidden path="members[${memberStatus.index}].curIdDocNum" id="curIdDocNumFH${memberStatus.index}"/>
						</td>	
						<td></td>	
					</tr>
					<tr>
						<td></td>
						<td><b>BOM Customer No: </b></td>
						<td>
							<span id="curCustNo${memberStatus.index}"></span>
							<form:hidden path="members[${memberStatus.index}].curCustNo" id="curCustNoFH${memberStatus.index}"/>
						</td>
						<td><b>Account No: </b></td>
						<td>
							<span id="curAcctNo${memberStatus.index}"></span>
							<form:hidden path="members[${memberStatus.index}].curAcctNo" id="curAcctNoFH${memberStatus.index}"/>
						</td>	
						<td></td>	
					</tr>
					<tr>
						<td></td>
						<td><b>Current SIM Info: </b></td>
						<td colspan="4">
							<span id="curSimIccid${memberStatus.index}"></span> / <span id="curSimItemDesc${memberStatus.index}"></span> (<span id="curSimItemCd${memberStatus.index}"></span>) / <span id="subSimType${memberStatus.index}"></span>-SIM
							<form:hidden path="members[${memberStatus.index}].curSimIccid" id="curSimIccidFH${memberStatus.index}"/>
							<form:hidden path="members[${memberStatus.index}].curSimItemDesc" id="curSimItemDescFH${memberStatus.index}"/>
							<form:hidden path="members[${memberStatus.index}].curSimItemCd" id="curSimItemCdFH${memberStatus.index}"/>
							<form:hidden path="members[${memberStatus.index}].subSimType" id="subSimTypeFH${memberStatus.index}"/>
						</td>
					</tr>
					<tr style="display:none;">
						<td></td>
						<td><b>Brand: </b></td>
						<td>
							<span id="subBrand${memberStatus.index}"></span>
							<form:hidden path="members[${memberStatus.index}].subBrand" id="subBrandFH${memberStatus.index}"/>
						</td>
					</tr>
					<tr id="oneNumberBRMAlert${memberStatus.index}">
						<td></td>
						<td colspan="4">
							<span style="color:red; font-size: 10pt">
								The line has One Number Service line(s). After Brand Migration order completes (SRD effective), One number service will be auto deactivate by system (hourly job between 09:00 - 22:00), and advise customer to register One number e-SIMs service again.
							</span>
						</td>
					</tr>
					<tr id="oneNumberTOO1Alert${memberStatus.index}">
						<td></td>
						<td colspan="4">
							<span style="color:red; font-size: 10pt">
								The line has One Number Service line(s). After TOO1 / TOO2 order completes (SRD effective), One number service will be auto deactivate by system (hourly job between 09:00 - 22:00), and advise customer to register One number e-SIMs service again.
							</span>
						</td>
					</tr>
				</table>
				<div id="s_line_text">
					<b>Current Active Contract</b>
				</div>
				<table class="table_style1" style="width: 100%">
					<thead>
						<tr>
							<td width="5%"></td>
							<td width="10%"><b>Type</b></td>
							<td width="50%"><b>Contract Description</b></td>
							<td width="12%"><b>Duration</b></td>
							<td width="14%"><b>Start Date</b></td>
							<td width="14%"><b>End Date</b></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td width="5%"></td>
							<td width="10%"><span id="curActiveContractparentProdType${memberStatus.index}"></span></td>
							<td width="50%"><span id="curActiveContracttcDesc${memberStatus.index}"></span></td>
							<td width="12%"><span id="curActiveContractduration${memberStatus.index}"></span></td>
							<td width="14%"><span id="curActiveContracteffStartDate${memberStatus.index}"></span></td>
							<td width="14%"><span id="curActiveContracteffEndDate${memberStatus.index}"></span></td>
							<form:hidden path="members[${memberStatus.index}].curActiveContractparentProdType" id="curActiveContractparentProdTypeFH${memberStatus.index}"/>
							<form:hidden path="members[${memberStatus.index}].curActiveContracttcDesc" id="curActiveContracttcDescFH${memberStatus.index}"/>
							<form:hidden path="members[${memberStatus.index}].curActiveContractduration" id="curActiveContractdurationFH${memberStatus.index}"/>
							<form:hidden path="members[${memberStatus.index}].curActiveContracteffStartDate" id="curActiveContracteffStartDateFH${memberStatus.index}"/>
							<form:hidden path="members[${memberStatus.index}].curActiveContracteffEndDate" id="curActiveContracteffEndDateFH${memberStatus.index}"/>
						</tr>
						
							<tr id="penaltyDiv${memberStatus.index}">
								<td colspan='6'>
									<span class="contenttext_red">* above contract may trigger penalty under Transfer of Ownership</span>
									<label><form:radiobutton id="curPenaltyWaive${memberStatus.index}" path="members[${memberStatus.index}].curPenaltyWaiveInd" value="N" />Charge</label>
						   			<label><form:radiobutton id="curPenaltyWaive${memberStatus.index}" path="members[${memberStatus.index}].curPenaltyWaiveInd" value="Y" />Waive</label>
									<form:errors path="members[${memberStatus.index}].curPenaltyWaiveInd" cssClass="error" />
								</td>
							</tr>
						
					</tbody>
				</table> 	
				
				<br/>
				
				<table class="table_style1" style="width: 100%">
					<tr>
						<td>
							* Transfer of Ownership Admin Charge: $<fmt:formatNumber value="${too1AdminCharge}" pattern="#,###.####" />
							<form:hidden path="members[${memberStatus.index}].too1AdminCharge" id="too1AdminCharge${memberStatus.index}"/>
						</td>
					</tr>
					<tr>
						<td>
						   	<label><form:radiobutton id="too1AdminChargeInd${memberStatus.index}" path="members[${memberStatus.index}].too1AdminChargeInd" value="Charge" />Charge</label>
						   	<label><form:radiobutton id="too1AdminChargeInd${memberStatus.index}" path="members[${memberStatus.index}].too1AdminChargeInd" value="Waive" />Waive</label> 
							 - Reason:
							<form:select id="too1WaiveReasonCd${memberStatus.index}" path="members[${memberStatus.index}].too1WaiveReasonCd">
								<form:option value="OT0010" label="BR Exemption" />
								<form:option value="OT0020" label="Change to MUP 2nd" />
								<form:option value="OT0030" label="Customer Refuse to Pay" />
								<form:option value="OT0040" label="Free SIM program" />
								<form:option value="OT0050" label="Full Contract Prepayment" />
								<form:option value="OT0060" label="Goodwill" />
								<form:option value="OT0070" label="Others" />
								<form:option value="OT0080" label="Segment Tier Permit" />
								<form:option value="OT0090" label="Special case" />
								<form:option value="OT0100" label="WIN Premium" />
							</form:select>
							<form:errors path="members[${memberStatus.index}].too1AdminCharge" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td>
							<label id="changeSimText${memberStatus.index}"><form:checkbox id="changeSim${memberStatus.index}" path="members[${memberStatus.index}].chgSimBool" onclick="chgSimBoolClick(${memberStatus.index})" /><b>Change SIM ?</b></label>
						</td>
					</tr>
				</table>
				<br/>
			</div>
			<!-- END @ MUPS04 Current Customer Profile View -->
			
		</div>
		<!-- END MUPS04 Migrate Current Line (Without Subscriber) -->
		
		<div id="changeSimTab${memberStatus.index}" style="display:none;">
			<table>
				<tr>
					<td colspan="3">
						<span class="contenttext_red">
							(Note: Manual handle SIM Replacement order in POS-BOM by frontline/support)
						</span>
					</td>
				</tr>
				<tr>
					<c:if test="${channelId == 2}">
						<td style="text-align:right;">SIM Type:</td>
						<td>
							<form:select path="members[${memberStatus.index}].chgSimItemCd">
								<c:forEach items="${multiSimInfo.simItemList}" var="simItem" varStatus="simRow">
									<form:option value="${simItem.itemId}" label="${simItem.posItemCd} - ${simItem.itemHtml2}"/>
								</c:forEach>
							</form:select>
							<form:errors path="members[${memberStatus.index}].chgSimItemCd" cssClass="error" />
						</td>
					</c:if>
					<c:if test="${channelId != '2'}">
						<td style="text-align:right;">SIM No.:</td>
						<td>
							<form:input path="members[${memberStatus.index}].chgSimIccid" maxlength="19"/>
							<form:errors path="members[${memberStatus.index}].chgSimIccid" cssClass="error" />
						</td>
					</c:if>
				</tr>
				<tr>
					<td style="text-align:right;">Admin Charge:</td>
					<td>$0.0</td>
				</tr>
				<tr>
					<td style="text-align:right;">
						<label><form:radiobutton id="chgSimWaiveReasonRadio${memberStatus.index}" path="members[${memberStatus.index}].chgSimWaiveReasonRadio" value="Charge" />Charge</label>
					   	<label><form:radiobutton id="chgSimWaiveReasonRadio${memberStatus.index}" path="members[${memberStatus.index}].chgSimWaiveReasonRadio" value="Waive" />Waive</label> 
					</td>
					<td>
						 - Reason:
						<form:select id="chgSimWaiveReason${memberStatus.index}" path="members[${memberStatus.index}].chgSimWaiveReason">
							<form:option value="OT0010" label="BR Exemption" />
							<form:option value="OT0020" label="Change to MUP 2nd" />
							<form:option value="OT0030" label="Customer Refuse to Pay" />
							<form:option value="OT0040" label="Free SIM program" />
							<form:option value="OT0050" label="Full Contract Prepayment" />
							<form:option value="OT0060" label="Goodwill" />
							<form:option value="OT0070" label="Others" />
							<form:option value="OT0080" label="Segment Tier Permit" />
							<form:option value="OT0090" label="Special case" />
							<form:option value="OT0100" label="WIN Premium" />
						</form:select>
						<form:errors path="members[${memberStatus.index}].chgSimWaiveReason" cssClass="error" />
					</td>
				</tr>
			</table>
		</div>

		<!-- VAS Form -->
		<div id="vasForm${memberStatus.index}" style="display:none;">
			<table>
				<tr class="numberInfo">
					<td colspan=4>
						<div class="vasError">
							<spring:bind path="members[${memberStatus.index}].selectedVasItemList">
								<c:forEach items="${status.errorMessages}" var="error">
									${error}
									<br/>
								</c:forEach>
							</spring:bind>
						</div>
						<b><span onclick="toggleVAS(${memberStatus.index})" id="toggleVASbutton_${memberStatus.index}">[ + ]</span> Optional Value Added Services:</b>
						<div id="vasDiv_${memberStatus.index}" style="display:none">
							<table width="100%" class="vasTable">
								<tr class="vasHeaderRow"><th colspan=2>Items</th>
									<th>Monthly Payment</th>
									<!-- <th>Upfront Payment</th>  -->
								</tr>
								<c:forEach items="${multiSimInfo.optionalItemList}" var="ptList" varStatus="ptRow">
									<tr class="vasRow">
										<td>
											<form:checkbox path="members[${memberStatus.index}].selectedVasItemList" value="${ptList.itemId}" cssClass="vasCheckBox"/>
										</td>
										<td>
											${ptList.itemHtml}
											<c:if test="${param.showDetail eq 'Y'}">
												<br/>
												Brand: ${ptList.mipBrand} 
												<br/>
												SimType: ${ptList.mipSimType}
											</c:if>
										</td>
										<td class="pricing">
											<c:if test='${not empty ptList.itemRecurrentAmt && ptList.itemRecurrentAmt != 0}'>
												$<fmt:formatNumber value="${ptList.itemRecurrentAmt}" pattern="#,###.####" /> / month
											</c:if>
										</td>
										<!-- <td class="pricing">
											<c:if test='${not empty ptList.itemOnetimeAmt && ptList.itemOnetimeAmt != 0}'>
												$<fmt:formatNumber value="${ptList.itemOnetimeAmt}" pattern="#,###.####" />
											</c:if>
										</td>  -->
									</tr>
								</c:forEach>
							</table>
						</div>
					</td>
				</tr>
				<tr class="numberInfo">
					<td colspan=4>
						<form:checkbox path="members[${memberStatus.index}].sameAsCust" id="sameAsCust_${memberStatus.index}" onclick="sameAsCustClick(${memberStatus.index})"/><b id="sameAsCustText_${memberStatus.index}">Same as Registered User</b>
						<div id="actualUserDiv_${memberStatus.index}" style="display: block;">
							<table style="width: 100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
								<tr>
									<td width="30%">
										<div align="right">
											<spring:message code="label.idDocType"/>
											<span class="contenttext_red">*</span>
										</div>
									</td>
									
									<!-- Modify BY Eliot ON 20110609 -->
									<td width="70%" colspan="2">
										<div align="left">
											<c:if test="${workStatus != 'recall' || orderDto.busTxnType == 'DRAF'}">
												<form:select id="actualUserDocType_${memberStatus.index}" path="members[${memberStatus.index}].actualUserDTO.subDocType" onchange="setActualUserCompanyCustomerDisplay(${memberStatus.index})"  >
													<form:option value="HKID" label="HKID" />
													<form:option value="PASS" label="Passport" />
													<form:option value="BS" label="HKBR" /> <!-- Add BY Eliot ON 20110608 -->
												</form:select>
											</c:if>
											<c:if test="${workStatus == 'recall' && orderDto.busTxnType != 'DRAF'}">
												<form:select id="actualUserDocType_members[${memberStatus.index}].actualUserDTO" path="members[${memberStatus.index}].actualUserDTO.subDocType" onchange="setActualUserCompanyCustomerDisplay(${memberStatus.index})" disabled="true">
													<form:option value="HKID" label="HKID" />
													<form:option value="PASS" label="Passport" />
													<form:option value="BS" label="HKBR" /> <!-- Add BY Eliot ON 20110608 -->
												</form:select>
											</c:if>
										</div>						
									</td>
								</tr>
								<tr>
									<!-- Modify BY Athena ON 20131015 -->
									<td width="30%">
										<div align="right">
											<spring:message code="label.idDocNum"/>
											<span class="contenttext_red">*</span>
										</div>
									</td>
									<td width="70%" colspan="2">
										<c:if test="${workStatus != 'recall' || orderDto.busTxnType == 'DRAF'}">
											<form:input path="members[${memberStatus.index}].actualUserDTO.subDocNum" maxlength="30" />
										</c:if>
										<c:if test="${workStatus == 'recall' && orderDto.busTxnType != 'DRAF'}">
											<form:input path="members[${memberStatus.index}].actualUserDTO.subDocNum" maxlength="30" disabled="true"/>
										</c:if>
										<form:errors path="members[${memberStatus.index}].actualUserDTO.subDocNum" cssClass="error" /> 
										<form:errors path="members[${memberStatus.index}].actualUserDTO.subDocType" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td width="30%"></td>
									<td width="70%" colspan="2" class="smalltextgray">
										<strong>Note 1:
											<span class="contenttext_red">*</span> 
											If HKID, please enter the ID in A999999(A)/AA999999(A) format
										</strong>
									</td>
								</tr>
								<!-- Add BY Eliot ON 20110608 -->
								<tr>
									<td width="30%"></td>
									<td width="70%" colspan="2" class="smalltextgray">
										<strong>Note 2:
											<span class="contenttext_red">*</span> 
											If HKBR, please enter the BR in 12345678-123/12345678-ABC format
										</strong>
									</td>
								</tr>
								<tr>
									<td width="30%">
										<div align="right" id="actualUserCompanyNameDiv_${memberStatus.index}">
											<spring:message code="label.companyName"/>
											<span class="contenttext_red">*</span>
										</div>
									</td>
									<td width="70%" colspan="2">
										<div id="actualUserCompanyNameInputFiledDiv_${memberStatus.index}">
											<form:input id="actualUserCompanyName_${memberStatus.index}" path="members[${memberStatus.index}].actualUserDTO.subCompanyName" maxlength="40" /> 
											<form:errors path="members[${memberStatus.index}].actualUserDTO.subCompanyName" cssClass="error" />
										</div>
									</td>
								</tr>
								<tr>
									<td width="30%">
										<div align="right">
											<span id="actualUserTitleLabel">
												<spring:message code="label.title"/>
											</span>
											<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
												<span class="contenttext_red">*</span>
											</c:if>
											<c:if test="${channelId == '2'}">
												<span class="contenttext_red">*#</span>
											</c:if>
										</div>
									</td>
									<td colspan="2" width="70%">
										<form:select path="members[${memberStatus.index}].actualUserDTO.subTitle">
											<form:option value="NONE" label="Select" />
											<form:options items="${titleList}" />
										</form:select> 
										<form:errors path="members[${memberStatus.index}].actualUserDTO.subTitle" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td width="30%">
										<div align="right">
											<span id="actualUserName_${memberStatus.index}">
												<spring:message code="label.nameInEng"/>
											</span>
											<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
												<span class="contenttext_red">*</span>
											</c:if>
											<c:if test="${channelId == '2'}">
												<span class="contenttext_red">*#</span>
											</c:if>
										</div>
									</td>
									<td width="20%">
										<form:input path="members[${memberStatus.index}].actualUserDTO.subLastName" maxlength="40" />
									</td> 
									<td width="50%">
										<form:input path="members[${memberStatus.index}].actualUserDTO.subFirstName" maxlength="40" />
									</td>
								</tr>
								<tr>
									<td width="30%"></td>
									<td width="20%" class="contenttext">(<spring:message code="label.familyName"/></td>
									<td width="50%" class="contenttext" colspan="2">(<spring:message code="label.givenName"/>)</td>
								</tr>
								<tr>
									<td width="30%"></td>
									<td width="20%" class="contenttext"><form:errors path="members[${memberStatus.index}].actualUserDTO.subLastName" cssClass="error" /></td>	
									<td width="50%" class="contenttext"><form:errors path="members[${memberStatus.index}].actualUserDTO.subFirstName" cssClass="error" /></td>
								</tr>
								<tr>
									<td width="30%"></td>
									<td width="70%" colspan="2" ></td>
								</tr>
								<tr>
									<!-- Add BY Eliot ON 20110608 -->
									<td width="30%">
										<div align="right" id="actualUserDateOfBirthDiv_${memberStatus.index}">
											<spring:message code="label.dateOfBirth"/>
											<span class="contenttext_red">*</span>
										</div>
									</td>
									<td width="70%" colspan="2">
										<div id="actualUserDateOfBirthInputFieldDiv_${memberStatus.index}">
											<c:choose>
												<c:when test="${channelId != '10' and channelId != '11'}">						
													<form:input path="members[${memberStatus.index}].actualUserDTO.subDateOfBirthStr" maxlength="10" id="actualUserDobDatepicker_${memberStatus.index}" readonly="true" />
												</c:when>
												<c:otherwise>
													<form:input path="members[${memberStatus.index}].actualUserDTO.subDateOfBirthStr" maxlength="10" id="actualUserDob_${memberStatus.index}" readonly="false" />(Date format:dd/mm/yyyy)
												</c:otherwise>
											</c:choose>
											<form:errors path="members[${memberStatus.index}].actualUserDTO.subDateOfBirthStr" cssClass="error" />
										</div>
									</td>
								</tr>
								<tr>
									<td width="30%">
										<div align="right">
											<spring:message code="label.contactPhone"/>
											<c:if test="${channelId == '1' or channelId == '3'}">
												<span class="contenttext_red">*</span>
											</c:if>
											<c:if test="${channelId == '2'}">
												<span class="contenttext_red">*#</span>
											</c:if>
											<c:if test="${channelId == '10' or channelId == '11'}">
												<span class="contenttext_red">*</span>
											</c:if>
										</div>
									</td>
									<td width="70%" colspan="2">
										<form:input maxlength="8" path="members[${memberStatus.index}].actualUserDTO.subContactTel" />
										<form:errors path="members[${memberStatus.index}].actualUserDTO.subContactTel" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td width="30%">
										<div align="right">
											<spring:message code="label.emailAddr"/>
											<span class="contenttext_red">&nbsp;</span>
										</div>
									</td>
									<td width="70%" colspan="2">
										<form:input id="actualUserEmailAddr_${memberStatus.index}" path="members[${memberStatus.index}].actualUserDTO.subEmailAddr"  maxlength="64" size="40" /> 
										<form:errors path="members[${memberStatus.index}].actualUserDTO.subEmailAddr" cssClass="error" />
									</td>
								</tr>				
								<tr>
									<td width="30%"></td>
									<td width="70%" colspan="2" class="smalltextgray">
										<c:if test="${channelId == '1' or channelId == '3' or channelId == '10' or channelId == '11'}">
											<strong>
												Note: <span class="contenttext_red">*</span> Mandatory Field
											</strong>
										</c:if>
										<c:if test="${channelId == '2'}">
											<strong>
												Note 3: <span class="contenttext_red">*</span> Pre, Pend Order Mandatory Field
											</strong>
											<br>
											<strong>
												Note 4: <span class="contenttext_red">#</span> Draft Order Mandatory Field
											</strong>
										</c:if>					
									</td>
								</tr>
								<tr>
									<td width="30%">
										<div align="right"></div>
									</td>
									<td width="70%" colspan="2">
										&nbsp;
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br/>
	</c:forEach>
	<div class="buttonmenubox_R" id="buttonArea">
		<c:if test="${(not empty action) && (action eq 'amend')}">
			<a href="#" class="nextbutton3" onClick="javascript:formSubmit('amend');">AMEND ORDER</a>
		</c:if>
		<c:if test="${action ne 'amend'}">
			<a href="#" class="nextbutton3" onClick="javascript:formSubmit('save');">SAVE</a>
		</c:if>
	</div>
</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>