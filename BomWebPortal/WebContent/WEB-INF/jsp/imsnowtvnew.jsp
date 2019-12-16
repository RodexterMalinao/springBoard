<style>
	#profile td{
		text-align: center; 
	}
 
	#col4{
		background-color:#f2f8ff;
		width:90%;
		padding:8px;
	}
	
	#col5{ 
		background-color:#f2f8ff;
		width:90%;
		padding:8px;
	}
 
	.imgcaption {  
		min-height: 160px;
		height:auto;
		position:relative;
		display: -moz-inline-stack;
        display: inline-block;
        vertical-align: top;
        margin: 5px;
        zoom: 1;
        *display: inline; 
	}
 
 .imgcaption a { 
		display: block;
		height:20px;
		position: absolute;
		right: 10px;
		bottom: 10px; 
	}
	.imgcaption a div{ 
		float:right; 
		font-weight: bold;
		font-size: 11px;  
	}
	
	#s_line_text2 {
		overflow: hidden;
		text-align: left;
		font-size: 1.2em;
		font-style: italic;
		font-weight: 600;
		color: #5d9de4;
		margin-bottom: 5px;
	}
	
	.block_content{ 
		margin-bottom:35px;
	}
	
	.block_content span{
		margin-left:10px; 
	}
	
	.block_content span:first-child { 
		font-size: 14pt;
		color: rgb(124, 93, 176); 
		font-weight: bold;
		font-family: Helvetica,Verdana,sans-serif;
	}	
	
	.table_style_sb{
			border-collapse:collapse;
			margin-right:5px;
		}
		.table_style_sb td, .table_style_sb th {
			font-size:1.2em;
			border:1px solid #FFFFFF;
			background-color:#F8F8F8;
			padding:3px 7px 2px 7px;
		}
		
		.tvlist td, .tvlist th {
			font-size:1.1em;
		}
		
		.table_style_sb td.itemPrice {
			background-color:#8dcdf4;
			text-align:center;
			color:#000000;
		}
		.table_style_sb th {
			text-align:center;
			padding-top:2px;
			padding-bottom:1px;
			background-color:#5d9de4 ;
			color:#fff;
		}
		.table_style_sb tr.alt td {
			color:#000;
			background-color:#e8e8e8;
		}
		.table_style_sb tr.alt td.itemPrice {
			background-color:#84c4f0;
			text-align:center;
			color:#000000;
		}
		.itemHtml ul {margin: 0 0 0 0;}
		.item_title_vas, .item_title_rp, .item_title_fee {
			font-family: "Helvetica", "Verdana", "Arial", "sans-serif";
			font-size: 1.2em;
			font-weight: bold;
		}
		.item_title_vas {
			color:#D58200;
		}
		.item_title_rp {
			color:#007d70;
		}
		.item_title_fee {
			color:#7c5db0;
		}
		.item_detail_wrapper{
			margin:20px;
		}
		
		.contenttext {
			font-family: "Verdana", "Arial", "Helvetica", "sans-serif";
			font-size: 12px; 
			color:#FFFFFF;
			
		}
 </style> 

<script>
var selectedBasketSupportHD = "${ImsOrder.selectedBasketSupportHD}";
var pcd4kBoxInd = "${ImsOrder.pcdNowOneBoxInd}" ;
var likeInd = "${ImsOrder.pcdLike100Ind}" ;
var giftWithCampaignSubInd = "${ImsOrder.giftWithCampaignSubInd}" ;
var initalPayPackCount = 0;
var technology = "${ImsOrder.installAddress.addrInventory.technology}";
$( document ).ready(function() {
	initalPayPackCount = $(".payTvPacksCheckbox:checked").length;
	checkHDChild();
	checkDecoderType();
	if($(".AIO_RENTAL_PREM:checked").length==0){
		$("#itemVasTitle_HDD_PREM").hide();
	
		$(".HDD_PREM").attr("checked", false);
		$(".HDD_PREM").attr("disabled", false);
	}else{
		$("#itemVasTitle_HDD_PREM").show();
	
		$(".HDD_PREM").attr("checked", true);
		$(".HDD_PREM").attr("disabled", true);
	}
	
	$("[id^='error_giftAttb']").find('.error').each(function(){
		if ($(this).text() != null) {
			$(this).parent().parent().parent().show();
		}	
	});

});
$(function() {
	var HDAct= "${nowTVdetailinfo.HDAct}";

	initalPayPackCount = $(".payTvPacksCheckbox:checked").length;
	
	
	if ($(".ftaPacks").length > 0  ) {
		if ("${nowtvPageGoneInd}" != "Y"){	
		if ($(".ftaPacks:checked").length == 0) {
		$(".ftaPacks").attr("checked",true);
		$(".hardPacks.mdo_ind-D").attr("checked",true);
		$(".hardPacks.mdo_ind-M").attr("checked",true);
		$(".hardPacksTopUp").attr("checked", true);}}
		//tick D-hardPack and M-hardPack if fta pack selected
		if ($(".ftaPacks:checked").length > 0){
			$(".hardPacks.mdo_ind-D").attr("checked",true);
			$(".hardPacks.mdo_ind-M").attr("checked",true);
			$(".hardPacksTopUp").attr("checked", true);
		}
		if (HDAct == "Y"){
//			$(".NTV_HD").attr("checked", true);
			checkHDChild();
			checkDecoderType();
		}
		if (pcd4kBoxInd == "Y"){
			$(".AIO_RENTAL").attr("checked", false);
			$(".AIO_SUBOWN").attr("checked", false);
			$(".NTV_HD").attr("checked", false);
		}
		if ($(".payTvPacksCheckbox:checked").length > 0) {
			$(".payTvPacksCheckbox:checked").first().trigger("change");
			if("${ImsOrder.isPT}"=="Y"){
			if($(".AIO_RENTAL_PREM").is(':visible')){
				$("#itemVasTitle_HDD_PREM").show();
				$(".AIO_RENTAL_PREM").attr("checked", true);
				$(".AIO_RENTAL_PREM").attr("disabled", true);
				$(".HDD_PREM").attr("checked", true);
				$(".HDD_PREM").attr("disabled", true);
				$(".NTV_SD").attr("checked", false);
				$('.NTV_SD').parent('td').parent('tr').css('display','none');
			}else{
				if(pcd4kBoxInd != "Y"){
					$(".NTV_SD").attr("checked", true);
					$(".NTV_SD").attr("disabled", true);
				}
			}
			}
		} else {
			$(".payTvPacksCheckbox").first().trigger("change");
			if("${ImsOrder.isPT}"=="Y"){
			if($(".AIO_RENTAL_PREM").is(':visible')){
				$('.NTV_SD').parent('td').parent('tr').css('display','');
				$(".AIO_RENTAL_PREM").attr("disabled", false);
			}else{
				$(".NTV_SD").attr("disabled", false);
			}
			}
		}
	}else{
		$("[name='selectedVas']").attr("checked",false);		
	}
	//if ("${ImsOrder.adultViewAllow}" == '' ){
		
	if ("${nowtvPageGoneInd}" != "Y"){
	if ($(".hardPacks:checked") != null && $(".hardPacks:checked").length == 0 ) {
		$(".hardPacks.mdo_ind-D").attr("checked",true);
		$(".hardPacks.mdo_ind-M").attr("checked",true);
		$(".hardPacksTopUp").attr("checked", true);
	}
	}
	$(".hardPacks").click(function(e) {
		if (e.target.checked) {
			$(".hardPackstopUp").attr("checked", true);
		} else {
			if ($(e.target).is(".mdo_ind-M")) {
				e.target.checked = true;
			}
		}
		
	});
	
	$(".payTvPacksCheckbox").focus(function(e) {
		initalPayPackCount = $(".payTvPacksCheckbox:checked").length;
	});

	
	$(".NTV_HD,.NTV_SD").change(function(e){
		if(e.target.checked){
			$(".NTV_HD,.NTV_SD").not($(e.target)).attr("checked",false);  
		}
		checkHDChild();
 		checkDecoderType();
	});

	$(".AIO_SUBOWN,.AIO_RENTAL_PREM").change(function(e){
		if(e.target.checked){
			$(".AIO_SUBOWN,.AIO_RENTAL_PREM").not($(e.target)).attr("checked",false);  
		}
		if($(".AIO_RENTAL_PREM:checked").length==0){
			$("#itemVasTitle_HDD_PREM").hide();
		
			$(".HDD_PREM").attr("checked", false);
			$(".HDD_PREM").attr("disabled", false);
		}else{
			$("#itemVasTitle_HDD_PREM").show();
		
			$(".HDD_PREM").attr("checked", true);
			$(".HDD_PREM").attr("disabled", true);
		}			
	});
	
	$(".NTV_HD,.NTV_SD,.AIO_RENTAL_PREM").change(function(e){
		if(e.target.checked){
			$(".NTV_HD,.NTV_SD,.AIO_RENTAL_PREM").not($(e.target)).attr("checked",false);  
		}
		if($(".AIO_RENTAL_PREM:checked").length==0){
			$("#itemVasTitle_HDD_PREM").hide();
		
			$(".HDD_PREM").attr("checked", false);
			$(".HDD_PREM").attr("disabled", false);
		}else{
			$("#itemVasTitle_HDD_PREM").show();
		
			$(".HDD_PREM").attr("checked", true);
			$(".HDD_PREM").attr("disabled", true);
		}
		checkDecoderType();
	});

	$(".AIO_SUBOWN,.AIO_RENTAL,.AIO_RENTAL_PREM").change(function(e){
		if($(".AIO_SUBOWN").length>0 || $(".AIO_RENTAL").length>0 || $(".AIO_RENTAL_PREM").length>0) {
			$(".HDChild").show();
		}
		else {
			$(".HDChild").hide();
		}
		
		checkAddFormTvType();
 		checkDecoderType();
	});
	
	
	var selectedVas = "${nowTVdetailinfo.selectedVas}";
	var ntvOtherList = "${nowTVdetailinfo.nowTVOtherList}";
	
	for (i=0;i<ntvOtherList;i++){
		if(ntvOtherList[i]==selectedVas[0]){
			$("[name='selectedVas']").attr("checked",true);	
		}
		
	}

}); 

function checkHDChild(){	
	
	if($(".NTV_HD:checked").length>0 ) {
		$("#HDAct").val("Y");
		$(".HDChild").show();
		$(".SDOffers").attr("disabled", true);
		$(".SDOffers").attr("checked", false);
	}
	else {
		$("#HDAct").val("N");
		$(".HDChild").hide();
		$(".SDOffers").attr("disabled", false); 
	}
	
	checkAddFormTvType();
}

function checkDecoderType(){
	
	if(pcd4kBoxInd == "Y"  || $(".AIO_RENTAL:checked").length>0 || $(".AIO_SUBOWN:checked").length>0 || $(".AIO_RENTAL_PREM:checked").length>0){
		if(pcd4kBoxInd == "Y")
			parm = "AIO_PUR_FS";
		else if($(".AIO_RENTAL:checked").length>0 )
			parm = "AIO_RENTAL";
		else if ($(".AIO_RENTAL_PREM:checked").length>0)
			parm = "AIO_RENTAL_PREM";
		else
			parm = "AIO_SUBOWN";
		$.ajax({
			url : 'checkdecodertype.html?decoderStr=' + parm +'&nowOneBox=Y',
			type : 'POST',
			dataType : 'json',
			timeout : 50000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please re-login."); 
// 			    	alert('<spring:message code="ims.pcd.nowtv.msg.011"/>');
			    }
			},
			success : function(msg) {
				var count=0;
				$.each(eval(msg), function(i, item) {
					$("#decodeType").val(parm);
 					document.getElementById('decodeTypeID').innerHTML = item.decoderType; 					
				});				
				
			}
	});
		
	}else{
	

	var housingType = "${ImsOrder.installAddress.housingTypeList[0].housingType}";
	var newTVPrice = "${nowTVdetailinfo.ntvPricingInd}";

	var tvList= "${nowTVdetailinfo.nowTVFormType}"; 
	
		var viHos = 'MASS';
		
		if(housingType.toUpperCase().indexOf("PT")>-1){
			viHos="PT";
		}else if(housingType.toUpperCase().indexOf("HOS")>-1||housingType.toUpperCase().indexOf("PH")>-1){
			viHos="PH";
		}	
		var hdPurchased;
		if($(".NTV_HD:checked").length>0 ) {
			hdPurchased = "Y";
			//canSubscrubeHD = "Y"; //tmp
		}
		else hdPurchased = "N";
		
		
		var parm="";

		if(newTVPrice.toUpperCase().indexOf("Y")>-1){	
			parm = tvList+"|"+viHos+"|"+hdPurchased+"|"+selectedBasketSupportHD; 			
		}
		$.ajax({
				url : 'checkdecodertype.html?decoderStr=' + parm+ '&serbdyno=' + '${nowTVdetailinfo.serbdyno}',
				type : 'POST',
				dataType : 'json',
				timeout : 50000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				    if(textStatus=='parsererror'){
// 				        alert("Your session has been timed out, please re-login."); 
				        alert("Your session has been timed out, please re-login."); 
				    }
				},
				success : function(msg) {
					var count=0;
					$.each(eval(msg), function(i, item) {
						$("#decodeType").val(item.decoderType);
	 					document.getElementById('decodeTypeID').innerHTML = item.decoderType; 					
					});				
					
				}
		});
		
	}
}
	
function checkAddFormTvType() {
	var bandwidth = $("#modelBandwidth");
	var connType = 0;
	var HDchecked = $(".NTV_HD:checked").length;
	var payTvCampaign = $(".payTvCampaign");
	var nowOneBoxPurchased = "N";
	
	if(pcd4kBoxInd == "Y" || $(".AIO_RENTAL:checked").length>0 || $(".AIO_SUBOWN:checked").length>0 || $(".AIO_RENTAL_PREM:checked").length>0){
		nowOneBoxPurchased = "Y";
	}
	
	if (bandwidth != null && bandwidth.length > 0) {
		bandwidth = parseFloat(bandwidth.val());
			
			if (nowOneBoxPurchased == "Y"){
				connType = 7; // SHD
			} else if (HDchecked > 0) {
				connType = 5; // HD
			} else if (bandwidth > 18) {
				connType = 3; // HDR
			}
			else
				connType = 0;
		
		//console.log(initalPayPackCount);
		$("[class*=AddForm_rr]").hide();
		for (var i = 0; i <= connType; i++) {
			$(".AddForm_rr" + i).show();
		}
		for (var i = 0; i < payTvCampaign.length; i++) {
			checkCampaignTvType(payTvCampaign[i], "payTvPacks", connType);
		}
		$(".payTvPacksCheckbox:checked").first().trigger("change");
	}
}

function checkCampaignTvType(campaign, pack, connType) {
	var packCounts = $(campaign).find("." + pack);
	var hiddenPack = $(campaign).find("." + pack + ":hidden");
	
	$(hiddenPack).each(function() {
		$(this).find(".payTvPacksCheckbox").attr("checked", false);
	});
	$(packCounts).each(function() {
		var totalChannel = $(this).find(".packChannels");
		var hiddenChannel = $(this).find(".packChannels:hidden");
		if (totalChannel.length > 0 && hiddenChannel.length > 0) {
			if (totalChannel.length == hiddenChannel.length) {
				$(this).hide();
			}
		}
	});
	
	if (packCounts.length > 0) {
		if (hiddenPack.length > 0) {
			if (packCounts.length == hiddenPack.length) {
				$(campaign).hide();
				if ($(campaign).find(".payTvPacksCheckbox:checked").length > 0) 
					$(campaign).find(".payTvPacksCheckbox:checked").attr("checked", false);
			}
		}
	}
}
function changeFTAPack(object){
 	if(!object.checked){ 
	 	if(confirm("<spring:message code="ims.pcd.nowtv.msg.001"/>") == true) {				
 			object.checked=false;
 			$(".hardPacks").attr("checked",false);
 			$(".hardPacksTopUp").attr("checked", false);
 			$(".payTvPacksCheckbox").attr("checked", false);
 		    $(".topUpOffer").attr("checked", false);
			$(".payTvPacksCheckbox").first().trigger("change");
 			$(".ntvother").attr("checked", false);
 			$("#AVSelect").attr("checked", false);
//  			$("[class*=AddForm_rr]").hide();
 			
	   }else{
	 			object.checked=true;
	 		}
 	}else{
 		object.checked=true;
			$(".hardPacks.mdo_ind-D").attr("checked",true);
 			$(".hardPacks.mdo_ind-M").attr("checked",true);
 			$(".hardPacksTopUp").attr("checked", true);
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
 
function formSubmit(){

	if($(".ftaPacks:checked").length == 0 && ($(".hardPacks:checked").length > 0 || $(".payTvPacksCheckbox:checked").length > 0)){
		alert("<spring:message code="ims.pcd.nowtv.msg.012"/>");
		$(".ftaPacks").attr("checked",true);
		$(".hardPacks.mdo_ind-D").attr("checked",true);
		$(".hardPacks.mdo_ind-M").attr("checked",true);
		return;
	}
	if (pcd4kBoxInd == "Y" && $(".ftaPacks:checked").length == 0 ){
// 		alert("As Now One TV Box selected, no Now TV service selected. Please select Now TV service.");
		alert("<spring:message code="ims.pcd.nowtv.msg.002"/>");
		return;
	}
	if (giftWithCampaignSubInd == "Y" && $(".ftaPacks:checked").length == 0 ){
		alert("Please select Now TV service.");
		return;
	}
	if ($(".ftaPacks:checked").length > 0 && likeInd == "Y" && $(".payTvPacksCheckbox:checked").length == 0 ){
// 		alert("Please select at least one NTV pack.");
		alert("<spring:message code="ims.pcd.nowtv.msg.003"/>");
		return;
	}
	if(!checkAllAttb()){
		return;
	}
	if(validateForm())  {
		$("input").attr("disabled",false);
		$("select").attr("disabled",false);
		showLoading(); 
		document.nowTVForm.submit();
	}
	
	
}

function validateForm(){
	$(".ui-widget").hide(); 
	var err = 0;
	///hd
	if((selectedBasketSupportHD != "Y" && ($(".NTV_HD:checked").length>0 ))){  
		err++;
		$("#warning_hdconn").show(); 
		$("#warning_hdconn_msg").text("<spring:message code="ims.pcd.nowtv.msg.004"/>"); 

	}
	//hd(end)
	////adult channel
	if($("[name='AVSelect']:checked").length >0){ 
		$("#isAdult").val("Y");
	}else
		$("#isAdult").val("N");
	//adult channel(end) 
	
	if(err == 0) return true;
	else window.scrollTo(0,0); 
}
function payBundleChange(i, min, num, elem) {
	var payCampaign = $(".payTvCampaign");
	var payPacksCount = 0;
	var mdo = "O";
	var showAll = false;
	
	if ($(elem).is(".payTvPacksCheckbox.mdo_ind-M")) {
		if (elem.checked == false) {
			if (confirm("<spring:message code="ims.pcd.nowtv.msg.005"/>") == true) {
				showAll = true;
			} else {
				elem.checked = true;
			}
		}else{
			if ($(payCampaign[i]).find(".payTvPacksCheckbox.mdo_ind-M").length > 0) {
				$(payCampaign[i]).find(".payTvPacksCheckbox.mdo_ind-M").attr("checked", true);
			}
		}
		mdo = "M";
	}
	if ($(elem).is(".payTvPacksCheckbox.mdo_ind-D")) {
		mdo = "D";
	}
	
	if ($(payCampaign[i]).length) {
		payPacksCount = $(payCampaign[i]).find(".payTvPacksCheckbox:checked").length;
		if (payPacksCount == 0 || showAll == true) {
			$("#payTvAlert").empty();
			$(".payTvPacksCheckbox").removeAttr("checked");
			$(payCampaign).each(function() {
				$(this).show();
				var packCounts = $(this).find(".payTvPacksCheckbox").length;
				var hiddenPack = $(this).find(".payTvPacksCheckbox:hidden").length;
				if (packCounts > 0 && packCounts == hiddenPack) {
					$(this).hide();
				}
				$(this).find(".topUpOffer").removeAttr("checked");
				$(payCampaign[i]).find(".topUpOffer").attr("disabled", true);
			});
			if("${ImsOrder.isPT}"=="Y"){
			if($(".AIO_RENTAL_PREM").is(':visible')){
				

// 			$("#itemVasTitle_NTV_SD").show();
				$('.NTV_SD').parent('td').parent('tr').css('display','');
				$(".AIO_RENTAL_PREM").attr("disabled", false);
				$(".AIO_SUBOWN").attr("disabled", false);
			}else{
				
				if(pcd4kBoxInd != "Y"){
					$(".NTV_SD").attr("disabled", false);
				}
			}
			}
		} else {
			var numOfCheckBox = $(payCampaign[i]).find(".payTvPacksCheckbox").length;
			var numOfHiddenCheckBox = $(payCampaign[i]).find(".payTvPacksCheckbox:hidden").length;
			
			$("#payTvAlert").html("<spring:message code="ims.pcd.nowtv.006"/>" + min + "<spring:message code="ims.pcd.nowtv.007"/>" + (numOfCheckBox - numOfHiddenCheckBox) + ")");
			$(payCampaign).not(payCampaign[i]).hide(); // hide other campaign
			$(payCampaign).not(payCampaign[i]).find(".payTvPacksCheckbox").removeAttr("checked"); // remove pack's tick from other campaign
			if (elem != null && elem.checked == true) {
				$(payCampaign[i]).find(".topUpOffer").attr("checked", true);
				$(payCampaign[i]).find(".topUpOffer").attr("disabled", false);
				if (initalPayPackCount == 0) {
					if ($(payCampaign[i]).find(".payTvPacksCheckbox.mdo_ind-D").length > 0) {
						$(payCampaign[i]).find(".payTvPacksCheckbox.mdo_ind-D").attr("checked", true);
					}
					if ($(payCampaign[i]).find(".payTvPacksCheckbox.mdo_ind-M").length > 0) {
						$(payCampaign[i]).find(".payTvPacksCheckbox.mdo_ind-M").attr("checked", true);
					}
				}
			}
			if("${ImsOrder.isPT}"=="Y"){
			if($(".AIO_RENTAL_PREM").is(':visible')){
				$("#itemVasTitle_HDD_PREM").show();
				$(".AIO_RENTAL_PREM").attr("checked", true);
				$(".AIO_RENTAL_PREM").attr("disabled", true);
				$(".AIO_SUBOWN").attr("checked", false);
				$(".AIO_SUBOWN").attr("disabled", true);
				$(".HDD_PREM").attr("checked", true);
				$(".HDD_PREM").attr("disabled", true);
				$(".NTV_SD").attr("checked", false);
// 				$("#itemVasTitle_NTV_SD").hide();
				$('.NTV_SD').parent('td').parent('tr').css('display','none');
			}else{
				
				if(pcd4kBoxInd != "Y"){
					$(".NTV_SD").attr("checked", true);
					$(".NTV_SD").attr("disabled", true);
				}
			}
			}
		}
		checkDecoderType();
	}

}
function selectedVasCheck(object){
	if (object.checked){
		if (pcd4kBoxInd == "Y"){
			$(".AIO_RENTAL").attr("checked", false);
			$(".AIO_SUBOWN").attr("checked", false);
			if(technology == "PON"){
				$(".NTV_HD").attr("checked", false);
			}
		}
		if ($("[name='selectedVas']:checked").length>1){
			if($(".NTV_OTHER:checked").length>1 ){
			document.getElementById('warning_OtherVAS').style.display = "inline";
 			document.getElementById('warning_OtherVAS_msg').innerHTML ="<spring:message code="ims.pcd.nowtv.msg.006"/>";	
 			object.checked=false;}
			else if ($(".AIO_RENTAL:checked").length>0 && $(".AIO_SUBOWN:checked").length>0){
				document.getElementById('warning_OtherVAS').style.display = "inline";
	 			document.getElementById('warning_OtherVAS_msg').innerHTML ="<spring:message code="ims.pcd.nowtv.msg.007"/>";	
	 			object.checked=false;}
			else if (technology == "PON" &&( $(".NTV_HD:checked").length>0||$(".NTV_SD:checked").length>0) && (pcd4kBoxInd == "Y" || $(".AIO_RENTAL:checked").length>0 || $(".AIO_SUBOWN:checked").length>0)){
				document.getElementById('warning_OtherVAS').style.display = "inline";
	 			document.getElementById('warning_OtherVAS_msg').innerHTML ="<spring:message code="ims.pcd.nowtv.msg.008"/>";	
	 			object.checked=false;}				
		}else if(technology == "PON" && pcd4kBoxInd == "Y" && ($(object).hasClass('NTV_HD')||$(object).hasClass('NTV_SD'))){
				document.getElementById('warning_OtherVAS').style.display = "inline";
	 			document.getElementById('warning_OtherVAS_msg').innerHTML ="<spring:message code="ims.pcd.nowtv.msg.009"/>";	
	 			object.checked=false;
		}else{			
			document.getElementById('warning_OtherVAS').style.display = "none";
			document.getElementById('warning_OtherVAS_msg').innerHTML = "";
		}
		if ($(".AIO_RENTAL:checked").length>0 || $(".AIO_SUBOWN:checked").length>0 ||$(".AIO_RENTAL_PREM:checked").length>0){
			checkDecoderType();
		}
		if($(".ftaPacks:checked").length == 0){
			object.checked=false;
			document.getElementById('warning_OtherVAS').style.display = "none";
			document.getElementById('warning_OtherVAS_msg').innerHTML = "";
		}
	
		if($(".AIO_RENTAL_PREM:checked").length>0){
			
			$("#itemVasTitle_HDD_PREM").show();
			$(".HDD_PREM").attr("checked", true);
			$(".HDD_PREM").attr("disabled", true);
		}	
	
	}
	else{ 
	 	if(confirm("<spring:message code="ims.pcd.nowtv.msg.010"/>")){
 			object.checked=false;
 			checkDecoderType();
			document.getElementById('warning_OtherVAS').style.display = "none";
			document.getElementById('warning_OtherVAS_msg').innerHTML = "";
			
			if($(".AIO_RENTAL_PREM:checked").length==0){				
				$("#itemVasTitle_HDD_PREM").hide();
				$(".HDD_PREM").attr("checked", false);
				$(".HDD_PREM").attr("disabled", false);
			}		
	 	}else{
	 		object.checked=true;
	 	}
	 	$(object).trigger("change"); 
 	}
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
//		if(document.getElementById("attbInput"+ItemID)!=null){
//			mobileNum = document.getElementById("attbInput"+ItemID).value;
//		}
	 
//		 $('.gift').each(function() {
//			 if($(this).is(':checked')){
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
	
//		 $('.giftAttb').each(function() {
//			 if($(this).parents('tbody').find('.gift').attr('checked')) {
//				 $(this).removeClass('hide');
//				 if($(this).find('.giftremark').hasClass('ASIA_MILE_ID')) {
//					 $(this).parents('tbody').find('.amnote').removeClass('hide');
//				 }
//			} 
//		 });  
}
 
function checkAllAttb()
{
	var addGiftCheckboxGroup=document.forms["nowTVForm"].PcdNtvGiftItemBox;
	var i = 0;
	var ItemID = "";
	error = 0;
	valid = "Y";
	var parmResult;
	
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

</script>
<table width="100%" border="0" cellspacing="1" rules="none">
	<tr><td>
	<div class="ui-widget" id="warning_starter" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_starter_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	<div class="ui-widget" id="warning_VAS" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_VAS_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>	
	<div class="ui-widget" id="warning_OtherVAS" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_OtherVAS_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	<div class="ui-widget" id="warning_HD" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_HD_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	<div class="ui-widget" id="warning_HD_Ready" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_HD_Ready_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	<div class="ui-widget" id="warning_STB" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_STB_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	<div class="ui-widget" id="warning_Ent" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_Ent_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	<div class="ui-widget" id="warning_FreeHD" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_FreeHD_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	<div class="ui-widget" id="warning_channel" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_channel_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	<div class="ui-widget" id="warning_HDchannel" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_HDchannel_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	<div class="ui-widget" id="warning_adult" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_adult_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	
	<div class="ui-widget" id="warning_hdconn" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_hdconn_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div> 
	
	<div class="ui-widget" id="warning_shdconn" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_shdconn_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	
	<div class="ui-widget" id="warning_h264" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_h264_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
	
	<div class="ui-widget" id="warning_ftaPack" style="display:none">
		<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0.1em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<div id="warning_ftaPack_msg" style="font-weight:bold;"></div>
			<p></p>
		</div>
	</div>
</table>
<form:form name="nowTVForm" commandName="nowTVdetailinfo" method="POST">
<input type="hidden" name="IMS_UID" value="<%= request.getAttribute("IMS_UID") %>" />

<%@ page import="			
					org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.ims.dto.ui.NowTVUI,
					com.bomwebportal.ims.dto.ui.ChannelUI,
					com.bomwebportal.ims.dto.ui.NowTVVasUI,
					java.util.*
					"
%>
<%
	String ContractPeriod = (String)request.getSession().getAttribute("IMS_ContractPeriod");
	NowTVUI NowTVDetail = (NowTVUI)request.getAttribute("nowTVdetailinfo");
	String[] SelectedChannelList = (String[])request.getSession().getAttribute("selectedIMSChannelList");
	String[] SelectedItemList = (String[])request.getSession().getAttribute("selectedIMSNowVasList");
	String checkStr = "";
	String disableStr="";
	String TVList = (String)request.getParameter("TVList");
%>

<br><br>
	 <spring:bind path="subscribedItems">
		 <c:if test="${not empty status.errorMessages}">
			<div class="ui-widget"> 
	 			<div class="ui-state-error ui-corner-all" style="padding: 0.1em;"> 
				<p>
				<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
				<div  style="font-weight:bold;">
	 				<c:forEach items="${status.errorMessages}" var="error">
	        			<c:out value="${error}" escapeXml="false"/><br>
	        		</c:forEach>
				</div>
				</p>
				</div> 
			</div>
			<br>
		</c:if> 
	</spring:bind>
<table width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="1" rules="none">	
		<tr>
			<td class="table_title" ><span class="now">now </span>TV</td>
		</tr>
		</table>
		
		
		<!-- new -->
		<table width="97%" class="table_style_sb">
		<tbody>
			<tr>
				<td bgcolor="#FFFFFF">
					<table class="tableGreytext" width="100%" cellspacing="1" border="0" bgcolor="white">
							<colgroup style="width: 10px;"></colgroup>
							<colgroup></colgroup>
							<colgroup style="width: 120px; text-align: middle; vertical-align: top"></colgroup>
							<colgroup style="width: 120px; text-align: middle; vertical-align: top"></colgroup>
						<tbody>
							<!-- FTA Campaign Pack -->
							<tr>
								<th style="text-align: left;" colspan="2">
<%-- 								${nowTVdetailinfo.nowTVAddUI.ftaCampaign.title} --%>
								<spring:message code="ims.pcd.nowtv.009"/>
								</th>
								<c:if test="${nowTVdetailinfo.contractPeriod ne '0'}"><th colspan="2"><spring:message code="ims.pcd.nowtv.003"/></th></c:if>
								<th colspan="1"><spring:message code="ims.pcd.nowtv.004"/></th>
							</tr>
							<c:forEach var="ftaPack" items="${nowTVdetailinfo.nowTVAddUI.ftaCampaign.tvPacks}" varStatus="i">
								<tr class="AddForm_rr${ftaPack.readRight} ftaCampagin">
									<td valign="top" width="10" class="itemHtml">
										<form:checkbox path="nowTVAddUI.ftaCampaign.tvPacks[${i.index}].selected" cssClass="ftaPacks" onchange="changeFTAPack(this)"/>
									</td>
									<td colspan="1" valign="top">
										<span class="item_title_vas">${ftaPack.title}</span><br>
										${ftaPack.displayDtails}
									</td>
									<c:if test="${nowTVdetailinfo.contractPeriod ne '0'}"><td colspan="2" align="right" valign="top" class="itemPrice" width="10%">${nowTVdetailinfo.nowTVAddUI.ftaCampaign.fix_term_rate}</td></c:if>
									<td  align="right" valign="top" class="itemPrice" width="10%">${nowTVdetailinfo.nowTVAddUI.ftaCampaign.mth_to_mth_rate}</td>
								</tr>
							</c:forEach>
						<!-- FTA Campaign Pack (end)-->
						
						<!-- Hard Bundle Campaign Pack -->
						<c:if test="${not empty nowTVdetailinfo.nowTVAddUI.hardCampaign}">
							<tr>
								<th colspan="5" style="text-align: left;"><spring:message code="ims.pcd.nowtv.010"/>
<%-- 								${nowTVdetailinfo.nowTVAddUI.hardCampaign.title} --%>
								<c:if test="${lang == 'EN'}">
								<spring:message code="ims.pcd.nowtv.006"/> ${nowTVdetailinfo.nowTVAddUI.hardCampaign.min_select_cnt} <spring:message code="ims.pcd.nowtv.007"/> ${nowTVdetailinfo.nowTVAddUI.hardCampaign.packSize}) <c:if test="${nowTVdetailinfo.contractPeriod ne '0'}">(${nowTVdetailinfo.contractPeriod} <spring:message code="ims.pcd.nowtv.008"/>)</c:if>								</th>
								</c:if>
								<c:if test="${lang == 'ZH_HK'}">
								<spring:message code="ims.pcd.nowtv.006"/>${nowTVdetailinfo.nowTVAddUI.hardCampaign.packSize}<spring:message code="ims.pcd.nowtv.007"/>${nowTVdetailinfo.nowTVAddUI.hardCampaign.min_select_cnt}) <c:if test="${nowTVdetailinfo.contractPeriod ne '0'}">(${nowTVdetailinfo.contractPeriod} <spring:message code="ims.pcd.nowtv.008"/>)</c:if>								</th>
								</c:if>
								</th>
							</tr>
							<c:forEach var="hardPack" items="${nowTVdetailinfo.nowTVAddUI.hardCampaign.tvPacks}" varStatus="i">
								<tr class="AddForm_rr${hardPack.readRight} hardCampaign<c:if test="${i.index%2 eq 1}"> alt</c:if>" >
									<td valign="top" width="10" class="itemHtml">
										<form:checkbox path="nowTVAddUI.hardCampaign.tvPacks[${i.index}].selected" cssClass="hardPacks mdo_ind-${hardPack.mdo_ind}"/>
									</td>
									<td colspan="4" valign="top">
										${hardPack.displayDtails}
									</td>
								</tr>
							</c:forEach>
							<tr><td></td></tr>
							<c:forEach var="topUp" items="${nowTVdetailinfo.nowTVAddUI.hardCampaign.topUps}" varStatus="i">
								<c:if test="${i.index eq 0}">
								<tr style="color:#0099ff"><td colspan="5">
<!-- 								Gift &amp; Premium: -->
								<spring:message code="ims.pcd.nowtv.011"/>
								</td></tr>
								</c:if>
								<tr style="color:#0099ff">
									<td valign="top" width="10" class="itemHtml">
										<form:checkbox path="nowTVAddUI.hardCampaign.topUps[${i.index}].selected" cssClass="hardPacksTopUp"/>
									</td>
									<td colspan="4" valign="top">
										${topUp.detail}
									</td>
								</tr>
							</c:forEach>
							</c:if>
						<!-- Hard Bundle Campaign Pack (end)-->	
						</tbody>
					</table>
					
					<table class="tableGreytext" width="100%" cellspacing="1" border="0" bgcolor="white">
					<tr>
        	<td align="left" width="25%"><spring:message code="ims.pcd.nowtv.005"/> <span id="decodeTypeID">${nowTVdetailinfo.decodeType}</span></td>
			
			<form:hidden path="decodeType" />
			<td align="left">
			<form:hidden path="isAdult" />
			<c:if test="${nowTVdetailinfo.isAdult == 'Y' }">
				<input type="checkbox" id="AVSelect" name="AVSelect" value="AVSelect" checked/>
			</c:if>
			<c:if test="${nowTVdetailinfo.isAdult == 'N' }">
				<input type="checkbox" id="AVSelect" name="AVSelect" value="AVSelect"/>
			</c:if>
				<spring:message code="ims.pcd.nowtv.002"/></td>
				
		</tr>
		<tr>
			<td></td><td colspan='3'><div id="warning_AVchannel">
			
			</div></td>
		</tr>
<!-- 						<tr> -->
<%-- 							<td align="left" width="25%">Decoder : <span id="decodeTypeID">${nowTVdetailinfo.decodeType}</span></td> --%>
<!-- 							<input id="decodeType" name="decodeType" type="hidden" value="HD"> -->
<!-- 							<form:hidden path="decodeType"/> -->
<!-- 							<td align="left"> -->
<!-- 								<form:checkbox path="isAdult"  value ="Y" label="I wish to view AV/adult channel preview" /> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td></td> -->
<!-- 							<td colspan="3"> -->
<!-- 								<div id="warning_AVchannel"></div>  -->
<!-- 							</td> -->
<!-- 						</tr> -->
 					</table>
 						<table class="tableGreytext" width="100%" cellspacing="1" border="0" bgcolor="white">
							<colgroup style="width: 10px;"></colgroup>
							<colgroup></colgroup>
							<colgroup style="width: 120px; text-align: middle; vertical-align: top"></colgroup>
							<colgroup style="width: 120px; text-align: middle; vertical-align: top"></colgroup>
						<tbody>
						<!-- Pay Bundle Campaign Pack -->
							<tr>
								<th colspan="2" style="text-align: left;">
								<c:if test="${not empty nowTVdetailinfo.nowTVAddUI.payTvCampaign}">
<%-- 								${nowTVdetailinfo.nowTVAddUI.payTvCampaign[0].title}  --%>
									<spring:message code="ims.pcd.nowtv.msg.013"/>
								</c:if>
								<span id="payTvAlert"></span>
								</th>
								<c:if test="${nowTVdetailinfo.contractPeriod ne '0'}"><th colspan="2"><spring:message code="ims.pcd.nowtv.003"/></th></c:if>
								<th colspan="1"><spring:message code="ims.pcd.nowtv.004"/></th>
							</tr>
							<c:forEach var="payTv" items="${nowTVdetailinfo.nowTVAddUI.payTvCampaign}" varStatus="i">
								<tr class="AddForm_rr${payTv.readRight} payTvCampaign<c:if test="${i.index%2 eq 1}"> alt</c:if>">
									<td valign="top" width="10" class="itemHtml"></td>
									<td colspan="1" valign="top">
										<c:forEach var="pack" items="${payTv.tvPacks}" varStatus="j">
											<div class="payTvPacks AddForm_rr${pack.readRight}">
											<form:checkbox path="nowTVAddUI.payTvCampaign[${i.index}].tvPacks[${j.index}].selected" cssClass="payTvPacksCheckbox mdo_ind-${pack.mdo_ind} " onchange="payBundleChange(${i.index}, ${payTv.min_select_cnt}, ${payTv.packSize}, this)"/>
											<span style="text-decoration:underline;">${pack.title}</span>
											<table style="width:100%;">
												<tr>
													<td style="width:50%;border-color:transparent;" valign="top">
														<table style="width:100%;">
														<c:forEach var="channel" items="${pack.tvChannels}" varStatus="k">
														 <c:if test="${k.index <= fn:length(pack.tvChannels)/2}">
															<tr class="AddForm_rr${channel.readRight} packChannels">
																<td style="width:60px;border-color:transparent;"><span class="item_detail" style="margin-left:0px;text-align:left;<c:if test="${channel.tvType eq 'HD' or channel.tvType eq 'SHD'}">color:#01A9DB</c:if>">${channel.channelId}</span></td>
																<td style="border-color:transparent;"><span class="item_detail" style="margin-left:0px;text-align:left;<c:if test="${channel.tvType eq 'HD' or channel.tvType eq 'SHD'}">color:#01A9DB</c:if>">${channel.channelDisplayDetail}</span></td>
															</tr>
															</c:if>
														</c:forEach>
														</table>
													</td>
													<td style="width:50%;border-color:transparent;" valign="top">
														<table style="width:100%;border:none;">
														<c:forEach var="channel" items="${pack.tvChannels}" varStatus="k">
															<c:if test="${k.index > fn:length(pack.tvChannels)/2}">
															<tr class="AddForm_rr${channel.readRight} packChannels">
																<td style="width:60px;border-color:transparent;"><span class="item_detail" style="margin-left:0px;text-align:left;<c:if test="${channel.tvType eq 'HD' or channel.tvType eq 'SHD'}">color:#01A9DB</c:if>">${channel.channelId}</span></td>
																<td style="border-color:transparent;"><span class="item_detail" style="margin-left:0px;text-align:left;<c:if test="${channel.tvType eq 'HD' or channel.tvType eq 'SHD'}">color:#01A9DB</c:if>">${channel.channelDisplayDetail}</span></td>
															</tr>
															</c:if>
														</c:forEach>
														</table>
													</td>
												</tr>
											</table>
											</div>
										</c:forEach>
										<c:forEach var="topUp" items="${payTv.topUps}" varStatus="k">
											<div style="color:#0099ff">
												<c:if test="${k.index eq 0}">
<!-- 												Gift &amp; Premium: -->
												<spring:message code="ims.pcd.nowtv.011"/>
												</c:if>
												<div>
												<form:checkbox path="nowTVAddUI.payTvCampaign[${i.index}].topUps[${k.index}].selected" cssClass="topUpOffer" disabled="true"/>
												${topUp.detail}
												</div>
											</div>
										</c:forEach>
									</td>
									<c:if test="${nowTVdetailinfo.contractPeriod ne '0'}"><td colspan="2" align="right" valign="top" class="itemPrice" width="10%">${payTv.fix_term_rate}</td></c:if>
									<td align="right" valign="top" class="itemPrice" width="10%">${payTv.mth_to_mth_rate}</td>
								</tr>
							</c:forEach>
						<!-- Pay Bundle Campaign Pack (end)-->
						
						<!-- Other -->
						<form:hidden path="bandwidth" id="modelBandwidth" />
							<c:set var="vasgrp" value=""/> 
							<c:set var="backcolor" value="a"/> 
							<c:forEach var="v" items="${nowTVdetailinfo.nowTVOtherList}" varStatus="j">
									<c:if test="${v.grpDesc ne vasgrp}">
										<tr>
											<th style="text-align: left;" colspan="2">${v.grpDesc}</th> 
											<c:if test="${nowTVdetailinfo.contractPeriod ne '0'}"><th colspan="2"><spring:message code="ims.pcd.nowtv.003"/></th></c:if>
											<th colspan="1"><spring:message code="ims.pcd.nowtv.004"/></th>
										</tr>
										<c:set var="vasgrp" value="${v.grpDesc}"/>
									</c:if>
									<tr <c:if test="${j.index%2 eq 1}">class="alt"</c:if> id = "itemVasTitle_${v.itemType}">
										<c:choose> 
											<c:when test="${v.itemType eq 'NTV_HD'}"><c:set var="connType" value="Conn${v.TVType}"/></c:when> 
											<c:otherwise><c:set var="connType" value=""/></c:otherwise> 
										</c:choose> 
										<c:choose>
											<c:when test="${v.itemType eq 'NTV_OTHER' and v.TVType eq 'SD'}"><c:set var="SDOffers" value="SDOffers"/></c:when>  
											<c:otherwise><c:set var="SDOffers" value=""/></c:otherwise> 
										</c:choose> 
									
									<td valign="top" width="10" class="itemHtml"><form:checkbox  onclick="selectedVasCheck(this);"  path="selectedVas" cssClass="ntvother ${v.itemType} ${connType} ${SDOffers}"  value="${v.itemID}"/></td>    
									<td colspan="1" valign="top" >
										<span class="item_title_vas"><font <c:if test="${v.itemType eq 'NTV_HD'}">color="#01A9DB"</c:if>>${v.VASTitle}</font></span><br>   
									</td>
											<c:if test="${nowTVdetailinfo.contractPeriod ne '0'}"><td colspan="2" align="right" valign="top" class="itemPrice" width="10%">${v.recurrentAmt}</td></c:if>
													<td align="right" valign="top" class="itemPrice" width="10%">${v.mthToMthRate}</td>
								</tr> 
							</c:forEach>
						<!-- Other (end)-->
 					
	
	
<!-- 	gift start   -->
	<c:if test="${nowTVdetailinfo.pcdGiftList!=null}">
	
						<tr>
						<td colspan="5" bgcolor="#FFFFFF">
							<form:errors path="giftExclusiveError" cssClass="error" />
						</td>
						</tr>
						<tr>
						<td colspan="5" bgcolor="#FFFFFF">
							<form:errors path="giftSelectError" cssClass="error" />
						</td>
						</tr>
			
				<tr>
					<td bgcolor="#FFFFFF">
						<div>
 						<c:set var="giftType" scope="session" value="S"/>
						<c:forEach var="gift" items="${nowTVdetailinfo.pcdGiftList}" varStatus="status3">
						<c:if test="${giftType!=gift.typeDesc}"> 
						<tr <c:if test="${gift.normalGiftCnt == 0}">style="display:none" </c:if> class="giftHeader" >   
							<th colspan= "2" style="text-align: left;" class="table_title2">${gift.typeDesc}<br>
							</th>
							<th colspan="2"><spring:message code="ims.pcd.basketdetails.002"/></th> 
 					   		<th colspan="1"><spring:message code="ims.pcd.basketdetails.003"/></th> 
 						</tr> 
 						</c:if>
						<c:choose>
						<c:when test='${fn:length(selectedPcdNtvGiftList) != 0}'>
							<c:set var="checkFrag" scope="session" value=""/>
							<c:set var="display" scope="session" value="none"/>
							<c:forEach items="${selectedPcdNtvGiftList}" var="selectedIMSVas" varStatus="VasStatus">
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
 								<input type="checkbox" id="VAS${gift.id}" class="gift ${gift.type}" name="PcdNtvGiftItemBox" value="${gift.id}" onclick="checkVASDtl(${gift.id});" ${checkFrag}> 
 							</td> 
 							<td valign="top"> 
 								<span class="item_title_vas">${gift.giftTitle}</span>
 								<div id="VASDtl${gift.id}" style=display:${display}>
								<DIR>
                					<c:if test="${!empty gift.giftAttbList}">
										<c:forEach var="giftAttb" items="${gift.giftAttbList}" varStatus="status4">
											<c:if test="${giftAttb.inputMethod == 'INPUT'}">
												<span class='item_detail' style="display:inline-block;width:120px">${giftAttb.attbDesc}</span>
												<form:input id="attbInput${giftAttb.itemId}" cssClass="giftremark ${giftAttb.inputFormat}" path="pcdGiftList[${status3.index}].giftAttbList[${status4.index}].inputValue" maxlength="${giftAttb.maxLength}" onblur="blurOnAttbInput(this)"/><br>
												<div id="AttbWarn${giftAttb.itemId}_${status4.index}" class="GiftAttbWarn" class="ui-widget" style=display:none>
													<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
													<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
				 										<div class="contenttext_red" id="AttbErr${giftAttb.itemId}_${status4.index}"></div></p>
				 									</div>
												</div>
												<div id="error_giftAttb${giftAttb.itemId}_${status4.index}" class="ui-widget" style=display:none>
													<div class='ui-state-highlight ui-corner-all' style='margin-top: 10px; padding: 0 .7em;'>
													<p>
										 			<span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;''></span>	
										 				<div class="contenttext_red"><form:errors path="pcdGiftList[${status3.index}].giftAttbList[${status4.index}].inputValue" cssClass="error" /></div></p>
										 			</div>
												</div>
											</c:if>
                						</c:forEach>
                					</c:if>
								<br>
								<span class='item_detail'>${gift.giftDetail}</span>
								</DIR> 
								</div>
								<br/>
 							</td> 
 							<td colspan="2" align="right" valign="top" class="itemPrice" width="10%"> 
 									N/A 
 							</td> 
 							<td align="right" valign="top" class="itemPrice" width="10%"> 
 									N/A 
 							</td> 
 							 <form:hidden path="pcdGiftList[${status3.index}].showInd" id="SHOWIND${gift.id}" />
 						</tr>	
						<c:set var="giftType" scope="session" value="${gift.typeDesc}"/>
 						</c:forEach>
 						</div>
 					</td>
 				</tr>
 	</c:if>
<!--  								gift end  -->
 					
 					
 					
 					
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- end new -->	
	
	<table width="100%" border="0" cellspacing="1" cellpadding="6" class="contenttext">
	<tr><td colspan="2">&nbsp;</td><td align="right">
				<div>
					<a href="#" class="nextbutton" onclick="formSubmit()" ><spring:message code="ims.pcd.nowtv.001"/> </a>
				</div>
				<input type="hidden" name="currentView" value="nowtv"/>
				<form:hidden path="freeHD" />
				<form:hidden path="HDAct" /> 
			</td>
		</tr>
	</table>
</td>
</tr>
</table>	

</form:form>