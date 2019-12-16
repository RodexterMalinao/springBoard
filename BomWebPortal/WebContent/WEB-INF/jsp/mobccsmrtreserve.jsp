<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script language="javascript">
	document.onkeypress = processKey;
	function processKey(e) {
		if (null == e)
			e = window.event;
		if (e.keyCode == 13) {
			search();
		}
	}

	function formSubmit(actType) {
		$("#actionType").val(actType);
		document.mrtReserveForm.submit();
	}
	
	$(document).ready(function(){
		
		$("#specialMRT").load("mobccsspecialmrtreserve.html");
		
	});
	
</script>

<form:form name="mrtReserveForm" method="POST"
	commandName="mrtReserve">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<form:errors path="errorMsg" cssClass="error" />
	<form:errors path="mrtPool" cssClass="error" />
	
	<c:if test='${actionType == "SUCCESS"}'>
		This record is saved successfully!
	</c:if>
	<c:if test='${actionType == "UNSUCCESS"}'>
		<font color="#FF0000">
			This record is NOT saved!
		</font>
	</c:if>
	<c:if test='${actionType == "MORE3"}'>
		<font color="#FF0000">
			3 reserved MRT exists!
		</font>
	</c:if>
	<c:if test='${actionType == "CHOOSEOTHER"}'>
		<font color="#FF0000">
			Number has been changed status and can't reserve anymore, please choose another number.
		</font>
	</c:if>
	
	<!-- ******************************* TEMP TABLE ************************** -->
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF" width="100%" colspan="4"><!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Reserve Normal MRT</td>
					</tr>
				</table>
				<c:choose>
					<c:when test = '${empty reserveMRTList}'>
						<%-- ***************************   NO RECORD FOUND   ****************************** --%>
						<table width="100%" class="tablegrey">
							<tr>
								<td bgcolor="#FFFFFF"><!--content-->
								<table width="100%" border="0" cellspacing="1" rules="none">
									<tr>
										<td>No Record Found.</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
					</c:when>
					<c:otherwise>
						<%-- ***************************   TEMP RESULT FOUND   ****************************** --%>
						<table width="100%" class="contenttext">
							<tr>
								<td>
								<div style="overflow:scroll; height:110px;">
								<table width="100%" border="1" cellspacing="0" class="contenttext"
									bgcolor="#FFFFFF">
		
									<tr class="contenttextgreen">
		
										<td class="table_subtitle_blue" width="5%">MRT</td>
										<td class="table_subtitle_blue" width="10%">Reserve Date</td>
										<td class="table_subtitle_blue" width="85%">Remaining Time</td>
		
										<c:forEach items="${reserveMRTList}" var="rMRT" >
											<tr>
												<td width="20%">
													&nbsp;${fn:toUpperCase(rMRT.dReservedMrt)}(${fn:toUpperCase(rMRT.msisdnlvl)})(<pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${rMRT.numType}"/>)
												</td>

												<td width="40%">
													&nbsp;${fn:toUpperCase(rMRT.reserveDate)}
												</td>
												
												<td width="40%">
													&nbsp;${fn:toUpperCase(rMRT.remainingTime)}
												</td>
											</tr>
										</c:forEach>
									</tr>
								</table>
								</div>
								</td>
							</tr>
						</table>

					</c:otherwise>
				</c:choose>
				
				<table width="100%" border="0" cellspacing="0" class="contenttext"
									bgcolor="#FFFFFF">
							<tr>
								<td width="15%">
								<div align="left"><spring:message code="label.mobccsspecialmrtrequest.mrtPool"/>:</div>
								</td>
								<c:choose>
									<c:when test='${is3reserved}'>
										<td width="40%">
											<form:select path="mrtPool" disabled='true'>
												<form:option value="DISABLE" label="---" />
											</form:select>
										</td>
									</c:when>
									<c:otherwise>
										<td width="40%">
											<form:select path="mrtPool" disabled='false'>
												<form:option value="NONE" label="Select" />
												<c:forEach var="mob3GMrtList" items="${mob3GMrtList}">
													<option value="${mob3GMrtList[1]}">${mob3GMrtList[1]}(${mob3GMrtList[2]})(<pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${mob3GMrtList[3]}"/>)</option>
												</c:forEach>
											</form:select>
										</td>
									</c:otherwise>
								</c:choose>
								<td align="right">
									<div><a href="#" class="nextbutton" onClick="javascript:formSubmit('INSERT')">Reserve</a></div>
								</td>
								
							</tr>
						</table>
			</td>
		</tr>
		
	</table>
	
	<br>
		
	
	<input type="hidden" name="actionType" id="actionType" />

</form:form>
<br>

<div id="specialMRT"></div>

<br>

<table width="100%" border="0" cellspacing="0">
		<tr>
			<td align="right" width="100%">
			<div><a href="welcome.html" class="nextbutton">Exit</a></div>
			</td>
		</tr>
</table>




<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>