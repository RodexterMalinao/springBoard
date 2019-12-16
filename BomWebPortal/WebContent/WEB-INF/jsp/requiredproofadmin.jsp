<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib"%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ include file="loadingpanel.jsp" %>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.16.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.16.custom.min.js"></script>
<style type="text/css">
.shadedRow  { background-color: #E2F5D3; }
.wrapContent { display: inline-block; width: 100%; padding: 10px 0 }
.supportDocForm { background-color: white; padding: 2px; margin-top: 21px; margin-left: 1px; font-size: 12px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.supportDocForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078; padding: 5px 10px }
.supportDocForm legend { color: #7F7F7F }
.supportDocForm .content { margin-left: 2em }
.supportDocForm .half { margin: 3px 0; width: 45%; display: inline-block; float: left }
.supportDocForm table { border-width: 1px; border-spacing: 0px; border-color: #7F7F7F; border-collapse: collapse; width: 100% }
.supportDocForm table th { border-width: 1px; padding: 3px; border-style: inset; border-color: #7F7F7F; background-color: #ABD078; color: white }
.supportDocForm table td { border-width: 1px; padding: 3px; border-style: inset; border-color: #7F7F7F; text-align: center }
.supportDocForm .springboardForms { display: block }
.supportDocForm .requiredProof .note { color: #7F7F7F }
.supportDocForm .eSignatureInfo .label { vertical-align: top; float: left; margin-top: 5px }
.supportDocForm .eSignatureInfo .input { display: inline; float: left }
.supportDocForm .buttons { padding-top: 1em; text-align: right }
.clear2 { clear: both }
.row .label { display: inline-block; width: 150px }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<script type="text/javascript">
function backHome(){
	window.location ="welcome.html";
}
function padZero(value) {
	return (value < 10 ? "0" : "") + value;
}
function updateVerify($updateVerifyBtn) {
	$.ajax({
		url: "requiredproofadminupdate.html"
		, data: $("#updateVerify").serialize()
		, type: "post"
		, dataType: "json"
		, success: function(data) {
			var $tr = $updateVerifyBtn.parent().parent();
			$.each(data, function(index, value) {
				var verifyDate = new Date(value.verifyDate.time);
				switch (value.verifyInd) {
				case "Y":
					$tr.find(".verifyInd").text("Accept");
					break;
				case "N":
					$tr.find(".verifyInd").text("Reject");
					break;
				default:
					$tr.find(".verifyInd").text("");
				}
				$tr.find(".verifyBy").text(value.verifyBy);
				$tr.find(".verifyDate").text($.datepicker.formatDate("dd/mm/yy", verifyDate) 
						+ " " + padZero(verifyDate.getHours())
						+ ":" + padZero(verifyDate.getMinutes())
						+ ":" + padZero(verifyDate.getSeconds()));
			});
		}
		, error: function() {
			alert("Cannot update Validate Result");
		}
	});
}
function changeCollectMethod() {
	switch ($("input[name=collectMethod]").val()) {
	case "P":
		$(".requiredProof tbody .note").text("");
		$(".requiredProof input[type=checkbox]").show();
		$(".requiredProof select").each(changeWaiveReason);
		break;
	case "D":
	default:
		$(".requiredProof tbody .note").text("Optional");
		// reset collected checkbox status
		$(".requiredProof input[type=checkbox]").each(function() {
			$(this).attr("checked", $(this).data("init"));
		}).hide();
	}
	// no change in collectedInd in this phase
	$(".requiredProof input[type=checkbox]").attr("disabled", true);
}
function changeWaiveReason() {
	var $tdLast = $(this).parent().parent().find("td:last");
	switch ($("input[name=collectMethod]").val()) {
	case "P":
		if (this.selectedIndex == 0) {
			$tdLast.find(".note").text("");
			$tdLast.find("input[type=checkbox]").show();
		} else {
			$tdLast.find(".note").text("Waived");
			$tdLast.find("input[type=checkbox]").hide().attr("checked", $tdLast.find("input[type=checkbox]").data("init"));
		}
		break;
	case "D":
	default:
	}
}
function initCollectMethod() {
	changeCollectMethod();
}
function initCollectedInd() {
	$("input[name$=collectedInd]").filter("[type=checkbox]").each(function() {
		$(this).data("init", $(this).is(":checked"));
	});
}
function notifyVerufyButtonPreviewed($updateVerifyBtn) {
	var authorizedInd = $updateVerifyBtn.data('authorized');
	if (authorizedInd){
		$updateVerifyBtn.attr("disabled", false);
	}
}
$(document).ready(function() {
	changeWaiveReason();
	initCollectMethod();
	initCollectedInd();
	$(".requiredProof select").each(changeWaiveReason).change(changeWaiveReason);
	$(".updateVerifyBtn").click(function(e) {
		e.preventDefault();
		var $tr = $(this).parent().parent();
		$("#updateVerify input[name=docType]").val($tr.find("input[name=docType]").val());
		$("#updateVerify input[name=seqNum]").val($.trim($tr.find(".seqNum").text()));
		$("#dialog .docName").text($tr.find(".docName").text());
		$("#dialog .seqNum").text($("#updateVerify input[name=seqNum]").val());
		var $updateVerifyBtn = $(this);
		$("#dialog").dialog({ 
			modal: true
			, width: 350
			, resizable: false
			, draggable: false
			, buttons: {
				"Accept": function() {
					$("#updateVerify input[name=verifyInd]").val("Y");
					$(this).dialog("close");
					updateVerify($updateVerifyBtn);
				}, "Reject": function() {
					$("#updateVerify input[name=verifyInd]").val("N");
					$(this).dialog("close");
					updateVerify($updateVerifyBtn);
				}
			}
		});
	});
	$(".capturedRecord tbody tr").each(function() {
		var $tr = $(this);
		$tr.find(".downloadBtn").click(function(e) {
			e.preventDefault();
			var url = "requiredproofdownload.html";
			var params = $.param({ orderId: $.trim($(".orderId").text())
				, docType: $tr.find("input[name=docType]").val()
				, seqNum: $.trim($tr.find(".seqNum").text())});
			window.open(url + "?" + params, "_blank", "height=300,width=600,status=no");
			return false;
		});
	});

	$(".requiredProof tbody tr,.capturedRecord tbody tr").each(function() {
		var $tr = $(this);
		if ($tr.index() == 0) {
			$tr.addClass("shadedRow");
			return;
		}
		var $trPrev = $tr.prev();
		if ($tr.find("input[name$=docType]").val() == $trPrev.find("input[name$=docType]").val()) {
			if ($trPrev.hasClass("shadedRow")) {
				$tr.addClass("shadedRow");
			}
		} else {
			if (!($trPrev.hasClass("shadedRow"))) {
				$tr.addClass("shadedRow");
			}
		}
	});
	
	$(".requiredProofSubmitBtn").click(function(e) {
		e.preventDefault();
		$("#requiredProofForm").submit();
		return false;
	});
	
	$(".downloadBtn").click(function(e) {
		e.preventDefault();
        var id = $(this).data('id');
        var $updateVerifyBtn = $(".updateVerifyBtn[data-id="+id+"]");
        notifyVerufyButtonPreviewed($updateVerifyBtn);
		return false;
	});
});
</script>

<div id="dialog" class="dialog" title="Update Verify Result" style="display: none; font-size: smaller">
	<form style="display: none" id="updateVerify">
		<input type="hidden" name="action" value="VERIFY">
		<input type="hidden" name="orderId" value="${form.orderId}">
		<input type="hidden" name="docType">
		<input type="hidden" name="seqNum">
		<input type="hidden" name="verifyInd">
	</form>
	<p>
		<span class="label">Doc Type:</span>
		<span class="docName"></span>
		<span class="docType" style="display: none"></span>
	</p>
	<p>
		<span class="label">Seq Num:</span>
		<span class="seqNum"></span>
	</p>
</div>

<div class="supportDocForm" style="margin: 0">

<p class="contenttextgreen">The order number is <span class="orderId">${form.orderId}</span></p>

<div style="padding-left: 1em">
	<div class="row">
		<span class="label" style="font-weight: bold">Distribution Mode :</span>
		<c:choose>
		<c:when test="${form.order.disMode == 'E'}">e-Signature</c:when>
		<c:when test="${form.order.disMode == 'P'}">Paper</c:when>
		</c:choose>
	</div>
	<div class="row">
		<span class="label" style="font-weight: bold">Collection Method :</span>
		<c:choose>
		<c:when test="${form.order.collectMethod == 'D'}">Digital</c:when>
		<c:when test="${form.order.collectMethod == 'P'}">Paper</c:when>
		</c:choose>
	</div>
	<div style="height: 30px;"></div>
	<c:if test="${actionType == 'NONE'}" >
	<div class="row" style="font-weight: bold">
		Note: <span class="contenttext_red">*</span> "Collected" column can be only updated under collection method of "Paper".
	</div>
	</c:if>
</div>

<div style="height: 30px;"></div>

<fieldset>
	<legend>Required Supporting Document Type</legend>

	<c:if test="${not empty param.updatedCount}">
	<c:choose>
	<c:when test="${param.updatedCount > 0}">
		<p class="contenttextgreen"><c:out value="${param.updatedCount}"/> record(s) were updated</p>
	</c:when>
	<c:otherwise>
		<p class="error">No records were updated</p>
	</c:otherwise>
	</c:choose>
	</c:if>
	
	<form:form name="requiredProofAdminForm" method="POST" commandName="form" id="requiredProofForm">
	<table class="requiredProof">
	<colgroup style="width: 150px"></colgroup>
	<colgroup></colgroup>
	<colgroup style="width: 100px"></colgroup>
	<thead>
	<tr class="contenttextgreen">
		<th class="table_title">Supporting Doc Type</th>
		<th class="table_title" align="center">Waive Reason</th>
		<th class="table_title" align="center">Collected</th>
		<c:if test="${lob == 'MOB'}">
		<th class="table_title" align="center">Last Update By</th>
		<th class="table_title" align="center">Last Update Date</th>
		</c:if>
	</tr>
	</thead>
	<tbody>
	<c:choose>
	<c:when test="${empty form.requiredProofList}">
	<tr class="centerText">
		<td colspan="3">No record.</td>
	</tr>
	</c:when>
	<c:otherwise>
	<c:forEach items="${form.requiredProofList}" var="requiredProof" varStatus="status">
	<tr class="centerText">
		<td>${requiredProof.data.docName}</td>
		<td align="center">
			<my:authorize id="waive">
				<form:select path="requiredProofList[${status.index}].waiveReason" cssStyle="width: 100%">
					<form:option value="" label="Select..."/>
					<c:choose>
					<c:when test="${lob == 'LTS'}">
						<form:options items="${ltsWaiveReasons[requiredProof.data.docType]}" itemValue="waiveReasonCd" itemLabel="waiveReasonDesc"/>
					</c:when>
					<c:otherwise>
						<form:options items="${waiveReasons[requiredProof.data.docType]}" itemValue="waiveReason" itemLabel="waiveDesc"/>
					</c:otherwise>
					</c:choose>
				</form:select>
			</my:authorize>
		</td>
		<td align="center">
			<span class="note"></span>
			<form:checkbox path="requiredProofList[${status.index}].collected"/>
			<form:hidden path="requiredProofList[${status.index}].data.docType"/>
		</td>
		<c:if test="${lob == 'MOB'}">
		<td>
			${requiredProof.data.lastUpdBy}
		</td>
		<td>
			<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${requiredProof.data.lastUpdDate}" />
		</td>
		</c:if>
	</tr>
	</c:forEach>
	</c:otherwise>
	</c:choose>
	</tbody>
	</table>
	<div style="clear: both"></div>
	<div class="buttonmenubox_R" id="buttonArea">
		<input type="hidden" name="collectMethod" value="${form.order.collectMethod}"/>
		<!-- <a href="#" class="nextbutton requiredProofSubmitBtn">Update</a> -->
		<my:authorize id="waive">
			<input type="submit" value="Update">
		</my:authorize>
	</div>
</form:form>

</fieldset>

<div style="height: 30px;"></div>

<fieldset>
	<legend>Captured Record</legend>
	<c:choose>
	<c:when test="${lob == 'MOB'}">
	<!-- new captured record applies to MOB only -->
	<table class="capturedRecord">
	<thead>
	<tr class="contenttextgreen">
		<th class="table_title">Support Doc Type</th>
		<th class="table_title">Seq Num</th>
		<th class="table_title">Capture By</th>
		<th class="table_title">Catpure Date / Time</th>
		<th class="table_title">Validate Result</th>
		<th class="table_title">Last Validate By</th>
		<th class="table_title">Last Validate Date</th>
		<th class="table_title">Verify Action</th>
		<th class="table_title">Document Preview</th>
	</tr>
	</thead>
	<tbody>
	<c:choose>
	<c:when test="${empty capturedRecordList}">
	<tr class="centerText">
		<td colspan="9">No record.</td>
	</tr>
	</c:when>
	<c:otherwise>
	<c:forEach items="${capturedRecordList}" var="capturedRecord" varStatus="itemStatus">
	<tr class="centerText">
		<td class="docName">${capturedRecord.docName}</td>
		<td class="seqNum">${capturedRecord.seqNum}</td>
		<td>${capturedRecord.captureBy}</td>
		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${capturedRecord.captureDate}" /></td>
		<td class="verifyInd">
			<c:choose>
			<c:when test="${capturedRecord.verifyInd == 'Y'}">Accept</c:when>
			<c:when test="${capturedRecord.verifyInd == 'N'}">Reject</c:when>
			<c:otherwise></c:otherwise>
			</c:choose>
		</td>
		<td class="verifyBy">${capturedRecord.verifyBy}</td>
		<td class="verifyDate"><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${capturedRecord.verifyDate}" /></td>
		<td>
			<sb-util:authorized permission="verify" var="authorized">
			  	<c:set var="previewed" value="${not empty capturedRecord.verifyInd }"/>
				<input type="button" class="updateVerifyBtn" data-id="${itemStatus.index}" data-authorized="${authorized}" value="Update" ${previewed and authorized ? "" : "disabled"}>
			</sb-util:authorized>			
		</td>
		<td>
			<input type="hidden" name="docType" value="${capturedRecord.docType}"/>
			<sb-util:authorized permission="image" var="authorized">
				<input type="button" class="downloadBtn" data-id="${itemStatus.index}" data-authorized="${authorized}" value="Preview">
			</sb-util:authorized>		
		</td>
	</tr>
	</c:forEach>
	</c:otherwise>
	</c:choose>
	</tbody>
	</table>

	</c:when>
	<c:otherwise>

	<table class="capturedRecord">
	<thead>
	<tr class="contenttextgreen">
		<td class="table_title"><b>Supporting Doc Type</b></td>
		<td class="table_title" align="center"><b>Seq Num</b></td>
		<td class="table_title" align="center"><b>Capture By</b></td>
		<td class="table_title"><b>Capture Date/Time</b></td>
	</tr>
	</thead>
	<tbody>
	<c:if test="${empty capturedRecordList}">
	<tr>
		<td colspan="4">No record.</td>
	</tr>
	</c:if>
	<c:forEach items="${capturedRecordList}" var="capturedRecord">
	<tr>
		<td>${capturedRecord.docName }</td>
		<td align="center">${capturedRecord.seqNum }</td>
		<td align="center">${capturedRecord.captureBy }</td>
		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${capturedRecord.captureDate }" /></td>
	</tr>
	</c:forEach>
	</tbody>
	</table>

	</c:otherwise>
	</c:choose>

</fieldset>

<div style="height: 30px;"></div>
<c:if test="${actionType == 'NONE'}" >
<div class="buttonmenubox_R" id="buttonArea">
	<a href="supportdocadmin.html?reload=true" class="nextbutton3" > Supporting Document Admin </a>
</div>
</c:if>
</div>

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>