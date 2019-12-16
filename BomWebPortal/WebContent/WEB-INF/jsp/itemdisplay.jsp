<jsp:include page="/WEB-INF/jsp/header.jsp">

<jsp:param name="enableOnloadJScript" value="Y" />
</jsp:include>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id=progress bar>

<br>
<br>
</div>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>

<!-- TinyMCE -->
<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript">
	tinyMCE
			.init({
				// General options
				mode : "textareas",
				theme : "advanced",
				plugins : "table,inlinepopups,preview",

				// Theme options
				theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,|,bullist,numlist,|,outdent,indent,|,table,|,cut,copy,paste,pastetext,|,undo,redo,removeformat,|,preview,code",
				theme_advanced_buttons2 : "",
				theme_advanced_buttons3 : "",
				theme_advanced_buttons4 : "",
				theme_advanced_toolbar_location : "top",
				theme_advanced_toolbar_align : "left",
				theme_advanced_statusbar_location : "none",
				theme_advanced_resizing : true,

				// Example content CSS (should be your site CSS)
				content_css : "css/ssc2.css",

				// Style formats
				style_formats : [ {
					title : 'Bold',
					inline : 'b'
				}, {
					title : 'Basket Title',
					inline : 'span',
					classes : 'basket_title'
				}, {
					title : 'Item Title (Rate plan)',
					inline : 'span',
					classes : 'item_title_rp'
				}, {
					title : 'Item Title (Handset)',
					inline : 'span',
					classes : 'item_title_hs'
				}, {
					title : 'Item Title (Basket VAS)',
					inline : 'span',
					classes : 'item_title_bvas'
				}, {
					title : 'Item Title (Optional VAS)',
					inline : 'span',
					classes : 'item_title_vas'
				}, {
					title : 'Item Title (Fee)',
					inline : 'span',
					classes : 'item_title_fee'
				}, {
					title : 'Item Detail',
					inline : 'span',
					classes : 'item_detail'
				} ]
			});

	function formSubmit() {
		alert('formSubmit');
		document.itemDisplayForm.submit();
	}
	
	
	
	function searchSubmit(){
		//alert('searchSubmit');
		document.itemDisplayForm.formAction.value="SEARCH";
		document.itemDisplayForm.submit();
	}
	function insertSubmit(){
		//alert('insertSubmit');
		document.itemDisplayForm.formAction.value="INSERT";
		document.itemDisplayForm.submit();
	}
	
	/*function updateSubmit(){
		alert('updateSubmit');
		document.itemDisplayForm.formAction.value="UPDATE";
		document.itemDisplayForm.submit();
	}*/
	function deleteSubmit(){
		//alert('deleteSubmit');
		document.itemDisplayForm.formAction.value="DELETE";
		document.itemDisplayForm.submit();
	}
	function cleanSubmit(){
		//alert('deleteSubmit');
		document.itemDisplayForm.formAction.value="CLEAN";
		document.itemDisplayForm.submit();
	}
	
	
	
	
</script>
<!-- /TinyMCE -->

<form:form method="POST" name="itemDisplayForm"	commandName="itemDisplay">
	<h3>Item Display HTML Editor</h3>
	<div id="itemDisplay" style="width:100%">
	<table width="100%"  bgcolor="#FFFFFF" border="1" >
	<tr>
											<td>Item ID</td>
											<td>locale</td>
											<td>display Type</td>
												<td>html</td>
											
								
											</tr>
	<c:forEach items="${itemDisplayDTOList}" var="item">
											<tr>
											<td>${item.itemId}</td>
											<td>${item.locale}</td>
											<td><a href="itemdisplay.html?itemId2=${item.itemId}&locale2=${item.locale}&type2=${item.displayType}&from=itemdisplay">${item.displayType}</a></td>
												<td>${item.html}</td>
											
								
											</tr>

										</c:forEach>
	
	
	
	
	</table>
	
	</div>
	<table width="100%"  bgcolor="#FFFFFF" border="0">

		<tr>
			<td>Message</td>
			<td ><form:input path="formMessage"  disabled="true" size="100"  />&nbsp;</td>
		<tr>
		<tr>
			<td colspan="2"><HR>Item Display Info </td>
			
		<tr>
		
		<tr>
			<td>Item ID:</td>
			<td><form:input path="itemId" maxlength="22" /></td>
		<tr>
		<tr>
			<td>Locale:</td>
			<td>
			<!-- 
			<form:input path="locale" maxlength="10" />
			 -->
				<form:select path="locale">
						<form:option value="NONE" label="Select" />
						<form:options items="${localeList}" />
					</form:select>
					<form:errors path="locale" cssClass="error" />
			
			</td>
		<tr>
		<tr>
			<td>Display type:</td>
			<td>
			<!-- 
			<form:input path="displayType" maxlength="20" />
			 -->
			 <form:select path="displayType">
						<form:option value="NONE" label="Select" />
						<form:options items="${displayTypeList}" />
					</form:select>
					<form:errors path="displayType" cssClass="error" />
			
			<input type="button" name="Search" value="Search" onClick="searchSubmit()"/>
			<input type="button" name="Insert" value="Insert&Update" onClick="insertSubmit()" /> 
	<!-- 		<input type="submit" name="Update" value="Update" onClick="updateSubmit()" />  -->
			<input type="button" name="Delete" value="Delete" onClick="deleteSubmit()" /> 
			<input type="button" name="Delete" value="Clear" onClick="cleanSubmit()" /></td>
			
		<tr>
		<tr>
			<td>Description:</td>
			<td><form:input path="description" maxlength="200"  size="60"/></td>
		<tr>
		
		
		<tr>
			<td>image path:</td>
			<td><form:input path="imagePath" maxlength="300" size="60"/></td>
		<tr>
		
		
	</table>
	
	<table>
		<tr>
			<td><form:textarea path="html" rows="15" cols="80" /></td>
		</tr>
	</table>

<!-- 
	<div class="buttonmenubox_R" id="buttonArea"><a href="#" class="nextbutton" onClick="javascript:formSubmit();">Submit</a></div>
			
    <input	type="reset" name="reset" value="Reset" /> 
-->

				
				<form:hidden path="formAction"/>
				<input type="hidden" name="itemId2" />
				<input type="hidden" name="currentView" value="itemdisplayeditor" />
</form:form>
<!-- -------------------------------------  end content ----------------------------------------- -->

<%@ include file="/WEB-INF/jsp/footer.jsp"%>