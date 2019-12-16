<%if ("Y".equals(request.getParameter("dM"))){%>
	<%@ include file="/WEB-INF/jsp/dialogheader.jsp"%>
<%}else{%>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%} %>

<%-- <%@ include file="imsvalidwindow.jsp" %> --%>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%
Map<String, List<CodeLkupDTO>> codeLkupList = LookupTableBean.getInstance().getCodeLkupList();
List<CodeLkupDTO> dtos = codeLkupList.get("IPAD_VERSION");
String ipadVersion = "";
if (dtos != null && dtos.size() > 0) {
	ipadVersion = dtos.get(0).getCodeId();
}

//debug ipad version
//ipadVersion = "2.3";

String scheme = ConfigProperties.getPropertyByEnvironment("orddoc.ipad.uri.scheme");
%>

 <%@ include file="imsloadingpanel.jsp" %>

<script language="Javascript">	
	function formSubmit(){
		//window.location = "basketdetails.html";
		//document.summary.submit();
	}
	
	function CapturePC(){
		var url = "imsdoccapture.html?orderId=${imsDoneUI.orderId}&disMode=${imsDoneUI.disMode}&SignOffed=true";
		window.open(url, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
	}
	
	function CaptureiPad(){
		var link = "<%=scheme %>://${imsDoneUI.orderId}/${salesUserDto.username}/${currentSessionId}/<%=ipadVersion%>";
		var url = "imsdoccapture.html?orderId=${imsDoneUI.orderId}&SignOffed=true&disMode=" + $('input[name=disMode]:checked').val()+"&iPadLink="+link;
		window.open(url);
	}
	
	//kinman
	function dummyprintLangForm(){
		if (document.getElementById("langPreference").value=='ENG'){
			window.location.replace('imsreport.html?pdfLang=en', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			}else {
			window.location.replace('imsreport.html?pdfLang=zh', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
			} 
		}
	/*
	function assignQCorder(){
	
		document.imsAssignForm.submit();
		alert("I'm ISIS");  
		parent.$("#qcDialog").dialog('close');
		//setTimeout(function(){parent.$("#qcDialog").dialog('close');},200);
	}*/
	
	
	function assignQCorder(staffId) {
		//var orderId = parent.$("#qcDialog").dialog('title'); 
		
//		alert("sysf is : " + sysf); 
		submitShowLoading(); 
		$.ajax({
			url : 'imsqcassignajax.html?tp=${action}'+'&orderIds=${orderids}'+'&staffId='+staffId+'&sysf=${sysf}',
			type : 'GET',
			dataType : 'json',
			timeout : 25000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			     if(textStatus=='parsererror'){
					alert("Your session has been timed out, please re-login."); 
			     } else {
			    	 alert("Error loading!");
			     }
			},
			success : function(msg) {
				//$.each(eval(msg), function(i, item) {
					//parent.$("#qcDialog").dialog('close');
					//alert("Success!");
					 
				//}); 
				parent.location.reload();
				
				
			}
		});
		
	}
	
	$(document).ready(function() {	
		
		
	});


</script> 


<form:form name="imsAssignForm"  commandName="qcImsAssignUI" method="POST">
<br><br>

   <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="1" rules="none">
		<tr>
			<td class="table_title" >Assign order ID to QC staff</td>
		</tr>
		</table>
		
		<table width="100%" border="0" cellspacing="1" class="contenttext">
		<thead>
			<tr class="contenttextgreen">
					    <td class="table_title">Staff Number</td>
						<td class="table_title">Staff Name</td>
						<td class="table_title">Skill Set</td>
						<td class="table_title">Today Outstanding QC order</td>
						<td class="table_title">Past 7 days Outstanding QC order</td>
						<td class="table_title">Total Outstanding QC order</td>
						<td class="table_title">Assign</td>
						
			</tr>
		</thead>
		
	<tbody>
	     <c:forEach items="${qcStaffInfo}" var="qcStaffInfo" varStatus="seq">
			<tr>
				<td>${qcStaffInfo.qcStaffId}</td>
				<td>${qcStaffInfo.staffName}</td>
			    <td>LOB:   ${qcStaffInfo.skillSet}</td>
				<td>${qcStaffInfo.todayOsOrders}</td>
				<td>${qcStaffInfo.todayAssignedOrders}</td>
				<td>${qcStaffInfo.past7daysAssignedOrders}</td>
				<td>
					<div class="orderSearchButton">               
				   		<input type="button" value="Assign" id="assignButton${seq.index}" name="assignButton${seq.index}" onclick="javascript:assignQCorder('${qcStaffInfo.qcStaffId}');">
				   </div>
				</td>
			</tr>
		
			
		</c:forEach>
 	</tbody>
 </table>
</form:form>

	
<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>