<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="/WEB-INF/jsp/imsloadingpanel.jsp" %>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" 
    rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.colReorderWithResize.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.inputPagination.js"></script>
<script type="text/javascript" src="js/iepngfix_tilebg.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<!-- <link type="text/css" href="css/dataTable/table.jui.css" rel="stylesheet" /> -->
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>


<c:set var="isAllOrders" value="${bomsalesuser.channelCd == 'CSFM'}"></c:set>

<style type="text/css">
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default { 
border: 1px solid #4252A6;
background: #3446A6; 
font-weight: normal; 
color: #ffffff; 
}

.orderSearch {
	background-color: white;
	border: solid 2px #C0C0C0
}

.orderSearchButton {
	text-align: right;
	padding: 0 0.5em 0.5em 0
}

.orderSearchLabel {
	display: inline-block;
	width: 90px
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

.sorting {
 	background:#abd078 url('images/sort_both.png') no-repeat center right; 
}

.sorting_asc  {
 	background:#abd078 url('images/sort_asc.png') no-repeat center right; 
}

.sorting_desc {
 	background:#abd078 url('images/sort_desc.png') no-repeat center right; 
}

.naviPage {
	float: right;
}
</style>

<style type="text/css">
	.sorting, .sorting_asc, .sorting_desc { behavior: url('js/iepngfix.htc') }
</style>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.orderSearch { padding-left: 0 }
.orderSearchResult { padding-bottom: 1em }
</style>
<![endif]-->





<script>

	function openDialog4Ntv(orderId) {
		openModalDialog("${ntvdomain}ntvorderdetail.html?_al=new&orderId=" + orderId, "Order Id :"+ orderId);
	}

	function openDialog4Sum(orderId){
		openModalDialog("imsorderdetail.html?orderId=" + orderId , "Order Id :"+orderId);
	}

	function openDialog4SumRet(orderId){
		openModalDialog("${retentionViewUrl}=" + orderId , "Order Id :"+orderId);
	}

	function openDialog4SumTerm(orderId){
		openModalDialog("${terminationViewUrl}=" + orderId , "Order Id :"+orderId);
	}

	function openDialog(sbuid, ocid){
		openModalDialog("orderamend.html?sbuid="+sbuid+"&orderId="+ocid+"&dialogMode=true", "Order Id :"+ocid, "amendPage");
	}
	
	function calcDate() {
	    var start = $('#dateDatepickerStart').datepicker('getDate');
	    var end = $('#dateDatepickerEnd').datepicker('getDate');
	    var days = (end - start) / 1000 / 60 / 60 / 24;

	   // alert('date diff'+days);
	    return days;
	}							
																								
		function colFilter(table, index, val){
			if(val==""){
				table.fnFilter("", index);
			}else{
				table.fnFilter("^"+val+"$", index, true);
			}			 
		};		
		
		$(document).ready(function() {		
			$("#reset").val("");												
			/*						
			var bTable = $("#basketGrid").dataTable({
				"bPaginate": false,
				"bLengthChange": false,
				"bAutoWidth": true,		
				"bJQueryUI" : true,
				//"sDom": 'Rlfrtip',
				//"sDom": 'Rlrtip',
				"sDom": '<"H"lr>t<"F"ip>',
				"aaSorting": [[ 8, "desc" ]],
				"sScrollY": "340px",
				//"bScrollCollapse": true,
				"bSortClasses": false
			});
			*/
			
			
			var bTable = $("#basketGrid").dataTable({
				"bPaginate": true,	// false -> true : enable paging
				"aLengthMenu": [[10,50,100,-1], [10,50,100,"All"]],
				"bLengthChange": true,	// false->true : default: [10, 25, 50, 100]
				"bAutoWidth": true,		
				"bJQueryUI" : true,
				//"sDom": 'Rlfrtip',
				//"sDom": 'Rlrtip',
				//"sDom": '<"H"r>t<"F"i>'
				"sDom": '<"H"r>t<"F"i<"naviPage"l><"naviPage"p>>',	
				// add : p(paging+buttons), l(drop down menu for choosing number of record shown)
				"oLanguage": {"sLengthMenu": "_MENU_"},	
				// add: sLengthMenu : format of drop down menu (e.g XXX _MENU_ YYY)
				"sPaginationType": "input",
				"aaSorting": [[ 8, "desc" ]],
				"sScrollY": "340px",
				"bScrollCollapse": true,
				"bSortClasses": false
			}); // edited 20140901
			
// 			$("[class='dataTables_scrollHead ui-state-default'] *").each(function(){
// 				$(this).css("overflow","hidden");
// 			});
			$("[class='dataTables_scrollHead ui-state-default']").css("position","static");
			
			$(window).resize( function () {
				bTable.fnAdjustColumnSizing();
				//$('[class="dataTables_scrollHead ui-state-default"]').css("display","none");
				//$('[class="dataTables_scrollHead ui-state-default"]').css("display","inline-block");
				//alert($("[class=dataTables_scrollBody]").attr("class"));
			} );
			
			//bTable.fnSort([[ 7, "desc" ]]);
			

// 			var today = new Date ();
// 			var dd = today.getDate();
// 			var mm = today.getMonth()+1; //January is 0!
// 			var yyyy = today.getFullYear();
// 			if(dd<10){dd='0'+dd};
// 			if(mm<10){mm='0'+mm};
// 			$("#dateDatepickerStart").val(dd+"/"+mm+"/"+yyyy);
// 			$("#dateDatepickerEnd").val(dd+"/"+mm+"/"+yyyy);


			
			$("#LOB").change(
					function(){
								$('#ImsLoginId').hide(10);
								$('#ImsLoginId2').hide(10);
								$("#loginIdType").attr('disabled', true);
								$("#loginId").attr('disabled', true);
					}							
				);	
			
			$("#LOB2").change(
					function(){
								$('#ImsLoginId').hide(10);
								$('#ImsLoginId2').hide(10);
								$("#loginIdType").attr('disabled', true);
								$("#loginId").attr('disabled', true);
					}							
				);	
			
			$("#LOB3").change(
					function(){
								$('#ImsLoginId').show(10);
								$('#ImsLoginId2').show(10);
								$("#loginIdType").attr('disabled', false);
								$("#loginId").attr('disabled', false);
					}							
				);	

			if ($("#LOB3").is(':checked')) {
				$('#ImsLoginId').show(10);
				$('#ImsLoginId2').show(10);
				$("#loginIdType").attr('disabled', false);
				$("#loginId").attr('disabled', false);
			}

						$("#searchChannelSelect").change(function(){
				colFilter(bTable, 14, $(this).val());
			});
			
			$("#searchLOBSelect").change(function(){
				//colFilter(bTable, 1, $(this).val());
			});
			
			$("#searchTeamSelect").change(function(){
				colFilter(bTable, 1, $(this).val()+" ");
			});

// 			$('#dateDatepickerStart').datepicker( "option", "maxDate", new Date (2999,1,1));
// 			$('#dateDatepickerEnd').datepicker( "option", "maxDate", new Date (2999,1,1));
			
		});

		function keyUpOnImsLoginID() {
			 	document.getElementById('loginId').value = document.getElementById('loginId').value.toLowerCase();
		}

		function keyUpOnDocNum() {
			 	document.getElementById('loginId').value = document.getElementById('loginId').value.toUpperCase();
		}
		
		function submitForm() {
			if(isInputOkayBeforeSubmit()){ 
				showLoading();
				document.ccLtsImsOrderEnquiryForm.submit();
			}
		}
		function isInputOkayBeforeSubmit(){
			if(
					(document.getElementById('orderId')==null ||document.getElementById('orderId').value=='')&&
					(document.getElementById('docNum')==null ||document.getElementById('docNum').value=='')&&
					(document.getElementById('serNum')==null ||document.getElementById('serNum').value=='')&&
					(document.getElementById('loginId')==null ||document.getElementById('loginId').value=='')					
					){
				alert("Please input at lease one search criteria to search record");
				return false;
			}
			return true;
		}
		function formClear() {
			$("#reset").val("Y");
			document.ccLtsImsOrderEnquiryForm.submit();
		}
		
	</script>			

							
<form:form name="ccLtsImsOrderEnquiryForm" method="POST" commandName="CcLtsImsOrderEnquiryUI">
					

							        			<form:hidden path="reset"/>
					
					<div class="orderSearch">
					<c:choose>
						<c:when  test="${isAllOrders}">
							<h2 class="table_title" style="font-size: medium; margin: 0">IMS Sales Order Enquiry</h2>
						</c:when>
						<c:otherwise>
							<h2 class="table_title" style="font-size: medium; margin: 0">Call Centre Sales Order Enquiry</h2>
						</c:otherwise>
					</c:choose>
						
						<div class="overflowDiv">
							<table width="100%" border="0"  class="contenttext display">			
							
					<c:choose>
						<c:when  test="${isAllOrders}">
<!-- 						Show nth here -->
						</c:when>
						<c:otherwise>
								<tr>
									<td style="width: 230px;"><b>LOB: </b>
										<form:radiobutton id="LOB" path="lob" label="IMS+LTS" value="IMS','LTS" />
										<form:radiobutton id="LOB2" path="lob" label="LTS" value="LTS" /> 
										<form:radiobutton id="LOB3" path="lob" label="IMS" value="IMS" /> 
									</td>
									
								<c:choose>
									<c:when test='${IsSalesManager == "Y"}'>
									<td>Team code: <form:select path="searchTeamSelect">
											<option value="">--All--</option>
											<form:options items="${teamCds}"/>
									</form:select>
									</td>
									</c:when>	
								</c:choose>
									
								</tr>	
						</c:otherwise>
					</c:choose>	

							</table>
							<table style="margin-left: 5px;">
									
									<tr>
										<td  style="width:110px;"><b>Order ID</b></td>
										<td style="width: 90px;"><form:input id="orderId" path="orderId" /></td>
										<td  style="width: 90px;"><b>ID Doc Num</b></td>
										<td style="width: 300px;">
										<form:select id="docType" path="docType" >
											<form:option value="HKID" label="HKID"/>
											<form:option value="PASS" label="PASSPORT"/>
											<form:option value="BS" label="HKBR"/>
										</form:select>
										<form:input id="docNum" path="docNum" onkeyup="keyUpOnDocNum()" cssStyle="text-transform:uppercase;"/>
									</tr>		
									<tr>
										<td  style="width: 110px;"><b>Service Number</b></td>
										<td style="width: 90px;"><form:input id="serNum" path="serNum" /></td>
										<td   id=ImsLoginId style="display: none;" style="width: 90px;"><b>Login ID</b></td>
										<td  id=ImsLoginId2 style="display: none;">
										<form:input id="loginId" path="loginId" onkeyup="keyUpOnImsLoginID()" cssStyle="text-transform:lowercase;"/>
										<form:select id="loginIdType" path="loginIdType">
											<form:option value="netvigator" label="@netvigator.com"/>
										</form:select>
										</td>
									</tr>
									
							</table>

						</div>
						<div class="orderSearchButton">
<!-- 							<input type="button" value="Search" id="searchButton" onclick="javascript:submitForm();"> -->
<!-- 							<input type="button" value="Clear" onclick="javascript:formClear();"> -->
							<a class="nextbutton" onclick="javascript:submitForm();" href="javascript:void(0)">Search</a>
							<a class="nextbutton" onclick="javascript:formClear();" href="javascript:void(0)">Clear</a>
						</div>

						<div style="clear: both"></div>
					</div>
					<input type="hidden" id="index" name="index" value="">

				</form:form>


<div class="orderSearch">
	<h2 class="table_title" style="font-size:medium; margin:0">IMS Order Enquiry</h2>
	<div class="overflowDiv orderSearchResult" style="margin-top: 5px">
		<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="basketGrid" style="padding-left:0px;">
			<thead>
				<tr class="contenttextgreen">
					<td class="table_title">Order ID</td>
					<td class="table_title">Team Code</td>
					<td class="table_title">Salesman Code.</td>
					<td class="table_title">Create by.</td>
					<td class="table_title">OCID</span></td>
					<td class="table_title" style="width: 200px;">Customer Name</td>
					<td class="table_title">Service Num</td>
					<td class="table_title">IMS Login Id</td>
					<td class="table_title">Application  Date</td>
					<td class="table_title">Order Status</span></td>
					<td class="table_title">Error Message</td>
					<td class="table_title">Recall Order</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${imsOrderList}" var="imsOrderList">
					<tr>
					<c:choose>
					<c:when test="${ imsOrderList.orderType eq 'NTV-A' || imsOrderList.orderType eq 'NTVAO' || imsOrderList.orderType eq 'NTVUS' || imsOrderList.orderType eq 'NTVRE' }">
						<td><a href="###" onclick ="openDialog4Ntv('${imsOrderList.orderId}')" title="Order Enquiry for IMS">${imsOrderList.orderId}</a><c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if></td>
					</c:when>
					<c:otherwise>
						<c:choose>
						<c:when test="${imsOrderList.isRetention=='N'&& imsOrderList.isTermination=='N'}">
							<td><a href="###" onclick="openDialog4Sum('${imsOrderList.orderId}')" title="Order Enquiry for IMS">${imsOrderList.orderId}</a><c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if></td>
						</c:when>
						</c:choose> 
						<c:choose>
						<c:when test="${ (imsOrderList.orderType eq 'P_R_T' ||imsOrderList.isRetention=='Y') && imsOrderList.isTermination=='N'}">
							<td><a href="###" onclick="openDialog4SumRet('${imsOrderList.orderId}')" title="Order Enquiry for IMS">${imsOrderList.orderId}</a><c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if></td>
						</c:when>
						</c:choose> 
						<c:choose>
						<c:when test="${imsOrderList.isRetention=='N' && imsOrderList.isTermination=='Y'}">
							<td><a href="###" onclick="openDialog4SumTerm('${imsOrderList.orderId}')" title="Order Enquiry for IMS">${imsOrderList.orderId}</a><c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if></td>
						</c:when>
						</c:choose> 
					</c:otherwise>
					</c:choose>
						<td>${imsOrderList.salesTeam}<c:if test='${empty imsOrderList.salesTeam}'>&nbsp;</c:if></td>
						<td>${imsOrderList.salesCd}<c:if test='${empty imsOrderList.salesCd}'>&nbsp;</c:if></td>
						<td>${imsOrderList.createBy}<c:if test='${empty imsOrderList.createBy}'>&nbsp;</c:if></td>
						<td>${imsOrderList.ocid}<c:if test='${empty imsOrderList.ocid}'>&nbsp;</c:if></td>
						<td>${imsOrderList.custName}<c:if test='${empty imsOrderList.custName}'>&nbsp;</c:if></td>
						<td>${imsOrderList.serviceNum}<c:if test='${empty imsOrderList.serviceNum}'>&nbsp;</c:if></td>
						<td>${imsOrderList.loginId}<c:if test='${empty imsOrderList.loginId}'>&nbsp;</c:if></td>
						<td>${imsOrderList.appDate}<c:if test='${empty imsOrderList.appDate}'>&nbsp;</c:if></td>
						<td>
							<c:choose>
								<c:when test='${empty imsOrderList.orderStatus}'>&nbsp;
								</c:when>
								<c:otherwise>
									<spring:message code="ims.orderStatus.${imsOrderList.orderStatus}" text="${imsOrderList.orderStatus}" /> 
									<c:choose>
											<c:when test="${imsOrderList.orderStatus=='10'}">
												 - (<spring:message code="ims.orderSuspendCode.${imsOrderList.reasonCD}" text="${imsOrderList.reasonCD}" />)
											</c:when> 
											<c:when test="${imsOrderList.orderStatus=='30'}">
												 - <spring:message code="ims.orderamendreason.${imsOrderList.reasonCD}" text="${imsOrderList.reasonCD}" />
											</c:when> 					
									</c:choose>
								</c:otherwise>							
							</c:choose>
						</td>
						<td>${imsOrderList.error}<c:if test='${empty imsOrderList.error}'>&nbsp;</c:if></td>
						<td height="30">
							<c:choose><c:when test="${imsOrderList.recall=='Y' && (imsOrderList.orderType ne 'NTV-A' && imsOrderList.orderType ne 'NTVAO' && imsOrderList.orderType ne 'NTVUS' && imsOrderList.orderType ne 'NTVRE') }">
														
								<c:choose><c:when test="${imsOrderList.orderStatus=='06'}">
										<a class="nextbutton"href="imsorderdetail.html?orderId=${imsOrderList.orderId}&status=${imsOrderList.orderStatus}&awaitCash=Y"
											title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>
								</c:when></c:choose>  
							
								<c:choose><c:when test="${(imsOrderList.orderStatus=='10' || imsOrderList.orderStatus=='31' || imsOrderList.orderStatus=='32')&& imsOrderList.isRetention=='N'&& imsOrderList.isTermination=='N'}">
										<a class="nextbutton"href="imsorderdetail.html?orderId=${imsOrderList.orderId}&status=${imsOrderList.orderStatus}"
											title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>
										
								</c:when></c:choose> 
								
								
								<c:choose><c:when test="${(imsOrderList.orderStatus=='10' || imsOrderList.orderStatus=='31' || imsOrderList.orderStatus=='32')&& imsOrderList.isRetention=='Y' && imsOrderList.isTermination=='N'}">
										<a class="nextbutton"href="${retentionRecallUrl}=${imsOrderList.orderId}"
											title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>
										
								</c:when></c:choose> 
								
								
								<c:choose><c:when test="${(imsOrderList.orderStatus=='10' || imsOrderList.orderStatus=='31' || imsOrderList.orderStatus=='32')&& imsOrderList.isRetention=='N' && imsOrderList.isTermination=='Y'}">
										<a class="nextbutton"href="${terminationRecallUrl}=${imsOrderList.orderId}"
											title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>
										
								</c:when></c:choose> 
								
								
								<c:choose><c:when test="${imsOrderList.orderStatus=='02' || imsOrderList.orderStatus=='04' || imsOrderList.orderStatus=='92' || imsOrderList.orderStatus=='93'}">
															<a class="nextbutton"
									href="#"
									title="Amend Order ${imsOrderList.orderId}"
									onclick="openDialog('${sessionScope.sbuid}','${imsOrderList.orderId}')" >Amend&nbsp;</a>
								</c:when></c:choose> 
								
								<c:forEach items= "${endingStatusList}" var="status">
								    <c:if test="${status eq imsOrderList.orderStatus }">
										<a class="nextbutton"	href="#"	title="View Amend History ${imsOrderList.orderId}"
											onclick="openDialog('${sessionScope.sbuid}','${imsOrderList.orderId}')" >History&nbsp;</a>
								    </c:if>
								</c:forEach>
							</c:when>
							
							<c:when test="${imsOrderList.recall=='Y' && (imsOrderList.orderType eq 'NTV-A' || imsOrderList.orderType eq 'NTVAO' || imsOrderList.orderType eq 'NTVUS' || imsOrderList.orderType eq 'NTVRE') }">
								<c:choose>
								<c:when test="${imsOrderList.orderStatus=='06'}">
								<a class="nextbutton"href="${ntvdomain}ntvorderdetail.html?_al=new&orderId=${imsOrderList.orderId}&status=${imsOrderList.orderStatus}&awaitCash=Y"
											title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>								
								</c:when>
								<c:when test="${imsOrderList.orderStatus=='08'}">
								<a class="nextbutton"href="${ntvdomain}ntvorderdetail.html?_al=new&orderId=${imsOrderList.orderId}&status=${imsOrderList.orderStatus}&awaitCash=Y"
											title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>								
								</c:when>
								<c:when test="${imsOrderList.orderStatus=='11' or imsOrderList.orderStatus=='15'}">
								<a class="nextbutton"href="${ntvdomain}ntvorderdetail.html?_al=new&orderId=${imsOrderList.orderId}&status=${imsOrderList.orderStatus}&awaitSignOff=Y"
											title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>								
								</c:when>
								<c:when test="${(imsOrderList.orderStatus=='10' || imsOrderList.orderStatus=='31' || imsOrderList.orderStatus=='32')}">
								<a class="nextbutton"href="${ntvdomain}ntvorderdetail.html?_al=new&orderId=${imsOrderList.orderId}&status=${imsOrderList.orderStatus}"
											title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>								
								</c:when><c:when test="${imsOrderList.orderStatus=='02' || imsOrderList.orderStatus=='04' || imsOrderList.orderStatus=='92' || imsOrderList.orderStatus=='93'}">
								<a class="nextbutton"href="#"title="Amend Order ${imsOrderList.orderId}" onclick="openDialog('${sessionScope.sbuid}','${imsOrderList.orderId}')" >Amend&nbsp;</a>
								</c:when>							
								</c:choose>							
								<c:forEach items= "${endingStatusList}" var="status">
								    <c:if test="${status eq imsOrderList.orderStatus }">
										<a class="nextbutton"	href="#"	title="View Amend History ${imsOrderList.orderId}"
											onclick="openDialog('${sessionScope.sbuid}','${imsOrderList.orderId}')" >History&nbsp;</a>
								    </c:if>
								</c:forEach>												
							</c:when>							
							<c:otherwise>							
								<c:choose><c:when test="${imsOrderList.recall=='Y' }">	
									<c:choose><c:when test="${(imsOrderList.orderStatus=='10' || imsOrderList.orderStatus=='31' || imsOrderList.orderStatus=='32')&& imsOrderList.isRetention=='N'&& imsOrderList.isTermination=='N'}">
											<a class="nextbutton"href="imsorderdetail.html?orderId=${imsOrderList.orderId}&status=${imsOrderList.orderStatus}"
												title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>
											
									</c:when></c:choose> 
									
									
									<c:choose><c:when test="${(imsOrderList.orderStatus=='10' || imsOrderList.orderStatus=='31' || imsOrderList.orderStatus=='32')&& imsOrderList.isRetention=='Y' && imsOrderList.isTermination=='N'}">
											<a class="nextbutton"href="${retentionRecallUrl}=${imsOrderList.orderId}"
												title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>
											
									</c:when></c:choose> 
									
									<c:choose><c:when test="${(imsOrderList.orderStatus=='10' || imsOrderList.orderStatus=='31' || imsOrderList.orderStatus=='32')&& imsOrderList.isRetention=='N' && imsOrderList.isTermination=='Y'}">
											<a class="nextbutton"href="${terminationRecallUrl}=${imsOrderList.orderId}"
												title="Recall Order ${imsOrderList.orderId}">Recall&nbsp;</a>
											
									</c:when></c:choose> 
									
									
									<c:choose><c:when test="${imsOrderList.orderStatus=='02' || imsOrderList.orderStatus=='04' || imsOrderList.orderStatus=='92' || imsOrderList.orderStatus=='93'}">
											<a class="nextbutton"	href="#"	title="Amend Order ${imsOrderList.orderId}"
												onclick="openDialog('${sessionScope.sbuid}','${imsOrderList.orderId}')" >Amend&nbsp;</a>
									</c:when></c:choose> 
									
									<c:forEach items= "${endingStatusList}" var="status">
									    <c:if test="${status eq imsOrderList.orderStatus }">
											<a class="nextbutton"	href="#"	title="View Amend History ${imsOrderList.orderId}"
												onclick="openDialog('${sessionScope.sbuid}','${imsOrderList.orderId}')" >History&nbsp;</a>
									    </c:if>
								</c:forEach>								
							</c:when></c:choose>
							</c:otherwise>
							</c:choose>
							&nbsp; 
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>		
	</div>
</div>
