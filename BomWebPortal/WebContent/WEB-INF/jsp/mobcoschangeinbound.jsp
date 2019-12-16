<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script>


function canelAction() {
	$(".checkbox").removeAttr('checked');
}

$(document).ready(function() {
	
	$('#clearButton').click(function(e) {
		$("#center option:first").attr("selected", true);
		$("#team option:first").attr("selected", true);
		$("#inboundindst4 option:first").attr("selected", true);
		$("#inboundindsow option:first").attr("selected", true); 
		$("#selectedStaffid").val("");		
		$('#actionType').val('CLEAR');
		$('#changeInboundForm').submit();
	});
	$('#quitButton').click(function(e) {
        close();
	});	
	$('#historyButton').click(function(e) {
		$('#actionType').val('HISTORY');

		$('#changeInboundForm').submit();
	});	
	$('#searchButton').click(function(e) {

		$('#actionType').val('SEARCH');
		$('#changeInboundForm').submit();
	});
	$('#assignbutton').click(function(e) {

		$('#actionType').val('ASSIGN');
		$('#assignForm').submit();
	});
	
});



function loadTeamId(selectedCenterId){
	$.ajax({				
		url : 'mobcoschangeinboundajax.html',
		type : 'POST',
		cache : false,
		data : {
			type : 'CCInstTeamList',
			selectedCenterId : this.$("#center").val(),
		},
		dataType : "json",
		timeout : 5000,
		error : function() {
			alert('Error loading Team List!');
		},
		success : function(data) {
			$('#team option').remove();
			$.each(eval(data), function(i, item) {
				$("<option value='" + item + "'>"
						+ item
						+ "</option>").appendTo(
						"#team");
		});						
	
		}
	});
}


</script>

<style type="text/css">
.MobCosStaffList { background-color: white; border: solid 2px #C0C0C0 }
.MobCosStaffListButton { text-align: right; padding: 0 0.5em 0.5em 0 }
.MobCosAssignButton { text-align: right; padding: 0 0.5em 0.5em 0 }
.MobCosStaffListLabel { display: inline-block; width: 90px }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.MobCosStaffListResult { background-color: white }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.orderSearch { padding-left: 0 }
.orderSearchResult { padding-bottom: 1em }
</style>
<![endif]-->
<c:if test="${mobCosChangeInbound.allowAccess}">
<form:form name="changeInboundForm" method="POST" commandName="mobCosChangeInbound">
<div class="MobCosStaffList">
	
	<h2 class="table_title" style="font-size:medium;margin:0">Searching Criteria</h2>
	<div class="overflowDiv">
	<div class="MobCosStaffListAssignButton" align="right">
	<input type="button" value="Quit" id="quitButton">
	<form:hidden path="allowAccess" id="allowAccess" />
	</div>
	<table width="100%" border="0" cellspacing="1" class="contenttext">
		<tr>
			<td>
				<span class="MobCosStaffListLabel">Channel</span>
			</td>
			<td>
				<form:input path="selectedChannelCd" id="selectedChannelCd" readonly="true" />
			</td>
		<tr>
			<td>
				<span class="MobCosStaffListLabel">*Center</span>
			</td>
			<td>
				<form:select path="selectedCenterId" id="center" onchange="javascript:loadTeamId(selectedCenterId);" >
					<form:options items="${center}" />
				</form:select>
			</td>
			<td>
				<span class="MobCosStaffListLabel">OR      *Staff ID</span>
			</td>
			<td>
				<form:input path="selectedStaffid" id="selectedStaffid"  />
			</td>
			<td>
			    <div class="MobCosStaffListHistoryButton" align="right">
	            <input type="submit" value="History" id="historyButton">
	            </div>
			</td>			
		</tr>
		<tr>
			<td>
				<span class="MobCosStaffListLabel">*Team</span>
			</td>
			<td>
				<form:select path="selectedTeamId" id="team">
					<form:options items="${team}"  id="teamCd"/>
				</form:select>
			</td>
			<td>

			</td>
		</tr>
		<tr>
			<td>
				<span class="MobCosStaffListLabel">Inbound Ind (ST4)</span>
			</td>
			<td>
				<form:select path="selectedInBoundindSt4" id="inboundindst4">
					<form:option value="all" label="Select"/>
					<form:option value="Y" label="Y" />
					<form:option value="N" label="N"/>
				</form:select>
			</td>
			<td>
	
			</td>
		</tr>
		<tr>
			<td>
				<span class="MobCosStaffListLabel">Inbound Ind (Overwrite)</span>
			</td>
			<td>
				<form:select path="selectedInBoundindOw" id="inboundindsow">
					<form:option value="all" label="Select"/>
					<form:option value="Y" label="Y" />
					<form:option value="N" label="N"/>
					<form:option value="NA" label="N/A"/>
				</form:select>
			</td>
			<td>
	
			</td>
		</tr>
	</table>
	<br/>
	</div>
	<div class="MobCosStaffListSearchButton" align="right">
		<input type="submit" value="Search" id="searchButton">
		<input type="submit" value="Clear" id="clearButton">
	</div>
	<div style="clear:both"></div>
</div>
<input type="hidden" id="index" name="index" value=""/> 

<c:choose>
<c:when test="${mobCosChangeInbound.actionType == 'SEARCH' || mobCosChangeInbound.actionType == 'ASSIGN'}">

<div class="overflowDiv MobCosStaffListSearchResult" id="div1" style="margin-top:5px">
<div>
<form:errors path="staffList" cssClass="error"/>
</div>
<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title"><span style="display:inline-block;width: 150px">Staff ID</span></td>
		<td class="table_title"><span style="display:inline-block;width: 200px">Channel / Center /Team</span></td>
		<td class="table_title"><span style="display:inline-block;width: 150px">Inbound Ind (ST4)</span></td>
		<td class="table_title"><span style="display:inline-block;width: 150px">Inbound Ind (Overwrite)</span></td>
		<td class="table_title"><span style="display:inline-block;width: 150px">Action</span></td>

	</tr>
</thead>
<tbody>
	<c:choose>
	
	<c:when test="${empty mobCosChangeInbound.staffList}">
		<tr>
			<td colspan="13">No Data Found</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${mobCosChangeInbound.staffList}" var="staff" varStatus="status">
	
		<tr>
			<td><span style="display:inline-block;width: 150px">${staff.staffid}</span></td>
			<td>
			<span style="display:inline-block;width: 150px">${staff.channelCd}
			<font>/</font>
			${staff.centerId}
			<font>/</font>
			${staff.teamId}
			</td>
			<td><span style="display:inline-block;width: 150px">${staff.inBoundindSt4}</span></td>
			<td><span style="display:inline-block;width: 150px">${staff.inBoundindOw}
			 <c:if test="${not empty staff.inBoundindOwLastUpdDate }">
			 <font>(</font>${staff.inBoundindOwLastUpdDate}<font>)</font>
			 </c:if> </span>
			</td>
			<td><form:checkbox path="staffList[${status.index}].actionInd" cssClass="checkbox" />
			<span style="display:inline-block;width: 150px">${staff.action}</span></td>	
		</tr> 
		</c:forEach>
	</c:otherwise>
</c:choose>
</tbody>
</table>
	<div class="MobCosStaffListAssignButton" align="right">
		<input type="submit" value="Assign" id="assignbutton">
		<input type="button" value="Cancel" onClick="javascript:canelAction();">
	</div>
	<div style="clear:both"></div>
</div>
</c:when>
<c:otherwise>
<div class="overflowDiv MobCosStaffListSearchResult" id="div2" style="margin-top:5px">
<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title"><span style="display:inline-block;width: 80px">Staff ID</span></td>
		<td class="table_title"><span style="display:inline-block;width: 200px">Channel / Center /Team</span></td>
		<td class="table_title"><span style="display:inline-block;width: 130px">Inbound Ind (ST4)</span></td>
		<td class="table_title"><span style="display:inline-block;width: 100px">Inbound Ind (Overwrite)</span></td>
		<td class="table_title"><span style="display:inline-block;width: 70px">Active</span></td>
		<td class="table_title"><span style="display:inline-block;width: 100px">Create Date</span></td>
		<td class="table_title"><span style="display:inline-block;width: 100px">Create By</span></td>
		<td class="table_title"><span style="display:inline-block;width: 100px">Last Update Date</span></td>
		<td class="table_title"><span style="display:inline-block;width: 100px">Last Update By</span></td>

	</tr>
</thead>
<tbody>
	<c:choose>
	
	<c:when test="${empty mobCosChangeInbound.staffList}">
		<tr>
			<td colspan="13">No Data Found</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${mobCosChangeInbound.staffList}" var="staff" varStatus="status">
	
		<tr>
			<td><span style="display:inline-block;width: 80px">${staff.staffid}</span></td>
			<td>
			<span style="display:inline-block;width: 150px">${staff.channelCd}
			<font>/</font>
			${staff.centerId}
			<font>/</font>
			${staff.teamId}
			</td>
			<td><span style="display:inline-block;width: 130px">${staff.inBoundindSt4}</span></td>
			<td><span style="display:inline-block;width: 100px">${staff.inBoundindOw}</span></td>			
			<td><span style="display:inline-block;width: 70px">${staff.activeInd}</span></td>
			<td><span style="display:inline-block;width: 100px">${staff.inBoundindOwLastUpdDate}</span></td>
			<td><span style="display:inline-block;width: 100px">${staff.createBy}</span></td>
			<td><span style="display:inline-block;width: 100px">${staff.lastUpdDate}</span></td>
			<td><span style="display:inline-block;width: 100px">${staff.updBy}</span></td>		
		</tr> 
		</c:forEach>
	</c:otherwise>
</c:choose>
</tbody>
</table>
</div>
</c:otherwise>
</c:choose>
<input type="hidden" id="index" name="index" value=""/> 
</div><form:hidden path="actionType"/>
</form:form>
</c:if>
<c:if test="${not mobCosChangeInbound.allowAccess}">
You are not allowed to access.
</c:if>