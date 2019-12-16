<%@ include file="/WEB-INF/jsp/header.jsp"%>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<style type="text/css"> 
	/* css for timepicker */
	.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
	.ui-timepicker-div dl { text-align: left; }
	.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
	.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
	.ui-timepicker-div td { font-size: 90%; }
	.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
	
	/*
	 * jQuery UI Slider 1.8.17
	 *
	 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
	 * Dual licensed under the MIT or GPL Version 2 licenses.
	 * http://jquery.org/license
	 *
	 * http://docs.jquery.com/UI/Slider#theming
	 */

	.ui-slider { position: relative; text-align: left; }
	.ui-slider .ui-slider-handle { position: absolute; z-index: 2; width: 1.2em; height: 1.2em; cursor: default; }
	.ui-slider .ui-slider-range { position: absolute; z-index: 1; font-size: .7em; display: block; border: 0; background-position: 0 0; }
	 
	.ui-slider-horizontal { height: .8em; }
	.ui-slider-horizontal .ui-slider-handle { top: -.3em; margin-left: -.6em; }
	.ui-slider-horizontal .ui-slider-range { top: 0; height: 100%; }
	.ui-slider-horizontal .ui-slider-range-min { left: 0; }
	.ui-slider-horizontal .ui-slider-range-max { right: 0; }
	
	.ui-slider-vertical { width: .8em; height: 100px; }
	.ui-slider-vertical .ui-slider-handle { left: -.3em; margin-left: 0; margin-bottom: -.6em; }
	.ui-slider-vertical .ui-slider-range { left: 0; width: 100%; }
	.ui-slider-vertical .ui-slider-range-min { bottom: 0; }
	.ui-slider-vertical .ui-slider-range-max { top: 0; }
	
</style>

<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.ui.slider.js"></script>
<script src="js/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();

	$('#smsRecordDatepicker').datepicker({
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate: "+0", maxDate: "+100Y", //Y is year, M is month, D is day  
		yearRange: "-8:+2" //the range shown in the year selection box
	}
	);

	$("input[name=backButton]").click(function() {
		window.location.href = "<c:url value="mobccscsicasecreate.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="caseNo" value="${param.caseNo}"/></c:url>";
	});
});
</script>

<form:form action="" name="submitForm" commandName="mobccscsismslogcreate">
	<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
	<colgroup style="width:150px"></colgroup>
	<tr class="contenttextgreen">
		<td class="table_title" colspan="2">SMS Log Create(CSI Case No: <c:out value="${param.caseNo}"/>)</td>
	</tr>
	<tr>
		<td>SMS Date:</td>
		<td>
		<form:input path="smsRecordDateStr" maxlength="10" id="smsRecordDatepicker" />
		</td>
	</tr>
	<tr>
		<td>SMS Time:</td>
		<td>
		<form:select path="smsRecordTimeHour"  >
			<form:options items="${hourList}"/>
		</form:select>
		<form:select path="smsRecordTimeMin" >
			<form:options items="${minList}"/>
		</form:select>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<input type="button" value="Quit" name="backButton" style="float:right">
		<input type="submit" value="Create" style="float:right">
		</td>
	</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>