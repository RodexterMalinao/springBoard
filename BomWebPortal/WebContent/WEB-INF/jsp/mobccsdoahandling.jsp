<%@ include file="/WEB-INF/jsp/header.jsp"%>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
	
<style type="text/css">
.selectResultTable { width: 100%; border-collapse: separate; border-width: 0px; border-spacing: 0px; }
.selectResultTable tr { padding: 3px 0 }
.selectResult { width: 100%; height: 300px; overflow-y: auto; }
.even { background-color: rgb(232,255,232); }
.searchTitle { text-align: left; }
.blueHeader { background-color: rgb(232, 242, 254) }
</style>

<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script language="javascript">

function formRequest(orderId) {
	var url = "mobccsdoarequest.html?orderId=" + orderId;
	window.open(url, '_blank', 'height=500,width=1000,resizable=yes,scrollbars=yes')
}

function formSubmit() {
	var answer = confirm("Confirm to save order?");
	if (answer){
		document.doaHandlingForm.submit();
		window.opener.location.reload();
		self.close();
	}
	
}

function formQuit() {
	self.close();
}

$(document).ready(function() {
		
	$('#result').find('tr:eq(2)').find('td:eq(0) input').attr("disabled", true).val("Deassign");
	
	var $simcodeBody = $('#result').find('tr:eq(2)').find('td:eq(2)');
	var simcode = $.trim($('#result').find('tr:eq(2)').find('td:eq(2)').text());
	$simcodeBody.empty();
	$simcodeBody.append(simcode);
	
	var hsserial = $.trim($('#result').find('tr:eq(1)').find('td:eq(4)').text());
	
	if (hsserial === "") {	
		$('#result').find('tr:eq(1)').find('td:eq(0) input').attr("disabled", true).val("Deassign");
	} else {
		var $hscodeBody = $('#result').find('tr:eq(1)').find('td:eq(2)');
		var hscode = $.trim($('#result').find('tr:eq(1)').find('td:eq(2)').text());
		$hscodeBody.empty();
		$hscodeBody.append(hscode);
	}
	
	//if ($('#completed').val() == "YES") {
	//	$('#update').attr("disabled", true).val("Update");
	//}
	if ($('#mnpInd').val() == "N") {
		$('.mnpDiv').hide();
	}
	
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$("#deassignButtonId").click(function(e) {
		
		var itemCode = $(this).parent().next().next().text();
		var itemSerial = $(this).parent().next().next().next().next().text();
		var $cell = $(this);
		itemCode = $.trim(itemCode);
		itemSerial = $.trim(itemSerial);

		if (itemSerial === "") {
				alert("This Item Serial is empty");	
			} else {
				$.ajax({
					url: "mobccsdoahandlingdeassign.html"
					, data: {
						itemCode: itemCode
						, itemSerial: itemSerial
						, orderId: $("#orderId").val()
					}
					, cache: false
					, dataType: "json"
					, success: function(data) {
						
						$.each(data, function(index, item) {
							if(item == "0"){
								$cell.parent().next().next().next().next().next().next().text(itemSerial);
								$cell.parent().next().next().next().next().text("");
								$cell.parent().next().next().next().next().next().text("");
								$cell.attr("disabled", true).val("Deassign");
								
								var $hscodeBody1 = $('#result').find('tr:eq(1)').find('td:eq(2)');
								var hscode1 = $.trim($('#result').find('tr:eq(1)').find('td:eq(2)').text());
								var orderId = $('#orderId').val();
								$hscodeBody1.empty();
								
								var location = $('#location').val();
								
								var $link = $("<a/>").attr({"href": "mobccsstockmanualassgn.html?itemCode=" + hscode1 + "&orderId=" + orderId + "&locFlag="+ location +"&source=DOA"}).text(hscode1);
								$hscodeBody1.append($link);
								
							}else if(item != "0" ){
								alert("Error!");
							}
						});
						
						
					}
				});
			}
	});
	$(function() {
		// Datepicker
		$('#deliveryDatepicker').datepicker({
			changeMonth : true,
			changeYear : false,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "0Y",
			yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		});
	});
	$(function() {
		// Datepicker
		$('#srvReqDatepicker').datepicker({
			changeMonth : true,
			changeYear : false,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "0Y",
			yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		});
	});
});
	
</script>

<form:form name="doaHandlingForm" method="POST" commandName="doaHandling">
	<table width="100%" class="tablegrey">
		<tr>
			<td>
				<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
					<tr>
						<td>
							Order ID:${orderId}
						</td>
					</tr>
					<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="result">
						<tr>
							<th class="blueHeader"/>
							<th class="blueHeader">Item Type</th>
							<th class="blueHeader">Item Code</th>
							<th class="blueHeader">Descriptions</th>
							<th class="blueHeader">Item Serial</th>
							<th class="blueHeader">Location</th>
							<th class="blueHeader">Previous Item Serial</th>
						</tr>
						<c:forEach items="${manualDetailList}" var="result">
							<tr>
								<td align="center">
									<input type="button" id="deassignButtonId" value="Deassign" class="deassignButtonClass">
								</td>
								<td>${result.itemType}</td>
								
								<td>
									<a href="mobccsstockmanualassgn.html?itemCode=${result.itemCode}&orderId=${orderId}&locFlag=${location}&source=DOA" >
										${result.itemCode}
									</a>
								</td>
	
								<td>${result.descriptions}</td>
	
								<td>${result.serialNum}</td>
	
								<td>${result.location}</td>
								<td>${result.oldSerialNum}</td>
							</tr>
						</c:forEach>
					</table>
					<table width="100%" border="0" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
						<tr>
							<br>
							<td width="13%">
								District:
							</td>
							<td width="15%">
								<input value="${deliveryUI.districtDesc}" disabled="true"/>
							</td>
							<td width="15%">
								Delivery Date:
							</td>
							<td width="15%">
								<form:input
									path="deliveryDateStr" maxlength="10"
									id="deliveryDatepicker" readonly="true" /> 
									<form:errors path="deliveryDateStr" cssClass="error" />
							</td>
							<td width="15%">
								Delivery Time
							</td>
							<td width="15%">
								<form:select path="deliveryTimeSlot">
								<form:options items="${codeList}" itemValue="codeId" itemLabel="codeDesc" />
								</form:select>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
						<br/>
						<tr>
							<td width="15%">
								Service Request Date:
							</td>
							<td width="17%">
								<form:input
									path="srvReqDateStr" maxlength="10"
									id="srvReqDatepicker" readonly="true"/>
							</td>
					
								<td width="17%">
										<div class="mnpDiv">
									MNP Ticket Number:
								</div>
								</td>
								<td width="51%">
										<div class="mnpDiv">
									<input value="${mnpTicketNum}"/>
									</div>
								</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
						<br>
						<tr>
							<td>
								<input type="button" value="Update" onClick="javascript:formSubmit();" id="update">
								<input type="button" value="DOA Request Enquiry" onClick="javascript:formRequest('${orderId}');">
								<input type="button" value="Quit" onClick="javascript:formQuit();">
							</td>
						</tr>
					</table>
				</table>
			</td>
		</tr>
		<input type="hidden" id="orderId" value="${orderId}"/>
		<input type="hidden" id="mnpInd" value="${orderDTO.mnpInd}"/>
		<input type="hidden" id="location" value="${location}"/>
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>