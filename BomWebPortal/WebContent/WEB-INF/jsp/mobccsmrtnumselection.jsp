<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Springboard</title>
<link href="css/ssc.css" rel="stylesheet" type="text/css" />
<link href="css/ssc2.css" rel="stylesheet" type="text/css" />
<link href="css/imsds.css" rel="stylesheet" type="text/css" /> 
<link href="css/typographyEN.css" rel="stylesheet" type="text/css" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="js/common.js" language="javascript"></script>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
			response.setHeader("Pragma", "no-cache"); //HTTP 1.0
			response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<%@ page
	import="org.springframework.web.servlet.support.RequestContextUtils,com.bomwebportal.dto.BomSalesUserDTO,java.util.Locale"%>
<%@ include file="loadingpanel.jsp"%>

<style type="text/css">
.goldenSearchResultTable {
	width: 100%;
	border-collapse: separate;
	border-width: 0px;
	border-spacing: 0px;
}

.goldenSearchResultTable tr {
	padding: 3px 0
}

.selectResult {
	width: 100%;
	height: 300px;
	overflow-y: auto;
}

.even {
	background-color: rgb(232, 255, 232);
}

.searchTitle {
	text-align: left;
}

.blueHeader {
	background-color: rgb(232, 242, 254)
}
</style>

<script language="javascript">
	function updatePaging(tableClass) {
		var $trs = $("table." + tableClass + " tbody tr");
		var $control = $("div." + tableClass);
		$control.find(".control").show().data("index", 0);
		$control.find(".control .left a").hide();
		if ($trs.length > 10) {
			$control.find(".control .right a").show();
		} else {
			$control.find(".control .right a").hide();
		}
		$control.find(".control .label").text("1 - " + $trs.filter(":visible").length + " / " + $trs.length);
		$control.find(".control .left a").unbind("click").click(function(e) {
			e.preventDefault();
			$trs.hide();
			var start = $control.find(".control").data("index") - 10;
			if (start < 0) {
				start = 0;
			}
			var end = start + 10;
			if (end > $trs.length) {
				end = $trs.length;
			}
			if (start > 0) {
				$control.find(".control .left a").show();
			} else {
				$control.find(".control .left a").hide();
			}
			if (end < $trs.length) {
				$control.find(".control .right a").show();
			} else {
				$control.find(".control .right a").hide();
			}
			$trs.slice(start, end).show();
			$control.find(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
			$control.find(".control").data("index", start);
			return false;
		});
		$control.find(".control .right a").unbind("click").click(function(e) {
			e.preventDefault();
			$trs.hide();
			var start = $control.find(".control").data("index") + 10;
			if (start > $trs.length) {
				start = $trs.length;
			}
			var end = start + 10;
			if (end > $trs.length) {
				end = $trs.length;
			}
			if (start > 0) {
				$control.find(".control .left a").show();
			} else {
				$control.find(".control .left a").hide();
			}
			if (end < $trs.length) {
				$control.find(".control .right a").show();
			} else {
				$control.find(".control .right a").hide();
			}
			$trs.slice(start, end).show();
			$control.find(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
			$control.find(".control").data("index", start);
			return false;
		});
	}

	function formSubmit() {

		if ($('#mrtType').val() == 'newMRT') {
			if($("input[name='mrtSelect']:checked").val()=='pattern'){
				var temp = $('input:radio[name=patternNumSearchSelect]').filter(":checked");
				opener.document.mobCcsMRTForm.mobMsisdn.value =temp.val();
				opener.document.mobCcsMRTForm.msisdnLvl.value =temp.data('msisdnlvl');
			}else{
				opener.document.mobCcsMRTForm.mobMsisdn.value =$('#msisdn').val();
				opener.document.mobCcsMRTForm.msisdnLvl.value = $('#msisdnlvl').val();
			}
		} else if ($('#mrtType').val() == 'unicom') {
			opener.document.mobCcsMRTForm.cityCd.value = $('#cityCdSelect').val();
			opener.document.mobCcsMRTForm.unicomMobMsisdn.value = $('#unicomMobMsisdnSelect').val();
			opener.document.mobCcsMRTForm.unicomMobGrade.value = $('#unicomMobGrade').val();
			opener.document.mobCcsMRTForm.specialNumInd.value = false;
		} else if ($('#mrtType').val() == 'goldenMRT') {
			if($("input[name='goldenSelect']:checked").val() == "random"){
				opener.document.mobCcsMRTForm.mobMsisdn.value = $('#goldenRandomSelect').val();
				opener.document.mobCcsMRTForm.msisdnLvl.value = $('#goldenNumGrade').val();
				opener.document.mobCcsMRTForm.specialNumInd.value = false;
			}else if($("input[name='goldenSelect']:checked").val() == "search"){
				opener.document.mobCcsMRTForm.mobMsisdn.value = $("input[name='goldenNumSearchSelect']:checked").data("msisdn");
				opener.document.mobCcsMRTForm.msisdnLvl.value = $("input[name='goldenNumSearchSelect']:checked").data("msisdnlvl");
				opener.document.mobCcsMRTForm.specialNumInd.value = false;
			}
		}else if ($('#mrtType').val() == 'specialMRT') {
			opener.document.mobCcsMRTForm.mobMsisdn.value = $('#msisdn').val();
			opener.document.mobCcsMRTForm.msisdnLvl.value = $('#msisdnlvl').val();
			opener.document.mobCcsMRTForm.specialNumInd.value = true;
		}

		self.close();

	}
	

	function patternSearch() {
		var str= $("#patternMrtSearchText").val();
		if(str.replace(/[^0-9]/g, '').length >= 2 && str.replace(/[^0-9]/g, '').length <= 8){

			var rdiv = $("#patternResultDiv");
			rdiv.html("Loading...");
			$.ajax({
				url : "mobccsmrtajax.html",
				data : {
					type : "patternMRTSearchCcs",
					searchMsisdn : $("#patternMrtSearchText").val()
				},
				cache : false,
				dataType : "json",
				success : function(data) {
					if (!data || data.length == 0) {
						rdiv.html("No result");
					} else {
						rdiv.html("");
						$.each(data, function(index, item) {
							rdiv.html(rdiv.html() + " <input type='radio' class='newNumSelection' id='patternNumSearchSelect'  name='patternNumSearchSelect'  value=" +   item.msisdn + " data-msisdnlvl='" + item.msisdnlvl + "' onclick='selectNewNumber("+ item.msisdn+")'/>" + item.msisdn);
							if (index % 5 == 4) {
								rdiv.html(rdiv.html() + "<br/>");
							}
							
						});
					}
				},
				error : function() {
					rdiv.html("Error found, please retry");
				}
			});
		}else{
			alert("Please input at least 2 valid digit numbers");
		}
	}
	$(function() {
		$("input[name='newNumSelection']").change(function() {
			selectNewNumber($(this).val());
		});
	});
	
	function selectNewNumber(value) {
		$('#patternMrtSearchText').val(value);
		$('#confirm').attr('disabled', false);
	}
	
	
	function formClose() {
		self.close();
	}

	$(document).ready(function() {
		if ($('#mrtType').val() == 'newMRT') {
			$('#confirm').attr('disabled', true);
			$('input[name=mrtSelect]:eq(0)').attr('checked', 'checked');
			radioSelection();
		} else if ($('#mrtType').val() == 'unicom') {
			$('#confirm').attr('disabled', true);
		} else if ($('#mrtType').val() == 'goldenMRT') {
			$('#confirm').attr('disabled', true);
			$('input[name=goldenSelect]:eq(0)').attr('checked', 'checked');
			goldenRadioSelection();
		} else if ($('#mrtType').val() == 'specialMRT') {
			$('#specialNumSearch').attr('disabled', true);
			$('#specialNumBtn').attr('disabled', true);
			$('#approvalSerial').attr('disabled', true);
			$('#confirm').attr('disabled', true);			
		}
	});

	function radioSelection() {

		$('#confirm').attr('disabled', true);

		var selected = $("input[name='mrtSelect']:checked").val();

		if (selected == 'reserve') {
			$('#mobSelect').attr('disabled', false);
			$('#numSearch').attr('disabled', true);
			$('#numValid').attr('disabled', true);			
			$('#mobRandomSelect').attr('disabled', true);
			$('#patternMrtSearchText').attr('disabled', true);
			$('#patternSearchBtn').attr('disabled',true);
			$('.newNumSelection').attr('disabled', true);
		} else if (selected == 'search') {
			$('#mobSelect').attr('disabled', true);
			$('#numSearch').attr('disabled', false);
			$('#numValid').attr('disabled', false);
			$('#mobRandomSelect').attr('disabled', true);
			$('#patternMrtSearchText').attr('disabled', true);
			$('#patternSearchBtn').attr('disabled',true);
			$('.newNumSelection').attr('disabled', true);
		} else if (selected == 'random') {
			$('#mobSelect').attr('disabled', true);
			$('#numSearch').attr('disabled', true);
			$('#numValid').attr('disabled', true);
			$('#mobRandomSelect').attr('disabled', false);
			$('#patternMrtSearchText').attr('disabled', true);
			$('#patternSearchBtn').attr('disabled',true);
			$('.newNumSelection').attr('disabled', true);
		}else if (selected == 'pattern') {
			$('#mobSelect').attr('disabled', true);
			$('#numSearch').attr('disabled', true);
			$('#numValid').attr('disabled', true);
			$('#mobRandomSelect').attr('disabled', true);
			$('#patternMrtSearchText').attr('disabled', false);
			$('#patternSearchBtn').attr('disabled', false);
			$('.newNumSelection').attr('disabled', false);
		}
	}

	function goldenRadioSelection() {

		$('#confirm').attr('disabled', true);

		var selected = $("input[name='goldenSelect']:checked").val();

		if (selected == 'random') {
			$('#goldenRandomSelect').attr('disabled', false);
			$('#goldenNumGrade').attr('disabled', false);
			
			$('#goldenNumSearchText').attr('disabled', true);
			$('#goldenNumSearchBtn').attr('disabled', true);
			document.getElementById('colheadDiv').style.visibility = 'hidden';
			document.getElementById('resultDiv').style.visibility = 'hidden';
			
			$(".goldenSearchResultTableContext:last tbody").empty();
		} else if (selected == 'search') {
			$('#goldenRandomSelect').attr('disabled', true);
			$('#goldenNumGrade').attr('disabled', true);
			
			$('#goldenNumSearchText').attr('disabled', false);
			$('#goldenNumSearchBtn').attr('disabled', false);
			document.getElementById('colheadDiv').style.visibility = 'visible';
			document.getElementById('resultDiv').style.visibility = 'visible';
			
			$(".goldenSearchResultTableContext:last tbody").empty();
		}
	}

	function mrtvalid() {
		
		if ($('#numSearch').val().length == 8) {
			$.ajax({
				url : 'mobccsmrtajax.html',
				type : 'POST',
				cache : false,
				data : {
					type : 'NewMrtSearch',
					msisdn : $('#numSearch').val()
				},
				dataType : 'JSON',
				error : function() {
					alert('Error Search PCCW 3G Mrt Number and Level!');
				},
				success : function(msg) {
					$.each(eval(msg), function(index, item) {
						if (item.result == 'success') {
							getValidMrtInfo();							
						} else if (item.result == 'fail') {
							alert('Number is invalid');
							$('#confirm').attr('disabled', true);
						}
					});
				}
			});	
		} else {
			alert('Please input 8 digits.');
		}
	}
	
	function superValid(){
		
		if ($('#superLogin').val().length != 0 && $('#superPassword').val().length != 0) {
			$.ajax({
				url : 'mobccsmrtajax.html',
				type : 'POST',
				cache : false,
				data : {
					type : 'superValid',
					login : $('#superLogin').val(),
					password : $('#superPassword').val()
				},
				dataType : 'JSON',
				error : function() {
					alert('Error validate supervisor!');
				},
				success : function(msg) {
					$.each(eval(msg), function(index, item) {
						if (item.valid == 'true') {
							$('#specialNumSearch').attr('disabled', false);
							$('#approvalSerial').attr('disabled', false);
							$('#specialNumBtn').attr('disabled', false);		
						} else if (item.valid == 'false') {
							alert('Supervisor is invalid');
							$('#specialNumSearch').attr('disabled', true);
							$('#specialNumBtn').attr('disabled', true);		
						}
					});
				}
			});	
		} else {
			alert('Please input Login and Password.');
		}
	}
	
	function getValidMrtInfo(){
		$.ajax({
			url : 'mobccsmrtajax.html',
			type : 'POST',
			cache : false,
			data : {
				type : 'GetRandomMrtLvl',
				msisdn : $('#numSearch').val()
			},
			dataType : 'JSON',
			error : function() {
				alert('Error loading PCCW 3G Mrt Level!');
			},
			success : function(msg) {
				$('#msisdn').val("");
				$('#msisdnlvl').val("");
				$.each(eval(msg),
						function(i, item) {							
							$('#msisdn').val($('#numSearch').val());
							$('#msisdnlvl').val(item.msisdnLvl);
						});
				$('#confirm').attr('disabled', false);
			}
		});
	}
	
	function getSpecialNum(){
		
		if($('#specialNumSearch').val().length == 8){
			$.ajax({
				url : 'mobccsmrtajax.html',
				type : 'POST',
				cache : false,
				data : {
					type : 'GetSpecialNum',
					msisdn : $('#specialNumSearch').val(),
					approvalSerial : $('#approvalSerial').val()
				},
				dataType : 'JSON',
				error : function() {
					alert('Error loading PCCW 3G Special Number!');
				},
				success : function(msg) {
					$('#msisdn').val("");
					$('#msisdnlvl').val("");
					$.each(eval(msg),
						function(i, item) {
							if(item.msisdnLvl != 'empty'){
								$('#msisdn').val($('#specialNumSearch').val());
								$('#msisdnlvl').val(item.msisdnLvl);
								$('#confirm').attr('disabled', false);
							}else{
								alert("Cannot find special number");
								$('#confirm').attr('disabled', true);
							}
							
						});
					
				}
			});
		}else{
			alert("Please input number for search!");
			$('#confirm').attr('disabled', true);
		}
	}

	function loadReserveNumLvl() {
		
		if ("Select" != $('#mobSelect').val()) {
			var temp = $('select#mobSelect option:selected');
	 		$('#msisdn').val(temp.val());
			$('#msisdnlvl').val(temp.data('msisdnlvl'));
			$('#confirm').attr('disabled', false);
		}
	}

 	function loadRandomNumLvl() { 		
 	
 		if ("Select" != $('#mobRandomSelect').val()) {
	 		var temp = $('select#mobRandomSelect option:selected');	 		
	 		$('#msisdn').val(temp.val());
			$('#msisdnlvl').val(temp.data('msisdnlvl'));
			$('#confirm').attr('disabled', false);
 		}
 	}

	function loadUnicomMrtMsisdnAndLvl(channelId) {

		if ("Select" != $('#cityCdSelect').val()) {

			$.ajax({
				url : 'mobccsmrtajax.html',
				type : 'POST',
				cache : false,
				data : {
					type : 'PCCWUnicomMrtNum',
					channelId : channelId,
					cityCd : $('#cityCdSelect').val()
				},
				dataType : 'JSON',
				error : function() {
					alert('Error loading Unicom Mrt number!');
				},
				success : function(msg) {					
					$.each(eval(msg), function(i, item) {
						$("<option value='" + item.msisdn + "' data-msisdnlvl='" + item.msisdnlvl + "'>"
										+ item.msisdn + "</option>")
								.appendTo($('#unicomMobMsisdnSelect'));
					});
				}

			});
		}
	}

	function unicomMrtMsisdnOnChange() {

		if ("Select" != $('#unicomMobMsisdnSelect').val()) {

			var temp = $('select#unicomMobMsisdnSelect option:selected');
			$('#unicomMobGrade').val(temp.data("msisdnlvl"));
			$('#confirm').attr('disabled', false);
		} else {
			$('#unicomMobGrade').val("");
			$('#confirm').attr('disabled', true);
		}
	}

	function searchGoldenNum() {
		
		if($("#goldenNumSearchText").val() != ""){

			var $tbody = $(".goldenSearchResultTableContext:last tbody");
			$tbody.empty();
			$tbody.append($("<tr/>").append($("<td/>")).append(
					$("<td/>").attr("colspan", 2).text("Loading...")));
			$.ajax({
				url : "mobccsmrtajax.html",
				data : {
					type : "goldenMRTSearch",
					period : $("#period").val(),
					charge : $("#charge").val(),
					searchMsisdn : $("#goldenNumSearchText").val(),
					grossPlanFee : $("#grossPlanFee").val(),
					appDate : $("#appDate").val()
				},
				cache : false,
				dataType : "json",
				success : function(data) {
					if (!data || data.length == 0) {
						$tbody.empty();
						$tbody.append($("<tr/>").append($("<td/>")).append(
								$("<td/>").attr("colspan", 2).text("No result")));
						$('#confirm').attr('disabled', true);
					} else {
						$tbody.empty();
						var displayLimit = 10;
						$.each(data, function(index, item) {
							var $tr = $("<tr/>");
							if (index % 2 == 0) {
								$tr.addClass("even");
							}
							if (index >= displayLimit) {
								$tr.css("display", "none"); 
							}
							var $td0 = "<td align='center'><input type='radio' id='goldenNumSearchSelect' name='goldenNumSearchSelect' value=" + index + " data-msisdn='" + item.msisdn + "' data-msisdnlvl='" + item.msisdnlvl + "' onClick='goldenMRTSearchResultRadioOnClick()'/></td>";
							var $td1 = $("<td/>").attr("align", "center").text(item.msisdn);
							var $td2 = $("<td/>").attr("align", "center").text(item.msisdnlvl);
							$tbody.append($tr.append($td0).append($td1).append($td2));
						});
						updatePaging("goldenSearchResultTable");
					}
				},
				error : function() {
					$tbody.empty();
					$tbody.append($("<tr/>").append($("<td/>")).append($("<td/>").attr("colspan", 2).text(
									"Error found, please retry")));
				}
			});
		}else{
			alert("Please input searching criteria");
		}
	}
	function goldenRadioSelection() {

		if (selected == 'search') {
			$('#goldenRandomSelect').attr('disabled', true);
			$('#goldenNumGrade').attr('disabled', true);
			
			$('#goldenNumSearchText').attr('disabled', false);
			$('#goldenNumSearchBtn').attr('disabled', false);
			document.getElementById('colheadDiv').style.visibility = 'visible';
			document.getElementById('resultDiv').style.visibility = 'visible';
			
			$(".goldenSearchResultTableContext:last tbody").empty();
		}
	}
	
	function goldenMRTRandomSelectOnchange(){
		if($('#goldenRandomSelect').val() == 'Select'){
			$('#goldenNumGrade').val("");
		}else{
			var temp = $('select#goldenRandomSelect option:selected');
			$('#goldenNumGrade').val(temp.data('msisdnlvl'));
			$('#confirm').attr('disabled', false);
		}
	}
	
	function goldenMRTSearchResultRadioOnClick(){
		$('#confirm').attr('disabled', false);
	}
	
	function disableConfirm(){
		$('#confirm').attr('disabled', true);
	}
</script>

</head>

<body>
	<form:form name="mobCcsMrtNumSelectionForm" method="POST">

		<table width="100%" class="tablegrey" background="images/background2.jpg">
			<tr>
				<c:choose>
					<c:when test="${mrtType == 'newMRT'}">
						<td class="table_title">Normal Mobile Number Search</td>
					</c:when>
					<c:when test="${mrtType == 'unicom'}">
						<td class="table_title">China Mobile Number Search</td>
					</c:when>
					<c:when test="${mrtType == 'goldenMRT'}">
						<td class="table_title">Golden Mobile Number Search</td>
					</c:when>
					<c:when test="${mrtType == 'specialMRT'}">
						<td class="table_title">Special Mobile Number Search</td>
					</c:when>
				</c:choose>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="1" class="contenttext">
						<c:choose>
							<c:when test="${mrtType == 'newMRT'}">
								<tr>
									<td><input type="radio" id="mrtSelect" name="mrtSelect"
										value="reserve" onclick="radioSelection()" />Reserve Number</td>
									<td>
									<div>
									<select name="mobSelect" id="mobSelect" onchange="loadReserveNumLvl()">
											<option value="Select" selected='selected'>-- Select --</option>
											<c:forEach var="mob3GNumList" items="${mobNumList}">
												<option value="${mob3GNumList[0]}" data-msisdnlvl="${mob3GNumList[1]}">${mob3GNumList[0]}(${mob3GNumList[1]}/${mob3GNumList[2]}/${mob3GNumList[3]})</option>
											</c:forEach>
									</select>
									
									<span style="float:right" class="contenttext_red"><b>(
									<label for="numTypeLabel">
									<pccw-util:codelookup codeType="MIP_NUM_TYPE" codeId="${customer.numType}"/>							
											& <pccw-util:codelookup codeType="MIP_SIM_TYPE" codeId="${customer.simType}" />									
									</label>								
									)</b></span>
									</div>
									</td>
									
								</tr>
								<tr>
									<td><input type="radio" id="mrtSelect" name="mrtSelect"
										value="search" onclick="radioSelection()" />MRT Search</td>
									<td><input type="text" id="numSearch" name="numSearch" onkeypress="disableConfirm()"
										maxlength="8" /> <input type="button" id="numValid"
										name="numValid" onclick="mrtvalid()" value="Valid" /></td>
									
								</tr>
							
								<tr>
									<td><input type="radio" id="mrtSelect" name="mrtSelect" 
										value="pattern" onclick="radioSelection()"/>Search MRT by pattern</td>
									<td>Pattern <input type="text" id="patternMrtSearchText" name="patternMrtSearchText" onkeypress="disableConfirm()"
										maxlength="8" /> <input type="button" id="patternSearchBtn"
										name="patternSearchBtn" onclick="patternSearch()" value="Search" /></td>
								</tr>
								<tr><td></td>
									<td colspan="3" bgcolor="#FFFFFF">
										<div id="patternResultDiv">
										</div>
									</td>
								</tr>
								
								<tr>
									<td><input type="radio" id="mrtSelect" name="mrtSelect"
										value="random" onclick="radioSelection()" />Number Pool</td>
									<td><select name="mobRandomSelect" id="mobRandomSelect"	onchange="loadRandomNumLvl()">
											<option value="Select" selected='selected'>-- Select --</option>
											<c:forEach var="mob3GRandomNumList" items="${mobRandomNumList}">
												<option value="${mob3GRandomNumList[0]}" data-msisdnlvl="${mob3GRandomNumList[1]}">${mob3GRandomNumList[0]}(${mob3GRandomNumList[1]})</option>
											</c:forEach>
									</select></td>
								</tr>
							</c:when>
							<c:when test="${mrtType == 'unicom'}">
								<tr>
									<td>City</td>
									<td><select name="cityCdSelect" id="cityCdSelect"
										onchange="loadUnicomMrtMsisdnAndLvl('${channelCd}')">
											<option value="Select" selected='selected'>-- Select --</option>
											<c:forEach var="unicomCityCdList" items="${mobUnicomCityCdList}">
												<option value="${unicomCityCdList}">${unicomCityCdList}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td>Mobile Number</td>
									<td><select name="unicomMobMsisdnSelect" id="unicomMobMsisdnSelect" onchange="unicomMrtMsisdnOnChange()">
											<option value="Select" selected='selected'>-- Select --</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>Mobile Number Grade</td>
									<td><input type="text" id="unicomMobGrade"
										name="unicomMobGrade" readonly="true" /></td>
								</tr>
							</c:when>
							<c:when test="${mrtType == 'goldenMRT'}">
								<tr>
									<td><input type="radio" id="goldenSelect"
										name="goldenSelect" value="random" onclick="goldenRadioSelection()" />Random
										Number</td>
									<td><select name="goldenRandomSelect" id="goldenRandomSelect" onchange="goldenMRTRandomSelectOnchange()">
											<option value="Select" selected='selected'>-- Select --</option>
											<c:forEach var="mobGoldenRandom" items="${mobGoldenRandomList}">
												<option value="${mobGoldenRandom[0]}" data-msisdnlvl="${mobGoldenRandom[1]}">${mobGoldenRandom[0]}</option>
											</c:forEach>
										</select>
										Grade:
										<input type="text" id="goldenNumGrade" name="goldenNumGrade" readonly="true" /></td>
								</tr>
								<tr>
									<td><input type="radio" id="goldenSelect"
										name="goldenSelect" value="search" onclick="goldenRadioSelection()" />MRT
										Search</td>
									<td><input type="text" id="goldenNumSearchText"
										name="goldenNumSearchText" maxlength="8" /> <input
										type="button" id="goldenNumSearchBtn"
										name="goldenNumSearchBtn" onclick="searchGoldenNum()" value="Search" /></td>
								</tr>
								<tr>
									<td colspan="3" bgcolor="#FFFFFF">
										<div id="colheadDiv">
											<table class="goldenSearchResultTable">
												<colgroup width="5%"></colgroup>
												<colgroup width="10%"></colgroup>
												<colgroup width="10%"></colgroup>
												<thead>
													<tr class="blueHeader">
														<th></th>
														<th>Number</th>
														<th>Grade</th>
													</tr>
												</thead>
											</table>
										</div>
										<div class="selectResult" id="resultDiv">
											<table class="goldenSearchResultTable goldenSearchResultTableContext">
												<colgroup width="5%"></colgroup>
												<colgroup width="10%"></colgroup>
												<colgroup width="10%"></colgroup>
												<tbody></tbody>
											</table>
										</div>
										<div style="text-align: center; padding: 5px 0" class="goldenSearchResultTable">
											<span class="control">
												<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
												<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
												<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
											</span>
										</div>
									</td>
								</tr>
							</c:when>
							<c:when test="${mrtType == 'specialMRT'}">								
								<tr>									
									<td>Login:<input type="text" id="superLogin" name="superLogin" />
										Password:<input type="password" id="superPassword" name="superPassword" /> 
										<input type="button" id="supervisorValid" name="supervisorValid" onclick="superValid()" value="Valid" />
									</td>
								</tr>
								<tr>									
									<td>
										Search Special Number:<input type="text" id="specialNumSearch" name="specialNumSearch"
										maxlength="8" /> 
										Approval Serial:<input type="text" id="approvalSerial" name="approvalSerial" maxlength="16" /> 
										<input type="button" id="specialNumBtn"	name="specialNumBtn" onclick="getSpecialNum()" value="Search" />
									</td>
								</tr>
							</c:when>
						</c:choose>
					</table>
				</td>
			</tr>
			<tr>
				<td><input type="button" id="confirm" name="confirm"
					value="Confirm" onclick="formSubmit()" /> <input type="button"
					id="close" name="close" value="Close" onclick="formClose()" /></td>
			</tr>
		</table>

		<input type="hidden" name="mrtType" id="mrtType" value="${mrtType}" />
		<input type="hidden" name="msisdnlvl" id="msisdnlvl" />
		<input type="hidden" name="msisdn" id="msisdn" />
		<input type="hidden" name="period" id="period" value="${period}"/>
		<input type="hidden" name="charge" id="charge" value="${charge}"/>
		<input type="hidden" name="grossPlanFee" id="grossPlanFee" value="${grossPlanFee}"/>
		<input type="hidden" name="appDate" id="appDate" value="${appDate}"/>
	</form:form>
</body>
</html>

