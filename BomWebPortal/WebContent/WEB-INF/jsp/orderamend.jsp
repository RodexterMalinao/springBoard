<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<link type="text/css" href="css/dataTable/table.jui.css"rel="stylesheet" />
<link href="css/custom-theme/jquery-ui-1.10.4.custom.css" rel="stylesheet" />

<c:set var="isRetail" value="${bomsalesuser.channelId == 1}"></c:set>

<script>

var isDS = "${ims_direct_sales}" == "true"?"Y":"N";
var isImsFlow = "${ImsFlow}";

window.onload=function(){
//document.getElementById('dataTableDiv').style.width= window.screen.width*0.85 + "px";

if (isDS == "Y") document.getElementById('dataTableDiv').style.width= window.screen.width + "px";
};

  function openDialog(index) {

		var div = "";
		div += "<div id=\"" + this.windowId
 			+ "\" title=\"" + this.title + "\" style=\"overflow: auto; margin: 5; padding: 5;\" >";
		div += index;
		div += "</div>";

		var scrollHandler = function () { 
			divDialog.dialog("option","position","center"); 
		};
		
		var divDialog = $(div).dialog({
										title:  'Remarks',
										minHeight: 400,
										minWidth: 500,
										maxHeight: 600,
										maxWidth: 700,
										modal:  true,
										resizable: true,
										autoResize: true,
										open : function(event, ui) {
											//$(window).scroll(scrollHandler);
											
										},
										close: function(event, ui) {
											$(this).dialog('destroy').remove();
											//$(window).off("scroll", scrollHandler);
								        }
		});
    }
  </script>

<form:form method="POST" action="#" id="orderamendHistform" commandName="orderamend">
	<input type="hidden" id="sbuid" name="sbuid" value="${sessionScope.sbuid}" />
	<input type="hidden" id="loginId" name="loginId" value="<%=username%>" />
	<input type="hidden" id="sessionId" name="sessionId" value="${pageContext.session.id}" />
	<input type="hidden" id="AmendSuccessInd" name="AmendSuccessInd" value="${sessionScope.AmendSuccessInd}" />
	<input type="hidden" id="ltsAmendAlertMsg" name="ltsAmendAlertMsg" value="${sessionLtsOrderInfoMsg}" />
<br>
<table width="98%" align="center">
	<tr>
		<td>
			<div id="s_line_text">Order Amendment</div>
			<br>
			<table width="100%" border="0" cellspacing="5" cellpadding="8" class="contenttext">
				<tr>
					<td width="1%">&nbsp;</td>
					<td width="25%">Springboard Order Number: </td>
					<td width="75%" class="contenttext_blue">
						<b>${orderamend.sbOrderNum}</b>
						<input type="hidden" id="orderId" name="orderId" value="${orderamend.sbOrderNum}" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>BOM OCID: </td>
					<td class="contenttext_blue">
						<b>${orderamend.bomOcId}</b>
					</td>
				</tr>
				<tr <c:if test='${ImsFlow == "Y" && ImsShowAmendButton == "N"}'>style="display:none"</c:if>>
					<td align="right" colspan="3">
						<a name="addAmendBtn" href="#" id="addAmendBtn" onClick="addAmend()">
							<div class="func_button">Add Amendment</div>
						</a>
					</td>
				</tr>
			</table>
			<br>
			<div id="s_line_text">Order Activity</div>
			<table width="98%" border="0" cellspacing="1" cellpadding="3" class="contenttext" align="center">
					<tr>
						<td id="historycell" bgcolor="#FFFFFF" valign="top">
						<c:if test="${orderamend.amendHistory == null}">
								<label>Amendment History not found.</label>
						</c:if> 
						<c:if test="${orderamend.amendHistory != null}">
						<div id="dataTableDiv" style="height:auto; overflow-x: auto; width: 100%">
							<table id="tblAmendHistList" style="margin: 0; padding-left:0px ;" border="1" cellspacing="0" class="contenttext">
									<thead>
										<tr>
											<th>Service Number</th>
											<th>Remarks</th>
											<th>Working Party/ Assignee</th>
											<th>WQ Status</th>
											<th>Nature</th>
											<th>WQ Serial</th>
											<th>Last Update Date</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${orderamend.amendHistory}" var="History" varStatus="s">
											<tr>
												<td>${History.serviceNum}</td>
												<td>
													<div id="remarks${s.index }" class="text-content short-text">${History.remarks}</div>
													<br/>
													<c:if test="${History.enableShowMore }">
														<div id="tempRemarks${s.index }" style="display:none">${History.remarks}</div>
														<a href="javascript:void(0);" class="nextbutton" id="opener" onclick="openDialog($('#tempRemarks${s.index }').html())">Show more</a>
													</c:if>
												</td>
												<td>${History.workingParty}</td>
												<td>
													${History.wqStatus}
													<br />
													<br /> 
													<c:if test="${History.enableStatusChg}">
														<a name="a${History.serviceNum}"
															href="#a${History.serviceNum}" class="nextbutton"
															id="showDetailBtn"
															onClick="changeStatus('${History.wqWpAssgnId}','${History.wqStatusCd}','${orderamend.bomOcId}','${History.wqSerial}');">Change
															Status</a>
													</c:if>
												</td>
												<td>${History.nature}</td>
												<td>${History.wqSerial}</td>
												<td>${History.date}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								</div>
								<br>
							</c:if>
						</td>
					</tr>
					<c:if test="${isRetail && orderamend.amendFormList != null}">
					<tr>
						<td id="formListCell" valign="top">
						<div style="overflow-x: auto;border-color:#BEBEBE "  class="ui-widget-content">
						<table width="100%" cellpadding="4" cellspacing="4" class="amendFormListTable" >
							<thead>
								<tr class="ui-widget-header" height="40">
									<th width="150px" align="center" valign="middle" ><b>Amendment Form</b></th>
									<th width="50px" align="center" valign="middle"><b>Sequence</b></th>
									<th width="150px" align="center" valign="middle"><b>Create Date</b></th>
									<th width="300px" align="center" valign="middle"><b>Print</b></th>											
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty orderamend.amendFormList}">
										<c:forEach items="${orderamend.amendFormList}" var="formDtl" varStatus="d">
											<tr>
												<td width="150px" align="center" valign="top">
													${formDtl.filePathName}
												</td>
												<td width="150px" align="center" valign="top">
												    ${formDtl.seqNum}
												</td>
												<td width="150px" align="center" valign="top">
													${formDtl.createDate}
												</td>
												<td width="300px" align="center" valign="top">
													<a name="prtFormBtn" href="#" class="nextbutton prtFormBtn" id="prtFormBtn$" onClick="printForm(${formDtl.seqNum})">Print</a>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="4" align="center">
												<span style="font-weight: bold;">No Amendments.</span>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						</div>
						</td>
					</tr>
					</c:if>
				</table>
			</td>
		</tr>
	</table>
	

	<c:choose>
		<c:when test='${ImsFlow == "Y"}'>
			<div id="addAmendDiv" style="display: none;">
				<iframe id="amendCreateFrame" data-src="imsorderamendment.html?OrderIdForAmend=${orderamend.sbOrderNum}" src="about:blank"
					frameborder="0" width="100%" scrolling="auto" onload="this.style.height=this.contentDocument.body.scrollHeight +'px' ;"></iframe>
			</div>
		</c:when>
		<c:otherwise>
			<div id="addAmendDiv" style="display: none;">
				<iframe id="amendCreateFrame" data-src="orderamendcreate.html?&orderId=${orderamend.sbOrderNum}" src="about:blank"
					frameborder="0" width="100%" scrolling="auto" onload="this.style.height=this.contentDocument.body.scrollHeight +'px';"></iframe>
			</div>
		</c:otherwise>
	</c:choose>
</form:form>

<div style="display: none" >
	<a href="#" id="btnRefreshCurrentPage" class="nextbutton">Refresh Result</a>
</div>


<script type="text/javascript">

if ($("#AmendSuccessInd").val()=='true'){
	alert("Amendment Success.");
	parent.location.reload();
}else if($("#ltsAmendAlertMsg").val() != ''){
	alert($("#ltsAmendAlertMsg").val());
}


	$("#submitBtn").click(function() {
		document.getElementById("amendCreateFrame").contentWindow.submitForm();	
		//location.reload();
	});
	
    $('#btnRefreshCurrentPage').click(function() {
    	location.reload();
        return false;
    });
 
	
    $(document).ready(
			function() {						
				$('#tblAmendHistList').dataTable({

					"bAutoWidth": true,
					"bSort" : true,
					"bLengthChange" : true,
					"sScrollY" : "300",
					"sScrollYInner": "300",
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"bScrollCollapse" : true,
					"bJQueryUI" : true,
					"bPaginate" : false,
					"oLanguage" : {
						"sZeroRecords" : "NO Record Found",
						"sInfo" : "Total Records: _TOTAL_ " + 
								  "<br/><br/> Total outstanding WQ count : ${orderamend.outstandingWqCount}",
						"sInfoEmpty" : "NO Record Found",
						"sInfoFiltered" : "(filtered from _MAX_ total records)",
						"sSearch" : "Filter: "
					},
					"aaSorting" : [ [ 6, 'desc' ] ],
					"aoColumns" : [ 
					                null, 
					               	null, 
					                null, 
					                null, 
					                null, 
					                null, 
					                {"sType" : "dd-mm-yyyy"}
					              ]
				});
				$("#tblAmendHistList").css("width","100%");				
			});
	
	function changeStatus(wqWpAssgnId,wqStatus,bomOcId,wqSerial){
		var page = "/qm/ShowChangeStatus.action";
		var selectedWorkQueueString = "{wqWpAssgnId,wqStatus,bomOcId,wqSerial}";
		selectedWorkQueueString += ";{";
		selectedWorkQueueString += wqWpAssgnId;
		selectedWorkQueueString += " ,";
		selectedWorkQueueString += wqStatus;
		selectedWorkQueueString += " ,";
		selectedWorkQueueString += bomOcId;
		selectedWorkQueueString += " ,";
		selectedWorkQueueString += wqSerial;
		selectedWorkQueueString += " }";
  		page = page + "?selectedWorkQueueString=" + selectedWorkQueueString; 
  		page = page + "&loginId=" + $('#loginId').val();
  		page = page + "&sbSessionId=" + $('#sessionId').val();
  		 
		
		window.open(page, 'newWin', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
		//alert(page);
	}
	function addRemarks(serviceNum){
		//event.preventDefault();
		window.open('http://intra.pccw.com', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
		alert(serviceNum);
	}
	function printForm(seqNum){
		var sbuid = $('input[name="sbuid"]').val();
		var orderId = $('input[name="orderId"]').val();
		window.open('ltsreport.html?sbuid='+ sbuid +'&seq='+ seqNum + '&orderId='+ orderId +'&ltsRptAction=AMEND_AF');	
	}	
	
	function addAmend(){
	    if (isDS == "Y" && isImsFlow == "Y" ) {
			window.location.assign("imsorderamendment.html?OrderIdForAmend=${orderamend.sbOrderNum}");
	    } else {
		    var iframe = $("#amendCreateFrame");
		    iframe.attr("src", iframe.data("src")); 
		    $('#addAmendDiv').show(10);
		} 
	}
	

	
	$("button").click(function(){

	});
	


</script>
<%
	session.setAttribute("AmendSuccessInd", null);
	session.setAttribute("sessionLtsOrderInfoMsg", null);
%>



<%@ include file="/WEB-INF/jsp/footer.jsp"%>