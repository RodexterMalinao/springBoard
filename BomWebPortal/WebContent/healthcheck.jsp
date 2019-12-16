<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Health Check</title>
</head>
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script src="js/json2.min.js"></script>
<script>
	$(document).ready(function() {
		testConnSbDb('98777777');
		testConnCS('V123456(0)');
		var currentdate = new Date($.now());
		
		$("#time").html("<span>" + currentdate + "</span>");
		$("#sbdb").click(function(event) {
			var tel=$("#tel").val();
			console.log(tel);
			testConnSbDb(tel);
			event.preventDefault();
		});
		
		$("#cs").click(function(event) {
			var idnum=$("#csInput").val();
			console.log(idnum);
			testConnCS(idnum);
			event.preventDefault();
		});
		
		$("#currDno").click(function(event) {
			var currDnoMsisdn=$("#currDnoMsisdn").val();
			console.log(currDnoMsisdn);
			getCurrDno(currDnoMsisdn);
			event.preventDefault();
		});
		
		$("#checkCNM").click(function(event) {
			var checkCNMMsisdn=$("#checkCNMMsisdn").val();
			console.log(checkCNMMsisdn);
			checkCentralNumPoolMsisdn(checkCNMMsisdn);
			event.preventDefault();
		});
		
		$("#checkNoChannel").click(function(event) {
			var checkNoChannelMsisdn=$("#checkNoChannelMsisdn").val();
			console.log(checkNoChannelMsisdn);
			checkNoChannel(checkNoChannelMsisdn);
			event.preventDefault();
		});
		
		$("#checkCInv").click(function(event) {
			var checkCInvMsisdn=$("#checkCInvMsisdn").val();
			console.log(checkCInvMsisdn);
			checkNoStatusInCInv(checkCInvMsisdn);
			event.preventDefault();
		});
		
			$("#verifyStudentPlanCard").click(function(event) {
			var idType=$("#studentPlanIdDocType").val();
			var idnum=$("#studentPlanIdDocNum").val();
			var fullPan=$("#studentPlanFullPan").val();
			verifyStudentPlanCard(idType,idnum,fullPan);
			event.preventDefault();
		});
			
		$("#mnpMsisdnSubmit").click(function(event) {
			var msisdn=$("#mnpMsisdn").val();
			getMnpCardInformation(msisdn);
			event.preventDefault();
		});
			
			$("#buttonClear").click(function(event) {
				$("textarea#status").val("");
			});
		
	});
	
	function testConnSbDb(tel) {
		  $.ajax({
				url: "mnp/healthchecksbdb.html?tel=" + tel,
		        type: "GET",
		        success: function(data) {
		        	var result =jQuery.parseJSON(data);
		        	if (result.result == "Fail"){
		        		showResult(result.error);
		        		showStatus("sbdbStatus",result.result);
		        	}
		        	else{
		        	showStatus("sbdbStatus",result.result);
					showResult(result.input);
		        	}
		        }
		    }); 
	}
	
	function testConnCS(idnum) {
		  $.ajax({
				url: "mnp/healthcheckcsportal.html?id=" + idnum,
		        type: "GET",
		        success: function(data) {
		        	var result = jQuery.parseJSON(data);
		        	if (result.result == "Fail"){
		        		showResult(result.error);
		        		showStatus("csStatus",result.result);
		        	}else{
		        		showStatus("csStatus",result.result);
						showResult(result.input);
		        	}
/* 		        	$("#csStatus").empty(); 
					$("#csStatus").append(result.result); 
					showResult(result.input); */
		        }
		    }); 
			
	}
	
	
	function getCurrDno(msisdn) {
		  $.ajax({
				url: "mnp/getcurrdno.html?msisdn=" + msisdn,
		        type: "GET",
		        success: function(data) {
		        	var result = jQuery.parseJSON(data);
		        	if (result.result == "Fail"){
		        		showResult(result.error);
		        		showStatus("currDnoStatus",result.result);
		        	}else{
		        		showStatus("currDnoStatus",result.result);
						showResult(result.input);
		        	}
/* 		        	$("#csStatus").empty(); 
					$("#csStatus").append(result.result); 
					showResult(result.input); */
		        }
		    }); 
			
	}
	
	function checkCentralNumPoolMsisdn(msisdn) {
		  $.ajax({
				url: "mnp/checkcentralnumpoolmsisdn.html?msisdn=" + msisdn,
		        type: "GET",
		        success: function(data) {
		        	var result = jQuery.parseJSON(data);
		        	if (result.result == "Fail"){
		        		showResult(result.error);
		        		showStatus("checkCNMStatus",result.result);
		        	}else{
		        		showStatus("checkCNMStatus",result.result);
						showResult(result.input);
		        	}
/* 		        	$("#csStatus").empty(); 
					$("#csStatus").append(result.result); 
					showResult(result.input); */
		        }
		    }); 
			
	}
	
	function checkNoChannel(msisdn) {
		  $.ajax({
				url: "mnp/checknochannel.html?msisdn=" + msisdn,
		        type: "GET",
		        success: function(data) {
		        	var result = jQuery.parseJSON(data);
		        	if (result.result == "Fail"){
		        		showResult(result.error);
		        		showStatus("checkNoChannelStatus",result.result);
		        	}else{
		        		showStatus("checkNoChannelStatus",result.result);
						showResult(result.input);
		        	}
/* 		        	$("#csStatus").empty(); 
					$("#csStatus").append(result.result); 
					showResult(result.input); */
		        }
		    }); 
			
	}
	
	
	function checkNoStatusInCInv(msisdn) {
		  $.ajax({
				url: "mnp/checknostatusincinv.html?msisdn=" + msisdn,
		        type: "GET",
		        success: function(data) {
		        	var result = jQuery.parseJSON(data);
		        	if (result.result == "Fail"){
		        		showResult(result.error);
		        		showStatus("checkCInvStatus",result.result);
		        	}else{
		        		showStatus("checkCInvStatus",result.result);
						showResult(result.input);
		        	}
/* 		        	$("#csStatus").empty(); 
					$("#csStatus").append(result.result); 
					showResult(result.input); */
		        }
		    }); 
			
	}
	
	function verifyStudentPlanCard(idDocType,idDocNum,fullPan) {
		  $.ajax({
				url: "studentplan/verifystudentplancard.html?idDocType=" + idDocType+"&idDocNum=" + idDocNum+"&fullPan="+fullPan,
		        type: "GET",
		        success: function(data) {
		        	var result = jQuery.parseJSON(data);
		        	if (result.result == "Fail"){
		        		showResult(result.error);
		        		showStatus("verifyStudentPlanCardStatus",result.result);
		        	}else{
		        		showStatus("verifyStudentPlanCardStatus",result.result);
						showResult(result.input);
		        	}

		        }
		    }); 
			
	}
	
	function getMnpCardInformation(msisdn) {
		  $.ajax({
				url: "mnp/getCardInformation.html?msisdn=" + msisdn,
		        type: "GET",
		        success: function(data) {
		        	var result = jQuery.parseJSON(data);
		        	if (result.result == "Fail"){
		        		showResult(result.error);
		        		showStatus("getCardInformationStatus",result.result);
		        	}else{
		        		showStatus("getCardInformationStatus",result.result);
						showResult(result.input);
		        	}

		        }
		    }); 
			
	}
	
	
	function showResult(message){
		$("textarea#status").val(message);
	}
	
	function showStatus(id,status){
		$('#'+id).empty();
		if (status == "Fail"){
			$('#'+id).append('<font color="red" size="bold">'+status+'</font>'); 
		}else{
		$('#'+id).append(status);
		}
	}
</script>
<body>
<form>
  <div id="topborder">Health Check at <div id = "time"></div></div>
<input type="button" value = "Create New Order" onclick="window.open('./customerprofile.html?_al=new', '_blank');"></input>
  <hr>
	

		<table border="1" style="width: 80%;">

			<tr>
				<td><strong>Desc</strong></td>
				<td>Input</td>
				<td>Button</td>
				<td>Status/Message</td>
				<td>Remarks</td>
			</tr>
		
			<!-- sbdb testing  -->
			<tr>
				<td><strong>SB DB</strong>(OrderDAO.getOrderIdUsingSameMRT)</td>
				<td><input type="text" id="tel" value="98777777" /></td>
				<td><input type="button" id="sbdb" value="Test" /></td>
				<td><div id="sbdbStatus"></div>
				</td>
				<td></td>
			</tr>
			
			<!-- Cs Portal check  -->
			<tr>
				<td><strong>Cs Portal check </strong>()</td>
				<td><input type="text" id="csInput" value="V123456(0)" />				
				</td>
				<td><input type="button" id="cs" value="Test" /></td>
				<td><div id="csStatus"></div>
				</td>
			<td>csp.idck.url | CsPortalService.idCheck()</td>
			</tr>	
			
			<!-- Get CurrDno  -->
			<tr>
				<td><strong>GetCurrDNO </strong>(msisdn)</td>
				<td><input type="text" id="currDnoMsisdn" value="" />				
				</td>
				<td><input type="button" id="currDno" value="Test" /></td>
				<td><div id="currDnoStatus"></div>
				</td>
				<td>ws.mvnoWsdl | mvnoWSClient.getCurrDNO()</td>
			</tr>	
			
			<!--checkCentralNumPoolMsisdn-->
			<tr>
				<td><strong>checkCentralNumPoolMsisdn </strong>(msisdn)</td>
				<td><input type="text" id="checkCNMMsisdn" value="" />				
				</td>
				<td><input type="button" id="checkCNM" value="Test" /></td>
				<td><div id="checkCNMStatus"></div>
				</td>
				<td>ws.cnmClient | cnmClient.checkCentralNumPoolMsisdnMIP()</td>
			</tr>
			
			<!--checkNoChannel-->
			<tr>
				<td><strong>checkNoChannel </strong>(msisdn)</td>
				<td><input type="text" id="checkNoChannelMsisdn" value="" />				
				</td>
				<td><input type="button" id="checkNoChannel" value="Test" /></td>
				<td><div id="checkNoChannelStatus"></div>
				</td>
				<td>ws.mvnoWsdl | mvnoWSClient.checkNoChannel()</td>
			</tr>
			
			<!--checkNoStatusInCInv-->
			<tr>
				<td><strong>checkNoStatusInCInv </strong>(msisdn)</td>
				<td><input type="text" id="checkCInvMsisdn" value="" />				
				</td>
				<td><input type="button" id="checkCInv" value="Test" /></td>
				<td><div id="checkCInvStatus"></div>
				</td>
				<td>ws.mvnoWsdl | mvnoWSClient.checkNoStatusInCInv()</td>
			</tr>	
			
			<!-- Get CurrDno  -->
			<tr>
				<td><strong>verifyStudentPlanCard </strong></td>
				<td>ID Doc Type<select id="studentPlanIdDocType">
				            		<option value="HKID">HKID</option>
				            		<option value="PASS">Passport</option>
				            		<option value="BS">HKBR</option>			         	 	
								</select>
				ID Doc Num<input type="text" id="studentPlanIdDocNum" value="" />
				
				Full Pan<input type="text" id="studentPlanFullPan" value="" />				
				</td>
				<td><input type="button" id="verifyStudentPlanCard" value="Test" /></td>
				<td><div id="verifyStudentPlanCardStatus"></div>
				</td>
			</tr>	
			
			<!-- MNP Get Card Information -->
			<tr>
				<td><strong>Mnp Get Card Information (msisdn)</strong></td>
				<td>
				<input type="text" id="mnpMsisdn" value="" />				
				</td>
				<td><input type="button" id="mnpMsisdnSubmit" value="Test" /></td>
				<td><div id="getCardInformationStatus"></div>
				</td>
			</tr>	
		</table>
		
		<hr>
		<textarea id="status" style="width: 80%; height: 100px;" readonly></textarea>
		<br>
		<input type="button" id = "buttonClear" value="Clear"/>

</form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>