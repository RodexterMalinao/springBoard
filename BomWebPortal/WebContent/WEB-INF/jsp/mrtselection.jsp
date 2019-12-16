<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<style type="text/css">
</style>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript">

function loadPatternMrtMsisdnAndLvl() {
	var shopCode = "<c:out value="${shopCd}"/>";

	var searchPattern = $('#searchPattern').val();
	var searchMsisdnlvl = $('#mrtGradeSelect').val();
	var numType = $('#numTypeSelect').val();
	
	var error = false;
	if (searchPattern == null || searchPattern == "" ) {
		$(".error_pattern").show();
		error = true;
	}else{
		var regex = /\d{2,}/;
		var regex2 = /^[0-9\*]*$/;
		if (searchPattern.match(regex)==null || searchPattern.match(regex2)==null ){
			$(".error_pattern2").show();
			error = true;
		}
	}
	
	
	if (searchMsisdnlvl == null || searchMsisdnlvl == "" ) {
		$(".error_grade").show();
		error = true;
	}
	
	if (numType == null || numType == "" ) {
		$(".error_numType").show();
		error = true;
	}

	
	if (error == true){
		e.preventDefault();
		return false;
		
	}else{

		$("#mrtSelect").empty();
		$("<option value=''>"+"- Select -"+"</option>").appendTo($('#mrtSelect'));
		$.ajax({
			url : 'mrtselectionajax.html',
			type : 'POST',
			cache : false,
			data : {
				searchType : 'pattern',	
				pattern : searchPattern,
				msisdnlvl : searchMsisdnlvl,
				shopCd : shopCode,
				numType : numType
			},
			dataType : 'JSON',
			error : function() {
				alert('Error searching Pattern Mrt number!');
			},
			success : function(msg) {
				var msgs = eval(msg);
				if (typeof msgs === 'undefined' || !msgs.length) {
					alert('No record found.');
					return;
				}
				$.each(msgs, function(i, item) {
					/* $("<option value='" + item.msisdn + "' data-msisdnlvl='" + item.msisdnlvl + "'>"
									+ item.msisdn + "</option>")
							.appendTo($('#mrtSelect')); */
					$("<option value='" + item.msisdn + "' data-msisdnlvl='" + item.msisdnlvl + "' data-numType='" + item.numType + "'>"
							+ item.msisdn + "(" + item.msisdnlvl +")(" + item.numType + "-MRT)</option>")
					.appendTo($('#mrtSelect'));
				});
			}
	
		});	
	}
}



function loadRandomMrtMsisdnAndLvl() {

	var shopCode = "<c:out value="${shopCd}"/>";
	var numType = $('#numTypeSelect').val();
	var error = false;

	var searchMsisdnlvl = "";
	if ($('#type').val() == 'secondary') {
		searchMsisdnlvl = "N";
	}
	
	$("#mrtSelect").empty();
	$("<option value=''>"+"- Select -"+"</option>").appendTo($('#mrtSelect'));

	if (numType == null || numType == "" ) {
		$(".error_numType").show();
		error = true;
	}

	
	if (error == true){
		e.preventDefault();
		return false;
		
	}
	
	$.ajax({
		url : 'mrtselectionajax.html',
		type : 'POST',
		cache : false,
		data : {
			searchType : 'random', 
			msisdnlvl : searchMsisdnlvl,
			shopCd : shopCode,
			numType : numType
		},
		dataType : 'JSON',
		error : function() {
			alert('Error searching Random Mrt number!');
		},
		success : function(msg) {	
			var msgs = eval(msg);
			if (typeof msgs === 'undefined' || !msgs.length) {
				alert('No record found.');
				return;
			}
			$.each(msgs, function(i, item) {
				$("<option value='" + item.msisdn + "' data-msisdnlvl='" + item.msisdnlvl + "' data-numType='" + item.numType + "'>"
								+ item.msisdn + "(" + item.msisdnlvl +")(" + item.numType + "-MRT)</option>")
						.appendTo($('#mrtSelect'));
			});
		}

	});	
}

function clearSearch(){
	$(".error_pattern").hide();
	$(".error_pattern2").hide();
	$(".error_grade").hide();	
	$(".error_msisdn").hide();	
	$(".error_numType").hide();	

	$('input[name=searchType]:eq(0)').attr('checked', 'checked');
	radioSelection();
	
	$("#mrtSelect").empty();
	$("<option value=''>"+"- Select -"+"</option>").appendTo($('#mrtSelect'));
	
	$('#searchPattern').val("");
	$("#mrtGradeSelect").val($("#mrtGradeSelect option:first").val());


}

function searchMRT(){
	$(".error_pattern").hide();
	$(".error_pattern2").hide()
	$(".error_grade").hide();
	$(".error_msisdn").hide();	
	$(".error_numType").hide();	

	var selected = $("input[name='searchType']:checked").val();
	
	if (selected == 'random') {
		loadRandomMrtMsisdnAndLvl();
	
	} else if (selected == 'pattern') {
		loadPatternMrtMsisdnAndLvl();
	} 
	
}

function formSubmit() {
	
	var msisdn = $('#mrtSelect').val();
	var msisdnlvl = $('#mrtSelect').find(":selected").attr('data-msisdnlvl');
	var numType = $('#mrtSelect').find(":selected").attr('data-numType');
	var error = false;
	if (msisdn == null || msisdn == "" ) {
		$(".error_msisdn").show();
		error = true;
	}
	
	
	if (error == true){
		e.preventDefault();
		return false;
	}
	
	if ($('#type').val() == 'primary') {
		opener.document.mnpForm.newMsisdn.value = msisdn;
		opener.document.getElementById('msisdnLvl').value = msisdnlvl;
		opener.document.getElementById('numType').value = numType;
		
		
		//opener.document.mnpForm.actionType.value = 'REFRESH';
		//opener.document.mnpForm.submit();
	} else if ($('#type').val() == 'customerprofile') {
		opener.document.getElementById('mrtSelection').value = msisdn;
	}
	else if ($('#type').val() == 'secondary') {
		var seq = $('#seq').val();
		opener.document.getElementById('msisdn_'+seq).value = msisdn;
		if (numType!=null){
			opener.document.getElementById('numType_'+seq).value = numType;
		}
	} 

	self.close();

}

function formClose() {
	self.close();
}

function radioSelection() {
	//$('#confirm').attr('disabled', true);
	var selected = $("input[name='searchType']:checked").val();

	if (selected == 'random') {
		$('#searchPattern').attr('disabled', true);
		$('#mrtGradeSelect').attr('disabled', true);
	
	} else if (selected == 'pattern') {
		$('#searchPattern').attr('disabled', false);
		$('#mrtGradeSelect').attr('disabled', false);
	} 
}

$(document).ready(function() {
	$("div#header").hide();
	$("div#footer").hide();
	

	//$('#confirm').attr('disabled', true);
	$('input[name=searchType]:eq(0)').attr('checked', 'checked');
	radioSelection();
	
	
	
});
</script>
<form:form name="mrtSelectionForm" method="POST">

		<table width="100%" class="tablegrey" class="contenttext"
			background="images/background2.jpg">
			<tr>
				<c:choose>
					<c:when test="${param.type == 'primary' or param.type == 'customerprofile'}">
						<td class="table_title">MRT Search</td>
					</c:when>
					<c:when test="${param.type == 'secondary'}">
						<td class="table_title">Secondary MRT Search</td>
					</c:when>
				</c:choose>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="1" class="contenttext">					
								<tr>
									<td colspan="2"><input type="radio" id="searchType" name="searchType"
										value="random" onclick="radioSelection()" />Random Search</td>
									<td>Number Type</td>
									<td colspan="2"><select name="numTypeSelect" disabled="disabled" id="numTypeSelect"	onchange="loadmrtGradeList()">
											<option value="" selected='selected'>- Select -</option>
											<c:forEach var="numType" items="${mrtNumTypeList}">
												<option value="${numType.codeId}" <c:if test="${selectedNumType eq numType.codeId}">selected</c:if>>${numType.codeId}-MRT</option>
											</c:forEach>
									</select>
									</td>
									<td>								
									</td>
								</tr>
								<tr>
								    <td colspan="3"></td>
									<td colspan="3">
									<span class="error error_msg error_numType" style="display:none">Number Type is required.</span>
									</td>
								</tr>
								<tr>
									<td><input type="radio" id="searchType" name="searchType"
										value="pattern" onclick="radioSelection()" />Pattern Search</td>
									<td><input type="text" id="searchPattern" name="searchPattern" maxlength="8"/>
									</td>
									<td>Grade</td>
									<td colspan="2"><select name="mrtGradeSelect" id="mrtGradeSelect"	onchange="loadmrtGradeList()">
											<option value="" selected='selected'>- Select -</option>
											<c:forEach var="mrtGrade" items="${mrtGradeList}">
												<option value="${mrtGrade.codeId}">${mrtGrade.codeDesc}</option>
											</c:forEach>
									</select>
									</td>
								</tr>
								<tr>
									<td>
									</td>
									<td colspan="2">
									<span class="error error_msg error_pattern" style="display:none">Pattern is required.</span>
									<span class="error error_msg error_pattern2" style="display:none">Invalid Pattern</span>
									</td>
									<td>
									
									<span class="error error_msg error_grade" style="display:none">Grade is required.</span>
									</td>
								</tr>	
								<tr>
									<td colspan="3">
									</td>
									<td>
										<div style="float: right">
											<input type="button" id="search" name="search"
										value="Search" onclick="searchMRT()" /> <input type="button"
										id="clear" name="clear" value="Clear" onclick="clearSearch()" />
										</div>
									</td>									
								</tr>			
								<tr>
									<td>MRT List</td>
									<td><select name="mrtSelect" id="mrtSelect"	 style="width: 110px;" onchange="loadmrtList()">
											<option value="" selected='selected'>- Select -</option>	
											<%-- <c:forEach var="mob3GRandomNumList" items="${mobRandomNumList}">
												<option value="${mob3GRandomNumList[0]}" data-msisdnlvl="${mob3GRandomNumList[1]}">${mob3GRandomNumList[0]}(${mob3GRandomNumList[1]})</option>
											</c:forEach>			 --%>						
									</select></td>
								</tr>
								<tr>
									<td></td>
									<td><span class="error error_msg error_msisdn" style="display:none">MRT is required.</span>
									</td>
								</tr>	
								<tr>
								<td colspan="3">
									</td>
									<td>
										<div style="float: right"><input type="button" id="confirm" name="confirm"
										value="Confirm" onclick="formSubmit()" /> <input type="button"
										id="close" name="close" value="Close" onclick="formClose()" />
										</div>
								</td>
								</tr>
					</table>
				</td>
			</tr>
		</table>
		<input type="hidden" name="type" id="type" value="${param.type}" />
		<input type="hidden" name="seq" id="seq" value="${param.seq}" />
		
	</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>