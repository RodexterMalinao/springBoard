<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp"%>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<%@ taglib prefix="sb-ui" tagdir="/WEB-INF/tags/ui" %>
<script src="js/jquery-1.9.1.js"></script>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="offerdetail" />
	<jsp:param name="sbuid" value="${param.sbuid}" />
</jsp:include>

<!-- <script type="text/javascript" src="js/jquery.dataTables.min.js"></script> -->
<script type="text/javascript" src="js/jquery.dataTables.1.10.0.js"></script>
<link type="text/css" href="css/dataTable/jquery.dataTables.css" rel="stylesheet" />
<style type="text/css">
.groupedPcRelationshipList {
	background-color: rgb(255, 240, 248);
	padding: 0.5em
}

.groupedPcRelationshipList h3, .groupedPcRelationshipList ul,
	.groupedPcRelationshipList li {
	font-size: 14px
}

.groupedPcRelationshipList a {
	color: #616161;
	display: inline-block
}

.groupedPcRelationshipList h3 {
	color: #7C5DB0;
	margin: 0.5em 0
}

.groupedPcRelationshipList .primaryItemDesc {
	color: #007D70
}

.groupedPcRelationshipList .group {
	display: block
}

.primaryItemCodeLink {
	display: block;
	width: 1em;
	text-align: center;
	text-decoration: none
}

.primaryItemCodeLink:hover {
	font-weight: bold
}

#searchText {
	width: 10em
}

.search_box {
	display: inline-block;
	float: right
}

.search_clear {
	color: black;
	cursor: pointer
}

.vas_detail_list, .sim_sel {
	width: 100%;
	background-color: white
}

.vas_detail_list td, .sim_sel td {
	margin: 1px;
	padding: 5px
}

.vas_detail_list .highlight {
	background-color: #FFFF80
}

.vas_detail_list .off {
	display: none
}

.vas_detail_list tbody td, .sim_sel tbody td {
	background-color: white
}

.vas_detail_list tbody td.BGgreen2 {
	background-color: #e2f5d3
}

.vas_detail_list tbody .even td, .sim_sel tbody .even td {
	background-color: #F2F2F2
}

.vas_detail_list tbody .even td.BGgreen2 {
	background-color: #D0EFB8
}

.table_title_2row {
	text-align: center
}

.table_title_2row span {
	display: inline-block;
	white-space: nowrap;
	text-align: center
}

.itemHtml ul {
	margin: 0 0 0 0;
}

#special_vas_detail_table .table_title,
#special_vas_detail_table_title.table_title {
	background-color: #4c93e1 !important;
}

#vas_vas_detail_table .table_title,
#vas_vas_detail_table_title.table_title {
	background-color: #7fb1ea !important;
}

#premium_vas_detail_table .table_title,
#premium_vas_detail_table_title.table_title {
	background-color: #b1d0f2 !important;
}
#scrollButton {
	position: fixed;
    bottom: 10px;
    right: 10px;
	width: 50px;
	height: 50px;
	opacity:0.4;
	filter:alpha(opacity=40);
	z-index: 1;
}
#scrollButton:hover {
opacity:1.0;
filter:alpha(opacity=100); /* For IE8 and earlier */
}

#special_vas_toogle
, #vas_vas_toogle
, #premium_vas_toogle {
	font-size: 20px;
	font-weight: bold;
	color: red;
}
</style>
<!--[if lte IE 7]>
<style type="text/css">
.groupedPcRelationshipList ul { margin-top: 0; margin-bottom: 0 }
</style>
<![endif]-->

<script type="text/javascript">
var authorizeType = "";
var specialVasTable;
var vasVasTable;
var premiumVasTable;

if(!document.getElementsByClassName)
{
	//For IE6,7,8 ,create getElementsByClassName function
	 document.getElementsByClassName = function(className, element){
	        var children = (element || document).getElementsByTagName('*');
	        var elements = new Array();
	        for (var i=0; i<children.length; i++){
	            var child = children[i];
	            var classNames = child.className.split(' ');
	            for (var j=0; j<classNames.length; j++){
	                if (classNames[j] == className){
	                    elements.push(child);
	                    break;
	                }
	            }
	        }
	        return elements;
	    };
}


function isBlank(str) {
	return str == null || $.trim(str).length == 0;
}
function updateVasDetailListStyle() {
	$("#vas_detail_table tbody tr:not(.off)").each(function(index) {
		var $tr = $(this);
		if (index % 2 == 0) {
			$tr.removeClass("even");
		} else {
			$tr.addClass("even");
		}
	});
}
function searchByText($tr, text) {
	// assume text key already trim and lower case
	return $tr.find(".vas_itemHtml").text().toLowerCase().indexOf(text) > -1;
}
function searchByCategoryId($tr, categoryId) {
	return $tr.find("input[name=categoryId]").val() == categoryId;
}
function updateSearch() {
	var text = $("#searchText").val();
	var categoryId = $("select[name=searchCategory]").val();
	if (isBlank(text) && isBlank(categoryId)) {
		$("#vas_detail_table tbody tr.off").removeClass("off");
	} else {
		text = $.trim(text).toLowerCase();
		var $trs = $("#vas_detail_table tbody tr");
		$trs.each(function(index) {
			var $tr = $(this);
			// exclude selected vas item from filtering 
			if ($tr.find("input[name=vasitem]").is(":checked")) {
				$tr.removeClass("off");
				return;
			}
			var matched = false;
			if (!isBlank(text) && !isBlank(categoryId)) {
				matched = searchByText($tr, text) && searchByCategoryId($tr, categoryId);
			} else if (!isBlank(text)) {
				matched = searchByText($tr, text);
			} else if (!isBlank(categoryId)) {
				matched = searchByCategoryId($tr, categoryId);
			}
			if (matched) {
				$tr.removeClass("off");
			} else {
				$tr.addClass("off");
			}
		});
	}
	updateVasDetailListStyle();
}
function groupToSearchCategory() {
	var $searchCategory = $("select[name=searchCategory]");
	$("#vas_detail_table tbody tr").each(function() {
		var categoryId = $.trim($(this).find("input[name=categoryId]").val());
		var categoryDesc = $.trim($(this).find("input[name=categoryDesc]").val());
		if (isBlank(categoryId) || isBlank(categoryDesc)) {
			return;
		}
		var categoryIdPresent = false;
		var $options = $searchCategory.find("option");
		$options.each(function() {
			if ($(this).val() == categoryId) {
				categoryIdPresent = true;
				return false;
			}
		});
		if (categoryIdPresent) {
			return;
		}
		var addToIndex = 0;
		$options.each(function(index) {
			if (categoryDesc.toLowerCase() <= $(this).text().toLowerCase()) {
				return false;
			}
			addToIndex = index;
		});
		$options.eq(addToIndex).after($("<option/>").val(categoryId).text(categoryDesc));
	});
	$searchCategory.css("width", "auto"); // for ie6 to refresh select width
}

function showItemFunctionPopup(url) {
	var sURL = url;
	window.open(sURL, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
}

function loadExFuncBtnSts(){
	var $vasItems = $("input[name=vasitem]");
	$vasItems.each(function(index) {
		var itemId = $(this).val();
		$("#itemFunc_" + itemId).attr("disabled","disabled");
		if ($(this).is(':checked')) {
			$("#itemFunc_" + itemId).removeAttr("disabled");
		}
	});
}

function handleBR86EasyOptout() {
	var br86EasyOptoutList = ${br86EasyOptoutList};
	$.each(br86EasyOptoutList, function( index, value ) {
		$("input[name=vasitem][value="+value+"]").prop( "checked", true );
	});
}

$.fn.dataTable.ext.order['dom-checkbox'] = function  ( settings, col )
{
    return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
        return $('input', td).prop('checked') ? '0' : '1';
    } );
}

$(document).ready(function() {
	
	if ("${vasDetail.isEagleAPIFailedInd}" == "Failed") {
		$("<p>Restart Service blacklist checking is not successfully conducted. Please try again. If it is still failed, the checking will be by-pass</p>").dialog({
			resizable: false,
			height:"200",
			width:"500",
			title:'Restart Service Blacklist API Call Failed',
			modal:true,
			buttons:{
				"Continue":function() {
					$("input[name=ignoreEagleAPICheck]").attr("checked", true);
					$(this).dialog("close");
				}
			}
		});
	}
	
	$("input[name=vasitem]").click(function(e) {
		var itemId = $(this).val(); 
		if($(this).is(':checked')) {			
			$("#itemFunc_" + itemId).removeAttr("disabled");
		} else {
			$("#itemFunc_" + itemId).attr("disabled","disabled");
		} 
	});
	loadExFuncBtnSts();
	
	$("input[name=vasitem][class*='VAS_GROUP']").click(function(e) {
		//VAS Group 
		var vasGroupValue = "VAS_GROUP_"+$(this).data("vasgroup");	
		if ($(this).is(':checked')) {
			if ("${channelId}" != 2) {
				$("."+vasGroupValue).prop('checked',true);
			} else {
				var stockNeed = 0;
				var stockcntTotal = 0;
				
				$("."+vasGroupValue).each(function() {
					var stockcnt = $(this).data("stockcnt");
					if (stockcnt != -1) {
						stockNeed = stockNeed + 1;
						stockcntTotal = stockcnt;
					}
				});
				
				if (stockNeed == 0) {
					$("."+vasGroupValue).prop('checked',true);
				} else {
					if (stockcntTotal >= stockNeed) {
						$("."+vasGroupValue).prop('checked',true);
					} else {
						$(this).prop('checked', false);
					}
				}
			}
		} else {
			$("."+vasGroupValue).prop('checked',false);
		}
	});
	
	$("input[name=vasitem][class*='CATEGORY_ID_1000078']").click(function(e) {
		if($(this).is(':checked')){
			$("#capacityPassDialog").dialog({ 
				modal: true
				, width: 500
				, height: 250
				, resizable: false
				, draggable: false
				
			});
		}
	});
	
	$(".primaryItemCodeLink").click(function(e) {
		e.preventDefault();
		var $secondaryItem = $(this).parent().parent().find(".secondaryItem");
		$secondaryItem.toggle();
		$(this).text($secondaryItem.is(":visible") ? "-" : "+"); 
		return false;
	});
	groupToSearchCategory();
	$("select[name=searchCategory]").change(updateSearch);
	$("#searchText").data("timeout", null).keypress(function(e) {
	    clearTimeout($(this).data("timeout"));
		switch (e.keyCode ? e.keyCode : e.which) {
		case 13: // enter
			e.preventDefault();
			updateSearch();
			return false;
		default:
		    $(this).data("timeout", setTimeout(updateSearch, 500));
		}
	}).keydown(function(e) {
		switch (e.keyCode ? e.keyCode : e.which) {
		case 8: // backspace
		case 46: // delete
			$(this).trigger("keypress");
			break;
		}
	}).focusout(function(e) {
		$(this).trigger("keypress");
	});
	$(".search_clear").click(function(e) {
		e.preventDefault();
		$("select[name=searchCategory]")[0].selectedIndex = 0;
		$("#searchText").val("");
		updateSearch();
		return false;
	});
	
	/*<c:if test='${basket != null}'>
	$("[name=selectedSimItemId]").change(function(){
		var value = $(this).val();
	});
	
	var value = "${vasDetail.selectedSimItemId}";
	</c:if>*/
	
	$("#quotaExceedAuthorizeButton").click(function(e) {
		e.preventDefault();
		authorizeType = "AU15";
		var sURL = "mobccsauthorize.html?" + $.param({"action": "AU15"});
		$('#authDialog').html('<iframe style="border: 0px; " src="' + sURL + '" width="100%" height="100%"></iframe>')
        .dialog({
            autoOpen: false,
            modal: true,
            height: 300,
            width: 500,
            title: "Authorize"
        });
		$('#authDialog').dialog('open');
		return false;
	});
	
	$("#goldenNumAuthorizeButton").click(function(e) {
		e.preventDefault();
		authorizeType = "AU21";
		var sURL = "mobccsauthorize.html?" + $.param({"action": "AU21"});
		$('#authDialog').html('<iframe style="border: 0px; " src="' + sURL + '" width="100%" height="100%"></iframe>')
        .dialog({
            autoOpen: false,
            modal: true,
            height: 300,
            width: 500,
            title: "Authorize"
        });
		$('#authDialog').dialog('open');
		return false;
	});
	
	if ($('#minVasAuthorizeButton').length) {
		$("#minVasAuthorizeButton").click(function(e) {
			e.preventDefault();
			authorizeType = "AU18";
			var sURL = "mobccsauthorize.html?" + $.param({"action": "AU18","basketId": "${selectedBasketId}"});
			$('#authDialog').html('<iframe style="border: 0px; " src="' + sURL + '" width="100%" height="100%"></iframe>')
	        .dialog({
	            autoOpen: false,
	            modal: true,
	            height: 300,
	            width: 500,
	            title: "Authorize"
	        });
			$('#authDialog').dialog('open');
			return false;
		});
	}
	
	if ($("#firstMonthPrepayAuthorizeButton").length){
		if (!isBlank($("#rpWaiveReasonSelect").val()) ||  "<c:out value="${dummyWaiveRpPrepayItemExist}"/>" == 'Y' || "<c:out value="${customer.idDocType}"/>" == 'BS'){
			if ("<c:out value="${customer.idDocType}"/>" == 'BS'){
				if (isBlank($("#rpWaiveReasonSelect").val())){
					
					$("#rpWaiveReasonSelect").val("OT0054");
					
				}
			}else if ("<c:out value="${dummyWaiveRpPrepayItemExist}"/>" == 'Y'){
				if (isBlank($("#rpWaiveReasonSelect").val())){
					
					$("#rpWaiveReasonSelect").val("OT0099");
					
				}
			}
			
			$("#firstMonthPrepayAuthorizeButton").hide();
			$("#rpWaiveReasonSelectDiv").show();
			//$("#rpWaiveReasonSelect").show();
		}else{
			$("#rpWaiveReasonSelectDiv").hide();
			//$("#rpWaiveReasonSelect").hide();
			
			$("#firstMonthPrepayAuthorizeButton").show();
			$("#firstMonthPrepayAuthorizeButton").click(function(e) {
				e.preventDefault();
				authorizeType = "AU16";
				
				var orderID = "<c:out value='${orderDto.orderId}'/>";
				var sURL;
				if(orderID != null && orderID !="")	{
					sURL = "mobccsauthorize.html?" + $.param({"action": "AU16", "orderId": orderID});
				} else {
					sURL= "mobccsauthorize.html?" + $.param({"action": "AU16"});
				}
				//var sURL = "mobccsauthorize.html?" + $.param({"action": "AU16"});
				$('#authDialog').html('<iframe style="border: 0px; " src="' + sURL + '" width="100%" height="100%"></iframe>')
		        .dialog({
		            autoOpen: false,
		            modal: true,
		            height: 300,
		            width: 500,
		            title: "Authorize"
		        });
				$('#authDialog').dialog('open');
				return false;
			}); 
		}
		
		if ("${customer.addrProofInd}" == "5" || "${customerProfileDTO.addrProofInd}" == "5") {
			$("#firstMonthPrepayAuthorizeButton").hide();
			$("#rpWaiveReasonSelect").attr("disabled", true);
			$("#rpWaiveReasonSelectDiv").show();
		}
	}
	
	$(".noStock").click(function() {
		return false;
	});
	
	$(".noStock").keydown(function() {
		return false;
	});
	
	specialVasTable = initDataTable("#special_vas_detail_table");
	vasVasTable = initDataTable("#vas_vas_detail_table");
	premiumVasTable = initDataTable("#premium_vas_detail_table");
	initToggleVasGroup();
	
	$("#special_vas_toogle").click(function() {
		var visible = toggleVasSelection('#special_vas_detail_table');
		$(this).text(visible ? '[-]' : '[+]');
	});
	
	$("#vas_vas_toogle").click(function() {
		var visible = toggleVasSelection('#vas_vas_detail_table');
		$(this).text(visible ? '[-]' : '[+]');
	}).click();
	
	$("#premium_vas_toogle").click(function() {
		var visible = toggleVasSelection('#premium_vas_detail_table');
		$(this).text(visible ? '[-]' : '[+]');
	}).click();
	
	scrollButtonControl();
	tickUADPlan();
	handleBR86EasyOptout();
	
});

function tickUniversalProPlanAndUADPlan(iGuardUADInd){
	if (iGuardUADInd=="N"){
		tickUniversalProPlan()
	}else{
		tickUADPlan()
	}
}

function tickUniversalProPlan(){
	var iguardIdList = ${iguardIdList};
	$.each(iguardIdList, function( index, value ) {
		if("${basket.basketTypeId}" == value && "${vasDetail.chkIguard}"=="true" && "${vasDetail.hkidInd}"=="true"){
			var iguardItemList = ${iguardItemList};
			$.each(iguardItemList, function( index, value ) {
				if ("${channelId}" != 2 || $("input[name=vasitem][value="+value+" ]").data("stockcnt") > 0 ) {
					$("input[name=vasitem][value="+value+" ]").prop( "checked", true );
				}
			});
		}
		
	});
}

function tickUADPlan(){
	var iguardIdList = ${iguardIdList};
	var iguardItemList = ${iguardItemList};
	var iguardBasketchecked = false;
	$.each(iguardItemList, function( index, value ) {
		if( $("input[name=vasitem][value="+value+"]").length > 0 && $("input[name=vasitem][value="+value+"]").is(':checked')){
			iguardBasketchecked = true;
		}
	});
	
	$.each(iguardIdList, function( index, value ) {
		if("${basket.basketTypeId}" == value && "${vasDetail.chkUADPlan}"=="true" && "${vasDetail.hkidInd}"=="true" && iguardBasketchecked==false){
			var iguardUADItemList = ${iguardUADItemList};
			$.each(iguardUADItemList, function( index, value ) {
				if ("${channelId}" != 2 ) {
					/* $("input[name=vasitem][value="+value+" ]").prop( "checked", true ); */
					untickUniversalProPlan();
				}else{
					tickUniversalProPlan();
				}
			});
		}
		
	});
}

function untickUniversalProPlan(){
	var iguardIdList = ${iguardIdList};
	$.each(iguardIdList, function( index, value ) {
		if("${basket.basketTypeId}" == value && "${vasDetail.chkIguard}"=="true" && "${vasDetail.hkidInd}"=="true"){
			var iguardItemList = ${iguardItemList};
			$.each(iguardItemList, function( index, value ) {
				if ("${channelId}" != 2 || $("input[name=vasitem][value="+value+" ]").data("stockcnt") > 0 ) {
					$("input[name=vasitem][value="+value+" ]").prop( "checked", false );
				}
			});
		}
		
	});
}

function checkEagleVAS(event) {
	(event.preventDefault) ? event.preventDefault() : event.returnValue = false; 
	var eagleBasket = false;
	var eagleItemList = ${eagleItemList};
	$.each(eagleItemList, function(index, value) {
		if( $("input[name=vasitem][value="+value+"]").length > 0 && $("input[name=vasitem][value="+value+"]").is(':checked')) {
			eagleBasket = true;
		}
	});
	if (eagleBasket) {
		$("<p>Customer personal data will be passed to Restart Service for application checking. Please get customer consent to continue.</br></br>Yes:Customer agreed</br>No:Customer declined</p>").dialog({
			resizable: false,
			height:"210",
			width:"500",
			title:'Restart Service Offer Subscription',
			modal:true,
			buttons:{
				"Yes":function() {
					$(this).dialog("close");
					// showLoading();
					chkUADPlanBox(event);
				},
				"No":function() {
					$(this).dialog("close");
				}
			}
		});
	} else {
		chkUADPlanBox(event);
	}
}

function chkUniversalProPlanBox(event){
	(event.preventDefault) ? event.preventDefault() : event.returnValue = false; 
	var iguardIdList = ${iguardIdList};
	var iguardBasket = false;
	var iguardUadBasket = false;
	$.each(iguardIdList, function( index, value ) {
		if(("${basket.basketTypeId}" == value && "${vasDetail.chkIguard}"=="true" && "${vasDetail.hkidInd}"=="true")|| ("${basket.basketTypeId}" == value && "${vasDetail.chkUADPlan}"=="true" && "${vasDetail.hkidInd}"=="true") ){
			var iguardItemList = ${iguardItemList};
			$.each(iguardItemList, function( index, value ) {
				if( $("input[name=vasitem][value="+value+"]").length > 0 && !$("input[name=vasitem][value="+value+"]").is(':checked')){
				iguardBasket = true;
				}
			});
			
			var iguardUadItemList = ${iguardUADItemList};
			$.each(iguardUadItemList, function( index, value ) {
				if( $("input[name=vasitem][value="+value+"]").length > 0 && !$("input[name=vasitem][value="+value+"]").is(':checked')){
					iguardUadBasket = true;
				}
			});
		}
	});	
		
	if(iguardBasket && iguardUadBasket){
		$("<p>Are you sure not to enjoy any iGuard Universal Repair Plan?</p>").dialog({
		resizable: false,
		height:"200",
 		    	width:"400",
		title:'Confirm Universal Repair Plan',
	    modal:true,
		    buttons:{
		    	"Yes":function(){
		    		$(this).dialog("close");
		    		realFormSubmit();
    			},
    			"No":function(){
					$(this).dialog("close");
    			}
		     }
		});
	}else{
		realFormSubmit();
	}
}

function chkUADPlanBox(event){
	(event.preventDefault) ? event.preventDefault() : event.returnValue = false; 
	var iguardIdList = ${iguardIdList};
	var iguardUADBasket = false;
	var iguardBasket = true;
	$.each(iguardIdList, function( index, value ) {
		if("${basket.basketTypeId}" == value && "${vasDetail.chkUADPlan}"=="true" && "${vasDetail.hkidInd}"=="true"){
			var iguardUadItemList = ${iguardUADItemList};
			$.each(iguardUadItemList, function( index, value ) {
				if( $("input[name=vasitem][value="+value+"]").length > 0 && !$("input[name=vasitem][value="+value+"]").is(':checked')){
				iguardUADBasket = true;
				}
			});
			var iguardItemList =  ${iguardItemList};
			$.each(iguardItemList, function( index, value ) {
				if( $("input[name=vasitem][value="+value+"]").length > 0 && !$("input[name=vasitem][value="+value+"]").is(':checked')){
				iguardBasket = false;
				}
			});
		}
	});	
	if(iguardUADBasket && !iguardBasket	&&	"${vasDetail.chkIguard}"=="true" ){
		$("<p>Yes :Subscribe i-GUARD Repair Plan Activation Card <br> No : Unsubscribe i-GUARD Repair Plan Activation Card </p> ").dialog({
		resizable: false,
		height:"200",
 		    	width:"650",
		title:'i-GUARD Repair Plan Activation Card Subscription',
	    modal:true,
		    buttons:{
		    	"Yes":function(){
		    		$(this).dialog("close");
		    		tickUniversalProPlanAndUADPlan("N")
		    		//realFormSubmit();
    			},
    			"No":function(){
		    		$(this).dialog("close");
		    		chkUniversalProPlanBox(event);
    			}
		     }
		});
	}else{
		chkUniversalProPlanBox(event);
		//realFormSubmit();
	}
}

function scrollButtonControl() {
	$("#scrollButton").css("display","none");
    $(window).scroll(function() {
    	$("table[id*='vas_detail_table']").each(function() {
    		var tableId = $(this).attr("id");
    		
    		if ($("#"+tableId+"_wrapper").is(':visible')) {
        		var tableTop = $("#"+tableId+"_title").offset().top;
        		var tableBottom = $("#"+tableId+" tfoot").offset().top;
        		
        		if ($(document).scrollTop() >= tableTop) {
                    $("#scrollButton").css("display","block");
                    
                    $("#scrollTableTop").click(function() {
            			$("body, html").scrollTop(tableTop);
            		});
                    
                    $("#scrollTableButton").click(function() {
            			$("body, html").scrollTop(tableBottom - $(window).height() + 20);
            		});
                }
        	}
    	});
     })
	  
    /* if (document.documentElement.clientHeight < document.documentElement.offsetHeight) {
         //$("#scrollButton").css("display","block");
    }else { 
         $("#scrollButton").css("display","none");
    }

    window.onresize=function(){
        if (document.documentElement.clientHeight < document.documentElement.offsetHeight) {
            //$("#scrollButton").css("display","block");
        }else{ 
            $("#scrollButton").css("display","none");
        }
    } */
}
	 
	 function removeItem(arg) {
		 x=confirm("Confirm to Remove out of stock item ? \nNOTE: will not allow select back while out of stock.");
		
		if (x){
			$('input[name=vasitem][value='+arg+']').removeAttr('checked'); // for uncheck
		 }
 
	 }
	 
	function formSubmit(arg, event) {
		var selectedSim = $('input[name="selectedSimItemId"]:checked').val();
		document.vasDetailForm.simWaiveReason.value = $("#simWaiveReason_" + selectedSim).val();
		document.vasDetailForm.simCharge.value = $("#simCharge_" + selectedSim).text();
		document.vasDetailForm.simWaivable.value = ($("#simWaivable_" + selectedSim).html() == "true"? true: false);
		document.vasDetailForm.simType.value = $("#simType_" + selectedSim).text();
		document.vasDetailForm.simExtraFunction.value = $("#simExtraFunction_" + selectedSim).text();
		document.vasDetailForm.rpWaiveReason.value = $("#rpWaiveReasonSelect").val();
		document.vasDetailForm.rpCharge.value = $("#rpChargeHidden").val();
		document.vasDetailForm.byPassGoldenNum.value = $("#byPassGoldenNum").val();
		
		if(arg == "saveDraft") {
			document.vasDetailForm.nextAction.value = arg;
		} else {
			document.vasDetailForm.nextAction.value = "";
		}
		
		checkEagleVAS(event);
	}
	
	function realFormSubmit(){
		showLoading();
		specialVasTable.fnDestroy();
		vasVasTable.fnDestroy();
		premiumVasTable.fnDestroy();
		document.vasDetailForm.submit();
	}
	
	function authorized() {
		$('#authDialog').dialog('close');
		if (authorizeType == 'AU15') {
			$("#quotaExceedAuthorizeButton").hide();
		} else if (authorizeType == 'AU16') {
			$("#firstMonthPrepayAuthorizeButton").hide();
			$("#rpWaiveReasonSelectDiv").show();
			//$("#rpWaiveReasonSelect").show();
			$("#rpWaiveReasonSelect").val("OT0099");
		} else if (authorizeType == 'AU18') {
			$("#minVasNotAuthorizedDiv").hide();
			$("#minVasAuthorizedDiv").show();
			$("input[name=minVasAuthInd]").val("Y");
		} else if ( authorizeType = 'AU21') {
			$("#goldenNumAuthorizeButton").hide();
			$("input[name=byPassGoldenNum]").val("Y");
			$("#goldenNumNotAuthDiv").hide();
			$("#goldenNumAuthDiv").show();	
		}
	}

	function initToggleVasGroup(){
		$("table tr[class*='TOGGLE_VAS_']").each(function(){
			var vasGroup = $(this).data("vasgroup");
			var vasGroupItemId = $(this).data("vasgroupitemid");
			if (vasGroup == vasGroupItemId) {
				toggleSameVasGroup(vasGroup);
			}
		});
	}
	
	function toggleSameVasGroup(vasGroup){
		/* var vasGroupValue = "TOGGLE_VAS_"+vasGroup;	
		$('table#vas_detail_table tr.'+vasGroupValue).toggle();
		$('.toggleVasGroupResultListStatus').text($('.vasGroupResultList').is(':visible') ? '[-]' : '[+]'); */
		$("table tr.TOGGLE_VAS_"+vasGroup).each(function(){
			 var vasGroupItemId = $(this).data("vasgroupitemid");
		     if (vasGroup != vasGroupItemId){
		    	$(this).toggle();
		 		$('.toggleVasGroupResultListStatus_'+vasGroup).text( $(this).is(':visible') ? '[-]' : '[+]'); 
		     }
		}); 
	}
	
	function fnCreateSelect(aData) {
		var r='<select><option value=""></option>', i, iLen=aData.length;
		var cleanData;
		for ( i=0 ; i<iLen ; i++ ){
			cleanData = aData[i];
			if (cleanData == '') {
				r += '<option value="^$">Empty</option>';
			} else {
				r += '<option value="'+cleanData+'">'+cleanData+'</option>';
			}
		}
		return r+'</select>';
	}
	
	function getColumnData(tablename, i) {
		var tempResultData = new Array();
		var asResultData = new Array();
		$(tablename+' tbody tr td:nth-child('+(i+1)+')').each(function() {
			var content = $(this).text().trim();
			if (content) {
				tempResultData.push(content);
			}
		});
		var tempResultDataSet = new Set(tempResultData);
		tempResultData = Array.from(tempResultDataSet);
		if (i == 2) {
			tempResultData.sort(function (a, b) {
			    return a.toLowerCase().localeCompare(b.toLowerCase()); // ascending
			});
		} else {
			tempResultData.sort(function (a, b) {
				a = a.replace('$', '').replace(',', '');
				b = b.replace('$', '').replace(',', '');
			    return b - a // descending
			});
		}
		var firstElement = tempResultData[0];
		if (firstElement) {
			asResultData.push(firstElement);
		}
		for (var i = 1; i < tempResultData.length; i++) {
			if (tempResultData[i] != tempResultData[i-1]) {
				asResultData.push(tempResultData[i]);
			}
		}
		asResultData.push(''); // for Empty option
		return asResultData;
	}
	
	function escapeRegExp(string) {
		if (string != null) {
			return string.replace(/([.*+?^=!:$(){}|\[\]\/\\])/g, "\\\$1");
		}
	    return "";
	}
	
	function initDataTable(tablename) {
		$(tablename+" thead tr:last-child th").each( function (i) {
			if (i != 0 && i != 1) {
				this.innerHTML = fnCreateSelect(getColumnData(tablename, i));
			}
		});
		
		var oTable = $(tablename).dataTable({
		    "regex": true
		    ,"bPaginate": false
            ,"iDisplayLength": -1
            ,"bSortCellsTop": true
            ,"aoColumnDefs": [{ "sSortDataType": "dom-checkbox", "aTargets": [ 0 ] }]
            ,"aaSortingFixed": [[0,'asc']]
		});
		
		$(tablename+" thead tr:last-child th").each( function (i) {
			if (i != 0 && i != 1) {
				$('select', this).change(function() {
					var filterKey = $(this).val();
					if (filterKey != "^$") {
						filterKey = escapeRegExp(filterKey);
					}
					oTable.fnFilter($.trim(filterKey), i, true, false);
				});
			}
		});
		
		return oTable;
	}
	
	function toggleVasSelection(tablename) {
		$(tablename+"_wrapper").toggle();
		return $(tablename+"_wrapper").is(':visible');
	}
</script>

<form:form method="POST" name="vasDetailForm" commandName="vasDetail">
	<pccw-util:codelookup codeType='ENABLE_EXTRA_FUNCTION' var='enableExtraFunctionMap' />
	<pccw-util:codelookup codeType='CCS_STOCK_COUNT_EXCEPT_CATEGORY_ID'	var='exceptCategoryMap' />
	
	<form:errors path="errorMsg" cssClass="error" />
	<form:errors path="itemHtml" cssClass="error" />

	<!-- Golden Num VAS offer Authorization -->
	<c:if test='${vasDetail.showGoldenNumAuth eq "Y"}' >
	
		<c:if test='${ vasDetail.byPassGoldenNum eq "N"}'>
			<div id="goldenNumNotAuthDiv" style="color: #d63c00">
				<spring:message code="label.goldenVasNotAuthorized"/><a href="#" id="goldenNumAuthorizeButton"
					class="nextbutton3">Authorize</a>
			</div>
		</c:if>
		
		<%-- <c:if test='${ vasDetail.byPassGoldenNum eq "Y"}'>
			<div id="goldenNumAuthDiv" style="color: #d63c00">
				<spring:message code="label.goldenVasAuthorized"/>
			</div>
		</c:if> --%>
		
	</c:if>
	<form:hidden path="byPassGoldenNum" id="byPassGoldenNum"/>
		
	<spring:bind path="isQuotaExceededInd">
		<c:if test="${status.error}">
			<div style="color: #d63c00">
				Mob Quota Exceeded : <a href="#" id="quotaExceedAuthorizeButton"
					class="nextbutton3">Authorize</a>
			</div>
			<form:errors path="isQuotaExceededInd" cssClass="error" />
		</c:if>
	</spring:bind>
	
	<spring:bind path="ignoreQuotaCheck">
		<c:if test="${status.error}">
			<form:checkbox path="ignoreQuotaCheck" />
			<span class="error"><form:errors path="ignoreQuotaCheck"
					cssClass="error" />Tick the checkbox if you want to ignore it.</span>
		</c:if>
	</spring:bind>
	
	<br />
	<div id="scrollButton">
		<input id="scrollTableTop" type="button" value="&#8593;"><br/>
		<input id="scrollTableButton" type="button" value="&#8595;">
	</div>
	<table width="100%" border="1" class="tableGreytext" bgcolor="white">
		<c:if test='${not empty  pageMessage}'>
			<tr>
				<td colspan="2">${pageMessage}</td>
			</tr>
		</c:if>
		<tr>

			<c:if test='${mobileDetail.modelName != null}'>
				<td rowspan="2" valign="top"><img
					src="${mobileDetail.modelImagePath}" width="150">
					<p class="contenttext">
						Selected color: <span class="contenttext_blk">${selectedColorName}
						</span><br>
					</p></td>
			</c:if>
			<td>
				<table width="100%" border="0" bgcolor="#FFFFFF">
					<tr>
						<td colspan="2">${basketDisplayTitle}</td>
					</tr>
					<c:if test="${not empty basket.grossPlanFee }">
						<tr>
							<td width="20">&nbsp;</td>
							<td class="contenttextgreen">Gross Plan Fee: $<fmt:formatNumber
									value="${basket.grossPlanFee}" pattern="#,###.####" />
							</td>
						</tr>
					</c:if>
					<c:forEach items="${basket.basketMobItemQuotaInfoList}"
						var="basketQuotaItem">
						<tr>
							<td width="20">&nbsp;</td>
							<td class="contenttextgreen">
								${basketQuotaItem.quotaEngDesc}</td>
						</tr>
					</c:forEach>
					<tr>
						<td width="20">&nbsp;</td>
						<td>
							<table width="100%" border="0">
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup
									style="width: 85px; text-align: right; vertical-align: top"></colgroup>
								<colgroup
									style="width: 85px; text-align: right; vertical-align: top"></colgroup>
								<colgroup
									style="width: 75px; text-align: right; vertical-align: top"></colgroup>
								<c:if test='${channelId == "2"}'>
									<colgroup
										style="width: 95px; text-align: right; vertical-align: top"></colgroup>
									<colgroup
										style="width: 95px; text-align: right; vertical-align: top"></colgroup>
								</c:if>
								<tr>
									<td class="table_title table_title_2row" colspan="2"
										style="text-align: left"><span style="text-align: left">
											<spring:message code="label.basket.desc" /> <br>
										<spring:message code="label.basket.desc.1" />
									</span></td>

									<!-- <td class="table_title table_title_2row">
										<span>
											<spring:message code="label.basket.offerFunction"/>
										</span>
									</td>  -->


									<td class="table_title table_title_2row"><span> <spring:message
												code="label.basket.monthly" /> <br>
										<spring:message code="label.basket.monthly.1" />
									</span></td>
									<td class="table_title table_title_2row"><span> <spring:message
												code="label.basket.upfront" /> <br>
										<spring:message code="label.basket.upfront.1" />
									</span></td>
									<c:if test='${channelId == "2"}'>
										<td class="table_title table_title_2row"><span> <spring:message
													code="label.basket.stockQty" /> <br>
											<spring:message code="label.basket.stockQty.1" />
										</span></td>
										<td class="table_title table_title_2row"><span> <spring:message
													code="label.basket.itemCode" /> <br>
											<spring:message code="label.basket.itemCode.1" />
										</span></td>
									</c:if>
								</tr>
								<c:forEach items="${vasHSRPList}" var="vas">
									<tr>
										<td class="itemHtml">${vas.itemHtml} <c:if
												test="${param.showDetail == 'Y'}">
												<br>
											Brand: ${vas.mipBrand} <br /> 
											SimType: ${vas.mipSimType}
										</c:if>
										</td>

										<td><c:if test="${not empty itemFuncInfos[vas.itemId]}">
												<c:forEach items="${itemFuncInfos[vas.itemId]}"
													var="itemFunc">
													<button type="button"
														onclick="showItemFunctionPopup('${itemFunc.extraInfo}?vasitem=${vas.itemId}&funcid=${itemFunc.funcId}');">${itemFunc.funcDesc}</button>
												</c:forEach>
											</c:if></td>



										<td class="BGgreen2"><c:if
												test='${not empty vas.itemRecurrentAmt && vas.itemRecurrentAmt != 0}'>
											$<fmt:formatNumber value="${vas.itemRecurrentAmt}"
													pattern="#,###.####" />
										</c:if></td>
										<td class="BGgreen2"><c:set var="rpOneTimeAmt" value="0" />
											<c:choose>
												<c:when
													test='${not empty vas.itemOnetimeAmt && vas.itemOnetimeAmt != 0}'>
											$<fmt:formatNumber value="${vas.itemOnetimeAmt}"
														pattern="#,###.####" />
													<c:if test="${vas.itemType == 'RP'}">
														<c:set var="rpOneTimeAmt" value="${vas.itemOnetimeAmt}" />
														<input id="rpChargeHidden" value="${rpOneTimeAmt}"
															type="hidden" />
														<c:if
															test='${dummyWaiveRpPrepayItemExist =="Y" || (channelId == "1" || channelId == "2" || channelId == "3" || ( (channelId == "10" || channelId == "11") &&  customer.idDocType == "BS" ))}'>

															<c:if test='${vas.rpWaivable}'>
																<a href="#" id="firstMonthPrepayAuthorizeButton"
																	class="nextbutton3">Authorize</a>
																<div id="rpWaiveReasonSelectDiv">
																	<br>Waive Reason:<br> <select
																		id="rpWaiveReasonSelect">
																		<option value="" label="N/A">N/A</option>
																		<c:forEach var="rpWaiveReason"
																			items="${rpWaiveReasons}">
																			<option value="${rpWaiveReason.codeId}"
																				label="${rpWaiveReason.codeDesc}"
																				<c:if test="${vasDetail.rpWaiveReason eq rpWaiveReason.codeId}">selected</c:if>>
																				${rpWaiveReason.codeDesc}</option>
																		</c:forEach>
																	</select>
																</div>
															</c:if>

														</c:if>
													</c:if>
												</c:when>
												<c:otherwise>
													<c:if test="${vas.itemType == 'RP'}">
														<input id="rpChargeHidden" value="${rpOneTimeAmt}"
															type="hidden" />
													</c:if>
												</c:otherwise>
											</c:choose></td>
										<c:if test='${channelId == "2"}'>
											<td class="BGgreen2">${vas.stockCount}</td>
											<td class="BGgreen2">${vas.posItemCd}</td>
										</c:if>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table> <!-- 	Start System Assigned VAS	 -->
		<tr>
			<td bgcolor="#FFFFFF" colspan=2>
				<div>
					<table style="width: 100%; background-color: :" border="0">
						<tbody>
							<tr>
								<td width="20">&nbsp;</td>
								<td>
									<table style="width: 100%" class="vas_detail_list">
										<colgroup></colgroup>
										<tr>
											<th class="table_title table_title_2row" colspan="2"
												style="text-align: left">System Assigned VAS</th>
										</tr>
										</thead>
										<c:forEach items="${systemAssignVasDetailList}" var="item"
											varStatus="itemStatus">
											<tr>
												<td><input type="hidden" name="vasitem"
													value="${item.itemId}" />${item.itemHtml} <c:if
														test="${param.showDetail == 'Y'}">
														<br /> Brand: ${item.mipBrand} / SimType: ${item.mipSimType}
											</c:if></td>
											</tr>
										</c:forEach>
										<c:if test="${fn:length(systemAssignVasDetailList) eq 0}">
											<tr>
												<td colspan=2>None</td>
											</tr>
										</c:if>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</td>
		</tr>
		<!-- 	END System Assigned VAS	 -->

		<tr>
			<td colspan=2>


				<table width="100%" class="tablegrey" border="0">
					<!-- sim block -->
					<tr>
						<td><c:if test="${not empty missingItems}">
								<div class="groupedPcRelationshipList">
									<h3>Type: Prerequisite</h3>
									<c:forEach var="entry" items="${missingItems}">
										<ul class="group">
											<li><span style="display: inline-block; width: 3em">[<a
													class="primaryItemCodeLink" href="#">-</a>]
											</span> <span class="primaryItemDesc"><c:out
														value="${entry.key.sourceDesc}" /></span>
												<ul class="secondaryItem">
													<c:forEach items="${entry.value}" var="item">
														<li><c:out value="${item.targetDesc}" /></li>
													</c:forEach>
												</ul></li>
										</ul>
									</c:forEach>
								</div>
							</c:if> <c:if test='${basket != null}'>
								<table class="tableGreytext sim_sel"
									style="border-spacing: 2px; border: 0">
									<thead>
										<tr>
											<td class="table_title" colspan="8">SIM Selection<c:if
													test='${not empty basket.handsetSimSize}'> (Handset SIM Slot: ${basket.handsetSimSize})</c:if>
												<form:hidden path="simWaiveReason" /> <form:hidden
													path="simCharge" /> <form:hidden path="simWaivable" /> <form:hidden
													path="simExtraFunction" /> <form:hidden path="simType" />
												<form:hidden path="mipSimType" /> <form:hidden
													path="rpWaiveReason" /> <form:hidden path="rpCharge" />
											</td>
										</tr>
										<tr>
											<td class="table_title" style="text-align: center">Select</td>
											<td class="table_title" style="text-align: center">Code</td>
											<td class="table_title" style="text-align: center"><span
												style="text-align: left"> <spring:message
														code="label.basket.desc" />
											</span></td>
											<td class="table_title" style="text-align: center">SIM
												Type</td>
											<td class="table_title" style="text-align: center">Type</td>
											<td class="table_title" style="text-align: center">Charge</td>
											<td class="table_title" style="text-align: center">Waive
												Reason</td>
											<c:if test='${channelId == "2"}'>
												<td class="table_title"
													style="white-space: nowrap; text-align: center"><spring:message
														code="label.basket.stockQty" /> <br>
												<spring:message code="label.basket.stockQty.1" /></td>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${simSelectedDetailList}" var="simDetail"
											varStatus="status">
											<tr
												<c:if test="${status.index % 2 == 1}"> class="even"</c:if>>
												<td style="text-align: center"><form:radiobutton
														path="selectedSimItemId" value="${simDetail.itemId}"
														disabled='${channelId == "2" && (simDetail.stockCount == null || simDetail.stockCount <= 0)}' />
												</td>
												<td style="text-align: center"><span
													id="simItemCode_${simDetail.itemId}">${simDetail.itemCode}</span></td>
												<td style="text-align: left">${simDetail.itemDisplay}</td>
												<td style="text-align: left"><pccw-util:codelookup
														codeType="MIP_SIM_TYPE" codeId="${simDetail.mipSimType}"
														/></td>
												<td style="text-align: left"><span
													id="simExtraFunction_${simDetail.itemId}"
													style="display: none;">${simDetail.extraFunction}</span> <c:if
														test="${simDetail.extraFunction == 0}"></c:if> <c:if
														test="${simDetail.extraFunction == 1}">Bank NFC</c:if> <c:if
														test="${simDetail.extraFunction == 2}">Octopus</c:if> <c:if
														test="${simDetail.extraFunction == 3}">Octopus & Bank  NFC</c:if>
													<c:if test="${simDetail.extraFunction == 5}">AIO</c:if></td>
												<td style="text-align: center">$<span
													id="simCharge_${simDetail.itemId}">${simDetail.charge}</span></td>
												<td style="text-align: center"><span
													id="simWaivable_${simDetail.itemId}" style="display: none;">${simDetail.waivable}</span>
													<span id="simType_${simDetail.itemId}"
													style="display: none;">${simDetail.simType}</span> <c:if
														test="${simDetail.waivable}">
														<select id="simWaiveReason_${simDetail.itemId}">
															<option value="" label="N/A">N/A</option>
															<c:forEach var="waiveReason" items="${waiveReasons}">
																<option value="${waiveReason.codeId}"
																	label="${waiveReason.codeDesc}"
																	<c:if test="${vasDetail.simWaiveReason eq waiveReason.codeId}">selected</c:if>>
																	${waiveReason.codeDesc}</option>
															</c:forEach>
														</select>
													</c:if></td>
												<c:if test='${channelId == "2"}'>
													<td style="text-align: center">${simDetail.stockCount}</td>
												</c:if>
											<tr>
										</c:forEach>
									</tbody>

								</table>
							</c:if></td>
					</tr>

					<!-- end sim block -->

					<tr>
						<td bgcolor="#FFFFFF">
							<!--content vas-->
							<div>
								<table width="100%" border="0" cellspacing="0"
									class="tableGreytext" bgcolor="white">


									<!-- original selected vas list -->
									<c:if test='${originalBasket.dummyBasketInd == "Y"}'>
									</c:if>
									<c:if test='${not empty selectedVasList}'>

										<tr>
											<td>
												<table width="100%" cellspacing="1" BORDER="1" BORDERCOLOR="RED">
													<tr>
														<td>
															<font Class="error"><b>Warning: Below VAS cannot be subscribed due to basket change</b></font>
														</td>
													</tr>
													<tr>
														<td>
															<table style="width: 100%">
																<colgroup></colgroup>
																<colgroup style="width: 85px; text-align: right; vertical-align: top"></colgroup>
																<colgroup style="width: 75px; text-align: right; vertical-align: top"></colgroup>
																<tr>
																	<td class="table_title table_title_2row" style="text-align: left">
																		<span style="text-align: left">
																			<spring:message code="label.basket.desc" /> <br>
																			<spring:message code="label.basket.desc.1" />
																		</span>
																	</td>
																	<td class="table_title table_title_2row">
																		<span>
																			<spring:message code="label.basket.monthly" /> <br>
																			<spring:message code="label.basket.monthly.1" />
																		</span>
																	</td>
																	<td class="table_title table_title_2row">
																		<span>
																			<spring:message code="label.basket.upfront" /> <br>
																			<spring:message code="label.basket.upfront.1" />
																		</span>
																	</td>
																</tr>
																<c:forEach items="${selectedVasList}" var="vas">
																	<tr>
																		<td>${vas.itemHtml}</td>
																		<td>
																			<c:if test='${not empty vas.itemRecurrentAmt && vas.itemRecurrentAmt != 0}'>
																			$<fmt:formatNumber value="${vas.itemRecurrentAmt}" pattern="#,###.####" />
																			</c:if>
																		</td>
																		<td>
																			<c:if test='${not empty vas.itemOnetimeAmt && vas.itemOnetimeAmt != 0}'>
																			$<fmt:formatNumber value="${vas.itemOnetimeAmt}" pattern="#,###.####" />
																			</c:if>
																		</td>
																	</tr>
																</c:forEach>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</c:if>

									<tr>
										<td><%@ page
												import="java.util.ArrayList, java.util.Collections, java.util.Iterator, java.util.LinkedHashMap, java.util.List, java.util.Map"%>
											<%@ page
												import="com.bomwebportal.dto.PcRelationshipDTO, com.bomwebportal.dto.PcRelationshipDTO.RelType, com.bomwebportal.dto.VasDetailDTO,com.bomwebportal.mob.ccs.util.MobCcsSessionUtil"%>
											<%!boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	// group the relationshipList by relationshipType + primaryItemCode -> PcRelationshipDTO
	Map<RelType, Map<String, List<PcRelationshipDTO>>> groupPcRelationshipList(
			List<PcRelationshipDTO> pcRelationshipList) {
		if (this.isEmpty(pcRelationshipList)) {
			return Collections.emptyMap();
		}
		Map<RelType, Map<String, List<PcRelationshipDTO>>> map = new LinkedHashMap<RelType, Map<String, List<PcRelationshipDTO>>>();
		for (PcRelationshipDTO dto : pcRelationshipList) {
			Map<String, List<PcRelationshipDTO>> primaryItemCodeMap;
			RelType relType = RelType.valueOf(dto.getRelationship());
			if (map.containsKey(relType)) {
				primaryItemCodeMap = map.get(relType);
			} else {
				primaryItemCodeMap = new LinkedHashMap<String, List<PcRelationshipDTO>>();
				map.put(relType, primaryItemCodeMap);
			}
			List<PcRelationshipDTO> primaryItemCodePcRelationshipList;
			if (primaryItemCodeMap.containsKey(dto.getPrimaryItemCode())) {
				primaryItemCodePcRelationshipList = primaryItemCodeMap.get(dto.getPrimaryItemCode());
			} else {
				primaryItemCodePcRelationshipList = new ArrayList<PcRelationshipDTO>();
				primaryItemCodeMap.put(dto.getPrimaryItemCode(), primaryItemCodePcRelationshipList);
			}
			primaryItemCodePcRelationshipList.add(dto);
		}
		return map;
	}%> <%
 	{
 			VasDetailDTO vasDetail = (VasDetailDTO) request.getAttribute("vasDetail");
 			if (vasDetail != null) {
 				List<PcRelationshipDTO> pcRelationshipList = vasDetail.getPcRelationshipList();
 				if (!isEmpty(pcRelationshipList)) {
 					pageContext.setAttribute("groupedPcRelationshipList",
 							groupPcRelationshipList(pcRelationshipList));
 				}
 			}
 		}
 %> <c:if test="${not empty groupedPcRelationshipList}">
												<div class="groupedPcRelationshipList">
													<c:forEach var="entry" items="${groupedPcRelationshipList}">
														<h3>
															Type:
															<c:out value="${entry.key.desc}" />
														</h3>
														<c:forEach var="primaryItemCodeMap" items="${entry.value}">
															<ul class="group">
																<li><span style="display: inline-block; width: 3em">[<a
																		class="primaryItemCodeLink" href="#">+</a>]
																</span> <span class="primaryItemDesc">${primaryItemCodeMap.key}</span>
																	<ul class="secondaryItem" style="display: none">
																		<c:forEach var="pcRelationshipDto"
																			items="${primaryItemCodeMap.value}">
																			<li><c:out
																					value="${pcRelationshipDto.secondaryItemCode}" /></li>
																		</c:forEach>
																	</ul></li>
															</ul>
														</c:forEach>
													</c:forEach>
												</div>
											</c:if></td>
									</tr>
									<!-- 
										bomsalesuser.channelId: ${bomsalesuser.channelId}
										basket.hsExtraFunction: ${basket.hsExtraFunction} 
									-->

									<!-- end of original selected vas list-->
									
									<tr>
										<td>
											<div class="error">
												<spring:bind path="vasitem">
													<c:forEach items="${status.errorMessages}" var="error">
														${error}<br />
													</c:forEach>
												</spring:bind>
											</div>
											<div id="ignoreEagleAPICheckDic" style="display:none">
												<form:checkbox path="ignoreEagleAPICheck" />
											</div>
										</td>
									</tr>
									
									<!-- Start Special VAS -->
									<tr>
										<td>
											<div class="table_title" id="special_vas_detail_table_title" style="margin: 0 2px">
												<span style="display: inline-block; padding-top: 5px; float: left">
													<span id="special_vas_toogle">[-]</span> Special VAS Selection
												</span>
												<div style="clear: both"></div>
											</div>

											<div style="clear: both"></div>

											<sb-ui:vasselectionlist channelId="${channelId}" vasList="${specialVasList}" showDetail="${param.showDetail}" tableId="special_vas_detail_table"/>
										</td>
									</tr>
									<tr><td><hr></td></tr>
									<!-- End Special VAS -->
									
									<!-- Start VAS -->
									<c:if test="${not empty vasDetail.minVas and vasDetail.minVas > 0}">
										<tr>
											<td>
												<c:choose>
												<c:when test="${vasDetail.minVasAuthInd == 'Y'}">
													<span style="color: #d63c00">Min VAS requirement has been by-pass.</span>
													<form:errors path="minVas" cssClass="error" />
												</c:when>
												<c:otherwise>
													<div id="minVasNotAuthorizedDiv">
														<span style="color: #d63c00">Min. VAS requirement : </span>
														<a href="#" id="minVasAuthorizeButton" class="nextbutton3">By-pass</a> <br/>
														
														<form:errors path="minVas" cssClass="error" />
													</div>
													<div id="minVasAuthorizedDiv" style="display:none">
														<span style="color: #d63c00">Min VAS requirement has been by-pass.</span>
													</div>
												</c:otherwise>
												</c:choose>
												
												<form:hidden path="minVasAuthInd" />
											</td>
										</tr>
									</c:if>
									
									<tr>
										<td>
											<div class="table_title" id="vas_vas_detail_table_title" style="margin: 0 2px">
												<span id="vas_vas_toogle">[-]</span> VAS Selection
												<c:if test="${not empty vasDetail.minVas and vasDetail.minVas > 0}">
												 <span style="color:yellow">($<fmt:formatNumber value="${vasDetail.minVas}" pattern="#,###" /> x ${basket.contractPeriod} mth) [Group: ${vasDetail.hardBundledGrpId}]</span>
												</c:if>
											</div>

											<div style="clear: both"></div>

											<sb-ui:vasselectionlist channelId="${channelId}" vasList="${vasList}" showDetail="${param.showDetail}" tableId="vas_vas_detail_table"/>
										</td>
									</tr>
									<tr><td><hr></td></tr>
									<!-- End VAS -->
									
									<!-- Start Premium -->
									<tr>
										<td>
											<div class="table_title" id="premium_vas_detail_table_title" style="margin: 0 2px">
												<span style="display: inline-block; padding-top: 5px; float: left">
													<span id="premium_vas_toogle">[-]</span> Premium/HS Selection
												</span>
												<div style="clear: both"></div>
											</div>

											<div style="clear: both"></div>

											<sb-ui:vasselectionlist channelId="${channelId}" vasList="${premiumList}" showDetail="${param.showDetail}" tableId="premium_vas_detail_table"/>
										</td>
									</tr>
									<!-- End Premium -->
									
								</table>

								<input type="hidden" name="brand" value="${selectedBrandId}" />
								<input type="hidden" name="model" value="${selectedModelId}" />
								<input type="hidden" name="color" value="${selectedColorId}" />
								<input type="hidden" name="basket" value="${selectedBasketId}" />
								<input type="hidden" name="hsPosItemCd"
									value="${basket.hsPosItemCd}" /> <input type="hidden"
									name="addVasItem" id="addVasItem" value="N" />

							</div> <!-- end content vas -->
						</td>
					</tr>
				</table>
			</td>

		</tr>
	</table>
	<!-- end   -->



	<!-- button -->
	<div style="clear: both"></div>
	<div class="buttonmenubox_R" id="buttonArea">
		<c:if test='${channelId == "2"}'>
			<c:if
				test='${!(originalOrderType=="PRE" || originalOrderType=="PEND")}'>
				<a href="#" class="nextbutton3"
					onClick="javascript:formSubmit('saveDraft',event);">Proceed As Draft
					Order</a>
			</c:if>
		</c:if>
		<a href="#" class="nextbutton3"
			onClick="javascript:formSubmit('next',event);">continue &gt;</a>
	</div>
	<div style="clear: both"></div>
	<!-- end of button -->
	<input type="hidden" name="currentView" value="vasdetail" />
	<input type="hidden" name="nextAction" value="" />
</form:form>

<div id="authDialog"></div>
<div id="capacityPassDialog" class="dialog" title="Subscription of data capacity package" style="display:none;">
		 <spring:message code="label.dataCapacityPackage"/>
</div>
<!-- -------------------------------------  end content ----------------------------------------- -->


<%@ include file="/WEB-INF/jsp/footer.jsp"%>