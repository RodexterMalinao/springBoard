<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<%@ page import="com.bomwebportal.dto.MobileSimInfoDTO,
					java.util.*
					"
%>

<%
String workStatus = (String) request.getAttribute("workStatus");
%>

<%
	MobileSimInfoDTO mobileSimInfoDTO = (MobileSimInfoDTO)request.getAttribute("MobileSimInfo");
	String baskettypeSession = (String)request.getSession().getAttribute("baskettypeSession");
	String selectedBasketId = (String) request.getSession().getAttribute("basketSession");
	if (baskettypeSession==null) 
		baskettypeSession = "";	
	    //System.out.println("baskettypeSession:" +baskettypeSession);
	
%>
<link rel="stylesheet" href="css/jquery-ui-1.10.3.css" />
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui-1.10.3.js"></script>

<script type="text/javascript">

	function initialManualInput(){
	if ($("input[id=manualInputBool]").is(":checked")) {
		$("#refCentreCd").removeAttr('readonly');
		$("#refTeamCd").removeAttr('readonly');
		$("#refSalesName").removeAttr('readonly');		
		$("#refreshButton").attr('disabled','disabled');}
	}

	function clearRefSalesInfo(){
		$("input[name=refSalesId]").val("");
		$("#refSalesName").val("");		
		$("#refCentreCd").val("");
		$("#refTeamCd").val("");
			
	}
	
	function formSubmit(actionType) {
	
		//modify by eliot 20110622
		document.mobileSimForm.actionType.value = actionType;
		//document.mobileSimForm.submit();
		$("#mobileSimForm").submit();
	}
	
	function clearSalesName() {
		document.mobileSimForm.salesName.value = "";
	}
	
	function clearSimInfo() {
		$("#simCharge").val(0);
		$("#simWaivable").val(false);
		$("#simWaiveReason").prop('disabled', 'disabled');
		$("#simChargeDisplay").html("-");
		$("#hwInvStatusMsg").html("-");
	}
	
	function checkSimInfo(value) {
		$.ajax({
			url : "siminfo.html?type=iccid&a="+value+"&b=<%=selectedBasketId%>&c=${appDate}", //idck
			cache: false,
			dataType : 'json',
			timeout : 5000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				clearSimInfo();
			},
			success : function(data) {
				var result = jQuery.parseJSON(data.gson);
				if (result != null) {
					$("#simCharge").val(result.charge);
					if (result.hwInvStatus == 0) $("#hwInvStatusMsg").html("<spring:message code="iccidStatus.INITIAL" text="" />");
					else if (result.hwInvStatus == 2) $("#hwInvStatusMsg").html("<spring:message code="iccidStatus.NORMAL" text="" />");
					else if (result.hwInvStatus == 3) $("#hwInvStatusMsg").html("<spring:message code="iccidStatus.LOCK" text="" />");
					else if (result.hwInvStatus == 4) $("#hwInvStatusMsg").html("<spring:message code="iccidStatus.SOLD" text="" />");
					else if (result.hwInvStatus == 5) $("#hwInvStatusMsg").html("<spring:message code="iccidStatus.FROZEN" text="" />");
					else if (result.hwInvStatus == 7) $("#hwInvStatusMsg").html("<spring:message code="iccidStatus.ABANDON" text="" />");
					else if (result.hwInvStatus == 11) $("#hwInvStatusMsg").html("<spring:message code="iccidStatus.INTRANSIT" text="" />");
					else $("#hwInvStatusMsg").html("-");
				}
			}
		});
	}
	
	function checkIguardUADSubscribed(){
		var iGuardUAD = ${iGuardUAD};
		var isAoInd = "N";
		var stockAssignList = ${mobileSimInfo.stockAssgnListJson}
		
		$.each(stockAssignList,function(index,value){
			 if(value.itemType=="01" && iGuardUAD==true && $(".stockAssgnList"+ index +"_aoInd").is(':checked')){
				 isAoInd = "Y";
			 }
		});
		
		if ("Y"==isAoInd){
			$("<p>1)Uncheck i-GUARD Phone & Tablet Accidental Damage Protection Plan (3) - MSP0025722 in Optional VAS Selection <br></p>").dialog({
			resizable: false,
			height:"200",
	 		    	width:"650",
			title:'i-GUARD Repair Plan Activation Card Subscription',
		    modal:true,
			    buttons:{
			    	"OK":function(){
			    		$(this).dialog("close");
			    		
	    			}
			     }
			});
		}else{
			formSubmit('SUBMIT');
		}
	}

$(document).ready(function() {
	initialManualInput();
	var actionType   = "${mobileSimInfo.actionType}";
		
	<%if (bomsalesuser.getChannelId() == 10 || bomsalesuser.getChannelId() == 11){ %>
	<c:forEach items="${mobileSimInfo.stockAssgnList}" var="saList" varStatus="saRow">
	
	var availableTags_${saRow.index} = [
	    "NONE"
	    <c:if test="${not empty saList.itemSerialList}">
	    <c:forEach var="serialNum" items="${saList.itemSerialList}">
		, "${serialNum}"
	    </c:forEach></c:if>
	];
	
	$( "#itemSerialList_${saRow.index}" ).autocomplete({
    	source: availableTags_${saRow.index}
	});
	</c:forEach>
	
	var availableSim = [
		"NONE"
		<c:if test="${not empty mobileSimInfo.simList}">
		<c:forEach var="simNum" items="${mobileSimInfo.simList}">
		, "${simNum}"
		</c:forEach>
		</c:if>
	];
	$( "#simList" ).autocomplete({
		source: availableSim
	});
	<%}%>
	
	
	$("#iccid, #simList").focusout(function(){
		var value = $(this).val();
	    if ( value.length == 19) {
	    	checkSimInfo(value);
	    } else {
	    	clearSimInfo();
	    }
	});
	
	var value = $("[name=iccid]").val();
    if ( value.length == 19) {
    	checkSimInfo(value);
    } else {
    	clearSimInfo();
    }
    
	$("input[id=manualInputBool]").change(function(){
		if ($(this).is(":checked")) {
			$("#refCentreCd").removeAttr('readonly');
			$("#refTeamCd").removeAttr('readonly');
			$("#refSalesName").removeAttr('readonly');
			$("#refreshButton").attr('disabled','disabled');
			
		}else{
			$("#refCentreCd").attr('readonly',true);
			$("#refTeamCd").attr('readonly',true);
			$("#refSalesName").attr('readonly',true);
			$("#refreshButton").removeAttr('disabled');
			
		}
		clearRefSalesInfo();
	});
	
});
</script>


<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobilesiminfo" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>
<br> 

<form:form method="POST" name="mobileSimForm" id="mobileSimForm" commandName="mobileSimInfo">
<table width="100%"  class="tablegrey" >
	<tr> 
		<td  bgcolor="#FFFFFF" > 
		  	<table width="100%"  border="0" cellspacing="1" >
				<tr><td class="table_title">Mobile &amp; SIM Information</td></tr>
          	</table>
          
          	<table width="100%" border="0" cellspacing="5" cellpadding="0" class="contenttext"
				background="images/background2.jpg">
				<tr><td colspan="6"><div></div></td></tr>
          		<tr>
          			<th><div align="center">Advanced Order</div></th>
          			<th><div align="center">Item Code</div></th>
          			<th><div align="left">Item Description</div></th>
          			<th><div align="center">SIM Number / Serial Number</div></th>
          			<c:if test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
	          			<th><div align="center">Sales Memo Number 1</div></th>
	          			<th><div align="center">Sales Memo Number 2</div></th>
	          			<th><div align="center">SM Issued</div></th>
          			</c:if>
          		</tr>
          		<tr>
          			<td><div align="center">-</div></td>
          			<td><div align="center">${mobileSimInfo.selectedSimItemCd}</div></td>
          			<td><div align="left">SIM <i>(HW INV Status: <span id="hwInvStatusMsg">-</span>)</i></div></td>
          			
          			<c:choose>
					<c:when test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
						<td><div align="center"><form:input path="iccid" id="simList" /><form:hidden path="simCharge" /></div></td>
					</c:when>
					<c:otherwise>
						<td><div align="center"><span class="contenttext_red">*</span><form:input path="iccid" maxlength="19" autocomplete="off"/><form:hidden path="simCharge" /></div></td>
					</c:otherwise>
					</c:choose>
					<c:if test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
	          			<td><div align="center"><form:input path="simSalesMemoNum" /></div></td>
	          			<td><div align="center">-</div></td>
	          			<td><div align="center">-<!-- <form:checkbox path="simSalesMemoInd" value="Y" /> --></div></td>
          			</c:if>
          		</tr>
          		<tr>
					<td colspan='7'><div align="center"><form:errors path="iccid" cssClass="error" /><form:errors path="simBrandType" cssClass="error" /></div></td>
				</tr>
				<c:forEach items="${mobileSimInfo.stockAssgnList}" var="saList" varStatus="saRow">
					<tr>
						<td><div align="center">
							<form:checkbox path="stockAssgnList[${saRow.index}].aoInd" cssClass="stockAssgnList${saRow.index}_aoInd" value="Y" /></div></td>
						<td><div align="center"><form:label path="stockAssgnList[${saRow.index}].itemCode" >${saList.itemCode}</form:label></div></td>
						<td><div align="left"><form:label path="stockAssgnList[${saRow.index}].itemDesc" >${saList.itemDesc}</form:label></div></td>
						<td>
							<div align="center">
								<c:if test="${bomsalesuser.channelId != 10 && bomsalesuser.channelId != 11 && saList.itemType == '01'}">
									<span class="contenttext_red">*</span>
								</c:if>
								<form:input path="stockAssgnList[${saRow.index}].itemSerial" id="itemSerialList_${saRow.index}" />
							</div>
						</td>
						<c:if test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
							<td><div align="center"><form:input path="stockAssgnList[${saRow.index}].salesMemoNum" /></div></td>
							<td><div align="center"><form:input path="stockAssgnList[${saRow.index}].salesMemoNum2" /></div></td>
							<td><div align="center"><form:checkbox path="stockAssgnList[${saRow.index}].salesMemoInd" value="Y" /></div></td>
						</c:if>
					</tr>
					<tr>
						<td colspan=7 align="center"><form:errors path="stockAssgnList[${saRow.index}].itemSerial" cssClass="error" /></td>
					</tr>
					<tr>
						<td colspan='7'><div align="center"><form:errors path="stockAssgnList[${saRow.index}].eagleValidate" cssClass="error" /></div></td>
					</tr>
				</c:forEach>
			</table>

          	<table width="100%"  border="0" cellspacing="1" >
				<tr><td class="table_title">Sales Commission Information</td></tr>
			</table>
			
			<table width="100%" border="0" cellspacing="1" class="contenttext"
				background="images/background2.jpg">
				<tr>
					<td>
					<div align="right"></div>
					</td>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td width="25%">
					<div align="right">Sales Type<span class="contenttext_red">*</span></div>
					</td>
					<td colspan="3"><form:select path="salesType">
						<form:option value="S" label="Salesman ID" />	
						<form:option value="C" label="CMRID" />			
					</form:select>
					<form:errors path="salesType" cssClass="error" /></td>
					
					<c:if test="${bomsalesuser.channelId==1 }">
						<td>
							<div align="left"><spring:message code="label.mobccsstaffinfo.refSalesId1"/>:</div>
						</td>
						<td>
							<% if ("recall".equalsIgnoreCase(workStatus)) { %>
								<form:input id="refSalesId" path="refSalesId" maxlength="8" disabled="true"/>
							
							<% } else { %>
								<form:input id="refSalesId" path="refSalesId" maxlength="8" disabled="false"/>
								<form:checkbox id="manualInputBool" path="manualInputBool" /> Manual Input
							<% } %>
							<form:errors path="refSalesId" cssClass="error" />
						</td>
					</c:if>
				</tr>
				<tr>
					<td width="25%">
					<div align="right">Sales Code<span class="contenttext_red">*</span></div>
					</td>
					<td colspan="3">
					<c:if test="${not empty mobileSimInfo.orderId}" >
						<form:input path="salesCd" maxlength="10" id="salesCd" onblur="clearSalesName()" readonly="true"/>
					</c:if>
					<c:if test="${empty mobileSimInfo.orderId}" >
						<form:input path="salesCd" maxlength="10" id="salesCd" onblur="clearSalesName()"/>
					</c:if>
					<form:errors path="salesCd" cssClass="error" /></td>
					
					<c:if test="${bomsalesuser.channelId==1 }">
						<td>
							<div align="left"><spring:message code="label.mobccsstaffinfo.refSalesName1"/>:</div>
						</td>
						<td>
							<% if ("recall".equalsIgnoreCase(workStatus)) { %>
								<form:input id="refSalesName" path="refSalesName" readonly="true"/>
								<input id="refreshButton" type="submit" value="Refresh" maxlength="80" onClick="javascript:formSubmit('REFRESH') " disabled="disabled"/>
							<% } else { %>
								<form:input id="refSalesName" path="refSalesName" readonly="true"/>
								<input id="refreshButton" type="submit" value="Refresh" maxlength="80" onClick="javascript:formSubmit('REFRESH') " />
							<% } %>
							<form:errors path="refSalesName" cssClass="error" />
						</td>
					</c:if>
				</tr>
				<!-- add by eliot 20110616 -->
				<tr>
					<td width="25%">
					<div align="right">Sales Name<span class="contenttext_red">*</span></div>
					</td>
					<td colspan="3"><form:input path="salesName" id="salesName" maxlength="10" readonly="true"/>					
					<input type="submit" value="Refresh" onClick="javascript:formSubmit('REFRESH')"/>
					<form:errors path="salesName" cssClass="error" />										
					</td>
					<c:if test="${bomsalesuser.channelId==1 }">
						<td>
							<div align="left"><spring:message code="label.mobccsstaffinfo.refSalesCentre"/>:</div>
						</td>
						<td>
							<form:input id="refCentreCd" path="refSalesCentre" maxlength="40" readonly="true"/>
							<form:errors path="refSalesCentre" cssClass="error" />
						</td>
					</c:if>
				</tr>				
				<c:if test="${bomsalesuser.channelId==1 }">
					<tr>
						<td width="25%">
						</td>
						<td colspan="3">
						</td>
						<td>
							<div align="left"><spring:message code="label.mobccsstaffinfo.refSalesTeam"/>:</div>
						</td>
						<td>
							<form:input id="refTeamCd" path="refSalesTeam" maxlength="40" readonly="true"/>
							<form:errors path="refSalesTeam" cssClass="error" />
						</td>
					</tr>
				</c:if>


				<c:choose>
				<c:when test="${bomsalesuser.channelId == 10 || bomsalesuser.channelId == 11}">
					<tr>
						<td width="25%">
						<div align="right">Sales Contact Number<span class="contenttext_red">*</span></div>
						</td>
						<td colspan="3"><form:input path="salesContactNum" id="salesContactNum" maxlength="12" readonly="false"/>
						<form:errors path="salesContactNum" cssClass="error" />								
						</td>
					</tr>
					<tr>
						<td width="25%">
						<div align="right">Sales Location<span class="contenttext_red">*</span></div>
						</td>
						<td colspan="3">
						<form:select path="salesLocation">
							<form:option value="" label="----Please Select----"/>
						    <form:options items="${dsLocationList}" />
						</form:select>
						<form:errors path="salesLocation" cssClass="error" />
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr><td><form:hidden path="salesContactNum" /></td></tr>
				</c:otherwise>
				</c:choose>
				<tr>
					<td>
					<div align="right"></div>
					</td>
					<td colspan="3">&nbsp;</td>
				</tr>
				
			</table>	
                
        </td>
        </tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0">
				<tr>

					<td>
					<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3" onClick="javascript:checkIguardUADSubscribed('SUBMIT');">continue</a></div>
					</td>
				</tr>
			</table>
	<input type="hidden" name="currentView" value="mobilesiminfo" />
	<input type="hidden" name="actionType" id="actionType"/>	
	<form:hidden path="bomWebItemCd"/>	
	<form:hidden path="shopCd"/>				
</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>