<%@ include file="/WEB-INF/jsp/header.jsp"%>
<style type="text/css">
.label { margin: 0.5em 1em; font-family: "Verdana", "Helvetica", "Arial", "sans-serif" }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px; font-family: "Helvetica", "Arial", "sans-serif" }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078 !important; padding: 5px 10px; position: relative }
</style>

<script type="text/javascript">
$(document).ready(function() {
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	$(".closeButton").click(function(e) {
		e.preventDefault();
		window.close();
		return false;
	});
});
</script>

<div class="previewForm">
	<h3>Cannot Download Document</h3>
	<div class="row label error">
	<c:choose>
	<c:when test="${INVALID_PARAMETER}">
		We cannot process as invalid order information provided
	</c:when>
	<c:when test="${FILE_NOT_FOUND}">
		File not found on server, it may be archived
	</c:when>
	</c:choose>
	</div>
	<div class="row buttonmenubox_R">
		<a href="#" class="nextbutton closeButton">Close</a>
		<div style="clear:both"></div>
	</div>
</div>

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>