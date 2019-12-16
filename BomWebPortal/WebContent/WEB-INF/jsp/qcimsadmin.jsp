<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" 
    rel="stylesheet" />
    
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
<!-- <link type="text/css" href="css/dataTable/jquery.dataTables.css" rel="stylesheet" />  --> 
 <%@ include file="imsloadingpanel.jsp" %>

<style type="text/css">
.sbOrderHistDiv { overflow-y:auto; display: inline-block; width: 100% }
.searchRow { padding: 15px 10px }
.label { display: inline-block; width: 85px; font-size: 12px; font-family: "Verdana", "Helvetica", "Arial", "sans-serif" }
.labelFirst { width: 110px }
.input { display: inline-block; width: 120px }
.searchColHalf { overflow: hidden; width: 48%; display: inline-block; float: left  }
.even { background-color: #eae5ef }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px; font-family: "Helvetica", "Arial", "sans-serif" }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078 !important; padding: 5px 10px; position: relative }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.custPremier { font-weight: bold; color: red }
.inputGroup { display: inline-block; overflow: hidden; vertical-align: middle }
.contenttext td { border-style: ridge}
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.sbOrderHistDiv { padding-bottom: 1em }
</style>
<![endif]-->

<script type="text/javascript">
var mainTable = null;
var counter = 1;

$(document).ready(function() {
	/* 
	$("#customerInformation input[type=text]").keypress(function(event) {
		switch(event.keyCode) {
		case 13:
			custInfoSearch();
			break;
		}
	});
		
	
		$('a#createEyeUpgradeOrderBtn')
		.click(
				function(event) {
					if("${sessionScope.bomsalesuser.channelId}" == 1){
						alert("<Sales> Remind Customer that his/her PCCW info of Fixed Line / Netvigator / NOW TV will be viewed during the process");
					}
					document.customerInformationForm.actionType.value = "CREATELTS";
					document.customerInformationForm.submit();
				});
		
		
		// add by Joyce 20111118
		// control PCD & eyex button display
		$("#PCD").show();
		$("#PCD2").hide();
		$("#createEyeUpgradeOrderBtn").show();
		$("#createEyeUpgradeOrderBtn2").hide();
		
		if ($("#imsLtsButtonInd").val() == 'Y') {
			if ( "${isCC}" != "Y")
			{
				$("#PCD").hide();
				$("#PCD2").show();
			}
		}
		
		if ($("#ltsButtonInd").val() == 'Y') {
			$("#createEyeUpgradeOrderBtn").hide();
			$("#createEyeUpgradeOrderBtn2").show();
		} 
		 
		////////jacky datatable test
		mainTable = $("#helloworld").dataTable({
						"bInfo" : false ,
						"bFilter" : false,
						"bLengthChange " : false,
						"bPaginate" :false,
						"paging": false
						
			    	}); */
			    	updateAllLobInd();
			    	
					$("#staffId").blur(function(e){
						e.target.value = e.target.value.toUpperCase(); 
					}); 
					$("#staffId").keyup(function(e){ 
						e.target.value = e.target.value.toUpperCase(); 
					});
});
/* 

function hidePCD() {
	
	if (document.customerInformationForm.idDocType.options[document.customerInformationForm.idDocType.selectedIndex].value == "BR"
	) {
		$("#PCD").hide();
		$("#PCD2").show();
		$("#createEyeUpgradeOrderBtn").show();
		$("#createEyeUpgradeOrderBtn2").hide();
	} else if (document.customerInformationForm.idDocType.options[document.customerInformationForm.idDocType.selectedIndex].value == "Certificate No"
	) {
		$("#PCD").hide();
		$("#PCD2").show();
		$("#createEyeUpgradeOrderBtn").hide();
		$("#createEyeUpgradeOrderBtn2").show();
	} else {
		$("#PCD").show();
		$("#PCD2").hide();
		$("#createEyeUpgradeOrderBtn").show();
		$("#createEyeUpgradeOrderBtn2").hide();
	}
}

function printErrMsg() {
	alert("BR and Certificate Customer is not supported in Springboard, please use written contract.");
}

function printLtsErrMsg() {
	alert("Certificate Customer is not supported in Springboard, please use written contract.");
}

function clickIdDocType() {
	document.customerInformationForm.serviceNumberType.value = "NONE";
	document.customerInformationForm.serviceNumber.value = "";
	document.customerInformationForm.loginIdPrefix.value = "";
	document.customerInformationForm.loginIdSuffix.value = "NONE";

	//		document.customerInformationForm.actionType.value = "CLEAR";
	//		document.customerInformationForm.submit();
}

function clickIdDocNum() {
	document.customerInformationForm.serviceNumberType.value = "NONE";
	document.customerInformationForm.serviceNumber.value = "";
	document.customerInformationForm.loginIdPrefix.value = "";
	document.customerInformationForm.loginIdSuffix.value = "NONE";

	//		document.customerInformationForm.actionType.value = "CLEAR";
	//		document.customerInformationForm.submit();
	//		document.customerInformationForm.idDocNum.select();
}

function clickServNumType() {
	document.customerInformationForm.idDocType.value = "NONE";
	document.customerInformationForm.idDocNum.value = "";
	document.customerInformationForm.loginIdPrefix.value = "";
	document.customerInformationForm.loginIdSuffix.value = "NONE";

	//		document.customerInformationForm.actionType.value = "CLEAR";
	//		document.customerInformationForm.submit();
}

function clickServNum() {
	document.customerInformationForm.idDocType.value = "NONE";
	document.customerInformationForm.idDocNum.value = "";
	document.customerInformationForm.loginIdPrefix.value = "";
	document.customerInformationForm.loginIdSuffix.value = "NONE";

	//		document.customerInformationForm.actionType.value = "CLEAR";
	//		document.customerInformationForm.submit();
}

function clickLoginIdPrefix() {
	document.customerInformationForm.idDocType.value = "NONE";
	document.customerInformationForm.idDocNum.value = "";
	document.customerInformationForm.serviceNumberType.value = "NONE";
	document.customerInformationForm.serviceNumber.value = "";

	//		document.customerInformationForm.actionType.value = "CLEAR";
	//		document.customerInformationForm.submit();
}

function clickLoginIdSuffix() {
	document.customerInformationForm.idDocType.value = "NONE";
	document.customerInformationForm.idDocNum.value = "";
	document.customerInformationForm.serviceNumberType.value = "NONE";
	document.customerInformationForm.serviceNumber.value = "";

	//		document.customerInformationForm.actionType.value = "CLEAR";
	//		document.customerInformationForm.submit();
}

function createMobOrder() {
	document.customerInformationForm.actionType.value = "CREATE";
	document.customerInformationForm.submit();
}
function createLtsOrder() {
	document.customerInformationForm.actionType.value = "CREATELTS";
	document.customerInformationForm.submit();
	document.customerInformationForm.submit();
}
function custInfoSearch() {
	$("#loginIdPrefix").val($("#loginIdPrefix").val().toLowerCase());
	document.customerInformationForm.actionType.value = "REFRESH";
	document.customerInformationForm.submit();
}
function custInfoClear() {
	document.customerInformationForm.actionType.value = "CLEAR";
	document.customerInformationForm.idDocType.value = "NONE";
	document.customerInformationForm.idDocNum.value = "";
	document.customerInformationForm.serviceNumberType.value = "NONE";
	document.customerInformationForm.serviceNumber.value = "";
	document.customerInformationForm.loginIdPrefix.value = "";
	document.customerInformationForm.loginIdSuffix.value = "NONE";
	document.customerInformationForm.submit();
}
 */
//added by andy
function createNewQCstaff() {
	
	if($('#staffId').val().length ==0) {
		alert("Please input a staff num.");
		return;
	}  
	
	if (!$('.qcSkillTmp').is(':checked')){
		alert("Please choose at least one skill");
		return;
	}

		
// 	var data=[];  
// 	data.push(++counter);  
// 	data.push("<input id='staffId"+counter+"' type='text' name='staffId' value='"+$('#staffId').val()+"' disabled>");  
// 	data.push($("#qcSkillTmp").clone().html());  
// 	data.push($("#qcStatusTmp").clone().html());
// 	data.push($("#todayOSTmp").clone().html());
// 	data.push($("#todayAssignedOSTmp").clone().html());
// 	data.push($("#pSevenDayOSTmp").clone().html());
// 	data.push($("#totalOSTmp").clone().html());
// 	data.push($("#reassignBtnTmp").clone().html());
// 	data.push("");
// 	data.push("");
	
	//alert($("#qcSKillTmp").clone());  
	
	//mainTable.fnAddData(data); 
 	submitShowLoading(); 
	
	 $.ajax({
			url : 'imsqcadminajax.html?tp=insert' ,   
			type : 'POST',
			dataType : 'json',
			data: $(createForm($("#row1"))).serialize() , 
			timeout : 25000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			     if(textStatus=='parsererror'){
					alert("Your session has been timed out, please re-login."); 
			     } else {
			    	 alert("Error Inserting Data. Please re-try");
			     }
			},
			success : function(msg) {
				/* $.each(eval(msg), function(i, item) {
					parent.$("#qcDialog").dialog('close'); 
				}); */
				hideSubmitLoading();
				$.each(eval(msg), function(i, item) {
					//alert( i );
					var arr = JSON.stringify(item);
					var obj = jQuery.parseJSON(arr); 
					//alert( obj.resultMsg.length);
					if (obj.resultMsg.length >0){
						alert( obj.resultMsg);
						return;
					}else if (obj.resultMsg.length == 0){
						location.reload();
					}
				});
				
					
// 				var obj = jQuery.parseJSON( msg );
				//alert( obj );	
				//var obj = jQuery.parseJSON(response);
				//alert( response );
				
				//location.reload();
			}
		});
	
	
}

function reAssignQCorder(btn){
	//alert($(btn).attr("id").substr(12));
	 var rowID = "#qcrow"+$(btn).attr("id").substr(12);
	 //alert("rowID: " + rowID);
	 //var rowID= row + $(btn).attr("id").substr(12);
	 
	 $(".displayStaff").attr("disabled",false);
	  
	 if (confirm("Confirm to release all orders?")) { 		
	 
		 $.ajax({
				url : 'imsqcadminajax.html?tp=release' ,   
				type : 'POST',
				dataType : 'json',
				data: $(createForm($(rowID))).serialize() , 
				timeout : 25000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				     if(textStatus=='parsererror'){
						alert("Your session has been timed out, please re-login."); 
				     } else {
				    	 alert("Error releasing staff orders. Please re-try");
				     }
				},
				success : function(msg) {
					/* $.each(eval(msg), function(i, item) {
						parent.$("#qcDialog").dialog('close'); 
					}); */
					$(".displayStaff").attr("disabled",true);
					$(".insertStaff").attr("disabled",false);				
					location.reload();
	// 				 if (confirm("Confirm to release all orders?")) { 
	// 					 y.innerHTML = "0";
	// 					 z.innerHTML = "0";
	// 					 x.innerHTML = "0";
	// 					 w.innerHTML = "0";
	// 				 }
				}
			}); 
	 }	 
	
}

function removeQcStaff(btn){
	//alert($(btn).attr("id"));
	 var rowID = "#qcrow"+$(btn).attr("id").substr(8);
	//alert("rowID: " + rowID);
	//var rowID= row + $(btn).attr("id").substr(12);
	 $(".displayStaff").attr("disabled",false);
	 if (confirm("Confirm to remove staff?")) { 	
		 
	 
		$.ajax({
			url : 'imsqcadminajax.html?tp=remove' ,   
			type : 'POST',
			dataType : 'json',
			data: $(createForm($(rowID))).serialize() , 
			timeout : 25000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			     if(textStatus=='parsererror'){
					alert("Your session has been timed out, please re-login."); 
			     } else {
			    	 alert("Error in removing staff. Please re-try");
			     }
			},
			success : function(msg) {
				/* $.each(eval(msg), function(i, item) {
					parent.$("#qcDialog").dialog('close'); 
				}); */
				
				//$("[name='staffId']").attr("disabled",true);
				$(".displayStaff").attr("disabled",true);
				$(".insertStaff").attr("disabled",false);
				location.reload();
// 				 var y = document.getElementById("todayOSData");
// 				 var z = document.getElementById("totalOSData");
// 				 var w = document.getElementById("todayAssignedOSData");
// 				 var x = document.getElementById("pSevenDayOSData");
				 
// 				 if (confirm("Confirm to remove QC staff?")) { 
// 					 y.innerHTML = "0";
// 					 z.innerHTML = "0";
// 					 x.innerHTML = "0";
// 					 w.innerHTML = "0";
// 				 }
			}
		});
	 }
}

function updateSkillsAndStatus(btn){
	//alert($(btn).attr("id"));
	 var rowID = "#qcrow"+$(btn).attr("id").substr(9);
	
	 //alert("rowID: " + rowID);
	  $(".displayStaff").attr("disabled",false);
	 
	 if (confirm("Confirm to upate staff info?")) {  
	  
		 $.ajax({
				url : 'imsqcadminajax.html?tp=update' ,   
				type : 'POST',
				dataType : 'json',
				data: $(createForm($(rowID))).serialize() , 
				timeout : 25000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				     if(textStatus=='parsererror'){
						alert("Your session has been timed out, please re-login."); 
				     } else {
				    	 alert("Error in updating staff info. Please re-try");
				     }
				},
				success : function(msg) {
					/* $.each(eval(msg), function(i, item) {
						parent.$("#qcDialog").dialog('close'); 
					}); */
					$(".displayStaff").attr("disabled",true);
					$(".insertStaff").attr("disabled",false);
					location.reload();
				}
			}); 
	 }
	
}


function createForm(div){
	
	//alert(div.html());
	
	$(div).find("input, select").each(function(i, e){ 
		if($(e).attr("name").indexOf("skillSet")>0) $(e).attr("name", "skillSet");
		else if($(e).attr("name").indexOf("status")>0) $(e).attr("name", "status");
	});
 	
	$form = $("<form></form>"); 
	$form.append(div.clone());
	
	$form[0].status.value = $(div).find("[name='status']").val(); 
	
	return $form;
}

function toggle(source) {
	  checkboxes = document.getElementsByName($(source).attr("value"));
	  
	  for(var i=0, n=checkboxes.length;i<n;i++) {
		 //alert(checkboxes[i].value);
		 if (checkboxes[i].value != "CHIN" && checkboxes[i].value !="ENG" )
	     checkboxes[i].checked = source.checked;
	  }
}

function updateAllLobInd(){
	$(".allLob").each(function(i, e){
		var a = $(e).parent().find(".lobSkills:checked").length;
		var b = $(e).parent().find(".lobSkills").length;
		if(a==b) $(e).attr("checked", true);
		else $(e).attr("checked", false);
	});
	
}

</script>	

<form:form name="imsAdminForm"  commandName="qcImsAdminUI" method="POST">

	<table id="	" width="100%"  class="tablegrey">
	  <tr>
		<td bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" rules="none">
			<tr>
				<td class="table_title" >Assign order ID to QC Staff</td>
			</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" class="contenttext">
			<thead>
				<tr class="contenttextgreen">
							<td class="table_title">#</td>
						    <td class="table_title">Staff Number</td> 
							<td class="table_title">Skill</td>
							<td class="table_title">Status</td>
							<td class="table_title">Today Outstanding QC Orders</td>
							<td class="table_title">Today Assigned Orders</td>
							<td class="table_title">Past 7 days Outstanding QC Orders</td>
							<td class="table_title">Total Outstanding QC Orders</td>
							<td class="table_title"></td>
							
				</tr>
			</thead>
			<tbody> 
			<tr id="row1">
			<td> </td>
			<td> <input id="staffId" class="insertStaff" type="text" name="staffId"></td>
			
			
			<td>  
			    <div id="qcSkillTmp" >
			        <input class="qcSkillTmp" type="checkbox" id="nowTV" name="skillSet" value="nowTV">nowTV
					<input class="qcSkillTmp" type="checkbox" id="pcd" name="skillSet" value="PCD">PCD
					<input class="qcSkillTmp" type="checkbox" id="lts" name="skillSet" value="DEL">DEL
					<input class="qcSkillTmp" type="checkbox" id="eye" name=skillSet value="eye">eye
					<input class="allLobTmp" type="checkbox" value="skillSet" onClick="toggle(this)">ALL LOB<br>
					<input class="qcSkillTmp" type="checkbox" id="languageZh" name="skillSet" value="CHIN">Chinese
					<input class="qcSkillTmp" type="checkbox" id="languageEn" name="skillSet" value="ENG">English<br>
				</div>	
			</td>
			
			<td>
			    <div id="qcStatusTmp">
					<select name="status">
					  <option value="A">Active</option>
					  <option value="IA">InActive</option>
					</select>
				</div>
			</td>
			
			<td><div id="todayOSTmp">------------</div></td>
			<td><div id="todayAssignedOSTmp">------------</div></td>
			<td><div id="pSevenDayOSTmp">------------</div></td>
			<td><div id="totalOSTmp">------------</div></td>
			
			<td>
				<div class="orderSearchButton">
			   		<input type="button" value="Insert New Staff" id="createNewStaff" onclick="javascript:createNewQCstaff()">
			   		<input type="button" value="Release" id="assignButtonTmp" onclick="javascript:reAssignQCorder(this);" hidden="hidden">
					<input type="button" value="Remove" id="rmButtonTmp" onclick="javascript:removeQcStaff(this);" hidden="hidden">	
<!-- 					<a class="nextbutton" href="javascript:void(0)" id="createNewStaff" onclick="javascript:createNewQCstaff()" >Insert New Staff</a> -->
<!-- 					<a class="nextbutton" href="javascript:void(0)" id="assignButtonTmp" onclick="javascript:reAssignQCorder(this);" >Release</a> -->
<!-- 					<a class="nextbutton" href="javascript:void(0)" id="rmButtonTmp" onclick="javascript:removeQcStaff(this);" >Remove</a>  -->
				</div>
			</td>
		</tr>
		
			<c:forEach items="${qcImsAdminUI.staffList}" var="s" varStatus="i"> 	
				<tr id="qcrow${i.index}">
						<td>${i.index}</td>
						<td><input name="staffId" value="${s.qcStaffId}" class="displayStaff" disabled/></td>
<!-- 					<td> <input id="1" type="text" name="staffNo" value="T1123355" disabled></td> -->
				
						
						<td>  
<%-- 						    <form:checkboxes items="${skillset}" path="staffList[${i.index}].skillSet" /><br> --%>
								<form:checkbox path="staffList[${i.index}].skillSet" value="nowTV" cssClass="lobSkills"/>nowTV
								<form:checkbox path="staffList[${i.index}].skillSet" value="PCD" cssClass="lobSkills"/>PCD
								<form:checkbox path="staffList[${i.index}].skillSet" value="DEL" cssClass="lobSkills"/>DEL
								<form:checkbox path="staffList[${i.index}].skillSet" value="eye" cssClass="lobSkills"/>eye
								<input class="allLob" type="checkbox" value="staffList[${i.index}].skillSet" onClick="toggle(this)">ALL LOB<br>
								<form:checkbox path="staffList[${i.index}].skillSet" value="CHIN"/>Chinese
								<form:checkbox path="staffList[${i.index}].skillSet" value="ENG"/>English
								
						</td>
						
						<td>
						  <!--<div id="qcStatus">
								   <option value="active">Active</option>
								  <option value="inactive">InActive</option>
								  --> 
							<form:select path="staffList[${i.index}].status">
								<form:options items="${staffStatus}" />
							</form:select>
			
<!-- 							</div> -->
						</td>
						
						<td><div id="todayOSData">		
							${s.todayOsOrders}
						</div></td> 
						
						<td><div id="todayAssignedOSData">		
							${s.todayAssignedOrders}
						</div></td> 
						
						<td><div id="pSevenDayOSData">		
							${s.past7daysAssignedOrders}
						</div></td>
						
						<td><div id="totalOSData">
							${s.totalOrders}
						</div></td> 
						
						<td>
							<div id="reassignBtn" class="orderSearchButton">
						   		<input type="button" value="Release" id="assignButton${i.index}" onclick="javascript:reAssignQCorder(this);">
						   		<input type="button" value="Remove" id="rmButton${i.index}" onclick="javascript:removeQcStaff(this);">
						   		<input type="button" value="Update" id="updButton${i.index}" onclick="javascript:updateSkillsAndStatus(this);">
<%-- 								<a class="nextbutton" href="javascript:void(0)" id="assignButton${i.index}" onclick="javascript:reAssignQCorder(this);" >Release</a> --%>
<%-- 								<a class="nextbutton" href="javascript:void(0)" id="rmButton${i.index}"onclick="javascript:removeQcStaff(this);" >Remove</a> --%>
<%-- 								<a class="nextbutton" href="javascript:void(0)" id="updButton${i.index}" onclick="javascript:updateSkillsAndStatus(this);" >Update</a>  --%>
							</div>
						</td>
					</tr>
				</c:forEach>
				
			</tbody>
		</table>
	</table>
	
</form:form>

<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>