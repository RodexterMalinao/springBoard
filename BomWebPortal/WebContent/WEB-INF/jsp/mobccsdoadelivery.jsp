<jsp:include page="/WEB-INF/jsp/header.jsp">
	<jsp:param name="enableOnloadJScript" value="Y" />
</jsp:include>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.10.2.js"></script>
<script>
	var jquery1102 = jQuery.noConflict();
</script>
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
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
	return jquery1102.ajax({
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
			}
		}
	});
}

function ccsDeliveryTimeslotAjax (delMode, delInd, delDate, distNo, minDate, minimumTimeslot) {
	jquery1102.ajax({
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
	
	$(document).ready(function() {
		appDate = appDate.replace(/\//g,"-");
		ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
			if (minDate != null && $('#deliveryDatepicker').datepicker('getDate') != null) {
				var minDateFormatted = minDate.replace(/\//g,"-");
				ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
			}
		});
		$("#header table:first td:gt(0)").remove();
		$("#header table:last").remove();
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
			/* //changeDeliveryTime($("#districtDesc").val());
			changeDeliveryTime($("#districtCodeSelect").val()); */
		}); 
	});
	
	function changeDeliveryTime(district) {
		$.ajax({
			url : 'timeslotlookup.html?district=' + district + '&appDate=' +'${appDate}',
		type : 'POST',
		dataType : 'JSON',
		timeout : 5000,
		error : function() {
			alert('Error loading Delivery Time, Please press F5 to refresh page!');
		},
		success : function(msg) {
			$("#timeSlotId").empty();
			$.each(
				eval(msg),
				function(i, item) {
					if (item.timeSlot == $("#deliveryTimeSlotId").val()) {
						$(
								"<option value='" + item.timeSlot + "' selected='selected'>"
										+ item.time
										+ "</option>")
								.appendTo(
										$("#timeSlotId"));
					} else {
						$(
								"<option value='" + item.timeSlot + "' >"
										+ item.time
										+ "</option>")
								.appendTo(
										$("#timeSlotId"));
					}
				});
		}
	});
	}

	function recieveByThirdPartyIndClick() {
		if ($("#recieveByThirdPartyInd1").is(':checked')) {
			$("#ThirdPartyInfoDIV").show();

		} else {
			$("#ThirdPartyInfoDIV").hide();

		}

	}

	/* $(function() {
		// Datepicker
		$('#deliveryDatepicker').datepicker({
			changeMonth : true,
			changeYear : false,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "0Y",
			yearRange : "c-100:c+100" ,//the range shown in the year selection box, c= inpu date
				maxDate : $.datepicker.parseDate( 'yy-mm-dd', '${maxDate}')
				, beforeShowDay: function(date) { 
					//generate array of selectable urgent date
					var selectableDateArray = [];
				
					<c:forEach items="${normalDateList}" var="items">
						selectableDateArray[selectableDateArray.length] = '<c:out value="${items}" />';
					</c:forEach>
					
					
					//disable specific date
					var m = date.getMonth(), d = date.getDate(), y = date.getFullYear();
					if($.inArray(y + '-' + (m+1) + '-' + d,selectableDateArray) > -1 ) {
							return [true];
					    }
					return [false];
				}
		});
		custAddressFlagClick();
		custAddressFlagClick2();
	}); */

	//need to edit , add billing address assign
	function formSubmit() {
		if ($("input[name=custAddressFlag2]").is(':checked')) {
			//copy value
			$("#districtCode").val($("#districtCodeSelect").val());
			$("#districtDesc").val(
					$("#districtCodeSelect option:selected").text());
			$("#areaCode").val($("#areaCodeSelect").val());
			$("#areaDesc").val($("#areaCodeSelect option:selected").text());

		}else{
			if ($("#custAddressFlag1").is(':checked')) {
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
		
		document.mobccsdoadeliveryForm.submit();
	}

	//20110314, for set ,set readOnly of buildingName,streetNum, streetName readonly
	function custAddressFlagClick() {

		if (document.mobccsdoadeliveryForm.custAddressFlag1.checked) {

			document.mobccsdoadeliveryForm.buildingName.readOnly = false;
			document.mobccsdoadeliveryForm.streetNum.readOnly = false;
			document.mobccsdoadeliveryForm.streetName.readOnly = false;
			document.mobccsdoadeliveryForm.quickSearch.readOnly = true;
			document.mobccsdoadeliveryForm.quickSearch.value = "";

			$("#sectionCodeSelect").show();
			$("#districtCodeSelect").show();
			$("#areaCodeSelect").show();
			$("#unlinkDiv").show();

			$("#streetCatgDesc").hide();
			$("#sectionDesc").hide();
			$("#districtDesc").hide();
			$("#areaDesc").hide();

			$("#streetCatgDesc").hide();
			$("#streetCatgCode").show();
			/* //changeDeliveryTime($("#districtCodeSelect option:selected").text());
			changeDeliveryTime($("#districtCodeSelect").val()); */
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
		if (!document.mobccsdoadeliveryForm.custAddressFlag1.checked) {
			sectionLoadAll = false;
			document.mobccsdoadeliveryForm.buildingName.readOnly = true;
			document.mobccsdoadeliveryForm.streetNum.readOnly = true;
			document.mobccsdoadeliveryForm.streetName.readOnly = true;
			document.mobccsdoadeliveryForm.streetCatgDesc.readOnly = true;
			document.mobccsdoadeliveryForm.quickSearch.readOnly = false;
			$("#districtCodeSelect").hide();
			$("#areaCodeSelect").hide();
			$("#unlinkDiv").hide();
			$("#sectionCodeSelect").hide();

			$("#sectionDesc").show();
			$("#districtDesc").show();
			$("#areaDesc").show();
			$("#streetCatgDesc").show();
			$("#streetCatgCode").hide();

			document.mobccsdoadeliveryForm.sectionDesc.readOnly = true;
			document.mobccsdoadeliveryForm.districtDesc.readOnly = true;
			document.mobccsdoadeliveryForm.areaDesc.readOnly = true;

			//for reacall order
			if ($("#quickSearch").val() == '') {

				//alert('clear');
				document.mobccsdoadeliveryForm.sectionDesc.value = "";
				document.mobccsdoadeliveryForm.districtDesc.value = "";
				document.mobccsdoadeliveryForm.areaDesc.value = "";
				document.mobccsdoadeliveryForm.streetCatgDesc.value = "";

				document.mobccsdoadeliveryForm.sectionCode.value = "";
				document.mobccsdoadeliveryForm.districtCode.value = "";
				document.mobccsdoadeliveryForm.areaCode.value = "";
				document.mobccsdoadeliveryForm.streetCatgCode.value = "";

				document.mobccsdoadeliveryForm.buildingName.value = "";
				document.mobccsdoadeliveryForm.streetNum.value = "";
				document.mobccsdoadeliveryForm.streetName.value = "";

			}
		}
	}
	 function custAddressFlagClick2() {

		if ($("input[name=custAddressFlag2]").is(':checked')) {
			
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
		document.mobccsdoadeliveryForm.currentSearchFrom.value = input;

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

					if (item.id == "${customerProfile.areaCode}") {

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
					if ($("#custAddressFlag1").is(':checked')||$("input[name=custAddressFlag2]").is(':checked')) {
						$("#areaCode").val($("#areaCodeSelect").val());
						$("#areaDesc").val(
								$("#areaCodeSelect option:selected").text());
					}
/* 					if (!document.mobccsdeliveryForm.custAddressFlag2.checked) {
					custAddressFlagClick();//change readonly
					} */
					/* //changeDeliveryTime($("#districtDesc").val());
					changeDeliveryTime($("#districtCodeSelect").val()); */
					if ($('#deliveryDatepicker').datepicker('getDate') != null) {
						var temp = $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate'));
						if ($("#districtCode").val() != null) {
							ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
								var minDateFormatted = minDate.replace(/\//g,"-");
								ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
							});
						}
					}
				});

		$("#districtCodeSelect")
				.change(
						function() {
							loadSection($("#districtCodeSelect").val());
							if ($("#custAddressFlag1").is(':checked')) {
								$("#districtCode").val(
										$("#districtCodeSelect").val());
								$("#districtDesc")
										.val(
												$(
														"#districtCodeSelect option:selected")
														.text());
							}
							/* //changeDeliveryTime($("#districtDesc").val());
							changeDeliveryTime($("#districtCodeSelect").val()); */
							if ($('#deliveryDatepicker').datepicker('getDate') != null) {
								var temp = $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate'));
								if ($("#districtCode").val() != null) {
									ccsDeliveryDateRangeAjax (orderType, delMode, delInd, hsInd, hsItemCd, payMthd, fsInd, mnpInd, orderId, appDate).done(function(){
										var minDateFormatted = minDate.replace(/\//g,"-");
										ccsDeliveryTimeslotAjax (delMode, delInd, $.datepicker.formatDate('dd-mm-yy', $('#deliveryDatepicker').datepicker('getDate')), $("#districtCode").val(), minDateFormatted, minimumTimeslot)
									});
								}
							}
						});

		$("#sectionCodeSelect")
				.change(
						function() {
							if ($("#custAddressFlag1").is(':checked')) {
								$("#sectionCode").val(
										$("#sectionCodeSelect").val());
								$("#sectionDesc").val(
										$("#sectionCodeSelect option:selected")
												.text());
							}
						});

		$("#streetCatgCode").change(
				function() {
					if ($("#custAddressFlag1").is(':checked')) {
						$("#streetCatgDesc").val(
								$("#streetCatgCode option:selected").text());
					}
				});

		$("#unlinkSectionFlag1").change(function() {
			if (document.mobccsdoadeliveryForm.unlinkSectionFlag1.checked) {
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
												if (item.id == "${customerProfile.districtCode}") {
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
			if ($("#unlinkSectionFlag1").is(':checked')
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
													if (item.id == "${customerProfile.sectionCode}") {

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

								if ("ZZZZ" != '${customerProfile.sectionCode}') {
									$("<option value='ZZZZ'></option>")
											.appendTo($("#sectionCodeSelect"));
								} else {
									$(
											"<option value='ZZZZ' selected='selected'></option>")
											.appendTo($("#sectionCodeSelect"));
								}
								$('#sectionCodeSelect').trigger("change");
								sectionLoadAll = true;//loaded

							},
							complete : function() {
								$("#loaderImagePlaceholder").empty().html();
								if ($("#custAddressFlag1").is(':checked')) {
									$("#sectionCodeSelect").show();
								}
							},
							beforeSend : function() {
								if ($("#custAddressFlag1").is(':checked')) {

									$("#sectionCodeSelect").hide();
								}
								$("#loaderImagePlaceholder")
										.empty()
										.html(
												"<font color='red'>Please wait... </font>");

							}
						});

			} else if (!$("#unlinkSectionFlag1").is(':checked')) {
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
							if (item.id == "${customerProfile.sectionCode}") {
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
						if ($("#custAddressFlag1").is(':checked')) {

							$("#sectionCodeSelect").show();
						}
					},
					beforeSend : function() {

						if ($("#custAddressFlag1").is(':checked')) {
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


<form:form method="POST" name="mobccsdoadeliveryForm"
	commandName="mobccsdoadelivery">

	<!-- customer info table -->


	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="0" rules="none">
					<tr>
						<td class="table_title">Delivery Note</td>
					</tr>
				</table> <!-- start delivery info -->

				<table width="100%" border="0" cellspacing="1" class="contenttext"
					background="images/background2.jpg">
					<tr>
						<td colspan="4">
							<table width="100%" border="0" cellspacing="1"
								class="contenttext">

								<tr>
									<td>
										<B>Delivery by Courier</B>
										<span class="contenttext_red">*</span>
									</td>
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
												<td class="table_title">Delivery Information</td>
											</tr>
										</table>
										<table width="100%" border="0">
											<tr>
												<td colspan="4">&nbsp;</td>
											</tr>

											<tr>
												<td width="25%">
													<div align="right">Customer Name</div>
												</td>
												<td colspan="3"><form:select path="primaryContact.title">
														<form:option value="" label="Select" />
														<form:options items="${titleList}" itemValue="codeId" itemLabel="codeDesc" />

													</form:select> <form:input path="primaryContact.contactName"
														maxlength="40" />
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
													<div align="right">1st Contact No.</div></td>
												<td colspan="3"><form:input maxlength="8"
														path="primaryContact.contactPhone" />(Pre-visit Call/SMS)
													&nbsp;&nbsp;&nbsp;&nbsp;2nd Contact No.<form:input
														maxlength="64" path="primaryContact.otherPhone" /> <form:errors
														path="primaryContact.contactPhone" cssClass="error" /> <form:errors
														path="primaryContact.otherPhone" cssClass="error" />
												</td>
											</tr>



											<tr>
												<td>
													<div align="right">Document Type</div>
												</td>
												<td colspan="3">
													<!--modify by eliot 20110609-->
													<div align="left">
														<form:select path="primaryContact.idDocType">
															<form:option value="HKID" label="HKID" />
															<form:option value="PASS" label="Passport" />
														</form:select>
														<form:input path="primaryContact.idDocNum" maxlength="30" />
													</div>
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
																	<c:if test="${basketType == '2'}">
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
																								<div align="right">Customer Name</div>
																							</td>
																							<td colspan="3"><form:select
																									path="thirdPartyContact.title">
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
																								<div align="right">1st Contact No.</div></td>
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
																								<div align="right">Document Type</div>
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
																							<td class="table_title">Delivery Address</td>
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
																				 <td align="right">Quick Search</td>
																				 <td colspan="3" align="left"><form:input
																					  size="100%" path="quickSearch"
																				      readonly="${mobccsdeilvery.custAddressFlag}"
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
																				 <td colspan="3"><form:checkbox
																					 path="custAddressFlag"
																					 onclick="custAddressFlagClick()" />Please tick
																								the checkbox if you want to input the address
																								manually. <br> <form:errors
																					 path="custAddressFlag" cssClass="error" /></td>
																			</tr>


																			 <tr class="fixAddress">
																			     <td align="right">Flat</td>
																			     <td><form:input maxlength="5" path="flat" />
																			     </td>

																				 <td align="right">Floor</td>
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
																							<td align="right">Lot/Hse</td>
																							<td><form:input maxlength="8" path="lotNum" /><br>
																								<form:errors path="lotNum" cssClass="error" />
																							</td>
																							<td colspan="2">&nbsp;</td>
																			 </tr>
																			 <tr class="fixAddress">
																							<td align="right">Building</td>
																							<td colspan="3" align="left"><form:input
																									maxlength="30" size="100%"
																									readonly="${custAddressFlag}"
																									path="buildingName" />
																							</td>
																			 </tr>
																			 <tr class="fixAddress">
																					        <td align="right">Street No</td>
																							<td><form:input maxlength="11"
																									readonly="${custAddressFlag}"
																									path="streetNum" />
																							</td>
																							<td align="right">Street Name</td>
																							<td><form:input maxlength="23"
																									readonly="${!custAddressFlag}"
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
																							<td align="right">Street Category</td>
																							<td colspan="3"><form:input
																									path="streetCatgDesc"
																									readonly="${custAddressFlag}" />
																								<form:select path="streetCatgCode">

																									<form:option value="" label="" />
																									<form:options items="${streetCatgList}"
																										itemValue="categoryCode"
																										itemLabel="categoryDesc" />
																								</form:select>
																							</td>
																			</tr>

																						<!-- area district Section-->
																			<tr class="fixAddress">
																							<td align="right">Section</td>
																							<td colspan="3">
																								<table cellspacing="0">
																									<tr>
																										<td>
																											<div id='loaderImagePlaceholder'></div></td>
																										<td><select id='sectionCodeSelect'
																											style="width: 250px;"></select>
																										</td>
																										<td><form:input path="sectionDesc"
																												readonly="${mobccsdeilvery.custAddressFlag}" />
																										</td>
																										<td><form:hidden path="sectionCode" />
																										<div id="unlinkDiv">
																											<form:checkbox path="unlinkSectionFlag" />
																											Unlink District and Section Code
																										</div> 
																										</td>
																									</tr>
																								</table></td>
																			</tr>
																			<tr>
																							<td align="right">District</td>
																							<td colspan="3"><form:input
																									path="districtDesc"
																									readonly="${mobccsdeilvery.custAddressFlag}" />
																									<select id='districtCodeSelect'
																										style="width: 250px;"></select>
																									
																									<form:hidden path="districtCode" /> <form:errors
																									path="districtCode" cssClass="error" />
																							</td>
																			</tr>
																			<tr>
																							<td align="right">Area</td>
																							<td colspan="3"><select id="areaCodeSelect"
																								style="width: 250px;"></select> <form:input
																									path="areaDesc"
																									readonly="${mobccsdeilvery.custAddressFlag}" />
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
												<tr>
													<td>
														<div align="right" id="deliveryDateDiv">Delivery Date</div>
													</td>
													<td>
														<form:input path="deliveryDateStr" maxlength="10" id="deliveryDatepicker" readonly="true" /> 
													</td>
													<td>
														<form:errors path="deliveryDateStr" cssClass="error" />
														<span id="deliveryDateStrError" class="contenttext_red"></span>
														<span id="deliveryDateRuleNo" style="display:none;"></span>
													</td>
												</tr>
												<tr>
													<td>
														<div align="right" id="deliveryTimeDiv">Delivery Time</div>
													</td>
													<td>
														<select id='timeSlotId'></select>
														<form:hidden path="deliveryTimeSlot" id="deliveryTimeSlotId"/>
														<input type="hidden" name="deliveryTimeSlotText" id="deliveryTimeSlotTextId"/>
													</td>
													<td>
														<form:errors path="deliveryTimeSlot" cssClass="error" />
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
					<a href="#" class="nextbutton" onClick="javascript:formSubmit();">SRS / MNP information
						&gt;</a>
				</div></td>
		</tr>
	</table>
	<!-- end button table -->
	<input type="hidden" id="currentSearchFrom" value="" />
	<input type="hidden" name="appMode" value="" />
	<form:hidden path="lotHouseInd" />

</form:form>

<!---------------------------------------  end content ------------------------------------------->

<%@ include file="/WEB-INF/jsp/footer.jsp"%>