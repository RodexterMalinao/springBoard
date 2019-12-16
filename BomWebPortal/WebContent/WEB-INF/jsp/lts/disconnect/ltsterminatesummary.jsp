<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="/WEB-INF/jsp/ltsinfobar.jsp" />

<c:set var="orderAction" value="${sessionScope.sessionLtsTerminateOrderCapture.orderAction}" />
<c:if test="${orderAction != 'ENQUIRY' && orderAction != 'ENQUIRY_N_CANCEL'}">
	<jsp:include page="/WEB-INF/jsp/lts/disconnect/ltsterminateprogressbar.jsp" >
		<jsp:param name="step" value="6" />
	</jsp:include>
</c:if>

<br/>
<form:form id="summary" method="POST" commandName="serviceSummary" autocomplete="off">
<input type="hidden" name="sbuid" value="${sessionScope.sbuid}" />

<div style="width:97%; padding-left:20px;" id="s_line_text">Order Summary</div>
	<div align="center">
		<div style="width:97%; border-width: 1px; border-style: solid; border-color: black;" class="paper_w2">
			<br/>
			<div style=" width:96%" align="left">
		   	<c:if test="${orderAction != 'ENQUIRY' && orderAction != 'ENQUIRY_N_CANCEL'}">
				<div><span class="title_a">Please review your order: </span></div>
				<div><span class="title_b">Order no.:  ${serviceSummary.orderId}</span></div>
			</c:if>
			<c:if test="${orderAction == 'ENQUIRY' || orderAction == 'ENQUIRY_N_CANCEL'}">
				<div><span class="title_a">The order number is ${serviceSummary.orderId}</span></div>
					<c:forEach items="${serviceSummary.statusHistList}" var="statusHist">
						<c:if test="${empty statusHist.reasonCd}">	
							<span class="title_a"> Order ${statusHist.sbStatus} on ${statusHist.statusDate} </span><BR>
						</c:if>
						<c:if test="${not empty statusHist.reasonCd}">	
						    <c:choose>
					    	   <c:when test="${statusHist.sbStatus == 'cancel' && statusHist.reasonCd == 'Manual Cancellation'}">
					        	   <span class='title_b'>Order ${statusHist.sbStatus} on ${statusHist.statusDate} with reason ${statusHist.reasonCd} by ${statusHist.lastUpdBy}</span><BR>
					       		</c:when>
					       		<c:otherwise>
					       	    	<span class='title_b'>Order ${statusHist.sbStatus} on ${statusHist.statusDate} with reason ${statusHist.reasonCd}</span><BR>
					       		</c:otherwise>
					    	</c:choose>
						</c:if>
									
					</c:forEach>
			</c:if>	  
			<c:if test="${not empty sessionScope.signOffError && sessionScope.signOffError != null && sessionScope.signOffError != 'null'}">
				<div id="error_div" class="ui-widget" style="visibility: visible;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						</p>
						<div id="error_msg" class="contenttext">
							<c:out value="${sessionScope.signOffError}"></c:out>			
						</div>
						<p></p>
					</div>
				</div>
				<c:set var="signOffError" scope="session" value="" />
			</c:if>
			<div style=" valign:bottom ; height:40;" id="line_text" >Termination of Fixed Line Service</div>
		
			<div id="s_line_text" class="table_title" >CUSTOMER DETAILS</div>
				<div  style="display:table; width:90%; border:0px; border-spacing: 10px; border-collapse: separate;" align="left" class="contenttext" >
		       	<table style="width:100%" cellpadding="3" cellspacing="3">
				    <tr>
					<td align="left" width="10%">&nbsp;</td>
					<td width="25%">&nbsp;</td>
					<td width="55%">&nbsp;</td>
					</tr> 
					<tr>
						<td >&nbsp;</td>
						<td align="left" ><b>Customer Name : </b></td>
						<td><c:out value="${serviceSummary.disconnectServiceSummaryList.name}" default="--" /></td>
					</tr>
					<c:if test="${not empty serviceSummary.disconnectServiceSummaryList.companyName}">
						<tr>
						<td>&nbsp;</td>
							<td align="left"  ><b>Company Name: </b></td>
							<td><c:out value="${serviceSummary.disconnectServiceSummaryList.companyName}" default="--"/></td>
						</tr>
					</c:if>
					<tr>
					<td>&nbsp;</td>
						<td align="left" ><b>Document Number : </b></td>
						<td>
							<c:out value="${serviceSummary.disconnectServiceSummaryList.docType}: ${serviceSummary.disconnectServiceSummaryList.docNum}" default="--"/>
						</td>
					</tr>
					<tr>
					<td>&nbsp;</td>
						<td align="left" ><b>Disconnect Reason : </b></td>
						<td><c:out value="${serviceSummary.disconnectServiceSummaryList.disconnectReason}" default="--"/></td>
					</tr>
					<tr>
					<td>&nbsp;</td>
						<td align="left" ><b>D-Form Serial : </b></td>
						<td><c:out value="${serviceSummary.disconnectServiceSummaryList.dFromSerialNum}" default="--"/></td>
					</tr>
					<tr>
					<td>&nbsp;</td>
						<td align="left" ><b>Waive D-Form Reason : </b></td>
						<td><c:out value="${serviceSummary.disconnectServiceSummaryList.waiveDfromReason}" default="--"/></td>
					</tr>
					<tr>
					<td>&nbsp;</td>
						<td align="left" ><b>5NA (Mass) / 3NA (PT): </b></td>
						<td><c:out value="${serviceSummary.disconnectServiceSummaryList.nA}" default="--"/></td>
					</tr>
					<tr>
					<td>&nbsp;</td>
						<td align="left"  ><b>Third Party application : </b></td>
						<td><c:out value="${serviceSummary.disconnectServiceSummaryList.thirdPartyAppl}" default="N"/></td>
					</tr>
					<tr>
					<td>&nbsp;</td>
						<td align="left" ><b>Lost Equipment : </b></td>
						<td><c:out value="${serviceSummary.disconnectServiceSummaryList.lostEquipment}" default="--"/></td>		
					</tr>
					<tr>
					<td>&nbsp;</td>
						<td align="left" ><b>Lost Modem : </b></td>
						<td><c:out value="${serviceSummary.disconnectServiceSummaryList.lostModem}" default="--"/></td>		
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
					</div>
</div>
<br/>
   <div style="padding-left:30px ;width:96%" align="left">
			<div id="s_line_text" class="table_title" >SALES INFO</div>
			<div  style="display:table; width:90%; border:0px; border-spacing: 10px; border-collapse: separate;" align="left" class="contenttext" >
        		<table style="width:100%" cellpadding="3" cellspacing="3">
			    <tr>
				<td align="left" width="10%">&nbsp;</td>
				<td width="25%">&nbsp;</td>
				<td width="55%">&nbsp;</td>
				</tr> 
				<tr>
					<td >&nbsp;</td>
					<td align="left" ><b>Staff No.: </b></td>
					<td><c:out value="${serviceSummary.staffNum }"/></td>
				</tr>
				<tr>
					<td >&nbsp;</td>
					<td align="left"><b>Salesmen Code / CMRID: </b></td>
					<td><c:out value="${serviceSummary.salesCd}"/></td>
				</tr>
				<tr>
					<td >&nbsp;</td>
					<td align="left" ><b>Sales Channel: </b></td>
					<td><c:out value="${serviceSummary.salesChannel}"/></td>
				</tr>
				<tr>
					<td >&nbsp;</td>
					<td align="left" ><b>Team / Shop Code:  </b></td>
					<td><c:out value="${serviceSummary.salesTeam}"/></td>
				</tr>
				<tr>
					<td >&nbsp;</td>
					<td align="left" ><b>Sales Contact No.:   </b></td>
					<td><c:out value="${serviceSummary.salesContactNum}"/></td>
				</tr>
				<c:if test="${not empty serviceSummary.salesPosition}">
				<tr>
					<td >&nbsp;</td>
					<td align="left" ><b>Position: </b></td>
					<td><c:out value="${serviceSummary.salesPosition}"/></td>
				</tr>
				</c:if>
				<c:if test="${not empty serviceSummary.salesJob}">
				<tr>
					<td >&nbsp;</td>
					<td align="left" ><b>Job: </b></td>
					<td><c:out value="${serviceSummary.salesJob}"/></td>
				</tr>
				</c:if>
				<c:if test="${not empty serviceSummary.salesProcessDate}">
				<tr>
					<td >&nbsp;</td>
					<td align="left" ><b>Process Date: </b></td>
					<td><c:out value="${serviceSummary.salesProcessDate}"/></td>
				</tr>
				</c:if>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
			</div>
</div>
<br>
<div style="padding-left:30px ;width:96%" align="left">
			<div id="s_line_text" class="table_title" >RELATED FSA ORDER</div>
			<div  style="display:table; width:90%; border:0px; border-spacing: 10px; border-collapse: separate;" align="left" class="contenttext" >
        		<table style="width:100%" cellpadding="3" cellspacing="3">
			    <tr>
				<td align="left" width="10%">&nbsp;</td>
				<td width="25%">&nbsp;</td>
				<td width="55%">&nbsp;</td>
				</tr>  
				<tr>
					<td >&nbsp;</td>
					<td align="left" ><b>FSA Number :  </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.fsaNumber}" default="--"/></td> 
				</tr>
				<tr>
				<!-- FSA Pending Order -->
					<td >&nbsp;</td>
					<td align="left" ><b>FSA Pending Order Springboard ID :  </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.fsaPendingSbOrdId}" default="--"/></td>
				</tr>
				<tr>
				<!-- FSA Pending Order -->
					<td>&nbsp;</td>
					<td align="left" ><b>FSA Pending Order OCID :  </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.fsaPendingOCID}" default="--"/></td>
				</tr>
				<tr>
				<!-- FSA SRD -->
					<td >&nbsp;</td>
					<td align="left" ><b>FSA SRD : </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.fsaSrd}" default="--"/></td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
			</div>
</div>
<br>
 <div style="padding-left:30px ;width:96%" align="left">
		<div id="s_line_text" class="table_title" >TERMINATION ORDER SUMMARY</div>
		<br/><br/>
		
		<b>Services to be terminated :</b>
        <table style="width:98%;" class="table_style" >
			<tr>
				<th align="center"><b>TP Categories</b></th>
				<th align="center"><b>TP Description</b></th>
				<th align="center"><b>Effective Date</b></th>
				<th align="center"><b>End Date</b></th>
				<th align="center"><b>Gross Effective Price</b></th>
				<th align="center"><b>Penalty Handling</b></th>															
			</tr>
			<c:set var="rowCount" value="0" />
			<c:forEach items="${serviceSummary.disconnectServiceSummaryList.disSrvItemList}" var="srvList" varStatus="status">
			<c:set var="rowCount" value="${rowCount + 1 }"/>
				<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''} " >
					<td align="center"><c:out value="${srvList.tpCatg}" default="-" /></td>
					<td align="center"><c:out value="${srvList.itemDisplayHtml}" default="-" /></td>
					<td align="center"><c:out value="${srvList.existStartDate}" default="-" /></td>
					<td align="center"><c:out value="${srvList.existEndDate}" default="-" /></td>
					<td align="center">$<c:out value="${srvList.grossEffPrice}" default="-"/></td>
					<td align="center"><c:out value="${srvList.penaltyHandling }" default="Free To Go"></c:out></td>
				</tr>
			</c:forEach>
			<c:if test="${rowCount == 0}">
				<tr><td colspan="6" align="center"><b>NIL</b></td></tr>
			</c:if>
		</table>
		
		<br/>
		<br/>
		
		<b>Other VAS to be terminated :</b>
		<table style="width:98%" class="table_style" >
			<tr>
				<th align="center"><b>Offer Categories</b></th>
				<th align="center"><b>Offer Description</b></th>
				<th align="center"><b>Effective Date</b></th>
				<th align="center"><b>End Date</b></th>
				<th align="center"><b>Gross Effective Price</b></th>
				<th align="center"><b>Net Effective Price</b></th>
				<th align="center"><b>Penalty Handling</b></th>															
			</tr>
			<c:set var="rowCount" value="0" />
													
			<c:forEach items="${serviceSummary.disconnectServiceSummaryList.vasItemList}" var="srvList" varStatus="status">
			<c:set var="rowCount" value="${rowCount + 1 }"/>
				<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''} " >
					<td align="center"><c:out value="${srvList.tpCatg}" default="-" /></td>
					<c:choose>
					<c:when test="${not empty srvList.itemDisplayHtml}">
					<td align="center">${srvList.itemDisplayHtml}</td>
					</c:when>
					<c:otherwise>
					<td align="center">-</td>
					</c:otherwise>
					</c:choose>
					<td align="center"><c:out value="${srvList.existStartDate}" default="-" /></td>
					<td align="center"><c:out value="${srvList.existEndDate}" default="-" /></td>
					<td align="center">$<c:out value="${srvList.grossEffPrice}" default="-"/></td>
					<td align="center">$<c:out value="${srvList.netEffPrice}" default="-"/></td>
					<td align="center"><c:out value="${srvList.penaltyHandling }" default="Free To Go"></c:out></td>
				</tr>
			</c:forEach>
			<c:if test="${rowCount == 0}">
				<tr><td colspan="7" align="center"><b>NIL</b></td></tr>
			</c:if>
		</table>
		
			<br/>
			<br/>
		
		
		<b>Related FSA VAS to be terminated :</b>
		<table style="width:98%" class="table_style" >
			<tr>
				<th align="center"><b>Offer Categories</b></th>
				<th align="center"><b>Offer Description</b></th>
				<!-- <th align="center"><b>Effective Date</b></th>
				<th align="center"><b>End Date</b></th> -->
				<th align="center"><b>Gross Effective Price</b></th>
				<th align="center"><b>Net Effective Price</b></th>
				<th align="center"><b>Penalty Handling</b></th>															
			</tr>
			<c:set var="rowCount" value="0" />
													
			<c:forEach items="${serviceSummary.disconnectServiceSummaryList.imsVasItemList}" var="srvList" varStatus="status">
			<c:set var="rowCount" value="${rowCount + 1 }"/>
				<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''} " >
					<td align="center"><c:out value="${srvList.tpCatg}" default="-" /></td>
					<c:choose>
					<c:when test="${not empty srvList.itemDisplayHtml}">
					<td align="center">${srvList.itemDisplayHtml}</td>
					</c:when>
					<c:otherwise>
					<td align="center">-</td>
					</c:otherwise>
					</c:choose>
					<%-- <td align="center"><c:out value="${srvList.existStartDate}" default="-" /></td>
					<td align="center"><c:out value="${srvList.existEndDate}" default="-" /></td> --%>
					<td align="center">$<c:out value="${srvList.grossEffPrice}" default="-"/></td>
					<td align="center">$<c:out value="${srvList.netEffPrice}" default="-"/></td>
					<td align="center"><c:out value="${srvList.penaltyHandling }" default="Free To Go"></c:out></td>
				</tr>
			</c:forEach>
			<c:if test="${rowCount == 0}">
				<tr><td colspan="7" align="center"><b>NIL</b></td></tr>
			</c:if>
		</table>
		
			<br/>
			<br/>
		
		
			
		<b>IDD 0060 : </b>
		  <table style="width:98%" class="table_style" >
			<tr>
				<th align="center"><b>Service Type</b></th>
				<th align="center"><b>Phone Number</b></th>
				<th align="center"><b>Action</b></th>
			</tr>
			<c:set var="rowCount" value="0"/>
				<c:forEach items="${serviceSummary.disconnectServiceSummaryList.srv0060DtlList}" var="iddDetails" varStatus="status">
						<c:set var="rowCount" value="${rowCount + 1 }"/>
						<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''}">
							<td align="center">
								${iddDetails.srvType} / ${iddDetails.datCode}
							</td>
							<td align="center">
								<c:out value="${iddDetails.srvNum}" default="-"/>
							</td>
							<td align="center">
								<c:out value="${iddDetails.action}" default="-"/>
							</td>
						</tr>
				</c:forEach>
			<c:if test="${rowCount == 0}">
			<tr>
				<td colspan="4" align="center"><b>NIL</b></td>
			</tr>
			</c:if> 
		</table>
		
		<br/>
		<br/>				
		
		<b>IDD Fixed-Fee Plans : </b>
		<table style="width:98%" class="table_style" >
			<tr>
				<th align="center"><b>Action</b></th>
				<th align="center"><b>Plan Code</b></th>
				<th align="center"><b>Holder Type</b></th>
				<th align="center"><b>Service Description</b></th>
				<th align="center"><b>Effective Start Date</b></th>
				<th align="center"><b>Contract Start Date</b></th>
				<th align="center"><b>Contract End Date</b></th>
				<th align="center"><b>Remain Contract</b></th>
				<th align="center"><b>Commitment Fee</b></th>
				<th align="center"><b>Effective End Date</b></th>
				<th align="center"><b>Penalty Handling</b></th>
				<th align="center"><b>New DCA</b></th>
			</tr>
			<c:set var="rowCount" value="0" />
													
			<c:forEach items="${serviceSummary.disconnectServiceSummaryList.srvCPDtlList}" var="srvList" varStatus="status">
				<c:set var="rowCount" value="${rowCount + 1 }"/>
				<tr class=" ${rowCount % 2 != 0  ? 'alt' : ''} " >
					<td align="center"><c:out value="${srvList.action}" default="Retain"/></td>
					<td align="center"><c:out value="${srvList.planCd}" default="--" /></td>
					<td align="center"><c:out value="${srvList.planHolderType}" default="--" /></td>
					<td align="center"><c:out value="${srvList.description}" default="--" /></td>
					<td align="center"><c:out value="${srvList.effStartDateForDisplay}" default="--" /></td>
					<td align="center"><c:out value="${srvList.contractStartDateForDisplay}" default="--" /></td>
					<td align="center"><c:out value="${srvList.contractEndDateForDisplay}" default="--" /></td>
					<td align="center"><c:out value="${srvList.remainContract}" default="--"/></td>
					<td align="center"><c:out value="$${srvList.planCharge}" default="--"/></td>
					<td align="center"><c:out value="${srvList.effEndDateForDisplay}" default="--" /></td>
					<td align="center">
						<c:choose>
							<c:when test="${srvList.action != 'REMOVE'}">
								--
							</c:when>
							<c:when test="${srvList.effStartDate gt today}" >
									Plan Not Yet Start
							</c:when>
							<c:when test="${srvList.penaltyHandling == 'OTHER_PARTY_HNDL' }">
								Other Party Handled
							</c:when>
							<%-- <c:when test="${today gt srvList.contractEndDate 
												|| today == callPlanDetail.contractEndDate 
												|| empty srvList.contractStartDate}">
									Free To Go
							</c:when> --%>
							<c:when test="${srvList.penaltyHandling == 'FREE_TO_GO' }">
									Free To Go
							</c:when>
							<c:when test="${srvList.penaltyHandling == 'AW' }">
									Auto Waived
							</c:when>
							<c:when test="${srvList.penaltyHandling == 'G' }">
								Generate
							</c:when>
							<c:when test="${srvList.penaltyHandling == 'MW' }">
								Waived (Special Approval by Marketing)
							</c:when>
							<c:when test="${srvList.penaltyHandling == 'SM' }">
								Waived (Speical Approval by SM)
							</c:when>
							<c:when test="${srvList.penaltyHandling == 'UM' }">
								Waived (Speical Approval by UM)
							</c:when>
							<c:otherwise>
								Free To Go
							</c:otherwise>
						</c:choose>
					</td>
					<td align="center"><c:out value="${srvList.newDca}" default="--"/></td>
				</tr>
			</c:forEach>
			
			<c:if test="${rowCount == 0}">
				<tr><td colspan="12" align="center"><b>NIL</b></td></tr>
			</c:if>
		</table>
			<br>
			<br>
				        
		<b>Calling Card : </b> <c:out value="${serviceSummary.disconnectServiceSummaryList.callingCardHandling}" default="--" /> 
		
			<br>
			<br>
			<br/>
</div>
<br>
<div style="padding-left:30px ;width:96%" align="left">
	<div id="s_line_text" class="table_title" >BILLING INFORMATION</div>
		<div  style="display:table; width:90%; border:0px; border-spacing: 10px; border-collapse: separate;" align="left" class="contenttext" >
		<table style="width:100%" cellpadding="4" cellspacing="4">
		    	<tr>
					<td align="left" width="10%">&nbsp;</td>
					<td width="25%">&nbsp;</td>
					<td width="55%">&nbsp;</td>
				</tr> 
				<c:if test="${not empty serviceSummary.disconnectServiceSummaryList.newBillingName}">
					<tr>
						<td >&nbsp;</td>
						<td align="left" valign="top"><b>New Billing Name : </b></td>
						<td>${serviceSummary.disconnectServiceSummaryList.newBillingName}</td>
					</tr>
				</c:if>
				<tr>
					<td >&nbsp;</td>
					<td align="left" valign="top"><b>Billing Address : </b></td>
					<td>${serviceSummary.disconnectServiceSummaryList.billingAddress}</td>
				</tr>
				<tr>
					<td >&nbsp;</td>
					<td align="left" ><b>Instant Update : </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.billingAddrInstantUpdateInd}" default="--"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left"><b>Billing Media : </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.billingMedia}" default="--"/></td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
			</div>
</div>	
<br>
<div style="padding-left:30px ;width:96%" align="left">
	<div id="s_line_text" class="table_title" >APPOINTMENT SUMMARY</div>
			<div  style="display:table; width:90%; border:0px; border-spacing: 10px; border-collapse: separate;" align="left" class="contenttext" >
        		<table style="width:100%"  cellpadding="3" cellspacing="3">
			    <tr>
				<td align="left" width="10%">&nbsp;</td>
				<td width="25%">&nbsp;</td>
				<td width="55%">&nbsp;</td>
				</tr> 
				<tr>
					<td>&nbsp;</td>
					<td align="left"><b>Application Date :</b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.applDate}"/></td>
				</tr>
<%-- 				<tr>
					<td>&nbsp;</td>
					<td align="left"><b>Application Time : </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.applTime}"/></td> 
				</tr> --%>
				<tr>
					<td>&nbsp;</td>
					<td align="left"><b>SRD : </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.apptDate}"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left" ><b>Cease Rental Date : </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.ceaseRentalDate}" default="--"/></td>		
				</tr>
<%-- 				<tr>
					<td>&nbsp;</td>
					<td align="left"><b>Effective Date : </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.effectiveDate}"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left"><b>Effective Time : </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.effectiveTime}"/></td>
				</tr> --%>
				<c:if test="${serviceSummary.disconnectServiceSummaryList.forceFieldVisitInd == 'Y'}">
				<tr>
					<td >&nbsp;</td>
					<td align="left"><b>Appointment Date :</b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.apptDate}"/></td>
				</tr>
				<tr>
					<td >&nbsp;</td>
					<td align="left"><b>Appointment Time : </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.apptTime}"/></td>
				</tr>
				<tr>
					<td >&nbsp;</td>
					<td align="left"><b>Collect equipment at: </b></td>
					<td><c:out value="${serviceSummary.disconnectServiceSummaryList.collectEquipAddr}"/></td>
				</tr>
				</c:if>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
			</div>
</div>
<br>
<div style="padding-left:30px ;width:96%" align="left">
	<div id="s_line_text" class="table_title" >DISTRIBUTION SUMMARY</div>
			<div  style="display:table; width:90%; border:0px; border-spacing: 10px; border-collapse: separate;" align="left" class="contenttext" >
        		<table style="width:100%"  cellpadding="3" cellspacing="3">
			    <tr>
				<td align="left" width="10%">&nbsp;</td>
				<td width="25%">&nbsp;</td>
				<td width="55%">&nbsp;</td>
				</tr> 
				<tr class="contenttext">
							<td>&nbsp;</td>
							<td><b>Distribution Mode : </b></td>
							<td class="">${serviceSummary.disconnectServiceSummaryList.disMode == 'E'? 'Digital': 
																		serviceSummary.disconnectServiceSummaryList.disMode == 'P'? 'Paper':
																		serviceSummary.disconnectServiceSummaryList.disMode}</td>
						</tr>
						<c:if test="${not empty serviceSummary.disconnectServiceSummaryList.esigEmailAddr}">
							<tr class="contenttext">
								<td>&nbsp;</td>
								<td><b>Email : </b></td>
								<td class="">
									<span id="esigEmailAddr" style="color: red; font-weight: bold;">${Summary.esigEmailAddr}</span>
									</td>
							</tr>
						</c:if>
						<c:if test="${not empty serviceSummary.smsNo}">
							<tr class="contenttext">
								<td>&nbsp;</td>
								<td><b>SMS No.: </b></td>
								<td class="">${serviceSummary.smsNo}</td>
							</tr>
						</c:if>
						<c:if test="${not empty Summary.esigEmailLang}">
						<tr class="contenttext">
							<td>&nbsp;</td>
							<td><b>Language : </b></td>
							<td class="">${Summary.esigEmailLang == 'ENG'? 'English': 
																		Summary.esigEmailLang == 'CHN'? 'Traditional Chinese': 
																		Summary.esigEmailLang}</td>
						</tr>
						</c:if>
						<tr>
			</table>
			</div>
</div>
<br>
<div style="padding-left:30px ;width:96%" align="left">
	<div id="s_line_text" class="table_title" >SUPPORTING DOCUMENTS</div>
		<div  style="display:table; width:100%; border:0px; border-spacing: 10px; border-collapse: separate;" align="left" class="contenttext" >
        	<table class="table_style" style="width:100%"  cellpadding="3" cellspacing="3" border="1">
<!-- 			    <tr>
				<td width="20%">&nbsp;</td>
				<td width="40%">&nbsp;</td>
				<td width="20%">&nbsp;</td>
				<td width="20%">&nbsp;</td>
				</tr>  -->
				<tr >
					<th width="20%">Document Type</th>
					<th width="40%">Waive Reason</th>
					<th width="20%">Collected</th>
					<th width="15%">Mandatory Before Signoff</th>
					<th width="20%">Fax Serial Number</th>
				</tr>
				<c:choose>
					<c:when test="${not empty serviceSummary.disconnectServiceSummaryList.collectDocList}">
						<c:forEach items="${serviceSummary.disconnectServiceSummaryList.collectDocList}" var="supportDocument" varStatus="status">
							<c:if test="${supportDocument.markDelInd != 'Y'}">
								<tr align="center">
									<td>${supportDocument.docTypeDisplay}</td>
									<td><c:out value="${supportDocument.waiveReasonDisplay}" default="--" /></td>
									<td>${supportDocument.collectedInd != 'Y'? 'N': supportDocument.collectedInd}</td>
									<td>${supportDocument.mandatory ? 'Y' : 'N'}</td>
									<td>${supportDocument.faxSerial}</td>
								</tr>
							</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td align="center" colspan="5">NIL</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
</div>
<br>
<c:if test="${not empty serviceSummary.messageList}">
	<div id="error_profile" class="ui-widget" style="visibility: visible;">
		<div class="ui-state-error ui-corner-all" style="margin-top: 1px; padding: 0 .7em;">
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span></p>
				<div id="error_profile_msg" class="contenttext">
					<c:forEach items="${serviceSummary.messageList}" var="message">
				 			${message}
				 			<br>
					</c:forEach>
				</div>
			<p></p>
		</div>
	</div>
</c:if>
<br>
</div>
</div>
<br><br>

<div align="right">
	<c:set var="btnName" value="Submit" />
	<c:if test="${serviceSummary.requireButton == '1'}">
		<c:set var="btnName" value="Request Approval" />
	</c:if>
<%--  	<c:if test="${serviceSummary.requireButton == '4'}">
		<c:set var="btnName" value="Suspend" />
	</c:if> --%>

	<spring:hasBindErrors name="serviceSummary">
	<table width="100%">
		<tr>
			<td width="5%">&nbsp;</td>
			<td width="70%">
				<div id="error_div" class="ui-widget" style="visibility: visible;">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						</p>
						<div id="error_msg" class="contenttext">
							<form:errors path="*"  />				
						</div>
						<p></p>
					</div>
				</div>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table><br>
	</spring:hasBindErrors>	
	<form:hidden path="buttonPressed" id="buttonPressed" />
	
<c:if test="${orderAction != 'ENQUIRY' && orderAction != 'ENQUIRY_N_CANCEL'}">
	<form:errors path="suspendReason" cssClass="error" /> 
	<b>Suspend Reason : </b> 
	<form:select path="suspendReason" id="suspendReason">
		<form:option value="NONE">-- SELECT --</form:option>
		<form:options items="${suspendReasonList}" itemValue="itemKey" itemLabel="itemValue" />
	</form:select> 

	<div class="button">
		<a id="suspend" href="#" class="nextbutton">Suspend</a>
	</div>
	
	<c:if test="${serviceSummary.requireButton != '3' && serviceSummary.requireButton != '4'}">
		<div class="button"><a id="signoff" href="#" class="nextbutton" name="asignoff">${btnName}</a></div>
	</c:if>
	
	<div class="button">
		<a href="#" id="cancel" class="nextbutton">Cancel</a>
	</div>
	
	<br><br>
	<c:if test="${serviceSummary.requireButton == '1'}">
		<div class="func_button">
			<a id="approvalRemarkBtn" href="#" class="nextbutton">Update Approval Remarks</a>
		</div> 
	</c:if>
	
	<c:if test="${sessionScope.bomsalesuser.channelId == 2}">
		<div class="func_button">
			<a href="#" id="updateFaxSerial" class="nextbutton">Update Fax Serial Number</a>
		</div>
	</c:if>
	
	<c:if test="${sessionScope.bomsalesuser.channelId == 3}">
		<a id="captureDoc" href="#" onclick="javascript:window.open('doccapture.html?sbuid=${sessionScope.sbuid}&orderId=${serviceSummary.orderId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')" class="nextbutton"><div class="func_button">Capture Proof (upload via PC only)</div></a>
	</c:if>

</c:if>

<c:if test="${orderAction == 'ENQUIRY_N_CANCEL'}">
	<div class="func_button">
		<a id="cancel" href="#" class="nextbutton">Cancel Order</a>
	</div>
	<div class="func_button">
		<a id="exit" href="welcome.html?sbuid=${param.sbuid}" onclick="return confirm('Exit without Save. Sure to continue?');" class="nextbutton">Exit Without Save</a>
	</div>
</c:if>
</div>
<br><br>
</form:form>
<script type='text/javascript'>
/* $(serviceSummary.actionPerform); */
var alertMsg = '${alertMsg}';
if(alertMsg != ''){
	alert(alertMsg);
}

</script>
<script type="text/javascript">
	
	$(document).ready(function() {

		$("a#signoff").click(function(event) {
			event.preventDefault();
			
			if ("${btnName}" == "Signoff") {
				
				if ($('input[name="orderGeneratePenalty"]').val() == "true") {
					var confirmPenalty = confirm("Penalty will be generated. Continue ?");
					if (!confirmPenalty){
						return;
					}
				}
				
				if ("${serviceSummary.distributeMode}" == 'E' && $('#esigEmailAddr').html() != null) {
					var email = $('#esigEmailAddr').html();
					var answer = confirm("Email will be sent to " + email + " Confirm to continue?");
					if (!answer){
						return;
					}
				}
			} /* else {
				alert("${btnName} Completed");
			} */
			$("#buttonPressed").val("${serviceSummary.requireButton}");
			$("#summary").submit();
		});

		$("#approvalRemarkBtn").click(function(event) {
			event.preventDefault();
			var win = window.open('ltswqremark.html?sbuid=${sessionScope.sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=600,width=1024');
		}); 
		
		$("a#updateFaxSerial").click(function(event) {
			event.preventDefault();
			var win = window.open('ltscollectdoc.html?sbuid=${sessionScope.sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=512,width=1024');
			//win.onclose(function(){location.reload(true); });
		});	
	
	
		$("a#cancel").click(function(event) {
			if (confirm("Please confirm cancel the order.")) {
				$("#buttonPressed").val("2");
				$("#summary").submit();
			}
		});

		$("a#suspend").click(function(event) {
			$("#buttonPressed").val("5");
			/* alert($("#buttonPressed").val()); */
			suspendReasonCheck();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    });
		
	});
	
	function suspendReasonCheck(){
		if ($("#suspendReason").find('option:selected').text() == '-- SELECT --'){
			alert('Please select a suspend reason.');
		}else{
			$("#summary").submit();
		}
	}
</script>



<%@ include file="/WEB-INF/jsp/footer.jsp"%>


