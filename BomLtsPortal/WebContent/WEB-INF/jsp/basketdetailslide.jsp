<%
	response.setHeader("Cache-Control", "no-store,no-cache"); //HTTP 1.1
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
		<title>Online Sales</title>
		
		<c:set var="srv" value="${sessionOnlineOrderCapture.serviceTypeInd}"/>
		<c:set var="lang" value="${sessionOnlineOrderCapture.lang}"/>
		<c:if test="${srv=='EYE'}">
			<link rel="stylesheet" type="text/css" href="./css/style.css" /> 
			<link href="css/colorbox.css" rel="stylesheet" type="text/css" />
		</c:if>
		<c:if test="${srv=='DEL'}">
			<link rel="stylesheet" type="text/css" href="./css/style_del.css" /> 
			<link href="css/colorbox_del.css" rel="stylesheet" type="text/css" />
		</c:if>
		
		<style>
			.swipe {
			  overflow: hidden;
			  visibility: hidden;
			  position: relative;
			  width: 800px;
			}
			.swipe-wrap {
			  overflow: hidden;
			  position: relative;
			}
			.swipe-wrap > div {
			  float:left;
			  width:100%;
			  position: relative;
			}
		</style>
		
		<script src="js/jquery-1.8.2.js"></script>
		<script language="javascript" src="js/function.js"></script>
		<script language="javascript" src="js/swipe.js"></script>
		<script language="javascript" src="js/jquery.colorbox-min.js"></script>
		<script type="text/javascript">
			var defaultDisplay = 0;
			var currentDisplay = -1;
			var swipeReady = false;
			$(document).ready(function() {
				var targetBasketId = $("#selectedBasketId").val();
				var targetDisplayId = parseInt($("#"+targetBasketId).parents(".basket_form_border").attr("id"));
				window.mySwipe = Swipe(document.getElementById('slider'), {
					startSlide : targetDisplayId,
					speed : 700,
					continuous : false,
					transitionEnd: function() {
						swipeReady = true;
						$('html, body').animate({
							scrollTop: 0
						}, 500);
						$("#slider").animate({
							height: $($(".basket_form")[mySwipe.getPos()]).height(),
						}, 800);
					}
				});
				toggleParentArrow();
				swipeReady = true;
			//	init();
				checkNowTvCheckbox();
				checkChannel();
				
				$(".nowTv_checkbox").click(function(){
					checkNowTvCheckbox();
				});
				
				$("body").css("overflow-x", "hidden");
				
				$("#buy_btn").click(function(){
					formSubmit();
				});
				
				$(".adult_confirm_checkbox").click(function(){
					if($(".adult_confirm_checkbox").is(":checked")){
						$(".adult_confirm_checkbox").siblings(".adulterror").html("");
					}
				});
				
				if($(".error_msg")[0]){
					$('html, body').animate({
						scrollTop: $($(".error_msg")[0]).offset().top - $(window).height()/2
					}, 500);
				}
			});
			
			function formSubmit(){
				//selectedBasketId
				currentDisplay = mySwipe.getPos();
				if($(".adultchannel").is(":checked") && !$(".adult_confirm_checkbox").is(":checked")){
					$(".adulterror").html("<spring:message code='nowTv.adult.warn'/><br/>");
					$('html, body').animate({
						scrollTop: $(".adulterror").offset().top - $(window).height()/2
					}, 500);
					return;
				}
				$("#selectedBasketId").val($("#"+currentDisplay).find(".basket_detail").attr("id"));
				$("#basketDetailSlideForm").submit();
			}
			
			function init(){
				var targetBasketId = $("#selectedBasketId").val();
				var targetDisplayId = parseInt($("#"+targetBasketId).parents(".basket_form_border").attr("id"));
				mySwipe.slide(targetDisplayId);
				//showBasketDetail(targetDisplayId);
			}
			
			function showBasketDetail(input){
				if(currentDisplay != input){
					$(".basket_form_border").hide();
					$($(".basket_form_border")[input]).show();
					currentDisplay = input;
				}
				toggleParentArrow();
				return currentDisplay;
			}
			
			function right(){
				if(swipeReady){
					swipeReady = false;
					mySwipe.next();
					/*var targetDisplay = currentDisplay + 1;
					if(targetDisplay < $('.basket_form_border').length){
						$($('.basket_form_border')[currentDisplay]).animate({"top":"0px", "left":"-900px"}, "slow").fadeOut();
						$($('.basket_form_border')[targetDisplay]).show().offset({top: 0, left: 900}).animate({"top":"0px", "left":"0px"}, "slow");
						currentDisplay = targetDisplay;
					}*/
					toggleParentArrow();
					return mySwipe.getPos;
				}
			}

			function left(){
				if(swipeReady){
					swipeReady = false;
					mySwipe.prev();
					/*var targetDisplay = currentDisplay - 1;
					if(targetDisplay >= 0){
						$($('.basket_form_border')[currentDisplay]).animate({"top":"0px", "left":"900px"}, "slow").fadeOut();
						$($('.basket_form_border')[targetDisplay]).show().offset({top: 0, left: -900}).animate({"top":"0px", "left":"0px"}, "slow");
						currentDisplay = targetDisplay;
					}*/
					toggleParentArrow();
					return mySwipe.getPos;
				}
			}
			
			function scrollToTop(){
				$('html, body').animate({
					scrollTop: 0
				}, 500);
			}
			
			function toggleParentArrow(){
				if ( parent != this ){
					if(mySwipe.getPos() > 0){
						parent.showArrow("#cboxPrevious");
					}
					if(mySwipe.getPos() < mySwipe.getNumSlides()-1){
						parent.showArrow("#cboxNext");
					}
					if(mySwipe.getPos() == 0){
						parent.hideArrow("#cboxPrevious");
					}
					if(mySwipe.getPos() == mySwipe.getNumSlides()-1){
						parent.hideArrow("#cboxNext");
					}
				}
			}
			
			function checkChannel(){
				showchannel($("#selectedNowTvGroupId").val());
			}
	
			function showchannel(id){
				$("div[id^='combo']").hide();
				$("#combo_"+id).fadeIn('quick', function () {});
				$("#selectedNowTvGroupId").val(id);
			}
	
			function checkNowTvCheckbox(){
				if($(".nowTv_checkbox").is(":checked")){
					$(".nowTv_detail").show();
				}else{
					$(".nowTv_detail").hide();
					$("div[id^='combo']").hide();
					$("#selectedNowTvGroupId").val("");
					$(".nowTv_radiobutton").removeAttr("checked");
				}
				
			}
			
			function changecombo_adult(input){
				changecombo(input);
				var adultcheckbox = $(input).parents(".vas_detail").find(".adult_confirm_checkbox");
				if(!$(adultcheckbox).is(":checked")){
					$(adultcheckbox).siblings(".adulterror").html("<spring:message code='nowTv.adult.warn'/><br/>");
					$('html, body').animate({
						scrollTop: $(".adulterror").offset().top - $(window).height()/2
					}, 500);
				}
			}

			function changecombo_normal(input){
				$(".adulterror").html("");
				changecombo(input);
			}
		
		</script>
		<script type="text/javascript">
			var detailFrame;
			
			$(window).resize(function() {  
				$.colorbox.resize({height:getBoxHeight()});
			});
			
			function getBoxHeight(){
				var boxheight = 400;
				var windowheight = $(window).height();
				if(windowheight - 60 > boxheight){
					boxheight = windowheight - 60;
				}
				return boxheight;
			}
			
			function showImageDetail(imagePath){
				var boxheight = getBoxHeight();
				
				var imageDetailContent = 
					"<div style='display: table; height: 100%; width:100%;'> " +
						"<div style='display: table-cell; text-align: center; vertical-align: middle;margin:0 auto;'> " +
							"<img src='getimage.html?id="+imagePath+"'></img>" +
						"</div>" +
					"</div>";

				$.colorbox({width:"800px", height: boxheight+"px", html:imageDetailContent, iframe:false, fixed:true, opacity:"0.6", 

						onClosed:function(){
							$('.c-buttons').remove();
							detailFrame = null;
						}
				});
			}
			
</script>
	</head>
	<body>
		<form:form id="basketDetailSlideForm" name="basketDetailSlideForm" method="POST" commandName="basketDetailSlideForm" action="basketdetailslide.html">
			<form:hidden path="selectedBasketId" />
			<div id='slider' class='swipe'>
			<div class='swipe-wrap'>
			<c:forEach items="${basketDetailSlideForm.basketFormList}" var="basketDetailForm" varStatus="basketDetailStatus" >
				<div id="${basketDetailStatus.index}" class="basket_form_border">
				<div class="basket_form">
					<c:forEach items="${basketDetailForm.planItemList}" var="planItem" varStatus="planItemStatus">
						<div id="${basketDetailForm.basketDetail.basketId}" class="basket_detail">
							<span class="header">
								${basketDetailForm.basketDetail.html} 
								<c:if test="(${basketDetailForm.basketDetail.contractPeriod > 0}">
								<br/> 
								(${basketDetailForm.basketDetail.contractPeriod}<spring:message code="basket.commitment" />) $${basketDetailForm.basketDetail.recurrentAmt}
								</c:if>
							</span>
							<hr/>
							<table width="100%">
								<th>
								</th>
								<th>
									<spring:message code="basket.commitMthlyRate"/>
								</th>
								<th>
									<spring:message code="basket.mthToMthRate"/>
								</th>
								<tr>
									<c:if test="${not empty basketDetailForm.installFeeItemList}">
										<c:forEach items="${basketDetailForm.installFeeItemList}" var="installFeeItem" varStatus="installFeeItemStatus">
											<td width="55%"><span class="item_title_rp">${installFeeItem.itemDisplayHtml}</span></td>
											<td width="20%" class="col2">
												${installFeeItem.recurrentAmtTxt}
											</td>
											<td width="20%" class="col2">
												${installFeeItem.mthToMthAmtTxt}
											</td>
											<%-- <td width="40%" colspan="2" class="col2"><span>${installFeeItem.oneTimeAmtTxt}</span></td> --%>
										</c:forEach>
									</c:if>
									<c:if test="${empty basketDetailForm.installFeeItemList}">
										<td width="55%"><span class="item_title_rp"><spring:message code="basket.installfee"/></span></td>
										<td width="20%" class="col2">
											<spring:message code="basket.installfee.na"/>
										</td>
										<td width="20%" class="col2">
											<spring:message code="basket.installfee.na"/>
										</td>
										<%-- <td width="40%" colspan="2" class="col2"><span><spring:message code="basket.installfee.na"/></span></td> --%>
										<!-- <td width="20%" class="col3"></td> -->
									</c:if>
								</tr>
								<tr>
									<td width="55%"><p>${planItem.itemDisplayHtml}</p></td>
									<td width="20%" class="col2">
										<p>${planItem.recurrentAmtTxt}</p>
									</td>
									<td width="20%" class="col2">
										<p>${planItem.mthToMthAmtTxt}</p>
									</td>
									<%-- <td width="30%" class="col2">
										<p>
											<c:if test="${planItem.recurrentAmt > 0}">${planItem.recurrentAmtTxt}</c:if>
											<c:if test="${planItem.recurrentAmt == 0}">${planItem.mthToMthAmtTxt}</c:if>
										</p>
									</td> --%>
								</tr>
							</table>
						</div>
					</c:forEach>
					<c:if test="${not empty  basketDetailForm.contItemSetList}"> 
						<br/>
						<c:forEach items="${basketDetailForm.contItemSetList}" var="contItemSet" varStatus="itemSetStatus" >
							<div id="premium">
								<span class="header">${contItemSet.displayHtml}</span>
								<hr/>
								<c:if test="${ not empty contItemSet.itemDetails }">
									<table width="100%" cellpadding="10">
										<c:set var="counter" value="1" scope="page" />
										<c:forEach items="${ contItemSet.itemDetails}" var="itemDetail" varStatus="itemStatus">
											<c:if test="${counter == 1 }">
												<tr>
											</c:if>
							
											<td width="25%">
												<c:choose>
													<c:when test="${itemDetail.itemType == 'NOWTV-PAY'||itemDetail.itemType == 'NOWTV-SPEC'}">
														<form:checkbox path="basketFormList[${basketDetailStatus.index}].contItemSetList[${itemSetStatus.index}].itemDetails[${itemStatus.index }].selected" disabled="${itemDetail.mdoInd == 'M'}"  cssClass="nowTv_checkbox" cssStyle="float:left"/>
													</c:when>
													<c:otherwise>
														<form:checkbox path="basketFormList[${basketDetailStatus.index}].contItemSetList[${itemSetStatus.index}].itemDetails[${itemStatus.index }].selected" disabled="${itemDetail.mdoInd == 'M'}" cssStyle="float:left"/>
													</c:otherwise>
												</c:choose>
												<c:if test="${not empty itemDetail.imagePath}">
													<img <c:if test="${not empty itemDetail.imageDetailPath}"> onclick="showImageDetail(${itemDetail.imageDetailPath});"</c:if> src="getimage.html?id=${itemDetail.imagePath}" class="premium_img"/>
												</c:if>
												<span class="packText">${itemDetail.itemDisplayHtml}</span>
											</td>
									 		
									 		<c:choose>
										 		<c:when test="${counter == 3 }">
													</tr>
													<c:set var="counter" value="1" scope="page"/>
												</c:when>
												<c:otherwise>
													<c:set var="counter" value="${counter + 1}" scope="page"/>
													<c:if test="${fn:length(contItemSet.itemDetails) == itemStatus.index + 1}">
														</tr>
													</c:if>	
												</c:otherwise>
									 		</c:choose>
										</c:forEach>
									</table>
								</c:if>
								<form:errors cssClass="error_msg" path="basketFormList[${basketDetailStatus.index}].contItemSetList[${itemSetStatus.index }].itemSetId"></form:errors>
							</div>
						</c:forEach>
					</c:if>
				<!--special-->
  				<!-- NOW TV SPEC ROWS -->
				<c:if test="${not empty basketDetailForm.channelGroupList}">
				<div class="vas_row" style="padding:10px 40px">
					<div class="vas_detail nowTv_detail" style="display: none;">
						<div class="clearboth"></div>
        				<table width="100%">
	        				<tbody>
	           					<tr>
		           					<td width="90%">
										<span class="adultarea"><form:checkbox cssClass="adult_confirm_checkbox" path="basketFormList[${basketDetailStatus.index}].selectedAdultChannel"/>
						  				<spring:message code='nowTv.adult'/>
										<br />
						  				<span class="adulterror" style="color: #FF0000"></span>
										<br />
										<span class="greenText"><spring:message code='nowTv.channelgrp.header'/></span><br />
										<spring:message code='nowTv.channelgrp'/>
										</span>
										<br />
										<br />
						                <div id="channelGroupIcon">
						                	<form:hidden id="selectedNowTvGroupId" path="basketFormList[${basketDetailStatus.index}].selectedNowTvGroupId"/>
						                	<c:forEach items="${basketDetailForm.channelGroupList}" var="channelGroup" varStatus="status">
						                		<div class="genre">
							                		<img class="channel channel_grp" id="${channelGroup.channelGroupId}" onclick="showchannel(this.id)" src="getimage.html?id=${channelGroup.imagePath}"  /><br />
							                		${channelGroup.channelGroupHtml}
						                		</div>
						                	</c:forEach>
						                </div>
										<div class="clearboth"></div>
									</td>
	           					</tr>
	           				</tbody>
        				</table>
        				
        				
        				<c:forEach items="${basketDetailForm.channelGroupList}" var="channelGroup" varStatus="groupStatus">
	        				<!--plan${channelGroup.channelGroupId}-->  
	        				<div id="combo_${channelGroup.channelGroupId}" class="combo" style="display:none;">
	        					<c:forEach items="${channelGroup.channelDetails}" var="channelDetail" varStatus="channelStatus">
	        					<hr />
						        <div class="program_row">
							  		<div class="col1">&nbsp;</div>
							  		<div class="col2_noprice">&nbsp;</div>
							 		<div class="col3"></div>
							  		<div class="col4"></div>
	        					</div>
						        <div class="program_row">
							  		<div class="col1">
							  		<c:if test="${channelDetail.isAdultChannel == 'Y'}">
							  			<form:radiobutton id="${channelDetail.channelId}" path="basketFormList[${basketDetailStatus.index}].selectedNowTvChannelId" 
							  				value="${channelDetail.channelId}" onclick="changecombo_adult(this)" cssClass="nowTv_radiobutton adultchannel"/>
							  		</c:if>
							  		<c:if test="${channelDetail.isAdultChannel != 'Y'}">
								  		<form:radiobutton id="${channelDetail.channelId}" path="basketFormList[${basketDetailStatus.index}].selectedNowTvChannelId" 
							  				value="${channelDetail.channelId}" onclick="changecombo_normal(this)" cssClass="nowTv_radiobutton"/>
							  		</c:if>
							  		</div>
							  		<div class="col2_noprice">${channelDetail.channelHtml}</div>
							 		<div class="col3">${channelDetail.itemDetail.recurrentAmtTxt}</div>
							  		<div class="col4">${channelDetail.itemDetail.mthToMthAmtTxt}</div>
						  		</div>
						  		<div class="program_row">
							  		<div class="col1">&nbsp;</div>
							  		<div class="col2_noprice">
							  			<c:if test="${not empty channelDetail.channelIcons}">
								  			<c:forEach items="${channelDetail.channelIcons}" var="channelIcon">
								  				<font class="tip">
									  				<img class="channel" src="getimage.html?id=${channelIcon.imagePath}" />
									  				<span>${channelIcon.imageHtml}</span>
								  				</font> 
								  			</c:forEach>
							  			</c:if>
							 		<div class="col3"></div>
							  		<div class="col4"></div>
							        </div>
							 	</div>
						        <div class="clearboth"></div>
        						</c:forEach>
	        					
	        					
	        				</div>
        				</c:forEach>
        				
						<div class="clearboth"></div>
					</div>
				</div>
				</c:if>
				<!-- NOW TV SPEC ROWS -->
  
				<!--end of special-->
				
				<!--install-->
				<c:if test="${not empty basketDetailForm.premiumItemSetList}"> 
				<div id="install_pre">
				
				<c:forEach items="${basketDetailForm.premiumItemSetList}" var="premiumItemSet" varStatus="itemSetStatus">
				<br/>
				<br/>
				<span class="header">${premiumItemSet.displayHtml}</span>
				<hr/>
				<p><spring:message code="basket.gift"/></p>
				<c:forEach items="${premiumItemSet.itemDetails }" var="itemDetail" varStatus="itemStatus">
				<table class="itemlisttable">
					<tbody>
						<tr style="vertical-align:middle;">
							<td width="5%">
								<form:radiobutton path="basketFormList[${basketDetailStatus.index}].premiumItemSetList[${itemSetStatus.index}].selectedItemId" value="${itemDetail.itemId}"/>
							</td>
							<td width="15%" style="vertical-align:middle ; height: 105px;">
								<c:if test="${not empty itemDetail.imagePath }">
									<img <c:if test="${not empty itemDetail.imageDetailPath}"> onclick="showImageDetail(${itemDetail.imageDetailPath});"</c:if> src="getimage.html?id=${itemDetail.imagePath}" style="border: 2px solid #DFDFDF; cursor: pointer; width: 100px;" alt="${itemDetail.itemDisplayHtml}" />
								</c:if>
							</td>
							<td width="80%">
								<span>${itemDetail.itemDisplayHtml} (<spring:message code="basket.price"/>${itemDetail.penaltyAmtTxt})</span>
							</td>
						</tr>
					</tbody>
				</table>
				
				
				</c:forEach>
				<form:errors cssClass="error_msg" path="basketFormList[${basketDetailStatus.index}].premiumItemSetList[${itemSetStatus.index}].itemSetId"></form:errors>
				
				</c:forEach>
				
				<div class="clearboth" ></div>
				</div>
				<div class="remind_gift"><spring:message code="plan.remind.gift"/></div>
				</c:if>
				<div style="height:50px; width:100%; float: left;"></div>
				</div>
				</div>
			</c:forEach>
			</div>
			</div>
			<div class="buy_btn_div">
				<img id="buy_btn" src="./images/${lang}/buy_btn.png" 
						    	onmouseover="this.src='./images/${lang}/buy_btn_mo.png'" 
						    	onmouseout="this.src='./images/${lang}/buy_btn.png'" />
			</div>
		</form:form>
	
	</body>
</html>
