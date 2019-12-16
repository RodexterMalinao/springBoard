<jsp:include page="/WEB-INF/jsp/header.jsp">
	<jsp:param name="enableOnloadJScript" value="Y" />
</jsp:include>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobccscustomerprofile" />
</jsp:include>


<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>

<script>
	var sectionLoadAll = false;
	var billingSectionLoadAll = false;

	$(document).ready(function() {
		$("input#quickSearch").autocomplete("addresslookup.html", {
			//minChars: 4,
			minChars : 3,
			delay : 600,
			selectFirst : false,
			max : 12,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}
		});
		//add 20110513
		$("input#billingQuickSearch").autocomplete("addresslookup.html", {
			//minChars: 4,
			minChars : 3,
			delay : 600,
			selectFirst : false,
			max : 12,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}
		});

		noBillingAddressClick1();

	});

	$(function() {
		// Datepicker
		$('#dobDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,

			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "-100Y",
			maxDate : "-18Y", //Y is year, M is month, D is day 
			yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		//yearRange: "1911:1993" //the range shown in the year selection box
		});
	});

	//20110131, disabled emailAddr
	function noEmailFlagClick() {
		if (document.custForm.noEmailFlag.checked) {
			document.custForm.emailAddr.value = "";
			document.custForm.emailAddr.disabled = true;

		} else {
			document.custForm.emailAddr.disabled = false;
		}
	}

	//need to edit , add billing address assign
	function formSubmit() {

		if ($("#custAddressFlag1").attr('checked')) {
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
		if ($("#billingCustAddressFlag1").attr('checked')) {
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
		}
		document.custForm.submit();
	}

	//20110314, for set ,set readOnly of buildingName,streetNum, streetName readonly
	function custAddressFlagClick() {

		if (document.custForm.custAddressFlag1.checked) {

			document.custForm.buildingName.readOnly = false;
			document.custForm.streetNum.readOnly = false;
			document.custForm.streetName.readOnly = false;
			document.custForm.quickSearch.readOnly = true;
			document.custForm.quickSearch.value = "";

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

		}
		if (!document.custForm.custAddressFlag1.checked) {
			sectionLoadAll = false;
			document.custForm.buildingName.readOnly = true;
			document.custForm.streetNum.readOnly = true;
			document.custForm.streetName.readOnly = true;
			document.custForm.streetCatgDesc.readOnly = true;
			document.custForm.quickSearch.readOnly = false;
			$("#districtCodeSelect").hide();
			$("#areaCodeSelect").hide();
			$("#unlinkDiv").hide();
			$("#sectionCodeSelect").hide();

			$("#sectionDesc").show();
			$("#districtDesc").show();
			$("#areaDesc").show();
			$("#streetCatgDesc").show();
			$("#streetCatgCode").hide();

			document.custForm.sectionDesc.readOnly = true;
			document.custForm.districtDesc.readOnly = true;
			document.custForm.areaDesc.readOnly = true;

			//for reacall order
			if ($("#quickSearch").val() == '') {

				//alert('clear');
				document.custForm.sectionDesc.value = "";
				document.custForm.districtDesc.value = "";
				document.custForm.areaDesc.value = "";
				document.custForm.streetCatgDesc.value = "";

				document.custForm.sectionCode.value = "";
				document.custForm.districtCode.value = "";
				document.custForm.areaCode.value = "";
				document.custForm.streetCatgCode.value = "";

				document.custForm.buildingName.value = "";
				document.custForm.streetNum.value = "";
				document.custForm.streetName.value = "";

			}

		}
	}

	function setCurrentSearchFrom(input) {
		document.custForm.currentSearchFrom.value = input;

	}

	function noBillingAddressClick1() {
		billingCustAddressFlagClick();
		if (document.custForm.noBillingAddressFlag1.checked) {
			//alert('cheked');
			$("#BillingDiv").hide();
		} else {
			//alert('uncheked');
			$("#BillingDiv").show();
		}
	}

	//add by eliot 20110608
	function setBDayAndCompanyNameDisplay() {
		if (document.custForm.docType.options[document.custForm.docType.selectedIndex].value == "BS") {
			$("#dateOfBirthDiv").hide();
			$("#dateOfBirthInputFieldDiv").hide();
			$("#CompanyNameDiv").show();
			$("#CompanyNameInputFiledDiv").show();
		} else {
			$("#dateOfBirthDiv").show();
			$("#dateOfBirthInputFieldDiv").show();
			$("#CompanyNameDiv").hide();
			$("#CompanyNameInputFiledDiv").hide();
		}
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
					if ($("#custAddressFlag1").attr('checked')) {
						$("#areaCode").val($("#areaCodeSelect").val());
						$("#areaDesc").val(
								$("#areaCodeSelect option:selected").text());
					}
					custAddressFlagClick();//change readonly

				});

		$("#districtCodeSelect")
				.change(
						function() {
							loadSection($("#districtCodeSelect").val());
							if ($("#custAddressFlag1").attr('checked')) {
								$("#districtCode").val(
										$("#districtCodeSelect").val());
								$("#districtDesc")
										.val(
												$(
														"#districtCodeSelect option:selected")
														.text());
							}
						});

		$("#sectionCodeSelect")
				.change(
						function() {
							if ($("#custAddressFlag1").attr('checked')) {
								$("#sectionCode").val(
										$("#sectionCodeSelect").val());
								$("#sectionDesc").val(
										$("#sectionCodeSelect option:selected")
												.text());
							}
						});

		$("#streetCatgCode").change(
				function() {
					if ($("#custAddressFlag1").attr('checked')) {
						$("#streetCatgDesc").val(
								$("#streetCatgCode option:selected").text());
					}
				});

		$("#unlinkSectionFlag1").change(function() {
			if (document.custForm.unlinkSectionFlag1.checked) {
				loadSection(0);//load all
				sectionLoadAll = false;//0315

			} else {
				loadSection($("#districtCodeSelect").val());
				$('#quickSearch').attr('readonly', false);
			}
		});

		function loadDistrict(parentid) {
			$.ajax({
				url : 'addressdistrictlookup.html?T=DISTRICT&ID=' + parentid,
				type : 'POST',
				dataType : 'JSON',
				timeout : 5000,
				error : function() {
					alert('Error loading data b!');
				},
				success : function(msg) {
					$("#districtCodeSelect").empty();
					$.each(eval(msg), function(i, item) {
						if (item.id == "${customerProfile.districtCode}") {
							$(
									"<option value='" + item.id + "' selected='selected'>"
											+ item.name + "</option>")
									.appendTo($("#districtCodeSelect"));
						} else {
							$(
									"<option value='" + item.id + "' >"
											+ item.name + "</option>")
									.appendTo($("#districtCodeSelect"));
						}
					});
					$('#districtCodeSelect').trigger("change");
				}
			});
		}

		function loadSection(parentid) {
			var varual = '';
			if ($("#unlinkSectionFlag1").attr('checked')
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
								if ($("#custAddressFlag1").attr('checked')) {
									$("#sectionCodeSelect").show();
								}
							},
							beforeSend : function() {
								if ($("#custAddressFlag1").attr('checked')) {

									$("#sectionCodeSelect").hide();
								}
								$("#loaderImagePlaceholder")
										.empty()
										.html(
												"<font color='red'>Please wait... </font>");

							}
						});

			} else if (!$("#unlinkSectionFlag1").attr('checked')) {
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
						if ($("#custAddressFlag1").attr('checked')) {

							$("#sectionCodeSelect").show();
						}
					},
					beforeSend : function() {

						if ($("#custAddressFlag1").attr('checked')) {
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

	//billing/////////////////////////////////////////////
	//20110314, for set ,set readOnly of buildingName,streetNum, streetName readonly
	function billingCustAddressFlagClick() {

		//custBillingAddressClick

		if (document.custForm.billingCustAddressFlag1.checked) {

			document.custForm.billingBuildingName.readOnly = false;
			document.custForm.billingStreetNum.readOnly = false;
			document.custForm.billingStreetName.readOnly = false;
			document.custForm.billingQuickSearch.readOnly = true;
			document.custForm.billingQuickSearch.value = "";

			$("#billingSectionCodeSelect").show();
			$("#billingDistrictCodeSelect").show();
			$("#billingAreaCodeSelect").show();
			$("#billingUnlinkDiv").show();

			$("#billingStreetCatgDesc").hide();
			$("#billingSectionDesc").hide();
			$("#billingDistrictDesc").hide();
			$("#billingAreaDesc").hide();

			$("#billingStreetCatgDesc").hide();
			$("#billingStreetCatgCode").show();

		}
		if (!document.custForm.billingCustAddressFlag1.checked) {
			billingSectionLoadAll = false; //check wilson
			document.custForm.billingBuildingName.readOnly = true;
			document.custForm.billingStreetNum.readOnly = true;
			document.custForm.billingStreetName.readOnly = true;
			document.custForm.billingStreetCatgDesc.readOnly = true;
			document.custForm.billingQuickSearch.readOnly = false;
			$("#billingDistrictCodeSelect").hide();
			$("#billingAreaCodeSelect").hide();
			$("#billingUnlinkDiv").hide();
			$("#billingSectionCodeSelect").hide();

			$("#billingSectionDesc").show();
			$("#billingDistrictDesc").show();
			$("#billingAreaDesc").show();
			$("#billingStreetCatgDesc").show();
			$("#billingStreetCatgCode").hide();

			document.custForm.billingSectionDesc.readOnly = true;
			document.custForm.billingDistrictDesc.readOnly = true;
			document.custForm.billingAreaDesc.readOnly = true;

			//for reacall order
			if ($("#billingQuickSearch").val() == '') {

				//alert('clear');
				document.custForm.billingSectionDesc.value = "";
				document.custForm.billingDistrictDesc.value = "";
				document.custForm.billingAreaDesc.value = "";
				document.custForm.billingStreetCatgDesc.value = "";

				document.custForm.billingSectionCode.value = "";
				document.custForm.billingDistrictCode.value = "";
				document.custForm.billingAreaCode.value = "";
				document.custForm.billingStreetCatgCode.value = "";

				document.custForm.billingBuildingName.value = "";
				document.custForm.billingStreetNum.value = "";
				document.custForm.billingStreetName.value = "";

			}

		}
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

				$("#billingAreaCodeSelect").empty();
				$.each(eval(msg), function(i, item) {

					if (item.id == "${customerProfile.billingAreaCode}") {

						$(
								"<option value='" + item.id + "' selected='selected'>"
										+ item.name + "</option>").appendTo(
								$("#billingAreaCodeSelect"));
					} else {
						$(
								"<option value='" + item.id + "' >" + item.name
										+ "</option>").appendTo(
								$("#billingAreaCodeSelect"));

					}
				});

				$('#billingAreaCodeSelect').trigger("change"); //for assign value
			}
		});

		$("#billingAreaCodeSelect").change(
				function() {
					loadBillingDistrict($("#billingAreaCodeSelect").val());
					if ($("#noBillingAddressFlag1").attr('checked')) {
						$("#billingAreaCode").val(
								$("#billingAreaCodeSelect").val());
						$("#billingAreaDesc").val(
								$("#billingAreaCodeSelect option:selected")
										.text());
					}

					// custBillingAddressClick();
					billingCustAddressFlagClick();
					// custnoBillingAddressFlagClick();//change readonly

				});

		$("#billingDistrictCodeSelect").change(
				function() {
					loadBillingSection($("#billingDistrictCodeSelect").val());
					if ($("#noBillingAddressFlag1").attr('checked')) {
						$("#billingDistrictCode").val(
								$("#billingDistrictCodeSelect").val());
						$("#billingDistrictDesc").val(
								$("#billingDistrictCodeSelect option:selected")
										.text());
					}
				});

		$("#billingSectionCodeSelect").change(
				function() {
					if ($("#noBillingAddressFlag1").attr('checked')) {
						$("#billingSectionCode").val(
								$("#billingSectionCodeSelect").val());
						$("#billingSectionDesc").val(
								$("#billingSectionCodeSelect option:selected")
										.text());
					}
				});

		$("#billingStreetCatgCode").change(
				function() {
					if ($("#noBillingAddressFlag1").attr('checked')) {
						$("#billingStreetCatgDesc").val(
								$("#billingStreetCatgCode option:selected")
										.text());
					}
				});

		$("#billingUnlinkSectionFlag1").change(function() {
			if (document.custForm.billingUnlinkSectionFlag1.checked) {
				loadBillingSection(0);//load all
				billingbillingSectionLoadAll = false;//0315

			} else {
				loadBillingSection($("#billingDistrictCodeSelect").val());
				$('#billingquickSearch').attr('readonly', false);
			}
		});

		function loadBillingDistrict(parentid) {
			$
					.ajax({
						url : 'addressdistrictlookup.html?T=DISTRICT&ID='
								+ parentid,
						type : 'POST',
						dataType : 'JSON',
						timeout : 5000,
						error : function() {
							alert('Error loading data b!');
						},
						success : function(msg) {
							$("#billingDistrictCodeSelect").empty();
							$
									.each(
											eval(msg),
											function(i, item) {
												if (item.id == "${customerProfile.billingDistrictCode}") {
													$(
															"<option value='" + item.id + "' selected='selected'>"
																	+ item.name
																	+ "</option>")
															.appendTo(
																	$("#billingDistrictCodeSelect"));
												} else {
													$(
															"<option value='" + item.id + "' >"
																	+ item.name
																	+ "</option>")
															.appendTo(
																	$("#billingDistrictCodeSelect"));
												}
											});
							$('#billingDistrictCodeSelect').trigger("change");
						}
					});
		}

		function loadBillingSection(parentid) {
			var varual = '';
			if ($("#billingUnlinkSectionFlag1").attr('checked')
					&& billingSectionLoadAll == false) {
				varual = 'addressdistrictlookup.html?T=SECTION&ID=' + parentid
						+ '&SECTIONALL=true';
				$
						.ajax({
							url : varual,//'addressdistrictlookup.html?T=SECTION&ID='+ parentid+'&SECTIONALL='+billingSectionLoadAll,
							type : 'POST',
							dataType : 'JSON',
							timeout : 5000,
							error : function() {
								alert('Error loading Section data!');
							},
							success : function(msg) {

								$("#billingSectionCodeSelect").empty();
								$
										.each(
												eval(msg),
												function(i, item) {
													if (item.id == "${customerProfile.billingSectionCode}") {

														$(
																"<option value='" + item.id + "' selected='selected'>"
																		+ item.name
																		+ "</option>")
																.appendTo(
																		$("#billingSectionCodeSelect"));
													} else {
														$(
																"<option value='" + item.id + "' >"
																		+ item.name
																		+ "</option>")
																.appendTo(
																		$("#billingSectionCodeSelect"));
													}
												});

								if ("ZZZZ" != '${customerProfile.billingSectionCode}') {
									$("<option value='ZZZZ'></option>")
											.appendTo(
													$("#billingSectionCodeSelect"));
								} else {
									$(
											"<option value='ZZZZ' selected='selected'></option>")
											.appendTo(
													$("#billingSectionCodeSelect"));
								}
								$('#billingSectionCodeSelect')
										.trigger("change");
								billingSectionLoadAll = true;//loaded

							},
							complete : function() {
								$("#billingLoaderImagePlaceholder").empty()
										.html();
								if ($("#noBillingAddressFlag1").attr('checked')) {
									$("#billingSectionCodeSelect").show();
								}
							},
							beforeSend : function() {
								if ($("#noBillingAddressFlag1").attr('checked')) {

									$("#billingSectionCodeSelect").hide();
								}
								$("#billingLoaderImagePlaceholder")
										.empty()
										.html(
												"<font color='red'>Please wait... </font>");

							}
						});

			} else if (!$("#billingUnlinkSectionFlag1").attr('checked')) {
				varual = 'addressdistrictlookup.html?T=SECTION&ID=' + parentid
						+ '&SECTIONALL=false';
				$
						.ajax({

							url : varual,
							type : 'POST',
							dataType : 'JSON',
							timeout : 5000,
							error : function() {
								alert('Error loading Section data!');
							},
							success : function(msg) {
								$("#billingSectionCodeSelect").empty();
								$
										.each(
												eval(msg),
												function(i, item) {
													if (item.id == "${customerProfile.billingSectionCode}") {
														$(
																"<option value='" + item.id + "' selected='selected'>"
																		+ item.name
																		+ "</option>")
																.appendTo(
																		$("#billingSectionCodeSelect"));
													} else {
														$(
																"<option value='" + item.id + "' >"
																		+ item.name
																		+ "</option>")
																.appendTo(
																		$("#billingSectionCodeSelect"));
													}
												});

								$('#billingSectionCodeSelect')
										.trigger("change");

							},
							complete : function() {
								$("#billingloaderImagePlaceholder").empty();
								if ($("#noBillingAddressFlag1").attr('checked')) {

									$("#billingSectionCodeSelect").show();
								}
							},
							beforeSend : function() {

								if ($("#noBillingAddressFlag1").attr('checked')) {
									$("#billingSectionCodeSelect").hide();
								}
								$("#billingloaderImagePlaceholder")
										.empty()
										.html(
												"<font color='red'>Please wait... </font>");
							}
						});

			}

		}

	});
	//END AJAX

	//end billing/////////////////////////////////////////////
</script>

<form:form method="POST" name="custForm"
	commandName="mobccscustomerProfile">

	<!-- customer info table -->
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Customer's information</td>
					</tr>
				</table>

				<table width="100%" border="0" cellspacing="1" class="contenttext"
					background="images/background2.jpg">
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								Title<span class="contenttext_red">*#</span>
							</div></td>
						<td colspan="3"><form:select path="title">
								<form:option value="NONE" label="Select" />
								<form:options items="${titleList}" />
							</form:select> <form:errors path="title" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								Name in English<span class="contenttext_red">*#</span>
							</div></td>
						<td width="20%"><form:input path="custLastName"
								maxlength="40" />
						</td>
						<td colspan="2"><form:input path="custFirstName"
								maxlength="40" />
						</td>
					</tr>
					<tr>
						<td>
							<div align="right"></div></td>
						<td class="contenttext">(Family Name)<form:errors
								path="custLastName" cssClass="error" />
						</td>
						<td colspan="2" class="contenttext">(Given Name)<form:errors
								path="custFirstName" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td>
							<div align="right"></div></td>
						<td colspan="3"><%-- <c:choose>
								<c:when
									test='${customerProfile.ignoreCustomerCheck==false&&customerProfile.isBomWsAvailable==true}'>
							&nbsp;
						</c:when>
								<c:otherwise>
									<form:checkbox path="ignoreCustomerCheck" />
									<span class="error">Customer checking service is
										unavailable, tick the checkbox if you want to ignore it.</span>
								</c:otherwise>
							</c:choose> --%>
						</td>
					</tr>
					<tr>
						<td>
							<div align="right">
								Document Type<span class="contenttext_red">*</span>
							</div></td>
						<td colspan="3">
							<!--modify by eliot 20110609-->
							<div align="left">
								<form:select id="docType" path="idDocType"
									onchange="setBDayAndCompanyNameDisplay()">
									<form:option value="HKID" label="HKID" />
									<form:option value="PASS" label="Passport" />
									<!--add by eliot 20110608-->
									<form:option value="BS" label="HKBR" />
								</form:select>
							</div></td>
					</tr>
					<tr>
						<td>
							<div align="right">
								Document Number<span class="contenttext_red">*</span>
							</div></td>
						<td colspan="3"><form:input path="idDocNum" maxlength="30" />
							<form:errors path="idDocNum" cssClass="error" /> <form:errors
								path="idDocType" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan="3" class="smalltextgray"><strong>Note
								1: <span class="contenttext_red">*</span> If HKID, please enter
								the ID in A999999(A)/AA999999(A) format</strong>
						</td>
					</tr>
					<!--add by eliot 20110608-->
					<tr>
						<td>&nbsp;</td>
						<td colspan="3" class="smalltextgray"><strong>Note
								2: <span class="contenttext_red">*</span> If HKBR, please enter
								the BR in 12345678-123/12345678-ABC format</strong>
						</td>
					</tr>
					<tr>
						<td>
							<div align="right"></div></td>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<!--add by eliot 20110608-->
						<td>
							<div align="right" id="CompanyNameDiv">
								Company Name<span class="contenttext_red">*</span>
							</div>
							<div align="right" id="dateOfBirthDiv">
								Date of birth<span class="contenttext_red">*</span>
							</div></td>
						<td colspan="3">
							<div id="CompanyNameInputFiledDiv">
								<form:input path="companyName" maxlength="40" />
								<form:errors path="companyName" cssClass="error" />
							</div>
							<div id="dateOfBirthInputFieldDiv">
								<form:input path="dobStr" maxlength="10" id="dobDatepicker"
									readonly="true" />
								<form:errors path="dobStr" cssClass="error" />
							</div></td>
					</tr>
					<tr>
						<td>
							<div align="right">
								Contact phone no.<span class="contenttext_red">*#</span>
							</div></td>
						<td colspan="3"><form:input maxlength="8" path="contactPhone" />
							<form:errors path="contactPhone" cssClass="error" />
						</td>
					</tr>

					<%-- add by herbert 20110720 start--%>
					<tr>
						<td>
							<div align="right">Other contact no. :</div></td>
						<td colspan="3"><form:input maxlength="64"
								path="otherContactPhone" /> <form:errors
								path="otherContactPhone" cssClass="error" />
						</td>
					</tr>
					<%-- add by herbert 20110720 end--%>

					<tr>
						<td>
							<div align="right">
								Email address<span class="contenttext_red">*</span>
							</div></td>
						<td colspan="3"><form:input path="emailAddr"
								disabled="${customerProfile.noEmailFlag}" /> <form:checkbox
								path="noEmailFlag" onclick="noEmailFlagClick()" />No Email <form:errors
								path="emailAddr" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan="3" class="smalltextgray"><strong>Note 3:
								<span class="contenttext_red">*</span> Pre, Pend Order Mandatory Field</strong>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan="3" class="smalltextgray"><strong>Note 4:
								<span class="contenttext_red">*</span> Draft Order Mandatory Field</strong>
						</td>
					</tr>
					<tr>
						<td>
							<div align="right"></div></td>
						<td colspan="3">&nbsp;</td>
					</tr>
				</table> <!--modify by Eliot 20110608--> <c:choose>
					<c:when test='${customerProfile.idDocType == "BS"}'>
						<script>
							$("#dateOfBirthDiv").hide();
							$("#dateOfBirthInputFieldDiv").hide();
							$("#CompanyNameDiv").show();
							$("#CompanyNameInputFiledDiv").show();
						</script>
					</c:when>
					<c:otherwise>
						<script>
							$("#dateOfBirthDiv").show();
							$("#dateOfBirthInputFieldDiv").show();
							$("#CompanyNameDiv").hide();
							$("#CompanyNameInputFiledDiv").hide();
						</script>
					</c:otherwise>
				</c:choose></td>
		</tr>
	</table>
	<!-- end customer info table -->
	
	<!-- contact info table -->
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Contact information</td>
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
												<td colspan="3"><form:select
														path="primaryContact.title">
														<form:option value="NONE" label="Select" />
														<form:options items="${titleList}"  />

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
											
											</table>

			</td>
		</tr>
	</table>
	<!-- end contact table -->
	<!-- register address bolck -->
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="1">
					<tr>
						<td class="table_title">Register Address</td>
					</tr>
				</table> <!-- Address Table -->
				<table width="100%" border="0" cellspacing="0" class="contenttext"
					background="images/background2.jpg">



					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>


					<tr>
						<td align="right"><span class="contenttext_red">*</span>Quick
							Search</td>
						<td colspan="3" align="left"><form:input size="100%"
								path="quickSearch" readonly="${customerProfile.custAddressFlag}"
								onclick="setCurrentSearchFrom('quickSearch')" /> <form:errors
								path="quickSearch" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td align="right">&nbsp;</td>
						<td colspan="3"><span class="contenttext"> Simply
								input the estate name, block no. or the building name of your
								installation address, separate with spaces </span>
						</td>
					</tr>

					<tr height="15px">

						<td colspan="4"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan="3"><form:checkbox path="custAddressFlag"
								onclick="custAddressFlagClick()" />Please tick the checkbox if
							you want to input the address manually. <br> <form:errors
								path="custAddressFlag" cssClass="error" />
						</td>
					</tr>


					<tr>
						<td align="right">Flat</td>
						<td><form:input maxlength="5" path="flat" />
						</td>

						<td align="right">Floor</td>
						<td><form:input maxlength="3" path="floor" />
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3"><form:errors path="flat" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td align="right">Lot/Hse</td>
						<td><form:input maxlength="8" path="lotNum" /><br> <form:errors
								path="lotNum" cssClass="error" />
						</td>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td align="right">Building</td>
						<td colspan="3" align="left"><form:input maxlength="30"
								size="100%" readonly="${!customerProfile.custAddressFlag}"
								path="buildingName" />
						</td>
					</tr>
					<tr>
						<td align="right">Street No</td>
						<td><form:input maxlength="11"
								readonly="${!customerProfile.custAddressFlag}" path="streetNum" />
						</td>
						<td align="right">Street Name</td>
						<td><form:input maxlength="23"
								readonly="${!customerProfile.custAddressFlag}" path="streetName" />
						</td>
					</tr>

					<tr>
						<td></td>
						<td colspan="3"><form:errors path="streetNum"
								cssClass="error" />
						</td>
					</tr>
					<tr>
						<td align="right">Street Category</td>
						<td colspan="3"><form:input path="streetCatgDesc"
								readonly="${customerProfile.custAddressFlag}" /> <form:select
								path="streetCatgCode">

								<form:option value="" label="" />
								<form:options items="${streetCatgList}" itemValue="categoryCode"
									itemLabel="categoryDesc" />
							</form:select>
						</td>
					</tr>

					<!-- area district Section-->
					<tr>
						<td align="right">Section</td>
						<td colspan="3">
							<table cellspacing="0">
								<tr>
									<td>
										<div id='loaderImagePlaceholder'></div></td>
									<td><select id='sectionCodeSelect' style="width: 250px;"></select>
									</td>
									<td><form:input path="sectionDesc"
											readonly="${customerProfile.custAddressFlag}" />
									</td>
									<td><form:hidden path="sectionCode" />
									</td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td align="right">District</td>
						<td colspan="3"><form:input path="districtDesc"
								readonly="${customerProfile.custAddressFlag}" />
							<div id="unlinkDiv">
								<select id='districtCodeSelect' style="width: 250px;"></select>
								<form:checkbox path="unlinkSectionFlag" />
								Unlink District and Section Code
							</div> <form:hidden path="districtCode" /> <form:errors
								path="districtCode" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td align="right">Area</td>
						<td colspan="3"><select id="areaCodeSelect"
							style="width: 250px;"></select> <form:input path="areaDesc"
								readonly="${customerProfile.custAddressFlag}" /> <form:hidden
								path="areaCode" /> <form:errors path="areaCode"
								cssClass="error" />
						</td>
					</tr>

				</table></td>
		</tr>
	</table>
	<!-- end register address bolck -->

	<!-- billing address bolck -->
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="1">
					<tr>
						<td class="table_title">Billing Address</td>
					</tr>
					<tr>
						<td><form:checkbox path="noBillingAddressFlag"
								onclick="noBillingAddressClick1()" /> Billing address is same
							as register address</td>

					</tr>
				</table>
				<div id="BillingDiv">
					<!-- Address Table -->
					<table width="100%" border="0" cellspacing="0" class="contenttext"
						background="images/background2.jpg">



						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>


						<tr>
							<td align="right"><span class="contenttext_red">*</span>Quick
								Search</td>
							<td colspan="3" align="left"><form:input size="100%"
									path="billingQuickSearch"
									readonly="${customerProfile.billingCustAddressFlag}"
									onclick="setCurrentSearchFrom('billingQuickSearch')" /> <form:errors
									path="billingQuickSearch" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td colspan="3"><span class="contenttext"> Simply
									input the estate name, block no. or the building name of your
									installation address, separate with spaces </span>
							</td>
						</tr>

						<tr height="15px">

							<td colspan="4"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td colspan="3"><form:checkbox path="billingCustAddressFlag"
									onclick="billingCustAddressFlagClick()" />Please tick the
								checkbox if you want to input the address manually. <br> <form:errors
									path="billingCustAddressFlag" cssClass="error" />
							</td>
						</tr>


						<tr>
							<td align="right">Flat</td>
							<td><form:input maxlength="5" path="billingFlat" />
							</td>

							<td align="right">Floor</td>
							<td><form:input maxlength="3" path="billingFloor" />
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="3"><form:errors path="billingFlat"
									cssClass="error" />
							</td>
						</tr>
						<tr>
							<td align="right">Lot/Hse</td>
							<td><form:input maxlength="8" path="billingLotNum" /><br>
								<form:errors path="billingLotNum" cssClass="error" />
							</td>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td align="right">Building</td>
							<td colspan="3" align="left"><form:input maxlength="30"
									size="100%"
									readonly="${!customerProfile.billingCustAddressFlag}"
									path="billingBuildingName" />
							</td>
						</tr>
						<tr>
							<td align="right">Street No</td>
							<td><form:input maxlength="11"
									readonly="${!customerProfile.billingCustAddressFlag}"
									path="billingStreetNum" />
							</td>
							<td align="right">Street Name</td>
							<td><form:input maxlength="23"
									readonly="${!customerProfile.billingCustAddressFlag}"
									path="billingStreetName" />
							</td>
						</tr>

						<tr>
							<td></td>
							<td colspan="3"><form:errors path="billingStreetNum"
									cssClass="error" />
							</td>
						</tr>
						<tr>
							<td align="right">Street Category</td>
							<td colspan="3"><form:input path="billingStreetCatgDesc"
									readonly="${customerProfile.billingCustAddressFlag}" /> <form:select
									path="billingStreetCatgCode">

									<form:option value="" label="" />
									<form:options items="${streetCatgList}" />
								</form:select>
							</td>
						</tr>

						<!-- area district Section-->
						<tr>
							<td align="right">Section</td>
							<td colspan="3">
								<table cellspacing="0">
									<tr>
										<td>
											<div id='loaderImagePlaceholder'></div></td>
										<td><select id='billingSectionCodeSelect'
											style="width: 250px;"></select>
										</td>
										<td><form:input path="billingSectionDesc"
												readonly="${customerProfile.billingCustAddressFlag}" />
										</td>
										<td><form:hidden path="billingSectionCode" />
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td align="right">District</td>
							<td colspan="3"><form:input path="billingDistrictDesc"
									readonly="${customerProfile.billingCustAddressFlag}" />
								<div id="billingUnlinkDiv">
									<select id='billingDistrictCodeSelect' style="width: 250px;"></select>
									<form:checkbox path="billingUnlinkSectionFlag" />
									Unlink District and Section Code
								</div> <form:hidden path="billingDistrictCode" /> <form:errors
									path="billingDistrictCode" cssClass="error" />
							</td>
						</tr>
						<tr>
							<td align="right">Area</td>
							<td colspan="3"><select id="billingAreaCodeSelect"
								style="width: 250px;"></select> <form:input
									path="billingAreaDesc"
									readonly="${customerProfile.billingCustAddressFlag}" /> <form:hidden
									path="billingAreaCode" /> <form:errors path="billingAreaCode"
									cssClass="error" />
							</td>
						</tr>

					</table>

				</div></td>
		</tr>
	</table>

	<!-- end billing address block -->

	<!-- Address Proof table  -->
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="1">
					<tr>
						<td class="table_title">Address Proof</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="1" class="contenttext"
					background="images/background2.jpg">
					<tr>
						<td>
							<div align="right"></div></td>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4">
							<div align="left">
								<form:errors path="addrProofInd" cssClass="error" />
							</div></td>
					</tr>
					<tr>
						<td width="25%">
							<div align="left">
								<span class="contenttext_red">*</span>
								<form:radiobutton path="addrProofInd" value="1" />
								Address Proof Collected
							</div></td>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="left">
								<span class="contenttext_red">*</span>
								<form:radiobutton path="addrProofInd" value="0" />
								Await Address Proof
							</div></td>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="left">
								<span class="contenttext_red">*</span>
								<form:radiobutton path="addrProofInd" value="2" />
								Refer Address Proof
							</div></td>
						<td>LOB <form:select path="lob">
								<form:option value="" label="Select" />
								<form:options items="${lobList}" />
							</form:select>
							<form:errors path="lob" cssClass="error" />
						</td>
						<td colspan="2">Service Account No. or Tel No.: <form:input
								path="serviceNum" maxlength="20" />
							<form:errors path="serviceNum" cssClass="error" />
						</td>
					</tr>
					<tr>

						<td colspan="3" class="smalltextgray"><strong>Note:
								LOB and service account no. or tel no. are required for refer
								address proof.</strong>
						</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
				</table>
		</tr>
	</table>
	<!-- end Address Proof table  -->
	<!-- Billing Language table -->
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="1">
					<tr>
						<td class="table_title">Billing Language</td>
					</tr>
				</table>

				<table width="100%" border="0" cellspacing="1" class="contenttext"
					background="images/background2.jpg">
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="left">
								Billing Language<span class="contenttext_red">*</span>
							</div></td>
						<td colspan="3"><form:select path="billLang">
								<form:option value="BILN" label="Bilingual" />
								<form:option value="CHN" label="Traditional Chinese" />
								<form:option value="ENG" label="English" />
							</form:select> <form:errors path="billLang" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="left">
								SMS Language<span class="contenttext_red">*</span>
							</div></td>
						<td colspan="3"><form:select path="smsLang">
								<form:option value="00" label="Traditional Chinese" />
								<form:option value="01" label="Simplified Chinese" />
								<form:option value="02" label="English" />
							</form:select> <form:errors path="smsLang" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td>
							<div align="right"></div></td>
						<td colspan="3">&nbsp;</td>
					</tr>
				</table></td>
		</tr>
	</table>
	<!-- end Billing Language table -->
	<!-- button table  -->
	<table width="100%" border="0" cellspacing="0">
		<tr>

			<td>
				<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton" onClick="javascript:formSubmit();">continue
						&gt;</a>
				</div></td>
		</tr>
	</table>
	<!-- end button table -->
	<input type="hidden" id="currentSearchFrom" value="" />
	<input type="hidden" name="currentView" value="mobccscustomerprofile" />
	<form:hidden path="isBomWsAvailable" />
	<form:hidden path="lotHouseInd" />
	<form:hidden path="billingLotHouseInd" />
</form:form>

<!---------------------------------------  end content ------------------------------------------->

<%@ include file="/WEB-INF/jsp/footer.jsp"%>