<%@ include file="/WEB-INF/jsp/header.jsp"%>
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
	
	function onSearch(){	
		if ($("#location").val() == "NONE") {
			alert("Please input Location!");
			$("#location").focus();
			return false;
		}
		if ($("#status").val() == "NONE") {
			alert("Please input Status!");
			$("#status").focus();
			return false;
		}

		var $tbody = $(".selectResultTableContext:last tbody");
		$tbody.empty();
		$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Loading...")));
		$.ajax({
			url: "mobccsstockmanualassgnsearch.html"
			, data: {
					  itemCode: $("#ic").val()
					, location: $("#location").val()
					, status: $("#status").val()
					, orderId: $("#oi").val()
					, stockPool: $("#stockPool").val()
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
						var $td0 = "<td><input type='radio' id='selItemSerial' name='selItemSerial' value=" + item + " /></td>";
						var $td1 = $("<td/>").text(item);
						$tbody.append($tr.append($td0).append($td1));
					});
					updatePaging("selectResultTable");
				}
			}
			, error: function() {
				$tbody.empty();
				$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text("Error found, please retry")));
			}
		});
	}
		
	function onClear(){
		$("#location").val("NONE");
		$("#status").val("NONE");
		$(".selectResultTableContext:last tbody").empty();
	}
	
	function onAssign(){
		if ($("#location").val() == "NONE") {
			alert("Please input Location!");
			$("#location").focus();
			return false;
		}
		if ($("#status").val() == "NONE") {
			alert("Please input Status!");
			$("#status").focus();
			return false;
		}
		var isChecked = false;
		
		if (document.stockManualAssgnForm.selItemSerial == null) {
			isChecked= false;
		} else if (document.stockManualAssgnForm.selItemSerial[0] == null) {
			if (document.stockManualAssgnForm.selItemSerial.checked) {
				isChecked = true;
			} else {
				isChecked= false;
			}
		} else {
			for (var x = 0; x < document.stockManualAssgnForm.selItemSerial.length; x ++) {
	            if (document.stockManualAssgnForm.selItemSerial[x].checked) {
            		isChecked = true;
                    $("#selItemSerial").val(document.stockManualAssgnForm.selItemSerial[x].value);
                    break;
            	}
	        } 
		}
		
		if (!isChecked) {
			alert("Please select Item Serial!");
			return false;
		}
		
		$("#actionType").val("ASSIGN");
		document.stockManualAssgnForm.submit();
		
	}
	
	function onQuick(){
		var link;
		if ($("#sourceId").val() == "DOA") {
			link = "mobccsdoahandling.html?orderId=" + $("#oi").val();
		} else {
			link = "mobccsstockmanualdetail.html?orderId=" + $("#oi").val();
		}
	 	
		window.location.href = link;
	}
	
	$(document).ready(function() {
		$("select[name=location]").change(function() {
			$(".selectResultTableContext:last tbody").empty();
		});
	});
</script>

<form:form name="stockManualAssgnForm" method="POST" commandName="stockManualAssgn">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	
	<form:errors path="location" cssClass="error" />
	<form:errors path="status" cssClass="error" />
	<form:errors path="errMsg" cssClass="error" />
	
	<table width="100%" class="tablegrey">
	
		<c:choose>
			<c:when test="${save == 'Y'}">
				Record is saved successfully!
			</c:when>
			<c:when test="${save == 'N'}">
				Record not saved!
			</c:when>
			<c:when test="${save == 'NL'}">
				Record not saved! Please select another location!
			</c:when>
			<c:when test="${save == 'S'}">
				Record not saved! Serial Number is assigned already!
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
			
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td colspan="2">
							Item Code:${param.itemCode}
							<span style="float: right">
								Stock Pool:${stockPool}
							</span>
						</td>
					</tr>
					<tr>
						<td>Location:</td>
						<td>
							<form:select path="location" >
								<form:option value="NONE" label="Select" />
								<form:options items="${locationList}" itemValue="codeId" itemLabel="codeDesc"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td>Status:</td>
						<td>
							<form:select path="status" >
								<form:option value="NONE" label="Select" />
								<form:option value="02" label="FREE" />
								<form:option value="18" label="RESERVE" />
							</form:select>
						</td>
						
						<td>
							<input type="button" id="searchButton" value="Search" onClick="onSearch()">
							<input type="button" id="clearButton" value="Clear" onClick="onClear()">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2" bgcolor="#FFFFFF">
				<table class="selectResultTable">
					<colgroup width="10%"></colgroup>
					<colgroup width="90%"></colgroup>
					
					<thead>
						<tr class="blueHeader">
							<th>&nbsp;</th>
							<th>Item Serial</th>
						</tr>
					</thead>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2" bgcolor="#FFFFFF">
				<div class="selectResult">
					<table class="selectResultTable selectResultTableContext">
						<colgroup width="10%"></colgroup>
						<colgroup width="90%"></colgroup>
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
		<input type="button" id="quitButton" value="Quit" onClick="onQuick()" style="display: inline-block; float:right">
		<input type="button" id="assignButton" value="Assign" onClick="onAssign()" style="display: inline-block; float:right">
	</div>
	
	<input type="hidden" name="ic" id="ic" value="${param.itemCode}"/>
	<input type="hidden" name="oi" id="oi" value="${param.orderId}"/>
	<input type="hidden" name="lf" id="lf" value="${param.locFlag}"/>
	<input type="hidden" id="sourceId" value="${source}"/>	
	<input type="hidden" name="actionType" id="actionType" />	
	<input type="hidden" name="stockPool" id="stockPool" value="${stockPool}"/>
</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>