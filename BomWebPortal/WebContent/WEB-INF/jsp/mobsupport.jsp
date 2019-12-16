<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><!doctype html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
	<style type="text/css">
body { font-family: sans-serif }
.selectResultTable { border-collapse: separate; border-width: 0px; border-spacing: 0px; font-size:15px; table-layout:fixed }
.even, .title { background-color: #C0C0C0 }
table, td, th { border:1px solid black }
#sql { border: dotted 1px; background-color: #C0C0C0 }
	</style>

	<script type="text/javascript" src="js/jquery-1.4.4.js" ></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.9.custom.min.js"></script>
	<script>
// http://stackoverflow.com/questions/646628/javascript-startswith
if (typeof String.prototype.startsWith != 'function') {
	String.prototype.startsWith = function (str){
		return this.slice(0, str.length) == str;
	};
}

$(function() {
	$('#nAppDateDP').datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-6M",
		maxDate : "+6M" //Y is year, M is month, D is day
	});
});

function editDsRejectMsg() {
	if ("${mobSupport.channelId}" == 10 && $("#orderStatusSelection option:selected").val() == "REJECTED") {
		$("#err_msg_tr").hide();
		$("#err_msg_edit_tr").show();
	} else {
		$("#err_msg_tr").show();
		$("#err_msg_edit_tr").hide();
	}
}

$(document).ready(function() {
	$('#orderSearchButton').click(function(e) {
		$('#actionType').val('ORDERSEARCH');
		$('#mobSupportTop').submit();
	});
	$('#clearButton').click(function(e) {
		$('#orderIdDropDown').val('');
		$('#orderIdTextField').val('');
		$('#actionType').val('CLEAR');
		$('#mobSupportTop').submit();
	});
	$('#readFromBOMButton').click(function(e) {
		$('#actionType').val('READFROMBOM');
		$('#orderIdTextField').val('${mobSupport.orderId}');
		$('#mobSupportTop').submit();
	});
	
	$('#backupOrderButton').click(function(e) {
		$('#orderAction').val('BACKUP_ORDER');
		$('#mobSupportBottom').submit();
	});
	
	$('#updateSIMStatusButton').click(function(e) {
		$('#orderAction').val('SIM_UPDATE');
		$('#mobSupportBottom').submit();
	});
	
	$('#updateMRTStatusButton').click(function(e) {
		$('#orderAction').val('MRT_UPDATE');
		$('#mobSupportBottom').submit();
	});
	
	$('#updateButton').click(function(e) {
		$('#orderAction').val('UPDATE');
		$('#mobSupportBottom').submit();
	});
	
	$('#memUpdateButton').click(function(e) {
		$('#orderAction').val('MEMBER_UPDATE');
		$('#mobSupportBottom').submit();
	});
	
	$('#ccsUpdateButton').click(function(e) {
		$('#orderAction').val('CCS_UPDATE');
		$('#mobSupportBottom').submit();
	});
	
	$('#ccsUpdateReasonCd').click(function(e) {
		$('#orderAction').val('CCS_UPDATE_ReasonCd');
		$('#mobSupportBottom').submit();
	});
	
	$('#conflictOrderSearchButton').click(function(e) {
		$('#orderAction').val('CONFLICTORDERSEARCH');
		$('#mobSupportBottom').submit();
	});
	
	$('#sendEmailtoFS').click(function(e) {
		//alert("hihi");
		var mailURL = "mailto:";
		mailURL += $('#emailTO').html();
		mailURL += "?cc=" + encodeURIComponent($('#emailCC').html());
		mailURL += "&subject=" + encodeURIComponent($('#emailSubject').html());
		mailURL += "&body=" + encodeURIComponent($('#emailBody').html());
		window.location.href = mailURL;
	});
	
	$('#sendEmailtoCCS').click(function(e) {
		//alert("hihi");
		var mailURL = "mailto:";
		mailURL += $('#ccsemailTO').html();
		mailURL += "?cc=" + encodeURIComponent($('#ccsemailCC').html());
		mailURL += "&subject=" + encodeURIComponent($('#ccsemailSubject').html());
		mailURL += "&body=" + encodeURIComponent($('#ccsemailBody').html());
		window.location.href = mailURL;
	});
	
	//$('#nAppDateDP').datepicker("setDate", new Date());
	
	if ('${mobSupport.oOcid}' != null && '${mobSupport.oOcid}' != '') {
		$('#orderStatusSelection').attr('disabled', true);
		$('#updateButton').attr('disabled', true);
		$('#nSimStatusId').attr('disabled', true);
		$('#nMrtStatusId').attr('disabled', true);
	}else {
		$('#orderStatusSelection').attr('disabled', false);
		if ('${mobSupport.orderId}' != null && '${mobSupport.orderId}' != '') {
			$('#updateButton').attr('disabled', false);
		}
	}
	
	$("#orderStatusSelection").change(function() {
		editDsRejectMsg();
	}).change();
	
	
	if ('${success}' == 'Update Successful') {
		$('#updateButton').attr('disabled', true);
	}
		
	if ($("font[colour='red']").text() != null && $("font[colour='red']").text() != "") {
		$('#updateButton').attr('disabled', true);
	}
	
	if ('${mobSupport.orderId}' != null && '${mobSupport.orderId}' != '') {
		$("option[value='${mobSupport.orderId}']").attr('selected', 'selected');
	}
	
	var resInd = <c:out value="${empty mobSupport.reserveId}"/>;
	if(resInd) {
		$("#nMrtStatusId option[value='18']").remove();
	}
	
	var ccsOrder = <c:out value="${mobSupport.channelId == 2}"/>;
	if (ccsOrder) {
		// print out recommended update
		var orderStatus = $.trim($(".oOrderStatus").text());
		var checkPoint = $.trim($(".oCheckPoint").text());
		var reasonCd = $.trim($(".oReasonCd").text());
		var nOrderStatus = "";
		var nCheckPoint = "";
		var nReasonCd = "";
		switch (orderStatus) {
		case "01": // long process
			switch (checkPoint) {
			case "449":
			case "499":
				nOrderStatus = "01";
				nCheckPoint = "400";
				nReasonCd = "";
				break;
			}
			break;
		case "03": // long process(cancel order)
			switch (checkPoint) {
			case "C449":
			case "C499":
				nOrderStatus = "03";
				nCheckPoint = "C400";
				nReasonCd = "";
				break;
			}
			break;
		case "99": // fail order
			switch (checkPoint) {
			case "999":
				if (reasonCd.startsWith("D")) {
					nOrderStatus = "01";
					nCheckPoint = "400";
					nReasonCd = "";
				} else if (reasonCd.startsWith("L")) {
					nOrderStatus = "03";
					nCheckPoint = "C400";
					nReasonCd = "";
				}
				break;
			}
			break;
		default:
		}
		
		if (nOrderStatus.length != 0 || nCheckPoint.length != 0 || nReasonCd.length != 0) {
			$("#sql .nOrderStatus").text(nOrderStatus);
			$("#sql .nCheckPoint").text(nCheckPoint);
			$("#sql .nReasonCd").text(nReasonCd);
			$("#sql .oOrderStatus").text(orderStatus);
			$("#sql .oCheckPoint").text(checkPoint);
			$("#sql .orderId").text($.trim($(".orderId").text()));
			$("#sql").show();
			$("#ccsUpdate").show();
			$("#ccsUpdateReason").show();
		}
	}
	
	var orderId = $.trim($(".orderId").text());
	if (orderId.indexOf('A', orderId.length - 1) != -1 || orderId.indexOf('B', orderId.length - 1) != -1) {
		// print out recommended update
		var orderStatus = $.trim($(".oOrderStatus").text());
		var checkPoint = $.trim($(".oCheckPoint").text());
		var nOrderStatus = "";
		var nCheckPoint = "";
		if (orderStatus == '79') {
			if (checkPoint == '449' || checkPoint == '999') {
				nOrderStatus = "01";
				nCheckPoint = "400";
			}
		}
		if (nOrderStatus.length != 0 || nCheckPoint.length != 0) {
			$("#memUpdate").show();
		}
	}
});
	</script>
</head>
<body>
<form:form commandName="mobSupport" name="mobSupportForm" method="GET" id="mobSupportTop">
	Order ID : 
	<form:select path="orderIdFmDropDown" id="orderIdDropDown">
		<c:forEach var="result" items="${alertOrderList}">
			<option value="${result[0]}" class="orderIdOption">${result[0]} [${result[1]} min]</option>
		</c:forEach>
	</form:select>
	<form:input path="orderIdFmTextField" id="orderIdTextField" />
	<input type="submit" id="orderSearchButton" value="Search">
	<input type="submit" id="clearButton" value="Clear">
	<input type="button" value="Home" onclick="window.location.href='welcome.html'" >
	<form:hidden path="actionType"/>
</form:form>

<hr>

<form:form commandName="mobSupport" name="mobSupportForm" method="POST" id="mobSupportBottom">
	<input type="hidden" id="orderAction" name="orderAction" />
	<table class="selectResultTable" >
		<tr class="title">
			<td colspan="1" >&nbsp;</td>
			<td colspan="1" align="center"><b>Values in DB</b></td>
			<td colspan="3" align="center"><b>Values to be updated</b></td>
		</tr>
		<tr class="even">
			<td colspan="5">
				<font size="5">
				<b>Order ID:</b>
					<c:choose>
						<c:when test="${mobSupport.channelId == '2'}">
							<c:choose>
							<c:when test="${fn:substring(mobSupport.orderId, fn:length(mobSupport.orderId)-1, fn:length(mobSupport.orderId)) == 'A' 
								or fn:substring(mobSupport.orderId, fn:length(mobSupport.orderId)-1, fn:length(mobSupport.orderId)) == 'B'}">
								<a href="mobccspreview.html?orderId=${fn:substring(mobSupport.orderId, 0, fn:length(mobSupport.orderId)-1)}&action=ENQUIRY" target="orderView">
							</c:when>
							<c:otherwise>
								<a href="mobccspreview.html?orderId=${mobSupport.orderId}&action=ENQUIRY" target="orderView">
							</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
							<c:when test="${fn:substring(mobSupport.orderId, fn:length(mobSupport.orderId)-1, fn:length(mobSupport.orderId)) == 'A' 
								or fn:substring(mobSupport.orderId, fn:length(mobSupport.orderId)-1, fn:length(mobSupport.orderId)) == 'B'}">
								<a href="orderdetail.html?orderId=${fn:substring(mobSupport.orderId,0, fn:length(mobSupport.orderId)-1)}&action=ENQUIRY" target="orderView">
							</c:when>
							<c:otherwise>
								<a href="orderdetail.html?orderId=${mobSupport.orderId}&action=ENQUIRY" target="orderView">
							</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<span class="orderId"><c:out value="${mobSupport.orderId}"/></span>
					</a>
				</font>
			</td>
		</tr>
		<tr>
			<td colspan="1">OCID</td>
			<td colspan="1">&nbsp;<c:out value="${mobSupport.oOcid}"/>&nbsp;</td>
			<td colspan="2">&nbsp;<c:out value="${mobSupport.nOcid}"/>&nbsp;</td>
			<td colspan="1">
				<input type="button" id="readFromBOMButton" value="Retrieve BOM OCID (BULK)">
			</td>
		</tr>
		<tr>
			<td colspan="1">Status</td>
			<td colspan="1" class="oOrderStatus">
				<c:out value="${mobSupport.oOrderStatus}"/>
			</td>
			<td colspan="3">
				<c:choose>
				<c:when test="${(mobSupport.channelId == '1' && mobSupport.oOrderStatus != '79') || mobSupport.channelId == '3' || mobSupport.channelId == '10'}">
					<form:select path="nOrderStatus" id="orderStatusSelection">
						<form:options items="${orderStatusList}" />
						<c:if test="${mobSupport.channelId == '10'}">
							<form:option value="REJECTED">REJECTED</form:option>
						</c:if>
					</form:select>
				</c:when>
				<c:when test="${mobSupport.channelId == '2'}">
				</c:when>
				</c:choose>
			</td>
		</tr>
		<c:if test="${mobSupport.channelId == '2' || mobSupport.channelId == '10' || mobSupport.oOrderStatus == '79'}">
		<tr>
			<td>
				Check Point
			</td>
			<td colspan="4" class="oCheckPoint">
				<c:out value="${mobSupport.oCheckPoint}"/>
			</td>
		</tr>
		<tr>
			<td>
				Reason Code
			</td>
			<td colspan="1" class="oReasonCd">
				<c:out value="${mobSupport.oReasonCd}"/>
			</td>
			<td colspan="3">
				<c:if test="${mobSupport.channelId == '2' && mobSupport.oReasonCd != 'C006'}">
					<!-- Account overdue -->
					<c:if test="${fn:contains(mobSupport.errMsg, 'Account overdue')}"> 
						<input type="button" id="ccsUpdateReasonCd" value="Update order status to C006">
						<font color='red'><b></b></font>
					</c:if>
					<!-- Insert bulk new act order I007 This Mobile no. exists is active profile -->
					<c:if test="${fn:contains(mobSupport.errMsg, 'I007')}"> 
						<input type="button" id="ccsUpdateReasonCd" value="Update order status to C006">
						<font color='red'><b></b></font>
					</c:if>
					<!-- Insert bulk new act order I008(Call Centre) [Not clone] -->
					<c:if test="${fn:contains(mobSupport.errMsg, 'I008') && empty mobSupport.cloneOrderId}"> 
						<input type="button" id="ccsUpdateReasonCd" value="Update order status to C006">
						<font color='red'><b></b></font>
					</c:if>
				</c:if>
			</td>		
		</tr>
		<c:if test="${not empty mobSupport.cloneOrderId }">
		<tr>
			<td>
				Clone Order ID
			</td>
			<td colspan="4" >
				<c:out value="${mobSupport.cloneOrderId}"/>
			</td>
		</tr>
		</c:if>
		</c:if>
		<tr>
			<td colspan="1">App Date</td>
			<td colspan="1">
				&nbsp;<c:out value="${mobSupport.oAppDate}"/>&nbsp;
			</td>
			<td colspan="3">
				<form:input path="nAppDate" id="nAppDateDP" readonly="true"/>
			</td>
		</tr>
		<tr id="err_msg_tr">
			<td colspan="1">Error msg</td>
			<td colspan="4">&nbsp;<c:out value="${mobSupport.errMsg}"/>&nbsp;</td>
		</tr>
		<tr id="err_msg_edit_tr" style="display: none;">
			<td colspan="1">Error msg</td>
			<td colspan="4"><form:input path="nErrMsg" cssStyle="width: 90%"/></td>
		</tr>
		<tr class="even">
			<td colspan="5" ><b>SIM :</b> <c:out value="${mobSupport.sim}"/></td>
		</tr>
		<tr>
			<td colspan="1">Status</td>
			<td colspan="1">
				<c:choose>
					<c:when test="${not empty mobSupport.oSimStatus}">
						<c:out value="${mobSupport.oSimStatus}"/> - 
						<c:out value="${mobSupport.oSimStatusDesc}"/>
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
			<td colspan="3">
				<c:choose>
					<c:when test="${mobSupport.bomSimStatus == 'Y'}">
						<form:select path="nSimStatus" disabled="true" id="nSimStatusId">
							<form:option value="" label=""/>
							<form:options items="${simStatusList}" itemLabel="label" itemValue="value"/>
						</form:select>
					</c:when>
					<c:otherwise>
						<form:select path="nSimStatus" id="nSimStatusId">
							<form:option value="" label=""/>
							<form:options items="${simStatusList}" itemLabel="label" itemValue="value"/>
						</form:select>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="1">Active/Pend in BOM</td>
			<td colspan="1" align="center"><c:out value="${mobSupport.bomSimStatus}"/></td>
			<td colspan="3">&nbsp;</td>
		</tr>
		<c:if test="true">
			<tr class="even">
				<td colspan="5" >
					<b>MRT : </b><form:hidden path="mrt" /><c:out value="${mobSupport.mrt}"/>
					<c:choose>
						<c:when test="${(mobSupport.mnpInd == 'Y' && not empty mobSupport.mrt)}">
							<c:out value=" (MNP)"/>
						</c:when>
						<c:when test="${(mobSupport.mnpInd == 'N' && not empty mobSupport.mrt)}">
							<c:out value=" (New Number)"/>
						</c:when>
						<c:when test="${(mobSupport.mnpInd == 'A' && not empty mobSupport.mrt)}">
							<c:out value=" (New Number + MNP)"/>
						</c:when>
						<c:when test="${(mobSupport.mnpInd == 'MUPS01' && not empty mobSupport.mrt)}">
							<c:out value=" (MUPS01 - New Number)"/>
						</c:when>
						<c:when test="${(mobSupport.mnpInd == 'MUPS02' && not empty mobSupport.mrt)}">
							<c:out value=" (MUPS02 - New Number + Further MNP)"/>
						</c:when>
						<c:when test="${(mobSupport.mnpInd == 'MUPS03' && not empty mobSupport.mrt)}">
							<c:out value=" (MUPS03 - Migrate Current Line (With Subscriber))"/>
						</c:when>
						<c:when test="${(mobSupport.mnpInd == 'MUPS04' && not empty mobSupport.mrt)}">
							<c:out value=" (MUPS04 - Migrate Current Line (Without Subscriber))"/>
						</c:when>
						<c:when test="${(mobSupport.mnpInd == 'MUPS05' && not empty mobSupport.mrt)}">
							<c:out value=" (MUPS05 - Direct MNP))"/>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td colspan="1">Status</td>
				<td colspan="1">
					<c:choose>
						<c:when test="${not empty mobSupport.oMrtStatus}">
							<c:out value="${mobSupport.oMrtStatus}"/> - 
							<c:out value="${mobSupport.oMrtStatusDesc}"/>
						</c:when>
						<c:otherwise>
							&nbsp;
						</c:otherwise>
					</c:choose>
				</td>
				<td colspan="3">
					<c:choose>
						<c:when test="${mobSupport.mnpInd == 'Y' || mobSupport.bomMrtStatus == 'Y'}">
							<form:select path="nMrtStatus" disabled="true" id="nMrtStatusId">
								<form:option value="" label=""/>
								<form:options items="${mrtStatusList}" itemLabel="label" itemValue="value"/>
							</form:select>
						</c:when>
						<c:otherwise>
							<form:select path="nMrtStatus" id="nMrtStatusId">
								<form:option value="" label=""/>
								<form:options items="${mrtStatusList}" itemLabel="label" itemValue="value"/>
							</form:select>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td colspan="1">Active/Pend in BOM</td>
				<td colspan="1" align="center"><c:out value="${mobSupport.bomMrtStatus}"/></td>
				<td colspan="3">&nbsp;</td>
			</tr>
			<c:if test="${not empty mobSupport.reserveId }">
				<tr>
					<td>
						Reserve ID
					</td>
					<td colspan="4" >
						<c:out value="${mobSupport.reserveId}"/>
					</td>
				</tr>
			</c:if>
		</c:if>
		<tr class="even">
			<td colspan="5"><b>Shop :</b> <c:out value="${mobSupport.shopCode}"/></td>
		</tr>
		<tr>
			<td colspan="1">Sales Name</td>
			<td colspan="4">&nbsp;<c:out value="${mobSupport.salesName}"/>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="1">Tel</td>
			<td colspan="4">&nbsp;<c:out value="${mobSupport.shopTel}"/>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="1">Email</td>
			<td colspan="4">&nbsp;<c:out value="${mobSupport.shopEmail}"/>&nbsp;</td>
		</tr>
	</table>
	<br>
	<c:out value="${success}"/>
	<form:errors path="*" cssStyle="color: red"/>
	<br/>
	<c:if test="${not empty mobSupport.conflictOrder}">
	SIM/MRT conflicts with:
	<form:select path="conflictOrderId" id="conflictOrderId">
		<c:forEach var="conflictOrderItem" items="${mobSupport.conflictOrder}">
			<option value="${conflictOrderItem[0]}">${conflictOrderItem[0]} (Status: ${conflictOrderItem[1]})</option>
		</c:forEach>
	</form:select>
	<input type="submit" id="conflictOrderSearchButton" value="Goto"><br/>
	</c:if>
	<c:if test="${not empty mobSupport.orderId}">
	<input type="button" id="backupOrderButton" value="Backup order"><br/>
	</c:if>
	<c:if test="${not empty mobSupport.orderId}">
	<input type="button" id="updateSIMStatusButton" value="Update SIM Status"><br/>
	</c:if>
	<c:if test="${not empty mobSupport.orderId}">
	<input type="button" id="updateMRTStatusButton" value="Update MRT Status"><br/>
	</c:if>
	<!-- channelId: ${mobSupport.channelId} -->
	<c:choose>
	<c:when test="${(mobSupport.channelId == '1' && mobSupport.oOrderStatus != '79') || mobSupport.channelId == '3' || mobSupport.channelId == '10'}">
	<input type="button" id="updateButton" value="Update Order/MRT/SIM Status">
	</c:when>
	</c:choose>
	<span id="memUpdate" style="display:none">
	<c:if test="${mobSupport.orderId != null && mobSupport.orderId != ''}">
		<c:if test="${mobSupport.oOcid == null || mobSupport.oOcid == ''}">
			<c:if test="${mobSupport.oSimStatus != '8'}">
				<c:if test="${mobSupport.bomSimStatus == 'N'}">
					<c:if test="${( (mobSupport.mnpInd == 'N' || mobSupport.mnpInd == 'A') && not empty mobSupport.mrt)}"> 
					<c:if test="${mobSupport.bomMrtStatus == 'N'}">
						<input type="button" id="memUpdateButton" value="Re-signoff Member order">
						<font color='red'><b></b></font>
					</c:if>
					</c:if>
				</c:if>
			</c:if>
		</c:if>
	</c:if>
	</span>
	<span id="ccsUpdate" style="display:none">
	<c:if test="${mobSupport.orderId != null && mobSupport.orderId != ''}">
		<c:if test="${mobSupport.oOcid == null || mobSupport.oOcid == ''}">
			<c:if test="${mobSupport.oSimStatus != '8'}">
				<c:if test="${mobSupport.bomSimStatus == 'N'}">
					<c:if test="${( (mobSupport.mnpInd == 'N' || mobSupport.mnpInd == 'A') && not empty mobSupport.mrt)}"> 
					<c:if test="${mobSupport.bomMrtStatus == 'N'}">
						<input type="button" id="ccsUpdateButton" value="Re-signoff CCS order">
						<font color='red'><b></b></font>
					</c:if>
					</c:if>
					<c:if test="${(mobSupport.mnpInd == 'Y' && not empty mobSupport.mrt)}">
						<input type="button" id="ccsUpdateButton" value="Re-signoff CCS order">
						<font color='red'><b></b></font>
					</c:if>
				</c:if>
			</c:if>
			<c:set var="btnFlg" value="N"/>
			<c:if test="${mobSupport.bomSimStatus == 'Y' || mobSupport.bomMrtStatus == 'Y'}">
			<div style="display:none;">
				<span id="emailTO">PCCWF%26SHKMobileOpn@pccw.com;Siu-Tat.Yau@pccw.com</span>
				<span id="emailCC">BOMSMOBS@pccw.com</span>
				<span id="emailSubject">Failed submission of order ${mobSupport.orderId}</span>
				<span id="emailBody">Dear Tat / FS Mobile team,

Would you please help to cancel the order <c:forEach var="conflictOrderItem" items="${mobSupport.conflictOrder}"><c:if test="${conflictOrderItem[1] == '03'}">${conflictOrderItem[0]}(OCID:${conflictOrderItem[2]}) <c:set var="btnFlg" value="Y"/></c:if></c:forEach>in BOM? It triggers the failure of order submission of ${mobSupport.orderId}.

MRT: ${mobSupport.mrt}
SIM#: ${mobSupport.sim}

Thanks!</span>
				</div>
				<c:if test="${btnFlg == 'Y'}">
					<input type="button" id="sendEmailtoFS" value="Send Email to F&S Team">
				</c:if>
			</c:if>
		</c:if>
	</c:if>
	</span>
	<span id="ccsUpdateReason" style="display:none">
	<c:if test="${fn:contains(mobSupport.errMsg, 'Account overdue')
				||fn:contains(mobSupport.errMsg, 'I007') 
				||(fn:contains(mobSupport.errMsg, 'I008') && empty mobSupport.cloneOrderId)}">
		<c:if test="${mobSupport.channelId == '2' && mobSupport.oReasonCd != 'C006'}">
			<div style="display:none;">
				<span id="ccsemailTO">Katy.WS.Chan@pccw.com;Leo.TC.Chu@pccw.com;PCCWCallCenter%96SalesPlanning%26DevelopmentMobile@pccw.com</span>
				<span id="ccsemailCC">BOMSMOBS@pccw.com</span>
				<span id="ccsemailSubject">Failed submission of order ${mobSupport.orderId}</span>
				<span id="ccsemailBody">Dear CCS Mobile Supporting Team, 
				
<c:if test="${fn:contains(mobSupport.errMsg, 'I007') || (fn:contains(mobSupport.errMsg, 'I008') && empty mobSupport.cloneOrderId)}"> Since the mnp mobile number has been used by another active order, SB order (${mobSupport.orderId}) failed in BOM submission.</c:if><c:if test="${fn:contains(mobSupport.errMsg, 'Account overdue')}">Due to account overdue, SB order (${mobSupport.orderId}) failed in BOM submission.</c:if>The order status will be patched as "Fallout - Payment Reject (Sales Follow up)", please ask the corresponding sales to follow up the SB order.

MRT: ${mobSupport.mrt}
SIM#: ${mobSupport.sim}
	
Thanks!</span></div>
			<input type="button" id="sendEmailtoCCS" value="Send Email to Call Centre">
		</c:if>
	</c:if>
	</span>
	
</form:form>

<pre id="sql" style="display:none">UPDATE bomweb_order
SET
  ORDER_STATUS = '<span class="nOrderStatus"></span>'
  , CHECK_POINT = '<span class="nCheckPoint"></span>'
  , REASON_CD = '<span class="nReasonCd"></span>'
  , ERR_CD = null
  , ERR_MSG = null
  , LAST_UPDATE_BY = 'SBSUPPORT'
  , LAST_UPDATE_DATE = sysdate
WHERE
  ORDER_ID = '<span class="orderId"></span>'
  AND ORDER_STATUS = '<span class="oOrderStatus"></span>'
  AND CHECK_POINT = '<span class="oCheckPoint"></span>'
;</pre>

</body>
</html>