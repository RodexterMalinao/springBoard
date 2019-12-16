<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>

<script type="text/javascript" src="js/jquery.pajinate.js"></script>
<script type="text/javascript">
var numTypeList = [], serviceType = [], msisdnlvl = [], channelCode = [];
<c:forEach items="${numTypeList}" var="r">
	numTypeList[numTypeList.length] = "<c:out value="${r.codeId}"/>";
</c:forEach>


var serviceTypeJson = {H: new Array("PCCW3G", "UNICOM1C2N"), C: new Array("PCCW3G", "CCU1C2N")};

// each serviceType can access which num lvl
var serviceTypeGoldenNumlvl = { PCCW3G: new Array("GB", "GC", "GD", "N1", "N2"), UNICOM1C2N: new Array("N1", "N2"), CCU1C2N: new Array("N1", "N2")};
var goldenNumChannelCode = []; // channel can access golden num
var goldenNumlvl = new Array("GB", "GC"); // golden num lvl 
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
		serviceTypeNew = filterValues(serviceTypeNew, new Array("PCCW3G", "UNICOM1C2N", "CCU1C2N"));
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
$(document).ready(function() {
	$("select[name=serviceType]").change(function() {
		updateMsisdnlvlList();
		updateChannelCodeList();
	}).change();
	$("select[name=msisdnlvl]").change(function() {
		updateServiceTypeList();
		updateChannelCodeList();
	}).change();
	$("select[name=channelCode]").change(function() {
		updateMsisdnlvlList();
		updateChannelCodeList();
	}).change();
	
	$("form[name=mobccsmrtstock]").submit(function(e) {
		if ($("select[name=numType]")[0].selectedIndex == 0
				&& $("select[name=serviceType]")[0].selectedIndex == 0
				&& $("select[name=msisdnlvl]")[0].selectedIndex == 0
				&& $("select[name=channelCode]")[0].selectedIndex == 0
				&& $("select[name=msisdnStatus]")[0].selectedIndex == 0
				&& $.trim($("input[name=orderId]").val()).length == 0
				&& $.trim($("input[name=msisdn]").val()).length == 0
		) {
			alert("Please enter Order ID / MSISDN / at least one criteria");
			e.preventDefault();
			return false;
		}
		$("input[name=orderId]").val($.trim($("input[name=orderId]").val()));
		$("input[name=msisdn]").val($.trim($("input[name=msisdn]").val()));
	});
	
	$("input[name=clearButton]").click(function() {
		$("select[name=serviceType]")[0].selectedIndex = 0;
		refreshList("serviceType", serviceType);
		$("select[name=msisdnlvl]")[0].selectedIndex = 0;
		refreshList("msisdnlvl", msisdnlvl);
		$("select[name=channelCode]")[0].selectedIndex = 0;
		refreshList("channelCode", channelCode);
		
		$("select[name=msisdnStatus]")[0].selectedIndex = 0;
		$("input[name=orderId]").val("");
		$("input[name=msisdn]").val("");
		$("#resultListForm tbody").empty();
	});

	$("input[name=inStockButton]").click(function() {
		window.location.href = "mobccsmrtstockin.html";
	});
	
	$("input[name=backButton]").click(function() {
		window.location.href = "welcome.html";
	});

	$('#page_container').pajinate({ 
		num_page_links_to_display: 0
		, nav_label_first: '|<'
		, nav_label_prev: '<'
		, nav_label_next: '>'
		, nav_label_last: '>|'
		, nav_label_info: '{0} - {1} / {2}'
	});
	$("#page_container .ellipse").remove();
	$(".previous_link").after($(".info_text").detach());
});
</script>

<style type="text/css">
.label { display: inline-block; font-weight: bold; width: 120px }
.page_navigation { text-align: center }
.page_navigation .first_link, .page_navigation .previous_link, .page_navigation .next_link, .page_navigation .last_link { color: black; padding: 0 0.5em }
.info_text { font-weight: bold; padding: 0 3em }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<form:form method="POST" name="mobccsmrtstock">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width: 150px"></colgroup>
<colgroup></colgroup>
<tr class="contenttextgreen">
	<td class="table_title" colspan="2">MRT Pool Maintenance (Main)</td>
</tr>
<tr>
	<td>Number Type</td>
	<td>
		<form:select path="numType">
			<form:option value="" label=""/>
			<form:options items="${numTypeList}" itemValue="codeId" itemLabel="codeDesc"/>
		</form:select>
	</td>
</tr>
<tr>
	<td>Service Type</td>
	<td>
		<form:select path="serviceType">
			<form:option value="" label=""/>
			<form:options items="${serviceTypeList}" itemValue="parmValue" itemLabel="parmValue"/>
		</form:select>
	</td>
</tr>
<tr>
	<td>Grade</td>
	<td>
		<form:select path="msisdnlvl">
			<form:option value="" label=""/>
			<form:options items="${msisdnlvlList}" itemValue="parmValue" itemLabel="parmValue"/>
		</form:select>
	</td>
</tr>
<tr>
	<td>Channel</td>
	<td>
		<form:select path="channelCode">
			<form:option value="" label=""/>
			<form:options items="${channelCodeList}" itemValue="parmValue" itemLabel="parmValue"/>
		</form:select>
	</td>
</tr>
<tr>
	<td>Status</td>
	<td>
		<form:select path="msisdnStatus">
			<form:option value="" label=""/>
			<form:options items="${msisdnStatusList}" itemValue="status" itemLabel="desc"/>
		</form:select>
	</td>
</tr>
<tr>
	<td>Order ID</td>
	<td>
		<form:input path="orderId" maxlength="16"/>
		<div style="clear:both"></div>
		<form:errors path="orderId" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>MRT</td>
	<td>
		<form:input path="msisdn" maxlength="16"/>
		<div style="clear:both"></div>
		<form:errors path="msisdn" cssClass="error"/>
	</td>
</tr>
</table>
<div class="buttonmenubox_R">
	<input type="hidden" name="action" value="search">
	<input type="submit" value="Search">
	<input type="button" name="clearButton" value="Clear">
</div>
</form:form>

<div style="clear:both;padding-top:5px"></div>

<c:if test="${recordList != null}">
<div id="page_container" style="background-color: white" class="contenttext">
<table width="100%" border="1" cellspacing="0" class="contenttext histList" id="resultListForm">
<thead>
<tr class="contenttextgreen">
	<td class="table_title">MRT</td>
	<td class="table_title">SERVICE TYPE</td>
	<td class="table_title">GRADE</td>
	<td class="table_title">STATUS</td>
	<td class="table_title">CHANNEL CODE</td>
	<td class="table_title">STOCK IN DATE</td>
	<td class="table_title">RESERVE ID</td>
	<td class="table_title">OPER ID</td>
	<td class="table_title">NUM TYPE</td>
</tr>
</thead>
<tbody class="content">
<c:choose>
<c:when test="${empty recordList}">
<tr>
	<td colspan="9">No records</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach var="r" items="${recordList}" varStatus="status">
<tr<c:if test="${status.index % 2 == 0}"> class="even"</c:if><c:if test="${status.index >= 20}"> style="display:none"</c:if>>
	<td>
	<c:choose>
	<c:when test="${r.msisdnStatus == 2 || r.msisdnStatus == 5 || r.msisdnStatus == 18 || r.msisdnStatus == 25}">
	<a href="<c:url value="mobccsmrtstockdetail.html"><c:param name="rowId" value="${r.rowId}"/></c:url>">${r.msisdn}</a>
	</c:when>
	<c:when test="${r.msisdnStatus == 19 || r.msisdnStatus == 20 }">
	<a href="mobccsmrtstockorder.html?msisdn=${r.msisdn}&stockInDate=<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${r.stockInDate}"/>">${r.msisdn}</a>
	</c:when>
	<c:otherwise>
	${r.msisdn}
	</c:otherwise>
	</c:choose>
	</td>
	<td>${r.srvNumType}</td>
	<td>${r.msisdnlvl}</td>
	<td>
	<c:forEach items="${msisdnStatusList}" var="m">
	<c:if test="${m.status == r.msisdnStatus}"><c:out value="${m.desc}" /></c:if>
	</c:forEach>
	</td>
	<td>${r.channelCd}</td>
	<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${r.stockInDate}"/></td>
	<td>${r.reserveId}</td>
	<td>${r.resOperId}</td>
	<td><pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${r.numType}"/></td>
</tr>
</c:forEach>
</c:otherwise>
</c:choose>
</tbody>
</table>
<div class="page_navigation"></div>
<span class="info_text"></span>
</div>
</c:if>

<div class="buttonmenubox_R">
	<a class="nextbutton" href="mobccsspecialmrtsummary.html">Special MRT</a>
	<a class="nextbutton" href="mobccsmrtstockin.html">In Stock</a>
	<a class="nextbutton" href="welcome.html">Quit</a>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>