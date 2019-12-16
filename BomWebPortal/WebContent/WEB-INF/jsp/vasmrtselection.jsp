<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<style type="text/css">
</style>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript">

function loadRandomMrtMsisdnAndLvl() {
	
	$("#searchResult").html("");

	var channelCd = "<c:out value="${channelCd}"/>";
	var numType = "<c:out value="${customer.numType}"/>";
	var error = false;


	if (numType == null || numType == "" ) {
		$(".error_numType").show();
		error = true;
	}

	
	if (error == true){
		e.preventDefault();
		return false;
		
	}
	
	$.ajax({
		url : 'mobccsmrtajax.html',
		type : 'POST',
		cache : false,
		data : {
			type : 'GetOneCardTwoNumberByRandom',
			channelCd : channelCd,
			cityCd : $('#cityCdSelect').val(),
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
				$("<tr><td><input type='radio' name='mrt' value='" + item.msisdn + "(" + item.cityCd + "/" + item.msisdnlvl + ")'"
						+ "onclick='radioSelection()'"
						+ "data-msisdn='" + item.msisdn
						+ "'data-msisdnlvl='" + item.msisdnlvl
						+ "' data-channelCd='" + item.channelCd
						+ "' data-cityCd='" + item.cityCd
						+ "' data-msisdnStatus='" + item.msisdnStatus
						+ "' data-srvNumType='" + item.srvNumType
						+ "' data-numType='" + item.numType + "' /></td>"
					+ "<td>" + item.msisdn + "(" + item.cityCd + "/" + item.msisdnlvl + ")</td>")
					.appendTo($('#searchResult'));
						
			});
		}

	});	
}

function loadInputMrtMsisdnAndLvl() {
	
	$("#searchResult").html("");

	var channelCd = "<c:out value="${channelCd}"/>";
	var searchMsisdn = $('#mrtInput').val();
	var numType = "<c:out value="${customer.numType}"/>";
	var error = false;


	if (numType == null || numType == "" ) {
		$(".error_numType").show();
		error = true;
	}

	
	if (error == true){
		e.preventDefault();
		return false;
		
	}
	
	$.ajax({
		url : 'mobccsmrtajax.html',
		type : 'POST',
		cache : false,
		data : {
			type : 'GetOneCardTwoNumberByFullNumber',
			channelCd : channelCd,
			searchMsisdn : searchMsisdn,
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
				$("<tr><td><input type='radio' name='mrt' value='" + item.msisdn + "(" + item.cityCd + "/" + item.msisdnlvl + ")'"
							+ "onclick='radioSelection()'"
							+ "data-msisdn='" + item.msisdn
							+ "'data-msisdnlvl='" + item.msisdnlvl
							+ "' data-channelCd='" + item.channelCd
							+ "' data-cityCd='" + item.cityCd
							+ "' data-msisdnStatus='" + item.msisdnStatus
							+ "' data-srvNumType='" + item.srvNumType
							+ "' data-numType='" + item.numType + "' /></td>"
						+ "<td>" + item.msisdn + "(" + item.cityCd + "/" + item.msisdnlvl + ")</td>")
						.appendTo($('#searchResult'));
						
			});
		}

	});	
}

function loadPatternMrtMsisdnAndLvl() {
	
	$("#searchResult").html("");
	
	$(".error_pattern").hide();
	$(".error_pattern2").hide();
	
	var shopCode = "<c:out value="${shopCd}"/>";
	var cityCd = $('#cityCdSelect').val();
	

	var searchPattern = $('#searchPattern').val();
	var numType = "<c:out value="${customer.numType}"/>";
	
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
				searchType : '1c2npattern',	
				pattern : searchPattern,
				shopCd : shopCode,
				cityCd : cityCd,
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
					$("<tr><td><input type='radio' name='mrt' value='" + item.msisdn + "(" + item.cityCd + "/" + item.msisdnlvl + ")'"
								+ "onclick='radioSelection()'"
								+ "data-msisdn='" + item.msisdn
								+ "'data-msisdnlvl='" + item.msisdnlvl
								+ "' data-shopCd='" + item.shopCd
								+ "' data-cityCd='" + item.cityCd
								+ "' data-msisdnStatus='" + item.msisdnStatus
								+ "' data-srvNumType='" + item.srvNumType
								+ "' data-numType='" + item.numType + "' /></td>"
							+ "<td>" + item.msisdn + "(" + item.cityCd + "/" + item.msisdnlvl + ")</td>")
							.appendTo($('#searchResult'));
							
				});
			}
	
		});	
	}
} 

function loadSsPatternMrtMsisdnAndLvl() {
	
	$("#searchResult").html("");
	
	$(".error_pattern").hide();
	$(".error_pattern2").hide();
	
	var shopCode = "<c:out value="${shopCd}"/>";
	var cityCd = $('#cityCdSelect').val();
	
	var searchPattern = $('#searchPattern').val();
	var numType = "<c:out value="${customer.numType}"/>";
	
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
				searchType : 'sspattern',	
				pattern : searchPattern,
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
					$("<tr><td><input type='radio' name='mrt' value='" + item.msisdn + "(" + item.msisdnlvl + ")'"
								+ "onclick='radioSelection()'"
								+ "data-msisdn='" + item.msisdn
								+ "'data-msisdnlvl='" + item.msisdnlvl
								+ "' data-shopCd='" + item.shopCd
								+ "' data-cityCd='" + item.cityCd
								+ "' data-msisdnStatus='" + item.msisdnStatus
								+ "' data-srvNumType='" + item.srvNumType
								+ "' data-numType='" + item.numType + "' /></td>"
							+ "<td>" + item.msisdn + "(" + item.msisdnlvl + ")</td>")
							.appendTo($('#searchResult'));
							
				});
			}
	
		});	
	}
} 

function searchMRT(){
	$(".error_input").hide();	
	$(".error_numType").hide();	

	var selected = $("input[name='searchType']:checked").val();
	
	if (selected == 'random') {
		loadRandomMrtMsisdnAndLvl();
	
	} else if (selected == 'input') {
		loadInputMrtMsisdnAndLvl();
	} 
	
}

function formClose() {
	self.close();
}

function radioSelection() {
	//$('#confirm').attr('disabled', true);
	var selected = $("input[name='mrt']:checked").val();
	$("#selectedMrt").val(selected);
	
	$("#msisdn").val($("input[name='mrt']:checked").attr("data-msisdn"));
	$("#msisdnLvl").val($("input[name='mrt']:checked").attr("data-msisdnlvl"));
	$("#cityCd").val($("input[name='mrt']:checked").attr("data-cityCd"));
	$("#numType").val($("input[name='mrt']:checked").attr("data-numType"));
	$("#channelCd").val($("input[name='mrt']:checked").attr("data-channelCd"));
	$("#msisdnStatus").val($("input[name='mrt']:checked").attr("data-msisdnStatus"));
	$("#srvNumType").val($("input[name='mrt']:checked").attr("data-srvNumType"));
	$("#shopCd").val($("input[name='mrt']:checked").attr("data-shopCd"));

}

$(document).ready(function() {
	$("div#header").hide();
	$("div#footer").hide();
	

	//$('#confirm').attr('disabled', true);
	$('input[name=searchType]:eq(0)').attr('checked', 'checked');
	
	
	
});
</script>
<form:form name="vasMrtSelectionForm" method="POST" commandName="vasMrtSelection">
	<c:choose>
	<c:when test="${vasMrtSelection.funcId eq 'EX06'}" >
		<table width="100%" class="tablegrey" class="contenttext"
			background="images/background2.jpg">
			<tr>
				<td class="table_title">China Mobile Number Search
					<span style="float:right" class="contenttext_red"><b>(
					<label for="numTypeLabel">
					<pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${customer.numType}"/>							
					</label>								
					)</b></span>				
				</td>
			</tr>
								<tr>
								    <td colspan="3"></td>
									<td colspan="3">
									<span class="error error_msg error_numType" style="display:none">Number Type is required.</span>
									</td>
								</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="1" class="contenttext">
					<c:choose>
					<c:when test="${channelId eq '2'}" >				
								<tr>
									<td><input type="radio" id="searchType" name="searchType"
										value="random" />Random Search</td>
									<td colspan="2"><select name="cityCdSelect" id="cityCdSelect">
											<option value="" selected='selected'>- Select -</option>
											<c:forEach var="city" items="${cityList}">
												<option value="${city.codeId}">${city.codeId}</option>
											</c:forEach>
									</select>
									</td>
									<td>
										<div style="float: right">
											<input type="button" id="search" name="search"
										value="Search" onclick="searchMRT()" />
										</div>
									</td>			
								<tr>
									<td><input type="radio" id="searchType" name="searchType"
										value="input" />Input</td>
									<td colspan="2"><input type="text" id="mrtInput" name="mrtInput" maxlength="11"/>
									</td>
									<td>
										<div style="float: right">
											<input type="button" id="search" name="search"
										value="Search" onclick="searchMRT()" />
										</div>
									</td>		
								</tr>
								<tr>
									<td>
									</td>
									<td colspan="2">
									</td>
									<td>								
									<span class="error error_msg error_input" style="display:none">Input is required.</span>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td>Pattern search</td>
									<td colspan="1"><select name="cityCdSelect" id="cityCdSelect">
											<option value="" selected='selected'>- Select -</option>
											<c:forEach var="city" items="${cityList}">
												<option value="${city.codeId}">${city.codeId}</option>
											</c:forEach>
									</select>
									</td>
									<td colspan="1"><input type="text" id="searchPattern" name="searchPattern" maxlength="11"/>
									</td>
									<td>
										<div style="float: right">
											<input type="button" id="search" name="search"
										value="Search" onclick="loadPatternMrtMsisdnAndLvl()" />
										</div>
									</td>		
								</tr>
								<tr>
									<td>
									</td>
									<td colspan="2">
									<span class="error error_msg error_pattern" style="display:none">Pattern is required.</span>
									<span class="error error_msg error_pattern2" style="display:none">Invalid Pattern</span>
									</td>
								</tr>	
							</c:otherwise>
							</c:choose>		
								<tr>
									<td>Result</td>
								</tr>
								<tr>
									<td><table id="searchResult"></table></td>
								</tr>
								<tr><td colspan="5"><hr/></td></tr>			
								<tr>								
								<td>Selected MRT</td>
									<td colspan="2">
										<input type='text' disabled="disabled" id="selectedMrt" value="${vasMrtSelection.msisdn}(${vasMrtSelection.cityCd}/${vasMrtSelection.msisdnLvl})" />
										<form:hidden path="msisdn" />
										<form:hidden path="oldSecSrvNum"/>
										<form:hidden path="dbSecSrvNum"/>
										<form:hidden path="msisdnLvl" />
										<form:hidden path="cityCd" />
										
										<form:hidden path="channelCd" />
										<form:hidden path="msisdnStatus" />
										<form:hidden path="srvNumType" />		
										<form:hidden path="numType" />	
										
										<form:hidden path="shopCd" />				
									</td>							
									<td>
										<div style="float: right"><input type="submit" id="confirm" name="confirm"
										value="Confirm" /> <input type="button"
										id="close" name="close" value="Close" onclick="formClose()" />
										</div>
								</td>
								</tr>
								<tr>
								<td></td>
								<td>
									<form:errors path="msisdn" cssClass="error" />
									<form:errors path="channelCd" cssClass="error" />
									<form:errors path="shopCd" cssClass="error" />
									<form:errors path="msisdnStatus" cssClass="error" />
									<form:errors path="numType" cssClass="error" />
								</td>
								</tr>
					</table>
				</td>
			</tr>
		</table>	
		</c:when>
		<c:when test="${vasMrtSelection.funcId eq 'EX07'}" >
		<table width="100%" class="tablegrey" class="contenttext"
			background="images/background2.jpg">
			<tr>
				<td class="table_title">Secretarial Service Number Search
					<span style="float:right" class="contenttext_red"><b>(
					<label for="numTypeLabel">
					<pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${customer.numType}"/>							
					</label>								
					)</b></span>				
				</td>
			</tr>
								<tr>
								    <td colspan="3"></td>
									<td colspan="3">
									<span class="error error_msg error_numType" style="display:none">Number Type is required.</span>
									</td>
								</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="1" class="contenttext">
								<tr>
									<td>Pattern search</td>
									<td colspan="1"><input type="text" id="searchPattern" name="searchPattern" maxlength="11"/>
									</td>
									<td>
										<div style="float: right">
											<input type="button" id="search" name="search"
										value="Search" onclick="loadSsPatternMrtMsisdnAndLvl()" />
										</div>
									</td>		
								</tr>
								<tr>
									<td>
									</td>
									<td colspan="2">
									<span class="error error_msg error_pattern" style="display:none">Pattern is required.</span>
									<span class="error error_msg error_pattern2" style="display:none">Invalid Pattern</span>
									</td>
								</tr>	
								<tr>
									<td>Result</td>
								</tr>
								<tr>
									<td><table id="searchResult"></table></td>
								</tr>
								<tr><td colspan="5"><hr/></td></tr>			
								<tr>								
								<td>Selected MRT</td>
									<td colspan="2">
										<input type='text' disabled="disabled" id="selectedMrt" value="${vasMrtSelection.msisdn}(${vasMrtSelection.msisdnLvl})" />
										<form:hidden path="msisdn" />
										<form:hidden path="oldSecSrvNum"/>
										<form:hidden path="dbSecSrvNum"/>
										<form:hidden path="msisdnLvl" />
										<form:hidden path="cityCd" />
										
										<form:hidden path="channelCd" />
										<form:hidden path="msisdnStatus" />
										<form:hidden path="srvNumType" />		
										<form:hidden path="numType" />	
										
										<form:hidden path="shopCd" />				
									</td>							
									<td>
										<div style="float: right"><input type="submit" id="confirm" name="confirm"
										value="Confirm" /> <input type="button"
										id="close" name="close" value="Close" onclick="formClose()" />
										</div>
								</td>
								</tr>
								<tr>
								<td></td>
								<td>
									<form:errors path="msisdn" cssClass="error" />
									<form:errors path="channelCd" cssClass="error" />
									<form:errors path="shopCd" cssClass="error" />
									<form:errors path="msisdnStatus" cssClass="error" />
									<form:errors path="numType" cssClass="error" />
								</td>
								</tr>
					</table>
				</td>
			</tr>
		</table>	
		</c:when>	
			<c:otherwise>
			Invalid function. Please close the tab.
			</c:otherwise>
		</c:choose>
	</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>