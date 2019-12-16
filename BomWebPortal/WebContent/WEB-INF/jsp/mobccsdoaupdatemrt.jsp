<%@ include file="/WEB-INF/jsp/header.jsp"
%><%@ page import="com.bomwebportal.mob.ccs.util.MobCcsSessionUtil"
%>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%> 
<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.min.css"
      rel="stylesheet" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-migrate-1.2.1.js"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="js/json2.min.js"></script>
<%@ include file="loadingpanel.jsp" %>
<%@ page import="com.bomwebportal.util.ConfigProperties"%>


<%
String mobCosUrl=ConfigProperties.getPropertyByEnvironment("mobcos.url.base");
%>

<pccw-util:codelookup var="mipEnableMNPTicketMap" codeType="MIP_SHOW_MNP_TICKET_BTN"  asEntryList="true" />
<c:set var="mipEnableMNPTicket" value="${mipEnableMNPTicketMap[0].key}" />
<style>
.ui-widget {
  font-size: 0.8em;
}
</style>
<script type="text/javascript">
/**
 * ONSITE_DOA case: bound srvReqDate / cutOverDate with deliveryDate
 */
var SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION = 1;
var MNP_REJECT = 2;
var ONSITE_DOA = 3;
var DOA = 4;
var SRD_EXP = 5;
var SALES_FOLLOW_UP = 6;

var CHANNEL_CD_SUPPORT = 101;
var CHANNEL_CD_SALES = 102;

var authorizeType = "";
// layout func
function trunc(date) {
	date.setHours(0);
	date.setMinutes(0);
	date.setSeconds(0);
	date.setMilliseconds(0);
	return date;
}
function getDate(amount) {
	var date = new Date();
	date.setDate(date.getDate() + amount);
	return trunc(date);
}
function addDate(date, amount) {
	var newDate = new Date(date.getTime());
	newDate.setDate(newDate.getDate() + amount);
	return trunc(newDate);
}
function isHKMobileNumValid(msisdn) {
	return msisdn.match(/^[35689][0-9]{7}$/) != null;
}
function prepaySimCustClicked(e) {
	// no change on fallout to sales
	// e == null not trigger from click
	if (e != null && $("#falloutToSales").is(":checked")) {
		e.preventDefault();
		return false;
	}
	
	if ($("#prepaySimCust").is(":checked")) {
		$("input[name=custName]").val("Prepaid Sim").attr("readonly", true);
		$(".docNumLabel").text("Prepaid Sim No.");
	} else {
		$("input[name=custName]").val("").attr("readonly", false);
		$(".docNumLabel").text("Old Customer Identity No.");
	}
}
function elementToText(el) {
	if ($(el).is("input")) {
		el.before($("<span/>").addClass("text").text(el.val())).hide();
	}
	if ($(el).is("select")) {
		el.before($("<span/>").addClass("text").text(el.find("option:selected").text())).hide();
	}
}
function disableUpdateSRD() {
	if ($(".blockNew").data("disabled")) {
		return;
	}
	$(".blockNew input[type=text]").each(function() {
		elementToText($(this));
	});
	$(".blockNew").data("disabled", true);
}
function disableUpdateMnp() {
	if ($(".blockMnp").data("disabled")) {
		return;
	}
	$(".blockMnp input[type=text]").each(function() {
		elementToText($(this));
	});
	//$(".blockMnp input[type=text]").attr("disabled", true);
	$(".blockMnp input[type=checkbox]").attr("disabled", true);
	$(".blockMnp select").attr("disabled", true);
	$(".blockMnp select").each(function() {
		elementToText($(this));
	});
	$(".blockMnp").data("disabled", true);
	if ($('#btnMnpTicket').length) {
		$("#btnMnpTicket").hide();
	}
	if ($('#btnCMnpTicket').length) {
		$("#btnCMnpTicket").hide();
	}
	if ($('#mnpExtendAuthorizeButton').length) {
		$('#mnpExtendAuthorizeButton').hide();
	}
	
}
function disableForm() {
	$("form input[type=text]").each(function() {
		elementToText($(this));
	});
	//$("form input[type=text]").attr("disabled", true);
	$("form input[type=checkbox]").attr("disabled", true);
	$("form select").attr("disabled", true);
	$("form select").each(function() {
		elementToText($(this));
	});
	$(".blockNew,.blockMnp").data("disabled", true);
	
	if ($('#btnMnpTicket').length) {
		$("#btnMnpTicket").hide();
	}
	if ($('#btnCMnpTicket').length) {
		$("#btnCMnpTicket").hide();
	}
	
	if ($('#mnpExtendAuthorizeButton').length) {
		$('#mnpExtendAuthorizeButton').hide();
	}
}
function resizePaymentMask() {
	var $blockMnp = $(".blockMnp");
	var $blockMnpH3 = $blockMnp.find("h3:first");
	var blockMnpMaskHeight = $blockMnp.innerHeight() - $blockMnpH3.outerHeight(true);
	var $blockNew = $(".blockNew");
	var $blockNewH3 = $blockNew.find("h3:first");
	var blockNewMaskHeight = $blockNew.innerHeight() - $blockNewH3.outerHeight(true);
	
	if ($.browser.msie && $.browser.version.substr(0,1) < 7) {
		blockMnpMaskHeight -= ($blockMnpH3.outerHeight(true) - $blockMnpH3.innerHeight());
		blockNewMaskHeight -= ($blockNewH3.outerHeight(true) - $blockNewH3.innerHeight());
	}
	
	$(".blockMnp_mask").css({ "width": $blockMnp.width(), "height": blockMnpMaskHeight });
	$(".blockNew_mask").css({ "width": $blockNew.width(), "height": blockNewMaskHeight });
}
function falloutToSalesChange() {
	var $inputs = $("#updateForm input[type=text], #updateForm input[type=checkbox]");
	$inputs.attr("readonly", $("#falloutToSales").is(":checked"));
	
	if ($("#falloutToSales").is(":checked")) {
		$("#updateForm")[0].reset();
		prepaySimCustClicked();
		$(".error").hide();
		// keep the fallToSales box checked after reset form
		$("#falloutToSales").attr({"checked": true, "readonly": false});
		$(".note_falloutToSales").show();
		//$("#updateForm select").hide();
		$(".blockNew_mask, .blockMnp_mask").show();
		resizePaymentMask();
	} else {
		$(".note_falloutToSales").hide();
		//$("#updateForm select").show();
		$(".blockNew_mask, .blockMnp_mask").hide();
	}
}
function sameDate(date, date2) {
	return date.getFullYear() == date2.getFullYear() 
			&& date.getMonth() == date2.getMonth()
			&& date.getDate() == date2.getDate();
}
// backend func
function getAmendScenario(orderStatus, reasonCd, ocidStatus) {
	if (orderStatus == "02") {
		return SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION;
	}
	if (orderStatus == "99") {
		if (reasonCd != null) {
			if (reasonCd == "J014") {
				return SALES_FOLLOW_UP;
			}
			if (reasonCd.substring(0, 1) == "J") {
				return MNP_REJECT;
			}
			if (reasonCd == "G002" || reasonCd == "G003") {
				return ONSITE_DOA;
			}
			if (reasonCd == "N002" || reasonCd == "N003") {
				return DOA;
			}
			if (reasonCd == "K001") {
				return SRD_EXP;
			}
		}
	}
	return 0;
}
function getTeam() {
	var team = "<c:out value="${team}"/>";
	switch (team) {
	case "SUPPORT":
		return CHANNEL_CD_SUPPORT;
	case "SALES":
		return CHANNEL_CD_SALES;
	}
	return 0;
}
function isMnpWindowFrozen(mnpInd, currentCutOverDate) {
	var mnpWindowFrozen = false;
	switch (mnpInd) {
	case "N":
		break;
	case "Y":
	case "A":
		var dayDiff = parseInt((currentCutOverDate - getDate(0)) / (24 * 60 * 60 * 1000), 10);
		mnpWindowFrozen = (dayDiff == 1);
		break;
	}
	return mnpWindowFrozen;
}
function allowMnpWindowChange(orderStatus, reasonCd, ocidStatus, channelCd, mnpInd, currentCutOverDate) {
	var amendScenario = getAmendScenario(orderStatus, reasonCd, ocidStatus);
	var team = getTeam();
	var mnpWindowChange = true;
	switch (mnpInd) {
	case "N":
		mnpWindowChange = false;
		break;
	case "Y":
	{

		switch (team) {
		case CHANNEL_CD_SUPPORT:
		{
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case DOA:
				if (isMnpWindowFrozen(mnpInd, currentCutOverDate)) {
					mnpWindowChange = false;
				}
				break;
			case MNP_REJECT:
			case ONSITE_DOA:
			case SRD_EXP:
			case SALES_FOLLOW_UP:
				break;
			}
		}
			break;
		case CHANNEL_CD_SALES:
		{
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case SALES_FOLLOW_UP:
				if (isMnpWindowFrozen(mnpInd, currentCutOverDate)) {
					mnpWindowChange = false;
				}
				break;
			case MNP_REJECT:
			case ONSITE_DOA:
			case DOA:
			case SRD_EXP:
				break;
			}
		}
			break;
		}
	}
		break;
	case "A":
	{
		switch (team) {
		case CHANNEL_CD_SUPPORT:
		{
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case DOA:
				mnpWindowChange = false;
				break;
			case MNP_REJECT:
			case ONSITE_DOA:
			case SRD_EXP:
			case SALES_FOLLOW_UP:
				break;
			}
		}
			break;
		case CHANNEL_CD_SALES:
		{
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case SALES_FOLLOW_UP:
				mnpWindowChange = false;
				break;
			case MNP_REJECT:
			case ONSITE_DOA:
			case DOA:
			case SRD_EXP:
				break;
			}
		}
			break;
		}
	}
		break;
	}
	return mnpWindowChange;	
}
function allowUpdateSRD(orderStatus, reasonCd, ocidStatus, channelCd, mnpInd) {
	var amendScenario = getAmendScenario(orderStatus, reasonCd, ocidStatus);
	var team = getTeam();
	if (amendScenario == 0) {
		return false;
	}
	if (team == 0) {
		return false;
	}
	switch (mnpInd) {
	case "N":
	case "A":
	{
		switch (team) {
		case CHANNEL_CD_SUPPORT:
		{
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case ONSITE_DOA:
			case DOA:
			case SRD_EXP:
				if (ocidStatus == "Pending") {
					return true;
				}
				break;
			case MNP_REJECT:
			case SALES_FOLLOW_UP:
				// F&S pass to SALES
				break;
			}
		}
			break;
		case CHANNEL_CD_SALES:
		{
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case SALES_FOLLOW_UP:
				if (ocidStatus == "Pending") {
					return true;
				}
				break;
			case MNP_REJECT:
			case ONSITE_DOA:
			case DOA:
			case SRD_EXP:
				// SALES not allow to process
				break;
			}
		}
			break;
		}
	}
		break;
	case "Y":
		return false;
	}
	return false;
}
function allowUpdateMnp(orderStatus, reasonCd, ocidStatus, channelCd, mnpInd, cutOverDate, bomActivated) {
	var amendScenario = getAmendScenario(orderStatus, reasonCd, ocidStatus);
	var team = getTeam();
	if (amendScenario == 0) {
		return false;
	}
	if (team == 0) {
		return false;
	}
	switch (mnpInd) {
	case "N":
		return false;
	case "Y":
		if (ocidStatus == "Pending") {
			switch (team) {
			case CHANNEL_CD_SUPPORT:
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
					return !isMnpWindowFrozen(mnpInd, cutOverDate)
				case MNP_REJECT:
				case ONSITE_DOA:
				case DOA:
				case SRD_EXP:
					return true;
				case SALES_FOLLOW_UP:
					// FS not allow to process
					break;
				}
				break;
			case CHANNEL_CD_SALES:
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case SALES_FOLLOW_UP:
					return true;
				case MNP_REJECT:
				case ONSITE_DOA:
				case DOA:
				case SRD_EXP:
					// SALES not allow to process
					break;
				}
				break;
			}
		}
		break;
	case "A":
	{
		if (cutOverDate < (new Date()) && bomActivated) {
			// when order's cutover date is before today, and mnp msisdn is activated in bom
			// no change is allowed
			return false;
		}
		switch (team) {
		case CHANNEL_CD_SUPPORT:
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case MNP_REJECT:
			case ONSITE_DOA:
			case DOA:
			case SRD_EXP:
				return true;
			case SALES_FOLLOW_UP:
				// FS not allow to process
				break;
			}
			break;
		case CHANNEL_CD_SALES:
			switch (amendScenario) {
			case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
			case SALES_FOLLOW_UP:
				return true;
			case MNP_REJECT:
			case ONSITE_DOA:
			case DOA:
			case SRD_EXP:
				// SALES not allow to process
				break;
			}
			break;
		}
	}
		break;
	}
	return false;
}
function isCutOverTimeValid(cutOverTime) {
	switch (cutOverTime) {
	case "0":
	case "1":
		return true;
	}
	return false;
}
function isMnpTicketNumValid(mnpTicketNum, cutOverDate, cutOverTime) {
	if ($.trim(mnpTicketNum).length == 0 || mnpTicketNum.match(/^[0-9]{10}$/) == null) {
		$(".error_mnpTicketNum").text("Requires MNP Ticket Number in 10 digits").show();
		return false;
	}
	var beginWith = $.datepicker.formatDate("mmdd", cutOverDate);
	var char5th = cutOverTime;
	if (mnpTicketNum.substring(0, 4) != beginWith) {
		$(".error_mnpTicketNum").text("Requires MNP Ticket Number starts with " + beginWith).show();
		return false;
	}
	if (mnpTicketNum.charAt(4) != char5th) {
		$(".error_mnpTicketNum").text("Requires MNP Ticket Number's 5th digit = " + char5th).show();
		return false;
	}
	return true;
}
function validateNewNumber(amendScenario, team) {
	var now = new Date();
	var doaDeliveryDate = ($("input[name=doaDeliveryDate]").length == 0 || $("input[name=doaDeliveryDate]").val().length == 0) ? null : $.datepicker.parseDate("dd/mm/yy", $("input[name=doaDeliveryDate]").val());
	var appDate = $.datepicker.parseDate("dd/mm/yy", "<fmt:formatDate pattern="dd/MM/yyyy" value="${orderDto.appInDate}"/>");
	var srvReqDate = trunc($("#serviceReqDateDatepicker").val().length == 0 ? null : trunc($("#serviceReqDateDatepicker").datepicker("getDate")));
	var currentSrvReqDate = trunc($.datepicker.parseDate("dd/mm/yy", $("#previousMrtUiServiceReqDateStr").val()));
	if (srvReqDate == null) {
		$(".error_serviceReqDate").text("Requires Service Request Date").show();
		return;
	}
	// srvReqDate range
	var minDate = null;
	var maxDate = null;
	switch (amendScenario) {
	case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
	case SRD_EXP:
	case DOA:
	case SALES_FOLLOW_UP:
		minDate = getDate(now.getHours() < 17 ? 0 : 1);
		maxDate = addDate(appDate, 90);
		break;
	case ONSITE_DOA:
		minDate = getDate(1);
		maxDate = addDate(appDate, 90);
		break;
	case MNP_REJECT:
	default:
	}
	if (amendScenario == ONSITE_DOA && minDate != null && doaDeliveryDate != null) {
		var minSrvReqDate = addDate(doaDeliveryDate, 1);
		if (minSrvReqDate > minDate) {
			minDate = minSrvReqDate;
		}
	}
	// check minDate / maxDate
	switch (amendScenario) {
	case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
	case ONSITE_DOA:
	case SRD_EXP:
		if (maxDate < trunc(now) || maxDate < minDate) {
			$(".error_serviceReqDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
		} else if (srvReqDate < minDate || srvReqDate > maxDate) {
			$(".error_serviceReqDate").text("Requires Service Request Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
		}
		break;
	case DOA:
		if (sameDate(currentSrvReqDate, srvReqDate)) {
			if (currentSrvReqDate < trunc(now)) {
				// not allow back day
				$(".error_serviceReqDate").text("Current Service Request Date is a back date").show();
			}
		} else {
			// changed date -> need to bound by minDate
			if (maxDate < trunc(now) || maxDate < minDate) {
				$(".error_serviceReqDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
			} else if (srvReqDate < minDate || srvReqDate > maxDate) {
				$(".error_serviceReqDate").text("Requires Service Request Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
			}
		}
		break;
	case SALES_FOLLOW_UP:
		// fallout from K001 / Jxxx
		if (maxDate < trunc(now) || maxDate < minDate) {
			$(".error_serviceReqDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
		} else if (srvReqDate < minDate || srvReqDate > maxDate) {
			$(".error_serviceReqDate").text("Requires Service Request Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
		}
		break;
	case MNP_REJECT:
	default:
		$(".error_serviceReqDate").text("[Unknown scenario]: cannot Service Request Date, please contact SB Support.").show();
	}
}
function validateMnp(amendScenario, team) {
	var now = new Date();
	var doaDeliveryDate = ($("input[name=doaDeliveryDate]").length == 0 || $("input[name=doaDeliveryDate]").val().length == 0) ? null : $.datepicker.parseDate("dd/mm/yy", $("input[name=doaDeliveryDate]").val());
	var appDate = $.datepicker.parseDate("dd/mm/yy", "<fmt:formatDate pattern="dd/MM/yyyy" value="${orderDto.appInDate}"/>");
	var falloutDate = ($("input[name=falloutDate]").length == 0 || $("input[name=falloutDate]").val().length == 0) ? null : $.datepicker.parseDate("dd/mm/yy", $("input[name=falloutDate]").val());
	var cutOverDate = trunc($("#cutOverDateDatepicker").val().length == 0 ? null : trunc($("#cutOverDateDatepicker").datepicker("getDate")));
	var currentCutOverDate = trunc($.datepicker.parseDate("dd/mm/yy", $("#previousMrtUiCutOverDateStr").val()));
	// mnpMsisdn
	if (!isHKMobileNumValid($("input[name=mnpMsisdn]").val())) {
		$(".error_mnpMsisdn").show();
		return;
	}
	// cutOverDate
	if (cutOverDate == null) {
		$(".error_cutOverDate").text("Requires Cutover Date").show();
		return;
	}
	// cutOverDate range
	var minDate = null;
	var maxDate = null;
	
	var smApprovalMnpExtendDays = ${smApprovalMnpExtendDays};
	var mnpExtendAuthInd = '${command.mnpExtendAuthInd}';

	switch (amendScenario) {
	case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
	case DOA:
		minDate = getDate(now.getHours() < 15 ? 2 : 3);
		maxDate = addDate(appDate, 90);
		if (mnpExtendAuthInd == 'Y'){
			maxDate = addDate(maxDate, smApprovalMnpExtendDays);
		}
		
		break;
	case MNP_REJECT:
		minDate = getDate(now.getHours() < 15 ? 2 : 3);
		//maxDate = addDate(appDate, 180);
		break;
	case SRD_EXP:
		minDate = getDate(now.getHours() < 15 ? 2 : 3);
		//maxDate = addDate(appDate, 180);
		break;
	case ONSITE_DOA:
		minDate = getDate(3);
		maxDate = addDate(appDate, 90);
		if (mnpExtendAuthInd == 'Y'){
			maxDate = addDate(maxDate, smApprovalMnpExtendDays);
		}
		break;
	case SALES_FOLLOW_UP:
		minDate = getDate(now.getHours() < 15 ? 2 : 3);
		maxDate = addDate(appDate, 90);
		if (mnpExtendAuthInd == 'Y'){
			maxDate = addDate(maxDate, smApprovalMnpExtendDays);
		}
		break;
	}
	if (amendScenario == ONSITE_DOA && minDate != null && doaDeliveryDate != null) {
		var minCutOverDate = addDate(doaDeliveryDate, 3);
		if (minCutOverDate > minDate) {
			minDate = minCutOverDate;
		}
	}
	// check minDate / maxDate
	switch (amendScenario) {
	case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
		switch (team) {
		case CHANNEL_CD_SUPPORT:
		{
			if (maxDate < trunc(now) || maxDate < minDate) {
				$(".error_cutOverDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
			} else if (cutOverDate < minDate || cutOverDate > maxDate) {
				$(".error_cutOverDate").text("Requires Cutover Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
			}
		}
			break;
		case CHANNEL_CD_SALES:
		{
			if (isMnpWindowFrozen($("input[name=mnpInd]").val(), currentCutOverDate)) {
				// Today+1 not allow to update MNP date
					// Today+1 not allow to update MNP date (disable fields and show a remarks "Not allow to update cutover date as MNP window frozen")
				if (!sameDate(cutOverDate, currentCutOverDate)) {
					$(".error_cutOverDate").text("Not allow to update CutOver Date as MNP window frozen").show();
				}
				if ($("select[name=cutOverTime] option:selected").val() != $("#previousMrtUiCutOverTime").val()) {
					$(".error_cutOverDate").text("Not allow to update CutOver Time as MNP window frozen").show();
				}
			} else {
				if (maxDate < trunc(now) || maxDate < minDate) {
					$(".error_cutOverDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
				} else if (cutOverDate < minDate || cutOverDate > maxDate) {
					$(".error_cutOverDate").text("Requires Cutover Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
				}
			}
		}
			break;
		}
		break;
	case ONSITE_DOA:
		if (maxDate < trunc(now) || maxDate < minDate) {
			$(".error_cutOverDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
		} else if (cutOverDate < minDate || cutOverDate > maxDate) {
			$(".error_cutOverDate").text("Requires Cutover Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
		}
		break;
	case MNP_REJECT:
		if (cutOverDate < minDate) {
			$(".error_cutOverDate").text("Requires Cutover Date on or after " + $.datepicker.formatDate("dd/mm/yy", minDate)).show();
		}
		break;
	case SRD_EXP:
		if (cutOverDate < minDate) {
			$(".error_cutOverDate").text("Requires Cutover Date on or after " + $.datepicker.formatDate("dd/mm/yy", minDate)).show();
		}
		break;
	case DOA:
		if (isMnpWindowFrozen($("input[name=mnpInd]").val(), currentCutOverDate)) {
			// Today+1 not allow to update MNP date
			if (!sameDate(cutOverDate, currentCutOverDate)) {
				$(".error_cutOverDate").text("Not allow to update CutOver Date as MNP window frozen").show();
			}
			if ($("select[name=cutOverTime] option:selected").val() != $("#previousMrtUiCutOverTime").val()) {
				$(".error_cutOverDate").text("Not allow to update CutOver Time as MNP window frozen").show();
			}
		} else {
			if (sameDate(currentCutOverDate, cutOverDate)) {
				if (currentCutOverDate < trunc(now)) {
					// now allow back day
					$(".error_cutOverDate").text("Current Cutover Date is a back date").show();
				}
			} else {
				// changed date -> need to bound by minDate
				if (maxDate < trunc(now) || maxDate < minDate) {
					$(".error_cutOverDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
				} else if (cutOverDate < minDate || cutOverDate > maxDate) {
					$(".error_cutOverDate").text("Requires Cutover Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and "+ $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
				}
			}
		}
		break;
	case SALES_FOLLOW_UP:
		if (isMnpWindowFrozen($("input[name=mnpInd]").val(), currentCutOverDate)) {
			// cutOverDate is frozen
			// Today+1 not allow to update MNP date
			if (!sameDate(cutOverDate, currentCutOverDate)) {
				$(".error_cutOverDate").text("Not allow to update CutOver Date as MNP window frozen").show();
			}
			if ($("select[name=cutOverTime] option:selected").val() != $("#previousMrtUiCutOverTime").val()) {
				$(".error_cutOverDate").text("Not allow to update CutOver Time as MNP window frozen").show();
			}
		} else {
			if (maxDate < trunc(now) || maxDate < minDate) {
				$(".error_cutOverDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
			} else if (cutOverDate < minDate || cutOverDate > maxDate) {
				$(".error_cutOverDate").text("Requires Cutover Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
			}
		}
		break;
	default:
		$(".error_cutOverDate").text("[Unknown scenario]: cannot check Cutover Date, please contact SB Support.").show();
	}
	// cutOverTime
	if (!isCutOverTimeValid($("select[name=cutOverTime] option:selected").val())) {
		$(".error_cutOverTime").show();
		return;
	}
	if ($(".error:visible").length > 0) {
		return;
	}
	// mnpTicketNum
	var enableMnp = "<c:out value="${mipEnableMNPTicket}"/>"; //dennis enable mnp
	if (enableMnp != "N"){
		if (!isMnpTicketNumValid($("input[name=mnpTicketNum]").val(), cutOverDate, $("select[name=cutOverTime] option:selected").val())) {
			return;	
		}
	}
	// custName
	if ($.trim($("input[name=custName]").val()).length == 0) {
		$(".error_custName").show();
	}
	// docNum
	if ($.trim($("input[name=docNum]").val()).length == 0) {
		$(".error_docNum").text($("#prepaySimCust").is(":checked") ? "Requires Prepaid Sim No." : "Requires Old Customer Identity No.").show();
	}
}
function validateNewNumberAndMnp(amendScenario, team) {
	var now = new Date();
	var doaDeliveryDate = ($("input[name=doaDeliveryDate]").length == 0 || $("input[name=doaDeliveryDate]").val().length == 0) ? null : $.datepicker.parseDate("dd/mm/yy", $("input[name=doaDeliveryDate]").val());
	var appDate = $.datepicker.parseDate("dd/mm/yy", "<fmt:formatDate pattern="dd/MM/yyyy" value="${orderDto.appInDate}"/>");	// msisdn
	var srvReqDate = trunc($("#serviceReqDateDatepicker").val().length == 0 ? null : trunc($("#serviceReqDateDatepicker").datepicker("getDate")));
	var currentSrvReqDate = trunc($.datepicker.parseDate("dd/mm/yy", $("#previousMrtUiServiceReqDateStr").val()));
	// srvReqDate
	if (srvReqDate == null) {
		$(".error_serviceReqDate").text("Requires Service Request Date").show();
		return;
	}
	// srvReqDate range, check only when srd can be amended
	if ($("#serviceReqDateDatepicker").is(":visible")) {
		var minDate = null;
		var maxDate = null;
		switch (amendScenario) {
		case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
		case SRD_EXP:
		case DOA:
		case SALES_FOLLOW_UP:
			minDate = getDate(now.getHours() < 17 ? 0 : 1);
			maxDate = addDate(appDate, 90);
			break;
		case ONSITE_DOA:
			minDate = getDate(1);
			maxDate = addDate(appDate, 90);
			break;
		case MNP_REJECT:
			break;
		}
		if (amendScenario == ONSITE_DOA && minDate != null && doaDeliveryDate != null) {
			var minSrvReqDate = addDate(doaDeliveryDate, 1);
			if (minSrvReqDate > minDate) {
				minDate = minSrvReqDate;
			}
		}
		// check minDate / maxDate
		switch (amendScenario) {
		case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
		case ONSITE_DOA:
		case SRD_EXP:
		case SALES_FOLLOW_UP:
			if (maxDate < trunc(now) || maxDate < minDate) {
				$(".error_serviceReqDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
			} else if (srvReqDate < minDate || srvReqDate > maxDate) {
				$(".error_serviceReqDate").text("Requires Service Request Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
			}
			break;
		case DOA:
			if (sameDate(currentSrvReqDate, srvReqDate)) {
				if (currentSrvReqDate < trunc(now)) {
					// not allow back day
					$(".error_serviceReqDate").text("Current Service Request Date is a back date").show();
				}
			} else {
				// changed date -> need to bound by minDate
				if (maxDate < trunc(now) || maxDate < minDate) {
					$(".error_serviceReqDate").text("Today already behind max amendment date " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
				} else if (srvReqDate < minDate || srvReqDate > maxDate) {
					$(".error_serviceReqDate").text("Requires Service Request Date between " + $.datepicker.formatDate("dd/mm/yy", minDate) + " and " + $.datepicker.formatDate("dd/mm/yy", maxDate)).show();
				}
			}
			break;
		case MNP_REJECT:
			// only for new number + mnp
			break;
		default:
			$(".error_serviceReqDate").text("[Unknown scenario]: cannot Service Request Date, please contact SB Support.").show();
		}
	}
	if ($(".error:visible").length > 0) {
		return;
	}
	// mnpMsisdn
	if (!isHKMobileNumValid($("input[name=mnpMsisdn]").val())) {
		$(".error_mnpMsisdn").show();
		return;
	}
	var cutOverDate = trunc($("#cutOverDateDatepicker").val().length == 0 ? null : trunc($("#cutOverDateDatepicker").datepicker("getDate")));
	var currentCutOverDate = trunc($.datepicker.parseDate("dd/mm/yy", $("#previousMrtUiCutOverDateStr").val()));
	// cutOverDate
	if (cutOverDate == null) {
		$(".error_cutOverDate").text("Requires Cutover Date").show();
		return;
	}
	// cutOverDate range
	var ccsFurtherMnpMaxDate = ${ccsFurtherMnpMaxDate};
	var cutOverDateMinDate = addDate(srvReqDate, 3);
	var cutOverDateMaxDate = addDate(srvReqDate, ccsFurtherMnpMaxDate);
	if (cutOverDate < cutOverDateMinDate || cutOverDate > cutOverDateMaxDate) {
		$(".error_cutOverDate").text("Requires Cutover Date between " + $.datepicker.formatDate("dd/mm/yy", cutOverDateMinDate) + " and " + $.datepicker.formatDate("dd/mm/yy", cutOverDateMaxDate)).show();
		return;
	}
	// cutOverTime
	if (!isCutOverTimeValid($("select[name=cutOverTime] option:selected").val())) {
		$(".error_cutOverTime").show();
	}
	if ($(".error:visible").length > 0) {
		return;
	}
	// mnpTicketNum
	var enableMnp = "<c:out value="${mipEnableMNPTicket}"/>"; //dennis enable mnp
	if (enableMnp != "N"){
		if (!isMnpTicketNumValid($("input[name=mnpTicketNum]").val(), cutOverDate, $("select[name=cutOverTime] option:selected").val())) {
			return;	
		}
	}
	
	// custName
	if ($.trim($("input[name=custName]").val()).length == 0) {
		$(".error_custName").show();
	}
	// docNum
	if ($.trim($("input[name=docNum]").val()).length == 0) {
		$(".error_docNum").text($("#prepaySimCust").is(":checked") ? "Requires Prepaid Sim No." : "Requires Old Customer Identity No.").show();
	}
}
function allowFalloutToSales(orderStatus, reasonCd, channelCd, mnpInd) {
	switch (mnpInd) {
	case "N":
		return false;
	case "Y":
	case "A":
		break;
	}
	var team = getTeam();
	if (team != CHANNEL_CD_SUPPORT) {
		return false;
	}
	if (orderStatus == "99") {
		if (reasonCd != null && reasonCd.substring(0, 1) == "J" && reasonCd != "J014") {
			return true;
		}
		if (reasonCd == "K001") {
			return true;
		}
	}
	return false;
}
function reloadcallback() {
	window.location.reload();
}
$(document).ready(function() {
	
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	// add close button
	$("#closeButton").click(function(e) {
		e.preventDefault();
		window.close();
		return false;
	});
	
	var orderStatus = "<c:out value="${orderDto.orderStatus}"/>";
	var reasonCd = "<c:out value="${orderDto.reasonCd}"/>";
	var ocidStatus = "<c:out value="${ocidStatus}"/>";
	var recordUpdated = "<c:out value="${param.recordUpdated}"/>";
	var mnpInd = $("input[name=mnpInd]").val();
	var channelCd = $("input[name=channelCd]").val();
	var statusAllowUpdateSRD = allowUpdateSRD(orderStatus, reasonCd, ocidStatus, channelCd, mnpInd);
	var statusAllowUpdateMnp = allowUpdateMnp(orderStatus, reasonCd, ocidStatus, channelCd, mnpInd, $("#previousMrtUiCutOverDateStr").length == 0 ? null : $.datepicker.parseDate("dd/mm/yy", $("#previousMrtUiCutOverDateStr").val()), $("#bomActivated").val() == "true");
	$("#updateButton").click(function(e) {
		e.preventDefault();
		$("#updateForm").submit();
		return false;
	});
	// cannot handle 1C2N
	/* if ($("input[name=chinaInd]").val() != "N") { 
		disableForm();
		return;
	} */
	// check if team allow to handle the form
	// bom order status complete 
	if (!(statusAllowUpdateSRD || statusAllowUpdateMnp)) {
		disableForm();
		var amendScenario = getAmendScenario(orderStatus, reasonCd, ocidStatus);
		switch (amendScenario) {
		case SALES_FOLLOW_UP:
			$(".blockFalloutToSales input[name=byPassValidation]").attr("checked", true);
			$(".blockFalloutToSales").show();
			break;
		}
		if (recordUpdated != "true" && $(".blockDelivery").length > 0) {
			$("#updateButton").show();
		}
	}
	if ($("input[name=custName]").val() == "Prepaid Sim") {
		$("#prepaySimCust").attr("checked", true);
	}
	$("#prepaySimCust").click(prepaySimCustClicked);
	$("#updateForm").submit(function(e) {
		$(".error").hide();
		// when bom order status is completed, no change in mrt info
		if (!(statusAllowUpdateSRD || statusAllowUpdateMnp)) {
			return true;
		}
		// fallout to sales, no update in mrt info
		if ($("#falloutToSales").is(":checked")) {
			return true;
		}
		var amendScenario = getAmendScenario(orderStatus, reasonCd, ocidStatus);
		var team = getTeam();
		/* if (mnpInd == "N") {
			validateNewNumber(amendScenario, team);
		} */
		/* switch (mnpInd) {
		case "N":
			validateNewNumber(amendScenario, team);
			break;
		case "Y":
			validateMnp(amendScenario, team);
			break;
		case "A":
			validateNewNumberAndMnp(amendScenario, team);
			break;
		} */
		if ($(".error:visible").length > 0) {
			e.preventDefault();
			return false;
		}
	});
	$("#updateButton").show();
	$("#serviceReqDateDatepicker").datepicker({
		changeMonth : true
		, changeYear : true
		, showButtonPanel : true
		, dateFormat : "dd/mm/yy"
		, yearRange : "c-2:c+2"
	});
	$("#cutOverDateDatepicker").datepicker({
		changeMonth : true
		, changeYear : true
		, showButtonPanel : true
		, dateFormat : "dd/mm/yy"
		, yearRange : "c-2:c+2"
	});

	// check if allow to update SRD
	if (!statusAllowUpdateSRD) {
		disableUpdateSRD();
	}
	if (statusAllowUpdateMnp) {
		// check if allow to update mnpWindowChange
		switch (mnpInd) {
		case "Y":
		{
			if (!allowMnpWindowChange(orderStatus, reasonCd, ocidStatus, channelCd, mnpInd, $.datepicker.parseDate("dd/mm/yy", $("#previousMrtUiCutOverDateStr").val()))) {
				elementToText($("input[name=cutOverDateStr]"));
				elementToText($("select[name=cutOverTime]"));
				$(".note_frozenWindow").show();
				$("#updateButton").hide();
			}
		}
			break;
		case "N":
		case "A":
			break;
		}
	} else {
		disableUpdateMnp();
		switch (mnpInd) {
		case "A":
			if ($.datepicker.parseDate("dd/mm/yy", $("#previousMrtUiCutOverDateStr").val()) < trunc(new Date()) && $("#bomActivated").val() == "true") {
				$(".note_bomActivated").show();
			}
			break;
		}
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
	
	
	// falloutToSales
	if (allowFalloutToSales(orderStatus, reasonCd, channelCd, mnpInd)) {
		$(".blockFalloutToSales").show();
	}
	$("#falloutToSales").change(falloutToSalesChange);
	if ($("#falloutToSales").is(":checked")) {
		falloutToSalesChange();
	}
	$(window).resize(resizePaymentMask);
});


function authorized() {
	$('#authDialog').dialog('close');
	var mnpInd = $("input[name=mnpInd]").val()
	if (authorizeType == 'AU19') {
		$("#mnpExtendNotAuthorizedDiv").hide();
		$("#mnpExtendAuthorizedDiv").show();		
		
		
		if (mnpInd == "Y"){
			$('#cutOverDateDatepicker').datepicker( "option", "maxDate", "${activeCutOverDateExtendedEnd}");	
		}
		
		$("input[name=mnpExtendAuthInd]").val("Y");
	}
}

</script>

<style type="text/css">
.updateForm { font-family: sans-serif; font-size: small; background-color: white; width: 99%; border: solid 1px #808080; }
.updateForm h2 { font-size: medium; background-color: #ABD078; color: white; padding: 5px 10px; margin: 0; font-weight: bold }
.updateForm h3 { font-size: medium; font-weight: bold; margin: 5px 0 }
.updateForm .block { padding-left: 10px; overflow: hidden }
.updateForm .blockHalf { float: left; display: inline-block; width: 48% }
.updateForm .label { width: 150px; padding-top: 5px; padding-left: 10px; float: left }
.updateForm .blockMnp .label { width: 170px; }
.updateForm .input { float: left; overflow-x: hidden }
.updateForm .input .text { margin-top: 5px; display: inline-block }
.updateForm .clear2 { clear: both }
.updateForm .textInput { width: 10em; float: left }
.updateForm .footer { margin: 5px 5px; display: block }
.updateForm .error { float: left; width: 600px }
.updateForm .blockHalf .error { width: 180px }
.updateForm .note { width: 180px; color: blue; display: inline-block }
.payment_mask { position: absolute; background-color: #f2f2f2; font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; z-index: 9999; opacity: 0.4; filter: alpha(opacity=40); }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:if test="${param.recordUpdated == 'true'}">
<h2 class="contenttextgreen" style="margin-top:0">Record updated</h2>
</c:if>

<form:form action="mobccsdoaupdatemrt.html" id="updateForm">
<c:if test="${sessionCcsOrderHash['doaDelivery'] != null}">
<c:set var="delivery" value="${sessionCcsOrderHash['doaDelivery']}"/>
<div class="updateForm">
	<h2>Delivery Note(Order ID: <c:out value="${command.orderId}" />)</h2>
	<div class="clear2"></div>
	<div class="block blockDelivery">
		<h3>Delivery Information</h3>
		<div class="label">Customer Name</div>
		<div class="input"><span class="text"><c:out value="${delivery.primaryContact.title}"/> <c:out value="${delivery.primaryContact.contactName}"/></span></div>
		<div class="clear2"></div>
		<div class="label">1st Contact No.</div>
		<div class="input"><span class="text"><c:out value="${delivery.primaryContact.contactPhone}"/> (Pre-visit Call/SMS)</span></div>
		<div class="clear2"></div>
		<div class="label">2nd Contact No.</div>
		<div class="input"><span class="text"><c:out value="${delivery.primaryContact.otherPhone}"/></span></div>
		<div class="clear2"></div>
		<div class="label">
		<c:choose>
		<c:when test="${delivery.primaryContact.idDocType == 'HKID'}">HKID</c:when>
		<c:when test="${delivery.primaryContact.idDocType == 'PASS'}">Passport</c:when>
		</c:choose>
		</div>
		<div class="input"><span class="text"><c:out value="${delivery.primaryContact.idDocNum}"/></span></div>
		<div class="clear2"></div>
		<div class="label">Delivery Address</div>
		<div class="input">
		<c:choose>
			<c:when test="${delivery.custAddressFlag2 == true}">
				<c:set var="displayDeliveryAddress" value=""/>
				<c:set var="displayDeliveryAddress">${delivery.address1}</c:set>
				<c:if test="${! empty delivery.address2}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.address2}</c:set></c:if>
				<c:if test="${! empty delivery.address3}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.address3}</c:set></c:if>
				<c:if test="${! empty delivery.districtDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.districtDesc}</c:set></c:if>
				<c:if test="${! empty delivery.areaDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.areaDesc}</c:set></c:if>
				
			</c:when>
			
			<c:otherwise>
			
				<c:set var="displayDeliveryAddress" value=""/>
				<c:if test="${! empty delivery.flat}"><c:set var="displayDeliveryAddress">Flat ${delivery.flat}</c:set></c:if>
				<c:if test="${! empty delivery.floor}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>Floor ${delivery.floor}</c:set></c:if>
				<c:if test="${! empty delivery.lotNum}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>Lot/Hse ${delivery.lotNum}</c:set></c:if>
				<c:if test="${! empty delivery.buildingName}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.buildingName}</c:set></c:if>
				<c:if test="${! empty delivery.streetNum}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.streetNum}</c:set></c:if>
				<c:if test="${! empty delivery.streetName}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.streetName}</c:set></c:if>
				<c:if test="${! empty delivery.streetCatgDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.streetCatgDesc}</c:set></c:if>
				<c:if test="${! empty delivery.sectionCode && delivery.sectionCode != 'ZZZZ' && ! empty delivery.sectionDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.sectionDesc}</c:set></c:if>
				<c:if test="${! empty delivery.districtDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.districtDesc}</c:set></c:if>
				<c:if test="${! empty delivery.areaDesc}"><c:set var="displayDeliveryAddress">${displayDeliveryAddress}<c:if test="${fn:length(displayDeliveryAddress) > 0}">, </c:if>${delivery.areaDesc}</c:set></c:if>
			
		    </c:otherwise>
		</c:choose> 
		<span class="text">${displayDeliveryAddress}</span>
		</div>
		<div class="clear2"></div>
		<div class="label">Delivery Date</div>
		<div class="input">
			<span class="text"><fmt:formatDate pattern="dd/MM/yyyy" value="${delivery.deliveryDate}"/></span>
			<input type="hidden" name="doaDeliveryDate" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${delivery.deliveryDate}"/>">
		</div>
		<div class="clear2"></div>
		<div class="label">Delivery Time</div>
		<div class="input">
		<span class="text">
		<c:choose>
		<c:when test="${delivery.deliveryTimeSlot == 'AM1'}">10:00-13:00</c:when>
		<c:when test="${delivery.deliveryTimeSlot == 'AM2'}">11:00-13:00</c:when>
		<c:when test="${delivery.deliveryTimeSlot == 'PM1'}">14:00-16:00</c:when>
		<c:when test="${delivery.deliveryTimeSlot == 'PM2'}">16:00-18:00</c:when>
		<c:when test="${delivery.deliveryTimeSlot == 'PM3'}">18:00-20:00</c:when>
		<c:when test="${delivery.deliveryTimeSlot == 'PM4'}">20:00-22:00</c:when>
		<c:when test="${delivery.deliveryTimeSlot == 'PM5'}">14:00-18:00</c:when>
		<c:when test="${delivery.deliveryTimeSlot == 'PM6'}">16:00-20:00</c:when>
		<c:when test="${delivery.deliveryTimeSlot == 'PM7'}">18:00-22:00</c:when>
		</c:choose>
		</span></div>
	</div>
	<div class="clear2"></div>
	<c:if test="${fn:startsWith(orderDto.reasonCd, 'G') != true || param.recordUpdated != 'true'}">
	<div class="block buttonmenubox_R">
		<c:if test="${fn:startsWith(orderDto.reasonCd, 'G') != true}">
		 <a href="#" class="nextbutton" onClick="javascript:window.open('<%=mobCosUrl%>doarequest.html?orderId=${delivery.orderId}&_al=new', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">DOA Request</a>
		</c:if>
		<c:if test="${param.recordUpdated != 'true'}">
		<a class="nextbutton" href="#" onclick="window.location='<c:url value="mobccsdoadelivery.html"><c:param name="orderId" value="${delivery.orderId}"/></c:url>'">Return DOA Delivery</a>
		</c:if>
		<div class="clear2"></div>
	</div>
	<div class="clear2"></div>
	</c:if>
</div>
</c:if>

<div class="updateForm">
	<h2>
	Update
	<c:choose>
	<c:when test="${command.mnpInd == 'N'}"> SRD </c:when>
	<c:when test="${command.mnpInd == 'Y'}"> MNP </c:when>
	<c:when test="${command.mnpInd == 'A'}"> SRD / MNP </c:when>
	</c:choose>
	information(Order ID: <c:out value="${command.orderId}" />)
	</h2>
	<div class="clear2"></div>
	<c:if test="${command.mnpInd == 'N' || command.mnpInd == 'A'}">
	<!-- New Mobile Number -->
	<div class="block blockNew<c:if test="${command.mnpInd == 'A'}"> blockHalf</c:if>">
		<h3>New Mobile Number</h3>
		<div class="payment_mask blockNew_mask" style="display:none"></div>
		<div class="clear2"></div>
		<div class="label">Mobile Number</div>
		<div class="input">
			<span class="text"><c:out value="${command.mobMsisdn}" /></span>
		</div>
		<div class="clear2"></div>
		<div class="label">Mobile Number Grade</div>
		<div class="input">
			<span class="text"><c:out value="${command.msisdnLvl}" /></span>
		</div>
		<div class="clear2"></div>
		<div class="label">Service Request Date</div>
		<div class="input">
			<form:input path="serviceReqDateStr" id="serviceReqDateDatepicker" readonly="true" cssClass="textInput" />
			<c:if test="${command.channelId == '66'}">
				<form:checkbox path="fulfillmentAuth" value="Y"/> By pass validation 
			</c:if> 

			<form:hidden path="previousMrtUi.serviceReqDateStr" id="previousMrtUiServiceReqDateStr" />
			<div class="clear2"></div>
			<form:errors path="serviceReqDateStr" cssClass="error" />
			<span class="error error_serviceReqDate" style="display:none"></span>
			<c:if test="${param.invalidSrvReqDate == 'true'}">
			<div class="clear2"></div>
			<span class="error error_invalidSrvReqDate">Requires Service Request Date after Doa Delivery Date +1 Day</span>
			</c:if>
		</div>
		<div class="clear2"></div>
	</div>
	</c:if>
	<c:if test="${command.mnpInd == 'Y' || command.mnpInd == 'A'}">
	<!-- MNP -->
	<div class="block blockMnp<c:if test="${command.mnpInd == 'A'}"> blockHalf</c:if>">
		<h3>MNP</h3>
		<div class="payment_mask blockMnp_mask" style="display:none"></div>
		<div class="clear2"></div>
		<div class="label">Transfer Mobile Number</div>
		<div class="input">
			<form:input path="mnpMsisdn" maxlength="16" cssClass="textInput" id="mnpMsisdn"/> <span class="text" style="padding-left:5px">to PCCW</span>
			<div class="clear2"></div>
			<form:errors path="mnpMsisdn" cssClass="error" />
			<span class="error error_mnpMsisdn" style="display:none">Requires 8 digits and starts with 3, 5, 6, 8 or 9</span>
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
		</div>
		
		<!-- dennis enable mnp -->
		<c:if test="${ empty mipEnableMNPTicket   or 	mipEnableMNPTicket eq 'Y'}">	
		<div class="clear2"></div>
		<div class="label">MNP Ticket Number</div>
		<div class="input">
			<form:input path="mnpTicketNum" maxlength="10" cssClass="textInput" id="mnpTicketNum" />
			
			<!-- 201607 C MNP Ticket -->
			<span id="btnCMnpTicket">
		   	<a href="http://ecs.hkcsl.com/cts/cts_login.jsp?login=csimcts&passwd=csim" target="_blank" class="nextbutton3 auth_button" style="vertical-align: middle">Get C MNP Ticket</a>
			
			<br><br>
			<span class="smalltextgray" style="font-weight:bold">Note: This is C-Web CTS system, for login, please consult C-system IT. It can only be accessed via intranet.</span>			
			</span>
			<!-- 201607 C MNP Ticket -->
			<div class="clear2"></div>
			<form:errors path="mnpTicketNum" cssClass="error" />
			<span class="error error_mnpTicketNum" style="display:none"></span>
		</div>
		</c:if>
		
		
		<div class="clear2"></div>
		<div class="label">Cutover Date</div>
		<div class="input" style="height:40px">
			<form:input path="cutOverDateStr" id="cutOverDateDatepicker" readonly="true" cssClass="textInput" />
			<c:if test="${command.channelId == '66'}">
				<form:checkbox path="fulfillmentAuth" value="Y"/> By pass validation 
			</c:if> 
			<form:hidden path="previousMrtUi.cutOverDateStr" id="previousMrtUiCutOverDateStr"/>
			
			<!-- DENNIS201606 -->
			<c:if test="${command.mnpInd == 'Y'}">
				<span id="mnpExtendSpan" style="text-align:right">
				<c:choose>
				<c:when test="${command.mnpExtendAuthInd == 'Y'}">
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
			</c:if>
			<form:hidden path="mnpExtendAuthInd" />
			
			<div class="clear2"></div>
			<span class="note note_frozenWindow" style="display:none;<c:if test="${command.mnpInd == 'Y'}">width:auto</c:if>">Not allow to update Cutover Date as MNP window frozen</span>
			<div class="clear2"></div>
			<form:errors path="cutOverDateStr" cssClass="error" />
			<span class="error error_cutOverDate" style="display:none"></span>
			<c:if test="${param.invalidCutOverDate == 'true'}">
			<div class="clear2"></div>
			<span class="error error_invalidCutOverDate">Requires Cutover Date after Doa Delivery Date +3 Days</span>
			</c:if>
		</div>
		<div class="clear2"></div>
		<div class="label">Cutover Time</div>
		<div class="input">
			<form:select path="cutOverTime" id="cutOverTime"><form:options items="${cutOverTimes}" /></form:select>
			<form:hidden path="previousMrtUi.cutOverTime" id="previousMrtUiCutOverTime"/>
			<div class="clear2"></div>
			<form:errors path="cutOverTime" cssClass="error" />
			<span class="error error_cutOverTime" style="display:none">Requires Cutover Time</span>
		</div>
		<div class="clear2"></div>
		<div class="label">Customer Name</div>
		<div class="input">
			<form:input path="custName" maxlength="81" readonly="${command.custName == 'Prepaid Sim'}" cssClass="textInput" cssStyle="width: 15em"/>
			<div class="clear2"></div>
			<form:errors path="custName" cssClass="error" />
			<span class="error error_custName" style="display:none">Requires Customer Name</span>
		</div>
		<div class="clear2"></div>
		<label for="prepaySimCust" class="label" style="width:160px">
			<input type="checkbox" id="prepaySimCust"<c:if test="${command.custName == 'Prepaid Sim'}"> checked="checked"</c:if>>Prepaid Sim Customer
		</label>
		<div class="clear2"></div>
		<div class="label docNumLabel">
			<c:choose>
			<c:when test="${command.custName == 'Prepaid Sim'}">Prepaid Sim No.</c:when>
			<c:otherwise>Old Customer Identity No.</c:otherwise>
			</c:choose>
		</div>
		<div class="input">
			<form:input path="docNum" maxlength="40" cssClass="textInput" cssStyle="width: 15em"/>
			<div class="clear2"></div>
			<form:errors path="docNum" cssClass="error" />
			<span class="error error_docNum" style="display:none"></span>
		</div>
		<div class="clear2"></div>
	</div>
	</c:if>
	<div class="clear2"></div>
	<div class="block blockFalloutToSales" style="display:none">
		<form:checkbox id="falloutToSales" path="byPassValidation" label="Route fallout order to sales" />
	</div>
	<div class="clear2"></div>
	<!-- 
	<span class="error error_noUpdateMrt" style="color: blue;display:none">No update on MRT as Order Status: <c:out value="${orderDto.orderStatus}"/>, Reason Code: <c:out value="${orderDto.reasonCd}"/> and BOM Order Status: <c:out value="${ocidStatus}"/></span>
	<div class="clear2"></div>
	 -->
	<form:errors path="orderId" cssClass="error" />
	<div class="clear2"></div>
	<div class="block footer">
		<span class="note note_falloutToSales" style="display:none; width: auto">Route to sales follow up, order status will updated to "Sales Follow up - MNP Reject/Activation Date Expired"] after clicking "Update" button</span>
		<div class="clear: both"></div>
		<span class="note note_bomActivated" style="display:none; width: auto">MNP Msisdn: <c:out value="${command.mnpMsisdn}"/> had been activated in BOM</span>
		<div class="clear: both"></div>
		<form:hidden path="goldenInd" />
		<form:hidden path="chinaInd" />
		<form:hidden path="mnpInd" />
		<form:hidden path="orderId" />
		<form:hidden path="channelCd" />
		<form:hidden path="bomActivated" />
	</div>
	<div class="clear2"></div>
	<div class="block buttonmenubox_R">
		<a class="nextbutton" id="updateButton" href="#" style="display:none">Update</a>
		<a class="nextbutton" id="closeButton" href="#">Close</a>
		<div class="clear2"></div>
	</div>
	<div class="clear2"></div>
</div>
</form:form>
<div id="authDialog"></div>
<c:if test="${param.recordUpdated == 'true'}">
<%
	// clean up session deliveryUI
	MobCcsSessionUtil.setSession(request, "doaDelivery", null); 
%>
</c:if>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>