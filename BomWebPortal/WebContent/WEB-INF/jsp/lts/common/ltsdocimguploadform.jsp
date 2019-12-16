<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
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

function goBackPage(url){
	window.location = url;
}
//-->
</script>

	<form:form modelAttribute="docImgUpload" commandName="docImgUploadForm" name="docImgUploadForm" enctype="multipart/form-data" method="post" autocomplete="off">
		<form:hidden path="orderId"/>
		<form:hidden path="docType"/>
		<form:hidden path="username"/>
		<div class="paper_w2">
		<table width="100%" class="contenttext" >
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
				<td><input id="uploadfile" type="file" name="file" accept="image/*,application/pdf" onchange="fileSelected(this)" onclick="fileClicked(this)" /></td>
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
		
		<table  width="100%" border="0" cellspacing="0">
			<tr>
				<td align="right">
					<div class="func_button"><a name="cancel" href="#" class="nextbutton3" onClick="goBackPage('ltsdoccapture.html?sbuid=${param.sbuid}&orderId=${docImgUploadForm.orderId }&isAmend=true'); return false;" >Back</a> &nbsp;</div>
				</td>
			</tr>
		</table>
	</div>
	</form:form>

<%---------------------------------------  end content -------------------------------------------%>


<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp" %>