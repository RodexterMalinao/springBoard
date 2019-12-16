<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="css/typographyEN.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nowTV Channel</title>
</head>


<body>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr bgcolor="#FFFFFF">
			<td width="100%">
		
				<c:choose>
					<c:when test="${not empty requestScope.errorMsg || requestScope.errorMsg != null}" >
						<table width="100%" border="0" cellpadding="4" cellspacing="2" class="contenttext">
							<tr>
								<td width="10%"></td>
								<td align="center"><b>${requestScope.errorMsg}</b></td>
								<td width="10%"></td>
							</tr>
						</table>
					</c:when>
					
					
					<c:when test="${not empty requestScope.channelDetailList}" >
						<table width="100%" border="0" cellpadding="4" cellspacing="2" class="contenttext">
							<tr height="40" bgcolor="#6495ED">
								<td width="10%" align="center"><b>Ch No.</b></td>
								<td width="60%" align="center"><b>Channel Name</b></td>
							</tr>
							<c:forEach items="${requestScope.channelDetailList}" var="channelDetail" varStatus="channelDetailCount">
								<c:choose>
						            <c:when test="${channelDetailCount.count % 2 != 0}">
						                <c:set var="rowColor" value="#FFFFFF"  />
						            </c:when>
						            <c:otherwise>
						                <c:set var="rowColor" value="#CCEEFF"  />
						            </c:otherwise>
						        </c:choose>
								<tr bgcolor='<c:out value="${rowColor}" />'>
									<td width="10%" align="center"><c:out value="${channelDetail.channelId}"/></td>
									<td width="60%" align="center"><c:out value="${channelDetail.channelHtml}"/></td>
								</tr>
							</c:forEach>	
	
							<tr>
								<td width="10%" align="center"> </td>
								<td width="60%" align="right"><b>Total Price : ${requestScope.arpu} </b></td>
							</tr>
						</table>
					</c:when>
					
					
					<c:otherwise>
						<table width="100%" border="0" cellpadding="4" cellspacing="2" class="contenttext">
							<tr>
								<td width="10%"></td>
								<td align="center"><b>Fail to retrieve nowTV Information</b></td>
								<td width="10%"></td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</td>
		<tr>
	</table>
</body>
</html>