<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>

<html>
<head>
	<style>
		.tdo_list{}
		.tdo_list li{margin-bottom: 20px;}
		.confirm_box{border: 1px solid #000000; margin-top: 40px; padding-top: 10px; width: 100%;}
	</style>
	
	<script type="text/javascript">
			$(document).ready(function(){
				//$(".detailcontent").colorbox({innerWidth:470, innerHeight:170, iframe:true });	
				$(".checkconfirm").click(function(event){
					event.preventDefault();
					if($("#Comfirmed").is(":checked")){
						top.location = "basketselect.html";
					}else{
						$('html, body').animate({
					         scrollTop: $("#Comfirmed").offset().top
					         
					    }, 500);
						$("#warning_notconfirmed_msg").html("<spring:message code="tdo.warn" />");
						$("#Comfirmed").focus();
					}
				});
			});
	</script>
</head>
<body>
<div id="wrapper">
<!--content-->
	<div id="content">
		<!--flow nav-->
		<div class="flow">
			<ul>
			<li class="flow_active">
				<spring:message code="tdo.title"/>
			</li>
			</ul>
		</div>
		<div id="main" style="min-height: 200px !important;">
	
		<div id="middle_content" style="visibility: visible;padding:0px 10px 10px 10px;">
			<div id="tdo_content" style="width: 840px;">
			<spring:message code="tdo.heading" />
				<c:if test="${srv=='EYE'}">
					<spring:message code="tdo.eye" />
				</c:if>
				<c:if test="${srv=='DEL'}">
					<spring:message code="tdo.del" />
				</c:if>
				<br/>
				<table cellpadding="5" class="confirm_box">
                    <tr>
						<td width="20" valign="top">
							<input type="checkbox" id="Comfirmed" />
						</td>
						<td valign="top">
							<spring:message code="tdo.confirm" />
						</td>
				   </tr>
				   <tr>
				  		<td colspan=2><span id="warning_notconfirmed_msg" style="color:#FF0000; "></span></td>
					</tr>
		  		</table>
			</div>
		</div>
	</div>
	</div>
	<div id="floating_bar" style="margin-left: 0px;">
		<div class="grad"></div>
			<div id="bottom_content"> 
				<table width="100%" style="bottom: -8pt;position: relative;"> 
					<tbody>
						<tr align="center"> 
							<td width="60%">
							<img id="nextview" class="checkconfirm" src="./images/${lang}/confirm_btn.png" 
								onmouseover="this.src='./images/${lang}/confirm_btn_mo.png'" 
								onmouseout="this.src='./images/${lang}/confirm_btn.png'" 
								style="margin-left:0pt;cursor: pointer;"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		<img src="images/${lang}/bottom_bar.png" />
	</div>
</div>
</body>
</html>
	