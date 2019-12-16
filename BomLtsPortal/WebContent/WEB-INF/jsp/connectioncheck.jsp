<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connection Check</title>
</head>
<body>

<c:choose>
	<c:when test="${isProcessing}">
		<p>Previous request is processing</p>
	</c:when>
	<c:otherwise>
		<p><c:choose>
			<c:when test="${connectionFail eq '0'}">
					All connections are ready.
				</c:when>
			<c:otherwise>
				<font color="red">${connectionFail} out of
				${connectionChecked} connections failed.</font>
			</c:otherwise>
		</c:choose></p>
		<!--		<p>-->
		<!--			Maintenance Start Date : ${mainStartDate} , Maintenance End Date : ${mainEndDate}-->
		<!--		</p>-->
		<p>
				<c:choose>
					<c:when test="${BomWebPortalDS}">BomWebPortalDS : Connected</c:when>
					<c:otherwise>
						<font color="red">BomWebPortalDS : Not connected</font>
					</c:otherwise>
				</c:choose>
		</p>
		<p>
				<c:choose>
					<c:when test="${BomDS}">BomDS : Connected</c:when>
					<c:otherwise>
						<font color="red">BomDS : Not connected</font>
					</c:otherwise>
				</c:choose>
		</p>
		<p>
				<c:choose>
					<c:when test="${UamsDS}">UamsDS : Connected</c:when>
					<c:otherwise>
						<font color="red">UamsDS : Not connected</font>
					</c:otherwise>
				</c:choose>
		</p>
		<p>
				<c:choose>
					<c:when test="${Csp}">CS Portal : Ready</c:when>
					<c:otherwise>
						<font color="red">CS Portal : Not Ready</font>
					</c:otherwise>
				</c:choose>
		</p>
		<p>
				<c:choose>
					<c:when test="${Ceks}">Ceks : Ready</c:when>
					<c:otherwise>
						<font color="red">Ceks : Not ready</font>
					</c:otherwise>
				</c:choose>
		</p>
		<p>
				<c:choose>
					<c:when test="${DataFile}">Data File : Ready</c:when>
					<c:otherwise>
						<font color="red">Data File : Not ready</font>
					</c:otherwise>
				</c:choose>
		</p>
	</c:otherwise>
</c:choose>


</body>
</html>