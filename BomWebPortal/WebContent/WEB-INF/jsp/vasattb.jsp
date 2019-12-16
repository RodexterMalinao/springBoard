<%@ include file="/WEB-INF/jsp/header.jsp" %>
<script src="js/jquery-1.9.1.js"></script>
<%@ page import="			
					org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.dto.VasAttbComponentDTO,
					com.bomwebportal.dto.ComponentDTO,
					java.util.*
					"
%>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="offerdetail" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>
<%
	List<ComponentDTO> componentList = (List<ComponentDTO>)request.getAttribute("componentList");
	Map<String, List<VasAttbComponentDTO>> vasAttbMap = (HashMap)request.getAttribute("vasAttbMap");
	
	List<String> inputFieldList = new ArrayList<String>();
	List<String> minInputFieldLengthList = new ArrayList<String>();
	List<String> fieldValidatedList = new ArrayList<String>();
	List<String> attbValueDescList = new ArrayList<String>();
	for(String key: vasAttbMap.keySet()){
		
		List<VasAttbComponentDTO> vasAttbList = vasAttbMap.get(key);	
		VasAttbComponentDTO firstVasAttb = (VasAttbComponentDTO)vasAttbList.get(0);
		//System.out.println(firstVasAttb.getInputMethod());
		if("INPUT".equals(firstVasAttb.getInputMethod())){
			//System.out.println(firstVasAttb.getCompAttbId());
			inputFieldList.add(firstVasAttb.getCompAttbId());	
			minInputFieldLengthList.add(String.valueOf(firstVasAttb.getMinLength()));
			fieldValidatedList.add(firstVasAttb.getInputFormat());
			attbValueDescList.add(firstVasAttb.getCompAttbDesc());
			//System.out.println("~~~~~: " + firstVasAttb.getCompAttbValueDesc());

		}
	}
%>
<%@ include file="loadingpanel.jsp" %>
<script src="js/json2.min.js"></script>
<script type="text/javascript">
<!--
var REX_CHAR = /^[A-Za-z\s]*$/;
var REX_DIGIT = /^[\d]*$/;
var REX_DIGITCHAR = /^[A-Za-z\d\s]*$/;
var REX_STRING = /^[A-Za-z\d\s\/',-\.]*$/;

$(document).ready(function() {
	$('html').bind('keypress', function(e)
			{
			   if(e.keyCode == 13)
			   {
			      return false;
			   }
			});
	
	$(document).ajaxStart(function() {
		 
		showLoading();
	}).ajaxStop(function() {
		hideLoading();
	});
	
	autoFillWalkInProduct();
	autoFillCoupon();
});

function validate(REX, input ) {
	//input =myInput.value;
	//if (input.length == 0) {
		//alert("validate length =0");
	//	return false;
	//}

	if (!REX.test(input)) {
		//alert("validate :false");
		return false;
	} else {
		//alert("validate :true");
		return true;
	}

}

function autoFillWalkInProduct(){
	 var walkInKey = ${projectSevenWalkInKey}; 
	 var isWalkInProduct = ${isWalkInProduct};
	 var projectSevenAttb = ${projectSevenAttb};
	 var previousWalkInProduct = ${previousWalkInProduct};
 	<%
	for(int i=0; i<inputFieldList.size(); i++){
		String inputField = (String)inputFieldList.get(i);

		request.setAttribute("inputField", inputField);
	
	%>
	var inputField = ${inputField};
	if(isWalkInProduct == true && projectSevenAttb==inputField){
	$('#<%=inputField%>').val(walkInKey);
	}else if (projectSevenAttb==<%=inputField%> && isWalkInProduct==false && previousWalkInProduct==true){
		$('#<%=inputField%>').val("");
	}
	<%}%>	
}

function autoFillCoupon() {
	var couponKey = ${digitalCouponSms}; 
	var digitalCouponAttb = ${digitalCouponAttb};
	var channelId = "<c:out value="${channelId}"/>";
	var docType = "<c:out value="${docType}"/>";
	var docId = "<c:out value="${docId}"/>";
	<%
	for (int i = 0; i<inputFieldList.size(); i++) {
		String inputField = (String)inputFieldList.get(i);
		request.setAttribute("inputField", inputField);
	%>
	var inputField = ${inputField};
	if (digitalCouponAttb == inputField) {
		if (channelId != '2') {
			$('#<%=inputField%>').val(couponKey);
			if (docType != 'BS') {
				$('#<%=inputField%>').attr('readonly', true);
				$('#<%=inputField%>').css('background-color', '#EBEBE4');
			} else {
				$('#<%=inputField%>').attr('readonly', false);
				$('#<%=inputField%>').css('background-color', '#ffffff');
			}
		} else {
			if (docType != 'BS') {
				$(sameAsMrt).attr('checked', true);
				$(sameAsMrt).attr("disabled", true);
				$('#<%=inputField%>').css('background-color', '#EBEBE4');
				$('#<%=inputField%>').val("        ");
				$('#<%=inputField%>').attr('readonly', true);
			}
		}
	}
	<%}%>
}

function formSubmit(){
	<%
	for(int i=0; i<inputFieldList.size(); i++){
		String inputField = (String)inputFieldList.get(i);
		String minInputField = (String)minInputFieldLengthList.get(i);
		String rex = (String)fieldValidatedList.get(i);
		String errorMsg4rex = new String();
		String attbValueDesc = (String)attbValueDescList.get(i);
		if("a".equalsIgnoreCase(rex)){
			rex = "REX_DIGITCHAR";
			errorMsg4rex = "alphanumeric.";
		}else if("n".equalsIgnoreCase(rex)){
			rex = "REX_DIGIT";
			errorMsg4rex = "numeric only.";
		}else if("s".equalsIgnoreCase(rex)){
			rex = "REX_STRING";
			errorMsg4rex = "valid string.";
		}else{
			rex = "REX_CHAR";
			errorMsg4rex = "character only.";
		}
		if (!"0".equals(minInputField) && (minInputField != null)){%>
			if (document.getElementById('<%=inputField%>').value.trim().length == "0"){
				alert("Please input <%=attbValueDesc%>. Thanks.");
				return;
			} else if (document.getElementById('<%=inputField%>').value.trim().length<<%=minInputField%>){
				alert("The minimum length of <%=attbValueDesc%> is <%=minInputField%>. Please re-enter. Thanks.");
				return;
			}
		<%}%>
		if(!validate(<%=rex%>, document.getElementById('<%=inputField%>').value.trim())){
			alert("The format of input field (<%=attbValueDesc%>) should be <%=errorMsg4rex%>");
			return;
		} 
		<%	
	}
	%>
	
	validateQueueRef();
	//document.vasattb.submit();
}

function sameAsMrtClick() {
	var digitalCouponAttb = ${digitalCouponAttb};
	var channelId = "<c:out value="${channelId}"/>";
	<%
	for (int i = 0; i<inputFieldList.size(); i++) {
		String inputField = (String)inputFieldList.get(i);
		request.setAttribute("inputField", inputField);
	%>
		var inputField = ${inputField};
		if (digitalCouponAttb == inputField) {
			if ($(sameAsMrt).is(':checked')) {
				$('#<%=inputField%>').val("        ");
				$('#<%=inputField%>').attr('readonly', true);
			} else {
				$('#<%=inputField%>').val("");
				$('#<%=inputField%>').attr('readonly', false);
			}
		}
	<%}%>
}

function validateQueueRef() {
	
	var orderId = "<c:out value="${orderId}"/>";
	var channelId = "<c:out value="${channelId}"/>";
	var appDateStr = "<c:out value="${appDateStr}"/>";
	var itemCode =	"<c:out value="${hsItemCode}"/>";
	var docType = "<c:out value="${docType}"/>";
	var docId =	"<c:out value="${docId}"/>";
	var msisdn = ${digitalCouponSms};
	var basketBrand = ${basketBrand};
	var $items = $("[data-index]");
	var len = $items.length;
	var array = Array();
	
 	for (var i = 0; i < len; i++) {	   
 		var myObj = {};
 		myObj.compAttbId = $($items[i]).data('index');
 		myObj.compAttbVal = $($items[i]).val();
 		array.push(myObj);     
	}

    $.ajax({
	      url: "queueRef/validQueueRef.html",
	      type: "POST",
	      timeout : 15000,
	      dataType : "JSON",
		  	data: {
		  		orderId: orderId,
		  		channelId: channelId,
				appDateStr: appDateStr,
				itemCode: itemCode,
				docType: docType,
				docId: docId,
				msisdn: msisdn,
				basketBrand: basketBrand,
				components : JSON.stringify(array)
			}
	    }).done(function(msg) {
	    	if (msg!=null){
				if (msg.error == "Y" || msg.error == "W"){
					var message = null;
					var height = "160";
					if (msg.refNo == null){
						message = msg.errMsg;
					}else{
						
						var maskReturnDocId = "";
						if (msg.docId!=null){
							maskReturnDocId = "****"+msg.docId.substring(4, msg.docId.length);
						}
						
						height = "300";
						message = "Pre-registration Number: "+msg.refNo+"<br><br>Registered ID Doc Num: "+ maskReturnDocId+"<br><br>Pre-registration Number Status: "+msg.status+"<br><br>Order ID Doc Num: "+docId+"<br><br>Error Message: "+msg.message;
					}
					
					$('<p>'+message+'</p>').dialog({
					    resizable: false,
					    height:height,
					    width:"450",
					    title:'Hero Product - Pre-registration Number Warning',
					    modal:true,
					    buttons:{
					        "Exit":function() {
					           $(this).dialog("close");
					       		if ( msg.error == "W"){
					       			$("#vasattb").submit();
					       		}
					        }
					    }
					});
					

				}else if(msg.error == "M"){
					var message = msg.message;
					var height = "160";
					$('<p>'+message+'</p>').dialog({
					    resizable: false,
					    height:height,
					    width:"450",
					    title:'Hero Product - Pre-registration Number Reminder',
					    modal:true,
					    buttons:{
					    	"Exit":function() {
					           $(this).dialog("close");
					       		if ( msg.error == "M"){
					       			$("#vasattb").submit();
					       		}
					        }
					    }
					});
				} else if(msg.error == "C") {
					var message = msg.couponErrMsg;
					var height = "160";
					$('<p>'+message+'</p>').dialog({
					    resizable: false,
					    height:height,
					    width:"450",
					    title:'Coupon SMS No.',
					    modal:true,
					    buttons:{
					    	"Exit":function() {
					           $(this).dialog("close");
					        }
					    }
					});
				}else{
					//document.vasattb.submit();
					$("#vasattb").submit();
				}
			}
    }).fail(function(jqXHR, textStatus, errorThrown) {
    	$('<p>System cannot call "HSRM Pre-Reg No. / Coupon SMS No. Validation" temporary. Please click "Exit" to proceed you order.</p>').dialog({
		    resizable: false,
		    height:"160",
		    width:"450",
		    title:'VAS Attribute Warning',
		    modal:true,
		    buttons:{
		        "Exit":function() {
		           $(this).dialog("close");
		        	   $("#vasattb").submit();
		        }
		    }
		}); 
        //alert(errorThrown);
        
    });

    
}


//-->
</script>

<form id="vasattb" name="vasattb" action="vasattb.html?sbuid=${param.sbuid}" method="POST">

<table width="100%"  class="tablegrey">
  <tr> 
  	<td bgcolor="#FFFFFF" > 
	  <table width="100%"  border="0" cellspacing="1" >
	   	<tr>
	   	  <td class="table_title">VAS Attribute Information</td>

	   	</tr>
	  </table>

<table width="100%" border="0" cellspacing="1" class="contenttext"	
	      background="images/background2.jpg">
	      <tr>
	   	  		<td>&nbsp;</td>
	   	  </tr>
	
<%
	
	
	for(String key: vasAttbMap.keySet()){
		//System.out.println("Key: " + key);
		List<VasAttbComponentDTO> vasAttbList = vasAttbMap.get(key);
		VasAttbComponentDTO firstVasAttb = (VasAttbComponentDTO)vasAttbList.get(0);
		
		ComponentDTO component = new ComponentDTO();
		boolean match = false;
		if(componentList!=null&&componentList.size()>0){
			for(int i=0; i<componentList.size(); i++){
				component = (ComponentDTO)componentList.get(i);	
				if(firstVasAttb.getCompAttbId().equals(component.getCompAttbId())){
					match = true;
					break;
				}
			}
		}

		if ("SELECT".equals(firstVasAttb.getInputMethod())) {%>	
			<tr>
				<td><%=firstVasAttb.getCompAttbDesc() %>: </td>
				<td>
					<select name="<%=firstVasAttb.getCompAttbId()%>" id="<%=firstVasAttb.getCompAttbId()%>"	data-index="<%=firstVasAttb.getCompAttbId()%>"	>
						<%for (VasAttbComponentDTO vasAttb: vasAttbList) {%>			
							<option <%=(match&&vasAttb.getCompAttbVal().equals(component.getCompAttbVal()))?"selected":""%>  value="<%=vasAttb.getCompAttbVal() %>"><%=vasAttb.getCompAttbValueDesc() %></option>
						<%} %>
					</select>
				</td>
			</tr>
		<%
		} else if ("INPUT".equals(firstVasAttb.getInputMethod())) {%>
			<tr>
				<td><%=firstVasAttb.getCompAttbDesc() %>: </td>
				<td aligh="left">
					<input type="text" id="<%=firstVasAttb.getCompAttbId()%>" name="<%=firstVasAttb.getCompAttbId()%>" data-index="<%=firstVasAttb.getCompAttbId()%>" maxlength="<%=firstVasAttb.getMaxLength()%>" value="<%=(match && (component.getCompAttbVal() != null))?component.getCompAttbVal():""%>" />
					<c:if test="${channelId eq '2'}">
						<c:if test="${digitalCouponAttb eq firstVasAttb.compAttbId}">
							<input type="checkbox" id="sameAsMrt" name="sameAsMrt" onclick="sameAsMrtClick()"/>Same as MRT<br>
						</c:if>
					</c:if>
				</td>
			</tr>
		<%}
	}	
%>
  		  <tr>
	   	  		<td>&nbsp;</td>
	   	 </tr>
</table>
</td>
</tr>
</table>

<table width="100%" border="0" cellspacing="0">
<tr>
	<td>
		<div class="buttonmenubox_R" id="buttonArea">
		<a href="#" class="nextbutton3" onClick="javascript:formSubmit();">continue ></a></div>
	</td>
</tr>
</table>
</form>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>