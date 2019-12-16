<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltsbasketselection.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' ? 3 : 5}" />
</jsp:include>

<form:form method="POST" id="ltsBasketSelectionForm" name="ltsBasketSelectionForm" commandName="ltsBasketSelectionCmd" autocomplete="off">

<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<form:hidden path="selectedBasketId" />
<form:hidden path="basketTabIndex" />
<form:hidden path="formAction" />

<div id="s_line_text"><spring:message code="lts.offerDtl.basketSeletion" text="Basket Selection"/></div>

<table width="98%" border="0" align="center">
	<tr>
		<td width="100%">
			<c:if test="${not empty sessionScope.sessionBasketSelectionInfo.selectedDevice 
							&& not empty sessionScope.sessionBasketSelectionInfo.selectedDevice.imagePath}">
				<div class="paper_w2 round_10">
					<table cellpadding="10">
						<tr>
							<td>
								<img width="180" src='<c:out value="${sessionScope.sessionBasketSelectionInfo.selectedDevice.imagePath}"/>'>		
							</td>
							<td>
								<span class="bold">${sessionScope.sessionBasketSelectionInfo.selectedDevice.html}</span>
							</td>
						</tr>
					</table>
				</div>
			</c:if>
		</td>
	</tr>
	<tr>
		<td>
			<c:if test='${not empty requestScope.errorMsgList}'> 
				<div id="errorDiv" style="width: 70%;">
					<div id="error_profile" class="ui-widget" style="visibility: visible;">
						
						<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
							<c:forEach items="${requestScope.errorMsgList}" var="errorMsg">
								<p>
									<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="error_profile_msg" class="contenttext">
									${errorMsg}
								</div>
								<p></p>
							</c:forEach>
						</div>
						
					</div>
				</div>
				<br/>
			</c:if>
		</td>
	</tr>
  	<tr>
  	 	<td width ="100%" align="center" >
  			<div id="tabs" class="paper_w ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible" style="width:98%;">
				<ul>
					
					<c:if test="${ltsBasketSelectionCmd.basketTab == 'UPG_MASS'}">
						<li><a id="hotOfferTab" href="#tabs-hot"><span class="contenttext"><spring:message code="lts.offerDtl.hotOffer" text="Hot Offer"/></span></a></li>
						<li><a id="regularOfferTab" href="#tabs-regular"><span class="contenttext"><spring:message code="lts.offerDtl.regularOffer" text="Regular Offer"/></span></a></li>
						<li><a id="expiringOfferTab" href="#tabs-expiring"><span class="contenttext"><spring:message code="lts.offerDtl.expiringOffer" text="Expiring Offer"/></span></a></li>
					</c:if>
					
					<c:if test="${ltsBasketSelectionCmd.basketTab == 'RET_MASS_EYE' || ltsBasketSelectionCmd.basketTab == 'RET_PT_EYE'}">
						<li><a id="hotOfferTab" href="#tabs-hot"><span class="contenttext"><spring:message code="lts.offerDtl.hotOffer" text="Hot Offer"/></span></a></li>
						<li><a id="premiumOfferTab" href="#tabs-premium"><span class="contenttext"><spring:message code="lts.offerDtl.premOffer" text="Premium Offer"/></span></a></li>
						<li><a id="rebateOfferTab" href="#tabs-rebate"><span class="contenttext"><spring:message code="lts.offerDtl.rebateOffer" text="Rebate Offer"/></span></a></li>
						<li><a id="otherOfferTab" href="#tabs-other"><span class="contenttext"><spring:message code="lts.offerDtl.otherOffer" text="Other Offer"/></span></a></li>
					</c:if>
					
					<c:if test="${ltsBasketSelectionCmd.basketTab == 'RET_MASS_DEL' || ltsBasketSelectionCmd.basketTab == 'RET_PT_DEL'}">
						<li><a id="hotOfferTab" href="#tabs-hot"><span class="contenttext"><spring:message code="lts.offerDtl.hotOffer" text="Hot Offer"/></span></a></li>
						<li><a id="premiumOfferTab" href="#tabs-premium"><span class="contenttext"><spring:message code="lts.offerDtl.premOffer" text="Premium Offer"/></span></a></li>
						<li><a id="rebateOfferTab" href="#tabs-rebate"><span class="contenttext"><spring:message code="lts.offerDtl.rebateOffer" text="Rebate Offer"/></span></a></li>
						<li><a id="rebateCouponOfferTab" href="#tabs-rebateCoupon"><span class="contenttext"><spring:message code="lts.offerDtl.rebateCouponOffer" text="Rebate + Coupon Offer"/></span></a></li>
						<c:if test="${ltsBasketSelectionCmd.basketTab == 'RET_MASS_DEL'}">
							<li><a id="couponIddOfferTab" href="#tabs-couponIdd"><span class="contenttext"><spring:message code="lts.offerDtl.couponIddOffer" text="Coupon + IDD Offer"/></span></a></li>
						</c:if>
						<c:if test="${ltsBasketSelectionCmd.basketTab == 'RET_PT_DEL'}">
							<li><a id="iddOfferTab" href="#tabs-idd"><span class="contenttext"><spring:message code="lts.offerDtl.iddOffer" text="IDD Offer"/></span></a></li>
							<li><a id="iddRebateOfferTab" href="#tabs-rebateIdd"><span class="contenttext"><spring:message code="lts.offerDtl.rebateIddOffer" text="Rebate + IDD Offer"/></span></a></li>
						</c:if>
					
						<li><a id="otherOfferTab" href="#tabs-other"><span class="contenttext"><spring:message code="lts.offerDtl.otherOffer" text="Other Offer"/></span></a></li>
					</c:if>
					
				</ul>
				
				<div id="filterDiv" align="left" style="padding-left: 30px;" class="bold">
					<br/>
					    OS Type: <form:checkbox path="filterOsTypeAndroid"/> Android &nbsp; <form:checkbox path="filterOsTypeiOS"/> iOS 
					<br/>
					<c:if test="${sessionScope.sessionLtsOrderCapture.orderType == 'SBR' }">
						<form:checkbox path="filterContractMthGe24"/> >= 24 <spring:message code="lts.offerDtl.months" text="months"/>
						&nbsp;
						<form:checkbox path="filterContractMthEq21"/> 21 <spring:message code="lts.offerDtl.months" text="months"/>
						&nbsp;
						<form:checkbox path="filterContractMthEq18"/> 18 <spring:message code="lts.offerDtl.months" text="months"/>
						&nbsp;
						<form:checkbox path="filterContractMthEq15"/> 15 <spring:message code="lts.offerDtl.months" text="months"/>
						&nbsp;
						<form:checkbox path="filterContractMthLe12"/> <= 12 <spring:message code="lts.offerDtl.months" text="months"/>
						&nbsp;	
					</c:if>
					
					&nbsp; <spring:message code="lts.offerDtl.spOffer" text="Special Offer"/>: &nbsp;
					<form:select path="filterProjectCd">
						<form:option value="">--</form:option>
						<c:if test="${not empty filterProjectCdList }">
							<form:options items="${ filterProjectCdList}" itemLabel="itemValue" itemValue="itemKey"/>
						</c:if>
					</form:select>	
					
					&nbsp;&nbsp; <a href="#"><div id="searchBtn" class="func_button"><spring:message code="lts.offerDtl.search" text="Search"/></div></a>	
				</div>
				
				<div id="tabs-hot" style="display: none;">
					<c:choose>
					<c:when test="${not empty sessionScope.sessionBasketSelectionInfo.hotBasketList}">
						<c:forEach items="${sessionScope.sessionBasketSelectionInfo.hotBasketList}" var="hotBasket">
							<div class="hot imgcaption_lts round_10" title="${hotBasket.premierInd}"> 
								${hotBasket.html}
								<br><br>
								<div class="func_button" style="position: absolute; right:10px; bottom:10px;">
									<a class="selectBasket" href='${hotBasket.basketId}' ><spring:message code="lts.offerDtl.buyNow" text="Buy Now"/></a>
								</div>
							</div>
						</c:forEach>
						 <div style="clear: both;"></div>
					</c:when>
					<c:otherwise>
						<div id="warning_addr" class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="warning_addr_msg" style="font-weight: bold;">
									<c:out value="${sessionScope.sessionBasketSelectionInfo.warningMsg}" default="No Basket Found."></c:out>
								</div>
							<p></p>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
				<div id="tabs-regular" style="display: none;">
					<c:choose>
						<c:when test="${not empty sessionScope.sessionBasketSelectionInfo.regularBasketList}">
							<c:forEach items="${sessionScope.sessionBasketSelectionInfo.regularBasketList}" var="regularBasket">
								<div class="reg imgcaption_lts round_10" title="${regularBasket.premierInd}">  
									${regularBasket.html}
									<br><br>
									<div class="func_button" style="position: absolute; right:10px; bottom:10px;">
										<a class="selectBasket" href='${regularBasket.basketId}' ><spring:message code="lts.offerDtl.buyNow" text="Buy Now"/></a>
									</div>
								</div>
							</c:forEach>
							<div style="clear: both;"></div>
						</c:when>
						<c:otherwise>
						<div id="warning_addr" class="ui-widget" style="visibility: visible;">
								<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="warning_addr_msg" style="font-weight: bold;">
									<c:out value="${sessionScope.sessionBasketSelectionInfo.warningMsg}" default="No Basket Found."></c:out>
								</div>
							<p></p>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
				
				<div id="tabs-expiring" style="display: none;">
				
				</div>
				
				<div id="tabs-premium" style="display: none;">
					<c:choose>
					<c:when test="${not empty sessionScope.sessionBasketSelectionInfo.premiumBasketList}">
						<c:forEach items="${sessionScope.sessionBasketSelectionInfo.premiumBasketList}" var="premiumBasket">
							<div class="reg imgcaption_lts round_10" title="${premiumBasket.premierInd}">  
								${premiumBasket.html}
								<br><br>
								<div class="func_button" style="position: absolute; right:10px; bottom:10px;">
									<a class="selectBasket" href='${premiumBasket.basketId}' ><spring:message code="lts.offerDtl.buyNow" text="Buy Now"/></a>
								</div>
							</div>
						</c:forEach>
						<div style="clear: both;"></div>
					</c:when>
					<c:otherwise>
						<div id="warning_addr" class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="warning_addr_msg" style="font-weight: bold;">
									<c:out value="${sessionScope.sessionBasketSelectionInfo.warningMsg}" default="No Basket Found."></c:out>
								</div>
							<p></p>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
				
				
				<div id="tabs-rebate" style="display: none;">
					<c:choose>
					<c:when test="${not empty sessionScope.sessionBasketSelectionInfo.rebateBasketList}">
						<c:forEach items="${sessionScope.sessionBasketSelectionInfo.rebateBasketList}" var="rebateBasket">
							<div class="reg imgcaption_lts round_10" title="${rebateBasket.premierInd}">  
								${rebateBasket.html}
								<br><br>
								<div class="func_button" style="position: absolute; right:10px; bottom:10px;">
									<a class="selectBasket" href='${rebateBasket.basketId}' ><spring:message code="lts.offerDtl.buyNow" text="Buy Now"/></a>
								</div>
							</div>
						</c:forEach>
						<div style="clear: both;"></div>
					</c:when>
					<c:otherwise>
						<div id="warning_addr" class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="warning_addr_msg" style="font-weight: bold;">
									<c:out value="${sessionScope.sessionBasketSelectionInfo.warningMsg}" default="No Basket Found."></c:out>
								</div>
							<p></p>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
				
				
				<div id="tabs-rebateCoupon" style="display: none;">
					<c:choose>
					<c:when test="${not empty sessionScope.sessionBasketSelectionInfo.rebateCouponBasketList}">
						<c:forEach items="${sessionScope.sessionBasketSelectionInfo.rebateCouponBasketList}" var="rebateCouponBasket">
							<div class="reg imgcaption_lts round_10" title="${rebateCouponBasket.premierInd}">  
								${rebateCouponBasket.html}
								<br><br>
								<div class="func_button" style="position: absolute; right:10px; bottom:10px;">
									<a class="selectBasket" href='${rebateCouponBasket.basketId}' ><spring:message code="lts.offerDtl.buyNow" text="Buy Now"/></a>
								</div>
							</div>
						</c:forEach>
						<div style="clear: both;"></div>
					</c:when>
					<c:otherwise>
						<div id="warning_addr" class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="warning_addr_msg" style="font-weight: bold;">
									<c:out value="${sessionScope.sessionBasketSelectionInfo.warningMsg}" default="No Basket Found."></c:out>
								</div>
							<p></p>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
				
				<div id="tabs-couponIdd" style="display: none;">
					<c:choose>
					<c:when test="${not empty sessionScope.sessionBasketSelectionInfo.couponIddBasketList}">
						<c:forEach items="${sessionScope.sessionBasketSelectionInfo.couponIddBasketList}" var="couponIddBasket">
							<div class="reg imgcaption_lts round_10" title="${couponIddBasket.premierInd}">  
								${couponIddBasket.html}
								<br><br>
								<div class="func_button" style="position: absolute; right:10px; bottom:10px;">
									<a class="selectBasket" href='${couponIddBasket.basketId}' ><spring:message code="lts.offerDtl.buyNow" text="Buy Now"/></a>
								</div>
							</div>
						</c:forEach>
						<div style="clear: both;"></div>
					</c:when>
					<c:otherwise>
						<div id="warning_addr" class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="warning_addr_msg" style="font-weight: bold;">
									<c:out value="${sessionScope.sessionBasketSelectionInfo.warningMsg}" default="No Basket Found."></c:out>
								</div>
							<p></p>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>				
				
				<div id="tabs-idd" style="display: none;">
					<c:choose>
					<c:when test="${not empty sessionScope.sessionBasketSelectionInfo.iddBasketList}">
						<c:forEach items="${sessionScope.sessionBasketSelectionInfo.iddBasketList}" var="iddBasket">
							<div class="reg imgcaption_lts round_10" title="${iddBasket.premierInd}">  
								${iddBasket.html}
								<br><br>
								<div class="func_button" style="position: absolute; right:10px; bottom:10px;">
									<a class="selectBasket" href='${rebateCouponBasket.basketId}' ><spring:message code="lts.offerDtl.buyNow" text="Buy Now"/></a>
								</div>
							</div>
						</c:forEach>
						<div style="clear: both;"></div>
					</c:when>
					<c:otherwise>
						<div id="warning_addr" class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="warning_addr_msg" style="font-weight: bold;">
									<c:out value="${sessionScope.sessionBasketSelectionInfo.warningMsg}" default="No Basket Found."></c:out>
								</div>
							<p></p>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
				
				<div id="tabs-rebateIdd" style="display: none;">
					<c:choose>
					<c:when test="${not empty sessionScope.sessionBasketSelectionInfo.rebateIddBasketList}">
						<c:forEach items="${sessionScope.sessionBasketSelectionInfo.rebateIddBasketList}" var="rebateIddBasket">
							<div class="reg imgcaption_lts round_10" title="${rebateIddBasket.premierInd}">  
								${rebateIddBasket.html}
								<br><br>
								<div class="func_button" style="position: absolute; right:10px; bottom:10px;">
									<a class="selectBasket" href='${rebateIddBasket.basketId}' ><spring:message code="lts.offerDtl.buyNow" text="Buy Now"/></a>
								</div>
							</div>
						</c:forEach>
						<div style="clear: both;"></div>
					</c:when>
					<c:otherwise>
						<div id="warning_addr" class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="warning_addr_msg" style="font-weight: bold;">
									<c:out value="${sessionScope.sessionBasketSelectionInfo.warningMsg}" default="No Basket Found."></c:out>
								</div>
							<p></p>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>				

				<div id="tabs-other" style="display: none;">
					<c:choose>
					<c:when test="${not empty sessionScope.sessionBasketSelectionInfo.otherBasketList}">
						<c:forEach items="${sessionScope.sessionBasketSelectionInfo.otherBasketList}" var="otherBasket">
							<div class="reg imgcaption_lts round_10" title="${hotBasket.premierInd}">  
								${otherBasket.html}
								<br><br>
								<div class="func_button" style="position: absolute; right:10px; bottom:10px;">
									<a class="selectBasket" href='${otherBasket.basketId}' ><spring:message code="lts.offerDtl.buyNow" text="Buy Now"/></a>
								</div>
							</div>
						</c:forEach>
						<div style="clear: both;"></div>
					</c:when>
					<c:otherwise>
						<div id="warning_addr" class="ui-widget" style="visibility: visible;">
							<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
								<p>
									<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
								</p>
								<div id="warning_addr_msg" style="font-weight: bold;">
									<c:out value="${sessionScope.sessionBasketSelectionInfo.warningMsg}" default="No Basket Found."></c:out>
								</div>
							<p></p>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
				
			</div>
		</td>						
  	</tr>
</table>
</form:form>

<script type='text/javascript'>
	$(ltsbasketselection.actionPerform);
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
