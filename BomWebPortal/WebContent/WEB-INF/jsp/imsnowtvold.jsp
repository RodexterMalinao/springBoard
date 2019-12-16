<script language="Javascript">

	$(document).ready(function() {
		checkAdditionChannel();
	});

	function formSubmit(){
		
 		if(checkAdditionChannel()&&EntCheck()&&FreeHDCheck()){		
 			//alert("EntCheck: "+EntCheck());
 			$(":input").attr("disabled", false);
 			submitShowLoading();
 			document.nowTVForm.submit();
 		}	
	}
	
	function getPar(par){
		    var local_url = document.location.href;
		    var get = local_url.indexOf(par +"=");
		    if(get == -1){
		        return false;  
		    }  
		    var get_par = local_url.slice(par.length + get + 1); 
		    var nextPar = get_par.indexOf("&");
		    if(nextPar != -1){
		        get_par = get_par.slice(0, nextPar);
		    }
		    return get_par;
		}

	
	function insertParam(key, value)
	{
	    key = escape(key); value = escape(value);

	    var paraStr = document.location.search.substr(1).split('&');

	    var i=paraStr.length; var x; while(i--) 
	    {
	        x = paraStr[i].split('=');

	        if (x[0]==key)
	        {
	                x[1] = value;
	                paraStr[i] = x.join('=');
	                break;
	        }
	    }

	    if(i<0) {paraStr[paraStr.length] = [key,value].join('=');}

	    document.location.search = paraStr.join('&'); 
	}
	
	function decoderTypeCheck(decoderStr){
		$.ajax({
			url : 'checkdecodertype.html?decoderStr=' + decoderStr+ '&serbdyno=' + '${nowTVdetailinfo.serbdyno}',
			type : 'POST',
			dataType : 'json',
			timeout : 5000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
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
	

	
	function checkAdditionChannel(){
		var addChannelCheckboxGroup=document.forms["nowTVForm"].channelItem;
		var addVASCheckboxGroup=document.forms["nowTVForm"].nowTVVASItem;
		var addHDChannelCheckboxGroup=document.forms["nowTVForm"].HDchannelItem;
		var addAVChannelCheckboxGroup=document.forms["nowTVForm"].AVchannelItem;
		var addVASHDCheckboxGroup=document.forms["nowTVForm"].nowTVVASHDItem;
		var addVASOtherCheckboxGroup=document.forms["nowTVForm"].nowTVOtherItem;
		var addHDCheckboxGroup=document.forms["nowTVForm"].FreeHDchannelItem;
		var addChildChannelCheckboxGroup=document.forms["nowTVForm"].childHDItem;
		var addHDConnectCheckboxGroup = document.forms["nowTVForm"].nowTVHDItem;
		var addSTBCheckboxGroup = document.forms["nowTVForm"].nowTVSTBItem;
		var addPTCheckboxGroup = document.forms["nowTVForm"].nowTVPTItem;
		
		var i=0;
		var k=0;
		var j=0;
		var l=0;
		var check = 0;
		var count_VAS=0;
		var channelCredit=0;
		var AVchannelCount=0;
		var	VASCredit=0;	
		var count_HDVAS=0;
		var count_OtherVAS=0;
		var HDchannelCredit=0;
		var	HDVASCredit=0;
		var count_HD=0;
		var count_ChildChannel=0;
		var HDcheck = 0;
		var STBcheck = 0;
		var PTcheck = 0;
		var TVBundleType="";
		var HDSelected="N";
		
		
		document.getElementById('warning_VAS').style.display = "none";
		document.getElementById('warning_VAS_msg').innerHTML = "";
		document.getElementById('warning_channel').style.display = "none";
		document.getElementById('warning_channel_msg').innerHTML = "";
		document.getElementById('warning_HDchannel').style.display = "none";
		document.getElementById('warning_HDchannel_msg').innerHTML = "";
		document.getElementById('warning_HD').style.display = "none";
		document.getElementById('warning_HD_msg').innerHTML = "";
		document.getElementById('warning_starter').style.display = "none";
		document.getElementById('warning_starter_msg').innerHTML = "";
		document.getElementById('warning_STB').style.display = "none";
		document.getElementById('warning_STB_msg').innerHTML = "";

		document.getElementById('warning_AVchannel').innerHTML = "";
		
		if(addChannelCheckboxGroup!=null){
			for(i=0;i<addChannelCheckboxGroup.length;i++){
				if(addChannelCheckboxGroup[i].checked){
					channelCredit = channelCredit + parseInt(addChannelCheckboxGroup[i].title);
					var childId = "Child"+addChannelCheckboxGroup[i].value;
					if(document.getElementById(childId)!=null){
						document.getElementById(childId).checked = true;
					}
				}else{
					var childId = "Child"+addChannelCheckboxGroup[i].value;
					if(document.getElementById(childId)!=null){
						document.getElementById(childId).checked = false;
					}
				}
			}
		}
		if(addAVChannelCheckboxGroup!=null){
			for(i=0;i<addAVChannelCheckboxGroup.length;i++){
				if(addAVChannelCheckboxGroup[i].checked){
					channelCredit = channelCredit + parseInt(addAVChannelCheckboxGroup[i].title);
					AVchannelCount++;
				}
			}
		}
		if(addVASCheckboxGroup!=null){
			for(k=0;k<addVASCheckboxGroup.length;k++){
				if(addVASCheckboxGroup[k].checked){
					count_VAS++;
					VASCredit = VASCredit + parseInt(addVASCheckboxGroup[k].title);
				}
 			}
 		}
		
		if(addPTCheckboxGroup != null)
			if(addPTCheckboxGroup.length>0){
				for(var k=0;k<addPTCheckboxGroup.length;k++){
					if(addPTCheckboxGroup[k].checked) 
						{
							PTcheck++;
							count_VAS++;
							VASCredit = VASCredit + parseInt(addPTCheckboxGroup[k].title);
						}
				}
			}
			else
			{
				if(addPTCheckboxGroup.checked) 
					{
						PTcheck++;
						count_VAS++;
						VASCredit = VASCredit + parseInt(addPTCheckboxGroup.title);
					}
			}
		
		if(addHDChannelCheckboxGroup!=null){
			for(i=0;i<addHDChannelCheckboxGroup.length;i++){
				if(addHDChannelCheckboxGroup[i].checked){
					HDchannelCredit = HDchannelCredit + parseInt(addHDChannelCheckboxGroup[i].title);
				}
			}
		}		
		if(addVASHDCheckboxGroup!=null){
			for(k=0;k<addVASHDCheckboxGroup.length;k++){
				if(addVASHDCheckboxGroup[k].checked){
					count_HDVAS++;
					HDVASCredit = HDVASCredit + parseInt(addVASHDCheckboxGroup[k].title);
				}
 			}
 		}
		
		if(addVASOtherCheckboxGroup!=null){
			if(addVASOtherCheckboxGroup.length>0){
				for(k=0;k<addVASOtherCheckboxGroup.length;k++){
					if(addVASOtherCheckboxGroup[k].checked){
						count_OtherVAS++;
					}
 				}
			}else if (addVASOtherCheckboxGroup.checked)count_OtherVAS++;
 		}
		
		if(addHDCheckboxGroup!=null){
			for(k=0;k<addHDCheckboxGroup.length;k++){
				if(addHDCheckboxGroup[k].checked)count_HD++;
			}
		}
		


		if(addChildChannelCheckboxGroup!=null){
			if(addChildChannelCheckboxGroup.size!=null){
				if(addChildChannelCheckboxGroup.checked){
					count_ChildChannel++;
				}
			}else if(addChildChannelCheckboxGroup.length!=null){
				for(k=0;k<addChildChannelCheckboxGroup.length;k++){
					if(addChildChannelCheckboxGroup[k].checked){
						count_ChildChannel++;
					}
				}
			}
		}
		
		
		if(addHDConnectCheckboxGroup != null && addHDConnectCheckboxGroup.length>0){
			for(var k=0;k<addHDConnectCheckboxGroup.length;k++){
				if(addHDConnectCheckboxGroup[k].checked) HDcheck++;
			}
		} else if (addHDConnectCheckboxGroup != null && addHDConnectCheckboxGroup.checked) HDcheck++;
		
		if(addSTBCheckboxGroup != null)
		if(addSTBCheckboxGroup.length>0){
			for(var k=0;k<addSTBCheckboxGroup.length;k++){
				if(addSTBCheckboxGroup[k].checked) STBcheck++;
			}
		}
		else 
		{
			if(addSTBCheckboxGroup.checked) STBcheck++;
		}
		
		
		
 		if((count_VAS+count_HDVAS) > 1){
 			document.getElementById('warning_VAS').style.display = "inline";
 			document.getElementById('warning_VAS_msg').innerHTML =
  				"Only 1 channel bundle can be selected";

 		}else {
 			check++;	
 		}
 		
 		if(channelCredit > VASCredit && (VASCredit==0)){
 			document.getElementById('warning_VAS').style.display = "inline";
 			document.getElementById('warning_VAS_msg').innerHTML =
  				"Please select the SD TV bundle";
 		}else if(channelCredit > VASCredit && (VASCredit!=0)){
 			document.getElementById('warning_channel').style.display = "inline";
 			document.getElementById('warning_channel_msg').innerHTML =
			"Your selected channel(s) do not match with " + 
			VASCredit + 
			" SD TV bundle";

 		}else if ((channelCredit < VASCredit)&&((count_VAS+count_HDVAS)==1)){
 			document.getElementById('warning_channel').style.display = "inline";
 			document.getElementById('warning_channel_msg').innerHTML =
 				"Please select " + (VASCredit-channelCredit) + " more SD channel(s)";
 		} 
 		else check++;
 		
 		if(HDchannelCredit > HDVASCredit && (HDVASCredit==0)){
 			document.getElementById('warning_HDchannel').style.display = "inline";
 			document.getElementById('warning_HDchannel_msg').innerHTML =
 				"Please select the HD TV bundle";
 		}else if((HDchannelCredit > HDVASCredit) && (HDVASCredit!=0)){
 			document.getElementById('warning_HDchannel').style.display = "inline";
 			document.getElementById('warning_HDchannel_msg').innerHTML =
 			"Your selected channel(s) do not match with " + 
			HDVASCredit + 
			" HD TV bundle";

		}else if ((HDchannelCredit < HDVASCredit)&&((count_VAS+count_HDVAS)==1)){
			document.getElementById('warning_HDchannel').style.display = "inline";
 			document.getElementById('warning_HDchannel_msg').innerHTML =
 				"Please select " + (HDVASCredit-HDchannelCredit) + " more HD channel(s)";
 		} else check++;	
 		
		if(AVchannelCount>0&&document.getElementById('AVSelect').checked == false){
			document.getElementById('warning_AVchannel').innerHTML =
 				"<font color='#CC3311'>Please select 'I wish to view AV/adult channel preview'</font>";
		} else check++;
 		
// 		if(document.getElementById('HDActivation')!=null){
// 			if(document.getElementById('HDActivation').checked){
// 				HDcheck = 1;
// 			}else HDcheck = 0;
// 		}else HDcheck = 0;
		
		
		if (document.getElementById('deselectFlag').checked == false &&
				(channelCredit >0 || count_VAS>0 || AVchannelCount>0
				|| VASCredit>0 || count_HDVAS>0 || HDchannelCredit>0
				|| HDVASCredit>0 || count_OtherVAS>0 || HDcheck>0)){
			
			document.getElementById('warning_starter').style.display = "inline";
 			document.getElementById('warning_starter_msg').innerHTML =
				"Please select the Starter Pack or deselect all the selection.";	
			document.getElementById('warning_VAS').style.display = "none";
			document.getElementById('warning_VAS_msg').innerHTML = "";
			document.getElementById('warning_channel').style.display = "none";
			document.getElementById('warning_channel_msg').innerHTML = "";
			document.getElementById('warning_HDchannel').style.display = "none";
			document.getElementById('warning_HDchannel_msg').innerHTML = "";
			
			document.getElementById('warning_AVchannel').innerHTML = "";
 		}else check++;
		
		if(count_OtherVAS > 1){
			document.getElementById('warning_OtherVAS').style.display = "inline";
 			document.getElementById('warning_OtherVAS_msg').innerHTML =
 				"Only 1 Special Rental Equipment can be selected.";	
		}else{
			document.getElementById('warning_OtherVAS').style.display = "none";
			document.getElementById('warning_OtherVAS_msg').innerHTML = "";
			check++;
		}
		
		if(addHDConnectCheckboxGroup != null)
		{
			if(HDcheck > 1)
			{
				document.getElementById('warning_HD').style.display = "inline";
	 			document.getElementById('warning_HD_msg').innerHTML = "Only 1 NOWTV HD can be selected.";	
			}
			else if(PTcheck > 0 && HDcheck != 1)//888
			{
				document.getElementById('warning_HD').style.display = "inline";
	 			document.getElementById('warning_HD_msg').innerHTML = "1 NOWTV HD should be selected.";	
			}
			else{
				document.getElementById('warning_HD').style.display = "none";
				document.getElementById('warning_HD_msg').innerHTML = "";
				check++;
			}
		}
		else if(addSTBCheckboxGroup != null)
		if(PTcheck > 0 && STBcheck != 1)
		{
			document.getElementById('warning_STB').style.display = "inline";
 			document.getElementById('warning_STB_msg').innerHTML = "1 NOWTV STB Rental should be selected.";	
		}
		else{
			document.getElementById('warning_STB').style.display = "none";
			document.getElementById('warning_STB_msg').innerHTML = "";
			check++;
		}
		else
		{
			check++;
		}
		
		
		if(addHDConnectCheckboxGroup!=null){
			var HDchecked1 = 0;
			if(addHDConnectCheckboxGroup.length != null)
			for(k=0;k<addHDConnectCheckboxGroup.length;k++){
				if(addHDConnectCheckboxGroup[k].checked)
				{
					HDchecked1 = 1;
					//document.getElementById('SDHomePlug').checked = false;
					//document.getElementById('SDHomePlug').disabled = true;
					$('#SDHomePlug').attr('checked', false);
					$('#SDHomePlug').attr('disabled', true);
					$('tr[name=HD_child]').show();	
					HDSelected = "Y";
					$("#HDAct").val("Y");
				}
			}
				else if(addHDConnectCheckboxGroup.checked)	
				{
					HDchecked1 = 1;
					//document.getElementById('SDHomePlug').checked = false;
					//document.getElementById('SDHomePlug').disabled = true;
					$('#SDHomePlug').attr('checked', false);
					$('#SDHomePlug').attr('disabled', true);
					$('tr[name=HD_child]').show();	
					HDSelected = "Y";
					$("#HDAct").val("Y");
				}
				if(HDchecked1 == 0)
				{
					//document.getElementById('SDHomePlug').disabled = false;
					$('#SDHomePlug').attr('disabled', false);
					$('tr[name=HD_child]').hide();
					HDSelected = "N";
					$("#HDAct").val("N");
				}
			
		}else{
			HDSelected = "N";
		}
		
/* 		if (document.getElementById('HDActivation')!=null){
			if(document.getElementById('HDActivation').checked){
				document.getElementById('SDHomePlug').checked = false;
				document.getElementById('SDHomePlug').disabled = true;
				$('tr[name=HD_child]').show();	
				HDSelected = "Y";
				$("#HDAct").val("Y");
			}else{
				document.getElementById('SDHomePlug').disabled = false;
				$('tr[name=HD_child]').hide();
				HDSelected = "N";
				$("#HDAct").val("N");
			}
			
		}else{
			HDSelected = "N";
		} */
		

		//FreeHDCheck();

		if("${nowTVdetailinfo.nowTVFormType}"=="PON List" 
				&& "${nowTVdetailinfo.inFormType}"!="PON2PCD"){
			if((count_VAS+count_HDVAS)>0){
				document.getElementById('Free2HD').style.display = "inline";
				$("#freeHD").val("Y");
			}else{
				document.getElementById('Free2HD').style.display = "none";
				$("#freeHD").val("N");
			}	
		}
		
		if((count_VAS + count_HDVAS) <= 1){
			if(VASCredit == 1){
				TVBundleType = "1SD";
			}else if(VASCredit == 2){
				TVBundleType = "2SD";
			}else if(VASCredit == 3){
				TVBundleType = "3SD";
			}else if(HDVASCredit == 1){
				TVBundleType = "1HD";
			}else if(HDVASCredit == 2){
				TVBundleType = "2HD";
			}
/* 			if((HDchannelCredit+count_ChildChannel)>=1 || $("#freeHD").val() == "Y"){
				HDSelected = "Y";
			}else{
				HDSelected = "N";
			} */
			
			var bandwidth = "${nowTVdetailinfo.bandwidth}";
			var larger18M = ""; 
			
			var listType="${nowTVdetailinfo.nowTVFormType}";
			var decodeTypeLoop="";
			if("${nowTVdetailinfo.nowTVFormType}" == "PON List"){
				if("${nowTVdetailinfo.inFormType}" != null){
					if("${nowTVdetailinfo.inFormType}" == "PON2PCD"){
						listType="PCD List";
					}else{
						listType="PON List";
					}
				}
			}

			if(bandwidth>=18){
				larger18M = "Y";
			}else larger18M = "N";
			
			var decoderStr = listType+"|"+TVBundleType+"|"+HDSelected+"|"+larger18M;
			
			if("${nowTVdetailinfo.otherTVList}" != "" 
	 			&& "${nowTVdetailinfo.otherTVList}" != "null"
	 			&& "${nowTVdetailinfo.otherTVList}" != null){
	 				var decoderStr = "${nowTVdetailinfo.otherTVList}"+"|"+TVBundleType+"|"+HDSelected+"|"+larger18M;
			}

			
			decoderTypeCheck(decoderStr);
			
		}
		
		if (check == 7){
			return true;
		}
 			
		
	}

	function deselectCheck(){	
		if(document.getElementById('deselectFlag').checked == true){
			document.getElementById('deselectFlagChannel').checked = true;
			document.getElementById('starterInfo').style.display = "inline";
			//document.getElementById('nowTVStarterVASDtl').style.display = "inline";
			
		}else {
			var answer = confirm("Are you sure to un-tick the check box for selected offer?");
			if (answer){
				document.getElementById('deselectFlagChannel').checked = false;
				document.getElementById('starterInfo').style.display = "none";

				//document.getElementById('nowTVStarterVASDtl').style.display = "none";				
				
	 		}else {
	 			document.getElementById('deselectFlagChannel').checked = true; 
 				document.getElementById('deselectFlag').checked = true;
 				
			}
		}
	}
	
 	function EntCheck(){
 		
		var addEntCheckboxGroup=document.forms["nowTVForm"].EntItem;
		var k=0;
		var count_Ent=0;
		
		if("${fn:length(nowTVdetailinfo.nowTVEntDescList)}" == 0){
			return true;
		}
		
	
		document.getElementById('warning_Ent').style.display = "none";
		document.getElementById('warning_Ent_msg').innerHTML = "";
		document.getElementById('warning_starter').style.display = "none";
		document.getElementById('warning_starter_msg').innerHTML = "";
		
		
		if(addEntCheckboxGroup!=null){
			for(k=0;k<addEntCheckboxGroup.length;k++){
				if(addEntCheckboxGroup[k].checked)count_Ent++;
			}
		}
		
		if(count_Ent > 1 && document.getElementById('deselectFlag').checked == true){
			document.getElementById('warning_Ent').style.display = "inline";
			document.getElementById('warning_Ent_msg').innerHTML =  
				"Only 1 Entertainment Pack can be selected";
			document.getElementById('warning_starter').style.display = "none";
			document.getElementById('warning_starter_msg').innerHTML = "";
		}else if(count_Ent==0 && document.getElementById('deselectFlag').checked == true){
			document.getElementById('warning_Ent').style.display = "inline";
			document.getElementById('warning_Ent_msg').innerHTML = 
				"Please select one Entertainment Pack.";
			document.getElementById('warning_starter').style.display = "none";
			document.getElementById('warning_starter_msg').innerHTML = "";
		}else if (count_Ent > 0 && document.getElementById('deselectFlag').checked != true){
			document.getElementById('warning_starter').style.display = "inline";
			document.getElementById('warning_starter_msg').innerHTML = 
			"Please select the Starter Pack or deselect the Entertainment Pack.";
		}else return true;

		
		if(check==2){
			return true;
		}
 	}
 	
 	function FreeHDCheck(){
		var addHDCheckboxGroup=document.forms["nowTVForm"].FreeHDchannelItem;
		var k=0;
		var count_HD=0;
		var addVASCheckboxGroup=document.forms["nowTVForm"].nowTVVASItem;
		var addVASHDCheckboxGroup=document.forms["nowTVForm"].nowTVVASHDItem;
		var VASCheck=0;
		var VASHDCheck=0;
		
		if(addVASCheckboxGroup!=null){
			for(k=0;k<addVASCheckboxGroup.length;k++){
				if(addVASCheckboxGroup[k].checked)VASCheck++;
			}
		}
		if(addVASHDCheckboxGroup!=null){
			for(k=0;k<addVASHDCheckboxGroup.length;k++){
				if(addVASHDCheckboxGroup[k].checked)VASHDCheck++;
			}
		}		
			
		
		if(addHDCheckboxGroup!=null){
			for(k=0;k<addHDCheckboxGroup.length;k++){
				if(addHDCheckboxGroup[k].checked)count_HD++;
			}
		}
		
		
		if (((VASCheck+VASHDCheck) == 0)){
			//document.getElementById('SDHomePlug').disabled = false;
			$('#SDHomePlug').attr('disabled', false);
			document.getElementById('warning_FreeHD').style.display = "none";
			document.getElementById('warning_FreeHD_msg').innerHTML = "";
			return true;
		}else if (VASHDCheck>0){
			//document.getElementById('SDHomePlug').checked = false;
			//document.getElementById('SDHomePlug').disabled = true;
			$('#SDHomePlug').attr('checked', false);
			$('#SDHomePlug').attr('disabled', true);
			document.getElementById('warning_FreeHD').style.display = "none";
			document.getElementById('warning_FreeHD_msg').innerHTML = "";
			return true;
		}else if(count_HD > 2){
			document.getElementById('warning_FreeHD').style.display = "inline";
			document.getElementById('warning_FreeHD_msg').innerHTML =
				"Only 1 additional free HD channel can be selected";	
		}else if(count_HD == 1){
			document.getElementById('warning_FreeHD').style.display = "inline";
			document.getElementById('warning_FreeHD_msg').innerHTML =
				"Please select 1 additional free HD channel";			
 		}else if(count_HD == 0){
			//document.getElementById('SDHomePlug').disabled = false;
			$('#SDHomePlug').attr('disabled', false);
			document.getElementById('warning_FreeHD').style.display = "none";
			document.getElementById('warning_FreeHD_msg').innerHTML = "";
			return true;
		}else{
			document.getElementById('warning_FreeHD').style.display = "none";
			document.getElementById('warning_FreeHD_msg').innerHTML = "";			
			return true;
		}
 	}
	
 	function changeForm()
 	{
 		if(document.getElementById("changeFrag").checked == true){	
 			window.location = "imsnowtv.html?NowTVFromType=PCD2PON";
 		}
 		else{
 			window.location = "imsnowtv.html?NowTVFromType=PON2PCD";
 		}
 	}
	function checkVASDtl(ItemID, TVList){

		checkAdditionChannel();
		deselect(ItemID, TVList);
	
	}
	
	function checkTVList(ItemID,TVList){
		checkVASDtl(ItemID,TVList);
		if(document.getElementById("VAS" + ItemID).checked == true){
			insertParam('TVList',TVList);
		}
	}
	
	function deselect(ItemID, TVList){	

		if(document.getElementById("VAS" + ItemID).checked == false){
			var answer = confirm("Are you sure to un-tick the check box for selected offer? \nThe selected channel(s)will be clear after to press \" OK \" ! ");
			if (answer){
				document.getElementById("VAS" + ItemID).checked = false;

				clearChannel();
				checkAdditionChannel();

				showLoading();
				//if(TVList != "" && TVList != "null"){
				
					
// 				if(TVList.indexOf("LIST") != -1 
// 						|| "${nowTVdetailinfo.otherTVList}".indexOf("LIST") != -1){
							
// 				}
				
				insertParam('TVList','deselect');
				
			}else {
 				document.getElementById("VAS" + ItemID).checked = true;
 				//document.getElementById("VASDtl"+ItemID).style.display = "inline";
 				checkAdditionChannel();
			}
		}else{
			//document.getElementById("VASDtl"+ItemID).style.display = "inline";
			checkAdditionChannel();
		}
	}
	
	function clearChannel(){
		var addChannelCheckboxGroup=document.forms["nowTVForm"].channelItem;
		var addHDChannelCheckboxGroup=document.forms["nowTVForm"].HDchannelItem;
		var addAVChannelCheckboxGroup=document.forms["nowTVForm"].AVchannelItem;
		var addFreeHDCheckboxGroup=document.forms["nowTVForm"].FreeHDchannelItem;

		
		if(addChannelCheckboxGroup!=null){
			for(i=0; i<addChannelCheckboxGroup.length; i++){
				if(addChannelCheckboxGroup[i].checked){
					addChannelCheckboxGroup[i].checked = false;
					//alert("channelItem");
				}
			}
		}		
		if(addHDChannelCheckboxGroup!=null){
			for(i=0; i<addHDChannelCheckboxGroup.length; i++){
				if(addHDChannelCheckboxGroup[i].checked){
					addHDChannelCheckboxGroup[i].checked = false;
					//alert("HDchannelItem");
				}
			}
		}
		if(addAVChannelCheckboxGroup!=null){
			for(i=0; i<addAVChannelCheckboxGroup.length; i++){
				if(addAVChannelCheckboxGroup[i].checked){
					addAVChannelCheckboxGroup[i].checked = false;
					//alert("AVchannelItem");
				}
			}
		}
		if(addFreeHDCheckboxGroup!=null){
			for(i=0; i<addFreeHDCheckboxGroup.length; i++){
				if(addFreeHDCheckboxGroup[i].checked && addFreeHDCheckboxGroup[i].style.display != "none"){
					addFreeHDCheckboxGroup[i].checked = false;
					//alert("FreeHDchannelItem");
				}
			}
		}
		
		if(document.getElementById('deselectFlag').checked == true){
			deselectCheck();
		}
		
		checkAdditionChannel();

	}


</script> 

<form:form name="nowTVForm" commandName="nowTVdetailinfo" method="POST">

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
<table width="100%"  class="tablegrey">
  <tr>
	<td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="1" rules="none">

			<tr><td>
			<div class="ui-widget" id="warning_starter" style="display:none">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_starter_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div>
			</td></tr>
			<tr><td>	
			<div class="ui-widget" id="warning_VAS" style="display:none">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_VAS_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div></td></tr>	
			<div class="ui-widget" id="warning_OtherVAS" style="display:none">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_OtherVAS_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div></td></tr>
			<div class="ui-widget" id="warning_HD" style="display:none">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_HD_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div></td></tr>
			<div class="ui-widget" id="warning_STB" style="display:none">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_STB_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div></td></tr>
			<div class="ui-widget" id="warning_Ent" style="display:none">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_Ent_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div></td></tr>
			<div class="ui-widget" id="warning_FreeHD" style="display:none">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_FreeHD_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div></td></tr>
			<div class="ui-widget" id="warning_channel" style="display:none">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_channel_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div></td></tr>
			<div class="ui-widget" id="warning_HDchannel" style="display:none">
				<div class="ui-state-error ui-corner-all" style="margin-top: 10px; padding: 0 .7em;"> 
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<div class="contenttext" id="warning_HDchannel_msg" style="font-weight:bold;"></div>
					</p>
				</div>
			</div></td></tr>
		<tr>
			<td class="table_title" ><font size="4">now</font>TV</td>
		</tr>
		</table>
		
		
		
		
		<table class="tableGreytext" width="100%" cellspacing="1" border="0" bgcolor="white">
			<c:if test='${nowTVdetailinfo.nowTVFormType == "PON List"}'>
			<c:set  var="type30F6" scope="session" value="N"/>	
				<c:choose>
	        			<c:when test='${nowTVdetailinfo.isCouponOffer == "Y"}'>
	        				<c:set  var="type30F6" scope="session" value="N"/>
	        			</c:when>
	        			<c:otherwise>
	        				<c:set  var="type30F6" scope="session" value="Y"/>
	        			</c:otherwise>
	        	</c:choose>
			<tr>
	        	<td colspan ="3" align="left">
	        		<c:choose>
	        		<c:when test='${nowTVdetailinfo.inFormType == "" && nowTVdetailinfo.isCouponOffer != "Y"}'>
	        			<c:set  var="type30F6" scope="session" value="Y"/>
	        			<input type="checkbox" name="changeFormName" id="changeFrag" onClick="changeForm()" checked/>
	        		</c:when>
	        		<c:when test='${nowTVdetailinfo.inFormType == "PON2PCD"}'>
	        			<c:set  var="type30F6" scope="session" value="N"/>
	        			<input type="checkbox" name="changeFormName" id="changeFrag" onClick="changeForm()" />
	        		</c:when>
	        		<c:when test='${nowTVdetailinfo.inFormType == "PCD2PON" && nowTVdetailinfo.isCouponOffer != "Y"}'>
	        			<c:set  var="type30F6" scope="session" value="Y"/>
	        			<input type="checkbox" name="changeFormName" id="changeFrag" onClick="changeForm()" checked/>
	        		</c:when>
	        		<c:otherwise>
	        			<c:set  var="type30F6" scope="session" value="N"/>
	        			<input type="checkbox" name="changeFormName" id="changeFrag" onClick="changeForm()" checked/>
	        		</c:otherwise>
	        		</c:choose>
	        		<c:choose>
	        			<c:when test='${nowTVdetailinfo.isCouponOffer == "Y"}'>
	        				Choose supermarket coupon 
	        			</c:when>
	        			<c:otherwise>
	        				Choose 30 months free 6 months 
	        			</c:otherwise>
	        		</c:choose>
	        	</td>
        	</tr>
        	</c:if>
        	
			<tr> 
	        	<td colspan= "3" class="table_title" align="left"><font size="3">now</font>TV bundle Service<br></td>
	            <td class="table_title" align="right" width="10%">Monthly Fixed Term Rate</td>
	            <td class="table_title" align="right" width="10%">Month-to-Month Rate</td>
	        </tr>
			
<%

		System.out.println("Making Starter List in imsnowtv.jsp");

		List<NowTVVasUI> NowTVStarterList = NowTVDetail.getNowTVStarterList();
		List<NowTVVasUI> NowTVStarterDescList = NowTVDetail.getNowTVStarterDescList();
		String display = "inline";
		
				for(int i = 0; i<NowTVStarterList.size();i++){
					
					
					if(SelectedItemList!=null ){
		 				checkStr = "checked";
		 				display = "inline";
						for(int l=0; l< SelectedItemList.length; l++){	
							if(SelectedItemList[l].equals(NowTVStarterList.get(i).getItemID())){
								checkStr = "checked";
								display = "inline";
								break;
							}else {
								checkStr = "";
								display = "none";
							}
						}	
					}	
					out.println("<tr><td valign='top'  width='10'>"+
							"<input id='deselectFlag' type='checkbox' onClick='deselectCheck()' name='nowTVStarterItem' value='"+
							NowTVStarterList.get(i).getItemID()+
							"' title ='0' checked></td>");
					out.println("<td colspan= '1' valign='top' ><span class='item_title_vas'>"+
							NowTVStarterList.get(i).getVASTitle()+
							" </span>");
						
					out.println("<br><div id='starterInfo'> ");
					for(int j = 0; j<NowTVStarterDescList.size();j++){
						out.println("<span class='item_detail'>"+
								NowTVStarterDescList.get(j).getChannelDesc()+
								"</span>");
						out.println("<span style='display:none;'>"+
								"<input id='deselectFlagChannel' type='checkbox' name='channelItem' value='"+
								NowTVStarterDescList.get(j).getChannelID()+
								"' title ='0' checked></span>");
					}
					out.println("</div></td><td  colspan= '2' align='right' valign='top' class='BGgreen2' width='10%'>"+
							//NowTVDetail.getNowTVStarterList().get(i).getRecurrentAmt()+
							NowTVStarterList.get(i).getRecurrentAmt()+
							"</td><td align='right' valign='top' class='BGgreen2' width='10%'>"+
							//NowTVDetail.getNowTVStarterList().get(i).getMthToMthRate()+
							NowTVStarterList.get(i).getMthToMthRate()+
							"</td></tr>");
				}

		System.out.println("Making VAS List in imsnowtv.jsp");
		List<NowTVVasUI> NowTVVasList = NowTVDetail.getNowTVVasList();
		
		System.out.println("Making Other VAS List in imsnowtv.jsp");
		List<NowTVVasUI> NowTVOtherList = NowTVDetail.getNowTVOtherList();
		String VasDesc = "";
		display = "none";
		
		String type30F6 = (String)request.getSession().getAttribute("type30F6");
		
		if(type30F6 == null){
			type30F6 = "N";
		}
		
			for(int i = 0; i<NowTVVasList.size();i++){
				
				if(SelectedItemList!=null ){
		 			checkStr = "a";
		 			display = "none";
					for(int l=0; l< SelectedItemList.length; l++){	
						if(SelectedItemList[l].equals(NowTVVasList.get(i).getItemID())){
							if(TVList == null)
							{
								checkStr = "checked";
								display = "inline";
							}
							else
							{
								if(!TVList.equals("deselect"))
								{
									
									if(TVList.equals(NowTVVasList.get(i).getTVList()))
									{
										checkStr = "checked";
										display = "inline";
										break;
									}
								}
							}
							
							
						}
					}	
					
					if(TVList!= null && !TVList.equals(NowTVDetail.getOtherTVList()))
					{
						NowTVDetail.setOtherTVList(TVList);
					}
				}
				if(NowTVDetail.getIsCouponOffer().equals("Y")){
					if(NowTVDetail.getInFormType()!=null&&NowTVDetail.getInFormType().equals("PON2PCD")){
						VasDesc = NowTVVasList.get(i).getVASTitle();
					}else{
						VasDesc = NowTVVasList.get(i).getCouponDesc();
					}
				
				}
				else{
					VasDesc = NowTVVasList.get(i).getVASTitle();
				}
				
				if(NowTVVasList.get(i).getTVType().equals("HD")){
					if(!NowTVDetail.getNowTVFormType().equals("PON List")||NowTVDetail.getInFormType().equals("PON2PCD")){
						out.println("<td valign='top'  width='10'><input id='VAS"+
							NowTVVasList.get(i).getItemID()+
							"' type='checkbox' name='nowTVVASHDItem' value='"+
							NowTVVasList.get(i).getItemID()+
							"' title='"  + NowTVVasList.get(i).getCredit()+ 
							"' onclick=\"checkVASDtl("+
							NowTVVasList.get(i).getItemID()+
							",'')\""+
							checkStr+
							"></td><td colspan= '1' valign='top'>"+
							"<span class='item_title_vas'><font color='#01A9DB'>"+
							NowTVVasList.get(i).getVASTitle()+"</font></span>");
						out.println("<div id='VASDtl" +
								NowTVVasList.get(i).getItemID()+
								"' style=display:none"+
								"><DIR><span class='item_detail'>"+
								NowTVVasList.get(i).getVASDetail()+
								"</span></DIR></div></td>");
						out.println("<td  colspan= '2' align='right' valign='top' class='BGgreen2' width='10%'>"+
								NowTVVasList.get(i).getRecurrentAmt()+
								"</td><td align='right' valign='top' class='BGgreen2' width='10%'>"+
								NowTVVasList.get(i).getMthToMthRate()+"</td></tr>");
						
					}
				}else if((type30F6.equals("Y")&&NowTVVasList.get(i).getItemType().equals("NTV_P_30F6"))||
						(type30F6.equals("N")&&NowTVVasList.get(i).getItemType().equals("NTV_PAY"))||
						(type30F6.equals("N")&&!NowTVVasList.get(i).getItemType().contains("NTV_P"))){
					
					 System.out.println("Making Special VAS TV List in imsnowtv.jsp");
					 System.out.println("NowTVVasList.get("+i+").getTVList() in : imsnowtv.jsp: " +NowTVVasList.get(i).getTVList());
					 System.out.println("NowTVDetail.getOtherTVList() in imsnowtv.jsp: " +NowTVDetail.getOtherTVList());
					 
					 String ItemTVList = NowTVVasList.get(i).getTVList();
					String name = "nowTVVASItem";
					if(NowTVDetail.getPtTvList() != null)
					for(int index = 0; index< NowTVDetail.getPtTvList().size();++index)
					{
						if(ItemTVList != null)
						if(ItemTVList.indexOf(NowTVDetail.getPtTvList().get(index))>=0)
						{
							name = "nowTVPTItem";
						}
					}
						
					 if (NowTVVasList.get(i).getTVList()!= null){
						 
						if(NowTVDetail.getOtherTVList()!= null && NowTVVasList.get(i).getTVList().equals(NowTVDetail.getOtherTVList())){
							checkStr = "checked";
						}else {
							checkStr = "";
						}
						
						
						
						out.println("<td valign='top'  width='10'><input id='VAS"+
							NowTVVasList.get(i).getItemID()+
							"' type='checkbox' name='"+name+"' value='"+
							NowTVVasList.get(i).getItemID()+
							"' title='" + NowTVVasList.get(i).getCredit()+ 
							"' onclick=\"checkTVList("+
							NowTVVasList.get(i).getItemID()+
							",'"+NowTVVasList.get(i).getTVList()+"')"+
							"\""+checkStr+"></td><td colspan= '1' valign='top'>"+
							"<span class='item_title_vas'>"+
							VasDesc+"</span>");
						checkStr = "";
					}else{ 
						out.println("<td valign='top'  width='10'><input id='VAS"+
							NowTVVasList.get(i).getItemID()+
							"' type='checkbox' name='"+name+"' value='"+
							NowTVVasList.get(i).getItemID()+
							"' title='" + NowTVVasList.get(i).getCredit()+ 
							"' onclick=\"checkVASDtl("+
							NowTVVasList.get(i).getItemID()+
							",'')\""+checkStr+"></td><td colspan= '1' valign='top'>"+
							"<span class='item_title_vas'>"+
							VasDesc+"</span>");
					}
					out.println("<div id='VASDtl" +
							NowTVVasList.get(i).getItemID()+
							"' style=display:none"+
							"><DIR><span class='item_detail'>"+
							NowTVVasList.get(i).getVASDetail()+
							"</span></DIR></div></td>");
					out.println("<td  colspan= '2' align='right' valign='top' class='BGgreen2' width='10%'>"+
							NowTVVasList.get(i).getRecurrentAmt()+
							"</td><td align='right' valign='top' class='BGgreen2' width='10%'>"+
							NowTVVasList.get(i).getMthToMthRate()+"</td></tr>");
					
				}
				
			}
	
		display = "none";

		if (NowTVOtherList!=null){
						
			boolean Is_New_Title = false;

			String id_str = "";
			String item_type = "";
			String HDtagO = "";
			String HDtagC = "";
		
			for(int i = 0; i<NowTVOtherList.size();i++){
				
 				if(NowTVOtherList.get(i).getMDOInd().equals("M")){
 					checkStr="checked";
 					disableStr="disabled";
 				}else if(NowTVOtherList.get(i).getMDOInd().equals("D")){
 					checkStr="checked";
 					disableStr="";
 				}else{
 					disableStr="";
 				}
				
				if(i==0){
					Is_New_Title = true;
				}else if(!NowTVOtherList.get(i).getGrpDesc().equals(NowTVOtherList.get(i-1).getGrpDesc())){
					Is_New_Title = true;
				}else{
					Is_New_Title = false;
				}
 							
				if(Is_New_Title){
					out.println("<tr><td colspan= '3' class='table_title' align='left'>"+
						NowTVOtherList.get(i).getGrpDesc()+
						"<br></td>"+
						"<td class='table_title' align='right' width='10%'></td>"+
						"<td class='table_title' align='right' width='10%'></td></tr>");
					Is_New_Title = false;
				}
				if(SelectedItemList!=null ){
		 			checkStr = "";
		 			display = "none";
					for(int l=0; l< SelectedItemList.length; l++){	
						if(SelectedItemList[l].equals(NowTVOtherList.get(i).getItemID())){
							checkStr = "checked";
							display = "inline";
							break;
						}
					}	
				}
		
				if(NowTVOtherList.get(i).getItemType().equals("NTV_OTHER")){
					id_str = NowTVOtherList.get(i).getTVType()+"HomePlug";
					item_type = "nowTVOtherItem";
					HDtagO = "";
					HDtagC = "";
				}else if(NowTVOtherList.get(i).getItemType().equals("NTV_HD")){
					id_str = NowTVOtherList.get(i).getTVType()+"Activation";
					item_type = "nowTVHDItem";
					HDtagO = "<font color='#01A9DB'>";
					HDtagC = "</font>";	
				}else if(NowTVOtherList.get(i).getItemType().equals("NTV_STB")){
					id_str = NowTVOtherList.get(i).getTVType()+"Activation";
					item_type = "nowTVSTBItem";
					HDtagO = "";
					HDtagC = "";
				}
				else{
					id_str = NowTVOtherList.get(i).getTVType();
					item_type = "nowTVOtherItem";
					HDtagO = "";
					HDtagC = "";
				}
					
				
				out.println("<td valign='top'  width='10'><input type='checkbox' name='"+
						item_type+"' value='"+
						NowTVOtherList.get(i).getItemID()+
						"' id='"  + id_str +
						"' onclick='checkAdditionChannel()' "+checkStr+"></td><td colspan= '1' valign='top'>"+
						"<span class='item_title_vas'>"+
						HDtagO + NowTVOtherList.get(i).getVASTitle() + HDtagC+
						"</span>");
				out.println("<div id='VASDtl" +
						NowTVOtherList.get(i).getItemID()+
						"' style=display:none"+
						"><DIR><span class='item_detail'>"+
						NowTVOtherList.get(i).getVASDetail()+
						"</span></DIR></div></td>");
				out.println("<td  colspan= '2' align='right' valign='top' class='BGgreen2' width='10%'>"+
						NowTVOtherList.get(i).getRecurrentAmt()+
						"</td><td align='right' valign='top' class='BGgreen2' width='10%'>"+
						NowTVOtherList.get(i).getMthToMthRate()+"</td></tr>");
						
			}
		}

%>						
		</table>
		
		<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
		<tr>
        	<td align="left" width="25%">Decoder : <span id="decodeTypeID">${nowTVdetailinfo.decodeType}</span></td>
			
			<form:hidden path="decodeType" />
			<td align="left">
			<c:if test="${nowTVdetailinfo.isAdult == 'Y' }">
				<input type="checkbox" id="AVSelect" name="AVSelect" value="AVSelect" checked/>
			</c:if>
			<c:if test="${nowTVdetailinfo.isAdult == 'N' }">
				<input type="checkbox" id="AVSelect" name="AVSelect" value="AVSelect"/>
			</c:if>
				I wish to view AV/adult channel preview</td>
				
		</tr>
		<tr>
			<td></td><td colspan='3'><div id="warning_AVchannel">
			
			</div></td>
		</tr>
		
		<c:if test='${fn:length(nowTVdetailinfo.nowTVEntDescList) != 0}'>
		<tr>
		<td colspan="3">
		<!-- Channel Selection Table -->
			<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>

				<td class="table_title" >Channel Selection - Entertainment Bundle (<%= ContractPeriod %> Month Contract)</td>

			</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" class="contenttext" background="images/background2.jpg">
			<tr>
				<td>
<%
		System.out.println("Making Entertainment Pack List in imsnowtv.jsp");

		List<NowTVVasUI> EntDescList = NowTVDetail.getNowTVEntDescList();

		for(int k=0;k<EntDescList.size();k++)
		{
			if(SelectedChannelList!=null ){
 				checkStr = "";
				for(int l=0; l< SelectedChannelList.length; l++){	
					if(SelectedChannelList[l].equals(EntDescList.get(k).getChannelID())){
						checkStr = "checked";
						break;
					}
				}	
			}	
			out.println("<input type='checkbox' name='EntItem' value='"+
					EntDescList.get(k).getChannelID()+"' onclick='EntCheck()' "+
					checkStr+">" +EntDescList.get(k).getChannelDesc()+"<br>");
		}
%>
				</td>
			</tr>
		</table>
		<!-- End Channel Selection Table -->
		</td>
		</tr>
		</c:if>
		
		<!-- HD Channel Selection Table -->
		<c:if test='${fn:length(nowTVdetailinfo.nowTVHDList) != 0}'>
		<tr>
		<td colspan="3">
		<c:choose>
			<c:when test='${fn:length(selectedIMSNowVasList) > 0}'>
				<div id="Free2HD" style=display:inline>		
			</c:when>
			<c:otherwise>
				<div id="Free2HD" style=display:none>
			</c:otherwise>
		</c:choose>
		<table width="100%" border="0" cellspacing="1" rules="none">
			<c:forEach items="${nowTVdetailinfo.nowTVHDList}" var="nowTVHDTitle" varStatus="status">
				<c:set var="freeHDTitle" scope="session" value="${nowTVHDTitle.groupDesc}"/>
			</c:forEach>
			<tr>
				<td class="table_title" >${freeHDTitle}</td>
			</tr>
			</table>
			<div>
			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
			<tr><td colspan="2"></td></tr> 
				<!-- Two column layout of channel table -->
				<tr>
					<td width="50%" valign="top">
						<table width="100%" border='0' cellspacing='1' class='contenttext'>				
							<tr>
								<td width="6%"></td>
								<td width="20%">Ch No.</td>
								<td>Channel Name</td>
							</tr>
							<c:forEach items="${nowTVdetailinfo.nowTVHDList}" var="nowTVHD" varStatus="status">
							<c:if test='${nowTVHD.MDOInd == "M" }'>
							<tr class='item_detail' valign='top'>								
								<td align='left'>
									<input type="checkbox"  id='FreeHD${nowTVHD.channelID}' type='checkbox' value='${nowTVHD.channelID}' title='0' checked disabled/><br>
									<input style=display:none type="checkbox"  id='FreeHDhidden${nowTVHD.channelID}' type='checkbox' name='FreeHDchannelItem' value='${nowTVHD.channelID}' checked /><br>
									
									<c:set var="FreeHDM" scope="session" value="${nowTVHD.channelID}"/>
								</td>
								<td align='left'>${nowTVHD.channelCD}</td>
								<td align='left' colspan='3'>
									<b><font color='#01A9DB'>${nowTVHD.channelDesc}</font></b>
								</td>
							</tr>
							</c:if>
							</c:forEach>
						</table>
					</td>
					<td width='50%' valign='top'>
						<table width='100%' border='0' cellspacing='1' class='contenttext'>
							<tr>
								<td width="6%"></td>
								<td width="20%">Ch No.</td>
								<td>Channel Name</td>
							</tr>
							<c:forEach items="${nowTVdetailinfo.nowTVHDList}" var="nowTVHD" varStatus="status">
							<c:if test='${nowTVHD.MDOInd == "O" }'>
							<tr class='item_detail' valign='top'>
								<td align='left'>
									<c:set var="checkFree" scope="session" value=""/>
									<c:if test='${fn:length(selectedIMSChannelList) != 0}'>
										<c:forEach items="${selectedIMSChannelList}" var="selectedIMSChannel" varStatus="chStatus">
											<c:if test='${selectedIMSChannel == nowTVHD.channelID}'>
												<c:set var="checkFree" scope="session" value="checked"/>
											</c:if>
										</c:forEach>
									</c:if>
									<input type="checkbox" id='FreeHD${nowTVHD.channelID}' name='FreeHDchannelItem' value='${nowTVHD.channelID}' title='0' onclick="FreeHDCheck()" ${checkFree}/>
								</td>
								<td align='left'>${nowTVHD.channelCD}</td>
								<td align='left' colspan='3'>
									<b><font color='#01A9DB'>${nowTVHD.channelDesc}</font></b>
								</td>
							</tr>
							</c:if>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
			</div>
		
		</td>
		</tr>
		</c:if>
		<!-- End HD Channel Selection Table -->
		<tr>
		<td colspan="3">
		<!-- Additional Channel Selection Table -->
			<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
				<td class="table_title" >Additional Channel Selection - (<%= ContractPeriod %> Month Contract)</td>
			</tr>
			</table>
			
			<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
			<td colspan="2">
				<form:errors path="exclusiveError" cssClass="error" />
			</td>
			</tr>
			<tr>
			<td colspan="2">
				<form:errors path="channelError" cssClass="error" />
			</td>
			</tr>
			</table>
			
			<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
				<!-- Two column layout of channel table -->
				<tr><td colspan="2"></td></tr> 
<% 
	System.out.println("Making Channel List in imsnowtv.jsp");

	List<ChannelUI> ChannelHDList = NowTVDetail.getNowTVChannelList();
	List<ChannelUI> ChannelList = new ArrayList<ChannelUI>();
	
	ChannelUI ChannelTempItem;
	
	ChannelUI ChannelItem;
	String ChannelGRPFrag = "";  

	boolean BreakCheck = false;
	boolean breakDone = false;
	String ChannelType = "";
	String ChannelItemName = "";
	String HDTagStart = "";
	String HDTagEnd = "";
 	String ChildCh = "";
	String ChannelPrint = "";
	String ChildTVType = "";
	
	ChannelList = ChannelHDList;
	
/* 	boolean HDAct = false;
	
	if(NowTVDetail.getHDAct()!=null){
		if(NowTVDetail.getHDAct().equals("Y")){
			HDAct = true;
		}else HDAct = false;
	}else HDAct = false;
	
	if(!HDAct){
		for(int i =0;i<ChannelHDList.size();i++){
			if(!ChannelHDList.get(i).getTVType().equals("HD")){
				if(ChannelHDList.get(i).getChildChTVType()!=null&&ChannelHDList.get(i).getChildChTVType().equals("HD")){
					ChannelTempItem = new ChannelUI();
		        	ChannelTempItem.setChannelID(ChannelHDList.get(i).getChannelID());
					ChannelTempItem.setChannelCD(ChannelHDList.get(i).getChannelCD());
					ChannelTempItem.setCredit(ChannelHDList.get(i).getCredit());
		        	ChannelTempItem.setIsAdult(ChannelHDList.get(i).getIsAdult());
		        	ChannelTempItem.setGroupDesc(ChannelHDList.get(i).getGroupDesc());
		        	ChannelTempItem.setChannelDesc(ChannelHDList.get(i).getChannelDesc());
		        	ChannelTempItem.setMDOInd(ChannelHDList.get(i).getMDOInd());
		        	ChannelTempItem.setTVType(ChannelHDList.get(i).getTVType());
		        	ChannelList.add(ChannelTempItem);
		        	
				}else ChannelList.add(ChannelHDList.get(i));
			}
		}
	}else ChannelList = ChannelHDList; */
	
	int ListCount = ChannelList.size();
	int ListBreak = ListCount/2 - ListCount/5;
	
	boolean Is_Same_channel = false;
	
	if(ChannelList!=null&&ListCount>0){ 
 		int i=0; 
 		out.println(
 				"<tr><td width='50%' valign='top'>"+
 					"<table width='100%' border='0' cellspacing='1' class='contenttext'>");
		out.println("<tr><td width='25px'></td><td width='80px' >Ch No.</td><td>Channel Name</td></tr>");
		
 		while(i<ListCount){ 
 			ChannelItem = (ChannelUI)ChannelList.get(i); 
 			checkStr = "";
 			disableStr ="";
 			
			if(i==0){
				Is_Same_channel = false;
			}else if(ChannelList.get(i).getChannelID().equals(ChannelList.get(i-1).getChannelID())){
				Is_Same_channel = true;
			}else{
				Is_Same_channel = false;
			}
 			
 			
 			if(SelectedChannelList!=null && (TVList == null || !TVList.equals("deselect"))){
 				
				for(int l=0; l< SelectedChannelList.length; l++){	
					if(SelectedChannelList[l].equals(ChannelItem.getChannelID())){
						checkStr = "checked";
						break;
					}else checkStr = "";
				
				}	
			}	
 			
 			if(ChannelItem.getChildChID()!=null){
 				if(ChannelItem.getChildChTVType().equals("HD")){
 					ChildCh = "<tr name='HD_child' class='item_detail' valign='top'><td align='left'></td>"+
 					"<td align='left' width='80px'>" +
 					"<input id='Child" + ChannelItem.getChannelID() + "' type='checkbox' name='childHDItem' value='Child" + ChannelItem.getChannelID() +
 					"' " +checkStr+ " style=display:none />"
 					+ ChannelItem.getChildChCD() +"</td><td align='left'><b><font color='#01A9DB'>"+
 					ChannelItem.getChildChDesc() + "</font></b></td></tr>";
 					ChildTVType = "HD";
 				}
 				else{
 					ChildCh = "<tr class='item_detail' valign='top'><td align='left'></td>"+
 					"<td align='left' width='80px'>" //+
 					//"<input id='Child" + ChannelItem.getChannelID() + "' type='checkbox' name='childHDItem' value='Child" + ChannelItem.getChannelID() +
 					//"' " +checkStr+ "/>"
 					+ ChannelItem.getChildChCD() +"</td><td align='left'><b>" +
 					ChannelItem.getChildChDesc() + "</b></td></tr>";
 					ChildTVType = "SD";
 				}
 				//ChildCh = ChildCh + "<input style=display:none type='checkbox' name='"+ ChannelItemName +"' value='"+ ChannelItem.getChildChID() +
				//	"' title='0' onclick='checkAdditionChannel()'/>";
 			}else ChildCh = "";
 			
 			if(ChannelItem.getTVType().equals("HD")){
				HDTagStart = "<font color='#01A9DB'>";
				HDTagEnd = "</font>";
				ChannelItemName = "HDchannelItem";
			}else{
				HDTagStart = "";
				HDTagEnd = "";
				if(ChannelItem.getIsAdult().equals("Y")){
					ChannelItemName = "AVchannelItem";
				}else{
					ChannelItemName = "channelItem";
				}
			}
 			
 			
 			
 			if((!(ChannelItem.getGroupDesc().equals(ChannelGRPFrag)))&&i<ChannelList.size()){ 
		
 				if(ChannelItem.getMDOInd().equals("M")){
 					checkStr="checked";
 					disableStr="disabled";
 				}else if(ChannelItem.getMDOInd().equals("D")){
 					checkStr="checked";
 					disableStr="";
 				}else{
 					disableStr="";
 				}
 				if(BreakCheck&&!breakDone){
 					out.println("</table></td><td width='50%' valign='top'><table width='100%' border='0' cellspacing='1' class='contenttext'>");
					out.println("<tr><td width='25px'></td><td id='tda' width='80px'>Ch No.</td><td>Channel Name</td></tr>");
					breakDone = true;
 				}
				//print Category title
				out.println("<td valign='top' bgcolor='#EAEAEA' colspan='3'>"+
					"<span class='item_title_vas'>"+ ChannelItem.getGroupDesc() +"</span></td></tr>");
				ChannelGRPFrag = ChannelItem.getGroupDesc();
				if(i>=ListBreak){
					BreakCheck = true;
				}
				if(!Is_Same_channel){
 					out.println(
 						"<tr class='item_detail' valign='top'><td align='left'>"+
 						"<input type='checkbox' name='"+ChannelItemName+"' value='"+ ChannelItem.getChannelID() +
 						"' title='"+ChannelItem.getCredit()+"' " +checkStr+" onclick='checkAdditionChannel()' " +disableStr+"/></td>"+
 						"<td align='left' width='80px'>"+ ChannelItem.getChannelCD() +"</td><td align='left'><b>"+
 						HDTagStart + ChannelItem.getChannelDesc() + "</b></td></tr>");
				}
 				out.println(HDTagEnd + ChildCh);

 			}else{
 				if(ChannelItem.getMDOInd().equals("M")){
 					checkStr="checked";
 					disableStr="disabled";
 				}else if(ChannelItem.getMDOInd().equals("D")){
 					checkStr="checked";
 					disableStr="";
 				}else{
 					disableStr="";
 				}
 				if(!Is_Same_channel){
 						out.println(
 								"<tr class='item_detail' valign='top'><td align='left'>"+
 								"<input type='checkbox' name='"+ChannelItemName+"' value='"+ ChannelItem.getChannelID() +
 								"' title='"+ChannelItem.getCredit()+"' " +checkStr+" onclick='checkAdditionChannel()' " +disableStr+"/></td>"+
 								"<td align='left' width='80px'>"+ ChannelItem.getChannelCD() +"</td><td align='left'><b>"+
 								HDTagStart + ChannelItem.getChannelDesc() + "</b></td></tr>");
 				}
 		 				out.println(HDTagEnd + ChildCh);

 			}		
			
 			i++;
 		}
 		out.println("</table></td></tr>");
	}
%>
			</table>
		<!-- Additional Channel Selection Table -->
		</td>
		</tr>
		<tr>
			<td colspan="2"></td>
			<td align="right">
				<div>
					<a href="#" class="nextbutton" onclick="formSubmit()">Continue > </a>
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