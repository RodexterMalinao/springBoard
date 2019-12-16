<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link rel="stylesheet" href="css/jquery-ui-1.10.3.css" />
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui-1.10.3.js"></script>
<script type="text/javascript">
	function saveBtnClick() {
		var saveInd = confirm("Please noted that the selection of VAS is not saved to database yet.  Please click 'Save Campaign' in the main page to save selection.");
   		if (!saveInd) return false;
		$("#editCampaignVasForm").submit();
		parent.$('#addBasketDialog').dialog('close');
	}
</script>
<style type="text/css">
.actionButton {
	color: #f3f3f3;
	background:URL(images/bottombut_blue.png) repeat;
	font-size: 12px;
	font-weight: bold;
	text-decoration: none;
	padding-top: 5px;
	padding-right: 12px;
	padding-bottom: 5px;
	padding-left: 12px;
	width: auto;
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif";
	margin-left: 5px;
	margin-bottom: 10px;
}
</style>
<form:form method="POST" id="editCampaignVasForm" name="editCampaignVasForm" commandName="editCampaignVas">
<h2 class="table_title" style="font-size: medium; margin: 0">Campaign Vas List:</h2>
<table width="100%" style="background-color: #ffffff;">
	<thead>
	<tr class="table_title">
		<td>Add</td>
		<td>Item Id</td>
		<td>Item Description</td>
	</tr>
	</thead>
	<c:forEach items="${cpgVasList}" var="vas" varStatus="vasStatus">
	<tr<c:if test="${vasStatus.index % 2 == 0}"> style="background-color: #F0F0F0;"</c:if>>
		<td><form:checkbox path="basVasList[${vasStatus.index}].action"/></td>
		<td><form:label path="basVasList[${vasStatus.index}].itemId">${vas.itemId}</form:label></td>
		<td><span class="item_title_vas"><form:label path="basVasList[${vasStatus.index}].itemHtml">${vas.itemHtml}</form:label></span></td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="4" align="right"><br/><span class="actionButton" onClick="saveBtnClick();">Select VAS</span><br/></td>
	</tr>
</table>
</form:form>