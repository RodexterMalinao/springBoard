<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="6" />
</jsp:include>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>

<!-- <link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" /> -->
<link type="text/css" href="css/dataTable/table.jui.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<!-- <script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script> -->
<script type="text/javascript" src="js/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.1.10.0.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<!-- <script type="text/javascript" src="js/jquery.dataTables.colReorderWithResize.plugin.js"></script> -->
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

<c:set var="isOrderSubmitted"
	value="${sessionScope.sessionLtsOrderCapture.sbOrder != null}" />
<form:form method="POST" action="#" id="acctselectform"
	name="ltsAcctSelectForm" commandName="ltsacctselect">
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<input type="hidden" id="errorMsg" />
	<table width="100%" class="paper_w2 round_10">
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="1">
					<tr>
						<div id="s_line_text"><spring:message code="lts.acq.selectAccount.selectAccount" htmlEscape="false"/></div>
					</tr>
				</table>

				<table width="100%" border="0" cellspacing="4" cellpadding="6"
					class="contenttext">
					<tr>
					<c:if test="${!isDelFree}">
						<td width="25%"><div align="left">
								<b><form:radiobutton path="newAccount" value="false"/>
									<spring:message code="lts.acq.selectAccount.selectExistingAccount" htmlEscape="false"/></b>
							</div></td>
					</c:if>
						<td><div align="left">
								<b><form:radiobutton path="newAccount" value="true"
										disabled="${isNowDrgDownTime}" /> <spring:message code="lts.acq.selectAccount.createaNewAccount" htmlEscape="false"/></b>
							</div></td>
					</tr>

				</table>

				<div id="accountSelectionDiv" style="display: none;">

					<table width="100%" border="0" cellspacing="5" class="paper_w2">

						<tr>
							<td width="10%">&nbsp;</td>
							<td width="15%">&nbsp;</td>
							<td width="10%">&nbsp;</td>
							<td width="15%">&nbsp;</td>
							<td width="10%">&nbsp;</td>
							<td width="40%">&nbsp;</td>
						</tr>

						<tr>
							<td align="right"><b><spring:message code="lts.acq.selectAccount.accountNumber" htmlEscape="false"/> :</b></td>
							<td><form:input id="accountNum" path="accountNum" /></td>
							<td align="right"><b><spring:message code="lts.acq.selectAccount.serviceType" htmlEscape="false"/> :</b></td>
							<td><form:select id="serviceType" path="serviceType">
									<form:option value="" />
									<form:option value="TEL" />
									<form:option value="MOB" />
								</form:select></td>

							<td align="right"><b><spring:message code="lts.acq.selectAccount.serviceNumber" htmlEscape="false"/> </b></td>

							<td><form:input id="serviceNumber" path="serviceNumber" />
								<input type="button" id="searchServiceNoBtn" value="<spring:message code="lts.acq.selectAccount.search" htmlEscape="false"/>">
							</td>
						</tr>

					</table>

				</div>

			</td>
		</tr>

		<tr>
			<td>

				<div id="accountSelectionDiv2" style="display: none;">

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
									<td><form:radiobutton id="selectedAccount"
											path="selectedAccount" value="${status.index}" /></td>
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
	<br>
	<c:if test="${isNowDrgDownTime}">
		<div id="warning_drgDownTime" class="ui-widget"
			style="visibility: visible;">
			<div class="ui-state-highlight ui-corner-all"
				style="padding: 0 .7em; margin-left: 25%; width: 50%;">
				<p>
					<span class="ui-icon ui-icon-info"
						style="float: left; margin-right: .3em;"></span>
				</p>
				<div id="drgDownTimeMsg" class="contenttext">
					<spring:message code="lts.acq.address.DRGDowntime" htmlEscape="false"/>:<br />
					<ul>
						<li><spring:message code="lts.acq.selectAccount.notAllowCreateCust" htmlEscape="false"/></li>
						<li><spring:message code="lts.acq.selectAccount.notAllowCreateAcc" htmlEscape="false"/></li>
						<li><spring:message code="lts.acq.selectAccount.allowSelectDNfromPool" htmlEscape="false"/></li>
					</ul>
				</div>
				<p></p>
			</div>
		</div>
	</c:if>

	<br>
	<br>
	<table width="100%" border="0" cellspacing="0" class="paper_w2">
		<tr>
			<td align="right"><br /> <a id="submit" href="#"><div
						class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a></td>
		</tr>
	</table>

</form:form>

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
						"sZeroRecords" : "NO Record Found",
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
                 if ($('input[name="selectedAccount"]:checked').val()==undefined
                         && $('input[name="newAccount"]:checked').val()=='false') {
	                alert('<spring:message code="lts.acq.selectAccount.plsSelectAccount" htmlEscape="false"/>');
                 } else {				    
					event.preventDefault();
					if($("#errorMsg").val() != ''){
						alert($("#errorMsg").val());
						return;
					}
					
					if((($("input[name=selectedAccount]:checked").val() === null || $("input[name=selectedAccount]:checked").val() === undefined))  
							&& $("#newAccount").val() == 'false'){
						alert('<spring:message code="lts.acq.selectAccount.plsSelectAccount" htmlEscape="false"/>');
					}
					else{
						$("#acctselectform").submit();
					}
                 } 	
				});				

			    checkOption();
			    
			    $("[name=newAccount]").click(function(){
			    	checkOption();
				});
			    
	});

	function checkOption(){
		var val = $('input[name="newAccount"]:checked').val();
		if(val == 'false'){
			$("#accountSelectionDiv").show();
			$("#accountSelectionDiv").show();
			$("#accountSelectionDiv2").show();
			$("#accountSelectionDiv2").show();
		} else {					
			$("#accountSelectionDiv").hide();
			$("#accountSelectionDiv").hide();
			$("#accountSelectionDiv2").hide();
			$("#accountSelectionDiv2").hide();
		}
		
		var isDs = "${sessionScope.bomsalesuser.channelId}" == "12" || "${sessionScope.bomsalesuser.channelId}" == "13" ;
		if(isDs)
		{
			$("#accountSelectionDiv2").hide();
			$(".searchBarTr").hide();
		}
	}
	
</script>



<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>
