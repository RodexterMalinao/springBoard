<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@page import="java.util.List, net.sf.json.*, com.bomwebportal.mob.ccs.dto.StockDTO"%>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<c:set var="isMdv" value="${bomsalesuser.category == 'SUPERVISOR' && bomsalesuser.channelCd == 'MDV'}" />

<!-- Store code and team code JSON -->
<%
JSONArray storeListJson = new JSONArray();
if (request.getAttribute("storeList") instanceof List<?>) {
	for (StockDTO store : (List<StockDTO>) request.getAttribute("storeList")) {
		JSONObject json = new JSONObject();
		json.put("searchStoreCode", store.getStoreCode());
		json.put("storeCode", store.getStoreCode());
		storeListJson.add(json);
	}
}
pageContext.setAttribute("storeListJson", storeListJson);

JSONArray teamListJson = new JSONArray();
if (request.getAttribute("teamListAll") instanceof List<?>) {
	for (StockDTO team : (List<StockDTO>) request.getAttribute("teamListAll")) {
		JSONObject json = new JSONObject();
		json.put("teamCode", team.getTeamCode());
		json.put("searchTeamCode", team.getTeamCode());
		json.put("searchStoreCode", team.getStoreCode());
		json.put("storeCode", team.getStoreCode());
		teamListJson.add(json);
	}
}
pageContext.setAttribute("teamListJson", teamListJson);
%>

<%
java.util.Date current=new java.util.Date();
java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("ddMMyyyy"); 
String date=format.format(current);
%>

<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
var storeListJson = <c:out value="${storeListJson}" escapeXml="false"/>;
var teamListJson = <c:out value="${teamListJson}" escapeXml="false"/>;
function isBlank(str) {
	return str == null || $.trim(str).length == 0;
}
function isDefault(str) {
	return isBlank(str) || str == "NONE" || str == "";
}
function storeChanged() {
	var selectedStoreCode = $("select[name=storeCode]").val();
	$("select[name=teamCode] option:gt(0)").remove();
	$.each(teamListJson, function(index, team) {
		if (isDefault(selectedStoreCode) || team.storeCode == selectedStoreCode) {
			var $option = $("<option/>").text(team.teamCode).val(team.teamCode);
			$("select[name=teamCode]").append($option);
		}
	});
}

function searchStoreChanged() {
	var selectedSearchStoreCode = $("select[name=searchStoreCode]").val();
	$("select[name=searchTeamCode] option:gt(0)").remove();
	$.each(teamListJson, function(index, team) {
		if (isDefault(selectedSearchStoreCode) || team.storeCode == selectedSearchStoreCode) {
			var $option = $("<option/>").text(team.teamCode).val(team.teamCode);
			$("select[name=searchTeamCode]").append($option);
		}
	});
}

function formSubmit(actType) {
	$("#actionType").val(actType);
	document.mrtManagementForm.submit();
}

function onClickMsisdnlvlAll() {
	if ($("#all_msisdnlvl").is(":checked")) {
		$("input[name='searchMsisdnlvl']").attr("checked", true);
	} else {
		$("input[name='searchMsisdnlvl']").removeAttr("checked");
	}
}

function onClickMsisdnlvl() {
	$("#all_msisdnlvl").attr("checked", true);
	$("input[name='searchMsisdnlvl']").each(function() {
		if (!$(this).is(":checked")) {
			$("#all_msisdnlvl").removeAttr("checked");
		}
	});
}

function onClickMsisdnAll() {
	if ($("#all_msisdn").is(":checked")) {
		$("input[name=selectMsisdn]").attr("checked", true);
	} else {
		$("input[name=selectMsisdn]").removeAttr("checked");
	}
}

function onClickMsisdn() {
	$("#all_msisdn").attr("checked", true);
	$("input[name=selectMsisdn]").each(function() {
		if (!$(this).is(":checked")) {
			$("#all_msisdn").removeAttr("checked");
		}
	});
}

function onClear() {
	$("form[name=mrtManagementForm] input[type=text]").val("");
	$("form[name=mrtManagementForm] select").attr("selectedIndex", 0);
	$("#all_msisdnlvl").attr("checked", true);
	$("input[name='searchMsisdnlvl']").attr("checked", true);
	$("tr[id=selectedRow]").removeAttr("id");
	$("#all_msisdn").attr("checked", false);
	onClickMsisdnAll();
}

function updatePaging(tableClass) {
	var $trs = $("table." + tableClass + " tbody tr");
	var $control = $("div." + tableClass);
	$control.find(".control").show().data("index", 0);
	$control.find(".control .left a").hide();
	if ($trs.length > 10) {
		$control.find(".control .right a").show();
	} else {
		$control.find(".control .right a").hide();
	}
	$control.find(".control .label").text("1 - " + $trs.filter(":visible").length + " / " + $trs.length);
	$control.find(".control .left a").unbind("click").click(function(e) {
		e.preventDefault();
		$trs.hide();
		var start = $control.find(".control").data("index") - 10;
		if (start < 0) {
			start = 0;
		}
		var end = start + 10;
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
		var start = $control.find(".control").data("index") + 10;
		if (start > $trs.length) {
			start = $trs.length;
		}
		var end = start + 10;
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

$(document).ready(function() {
	var selectedSearchStoreCode = $("select[name=searchStoreCode]").val();
	var selectedSearchTeamCode = $("select[name=searchTeamCode]").val();
	var sessionSearchStoreCode = "<c:out value="${mrtManagement.searchStoreCode}"/>";
	var sessionSearchTeamCode = "<c:out value="${mrtManagement.searchTeamCode}"/>";
	$.each(storeListJson, function(index, store) {
		var $option = $("<option/>").text(store.storeCode).val(store.storeCode);
		if ((!isDefault(selectedSearchStoreCode) && store.storeCode == selectedSearchStoreCode) || store.storeCode == sessionSearchStoreCode) {
			$option.attr("selected", true);
		}
		$("select[name=searchStoreCode]").append($option);
	});
	
	$.each(teamListJson, function(index, team) {
		if ((isDefault(sessionSearchStoreCode) && isDefault(selectedSearchStoreCode)) 
				|| (sessionSearchStoreCode == team.storeCode || selectedSearchStoreCode == team.storeCode)) {
			var $option = $("<option/>").text(team.teamCode).val(team.teamCode);
			if ((!isDefault(selectedSearchTeamCode) && selectedSearchTeamCode == team.teamCode) || team.teamCode == sessionSearchTeamCode) {
				$option.attr("selected", true);
			}
			$("select[name=searchTeamCode]").append($option);
		}
	});
	$("select[name=searchStoreCode]").change(searchStoreChanged);
	
	var selectedStoreCode = $("select[name=storeCode]").val();
	var selectedTeamCode = $("select[name=teamCode]").val();
	var sessionStoreCode = "<c:out value="${mrtManagement.storeCode}"/>";
	var sessionTeamCode = "<c:out value="${mrtManagement.teamCode}"/>";
	$.each(storeListJson, function(index, store) {
		var $option = $("<option/>").text(store.storeCode).val(store.storeCode);
		if ((!isDefault(selectedStoreCode) && store.storeCode == selectedStoreCode) || store.storeCode == sessionStoreCode) {
			$option.attr("selected", true);
		}
		$("select[name=storeCode]").append($option);
	});
	
	var notMdv = <c:out value="${not isMdv}"/>;
	if (notMdv) {
		$.each(teamListJson, function(index, team) {
			if ((isDefault(sessionStoreCode) && isDefault(selectedStoreCode)) 
					|| (sessionStoreCode == team.storeCode || selectedStoreCode == team.storeCode)) {
				var $option = $("<option/>").text(team.teamCode).val(team.teamCode);
				if ((!isDefault(selectedTeamCode) && selectedTeamCode == team.teamCode) || team.teamCode == sessionTeamCode) {
					$option.attr("selected", true);
				}
				$("select[name=teamCode]").append($option);
			}
		});
		$("select[name=storeCode]").change(storeChanged);
	}
	
	<c:if test="${mrtManagement.searchMsisdnlvl == null}">
		onClear();
	</c:if>
	onClickMsisdnlvl();
	onClickMsisdn();
	if (<c:out value="${empty mrtManagement.mrtSummaryList}" />) {
		$("#all_msisdn").removeAttr("checked");
	}
	$("input[name='searchMsisdnlvl']").click(function() {
		onClickMsisdnlvl();
	});
	
	$("#all_msisdn").click(function() {
		onClickMsisdnAll();
	});
	
	$("input[name=selectMsisdn]").click(function() {
		onClickMsisdn();
	});
	
	updatePaging("mrtSummaryTable");
});

</script>
<style type="text/css">
	.mrtSummaryTable td { height:28px }
	.row2 { background:#C8E2B1 }
	#selectedRow { background:#FFFF99 }
</style>
<form:form method="POST" name="mrtManagementForm" commandName="mrtManagement">
<form:errors path="searchStoreCode" cssClass="error" />
<form:errors path="selectMsisdn" cssClass="error" />
<form:errors path="storeCode" cssClass="error" />
<form:errors path="teamCode" cssClass="error" />
<form:errors path="staffId" cssClass="error" />
<c:if test='${mrtManagement.actionType=="ERROR"}'><span class="error">Update Error</span></c:if>
<c:if test='${mrtManagement.actionType=="SUCCESS"}'>Updated successfully</c:if>
<table width="100%"  class="tablegrey">
	<tr> 
		<td bgcolor="#FFFFFF" > 
			<table width="100%"  border="0" cellspacing="1" >
				<tr><td class="table_title">Direct Sales MRT Management</td></tr>
			</table>
		
			<!-- Search MRT function -->
			<table id="mrtManagementSearch" width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
      			<tr><td class="table_title" colspan="2" style="font-size: medium">MRT Search</td></tr>
      			<tr>
		        	<td width="20%">MRT: </td>
		        	<td><form:input path="searchMrt"/></td>
	        	</tr>
	        	
	        	<tr><td colspan="2"><hr /></td></tr>
	        	
	        	<c:if test="${bomsalesuser.category != 'FRONTLINE'}">
			        <tr>
			        	<td width="20%">Staff ID: </td>
			        	<td><form:input path="searchStaffId"/></td>
		        	</tr>
	        	
			        <tr>
			        	<td width="20%">Store Code: <span class="contenttext_red">*</span></td>
			          	<td>
							<form:select path="searchStoreCode">
								<c:choose>
									<c:when test="${bomsalesuser.category == 'SUPERVISOR'}">
										<form:option value="ALL" label="Select" />
									</c:when>
									<c:otherwise>
										<form:option value="" label="Select" />
									</c:otherwise>
								</c:choose>
							</form:select>
			          	</td>
		          	</tr>
	
					<tr>
			          	<td width="20%">Team Code: </td>
			          	<td>
							<form:select path="searchTeamCode">
								<form:option value="" label="Select" />
							</form:select>
			          	</td>
			        </tr>
		        </c:if>
		        
		        <tr>
		      		<td width="20%">Status: </td>
		      		<td>
		      			<form:select path="searchStatus">
							<form:options items="${msisdnStatusList}" itemValue="status" itemLabel="desc"/>
						</form:select>
					</td>
		      	</tr>
		      	
		      	<tr>
		      		<td width="20%">Num Type: </td>
		      		<td>
		      			<form:select path="searchNumType">
		      				<form:option value="" label="Select" />
							<form:options items="${numTypeList}" itemValue="codeId" itemLabel="codeDesc"/>
						</form:select>
					</td>
		      	</tr>
		      	
		        <tr>
		      		<td width="20%">Grade: <span class="contenttext_red">*</span></td>
		      		<td>
		      			(<input type="checkbox" id="all_msisdnlvl" value="" onclick="javascript:onClickMsisdnlvlAll()" />ALL)
		      			<form:checkboxes path="searchMsisdnlvl" items="${msisdnlvlList}" itemValue="parmValue" itemLabel="parmValue" />
		      		</td>
		      	</tr>
		        
		        <tr>
		        	<td colspan="2">
		        		<div class="buttonmenubox_R">
			        		<a href="#" class="nextbutton" onClick="javascript:formSubmit('SEARCH');">Search</a>
		        			<a href="#" class="nextbutton" onClick="javascript:onClear();">Clear</a>
	        			</div>
	        		</td>
	        	</tr>
      		</table>

      		<!-- MRT Summary -->
      		<table width="100%" border="0" cellspacing="1" class="contenttext mrtSummaryTable">
	      		<thead>
	      			<tr><td class="table_title" colspan="9" style="font-size: medium">MRT Summary</td></tr>
	      			<tr><th><input type="checkbox" id="all_msisdn" value="" />Select All</th><th>MRT</th><th>Grade</th><th>Store Code</th><th>Team Code</th><th>Staff ID</th><th>Status</th><th>Reserve ID</th><th>Num Type</th></tr>
	   			<thead>
	   			<tbody>
	   				<c:choose>
						<c:when test='${empty mrtManagement.mrtSummaryList}'>
		      				<tr><td colspan="9" align="center">No MRT found.</td></tr>
						</c:when>
						
						<c:otherwise>
							<c:forEach items="${mrtManagement.mrtSummaryList}" var="summary" varStatus="counter">
								<tr align="center"
									<c:if test="${counter.index >= 10}"> style="display:none"</c:if>
									<c:if test="${counter.index%2 == 0}"> class="row2"</c:if>
								>
									<td>
										<c:choose>
										<c:when test="${bomsalesuser.category != 'FRONTLINE' && 
										(summary.msisdnStatus == '2' || summary.msisdnStatus == '5' || summary.msisdnStatus == '25')}">
											<form:checkbox path="selectMsisdn" value="${summary.msisdn}" />
										</c:when>
										<c:when test="${summary.msisdnStatus == '19'}">
											${summary.orderId}
										</c:when>
										</c:choose>
									</td>
									<td>${summary.msisdn}</td>
									<td>${summary.msisdnlvl}</td>
									<td>${summary.storeCode}</td>
									<td>${summary.teamCode}</td>
									<td>${summary.staffId}</td>
									<td>${summary.msisdnStatusDesc}</td>
									<td>${summary.reserveId}</td>
									<td><pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${summary.numType}" /></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
      		</table>
	      	<c:if test='${not empty mrtManagement.mrtSummaryList}'>
				<div style="text-align: center; padding: 5px 0" class="mrtSummaryTable">
					<span class="control">
						<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
						<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
						<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
					</span>
				</div>
			</c:if>
	      	
	      	<!-- Assign Table -->
	      	<c:if test="${bomsalesuser.category != 'FRONTLINE'}">
	      	<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
      			<tr><td class="table_title" colspan="2" style="font-size: medium">Assign MRT</td></tr>
      			<tr>
		        	<td width="20%">
		            	Store Code: <span class="contenttext_red">*</span>
		          	</td>
		          	<td>
						<form:select path="storeCode">
							<form:option value="" label="Select" />
						</form:select>
		          	</td>
		        </tr>
		        <tr>
		          	<td width="20%">Team Code: <span class="contenttext_red">*</span>
		          	</td>
		          	<td>
						<form:select path="teamCode">
							<form:option value="" label="Select" />
							<c:if test="${isMdv}">
								<form:options items="${mdvTeamList}" />
							</c:if>
						</form:select>
		          	</td>
		        </tr>
		        <tr>
		          	<td width="20%">Status: <span class="contenttext_red">*</span>
		          	</td>
		          	<td>
						<form:select path="msisdnStatus">
							<form:option value="2" label="FREE" />
							<form:option value="5" label="FROZEN" />
							<form:option value="25" label="SUSPEND" />
						</form:select>
		          	</td>
		        </tr>
		        <tr>
		        	<td width="20%">
		            	Staff ID: 
			        </td>
			        <td>
						<form:input path="staffId" />
			        </td>
		        </tr>
		        <tr>
		        	<td colspan="2">
			        	<div class="buttonmenubox_R">
			        		<a href="#" class="nextbutton" onClick="javascript:formSubmit('ASSIGN');">Assign</a>
			        	</div>
		        	</td>
	        	</tr>
      		</table>
	      	</c:if>
    	</td>
  	</tr>
</table>

<form:hidden path="actionType"/>

</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>