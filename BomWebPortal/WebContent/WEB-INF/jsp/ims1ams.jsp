<%@ include file="header.jsp" %>
<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" 
    rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>    


<link href="css/ims1ams.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="css/dataTable/jquery.dataTables.css" >
<link rel="stylesheet" type="text/css" href="css/custom-theme/jquery.treetable.theme.default.css" >
	
<script>
$(document).ready(function() {		
	var bTable = $(".1AMSList").dataTable({
		"sScrollY": "140px",
		"iDisplayLength" : -1, 
		"bPaginate": false,
		"sDom": 'lrt',
		"bSort" : false 
	});
	
	$("#tabs a").removeClass("ui-widget-content a").css('color', '#222222');
	$("#tabs a").addClass("table_button").css('color','#f3f3f3');		
	$("#tabs a").hover(function() {
	        $(this).css("cursor","hand");
	        $(this).css("cursor","pointer");
	});
	
	$("#header").hide();
	
});


function displayDetail(fsa, id, line_type){
	
	var id = id;
	
	if ($('#' + id).hasClass('detail_picked')) {
		return false;
	}
	
	$.ajax({
		url : 'ims1amsenquirydtl.html?fsa=' + fsa,
		type : 'POST',
		dataType : 'json',
		timeout : 60000,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if(textStatus=='parsererror'){
				alert("Your session has been timed out, please re-login.");	
			}else if(textStatus == 'timeout'){
		    	alert("Server response timeout, please try again.");
		    }					
		},
		success : function(msg) {
			$.each(eval(msg), function(i, item) {
				
				document.getElementById("recordDtlFSA").value = fsa;
				document.getElementById("recordDtlOrderNo").value = item.Ocid;
				document.getElementById("recordDtlOrderType").value = item.OrderType;
				document.getElementById("recordDtlSRDate").value = item.SrdDate;
				
				if (item.IsPCD == 'Y'){
					document.getElementById("recordDtlPCDBox").checked=true;
				}else{
					document.getElementById("recordDtlPCDBox").checked=false;
				}
				
				if (item.IsStandAloneTV == 'Y'){
					document.getElementById("recordDtlStandANOWBox").checked=true;
				}else{
					document.getElementById("recordDtlStandANOWBox").checked=false;
				}
				
				
				if (item.IsStandAloneEYE == 'Y'){
					document.getElementById("recordDtlStandAEYEBox").checked=true;
				}else{
					document.getElementById("recordDtlStandAEYEBox").checked=false;
				}
				
				if (item.IsEYE == 'Y'){
					document.getElementById("recordDtlEYEBox").checked=true;
				}else{
					document.getElementById("recordDtlEYEBox").checked=false;
				}
				
				if (item.IsEYEX == 'Y'){
					document.getElementById("recordDtlEYEXBox").checked=true;
				}else{
					document.getElementById("recordDtlEYEXBox").checked=false;
				}
				
				if (item.IsTV == 'Y' && item.Is1L1B[0] == 'Y'){
					document.getElementById("recordDtl1L2BBox").checked=true;
					document.getElementById("recordDtl1L1BBox").checked=false;
				}else if (item.IsTV == 'Y' ){
					document.getElementById("recordDtl1L2BBox").checked=false;
					document.getElementById("recordDtl1L1BBox").checked=true;
				}else{
					document.getElementById("recordDtl1L2BBox").checked=false;
					document.getElementById("recordDtl1L1BBox").checked=false;
				}
				/*
				if (item.Is1L1B[0] == 'Y'){
					document.getElementById("recordDtl1L1BBox").checked=true;
				}else{
					document.getElementById("recordDtl1L1BBox").checked=false;
				}
				
				if (item.Is1L1B[2] == 'Y'){
					document.getElementById("recordDtl1L2BBox").checked=true;
				}else{
					document.getElementById("recordDtl1L2BBox").checked=false;
				}
				*/
				if (item.IsPCDTV == 'Y'){
					document.getElementById("recordDtlPCDTVBox").checked=true;
				}else{
					document.getElementById("recordDtlPCDTVBox").checked=false;
				}
				
				if (item.IsStandAloneEasyWatch == 'Y'){
					document.getElementById("recordDtlStandAEWBox").checked=true;
				}else{
					document.getElementById("recordDtlStandAEWBox").checked=false;
				}
				
				
				if (item.IsILRC == 'Y'){
					document.getElementById("recordDtlIsILRCBox").checked=true;
				}else{
					document.getElementById("recordDtlIsILRCBox").checked=false;
				}
				
			
		    document.getElementById("recordDtlPCDCreditStatus").value = item.pCDAccStatus[0];
			document.getElementById("recordDtlVICreditStatus").value = item.vIAccStatus;
			document.getElementById("recordDtlLineType").value = line_type;	
			
			});
			
			document.getElementById("detailbox").style.display= 'block';
			document.getElementById("s_line_text").style.display= 'block';	
			
			 $("#tabs a").hover(function() {
	       		 $(this).css("cursor","hand");
	        	$(this).css("cursor","pointer");
	    	});
			
			
			$("#tabs a").removeClass("detail_picked");
			/////////////////////////////////////////////////////
			$('#' + id).hover(function() {
	       		 $(this).css("cursor","default");
	        	
	    	});
			
			$('#' + id).addClass( "detail_picked" );
			
		
		}
		
	});
	

	
	//openModalDialog("");
	
}
</script>

    
<table width="100%" height="30">
	<tr>
		<td class="contenttextgreen" align="center">
		 	1AMS Enquiry
		</td>
	</tr>
</table>



<form:form method="POST" name="ims1amsForm" commandName="Ims1amsUI">



	<div class="paper_w2">	
			<div id="tabs" style="width:100%; height:10%; border: 1px solid #aaaaaa;" class="paper_w">
							
					<table class="paper_no 1AMSList" cellspacing="0" width="100%" border="0">
						<thead align="center">
							<tr align="center">
								<th>Flat</th>
								<th>Floor</th>	
								<th>Hse/Lot</th>
								<th>Status</th>
								<th>FSA</th>
								<th>Customer Name</th>
								<th>Lines Type</th>
								<th></th>
							</tr>
						</thead>
						
						
						<tbody>
						
						<c:forEach var="record" items="${Ims1amsUI.ims1AMSFSAInforecords}" varStatus="i">
							<tr align="center">
								<td>${record.flat}</td>
								<td>${record.floor}</td>	
								<td>${record.HLotNO}</td>								
								<td>${record.status}</td>
								<td>${record.FSA}</td>
								<td>${record.custName}</td>
								<td>${record.lineType}</td>				
								<td><a id=funBtn_${i.index} class="table_button" onclick="displayDetail('${record.FSA}', this.id, '${record.lineType}');">Details</a></td>						
							</tr>
						</c:forEach>
						</tbody>
				
					</table>
					
					<div id="s_line_text" style="display:none";>1AMS Enquiry Details</div>
					
					<table border='0' id="detailbox" width="100%" style="display:none";>
						
						<tr align="left">
							<td align="left">FSA</td>
							<td><input id="recordDtlFSA" path="" value="" readonly/></td>
							<td align="left">Order #</td>
							<td><input id="recordDtlOrderNo" path="" value="" readonly/></td>
							<td align="left">Order Type (I/C)</td>
							<td><input id="recordDtlOrderType" path="" value="" readonly/></td>
							<td align="left">SR Date</td>
							<td><input id="recordDtlSRDate" path="" value="" readonly/></td>
							<td colspan="2">&nbsp;</td>	
						</tr>
				
						<tr align="left">
							<td colspan="10">Existing Service</td>		
						</tr>
				
						<tr align="left">	
							<td align="center"><input type="checkbox"  id="recordDtlPCDBox" path="" value="" disabled="disabled"/></td>
							<td align="left">PCD</td>
							<td align="center"><input type="checkbox"  id="recordDtlStandANOWBox" path="" value="" disabled="disabled"/></td>
							<td align="left">Standalone nowTV</td>
							<td align="center"><input type="checkbox" id="recordDtlStandAEYEBox" path="" value="" disabled="disabled"/></td>
							<td align="left">Standalone eye</td>
							<td colspan="2">&nbsp;</td>	
						</tr>
				
						<tr align="left">
							<td colspan="10">Existing VAS</td>		
						</tr>
				
						<tr align="left">
							<td align="center"><input type="checkbox"  id="recordDtlEYEBox" path="" value="" disabled="disabled"/></td>
							<td align="left">eye/eye3A</td>
							<td align="center"><input type="checkbox"  id="recordDtlEYEXBox" path="" value="" disabled="disabled"/></td>
							<td align="left">eye2/eye1.5Aeye2A</td>
						</tr>
				
						<tr align="left">
							<td align="center"><input type="checkbox"  id="recordDtl1L1BBox" path="" value="" disabled="disabled"/></td>
							<td align="left">TV-1L1B</td>
							<td align="center"><input type="checkbox"  id="recordDtl1L2BBox" path="" value="" disabled="disabled"/></td>
							<td align="left">TV-1L2B</td>
							<td align="center"><input type="checkbox"  id="recordDtlPCDTVBox" path="" value="" disabled="disabled"/></td>
							<td align="left">PCD1/2/3 TV</td>
							<td align="center"><input type="checkbox"  id="recordDtlStandAEWBox" path="" value="" disabled="disabled"/></td>
							<td align="left">eye1/2/3 TV</td>			
						</tr>
				
						<tr align="left">
							<td align="right" colspan="3">Line Type</td>
							<td><input id="recordDtlLineType" path="" value="" readonly/></td>		
						</tr>
				
				
						<tr align="left">
							<td colspan="10">MISC</td>		
						</tr>
				
						<tr align="left">
							<td colspan="2" style="padding-left:10px">PCD Credit Status</td>
							<td colspan="2" style="padding-left:10px">VI Credit Status</td>		
						</tr>
				
						<tr align="left">
							<td colspan="2" style="padding-left:10px"><input id="recordDtlPCDCreditStatus" path="" value="" readonly/></td>
							<td colspan="2" style="padding-left:10px"><input id="recordDtlVICreditStatus" path="" value="" readonly/></td>		
							<td align="center"><input type="checkbox" id="recordDtlIsILRCBox" path="" value="" disabled="disabled"/></td>
							<td align="left">ILRC</td>
						</tr>
			
					</table>
							
			</div>
		</div>

</form:form>