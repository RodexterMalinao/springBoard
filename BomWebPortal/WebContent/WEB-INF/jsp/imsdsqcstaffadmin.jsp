<%@page import="com.bomwebportal.lts.util.LtsConstant"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<!-- <link type="text/css" href="css/dataTable/table.jui.css" rel="stylesheet" /> 
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>-->



<style type="text/css">
.orderSearch {
	background-color: white;
	border: solid 2px #C0C0C0
}

.orderSearchButton {
	text-align: right;
	padding: 0 0.5em 0.5em 0
}

.orderSearchLabel {
	display: inline-block;
	width: 90px
}

.overflowDiv {
	overflow-x: auto;
	overflow-y: hidden;
	display: inline-block;
	width: 100%
}

.orderSearchResult {
	background-color: white
}

.sorting {
	background: #abd078 url('images/sort_both.png') no-repeat center right;
}

.sorting_asc {
	background: #abd078 url('images/sort_asc.png') no-repeat center right;
}

.sorting_desc {
	background: #abd078 url('images/sort_desc.png') no-repeat center right;
}
</style>

<style type="text/css">
	.sorting, .sorting_asc, .sorting_desc { behavior: url('js/iepngfix.htc') }
</style>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.orderSearch { padding-left: 0 }
.orderSearchResult { padding-bottom: 1em }
</style>
<![endif]-->





<script>

		function submitForm() {
				document.imsDsQCStaffAdminForm.submit();
		}
		
</script>			

							
<form:form name="imsDsQCStaffAdminForm" method="POST" commandName="ImsDsQCStaffAdminUI">
					

		<form:hidden path="reset"/>
		<div class="orderSearch">
			<h2 class="table_title" style="font-size: medium; margin: 0">QC property Setting</h2>
			
			<div style="clear: both"></div>
		</div>
		<input type="hidden" id="index" name="index" value="">

		<div class="orderSearch">
			<!-- <h2 class="table_title" style="font-size:medium; margin:0">IMS Order Enquiry</h2> -->
			<div class="overflowDiv orderSearchResult" style="margin-top: 5px">
				<div>Control Staff QC Ratio 
					 <form:input path="controlStaffRatio"/>  <br><br>
				</div>
				
				<div>Clean Staff QC Ratio
					 <form:input path="cleanStaffRatio" /> 
				</div>	 
				
				<div class="orderSearchButton">
<!-- 					 <input type="button" value="Save" id="savebtn" onclick="javascript:submitForm();"> -->							
						<a class="nextbutton" href="javascript:void(0)" id="savebtn" onclick="javascript:submitForm();" >Save</a>

				</div>		
			</div>
		</div>
		
		

</form:form>