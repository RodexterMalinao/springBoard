<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="loadingpanel.jsp" %>
<link rel="stylesheet" href="css/jquery-ui-1.10.4.custom.css" /> 
<script src="js/jquery-1.9.1.js"></script>  
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>  
<script type="text/javascript">
function paymentTrxn() {
	var sURL = "<c:url value="mobccspaymenttranshistory.html"><c:param name="orderId" value="${param.orderId}"/></c:url>";
	var vArguments = new Object();
	var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
	window.showModalDialog(sURL, vArguments, sFeatures);
}

// RecreateConfirmation
<%-- comment out the original code
function recreateConfirmation(){
	
	     	$("<p>Recreate from current order?</p>").dialog({	
			resizable: false,
			height:"200",
	        width:"400",
			title:'Cancellation Confirmation',
		    modal:true,
		    buttons:{
		    	
		    	"Yes":function(){
			    	$("input[name=orderRecreateInd]").val("Y");
			    	
			    	if ($("input[name=creditCardTrxInd]").val() == "Y"){
			    	transferConfirmation();
			    	$(this).dialog("close"); 
			    	}	
			    	else{
			    	 cancelConfirmation(); 	
			    	 $(this).dialog("close"); 
			    	}
			    		
			    }, 
			    "No":function(){
			    cancelConfirmation(); 	
			    $(this).dialog("close"); 
			    }
	    
	
			    
		    }
		    }); 
	    	
	    	


}
// end of RecreateConfirmation
--%>

function recreateConfirmation(){
	
	//checked
	//$('input[name="recreateConfirmation"]').attr("checked", true);
	if ($("input[name=recreateConfirmation]").is(':checked')){
		$("input[name=orderRecreateInd]").val("Y");
	}else{
		$("input[name=orderRecreateInd]").val("N");
	}
	
	if ($("input[name=recreateConfirmation]").is(':checked') && ("${command.creditCardTrxInd}"== 'Y') && $("#transferConfirmationBut").is(':checked')){
		$("input[name=paymentTransferInd]").val("Y");
		
		cancelConfirmation();
	}else{
		cancelConfirmation(); 
	}

	//$("input[name=recreateConfirmation]").attr("checked", false);
		// cancelConfirmation(); 	


}


// transferConfirmation  comment out the original code
<%--function transferConfirmation(){
	    	$("<p>Transfer payment?</p>").dialog({
			resizable: false,
			height:"200",
	        width:"400",
			title:'Cancellation Confirmation',
		    modal:true,
		    buttons:{
		    	 "Yes":function(){
				    	$("input[name=paymentTransferInd]").val("Y");
				    	 cancelConfirmation();
				    	 $(this).dialog("close");
				    	
				    }, 
			    "No":function(){
			    	 cancelConfirmation();	
			    	 $(this).dialog("close");
			    }
		    }
		    });
}
--%>
// end of transferConfirmation

// cancelConfirmation
function cancelConfirmation(){
	    	$("<p>Confirm cancel order?</p>").dialog({
			resizable: false,
			height:"200",
	        width:"400",
			title:'Cancellation Confirmation',
		    modal:true,
		    buttons:{
		    	 "Yes":function(){
				    	$("#submitForm").submit();
				    	$(this).dialog("close");
				    },
			    "No":function(){
			    	$(this).dialog("close"); 
			    }
		    }
		    });
}
// end of cancelConfirmation


function multiSimWarning(){
	    	$("<p>Can not recreate order due to MultiSim</p>").dialog({
			resizable: false,
			height:"200",
	        width:"400",
			title:'Warning',
		    modal:true,
		    buttons:{
			    "OK":function(){
			    	$(this).dialog("close"); 
			    }
		    }
		    });
}
function controlPaymentTransferButton(){

	if (($("input[name=recreateConfirmation]").is(':checked')) && ("${command.creditCardTrxInd}"== 'Y')){
	  $(".transferConfirmation").show();
	}else{
		
	  $(".transferConfirmation").hide();
	  $("#transferConfirmationBut").attr("checked",false);
	}
	
}

$(document).ready(function() {
	controlPaymentTransferButton();
	if ($("input[name=orderStatus]").val() == "03" 
			|| $("input[name=orderStatus]").val() == "04"
			|| $("input[name=lockInd]").val() == "Y") {
		$("select[name=codeId]").attr("disabled", true);
		$("textarea[name=remark]").attr("disabled", true);
		$("form[name=submitForm] input[type=button]").attr("disabled", true);
		
		switch ($("input[name=orderStatus]").val()) {
		case "03":
			$("form[name=submitForm] input[type=button]").after($("<span/>").addClass("error").text("Order in CANCELLING status"));
			break;
		case "04":
			$("form[name=submitForm] input[type=button]").after($("<span/>").addClass("error").text("Order in CANCELLED status"));
			break;
		}
		if ($("input[name=lockInd]").val() == "Y") {
			$("form[name=submitForm] input[type=button]").after($("<span/>").addClass("error").text("Order is locked"));
		}
	}
	
	$("#cancelOrderButton").click(function(e) {
		$("input[name=orderRecreateInd]").val("N");
		$("input[name=paymentTransferInd]").val("N");
		$(".error_msg").hide();
		var hasError = false;
		if ($("select[name=codeId]")[0].selectedIndex == 0) {
			$(".error_codeId").show();
			hasError = true;
		}
		if ($("textarea[name=remark]").val().length > 150) {
			$(".error_remark").show();
			hasError = true;
		}
		if (hasError) {
			e.preventDefault();
			return false;
		}
	
		<c:if test="${!fn:startsWith(param.orderId, 'CSBOM')}">
			<c:choose>
			<c:when test="${!isMultiSimOrNot && !isPreRegOrNot}">
				//recreateConfirmation();
				//cancelConfirmation();
				recreateConfirmation();
			</c:when>
			<c:otherwise>
				cancelConfirmation();
			</c:otherwise>
			</c:choose>
		</c:if>
		
		<c:if test="${fn:startsWith(param.orderId, 'CSBOM')}">
			cancelConfirmation();
		</c:if>
	});

	<c:if test="${not empty param.orderId}">
	$(".orderPreview").load("<c:url value="mobccspreview.html"><c:param name="orderId" value="${param.orderId}"/><c:param name="action" value="ENQUIRY"/></c:url> #wrapper"
				, function() {
					$(".orderPreview > div").removeAttr("id");
				});
	</c:if>
	
	  $("input[name=recreateConfirmation]").click(function(){
		  controlPaymentTransferButton();
	    });
	    
});
</script>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<form:form action="mobccscancellation.html" id="submitForm" name="submitForm">
<c:if test="${command.lob == 'MOB'}">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<colgroup style="width:25%"></colgroup>
<thead>
<tr class="contenttextgreen">
	<td class="table_title" colspan="2">Cancel Order(Order ID: <c:out value="${param.orderId}"/>)</td>
</tr>
</thead>
<tbody>
<tr>
	<td>Cancel Reason</td>
	<td>
		<form:select path="codeId">
			<form:option value="" />
			<form:options items="${cancelReasons}" itemLabel="codeDesc" itemValue="codeId" />
		</form:select>
		<div style="clear:both"></div>
		<span class="error error_msg error_codeId" style="display:none">Cancel Reason is required</span>
		<form:errors cssClass="error" path="codeId"/>
	</td>
</tr>
<tr>
	<td>Cancel Remark</td>
	<td>
		<form:textarea path="remark" rows="5" cols="60"/>
		<div style="clear:both"></div>
		<span class="error error_msg error_remark" style="display:none">Remark over 150 characters</span>
		<form:errors cssClass="error" path="codeId"/>
	</td>
</tr>
<tr>
	<td colspan="2">
	<c:choose>
	<c:when test="${command.reserveMrtInd == 'Y'}">
	Reserve MRT found, Not allow to cancel recreate order (allow cancel only)!!
	</c:when>
	<c:when test="${command.preRegInd == 'Y'}">
	Pre-registration number found, Not allow to cancel recreate order (allow cancel only)!!
	</c:when>
	</c:choose>
		
	</td>
</tr>
<tr>
	<td>Do you want to recreate order?</td>
	<td>
		<input type="checkbox" name="recreateConfirmation" id="recreateConfirmation"><font color="red"> tick (yes), untick (no)</font>	
	</td>
</tr>

<c:if test="${command.creditCardTrxInd=='Y'}">
	<tr class="transferConfirmation">
		<td>Do you want to transfer payment to new order?</td>
		<td>
			<input type="checkbox" id="transferConfirmationBut" name="transferConfirmation"><font color="red"> tick (yes), untick (no)</font>
		</td>
	</tr>
</c:if>
<tr>
	<td colspan="2">
		<form:hidden path="orderId"/>
		<form:hidden path="orderRecreateInd"/>
		<form:hidden path="paymentTransferInd"/>
		<form:hidden path="creditCardTrxInd"/>
		<form:hidden path="orderStatus"/>
		<form:hidden path="lockInd"/>
		<form:hidden path="reserveMrtInd"/>
		<input type="button" id="cancelOrderButton" value="Cancel Order" style="float:right">
		<form:errors cssClass="error" path="orderId" cssStyle="float:left"/>
	</td>
</tr>
</tbody>
</table>
</c:if>
</form:form>

<div style="clear:both;padding-top:5px"></div>

<div class="orderPreview"><span class="contenttextgreen">Loading...</span></div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>