<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>



<script language="javascript">
function viewForm(orderId){
	//alert(orderId);
	window.location.replace('imsreport.html?OrderIdForViewPDF='+orderId, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
}
</script>


<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;">
<c:choose>
	<c:when test="${not empty sboImsOrderList}">
	
	
	
<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title">Order ID</td>
		<td class="table_title">OCID</td>
		<td class="table_title">LOB</td>
		<td class="table_title">Customer Name</td>
		<td class="table_title">Service Num</td>
		<td class="table_title">IMS Login id</td>
		<td class="table_title">Application Date</td>
		<td class="table_title">Order Status</td>
		<td class="table_title">Contact Email Address</td>										
		<td class="table_title">Email Sent</td>
		<td class="table_title">Error Message</td>
		<td class="table_title">AF</td>
	</tr>
</thead>

<tbody>



									<c:forEach items="${sboImsOrderList}" var="orderHist">
										<tr>
											<td>
												
												
													
														<c:choose>
															<c:when test='${orderHist.emailSent == "Y"  }'>
															<a href="imsorderdetail.html?orderId=${orderHist.orderId}&imsOrderEnquiry=Y"
																title="Order Enquiry for IMS">${orderHist.orderId}&nbsp;
															</a>
															</c:when>
															<c:otherwise>  
															${orderHist.orderId}
															</c:otherwise>
														</c:choose>
													
													
											</td>

											<td>${orderHist.ocid}&nbsp;</td>

											<td>${orderHist.lob}&nbsp;</td>
											
											<td>${orderHist.orderHistCustName}&nbsp;</td>

											<c:if test='${orderHist.lob == "MOB" && !(empty orderHist.serviceNum)}'>
												<td>MRT: ${orderHist.serviceNum}&nbsp;</td>
											</c:if>
											<c:if test='${orderHist.lob == "IMS" && !(empty orderHist.serviceNum)}'>
												<td>FSA: ${orderHist.serviceNum}&nbsp;</td>
											</c:if>
											<c:if test='${orderHist.lob == "LTS" && !(empty orderHist.serviceNum)}'>
												<c:choose>
													<c:when test="${fn:length(orderHist.serviceNum) == 12}">
														<td>${fn:substring(orderHist.serviceNum, 4, 12)}&nbsp;</td>
													</c:when>
													<c:otherwise>
														<td>${orderHist.serviceNum}&nbsp;</td>	
													</c:otherwise>
												</c:choose>
											</c:if>
											
											
											<c:if test='${empty orderHist.serviceNum || orderHist.lob == null}'>
												<td>&nbsp;</td>
											</c:if>

											<td>${orderHist.imsLoginId}&nbsp;</td>
	
											<td>
												<c:choose>
													<c:when test='${orderHist.lob == "IMS" }'>
														<fmt:formatDate pattern="dd/MM/yyyy"
															value="${orderHist.appDate}" />
													</c:when>
													<c:otherwise>
														<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"
															value="${orderHist.appDate}" />
													</c:otherwise>											
												</c:choose>
												&nbsp;
											</td>
											
											<td>
												<c:choose>
													<c:when test='${orderHist.lob == "MOB"}'>
														<c:choose>
															<c:when test='${fn:length(orderHist.orderId) == 11}'>
																<c:choose>
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "R"}'>
																		<c:choose>
																			<c:when test='${orderHist.orderStatus == "INITIAL"}'>
																				<spring:message code="orderStatus.INITIAL" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "SIGNOFF"}'>
																				<spring:message code="orderStatus.SIGNOFF" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "PENDING"}'>
																				<spring:message code="orderStatus.PENDING" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "PROCESS"}'>
																				<spring:message code="orderStatus.PROCESS" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "SUCCESS"}'>
																				<spring:message code="orderStatus.SUCCESS" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "FAILED"}'>
																				<spring:message code="orderStatus.FAILED" text="" />
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "VOID"}'>
																				<spring:message code="orderStatus.VOID" text="" />
																			</c:when>
																			<c:otherwise>
																				${orderHist.orderStatus}
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:when test='${fn:substring(orderHist.orderId, 0, 1) == "C"}'>
																		<c:choose>
																			<c:when test='${orderHist.orderStatus == "99"}'>
																				<my:code id="${orderHist.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "01"}'>
																				<my:code id="${orderHist.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "02"}'>
																				Completed
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "03"}'>
																				Cancelling
																			</c:when>
																			<c:when test='${orderHist.orderStatus == "04"}'>
																				Cancelled
																			</c:when>
																			<c:otherwise>
																				${orderHist.orderStatus}
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<c:when test='${orderHist.orderStatus == "INITIAL"}'>
																			<spring:message code="orderStatus.INITIAL" text="" />
																		</c:when>
																		<c:when test='${orderHist.orderStatus == "SIGNOFF"}'>
																			<spring:message code="orderStatus.SIGNOFF" text="" />
																		</c:when>
																		<c:when test='${orderHist.orderStatus == "PENDING"}'>
																			<spring:message code="orderStatus.PENDING" text="" />
																		</c:when>
																		<c:when test='${orderHist.orderStatus == "PROCESS"}'>
																			<spring:message code="orderStatus.PROCESS" text="" />
																		</c:when>
																		<c:when test='${orderHist.orderStatus == "SUCCESS"}'>
																			<spring:message code="orderStatus.SUCCESS" text="" />
																		</c:when>
																		<c:when test='${orderHist.orderStatus == "FAILED"}'>
																			<spring:message code="orderStatus.FAILED" text="" />
																		</c:when>
																		<c:when test='${orderHist.orderStatus == "VOID"}'>
																			<spring:message code="orderStatus.VOID" text="" />
																		</c:when>
																		<c:otherwise>
																			${orderHist.orderStatus}
																		</c:otherwise>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test='${orderHist.orderStatus == "INITIAL"}'>
																		<spring:message code="orderStatus.INITIAL" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "SIGNOFF"}'>
																		<spring:message code="orderStatus.SIGNOFF" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "PENDING"}'>
																		<spring:message code="orderStatus.PENDING" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "PROCESS"}'>
																		<spring:message code="orderStatus.PROCESS" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "SUCCESS"}'>
																		<spring:message code="orderStatus.SUCCESS" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "FAILED"}'>
																		<spring:message code="orderStatus.FAILED" text="" />
																	</c:when>
																	<c:when test='${orderHist.orderStatus == "VOID"}'>
																		<spring:message code="orderStatus.VOID" text="" />
																	</c:when>
																	<c:otherwise>
																		${orderHist.orderStatus}
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test='${orderHist.lob == "IMS"}'>
														<spring:message code="ims.orderStatus.${orderHist.orderStatus}" text="${orderHist.orderStatus}" />
													</c:when>
													<c:when test='${orderHist.lob == "LTS"}'>
														<c:choose>
															<c:when test='${orderHist.orderStatus == "S" && orderHist.errMsg != null}'>
																<spring:message code="lts.orderStatus.${orderHist.orderStatus}.${orderHist.errMsg}" text="${orderHist.errMsg}" />
															</c:when>
															<c:otherwise>
																<spring:message code="lts.orderStatus.${orderHist.orderStatus}" text="${orderHist.orderStatus}" />
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														${orderHist.orderStatus}&nbsp;
													</c:otherwise>
												</c:choose>
											</td>

											<td >									
												${orderHist.emailAddress}
											</td>
																						
											<td style="text-align: center;">									
												${orderHist.emailSent}
											</td>
											
											<td>									
												<c:choose>
													<c:when test='${orderHist.lob == "IMS" || orderHist.lob == "LTS"}'>N/A</c:when>										
													<c:otherwise>${orderHist.errMsg}</c:otherwise>
												</c:choose>
												&nbsp;
											</td>
											
											<td>									
												<c:choose>
													<c:when test='${orderHist.lob == "IMS"}'>
<%-- 													<a href="#" class="nextbutton2" style="padding:4px;position:relative;left:2px;top:2px;" onClick="javascript:viewForm('${orderHist.orderId}');">View</a> --%>
													<a href="#" class="nextbutton" style="padding:4px;left:2px;top:2px;float:right" onClick="javascript:viewForm('${orderHist.orderId}');">View</a>
													</c:when>										
													<c:otherwise>Not IMS</c:otherwise>
												</c:choose>
												&nbsp;
											</td>

										</tr>

									</c:forEach>




</tbody>
</table>
	</c:when>
	<c:otherwise>
		<center>No record</center>
	</c:otherwise>
</c:choose>

</div>
