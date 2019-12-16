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
 

<style type="text/css">
.sbOrderHistDiv { overflow-y:auto; display: inline-block; width: 100% }
.searchRow { padding: 15px 10px }
.label { display: inline-block; width: 85px; font-size: 12px; font-family: "Verdana", "Helvetica", "Arial", "sans-serif" }
.labelFirst { width: 110px }
.input { display: inline-block; width: 120px }
.searchColHalf { overflow: hidden; width: 48%; display: inline-block; float: left  }
.even { background-color: #E8FFE8 }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px; font-family: "Helvetica", "Arial", "sans-serif" }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078 !important; padding: 5px 10px; position: relative }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.custPremier { font-weight: bold; color: red }
.inputGroup { display: inline-block; overflow: hidden; vertical-align: middle }
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
						
			    	});
		
});

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

//added by andy
function createNewQCstaff() {
	
	if($('#staffNo').val().length ==0) {
		alert("Please input a staff num.");
		return;
	} 
	
	var data=[];  
	data.push(++counter);  
	data.push("<input id='staff"+counter+"' type='text' name='staffNo' value='"+$('#staffNo').val()+"' disabled>");  
	data.push($("#qcSkill").clone().html());
	data.push($("#qcStatus").clone().html());
	data.push($("#todayOS").clone().html());
	data.push($("#pSevenDayOS").clone().html());
	data.push($("#totalOS").clone().html());
	data.push($("#reassignBtn").clone().html());
	data.push("");
	data.push("");
	
	mainTable.fnAddData(data); 
	$('#staffNo').val(""); 
}

function reAssignQCorder(){
	 var y = document.getElementById("todayOSData");
	 var z = document.getElementById("totalOSData");
	 var x = document.getElementById("pSevenDayOSData");
	 
	 if (confirm("Confirm to release all orders?")) { 
		 y.innerHTML = "0";
		 z.innerHTML = "0";
		 x.innerHTML = "0";
	}
	
}

</script>	



<table id="	" width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="0" rules="none">
		<tr>
			<td class="table_title" >Assign order ID to QC Staff</td>
		</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" class="contenttext" id="helloworld">
		<thead>
			<tr class="contenttextgreen">
						<td class="table_title">#</td>
					    <td class="table_title">Staff Number</td> 
						<td class="table_title">Skill</td>
						<td class="table_title">Status</td>
						<td class="table_title">Today Outstanding Orders</td>
						<td class="table_title">Past 7 days Outstanding Orders</td>
						<td class="table_title">Total Outstanding Orders</td>
						<td class="table_title"></td>
						
			</tr>
		</thead>
		<tbody> 
		<tr id="row1">
		<td> </td>
		<td> <input id="staffNo" type="text" name="staffNo" value="" ></td>
		
		
		<td> 
		    <form>
				<input type="checkbox" id="nowTV" name="nowTV" value="nowTV">nowTV
				<input type="checkbox" id="pcd" name="pcd" value="pcd">PCD
				<input type="checkbox" id="lts" name="lts" value="del">DEL
				<input type="checkbox" id="eye" name="eye" value="eye">eye
				<input type="checkbox" id="allLob" name="allLob" value="allLob">ALL LOB<br>
				<input type="checkbox" id="languageZh" name="language" value="Chinese">Chinese
				<input type="checkbox" id="languageEn" name="language" value="English">English<br>
			</form>	
		</td>
		
		<td>
			<select name="Status">
			  <option value="active">Active</option>
			  <option value="inactive">InActive</option>
			</select>
		</td>
		
		<td><div id="todayOS">------------</div></td>
		<td><div id="pSevenDayOS">------------</div></td>
		<td><div id="totalOS">------------</div></td>
		
		<td>
			<div class="orderSearchButton">
		   		<input type="button" value="Insert New Staff" id="createNewStaff" onclick="javascript:createNewQCstaff()">
			</div>
		</td>
	</tr>
			<tr>
				<td>1</td>
				<td> <input id="1" type="text" name="staffNo" value="T1123355" disabled></td>
				
				
				<td> 
				    <div id="qcSkill">
						<input type="checkbox" id="nowTV" name="nowTV" value="nowTV">nowTV
						<input type="checkbox" id="pcd" name="pcd" value="pcd">PCD
						<input type="checkbox" id="lts" name="lts" value="del">DEL
						<input type="checkbox" id="eye" name="eye" value="eye">eye
						<input type="checkbox" id="allLob" name="allLob" value="allLob">ALL LOB<br>
						<input type="checkbox" id="languageZh" name="language" value="Chinese">Chinese
						<input type="checkbox" id="languageEn" name="language" value="English">English<br>
					</div>	
				</td>
				
				<td>
				  <div id="qcStatus">
						<select name="Status">
						  <option value="active">Active</option>
						  <option value="inactive">InActive</option>
						</select>
					</div>
				</td>
				
				<td><div id="todayOSData">		
					12
				</div></td> 
				
				<td><div id="pSevenDayOSData">		
					60
				</div></td>
				
				<td><div id="totalOSData">
					170
				</div></td> 
				
				<td>
					<div id="reassignBtn" class="orderSearchButton">
				   		<input type="button" value="Release" id="assignButton1" onclick="javascript:reAssignQCorder();">
				   		<input type="button" value="Remove" id="rmButton" onclick="javascript:reAssignQCorder();">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</table>


<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>