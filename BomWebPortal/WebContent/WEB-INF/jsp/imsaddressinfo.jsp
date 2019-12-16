<jsp:include page="/WEB-INF/jsp/header.jsp">
	<jsp:param name="enableOnloadJScript" value="Y" />
</jsp:include>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.modalDialog_1ams.js?v=<fmt:formatDate pattern="yyyyMMddHH24mmss" value="<%=new java.util.Date()%>" />"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<!--  <script type="text/javascript" charset="utf-8" src="js/jquery-1.4.4.js"></script>-->
<script type="text/javascript" charset="utf-8" src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.imsautocomplete.js"></script> 
<script type="text/javascript" charset="utf-8" src="js/inputInterceptorIms.js"></script>
<%@ include file="imsloadingpanel.jsp" %>

<!-- <link rel="stylesheet" type="text/css" href="./css/ims_prereg.css" /> -->

<script type="text/javascript" charset="utf-8">
	var areaSearchInput = "";
	var districtSearchInput = "";
	var sectionSearchInput = "";
	var typingTimer;
	var doneTypingInterval = 800;
	var popupReset = true;
	var popupPrevSBNo = '';
	
	String.prototype.trim = function() {
	    return this.replace(/^\s+|\s+$/g,"");
	};
	String.prototype.ltrim = function() {
	    return this.replace(/^\s+/,"");
	};
	String.prototype.rtrim = function() {
	    return this.replace(/\s+$/,"");
	};

	// START AJAX
// 	$(document).ready(function() {
// 		alert("${isShopCodeValid}");
// 		alert("${!isShopCodeValid eq 'N'}");
// 		init();
// 	});
	
	$(document).ready(function() {
		init();
		

		if($("#isPon4Village").val()=="Y" ){
			$("#high_speed_pre_reg").css('display','');
		}else{
			$("#high_speed_pre_reg").css('display','none');
		}
		
		
		
		//if("${ImsOrder.orderActionInd}" == "W" && ("${ImsOrder.orderStatus}" == "31" || "${ImsOrder.orderStatus}" == "32") ){
		if("${ImsOrder.orderActionInd}" == "W" && "${addressinfo.approvalRequested}" == "Y"){
			$(":input").attr("disabled", true);
		}
		if("${ImsOrder.mobileOfferInd}" !=null && "${ImsOrder.mobileOfferInd}" == "Y" ){		
			document.getElementById('cslMobileOffer').style.display = "";	
			document.getElementById('cslDocTypeSelect').value = "${ImsOrder.customer.mobileCutomerInfo.idDocType}";			
			$('input[name=cslDocNum]').val("${ImsOrder.customer.mobileCutomerInfo.idDocNum}");
			$('input[name=cslMobileNum]').val("${ImsOrder.mrt}");
			document.getElementById('cslOrder_checkOk').style.display = "";
		}else if ("${ImsOrder.mobileOfferInd}" == "N" && "${ims_direct_sales}" == "false" && "${addressinfo.cslOfferEnableInd}" == "Y"){
			document.getElementById('cslMobileOffer').style.display = "";	
		}else{
			document.getElementById('cslMobileOffer').style.display = "none";	
		}
		
		if(("${ImsOrder.preRegSubmitted}" != null && "${ImsOrder.preRegSubmitted}" == "Y" )|| ($("#isPreReged").val()!= null && $("#isPreReged").val()=="Y")){
			 $("#preregbtn").removeClass("nextbutton");
			 $("#preregbtn").addClass("nextbuttonPrereg");
			document.getElementById("preregBtn").disabled = true;
		}
		
	});
	
	
	
	$(document).ready(function() {			


		function loadDistrict(parentid) {
			$.ajax({
				url : 'imsaddressdistrictlookup.html?T=DISTRICT&ID=' + parentid,
				type : 'POST',
				dataType : 'json',
				timeout : 25000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				     if(textStatus=='parsererror'){
// 						alert("Your session has been timed out, please re-login."); 
						alert("<spring:message code="ims.pcd.addressinfo.msg.003"/>");
				     } else {
// 				    	 alert("Error loading district data!");
				    	 alert("<spring:message code="ims.pcd.addressinfo.msg.004"/>");
				     }
				},
				success : function(msg) {
					$("#distNoSelect").empty();
					$.each(eval(msg), function(i, item) {
						if (item.id == "${installAddress.distNo}") {
							$("<option value='" + item.id + "' selected='selected'>"
							+ item.name + "</option>").appendTo($("#distNoSelect"));
						} else {
							$("<option value='" + item.id + "' >"
							+ item.name + "</option>").appendTo($("#distNoSelect"));
						}
					});
					$('#distNoSelect').trigger("change");
				}
			});
		}

		function loadSection(parentid) {
			var varual = '';
			varual = 'imsaddressdistrictlookup.html?T=SECTION&ID=' + parentid + '&SECTIONALL=false';
			$.ajax({
				url : varual,
				type : 'POST',
				dataType : 'json',
				timeout : 25000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				     if(textStatus=='parsererror'){
// 						alert("Your session has been timed out, please re-login."); 
						alert("<spring:message code="ims.pcd.addressinfo.msg.003"/>");
				     } else {
// 				    	alert("Error loading section data!");
				    	alert("<spring:message code="ims.pcd.addressinfo.msg.010"/>");
				     }
				},
				success : function(msg) {
					$("#sectCdSelect").empty();
					$.each(eval(msg), function(i, item) {
						if (item.id == "${installAddress.sectCd}") {
							$("<option value='" + item.id + "' selected='selected'>"
							+ item.name + "</option>").appendTo($("#sectCdSelect"));
						} else {
							$("<option value='" + item.id + "' >"
							+ item.name + "</option>").appendTo($("#sectCdSelect"));
						}
					});
					$('#sectCdSelect').trigger("change");
				},
				complete : function() {
// 					$("#loaderImagePlaceholder").empty();
				},
				beforeSend : function() {
// 					$("#loaderImagePlaceholder").empty().html(
// 						"<font color='red'>Please wait... </font>");
				}
			});
		}
		
		$("#areaCdSelect").change(
			function() {
				if ($("#areaCdSelect").val() == "") {
					//alert("area empty");
					$("#distNoSelect").empty();
					$("#sectCdSelect").empty();
					areaSearchInput = " ";
					districtSearchInput = " ";
					sectionSearchInput = " ";
				} else {
					loadDistrict($("#areaCdSelect").val());
					$("#installAddress.areaCd").val($("#areaCdSelect").val());
					$("#installAddress.areaDesc").val($("#areaCdSelect option:selected").text());
					areaSearchInput = " ," + $("#areaCdSelect option:selected").text() + ", ";
				}
			});

		$("#distNoSelect").change(
			function() {
				sectionSearchInput = " ";
				if (($("#distNoSelect").val()) == "") {
					//alert("district empty");
					$("#sectCdSelect").empty();
					districtSearchInput = " ";
					sectionSearchInput = " ";
				} else {
					loadSection($("#distNoSelect").val());
					$("#installAddress.distNo").val($("#distNoSelect").val());
					$("#installAddress.distDesc").val($("#distNoSelect option:selected").text());
					districtSearchInput = " " + $("#distNoSelect option:selected").text() + ",";
					document.imsaddressForm.imsSearchFrom.value = 'installAddress.quickSearchDis';
					
					document.getElementById("quickSearchSBMsg").innerHTML = "";
					document.getElementById("quickSearchMsg").innerHTML = "";
					
					$('.ac_results').remove();
					
					$("select[id=distNoSelect]").autocomplete("imsaddresslookup.html?type=dist", {
						//minChars: 4,
						//minChars : 6,
						delay : 600,
						selectFirst : false,
						max : 30,
						matchSubset : false,
						highlight : false,
						//extraParams: {random: (function(){return Math.random()})},
						formatItem : function(row, i, max) {
							return row;
						}
					}); 
					
					setTimeout(function(){
						$("#debug").append("timeout\n");
						$("#distNoSelect").trigger("focus");
						$("#distNoSelect").trigger("focus");
						$("#distNoSelect").trigger("click");
					});
				}
			});

		$("#sectCdSelect").change(
			function() {
				$("#installAddress.sectCd").val($("#sectCdSelect").val());
				$("#installAddress.sectDesc").val($("#sectCdSelect option:selected").text());
				sectionSearchInput = " " + $("#sectCdSelect option:selected").text() + ",";
				
				document.getElementById("quickSearchSBMsg").innerHTML = "";
				document.getElementById("quickSearchMsg").innerHTML = "";
				
				document.imsaddressForm.imsSearchFrom.value = 'installAddress.quickSearchDis';
				
				$('.ac_results').remove();
				
				$("select[id=sectCdSelect]").autocomplete("imsaddresslookup.html?type=dist", {
					//minChars: 4,
					//minChars : 6,
					delay : 600,
					selectFirst : false,
					max : 30,
					matchSubset : false,
					highlight : false,
					//extraParams: {random: (function(){return Math.random()})},
					formatItem : function(row, i, max) {
						return row;
					}

				});
				
				setTimeout(function(){
					$("#sectCdSelect").trigger("focus");
					$("#sectCdSelect").trigger("focus");
					$("#sectCdSelect").trigger("click");
				});
				
				});
		
		$('#areaCdSelect,#distNoSelect,#sectCdSelect').click(function(){

			allClearDis();
			//$('.ac_results').remove();		//clear previous autocomplete result   
		});
		
		$("input[id=installAddress.quickSearch]").autocomplete("imsaddresslookup.html?type=address", {
			//minChars: 4,
			minChars : 1,
			delay : 600,
			selectFirst : false,
			max : 30,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}

		});
		
		$("input[id=installAddress.quickSearchSB]").autocomplete("imsaddresslookup.html?type=sb", {
			//minChars: 4,
			minChars : 6,
			delay : 600,
			selectFirst : false,
			max : 30,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}

		});
		
		/*$("select[id=areaCdSelect]").autocomplete("imsaddresslookup.html?type=dist", {
			//minChars: 4,
			//minChars : 6,
			delay : 600,
			selectFirst : false,
			max : 30,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}

		});*/
		
		$("select[id=distNoSelect]").autocomplete("imsaddresslookup.html?type=dist", {
			//minChars: 4,
			//minChars : 6,
			delay : 600,
			selectFirst : false,
			max : 30,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}

		});
		
		$("select[id=sectCdSelect]").autocomplete("imsaddresslookup.html?type=dist", {
			//minChars: 4,
			//minChars : 6,
			delay : 600,
			selectFirst : false,
			max : 30,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}

		}); 
	});
	
	// END AJAX
	function insertPageTrack(){
		var staffId = "<c:out value="${bomsalesuser.username}"/>";
			$.ajax({
				url : "pagetrack.html?page=imsaddressinfo&func=rollout&staffId="+staffId,
				type : 'POST',
				dataType : 'json',
				timeout : 25000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				     if(textStatus=='parsererror'){
// 						alert("Your session has been timed out, please re-login."); 
						alert("<spring:message code="ims.pcd.addressinfo.msg.003"/>");
				     }
				},
				success: function(data){				
				return;
				}
			});	
		
		}
	
	function formSubmit() {
		var valid = true;

		$(".noSymbol").each(function(i ,e){
			if ($(e).html().length >0 ) {
				valid = false;
			} 
		});
		if (document.getElementById('isError1').value == "Y" ||
			document.getElementById('isError2').value == "Y" || !valid) { 
// 			alert("Please refer to the error shown in the page.");
			alert("<spring:message code="ims.pcd.addressinfo.msg.007"/>");
		} else {
			if($("[id='installAddress.unitNo']").val().length==0){  
				if (!confirm("<spring:message code="ims.pcd.addressinfo.msg.005"/>")) 
				
					return; 
			}
			
			//alert("prevSbNo==>" + document.getElementById('prevSbNo').value + "<" + "currSbNo==>" + document.getElementById('installAddress.serbdyno').value + "<");
			if (document.getElementById('prevSbNo').value == null ||
				document.getElementById('prevSbNo').value == "" ||
				document.getElementById('prevSbNo').value == document.getElementById('installAddress.serbdyno').value) {
				cleanCslInfo();
			
					submitShowLoading();
					$(":input").attr("disabled", false);
					document.imsaddressForm.submit();
			} else {
				var answer = confirm("<spring:message code="ims.pcd.addressinfo.msg.006"/>");
						if (answer) {
					
				cleanCslInfo();
					submitShowLoading();
					$(":input").attr("disabled", false);
					document.imsaddressForm.submit();
				}
			}
		}
	}
	
	
	function clearAddr() {
		
		document.getElementById('installAddress.quickSearch').value = "";
		document.getElementById('installAddress.quickSearchSB').value = "";
		document.getElementById('installAddress.quickSearch').readOnly = false;
		document.getElementById('installAddress.quickSearchSB').readOnly = false;

		document.getElementById('installAddress.unitNo').value = "";
		document.getElementById('installAddress.floorNo').value = "";
		document.getElementById('installAddress.unitNo').readOnly = true;
		document.getElementById('installAddress.floorNo').readOnly = true;

		document.getElementById('installAddress.hiLotNo').value = "";
		document.getElementById('installAddress.buildNo').value = "";
		document.getElementById('installAddress.strNo').value = "";
		document.getElementById('installAddress.strName').value = "";
		document.getElementById('installAddress.strCatDesc').value = "";
		document.getElementById('installAddress.strNo').readOnly = true;
		document.getElementById('installAddress.strName').readOnly = true;
		document.getElementById('installAddress.strCatDesc').readOnly = true;
		document.getElementById('installAddress.hiLotNo').readOnly = true;
		document.getElementById('installAddress.buildNo').readOnly = true;

		document.getElementById('installAddress.sectCd').value = "";
		document.getElementById('installAddress.distNo').value = "";
		document.getElementById('installAddress.areaCd').value = "";
		document.getElementById('installAddress.sectDesc').value = "";
		document.getElementById('installAddress.distDesc').value = "";
		document.getElementById('installAddress.areaDesc').value = "";
		document.getElementById('installAddress.sectDesc').readOnly = true;
		document.getElementById('installAddress.distDesc').readOnly = true;
		document.getElementById('installAddress.areaDesc').readOnly = true;
		document.getElementById('brm_vas_desc').style.display = "none";
		//enableAddrSelect();

		document.getElementById('installAddress.coverPe1').checked = false;
		document.getElementById('installAddress.coverEyeX1').checked = false;  

		$(".noSymbol").each(function(i ,e){
			$(e).html("");   
		});
	}
	
	function readOnlyAddr() {
		
		document.getElementById('installAddress.strNo').readOnly = true;
		document.getElementById('installAddress.strName').readOnly = true;
		document.getElementById('installAddress.strCatDesc').readOnly = true;
		document.getElementById('installAddress.hiLotNo').readOnly = true;
		document.getElementById('installAddress.buildNo').readOnly = true;
		document.getElementById('installAddress.sectDesc').readOnly = true;
		document.getElementById('installAddress.distDesc').readOnly = true;
		document.getElementById('installAddress.areaDesc').readOnly = true;

	}
	
	function clearInfo() {
		
		document.imsaddressForm.hosType.value = "";
		document.imsaddressForm.ponSrd.value = "";
		document.imsaddressForm.adslSrd.value = "";
		document.imsaddressForm.vdslSrd.value = "";
		document.imsaddressForm.vectorSrd.value = "";
		//kinman
		document.imsaddressForm.ponInstFee.value = "";
		document.imsaddressForm.adslInstFee.value = "";
		document.imsaddressForm.vdslInstFee.value = "";
		document.imsaddressForm.vectorInstFee.value = "";
		//
		
		document.imsaddressForm.hosType.readOnly = true;
		document.imsaddressForm.ponSrd.readOnly = true;
		document.imsaddressForm.adslSrd.readOnly = true;
		document.imsaddressForm.vdslSrd.readOnly = true;
		document.imsaddressForm.vectorSrd.readOnly = true;
		//kinman
		document.imsaddressForm.ponInstFee.readOnly = true;
		document.imsaddressForm.adslInstFee.readOnly = true;
		document.imsaddressForm.vdslInstFee.readOnly = true;
		document.imsaddressForm.vectorInstFee.readOnly = true;
		//
		
		$("#hosType").hide();
		$("#ponSrd").hide();
		$("#adslSrd").hide();
		$("#vdslSrd").hide();
		$("#vectorSrd").hide();
		//kinman
		$("#ponInstFee").hide();
		$("#adslInstFee").hide();
		$("#vdslInstFee").hide();
		$("#vectorInstFee").hide();
		//
	}
	
	function hideMsg() {
		
    	document.getElementById('warning_addr_1').style.visibility = "hidden";
    	document.getElementById('warning_addr_1').hidden = true;
    	document.getElementById('warning_addr_1').style.display = "none";
    	document.getElementById('warning_addr_2').style.visibility = "hidden";
    	document.getElementById('warning_addr_2').hidden = true;
    	document.getElementById('warning_addr_2').style.display = "none";
    	document.getElementById('warning_addr_3').style.visibility = "hidden";
    	document.getElementById('warning_addr_3').hidden = true;
    	document.getElementById('warning_addr_3').style.display = "none";
    	document.getElementById('warning_addr_4').style.visibility = "hidden";
    	document.getElementById('warning_addr_4').hidden = true;
    	document.getElementById('warning_addr_4').style.display = "none";
    	document.getElementById('warning_addr_5').style.visibility = "hidden";
    	document.getElementById('warning_addr_5').hidden = true;
    	document.getElementById('warning_addr_5').style.display = "none";
    	document.getElementById('error_addr_1').style.visibility = "hidden";
    	document.getElementById('error_addr_1').hidden = true;
    	document.getElementById('error_addr_1').style.display = "none";
    	document.getElementById('error_addr_2').style.visibility = "hidden";
    	document.getElementById('error_addr_2').hidden = true;
    	document.getElementById('error_addr_2').style.display = "none";
    	document.getElementById('sbPopUp').style.visibility = "hidden";
    	document.getElementById('sbPopUp').hidden = true;
    	document.getElementById('sbPopUp').style.display = "none";
    	document.getElementById('error_no_pon10G').style.visibility = "hidden";
    	document.getElementById('error_no_pon10G').hidden = true;
    	document.getElementById('error_no_pon10G').style.display = "none";
    	
	}

	function clearAddrSelect() {
		
		document.getElementById('installAddress.sectCd').value = "";
		document.getElementById('installAddress.distNo').value = "";
		document.getElementById('installAddress.areaCd').value = "";
		
		document.getElementById('installAddress.sectDesc').value = "";
		document.getElementById('installAddress.distDesc').value = "";
		document.getElementById('installAddress.areaDesc').value = "";

		areaSearchInput = "";
		districtSearchInput = "";
		sectionSearchInput = "";
		
		document.getElementById('sectCdSelect').selectedIndex = 0;
		document.getElementById('distNoSelect').selectedIndex = 0;
		document.getElementById('areaCdSelect').selectedIndex = 0;

		$("#distNoSelect").empty();
		$("#sectCdSelect").empty();
		
	}
	
	function enableAddrSelect() {
		
		document.getElementById('installAddress.sectDesc').style.visibility = "hidden";
		document.getElementById('installAddress.distDesc').style.visibility = "hidden";
		document.getElementById('installAddress.areaDesc').style.visibility = "hidden";
		$("#sectCdSelect").show();
		$("#distNoSelect").show();
		$("#areaCdSelect").show();
		document.getElementById('sectCdSelect').disabled = false;
		document.getElementById('distNoSelect').disabled = false;
		document.getElementById('areaCdSelect').disabled = false;

	}
	
	function disableAddrSelect() {
		
		document.getElementById('installAddress.sectDesc').style.visibility = "visible";
		document.getElementById('installAddress.distDesc').style.visibility = "visible";
		document.getElementById('installAddress.areaDesc').style.visibility = "visible";
		document.getElementById('installAddress.sectDesc').readOnly = true;
		document.getElementById('installAddress.distDesc').readOnly = true;
		document.getElementById('installAddress.areaDesc').readOnly = true;
		$("#sectCdSelect").hide();
		$("#distNoSelect").hide();
		$("#areaCdSelect").hide();
		document.getElementById('sectCdSelect').disabled = true;
		document.getElementById('distNoSelect').disabled = true;
		document.getElementById('areaCdSelect').disabled = true;

	}
	
	function keyUpOnQuickSearch() {
		
	//	document.getElementById('installAddress.quickSearch').value = document.getElementById('installAddress.quickSearch').value.toUpperCase();
		
	}
	
	function keyUpOnQuickSearchSB() {
		
	//	document.getElementById('installAddress.quickSearchSB').value = document.getElementById('installAddress.quickSearchSB').value.toUpperCase();
		
	}
	
	function keyUpOnUnitNo() {
		
		document.getElementById('installAddress.unitNo').value = document.getElementById('installAddress.unitNo').value.toUpperCase();
		
	}
	
	function keyUpOnFloorNo() {
		
		document.getElementById('installAddress.floorNo').value = document.getElementById('installAddress.floorNo').value.toUpperCase();
		
		clearTimeout(typingTimer);
		typingTimer = setTimeout(function(){validAddressInfoByFF(document.getElementById('installAddress.serbdyno').value, document.getElementById('installAddress.floorNo').value.trim(), document.getElementById('installAddress.unitNo').value.trim(), document.getElementById('phInd').value.trim());}, doneTypingInterval);
		
		
	}
	
	function blurOnUnitNo() {
		
		document.getElementById('installAddress.unitNo').value = document.getElementById('installAddress.unitNo').value.trim().toUpperCase();
		
	}
	
	function blurOnFloorNo() {

		document.getElementById('installAddress.floorNo').value = document.getElementById('installAddress.floorNo').value.trim().toUpperCase();
		
	}
	
	function keyDownOnFloorNo() {

		if (document.getElementById('installAddress.floorNo.errors') != null) {
			document.getElementById('installAddress.floorNo.errors').value = "";
		}
		clearTimeout(typingTimer);
	}
	
	function clickOnFloorNo(){

		if (document.getElementById('installAddress.floorNo.errors') != null) {
	    	document.getElementById('installAddress.floorNo.errors').style.visibility = "hidden";
	    	document.getElementById('installAddress.floorNo.errors').hidden = true;
	    	document.getElementById('installAddress.floorNo.errors').style.display = "none";
		}
		
	}
	
	function keyDownOnQuickSearch() {
		
		document.getElementById("quickSearchMsg").innerHTML = "";
		
	}
	
	function keyDownOnQuickSearchSB() {
		
		document.getElementById("quickSearchSBMsg").innerHTML = "";
		
	}


	function allClear() {
		popupReset = true;
		if (document.getElementById('commonErrorArea.errors') != null) {
			document.getElementById('commonErrorArea.errors').style.visibility = "hidden";
			document.getElementById('commonErrorArea.errors').hidden = true;
			document.getElementById('commonErrorArea.errors').style.display = "none";
		}
		if (document.getElementById('installAddress.quickSearch').disabled == false || document.getElementById('installAddress.quickSearchSB').disabled == false) {
			clearAddr();
		
			clearAddrSelect();
		
			//enableAddrSelect();

			clearInfo();
		
			hideMsg();
		
			if (document.getElementById('installAddress.quickSearch.errors') != null) {
    			document.getElementById('installAddress.quickSearch.errors').style.visibility = "hidden";
    			document.getElementById('installAddress.quickSearch.errors').hidden = true;
    			document.getElementById('installAddress.quickSearch.errors').style.display = "none";
			}
		
			if (document.getElementById('installAddress.quickSearchSB.errors') != null) {
    			document.getElementById('installAddress.quickSearchSB.errors').style.visibility = "hidden";
    			document.getElementById('installAddress.quickSearchSB.errors').hidden = true;
    			document.getElementById('installAddress.quickSearchSB.errors').style.display = "none";
			}

			if (document.getElementById('installAddress.floorNo.errors') != null) {
		    	document.getElementById('installAddress.floorNo.errors').style.visibility = "hidden";
		    	document.getElementById('installAddress.floorNo.errors').hidden = true;
		    	document.getElementById('installAddress.floorNo.errors').style.display = "none";
			}

			document.getElementById('brm_vas_desc').style.display = "none";
			
			document.getElementById('isError1').value = "N";
			document.getElementById('isError2').value = "N";
  		  	document.getElementById('installAddress.blacklistInd').value = "N";
  		  	
  		  	document.getElementById('installAddress.serbdyno').value = "";
  			
  			document.getElementById("quickSearchMsg").innerHTML = "";
  			
  			document.getElementById("quickSearchSBMsg").innerHTML = "";
    	
    	//	init();
		}
	}
	
	function allClearDis() {
		if (document.getElementById('installAddress.quickSearch').disabled == false || document.getElementById('installAddress.quickSearchSB').disabled == false) {
			
			clearAddr();

			clearInfo();
		
			hideMsg();
		
			if (document.getElementById('installAddress.quickSearch.errors') != null) {
    			document.getElementById('installAddress.quickSearch.errors').style.visibility = "hidden";
    			document.getElementById('installAddress.quickSearch.errors').hidden = true;
    			document.getElementById('installAddress.quickSearch.errors').style.display = "none";
			}
		
			if (document.getElementById('installAddress.quickSearchSB.errors') != null) {
    			document.getElementById('installAddress.quickSearchSB.errors').style.visibility = "hidden";
    			document.getElementById('installAddress.quickSearchSB.errors').hidden = true;
    			document.getElementById('installAddress.quickSearchSB.errors').style.display = "none";
			}

			if (document.getElementById('installAddress.floorNo.errors') != null) {
		    	document.getElementById('installAddress.floorNo.errors').style.visibility = "hidden";
		    	document.getElementById('installAddress.floorNo.errors').hidden = true;
		    	document.getElementById('installAddress.floorNo.errors').style.display = "none";
			}

			document.getElementById('brm_vas_desc').style.display = "none";
			
			document.getElementById('isError1').value = "N";
			document.getElementById('isError2').value = "N";
  		  	document.getElementById('installAddress.blacklistInd').value = "N";
  		  	
  		  	document.getElementById('installAddress.serbdyno').value = "";
  			
  			document.getElementById("quickSearchMsg").innerHTML = "";
  			
  			document.getElementById("quickSearchSBMsg").innerHTML = "";
    	
		}
	}

	function showCustomer() {
		
		var lob = document.getElementById('customer.lob').value;
		if (lob == "IMS") {
			document.getElementById('idDocType').readOnly = true;
			document.getElementById('idDocNum').readOnly = true;
			document.getElementById('title').readOnly = true;
			document.getElementById('lastName').readOnly = true;
			document.getElementById('firstName').readOnly = true;
			document.getElementById('contactPersonName').readOnly = true;
			document.getElementById('companyName').readOnly = true;
			document.getElementById('dobStr').readOnly = true;
		} else {
			document.getElementById('idDocType').style.visibility = "hidden";
			document.getElementById('idDocNum').style.visibility = "hidden";
			document.getElementById('title').style.visibility = "hidden";
			document.getElementById('lastName').style.visibility = "hidden";
			document.getElementById('firstName').style.visibility = "hidden";
			document.getElementById('contactPersonName').style.visibility = "hidden";
			document.getElementById('companyName').style.visibility = "hidden";
			document.getElementById('dobStr').style.visibility = "hidden";
		}
		
		var blCust = document.getElementById('blacklistCustInfo').value;
		if (blCust == "") {
			document.getElementById('warning_profile_1').style.display = "none";
		} else {
			document.getElementById('warning_profile_msg1').innerHTML = document.getElementById('blacklistCustInfo').value;
			document.getElementById('warning_profile_1').style.display = "inline";
			document.getElementById('warning_profile_1').style.visibility = "visible";
		}
		convertCustinfo();
	}
	
	function convertCustinfo()
	{
		if ( $("[id='idDocType']").val() == "BS" )
		{
			$("[id='idDocType']").val("HKBR");
			$("[id='contactPersonName']").val($("[id='firstName']").val());
			$(".personal").hide();
			$(".business").show();
		}
		else
		{
			$(".business").hide();
			$(".personal").show();
		}
		
		
		if ( $("[id='idDocType']").val() == "PASS" )
		{
			$("[id='idDocType']").val("Passport");
		}
		
		
	}
	
	function init() {
		
		showCustomer();
		
		//for reacall order
		var quickSearchStr = document.getElementById('installAddress.quickSearch').value;
		var quickSearchSBStr = document.getElementById('installAddress.quickSearchSB').value;
		if (quickSearchStr == '' && quickSearchSBStr == '') {
			clearAddr();
			hideMsg();
	    	document.getElementById('installAddress.blacklistInd').value = "N";
			clearInfo();
		} else {
			document.getElementById('installAddress.quickSearch').readOnly = true;
			document.getElementById('installAddress.quickSearchSB').readOnly = true;
			
			if (document.getElementById('installAddress.unitNo').value == "") {
				document.getElementById('installAddress.unitNo').readOnly = false;	
			} else {
				document.getElementById('installAddress.unitNo').readOnly = true;
			}
			if (document.getElementById('installAddress.floorNo').value == "") {
				document.getElementById('installAddress.floorNo').readOnly = false;
			} else {
				document.getElementById('installAddress.floorNo').readOnly = true;
			}

			readOnlyAddr();
			//disableAddrSelect();
			hideMsg();
			//$('[id=installAddress.floorNo]').trigger("change");
			validAddressInfoByFF(document.getElementById('installAddress.serbdyno').value, document.getElementById('installAddress.floorNo').value.trim(), document.getElementById('installAddress.unitNo').value.trim(), document.getElementById('phInd').value.trim());
		}
		
	}

	function setCurrentSearchFrom(input) {
		if (document.getElementById('installAddress.quickSearch.errors') != null) {
			document.getElementById('installAddress.quickSearch.errors').style.visibility = "hidden";
			document.getElementById('installAddress.quickSearch.errors').hidden = true;
			document.getElementById('installAddress.quickSearch.errors').style.display = "none";
		}
		if (document.getElementById('installAddress.quickSearchSB.errors') != null) {
			document.getElementById('installAddress.quickSearchSB.errors').style.visibility = "hidden";
			document.getElementById('installAddress.quickSearchSB.errors').hidden = true;
			document.getElementById('installAddress.quickSearchSB.errors').style.display = "none";
		}
		if (document.getElementById('installAddress.floorNo.errors') != null) {
			document.getElementById('installAddress.floorNo.errors').style.visibility = "hidden";
			document.getElementById('installAddress.floorNo.errors').hidden = true;
			document.getElementById('installAddress.floorNo.errors').style.display = "none";
		}
		
		document.imsaddressForm.imsSearchFrom.value = input;
		
		$("input[id=installAddress.quickSearch]").autocomplete("imsaddresslookup.html?type=address", {
			//minChars: 4,
			minChars : 1,
			delay : 600,
			selectFirst : false,
			max : 30,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}

		});
		
		$("input[id=installAddress.quickSearchSB]").autocomplete("imsaddresslookup.html?type=sb", {
			//minChars: 4,
			minChars : 6,
			delay : 600,
			selectFirst : false,
			max : 30,
			matchSubset : false,
			highlight : false,
			//extraParams: {random: (function(){return Math.random()})},
			formatItem : function(row, i, max) {
				return row;
			}

		});
		
	}

	//STATR AJAX
	$(document).ready(function() {
//	$(function() {
		$.ajax({
			url : "imsaddressdistrictlookup.html?T=AREA",
			type : 'POST',
			dataType : 'json',
			timeout : 25000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			     if(textStatus=='parsererror'){
// 					alert("Your session has been timed out, please re-login."); 
					alert("<spring:message code="ims.pcd.addressinfo.msg.003"/>");
			     } else {
// 			    	alert("Error loading area data!");
			    	alert("<spring:message code="ims.pcd.addressinfo.msg.011"/>");
			     }
			},
			success : function(msg) {
				$("#areaCdSelect").empty();
				$.each(eval(msg), function(i, item) {
					if (item.id == "${installAddress.areaCd}") {
						$("<option value='" + item.id + "' selected='selected'>"
						+ item.name + "</option>").appendTo($("#areaCdSelect"));
					} else {
						$("<option value='" + item.id + "' >" 
						+ item.name	+ "</option>").appendTo($("#areaCdSelect"));
					}
				});

				$('#areaCdSelect').trigger("change"); //for assign value
			}
		});
		
	});
	//END AJAX

	//STATR AJAX
	$(function() {
		$("[id=installAddress.serbdyno]").change(
				function() {
					loadAddressInfoBySB(
							document.getElementById('installAddress.serbdyno').value, 
							document.getElementById('installAddress.blacklistInd').value, 
							document.getElementById('customer.blacklistInd').value,
							document.getElementById('installAddress.unitNo').value.trim(),
							document.getElementById('installAddress.floorNo').value.trim());
				});
		$("[id=installAddress.floorNo]").change(
				function() {
					//alert(document.getElementById('installAddress.floorNo').value);
					validAddressInfoByFF(document.getElementById('installAddress.serbdyno').value, document.getElementById('installAddress.floorNo').value.trim(), document.getElementById('installAddress.unitNo').value.trim(), document.getElementById('phInd').value.trim());					
				});
		$("[id=installAddress.unitNo]").change(
				function() {
					//alert(document.getElementById('installAddress.unitNo').value);
					validAddressInfoByFF(document.getElementById('installAddress.serbdyno').value, document.getElementById('installAddress.floorNo').value.trim(), document.getElementById('installAddress.unitNo').value.trim(), document.getElementById('phInd').value.trim());
				});

	});
	
	function loadAddressInfoBySB(sbNum, isBlacklistAddr, isBlacklistCust, flat, floor) {
		var varual = '';
		var txtError1 = '';
		var txtWarning1 = '';
		var txtWarning2 = '';
		var noPon10G = '';
		var ponSB = '';
		var phInd = 'N';
		var sbPopUp = ['',''];
		var sbPopUpJS = [];
		var sbPopUpHTML = [];
		
		varual = 'imsaddressinfolookupbysb.html?SB=' + sbNum + "&BlAddr=" + isBlacklistAddr + "&BlCust=" + isBlacklistCust + "&flat=" + flat + "&floor=" + floor;
		$.ajax({
			url : varual,
			type : 'POST',
			dataType : 'json',
			timeout : 25000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			     if(textStatus=='parsererror'){
// 					alert("Your session has been timed out, please re-login."); 
					alert("<spring:message code="ims.pcd.addressinfo.msg.003"/>");
			     } else {
// 			    	alert("Error validate Address Info By SB!");
			    	alert("<spring:message code="ims.pcd.addressinfo.msg.012"/>");
			     }
			},
			success : function(msg) {
				$.each(eval(msg), function(i, item) {
					$("#hosType").val(item.hosType);
					$("#ponSrd").val(item.ponSrd);
					$("#adslSrd").val(item.adslSrd);
					$("#vdslSrd").val(item.vdslSrd);
					$("#vectorSrd").val(item.vectorSrd);
					$("#isPon4Village").val(item.isPonForVillage);
					//kinman
					$("#ponInstFee").val(item.ponInstFee);
					$("#adslInstFee").val(item.adslInstFee);
					$("#vdslInstFee").val(item.vdslInstFee);
					$("#vectorInstFee").val(item.vectorInstFee);
					
					//Anthony
					$("#otChrgType").val(item.OtChrgType);
					$("#ponOTChrgType").val(item.ponOTChrgType);
					$("#adslOTChrgType").val(item.adslOTChrgType);
					$("#vdslOTChrgType").val(item.vdslOTChrgType);
					$("#vectorOTChrgType").val(item.vectorOTChrgType);
					IOA(item.ponOTChrgType, "PON");
					IOA(item.adslOTChrgType, "ADSL");
					IOA(item.vdslOTChrgType, "VDSL");
					IOA(item.vectorOTChrgType, "Vectoring");
					

					$('#vectorSRDRow').css('display','none');
					$('#vectorIFRow').css('display','none');
					
					if($("#vectorSrd").val()!=""&&$("#vectorSrd").val()!="N/A"){
						$('#vectorSRDRow').css('display','');
						$('#vectorIFRow').css('display','');
						$('#vdslSRDRow').css('display','none');
						$('#vdslIFRow').css('display','none');
					}else{
						$('#vectorSRDRow').css('display','none');
						$('#vectorIFRow').css('display','none');
						$('#vdslSRDRow').css('display','');
						$('#vdslIFRow').css('display','');
					}
					
					//
					$("#hosType").show();
					$("#ponSrd").show();
					$("#adslSrd").show();
					$("#vdslSrd").show();
					$("#vectorSrd").show();
					//kinman
					$("#ponInstFee").show();
					$("#adslInstFee").show();
					$("#vdslInstFee").show();
					$("#vectorInstFee").show();
					//
					document.imsaddressForm.hosType.readOnly = true;
					document.imsaddressForm.ponSrd.readOnly = true;
					document.imsaddressForm.adslSrd.readOnly = true;
					document.imsaddressForm.vdslSrd.readOnly = true;
					document.imsaddressForm.vectorSrd.readOnly = true;
					//kinman
					document.imsaddressForm.ponInstFee.readOnly = true;
					document.imsaddressForm.adslInstFee.readOnly = true;
					document.imsaddressForm.vdslInstFee.readOnly = true;
					document.imsaddressForm.vectorInstFee.readOnly = true;
					//
					
					txtError1 = item.sbDeadCaseError1;
					txtWarning1 = item.sbInsuffRscWarning1;
					txtWarning2 = item.sbOutServiceWarning2;
					noPon10G = item.noPon10G;
					ponSB = item.ponSBStr;
					phInd = item.phInd;
					bomWarning = item.bomWarning;
					if (sbPopUp.length == 2) {
						sbPopUpJS = item.sbPopUpJS;
						sbPopUpHTML = item.sbPopUpHTML;
						sbPopUp[0] = item.sbPopUpJS[0]==null?"":item.sbPopUpJS[0];
						sbPopUp[1] = item.sbPopUpHTML[0]==null?"":item.sbPopUpHTML[0];
					}
					//alert(item.bomWarning);
					if (bomWarning != ''){
						document.getElementById('brm_vas_desc_msg').innerHTML = bomWarning;
						document.getElementById('brm_vas_desc').style.display = "inline";
						document.getElementById('brm_vas_desc').style.visibility = "visible";
						document.getElementById('brm_vas_desc').hidden = false;
					}else{
						document.getElementById('brm_vas_desc').style.display = "none";
						document.getElementById('brm_vas_desc_msg').innerHTML = bomWarning;
					}
				});
			},
			complete : function() {
				document.getElementById('phInd').value = phInd;
				if (txtError1 == '') {
					document.getElementById('error_addr_1').style.display = "none";
					document.getElementById('isError1').value = "N";
				} else {
					document.getElementById('isError1').value = "Y";
					document.getElementById('error_addr_msg1').innerHTML = txtError1;
					document.getElementById('error_addr_1').style.display = "inline";
					document.getElementById('error_addr_1').style.visibility = "visible";
					document.getElementById('error_addr_1').hidden = false;
				}
				
				if (txtWarning1 == '') {
					document.getElementById('warning_addr_1').style.display = "none";
				} else {
					document.getElementById('warning_addr_msg1').innerHTML = txtWarning1;
					document.getElementById('warning_addr_1').style.display = "inline";
					document.getElementById('warning_addr_1').style.visibility = "visible";
					document.getElementById('warning_addr_1').hidden = false;
				}
					
				if (txtWarning2 == '') {
					document.getElementById('warning_addr_2').style.display = "none";
				} else {
					document.getElementById('warning_addr_msg2').innerHTML = txtWarning2;
					document.getElementById('warning_addr_2').style.display = "inline";
					document.getElementById('warning_addr_2').style.visibility = "visible";
					document.getElementById('warning_addr_2').hidden = false;
				}
				
				if (noPon10G == '') {
					document.getElementById('error_no_pon10G').style.display = "none";
// 					document.getElementById('isError1').value = "N";
				} else {
// 					document.getElementById('isError1').value = "Y";
					document.getElementById('error_no_pon10G_msg1').innerHTML = noPon10G;
					document.getElementById('error_no_pon10G').style.display = "inline";
					document.getElementById('error_no_pon10G').style.visibility = "visible";
					document.getElementById('error_no_pon10G').hidden = false;
				}
				
				if (ponSB != ''){
					var box_ponSB = new imsSBModalBox({
						width: "35%", 
						height: "auto",
						maxHeight: "80px",
						title: "", 
						text: document.getElementById("addressInfoBySB_relatedPONSBMsg").innerHTML + ponSB, 
						ButtonYtext: "OK", 
						ButtonNtext: "",
						centerBox: "Y"
					});
					box_ponSB.openBox();
					//alert('Related PON service boundary exists, countinue?\n\n'+ponSB);
				}
				
				if($("#isPon4Village").val()=="Y"){
					$("#high_speed_pre_reg").css('display','');
				}else{
					$("#high_speed_pre_reg").css('display','none');
				}
				
				if (sbPopUp == null) {
					document.getElementById('sbPopUp').style.display = "none";
				} else if (sbPopUp != null && sbPopUp.length == 2 && sbPopUp[0] != "" && sbPopUp[1] != "") {
					var htmlMsg = "";
					for(var i=0;i<sbPopUpHTML.length;i++){
						htmlMsg += sbPopUpHTML[i];
						htmlMsg += "<br/><br/>";
					}
					document.getElementById('sbPopUpMsg').innerHTML = htmlMsg;//sbPopUp[1];
					document.getElementById('sbPopUp').style.display = "inline";
					document.getElementById('sbPopUp').style.visibility = "visible";
					document.getElementById('sbPopUp').hidden = false;
					if(popupPrevSBNo!=sbNum || popupReset){
					for (var i=0; i<sbPopUpHTML.length; i++) {
						var box_sbRemarks = new imsSBModalBox({
							width: "50%", 
							height: "auto",
							maxHeight: "180px",
							title: "", 
							text: sbPopUpHTML[i], 
							ButtonYtext: "OK", 
							ButtonNtext: "",
							centerBox: "Y"
						});
						box_sbRemarks.openBox();
					}
					}
					popupPrevSBNo = sbNum;
					popupReset = false;
					//for(var i=0;i<sbPopUpJS.length;i++){
					//	alert(sbPopUpJS[i]);
					//}
					//alert(sbPopUp[0]);
				}
			}
		});
	}
	
	//Anthony
	function IOA(ioa, type)
	{
		
		if ( ioa == "I")
		{
			$("." + type + "Installation").show();
			$("." + type + "Activation").hide();
			
		}
		else if ( ioa == "A" )
		{
			$("." + type + "Activation").show();
			$("." + type + "Installation").hide();
		}
		else
		{ 
			$("." + type + "Installation").show();
			$("." + type + "Activation").hide();
		}
	}
	
	function validAddressInfoByFF(sbNum, floor, unitNo, phInd) {
		var varual = '';
		var txtWarning3 = '';
		var txtError2 = '';
		var txtWarning4 = '';
		var txtWarning5 = '';
		var bomWarning = '';
		varual = 'imsaddressinfolookupbyff.html?SBNUM=' + sbNum + '&FLOOR=' + floor + '&UNITNO=' + unitNo + '&PHIND=' + phInd;
		$.ajax({
			url : varual,
			type : 'POST',
			dataType : 'json',
			timeout : 25000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			     if(textStatus=='parsererror'){
// 					alert("Your session has been timed out, please re-login."); 
					alert("<spring:message code="ims.pcd.addressinfo.msg.003"/>");
			     } else {
// 			    	alert("Error validate Address Info By floor and flat!");
			    	alert("<spring:message code="ims.pcd.addressinfo.msg.008"/>");
			     }
			},
			success : function(msg) {
				$.each(eval(msg), function(i, item) {
					
					txtWarning3 = item.osOrderWarning3;
					txtError2 = item.blockageError2;
					txtWarning4 = item.blockageWarning4;
					document.getElementById('installAddress.blacklistInd').value = item.isBlacklistAddr;
					document.getElementById('installAddress.serbdyno').value = item.correctSBwithFlatFloor; //martin
					txtWarning5 = item.blAddrWarning5;
				});
			},
			complete : function() {
				//$("[id=installAddress.serbdyno]").trigger("change");
				loadAddressInfoBySB(
						document.getElementById('installAddress.serbdyno').value, 
						document.getElementById('installAddress.blacklistInd').value, 
						document.getElementById('customer.blacklistInd').value,
						document.getElementById('installAddress.unitNo').value.trim(),
						document.getElementById('installAddress.floorNo').value.trim());
				if (txtWarning3 == '') {
					document.getElementById('warning_addr_3').style.display = "none";
					document.getElementById('warning_addr_msg3').innerHTML = txtWarning3;
				} else {
					document.getElementById('warning_addr_msg3').innerHTML = txtWarning3;
					document.getElementById('warning_addr_3').style.display = "inline";
					document.getElementById('warning_addr_3').style.visibility = "visible";
					document.getElementById('warning_addr_3').hidden = false;
				}
				
				if (txtError2 == '') {
					document.getElementById('error_addr_2').style.display = "none";
					document.getElementById('isError2').value = "N";
				} else {
					document.getElementById('isError2').value = "Y";
					document.getElementById('error_addr_msg2').innerHTML = txtError2;
					document.getElementById('error_addr_2').style.display = "inline";
					document.getElementById('error_addr_2').style.visibility = "visible";
					document.getElementById('error_addr_2').hidden = false;
				}
					
				if (txtWarning4 == '') {
					document.getElementById('warning_addr_4').style.display = "none";
					document.getElementById('warning_addr_msg4').innerHTML = txtWarning4;
				} else {
					document.getElementById('warning_addr_msg4').innerHTML = "<i>" + txtWarning4 + "</i>";
					document.getElementById('warning_addr_4').style.display = "inline";
					document.getElementById('warning_addr_4').style.visibility = "visible";
					document.getElementById('warning_addr_4').hidden = false;
				}
					
				if (txtWarning5 == '') {
					document.getElementById('warning_addr_5').style.display = "none";
					document.getElementById('warning_addr_msg5').innerHTML = txtWarning5;
				} else {
					document.getElementById('warning_addr_msg5').innerHTML = "<i>" + txtWarning5 + "</i>";
					document.getElementById('warning_addr_5').style.display = "inline";
					document.getElementById('warning_addr_5').style.visibility = "visible";
					document.getElementById('warning_addr_5').hidden = false;
				}
			}
		});
	}
	//END AJAX
	
	
	function openDialog1AMS(){
		var serbdyno1ams = document.getElementById('installAddress.serbdyno').value;
		var floorNo1ams = document.getElementById('installAddress.floorNo').value.trim();
		var flatNo1ams = document.getElementById('installAddress.unitNo').value.trim();
		var hiLotNo1ams = document.getElementById('installAddress.hiLotNo').value.trim();
		
		//alert(serbdyno1ams);
		//alert(floorNo1ams);
		openModalDialog("ims1ams.html?serbdyno="+serbdyno1ams+"&floor="+floorNo1ams+"&flatno="+flatNo1ams+"&hiLotNo="+hiLotNo1ams+"&dialogMode=true", "1AMS");
		
	}
	function validHktMobileNo() {
		hktMobileNumEdited=true;
		var hktMobileNum = $("#hktMobileNum").val();
		var hktMobilePattern=/[569][0-9][0-9][0-9][0-9][0-9][0-9][0-9]/;
		if (hktMobileNum.match(hktMobilePattern)){
			document.getElementById('hktMobileNum_warning').style.display = "none";
			document.getElementById('hktMobileNum_warning_msg').innerHTML = "";
			document.getElementById('hktMobileNum_error').style.display = "none";
			document.getElementById('hktMobileNum_error_msg').innerHTML = "";
		}else if(hktMobileNum==""){
			document.getElementById('hktMobileNum_error').style.display = "";
			document.getElementById('hktMobileNum_error_msg').innerHTML = "Please input mobile no.";
			document.getElementById('hktMobileNum_warning').style.display = "none";
			document.getElementById('hktMobileNum_warning_msg').innerHTML = "";
		}else{
			document.getElementById('hktMobileNum_warning').style.display = "";
			document.getElementById('hktMobileNum_warning_msg').innerHTML = "Invalid Mobile number.";
			document.getElementById('hktMobileNum_error').style.display = "none";
			document.getElementById('hktMobileNum_error_msg').innerHTML = "";
		}
		/*if(document.getElementById('hktMobileNum').value != document.getElementById("mobileNum").value){
			hktMobileNumEdited=true;
		}else {
			hktMobileNumEdited=false;
		}*/
	}
	
	function checkCslCustomer(){
		

		var idDocType = document.getElementById('cslDocTypeSelect').value;
		var idDocNum  = $('input[name=cslDocNum]').val();
		var cslMobileNum = $('input[name=cslMobileNum]').val();
		var serbdyno = document.getElementById('installAddress.serbdyno').value;
		var unit = document.getElementById('installAddress.unitNo').value.trim();
		var floor = document.getElementById('installAddress.floorNo').value.trim();
// 		alert(idDocType+idDocNum+cslMobileNum+serbdyno+unit+floor);
		$.ajax({
			url : "imscheckcslmobilenum.html?IDDOCTYPE="+idDocType+"&IDDOCNUM="+idDocNum+"&CSLMOBILENUM="+cslMobileNum+"&SERBDYNO="+serbdyno+"&UNIT="+unit+"&FLOOR="+floor,
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus=='parsererror'){
// 			        alert("Your session has been timed out, please re-login."); 
			        alert("<spring:message code="ims.pcd.addressinfo.msg.003"/>");
			    }else if(textStatus == 'timeout'){
// 			    	alert("Server response timeout, please try again.");
			    	alert("<spring:message code="ims.pcd.addressinfo.msg.009"/>");
			    }
				document.getElementById('warning_login').style.visibility = "hidden";
				document.getElementById('error_login').style.visibility = "hidden";
			},			
			success : function(msg) {

				$.each(eval(msg), function(i, item) {
// 					alert(item.valid+item.errorText);
						 if(!item.valid){
							 		 
							 document.getElementById('cslOrder_error').style.display = "";
							 document.getElementById('csl_order_error_msg').innerHTML = item.errorText;	
							 document.getElementById('cslOrder_checkOk').style.display = "none";
						 }else {
							 document.getElementById('cslOrder_error').style.display = "none";
							 document.getElementById('csl_order_error_msg').innerHTML = "";	
							 document.getElementById('cslOrder_checkOk').style.display = "";
							 document.getElementById('customer.mobileCutomerInfo.mobCustNo').value=cslMobileNum;
							 document.getElementById('customer.mobileCutomerInfo.idDocType').value=idDocType;
							 document.getElementById('customer.mobileCutomerInfo.idDocNum').value=idDocNum;
							 if(item.custFirstName!=undefined){
								 document.getElementById('customer.mobileCutomerInfo.firstName').value=item.custFirstName;
							 }else{
								 document.getElementById('customer.mobileCutomerInfo.firstName').value="";
							 }
							 if(item.custLastName!=undefined){
							 	document.getElementById('customer.mobileCutomerInfo.lastName').value=item.custLastName;	
							 }else{
								document.getElementById('customer.mobileCutomerInfo.lastName').value="";	
							 }

						 }
				});				

			}
		});
		
		
	}
		
	function cslFormSubmit(){	
		if(document.getElementById('cslDocTypeSelect').value !=document.getElementById('customer.mobileCutomerInfo.idDocType').value
		|| $('input[name=cslDocNum]').val() != document.getElementById('customer.mobileCutomerInfo.idDocNum').value
		|| $('input[name=cslMobileNum]').val() != document.getElementById('customer.mobileCutomerInfo.mobCustNo').value){			
// 			alert("Please press check button.");
			alert("<spring:message code="ims.pcd.addressinfo.msg.013"/>");
			document.getElementById('cslOrder_checkOk').style.display = "none";
		}
		
		if(document.getElementById('cslOrder_checkOk').style.display == "none"){			
			$("#mobileOfferInd").val("N");
			return;
		}else{			
			$("#mobileOfferInd").val("Y");
			var valid = true;
			$(".noSymbol").each(function(i ,e){
				if ($(e).html().length >0 ) {
					valid = false;
				} 
			});
			if (document.getElementById('isError1').value == "Y" ||
				document.getElementById('isError2').value == "Y" || !valid) { 
// 				alert("Please refer to the error shown in the page.");
				alert("<spring:message code="ims.pcd.addressinfo.msg.007"/>");
			} else {
				if($("[id='installAddress.unitNo']").val().length==0){  
					if (!confirm("<spring:message code="ims.pcd.addressinfo.msg.005"/>")) 
				
						return; 
				}
				
				//alert("prevSbNo==>" + document.getElementById('prevSbNo').value + "<" + "currSbNo==>" + document.getElementById('installAddress.serbdyno').value + "<");
				if (document.getElementById('prevSbNo').value == null ||
					document.getElementById('prevSbNo').value == "" ||
					document.getElementById('prevSbNo').value == document.getElementById('installAddress.serbdyno').value) {
						submitShowLoading();
						$(":input").attr("disabled", false);
						document.imsaddressForm.submit();
				} else {
					var answer = confirm("<spring:message code="ims.pcd.addressinfo.msg.006"/>");
					if (answer) {
						submitShowLoading();
						$(":input").attr("disabled", false);
						document.imsaddressForm.submit();
					}
				}
			}
		}	
	}
	function cleanCslInfo(){
		$("#mobileOfferInd").val("N");
		 document.getElementById('customer.mobileCutomerInfo.mobCustNo').value="";
		 document.getElementById('customer.mobileCutomerInfo.idDocType').value="";
		 document.getElementById('customer.mobileCutomerInfo.idDocNum').value="";
		 document.getElementById('customer.mobileCutomerInfo.firstName').value="";
		 document.getElementById('customer.mobileCutomerInfo.lastName').value="";
	}
	function keyUpOnCslDocNum(){	
		document.getElementById('cslDocNum').value = document.getElementById('cslDocNum').value.toUpperCase();
	}
	
	function preReg(){

		if(("${ImsOrder.preRegSubmitted}" != null && "${ImsOrder.preRegSubmitted}" == "Y" )|| ($("#pon4VillageSubmitted").val() != null && $("#pon4VillageSubmitted").val() == "Y")){
// 			alert("df");
			 $("#preregbtn").removeClass("nextbutton");
			 $("#preregbtn").addClass("nextbuttonPrereg");
			document.getElementById("high_speed_pre_reg").disabled = true;
		}else{

// 		    var form_ = document.getElementById('preregform');
					    
// 		    document.getElementById("imspreregistration_form").src = "imspreregistration.html?dialogMode=true&bw="+$("#bandwidth").val()+"&type="+'regPON'
// 		    		+"&sb_no="+document.getElementById('installAddress.serbdyno').value
// 		    		+"&floor="+document.getElementById('installAddress.floorNo').value.trim()
// 		    		+"&flat="+document.getElementById('installAddress.unitNo').value.trim();
		    
		    
// 			$('#preregistration_form').dialog('open');
			
			
	    	
// 			var box= "#imspreregistration_form";
			
// 			$(box).dialog({
// 				width: $(window).height() - 70,
// 				height:$(window).height() - 132,
// 				resizable: false,
// 				draggable: false,
// 				modal: true	
			
// 			});
			if(document.getElementById('installAddress.floorNo').value==null || document.getElementById('installAddress.floorNo').value.trim()==""
			 ||document.getElementById('installAddress.unitNo').value==null || document.getElementById('installAddress.unitNo').value.trim()==""){
				alert("Please enter the flat and floor first.");
				return false;
			}
			openModalDialog( "imspreregistration.html?dialogMode=true"+"&type="+'regPON'
		    		+"&sb_no="+document.getElementById('installAddress.serbdyno').value
		    		+"&floor="+document.getElementById('installAddress.floorNo').value.trim()
		    		+"&flat="+document.getElementById('installAddress.unitNo').value.trim(), "PON to Village Enquiry");
// 		    form_.style.display = 'block';
		 
// 		    $("#preregistration_form").css("height","275px");
			}
		
		
		}
	
	function hide_Form() {		
		closeModalDialog();
		$('.ui-widget-overlay').css("display","none");
		alert("Your Pre-registration request has been submitted.");	
		$("#pon4VillageSubmitted").val("Y");
		 $("#preregbtn").removeClass("nextbutton");
		 $("#preregbtn").addClass("nextbuttonPrereg");


//			$('#preregistration_form').dialog('close');
// 		  $("#preregistration_form").css("display","none");
// 		$('.ui-icon-closethick').click();
// 		

	}
	
</script>



<table width="100%" >
	<tr>
		<td class="contenttextgreen" align="center">
		 	<spring:message code="ims.pcd.addressinfo.048"/>
		</td>
	</tr>
</table>

<link href="css/imsds.css" rel="stylesheet" type="text/css"> 
<%@ include file="/WEB-INF/jsp/imssubheader.jsp" %>
<%@ include file="/WEB-INF/jsp/imsspringboardmodalboxui.jsp" %>

<form:form method="POST" name="imsaddressForm" commandName="addressinfo">
<input type="hidden" name="IMS_UID" value="<%= request.getAttribute("IMS_UID") %>" />
   			<div id="preregform" style="display:none">
        	<iframe id="imspreregistration_form" src="" frameBorder="0" scrolling="no"></iframe>
        	</div>
<br/>
	<form:hidden path="otChrgType"/>
	<form:hidden path="ponOTChrgType"/>
	<form:hidden path="adslOTChrgType"/>
	<form:hidden path="vdslOTChrgType"/>
	<form:hidden path="vectorOTChrgType"/>
	<table width="100%" class="tablegrey">
	<tr>
		<td>
  			<table width="100%"  border="0" cellspacing="1">
		   	<tr>
	   			<td class="table_title" ><spring:message code="ims.pcd.addressinfo.001"/></td>
	   		</tr>
	  		</table>
	  	
			<!-- customer block -->
			<c:choose>
			<c:when test="${ims_direct_sales eq true}">
				<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">  
	        	<tr>
	         		<td colspan="6">&nbsp;</td>
	        	</tr>
	        	<tr>
		   			<td class="table_title" colspan="6"><spring:message code="ims.pcd.addressinfo.002"/></td>
	        	</tr>
	        	<tr>
					<th align="left"><spring:message code="ims.pcd.addressinfo.006"/></th>  
					<td><input type=text id="idDocType" value="${addressinfo.customer.idDocType}"/></td> 
					<th align="left"><spring:message code="ims.pcd.addressinfo.007"/></th>
					<td><input type=text id="idDocNum" value="${addressinfo.customer.idDocNum}"/></td>
				</tr> 
				<tr> 
					<th align="left" <c:if test="${addressinfo.customer.idDocType eq 'BR'}">style='display:none;'</c:if>><spring:message code="ims.pcd.addressinfo.003"/> </th> 
					<td <c:if test="${addressinfo.customer.idDocType eq 'BR'}">style='display:none;'</c:if>><input type=text id="lastName" value="${addressinfo.customer.lastName}"/></td>
					<th align="left" <c:if test="${addressinfo.customer.idDocType eq 'BR'}">style='display:none;'</c:if>><spring:message code="ims.pcd.addressinfo.004"/></th>
					<td <c:if test="${addressinfo.customer.idDocType eq 'BR'}">style='display:none;'</c:if>><input type=text id="firstName" value="${addressinfo.customer.firstName}"/></td>
					<th align="left" <c:if test="${addressinfo.customer.idDocType ne 'BR'}">style='display:none;'</c:if>><spring:message code="ims.pcd.addressinfo.005"/></th> 
					<td <c:if test="${addressinfo.customer.idDocType ne 'BR'}">style='display:none;'</c:if>><input type=text id="companyName" value="${addressinfo.customer.companyName}"/></td>
				</tr>
				<tr> 
					<td colspan="6">
		 			<div id="warning_profile_1" class="ui-widget"  style="visibility:hidden">	
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							<div id="warning_profile_msg1" class="contenttext" style="font-weight:bold;"></div>
						</div>					
					</div>
					</td>
				</tr>
	        	</table>
        		<div id="title" style="display:none"></div>
        		<div id="contactPersonName" style="display:none"></div>
        		<div id="dobStr" style="display:none"></div>
        		
			</c:when>
			<c:otherwise>
			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
        	<tr>
         		<td colspan="6">&nbsp;</td>
        	</tr>
        	<tr>
	   			<td class="table_title" colspan="6"><spring:message code="ims.pcd.addressinfo.002"/></td>
        	</tr>
        	<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.006"/></th>
				<td><input type=text id="idDocType" value="${addressinfo.customer.idDocType}"/></td>
				<th align="left"><spring:message code="ims.pcd.addressinfo.007"/></th>
				<td><input type=text id="idDocNum" value="${addressinfo.customer.idDocNum}"/></td>
			</tr>
	        <tr>
	        	<td colspan="6"><hr/></td>
	        </tr>
	        <tr>
    	    	<td colspan="6">
        			<table width="100%" border="0" cellspacing="1" class="contenttext" >
        			<tr class="personal">
						<th align="left"><spring:message code="ims.pcd.addressinfo.008"/></th>
						<td><input type=text maxlength="5" id="title" value="${addressinfo.customer.title}"/></td>
						<th align="left"><spring:message code="ims.pcd.addressinfo.003"/></th>
						<td><input type=text maxlength="40" id="lastName" value="${addressinfo.customer.lastName}"/></td>
						<th align="left"><spring:message code="ims.pcd.addressinfo.004"/></th>
						<td><input type=text maxlength="40" id="firstName" value="${addressinfo.customer.firstName}"/></td>
					</tr>
					<tr class="personal">
						<th align="left"><spring:message code="ims.pcd.addressinfo.009"/></th>
						<td><form:input maxlength="10" path="dobStr" /></td>
					</tr>
					<tr class="business">
						<th align="left"><spring:message code="ims.pcd.addressinfo.005"/></th>
						<td><input type=text maxlength="40" id="companyName" value="${addressinfo.customer.companyName}"/></td>
						<th align="left"><spring:message code="ims.pcd.addressinfo.010"/></th>
						<td><input type="text" maxlength="40" id="contactPersonName" value="${addressinfo.customer.firstName}"/></td>
					</tr>
        			</table>
        		</td>
        	</tr>    
			<tr>
				<td colspan="6">
	 			<div id="warning_profile_1" class="ui-widget"  style="visibility:hidden">	
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="warning_profile_msg1" class="contenttext" style="font-weight:bold;"></div>
					</div>					
				</div>
				</td>
			</tr>
        	</table>
			</c:otherwise>
			</c:choose>
				
			<!-- register address block -->
    	    <table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF"">
    	    <tr>
         		<td colspan="6">&nbsp;</td>
        	</tr>
    	    
    	    <tr>
		   		<td class="table_title" colspan="6"><spring:message code="ims.pcd.addressinfo.011"/></td>
	       	</tr>
		
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>

			<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.012"/></th>
				<td colspan="4" align="left">
					<form:input size="19%"	path="installAddress.quickSearchSB" onfocus="keyDownOnQuickSearchSB()" onkeydown="keyDownOnQuickSearchSB()" onkeyup="keyUpOnQuickSearchSB()" onclick="setCurrentSearchFrom('installAddress.quickSearchSB')" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="4"><form:errors path="installAddress.quickSearchSB" cssClass="error" /></td>
			</tr>
			<tr>
				<td align="right">&nbsp;</td>
				<td colspan="5">
					<span class="contenttext"><spring:message code="ims.pcd.addressinfo.msg.001"/>
					</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="4" id="quickSearchSBMsg" style="color:red"></td>
			</tr>
			
			<tr height="15px"></tr>
		
			<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.013"/></th>
				<td>
					<select id="areaCdSelect" style="width: 150px;"></select> 
<!-- 					<div id='loaderImagePlaceholder'></div> -->
				</td>
				
				<td>
					<select id='distNoSelect' style="width: 150px;"></select> 
<!-- 					<div id='loaderImagePlaceholder'></div> -->
				</td>
				
				<td valign="top">
					<select id='sectCdSelect' style="width: 150px;"></select>
<!-- 					<div id='loaderImagePlaceholder'></div> -->
				</td>
			</tr>

			<tr height="15px"></tr>

			<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.043"/></th>
				<td colspan="4" align="left" id="addrsearch">
					<c:choose>
					<c:when test="${ims_direct_sales eq true}">
					<form:input size="100%"	path="installAddress.quickSearch" onfocus="keyDownOnQuickSearch()" onkeydown="keyDownOnQuickSearch()" onkeyup="keyUpOnQuickSearch()" onclick="setCurrentSearchFrom('installAddress.quickSearch');insertPageTrack();" />
					</c:when>
					<c:otherwise>
					<form:input size="100%"	path="installAddress.quickSearch" onfocus="keyDownOnQuickSearch()" onkeydown="keyDownOnQuickSearch()" onkeyup="keyUpOnQuickSearch()" onclick="setCurrentSearchFrom('installAddress.quickSearch');" />
					</c:otherwise>	
					</c:choose>
				</td>
				<td align="right">
	 				<a href='#' class='nextbutton' onclick="allClear()"><spring:message code="ims.pcd.addressinfo.044"/></a>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="4"><form:errors path="installAddress.quickSearch" cssClass="error" /></td>
			</tr>
			<tr>
				<td align="right">&nbsp;</td>
				<td colspan="5">
					<span class="contenttext"><spring:message code="ims.pcd.addressinfo.msg.002"/>
					</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="4" id="quickSearchMsg" style="color:red"></td>
			</tr>
 
			<!-- Flat Floor -->
			<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.045"/></th>
				<td><form:input maxlength="5" path="installAddress.unitNo" size="16%" onkeyup="keyUpOnUnitNo()" onblur="blurOnUnitNo()" cssClass="noSymbol flat"/>
				<form:errors path="installAddress.unitNo" cssClass="error" /> 
				</td>
				<th align="left"><spring:message code="ims.pcd.addressinfo.046"/></th>
				<td><form:input maxlength="3" path="installAddress.floorNo" size="16%" onkeyup="keyUpOnFloorNo()" onfocus="keyDownOnFloorNo()" onblur="blurOnFloorNo()" cssClass="noSymbol" onclick="clickOnFloorNo()"/></td>
				<td><form:errors path="installAddress.floorNo" cssClass="error" /></td>
			</tr>

			<tr height="15px">
				<th/>
				<td colspan="2" ><span class="installAddress.unitNonoSymbolError error"></span></td>
				<td colspan="2" ><span class="installAddress.floorNonoSymbolError error"></span></td>
			</tr>

			<!-- Block Building -->
			<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.014"/></th>
				<td><form:input path="installAddress.hiLotNo" size="16%" readonly="readonly" /></td>

				<th align="left"><spring:message code="ims.pcd.addressinfo.015"/></th>
				<td colspan="3">
					<form:input path="installAddress.buildNo" size="50%" readonly="readonly" />
				</td>
			</tr>

			<!-- Street No & Name -->
			<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.016"/></th>
				<td><form:input path="installAddress.strNo" size="16%" readonly="readonly" /></td>
				
				<th align="left"><spring:message code="ims.pcd.addressinfo.017"/></th>
				<td><form:input path="installAddress.strName" size="16%" readonly="readonly" /></td>
				
				<th align="left"><spring:message code="ims.pcd.addressinfo.018"/></th>
				<td><form:input path="installAddress.strCatDesc" size="16%" readonly="readonly" /></td>
			</tr>
			
			<!-- Section District Area -->
			<tr>
				<th align="left" valign="top"><spring:message code="ims.pcd.addressinfo.019"/></th>
				<td valign="top">
					<form:input path="installAddress.sectDesc" readonly="readonly" />
					<form:hidden path="installAddress.sectCd" />
				</td>

				<th align="left" valign="top"><spring:message code="ims.pcd.addressinfo.020"/></th>
				<td>
					<form:input path="installAddress.distDesc" readonly="readonly" />
					<form:hidden path="installAddress.distNo" /> 
				</td>

				<th align="left" valign="top"><spring:message code="ims.pcd.addressinfo.021"/></th>
				<td>
					<form:input path="installAddress.areaDesc" readonly="readonly" /> 
					<form:hidden path="installAddress.areaCd" /> 
				</td>
			</tr>
			</table>
			<!-- end register address block -->

			<!-- 1AMS -->
			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF" <c:if test="${ims_direct_sales eq true}">style="display:none;"</c:if>> 
			<tr><tr height="15px"></tr>
				<td align="right" colspan="6">
					<div>
						<a href="#" id="search1AMS" class="nextbutton" onClick="openDialog1AMS()" >1AMS</a>
					</div>
				</td>
			</tr>	<tr height="15px"></tr>		
			</table>
			<!-- end 1MAS -->
			<!-- PON to Village -->

			<table id = "high_speed_pre_reg" style="display:none;    padding-top: 12px;" width="100%" border="0" cellspacing="1"  class="contenttext" bgcolor="#FFFFFF"> 

			<tr></tr>
			<tr height="15px" >
				<td align="right" colspan="6" >
					<div>
						<a href='#' class='nextbutton' onclick="preReg()" id= "preregbtn"><spring:message code="ims.pcd.prereg.020"/></a>
					</div>
				</td>
			</tr>	
			</table>	
			<!-- end PON to Village -->
			
			
			
			
			<!-- address info -->
    	    <table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
    	    <tr>
         		<td colspan="2">&nbsp;</td>
        	</tr>
    	    
    	    <tr <c:if test="${ims_direct_sales ne true}">display:none;</c:if>>
				<th align="left" style="width: 250px;"><spring:message code="ims.pcd.addressinfo.022"/></th>
				<td><form:checkbox path="installAddress.coverPe" value="Y" onclick="return false;"/>P/E &nbsp;&nbsp;&nbsp;&nbsp; <form:checkbox path="installAddress.coverEyeX" value="Y" onclick="return false;"/> eye</td>
			</tr>
    	    
			<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.023"/></th>
				<td><form:input path="hosType" readonly="" size="100%" /></td>
			</tr>
			<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.024"/></th>
				<td><form:input path="ponSrd" readonly="readonly" size="100%" /></td>
			</tr>
			<tr id="vdslSRDRow" >
				<th align="left"><spring:message code="ims.pcd.addressinfo.025"/></th>
				<td><form:input path="vdslSrd" readonly="readonly" size="100%" /></td>
			</tr>
			<tr id="vectorSRDRow" style="display:none;">
				<th align="left"><spring:message code="ims.pcd.addressinfo.026"/></th>
				<td><form:input path="vectorSrd" readonly="readonly" size="100%" /></td>
			</tr>
			<tr>
				<th align="left"><spring:message code="ims.pcd.addressinfo.027"/></th>
				<td><form:input path="adslSrd" readonly="readonly" size="100%" /></td>
			</tr>
			<!-- kinman -->
<!-- 			Anthony -->
			<tr>
				<th class="PONInstallation" align="left"><spring:message code="ims.pcd.addressinfo.028"/></th>
				<th class="PONActivation" align="left" style="display:none"><spring:message code="ims.pcd.addressinfo.029"/></th>
				<td><form:input path="ponInstFee" readonly="readonly" size="100%" /></td>
			</tr>
			<tr id="vdslIFRow">
				<th class="VDSLInstallation" align="left"><spring:message code="ims.pcd.addressinfo.030"/></th>
				<th class="VDSLActivation" align="left" style="display:none"><spring:message code="ims.pcd.addressinfo.031"/></th>
				<td><form:input path="vdslInstFee" readonly="readonly" size="100%" /></td>
			</tr>
			<tr id="vectorIFRow" style="display:none;">
				<th class="VectorInstallation" align="left"><spring:message code="ims.pcd.addressinfo.032"/></th>
				<th class="VectorActivation" align="left" style="display:none"><spring:message code="ims.pcd.addressinfo.033"/></th>
				<td><form:input path="vectorInstFee" readonly="readonly" size="100%" /></td>
			</tr>
			<tr>
				<th class="ADSLInstallation" align="left"><spring:message code="ims.pcd.addressinfo.034"/></th>
				<th class="ADSLActivation" align="left" style="display:none"><spring:message code="ims.pcd.addressinfo.035"/></th>
				<td><form:input path="adslInstFee" readonly="readonly" size="100%" /></td>
			</tr>
				
			</table>

			<!-- end address info -->	
			
			<!-- message -->															    
    	    <table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF"">
			<tr>
			    <td colspan="3">
			    <div id="brm_vas_desc" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="brm_vas_desc_msg" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
						</p>		
					</div>
				</div>
				</td>
			</tr>		
			<tr>
				<td colspan="3">
	 			<div id="error_addr_1" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						<div id="error_addr_msg1" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
	 			<div id="error_addr_2" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						<div id="error_addr_msg2" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>

			<tr>
				<td colspan="3">
	 			<div id="warning_addr_1" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="warning_addr_msg1" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
	 			<div id="warning_addr_2" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="warning_addr_msg2" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
	 			<div id="error_no_pon10G" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						<div id="error_no_pon10G_msg1" class="contenttext" style="font-weight:bold; margin-left: 1.5em; color: red;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			<tr <c:if test="${ims_direct_sales eq true}">style="display:none;"</c:if>>  
				<td colspan="3">
	 			<div id="warning_addr_3" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="warning_addr_msg3" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
	 			<div id="warning_addr_4" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="warning_addr_msg4" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
	 			<div id="warning_addr_5" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="warning_addr_msg5" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
	 			<div id="sbPopUp" class="ui-widget"  style="visibility:hidden">
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="sbPopUpMsg" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			
			</table>
			<!-- end mesage -->
			
			<!-- button table  -->
			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
			<tr height="15px"></tr>
			<tr <c:if test="${isShopCodeValid eq 'N'}">style="display:none"</c:if>>   
				<td align="right" colspan="6">
					<div>
						<a href="#" id="continue" class="nextbuttonS" onClick="javascript:formSubmit();"> <spring:message code="ims.pcd.addressinfo.047"/>&gt;</a>
					</div> 
				</td>
			</tr>
			
			<tr <c:if test="${isShopCodeValid eq 'Y'}">style="display:none"</c:if>>   
				<td colspan="3">
	 			<div class="ui-widget" >
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
						<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<div id="shopCodeNotValid" class="contenttext" style="font-weight:bold; margin-left: 1.5em;">
						<spring:message code="ims.pcd.addressinfo.msg.014"/>${inValidShopCode}<spring:message code="ims.pcd.addressinfo.msg.014a"/>
						
						</div>
						</p>
					</div>
				</div>
				</td>
			</tr>
			
			<tr>
				<td></td>
				<td colspan="4"><form:errors path="commonErrorArea" cssClass="error" /></td>
			</tr>
			<tr height="15px"></tr>
			</table>
			<!-- end button table -->
			
						<!-- 			celia -->
			<div  id = "cslMobileOffer" style = "display:none;">
			<table  width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
		

			<tr>
		   		<th class="table_title" colspan = "6"  align="left"><spring:message code="ims.pcd.addressinfo.036"/></th>
	       	</tr>
			
			<tr>
				<td><spring:message code="ims.pcd.addressinfo.006"/></td>
				<td colspan = "2">
					<select id="cslDocTypeSelect" >
					  <option value="HKID">HKID</option>
					  <option value="PASS">Passport</option>
					  <c:if test='${ImsOrder.isCC == "Y"}'>
						 <option value="BS">BR</option>
					  </c:if>
				        
<!-- 					<div id='loaderImagePlaceholder'></div> -->
					</select> 
				</td>
				<td ><spring:message code="ims.pcd.addressinfo.007"/></th>
				<td colspan = "2">
<!-- 					<input id="cslDocNum" ></input>  -->
					
					<input type="text" name="cslDocNum" id="cslDocNum" maxlength="30" onkeyup="keyUpOnCslDocNum()" />
<!-- 					<div id='loaderImagePlaceholder'></div> -->
				</td>
			</tr>
				
			<tr>
		   		<td align="left"><spring:message code="ims.pcd.addressinfo.039"/></td>
		   		<td align="left" colspan = "2">
<!-- 		   		<input id="cslMobileNum" /> -->
		   		<input type="text" name="cslMobileNum" id="cslMobileNum" maxlength="8" />
		   		</td>
		   		<td align="left" >
					<div>
						<a href="#" id="checkCslMobileNum" class="nextbutton" onClick="checkCslCustomer()" ><spring:message code="ims.pcd.addressinfo.041"/></a>
					</div>
				</td>
				<td align="left" colspan="2">	       	
	       			<div id="cslOrder_checkOk"  class="ui-widget" style="display:none">					
						<p>						
							<div id="csl_order_ok_msg"  style="font-weight:bold; margin-left: 1.5em;"><spring:message code="ims.pcd.addressinfo.040"/></div>
						</p>					
					</div>	       	
	      	 	</td>
	       	</tr>

	       	<tr>
	       	<td align="left" colspan = "6">	       	
	       		<div id="cslOrder_error"  class="ui-widget" style="display:none">					
					<p>						
						<div class="error" id="csl_order_error_msg"  style="font-weight:bold; margin-left: 1.5em;"></div>
					</p>					
				</div>	       	
	       	</td>
	       	</tr>
	       	
	       <tr id = "cslOfferSubmit"> 
	       		<td></td>  
	       		<td></td>
	       		<td></td>
				<td align="right" colspan="6">
					<div>
						<a href="#" id="continue" class="nextbuttonS" onClick="cslFormSubmit();" ><spring:message code="ims.pcd.addressinfo.042"/>&gt;</a>
					</div> 
				</td>
			</tr>
	  
			</table>
			</div>
<!-- 			celia(End) -->
			
			<input type="hidden" id="imsSearchFrom" value="" />
			<input type="hidden" name="currentView" value="addressinfo" />
			
			<form:hidden path="mobileOfferInd" />
			<form:hidden path="customer.mobileCutomerInfo.idDocType" />
			<form:hidden path="customer.mobileCutomerInfo.idDocNum" />
			<form:hidden path="customer.mobileCutomerInfo.firstName" />
			<form:hidden path="customer.mobileCutomerInfo.lastName" />
			<form:hidden path="customer.mobileCutomerInfo.mobCustNo" />
			<form:hidden path="customer.blacklistInd" />
			<form:hidden path="customer.lob" />
			<form:hidden path="installAddress.serbdyno" />
			<form:hidden path="installAddress.blacklistInd" />
			<form:hidden path="installAddress.strCatCd" />
			<form:hidden path="blacklistCustInfo" />
			<form:hidden path="prevSbNo" />
			<form:hidden path="phInd" />
			<form:hidden path="isPon4Village" />
			<form:hidden path="pon4VillageSubmitted" />
			<input type="hidden" id="blockageCode" value="N" />
			<input type="hidden" id="isError1" value="N" />
			<input type="hidden" id="isError2" value="N" />
			<div id="addressInfoBySB_relatedPONSBMsg" style="display:none">
				<c:out value="${relatedPonSBMsg}" escapeXml="false" />
			</div>
		</td>
	</tr>
	</table>
</form:form>

<!---------------------------------------  end content ------------------------------------------->

<%@ include file="/WEB-INF/jsp/footer.jsp"%>