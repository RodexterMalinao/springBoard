<jsp:include page="/WEB-INF/jsp/header.jsp">
	<jsp:param name="enableOnloadJScript" value="Y" />
</jsp:include>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.imsautocomplete.js"></script>
<!-- <script type="text/javascript" charset="utf-8" src="js/inputInterceptor.js"></script> -->
<script type="text/javascript" charset="utf-8" src="js/inputInterceptorIms.js"></script>
<%@ include file="imsloadingpanel.jsp" %>

<script>
function formSubmit() {
	var valid = true;
	$(".error").each(function(i ,e){
		if ($(e).html().length >0 ) {
			valid = false;
		} 
	});
	if (!valid) { 
		alert("Please refer to the error shown in the page.");
	}else{
	//alert("prevSbNo==>" + document.getElementById('prevSbNo').value + "<" + "currSbNo==>" + document.getElementById('installAddress.serbdyno').value + "<");
	if (document.getElementById('prevSbNo').value == null ||
		document.getElementById('prevSbNo').value == "" ||
		document.getElementById('prevSbNo').value == $("[name='sbSelected']:checked").val()) {
			submitShowLoading();
			$(":input").attr("disabled", false);
			document.imsaddressForm.submit();
	} else {
		var answer = confirm("The data will be lost due to Address change, do you want to continue?");
		if (answer) {
			submitShowLoading();
			$(":input").attr("disabled", false);
			document.imsaddressForm.submit();
		}
	}
	}				
}
function keyUpOnUnitNo() {
	
	document.getElementById('installAddress.unitNo').value = document.getElementById('installAddress.unitNo').value.toUpperCase();
	
}

function keyUpOnFloorNo() {
	
	document.getElementById('installAddress.floorNo').value = document.getElementById('installAddress.floorNo').value.toUpperCase();
	
}

function blurOnUnitNo() {
	
	document.getElementById('installAddress.unitNo').value = document.getElementById('installAddress.unitNo').value.trim().toUpperCase();
	
}

function blurOnFloorNo() {

	document.getElementById('installAddress.floorNo').value = document.getElementById('installAddress.floorNo').value.trim().toUpperCase();
	
}

function keyDownOnFloorNo() {

	if (document.getElementById('installAddress.floorNo.errors') != null) {
		document.getElementById('installAddress.floorNo.errors').value = "";
	}
}

function clickOnFloorNo(){

	if (document.getElementById('installAddress.floorNo.errors') != null) {
    	document.getElementById('installAddress.floorNo.errors').style.visibility = "hidden";
    	document.getElementById('installAddress.floorNo.errors').hidden = true;
    	document.getElementById('installAddress.floorNo.errors').style.display = "none";
	}
}

</script>

<table width="100%" height="30">
	<tr>
		<td class="contenttextgreen" align="center">
		 	PCD Acquisition
		</td>
	</tr>
</table>

<link href="css/imsds.css" rel="stylesheet" type="text/css">
<%@ include file="/WEB-INF/jsp/imssubheader.jsp" %>
<br>

<form:form method="POST" name="imsaddressForm" commandName="addressinfo">
	<form:hidden path="otChrgType"/>
	<table width="100%" class="tablegrey">
	<tr>
		<td>
	  	
			<!-- register address block -->

			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF"">
				<tbody>
				   	<tr>
			   			<td class="table_title" colspan="2">Installation Address</td>
		       		</tr>
				</tbody>
			</table>
		
			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF"" style="padding-top: 20px;padding-bottom: 20px;"> 
				<tbody>
				   	<tr>
						<th style="width:20%;" align="left" valign="top">Installation Address:</th>
						<td style="width:50%;">
							<c:if test="${not empty ImsOrder.orderImg.installAddress.unitNo}">Flat ${ImsOrder.orderImg.installAddress.unitNo}, </c:if>
							<c:if test="${not empty ImsOrder.orderImg.installAddress.floorNo}">${ImsOrder.orderImg.installAddress.floorNo}/F, </c:if> 
							${ImsOrder.orderImg.installAddress.buildNo}, ${ImsOrder.orderImg.installAddress.distDesc} 
						</td> 
		       		</tr>
		        <tr>
				<th align="left">Flat/ Room:</th>
				<td><form:input maxlength="5" path="installAddress.unitNo" size="16%" onkeyup="keyUpOnUnitNo()" onblur="blurOnUnitNo()" cssClass="noSymbol flat"/>
				<form:errors path="installAddress.unitNo" cssClass="error" /> 
				</td>
				<th align="left">Floor:</th>
				<td><form:input maxlength="3" path="installAddress.floorNo" size="16%" onkeyup="keyUpOnFloorNo()" onfocus="keyDownOnFloorNo()" onblur="blurOnFloorNo()" cssClass="noSymbol" onclick="clickOnFloorNo()"/></td>
				<td><form:errors path="installAddress.floorNo" cssClass="error" /></td>
				</tr>
				<tr height="15px">
				<th/>
				<td colspan="2" ><span class="installAddress.unitNonoSymbolError error"></span></td>
				<td colspan="2" ><span class="installAddress.floorNonoSymbolError error"></span></td>
				</tr>
				</tbody>
			</table>
			 
			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF"" style="padding: 20px;">
				<tbody> 
					<tr>
						<td style="width:5%;"><form:radiobutton path="sbSelected" value="${addressinfo.installAddress.serbdyno}"/></td>
						<th align="left" valign="top" style="width:20%;">Address:</th> 
						<td style="width:50%;">
							<c:if test="${not empty addressinfo.installAddress.sbFlat}">Flat ${addressinfo.installAddress.sbFlat}, </c:if>
							<c:if test="${not empty addressinfo.installAddress.sbFloor}">${addressinfo.installAddress.sbFloor}/F, </c:if> 
							${addressinfo.installAddress.buildNo}, ${addressinfo.installAddress.distDesc} 
							<span style="color:red"><b>(selected SB)</b></span></td>       
					</tr>	       	 	
					<tr>
						<td style="width:5%;"></td>
						<th align="left" valign="top" style="width:20%;">Service Boundary:</th>
						<td style="width:50%;">${addressinfo.installAddress.serbdyno}</td> 
					</tr>
					<tr>
						<td></td>
						<th align="left">Housing Type:</th>
						<td><form:input path="hosType" readonly="" size="100%" /></td>
					</tr>
					<tr>
						<td></td>
						<th align="left">Estimated FTTH Earliest SRD:</th>
						<td><form:input path="ponSrd" readonly="true" size="100%" /></td>
					</tr>
					<tr id="vdslSRDRow" <c:if test= '${addressinfo.vectorSrd != "N/A"}'>style="display:none"</c:if> >
						<td></td>	
						<th align="left">Estimated VDSL Earliest SRD:</th>
						<td><form:input path="vdslSrd" readonly="true" size="100%" /></td>
					</tr>
					<tr id="vectorSRDRow" <c:if test= '${addressinfo.vectorSrd == "N/A"}'>style="display:none"</c:if> >
						<td></td>
						<th align="left">Estimated Vectoring Earliest SRD:</th>
						<td><form:input path="vectorSrd" readonly="true" size="100%" /></td>
					</tr>
					<tr>
						<td></td>
						<th align="left">Estimated ADSL Earliest SRD:</th>
						<td><form:input path="adslSrd" readonly="true" size="100%" /></td>
					</tr>
				<tr>
				<td></td>
				<th class="PONInstallation" align="left">PON Install Fee:</th>
				<td><form:input path="ponInstFee" readonly="true" size="100%" /></td>
			</tr>
			<tr id="vdslIFRow" <c:if test= '${addressinfo.vectorSrd != "N/A"}'>style="display:none"</c:if> >
				<td></td>
				<th class="VDSLInstallation" align="left">VDSL Install Fee:</th>
				<td><form:input path="vdslInstFee" readonly="true" size="100%" /></td>  
			</tr>
			<tr id="vectorIFRow" <c:if test= '${addressinfo.vectorSrd == "N/A"}'>style="display:none"</c:if> >
				<td></td>
				<th class="VectorInstallation" align="left">Vectoring Install Fee:</th>
				<td><form:input path="vectorInstFee" readonly="true" size="100%" /></td>
			</tr>
			<tr>
				<td></td>
				<th class="ADSLInstallation" align="left">ADSL Install Fee:</th>
				<td><form:input path="adslInstFee" readonly="true" size="100%" /></td>
			</tr>  
				</tbody>
			</table>

		<!--related SB-->
		<c:forEach items="${addressinfo.relatedSbList}" var="r" varStatus="status">
			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF"" style="padding: 20px;">
				<tbody> 
					<tr>
						<td style="width:5%;"><form:radiobutton path="sbSelected" value="${r.installAddress.serbdyno}"/></td>
						<th align="left" valign="top" style="width:20%;">Address:</th> 
						<td style="width:50%;">
							<c:if test="${not empty r.installAddress.sbFlat}">Flat ${addressinfo.installAddress.sbFlat}, </c:if>
							<c:if test="${not empty r.installAddress.sbFloor}">${r.installAddress.sbFloor}/F, </c:if> 
							${r.installAddress.buildNo}, ${r.installAddress.distDesc} 
							</td>       
					</tr>	       	 	
					<tr>
						<td style="width:5%;"></td>
						<th align="left" valign="top" style="width:20%;">Service Boundary:</th>
						<td style="width:50%;">${r.installAddress.serbdyno}</td> 
					</tr>
					<tr>
						<td></td>
						<th align="left">Housing Type:</th>
						<td><form:input path="relatedSbList[${status.index}].hosType" readonly="" size="100%" /></td>
					</tr>
					<tr>
						<td></td>
						<th align="left">Estimated FTTH Earliest SRD:</th>
						<td><form:input path="relatedSbList[${status.index}].ponSrd" readonly="true" size="100%" /></td>
					</tr>
					<tr>
						<td></td>	
						<th align="left">Estimated VDSL Earliest SRD:</th>
						<td><form:input path="relatedSbList[${status.index}].vdslSrd" readonly="true" size="100%" /></td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td></td> -->
<!-- 						<th align="left">Estimated Vectoring Earliest SRD:</th> -->
<%-- 						<td><form:input path="relatedSbList[${status.index}].vectorSrd" readonly="true" size="100%" /></td> --%>
<!-- 					</tr> -->
					<tr>
						<td></td>
						<th align="left">Estimated ADSL Earliest SRD:</th>
						<td><form:input path="relatedSbList[${status.index}].adslSrd" readonly="true" size="100%" /></td>
					</tr>
				<tr>
				<td></td>
				<th class="PONInstallation" align="left">PON Install Fee:</th>
				<td><form:input path="relatedSbList[${status.index}].ponInstFee" readonly="true" size="100%" /></td>
			</tr>
			<tr>
				<td></td>
				<th class="VDSLInstallation" align="left">VDSL Install Fee:</th>
				<td><form:input path="relatedSbList[${status.index}].vdslInstFee" readonly="true" size="100%" /></td>  
			</tr>
<!-- 			<tr> -->
<!-- 				<td></td> -->
<!-- 				<th class="VectorInstallation" align="left">Vectoring Install Fee:</th> -->
<%-- 				<td><form:input path="relatedSbList[${status.index}].vectorInstFee" readonly="true" size="100%" /></td> --%>
<!-- 			</tr> -->
			<tr>
				<td></td>
				<th class="ADSLInstallation" align="left">ADSL Install Fee:</th>
				<td><form:input path="relatedSbList[${status.index}].adslInstFee" readonly="true" size="100%" /></td>
			</tr>  
				</tbody>
			</table>
		</c:forEach>
		
		
		<!--related SB(end)-->


			<!-- button table  -->
			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
			<tr height="15px"></tr>
			<tr>
				<td align="right" colspan="6">
					<div>
						<a href="#" id="continue" class="nextbutton" onClick="javascript:formSubmit();" >Continue &gt;</a>
					</div> 
				</td>
			</tr>
			<tr height="15px"></tr>
			</table>
			<!-- end button table -->
			
			<input type="hidden" id="imsSearchFrom" value="" />
			<input type="hidden" name="currentView" value="addressinfo" />
			<form:hidden path="installAddress.serbdyno" />
			<form:hidden path="installAddress.blacklistInd" />
			<form:hidden path="installAddress.strCatCd" />
			<form:hidden path="blacklistCustInfo" />
			<form:hidden path="prevSbNo" />
			<form:hidden path="phInd" />
			<input type="hidden" id="blockageCode" value="N" />
			<input type="hidden" id="isError1" value="N" />
			<input type="hidden" id="isError2" value="N" />
		</td>
	</tr>
	</table>
</form:form>

<!---------------------------------------  end content ------------------------------------------->

<%@ include file="/WEB-INF/jsp/footer.jsp"%>