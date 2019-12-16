<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ include file="imsloadingpanel.jsp" %>
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

<script>

$(document).ready(function(){
	$("#header").remove();
	
	}); 



function doQC(action,isActiveQCstaff){

// 	if(!checkRemarkIsEng('qcFinding')) return false;

	if(isActiveQCstaff){
		$("#action").val(action);
		$("#orderId").val("${orderid}");
		submitShowLoading();
		document.form.submit();	
	} else {
		alert("You are not authorized to press the button");
	return;
	}
	
}	

function validationCQ(action,isActiveQCstaff){
	if(isActiveQCstaff){
		if($("#reasonCQDesc").val() == "")
			alert( "Please choose a reason for cannot QC" );
		else doQC(action,isActiveQCstaff);
	}else{
		alert("You are not authorized to press the button");
		return;
	} 
}

function validationF(action,isActiveQCstaff){
	
	if(isActiveQCstaff){	
		if($("#reasonFailDesc").val().length == 0)
			alert( "Please choose a reason for Failed QC" );
		else doQC(action,isActiveQCstaff);
	}else{
		alert("You are not authorized to press the button");
		return;
	} 
}

function updateQcRemark() {
	//var orderId = parent.$("#qcDialog").dialog('title'); 
	//alert("staffId is : " + staffId); 
	//alert("reMARKS is :"+ $("#qcFinding").val());
	
// 	if(!checkRemarkIsEng('qcFinding')) return false;
	
	$.ajax({
		url : 'imsqcprocessajax.html',
		type : 'POST',
		dataType : 'json',
	    data: { "oid": '${orderid}', "remark": $("#qcFinding").val() },
		timeout : 25000,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		     if(textStatus=='parsererror'){
				alert("Your session has been timed out, please re-login."); 
		     } else {
		    	 alert("Error loading!");
		     }
		},
		success : function(msg) {
			$.each(eval(msg), function(i, item) {
				//parent.$("#qcDialog").dialog('close');
				//parent.location.reload();
			});
		}
	});
}


$(function(){
	var f=$('#orderFrame');
	f.load(function(){ 
		f.contents().find("#header").find('a').hide(); 
		var reelectsrd = f.contents().find("#srdAvailble").val();
		if(reelectsrd == "N"){
		$("#reSelectSrd").replaceWith( '<input value="Y" disabled="true"'+'/>' );
		}else {$("#reSelectSrd").replaceWith( '<input value="N" disabled="true"'+'/>' );}
			
	});
});

function   checkRemarkIsEng(id)   {   
	  var   noCharOver127   =   true;     
	  var   s   =   document.getElementById(id).value;
	  for (var   i=0;   i<s.length;   i++)   {           
	  if (s.charCodeAt(i)>127)
		  noCharOver127 = false; 
	  }
	  if (noCharOver127){
		  $('#'+id+'RemarkError').hide();
		  return true;
	  }
	  else {
		  $('#'+id+'RemarkError').show();
		  return false;
	  }
	} 
</script>

<body> 
<form:form method="POST" name="form" commandName="ImsDsQcProcessDetailUI">

<div id="orderdetail">
	<br/>
	<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF" "="">
		<tr>
		   		<td class="table_title" colspan="6">Order Summary</td>
	       	</tr>
	</table>
	<br/>
	<c:choose>
		<c:when test="${fn:containsIgnoreCase(orderType, 'NTV')}">
			<iframe id="orderFrame" src="${ntvDomain}ntvorderdetail.html?orderId=${orderid}&_al=true" scrolling="yes" width="100%" height="280px"></iframe>  			
		</c:when>
		<c:otherwise>
			<iframe id="orderFrame" src="imsorderdetail.html?orderId=${orderid}&reqType=QC" scrolling="yes" width="100%" height="280px"></iframe>  
		</c:otherwise>
	</c:choose>
</div>

<div id="amendmentHistory">
	<br/>
	<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF" "="">
		<tr>
		   		<td class="table_title" colspan="6">Amendment History</td>
	       	</tr>
	</table>
	<br/>
	<iframe src="qcimsamendremarks.html?orderId=${orderid}" scrolling="yes" width="100%" height="280px"></iframe>  
</div>

<div id="qcAction">
	<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF" "="">
    	    <tbody><tr>
         		<td colspan="6">&nbsp;</td>
        	</tr>
    	    
    	    <tr>
		   		<td class="table_title" colspan="6">QC Action</td>
	       	</tr>
		
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>

			<tr>
				<th align="left">Order LOB:</th>
				<td colspan="4" align="left">
					IMS
				</td>
			</tr>
			<!-- <tr>
				<th align="left">Address Matching:</th>
				<td colspan="4" align="left">
					()
				</td>
			</tr>  -->
			<tr>
				<th align="left">Assign Date:</th>
				<td align="left">
					<form:input path="assignDate" disabled="true"/>
				</td>
				<th align="left">Assignee:</th>
				<td align="left">
				    <form:input path="assignee" disabled="true"/>
<!-- 					<input name="installAddress.quickSearch" type="text" value="" autocomplete="off" class="ac_input" value="1322593"> -->
				</td>
				<th align="left"></th>
			</tr>
			<tr>
				<th align="left">QC Date Time:</th>
				<td align="left">
				<form:input path="qcDateTime" disabled="true" />
<!-- 					<input name="installAddress.quickSearch" type="text" value="" autocomplete="off" class="ac_input"> -->
				</td>
				<th align="left">Re-Select SRD:</th>
				<td align="left">
					<img id="reSelectSrd"  src="./images/imsalertloader.gif" style="width: 10px;top: 1px;position: relative;left: 3px;"/>
				</td>
			</tr>
			<tr>
				<th align="left" >Is Pre-Installation:</th>
				<td align="left">
						<form:input path="preInstallInd" disabled="true" />
				</td>
			</tr>
			
			<tr>
				<th align="left">QC Calling Time:</th>
				<td align="left">

 				<form:textarea path="qcCallTime" rows="6" cols="40" />  
<!-- 					<input type="button" id="qcCallingTime" value="add" onclick=""> -->
				</td>
				
				
				<th align="left">Other Orders created by<br>
								 same customer within T-7 <br>
								 to T+7 days:
				</th>
				<td align="left">
					<form:textarea path="sameCustOrder" rows="6" cols="40" readonly="true"/>  
				</td>
			</tr>	
			
			<tr>
				<th align="left">QC Finding Details:</th>
				<td colspan="4" align="left">
<!-- 					<textarea rows="3" cols="40"></textarea>   -->
 				<form:textarea id ="qcFinding" path="qcFinding" rows="6" cols="40" />  
					<input type="button" id="addQcFinding" name="addQcFinding" value="Add" onclick="javascript:updateQcRemark()">
				</td>
			</tr>
			
			<tr>
				<th align="left"></th>
				<td colspan="4" align="left" class="error">
					<span id="qcFindingRemarkError" style="display: none">Please use English input for QC Finding Details.</span>
				</td>
			</tr>	
				
			<tr>
				<th align="left">Order District:</th>
				<td colspan="4" align="left">
					<form:select path="orderDistict">  
        				<option></option>
        				<option>HONG KONG</option>
        				<option>KOWLOON</option>
        				<option>NEW TERRITORIES</option>
        			</form:select>
				</td>
			</tr>	
			<tr>
				<th align="left">Order Place:</th>
				<td colspan="4" align="left">
				<form:input path="orderPlace" />
<!-- 					<input name="installAddress.quickSearch" type="text" value="" autocomplete="off" class="ac_input"> -->
				</td>
			</tr>
			</tbody>
			</table>
			
	<div class="default" style="margin-top: 30px;" align="right">
	<!-- added by andy--> 
	<a id="qcPass" class="nextbutton" onclick="doQC('PASS',${isActiveQCstaff})">Pass</a> 
<!-- 	<a id="qcCannot" class="nextbutton" href="#" onclick="doQC('CANNOTQC')">Cannot QC</a> -->
	<a id="qcCannot" class="nextbutton" onclick="validationCQ('CANNOTQC',${isActiveQCstaff})">Cannot QC</a>
	<form:select path="reasonCQDesc" id="reasonCQDesc" items="${reasonCQList}" />
	
	<a id="qcFail" class="nextbutton" onclick="validationF('FAIL',${isActiveQCstaff})">Fail</a>
	<form:select path="reasonFailDesc" id="reasonFailDesc"  items="${reasonFailList}" />
	
	<a id="qcRNA" class="nextbutton"  onclick="doQC('RNA',${isActiveQCstaff})">RNA</a>
	
	<a id="qcPass_without_qc" class="nextbutton" onclick="doQC('PASSWITHOUTQC',${isActiveQCstaff})">Proceed order without QC</a> 
	
	</div>
</div>


<form:hidden path="action"/>

</form:form>
</body>



					
					