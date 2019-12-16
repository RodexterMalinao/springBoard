<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui"%>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<link type="text/css"
	href="css/custom-theme/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="js/json2.min.js"></script>
<%@ include file="loadingpanel.jsp"%>



<script type="text/javascript">
var iotPlan = "${quotaPlanInfoUI.iotPlan}"
var maxTime ="${quotaPlanInfoUI.maxTopUpTimes}"
var engDesc = "${quotaPlanInfoUI.engDesc}";
var suppressLocal = "${quotaPlanInfoUI.suppressLocal}";
var suppressRoam = "${quotaPlanInfoUI.suppressRoam}";
var selectedQuotaPlanOption = "${quotaPlanInfoUI.selectedQuotaPlanOption}";

function formSubmit(actionType) {
	if (iotPlan =="Y"){
		$("select[name=selectedQuotaPlanOption]").attr("disabled",false);
		$("input[name=maxTopUpTimes]").attr("disabled",false);
		$("input[name=suppressLocal]").attr("disabled",false);
		$("input[name=suppressRoam]").attr("disabled",false);
		$("input[name=autoTopUpInd]").attr("disabled",false)
	}
	$("form").submit();
}

function enableTopUp(){
	if ($("input[name=autoTopUpInd]").prop("checked")){
		$("select[name=selectedQuotaPlanOption]").attr("disabled", false);
		$("input[name=maxTopUpTimes]").attr("disabled", false);
	}else{
		$("select[name=selectedQuotaPlanOption]").attr("disabled", true);
		$("input[name=maxTopUpTimes]").attr("disabled", true);
	 	$("select[name=selectedQuotaPlanOption]").val("");
		$("input[name=maxTopUpTimes]").val("");
	}
}

function autoFieldIotPlan(maxCount){
	$("input[name=autoTopUpInd]").prop("checked",true);
	$("input[name=autoTopUpInd]").attr("disabled",true);
	$("select[name=selectedQuotaPlanOption]").attr("disabled",true);
	$("<option value='" + selectedQuotaPlanOption + "' selected='selected' >" + engDesc + "</option>").appendTo($("#selectedQuotaPlanOption"));
	$("input[name=maxTopUpTimes]").attr("disabled",true);
	$("input[name=maxTopUpTimes]").val(maxCount);
	$("input[name=suppressLocal]").attr("disabled",true);
	$("input[name=suppressRoam]").attr("disabled",true);

	
	
}
$(document).ready(function() {
	
	if (iotPlan!="Y"){
		$("input[name=autoTopUpInd]").change(function(){
			enableTopUp();
		}).change();
	}
	
	if (iotPlan=="Y"){
		autoFieldIotPlan(maxTime);
	}
	
	
});
</script>
<form:form name="quotaPlanInfoForm" method="POST"
	commandName="quotaPlanInfoUI">
	<!--content-->
	<h3 class="table_title">Quota Plan Information</h3>
	<table style="width: 100%" border="0" cellspacing="0"
		class="contenttext">
		<tr>
			<td>Auto Top-up Service Pack:</td>
		</tr>
		<tr>
			<td>${quotaPlanInfoUI.itemDisplayDTO.description}</td>
		</tr>

		<tr>
			<td>
				<table style="width: 100%" border="0" cellspacing="0"
					class="contenttext">
					<tr>
						<td>
							<h4>Local/Roaming Data</h4>
						</td>
					</tr>
					<tr>
						<td><form:checkbox path="autoTopUpInd" value="Y" />Enable
							Auto Top-up</td>
					</tr>
					<tr>
						<td>Top-Up Options</td>
						<td><form:select path="selectedQuotaPlanOption">
							<c:if test="${quotaPlanInfoUI.iotPlan!='Y'}">
									<form:option label="" value="" />
									<c:forEach items="${quotaPlanInfoUI.quotaPlanInfoDTO}"
										var="topUpOptions">
										<form:option label="${topUpOptions.engDesc}"
											value="${topUpOptions.buoId}" />
									</c:forEach>
								</c:if>
							</form:select></td>
					</tr>
					<tr>
						<td>
						</td>
						<form:errors path="selectedQuotaPlanOption" cssClass="error" />
						<td>
						</td>
					</tr>
					<tr>
						<td>Maximum Top-Up</td>
						<td><form:input maxlength="2" path="maxTopUpTimes" /> (1-99)
							Time(s)</td>
					</tr>
					
					<tr>
						<td>
						</td>
						<td>
							<form:errors path="maxTopUpTimes" cssClass="error" />
						</td>
					</tr>
					
					<c:if test="${quotaPlanInfoUI.iotPlan =='Y'}">
						<tr>
							<td><form:checkbox path="suppressLocal" value="Y" />Suppress local mobile data top-up option</td>
						</tr>
						
						<tr>
							<td><form:checkbox path="suppressRoam" value="Y" />Suppress roaming mobile data top-up option</td>
						</tr>
					</c:if>
				</table>

			</td>
		</tr>
		<tr>
			<td><a href="#" class="nextbutton3"
				onClick="javascript:formSubmit('save');">SAVE</a></td>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>