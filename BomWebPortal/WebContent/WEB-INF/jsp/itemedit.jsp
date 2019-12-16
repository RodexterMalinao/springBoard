<jsp:include page="/WEB-INF/jsp/header.jsp">

	<jsp:param name="enableOnloadJScript" value="Y" />
</jsp:include>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id=progress bar><br>
<br>
</div>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>

<!-- TinyMCE -->
<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript"><!--

	$(function() {

		// Datepicker
		//effStartDateeffEndDate
		$('#effStartDate').datepicker({
			changeMonth : true,
			changeYear : true,
			//showOn: "button",
			//buttonImage: "images/calendar.gif",
			//buttonImageOnly: true,

			showButtonPanel : true,
			dateFormat : 'yy/mm/dd'//, //'yy' is 4 digit in jquery UI, eg. 1920 
		//minDate : "-100Y",
		//maxDate : "-18Y", //Y is year, M is month, D is day 
		//yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		//yearRange: "1911:1993" //the range shown in the year selection box

		});

		$('#effEndDate').datepicker({
			changeMonth : true,
			changeYear : true,
			//showOn: "button",
			//buttonImage: "images/calendar.gif",
			//buttonImageOnly: true,

			showButtonPanel : true,
			dateFormat : 'yy/mm/dd'//, //'yy' is 4 digit in jquery UI, eg. 1920 
			//minDate : "-100Y",
			//maxDate : "-18Y", //Y is year, M is month, D is day 
			//yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
			//yearRange: "1911:1993" //the range shown in the year selection box
			,
			buttons : {

				'clear' : function(event, ui) {

					$(this).val('');

				}

			}

		});

	});

	$(document).ready(function() {
		//alert('ready(function()ready(function()');
		//alert(document.itemEditForm.type.value);
		show();
		//	$('#type').trigger("show"); 

	});

	function show() {
		//onclick="javascript:show('mnpN');" 
		//style="display: block;"

		//hsItemDiv
		//rpRbVasItemDiv
		if (document.itemEditForm.type.value == 'HS') {
			document.getElementById('hsItemDiv').style.display = 'block';
			document.getElementById('rpRbVasItemDiv').style.display = 'none';
		} else {

			document.getElementById('hsItemDiv').style.display = 'none';
			document.getElementById('rpRbVasItemDiv').style.display = 'block';
		}
	}

	function searchSubmit() {
		//alert('searchSubmit');
		document.itemEditForm.formAction.value = "SEARCH";
		document.itemEditForm.submit();
	}

	//function offerInsertSubmit() {
		//alert('searchSubmit');
		//document.itemEditForm.formAction.value = "OFFER_INSERT";
		//document.itemEditForm.submit();
	//}

	function offerPreviewSubmit() {
		//alert('searchSubmit');
		document.itemEditForm.formAction.value = "OFFER_PREVIEW";
		document.itemEditForm.submit();
	}

	function insertSubmit() {

		if (document.itemEditForm.effStartDate.value == "") {
			alert("please input effStartDate");
			return false;
		}

		if (document.itemEditForm.env.value == "") {
			alert("please select env first");
			return false;
		} else if (document.itemEditForm.env.value == "custom") {
			if (document.itemEditForm.id.value == "") {//no itemId input
				alert("please select env first, or input custom input itemId");
				return false;
			} else {
				var answer1 = confirm("confirm to insert with id :"
						+ document.itemEditForm.id.value);
				if (answer1) {
					//custom input itemId
					document.itemEditForm.formAction.value = "INSERT";
					document.itemEditForm.submit();
				}
			}
		} else {
			var answer = confirm("confirm to insert with auto create itemId?");
			if (answer) {

				document.itemEditForm.formAction.value = "INSERT";
				document.itemEditForm.id.value = 0;
				document.itemEditForm.submit();
				//} else {
				//	alert("cancel insert call");
				//	return false;
				//}
			}

		}

	}

	function updateSubmit() {
		alert('updateSubmit');
		document.itemEditForm.formAction.value = "UPDATE";
		document.itemEditForm.submit();
	}
	function deleteSubmit() {
		//alert('deleteSubmit');
		var answer = confirm("confirm to delete ?");
		if (answer) {
			document.itemEditForm.formAction.value = "DELETE";
			document.itemEditForm.submit();
		} else {
			alert("cancel delete call");
			return false;
		}
	}
	function cleanSubmit() {
		//alert('deleteSubmit');
		document.itemEditForm.formAction.value = "CLEAN";
		document.itemEditForm.submit();
	}

	function toItemDisplay() {

		window.location = "itemdisplay.html?itemId2="
				+ document.itemEditForm.id.value;

	}
//
--></script>


<form:form method="POST" name="itemEditForm" commandName="itemEdit">
	<h3>Item Editor</h3>
	<div id="baseInfoDiv">


	<table bgcolor="#FFFFFF" border="0">

		<tr>
			<td>Message</td>
			<td colspan="3"><form:textarea path="formMessage"
				readonly="true" rows="2" cols="80" />&nbsp;</td>
		<tr>
		<tr>
			<td>Item ID:</td>
			<td colspan="3">
			<div id="buttonArea"><form:input path="id" maxlength="10" /> <input
				type="submit" name="Search" value="Search" onClick="searchSubmit()" />

			<form:select path="env">
				<form:option value="" label="" />
				<form:option value="custom" label="custom" />
				<form:options items="${envList}" />
			</form:select> <input type="button" name="Insert" value="Insert"
				onClick="insertSubmit()" /> <input type="button" name="Update"
				value="Update" onClick="updateSubmit()" /> <input type="button"
				name="Delete" value="Delete" onClick="deleteSubmit()" /> <input
				type="button" name="Delete" value="Clear" onClick="cleanSubmit()" />
			<a href="#" onClick="javascript:toItemDisplay();">itemdisplay.html</a>
			</div>
			</td>

		</tr>
	</table>
	
	<h4>===== offer info=====</h4>
	
	<table bgcolor="#FFFFFF" border="0">
		<tr>
			<td>Offer info</td>
		</tr>
		<tr>

			<td><form:textarea path="offerString" rows="10" cols="80" />&nbsp;</td>
		<tr>
		</tr>
	</table>
	<table bgcolor="#FFFFFF" border="0">
		<tr>
			<td><input type="button" name="offerPreview"
				value="offerPreview" onClick="offerPreviewSubmit()" /> 
				<!--  <input
				type="button" name="offerInsert" value="offerInsert"
				onClick="offerInsertSubmit()" />
				 -->
				
				</td>

		</tr>

	</table>
	<h4>===== offer info preview =====</h4>
	
	<table bgcolor="#FFFFFF" border="1">
		<tr>
			<td>itemId</td>
			<td>itemOfferSeq</td>
			<td>offerId</td>
			<td>offerSubId</td>
			<td>offerType</td>
			<td>selectQty</td>
			<td>itemProductSeq</td>
			<td>productId</td>
			<td>productType</td>
			<td>parsingMessage</td>
			<td>source</td>
		</tr>

		${offerTable}
	</table>
	<h4>===== item basic info=====</h4>

	<table bgcolor="#FFFFFF" border="0">

		<!-- undo to here here here -->


		<tr>
			<td>lob:</td>
			<td><form:select path="lob">
				<form:options items="${itemLobList}" />
			</form:select></td>
		</tr>

		<tr>
			<td>Type:</td>
			<td><form:select path="type" onchange="show()">
				<form:options items="${itemTypeList}" />
			</form:select></td>
		</tr>

		<tr>
			<td>description:</td>
			<td colspan="3"><form:textarea path="description" rows="3"
				cols="80" /></td>
		</tr>

		<tr>
			<td>createBy:</td>
			<td><form:input path="createBy" readonly="true" /></td>
			<td>createDate:</td>
			<td><form:input path="createDate" readonly="true" /></td>
		</tr>



		<tr>
			<td>lastUpdBy:</td>
			<td><form:input path="lastUpdBy" readonly="true" /></td>
			<td>lastUpdDate:</td>
			<td><form:input path="lastUpdDate" readonly="true" /></td>
		</tr>



	</table>


	</div>
	<div id="hsItemDiv" style="display: block;">
	<h4>=====HS item info=====</h4>
	<table bgcolor="#FFFFFF" border="0">
		<tr>
			<td>brandName:</td>
			<td><form:select path="brandId">

				<form:option value="" label="" />
				<form:options items="${brandList}" />
			</form:select></td>
		</tr>

		<tr>
			<td>modelName:</td>
			<td><form:select path="modelId">

				<form:option value="" label="" />
				<form:options items="${modelList}" />
			</form:select></td>
		</tr>

		<tr>
			<td>colorName:</td>
			<td><form:select path="colorId">
				<form:option value="" label="" />
				<form:options items="${colorList}" />
			</form:select></td>
		</tr>

		<tr>
			<td>hsContractPeriod:</td>
			<td><form:input path="hsContractPeriod" /></td>
		</tr>

	</table>
	</div>


	<div id="rpRbVasItemDiv" style="display: block;">
	<h4>=====RP RB VAS item info=====</h4>
	<table bgcolor="#FFFFFF" border="0">
		<tr>
			<td>rpType:</td>
			<td><form:select path="rpType">
				<form:option value="" label="" />
				<form:options items="${rpTypeList}" />
			</form:select></td>

		</tr>

		<tr>
			<td>rpRbVasContractPeriod:</td>
			<td><form:input path="rpRbVasContractPeriod" /></td>
		</tr>

		<tr>
			<td>rebatePeriod:</td>
			<td><form:input path="rebatePeriod" /></td>
		</tr>

		<tr>
			<td>rebateSchedule:</td>
			<td><form:input path="rebateSchedule" /></td>
		</tr>

		<tr>
			<td>rebateAmt:</td>
			<td><form:input path="rebateAmt" /></td>
		</tr>

		<tr>
			<td>penaltyType:</td>
			<td><form:select path="penaltyType">
				<form:option value="" label="" />
				<form:options items="${penaltyTypeList}" />
			</form:select></td>


		</tr>

		<tr>
			<td>penaltyAmt:</td>
			<td><form:input path="penaltyAmt" /></td>
		</tr>
	</table>
	</div>

	<div id="pricingItemDiv">
	<h4>===== item pricing info=====</h4>
	<table bgcolor="#FFFFFF" border="0">

		<tr>
			<td>effStartDate:</td>
			<td><form:input path="effStartDate" />(yyyy/mm/dd)</td>
		</tr>
		<tr>
			<td>effEndDate:</td>
			<td><form:input path="effEndDate" /></td>
		</tr>
		<tr>
			<td>onetimeType:</td>


			<td><form:select path="onetimeType">
				<form:option value="" label="" />
				<form:options items="${onetimeTypeList}" />
			</form:select></td>
		</tr>
		<tr>
			<td>onetimeAmt:</td>
			<td><form:input path="onetimeAmt" /></td>
		</tr>
		<tr>
			<td>recurrentAmt:</td>
			<td><form:input path="recurrentAmt" /></td>
		</tr>
	</table>
	</div>

	






	<form:hidden path="formAction" />

	<input type="hidden" name="currentView" value="itemedit" />
</form:form>
<!-- -------------------------------------  end content ----------------------------------------- -->

<%@ include file="/WEB-INF/jsp/footer.jsp"%>