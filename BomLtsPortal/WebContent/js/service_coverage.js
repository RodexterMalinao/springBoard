var hide_pos = "-180";
var show_pos = "95";
var show_dist_action = null;
var newWindow="";
var newWindow2="";

function initDistrictPanel(){
	var outter; 
	var sPage = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
	if (sPage == 'coveragecheck.html') {
		outter = document.getElementById("map");
		} 
	else if (sPage == 'pcdregistration.html'){ 
		outter = document.getElementById("map");
		hide_pos = "-100";
		show_pos = "140";
		}
	 
	var btn = document.getElementById("search_by_district_btn");
	var offset = $(btn).offset(); 
	var panel = document.getElementById("search_by_district_panel");
	var myWidth = 0; 
	var live_chat = document.getElementById("live_chat");
		myWidth =$(outter).outerWidth();
	panel.style.position="absolute";
	panel.style.top = hide_pos+"px"; 
	panel.style.left = (myWidth - $(panel).outerWidth())+"px"; 
	if (sPage == 'pcdregistration.html') {panel.style.left = offset.left-319+"px";}
	panel.style.zindex="0";
	live_chat.style.top = "7px";
	live_chat.style.left = (myWidth - $(live_chat).outerWidth() - 7)+"px";
	panel.style.display = "block";
	live_chat.style.display="block";
}

function openWindowWithPost3(title, locale){
	//alert("openWindowWithPost3");
	var form = document.createElement("form");
	
	//if		(locale=="en"){var url = "http://www.netvigator.com/registration_1209/form_e.php";}
	//else if (locale=="zh"){var url = "http://www.netvigator.com/registration_1209/form.php";}
	url=Hos_Ph_Pon_link;
	//alert(url);
	form.setAttribute("method", "post");
	form.setAttribute("action", url);
	
    var hiddenField = document.createElement("input");   
    hiddenField.setAttribute("type", "hidden");
    hiddenField.setAttribute("name", "address");
    hiddenField.setAttribute("value", title);
    //hiddenField.setAttribute("address", title);
    form.setAttribute("target", "_blank");
    form.appendChild(hiddenField);
    document.body.appendChild(form);               
    form.submit();
    //http://stackoverflow.com/questions/133925/javascript-post-request-like-a-form-submit
}

//function openWindowWithPost(title, locale) { 
//	var temp = encodeURI(title);
//	//alert(temp);
//	if		(locale=="en"){var url = "http://www.netvigator.com/registration_1209/form_e.php";}
//	else if (locale=="zh"){var url = "http://www.netvigator.com/registration_1209/form.php";}
//	var html = "";
//	html += "<html><head></head><body><form id='formid' method='post' action='" + url + "'>";
//    html += "<input type='hidden' name=address value='" + temp + "'/>";
//	html += "</form><script type='text/javascript'>document.getElementById(\"formid\").submit()</script></body></html>";
//	if (newWindow && !newWindow.closed) {
//      	newWindow.close(); 
//	}
//    newWindow = window.open(url, "dummy_name", "height=805, width=1024, location=no, scrollbars=no, menubar=no, status=no,toolbar=no");
//    newWindow.document.write(html);
//    alert("end of openWindowWithPost");
//}//http://www.dotblogs.com.tw/puma/archive/2008/09/03/5288.aspx


function ShowOldSalesLeadForm(type,marker_idx){
	var layer_form = document.getElementById('form_bg');
	var form_ = document.getElementById('form_');
	var url = [];
	var _lang = "en";
	if (lang=="ch")
		_lang = "zh";
	
	switch (type)
	{
		case "now":
			form_.className="now_form";
			url.push("serviceregnowtv.html?type=net");
			break;
		case "net_checked":
			form_.className="net_form";
			url.push("servicereg.html?type=net");
			break;
		case "net_checked_isExistingCust":
			form_.className="net_form";
			url.push("servicereg.html?type=net&upgradeExistService=Y");
			break;
		case "net_b":
			form_.className="net_form_biz";
			url.push("serviceregbiz.html?type=net");
			break;
		case "now_b":
			form_.className="now_form";
			url.push("serviceregnowtvbiz.html?type=net");
			break;
	}
	
	url.push("&marker_idx="+marker_idx);
	url.push("&lang_preference="+_lang);
	url.push("&channel="+channel);
	document.getElementById("service_reg_form").src=url.join('');
	layer_form.style.display = 'block';
	form_.style.display = 'block';
	
}

function showTwoOptions(marker_idx, button){
	var layer_form = document.getElementById('form_bg');
	var form_ = document.getElementById('form_');
	var url = [];
	var _lang = "en";
	if (lang=="ch") 
		_lang = "zh"; 
	var mywindow;
//	var sPath = window.location.pathname;
//	var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
	form_.className="net_form_biz";
//	url.push("temp_ph_pos_pon.html");
//	url.push("?lang_preference="+_lang);
//	url.push("&marker_idx="+marker_idx);
	
	url.push("regaddressinfo.html");
	//url.push("?lang_preference="+_lang);
	//url.push("&channel="+channel);
	url.push("?marker_idx="+marker_idx);
	url = url.join('');
	//document.getElementById("service_reg_form").src=url.join('');
	layer_form.style.display = 'none';
	form_.style.display = 'none';
	
	
	$('.cbutton').hide();
	$('.cbutton').fadeIn('quick', function () {});
	
	
	var newC = $(button).parent().parent().parent().parent().parent().children('#NewC');
	var oldC = $(button).parent().parent().parent().parent().parent().children('#OldC');
	
	newC.css('top',0);
	newC.css('left',0);
	oldC.css('top',0);
	oldC.css('left',0);
	var y = $(button).offset().top-newC.offset().top+30; 
	var x = $(button).offset().left-newC.offset().left-60;
	
	newC.css('top', y);
	newC.css('left', x); 
	oldC.css('top', y+$(newC).outerHeight()); 
	oldC.css('left', x);
	 
	newC.click(function() { 
		mywindow = window.open(url, "Registration", "scrollbars=1,directories=0,titlebar=0,toolbar=0, location=0,status=0,menubar=0, height=768, width=1050");
		mywindow.moveTo(100, 100);
	}); 
	oldC.click(function() { 	
		ShowOldSalesLeadForm("net_checked_isExistingCust", marker_idx);
	});
	
}

function showForm(type, marker_idx, button){
	hideForm();
	
	var sPath = window.location.pathname;
	var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
	
	if (sPage == "pcdregistration.html") {
		 
		if(isUpgrade =="Y"){
			ShowOldSalesLeadForm("net_checked_isExistingCust", marker_idx);
		}
		else {
		
		var layer_form = document.getElementById('form_bg');
		var form_ = document.getElementById('form_');
		var url = [];
		var _lang = "en";
		if (lang=="ch") 
			_lang = "zh"; 
		
		//form_.className="net_form_biz";

		$.ajax({
			url : 'getaddresstype.html?marker_idx=' + marker_idx + "&channel=" + "Netvagator",
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    if(textStatus=='parsererror'){
			        alert("Your session has been timed out, please try again."); 
			    }else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
			    }
			},
			success : function(msg) {
				var item =  eval(msg)[0];
				var IsPremier = item.IsPremier;
				var IsRm = item.IsRm;
				var IsPonOnly = item.IsPonOnly;
				var isPh = item.isPh;
				var isHos = item.isHos;
				var disp_address = item.disp_address;
				var isCleanedVillage = item.isCleanedVillage;
				var _lang = "en";
				if (lang=="ch") 
					_lang = "zh"; 
				
//				alert("marker_idx: " + marker_idx + "\nIsPremier: " + IsPremier + "\nIsRm: " 
//						+ IsRm + "\nIsPonOnly: " + IsPonOnly + "\nisPh: " + isPh
//						+ "\nisHos: " +isHos);	

					if (IsPremier == "Y" || IsRm == "Y"||isCleanedVillage=="N"){
							$("html").css("overflow", "hidden");
							ShowOldSalesLeadForm("net_checked", marker_idx);
					}else{
						url.push("regaddressinfo.html");
						url.push("?marker_idx="+marker_idx);
						url = url.join('');
						layer_form.style.display = 'none';
						form_.style.display = 'none';
						window.location.replace(url);
					}
				
			}
		
		});
	}
	}else{
		if(type=="net"){
			$.ajax({
				url : 'getaddresstype.html?marker_idx=' + marker_idx + "&channel=" + channel,
				type : 'POST',
				dataType : 'json',
				timeout : 60000,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				    if(textStatus=='parsererror'){
				        alert("Your session has been timed out, please try again."); 
				    }else if(textStatus == 'timeout'){
				    	alert("Server response timeout, please try again.");
				    }
				},
				success : function(msg) {
					var item =  eval(msg)[0];
					var IsPremier = item.IsPremier;
					var IsRm = item.IsRm;
					var IsPonOnly = item.IsPonOnly;
					var isPh = item.isPh;
					var isHos = item.isHos;
					var disp_address = item.disp_address;
					var _lang = "en";
					if (lang=="ch") 
						_lang = "zh"; 
					
//					alert("marker_idx: " + marker_idx + "\nIsPremier: " + IsPremier + "\nIsRm: " 
//							+ IsRm + "\nIsPonOnly: " + IsPonOnly + "\nisPh: " + isPh
//							+ "\nisHos: " +isHos);	
					
					if (IsPremier == "Y" || IsRm == "Y"){
							$("html").css("overflow", "hidden");
							ShowOldSalesLeadForm("net_checked", marker_idx);
					}else{
						//show 2 options
						showTwoOptions(marker_idx, button);
					}
				
				}
			});
		}else{
			ShowOldSalesLeadForm(type, marker_idx);
		}
		
	}
	
}

function showHotline(type){
	hideForm();
	var layer_form = document.getElementById('form_bg');
	var form_ = document.getElementById('form_');
	var url = [];
	var _lang = "en";
	form_.className="hotline_form";
	if (type=="now"){
		url.push("hotlinenow.html");
	}else if (type=="net"){
		url.push("hotlinenet.html");
	}else if (type=="net_b"){
		url.push("hotlinenetbiz.html");
	}else if (type=="now_b"){
		url.push("hotlinenowbiz.html");
	}else if (type=="netRm"){
		url.push("hotlinenet.html");
	}else if (type=="netPremier"){
		url.push("hotlinenet.html");
	}
	url.push("?channel="+channel);
	if (type=="netRm"){
		url.push("&RmPremier=Rm");
	}else if (type=="netPremier"){
		url.push("&RmPremier=Premier");
	}
	document.getElementById("service_reg_form").src=url.join('');
	//document.getElementById("service_reg_form").src=url;
//	window.open(url);
	layer_form.style.display = 'block';
	form_.style.display = 'block';
}

function change_form_type_for_result(){
		document.getElementById("service_reg_form").src = "";
		var layer_form = document.getElementById('form_bg');
		var form_ = document.getElementById('form_');
		layer_form.style.display = 'block';
		form_.style.display = 'block';
		form_.className="hotline_form";
}

//function showForm(type, item, area_en, area_ch, distr_cd, district_en, district_ch, section_en, section_ch, housingAddress_en, housingAddress_ch, build_xy, isPremier, isPon) {
//	
////	alert("type:"+type+", item:"+ item+", area_en:"+ area_en+", area_ch:"+ area_ch+", district_en:"+ district_en+", district_ch:"+ district_ch+", section_en:"+ section_en+", section_ch:"+ section_ch+", housingAddress_en:"+ housingAddress_en+", housingAddress_ch:"+ housingAddress_ch+", build_xy:"+ build_xy+", isPremier:"+ isPremier+", isPon:"+ isPon);
//	var layer_form = document.getElementById('form_bg');
//	var form_ = document.getElementById('form_');
//	var url = "";
//	var _lang = "en";
//	if (type=="now"){
//		form_.className="now_form";
//		url = "serviceregnowtv.html?type=net";
//	}else if (type=="net"){
//		form_.className="net_form";
//		url = "servicereg.html?type=net";
//	}else if (type=="net_b"){
//		url = "servicereg.html?type=net";
//	}
//	if (lang=="ch")
//		_lang = "zh";
//	url+="&item_id=" +encodeURIComponent(item)+
//	"&area_desc_en=" +encodeURIComponent(area_en)+
//	"&area_desc_ch=" +encodeURIComponent(area_ch)+
//	"&distr_cd=" +encodeURIComponent(distr_cd)+
//	"&district_desc_en=" +encodeURIComponent(district_en)+
//	"&district_desc_ch=" +encodeURIComponent(district_ch)+
//	"&section_desc_en=" +encodeURIComponent(section_en)+
//	"&section_desc_ch=" +encodeURIComponent(section_ch)+
//	"&housing_addr_en=" +encodeURIComponent(housingAddress_en)+
//	"&housing_addr_ch=" +encodeURIComponent(housingAddress_ch)+
//	"&build_xy=" +encodeURIComponent(build_xy)+
//	"&is_premier=" +encodeURIComponent(isPremier)+
//	"&is_pon_only=" +encodeURIComponent(isPon)+
//	"&lang_preference="+encodeURIComponent(_lang)+
//	"&channel="+channel;
////	alert(url);
//	document.getElementById("service_reg_form").src=url;
////	window.open(url);
//	layer_form.style.display = 'block';
//	form_.style.display = 'block';
//}

function hideForm() {
	var layer_form = document.getElementById('form_bg');
	var form_ = document.getElementById('form_');
	layer_form.style.display = 'none';
	form_.style.display = 'none';
	document.getElementById("service_reg_form").src = "";
	$("html").css("overflow", "scroll");
}

function closeDistSch(){
	var panel = document.getElementById("search_by_district_panel");
	var btn = document.getElementById("search_by_district_btn");
	if (panel.offsetTop > hide_pos){
		clearTimeout(show_dist_action);
		show_dist_action = null;
		btn.childNodes[0].src=district_btn_off;
		setTimeout("slideDown(-5)", 30);
		$('#button_clicked').hide();
	}
}

function show_search_dist() {
	var sPage = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
	var panel = document.getElementById("search_by_district_panel");
	var btn = document.getElementById("search_by_district_btn");
	var offset = $(btn).offset(); 
	
	if (panel.offsetTop ==hide_pos) {
		btn.childNodes[0].src=district_btn_on;
		show_dist_action=setTimeout("slideDown(5)", 30);
		if (sPage == 'regcoveragecheck.html') {panel.style.left = offset.left-319+"px";}
		$('#button_clicked').show();
	} else if (panel.offsetTop == show_pos){
		btn.childNodes[0].src=district_btn_off;
		show_dist_action=setTimeout("slideDown(-5)", 30);
		$('#button_clicked').hide();
	}
}

function slideDown(inc) {
	var obj = document.getElementById("search_by_district_panel");
	if (inc > 0 && obj.offsetTop < show_pos || inc < 0 && obj.offsetTop > hide_pos) {
		obj.style.top = (obj.offsetTop + inc)+"px";
		show_dist_action=setTimeout("slideDown(" + inc + ")", 30);
	}
}

function legendControl(type) {
	var now_icon = document.getElementById("now_icon");
	var net_icon = document.getElementById("net_icon");
	var net_busi_icon = document.getElementById("net_busi_icon");
	gotoMarkerIdx=null;
	if (activeBubble) {
		activeBubble.close();
		activeBubble = null;
	}
	if (type == 'now') {
		if (showNow) {
			now_icon.src = legend_now_off;
			showNow = false;
		} else {
			now_icon.src = legend_now_on;
			showNow = true;
		}
	} else if (type == 'net') {
		if (showNet) {
			net_icon.src = legend_net_off;
			showNet = false;
		} else {
			net_icon.src = legend_net_on;
			showNet = true;
		}
	} else if (type == 'net_b') {
		if (showNetB) {
			net_busi_icon.src = legend_netb_off;
			showNetB = false;
		} else {
			net_busi_icon.src = legend_netb_on;
			showNetB = true;
		}
	}
	loadMarkers(markers);
}

function openChat(type){
	window.open(live_chat_link, "livechat", "height=650, width=800, location=no, scrollbars=no, menubar=no, status=no,toolbar=no");
	var layer_form = document.getElementById('form_bg');
	var form_ = document.getElementById('form_');
	var url = [];
	var _lang = "en";
	if (type=="now"){
		form_.className="now_form";
		url.push("livechatlognow.html?action=RES_NOWTV_OL_CHAT");
	}else if (type=="net_b"){
		form_.className="net_form_biz";
		url.push("livechatlognetbiz.html?action=BUS_NET_OL_CHAT");
	}else if (type=="now_b"){
		form_.className="now_form";
		url.push("livechatlognowbiz.html?action=BUS_NOWTV_OL_CHAT");
	}else if (type=="net"){
		form_.className="net_form";
		url.push("livechatlognet.html?action=RES_NET_OL_CHAT");
	}
	url.push("&channel="+channel);
	document.getElementById("service_reg_form").src=url.join('');
	layer_form.style.display = 'none';
	form_.style.display = 'none';
	//hideForm(); 
}

function open_chat(){
	mywindow = window.open ("./images/live_chat_fake.PNG","live_chat","height=681, width=805, location=no, scrollbars=no, menubar=no, resizable=0, status=no,toolbar=no");
	mywindow.moveTo(400, 100);
}

var reg_mywindow = false;

function reg_open_chat(url){
	if(reg_mywindow && !reg_mywindow.closed){
		reg_mywindow.focus();
	}else{

	    var reg_live_chat_link = url;
	    var layer_form = document.getElementById('form_bg');
		var form_ = document.getElementById('form_');
		var url = [];
		var _lang = "en";
		var type = srvType;
		
		callajaxlivechatlog(srvType);
		
		reg_mywindow = window.open(reg_live_chat_link, "livechat", "height=673, width=410, location=no, scrollbars=no, menubar=no, status=no,toolbar=no");
	    reg_mywindow.moveTo(400, 100);
	    
	    
		
//		if (type=="DEL"){
//			form_.className="del_form";
//			url.push("livechatlog.html?action=RES_DEL_OL_CHAT");
//		}else if (type=="EYE"){
//			form_.className="eye_form";
//			url.push("livechatlog.html?action=RES_EYE_OL_CHAT");
//		}
//		url.push("&channel="+channel);
//		document.getElementById("service_reg_form").src=url.join('');
//		layer_form.style.display = 'none';
//		form_.style.display = 'none';

	}


	function callajaxlivechatlog(srvType){
		if(srvType == "DEL"){
			$.ajax({
				url : "livechatlog.html",
				type : 'POST',
				data : "action=RES_DEL_OL_CHAT" , //+ "&channel=" + channel,
				dataType : 'json',
				//timeout : 5000,
				success : function(data){
					
					return 1;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					return textStatus;	
				}
			});
		}
		
		if(srvType == "EYE"){
			$.ajax({
				url : "livechatlog.html",
				type : 'POST',
				data : "action=RES_EYE_OL_CHAT" , //+ "&channel=" + channel,
				dataType : 'json',
				//timeout : 5000,
				success : function(data){
					
					return 1;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					return textStatus;	
				}
			});
		}
	}
}


