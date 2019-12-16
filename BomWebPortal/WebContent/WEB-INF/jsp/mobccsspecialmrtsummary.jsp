<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
$(function() {
	

	$("#requestDateFrom").datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		dateFormat : "dd/mm/yy",
		//minDate : "-7D",
		maxDate : "+0",
		onSelect : function(dataText, inst) {
			
			$("#requestDateTo").datepicker("option", "minDate", $(this).datepicker("getDate"));
			$("#requestDateTo").datepicker("setDate", $(this).datepicker("getDate"));
			
		}
	});
	
});

$(function() {
	
	$("#requestDateTo").datepicker({
		changeMonth : true,
		changeYear : false,
		showButtonPanel : true,
		dateFormat : "dd/mm/yy",
		maxDate : "+0",
		beforeShow : function(input, ins) {
				
			$("#requestDateTo").datepicker("option", "minDate", $("#requestDateFrom").datepicker("getDate"));
			var futureDate = $(this).datepicker("getDate");
			futureDate.setDate(futureDate.getDate() + 7);
			$("#requestDateTo").datepicker("option", "maxDate", futureDate);
			
		}
	});
	
});

$(document).ready(function(){

	
	if ($("#requestDateFrom").val().length == 0) 	{
		
		/* $("#requestDateFrom").val($.datepicker.formatDate('dd/mm/yy', new Date()));
		$("#requestDateTo").val($.datepicker.formatDate('dd/mm/yy', new Date())); */
		
		$("#requestDateFrom").val("");
		$("#requestDateTo").val("");
	
	}
	
	$("#pageIndex a").click(function(e){
		e.preventDefault();
		switch ($(this).attr("id")) {
		case "prev":
			$("#index").val("previous");
			break;
		case "next":
			$("#index").val("next");
			break;
		default:
			$("#index").val($.trim($(this).text())); //click on digits...
		}
		$("#specialMRTSearchForm").submit();
		return false;
	});
	
	
	if ('<%=request.getAttribute("channelCd")%>' != 'CPA' ) {
	
		$("#tr3, #tr4, #tr5").hide();
		
	}
	
	
	if ('<%=request.getAttribute("channelCd")%>' == 'CPA' ) {
		
	 	if (!$("#requestDateFrom").val() && !$("#requestDateTo").val() &&  !$("#requestedBy").val() && !$("#mobNum").val()
				 && $("#channel").val()=='ALL' && !$("#resultStatus").val()){
			$("#searchResult tbody").empty();
			$("#pageIndex tbody").empty();
		} 
	
	}  else {
		
		if (!$("#requestDateFrom").val() && !$("#requestDateTo").val() && !$("#resultStatus").val()){
			$("#searchResult tbody").empty();
			$("#pageIndex tbody").empty();
		} 
		
	}  
	
		
});

function exit(){
	
	if ('<%=request.getAttribute("channelCd")%>' != 'CPA' ) {
	
		location.href="./welcome.html";
	
	} else {
		
		location.href="./mobccsmrtstock.html";
		
	}	
}

function formClear(){
	
	$("#requestDateFrom").val("");
	$("#requestDateTo").val("");
	$("#resultStatus option:first").attr("selected", true);
	$("#channel option:first").attr("selected", true);
	$("#requestedBy").val("");
	$("#mobNum").val("");
	$("#searchResult tbody").empty();
	$("#pageIndex tbody").empty();
	$("#warningMsg").html("");
	
}

function checkInputBeforeSubmit(){

	
	if ('<%=request.getAttribute("channelCd")%>' == 'CPA' && $("#resultStatus").val().length == 0 && $("#requestedBy").val().length==0 && $("#mobNum").val().length==0) {
		$("#warningMsg").html("<font color='#FF0000'>Please select a result status.</font>");
		return false;		
	}  else if ('<%=request.getAttribute("channelCd")%>' != 'CPA') {
		if ($("#resultStatus").val().length == 0) {
			$("#warningMsg").html("<font color='#FF0000'>Please select a result status.</font>");
			return false;
		}
	} 
	
	
	
	if ($("#resultStatus").val() == "ALL" && $("#requestDateFrom").val().length == 0 && $("#requestDateTo").val().length == 0 ) {
		
		$("#warningMsg").html("<font color='#FF0000'>Please input the Request Date range.</font>");
		return false;	
		
	}

		
	$("#specialMRTSearchForm").submit();
	
}

function toSpecialMRTRequest(requestId){

	
	location.href = "./mobccsspecialmrtrequest.html?" + $.param({
		
		requestId:requestId,
		from:'smr'
		
	});

}

</script>

<style type="text/css">
.specialMRTSearch { background-color: white; border: solid 2px #C0C0C0 }
.specialMRTSearchButton { text-align: right; padding: 0 0.5em 0.5em 0 }
.specialMRTSearchLabel { display: inline-block; width: 100px }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.specialMRTSearchResult { background-color: white }
</style>

<form:form name="specialMRTSearchForm" id="specialMRTSearchForm" method="POST" commandName="specialMRTSummary">
<div id="warningMsg"></div>
<div class="specialMRTSearch">
	<h2 class="table_title" style="font-size:medium;margin:0">Special MRT Request Summary</h2>
	<div class="overflowDiv">
	<table width="100%" border="0" cellspacing="1" class="contexttext">
	
	<tr>
		<td>
			<span class="specialMRTSearchLabel">Request Date</span>
		</td>
		<td>
			<form:input path="requestDateFrom" readonly="true"/>
		</td>
		<td>
			<span class="specialMRTSearchLabel">To</span>
		</td>
		<td>
			<form:input path="requestDateTo" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td>
			<span class="specialMRTSearchLabel">Result Status</span>
		</td>
		<td>
			<form:select path="resultStatus">
				<form:option value="" label="----Select----" />
				<form:options items="${resultStatusTypes}" itemValue="parmValue" itemLabel="parmValue"/>
			</form:select>
		</td>
	</tr>
		
	<tr id="tr3">
		<td>
			<span class="specialMRTSearchLabel">Channel</span>
		</td>
		<td>
			<form:select path="channel">
				<form:option value="ALL" label="ALL"/>
				<form:options items="${channelTypes}" itemValue="parmValue" itemLabel="parmValue"/>
			</form:select>
		</td>
	</tr>
	<tr id="tr4">
		<td>
			<span class="specialMRTSearchLabel">Requested By</span>
		</td>
		<td>
			<form:input path="requestedBy"/>
		</td>
	</tr>
	<tr id="tr5">
		<td>
			<span class="specialMRTSearchLabel">Mobile Number</span>
		</td>
		<td>
			<form:input path="mobNum"/>
		</td>
	</tr>
	
	<%-- </c:if> --%>
	</table>
	
	</div>
	<div class="specialMRTSearchButton">
		<input type="button" value="Search" onclick="javascript:checkInputBeforeSubmit();"/>
		<input type="button" value="Clear" onclick="javascript:formClear();"/>
		<input type="button" value="Exit" onclick="javascript:exit();"/> 
	</div>


</div>
<input type="hidden" id="index" name="index" value=""/>
</form:form>

<div class="overflowDiv specialMRTSearchResult" style="margin-top:5px">
<table id="searchResult" width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title">Request Number</td>
		<td class="table_title">Request Date</td>
		<td class="table_title">Requested By</td>
		<td class="table_title">Channel</td>
		<td class="table_title">Customer Name</td>
		<td class="table_title">MRT Pattern</td>
		<td class="table_title">Request Status</td>
		<td class="table_title">Mobile Number</td>
</thead>

<tbody>
	<c:choose>
	<c:when test="${sessionSpecialMRTRequestPagedList.pageList == null }">
	</c:when>
	<c:when test="${empty sessionSpecialMRTRequestPagedList.pageList}">
		<tr>
			<td colspan="13">No Data Found</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${sessionSpecialMRTRequestPagedList.pageList}" var="specialMRTRequestPagedList">
			<tr>
				<td><a href="javascript:toSpecialMRTRequest('${specialMRTRequestPagedList.requestId }')">${specialMRTRequestPagedList.requestId }</a></td>
				<td>${specialMRTRequestPagedList.requestDate }</td>
				<td>${specialMRTRequestPagedList.requestBy }</td>
				<td>${specialMRTRequestPagedList.channel }</td>
				<td>${specialMRTRequestPagedList.firstName } ${specialMRTRequestPagedList.lastName }</td>
				<td>${specialMRTRequestPagedList.msisdnPattern }</td>
				<td>${specialMRTRequestPagedList.approvalResult }</td>
				<td>${specialMRTRequestPagedList.msisdn }</td>
			</tr>
		</c:forEach>
	</c:otherwise>
	</c:choose>
</tbody>
</table>

<c:if test="${sessionSpecialMRTRequestPagedList.pageList != null && not empty sessionSpecialMRTRequestPagedList.pageList }">
	<table width="100%" id="pageIndex">
	<tr align="center" class="contenttext">

		<td>
			<c:if test="${!sessionSpecialMRTRequestPagedList.firstPage }">
				<a href="#" id="prev" class="contentext">&lt;</a>
			</c:if>
			<c:forEach begin="1" end="${noOfPages }" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<b>${i}</b>
					</c:when>
					<c:otherwise>
						&nbsp;&nbsp;<a href="#" class="contenttext">${i}</a>&nbsp;&nbsp;
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${!sessionSpecialMRTRequestPagedList.lastPage}">
				<a href="#" id="next" class="contenttext">&gt;</a>
			</c:if>
		</td>
	</tr>
	</table>
</c:if>

</div>

