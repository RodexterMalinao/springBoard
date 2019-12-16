<%@ include file="header.jsp" %>
<%@ include file="imsloadingpanel.jsp" %>
<%-- <%@ include file="imsvalidwindow.jsp" %> --%>

<%@ page import="			
					org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.ims.dto.ui.VASDetailUI,
					com.bomwebportal.ims.dto.ui.BasketDetailUI,
					com.bomwebportal.ims.dto.BasketDetailsDTO,
					java.util.*
					"
%>
<link href="css/ims.css" rel="stylesheet" type="text/css">

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>

<script language="Javascript">
	var valid = "Y";
	var error = 0;

// 	$(document).ready(function() {
// 		if("${ImsOrder.orderActionInd}" == "W" 
// 				&& ("${ImsOrder.orderStatus}" == "31")){
// 			$(":input").attr("disabled", true);
// 		}
// 	});


	$(window).load(function () {
		if (document.getElementById('promoGiftCode').value){
			addBackPromoGift(document.getElementById('promoGiftCode').value);
		}
	});
			
	

	//kinman	

	$(document).ready(function() {
		
		$("input[name='VASItemBox']").each(function(){
			if($(this).parents().find('div#vasParm_'+this.value).length > 0 ){
				if(this.checked){
					if (document.getElementById("vasParm_"+this.value) != null) {
						document.getElementById("vasParm_"+this.value).style.display = "inline";
					}	
					if (document.getElementById("VASDtl"+this.value) != null) {
						document.getElementById("VASDtl"+this.value).style.display = "inline";
					}
				}else{
					if (document.getElementById("VASDtl"+this.value) != null) {
						document.getElementById("VASDtl"+this.value).style.display = "none";
					}
					if (document.getElementById("AttbWarn"+this.value) != null) {
						document.getElementById("AttbWarn"+this.value).style.display = "none";
					}
					if (document.getElementById("vasParm_"+this.value) != null) {
						document.getElementById("vasParm_"+this.value).style.display = "none";
					}	
				}
			}
			if(this.checked){
				document.getElementById("VASDtl"+this.value).style.display = "inline";
				//display VAS link gift
				$('.LINK'+this.value).css('display','');
				var linkGiftItem = $('.LINK'+this.value).find('.gift');
				$(linkGiftItem).each(function(){	 
					var linkGiftId = this.value;
					$('#VAS'+linkGiftId).parent().parent().parent().find('.giftHeader').css('display','');//show gift header
					$('#SHOWIND'+linkGiftId).val('Y');//set show indicator to Y
				});
			}
		});
		
		$("[id^='warnVasParm_']").find('.error').each(function(){
			if ($(this).text() != null) {
				$(this).parent().parent().parent().show();
			}	
		});
		
		$("[id^='error_ParmA']").find('.error').each(function(){
			if ($(this).text() != null) {
				$(this).parent().parent().parent().show();
			}	
		});
		
		$("[id^='error_ParmB']").find('.error').each(function(){
			if ($(this).text() != null) {
				$(this).parent().parent().parent().show();
			}	
		});
		
		$("[id^='error_ParmC']").find('.error').each(function(){
			if ($(this).text() != null) {
				$(this).parent().parent().parent().show();
			}	
		});
		
		$("[id^='error_giftAttb']").find('.error').each(function(){
			if ($(this).text() != null) {
				$(this).parent().parent().parent().show();
			}	
		});
		
	});
	

	
	function checkCslOfferMissingMrtPin(){
		
		//init cslNumPinMissing as "N"
		$("#cslNumPinMissing").val("N"); 
		
		//core offer - csl offer 
		$("[id^='core_vasParm_']").each(function(){
			var coreVasParmFullId = this.id;
			var coreVasParmItemId = coreVasParmFullId.replace("core_vasParm_",""); 
			if($('#mRTNumber'+ coreVasParmItemId).val() == "" && $('#mRTPin'+ coreVasParmItemId).val() == ""){
				$("#cslNumPinMissing").val("Y");
			}			
		});
		
		
		//find Mandatory Bvas offer have csl checked		
		$("input[name='VASItemBoxDisable']:checked").parent().find("[id^='vasParm_']").each(function(){
			var vasParmFullId = this.id;
			var vasParmItemId = vasParmFullId.replace("vasParm_",""); 
			if($('#mRTNumber'+ vasParmItemId).val() == "" && $('#mRTPin'+ vasParmItemId).val() == ""){
				$("#cslNumPinMissing").val("Y");
			}
		});
		
		//find deflaut and optional Bvas offer have csl checked		
		$("input[name='VASItemBox']:checked").parent().find("[id^='vasParm_']").each(function(){
			var vasParmFullId = this.id;
			var vasParmItemId = vasParmFullId.replace("vasParm_",""); 
			if($('#mRTNumber'+ vasParmItemId).val() == "" && $('#mRTPin'+ vasParmItemId).val() == ""){
				$("#cslNumPinMissing").val("Y");
			}
		});
		
		//find vas offer have csl checked	
		$("input[name='VASItemBox']:checked").parent().siblings().find("[id^='vasParm_']").each(function(){
			var vasParmFullId = this.id;
			var vasParmItemId = vasParmFullId.replace("vasParm_",""); 
			if($('#mRTNumber'+ vasParmItemId).val() == "" && $('#mRTPin'+ vasParmItemId).val() == ""){
				$("#cslNumPinMissing").val("Y");
			}	
		});
		
		if($("#cslNumPinMissing").val() == "Y"){
			if(confirm("<spring:message code="ims.pcd.basketdetails.msg.001"/>") == true){			
				return true;
			}
		}else return true;
				
	}
	
	
	function formSubmit(){
		//checkCslOfferMissingMrtPin();
		//if(checkAllAttb() && checkCsloffer()){
		if(checkCslOfferMissingMrtPin() && checkAllAttb()){
			$(":input").attr("disabled", false);

			var clubPtRedmVASGiftSelected = false;
			$('.CLUB_PT_REDM_GIFT').each(function() {
				if($(this).is(':checked')){
					clubPtRedmVASGiftSelected = true;
			    }
			});
			$('.IMS_ITYPE_CLUB_PT_REDM_ISF').each(function() {
				if($(this).is(':checked')){
					clubPtRedmVASGiftSelected = true;
			    }
			});
			
			if(clubPtRedmVASGiftSelected && $('#otcDesc').is(':visible')){
				$(function(){
					var self = this;
					var preInstSubmitBox = new imsSBModalBox({
						width: "50%", 
						height: "auto",
						maxHeight: "350px",
						title: "", 
						text: document.getElementById("overrideOTC_Msg").innerHTML, 
						ButtonYtext: "OK", 
						ButtonNtext: "",
						centerBox: "Y",
						onClose: function() {
							window.location.href = self.href;
							submitShowLoading();//redirect after this box is closed
							document.imsBasketDetailForm.submit();
						}
					});
					preInstSubmitBox.openBox();
				});
			}else{
				submitShowLoading();
				document.imsBasketDetailForm.submit();
			}
		}
	}
	
	function isInt(x) 
	 { 
	    var y=parseInt(x); 
	    if (isNaN(y)) return false; 
	    return x==y && x.toString()==y.toString(); 
	 } 

	function deselect(ItemID){	
		if(document.getElementById("VAS" + ItemID).checked == false){
			var answer = confirm("<spring:message code="ims.pcd.basketdetails.msg.002"/>");
			if (answer){
				document.getElementById("VAS" + ItemID).checked = false;
				document.getElementById("VASDtl"+ItemID).style.display = "none";
				if(document.getElementById("AttbWarn"+ItemID)!=null){
					document.getElementById("AttbWarn"+ItemID).style.display = "none";
				}
				if (document.getElementById("vasParm_"+ItemID) != null) {
					document.getElementById("vasParm_"+ItemID).style.display = "none";
				}
				if (document.getElementById("mRTNumber"+ItemID) != null) {
					document.getElementById("mRTNumber"+ItemID).value = "";
				}
				if (document.getElementById("mRTPin"+ItemID) != null) {
					document.getElementById("mRTPin"+ItemID).value = "";
				}
				if (document.getElementById("attbInput"+ItemID) != null) {
					document.getElementById("attbInput"+ItemID).value = "";
				}
				//hide VAS link gift if no other linked VAS to this gift checked
				var linkGiftItem = $('.LINK'+ItemID).find('.gift');
				$(linkGiftItem).each(function(){
					
					var hideInd = true;
					var linkGiftId = this.value;
					
					$("input[name='VASItemBox']").each(function(){
						if(this.checked){
							var checkItem = $('.LINK'+this.value).find('.gift');
							$(checkItem).each(function(){
								if(this.value==linkGiftId)
									hideInd = false;
							});
						}
					});
					
					if(hideInd){
						$('#VAS'+linkGiftId).attr('checked',false);
						$('#VASDtl'+linkGiftId).css('display','none');
						$('#VAS'+linkGiftId).parent().parent('tr').css('display','none');
						$('#SHOWIND'+linkGiftId).val('N');//set show indicator to N
						if(!$('#VAS'+linkGiftId).parent().parent().parent().find('.gift').is(':visible')){//hide gift header if no other gift displayed
							$('#VAS'+linkGiftId).parent().parent().parent().find('.giftHeader').css('display','none');
						}
					}
				});
			}else {
 				document.getElementById("VAS" + ItemID).checked = true;
 				document.getElementById("VASDtl"+ItemID).style.display = "inline";
 				if (document.getElementById("vasParm_"+ItemID) != null) {
 					document.getElementById("vasParm_"+ItemID).style.display = "inline";
 				} 			
			}
		}else{
			document.getElementById("VASDtl"+ItemID).style.display = "inline";
			if (document.getElementById("vasParm_"+ItemID) != null) {
				document.getElementById("vasParm_"+ItemID).style.display = "inline";
			}
			//display and pre-tick VAS link gift
				$('.LINK'+ItemID).css('display','');
				var linkGiftItem = $('.LINK'+ItemID).find('.gift');
				$(linkGiftItem).each(function(){	 
					var linkGiftId = this.value;
					$('#VAS'+linkGiftId).parent().parent().parent().find('.giftHeader').css('display','');//show gift header
					$('#VAS'+linkGiftId).attr('checked',true);
					$('#VASDtl'+linkGiftId).css('display','');
					$('#SHOWIND'+linkGiftId).val('Y');//set show indicator to Y
				});
		}
	}
	function checkVASDtl(ItemID){
		deselect(ItemID);
		if(document.getElementById("VAS"+ItemID).checked == false){
			document.getElementById("VASDtl"+ItemID).style.display = "none";
			if(document.getElementById("AttbWarn"+ItemID)!=null){
				document.getElementById("AttbWarn"+ItemID).style.display = "none";
			}
			if (document.getElementById("vasParm_"+ItemID) != null) {
				document.getElementById("vasParm_"+ItemID).style.display = "none";
			}
			if (document.getElementById("mRTNumber"+ItemID) != null) {
				document.getElementById("mRTNumber"+ItemID).value = "";
			}
			if (document.getElementById("mRTPin"+ItemID) != null) {
				document.getElementById("mRTPin"+ItemID).value = "";
			}
			if (document.getElementById("attbInput"+ItemID) != null) {
				document.getElementById("attbInput"+ItemID).value = "";
			}
			
		}else{
			if (document.getElementById("vasParm_"+ItemID) != null) {
				document.getElementById("vasParm_"+ItemID).style.display = "inline";
			}
			document.getElementById("VASDtl"+ItemID).style.display = "inline";
		}
		
		if (document.getElementById("VAS"+ItemID).checked == true) {
			if (document.getElementById("vasAlertMsg"+ItemID) != null) {
				if (document.getElementById("vasAlertMsg"+ItemID).value != null && document.getElementById("vasAlertMsg"+ItemID).value != "" && document.getElementById("vasAlertMsg"+ItemID).value.toLowerCase() != "null") {
					if (confirm(document.getElementById("vasAlertMsg"+ItemID).value))  {
						document.getElementById("VASDtl"+ItemID).style.display = "inline";
					} else {
						document.getElementById("VAS" + ItemID).checked = false;
						document.getElementById("VASDtl"+ItemID).style.display = "none";
					}
				}
			}
		}	
		
		if (document.getElementById("VAS"+ItemID).checked == true) {
			if (document.getElementById("vasPreInstAlertMsg"+ItemID) != null) {
				if (document.getElementById("vasPreInstAlertMsg"+ItemID).value != null && document.getElementById("vasPreInstAlertMsg"+ItemID).value != "" && document.getElementById("vasPreInstAlertMsg"+ItemID).value.toLowerCase() != "null") {
					if (confirm(document.getElementById("vasPreInstAlertMsg"+ItemID).value))  {
						document.getElementById("VASDtl"+ItemID).style.display = "inline";
					} else {
						document.getElementById("VAS" + ItemID).checked = false;
						document.getElementById("VASDtl"+ItemID).style.display = "none";
					}
				}
			}
		}	
		
	}
	
	function checkMobile(ItemID){
	
		var i;
		var mobileNum;
		if(document.getElementById("AttbWarn"+ItemID)!=null){
			document.getElementById("AttbWarn"+ItemID).style.display = "none";
		}
		if(document.getElementById("attbInput"+ItemID)!=null){
			mobileNum = document.getElementById("attbInput"+ItemID).value;
		}
		if(mobileNum!=null){
			if(mobileNum.substring(0, 1)=="9" 
				|| mobileNum.substring(0, 1)=="8" 
					|| mobileNum.substring(0, 1)=="7" 
						|| mobileNum.substring(0, 1)=="6"
							|| mobileNum.substring(0, 1)=="5"
								|| mobileNum.substring(0, 1)=="4"){
	    		valid = "Y";
	    		if(document.getElementById("AttbWarn"+ItemID)!=null){
	    			document.getElementById("AttbWarn"+ItemID).style.display = "none";
	    		}
	    	}else{
	    		valid = "N";
	    		document.getElementById("AttbWarn"+ItemID).style.display = "inline";
	    	}
			
			if(mobileNum.length!= 8){
				valid = "N";
	    		document.getElementById("AttbWarn"+ItemID).style.display = "inline";
	    	}
	    	
	    	if(mobileNum != null || mobileNum!=""){
	    		for(i=0; i<mobileNum.length; i++){
	                if(!isInt(mobileNum.charAt(i))){
	                	valid = "N";
	                	document.getElementById("AttbWarn"+ItemID).style.display = "inline";
	                }
	    		}
	    	}
		}
	}
	
	function checkVasParmMobile(input){
	
		if(input==""){
			valid = "N";
		}else{
			if(input.substring(0, 1)=="9"  
					|| input.substring(0, 1)=="8"
					|| input.substring(0, 1)=="6" 
					|| input.substring(0, 1)=="5"){
	    		valid = "Y";
	    	}else{
	    		valid = "N";
	    	}
			
			if(input.length!= 8){
				valid = "N";
	    	}
	    	
	    	if(input != null || input!=""){
	    		for(i=0; i<input.length; i++){
	                if(!isInt(input.charAt(i))){
	                	valid = "N";
	                }
	    		}
	    	}
		}
		return valid;
	}
	
	function checkNumber(input){
	
		valid = "Y";
		if(input==""){
			valid = "N";
		}else{
	    	
	    	if(input != null || input!=""){
	    		for(i=0; i<input.length; i++){
	                if(!isInt(input.charAt(i))){
	                	valid = "N";
	                }
	    		}
	    	}
		}
		return valid;
	}
	
	function checkAsiaMileAttb(inputValue,type){
		
		var valid;
	    		
	    $.ajax({
	    	url : 'checkasiamilesattb.html?input='+inputValue+'&type='+type,
	    	async: false,
	    	type : 'POST',
	    	dataType : 'json',
	    	timeout : 60000,
	    	error : function(XMLHttpRequest, textStatus, errorThrown) {
	    		alert('error: '+textStatus);	
	    			
	    		if(textStatus=='parsererror'){
// 	    			alert("Your session has been timed out, please re-login."); 
	    			alert("<spring:message code="ims.pcd.basketdetails.msg.003"/>");
	    		}else if(textStatus == 'timeout'){
// 	    			alert("Server response timeout, please try again.");
	    			alert("<spring:message code="ims.pcd.basketdetails.msg.004"/>");
	    		}
	    	},	
	    	success : function(msg) {
	    				
	    		$.each(eval(msg), function(i, item) {
	    			//alert('asiamileid '+item.aisaMilesId+' valid '+item.valid);	
	    			valid = item.valid;
	    		});
	    	}
	    });
	    		
	    return valid;
	}
	
	function checkNull(input,itemID,count) {
		var inputValue = $(input).val();
		var result;

		document.getElementById("AttbWarn"+itemID+"_"+count).style.display = "none";
		
		if($(input).val().length == 0) {
			if($(input).hasClass('ASIA_MILE_FAMILY_NAME')){
				document.getElementById("AttbErr"+itemID+"_"+count).innerHTML="<spring:message code="ims.pcd.basketdetails.msg.005"/>";
				document.getElementById("AttbWarn"+itemID+"_"+count).style.display = "inline";	
			}
			else if ($(input).hasClass('ASIA_MILE_GIVEN_NAME')){	
				document.getElementById("AttbErr"+itemID+"_"+count).innerHTML="<spring:message code="ims.pcd.basketdetails.msg.006"/>";
				document.getElementById("AttbWarn"+itemID+"_"+count).style.display = "inline";		 
			}
			else if ($(input).hasClass('ASIA_MILE_ID')){
				document.getElementById("AttbErr"+itemID+"_"+count).innerHTML="<spring:message code="ims.pcd.basketdetails.msg.007"/>";
				document.getElementById("AttbWarn"+itemID+"_"+count).style.display = "inline";
			}
			else {
				document.getElementById("AttbErr"+itemID+"_"+count).innerHTML="<spring:message code="ims.pcd.basketdetails.msg.008"/>";
				document.getElementById("AttbWarn"+itemID+"_"+count).style.display = "inline";
			}
			
			return "N";
		 
		}else{
			if ($(input).hasClass('ASIA_MILE_ID')){
				result = checkAsiaMileAttb(inputValue,"id");
				if(result=="N"){
					document.getElementById("AttbErr"+itemID+"_"+count).innerHTML="<spring:message code="ims.pcd.basketdetails.msg.009"/>";
					document.getElementById("AttbWarn"+itemID+"_"+count).style.display = "inline";
				}
				return result;
			}
			if ($(input).hasClass('ASIA_MILE_FAMILY_NAME')){
				result = checkAsiaMileAttb(inputValue,"name");
				if(result=="N"){
					document.getElementById("AttbErr"+itemID+"_"+count).innerHTML="<spring:message code="ims.pcd.basketdetails.msg.010"/>";
					document.getElementById("AttbWarn"+itemID+"_"+count).style.display = "inline";
				}
				return result;
			}
			if ($(input).hasClass('ASIA_MILE_GIVEN_NAME')){
				result = checkAsiaMileAttb(inputValue,"name");
				if(result=="N"){
					document.getElementById("AttbErr"+itemID+"_"+count).innerHTML="<spring:message code="ims.pcd.basketdetails.msg.010"/>";
					document.getElementById("AttbWarn"+itemID+"_"+count).style.display = "inline";
				}
				return result;
			}
		}

		return "Y";
	}
	
	function checkGiftAttb(ItemID){
	
		var i;
		var mobileNum;
		var tmpValid;
		if(document.getElementById("AttbWarn"+ItemID)!=null){
			document.getElementById("AttbWarn"+ItemID).style.display = "none";
		}
// 		if(document.getElementById("attbInput"+ItemID)!=null){
// 			mobileNum = document.getElementById("attbInput"+ItemID).value;
// 		}
		 
// 		 $('.gift').each(function() {
// 			 if($(this).is(':checked')){
				 //var remark=$(this).parent().parent('tr').find('.giftremark'); 
				// var length=$(this).parent().parent('tr').find('.giftremark').length; 
		var length=$('#VAS'+ItemID).parent().parent('tr').find('.giftremark').length; 

		//alert(ItemID+" "+length);
		if (length>0){
			var remark=$('#VAS'+ItemID).parent().parent('tr').find('.giftremark'); 
			var count=0;
			$(remark).each(function(){	 
				tmpValid=checkNull(this,ItemID,count);
				count++;
				if (tmpValid=="N"){
					valid="N";
				}
			});
		}
		
// 		 $('.giftAttb').each(function() {
// 			 if($(this).parents('tbody').find('.gift').attr('checked')) {
// 				 $(this).removeClass('hide');
// 				 if($(this).find('.giftremark').hasClass('ASIA_MILE_ID')) {
// 					 $(this).parents('tbody').find('.amnote').removeClass('hide');
// 				 }
// 			} 
// 		 });  
	}
	
	function checkNullImsVasParm(input,itemID,count) {
		var inputValue = $(input).val();
		var result;
		
		document.getElementById("ParmWarn"+count+itemID).style.display = "none";
		
		if(inputValue.length == 0) {
			document.getElementById("ParmErr"+count+itemID).innerHTML="<spring:message code="ims.pcd.basketdetails.msg.011"/>";
			document.getElementById("ParmWarn"+count+itemID).style.display = "inline";	
			
			return "N";
		 
		}
		return "Y";
	}
	
	function checkImsVasParmInput(ItemID){
	
		var i;
		var mobileNum;
		var tmpValid;
		var parmInputResult = "Y";
		if(document.getElementById("AttbWarn"+ItemID)!=null){
			document.getElementById("AttbWarn"+ItemID).style.display = "none";
		}
		var length=$('#VAS'+ItemID).parent().parent('tr').find('.imsvasparminput').length; 
		
		if (length>0){
			var remark=$('#VAS'+ItemID).parent().parent('tr').find('.imsvasparminput'); 
			var count=0;
			$(remark).each(function(){	 
				tmpValid=checkNullImsVasParm(this,ItemID,count);
				count++;
				if (tmpValid=="N"){
					parmInputResult="N";
				}
			});
		}
		
		return parmInputResult;
	}
	
	function checkAllAttb()
	{
		var addVASCheckboxGroup=document.forms["imsBasketDetailForm"].VASItemBox;
		var addGiftCheckboxGroup=document.forms["imsBasketDetailForm"].GiftItemBox;
		var i = 0;
		var ItemID = "";
		error = 0;
		valid = "Y";
		var parmResult;
		
		if(addVASCheckboxGroup!=null){
			for(i=0;i<addVASCheckboxGroup.length;i++){
				if(addVASCheckboxGroup[i].checked){
					ItemID = addVASCheckboxGroup[i].value;	
					checkMobile(ItemID);
					if(valid=="N"){
						error++;
					}
				}
			}
		}

		
		if(addVASCheckboxGroup!=null){
			for(i=0;i<addVASCheckboxGroup.length;i++){
				if(addVASCheckboxGroup[i].checked){
					ItemID = addVASCheckboxGroup[i].value;
					parmResult=checkImsVasParmInput(ItemID);
					if(parmResult=="N"){
						error++;
					}
				}
			}
		}
		
		if(addGiftCheckboxGroup!=null){
			for(i=0;i<addGiftCheckboxGroup.length;i++){
				if(addGiftCheckboxGroup[i].checked){
					ItemID = addGiftCheckboxGroup[i].value;	
					checkGiftAttb(ItemID);
					if(valid=="N"){
						error++;
					}
				}
			}
		}
		
		if(error==0){
			return true;
		}
		
	}
	
	function giftSelect(id){
	       //var group = 'input:checkbox[name="GiftItemBox"]';
	       if($('#VAS'+id).is(':checked')){
		       var group = $('#VAS'+id).parent().parent().parent().find('input:checkbox[name="GiftItemBox"]');
		       $(group).attr("checked", false);
		       $(group).each(function() {
		    	   var giftid = $(this).attr('id').substring(3);
					document.getElementById("VASDtl"+giftid).style.display = "none";
		       });
		       $('#VAS'+id).attr("checked", true);
		       checkVASDtl(id);
	       }else{
	    	   deselect(id);
	       }
	}

	
	function blurOnAttbInput(item){
		 
			 $('.ASIA_MILE_FAMILY_NAME, .ASIA_MILE_GIVEN_NAME').keyup(function() {
			 $(this).val($(this).val().toUpperCase()); 
		 }); 
		 
		 $('.ASIA_MILE_FAMILY_NAME, .ASIA_MILE_GIVEN_NAME').blur(function() {
			 $(this).val($(this).val().toUpperCase()); 
		 }); 
	}
	
	function validatePromoGift(pcode){
		
		if($('#'+pcode+'d').length>0){
			var quota = $('#'+pcode+'d').find('#cnt_'+pcode).text();
			quota = parseInt(quota,10);
			if(quota>0){
				$('#promogiftWarn').css('display','none');
				$('.promogiftWarnText').text('');
				$('.promgift_detail').css('display','none');
				$('#'+pcode+'d').css('display','');
				document.getElementById('promoGiftCode').value = pcode;
				document.getElementById('promoCode').value = '';
			}else{
				$('.promgift_detail').css('display','none');
				document.getElementById('promoGiftCode').value = '';
				$('#promogiftWarn').css('display','');
				$('.promogiftWarnText').text('<spring:message code="ims.pcd.basketdetails.msg.012"/>');
				document.getElementById('promoCode').value = '';
				
			}
		}else{
			$('.promgift_detail').css('display','none');
			document.getElementById('promoGiftCode').value = '';
			$('#promogiftWarn').css('display','');
			$('.promogiftWarnText').text('<spring:message code="ims.pcd.basketdetails.msg.013"/>');
			document.getElementById('promoCode').value = '';
		}
	
	}
	
	function addBackPromoGift(pcode){
		validatePromoGift(pcode);
	}
	
	function checkPromoGift(){
		
		var pcode = document.getElementById('promoCode').value;
		
		if (pcode!=""){
			validatePromoGift(pcode);
		}
	}
			
</script> 

<table width="100%" height="30">
	<tr>
		<td class="contenttextgreen" align="center">
		 	<spring:message code="ims.pcd.basketdetails.007"/>
		</td>
	</tr>
</table>

<link href="css/imsds.css" rel="stylesheet" type="text/css">
<%@ include file="/WEB-INF/jsp/imssubheader.jsp" %>
<%@ include file="/WEB-INF/jsp/imsspringboardmodalboxui.jsp" %>
<br/>

<form:form name="imsBasketDetailForm" commandName="basketdetailinfo" method="POST">
<input type="hidden" name="IMS_UID" value="<%= request.getAttribute("IMS_UID") %>" />
 <table width="100%" border="0" >
 	<tr>
	<td>
	
<%
	BasketDetailUI BasketDetail = (BasketDetailUI)request.getAttribute("basketdetailinfo");
	List<BasketDetailsDTO> BasketDetailList = BasketDetail.getBasketDetailList();

%>

  <table width="100%" border="1"> 
	<tr>
		<td colspan='4'>
			<table width="100%" border="0" bgcolor="#FFFFFF">
				<tr>
					<td class="table_title2" ><spring:message code="ims.pcd.basketdetails.001"/></td>
					<td class="table_title2" width="13%"><spring:message code="ims.pcd.basketdetails.002"/></td>
					<td class="table_title2" width="13%"><spring:message code="ims.pcd.basketdetails.003"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
<%
		for(int i =0;i<BasketDetailList.size();i++){
			out.println("<td width='15%' rowspan='4' valign='top'>");
			if(!BasketDetailList.get(i).getImagePath().equals("NA")){
				out.println("<img src='basket/"+
						BasketDetailList.get(i).getImagePath() +
						"' width='148'>");						
			}
			out.println("<p class='contenttext'></p></td>");		
		}
%>	
		
	  
		<td colspan="2">
	  		<table width="100%" border="0" bgcolor="#FFFFFF">
<!--<%-->
<!--		for(int i =0;i<BasketDetailList.size();i++){-->
<!--			-->
<!--					if ( BasketDetailList.get(i) != null && BasketDetailList.get(i).getBandwidth()!= null )-->
<!--					{-->
<!--						out.println("<tr><td colspan= '2' align='left'><span class='basket_title'>"+-->
<!--						BasketDetailList.get(i).getBandwidth() + "M " + -->
<!--						BasketDetailList.get(i).getTitle()+"</span><br><br></td>"+-->
<!--						"<td></td><td></td></tr>");-->
<!--					}-->
<!--					else-->
<!--					{-->
<!--						out.println("<tr><td colspan= '2' align='left'><span class='basket_title'>"+-->
<!--						BasketDetailList.get(i).getTitle()+"</span><br><br></td>"+-->
<!--						"<td></td><td></td></tr>");-->
<!--					}-->
<!--					-->
<!--	            	-->
<!---->
<!--			out.println("<tr></tr><tr><td colspan= '2' width='70%' valign='top'><DIR><span class='item_detail'>"+-->
<!--					BasketDetailList.get(i).getItemDetail()+"</span><br></DIR></td>"+-->
<!--					"<td class='BGgreen2' align='center' width='15%'>"+-->
<!--	            	BasketDetailList.get(i).getMthFixText()+ "</td><td class='BGgreen2' align='center' width='15%'>"+-->
<!--	            	BasketDetailList.get(i).getMthToMthText()+"</td></tr>");-->
<!--		}-->
<!---->
<!--%>-->

				<c:forEach items="${basketdetailinfo.basketDetailList}" var="BDetails" varStatus="status">
					<c:choose>
						<c:when test="${BDetails.bandwidth != null}">
							<tr>
								<td colspan= '2' align='left'>
									<span class='basket_title'>${BDetails.bandwidth_desc} ${BDetails.title}</span><br><br>
								</td>
							<td></td>
							<td></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan= '2' align='left'>
									<span class='basket_title'>${BDetails.title}</span><br><br>
								</td>
								<td></td>
								<td></td>
							</tr>
					</c:otherwise>
					</c:choose>
						<tr></tr>
						<tr>
							<td colspan= '2' width='70%' valign='top'>
								<DIR>
									<span class='item_detail'>${BDetails.itemDetail}</span>
									<br>									
								</DIR>
						<!-- kinman CSL Basket-->
						<c:if test="${BDetails.vasParmDTO.vas_type == 'MOBILEPIN'}">
						<div id="core_vasParm_${BDetails.itemID}" class="ui-widget">
						
						<DIR ><span class='item_detail'><spring:message code="ims.pcd.basketdetails.004"/></span></DIR>
						<DIR>
							<span class='item_detail'>${BDetails.vasParmDTO.vas_type_display}</span>
							<br/> <br/>
							<span class="item_detail" style="display:inline-block;width:120px">${BDetails.vasParmDTO.vas_parm_a_display}</span>
							<form:input id="mRTNumber${BDetails.itemID}" path="basketDetailList[${status.index}].vasParmDTO.vas_parm_a" maxlength="15"/>
							
							<br/>
							<span class="item_detail" style="display:inline-block;width:120px">${BDetails.vasParmDTO.vas_parm_b_display}</span>
							
							<form:input id="mRTPin${BDetails.itemID}" path="basketDetailList[${status.index}].vasParmDTO.vas_parm_b" maxlength="15"/>
			
						</DIR> 
						
						<div id="warnVasParm_${BDetails.itemID}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>
 								<div class="contenttext_red"><form:errors path="basketDetailList[${status.index}].vasParmDTO.vas_parm_a" cssClass="error" /></div></p>
 							</div>
						</div>
						
						
						</div>
						</c:if>
							</td>
							<td class='BGgreen2' align='center' width='15%'>${BDetails.mthFixText}</td>
							<td class='BGgreen2' align='center' width='15%'>${BDetails.mthToMthText}</td>
						</tr>
				</c:forEach>
	  		
	  		

				<c:forEach items="${basketdetailinfo.BVasDetailList}" var="BVASDetails" varStatus="status">
				<c:choose>
				<c:when test="${BVASDetails.itemType == 'OTHER' || (BVASDetails.VASTitle == null && BVASDetails.itemMdoInd == 'M')}">
				<tr style="display:none;" >	
					<td>
						<input id="paraFlag" type="checkbox" name="VASItemBox" value="${BVASDetails.itemId}" checked/>			
					</td>
					<td class="BGgreen2" align="right" width="15%"></td>
	               	<td class="BGgreen2" align="right" width="15%"></td>	
				</tr>					
				</c:when>		
				<c:when test="${BVASDetails.itemType != 'OTHER'&& BVASDetails.VASTitle != null}">
				<tr>	
					<c:if test="${BVASDetails.itemMdoInd == 'M'}">								
					<td colspan= "2" valign="top">
					 	<input id="paraFlag" type="checkbox" name="VASItemBoxDisable" value="${BVASDetails.itemId}"  disabled="disabled" checked/>
						<span class="item_title_vas">${BVASDetails.VASTitle}</span>
						<!-- kinman CSL mandatory BVAS-->
						<c:if test="${BVASDetails.vasParmDTO.vas_type == 'MOBILEPIN'}">
						<div id="vasParm_${BVASDetails.itemId}" class="ui-widget">
						
						<DIR ><span class='item_detail'><spring:message code="ims.pcd.basketdetails.004"/></span></DIR>
						<DIR>
							<span class='item_detail'>${BVASDetails.vasParmDTO.vas_type_display}</span>
							<br/> <br/>
							<span class="item_detail" style="display:inline-block;width:120px">${BVASDetails.vasParmDTO.vas_parm_a_display}</span>
							<form:input id="mRTNumber${BVASDetails.itemId}" path="BVasDetailList[${status.index}].vasParmDTO.vas_parm_a" maxlength="15"/>
							
							<br/>
							<span class="item_detail" style="display:inline-block;width:120px">${BVASDetails.vasParmDTO.vas_parm_b_display}</span>
							
							<form:input id="mRTPin${BVASDetails.itemId}" path="BVasDetailList[${status.index}].vasParmDTO.vas_parm_b" maxlength="15"/>
			
						</DIR> 
						
						<div id="warnVasParm_${BVASDetails.itemId}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>
 								<div class="contenttext_red"><form:errors path="BVasDetailList[${status.index}].vasParmDTO.vas_parm_a" cssClass="error" /></div></p>
 							</div>
						</div>
						
						
						</div>
						</c:if>
						<div id="VASDtl${BVASDetails.itemId}" style=display:inline>
						<DIR>
							<c:if test="${BVASDetails.attbId != null}">
								<span class='item_detail'>${BVASDetails.attbDesc}</span>
								<c:if test="${BVASDetails.inputMethod == 'INPUT'}">
									<form:input id="attbInput${BVASDetails.itemId}" path="BVasDetailList[${status.index}].inputValue" maxlength="8" onblur="checkMobile(${BVASDetails.itemId})" />
								</c:if>
								
								<br>
							</c:if>
							<span class='item_detail'>${BVASDetails.VASDetail}</span>
							
						</DIR>
						</div>
						
						<div id="AttbWarn${BVASDetails.itemId}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>
 								<div class="contenttext"><spring:message code="ims.pcd.basketdetails.msg.014"/></div></p>
 							</div>
						</div>
						<br/>
						<input style="display:none;" id="paraFlag" type="checkbox" name="VASItemBox" value="${BVASDetails.itemId}" checked/>
						<br/></td>
						<td class="BGgreen2" align="right" width="15%">${BVASDetails.mthFixText}</td>
	               		<td class="BGgreen2" align="right" width="15%">${BVASDetails.mthToMthText}</td>		
					</c:if>
					<c:if test="${BVASDetails.itemMdoInd == 'D'}">
						<td colspan= "2" valign="top">
						<c:choose>
						<c:when test="${fn:length(selectedIMSVasList) != 0 && isSameBasket=='Y'}">
							<c:set var="checkFrag" scope="session" value=""/>
							<c:forEach items="${selectedIMSVasList}" var="selectedIMSVas" varStatus="VasStatus">
								<c:if test='${selectedIMSVas == BVASDetails.itemId}'>
									<c:set var="checkFrag" scope="session" value="checked"/>
								</c:if>
 							</c:forEach> 								
 						</c:when>
 						<c:otherwise>
 							<c:set var="checkFrag" scope="session" value="checked"/>
 						</c:otherwise>
 						</c:choose>
 						<input id="VAS${BVASDetails.itemId}" type="checkbox" name="VASItemBox" value="${BVASDetails.itemId}" onclick="deselect('${BVASDetails.itemId}')" ${checkFrag}/>
 								
 						<span class="item_title_vas">${BVASDetails.VASTitle}</span>
						
						
						<!-- kinman CSL default BVAS-->
						<c:if test="${BVASDetails.vasParmDTO.vas_type == 'MOBILEPIN'}">
						<div id="vasParm_${BVASDetails.itemId}" class="ui-widget" style=display:none>
						<DIR ><span class='item_detail'><spring:message code="ims.pcd.basketdetails.004"/></span></DIR>
						<DIR>
							<span class='item_detail'>${BVASDetails.vasParmDTO.vas_type_display}</span>
							<br/> <br/>
							<span class="item_detail" style="display:inline-block;width:120px">${BVASDetails.vasParmDTO.vas_parm_a_display}</span>
							<form:input id="mRTNumber${BVASDetails.itemId}" path="BVasDetailList[${status.index}].vasParmDTO.vas_parm_a" maxlength="15"/>
							
							<br/>
							<span class="item_detail" style="display:inline-block;width:120px">${BVASDetails.vasParmDTO.vas_parm_b_display}</span>
							
							<form:input id="mRTPin${BVASDetails.itemId}" path="BVasDetailList[${status.index}].vasParmDTO.vas_parm_b" maxlength="15"/>
			
						</DIR> 
						
						<div id="warnVasParm_${BVASDetails.itemId}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>
 								<div class="contenttext_red"><form:errors path="BVasDetailList[${status.index}].vasParmDTO.vas_parm_a" cssClass="error" /></div></p>
 							</div>
						</div>
					
						</div>
						</c:if>
						
						
						
						
						<div id="VASDtl${BVASDetails.itemId}" style=display:inline>
						<DIR>
							<c:if test="${BVASDetails.attbId != null}">
								<span class='item_detail'>${BVASDetails.attbDesc}</span>
								<c:if test="${BVASDetails.inputMethod == 'INPUT'}">
									<form:input id="attbInput${BVASDetails.itemId}" path="BVasDetailList[${status.index}].inputValue" maxlength="8" onblur="checkMobile(${BVASDetails.itemId})" />
								</c:if>
								<br>
							</c:if>
							<span class='item_detail'>${BVASDetails.VASDetail}</span>
						</DIR> 
						</div>
						<div id="AttbWarn${BVASDetails.itemId}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
 							<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>
 								<div class="contenttext"><spring:message code="ims.pcd.basketdetails.msg.014"/></div></p>
 							</div>
						</div>
						<br/>
						</td>
						<td class="BGgreen2" align="right" width="15%">${BVASDetails.mthFixText}</td>
	               		<td class="BGgreen2" align="right" width="15%">${BVASDetails.mthToMthText}</td>
					</c:if>
					<c:if test="${BVASDetails.itemMdoInd == 'O'}">
						<td colspan= "2" valign="top">
						<c:choose>
						<c:when test='${fn:length(selectedIMSVasList) != 0}'>
							<c:set var="checkFrag" scope="session" value=""/>
							<c:forEach items="${selectedIMSVasList}" var="selectedIMSVas" varStatus="VasStatus">
								<c:if test='${selectedIMSVas == BVASDetails.itemId}'>
									<c:set var="checkFrag" scope="session" value="checked"/>
								</c:if>
 							</c:forEach> 								
 						</c:when>
 						<c:otherwise>
 							<c:set var="checkFrag" scope="session" value=""/>
 						</c:otherwise>
 						</c:choose>
							 <input type="checkbox" id="VAS${BVASDetails.itemId}" name="VASItemBox" value="${BVASDetails.itemId}" onclick="checkVASDtl(${BVASDetails.itemId})" ${checkFrag}> 
						<span class="item_title_vas">${BVASDetails.VASTitle}</span>
						
						<!-- kinman CSL optional BVAS -->
						<c:if test="${BVASDetails.vasParmDTO.vas_type == 'MOBILEPIN'}">
						<div id="vasParm_${BVASDetails.itemId}" class="ui-widget" style=display:none>
						<DIR ><span class='item_detail'><spring:message code="ims.pcd.basketdetails.004"/></span></DIR>
						<DIR>
							<span class='item_detail'>${BVASDetails.vasParmDTO.vas_type_display}</span>
							<br/> <br/>
							<span class="item_detail" style="display:inline-block;width:120px">${BVASDetails.vasParmDTO.vas_parm_a_display}</span>
							<form:input id="mRTNumber${BVASDetails.itemId}" path="BVasDetailList[${status.index}].vasParmDTO.vas_parm_a" maxlength="15"/>
							
							<br/>
							<span class="item_detail" style="display:inline-block;width:120px">${BVASDetails.vasParmDTO.vas_parm_b_display}</span>
							
							<form:input id="mRTPin${BVASDetails.itemId}" path="BVasDetailList[${status.index}].vasParmDTO.vas_parm_b" maxlength="15"/>
			
						</DIR> 
						
						<div id="warnVasParm_${BVASDetails.itemId}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>
 								<div class="contenttext_red"><form:errors path="BVasDetailList[${status.index}].vasParmDTO.vas_parm_a" cssClass="error" /></div></p>
 							</div>
						</div>
					
						</div>
						</c:if>
						
						
						<div id="VASDtl${BVASDetails.itemId}" style=display:none>
						<DIR>
							<c:if test="${BVASDetails.attbId != null}">
								<span class='item_detail'>${BVASDetails.attbDesc}</span>
								<c:if test="${BVASDetails.inputMethod == 'INPUT'}">
									<form:input id="attbInput${BVASDetails.itemId}" path="BVasDetailList[${status.index}].inputValue" maxlength="8" onblur="checkMobile(${BVASDetails.itemId})" />
								</c:if>
								<br>
							</c:if>
							<span class='item_detail'>${BVASDetails.VASDetail}</span>
						</DIR> 
						</div>
						<div id="AttbWarn${BVASDetails.itemId}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
 							<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
 								<div class="contenttext"><spring:message code="ims.pcd.basketdetails.msg.014"/></div></p>
 							</div>
						</div>
						<br/>
						</td>
						<td class="BGgreen2" align="right" width="15%">${BVASDetails.mthFixText}</td>
	               		<td class="BGgreen2" align="right" width="15%">${BVASDetails.mthToMthText}</td>
					</c:if>		
					<c:if test="${BVASDetails.itemMdoInd == 'X'}">
					</c:if>	
					
				</tr>
				</c:when>
				</c:choose>
				</c:forEach>
				<c:if test="${basketdetailinfo.basketSpecialOTCDesc!=''}">
				<tr>
				<td colspan= "2" valign="top">
				<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
					<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 	<div class="contenttext" id="otcDesc"><spring:message code="ims.pcd.basketdetails.008"/> ${basketdetailinfo.basketSpecialOTCDesc}</div></p>
				 </div>
				</td>
				<td class="BGgreen2" align="right" width="15%"></td>
	            <td class="BGgreen2" align="right" width="15%"></td>
				</tr>
				</c:if>
			</table>
		</td>
		
	</tr>
<!-- 	gift start   -->
	<c:if test="${basketdetailinfo.giftList!=null}">
	<tr>
		<td colspan="2" width="100%">
			<table width="100%" border="0">
<!-- 			<tr id="giftErr" style="display:none"> -->
<!-- 				<td> -->
<!-- 				<table width="100%" border="0" cellspacing="1" rules="none"> -->
<!-- 				<tr> -->
<!-- 					<td colspan="2" bgcolor="#FFFFFF"> -->
<!-- 						<span class="error">One welcome gift will be offered, please select. </span> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				</table> -->
<!-- 				</td> -->
<!-- 			</tr> -->
			
	 <spring:bind path="subscribedGiftsError">
		 <c:if test="${not empty status.errorMessages}">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="1" rules="none">
	 				<c:forEach items="${status.errorMessages}" var="error">
					<tr>
					<td colspan="2" bgcolor="#FFFFFF">
	        			<span class="error"><c:out value="${error}" escapeXml="false"/></span><br>
					</td>
					</tr>
	        		</c:forEach>
				</table>
				</td>
			</tr>
			<br>
		 </c:if> 
	</spring:bind>
	
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="1" rules="none">
						<tr>
						<td colspan="2" bgcolor="#FFFFFF">
							<form:errors path="exclusiveError" cssClass="error" />
						</td>
						</tr>
						<tr>
						<td colspan="2" bgcolor="#FFFFFF">
							<form:errors path="VASError" cssClass="error" />
						</td>
						</tr>
						</table>
					</td>
				</tr>
			
				<tr>
					<td bgcolor="#FFFFFF">
						<div>
 						<c:set var="giftType" scope="session" value="S"/>
						<c:forEach var="gift" items="${basketdetailinfo.giftList}" varStatus="status3">
						<c:if test="${giftType!=gift.typeDesc && giftType != 'S'}">
						</table> 
						</c:if>
						<c:if test="${giftType!=gift.typeDesc}"> 
						<table class="tableGreytext" width="100%" cellspacing="1" border="0" bgcolor="white"> 
						<tr <c:if test="${gift.normalGiftCnt == 0}">style="display:none" </c:if> class="giftHeader" >   
							<td colspan= "2" class="table_title2" align="left">${gift.typeDesc}<br>
							</td>
							<td class="table_title2" align="right" width="15%"><spring:message code="ims.pcd.basketdetails.002"/></td> 
 					   		<td class="table_title2" align="right" width="15%"><spring:message code="ims.pcd.basketdetails.003"/></td> 
 						</tr> 
 						</c:if>
						<c:choose>
						<c:when test='${fn:length(selectedIMSGiftList) != 0}'>
							<c:set var="checkFrag" scope="session" value=""/>
							<c:set var="display" scope="session" value="none"/>
							<c:forEach items="${selectedIMSGiftList}" var="selectedIMSVas" varStatus="VasStatus">
								<c:if test='${selectedIMSVas == gift.id}'>
									<c:set var="checkFrag" scope="session" value="checked"/>
									<c:set var="display" scope="session" value="inline"/>
								</c:if>
 							</c:forEach> 								
 						</c:when>
 						<c:otherwise>
 							<c:set var="checkFrag" scope="session" value=""/>
							<c:set var="display" scope="session" value="none"/>
 						</c:otherwise>
 						</c:choose>
 	            		<tr <c:if test="${not empty gift.linkVas}">style="display:none" class="${gift.linkVas}" </c:if> > 
 							<td valign="top"  width="10"> 
 								<input type="checkbox" id="VAS${gift.id}" class="gift ${gift.type}" name="GiftItemBox" value="${gift.id}" onclick="checkVASDtl(${gift.id});" ${checkFrag}> 
<%--  								<form:radiobutton cssClass="gift" path="basketDetailList[${status1.index}].giftInd" value="${gift.id}" onclick="javascript:$('.giftwarn').remove();"/> --%>
 							</td> 
 							<td valign="top"> 
 								<span class="item_title_vas">${gift.giftTitle}</span>
 								<div id="VASDtl${gift.id}" style=display:${display}>
								<DIR>
                					<c:if test="${!empty gift.giftAttbList}">
<%-- 										<c:if test="${gift.giftAttbList[0].inputFormat == 'ASIA_MILE_ID'}"> --%>
<!-- 											<tr class="amnote hide"> -->
<!-- 											<td width="10%" align="center" class="cell-middle"></td> -->
<%-- 											<td colspan=3><span class="inline"><spring:message code="reg.plan.asiamile.input"/></span></td></tr>    --%>
<%-- 										</c:if> --%>
										<c:forEach var="giftAttb" items="${gift.giftAttbList}" varStatus="status4">
											<c:if test="${giftAttb.inputMethod == 'INPUT'}">
												<span class='item_detail' style="display:inline-block;width:120px">${giftAttb.attbDesc}</span>
												<form:input id="attbInput${giftAttb.itemId}" cssClass="giftremark ${giftAttb.inputFormat}" path="giftList[${status3.index}].giftAttbList[${status4.index}].inputValue" maxlength="${giftAttb.maxLength}" onblur="blurOnAttbInput(this)"/><br>
												<div id="AttbWarn${giftAttb.itemId}_${status4.index}" class="GiftAttbWarn" class="ui-widget" style=display:none>
													<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
													<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 										<div class="contenttext" id="AttbErr${giftAttb.itemId}_${status4.index}"></div></p>
				 									</div>
												</div>
												<div id="error_giftAttb${giftAttb.itemId}_${status4.index}" class="ui-widget" style=display:none>
													<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
													<p>
										 			<span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
										 				<div class="contenttext"><form:errors path="giftList[${status3.index}].giftAttbList[${status4.index}].inputValue" cssClass="error" /></div></p>
										 			</div>
												</div>
											</c:if>
                						</c:forEach>
<%-- 										<c:if test="${gift.giftAttbList[0].inputFormat == 'ASIA_MILE_ID'}"> --%>
<!-- 											<tr class="amnote hide"> -->
<!-- 											<td width="10%" align="center" class="cell-middle"></td> -->
<%-- 											<td colspan=3><span class="inline"><spring:message code="reg.plan.asiamile.note"/></span></td></tr>    --%>
<%-- 										</c:if> --%>
                					</c:if>
								<br>
								<span class='item_detail'>${gift.giftDetail}</span>
								</DIR> 
								</div>
								<br/>
 							</td> 
 							<td align="right" valign="top" class="BGgreen2" width="15%"> 
 									N/A 
 							</td> 
 							<td align="right" valign="top" class="BGgreen2" width="15%"> 
 									N/A 
 							</td> 
 							 <form:hidden path="giftList[${status3.index}].showInd" id="SHOWIND${gift.id}" />
 						</tr>	
<%-- 						<c:if test="${giftType!=gift.typeDesc}">  --%>
<!-- 						</table>  -->
<%-- 						</c:if> --%>
						<c:set var="giftType" scope="session" value="${gift.typeDesc}"/>
 						</c:forEach>
 						</div>
 					</td>
 				</tr>
 			</table>
 		</td>
 	</tr>
 	</c:if>
<!--  								gift end  -->
<!-- promotion gift -->
	<c:if test="${basketdetailinfo.promoGiftList!=null}">
	<tr onmouseleave="checkPromoGift()">
		<td colspan="2" width="100%">
			<table width="100%" border="0">
				<tr>
					<td bgcolor="#FFFFFF">
					<div>
					<table class="tableGreytext" width="100%" cellspacing="1" border="0" bgcolor="white"> 
						<tr>  
							<td colspan= "2" class="table_title2" align="left"><spring:message code="ims.pcd.basketdetails.006"/><br>
							</td>
							<td class="table_title2" align="right" width="15%"><spring:message code="ims.pcd.basketdetails.002"/></td> 
 					   		<td class="table_title2" align="right" width="15%"><spring:message code="ims.pcd.basketdetails.003"/></td> 
 						</tr> 
 						<tr>
 						<td  colspan= "2">Please enter the promotion code: <input type="text" maxlength="15" size="35%" placeholder="Please enter the code" id="promoCode" onblur="checkPromoGift()" /></td>
 						<td></td>
 						<td></td>
 						<td></td>
 						</tr>
 						<tr>						
 						<td id="promogiftWarn" class="ui-widget" style="display:none">
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>
 								<div class="promogiftWarnText"></div></p>
 							</div>
						</td> 	          
						</tr>  	
						<c:forEach var="promogift" items="${basketdetailinfo.promoGiftList}" varStatus="status3">	
 						<tr id="${promogift.promoCode}d" class="promgift_detail" style="display:none"> 
 							<td style="display:none" id="cnt_${promogift.promoCode}">${promogift.quota}</td>
 							<td valign="top"  width="10"> 
 								<input type="checkbox" id="promogift_${promogift.promoCode}" class="gift" name="GiftItemBox" value="" onclick="checkVASDtl();" checked disabled> 
 							</td> 
 							<td valign="top"> 
 								<span class="item_title_promgift">${promogift.giftTitle}</span>
 								<div id="VASDtl" style=display:none>
								<br/>
								<span class='item_detail_promgift'>${promogift.giftDetail}</span>
								</div>
								<br/>
 							</td>
 							<td align="right" valign="top" class="BGgreen2" width="15%"> 
 								N/A 
 							</td> 
 							<td align="right" valign="top" class="BGgreen2" width="15%"> 
 								N/A
 							</td> 
 						</tr>	
 					</c:forEach>
 					</table>
					</div>
					</td>
				</tr>
		</table>
		</td>
	</tr>
	</c:if>
<!-- promotion gift end -->
	
	
	
	<tr>
		<td colspan="2" width="100%">
			<table width="100%" border="0">
				<tr>
					<td bgcolor="#FFFFFF">
						<div>
<%	
 	
 	List<VASDetailUI> VASDetailList = BasketDetail.getVasDetailList();
 	String[] SelectedItemList = (String[])request.getSession().getAttribute("selectedIMSVasList");
 	
 	boolean VASFirstItem = true; 
 	String VASTypeFrag = ""; 
 	String VASTypeFragComp = ""; 
 	String checkStr = "";
 	String display = "none";
 	String RecurrentAmt = "";
 	String MthToMthRate = "";
 	VASDetailUI VASItem; 
 	boolean firstBasket = false; 
 	if(VASDetailList!=null&&VASDetailList.size()>0){ 
 		int i=0; 
 		while(i<VASDetailList.size()){ 
 			VASItem = (VASDetailUI)VASDetailList.get(i); 
			if((VASItem.getVASType().equals("IMS_ITYPE_OPT_PREM")&&(Integer.valueOf(VASItem.getRPContractPeriod())>=24))
 					||VASItem.getRPVASContractMatch().equals("0")
 					||(VASItem.getVASType().equals("IMS_ITYPE_MEDIA")&&(Integer.valueOf(VASItem.getRPContractPeriod())>0))
 					|| VASItem.getVASContractPeriod().equals("0")){
 				if((!(VASItem.getVASTypeDtl().equals(VASTypeFrag)))&&i<VASDetailList.size()){
%>
 					<table class="tableGreytext" width="100%" cellspacing="1" border="0" bgcolor="white"> 
						<tr>  
							<td colspan= "2" class="table_title2" align="left"><%=VASItem.getVASTypeDtl()%><br>
							</td>
							<td class="table_title2" align="right" width="15%"><spring:message code="ims.pcd.basketdetails.002"/></td> 
 					   		<td class="table_title2" align="right" width="15%"><spring:message code="ims.pcd.basketdetails.003"/></td> 
 						</tr> 
<%	
					VASTypeFrag = VASItem.getVASTypeDtl();
					VASFirstItem = false;
 				}
 				while((VASItem.getVASTypeDtl().equals(VASTypeFrag))&&i<VASDetailList.size()){ 
  					if(SelectedItemList!=null){
  							checkStr = "";
							display = "none";  
  						for(int l=0; l< SelectedItemList.length; l++){	
  							if(SelectedItemList[l].equals(VASItem.getItemId())){
  								checkStr = "checked";
  								display = "inline";
  								if(VASItem.getItemMdoInd().equals("M")){
  									checkStr = "checked disabled";}
  								break;
  							}
  						}	
  					}else{
							checkStr = "";
							display = "none"; 
  						if(VASItem.getItemMdoInd()!=null ){
  						if(VASItem.getItemMdoInd().equals("M")){
								checkStr = "checked disabled";								
  								display = "inline";
  						}else if(VASItem.getItemMdoInd().equals("D")){
								checkStr = "checked";
  								display = "inline";
  						}else if(VASItem.getItemMdoInd().equals("O")){
  							checkStr = "";
							display = "none"; 
  						}}  
  						
  					}
  					if(VASItem.getRPVASContractMatch().equals("0")
  							||VASItem.getVASType().equals("IMS_ITYPE_OPT_PREM")
  							||VASItem.getVASType().equals("IMS_ITYPE_MEDIA")
  							|| VASItem.getVASContractPeriod().equals("0")){			
 %>     
 	            				<tr> 
 									<td valign="top"  width="10">  
 										<input type="checkbox" class="<%=VASItem.getVASType()%>" id="VAS<%=VASItem.getItemId()%>" name="VASItemBox" value="<%=VASItem.getItemId()%>" onclick="checkVASDtl(<%=VASItem.getItemId()%>)" <%=checkStr%>>
 									</td> 
 									<td valign="top"> 
 										<span class="item_title_vas"><%=VASItem.getVASTitle()%></span>
 										<div id="VASDtl<%=VASItem.getItemId()%>" style=display:<%=display%>>
										<DIR>

						<!-- kinman CSL VAS-->
<% 
						if(VASItem.getVasParmDTO()!= null && "MOBILEPIN".equals(VASItem.getVasParmDTO().getVas_type())){
%>
						<c:set var="i_val" scope="session" value="<%=i%>"/>
						<c:set var="item_id" scope="session" value="<%=VASItem.getItemId()%>"/>
						<div id="vasParm_${item_id}" class="ui-widget">
						<span class='item_detail'><spring:message code="ims.pcd.basketdetails.004"/></span>
						<DIR>
						<span class='item_detail'><%=VASItem.getVasParmDTO().getVas_type_display()%></span>
						
						<br/>
						<span class="item_detail" style="display:inline-block;width:120px"><%=VASItem.getVasParmDTO().getVas_parm_a_display()%></span>
							<form:input id="mRTNumber${item_id}" path="vasDetailList[${i_val}].vasParmDTO.vas_parm_a" maxlength="15"/>		
						<br/>
						<span class="item_detail" style="display:inline-block;width:120px"><%=VASItem.getVasParmDTO().getVas_parm_b_display()%></span>
							
							<form:input id="mRTPin${item_id}" path="vasDetailList[${i_val}].vasParmDTO.vas_parm_b" maxlength="15"/>
			
						</DIR> 
						
						
						<div id="warnVasParm_${item_id}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>
 								<div class="contenttext_red"><form:errors path="vasDetailList[${i_val}].vasParmDTO.vas_parm_a" cssClass="error" /></div></p>
 							</div>
						</div>
						</div>
<%
						}
%>


<% 
						if(VASItem.getImsVasParmDTO()!= null){
%>
						<c:set var="i_val" scope="session" value="<%=i%>"/>
						<c:set var="item_id" scope="session" value="<%=VASItem.getItemId()%>"/>
						<c:set var="parma_format" scope="session" value="<%=VASItem.getImsVasParmDTO().getVas_parm_a_input_format()%>"/>
						<c:set var="parmb_format" scope="session" value="<%=VASItem.getImsVasParmDTO().getVas_parm_b_input_format()%>"/>
						<c:set var="parmc_format" scope="session" value="<%=VASItem.getImsVasParmDTO().getVas_parm_c_input_format()%>"/>
						<div id="imsVasParm_${item_id}" class="ui-widget">
						<DIR>
<%
						if("INPUT".equalsIgnoreCase(VASItem.getImsVasParmDTO().getVas_parm_a_input_method())){
%>
						<span class="item_detail" style="display:inline-block;width:120px"><%=VASItem.getImsVasParmDTO().getVas_parm_a_label()%></span>
<%
						if(VASItem.getImsVasParmDTO().getVas_parm_a_max_length()!=null&&!"".equals(VASItem.getImsVasParmDTO().getVas_parm_a_max_length())){
%>
						<form:input id="imsVasParmA${item_id}" cssClass="${parma_format} imsvasparminput" path="vasDetailList[${i_val}].imsVasParmDTO.vas_parm_a" 
						maxlength="<%=VASItem.getImsVasParmDTO().getVas_parm_a_max_length()%>" onblur="blurOnAttbInput(this)"/>
<%
						}else{
%>
						<form:input id="imsVasParmA${item_id}" cssClass="${parma_format} imsvasparminput" path="vasDetailList[${i_val}].imsVasParmDTO.vas_parm_a" onblur="blurOnAttbInput(this)"/>
<%
						}
%>
						<div id="ParmWarn0${item_id}" class="GiftAttbWarn" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
								<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 					<div class="contenttext" id="ParmErr0${item_id}"></div></p>
				 			</div>
						</div>		
						<div id="error_ParmA${item_id}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p>
				 			<span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 				<div class="contenttext"><form:errors path="vasDetailList[${i_val}].imsVasParmDTO.vas_parm_a" cssClass="error" /></div></p>
				 			</div>
						</div>
						<br/>
<%
						}
%>
<%
						if("INPUT".equalsIgnoreCase(VASItem.getImsVasParmDTO().getVas_parm_b_input_method())){
%>
						<span class="item_detail" style="display:inline-block;width:120px"><%=VASItem.getImsVasParmDTO().getVas_parm_b_label()%></span>
							
<%
						if(VASItem.getImsVasParmDTO().getVas_parm_b_max_length()!=null&&!"".equals(VASItem.getImsVasParmDTO().getVas_parm_b_max_length())){
%>
						<form:input id="imsVasParmB${item_id}" cssClass="${parmb_format} imsvasparminput" path="vasDetailList[${i_val}].imsVasParmDTO.vas_parm_b" 
						maxlength="<%=VASItem.getImsVasParmDTO().getVas_parm_b_max_length()%>" onblur="blurOnAttbInput(this)"/>
<%
						}else{
%>
						<form:input id="imsVasParmB${item_id}" cssClass="${parmb_format} imsvasparminput" path="vasDetailList[${i_val}].imsVasParmDTO.vas_parm_b" onblur="blurOnAttbInput(this)"/>
<%
						}
%>
						<div id="ParmWarn1${item_id}" class="GiftAttbWarn" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
								<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 					<div class="contenttext" id="ParmErr1${item_id}"></div></p>
				 			</div>
						</div>	
						<div id="error_ParmB${item_id}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p>
				 			<span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 				<div class="contenttext"><form:errors path="vasDetailList[${i_val}].imsVasParmDTO.vas_parm_b" cssClass="error" /></div></p>
				 			</div>
						</div>
						<br/>
<%
						}
%>
<%
						if("INPUT".equalsIgnoreCase(VASItem.getImsVasParmDTO().getVas_parm_c_input_method())){
%>
						<span class="item_detail" style="display:inline-block;width:120px"><%=VASItem.getImsVasParmDTO().getVas_parm_c_label()%></span>
						
<%
						if(VASItem.getImsVasParmDTO().getVas_parm_c_max_length()!=null&&!"".equals(VASItem.getImsVasParmDTO().getVas_parm_c_max_length())){
%>
						<form:input id="imsVasParmC${item_id}" cssClass="${parmc_format} imsvasparminput" path="vasDetailList[${i_val}].imsVasParmDTO.vas_parm_c" 
						maxlength="<%=VASItem.getImsVasParmDTO().getVas_parm_c_max_length()%>" onblur="blurOnAttbInput(this)"/>
<%
						}else{
%>
						<form:input id="imsVasParmC${item_id}" cssClass="${parmc_format} imsvasparminput" path="vasDetailList[${i_val}].imsVasParmDTO.vas_parm_c" onblur="blurOnAttbInput(this)"/>
<%
						}
%>
						<div id="ParmWarn2${item_id}" class="GiftAttbWarn" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
								<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 					<div class="contenttext" id="ParmErr2${item_id}"></div></p>
				 			</div>
						</div>	
						<div id="error_ParmC${item_id}" class="ui-widget" style=display:none>
							<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
							<p>
				 			<span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 				<div class="contenttext"><form:errors path="vasDetailList[${i_val}].imsVasParmDTO.vas_parm_c" cssClass="error" /></div></p>
				 			</div>
						</div>
						<br/>
<%
						}
%>
			
						</DIR> 
						</div>
<%
						}
%>



<% 
						if(VASItem.getAttbId()!= null){
%>
											<span class='item_detail'><%=VASItem.getAttbDesc()%></span>
<%
							if(VASItem.getInputMethod().equals("INPUT")){
							
%>
									<c:set var="i_val" scope="session" value="<%=i%>"/>
									<c:set var="item_id" scope="session" value="<%=VASItem.getItemId()%>"/>
									<form:input id="attbInput${item_id}" path="vasDetailList[${i_val}].inputValue" maxlength="8" onblur="checkMobile(${item_id})"/>								
<%
							} 
%>
											<br>
<%
						} 
%>
											<span class='item_detail'><%=VASItem.getVASDetail()%></span>
											<input type="hidden" id="vasAlertMsg<%=VASItem.getItemId()%>" name="vasAlertMsg" value="<%=VASItem.getVASAlertMsg()%>" />
											<input type="hidden" id="vasPreInstAlertMsg<%=VASItem.getItemId()%>" name="vasPreInstAlertMsg" value="<%=VASItem.getVASPreInstAlertMsg()%>" />
										</DIR> 
										</div>
										<div id="AttbWarn<%=VASItem.getItemId()%>" class="ui-widget" style=display:none>
											<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
											<p>
				 							<span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 								<div class="contenttext"><spring:message code="ims.pcd.basketdetails.msg.014"/></div></p>
				 							</div>
										</div>
										<br/>
 									</td> 
 									<td align="right" valign="top" class="BGgreen2" width="15%"> 
 										<%=VASItem.getMthFixText()%> 
 									</td> 
 									<td align="right" valign="top" class="BGgreen2" width="15%"> 
 										<%=VASItem.getMthToMthText()%> 
 									</td> 
 								</tr>											 
 <%			 
  						}
					checkStr = "";
					display = "none";
 					i++; 
 					if(i<VASDetailList.size()){ 
 						VASItem = (VASDetailUI)VASDetailList.get(i); 
 					}		 
 					if(!(VASItem.getVASTypeDtl().equals(VASTypeFrag))){ 
 %> 
 							</table> 
 <%					 
 					}
 				}
		
 			}else{
 				i++;
 			}
 		} 
 	} 
 %>								 
						</div>
					</td>
				</tr>					
			</table>
		</td>
	</tr>	
	</table>
	</td>
	</tr>
	
   <tr>
	<td align="right"> 
	<br>
	<a href="#" class="nextbuttonS" onClick="javascript:formSubmit();"><spring:message code="ims.pcd.basketdetails.005"/>&gt;</a> 
	<input type="hidden" name="currentView" value="customerprofile"/>
	</td>
	
   </tr>
</table>
<div id="overrideOTC_Msg" style="display:none">
<spring:message code="ims.pcd.basketdetails.009"/>
</div>
<form:hidden path="promoGiftCode" id="promoGiftCode" />
<form:hidden id="cslNumPinMissing" path="cslNumPinMissing"/>
</form:form>
  

<%@ include file="/WEB-INF/jsp/footer.jsp" %>