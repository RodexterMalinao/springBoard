<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>

<html>
<head>
</head>
<body>
<div id="wrapper">
<!--content-->
<div id="content">
	<!--flow nav-->
	<div class="flow">
		<ul>
		<li class="flow_active">
			<c:if test="${not empty title}"><spring:message code="${title}"/></c:if>
			<c:if test="${empty title}"><spring:message code="msg.title.sysmsg"/></c:if>
		</li>
		</ul>
	</div>

	<div id="main" style="min-height: 200px !important;">
	
		<div id="middle_content" style="visibility: visible;padding:0px 10px 10px 10px;">
		<c:if test="${not empty msgCode}"><spring:message code="${msgCode}" /></c:if>
		<c:if test="${not empty msg}">${msg}</c:if>
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
