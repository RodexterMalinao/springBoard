<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>
<html>
<head>
<script language="javascript" src="js/jquery.colorbox-min.js"></script>
<script type="text/javascript" charset="utf-8" src="./js/shoppingcart.js"></script>
<script type="text/javascript">
function toggleDiv(divId) {
   //$("#"+divId).slideToggle("5000");
   $("#"+divId).fadeToggle("5000");
   //$("#"+divId).toggle("5000");
}

function formSubmit(basketId) {
	$('input[name="selectedBasketId"]').val(basketId);
	document.getElementById('basketSelectForm').submit();
	
}

$(document).ready(function() {
	$(function() {
		$("#nextview").attr('onclick', 'formSubmit()');
		$("#middle_content").css("min-height",
				viewportHeight - 450 + "px");
		$("#middle_content").css("visibility", "visible");
	});
});

$(document).bind('cbox_open', function(){
	$('body').css({overflow: 'hidden'});
}).bind('cbox_closed', function(){
	$('body').css({overflow: ''});
});
</script>
<script type="text/javascript">
			var detailFrame;
			
			$(document).ready(function(){
				var max_height = 0;
				for(var i = 0; i < $(".plancontent").length; i++){
				    var cur_height = $($(".plancontent")[i]).height();
				    if(cur_height > max_height){
				        max_height = cur_height;
				    }
				};
				for(var i = 0; i < $(".plancontent").length; i++){
				    $($(".plancontent")[i]).height(max_height+60);
				}			
				
				$("#main").css("height", Math.ceil($(".plan:visible").length / 3) * 480);
			});
			
			$(window).resize(function() {  
				repositionArrows();
				$.colorbox.resize({height:getBoxHeight()});
			});
			
			function repositionArrows(){
				if(detailFrame != null){
					$('.leftbtn').css('top',$('#cboxLoadedContent').offset().top-$(window).scrollTop()+$('#cboxLoadedContent').height()/2-$('.leftbtn').height()/2);
					$('.rightbtn').css('top',$('#cboxLoadedContent').offset().top-$(window).scrollTop()+$('#cboxLoadedContent').height()/2-$('.rightbtn').height()/2);
					if($(window).width() < 1100){
						$('.leftbtn').css('left',$(window).width() * 0.05); 
						$('.rightbtn').css('left',$(window).width() * 0.95 - 50);
					}else{
						$('.leftbtn').css('left',$(window).width()/2 - $('#cboxLoadedContent').width()/2 - 100);
						$('.rightbtn').css('left',$(window).width()/2 + $('#cboxLoadedContent').width()/2 + 50);
					}
				}
			}
			
			function hideArrow(arrow_class){
				$(arrow_class).fadeOut();
			}
			
			function showArrow(arrow_class){
				$(arrow_class).fadeIn();
			}
			
			function getBoxHeight(){
				var boxheight = 400;
				var windowheight = $(window).height();
				if(windowheight - 60 > boxheight){
					boxheight = windowheight - 60;
				}
				return boxheight;
			}
			
			function showBasketDetail(basketId){
				var boxheight = getBoxHeight();
				$.colorbox({width:"860px", height: boxheight+"px", href:"basketdetailslide.html?basketId="+basketId, iframe:true, fixed:true, opacity:"0.6", 
						onComplete: function(){
							//$('#cboxOverlay').css('cursor', 'default');
							detailFrame = $("#cboxLoadedContent").find("iframe")[0].contentWindow;
							
							$('#cboxOverlay').after('<img class="c-buttons leftbtn" src="./images/left-arrow.png"></img>');
							$('#cboxOverlay').after('<img class="c-buttons rightbtn" src="./images/right-arrow.png"></img>');
							//$('.leftbtn').click(function(){$("#cboxLoadedContent").find("iframe")[0].contentWindow.left();});
							//$('.rightbtn').click(function(){$("#cboxLoadedContent").find("iframe")[0].contentWindow.right();});
							//$('.leftbtn').click(function(){detailFrame.left();});
							//$('.rightbtn').click(function(){detailFrame.right();});
							$("#cboxPrevious").show().click(function(){detailFrame.left();});
							$("#cboxNext").show().click(function(){detailFrame.right();});
							//repositionArrows();
						},
						onClosed:function(){
							$('.c-buttons').remove();
							detailFrame = null;
						}
				});
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
<!--wrapper-->
<div id="wrapper">

<form:form id="basketSelectForm" name="basketSelectForm" method="POST" commandName="basketSelectForm" action="basketselect.html">
<!--content-->
<div id="content">
<!--flow nav-->
<div class="flow">
<a href="javascript:toggleCart();"><span class="cart_icon"></span></a>
<ul>
<li class="flow_active"><spring:message code="reg.title.plan.${srv}"/></li>
<li class="flowsep"></li>
<li class="flow_inactive"><spring:message code="reg.title.optional"/></li>
</ul>
</div>
<!-- end of flow nav-->
<!--main-->
<div id="main">
<div class="thx_note">
	<spring:message code="plan.remind.${srv}"/>
</div>


<form:hidden path="selectedBasketId" />

<!--plan-->
<c:forEach items="${basketSelectForm.onlineBasketList}" var="onlineBasket" >
<!--col1-->	
<div class="plan">
<div class="plantop">${ onlineBasket.basketDetail.baseBasketDesc }</div>
<div class="plancontent">
<div class="plantext">${ onlineBasket.basketDetail.html }
<br/>
<c:if test="${onlineBasket.basketDetail.contractPeriod != '0'}">
<b>(<span class="highlight">${onlineBasket.basketDetail.contractPeriod}</span><spring:message code="basket.header1" />)</b>
</c:if>
</div>
<div class="planprice">
	<c:if test="${not empty onlineBasket.basketDetail.recurrentAmt && onlineBasket.basketDetail.recurrentAmt != '0'}">
		<span class="price"> $${onlineBasket.basketDetail.recurrentAmt}</span> <spring:message code="plan.permonth"/>
	</c:if>
	<c:if test="${empty onlineBasket.basketDetail.recurrentAmt || onlineBasket.basketDetail.recurrentAmt == '0'}">
		<span class="price">$${onlineBasket.basketDetail.mthToMthAmt}</span> <spring:message code="plan.permonth"/>
	</c:if>
</div>

<c:if test="${not empty onlineBasket.imageItemList }">
	<!--<spring:message code="plan.title.include"/>-->
	<div class="iconrow2">
		<c:forEach items="${onlineBasket.imageItemList}" var="imageItem">
			<font class="tip">
				<c:if test="${not empty imageItem.imagePath}">
					<img class="channel" style="border:0" width="200" height="auto" src="getimage.html?id=${imageItem.imagePath}" />
				</c:if>
				<span style="width:100px;">${ imageItem.itemDisplayHtml }</span>
			</font>	
		</c:forEach>
	</div>
</c:if>

<c:if test="${not empty onlineBasket.contentImageSetList}">
	<c:forEach items="${onlineBasket.contentImageSetList}" var="contentImageSet" varStatus="itemSetStatus">
		${contentImageSet.displayHtml}
		<div class="iconrow2">	
		
		<c:if test="${not empty contentImageSet.itemDetails}">
			<c:forEach items="${contentImageSet.itemDetails}" var="itemDetail" varStatus="itemStatus">
				<font class="tip">
					<c:if test="${not empty itemDetail.imagePath}">
						<img <c:if test="${not empty itemDetail.imageDetailPath}"> onclick="showImageDetail(${itemDetail.imageDetailPath});"</c:if> class="channel" style="cursor: pointer; border:0" width="60" height="60" src="getimage.html?id=${itemDetail.imagePath}" />
					</c:if>
					<span style="width:100px;">${ itemDetail.itemDisplayHtml }</span>
				</font>
			</c:forEach>
		</c:if>
		
		</div>		
	</c:forEach>
</c:if>

<c:if test="${not empty onlineBasket.premiumDelItemSetList}">
	<br/>
	<c:forEach items="${onlineBasket.premiumDelItemSetList}" var="premiumDelSet" varStatus="itemSetStatus">
		${premiumDelSet.displayHtml}
		<div class="iconrow2">
		<c:if test="${not empty premiumDelSet.itemDetails}">
			<c:forEach items="${premiumDelSet.itemDetails}" var="itemDetail" varStatus="itemStatus">
					<c:if test="${not empty itemDetail.imagePath}">
						<img <c:if test="${not empty itemDetail.imageDetailPath}">style='cursor: pointer;' onclick="showImageDetail(${itemDetail.imageDetailPath});"</c:if> width="97" height="97" src="getimage.html?id=${itemDetail.imagePath}" />
					</c:if>
					<br/>
					<br/>
					<span style="width:100px; font-weight: bold; font-size: 1.2em; ">${ itemDetail.itemDisplayHtml }</span>
			</c:forEach>
		</c:if>
		</div>		
	</c:forEach>
</c:if>


<c:if test="${not empty onlineBasket.premiumImageSetList}" >
	<c:forEach items="${onlineBasket.premiumImageSetList}" var="premiumImageSet" varStatus="itemSetStatus">
		<br/>
		<div class="creditcard_promote">
			<c:if test="${not empty premiumImageSet.itemDetails}">
				<c:forEach items="${premiumImageSet.itemDetails}" var="itemDetail" varStatus="itemStatus">
					<div class="card">
						<c:if test="${not empty itemDetail.imagePath}">
							<img <c:if test="${not empty itemDetail.imageDetailPath}">style='cursor: pointer;' onclick="showImageDetail(${itemDetail.imageDetailPath});"</c:if> src="getimage.html?id=${itemDetail.imagePath}" width="100%" height="100%" />
						</c:if>
					</div>					
				</c:forEach>
			</c:if>
			<b>${premiumImageSet.displayHtml} </b>
			<br/>
		</div>
	</c:forEach>	
</c:if>


<div class="clearboth"></div>

</div>
<div class="bottom_btn">
<c:if test="${srv == 'EYE'}">
	<a class='detailcontent' onclick = "showBasketDetail(${onlineBasket.basketDetail.basketId})">
		<c:if test="${onlineBasket.selected}">
			<img src="./images/${lang}/details_selected_btn.png" 
				onmouseover="this.src='./images/${lang}/details_selected_btn_press.png'" 
				onmouseout="this.src='./images/${lang}/details_selected_btn.png'" />
		</c:if>
		<c:if test="${!onlineBasket.selected}">
			<img src="./images/${lang}/details_btn.png" 
				onmouseover="this.src='./images/${lang}/details_btn_press.png'" 
				onmouseout="this.src='./images/${lang}/details_btn.png'" />
		</c:if>
	</a>
</c:if>
<c:if test="${srv == 'DEL'}">
	<a onclick = "showBasketDetail(${onlineBasket.basketDetail.basketId})">
		<c:if test="${onlineBasket.selected}">
			<img src="./images/${lang}/buy_selected_btn.png" 
				onmouseover="this.src='./images/${lang}/buy_selected_btn_press.png'" 
				onmouseout="this.src='./images/${lang}/buy_selected_btn.png'" />
		</c:if>
		<c:if test="${!onlineBasket.selected}">
			<img src="./images/${lang}/buy_btn_2.png" 
				onmouseover="this.src='./images/${lang}/buy_btn_2_press.png'" 
				onmouseout="this.src='./images/${lang}/buy_btn_2.png'" />
		</c:if>
	</a>
</c:if>
</div>
</div>
<!--ed of col1-->


</c:forEach>

</div>
<!--end of main-->

</div>
<!--end of content-->
<div id="floating_bar" style="margin-left: 0px;">
				<div class="grad"></div>
					<div id="bottom_content"> 
						<table width="100%" style="position: relative;">  
						<tbody>
							<tr> 
								<td width="80%">&nbsp;</td>
								<td width="20%">
									<!--  <span class="promo"><spring:message code="plan.offervalid"/>${basketSelectForm.offerValidDate}</span> -->
								</td>
							</tr>
							<c:if test="${srv=='DEL'}">
							<tr>
								<td colspan=2>
									<div class="remind_gift"><spring:message code="plan.remind.gift"/></div>
								</td>
							</tr>
							</c:if>
						</tbody>
                        </table>
					</div>
					<img src="images/${lang}/bottom_bar.png" />
</div>
</form:form>
</div>
<!--end of wrapper-->

<!--cart area-->
<iframe id="cartarea" src="">
</iframe>
<!--end of cart area-->
</body>
</html>
