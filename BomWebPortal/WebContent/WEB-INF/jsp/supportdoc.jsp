<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>


<script type="text/javascript">
function changeCollectMethod() {
	switch ($("input[name=collectMethod]").val()) {
	case "P":
		$(".requiredProof tbody .note").text("");
		$(".requiredProof input[type=checkbox]").show();
		$(".requiredProof select").each(changeWaiveReason);
		break;
	case "D":
	default:
		$(".requiredProof tbody .note").text("Optional");
		// reset collected checkbox status
		$(".requiredProof input[type=checkbox]").each(function() {
			$(this).attr("checked", $(this).data("init"));
		}).hide();
	}
}
function changeWaiveReason() {
	var $tdLast = $(this).parent().parent().find("td:last");
	switch ($("input[name=collectMethod]").val()) {
	case "P":
		if (this.selectedIndex == 0) {
			$tdLast.find(".note").text("");
			$tdLast.find("input[type=checkbox]").show();
		} else {
			$tdLast.find(".note").text("Waived");
			$tdLast.find("input[type=checkbox]").hide().attr("checked", $tdLast.find("input[type=checkbox]").data("init"));
		}
		break;
	case "D":
	default:
	}
}
function changeDisMode() {
	$(".eSignatureInfo .error").hide();
	initDisMode();
}
function initCollectMethod() {
	changeCollectMethod(); 
}
function initDisMode() {
	switch ($("input[name=disMode]:checked").val()) {
	case "E":
		$(".collectMethod").show();
		$(".eSignatureInfo select,.eSignatureInfo input").attr("disabled", false);
		$(".collectMethod").text("Digital");
		$("input[name=collectMethod]").val("D");
		break;
	case "P":    //add a dialog box to confirm distribution mode selection 
	             paperConfirmation();  
	default:
		$(".eSignatureInfo input").val("");
		$(".eSignatureInfo select").each(function() { this.selectedIndex = 0; });
		$(".eSignatureInfo select,.eSignatureInfo input").attr("disabled", true);
		$(".collectMethod").text("Paper");
		$("input[name=collectMethod]").val("P");
	}
	changeCollectMethod();
	$(".requiredProof tbody tr").each(function() {
		if ($(this).find("input[name$=docType]").val() == "M010") {
			if ($("input[name=disMode]:checked").val() == "E") {
				$(this).show();
			} else {
				$(this).hide();
			}
			return false;
		}
	});
}
function initCollectedInd() {
	$("input[name$=collectedInd]").filter("[type=checkbox]").each(function() {
		$(this).data("init", $(this).is(":checked"));
	});
}
$(document).ready(function() {
	// now collectMethod is 
	initDisMode();
	initCollectMethod();
	initCollectedInd();
	$(".requiredProof select").each(changeWaiveReason).change(changeWaiveReason);
	$("input[name=disMode]").click(changeDisMode);
	$("#supportDocForm").submit(function(e) {
		if ($(".error:visible").length > 0) {
			e.preventDefault();
			return false;
		}
		//showLoading();
	});
	$(".continueBtn").click(function() {
		
		realFormSubmit();
		//$("#supportDocForm").submit();
	});
});


function realFormSubmit(){
	var iGuard = ${iGuardUAD};
	$(".error").hide();
	// disMode
	switch ($("input[name=disMode]:checked").val()) {
	case "E":
		$("input[name=esigEmailAddr]").val($.trim($("input[name=esigEmailAddr]").val()));
		if ($("input[name=esigEmailAddr]").val().length == 0) {
			$(".error_esigEmailAddr").text("Require Email").show();
			
			//to add a dialog box to confirm email vacancy
			emailVacancyConfirmation();
			} 
		else if (!(/^\S+@\S+$/).test($("input[name=esigEmailAddr]").val())) {
			$(".error_esigEmailAddr").text("Invalid Email format").show();
		}else{
			$("#supportDocForm").submit();
			//document.getElementById("supportDocForm").submit();
		}
		
		break;
	case "P":
		if (iGuard == true){
			iGuardUADCheckBox()
		}else{
			$("#supportDocForm").submit();
		}
		//document.getElementById("supportDocForm").submit();
	default:
		
	}

}

// to confirm user's choice on paper mode  modified on Sept 25
function paperConfirmation(){
	if("#ChoosePaper:radio"){
	    		$("<p>Are you sure to use paper mode?</p>").dialog({
			resizable: false,
			height:"200",
	        width:"400",
			title:'Distribution Mode Selection',
		    modal:true,
		    buttons:{
		    "Yes":function(){
		    	$(this).dialog("close");
		    	
		    },
		    "No":function(){
		    	//change the choice of radiobutton
		    	document.getElementById("ChooseDigital").checked=true;
	            document.getElementById("ChoosePaper").checked=false;
	    		
	            //reset the digital configuration
	            $("input[name=disMode]:checked").val("E");
	            $("input[name=collectMethod]").val("D");
	            
	            //1.changeCollectMethod            
	    		changeCollectMethod();
	            //2.changeDisMode
	            changeDisMode();            
	            //3.changeCollectInd
	            initCollectedInd();
		    	$(this).dialog("close");
		    }
		           }
		    });
	}
	}

//to prompt a dialog box to confirm blank email address
function emailVacancyConfirmation(){
	$("<p>Are you sure no email address?</p>").dialog({
		resizable: false,
		height:"200",
        width:"400",
		title:'Confirm Your Email Address',
	    modal:true,
	    buttons:{
	    "Yes":function(){
	    	$(this).dialog("close");
	    	//forward to the next page
	    	showLoading();
            document.getElementById("supportDocForm").submit();
            //$("#supportDocForm").submit();
	    },
	    "No":function(){
	    	$(this).dialog("close");
	    }
	           }
	    });
	
}

function iGuardUADCheckBox(){
		$("<p>1)Uncheck i-GUARD Phone & Tablet Accidental Damage Protection Plan (3) - MSP0025722 in Optional VAS Selection <br</p>").dialog({
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
}
</script>

<style type="text/css">
.wrapContent { display: inline-block; width: 100%; padding: 10px 0 }
.supportDocForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-top: 21px; margin-left: 1px }
.supportDocForm h3 { margin: 0; font-size: medium; color: white; background-color: #6CA9F5; padding: 5px 10px; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.supportDocForm legend { color: #7F7F7F }
.supportDocForm .content { margin-left: 2em }
.supportDocForm .half { margin: 3px 0; width: 45%; display: inline-block; float: left }
.supportDocForm table { border-width: 1px; border-spacing: 0px; border-color: #7F7F7F; border-collapse: collapse }
.supportDocForm table th { border-width: 1px; padding: 3px; border-style: inset; border-color: #7F7F7F; background-color: #6CA9F5; color: white }
.supportDocForm table td { border-width: 1px; padding: 3px; border-style: inset; border-color: #7F7F7F; text-align: center }
.supportDocForm .springboardForms { display: block }
.supportDocForm .requiredProof { display: block }
.supportDocForm .requiredProof .note { color: #7F7F7F }
.supportDocForm .eSignatureInfo .label { vertical-align: top; float: left; margin-top: 5px }
.supportDocForm .eSignatureInfo .input { display: inline; float: left }
.supportDocForm .buttons { padding-top: 1em; text-align: right }
.clear2 { clear: both }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobsupportdoc" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>

<%@ page import="com.bomwebportal.dto.OrderDTO" %>
<%
String orderStatus = "INITIAL";
OrderDTO orderDto = (OrderDTO) request.getSession().getAttribute("orderDtoOriginal");
if (orderDto != null) {
	orderStatus = orderDto.getOrderStatus();
}
String appMode = (String)request.getSession().getAttribute("appMode");
boolean allowEdit = true;
if (!orderStatus.equals("INITIAL") && !orderStatus.equals("REJECTED") && ("directsales".equals(appMode))) {
	allowEdit = false;
}
%>

<c:if test="${param.conciergeSignPresent == 'false'}">
<h2 class="contenttext_red">Concierge sign is required</h2>
</c:if>

<% if (!allowEdit) {%>
	<table width="100%"  class="tablegrey" background="images/background2.jpg">
			<tr>
	   			<td class="contenttextgreen" colspan=3>
					<div class="buttonmenubox_L" id="buttonArea">
						<font>Not allow to change Support Docs : <a href="summary.html?action=AMEND" class="nextbutton3">Proceed and save changes</a>
						</font>
					</div>
				</td>   	 
	   		</tr>
	</table>
<%} %>

<div class="supportDocForm contenttext">
<form:form action="supportdoc.html?sbuid=${param.sbuid}" method="post" id="supportDocForm">

<h3>Springboard Form Generation</h3>
<div class="wrapContent">
	<div class="content">
		<div class="springboardForms">
			<div class="half">
			<c:forEach items="${command.generateSpringboardForms}" var="form" begin="0" end="${fn:length(command.generateSpringboardForms) / 2 - 1}">
				<input type="checkbox" disabled="disabled"<c:if test="${form.required}"> checked="checked"</c:if>>
				<c:out value="${form.springboardForm.label}"/>
				<div class="clear2"></div>
			</c:forEach>
			</div>
			<div class="half">
			<c:forEach items="${command.generateSpringboardForms}" var="form" begin="${fn:length(command.generateSpringboardForms) / 2}">
				<input type="checkbox" disabled="disabled"<c:if test="${form.required}"> checked="checked"</c:if>>
				<c:out value="${form.springboardForm.label}"/>
				<div class="clear2"></div>
			</c:forEach>
			</div>
		</div>
	</div>
</div>
<div class="clear2"></div>

<h3>Distribution Mode</h3>
<div class="wrapContent">
	<div class="content">
		<c:forEach items="${disModes}" var="disMode">
		<c:choose>
		<c:when test="${disMode == 'E'}">
	    

		<form:radiobutton id="ChooseDigital" path="disMode" label="Digital Signature" value="${disMode}"/>
		<div class="clear2"></div>
		<div class="eSignatureInfo">
			<div class="half">
				<span class="label" style="margin-left: 40px">Email:</span> 
				<div class="input">
					<%if (allowEdit) {%>
						<form:input path="esigEmailAddr" maxlength="64" cssStyle="width:20em"/>
					<%}else{%>
						<form:input path="esigEmailAddr" maxlength="64" cssStyle="width:20em" readonly="true"/>
					<%}%>
					<div class="clear2"></div>
					<span class="error error_esigEmailAddr" style="display:none"></span>
					<form:errors path="esigEmailAddr" cssClass="error"/>
				</div>
			</div>
			<div class="half">
				<%if (allowEdit) {%>
				<span class="label">Language:</span>			
				<div class="input">

					<form:select path="esigEmailLang">
						<form:option label="Traditional Chinese" value="CHN"/>
						<form:option label="English" value="ENG"/>
					</form:select>
					<div class="clear2"></div>
					<form:errors path="esigEmailLang" cssClass="error"/>
				</div>
				<% } else { %>
					<span class="label">Language: ${command.esigEmailLang}</span>
				<% } %>
			</div>
		</div>
		<div class="clear2"></div>
		</c:when>
		<c:when test="${(disMode == 'P') && (salesUserDTO.channelId != '10') && (salesUserDTO.channelId != '11')}">
		
		
		<form:radiobutton id="ChoosePaper" path="disMode" label="Paper" value="${disMode}"/>
		<div class="clear2"></div>
		</c:when>
		</c:choose>
		</c:forEach>
	</div>
</div>
	
<h3>Required Supporting Document</h3>
<div class="wrapContent">
	<div class="content">
		<div class="collecteMethods">
			<span style="padding-left: 13px">Collect method:</span>
			<span class="collectMethod"></span>
			<form:hidden path="collectMethod"/>
			<form:errors path="collectMethod" cssClass="error"/>
		</div>
		<div class="clear2"></div>
		<table class="requiredProof">
		<colgroup style="width:25%"></colgroup>
		<colgroup style="width:50%"></colgroup>
		<colgroup style="width:25%"></colgroup>
		<thead>
		<tr>
			<th>Supporting Doc Type</th>
			<th>Waive Reason</th>
			<th>Collected</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${command.allOrdDocAssgnDTOs}" var="dto" varStatus="status">
		<tr>
			<td>
				<c:out value="${dto.docName}"/>
				<form:hidden path="allOrdDocAssgnDTOs[${status.index}].docType"/>
			</td>
			<td>
				
				<c:choose>
				<c:when test="${not empty waiveReasons[dto.docTypeMob]}">
				<%if (allowEdit) {%>
				<form:select path="allOrdDocAssgnDTOs[${status.index}].waiveReason">
					<form:option value="" label="Select..."/>
					<form:options items="${waiveReasons[dto.docTypeMob]}" itemValue="waiveReason" itemLabel="waiveDesc"/>
				</form:select>
				<% } else { %>
				<form:select path="allOrdDocAssgnDTOs[${status.index}].waiveReason" disabled="true">
					<form:option value="" label="Select..."/>
					<form:options items="${waiveReasons[dto.docTypeMob]}" itemValue="waiveReason" itemLabel="waiveDesc"/>
				</form:select>
				<% } %>
				</c:when>
				<c:otherwise>
				N/A
				<form:hidden path="allOrdDocAssgnDTOs[${status.index}].waiveReason"/>
				</c:otherwise>
				</c:choose>
			</td>
			<td>
				<span class="note"></span>
				<form:checkbox path="allOrdDocAssgnDTOs[${status.index}].collectedInd" value="Y"/>
			</td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>
	<br/>
	<c:if test="${salesUserDTO.channelId == '10' || salesUserDTO.channelId == '11'}">
		<span style="padding-left: 25px">Missing Doc Reason:</span>
		<c:if test="${not empty missingDocReason}">
			<form:select path="dsMissingDocReason">
				<form:option value="" label="Select..."/>
				<form:options items="${missingDocReason}"/>
			</form:select>
		</c:if>
	</c:if>
</div>

<div class="clear2"></div>
<h3>Manual AF</h3>
<div class="wrapContent">
	<div class="content">
		Manual AF # :
		<%if (allowEdit) {%>
			<form:input path="manualAfNo" maxlength="13" cssStyle="width:20em"/>
		<%}else{%>
			<form:input path="manualAfNo" maxlength="13" cssStyle="width:20em" readonly="true"/>
		<%}%>
	</div>
</div>



<% if (allowEdit) {%>
<div class="buttonmenubox_R">
	<input type="hidden" name="currentView" value="supportdoc"/>
	<a href="#" class="nextbutton3 continueBtn">continue</a>
</div>
<% } %>
</form:form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>