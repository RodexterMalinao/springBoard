<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="hkt_header.jsp"%>

<html>
<head>
<script language="javascript" src="js/jquery.colorbox-min.js"></script>
<script type="text/javascript">
function toggleDiv(divId) {
   //$("#"+divId).slideToggle("5000");
   $("#"+divId).fadeToggle("5000");
   //$("#"+divId).toggle("5000");
}

function formSubmit() {
	//validation
	
	//submit
	document.summaryForm.submit();
}

</script>
<script type="text/javascript">
			$(document).ready(function(){
				//$(".detailcontent").colorbox({innerWidth:470, innerHeight:170, iframe:true });	
				$(".checkconfirm").click(function(event){
					event.preventDefault();
					if($("#Comfirmed").is(":checked")){
						if("${summaryForm.showReminder}" == "true"){
							$.colorbox({innerWidth:470, innerHeight:170, iframe:true, fixed:true, href:"reminder.html",	 opacity:"0.6"});
							$("#warning_notconfirmed_msg").html("");
						}else{
							formSubmit();
						}
					}else{
						$('html, body').animate({
					         scrollTop: $("#Comfirmed").offset().top
					         
					    }, 500);
						$("#warning_notconfirmed_msg").html("<spring:message code="sum.comfirm.warn" />");
						$("#Comfirmed").focus();
						//document.getElementById("warning_notconfirmed_msg").innerHTML = "<spring:message code="sum.comfirm.warn" />";
					}
				});
			});
</script>
<script type="text/javascript">

    
</script>
</head>

<body>
<!--wrapper-->
<form:form id="summaryForm" name="summaryForm" method="POST" commandName="summaryForm" action="summary.html">

<div id="wrapper">
<!--top border-->
<!--end of top border-->
<!--content-->
<div id="content">
<!--flow nav-->
<div class="flow">
<!--<a href="javascript:toggleDiv('cartarea');"><span class="cart_icon"></span></a>-->
<ul>
<li class="flow_inactive"><spring:message code="reg.title.iden"/></li>
<li class="flowsep"></li>
<li class="flow_active"><spring:message code="reg.title.confirm"/></li>
</ul>
</div>
<!-- end of flow nav-->

<!--vas main-->
<div id="frame_main">

<!--vas-->
<div id="middle_content" style="visibility: visible;padding:0px 15px 15px 15px;">
<div id="ChForm">
		  <div class="topic">
		  	<span><spring:message code="sum.warn" />[${sessionOnlineOrderCapture.sbOrder.orderId}]</span>
		  	<br />
		  	<c:if test="${summaryForm.paymentFailed}">
		  		<span style="color: #FF0000"><spring:message code="sum.failpayment.warn" /></span>
		  	</c:if>
		  </div>
				<hr />
					<table cellpadding="5">
						<tbody>
						<!--<tr>
							<td>&nbsp;</td>
							<td width="650" class="_strong" style="padding-left:50px">Installation/activation service fee</td>
							<td width="100" class="PlanFeeTitle">$680</td>
						</tr>-->
						<tr>
						  <td colspan="3"><span class="deep">${summaryForm.planItem.itemDesc}<br />
						  </span></td>
						  </tr>
						<tr>
						  <td valign="bottom" style="padding-left:20px"><span class="PlanItemTitle"><spring:message code="sum.header.des"/></span>
						    </td><td width="140" align="center" nowrap="nowrap" class="PlanFeeTitle"></td>
						  <!--<td width="160" align="center" nowrap="nowrap" class="PlanFeeTitle"><spring:message code="sum.header.m2m.price"/></td>-->
						</tr>
						<tr>
						    <td width="650" style="padding:10px 10px 10px 30px;line-height:150%;">
						   		${summaryForm.planItem.itemDisplayHtml}
                            </td>
							<td width="140" align="center" valign="top" style="padding:10px 10px 10px 10px;;">
								<span class="PlanFeeValue">
		                          		${summaryForm.planItem.displayAmtTxt}
								</span>
							</td>
							<!-- <td width="160" align="center" valign="top" style="padding:10px 10px 10px 10px;;"><span class="PlanFeeValue">${summaryForm.planItem.mthToMthAmtTxt}</span></td> -->
						</tr>
						
						<c:if test="${not empty summaryForm.contentItemList}">
							<tr>
							  <td valign="bottom" style="padding-left:20px">
									<span class="PlanItemTitle"><spring:message code="sum.title.basket.content" /></span>
							  </td>
							</tr>
							<c:forEach var="vasItem" items="${summaryForm.contentItemList}" varStatus="status">
								<tr>
		                         <td width="650" style="padding:0px 10px 10px 30px; line-height:150%;">
		                            ${vasItem.itemDisplayHtml}
		                          </td>
		                          <td width="140" align="center" valign="top" style="padding:0px 10px 10px 30px">
		                          	<span class="PlanFeeValue">
		                          		${vasItem.displayAmtTxt}
		                          	</span>
		                          </td>
								 <!-- <td width="160" align="center" valign="top" style="padding:0px 10px 10px 30px;"><span class="PlanFeeValue">${vasItem.mthToMthAmtTxt}</span></td>-->
		                        </tr>
							</c:forEach>
						</c:if>
						
						<c:if test="${not empty summaryForm.premiumItemList}">
							<tr>
							  <td valign="bottom" style="padding-left:20px">
									<span class="PlanItemTitle"><spring:message code="sum.title.basket.prem" /></span>
							  </td>
							</tr>
							<c:forEach var="vasItem" items="${summaryForm.premiumItemList}" varStatus="status">
								<tr>
		                         <td width="650" style="padding:0px 10px 10px 30px; line-height:150%;">
		                            ${vasItem.itemDisplayHtml}
		                          </td>
		                          <td width="140" align="center" valign="top" style="padding:0px 10px 10px 30px">
			                          <span class="PlanFeeValue">
		                          		${vasItem.displayAmtTxt}
			                          </span>
		                          </td>
								  <!-- <td width="160" align="center" valign="top" style="padding:0px 10px 10px 30px;"><span class="PlanFeeValue">${vasItem.mthToMthAmtTxt}</span></td>-->
		                        </tr>
							</c:forEach>
						</c:if>
						
						<c:if test="${not empty summaryForm.vasItemList}">
							<tr>
							  <td valign="bottom" style="padding-left:20px">
									<span class="PlanItemTitle"><spring:message code="sum.title.basket.optsrv" /></span>
							  </td>
							</tr>
							<c:forEach var="vasItem" items="${summaryForm.vasItemList}" varStatus="status">
								<tr>
		                         <td width="650" style="padding:0px 10px 10px 30px; line-height:150%;">
		                            ${vasItem.itemDisplayHtml}
		                          </td>
		                          <td width="140" align="center" valign="top" style="padding:0px 10px 10px 30px">
			                          <span class="PlanFeeValue">
		                          		${vasItem.displayAmtTxt}
			                          </span>
		                          </td>
								<!--  <td width="160" align="center" valign="top" style="padding:0px 10px 10px 30px;"><span class="PlanFeeValue">${vasItem.mthToMthAmtTxt}</span></td>-->
		                        </tr>
							</c:forEach>
						</c:if>
						<c:if test="${not empty summaryForm.nowTvItemList}">
							<tr>
							  <td valign="bottom" style="padding-left:20px">
									<span class="PlanItemTitle"><spring:message code="sum.title.basket.nowTv" /></span>
							  </td>
							</tr>
							<c:forEach var="vasItem" items="${summaryForm.nowTvItemList}" varStatus="status">
								<tr>
		                         <td width="650" style="padding:0px 10px 10px 30px; line-height:150%;">
		                            ${vasItem.itemDisplayHtml}
		                          </td>
		                          <td width="140" align="center" valign="top" style="padding:0px 10px 10px 30px">
			                          <span class="PlanFeeValue">
		                          		${vasItem.displayAmtTxt}
			                          </span>
		                          </td>
								 <!--  <td width="160" align="center" valign="top" style="padding:0px 10px 10px 30px;"><span class="PlanFeeValue">${vasItem.mthToMthAmtTxt}</span></td>-->
		                        </tr>
							</c:forEach>
							<c:forEach var="nowTvDesc" items="${summaryForm.nowTvDescList}" varStatus="status">
								<tr>
		                         <td width="650" style="padding:0px 10px 10px 30px; line-height:150%;">
		                            &nbsp;&nbsp;- ${nowTvDesc}
		                          </td>
		                          <td width="140" align="center" valign="top" style="padding:0px 10px 10px 30px"><span class="PlanFeeValue"></span></td>
								 <!-- <td width="160" align="center" valign="top" style="padding:0px 10px 10px 30px;"><span class="PlanFeeValue"></span></td>-->
		                        </tr>
							</c:forEach>
						</c:if>
						  <tr>
							<td>&nbsp;</td>
							<td></td>
						</tr>
						  <tr>
						    <td><a href="basketselect.html"><img id="editBtn1" src="./images/${lang}/edit_plan_btn.png" 
						    	onmouseover="this.src='./images/${lang}/edit_plan_btn_mo.png'" 
						    	onmouseout="this.src='./images/${lang}/edit_plan_btn.png'"
						    	style="cursor: pointer;" /></a></td>
						    <td></td>
					      </tr>
				      </tbody>
					</table>
				<br />
				<hr />
				<table>
					<tbody><tr>
						<td class="deep"><spring:message code="sum.header.inst.addr" /></td>
					</tr>
					<tr>
						<td>
							${summaryForm.address}
						</td>
					</tr>
				</tbody></table>
				<br />
				<hr />
		  		<table cellpadding="5" cellspacing="0">
					<tbody><tr>
						<td class="deep" colspan="2"><spring:message code="reg.title.iden" /></td>
					</tr>
					<tr>
						<td width="200"><spring:message code="reg.iden.tit" /></td>
							<td>
								<c:if test="${summaryForm.title == 'Mr'}"><spring:message code="reg.iden.tit.1"/></c:if>
								<c:if test="${summaryForm.title == 'Ms'}"><spring:message code="reg.iden.tit.2"/></c:if>
							</td>
					</tr>
					<tr>
						<td><spring:message code="reg.iden.lsna" /></td>
							<td>${summaryForm.familyName}</td>
					</tr>
					<tr>
						<td><spring:message code="reg.iden.fsna" /></td>
						<td>${summaryForm.givenName}</td>
					</tr>
					<tr>
						<td><spring:message code="reg.iden.idt" /></td>
						<td><spring:message code="${summaryForm.docType}" /></td>
					</tr>
					<tr>
						<td><spring:message code="reg.iden.idno" /></td>
							<td>${summaryForm.docNum}</td>
					</tr>
					<tr>
						<td><spring:message code="reg.iden.dob" /></td>

							<td>${summaryForm.dateOfBirth} <spring:message code="sum.dateformat.hint"/></td>
					</tr>
					<tr>
						<td><spring:message code="reg.iden.monum" /></td>

						<td>${summaryForm.contactMobileNum}</td>
					</tr>
					<tr>
						<td><spring:message code="reg.iden.email" /></td>

						<td>${summaryForm.contactEmailAddr}</td>
					</tr>
					<c:if test="${summaryForm.showCsPoralStatement}">
						<tr><td colspan="3"><spring:message code="reg.iden.csportal"/>
						</td></tr>
					</c:if>
					<c:if test="${summaryForm.showTheClubStatement}">
						<tr><td colspan="3"><spring:message code="reg.iden.theclub"/>
						</td></tr>
					</c:if>
					<c:if test="${summaryForm.showCsPoralTheClubStatement}">
						<tr><td colspan="3"><spring:message code="reg.iden.csportal.theclub"/>
						</td></tr>
					</c:if>
										<tr><td style="padding:5px;">&nbsp;</td></tr>
					<tr>
						<td colspan="3" class="deep"><spring:message code="reg.iden.subtitle2" /></td>
					</tr>
					<tr>
						<td><spring:message code="reg.iden.subtitle2" /></td>

							<td><b>
							<c:choose>
								<c:when test="${fn:length(summaryForm.srvNum)==12 && fn:startsWith(summaryForm.srvNum, '0000')}">
									${fn:substring(summaryForm.srvNum, 4, 12)}
								</c:when>
								<c:otherwise>
									${summaryForm.srvNum}
								</c:otherwise>
							</c:choose>
							</b></td>
					</tr>
					<c:if test="${summaryForm.showExDirStatement}">
						<tr><td colspan="2"><spring:message code="reg.iden.whitepage.agree"/></td></tr>
					</c:if>
					<tr><td style="padding:5px;">&nbsp;</td></tr>
					<tr>
						<td colspan="3" class="deep"><spring:message code="reg.sum.appt.title" /></td>
					</tr>
					<tr>
						<td><spring:message code="reg.iden.inst.date" /></td>
						<td><span id="InstallationD">${summaryForm.installDate} <spring:message code="sum.dateformat.hint"/></span></td>
					</tr>
					<tr>
						
							<td><spring:message code="reg.iden.inst.time" /></td>
	<!-- 						<td id="showiTime">Full day(10:00 - 22:00)</td> -->
	
							<td><span id="InstallationT">${summaryForm.installTime}</span></td>
						
					</tr>
					<tr><td style="padding:5px;">&nbsp;</td></tr>
					<c:if test="${not empty summaryForm.epdItemList}">
						<tr>
							<td colspan="3" class="deep"><spring:message code="sum.header.weee" /></td>
						</tr>
						<c:forEach items="${summaryForm.epdItemList}" var="epdItem" varStatus="status">
							<c:if test="${epdItem.selected}">
								<tr>
										<td><spring:message code="sum.title.weee.option" /></td>
										<td><span>${epdItem.itemDesc}</span></td>
								</tr>
								<c:forEach items="${epdItem.itemAttbs}" var="itemAttb" varStatus="attbStatus">
									<c:if test="${itemAttb.visualInd != 'N'}">
										<tr>
												<td>${itemAttb.attbDesc}:</td>
												<td><span>${itemAttb.attbValue}</span></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
						<tr><td style="padding:5px;">&nbsp;</td></tr>
					</c:if>
					<tr>
						<td colspan="3" class="deep"><spring:message code="reg.iden.subtitle4" /></td>
					</tr>
					<tr>
						<td colspan="3">
							${summaryForm.billMethod}
						</td>
					</tr>
						<tr><td style="padding:5px;">&nbsp;</td></tr>
                    <tr>
						<td colspan="3" class="deep"><spring:message code="reg.iden.subtitle5" /></td>
					</tr>
					<tr>
						<td colspan="3">
							<c:if test="${summaryForm.billLang == 'C'}">
								<spring:message code="reg.iden.bill.lang.chi" />
							</c:if>
							<c:if test="${summaryForm.billLang == 'E'}">
								<spring:message code="reg.iden.bill.lang.eng" />
							</c:if>
						</td>
					</tr>
					<tr><td style="padding:5px;">&nbsp;</td></tr>
					<tr>
						<td class="deep"><spring:message code="reg.iden.subtitle6" /></td>
					</tr>
					<tr>
						<td class="highlight">
							<b><spring:message code="reg.iden.pay.cc" /></b>
						</td>
					</tr>
					<tr>
						<td colspan=3>
							<c:if test="${!summaryForm.noPay }">
								<c:if test="${not empty sessionOnlineOrderCapture.applicantInfoForm.prepayItemList}">
									<c:forEach var="prepayItem" items="${sessionOnlineOrderCapture.applicantInfoForm.prepayItemList}" varStatus="status">
										<span style="display: inline; color: rgb(119, 119, 119);">
											${prepayItem.itemDisplayHtml};
										</span>
									</c:forEach>
								</c:if>
								<c:if test="${empty sessionOnlineOrderCapture.applicantInfoForm.prepayItemList}">
									<spring:message code="reg.iden.pay.ca.remind1" />
									<!--<spring:message code="reg.iden.pay.cc.remind2" />
									<span style="font-width:bold"><font class="green_strong"><b>$${summaryForm.paymentAmount}</b></font></span>
									<spring:message code="reg.iden.pay.cc.remind3" />-->
								</c:if>
							</c:if>
							<c:if test="${summaryForm.noPay }">
									<spring:message code="reg.iden.pay.ca.remind1" />
							</c:if>
						</td>
					</tr>
			  		<c:if test="${summaryForm.showPrivacyStatement}">
						<tr><td style="padding:5px;">&nbsp;</td></tr>
						  <tr>
						    <td valign="top" colspan=3><span class="deep"><spring:message code="reg.iden.privacy.title" /></span></td>
					      </tr>
							<tr>
								<td colspan=3><spring:message code="reg.iden.privacy.agree"/></td>
							</tr>
				  		<br/>
			  		</c:if>										
					<tr>
					  <td colspan="2"><br/><a href="applicantinfo.html">
					  <img id="editBtn2" src="./images/${lang}/edit_info_btn.png" 
							onmouseover="this.src='./images/${lang}/edit_info_btn_mo.png'" 
							onmouseout="this.src='./images/${lang}/edit_info_btn.png'"
							style="cursor: pointer;" /></a></td>
					</tr>
					<tr>
						<td><br/></td>
					</tr>
					</tbody>
				</table>
				<hr />
				<table cellpadding="5">
					  <tr>
					    <td colspan="2" valign="top"><span class="deep"><spring:message code="sum.title.cust" /></span>
					    <spring:message code="sum.subtitle.cust1" />
					    <a href="report.html?orderId=${sessionOnlineOrderCapture.sbOrder.orderId}&action=PRINT_AF"><spring:message code="sum.subtitle.cust2" /></a>
					    <spring:message code="sum.subtitle.cust3" />
					    </td>
				      </tr>
         		</table>
                <table cellpadding="5">
                    <tr>
						<td width="20" valign="top">
							<input type="checkbox" id="Comfirmed" />
						</td>
						<td valign="top">
							<spring:message code="sum.cust.content1" />
						</td>
				   </tr>
				   <tr>
				  		<td colspan=2><span id="warning_notconfirmed_msg" style="color:#FF0000; "></span></td>
						
					</tr>
                    <tr>
                      <td valign="top" colspan=2><spring:message code="sum.remind1" />
                      <font class="highlight"><spring:message code="sum.remind2" /></font>
                      <spring:message code="sum.remind3" /></td>
                      <td valign="top"></td>
                    </tr>
		  		</table>
				<a name="bottom"></a>
				<span id="Warning_msg" style="color:#FF0000; visibility:hidden;"><spring:message code="sum.cust.warn" /></span>
				<span id="Warning_msg2" style="color:#FF0000; "></span>
		</div>
	</div>
<!--end of vas-->


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
								<td>
								<c:if test="${summaryForm.showReminder}">
									<a class="checkconfirm detailcontent">
										<!--  <span class="grey_btn" style="margin-top: auto"><spring:message code="bottom.button.confirm.and.payment" /></span>
										-->
										<img src="./images/${lang}/firm_pay_btn.png" 
											onmouseover="this.src='./images/${lang}/firm_pay_btn_mo.png'" 
											onmouseout="this.src='./images/${lang}/firm_pay_btn.png'" 
											style="margin-left:0pt;cursor: pointer;"/>
									</a>
								</c:if>
								
								<c:if test="${!summaryForm.showReminder}">
									<a class="checkconfirm">
										<!-- <span class="grey_btn" style="margin-top: auto"><spring:message code="bottom.button.confirm.and.payment" /></span>
										-->
										<img src="./images/${lang}/firm_pay_btn.png" 
											onmouseover="this.src='./images/${lang}/firm_pay_btn_mo.png'" 
											onmouseout="this.src='./images/${lang}/firm_pay_btn.png'" 
											style="margin-left:0pt;cursor: pointer;"/>
									</a>
								</c:if>
                                </td>
							</tr>
						</tbody>
                    </table>
					</div>  
					<img src="images/${lang}/bottom_bar.png" />
</div>
</div>
</form:form>
<!--end of wrapper-->
</body>
</html>
