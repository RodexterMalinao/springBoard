<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ page import="java.util.*"%>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%
String scheme = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");

%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
var $itemTd8 = null;
var $itemTd9 = null; 
var recurringAmt = null;
var oneTimeAmt = null;

var arrayBrand = new Array();
var arraySimType = new Array();


function vasMaintenanceSearch() {
	$("#offerCdSearchResult tbody").empty();
	//$("#itemPreview tbody").empty();
	//$("#itemPreview").clear();
	$.ajax({
		url: "vasmaintenancesearch.html"
		, data: {
			offerCd: $("#offerCd").val()
		}
		, cache: false
		, dataType: "json"
		, success: function(data) {
			
			if (data.length == 0){
			var $alertTr = $("<tr/>");
			var $alertTd0 = $("<td/>").attr("colspan", "9").text("No Results Found!");
			$("#offerCdSearchResult tbody").append($alertTr.append($alertTd0));
			} 
			
			$.each(data, function(index, dto){
				//for Search Result...
				var $tr = $("<tr/>");
				
				//Add btn in Search Result...
				var $td0 = $("<td/>").append($("<input/>").attr("type", "button").val("ADD").click(function() {
					
					//for Item Preview
					var $parentTr = $(this).parent().parent();
					var $itemTr = $("<tr/>");
					var $itemTd0 = $("<td/>").append($("<input/>").attr("type", "button").val("DELETE").click(function(){
						$itemTr.remove();
						recurringAmt-=$itemTd8.text();
						$("#RA").val(recurringAmt);
						seqUpdate();
						var brand=$parentTr.find(".brand").text();
						var index = arrayBrand.indexOf(brand);
						if (index > -1 && (brand=="1"||brand=="0")) {
						    arrayBrand.splice(index, 1);
						}
						$("#mipBrand").empty();
						for (i = 0; i < arrayBrand.length; i++) { 
	        				$("<option value='"+arrayBrand[i]+"'>"+arrayBrand[i]+"</option>").appendTo($("#mipBrand"));
	        			}
					}));
					$itemTd0.append($("<input/>").attr({"type": "hidden", "name": "offerCd"}).val($parentTr.find(".offerCd").text()));
					$itemTd0.append($("<input/>").attr({"type": "hidden", "name":"prodCd"}).val($parentTr.find(".prodCd").text()));
					$itemTd0.append($("<input/>").attr({"type": "hidden", "name":"prodId"}).val($parentTr.find(".prodId").text()));
					$itemTd0.append($("<input/>").attr({"type": "hidden", "name":"offerId"}).val($parentTr.find(".offerId").text()));
					$itemTd0.append($("<input/>").attr({"type": "hidden", "name":"offerSubId"}).val($parentTr.find(".offerSubId").text()));
					var $itemTd1 = $("<td/>").addClass("seq_no");
					var $itemTd2 = $("<td/>").text($parentTr.find(".offerCd").text());
					var $itemTd3 = $("<td/>").text($parentTr.find(".offerDesc").text());
					var $itemTd4 = $("<td/>").text($parentTr.find(".prodCd").text());
					var $itemTd5 = $("<td/>").text($parentTr.find(".prodDesc").text());
					var $itemTd6 = $("<td/>").text($parentTr.find(".posItemCd").text());
					var $itemTd7 = $("<td/>").append($("<input/>").attr({"type": "text", "name": "mth2MthRate"}));
					$itemTd8 = $("<td/>").text("");
					$itemTd9 = $("<td/>").text("");
					var $itemTd10 = $("<td/>").text($parentTr.find(".brand").text());
					var $itemTd11 = $("<td/>").text($parentTr.find(".simType").text());
					$("#prodId").val($parentTr.find(".prodId").text());	
					
					$("#mipBrand").empty();
					$("#mipSimType").empty();
        			var brand=$parentTr.find(".brand").text(); //9
        			var simType=$parentTr.find(".simType").text(); //9
        			if(arrayBrand.indexOf(brand)+1==0){
        				if(brand=="0"&& ($.inArray("1", arrayBrand)!= "-1") ){
        					alert("Please check the product, it contains brand 1 and 0 in this item");
        				}
        				if(brand=="1"&& ($.inArray("0", arrayBrand)!= "-1")){
        					alert("Please check the product, it contains brand 1 and 0 in this item");
        				}
        				arrayBrand.push(brand);
    					arrayBrand.sort(); 
        			}
        			for (i = 0; i < arrayBrand.length; i++) { 
        				$("<option value='"+arrayBrand[i]+"'>"+arrayBrand[i]+"</option>").appendTo($("#mipBrand"));
        			} 
        			if(arraySimType.indexOf(simType)+1==0){
        				arraySimType.push(simType);
         			}
         			for (i = 0; i < arraySimType.length; i++) { 
         				$("<option value='"+arraySimType[i]+"'>"+arraySimType[i]+"</option>").appendTo($("#mipSimType"));
         			} 
        			
					//vincy for both recurring amt and onetime amt
					$.ajax({
						url: "vasmaintenancesearchrecurring.html"
						, data: {
							//prodId: $parentTr.find(".prodId").text()
							offerCd: $("#offerCd").val()
						}
						, cache: false
						, dataType: "json"
						, success: function(data) {
							
							console.log(JSON.stringify(data));
							if (data.length == 0){
								//alert("No recurring amt is found for this item!");
								$itemTd8 = $("<td/>").text("");
								$itemTd9 = $("<td/>").text("");
							} 
							
							$("#oneTimeAmt").val("0");
							$.each(data, function(index, dto){
								
								if(dto.prodType=='Recurring'){
									//alert("Recurring");
									$itemTd8 = $("<td/>").append($("<font/>").attr("color", "red").text(dto.chrgAmt));
									//S$("#RA").val(dto.chrgAmt);
									recurringAmt += parseInt(dto.chrgAmt);
									$("#RA").val(recurringAmt);
								
								}else if(dto.prodType=='Onetime'){
									//alert("Onetime");
									$itemTd9 = $("<td/>").append($("<font/>").attr('color', 'red').text(dto.chrgAmt));
									$("#oneTimeAmt").val(dto.chrgAmt);
									oneTimeAmt += parseInt(dto.chrgAmt);
									
								}
							});
						}
					});
					
					
					// for get params alert 
					$.ajax({
						url: "vasmaintenancesearchonetime.html"
						, data: {
							prodId: $("#prodId").val()
						}
						, cache: false
						, dataType: "json"
						, success: function(data) {
							
							console.log(JSON.stringify(data));
							if (data.length == 0){
								//alert("check for nothing");
							}else{
								$("#paramsAlert").css("display","block");
							}
						}
					});
					/* //for recurring amt
					$.ajax({
						url: "vasmaintenancesearchrecurring.html"
						, data: {
							//prodId: $parentTr.find(".prodId").text()
							prodId: $("#prodId").val()
						}
						, cache: false
						, dataType: "json"
						, success: function(data) {
							
							console.log(JSON.stringify(data));
							if (data.length == 0){
								//alert("No recurring amt is found for this item!");
								$itemTd8 = $("<td/>").text("");
							} 
							
							$.each(data, function(index, dto){
								$itemTd8 = $("<td/>").append($("<font/>").attr("color", "red").text(dto.chrgAmt));
								$("#RA").val(dto.chrgAmt);
								recurringAmt += parseInt(dto.chrgAmt);
							});
						}
					});
					
					//for one time amt
					$.ajax({
						url: "vasmaintenancesearchonetime.html"
						, data: {
							prodId: $("#prodId").val()
						}
						, cache: false
						, dataType: "json"
						, success: function(data) {
							
							console.log(JSON.stringify(data));
							if (data.length == 0){
								//alert("No one time amt is found for this item!");
								$itemTd9 = $("<td/>").text("");
							} 
							
							$.each(data, function(index, dto){
								$itemTd9 = $("<td/>").append($("<font/>").attr('color', 'red').text(dto.chrgAmt));
								$("#oneTimeAmt").val(dto.chrgAmt);
								oneTimeAmt += parseInt(dto.chrgAmt);
							});
						}
					}); */
					
					window.setTimeout(function(){$("#itemPreview tbody").append($itemTr.append($itemTd0).append($itemTd1).append($itemTd2).append($itemTd3).append($itemTd4).append($itemTd5).append($itemTd6).append($itemTd7).append($itemTd8).append($itemTd9).append($itemTd10).append($itemTd11));seqUpdate();}, 300);
					
					$("textarea[name=itemDescription]").val($parentTr.find(".offerDesc").text());
					$("textarea[name=itemDisplayEng]").val($parentTr.find(".offerDesc").text());
					$("textarea[name=itemDisplayChi]").val($parentTr.find(".offerDesc").text());
					
					
				}));
				
				//for Search Result...
				var $td1 = $("<td/>").text(dto.offerCd).addClass("offerCd");
				var $td2 = $("<td/>").text(dto.offerDesc).addClass("offerDesc");
				var $td3 = $("<td/>").text(dto.prodCd).addClass("prodCd");
				var $td4 = $("<td/>").text(dto.prodDesc).addClass("prodDesc");
				var $td5 = $("<td/>").text(dto.posItemCd).addClass("posItemCd");
				var $td6 = $("<td/>").text(dto.prodId).addClass("prodId");
				var $td7 = $("<td/>").text(dto.offerId).addClass("offerId");
				var $td8 = $("<td/>").text(dto.offerSubId).addClass("offerSubId");
				var $td9 = $("<td/>").text(dto.brand).addClass("brand");
				var $td10 = $("<td/>").text(dto.simType).addClass("simType");
				
				$("#offerCdSearchResult tbody").append($tr.append($td0).append($td1).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7).append($td8).append($td9).append($td10));
			});
		}
	});

}

function seqUpdate(){
	$("#itemPreview tbody tr").each(function(index) {
		$(this).find(".seq_no").text(index+1);
	});
}

function isValuesLenSafe() {
	var offerCdLen = $("input[name=offerCd]").length;
	if (offerCdLen < 1) {
		//alert("offerCdLen: " + offerCdLen);
		alert("please insert information")
		return false;
	}
	var prodCdLen = $("input[name=prodCd]").length;
	if (offerCdLen != prodCdLen) {
		alert("offerCdLen: " + offerCdLen + ", prodCdLen: " + prodCdLen);
		return false;
	}
	var prodIdLen = $("input[name=prodId]").length;
	if (offerCdLen != prodIdLen) {
		alert("offerCdLen: " + offerCdLen + ", prodIdLen: " + prodIdLen);
		return false;
	}
	var offerIdLen = $("input[name=offerId]").length;
	if (offerCdLen != offerIdLen) {
		alert("offerCdLen: " + offerCdLen + ", offerIdLen: " + offerIdLen);
		return false;
	}
	var offerSubIdLen = $("input[name=offerSubId]").length;
	if (offerCdLen != offerSubIdLen) {
		alert("offerCdLen: " + offerCdLen + ", offerSubIdLen: " + offerSubIdLen);
		return false;
	}
	return true;
}

$(document).ready(function() {
	$("#searchForm").submit(function(e) {
		e.preventDefault();
		vasMaintenanceSearch();
		return false;
	});
	$("input[name=clearBtn]").click(function(){
		$("#offerCdSearchResult tbody").empty();
		$("#itemPreview tbody").empty();
		$("#IDT,#IDE,#IDC,#SSDE,#SSDC,#IT").val("");
		$("#RA").val("0");

	});
	$("#vasForm").submit(function(e) {
		if (!isValuesLenSafe()) {
			e.preventDefault();
			return false;
		}
		if ($("#IT").val() == "") {
			alert("Please select vas type!");
			e.preventDefault();
			return false;
		}
 		if ((recurringAmt>0||oneTimeAmt>0) && ($("#RA").val()==0||$("#RA").val().length==0) && ($("#oneTimeAmt").val()==0||$("#oneTimeAmt").val().length==0)){
			r = confirm("Please note that the item(s) selected is/are NOT free of charge. Please press 'Cancel' to fill in the price if needed or press 'OK' to continue.");
			if (r==false){
				e.preventDefault();
				return false;
			}
		} 
	});
	$("#effStartDate").datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		dateFormat : "dd/mm/yy",
		defaultDate : 0

	});
	
	$('input[type="checkbox"][id="checkRebate"]').click(function() {
    	
      if ($(this).is(':checked')) {
           $('#rebateInput').attr('disabled', false);
           $('#rebateDisInput').show();
      }else{
    	  $('#rebateInput').attr('disabled', true);
    	  $('#rebateDisInput').hide();
      }
    });
	
	$('#checkOnline').click(function() {
	      if ($(this).is(':checked')) {
	           $('#onlineInd').show();
	      }else{
	    	  $('#onlineInd').hide();
	    	
	      }
	 });
	
	var today = new Date();
	var defaultDate = today.getDate() + '/' + (today.getMonth()+1) + '/' +
	        today.getFullYear();
	$("#effStartDate").val(defaultDate);
	
});
		

</script>

<c:choose>
<c:when test="${param.complete == 'true' && param.itemId > 0}">
<div class="contenttextgreen">Process is complete! The item id assigned is ${param.itemId}.</div>
</c:when>
<c:when test="${param.complete == 'true' && (empty param.itemId || param.itemId <= 0)}">
<div class="contenttext_red">Error occurs! The error code returned is ${param.itemId}. Please check.</div>
</c:when>
</c:choose>

<form action="#" method="post" id="searchForm">
	<input type="text" id="offerCd" name="searchOfferCd">
	<input type="submit" value="Search" id="searchBtn">
	<input type="hidden" id="prodId">
</form>

<table class="contenttext" id="offerCdSearchResult" style="width: 100%; background-color: white">
<thead>
<tr class="contenttextblack">
	<td class="table_title" colspan="11">Search Result</td>
</tr>

	<tr>
		<th class="table_title">[add]</th>		
		<th class="table_title">Offer Code</th>
		<th class="table_title">Offer Desc</th>
		<th class="table_title">Prod CD</th>
		<th class="table_title">Prod Desc</th>
		<th class="table_title">POS Item CD</th>
		<th class="table_title">Prod ID</th>
		<th class="table_title">Offer ID</th>
		<th class="table_title">Offer Sub ID</th>
		<th class="table_title">Brand</th>
		<th class="table_title">Sim Type</th>
	</tr>
</thead>
<tbody>
</tbody>
</table>


<form:form method="POST" action="vasmaintenance.html" id="vasForm" commandName="vasmaintenance">

<table class="contenttext" id="itemPreview" style="width: 100%; background-color: white">
<thead>
<tr class="contenttextblack">
	<td class="table_title" colspan="12">Item Preview</td>
</tr>

	<tr>
		<th class="table_title">[delete]</th>
		<th class="table_title">Seq</th>
		<th class="table_title">Offer Code</th>
		<th class="table_title">Offer Desc</th>
		<th class="table_title">Prod CD</th>
		<th class="table_title">Prod Desc</th>
		<th class="table_title">POS Item CD</th>
		<th class="table_title">Mth to Mth Rate</th>
		<th class="table_title">Recurring Amt</th>
		<th class="table_title">One Time Amt</th>
		<th class="table_title">Brand</th>
		<th class="table_title">Sim Type</th>
	</tr>
	
</thead>
<tbody>
</tbody>
</table>

<table class="contenttext" id="itemInfo" style="width: 100%; background-color: white">
<thead>
<tr class="contenttextblack">
	<td class="table_title" colspan="2">Item Info</td>
</tr>
</thead>
<tr>
	<td>Item Description</td>
	<td>
		<form:textarea path="itemDescription" cols="100" rows="3" id="IDT"/>
	</td>
</tr>
<tr>
	<td>Item Display ENG</td>
	<td>
		<form:textarea path="itemDisplayEng" cols="100" rows="3" id="IDE"/>
	</td>
</tr>
<tr>
	<td>Item Display CHI</td>
	<td>
		<form:textarea path="itemDisplayChi" cols="100" rows="3" id="IDC"/>
	</td>
</tr>
<tr>
	<td>S&amp;S Display ENG</td>
	<td>
		<form:textarea path="ssDisplayEng" cols="100" rows="3" id="SSDE"/>
	</td>
</tr>
<tr>
	<td>S&amp;S Display CHI</td>
	<td>
		<form:textarea path="ssDisplayChi" cols="100" rows="3" id="SSDC"/>
	</td>
</tr>
<tr>
	<td>Recurrent_Amt</td>
	<td>
		<form:input path="recurrentAmt" id="RA"/>
	</td>
</tr>
<tr>
<tr>
	<td>One_Time_Amt</td>
	<td>
		<form:input path="oneTimeAmt" id="oneTimeAmt"/>
	</td>
</tr>
<tr>
	<td>Eff_Start_Date (w_item_pricing)</td>
	<td><form:input path="effStartDate" readonly="true" /></td>
</tr>
<tr>
	<td>Item Type</td>
	<td>
		<form:select path="itemType" id="IT">
			<%-- <form:option label="--Please select--" value=""/> --%>
			<form:options items="${itemTypes}"/>
		</form:select>
	</td>
</tr>
<tr>
	<td>Brand</td>
	<td>
		<form:select path="mipBrand" id="mipBrand">
			<form:options items="${mipBrand}"/>
		</form:select>
	</td>
</tr>
<tr>
	<td>Sim Type</td>
	<td>
		<form:select path="mipSimType" id="mipSimType">
			<form:options items="${mipSimType}"/>
		</form:select>
	</td>
</tr>
<tr>
	<td>
		<form:checkbox path="checkOnlineInd" id="checkOnline" />Online Sales
	</td>
	<td>
	   <div  id ="onlineInd" style="display:none;color:red"> Y</div>
	</td>
</tr>
<tr>
	<td>
		<input type="checkbox" id="checkRebate" />Input Rebate
	</td>
	<td>
		<form:input path="rebateInput" id="rebateInput" disabled="true"/>
	</td>
</tr>
	
<tr>
	<td></td>
	<td>
		<table class="contenttext" id="rebateDisInput" style="width: 100%; background-color: white; display: none">
		<tr>
			<td>Rebate Display ENG</td>
			<td>
				<form:textarea path="rebateDisplayEng" cols="80" rows="2" id="RebateDE"/>
			</td>
         </tr>
         <tr>
			<td>Rebate Display CHI</td>
			<td>
				<form:textarea path="rebateDisplayChi" cols="80" rows="2" id="RebateDC"/>
			</td>
         </tr>
		</table>
	</td>
</tr>

<tr>
	<td><div id="paramsAlert" style="display: none; color:red" >Warning : Product Parameter found </div></td>

</tr>

<tr>
	<td>
		<input type="submit" value="Create">
	</td>
</tr>
<tr>
	<td>
		<input type="button" name="clearBtn" value="Clear">
	</td>	
</tr>


</table>

</form:form>

<a href="basketpreview.html?basketId=999">Basket Preview Page (Production)</a>     
<br/>
<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
 <a href="${mobcosurl}adm/itemsearch.html?searchItemId=${param.itemId}">Item Search Page</a>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>