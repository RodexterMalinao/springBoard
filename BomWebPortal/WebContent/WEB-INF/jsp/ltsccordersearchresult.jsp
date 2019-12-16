<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
 
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link type="text/css" href="css/dataTable/table.jui.css" rel="stylesheet" />
<link href="css/custom-theme/jquery-ui-1.10.4.custom.css" rel="stylesheet" />
<link href="css/custom-theme/ltssb.css" rel="stylesheet" type="text/css" />

<script>
	function openDialog(sbuid, orderId){
		/*open new window if safari instead of dialogbox*/
		if(navigator.userAgent.indexOf("Safari") != -1
				&& navigator.userAgent.indexOf('Chrome') == -1){
			var win = window.open('orderamend.html?orderId='+orderId, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
		}else{
			openModalDialog("orderamend.html?orderId="+orderId+"&dialogMode=true", "Order Id :"+orderId);
		}
	}

	$(document).ready(
	function() {
			$('#tbOrderSearch').dataTable({
				 /* "sDom": "Rlfrtip",  */
				"bAutoWidth": true,
				"bSortable" : true,
				"bLengthChange" : false,
				"sScrollY": "240",
				"sScrollYInner": "240",
				"sScrollX": "100%",
				"sScrollXInner": "100%",
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
				"aaSorting": [[ 4, "desc" ]],
				"aoColumns" : [ 
				                null, 
				                null, 
				                null, 
				                null, 
				                null, 
				                null, 
				                {"sType" : "date-euro"}, 
				                null, 
				                null, 
				                null, 
				                null
				              ]
				});
			
			
		    	$("#tbOrderSearch").css("width","100%");
	}
	
	);
	
	function openRecallWindow(action, orderId){
		var win = window.open('ltsorder.html?sbuid=${sessionScope.sbuid}&action='+ action +'&orderId='+orderId, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
	}

</script>


<div style="margin-top:5px; min-height: 80px; width:100%">
	<table id="tbOrderSearch" style="margin: 0; padding-left:0px ;" border="1" cellspacing="0" class="contenttext" >
		<thead>
			<tr class="contenttext">
				<!-- <td class="table_title">LOB</td> -->
				<th>Order ID</th>
				<th>Staff Num</th>
				<th>Team Code</th>
				<th>OCID</th>
				<th>Customer Name</th>
				<th>Service Number</th>
				<th>Application Date</th>
				<th>Contact Email Address</th>
				<th>Order Status</th>  
				<th>Error Message</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ltsOrderSearchResult}" var="ltsResult">
				<tr>
					<!--<td>${ltsResult.lob}</td>-->
					<td>
						<a style="text-decoration: underline;" href="#${ltsResult.orderId}" onclick="openRecallWindow('ENQUIRY', '${ltsResult.orderId}');return false;"> ${ltsResult.orderId}</a>
					</td>
					<td>${ltsResult.staffNum}</td>
					<td>${ltsResult.shopCd}</td>
					<td>${ltsResult.ocId}</td>
					<td>${ltsResult.custName}</td>
					<td>${ltsResult.srvNum}</td>
					<td>${ltsResult.applicationDate}</td>
					<td>${ltsResult.contactEmail}</td>
					<td>
						<c:choose>
							<c:when test='${(ltsResult.status == "S" || ltsResult.status == "W") && not empty ltsResult.statusReaCd}'>
								<spring:message code="lts.orderStatus.${ltsResult.status}.${ltsResult.statusReaCd}" 
									text="${ltsResult.status}(${ltsResult.statusReaCd})" />
							</c:when>
							<c:otherwise>
								<spring:message code="lts.orderStatus.${ltsResult.status}" text="${ltsResult.status}" />
							</c:otherwise>
						</c:choose>
					</td>
					<td>${ltsResult.errorMsg}</td>
					<td>
					<div style="margin-top: 5px; min-height: 20px;">
						<!-- Order recall for status = Suspended (S), Approval Reject (R), Initial (I), Pending Approval (G), Pending Signoff (O), Await Cash Prepayment (W) -->
						<table border="0" cellspacing="0" class="contenttext" >
						<tr>
						<c:if test="${ltsResult.allowRecallOrder}">
							<c:if test="${ltsResult.status=='W' && (empty ltsResult.statusReaCd || ltsResult.statusReaCd == 'PREPAY') }">
								<td>
                                   <a class="nextbutton"
									href="#" onclick="openRecallWindow('RECALL_N_UPDATE_PREPAYMENT', '${ltsResult.orderId}');return false;"
									title="Recall Order ${ltsResult.orderId}">
									<div class="func_button">Recall</div></a>	
								</td>		
							</c:if>
							<c:if test="${ltsResult.status=='S' || ltsResult.status=='R' || ltsResult.status=='I' || ltsResult.status=='G' || ltsResult.status=='O'}">
								<td>
                                   <a class="nextbutton"
									href="#" onclick="openRecallWindow('RECALL', '${ltsResult.orderId}');return false;"
									title="Recall Order ${ltsResult.orderId}">
									<div class="func_button">Recall</div></a>
								</td>	
							</c:if>
						</c:if>
						<c:if test="${ltsResult.allowCancelOrder}">
							<c:if test="${ltsResult.status=='S' || ltsResult.status=='R' || ltsResult.status=='I' || ltsResult.status=='G' || ltsResult.status=='O' || ltsResult.status=='W' || ltsResult.status=='Q'}">
                                   <td>
								<a class="nextbutton"
									href="#" onclick="openRecallWindow('ENQUIRY_N_CANCEL', '${ltsResult.orderId}');return false;"
									title="Enquriy and Cancel Order ${ltsResult.orderId}">
									<div class="func_button">Cancel</div></a>	
								</td>
							</c:if>
						</c:if>
						
						<!-- Amend Order for status = SignOff(D), Full WorkQueue(P),  Forced WQ(F), Extracted(E), Created Bom Order (B) and Completed (L) -->											
						<c:if test="${ltsResult.allowAmendOrder && (ltsResult.status=='D' || ltsResult.status=='F' || ltsResult.status=='E' || ltsResult.status=='B' || ltsResult.status=='P'|| ltsResult.status=='L')}">
							<td>
							<a class="nextbutton"
								href="#"
								onclick="openDialog('${sessionScope.sbuid}','${ltsResult.orderId}');return false;"
								title="Amend Order ${ltsResult.orderId}">
								<div class="func_button">Amend</div></a>
							</td>	
						</c:if>		
						</tr>	
						</table>	
					</div>			
				</tr>					
			</c:forEach>
		</tbody>
	</table>
</div>
