<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("P3P","CP=CAO PSA OUR");
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page
	import="org.springframework.web.servlet.support.RequestContextUtils,java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="format-detection" content="telephone=no"/>
<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='EYE'}">
	<link rel="stylesheet" type="text/css" href="./css/style.css" /> 
</c:if>
<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='DEL'}">
	<link rel="stylesheet" type="text/css" href="./css/style_del.css" /> 
</c:if>
<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='ALL'}">
	<link rel="stylesheet" type="text/css" href="./css/style_all.css" /> 
</c:if>
<c:if test="${sessionOnlineOrderCapture.serviceTypeInd==null}">
	<link rel="stylesheet" type="text/css" href="./css/style.css" /> 
</c:if>
<script src="js/jquery-1.8.2.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	if ( parent != null ){
		parent.setCartSize($("#carttable").height(), $("#carttable").width());
	}
});
</script>
<title>Cart</title>
</head>
<body>
<form:form id="shoppingCartForm" name="shoppingCartForm" method="POST" commandName="shoppingCartForm">
	<c:set var="isRecurrent" value="${ not empty shoppingCartForm.basketDetail.recurrentAmt && shoppingCartForm.basketDetail.recurrentAmt != '0'}"/>
	<table cellspacing="0" id="carttable">
	<tr>
		<th class="cartheader" colspan=3><spring:message code="cart.title"/></th>
	</tr>
	<tr class="rowA">
		<td class="colA"><spring:message code="cart.header1"/></td>
		<td class="colB" ><spring:message code="cart.header2"/></td> 
	</tr>
	<c:if test="${empty shoppingCartForm.basketDetail}">
		<tr class="rowB">
			<td class="colA">- </td>
			<td class="colB">- </td>
		</tr>
	</c:if>
	<c:if test="${not empty shoppingCartForm.basketDetail}">
		<tr class="rowB">
			<td class="colA">${shoppingCartForm.basketDetail.html}</td>
			<td class="colB">
				<c:if test="${isRecurrent}">
					${shoppingCartForm.basketDetail.recurrentAmtTxt}
				</c:if>
				<c:if test="${!isRecurrent}">
					${shoppingCartForm.basketDetail.mthToMthAmtTxt}
				</c:if>
			</td>
		</tr>
	</c:if>
	<c:forEach var="contentItem" items="${shoppingCartForm.contentItemList}" varStatus="status">
		<tr class="rowB">
			<td class="colA">${contentItem.itemDisplayHtml}</td>
			<td class="colB">
				${contentItem.displayAmt}
			</td>
		</tr>
	</c:forEach>
	<tr><td colspan=3><hr/></td></tr>
	<tr class="rowA">
		<td class="colA"><spring:message code="cart.header3"/></td>
		<td class="colB" ><spring:message code="cart.header4"/></td>
	</tr>
	<c:if test="${empty shoppingCartForm.premiumItemList}">
		<tr class="rowB">
			<td class="colA">- </td>
			<td class="colB">- </td>
		</tr>
	</c:if>
	<c:forEach var="premItem" items="${shoppingCartForm.premiumItemList}" varStatus="status">
		<tr class="rowB">
			<td class="colA">${premItem.itemDisplayHtml}</td>
			<td class="colB">
				${premItem.displayAmt}
			</td>
		</tr>
	</c:forEach>
	<tr><td colspan=3><hr/></td></tr>
	<tr class="rowA">
		<td class="colA"><spring:message code="cart.header5"/></td>
		<td class="colB" ><spring:message code="cart.header6"/></td>
	</tr>
	<c:if test="${empty shoppingCartForm.vasMoovItems
				&& empty shoppingCartForm.vasOtherItems
				&& empty shoppingCartForm.vasNowTvSpecItems
				&& empty shoppingCartForm.vasNowTvPayItems}">
		<tr class="rowB">
			<td class="colA">- </td>
			<td class="colB">- </td>
		</tr>
	</c:if>
	 <c:forEach var="vasMoovItem" items="${shoppingCartForm.vasMoovItems}" varStatus="status">
		<tr class="rowB">
			<td class="colA">${vasMoovItem.itemDesc}</td>
			<td class="colB">
				${vasMoovItem.displayAmt}
			</td>
		</tr>
	</c:forEach>
	 <c:forEach var="vasOtherItem" items="${shoppingCartForm.vasOtherItems}" varStatus="status">
		<tr class="rowB">
			<td class="colA">${vasOtherItem.itemDesc}</td>
			<td class="colB">
				${vasOtherItem.displayAmt}
			</td>
		</tr>
	</c:forEach>
	 <c:forEach var="vasNowTvSpecItem" items="${shoppingCartForm.vasNowTvSpecItems}" varStatus="status">
		<tr class="rowB">
			<td class="colA">${vasNowTvSpecItem.channelHtml}</td>
			<td class="colB">
				${vasNowTvSpecItem.itemDetail.displayAmt}
			</td>
		</tr>
	</c:forEach>
	 <c:forEach var="vasNowTvPayItem" items="${shoppingCartForm.vasNowTvPayItems}" varStatus="status">
		<tr class="rowB">
			<td class="colA">${vasNowTvPayItem.channelHtml}</td>
			<td class="colB">
				${vasNowTvPayItem.itemDetail.displayAmt}
			</td>
		</tr>
	</c:forEach>
	<tr><td colspan=3><hr/></td></tr>
	<tr class="rowA">
		<td class="colA"><spring:message code="cart.header7"/></td>
		<td class="colB" >
		<c:if test="${empty shoppingCartForm.serviceCharge}">
			<spring:message code="cart.na.fee"/>
		</c:if>
		<c:if test="${not empty shoppingCartForm.serviceCharge}">
			${shoppingCartForm.serviceCharge}
		</c:if>
		</td>
	</tr>
	<tr class="rowB">
		<td class="colA"></td>
		<td class="colB"></td>
	</tr>
	<tr class="rowA">
		<td class="colA"><spring:message code="cart.header8"/></td>
		<td class="colB" >${shoppingCartForm.totalAmount}</td>
	</tr>
	<tr class="rowB">
		<td class="colA"></td>
		<td class="colB"></td>
	</tr>
	<tr class="rowA">
		<td class="colA"><spring:message code="cart.header9"/></td>
		<td class="colB" >
		<c:if test="${empty shoppingCartForm.installFee}">
			<spring:message code="cart.na.fee"/>
		</c:if>
		<c:if test="${not empty shoppingCartForm.installFee}">
			${shoppingCartForm.installFee}
		</c:if></td>
	</tr>
	<tr class="rowB">
		<td class="colA"></td>
		<td class="colB"></td>
	</tr>
	</table>
	
</form:form>
</body>
</html>