var iconNowTv = new google.maps.MarkerImage("./images/now_marker.png");
var iconNetvigator = new google.maps.MarkerImage("./images/net_marker.png");
var iconShadow = new google.maps.MarkerImage("./images/shadow_marker.png");
var initLat = 22.380208;
var initLng = 114.135132;
var zoomArray = [ 11, 13, 16, 19, 20 ];
var open_msg_window_level = 4;
var adjust_latlngArray = [ 0.008, 0.002, 0.0004, 0.00005, 0.00004 ];
var adjust_latlng = adjust_latlngArray[0];
var lastZoomLevel = zoomArray[0];
var map = null;
var mm = null; 
var showNow = true;
var showNet = true;
var showNetB = true;
var markers = [];
var activeBubble = null;
var markers_d = [];
var activeAction = null;
var lastBound = null;
var goToLat = null;
var goToLng = null;
var gotoMarkerIdx = null;
var startTime;
var endTime;

function init() {
	var latlng = new google.maps.LatLng(initLat, initLng);
	var myOptions = {
		zoom : zoomArray[0],
		minZoom : zoomArray[0],
		maxZoom : zoomArray[4],
		center : latlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		 mapTypeControlOptions: {
		      mapTypeIds: [google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.SATELLITE]
		    },
		overviewMapControl : false,
		streetViewControl : false
//		,
//		panControl: false
	};

	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

	google.maps.event.addListener(map, "zoom_changed", function() {
		if (activeBubble) {
			activeBubble.close();
			$("#map_disclaimer").hide();
			activeBubble = null;
		}
		var d = map.getZoom();
		
		if (lastZoomLevel != d) {
			
		if (d >= zoomArray[0] && d < zoomArray[1]) {
			if (d > lastZoomLevel) {
				lastZoomLevel = zoomArray[1];
				map.setZoom(zoomArray[1]);
				adjust_latlng = adjust_latlngArray[1];
			} else if (d < lastZoomLevel){
				lastZoomLevel = zoomArray[0];
				map.setZoom(zoomArray[0]);
				adjust_latlng = adjust_latlngArray[0];
			}
		}else if (d > zoomArray[1] && d <= zoomArray[2]) {
			if (d > lastZoomLevel) {
				lastZoomLevel = zoomArray[2];
				map.setZoom(zoomArray[2]);
				adjust_latlng = adjust_latlngArray[2];
			} else if (d < lastZoomLevel){
				lastZoomLevel = zoomArray[1];
				map.setZoom(zoomArray[1]);
				adjust_latlng = adjust_latlngArray[1];
			}
		}else if (d > zoomArray[2] && d < zoomArray[3]) {
			if (d > lastZoomLevel) {
				lastZoomLevel = zoomArray[3];
				map.setZoom(zoomArray[3]);
				adjust_latlng = adjust_latlngArray[3];
			} else if (d < lastZoomLevel){
				lastZoomLevel = zoomArray[2];
				map.setZoom(zoomArray[2]);
				adjust_latlng = adjust_latlngArray[2];
			}
		}else if (d >= zoomArray[3] && d <= zoomArray[4]) {
//			if (d > lastZoomLevel) {
			if (d == zoomArray[4]){
				lastZoomLevel = zoomArray[4];
				adjust_latlng = adjust_latlngArray[4];
//			} else if (d < lastZoomLevel){
			}else if (d == zoomArray[3]){
				lastZoomLevel = zoomArray[3];
				adjust_latlng = adjust_latlngArray[3];
			}
		}
		}
		// for demo use only
		// if (d >= zoomArray[0] && d <= zoomArray[2]) {
		// map.setCenter(new google.maps.LatLng(22.286356, 114.213653));
		// }
		// End for demo use only
		if (activeAction) {
			clearTimeout(activeAction);
			actionAction = null;
		}
		activeAction = setTimeout(function() {
			getMarkers(map.getBounds().getNorthEast(), map.getBounds()
					.getSouthWest(), map.getZoom(), 'load');
		}, 500);
	});
	
	google.maps.event.addListener(map, 'center_changed', function() {
		if (activeAction) {
			clearTimeout(activeAction);
			actionAction = null;
		}
		activeAction = setTimeout(function() {
			if (getZoomLevel(map.getZoom()) == 5) {
				getMarkers(map.getBounds().getNorthEast(), map.getBounds()
						.getSouthWest(), map.getZoom(), 'load');
			}else{
				getMarkers(map.getBounds().getNorthEast(), map.getBounds()
						.getSouthWest(), map.getZoom(), 'refresh');
			}
		}, 500);
	});

	google.maps.event.addListener(map, 'click', function(event) {
		if (activeBubble) {
			activeBubble.close();
			$("#map_disclaimer").hide();
			activeBubble = null;
		} else if (map.getZoom() >= zoomArray[3]) {
			// }else{
			getInboundInfo(event.latLng);
		}
	});

	mm = new MarkerManager(map);
	google.maps.event.addListener(mm, 'loaded', function() {
		getMarkers(map.getBounds().getNorthEast(), map.getBounds()
				.getSouthWest(), map.getZoom(), 'load');
	});
}

function loadMarkers() {
	// alert("loadMarkers:"+markers.length);
	startTime = new Date();
	markers_d = [];
	for ( var i = 0; i < markers.length; i++) {
		marker = markers[i];
		var title;
		var type;
		title = marker.getMarkerTitle(lang);
//		alert(title);
		if ((marker.isNet() && showNet) || (marker.isNetB() && showNetB)) {
			var point;
			if (map.getZoom() == zoomArray[0] || map.getZoom() == zoomArray[1] || map.getZoom() == zoomArray[2]) {
				if (showNetB && !showNet && !showNow){
					point = new google.maps.LatLng(marker.getBusLat(), marker.getBusLng());
				}else{
					point = new google.maps.LatLng(marker.getResLat(), marker.getResLng());
				}
			}else{
				point = new google.maps.LatLng(marker.getLat(), marker.getLng());
			}
			var marker_d = new google.maps.Marker({
				position : point,
				title : title,
				icon : iconNetvigator,
				shadow : iconShadow
			});
			if (marker.isNet() && !marker.isNetB()){
				type="net";
			}else if (!marker.isNet() && marker.isNetB()){
				type="net_busi";
			}else if(marker.isNet() && marker.isNetB()){
				type="net_n_busi";
			}
			attachMessage(marker_d, type, marker);
			if (getZoomLevel(map.getZoom()) == 5 || (getZoomLevel(map.getZoom()) == 4 && (map.getMapTypeId()==google.maps.MapTypeId.SATELLITE ||map.getMapTypeId() == google.maps.MapTypeId.HYBRID))) {
//				if (goToLat==marker.getLat() && goToLng==marker.getLng()){
				if (gotoMarkerIdx ==marker.getMarker_idx()){
					if (!(channel=="NOWTV" && showNow)){
						attachGotoBubble(marker_d, marker);
						gotoMarkerIdx=null;
						goToLat = null;
						goToLng = null;
					}
				}
			}
			markers_d.push(marker_d);
		}
		if ((marker.isNow() && showNow)) {
			var point = new google.maps.LatLng(marker.getLat(), Number(marker
					.getLng())
					+ Number(adjust_latlng));
			var marker_d = new google.maps.Marker({
				position : point,
				title : title,
				icon : iconNowTv,
				shadow : iconShadow
			});
			if (marker.isNowB() && !marker.isNow()){
				type = "nowB";
			}else{
				if (marker.isNowB() && marker.isNow()){
					if (marker.getSf_bldg_bus() == "Y") {
						type = "nowB";
					} else {
						type = "now";
					}
				}else{
					type="now";
				}
			}
			attachMessage(marker_d, type, marker);
			if (getZoomLevel(map.getZoom()) == 5 || (getZoomLevel(map.getZoom()) == 4 && (map.getMapTypeId()==google.maps.MapTypeId.SATELLITE || map.getMapTypeId() == google.maps.MapTypeId.HYBRID))) {
				if (gotoMarkerIdx ==marker.getMarker_idx()){
//				if (goToLat==marker.getLat() && goToLng== marker.getLng()){
					attachGotoBubble(marker_d, marker);
					gotoMarkerIdx = null;
					goToLat = null;
					goToLng = null;
				}
			}
			markers_d.push(marker_d);
		}
	}
	gotoMarkerIdx=null;
	goToLat = null;
	goToLng = null;
	mm.clearMarkers();
	if (getZoomLevel(map.getZoom()) != 5) {
		mm.addMarkers(markers_d, map.getZoom());
	} else {
		mm.addMarkers(markers_d, zoomArray[3]);
	}
	lastBound = map.getBounds();
	mm.refresh();
	endTime =new Date();
//	alert("start:"+startTime+", end:"+endTime);
}

function getMarkerByLatLng(lat, lng){
//	alert(markers.length);
	for (var i=0;i<markers.length;i++){
		if (markers[i].getLat()==lat && markers[i].getLng() == lng)
			return markers[i];
	}
	return null;
}

function attachGotoBubble(marker, mymarker){
//	alert("123");
	var contentString = [];
	var address_div = '<div class="bubble_address">'+marker.title+'</div>';
	var item = "";
//	var disp_now = false;
//	var disp_nowB = false;
	var disp_net = true;
//	var disp_netB = false;
	
//	if (channel =="NOWTV" ){
//		disp_now = true;
//		disp_nowB = true;
//	}
//	if (channel == "NETVIGATOR"){
//		disp_net = true;
//		disp_netB = true;
//	}
//	if (channel == "BIZNETVIGATOR"){
//		disp_net = true;
//		disp_netB = true;
//	}
//	if (channel == "HKT" || channel == "PCCW" || channel == "OTHERS"){
//		disp_now = true;
//		disp_nowB = true;
//		disp_net = true;
//		disp_netB = true;
//	}
//	alert("disp_now = "+disp_now+", disp_nowB = "+disp_nowB+", disp_net = "+disp_net+", disp_netB = "+disp_netB);
	
	
//	if (!showNow || !mymarker.isNowB() || mymarker.getSf_bldg_bus()!="Y" ){
//		disp_nowB = false;
//	}
//	if (!showNow || !mymarker.isNow() || mymarker.getSf_bldg_res()!="Y" || disp_nowB){
//		disp_now = false;
//	}
//	if (!showNet || !mymarker.isNet()){
//		disp_net = false;
//	}
//	if (!showNetB || !mymarker.isNetB()){
//		disp_netB = false;
//	}
//	
//	if (!disp_now && !disp_nowB && !disp_net && !disp_netB){
//		if (showNow && mymarker.isNowB() && mymarker.getSf_bldg_bus()=="Y"){
//			disp_nowB = true;
//		}
//		if (showNow && mymarker.isNow() && mymarker.getSf_bldg_res()=="Y" && !disp_nowB){
//			disp_now = true;
//		}
//		if (showNet && mymarker.isNet()){
//			disp_net = true;
//		}
//		if (showNetB && mymarker.isNetB()){
//			disp_netB = true;
//		}
//	}
//	alert("disp_now = "+disp_now+", disp_nowB = "+disp_nowB+", disp_net = "+disp_net+", disp_netB = "+disp_netB);
	
	contentString.push(address_div);
	contentString.push('<div style="background-color:Gray;height:1px;"></div>');
	if (disp_net){
		contentString.push('<div class="bubble_black">');
		if(getBig_bubble_left(mymarker)){
			contentString.push('<div class="bubble_left_big"><img class="bubble_logo" src="./images/netvigator_logo.png" />&nbsp;</div>');
		}else{
		contentString.push('<div class="bubble_left"><img class="bubble_logo" src="./images/netvigator_logo.png" />&nbsp;</div>');
		}
		//contentString.push('<div class="bubble_text">Netvigator Broadband Internet<br/>'
		contentString.push('<div class="bubble_text">'+netvigator+'<br/>')
		contentString.push(getMaxService(mymarker, "net"));
		contentString.push('</div>');
		contentString.push('<div class="bubble_btn">');
/*		if(showChat(mymarker)){
		contentString.push('&nbsp;<img src="./images/live_chat_icon.gif" onclick="openChat(\'net\');" title="'+bubble_icon_livechat_net+'"/>');
		}
		if(CheckIsRM(mymarker)){
			contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'netRm\');" title="'+bubble_icon_hotline_netRM+'"/>');
		}else if(CheckIsPremier(mymarker)){
			contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'netPremier\');" title="'+bubble_icon_hotline_netPremier+'"/>');
		}else{
			contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'net\');" title="'+bubble_icon_hotline_net+'"/>');
		}*/
		//contentString.push('&nbsp;<img src="./images/registor.png" onmouseout="this.src=\'./images/registor.png\'" onmouseover="this.src=\'./images/registor_hover.png\'" width="57px" onclick="showForm(\'net\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_net+'"/>');
		contentString.push('&nbsp;<a href="#" class="Registorbutton" onclick="showForm(\'net\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_net+'">'+register+'</a>');
		contentString.push('</div>');
		contentString.push('<div style="clear:both;"></div>');
		contentString.push('</div>');	
	}
/*	if (disp_netB){
	contentString.push('<div class="bubble_white">');
	contentString.push('<div class="bubble_left"><img class="bubble_logo" src="./images/netvigator_biz_logo.png" />&nbsp;</div>');
	contentString.push('<div class="bubble_text">'+biz_netvigator+'<br/>');
	contentString.push(getMaxService(mymarker, "netB"));
	contentString.push('<div style="clear:both;"></div>');
	contentString.push('</div>');
	contentString.push('<div class="bubble_btn">');
	contentString.push('&nbsp;<img src="./images/live_chat_icon_biz.gif" onclick="openChat(\'net_b\');" title="'+bubble_icon_livechat_net+'"/>');
	contentString.push('&nbsp;<img src="./images/hotline_icon_biz.gif" onclick="showHotline(\'net_b\');" title="'+bubble_icon_hotline_netB+'"/>');
	contentString.push('&nbsp;<img src="./images/regi_icon_biz.gif" onclick="showForm(\'net_b\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_netB+'"/>');
	contentString.push('</div>');
	contentString.push('<div style="clear:both;"></div>');
	contentString.push('</div>');
	
	}
	if (disp_now){
	contentString.push('<div class="bubble_black">');
	contentString.push('<div class="bubble_left"><img class="bubble_logo_now" src="./images/nowtv_logo.png" />&nbsp;</div>');
	contentString.push('<div class="bubble_text">'+nowtv+'<br/>');
	contentString.push(getMaxService(mymarker, "now"));
	contentString.push('</div>');
	contentString.push('<div class="bubble_btn">');
	if(showChat(mymarker)){
	contentString.push('&nbsp;<img src="./images/live_chat_icon.gif" onclick="openChat(\'now\');" title="'+bubble_icon_livechat_now+'"/>');
	}
	contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'now\');" title="'+bubble_icon_hotline_now+'"/>');
	contentString.push('&nbsp;<img src="./images/regi_icon.gif" onclick="showForm(\'now\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_now+'"/>');
	contentString.push('</div>');
	contentString.push('<div style="clear:both;"></div>');
	contentString.push('</div>');	
	}
	if (disp_nowB){
		contentString.push('<div class="bubble_nowBiz">');
		contentString.push('<div class="bubble_left"><img class="bubble_logo_now" src="./images/nowtv_logo.png" />&nbsp;</div>');
		contentString.push('<div class="bubble_text">'+nowtv+'<br/>');
		contentString.push(getMaxService(mymarker, "nowB"));
		contentString.push('<div style="clear:both;"></div>');
		contentString.push('</div>');
		contentString.push('<div class="bubble_btn">');
		contentString.push('&nbsp;<img src="./images/live_chat_icon.gif" onclick="openChat(\'now_b\');" title="'+bubble_icon_livechat_now+'"/>');
		contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'now_b\');" title="'+bubble_icon_hotline_nowB+'"/>');
		contentString.push('&nbsp;<img src="./images/regi_icon.gif" onclick="showForm(\'now_b\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_nowB+'"/>');
		contentString.push('</div>');
		contentString.push('<div style="clear:both;"></div>');
		//contentString.push('<div class="bubble_nowBiz_remark"/>'+biz_now_remark+'</div>');
		contentString.push('</div>');	
	}*/
	contentString.push('<div style="height:4px;"></div>');
	var infoBubble = new InfoBubble({
		map : map,
		content : contentString.join(''),
		shadowStyle : 1,
		padding : 0,
		//backgroundColor : '#FF6600',
		backgroundColor : '#F8921D',
		borderRadius : 0,
		borderWidth : 0,
		borderColor : '#000',
		disableAutoPan : false,
		hideCloseButton : true,
		disableAnimation : true,
		arrowPosition : 0,
		arrowSize : 20,
		arrowStyle : 2,
		minWidth : 330,
		maxWidth : 330,
		minHeight : 10,
		maxHeight : 300
	});
	
	if (activeBubble) {
		activeBubble.close();
		$("#map_disclaimer").hide();
		activeBubble = null;
	} 
	infoBubble.open(map, marker);
	if (disp_net) {$('#map_disclaimer').fadeIn("quick");}
	// map.setCenter(marker.getPosition());
	activeBubble = infoBubble;
}

function attachMessage(marker, type, mymarker) {
	google.maps.event
			.addListener(
					marker,
					'click',
					function() {
						var contentString = [];
						var address_div = '<div class="bubble_address">'+marker.title+'</div>';
						contentString.push(address_div);
						contentString.push('<div style="border:1px solid #3E3535;"></div>');
						// infowindow.open(map,marker);
						if (map.getZoom() >= zoomArray[3]) {
							var item = "";
							type = "net";
/*							if (type == "now") {
								contentString.push('<div class="bubble_black">');
								contentString.push('<div class="bubble_left"><img class="bubble_logo_now" src="./images/nowtv_logo.png" />&nbsp;</div>');
								contentString.push('<div class="bubble_text">'+nowtv+'<br/>');
								contentString.push(getMaxService(mymarker, "now"));
								contentString.push('<div style="clear:both;"></div>');
								contentString.push('</div>');
								contentString.push('<div class="bubble_btn">');
								if(showChat(mymarker)){
								contentString.push('&nbsp;<img src="./images/live_chat_icon.gif" onclick="openChat(\'now\');" title="'+bubble_icon_livechat_now+'"/>');
								}
								contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'now\');" title="'+bubble_icon_hotline_now+'"/>');
								contentString.push('&nbsp;<img src="./images/regi_icon.gif" onclick="showForm(\'now\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_now+'"/>');
								contentString.push('</div>');
								contentString.push('<div style="clear:both;"></div>');
								contentString.push('</div>');
							}
							if (type == "nowB") {
								contentString.push('<div class="bubble_nowBiz">');
								contentString.push('<div class="bubble_left"><img class="bubble_logo_now" src="./images/nowtv_logo.png" />&nbsp;</div>');
								contentString.push('<div class="bubble_text">'+nowtv+'<br/>');
								contentString.push(getMaxService(mymarker, "nowB"));
								contentString.push('<div style="clear:both;"></div>');
								contentString.push('</div>');
								contentString.push('<div class="bubble_btn">');
								contentString.push('&nbsp;<img src="./images/live_chat_icon.gif" onclick="openChat(\'now_b\');" title="'+bubble_icon_livechat_now+'"/>');
								contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'now_b\');" title="'+bubble_icon_hotline_nowB+'"/>');
								contentString.push('&nbsp;<img src="./images/regi_icon.gif" onclick="showForm(\'now_b\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_nowB+'"/>');
								contentString.push('</div>');
								contentString.push('<div style="clear:both;"></div>');
//								contentString.push('<div class="bubble_nowBiz_remark"/>'+biz_now_remark+'</div>');
								contentString.push( '</div>');
							}*/
							if (type == "net") {
								contentString.push('<div class="bubble_black">');
								if(getBig_bubble_left(mymarker)){
									contentString.push('<div class="bubble_left_big"><img class="bubble_logo" src="./images/netvigator_logo.png" />&nbsp;</div>');
								}else{
								contentString.push('<div class="bubble_left"><img class="bubble_logo" src="./images/netvigator_logo.png" />&nbsp;</div>');
								}
								contentString.push('<div class="bubble_text">'+netvigator+'<br/>');
								contentString.push(getMaxService(mymarker, "net"));
								contentString.push('<div style="clear:both;"></div>');
								contentString.push('</div>');
								contentString.push('<div class="bubble_btn">');
/*								if(showChat(mymarker)){
								contentString.push('&nbsp;<img src="./images/live_chat_icon.gif" onclick="openChat(\'net\');" title="'+bubble_icon_livechat_net+'"/>');
								}
								if(CheckIsRM(mymarker)){
									contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'netRm\');" title="'+bubble_icon_hotline_netRM+'"/>');
								}else if(CheckIsPremier(mymarker)){
									contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'netPremier\');" title="'+bubble_icon_hotline_netPremier+'"/>');
								}else{
								contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'net\');" title="'+bubble_icon_hotline_net+'"/>');
								}*/
								//contentString.push('&nbsp;<img src="./images/registor.png" onmouseout="this.src=\'./images/registor.png\'" onmouseover="this.src=\'./images/registor_hover.png\'" width="57px" onclick="showForm(\'net\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_net+'"/>');
								contentString.push('&nbsp;<a href="#" class="Registorbutton" onclick="showForm(\'net\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_net+'">'+register+'</a>');
								contentString.push('</div>');
								contentString.push('<div style="clear:both;"></div>');
								contentString.push( '</div>');		
							}
							if (type == "net_busi") {
								contentString.push('<div class="bubble_white">');
								contentString.push('<div class="bubble_left"><img class="bubble_logo" src="./images/netvigator_biz_logo.png" />&nbsp;</div>');
								contentString.push('<div class="bubble_text">'+biz_netvigator+'<br/>');
								contentString.push(getMaxService(mymarker, "netB"));
								contentString.push('<div style="clear:both;"></div>');
								contentString.push('</div>');
								contentString.push('<div class="bubble_btn">');
								contentString.push('&nbsp;<img src="./images/live_chat_icon_biz.gif" onclick="openChat(\'net_b\');" title="'+bubble_icon_livechat_net+'"/>');
								contentString.push('&nbsp;<img src="./images/hotline_icon_biz.gif" onclick="showHotline(\'net_b\');" title="'+bubble_icon_hotline_netB+'"/>');
								contentString.push('&nbsp;<img src="./images/regi_icon_biz.gif" onclick="showForm(\'net_b\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_netB+'"/>');
								contentString.push('</div>');
								contentString.push('<div style="clear:both;"></div>');
								contentString.push( '</div>');
							}
							if (type == "net_n_busi") {
								contentString.push('<div class="bubble_black">');
								if(getBig_bubble_left(mymarker)){
									contentString.push('<div class="bubble_left_big"><img class="bubble_logo" src="./images/netvigator_logo.png" />&nbsp;</div>');
								}else{
								contentString.push('<div class="bubble_left"><img class="bubble_logo" src="./images/netvigator_logo.png" />&nbsp;</div>');
								}
								contentString.push('<div class="bubble_text">'+netvigator+'<br/>');
								contentString.push(getMaxService(mymarker, "net"));
								contentString.push('<div style="clear:both;"></div>');
								contentString.push('</div>');
								contentString.push('<div class="bubble_btn">');
/*								if(showChat(mymarker)){
								contentString.push('&nbsp;<img src="./images/live_chat_icon.gif" onclick="openChat(\'net\');" title="'+bubble_icon_livechat_net+'"/>');
								}
								if(CheckIsRM(mymarker)){
									contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'netRm\');" title="'+bubble_icon_hotline_netRM+'"/>');
								}else if(CheckIsPremier(mymarker)){
									contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'netPremier\');" title="'+bubble_icon_hotline_netPremier+'"/>');
								}else{
								contentString.push('&nbsp;<img src="./images/hotline_icon.gif" onclick="showHotline(\'net\');" title="'+bubble_icon_hotline_net+'"/>');
								}*/
								//contentString.push('&nbsp;<img src="./images/registor.png" onmouseout="this.src=\'./images/registor.png\'" onmouseover="this.src=\'./images/registor_hover.png\'" width="57px" onclick="showForm(\'net\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_net+'"/>');
								contentString.push('&nbsp;<a href="#" class="Registorbutton" onclick="showForm(\'net\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_net+'">'+register+'</a>');
								contentString.push('</div>');
								contentString.push('<div style="clear:both;"></div>');
								contentString.push( '</div>');
								contentString.push('<div class="bubble_white">');
								contentString.push('<div class="bubble_left"><img class="bubble_logo" src="./images/netvigator_biz_logo.png" />&nbsp;</div>');
								contentString.push('<div class="bubble_text">'+biz_netvigator+'<br/>');
								contentString.push(getMaxService(mymarker, "netB"));; 
								contentString.push('<div style="clear:both;"></div>');
								contentString.push('</div>');
								contentString.push('<div class="bubble_btn">');
								contentString.push('&nbsp;<img src="./images/live_chat_icon_biz.gif" onclick="openChat(\'net_b\');" title="'+bubble_icon_livechat_net+'"/>');
								contentString.push('&nbsp;<img src="./images/hotline_icon_biz.gif" onclick="showHotline(\'net_b\');" title="'+bubble_icon_hotline_netB+'"/>');
								contentString.push('&nbsp;<img src="./images/regi_icon_biz.gif" onclick="showForm(\'net_b\',\''+ encodeURIComponent(mymarker.getMarker_idx())+ '\');" title="'+bubble_icon_regi_netB+'"/>');
								contentString.push('</div>');
								contentString.push('<div style="clear:both;"></div>');
								contentString.push( '</div>');
							}
							contentString.push( '<div style="height:4px;"></div>');
							var infoBubble = new InfoBubble({
								map : map,
								content : contentString.join(''),
								shadowStyle : 1,
								padding : 0,
								//backgroundColor : '#FF6600',
								backgroundColor : '#F8921D',
								borderRadius : 0,
								borderWidth : 0,
								borderColor : '#000',
								disableAutoPan : false,
								hideCloseButton : true,
								disableAnimation : true,
								arrowPosition : 0,
								arrowSize : 20,
								arrowStyle : 2,
								minWidth : 330,
								maxWidth : 330,
								minHeight : 10,
								maxHeight : 300
							});

							if (activeBubble) {
								activeBubble.close();
								$("#map_disclaimer").hide();
								activeBubble = null;
							}
							infoBubble.open(map, marker);
							if(type == "net" || type == "net_n_busi") {$('#map_disclaimer').fadeIn("quick");}
							// map.setCenter(marker.getPosition());
							activeBubble = infoBubble;
						} else {
							map.setCenter(marker.getPosition());
							map.setZoom(map.getZoom() + 1);
						}

					});
}

function getInboundInfo(latlng) {
	if (markers && markers.length > 0) {
		var contentString =[];
		contentString.push("<div class='st_bubble'><div class='plz_sel'>");
		contentString.push(address_bubble_header);
		contentString.push("</div>");
		for ( var i in markers) {
			contentString.push("<div class='addr_lst'><a href='javascript:goToMarker(");
			contentString.push(markers[i].getLat() + "," + markers[i].getLng()+",\""+markers[i].getMarker_idx()+"\"");
			contentString.push(");' style='color:#FFF;'>");
			contentString.push(markers[i].getMarkerTitle(lang) + "</a></div>");
		}
		contentString.push("</div><div style='height:4px;'>&nbsp;</div>");
		var infoBubble = new InfoBubble({
			map : map,
			content : contentString.join(''),
			position : latlng,
			shadowStyle : 1,
			padding : 0,
//			backgroundColor : '#FF6600',
			backgroundColor : '#F8921D',
			borderRadius : 0,
			borderWidth : 0,
			borderColor : '#000',
			disableAutoPan : false,
			hideCloseButton : true,
			arrowPosition : 0,
			arrowSize : 20,
			arrowStyle : 2,
			minWidth : 200,
			maxWidth : 400,
			minHeight : 30,
			maxHeight : 250
		});

		activeBubble = infoBubble;
		infoBubble.open(map);
	}
}

function goToMarker(lat, lng, markerIdx) {
//	alert(markerIdx+":"+);
//	 alert("gotoMarker");
	if (activeBubble)
		activeBubble.close();
	$("#map_disclaimer").hide();
	activeBubble = null;
	map.setCenter(new google.maps.LatLng(lat, lng));
	if (map.getMapTypeId()==google.maps.MapTypeId.SATELLITE || map.getMapTypeId()==google.maps.MapTypeId.HYBRID){
		map.setZoom(zoomArray[zoomArray.length - 2]);
	}else{
		map.setZoom(zoomArray[zoomArray.length - 1]);
	}
	gotoMarkerIdx = markerIdx;
	goToLat =lat;
	goToLng = lng;
	var input = document.getElementById("quickSearch");
	input.blur();
	// setTimeout(function (){map.setZoom(zoomArray[zoomArray.length-1]);},
	// 1000);

}

function getMarkers(topRight, buttomLeft, zoomLevel, act) {
	var lat = [];
	var lng = [];
	markers = [];
//	alert("getMarker:"+ zoomLevel);
	if (zoomLevel == zoomArray[0] || zoomLevel == zoomArray[1]
			|| zoomLevel == zoomArray[2]) {
		$.ajax({
			url : 'marker.html?zoomlevel=' + getZoomLevel(zoomLevel)
					+ "&latLower=" + buttomLeft.lat() + "&latUpper="
					+ topRight.lat() + "&lngLower=" + buttomLeft.lng()
					+ "&lngUpper=" + topRight.lng(),
			type : 'POST',
			dataType : 'json',
			timeout : 5000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
//				alert(XMLHttpRequest+":"+textStatus+":"+errorThrown);
				// if(textStatus=='parsererror'){
				// alert(textStatus);
				// }
			},
			success : function(msg) {
//				alert("success");
//				alert(msg);
				$.each(eval(msg), function(i, item) {
//					alert(item.marker_desc_ch);
					marker = new myMarker({
						//NAME_EN : item.marker_desc_en,
						//NAME_CH : item.marker_desc_ch,
						housing_addr_en: item.marker_desc_en,
						housing_addr_ch: item.marker_desc_ch,
						lat : item.marker_lat,
						lng : item.marker_lng,
						bus_lat: item.bus_lat,
						bus_lng: item.bus_lng,
						res_lat: item.res_lat,
						res_lng: item.res_lng
					});
					marker.setNow(item.res_tv_ind == "Y"
							|| item.bus_tv_ind == "Y");
					marker.setNet(item.res_bb_ind == "Y");
					marker.setNetB(item.bus_bb_ind == "Y");
//					alert(item.bus_bb_ind+"--"+item.res_bb_ind+"--"+item.bus_tv_ind+"--"+item.res_tv_ind);
					// if (topRight.lat() > marker_lat[i] && marker_lat[i] >
					// buttomLeft.lat()
					// && topRight.lng() > marker_lng[i] && marker_lng[i] >
					// buttomLeft.lng()) {
					markers.push(marker);
					// }
				});
				if (act == "load") {
					loadMarkers();
				} else if (act == "refresh") {
//					refreshMarker(topRight, buttomLeft, zoomLevel);
					refreshMarker();
				}
			}
		});

	} else if (zoomLevel == zoomArray[3] || zoomLevel == zoomArray[4]) {
		// alert('marker.html?zoomlevel=' +
		// zoomLevel+"&latLower="+buttomLeft.lat()+"&latUpper="+topRight.lat()+"&lngLower="+buttomLeft.lng()+"&lngUpper="+topRight.lng());
		$.ajax({
			url : 'marker.html?zoomlevel=' + zoomLevel + "&latLower="
					+ buttomLeft.lat() + "&latUpper=" + topRight.lat()
					+ "&lngLower=" + buttomLeft.lng() + "&lngUpper="
					+ topRight.lng(),
			type : 'POST',
			dataType : 'json',
			timeout : 5000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// if(textStatus=='parsererror'){
				// alert(textStatus);
				// }
			},
			success : function(msg) {
				// alert("success");
				$.each(eval(msg), function(i, item) {
					marker = new myMarker({
						marker_idx: item.marker_idx,
						BUILD_XY : item.BUILD_XY,
						lat : item.lat,
						lng : item.lng,
						NAME_EN : item.NAME_EN,
						NAME_CH : item.NAME_CH,
						STREET_NUM : item.STREET_NUM,
						STREET_NAME_EN : item.STREET_NAME_EN,
						STREET_NAME_CH : item.STREET_NAME_CH,
						SITE_GROUP : item.SITE_GROUP,
						SF_BLDG_RES : item.SF_BLDG_RES,
						SF_BLDG_BUS : item.SF_BLDG_BUS,
						housing_addr_en: item.housing_addr_en,
						housing_addr_ch: item.housing_addr_ch,
						SECT_CD : item.SECT_CD,
						section_desc_en : item.section_desc_en,
						section_desc_ch : item.section_desc_ch,
						DISTR_CD : item.DISTR_CD,
						district_desc_en : item.district_desc_en,
						district_desc_ch : item.district_desc_ch,
						AREA_CD : item.AREA_CD,
						area_desc_en : item.area_desc_en,
						area_desc_ch : item.area_desc_ch,
						RES_BASIC_BW : item.RES_BASIC_BW,
						RES_FTTB_BW : item.RES_FTTB_BW,
						RES_FTTH_BW : item.RES_FTTH_BW,
						RES_TV_SD : item.RES_TV_SD,
						RES_TV_HD : item.RES_TV_HD,
						BUS_BASIC_BW : item.BUS_BASIC_BW,
						BUS_FTTB_BW : item.BUS_FTTB_BW,
						BUS_FTTH_BW : item.BUS_FTTH_BW,
						BUS_TV_SD : item.BUS_TV_SD,
						BUS_TV_HD : item.BUS_TV_HD,
						IS_RM : item.IS_RM,
						IS_PREMIER : item.IS_PREMIER,
						IS_PH : item.IS_PH,
						IS_HOS : item.IS_HOS
					});
//					marker.setNow(item.RES_TV_SD == "Y"
//							|| item.RES_TV_HD == "Y");
//					marker.setNet(item.RES_BASIC_BW != "null"
//							|| item.RES_FTTB_BW != "null"
//							|| item.RES_FTTH_BW != "null");
//					marker.setNetB(item.BUS_BASIC_BW != "null"
//							|| item.BUS_FTTB_BW != "null"
//							|| item.BUS_FTTH_BW != "null");

					markers.push(marker);

				});

				if (act == "load") {
					loadMarkers();
				} else if (act == "refresh") {
//					refreshMarker(topRight, buttomLeft, zoomLevel);
					refreshMarker();
				}
				// alert("markers.length:"+markers.length);
			}
		});
	}
}

function getZoomLevel(gLevel) {
	for ( var i = 0; i < zoomArray.length; i++) {
		if (gLevel == zoomArray[i])
			return i + 1;
	}
}

function isInbound(marker) {
	return map.getBounds().contains(marker.getPosition());
}

//function refreshMarker(topRight, buttomLeft, zoomLevel) {
function refreshMarker() {
	// var remove_cnt = 0;
//	alert(refreshMarker);
	for ( var i = 0; i < markers_d.length; i++) {
		if (!isInbound(markers_d[i])) {
			mm.removeMarker(markers_d[i]);
			// remove_cnt++;
		}
	}
	// alert("remove:"+remove_cnt);
	// for (var a = 0; a < b.length; a++) {
	// if (isOutBounds(b[a].getPosition())) {
	// mgr.removeMarker(b[a])
	// }
	// }
	var type;
	markers_d = [];
	var last_lat_max=lastBound.getNorthEast().lat() ;
	var last_lat_min=lastBound.getSouthWest().lat();
	var last_lng_max=lastBound.getNorthEast().lng() ;
	var las_lng_min=lastBound.getSouthWest().lng();
	for ( var i = 0; i < markers.length; i++) {
		marker = markers[i];
		var title;
		title = marker.getMarkerTitle(lang);
		if ((marker.isNet() && showNet) || (marker.isNetB() && showNetB)) {
			if (!(last_lat_max>=marker.getLat() && marker.getLat()>=last_lat_min && last_lng_max >= marker.getLng() && marker.getLng() >=las_lng_min)) {
				var point = new google.maps.LatLng(marker.getLat(), marker
						.getLng());
				var marker_d = new google.maps.Marker({
					position : point,
					title : title,
					icon : iconNetvigator,
					shadow : iconShadow
				});
				if (marker.isNet() && !marker.isNetB()){
					type="net";
				}else if (!marker.isNet() && marker.isNetB()){
					type="net_busi";
				}else if (marker.isNet() && marker.isNetB()){
					type="net_n_busi";
				}
				attachMessage(marker_d, type, marker);
				markers_d.push(marker_d);
				if (getZoomLevel(map.getZoom()) != 5) {
					mm.addMarker(marker_d, map.getZoom());
				} else {
					mm.addMarker(marker_d, zoomArray[3]);
				}
			}
		}
		if ((marker.isNow() && showNow ) || marker.isNowB() && showNow) {
			if (!(last_lat_max>=marker.getLat() && marker.getLat()>=last_lat_min && last_lng_max >= Number(marker.getLng()) + Number(adjust_latlng) && Number(marker.getLng()) + Number(adjust_latlng) >=las_lng_min)) {
				var point = new google.maps.LatLng(marker.getLat(),
						Number(marker.getLng()) + Number(adjust_latlng));
				var marker_d = new google.maps.Marker({
					position : point,
					title : title,
					icon : iconNowTv,
					shadow : iconShadow
				});
				if (marker.isNowB() && !marker.isNow()){
					type = "nowB";
				}else{
					if (marker.isNowB() && marker.isNow()){
						if (marker.getSf_bldg_bus() == "Y") {
							type = "nowB";
						} else {
							type = "now";
						}
					}else{
						type="now";
					}
				}
				attachMessage(marker_d, type, marker);

				markers_d.push(marker_d);
				if (getZoomLevel(map.getZoom()) != 5) {
					mm.addMarker(marker_d, map.getZoom());
				} else {
					mm.addMarker(marker_d, zoomArray[3]);
				}
			}
		}
	}
	lastBound = map.getBounds();
}

function showChat(marker){
	if(marker.isRm()=='Y'||marker.isPremier()=='Y'){
		//alert('rm: '+marker.isRm()+' Premier:'+marker.isPremier()+' ,is rm or premier');
		return false;
	}else{
		//alert('rm: '+marker.isRm()+' Premier:'+marker.isPremier()+' ,not rm and not premier');
		return true;
	}
}

function CheckIsRM(marker){
	if(marker.isRm()=='Y'){
		//alert('rm: '+marker.isRm()+' ,is rm');
		return true;
	}else{
		//alert('rm: '+marker.isRm()+' ,not rm');
		return false;
	}
}

function CheckIsPremier(marker){
	if(marker.isPremier()=='Y'){
		//alert(' Premier:'+marker.isPremier()+' ,is premier');
		return true;
	}else{
		//alert(' Premier:'+marker.isPremier()+' ,not premier');
		return false;
	}
}

function getMaxService(marker, type){
	if (type=="now"){
		if (marker.getRes_tv_hd()=="Y"){
			return now_hd;
		}else if (marker.getRes_tv_sd()=="Y"){
			return now_sd;
		}
	}
	if (type=="nowB"){ 
		if (marker.getBus_tv_hd()=="Y"){
			return now_hd;
		}else if (marker.getBus_tv_sd()=="Y"){
			return now_sd;
		}
	}
	if (type=="net"){
		var ftth_bw=0;
		if (!isNaN(marker.getRes_ftth_bw()))
			ftth_bw = Number(marker.getRes_ftth_bw());
		var fttb_bw=0;
		if (!isNaN(marker.getRes_fttb_bw()))
			fttb_bw = Number(marker.getRes_fttb_bw());
		var basic_bw=0;
		if (!isNaN(marker.getRes_basic_bw()))
			basic_bw = Number(marker.getRes_basic_bw());
		var high_bw=0;
		if (ftth_bw >= fttb_bw && ftth_bw >= basic_bw){
			high_bw = ftth_bw;
		}
		if (fttb_bw >= ftth_bw && fttb_bw >= basic_bw){
			high_bw = fttb_bw;
		}
		if (basic_bw >= ftth_bw && basic_bw >= fttb_bw){
			high_bw = basic_bw;
		}
		if (high_bw >=1000){
			document.getElementById("map_disclaimer").innerHTML = net_ftth_disclaimer;
			return net_ftth+" "+high_bw+"M*"+net_registerNow;
		}else if(high_bw <1000 && high_bw >= 100){
			document.getElementById("map_disclaimer").innerHTML = net_fttb_disclaimer;
			return net_fttb+" "+high_bw+"M*"+net_registerNow;
		}else if(high_bw <100 && high_bw >= 30){
			document.getElementById("map_disclaimer").innerHTML = net_basic_disclaimer;
			return net_fttb+" "+high_bw+"M*"+net_contact_for_high_speed;
		}else{
			document.getElementById("map_disclaimer").innerHTML = "";
			return net_basic+net_contact_for_high_speed;
		}
	}
	if (type=="netB"){
//		if (marker.getBus_ftth_bw() && marker.getBus_ftth_bw()!=""){
//			return net_ftth+": "+net_highest_bw+" "+marker.getBus_ftth_bw()+"M";
//		}else if(marker.getBus_fttb_bw() && marker.getBus_fttb_bw()!=""){
//			return net_fttb+": "+net_highest_bw+" "+marker.getBus_fttb_bw()+"M";
//		}else if(marker.getBus_basic_bw() && marker.getBus_basic_bw()!=""){
//			return net_basic+": "+net_highest_bw+" "+marker.getBus_basic_bw()+"M";
//		}
		
		if (isNaN(marker.getBus_ftth_bw()) && isNaN(marker.getBus_fttb_bw())){
			return net_biz_basic;
		}else{
			return net_biz_ftth;
		}
	}
}

function getBig_bubble_left(marker){
	var temp_string;
		var ftth_bw=0;
		if (!isNaN(marker.getRes_ftth_bw()))
			ftth_bw = Number(marker.getRes_ftth_bw());
		var fttb_bw=0;
		if (!isNaN(marker.getRes_fttb_bw()))
			fttb_bw = Number(marker.getRes_fttb_bw());
		var basic_bw=0;
		if (!isNaN(marker.getRes_basic_bw()))
			basic_bw = Number(marker.getRes_basic_bw());
		var high_bw=0;
		if (ftth_bw >= fttb_bw && ftth_bw >= basic_bw){
			high_bw = ftth_bw;
		}
		if (fttb_bw >= ftth_bw && fttb_bw >= basic_bw){
			high_bw = fttb_bw;
		}
		if (basic_bw >= ftth_bw && basic_bw >= fttb_bw){
			high_bw = basic_bw;
		}
		if (high_bw >=1000){
			document.getElementById("map_disclaimer").innerHTML = net_ftth_disclaimer;
			temp_string= net_ftth+" "+high_bw+"M*"+net_registerNow;
		}else if(high_bw <1000 && high_bw >= 100){
			document.getElementById("map_disclaimer").innerHTML = net_fttb_disclaimer;
			temp_string= net_fttb+" "+high_bw+"M*"+net_registerNow;
		}else if(high_bw <100 && high_bw >= 30){
			document.getElementById("map_disclaimer").innerHTML = net_basic_disclaimer;
			temp_string= net_fttb+" "+high_bw+"M*"+net_contact_for_high_speed;
		}else{
			document.getElementById("map_disclaimer").innerHTML = "";
			temp_string= net_basic+net_contact_for_high_speed;
		}
		//alert(temp_string.length);
		if (temp_string.length>80){
			return true;
		}else{
			return false;
		}
}
