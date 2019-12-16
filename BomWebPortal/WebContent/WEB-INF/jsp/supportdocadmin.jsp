<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib"%>


<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<link href="css/imsds.css" rel="stylesheet" type="text/css"> 
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script type="text/javascript">
function initCollectMethodChanged() {
	
	$("input[name=dmsInd]").attr("disabled", false);
	switch ($("input[name=collectMethod]:checked").val()) {
	case "P":
		$("input[name=dmsInd]").slice(0, 3).attr("disabled", true);
		//$("input[name=dmsInd]").filter('[value=NA]').attr("checked", true);
		break;

	case "D":
		$("input[name=dmsInd]").filter('[value=NA]').attr("disabled", true);
		//$("input[name=dmsInd]:first").attr("checked", true);
		break;
	default:
		$("input[name=dmsInd]").attr("disabled", true);
		//$("input[name=dmsInd]:first").attr("checked", true);		
	}

}
function collectMethodChanged(init) {
	$("input[name=dmsInd]").attr("disabled", false);
	switch ($("input[name=collectMethod]:checked").val()) {
	case "P":
		$("input[name=dmsInd]").slice(0, 3).attr("disabled", true);
		$("input[name=dmsInd]").filter('[value=NA]').attr("checked", true);
		break;
	case "":
		$("input[name=dmsInd]").attr("disabled", true);
		$("input[name=dmsInd]:first").attr("checked", true);
		break;
	case "D":
		$("input[name=dmsInd]").filter('[value=NA]').attr("disabled", true);
		$("input[name=dmsInd]:first").attr("checked", true);
		break;
	default:
		$("input[name=dmsInd]").attr("disabled", true);
		$("input[name=dmsInd]:first").attr("checked", true);
		$("input[name=dmsInd]:first").attr("checked", true);
	}
}
$(document).ready(function() {
	$("input[name=collectMethod]").change(collectMethodChanged);
	initCollectMethodChanged();
	$("input[type=text]").keypress(function(e) {
		switch (e.keyCode ? e.keyCode : e.which) {
		case 13:
			$("form[name=supportDocAdminForm]").submit();
			break;
		}
	});
});

function formClear() {
	//alert('formClear function called');
	document.supportDocAdminForm.orderId.value = "";


	//$('input:radio[name=collectionStatus]').filter('[value=ALL]').attr('checked', true);
	<c:if test='${shopCode == "TTW"}'>
		$('input:radio[name=selectedShopCode]').filter('[value=ALL]').attr(
				'checked', true);
	</c:if>
	setCurrentDate('CLEAR');

	$('#search_results').hide();

}




function setCurrentDate( callType) {
	if (callType=='CLEAR'){
		//alert('setCurrentDate function called');
		var currentTime = new Date();
		var month = currentTime.getMonth() + 1;
		var day = currentTime.getDate();
		var year = currentTime.getFullYear();
		
		document.supportDocAdminForm.startDate.value = day + "/" + month + "/"	+ year;
		document.supportDocAdminForm.endDate.value =  day + "/" + month + "/"	+ year; 
	}

	$(function() {
		$('#dateDatepickerStart').datepicker({
				changeMonth : true,
				changeYear : true,
				showButtonPanel : true,
				dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
				maxDate: "+0d",yearRange: '2010:+0'
				, onSelect: function(dateText, inst) {
					var futureDate = $(this).datepicker("getDate");
					var toDate = new Date ();
					var futureDateMin = $(this).datepicker("getDate");
					futureDateMin.setDate(futureDate.getDate() - 6);

					//var maxDate = $( ".selector" ).datepicker( "option", "maxDate" );
					//alert(toDate);
					futureDate.setDate(futureDate.getDate() + 6);
					$('#dateDatepickerEnd').attr("disabled", false);
					$('#dateDatepickerEnd').datepicker( "option", "minDate", $(this).datepicker("getDate"));
					if (futureDate < toDate ){
					$('#dateDatepickerEnd').datepicker( "option", "maxDate", futureDate);
					$('#dateDatepickerEnd').datepicker( "setDate", $(this).datepicker("getDate"));
					}else{
						$('#dateDatepickerEnd').datepicker( "option", "maxDate", toDate);
						//$('#dateDatepickerEnd').datepicker( "option", "minDate", futureDateMin);
						$('#dateDatepickerEnd').datepicker( "setDate", $(this).datepicker("getDate"));
					}
					
					
				}
			});
			$('#dateDatepickerEnd').datepicker({
				changeMonth : true,
				changeYear : true,
				showButtonPanel : true,
				dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
				maxDate: "+0d",yearRange: '2010:+0'
			});
	}); 
	
//	$( "#dateDatepickerStart" ).datepicker( "option", "defaultDate", +5 );

}

function calcDate() {
    var start = $('#dateDatepickerStart').datepicker('getDate');
    var end = $('#dateDatepickerEnd').datepicker('getDate');
    var days = (end - start) / 1000 / 60 / 60 / 24;

   // alert('date diff'+days);
    return days;
    //document.getElementById('total_days').value = days;
}

$(function() {
	//alert('main function called, set default value');

	<c:if test='${shopCode == "TTW" || shopCode == "ALL"}'>
	var $radios3 = $('input:radio[name=selectedShopCode]');
	if ($radios3.is(':checked') == false) {
		$radios3.filter('[value=ALL]').attr('checked', true);
	}
	$radios3.filter('[value=${command.selectedShopCode}]').attr('checked', true);
	</c:if>

	var $radiolob = $('input:radio[name=lob]');
	if ($radiolob.is(':checked') == false) {
		$radiolob.filter('[value=ALL]').attr('checked', true);
	}
	$radiolob.filter('[value=${command.lob}]').attr('checked', true);
	
	setCurrentDate();
	

	if (!$("input[name=collectMethod]").is(":checked")) {
		$("input[name=collectMethod]:first").attr("checked", true);
	}

	if (!$("input[name=dmsInd]").is(":checked")) {
		$("input[name=dmsInd]:first").attr("checked", true);
	}
});

var currentPage = ${command.page}+0;
function nextPage() {
	if (${hasNext}) {
		//formSubmit(currentPage+1);
		$("input[name='page']").val(currentPage+1);
		
		document.supportDocAdminNavForm.submit();
	} else {
		alert("No more page");
	}
}

function prevPage() {
	if (currentPage == 0) {
		alert("No more page");
		return;
	}
	//formSubmit(currentPage-1);
	$("input[name='page']").val(currentPage-1);
	
	document.supportDocAdminNavForm.submit();
}
function formSubmit() {
	
	if ($("input[name='orderId']").val() == "") {

		var daysDiff=calcDate();
		//alert(daysDiff);
		if (daysDiff >=14){
			alert("End Date - Start Date must less or equal then 14 days ");
			return;
		}
		//alert($("input[name='selectedShopCode']:checked").val());
		if ($("input[name='selectedShopCode']:checked").val() =='ALL'){
			
			if (document.supportDocAdminForm.startDate.value != document.supportDocAdminForm.endDate.value){
				alert("Start and End date must be same, when Selected ALL shop");
				//e.preventDefault();
				return false;
			}
		}
	}
	
	//$("input[name='page']").val(page);
	
	document.supportDocAdminForm.submit();

}


</script>

<script type="text/javascript">
// for grouping table ...
$(function() {
//Created By: Brij Mohan
//Website: http://techbrij.com
	function groupTable($rows, startIndex, total){
		if (total === 0){
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		for (i=1;i<=tds.length;i++){
			if (ctrl.text() ==  $(tds[i]).text()){
				count++;
				$(tds[i]).addClass('deleted');
					lst.push($rows[i]);
				}
			else{
				if (count>1){
					ctrl.attr('rowspan',count);
					groupTable($(lst),startIndex+1,total-1)
				}
				count=1;
				lst = [];
				ctrl=$(tds[i]);
				lst.push($rows[i]);
			}
		}
	}
	
	groupTable($('#resultTable tr:has(td)'),0,6);
	$('#resultTable .deleted').remove();
});
</script>



<form:form name="supportDocAdminForm" method="POST"
	commandName="command" action="supportdocadmin.html">
	<input type="hidden" name="action" value="SEARCH"/>


<table width="100%" class="tablegrey">
	<tr>
		<td bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="1">
				<tr>
					<td class="table_title">Supporting Document Admin</td>
				</tr>
			</table>
			
			<table width="100%" border="0" cellspacing="1"
					bgcolor="#FFFFFF">

				<tr class="contenttext">
					<td width="500px">
						<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF">

						<c:choose>
						<c:when test='${(shopCode == "TTW" || shopCode == "ALL") && allowSearchAllInd=="Y"}'>
							<tr>
								<td width="130px"><b>Shop Code: </b></td>
								<td>
									<form:radiobutton path="selectedShopCode" value="ALL" label="All"/> 
									<form:radiobutton path="selectedShopCode" value="TTW" label="TTW"/>
								</td>

							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td width="130px"><b>Shop Code: </b></td>
								<td>${shopCode}</td>
							</tr>
						</c:otherwise>
						</c:choose>
							<tr>
								<td><b>LOB :</b></td>
								<td>
									<form:radiobutton path="lob" value="ALL" label="All"/>
									<form:radiobutton path="lob" value="LTS" label="LTS"/>
									<form:radiobutton path="lob" value="IMS" label="IMS"/>
									<form:radiobutton path="lob" value="MOB" label="MOB"/>
								</td>
							</tr>
							<tr>
								<td><b>Application Date :</b>
								</td>

								<td>
									<form:input path="startDate" maxlength="10" id="dateDatepickerStart" readonly="true" />
									to 
									<form:input path="endDate" maxlength="10" id="dateDatepickerEnd" readonly="true"/>
								</td>

							</tr>
							<tr>
								<td><b>Collection Method:</b></td>
								<td>
									<form:radiobutton path="collectMethod" value="" label="All"/>
									<form:radiobutton path="collectMethod" value="D" label="Digital"/>
									<form:radiobutton path="collectMethod" value="P" label="Paper"/>
								</td>
							</tr>
							<tr>
								<td><b>DMS Indicator:</b></td>
								<td>
									<form:radiobutton path="dmsInd" value="" label="All"/>
									<form:radiobutton path="dmsInd" value="Y" label="Uploaded"/>
									<form:radiobutton path="dmsInd" value="N" label="Outstanding"/>
									<form:radiobutton path="dmsInd" value="NA" label="Not Applicable"/>
								</td>
							</tr>
						</table>
					</td>
					
					
					
					<td valign="middle" width="50px"><b>OR</b>
					</td>
					<td valign="middle">
						<table bgcolor="#FFFFFF">
							<tr>
								<td><b>Order ID: </b><form:input path="orderId" maxlength="20"/></td>
							</tr>
						</table>
					</td>	
				</tr>
				
				
				
				<tr>
					<td colspan="3">
						<div class="buttonmenubox_R">
							<a href="#" class="nextbutton"	onClick="javascript:formSubmit();">Search</a> 
							<a href="#"	class="nextbutton" onClick="javascript:formClear();">Clear	</a><br/>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
			<div>&nbsp;</div>
			<div id="search_results">
				<c:if test='${supportDocList != null}'>
				
					<c:if test="${not empty supportDocList }">
					<table width="100%">
						<tr class="contenttext">
							<td align="left" width="100px">
								<c:if test="${hasPrev}">
								<a href="#" class="nextbutton" onClick="javascript:prevPage();">&lt; Prev</a>
								</c:if>
								&nbsp;
							</td>
							<td class="contenttextgreen" align="center"><b>Page ${command.page+1} of ${numOfPages }</b></td>
							<td align="right" width="100px">
								<c:if test="${hasNext}">
								<a href="#" class="nextbutton" onClick="javascript:nextPage();">Next &gt;</a>
								</c:if>
							</td>
						</tr>
					</table>
					</c:if>

				<div>&nbsp;</div>
				<table id="resultTable" width="100%" border="1" cellspacing="0" class="contenttext"
							bgcolor="#FFFFFF" cellpadding="5px">
		
		
					<tr class="contenttextgreen">
						<th class="table_title" align="center">Shop Code</th>
						<th class="table_title" align="center">Order Id</th>
						<th class="table_title" align="center">LOB</th>
						<th class="table_title" align="center">Distribution Mode</th>
						<th class="table_title" align="center">Collection Method</th>
						<th class="table_title" align="center">DMS Indicator</th>
						<th class="table_title" align="center">Doc Type</th>
						<th class="table_title" align="center">Collection Status</th>
						<!-- 
						<th class="table_title" align="center">Waive Reason</th>
						<th class="table_title" align="center">Waived By</th>
						 -->
						<th class="table_title" align="center">Last Upd Date</th>
						<th class="table_title" align="center">Last Upd By</th>
					</tr>
					<c:if test="${empty supportDocList}">
					<tr>
						<td colspan="11" align="center" height="80px">No record.</td>
					</tr>
					</c:if>
					<c:forEach items="${supportDocList}" var="supportDoc">
					<tr height="30px">
						<td align="center">${supportDoc.shopCd }</td>
						<td align="center"><a href="requiredproofadmin.html?orderId=${supportDoc.orderId }">${supportDoc.orderId }</a> </td>
						<td align="center">${supportDoc.lob }</td>
						<td align="center">${supportDoc.disMode=="E" ? "e-Signature" : (supportDoc.disMode=="P"?"Paper" : "&nbsp;")}</td>
						<td align="center">${supportDoc.collectMethod=="D" ? "Digital" : (supportDoc.collectMethod=="P"?"Paper" : "&nbsp;")}</td>
						<td align="center">${supportDoc.collectMethod=="D" ? (supportDoc.dmsInd=="Y"?"Uploaded":"Outstanding") : "Not Applicable"}</td>
						<td>${supportDoc.docName }</td>
						<td align="center">${empty supportDoc.waiveReason ? (supportDoc.collectedInd=="Y"?"Collected":"Outstanding") : "Waived" }</td>
						<!-- 
						<td><c:out value="${supportDoc.waiveDesc}" default="&nbsp;" escapeXml="false"/></td>
						<td align="center"><c:out value="${supportDoc.waivedBy}" default="&nbsp;" escapeXml="false"/></td>
						 -->
						<td align="center">
							<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${supportDoc.lastUpdDate }" />
						</td>
						<td align="center">${supportDoc.lastUpdBy }</td>
						
					</tr>
					</c:forEach>				
				</table>
				<br/>
				<c:if test="${not empty supportDocList }">
				<table width="100%">
					<tr class="contenttext">
						<td align="left" width="100px">
							<c:if test="${hasPrev}">
							<a href="#" class="nextbutton" onClick="javascript:prevPage();">&lt; Prev</a>
							</c:if>
							&nbsp;
						</td>
						<td class="contenttextgreen" align="center"><b>Page ${command.page+1} of ${numOfPages }</b></td>
						<td align="right" width="100px">
							<c:if test="${hasNext}">
							<a href="#" class="nextbutton" onClick="javascript:nextPage();">Next &gt;</a>
							</c:if>
						</td>
					</tr>
				</table>
				</c:if>
				<br/>
				</c:if>
			</div>
			
		</td>
	</tr>
				
</table>

</form:form>
<form:form name="supportDocAdminNavForm" method="GET"
	commandName="command" action="supportdocadmin.html">
	<input type="hidden" name="reload" value="true"/>
	<input type="hidden" name="page"/>
</form:form>



<!-- -------------------------------------  end content ----------------------------------------- -->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>