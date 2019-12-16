<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0058)https://sbuat.pccw.com/BomWebPortal/orderamendhistory.html -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--
<link href="css/ssc.css" rel="stylesheet" type="text/css">
<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<link href="css/typographyEN.css" rel="stylesheet" type="text/css">-->
<link href="./css/ssc2.css" rel="stylesheet" type="text/css">
<link type="text/css" href="./css/jquery-ui-1.8.9.custom.css" rel="stylesheet">
<script src="./js/jquery.blockUI.js"></script>
<script type="text/javascript" src="./js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="./js/jquery-ui-1.8.16.custom.min.js"></script>
 <style type="text/css">
.OrderAmendBGgreen2 {
	background-color: #e2f5d3;
	font-family:    "Helvetica","Verdana", "Arial",  "sans-serif",  ·s²Ó©úÅé;
	vertical-align: middle;
	font-size: 12px;
	color: #000000;
		}
.OrderAmendcontenttext {
	font-family:    "Helvetica", "Verdana", "Arial", "sans-serif", ·s²Ó©úÅé;
	vertical-align:top;
	font-size: 12px;
		}
.OrderAmendtable_title {
	color: #FFF;
	background-color: #abd078;
	padding-top: 5px;
	padding-right: 10px;
	padding-bottom: 5px;
	padding-left: 10px;
	width: auto;
	font-weight: bold;
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif", ·s²Ó©úÅé;
}

</style> 
</head>



<%@ include file="imsloadingpanel.jsp"%>
<%@ include file="imsvalidwindow.jsp"%>
<%@ page
	import="com.bomwebportal.bean.LookupTableBean,com.bomwebportal.mob.ccs.dto.CodeLkupDTO,com.bomwebportal.util.ConfigProperties,java.util.*"%>



<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<style type="text/css">
.label {
	display: inline-block;
	font-weight: bold;
	width: 120px
}

.row {
	height: 24px
}

.input {
	display: inline-block
}

.input label {
	display: inline-block;
	width: 50px
}

.previewForm {
	background-color: white;
	border: 2px solid #BEBEBE;
	padding: 2px;
	margin-left: 1px
}

.previewForm h3 {
	margin: 0;
	font-size: medium;
	color: white;
	background-color: #ABD078;
	padding: 5px 10px;
	font-family: "Helvetica", "Verdana", "Arial", "sans-serif"
}

.wrapContent {
	display: inline-block;
	width: 100%;
	padding: 10px 0
}

.previewForm .content {
	margin-left: 2em
}

.previewForm .half {
	width: 40%;
	float: left
}

.header tr {
	background-color: #E8F2FE
}

.even {
	background-color: #E8FFE8
}

.success {
	color: green
}

.fail {
	color: red
}

h2 {
	margin: 0
}

.histList {
	width: 100%;
	clear: both
}

.histList td {
	text-align: center
}
</style>



<script language="Javascript">
	var showArray = new Array();
	function showDetail(historySequence) {
		//event.preventDefault();
		var id = '#s' + historySequence + '';
		if (showArray[historySequence] != true) {
			$(id).show();
			showArray[historySequence] = true;
		} else {
			$(id).hide();
			showArray[historySequence] = false;
		}
	}
</script>



<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />

<form:form name="summaryForm" method="POST" commandName="imsSummaryUI">
<input type="hidden" name="sbuid" value="8939db9f-c618-442b-84d2-293aed7dda19">
	
	
	<table width="100%" border="1" bordercolor="#616161" cellpadding="0" cellspacing="0">
	
	<tbody><tr bgcolor="#FFFFFF">
	<td>
		<table class="OrderAmendcontenttext" width="100%" cellpadding="4" cellspacing="2" bgcolor="white">
			<tbody><tr bgcolor="#99C68E">
				<td width="5%" align="center" valign="middle">
				<b>Sequence</b>
				</td>
				<td width="15%" align="center" valign="middle">
				<b>Sales Channel</b>
				</td>
				<td width="10%" align="center" valign="middle">
				<b>Staff Number</b>
				</td>
				<td width="10%" align="center" valign="middle">
				<b>Shop Code</b>
				</td>
				<td width="15%" align="center" valign="middle">
				<b>Amendment Date</b>
				</td>
				<td width="15%" align="center" valign="middle">
				<b>Amendment Type</b>
				</td>
				<td width="20%" align="center" valign="middle">
				<b>New SRD</b>
				</td>
				<td width="10%" align="center" valign="middle">
				</td>
			</tr>
			<tr>
				<td align="center" class="OrderAmendBGgreen2">
				<b>1</b>
				</td>
				<td align="center">
				SC
				</td>
				<td align="center" class="OrderAmendBGgreen2">
				TIMS9I08
				</td>
				<td align="center">
				TP1
				</td>
				<td align="center" class="OrderAmendBGgreen2">
				15/10/2013 11:23
				</td>
				<td align="center">
				
				SRD Amendment<br>
				
				</td>
				<td align="center" class="OrderAmendBGgreen2">
				
				09/12/2013 16:00-18:00<br>
				</td>
				<td align="center">
				<a name="a1" href="orderamendhistory.html#a1" class="nextbutton" id="showDetailBtn" onclick="showDetail(&#39;1&#39;);return false">Detail</a>
				</td>
			</tr>
			<tr id="s1" style="display:none;">
				<td align="center" class="OrderAmendBGgreen2">
				</td>
				<td colspan="7">
				
				
				
				<table class="OrderAmendcontenttext" width="100%" cellpadding="4" cellspacing="2" bgcolor="white">
					<tbody><tr>
						<td colspan="4" class="OrderAmendtable_title">302 F&amp;S Amendment – Change SRD &amp; Reason - Service Number: 000021220038</td>
					</tr>
					<tr>
					<td bgcolor="#99C68E" width="15%">
						Remarks
					</td>
					<td align="left" class="OrderAmendBGgreen2">
						
						<label>F&amp;S Order Amendment<br>
							Request Date: 15/10/2013 11:23<br>
							Amendment Remarks: Add new VAS ABC<br>
							*************************<br>
							F&amp;S Recjected<br>
							Update Date: 17/10/2013 17:08<br>
							Remarks: Please double new VAS offer<br></label>
						<textarea id="" name="cancelRemark" rows="5" cols="100">Sorry, new VAS offer code: ABC132
						</textarea>
						<a href="#" class="nextbutton" id="">Submit</a>
					</td>
					</tr>
					<tr>
					<td bgcolor="#99C68E">
						WQ Status
					</td>
					<td class="OrderAmendBGgreen2">
						020 Un-Assigned 
						<a href="#" class="nextbutton" style="float: right;" id="">Cancel</a>
						<a href="#" class="nextbutton" style="float: right;" id="">Re-Amend</a>
					</td>
					</tr>
				</tbody></table>
				
				
				</td>
			</tr>
		</tbody></table>
	</td>
	</tr>
	
	</tbody></table>
	

</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>