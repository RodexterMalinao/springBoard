<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="js/web/lts/ltscommon.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/web/lts/acq/ltsacqbillingaddress.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/lts/acq/ltsacqprogressbar.jsp">
	<jsp:param name="step" value="6" />
</jsp:include>

<script src="js/jquery.lts.autocomplete.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<c:set var="isOrderSubmitted"
	value="${sessionScope.sessionLtsOrderCapture.sbOrder != null}" />

<form:form method="POST" action="#" id="billingaddressform"	name="ltsBillingAddressForm" commandName="ltsbillingaddress" autocomplete="off">
	<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
	<input type="hidden" id="errorMsg" />

	<table width="100%" class="paper_w2 round_10">
		<tr>
			<td>
				<table width="100%" border="0" cellspacing="1"
					class="paper_w2 round_10">
					<tr>
						<div id="s_line_text"><spring:message code="lts.acq.billingAddress.billingAddress" htmlEscape="false"/></div>
					</tr>
					<tr><td>
					<c:choose>
                      <c:when test="${not empty ltsbillingaddress.billingAddrDtlList}">
                       						<td>
							 <c:forEach items="${ltsbillingaddress.billingAddrDtlList}" var="account" varStatus="status">
								<table width="100%" border="0" cellspacing="1">
									<tr>
										<td><span style="color: DarkBlue;"> <b>&nbsp;<spring:message code="lts.acq.billingAddress.accountNumber" htmlEscape="false"/> </b> ${account.acctNum}</span></td>
									</tr>
									<tr><td></td></tr>
									<tr><td></td></tr>
									<tr><td><b><spring:message code="lts.acq.billingAddress.enterNewBillAddress" htmlEscape="false"/></b></td></tr>
<!-- 																				<tr><td>
											<table width="100%" border="0" cellspacing="2" >
											<tr>
												<td width="30%">&nbsp;</td>
												<td width="70%">&nbsp;</td>
											</tr> -->
									<tr><td>
										<table class="billingAddrTable" width="100%" border="0" cellpadding="1" cellspacing="1" >
												<tr><td colspan="2">&nbsp;</td></tr>
												<tr>
													<td align="right" width="15%"><spring:message code="lts.acq.billingAddress.quickSearch" htmlEscape="false"/></td>
													<td align="left"><input id="baQuickSearch${status.index}" class="baQuickSearch" size="80"/> 
													&nbsp; 
													<div class="func_button">
					                                   <a id="clearBaInputAddress${status.index}" style="color:white"  class="clearBtn" href="baQuickSearch${status.index}"><spring:message code="lts.acq.billingAddress.clear" htmlEscape="false"/></a>
							                      	</div>
												</tr>
												<tr>
													<td colspan="2">&nbsp;</td>
												</tr>
												<tr>
													<td align="right" valign="top" width="15%"><spring:message code="lts.acq.billingAddress.billingAddress" htmlEscape="false"/> :&nbsp;</td>
													<td><form:radiobutton cssClass="iAradio" path="billingAddrDtlList[${status.index}].baCaAction" id="sameIAradio${status.index}" value="I"/><spring:message code="lts.acq.billingAddress.sameNewIA" htmlEscape="false"/>
														<c:if test="${not empty account.acctBillingAddress}">
															<form:radiobutton cssClass="remainRadio" path="billingAddrDtlList[${status.index}].baCaAction" id="remainRadio${status.index}" value="E"/><spring:message code="lts.acq.billingAddress.remainUnchanged" htmlEscape="false"/>
														</c:if>
														<form:radiobutton cssClass="diffBaRadio" path="billingAddrDtlList[${status.index}].baCaAction" id="diffBaRadio${status.index}" value="N"/><spring:message code="lts.acq.billingAddress.differentBA" htmlEscape="false"/>
												    	<!-- <input type="radio" class="remainRadio" name="addrRadio${status.index}" id="remainUnchangeRadio${status.index}" value="${account.billingAddressTextArea}">Remain unchanged
												    	 -->
												    </td>
												</tr>
												<tr>
													<td></td>
													<td>
														<div style="float: left">
														<form:textarea id="billingAddressTextArea${status.index}" cssClass="billingAddressTextArea" path="billingAddrDtlList[${status.index}].billingAddressTextArea" rows="6" cols="40"	cssStyle="resize: none;" />
														<br>
														<form:errors path="billingAddrDtlList[${status.index}].billingAddressTextArea" cssClass="error" />
														<input type="hidden" value="${account.acctBillingAddress}" class="originalBillAddr" id="originalBillAddr${status.index}">
														</div>
														<c:if test="${status.index > 0}">
															<div class="func_button copyBtn">
						                                    <a id="copyBtn${status.index}" style="color:white" href="billingAddressTextArea${status.index}"><spring:message code="lts.acq.billingAddress.copy" htmlEscape="false"/></a>
								                         	</div>
							                         	</c:if>
							                         </td>
												</tr>
											</table>
										
									<tr><td><br><br><br></td></tr>
								</table>
							</c:forEach>
                        </td>
                      </c:when>

                      <c:otherwise>
 							<table width="100%" border="0" cellspacing="1">
									<tr>
										<td><span style="color: DarkBlue;"> <b>&nbsp;<spring:message code="lts.acq.billingAddress.newAccount" htmlEscape="false"/></b></span></td>
									</tr>
									<tr><td></td></tr>
									<tr><td></td></tr>
									<tr><td><b><spring:message code="lts.acq.billingAddress.enterNewBillAddress" htmlEscape="false"/></b></td></tr>
<!-- 																				<tr><td>
											<table width="100%" border="0" cellspacing="2" >
											<tr>
												<td width="30%">&nbsp;</td>
												<td width="70%">&nbsp;</td>
											</tr> -->
									<tr><td>
										<table width="100%" border="0" cellpadding="1" cellspacing="1" >
												<tr><td colspan="2">&nbsp;</td></tr>
												<tr>
													<td align="right" width="15%"><spring:message code="lts.acq.billingAddress.quickSearch" htmlEscape="false"/></td>
													<td align="left"><input id="baQuickSearch" size="80"/> 
													&nbsp; 
													<div class="func_button">
					                                   <a id="clearBaInputAddress" style="color:white"  class="searchCriteria" href="#">Clear</a>
							                      	</div>
													<!-- <input name="clearBaInputAddress"	type="button" value="Clear"></td> -->
												</tr>
												<tr>
													<td colspan="2">&nbsp;</td>
												</tr>
												<tr><td align="right" valign="top" width="15%"><spring:message code="lts.acq.billingAddress.billingAddress" htmlEscape="false"/> :&nbsp;</td>
												   <td><form:radiobutton path="baOption" /><spring:message code="lts.acq.billingAddress.sameNewIA" htmlEscape="false"/> 
												       <form:radiobutton path="baOption" /><spring:message code="lts.acq.billingAddress.remainUnchanged" htmlEscape="false"/></td>
												</tr>
												<tr>
													<td ></td> 
													<td><div style="float: left">
														<form:textarea id="billingAddressTextArea" path="billingAddressTextArea" rows="6" cols="40"	cssStyle="resize: none;" />
														</div>
														<input type="button" id="copyBtn" value="Copy">
														<div style="width: 30%; float: left; padding-left: 10px;">
														<form:errors path="billingAddressTextArea" cssClass="error" />
														</div></td>
												</tr>
												
											</table>
										
									<tr><td><br><br><br></td></tr>
								</table>
                       <br />
                      </c:otherwise>
                    </c:choose>
</td>
</tr>
</table> 
<br>
<table width="100%" border="0" cellspacing="0" class="paper_w2 round_10">
<tr>
	<td align="right"><br /> <a id="submit" href="#"><div class="button"><spring:message code="lts.acq.common.continue" htmlEscape="false"/></div></a></td>
</tr>
</table> </td></tr></table></form:form>
<script language="javascript">
var isRetail = ${sessionScope.bomsalesuser.channelId == 1};
var isCc = ${sessionScope.bomsalesuser.channelId == 2};
var iA = "${installAddress}";
$(ltsbillingaddress.actionPerform);

</script> <%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>