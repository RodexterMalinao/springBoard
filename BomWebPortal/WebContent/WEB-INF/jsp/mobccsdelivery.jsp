<jsp:include page="/WEB-INF/jsp/header.jsp">
	<jsp:param name="enableOnloadJScript" value="Y" />
</jsp:include>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="loadingpanel.jsp" %>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobccsdelivery" />
</jsp:include>
<link href="js/jquery.autocomplete.css" rel="sty.whenlesheet" type="text/css" />
<script src="js/jquery-migrate-1.2.1.js"></script>
<script src="js/jquery.autocomplete.js"></script>

<script>
var isUrgent = false;
var orderType = 'ACQ';
var delMode = 'HD';
var hsInd = "<c:out value="${hsInd}"/>";
var hsItemCd;
<c:choose>
	<c:when test="${empty hsItemCd}">
		hsItemCd = 'NONE';
	</c:when>
	<c:otherwise>
		hsItemCd = "<c:out value="${hsItemCd}"/>";
	</c:otherwise>
</c:choose>
var delInd = 'NORMAL';
var payMthd;
<c:choose>
	<c:when test="${empty payMethodType}">
		payMthd = 'NONE';
	</c:when>
	<c:otherwise>
		payMthd = "<c:out value="${payMethodType}"/>";
	</c:otherwise>
</c:choose>	
var fsInd = 'Y';
var orderId;
<c:choose>
	<c:when test="${empty orderId}">
		orderId = 'NONE';
	</c:when>
	<c:otherwise>
		orderId = "<c:out value="${orderId}"/>";
	</c:otherwise>
</c:choose>
var appDate= "<c:out value="${appDate}"/>";
var delDate = null;
var phDateArray= [];
var mnpInd= "<c:out value="${mnpInd}"/>";
var minDate = null;
var maxDate = null;
var startDateRule;
var endDateRule;
var minimumTimeslot;
var errcode;
var errtext;
var sectionLoadAll = false;

function ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate) {
	return $.ajax({
		url : "/BomWebPortal/getCcsDeliveryDateRange.html?orderType=" + orderType + "&delMode=" + delMode + "&delInd=" + delInd + "&hsInd=" + hsInd + "&hsItemCd=" + hsItemCd + "&payMthd=" + payMthd + "&fsInd=" + fsInd + "&mnpInd=" + mnpInd + "&orderId=" + orderId + "&appDate=" + appDate,
		cache: false,
		dataType : 'json',
		timeout : 5000,
		error : function(XMLHttpRequest, textStatus, errorThrown, errtext) {
			alert(XMLHttpRequest.status + " - " + errorThrown);
		},
		success : function(msg) {
			if (msg.length == 0)
				alert('No data from getCcsDeliveryDateRange');
			msg = msg[0];
			if (msg.retValue != 0) {
				$("#deliveryDateRuleNo").html(msg.ruleNo);
				$("#deliveryDateStrError").html(msg.errText);
				$("#deliveryDatepicker").datepicker("option", "disabled", true);
				$("#deliveryDatepicker").val("");
				$("#timeSlotId").empty();
			} else {
				$("#deliveryDatepicker").datepicker("option", "disabled", false);
				if (msg.phDateString != null) {
					var tempArray = new Array();
					tempArray = msg.phDateString.split(",");
					for (var x=0; x<tempArray.length; x++) {
						phDateArray[x] = tempArray[x];			
					}			
				}
				if (msg.minDate != "") {
					minDate = msg.minDate;
				}
				if (msg.maxDate != "") {
					maxDate = msg.maxDate;
				}
				if (msg.startDateString != "") {
					startDateRule = msg.startDateString;
				}
				if (msg.endDateString != "") {
					endDateRule = msg.endDateString;
				}
				if (msg.timeslot != "") {
					minimumTimeslot = msg.timeslot;
				}
				ccsDeliveryDateHandler();
				dummyDeliveryDateIndClick();
			}
		}
	});
}

function ccsDeliveryTimeslotAjax (delMode, delInd, delDate, distNo, minDate, minimumTimeslot) {
	$.ajax({
		url : "/BomWebPortal/getCcsDeliveryTimeslot.html?delMode=" + delMode + "&delInd=" + delInd + "&delDate=" + delDate + "&distNo=" + distNo + "&minDate=" + minDate + "&minTimeslot=" + minimumTimeslot,
		cache: false,
		dataType : 'json',
		timeout : 5000,
		error : function(XMLHttpRequest, textStatus, errorThrown, errtext) {
			alert(XMLHttpRequest.status + " - " + errorThrown);
		},
		success : function(msg) {
			if (msg.retValue != null) {
				$("#deliveryTimeSlotError").html(msg.errText);
			}
			$("#timeSlotId").empty();
			$.each(eval(msg), function(i, item) {
				
				var isDisabled = (item.quotaRemain<=0)?"disabled='true'":"";
				if (item.timeslot == $("#deliveryTimeSlotId").val()) {
					$("<option value='" + item.timeslot + "' selected='selected' "+ isDisabled +">" + item.timeRange + " (" + item.quotaRemain+")" + "</option>").appendTo($("#timeSlotId"));
				} else {
					$("<option value='" + item.timeslot + "' "+isDisabled+ ">" + item.timeRange + " (" + item.quotaRemain + ")" + "</option>").appendTo($("#timeSlotId"));
				}
			});
		}
	});
	$("#timeSlotId").change(function() {
		$("#deliveryTimeSlotId").val($("#timeSlotId").val());
		$("#deliveryTimeSlotTextId").val($("#timeSlotId option:selected").html());
	});
}

function ccsDeliveryDateHandler() {
	$("#deliveryDatepicker").datepicker("option", "minDate", minDate);
	$("#deliveryDatepicker").datepicker("option", "maxDate", maxDate);	
	$("#deliveryDatepicker").datepicker("option", "beforeShowDay", highlightDays);			
}

function pad(n) {
	return (n<10 ? '0'+n : n);
}

function highlightDays(date) {
	var m = pad(date.getMonth()+1), d = pad(date.getDate()), y = date.getFullYear();
	if($.inArray(d + '/' + (m) + '/' + y,phDateArray) > -1 ) {
		return [false];
	}
	return [true];
	/* if (isUrgent) {
		var m = date.getMonth(), d = date.getDate(), y = date.getFullYear();
		if($.inArray(y + '-' + (m+1) + '-' + d,phDateArray) != -1) {
	        return [true];
	    }
		return [false];
	} else {
		var m = pad(date.getMonth()+1), d = pad(date.getDate()), y = date.getFullYear();
		if($.inArray(d + '/' + (m) + '/' + y,phDateArray) > -1 ) {
			return [false];
		}
		return [true];
	} */
}

function authorized() {
	$('#authDialog').dialog('close');
	$("input[name=urgentInd]").attr("checked", true).show();
	$(".auth_button").hide();
	isUrgent = true;
	delInd = 'URGENT';
	ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
		if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
			var minDateFormatted = minDate.replace(/\//g,"-");
			ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
		}
	});
}

$(function() {
	$('#deliveryDatepicker').datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		dateFormat : 'dd/mm/yy',
		onClose: function( selectedDate ) {
			if ($('#deliveryDatepicker').datepicker('getDate') != null) {
				var temp = $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate'));
				if ($("#districtCode").val() != null) {
					ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
						var minDateFormatted = minDate.replace(/\//g,"-");
						ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
					});
				}
			}
		}
	});
});

function dummyDeliveryDateIndClick() {
	if ($("input[name=dummyDeliveryDateInd]").is(":checked")) {
		$("#UrgentDeliveryDiv").hide();
		$("#deliveryDatepicker").val('');
		$("#deliveryDatepicker").datepicker("option", "disabled", true);
		$("#timeSlotId").empty();
		$("#timeSlotId").attr("disabled", true);
	} else {
		$("#UrgentDeliveryDiv").show();
		$("#deliveryDatepicker").datepicker("option", "disabled", false);
		$("#timeSlotId").attr("disabled", false);
	}
}	
	
	$(document).ready(function() {
		if ($("input[name=urgentInd]").is(':checked')) {
			delInd = 'URGENT';
		} else {
			delInd = 'NORMAL';
		}
		appDate = appDate.replace(/\//g,"-");
		ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
			if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
				var minDateFormatted = minDate.replace(/\//g,"-");
				ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
			}
		});
		
		if ('${errorDate}' != null && '${errorDate}' != "") {
			alert('${errorDate}');
			$("#buttonArea").hide();
		}
		
		$("input#quickSearch").autocomplete("addresslookup.html", {
			//minChars: 4,
			minChars : 3,
			delay : 600,
			selectFirst : false,
			max : 12,
			matchSubset : false,
			highlight : false,
			cacheLength: 0,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}
		}) .result(function(event, item) {
			//changeDeliveryTime($("#districtDesc").val());
			ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
				if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
					var minDateFormatted = minDate.replace(/\//g,"-");
					ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
				}
			});
		}); 
					
		var selectVal = $('input[name=deliveryInd]:checked').val();
				
		show(selectVal);
			
		if ($("input[name=urgentInd]").is(':checked')) {
			$("input[name=urgentInd]").show();
			$(".auth_button").hide();
			isUrgent = true;
		} else {
			$("#urgentDeliveryDiv").hide();
			$("input[name=urgentInd]").hide();
			isUrgent = false;
			$("#deliveryDatepicker").datepicker("option", "disabled", false);
			$(".auth_button").click(function(e) {
				e.preventDefault();
				var sURL = "mobccsauthorize.html?" + $.param({"action": "AU01", "orderId": "<c:out value="${orderDTO.orderId}" />"});
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
				
				/* e.preventDefault();
				// http://msdn.microsoft.com/en-us/library/ie/ms536759%28v=vs.85%29.aspx
				var sURL = "mobccsauthorize.html?" + $.param({"action": "AU01", "orderId": "<c:out value="${orderDTO.orderId}" />"});
				var vArguments = self;
				var sFeatures = "dialogHeight:230px;dialogLeft:0;dialogTop:0;dialogWidth:400px;resizable=yes;scroll=yes;status=no";
				window.showModalDialog(sURL, vArguments, sFeatures);
				return false; */
			});
		}
		
		$("#urgentIndId").click(function(e) {
			if ($("input[name=urgentInd]").is(':checked')) {
				isUrgent = true;
				delInd = 'URGENT';
				ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
					if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
						var minDateFormatted = minDate.replace(/\//g,"-");
						ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
					}
				});
			} else {
				var dis = "";
				isUrgent = false;
				if ($("#custAddressFlag1").is(':checked')|| $("input[name=custAddressFlag2]").is(':checked')) {
					//dis = $("#districtCodeSelect option:selected").text();
					dis = $("#districtCodeSelect").val();
				} else {
					//dis = $("#districtDesc").val();
					dis = $("#districtCode").val();
				}
				delInd = 'NORMAL';
				ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
					if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
						var minDateFormatted = minDate.replace(/\//g,"-");
						ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
					}
				});
			}
		});
		custAddressFlagClick2();
	});
	
	function changeDeliveryTime(distNo) {
		if ($("input[name=urgentInd]").is(':checked')) {
			delInd = 'URGENT';
			if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
				var minDateFormatted = minDate.replace(/\//g,"-");
				ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), distNo, minDateFormatted, minimumTimeslot);
			}
		} else {
			if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
				ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate)
				.done(function(){
				});
			}
		}
	}

	function show(which) {
		var editable = 'Y';

		//M - cashDiv, C - creditCardDiv, A-autoPayDiv
		if (which == 'D') {
			//alert('dddd click');
			$("#PickUpDiv").hide();
			$("#DeliveryByCourierDiv").show();
			$("#DeliveryAddressDiv").show();

			$("#DeliveryCentreDIV").hide();
			$("#DeliveryTimeSlotDIV").show();
			$("#DeliveryByCourierInfoDIV").show();

			//$("#recieveByThirdPartyInd1").attr('checked', true);

			//$("#commonDiv").show();
			//	$("#autoPayDiv").show();
			//$("#creditCardDiv").hide();
		}
		if (which == 'P') {
			$("#PickUpDiv").show();
			$("#DeliveryByCourierDiv").hide();
			$("#DeliveryAddressDiv").hide();

			$("#DeliveryCentreDIV").show();
			$("#DeliveryTimeSlotDIV").hide();
			$("#DeliveryByCourierInfoDIV").hide();
			$("#recieveByThirdPartyInd1").attr('checked', false);
			///	$("#commonDiv").show();
			//$("#autoPayDiv").hide();
			//$("#creditCardDiv").show();
		}

		//$("#mobccsdeliveryForm :input").attr("disabled", true);// (readonly/disable) all field

		recieveByThirdPartyIndClick();

	}

	function recieveByThirdPartyIndClick() {
		if ($("#recieveByThirdPartyInd1").is(":checked")) {
			$("#ThirdPartyInfoDIV").show();
		} else {
			$("#ThirdPartyInfoDIV").hide();
		}
	}
	
	//need to edit , add billing address assign
	function formSubmit() {
	
		if ($("input[name=custAddressFlag2]").is(":checked")) {
			//copy value
			$("#districtCode").val($("#districtCodeSelect").val());
			$("#districtDesc").val(
					$("#districtCodeSelect option:selected").text());
			$("#areaCode").val($("#areaCodeSelect").val());
			$("#areaDesc").val($("#areaCodeSelect option:selected").text());


		}else{
			if ($("#custAddressFlag1").is(":checked")) {
				//copy value
				$("#districtCode").val($("#districtCodeSelect").val());
				$("#districtDesc").val(
						$("#districtCodeSelect option:selected").text());
				$("#areaCode").val($("#areaCodeSelect").val());
				$("#areaDesc").val($("#areaCodeSelect option:selected").text());
				$("#sectionCode").val($("#sectionCodeSelect").val());
				$("#sectionDesc").val(
						$("#sectionCodeSelect option:selected").text());
				$("#streetCatgDesc").val(
						$("#streetCatgCode option:selected").text());
			}
		}
		
		$("#deliveryTimeSlotId").val($("#timeSlotId option:selected").val());
		$("#deliveryTimeSlotTextId").val($("#timeSlotId option:selected").text());
		
		/* if ($("#billingCustAddressFlag1").is(':checked')) {
			//copy value
			$("#billingDistrictCode")
					.val($("#billingDistrictCodeSelect").val());
			$("#billingDistrictDesc").val(
					$("#billingDistrictCodeSelect option:selected").text());
			$("#billingAreaCode").val($("#billingAreaCodeSelect").val());
			$("#billingAreaDesc").val(
					$("#billingAreaCodeSelect option:selected").text());
			$("#billingSectionCode").val($("#billingSectionCodeSelect").val());
			$("#billingSectionDesc").val(
					$("#billingSectionCodeSelect option:selected").text());
			$("#billingStreetCatgDesc").val(
					$("#billingStreetCatgCode option:selected").text());
		} */
		//document.mobccsdeliveryForm.submit();
		$("#mobccsdeliveryForm").submit();
	}

	//20110314, for set ,set readOnly of buildingName,streetNum, streetName readonly
	function custAddressFlagClick() {

		if (document.mobccsdeliveryForm.custAddressFlag1.checked) {
			document.mobccsdeliveryForm.buildingName.readOnly = false;
			document.mobccsdeliveryForm.streetNum.readOnly = false;
			document.mobccsdeliveryForm.streetName.readOnly = false;
			document.mobccsdeliveryForm.quickSearch.readOnly = true;
			document.mobccsdeliveryForm.quickSearch.value = "";
			$("#sectionCodeSelect").show();
			$("#districtCodeSelect").show();
			$("#areaCodeSelect").show();
			$("#unlinkDiv").show();
			$("#unlinkDiv").attr('checked', true);
			$("#streetCatgDesc").hide();
			$("#sectionDesc").hide();
			$("#districtDesc").hide();
			$("#areaDesc").hide();
			$("#streetCatgDesc").hide();
			$("#streetCatgCode").show();
			//changeDeliveryTime($("#districtCodeSelect option:selected").text());
			//changeDeliveryTime($("#districtCodeSelect").val());
			ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
				if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
					var minDateFormatted = minDate.replace(/\//g,"-");
					ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
				}
			});
		}
		
		if (!document.mobccsdeliveryForm.custAddressFlag1.checked) {
			sectionLoadAll = false;
			document.mobccsdeliveryForm.buildingName.readOnly = true;
			document.mobccsdeliveryForm.streetNum.readOnly = true;
			document.mobccsdeliveryForm.streetName.readOnly = true;
			document.mobccsdeliveryForm.streetCatgDesc.readOnly = true;
			document.mobccsdeliveryForm.quickSearch.readOnly = false;
/* 			document.mobccsdeliveryForm.flat.value = "";
			document.mobccsdeliveryForm.floor.value = ""; */
			$("#districtCodeSelect").hide();
			$("#areaCodeSelect").hide();
			$("#unlinkDiv").hide();
			$("#unlinkDiv").attr('checked', false);
			$("#sectionCodeSelect").hide();
			$("#sectionDesc").show();
			$("#districtDesc").show();
			$("#areaDesc").show();
			$("#streetCatgDesc").show();
			$("#streetCatgCode").hide();
			document.mobccsdeliveryForm.sectionDesc.readOnly = true;
			document.mobccsdeliveryForm.districtDesc.readOnly = true;
			document.mobccsdeliveryForm.areaDesc.readOnly = true;

			//for reacall order
			if ($("#quickSearch").val() == '') {

				//alert('clear');
				document.mobccsdeliveryForm.sectionDesc.value = "";
				document.mobccsdeliveryForm.districtDesc.value = "";
				document.mobccsdeliveryForm.areaDesc.value = "";
				document.mobccsdeliveryForm.streetCatgDesc.value = "";
				document.mobccsdeliveryForm.sectionCode.value = "";
				document.mobccsdeliveryForm.districtCode.value = "";
				document.mobccsdeliveryForm.areaCode.value = "";
				document.mobccsdeliveryForm.streetCatgCode.value = "";
				document.mobccsdeliveryForm.buildingName.value = "";
				document.mobccsdeliveryForm.streetNum.value = "";
				document.mobccsdeliveryForm.streetName.value = "";

			}
		}
	}

	function custAddressFlagClick2() {

		if (document.mobccsdeliveryForm.custAddressFlag2.checked) {
			
			$(".freeAddress").show();
			$(".fixAddress").hide();
			$("#districtCodeSelect").show();
			$("#districtDesc").hide();
			$("#areaCodeSelect").show();
			$("#areaDesc").hide();
		} else {
			$(".freeAddress").hide();
			$(".fixAddress").show();
			custAddressFlagClick();
		}
	}
	
	function setCurrentSearchFrom(input) {
		document.mobccsdeliveryForm.currentSearchFrom.value = input;

	}

	//STATR AJAX
	$(function() {
		$.ajax({
			url : "addressdistrictlookup.html?T=AREA",
			type : 'POST',
			dataType : 'JSON',
			timeout : 5000,
			error : function() {
				alert('Error loading area data!');
			},
			success : function(msg) {

				$("#areaCodeSelect").empty();
				$.each(eval(msg), function(i, item) {

					if (item.id == "${mobccsdelivery.areaCode}") {

						$(
								"<option value='" + item.id + "' selected='selected'>"
										+ item.name + "</option>").appendTo(
								$("#areaCodeSelect"));
					} else {
						$(
								"<option value='" + item.id + "' >" + item.name
										+ "</option>").appendTo(
								$("#areaCodeSelect"));

					}
				});

				$('#areaCodeSelect').trigger("change"); //for assign value
			}
		});

		$("#areaCodeSelect").change(
				function() {
					loadDistrict($("#areaCodeSelect").val());
					if ($("#custAddressFlag1").is(":checked")||$("input[name=custAddressFlag2]").is(":checked")) {
						$("#areaCode").val($("#areaCodeSelect").val());
						$("#areaDesc").val(
								$("#areaCodeSelect option:selected").text());
					}
/* 					if (!document.mobccsdeliveryForm.custAddressFlag2.checked) {
						custAddressFlagClick();//change readonly 
					} */
					//changeDeliveryTime($("#districtDesc").val());
					//changeDeliveryTime($("#districtCodeSelect").val());
					ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
						if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
							var minDateFormatted = minDate.replace(/\//g,"-");
							ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
						}
					});
				});

		$("#districtCodeSelect")
				.change(
						function() {
							loadSection($("#districtCodeSelect").val());
							if ($("#custAddressFlag1").is(":checked")||$("input[name=custAddressFlag2]").is(":checked")) {
								$("#districtCode").val(
										$("#districtCodeSelect").val());
								$("#districtDesc")
										.val(
												$(
														"#districtCodeSelect option:selected")
														.text());
							}
							//changeDeliveryTime($("#districtDesc").val());
							ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
								if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
									var minDateFormatted = minDate.replace(/\//g,"-");
									ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
								}
							});
						});

		$("#sectionCodeSelect")
				.change(
						function() {
							if ($("#custAddressFlag1").is(":checked")) {
								$("#sectionCode").val(
										$("#sectionCodeSelect").val());
								$("#sectionDesc").val(
										$("#sectionCodeSelect option:selected")
												.text());
							}
						});

		$("#streetCatgCode").change(
				function() {
					if ($("#custAddressFlag1").is(":checked")) {
						$("#streetCatgDesc").val(
								$("#streetCatgCode option:selected").text());
					}
				});

		$("#unlinkSectionFlag1").change(function() {
			if (document.mobccsdeliveryForm.unlinkSectionFlag1.checked) {
				loadSection(0);//load all
				sectionLoadAll = false;//0315

			} else {
				loadSection($("#districtCodeSelect").val());
				$('#quickSearch').attr('readonly', false);
			}
		});

		function loadDistrict(parentid) {
			$
					.ajax({
						url : 'addressdistrictlookup.html?T=DISTRICT&ID='
								+ parentid,
						type : 'POST',
						dataType : 'JSON',
						timeout : 5000,
						error : function() {
							alert('Error loading District data, Please press F5 to refresh page!');
						},
						success : function(msg) {
							$("#districtCodeSelect").empty();
							$
									.each(
											eval(msg),
											function(i, item) {
												if (item.id == "${mobccsdelivery.districtCode}") {
													$(
															"<option value='" + item.id + "' selected='selected'>"
																	+ item.name
																	+ "</option>")
															.appendTo(
																	$("#districtCodeSelect"));
												} else {
													$(
															"<option value='" + item.id + "' >"
																	+ item.name
																	+ "</option>")
															.appendTo(
																	$("#districtCodeSelect"));
												}
											});
							$('#districtCodeSelect').trigger("change");
						}
					});
		}

		function loadSection(parentid) {
			var varual = '';
			if ($("#unlinkSectionFlag1").is(":checked")
					&& sectionLoadAll == false) {
				varual = 'addressdistrictlookup.html?T=SECTION&ID=' + parentid
						+ '&SECTIONALL=true';
				$
						.ajax({
							url : varual,//'addressdistrictlookup.html?T=SECTION&ID='+ parentid+'&SECTIONALL='+sectionLoadAll,
							type : 'POST',
							dataType : 'JSON',
							timeout : 5000,
							error : function() {
								alert('Error loading Section data!');
							},
							success : function(msg) {

								$("#sectionCodeSelect").empty();
								$
										.each(
												eval(msg),
												function(i, item) {
													if (item.id == "${mobccsdelivery.sectionCode}") {

														$(
																"<option value='" + item.id + "' selected='selected'>"
																		+ item.name
																		+ "</option>")
																.appendTo(
																		$("#sectionCodeSelect"));
													} else {
														$(
																"<option value='" + item.id + "' >"
																		+ item.name
																		+ "</option>")
																.appendTo(
																		$("#sectionCodeSelect"));
													}
												});

								$('#sectionCodeSelect').trigger("change");
								sectionLoadAll = true;//loaded

							},
							complete : function() {
								$("#loaderImagePlaceholder").empty().html();
								if ($("#custAddressFlag1").is(":checked")) {
									$("#sectionCodeSelect").show();
								}
							},
							beforeSend : function() {
								if ($("#custAddressFlag1").is(":checked")) {

									$("#sectionCodeSelect").hide();
								}
								$("#loaderImagePlaceholder")
										.empty()
										.html(
												"<font color='red'>Please wait... </font>");

							}
						});

			} else if (!$("#unlinkSectionFlag1").is(":checked")) {
				varual = 'addressdistrictlookup.html?T=SECTION&ID=' + parentid
						+ '&SECTIONALL=false';
				$.ajax({

					url : varual,
					type : 'POST',
					dataType : 'JSON',
					timeout : 5000,
					error : function() {
						alert('Error loading Section data!');
					},
					success : function(msg) {
						$("#sectionCodeSelect").empty();
						$.each(eval(msg), function(i, item) {
							if (item.id == "${mobccsdelivery.sectionCode}") {
								$(
										"<option value='" + item.id + "' selected='selected'>"
												+ item.name + "</option>")
										.appendTo($("#sectionCodeSelect"));
							} else {
								$(
										"<option value='" + item.id + "' >"
												+ item.name + "</option>")
										.appendTo($("#sectionCodeSelect"));
							}
						});

						$('#sectionCodeSelect').trigger("change");

					},
					complete : function() {
						$("#loaderImagePlaceholder").empty();
						if ($("#custAddressFlag1").is(":checked")) {

							$("#sectionCodeSelect").show();
						}
					},
					beforeSend : function() {

						if ($("#custAddressFlag1").is(":checked")) {
							$("#sectionCodeSelect").hide();
						}
						$("#loaderImagePlaceholder").empty().html(
								"<font color='red'>Please wait... </font>");
					}
				});

			}

		}

	});
	//END AJAX
</script>


<form:form method="POST" name="mobccsdeliveryForm" id="mobccsdeliveryForm"
	commandName="mobccsdelivery">

	<!-- customer info table -->


	<table width="100%" class="tablegrey">
		<tr>
		<td bgcolor="#FFFFFF">
				<!--content-->
		<table width="100%" border="0" cellspacing="0" rules="none">
			<tr>
				<td class="table_title">Delivery Note</td>
				<td class="table_title" align="right">
					<c:if test="${orderDTO.busTxnType == 'PEND' && workStatus == 'recall'}">
						<form:checkbox path="byPassValidation" disabled="true"/>By-pass validation
					</c:if>
					<c:if test="${orderDTO.busTxnType != 'PEND'}">
						<form:checkbox path="byPassValidation"/>By-pass validation
					</c:if>
				</td>
			</tr>
		</table> <!-- start delivery info -->

		<table width="100%" border="0" cellspacing="1" class="contenttext"
			background="images/background2.jpg">
			<tr>
				<td colspan="4">
					<table width="100%" border="0" cellspacing="1"
						class="contenttext">

						<tr>
							<c:if test="${basketType == '2'}">
								<td>
									<label>
										<form:radiobutton path="deliveryInd" value="D" onclick="show('D')" />
										 <b><spring:message code="label.deliveryInd.D"/></b>
									</label>
									<span class="contenttext_red">*</span>
								</td>
								<!--  <td>
									<label>
										<form:radiobutton path="deliveryInd" value="P" onclick="show('P')" /> 
										<b><spring:message code="label.deliveryInd.P"/></b>
									</label>
									<span class="contenttext_red">*</span>
								</td>-->
							</c:if>
							<c:if test="${basketType != '2'}">
								<td>
									<label>
										<form:radiobutton path="deliveryInd" value="D" onclick="show('D')" />
										 <b><spring:message code="label.deliveryInd.D"/></b>
									</label>
									<span class="contenttext_red">*</span>
								</td>
							</c:if>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>


			<!-- start cust info1 -->
			<tr>
				<td colspan="4">
					<table width="100%" class="tablegrey" border="0">
						<tr>
							<td bgcolor="#FFFFFF">
								<!-- title -->
								<table width="100%" border="0" cellspacing="1">
									<tr>
										<td class="table_title"><spring:message code="label.mobccsdelivery.header"/></td>
									</tr>
								</table>
								<table width="100%" border="0">
									<tr>
										<td colspan="4">&nbsp;</td>
									</tr>

									<tr>
										<td width="25%">
											<div align="right"><spring:message code="label.mobccsdelivery.custName"/></div>
										</td>
										<td colspan="3"><form:select path="primaryContact.title" disabled="true" >
												<form:option value="" label="Select" />
												<form:options items="${titleList}" itemValue="codeId" itemLabel="codeDesc" />

											</form:select> <form:input path="primaryContact.contactName"
												maxlength="40" disabled="true"/>
										</td>

									</tr>
									<tr>
										<td></td>
										<td class="contenttext"><form:errors
												path="primaryContact.title" cssClass="error" /> <form:errors
												path="primaryContact.contactName" cssClass="error" /></td>
									</tr>

									<tr>
										<td>
											<div align="right"><spring:message code="label.mobccsdelivery.contactPhone.1st"/></div>
										</td>
										<td colspan="3">
											<form:input maxlength="8" path="primaryContact.contactPhone" />
											(Pre-visit Call/SMS) &nbsp;&nbsp;&nbsp;&nbsp;
											<spring:message code="label.mobccsdelivery.contactPhone.2nd"/>
											<form:input maxlength="64" path="primaryContact.otherPhone" /> 
											<form:errors path="primaryContact.contactPhone" cssClass="error" /> 
											<form:errors path="primaryContact.otherPhone" cssClass="error" />
										</td>
									</tr>


									<tr>
										<td></td>
										<td colspan="3"><form:errors
												path="primaryContact.idDocType" cssClass="error" /> <form:errors
												path="primaryContact.idDocNum" cssClass="error" /></td>
									</tr>
									<tr>
										<td colspan="4">
										<div id="DeliveryByCourierInfoDIV">
											<table border="0" width="100%" border="0" cellspacing="1"
												class="contenttext">
												<tr>
													<td colspan="4">
														<c:if test="${basketType == '2' && showReceiveByThirdPartyInd !='N'}">
														<table width="100%" class="tablegrey">
															<tr>
																<td bgcolor="#FFFFFF">
																	<table width="100%" border="0" cellspacing="1">
																		<tr>
																			<td class="table_title"><form:checkbox
																					path="recieveByThirdPartyInd"
																					onclick="recieveByThirdPartyIndClick()" />
																				Recieve By 3rd Party</td>
																		</tr>
																	</table> <!-- 3Rd party table -->
																	<div id="ThirdPartyInfoDIV">
																		<table width="100%" border="0">
																			<tr>
																				<td colspan="4">&nbsp;</td>
																			</tr>

																			<tr>
																				<td width="25%">
																					<div align="right">
																						<spring:message code="label.mobccsdelivery.custName"/>
																					</div>
																				</td>
																				<td colspan="3"><form:select
																						path="thirdPartyContact.title" >
																						<form:option value="" label="Select" />
																						<form:options items="${titleList}" itemValue="codeId" itemLabel="codeDesc" />
																					</form:select> <form:input
																						path="thirdPartyContact.contactName"
																						maxlength="40" /></td>

																			</tr>
																			<tr>
																				<td></td>
																				<td class="contenttext"><form:errors
																						path="thirdPartyContact.title" cssClass="error" />
																					<form:errors
																						path="thirdPartyContact.contactName"
																						cssClass="error" /></td>
																			</tr>

																			<tr>
																				<td>
																					<div align="right">
																					<spring:message code="label.mobccsdelivery.contactPhone.1st"/>
																					</div>
																				</td>
																				<td colspan="3"><form:input maxlength="8"
																						path="thirdPartyContact.contactPhone" />
																					<form:errors path="thirdPartyContact.otherPhone"
																						cssClass="error" /> <form:errors
																						path="thirdPartyContact.contactPhone"
																						cssClass="error" />
																				</td>
																			</tr>

																			<tr>
																				<td>
																					<div align="right"><spring:message code="label.mobccsdelivery.idDocType"/></div>
																				</td>
																				<td colspan="3">
																					<!--modify by eliot 20110609-->
																					<div align="left">
																						<form:select id="docType"
																							path="thirdPartyContact.idDocType"
																							onchange="setBDayAndCompanyNameDisplay()">
																							<form:option value="HKID" label="HKID" />
																							<form:option value="PASS" label="Passport" />

																						</form:select>
																						<form:input path="thirdPartyContact.idDocNum"
																							maxlength="30" />
																					</div>
																				</td>
																			</tr>


																			<tr>
																				<td></td>
																				<td colspan="3"><form:errors
																						path="thirdPartyContact.idDocType"
																						cssClass="error" /> <form:errors
																						path="thirdPartyContact.idDocNum"
																						cssClass="error" />
																				</td>
																			</tr>
																		</table>
																	</div> <!-- end ThirdPartyInfoDIV --> <!-- end 3Rd party table -->



																</td>
																<!--end tablegrey  -->
															</tr>
															<!--end tablegrey  -->
														</table> <!--end tablegrey  -->
														</c:if>
													</td>

												</tr>
												<tr>
													<td colspan="4">
														<!-- start address -->

														<div id="DeliveryAddressDiv">


															<!-- register address bolck -->
															<table width="100%" class="tablegrey">
																<tr>
																	<td bgcolor="#FFFFFF">
																		<!--content-->
																		<table width="100%" border="0" cellspacing="1">
																			<tr>
																				<td class="table_title"><spring:message code="label.mobccsdelivery.header.deliveryAddr"/></td>
																			</tr>
																		</table> <!-- Address Table -->
																		<table width="100%" border="0" cellspacing="0"
																			class="contenttext"
																			background="images/background2.jpg">



																			

                                                                           <tr id="freeaddr">
																				<td>&nbsp;</td>
																				<td colspan="3">
																					<label>
																						<form:checkbox path="custAddressFlag2" onclick="custAddressFlagClick2()"/>
																						Free input address
																					</label> 
																					<br/> 
																					<form:errors path="custAddressFlag2" cssClass="error" />
																				</td>
																			</tr>
                                                                           
                                                                           
                                                                           
                                                                           <tr id ="Freeaddress1" style="display:none;" class="freeAddress">
																				<td align="right"><spring:message code="label.address1"/></td>
                                                                           		<td colspan="3" align="left">
                                                                           			<form:input maxlength="50" path="address1" size="50"/>
                                                                           			<form:errors path="address1" cssClass="error" />
                                                                           		</td>
                                                                           </tr>
                                                                           
																			<tr id ="Freeaddress2" style="display:none;" class="freeAddress">
																				<td align="right">
																					<spring:message code="label.address2"/>
																				</td>
                                                                           		<td colspan="3" align="left">
                                                                           			<form:input maxlength="50" path="address2" size="50"/>
                                                                           		</td>
                                                                           	</tr>
                                                                           
																			
																			<tr id ="Freeaddress3" style="display:none;"  class="freeAddress">
                                                                           		<td align="right">
                                                                           			<spring:message code="label.address3"/></td>
                                                                           		<td  colspan="3" align="left">
                                                                           			<form:input maxlength="50" path="address3" size="50"/>
                                                                           		</td>
                                                                           	</tr>
                                                                                                                                                      
                                                                            <tr>
																				<td colspan="4">&nbsp;</td>
																			</tr>
																			<tr class="fixAddress">
																				<td align="right"><spring:message code="label.quickSearch"/></td>
																				<td colspan="3" align="left"><form:input
																						size="100%" path="quickSearch"
																						readonly="${mobccsdelivery.custAddressFlag}"
																						onclick="setCurrentSearchFrom('quickSearch')" />
																					<form:errors path="quickSearch" cssClass="error" />
																				</td>
																			</tr>
																			<tr class="fixAddress">
																				<td align="right">&nbsp;</td>
																				<td colspan="3"><span class="contenttext">
																						Simply input the estate name, block no. or the
																						building name of your installation address,
																						separate with commas </span>
																				</td>
																			</tr>

																			<tr class="fixAddress" height="15px">

																				<td colspan="4"></td>
																			</tr>
																			<tr class="fixAddress">
																				<td>&nbsp;</td>
																				<td colspan="3">
																					<label>
																						<form:checkbox path="custAddressFlag" onclick="custAddressFlagClick()" />
																						Please tick the checkbox if you want to input the address manually.
																					</label> 
																					<br> 
																					<form:errors path="custAddressFlag" cssClass="error" />
																				</td>
																			</tr>
                                                                          

																			<tr class="fixAddress">
																				<td align="right"><spring:message code="label.flat"/></td>
																				<td><form:input maxlength="5" path="flat" />
																				</td>

																				<td align="right"><spring:message code="label.floor"/></td>
																				<td><form:input maxlength="3" path="floor" />
																				</td>
																			</tr>
																			<tr class="fixAddress">
																				<td></td>
																				<td colspan="3"><form:errors path="flat"
																						cssClass="error" />
																				</td>
																			</tr>
																			<tr class="fixAddress">
																				<td align="right"><spring:message code="label.lotNum"/></td>
																				<td><form:input maxlength="8" path="lotNum" /><br>
																					<form:errors path="lotNum" cssClass="error" />
																				</td>
																				<td colspan="2">&nbsp;</td>
																			</tr>
																			<tr class="fixAddress">
																				<td align="right"><spring:message code="label.buildingName"/></td>
																				<td colspan="3" align="left"><form:input
																						maxlength="30" size="100%"
																						readonly="${!mobccsdelivery.custAddressFlag}"
																						path="buildingName" />
																				</td>
																			</tr>
																			<tr class="fixAddress">
																				<td align="right"><spring:message code="label.streetNum"/></td>
																				<td><form:input maxlength="11"
																						readonly="${!mobccsdelivery.custAddressFlag}"
																						path="streetNum" />
																				</td>
																				<td align="right"><spring:message code="label.streetName"/></td>
																				<td><form:input maxlength="23"
																						readonly="${!mobccsdelivery.custAddressFlag}"
																						path="streetName" />
																				</td>
																			</tr>

																			<tr class="fixAddress">
																				<td></td>
																				<td colspan="3"><form:errors
																						path="streetNum" cssClass="error" />
																				</td>
																			</tr>
																			<tr class="fixAddress">
																				<td align="right"><spring:message code="label.streetCatgCode"/></td>
																				<td colspan="3"><form:input
																						path="streetCatgDesc"
																						readonly="${mobccsdelivery.custAddressFlag}" />
																					<form:select path="streetCatgCode">

																						<form:option value="" label="" />
																						<form:options items="${streetCatgList}"
																							itemValue="categoryCode"
																							itemLabel="categoryDesc" />
																					</form:select>
																					<form:errors path="streetCatgCode" cssClass="error" />
																				</td>
																			</tr>

																			<!-- area district Section-->
																			<tr class="fixAddress">
																				<td align="right"><spring:message code="label.sectionCode"/></td>
																				<td colspan="3">
																					<table cellspacing="0">
																						<tr>
																							<td>
																								<div id='loaderImagePlaceholder'></div></td>
																							<td><select id='sectionCodeSelect'
																								style="width: 250px;"></select>
																							</td>
											
																							<td><form:input path="sectionDesc"
																									readonly="${mobccsdelivery.custAddressFlag}" />
																							</td>
																							<td><div id="unlinkDiv">
																									<form:checkbox path="unlinkSectionFlag" />
																									Unlink District and Section Code
																								</div><form:hidden path="sectionCode" />
																							</td>
																						</tr>
																					</table></td>
																			</tr>
																			<tr>
																				<td align="right"><spring:message code="label.districtCode"/></td>
																				<td colspan="3"><form:input
																						path="districtDesc"
																						readonly="${mobccsdelivery.custAddressFlag}" />
																					
																						<select id='districtCodeSelect'
																							style="width: 250px;"></select>
																					 <form:hidden path="districtCode" /> <form:errors
																						path="districtCode" cssClass="error" />
																				</td>
																			</tr>
																			<tr>
																				<td align="right"><spring:message code="label.areaCode"/></td>
																				<td colspan="3"><select id="areaCodeSelect"
																					style="width: 250px;"></select> <form:input
																						path="areaDesc"
																						readonly="${mobccsdelivery.custAddressFlag}" />
																					<form:hidden path="areaCode" /> <form:errors
																						path="areaCode" cssClass="error" />
																				</td>
																			</tr>

																		</table>
																	</td>
																</tr>
															</table>
															<!-- end register address bolck -->
														</div> <!-- end register address div --> <!-- end address -->
														<tr>
														</tr>
												</table>
											</div>
										</td>
									</tr>
									<tr height="30">
										<td style="text-align: right;">
											Dummy Delivery Mode
										</td>
										<td>
											<form:checkbox path="dummyDeliveryDateInd" onclick="dummyDeliveryDateIndClick()"/>
										</td>
									</tr>
									<tr height="30" id="UrgentDeliveryDiv">
										<c:if test="${mobccsdelivery.allowUrgentDelivery}">
											<td style="text-align: right;">
												<span style="display: inline-block; text-align: right; vertical-align: middle; padding-left: 2em">
													<spring:message code="label.delivery.urgent"/>
													<br/>
													<spring:message code="label.delivery.urgent.note"/>
													<br/>
												</span>
											</td>
											<td>
												<a href="#" class="nextbutton3 auth_button" style="vertical-align: middle">Authorize</a>
												<form:checkbox path="urgentInd" cssStyle="display:none" id="urgentIndId"/>
											</td>
										</c:if>
									</tr>
									<tr height="30">
										<td>
											<div id="PickUpDiv" style="text-align: right;">
												Pick Up Date
											</div>
											<div id="DeliveryByCourierDiv" style="text-align: right;">
												<spring:message code="label.delivery.date"/>
											</div>
										</td>
										<td>
											<form:input path="deliveryDateStr" maxlength="10" id="deliveryDatepicker" readonly="true" /> 
											<form:errors path="deliveryDateStr" cssClass="error" />
											<span id="deliveryDateStrError" class="contenttext_red"></span>
											<span id="deliveryDateRuleNo" style="display:none;"></span>
										<td>
									</tr>
									<tr height="30" id="DeliveryCentreDIV">
										<td style="text-align: right;">
											Delivery Centre
										</td>
										<td>
											<form:select path="deliveryCentre">
												<form:option value="" label="--Please Select--" />
												<form:options items="${pickUpCentreList}" itemValue="codeId" itemLabel="codeDesc" />
											</form:select>
											<form:errors path="deliveryCentre" cssClass="error" />
										</td>
									</tr>
						
									<tr height="30" id="DeliveryTimeSlotDIV">
										<td style="text-align: right;">
											<spring:message code="label.delivery.time"/>
										</td>
										<td>
											<select id='timeSlotId' ></select>
											<form:hidden path="deliveryTimeSlot" id="deliveryTimeSlotId"/>
											<form:errors path="deliveryTimeSlot" cssClass="error" />
											<input type="hidden" name="deliveryTimeSlotText" id="deliveryTimeSlotTextId"/>
											<span id="deliveryTimeSlotError" class="contenttext_red"></span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table> <!-- end title -->
				</td>
			</tr>

			<!-- end cust info1 -->

			<tr>

				<td colspan="4"></td>
			</tr>

		</table>
	</table>

	<!-- end customer info table -->

	<!-- button table  -->
	<table width="100%" border="0" cellspacing="0">
		<tr>

			<td>
				<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3" onClick="javascript:formSubmit();">continue
						&gt;</a>
				</div></td>
		</tr>
	</table>
	<!-- end button table -->
	<input type="hidden" id="currentSearchFrom" value="" />
	<input type="hidden" name="appMode" value="" />
	<input type="hidden" name="currentView" value="mobccsdelivery" />
	<form:hidden path="lotHouseInd" />

</form:form>
<div id="authDialog"></div>
<!---------------------------------------  end content ------------------------------------------->

<%@ include file="/WEB-INF/jsp/footer.jsp"%>