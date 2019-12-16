<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>
<html>
<head>
<script language="javascript" src="js/function.js"></script>
<script type="text/javascript" charset="utf-8" src="./js/shoppingcart.js"></script>
<script type="text/javascript">
function toggleDiv(divId) {
   $("#"+divId).fadeToggle("5000");
}

function formSubmit() {
	/*validation*/
	if($(".adultchannel").is(":checked") && !$(".adult_confirm_checkbox").is(":checked")){
		$(".adulterror").html("<spring:message code='nowTv.adult.warn'/><br/>");
		$('html, body').animate({
			scrollTop: $(".adulterror").offset().top - $(window).height()/2
		}, 500);
		return;
	}
	
	/*submit*/
	document.getElementById('vasDetailForm').submit();
	//$("#vasDetailForm").submit();
	//document.vasDetailForm.submit();
}

function clickchildcheck(input){
	if ($(input).attr('checked')) {
		$(input).parents(".vas_title").find(":checkbox").attr('checked', true);
	}
}

function vasonclick(input){
	if ($(input).attr('checked') && !$(input).parents(".vas_row").children(".vas_detail").is(":visible")) {
		$(input).parents(".vas_row").children(".vas_detail").slideDown('quick', function () {});
		$(input).parents(".vas_row").find(".showdtlbtn").hide();
	}else if (!$(input).attr('checked') && $(input).parents(".vas_row").children(".vas_detail").is(":visible")) {
		$(input).parents(".vas_row").children(".vas_detail").slideUp('quick', function () {});
		$(input).parents(".vas_row").find(".showdtlbtn").fadeIn('quick');
	}
}

function togglegrp(input) {
	if (!$(input).parents(".vas_row").children(".vas_detail").is(":visible")) {
		showgrp(input);
	} else {
		hidegrp(input);
	}
}

function showgrp(input){
	$(input).parents(".vas_row").children(".vas_detail").slideDown('quick', function () {});
	$(input).parents(".vas_row").find(".showdtlbtn").hide();
}

function hidegrp(input){
	$(input).parents(".vas_row").children(".vas_detail").slideUp('quick', function () {});
	$(input).parents(".vas_row").find(".showdtlbtn").fadeIn('quick');
}

function collapsedetail(input){
	if(input == 'C'){
		hidegrp(".vas_detail");
	}else if(input == 'E'){
		showgrp(".vas_detail");
	}
}

$(document).ready(function() {
	
	$(function() {
		$("#nextview").attr('onclick', 'formSubmit()');
		$("#middle_content").css("min-height",
				viewportHeight - 450 + "px");
		$("#middle_content").css("visibility", "visible");
	});
	
	//vas group checkboxes setting
	$(".vas_row").each(function(){
		var row = $(this);
		if(!row.is(".nowTv")){ //exclude nowTv
			row.children(".vas_detail").find(":checkbox").click(function(){
				var total = row.children(".vas_detail").find(":checkbox").length;
				var notchecked = row.children(".vas_detail").find(":checkbox").not(':checked').length;
				if(notchecked == total){
					row.children(".vas_title").children(":checkbox").attr("checked", false);
				}else{
					row.children(".vas_title").children(":checkbox").attr("checked", true);
				}
			});
		}else{
			if(row.children(".vas_detail").find(":radio").is(":checked")){
				row.children(".vas_title").children(":checkbox").attr("checked", true);
			}
			row.children(".vas_detail").find(":radio").click(function(){
				row.children(".vas_title").children(":checkbox").attr("checked", true);
			});
			checkChannel();
		}

		row.children(".vas_title").children(":checkbox").each(function(){
			if($(this).is(":checked")){
				row.children(".vas_detail").show();
				row.find(".showdtlbtn").hide();
			}else{
				row.children(".vas_detail").find(":checkbox").removeAttr("checked");
				row.children(".vas_detail").find(":radio").removeAttr("checked");
			}
			$(this).click(function(){
				if($(this).is(":checked")){
					row.children(".vas_detail").show();
				}else{
					row.children(".vas_detail").find(":checkbox").removeAttr("checked");
					row.children(".vas_detail").find(":radio").removeAttr("checked");
				}
			});
		});
	});
	
	$(".adult_confirm_checkbox").click(function(){
		if($(".adult_confirm_checkbox").is(":checked")){
			$(".adult_confirm_checkbox").siblings(".adulterror").html("");
		}
	});
	
	
});

function showchannel(type, id){
	if(id != null && id != ""){
		$("div[id^='combo"+"_"+type+"']").hide();
		$("#combo"+"_"+type+"_"+id).fadeIn('quick', function () {});
		$("#selectedNowTvGroupId_"+type).val(id);
	}
}

function checkChannel(){
	showchannel("spec", $("#selectedNowTvGroupId_spec").val());
	showchannel("pay", $("#selectedNowTvGroupId_pay").val());
}

function checkNowTvCheckbox(){
	if($("#nowTv_checkbox_spec").is(":checked")){
		$(".nowTv_detail_spec").show();
	}else{
		$(".nowTv_detail_spec").hide();
	}

	if($("#nowTv_checkbox_pay").is(":checked")){
		$(".nowTv_detail_pay").show();
	}else{
		$(".nowTv_detail_pay").hide();
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
</head>

<body>
<!--wrapper-->
<div id="wrapper">
<!--content-->
<div id="content">
<!--flow nav-->
<div class="flow">
<a href="javascript:toggleCart();"><span class="cart_icon"></span></a>
<ul>
<li class="flow_inactive"><spring:message code="reg.title.plan.${srv}"/></li>
<li class="flowsep"></li>
<li class="flow_active"><spring:message code="reg.title.optional"/></li>
</ul>
</div>
<!-- end of flow nav-->
<!--vas main-->
<div id="frame_main">
<form:form id="vasDetailForm" name="vasDetailForm" method="POST" commandName="vasDetailForm" action="vasdetail.html">

<!--vas-->
<div id="middle_content" style="visibility:hidden;">
				<div id="collapse_panel">
					<spring:message code="vas.remind" />
					<br />
					<div class="styled-select">
						<select id="select" onchange="collapsedetail(this.options[this.selectedIndex].value)">
							<option selected="selected" disabled="disabled"><spring:message code="vas.hideshow" /></option>
							<option value="C"><spring:message code="vas.hide" /></option>
							<option value="E"><spring:message code="vas.show" /></option>
						</select>
					</div>
					<div><font color="red">
					<form:errors path="errorMsg"></form:errors>
					<c:if test='${not empty requestScope.errorMsgList}'> 
						${requestScope.errorMsgList}
					</c:if>
					</font></div>
				<hr />
				</div>
				
								
				<!-- NOW TV PAY ROWS -->
				<c:if test="${not empty vasDetailForm.nowTvPayItems && not empty vasDetailForm.payChannelGroupList}">
				<div class="vas_row nowTv">
						<div class="vas_title">
							<form:checkbox id="nowTv_checkbox_pay" path="selectedNowTv" onclick="vasonclick(this)" />
							<spring:message code="vas.item.now" />
							<div class="showdtlbtn" style="float:right;"><a onclick="togglegrp(this);"><img src="./images/${lang}/details_btn_2.png"/></a></div>
						</div>
					<div class="vas_detail nowTv_detail_pay" style="display: none;">
						<div class="clearboth"></div>
        				<table width="100%">
	        				<tbody>
	           					<tr>
		           					<td width="90%">
										<span class="adultarea"><form:checkbox cssClass="adult_confirm_checkbox" path="selectedAdultChannel"/>
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
						                	<form:hidden id="selectedNowTvGroupId_pay" path="selectedNowTvPayGroupId"/>
						                	<c:forEach items="${vasDetailForm.payChannelGroupList}" var="channelGroup" varStatus="status">
						                		<div class="genre">
							                		<img class="channel channel_grp" id="${channelGroup.channelGroupId}" onclick="showchannel('pay', this.id)" src="getimage.html?id=${channelGroup.imagePath}"  /><br />
							                		${channelGroup.channelGroupHtml}
						                		</div>
						                	</c:forEach>
						                </div>
										<div class="clearboth"></div>
									</td>
	           					</tr>
	           				</tbody>
        				</table>
        				
        				
        				<c:forEach items="${vasDetailForm.payChannelGroupList}" var="channelGroup" varStatus="groupStatus">
	        				<!--plan${channelGroup.channelGroupId}-->  
	        				<div id="combo_pay_${channelGroup.channelGroupId}" class="combo" style="display:none;">
	        					<c:forEach items="${channelGroup.channelDetails}" var="channelDetail" varStatus="channelStatus">
	        					<hr />
						        <!-- <div class="program_row">
							  		<div class="col1">&nbsp;</div>
							  		<div class="col2">&nbsp;</div>
							 		<div class="col3"><spring:message code='nowTv.header.term.price'/></div>
	        					</div> -->
						        <div class="program_row">
							  		<div class="col1">
							  		<c:if test="${channelDetail.isAdultChannel == 'Y'}">
								  		<form:radiobutton id="${channelDetail.channelId}" path="selectedNowTvPayChannelId" 
								  			value="${channelDetail.channelId}" cssClass="adultchannel" onclick="changecombo_adult(this)"/>
							  		</c:if>
							  		<c:if test="${channelDetail.isAdultChannel != 'Y'}">
								  		<form:radiobutton id="${channelDetail.channelId}" path="selectedNowTvPayChannelId" 
								  			value="${channelDetail.channelId}" onclick="changecombo_normal(this)"/>
							  		</c:if>
							  		</div>
							  		<div class="col2">${channelDetail.channelHtml}</div>
							  		<div class="col3">${channelDetail.itemDetail.displayAmtTxt}</div>
						  		</div>
						  		<div class="program_row">
							  		<div class="col1">&nbsp;</div>
							  		<div class="col2">
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
        				<div class="hidedtlbtn" style="float:right;"><a onclick="togglegrp(this);"><img style="width:auto" src="./images/${lang}/hide_btn.png"/></a></div>
						<div class="clearboth"></div>
					</div>
					<hr />
				</div>
				</c:if>
				<!-- NOW TV PAY ROWS -->
				
				<!-- NOW TV SPEC ROWS -->
				<c:if test="${not empty vasDetailForm.nowTvSpecItems && not empty vasDetailForm.specChannelGroupList}">
				<div class="vas_row nowTv">
					<div class="vas_title">
							<form:checkbox id="nowTv_checkbox_spec" path="selectedNowTv" onclick="vasonclick(this)" />
							<spring:message code="vas.item.now" />
							<div class="showdtlbtn" style="float:right;"><a onclick="togglegrp(this);"><img src="./images/${lang}/details_btn_2.png"/></a></div>
						</div>
					<div class="vas_detail nowTv_detail_spec" style="display: none;">
						<div class="clearboth"></div>
        				<table width="100%">
	        				<tbody>
	           					<tr>
		           					<td width="90%">
										<span class="adultarea"><form:checkbox cssClass="adult_confirm_checkbox" path="selectedAdultChannel"/>
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
						                	<form:hidden id="selectedNowTvGroupId_spec" path="selectedNowTvSpecGroupId"/>
						                	<c:forEach items="${vasDetailForm.specChannelGroupList}" var="channelGroup" varStatus="status">
						                		<div class="genre">
							                		<img class="channel channel_grp" id="${channelGroup.channelGroupId}" onclick="showchannel('spec', this.id)" src="getimage.html?id=${channelGroup.imagePath}"  /><br />
							                		${channelGroup.channelGroupHtml}
						                		</div>
						                	</c:forEach>
						                </div>
										<div class="clearboth"></div>
									</td>
	           					</tr>
	           				</tbody>
        				</table>
        				
        				
        				<c:forEach items="${vasDetailForm.specChannelGroupList}" var="channelGroup" varStatus="groupStatus">
	        				<!--plan${channelGroup.channelGroupId}-->  
	        				<div id="combo_spec_${channelGroup.channelGroupId}" class="combo" style="display:none;">
	        					<c:forEach items="${channelGroup.channelDetails}" var="channelDetail" varStatus="channelStatus">
	        					<hr />
						        <!-- <div class="program_row">
							  		<div class="col1">&nbsp;</div>
							  		<div class="col2">&nbsp;</div>
							 		<div class="col3"><spring:message code='nowTv.header.term.price'/></div>
	        					</div> -->
						        <div class="program_row">
							  		<div class="col1">
							  		<c:if test="${channelDetail.isAdultChannel == 'Y'}">
								  		<form:radiobutton id="${channelDetail.channelId}" path="selectedNowTvSpecChannelId" 
								  			value="${channelDetail.channelId}" cssClass="adultchannel" onclick="changecombo_adult(this)"/>
							  		</c:if>
							  		<c:if test="${channelDetail.isAdultChannel != 'Y'}">
								  		<form:radiobutton id="${channelDetail.channelId}" path="selectedNowTvSpecChannelId" 
								  			value="${channelDetail.channelId}" onclick="changecombo_normal(this)"/>
							  		</c:if>
							  		</div>
							  		<div class="col2">${channelDetail.channelHtml}</div>
							  		<div class="col3">${channelDetail.itemDetail.displayAmtTxt}</div>
						  		</div>
						  		<div class="program_row">
							  		<div class="col1">&nbsp;</div>
							  		<div class="col2">
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
        				<div class="hidedtlbtn" style="float:right;"><a onclick="togglegrp(this);"><img style="width:auto" src="./images/${lang}/hide_btn.png"/></a></div>
						<div class="clearboth"></div>
					</div>
					<hr />
				</div>
				</c:if>
				<!-- NOW TV SPEC ROWS -->
				
				
				<!-- MOOV ROWS -->
				<c:if test="${not empty vasDetailForm.moovItems}">
				<div class="vas_row">
					<div class="vas_title">
					<form:checkbox path="selectedMoov" id="moov" onclick="vasonclick(this)" />
						<spring:message code="vas.item.moov" />
						<div class="showdtlbtn" style="float:right;"><a onclick="togglegrp(this);"><img src="./images/${lang}/details_btn_2.png"/></a></div>
					</div>
					<div class="vas_detail" style="display: none;">
						<!-- <div class="program_row">
	  						<div class="col1">&nbsp;</div>
	  						<div class="col2_green"><spring:message code="vas.header.des" /></div>
	 						<div class="col3"><spring:message code="vas.header.term.price" /></div>
	  					</div> -->
						<c:forEach items="${vasDetailForm.moovItems}" var="vasItem" varStatus="status">
							<div class="program_row">
	  							<div class="col1">&nbsp;</div>
	  							<div class="col2_black">
		  							<form:checkbox id="${vasItem.itemId}" path="moovItems[${status.index}].selected" onclick="clickchildcheck(this)"/>
									${vasItem.itemDesc }
		  						</div>
								<div class="col3">${vasItem.displayAmtTxt}</div>
	  						</div>
						</c:forEach>
						<div class="hidedtlbtn" style="float:right;"><a onclick="togglegrp(this);"><img style="width:auto" src="./images/${lang}/hide_btn.png"/></a></div>
						<div class="clearboth"></div>
					</div>
					<hr/>
				</div>
				</c:if>
				<!-- MOOV ROWS -->
				
				<!-- VAS HOT ROWS -->
				<c:if test="${not empty vasDetailForm.otherItems}">
				<c:forEach items="${vasDetailForm.otherItems}" var="vasItem" varStatus="status">
				<div class="vas_row">
					<div class="vas_title">
						<form:checkbox id="${vasItem.itemId}" path="otherItems[${status.index}].selected" onclick="vasonclick(this)" cssStyle="float: left;"/>
						<div style="display: block;">${vasItem.itemDesc }</div>
						<div class="showdtlbtn" style="float:right;"><a onclick="togglegrp(this);"><img src="./images/${lang}/details_btn_2.png"/></a></div>
					</div>
					<div class="vas_detail" style="display: none;">
						<!--  <div class="program_row">
	  						<div class="col1">&nbsp;</div>
	  						<div class="col2_green"><spring:message code="vas.header.des" /></div>
	 						<div class="col3"><spring:message code="vas.header.term.price" /></div>
	  					</div>-->
						
							<div class="program_row">
	  							<div class="col1">&nbsp;</div>
	  							<div class="col2_black"> ${vasItem.itemDisplayHtml} <c:if test="${empty vasItem.itemDesc}"> - </c:if> </div>
	  							<div class="col3">${vasItem.displayAmtTxt}</div>
	  						</div>
						<div class="hidedtlbtn" style="float:right;"><a onclick="togglegrp(this);"><img style="width:auto" src="./images/${lang}/hide_btn.png"/></a></div>
						<div class="clearboth"></div>
					</div>
					<hr/>
				</div>
				</c:forEach>
				</c:if>
				<!-- VAS HOT ROWS -->

</div>
<!--end of vas-->
</form:form>

</div>
<!--end of vas main-->
<div class="clearboth"></div>
</div>
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
									onclick="top.location.href='basketselect.html'"/>
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

<!--cart area-->
<iframe id="cartarea" src="">
</iframe>
<!--end of cart area-->
</body>
</html>
