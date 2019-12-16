<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" 
    rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.colReorderWithResize.plugin.js"></script>
<script type="text/javascript" src="js/iepngfix_tilebg.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialogAndy.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script> 
<!-- <link type="text/css" href="css/dataTable/table.jui.css" rel="stylesheet" /> -->
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<!-- <link type="text/css" href="css/dataTable/jquery.dataTables.css" rel="stylesheet" />  --> 
 

<style type="text/css">
.sbOrderHistDiv { overflow-y:auto; display: inline-block; width: 100% }
.searchRow { padding: 15px 10px }
.label { display: inline-block; width: 85px; font-size: 12px; font-family: "Verdana", "Helvetica", "Arial", "sans-serif" }
.labelFirst { width: 110px }
.input { display: inline-block; width: 120px }
.searchColHalf { overflow: hidden; width: 48%; display: inline-block; float: left  }
.even { background-color: #E8FFE8 }
.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px; font-family: "Helvetica", "Arial", "sans-serif" }
.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078 !important; padding: 5px 10px; position: relative }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.custPremier { font-weight: bold; color: red }
.inputGroup { display: inline-block; overflow: hidden; vertical-align: middle }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.sbOrderHistDiv { padding-bottom: 1em }
</style>
<![endif]-->


<script type="text/javascript">
var mainTable = null;
var counter = 1;

$(document).ready(function() {

		
});

</script>	


<h3>QC Remarks</h3>
<form:form method="POST" name="form" commandName="ImsDsQcProcessDetailUI">

<table id="	" width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="0" rules="none">
		
		</table>
		<table width="100%" border="0" cellspacing="1" class="contenttext" id="helloworld">
		<thead>
			<tr class="contenttextgreen">
						<td class="table_title">Remarks</td> 
			</tr>
		</thead>
		<tbody> 
		<tr id="row1">
<%--				<td>${ImsDsQcProcessDetailUI.qcFinding} --%>
<%-- 					<form:label path="qcFinding"   /> 	 --%>
<%-- 					<c:out value="${ImsDsQcProcessDetailUI.qcFinding}"></c:out> --%>
				<td>
				<form:textarea cssStyle="width: 99.5%;" path="qcFinding"  readonly="true" rows="20"  />
<%-- 					<textarea style="width: 100%; height: 300px;">${ImsDsQcProcessDetailUI.qcFinding}</textarea> --%>
				</td>
		</tr>
		</tbody>
	</table>
</table>
</form:form>

<%---------------------------------------  end content -------------------------------------------%>
<%-- <%@ include file="/WEB-INF/jsp/footer.jsp"%> --%>