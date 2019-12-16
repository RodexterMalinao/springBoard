<%@ include file="/WEB-INF/jsp/header.jsp"%>
<script src="js/jquery-1.4.4.js"></script>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp"></jsp:include>

<script language="javascript">
	function nextPage() {
		document.mobccssimtypeselectionForm.submit();
	}
</script>

<form:form method="POST" name="mobccssimtypeselectionForm"
	commandName="mobccssimtypeselection">
	<!-- Sim Type Selection Table -->
	<table width="100%" border="1">
		<tr>
			<td class="table_title" colspan="3">Sim Type Selection</td>
		</tr>
		<tr>
			<td class="table_title" align="left" >Select</td>
			<td class="table_title" align="left" >Desc</td>
			<td class="table_title" align="left" >Qty</td>
		</tr>
		<c:forEach items="${vasDetailList}" var="vasDetail">
			<tr>
				<td align="center" width="3%"><form:radiobutton path="itemId" value="${vasDetail.itemId}"/></td>
				<td align="left" width="70%">${vasDetail.itemHtml}</td>
				<td aligh="left" width="17%">${vasDetail.stockCount}</td>
			<tr>
		</c:forEach>
		<tr>
			<td colspan="3">
				<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3"
						onClick="javascript:nextPage();">continue ></a>
				</div>
			</td>
		</tr>
	</table>

</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>