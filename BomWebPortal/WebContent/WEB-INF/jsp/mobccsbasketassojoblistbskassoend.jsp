<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<style type="text/css">
.basketSelectResult { height: 300px; overflow: auto; }
.even { background-color: rgb(232,255,232); }
.searchTitle { text-align: left; }
.blueHeader { background-color: rgb(232, 242, 254) }
.error_endDateBeforeNowStr .baskets span { display: block }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
function toDate(str) {
	if (str == null) return null;
	str = $.trim(str);
	if (str.length == 0) return null;
	return $.datepicker.parseDate("dd/mm/yy", str);
}
function getToday() {
	var date = new Date();
	date.setHours(0);
	date.setMinutes(0);
	date.setSeconds(0);
	date.setMilliseconds(0);
	return date;
}
$(document).ready(function() {
	$('#endDateDatepicker').datepicker({
		changeMonth : true,
		changeYear : true,

		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-100Y",
		yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
	//yearRange: "1911:1993" //the range shown in the year selection box
	});
	$('#endDateDatepicker').datepicker("setDate", new Date());

	$("input[name=backButton]").click(function() {
		window.location.href = "mobccsbasketassojoblistbskasso.html";
	});
	
	$("#endForm").submit(function(e) {
		$(".error").hide();
		if ($("input[name=rowIds]:checked").length == 0) {
			$(".error_basket").text("Select at least one basket").show();
		}
		if ($("input[name=endDate]").val().length == 0) {
			$(".error_endDate").text("End Date required");
		} else {
			var now = getToday();
			var selEndDate = toDate($("input[name=endDate]").val());
			var endDateBeforeNow = [];
			var endDateBeforeStartDate = [];
			$("input[name=rowIds]:checked").each(function(index, rowId) {
				var $div = $(rowId).parent().parent();
				var basketId = $div.find("span:eq(1)").text();
				var basketDesc = $div.find("span:eq(2)").text();
				var startDate = toDate($div.find("span:eq(3)").text());
				if (selEndDate < now) {
					endDateBeforeNow[endDateBeforeNow.length] = { "basketId": basketId, "basketDesc": basketDesc };
				} else if (selEndDate < startDate) {
					endDateBeforeStartDate[endDateBeforeStartDate.length] = { "basketId": basketId, "basketDesc": basketDesc };
				} 
			});
			if (endDateBeforeNow.length > 0) {
				for (var i = 0; i < endDateBeforeNow.length; i++) {
					$(".error_endDateBeforeNowStr .baskets").append($("<span/>").text(endDateBeforeNow[i].basketId + " - " + endDateBeforeNow[i].basketDesc));
				}
				$(".error_endDateBeforeNowStr").show();
			}
			if (endDateBeforeStartDate.length > 0) {
				for (var i = 0; i < endDateBeforeStartDate.length; i++) {
					$(".error_endDateBeforeStartDateStr .baskets").append($("<span/>").text(endDateBeforeStartDate[i].basketId + " - " + endDateBeforeStartDate[i].basketDesc));
				}
				$(".error_endDateBeforeStartDateStr").show();
			}
		}
		if ($(".error:visible").length > 0) {
			e.preventDefault();
			return false;
		}
	});
});
</script>


<form:form method="POST" action="mobccsbasketassojoblistbskassoend.html" id="endForm">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:10%"></colgroup>
<tr class="contenttextgreen">
	<td class="table_title" colspan="9">Job List / Basket(Campaign Offer) End Association</td>
</tr>
<tr>
	<td>Job List</td>
	<td>
		<c:out value="${command.jobList}"/><form:hidden path="jobList"/>
		<div style="clear:both"></div>
		<form:errors path="jobList" cssClass="error"/>
	</td>
</tr>
<tr>
	<td>Already selected basket</td>
	<td id="alreadySelectedBasket">
		<div style="padding: 3px 0" class="blueHeader">
			<span style="width: 25px;display: inline-block">&nbsp;</span>
			<span style="width: 100px;text-align: center;font-weight: bold;display: inline-block">Basket ID</span>
			<span style="width: 600px;text-align: center;font-weight: bold;display: inline-block">Basket Desc</span>
			<span style="width: 90px;text-align: center;font-weight: bold;display: inline-block">Start Date</span>
			<span style="width: 90px;text-align: center;font-weight: bold;display: inline-block">End Date</span>
		</div>
		<c:forEach items="${resultList}" var="item" varStatus="status">
		<div style="padding: 3px 0"<c:if test="${status.index % 2 == 0}"> class="even"</c:if>>
			<span style="width: 25px;display: inline-block"><input type="checkbox" name="rowIds" value="${item.rowId}"></span>
			<span style="width: 100px;vertical-align: top;display: inline-block">${item.basketId}</span>
			<span style="width: 600px;vertical-align: top;display: inline-block">${item.basketDesc}</span>
			<span style="width: 90px;vertical-align: top;display: inline-block"><fmt:formatDate pattern="dd/MM/yyyy" value="${item.startDate}"/></span>
			<span style="width: 90px;vertical-align: top;display: inline-block"><fmt:formatDate pattern="dd/MM/yyyy" value="${item.endDate}"/></span>
			<input type="hidden" name="alreadyBasketIds" value="${item.basketId}" disabled="disabled">
		</div>
		<div style="clear:both"></div>
		</c:forEach>
		<div style="clear:both"></div>
		<span class="error error_basket" style="display:none"></span>
	</td>
</tr>
<tr>
	<td>End Date</td>
	<td>
		<form:input path="endDate" id="endDateDatepicker" readonly="true"/>
		<div style="clear:both"></div>
		<form:errors path="endDate" cssClass="error"/>
		<div style="clear:both"></div>
		<span class="error error_endDate" style="display:none"></span>
		<div style="clear:both"></div>
		<span class="error error_endDateBeforeStartDateStr" style="display:none">
			Please select End Date on / after Start Date for:
			<br><span class="baskets"></span>
		</span>
		<div style="clear:both"></div>
		<span class="error error_endDateBeforeNowStr" style="display:none">
			Please select End Date on / after Now for:
			<br><span class="baskets"></span>
		</span>
	</td>
<tr>
	<td colspan="2">
		<input type="button" name="backButton" value="Quit" style="float:right">
		<input type="submit" name="endButton" value="End" style="float:right">
	</td>
</tr>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>