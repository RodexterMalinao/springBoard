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

function formSubmit() {
	var answer = confirm("Confirm to save order?");
	if (answer){
		document.doaDetailForm.submit();
		window.opener.location.reload();
		self.close();
	}
	
}

function formQuit() {
	self.close();
}

$(document).ready(function() {
	$('#result tbody tr').each(function(index, tr) {
		
		var itemSerial = $(tr).find("td:eq(4)").text();
		
		if ($(tr).find("input[name=issueInd]").val() == "Y" && itemSerial != "") {
			$(tr).find("input[type=button]").attr("disabled", false);
		} else {
			$(tr).find("input[type=button]").attr("disabled", true);
		}
		
		
		if (itemSerial != "") {
			//diable item code href
			var $hscodeBody = $(tr).find("td:eq(2)");
			var hscode = $(tr).find("td:eq(2)").text();
			$hscodeBody.empty();
			$hscodeBody.append(hscode);
		}
				
	});
	
	$('#result tr input[type=button]').click(function() {
		
		var itemCode = $(this).closest('tr').find("td:eq(2)").text();
		var itemSerial = $(this).closest('tr').find("td:eq(4)").text();
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
								
								var $hscodeBody1 = $cell.closest('tr').find('td:eq(2)');
								var hscode1 = $.trim($cell.closest('tr').find('td:eq(2)').text());
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
});



</script>

<form:form name="doaDetailForm" method="POST" commandName="doaDetail">
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
					<thead>
						<tr>
							<th class="blueHeader"/>
							<th class="blueHeader">Item Type</th>
							<th class="blueHeader">Item Code</th>
							<th class="blueHeader">Descriptions</th>
							<th class="blueHeader">Item Serial</th>
							<th class="blueHeader">Location</th>
							<th class="blueHeader">Previous Item Serial</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${doaDetailList}" var="result">
							<tr>
								<td align="center">
									<input type="button" id="deassignButtonId" value="Deassign" class="deassignButtonClass">
									<input type="hidden" id="issueInd" name="issueInd" value="${result.issueInd}"/>
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
					</tbody>
					</table>
				</table>
			</td>
		</tr>
		<table width="100%" border="0" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
			<br>
			<tr>
				<td>
					<input type="button" value="Update" onClick="javascript:formSubmit();" id="update">
					<input type="button" value="Quit" onClick="javascript:formQuit();">
				</td>
			</tr>
		</table>
		<input type="hidden" id="location" value="${location}"/>
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>