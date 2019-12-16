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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
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
		//openModalDialog("imsorderdetail.html?orderId=" + orderId , "Order Id :"+orderId);
		if(lob == 'IMS'){
			openModalDialog("imsdsqcprocessdetail.html?orderId=" + orderId + "&orderType=" + orderType, "Order Id :"+orderId); 
		}else if(lob == 'LTS'){
			openModalDialog("ltsdsqcprocessdetail.html?orderId=" + orderId , "Order Id :"+orderId); 
		}
	}
	
	function openDialog(sbuid, ocid){
		openModalDialog("orderamend.html?sbuid="+sbuid+"&orderId="+ocid+"&dialogMode=true", "Order Id :"+ocid);
	}
	
	function QCRemarksOpenDialog(orderid){
		openModalDialog("qcimsremarks.html?orderId="+orderid);
	}
	
	function QCamendRemarksOpenDialog(orderid){
		//openModalDialog("qcimsremarks.html");
		//openModalDialog("qcimsamendremarks.html");
		openModalDialog("qcimsamendremarks.html?orderId=" +orderid);
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
			
			displaySelectList();
			
			//$("#reset").val("");												
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
				"aaSorting": [[ 8, "desc" ]],
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
			
// 			$("#LOB").change(
// 					function(){
// 								$('#ImsLoginId').hide(10);
// 								$('#ImsLoginId2').hide(10);
// 								$("#loginIdType").attr('disabled', true);
// 								$("#loginId").attr('disabled', true);
// 					}							
// 				);	
			
// 			$("#LOB2").change(
// 					function(){
// 								$('#ImsLoginId').hide(10);
// 								$('#ImsLoginId2').hide(10);
// 								$("#loginIdType").attr('disabled', true);
// 								$("#loginId").attr('disabled', true);
// 					}							
// 				);	
			
// 			$("#LOB3").change(
// 					function(){
// 								$('#ImsLoginId').show(10);
// 								$('#ImsLoginId2').show(10);
// 								$("#loginIdType").attr('disabled', false);
// 								$("#loginId").attr('disabled', false);
// 					}							
// 				);	

// 			if ($("#LOB3").is(':checked')) {
// 				$('#ImsLoginId').show(10);
// 				$('#ImsLoginId2').show(10);
// 				$("#loginIdType").attr('disabled', false);
// 				$("#loginId").attr('disabled', false);
// 			}

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
				$("#signOffDate").change(
						function(){
							if ($("#signOffDate").is(':checked')) {
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
			
			
// 			$("#searchChannelSelect").change(function(){
// 				colFilter(bTable, 14, $(this).val());
// 			});
			
// 			$("#searchLOBSelect").change(function(){
// 				colFilter(bTable, 1, $(this).val());
// 			});
			
// 			$("#searchTeamSelect").change(function(){
// 				colFilter(bTable, 1, $(this).val()+" ");
// 			});

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
					(document.getElementById('firstName')==null ||document.getElementById('firstName').value=='')&&
					(document.getElementById('lastName')==null ||document.getElementById('lastName').value=='')&&
					(document.getElementById('loginId')==null ||document.getElementById('loginId').value=='')&&
					(document.getElementById('orderStatus')==null ||document.getElementById('orderStatus').value==''||
					 document.getElementById('ltsOrderStatus')==null ||document.getElementById('ltsOrderStatus').value=='')&&
					(document.getElementById('qcStatus')==null ||document.getElementById('qcStatus').value==''||
					 document.getElementById('ltsQcStatus')==null ||document.getElementById('ltsQcStatus').value=='')&&
					(document.getElementById('createStaff')==null ||document.getElementById('createStaff').value=='')&&
					(document.getElementById('teamSearch')==null ||document.getElementById('teamSearch').value=='')&&
					(document.getElementById('salesNum')==null ||document.getElementById('salesNum').value=='')&&
					(document.getElementById('cashMethod').checked==false&&document.getElementById('creditCardMethod').checked==false)&&
					(document.getElementById('yes3rd').checked==false&&document.getElementById('no3rd').checked==false)&&
					(document.getElementById('housingType')==null ||document.getElementById('housingType').value=='')&&
					(document.getElementById('staffType')==null ||document.getElementById('staffType').value=='')&&
					(document.getElementById('amendment')==null ||document.getElementById('amendment').value=='')&&
					(document.getElementById('assignee')==null ||document.getElementById('assignee').value=='')
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
		
		function openDialogForQCAssign(orderId){
			openModalDialog("qcimsassign.html?orderId=" + orderId+"&action=assign" , "Order Id :"+orderId);
		}
		
		function exportExcel(){
			<c:if test='${empty imsOrderList}'>
				alert ("please search a required list first.");
				return;
			</c:if>
			if (!confirm("Excel will be generated. " + ". Confirm to continue?")) {
				e.preventDefault();
			}
			window.location.href = 'genimsdsqccomxls.html';
		} 
		
		function displaySelectList(){
			if($("[name=lob]:checked").val() == 'LTS'){
				//$("#orderStatus").hide().attr('disabled', true);
				//$("#ltsOrderStatus").show().attr('disabled', false);
				//$("#qcStatus").hide().attr('disabled', true);
				//$("#ltsQcStatus").show().attr('disabled', false);
				$(".ltsList").show().attr('disabled', false);
				$(".imsList").hide().attr('disabled', true);
				$("#loginId").attr('disabled',true);
				$("#loginIdType").attr('disabled',true);
				
			}else{
				//$("#orderStatus").show().attr('disabled', false);
				//$("#ltsOrderStatus").hide().attr('disabled', true);
				//$("#qcStatus").show().attr('disabled', false);
				//$("#ltsQcStatus").hide().attr('disabled', true);
				$("#loginId").attr('disabled',false);
				$("#loginIdType").attr('disabled',false);
				$(".ltsList").hide().attr('disabled', true);
				$(".imsList").show().attr('disabled', false);
			}
		}

	</script>			

							
<form:form name="ccLtsImsOrderEnquiryForm" method="POST" commandName="imsQcComOrderSearchUI">
					

							        			<form:hidden path="reset"/>
					
					<div class="orderSearch">
						<h2 class="table_title" style="font-size: medium; margin: 0">Direct Sales QA Order Enquiry</h2>
						
						<div class="overflowDiv">
							<table width="100%" border="0"  class="contenttext display">	
							
						 			<tr>
										<td style="width: 300px;"><b>LOB: </b>
											<form:radiobutton id="LOB2" path="lob" label="LTS" value="LTS" />  
											<form:radiobutton id="LOB3" path="lob" label="IMS" value="IMS" /> 
										</td>
									
								 <!--<c:choose>
										<c:when test='${IsSalesManager == "Y"}'>
										<td>Team code: <form:select path="searchTeamSelect">
												<option value="">--All--</option>
												<form:options items="${teamCds}"/>
										</form:select>
										</td>
										</c:when>	
									</c:choose> -->
									
									</tr>				
	
									<tr>
										<td style="width:300px;">
											<form:radiobutton id="applicationDate"  path="dateType" label="Application Date " value="A" />
											<form:radiobutton id="serviceRequestDate" path="dateType" label="Service Request Date" value="S" /> 
											<form:radiobutton id="signOffDate" path="dateType" label="Submit Date" value="F" />
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
									
									<!-- added by ANdy -->
									<tr>
										<td  style="width: 110px;"><b>First Name</b></td>
										<td style="width: 90px;"><form:input id="firstName" path="firstName" /></td>
										<td  style="width: 110px;"><b>Last Name</b></td>
										<td style="width: 90px;"><form:input id="lastName" path="lastName" /></td>
									</tr>
											
									<tr>
										<td  style="width: 90px;"><b>Login ID</b></td>
										<td  style="width: 300px;">
										<form:input id="loginId" path="loginId" onkeyup="keyUpOnImsLoginID()" cssStyle="text-transform:lowercase;"/>
										<form:select id="loginIdType" path="loginIdType">
											<form:option value="netvigator" label="@netvigator.com"/>
										</form:select>
										</td>
									</tr>
									<tr>
										<td  style="width: 90px;"><b>Order Status</b></td>
										<td style="width: 300px;">
										<c:choose>
											<c:when test="${not empty orderStatusList}">
											<form:select cssClass="imsList" id="orderStatus" path="orderStatus" >
												<form:option value="" label="Select..."/>
												<form:options items="${orderStatusList}" />
											</form:select>
											</c:when>					
											<c:otherwise>
												<select class="imsList" id="orderStatus"  style="width:200px;" disabled> 
													<option selected="selected" value = "">Select...</option>
												</select>
											</c:otherwise>			
										</c:choose>
										<c:choose>
											<c:when test="${not empty ltsOrderStatusList}">
											<form:select cssClass="ltsList" id="ltsOrderStatus" path="orderStatus" cssStyle="display:none" disabled="true">
												<form:option value="" label="Select..."/>
												<form:options items="${ltsOrderStatusList}" itemValue="itemKey" itemLabel="itemValue"/>
											</form:select>
											</c:when>					
											<c:otherwise>
												<select class="ltsList" id="ltsOrderStatus"  style="width:200px;" disabled> 
													<option selected="selected" value = "">Select...</option>
												</select>
											</c:otherwise>			
										</c:choose>
										
										</td>
										<td  style="width: 90px;"><b>QC Status</b></td>
										<td style="width: 300px;">
										<c:choose>
											<c:when test="${not empty QCStatusList}">
											<form:select cssClass="imsList" id="qcStatus" path="qcStatus" >
												<form:option value="" label="Select..."/>
												<form:options items="${QCStatusList}" />
											</form:select>
											</c:when>					
											<c:otherwise>
												<select class="imsList" id="qcStatus"  style="width:200px;" disabled> 
													<option selected="selected" value = "">Select...</option>
												</select>
											</c:otherwise>			
										</c:choose>
										<c:choose>
												<c:when test="${not empty ltsQcStatusList}">
													<form:select cssClass="ltsList" id="ltsQcStatus" path="qcStatus" cssStyle="display:none" disabled="true">
														<form:option value="" label="Select..."/>
														<form:options items="${ltsQcStatusList}" itemValue="itemKey" itemLabel="itemValue"/>
													</form:select>
												</c:when>					
												<c:otherwise>
													<select class="ltsList" id="ltsQcStatus"  style="width:200px;" disabled> 
														<option selected="selected" value = "">Select...</option>
													</select>
												</c:otherwise>			
											</c:choose>
										</td>
									</tr>	
									
									<tr>
										
										<td  style="width:90px;"><b>Create by (Staff no.):</b></td>
										<td style="width: 90px;">
											<form:input id="createStaff" path="createStaff" />
										</td>
									</tr>																
									<tr>
																			
										<td  style="width: 90px;"><b>Team Code:</b></td>
										<td style="width: 110px;">
										
									
										<c:choose>
											<c:when test="${not empty teamCds}">
											<form:select id="teamSearch" path="teamSearch" >
												<form:option value="" label=""/>
												<form:options items="${teamCds}" />
											</form:select>
											</c:when>					
											<c:otherwise>
												<select id="teamSearch"  style="width:200px;" > 
													<option selected="selected" value = ""></option>
												</select>
											</c:otherwise>			
										</c:choose>
										</td>
										<td  style="width:70px;"><b>Salesman Code:</b></td>
										<td style="width: 90px;">
										<form:input id="salesNum" path="salesNum" />	</td>									
									</tr>									
									<tr >
										<td style="width:300px;"><b>Payment Method</b></td>
										<td>
											<form:radiobutton id="cashMethod"  path="paymentMethod" label="Cash" value="M" />
											<form:radiobutton id="creditCardMethod" path="paymentMethod" label="Credit Card" value="C" /> 
										</td>
										
										<td style="width:300px;"><b>3rd Party Credit Card</b></td>
										<td>
											<form:radiobutton id="yes3rd" path="is3rdParty" label="Yes" value="Y" />
											<form:radiobutton id="no3rd"  path="is3rdParty" label="No" value="N" /> 
								    	</td>
								    	
								    </tr>									
									<tr>
										<td  style="width: 90px;"><b>Housing Type</b></td>
											<td style="width: 300px;">
											<c:choose>
												<c:when test="${not empty HousingTypeList}">
												<form:select cssClass="imsList" id="housingType" path="housingType" >
													<form:option value="" label="Select..."/>
													<form:options items="${HousingTypeList}" />
												</form:select>
												</c:when>					
												<c:otherwise>
													<select class="imsList" id="housingType"  style="width:200px;" disabled> 
														<option selected="selected" value = "">Select...</option>
													</select>
												</c:otherwise>			
											</c:choose>
											
											<form:select cssClass="ltsList" id="ltsHousingType" path="housingType" >
													<form:option value="" label="Select..."/>
													<form:option value="PH" label="PH"/>
													<form:option value="non-PH" label="non-PH"/>							    	
											</form:select>
											
											<td  style="width: 90px;"><b>Staff Type</b></td>
											<td style="width: 300px;">
											<c:choose>
												<c:when test="${not empty StaffTypeList}">
												<form:select id="staffType" path="staffType" >
													<form:option value="" label="Select..."/>
													<form:options items="${StaffTypeList}" />
												</form:select>
												</c:when>					
												<c:otherwise>
													<select id="staffType"  style="width:200px;" disabled> 
														<option selected="selected" value = "">Select...</option>
													</select>
												</c:otherwise>			
											</c:choose>
									</tr>
									<tr>
													<td  style="width: 90px;"><b>Amendment</b></td>
											<td style="width: 300px;">
												<form:select id="amendment" path="amendment" >
													<form:option value="" label=""/>
													<form:option value="Y" label="Y"/>
													<form:option value="N" label="N"/>							    	
												</form:select>
												</td>
												<td  style="width:70px;"><b>Assignee</b></td>
										<td style="width: 90px;">
										<form:input id="assignee" path="assignee" />
									
							</table>

						</div>
						<div class="orderSearchButton">
							<input type="button" value="Export" id="exportButton" onclick="exportExcel()">
							<input type="button" value="Search" id="searchButton" onclick="javascript:submitForm();">
							<input type="button" value="Clear" onclick="javascript:formClear();">
						</div>

						<div style="clear: both"></div>
					</div>
					<input type="hidden" id="index" name="index" value="">

				</form:form>


<div class="orderSearch">
	<h2 class="table_title" style="font-size:medium; margin:0">Order Enquiry</h2>
	<div class="overflowDiv orderSearchResult" style="margin-top: 5px">
		<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="basketGrid" style="padding-left:0px;">
			<thead>
				<tr class="contenttextgreen">
					<td class="table_title">Order ID</td>
					<td class="table_title">Team Code</td>
<!-- 					<td class="table_title">Salesman Code.</td> -->
					<td class="table_title">Create by.</td>
					<td class="table_title">Customer Name</td>
					<td class="table_title">Id Doc Num</td>
					<c:if test="${ImsDsQCProcessUI.lob == 'IMS'}">
						<td class="table_title">Login Id</td>
					</c:if>
					<td class="table_title">Payment Method</td>
					<td class="table_title">Is 3rd Party Credit Card</td>
					<td class="table_title">Housing Type</td>
					<td class="table_title">Staff Type</td>									
					<td class="table_title">Assignee</td>
					
					<td class="table_title">Application Date</td>
					<td class="table_title">Service Request Date</td>
					<td class="table_title">Sign-off Date</td>
					
					<td class="table_title">Amendment</td>
<!-- 					<td class="table_title">Last Amendment Date</td> -->
					<td class="table_title">Amendment History</td>
					<td class="table_title">Order Status</span></td>
					<td class="table_title">QC Status</td>
					<td class="table_title">Re-assgn Order</td>
					

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${imsOrderList}" var="imsOrderList">
					<tr>
<%-- 						<td><a href="imsorderdetail.html?orderId=${imsOrderList.orderId}" title="Order Enquiry for IMS">${imsOrderList.orderId}</a><c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if></td> --%>
						<td><a href="###" onclick="openDialog4Sum('${imsOrderList.orderId}', '${imsQcComOrderSearchUI.lob}', '${imsOrderList.orderType}')" title="Order Enquiry">${imsOrderList.orderId}</a><c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if></td>
						<td>${imsOrderList.salesTeam}<c:if test='${empty imsOrderList.salesTeam}'>&nbsp;</c:if></td>
<%-- 						<td>${imsOrderList.salesCd}<c:if test='${empty imsOrderList.salesCd}'>&nbsp;</c:if></td> --%>
						<td>${imsOrderList.createBy}<c:if test='${empty imsOrderList.createBy}'>&nbsp;</c:if></td>
						<!-- <td>${imsOrderList.ocid}<c:if test='${empty imsOrderList.ocid}'>&nbsp;</c:if></td> -->
						<td>${imsOrderList.custName}<c:if test='${empty imsOrderList.custName}'>&nbsp;</c:if></td>
						<td>${imsOrderList.idDocNum}<c:if test='${empty imsOrderList.idDocNum}'>&nbsp;</c:if></td>
						<c:if test="${ImsDsQCProcessUI.lob == 'IMS'}">
							<td>${imsOrderList.loginId}<c:if test='${empty imsOrderList.loginId}'>&nbsp;</c:if></td>
						</c:if>
						<td>${imsOrderList.paymentMtd}<c:if test='${empty imsOrderList.paymentMtd}'>&nbsp;</c:if></td>
						<td>${imsOrderList.is3rdParty}<c:if test='${empty imsOrderList.is3rdParty}'>&nbsp;</c:if></td>
						<td>${imsOrderList.housingType}<c:if test='${empty imsOrderList.is3rdParty}'>&nbsp;</c:if></td>
						<td>${imsOrderList.staffType}<c:if test='${empty imsOrderList.is3rdParty}'>&nbsp;</c:if></td>
						<td>${imsOrderList.assignee}<c:if test='${empty imsOrderList.assignee}'>&nbsp;</c:if></td>
							
						
						<td>${imsOrderList.appDate}<c:if test='${empty imsOrderList.appDate}'>&nbsp;</c:if></td>
						<td>${imsOrderList.serviceReqDate}<c:if test='${empty imsOrderList.serviceReqDate}'>&nbsp;</c:if></td>
						<td>${imsOrderList.signoffDate}<c:if test='${empty imsOrderList.signoffDate}'>&nbsp;</c:if></td>
						<td>${imsOrderList.amendment}<c:if test='${empty imsOrderList.amendment}'>&nbsp;</c:if></td>
						
					
						<c:choose>
							<c:when test='${imsOrderList.amendment == "N"}'><td>&nbsp;</td>
							</c:when>
							<c:otherwise>
								<td><input type="button" value="Details" id="rmDtlButton" onclick="javascript:QCamendRemarksOpenDialog('${imsOrderList.orderId}');"></td>
							&nbsp;</c:otherwise>
						</c:choose>
						
						
						<td>
							<c:choose>
								<c:when test='${empty imsOrderList.orderStatus}'>&nbsp;
								</c:when>
								<c:otherwise>
								${imsOrderList.orderStatus}
								</c:otherwise>							
							</c:choose>
						</td>
						
<%-- 						<td>${imsOrderList.qcStatus} </td> --%>

							<%-- <td>${imsOrderList.qcStatus} <input type="button"
							 	value="Details" id="qcStatusButton"
								onclick="javascript:QCRemarksOpenDialog('${imsOrderList.orderId}');"></td> --%>

						<c:choose>
							<c:when test='${empty imsOrderList.qcRemarks}'><td>&nbsp;</td>
							</c:when>
							<c:otherwise>
								<td>${imsOrderList.qcStatus} <input type="button"
 									value="Details" id="qcStatusButton" 
									onclick="javascript:QCRemarksOpenDialog('${imsOrderList.orderId}');"></td>
							&nbsp;</c:otherwise>
						</c:choose>
					    
						<td><c:choose><c:when test='${IsSalesManager == "Y"}'>
								<input type="button" value="Re-assign" id="reAssignButton" onclick="javascript:openDialogForQCAssign('${imsOrderList.orderId}');">
							</c:when></c:choose>
						</td>
	
					</tr>
				</c:forEach>
			</tbody>
		</table>		
	</div>
</div>
