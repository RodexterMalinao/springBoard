<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<script type="text/javascript">
var serviceType = [], msisdnlvl = [], channelCode = [];
// each serviceType can access which num lvl
var serviceTypeGoldenNumlvl = { PCCW3G: new Array("GB", "GC", "GD", "N1", "N2"), UNICOM1C2N: new Array("N1", "N2")};
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
cityCode[cityCode.length] = "<c:out value="${r}"/>";
</c:forEach>
function isBlank(str) {
	return str == null || $.trim(str).length == 0;
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
function updateServiceTypeList(msisdnlvl, channelCode) {
	var msisdnlvl = $("select[name=msisdnlvl] option:selected").val();
	var channelCode = $("select[name=channelCode] option:selected").val();
	var serviceTypeNew = serviceType.slice();
	switch (msisdnlvl) {
	case "GB":
	case "GC":
	case "GD":
		serviceTypeNew = filterValues(serviceTypeNew, new Array("PCCW3G"));
		break;
	case "N1":
	case "N2":
	default:
		serviceTypeNew = filterValues(serviceTypeNew, new Array("PCCW3G", "UNICOM1C2N"));
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
	var serviceType = $("select[name=serviceType] option:selected").val();
	switch (serviceType) {
	case "UNICOM1C2N":
		refreshList("cityCode", cityCode);
		$("select[name=cityCode]").attr("disabled", false);
		$(".cityCode").show();
		break;
	default:
		refreshList("cityCode", new Array());
		$("select[name=cityCode]").attr("disabled", true);
		$(".cityCode").hide();
	}
}
function elementToText($element) {
	if ($element.is("input")) {
		$element.before($("<span/>").text($element.val())).hide();
	}
	if ($element.is("select")) {
		$element.before($("<span/>").text($element.find("option:selected").text())).hide();
	}
}
$(document).ready(function() {
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

	$("input[name=quitButton]").click(function() {
		window.location.href = "mobccsmrtstock.html";
	});
	
	$(".stockHistory").click(function(e) {
		e.preventDefault();
		$("form[name=stockHistoryForm]").submit();
		return false;
	});
	
	<c:if test="${param.recordUpdated == 'true'}">
	$("input[type=text],select").filter(":visible").each(function() {
		elementToText($(this));
	});
	$("input[type=submit]").hide();
	</c:if>
	
	
	elementToText($("select[name=serviceType]:visible"));
	
	var msisdnReserveRecordsExist = <c:out value="${not empty command.msisdnReserveRecords}"/>;
	if (msisdnReserveRecordsExist) {
		var names = [ "msisdnlvl", "msisdnStatus", "channelCode" ];
		for (var i = 0; i < names.length; i++) {
			var $select = $("select[name=" + names[i] + "]");
			$select.find("option:not(:selected)").remove();
			$select.attr("disabled", true);
		}
	}
	
	if ($("#reserveId").val().length > 0 || $("#operId").val().length > 0) {
		
		$("#msisdnlvl, #msisdnStatus, #channelCode").attr("disabled", "disabled");
		
	}
	
	
});
</script>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:choose>
<c:when test="${param.recordUpdated == 'true'}">
<div class="contenttextgreen">
	MRT: 
	<a href="#" class="stockHistory contenttextgreen"><c:out value="${param.msisdnUpdated}" /></a>
	is updated.
</div>
<form action="mobccsmrtstock.html" method="post" name="stockHistoryForm">
	<input type="hidden" name="msisdn" value="${param.msisdnUpdated}">
</form>
</c:when>
<c:when test="${param.msisdnReserved == 'true'}">
<div class="contenttext_red">MRT: <c:out value="${param.msisdnUpdated}" /> is reserved by <c:out value="${param.msisdnReservedBy}" />.</div>
</c:when>
<c:when test="${param.override == 'true'}">
<div class="contenttextgreen">Reserved MRT: <c:out value="${param.msisdnUpdated}" /> record is overrided.</div>
</c:when>
</c:choose>
<div style="clear:both"></div>

<form:form method="POST" action="mobccsmrtstockdetail.html">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width: 150px"></colgroup>
<colgroup></colgroup>
<tr class="contenttextgreen">
	<td class="table_title" colspan="2">MRT Pool Maintenance (Modify)</td>
</tr>
<tr>
	<td>MRT</td>
	<td>
		<c:out value="${command.msisdn}"/>
		<form:hidden path="msisdn"/>
		<div style="clear:both"></div>
		<form:errors path="msisdn" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>NUM Type</td>
	<td>
		<pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${command.numType}"/>
		<form:hidden path="numType"/>
		<div style="clear:both"></div>
		<form:errors path="numType" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Service Type</td>
	<td>
		<c:out value="${command.serviceType}"/>
		<form:select path="serviceType" cssStyle="display: none">
			<form:option value="" label="" />
			<form:options items="${serviceTypeList}" itemValue="parmValue" itemLabel="parmValue"/>
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="serviceType" cssClass="error"/>
	</td>
</tr>
<tr class="cityCode">
	<td>City</td>
	<td>
		<c:out value="${command.cityCode}"/>
		<form:select path="cityCode" cssStyle="display: none">
			<form:option value="" label="" />
			<form:options items="${cityCodeList}" />
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
			<form:options items="${msisdnStatusList}" itemLabel="desc" itemValue="status" />
		</form:select>
		<div style="clear:both"></div>
		<form:errors path="msisdnStatus" cssClass="error"/>
		<c:set var="overrideError"><form:errors path="override"/></c:set>
	</td>
</tr>
<tr>
	<td>Reserve ID</td>
	<td>
		<c:out value="${command.reserveId}"/>
		<form:hidden path="reserveId"/>
		<div style="clear:both"></div>
		<form:errors path="reserveId" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Oper ID</td>
	<td>
		<c:out value="${command.operId}"/>
		<form:hidden path="operId"/>
		<div style="clear:both"></div>
		<form:errors path="operId" cssClass="error"/>
	</td>
</tr>




<c:if test="${not empty command.msisdnReserveRecords}">
<tr>
	<td colspan="2">
		<span class="error">
			MRT is reserved by
			<c:forEach items="${command.msisdnReserveRecords}" var="r" varStatus="status">
				<c:if test="${status.index > 0}">, </c:if><c:out value="${r.staffId}"/>
			</c:forEach>
			, do you want to override it?
		</span>
		<form:checkbox path="override"/>
	</td>
</tr>
</c:if>
</table>
<div class="buttonmenubox_R">
	<form:hidden path="rowId"/>
	<input type="submit" value="Save" name="saveButton">
	<input type="button" value="Quit" name="quitButton">
</div>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>