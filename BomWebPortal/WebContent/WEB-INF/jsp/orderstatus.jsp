<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<c:if test='${order.orderSumLob == "MOB"}'>
	<c:if
		test='${(order.checkPoint == "999" || empty order.checkPoint) 
		&& !fn:startsWith(order.orderId, "R") 
		&& !fn:startsWith(order.orderId, "P") 
		&& fn:length(order.orderId) == 11}'>
	Fallout(<my:code id="${order.reasonCd}" codeType=""
			source="ORDER_STATUS"></my:code>)
</c:if>
	<c:if
		test='${(order.checkPoint != "999" && not empty order.checkPoint) 
		&& !fn:startsWith(order.orderId, "R") 
		&& !fn:startsWith(order.orderId, "P") 
		&& fn:length(order.orderId) == 11}'>
	Pending(<my:code id="${order.checkPoint}" codeType=""
			source="ORDER_STATUS"></my:code>)
</c:if>
	<c:if
		test="${fn:startsWith(order.orderId, 'R') 
		|| fn:startsWith(order.orderId, 'P')}">
		<c:choose>
			<c:when test='${order.orderStatus == "INITIAL"}'>
				<spring:message code="orderStatus.INITIAL" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "SIGNOFF"}'>
				<spring:message code="orderStatus.SIGNOFF" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "PENDING"}'>
				<spring:message code="orderStatus.PENDING" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "PROCESS"}'>
				<spring:message code="orderStatus.PROCESS" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "PARTIAL"}'>
				<spring:message code="orderStatus.PARTIAL" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "SUCCESS"}'>
				<spring:message code="orderStatus.SUCCESS" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "FAILED"}'>
				<spring:message code="orderStatus.FAILED" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "VOID"}'>
				<spring:message code="orderStatus.VOID" text="" />
			</c:when>
			
			<c:when test='${order.orderStatus == "01"}'>
				<spring:message code="orderStatus.PENDING" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "02"}'>
				<spring:message code="orderStatus.SUCCESS" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "04"}'>
				<spring:message code="orderStatus.VOID" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "05"}'>
				<spring:message code="orderStatus.TIMEOUT" text="" />
			</c:when>
			<c:when test='${order.orderStatus == "99"}'>
				Fallout - <sb-util:codelookup codeType="ORD_FALLOUT_CODE" codeId="${order.reasonCd }"/>
			</c:when>
			<c:otherwise>${order.orderStatus}</c:otherwise>
		</c:choose>
	</c:if>
</c:if> <c:if test='${order.orderSumLob == "IMS"}'>
	<spring:message code="ims.orderStatus.${order.orderStatus}"
		text="${order.orderStatus}" />
	<c:choose>
		<c:when test='${order.orderStatus == "10"}'>
			<br />(<spring:message
				code="ims.orderSuspendCode.${order.reasonCd}"
				text="${order.reasonCd}" />)
	</c:when>
		<c:when test='${order.orderStatus == "20" || order.orderStatus == "21"}'>
			<br />(<spring:message
				code="ims.orderCancelCode.${order.reasonCd}"
				text="${order.reasonCd}" />)
	</c:when>
		<c:when test='${order.orderStatus == "30"}'>
			<br />(<spring:message
				code="ims.orderApproveCode.${order.reasonCd}"
				text="${order.reasonCd}" />)
	</c:when>
	</c:choose>
</c:if> <c:if test='${order.orderSumLob == "LTS"}'>
	<c:choose>
		<c:when
			test='${order.orderStatus == "S" && order.reasonCd != null}'>
			<spring:message
				code="lts.orderStatus.${order.orderStatus}.${order.reasonCd}"
				text="${order.reasonCd}" />
		</c:when>
		<c:otherwise>
			<spring:message code="lts.orderStatus.${order.orderStatus}"
				text="${order.orderStatus}" />
		</c:otherwise>
	</c:choose>
</c:if> &nbsp;