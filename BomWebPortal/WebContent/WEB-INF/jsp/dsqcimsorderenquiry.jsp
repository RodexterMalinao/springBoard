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
   // window.onload = loadFormOnStartup(); //added by ANdy
	

	function openDialog4Sum(orderId, lob){
	
		if(lob == 'IMS'){
			openModalDialog("imsorderdetail.html?orderId=" + orderId , "Order Id :"+orderId); 
		}else if(lob == 'LTS'){
			openModalDialog("ltsorder.html?action=ENQUIRY&orderId=" + orderId , "Order Id :"+orderId); 
		}
	
	}
   
	function openDialog4SumNTV(orderId){	
		   openModalDialog('${ntvDomain}'+"ntvorderdetail.html?orderId=" + orderId , "Order Id :"+orderId);	
	}
   
	
	function openDialog(sbuid, ocid){
		openModalDialog("orderamend.html?sbuid="+sbuid+"&orderId="+ocid+"&dialogMode=true", "Order Id :"+ocid);
	}
	
	//added by Andy 16092104
	function openDialogForQCAssign(orderId,sys_f){
		//alert("checkedOrderIds: " + checkedOrderIds);
		//openModalDialog("qcimsassign.html?orderId=" + orderId+"&action=assign" , "Order Id :"+orderId);
		openModalDialog("qcimsassign.html?orderId=" + orderId+"&action=assign"+"&sys_f="+sys_f , "Order Id :"+orderId);
	}
	
	function openDialogForQCAssignBulk(){
		var checkedOrderIds=[];
		var delimiter = "";
		
		$("[class='qccheckbox']:checked").each(function(i,e){
			checkedOrderIds += delimiter + $(e).attr("id");
			delimiter = "|";
		});
		
		//alert("checkedOrderIds: " + checkedOrderIds);
		
		openModalDialog("qcimsassign.html?orderId="+checkedOrderIds+"&action=assignBulk" , "Order Id :"+checkedOrderIds);
	}
	
	function QCRemarksOpenDialog(orderid){
		openModalDialog("qcimsremarks.html?orderId="+orderid);
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
	
	function QCAdminOpenDialog(orderId){
		//alert("orderId:" +orderId);
		
		 /* Test this function */
		// When click on #btntest, alert the selected values
		//document.getElementById('btntest').onclick = function(){
		//  var selchb = getSelectedChbox(this.form);     // gets the array returned by getSelectedChbox()
		//  alert(selchb);
	   //	}
		openModalDialog("qcimsadmin.html");
		//openModalDialog("qcimsadmin.html?orderId=" + orderId);
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
			
			$(".qccheckbox").change(function(){
				$("#bulkAssignButton").attr("disabled",true); 
				$(".qcAssgnButton").attr("disabled",true); 
				if($(".qccheckbox:checked").length >=2) $("#bulkAssignButton").attr("disabled",false);  
				$(".qccheckbox:checked").each(function(i,e){
					var seq = $(e).attr("name").substr(8);  
					//alert(seq); 
					
					$("[name='assign"+seq+"']").attr("disabled",false);
				});

			});
			
			//alert("ready function loaded");
			
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
				"aaSorting": [[ 8, "desc" ]],
				"sScrollY": "340px",
				"bScrollCollapse": true,
				"bSortClasses": false
			});
			
			var a = 10;
			var b = 30;
			var c = 100;
			
			//$("#basketGrid_wrapper #basketGrid_info").append("<span>&nbsp;&nbsp;&nbsp;&nbsp; Today OutStanding Orders:"+a+" &nbsp;&nbsp;&nbsp;&nbsp; Past 7 days OutStanding Orders:"+b+" &nbsp;&nbsp;&nbsp;&nbsp; Total OutStanding Orders:"+c+" </span>");   
			
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
			
			$("#LOB").change(
					function(){
								$('#ImsLoginId').hide(10);
								$('#ImsLoginId2').hide(10);
								$("#loginIdType").attr('disabled', true);
								$("#loginId").attr('disabled', true);
					}							
				);	
			
			
			$("[name=lob]").change(
					function(){
						displaySelectList();
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
			 
			 //added for sign-off date
			 $('#dateDatepickerStart2').datepicker({
					changeMonth : true,
					changeYear : true,
					showButtonPanel : true,
					dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
					maxDate: "+0d",yearRange: '2013:+1'
					, onSelect: function(dateText, inst) {
						var futureDate = $(this).datepicker("getDate");
						var toDate = new Date ();
//						alert(toDate);
						futureDate.setDate(futureDate.getDate() + 30);
												
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
				colFilter(bTable, 2, $(this).val()+" ");
			});
			
// 			$('#dateDatepickerStart').datepicker( "option", "maxDate", new Date (2999,1,1));
// 			$('#dateDatepickerEnd').datepicker( "option", "maxDate", new Date (2999,1,1));
			
		});
		
		$( window ).load(function() {
			//alert("load function loaded");
			//loadFormOnStartup();
		});
		
		function displaySelectList(){
			if($("[name=lob]:checked").val() == 'LTS'){
				$("#orderStatus").hide().attr('disabled', true);
				$("#ltsOrderStatus").show().attr('disabled', false);
				
			}else{
				$("#orderStatus").show().attr('disabled', false);
				$("#ltsOrderStatus").hide().attr('disabled', true);
			}
		}

		function keyUpOnImsLoginID() {
			 	document.getElementById('loginId').value = document.getElementById('loginId').value.toLowerCase();
		}

		function keyUpOnDocNum() {
			 	document.getElementById('loginId').value = document.getElementById('loginId').value.toUpperCase();
		}
		
		//added by Andy
		function loadFormOnStartup() {
			/*if(isInputOkayBeforeSubmit()){
				document.ccLtsImsOrderEnquiryForm.submit();
			}*/
			submitForm();
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
		
		
		
// 		$('input[type="checkbox"][name="kinman"]').change(
// 			    function(){
// 			        if (this.checked) {
// 			            alert('checked');
// 			        }
// 			    });
		
		/*
		$('input[type="checkbox"][name="kinman"]').live('change', function(){
		    if($(this).is(':checked')){
		        alert('checked');
		    } else {
		        alert('un-checked');
		    }
		});
		*/
	    //$('input[type="checkbox"][name="kinman"]').click(function() {
	    	
	    	//alert('abc');
	      //  if ($(this).is(':checked')) {
	      //      $('#assgnButton').attr('disabled', 'disabled');
	       // } else {
	       //     $('#assgnButton').removeAttr('disabled');
	       // }
	    //});
		
	</script>			

							
<form:form name="ccLtsImsOrderEnquiryForm" method="POST" commandName="DsQCImsOrderEnquiryUI">
					

							        			<form:hidden path="reset"/>
					
					<div class="orderSearch">
						<h2 class="table_title" style="font-size: medium; margin: 0">Direct Sales QC Assignment</h2>
						
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
											<form:radiobutton id="signOffDate" path="dateType" label="Sign-off Date" value="F" />
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
										</tr>	
										<!-- added by Andy --> 
<!-- 										<tr> -->
<!-- 											<td  style="width:110px;"><b>Order ID</b></td> -->
<%-- 											<td  style="width: 90px;"><form:input id="orderId" path="orderId" /></td> --%>
<!-- 											<td  style="width:110px;"><b>Await waive QC</b></td> -->
<%-- 											<td  style="width: 90px;"><form:checkbox id="waiveQC" path="waiveQC" value="Y" /></td> --%>
<!-- 										</tr> -->
								
<!-- 										<tr> -->
<!-- 											<td  style="width:110px;"><b>Salesman Code</b></td> -->
<%-- 											<td  style="width: 90px;"><form:input id="salesNum" path="salesNum" /></td> --%>
<!-- 											<td  style="width:110px;"><b>Waive QC Approved</b></td> -->
<%-- 											<td  style="width: 90px;"><form:checkbox path="waiveQCapproved" id="waiveQCapproved" value="Y" /></td> --%>
<!-- 										</tr> -->
								
								<!---
								<tr>
									<td  style="width:110px;"><b>Sign-off Date:</b></td>
										<td style="width: 90px;">
										<form:input id="dateDatepickerStart2" path="" readonly="true"/>
									</td>
								</tr>
								--->
						</table>

						</div>
						
						<div class="orderSearchButton">
						    <input type="button" value="QC Admin" id="qcAdminButton" onclick="javascript:QCAdminOpenDialog();">
						    <input type="button" value="QC Bulk orders assign" id="bulkAssignButton" disabled onclick="javascript:openDialogForQCAssignBulk();">
						    <input type="button" value="Search" id="searchButton" onclick="javascript:submitForm();">
							<input type="button" value="Clear" onclick="javascript:formClear();">
<!-- 							<a class="nextbutton" href="javascript:void(0)" id="qcAdminButton" onclick="javascript:QCAdminOpenDialog();" >QC Admin</a> -->
<!-- 							<a class="nextbuttonDisabled"  id="bulkAssignButton" onclick="javascript:openDialogForQCAssignBulk();" >QC Bulk orders assign</a> -->
<!-- 							<a class="nextbutton" href="javascript:void(0)" id="searchButton" onclick="javascript:submitForm();" >Search</a> -->
<!-- 							<a class="nextbutton" href="javascript:void(0)" onclick="javascript:formClear();" >Clear</a> -->
						</div>

	
						<div style="clear: both"></div>
					</div>
					<input type="hidden" id="index" name="index" value="">
			
				

<div class="orderSearch">
	<h2 class="table_title" style="font-size:medium; margin:0">Order Enquiry</h2>
	<div class="overflowDiv orderSearchResult" style="margin-top: 5px">
		<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF" id="basketGrid" style="padding-left:0px;">
			<thead>
				<tr class="contenttextgreen">
				    <td class="table_title">QC Item</td>
				    <td class="table_title">Order ID</span></td>
				    <c:choose>
							<c:when test='${IsSalesManager == "Y"}'>
						    <td class="table_title">Team Code</span></td>
						    </c:when>
				  	</c:choose>						    
					
					<td class="table_title">Create by.</td>
					<td class="table_title">Customer Name</td>
					<td class="table_title">Application  Date</td>
					<td class="table_title">Service Request Date</td>
					<td class="table_title">Sign-off Date</td>
					<td class="table_title">Order Status</span></td>
<!-- 					<td class="table_title">Waived QC</td> -->
<!-- 					<td class="table_title">Waive QC Approved</td> -->
					<td class="table_title">Remarks</td>
					<td class="table_title">Assign QC Order</td>
				</tr>
			</thead>
			<tbody>
			    <c:forEach items="${imsOrderList}" var="imsOrderList" varStatus="i">
					<tr>
<%-- 						<td><a href="imsorderdetail.html?orderId=${imsOrderList.orderId}" title="Order Enquiry for IMS">${imsOrderList.orderId}</a><c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if></td> --%>
						 <td>
						 	<input id=${imsOrderList.orderId} class="qccheckbox" name="checkbox${i.index}" type="checkbox" value="qcItem" ><br> 
<%-- 						 	<form:checkbox path="chkBoxOrderID" cssClass="qccheckbox" value="${imsOrderList.orderId}"/> --%>
						 </td>
						
						<c:choose>
							<c:when test='${imsOrderList.orderType == "NTV-A" || imsOrderList.orderType == "NTVAO" || imsOrderList.orderType == "NTVUS"|| imsOrderList.orderType == "NTVRE"}'>
								<td><a href="###" onclick="openDialog4SumNTV('${imsOrderList.orderId}&_al=new')" title="Order Enquiry for IMS">${imsOrderList.orderId}</a><c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if></td>
					 		</c:when>
							<c:otherwise>
								<td><a href="###" onclick="openDialog4Sum('${imsOrderList.orderId}','${DsQCImsOrderEnquiryUI.lob}')" title="Order Enquiry">${imsOrderList.orderId}</a><c:if test='${empty imsOrderList.orderId}'>&nbsp;</c:if></td>
							</c:otherwise>						
						</c:choose>
						
					 	<c:choose>
							<c:when test='${IsSalesManager == "Y"}'>
								<td>${imsOrderList.salesTeam}</td>
							</c:when>
						</c:choose>			
					 	
					 	<td>${imsOrderList.createBy}<c:if test='${empty imsOrderList.createBy}'>&nbsp;</c:if></td>       
					 	<td>${imsOrderList.custName}<c:if test='${empty imsOrderList.custName}'>&nbsp;</c:if></td>      
					 	<td>${imsOrderList.appDate}<c:if test='${empty imsOrderList.appDate}'>&nbsp;</c:if></td>
						<td>${imsOrderList.serviceReqDate}<c:if test='${empty imsOrderList.serviceReqDate}'>&nbsp;</c:if></td>
						<td>${imsOrderList.signoffDate}<c:if test='${empty imsOrderList.signoffDate}'>&nbsp;</c:if></td>
						<td>
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
						</td>

<%-- 						<c:choose> --%>
<%-- 							<c:when test="${imsOrderList.waiveQC eq 'Y'}"> --%>
<!--  								<td>Y</td>&nbsp;  -->
<%-- 							</c:when> --%>
<%-- 							<c:otherwise> --%>
<!--  								<td>N</td>&nbsp;  -->
<%-- 							</c:otherwise> --%>
<%-- 						</c:choose> --%>
						
<%-- 						<c:choose> --%>
<%-- 							<c:when test="${imsOrderList.waiveQCapproved eq 'Y'}"> --%>
<!--  								<td>Y</td>&nbsp;  -->
<%-- 							</c:when> --%>
<%-- 							<c:otherwise> --%>
<!--  								<td>N</td>&nbsp;  -->
<%-- 							</c:otherwise> --%>
<%-- 						</c:choose> --%>
						
						<td> 
<%-- 							<c:forTokens items="${imsOrderList.qcRemarks}" delims=" " var="name"> --%>
<%--    								<c:out value="${name}"/><p> --%>
<%-- 							</c:forTokens> --%>
<%-- 							${imsOrderList.qcRemarks} --%>
							${imsOrderList.qcRemarks}
							<c:if test="${not empty imsOrderList.qcRemarks}">
								<input type="button" value="Details" id="rmDtlButton${i.index}" onclick="javascript:QCRemarksOpenDialog('${imsOrderList.orderId}');">	
<%-- 								<a class="nextbutton" style="padding-top: 1px;padding-bottom: 1px;" href="javascript:void(0)" id="rmDtlButton${i.index}" onclick="javascript:QCRemarksOpenDialog('${imsOrderList.orderId}');" >Details</a> --%>
							</c:if>
						</td>&nbsp;
						
<%-- 						<c:choose> --%>
<%-- 						<c:when test = "${i.index eq 12 or i.index eq 7}"> --%>
<!-- 							<td>Ressign due to customer call back and quest call 01/03 after 19:00 -->
<%-- 							<input type="button" value="Details" id="rmDtlButton${i.index}" onclick="javascript:QCRemarksOpenDialog('${imsOrderList.orderId}');"> --%>
<!-- 							</td>&nbsp; -->
							
<%-- 						</c:when> --%>
<%-- 						<c:when test = "${i.index%3 eq 0}"> --%>
<!-- 							<td>System lookup -->
<%-- 							<input type="button" value="Details" id="rmDtlButton${i.index}" onclick="javascript:QCRemarksOpenDialog('${imsOrderList.orderId}');"> --%>
<!-- 							</td>&nbsp; -->
							
<%-- 						</c:when> --%>
<%-- 						<c:otherwise>  --%>
<!-- 							<td></td>&nbsp; -->
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose> --%>
						
						<td>
						<input type="button" value="Assign" class="qcAssgnButton" name="assign${i.index}" disabled onclick="javascript:openDialogForQCAssign('${imsOrderList.orderId}','${imsOrderList.sysF}');">
<%-- 							<a class="nextbuttonDisabled" name="assign${i.index}" id="rmDtlButton${i.index}" onclick="" style="    padding-top: 1px;    padding-bottom: 1px;">Assign</a> --%>
						</td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		
	</div>
</div>
</form:form> 