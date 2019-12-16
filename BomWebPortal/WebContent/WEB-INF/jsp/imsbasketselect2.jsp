<%-- <%@ include file="/WEB-INF/jsp/imscommon/header.jsp" %> --%>
<!doctype html>
<html>
	<head>
		<script type="text/javascript" charset="utf-8" src="js/inputInterceptor.js"></script>
		<script src="js/imsbasketselect.js"></script> 
		<link rel="stylesheet" type="text/css" href="css/imsbasketselect.css" >
		
	</head>
	<body>
		<div id="tabs" style="width:98%; " class="paper_w">
			<div id="s_line_text">Basket Selection</div>
			<div class="filter" style="width:100%">
				<div class="inline" style="width:10%">Bandwidth:</div> 
				<div class="inline" style="width:80%">
					<c:forEach var="bw" items="${bandwidthParamList}" varStatus="i">
						<div class="inline filter_item">   
							<fmt:parseNumber var="bw2" type="number" value="${bw}" /> 
							<input type="CHECKBOX" name="bandwidthItem" value="${fn:replace(bw, '.', '_')}M" <c:if test="${i.first or bw2>=100}">checked</c:if>>${bw}M 
						</div>
					</c:forEach>
				</div>
				<div class="inline" style="width:10%">Plan Type:</div> 
				<div class="inline" style="width:80%">
				<c:set var="priceAdjust" value="0"/> 
					<c:forEach var="p" items="${imsPlanTypeListNew}" varStatus="i"> 
						<div class="inline filter_item">
							<input type="checkbox" name="planTypeBox" value="type${p.planType}" 
								<c:if test="${p.planType eq '1' or priceAdjust eq 0}">      
								checked 
								</c:if>> 	
								${p.planTypeDesc} 
							<c:if test="${p.planType eq '0'}"><c:set var="priceAdjust" value="1"/></c:if>
						</div>   
					</c:forEach>
				</div>
			</div>
			
			<c:set var="parm1" value="0"/>
			<c:set var="parm2" value="0"/>
			<c:set var="parm3" value="0"/>
				
			<div class="offers"> 
				<c:forEach var="b" items="${ImsBasketList}" varStatus="i">
					<c:if test="${b.bandwidth ne parm2}">
						<c:out value = "<div class='${b.bandwidth}M bwFilter' style='display:none;'>" escapeXml="false"/> 
						<c:out value = "<div style='margin-left: 10px;' id='s_line_text'><b>${b.bandwidth}M</b></div>" escapeXml="false"/>
						<c:set var="parm2" value="${b.bandwidth}"/>
						<c:set var="parm3" value="0"/>
					</c:if>
					<c:if test="${b.planType ne parm3}">
						<c:out value = "<div class='type${b.planType} planFilter'>" escapeXml="false"/> 
						<div style="margin-left: 20px;" id="s_line_text2"><b>${b.planTypeDesc}</b></div>
						<c:set var="parm3" value="${b.planType}"/>
					</c:if>  
					<div id="${b.basketId}" class="imgcaption round_10 ${fn:replace(b.bandwidth, '.', '_')}M type${b.planType}"> 
						<div align="right"><font size="2"><b>(${b.offerCode})</b></font></div>
						<p><c:if test="${not empty b.imagePath and b.imagePath ne 'NA' and b.imagePath ne 'N/A'}"><img class="imgleft" src="img/${b.imagePath}"></c:if></p> 
						
						<c:choose>
							<c:when test="${b.contractPeriod eq 0}"> 
								<div class="offerselection_title">${b.mthToMthText}</div><br>
							</c:when> 
							<c:otherwise>
								<div class="offerselection_title">${b.contractPeriod}Mth : ${b.mthFixText}</div><br>
							</c:otherwise>
						</c:choose>
						<div class="offerselection_content">${b.bandwidth}M ${b.summary}</div>
						<div class="offerselection_content"><br/><br/><br/><br/></div>
						<c:choose>
						<c:when test="${IMS_BasketID eq b.basketId}"><a class="selected_button" value="${selectedBasketCssClasses}">Currently Selected</a></c:when>  
						<c:otherwise> <a class="func_button" value='${b.basketId}'>BUY NOW</a></c:otherwise>
						</c:choose>
					</div>
					<c:if test="${i.last || ImsBasketList[i.index+1].bandwidth ne parm2 || ImsBasketList[i.index+1].planType ne parm3}">
						<c:out value = "</div>" escapeXml="false"/>
					</c:if>
					<c:if test="${i.last || ImsBasketList[i.index+1].bandwidth ne parm2}">  
						<c:out value = "</div>" escapeXml="false"/>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</body>
</html>
<%@ include file="/WEB-INF/jsp/imscommon/footer.jsp" %>