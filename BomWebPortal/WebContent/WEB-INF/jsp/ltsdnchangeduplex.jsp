<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" src="js/web/lts/ltsdnchangeduplex.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />
<jsp:include page="/WEB-INF/jsp/ltsprogressbar.jsp" >
    <jsp:param name="step" value="2" />
</jsp:include>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />

<form:form method="POST" id="ltsDnChangeDuplexForm" name="ltsDnChangeDuplexForm" commandName="ltsdnchangeduplexCmd" autocomplete="off">
<form:hidden path="formAction"/>
<form:hidden path="numSelection"/>
<form:hidden path="searchMethod"/>
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />
<table width="100%" class="paper_w2 round_10">
	<tr>
		<td> 
		<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
				<td><div id="s_line_text">Number Selection</div></td>
			</tr> 
		</table>
		<div id="options">
			<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">				
				<tr>
				    <td width="10%"></td>
					<td width="20%"><div align="left"><b><form:radiobutton path="numSelectionRadio" value="useNewDn" cssClass="searchCriteria" /> Use New DN</b></div></td>
				    <td width="20%"><div align="left"><b><form:radiobutton path="numSelectionRadio" value="keepExistDn" cssClass="searchCriteria" /> Keep Existing DN</b></div></td>
				</tr>
			</table>
		</div>
		<!-- //##TODO## -->
		<c:if test="${ltsdnchangeduplexCmd.duplexProfile}">
		
			<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
				<td><div id="s_line_text">Duplex Number Selection</div></td>
			</tr> 
		</table>
		
			<div id="options">
			<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">				
				<tr>
				    <td width="30%"></td>
					<td width="20%"><div align="left"> DNY (${fn:substring(sessionLtsOrderCapture.ltsServiceProfile.duplexNum,4,12)})</b>
					<input type="hidden" value="${ltsdnchangeduplexCmd.duplexProfile}" id="duplexProfileInd" /></div></td>
					<td align=left><form:errors path="keepDNErrMsg" cssClass="error" /></td>
				</tr>
			</table>
			</div>
			</c:if>
		<!-- ##TODO## END -->
	
		<div id="numberSelection">		
		<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
			    <td><div id="s_line_text">Number Selection (New Service Number)</div></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		<tr><td width="50%" valign="top">	
			<table border="0" cellspacing="4" cellpadding="6" class="contenttext">
				<tr>
				    <td width="10%"></td>
					<td width="19%"><div align="left"><b><form:radiobutton path="searchMethodRadio" value="noCriteriaRadio" cssClass="searchCriteria" /> No Criteria</b></div></td>
					<td width="22%"></td>				
				</tr>
				<tr>
				    <td></td>
				    <td><div align="left"><b><form:radiobutton path="searchMethodRadio" value="first5to8Radio" cssClass="searchCriteria" /> Preferred first 5-8 digits: </b></div></td>
	                <td><div align="left"><form:input maxlength="8" path="first5To8Digits" /></div></td> 
				</tr>				
				<tr>
				    <td></td>
				    <td><div align="left"><b><form:radiobutton path="searchMethodRadio" value="last1to3Radio" cssClass="searchCriteria" /> Preferred last 1-3 digits: </b></div></td>
	                <td><div align="left"><form:input maxlength="3" path="last1To3Digits" /></div></td>
				</tr>
				<tr>
				    <td></td>
				    <td></td>
	                <td></td>
				</tr>
				<tr>
				    <td></td>
				    <td>
				    <div class="func_button">
                    <a id="searchBtn" style="color:white" class="searchCriteria" href="#">Search</a>
                    </div>
				    </td>
	                <td ></td> 
				</tr>
				<tr>
				    <td></td>
				    <td align="left"><form:errors path="searchErrMsg" cssClass="error" /></td>
	                <td></td>
				</tr>													
			</table></td>
			<td width="50%" valign="top">		
			<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">			
				<tr>			    
					<td width="35%"><div align="left"><b><form:radiobutton path="searchMethodRadio" value="reservedDnRadio" cssClass="searchCriteria" /> Reserved DN</b></div></td>
					<td width="60%"></td>				
					<td width="5%"></td>
				</tr>
				<tr>
				    <td><div align="right"><b>Service Number : </div></td>
	                <td ><div align="left"><form:input maxlength="12" path="reservedSrvNum" /></div></td> 
				</tr>
				<tr>
				    <td><div align="right"><b>Account Code : </div></td>
	                <td ><div align="left"><form:input path="reservedAccountCd" /></div></td> 
				</tr>
				<tr>
				    <td><div align="right"><b>BOC : </div></td>
	                <td ><div align="left"><form:input path="reservedBoc" /></div></td> 
				</tr>
				<tr>
				    <td><div align="right"><b>Project Code : </div></td>
	                <td ><div align="left"><form:input path="reservedProjectCd" /></div></td> 
				</tr>
				<tr>
				    <td></td>
	                <td></td> 
				</tr>				
			</table>
			</td></tr>				
		</table>
		<div id="s_line_text"></div>
		<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">
		<tr><td width="50%" valign="top">		
		<table border="0" cellspacing="4" cellpadding="6" class="contenttext">			
			<tr><td width="26%" valign="top">
			<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">	
			<tr>			   
				<td width="100%"><div align="right"><b>Please select a number : </b></div></td>
			</tr>			
			<tr>
			    <td align="right">
			    <div class="func_button" align="right">
                    <a id="lockBtn" style="color:white" class="searchCriteria" href="#">Lock</a>
                </div>
			    </td>
			</tr>
			</table>
			</td>
			<td id="numbox" width="20%" valign="top">			
			<c:if test="${not empty ltsdnchangeduplexCmd.numSelectionList}">
			<c:forEach items="${ltsdnchangeduplexCmd.numSelectionList}" var="itemList" varStatus="itemListStatus">
			<div align="left">
			<table border="0" cellspacing="1" cellpadding="1" class="contenttext">	
		    <tr><td>
		    <form:checkbox onclick="chooseOne(this)" path="numSelectionStringList" value="${itemList.srvNum}" /> ${itemList.displaySrvNum}		    	      
		    </td></tr></table></div>
			</c:forEach>
		    </c:if>							
			</td>
			</tr>			
		</table></td>
		<td width="50%" valign="top">		
		<table border="0" cellspacing="4" cellpadding="6" class="contenttext">			
			<tr><td width="20%" valign="top">
			<table width="100%" border="0" cellspacing="4" cellpadding="6" class="contenttext">	
			<tr>			   
				<td width="100%"><div align="right"><b>Locked Number : </b></div></td>
			</tr>			
			<tr>
			    <td align="right">
			    <div class="func_button" align="right">
                    <a id="releaseBtn" style="color:white" class="searchCriteria" href="#">Release</a>
                </div>
			    </td>
			</tr>
			</table>
			</td>
			<td width="35%" valign="top">
			<c:if test="${not empty ltsdnchangeduplexCmd.reservedDnList}">
			<c:forEach items="${ltsdnchangeduplexCmd.reservedDnList}" var="itemList" varStatus="itemListStatus">
			<div align="left">
			<table border="0" cellspacing="1" cellpadding="1" class="contenttext">	
		    <tr><td>
		    <form:checkbox path="reservedDnStringList" value="${itemList.srvNum}" /> ${itemList.displaySrvNum}		    	      
		    </td></tr></table></div>
			</c:forEach>
		    </c:if>																	
			</td>
			</tr>			
		</table>
		</td></tr>
		<tr>
			<td align=center><form:errors path="lockNumErrMsg" cssClass="error" /></td>
	        <td></td>
		</tr>
		<tr>
		   <td align=center><form:errors path="chooseOneErrMsg" cssClass="error" /></td>
		   <td></td>
		</tr>
		</table>		
		</div>			
			<div id="s_line_text"></div>
	</td>		
	</tr>
		<c:if test="${not empty ltsdnchangeduplexCmd.chargeItem}">
				<tr id="chargeItems">
					<td colspan="5">
						<table width="100%">
							<tr>
								<td colspan="3">
									<table width="100%" border="0">
										<tr>
											<td>
												<table class="table_style" width="100%" border="1">
													<tr>
														<th align="center">Charge Description</th>
														<th align="center">oneTimeAmtTxt</th>
														<th align="center">Penalty Handling</th>
													</tr>
													
													
													
														<tr class="" >
														
															<td align="center"><c:out value="${ltsdnchangeduplexCmd.chargeItem.itemDisplayHtml}" default="-" /></td>
															<td align="center"><c:out value="${ltsdnchangeduplexCmd.chargeItem.oneTimeAmtTxt}" default="-"/></td>
															<td class="penaltyHandling" align="center">
															        <input type="hidden" id="channel" value="${ltsdnchangeduplexCmd.channelID}" />
															        <form:select id="penalty" cssClass="penalty" path="chargeItem.penaltyHandling" >
																		<form:option  value="AG">Generate</form:option>
																		<form:option  value="MW">Wavie</form:option>
																	</form:select>
																	<div id="approveBtn" class="func_button" align="right">                       
                                                                       <a type="hidden" id="approvalBtn" href="approvalloginduplex.html" onclick="window.open(this.href, 'mywin','left=20,top=20,width=500,height=500,toolbar=1,resizable=0'); return false;" >Approval</a>
                                                                    </div>
                                                                    <input type="hidden" id="penaltyApprovalInd" value="${ltsdnchangeduplexCmd.penaltyApprovalInd}" />
															</td>
														</tr>
													<c:if test="${not empty ltsdnchangeduplexCmd.rebateItem}">
														<tr id="rebateItem" style="display: none;">														
														<td align="center"><c:out value="${ltsdnchangeduplexCmd.rebateItem.itemDisplayHtml}" default="-" /></td>
														<td align="center"><c:out value="${ltsdnchangeduplexCmd.rebateItem.oneTimeAmtTxt}" default="-"/></td>
														<td align="center"><c:out value="N/A" default="-"/></td>
													    </tr>
													</c:if>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<div id="s_line_text"></div>
					</td>
				</tr>
				</c:if>
</table>
</form:form>

<c:if test="${not empty requestScope.errorMsgList}">
<div id="errorMsgDiv">
<br>
	<table width="90%" border="0">
		<tr>
			<td width="10%">&nbsp;</td>											
			<td width="80%" align="left">
				<div id="error_Msg_div" class="ui-widget" style="visibility: visible;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<c:if test="${not empty requestScope.errorMsgList}">
						<c:forEach items="${requestScope.errorMsgList }" var="errorMsg">
							<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
								<div  class="contenttext">
									<c:out value="${errorMsg}"></c:out>
								</div>
							<p></p>
						</c:forEach>
						</c:if>
					</div>
				</div>													
			</td>
			<td width="10%">&nbsp;</td>
		</tr>
	</table>
</div>
</c:if>


<div id="continueDiv">
	<table width="100%" border="0" cellspacing="0">
	<tr>
		<td align="right"> 
		<br>
		<a id="continueBtn" href="#" ><div class="button">Continue &gt;</div></a>
		</td>
	</tr>
	</table>
</div>

<script type="text/javascript">
	$(ltsdnchangeduplex.actionPerform);		
</script>

<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp" %>
