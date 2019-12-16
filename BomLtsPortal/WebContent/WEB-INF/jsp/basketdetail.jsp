<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>
<html>
<head>
<script language="javascript" src="js/jquery.colorbox-min.js"></script>
<script language="javascript" src="js/function.js"></script>
<script type="text/javascript" charset="utf-8" src="./js/shoppingcart.js"></script>
<script type="text/javascript">
function toggleDiv(divId) {
   $("#"+divId).fadeToggle("5000");
}

function formSubmit() {
	document.getElementById('basketDetailForm').submit();
	
}

$(document).ready(function() {
	checkNowTvCheckbox();
	checkChannel();
	
	$(function() {
		$("#nextview").attr('onclick', 'formSubmit()');
		$("#middle_content").css("min-height",
				viewportHeight - 450 + "px");
		$("#middle_content").css("visibility", "visible");
		$(".detailcontent").colorbox({innerWidth:"65%", innerHeight:330, iframe:true });
	});
	
	$(".nowTv_checkbox").click(function(){
		checkNowTvCheckbox();
	});
});

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
</script>

</head>

<body>
<!--wrapper-->
<div id="wrapper">
<!--top border-->
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

<form:form id="basketDetailForm" name="basketDetailForm" method="POST" commandName="basketDetailForm" action="basketdetail.html">
<!--main-->
<div id="main">





<!--hidden popup-->
<div id="reminder">
<div id="dbs_middle2">
  <div class="coupon">
    <div class="coupon1"></div>
    <div class="coupon2"></div>
  </div>
  <div class="couponText2"> Please remeber to use your DBS credit card
  <div class="couponTextB">DBS Credit Card - $300 Wellcome Coupon
<span class="small10px">(Eligible if payment by DBS bank credit card)</span> </div>
  
  </div><br />
    
    
  
  <div class="clearboth"></div>
</div>
</div>
<!-- end of hidden pop up-->

<c:if test="${not empty  basketDetailForm.contItemSetList}"> 

<c:forEach items="${basketDetailForm.contItemSetList}" var="contItemSet" varStatus="itemSetStatus" >

<div id="premium">
	<h3>${contItemSet.itemSetDesc}</h3>
		
		<c:if test="${ not empty contItemSet.itemDetails }">

			<table width="100%" cellpadding="10">
			
			<c:set var="counter" value="1" scope="page" />
			
			<c:forEach items="${ contItemSet.itemDetails}" var="itemDetail" varStatus="itemStatus">
				<c:if test="${counter == 1 }">
					<tr>
				</c:if>

				<td width="31%">
					<span class="packText">${itemDetail.itemDisplayHtml}</span>
					<c:choose>
						<c:when test="${itemDetail.itemType == 'NOWTV-PAY'||itemDetail.itemType == 'NOWTV-SPEC'}">
							<form:checkbox path="contItemSetList[${itemSetStatus.index}].itemDetails[${itemStatus.index }].selected" disabled="${itemDetail.mdoInd == 'M'}"  cssClass="nowTv_checkbox"/>
						</c:when>
						<c:otherwise>
							<form:checkbox path="contItemSetList[${itemSetStatus.index}].itemDetails[${itemStatus.index }].selected" disabled="${itemDetail.mdoInd == 'M'}"/>
						</c:otherwise>
					</c:choose>
					<c:if test="${not empty itemDetail.imagePath}">
						<img src="getimage.html?id=${itemDetail.imagePath}" style="vertical-align: middle;"/>
					</c:if>
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

<form:errors cssStyle="color: red; font-weight: bold;" path="contItemSetList[${itemSetStatus.index }].itemSetId"></form:errors>
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
										<span class="adultarea"><form:checkbox path="selectedAdultChannel"/>
						  				I wish to view AV On-Demand programs and adult channel content previews on <span class="now">now</span> TV<br />
										<br />
										<span class="greenText">Special Pack</span><br />
										Please select channel category and subscribe any special packs
										</span>
										<br />
										<br />
						                <div id="channelGroupIcon">
						                	<form:hidden id="selectedNowTvGroupId" path="selectedNowTvGroupId"/>
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
							  		<form:radiobutton id="${channelDetail.channelId}" path="selectedNowTvChannelId" 
							  			value="${channelDetail.channelId}" onclick="changecombo(this)" cssClass="nowTv_radiobutton"/>
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
<h3>${premiumItemSet.itemSetDesc}</h3>

<c:forEach items="${premiumItemSet.itemDetails }" var="itemDetail" varStatus="itemStatus">

<div class="col">
	<div class="top"></div>
	<div class="middle">
		
		<c:if test="${not empty itemDetail.imagePath }">
			<img src="getimage.html?id=${itemDetail.imagePath}" width="127" height="128" alt="${itemDetail.itemDisplayHtml}" />
		</c:if>
		<br />
  		<table cellspacing="0" cellpadding="0">
    		<tr>
      			<td>
      				<form:checkbox path="premiumItemSetList[${itemSetStatus.index}].itemDetails[${itemStatus.index }].selected" disabled="${itemDetail.mdoInd == 'M'}"/>
				</td>
      			<td align="left"> ${itemDetail.itemDisplayHtml}</td>
    		</tr>
  		</table>
	</div>
	<div class="btn">
	<div class="detail_amo">
		value: <span class="green24px">${itemDetail.penaltyAmtTxt}</span>
	</div>
	<a href="" class="detailcontent"><span class="detail_btn">Details</span></a>

	</div>
	<div class="bottom"></div>
</div>
</c:forEach>
<form:errors cssStyle="color: red; font-weight: bold;" path="premiumItemSetList[${itemSetStatus.index }].itemSetId"></form:errors>

</c:forEach>

<div class="clearboth" ></div>
</div>
</c:if>

<!--end of install-->


<!--end of main-->
<div class="clearboth"></div>
</div>

</form:form>

<!--end of content-->
<div id="floating_bar" style="margin-left: 0px;">
				<div class="grad"></div>
					<div id="bottom_content"> 
                    <table width="100%" style="bottom: -8pt;position: relative;">  
						<tbody>
							<tr align="center"> 
								<td width=25%></td>
								<td>
								<img id="back" src="./images/${lang}/previous_btn.png" 
									onmouseover="this.src='./images/${lang}/previous_btn_mo.png'" 
									onmouseout="this.src='./images/${lang}/previous_btn.png'" 
									style="margin-left:0pt;cursor: pointer;"
									onclick="history.go(-1);"/>
                                </td>
								<td>
								<img id="nextview" src="./images/${lang}/next_btn.png" 
									onmouseover="this.src='./images/${lang}/next_btn_mo.png'" 
									onmouseout="this.src='./images/${lang}/next_btn.png'" 
									style="margin-left:0pt;cursor: pointer;"/>
                                </td>
								<td width=25%></td>
							</tr>
						</tbody>
                    </table>
                    
                    
                    
                    
						
					</div>  
					<img src="images/${lang}/bottom_bar.png" />
</div>


</div>
<!--end of wrapper-->
</div>

<!--cart area-->
<iframe id="cartarea" src="">
</iframe>
<!--end of cart area-->

</body>
</html>
