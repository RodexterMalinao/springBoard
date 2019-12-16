<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<sb-util:codelookup var="numTypeMap" codeType="MIP_NUM_TYPE" />
<script type="text/javascript">
var mrtControl = jQuery.parseJSON('${mrtControlJson}');


var serviceType = [], msisdnlvl = [], channelCode = [];
// each serviceType can access which num lvl
var serviceTypeGoldenNumlvl = { PCCW3G: new Array("GB", "GC", "GD", "N1", "N2"), UNICOM1C2N: new Array("N1", "N2"), CCU1C2N: new Array("N1", "N2")};
var goldenNumChannelCode = []; // channel can access golden num
var goldenNumlvl = new Array("GB", "GC"); // golden num lvl 
var cityCode = [];
<c:forEach items="${serviceTypeList}" var="r">
serviceType[serviceType.length] = "<c:out value="${r.parmValue}"/>";
</c:forEach>
<c:forEach items="${msisdnlvlList}" var="r">
msisdnlvl[msisdnlvl.length] = "<c:out value="${r.parmValue}"/>";
</c:forEach>
<c:forEach items="${channelCodeList}" var="r">
channelCode[channelCode.length] = "<c:out value="${r.parmValue}"/>";
</c:forEach>
<c:forEach items="${goldenNumChannelCodeList}" var="r">
goldenNumChannelCode[goldenNumChannelCode.length] = "<c:out value="${r.codeId}"/>";
</c:forEach>
<c:forEach items="${cityCodeList}" var="r">
cityCode[cityCode.length] = "<c:out value="${r.codeId}"/>";
</c:forEach>
function isBlank(str) {
	return str == null || $.trim(str).length == 0;
}

function controlMrtCriteria() {
	
}

function filterValues(listValues, keepValues) {
	var filteredValues = [];
	// filter values in listValues, keep only value presents in keepValues
	for (var i = 0; i < listValues.length; i++) {
		for (var j = 0; j < keepValues.length; j++) {
			if (listValues[i] == keepValues[j]) {
				filteredValues[filteredValues.length] = listValues[i];
				break;
			}
		}
	}
	return filteredValues;
}
function filterOutValues(listValues, outValues) {
	var filteredValues = [];
	// filter values in listValues, remove value presents in keepValues
	for (var i = 0; i < listValues.length; i++) {
		var matched = false;
		for (var j = 0; j < outValues.length; j++) {
			if (listValues[i] == outValues[j]) {
				matched = true;
				break;
			}
		}
		if (!matched) {
			filteredValues[filteredValues.length] = listValues[i];
		}
	}
	return filteredValues;
}
function refreshList(name, list) {
	var $select = $("select[name=" + name + "]");
	var selectVal = $select.find("option:selected").val();
	$select.find("option").remove();
	$select.append($("<option/>"));
	for (var i = 0; i < list.length; i++) {
		$select.append($("<option/>").val(list[i]).text(list[i]));
	}
	$select.val(selectVal).css("width", "auto");
}
function updateNumTypeList() {
	var numType = $("select[name=numType] option:selected").val();
	if (!isBlank(numType)) {
		refreshList("serviceType", mrtControl[numType].serviceType);
	} else {
		refreshList("serviceType", new Array());
	}
}
function updateServiceTypeList(msisdnlvl, channelCode) {
	var msisdnlvl = $("select[name=msisdnlvl] option:selected").val();
	var channelCode = $("select[name=channelCode] option:selected").val();
	var numType = $("select[name=numType] option:selected").val();
	var serviceTypeNew = serviceType.slice();
	if (!isBlank(numType)) {
		switch (msisdnlvl) {
		case "GB":
		case "GC":
		case "GD":
			serviceTypeNew = filterValues(serviceTypeNew, new Array("PCCW3G"));
			break;
		case "N1":
		case "N2":
		default:
			serviceTypeNew = filterValues(serviceTypeNew, new Array("PCCW3G", "UNICOM1C2N", "CCU1C2N"));
		}
	}
	// all channel can access to both pccw3g/unicom
	refreshList("serviceType", serviceTypeNew);
}
function updateMsisdnlvlList() {
	var serviceType = $("select[name=serviceType] option:selected").val();
	var channelCode = $("select[name=channelCode] option:selected").val();
	var msisdnlvlNew = msisdnlvl.slice();
	switch (serviceType) {
	case "":
		break;
	case "PCCW3G":
		msisdnlvlNew = filterValues(msisdnlvlNew, serviceTypeGoldenNumlvl.PCCW3G);
		break;
	case "UNICOM1C2N":
	case "CCU1C2N":
		msisdnlvlNew = filterValues(msisdnlvlNew, serviceTypeGoldenNumlvl.UNICOM1C2N);
		break;
	}
	if (!isBlank(channelCode)) {
		if ($.inArray(channelCode, goldenNumChannelCode) == -1) {
			msisdnlvlNew = filterOutValues(msisdnlvlNew, goldenNumlvl);
		} else {
			msisdnlvlNew = filterValues(msisdnlvlNew, goldenNumlvl);
		}
	}
	refreshList("msisdnlvl", msisdnlvlNew);
}
function updateChannelCodeList() {
	var serviceType = $("select[name=serviceType] option:selected").val();
	var msisdnlvl = $("select[name=msisdnlvl] option:selected").val();
	var channelCodeNew = channelCode.slice();
	// all channel can access to both pccw3g/unicom
	if (!isBlank(msisdnlvl)) {
		if ($.inArray(msisdnlvl, goldenNumlvl) == -1) {
			channelCodeNew = filterOutValues(channelCodeNew, goldenNumChannelCode);
		} else {
			channelCodeNew = filterValues(channelCodeNew, goldenNumChannelCode);
		}
	}
	refreshList("channelCode", channelCodeNew);
}
function updateCityCodeList() {
	var numType = $("select[name=numType] option:selected").val();
	var serviceType = $("select[name=serviceType] option:selected").val();
	switch (serviceType) {
	case "UNICOM1C2N":
	case "CCU1C2N":
		refreshList("cityCode", mrtControl[numType][serviceType].city);
		$("select[name=cityCode]").attr("disabled", false);
		$(".cityCode").show();
		break;
	default:
		refreshList("cityCode", new Array());
		$("select[name=cityCode]").attr("disabled", true);
		$(".cityCode").hide();
	}
}
$(document).ready(function() {
	console.log("1");
	console.log(mrtControl);
	$("select[name=numType]").change(function() {
		updateNumTypeList();
		updateMsisdnlvlList();
		updateChannelCodeList();
		updateCityCodeList();
	}).change();
	$("select[name=serviceType]").change(function() {
		updateMsisdnlvlList();
		updateChannelCodeList();
		updateCityCodeList();
	}).change();
	$("select[name=msisdnlvl]").change(function() {
		updateServiceTypeList();
		updateChannelCodeList();
		updateCityCodeList();
	}).change();
	$("select[name=channelCode]").change(function() {
		updateMsisdnlvlList();
		updateChannelCodeList();
		updateCityCodeList();
	}).change();

	$("form[name=mobccsmrtstockin]").submit(function(e) {
		$("input[name=msisdn]").val($.trim($("input[name=msisdn]").val()));
	});
	
	$("input[name=quitButton]").click(function() {
		window.location.href = "mobccsmrtstock.html";
	});
	
	$(".stockHistory").click(function(e) {
		e.preventDefault();
		$("form[name=stockHistoryForm]").submit();
		return false;
	});
});
</script>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:choose>
<c:when test="${param.recordCreated == 'true'}">
<div class="contenttextgreen">
	MRT: 
	<a href="#" class="stockHistory contenttextgreen"><c:out value="${param.msisdnCreated}" /></a>
	is created.
</div>
<form action="mobccsmrtstock.html" method="post" name="stockHistoryForm">
	<input type="hidden" name="msisdn" value="${param.msisdnCreated}">
</form>
</c:when>
<c:when test="${param.msisdnAvailable == 'false'}">
<div class="contenttext_red">MRT: ${param.msisdnCreated} already in inventory</div>
</c:when>
</c:choose>

<div style="clear:both;padding-top:5px"></div>

<form:form method="POST" name="mobccsmrtstockin">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width: 150px"></colgroup>
<colgroup></colgroup>
<tr class="contenttextgreen">
	<td class="table_title" colspan="2">MRT Pool Maintenance (In Stock)</td>
</tr>
<tr>
	<td>MRT</td>
	<td>
		<form:input path="msisdn" maxlength="16"/>
		<div style="clear:both"></div>
		<form:errors path="msisdn" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Number Type</td>
	<td>
		<form:select path="numType">
			<form:option value="" label="" />
			<c:forEach items="${numTypeList}" var="numType">
				<form:option value="${numType.parmValue}" label="${numTypeMap[numType.parmValue]}" />
			</c:forEach>
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="numType" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Service Type</td>
	<td>
		<form:select path="serviceType">
			<form:option value="" label="" />
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="serviceType" cssClass="error"/>
	</td>
</tr>
<tr class="cityCode">
	<td>City</td>
	<td>
		<form:select path="cityCode">
			<form:option value="" label="" />
			<form:options items="${cityCodeList}" itemValue="codeId" itemLabel="codeDesc" />
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="cityCode" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Grade</td>
	<td>
		<form:select path="msisdnlvl">
			<form:option value="" label=""/>
			<form:options items="${msisdnlvlList}" itemValue="parmValue" itemLabel="parmValue"/>
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="msisdnlvl" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Channel</td>
	<td>
		<form:select path="channelCode">
			<form:option value="" label=""/>
			<form:options items="${channelCodeList}" itemValue="parmValue" itemLabel="parmValue"/>
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="channelCode" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Status</td>
	<td>
		<form:select path="msisdnStatus">
			<form:option value="" label="" />
			<form:options items="${msisdnStatusList}" itemValue="status" itemLabel="desc"/>
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="msisdnStatus" cssClass="error"/>
	</td>
</tr>
</table>
<div class="buttonmenubox_R">
	<input type="submit" value="Save" name="saveButton">
	<input type="button" value="Quit" name="quitButton">
</div>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>