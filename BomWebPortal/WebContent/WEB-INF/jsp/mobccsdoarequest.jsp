<%@ include file="/WEB-INF/jsp/header.jsp"%>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script type="text/javascript">
function validateMktSerialIdPattern(serial) {
	if (serial == null || $.trim(serial).length == 0) {
		return false;
	}
	// check for pattern
	if (!(/^DOA[0-9]{8}[0-9]{3}[A-Z]$/.test(serial))) {
		return false;
	}
	// check date value
	var serialDate = serial.substring(3, 11);
	try {
		$.datepicker.parseDate("yymmdd", serialDate);
	} catch (e) {
		return false;
	}
	return true;
}
function disableForm() {
	$("input[type=checkbox]").attr("disabled", true);
	$("textarea[name=remarks]").attr("disabled", true);
	$("input[name=mktSerialId]").attr("disabled", true);
}
function authorized() {
	$("input[name=approved]").val(true);
	$("input[name=approveButton]").attr("disabled", true).val("UM Approved");
	$("input[type=submit]").val("Save");
}
$(document).ready(function() {
	$("input[name=stocks]:even").parent().addClass("even");
	$("input[name=reasons]:even").parent().addClass("even");
	
	$("form[name=submitForm]").submit(function(e) {
		$(".error").hide();
		$(".error_msg").hide();
		
		if ($("input[name=approved]").val() == "true") {
			if ($("input[name=stocks]:checked").length == 0) {
				$(".error_stocks").show();
			}
			if ($("input[name=reasons]:checked").length == 0 && $.trim($("textarea[name=remarks]").val()).length == 0) {
				$(".error_reasons").show();
			}
			if ($("textarea[name=remarks]").val().length > 500) {
				$(".error_remarks").show();
			}
			// 2nd doa / 10days+
			if ($("input[name=mktSerialId]").is(":visible")) {
				if ($.trim($("input[name=mktSerialId]").val()).length == 0) {
					$(".error_mktSerialId").text("Requires Serial #").show();
				} else {
					if (!validateMktSerialIdPattern($("input[name=mktSerialId]").val())) {
						$(".error_mktSerialId").text("Invalid Serial pattern").show();
					}
				}
			}
		} else {
			if ($("input[name=stocks]:checked").length == 0 && $("input[name=reasons]:checked").length == 0 && $.trim($("textarea[name=remarks]").val()).length == 0) {
				$(".error_draft").show();
			}
		}
		if ($(".error_msg:visible").length > 0) {
			e.preventDefault();
			return false;
		}
	});
	
	$("input[name=approveButton]").click(function(e) {
		e.preventDefault();
		// http://msdn.microsoft.com/en-us/library/ie/ms536759%28v=vs.85%29.aspx
		var sURL = "<c:url value="mobccsauthorize.html"><c:param name="action" value="AU04"/><c:param name="orderId" value="${param.orderId}"/></c:url>";
		var vArguments = self;
		var sFeatures = "dialogHeight:230px;dialogLeft:0;dialogTop:0;dialogWidth:400px;resizable=yes;scroll=yes;status=no";
		window.showModalDialog(sURL, vArguments, sFeatures);
		return false;
	});
	
	$("input[name=cancelButton]").click(function() {
		$("form[name=cancelForm]").submit();
	});
	
	$("input[name=enterSerialButton]").click(function(e) {
		$(this).hide();
		$(".enterSerialBlock").show();
		$("input[name=approved]").val(true);
		$("input[type=submit]").val("Save");
	});
	
	<c:choose>
	<c:when test="${param.recordUpdated == 'true'}">
	disableForm();
	</c:when>
	<c:when test="${command.orderStatus == '02' || (command.orderStatus == '99' && command.checkPoint == '999' && (command.reasonCd == 'N000'))}">
	</c:when>
	<c:otherwise>
	disableForm();
	</c:otherwise>
	</c:choose>

	$("input[name=backButton]").click(function() {
		var callback = (window.dialogArguments ? window.dialogArguments : window.opener).reloadcallback;
		if (callback) callback();
		self.close();
	});
});
</script>

<style type="text/css">
.hsItemDesc { border-bottom: dotted 1px; }
.stockList span input, .rejectReasons span input { vertical-align: middle }
.stockList span, .rejectReasons span { display: block; vertical-align: top }
.even { background-color: rgb(232,255,232); }
.blueHeader { background-color: rgb(232, 242, 254) }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<c:if test="${param.recordUpdated == 'true'}">
<h2 class="contenttextgreen" style="margin-top:0"><c:if test="${param.saveAsDraft == 'true'}">Draft </c:if>Record updated<c:if test="${param.saveAsDraft == 'false'}">, DOA stock changed</c:if></h2>
</c:if>
<c:if test="${param.errorStockChange == 'true'}">
<h2 class="error" style="margin-top:0">Error in DOA stock change</h2>
</c:if>

<form:form action="mobccsdoarequest.html" name="submitForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:150px"></colgroup>
<colgroup></colgroup>
<colgroup style="width:150px"></colgroup>
<colgroup></colgroup>
<thead>
	<tr class="contenttextgreen">
		<td class="table_title" colspan="4">Mobile Counter Request(Order ID: <c:out value="${param.orderId}"/>)</td>
	</tr>
</thead>
<tbody>
	<tr>
		<th colspan="4">Customer Information</th>
	</tr>
	<tr>
		<td>Customer Name</td>
		<td><c:out value="${command.contactName}" /></td>
		<td>MRT Number</td>
		<td><c:out value="${command.msisdn}" /></td>
	</tr>
	<tr>
		<td>1<sup>st</sup> delivery Date</td>
		<td><fmt:formatDate pattern="dd/MM/yyyy" value="${command.deliveryDate}" /></td>
		<td>Request Claim Date</td>
		<td><fmt:formatDate pattern="dd/MM/yyyy" value="${command.requestDate}" /></td>
	</tr>
	<tr>
		<th colspan="4">Reject Detail</th>
	</tr>
	<tr>
		<td>Items</td>
		<td colspan="3" class="stockList">
			<div style="padding: 3px 0" class="blueHeader">
				<span style="width: 80px;text-align: center;font-weight: bold;display: inline-block;padding-left:20px">Item Type</span>
				<span style="width: 80px;text-align: center;font-weight: bold;display: inline-block">Item Code</span>
				<span style="width:  36%;text-align: center;font-weight: bold;display: inline-block">Item Desc</span>
				<span style="width:  36%;text-align: center;font-weight: bold;display: inline-block">Item Serial</span>
			</div>
			<c:forEach items="${stockList}" var="stock" varStatus="s">
                <span>
               		<form:checkbox path="stocks" value="${stock.itemSerial}" />
	                <label for="stocks${s.index+1}">
	                    <span style="width:80px;text-align:center;display:inline-block">${stock.type}</span>
	                    <span style="width:80px;text-align:center;display:inline-block">${stock.itemCode}</span>
	                    <span style="width:36%;text-align:center;display:inline-block">${stock.itemDesc}</span>
	                    <span style="width:36%;text-align:center;display:inline-block">${stock.itemSerial}</span>
	                </label>
                </span>
			</c:forEach>
			<form:errors path="stocks" cssClass="error" />
			<span class="error error_msg error_stocks" style="display:none">Select at least 1 Item</span>
		</td>
	</tr>
	<tr>
		<td>Reason</td>
		<td colspan="3" class="rejectReasons">
			<form:checkboxes items="${requestReasons}" path="reasons" itemValue="codeId" itemLabel="codeDesc" />
			<form:errors path="reasons" cssClass="error" />
			<span class="error error_msg error_reasons" style="display:none">Select at least 1 Reason or input Remarks</span>
		</td>
	</tr>
	<tr>
		<td>Remarks</td>
		<td colspan="3">
			<form:textarea path="remarks" cols="50" rows="5" cssStyle="width:95%"/>
			<div class="clear:both"></div>
			<form:errors path="remarks" cssClass="error" />
			<span class="error error_msg error_remarks" style="display:none">Remarks over 500 characters</span>
		</td>
	</tr>
	<tr class="enterSerialBlock"<c:if test="${empty command.mktSerialId}"> style="display:none"</c:if>>
		<td>Approval Serial #</td>
		<td colspan="3">
			<form:input path="mktSerialId" maxlength="20" />
			<form:errors path="mktSerialId" cssClass="error" />
			<span class="error error_msg error_mktSerialId" style="display:none"></span>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<input type="button" value="Quit" name="backButton" style="float:right">
			
			<c:if test="${param.recordUpdated != 'true'}">
				<c:choose>
				<c:when test="${command.approved == 'true'}">
					<!--  return from Validator -->
					<c:if test="${command.orderStatus == '02' || (command.orderStatus == '99' && command.checkPoint == '999' && command.reasonCd == 'N000')}">
					<input type="submit" value="Save" name="saveButton" style="float:right">
					</c:if>
				</c:when>
				<c:otherwise>
					<c:choose>
					<c:when test="${command.orderStatus == '02'}">
					<input type="submit" value="Save Draft" name="saveButton" style="float:right">
					</c:when>
					<c:when test="${command.orderStatus == '99' && command.checkPoint == '999' && command.reasonCd == 'N000'}">
					<input type="button" value="Cancel Draft" name="cancelButton" style="float:right">
					<input type="submit" value="Update Draft" name="updateButton" style="float:right">
					</c:when>
					</c:choose>
					
					<c:if test="${command.orderStatus == '02' || (command.orderStatus == '99' && command.checkPoint == '999' && command.reasonCd == 'N000')}">
					<c:choose>
					<c:when test="${command.approveByManager}">
					<input type="button" value="UM Approve" name="approveButton" style="float:right">
					</c:when>
					<c:when test="${empty command.mktSerialId}">
					<input type="button" value="Approve" name="enterSerialButton" style="float:right">
					</c:when>
					</c:choose>
					</c:if>
				</c:otherwise>
				</c:choose>
			</c:if>
			
			<span class="error error_msg error_draft" style="display:none">Select at least 1 Item / Reason, or input Remark to create draft</span>
			<form:errors path="orderId" cssClass="error" />
			<!-- 
			orderStatus: <c:out value="${command.orderStatus}"/>
			checkPoint: <c:out value="${command.checkPoint}"/>
			reasonCd: <c:out value="${command.reasonCd}"/>
			approveByManager: <c:out value="${command.approveByManager}"/>
			 -->
			<form:hidden path="orderId" />
			<form:hidden path="approved" />
			<form:hidden path="rowId" />
		</td>
	</tr>
</tbody>
</table>
</form:form>

<form:form action="mobccsdoarequestreject.html" name="cancelForm">
	<form:hidden path="orderId"/>
	<form:hidden path="rowId" />
</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>