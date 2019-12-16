<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>

<script type="text/javascript" src="js/jquery-1.10.2.js"></script> 
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="js/dataTables.tableTools.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.1.10.0.js"></script>
<link type="text/css" href="css/dataTable/jquery.dataTables.css"
	rel="stylesheet" />
<link type="text/css" href="css/dataTables.tableTools.css"
	rel="stylesheet" />
<!-- <link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/custom-theme/jquery.dataTables.css" />
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script> 
<script type="text/javascript" language="javascript"	src="js/jquery.dataTables.1.10.0.js"></script>

<!-- <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.colReorderWithResize.plugin.js"></script> 
<script src="js/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="../css/custom-theme/datatable/js/jquery.dataTables.js"></script>
<link href="../css/custom-theme/datatable/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="dataTables.filter.html.js"></script> -->
<script>
function formSubmit() {
	document.orderReviewForm.submit();
}

var dataTable; // = $('#mobdsorderreview').DataTable();

$(document).ready(function() {	
	$dataTable = $('#mobdsorderreview').dataTable({
		"sDom": 'T<"clear">lfrtip',
        "oTableTools": {
        	"sSwfPath": "<c:url value='/js/copy_csv_xls_pdf.swf'/>",
            "aButtons": [
                {
					"sExtends": "csv",
					"oSelectorOpts": {
				        filter: "applied"
				    },
					"sButtonText": "Export csv",
					"mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ],
					"bSelectedOnly": true,
					"fnClick": function (nButton, oConfig, oFlash) {
	                   	oFlash.setFileName("export_ds_review.csv");
	                   	this.fnSetText(oFlash, this.fnGetTableData(oConfig));
 					}
                }
            ]
        }
    });

	 
	 $('#filterAppDateTo').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
			maxDate: "+0d",yearRange: '2010:+0'
		});
	 
	 $('#filterAppDateFrom').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
			maxDate: "+0d",yearRange: '2010:+0'
		});

	regAppDateSearch();
	
	$('#allDocUpldFilter').click(function () {
		if ($(this).is(':checked')) {
			$dataTable.fnFilter('Y', 9, true, false);
		} else {
			$dataTable.fnFilter('', 9, true, false);
		}
	});

	$('#supDocRcvFilter').click(function () {
		if ($(this).is(':checked')) {
			$dataTable.fnFilter('Y', 10, true, false);
		} else {
			$dataTable.fnFilter('', 10, true, false);
		}
	});
	
	$('#signoffDateFilter').click(function () {
		if ($(this).is(':checked')) {
			if($('#filterAppDateTo').val()==""||$('#filterAppDateFrom').val()==""){
				$('#datePickErrMsg').attr("disabled", false);
			}else{
				$dataTable.fnDraw();
			}
		}else{
			$("#filterAppDateFrom").val("");
			$("#filterAppDateTo").val("");
			$dataTable.fnDraw();
			$dataTable.fnFilter('', 10, true, false);
			$("#signoffDateFilter").attr("disabled", true);
		}
	});
	
	//signoffDateFilter
	
	$('#channelCdFilter').click(function () {
		if ($(this).is(':checked')) {
			$('input[name=channelCd]').attr("disabled", false);
			$('input[name=channelCd]').change();
		} else {
			$dataTable.fnFilter('', 0, true, false);
			$('input[name=channelCd]').attr("disabled", 'disabled');
		}
	});

	$('input[name=channelCd]').change(function () {
		$dataTable.fnFilter('', 0, true, false);//reset
		
		var channel = $("input[name=channelCd]:checked").val();
		if (channel == "SIS") {
			$dataTable.fnFilter('^((?!MDS).)*$', 0, true, false);
		} else if (channel == "MDV") {
			$dataTable.fnFilter('MDS', 0, true, false);
		}
	});
	
	$('#filterAppDateTo').change(function(){
		if($('#filterAppDateTo').val()!=""&&$('#filterAppDateFrom').val()!=""){
			$("#signoffDateFilter").attr("disabled", false);
		}
	});
	$('#filterAppDateFrom').change(function(){
		if($('#filterAppDateTo').val()!=""&&$('#filterAppDateFrom').val()!=""){
			$("#signoffDateFilter").attr("disabled", false);
		}
	});
});

function regAppDateSearch() {
	//$.format.date(new Date(), "dd/MM/yyyy H:mm:ss");
	$.fn.dataTableExt.afnFiltering.push(
		    function( settings, data, dataIndex ) {
		    	
		        var min = string2date($('#filterAppDateFrom').val());console.log("min = "+min);
		        var max = string2date($('#filterAppDateTo').val());console.log("max = "+max);
		        var appDate = string2date( data[8] ) || null;console.log("appDate = "+appDate);

		        if ( ( min == null    && max == null ) ||
		             ( min == null    && appDate <= max ) ||
		             ( min <= appDate && max == null ) ||
		             ( min <= appDate && appDate <= max ) )
		        {
		            return true;
		        }
		        return false;
		    }
		);
}

function string2date(dateString) {
   if (dateString == null || dateString == "") {
	   return null;
   } else if (dateString.length != 10) {
	   return null;
   }
   var arr = dateString.split("/");
   var result = new Date(arr[2],parseInt(arr[1])-1,arr[0]);
   if (!isValidDate(result)) {
	   return null;
   }
   return result;
}

function isValidDate(d) {
	if ( Object.prototype.toString.call(d) !== "[object Date]" )
	   return false;
	return !isNaN(d.getTime());
}


</script>

<style type="text/css">
.orderSearch { background-color: white; border: solid 2px #C0C0C0 }
.orderSearchButton { text-align: right; padding: 0 0.5em 0.5em 0 }
.orderSearchLabel { display: inline-block; width: 90px }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.overflowDiv td {text-align: center;}
.orderSearchResult { background-color: white }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.orderSearch { padding-left: 0 }
.orderSearchResult { padding-bottom: 1em }
</style>
<![endif]-->
<form:form name="orderReviewForm" method="POST" commandName="orderReview">
<div class="orderSearch">
	<h2 class="table_title" style="font-size:medium;margin:0">MOB Order Review</h2>
</div>

<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%
String scheme = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");

%>

<div class="overflowDiv orderSearchResult" style="margin-top:5px">

<div class="contenttext" style="font-size:14px">Filtering Options:</div>
<div class="contenttext"><input id="allDocUpldFilter" type="checkbox" value="Y" />Filter out orders with all document uploaded</div>
<div class="contenttext"><input id="supDocRcvFilter" type="checkbox" value="Y" />Filter out orders with all original copies of support documents received</div>
<div class="contenttext"><input id="signoffDateFilter" type="checkbox" value="Y" disabled/>Filter out orders with application date between <input type="text" id="filterAppDateFrom" value=""/> and <input type="text" id="filterAppDateTo" value=""/> <span id="datePickErrMsg"style="color:red;display:none">Please choose date first.</span></div>
<div class="contenttext"><input id="channelCdFilter" type="checkbox" value="Y" />Show only orders from
		<input type="radio" name="channelCd" checked="checked" id="SIS" value="SIS" disabled> SIS<input type="radio" id="MDV" name="channelCd" value="MDV" disabled> MDV</div>
<br>

<div id="demo">
<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="mobdsorderreview">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title">Order ID</td>
		<td class="table_title">Service Number</td>
		<td class="table_title"><span style="display:inline-block;width: 150px">Customer Name</span></td>
		<td class="table_title">Staff ID</td>
		<!-- <td class="table_title">Order Status</td> -->
		<td class="table_title">Supervisor <br/> Approval</td>
		<td class="table_title">Support <br/>Approval</td>
		<td class="table_title">Basket Type</td>
		<td class="table_title">Service Date</td>
		<td class="table_title">Application Date</td>
		<td class="table_title">Doc <br/>Uploaded</td>
		<td class="table_title">Doc <br/> Received</td>
		<!-- <td class="table_title"><span style="display:inline-block;width: 100px">Last Update</span></td> -->
		<td class="table_title">Upfront <br/> Payment</td>
		<td class="table_title">Action</td>
		
	</tr>
</thead>
<tbody>
	<c:choose>
	<c:when test="${empty orderList}">
		<tr>
			<td colspan="13">No Data Found</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${orderList}" var="orderList">
		
				<tr height="30px">
				<td>
					
					<sb-util:orderpreview orderId="${orderList.orderId}" shopCd="${orderList.shopCode}" mobcosbaseurl="<%=scheme %>"/>
					
				</td>
				<td>${orderList.msisdn}</td>
				<td>${orderList.orderSumCustName}</td>
				<td>${orderList.salesCd}</td>
				<!-- <td>
				 <c:choose>
					<c:when test='${orderList.orderStatus == "01"}'>
							REVIEWING
					</c:when>
					<c:otherwise>
						${orderList.orderStatus}
					</c:otherwise>
				 </c:choose>
				</td> -->
				<td>${orderList.superAppInd}</td>
				<td>${orderList.orderAppInd}</td>
				
				<td>${orderList.basketType}</td>
				<td>
					<fmt:formatDate pattern="yyyy/MM/dd"
							value="${orderList.srvReqDate}" />
				</td>
				<td>
					<fmt:formatDate pattern="yyyy/MM/dd"
							value="${orderList.appInDate}" />
				</td>
				<td>${orderList.docUpldInd}</td>
				<td>${orderList.supportDocCheck}</td>
				<!-- <td>
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm"
							value="${orderList.lastUpdateDate}" />
				</td> -->
				
				<td>${orderList.upfrontInd}</td>
				<td>
					
					<c:if test="${(salesUserDto.channelId == 10) and (salesUserDto.category == 'SUPERVISOR') and (orderList.superAppInd == 'N')}">
						<c:if test="${(orderList.orderStatus == 'REVIEWING')}">	
							<input type="button" value="Review" onClick="window.location='orderdetail.html?sbuid=${param.sbuid}&orderId=${orderList.orderId}&action=REVIEW&appInd=N' ">
						</c:if>	
						<c:if test="${(orderList.orderStatus == '01' and orderList.checkPoint == '610')}">
							<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
							<sb-util:sso url="${mobcosurl}review.html?orderId=${orderList.orderId}&staffId=${bomsalesuser.username}&action=REVIEW" var="supportReviewUrl"/>
							<input type="button" value="Review" onClick="window.location='${supportReviewUrl}'">
						</c:if>
					</c:if>
					<c:if test="${(salesUserDto.channelId == 11) and (salesUserDto.category == 'ORDER SUPPORT') and (orderList.orderAppInd == 'N')}">
						<c:if test="${orderList.orderStatus == 'REVIEWING'}">
							<input type="button" value="Review" onClick="window.location='orderdetail.html?sbuid=${param.sbuid}&orderId=${orderList.orderId}&action=REVIEW&appInd=N' ">
						</c:if>
						<c:if test ="${orderList.orderStatus == '01' and orderList.checkPoint == '610'}">
							<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
							<sb-util:sso url="${mobcosurl}review.html?orderId=${orderList.orderId}&staffId=${bomsalesuser.username}&action=REVIEW" var="supportReviewUrl"/>
							<input type="button" value="Review" onClick="window.location='${supportReviewUrl}'">
						</c:if>
						
					</c:if>
				</td>
				
			</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
</tbody>
</table>
</div>
</div>
</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>