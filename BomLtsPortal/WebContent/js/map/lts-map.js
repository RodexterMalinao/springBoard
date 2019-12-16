var iconShadow = new google.maps.MarkerImage("./images/shadow_marker.png");
var iconEye = new google.maps.MarkerImage("./images/eye_marker.png");
var iconFixline = new google.maps.MarkerImage(fixLineMarkerSrc);

var initLat = 22.310208;
var initLng = 114.135132;
var zoomArray = [ 11, 13, 16, 19, 20 ];
//var open_msg_window_level = 4;
var adjust_latlngArray = [ 0.008, 0.002, 0.0004, 0.00005, 0.00004 ];
var adjust_latlng = adjust_latlngArray[0];
var lastZoomLevel = zoomArray[0];
var map = null;
var mm = null; 
var showEye = false;
var showTel = false;
var markerLang = 'en';
if(lang == 'zh_HK'){
	markerLang = 'ch';
}
if(srvType == 'EYE' || srvType == 'ALL'){
	showEye = true;
}
if(srvType == 'DEL' || srvType == 'ALL'){
	showTel = true;
}
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


var mapdebug = false;
function debugConsole(msg){
	if(mapdebug){
		console.log(msg);	
	}
}

function init() {
	debugConsole("lts-map.js init");
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
	};

	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

	google.maps.event.addListener(map, "zoom_changed", function() {
		debugConsole("lts-map.js - zoom_changed");
		screenMask();
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
				if (d == zoomArray[4]){
					lastZoomLevel = zoomArray[4];
					adjust_latlng = adjust_latlngArray[4];
				}else if (d == zoomArray[3]){
					lastZoomLevel = zoomArray[3];
					adjust_latlng = adjust_latlngArray[3];
				}
			}
		}

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
		debugConsole("lts-map.js - center_changed, zoom level:" + getZoomLevel(map.getZoom()));
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
		debugConsole("lts-map.js - click");
		if (activeBubble) {
			activeBubble.close();
			$("#map_disclaimer").hide();
			activeBubble = null;
		} else if (map.getZoom() >= zoomArray[3]) {
			debugConsole("lts-map.js calling getInboundInfo");
			//getInboundInfo(event.latLng);
		}
	});

	mm = new MarkerManager(map);
	google.maps.event.addListener(mm, 'loaded', function() {
		getMarkers(map.getBounds().getNorthEast(), map.getBounds()
				.getSouthWest(), map.getZoom(), 'load');
	});
//	//TEMP
//	if(activeBubble){
//		activeBubble.open(map);
//	}
}

function loadMarkers() {

	debugConsole("lts-map.js loadMarkers");
	markers_d = [];
	
	for ( var i = 0; i < markers.length; i++) {
		
		marker = markers[i];
		var type = "";
		var icon = "";
		if ((marker.isEye() && showEye)) {
			type = "eye";
			icon = iconEye;
			createMarkerContent(type, icon, marker);
		}else if ((marker.isTel() && showTel)) {
			type = "tel";
			icon = iconFixline;
			createMarkerContent(type, icon, marker);
		}
	}

	
	gotoMarkerIdx = null;
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
}
 

function createMarkerContent(type, icon, marker) {
	debugConsole("lts-map.js createMarkerContent");
	var point;
	if (map.getZoom() == zoomArray[0] || map.getZoom() == zoomArray[1]
			|| map.getZoom() == zoomArray[2]) {
		point = new google.maps.LatLng(marker.getResLat(), marker
				.getResLng());
	} else {
		point = new google.maps.LatLng(marker.getLat(), marker.getLng());
	}
	
	var title = marker.getMarkerTitle(markerLang);
	
	var marker_d = new google.maps.Marker({
		position : point,
		title : title,
		icon : icon,
		shadow : iconShadow
	});

	attachMessage(marker_d, type, marker);
	if (getZoomLevel(map.getZoom()) == 5
			|| (getZoomLevel(map.getZoom()) == 4 && (map.getMapTypeId() == google.maps.MapTypeId.SATELLITE || map
					.getMapTypeId() == google.maps.MapTypeId.HYBRID))) {

		if (gotoMarkerIdx == marker.getMarker_idx()) {
			clickOnMarker(marker_d, type, marker);
			//attachGotoBubble(marker_d, marker);
			//activeBubble.open(map	);
			gotoMarkerIdx = null;
			goToLat = null;
			goToLng = null;
		}
	}
	markers_d.push(marker_d);
	


	
}

function getMarkerByLatLng(lat, lng){
	debugConsole("lts-map.js getMarkerByLatLng");
	for (var i=0;i<markers.length;i++){
		if (markers[i].getLat()==lat && markers[i].getLng() == lng)
			return markers[i];
	}
	return null;
}

function attachGotoBubble(marker, mymarker){
	debugConsole("lts-map.js attachGotoBubble");
	var contentString = [];
	var type = (srvType == 'EYE')? "eye": "del";
	
	contentString = getInfoBubbleString(marker, type, mymarker);
	var infoBubble = new InfoBubble({
		map : map,
		content : contentString.join(''),
		shadowStyle : 1,
		padding : 0,
		//backgroundColor : '#FF6600',
		backgroundColor : '#57CAA9',
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
	debugConsole("lts-map.js attachMessage");
	google.maps.event.addListener(marker,'click', function(){
		clickOnMarker(marker, type, mymarker);
	});
}

function clickOnMarker(marker, type, mymarker) {
	debugConsole("lts-map.js clickOnMarker");
	debugConsole("marker: " + marker);
	debugConsole("type: " + type);
	debugConsole("mymarker: " + mymarker);
	var contentString = [];
//	contentString.push('<div style="overflow-x: auto; overflow-y: hidden; cursor: default; clear: both; position: relative; padding: 0px; width: 330px; height: 65px; background-color: #57caa9; border-top-left-radius: 0px; border-top-right-radius: 0px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px; border: 0px solid rgb(0, 0, 0);">');
	
//	var address_div = '<div class="bubble_address">'+marker.title+'</div>';
//	contentString.push(address_div);
	if (map.getZoom() >= zoomArray[3]) {
		contentString = getInfoBubbleString(marker, type, mymarker);
		updateselected(mymarker.marker_idx);
		var infoBubble = new InfoBubble({
			map : map,
			content : contentString.join(''),
			shadowStyle : 1,
			padding : 0,
			//backgroundColor : '#57CAA9',
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
			maxWidth : 330
		});
		
		if (type == "eye") {
			infoBubble.setBackgroundColor('#57CAA9');
		}
		if (type == "tel") {
			infoBubble.setBackgroundColor('#00508F');
		}

		if (activeBubble) {
			activeBubble.close();
			$("#map_disclaimer").hide();
			activeBubble = null;
		}
		infoBubble.open(map, marker);
		//if(type == "net" || type == "net_n_busi") {$('#map_disclaimer').fadeIn("quick");}
		// map.setCenter(marker.getPosition());
		activeBubble = infoBubble;
	} else {
		map.setCenter(marker.getPosition());
		map.setZoom(map.getZoom() + 1);
	}
}


function getInfoBubbleString(marker, type, mymarker){
	debugConsole("lts-map.js getInfoBubbleString");
	var contentString = [];
	var bubbleoutside_s = '<div class="bubbleoutside" style="background-color:#57CAA9">';
	var logo_img = '<img src="./images/eye_logo_en.png">';
	var service = 'eye Home Tablet';
	if (type == "eye") {
		bubbleoutside_s = '<div class="bubbleoutside" style="background-color:#57CAA9">';
		logo_img = '<img src="./images/eye_logo_en.png">';
		service = srvDesc;
	}
	if (type == "tel") {
		bubbleoutside_s = '<div class="bubbleoutside" style="background-color:#00508F">';
		logo_img = '<img src="' + fixLineBubbleLogoSrc + '">';
		service = srvDesc;
	}
	contentString.push(bubbleoutside_s);
		contentString.push('<div class="bubbleBlack">');
			contentString.push('<div class="bubble_address">');
				contentString.push('<div class="bubbleText">');
					contentString.push(marker.title);
				contentString.push('</div>');
			contentString.push('</div>');
			contentString.push('<div class="bubbleborder_h"></div>');
			contentString.push('<div class="bubble_detail">');
				contentString.push('<div class="bubble_logo">');
					contentString.push(logo_img);
				contentString.push('</div>');
				contentString.push('<div class="bubble_desc">');
					contentString.push(service);
				contentString.push('</div>');
				contentString.push('<div class="bubble_btn">');
					contentString.push('<a class="Registorbutton" onclick="showoption(\''+ type + '\')" >'+ nextbtn + '</a>');
				//	contentString.push('<img onclick="showoption(\''+ type + '\')" title="Registration" src="./images/regi_icon.gif"> ');
				//	contentString.push('<img title="Hotline" src="./images/hotline_icon.gif">');
				//	contentString.push('<img id="livechat_del" title="Live Chat" src="./images/live_chat_icon.gif">');
				contentString.push('</div>');
			contentString.push('</div>');
			contentString.push('<div style="clear:both;"></div>');
		contentString.push('</div>');
	contentString.push('</div>');
	return contentString;
}

function getInboundInfo(latlng) {
	debugConsole("lts-map.js getInboundInfo");
	if (markers && markers.length > 0) {
		var contentString =[];
		contentString.push("<div class='st_bubble'><div class='plz_sel'>");
		contentString.push(address_bubble_header);
		contentString.push("</div>");
		for ( var i in markers) {
			contentString.push("<div class='addr_lst'><a href='javascript:goToMarker(");
			contentString.push(markers[i].getLat() + "," + markers[i].getLng()+",\""+markers[i].getMarker_idx()+"\"");
			contentString.push(");' style='color:#FFF;'>");
			contentString.push(markers[i].getMarkerTitle(markerLang) + "</a></div>");
		}
		contentString.push("</div><div style='height:4px;'>&nbsp;</div>");
		var infoBubble = new InfoBubble({
			map : map,
			content : contentString.join(''),
			position : latlng,
			shadowStyle : 1,
			padding : 0,
//			backgroundColor : '#FF6600',
			backgroundColor : '#57CAA9',
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
	debugConsole("lts-map.js goToMarker");
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

	
	debugConsole("lts-map.js markerIdx: " + markerIdx);
	debugConsole("lts-map.js MARKERS: " + markers);
	
	var targetMarker = getMarkerByLatLng(lat, lng);
	var type = (srvType == 'EYE')? "eye": "del";
	//clickOnMarker(targetMarker, type);
	//TEMP
//	var marker = markers[markerIdx];
//	var type = "";
//	if ((marker.isEye() && showEye)) {
//		type = "eye";
//		icon = iconEye;
//	}
//	if ((marker.isTel() && showTel)) {
//		type = "tel";
//		icon = iconFixline;
//	}
//	var title = marker.getMarkerTitle(lang);
//	var point = new google.maps.LatLng(marker.getLat(),
//			Number(marker.getLng()) + Number(adjust_latlng));
//	var marker_d = new google.maps.Marker({
//		position : point,
//		title : title,
//		icon : icon,
//		shadow : iconShadow
//	});
//	clickOnMarker(marker_d, type, markers[markerIdx]);
	debugConsole("lts-map.js goToMarker end");
}

function getMarkers(topRight, buttomLeft, zoomLevel, act) {
	debugConsole("lts-map.js getMarkers act: " + act);
	
	var lat = [];
	var lng = [];
	markers = [];

	if (zoomLevel == zoomArray[0] || zoomLevel == zoomArray[1]
			|| zoomLevel == zoomArray[2]) {
		
		$.ajax({
			url : 'marker.html?zoomLevel=' + getZoomLevel(zoomLevel)
					+ "&latLower=" + buttomLeft.lat() + "&latUpper="
					+ topRight.lat() + "&lngLower=" + buttomLeft.lng()
					+ "&lngUpper=" + topRight.lng(),
			type : 'POST',
			dataType : 'json',
			timeout : 5000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {},
			success : function(msg) {
				$.each(eval(msg), function(i, item) {
					marker = new myMarker({
						housing_addr_en: item.marker_desc_en,
						housing_addr_ch: item.marker_desc_ch,
						lat : item.marker_lat,
						lng : item.marker_lng,
						bus_lat: item.bus_lat,
						bus_lng: item.bus_lng,
						res_lat: item.res_lat,
						res_lng: item.res_lng
					});
					marker.setEye(item.res_eye_ind == "Y" || item.res_eye_pe_ind == "Y");
					marker.setTel(item.res_tel_ind == "Y");
					markers.push(marker);
				});
			},
			complete: function(){
				if (act == "load") {
					loadMarkers();
				} else if (act == "refresh") {
					refreshMarker();
				}
				screenMaskRemove();
			}
		});

	} else if (zoomLevel == zoomArray[3] || zoomLevel == zoomArray[4]) {
		
		$.ajax({
			url : 'marker.html?zoomLevel=' + zoomLevel + "&latLower="
					+ buttomLeft.lat() + "&latUpper=" + topRight.lat()
					+ "&lngLower=" + buttomLeft.lng() + "&lngUpper="
					+ topRight.lng(),
			type : 'POST',
			dataType : 'json',
			timeout : 5000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {},
			success : function(msg) {
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
						IS_CLEANED_VILLAGE : item.IS_CLEANED_VILLAGE,
						IS_HOS : item.IS_HOS,
						IS_HOS : item.IS_HOS,
						RES_EYE : item.res_eye,
						RES_EYE_PE : item.res_eye_pe,
						RES_TEL : item.res_tel
					});
					
					marker.setEye(item.res_eye == "Y" || item.res_eye_pe == "Y");
					marker.setTel(item.res_tel == "Y");
					markers.push(marker);
				});
			},
			complete: function(){
				if (act == "load") {
					loadMarkers();
				} else if (act == "refresh") {
					refreshMarker();
				}
				screenMaskRemove();
			}
		});

	}
	
//	if (act == "load") {
//		loadMarkers();
//	} else if (act == "refresh") {
//		refreshMarker();
//	}

}

function getZoomLevel(gLevel) {
	debugConsole("lts-map.js getZoomLevel");
	for ( var i = 0; i < zoomArray.length; i++) {
		if (gLevel == zoomArray[i])
			return i + 1;
	}
}

function isInbound(marker) {
	debugConsole("lts-map.js isInbound");
	return map.getBounds().contains(marker.getPosition());
}

//function refreshMarker(topRight, buttomLeft, zoomLevel) {
function refreshMarker() {
	debugConsole("lts-map.js refreshMarker");
	
	
	
	// var remove_cnt = 0;
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
		title = marker.getMarkerTitle(markerLang);
		debugConsole(title);

		if ((marker.isEye() && showEye )) {
			if (!(last_lat_max>=marker.getLat() && marker.getLat()>=last_lat_min && last_lng_max >= Number(marker.getLng()) + Number(adjust_latlng) && Number(marker.getLng()) + Number(adjust_latlng) >=las_lng_min)) {
				var point = new google.maps.LatLng(marker.getLat(), marker.getLng());
//						Number(marker.getLng()) + Number(adjust_latlng));
				var marker_d = new google.maps.Marker({
					position : point,
					title : title,
					icon : iconEye, //iconFixline,
					shadow : iconShadow
				});
				type = "eye";
				attachMessage(marker_d, type, marker);

				markers_d.push(marker_d);
				if (getZoomLevel(map.getZoom()) != 5) {
					mm.addMarker(marker_d, map.getZoom());
				} else {
					mm.addMarker(marker_d, zoomArray[3]);
				}
			}
		}else if ((marker.isTel() && showTel )) {
			if (!(last_lat_max>=marker.getLat() && marker.getLat()>=last_lat_min && last_lng_max >= Number(marker.getLng()) + Number(adjust_latlng) && Number(marker.getLng()) + Number(adjust_latlng) >=las_lng_min)) {
				var point = new google.maps.LatLng(marker.getLat(), marker.getLng());
//						Number(marker.getLng()) + Number(adjust_latlng));
				var marker_d = new google.maps.Marker({
					position : point,
					title : title,
					icon : iconFixline,
					shadow : iconShadow
				});
				type = "tel";
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
	debugConsole("lts-map.js showChat");
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

