<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<link type="text/css" href="css/dataTable/table.jui.css"
	rel="stylesheet" />
<style type="text/css">

.ui-widget-header {
border: 1px solid #6CA9F5;
background: #6CA9F5;
color: #ffffff;
font-weight: bold;
}
.contenttextgreen {
font-family: "Helvetica", "Verdana", "Arial", "sans-serif", �s�ө���;
padding-left: 5px;
font-size: 14px;
color: #6CA9F5;
font-weight: bold;
}
.table_title {
color: #FFF;
background-color: #6CA9F5;
padding-top: 5px;
padding-right: 10px;
padding-bottom: 5px;
padding-left: 10px;
width: auto;
font-weight: bold;
font-family: "Helvetica", "Verdana", "Arial", "sans-serif", �s�ө���;
}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
border: 1px solid #FFFFFF;
background: #6CA9F5;
font-weight: normal;
color: #ffffff;
}

.text-content {
	line-height: 1em;
}

.short-text {
	overflow: hidden;
	height: 4em;
}

.full-text {
	height: auto;
}

table.display {
margin: 0 auto;
clear: both;
border-collapse: collapse;
word-wrap:break-word; 
}
</style>

<script>
var divHeight = 280;
var dialogHeight = 210;
var dialogWidth = 700;
var tableHeight = 125;

$(document).ready(function(){
	divHeight = $(window).height() - 20;
	$("#dataTableDiv").css('height', divHeight);
	dialogHeight = $(window).height() - 70;
	tableHeight = $(window).height() - 132;
	
	$('#tblAmendHistList').dataTable({
		"bFilter" : false,
		"bSort" : true,
		"bLengthChange" : false,
		"sScrollY" : tableHeight,
		"sScrollX" : "100%",
		"bScrollCollapse" : true,
		"bJQueryUI" : true,
		"bPaginate" : false,
		"oLanguage" : {
			"sZeroRecords" : "NO Record Found",
			//"sInfo" : "Total Records: _TOTAL_ " + 
			//		  "<br/><br/> Total outstanding WQ count : ${orderamend.outstandingWqCount}",
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
});

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
										height: dialogHeight,
										width: dialogWidth,
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


<c:if test="${amendHistList == null}">
	<label>Amendment History not found.</label>
</c:if> 
<c:if test="${amendHistList != null}">
<div id="dataTableDiv" style="height:280px; overflow-y: auto; overflow-x: auto">
<table class="OrderAmendcontenttext" width="1450px" 
		cellpadding="4" cellspacing="2" id="tblAmendHistList">
		<thead>
			<tr bgcolor="#99C68E">
				<th width="150px" align="center" valign="middle"><b>Service
						Number</b></th>
				
				<th width="400px" align="center" valign="middle"><b>Remarks</b>
				</th>
				<th width="200px" align="center" valign="middle"><b>Working
						Party/ Assignee</b></th>
				<th width="150px" align="center" valign="middle"><b>WQ
						Status</b></th>
				<th width="300px" align="center" valign="middle"><b>Nature</b>
				</th>
				<th width="150px" align="center" valign="middle"><b>WQ
						Serial</b></th>
				<th width="100px" align="center" valign="middle"><b>Last Update Date</b>
				</th>
				<!-- <th width="10%" align="center" valign="middle">
								</th>
								<th width="10%" align="center" valign="middle">
								</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${amendHistList}" var="History"
				varStatus="s">
				<tr>
					<td width="150px" align="center" valign="top"
						class="OrderAmendBGgreen2"><b>${History.serviceNum}</b>
					</td>
					<td width="400px" align="left" valign="top"
						class="OrderAmendBGgreen2">
						<div id="remarks${s.index }" class="text-content short-text">${History.remarks}</div>
						<br/>
						<c:if test="${History.enableShowMore }">
							<div id="tempRemarks${s.index }" style="display:none">${History.remarks}</div>
							<a href="javascript:void(0);" class="nextbutton" id="opener" onclick="openDialog($('#tempRemarks${s.index }').html())">Show more</a>
						</c:if>
					</td>
					<td width="300px" align="center" valign="top">${History.workingParty}</td>
					<td width="150px" align="center" valign="top">
						${History.wqStatus}<br />
					<br /> <c:if test="${History.enableStatusChg}">
							<a name="a${History.serviceNum}"
								href="#a${History.serviceNum}" class="nextbutton"
								id="showDetailBtn"
								onClick="changeStatus('${History.wqWpAssgnId}','${History.wqStatusCd}','${orderamend.bomOcId}','${History.wqSerial}');">Change
								Status</a>
						</c:if>
					</td>
					<td width="300px" align="center" valign="top"
						class="OrderAmendBGgreen2">${History.nature}</td>
					<td width="150px" align="center" valign="top"
						class="OrderAmendBGgreen2">${History.wqSerial}</td>
					<td width="100px" align="center" valign="top">${History.date}</td>
					<%-- 																<td align="center"><c:if
											test="${History.enableStatusChg}">
											<a name="a${History.serviceNum}"
												href="#a${History.serviceNum}" class="nextbutton"
												id="showDetailBtn"
												onClick="changeStatus('${History.serviceNum}');">Change
												Status</a>
										</c:if></td>
									<td align="center"><c:if
											test="${History.enableAddRemarks}">
											<a name="a${History.serviceNum}"
												href="#a${History.serviceNum}" class="nextbutton"
												id="showDetailBtn"
												onClick="addRemarks('${History.serviceNum}');">Add
												Remarks</a>
										</c:if></td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:if>