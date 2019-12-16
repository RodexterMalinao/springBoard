<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="2" />
</jsp:include>

<style>
.left {
	display: inline-block;
	min-width: 100px;
}
</style>

<div class="cosContent">
	<form:form method="POST" id="ltsBasketSelectionForm"
		name="ltsBasketSelectionForm" commandName="ltsAcqBasketSelectionCmd">
		<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
		<input type="hidden" name="submitInd" value="SUBMIT" />
		<form:hidden path="currentTab" />
		<form:hidden path="selectedBasketId" />
		<form:hidden path="searchingStaffPlan" id="searchingStaffPlan" />
		<div id="s_line_text"><spring:message code="lts.acq.basketSelection.basketSelection" htmlEscape="false"/></div>

		<table width="100%" border="0">
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
				</c:if>
				</td>
			</tr>
			<tr>
				<td width="100%" align="left">
					<div id="tabs"
						class="paper_w ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible"
						style="width: 98%;">
						<ul>
							<li><a id="hotOfferTab" 
								href="#tabs-1"><span class="contenttext"><spring:message code="lts.acq.basketSelection.hotOffer" htmlEscape="false"/></span></a></li>
							<li><a id="eyeOfferTab" class="eyeTab" 
								href="#tabs-2"><span class="contenttext"><spring:message code="lts.acq.basketSelection.eyeOffer" htmlEscape="false"/></span></a></li>
							<li><a id="eyeOtherOfferTab" class="eyeTab" 
								href="#tabs-3"><span class="contenttext"><spring:message code="lts.acq.basketSelection.eyeOtherOffer" htmlEscape="false"/></span></a></li>
							<li><a id="fixLinePremiumOfferTab" class="delTab"
								href="#tabs-4"><span class="contenttext"><spring:message code="lts.acq.basketSelection.DELPremium" htmlEscape="false"/></span></a></li>
							<li><a id="fixLineRebateOfferTab" class="delTab"
								href="#tabs-5"><span class="contenttext"><spring:message code="lts.acq.basketSelection.DELRebate" htmlEscape="false"/></span></a></li>
							<li><a id="fixLineRebateCouponOfferTab" class="delTab"
								href="#tabs-6"><span class="contenttext"><spring:message code="lts.acq.basketSelection.DELRebateCoupon" htmlEscape="false"/></span></a></li>
							<li><a id="fixLineRebatePremiumOfferTab" class="delTab"
								href="#tabs-7"><span class="contenttext"><spring:message code="lts.acq.basketSelection.DELRebatePremium" htmlEscape="false"/></span></a></li>
							<li><a id="fixLineOtherOfferTab" class="delTab"
								href="#tabs-8"><span class="contenttext"><spring:message code="lts.acq.basketSelection.DELOther" htmlEscape="false"/></span></a></li>
							<li><a id="pcdBundleTab" 
								href="#tabs-9"><span class="contenttext"><spring:message code="lts.acq.basketSelection.PCDBundle" htmlEscape="false"/></span></a></li>
							<li><a id="delFreeBundleTab" 
								href="#tabs-10"><span class="contenttext"><spring:message code="lts.acq.basketSelection.DEL0Bundle" htmlEscape="false"/></span></a></li>
						</ul>
						<div id="filters" style="padding: 10px 0 0 10px; font-size: 12px;">
							<div id="pcdSbidDiv" style="float: right;">
								<spring:message code="lts.acq.basketSelection.PCDSBID" htmlEscape="false"/> <form:input maxlength="30" path="pcdSbid" cssClass="toUpper" autocomplete="off" />&nbsp;&nbsp;&nbsp;
								<c:if test="${not empty pcdSbidErrMsg}">
									<br/>
									<span class="error">${pcdSbidErrMsg}&nbsp;&nbsp;&nbsp;</span>
								</c:if>
							</div>
							<div id="staffPlanDiv">
								<span class="left"><spring:message code="lts.acq.basketSelection.staffPlan" htmlEscape="false"/></span>
								<form:checkbox path="staffPlan" id="staffPlan" />
								<div id="staffNumberDiv" style="display: none">
									<div id="missingStaffNumberError" style="display: none">
										<span class="left" style="color: #FF0000"> <spring:message code="lts.acq.basketSelection.missingStaffNumber" htmlEscape="false"/></span>
									</div>
									<span class="left"><spring:message code="lts.acq.basketSelection.staffNumber" htmlEscape="false"/></span>
									<form:textarea path="staffNumber" id="staffNumber" />
								</div>
							</div>							
							<div id="eyeDevice">
								<span class="left"><spring:message code="lts.acq.basketSelection.device" htmlEscape="false"/></span>
								<form:radiobutton path="filterDeviceType" value='' />
								<spring:message code="lts.acq.basketSelection.all" htmlEscape="false"/>
								<form:radiobutton path="filterDeviceType" value='-7' />
								7"
								<form:radiobutton path="filterDeviceType" value='-10' />
								10"
							</div>
							<div id="osType">
								<span class="left"><spring:message code="lts.acq.basketSelection.osType" htmlEscape="false"/></span>
								<form:checkbox path="osType" value="AND" />
								<spring:message code="lts.acq.basketSelection.android" htmlEscape="false"/>
								<form:checkbox path="osType" value="IOS" />
								<spring:message code="lts.acq.basketSelection.ios" htmlEscape="false"/>
							</div>
							<div id="bridgingMonth">
								<span class="left"><spring:message code="lts.acq.basketSelection.bridging" htmlEscape="false"/></span>
								<form:checkbox path="filterBridgingMonth" value="0" />
								<spring:message code="lts.acq.basketSelection.0M" htmlEscape="false"/>
								<form:checkbox path="filterBridgingMonth" value="1" />
								<spring:message code="lts.acq.basketSelection.1M" htmlEscape="false"/>
								<form:checkbox path="filterBridgingMonth" value="2" />
								<spring:message code="lts.acq.basketSelection.2M" htmlEscape="false"/>
								<form:checkbox path="filterBridgingMonth" value="3" />
								<spring:message code="lts.acq.basketSelection.3M" htmlEscape="false"/>
								<form:checkbox path="filterBridgingMonth" value="4" />
								<spring:message code="lts.acq.basketSelection.4M" htmlEscape="false"/>
								<form:checkbox path="filterBridgingMonth" value="5" />
								<spring:message code="lts.acq.basketSelection.5M" htmlEscape="false"/>
							</div>
							<div id="commitPeriod">
								<span class="left"><spring:message code="lts.acq.basketSelection.commitmentPeriod" htmlEscape="false"/></span>
								<form:checkbox path="filterCommitPeriodGt24mth" />
								<spring:message code="lts.acq.basketSelection.more24months" htmlEscape="false"/>
								<form:checkbox path="filterCommitPeriod18mth" />
								<spring:message code="lts.acq.basketSelection.13to23months" htmlEscape="false"/>
								<form:checkbox path="filterCommitPeriodLt12mth" />
								<spring:message code="lts.acq.basketSelection.less12months" htmlEscape="false"/>
							</div>
							<div id="specialOffer">
								<span class="left"><spring:message code="lts.acq.basketSelection.specialOffer" htmlEscape="false"/></span>
								<form:select path="filterProjectCd">
									<form:option value="">--</form:option>
									<c:if test="${not empty filterProjectCdList }">
										<form:options items="${ filterProjectCdList}"
											itemLabel="itemValue" itemValue="itemKey" />
									</c:if>
								</form:select>
							</div>
							<div id="parallelExtension">
								<span class="left"><spring:message code="lts.acq.basketSelection.PEbaskets" htmlEscape="false"/></span>

								<c:choose>
									<c:when test="${ acqPeCoverage == 'Y' }">
										<form:radiobutton id="peInd" path="peInd" value="Y" /> <spring:message code="lts.acq.basketSelection.Y" htmlEscape="false"/>
										<form:radiobutton id="peInd" path="peInd" value="N" /> <spring:message code="lts.acq.basketSelection.N" htmlEscape="false"/>
									</c:when>
									<c:otherwise>
										<form:radiobutton id="peInd" path="peInd" value="Y"
											disabled="true" /> <spring:message code="lts.acq.basketSelection.Y" htmlEscape="false"/>
										<form:radiobutton id="peInd" path="peInd" value="N"
											disabled="true" /> <spring:message code="lts.acq.basketSelection.N" htmlEscape="false"/>
									</c:otherwise>
								</c:choose>
							</div>
							<br />
							<div class="func_button">
								<a id="searchBtn" href="#"><spring:message code="lts.acq.basketSelection.search" htmlEscape="false"/></a>
							</div>
							<div class="func_button">
								<a id="clearBtn" href="#"><spring:message code="lts.acq.basketSelection.clear" htmlEscape="false"/></a>
							</div>
						</div>
						<div id="tabs-1">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.hotBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.hotBasketList}"
									var="hotBasket">
									<div class="imgcaption_lts round_10" title="${hotBasket.premierInd}">
										${hotBasket.html} 
										<br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket" href='${hotBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
						<div id="tabs-2">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.eyeBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.eyeBasketList}"
									var="eyeBasket">
									<div class="imgcaption_lts round_10" title="${eyeBasket.premierInd}">
										${eyeBasket.html} <br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket" href='${eyeBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
						<div id="tabs-3">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.eyeOtherBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.eyeOtherBasketList}"
									var="eyeOtherBasket">
									<div class="imgcaption_lts round_10" title="${eyeOtherBasket.premierInd}">
										${eyeOtherBasket.html} <br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket" href='${eyeOtherBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
						<div id="tabs-4">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.fixLinePremiumBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.fixLinePremiumBasketList}"
									var="fixLinePremiumBasket">
									<div class="imgcaption_lts round_10" title="${fixLinePremiumBasket.premierInd}">
										${fixLinePremiumBasket.html} <br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket"
												href='${fixLinePremiumBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
						<div id="tabs-5">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.fixLineRebateBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.fixLineRebateBasketList}"
									var="fixLineRebateBasket">
									<div class="imgcaption_lts round_10" title="${fixLineRebateBasket.premierInd}">
										${fixLineRebateBasket.html} <br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket"
												href='${fixLineRebateBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
						<div id="tabs-6">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.fixLineRebateCouponBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.fixLineRebateCouponBasketList}"
									var="fixLineRebateCouponBasket">
									<div class="imgcaption_lts round_10" title="${fixLineRebateCouponBasket.premierInd}">
										${fixLineRebateCouponBasket.html} <br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket"
												href='${fixLineRebateCouponBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
						<div id="tabs-7">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.fixLineRebatePremiumBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.fixLineRebatePremiumBasketList}"
									var="fixLineRebatePremBasket">
									<div class="imgcaption_lts round_10" title="${fixLineRebatePremBasket.premierInd}">
										${fixLineRebatePremBasket.html} <br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket"
												href='${fixLineRebatePremBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
						<div id="tabs-8">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.fixLineOtherBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.fixLineOtherBasketList}"
									var="fixLineOtherBasket">
									<div class="imgcaption_lts round_10" title="${fixLineOtherBasket.premierInd}">
										${fixLineOtherBasket.html} <br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket" href='${fixLineOtherBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
						<div id="tabs-9">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.pcdBundleBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.pcdBundleBasketList}"
									var="pcdBundleBasket">
									<div class="imgcaption_lts round_10" title="${pcdBundleBasket.premierInd}">
										${pcdBundleBasket.html} <br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket" href='${pcdBundleBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
						<div id="tabs-10">
							<c:if
								test="${not empty sessionScope.sessionBasketSelectionInfo.delfreeBundleBasketList}">
								<c:forEach
									items="${sessionScope.sessionBasketSelectionInfo.delfreeBundleBasketList}"
									var="delfreeBundleBasket">
									<div class="imgcaption_lts round_10" title="${delfreeBundleBasket.premierInd}">
										${delfreeBundleBasket.html} <br>
										<br>
										<div class="func_button"
											style="position: absolute; right: 10px; bottom: 10px;">
											<a class="selectBasket" href='${delfreeBundleBasket.basketId}'><spring:message code="lts.acq.basketSelection.buyNow" htmlEscape="false"/></a>
										</div>
									</div>
								</c:forEach>
								<div style="clear: both;"></div>
							</c:if>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</form:form>
</div>
<script type="text/javascript">
	$('#tabs').tabs({active: $("[name=currentTab]").val()});
	if($("#tabs [tabindex=0] a").is('#hotOfferTab')){
		showHotOfferTab();
	}else if($("#tabs [tabindex=0] a").is('#pcdBundleTab')){
		showPcdBundleTab();		
	}else if($("#tabs [tabindex=0] a").is('#delFreeBundleTab')){
		showDelFreeBundleTab();		
	}else if($("#tabs [tabindex=0] a").is('.eyeTab')){
		showEyeTab();
	}else if($("#tabs [tabindex=0] a").is('.delTab')){
		showDelTab();
	}
	
	if($('#staffPlan').attr('checked')){
		$('#staffNumberDiv').show();
		
		if($('#staffNumber').val() != ''){
			$('#staffNumber').attr("disabled",true);
		}
		else{
			$('#staffNumber').attr("disabled",false);
		}
	}
	else{
		$('#staffNumberDiv').hide();
	}	
	
	//hover states on the static widgets
	$('#dialog_link, ul#icons li').hover(
		function() { $(this).addClass('ui-state-hover'); }, 
		function() { $(this).removeClass('ui-state-hover'); }
	);
	
	function showHotOfferTab(){
		$('#bridgingMonth').show();
		$('#commitPeriod').show();
		$('#eyeDevice').show();
		$('#parallelExtension').show();
		$("#staffPlanDiv").show();
		$("#pcdSbidDiv").hide();
	}
	
	function showEyeTab(){
		$('#bridgingMonth').hide();
		$('#commitPeriod').hide();
		$('#eyeDevice').show();
		$('#parallelExtension').show();
		$("#staffPlanDiv").show();
		$("#pcdSbidDiv").hide();
	}
	
	function showDelTab(){
		$('#bridgingMonth').show();
		$('#commitPeriod').show();
		$('#eyeDevice').hide();
		$('#parallelExtension').hide();
		$("#staffPlanDiv").hide();
		$("#pcdSbidDiv").hide();
	}
	
	function showPcdBundleTab(){
		$('#bridgingMonth').show();
		$('#commitPeriod').show();
		$('#eyeDevice').show();
		$('#parallelExtension').show();
		$("#staffPlanDiv").show();
		$("#pcdSbidDiv").show();
	}
	
	function showDelFreeBundleTab(){
		$('#bridgingMonth').show();
		$('#commitPeriod').show();
		$('#eyeDevice').show();
		$('#parallelExtension').show();
		$("#staffPlanDiv").show();
		$("#pcdSbidDiv").show();
	}
	
	$('#staffPlan').click(function(){
		$('#staffNumber').val('');
		if($('#staffPlan').attr('checked')){
			$('#staffNumberDiv').show();
			$('#staffNumber').attr("disabled",false);
		}
		else{
			$('#staffNumberDiv').hide();
		}
	});
	
	$('#hotOfferTab').click(function(){
		showHotOfferTab();
	});

	$('#pcdBundleTab').click(function(){
		showPcdBundleTab();
	});
	
	$('#delFreeBundleTab').click(function(){
		showDelFreeBundleTab();
	});
	
	$('.eyeTab').click(function(){
		showEyeTab();
	});

	$('.delTab').click(function(){
		showDelTab();
	});
	
	$('a.selectBasket').click(function(event){
		var valid = true;
		if($('#searchingStaffPlan').val() == 'Y'){
			if($('#staffNumber').val() == ''){
				$('#missingStaffNumberError').show();
				valid = false;
			}
		}	
		
		if(valid){
			event.preventDefault();
			var basketId = $(this).attr('href');
			$('input[name="selectedBasketId"]').val(basketId);
			//$('input[name="formAction"]').val('SUBMIT');
			$('input[name="submitInd"]').val('SUBMIT');
			$('input[name="currentTab"]').val($("#tabs").tabs("option", "active") );			
			$('form[name="ltsBasketSelectionForm"]').submit();
		}
	});
	
	$("#searchBtn").click(function(event){
		$('#missingStaffNumberError').hide();
		var valid = true;
		if($('#staffPlan').attr('checked')){
			$('#searchingStaffPlan').val('Y');
			if($('#staffNumber').val() == ''){
				$('#missingStaffNumberError').show();
				valid = false;
			}
		}	
		else{
			$('#searchingStaffPlan').val('N');
		}
		
		if(valid){
			event.preventDefault();
			$('input[name="submitInd"]').val('SEARCH');
			$('input[name="currentTab"]').val($("#tabs").tabs("option", "active") );
			$('form[name="ltsBasketSelectionForm"]').submit();
		}
	});
	
	$("#clearBtn").click(function(event){
		event.preventDefault();
		$('#filters input').removeAttr("checked");
		$('input[name="submitInd"]').val('SEARCH');
		$('input[name="currentTab"]').val($("#tabs").tabs("option", "active") );
		$('form[name="ltsBasketSelectionForm"]').submit();
		
	});
	
	$('form').bind('submit', function() {
		$.blockUI({ message: null });
	});
	
    $(".toUpper").keyup(function(){
    	$(this).val($(this).val().toUpperCase());
    });
</script>
<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>