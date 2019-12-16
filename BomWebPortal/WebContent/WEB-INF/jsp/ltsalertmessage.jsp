<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib"%>
<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<link href="css/imsds.css" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.colReorderWithResize.plugin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link type="text/css" href="css/dataTable/table.jui.css" rel="stylesheet" />
<script src="js/jquery.blockUI.js"></script>
<script>
	function openDialog(sbuid, ocid){
		openModalDialog("orderamend.html?sbuid="+sbuid+"&orderId="+ocid+"&dialogMode=true", "Order Id :"+ocid);
	}
	
	function formSubmit() {
		document.alertMsgForm.submit();
	}

	function ocidStatusSearch(ref) {
		$.ajax({
			url : 'mobccsordersearchlookup.html',
			data : {
				ocid : $.trim($(ref).text())
			},
			type : 'POST',
			dataType : 'JSON',
			timeout : 5000,
			success : function(msg) {
				$.each(eval(msg), function(i, item) {
					alert("BOM Status: " + item.status);
				});
			}
		});
	}
	$(document).ready(
	function() {
		if ($('#srvType').val()=="IMS") {
			$('#tbOrderEnquiryList').dataTable({
				 /* "sDom": "Rlfrtip",  */
				"bAutoWidth": false,
				"bSort" : true,
				"bLengthChange" : false,
				"sScrollX" : "100%",
				"sScrollXInner": "1600",
				"sScrollY" : 300,
				"bScrollCollapse" : true,
				"bInfinite" : true,
				"bJQueryUI" : true,
				"bPaginate" : false,
				"sPaginationType" : "two_button",
				"oLanguage" : {
					"sZeroRecords" : "NO Record Found",
					"sInfo" : "Total Records: _TOTAL_",
					"sInfoEmpty" : "NO Record Found",
					"sInfoFiltered" : "(filtered from _MAX_ total records)",
					"sSearch" : "Filter: "
				}, 
				"aaSorting": [[ 3, "desc" ]],
				"aoColumns" : [ 
				                null, 
				                null, 
				                null, 
				                {"sType" : "dd-mm-yyyy"}, 
				                null, 
				                null, 
				                null, 
				                {"sType" : "dd-mm-yyyy"}, 
				                null,
				                null,
				                null
				              ]
				});
		} else if ($('#srvType').val()=="TEL") {
			$('#tbOrderEnquiryList').dataTable({
			 /* "sDom": "Rlfrtip",  */
			"bAutoWidth": false,
			"bSort" : true,
			"bLengthChange" : false,
			"sScrollX" : "100%",
			"sScrollXInner": "1600",
			"sScrollY" : 300,
			"bScrollCollapse" : true,
			"bInfinite" : true,
			"bJQueryUI" : true,
			"bPaginate" : false,
			"sPaginationType" : "two_button",
			"oLanguage" : {
				"sZeroRecords" : "NO Record Found",
				"sInfo" : "Total Records: _TOTAL_",
				"sInfoEmpty" : "NO Record Found",
				"sInfoFiltered" : "(filtered from _MAX_ total records)",
				"sSearch" : "Filter: "
			}, 
			"aaSorting": [[ 3, "desc" ]],
			"aoColumns" : [ 
			                null, 
			                null, 
			                null, 
			                {"sType" : "dd-mm-yyyy"}, 
			                null, 
			                null, 
			                {"sType" : "dd-mm-yyyy"},
			                null,
			                null,
			                null
			              ]
			});
		}

	});
	

</script>



<style type="text/css">

<c:if test='${param.srvType=="IMS"}'>	
.dataTables_empty {
	text-align: left;
	}
</c:if>

.orderSearch {
	background-color: white;
	border: solid 2px #C0C0C0
}

.overflowDiv {
	overflow-x: auto;
	overflow-y: hidden;
	display: inline-block;
	width: 100%
}

.orderSearchResult {
	background-color: white
}

table.display {
margin: 0 auto;
clear: both;
border-collapse: collapse;
/*table-layout: fixed;*/ // ***********add this 
word-wrap:break-word; // ***********and this 

}
</style>
<input type="hidden" id="srvType" value="${param.srvType}" />
<form:form name="alertMsgForm" method="POST" commandName="alertMsg" autocomplete="off">
<div style="width: 100%; padding-left: 10px overflow-x: auto">
	<table id="tbOrderEnquiryList" style="border-width: 1; width: 1500px; padding: 10" class="display" >
		<thead>
			<tr bgcolor="#99C68E" class="table_title">
				<th style="width: 100px;" align="left" valign="middle"><b>Order ID</b></th>
				<th style="width: 100px;" align="left" valign="middle"><b>Detail</b></th>
				<th style="width: 100px;" align="left" valign="middle"><b>OC ID</b></th>
				<th style="width: 100px;" align="left" valign="middle"><b>Application Date</b></th>
				<th style="width: 200px;" align="left" valign="middle"><b>Customer Name</b></th>
				<th style="width: 150px;" align="left" valign="middle"><b>Service Number</b></th>
				<c:if test='${param.srvType == "IMS"}' >
					<th style="width: 100px" align="left" valign="middle"><b>IMS Login ID</b></th>
				</c:if>
				<th style="width: 100px;" align="left" valign="middle"><b>BOM Order Create Date</b></th>
				<th style="width: 150px;" align="left" valign="middle"><b>SB Order Status</b></th>
				<th style="width: 300px;" align="left" valign="middle"><b>Nature</b></th>
				<th style="width: 300px;" align="left" valign="middle"><b>Responsible Party</b></th>
			</tr>
		</thead>
		<tbody>
<c:forEach items="${alertMsg.orderList}" var="order">
				<tr class="contenttext"  height="30">
					<td style="width: 100px;" align="left" valign="top">${order.orderId}</td>
					<td style="width: 100px;" align="left" valign="middle">
						<!-- Amend Order for status = SignOff(D), Full WorkQueue(P),  Forced WQ(F), Extracted(E) and Created Bom Order (B) -->											
						<a class="nextbutton"
								href="#"
								title="Amend Order ${order.orderId}"
								onclick="openDialog('${sessionScope.sbuid}','${order.orderId}')" >Detail&nbsp;</a>
					</td>
					<td style="width: 100px;" align="left" valign="top">${order.ocid}</td>
					<td style="width: 100px;" align="left" valign="top">${order.applnDate}</td>
					<td style="width: 200px;" align="left" valign="top">${order.orderSumCustName}</td>
					<td style="width: 150px;" align="left" valign="top">${order.orderSumServiceNum}</td>
					<c:if test='${param.srvType == "IMS"}' >
						<td style="width: 100px" align="left" valign="top">${order.imsLoginId}</td>
					</c:if>
					<td style="width: 100px;" align="left" valign="top">${order.bomCreationDate}</td>
					<td style="width: 150px;" align="left" valign="top">
						<%@ include file="/WEB-INF/jsp/orderstatus.jsp"%>
					</td>
					<td style="width: 300px;" align="left" valign="top">${order.wqNatureDesc}</td>
					<td style="width: 300px;" align="left" valign="top">${order.wpDesc}</td>
				</tr>
</c:forEach>
		</tbody>
	</table>
	</div>
</form:form>
<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>