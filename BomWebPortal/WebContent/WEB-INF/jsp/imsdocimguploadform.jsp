<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils,
				 com.bomwebportal.dto.BomSalesUserDTO,
				 java.util.Locale"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Springboard</title>
<link href="css/ssc.css" rel="stylesheet" type="text/css">
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">
</head>
<div id="head"/>
<div id="wrapper"/>


<!doctype html>
<html>
<head>
<title>PCCW Mobile</title>
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">
<script src="js/common.js" language="javascript"></script>
<script src="js/jquery-1.4.4.js"></script>
<style>
	.error {
		color: #ff0000;
	}
</style>
<script type="text/javascript">
<!--
var exts = "${jsAllowedExtensions}";
function readURL(input) {

	if (input.value == null || input.value == "") {
		resetForm();
		return;
	}
	
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
           $('#preview').attr('src', e.target.result).show();
           $('#previewPanel').show();
		}
		reader.readAsDataURL(input.files[0]);

    } else {
    	
    	//$('#preview').attr('src', input.value).width(400).show();
    	
    }
    
    $('#buttonPanel').show();
}
function fileSelected(obj){

	//readURL(obj);
}

function fileClicked(obj) {
	$('#errors').hide();
}

function resetForm() {
	$('#preview').removeAttr("src").hide();
	$('#previewPanel').hide();
	//$('#buttonPanel').hide();
	$('#errors').hide();
}

function submitUpload() {
	var filename = document.docImgUploadForm.file.value;
	if (filename == "") {
		alert('Please choose a file to upload');
		return false;
	}

	if (!testFileType(filename, exts)) {
		alert('File type is not allowed');
		return false;
	}
	
	return true;

}


function testFileType( fileName, fileTypes ) {

	if (!fileName) return false;

	var dots = fileName.split(".")
	//get the part AFTER the LAST period.
	var fileType = dots[dots.length-1].toLowerCase();

	if ( fileTypes.indexOf(("|"+fileType+"|")) >= 0) {
		// ok
		return true;
	} else {
		return false;
	}

	return false;
}

//-->
</script>



</head>


<body>
<br/>
<table class="tablegrey" width="100%" height="350">
	<tr>
	<td  valign="top" bgcolor="#ffffff">
	<table width="100%" border="0" cellspacing="0">
		<tr> 
			<td class="table_title" >Upload Document Capture - Chooose file and upload</td>
		</tr>
	</table>
	<form:form modelAttribute="docImgUpload" commandName="docImgUploadForm" name="docImgUploadForm" enctype="multipart/form-data" method="post" >
		<form:hidden path="orderId"/>
		<form:hidden path="docType"/>
		<form:hidden path="username"/>

		<table width="100%" bgcolor="#ffffff" class="contenttext">
			<tr height="30px">
				<td><b>Order Id:</b></td>
				<td>${docImgUploadForm.orderId }</td>
				<td><b>Doc Type:</b></td>
				<td>${docImgUploadForm.docName }</td>
			</tr>
			<%--
			<tr>
				<td><b>Support file types:</b></td>
				<td colspan="3">${fn:join(docImgUploadForm.allowedExtensions, ', ') }</td>
			</tr>
			<tr>
				<td><b>Max file size:</b></td>
				<td colspan="3"><fmt:formatNumber type="number" pattern="0.0'MB'" value="${ docImgUploadForm.maxUploadSize/1024/1024 }" /></td>
			</tr>
			 --%>
			<tr height="30px">
				<td><b>File:</b></td>
				<td><input id="uploadfile" type="file" name="file" accept="image/*" onchange="fileSelected(this)" onclick="fileClicked(this)" /></td>
				<td colspan="2">
					<div id="buttonPanel" style1='display: none !important;'>
						<input type="submit" value="Upload" onclick='return submitUpload();'/>
						<input type="reset" value="Clear" onclick='resetForm()'/>
					</div>
				</td>
			</tr>
			
			<tr>
				<td colspan="4" class="smalltextgray">
					<strong><span class="contenttext_red">*</span> Support file types :  ${fn:join(docImgUploadForm.allowedExtensions, ', ') }</strong>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="smalltextgray">
					<strong><span class="contenttext_red">*</span> Max file size : 
						<fmt:formatNumber type="number" pattern="0.0'MB'" value="${ docImgUploadForm.maxUploadSize/1024/1024 }" />
					</strong>
				</td>
			</tr>
			<tr>
				<td colspan="4"><div id="errors"><form:errors path="file" cssClass="error" element="div"/></div></td>
			</tr>
		</table>
	
		<div class="clear"></div>

		<div id="previewPanel" class="contenttext" style='display: none !important;'>
			<div><b>Preview :</b></div>
			<div style="margin: 10px;"></div>
			<div style="width: 100%; height: 300px; overflow-y:auto; overflow-x:auto;">
				<img id="preview" width="100%" />
			</div>
		</div>

	</form:form>
	</td>
	</tr>
</table>

<div>&nbsp;</div>
<table  width="100%" border="0" cellspacing="0">
	<tr>
		<td align="right">
			<a name="cancel" href="imsdoccapture.html?orderId=${docImgUploadForm.orderId }" class="nextbutton3" >Cancel</a> &nbsp;
		</td>
	</tr>
</table>

<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</body>

</html>