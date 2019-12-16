<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />


<table width="100%" class="tablegrey" id="historytable">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" rules="none">
		<tr>
			<td class="table_title">Amendment History</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr >
		<td id="historycell" bgcolor="#FFFFFF" height="500" valign="middle" align="center">
			<c:if test="${orderamend.amendHistory == null}">
			<label><span style="font-weight: bold;">No amendment history found.</span></label>
			</c:if>
			<c:if test="${orderamend.amendHistory != null}">
			<iframe id="historyframe" src="orderamendhistory.html" frameborder="0" width="100%" height="100%"></iframe>
			</c:if>
		</td>
	</tr>
</table>
<br>
<table width="100%" border="0" cellspacing="0" align="right">
	<tr align="right">
		<td align="right">
			<a href="javascript:history.go(-1)" class="nextbutton">Back</a> 
		</td>
	</tr>
</table>
<br>