<%@ include file="/WEB-INF/jsp/header.jsp"%>
<style type="text/css">
.success { color: red }
</style>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript">

/* function authorized() {
	
	$('#actionType').val('APPROVE');
	$('#specialMrtRequestForm').submit(); 
	
} */

function checkNumberOfRequest(){

	if (<%=request.getAttribute("twoRequestsAlready")%>) {
		alert("Two requests have been made already!");
		window.location = "mobccsmrtreserve.html";
			
	};
				
}

$(document).ready(function() {
	checkNumberOfRequest();

	$('#exitButton').click(function(e) {
		$('#actionType').val('EXIT');
		$('#specialMrtRequestForm').submit();
	});
	$('#saveButton').click(function(e) {
		$('#actionType').val('SAVE');
		$('#specialMrtRequestForm').submit();
	});
	$('#cancelButton').click(function(e) {
		$('#actionType').val('CANCEL');
		$('#specialMrtRequestForm').submit();
	});
	$('#approveButton').click(function(e) {		
	 	/* e.preventDefault();
		//http://msdn.microsoft.com/en-us/library/ie/ms536759%28v=vs.85%29.aspx
		//var sURL = "mobccsauthorize.html?" + $.param({"action": "AU08"});
			 
		var sURL = "mobccsauthorize.html?" + $.param({ "action" : "AU08", "orderId" : "<c:out value="${param.requestId}" />" });
			 
		var vArguments = self;
		var sFeatures = "dialogHeight:230px;dialogLeft:0;dialogTop:0;dialogWidth:400px;resizable=yes;scroll=yes;status=no";
			window.showModalDialog(sURL, vArguments, sFeatures);
		return false;  */
		$('#actionType').val('APPROVE');
		$('#specialMrtRequestForm').submit();
		
	});
	$('#updateButton').click(function(e) {
		$('#actionType').val('UPDATE');
		$('#specialMrtRequestForm').submit();
	});
	$('#rejectButton').click(function(e) {
		$('#actionType').val('REJECT');
		$('#specialMrtRequestForm').submit();
	});
	

});

$(function() {
	// Datepicker
	$('#validDate').datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "+1"
		//maxDate : "+30D", //Y is year, M is month, D is day  
		//yearRange : "c:c+2"
	});
});
</script>


<form:form method="POST" id="specialMrtRequestForm" name="specialMrtRequestForm" commandName="mobccsspecialmrtrequest">
<table width="100%" class="tablegrey">
	
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="0" rules="none">
					<tr>
						<td class="table_title">Reserve Special MRT Request</td>
					</tr>
				</table>
			</td>
		</tr>
				<!-- requestor table -->
		<tr>
			<td bgcolor="#FFFFFF">
				<table class="contenttext" width="100%" border="0" cellspacing="0">
					<c:if test='${not empty param.requestId}'>
					<tr>
						<td width="25%">
							<div align="right">
							Request ID: 
							</div></td>
						<td width="25%">
							<div class="contenttextgreen">${mobccsspecialmrtrequest.requestId}</div>
						</td>
					</tr>
					</c:if>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.title"/>
							</div></td>
						<td width="25%"><form:select disabled="${user.channelCd=='CPA' || (mobccsspecialmrtrequest.approvalResult != 'REQUEST' && mobccsspecialmrtrequest.approvalResult !=null )}" path="title">
								<form:option value="NONE" label="Select" />
								<form:options items="${titleList}" itemValue="parmValue" itemLabel="parmValue" />
							</form:select> <form:errors path="title" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.name"/>
							</div></td>
						<td width="25%"><form:input path="lastName" disabled="${user.channelCd=='CPA' || (mobccsspecialmrtrequest.approvalResult != 'REQUEST' && mobccsspecialmrtrequest.approvalResult !=null )}"
								maxlength="40" />&nbsp;&nbsp;&nbsp;&nbsp;
						<form:input path="firstName" disabled="${user.channelCd=='CPA' || (mobccsspecialmrtrequest.approvalResult != 'REQUEST' && mobccsspecialmrtrequest.approvalResult !=null )}"
						maxlength="40" />
						</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="right"></div></td>
						<td class="contenttext">&nbsp;&nbsp;
						<spring:message code="label.mobccsspecialmrtrequest.familyName"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<spring:message code="label.mobccsspecialmrtrequest.givenName"/>
						</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="right"></div></td>
						<td width="25%">					
						<form:errors path="lastName" cssClass="error" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;					
						<form:errors path="firstName" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.commitmentPeriod"/>
							</div></td>
						<td width="25%"><form:select disabled="${user.channelCd=='CPA' || (mobccsspecialmrtrequest.approvalResult != 'REQUEST' && mobccsspecialmrtrequest.approvalResult !=null )}" path="contractPeriod">
								<form:option value="NONE" label="Select" />
								<form:options items="${contractPeriodList}" itemValue="parmValue" itemLabel="parmValue"/>
							</form:select> <form:errors path="contractPeriod" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.monthlyRate"/>
							</div></td>
						<td width="25%"><form:select disabled="${user.channelCd=='CPA' || (mobccsspecialmrtrequest.approvalResult != 'REQUEST' && mobccsspecialmrtrequest.approvalResult !=null )}" path="recurrentAmt">
								<form:option value="NONE" label="Select" />
								<form:options items="${recurrentAmtList}" itemValue="parmValue" itemLabel="parmValue"  />
							</form:select> <form:errors path="recurrentAmt" cssClass="error" />
						</td>
					</tr>
					
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.mrtPattern"/>
							</div></td>
						<td width="25%"><form:input path="msisdnPattern" disabled="${user.channelCd=='CPA' || (mobccsspecialmrtrequest.approvalResult != 'REQUEST' && mobccsspecialmrtrequest.approvalResult !=null )}"
								maxlength="16" />
								<form:checkbox path="offerViolateInd" disabled="${user.channelCd=='CPA' || (mobccsspecialmrtrequest.approvalResult != 'REQUEST' && mobccsspecialmrtrequest.approvalResult !=null )}"/>
								<spring:message code="label.mobccsspecialmrtrequest.offerViolateInd"/>
								<form:errors path="msisdnPattern" cssClass="error"/>
						</td>						
					</tr>
					
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.remark"/>
							</div></td>	
						<td width="25%">
						<form:textarea path="remark" disabled="${user.channelCd=='CPA' || (mobccsspecialmrtrequest.approvalResult != 'REQUEST' && mobccsspecialmrtrequest.approvalResult !=null )}" id="remark" rows="5" cols="60" />
						<div style="clear:both"></div>
						<form:errors path="remark" cssClass="error"/>
						</td>
					</tr>
					
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.channel"/>
							</div></td>
						<td width="25%"><form:input path="channel" disabled="true" maxlength="16" />
						</td>						
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.requestor"/>
							</div></td>
						<td width="25%"><form:input path="requestor" disabled="true" maxlength="16" />
						</td>
												
					</tr>
				</table>
					<!-- end requestor table -->
			</td>
		</tr>
	
		<tr>
			<td bgcolor="#FFFFFF">
				<!--content-->
				<table width="100%" border="0" cellspacing="0" rules="none">
					<tr>
						<td class="table_title">Administrator Update</td>
					</tr>
				</table>
			</td>
		</tr>
					<!-- admin table -->
		<tr>
			<td bgcolor="#FFFFFF">
					<!-- admin table -->
				<table class="contenttext" width="100%" border="0" cellspacing="0">
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.resultStatus"/>
							</div></td>
						<td width="25%"><form:input path="approvalResult" disabled="true" maxlength="16" />
						</td>						
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.mrt"/>
							</div></td>
						<td width="25%">
						<form:input path="msisdn" maxlength="8" disabled="${user.channelCd!='CPA' || (mobccsspecialmrtrequest.approvalResult != 'APPROVED' && mobccsspecialmrtrequest.approvalResult != 'PROCEED')}" />
						<form:errors path="msisdn" cssClass="error"/>
						</td>												
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.mrtGrade"/>
							</div></td>
						<td width="25%"><form:select disabled="${user.channelCd!='CPA'  || (mobccsspecialmrtrequest.approvalResult != 'APPROVED' && mobccsspecialmrtrequest.approvalResult != 'PROCEED')}" path="msisdnlvl" >
						<form:option value="NONE" label="Select" />
						<form:options items="${msisdnlvlList}" itemValue="parmValue" itemLabel="parmValue" />
						</form:select>		
						<form:errors path="msisdnlvl" cssClass="error"/>	
						</td>						
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.reserveId"/>
							</div></td>
						<td width="25%">
						<form:input path="reserveId" maxlength="10" disabled="${user.channelCd!='CPA' || (mobccsspecialmrtrequest.approvalResult != 'APPROVED' && mobccsspecialmrtrequest.approvalResult != 'PROCEED')}"  />
						<form:errors path="reserveId" cssClass="error"/>	
						</td>									
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.resOperId"/>
							</div></td>
						<td width="25%">
							<form:input path="resOperId" maxlength="15" disabled="${user.channelCd!='CPA'|| (mobccsspecialmrtrequest.approvalResult != 'APPROVED' && mobccsspecialmrtrequest.approvalResult != 'PROCEED')}" />
							<form:errors path="resOperId" cssClass="error"/>
						</td>						
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.validDateTill"/>
							</div></td>
						<td width="25%">
						<form:input path="validDate" id="validDate" disabled="${user.channelCd!='CPA' || (mobccsspecialmrtrequest.approvalResult != 'APPROVED' && mobccsspecialmrtrequest.approvalResult != 'PROCEED')}" maxlength="10" readonly="true" />
						<form:errors path="validDate" cssClass="error"/>
						</td>
											
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.approvalSerial"/>
							</div></td>
						<td width="25%"><form:input path="approvalSerial" disabled="${user.channelCd!='CPA' || (mobccsspecialmrtrequest.approvalResult != 'APPROVED' && mobccsspecialmrtrequest.approvalResult != 'PROCEED')}" maxlength="16" />
						<form:errors path="approvalSerial" cssClass="error"/>
						</td>	
												
					</tr>
					<tr>
						<td width="25%">
							<div align="right">
								<spring:message code="label.mobccsspecialmrtrequest.adminRemark"/>
							</div></td>
						<td width="25%">
						<c:choose>
						<c:when test="${user.channelCd =='CPA'}">
							<form:textarea path="adminRemark" disabled="${(mobccsspecialmrtrequest.approvalResult != 'APPROVED' && mobccsspecialmrtrequest.approvalResult != 'PROCEED')}" id="remark" rows="5" cols="60" />
						</c:when>
						<c:when test="${user.category eq 'MANAGER' && param.from eq 'smr' }">
							<form:textarea path="adminRemark" disabled="${(mobccsspecialmrtrequest.approvalResult != 'REQUEST')}" id="remark" rows="5" cols="60" />
						</c:when>
						<c:otherwise>
						<form:textarea path="adminRemark" disabled="true" id="remark" rows="5" cols="60" />
						</c:otherwise>
						</c:choose>
						<form:errors path="adminRemark" cssClass="error"/>
						</td>						
					</tr>
						<tr>
						<td width="25%">
							<div align="right">
							</div></td>
						<td width="25%">
						<form:errors path="approvalResult" cssClass="error"/>
						</td>						
					</tr>
					<tr height="30px">
					</tr>
					<tr>
						<td>
						</td>
						<td>
							
					<%-- 	<c:if test="${param.success}">
							<h4>Update Successfully ${param.updateDate}</h4>
						</c:if> --%>
						<div align="right">
						
						<c:choose>
						<c:when test="${user.channelCd!='CPA'}">
							<c:if test="${empty param.requestId || (mobccsspecialmrtrequest.approvalResult=='REQUEST' && mobccsspecialmrtrequest.requestor eq user.username)}">
								<c:if test="${param.from eq 'rsv' }">
									<input type="button" id="saveButton" value="Save">
								</c:if>
							</c:if>
					
							<c:if test="${mobccsspecialmrtrequest.approvalResult=='REQUEST' }">
								<c:if test="${mobccsspecialmrtrequest.requestor eq user.username}">
									<c:if test="${param.from eq 'rsv' }">
										<input type="button" id="cancelButton" value="Cancel">
									</c:if>
								</c:if>
								<c:if test="${user.category eq 'MANAGER'}">
									<c:if test="${param.from eq 'smr' }">
										<input type="button" id="approveButton" value="Approve">
										<input type="button" id="rejectButton" value="Reject">
									</c:if>
								</c:if>
							</c:if>	
									
						</c:when>
						<c:otherwise>
							<c:if test="${mobccsspecialmrtrequest.approvalResult=='APPROVED'}">
								<input type="button" id="rejectButton" value="Reject">
							</c:if>
							<c:if test="${mobccsspecialmrtrequest.approvalResult=='APPROVED' || mobccsspecialmrtrequest.approvalResult=='PROCEED'}">
								<input type="button" id="updateButton" value="Update">
							</c:if>						
						</c:otherwise>
						</c:choose>
						<input type="button" id="exitButton" value="Exit">
					</div>				
					</td>
				</tr>
					
					
				</table>
				
				<!-- end admin table -->
			</td>
		</tr>
		
			
</table>

<c:if test="${param.success}">
		<div align="right">
			<h4 class="success">Update Successfully on ${param.updateDate}</h4>
		</div>
</c:if>
<form:hidden path="actionType"/>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>