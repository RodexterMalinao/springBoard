<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/custom-theme/ltssb.css" rel="stylesheet" type="text/css" />
<link href="css/custom-theme/jquery-ui-1.10.4.custom.css" rel="stylesheet" />
</head>
<body>
	<div style="text-align: right">
		<a href="${theClubCatalogueUrl}" target="_blank">
			<u><spring:message code="lts.acq.clubMemEnq.theClubCatalogue" arguments="" htmlEscape="false"/></u>
		</a>
	</div>
	<div id="tabs-3" style="padding: 5px">
		<c:if test="${not empty theClubInfo.rtnMsg && theClubInfo.rtnCode == 'SUCC'}">
				<div id="s_line_text"><b><spring:message code="lts.acq.clubMemEnq.clubMembership" arguments="" htmlEscape="false"/></b></div>
		<table style="margin: 10px;"> 
		<tbody><tr>
			<td><input type="radio" value="DOCUMENT" checked="true" name="searchType" id="byDocNum"><spring:message code="lts.acq.clubMemEnq.byDocTypeNum" arguments="" htmlEscape="false"/></td>
			<td>
				<input type="text" size="10" value="${docType}" name="clubDocNum" id="idDocNum" readonly="readonly">
			</td>
			<td width="25%"><input type="text" size="30" value="${docNum}" name="clubDocNum" id="idDocNum" readonly="readonly">
			</td>		
		</tr>
		</tbody>
		</table>
		<div id="s_line_text"></div>
		</c:if>
		<c:if test="${theClubInfo.rtnCode == 'ERROR' || (not empty theClubInfo.rtnMsg && theClubInfo.rtnCode != 'SUCC')}">
			<div class="ui-state-highlight ui-corner-all" style="margin: 10px; padding: 0 .7em;">
				<p>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				</p>
				<div class="contenttext">
					<b>${theClubInfo.rtnMsg}</b>
				</div>
				<p></p>
			</div>
		</c:if>
		<c:if test="${not empty theClubInfo.rtnMsg && theClubInfo.rtnCode == 'SUCC'}">
		<table id="memberInfo">
			<tbody>
				<tr>
					<td width="25%">
					<table style="border-collapse:collapse;"> 
					<tbody><tr>
						<td><spring:message code="lts.acq.clubMemEnq.memberNo" arguments="" htmlEscape="false"/>:</td>
					</tr>
					</tbody></table>
					</td>
					<td><span id="searchMemberNo">${theClubInfo.memberId}</span></td>
				</tr>
				<tr>
					<td><spring:message code="lts.acq.clubMemEnq.phantom" arguments="" htmlEscape="false"/>:</td>
					<td><span id="phantom">${theClubInfo.phantom}</span></td>
				</tr>
				<tr>
					<td><spring:message code="lts.acq.clubMemEnq.tier" arguments="" htmlEscape="false"/>:</td>
					<td><span id="tier">${theClubInfo.tier}</span></td>
				</tr>
				<tr>
					<td><spring:message code="lts.acq.clubMemEnq.name" arguments="" htmlEscape="false"/>:</td>
					<td><span id="name">${theClubInfo.name}</span></td>
				</tr>
				<tr>
					<td><spring:message code="lts.acq.clubMemEnq.loginId" arguments="" htmlEscape="false"/>:</td>
					<td><span id="resultLoginId">${theClubInfo.loginId}</span></td>
				</tr>
				<tr>
					<td><spring:message code="lts.acq.clubMemEnq.docTypeNum" arguments="" htmlEscape="false"/>:</td>
					<td><span id="searchDocTypeNum">${theClubInfo.idDocType} / ${theClubInfo.maskedIdDocNum}</span></td>
				</tr>
				<tr>
					<td><spring:message code="lts.acq.clubMemEnq.currentPtBalance" arguments="" htmlEscape="false"/>:</td>
					<td><span id="pointBalance">${theClubInfo.currentPointBal}</span></td>				
				</tr>
			</tbody>
		</table>
		<br/>
		<div style="width:100% !important;">	
		 	<table border="2" id="pointQuarter" class="table_style2 couponInd">
			 	<tbody>
			 		<tr>
				 		<th><spring:message code="lts.acq.clubMemEnq.pts" arguments="" htmlEscape="false"/></th>
				 		<th><spring:message code="lts.acq.clubMemEnq.startDate" arguments="" htmlEscape="false"/></th>
				 		<th><spring:message code="lts.acq.clubMemEnq.endDate" arguments="" htmlEscape="false"/></th>
				 		<th><spring:message code="lts.acq.clubMemEnq.expiryDate" arguments="" htmlEscape="false"/></th>
			 		</tr>
			 		<c:if test="${empty theClubInfo.pointQuarter}">
			 			<tr><td colspan="4" align="centre"><spring:message code="lts.acq.clubMemEnq.noPtsHistory" arguments="" htmlEscape="false"/></td></tr>
			 		</c:if>
			 		<c:if test="${not empty theClubInfo.pointQuarter}">
			 			<c:forEach items="${theClubInfo.pointQuarter}" var="pointHist" varStatus="status">
						 	<tr>
							 	<td>${pointHist.point_bal}</td>
							 	<td>${pointHist.start_date}</td>
							 	<td>${pointHist.end_date}</td>
							 	<td>${pointHist.expiry_date}</td>
						 	</tr>
			 			</c:forEach>
			 		</c:if>
			 	</tbody>
		 	</table>
		</div>
		</c:if>
	</div>
	<div style="bottom: 10px; text-align: center; position: relative; margin-top: 20px;">
		<div class="func_button" onclick="window.close();"><a href="#" id="quitBtn"><spring:message code="lts.custenq.quit" arguments="" htmlEscape="false"/></a></div>
	</div>
</body>
</html>