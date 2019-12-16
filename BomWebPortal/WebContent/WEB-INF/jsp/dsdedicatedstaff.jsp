<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br><br></div>

<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />    
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.colReorderWithResize.plugin.js"></script>
<script type="text/javascript" src="js/iepngfix_tilebg.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialogAndy.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script> 
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
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

<script type="text/javascript">

$(document).ready(function() {
			    	// updateAllLobInd();
			    	
					$("#staffId").blur(function(e){
						e.target.value = e.target.value.toUpperCase(); 
					}); 
					$("#staffId").keyup(function(e){ 
						e.target.value = e.target.value.toUpperCase(); 
					});
});

function updateStaffList(btn){
	 var rowID = $(btn).attr("id").substr(9); 
	 
	 if (confirm("Confirm to upate staff list?")) {  

		var staffListInputShow = "staffListInput"+rowID;		
		var staffListNameShow = "staffListName"+rowID;		

		document.getElementById("staffListInput").value = document.getElementById(staffListInputShow).value;		
		document.getElementById("staffListName").value = document.getElementById(staffListNameShow).value;
		
		// alert(document.getElementById("staffListInput").value);		
		// alert(document.getElementById("staffListName").value);	
		
		if($('#staffListInput').val().length == 0) {
			alert("Please input staff list.");
			return;
		} else {
			document.submitForm.submit();
		}
	 }
}
</script>
<form:form name="submitForm" action="dsdedicatedstaff.html" method="POST" commandName="dsDedicatedStaffUI">

 	<spring:hasBindErrors name ="dsDedicatedStaffUI">
		<div class="ui-widget">
			<div class="ui-state-error ui-corner-all" style="padding: 0.1em;"> 
			<p>
				<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
				<div class="contenttext" style="font-weight:bold;"><form:errors path="*"/></div>
			</p>
			</div> 
		</div>
	</spring:hasBindErrors>

	<table id="	" width="100%"  class="tablegrey">
	  <tr>
		<td bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" rules="none">
			<tr>
				<td class="table_title">Update Dedicated Staff List</td>
			</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" class="contenttext">
			<thead>
				<tr class="contenttextgreen">
					<td class="table_title">Staff List Name<input type="hidden" id="staffListName" name="staffListName"/></td>
					<td class="table_title">Staff List<input type="hidden" id="staffListInput" name="staffListInput"/></td> 
					<td class="table_title">Assigned Offer/ VAS/ Gift</td>
					<td class="table_title"></td>
				</tr>
			</thead>
			<tbody>
		
			<c:forEach items="${dsDedicatedStaffUI.staffList}" var="s" varStatus="i"> 	
				<tr id="dsrow${i.index}">
						<td><div id="staffListNameData">		
							${s.staffListName} <input type="hidden" id="staffListName${i.index}" name="staffListName${i.index}" value="${s.staffListName}">
						</div></td>
						<td><div id="staffIdListData" align="top">
							<textarea id="staffListInput${i.index}" rows="8" cols="55">${s.staffListInput}</textarea>										
						</div></td>
						<td valign="top"><div id="offerVasGiftData">
							Program Offer: <br>
							<c:forEach items="${s.offerDescList}" var="offerDesc">		
								${offerDesc}<br>
							</c:forEach>						
							 <br>
							
							VAS:<br>
							<c:forEach items="${s.vasDescList}" var="vasDesc">		
								${vasDesc}<br>
							</c:forEach>						
							<br>
							
							Gift:<br>
							<c:forEach items="${s.giftDescList}" var="giftDesc">		
								${giftDesc}<br>
							</c:forEach><br>
						</div></td> 
						<td>
							<div id="updateBtn" class="orderSearchButton">
						   		<input type="button" value="Update" id="updButton${i.index}" onclick="javascript:updateStaffList(this);">
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