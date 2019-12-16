<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<%@ include file="loadingpanel.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<style type="text/css">
.modelSearchResultTable { width: 100%; border-collapse: separate; border-width: 0px; border-spacing: 0px; }
.modelSearchResultTable tr { padding: 3px 0 }
.hottestSearchResultTable { width: 100%; border-collapse: separate; border-width: 0px; border-spacing: 0px; }
.hottestSearchResultTable tr { padding: 3px 0 }
.selectResult { width: 100%; height: 300px; overflow-y: auto; }
.even { background-color: rgb(232,255,232); }
.searchTitle { text-align: left; }
.blueHeader { background-color: rgb(232, 242, 254) }
</style>

<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script language="javascript">

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
		
		$("#clearModelSearchBtn").attr("disabled",true);
		$("#searchHottestPeriodBtn").attr("disabled",true);
		
		$("#updateHottestPeriodBtn").attr("disabled",true);
		$("#createHottestPeriodBtn").attr("disabled",true);
		
		document.getElementById('hottestManagementDiv').style.visibility = 'hidden';
		
		$('#startDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
	
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', 
			minDate : "-90d",
			maxDate : "+90d"
		
		});
		
		$('#endDateDatepicker').datepicker({
			changeMonth : true,
			changeYear : true,
	
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy', 
			minDate : "-90d",
			maxDate : "+90d"
		
		});
		
		$("#saveButton").click(function(e) {

			if ($("#startDateDatepicker").val().length == 0
					&& $("#endDateDatepicker").val().length == 0) {
				alert("Please input start date & end date!");
				e.preventDefault();
				return false;
			}
			
			if ($("#startDateDatepicker").val().length == 0) {
				alert("Please input Start Date!");
				$("#startDateDatepicker").focus();
				e.preventDefault();
				return false;
			}
			
			if ($("#endDateDatepicker").val().length == 0) {
				alert("Please input End Date!");
				$("#endDateDatepicker").focus();
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
			
			if (!dateBefore(startDate, endDate)){
				alert("Start Day must before the end day");
				return false;
			}
			
			$.ajax({
				url: "mobccshottestmodelmanagementajax.html"
				, data: {
					type: "HottestValidate"
					, actionType: $("#actionType").val()
					, startDateStr: $("#startDateDatepicker").val()
					, endDateStr: $("#endDateDatepicker").val()
					, itemCode: $("#itemCode").val()
				}
				, cache: false
				, dataType: "json"
				, success: function(data) {
					$.each(data, function(index, item) {
						if(item.result == "valid"){
							saveHottestPeriodManagemnet();
							searchHottestPeriodSearchResult();
						}else if(item.result == "invalid" ){
							alert("Start Date and End Date is overlap the previous record.");
						}
					});
				}
				, error: function() {
					alert("cannot valid the record. Please check.");
				}
			});
		});
		
		$("#clearButton").click(function(e) {
			$("#typeSelect").val("0");
			$("#searchModel").val("");
			$("#searchItemCode").val("");
			
			$(".modelSearchResultTable:last tbody").empty();			
			$(".hottestSearchResultTable:last tbody").empty();
			
			document.getElementById('hottestManagementDiv').style.visibility = 'hidden';
		});
		
		$("#clearModelSearchBtn").click(function(e) {
						
			$(".modelSearchResultTable:last tbody").empty();
			$(".hottestSearchResultTable:last tbody").empty();
			
			document.getElementById('hottestManagementDiv').style.visibility = 'hidden';
		});
		
		$("#quitButton").click(function(e) {
			
			document.getElementById('hottestManagementDiv').style.visibility = 'hidden';
		});
		
		function searchModelSearchResult() {
			$("#searchModel").val($("#searchModel").val().toUpperCase());
			
			var $tbody = $(".modelSearchResultTable:last tbody");
			$tbody.empty();
			$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Loading...")));
			$.ajax({
				url: "mobccshottestmodelmanagementajax.html"
				, data: {
					type: "ModelSearch"
					, codeId: $("#typeSelect").val()
					, itemDesc: $("#searchModel").val()
					, itemCode: $("#searchItemCode").val()
				}
				, cache: false
				, dataType: "json"
				, success: function(data) {
					if (!data || data.length == 0) {
						$tbody.empty();
						$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 4).text("No result")));
					} else {
						$tbody.empty();
						var displayLimit = 50;
						$.each(data, function(index, item) {
							if (index >= displayLimit) {
								alert("More than " + displayLimit + " records return, show only first " + displayLimit);
								return false;
							}
							var $tr = $("<tr/>");
							if (index % 2 == 0) { $tr.addClass("even"); }
							var $td0 = "<td align='center'><input type='radio' id='modelItemCode' name='modelItemCode' value='" + item.item_code + "' data-code_desc='" + item.code_desc + "' data-item_code='" + item.item_code + "' data-item_desc='" + item.item_desc + "'/></td>";
							var $td1 = $("<td/>").attr("align", "center").text(item.code_desc);
							var $td2 = $("<td/>").attr("align", "center").text(item.item_code);
							var $td3 = $("<td/>").attr("align", "center").text(item.item_desc);
							$tbody.append($tr.append($td0).append($td1).append($td2).append($td3));
						});
						$("#clearModelSearchBtn").attr("disabled", false);
						$("#searchHottestPeriodBtn").attr("disabled", false);
						$("#createHottestPeriodBtn").attr("disabled", false);
					}
				}
				, error: function() {
					$tbody.empty();
					$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 4).text("Error found, please retry")));
				}
			});
		}
		
		$("#searchButton").click(function(e) {
			
			if($("#searchModel").val() == "" && $("#searchItemCode").val() == ""){
				alert("Please input Model or Item Code for searching.");
				return false;
			}
			searchModelSearchResult();
		});
				
		$("#searchHottestPeriodBtn").click(function(e) {

			var isChecked = false;
			
			if (document.mobCcsHottestModelManagementForm.modelItemCode[0] == null) {
				if (document.mobCcsHottestModelManagementForm.modelItemCode.checked) {
					isChecked = true;
				} else {
					isChecked= false;
				}
			} else {
				for (var x = 0; x < document.mobCcsHottestModelManagementForm.modelItemCode.length; x ++) {
		            if (document.mobCcsHottestModelManagementForm.modelItemCode[x].checked) {
	            		isChecked = true;
	            		$("#modelItemCode").val(document.mobCcsHottestModelManagementForm.modelItemCode[x].value);
	                    break;
	            	}
		        } 
			}
			
			if(isChecked){
				searchHottestPeriodSearchResult();
			} else {
				alert("Please select one record for searching!");
			}
		});
		
		function searchHottestPeriodSearchResult() {
			var $tbody = $(".hottestSearchResultTable:last tbody");
			$tbody.empty();
			$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 6).text("Loading...")));
			$.ajax({
				url: "mobccshottestmodelmanagementajax.html"
				, data: {
					type: "HottestPeriodSearch"						
					, itemCode: $("#modelItemCode").val()
				}
				, cache: false
				, dataType: "json"
				, success: function(data) {
					if (!data || data.length == 0) {
						$tbody.empty();
						$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 6).text("No result")));
					} else {
						$tbody.empty();
						var displayLimit = 50;
						$.each(data, function(index, item) {
							if (index >= displayLimit) {
								alert("More than " + displayLimit + " records return, show only first " + displayLimit);
								return false;
							}
							var $tr = $("<tr/>");
							if (index % 2 == 0) { $tr.addClass("even"); }
							var $td0 = "<td align='center'><input type='radio' id='hottestItem' name='hottestItem' value=" + index + " data-code_desc='" + item.code_desc + "' data-item_code='" + item.item_code + "' data-item_desc='" + item.item_desc + "' data-start_date='" + item.start_date + "' data-end_date='" + item.end_date + "'/></td>";
							var $td1 = $("<td/>").attr("align", "center").text(item.code_desc);
							var $td2 = $("<td/>").attr("align", "center").text(item.item_code);
							var $td3 = $("<td/>").attr("align", "center").text(item.item_desc);
							var $td4 = $("<td/>").attr("align", "center").text(item.start_date);
							var $td5 = $("<td/>").attr("align", "center").text(item.end_date);
							$tbody.append($tr.append($td0).append($td1).append($td2).append($td3).append($td4).append($td5));
						});
						
						$("#updateHottestPeriodBtn").attr("disabled", false);
						$("#createHottestPeriodBtn").attr("disabled", false);
					}
				}
				, error: function() {
					$tbody.empty();
					$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Error found, please retry")));
				}
			});
		}
		
		$("#exitButton").click(function (e) {
			window.location.href('welcome.html');
		});
		
		$("#updateHottestPeriodBtn").click(function(e) {
			
			var hottestItem = $('input[name=hottestItem]:checked');
			
			if(hottestItem.val() != "" && hottestItem.attr("checked") == true){
				
				document.getElementById('hottestManagementDiv').style.visibility = 'visible';
				
				clearHottestPeriodManagementField();
				
				$("#type").val(hottestItem.data("code_desc"));
				$("#itemCode").val(hottestItem.data("item_code"));
				$("#model").val(hottestItem.data("item_desc"));
				$("#startDateDatepicker").val(hottestItem.data("start_date"));
				$("#endDateDatepicker").val(hottestItem.data("end_date"));
				$("#actionType").val("update");
				
				$("#type").attr("disabled", true);
				$("#itemCode").attr("disabled", true);
				$("#model").attr("disabled", true);
				$("#startDateDatepicker").attr("disabled", true);
				$("#endDateDatepicker").attr("readonly", false);
			}else{
				alert("Please selected one record for update");
			}
			
		});
		
		$("#createHottestPeriodBtn").click(function(e) {
			
			var modelItemCode = $('input[name=modelItemCode]:checked');
			
			if(modelItemCode.val() != "" && modelItemCode.attr("checked") == true){
			
				document.getElementById('hottestManagementDiv').style.visibility = 'visible';			
				clearHottestPeriodManagementField();
				
				$("#type").val(modelItemCode.data("code_desc"));
				$("#itemCode").val(modelItemCode.data("item_code"));
				$("#model").val(modelItemCode.data("item_desc"));
				$("#actionType").val("create");
				
				$("#type").attr("disabled", true);
				$("#itemCode").attr("disabled", true);
				$("#model").attr("disabled", true);
				$("#startDateDatepicker").attr("readonly", "readonly");
				$("#startDateDatepicker").attr("disabled", false);
				$("#endDateDatepicker").attr("readonly", "readonly");
				$("#endDateDatepicker").attr("disabled", false);
			}else{
				alert("Please selected one record for update");
			}
		});

	});
	
	function clearHottestPeriodManagementField(){
		$("#type").val("");
		$("#itemCode").val("");
		$("#model").val("");
		$("#startDateDatepicker").val("");
		$("#endDateDatepicker").val("");
	}
	
	function saveHottestPeriodManagemnet() {
		$.ajax({
			url: "mobccshottestmodelmanagementajax.html"
			, data: {
				type: "Save"
				, actionType: $("#actionType").val()
				, startDateStr: $("#startDateDatepicker").val()
				, endDateStr: $("#endDateDatepicker").val()
				, itemCode: $("#itemCode").val()
			}
			, cache: false
			, dataType: "json"
			, success: function(data) {
				$.each(data, function(index, item) {
					if(item.result == "saved"){
						alert("The record is saved.");
					}else if(item.result == "notSave" ){
						alert("The record is NOT saved!");
					}
				});
			}
			, error: function() {
				alert("Cannot save the record. Please check.");
			}
		});
	}
	
</script>

<form:form name="mobCcsHottestModelManagementForm" method="POST" commandName="mobCcsHottestModelManagement">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF"><!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Hottest Model management</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="50%" border="0" cellspacing="1" rules="none">
					<tr>						
						<td>
							Type
							<select	name="typeSelect" id="typeSelect">								
								<c:forEach var="typeList" items="${typeList}">
									<option value="${typeList[0]}">${typeList[1]}</option>
								</c:forEach>
							</select>
						</td>
						<td>Model</td>
						<td><input type="text" id="searchModel" name="searchModel"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>Item Code</td>
						<td><input type="text" id="searchItemCode" name="searchItemCode"></td>
					</tr>					
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>						
						<td align="left">
							<input type="button" id="clearButton" value="Clear">
							<input type="button" id="searchButton" value="Search">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="table_title">Model Search Result</td>
		</tr>
		<tr>
			<td colspan="4" bgcolor="#FFFFFF">
				<table class="modelSearchResultTable">
					<colgroup width="5%"></colgroup>
					<colgroup width="20%"></colgroup>
					<colgroup width="20%"></colgroup>
					<colgroup width="55%"></colgroup>					
					<thead>
						<tr class="blueHeader">
							<th></th>
							<th>Type</th>
							<th>Item Code</th>
							<th>Model</th>
						</tr>
					</thead>
				</table>
			</td>
		</tr>		
		<tr>
			<td colspan="4" bgcolor="#FFFFFF">
				<div class="selectResult">
					<table class="modelSearchResultTable">
						<colgroup width="5%"></colgroup>
						<colgroup width="20%"></colgroup>
						<colgroup width="20%"></colgroup>
						<colgroup width="55%"></colgroup>
						<tbody></tbody>				
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td align="right">
				<input type="button" id="createHottestPeriodBtn" value="Create"/>
				<input type="button" id="clearModelSearchBtn" value="Clear"/>
				<input type="button" id="searchHottestPeriodBtn" value="Search"/>
			</td>
		</tr>
		<tr>
			<td class="table_title">Hottest Period Search Result</td>
		</tr>
		<tr>
			<td colspan="3" bgcolor="#FFFFFF">
				<table class="hottestSearchResultTable">
					<colgroup width="5%"></colgroup>
					<colgroup width="10%"></colgroup>
					<colgroup width="10%"></colgroup>
					<colgroup width="55%"></colgroup>
					<colgroup width="10%"></colgroup>
					<colgroup width="10%"></colgroup>
					<thead>
						<tr class="blueHeader">
							<th></th>
							<th>Type</th>
							<th>Item Code</th>
							<th>Model</th>
							<th>Start Date</th>
							<th>End Date</th>
						</tr>
					</thead>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" bgcolor="#FFFFFF">
				<div class="selectResult">
					<table class="hottestSearchResultTable">
						<colgroup width="5%"></colgroup>
						<colgroup width="10%"></colgroup>
						<colgroup width="10%"></colgroup>
						<colgroup width="55%"></colgroup>
						<colgroup width="10%"></colgroup>
						<colgroup width="10%"></colgroup>
						<tbody></tbody>						
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td align="right">
				<input type="button" id="updateHottestPeriodBtn" value="Update"/>
			</td>
		</tr>
		<tr>
			<td class="table_title">Hottest Period Management</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
				<div id="hottestManagementDiv">				
					<table width="100%" border="0" cellspacing="1" rules="none">						
						<tr>
							<td>Type</td>
							<td>
								<input type="text" id="type" name="type" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td>Item Code</td>
							<td>
								<input type="text" id="itemCode" name="itemCode" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td>Model</td>
							<td>
								<input type="text" id="model" name="model" disabled="disabled" size="100%"/>
							</td>
						</tr>
						<tr>
							<td>Start Date</td>
							<td>
								<input type="text" id="startDateDatepicker" name="startDateDatepicker" readonly="readonly"/>
								<div style="clear:both"></div>
							</td>
						</tr>
						<tr>
							<td>End Date</td>
							<td>
								<input type="text" id="endDateDatepicker" name="endDateDatepicker" readonly="readonly"/>
								<div style="clear:both"></div>
							</td>							
						</tr>						
						<tr>
							<td>&nbsp;</td>							
							<td align="right">
								<input type="button" id="saveButton" value="Save">
								<input type="button" id="quitButton" value="Quit">
							</td>
						</tr>						
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td align="right"><input type="button" id="exitButton" value="Exit"></td>
		</tr>
	</table>
	
	<input type="hidden" id="actionType" name="actionType"/>
</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>