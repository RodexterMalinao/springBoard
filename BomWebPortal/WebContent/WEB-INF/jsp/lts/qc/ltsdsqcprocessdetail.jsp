<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>

<form:form id="form" commandName="ltsdsqcprocessform" cssClass="paper_w2" autocomplete="off">
<input type="hidden" id="qcStatus" name="qcStatus" value="${ltsdsqcprocessform.qcStatus}" />
<input type="hidden" id="sbStatus" name="sbStatus" value="${ltsdsqcprocessform.sbStatus}" />

	<form:hidden path="action"/>
	<div id="orderdetail" style="margin-left: 5%; width: 90%;">
		<br/>
		<table width="100%" border="0" cellspacing="1" bgcolor="#FFFFFF" class="paper_no round_20 table_style_sb">
			<tr>
			   	<th class="table_title">Order Summary</td>
		    </tr>
		</table>
		<br/>
		<div id="orderSummaryFrame">
			<iframe src="about:blank" width="100%" height="320px" onload="summaryLoaded()"></iframe>  
		</div>
	</div>
	
	<div id="amendmentHistory" style="margin-left: 5%; width: 90%;">
		<br/>
		<table width="100%" border="0" cellspacing="1" bgcolor="#FFFFFF" class="paper_no round_20 table_style_sb">
			<tr>
			   	<th class="table_title">Amendment History</td>
		    </tr>
		</table>
		<br/>
		<div id="amendHistoryFrame">
			<iframe src="about:blank" width="100%" height="320px" onload="amendHistLoaded()"></iframe> 
			<%-- <iframe src="qcimsamendremarks.html?orderId=${orderid}" width="100%" height="280px"></iframe>   --%>
		</div>
	</div>

	<div id="qcAction" style="margin-left: 5%; width: 90%;">
		<table width="100%" border="0" cellspacing="1" bgcolor="#FFFFFF" class="paper_no round_20 table_style_sb">
			<tbody>
				<tr>
	         		<td colspan="6">&nbsp;</td>
	        	</tr>
	    	    
	    	    <tr>
			   		<th class="table_title" colspan="6">QC Action</td>
		       	</tr>
			
				<tr>
					<td colspan="6">&nbsp;</td>
				</tr>
	
				<tr>
					<td align="left">Order LOB:</td>
					<td colspan="4" align="left">
						${ltsdsqcprocessform.lob}
					</td>
				</tr>
				<tr>
					<td align="left">Assign Date:</td>
					<td align="left">
						<form:input path="assignDate" disabled="true"/>
					</td>
					<td align="left">Assignee:</td>
					<td align="left">
					    <form:input path="assignee" disabled="true"/>
					</td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="left">QC Date Time:</td>
					<td colspan="4" align="left">
						<form:input path="qcDateTime" disabled="true" />
					</td>
				</tr>
				
				<tr>
					<td align="left">QC Calling Time:</td>
					<td colspan="4" align="left">
	 					<form:textarea cssClass="formText" id="qcCallTime" path="qcCallTime" rows="6" cols="40" />  
					</td>
				</tr>	
				
				<tr>
					<td align="left">QC Finding Details:</td>
					<td colspan="4" align="left">
		 				<form:textarea cssClass="formText"  id ="qcFindingDetail" path="qcFindingDetail" rows="6" cols="40" />  
						<input class="formText"  type="button" id="addQcFinding" name="addQcFinding" value="Add" onclick="javascript:updateQcRemark()">
					</td>
				</tr>	
				<tr>
					<td align="left">Order District:</td>
					<td colspan="4" align="left">
						<form:select cssClass="formText"  path="orderDistrict">  
	        				<option></option>
	        				<option>HONG KONG</option>
	        				<option>KOWLOON</option>
	        				<option>NEW TERRITORIES</option>
	        			</form:select>
					</td>
				</tr>	
				<tr>
					<td align="left">Order Place:</td>
					<td colspan="4" align="left">
					<form:input cssClass="formText"  path="orderPlace" />
	<!-- 					<input name="installAddress.quickSearch" type="text" value="" autocomplete="off" class="ac_input"> -->
					</td>
				</tr>
			</tbody>
		</table>
				
		<div class="default" style="margin-top: 30px;" align="right">
		<%-- <c:if test="${!ltsdsqcprocessform.mustQc}">
			<a id="qcPass" class="nextbutton func_button" >
				Pass
			</a> 
		</c:if>
		<c:if test="${ltsdsqcprocessform.mustQc}">
			<a id="qcPassMakeAppnt" class="nextbutton func_button" >
				Pass and Make appointment
			</a> 
		</c:if> --%>
				
		<a id="qcPassAppt" class="nextbutton func_button" hidden=true >Pass and Make Appointment</a> 
		<a id="qcPass" class="nextbutton func_button" hidden=false >Pass</a>
		<a id="qcAppt" class="nextbutton func_button" hidden=true >Make Appointment</a>
		 
		<a id="qcCannot" class="nextbutton func_button" >Cannot QC</a>
		<form:select cssClass="qcSelect" path="qcCannotQcReason" id="qcCannotQcReason">
			<form:option value="">-- SELECT --</form:option>
			<form:options items="${cannotQcReasonList}" itemValue="itemKey" itemLabel="itemValue" />
		</form:select>
		
		<a id="qcFailed" class="nextbutton func_button" >Fail</a>
		<form:select  cssClass="qcSelect" path="qcFailedReason" id="qcFailedReason">
			<form:option value="">-- SELECT --</form:option>
			<form:options items="${failedReasonList}" itemValue="itemKey" itemLabel="itemValue" />
		</form:select>
		
		<a id="qcRNA" class="nextbutton func_button" >RNA</a>
		
		</div>
	</div>

</form:form>


<script>
function summaryLoaded(){
	$("#orderSummaryFrame").unblock();
};

function amendHistLoaded(){
	$("#amendHistoryFrame").unblock();
}

function submitForm(){
	$.blockUI();
	$("#form").submit();
}
function hideElemetns(){
	$(".formText").attr("disabled", false);
	$(".nextbutton").show();
	$("#qcPassAppt").hide();
	$("#qcPass").show();
	$("#qcAppt").hide();
	
	if($("#qcStatus").val()!= null && $("#qcStatus").val()!= ""){
		/* $(".formText").attr("disabled", true);
		$(".nextbutton").hide();
		$(".qcSelect").hide();	 */	
		
		if($("#qcStatus").val() == 'Q01' && $("#sbStatus").val() == 'Q'){
			$("#qcAppt").show();
			$("#qcPassAppt").hide();
			$("#qcPass").hide();
		}		
	}else{
		if($("#sbStatus").val() == 'Q'){
			$("#qcPassAppt").show();
			$("#qcPass").hide();
			$("#qcAppt").hide();
		}
	}
}

function updateQcRemark(){
	$.blockUI();
	$.ajax({
		url : 'ltsqcprocessajax.html?oid=${ltsdsqcprocessform.orderId}&remark='+$("#qcFindingDetail").val(),
		type : 'GET',
		dataType : 'json',
		timeout : 25000,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		     if(textStatus=='parsererror'){
				alert("Your session has been timed out, please re-login."); 
		     } else {
		    	 alert("Error loading!");
		     }
		     $.unblockUI();
		},
		success : function(msg) {
			alert("Remark added.");
			$.unblockUI();
		}
	});
}

$(document).ready(function(){
	checkWindow();
	hideElemetns();
	$("#orderSummaryFrame").block({ message: "<p><image src='images/large-loading.gif'/><br><b>Loading</b></p>"});
	$("#orderSummaryFrame iframe").attr('src', 'ltsorder.html?action=ENQUIRY&orderId=${ltsdsqcprocessform.orderId}');

	$("#amendHistoryFrame").block({ message: "<p><image src='images/large-loading.gif'/><br><b>Loading</b></p>"});
	$("#amendHistoryFrame iframe").attr('src', 'ltsamendhistview.html?orderId=${ltsdsqcprocessform.orderId}');
	
	$("#qcPass").click(function(){
		$("#action").val('PASS');
		submitForm();
	});
	
	$("#qcPassAppt").click(function(){
		$("#action").val('PASS_APPT');
		submitForm();
	});
	
	$("#qcAppt").click(function(){
		$("#action").val('APPT');
		submitForm();
	});
	
	$("#qcCannot").click(function(){
		/* if($("#qcCannotQcReason").val() == ''){
			alert("Please select a reason.");
			return;
		} */
		$("#action").val('CANNOT_QC');
		submitForm();
	});
	
	$("#qcFailed").click(function(){
		/* if($("#qcFailedReason").val() == ''){
			alert("Please select a reason.");
			return;
		} */
		$("#action").val('FAILED');
		submitForm();
	});
	
	$("#qcRNA").click(function(){
		$("#action").val('RNA');
		submitForm();
	});
});
</script>

</body>
</html>
<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>