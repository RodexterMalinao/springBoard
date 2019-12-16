<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
<!--

function closeSelfWindows(){
	self.close();
}
function backHome(){
	window.location ="welcome.html?sbuid=${param.sbuid}";
	
}

//-->

function goNextPage(url){
	window.location = url;
}

function submitPage(){
	$("#submitDocType").val('');
	$('form').submit();
	$.blockUI({ message: null });
}

var exts = "${jsAllowedExtensions}";
function submitUpload(name, submitDocType) {
	var filename = $("[name='"+name+"']").val();
	if (filename == "") {
		alert('Please choose a file to upload');
		return false;
	}

	if (!testFileType(filename, exts)) {
		alert('File type is not allowed');
		return false;
	}
	
	$("#submitDocType").val(submitDocType);
	
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

</script>
<style type="text/css">
	.shadedRow  {
		background-color: #E2F5D3;
	}
</style>

<%if (!"true".equals(request.getParameter("isAmend"))){%>
<table width="100%"  class="tablegrey" background="images/background2.jpg">
  <tr> 
  	<td> 
	  <table width="100%"  border="0" cellspacing="1" >
	   	<tr>
	   	 <div id="header_inner">
	   	 <td  class="contenttextgreen" height="40">
			The order number is ${orderId}.</td>
		</div>
	   	</tr>
	  </table>
	 </td>
  </tr>
 </table>	
<%} %>

<form:form modelAttribute="ltsDocCapture" commandName="ltsDocCaptureForm" name="ltsDocCaptureForm" enctype="multipart/form-data" method="post" autocomplete="off">
<form:hidden path="submitDocType"/>
<table width="100%" border="0" bordercolor="#ffffff" cellspacing="1" class="paper_w2">
	<tr> 
		<td><img src="images/x_graphic.gif" height="1" ></td>
	</tr>
	<tr class="contenttextgreen"> 
		<td class="table_title" id="s_line_text">Document Upload</td>
	</tr>
	
	<tr> 
		<td><img src="images/x_graphic.gif" height="5" ></td>
	</tr>
	<tr>
		<td>
		<form:errors path="*" cssClass="error"/>
		<table width="80%" border="1" cellspacing="0" cellpadding="2" bgcolor="#FFFFFF"  class="table_style">
			<tr class="contenttextgreen">
				<th class="table_title">Doc Type</th>
				<th class="table_title">Mandatory</th>
				<th class="table_title">Collected</th>
				<th class="table_title">File</th>
				<th class="table_title">Upload</th>
			</tr>
			<c:forEach items="${ltsDocCaptureForm.docImgUploadList}" var="requriedDoc" varStatus="status">
				<tr>
					<td>
						${requriedDoc.docName}
					</td>
					<td>
						${requriedDoc.mandatory?'<b>Y</b>':'N'}
					</td>
					<td>
						${requriedDoc.collected?'<b><font color="red">Y</font></b>':'N'}
					</td>
					<td>
						<input type="file" name="docImgUploadList[${status.index}].file" accept="image/*,application/pdf" />
					</td>
					<td>
						<input type="submit" value="Upload" onclick='return submitUpload("docImgUploadList[${status.index}].file", "${requriedDoc.docType}");'/>
						<!-- <input type="reset" value="Clear" onclick='resetForm()'/> -->
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5" class="smalltextgray">
					<strong><span class="contenttext_red">*</span> Support file types :  ${fn:join(ltsDocCaptureForm.allowedExtensions, ', ') }</strong>
				</td>
			</tr>
			<tr>
				<td colspan="5" class="smalltextgray">
					<strong><span class="contenttext_red">*</span> Max file size : 
						<fmt:formatNumber type="number" pattern="0.0'MB'" value="${ ltsDocCaptureForm.maxUploadSize/1024/1024 }" />
					</strong>
				</td>
			</tr>
		</table>
		</td>
		<table class="paper_w2" width="100%" border="0" cellspacing="0">
			<tr>
				<td align="right">
					<div class="func_button"><a name="continue" href="#" class="nextbutton3" onClick="submitPage(); return false;" >Continue</a> &nbsp;</div>
				</td>
			</tr>
		</table>
	</tr>
</table>
</form:form>

<%---------------------------------------  end content -------------------------------------------%>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp" %>