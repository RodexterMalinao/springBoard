<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />

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
	
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script language="javascript">

	function onDeassign(itemCode, itemSerial) {
		$("#actionType").val("DEASSIGN");
		$("#itemCode").val(itemCode);
		$("#itemSerial").val(itemSerial);
		$("form[name=stockManualDetailForm]").submit();
	}
	
	function onQuit() {
		unlockOrder();
	}
	
	function unlockOrder() {
		// unlock order
		$.ajax({
			url: "mobccsorderlock.html"
			, data: {
				orderId: '${orderId}'
			}
			, cache: false
			, dataType: "json"
			, success: function(data) {
				orderStatusProcess();
			}
			, error: function() {
				alert("Lock Order Error found, please retry!");
			}
		});
	}
	
	function orderStatusProcess() {
		// call store proc to change status of order
		$.ajax({
			url : 'mobccsstockmanualorderstatusprocess.html'
			, data : {
						orderId: '${orderId}'
					 }
			, type : 'POST'
			, dataType : 'JSON'
			, timeout : 5000
			, success : function(msg) {
				window.opener.location.reload();
				self.close();
			}
			, error: function() {
				alert("Order Status Process Error found, please retry!");
			}
		});
	}
	
	$(document).ready(function() {
		$("#quitButton").click(function() {
			onQuit();
		});
		
		$('#resulttable input[id=deassignButton]').click(function() {
		/* $('#resulttable input[type=button]').click(function() { */
			var itemCode = $(this).closest('tr').find("td:eq(2)").text();
			var itemSerial = $(this).closest('tr').find("td:eq(4)").text();
			itemCode = $.trim(itemCode);
			itemSerial = $.trim(itemSerial);			
			onDeassign(itemCode, itemSerial);

		});
		
	});
</script>

<form:form name="stockManualDetailForm" method="POST"
	commandName="stockManualDetail">
	
	<form:errors path="errMsg" cssClass="error" />
	<font color="red">
		<c:out value="${errMsg}" />
	</font>
	<c:if test="${save != null}">
		The record is saved successfully.
	</c:if>
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<table width="100%" class="tablegrey">
		<tr>
			<td>
				<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
					<tr>
						<td>
							Order ID:${orderId}
							<span style="margin-left: 50px">Stock Pool:${stockPool}</span>
						</td>
					</tr>
				</table>
				<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="resulttable">
					<tr>
						<th class="blueHeader">&nbsp;</th>
						<th class="blueHeader">Item Type</th>
						<th class="blueHeader">Item Code</th>
						<th class="blueHeader">Descriptions</th>
						<th class="blueHeader">Item Serial</th>
						<th class="blueHeader">Location</th>
						<th class="blueHeader">Previous Item Serial</th>
					</tr>
					<c:forEach items="${stockManualDetailList}" var="result">
						<tr>
							<c:choose>
								<c:when test='${result.serialNum == "" || empty result.serialNum}'>
									<td>
										<input type="button" id="deassignButton" value="Deassign" disabled="disabled">
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<input type="button" id="deassignButton" value="Deassign">
									</td>
								</c:otherwise>
							</c:choose>
							
							<td>&nbsp;${result.itemType}&nbsp;</td>
							
							<c:choose>
								<c:when test='${result.serialNum == "" || empty result.serialNum}'>
									<td>
										<a href="mobccsstockmanualassgn.html?itemCode=${result.itemCode}&orderId=${orderId}&locFlag=${locFlag}&action=ASSIGN">
											&nbsp;${result.itemCode}&nbsp;
										</a>
									</td>
								</c:when>
								<c:otherwise>
									<td>&nbsp;${result.itemCode}&nbsp;</td>
								</c:otherwise>
							</c:choose>

							<td>&nbsp;${result.descriptions}&nbsp;</td>

							<td>&nbsp;${result.serialNum}&nbsp;</td>
							
							<td>&nbsp;${result.location}&nbsp;</td>
							
							<td>&nbsp;${result.oldSerialNum}&nbsp;</td>

						</tr>
					</c:forEach>
				</table>
				<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
					<tr>
						<td align="right">
							<!--  <input type="button" id="deassignButton" value="Deassign All Item"> -->
							<input type="button" id="quitButton" value="Quit">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
	<form:hidden path="actionType" />
	<form:hidden path="itemCode" />
	<form:hidden path="itemSerial" />
	
	
</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>