<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<style type="text/css">
.selectResultTable { width: 100%; border-collapse: separate; border-width: 0px; border-spacing: 0px; }
.selectResultTable tr { padding: 3px 0 }
.selectResult { width: 100%; height: 330px; overflow-y: auto; }
.even { background-color: rgb(232,255,232); }
.searchTitle { text-align: left; }
.blueHeader { background-color: rgb(232, 242, 254) }
</style>

<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script language="javascript">
	function updatePaging(tableClass) {
		var $trs = $("table." + tableClass + " tbody tr");
		var $control = $("div." + tableClass);
		$control.find(".control").show().data("index", 0);
		$control.find(".control .left a").hide();
		if ($trs.length > 15) {
			$control.find(".control .right a").show();
		} else {
			$control.find(".control .right a").hide();
		}
		$control.find(".control .label").text("1 - " + $trs.filter(":visible").length + " / " + $trs.length);
		$control.find(".control .left a").unbind("click").click(function(e) {
			e.preventDefault();
			$trs.hide();
			var start = $control.find(".control").data("index") - 15;
			if (start < 0) {
				start = 0;
			}
			var end = start + 15;
			if (end > $trs.length) {
				end = $trs.length;
			}
			if (start > 0) {
				$control.find(".control .left a").show();
			} else {
				$control.find(".control .left a").hide();
			}
			if (end < $trs.length) {
				$control.find(".control .right a").show();
			} else {
				$control.find(".control .right a").hide();
			}
			$trs.slice(start, end).show();
			$control.find(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
			$control.find(".control").data("index", start);
			return false;
		});
		$control.find(".control .right a").unbind("click").click(function(e) {
			e.preventDefault();
			$trs.hide();
			var start = $control.find(".control").data("index") + 15;
			if (start > $trs.length) {
				start = $trs.length;
			}
			var end = start + 15;
			if (end > $trs.length) {
				end = $trs.length;
			}
			if (start > 0) {
				$control.find(".control .left a").show();
			} else {
				$control.find(".control .left a").hide();
			}
			if (end < $trs.length) {
				$control.find(".control .right a").show();
			} else {
				$control.find(".control .right a").hide();
			}
			$trs.slice(start, end).show();
			$control.find(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
			$control.find(".control").data("index", start);
			return false;
		});
	}

	function dateBefore(date, date2) {
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		date.setMilliseconds(0);
		
		date2.setHours(0);
		date2.setMinutes(0);
		date2.setSeconds(0);
		date2.setMilliseconds(0);
		
		return date < date2;
	}
	
	$(document).ready(function() {
		$('#startDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
	
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "-90d",
			maxDate : "+90d"
		//	yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		//yearRange: "1911:1993" //the range shown in the year selection box
		});
		$('#endDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
	
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
			minDate : "-90d",
			maxDate : "+90d"
		//	yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
		//yearRange: "1911:1993" //the range shown in the year selection box
		});
		$("#searchButton").click(function(e) {

			if ($("#startDateDatepicker").val().length == 0
					&& $("#endDateDatepicker").val().length == 0
					&& $("#orderStatusField").val() == "NONE"
					&& $("#orderIdField").val().length == 0) {
				alert("Please select one of the search criteria!");
				e.preventDefault();
				return false;
			}
			
			if ($("#orderStatusField").val() == "NONE"
				&& $("#orderIdField").val().length == 0) {
				
				if ($("#startDateDatepicker").val().length == 0) {
					alert("Please input Start Date!");
					$("#startDateDatepicker").focus();
					e.preventDefault();
					return false;
				}
				
				var startDate = $("#startDateDatepicker").datepicker("getDate");
				var endDate = $("#endDateDatepicker").datepicker("getDate");
				
				if ($("#endDateDatepicker").val().length != 0) {
					if (startDate > endDate) {
						alert("Please input End Date after Start Date!");
						$("#endDateDatepicker").focus();
						e.preventDefault();
						return false;
					}
				}
				
			}
			
			if ($("#startDateDatepicker").val().length != 0
					&& $("#orderStatusField").val() == "NONE") {
				$("#orderStatusField").val("ALL");
			}
			
			if ($("#startDateDatepicker").val().length != 0
					&& $("#endDateDatepicker").val().length != 0
					&& $("#orderStatusField").val() != "NONE") {
				var startDate = $("#startDateDatepicker").datepicker("getDate");
				var endDate = $("#endDateDatepicker").datepicker("getDate");
				
				if ($("#endDateDatepicker").val().length != 0) {
					if (startDate > endDate) {
						alert("Please input End Date after Start Date!");
						$("#endDateDatepicker").focus();
						e.preventDefault();
						return false;
					}
				}
			}
			
			// (1) by delivery date range + order status
			if ($("#startDateDatepicker").val().length != 0) {
				$("#searchCriteriaField").val("1");
			
			// (3) by order id only
			} else if ($("#orderIdField").val().length != 0) {
				$("#searchCriteriaField").val("3");
				
			// (2) by order status only
			} else if ($("#startDateDatepicker").val().length == 0 && $("#orderStatusField").val() != "NONE") {
				$("#searchCriteriaField").val("2");
			}
			
			var $tbody = $(".selectResultTableContext:last tbody");
			$tbody.empty();
			$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Loading...")));
			$.ajax({
				url: "mobdsstockmanualsearch.html"
				, data: {
					  startDate: $("#startDateDatepicker").val()
					, endDate: $("#endDateDatepicker").val()
					, orderStatus: $("#orderStatusField").val()
					, orderId: $("#orderIdField").val()
					, searchCriteria: $("#searchCriteriaField").val()
				}
				, cache: false
				, dataType: "json"
				, success: function(data) {
					if (!data || data.length == 0) {
						$tbody.empty();
						$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("No result")));
					} else {
						$tbody.empty();
						var displayLimit = 15;
						$.each(data, function(index, item) {
							var $tr = $("<tr/>");
							if (index % 2 == 0) { $tr.addClass("even"); }
							if (index >= displayLimit) { $tr.css("display", "none"); }
							var orderId = item.orderId;
							
							var $stockLink = $("<a/>").attr({"href": "#" }).text(orderId).click(function() {
								// check order is locked
								// yes, alert & ban continue
								// no, lock order & proceed to stockmanualdetail page
									$.ajax({
										url: "mobccsorderlockcheck.html"
										, data: {
											orderId: orderId
										}
										, cache: false
										, dataType: "json"
										, success: function(lockresult) {
											$.each(eval(lockresult),
												function(i2, item2) {
													if (item2.result == "N") {
														// lock order
														$.ajax({
															url: "mobccsorderlock.html"
															, data: {
																orderId: orderId
															}
															, cache: false
															, dataType: "json"
															, success: function(data) {
																window.open('mobdsstockmanualdetail.html?'+ $.param({"orderId": orderId}), '_blank', 'toolbar=0,location=0,menubar=0,height=768,width=1024');
															}
															, error: function() {
																alert("Lock Order Error found, please retry!");
															}
														});
														
													} else {
														alert("Order " + orderId + " is locked!");
													}
												}
											);
										}
										, error: function() {
											alert("Check Order Lock Error found, please retry!");
										}
									});
                          	});
							var $td0 = $("<td/>").attr("align", "center").append($stockLink);
							var $td1 = $("<td/>").attr("align", "center").text(item.requestDate);
							var $td2 = $("<td/>").attr("align", "center").text(item.salesId);
							var $td3 = $("<td/>").attr("align", "center").text(item.orderStatus);
							$tbody.append($tr.append($td0).append($td1).append($td2).append($td3));
						});
						updatePaging("selectResultTable");
					}
				}
				, error: function() {
					$tbody.empty();
					$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Error found, please retry")));
				}
			});
		});
		$("#startDateDatepicker").click(
			function(e) {
				$("#orderStatusField").val("ALL");
				$("#orderIdField").val("");
			}
		);
		$("#endDateDatepicker").click(
			function(e) {
				$("#orderStatusField").val("ALL");
				$("#orderIdField").val("");
			}
		);
		$("#orderStatusField").click(
			function(e) {
				$("#orderIdField").val("");
			}
		);
		$("#orderIdField").click(
			function(e) {
				$("#orderStatusField").val("NONE");
				$("#startDateDatepicker").val("");
				$("#endDateDatepicker").val("");
			}
		);
		$("#clearButton").click(function(e) {
			$("#startDateDatepicker").val("");
			$("#endDateDatepicker").val("");
			$("#orderStatusField").val("NONE");
			$("#orderIdField").val("");
			$(".selectResultTableContext:last tbody").empty();
		});
	});
</script>

<form:form name="stockManualForm" method="POST" commandName="stockManual">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Item Manual Assignment</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td>Application Date</td>
						<td>
							From
							<form:input path="startDate" id="startDateDatepicker" readonly="true"/>
							<div style="clear:both"></div>
							<form:errors path="startDate" cssClass="error"/>
						</td>
						<td>
							To
							<form:input path="endDate" id="endDateDatepicker" readonly="true"/>
							<div style="clear:both"></div>
							<form:errors path="endDate" cssClass="error"/>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Order Status</td>
						<td>
							<form:select path="orderStatus" id="orderStatusField">
								<form:options items="${orderStatus}" itemLabel="codeDesc" itemValue="codeId" />
							</form:select>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>Order ID</td>
						<td>
							<form:input path="orderId" id="orderIdField"/>
						</td>
						<td>&nbsp;</td>
						<td align="right">
							<input type="button" id="searchButton" value="Search">
							<input type="button" id="clearButton" value="Clear">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" bgcolor="#FFFFFF">
				<table class="selectResultTable">
					<colgroup width="25%"></colgroup>
					<colgroup width="25%"></colgroup>
					<colgroup width="25%"></colgroup>
					<colgroup width="25%"></colgroup>
					<thead>
						<tr class="blueHeader">
							<th>Order ID</th>
							<th>Application Date</th>
							<th>Staff ID</th>
							<th>Order Status</th>
						</tr>
					</thead>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" bgcolor="#FFFFFF">
				<div class="selectResult">
					<table class="selectResultTable selectResultTableContext">
						<colgroup width="25%"></colgroup>
						<colgroup width="25%"></colgroup>
						<colgroup width="25%"></colgroup>
						<colgroup width="25%"></colgroup>
						<tbody></tbody>
					</table>
				</div>
			</td>
		</tr>
	</table>
	
	<div style="text-align: center; padding: 5px 0" class="selectResultTable">
		<span class="control">
			<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
			<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
			<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
		</span>
		<div class="quit" style="float:right">
			<a href="mobdsstock.html" class="nextbutton">Quit</a>
		</div>
	</div>
	
	<form:hidden path="searchCriteria" id="searchCriteriaField"/>
</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>