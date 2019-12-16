<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<style type="text/css">
#s_title_text {
    overflow: hidden;
    text-align: left;
   	font-size: 14px;
   	font-weight:bold;
   	font-style:italic;
  	color:#5d9de4;
  	padding-bottom:5px;
  	padding-top:25px;
}

.table_style_sb td, .table_style_sb th {
	font-size:13px;
	border:1px solid #FFFFFF;
	background-color:#F8F8F8;
	padding:5px 9px 4px 9px;
}
.table_style_sb th {
	text-align:left;
	padding-top:2px;
	padding-bottom:1px;
	background-color:#5d9de4 ;
	color:#fff;
}
.table_style_sb tr.alt td {
	color:#000;
	background-color:#e8e8e8;
}
.table_style_sb .off{
	display:none;
}

</style>

<div class="paper_w2 round_10">
<table width="100%">
<tr>
	<td><div id="line_text">LTS Debug Console</div></td>
	<td width="5%">
		<div class="func_button"><a id="quitBtn" href="#" onclick="window.top.close();">Quit</a></div>
	</td>
</table>	
</div>
<br/>
<table width="100%" border="0">
<tr>
	<td width ="98%" align="center">
		<div id="s_title_text" style="width:98%; height:10%">Session Object List</div>
 		<div id="tabs" class="paper_w2 ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible" style="width:98%; height:10%">
 		<table width="99%" border="0" cellspacing="5" cellpadding="5" align="center" class="table_style_sb">
		<tr>
			<th width="30%">Object ID</th>
			<th width="70%">Object Value</th>
		</tr>
		<c:forEach items="${sessionScope}" var="object" >
		<tr>
			<td><b><a href="ltsdebug.html?id=${object.key}">${object.key}</a></b></td>    
		    <td><b>${object.value}</b></td>
		</tr>
		</c:forEach>
		</table>
		</div>
	</td>
</tr>
</table>
