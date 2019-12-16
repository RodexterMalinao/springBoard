<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" 
    rel="stylesheet" />
<link href="css/imsds.css" rel="stylesheet" type="text/css">
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.colReorderWithResize.plugin.js"></script>
<script type="text/javascript" src="js/iepngfix_tilebg.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialogAndy.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script> 
<!-- <link type="text/css" href="css/dataTable/table.jui.css" rel="stylesheet" /> -->
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>



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

#basketGrid tbody tr {  
    min-height: 35px; /* or whatever height you need to make them all consistent */
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

	function openDialog4Sum(orderId, lob, orderType){
		if(lob == 'IMS'){
			//openModalDialog("imsdsqcprocessdetail.html?orderId=" + orderId , "Order Id :"+orderId); 
			openModalDialog("imsdsqcprocessdetail.html?orderId=" + orderId + "&orderType=" + orderType, "Order Id :"+orderId); 
		}else if(lob == 'LTS'){
			openModalDialog("ltsdsqcprocessdetail.html?orderId=" + orderId , "Order Id :"+orderId); 
		}
	}
	
	function openDialog(sbuid, ocid){
		openModalDialog("orderamend.html?sbuid="+sbuid+"&orderId="+ocid+"&dialogMode=true", "Order Id :"+ocid);
	}
	
	//added by Andy 16092104
	function openDialogForQCAssign(orderId){
		openModalDialog("qcimsassign.html?orderId=" + orderId , "Order Id :"+orderId);
	}
	
	//added by Andy 16092104
	function validate(index) {
		/*
		alert(index);
        if (document.getElementById('qcItem'+index).checked) {
            alert("checked");
        } else {
            alert("You didn't check it! Let me check it for you.");
        }*/
    }
	
	function QCAdminOpenDialog(){
		openModalDialog("qcimsadmin.html");
	}
	
	function QCRemarksOpenDialog(orderId){
		openModalDialog("qcimsremarks.html?orderId="+ orderId);
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
	
	function displaySelectList(){
		if($("[name=lob]:checked").val() == 'LTS'){
			$("#orderStatus").hide().attr('disabled', true);
			$("#ltsOrderStatus").show().attr('disabled', false);
			$("#qcStatus").hide().attr('disabled', true);
			$("#ltsQcStatus").show().attr('disabled', false);
			$("#qcOrderType")[0].selectedIndex = 0;
			$("#qcOrderType").attr('disabled', true);
			
			
		}else{
			$("#orderStatus").show().attr('disabled', false);
			$("#ltsOrderStatus").hide().attr('disabled', true);
			$("#qcStatus").show().attr('disabled', false);
			$("#ltsQcStatus").hide().attr('disabled', true);
			$("#qcOrderType").attr('disabled', false);
			
		}
	}
		
		$(document).ready(function() {		
			
			displaySelectList();
			
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
				"bPaginate": false,
				"bLengthChange": false, 
				"bAutoWidth": true, 		
				"bJQueryUI" : true,
				//"sDom": 'Rlfrtip',
				//"sDom": 'Rlrtip', 
				"sDom": '<"H"r>t<"F"i>',
				//"aaSorting": [[ 5, "desc" ]],
				"aaSorting": [],
				"sScrollY": "340px",
				"bScrollCollapse": true,
				"bSortClasses": false
			});
			
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

			$("#startDate").change(
					function(){
						$('#endDate').datepicker( "option", "minDate", $(this).datepicker("getDate"));	
					}							
				);	
			
			$("[name=lob]").change(
				function(){
					displaySelectList();
				}		
			);
			
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

			 $('#dateDatepickerStart').datepicker({
					changeMonth : true,
					changeYear : true,
					showButtonPanel : true,
					dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
					maxDate: "+0d",yearRange: '2013:+1'
					, onSelect: function(dateText, inst) {
						var futureDate = $(this).datepicker("getDate");
						var toDate = new Date ();
// 						alert(toDate);
						futureDate.setDate(futureDate.getDate() + 30);
						$('#dateDatepickerEnd').attr("disabled", false);
						$('#dateDatepickerEnd').datepicker( "option", "minDate", $(this).datepicker("getDate"));
						if (futureDate < toDate ){
						$('#dateDatepickerEnd').datepicker( "option", "maxDate", futureDate);
						$('#dateDatepickerEnd').datepicker( "setDate", $(this).datepicker("getDate"));
						}else{
							$('#dateDatepickerEnd').datepicker( "option", "maxDate", toDate);
							$('#dateDatepickerEnd').datepicker( "setDate", $(this).datepicker("getDate"));
						}
						if ($("#serviceRequestDate").is(':checked')) {
							$('#dateDatepickerEnd').datepicker( "option", "maxDate", futureDate);
						}
						
					}
				});
			 
				$('#dateDatepickerEnd').datepicker({
					changeMonth : true,
					changeYear : true,
					showButtonPanel : true,
					dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
					maxDate: "+0d",yearRange: '2013:+1'
				});


				$("#applicationDate").change(
					function(){
						if ($("#applicationDate").is(':checked')) {
							$('#dateDatepickerStart').datepicker( "option", "maxDate", new Date ());
							$('#dateDatepickerEnd').datepicker( "option", "maxDate", new Date ());
							$("#dateDatepickerStart").val("");
							$("#dateDatepickerEnd").val("");
						}
					}
				);

				$("#serviceRequestDate").change(
					function(){
						if ($("#serviceRequestDate").is(':checked')) {
						$('#dateDatepickerStart').datepicker( "option", "maxDate", new Date (2999,1,1));
						$('#dateDatepickerEnd').datepicker( "option", "maxDate", new Date (2999,1,1));
						$("#dateDatepickerStart").val("");
						$("#dateDatepickerEnd").val("");
						}
					}
				);
				if ($("#serviceRequestDate").is(':checked')) {
					$('#dateDatepickerStart').datepicker( "option", "maxDate", new Date (2999,1,1));
					$('#dateDatepickerEnd').datepicker( "option", "maxDate", new Date (2999,1,1));
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
				document.ccLtsImsOrderEnquiryForm.submit();
			}
		}
		function isInputOkayBeforeSubmit(){
			if(((document.getElementById('dateDatepickerStart')==null ||document.getElementById('dateDatepickerStart').value=='')||
					(document.getElementById('dateDatepickerEnd')==null ||document.getElementById('dateDatepickerEnd').value==''))&&
					(document.getElementById('orderId')==null ||document.getElementById('orderId').value=='')&&
					(document.getElementById('docNum')==null ||document.getElementById('docNum').value=='')&&
					(document.getElementById('serNum')==null ||document.getElementById('serNum').value=='')&&
					(document.getElementById('createStaff')==null ||document.getElementById('createStaff').value=='')&&
					(document.getElementById('loginId')==null ||document.getElementById('loginId').value=='')&&
					(document.getElementById('teamSearch')==null ||document.getElementById('teamSearch').value=='')&&
					(document.getElementById('salesNum')==null ||document.getElementById('salesNum').value=='')&&
					(document.getElementById('orderStatus')==null ||document.getElementById('orderStatus').value=='')){
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

							
<form:form name="ccLtsImsOrderEnquiryForm" method="POST" commandName="ImsDsQCProcessUI">

							        			<form:hidden path="reset"/>
					
					<div class="orderSearch">
						<h2 class="table_title" style="font-size: medium; margin: 0">Direct Sales QC Process</h2>
						<div class="overflowDiv">
							<table width="100%" border="0"  class="contenttext display">			
							
								<tr>
									<td style="width: 230px;"><b>LOB: </b>
									<!-- 	<form:radiobutton id="LOB" path="lob" label="IMS+LTS" value="IMS','LTS" />-->
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
									<tr>
										<td style="width:300px;">
											<form:radiobutton id="applicationDate"  path="dateType" label="Application Date " value="A" />
											<form:radiobutton id="serviceRequestDate" path="dateType" label="Service Request Date" value="S" /> 
											<form:radiobutton id="signOffDate" path="dateType" label="Sign-Off Date" value="F" />
										</td>
									</tr>
							</table>
							<table style="margin-left: 5px;">	
									<tr>
										<td  style="width:110px;"><b>Start Date:</b></td>
										<td style="width: 90px;">
											<form:input id="dateDatepickerStart" path="startDate" readonly="true"/>
										</td>
										<td  style="width:70px;"><b>End Date:</b></td>
										<td style="width: 90px;">
											<form:input id="dateDatepickerEnd" path="endDate" readonly="true"/>
										</td>
									</tr>	
									
									<tr>
										<td  style="width: 90px;"><b>Order Status</b></td>
										<td style="width: 300px;">
										<c:choose>
											<c:when test="${not empty orderStatusList}">
											<form:select id="orderStatus" path="orderStatus" >
												<form:option value="" label="Select..."/>
												<form:options items="${orderStatusList}" />
											</form:select>
											</c:when>					
											<c:otherwise>
												<select id="orderStatus"  style="width:200px;" disabled> 
													<option selected="selected" value = "">Select...</option>
												</select>
											</c:otherwise>			
										</c:choose>
										<c:choose>
											<c:when test="${not empty ltsOrderStatusList}">
											<form:select id="ltsOrderStatus" path="orderStatus" cssStyle="display:none" disabled="true">
												<form:option value="" label="Select..."/>
												<form:options items="${ltsOrderStatusList}" itemValue="itemKey" itemLabel="itemValue"/>
											</form:select>
											</c:when>					
											<c:otherwise>
												<select id="ltsOrderStatus"  style="width:200px;" disabled> 
													<option selected="selected" value = "">Select...</option>
												</select>
											</c:otherwise>			
										</c:choose>
										
										</td>
										<td  style="width: 90px;"><b>QC Status</b></td>
										<td style="width: 300px;">
											<c:choose>
												<c:when test="${not empty qcStatusList}">
												<form:select id="qcStatus" path="qcStatus" >
													<form:option value="" label="Select..."/>
													<form:options items="${qcStatusList}" />
												</form:select>
												</c:when>					
												<c:otherwise>
													<select id="qcStatus"  style="width:200px;" disabled> 
														<option selected="selected" value = "">Select...</option>
													</select>
												</c:otherwise>			
											</c:choose>
											<c:choose>
												<c:when test="${not empty ltsQcStatusList}">
													<form:select id="ltsQcStatus" path="qcStatus" cssStyle="display:none" disabled="true">
														<form:option value="" label="Select..."/>
														<form:options items="${ltsQcStatusList}" itemValue="itemKey" itemLabel="itemValue"/>
													</form:select>
												</c:when>					
												<c:otherwise>
													<select id="ltsQcStatus"  style="width:200px;" disabled> 
														<option selected="selected" value = "">Select...</option>
													</select>
												</c:otherwise>			
											</c:choose>
										
										</td>
									</tr>	
									<tr>
										<td  style="width:110px;"><b>Order ID</b></td>
										<td  style="width: 90px;"><form:input id="orderId" path="orderId" /></td>
										<td  style="width:110px;"><b>Assignee </b></td>
										<td  style="width: 90px;"><form:input id="Assignee" path="assignee"/></td>
									</tr>
									<tr>
										<td style="width: 90px;"><b>QC Type</b></td>
										<td style="width: 300px;">
											<c:choose>
												<c:when test="${not empty qcOrderTypeSelectionList}">
												<form:select id="qcOrderType" path="qcOrderType" >
													<form:option value="" label="Select..."/>
													<form:options items="${qcOrderTypeSelectionList}" />
												</form:select>
												</c:when>					
												<c:otherwise>
													<select id="qcOrderType"  style="width:200px;" disabled> 
														<option selected="selected" value = "">Select...</option>
													</select>
												</c:otherwise>			
											</c:choose>
											</td>
										</tr>
							</table>

						</div>
					
						
						<div class="orderSearchButton"> 
<!-- 						    <input type="button" value="Search" id="searchButton" onclick="javascript:submitForm();"> -->
<!-- 							<input type="button" value="Clear" onclick="javascript:formClear();">							 -->
							<a class="nextbutton" href="javascript:void(0)" id="searchButton" onclick="javascript:submitForm();" >Search</a>
							<a class="nextbutton" href="javascript:void(0)" onclick="javascript:formClear();" >Clear</a>
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
<!--				    <td class="table_title">QC Item</td>--> 
				    <td class="table_title">Order ID</span></td>
				    	<c:choose>
							<c:when test='${IsSalesManager == "Y"}'>
					    		<td class="table_title">Sales Team</td>
					    	</c:when>
				    	</c:choose>	
				    <td class="table_title">Sys-F</td>
<!--					<td class="table_title">Create by.</td>-->
					<td class="table_title">Customer Name</td>
					<td class="table_title">Application  Date</td>
					<td class="table_title">Service Request Date</td>
					<td class="table_title">Sign-Off Date</td>
					<td class="table_title">Assign Date</td>
					<td class="table_title">Created By</td>
					<td class="table_title">Order Status</span></td>
					<td class="table_title">Remarks</td>
					<td class="table_title">QC Status</td>
				</tr>
			</thead>
			<tbody>
			    <c:forEach items="${imsOrderList}" var="imsOrderList" varStatus="i">
					<tr> 
<!--						<td><input id=${imsOrderList.orderId}  type="checkbox" name="qcItem" value="qcItem" onclick="validate('${imsOrderList.orderId}')" ><br></td>-->
						
						<td>
							<a href="###" onclick="openDialog4Sum('${imsOrderList.orderId}', '${ImsDsQCProcessUI.lob}',  '${imsOrderList.orderType}')" title="Order Enquiry for ${ImsDsQCProcessUI.lob}">${imsOrderList.orderId}</a>
							<c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if>
						</td>
						<c:choose>
							<c:when test='${IsSalesManager == "Y"}'>
								<td>${imsOrderList.salesTeam}</td>
							</c:when>
						</c:choose>							
						
						<td>${imsOrderList.sysF}</td>
						
<%-- 						<c:choose> --%>
<%-- 							<c:when test = "${i.index eq 4}"> --%>
<!-- 								<td>Door Knock</td>&nbsp; -->
<%-- 							</c:when> --%>
<%-- 							<c:when test = "${i.index%5 eq 0}"> --%>
<!-- 								<td>ALL eye</td>&nbsp; -->
<%-- 							</c:when> --%>
<%-- 							<c:otherwise> --%>
<!-- 								<td>1D1l</td>&nbsp; -->
<%-- 							</c:otherwise> --%>
<%-- 						</c:choose> --%>
						
						
<!--						<td>${imsOrderList.createBy}<c:if test='${empty imsOrderList.createBy}'>&nbsp;</c:if></td>       -->
					 				 
						<td>${imsOrderList.custName}<c:if test='${empty imsOrderList.custName}'>&nbsp;</c:if></td>      
						<td>${imsOrderList.appDate}<c:if test='${empty imsOrderList.appDate}'>&nbsp;</c:if></td>
						<td>${imsOrderList.serviceReqDate}<c:if test='${empty imsOrderList.serviceReqDate}'>&nbsp;</c:if></td>
						<td>${imsOrderList.signoffDate}<c:if test='${empty imsOrderList.signoffDate}'>&nbsp;</c:if></td>
						<td>${imsOrderList.appDate}<c:if test='${empty imsOrderList.appDate}'>&nbsp;</c:if></td>
						
						<td> ${imsOrderList.createBy} </td>
						
						<td>
							<c:if test="${ImsDsQCProcessUI.lob == 'LTS'}">
								<c:if test="${not empty imsOrderList.orderStatus}">
									<spring:message code="lts.orderStatus.${imsOrderList.orderStatus}" text="${imsOrderList.orderStatus}"/>
								</c:if> 
							</c:if>
							<c:if test="${ImsDsQCProcessUI.lob == 'IMS'}">
								<c:choose>
									<c:when test='${empty imsOrderList.orderStatus}'>&nbsp;
									</c:when>
									<c:otherwise>
										<spring:message code="ims.orderStatus.${imsOrderList.orderStatus}" text="${imsOrderList.orderStatus}" /> 
										<c:choose>
												<c:when test="${imsOrderList.orderStatus=='30'}">
													 - <spring:message code="ims.orderamendreason.${imsOrderList.reasonCD}" text="${imsOrderList.reasonCD}" />
												</c:when> 					
										</c:choose>
									</c:otherwise>							
								</c:choose>
							</c:if>
						</td>
						
						<td>
	 						<c:if test="${not empty imsOrderList.qcRemarks}">
	 						    ${imsOrderList.qcRemarks}
<%-- 	 							<input type="button" value="Details" id="rmDtlButton${i.index}" onclick="javascript:QCRemarksOpenDialog('${imsOrderList.orderId}');"> --%>
									<a class="nextbutton" href="javascript:void(0)" id="rmDtlButton${i.index}" 
									style="    padding-top: 1px;    padding-bottom: 1px;"
									onclick="javascript:QCRemarksOpenDialog('${imsOrderList.orderId}');" >Details</a>
	 						</c:if> 
						</td>
						
						<td>
							<c:if test="${ImsDsQCProcessUI.lob == 'LTS'}">
								<c:if test="${not empty imsOrderList.qcStatus}">
									<spring:message code="lts.qc.status.${imsOrderList.qcStatus}" text="${imsOrderList.qcStatus}"/>
								</c:if> 
							</c:if>
							<c:if test="${ImsDsQCProcessUI.lob == 'IMS'}">
								${imsOrderList.qcStatus}
							</c:if>
						</td>
						
<%-- 						<c:choose> --%>
<%-- 							<c:when test = "${i.index%7 eq 0}">  	  --%>
<!-- 								<td>RNA</td>&nbsp; -->
<%-- 							</c:when> --%>
<%-- 							<c:otherwise>  --%>
<!-- 								<td></td>&nbsp; -->
<%-- 							</c:otherwise> --%>
<%-- 						</c:choose> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>		
	</div>
</div>
