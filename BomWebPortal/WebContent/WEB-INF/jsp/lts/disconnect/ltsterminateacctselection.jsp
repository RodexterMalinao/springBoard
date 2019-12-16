<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/dataTable/table.jui.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<!-- <script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script> -->
<script type="text/javascript" src="js/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.1.10.0.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>

<script language="javascript">
	
	$(document).ready(
			function() {
			$("#acctTable").dataTable({
					 /* "sDom": "Rlfrtip",  */
					"bAutoWidth": false,
					"bSort" : true,
					"bLengthChange" : false,
					"bInfinite" : true,
					"bJQueryUI" : true,
					"oLanguage" : {
						"sZeroRecords" : "NO Record Found / All Services under Billable Accounts are being Removed",
						"sInfo" : "Total Records: _TOTAL_",
						"sInfoEmpty" : "NO Record Found",
						"sInfoFiltered" : "(filtered from _MAX_ total records)",
						"sSearch" : "Filter: "
					}, 
					"aaSorting": [[ 3, "desc" ]],
					"aoColumns" : [ 
					                null, 
					                null, 
					                null, 
									null,
									null
					              ]
					});

				// Setup - add a text input to each footer cell
			   $('#acctTable .searchBar').each( function () {
			        var title = $('#acctTable th').eq( $(this).index() ).text();
			        var search_title;
			        if(title=='<spring:message code="lts.acq.selectAccount.accountNumber" htmlEscape="false"/> :')
			        {
			        	search_title = '<spring:message code="lts.acq.selectAccount.searchAccountNumber" htmlEscape="false"/>';
			        }
			        
			        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
			    });				
				
				 // DataTable
          	    var table=$("#acctTable").DataTable();
				
			    // Apply the filter
			    $("#acctTable .searchBar input").keyup( function () {
			        table
			            .column( $(this).parent().index()+':visible' )
			            .search( this.value )
			            .draw();
			    } );			  
			    
			    $("#searchServiceNoBtn").click(function(){
			    	
			    	if(!$("#accountNum").val() && !$("#serviceNumber").val() && !$("#serviceType").val())
			    	{
			    		alert('<spring:message code="lts.acq.selectAccount.plsEnterAccNumSerTypeOrSerNum" htmlEscape="false"/>');
			    		return;
			    	}
			    	else
			    	{
			    		if(!$("#accountNum").val())
			    		{
			    			if($("#serviceType").val() && !$("#serviceNumber").val())
			    			{
			    				alert('<spring:message code="lts.acq.selectAccount.plsEnterAccNumOrSerNum" htmlEscape="false"/>');
					    		return;
			    			}
			    			else if(!$("#serviceType").val() && $("#serviceNumber").val())
			    			{
			    				alert('<spring:message code="lts.acq.selectAccount.plsSelectSerType" htmlEscape="false"/>');
					    		return;
			    			}
			    		}
			    		else if( $("#accountNum").val().length != 14 )
		    			{
		    				alert('<spring:message code="lts.acq.selectAccount.plsEnterValAccNum" htmlEscape="false"/>');
				    		return;
		    			}
			    		
			    	}
			    	
			    	if( $("#serviceNumber").val() )
			    	{
			    		if( $("#serviceNumber").val().replace(/\b(0(?!\b))+/g, "").length != 8 )
				    	{
			    			alert('<spring:message code="lts.acq.selectAccount.plsEnterValSerNum" htmlEscape="false"/>');
				    		return;
				    	}
			    	}
			    	
			    	
			    	table
		            .column( '1 :visible' )
		            .search( $("#accountNum").val() )		            
		            .column( '3 :visible' )
		            .search( $("#serviceType").val() )		            
		            .column( '2 :visible' )
		            .search( $("#serviceNumber").val() )
		            .draw();
			    	
			    	$("#accountSelectionDiv2").show();
			    });
			    
			    $("#submit").click(function(event) {
                 if ($('input[name="selectedAccount"]:checked').val()==undefined) {
	                alert('<spring:message code="lts.acq.selectAccount.plsSelectAccount" htmlEscape="false"/>');
                 } else {				    
					event.preventDefault();
					if($("#errorMsg").val() != ''){
						alert($("#errorMsg").val());
						return;
					}
					
					if((($("input[name=selectedAccount]:checked").val() === null || $("input[name=selectedAccount]:checked").val() === undefined)) ){
						alert('<spring:message code="lts.acq.selectAccount.plsSelectAccount" htmlEscape="false"/>');
					}
					else{
						var acctIdx = $("input[name=selectedAccount]:checked").val();
						var acctNum = $("#acctLabel"+acctIdx).html();
						var ffpIdx = $("#ffpIdx").val();
						opener.putSelectedAcct(ffpIdx, acctNum);
						window.close();
					}
                 } 	
				});				

			    checkOption();
			    
	});

	function checkOption(){
		$("#accountSelectionDiv2").show();
		$("#accountSelectionDiv2").show();
	}
	
</script>

<style type="text/css">
.orderSearch {
	background-color: white;
	border: solid 2px #C0C0C0
}

.fg-toolbar {
	background-color: #5D9DE4;
	border: 0px;
}

table.dataTable thead th {
	border-bottom: 0 solid #000000;
}

.ui-state-default,.ui-widget-content .ui-state-default,.ui-widget-header .ui-state-default
	{
	background: none repeat scroll 0 0 #5D9DE4;
	border: 1px solid #5D9DE4;
	color: #FFFFFF;
	font-weight: normal;
}
</style>

<title>Account Selection</title>
</head>
<body>
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<input type="hidden" id="ffpIdx" value="${ffpIdx}" />
	<input type="hidden" id="errorMsg" />
	<table width="100%" class="paper_w2 round_10">
		<tr>
			<td>

				<div id="accountSelectionDiv2">

					<table id="acctTable" width="100%" border="0" cellspacing="5"
						class="table_style_sb  paper_w2">

						<thead>
							<tr>
								<th width="3%">&nbsp;</th>
								<th width="17%" align="center" style="color: Black"><b><spring:message code="lts.acq.selectAccount.accountNumber" htmlEscape="false"/></b></th>
								<th width="20%" valign="top" class="table_style_sb"><b><spring:message code="lts.acq.selectAccount.relatedServiceNumber" htmlEscape="false"/></b></th>
								<th width="30%" valign="top" class="table_style_sb"><b><spring:message code="lts.acq.selectAccount.serviceType" htmlEscape="false"/></b></th>
								<th width="30%" valign="top" class="table_style_sb"><b><spring:message code="lts.acq.selectAccount.chargeType" htmlEscape="false"/></b></th>
							</tr>

							<tr class="searchBarTr">
								<td width="3%">&nbsp;</td>
								<td class="searchBar" width="17%">&nbsp;</td>
								<td class="searchBar" width="20%">&nbsp;</td>
								<td class="searchBar" width="30%">&nbsp;</td>
								<td class="searchBar" width="30%">&nbsp;</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${acctList}" var="account" varStatus="status">
								<tr>
									<td><input type="radio" id="selectedAccount${status.index}" name="selectedAccount" value="${status.index}" /></td>
									<td><label id="acctLabel${status.index}">${account.acctNum}</label></td>
									<td align="center" class="itemPrice">${account.srvNum}</td>
									<td align="center" class="itemPrice">${account.serviceType}</td>
									<td align="center" class="itemPrice">${account.displayChargeType}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>

			</td>
		</tr>

	</table>
	<table width="100%" cellspacing="0" border="0" id="submitButton" class="contenttext">
	<tbody><tr>
		<td align="center">
			<div class="button">
				<a id="submit" href="#">Continue</a>
			</div>
		</td>
	</tr>
	</tbody>
	</table>
</body>
</html>