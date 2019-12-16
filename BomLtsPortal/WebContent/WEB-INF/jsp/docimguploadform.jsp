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
<%@ include file="hkt_header.jsp"%>

<html>
<head>
 <script>
 	var completeMsg = '<spring:message code="uploadform.complete"/>';
 	var processMsg = '<spring:message code="uploadform.process"/>';
 </script>
 <script src="js/fileUpload.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
</head>
<body>
<div id="wrapper">
<!--content-->
<div id="content">
	<!--flow nav-->
	<div class="flow">
		<ul>
		<li class="flow_active">
			<spring:message code="uploadform.title"/>
		</li>
		</ul>
	</div>

	<div id="main" style="min-height: 200px !important;">
	
		<div id="middle_content" style="visibility: visible;padding:0px 10px 10px 10px;">
		<header>
            <h2><spring:message code="uploadform.header1" /></h2>
        </header>
        <div class="container">
 
            <div class="upload_form_cont">
                <form:form modelAttribute="docImgUpload" commandName="docImgUploadForm" name="docImgUploadForm" id="docImgUploadForm"  enctype="multipart/form-data" method="post" action="docimgupload.html">
                
                    <div> 
                        <div><input name="file" type="file" value="<spring:message code="uploadform.browse" />" id="fileUpload" onchange="fileSelected();" /></div>
                    </div>
                    <div>
                        <input id="submitButton" type="button" value="<spring:message code="uploadform.upload" />" onclick="startUploading()" /> 
                    </div>
                    <div id="fileinfo">
                        <div id="filename"></div>
                        <div id="filesize"></div>
                        <div id="filetype"></div>
                        <div id="filedim"></div>
                    </div>
                    <div>
                    <br>
                   <spring:message code="uploadform.remark" />
                    <ul> 
                         <li><spring:message code="uploadform.remark.text1" /></li> 
                         <li><spring:message code="uploadform.remark.text2" /></li>
                    </ul>
                    </div>
					<div id="error"><spring:message code="uploadform.error" /></div>
					<div id="error2"><spring:message code="uploadform.error2" /></div>
					<div id="warnhtml5notsupport"><spring:message code="uploadform.warnhtml5notsupport" /></div>
					<div id="abort"><spring:message code="uploadform.abort" /></div>
					<div id="warnsize"><spring:message code="uploadform.warnsize" /></div>
                    <div id="errors"><form:errors path="file" cssClass="error" element="div"/></div>
                    
                    <div id="progress_info">
                        <div id="progress"></div>
                        <div id="progress_percent">&nbsp;</div>
                        <div class="clear_both"></div>
                        <div>
                            <div id="speed">&nbsp;</div>
                            <div id="remaining">&nbsp;</div>
                            <div id="b_transfered">&nbsp;</div>
                            <div class="clear_both"></div>
                        </div>
                        <div id="upload_response"></div>
                    </div>
                </form:form>
				<img id="preview" />
            </div>
        </div>
		</div>
	</div>
</div>
<div id="floating_bar" style="margin-left: 0px;">
				<div class="grad"></div>
					<div id="bottom_content"> 
						<table width="100%" style="bottom: -8pt;position: relative;">  
						<tbody>
							<tr> 
								<td width="60%">&nbsp;</td>
                                <td width="20%">&nbsp;</td>
								<td width="3%" align="center"></td>
								<td width="20%" align="left">		
								</td>
							</tr>
						</tbody>
                        </table>
					</div>
                    
                    
                      
					<img src="images/${lang}/bottom_bar.png" />
</div>
</div>
</body>
</html>
