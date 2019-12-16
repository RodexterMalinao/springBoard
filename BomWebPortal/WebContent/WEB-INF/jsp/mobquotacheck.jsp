<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<table width="100%" border="0" cellspacing="0">
	<tr>
		<td class="table_title">
			<spring:message code="label.quotaInUse"/>
		</td>
	</tr>
	<tr>
		<td>
			<c:choose>
			<c:when test="${mobQuotaUsageException != null}">
				<span class="error">
					Quota Usage Search is not available: <br/ >
					<c:out value="${mobQuotaUsageException}" />
				</span>
			</c:when>
			<c:otherwise>
				<table width="100%" border="1" cellspacing="0"
					class="contenttext" bgcolor="#FFFFFF">
					<tr class="contenttextgreen">
						<td class="table_title"><spring:message code="label.quotaType"/></td>
						<td class="table_title"><spring:message code="label.quotaEntitle"/></td>
						<td class="table_title"><spring:message code="label.quotaInUse"/></td>
						<td class="table_title"><spring:message code="label.quotaId"/></td>
					</tr>
					<c:choose>
						<c:when test='${empty mobQuotaUsageArray}'>
								<tr>
									<td colspan="4">
										<div align="left">No Record Found.</div></td>
								</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${mobQuotaUsageArray}" var="quotaList">
								<tr>
									<td>${quotaList.engDesc} / ${quotaList.chiDesc}</td>
									<td>${quotaList.ceilCnt}</td>
									<td>${quotaList.used}</td>
									<td>${quotaList.quotaId}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
			</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
