<%@ include file="/WEB-INF/jsp/header.jsp"%>
<style type="text/css">
tr.vasRow { background-color: #F0F0F0 }
.vasRow .pricing {text-align:center; width:15%}
.vasRow td ul { margin:0 0 0 0;}
tr.vasHeaderRow { 
	background-color: #abd078; 
	color: #ffffff; 
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; 
	font-size: 13px
}
td.pricing {background-color: #e2f5d3}
</style>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript">
</script>

<form:form method="POST" name="previewCampaignDtlForm" commandName="previewCampaignDtl">
<h2 class="table_title" style="font-size: medium; margin: 0">Campaign Basket:</h2>
<table width="100%" style="background-color: #ffffff;">
	<tr class="vasHeaderRow">
		<th>Items</th>
		<th>Monthly Payment</td>
	</tr>
	<c:forEach items="${bundleList}" var="ptList" varStatus="ptRow">
	<c:if test='${not empty ptList.itemHtml || (not empty ptList.itemRecurrentAmt && ptList.itemRecurrentAmt != 0) }'>
	<tr class="vasRow">
		<td>${ptList.itemHtml}</td>
		<td class="pricing">
			<c:if test='${not empty ptList.itemRecurrentAmt && ptList.itemRecurrentAmt != 0}'>
				$<fmt:formatNumber value="${ptList.itemRecurrentAmt}" pattern="#,###.####" /> / month
			</c:if>
		</td>
	</tr>
	</c:if>
	</c:forEach>
	<c:if test="${not empty simList}">
		<c:forEach items="${simList}" var="ptList" varStatus="ptRow">
		<c:if test='${not empty ptList.itemHtml || (not empty ptList.itemRecurrentAmt && ptList.itemRecurrentAmt != 0) }'>
		<tr class="vasRow">
			<td>${ptList.itemHtml}</td>
			<td class="pricing">
				<c:if test='${not empty ptList.itemRecurrentAmt && ptList.itemRecurrentAmt != 0}'>
					$<fmt:formatNumber value="${ptList.itemRecurrentAmt}" pattern="#,###.####" /> / month
				</c:if>
			</td>
		</tr>
		</c:if>
		</c:forEach>
	</c:if>
</table>
<%-- <br/>
<h2 class="table_title" style="font-size: medium; margin: 0">Optional Vas List:</h2>
<table width="100%" style="background-color: #ffffff;">
	<tr class="vasHeaderRow">
		<th>Items</th>
		<th>Monthly Payment</td>
	</tr>
	<c:forEach items="${optionList}" var="ptList" varStatus="ptRow">
	<tr class="vasRow">
		<td>${ptList.itemHtml}</td>
		<td class="pricing">
			<c:if test='${not empty ptList.itemRecurrentAmt && ptList.itemRecurrentAmt != 0}'>
				$<fmt:formatNumber value="${ptList.itemRecurrentAmt}" pattern="#,###.####" /> / month
			</c:if>
		</td>
	</tr>
	</c:forEach>
</table> --%>
</form:form>