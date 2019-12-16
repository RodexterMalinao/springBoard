<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script>




$(document).ready(function() {
	
// 	document.orderSearchForm.idDocNum.onkeypress = enterKeyDown;
// 	document.orderSearchForm.idDocType.onkeypress = enterKeyDown;
// 	document.orderSearchForm.idDocNum.onkeypress = enterKeyDown;
// 	document.orderSearchForm.serviceNumberType.onkeypress = enterKeyDown;
// 	document.orderSearchForm.serviceNumber.onkeypress = enterKeyDown;
// 	document.orderSearchForm.loginIdPrefix.onkeypress = enterKeyDown;
// 	document.orderSearchForm.loginIdSuffix.onkeypress = enterKeyDown;
// 	document.orderSearchForm.contactEmail.onkeypress = enterKeyDown;
// 	document.orderSearchForm.orderId.onkeypress = enterKeyDown;
	
	if (${sboordersearch ne null}) {
		// temporary ..avoid search all in other lob if only contactNum is inputed..
		if (${empty sboordersearch.idDocNum && empty sboordersearch.serviceNumber && empty sboordersearch.contactEmail && empty sboordersearch.orderId && empty sboordersearch.loginIdPrefix}) {
			searchMob();
			$('#imsOrderSearchResult').html('<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;"><center>No record</center>');
			$('#ltsOrderSearchResult').html('<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;"><center>No record</center>');
			$('#imsOrderSearchResult_NTV').html('<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;"><center>No record</center>');
			if (${not empty sboordersearch.contactNum}) {
				searchIms();
				searchIms_NTV();
			}
		} else {
			searchIms();
			searchMob();
			searchLts();
			searchIms_NTV();	
		}


	}
});


// function enterKeyDown (e){
//     if (!e) e = window.event;   // resolve event instance
//     if (e.keyCode == '13'){
//     	searchSubmit();
//     }
// }

function searchSubmit() {
	/*Remove previous display result*/
	$('#imsOrderSearchResult').html('');
	$('#mobOrderSearchResult').html('');
	$('#ltsOrderSearchResult').html('');
	$('#imsOrderSearchResult_NTV').html('');
	document.orderSearchForm.action.value = "SEARCH";
	document.orderSearchForm.submit();
}
function searchClear() {
	document.orderSearchForm.idDocType.value = "";
	document.orderSearchForm.idDocNum.value = "";
	document.orderSearchForm.serviceNumberType.value = "";
	document.orderSearchForm.serviceNumber.value = "";
	document.orderSearchForm.loginIdPrefix.value = "";
	document.orderSearchForm.loginIdSuffix.value = "";
	document.orderSearchForm.contactEmail.value = "";
	document.orderSearchForm.contactNum.value = "";
	document.orderSearchForm.action.value = "CLEAR";
	//document.orderSearchForm.submit();
	window.location.href="sboordersearch.html";
}


function searchMob() {
	//cursor changes to wait
	document.body.style.cursor = 'wait';
	$('#mobOrderSearchResult').html('<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;"><center>Loading</center>');
	//finish loading, cursor changes back to auto
	$("#mobOrderSearchResult").load("sbomobordersearch.html?_t="+ (new Date().getTime()), 0, function() {
		document.body.style.cursor = 'auto';
	});
}

function searchIms() {
	$('#imsOrderSearchResult').html('<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;"><center>Loading</center>');
	$("#imsOrderSearchResult").load("sboimsordersearch.html?_t="+ (new Date().getTime()) + "&app=PCD" );
}

function searchLts() {
	$("#ltsOrderSearchResult").load("sboltsordersearch.html?_t="+ (new Date().getTime()));
}

function searchIms_NTV() {
	$('#imsOrderSearchResult_NTV').html('<div class="overflowDiv orderSearchResult" style="margin-top:5px; min-height: 80px;"><center>Loading</center>');
	$("#imsOrderSearchResult_NTV").load("sboimsordersearch.html?_t="+ (new Date().getTime())+ "&app=NTV");
}

function keyUpOnDocNum(){		
	document.getElementById('idDocNum').value = document.getElementById('idDocNum').value.toUpperCase();
}

</script>

<style type="text/css">
	.orderSearch { background-color: white; border: solid 2px #C0C0C0 }
	.orderSearchButton { text-align: right; padding: 0 0.5em 0.5em 0 }
	.orderSearchLabel { display: inline-block; width: 90px }
	.overflowDiv { overflow-x: auto; overflow-y: auto; max-height: 320px; display: inline-block; width: 100% }
	.orderSearchResult { background-color: white }
	
	.searchColHalf { overflow: hidden; width: 48%; display: inline-block; float: left  }
	.searchRow { padding: 15px 10px }
	.label { display: inline-block; width: 85px; font-size: 12px; font-family: "Verdana", "Helvetica", "Arial", "sans-serif" }
	.labelFirst { width: 110px }
	.input { display: inline-block; width: 120px }
	.even { background-color: #E8FFE8 }
	.previewForm { background-color: white; border: 2px solid #BEBEBE; padding: 2px; margin-left: 1px; font-family: "Helvetica", "Arial", "sans-serif" }
	.previewForm h3 { margin: 0; font-size: medium; color: white; background-color: #ABD078 !important; padding: 5px 10px; position: relative }
	.inputGroup { display: inline-block; overflow: hidden; vertical-align: middle }
	
</style>

<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.orderSearch { padding-left: 0 }
.orderSearchResult { padding-bottom: 1em }
</style>
<![endif]-->

<form:form name="orderSearchForm" method="POST" commandName="sboOrderSearch">
<form:hidden path="action"/>
<div class="orderSearch">
	<h2 class="table_title" style="font-size:medium;margin:0">SBO Order Enquiry</h2>
	
	<div class="row searchRow">
		<div class="searchColHalf">
			<span class="label labelFirst">ID Doc Num:</span>
			<span class="inputGroup">
				<form:select path="idDocType">

					<form:option value="" label="Select" />
					<form:options items="${idDocTypeList}" />
				</form:select>
				<form:input path="idDocNum" maxlength="20"  onkeyup="keyUpOnDocNum()" />
			</span>
		</div>
	
		<span class="inputGroup">
			<span class="label labelFirst" style="width: 150px;">Contact Email Address:</span>
			<form:input path="contactEmail" maxlength="40" />

		</span>
		<div style="clear:both"></div>
	</div>
	
	<div class="row searchRow">
		<div class="searchColHalf">
		<span class="label labelFirst">Service Number:</span>
		<span class="inputGroup">
	
			<form:select path="serviceNumberType">
				<form:option value="" label="Select" />
				<form:options items="${serviceNumberTypeList}" />
			</form:select>
			<form:input path="serviceNumber" maxlength="20"/>
		</span>
		</div>
		<span class="inputGroup">
			<span class="label">Order ID:</span>
			<form:input path="orderId" maxlength="20"/>
		</span>
		<div style="clear:both"></div>
	</div>
	<div class="row searchRow">	
		<div class="searchColHalf">
		<span class="label labelFirst">Login ID:</span>
		<span class="inputGroup">
			<form:input path="loginIdPrefix" /> 
			<form:select path="loginIdSuffix">
				<form:option value="" label="Select" />
				<form:options items="${loginIdSuffixList}" />
			</form:select>
		</span>
		</div>
		<span class="inputGroup">
			<span class="label">Contact Number:</span>
			<form:input path="contactNum" maxlength="20"/>
		</span>
		<div style="clear:both"></div>
	</div>

<div class="searchError">
	<form:errors path="*" cssClass="error" />
	<!-- 
	<form:errors path="idDocType" cssClass="error" /> 
	<form:errors path="idDocNum" cssClass="error" />
	<form:errors path="serviceNumberType" cssClass="error" /> 
	<form:errors path="serviceNumber" cssClass="error" /> 
	<form:errors path="loginIdSuffix" cssClass="error" /> 
	<form:errors path="loginIdPrefix" cssClass="error" />
	 -->
	<div style="clear:both"></div>
</div>



	<div class="orderSearchButton">
		<input type="button" value="Search" id="searchButton" onClick="javascript:searchSubmit();">
		<input type="button" value="Clear" onClick="javascript:searchClear();">
	</div>
	<div style="clear:both"></div>
</div>



<input type="hidden" id="index" name="index" value=""/> 

</form:form>

<!-- div class="overflowDiv orderSearchResult" style="margin-top:5px" -->

<div style="height: 50px; clear: both;"></div>

<!-- IMS Order Search -->
<div class="orderSearch" style="min-height: 80px; background-color: white;">
<h2 class="table_title" style="font-size:medium;margin:0">IMS Orders</h2>
<div id="imsOrderSearchResult"></div>
</div>

<div style="height: 50px; clear: both;"></div>

<!-- MOB Order Search -->
<div class="orderSearch" style="min-height: 80px; background-color: white;">
<h2 class="table_title" style="font-size:medium;margin:0">MOB Orders</h2>
<div id="mobOrderSearchResult"></div>
</div>

<div style="height: 50px; clear: both;"></div>

<!-- LTS Order Search -->
<div class="orderSearch" style="min-height: 80px; background-color: white;">
<h2 class="table_title" style="font-size:medium;margin:0">LTS Orders</h2>
<div id="ltsOrderSearchResult"></div>
</div>

<div style="height: 50px; clear: both;"></div>

<!-- IMS Order Search -->
<div class="orderSearch" style="min-height: 80px; background-color: white;">
<h2 class="table_title" style="font-size:medium;margin:0">Now TV Orders</h2>
<div id="imsOrderSearchResult_NTV"></div>
</div>

